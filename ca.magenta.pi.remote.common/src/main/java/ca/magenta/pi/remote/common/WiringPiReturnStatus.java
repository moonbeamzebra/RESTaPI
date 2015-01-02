package ca.magenta.pi.remote.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class WiringPiReturnStatus {

	private static Logger logger = Logger.getLogger(WiringPiReturnStatus.class);

	public static final int VOID_FUNCTION_RET_CODE = 0;
	public static final String VOID_FUNCTION_DETAIL = "NOTE: This function returns (void): returns no status";

	public static final String DETAIL_OK = "OK";
	public static final String DETAIL_FAIL = "FAIL";

	public static final int RUNTIME_EXCEPTION = Integer.MIN_VALUE;

	protected boolean isOk;
	protected String function;
	protected int retCode;
	protected String detail;

	public String getFunction() {
		return function;
	}

	public void setFunction(String call) {
		this.function = call;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isOk() {
		return isOk;
	}

	private void setOkBool(boolean isOk) {
		this.isOk = isOk;
	}

	public void setOk() {
		setOkBool(true);
	}

	public void setOk(String detail) {
		setOkBool(true);
		this.detail = detail;
	}

	public void setFail(String detail) {
		setOkBool(false);
		this.detail = detail;
	}

	public void setRuntimeException(Throwable e) {
		setOkBool(false);
		retCode = RUNTIME_EXCEPTION;
		this.detail = e.getMessage();
	}

	public void reset() {
		this.function = "";
		this.retCode = VOID_FUNCTION_RET_CODE;
		this.detail = VOID_FUNCTION_DETAIL;
		this.setOk();
	}

	public WiringPiReturnStatus() {
		super();
		reset();
	}

	public WiringPiReturnStatus(WiringPiReturnStatus wiringPiReturnStatus) {

		this.function = wiringPiReturnStatus.function;
		this.retCode = wiringPiReturnStatus.retCode;
		this.detail = wiringPiReturnStatus.detail;
		this.isOk = wiringPiReturnStatus.isOk;
	}

	public static WiringPiReturnStatus httpGetResource(String url)
			throws RemotePiException {

		WiringPiReturnStatus wiringPiReturnStatus = null;


		try {
			String ret = httpGetResourceStr(url);
			wiringPiReturnStatus = (new Gson()).fromJson(ret,
					WiringPiReturnStatus.class);
			if (wiringPiReturnStatus.getRetCode() == WiringPiReturnStatus.RUNTIME_EXCEPTION) {
				RemotePiException remotePiException = new RemotePiException(
						wiringPiReturnStatus.getDetail());
				throw remotePiException;
			}
		} catch (Throwable e) {

			RemotePiException remotePiException = new RemotePiException(
					RemotePiException.MESSAGE);
			remotePiException.initCause(e);
			logger.error("", remotePiException);
			throw remotePiException;
		}

		return wiringPiReturnStatus;
	}
	
	// com.sun.jersey and org.apache.httpcomponents are too slow to run on a Raspberry PI
	// TODO Figure out why ?  See Jersey and Apache code below
	// Here use basic java.net.HttpURLConnection instead
	protected static String httpGetResourceStr(String url) throws IOException {

		String wiringPiReturnStatusStr = null;

		if (logger.isTraceEnabled())
			logger.trace("URL: " + url);

		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			// add request headers
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("accept", "application/json");

			if (con.getResponseCode() != 200) {
				RemotePiException remotePiException = new RemotePiException(
						"Failed : HTTP error code : " + con.getResponseCode());
				logger.error("", remotePiException);
				throw remotePiException;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer responseBuf = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseBuf.append(inputLine);
			}

			in.close();

			wiringPiReturnStatusStr = responseBuf.toString();

			if (logger.isTraceEnabled())
				logger.trace("RESPONSE: " + wiringPiReturnStatusStr);

		} catch (IOException e) {
			logger.error("URL:[" + url + "]", e);
			throw e;
		}

		return wiringPiReturnStatusStr;

	}

	// TODO Jersey library
	// protected static String httpGetResourceStrJersey(String url)
	// throws RemotePiException {
	//
	// Client client = Client.create();
	//
	// WebResource webResource = client.resource(url);
	//
	// ClientResponse response = webResource.accept("application/json").get(
	// ClientResponse.class);
	//
	// if (response.getStatus() != 200) {
	// RemotePiException remotePiException = new RemotePiException(
	// "Failed : HTTP error code : " + response.getStatus());
	// logger.error("", remotePiException);
	// throw remotePiException;
	// }
	//
	// String wiringPiReturnStatusStr = response.getEntity(String.class);
	//
	// logger.trace(wiringPiReturnStatusStr);
	//
	// return wiringPiReturnStatusStr;
	// }

	
	// TODO Apache library
	// protected static String httpGetResourceStrApache(String url) throws
	// IOException {
	//
	// String wiringPiReturnStatusStr = null;
	//
	// try {
	// logger.trace("Before create");
	// HttpClientBuilder a = HttpClientBuilder.create();
	// logger.trace("Before build");
	// CloseableHttpClient httpClient = a.build();
	// logger.trace("After build");
	//
	// // HttpClient httpClient = new DefaultHttpClient();
	// logger.trace("Before HttpGet:[" + url + "]");
	// HttpGet getRequest = new HttpGet(url);
	// logger.trace("Before addHeader");
	// getRequest.addHeader("accept", "application/json");
	//
	// HttpResponse response;
	// logger.trace("Before execute");
	// response = httpClient.execute(getRequest);
	// logger.trace("After execute");
	//
	// if (response.getStatusLine().getStatusCode() != 200) {
	// RemotePiException remotePiException = new RemotePiException(
	// "Failed : HTTP error code : "
	// + response.getStatusLine().getStatusCode());
	// logger.error("", remotePiException);
	// throw remotePiException;
	// }
	//
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// (response.getEntity().getContent())));
	// String inputLine;
	// StringBuffer responseBuf = new StringBuffer();
	// while ((inputLine = br.readLine()) != null) {
	// responseBuf.append(inputLine);
	// }
	//
	// logger.trace("Before shutdown");
	// httpClient.close();
	// // httpClient.getConnectionManager().shutdown();
	//
	// wiringPiReturnStatusStr = responseBuf.toString();
	//
	// if (logger.isTraceEnabled())
	// logger.trace(wiringPiReturnStatusStr);
	//
	// } catch (IOException e) {
	// logger.error("URL:[" + url + "]", e);
	// throw e;
	// }
	//
	// return wiringPiReturnStatusStr;
	//
	// }


	protected String returnRuntimeExceptionJSonStr(Throwable e) {
		String r_string = e.getMessage();
		WiringPiReturnStatus wiringPiReturnStatus = new WiringPiReturnStatus();
		wiringPiReturnStatus.setRuntimeException(e);
		try {
			r_string = (new Gson()).toJson(wiringPiReturnStatus);
		} catch (Throwable e1) {
			logger.error("", e1);
		}
		return r_string;
	}

	public String returnJSonAsString(boolean pretty) {
		String r_string = null;

		try {
			if (pretty) {
				r_string = (new GsonBuilder().setPrettyPrinting().create())
						.toJson(this);
			} else {
				r_string = (new Gson()).toJson(this);
			}

		} catch (Throwable e) {
			r_string = this.returnRuntimeExceptionJSonStr(e);
		}

		return r_string;
	}

}
