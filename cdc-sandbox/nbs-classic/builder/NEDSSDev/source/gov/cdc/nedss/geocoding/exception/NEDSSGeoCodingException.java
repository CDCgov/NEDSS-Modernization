package gov.cdc.nedss.geocoding.exception;

import gov.cdc.nedss.exception.NEDSSAppException;

public class NEDSSGeoCodingException extends NEDSSAppException 
{
	private static final long serialVersionUID = 1L;
	public NEDSSGeoCodingException() {
		super();
	}
	public NEDSSGeoCodingException(String errorCd) {
		super(errorCd);
	}
}
