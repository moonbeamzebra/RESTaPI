package ca.magenta.pi.remote;



import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ca.magenta.pi.remote.common.WiringPiReturnBooleanValue;
import ca.magenta.pi.remote.common.WiringPiReturnIntValue;
import ca.magenta.pi.remote.common.WiringPiReturnLongValue;
import ca.magenta.pi.remote.common.WiringPiReturnStatus;

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

/**
 * <[>WiringPi GPIO Control</[>
 * 
 * <p>
 * Some of the functions in the WiringPi library are designed to mimic those in the Arduino Wiring
 * system. There are relatively easy to use and should present no problems for anyone used to the
 * Arduino system, or C programming in-general.
 * </p>
 * 
 * <p>
 * The main difference is that unlike the Arduino system, the main loop of the program is not
 * provided for you and you need to write it yourself. This is often desirable in a Linux system
 * anyway as it can give you access to command-line arguments and so on. See the examples page for
 * some simple examples and a Makefile to use.
 * </p>
 * 
 * <p>
 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
 * the following system libraries:
 * <ul>
 * <li>pi4j</li>
 * <li>wiringPi</li>
 * </ul>
 * <blockquote> This library depends on the wiringPi native system library.</br> (developed by
 * Gordon Henderson @ <a href="http://wiringpi.com/">http://wiringpi.com/</a>)
 * </blockquote>
 * </p>
 * 
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @see <a
 *      href="http://wiringpi.com/reference/">http://wiringpi.com/reference/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */

@Path("/wiringPiGpio")
public class WiringPiGpioResources {
	
	private static Logger logger = Logger.getLogger(WiringPiGpioResources.class);

