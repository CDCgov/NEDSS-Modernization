package gov.cdc.nedss.systemservice.exception;
import gov.cdc.nedss.systemservice.exception.SRTCacheManagerException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InvalidSRTFilterKeysException extends SRTCacheManagerException {
  public InvalidSRTFilterKeysException() {
  }
  /**
   This is a constructor for the class.
   @param e
    */
   public InvalidSRTFilterKeysException(String e)
   {
                super(e);
   }


   public InvalidSRTFilterKeysException(String e, Exception x)
   {
                super(e, x);
   }
}