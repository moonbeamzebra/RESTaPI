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
 * Copyright (C) 2012 - 2013 Pi4J
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


import java.util.Vector;



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
 * Gordon Henderson @ <a href="https://projects.drogon.net/">https://projects.drogon.net/</a>)
 * </blockquote>
 * </p>
 * 
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */

//Modified by jplaberge@magenta.ca : Separate native code to not native  
// January 2014

public class GpioInterruptNonNative {

    private static Vector<GpioInterruptListener> listeners = new Vector<GpioInterruptListener>();
    private Object lock;

    // private constructor 
    private GpioInterruptNonNative()  {
        // forbid object construction 
    }
    
    // Now done in GpioInterrupt (native only)
//    static {
//        // Load the platform library
//        NativeLibraryLoader.load("pi4j", "libpi4j.so");
//    }

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
// Remed out by jplaberge (Native)
//    public static int enablePinStateChangeCallback(int pin)
//    {
//    	return GpioInterrupt.enablePinStateChangeCallback(pin);
//    }

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
 // Remed out by jplaberge (Native)
//    public static int disablePinStateChangeCallback(int pin)    
//    {
//    	return GpioInterrupt.disablePinStateChangeCallback(pin);
//    }

    /**
     * <p>
     * This method is provided as the callback handler for the Pi4J native library to invoke when a
     * GPIO interrupt is detected. This method should not be called from any Java consumers. (Thus
     * is is marked as a private method.)
     * </p>
     * 
     * @param pin GPIO pin number (not header pin number; not wiringPi pin number)
     * @param state New GPIO pin state.
     */
    @SuppressWarnings("unchecked")
    // Modified by jplaberge@magenta.ca ; makes public
	public
    static void pinStateChangeCallback(int pin, boolean state) {

        Vector<GpioInterruptListener> dataCopy;
        dataCopy = (Vector<GpioInterruptListener>) listeners.clone();

        for (int i = 0; i < dataCopy.size(); i++) {
            GpioInterruptEvent event = new GpioInterruptEvent(listeners, pin, state);
            ((GpioInterruptListener) dataCopy.elementAt(i)).pinStateChange(event);
        }

        //System.out.println("In GpioInterrupt::pinStateChangeCallback : GPIO PIN [" + pin + "] = " + state);
    }

    /**
     * <p>
     * Java consumer code can all this method to register itself as a listener for pin state
     * changes.
     * </p>
     * 
     * @see #com.pi4j.wiringpi.GpioInterruptListener
     * @see #com.pi4j.wiringpi.GpioInterruptEvent
     * 
     * @param listener A class instance that implements the GpioInterruptListener interface.
     */
    public static synchronized void addListener(GpioInterruptListener listener) {
        if (!listeners.contains(listener)) {
            listeners.addElement(listener);
        }
    }

    /**
     * <p>
     * Java consumer code can all this method to unregister itself as a listener for pin state
     * changes.
     * </p>
     * 
     * @see #com.pi4j.wiringpi.GpioInterruptListener
     * @see #com.pi4j.wiringpi.GpioInterruptEvent
     * 
     * @param listener A class instance that implements the GpioInterruptListener interface.
     */
    public static synchronized void removeListener(GpioInterruptListener listener) {
        if (listeners.contains(listener)) {
            listeners.removeElement(listener);
        }
    }
    
    
    /**
     * <p>
     * Returns true if the listener is already registered for event callbacks.
     * </p>
     * 
     * @see #com.pi4j.wiringpi.GpioInterruptListener
     * @see #com.pi4j.wiringpi.GpioInterruptEvent
     * 
     * @param listener A class instance that implements the GpioInterruptListener interface.
     */
    public static synchronized boolean hasListener(GpioInterruptListener listener) {
        return listeners.contains(listener);
    }    
}
