package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * NBSPageDAOImpl used to Load, Store or Delete NBS Page Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSPageDAOImpl.java
 * @version
 */
public class NBSPageDAOImpl extends DAOBase {
	private static final LogUtils logger = new LogUtils(NBSPageDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	private static final String SELECT_NBS_PAGE = "SELECT  "
												+ "nbs_page_uid \"nbsPageUid\", "
												+ "wa_template_uid \"waTemplateUid\", "
												+ "form_cd \"formCd\", "
												+ "desc_txt \"descTxt\", "
												+ "jsp_payload \"jpsPayload\", "
												+ "datamart_nm \"datamartNm\", "
												+ "local_id \"localId\", "
												+ "bus_obj_type \"busObjType\", "
												+ "last_chg_user_id \"lastChgUserId\", "
												+ "last_chg_time \"lastChgTime\", "
												+ "record_status_cd \"recordStatusCd\", "
												+ "record_status_time \"recordStatusTime\" "
											    + "FROM NBS_page ";
													
	private static final String WHERE_NBS_PAGE_UID = "WHERE nbs_page_uid=? ";
	private static final String WHERE_WA_TEMPLATE_UID = "WHERE wa_template_uid=? ";
	private static final String WHERE_WA_FORM_CD = "WHERE form_cd=? ";

	private static final String CREATE_NBS_PAGE_SQL = "INSERT INTO NBS_page(nbs_page_uid,wa_template_uid,form_cd,desc_txt,jsp_payload,datamart_nm,local_id,bus_obj_type,last_chg_user_id,last_chg_time,record_status_cd,record_status_time) " 
												+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_NBS_PAGE_SQL = "UPDATE NBS_PAGE SET wa_template_uid=?,form_cd=?,desc_txt=?,jsp_payload=?,datamart_nm=?,local_id=?,bus_obj_type=?,last_chg_user_id=?,last_chg_time=?,record_status_cd=?,record_status_time=? ";
	
	private final String INSERT_PAGE_HISTORY_SQL = "INSERT INTO NBS_Page_Hist(nbs_page_uid,wa_template_uid,form_cd,desc_txt,jsp_payload,datamart_nm,local_id,bus_obj_type,last_chg_user_id,last_chg_time,record_status_cd,record_status_time,version_ctrl_nbr)"
		+ " SELECT nbs_page_uid,wa_template_uid,form_cd,desc_txt,jsp_payload,datamart_nm,local_id,bus_obj_type,last_chg_user_id,last_chg_time,record_status_cd,record_status_time, ISNULL((select max(version_ctrl_nbr)+1 from NBS_Page_Hist where nbs_page_uid = ? ),1 ) FROM NBS_Page"
		+ " where nbs_page_uid = ? ";	

	private static final String SELECT_NBS_PAGE_LAST_CHG_TIME = "SELECT  "
			+ "nbs_page_uid \"nbsPageUid\", "
			+ "wa_template_uid \"waTemplateUid\", "
			+ "form_cd \"formCd\", "
			+ "desc_txt \"descTxt\", "
			+ "bus_obj_type \"busObjType\", "
			+ "last_chg_time \"lastChgTime\", "
			+ "record_status_cd \"recordStatusCd\", "
			+ "record_status_time \"recordStatusTime\" "
		    + "FROM NBS_page WTIH (NOLOCK) "
			+ "WHERE form_cd=? ";
	
	  /**
	 * Returns a NbsPageDT searching by nbsPageUid
	 * @param nbsPageUid
	 * @return NbsPageDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsPageDT findNBSPage(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			NbsPageDT pageDT = new NbsPageDT();
			ArrayList<Object> paramList = new ArrayList<Object> ();
			paramList.add(nbsPageUid);
			
			try {			
				paramList = (ArrayList<Object> ) preparedStmtMethod(pageDT, paramList, SELECT_NBS_PAGE + WHERE_NBS_PAGE_UID, NEDSSConstants.SELECT);
				if(paramList.size() > 0)
					return (NbsPageDT)paramList.get(0);
				
			} catch (Exception ex) {
				logger.fatal("Exception in findNBSPage: ERROR = " + ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return pageDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
	}
	
	/**
	 * Returns a NbsPageDT searching by waTemplateUid
	 * @param waTemplateUid
	 * @return NbsPageDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsPageDT findNBSPageByTemplateUid(Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsPageDT pageDT = new NbsPageDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(waTemplateUid);
		
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(pageDT, paramList, SELECT_NBS_PAGE + WHERE_WA_TEMPLATE_UID, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsPageDT)paramList.get(0);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSPageByTemplateUid: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pageDT;
		
	}
	
	@SuppressWarnings("unchecked")
	public NbsPageDT findNBSPageByConditionCd(String formCode) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsPageDT pageDT = new NbsPageDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(formCode);
		
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(pageDT, paramList, SELECT_NBS_PAGE + WHERE_WA_FORM_CD, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsPageDT)paramList.get(0);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSPageByConitionCd: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pageDT;
		
	}
		
	public Long createNBSPage(NbsPageDT pageDT) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			return createNBSPageSQL(pageDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	
	private long createNBSPageSQL(NbsPageDT pageDT) throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Long nbsPageUid = null;

		try {
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			nbsPageUid = uidGen.getNbsIDLong(UidClassCodes.NBS_PAGE_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("createNBSPageSQL - Exception while calling UID Generator for NBS_Page: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.toString());			
			
		}
		
		logger.debug("CREATE_NBS_PAGE_SQL="+CREATE_NBS_PAGE_SQL);
		
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for createNBSPageSQL ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		
		ResultSet rs = null;
		try {

			
			preparedStmt = dbConnection.prepareStatement(CREATE_NBS_PAGE_SQL);
			int i = 1;

			preparedStmt.setLong(i++,  nbsPageUid);
			preparedStmt.setLong(i++,  pageDT.getWaTemplateUid());
			preparedStmt.setString(i++,  pageDT.getFormCd());
			preparedStmt.setString(i++,  pageDT.getDescTxt());
			preparedStmt.setBytes(i++,  pageDT.getJspPayload());
			preparedStmt.setString(i++,  pageDT.getDatamartNm());
			preparedStmt.setString(i++,  pageDT.getLocalId());
			preparedStmt.setString(i++,  pageDT.getBusObjType());
			preparedStmt.setLong(i++,  pageDT.getLastChgUserId());
			preparedStmt.setTimestamp(i++,  pageDT.getLastChgTime());
			preparedStmt.setString(i++,  pageDT.getRecordStatusCd());
			preparedStmt.setTimestamp(i++,  pageDT.getRecordStatusTime());

			resultCount = preparedStmt.executeUpdate();      

			logger.debug("resultCount in createNBSPageSQL is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting pageDT into NBS_page: " + pageDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into NBS_page, pageDT = " + pageDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return nbsPageUid;
	}

	public void updateNBSPage(NbsPageDT pageDT) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			updateNBSPageSQL(pageDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private void updateNBSPageSQL(NbsPageDT pageDT) throws NEDSSDAOSysException, NEDSSSystemException {
		//History
		updateNBSPageHistory(pageDT.getNbsPageUid());

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		
		logger.debug("UPDATE_NBS_PAGE_SQL+WHERE_NBS_PAGE_UID="+UPDATE_NBS_PAGE_SQL+WHERE_NBS_PAGE_UID);
		
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for updateNBSPageSQL ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		
		ResultSet rs = null;
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_NBS_PAGE_SQL+WHERE_NBS_PAGE_UID);
			int i = 1;

			preparedStmt.setLong(i++,  pageDT.getWaTemplateUid());
			preparedStmt.setString(i++,  pageDT.getFormCd());
			preparedStmt.setString(i++,  pageDT.getDescTxt());
			preparedStmt.setBytes(i++,  pageDT.getJspPayload());
			preparedStmt.setString(i++,  pageDT.getDatamartNm());
			preparedStmt.setString(i++,  pageDT.getLocalId());
			preparedStmt.setString(i++,  pageDT.getBusObjType());
			preparedStmt.setLong(i++,  pageDT.getLastChgUserId());
			preparedStmt.setTimestamp(i++,  pageDT.getLastChgTime());
			preparedStmt.setString(i++,  pageDT.getRecordStatusCd());
			preparedStmt.setTimestamp(i++,  pageDT.getRecordStatusTime());
			preparedStmt.setLong(i++,  pageDT.getNbsPageUid());

			resultCount = preparedStmt.executeUpdate();      

			logger.debug("resultCount in updateNBSPage is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating pageDT into NBS_page: " + pageDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating into NBS_page, pageDT = " + pageDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	
	}
	
	
	public void updateNBSPageHistory(Long pageUid) {
		try{
			updateNBSPageHistorySQL(pageUid);
		}catch(Exception ex){
			logger.error("Exception ="+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	private void updateNBSPageHistorySQL(Long pageUid) {
		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param
		paramList.add(pageUid);
		paramList.add(pageUid);
		
		try {			
			preparedStmtMethod(null, paramList, INSERT_PAGE_HISTORY_SQL, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSPageHistory: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
		
	/**
	 * Use to check if published page in Nedss\pagemanagement is modified before last_modified_time in Nbs_page table.
	 * 
	 * @param formCd
	 * @return NbsPageDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsPageDT findNBSPageDetailsExceptJspPayload(String formCd) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			NbsPageDT pageDT = new NbsPageDT();
			ArrayList<Object> paramList = new ArrayList<Object> ();
			paramList.add(formCd);
			
			paramList = (ArrayList<Object> ) preparedStmtMethod(pageDT, paramList, SELECT_NBS_PAGE_LAST_CHG_TIME, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsPageDT)paramList.get(0);
				
			return pageDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("formCd "+formCd+", NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("formCd "+formCd+", Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
	}
	
}