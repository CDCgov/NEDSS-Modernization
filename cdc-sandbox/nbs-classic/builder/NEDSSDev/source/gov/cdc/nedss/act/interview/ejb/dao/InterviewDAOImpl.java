/**
 * Title:       InterviewDAOImpl.java
 * Description: This is the implementation of NEDSSDAOInterface for the
 *               Interview value object in the Interview entity bean.
 *               This class encapsulates all the JDBC calls made by the InterviewEJB
 *               for a Interview object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of InterviewEJB is
 *               implemented here.
 * Copyright:   Copyright (c) 2013
 * Company:     Leidos
 * @version     1.0
 */

package gov.cdc.nedss.act.interview.ejb.dao;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

public class InterviewDAOImpl extends BMPBase {

	// private static final String ACT_UID = "ACTIVITY_UID";
	private long interviewUID = -1;
	// For logging
	static final LogUtils logger = new LogUtils(
			InterviewDAOImpl.class.getName());
	public static final String INSERT_ACTIVITY = "INSERT INTO "
			+ DataTables.ACTIVITY_TABLE
			+ "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";
	public static final String SELECT_INTERVIEW = "SELECT interview_uid \"interviewUid\","
			+ "interview_status_cd \"interviewStatusCd\","
			+ "interviewee_role_cd \"intervieweeRoleCd\","
			+ "interview_date \"interviewDate\","
			+ "interview_type_cd \"interviewTypeCd\", "
			+ "interview_loc_cd \"interviewLocCd\", "
			+ "add_time \"addTime\","
			+ "add_user_id \"addUserId\","
			+ "last_chg_time \"lastChgTime\","
			+ "last_chg_user_id \"lastChgUserId\", "
			+ "record_status_cd \"recordStatusCd\","
			+ "record_status_time \"recordStatusTime\", "
			+ "local_id \"localId\", "
			+ "version_ctrl_nbr \"versionCtrlNbr\" "
			+ "FROM " + DataTables.INTERVIEW_TABLE + " WHERE interview_uid = ?";

	public static final String SELECT_INVESTIGATIONS_ASSOCIATED_INTERVIEW = "SELECT count(*) "
			+ "FROM " + DataTables.PUBLIC_HEALTH_CASE_TABLE + " WHERE public_health_case_uid in "
			+" (select target_act_uid from act_relationship where source_act_uid = ?)";
	
	public static final String SELECT_INTERVIEW_UID = "SELECT interview_uid FROM "
			+ DataTables.INTERVIEW_TABLE + " WHERE interview_uid = ?";

	public static final String DELETE_INTERVIEW = "DELETE FROM "
			+ DataTables.INTERVIEW_TABLE + " WHERE interview_uid = ?";

	public static final String UPDATE_INTERVIEW = "UPDATE "
			+ DataTables.INTERVIEW_TABLE + " set interview_status_cd=?, "
			+ "interviewee_role_cd = ?, " + "interview_date = ?, "
			+ "interview_type_cd = ?, " + "interview_loc_cd = ?, "
			+ "add_time = ?, " + "add_user_id = ?, " + "last_chg_time = ?, "
			+ "last_chg_user_id = ?, " + "record_status_cd = ?, "
			+ "record_status_time = ?, " + "local_id = ?, "
			+ "version_ctrl_nbr = ? " + "WHERE  interview_uid = ? "
			+ "AND version_ctrl_nbr = ?";

