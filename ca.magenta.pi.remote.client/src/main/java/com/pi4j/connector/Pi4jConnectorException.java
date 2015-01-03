package com.pi4j.connector;

import com.pi4j.exception.Pi4jException;


/**
 * @author jean-paul.laberge <moonbeamzebra@magenta.ca>
 * @version 0.1
 * @since January 2014
 */

//TODO Should have some kind of Pi4j mother exception class 
//      like for example com.pi4j.exception.Pi4jException that extend RuntimeException
//      This new class would be super class of everything in com.pi4j.io.gpio.exception
//      And super class of Pi4jConnectorException
public class Pi4jConnectorException extends Pi4jException {

	
    private static final long serialVersionUID = -6617503404326200292L;

    public Pi4jConnectorException(String problemDetail) {
        super(problemDetail.toString());
    }
	
	   public Pi4jConnectorException(String problemDetail, Throwable cause) {
	        super(problemDetail.toString(), cause);
	    }

}
