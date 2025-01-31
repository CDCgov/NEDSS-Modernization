package gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.ldf.importer.SubformImportData;
import gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;

import java.rmi.*;
import java.util.*;

import javax.ejb.*;

/**
 *
 * <p>Title: SubformMetaDataEJB</p>
 * <p>Description: This EJB serves the client request against querying and manupulate subform data</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Narendra Mallela
 * @version 1.0
 */
public class SubformMetaDataEJB implements SessionBean {

	//For logging
	static final LogUtils logger =
		new LogUtils(SubformMetaDataEJB.class.getName());
	private CustomSubformMetadataDAO subformMetadataDAO;
	private long subformUID;

	public SubformMetaDataEJB() {
	}

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

	/**
	 *
	 * @param subform
	 * @param secObj
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Long setSubformMetaData(
		CustomSubformMetadataDT subformMetaDataDT,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}

			subformUID = subformMetadataDAO.create(subformMetaDataDT);

			return new Long(subformUID);
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSDAOSysException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSSystemException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}

	/**
	 *
	 * @param businessObjectName
	 * @param secObj
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Collection<Object>  getSubformMetaDataByBusinessObject(
		String businessObjectName,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}
			return subformMetadataDAO.select(businessObjectName);
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}

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
		String businessObjectName,
		String conditionCode,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}
			return subformMetadataDAO.select(businessObjectName, conditionCode);
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}

	/**
	 *
	 * @param subformMetaDataUid
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public CustomSubformMetadataDT getSubformMetaData(
		Long subformMetaDataUid,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}
			Object obj = subformMetadataDAO.select(subformMetaDataUid);
			if (obj != null) {
				return (CustomSubformMetadataDT) obj;
			}

			return null;
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param uidList
	 * @param secObj
	 * @return
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Collection<Object>  getSubformMetaDataByUids(
		Collection<Object>  uidList,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			Collection<Object>  returnList = new ArrayList<Object> ();
			//add security once defined !!!
			for (Iterator anIter = uidList.iterator(); anIter.hasNext();) {
				Long uid = (Long) anIter.next();
				CustomSubformMetadataDT dt = getSubformMetaData(uid, secObj);
				if (dt != null) {
					returnList.add(dt);
				}
			}
			return returnList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}

	public Collection<Object>  getSubformMetaDataByUidsAndCondition(
		Collection<Object>  uidList,
		String conditionCd,
		NBSSecurityObj secObj)
		throws RemoteException, NEDSSAppException {

		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			Collection<Object>  returnList = new ArrayList<Object> ();
			//add security once defined !!!
			for (Iterator anIter = uidList.iterator(); anIter.hasNext();) {
				Long uid = (Long) anIter.next();
				CustomSubformMetadataDT dt = getSubformMetaData(uid, secObj);
				if (conditionCd == null
					&& dt != null
					&& dt.getConditionCd() == null) {
					returnList.add(dt);
				} else if (	conditionCd != null
					&& dt != null
					&& dt.getConditionCd() != null
					&& dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
					returnList.add(dt);
				}
			}
			return returnList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}

	}
	
	/**
	 * getLDFPageSetData method returns a HashMap<Object,Object> comprising LDFPageSetId as Key and ShortDescTxt as value
	 * @return java.util.HashMap
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public HashMap<?,?> getLDFPageSetData(NBSSecurityObj secObj) throws RemoteException,
			NEDSSAppException {
		
		try {
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}
			Object obj = subformMetadataDAO.selectPageSet();
			if (obj != null) {
				return (HashMap<?,?>) obj;
			}

			return null;
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}	
		
	}
	
	/**
	 * getMaxImportVersion returns the max. importVersion Number for importing CDF/CustomSubforms
	 * @param secObj
	 * @return java.lang.Long
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public Long getMaxImportVersion(NBSSecurityObj secObj) throws RemoteException,
	NEDSSAppException {
		
		try {
			long maxVersion = 0;
			
			if (secObj == null) {
				logger.info("nbsSecurityObj is null ");
				throw new NEDSSAppException("NBSSecurityObj is null");
			}
			//add security once defined !!!

			if (subformMetadataDAO == null) {
				subformMetadataDAO =
					(CustomSubformMetadataDAO) NEDSSDAOFactory.getDAO(
						"gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO");
			}
			maxVersion = subformMetadataDAO.selectMaxImportVersion();
			
			return new Long(maxVersion);
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}
	

} //LDFMetaDataEJB
