package com.pi4j.connector;

import java.io.IOException;
import java.text.ParseException;

public interface SystemInfoConnectorInterface  {

    public String[] getCpuInfo()  throws IOException, InterruptedException;
    public String getProperty(String property)  throws IOException, InterruptedException;
    public String getOsFirmwareBuild() throws IOException, InterruptedException;
    public String getOsFirmwareDate() throws IOException, InterruptedException, ParseException;
    public String getJavaRuntime();
    public boolean isHardFloatAbi();
    public long getMemoryTotal() throws IOException, InterruptedException;
    public long getMemoryUsed() throws IOException, InterruptedException;
    public long getMemoryFree() throws IOException, InterruptedException;
    public long getMemoryShared() throws IOException, InterruptedException;
    public long getMemoryBuffers() throws IOException, InterruptedException;
    public long getMemoryCached() throws IOException, InterruptedException;
    public float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException;
    public float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException;
    public boolean getCodecEnabled(String codec) throws IOException, InterruptedException;
    public long getClockFrequency(String target) throws IOException, InterruptedException;
}
