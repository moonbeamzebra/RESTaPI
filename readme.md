#RESTaPI
#Let's take a REST ... with a Pi !

RESTaPI is a REST API server using PI4J library [pi4j.com] (including WiringPi library [wiringpi.com])

*********************
#REMOTELY CONTROL GPIO
The goal is to control remotely the Raspberry Pi GPIO by simply using HTTP GET requests.
For example, using any browser from any machine (even another Raspberry Pi) and do the following HTTP GETs in order will
Turn ON and OFF a LED connected to RaspiPin.GPIO_01:

http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/export/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/setDirection/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/pinMode/1
Turn ON
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/digitalWrite/1
Turn OFF
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/1/digitalWrite/0

Where raspberrypi is the IP address of your Raspberry Pi (set as required)

You could even remotely listen to GPIO interrupts!

*********************
#GET THE MAVEN PROJECT
Get the complete Maven project at:
https://github.com/moonbeamzebra/RESTaPI
Clicking 'Download ZIP' button and unzip it
Or 
Simply with 'git':
git clone https://github.com/moonbeamzebra/RESTaPI.git


*********
#QUICK TRY
For a quick try out, an installation tarball is included in the git project
Look for RESTaPI.tz

-> IMPORTANT NOTE:
   - Accessing RESTaPI does NOT require any authentication.  Must NOT be used in a production or secure environments.
   - HTTPS capability will be added in a future version

#INSTALLATION AND RUN INSTRUCTIONS 
(Java 7 is required on the Raspberry Pi)
- Transfer RESTaPI.tz to the /home/pi in a Raspberry Pi
- As 'pi' user, untar :                 
-- cd ~ ; tar zxvf RESTaPI.tz
- Go into RESTaPI directory :           
-- cd ~/RESTaPI
- Sudo as 'root' :                      
-- sudo -s
- Start the server :                    
-- ./startRESTaPI
NOTE: it takes about 60 seconds to start; wait for the following pattern:
[...] Started SelectChannelConnector@0.0.0.0:8080

After RESTaPI is started, use a browser to do the above HTTP GETs and see the LED turning ON and OFF


*************************
#LISTEN TO GPIO INTERRUPTS
To remotely get GPIO interrupt feedback, let 1st use telnet
On the Raspberry Pi
     Connect a toggle switch to RaspiPin.GPIO_02
From a remote workstation
- Open a terminal and do :              telnet raspberrypi 8181
- In a browser :                        Do the following HTTP GETs in order

http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/export/0
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/pinMode/0
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/setEdgeDetection/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/pullUpDnControl/1
http://raspberrypi:8080/RESTaPI/wiringPiGpio/pins/2/enablePinStateChangeCallback   

Toggle the switch and see the listener feedback in telnet screen


*******************
#BUILD MAVEN PROJECT
To build, go in the project directory just download above and do
mvn clean install
RESTaPI.war is in ca.magenta.pi.remote/target


*********
#REST APIs
Look for URLs-Of-Supported-Functions.txt for the list of ported functions
The intend was to port into a REST API the low level functions to allow, to the REST users, the full functionality of the ported libraries
Not all functions are yet ported, but I'm working on that.


*********************************
#PROGRAMMATICALLY USE THE REST API
Most programming language allows doing HTTP GETs and implement TCP client (for GPIO interrupts).  
With RESTaPI running, it is then possible to remotely control a Raspberry Pi in many languages.

The REST responses are JSON formatted.  Same for the GPIO interrupt notifications.
See the ca.magenta.pi.remote.common package for the corresponding Java classes.

See ca.magenta.pi.example sub-project. 
It contains a subset of Robert Savage (PI4J) examples that are functional to be run remotely (look at runExampleViaREST bash script). 
Note: it is not required to run runExampleViaREST as 'root'
You could even run/trace/debug those examples from Eclipse on your power-workstation (Windows, MAC, etc) 
 

******
#AUTHOR
Jean-Paul Laberge <moonbeamzebra@magenta.ca>


*************** 
#ACKNOWLEDGEMENT
PI4J library - pi4j.com - Thanks for this nice project
WiringPi library - wiringpi.com - Thanks to Gordon Henderson

Thanks again to PI4J project for nice Maven POMs (very good kick start to the Maven world)
github.com/Pi4J/pi4j

www.mkyong.com - nice examples for REST (Jersey), JSON, etc.

RESTaPI.war is launched by jetty-runner
mvnrepository.com/artifact/org.mortbay.jetty/jetty-runner 

Raspberry Pi and the associated Logo are trademarks of The Raspberry Pi Foundation
www.raspberrypi.org


*******
#LICENSE
==========================================================================
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
==========================================================================
