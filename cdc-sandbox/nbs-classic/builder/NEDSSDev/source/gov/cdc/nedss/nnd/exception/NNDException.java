/**
* Name:		NNDException.java
* Description:	This is an application exception thrown in the event of
*  		non-fatal errors.  This allows the application to continue.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
**/


package gov.cdc.nedss.nnd.exception;


import java.lang.Exception;



public class NNDException extends Exception
{

    public NNDException()
    {
        super();
    }

    public NNDException(String string)
    {
        super(string);
    }

 // this would be used by the calling program to identify where this exception was being generated
 // and be used when throwing a NEDSSAppException
  private String moduleName;
  public String getModuleName() {
    return moduleName;
  }
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
}//end of NNDException class

