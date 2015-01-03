package ca.magenta.pi.remote.connector;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pi4j.connector.Pi4jConnectorException;
import com.pi4j.connector.WiringpiConnectorInterface;
import com.pi4j.wiringpi.GpioInterruptListener;

import ca.magenta.pi.remote.common.Globals;
import ca.magenta.pi.remote.common.RemotePiException;
import ca.magenta.pi.remote.common.WiringPiReturnBooleanValue;
import ca.magenta.pi.remote.common.WiringPiReturnIntValue;
import ca.magenta.pi.remote.common.WiringPiReturnLongValue;
import ca.magenta.pi.remote.common.WiringPiReturnStatus;

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
public class RemoteWiringpiConnector implements WiringpiConnectorInterface {

    static Logger logger = Logger.getLogger(RemoteWiringpiConnector.class);

    private InterruptClient interruptClient = null;
    //private PrintWriter outToLocalInterruptHandler = null;
    private String restURL = null;

    public RemoteWiringpiConnector() {
        super();

        try {
            Globals.loadRemoteRaspberryPIProperties();
        } catch (IOException e1) {
            logger.error("", e1);
            // Re-throw
            throw new Pi4jConnectorException("", e1);
        }

        String remoteRaspberryPiIPAddress = Globals.getRemoteRaspberryPiIPAddress();
        if (remoteRaspberryPiIPAddress != null) {

            restURL = Globals.getRestURL();

            try {
                interruptClient = new InterruptClient();

                interruptClient.startClient(remoteRaspberryPiIPAddress, Globals.getGpioInterruptHandlerPort());

               // outToLocalInterruptHandler = interruptClient.getOutToServer();
            } catch (Throwable e) {
                logger.error("", e);
                throw new RemotePiException("", e);
            }
        } else {
            throw new Pi4jConnectorException("Error: RemoteWiringpiConnector requires a remoteRaspberryPiIPAddress in " + Globals.PROPS_FILE_NAME);
        }

    }

    @Override
    public int wiringPiSetup() throws RemotePiException {
        return WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/setup?pretty=false").getRetCode();
    }

    @Override
    public int wiringPiSetupSys() throws RemotePiException {
        throw new RemotePiException("wiringPiSetupSys not implemented");
    }

    @Override
    public int wiringPiSetupGpio() throws RemotePiException {
        throw new RemotePiException("wiringPiSetupGpio not implemented");
    }

    @Override
    public void pinMode(int pin, int mode) throws RemotePiException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/pinMode/" + mode + "?pretty=false");
    }

    @Override
    public void pullUpDnControl(int pin, int pud) throws RemotePiException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/pullUpDnControl/" + pud + "?pretty=false");
    }

    @Override
    public void digitalWrite(int pin, int value) throws RemotePiException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/digitalWrite/" + value + "?pretty=false");
    }

    @Override
    public void digitalWrite(int pin, boolean value) throws RemotePiException {
        throw new RemotePiException("digitalWrite(int,boolean) not implemented");
    }

    @Override
    public void pwmWrite(int pin, int value) throws RemotePiException {
        throw new RemotePiException("pwmWrite not implemented");
    }

    @Override
    public int digitalRead(int pin) throws RemotePiException {
        return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/digitalRead?pretty=false").getValue();
    }

    @Override
    public void delay(long howLong) throws RemotePiException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/delay/" + howLong + "?pretty=false");
    }

    @Override
    public long millis() throws RemotePiException {
        return WiringPiReturnLongValue.httpGetResource(restURL + "/wiringPiGpio/millis?pretty=false").getValue();
    }

    @Override
    public void delayMicroseconds(long howLong) throws RemotePiException {
        throw new RemotePiException("delayMicroseconds not implemented");
    }

    @Override
    public int piHiPri(int priority) throws RemotePiException {
        throw new RemotePiException("piHiPri not implemented");
    }

    @Override
    public int waitForInterrupt(int pin, int timeout) throws RemotePiException {
        throw new RemotePiException("waitForInterrupt not implemented");
    }

    @Override
    public int piBoardRev() throws RemotePiException {
        throw new RemotePiException("piBoardRev not implemented");
    }

    @Override
    public int wpiPinToGpio(int wpiPin) throws RemotePiException {
        return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + wpiPin + "/wpiPinToGpio?pretty=false").getValue();
    }

    // com.pi4j.wiringpi.GpioUtil
    @Override
    public void export(int pin, int direction) throws RuntimeException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/export/" + direction + "?pretty=false");
    }

    @Override
    public void unexport(int pin) throws RuntimeException {
    	WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "?pretty=false");
    }

    @Override
    public boolean isExported(int pin) throws RuntimeException {
    	return WiringPiReturnBooleanValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/isExported?pretty=false").getValue();
    }

    @Override
    public boolean setEdgeDetection(int pin, int edge) throws RuntimeException {
        return WiringPiReturnBooleanValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/setEdgeDetection/" + edge + "?pretty=false").getValue();
    }

    @Override
    public int getEdgeDetection(int pin) throws RuntimeException {
        throw new RemotePiException("getEdgeDetection not implemented");
    }

    @Override
    public boolean setDirection(int pin, int direction) throws RuntimeException {
        return WiringPiReturnBooleanValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/setDirection/" + direction + "?pretty=false").getValue();
    }

    @Override
    public int getDirection(int pin) throws RuntimeException {
    	 return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/getDirection?pretty=false").getValue();
    }

    @Override
    public int isPinSupported(int pin) throws RemotePiException {
        return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/isPinSupported?pretty=false").getValue();
    }
    
    // GpioInterrupt
    @Override
    public int enablePinStateChangeCallback(int pin) throws RuntimeException {
        return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/enablePinStateChangeCallback?pretty=false").getValue();
    }

    @Override
    public int disablePinStateChangeCallback(int pin) throws RuntimeException {
        return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/disablePinStateChangeCallback?pretty=false").getValue();
    }
    
	@Override
	public void addListener(GpioInterruptListener listener) {
		com.pi4j.wiringpi.GpioInterruptNonNative.addListener(listener);
		
	}

	@Override
	public void removeListener(GpioInterruptListener listener) {
		com.pi4j.wiringpi.GpioInterruptNonNative.removeListener(listener);
	}

	@Override
	public boolean hasListener(GpioInterruptListener listener) {
		return com.pi4j.wiringpi.GpioInterruptNonNative.hasListener(listener);
	}

	@Override
	public void shutdown() {
		 interruptClient.stopClient();
	}
	
	// com.pi4j.wiringpi.SoftPwm
    public int softPwmCreate(int pin, int initialValue, int pwmRange) throws RuntimeException {
   	 return WiringPiReturnIntValue.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/softPwmCreate/" + initialValue + "/" + pwmRange + "?pretty=false").getValue();
   }

    
	@Override
    public void softPwmWrite(int pin, int value) throws RuntimeException {
        WiringPiReturnStatus.httpGetResource(restURL + "/wiringPiGpio/pins/" + pin + "/softPwmWrite/" + value + "?pretty=false");
    }

}
