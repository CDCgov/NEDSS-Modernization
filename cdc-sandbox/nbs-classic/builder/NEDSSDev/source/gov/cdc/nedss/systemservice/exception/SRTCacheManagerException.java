package gov.cdc.nedss.systemservice.exception;

import gov.cdc.nedss.exception.NEDSSAppException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SRTCacheManagerException extends NEDSSAppException {
  public SRTCacheManagerException() {
  }
  public SRTCacheManagerException(String e)
   {
                super(e);
   }
  public SRTCacheManagerException(String e, Exception x)
  {
               super(e, x);
  }

}