package gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.bean;

import gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dao.NBSProcessLogDAO;
import gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dt.NBSMasterLogDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionLoggerDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
/*
 * 
 */

public class NBSMasterLogEJB    implements SessionBean{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	   * Logger for this class
	   */
	  static final LogUtils logger = new LogUtils( (NBSMasterLogEJB.class).getName()); // Added for the logger
	  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	  /**
	   * Session Context.
	   */
	  private SessionContext sessionContext;

	  /**
	   * Creates a new Jursidiction
	   *
	   * @exception RemoteException EJB Remote Exception
	   * @exception CreateException EJB Create Exception
	   */
	  public void ejbCreate()
	  {
	    logger.debug("*** ejbCreate ...");
	  }

	  /**
	   * Removes the NBSMasterLog
	   *
	   * @exception RemoteException EJB Remote Exception
	   */
	  public void ejbRemove() throws RemoteException
	  {
	    logger.debug("*** ejbRemove ...");
	  }

	  /**
	   * Is called whenever Jurisiction becomes activated.
	   *
	   * @exception RemoteException EJB Remote Exception
	   */
	  public void ejbActivate() throws RemoteException
	  {
	    logger.debug("*** ejbActivate ...");
	  }

	  /**
	   * Is called whenever NBSMasterLog becomes pasivated.
	   *
	   * @exception RemoteException EJB Remote Exception
	   */
	  public void ejbPassivate() throws RemoteException
	  {
	    logger.debug("*** ejbPassivate ...");
	  }

	  /**
	   * Is called whenever Session Context is needed to be set.
	   *
	   * @exception RemoteException EJB Remote Exception
	   */
	  public void setSessionContext(SessionContext sessionContext) throws
	      RemoteException
	  {
	    this.sessionContext = sessionContext;
	    logger.debug("*** setSessionContext ...");
	  }
	  
	  public void writeToNBSLogMaster(NBSMasterLogDT nBSMasterLogDT) throws RemoteException{
		  try {
			NBSProcessLogDAO nBSProcessLogDAO = new NBSProcessLogDAO();
			  nBSProcessLogDAO.insertNBSMasterLogDT(nBSMasterLogDT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
		}
		


}
