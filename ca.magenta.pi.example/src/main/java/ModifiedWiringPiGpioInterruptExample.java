
/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Examples
 * FILENAME      :  WiringPiGpioInterruptExample.java  
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
import com.pi4j.connector.WiringpiConnector;
import com.pi4j.connector.WiringpiConnector.WiringpiConnectorFactory;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;

public class ModifiedWiringPiGpioInterruptExample {
    
    public static void main(String args[]) throws InterruptedException {
    	
        
        System.out.println("<--Pi4J--> GPIO INTERRUPT test program");

        final WiringpiConnector wiringpiConnector = WiringpiConnectorFactory.getInstance();
        
        // create and add GPIO listener 
        wiringpiConnector.addListener(new GpioInterruptListener() {
            @Override
            public void pinStateChange(GpioInterruptEvent event) {
                System.out.println("Raspberry Pi PIN [" + event.getPin() +"] is in STATE [" + event.getState() + "]");
                
                if(event.getPin() == 7) {
                	wiringpiConnector.digitalWrite(6, event.getStateValue());
                }
                if(event.getPin() == RaspiPin.GPIO_02.getAddress()) {
                	wiringpiConnector.digitalWrite(5, event.getStateValue());
                }
            }
        });
        
        // setup wiring pi
        if (wiringpiConnector.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        // export all the GPIO pins that we will be using
        wiringpiConnector.export(RaspiPin.GPIO_02.getAddress(), GpioUtil.DIRECTION_IN);
        wiringpiConnector.export(7, GpioUtil.DIRECTION_IN);
        wiringpiConnector.export(5, GpioUtil.DIRECTION_OUT);
        wiringpiConnector.export(6, GpioUtil.DIRECTION_OUT);
        
        // set the edge state on the pins we will be listening for
        wiringpiConnector.setEdgeDetection(0, GpioUtil.EDGE_BOTH);
        wiringpiConnector.setEdgeDetection(7, GpioUtil.EDGE_BOTH);
        
        // configure GPIO pins 5, 6 as an OUTPUT;
        wiringpiConnector.pinMode(5, Gpio.OUTPUT);
        wiringpiConnector.pinMode(6, Gpio.OUTPUT);

        // configure GPIO 0 as an INPUT pin; enable it for callbacks
        wiringpiConnector.pinMode(RaspiPin.GPIO_02.getAddress(), Gpio.INPUT);
        wiringpiConnector.pullUpDnControl(RaspiPin.GPIO_02.getAddress(), Gpio.PUD_DOWN);        
        wiringpiConnector.enablePinStateChangeCallback(RaspiPin.GPIO_02.getAddress());
        
        // configure GPIO 7 as an INPUT pin; enable it for callbacks
        wiringpiConnector.pinMode(7, Gpio.INPUT);
        wiringpiConnector.pullUpDnControl(7, Gpio.PUD_DOWN);        
        wiringpiConnector.enablePinStateChangeCallback(7);

        System.out.println("Endless loop ...");
        // continuously loop to prevent program from exiting
        for (;;) {
            Thread.sleep(5000);
        }
    }
}
