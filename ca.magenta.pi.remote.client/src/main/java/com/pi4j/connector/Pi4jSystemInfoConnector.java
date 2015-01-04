package com.pi4j.connector;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

public interface Pi4jSystemInfoConnector  {

    public String[] getCpuInfo()  throws IOException, InterruptedException;
    public String getProperty(String property)  throws IOException, InterruptedException;
    public String getOsFirmwareBuild() throws IOException, InterruptedException;
    public String getOsFirmwareDate() throws IOException, InterruptedException, ParseException;
    public String getJavaRuntime();
    public boolean isHardFloatAbi();
    public long getMemoryTotal() throws IOException, InterruptedException;
    public long getMemoryUsed() throws IOException, InterruptedException;
    public long getMemoryFree() throws IOException, InterruptedException;
    public long getMemoryShared() throws IOException, InterruptedException;
    public long getMemoryBuffers() throws IOException, InterruptedException;
    public long getMemoryCached() throws IOException, InterruptedException;
    public float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException;
    public float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException;
    public boolean getCodecEnabled(String codec) throws IOException, InterruptedException;
    public long getClockFrequency(String target) throws IOException, InterruptedException;
    
    /**
     * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
     * @version 0.1
     * @since January 04, 2014
     */
    public static class Pi4jSystemInfoConnectorFactory
    {
    	private static final String PI4J_SYSTEM_INFO_CONNECTOR_CLASS_PROPERTY = "systemInfoConnectorClass";

    	private static Logger logger = Logger.getLogger(Pi4jSystemInfoConnectorFactory.class);

    	public static Pi4jSystemInfoConnector getInstance()
    	{
    		Pi4jSystemInfoConnector pi4jSystemInfoConnector = null;
    		
    		
        	String connectorClass = ConnectorUtils.readConnectorClassProperty(PI4J_SYSTEM_INFO_CONNECTOR_CLASS_PROPERTY);
            if (connectorClass == null) {
                pi4jSystemInfoConnector = new LocalPi4jSystemInfoConnector();
            } else {
                try {
                    pi4jSystemInfoConnector = (Pi4jSystemInfoConnector) Class.forName(connectorClass).newInstance();
                } catch (Throwable e) {
                    logger.error("", e);
                    // Re-throw
                    throw new Pi4jConnectorException("", e);
                }
            }
    		
			return pi4jSystemInfoConnector;
    	}
    }

}
