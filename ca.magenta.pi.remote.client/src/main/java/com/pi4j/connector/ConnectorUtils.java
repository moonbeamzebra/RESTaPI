/**
 * 
 */
package com.pi4j.connector;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
 * @version 0.1
 * @since 2014-12-29
 */
public class ConnectorUtils {

	private static Logger logger = Logger.getLogger(ConnectorUtils.class);

	private static final String PI4J_PROPERTIES_FILE = "pi4j.properties.file";

	public static String readConnectorClassProperty(String connectorClassProperty) {

		String connectorClass = null;
		Properties props = new Properties();
		InputStream propsFile = null;
		try {
			if (logger.isDebugEnabled())
				logger.debug("Current dir: " + System.getProperty("user.dir"));
			String filename = System.getProperty(PI4J_PROPERTIES_FILE);
			if (filename == null) {
				filename = "pi4j.properties";
			}
			if (logger.isDebugEnabled())
				logger.debug(PI4J_PROPERTIES_FILE + "=[" + filename + "]");

			File file = new File(filename);

			if (file.exists()) {
				propsFile = new FileInputStream(filename);
				if (propsFile != null) {
					props.load(propsFile);
					String connectorClassStr = props.getProperty(connectorClassProperty);
					if (connectorClassStr != null) {
						connectorClass = connectorClassStr;
						if (logger.isDebugEnabled())
							logger.debug("Alternative Connector Class=" + connectorClass);
					}
				}
			}
		} catch (Throwable e) {
			logger.error("", e);
			connectorClass = null;
		}

		return connectorClass;
	}

}
