package com.pi4j.system;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  SystemInfo.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2014 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

// Modified by jplaberge@magenta.ca : Separate hi-level code and low-level code
// Add SystemInfoConnectorInterface functionality
// January 2014

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.pi4j.connector.ConnectorUtils;
import com.pi4j.connector.LocalSystemInfoConnector;
import com.pi4j.connector.Pi4jConnectorException;
import com.pi4j.connector.SystemInfoConnectorInterface;


// OK for pi4j-1.0-SNAPSHOT - jplaberge
public class SystemInfo {
	
	// Add by jplaberge - January 2014
    private static Logger logger = Logger.getLogger(SystemInfo.class);
	private static SystemInfoConnectorInterface systemInfoConnector = null;
	
	static 
	{

        String connectorClass = ConnectorUtils.readConnectorClassProperty("systemInfoConnectorClass");
        if (connectorClass == null) {
        	systemInfoConnector = new LocalSystemInfoConnector();
        }
        else
        {
            try {
            	systemInfoConnector = (SystemInfoConnectorInterface) Class.forName(connectorClass).newInstance();
            } catch (Throwable e) {
                logger.error("", e);
                // Re-throw
                throw new Pi4jConnectorException("", e);
            }
        }
	}
    // End of modifs by jplaberge
    // ///////////////////////////

    // private constructor 
    private SystemInfo() {
        // forbid object construction 
    }
    
    public enum BoardType {
        UNKNOWN,
        ModelA_Rev1,
        ModelA_Plus_Rev1,
        ModelB_Rev1,
        ModelB_Rev2,
        ModelB_Plus_Rev1,
        Compute_Module_Rev1
    }  
    
    private static Map<String, String> cpuInfo;

    private static String getCpuInfo(String target) throws IOException, InterruptedException {
        // if the CPU data has not been previously acquired, then acquire it now
        if (cpuInfo == null) {
            cpuInfo = new HashMap<String, String>();
            // Next modified jplaberge
            String result[] = systemInfoConnector.getCpuInfo() ;
            if(result != null){
                for(String line : result) {
                    String parts[] = line.split(":", 2);
                    if (parts.length >= 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty()) {
                        cpuInfo.put(parts[0].trim().toLowerCase(), parts[1].trim());
                    }
                }
            }
        }

        if (cpuInfo.containsKey(target.toLowerCase())) {
            return cpuInfo.get(target.toLowerCase());
        }

        throw new RuntimeException("Invalid target: " + target);
    }

    public static String getProcessor() throws IOException, InterruptedException {
        return getCpuInfo("Processor");
    }
    
    public static String getModelName() throws IOException, InterruptedException {
        return getCpuInfo("model name");
    }

    @Deprecated
    public static String getBogoMIPS() throws IOException, InterruptedException {
        return "UNKNOWN";
    }

    public static String[] getCpuFeatures() throws IOException, InterruptedException {
        return getCpuInfo("Features").split(" ");
    }

    public static String getCpuImplementer() throws IOException, InterruptedException {
        return getCpuInfo("CPU implementer");
    }

    public static String getCpuArchitecture() throws IOException, InterruptedException {
        return getCpuInfo("CPU architecture");
    }

    public static String getCpuVariant() throws IOException, InterruptedException {
        return getCpuInfo("CPU variant");
    }

    public static String getCpuPart() throws IOException, InterruptedException {
        return getCpuInfo("CPU part");
    }

    public static String getCpuRevision() throws IOException, InterruptedException {
        return getCpuInfo("CPU revision");
    }

    public static String getHardware() throws IOException, InterruptedException {
        return getCpuInfo("Hardware");
    }

    public static String getRevision() throws IOException, InterruptedException {
        return getCpuInfo("Revision");
    }

    public static String getSerial() throws IOException, InterruptedException {
        return getCpuInfo("Serial");
    }

    public static String getOsName() throws IOException, InterruptedException {
    	// Next modified jplaberge
        return systemInfoConnector.getProperty("os.name");
    }

