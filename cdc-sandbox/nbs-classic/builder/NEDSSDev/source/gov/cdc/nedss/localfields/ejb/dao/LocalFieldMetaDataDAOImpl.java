package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.localfields.dt.LocalFieldsDT;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.localfields.helper.LocalFieldMetaDataHelper;
import gov.cdc.nedss.localfields.vo.NbsQuestionVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.sqlscript.DefinedFieldSQLQuery;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * LocalFieldMetaDataDAOImpl used to Load, Store or Delete NBSQuestion, NBSUIMetadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDAOImpl.java
 * Sep 2, 2008
 * @version
 */
public class LocalFieldMetaDataDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(LocalFieldMetaDataDAOImpl.class.getName());
	private  NBSQuestionDAOImpl questionDAO = null;
	private  NBSUIMetaDataDAOImpl uiMetadataDAO = null;
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	
	private static final String LOAD_LDFs_BY_PAGE_ID = "SELECT 	num.nbs_ui_metadata_uid \"nbsUiMetadataUid\", " 
														+"num.question_label \"questionLabel\", "
														+"nuc.type_cd_desc \"typeCdDesc\", "														
														+"num.order_nbr \"orderNbr\", "
														+"nq.nbs_question_uid \"nbsQuestionUid\", "
														+"num.parent_uid \"parentUid\" "
														+"FROM   NBS_UI_Component nuc "
														+"INNER JOIN NBS_UI_Metadata num ON nuc.nbs_ui_component_uid = num.nbs_ui_component_uid "
														+" LEFT OUTER JOIN NBS_Question nq ON num.nbs_question_uid = nq.nbs_question_uid "
														+"WHERE (num.record_status_cd='Active') AND (num.ldf_page_id = ?) order by  num.parent_uid, num.order_nbr ";
	
	private static final String COUNT_LDFS_ASSOCIATED_TO_TAB = "SELECT count(*) FROM nbs_ui_metadata WHERE record_status_cd = 'Active' and parent_uid= ? "; 
	
	private static final String COUNT_DUPLICATE_DMCOLUMNS = "SELECT COUNT(*) FROM  NBS_Question INNER JOIN NBS_UI_Metadata ON NBS_Question.nbs_question_uid = NBS_UI_Metadata.nbs_question_uid "
															+"WHERE NBS_Question.datamart_column_nm=? AND NBS_UI_Metadata.investigation_form_cd=? ";
	
	public static final String LDF_METADATA_SQL = "SELECT "
		+ "NBS_QUESTION.nbs_question_uid nbsQuestionUid, "
		+ "NBS_QUESTION.add_time addTime, "
		+ "NBS_QUESTION.add_user_id addUserId, "
		+ "NBS_QUESTION.code_set_group_id codeSetGroupId, "
		+ "NBS_QUESTION.data_type dataType, "
		+ "NBS_UI_Metadata.investigation_form_cd investigationFormCd, "
		+ "NBS_QUESTION.last_chg_time lastChgTime, "
		+ "NBS_QUESTION.last_chg_user_id lastChgUserId, "
		+ "	ISNULL(NBS_UI_Metadata.question_label, NBS_Question.question_label) AS questionLabel, "
		+ "ISNULL(NBS_UI_Metadata.question_tool_tip, NBS_Question.question_tool_tip) AS questionToolTip, "
		+ "NBS_QUESTION.version_ctrl_nbr questionVersionNbr, "
		+ "NBS_UI_Metadata.tab_order_id tabId, "
		+ "NBS_UI_Metadata.enable_ind enableInd, "
		+ "NBS_UI_Metadata.order_nbr orderNbr, "
		+ "NBS_UI_Metadata.default_value defaultValue, "
		+ "NBS_UI_Metadata.required_ind requiredInd, "
		+ "NBS_UI_Metadata.future_date_ind_cd futureDateInd, "
		+ "NND_METADATA.nnd_metadata_uid nndMetadataUid, "
		+ "NBS_Question.question_identifier questionIdentifier, "
		+ "NND_METADATA.question_identifier_nnd questionIdentifierNnd,"
		+ "NND_METADATA.question_required_nnd questionRequiredNnd,"
		+ "NBS_Question.question_oid questionOid, "
		+ "NBS_Question.question_oid_system_txt questionOidSystemTxt, "
		+ "CODE_SET.code_set_nm codeSetNm, "
		+ "NBS_QUESTION.data_location dataLocation, "
		+ "NBS_QUESTION.data_cd dataCd, "
		+ "NBS_QUESTION.data_use_cd dataUseCd, "
		+ "NBS_UI_Metadata.field_size fieldSize, "
		+ "NBS_UI_Metadata.parent_uid parentUid, "
		+ "NBS_UI_Metadata.ldf_page_id ldfPageId, "
		+ "NBS_UI_Metadata.nbs_ui_metadata_uid nbsUiMetadataUid, "
		+ "NBS_UI_Metadata.coinfection_ind_cd coinfectionIndCd, "
		+ "NBS_UI_Component.nbs_ui_component_uid nbsUiComponentUid "
		+ "FROM "
		+ "NBS_UI_Metadata INNER JOIN NBS_UI_Component ON NBS_UI_Metadata.nbs_ui_component_uid = NBS_UI_Component.nbs_ui_component_uid " 
		+ "LEFT OUTER JOIN "
	    + "NBS_Question ON NBS_UI_Metadata.nbs_question_uid = NBS_Question.nbs_question_uid LEFT OUTER JOIN "
	    + "NND_Metadata ON  NBS_UI_Metadata.nbs_ui_metadata_uid = NND_Metadata.nbs_ui_metadata_uid LEFT OUTER JOIN "
	    + "(SELECT DISTINCT code_set_group_id, code_set_nm FROM "           
	    + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..CODESET) CODE_SET "
	    + "ON CODE_SET.code_set_group_id = NBS_Question.code_set_group_id "
	    + "where NBS_UI_Metadata.record_status_cd = 'Active' "
	    + "AND NBS_UI_Metadata.ldf_page_id IS NOT NULL AND NBS_UI_Metadata.ldf_page_id <> '' "
	    + "AND NBS_UI_Metadata.investigation_form_cd = ? "
	    + "order by NBS_UI_Metadata.order_nbr  ";	
		
	
	/**
	 * Returns a Collection<Object>  of LocalFields by PageId
	 * @param pageId
	 * @return java.util.Collection
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  findLocalFields(String pageId) throws NEDSSDAOSysException, NEDSSSystemException {
		
		LocalFieldsDT localFieldsDT = new LocalFieldsDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(pageId);
		
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(localFieldsDT, paramList, LOAD_LDFs_BY_PAGE_ID, NEDSSConstants.SELECT);
			
		} catch (Exception ex) {
			logger.fatal("pageId: "+pageId+" Exception in findLocalFields: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return paramList;
		
	}

	/**
	 * Returns a NbsQuestionVO wrapped in a Collection
	 * @param questionUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Collection<Object>  getLocalField(Long uiMetadataUid) throws NEDSSDAOSysException, NEDSSSystemException {
		
		ArrayList<Object> returnList = new ArrayList<Object> ();
		NbsQuestionVO vo = new NbsQuestionVO();
		try {			
            if(questionDAO == null)
            	questionDAO = (NBSQuestionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_QUESTION_METADATA_DAO_CLASS);
           	if(uiMetadataDAO == null)
           		uiMetadataDAO = (NBSUIMetaDataDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_UI_METADATA_DAO_CLASS);
          	
           	NbsUiMetadataDT metadataDT = uiMetadataDAO.findNBSUiMetadata(uiMetadataUid);
			vo.setUiMetadata(metadataDT);

           	//If metadata has the questionUid, then retrieve the NBSQuestion
           	if(metadataDT.getNbsQuestionUid() != null && metadataDT.getNbsQuestionUid().longValue() > 0) {
           		NbsQuestionDT questionDT = questionDAO.findNBSQuestion(metadataDT.getNbsQuestionUid());
    			vo.setQuestion(questionDT);
           	}

           	returnList.add(vo);
		} catch (Exception ex) {
			logger.fatal("Exception in getLocalField: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
		return returnList;		
	}
	
	/**
	 * creates an NBSQuestion
	 * @param vo
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void createLocalField(NbsQuestionVO vo, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//populate common attributes
		Timestamp ts = new Timestamp(new Date().getTime());
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		
		vo.getQuestion().setItNew(true);
		vo.getQuestion().setAddTime(ts);
		vo.getQuestion().setAddUserId(Long.valueOf(userId));
		vo.getQuestion().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		vo.getQuestion().setStatusTime(ts);
		vo.getQuestion().setVersionCtrlNbr(new Integer(1));
		vo.getQuestion().setDataLocation("NBS_Case_Answer.answer_txt");
		vo.getQuestion().setLastChgTime(ts);
		vo.getQuestion().setLastChgUserId(Long.valueOf(userId));
		
		vo.getUiMetadata().setItNew(true);
		vo.getUiMetadata().setAddUserId(Long.valueOf(userId));
		vo.getUiMetadata().setAddTime(ts);
		//For RDB Purpose, populate LastChgUserId and LastChgTime
		vo.getUiMetadata().setLastChgUserId(Long.valueOf(userId));
		vo.getUiMetadata().setLastChgTime(ts);		
		vo.getUiMetadata().setRecordStatusCd("Active");
		vo.getUiMetadata().setRecordStatusTime(ts);
		vo.getUiMetadata().setLdfStatusCd(NEDSSConstants.LDF_CREATE_RECORD_STATUS_CD);
		vo.getUiMetadata().setLdfStatusTime(ts);
		vo.getUiMetadata().setVersionCtrlNbr(new Integer(1));
		// For 3.0ER civil00017335, enable_ind and display_ind get value 'T'
		vo.getUiMetadata().setEnableInd("T");
		vo.getUiMetadata().setDisplayInd("T");
		
		long nbsUiMetadataUid = 0;
		
		try {			
            if(questionDAO == null)
            	questionDAO = (NBSQuestionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_QUESTION_METADATA_DAO_CLASS);
           	if(uiMetadataDAO == null)
           		uiMetadataDAO = (NBSUIMetaDataDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_UI_METADATA_DAO_CLASS);
           	
           	//For Component Type 'TAB'(component Uid '1010'), no need to create record in NBSQuestion Table, so bypass this
    		Long componentUid = vo.getUiMetadata().getNbsUiComponentUid();
    		if(componentUid.compareTo(new Long(1010)) != 0) {
    			long questionUid = questionDAO.createNBSQuestion(vo.getQuestion());           	
               	vo.getUiMetadata().setNbsQuestionUid(new Long(questionUid));
               	//Retrieve tabOrderNo
               	int tabOrderId = LocalFieldMetaDataHelper.getInstance().retrieveTabOrder(vo.getUiMetadata().getInvestigationFormCd(), vo.getTabId(), false);
               	vo.getUiMetadata().setTabOrderId(new Integer(tabOrderId));
    		} else {
               	//If Component Type is 'TAB' , get the highest order number and increment by 1
               	int tabOrderId = LocalFieldMetaDataHelper.getInstance().retrieveTabOrder(vo.getUiMetadata().getInvestigationFormCd(), vo.getTabId(), true) + 1;
               	vo.getUiMetadata().setOrderNbr(new Integer(tabOrderId));
    		}
           	
    		nbsUiMetadataUid = uiMetadataDAO.createNBSUiMetadata(vo.getUiMetadata());
           	
		} catch (Exception ex) {
			logger.error("Exception in createNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
       	//Step2: Once an LDF is added, update the QuestionCache with this new ID
		updateLDFsInQuestionCache(new Long(nbsUiMetadataUid));
	}

	/**
	 * Update NBSQuestion
	 * @param dt
	 * @param nbsSecurityObj
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateLocalField(NbsQuestionVO vo, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, NEDSSSystemException {
		
		// increase the version control number by 1
		Integer versionCtrlNbr = vo.getQuestion().getVersionCtrlNbr() == null ? new Integer(1) : new Integer(vo.getQuestion().getVersionCtrlNbr().intValue()+ 1);
		//populate common attributes
		Timestamp ts = new Timestamp(new Date().getTime());
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		vo.getQuestion().setItNew(false);
		vo.getQuestion().setItDirty(true);
		vo.getQuestion().setLastChgUserId(Long.valueOf(userId));
		vo.getQuestion().setLastChgTime(ts);
		vo.getQuestion().setVersionCtrlNbr(versionCtrlNbr);
		vo.getQuestion().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		//UIMetadata
		vo.getUiMetadata().setLdfStatusCd(NEDSSConstants.LDF_UPDATE_RECORD_STATUS_CD);
		vo.getUiMetadata().setLdfStatusTime(ts);
		vo.getUiMetadata().setItNew(false);
		vo.getUiMetadata().setItDirty(true);
		vo.getUiMetadata().setLastChgUserId(Long.valueOf(userId));
		vo.getUiMetadata().setLastChgTime(ts);
		versionCtrlNbr = vo.getUiMetadata().getVersionCtrlNbr() == null ? new Integer(1) : new Integer(vo.getUiMetadata().getVersionCtrlNbr().intValue()+ 1);
		vo.getUiMetadata().setVersionCtrlNbr(versionCtrlNbr);
		// For 3.0ER civil00017335, enable_ind and display_ind get value 'T'
		vo.getUiMetadata().setEnableInd("T");
		vo.getUiMetadata().setDisplayInd("T");
		
       	//Retrieve tabOrderNo and update UIMetaData
       	int tabOrderId = LocalFieldMetaDataHelper.getInstance().retrieveTabOrder(vo.getUiMetadata().getInvestigationFormCd(), vo.getTabId(), false);
       	vo.getUiMetadata().setTabOrderId(new Integer(tabOrderId));
		
		try {			
            if(questionDAO == null)
            	questionDAO = (NBSQuestionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_QUESTION_METADATA_DAO_CLASS);
           	if(uiMetadataDAO == null)
           		uiMetadataDAO = (NBSUIMetaDataDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_UI_METADATA_DAO_CLASS);

           	//For Component Type 'TAB'(component Uid '1010'), no need to create/update record in NBSQuestion Table, so bypass this
    		Long componentUid = vo.getUiMetadata().getNbsUiComponentUid();
    		if(componentUid.compareTo(new Long(1010)) != 0) {
               	questionDAO.updateNBSQuestion(vo.getQuestion());    			
    		}
           	uiMetadataDAO.updateNBSUiMetadata(vo.getUiMetadata());
           	
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
       	//Step2: Once an LDF is updated, update the QuestionCache with this  ID
		updateLDFsInQuestionCache(vo.getUiMetadata().getNbsUiMetadataUid());
		
		
	}
	
	/**
	 * checkLocalFieldsAssociatedToTab checks to see any associated LDFs to the newly added TAB, if none good to delete the added TAB
	 * @param uiMetadataUid
	 * @return
	 */
	public Collection<Object>  checkLocalFieldsAssociatedToTab(Long uiMetadataUid) {
		ArrayList<Object> returnList = new ArrayList<Object> ();
		int resultCount = 0;
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(uiMetadataUid);
		try {			
			resultCount = ((Integer)preparedStmtMethod(null, paramList, COUNT_LDFS_ASSOCIATED_TO_TAB , NEDSSConstants.SELECT_COUNT)).intValue();
			
		} catch (Exception ex) {
			logger.error("Exception in checkLocalFieldsAssociatedToTab: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
		returnList.add(new Integer(resultCount));
		return returnList;
	}
	
	
	/**
	 * Delete NBSQuestion by questionUid
	 * @param Long uiMetadataUid, Long questionUid, String formCd
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteLocalField(Long uiMetadataUid, Long questionUid, String formCd, NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException, NEDSSSystemException {
		
		try {			
            if(questionDAO == null)
            	questionDAO = (NBSQuestionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_QUESTION_METADATA_DAO_CLASS);
			
           	if(uiMetadataDAO == null)
           		uiMetadataDAO = (NBSUIMetaDataDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NBS_UI_METADATA_DAO_CLASS);
           	
           	ArrayList<Object> list = (ArrayList<Object> ) getLocalField(uiMetadataUid);
           	if(list.size() > 0 ) {
       			NbsQuestionVO questionVO = (NbsQuestionVO) list.get(0);
       			
       			NbsQuestionDT questionDT = (NbsQuestionDT)questionVO.getQuestion();
       			NbsUiMetadataDT uiMetadataDT = questionVO.getUiMetadata();

       			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();       			
       			Timestamp ts = new Timestamp(new Date().getTime());
       			// increase the version control number by 1
       			Integer versionCtrlNbr = uiMetadataDT.getVersionCtrlNbr() == null ? new Integer(1) : new Integer(uiMetadataDT.getVersionCtrlNbr().intValue()+ 1);
       			uiMetadataDT.setVersionCtrlNbr(versionCtrlNbr);
       			uiMetadataDT.setRecordStatusTime(ts);
       			uiMetadataDT.setLdfStatusTime(ts);
       			uiMetadataDT.setLastChgUserId(Long.valueOf(userId));
       			uiMetadataDT.setLastChgTime(ts);
       			
       			questionDT.setLastChgUserId(Long.valueOf(userId));
       			questionDT.setLastChgTime(ts);
       			versionCtrlNbr = questionDT.getVersionCtrlNbr() == null ? new Integer(1) : new Integer(questionDT.getVersionCtrlNbr().intValue()+ 1);
       			questionDT.setVersionCtrlNbr(versionCtrlNbr);
       			//DAO
       			questionDAO.deleteNBSQuestion(questionDT);
       			uiMetadataDAO.deleteNBSUiMetadata(uiMetadataDT);	
           	}
           	
			
			
		} catch (Exception ex) {
			logger.fatal("Exception in deleteNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}			
       	//Step2: Once an LDF is updated, update the QuestionCache with this  ID
       	try {
			LocalFieldMetaDataHelper helper = LocalFieldMetaDataHelper.getInstance();
			helper.deleteLDFQuestionMap(questionUid, formCd);
		} catch (Exception e) {
			logger.error("Exception after updateNBSQuestion for LDFs while updating QuestionCache: ERROR = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}		
		
	}
	
	/**
	 * updateLDFsInQuestionCache retrieves the NBSQuestionVO after LDF CREATE/ UPDATE and marks an entry in questioncache accordingly
	 * to be picked up by PREVIEW / FILE 
	 * @param questionUid
	 */
	private void updateLDFsInQuestionCache(Long nbsUiMetadataUid) {
		
       	try {
       		ArrayList<Object> list = (ArrayList<Object> ) getLocalField(nbsUiMetadataUid);
       		if(list.size() > 0 ) {
       			NbsQuestionVO questionVO = (NbsQuestionVO) list.get(0);
    			LocalFieldMetaDataHelper helper = LocalFieldMetaDataHelper.getInstance();
    			helper.updateLDFQuestionMap(questionVO);
       		}
		} catch (Exception e) {
			logger.error("Exception after createNBSQuestion for LDFs while updating QuestionCache: ERROR = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}		
		
	}
	
	/**
	 * retrieveLDFsForDisplay retrieves LDFs for PREVIEW and DISPLAY in BOs 
	 * @param pageId
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  retrieveLDFsForDisplay(String pageId) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsQuestionMetadata dt = new NbsQuestionMetadata();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(pageId);
		String sqlQuery = LDF_METADATA_SQL;
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(dt, paramList, sqlQuery, NEDSSConstants.SELECT);
			
		} catch (Exception ex) {
			logger.fatal("pageId: "+pageId+" Exception in retrieveLDFsForDisplay: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return paramList;
		
	}	
	
	/**
	 * checkDMColumnForDuplicate checks to see if the passed DataMart Column Name Exists for the passed FORM_CD 
	 * @param dmColumnName, formCd
	 * @return
	 */
	public Collection<Object>  checkDMColumnForDuplicate(String dmColumnName, String formCd) {
		ArrayList<Object> returnList = new ArrayList<Object> ();
		int resultCount = 0;
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dmColumnName);
		paramList.add(formCd);
		
		try {			
			resultCount = ((Integer)preparedStmtMethod(null, paramList, COUNT_DUPLICATE_DMCOLUMNS , NEDSSConstants.SELECT_COUNT)).intValue();
			
		} catch (Exception ex) {
			logger.fatal("dmColumnName:"+dmColumnName+", formCd: "+formCd+" Exception in checkDMColumnForDuplicate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
		returnList.add(new Integer(resultCount));
		return returnList;
	}
	
	/**
	 * selectMetaDataByPageIdforLDF is used to retrieve Legacy LDFs (from StateDefinedFieldMetaData Table)
	 * to display Patient LDFs on the PAMs
	 * @param pageId
	 * @return
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByPageIdforLDF(String pageId) throws NEDSSSystemException {
		
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(pageId);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT = new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(pageId);
			list = (ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT, inputArgs, DefinedFieldSQLQuery.SELECT_METADATA_BY_PAGEID, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("pageId: "+pageId+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	}	
}