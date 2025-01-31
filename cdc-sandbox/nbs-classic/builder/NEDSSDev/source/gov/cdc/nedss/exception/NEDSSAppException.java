/**
* Name:		NEDSSAppException.java
* Description:	This is an exception thrown in the event of application errors.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
**/


package gov.cdc.nedss.exception;


import java.lang.Exception;



public class NEDSSAppException extends Exception
{
	private static final long serialVersionUID = 1L;
    private String errorCd;
    private Exception exception;
    private String errorDescription;
    
    public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public NEDSSAppException()
    {
        super();
    }

    public NEDSSAppException(String errorCd)
    {
        super(errorCd);
        this.errorCd = errorCd;
    }
    public NEDSSAppException(String errorCd, Exception e)
    {
        super(errorCd, e);
        this.errorCd = errorCd;
        this.exception = e;
    }
    public String getErrorCd() {
      return errorCd;
    }
    public Exception getException() {
        return exception;
      }
    public NEDSSAppException(String errorCd, String errorDescription)
    {
        super(errorCd);
        this.errorCd = errorCd;
        this.errorDescription = errorDescription;
        
    }
}//end of NEDSSAppException class

