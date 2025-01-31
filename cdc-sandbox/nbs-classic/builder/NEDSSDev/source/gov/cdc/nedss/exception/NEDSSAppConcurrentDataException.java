package gov.cdc.nedss.exception;

/**
 * Title: DataConcurrencyException Description: This is the exception thrown in
 * case of concurrent data access Copyright: Copyright (c) 2002 Company:
 * Computer Sciences Corporation
 * 
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class NEDSSAppConcurrentDataException extends NEDSSAppException {

	public NEDSSAppConcurrentDataException() {
		super();
	}

	public NEDSSAppConcurrentDataException(String msg) {
		super(msg);
	}

	public NEDSSAppConcurrentDataException(String msg, Exception e) {
		super(msg, e);
	}

}// end of DataConcurrencyException