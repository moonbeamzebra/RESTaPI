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
    
    public static final String PROPS_FILE_NAME_PROP = "RESTaPI.properties.file";
    public static final String PROPS_FILE_NAME = "RESTaPI.properties";

    public static Properties remoteConnectorProps = new Properties();

    private static final String DEFAULT_RESTaPI_IPAddress = "127.0.0.1";
    private static final String DEFAULT_restTransport = "http";
    private static final int DEFAULT_restPort = 8080;
    private static final int DEFAULT_gpioInterruptHandlerPort = 8181;
    public static final String REST_PATH = "/RESTaPI";

    public static int getGpioInterruptHandlerPort() {
        int port = -1;
        String portStr = remoteConnectorProps.getProperty("gpioInterruptHandlerPort");

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
        String info = "gpioInterruptHandlerPort=" + port;
        logger.info(info);
        return port;
    }

    public static String getRESTaPI_IPAddress() {
        String rESTaPI_IPAddress = remoteConnectorProps.getProperty("RESTaPI-IPAddress", DEFAULT_RESTaPI_IPAddress);
        String info = "RESTaPI-IPAddress=" + rESTaPI_IPAddress;
        logger.info(info);
        return rESTaPI_IPAddress;
    }

    
    public static String getRestURL()
    {
        String restURL = Globals.getRestTransport() + "://" + getRESTaPI_IPAddress() + ":" + Globals.getRestPort() + Globals.REST_PATH;
        String info = "RestURL is " + restURL;
        logger.info(info);
        return restURL;    
    }
    
    private static String getRestTransport() {
        String restTransport = remoteConnectorProps.getProperty("restTransport");

        if (restTransport == null)
        {
            restTransport = DEFAULT_restTransport;
        }

        String info = "restTransport=" + restTransport;
        logger.info(info);
        return restTransport;
    }

    private static int getRestPort() {
        int restPort = DEFAULT_restPort;
        String restPortStr = remoteConnectorProps.getProperty("restPort");

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

    public static void loadRESTaPIProperties() throws IOException {
        InputStream propsFile = null;
        
        String filename = System.getProperty(PROPS_FILE_NAME_PROP,PROPS_FILE_NAME); 
        
        File file = new File(filename);
        
        if (file.exists())
        {
            //propsFile = Globals.class.getClassLoader().getResourceAsStream(filename);
    		propsFile = new FileInputStream(file);
            remoteConnectorProps.load(propsFile);
        }
        else
        {
            remoteConnectorProps.put("RESTaPI-IPAddress", DEFAULT_RESTaPI_IPAddress);
            remoteConnectorProps.put("restTransport", DEFAULT_restTransport);
            remoteConnectorProps.put("restPort", Integer.toString(DEFAULT_restPort));
            remoteConnectorProps.put("gpioInterruptHandlerPort", Integer.toString(DEFAULT_gpioInterruptHandlerPort));
        }

        if (propsFile != null) {
        }

    }

}
