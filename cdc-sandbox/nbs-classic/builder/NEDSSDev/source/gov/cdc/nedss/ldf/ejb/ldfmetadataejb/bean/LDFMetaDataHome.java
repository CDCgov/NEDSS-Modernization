package gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean;

/**
 * Title: LDFMetaDataHome.java.
 * Description: This class is the Home interface for LDFMetaData session EJB.
 * Copyright:    Copyright (c) 2003
 * Company: CSC
 * @author:
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LDFMetaDataHome extends EJBHome {

   public LDFMetaData create()
    throws RemoteException, CreateException;
}
