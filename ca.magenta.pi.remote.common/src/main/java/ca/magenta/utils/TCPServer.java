package ca.magenta.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
 * @since 2011-11-27
 */
public class TCPServer implements Cloneable, Runnable {
	
	public static int getClientCount() {
		return clientCount;
	}

	public static void setClientCount(int clientCount) {
		TCPServer.clientCount = clientCount;
	}

	private static int clientCount = 0;
	
	public TCPServer(Object myServlet) {
		super();
		this.myServlet = myServlet;
	}

	private Thread runner = null;
	ServerSocket server = null;
	Socket data = null;
	volatile boolean shouldStop = false;
	
	protected Object myServlet = null;

	public synchronized void startServer(int port) throws IOException {
		if (getRunner() == null) {
			server = new ServerSocket(port);
			setRunner(new Thread(this));
			getRunner().start();
		}
	}

	public synchronized void stopServer() {
		if (server != null) {
			shouldStop = true;
			getRunner().interrupt();
			setRunner(null);
			try {
				server.close();
			} catch (IOException ioe) {
			}
			server = null;
		}
	}

	public void run() {
		if (server != null) {
			while (!shouldStop) {
				try {
					Socket datasocket = server.accept();
					setClientCount(getClientCount()+1);
					TCPServer newSocket = (TCPServer) clone();
					newSocket.server = null;
					newSocket.data = datasocket;
					newSocket.setRunner(new Thread(newSocket));
					newSocket.getRunner().start();
				} catch (Exception e) {
				}
			}
		} else {
			run(data);
		}
	}

	public void run(Socket data) {
	}

	public Thread getRunner() {
		return runner;
	}

	public void setRunner(Thread runner) {
		this.runner = runner;
	}
}