package gov.cdc.nedss.page.ejb.portproxyejb.bean;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJBObject;

public interface PortProxy extends EJBObject {
	
	public ArrayList<Object> getPortPageList(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;

	public Long createPortPageMapping(String mapName, String fromPageTemplateName, String toPageTemplateName, String mappingStatus,String xmlPayload, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Object viewPortPageMapping(Long fromWaTemplateUid, Long toWaTemplateUid,Long waPortPageUid, String mappingType, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Object viewPortPageSummary(Long waPortPageUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> getFieldsFromPageQuestions(Long fromPageTemplateUid, Long toPageTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> getToPageQuestionsFields(Long fromPageTemplateUid, Long toPageTemplateUid, String mappingType, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> getAnswersToMap(ArrayList<Object> questionIDList, ArrayList<Object> notMappedQuestionList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public boolean insertQuestionAnswers(ArrayList<Object> mappedQueAnsList, Long waPortMappingUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> retrieveQuestionAnswers(String conditionCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> findAnswersForQuestionsByPage(String questionIdentifier, Long fromPageWaTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> findWaTemplateByPageName(String pageName, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> findCoreQuestionsByPage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Integer getNotificationCountByConditionAndStatus(String conditionCd, String statusCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public boolean insertMappingForConversion(ArrayList<Object> mappedQueAnsList, Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Long createNBSConversionCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Long findConditionCdGroupIdByCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public boolean removeMappingsCreatedForConversion(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public ArrayList<Object> getNBSConversionMappings(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public void createPageCondiMapping(PageCondMappingDT pageCondMappDT, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public Map<Long, String> getPublicHealthCaseUidsByCondition(String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public Map<Long, String> getQuestionIdentifiersForNbsQuestionUid(ArrayList<Object> nbsQuestionUidList, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public Map<String, Long> getNbsQuestionUidsForQuestionIdentifiers(ArrayList<Object> questionIdentifierList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public void updatePageConditionMappingByCondtionCdAndTempalteUid(String conditionCd, Long oldWaTemplateUid, Long newWaTemplateUid, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public void updateNbsConversionPageMgmt(Long nbsConversionPageMgmtUid, String mappingStatusCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public void updateNbsConversionConditionAfterPorting(Long nbsConversionPageMgmtUid, String statusCd, Long conditionCdGroupId, String conditionCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public ArrayList<Object> getConvertedCasesFromNbsConversionMaster(Long conditionCodeGroupId, String status, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public NBSConversionConditionDT getNBSConversionConditionDTForCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public Long insertEDXActivityLog(EDXActivityLogDT edxActivityLogDT, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public void insertEDXActivityDetailsLog(EDXActivityLogDT edxActivityLogDT, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public Map<String, MappedFromToQuestionFieldsDT> getQuestionsToBeMappedByWaTemplateUid(Long waTemplateUid, Boolean addSNDummyQue, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public Map<String, WaUiMetadataDT> getWaUiMetadataByTemplateUidAndQuestionIdentifier(ArrayList<Object> questionIdentiferList, Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public Map<Integer, ArrayList<Object>> getBatchGroupQuestionsByPage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public ArrayList<Object> getConditionByTemplateFormCd(String formCd, NBSSecurityObj nbsSecurityObj)throws NEDSSDAOSysException, Exception;
	
	public Integer getEventCountByEventType(String eventType, String conditionCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	
	public Map<Long, String> getInterventionUids(NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public ManagePageDT getNBSConversionPageMgmtByUid(Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public void updateToPageFormCdInNbsConversionPageMgmt(String toPageFormCd, String fromPageFormCd, String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public boolean isLegacyEventPortedToNewPage(String fromPageFormCd, String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public boolean insertLDFFromNbsUiMetadataToWaUiMetadata(String fromPageInvFormCd, Long fromPageTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public ArrayList<Object> getNewlyAddedLDFsForHybridPage(String fromPageInvFormCd, Long fromPageTemplateUid , NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public boolean correctDataBeforeConversion(String conditionCd, String mappingType, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public NBSConversionConditionDT getNBSConversionConditionByParameters(String conditionCd, Long conditionCdGroupId, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
	public boolean postConversionCleanUp(String conditionCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, Exception;
	
}
