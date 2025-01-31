/**
 * Name:		NEDSSException.java
 * Description:	This is an application exception thrown in the event of
 *  		non-fatal errors.  This allows the application to continue.
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Brent Chen & NEDSS Development Team
 * @version	1.0
 **/

package gov.cdc.nedss.exception;

import java.lang.Exception;

public class NEDSSException extends Exception {

	public NEDSSException() {
		super();
	}

	public NEDSSException(String string) {
		super(string);
	}

	public NEDSSException(String string, Exception e) {
		super(string, e);
	}
}// end of NEDSSException class

