package gov.cdc.nedss.ldf.importer.dao;

import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDBUniqueKeyViolation;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.importer.dt.CdfSubformImportLogDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;

public class CdfSubformImportLogDAOImpl extends DAOBase {
	static final LogUtils logger =
		new LogUtils((CdfSubformImportLogDAOImpl.class).getName());

	private static String selectStmt =
		"SELECT "
			+ "IMPORT_LOG_UID \"importLogUid\", "
			+ "ADMIN_COMMENT \"adminComment\", "
			+ "SOURCE_NM \"sourceNm\", "
			+ "IMPORT_VERSION_NBR \"importVersionNbr\", "
			+ "IMPORT_TIME \"importTime\", "
			+ "PROCESS_CD \"processCd\", "
			+ "LOG_MESSAGE_TXT \"logMessageTxt\", "
			+ "VERSION_CTRL_NBR \"versionCtrlNbr\" "
			+ "FROM CDF_SUBFORM_IMPORT_LOG"
			+ "WHERE IMPORT_LOG_UID = ? ";

	private static String selectAllStmt =
		"SELECT "
			+ "IMPORT_LOG_UID \"importLogUid\", "
			+ "ADMIN_COMMENT \"adminComment\", "
			+ "SOURCE_NM \"sourceNm\", "
			+ "IMPORT_VERSION_NBR \"importVersionNbr\", "
			+ "IMPORT_TIME \"importTime\", "
			+ "PROCESS_CD \"processCd\", "
			+ "LOG_MESSAGE_TXT \"logMessageTxt\", "
			+ "VERSION_CTRL_NBR \"versionCtrlNbr\" "
			+ "FROM CDF_SUBFORM_IMPORT_LOG order by IMPORT_VERSION_NBR ";

	private static String selectStmtByImportTime =
		"SELECT "
			+ "IMPORT_LOG_UID \"importLogUid\", "
			+ "ADMIN_COMMENT \"adminComment\", "
			+ "SOURCE_NM \"sourceNm\", "
			+ "IMPORT_VERSION_NBR \"importVersionNbr\", "
			+ "IMPORT_TIME \"importTime\", "
			+ "PROCESS_CD \"processCd\", "
			+ "LOG_MESSAGE_TXT \"logMessageTxt\", "
			+ "VERSION_CTRL_NBR \"versionCtrlNbr\" "
			+ "FROM CDF_SUBFORM_IMPORT_LOG "
			+ "where Import_time = (select max(IMPORT_TIME) from CDF_SUBFORM_IMPORT_LOG) ";

	private static String selectStmtByVersion =
		"SELECT "
			+ "IMPORT_LOG_UID \"importLogUid\", "
			+ "ADMIN_COMMENT \"adminComment\", "
			+ "SOURCE_NM \"sourceNm\", "
			+ "IMPORT_TIME \"importTime\", "
			+ "PROCESS_CD \"processCd\", "
			+ "LOG_MESSAGE_TXT \"logMessageTxt\", "
			+ "VERSION_CTRL_NBR \"versionCtrlNbr\" "
			+ "FROM CDF_SUBFORM_IMPORT_LOG "
			+ "where IMPORT_VERSION_NBR = ?";

	private static String updateStmt =
		" update CDF_SUBFORM_IMPORT_LOG set "
			+ "ADMIN_COMMENT=? , "
			+ "SOURCE_NM=? , "
			+ "IMPORT_VERSION_NBR=? , "
			+ "IMPORT_TIME=? , "
			+ "PROCESS_CD=? , "
			+ "LOG_MESSAGE_TXT=? , "
			+ "VERSION_CTRL_NBR=?"
			+ " WHERE "
			+ "IMPORT_LOG_UID=? "
			+ "AND VERSION_CTRL_NBR = ? ";

	private static String deleteStmt =
		"delete from CDF_SUBFORM_IMPORT_LOG where IMPORT_LOG_UID = ? ";

	private static String insertStmt =
		" insert into CDF_SUBFORM_IMPORT_LOG("
			+ "IMPORT_LOG_UID,"
			+ "ADMIN_COMMENT,"
			+ "SOURCE_NM,"
			+ "IMPORT_VERSION_NBR,"
			+ "IMPORT_TIME,"
			+ "PROCESS_CD,"
			+ "LOG_MESSAGE_TXT,"
			+ "VERSION_CTRL_NBR"
			+ ")values(?,?,?,?,?,?,?,?)";

