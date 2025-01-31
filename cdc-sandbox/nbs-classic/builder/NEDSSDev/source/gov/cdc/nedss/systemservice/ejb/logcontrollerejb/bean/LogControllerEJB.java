/**
 * Title:        LogController EJB
 * Description:  NEDSS Log Controller Session Bean EJB
 *
 * Copyright:    Copyright (c) 2001-2002
 * Company: 	 Computer Sciences Corporation
 * @author       12/28/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     01/08/2002 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean;

// Import Statements
import gov.cdc.nedss.act.interview.dt.InterviewAnswerDT;

// gov.cdc.nedss.* imports
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class LogControllerEJB implements SessionBean {

  /**
   * Logger for this class
   */
    static final LogUtils logger = new LogUtils((LogControllerEJB.class).getName()); // Added for the logger

  /**
   * Session Context.
   */
  private SessionContext sessionContext;

  /**
   * Creates a new Log Controller
   *
   * @exception RemoteException EJB Remote Exception
   * @exception CreateException EJB Create Exception
   */
  public void ejbCreate() {
    logger.debug("*** ejbCreate ...");
  }

  /**
   * Removes the Log Controller
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbRemove() throws RemoteException {
    logger.debug("*** ejbRemove ...");
  }

  /**
   * Is called whenever Log Controller becomes activated.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbActivate() throws RemoteException {
    logger.debug("*** ejbActivate ...");
  }

  /**
   * Is called whenever Log Controller becomes pasivated.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbPassivate() throws RemoteException {
    logger.debug("*** ejbPassivate ...");
  }

  /**
   * Is called whenever Session Context is needed to be set.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void setSessionContext(SessionContext sessionContext) throws RemoteException {
    this.sessionContext = sessionContext;
    logger.debug("*** setSessionContext ...");
  }

  /**
   * Sets the Log Level of LogUtils
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void setLogLevel(int iLevel) throws RemoteException {
    LogUtils.setLogLevel(iLevel);
    logger.debug("*** setLogLevel(" + iLevel + ") ...");
  }

  /**
   * Method to test the functionality of the Log Controller.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void runTest() throws RemoteException {

    logger.fatal("*** Testing LogController BEGIN ...");
    logger.fatal("Current Level:" + LogUtils.getLogLevelName() + "[" + LogUtils.getLogLevel() + "]");
    logger.debug("DEBUG");
    logger.info("INFO");
    logger.warn("WARNING");
    logger.error("ERROR");
    logger.fatal("FATAL");
    logger.fatal("*** Testing LogController END ...");

    logger.fatal("*** Testing LogController (More Informative) BEGIN ...");
    logger.fatal("Current Level:" + LogUtils.getLogLevelName() + "[" + LogUtils.getLogLevel() + "]");
    logger.debug("Sohrab", "DEBUG");
    logger.info("Sohrab", "INFO");
    logger.warn("Sohrab", "WARNING");
    logger.error("Sohrab", "ERROR");
    logger.fatal("Sohrab", "FATAL");
    logger.fatal("*** Testing LogController (More Informative) END ...");

  }
  

  
  
}