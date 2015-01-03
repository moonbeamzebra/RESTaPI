package com.pi4j.connector;

import java.io.IOException;

import com.pi4j.system.NetworkInfoLowLevel;

public class LocalNetworkInfoConnector implements NetworkInfoConnectorInterface {

	@Override
	public String getHostname() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getHostname();
	}

	@Override
	public String getFQDN() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getFQDN();
	}

	@Override
	public String[] getIPAddresses() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getIPAddresses();
	}

	@Override
	public String getIPAddress() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getIPAddress();
	}

	@Override
	public String[] getFQDNs() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getFQDNs();
	}

	@Override
	public String[] getNameservers() throws IOException, InterruptedException {
		return NetworkInfoLowLevel.getNameservers();
	}

}