    public static String getOsVersion() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	return systemInfoConnector.getProperty("os.version");
    }

    public static String getOsArch() throws IOException, InterruptedException  {
    	// Next modified jplaberge
    	return systemInfoConnector.getProperty("os.arch");
    }
    
    public static String getOsFirmwareBuild() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
        return systemInfoConnector.getOsFirmwareBuild();
    }  
    
    public static String getOsFirmwareDate() throws IOException, InterruptedException, ParseException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getOsFirmwareDate();
    }  
    
    public static String getJavaVendor() throws IOException, InterruptedException  {
    	// Next modified jplaberge
        return systemInfoConnector.getProperty("java.vendor");
    }
 
    public static String getJavaVendorUrl() throws IOException, InterruptedException {
    	// Next modified jplaberge
        return systemInfoConnector.getProperty("java.vendor.url");
    }
 
    public static String getJavaVersion() throws IOException, InterruptedException {
    	// Next modified jplaberge
        return systemInfoConnector.getProperty("java.version");
    }

    public static String getJavaVirtualMachine() throws IOException, InterruptedException {
    	// Next modified jplaberge
        return systemInfoConnector.getProperty("java.vm.name");
    }

    public static String getJavaRuntime() {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
        return systemInfoConnector.getJavaRuntime();
    }
    
    public static boolean isHardFloatAbi() {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
        return systemInfoConnector.isHardFloatAbi();
    }
    
    public static long getMemoryTotal() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryTotal();
    }

    public static long getMemoryUsed() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryUsed();
    }

    public static long getMemoryFree() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryFree();
    }

    public static long getMemoryShared() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryShared();
    }

    public static long getMemoryBuffers() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryBuffers();
    }

    public static long getMemoryCached() throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getMemoryCached();
    }

    public static BoardType getBoardType() throws IOException, InterruptedException
    {
        // The following info obtained from:
        // http://www.raspberrypi.org/archives/1929
        // http://raspberryalphaomega.org.uk/?p=428
        // http://www.raspberrypi.org/phpBB3/viewtopic.php?p=281039#p281039
        // http://elinux.org/RPi_HardwareHistory
        switch(getRevision())
        {
        case "0002":  // Model B Revision 1
        case "0003":  // Model B Revision 1 + Fuses mod and D14 removed
            return BoardType.ModelB_Rev1;
        case "0004":  // Model B Revision 2 256MB (Sony)
        case "0005":  // Model B Revision 2 256MB (Qisda)
        case "0006":  // Model B Revision 2 256MB (Egoman)
            return BoardType.ModelB_Rev2;
        case "0007":  // Model A 256MB (Egoman)
        case "0008":  // Model A 256MB (Sony)
        case "0009":  // Model A 256MB (Qisda)
            return BoardType.ModelA_Rev1;
        case "000d":  // Model B Revision 2 512MB (Egoman)
        case "000e":  // Model B Revision 2 512MB (Sony)
        case "000f":  // Model B Revision 2 512MB (Qisda)
            return BoardType.ModelB_Rev2;
        case "0010":  // Model B Plus 512MB (Sony)
            return BoardType.ModelB_Plus_Rev1;
        case "0011":  // Compute Module 512MB (Sony)
            return BoardType.Compute_Module_Rev1;
        case "0012":  // Model A Plus 512MB (Sony)
            return BoardType.ModelA_Plus_Rev1;
        default:
            return BoardType.UNKNOWN;
        }
    }
    
    public static float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
    	return systemInfoConnector.getCpuTemperature();
    }

    private static float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
       return systemInfoConnector.getVoltage(id);

    }
    
    public static float getCpuVoltage() throws IOException, InterruptedException, NumberFormatException {
        return getVoltage("core");
    }

    public static float getMemoryVoltageSDRam_C() throws IOException, InterruptedException, NumberFormatException {
        return getVoltage("sdram_c");
    }

    public static float getMemoryVoltageSDRam_I() throws IOException, InterruptedException, NumberFormatException {
        return getVoltage("sdram_i");
    }

    public static float getMemoryVoltageSDRam_P() throws IOException, InterruptedException, NumberFormatException {
        return getVoltage("sdram_p");
    }
    
    private static boolean getCodecEnabled(String codec) throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
        return systemInfoConnector.getCodecEnabled(codec);
    }

    public static boolean getCodecH264Enabled() throws IOException, InterruptedException {
        return getCodecEnabled("H264");
    }

    public static boolean getCodecMPG2Enabled() throws IOException, InterruptedException {
        return getCodecEnabled("MPG2");
    }

    public static boolean getCodecWVC1Enabled() throws IOException, InterruptedException {
        return getCodecEnabled("WVC1");
    }
    
    private static long getClockFrequency(String target) throws IOException, InterruptedException {
    	// Next modified jplaberge
    	// Original implementation in SystemInfoLowLevel.java
        return systemInfoConnector.getClockFrequency(target);
    }

    public static long getClockFrequencyArm() throws IOException, InterruptedException {
        return getClockFrequency("arm");
    }

    public static long getClockFrequencyCore() throws IOException, InterruptedException {
        return getClockFrequency("core");
    }

    public static long getClockFrequencyH264() throws IOException, InterruptedException {
        return getClockFrequency("h264");
    }

    public static long getClockFrequencyISP() throws IOException, InterruptedException {
        return getClockFrequency("isp");
    }

    public static long getClockFrequencyV3D() throws IOException, InterruptedException {
        return getClockFrequency("v3d");
    }

    public static long getClockFrequencyUART() throws IOException, InterruptedException {
        return getClockFrequency("uart");
    }

    public static long getClockFrequencyPWM() throws IOException, InterruptedException {
        return getClockFrequency("pwm");
    }

    public static long getClockFrequencyEMMC() throws IOException, InterruptedException {
        return getClockFrequency("emmc");
    }
    
    public static long getClockFrequencyPixel() throws IOException, InterruptedException {
        return getClockFrequency("pixel");
    }
    
    public static long getClockFrequencyVEC() throws IOException, InterruptedException {
        return getClockFrequency("vec");
    }
    
    public static long getClockFrequencyHDMI() throws IOException, InterruptedException {
        return getClockFrequency("hdmi");
    }

    public static long getClockFrequencyDPI() throws IOException, InterruptedException {
        return getClockFrequency("dpi");
    }
    
}
