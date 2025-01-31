package gov.cdc.nedss.systemservice.ejb.srtmapejb.bean;

/**
 * Title: SRTMapHome.java.
 * Description: This class is the Home interface for SRTMap session EJB.
 * Copyright:    Copyright (c) 2001
 * Company: CSC
 * @author: Roger Wilson
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SRTMapHome extends EJBHome {

   public SRTMap create()
    throws RemoteException, CreateException;
}
