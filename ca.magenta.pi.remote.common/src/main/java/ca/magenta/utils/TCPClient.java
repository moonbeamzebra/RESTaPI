package ca.magenta.utils;

import java.net.*;
import java.io.*;

import org.apache.log4j.Logger;

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
 *  Use many examples from the internet
 * @version 0.1
 * @since 2014-01-09
 */
public class TCPClient implements Runnable {
	
	private static Logger logger = Logger.getLogger(TCPClient.class);
	
	public PrintWriter getOutToServer() {
		return outToServer;
	}

	
	public TCPClient() {
		super();
	}

	Thread runner = null;
	Socket client = null;
	PrintWriter outToServer = null;
	BufferedReader inFromServer = null;
	protected volatile boolean shouldStop = false;
	

	public synchronized void startClient(String hostname, int port) throws IOException {
		if (runner == null) {
			client = new Socket(hostname, port);
			logger.info("TCPClient started");;
			outToServer = new PrintWriter(client.getOutputStream(), true);
			inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			runner = new Thread(this);
			runner.start();
		}
	}

	public synchronized void stopClient() {
		if (client != null) {
			shouldStop = true;
			runner.interrupt();
			runner = null;
			try {
				client.close();
			} catch (IOException ioe) {
			}
			client = null;
			logger.info("TCPClient stopped");;
		}
	}

	public void run() {
		run(inFromServer, outToServer);
	}

	public void run(BufferedReader in, PrintWriter out) {
	}
}