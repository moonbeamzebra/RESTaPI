package com.pi4j.connector;

import org.apache.log4j.Logger;

import com.pi4j.wiringpi.GpioInterruptListener;

public interface WiringpiConnector  {
	
	// Next added by jplaberge - January 2014
	public void shutdown();
	
	// com.pi4j.wiringpi.Gpio
    public  int wiringPiSetup();
    public  int wiringPiSetupSys();
    public  int wiringPiSetupGpio();
    public  void pinMode(int pin, int mode);
    public  void pullUpDnControl(int pin, int pud);
    public  void digitalWrite(int pin, int value);
    public  void digitalWrite(int pin, boolean value);
    public  void pwmWrite(int pin, int value);
    public  int digitalRead(int pin);
    public  void delay(long howLong);
    public  long millis();
    public  void delayMicroseconds(long howLong);
    public  int piHiPri(int priority);
    public  int waitForInterrupt(int pin, int timeout);
    public  int piBoardRev();
    public  int wpiPinToGpio(int wpiPin);
    
    // com.pi4j.wiringpi.GpioUtil
    public void export(int pin, int direction) throws RuntimeException;
	public void unexport(int pin) throws RuntimeException;
	public boolean isExported(int pin) throws RuntimeException;
	public boolean setEdgeDetection(int pin, int edge) throws RuntimeException;
	public int getEdgeDetection(int pin) throws RuntimeException;
	public boolean setDirection(int pin, int direction) throws RuntimeException;
	public int getDirection(int pin) throws RuntimeException;
	// Next new in pi4j-1.0-SNAPSHOT 
	public int isPinSupported(int pin) throws RuntimeException;
	
	// com.pi4j.wiringpi.GpioInterrupt
	public int enablePinStateChangeCallback(int pin) throws RuntimeException;
	public int disablePinStateChangeCallback(int pin) throws RuntimeException;
	public void addListener(GpioInterruptListener listener);
	public void removeListener(GpioInterruptListener listener);
	boolean hasListener(GpioInterruptListener listener);
	
	// com.pi4j.wiringpi.SoftPwm
    public int softPwmCreate(int pin, int initialValue, int pwmRange);
    public void softPwmWrite(int pin, int value);
    
    /**
     * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
     * @version 0.1
     * @since January 02, 2014
     */
    public static class WiringpiConnectorFactory
    {
    	private static final String WIRINGPI_CONNECTOR_CLASS_PROPERTY = "wiringpiConnectorClass";

    	private static Logger logger = Logger.getLogger(WiringpiConnectorFactory.class);

    	public static WiringpiConnector getInstance()
    	{
    		WiringpiConnector wiringpiConnector = null;
    		
    		
        	String connectorClass = ConnectorUtils.readConnectorClassProperty(WIRINGPI_CONNECTOR_CLASS_PROPERTY);
            if (connectorClass == null) {
                wiringpiConnector = new LocalWiringpiConnector();
            } else {
                try {
                    wiringpiConnector = (WiringpiConnector) Class.forName(connectorClass).newInstance();
                } catch (Throwable e) {
                    logger.error("", e);
                    // Re-throw
                    throw new Pi4jConnectorException("", e);
                }
            }
    		
			return wiringpiConnector;
    	}
    }
}
