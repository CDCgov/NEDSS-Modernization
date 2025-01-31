package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean;


import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ejb.EJBException;
import java.rmi.RemoteException;


import javax.ejb.EJBObject;

/**
 * Title: DSMAlgorithm
 * Description: Decision Support Algorithm High Level Services Interface
 * Copyright:    Copyright (c) 2011
 * Company: CSC
 * @author Greg Tucker
 * @version 1.0
 */
public interface DSMAlgorithm  extends EJBObject {
	 public  Long createDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Long updateDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  DSMAlgorithmDT selectDSMAlgorithm(Long dsmAlgorithmUid,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> selectDSMAlgorithmList(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> selectDSMAlgorithmForCondition(String conditionCd,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  void logicallyDeleteDSMAlgorithm(DSMAlgorithmDT dsmAlgorithmDT,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  void updateDSMAlgorithmStatus(Long algorithmUid, String status, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Map<String, String> retrieveRalatedPagesForConditionCodes(ArrayList<String> conditionCodes, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  String retrievePageCodeForConditionCode(String conditionCode, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> selectDSMAlgorithmForResultedTests(Collection<Object> tests,NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> selectDSMAlgorithmDTList(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> selectDSMAlgorithmDTCollection(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
	 public  Collection<Object> getDSMAlgorithmDTCollectionForConditionEvent(String condCode, String EventType, NBSSecurityObj nbsSecurityObj) throws  EJBException, RemoteException;
	 public  void refreshDSMAlgorithmCache(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException;
}



