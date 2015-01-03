package ca.magenta.pi.remote;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ca.magenta.pi.remote.common.Globals;

/*
 * #%L
Copyright 2014 Magenta INC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

/**
 * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
 * @version 0.1
 * @since January 2014
 */
public class RemoteRaspberryPiContextListener implements ServletContextListener {
    
    private static Logger logger = Logger.getLogger(RemoteRaspberryPiContextListener.class);

    private GpioInterruptHandler myThread = null;

    public void contextInitialized(ServletContextEvent sce) {

        try {
            Globals.loadRemoteRaspberryPIProperties();

            if ((myThread == null) || (!myThread.getRunner().isAlive())) {
                myThread = new GpioInterruptHandler(this);
                logger.info("Starting InterruptHandler...");
                myThread.startServer(Globals.getGpioInterruptHandlerPort());
                logger.info("InterruptHandler started");
            }
        } catch (IOException e) {
            
            logger.error("",e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            myThread.stopServer();
        } catch (Exception ex) {
        }
    }
}