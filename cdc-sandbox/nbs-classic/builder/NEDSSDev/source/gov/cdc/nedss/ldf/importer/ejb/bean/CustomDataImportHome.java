package gov.cdc.nedss.ldf.importer.ejb.bean;

/**
 * Title: CustomDataImportHome.java.
 * Description: This class is the Home interface for CustomDataImport session EJB.
 * Copyright:    Copyright (c) 2004
 * Company: CSC
 * @author:
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CustomDataImportHome extends EJBHome {

   public CustomDataImport create()
    throws RemoteException, CreateException;
}
