package gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.bean;

import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.Jurisdiction;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/*
 * 
 */
public interface NBSMasterLogHome extends EJBHome {

	  /**
	   * Creates a new Jursidiction Service
	   *
	   * @exception RemoteException EJB Remote Exception
	   * @exception CreateException EJB Create Exception
	   */
	public Jurisdiction create() throws RemoteException, CreateException;
	}

