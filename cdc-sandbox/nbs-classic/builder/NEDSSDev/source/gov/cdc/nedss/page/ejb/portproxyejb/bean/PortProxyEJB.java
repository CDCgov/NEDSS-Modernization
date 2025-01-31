package gov.cdc.nedss.page.ejb.portproxyejb.bean;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.portproxyejb.dao.PortPageDAO;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;

public class PortProxyEJB extends BMPBase implements javax.ejb.SessionBean {
	
	static final LogUtils logger = new LogUtils(PortProxyEJB.class.getName());
	
	
	/**
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> getPortPageList(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> portPageList = portPageDAO.getPortPageList();
			return portPageList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getFieldsFromPageQuestions: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param mapName
	 * @param fromPageTemplateName
	 * @param toPageTemplateName
	 * @param mappingStatus
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Long createPortPageMapping(String mapName, String fromPageTemplateName, String toPageTemplateName, String mappingStatus,String xmlPayload, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			String userIdStr = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long userId = Long.parseLong(userIdStr);
			Long nbsConversionPageMgmtUid = portPageDAO.createPortPageMapping(mapName, fromPageTemplateName, toPageTemplateName, mappingStatus,xmlPayload, userId, userId);
			return nbsConversionPageMgmtUid;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.cratePortPageMapping: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param fromWaTemplateUid
	 * @param toWaTemplateUid
	 * @param waPortPageUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Object viewPortPageMapping(Long fromWaTemplateUid, Long toWaTemplateUid,Long waPortPageUid, String mappingType, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> mappedQuestionAnswersList = portPageDAO.viewPortPageMapping(fromWaTemplateUid, toWaTemplateUid, waPortPageUid, mappingType);
			return mappedQuestionAnswersList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getFieldsFromPageQuestions: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param waPortPageUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Object viewPortPageSummary(Long waPortPageUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> mappedQuestionAnswersList = portPageDAO.viewPortPageSummary(waPortPageUid);
			return mappedQuestionAnswersList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getFieldsFromPageQuestions: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param fromPageTemplateUid
	 * @param toPageTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> getFieldsFromPageQuestions(Long fromPageTemplateUid, Long toPageTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> fromFieldColl = portPageDAO.getFieldsFromPageQuestions(fromPageTemplateUid, toPageTemplateUid);
			return fromFieldColl;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getFieldsFromPageQuestions: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}

	/**
	 * @param fromPageTemplateUid
	 * @param toPageTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> getToPageQuestionsFields(Long fromPageTemplateUid, Long toPageTemplateUid, String mappingType, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> toFieldList = portPageDAO.getToPageQuestionsFields(fromPageTemplateUid, toPageTemplateUid, mappingType);
			return toFieldList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getToPageQuestionsFields: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param questionIDList
	 * @param notMappedQuestionList
	 * @param waTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> getAnswersToMap(ArrayList<Object> questionIDList, ArrayList<Object> notMappedQuestionList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> toFieldList = portPageDAO.getAnswersToMap(questionIDList, notMappedQuestionList, waTemplateUid);
			return toFieldList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getAnswersToMap: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	

	/**
	 * @param queAnsList
	 * @param waPortMappingUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean insertQuestionAnswers(ArrayList<Object> queAnsList, Long waPortMappingUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isListInserted = portPageDAO.insertQuestionAnswers(queAnsList,waPortMappingUid);
			return isListInserted;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.insertQuestionAnswers: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> retrieveQuestionAnswers(String conditionCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> queAnsList = portPageDAO.retrieveQuestionAnswers(conditionCd);
			return queAnsList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.retrieveQuestionAnswers: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param questionIdentifier
	 * @param fromPageWaTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> findAnswersForQuestionsByPage(String questionIdentifier, Long fromPageWaTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> answersByPageList = portPageDAO.findAnswersForQuestionsByPage(questionIdentifier, fromPageWaTemplateUid);
			return answersByPageList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.findAnswersForQuestionsByPage: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param pageName
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> findWaTemplateByPageName(String pageName, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> pageList = portPageDAO.findWaTemplateByPageName(pageName);
			return pageList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.findWaTemplateByPageName: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param waTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> findCoreQuestionsByPage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> coreQuestionsList = portPageDAO.findCoreQuestionsByPage(waTemplateUid);
			return coreQuestionsList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.findWaTemplateByPageName: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param statusCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Integer getNotificationCountByConditionAndStatus(String conditionCd, String statusCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Integer notificationCount = portPageDAO.getNotificationCountByConditionAndStatus(conditionCd, statusCd);
			return notificationCount;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNotificationCountByConditionAndStatus: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param mappedQueAnsList
	 * @param conditionCdGroupId
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean insertMappingForConversion(ArrayList<Object> mappedQueAnsList, Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean success = portPageDAO.insertMappingForConversion(mappedQueAnsList, conditionCdGroupId);
			return success;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.insertMappingInNBSConversionMapping: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param nbsConversionPageMgmtUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Long createNBSConversionCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Long conditionCdGroupId = portPageDAO.createNBSConversionCondition(conditionCd,nbsConversionPageMgmtUid);
			return conditionCdGroupId;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.createNBSConversionCondition: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param nbsConversionPageMgmtUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Long findConditionCdGroupIdByCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Long conditionCdGroupId = portPageDAO.findConditionCdGroupIdByCondition(conditionCd,nbsConversionPageMgmtUid);
			return conditionCdGroupId;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.findConditionCdGroupIdByCondition: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCdGroupId
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean removeMappingsCreatedForConversion(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isRemoved = portPageDAO.removeMappingsCreatedForConversion(conditionCdGroupId);
			return isRemoved;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.findConditionCdGroupIdByCondition: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCdGroupId
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ArrayList<Object> getNBSConversionMappings(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> nbsConversionMappingList = portPageDAO.getNBSConversionMappings(conditionCdGroupId);
			return nbsConversionMappingList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNBSConversionMappings: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param pageCondMappDT
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void createPageCondiMapping(PageCondMappingDT pageCondMappDT, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			//Set UserId
			String userIdStr = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long userId = Long.parseLong(userIdStr);
			pageCondMappDT.setAddUserId(userId);
			pageCondMappDT.setLastChgUserId(userId);
			
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			pageManagementDAOImpl.setPageCondiMapping(pageCondMappDT);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.createPageCondiMapping: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<Long, String> getPublicHealthCaseUidsByCondition(String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<Long, String> phcUidAndLocalIdMap  = portPageDAO.getPublicHealthCaseUidsByCondition(conditionCd);
			return phcUidAndLocalIdMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getPublicHealthCaseUidsByCondition: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param nbsQuestionUidList
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<Long, String> getQuestionIdentifiersForNbsQuestionUid(ArrayList<Object> nbsQuestionUidList, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<Long, String>  questionIdentifierAndNbsQuestionUidMap  = portPageDAO.getQuestionIdentifiersForNbsQuestionUid(nbsQuestionUidList);
			return questionIdentifierAndNbsQuestionUidMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getQuestionIdentifiersForNbsQuestionUid: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param questionIdentifierList
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<String, Long> getNbsQuestionUidsForQuestionIdentifiers(ArrayList<Object> questionIdentifierList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<String, Long>  questionIdentifierAndNbsQuestionUidMap  = portPageDAO.getNbsQuestionUidsForQuestionIdentifiers(questionIdentifierList, waTemplateUid);
			return questionIdentifierAndNbsQuestionUidMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNbsQuestionUidsForQuestionIdentifiers: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param oldWaTemplateUid
	 * @param newWaTemplateUid
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void updatePageConditionMappingByCondtionCdAndTempalteUid(String conditionCd, Long oldWaTemplateUid, Long newWaTemplateUid, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{
			String userIdStr = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long userId = Long.parseLong(userIdStr);
			
			PortPageDAO portPageDAO = new PortPageDAO();
			portPageDAO.updatePageConditionMappingByCondtionCdAndTempalteUid(conditionCd,oldWaTemplateUid,newWaTemplateUid, userId);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNbsQuestionUidsForQuestionIdentifiers: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param mappingStatusCd
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void updateNbsConversionPageMgmt(Long nbsConversionPageMgmtUid, String mappingStatusCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{
			String userIdStr = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long userId = Long.parseLong(userIdStr);
			
			PortPageDAO portPageDAO = new PortPageDAO();
			portPageDAO.updateNbsConversionPageMgmt(nbsConversionPageMgmtUid,mappingStatusCd, userId);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.updateNbsConversionPageMgmt: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param statusCd
	 * @param conditionCdGroupId
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void updateNbsConversionConditionAfterPorting(Long nbsConversionPageMgmtUid, String statusCd, Long conditionCdGroupId, String conditionCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			PortPageDAO portPageDAO = new PortPageDAO();
			portPageDAO.updateNbsConversionConditionAfterPorting(nbsConversionPageMgmtUid,statusCd, conditionCdGroupId, conditionCd);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.updateNbsConversionConditionAfterPorting: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCodeGroupId
	 * @param status
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public ArrayList<Object> getConvertedCasesFromNbsConversionMaster(Long conditionCodeGroupId, String status, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> publicHealthCaseUidList = portPageDAO.getConvertedCasesFromNbsConversionMaster(conditionCodeGroupId,status);
			return publicHealthCaseUidList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getConvertedCasesFromNbsConversionMaster: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param nbsConversionPageMgmtUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public NBSConversionConditionDT getNBSConversionConditionDTForCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			PamConversionDAO pamConvDAO = new PamConversionDAO();
			NBSConversionConditionDT nbsConvCondDT = pamConvDAO.getNBSConversionConditionDTForCondition(conditionCd, nbsConversionPageMgmtUid);
			return nbsConvCondDT;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNBSConversionConditionDTForCondition: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param edxActivityLogDT
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Long insertEDXActivityLog(EDXActivityLogDT edxActivityLogDT, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
			Long edxActivityLogUid = edxActivityLogDAOImpl.insertEDXActivityLogDT(edxActivityLogDT);
			return edxActivityLogUid;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.insertEDXActivityLog: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param edxActivityLogDT
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void insertEDXActivityDetailsLog(EDXActivityLogDT edxActivityLogDT, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
			edxActivityLogDAOImpl.insertEDXActivityLog(edxActivityLogDT);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.insertEDXActivityDetailsLog: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param waTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<String, MappedFromToQuestionFieldsDT> getQuestionsToBeMappedByWaTemplateUid(Long waTemplateUid, Boolean addSNDummyQue, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<String, MappedFromToQuestionFieldsDT>  questionIdentifierAndNbsQuestionUidMap  = portPageDAO.getQuestionsToBeMappedByWaTemplateUid(waTemplateUid, addSNDummyQue);
			return questionIdentifierAndNbsQuestionUidMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getQuestionsToBeMappedByWaTemplateUid: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param questionIdentiferList
	 * @param waTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<String, WaUiMetadataDT> getWaUiMetadataByTemplateUidAndQuestionIdentifier(ArrayList<Object> questionIdentiferList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<String, WaUiMetadataDT> questionMap  = portPageDAO.getWaUiMetadataByTemplateUidAndQuestionIdentifier(questionIdentiferList, waTemplateUid);
			return questionMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getWaUiMetadataByTemplateUidAndQuestionIdentifier: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param waTemplateUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<Integer, ArrayList<Object>> getBatchGroupQuestionsByPage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<Integer, ArrayList<Object>> batchQuestionMap = portPageDAO.getBatchGroupQuestionsByPage(waTemplateUid);
			return batchQuestionMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getBatchGroupQuestionsByPage: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param formCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public ArrayList<Object> getConditionByTemplateFormCd(String formCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception{
		try{		
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> conditionList = portPageDAO.getConditionByTemplateFormCd(formCd);
			return conditionList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getConditionByTemplateFormCd: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public ManagePageDT getNBSConversionPageMgmtByUid(Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception {
		try{		
			PortPageDAO portPageDAO = new PortPageDAO();
			ManagePageDT managePageDT = portPageDAO.getNBSConversionPageMgmtByUid(nbsConversionPageMgmtUid);
			return managePageDT;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNBSConversionPageMgmtByUid: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param eventType
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Integer getEventCountByEventType(String eventType, String conditionCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Integer eventCount = portPageDAO.getEventCountByEventType(eventType,conditionCd);
			return eventCount;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNotificationCountByConditionAndStatus: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public Map<Long, String> getInterventionUids(NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			Map<Long, String> interventionUidAndLocalIdMap  = portPageDAO.getInterventionUids();
			return interventionUidAndLocalIdMap;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getInterventionUids: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	/**
	 * @param toPageFormCd
	 * @param fromPageFormCd
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public void updateToPageFormCdInNbsConversionPageMgmt(String toPageFormCd, String fromPageFormCd, String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			portPageDAO.updateToPageFormCdInNbsConversionPageMgmt(toPageFormCd, fromPageFormCd, conditionCd);
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.updateToPageFormCdInNbsConversionPageMgmt: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	public boolean isLegacyEventPortedToNewPage(String fromPageFormCd, String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isLegacyEventPorted = portPageDAO.isLegacyEventPortedToNewPage(fromPageFormCd, conditionCd);
			return isLegacyEventPorted;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.updateToPageFormCdInNbsConversionPageMgmt: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	public boolean insertLDFFromNbsUiMetadataToWaUiMetadata(String fromPageInvFormCd, Long fromPageTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isSuccessfulInsert = portPageDAO.insertLDFFromNbsUiMetadataToWaUiMetadata(fromPageInvFormCd, fromPageTemplateUid);
			return isSuccessfulInsert;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.insertLDFFromNbsUiMetadataToWaUiMetadata: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	
	public ArrayList<Object> getNewlyAddedLDFsForHybridPage(String fromPageInvFormCd, Long fromPageTemplateUid , NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			ArrayList<Object> newlyAddedLDFList = portPageDAO.getNewlyAddedLDFsForHybridPage(fromPageInvFormCd, fromPageTemplateUid);
			return newlyAddedLDFList;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNewlyAddedLDFsForHybridPage: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public boolean correctDataBeforeConversion(String conditionCd, String mappingType, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isDataCorrected = portPageDAO.correctDataBeforeConversion(conditionCd, mappingType);
			return isDataCorrected;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.correctDataBeforeConversion: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param conditionCdGroupId
	 * @param nbsConversionPageMgmtUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public NBSConversionConditionDT getNBSConversionConditionByParameters(String conditionCd, Long conditionCdGroupId, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception {
		try{		
			PortPageDAO portPageDAO = new PortPageDAO();
			NBSConversionConditionDT nbsConversionConditionDT = portPageDAO.getNBSConversionConditionByParameters(conditionCd, conditionCdGroupId, nbsConversionPageMgmtUid);
			return nbsConversionConditionDT;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.getNBSConversionConditionByParameters: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	
	/**
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws Exception
	 */
	public boolean postConversionCleanUp(String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception{
		try{
			PortPageDAO portPageDAO = new PortPageDAO();
			boolean isDataCorrected = portPageDAO.postConversionCleanUp(conditionCd);
			return isDataCorrected;
		}catch (Exception ex) {
			logger.fatal("PortProxyEJB.postConversionCleanUp: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(),ex);
		}
	}
	
	@Override
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}

}
