package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util;

import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao.DSMAlgorithmDaoImpl;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.PatternSyntaxException;

import javax.ejb.EJBException;

/**
 * Title: DSMAlgorithmCache helper class
 * <p>Description:
 * DSMAlgorithmCache is a Singleton class for Algorithm Caching.
 *
 * </p>
 * Copyright (c) 2016
 * Company: CSRA
 * @author Greg Tucker
 * @version 1.0
 */

public class DSMAlgorithmCache {
	static final LogUtils logger = new LogUtils(DSMAlgorithmCache.class.getName());

	private static HashMap<String,Collection<Object>> cachedCaseAlgorithmsByConditionCollection = null;
	private static ArrayList<Object> cachedAlgorithm  = null;

	private static DSMAlgorithmCache algorithmSingleton = new DSMAlgorithmCache();
	private static boolean algorithmsPresent = true;


	/**
	 * Returns single instance of the class
	 * @return
	 */
	public static DSMAlgorithmCache getInstance()
	{
		return algorithmSingleton;
	}


	/**
	 * getAlgorithmDTCollection - Retrieve the algorithm collection.
	 * Called when rebuilding the cache.
	 * @param nbsSecurityObj
	 * @return
	 * @throws EJBException
	 * @throws RemoteException
	 */
	private  static Collection<Object> getAlgorithmDTCollection(NBSSecurityObj nbsSecurityObj) throws
	EJBException, RemoteException
	{
		
		boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN);
		if (check == false)
		{
			throw new NEDSSSystemException(
					"DSMAlgorithmCache.getAlgorithmDTCollection Sorry, you don't have permission to select a Decision Support Mgr Algorithm Records");
		}
		ArrayList<Object> algorithmList = new ArrayList<Object> ();
		try 
		{
			DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
			algorithmList = (ArrayList<Object>) algorithmDao.selectDsmAlgorithmDTCollection();
		} catch(NEDSSSystemException se2) {
			String errorString = "DSMAlgorithmCache.getAlgorithmDTCollection System Exception:  "+ se2.getMessage();
			logger.error(errorString);
			throw new EJBException(errorString, se2);
		}
		catch(Exception e) {
			String errorString = "DSMAlgorithmCache.getAlgorithmDTCollection General Exception:  "+ e.getMessage();
			logger.error(errorString);
			throw new EJBException(errorString, e);
		}

		if (algorithmList == null || algorithmList.isEmpty())
			algorithmsPresent = false;
		
		//Iterate thru list and build cache(s)
		Iterator<Object> iterator = algorithmList.iterator();
		while(iterator.hasNext()){
			DSMAlgorithmDT algorithmDT = (DSMAlgorithmDT)iterator.next();
			//skip inactive and case reports
			if (algorithmDT.getStatusCd() != null && algorithmDT.getStatusCd().contentEquals(NEDSSConstants.INACTIVE))
				continue; //skip inactive
			addAlgorithmToCaseAlgorithmsByConditionCollection(algorithmDT);
			//helper class DSMLabMatchHelper will assist with algorithm matching
		} //hasNext
		return algorithmList;
	}

	/**
	 * addAlgorithmToCaseAlgorithmsByConditionCollection - For each condition supported by
	 * the algorithm, add an entry in the condition/algorithm map cachedCaseAlgorithmsByConditionCollection.
	 * @param algorithmDT
	 */
	private static void addAlgorithmToCaseAlgorithmsByConditionCollection(
			DSMAlgorithmDT algorithmDT) {

		if (algorithmDT.getConditionList() == null || algorithmDT.getConditionList().isEmpty() )
			return;
		if (algorithmDT.getEventType() != null && !algorithmDT.getEventType().contains(NEDSSConstants.PHC_236)) 
			return; //only add case algorithms
		String condList = algorithmDT.getConditionList();
		if (condList != null && !condList.contains("^")) {
			String condCd = condList.trim();
			if (condCd != null && !condCd.isEmpty()) {
				if (cachedCaseAlgorithmsByConditionCollection.containsKey(condCd)) {
					Collection<Object> algoColl = cachedCaseAlgorithmsByConditionCollection.get(condCd);
					algoColl.add((Object)algorithmDT);
					//cachedCaseAlgorithmsByConditionCollection.put(condCd, algoColl);
				} else {
					Collection<Object> algColl = new ArrayList<Object>();
					algColl.add(algorithmDT);
					cachedCaseAlgorithmsByConditionCollection.put(condCd, algColl);
				}
			}
		} else {
			String[] condToken = null;
			try {
				condToken = condList.split("\\^"); //caret special char in regex
			} catch (PatternSyntaxException ex) {
			    logger.error("AlgorithmToCaseAlgorithmsByConditionCollection.Condition Parsing Error is " + ex.getMessage());
			    return;
			}

			for (int i = 0; i < condToken.length; i++) {
					String condCd = condToken[i].trim();
					if (condCd != null && !condCd.isEmpty()) {
						if (cachedCaseAlgorithmsByConditionCollection.containsKey(condCd)) {
							Collection<Object> algoColl = cachedCaseAlgorithmsByConditionCollection.get(condCd);
							algoColl.add((Object)algorithmDT);
							//cachedCaseAlgorithmsByConditionCollection.put(condCd, algoColl);
						} else {
							Collection<Object> algColl = new ArrayList<Object>();
							algColl.add(algorithmDT);
							cachedCaseAlgorithmsByConditionCollection.put(condCd, algColl);
						}

					} //if cond
				} //for
		} //else
	}


	/**
	 * Rebuild the cache. Called at the start of batch jobs to refresh the cache.
	 * @param nbsSecurityObj
	 */
	public static void refreshAlgorithmCache(NBSSecurityObj nbsSecurityObj) {
		cachedCaseAlgorithmsByConditionCollection = new HashMap<String,Collection<Object>>();
		try {
			cachedAlgorithm = (ArrayList) getAlgorithmDTCollection(nbsSecurityObj);
		} catch (EJBException | RemoteException e) {
			logger.error("DSMAlgorithmCache:refreshAlgorithmCache had error " +e.getMessage());
			e.printStackTrace();
		}
		return;
	}
	/**
	 * Get the arrayList of algorithms for a condition (may be more than one if multiple algorithms for condition)
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @return collection of DSMAlgorithmDT or empty collection
	 */
	public static Collection<Object> getCaseAlgorithmsForConditionCollection(String conditionCd,
			NBSSecurityObj nbsSecurityObj) {
		logger.debug("DSMALgorithmCache.getCaseAlgorithmsForConditionCollectionmessage(" + conditionCd + "called ..");
		
		//failsafe in case this call made before refresh cache called 
		if (cachedAlgorithm == null && algorithmsPresent)
			refreshAlgorithmCache(nbsSecurityObj);
		
		//get the list of algorithms, if any, for the condition code
		if (cachedCaseAlgorithmsByConditionCollection != null && cachedCaseAlgorithmsByConditionCollection.containsKey(conditionCd) )
			return(cachedCaseAlgorithmsByConditionCollection.get(conditionCd));
		else
			return (new ArrayList<Object>());
	}

} //class
