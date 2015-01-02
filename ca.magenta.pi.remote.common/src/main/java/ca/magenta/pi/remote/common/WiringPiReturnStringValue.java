package ca.magenta.pi.remote.common;



import org.apache.log4j.Logger;

import com.google.gson.Gson;

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
public class WiringPiReturnStringValue extends WiringPiReturnStatus {
	
	private static Logger logger = Logger.getLogger(WiringPiReturnStringValue.class);

	public WiringPiReturnStringValue() {
		super();
	}
	
	public WiringPiReturnStringValue(WiringPiReturnStatus wiringPiReturnStatus) {
		super(wiringPiReturnStatus);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private String value = null;
	
	@Override
	protected String returnRuntimeExceptionJSonStr(Throwable e)
	{
		String r_string = e.getMessage();
		WiringPiReturnStringValue wiringPiReturnStringValue = new WiringPiReturnStringValue();
		wiringPiReturnStringValue.setRuntimeException(e);
		try {
			r_string = (new Gson()).toJson(wiringPiReturnStringValue); 
		} catch (Throwable e1) {
			logger.error("", e1);
		}
		 return r_string;
	}
	
	public static WiringPiReturnStringValue httpGetResource(String url) throws RemotePiException {
		
		WiringPiReturnStringValue wiringPiReturnStringValue = null;

		try {
		    wiringPiReturnStringValue = (new Gson()).fromJson(httpGetResourceStr(url), WiringPiReturnStringValue.class);
			if (wiringPiReturnStringValue.getRetCode() == WiringPiReturnStatus.RUNTIME_EXCEPTION) {
				RemotePiException remotePiException = new RemotePiException(wiringPiReturnStringValue.getDetail());
				throw remotePiException;
			}
		} catch (Throwable e) {

		    RemotePiException remotePiException = new RemotePiException(RemotePiException.MESSAGE);
		    remotePiException.initCause(e);
			logger.error("", remotePiException);
			throw remotePiException;
		}

		return wiringPiReturnStringValue;
	}

}
