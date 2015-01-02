package ca.magenta.pi.remote.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

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
public class Globals {

    private static Logger logger = Logger.getLogger(Globals.class);
    
    public static final String PROPS_FILE_NAME_PROP = "rRaspPI.properties.file";
    public static final String PROPS_FILE_NAME = "rRaspPI.properties";

    public static Properties remoteConectorProps = new Properties();

    private static final String DEFAULT_remoteRaspberryPiIPAddress = "127.0.0.1";
    private static final String DEFAULT_restTransport = "http";
    private static final int DEFAULT_restPort = 8080;
    private static final int DEFAULT_gpioInterruptHandlerPort = 8181;
    public static final String REST_PATH = "/remoteRaspberryPI/RESTAPI";

    public static int getGpioInterruptHandlerPort() {
        int port = -1;
        String portStr = remoteConectorProps.getProperty("gpioInterruptHandlerPort");

        if (portStr != null)
        {
            try {
                port = Integer.parseInt(portStr);
            } catch (Throwable e) {
                logger.error("Error in parseInt(" + portStr + ")", e);
            }
        }
        else
        {
            port = DEFAULT_gpioInterruptHandlerPort;
        }

        logger.info("gpioInterruptHandlerPort=" + port);
        return port;
    }

    public static String getRemoteRaspberryPiIPAddress() {
        String remoteRaspberryPiIPAddress = remoteConectorProps.getProperty("remoteRaspberryPiIPAddress", DEFAULT_remoteRaspberryPiIPAddress);
        logger.info("remoteRaspberryPiIPAddress=" + remoteRaspberryPiIPAddress);
        return remoteRaspberryPiIPAddress;
    }

    
    public static String getRestURL()
    {
        String restURL = Globals.getRestTransport() + "://" + getRemoteRaspberryPiIPAddress() + ":" + Globals.getRestPort() + Globals.REST_PATH;
        logger.info("RestURL is " + restURL);
        return restURL;    
    }
    
    private static String getRestTransport() {
        String restTransport = remoteConectorProps.getProperty("restTransport");

        if (restTransport == null)
        {
            restTransport = DEFAULT_restTransport;
        }

        logger.info("restTransport=" + restTransport);
        return restTransport;
    }

    private static int getRestPort() {
        int restPort = DEFAULT_restPort;
        String restPortStr = remoteConectorProps.getProperty("restPort");

        if (restPortStr != null)
        {
            try {
                restPort = Integer.parseInt(restPortStr);
            } catch (Throwable e) {
                logger.error("Error in parseInt(" + restPortStr + ")", e);
            }
        }
        else
        {
            restPort = DEFAULT_restPort;
        }

        logger.info("restPort=" + restPort);
        return restPort;
    }

    public static void loadRemoteRaspberryPIProperties() throws IOException {
        InputStream propsFile = null;
        
        String filename = System.getProperty(PROPS_FILE_NAME_PROP,PROPS_FILE_NAME); 
        
        File file = new File(filename);
        
        if (file.exists())
        {
            //propsFile = Globals.class.getClassLoader().getResourceAsStream(filename);
    		propsFile = new FileInputStream(file);
            remoteConectorProps.load(propsFile);
        }
        else
        {
            remoteConectorProps.put("remoteRaspberryPiIPAddress", DEFAULT_remoteRaspberryPiIPAddress);
            remoteConectorProps.put("restTransport", DEFAULT_restTransport);
            remoteConectorProps.put("restPort", Integer.toString(DEFAULT_restPort));
            remoteConectorProps.put("gpioInterruptHandlerPort", Integer.toString(DEFAULT_gpioInterruptHandlerPort));
        }

        if (propsFile != null) {
        }

    }

}
