package ca.magenta.pi.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.pi4j.wiringpi.GpioInterrupt;

import ca.magenta.utils.TCPServer;

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
public class GpioInterruptHandler extends TCPServer {

	private static Logger logger = Logger.getLogger(GpioInterruptHandler.class);
	private static int MAX_NUMBER_OF_CLIENT = 3;

	public GpioInterruptHandler(RemoteRaspberryPiContextListener myServlet) {
		super(myServlet);

	}

	public void run(Socket data) {
		try {

			InetAddress clientAddress = data.getInetAddress();
			int port = data.getPort();
			logger.trace("Connected to client: " + clientAddress.getHostAddress() + ":" + port);

			PrintWriter out = new PrintWriter(data.getOutputStream(), true);
			GpioInterrupt.addOutToClientPrintWriter( clientAddress.getHostAddress() + ":" + port, out);

			BufferedReader in = new BufferedReader(new InputStreamReader(data.getInputStream()));

			String inputLine;

			if (getClientCount() <= MAX_NUMBER_OF_CLIENT) {
				out.println("Type BYE to quit.");
				while ((inputLine = in.readLine()) != null) {
					logger.debug("Client: " + inputLine);

					if (inputLine.equals("BYE")) {

						break;
					}

				}

			} else {
				String s = "Only " + MAX_NUMBER_OF_CLIENT + " connection allowed .... DISCONNECT";
				out.println(s);
				logger.trace(s);
			}

			setClientCount(getClientCount() - 1);
			logger.trace("Client " + clientAddress.getHostAddress() + ":" + port + " has DISCONNECT");
			GpioInterrupt.removeOutToClientPrintWriter(clientAddress.getHostAddress() + ":" + port, out);

			out.close();
			in.close();
			data.close();

			// Process the data socket here.
		} catch (Exception e) {
		}
	}

}
