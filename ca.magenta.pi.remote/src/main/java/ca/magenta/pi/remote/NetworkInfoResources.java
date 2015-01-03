package ca.magenta.pi.remote;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;


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

@Path("/pi4jNetworkInfo")
public class NetworkInfoResources {

	private static Logger logger = Logger.getLogger(NetworkInfoResources.class);

	@GET
	@Path("/hostname")
	@Produces(MediaType.APPLICATION_JSON)
	public String hostname(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		wiringPiReturnStringValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getHostname()");
		try {
			String value = com.pi4j.system.NetworkInfoLowLevel.getHostname();
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
	@Path("/fqdn")
	@Produces(MediaType.APPLICATION_JSON)
	public String fqdn(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		wiringPiReturnStringValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getFQDN()");
		try {
			String value = com.pi4j.system.NetworkInfoLowLevel.getFQDN();
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
	@Path("/ipAddresses")
	@Produces(MediaType.APPLICATION_JSON)
	public String ipAddresses(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringArrayValue wiringPiReturnStringArrayValue = new WiringPiReturnStringArrayValue();

		wiringPiReturnStringArrayValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getIPAddresses()");
		try {
			String[] value = com.pi4j.system.NetworkInfoLowLevel.getIPAddresses();
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
	@Path("/ipAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String ipAddress(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();

		wiringPiReturnStringValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getIPAddress()");
		try {
			String value = com.pi4j.system.NetworkInfoLowLevel.getIPAddress();
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
	@Path("/fqdns")
	@Produces(MediaType.APPLICATION_JSON)
	public String fqdns(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringArrayValue wiringPiReturnStringArrayValue = new WiringPiReturnStringArrayValue();

		wiringPiReturnStringArrayValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getFQDNs()");
		try {
			String[] value = com.pi4j.system.NetworkInfoLowLevel.getFQDNs();
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
	@Path("/nameservers")
	@Produces(MediaType.APPLICATION_JSON)
	public String nameservers(@DefaultValue("true") @QueryParam("pretty") boolean pretty) {

		WiringPiReturnStringArrayValue wiringPiReturnStringArrayValue = new WiringPiReturnStringArrayValue();

		wiringPiReturnStringArrayValue.setFunction("com.pi4j.system.NetworkInfoLowLevel.getNameservers()");
		try {
			String[] value = com.pi4j.system.NetworkInfoLowLevel.getNameservers();
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


}
