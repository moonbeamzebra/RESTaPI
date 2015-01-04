package com.pi4j.connector;

import com.pi4j.wiringpi.GpioInterruptListener;


public class LocalWiringpiConnector implements WiringpiConnector {

    // com.pi4j.wiringpi.Gpio
    public int wiringPiSetup()
    {
    	return com.pi4j.wiringpi.Gpio.wiringPiSetup();
    }
    
    public int wiringPiSetupSys()
    {
    	return com.pi4j.wiringpi.Gpio.wiringPiSetupSys();
    }
    
    public int wiringPiSetupGpio()
    {
    	return com.pi4j.wiringpi.Gpio.wiringPiSetupGpio();
    }
    
    public void pinMode(int pin, int mode)
    {
    	com.pi4j.wiringpi.Gpio.pinMode( pin, mode);
    }
    
    public void pullUpDnControl(int pin, int pud)
    {
    	com.pi4j.wiringpi.Gpio.pullUpDnControl( pin, pud);
    }
    
    public void digitalWrite(int pin, int value)
    {
    	com.pi4j.wiringpi.Gpio.digitalWrite( pin, value);
    }
    
    public void digitalWrite(int pin, boolean value)
    {
    	com.pi4j.wiringpi.Gpio.digitalWrite( pin, value);
    }
    
    public void pwmWrite(int pin, int value)
    {
    	com.pi4j.wiringpi.Gpio.pwmWrite( pin, value);
    }
    
    public int digitalRead(int pin)
    {
    	return com.pi4j.wiringpi.Gpio.digitalRead(pin);
    }
    
    public void delay(long howLong)
    {
    	com.pi4j.wiringpi.Gpio.delay(howLong);
    }
    
    public long millis()
    {
    	return com.pi4j.wiringpi.Gpio.millis();
    }
    
    public void delayMicroseconds(long howLong)
    {
    	com.pi4j.wiringpi.Gpio.delayMicroseconds(howLong);
    }
    
    public int piHiPri(int priority)
    {
    	return com.pi4j.wiringpi.Gpio.piHiPri(priority);
    }

    public int waitForInterrupt(int pin, int timeout)
    {
    	return com.pi4j.wiringpi.Gpio.waitForInterrupt(pin, timeout);
    }
    
    public int piBoardRev()
    {
    	return com.pi4j.wiringpi.Gpio.piBoardRev();
    }
    
    public int wpiPinToGpio(int wpiPin)
    {
    	return com.pi4j.wiringpi.Gpio.wpiPinToGpio(wpiPin);
    }
    
    // com.pi4j.wiringpi.GpioUtil
    public void export(int pin, int direction) throws RuntimeException
    {
    	com.pi4j.wiringpi.GpioUtil.export(pin, direction);
    }
    
	public void unexport(int pin) throws RuntimeException
    {
    	com.pi4j.wiringpi.GpioUtil.unexport(pin);
    }
	
	public boolean isExported(int pin) throws RuntimeException
    {
    	return com.pi4j.wiringpi.GpioUtil.isExported(pin);
    }
	
	public boolean setEdgeDetection(int pin, int edge) throws RuntimeException
    {
    	return com.pi4j.wiringpi.GpioUtil.setEdgeDetection(pin, edge);
    }
	
	public int getEdgeDetection(int pin) throws RuntimeException
    {
    	return com.pi4j.wiringpi.GpioUtil.getEdgeDetection(pin);
    }
	
	public boolean setDirection(int pin, int direction) throws RuntimeException
    {
    	return com.pi4j.wiringpi.GpioUtil.setDirection(pin, direction);
    }
	
	public int getDirection(int pin) throws RuntimeException
    {
    	return com.pi4j.wiringpi.GpioUtil.getDirection(pin);
    }

	// New in pi4j-1.0-SNAPSHOT 
	public int isPinSupported(int pin) throws RuntimeException
	{
		return com.pi4j.wiringpi.GpioUtil.isPinSupported(pin);
	}
	
	// GpioInterrupt
	public int enablePinStateChangeCallback(int pin) throws RuntimeException
	{
		return com.pi4j.wiringpi.GpioInterrupt.enablePinStateChangeCallback(pin);
		//return com.pi4j.wiringpi.GpioInterruptNonNative.enablePinStateChangeCallback(pin);
	}
	
	public int disablePinStateChangeCallback(int pin) throws RuntimeException
	{
		return com.pi4j.wiringpi.GpioInterrupt.disablePinStateChangeCallback(pin);
		//return com.pi4j.wiringpi.GpioInterruptNonNative.disablePinStateChangeCallback(pin);
	}

	@Override
	public void addListener(GpioInterruptListener listener) {
		com.pi4j.wiringpi.GpioInterrupt.addListener(listener);
	}

	@Override
	public void removeListener(GpioInterruptListener listener) {
		com.pi4j.wiringpi.GpioInterrupt.removeListener(listener);
		
	}

	@Override
	public boolean hasListener(GpioInterruptListener listener) {
		return com.pi4j.wiringpi.GpioInterrupt.hasListener(listener);
	}

	@Override
	public void shutdown() {
		return;
	}

	@Override
	public int softPwmCreate(int pin, int initialValue, int pwmRange) {
		return com.pi4j.wiringpi.SoftPwm.softPwmCreate(pin, initialValue, pwmRange);
	}

	@Override
	public void softPwmWrite(int pin, int value) {
		com.pi4j.wiringpi.SoftPwm.softPwmWrite(pin, value);
	}
}
