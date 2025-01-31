package gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dt.NBSDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dt.NBSMasterLogDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;

/**
 * PamConvserionLoggerDAO Description: Copyright: Copyright (c) 2009
 * 
 * @author Pradeep Sharma
 */
public class NBSProcessLogDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(NBSProcessLogDAO.class
			.getName());
	private String INSERT_INTO_LOG = "INSERT INTO NBS_detail_log(nbs_detail_cd,NBS_detail_txt,NBS_Master_log_uid)VALUES(?,?,?)";
	private String INSERT_INTO_PROCESS_MASTER = "INSERT INTO NBS_Master_Log(Act_uid,Entity_uid,Event_type,Status_cd,start_time,end_time,process_message_txt,local_id)VALUES(?,?,?,?,?,?,?,?)";

	public void insertLog(Collection<Object> coll) {
		try{
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					NBSDetailLogDT nBSDetailLogDT = (NBSDetailLogDT) it.next();
					insertNBSDetailLogDT(nBSDetailLogDT);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void insertNBSDetailLogDT(NBSDetailLogDT nBSDetailLogDT)
			throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_INTO_LOG=" + INSERT_INTO_LOG);
		
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_INTO_LOG);
			int i = 1;
			preparedStmt.setString(i++, nBSDetailLogDT.getNbsDetailCd()); // 1
			String trimmedString = trimToTwoKLength(nBSDetailLogDT
					.getNBSDetailTxt());
			preparedStmt.setString(i++, trimmedString); // 2
			preparedStmt.setLong(i++, nBSDetailLogDT.getNBSMasterLogUid()
					.longValue());// 3
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertNBSConversionErrorDT is "
					+ resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "NBSConversionErrorDT into NBS_conversion_error:"
					+ nBSDetailLogDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString(), sqlex);
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into NBS_conversion_error, NBSConversionErrorDT="
							+ nBSDetailLogDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}// end of insert method

	public void insertNBSMasterLogDT(NBSMasterLogDT nBSMasterLogDT) {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger
				.debug("INSERT_INTO_PROCESS_MASTER="
						+ INSERT_INTO_PROCESS_MASTER);
		
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(INSERT_INTO_PROCESS_MASTER);
			int i = 1;
			if (nBSMasterLogDT.getActUid() != null)
				preparedStmt.setLong(i++, nBSMasterLogDT.getActUid()
						.longValue());// 1
			else
				preparedStmt.setNull(i++, Types.BIGINT);// 1
			if (nBSMasterLogDT.getEntityUid() != null)
				preparedStmt.setLong(i++, nBSMasterLogDT.getEntityUid()
						.longValue());// 1
			else
				preparedStmt.setNull(i++, Types.BIGINT);// 2
			preparedStmt.setString(i++, nBSMasterLogDT.getEventType());// 3
			preparedStmt.setString(i++, nBSMasterLogDT.getStatusCd());// 4
			preparedStmt.setTimestamp(i++, nBSMasterLogDT.getStartTime());// 5
			preparedStmt.setTimestamp(i++, nBSMasterLogDT.getEndTime());// 6
			String trimmedString = null;
			if (nBSMasterLogDT.getProcessMessageTxt() != null)
				trimmedString = trimToMaxLength(nBSMasterLogDT
						.getProcessMessageTxt(), 250);
			preparedStmt.setString(i++, trimmedString);// 7
			preparedStmt.setString(i++, nBSMasterLogDT.getLocalId());// 8

			resultCount = preparedStmt.executeUpdate();

			PreparedStatement preparedStmt2 = dbConnection
					.prepareStatement("SELECT MAX(NBS_Master_Log_uid) from NBS_Master_Log");
			Long nbsMasterLogUid = null;
			ResultSet rs = preparedStmt2.executeQuery();
			if (rs.next()) {
				logger.debug("NBS_Master_Log_uid = " + rs.getLong(1));
				nbsMasterLogUid = new Long(rs.getLong(1));
			}
			logger.debug("resultCount in insertNBSConversionMasterDT is "
					+ resultCount);

			if (nBSMasterLogDT.getNBSConversionDetailDTCollection() != null
					&& nBSMasterLogDT.getNBSConversionDetailDTCollection()
							.size() > 0) {
				Iterator<Object> it = nBSMasterLogDT
						.getNBSConversionDetailDTCollection().iterator();
				while (it.hasNext()) {
					NBSDetailLogDT nBSDetailLogDT = (NBSDetailLogDT) it.next();
					nBSDetailLogDT.setNBSMasterLogUid(nbsMasterLogUid);
					insertNBSDetailLogDT(nBSDetailLogDT);

				}
			}
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "NBSConversionErrorDT into NBS_conversion_master:"
					+ nBSMasterLogDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString(), sqlex);
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into NBS_conversion_master, NBSConversionErrorDT="
							+ nBSMasterLogDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}

	public static String trimToTwoKLength(String s) {
		if (s != null && s.length() > 4000) {
			return s.substring(0, 3900);
		} else
			return s;
	}

	public static String trimToMaxLength(String s, int maxlength) {
		if (s != null && s.length() > maxlength) {
			return s.substring(0, (maxlength - 1));
		} else
			return s;
	}
}
