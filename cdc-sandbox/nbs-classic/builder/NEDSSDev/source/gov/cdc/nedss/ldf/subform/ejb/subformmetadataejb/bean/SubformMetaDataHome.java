package gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean;

/**
 * Title: SubformMetaDataHome.java.
 * Description: This class is the Home interface for SubformMetaData session EJB.
 * Copyright:    Copyright (c) 2004
 * Company: CSC
 * @author:
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SubformMetaDataHome extends EJBHome {

   public SubformMetaData create()
    throws RemoteException, CreateException;
}
