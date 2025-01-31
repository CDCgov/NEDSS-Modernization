/**
 * Title:        LogControllerHome
 * Description:  NEDSS Log Controller Session Bean Home Interface
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       12/28/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     12/28/2001 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean;

// Import Statements
import java.rmi.*;
import javax.ejb.*;

public interface LogControllerHome extends EJBHome {

  /**
   * Creates a new Log Controller
   *
   * @exception RemoteException EJB Remote Exception
   * @exception CreateException EJB Create Exception
   */
  public LogController create() throws RemoteException, CreateException;
}