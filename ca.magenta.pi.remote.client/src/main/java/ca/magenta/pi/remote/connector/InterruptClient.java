package ca.magenta.pi.remote.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import ca.magenta.pi.remote.common.WiringPiInterruptHandlerMsg;
import ca.magenta.utils.TCPClient;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import com.pi4j.wiringpi.GpioInterruptNonNative;

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
public class InterruptClient extends TCPClient {

	private static Logger logger = Logger.getLogger(InterruptClient.class);

	public InterruptClient() throws IOException {
		super();

	}

	public void run(BufferedReader in, PrintWriter out) {
		try {

			String inputLine;

			logger.debug("InterruptClient is starting");

			while (!shouldStop && (inputLine = in.readLine()) != null) {
				logger.debug("From server side: " + inputLine);

				try {
					WiringPiInterruptHandlerMsg msg = (new Gson()).fromJson(inputLine, WiringPiInterruptHandlerMsg.class);

					GpioInterruptNonNative.pinStateChangeCallback(msg.getPin(), msg.getState());
				} catch (JsonParseException e) {
					if (inputLine.equals("BYE")) {

						break;
					}
				}
			}
			logger.debug("Out of while");

			out.close();
			in.close();
			// Process the data socket here.
		} catch (Exception e) {
		}
	}

}
