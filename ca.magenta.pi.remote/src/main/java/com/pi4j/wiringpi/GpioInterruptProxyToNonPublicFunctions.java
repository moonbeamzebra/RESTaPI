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

//Modified by jplaberge@magenta.ca : Separate native code to not native  
//January 2014

/**
 * <p>
 * This class provides static methods to configure the native Pi4J library to listen to GPIO
 * interrupts and invoke callbacks into this class. Additionally, this class provides a listener
 * registration allowing Java consumers to subscribe to GPIO pin state changes.
 * </p>
 * 
 * <p>
 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
 * the following system libraries:
 * <ul>
 * <li>pi4j</li>
 * <li>wiringPi</li>
 * </ul>
 * <blockquote> This library depends on the wiringPi native system library.</br> (developed by
 * Gordon Henderson @ <a href="http://wiringpi.com/">http://wiringpi.com/</a>)
 * </blockquote>
 * </p>
 * 
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class GpioInterruptProxyToNonPublicFunctions {
	


	

    /**
     * <p>
     * This method is used to instruct the native code to setup a monitoring thread to monitor
     * interrupts that represent changes to the selected GPIO pin.
     * </p>
     * 
     * <p>
     * <b>The GPIO pin must first be exported before it can be monitored.</b>
     * </p>
     * 
     * @param pin GPIO pin number (not header pin number; not wiringPi pin number)
     * @return A return value of a negative number represents an error. A return value of '0'
     *         represents success and that the GPIO pin is already being monitored. A return value
     *         of '1' represents success and that a new monitoring thread was created to handle the
     *         requested GPIO pin number.
     */
    public static int enablePinStateChangeCallback(int pin)
    {
    	return com.pi4j.wiringpi.GpioInterrupt.enablePinStateChangeCallback(pin);
    }

    /**
     * <p>
     * This method is used to instruct the native code to stop the monitoring thread monitoring
     * interrupts on the selected GPIO pin.
     * </p>
     * 
     * @param pin GPIO pin number (not header pin number; not wiringPi pin number)

     * @return A return value of a negative number represents an error. A return value of '0'
     *         represents success and that no existing monitor was previously running. A return
     *         value of '1' represents success and that an existing monitoring thread was stopped
     *         for the requested GPIO pin number.
     */
    public static int disablePinStateChangeCallback(int pin)
    {
    	return com.pi4j.wiringpi.GpioInterrupt.disablePinStateChangeCallback(pin);
    }
}
