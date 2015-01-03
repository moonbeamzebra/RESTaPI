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
import ca.magenta.pi.remote.common.WiringPiReturnFloatValue;
import ca.magenta.pi.remote.common.WiringPiReturnLongValue;
import ca.magenta.pi.remote.common.WiringPiReturnStatus;
import ca.magenta.pi.remote.common.WiringPiReturnStringArrayValue;
import ca.magenta.pi.remote.common.WiringPiReturnStringValue;

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

@Path("/pi4jSystemInfo")
public class SystemInfoResources {

	private static Logger logger = Logger.getLogger(SystemInfoResources.class);

	@GET
	@Path("/cpu/info")
	@Produces(MediaType.APPLICATION_JSON)
	public String cpuInfo(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringArrayValue wiringPiReturnStringArrayValue = new WiringPiReturnStringArrayValue();

		wiringPiReturnStringArrayValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getCpuInfo()");
		try {
			String[] value = com.pi4j.system.SystemInfoLowLevel.getCpuInfo();
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnStringArrayValue.getFunction());
			wiringPiReturnStringArrayValue.setValue(value);
			wiringPiReturnStringArrayValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnStringArrayValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnStringArrayValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/properties/{property}")
	@Produces(MediaType.APPLICATION_JSON)
	public String properties(@PathParam("property") String property, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getProperty(" + property + ")");
		try {
			String value = com.pi4j.system.SystemInfoLowLevel.getProperty(property);
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnStringValue.getFunction());

			wiringPiReturnStringValue.setValue(value);
			wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnStringValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnStringValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/os/{property}")
	@Produces(MediaType.APPLICATION_JSON)
	public String osProperty(@PathParam("property") String property, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();
		
		String lowerProperty = property.toLowerCase();
		if (	lowerProperty.equals("name") ||
				lowerProperty.equals("version") ||
				lowerProperty.equals("architecture") )
		{
			
			if (lowerProperty.equals("architecture"))
				lowerProperty = "arch";

			lowerProperty = "os." + lowerProperty;

			wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getProperty(" + lowerProperty + ")");
			try {
				String value = com.pi4j.system.SystemInfoLowLevel.getProperty(lowerProperty);
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnStringValue.getFunction());

				wiringPiReturnStringValue.setValue(value);
				wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			} catch (Throwable e) {
				wiringPiReturnStringValue.setRuntimeException(e);
				logger.error("", e);
			}
		}
		else
		{
			wiringPiReturnStringValue.setFail("Bad parameter: OS properties are name, version or architecture");
		}

		return wiringPiReturnStringValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/java/{property}")
	@Produces(MediaType.APPLICATION_JSON)
	public String javaProperty(@PathParam("property") String property, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		try {
			String lowerProperty = property.toLowerCase();
			if (lowerProperty.equals("runtime")) {
				wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getJavaRuntime()");
				String value = com.pi4j.system.SystemInfoLowLevel.getJavaRuntime();
				if (logger.isTraceEnabled())
					logger.trace("Called: " + wiringPiReturnStringValue.getFunction());

				wiringPiReturnStringValue.setValue(value);
				wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);

			} else if (lowerProperty.equals("vendor") || lowerProperty.equals("vendor.url") || lowerProperty.equals("version")
					|| lowerProperty.equals("vm.name")) {

				lowerProperty = "java." + lowerProperty;

				wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getProperty(" + lowerProperty + ")");
				String value = com.pi4j.system.SystemInfoLowLevel.getProperty(lowerProperty);
				if (logger.isTraceEnabled())
					logger.trace("Called: " + wiringPiReturnStringValue.getFunction());

				wiringPiReturnStringValue.setValue(value);
				wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			} else {
				wiringPiReturnStringValue.setFail("Bad parameter: Java properties are runtime, vendor, vendor.url, version or vm.name");
			}
		} catch (Throwable e) {
			wiringPiReturnStringValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnStringValue.returnJSonAsString(pretty);
	}
	
	@GET
	@Path("/firmware/{property}")
	@Produces(MediaType.APPLICATION_JSON)
	public String firmwareProperty(@PathParam("property") String property, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		String lowerProperty = property.toLowerCase();
		try {
			if (lowerProperty.equals("build")) {
				wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getOsFirmwareBuild()");
				String value = com.pi4j.system.SystemInfoLowLevel.getOsFirmwareBuild();
				if (logger.isTraceEnabled())
					logger.trace("Called: " + wiringPiReturnStringValue.getFunction());

				wiringPiReturnStringValue.setValue(value);
				wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			} else if (lowerProperty.equals("date")) {
				wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getOsFirmwareDate()");
				String value = com.pi4j.system.SystemInfoLowLevel.getOsFirmwareDate();
				if (logger.isTraceEnabled())
					logger.trace("Called: " + wiringPiReturnStringValue.getFunction());

				wiringPiReturnStringValue.setValue(value);
				wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			} else {
				wiringPiReturnStringValue.setFail("Bad parameter: Firmware properties are build or date");
			}
		} catch (Throwable e) {
			wiringPiReturnStringValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnStringValue.returnJSonAsString(pretty);
	}

//	@GET
//	@Path("/os/firmware/build")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String osFirmwareBuild(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {
//
//		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();
//
//		wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getOsFirmwareBuild()");
//		try {
//			String value = com.pi4j.system.SystemInfoLowLevel.getOsFirmwareBuild();
//			if (logger.isTraceEnabled())
//				logger.trace("Called: " +  wiringPiReturnStringValue.getFunction());
//
//			wiringPiReturnStringValue.setValue(value);
//			wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
//		} catch (Throwable e) {
//			wiringPiReturnStringValue.setRuntimeException(e);
//			logger.error("", e);
//		}
//
//		return wiringPiReturnStringValue.returnJSonAsString(pretty);
//	}
//
//	@GET
//	@Path("/os/firmware/date")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String osFirmwareDate(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {
//
//		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();
//
//		wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getOsFirmwareDate()");
//		try {
//			String value = com.pi4j.system.SystemInfoLowLevel.getOsFirmwareDate();
//			if (logger.isTraceEnabled())
//				logger.trace("Called: " +  wiringPiReturnStringValue.getFunction());
//
//			wiringPiReturnStringValue.setValue(value);
//			wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
//		} catch (Throwable e) {
//			wiringPiReturnStringValue.setRuntimeException(e);
//			logger.error("", e);
//		}
//
//		return wiringPiReturnStringValue.returnJSonAsString(pretty);
//	}

//	@GET
//	@Path("/javaRuntime")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String javaRuntime(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {
//
//		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();
//
//		wiringPiReturnStringValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getJavaRuntime()");
//		try {
//			String value = com.pi4j.system.SystemInfoLowLevel.getJavaRuntime();
//			if (logger.isTraceEnabled())
//				logger.trace("Called: " +  wiringPiReturnStringValue.getFunction());
//
//			wiringPiReturnStringValue.setValue(value);
//			wiringPiReturnStringValue.setOk(WiringPiReturnStatus.DETAIL_OK);
//		} catch (Throwable e) {
//			wiringPiReturnStringValue.setRuntimeException(e);
//			logger.error("", e);
//		}
//
//		return wiringPiReturnStringValue.returnJSonAsString(pretty);
//	}

	@GET
	@Path("/isHardFloatAbi")
	@Produces(MediaType.APPLICATION_JSON)
	public String isHardFloatAbi(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnBooleanValue wiringPiReturnBooleanValue = new WiringPiReturnBooleanValue();

		wiringPiReturnBooleanValue.setFunction("com.pi4j.system.SystemInfoLowLevel.isHardFloatAbi()");
		try {
			boolean value = com.pi4j.system.SystemInfoLowLevel.isHardFloatAbi();
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnBooleanValue.getFunction());

			wiringPiReturnBooleanValue.setValue(value);
			wiringPiReturnBooleanValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnBooleanValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnBooleanValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/memory/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public String memory(@PathParam("type") String type, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnLongValue wiringPiReturnLongValue = new WiringPiReturnLongValue();

		
		try {
			long value = -1;
			if (type.equals("total"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryTotal()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryTotal();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			else if (type.equals("used"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryUsed()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryUsed();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			else if (type.equals("free"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryFree()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryFree();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			else if (type.equals("shared"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryShared()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryShared();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			else if (type.equals("buffers"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryBuffers()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryBuffers();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			else if (type.equals("cached"))
			{
				wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getMemoryCached()");
				value = com.pi4j.system.SystemInfoLowLevel.getMemoryCached();
				if (logger.isTraceEnabled())
					logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());
			}
			if (value != -1)
			{
				wiringPiReturnLongValue.setValue(value);
				wiringPiReturnLongValue.setOk(WiringPiReturnStatus.DETAIL_OK);
			}
			else
			{
				wiringPiReturnLongValue.setFail("Bad parameter: Memory types are total, used, free, shared, buffers or cached");
			}
		} catch (Throwable e) {
			wiringPiReturnLongValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnLongValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/cpu/temperature")
	@Produces(MediaType.APPLICATION_JSON)
	public String cpuTemperature(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnFloatValue wiringPiReturnFloatValue = new WiringPiReturnFloatValue();

		wiringPiReturnFloatValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getCpuTemperature()");
		try {
			float value = com.pi4j.system.SystemInfoLowLevel.getCpuTemperature();
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnFloatValue.getFunction());
			wiringPiReturnFloatValue.setValue(value);
			wiringPiReturnFloatValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnFloatValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnFloatValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/voltage/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String voltage(@PathParam("id") String id, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnFloatValue wiringPiReturnFloatValue = new WiringPiReturnFloatValue();

		wiringPiReturnFloatValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getVoltage(" + id + ")");
		try {
			float value = com.pi4j.system.SystemInfoLowLevel.getVoltage(id);
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnFloatValue.getFunction());

			wiringPiReturnFloatValue.setValue(value);
			wiringPiReturnFloatValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnFloatValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnFloatValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/codecEnabled/{codec}")
	@Produces(MediaType.APPLICATION_JSON)
	public String codecEnabled(@PathParam("codec") String codec, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnBooleanValue wiringPiReturnBooleanValue = new WiringPiReturnBooleanValue();

		wiringPiReturnBooleanValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getCodecEnabled(" + codec + ")");
		try {
			boolean value = com.pi4j.system.SystemInfoLowLevel.getCodecEnabled(codec);
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnBooleanValue.getFunction());

			wiringPiReturnBooleanValue.setValue(value);
			wiringPiReturnBooleanValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnBooleanValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnBooleanValue.returnJSonAsString(pretty);
	}

	@GET
	@Path("/clockFrequency/{target}")
	@Produces(MediaType.APPLICATION_JSON)
	public String clockFrequency(@PathParam("target") String target, @DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnLongValue wiringPiReturnLongValue = new WiringPiReturnLongValue();

		wiringPiReturnLongValue.setFunction("com.pi4j.system.SystemInfoLowLevel.getClockFrequency(" + target + ")");
		try {
			long value = com.pi4j.system.SystemInfoLowLevel.getClockFrequency(target);
			if (logger.isTraceEnabled())
				logger.trace("Called: " +  wiringPiReturnLongValue.getFunction());

			wiringPiReturnLongValue.setValue(value);
			wiringPiReturnLongValue.setOk(WiringPiReturnStatus.DETAIL_OK);
		} catch (Throwable e) {
			wiringPiReturnLongValue.setRuntimeException(e);
			logger.error("", e);
		}

		return wiringPiReturnLongValue.returnJSonAsString(pretty);
	}

}
