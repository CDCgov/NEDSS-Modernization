 package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean;

import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao.*;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.*;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util.DSMAlgorithmCache;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;




import javax.ejb.EJBException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Title: DSMAlgorithmEJB
 * Description: Decision Support Criteria (Algorithm) High Level Services
 * Copyright:    Copyright (c) 2011
 * Company: CSC
 * @author Greg Tucker
 * @version 1.0
 */


public class DSMAlgorithmEJB implements SessionBean {

  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils (DSMAlgorithmEJB.class.getName());
	private SessionContext sessionContext;

	
/**
 * Insert Decision Support Criteria (algorithm) record
 * @param securityObj and dsmAlgorithmDT
 * @return UID if successful
 * @throws EJBException, RemoteException
 */	
 public  Long createDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws
      EJBException, RemoteException
  {
    boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                     NBSOperationLookup.DECISION_SUPPORT_ADMIN);

    if (check == false)
    {
      throw new NEDSSSystemException(
          "Sorry, you don't have permission to insert a new Decision Support Mgr Algorithm!");
    }

    try {
    	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
    	dsmAlgorithmDT = algorithmDao.insertDSMAlgorithmDT(dsmAlgorithmDT);
  	}
    catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.createDSMAlgorithm: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);
	}
  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.createDSMAlgorithm: Exception: :  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);
	}
    return dsmAlgorithmDT.getDsmAlgorithmUid(); //return only the new UID
  } //insertDSMAlgorithm

 /**
  * Update algorithm record based on UID
  * @param securityObj and dsmAlgorithmDT
  * @return DSMAlgorithmDT UID if found
  * @throws EJBException, RemoteException
  */
 public  Long updateDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws
      EJBException, RemoteException
  {
    boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                     NBSOperationLookup.DECISION_SUPPORT_ADMIN);

    if (check == false)
    {
      throw new NEDSSSystemException(
          "Sorry, you don't have permission to update a  Decision Support Mgr Algorithm Record!");
    }

    try {
    	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
    	dsmAlgorithmDT = algorithmDao.updateDSMAlgorithmDT(dsmAlgorithmDT);
  	} catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.createDSMAlgorithm: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);
	}
  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.createDSMAlgorithm: Exception: :  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);
	}
    return dsmAlgorithmDT.getDsmAlgorithmUid(); //return only the updated UID
  } //updateDSMAlgorithm

 /**
  * Activate/Inactivate algorithm record based on UID
  * @param securityObj and dsmAlgorithmDT
  * @return DSMAlgorithmDT UID if found
  * @throws EJBException, RemoteException
  */
 public  void updateDSMAlgorithmStatus(Long algorithmUid , String status, NBSSecurityObj nbsSecurityObj) throws
      EJBException, RemoteException
  {
    boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                     NBSOperationLookup.DECISION_SUPPORT_ADMIN);

    if (check == false)
    {
      throw new NEDSSSystemException(
          "Sorry, you don't have permission to update a  Decision Support Mgr Algorithm Record!");
    }

    try {
    	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
    	algorithmDao.updateDSMAlgorithmStatus(algorithmUid,status);
  	} catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.updateDSMAlgorithmStatus: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);
	}
  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.updateDSMAlgorithmStatus: Exception:  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);
	}
  } //updateDSMAlgorithmStatus

 /**
 * Select and algorithm record based on UID
 * @param securityObj and UID
 * @return DSMAlgorithmDT if found
 * @throws EJBException, RemoteException
 */
 public  DSMAlgorithmDT selectDSMAlgorithm(Long dsmAlgorithmUid,NBSSecurityObj nbsSecurityObj) throws
      EJBException, RemoteException
  {
	DSMAlgorithmDT dsmAlgorithmDT;
    boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                     NBSOperationLookup.DECISION_SUPPORT_ADMIN);

    if (check == false)
    {
      throw new NEDSSSystemException(
          "Sorry, you don't have permission to retrieve a  Decision Support Mgr Algorithm Record!");
    }

    try {
    	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
    	dsmAlgorithmDT = algorithmDao.selectDSMAlgorithmDT(dsmAlgorithmUid);
  	} catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithm: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);
	}
  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithm: Exception:  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);
	}
    return dsmAlgorithmDT; //return the record
  } //selectDSMAlgorithm

