package com.pi4j.wiringpi;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  GpioInterrupt.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2014 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.PrintWriter;
import java.util.HashSet;


import org.apache.log4j.Logger;

import ca.magenta.pi.remote.common.WiringPiInterruptHandlerMsg;


import com.google.gson.Gson;
import com.pi4j.util.NativeLibraryLoader;

/**
 * <p>
 * This class provides static methods to configure the native Pi4J library to
 * listen to GPIO interrupts and invoke callbacks into this class. Additionally,
 * this class provides a listener registration allowing Java consumers to
 * subscribe to GPIO pin state changes.
 * </p>
 * 
 * <p>
 * Before using the Pi4J library, you need to ensure that the Java VM in
 * configured with access to the following system libraries:
 * <ul>
 * <li>pi4j</li>
 * <li>wiringPi</li>
 * </ul>
 * <blockquote> This library depends on the wiringPi native system library.</br>
 * (developed by Gordon Henderson @ <a
 * href="https://projects.drogon.net/">https://projects.drogon.net/</a>)
 * </blockquote>
 * </p>
 * 
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www
 *         .savagehomeautomation.com</a>)
 */

// Modified by jplaberge@magenta.ca : Separate native code to not native
// January 2014

public class GpioInterrupt {
	
	private static Logger logger = Logger.getLogger(GpioInterrupt.class);

	private static HashSet<PrintWriter> outToClientPrintWriters = new HashSet<PrintWriter>();

	synchronized public static void addOutToClientPrintWriter(String clientAddress, PrintWriter outToClientPrintWriter) {
		logger.debug("Adding outToClientPrintWriter; client " + clientAddress);
		GpioInterrupt.outToClientPrintWriters.add(outToClientPrintWriter);
		logger.debug("Adding outToClientPrintWriter...done");
	}

	synchronized public static void removeOutToClientPrintWriter(String clientAddress, PrintWriter outToClientPrintWriter) {
		logger.debug("Removing outToClientPrintWriter; client " + clientAddress);
		GpioInterrupt.outToClientPrintWriters.remove(outToClientPrintWriter);
		logger.debug("Removing outToClientPrintWriter...done");
	}


	// private constructor
	private GpioInterrupt() {
		// forbid object construction
	}

	static {
		// Load the platform library
		logger.trace("NativeLibraryLoader.load");
		// 0.0.5 NativeLibraryLoader.load("pi4j", "libpi4j.so");
		// New way in pi4j-1.0-SNAPSHOT
		NativeLibraryLoader.load("libpi4j.so");
	}

	/**
	 * <p>
	 * This method is used to instruct the native code to setup a monitoring
	 * thread to monitor interrupts that represent changes to the selected GPIO
	 * pin.
	 * </p>
	 * 
	 * <p>
	 * <b>The GPIO pin must first be exported before it can be monitored.</b>
	 * </p>
	 * 
	 * @param pin
	 *            GPIO pin number (not header pin number; not wiringPi pin
	 *            number)
	 * @return A return value of a negative number represents an error. A return
	 *         value of '0' represents success and that the GPIO pin is already
	 *         being monitored. A return value of '1' represents success and
	 *         that a new monitoring thread was created to handle the requested
	 *         GPIO pin number.
	 */
	static native int enablePinStateChangeCallback(int pin);

	/**
	 * <p>
	 * This method is used to instruct the native code to stop the monitoring
	 * thread monitoring interrupts on the selected GPIO pin.
	 * </p>
	 * 
	 * @param pin
	 *            GPIO pin number (not header pin number; not wiringPi pin
	 *            number)
	 * 
	 * @return A return value of a negative number represents an error. A return
	 *         value of '0' represents success and that no existing monitor was
	 *         previously running. A return value of '1' represents success and
	 *         that an existing monitoring thread was stopped for the requested
	 *         GPIO pin number.
	 */
	static native int disablePinStateChangeCallback(int pin);

	/**
	 * <p>
	 * This method is provided as the callback handler for the Pi4J native
	 * library to invoke when a GPIO interrupt is detected. This method should
	 * not be called from any Java consumers. (Thus is is marked as a private
	 * method.)
	 * </p>
	 * 
	 * @param pin
	 *            GPIO pin number (not header pin number; not wiringPi pin
	 *            number)
	 * @param state
	 *            New GPIO pin state.
	 */
	synchronized private static void pinStateChangeCallback(int pin, boolean state) {

		logger.trace("PinStateChangeCallback : GPIO PIN [" + pin + "] change to " + state);

		WiringPiInterruptHandlerMsg msg = new WiringPiInterruptHandlerMsg();
		msg.setPin(pin);
		msg.setState(state);

		String msgInJSon = (new Gson()).toJson(msg);
		logger.debug("pinStateChangeCallback : Returning JSON [" + msgInJSon + "]");

		for (PrintWriter out : outToClientPrintWriters ) {
			if (out != null) {
				logger.debug("Out to client");
				out.println(msgInJSon);
			}
		}
	}

}
