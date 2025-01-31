package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NedssAppLogException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageMetadataVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleSummaryDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.NbsBusObjMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DsmLogSearchDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managetemplate.ManageTemplateForm;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJBException;
import javax.servlet.http.HttpSession;



public interface PageManagementProxy extends javax.ejb.EJBObject {
	
	public PageManagementProxyVO publishPage(WaTemplateDT inWaTemplateDT,  NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException;
	public Long addPage(WaTemplateVO waTemplateVO,String actionMode, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public Long savePageDraft(PageManagementProxyVO pageManagementProxyVO, String pageElementOrder, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public Long saveTemplate(Long waTemplateUid,String templNm, String desc, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public void inactivatePage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public Long deleteDraft(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public PageManagementProxyVO getPageDraft(Long waTemplateUid,boolean pageBuilderInd,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public Map<Object,Object> getPageSummaries(NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;

	public Collection<Object> getWaQuestion(Long waQuestionUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

	public Long setWaQuestion(Collection<Object> waQuestionDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

	public ArrayList<Object> searchWaQuestions(String whereclause, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

	public Collection<Object> getWaQuestionDTCollection(NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

	public Collection<Object> processManageConditionRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public Collection<Object> getConditionLib(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public Map<Object,Object> createCondition( ConditionDT conditionDt, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public Map<Object,Object> updateCondition( ConditionDT conditionDt, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public Long setWaUIMetadata(Collection<Object> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
   
    public WaRuleMetadataDT insertRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public void insertRuleMetadataDTColl(Collection<Object> waRuleMetadataDTColl, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public WaRuleMetadataDT updateRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public void deleteRuleMetadata(Long  waRuleMetadataDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public void publishRuleMetadata(Collection<WaRuleMetadataDT> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public void createDraftWaRuleMetadata(Collection<WaRuleMetadataDT> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public Collection<Object> getWaRuleSummaryDTCollection(Long templateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public Long savePublishedPageAsDraft(Long waTemplateDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException,NEDSSSystemException,NEDSSConcurrentDataException;
    public Collection<Object> getWaUiElementDTDropDown(Long templateUid, String type,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public WaRuleMetadataDT selectWaRuleMetadataDT(Long waRuleMetadataDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public Collection<Object> getPageHistory(String pageNm,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;	
    public Collection<Object> getJspFiles(String invFormCd,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public Collection<Object> getInvestigationFormCodeonServerStartup() throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public ArrayList<Object> getTemplateLib(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public Map<Object,Object> fetchInsertSql(String strTableName,String colName, ArrayList<Object> IdList,String waTemplateUid,String templateNm,Map<Object,Object> codeSetNmColl,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public String InsertImportSql( ArrayList<Object> aList,ArrayList<Object> vList,String templateNm,Long activityLogUid,Map<Object,Object> codeSetNmColl,NBSSecurityObj nbsSecurityObj) throws NedssAppLogException, java.rmi.RemoteException,javax.ejb.EJBException;
    public String WriteImportSqlLog( EDXActivityLogDT dt) throws java.rmi.RemoteException,javax.ejb.EJBException;
    public String InsertImportSqlWithLog( ArrayList<Object> aList,ArrayList<Object> vList,String templateNm,Long activityLogUid,Map<Object,Object> codeSetNmColl,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException;
    public ArrayList<Object> getActivityLogCollection(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public ArrayList<Object> getAllDsmActivityLogCollection(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public ArrayList<Object> getDsmActivityLogCollection(DsmLogSearchDT dsmLogSearchDT,NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public EDXActivityLogDT getLatestDsmActivityLog(String algorithmName,NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public ArrayList<Object> getDsmActivityLogDetailCollection(Long activityUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public void updateConditionCode() throws RemoteException, Exception;
    public void updatePageDetails(WaTemplateVO waTempVO)throws java.rmi.RemoteException,javax.ejb.EJBException,Exception;
    public WaTemplateVO getPageDetails(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException; 
    public PageMetadataVO getPageMetadata(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException; 
    public Map<Object,Object> findBatchRecords(String invFrmCd,NBSSecurityObj nbsSecurityObj) throws RemoteException, javax.ejb.EJBException;
    public Map<Object,Object> getBatchMap(Long waTemplateUid,NBSSecurityObj nbsSecurityObj) throws RemoteException, javax.ejb.EJBException;

    public Collection<Object> getUiMetaQuestionDropDown(Long templateUid,String source,String mode, String ruleId, String ruleCode, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    
    public Collection<Object> getUiMetaSubsectionDropDown(Long templateUid, String source,String mode, String ruleId, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public Collection<Object> getWaTemplateUidByPageNm(String templateNm) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException,NEDSSSystemException,NEDSSConcurrentDataException;
    
    public Collection<String> getPreviousSeletedTargets(Long templateUid,String mode, String ruleId, String ruleCode, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;
    public WaTemplateDT getWaTemplate(String conditionCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public WaTemplateDT getWaTemplateByCondTypeBusObj(String conditionCd, String templateType, String busObj, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public String getPublishedCondition(String conditionCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public ArrayList<Object> getActivityLogColl(Date fromDate, Date toDate, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,  javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    public Long getPHCUidForLoaclId (String localid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, java.rmi.RemoteException, javax.ejb.EJBException;
    public ArrayList<Object> findPageByBusinessObjType(String busObjType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public ArrayList<Object> getNbsBusObjMetadataList(NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public NbsBusObjMetadataDT getNbsBusObjMetadataByBusObjType(String busObjType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public void loadLookups(NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public boolean correctMetadataAfterTemplateImport(String templateNm, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public ArrayList<Object> findPageByTemplateType(String templateType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public ArrayList<Object> findWaUiMetadataForSubFormByWaTemplateUid(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException;
    public String editAndPublishPage(String waTemplateUidString, String mode,NBSSecurityObj nbsSecurityObj);
    public ArrayList<Object> getWaTemplateListToRepublish(NBSSecurityObj nbsSecurityObj);	
    public ArrayList<Object> getWaUiTemplateUidsToRepublishByTemplateName(String listOfTemplates, NBSSecurityObj nbsSecurityObj);
    public NbsPageDT findNBSPageDetailsExceptJspPayloadByFormCd(String formCd)  throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
    /*

	public Long createWaQuestion(WaQuestionDT waQuestionDT, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException,
			javax.ejb.CreateException;
	
	public void updateWaQuestion(WaQuestionDT waQuestionDT, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException,
			javax.ejb.CreateException;*/
}
