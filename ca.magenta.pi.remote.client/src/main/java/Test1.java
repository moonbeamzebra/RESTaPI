// START SNIPPET: control-gpio-snippet

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Examples
 * FILENAME      :  ControlGpioExample.java  
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

import java.io.IOException;


import ca.magenta.pi.remote.connector.RemoteWiringpiConnector;

import com.pi4j.connector.WiringpiConnectorInterface;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;


/**
 * This example code demonstrates how to perform simple state
 * control of a GPIO pin on the Raspberry Pi.  
 * 
 * @author Robert Savage
 */
public class Test1 {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        
        System.out.println("<--Pi4J--> GPIO Control Example ... started.");
        
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        
        WiringpiConnectorInterface wiringpiConnector = new RemoteWiringpiConnector();

        wiringpiConnector.wiringPiSetup();
        
        //wiringpiConnector.setDirection(1, 0);  // Boolean
        wiringpiConnector.delay(10000);	// Status
        //System.out.println("millis:" + wiringpiConnector.millis());	// Long
        System.out.println("Value:" + wiringpiConnector.digitalRead(1)  );	// Long
        
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
        
        System.out.println("<--Pi4J--> GPIO Control Example ... ENDed.");
    }
}
//END SNIPPET: control-gpio-snippet
