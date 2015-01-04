package com.pi4j.io.gpio;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.pi4j.connector.ConnectorUtils;
import com.pi4j.connector.LocalWiringpiConnector;
import com.pi4j.connector.Pi4jConnectorException;
import com.pi4j.connector.WiringpiConnector;
import com.pi4j.connector.WiringpiConnector.WiringpiConnectorFactory;
import com.pi4j.io.gpio.event.PinListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioInterruptListener;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  RaspiGpioProvider.java  
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

/**
 * Raspberry PI {@link GpioProvider} implementation.
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */

//Modified by jplaberge@magenta.ca :Add log4j facilities 
//                                  Add WiringpiConnector layer
//										Every reference to original:
//											com.pi4j.wiringpi.Gpio,
//											com.pi4j.wiringpi.GpioUtil and
//											com.pi4j.wiringpi.GpioInterrupt
//										have been replaced WiringpiConnector
//										Original implementation in LocalWiringpiConnector
//                                  Call com.pi4j.wiringpi.GpioInterruptNonNative instead of
//                                  com.pi4j.wiringpi.GpioInterrupt
// January 2014
//OK for pi4j-1.0-SNAPSHOT - jplaberge
@SuppressWarnings("unused")
public class RaspiGpioProvider extends GpioProviderBase implements GpioProvider, GpioInterruptListener {

    
    public static final String NAME = "RaspberryPi GPIO Provider";
    
    // Next 2 added jplaberge
    private static Logger logger = Logger.getLogger(RaspiGpioProvider.class);
    private WiringpiConnector wiringpiConnector;
    
    public RaspiGpioProvider() {
        // set wiringPi interface for internal use
        // we will use the WiringPi pin number scheme with the wiringPi library

        // Next added by jplaberge
    	wiringpiConnector = WiringpiConnectorFactory.getInstance(); 

        wiringpiConnector.wiringPiSetup();
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean hasPin(Pin pin) {
        return (wiringpiConnector.isPinSupported(pin.getAddress()) >= 0);
    }

    // old version for 0.0.5
//    @Override
//    public void export(Pin pin, PinMode mode) {
//        super.export(pin, mode);
//       
//        //System.out.println("-- EXPORTING PIN [" + pin.getAddress() + "] to mode [" + mode.getName() + "]");       
//        
//        // export the pin and set the mode
//        wiringpiConnector.export(pin.getAddress(), mode.getDirection().getValue());
//        setMode(pin, mode);
//    }
    
    @Override
    public void export(Pin pin, PinMode mode) {
        super.export(pin, mode);
       
        //System.out.println("-- EXPORTING PIN [" + pin.getAddress() + "] to mode [" + mode.getName() + "]");       

        // if not already exported, export the pin and set the pin direction
        if(!wiringpiConnector.isExported(pin.getAddress())){
        	wiringpiConnector.export(pin.getAddress(), mode.getDirection().getValue());
        }
        // if the pin is already exported, then check its current configured direction
        // if the direction does not match, then set the new direction for the pin
        else if(wiringpiConnector.getDirection(pin.getAddress()) == mode.getDirection().getValue()){
        	wiringpiConnector.setDirection(pin.getAddress(), mode.getDirection().getValue());
        }

        // set the pin input/output mode
        setMode(pin, mode);
    }

    @Override
    public boolean isExported(Pin pin) {
        super.isExported(pin);
        
        // return the pin exported state
        return wiringpiConnector.isExported(pin.getAddress());
    }

    @Override
    public void unexport(Pin pin) {
        super.unexport(pin);

        // unexport the pins
        wiringpiConnector.unexport(pin.getAddress());
    }

    @Override
    public void setMode(Pin pin, PinMode mode) {
        super.setMode(pin, mode);

        wiringpiConnector.pinMode(pin.getAddress(), mode.getValue());
        
        // if this is an input pin, then configure edge detection
        if (PinMode.allInputs().contains(mode)) {
            wiringpiConnector.setEdgeDetection(pin.getAddress(), PinEdge.BOTH.getValue());
        }
    }

    @Override
    public PinMode getMode(Pin pin) {
        // TODO : get actual pin mode from native impl
        return super.getMode(pin);
    }
    
    @Override
    public void setPullResistance(Pin pin, PinPullResistance resistance) {
        super.setPullResistance(pin, resistance);

        wiringpiConnector.pullUpDnControl(pin.getAddress(), resistance.getValue());
    }

    @Override
    public PinPullResistance getPullResistance(Pin pin) {
        // TODO : get actual pin pull resistance from native impl
        return super.getPullResistance(pin);
    }
    
    @Override
    public void setState(Pin pin, PinState state) {
        super.setState(pin, state);

        wiringpiConnector.digitalWrite(pin.getAddress(), state.getValue());        
    }

    @Override
    public PinState getState(Pin pin) {
        super.getState(pin);
        
        // return pin state
        PinState state = null;
        int ret = wiringpiConnector.digitalRead(pin.getAddress());
        if (ret >= 0) {
            state = PinState.getState(ret);
        }
        return state;
    }

    @Override
    public void setValue(Pin pin, double value) {
        super.setValue(pin, value);
        throw new RuntimeException("This GPIO provider does not support analog pins.");
    }

    @Override
    public double getValue(Pin pin) {
        super.getValue(pin);
        throw new RuntimeException("This GPIO provider does not support analog pins.");
    }

    @Override
    public void setPwm(Pin pin, int value) {
        super.setPwm(pin, value);

        if (getMode(pin) == PinMode.PWM_OUTPUT) {
            setPwmValue(pin, value);
        }
    }

    @Override
    public int getPwm(Pin pin) {
        return super.getPwm(pin);
    }
    
    // internal
    private void setPwmValue(Pin pin, int value) {
        // set pin PWM value
        wiringpiConnector.pwmWrite(pin.getAddress(), value);
    }

    @Override
    public void pinStateChange(GpioInterruptEvent event) {
        // iterate over the pin listeners map
        for (Pin pin : listeners.keySet()) {
            // dispatch this event to the listener 
            // if a matching pin address is found
            if (pin.getAddress() == event.getPin()) {
                dispatchPinDigitalStateChangeEvent(pin, PinState.getState(event.getState()));
            }            
        }
    }

    @Override
    public void addListener(Pin pin, PinListener listener) {
        super.addListener(pin, listener);

        // update the native interrupt listener thread for callbacks
        updateInterruptListener(pin);        
    }
    
    @Override
    public void removeListener(Pin pin, PinListener listener) {
        super.removeListener(pin, listener);
        
        // update the native interrupt listener thread for callbacks
        updateInterruptListener(pin);        
    }
    
    // Next modified jplaberge
    // To let wiringpiConnector implement their own
    @Override
    public void shutdown() {
       // prevent reentrant invocation
        if(isShutdown())
            return;
        
        // set shutdown tracking state variable
        isshutdown = true;
        
        wiringpiConnector.shutdown();
    }     
    
    // internal 
    private void updateInterruptListener(Pin pin) {
        if (listeners.size() > 0) {
            // setup interrupt listener native thread and enable callbacks
        	// All next modified by jplaberge, calling wiringpiConnector methods instead of GpioInterrupt ones
            if (!wiringpiConnector.hasListener(this)) {
            	wiringpiConnector.addListener(this);
            }
            wiringpiConnector.enablePinStateChangeCallback(pin.getAddress());
        } else {
            // remove interrupt listener, disable native thread and callbacks
        	wiringpiConnector.disablePinStateChangeCallback(pin.getAddress());
            if (wiringpiConnector.hasListener(this)) {
            	wiringpiConnector.removeListener(this);
            }
        }
    }    
}
