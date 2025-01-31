package gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean;

/**
 * Title: Session EJB Class using LDFMetaData
 * Description: This class is the EJB for LDFMetaData Session EJB
 * Copyright:    Copyright (c) 2003
 * Company:csc
 * @author:
 */
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;

import java.rmi.*;
import java.util.*;

import javax.ejb.*;
import javax.rmi.PortableRemoteObject;

import java.sql.Timestamp;

/**
 *
 */
public class LDFMetaDataEJB
    implements SessionBean {

  //For logging
  static final LogUtils logger = new LogUtils(LDFMetaDataEJB.class.getName());
  private long ldfUID;
  private StateDefinedFieldMetaDataDAOImpl sdfMetaDataDAO;
  private NBSUIMetaDataDAOImpl nbsUIMetaDataDAO;
	
  public LDFMetaDataEJB() {
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

  public Collection<Object>  getAllLDFMetaData(NBSSecurityObj inNbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {
    try {
		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		
		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		}

		//
		
		ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.selectAllLDFMetaData();

		//System.out.println("40000001 = " + ldfMetaDataColl.size());
		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  public Collection<Object>  getLDFMetaDataByPageId(String ldfPageId,
                                           NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {

    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		//System.out.println("40000000 = " + pageID);
		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		if (ldfPageId == null) {
		  throw new NEDSSSystemException(
		      "Cannot retrieve Meta Data for a null ldfUID");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		}
		ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.loadMetaData(
		    ldfPageId);

		//System.out.println("40000001 = " + ldfMetaDataColl.size());
		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("LDFPageID: " + ldfPageId + ", " + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  public Collection<Object>  getLDFMetaDataByBusObjNmAndConditionCd(String busObjNm, Long businessObjUID,
      String condCd, Boolean includeNonCondLDF, NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		//System.out.println("40000000 = " + pageID);
		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		if (condCd == null && busObjNm == null) {
		  throw new NEDSSSystemException(
		      "Cannot retrieve Meta Data when busObjm AND condCd are null!");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");
		}
		boolean includeNonCondLDFPri = includeNonCondLDF.booleanValue();
		if (includeNonCondLDFPri) {
		  ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.
		      selectMetaDataByBusObjUidAndBusObjNmAndConditionInclusive(businessObjUID, busObjNm, condCd);
		}
		else {
		  ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.
		      selectMetaDataByBusObjUidAndBusObjNmAndCondition(businessObjUID,busObjNm, condCd);

		  //System.out.println("40000001 = " + ldfMetaDataColl.size());
		}
		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  public Collection<Object>  getLDFMetaDataByBusObjNm(String busObjNm, Long busObjUID,
                                             NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		//System.out.println("40000000 = " + pageID);
		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		if (busObjNm == null) {
		  throw new NEDSSSystemException(
		      "Cannot retrieve Meta Data with busObjm value null!");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		}
		ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.selectMetaDataByBusObjUidAndBusObjNm(busObjUID, busObjNm);

		//System.out.println("40000001 = " + ldfMetaDataColl.size());
		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  /**
   *
   * @param classCd - is a state specific or CDC specific identifier
   * @param activeInd - indicator for display of LDFs on the UI
   * @param nbsSecurityObj - security Object
   * @return Collection<Object>  of StateDefinedFieldMetaData based on the specific condition.
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public Collection<Object>  getLDFMetaDataByClassCdAndActiveInd(String classCd,
      String activeInd, NBSSecurityObj nbsSecurityObj) throws RemoteException,
      EJBException, NEDSSSystemException {

    try {
		if (nbsSecurityObj == null) {
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		if (classCd == null || activeInd == null) {
		  throw new NEDSSSystemException(
		      "Cannot retrieve LDFMetaData without classCd or activeInd values");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO = (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.
		      getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		}
		ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.
		    selectMetaDataByClassCdAndActiveInd(classCd, activeInd);

		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  }

  /**
   *
   * @param classCd - is a state specific or CDC specific identifier
   * @param nbsSecurityObj - security Object
   * @return Collection<Object>  of StateDefinedFieldMetaData based on the specific condition.
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public Collection<Object>  getLDFMetaDataByClassCd(String classCd,
                                            NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {

    try {
		if (nbsSecurityObj == null) {
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		Collection<Object>  ldfMetaDataColl = new ArrayList<Object> ();
		if (classCd == null) {
		  throw new NEDSSSystemException(
		      "Cannot retrieve LDFMetaData without classCd value");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO = (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.
		      getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		}
		ldfMetaDataColl = (ArrayList<Object> ) sdfMetaDataDAO.selectMetaDataByClassCd(
		    classCd);

		return ldfMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  }

  /**
   *
   */
  public void setLDFMetaData(Collection<Object> LDFMetaDataColl,
                             NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, CreateException, NEDSSSystemException,
      NEDSSConcurrentDataException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}
		/**
		 * Check the user permission for LDF Administration
		 */
		boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
		                                              NBSOperationLookup.
		                                              LDFADMINISTRATION);

		if (check1 == false) {
		  logger.error("don't have permission for LDF Administration");
		  throw new NEDSSSystemException(
		      "don't have permission for LDF Administration");
		}
		else if (check1 == true) {

		  if (LDFMetaDataColl != null) {
		   Iterator<Object>  ldfIter = null;
		    if (sdfMetaDataDAO == null) {
		      sdfMetaDataDAO =
		          (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		          "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");
		    }
		    for (ldfIter = LDFMetaDataColl.iterator(); ldfIter.hasNext(); ) {
		      StateDefinedFieldMetaDataDT sdfMetaDataDT = (
		          StateDefinedFieldMetaDataDT) ldfIter.next();
		      if (sdfMetaDataDT.isItNew()) {
		        sdfMetaDataDT.setRecordStatusCd(NEDSSConstants.
		                                        LDF_CREATE_RECORD_STATUS_CD);
		        ldfUID = sdfMetaDataDAO.create(sdfMetaDataDT);
		        sdfMetaDataDT.setLdfUid(new Long(ldfUID));
		      }
		      if (sdfMetaDataDT.isItDirty()) {
		        sdfMetaDataDT.setRecordStatusCd(NEDSSConstants.
		                                        LDF_UPDATE_RECORD_STATUS_CD);
		        sdfMetaDataDAO.store(sdfMetaDataDT);
		      }
		      if (sdfMetaDataDT.isItDelete()) {
		        sdfMetaDataDAO.remove(sdfMetaDataDT.getLdfUid());
		      }
		    }
		  }
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  } //setLDFMetaData

  /**
       * Sets Record Status for each DT to NEDSSConstants.LDF_METADATA_RECORD_STATUS_CD
   */
  public void setNNDLdfProcess(Collection<Object> LDFMetaDataColl,
                               NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, CreateException, NEDSSSystemException,
      NEDSSConcurrentDataException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}
		/**
		 * Check the user permission for LDF Administration
		 */
		boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
		                                              NBSOperationLookup.
		                                              LDFADMINISTRATION);

		if (check1 == false) {
		  logger.error("don't have permission for LDF Administration");
		  throw new NEDSSSystemException(
		      "don't have permission for LDF Administration");
		}
		else if (check1 == true) {

		  if (LDFMetaDataColl != null) {
		   Iterator<Object>  ldfIter = null;
		    if (sdfMetaDataDAO == null) {
		      sdfMetaDataDAO =
		          (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		          "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");
		    }
		    if (nbsUIMetaDataDAO == null) {
		    	nbsUIMetaDataDAO =
		            (NBSUIMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		            "gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl");
		    }
		    Timestamp statusTime = new Timestamp(new Date().getTime());
		    for (ldfIter = LDFMetaDataColl.iterator(); ldfIter.hasNext(); ) {
		      StateDefinedFieldMetaDataDT sdfMetaDataDT = (
		          StateDefinedFieldMetaDataDT) ldfIter.next();
		      
		      if (sdfMetaDataDT.getNbsQuestionInd() != null && sdfMetaDataDT.getNbsQuestionInd().equals(NNDConstantUtil.YES)) {
		          //Get and increment version control number
		    	  Integer versionCtrlNbr = new Integer(sdfMetaDataDT.getVersionCtrlNbr().intValue() + 1);
		    	  NbsUiMetadataDT uiMetaDT = new NbsUiMetadataDT();
		    	  uiMetaDT.setNbsUiMetadataUid(sdfMetaDataDT.getLdfUid());
		    	  uiMetaDT.setLdfStatusCd(NNDConstantUtil.LDF_PROCESSED);
		    	  uiMetaDT.setLdfStatusTime(statusTime);
		    	  uiMetaDT.setVersionCtrlNbr(versionCtrlNbr);
		    	  nbsUIMetaDataDAO.updateNBSUiMetadataLDFStatus(uiMetaDT);
		      }
		      else {
		    	  sdfMetaDataDT.setRecordStatusCd(NNDConstantUtil.LDF_PROCESSED);
		      
		          sdfMetaDataDT.setRecordStatusTime(statusTime);
		          sdfMetaDataDT.setItNew(false);
		          sdfMetaDataDT.setItDirty(true);
		          sdfMetaDataDT.setItDelete(false);
		
		          sdfMetaDataDAO.store(sdfMetaDataDT);
		      }
		    }

		  }
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  } //setLDFMetaDataRecordStatus

  /**
   *
   */
  public void deleteLDFMetaData(Long ldfUid, NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, CreateException, NEDSSSystemException,
      NEDSSConcurrentDataException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		if (ldfUid == null) {
		  throw new NEDSSSystemException(
		      "Cannot delete Meta Data for a null ldfUID");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");

		  //System.out.println("40000003 = " + ldfUid);
		}
		sdfMetaDataDAO.remove(ldfUid);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  public Map<Object,Object> getMetaDataCount(Collection<Object> pageIdColl,
                              NBSSecurityObj nbsSecurityObj) throws
      RemoteException, EJBException, NEDSSSystemException {
    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

   Iterator<Object>  iter = pageIdColl.iterator();
		String pageId = null;
		Integer count = null;
		Map<Object,Object> countMap = new HashMap<Object,Object>();
		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");
		}
		
		while (iter.hasNext()) {
		  pageId = (String) iter.next();
		  count = sdfMetaDataDAO.selectCountByPageId(pageId);
		  countMap.put(pageId, count);
		}
		return countMap;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }

  public Collection<Object>  testGetLDFCollection(Long busObjectUid,
                                         String conditionCode,
                                         NBSSecurityObj nbsSecurityObj) throws
      RemoteException, NEDSSAppException, NEDSSConcurrentDataException {
    try {
		LDFHelper lDFHelper = LDFHelper.getInstance();

		Collection<Object>  actualReturn = lDFHelper.getLDFCollection(busObjectUid,
		    conditionCode, nbsSecurityObj);
		return actualReturn;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("Conditon Code: " + conditionCode + ", " + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  }

  public void testSetLDFCollection(
      Collection<Object>  stateDefinedFieldDataCollection,
      String businessObjNm,
      Integer versionCtrlNbr,
      Long businessObjUid, NBSSecurityObj nbsSecurityObj) throws
      RemoteException, NEDSSAppException, NEDSSConcurrentDataException {

    try {
		LDFHelper lDFHelper = LDFHelper.getInstance();
		lDFHelper.setLDFCollection(stateDefinedFieldDataCollection, null, businessObjNm,
		                           versionCtrlNbr, businessObjUid, nbsSecurityObj);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

  }

  /**
   *
   * @param subformUid
   * @param nbsSecurityObj
   * @return
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public Collection<Object>  getLDFMetaDataBySubformUid(Long subformUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException {

    try {
		if (nbsSecurityObj == null) {
		  logger.info("nbsSecurityObj is null ");
		  throw new NEDSSSystemException("nbsSecurityObj is null");
		}

		if (subformUid == null) {
		  throw new NEDSSSystemException(
		      "Cannot find Meta Data for a null subformUid");
		}

		if (sdfMetaDataDAO == null) {
		  sdfMetaDataDAO =
		      (StateDefinedFieldMetaDataDAOImpl) NEDSSDAOFactory.getDAO(
		      "gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.StateDefinedFieldMetaDataDAOImpl");
		}

		return sdfMetaDataDAO.selectMetaDataBySubformUid(subformUid);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("SubFormUid: " + subformUid + ", " + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
  }
  
} //LDFMetaDataEJB
