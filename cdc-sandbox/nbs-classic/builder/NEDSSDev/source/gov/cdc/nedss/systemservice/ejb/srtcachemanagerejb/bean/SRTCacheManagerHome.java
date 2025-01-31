package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean;

import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * <p>Title: SRTCacheManagerHome</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

  public interface SRTCacheManagerHome extends javax.ejb.EJBHome{

  public SRTCacheManager create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;

}