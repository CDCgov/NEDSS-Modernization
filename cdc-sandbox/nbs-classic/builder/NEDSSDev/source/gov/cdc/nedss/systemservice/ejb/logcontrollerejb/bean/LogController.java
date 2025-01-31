/**
 * Title:        LogController
 * Description:  NEDSS Log Controller Session Bean Remote Interface
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       12/28/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     12/28/2001 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean;

// Import Statements
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.*;
import java.util.Collection;

import javax.ejb.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public interface LogController extends EJBObject {
  /**
   * Sets the log level.
   *
   * @exception RemoteException EJB Remote Exception
   */

  public void setLogLevel(int iLevel) throws RemoteException;
  /**
   * Method to test the functionality of the Log Controller.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void runTest() throws RemoteException;

}