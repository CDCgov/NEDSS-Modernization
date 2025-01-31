/**
 * Title:        JurisdictionHome
 * Description:  NEDSS Jurisdiction Session Bean Home Interface
 *
 * Copyright:    Copyright (c) 2003
 * Company: 	 Computer Sciences Corporation
 * @author       3/12/2003 Chris Hanson & NEDSS Development Team
 * @modified     
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean;

// Import Statements
import java.rmi.*;
import javax.ejb.*;

public interface JurisdictionHome extends EJBHome {

  /**
   * Creates a new Jursidiction Service
   *
   * @exception RemoteException EJB Remote Exception
   * @exception CreateException EJB Create Exception
   */
  public Jurisdiction create() throws RemoteException, CreateException;
}