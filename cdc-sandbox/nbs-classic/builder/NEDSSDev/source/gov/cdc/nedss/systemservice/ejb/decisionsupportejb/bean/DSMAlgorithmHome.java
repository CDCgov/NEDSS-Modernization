package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
/**
 * Title: DSMAlgorithmHome
 * Description: Decision Support Algorithm High Level Services
 * Copyright:    Copyright (c) 2011
 * Company: CSC
 * @author Greg Tucker
 * @version 1.0
 */
public interface DSMAlgorithmHome extends EJBHome {
	  /**
	   * Creates a new DSMAlgorithm Service
	   *
	   * @exception RemoteException EJB Remote Exception
	   * @exception CreateException EJB Create Exception
	   */
	   public DSMAlgorithm create()
	    throws RemoteException, CreateException;
}