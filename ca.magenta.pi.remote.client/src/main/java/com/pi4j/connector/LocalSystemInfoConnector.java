package com.pi4j.connector;

import java.io.IOException;
import java.text.ParseException;

import com.pi4j.system.SystemInfoLowLevel;

//No change required for pi4j-1.0-SNAPSHOT - jplaberge
public class LocalSystemInfoConnector implements SystemInfoConnectorInterface {

	@Override
	public String[] getCpuInfo() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getCpuInfo();
	}
	
	@Override
	public String getProperty(String property) throws IOException, InterruptedException {
		return SystemInfoLowLevel.getProperty(property);
	}

	@Override
	public String getOsFirmwareBuild() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getOsFirmwareBuild();
	}

	@Override
	public String getOsFirmwareDate() throws IOException, InterruptedException, ParseException {
		return SystemInfoLowLevel.getOsFirmwareDate();
	}

	@Override
	public String getJavaRuntime() {
		return SystemInfoLowLevel.getJavaRuntime();
	}

	@Override
	public boolean isHardFloatAbi() {
		return SystemInfoLowLevel.isHardFloatAbi();
	}

	@Override
	public long getMemoryTotal() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryTotal();
	}

	@Override
	public long getMemoryUsed() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryUsed();
	}

	@Override
	public long getMemoryFree() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryFree();
	}

	@Override
	public long getMemoryShared() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryShared();
	}

	@Override
	public long getMemoryBuffers() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryBuffers();
	}

	@Override
	public long getMemoryCached() throws IOException, InterruptedException {
		return SystemInfoLowLevel.getMemoryCached();
	}

	@Override
	public float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException {
		return SystemInfoLowLevel.getCpuTemperature();
	}

	@Override
	public float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException {
		return SystemInfoLowLevel.getVoltage(id);
	}

	@Override
	public boolean getCodecEnabled(String codec) throws IOException, InterruptedException {
		return SystemInfoLowLevel.getCodecEnabled(codec);
	}

	@Override
	public long getClockFrequency(String target) throws IOException, InterruptedException {
		return SystemInfoLowLevel.getClockFrequency(target);
	}


}
