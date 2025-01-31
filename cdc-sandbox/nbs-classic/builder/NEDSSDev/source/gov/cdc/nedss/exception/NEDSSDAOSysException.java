/**
 * Name:        NEDSSDAOSysException.java
 * Description:    This is an exception thrown in the event of unrecoverable
 *               system errors.
 * Copyright:    Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author    Brent Chen & NEDSS Development Team
 * @version    1.0
 **/
package gov.cdc.nedss.exception;

public class NEDSSDAOSysException extends NEDSSSystemException {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NEDSSDAOSysException object.
	 */
	public NEDSSDAOSysException() {
		super();
	}

	/**
	 * Creates a new NEDSSDAOSysException object.
	 * 
	 * @param stringMsg
	 */
	public NEDSSDAOSysException(String stringMsg) {
		super(stringMsg);
	}

	public NEDSSDAOSysException(String stringMsg, Exception e) {
		super(stringMsg, e);
	}
} // end of NEDSSDAOSysException class