package gov.cdc.nedss.ldf.importer.dao;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.importer.dt.CdfSubformImportDataLogDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;

public class CdfSubformImportDataLogDAOImpl extends DAOBase {

	static final LogUtils logger =
		new LogUtils(CdfSubformImportDataLogDAOImpl.class.getName());
	private static String selectStmt =
		"SELECT "
			+ "import_log_uid \"importLogUid\", "
			+ "data_oid \"dataOid\", "
			+ "data_type \"dataType\", "
			+ "action_type \"actionType\", "
			+ "import_time \"importTime\", "
			+ "log_message_txt \"logMessageTxt\", "
			+ "process_cd \"processCd\", "
			+ "version_ctrl_nbr \"versionCtrlNbr\" "
			+ "from CDF_SUBFORM_IMPORT_DATA_LOG "
			+ "WHERE import_log_uid = ? ";

	private static String updateStmt =
		"UPDATE CDF_SUBFORM_IMPORT_DATA_LOG SET "
			+ "version_ctrl_nbr = ?,"
			+ "data_oid = ?, "
			+ "data_type = ?, "
			+ "action_type = ?, "
			+ "import_time = ?, "
			+ "log_message_txt = ?, "
			+ "process_cd = ?, "
			+ "WHERE "
			+ "import_log_uid = ? "
			+ "version_ctrl_nbr = ? ";

	private static String deleteStmt =
		"delete from CDF_SUBFORM_IMPORT_DATA_LOG where import_log_uid = ? ";

	private static String insertStmt =
		"INSERT INTO CDF_SUBFORM_IMPORT_DATA_LOG ( "
			+ "import_log_uid, "
			+ "data_oid, "
			+ "data_type, "
			+ "action_type, "
			+ "import_time, "
			+ "log_message_txt, "
			+ "process_cd, "
			+ "version_ctrl_nbr "
			+ " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

	@SuppressWarnings("unchecked")
	public Collection<Object>  select(Long importLogUid) throws NEDSSAppException {
		CdfSubformImportDataLogDT cdfSubformImportDataLog =
			new CdfSubformImportDataLogDT();

		ArrayList<Object> arrayList = new ArrayList<Object> ();

		arrayList.add(importLogUid);

		try {
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(cdfSubformImportDataLog,
					arrayList,
					selectStmt,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportDataLog.select(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return arrayList;
	}

	public void update(CdfSubformImportDataLogDT cdfSubformImportDataLog)
		throws NEDSSAppException, NEDSSConcurrentDataException {

		ArrayList<Object> valueList = new ArrayList<Object> ();
		try {
			if (cdfSubformImportDataLog != null) {
				valueList.add(
					new Integer(
						cdfSubformImportDataLog.getVersionCtrlNbr().intValue()
							+ 1));
				valueList.add(cdfSubformImportDataLog.getDataOid());
				valueList.add(cdfSubformImportDataLog.getDataType());
				valueList.add(cdfSubformImportDataLog.getActionType());
				valueList.add(cdfSubformImportDataLog.getImportTime());
				valueList.add(cdfSubformImportDataLog.getLogMessageTxt());
				valueList.add(cdfSubformImportDataLog.getProcessCd());
				valueList.add(cdfSubformImportDataLog.getImportLogUid());
				valueList.add(cdfSubformImportDataLog.getVersionCtrlNbr());

				int resultCount =
					((Integer) preparedStmtMethod(null,
						valueList,
						updateStmt,
						NEDSSConstants.UPDATE))
						.intValue();
				if (resultCount != 1) {
					logger.error(
						"Error: none or more than one CdfSubformImportDataLog updated at a time, "
							+ "resultCount = "
							+ resultCount);
					throw new NEDSSConcurrentDataException("Update failed");
				}
			}
		} catch (Exception e) {
			logger.fatal(
				"Exception in CdfSubformImportDataLog.update(): Error = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
	}

	public void delete(Long importLogUid)
		throws NEDSSAppException, NEDSSConcurrentDataException {
		CdfSubformImportDataLogDT cdfSubformImportDataLog =
			new CdfSubformImportDataLogDT();
		ArrayList<Object> arrayList = new ArrayList<Object> ();
		arrayList.add(importLogUid);
		try {
			preparedStmtMethod(
				cdfSubformImportDataLog,
				arrayList,
				deleteStmt,
				NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportDataLog.delete(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void insert(CdfSubformImportDataLogDT cdfSubformImportDataLog)
		throws NEDSSAppException {

		ArrayList<Object> valueList = new ArrayList<Object> ();
		Integer VERSIONCTLNBR = new Integer(1);
		try {
			if (cdfSubformImportDataLog != null) {
				valueList.add(cdfSubformImportDataLog.getImportLogUid());
				valueList.add(cdfSubformImportDataLog.getDataOid());
				valueList.add(cdfSubformImportDataLog.getDataType());
				valueList.add(cdfSubformImportDataLog.getActionType());
				valueList.add(cdfSubformImportDataLog.getImportTime());
				valueList.add(cdfSubformImportDataLog.getLogMessageTxt());
				valueList.add(cdfSubformImportDataLog.getProcessCd());
				valueList.add(VERSIONCTLNBR);
				int resultCount =
					((Integer) preparedStmtMethod(null,
						valueList,
						insertStmt,
						NEDSSConstants.UPDATE))
						.intValue();
				if (resultCount != 1) {
					logger.error(
						"Error: none or more than one CdfSubformImportDataLog updated at a time, "
							+ "resultCount = "
							+ resultCount);
					throw new NEDSSConcurrentDataException("Insert failed");
				}
			}
		} catch (Exception e) {
			logger.fatal(
				"Exception in CdfSubformImportDataLog.insert(): Error = " + e.getMessage(), e );
			throw new NEDSSSystemException(e.toString());
		}
	}

}