	public static final String INSERT_INTERVIEW = "INSERT INTO "
			+ DataTables.INTERVIEW_TABLE + "(interview_uid, "
			+ "interview_status_cd, " + "interviewee_role_cd, "
			+ "interview_date, " + "interview_type_cd, " + "interview_loc_cd, "
			+ "add_time, " + "add_user_id, " + "last_chg_time, "
			+ "last_chg_user_id, " + "record_status_cd, "
			+ "record_status_time, " + "local_id, " + "version_ctrl_nbr)"
			+ " Values (?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERT_INTERVIEW_HIST = "INSERT into interview_hist(interview_hist_uid, interview_uid, "
			+ "interview_status_cd, "
			+ "interview_date, "
			+ "interviewee_role_cd, "
			+ "interview_type_cd, "
			+ "interview_loc_cd, "
			+ "local_id, "
			+ "record_status_cd, "
			+ "record_status_time, "
			+ "add_time, "
			+ "add_user_id, "
			+ "last_chg_time, "
			+ "last_chg_user_id, "
			+ "version_ctrl_nbr) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String INTERVIEW_ASSOCIATED_W_CONTACT_RECORD = "Select count(*) from CT_contact where record_status_cd='ACTIVE' and named_during_interview_uid = ?";

	
	public InterviewDAOImpl() {
	}

	public long create(Object obj) throws NEDSSSystemException {
		try{
			interviewUID = insertInterview((InterviewDT) obj);
			logger.debug("(From inserting interview_table)interview UID = "
					+ interviewUID);
			((InterviewDT) obj).setItNew(false);
			((InterviewDT) obj).setItDirty(false);
			return interviewUID;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void storeInterview(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException {
		try{
			updateInterview((InterviewDT) obj);
		}catch(NEDSSConcurrentDataException ex){
			logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void remove(long interviewUID) throws NEDSSSystemException {
		try{
			removeInterview(interviewUID);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Object loadObject(long InterviewUID) throws NEDSSSystemException {
		try{
			InterviewDT interviewDT = selectInterview(InterviewUID);
			interviewDT.setItNew(false);
			interviewDT.setItDirty(false);
			return interviewDT;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long findByPrimaryKey(long interviewUID)
			throws NEDSSSystemException {
		try{
			if (interviewExists(interviewUID))
				return (new Long(interviewUID));
			else
				logger.error("No interview found for this primary key :"
						+ interviewUID);
			return null;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	protected boolean interviewExists(long interviewUID)
			throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_INTERVIEW_UID);
			logger.debug("interviewID = " + interviewUID);
			preparedStmt.setLong(1, interviewUID);
			resultSet = preparedStmt.executeQuery();
			if (!resultSet.next()) {
				returnValue = false;
			} else {
				interviewUID = resultSet.getLong(1);
				returnValue = true;
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while checking for an"
					+ " existing interview's uid in interview table-> "
					+ interviewUID, sex);
			throw new NEDSSSystemException(sex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while checking for an"
					+ " existing interview's uid in interview table-> "
					+ interviewUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}

	private long insertInterview(InterviewDT interviewDT)
			throws NEDSSSystemException {
		/**
		 * Starts inserting a new interview
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		@SuppressWarnings("unused")
		ResultSet resultSet = null;
		// long interviewUID = -1;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;

		/**
		 * Inserts into act table for interview
		 */
		try {
			uidGen = new UidGeneratorHelper();
			interviewUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE)
					.longValue();
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);

			int i = 1;
			preparedStmt.setLong(i++, interviewUID);
			preparedStmt.setString(i++, NEDSSConstants.INTERVIEW_CLASS_CODE);
			preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
			resultCount = preparedStmt.executeUpdate();

		} catch (Exception e) {
			logger.fatal("Exception  = "+e.getMessage(), e);
			throw new NEDSSDAOSysException(e.getMessage());
		} finally {
			// close statement before reuse
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);

		}
		if (resultCount != 1) {
			logger.error("Error while inserting "
					+ "uid into ACT_TABLE for a new interview, resultCount = "
					+ resultCount);
		}

		/**
		 * inserts into INTERVIEW_TABLE
		 */
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_INTERVIEW);
			uidGen = new UidGeneratorHelper();
			localUID = uidGen
					.getLocalID(UidClassCodes.INTTERVENTION_CLASS_CODE);

			int i = 1;

			preparedStmt.setLong(i++, interviewUID);// 1
			preparedStmt.setString(i++, interviewDT.getInterviewStatusCd());// 2
			preparedStmt.setString(i++, interviewDT.getIntervieweeRoleCd());// 3
			if (interviewDT.getInterviewDate() == null)// 4
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, interviewDT.getInterviewDate());
			preparedStmt.setString(i++, interviewDT.getInterviewTypeCd());// 5
			preparedStmt.setString(i++, interviewDT.getInterviewLocCd());// 6

			if (interviewDT.getAddTime() == null)// 7
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, interviewDT.getAddTime());
			if (interviewDT.getAddUserId() == null)// 8
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++,
						(interviewDT.getAddUserId()).longValue());
			if (interviewDT.getLastChgTime() == null)// 9
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, interviewDT.getLastChgTime());
			if (interviewDT.getLastChgUserId() == null)// 10
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++,
						(interviewDT.getLastChgUserId()).longValue());
			preparedStmt.setString(i++, interviewDT.getRecordStatusCd());// 11
			if (interviewDT.getRecordStatusTime() == null)// 12
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,
						interviewDT.getRecordStatusTime());
			preparedStmt.setString(i++, localUID);// 13
			// new ones for security, concurrence
			logger.debug("The version control number :"
					+ interviewDT.getVersionCtrlNbr());
			preparedStmt
					.setInt(i++, interviewDT.getVersionCtrlNbr().intValue());// 14
			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert interview! interviewUID = "
					+ interviewUID);
			return interviewUID;
		} catch (SQLException sex) {
			logger.fatal("SQLException while inserting "
					+ "interview into INTERVIEW_TABLE: \n", sex);
			throw new NEDSSSystemException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into INTERVIEW_TABLE, id = "
					+ interviewUID, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of inserting interview

	public void updateInterview(InterviewDT interviewDT)
			throws NEDSSSystemException,
			NEDSSConcurrentDataException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;

		try {
			// Updates interview table
			if (interviewDT != null) {
				dbConnection = getConnection();
				logger.debug("Updating interviewDT: UID = "
						+ interviewDT.getInterviewUid().longValue());
				preparedStmt = dbConnection.prepareStatement(UPDATE_INTERVIEW);

				int i = 1;
				preparedStmt.setString(i++, interviewDT.getInterviewStatusCd()); // 1
				preparedStmt.setString(i++, interviewDT.getIntervieweeRoleCd()); // 2
				if (interviewDT.getInterviewDate() == null)// 3
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++,
							interviewDT.getInterviewDate());
				preparedStmt.setString(i++, interviewDT.getInterviewTypeCd());// 4
				preparedStmt.setString(i++, interviewDT.getInterviewLocCd());// 5

				if (interviewDT.getAddTime() == null)// 6
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, interviewDT.getAddTime());
				if (interviewDT.getAddUserId() == null)// 7
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++,
							(interviewDT.getAddUserId()).longValue());
				if (interviewDT.getLastChgTime() == null)// 8
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt
							.setTimestamp(i++, interviewDT.getLastChgTime());
				if (interviewDT.getLastChgUserId() == null)// 9
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++,
							(interviewDT.getLastChgUserId()).longValue());
				preparedStmt.setString(i++, interviewDT.getRecordStatusCd());// 10
				if (interviewDT.getRecordStatusTime() == null)// 11
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++,
							interviewDT.getRecordStatusTime());
				preparedStmt.setString(i++, interviewDT.getLocalId());// 12
				if (interviewDT.getVersionCtrlNbr() == null)// 13
				{
					logger.error("** VersionCtrlNbr cannot be null *** :"
							+ interviewDT.getVersionCtrlNbr());
				} else {
					logger.debug("VersionCtrlNbr exists"
							+ interviewDT.getVersionCtrlNbr());
					preparedStmt.setInt(i++,
							(interviewDT.getVersionCtrlNbr().intValue()));
					logger.debug("new versioncontrol number:"
							+ (interviewDT.getVersionCtrlNbr().intValue()));
				}
				if (interviewDT.getInterviewUid() == null)// 14
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++,
							(interviewDT.getInterviewUid()).longValue());
				if (interviewDT.getVersionCtrlNbr() == null)// 15
				{
					logger.error("** VersionCtrlNbr cannot be null here :"
							+ interviewDT.getVersionCtrlNbr());
				} else {
					logger.debug("VersionCtrlNbr exists here:"
							+ interviewDT.getVersionCtrlNbr());
					preparedStmt.setInt(i++, interviewDT.getVersionCtrlNbr()
							.intValue() - 1);
				}
				logger.debug("sql query is :" + preparedStmt.toString());

				resultCount = preparedStmt.executeUpdate();
				logger.debug("Done updating interview, UID = "
						+ (interviewDT.getInterviewUid()).longValue());
				if (resultCount <= 0) {
					logger.error("Error: none or more than one interview updated at a time, "
							+ "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException(
							"NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while updating "
					+ "interview into INTERVIEW_TABLE: \n", sex);
			throw new NEDSSDAOSysException(sex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);

		}
	}// end of updating interview table

	public InterviewDT selectInterview(long interviewUID)
			throws NEDSSSystemException {
		InterviewDT interviewDT = new InterviewDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object> pList = new ArrayList<Object>();

		/**
		 * Selects interview from Interview table
		 */

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_INTERVIEW);
			preparedStmt.setLong(1, interviewUID);
			resultSet = preparedStmt.executeQuery();
			logger.debug("InterviewDT object for: interviewUID = "
					+ interviewUID);
			/*
			 * if(!resultSet.next()) throw new
			 * NEDSSSystemException("No record for this primary key, PK = " +
			 * interviewUID);
			 */
			resultSetMetaData = resultSet.getMetaData();

			pList = (ArrayList<Object>) resultSetUtils
					.mapRsToBeanList(resultSet, resultSetMetaData,
							interviewDT.getClass(), pList);
			// logger.debug("Size of interview list in interviewDaoImpl is: " +
			// pList.size());
			for (Iterator<Object> anIterator = pList.iterator(); anIterator
					.hasNext();) {
				interviewDT = (InterviewDT) anIterator.next();
				interviewDT.setItNew(false);
				interviewDT.setItDirty(false);
			}
			
			// Check to see if interview is associated to any contact records.		
			if(this.selectNumberOfContactsAssociatedToInterview(interviewUID)>0)
				interviewDT.setAssociated(true);
			else
				interviewDT.setAssociated(false);
						

		} catch (SQLException sex) {
			logger.fatal("SQLException while selecting "
					+ "interview vo; id = " + interviewUID, sex);
			throw new NEDSSSystemException(sex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while selecting " + "interview vo; id = "
					+ interviewUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return interview object");
		return interviewDT;
	}// end of selecting interview ethnic groups

	public int selectNumberOfInvestigationsAssociatedToInterview(long interviewUID)
			throws NEDSSSystemException {
		InterviewDT interviewDT = new InterviewDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		ArrayList<Object> pList = new ArrayList<Object>();
		String result;
		int resultInt = 0;
		/**
		 * Selects interview from Interview table
		 */

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_INVESTIGATIONS_ASSOCIATED_INTERVIEW);
			preparedStmt.setLong(1, interviewUID);
			resultSet = preparedStmt.executeQuery();
			
			if (resultSet != null && resultSet.next()) {
				result = resultSet.getString(1);
				resultInt = Integer.parseInt(result);
			}
			
			
			logger.debug("InterviewDT object for: interviewUID = "
					+ interviewUID);
			/*
			 * if(!resultSet.next()) throw new
			 * NEDSSSystemException("No record for this primary key, PK = " +
			 * interviewUID);
			 */
			
			// logger.debug("Size of interview list in interviewDaoImpl is: " +
			// pList.size());


		} catch (SQLException sex) {
			logger.fatal("SQLException while selecting "
					+ "interview vo; id = " + interviewUID, sex);
			throw new NEDSSSystemException(sex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while selecting " + "interview vo; id = "
					+ interviewUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return interview object");
		return resultInt;
	}// end of selecting interview ethnic groups
	
	

	public int selectNumberOfContactsAssociatedToInterview(long interviewUID)
			throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		ArrayList<Object> pList = new ArrayList<Object>();
		String result;
		int resultInt = 0;
		/**
		 * Selects interview from Interview table
		 */

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INTERVIEW_ASSOCIATED_W_CONTACT_RECORD);
			preparedStmt.setLong(1, interviewUID);
			resultSet = preparedStmt.executeQuery();
			
			if (resultSet != null && resultSet.next()) {
				result = resultSet.getString(1);
				resultInt = Integer.parseInt(result);
			}
			
			
			logger.debug("InterviewDT object for: interviewUID = "
					+ interviewUID);

		} catch (SQLException sex) {
			logger.fatal("SQLException while selecting "
					+ "interview associated to Contact Record with interview Uid = " + interviewUID, sex);
			throw new NEDSSSystemException(sex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while selecting interview associated to Contact Record with interview Uid =  "
					+ interviewUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return associated count");
		return resultInt;
	}// end of selecting interview ethnic groups

	
	private void removeInterview(long interviewUID) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;

		/**
		 * Deletes interview ethnic groups
		 */
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_INTERVIEW);
			preparedStmt.setLong(1, interviewUID);
			resultCount = preparedStmt.executeUpdate();

			if (resultCount != 1) {
				logger.error("Error: cannot delete interview from INTERVIEW_TABLE!! resultCount = "
						+ resultCount);
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while removing " + "interview; id = "
					+ interviewUID, sex);
			throw new NEDSSDAOSysException(sex.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of removing interview

	public void insertInterviewHist(InterviewDT dt) throws NEDSSSystemException {
		if (dt.getInterviewUid() != null) {
			int resultCount = 0;

			int i = 1;
			Connection dbConnection = null;
			PreparedStatement pStmt = null;
			long interviewHistUID = -1;
			try {
				UidGeneratorHelper uidGen = new UidGeneratorHelper();
				interviewHistUID = uidGen.getNbsIDLong(
						UidClassCodes.NBS_CLASS_CODE).longValue();
				dbConnection = getConnection();
				pStmt = dbConnection.prepareStatement(INSERT_INTERVIEW_HIST);
				pStmt.setLong(i++, interviewHistUID);// 1
				pStmt.setLong(i++, dt.getInterviewUid().longValue());// 2
				pStmt.setString(i++, dt.getInterviewStatusCd());// 3

				if (dt.getInterviewDate() == null)// 4
					pStmt.setNull(i++, Types.TIMESTAMP);
				else
					pStmt.setTimestamp(i++, dt.getInterviewDate());
				pStmt.setString(i++, dt.getIntervieweeRoleCd());// 5
				pStmt.setString(i++, dt.getInterviewTypeCd());// 6
				pStmt.setString(i++, dt.getInterviewLocCd());// 7
				pStmt.setString(i++, dt.getLocalId());// 8
				pStmt.setString(i++, dt.getRecordStatusCd());// 9
				if (dt.getRecordStatusTime() == null)// 10
					pStmt.setNull(i++, Types.TIMESTAMP);
				else
					pStmt.setTimestamp(i++, dt.getRecordStatusTime());
				if (dt.getAddTime() == null)// 11
					pStmt.setNull(i++, Types.TIMESTAMP);
				else
					pStmt.setTimestamp(i++, dt.getAddTime());
				if (dt.getAddUserId() == null)// 12
					pStmt.setNull(i++, Types.BIGINT);
				else
					pStmt.setLong(i++, dt.getAddUserId().longValue());

				if (dt.getLastChgTime() == null)// 13
					pStmt.setNull(i++, Types.TIMESTAMP);
				else
					pStmt.setTimestamp(i++, dt.getLastChgTime());

				if (dt.getLastChgUserId() == null)// 14
					pStmt.setNull(i++, Types.BIGINT);
				else
					pStmt.setLong(i++, dt.getLastChgUserId().longValue());
				if (dt.getVersionCtrlNbr() == null)
					pStmt.setNull(i++, Types.SMALLINT);
				else
					pStmt.setInt(i++, dt.getVersionCtrlNbr().intValue());
				logger.debug("sql query is :" + pStmt.toString());
				resultCount = pStmt.executeUpdate();
				if (resultCount != 1) {
					throw new NEDSSSystemException(
							"Error: none or more than one entity inserted at a time, resultCount = "
									+ resultCount);
				}

			} catch (SQLException sex) {
				logger.fatal("SQLException while inserting "
						+ "interview into INTERVIEW_HIST_TABLE: \n", sex);
				throw new NEDSSSystemException(sex.toString());
			} catch (Exception ex) {
				logger.fatal(
						"Error while inserting into INTERVIEW_HIST_TABLE, id = "
								+ interviewHistUID, ex);
				throw new NEDSSSystemException(ex.toString());
			} finally {
				closeStatement(pStmt);
				releaseConnection(dbConnection);
			}// end of finally
		}// end of if
	}// end of insertInterviewHist()

}// end of InterviewDAOImpl class

