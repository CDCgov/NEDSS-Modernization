package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * NBSUIMetaDataDAOImpl used to Load, Store or Delete an NBS UI Metadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDAOImpl.java
 * Sep 2, 2008
 * @version
 */
public class NBSUIMetaDataDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(NBSUIMetaDataDAOImpl.class.getName());
    private long nbsUiMetadataUid = -1;
	
	private static final String FIND_NBS_UI_METADATA = "SELECT num.nbs_ui_metadata_uid \"nbsUiMetadataUid\", "
														+"num.nbs_ui_component_uid \"nbsUiComponentUid\", " 
														+"num.nbs_question_uid \"nbsQuestionUid\", " 
														+"num.parent_uid \"parentUid\", " 
														+"num.question_label \"questionLabel\", " 
														+"num.block_nm \"blockName\", " 
														+"num.question_tool_tip \"questionToolTip\", " 
														+"num.investigation_form_cd \"investigationFormCd\", " 
														+"num.enable_ind \"enableInd\", " 
														+"num.default_value \"defaultValue\", " 
														+"num.display_ind \"displayInd\", " 
														+"num.order_nbr \"orderNbr\", " 
														+"num.required_ind \"requiredInd\", " 
														+"num.tab_order_id \"tabOrderId\", " 
														+"num.tab_name \"tabName\", " 
														+"num.add_time \"addTime\", " 
														+"num.add_user_id \"addUserId\", " 
														+"num.last_chg_time \"lastChgTime\", " 
														+"num.last_chg_user_id \"lastChgUserId\", " 
														+"num.record_status_cd \"recordStatusCd\", " 
														+"num.record_status_time \"recordStatusTime\", " 
														+"num.max_length \"maxLength\", " 
														+"num.ldf_position \"ldfPosition\", " 
														+"num.css_style \"cssStyle\", " 
														+"num.ldf_page_id \"ldfPageId\", "
														+"num.ldf_status_cd \"ldfStatusCd\", "
														+"num.admin_comment \"adminComment\", "
														+"num.ldf_status_time \"ldfStatusTime\", "
														+"num.version_ctrl_nbr \"versionCtrlNbr\", "
														+"num.field_size \"fieldSize\", "
														+"num.future_date_ind_cd \"futureDateInd\", "
														+"num.unit_type_cd \"unitTypeCd\", "
														+"num.unit_value \"unitValue\", "
														+"num.other_value_ind_cd \"otherValueIndCd\", "
														+"nq.datamart_column_nm \"datamartColumnNm\" "
														+"FROM NBS_UI_Metadata num "
														+"LEFT OUTER JOIN "
														+"NBS_Question nq ON num.nbs_question_uid = nq.nbs_question_uid "
														+"WHERE num.nbs_ui_metadata_uid = ? ";

    private static final String FIND_NBS_UI_METADATA_BY_QUESTION_IDENTIFIER = "SELECT "
    	                                                                    + "nbs_ui_metadata_uid \"nbsUiMetadataUid\", "
    	                                                                    + "nbs_ui_component_uid \"nbsUiComponentUid\", "
    	                                                                    + "nbs_question_uid \"nbsQuestionUid\", "
    	                                                                    + "parent_uid \"parentUid\", "
    	                                                                    + "question_label \"questionLabel\", "
    	                                                                    + "block_nm \"blockName\", "
    	                                                                    + "question_tool_tip \"questionToolTip\", "
    	                                                                    + "investigation_form_cd \"investigationFormCd\", "
    	                                                                    + "enable_ind \"enableInd\", "
    	                                                                    + "default_value \"defaultValue\", "
    	                                                                    + "display_ind \"displayInd\", "
    	                                                                    + "order_nbr \"orderNbr\", "
    	                                                                    + "required_ind \"requiredInd\", "
    	                                                                    + "tab_order_id \"tabOrderId\", "
    	                                                                    + "tab_name \"tabName\", "
    	                                                                    + "add_time \"addTime\", "
    	                                                                    + "add_user_id \"addUserId\", "
    	                                                                    + "last_chg_time \"lastChgTime\", "
    	                                                                    + "last_chg_user_id \"lastChgUserId\", "
    	                                                                    + "record_status_cd \"recordStatusCd\", "
    	                                                                    + "record_status_time \"recordStatusTime\", "
    	                                                                    + "max_length \"maxLength\", "
    	                                                                    + "ldf_position \"ldfPosition\", "
    	                                                                    + "css_style \"cssStyle\", "
    	                                                                    + "ldf_page_id \"ldfPageId\", "
    	                                                                    + "ldf_status_cd \"ldfStatusCd\", "
    	                                                                    + "admin_comment \"adminComment\", "
    	                                                                    + "ldf_status_time \"ldfStatusTime\", "
    	                                                                    + "version_ctrl_nbr \"versionCtrlNbr\", "
    	                                                                    + "field_size \"fieldSize\", "
    	                                                                    + "future_date_ind_cd \"futureDateInd\", "
    	                                                                    + "nbs_table_uid \"nbsTableUid\", "
    	                                                                    + "code_set_group_id \"codeSetGroupId\", "
    	                                                                    + "data_cd \"dataCd\", "
    	                                                                    + "data_location \"dataLocation\", "
    	                                                                    + "data_type \"dataType\", "
    	                                                                    + "data_use_cd \"dataUseCd\", "
    	                                                                    + "legacy_data_location \"legacyDataLocation\", "
    	                                                                    + "part_type_cd \"partTypeCd\", "
    	                                                                    + "question_group_seq_nbr \"questionGroupSeqNbr\", "
    	                                                                    + "question_identifier \"questionIdentifier\", "
    	                                                                    + "question_oid \"questionOid\", "
    	                                                                    + "question_oid_system_txt \"questionOidSystemTxt\", "
    	                                                                    + "question_unit_identifier \"questionUnitIdentifier\", "
    	                                                                    + "repeats_ind_cd \"repeatsIndCd\", "
    	                                                                    + "unit_parent_identifier \"unitParentIdentifier\", "
    	                                                                    + "group_nm \"groupNm\", "
    	                                                                    + "sub_group_nm \"subGroupNm\", "
    	                                                                    + "desc_txt \"descTxt\", "
    	                                                                    + "mask \"mask\", "
    	                                                                    + "min_value \"minValue\", "
    	                                                                    + "max_value \"maxValue\", "
    	                                                                    + "standard_nnd_ind_cd \"standardNndIndCd\", "
    	                                                                    + "nbs_page_uid \"nbsPageUid\", "
    	            														+ "unit_type_cd \"unitTypeCd\", "
    	            														+ "unit_value \"unitValue\", "
    	            														+ "other_value_ind_cd \"otherValueIndCd\" "
    	                                                                    + "FROM NBS_UI_Metadata "
    																		+ "WHERE question_identifier=? and nbs_page_uid=?";



	private static final String CREATE_NBS_UI_METADATA = "INSERT INTO NBS_UI_Metadata(nbs_ui_metadata_uid,nbs_ui_component_uid,nbs_question_uid,parent_uid,question_label,block_nm,question_tool_tip,investigation_form_cd,enable_ind,default_value,display_ind,order_nbr,required_ind,tab_order_id,tab_name,add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,max_length,ldf_position,css_style,ldf_page_id,ldf_status_cd,admin_comment,ldf_status_time,version_ctrl_nbr,field_size,future_date_ind_cd,nbs_table_uid,code_set_group_id,data_cd,data_location,data_type,data_use_cd,legacy_data_location,part_type_cd,question_group_seq_nbr,question_identifier,question_oid,question_oid_system_txt,question_unit_identifier,repeats_ind_cd,unit_parent_identifier,group_nm,sub_group_nm,desc_txt,mask,nbs_page_uid,standard_nnd_ind_cd,unit_type_cd,unit_value,other_value_ind_cd,batch_table_appear_ind_cd,batch_table_header,batch_table_column_width, coinfection_ind_cd) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_NBS_UI_METADATA_LDF_STATUS = "UPDATE NBS_UI_Metadata "
																			+ "SET ldf_status_cd = ?, "
																			+ "ldf_status_time = ?, "
																			+ "version_ctrl_nbr = ? "
																			+ "WHERE nbs_ui_metadata_uid = ? ";

	
	private static final String UPDATE_NBS_UI_METADATA = "UPDATE NBS_UI_METADATA SET "
											           + "nbs_ui_component_uid=?, "
											           + "nbs_question_uid=?, "
											           + "parent_uid=?, "
											           + "question_label=?, "
											           + "block_nm=?, "								           
											           + "question_tool_tip=?, "
											           + "investigation_form_cd=?, "
											           + "enable_ind=?, "
											           + "default_value=?, "
											           + "display_ind=?, "
											           + "order_nbr=?, "
											           + "required_ind=?, "
											           + "tab_order_id=?, "
											           + "tab_name=?, "
											           + "add_time=?, "
											           + "add_user_id=?, "
											           + "last_chg_time=?, "
											           + "last_chg_user_id=?, "
											           + "record_status_cd=?, "
											           + "record_status_time=?, "
											           + "max_length=?, "
											           + "ldf_position=?, "
											           + "css_style=?, "
											           + "ldf_page_id=?, "
											           + "ldf_status_cd=?, "
											           + "admin_comment=?, "
											           + "ldf_status_time=?, "
											           + "version_ctrl_nbr=?, "
											           + "field_size=?, "
											           + "future_date_ind_cd=?, "
											           + "nbs_table_uid=?, "
											           + "code_set_group_id=?, "
											           + "data_cd=?, "
											           + "data_location=?, "
											           + "data_type=?, "
											           + "data_use_cd=?, "
											           + "legacy_data_location=?, "
											           + "part_type_cd=?, "
											           + "question_group_seq_nbr=?, "
											           + "question_identifier=?, "
											           + "question_oid=?, "
											           + "question_oid_system_txt=?, "
											           + "question_unit_identifier=?, "
											           + "repeats_ind_cd=?, "
											           + "unit_parent_identifier=?, "
											           + "group_nm=?, "
											           + "sub_group_nm=?, "
											           + "desc_txt=?, "
											           + "mask=?, "
											           + "min_value=?, "
											           + "max_value=?, "
											           + "nbs_page_uid=?, "
											           + "standard_nnd_ind_cd=?, "
											           + "unit_type_cd=?, "
											           + "unit_value=?, "
											           + "other_value_ind_cd=? , "
											           + "batch_table_appear_ind_cd=?, "
											           + "batch_table_header=?, "
											           + "batch_table_column_width=?, "
											           + "coinfection_ind_cd=? "
											           + "WHERE nbs_ui_metadata_uid = ?";

	private final String SELECT_MAX_VERSION_CTRL_NBR = "SELECT MAX(version_ctrl_nbr) FROM NBS_ui_metadata WHERE (nbs_page_uid = ?)";

	private static final String DELETE_STATIC_ELEMENTS_BY_NBS_PAGE_UID = "DELETE FROM NBS_ui_metadata WHERE question_identifier is null AND nbs_page_uid=?";
	
	private static final String DELETE_NBS_UI_METADATA = "UPDATE NBS_UI_Metadata SET record_status_cd='Inactive', record_status_time=? ,ldf_status_cd='LDF_UPDATE', ldf_status_time=?, version_ctrl_nbr=?,last_chg_time=?,last_chg_user_id=? WHERE nbs_ui_metadata_uid=? ";
	
	private static final String INSERT_HISTORY = "INSERT INTO NBS_UI_Metadata_Hist(nbs_ui_metadata_uid,nbs_ui_component_uid, nbs_question_uid,parent_uid,question_label,block_nm, question_tool_tip,investigation_form_cd,enable_ind,default_value,display_ind,order_nbr,required_ind,tab_order_id,tab_name,add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,max_length,ldf_position,css_style,ldf_page_id,ldf_status_cd,admin_comment,ldf_status_time,version_ctrl_nbr,field_size,future_date_ind_cd,nbs_table_uid,code_set_group_id,data_cd,data_location,data_type,data_use_cd,legacy_data_location,part_type_cd,question_group_seq_nbr,question_identifier,question_oid,question_oid_system_txt,question_unit_identifier,repeats_ind_cd,unit_parent_identifier,group_nm,sub_group_nm,desc_txt,mask,min_value,max_value,nbs_page_uid,standard_nnd_ind_cd,unit_type_cd,unit_value,other_value_ind_cd, batch_table_appear_ind_cd, batch_table_header, batch_table_column_width) "
		                                       + "SELECT nbs_ui_metadata_uid,nbs_ui_component_uid, nbs_question_uid,parent_uid,question_label,block_nm,question_tool_tip,investigation_form_cd,enable_ind,default_value,display_ind,order_nbr,required_ind,tab_order_id,tab_name,add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,max_length,ldf_position,css_style,ldf_page_id,ldf_status_cd,admin_comment,ldf_status_time,version_ctrl_nbr,field_size,future_date_ind_cd,nbs_table_uid,code_set_group_id,data_cd,data_location,data_type,data_use_cd,legacy_data_location,part_type_cd,question_group_seq_nbr,question_identifier,question_oid,question_oid_system_txt,question_unit_identifier,repeats_ind_cd,unit_parent_identifier,group_nm,sub_group_nm,desc_txt,mask,min_value,max_value,nbs_page_uid,standard_nnd_ind_cd,unit_type_cd,unit_value,other_value_ind_cd, batch_table_appear_ind_cd, batch_table_header, batch_table_column_width FROM NBS_UI_Metadata ";
	
	private static final String WHERE_NBS_UI_METADATA_UID = " WHERE nbs_ui_metadata_uid = ?";
	private static final String WHERE_NBS_PAGE_UID = " WHERE nbs_page_uid = ?";
	
	/**
	 *  Returns NbsUiMetadataDT searching by metadataUid
	 * @param metadataUid
	 * @return NbsUiMetadataDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsUiMetadataDT findNBSUiMetadata(Long metadataUid) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsUiMetadataDT metadataDT = new NbsUiMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(metadataUid);
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(metadataDT, paramList, FIND_NBS_UI_METADATA, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsUiMetadataDT)paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return metadataDT;
		
	}
	
	/**
	 *  Returns NbsUiMetadataDT searching by Question Identifier
	 * @param questionIdentifier
	 * @return NbsUiMetadataDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsUiMetadataDT findNBSUiMetadataByQuestionIdentifier(String questionIdentifier, Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		if (questionIdentifier == null || questionIdentifier.trim().length() < 1 || nbsPageUid == null) {
			String error = "NBSUIMetaDataDAOImpl.findNBSUiMetadataByQuestionIdentifier: ERROR = questionIdentifier and/or nbsPageUid missing:  questionIdentifier=" + questionIdentifier + ", nbsPageUid=" + nbsPageUid;
			logger.fatal(error);
			throw new NEDSSSystemException(error);
		}

		
		NbsUiMetadataDT metadataDT = new NbsUiMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(questionIdentifier);
		paramList.add(nbsPageUid);

		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(metadataDT, paramList, FIND_NBS_UI_METADATA_BY_QUESTION_IDENTIFIER, NEDSSConstants.SELECT);
			if (paramList.size() == 1)
				return (NbsUiMetadataDT)paramList.get(0);
			else if (paramList.size() > 1)
				throw new NEDSSSystemException("More than one row returned from query.");
		} catch (Exception ex) {
			String error = "NBSUIMetaDataDAOImpl.findNBSUiMetadataByQuestionIdentifier: ERROR = " + ex + " Query: "+ FIND_NBS_UI_METADATA_BY_QUESTION_IDENTIFIER+ ",  questionIdentifier=" + questionIdentifier + ", nbsPageUid=" + nbsPageUid;
			logger.fatal(error,ex);
			throw new NEDSSSystemException(error);
		}
		return metadataDT;
	}
	
	
	/**
	 * creates an NBS UiMetadata
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public long createNBSUiMetadata(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			String requiredInd = dt.getRequiredInd() == null ? "" : (String) dt.getRequiredInd();
			if(requiredInd.equals("1")) 
				dt.setRequiredInd(NEDSSConstants.TRUE);
			else
				dt.setRequiredInd(NEDSSConstants.FALSE);
	
			String futureDateInd = dt.getFutureDateInd() == null ? "" : (String) dt.getFutureDateInd();
			if(futureDateInd.equals("1")) 
				dt.setFutureDateInd(NEDSSConstants.TRUE);
			else  
				dt.setFutureDateInd(NEDSSConstants.FALSE);
	
			
			return createNBSUiMetadataBase(dt);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	public Integer getVersionCtrlNbrForPage(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_VERSION_CTRL_NBR);
			preparedStmt.setLong(1, nbsPageUid);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1) + 1;
			} else {
				return 1;
			}
			
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNNDMetadataHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
	}
	
	public long createNBSUiMetadataBase(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		try {
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			nbsUiMetadataUid = uidGen.getNbsIDLong(UidClassCodes.NBS_UIMETEDATA_LDF_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("Exception while calling UID Generator for NBS_QUESTION_LDF: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());			
			
		}
	
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(new Long(nbsUiMetadataUid));
		paramList.add(dt.getNbsUiComponentUid());
		paramList.add(dt.getNbsQuestionUid());
		paramList.add(dt.getParentUid());
		paramList.add(dt.getQuestionLabel());
		paramList.add(dt.getBlockName());
		//paramList.add(dt.getDataMartRepeatNumber());
		paramList.add(dt.getQuestionToolTip());		
		paramList.add(dt.getInvestigationFormCd());
		paramList.add(dt.getEnableInd());
		paramList.add(dt.getDefaultValue());
		paramList.add(dt.getDisplayInd());
		paramList.add(dt.getOrderNbr());
		
		paramList.add(dt.getRequiredInd());
		
		paramList.add(dt.getTabOrderId());
		paramList.add(dt.getTabName());
		paramList.add(dt.getAddTime());
		paramList.add(dt.getAddUserId());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getRecordStatusCd());
		paramList.add(dt.getRecordStatusTime());
		paramList.add(dt.getMaxLength());
		paramList.add(dt.getLdfPosition());
		paramList.add(dt.getCssStyle());
		paramList.add(dt.getLdfPageId());
		paramList.add(dt.getLdfStatusCd());
		paramList.add(dt.getAdminComment());
		paramList.add(dt.getLdfStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getFieldSize());
		
		paramList.add(dt.getFutureDateInd());

		paramList.add(dt.getNbsTableUid());
		paramList.add(dt.getCodeSetGroupId());
		paramList.add(dt.getDataCd());
		paramList.add(dt.getDataLocation());
		paramList.add(dt.getDataType());
		paramList.add(dt.getDataUseCd());
		paramList.add(dt.getLegacyDataLocation());
		paramList.add(dt.getPartTypeCd());
		paramList.add(dt.getQuestionGroupSeqNbr());
		paramList.add(dt.getQuestionIdentifier());
		paramList.add(dt.getQuestionOid());
		paramList.add(dt.getQuestionOidSystemTxt());
		paramList.add(dt.getQuestionUnitIdentifier());
		paramList.add(dt.getRepeatsIndCd());
		paramList.add(dt.getUnitParentIdentifier());
		paramList.add(dt.getGroupNm());
		paramList.add(dt.getSubGroupNm());
		paramList.add(dt.getDescTxt());
		paramList.add(dt.getMask());
		paramList.add(dt.getNbsPageUid());
		paramList.add(dt.getStandardNndIndCd());
		paramList.add(dt.getUnitTypeCd());
		paramList.add(dt.getUnitValue());
		paramList.add(dt.getOtherValueIndCd());
		paramList.add(dt.getBatchTableAppearIndCd());
		paramList.add(dt.getBatchTableHeader());
		paramList.add(dt.getBatchTableColumnWidth());
		paramList.add(dt.getCoinfectionIndCd());
		//paramList.add(dt.getQuestionWithQuestionIdentifier());
		
		
		
		try {			
			int resultCount = ((Integer)preparedStmtMethod(null, paramList, CREATE_NBS_UI_METADATA, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1) {
                logger.error("Exception in createNBSUiMetadata: , " + "resultCount = " + resultCount);
    			throw new NEDSSSystemException("Exception in createNBSUiMetadata:");
            }			
		} catch (Exception ex) {
			logger.fatal("Exception in createNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
		return nbsUiMetadataUid;
	}
	public void updateNBSUiMetadata(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			String requiredInd = dt.getRequiredInd() == null ? "" : (String) dt.getRequiredInd();
			if(requiredInd.equals("1")) 
				dt.setRequiredInd(NEDSSConstants.TRUE);
			else
				dt.setRequiredInd(NEDSSConstants.FALSE);
	
			String futureDateInd = dt.getFutureDateInd() == null ? "" : (String) dt.getFutureDateInd();
			if(futureDateInd.equals("1")) 
				dt.setFutureDateInd(NEDSSConstants.TRUE);
			else
				dt.setFutureDateInd(NEDSSConstants.FALSE);
	
			updateNBSUiMetadataBase(dt);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and updating the current record
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateNBSUiMetadataBase(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//History
		updateNBSUiMetadataHistory(dt.getNbsUiMetadataUid());
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getNbsUiComponentUid());
		paramList.add(dt.getNbsQuestionUid());
		paramList.add(dt.getParentUid());
		paramList.add(dt.getQuestionLabel());
		paramList.add(dt.getBlockName());
		//paramList.add(dt.getDataMartRepeatNumber());
		paramList.add(dt.getQuestionToolTip());
		paramList.add(dt.getInvestigationFormCd());
		paramList.add(dt.getEnableInd());
		paramList.add(dt.getDefaultValue());
		paramList.add(dt.getDisplayInd());
		paramList.add(dt.getOrderNbr());
		paramList.add(dt.getRequiredInd());
		paramList.add(dt.getTabOrderId());
		paramList.add(dt.getTabName());
		paramList.add(dt.getAddTime());
		paramList.add(dt.getAddUserId());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getRecordStatusCd());
		paramList.add(dt.getRecordStatusTime());
		paramList.add(dt.getMaxLength());
		paramList.add(dt.getLdfPosition());
		paramList.add(dt.getCssStyle());
		paramList.add(dt.getLdfPageId());
		paramList.add(dt.getLdfStatusCd());
		paramList.add(dt.getAdminComment());
		paramList.add(dt.getLdfStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getFieldSize());
		paramList.add(dt.getFutureDateInd());
		paramList.add(dt.getNbsTableUid());
		paramList.add(dt.getCodeSetGroupId());
		paramList.add(dt.getDataCd());
		paramList.add(dt.getDataLocation());
		paramList.add(dt.getDataType());
		paramList.add(dt.getDataUseCd());
		paramList.add(dt.getLegacyDataLocation());
		paramList.add(dt.getPartTypeCd());
		paramList.add(dt.getQuestionGroupSeqNbr());
		paramList.add(dt.getQuestionIdentifier());
		paramList.add(dt.getQuestionOid());
		paramList.add(dt.getQuestionOidSystemTxt());
		paramList.add(dt.getQuestionUnitIdentifier());
		paramList.add(dt.getRepeatsIndCd());
		paramList.add(dt.getUnitParentIdentifier());
		paramList.add(dt.getGroupNm());
		paramList.add(dt.getSubGroupNm());
		paramList.add(dt.getDescTxt());
		paramList.add(dt.getMask());
		paramList.add(dt.getMinValue());
		paramList.add(dt.getMaxValue());
		paramList.add(dt.getNbsPageUid());
		paramList.add(dt.getStandardNndIndCd());
		paramList.add(dt.getUnitTypeCd());
		paramList.add(dt.getUnitValue());
		paramList.add(dt.getOtherValueIndCd());
		paramList.add(dt.getBatchTableAppearIndCd());
		paramList.add(dt.getBatchTableHeader());
		paramList.add(dt.getBatchTableColumnWidth());
		paramList.add(dt.getCoinfectionIndCd());
		//where param
		paramList.add(dt.getNbsUiMetadataUid());
		
		try {			
			preparedStmtMethod(null, paramList, UPDATE_NBS_UI_METADATA, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	/**
	 * Update NBSUiMetadata ldf_status comprises of INSERTING exiting row to HIST, and updating the current record
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateNBSUiMetadataLDFStatus(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//History
		updateNBSUiMetadataHistory(dt.getNbsUiMetadataUid());
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getLdfStatusCd());
		paramList.add(dt.getLdfStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		
		//where param
		paramList.add(dt.getNbsUiMetadataUid());
		
		try {			
			preparedStmtMethod(null, paramList, UPDATE_NBS_UI_METADATA_LDF_STATUS, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	
	/**
	 * Delete NBS_ui_metadata by nbs_ui_metadata_uid
	 * @param NbsUiMetadataDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteNBSUiMetadata(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//History
		updateNBSUiMetadataHistory(dt.getNbsUiMetadataUid());
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getRecordStatusTime());
		paramList.add(dt.getLdfStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		//where param
		paramList.add(dt.getNbsUiMetadataUid());
		try {			
			preparedStmtMethod(null, paramList, DELETE_NBS_UI_METADATA, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in deleteNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}			
		
	}

	/**
	 * Delete NBS_ui_metadata by nbs_page_uid
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteStaticElementByNbsPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object> ();
	
		//where param
		paramList.add(nbsPageUid);
		try {			
			preparedStmtMethod(null, paramList, DELETE_STATIC_ELEMENTS_BY_NBS_PAGE_UID, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in deleteNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}			
	}	
	
	
	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata history table before updated
	 * @param questionUid
	 */
	public void updateNBSUiMetadataHistory(Long nbsUiMetadataUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param
		paramList.add(nbsUiMetadataUid);
		
		try {			
			preparedStmtMethod(null, paramList, INSERT_HISTORY+WHERE_NBS_UI_METADATA_UID, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSUiMetadataHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}	

	public void updateNBSUiMetadataHistoryByNbsPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param
		paramList.add(nbsPageUid);

		try {
			preparedStmtMethod(null, paramList, INSERT_HISTORY+WHERE_NBS_PAGE_UID, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSUiMetadataHistoryByNbsPageUid: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} 
	}

	
}