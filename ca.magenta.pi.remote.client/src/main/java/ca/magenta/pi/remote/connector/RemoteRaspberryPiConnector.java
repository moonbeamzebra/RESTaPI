package ca.magenta.pi.remote.connector;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.pi4j.connector.Pi4jNetworkInfoConnector;
import com.pi4j.connector.Pi4jConnectorException;
import com.pi4j.connector.Pi4jSystemInfoConnector;

import ca.magenta.pi.remote.common.Globals;
import ca.magenta.pi.remote.common.WiringPiReturnBooleanValue;
import ca.magenta.pi.remote.common.WiringPiReturnFloatValue;
import ca.magenta.pi.remote.common.WiringPiReturnLongValue;
import ca.magenta.pi.remote.common.WiringPiReturnStringArrayValue;
import ca.magenta.pi.remote.common.WiringPiReturnStringValue;

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
// No change required for pi4j-1.0-SNAPSHOT - jplaberge
public class RemoteRaspberryPiConnector implements Pi4jSystemInfoConnector, Pi4jNetworkInfoConnector {

    static Logger logger = Logger.getLogger(RemoteRaspberryPiConnector.class);

    private String restURL = null;

    public RemoteRaspberryPiConnector() {
        super();

        try {
            Globals.loadRESTaPIProperties();
        } catch (IOException e1) {
            logger.error("", e1);
            // Re-throw
            throw new Pi4jConnectorException("", e1);
        }

        restURL = Globals.getRestURL();

    }
    
    // SystemInfo
    @Override
    public String[] getCpuInfo() throws IOException, InterruptedException {
        return WiringPiReturnStringArrayValue.httpGetResource(restURL + "/pi4jSystemInfo/cpu/info?pretty=false").getValue();
    }
    
	@Override
	public String getProperty(String property) throws IOException, InterruptedException {
		return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jSystemInfo/properties/" + property + "?pretty=false").getValue();
	}


    @Override
    public String getOsFirmwareBuild() throws IOException, InterruptedException {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jSystemInfo/firmware/build?pretty=false").getValue();
    }

    @Override
    public String getOsFirmwareDate() throws IOException, InterruptedException, ParseException {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jSystemInfo/firmware/date?pretty=false").getValue();
    }

    @Override
    public String getJavaRuntime() {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jSystemInfo/java/runtime?pretty=false").getValue();
    }

    @Override
    public boolean isHardFloatAbi() {
        return WiringPiReturnBooleanValue.httpGetResource(restURL + "/pi4jSystemInfo/isHardFloatAbi?pretty=false").getValue();
    }

    @Override
    public long getMemoryTotal() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/total?pretty=false").getValue();
    }

    @Override
    public long getMemoryUsed() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/used?pretty=false").getValue();
    }
    
    @Override
    public long getMemoryFree() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/free?pretty=false").getValue();
    }

    @Override
    public long getMemoryShared() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/shared?pretty=false").getValue();
    }

    @Override
    public long getMemoryBuffers() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/buffers?pretty=false").getValue();
    }

    @Override
    public long getMemoryCached() throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/memory/cached?pretty=false").getValue();
    }

    @Override
    public float getCpuTemperature() throws IOException, InterruptedException, NumberFormatException {
        return WiringPiReturnFloatValue.httpGetResource(restURL + "/pi4jSystemInfo/cpu/temperature?pretty=false").getValue();
    }

    @Override
    public float getVoltage(String id) throws IOException, InterruptedException, NumberFormatException {
        return WiringPiReturnFloatValue.httpGetResource(restURL + "/pi4jSystemInfo/voltage/" + id + "?pretty=false").getValue();
    }

    @Override
    public boolean getCodecEnabled(String codec) throws IOException, InterruptedException {
        return WiringPiReturnBooleanValue.httpGetResource(restURL + "/pi4jSystemInfo/codecEnabled/" + codec + "?pretty=false").getValue();
    }

    @Override
    public long getClockFrequency(String target) throws IOException, InterruptedException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/pi4jSystemInfo/clockFrequency/" + target + "?pretty=false").getValue();
    }

    // NetworkInfo
	@Override
	public String getHostname() throws IOException, InterruptedException {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jNetworkInfo/hostname?pretty=false").getValue();
	}

	@Override
	public String getFQDN() throws IOException, InterruptedException {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jNetworkInfo/fqdn?pretty=false").getValue();
	}

	@Override
	public String[] getIPAddresses() throws IOException, InterruptedException {
        return WiringPiReturnStringArrayValue.httpGetResource(restURL + "/pi4jNetworkInfo/ipAddresses?pretty=false").getValue();
	}

	@Override
	public String getIPAddress() throws IOException, InterruptedException {
        return WiringPiReturnStringValue.httpGetResource(restURL + "/pi4jNetworkInfo/ipAddress?pretty=false").getValue();
	}

	@Override
	public String[] getFQDNs() throws IOException, InterruptedException {
        return WiringPiReturnStringArrayValue.httpGetResource(restURL + "/pi4jNetworkInfo/fqdns?pretty=false").getValue();
	}

	@Override
	public String[] getNameservers() throws IOException, InterruptedException {
        return WiringPiReturnStringArrayValue.httpGetResource(restURL + "/pi4jNetworkInfo/nameservers?pretty=false").getValue();
	}


}
