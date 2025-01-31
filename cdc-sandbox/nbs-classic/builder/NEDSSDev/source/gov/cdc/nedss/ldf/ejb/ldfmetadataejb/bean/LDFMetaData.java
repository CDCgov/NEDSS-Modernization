package gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean;

/**
 * Title: LDFMetaData
 * Description: This class is the Remote interface for LDFMetaData session bean.
 * Copyright:    Copyright (c) 2003
 * Company:csc
 * @author:
 */

import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;

/**
 *
 */
public interface LDFMetaData extends EJBObject {
	public Collection<Object>  getLDFMetaDataByPageId(String ldfPageId, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

	public Collection<Object>  getLDFMetaDataByBusObjNmAndConditionCd(String busObjNm, Long businessObjUID,String ConditionCd, Boolean includeNonCondLDF, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

	public Collection<Object>  getLDFMetaDataByBusObjNm(String busObjNm, Long businessObjUID, NBSSecurityObj nbsSecurityObj ) throws RemoteException, EJBException, NEDSSSystemException;


	public void setLDFMetaData(Collection<Object> LDFMetaDataColl, NBSSecurityObj nbsSecurityObj)
		throws RemoteException, EJBException, CreateException, NEDSSSystemException, NEDSSConcurrentDataException;

        public void setNNDLdfProcess(Collection<Object> LDFMetaDataColl, NBSSecurityObj nbsSecurityObj)
		throws RemoteException, EJBException, CreateException, NEDSSSystemException, NEDSSConcurrentDataException;

	public void deleteLDFMetaData(Long LDFUid, NBSSecurityObj nbsSecurityObj)
		throws RemoteException, EJBException, CreateException, NEDSSSystemException, NEDSSConcurrentDataException;

	/*Accepts a collection of pageId Strings and returns a map with the strings as keys and the count as the value */
	public Map<Object,Object> getMetaDataCount(Collection<Object> pageIdColl, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

	public Collection<Object>  testGetLDFCollection(Long busObjectUid, String conditionCode,NBSSecurityObj nbsSecurityObj)
		throws RemoteException, NEDSSAppException, NEDSSConcurrentDataException;

	public void testSetLDFCollection(
		Collection<Object>  stateDefinedFieldDataCollection,
		String businessObjNm,
		Integer versionCtrlNbr,
		Long businessObjUid,NBSSecurityObj nbsSecurityObj)
		throws RemoteException, NEDSSAppException, NEDSSConcurrentDataException;

    public Collection<Object>  getAllLDFMetaData(NBSSecurityObj inNbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

    public Collection<Object>  getLDFMetaDataByClassCdAndActiveInd(String classCd, String activeInd, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;
    public Collection<Object>  getLDFMetaDataByClassCd(String classCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;
    public Collection<Object>  getLDFMetaDataBySubformUid(Long subformUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

} //LDFMetaData