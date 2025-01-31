package gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean;

/**
 * Title: SubformMetaData
 * Description: This class is the Remote interface for SubformMetaData session bean.
 * Copyright:    Copyright (c) 2004
 * Company:csc
 * @author:
 */

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;

/**
 *  
 */
public interface SubformMetaData extends EJBObject {

	/**
	 * 
	 * @param subform
	 * @param secObj
	 * @return subformUid
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Long setSubformMetaData(CustomSubformMetadataDT subformMetaDataDT,
			NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

	/**
	 * 
	 * @param businessObjectName
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Collection<Object>  getSubformMetaDataByBusinessObject(
			String businessObjectName, NBSSecurityObj secObj)
			throws RemoteException, NEDSSAppException;

	/**
	 * 
	 * @param businessObjectName
	 * @param conditionCode
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Collection<Object>  getSubformMetaDataByBusinessObjAndCondition(
			String businessObjectName, String conditionCode,
			NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

	/**
	 * 
	 * @param uidList
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Collection<Object>  getSubformMetaDataByUids(Collection<Object>  uidList,
			NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

	public Collection<Object>  getSubformMetaDataByUidsAndCondition(Collection<Object>  uidList,
			String conditionCd, NBSSecurityObj secObj) throws RemoteException,
			NEDSSAppException;

	/**
	 * 
	 * @param subformMetaDataUid
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public CustomSubformMetadataDT getSubformMetaData(Long subformMetaDataUid,
			NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

	/**
	 * getLDFPageSetData method returns a HashMap<Object,Object> comprising LDFPageSetId as Key and ShortDescTxt as value
	 * @return java.util.HashMap
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public HashMap<Object,Object> getLDFPageSetData(NBSSecurityObj secObj) throws RemoteException,
			NEDSSAppException;

	/**
	 * getMaxImportVersion returns the max. importVersion Number for importing CDF/CustomSubforms
	 * @param secObj
	 * @return java.lang.Long
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Long getMaxImportVersion(NBSSecurityObj secObj) throws RemoteException,
	NEDSSAppException;
}