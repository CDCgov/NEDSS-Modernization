package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean;

/**
 * NbsPHCRDocumentEJB: EJB that defines Service within NBS framework to import PHCR Document. 
 * The intent of this EJB is to create NBS Objects from the predefined PHCR Document.  
 * EdxPHCRDocumentEJB code in updated to reflect the Performance related changes to process thousands of ELRs per hour 
* @author: Pradeep Sharma, Paul Gillen
 * @Company: SAIC
 * @since:Release 4.3
 * Updated to fix ELR import performance issues
 * @author Pradeep.Sharma
 * @Updated Release 5.4.6/6.0.6
 
 */
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindOrganizationDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindPersonDAOImpl;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxLabHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.EdxCDAConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.AutoLabDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.AutoLabInvDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.HL7ELRValidateDecisionSupport;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPatientMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;


public class EdxPHCRDocumentEJB implements SessionBean {

	private static final long serialVersionUID = 1L;
	private SessionContext sessionCtx = null;
	static final LogUtils logger = new LogUtils(EdxPHCRDocumentEJB.class.getName());
	static final Map<String, String> phinToNBSCodeMap = new HashMap<String, String>();
	
	@Override
	public void ejbActivate() throws EJBException, RemoteException {
	}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
	}

	@Override
	public void ejbRemove() throws EJBException, RemoteException {
	}

	@Override
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		try {
			sessionCtx = ctx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.setSessionContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public void ejbCreate() throws java.rmi.RemoteException, javax.ejb.CreateException {
	}

	public EdxRuleAlgorothmManagerDT createNbsEventsFromPHCR(String datamigration,
			NbsInterfaceDT nbsInterfaceDT, NBSSecurityObj nbsSecurityObj)
			throws EJBException, RemoteException, NEDSSAppException {
		EdxPHCRHelper eph = new EdxPHCRHelper();
		EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT =null;
		try{
		 edxRuleAlgorothmManagerDT = eph
				.createDocumentInvestigation(datamigration,nbsInterfaceDT, nbsSecurityObj,
						sessionCtx);
		if(edxRuleAlgorothmManagerDT.getPHCUid() != null && edxRuleAlgorothmManagerDT.getPHCUid().longValue()>0)
			eph.createDocumentInterviews(edxRuleAlgorothmManagerDT, nbsSecurityObj);
		
		if (edxRuleAlgorothmManagerDT.isContactRecordDoc()
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap() != null
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap().get(
								EdxCDAConstants.SUBJECT_INV) != null && !edxRuleAlgorothmManagerDT.isUpdatedDocument())
			
			eph.createDocumentContactRecord(edxRuleAlgorothmManagerDT,
					nbsSecurityObj);
		NBSDocumentDT nbsDocumentDT = edxRuleAlgorothmManagerDT.getDocumentDT();
		if (edxRuleAlgorothmManagerDT.isLabReportDoc() && nbsDocumentDT != null
				&& nbsDocumentDT.getProgAreaCd() != null 
				&& nbsDocumentDT.getJurisdictionCd() != null
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap() != null
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap().size()>0 && !edxRuleAlgorothmManagerDT.isUpdatedDocument())
			
			eph.createDocumentLabReport(edxRuleAlgorothmManagerDT,
					nbsSecurityObj);
		
		if (edxRuleAlgorothmManagerDT.isMorbReportDoc() && nbsDocumentDT != null
				&& nbsDocumentDT.getProgAreaCd() != null 
				&& nbsDocumentDT.getJurisdictionCd() != null
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap() != null
				&& edxRuleAlgorothmManagerDT
						.geteDXEventProcessCaseSummaryDTMap().size()>0 && !edxRuleAlgorothmManagerDT.isUpdatedDocument())
			
			eph.createDocumentMorbReport(edxRuleAlgorothmManagerDT,
					nbsSecurityObj);
		
		edxRuleAlgorothmManagerDT.setDocumentDT(null);
		}catch(Exception ex){
			logger.fatal("EdxPHCRDocumentEJB.createNbsEventsFromPHCR: " + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
		return edxRuleAlgorothmManagerDT;
	}

	public Collection<Object> labToConditionMapping(NBSSecurityObj nbsSecurityObj) {
		Collection<Object> coll = new ArrayList<Object>();
		try {
			AutoLabDAO autoLabDAO = new AutoLabDAO();
			coll = autoLabDAO.getUnassociatedLabs();

		} catch (NEDSSSystemException e) {
			logger.fatal("EdxPHCRDocumentEJB.labToConditionMapping: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return coll;
	}

	public Collection<Object> ConvertLabToInvestigation(Map<Object, Object> map, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		try {
			String sqlQuery = "";
			Collection<Object> autoLabInvDTColl = new ArrayList<Object>();
			ArrayList<Object> autoLabInvDTCollection = new ArrayList<Object>();
			if (map != null) {
				Set<Object> set = map.keySet();
				Iterator<Object> iter = set.iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					if (key.equalsIgnoreCase("all")) {
						sqlQuery = 
								"SELECT auto_lab_inv_uid \"autoLabInvUid\", condition_code \"conditionCode\", order_observation_local_id \"orderObservationLocalId\", order_observation_uid \"orderObservationUid\", result_observation_uid \"resultObservationUid\", public_health_case_uid \"publicHealthCaseUid\", refresh_time \"refreshTime\", inv_created \"invCreated\", comment \"comment\" "
								+ "FROM auto_lab_inv where public_health_case_uid is null";
					} else if (key.equalsIgnoreCase("Condition")) {
						sqlQuery = "SELECT auto_lab_inv_uid \"autoLabInvUid\", condition_code \"conditionCode\", order_observation_local_id \"orderObservationLocalId\", order_observation_uid \"orderObservationUid\", result_observation_uid \"resultObservationUid\", public_health_case_uid \"publicHealthCaseUid\", refresh_time \"refreshTime\", inv_created \"invCreated\", comment \"comment\" "
								+ "FROM auto_lab_inv where public_health_case_uid is null and condition_code =?";
						autoLabInvDTCollection.add(map.get(key));
					} else if (key.equalsIgnoreCase("uid")) {
						sqlQuery = "SELECT auto_lab_inv_uid \"autoLabInvUid\", condition_code \"conditionCode\", order_observation_local_id \"orderObservationLocalId\", order_observation_uid \"orderObservationUid\", result_observation_uid \"resultObservationUid\", public_health_case_uid \"publicHealthCaseUid\", refresh_time \"refreshTime\", inv_created \"invCreated\", comment \"comment\" "
								+ "FROM auto_lab_inv where public_health_case_uid is null and order_observation_uid =?";
						autoLabInvDTCollection.add(map.get(key));
					}
				}
				AutoLabDAO autoLabDAO = new AutoLabDAO();
				Collection<Object> coll = autoLabDAO.getAutoLabInvDTCollection(sqlQuery, autoLabInvDTCollection);
				Iterator<Object> it = coll.iterator();

				int i = 0;
				boolean multipleConditionExists = false;
				AutoLabInvDT oldAutoLabInvDT = null;
				while (it.hasNext()) {
					try {
						AutoLabInvDT autoLabInvDT = (AutoLabInvDT) it.next();
						if (autoLabInvDT!=null && autoLabInvDT.getPublicHealthCaseUid() != null) {
							autoLabInvDT.setComment("Error");
							autoLabInvDTColl.add(autoLabInvDT);
							return autoLabInvDTColl;
						}

						if (i == 0) {
							oldAutoLabInvDT = autoLabInvDT;
						} else {
							if (oldAutoLabInvDT != null && autoLabInvDT.getOrderObservationUid().longValue() != oldAutoLabInvDT.getOrderObservationUid().longValue()) {
								if (!multipleConditionExists) {
							
									oldAutoLabInvDT.setInvCreated("True");
									oldAutoLabInvDT.setComment("Public Health Case successfully created!");
									updateAutoLabInvDT(oldAutoLabInvDT, nbsSecurityObj);
									autoLabInvDTColl.add(oldAutoLabInvDT);
									oldAutoLabInvDT = autoLabInvDT;
								} else {
									oldAutoLabInvDT.setInvCreated("False");
									oldAutoLabInvDT.setComment("Cannot Create Public Health Case as Resulted Tests are not linked to same condition!!!");
									autoLabInvDTColl.add(oldAutoLabInvDT);
									autoLabDAO.updateAutoLabInvDT(oldAutoLabInvDT);
								}
								multipleConditionExists = false;
							} else {
								if (autoLabInvDT!=null && oldAutoLabInvDT!=null && autoLabInvDT.getConditionCode().equals(oldAutoLabInvDT.getConditionCode())) {
									oldAutoLabInvDT.setInvCreated("False");
									oldAutoLabInvDT.setComment("Cannot Create Public Health Case as multiple Resulted Tests of same ordered tests are not linked to same condition!!!");
									updateAutoLabInvDT(oldAutoLabInvDT, nbsSecurityObj);
									autoLabInvDTColl.add(oldAutoLabInvDT);
									multipleConditionExists = true;
								} else {
									if(oldAutoLabInvDT!=null)
										logger.debug("For Root Observation " + oldAutoLabInvDT.getOrderObservationLocalId() + "Two different Condition Exisits :" + autoLabInvDT.getConditionCode() + oldAutoLabInvDT.getConditionCode());
									else
										logger.debug("For Root Observation oldAutoLabInvDT is null" );
										
								}
							}
						}
						if (!it.hasNext()) {
							if (i == 0 || !multipleConditionExists) {
							
								oldAutoLabInvDT.setInvCreated("True");
								oldAutoLabInvDT.setComment("Public Health Case successfully created!");
								autoLabInvDTColl.add(oldAutoLabInvDT);
								updateAutoLabInvDT(oldAutoLabInvDT, nbsSecurityObj);
							} else {
								oldAutoLabInvDT.setInvCreated("False");
								oldAutoLabInvDT.setComment("Cannot Create Public Health Case as Resulted Tests are not linked to same condition!!!");
								autoLabInvDTColl.add(oldAutoLabInvDT);
								updateAutoLabInvDT(oldAutoLabInvDT, nbsSecurityObj);
							}
						}
						i++;

					} catch (Exception e) {
						logger.error("NbsPHCRDocumentEJB:ConvertLabToInvestigation Error thrown! " + e, e);
					}
				}
			}

			return autoLabInvDTColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.ConvertLabToInvestigation: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public void updateAutoLabInvDT(AutoLabInvDT oldAutoLabInvDT, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException, CreateException {
		try {
			AutoLabDAO autolabDAO = new AutoLabDAO();
			autolabDAO.updateAutoLabInvDT(oldAutoLabInvDT);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.updateAutoLabInvDT: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	public void getEntityHashCd(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		try {
			FindOrganizationDAOImpl orgDao = new FindOrganizationDAOImpl();
			ArrayList<Object> orgList = new ArrayList<Object>();
			// Getting the Organization and inserting to EDXEntity Match table
			try {
				orgList = orgDao.getOrganizationUids();

			} catch (Exception e) {
				logger.fatal("Error on getPatientHashCd" + e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(), e);
			}
			EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
			try {
				util.matchOrganization(orgList, nbsSecurityObj);
			} catch (Exception e) {
				logger.fatal("Error in matching the Organzation" + e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(), e);
			}
			// Getting the Provider and inserting to EDXEntity Match table
			FindPersonDAOImpl personDao = new FindPersonDAOImpl();
			ArrayList<Object> personList = new ArrayList<Object>();
			try {
				personList = personDao.getProviderUids();
			} catch (Exception e) {
				logger.fatal("Error in getProviderUids from EJB" + e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(), e);
			}
			try {
				util.matchProvider(personList, nbsSecurityObj);
			} catch (Exception e) {
				logger.fatal("Error in matching the Provider" + e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(), e);
			}
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.getEntityHashCd: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

	// added for Patient entity match-Lukkireddys
	public void getPatientHashCd(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		EdxPatientMatchingCriteriaUtil util = new EdxPatientMatchingCriteriaUtil();
		// Getting the patient and inserting to EDXpatient Match table
		FindPersonDAOImpl personDao = new FindPersonDAOImpl();
		try {
			Long personUid = personDao.getPatientUid();
			while (personUid != null) {
				util.matchPatient(personUid, nbsSecurityObj);
				// after insert the record update the record in Person table
				// with the // indicator 'Y
				personDao.updateMPREdxIndicator(personUid);
				personUid = personDao.getPatientUid();
			}
		} catch (Exception e) {
			logger.fatal("EdxPHCRDocumentEJB.getPatientHashCd: Error in matching the Patient, " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public void genPatientHashCd(NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		FindPersonDAOImpl personDao = new FindPersonDAOImpl();
		try {
			// Call stored procedure to Create EDX_PATIENT_MATCH_TMP table
			personDao.callPatientHashCodeStoredProc();
			// this method will select the records from EDX_PATIENT_MATCH_TMP 
			// and insert the records along with hascode in EDX_PATIENT_MATCH table
			// and also updates the EDX_IND flag as 'Y' in person table for that Person_uid.
			personDao.updateHashCodes();
			
			//For patient Next of Kin stored procedure
			personDao.callPatientNOKHashCodeStoredProc();
			
			// this method will select the records from EDX_NOK_MATCH_TMP 
			// and insert the records along with hascode in EDX_PATIENT_MATCH table
			// and also updates the EDX_IND flag as 'Y' in person table for that Person_uid.
			personDao.updateHashCodesforNOK();
			
		} catch (Exception e) {
			logger.fatal("EdxPHCRDocumentEJB.setSessionContext: Error in matching the Patient, " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public EdxRuleAlgorothmManagerDT processELRService(NbsInterfaceDT nbsInterfaceDT, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		try {
			EdxLabHelper eph = new EdxLabHelper();
			return eph.processELRService(nbsInterfaceDT, nbsSecurityObj, sessionCtx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.processELRService: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	
	/**
	 * Reset the cache of DSMLabMatchHelper for ELR ALgorithm Matching. 
	 * This is called at the start of the ELRImport if labs are found to process.
	 * @param nbsSecurityObj
	 * @throws EJBException
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public void resetElrAlgorithmCache(NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		logger.debug("EdxPHCRDocumentEJB.resetElrAlgorithmCache called"); 
		try {
			HL7ELRValidateDecisionSupport hL7ELRValidateDecisionSupport= new HL7ELRValidateDecisionSupport();
			hL7ELRValidateDecisionSupport.resetElrDsmAlgorithmCache();
		} catch (Exception e) {
			logger.fatal("EdxPHCRDocumentEJB.resetElrAlgorithmCache: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	
	public EdxLabInformationDT getUnProcessedELR(NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		try {
			EdxLabHelper eph = new EdxLabHelper();
			return eph.getUnProcessedELR(new Long (-1), sessionCtx, nbsSecurityObj);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.getUnProcessedELR: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	public ArrayList<Object> getUnProcessedElrDTList(NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		try {
			EdxLabHelper eph = new EdxLabHelper();
			return eph.getUnProcessedELRList(nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.getUnProcessedElrDTList: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	public Map<Integer,ArrayList<Object>> getUnProcessedElrDTMap(int batches, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		try {
			EdxLabHelper eph = new EdxLabHelper();
			return eph.getUnProcessedElrDTMap(batches, nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EdxPHCRDocumentEJB.getUnProcessedElrDTMap: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	public boolean getUnProcessedELRAll(ArrayList<Long> elrList, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		boolean allMessagesProcessed= true;
		try {
			
			logger.info("numberOfElr: "+elrList);
				if(elrList.size()>0) {
				Iterator<Long> it = 	elrList.iterator();
					while(it.hasNext()) {
						Long uid = (Long)it.next();
						EdxLabHelper eph = new EdxLabHelper();
						eph.getUnProcessedELR(uid, sessionCtx, nbsSecurityObj);
						logger.debug("EdxPHCRDocumentEJB.getUnProcessedELRAll processed nbsInterfaceUid successfully:-" + uid);
					}
				}
		} catch (Exception e) {
			allMessagesProcessed =  false;
			logger.error("EdxPHCRDocumentEJB.getUnProcessedELRAll Exception thrown " + e.getMessage());
			e.printStackTrace();
		}
		return allMessagesProcessed;
	}
	
	public EdxLabInformationDT processedSingleELR(Long nbsInterfaceUid, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException{
		EdxLabInformationDT edxLabInformationDT = null;
		try {
			
			logger.info("nbsInterfaceUid: "+nbsInterfaceUid);
				EdxLabHelper eph = new EdxLabHelper();
				edxLabInformationDT =eph.getUnProcessedELR(nbsInterfaceUid, sessionCtx, nbsSecurityObj);
				logger.debug("EdxPHCRDocumentEJB.processedSingleELR processed nbsInterfaceUid successfully:-" + nbsInterfaceUid);
		} catch (Exception e) {
			logger.error("EdxPHCRDocumentEJB.getUnProcessedELRAll Exception thrown " + e);
			e.printStackTrace();
		}
		return edxLabInformationDT;
	}

}
