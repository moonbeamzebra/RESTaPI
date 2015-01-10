#RESTaPI
###Let's take a REST ... with a Pi !

RESTaPI is a REST API server using PI4J library [pi4j.com] (including WiringPi library [wiringpi.com])

*********************
##REMOTELY CONTROL GPIO
The goal is to control remotely the Raspberry Pi GPIO by simply using HTTP GET requests.<br>
For example, using any browser from any machine (even another Raspberry Pi) and do the following HTTP GETs in order will<br>
Turn ON and OFF a LED connected to RaspiPin.GPIO_01:<br>

http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/export/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/setDirection/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/pinMode/1
Turn ON
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/digitalWrite/1
Turn OFF
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/digitalWrite/0

Where **raspberrypi** is the IP address of your Raspberry Pi (set as required)

You could even remotely listen to GPIO interrupts!

*********************
##GET THE MAVEN PROJECT
Get the complete Maven project at:<br>
https://github.com/moonbeamzebra/RESTaPI<br>
Clicking 'Download ZIP' button and unzip it.<br>
OR <br>
Simply with 'git'.<br>
git clone https://github.com/moonbeamzebra/RESTaPI.git


*********
##QUICK TRY
For a quick try out, an installation tarball is included in the git project<br>
Look for **RESTaPI.tz**<br>

**IMPORTANT NOTES:**<br>
   - Accessing RESTaPI does NOT require any authentication.  Must NOT be used in a production or secure environments.<br>
   - HTTPS capability will be added in a future version<br>

##INSTALLATION AND RUN INSTRUCTIONS 
(Java 7 is required on the Raspberry Pi)<br>
- Transfer RESTaPI.tz to the /home/pi in a Raspberry Pi<br>
- As 'pi' user, untar :<br>
**cd ~ ; tar zxvf RESTaPI.tz**<br>
- Go into RESTaPI directory :<br>
**cd ~/RESTaPI**<br>
- Sudo as 'root' :<br>
**sudo -s**<br>
- Start the server :<br>
**./startRESTaPI**<br>
<br>**NOTE:** it takes about 60 seconds to start; wait for the following pattern<br>
[...] Started SelectChannelConnector@0.0.0.0:8080<br>

After RESTaPI is started, use a browser to do the above HTTP GETs and see the LED turning ON and OFF


*************************
##LISTEN TO GPIO INTERRUPTS
To remotely get GPIO interrupt feedback, let 1st use telnet<br>
On the Raspberry Pi<br>
- Connect a toggle switch to RaspiPin.GPIO_02<br>
From a remote workstation<br>
- Open a terminal and do :<br>
**telnet raspberrypi 8181**<br>
- In a browser :<br>
**Do the following HTTP GETs in order**

http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/export/0
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/pinMode/0
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/setEdgeDetection/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/pullUpDnControl/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/enablePinStateChangeCallback   

Toggle the switch and see the listener feedback in telnet screen


*******************
##BUILD MAVEN PROJECT
To build, go in the project directory just download above and do<br>
**mvn clean install**<br>
RESTaPI.war is in ca.magenta.pi.remote/target


*********
##REST APIs
Look for URLs-Of-Supported-Functions.txt for the list of ported functions<br>
The intend was to port into a REST API the low level functions to allow, to the REST users, the full functionality of the ported libraries<br>
Not all functions are yet ported, but I'm working on that.


*********************************
##PROGRAMMATICALLY USE THE REST API
Most programming language allows doing HTTP GETs and implement TCP client (for GPIO interrupts).<br>
With RESTaPI running, it is then possible to remotely control a Raspberry Pi in many languages.

The REST responses are JSON formatted.  Same for the GPIO interrupt notifications.<br>
See the ca.magenta.pi.remote.common package for the corresponding Java classes.

See ca.magenta.pi.example sub-project.<br>
It contains a subset of Robert Savage (PI4J) examples that are functional to be run remotely (look at runExampleViaREST bash script).<br>
Note: it is not required to run runExampleViaREST as 'root'<br>
You could even run/trace/debug those examples from Eclipse on your power-workstation (Windows, MAC, etc) 
 

******
##AUTHOR
Jean-Paul Laberge <moonbeamzebra@magenta.ca>


*************** 
##ACKNOWLEDGEMENT
PI4J library - pi4j.com - Thanks for this nice project<br>
WiringPi library - wiringpi.com - Thanks to Gordon Henderson<br>

Thanks again to PI4J project for nice Maven POMs (very good kick start to the Maven world)<br>
github.com/Pi4J/pi4j

www.mkyong.com - nice examples for REST (Jersey), JSON, etc.

RESTaPI.war is launched by jetty-runner<br>
mvnrepository.com/artifact/org.mortbay.jetty/jetty-runner 

Raspberry Pi and the associated Logo are trademarks of The Raspberry Pi Foundation<br>
www.raspberrypi.org


*******
##LICENSE
Copyright 2015 Magenta INC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
