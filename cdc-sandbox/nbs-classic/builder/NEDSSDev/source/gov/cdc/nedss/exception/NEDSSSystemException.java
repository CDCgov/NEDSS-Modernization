/**
 * Name:		NEDSSSystemException.java
 * Description:	This is an exception thrown in the event of unrecoverable
 *               system errors.
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Brent Chen & NEDSS Development Team
 * @version	1.0
 **/

package gov.cdc.nedss.exception;

import java.lang.RuntimeException;

public class NEDSSSystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NEDSSSystemException() {
		super();
	}

	public NEDSSSystemException(String stringMsg) {
		super(stringMsg);
	}

	public NEDSSSystemException(String stringMsg, Exception e) {
		super(stringMsg, e);
	}
}// end of NEDSSSystemException class

