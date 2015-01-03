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


import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.pi4j.util.ExecUtil;
import com.pi4j.util.StringUtil;

//OK for pi4j-1.0-SNAPSHOT - jplaberge
public class SystemInfoLowLevel {

    // private constructor 
    private SystemInfoLowLevel() {
        // forbid object construction 
    }
    
    public static String[] getCpuInfo()  throws IOException, InterruptedException
    {
        return ExecUtil.execute("cat /proc/cpuinfo");
    }
    
    public static String getProperty(String property)  throws IOException, InterruptedException
    {
        return System.getProperty(property);
    }
    
    
    public static String getOsFirmwareBuild() throws IOException, InterruptedException {
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd version");
        if(result != null){
            for(String line : result) {
                if(line.startsWith("version ")){                
                    return line.substring(8);
                }
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }  

    public static String getOsFirmwareDate() throws IOException, InterruptedException, ParseException {
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd version");
        if(result != null){
            for(String line : result) {
                return line; // return 1st line
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }  

    public static String getJavaRuntime() {
        return AccessController.doPrivileged(new PrivilegedAction<String>()  {
            public String run()  {
                return System.getProperty("java.runtime.name");
            }
        });
    }
    
    /*
     * this method was partially derived from :: (project) jogamp / (developer) sgothel
     * https://github.com/sgothel/gluegen/blob/master/src/java/jogamp/common/os/PlatformPropsImpl.java#L160
     * https://github.com/sgothel/gluegen/blob/master/LICENSE.txt
     */
    public static boolean isHardFloatAbi() {
        
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            private final String[] gnueabihf = new String[] { "gnueabihf", "armhf" };
            public Boolean run() {                    
                return ( StringUtil.contains(System.getProperty("sun.boot.library.path"), gnueabihf) ||
                     StringUtil.contains(System.getProperty("java.library.path"), gnueabihf) ||
                     StringUtil.contains(System.getProperty("java.home"), gnueabihf) || 
                     getBashVersionInfo().contains("gnueabihf") ||
                     hasReadElfTag("Tag_ABI_HardFP_use"));
            } } );
    }

    public static long getMemoryTotal() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 0){
            return values.get(0); // total memory value is in first position
        }
        return -1;
    }

    public static long getMemoryUsed() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 1){
            return values.get(1); // used memory value is in second position
        }
        return -1;
    }

    public static long getMemoryFree() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 2){
            return values.get(2); // free memory value is in third position
        }
        return -1;
    }

    public static long getMemoryShared() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 3){
            return values.get(3); // shared memory value is in fourth position
        }
        return -1;
    }

    public static long getMemoryBuffers() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 4){
            return values.get(4); // buffers memory value is in fifth position
        }
        return -1;
    }

    public static long getMemoryCached() throws IOException, InterruptedException {
        List<Long> values = getMemory();
        if(!values.isEmpty() && values.size() > 5){
            return values.get(5); // cached memory value is in sixth position
        }
        return -1;
    }



    public static float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException {
        // CPU temperature is in the form
        // pi@mypi$ /opt/vc/bin/vcgencmd measure_temp
        // temp=42.3'C
        // Support for this was added around firmware version 3357xx per info
        // at http://www.raspberrypi.org/phpBB3/viewtopic.php?p=169909#p169909
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd measure_temp");
        if(result != null && result.length > 0){
            for(String line : result) {
                String parts[] = line.split("[=']", 3);                
                return Float.parseFloat(parts[1]);
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }

    public static float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException, IllegalArgumentException {
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd measure_volts " + id);
        if(result != null && result.length > 0){
            for(String line : result) {
        		if (line.contains("bad argument"))
        		{
        			throw new IllegalArgumentException("Bad argument:[" + id + "]");
        		}
                String parts[] = line.split("[=V]", 3);                
                return Float.parseFloat(parts[1]);
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }

    public static boolean getCodecEnabled(String codec) throws IOException, InterruptedException {
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd codec_enabled " + codec);
        if(result != null && result.length > 0){
            for(String line : result) {
                String parts[] = line.split("=", 2);                
                return parts[1].trim().equalsIgnoreCase("enabled");
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }

    public static long getClockFrequency(String target) throws IOException, InterruptedException {
        String result[] = ExecUtil.execute("/opt/vc/bin/vcgencmd measure_clock " + target.trim());
        if(result != null && result.length > 0){
            for(String line : result) {
                String parts[] = line.split("=", 2);                
                return Long.parseLong(parts[1].trim());
            }
        }
        throw new RuntimeException("Invalid command or response.");
    }
    
    
    /////////////////////
    // Private after here
    /////////////////////
    
    /*
     * this method will to obtain the version info string from the 'bash' program
     * (this method is used to help determine the HARD-FLOAT / SOFT-FLOAT ABI of the system)
     */
    private static String getBashVersionInfo() {
        String versionInfo = "";
        try {
            String result[] = ExecUtil.execute("bash --version");
            for(String line : result) {
                if(!line.isEmpty()) { 
                    versionInfo = line; // return only first output line of version info
                    break;
                }
            }
        }
        catch (IOException|InterruptedException ioe) { ioe.printStackTrace(); }
        return versionInfo;
    }
    
    /*
     * this method will determine if a specified tag exists from the elf info in the '/proc/self/exe' program
     * (this method is used to help determine the HARD-FLOAT / SOFT-FLOAT ABI of the system)
     */    
    private static boolean hasReadElfTag(String tag) {
        String tagValue = getReadElfTag(tag);
        return (tagValue != null && !tagValue.isEmpty());
    }
    
    /*
     * this method will obtain a specified tag value from the elf info in the '/proc/self/exe' program
     * (this method is used to help determine the HARD-FLOAT / SOFT-FLOAT ABI of the system)
     */    
    private static String getReadElfTag(String tag) {
        String tagValue = null;
        try {
            String result[] = ExecUtil.execute("/usr/bin/readelf -A /proc/self/exe");
            if(result != null){
                for(String line : result) {
                    line = line.trim();
                    if (line.startsWith(tag) && line.contains(":")) {
                        String lineParts[] = line.split(":", 2);
                        if(lineParts.length > 1)
                            tagValue = lineParts[1].trim();
                        break;
                    }
                }
            }            
        }
        catch (IOException|InterruptedException ioe) { ioe.printStackTrace(); }
        return tagValue;
    }
    
    private static List<Long> getMemory() throws IOException, InterruptedException {
        // Memory information is in the form
        // root@mypi:/home/pi# free -b
        //              total       used       free     shared    buffers     cached
        // Mem:     459771904  144654336  315117568          0   21319680   63713280
        // -/+ buffers/cache:   59621376  400150528
        // Swap:    104853504          0  104853504
        List<Long> values = new ArrayList<>();
        String result[] = ExecUtil.execute("free -b");
        if(result != null){
            for(String line : result) {
                if(line.startsWith("Mem:")){
                    String parts[] = line.split(" ");                    
                    for(String part : parts){
                        part = part.trim();
                        if(!part.isEmpty() && !part.equalsIgnoreCase("Mem:")){
                            values.add(new Long(part));
                        }
                    }
                }
            }
        }
        return values;
    }
    

}
