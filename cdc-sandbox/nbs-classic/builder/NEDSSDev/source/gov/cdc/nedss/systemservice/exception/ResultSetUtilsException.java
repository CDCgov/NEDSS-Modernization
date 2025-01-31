/**
 * Title: Resultset Exception class.
 * Description: An exception class for resultset.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author sdesai
 * @version 1.0
 */
package gov.cdc.nedss.systemservice.exception;

import java.io.*;

/**
*
*/
public class ResultSetUtilsException extends Exception{

   public ResultSetUtilsException(){
      super();
   }

   public ResultSetUtilsException(String msg){
      super(msg);
   }

   public ResultSetUtilsException(String msg, Exception e){
      super(msg, e);
   }

}