package gov.cdc.nedss.systemservice.exception;


/**
 * An exceptional condition occurred while attempting to
 * inflate bean from an HttpServletRequest
 */
public class RequestUtilsException extends Exception{

   public RequestUtilsException(){
      super();
   }

   public RequestUtilsException(String msg){
      super(msg);
   }

   public RequestUtilsException(String msg, Exception e){
      super(msg, e);
   }
}