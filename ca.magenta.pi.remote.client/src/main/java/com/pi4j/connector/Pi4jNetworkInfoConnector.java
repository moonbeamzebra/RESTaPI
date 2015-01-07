package com.pi4j.connector;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pi4j.connector.Pi4jSystemInfoConnector.Pi4jSystemInfoConnectorFactory;

public interface Pi4jNetworkInfoConnector  {

    public String getHostname() throws IOException, InterruptedException;
    public String getFQDN() throws IOException, InterruptedException;
    public String[] getIPAddresses() throws IOException, InterruptedException;
    public String getIPAddress() throws IOException, InterruptedException;
    public String[] getFQDNs() throws IOException, InterruptedException;
    public String[] getNameservers() throws IOException, InterruptedException;
    
    /**
     * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
     * @version 0.1
     * @since January 04, 2014
     */
    public static class Pi4jNetworkInfoConnectorFactory
    {
    	private static final String PI4J_NETWORK_INFO_CONNECTOR_CLASS_PROPERTY = "networkInfoConnectorClass";

    	private static Logger logger = Logger.getLogger(Pi4jNetworkInfoConnectorFactory.class);

    	public static Pi4jNetworkInfoConnector getInstance()
    	{
    		Pi4jNetworkInfoConnector pi4jNetworkInfoConnector = null;
    		
    		
        	String connectorClass = ConnectorUtils.readConnectorClassProperty(PI4J_NETWORK_INFO_CONNECTOR_CLASS_PROPERTY);
            if (connectorClass == null) {
            	pi4jNetworkInfoConnector = new LocalPi4jNetworkInfoConnector();
            } else {
                try {

                	String info = "Starting alternate Connector Class=" + connectorClass + " ...";
                    logger.info(info);
                	
                	pi4jNetworkInfoConnector = (Pi4jNetworkInfoConnector) Class.forName(connectorClass).newInstance();

                	info = "Started";
                    logger.info(info);
                	

                } catch (Throwable e) {
                    logger.error("", e);
                    // Re-throw
                    throw new Pi4jConnectorException("", e);
                }
            }
    		
			return pi4jNetworkInfoConnector;
    	}
    }


}
