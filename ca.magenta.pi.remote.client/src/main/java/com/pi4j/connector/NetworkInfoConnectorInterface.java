package com.pi4j.connector;

import java.io.IOException;

public interface NetworkInfoConnectorInterface  {

    public String getHostname() throws IOException, InterruptedException;
    public String getFQDN() throws IOException, InterruptedException;
    public String[] getIPAddresses() throws IOException, InterruptedException;
    public String getIPAddress() throws IOException, InterruptedException;
    public String[] getFQDNs() throws IOException, InterruptedException;
    public String[] getNameservers() throws IOException, InterruptedException;

}
