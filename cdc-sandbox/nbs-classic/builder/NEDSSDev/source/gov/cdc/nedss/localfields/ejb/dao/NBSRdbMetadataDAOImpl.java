package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsRdbMetadataDT;
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
 * NBSRdbMetadataDAOImpl used to Load, Store or Delete NBS RdbMetadata Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSRdbMetadataDAOImpl.java
 * @version
 */
public class NBSRdbMetadataDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(NBSRdbMetadataDAOImpl.class.getName());

	private static final String SELECT_NBS_RDB_METADATA = "SELECT  "
														+ "nbs_rdb_metadata_uid \"nbsRdbMetadataUid\", "
		                                                + "nbs_page_uid \"nbsPageUid\", "
		                                                + "nbs_ui_metadata_uid \"nbsUiMetadataUid\", "
		                                                + "record_status_cd \"recordStatusCd\", "
		                                                + "record_status_time \"recordStatusTime\", "
		                                                + "last_chg_user_id \"lastChgUserId\", "
		                                                + "last_chg_time \"lastChgTime\", "
		                                                + "local_id \"localId\", "
		                                                + "rpt_admin_column_nm \"rptAdminColumnNm\", "
		                                                + "rdb_table_nm \"rdbTableNm\" "
												        + "user_defined_column_nm \"userDefinedColumnNm\" "
		                                                + "FROM nbs_rdb_metadata ";

	private static final String WHERE_NBS_RDB_METADATA_UID = "WHERE nbs_rdb_metadata_uid=? ";


	private static final String CREATE_NBS_RDB_METADATA = "INSERT INTO NBS_rdb_metadata "
												        + "(nbs_rdb_metadata_uid, "
												        + "nbs_page_uid, "
												        + "nbs_ui_metadata_uid, "
												        + "record_status_cd, "
												        + "record_status_time, "
												        + "last_chg_user_id, "
												        + "last_chg_time, "
												        + "local_id, "
												        + "rpt_admin_column_nm, "
												        + "rdb_table_nm, "
												        + "user_defined_column_nm, " 
												        + "block_pivot_nbr, " 
												        + "rdb_column_nm) "
												        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_NBS_RDB_METADATA = "UPDATE nbs_rdb_metadata SET "
														+ "nbs_page_uid=?, "
													    + "record_status_cd=?, "
													    + "record_status_time=?, "
													    + "last_chg_user_id=?, "
													    + "last_chg_time=?, "
													    + "local_id=?, "
													    + "rpt_admin_column_nm=?, "
													    + "rdb_table_nm=?, "
														+ "user_defined_column_nm=?, "
													    + "block_pivot_nbr=? "
														+ "rdb_column_nm=? ";


	private final String INSERT_RDB_METADATA_HISTORY = "INSERT INTO NBS_rdb_metadata_hist(nbs_rdb_metadata_uid, nbs_page_uid, nbs_ui_metadata_uid, record_status_cd, record_status_time, last_chg_user_id, last_chg_time, local_id, rpt_admin_column_nm, rdb_table_nm, user_defined_column_nm, rdb_column_nm, block_pivot_nbr, version_ctrl_nbr)"
	                                                 + " SELECT nbs_rdb_metadata_uid, nbs_page_uid, nbs_ui_metadata_uid, record_status_cd, record_status_time, last_chg_user_id, last_chg_time, local_id, rpt_admin_column_nm, rdb_table_nm, user_defined_column_nm, rdb_column_nm, block_pivot_nbr, ? from NBS_rdb_metadata"
	                                                 + " where nbs_rdb_metadata_uid = ? ";

	private final String INSERT_RDB_METADATA_HISTORY_BY_NBS_PAGE_UID = "INSERT INTO NBS_rdb_metadata_hist(nbs_rdb_metadata_uid, nbs_page_uid, nbs_ui_metadata_uid, record_status_cd, record_status_time, last_chg_user_id, last_chg_time, local_id, rpt_admin_column_nm, rdb_table_nm, user_defined_column_nm, rdb_column_nm, block_pivot_nbr, version_ctrl_nbr)"
        + " SELECT nbs_rdb_metadata_uid, nbs_page_uid, nbs_ui_metadata_uid, record_status_cd, record_status_time, last_chg_user_id, last_chg_time, local_id, rpt_admin_column_nm, rdb_table_nm, user_defined_column_nm, rdb_column_nm, block_pivot_nbr, ? from NBS_rdb_metadata"
        + " where nbs_page_uid = ? ";

	
	private final String SELECT_MAX_HISTORY_VERSION = "SELECT MAX(version_ctrl_nbr) FROM NBS_rdb_metadata_hist WHERE (nbs_page_uid = ?)";

	private final String DELETE_RDB_METADATA_FOR_PAGE = "DELETE FROM nbs_rdb_metadata where nbs_page_uid = ?";
	
	/**
	 * Returns a NbsRdbMetadataDT searching by nbsRdbMetadataUid
	 * @param nbsRdbMetadataUid
	 * @return NbsRdbMetadataDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsRdbMetadataDT findNBSRdbMetadata(Long nbsRdbMetadataUid) throws NEDSSDAOSysException, NEDSSSystemException {

		NbsRdbMetadataDT rdbMetadataDT = new NbsRdbMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsRdbMetadataUid);

		try {
			paramList = (ArrayList<Object> ) preparedStmtMethod(rdbMetadataDT, paramList, SELECT_NBS_RDB_METADATA + WHERE_NBS_RDB_METADATA_UID, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsRdbMetadataDT)paramList.get(0);

		} catch (Exception ex) {
			logger.fatal("Exception in findNBSRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return rdbMetadataDT;

	}


	public void deleteByNbsPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsPageUid);

		try {
			preparedStmtMethod(null, paramList, DELETE_RDB_METADATA_FOR_PAGE, NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		
	}
	 
	public long createNBSRdbMetadata(NbsRdbMetadataDT nbsRdbMetadataDT) throws NEDSSDAOSysException, NEDSSSystemException {
		Long nbsRdbMetadataUid = null;
		
		try {
			//Generate questionUid
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			nbsRdbMetadataUid = uidGen.getNbsIDLong(UidClassCodes.NND_METADATA_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("NNDMetadataDAOImpl.createNNDMetadata - Exception while calling UID Generator for NND_METADATA: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}

		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsRdbMetadataUid);
		paramList.add(nbsRdbMetadataDT.getNbsPageUid());
		paramList.add(nbsRdbMetadataDT.getNbsUiMetadataUid());
		paramList.add(nbsRdbMetadataDT.getRecordStatusCd());
		paramList.add(nbsRdbMetadataDT.getRecordStatusTime());
		paramList.add(nbsRdbMetadataDT.getLastChgUserId());
		paramList.add(nbsRdbMetadataDT.getLastChgTime());
		paramList.add(nbsRdbMetadataDT.getLocalId());
		paramList.add(nbsRdbMetadataDT.getRptAdminColumnNm());
		paramList.add(nbsRdbMetadataDT.getRdbTableNm());
		paramList.add(nbsRdbMetadataDT.getUserDefinedColumnNm());
		paramList.add(nbsRdbMetadataDT.getDataMartRepeatNbr());
		paramList.add(nbsRdbMetadataDT.getRdbColumnNm());
		

		try {
			int resultCount = ((Integer)preparedStmtMethod(null, paramList, CREATE_NBS_RDB_METADATA, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1) {
                logger.error("Exception in createNBSRdbMetadata: , " + "resultCount = " + resultCount);
    			throw new NEDSSSystemException("Exception in createNBSRdbMetadata:");
            }

		} catch (Exception ex) {
			logger.error("Exception in createNBSRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsRdbMetadataUid;
	}

	public void updateNBSRdbMetadata(NbsRdbMetadataDT nbsRdbMetadataDT) throws NEDSSDAOSysException, NEDSSSystemException {

		//History
		updateNBSRdbMetadataHistory(nbsRdbMetadataDT.getNbsRdbMetadataUid(), nbsRdbMetadataDT.getNbsPageUid());

		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsRdbMetadataDT.getNbsPageUid());
		paramList.add(nbsRdbMetadataDT.getNbsUiMetadataUid());
		paramList.add(nbsRdbMetadataDT.getRecordStatusCd());
		paramList.add(nbsRdbMetadataDT.getRecordStatusTime());
		paramList.add(nbsRdbMetadataDT.getLastChgUserId());
		paramList.add(nbsRdbMetadataDT.getLastChgTime());
		paramList.add(nbsRdbMetadataDT.getLocalId());
		paramList.add(nbsRdbMetadataDT.getRptAdminColumnNm());
		paramList.add(nbsRdbMetadataDT.getRdbTableNm());
		paramList.add(nbsRdbMetadataDT.getNbsRdbMetadataUid());
		paramList.add(nbsRdbMetadataDT.getUserDefinedColumnNm());
		paramList.add(nbsRdbMetadataDT.getDataMartRepeatNbr());
		paramList.add(nbsRdbMetadataDT.getRdbColumnNm());
		

		try {
			preparedStmtMethod(null, paramList, UPDATE_NBS_RDB_METADATA, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void updateNBSRdbMetadataHistoryByNbsPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		int nextVersionCtrlNbr = 1; 

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		ArrayList<Object> paramList = new ArrayList<Object> ();

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_HISTORY_VERSION);
			preparedStmt.setLong(1, nbsPageUid);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				nextVersionCtrlNbr = resultSet.getInt(1) + 1;
			}
			
			paramList.add(nextVersionCtrlNbr);
			paramList.add(nbsPageUid);
			preparedStmtMethod(null, paramList, INSERT_RDB_METADATA_HISTORY_BY_NBS_PAGE_UID, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSRdbMetadataHistoryByNbsPageUid: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public void updateNBSRdbMetadataHistory(Long nbsRdbMetadataUid, Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		int nextVersionCtrlNbr = 1; 

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		ArrayList<Object> paramList = new ArrayList<Object> ();

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_HISTORY_VERSION);
			preparedStmt.setLong(1, nbsPageUid);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				nextVersionCtrlNbr = resultSet.getInt(1) + 1;
			}

			paramList.add(nextVersionCtrlNbr);
			paramList.add(nbsRdbMetadataUid);
			
			
			preparedStmtMethod(null, paramList, INSERT_RDB_METADATA_HISTORY, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSRdbMetadataHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}


}