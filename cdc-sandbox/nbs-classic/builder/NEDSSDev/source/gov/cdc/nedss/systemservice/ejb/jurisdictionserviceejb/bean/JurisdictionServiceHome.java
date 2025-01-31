/**
 * Title:        JurisdictionServiceHome
 * Description:  NEDSS Jurisdiction Session Bean Home Interface
 *
 * Copyright:    Copyright (c) 2003
 * Company: 	 Computer Sciences Corporation
 * @author       3/12/2003 Chris Hanson & NEDSS Development Team
 * @modified     
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean;

// Import Statements
import java.rmi.*;
import javax.ejb.*;

public interface JurisdictionServiceHome extends EJBHome {

  /**
   * Creates a new Jursidiction Service
   *
   * @exception RemoteException EJB Remote Exception
   * @exception CreateException EJB Create Exception
   */
  public JurisdictionService create() throws RemoteException, CreateException;
}