	@SuppressWarnings("unchecked")
	public CdfSubformImportLogDT select(Long importLogUid) {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();

		ArrayList<Object> arrayList = new ArrayList<Object> ();

		arrayList.add(importLogUid);

		try {
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(cdfSubformImportLog,
					arrayList,
					selectStmt,
					NEDSSConstants.SELECT);
			if (arrayList.size() != 0) {
				cdfSubformImportLog = (CdfSubformImportLogDT) arrayList.get(0);

			}
			return cdfSubformImportLog;
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportLog.select(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectAllVersion() {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();

		ArrayList<Object> arrayList = new ArrayList<Object> ();
		ArrayList<Object> versionColl = new ArrayList<Object> ();

		try {
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(cdfSubformImportLog,
					arrayList,
					selectAllStmt,
					NEDSSConstants.SELECT);

			for (int i = 0; i < arrayList.size(); i++) {
				cdfSubformImportLog = (CdfSubformImportLogDT) arrayList.get(i);
				if (cdfSubformImportLog != null
					&& (cdfSubformImportLog.getImportVersionNbr() != null)) {
					versionColl.add(cdfSubformImportLog.getImportVersionNbr());
				}
			}
			return versionColl;

		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportLog.select(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public CdfSubformImportLogDT selectByVersion(Long importVersionNbr) {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();

		ArrayList<Object> arrayList = new ArrayList<Object> ();

		arrayList.add(importVersionNbr);

		try {
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(cdfSubformImportLog,
					arrayList,
					selectStmtByVersion,
					NEDSSConstants.SELECT);
			if (arrayList.size() != 0) {
				cdfSubformImportLog = (CdfSubformImportLogDT) arrayList.get(0);

			}
			return cdfSubformImportLog;
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportLog.select(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public CdfSubformImportLogDT selectbyImportTime() {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();

		ArrayList<Object> arrayList = new ArrayList<Object> ();

		try {
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(cdfSubformImportLog,
					arrayList,
					selectStmtByImportTime,
					NEDSSConstants.SELECT);
			if (arrayList.size() != 0) {
				cdfSubformImportLog = (CdfSubformImportLogDT) arrayList.get(0);

			}
			return cdfSubformImportLog;
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportLog.select(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void update(CdfSubformImportLogDT cdfSubformImportLog)
		throws NEDSSConcurrentDataException {

		ArrayList<Object> valueList = new ArrayList<Object> ();
		try {
			if (cdfSubformImportLog != null) {
				// increase the version control number by 1
				Integer versionCtrlNbr =
					cdfSubformImportLog.getVersionCtrlNbr() == null
						? new Integer(1)
						: new Integer(
							cdfSubformImportLog.getVersionCtrlNbr().intValue()
								+ 1);
				cdfSubformImportLog.setVersionCtrlNbr(versionCtrlNbr);

				valueList.add(cdfSubformImportLog.getAdminComment());
				valueList.add(cdfSubformImportLog.getSourceNm());
				valueList.add(cdfSubformImportLog.getImportVersionNbr());
				valueList.add(cdfSubformImportLog.getImportTime());
				valueList.add(cdfSubformImportLog.getProcessCd());
				valueList.add(cdfSubformImportLog.getLogMessageTxt());
				valueList.add(cdfSubformImportLog.getVersionCtrlNbr());
				valueList.add(cdfSubformImportLog.getImportLogUid());
				valueList.add(
					new Integer(
						cdfSubformImportLog.getVersionCtrlNbr().intValue()
							- 1));
				int resultCount =
					((Integer) preparedStmtMethod(null,
						valueList,
						updateStmt,
						NEDSSConstants.UPDATE))
						.intValue();
				if (resultCount != 1) {
					logger.error(
						"Error: none or more than one CdfSubformImportLog updated at a time, "
							+ "resultCount = "
							+ resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");

				}
			}
		} catch (Exception e) {
			logger.fatal(
				"Exception in CdfSubformImportLog.update(): Error = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
	}

	public void delete(Long importLogUid) {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();
		ArrayList<Object> arrayList = new ArrayList<Object> ();
		arrayList.add(importLogUid);
		try {
			preparedStmtMethod(
				cdfSubformImportLog,
				arrayList,
				deleteStmt,
				NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			logger.fatal(
				"Exception in CdfSubformImportLog.delete(): Error = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public long insert(CdfSubformImportLogDT cdfSubformImportLog)
		throws NEDSSConcurrentDataException, NEDSSDBUniqueKeyViolation {

		if (cdfSubformImportLog == null) {
			throw new NEDSSSystemException("Null input parameter for CdfSubformImportLog.insert()");
		}

		Long uid = new Long(0);
		UidGeneratorHelper uidGen = null;
		cdfSubformImportLog.setVersionCtrlNbr(new Integer(1));
		Long importVersionNbr = cdfSubformImportLog.getImportVersionNbr();

		ArrayList<Object> valueList = new ArrayList<Object> ();
		try {

			uidGen = new UidGeneratorHelper();
			// new uid
			uid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE);
			cdfSubformImportLog.setImportLogUid(uid);

			valueList.add(cdfSubformImportLog.getImportLogUid());
			valueList.add(cdfSubformImportLog.getAdminComment());
			valueList.add(cdfSubformImportLog.getSourceNm());
			valueList.add(cdfSubformImportLog.getImportVersionNbr());
			valueList.add(cdfSubformImportLog.getImportTime());
			valueList.add(cdfSubformImportLog.getProcessCd());
			valueList.add(cdfSubformImportLog.getLogMessageTxt());
			valueList.add(cdfSubformImportLog.getVersionCtrlNbr());

			int resultCount =
				((Integer) preparedStmtMethod(cdfSubformImportLog,
					valueList,
					insertStmt,
					NEDSSConstants.UPDATE))
					.intValue();
			if (resultCount != 1) {
				logger.error(
					"Error: none or more than one CdfSubformImportLog updated at a time, "
						+ "resultCount = "
						+ resultCount);
				throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
			}

			return uid.longValue();
		} catch (Exception e) {
			// check whether it is because of violation of unique version number
			CdfSubformImportLogDT dt = selectByVersion(importVersionNbr);
			if (dt != null) {
				logger.fatal(
					"File with version number of "
						+ importVersionNbr
						+ " was already imported."+e.getMessage(), e);
				throw new NEDSSDBUniqueKeyViolation(
					"File with version number of "
						+ importVersionNbr
						+ " was already imported.");
			} else {
				logger.fatal(
					"Exception in CdfSubformImportLog.insert(): Error = " + e.getMessage(), e);
				throw new NEDSSSystemException(e.toString());
			}
		}
	}

}