	@GET
	@Path("/setup")
	@Produces(MediaType.APPLICATION_JSON)
	public String setup(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		doSetup(wiringPiReturnStatus);
		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	// Not implemented
	//public static native int wiringPiSetupSys();
	//public static native int wiringPiSetupGpio();
	//public static native int wiringPiSetupPhys();
	
	@GET
	@Path("/pins/{pin}/pinMode/{mode}")
	@Produces(MediaType.APPLICATION_JSON)
	public String pinMode(@PathParam("pin") int pin, @PathParam("mode") int mode, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.pinMode(" + pin + "," + mode + ")");
			try
			{
				com.pi4j.wiringpi.Gpio.pinMode(pin, mode);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}

	@GET
	@Path("/pins/{pin}/pullUpDnControl/{pud}")
	@Produces(MediaType.APPLICATION_JSON)
	public String pullUpDnControl(@PathParam("pin") int pin,	@PathParam("pud") int pud, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.pullUpDnControl(" + pin + "," + pud + ")");
			try
			{
				com.pi4j.wiringpi.Gpio.pullUpDnControl(pin, pud);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
	
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/digitalWrite/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String digitalWrite(@PathParam("pin") int pin,	@PathParam("value") int value,@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.digitalWrite(" + pin + "," + value + ")");
			try
			{
				com.pi4j.wiringpi.Gpio.digitalWrite(pin, value);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
    //public static void digitalWrite(int pin, boolean value);
	
	@GET
	@Path("/pins/{pin}/pwmWrite/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String pwmWrite(@PathParam("pin") int pin,	@PathParam("value") int value, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.pwmWrite(" + pin + "," + value + ")");
			try
			{
				com.pi4j.wiringpi.Gpio.pwmWrite(pin, value);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/digitalRead")
	@Produces(MediaType.APPLICATION_JSON)
	public String digitalRead(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();

		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.Gpio.digitalRead(" + pin + ")");
			try
			{
				int value = com.pi4j.wiringpi.Gpio.digitalRead(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);

				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}
	
    //public static native int analogRead(int pin);
    //public static native void analogWrite(int pin, int value);

	@GET
	@Path("/delay/{howLong}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delay(@PathParam("howLong") long howLong,@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.delay(" + howLong + ")");
			
			try
			{
				com.pi4j.wiringpi.Gpio.delay(howLong);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());

				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}
//////////	
//		try
//		{
//			throw  new RuntimeException("Ceci test pour status");
//		}
//		catch (Throwable e)
//		{
//			logger.error("", e);
//			wiringPiReturnStatus.setRuntimeException(e);
//		}
//////////
		
		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/millis")
	@Produces(MediaType.APPLICATION_JSON)
	public String millis(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnLongValue wiringPiReturnLongValue = new WiringPiReturnLongValue();

		WiringPiGpioResources.doSetup(wiringPiReturnLongValue);

		if (wiringPiReturnLongValue.isOk()) {

			wiringPiReturnLongValue.reset();
			wiringPiReturnLongValue.setFunction("com.pi4j.wiringpi.Gpio.millis()");
			try
			{
				long value = com.pi4j.wiringpi.Gpio.millis();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction() + "; value=" + value);

				wiringPiReturnLongValue.setValue(value);
				wiringPiReturnLongValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnLongValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnLongValue.returnJSonAsString(pretty);
	}
	
    //public static native long micros();
	
	@GET
	@Path("/delayMicroseconds/{howLong}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delayMicroseconds(@PathParam("howLong") long howLong, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.delayMicroseconds(" + howLong + ")");
			
			try
			{
				com.pi4j.wiringpi.Gpio.delayMicroseconds(howLong);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());

				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}

    //public static native int piHiPri(int priority);
	//public static native int waitForInterrupt(int pin, int timeout);  // Deprecated 
    //public static native int wiringPiISR(int pin, int edgeType, GpioInterruptCallback callback);
	
	@GET
	@Path("/piBoardRev")
	@Produces(MediaType.APPLICATION_JSON)
	public String wpiPinToGpio(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.Gpio.piBoardRev()");
			try
			{
				int value = com.pi4j.wiringpi.Gpio.piBoardRev();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
	
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}
		
		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/wpiPinToGpio")
	@Produces(MediaType.APPLICATION_JSON)
	public String wpiPinToGpio(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.Gpio.wpiPinToGpio(" + pin + ")");
			try
			{
				int value = com.pi4j.wiringpi.Gpio.wpiPinToGpio(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
	
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}
		
		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}
	
    //public static native int physPinToGpio(int physPin);
    //public static native void digitalWriteByte(int value);
    //public static native void pwmSetMode(int mode);
    //public static native void pwmSetRange(int range);
    //public static native void pwmSetClock(int divisor);
    //public static native void setPadDrive(int group, int value);
    //public static native int getAlt(int pin);
    //public static native void gpioClockSet(int pin, int frequency);

	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}
	
	private static boolean setupDone = false;
	
	public static void doSetup(WiringPiReturnStatus wiringPiReturnStatus) {

		wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.Gpio.wiringPiSetup()");

		try
		{
			if (!setupDone)
			{
	
				int retCode = com.pi4j.wiringpi.Gpio.wiringPiSetup();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction() + "; retCode=" + retCode);
		
				wiringPiReturnStatus.setRetCode(retCode);
				if (retCode == 0)
				{
					wiringPiReturnStatus.setOk(WiringPiReturnStatus.DETAIL_OK);
					setupDone = true;
				}
				else
				{
					wiringPiReturnStatus.setFail(WiringPiReturnStatus.DETAIL_FAIL);
				}
			}
			else
			{
				wiringPiReturnStatus.setRetCode(0);
				wiringPiReturnStatus.setOk("Setup already done");
			}
		}
		catch (Throwable e)
		{
			wiringPiReturnStatus.setRuntimeException(e);
			logger.error("", e);
		}
	}
	
	
	// UtilGpio
	///////////
	/**
	 * <p>This utility class is provided to export, unexport, and manipulate pin direction.</p>
	 * 
	 * <p>
	 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
	 * the following system libraries:
	 * <ul>
	 * <li>pi4j</li>
	 * <li>wiringPi</li>
	 * </ul>
	 * <blockquote> This library depends on the wiringPi native system library.</br> (developed by
	 * Gordon Henderson @ <a href="http://wiringpi.com/">http://wiringpi.com/</a>)
	 * </blockquote>
	 * </p>
	 * 
	 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
	 * @see <a
	 *      href="http://wiringpi.com/">http://wiringpi.com/</a>
	 * @author Robert Savage (<a
	 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
	 */

	@GET
	@Path("/pins/{pin}/export/{direction}")
	@Produces(MediaType.APPLICATION_JSON)
	public String export(@PathParam("pin") int pin, @PathParam("direction") int direction, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		
		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();

			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.GpioUtil.export(" + pin + "," + direction + ")");
			
			try
			{
				com.pi4j.wiringpi.GpioUtil.export(pin, direction);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/unexport")
	@Produces(MediaType.APPLICATION_JSON)
	public String unexport(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		
		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();

			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.GpioUtil.unexport(" + pin + ")");
			
			try
			{
				com.pi4j.wiringpi.GpioUtil.unexport(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/isExported")
	@Produces(MediaType.APPLICATION_JSON)
	public String isExported(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnBooleanValue wiringPiReturnBooleanValue = new WiringPiReturnBooleanValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnBooleanValue);

		if (wiringPiReturnBooleanValue.isOk()) {
			
			wiringPiReturnBooleanValue.reset();
			
			wiringPiReturnBooleanValue.setFunction("com.pi4j.wiringpi.GpioUtil.isExported(" + pin + ")");
			try
			{
				boolean value = com.pi4j.wiringpi.GpioUtil.isExported(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnBooleanValue.getFunction() + "; value=" + value);
		
				wiringPiReturnBooleanValue.setValue(value);
				wiringPiReturnBooleanValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnBooleanValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnBooleanValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/setEdgeDetection/{edge}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setEdgeDetection(@PathParam("pin") int pin, @PathParam("edge") int edge, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnBooleanValue wiringPiReturnBooleanValue = new WiringPiReturnBooleanValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnBooleanValue);

		if (wiringPiReturnBooleanValue.isOk()) {
			
			wiringPiReturnBooleanValue.reset();
			
			wiringPiReturnBooleanValue.setFunction("com.pi4j.wiringpi.GpioUtil.setEdgeDetection(" + pin + "," + edge + ")");
			try
			{
				boolean value = com.pi4j.wiringpi.GpioUtil.setEdgeDetection(pin, edge);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnBooleanValue.getFunction() + "; value=" + value);
		
		
				wiringPiReturnBooleanValue.setValue(value);
				wiringPiReturnBooleanValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnBooleanValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnBooleanValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/pins/{pin}/getEdgeDetection")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEdgeDetection(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.GpioUtil.getEdgeDetection(" + pin + ")");
			try
			{
				int value = com.pi4j.wiringpi.GpioUtil.getEdgeDetection(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
		
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
				
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}

	
	@GET
	@Path("/pins/{pin}/setDirection/{direction}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setDirection(@PathParam("pin") int pin, @PathParam("direction") int direction, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnBooleanValue wiringPiReturnBooleanValue = new WiringPiReturnBooleanValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnBooleanValue);

		if (wiringPiReturnBooleanValue.isOk()) {
			
			wiringPiReturnBooleanValue.reset();
			
			wiringPiReturnBooleanValue.setFunction("com.pi4j.wiringpi.GpioUtil.setDirection(" + pin + "," + direction + ")");
			try
			{
				boolean value = com.pi4j.wiringpi.GpioUtil.setDirection(pin, direction);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnBooleanValue.getFunction() + "; value=" + value);
		
				wiringPiReturnBooleanValue.setValue(value);
				wiringPiReturnBooleanValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnBooleanValue.setRuntimeException(e);
				logger.error("", e);
			}
		}
		
		//////////
		//RuntimeException runtimeException = new RuntimeException("Ceci test pour bool");
		//wiringPiReturnBooleanValue.setRuntimeException(runtimeException);
		//////////

		return wiringPiReturnBooleanValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/getDirection")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDirection(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();
		
		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.GpioUtil.getDirection(" + pin + ")");
			try
			{
				int value = com.pi4j.wiringpi.GpioUtil.getDirection(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
		
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
				
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}
	
	// New in pi4j-1.0-SNAPSHOT 
	@GET
	@Path("/pins/{pin}/isPinSupported")
	@Produces(MediaType.APPLICATION_JSON)
	public String isPinSupported(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();

		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.GpioUtil.isPinSupported(" + pin + ")");
			try
			{
				int value = com.pi4j.wiringpi.GpioUtil.isPinSupported(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}
	
	// GpioInterrupt
	/**
	 * <p>
	 * This class provides static methods to configure the native Pi4J library to listen to GPIO
	 * interrupts and invoke callbacks into this class. Additionally, this class provides a listener
	 * registration allowing Java consumers to subscribe to GPIO pin state changes.
	 * </p>
	 * 
	 * <p>
	 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
	 * the following system libraries:
	 * <ul>
	 * <li>pi4j</li>
	 * <li>wiringPi</li>
	 * </ul>
	 * <blockquote> This library depends on the wiringPi native system library.</br> (developed by
	 * Gordon Henderson @ <a href="http://wiringpi.com/">http://wiringpi.com/</a>)
	 * </blockquote>
	 * </p>
	 * 
	 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
	 * @author Robert Savage (<a
	 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
	 */
	
	@GET
	@Path("/pins/{pin}/enablePinStateChangeCallback")
	@Produces(MediaType.APPLICATION_JSON)
	public String enablePinStateChangeCallback(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		
		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
		
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.GpioInterrupt.enablePinStateChangeCallback(" + pin + ")");
			
			try
			{
				if (logger.isTraceEnabled())
					logger.trace("Before enablePinStateChangeCallback");
				int retCode = com.pi4j.wiringpi.GpioInterruptProxyToNonPublicFunctions.enablePinStateChangeCallback(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction() + "; retCode=" + retCode);
				
				wiringPiReturnStatus.setRetCode(retCode);
				if (retCode == 0)
				{
					wiringPiReturnStatus.setOk("GPIO pin already monitored (Success)");
					
				}
				else if (retCode == 1)
				{
					wiringPiReturnStatus.setOk("GPIO pin is now monitored");
				}
				else
				{
					wiringPiReturnStatus.setFail("Error");
					logger.error("Error doing enablePinStateChangeCallback");
				}
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/pins/{pin}/disablePinStateChangeCallback")
	@Produces(MediaType.APPLICATION_JSON)
	public String disablePinStateChangeCallback(@PathParam("pin") int pin, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {


		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();

		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();
		
			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.GpioInterrupt.disablePinStateChangeCallback(" + pin + ")");
			
			try
			{
				if (logger.isTraceEnabled())
					logger.trace("Before disablePinStateChangeCallback");
				int retCode = com.pi4j.wiringpi.GpioInterruptProxyToNonPublicFunctions.disablePinStateChangeCallback(pin);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction() + "; retCode=" + retCode);
				
				wiringPiReturnStatus.setRetCode(retCode);
				if (retCode == 0)
				{
					wiringPiReturnStatus.setOk("GPIO pin was not monitored (Success)");
					
				}
				else if (retCode == 1)
				{
					wiringPiReturnStatus.setOk("GPIO pin no longuer monitored");
				}
				else
				{
					wiringPiReturnStatus.setFail("Error");
					logger.error("Error doing disablePinStateChangeCallback");
				}
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}
	
	// SoftPwm
	/**
	 * <p>
	 * WiringPi includes a software-driven PWM handler capable of outputting a PWM signal on any of the
	 * Raspberry Pi's GPIO pins.
	 * </p>
	 * 
	 * <p>
	 * There are some limitations. To maintain a low CPU usage, the minimum pulse width is 100uS. That
	 * combined with the default suggested range of 100 gives a PWM frequency of 100Hz. You can lower
	 * the range to get a higher frequency, at the expense of resolution, or increase to get more
	 * resolution, but that will lower the frequency. If you change the pulse-width in the drive code,
	 * then be aware that at delays of less than 100uS wiringPi does it in a software loop, which means
	 * that CPU usage will rise dramatically, and controlling more than one pin will be almost
	 * impossible.
	 * </p>
	 * 
	 * <p>
	 * Also note that while the routines run themselves at a higher and real-time priority, Linux can
	 * still affect the accuracy of the generated signal.
	 * </p>
	 * 
	 * <p>
	 * However, within these limitations, control of a light/LED or a motor is very achievable.
	 * </p>
	 * 
	 * <p>
	 * <b> You must initialize wiringPi with one of wiringPiSetup() or wiringPiSetupGpio() functions
	 * beforehand. wiringPiSetupSys() is not fast enough, so you must run your programs with sudo. </b>
	 * </p>
	 * 
	 * <p>
	 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
	 * the following system libraries:
	 * <ul>
	 * <li>pi4j</li>
	 * <li>wiringPi</li>
	 * <li>pthread</li>
	 * </ul>
	 * <blockquote> This library depends on the wiringPi native system library.</br> (developed by
	 * Gordon Henderson @ <a href="http://wiringpi.com/">http://wiringpi.com/</a>)
	 * </blockquote>
	 * </p>
	 * 
	 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
	 * @see <a
	 *      href="http://wiringpi.com/reference/software-pwm-library/">http://wiringpi.com/reference/software-pwm-library/</a>
	 * @author Robert Savage (<a
	 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
	 */
	
	@GET
	@Path("/pins/{pin}/softPwmCreate/{initialValue}/{pwmRange}")
	@Produces(MediaType.APPLICATION_JSON)
	public String isPinSupported(@PathParam("pin") int pin, @PathParam("initialValue") int initialValue, @PathParam("pwmRange") int pwmRange, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnIntValue wiringPiReturnIntValue = new WiringPiReturnIntValue();

		WiringPiGpioResources.doSetup(wiringPiReturnIntValue);

		if (wiringPiReturnIntValue.isOk()) {
			
			wiringPiReturnIntValue.reset();
			
			wiringPiReturnIntValue.setFunction("com.pi4j.wiringpi.SoftPwm.softPwmCreate(" + pin + "," + initialValue + "," + pwmRange + ")");
			try
			{
				int value = com.pi4j.wiringpi.SoftPwm.softPwmCreate(pin, initialValue, pwmRange);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnIntValue.getFunction() + "; value=" + value);
				wiringPiReturnIntValue.setValue(value);
				wiringPiReturnIntValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			catch (Throwable e)
			{
				wiringPiReturnIntValue.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnIntValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/pins/{pin}/softPwmWrite/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String unexport(@PathParam("pin") int pin, @PathParam("value") int value, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		
		WiringPiGpioResources.doSetup(wiringPiReturnStatus);

		if (wiringPiReturnStatus.isOk()) {
			
			wiringPiReturnStatus.reset();

			wiringPiReturnStatus.setFunction("com.pi4j.wiringpi.SoftPwm.softPwmWrite(" + pin + "," + value + ")");
			
			try
			{
				com.pi4j.wiringpi.SoftPwm.softPwmWrite(pin, value);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStatus.getFunction());
				wiringPiReturnStatus.setOk();
			}
			catch (Throwable e)
			{
				wiringPiReturnStatus.setRuntimeException(e);
				logger.error("", e);
			}
		}

		return wiringPiReturnStatus.returnJSonAsString(pretty);
	}


}