/**
* Get all the records in the DSM_algorithm table
* Note: Does not return the payload
* @param none
* @return Collection of DSMAlgorithmDTs as objects
* @throws EJBException, RemoteException
*/
 public  Collection<Object> selectDSMAlgorithmList(NBSSecurityObj nbsSecurityObj) throws
      EJBException, RemoteException
  {
    ArrayList<Object> algorithmList = new ArrayList<Object> ();
    boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                     NBSOperationLookup.DECISION_SUPPORT_ADMIN);

    if (check == false)
    {
      throw new NEDSSSystemException(
          "Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List!");
    }

    try {
    	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
    	algorithmList = (ArrayList<Object> ) algorithmDao.selectDSMAlgorithmDTList();
  	} catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmList: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);
	}

  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmList: Exception:  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);

	}
    return algorithmList; //return all recs
  } //selectDSMAlgorithmList
 
 public  Map<String, String> retrieveRalatedPagesForConditionCodes(ArrayList<String> conditionCodes, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException
 {
	 try {
		 if(conditionCodes == null || conditionCodes.size() == 0)
			 return null;
		 DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
		 Map<String, String> conditionCodeRelatedPageMap = algorithmDao.retrieveRalatedPagesForConditionCodes(conditionCodes);
			return conditionCodeRelatedPageMap;
			} catch(NEDSSSystemException se) {
				logger.fatal("DSMAlgorithmEJB.retrieveRalatedPagesForConditionCodes: NEDSSSystemException:  "+ se.getMessage(), se);
				throw new NEDSSSystemException(se.getMessage(), se);

		}catch(Exception e) {
				logger.fatal("DSMAlgorithmEJB.retrieveRalatedPagesForConditionCodes: Exception:  "+ e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);

		}
 }
 
 public  String retrievePageCodeForConditionCode(String conditionCode, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException
 {
	 try {
		 if(conditionCode == null)
			 return null;
		 DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
		 String pageCode = algorithmDao.retrievePageCodeForConditionCode(conditionCode);
			return pageCode;
			} catch(NEDSSSystemException se) {
				logger.fatal("DSMAlgorithmEJB.retrievePageCodeForConditionCode: NEDSSSystemException:  "+ se.getMessage(), se);
				throw new NEDSSSystemException(se.getMessage(), se);
			}catch(Exception e) {
				logger.fatal("DSMAlgorithmEJB.retrievePageCodeForConditionCode: Exception:  "+ e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);

		}
 }

 /**
 * Get all the Case records in the DSM_algorithm table which have the condition code
 *  in the condition_list field
 * @param securityObj and ConditionCd i.e. 10020
 * @return Collection of DSMAlgorithmDTs as objects
 * @throws EJBException, RemoteException
 */
 
 public  Collection<Object> selectDSMAlgorithmForCondition(String conditionCd,NBSSecurityObj nbsSecurityObj) throws
 EJBException, RemoteException
{
ArrayList<Object> algorithmList = new ArrayList<Object> ();
boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                NBSOperationLookup.DECISION_SUPPORT_ADMIN);

if (check == false)
{
 throw new NEDSSSystemException(
     "Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List by Condition!");
}
if (conditionCd == null || conditionCd.isEmpty())
{
 throw new NEDSSSystemException(
     "Error: Condition is blank. Can not get Algorithm Record List by Condition!");
}
logger.debug("DSMAlgorithmEJB.selectDSMAlgorithmForCondition: " +conditionCd);
try {
	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
	algorithmList = (ArrayList<Object> ) algorithmDao.selectDSMAlgorithmDTForCondition(conditionCd, NEDSSConstants.PHC_236);
	} catch(NEDSSSystemException se2) {
		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmForCondition: NEDSSSystemException:  "+ se2.getMessage(), se2);
		throw new NEDSSSystemException(se2.getMessage(), se2);

}

	catch(Exception e) {
		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmForCondition: Exception:  "+ e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

}
return algorithmList; //return all recs
} //selectDSMAlgorithmForCondition
 
 public  Collection<Object> selectDSMAlgorithmForResultedTests(Collection<Object> tests,NBSSecurityObj nbsSecurityObj) throws
 EJBException, RemoteException
{
ArrayList<Object> algorithmList = new ArrayList<Object> ();
boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                NBSOperationLookup.DECISION_SUPPORT_ADMIN);

if (check == false)
{
 throw new NEDSSSystemException(
     "Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List by Condition!");
}
if (tests == null || tests.size()==0)
{
 throw new NEDSSSystemException(
     "Error: Resulted tests are blank. Can not get Algorithm Record List by Resulted tests!");
}

try {
	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
	algorithmList = (ArrayList<Object> ) algorithmDao.selectDSMAlgorithmDTForResultedTests(tests);
	} catch(NEDSSSystemException se2) {
		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmForResultedTests: NEDSSSystemException:  "+ se2.getMessage(), se2);
		throw new NEDSSSystemException(se2.getMessage(), se2);

}

	catch(Exception e) {
		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmForResultedTests: Exception:  "+ e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

}
return algorithmList; //return all recs
} //selectDSMAlgorithmDTForResultedTests


 /**
 * Update the status_cd, status_time, last_chg_user_id, last_chg_time
 *   with whatever is passed in the DT
 * @param securityObj and dsmAlgorithmDT with above set
 * @return nothing
 * @throws EJBException, RemoteException
 */

   public  void logicallyDeleteDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws
        EJBException, RemoteException
   {
      boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
                                       NBSOperationLookup.DECISION_SUPPORT_ADMIN);

      if (check == false)
      {
        throw new NEDSSSystemException(
            "Sorry, you don't have permission to logically delete a  Decision Support Mgr Algorithm Record!");
      }

      try {
      	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
      	algorithmDao.logicallyDeleteDSMAlgorithmDT(dsmAlgorithmDT);
    	}
      	catch(NEDSSSystemException se2) {
    		logger.fatal("DSMAlgorithmEJB.logicallyDeleteDSMAlgorithm: NEDSSSystemException:  "+ se2.getMessage(), se2);
    		throw new NEDSSSystemException(se2.getMessage(), se2);

    	}
      	catch(Exception e) {
      		logger.fatal("DSMAlgorithmEJB.logicallyDeleteDSMAlgorithm: Exception:  "+ e.getMessage(), e);
      		throw new EJBException(e.getMessage(), e);

      	}
    } //logicallyDeleteDSMAlgorithm


   public void ejbCreate() {
   }

   public void ejbRemove() {
   }

   public void ejbActivate() {
   }

   public void ejbPassivate() {
   }

   public void setSessionContext(SessionContext sc) {
   }

   public  Collection<Object> selectDSMAlgorithmDTList(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException
   {
	   ArrayList<Object> algorithmList = new ArrayList<Object> ();
	   boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN);
	   if (check == false)
	   {
		   throw new NEDSSSystemException(
				   "DSMAlgorithmEJB.selectDSMAlgorithmDTList:- You don't have permission to select a Decision Support Mgr Algorithm Record List by Condition! Please contact Administrator!!!");
	   }
  
	   try {
		   DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
		   algorithmList = (ArrayList<Object> ) algorithmDao.selectDSMAlgorithmDTList();
	   } catch(NEDSSSystemException se2) {
		   logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmDTList: NEDSSSystemException:  "+ se2.getMessage(), se2);
		   throw new NEDSSSystemException(se2.getMessage(), se2);

	   }

	   catch(Exception e) {
		   logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmDTList: Exception:  "+ e.getMessage(), e);
		   throw new EJBException(e.getMessage(), e);

	   }
	   return algorithmList; //return collection
   }//selectDSMAlgorithmDTList

   
   
   public  Collection<Object> selectDSMAlgorithmDTCollection(NBSSecurityObj nbsSecurityObj) throws
   EJBException, RemoteException
  {
  ArrayList<Object> algorithmList = new ArrayList<Object> ();
  boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN);

  if (check == false)
  {
   throw new NEDSSSystemException(
       "Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List by Condition!");
  }
  
  try 
  {
  	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
  	algorithmList = (ArrayList<Object> ) algorithmDao.selectDsmAlgorithmDTCollection();
  	} catch(NEDSSSystemException se2) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmDTCollection: NEDSSSystemException:  "+ se2.getMessage(), se2);
  		throw new NEDSSSystemException(se2.getMessage(), se2);

  }

  	catch(Exception e) {
  		logger.fatal("DSMAlgorithmEJB.selectDSMAlgorithmDTCollection: Exception:  "+ e.getMessage(), e);
  		throw new EJBException(e.getMessage(), e);

  }
  return algorithmList; //return all recs
  }//selectDSMAlgorithmDTCollection
  /**
   *  getDSMAlgorithmDTCollectionForConditionEvent - 
   * @param condCode
   * @param EventType - PHC236 or 11648804
   * @param nbsSecurityObj
   * @return collection of DSMAlgorithmDT
   * @throws EJBException
   * @throws RemoteException
   */
  public  Collection<Object> getDSMAlgorithmDTCollectionForConditionEvent(String condCode, String EventType, NBSSecurityObj nbsSecurityObj) throws
  EJBException, RemoteException {
		DSMAlgorithmCache dsmAlgorithmCache = new DSMAlgorithmCache();
		return dsmAlgorithmCache.getCaseAlgorithmsForConditionCollection(condCode, nbsSecurityObj);  
  } //getDSMAlgorithmDTCollectionForConditionEvent
   
  /**
   * refreshDSMAlgorithmCache - reload the cache in case it is stale
   * @param nbsSecurityObj
   * @throws EJBException
   * @throws RemoteException
   */
  public  void refreshDSMAlgorithmCache(NBSSecurityObj nbsSecurityObj) throws
  EJBException, RemoteException {
	DSMAlgorithmCache dsmAlgorithmCache = new DSMAlgorithmCache();
	dsmAlgorithmCache.refreshAlgorithmCache(nbsSecurityObj);
	  
  } //refreshDSMAlgorithmCache
  
} //class DSMAlgorithmEJB



