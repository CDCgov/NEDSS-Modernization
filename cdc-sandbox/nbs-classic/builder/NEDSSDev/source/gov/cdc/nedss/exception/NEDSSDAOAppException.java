/**
 * Name:        NEDSSDAOAppException.java
 * Description:    This is an exception thrown in the event of application errors.
 * Copyright:    Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author    Brent Chen & NEDSS Development Team
 * @version    1.0
 **/
package gov.cdc.nedss.exception;

public class NEDSSDAOAppException extends NEDSSAppException {

	/**
	 * Creates a new NEDSSDAOAppException object.
	 */
	public NEDSSDAOAppException() {
		super();
	}

	/**
	 * Creates a new NEDSSDAOAppException object.
	 * 
	 * @param stringMsg
	 */
	public NEDSSDAOAppException(String stringMsg) {
		super(stringMsg);
	}
	
	public NEDSSDAOAppException(String stringMsg, Exception e) {
		super(stringMsg, e);
	}
} // end of NEDSSDAOAppException class