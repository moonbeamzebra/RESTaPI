package ca.magenta.pi.remote.common;

import com.pi4j.exception.Pi4jException;

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
public class RemotePiException extends Pi4jException {

	
    private static final long serialVersionUID = -3925134217445776254L;

    public static final String MESSAGE = "Exception thrown on targeted Raspberry PI (SEE log4j on Raspberry PI)";

	public RemotePiException(String problemDetail) {
        super(problemDetail.toString());
    }
	
	   public RemotePiException(String problemDetail, Throwable cause) {
	        super(problemDetail.toString(), cause);
	    }

}
