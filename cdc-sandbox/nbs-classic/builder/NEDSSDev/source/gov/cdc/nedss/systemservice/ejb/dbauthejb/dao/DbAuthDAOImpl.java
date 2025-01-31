package gov.cdc.nedss.systemservice.ejb.dbauthejb.dao;


/**
 * 
 * @Name:  DbAuthDAOImpl.java
 * @Description: Inserts, updates, and deletes for new database based NBS Security
 * @Copyright:	Copyright (c) 2011
 * @Company: 	CSC
 * @author	Gregory Tucker
 * @updated: Pradeep K Sharma 
 * @Company SAIC 
 * @UpdateYear:2012
 * @version	4.4.1
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusObjTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpRtDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthBusOpTypeDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthProgAreaAdminDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserRoleDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish.EncryptorDecryptor;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

public class DbAuthDAOImpl extends DAOBase {

	static final LogUtils logger = new LogUtils(DbAuthDAOImpl.class.getName());
	/**
	 * Insert the authUserDT record specified
	 * @param secureUserDtDT with fields
	 * @return authUserDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthUserDT insertSecureUserDT(AuthUserDT authUserDT)
			throws NEDSSSystemException{

		logger.debug("Inserting new user record into the secure_user table.");
		/**
		 * Inserting a new user
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureUserUid = null;

		String INSERT_SECURE_USER = "INSERT INTO " +DataTables.SECURE_USER+
				"(user_id,user_type, user_title, user_department,user_first_nm, user_last_nm,user_work_email, user_work_phone, user_mobile_phone, master_sec_admin_ind, prog_area_admin_ind,"
				+"nedss_entry_id, external_org_uid, provider_uid, user_password, user_comments, add_time,add_user_id, last_chg_time, last_chg_user_id, record_status_cd,"
				+" record_status_time, jurisdiction_derivation_ind) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_SECURE_USER);
			preparedStmt.setString(i++,EncryptorDecryptor.encrypt(authUserDT.getUserId()));
			//preparedStmt.setString(i++,authUserDT.getUserId());		 //1
			preparedStmt.setString(i++,authUserDT.getUserType());		 //2
			preparedStmt.setString(i++,authUserDT.getUserTitle());	 //3
			preparedStmt.setString(i++,authUserDT.getUserDepartment());//4
			preparedStmt.setString(i++,authUserDT.getUserFirstNm()); //5
			preparedStmt.setString(i++,authUserDT.getUserLastNm());  //6
			preparedStmt.setString(i++,authUserDT.getUserWorkEmail()); //7
			preparedStmt.setString(i++,authUserDT.getUserWorkPhone()); //8
			preparedStmt.setString(i++,authUserDT.getUserMobilePhone()); //9
			preparedStmt.setString(i++,authUserDT.getMasterSecAdminInd());//10
			preparedStmt.setString(i++,authUserDT.getProgAreaAdminInd());//11
			if (authUserDT.getNedssEntryId() == null)
				throw new NEDSSDAOAppException("DbAuthDAOImpl.insertSecureUserDT Exception thrown as authUserDT.getNedssEntryId() is null!!");
			else
				preparedStmt.setLong(i++, authUserDT.getNedssEntryId().longValue()); // 12

			if (authUserDT.getExternalOrgUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserDT.getExternalOrgUid().longValue()); // 13
			if (authUserDT.getProviderUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserDT.getProviderUid().longValue()); // 14
			preparedStmt.setString(i++,authUserDT.getUserPassword());//15
			preparedStmt.setString(i++,authUserDT.getUserComments());     //16
			if (authUserDT.getAddTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, authUserDT.getAddTime()); // 17
			if (authUserDT.getAddUserId()==null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserDT.getAddUserId().longValue());//18
			if(authUserDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, authUserDT.getLastChgTime()); 	// 19
			if (authUserDT.getLastChgUserId()==null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserDT.getLastChgUserId().longValue());//20
			if(authUserDT.getRecordStatusCd() == null)
				preparedStmt.setString(i++,NEDSSConstants.RECORD_STATUS_ACTIVE);
			else
				preparedStmt.setString(i++, authUserDT.getRecordStatusCd());//21

			if (authUserDT.getRecordStatusTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, authUserDT.getRecordStatusTime()); // 22

			preparedStmt.setString(i++, authUserDT.getJurisdictionDerivationInd());//23
			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting new user < "+authUserDT.getNedssEntryId() + ">");
			logger.debug("resultCount inserting new user is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(auth_user_uid) from "+DataTables.SECURE_USER) ;
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New Secure User Uid = " + rs2.getLong(1));
				secureUserUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertSecureUserDT.SQLException while inserting a new entry in auth_user: \n"+ sqlex.getMessage() , sqlex);
			logger.fatal("insertSecureUserDT.authUserDT resultCount is "+resultCount);
			logger.fatal("insertSecureUserDT.ERROR running INSERT_SECURE_USER query:\n"+INSERT_SECURE_USER);
			logger.fatal("insertSecureUserDT.ERROR inserting authUserDT with values:"+ authUserDT.toString());
			throw new NEDSSSystemException( "insertSecureUserDT.SQLException while inserting a new entry in auth_user: \n"+sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("insertSecureUserDT.Error while inserting into Secure_user TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("insertSecureUserDT.authUserDT resultCount is "+resultCount);
			logger.fatal("insertSecureUserDT.ERROR running INSERT_SECURE_USER query:\n"+INSERT_SECURE_USER);
			logger.fatal("insertSecureUserDT.ERROR inserting authUserDT with values:"+ authUserDT.toString());
			throw new NEDSSSystemException(ex.toString());
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		authUserDT.setAuthUserUid(secureUserUid);
		return authUserDT;
	}

	/**
	 * Insert the authPermSetDT record specified
	 * @param authPermSetDT with fields
	 * @return authPermSetDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthPermSetDT insertSecurePermissionSetDT(AuthPermSetDT authPermSetDT)
			throws NEDSSSystemException{

		logger.debug("Inserting new permission set into the secure_permission_set table.");
		/**
		 * Inserting a new permission set
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  securePermissionSetUid = null;

		String INSERT_SECURE_PERMISSION_SET = "INSERT INTO " +DataTables.SECURE_PERMISSION_SET+
				"(PERM_SET_NM, PERM_SET_DESC, SYS_DEFINED_PERM_SET_IND, ADD_TIME, ADD_USER_ID, LAST_CHG_USER_ID, LAST_CHG_TIME, RECORD_STATUS_CD, RECORD_STATUS_TIME)"
				+"VALUES(?,?,?,?,?,?,?,?,?)";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_SECURE_PERMISSION_SET);
			preparedStmt.setString(i++,authPermSetDT.getPermSetNm());		   //1
			preparedStmt.setString(i++,authPermSetDT.getPermSetDesc()); //2
			preparedStmt.setString(i++,authPermSetDT.getSysDefinedPermSetInd());  //3
			preparedStmt.setTimestamp(i++, authPermSetDT.getAddTime());                //4
			preparedStmt.setLong(i++, authPermSetDT.getAddUserId().longValue());		   //5
			preparedStmt.setLong(i++, authPermSetDT.getLastChgUserId().longValue());   //6
			preparedStmt.setTimestamp(i++, authPermSetDT.getLastChgTime()); 	       //7
			preparedStmt.setString(i++, authPermSetDT.getRecordStatusCd());//8
			preparedStmt.setTimestamp(i++, authPermSetDT.getRecordStatusTime()); 	   // 9


			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting new permission set < "+authPermSetDT.toString() + ">");
			logger.debug("resultCount inserting new perm set is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(auth_perm_set_uid) from "+DataTables.SECURE_PERMISSION_SET);
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New Permission Set UID = " + rs2.getLong(1));
				securePermissionSetUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertSecurePermissionSetDT.SQLException while inserting " +
					"a new entry in secure_permission_set: \n"+ sqlex.getMessage(), sqlex);
			logger.fatal("insertSecurePermissionSetDT.securePermissionSet DT is "+authPermSetDT.toString());
			logger.fatal("insertSecurePermissionSetDT.securePermissionSet resultCount is "+resultCount);
			logger.fatal("insertSecurePermissionSetDT.ERROR running INSERT_SECURE_PERMISSION_SET query:\n"+INSERT_SECURE_PERMISSION_SET);
			logger.fatal("insertSecurePermissionSetDT.ERROR inserting authPermSetDT with values:"+ authPermSetDT.toString());

			throw new NEDSSSystemException( "insertSecurePermissionSetDT.SQLException while inserting a new entry : \n"+sqlex );

		}
		catch(Exception ex)
		{
			logger.fatal("insertSecurePermissionSetDT.ERROR while inserting into Secure_permission_set TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("insertSecurePermissionSetDT.securePermissionSet resultCount is "+resultCount);
			logger.fatal("insertSecurePermissionSetDT.ERROR running INSERT_SECURE_PERMISSION_SET query:\n"+INSERT_SECURE_PERMISSION_SET);
			logger.fatal("insertSecurePermissionSetDT.ERROR inserting authPermSetDT with values:"+ authPermSetDT.toString());
			throw new NEDSSSystemException(ex.toString());
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		authPermSetDT.setAuthPermSetUid(securePermissionSetUid);
		return(authPermSetDT);
	} //PermissionSet

	/**
	 * Insert the AuthBusObjRightDT record specified
	 * @param authBusObjRtDT with fields in the table
	 * @return AuthBusObjRightDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthBusObjRtDT insertAuthBusObjRightDT(AuthBusObjRtDT authBusObjRtDT)throws NEDSSSystemException{

		logger.debug("Inserting new permission set into the secure_business_object_right table.");
		/**
		 * Inserting a new access right to a business object
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureBusinessObjectRightUid = null;

		String INSERT_SECURE_BUSINESS_OBJECT_RIGHT = "INSERT INTO " +DataTables.SECURE_BUSINESS_OBJECT_RIGHT+
				"(AUTH_PERM_SET_UID, AUTH_BUS_OBJ_TYPE_UID, ADD_TIME, ADD_USER_ID, LAST_CHG_TIME, LAST_CHG_USER_ID, RECORD_STATUS_CD, RECORD_STATUS_TIME)"+
				"VALUES(?,?,?,?,?,?,?,?)";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_SECURE_BUSINESS_OBJECT_RIGHT);
			preparedStmt.setLong(i++,authBusObjRtDT.getAuthPermSetUid());		 //1
			preparedStmt.setLong(i++,authBusObjRtDT.getAuthBusObjTypeUid());	 //2
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getAddTime()); // 3
			preparedStmt.setLong(i++, authBusObjRtDT.getAddUserId().longValue());//4
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getLastChgTime()); 	// 5
			preparedStmt.setLong(i++, authBusObjRtDT.getLastChgUserId().longValue());//6
			preparedStmt.setString(i++, authBusObjRtDT.getRecordStatusCd());//7
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getRecordStatusTime()); // 8


			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting business object access right");
			logger.debug("resultCount inserting new bus obj right count is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(AUTH_BUS_OBJ_RT_UID) from "+DataTables.SECURE_BUSINESS_OBJECT_RIGHT);
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New Business Object Right UID = " + rs2.getLong(1));
				secureBusinessObjectRightUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertAuthBusObjRightDT.SQLException while adding " +
					"a new business object to a permission set: \n"+ sqlex.getMessage(), sqlex);
			logger.fatal("insertAuthBusObjRightDT.secureBusinessObjectRight resultCount is "+resultCount);
			logger.fatal("insertAuthBusObjRightDT.ERROR running INSERT_SECURE_PERMISSION_SET query:\n"+INSERT_SECURE_BUSINESS_OBJECT_RIGHT);
			logger.fatal("insertAuthBusObjRightDT.ERROR inserting authBusObjRtDT with values:"+ authBusObjRtDT.toString());
			throw new NEDSSSystemException( "insertAuthBusObjRightDT.SQLException while inserting a new entry : \n"+sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("insertAuthBusObjRightDT.Error while inserting into Secure_business_object_right TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("insertAuthBusObjRightDT.secureBusinessObjectRight resultCount is "+resultCount);
			logger.fatal("insertAuthBusObjRightDT.ERROR running INSERT_SECURE_PERMISSION_SET query:\n"+INSERT_SECURE_BUSINESS_OBJECT_RIGHT);
			logger.fatal("insertAuthBusObjRightDT.ERROR inserting authBusObjRtDT with values:"+ authBusObjRtDT.toString());
			throw new NEDSSSystemException( "insertAuthBusObjRightDT.Exception while inserting a new entry : \n"+ex );
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		authBusObjRtDT.setAuthBusObjRtUid(secureBusinessObjectRightUid);
		return(authBusObjRtDT);
	}

	/**
	 * Insert the SecureBusinessOperationRightDT record specified
	 * @param authBusOpRtDT with fields in the table
	 * @return SecureBusinessOperationRightDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthBusOpRtDT insertSecureBusinessOperationRightDT(AuthBusOpRtDT authBusOpRtDT)
			throws NEDSSSystemException{

		logger.debug("Inserting new permission set into the secure_business_object_right table.");
		/**
		 * Inserting a new operation on a business object i.e. View for Investigation
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureBusinessOperationRightUid = null;

		String INSERT_SECURE_BUSINESS_OPERATION_RIGHT = "INSERT INTO " +DataTables.SECURE_BUSINESS_OPERATION_RIGHT+
				"(AUTH_BUS_OP_TYPE_UID, AUTH_BUS_OBJ_RT_UID, BUS_OP_USER_RT, BUS_OP_GUEST_RT, ADD_TIME, ADD_USER_ID, LAST_CHG_TIME, LAST_CHG_USER_ID, RECORD_STATUS_CD, RECORD_STATUS_TIME) "+
				"VALUES(?,?,?,?,?,?,?,?,?,?)";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_SECURE_BUSINESS_OPERATION_RIGHT);
			preparedStmt.setLong(i++,authBusOpRtDT.getAuthBusOpTypeUid());	//1
			preparedStmt.setLong(i++,authBusOpRtDT.getAuthBusObjRtUid());	//2
			preparedStmt.setString(i++,authBusOpRtDT.getBusOpUserRt());//3
			preparedStmt.setString(i++,authBusOpRtDT.getBusOpGuestRt());//4
			if (authBusOpRtDT.getAddTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, authBusOpRtDT.getAddTime());//5
			if (authBusOpRtDT.getAddUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authBusOpRtDT.getAddUserId().longValue());//6
			if(authBusOpRtDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, authBusOpRtDT.getLastChgTime());//7
			if (authBusOpRtDT.getLastChgUserId()==null) 
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authBusOpRtDT.getLastChgUserId().longValue());//8
			if(authBusOpRtDT.getRecordStatusCd() == null)
				preparedStmt.setString(i++,"A");
			else
				preparedStmt.setString(i++, authBusOpRtDT.getRecordStatusCd());//9

			if (authBusOpRtDT.getRecordStatusTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, authBusOpRtDT.getRecordStatusTime());//10

			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting business operation access right");
			logger.debug("resultCount inserting new bus op right count is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(AUTH_BUS_OP_RT_uid) from "+DataTables.SECURE_BUSINESS_OPERATION_RIGHT);
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New Business Operation Right UID = " + rs2.getLong(1));
				secureBusinessOperationRightUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertSecureBusinessOperationRightDT.SQLException while inserting " +
					"a new entry in business operation right: \n"+ sqlex.getMessage(), sqlex);
			logger.fatal("insertSecureBusinessOperationRightDT.Secure Bus Op Result Count is "+resultCount);
			logger.fatal("insertSecureBusinessOperationRightDT.ERROR running INSERT_SECURE_BUSINESS_OPERATION_RIGHT query:\n"+INSERT_SECURE_BUSINESS_OPERATION_RIGHT);
			logger.fatal("insertSecureBusinessOperationRightDT.ERROR inserting authBusOpRtDT with values:"+ authBusOpRtDT.toString());
			throw new NEDSSSystemException( "insertSecureBusinessOperationRightDT.SQLException while inserting a new entry : \n"+sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("insertSecureBusinessOperationRightDT.Error while inserting into the Secure business operation right TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("insertSecureBusinessOperationRightDT.secureBusOp resultCount is "+resultCount);
			logger.fatal("insertSecureBusinessOperationRightDT.ERROR running INSERT_SECURE_BUSINESS_OPERATION_RIGHT query:\n"+INSERT_SECURE_BUSINESS_OPERATION_RIGHT);
			logger.fatal("insertSecureBusinessOperationRightDT.ERROR inserting authBusOpRtDT with values:"+ authBusOpRtDT.toString());
			throw new NEDSSSystemException( "insertSecureBusinessOperationRightDT.Exception while inserting a new entry : \n"+ex );
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		//set the new uid
		authBusOpRtDT.setAuthBusOpRtUid(secureBusinessOperationRightUid);
		return(authBusOpRtDT);
	}

	/**
	 * Insert the SecureUserRoleDT record specified
	 * @param authUserRoleDT with fields in the table
	 * @return SecureUserRoleDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthUserRoleDT insertSecureUserRoleDT(AuthUserRoleDT authUserRoleDT)
			throws NEDSSSystemException{

		logger.debug("Adding a new user role to a permission set in a jurisdiction, program area to a user.");
		/**
		 * Inserting a new realized role
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureUserRoleUid = null;

		String INSERT_SECURE_USER_ROLE = "INSERT INTO " +DataTables.SECURE_USER_ROLE+
				"(AUTH_ROLE_NM, PROG_AREA_CD, JURISDICTION_CD, AUTH_USER_UID, AUTH_PERM_SET_UID, ROLE_GUEST_IND, READ_ONLY_IND, DISP_SEQ_NBR, ADD_TIME, ADD_USER_ID, LAST_CHG_TIME, LAST_CHG_USER_ID, RECORD_STATUS_CD, RECORD_STATUS_TIME)"+
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		//todo: Compute oid using call to pajHashList()
		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_SECURE_USER_ROLE);
			preparedStmt.setString(i++,authUserRoleDT.getAuthRoleNm());	        //1
			preparedStmt.setString(i++,authUserRoleDT.getProgAreaCd()); //2
			preparedStmt.setString(i++,authUserRoleDT.getJurisdictionCd());      //3
			preparedStmt.setLong(i++,authUserRoleDT.getAuthUserUid());     //4
			if (authUserRoleDT.getAuthPermSetUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserRoleDT.getAuthPermSetUid().longValue());  //5
			preparedStmt.setString(i++,authUserRoleDT.getRoleGuestInd()); 	//6
			preparedStmt.setString(i++,authUserRoleDT.getReadOnlyInd()); 		//7
			if(authUserRoleDT.getDispSeqNbr()==null){
				preparedStmt.setNull(i++, Types.BIGINT);
			}else
				preparedStmt.setInt(i++, authUserRoleDT.getDispSeqNbr().intValue());  //8
			preparedStmt.setTimestamp(i++, authUserRoleDT.getAddTime());                   //9
			preparedStmt.setLong(i++, authUserRoleDT.getAddUserId().longValue());			   //10
			preparedStmt.setTimestamp(i++, authUserRoleDT.getLastChgTime());                   //11
			preparedStmt.setLong(i++, authUserRoleDT.getLastChgUserId().longValue());			   //12
			preparedStmt.setString(i++, authUserRoleDT.getRecordStatusCd());                   //13
			preparedStmt.setTimestamp(i++, authUserRoleDT.getRecordStatusTime());			   //14

			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting secure user role");
			logger.debug("resultCount inserting new user role count is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(auth_user_role_uid) from "+DataTables.SECURE_USER_ROLE);
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New User Role UID = " + rs2.getLong(1));
				secureUserRoleUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertSecureUserRoleDT.SQLException while inserting " +
					"a new entry in secure_user_role: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("insertSecureUserRoleDT.secureUserRole resultCount is "+resultCount);
			logger.fatal("insertSecureUserRoleDT.ERROR running INSERT_SECURE_USER_ROLE query:\n"+INSERT_SECURE_USER_ROLE);
			logger.fatal("insertSecureUserRoleDT.ERROR inserting authUserRoleDT with values:"+ authUserRoleDT.toString());
			throw new NEDSSSystemException( "insertSecureUserRoleDT.SQLException while inserting a new entry : \n"+sqlex );

		}
		catch(Exception ex)
		{
			logger.fatal("insertSecureUserRoleDT.Error while inserting into Secure_user_role TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("insertSecureUserRoleDT.secureUserRole resultCount is "+resultCount);
			logger.fatal("insertSecureUserRoleDT.ERROR running INSERT_SECURE_USER_ROLE query:\n"+INSERT_SECURE_USER_ROLE);
			logger.fatal("insertSecureUserRoleDT.ERROR inserting authUserRoleDT with values:"+ authUserRoleDT.toString());
			throw new NEDSSSystemException( "insertSecureUserRoleDT.Exception while inserting a new entry : \n"+ex );
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		//set the new uid
		authUserRoleDT.setAuthUserUid(secureUserRoleUid);
		return(authUserRoleDT);
	}

	/**
	 * Insert the ProgramAreaAdminDT record specified
	 * @param ProgramAreaAdminDT with user uid and prog area code fields in the table
	 * @return ProgramAreaAdminDT with updated UID
	 * @throws NEDSSSystemException
	 */
	public  AuthProgAreaAdminDT insertProgramAreaAdminDT(AuthProgAreaAdminDT authProgAreaAdminDT)
			throws NEDSSSystemException{

		logger.debug("Adding a new Program Area Admin record.");
		/**
		 * Inserting a new secure_program_area_admin rec
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureProgramAreaAdminUid = null;

		String INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR = "INSERT INTO " +DataTables.SECURE_PROGRAM_AREA_ADMIN+
				"(prog_area_cd,auth_user_uid,Auth_user_ind,add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time) "+
				"VALUES(?,?,?,?,?,?,?,?,?)";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR);
			preparedStmt.setString(i++,authProgAreaAdminDT.getProgAreaCd());	 //1
			preparedStmt.setLong(i++, authProgAreaAdminDT.getAuthUserUid().longValue());//2
			preparedStmt.setString(i++,authProgAreaAdminDT.getAuthUserInd());	 //3
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getAddTime()); 	// 4
			preparedStmt.setLong(i++, authProgAreaAdminDT.getAddUserId().longValue());//5
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getLastChgTime()); 	// 6
			preparedStmt.setLong(i++, authProgAreaAdminDT.getLastChgUserId().longValue());//7
			preparedStmt.setString(i++,authProgAreaAdminDT.getRecordStatusCd());	 //8
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getRecordStatusTime()); 	// 9


			resultCount = preparedStmt.executeUpdate();
			logger.debug("done inserting program area admin");
			logger.debug("resultCount inserting new prog area admin count is " + resultCount);


			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(Auth_prog_area_admin_uid) from "+DataTables.SECURE_PROGRAM_AREA_ADMIN);
			rs2 = preparedStmt2.executeQuery();
			if (rs2.next()) {
				logger.debug("New Prog Area Admin UID = " + rs2.getLong(1));
				secureProgramAreaAdminUid=new Long( rs2.getLong(1));
			}

		}
		catch(SQLException sqlex)
		{
			logger.fatal("insertProgramAreaAdminDT.SQLException while inserting " +
					"a new entry in secure program Area Admin table: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("insertProgramAreaAdminDT.secureBusinessObjectRight resultCount is "+resultCount);
			logger.fatal("insertProgramAreaAdminDT.ERROR running INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR query:\n"+INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR);
			logger.fatal("insertProgramAreaAdminDT.ERROR inserting authProgAreaAdminDT with values:"+ authProgAreaAdminDT.toString());
			throw new NEDSSSystemException( "insertProgramAreaAdminDT.Exception while inserting a new entry : \n"+sqlex );

		}
		catch(Exception ex)
		{
			logger.fatal("insertProgramAreaAdminDT.Error while inserting into Secure_program_area_admin TABLE, exception = " + ex.getMessage()+ ex);
			logger.fatal("insertProgramAreaAdminDT.secureProgramAreaAdmin resultCount is "+resultCount);
			logger.fatal("insertProgramAreaAdminDT.ERROR running INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR query:\n"+INSERT_NEW_PROGRAM_AREA_ADMINISTRATOR);
			logger.fatal("insertProgramAreaAdminDT.ERROR inserting authProgAreaAdminDT with values:"+ authProgAreaAdminDT.toString());
			throw new NEDSSSystemException( "insertProgramAreaAdminDT.Exception while inserting a new entry : \n"+ex );
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		authProgAreaAdminDT.setAuthProgAreaAdminUid(secureProgramAreaAdminUid);
		return(authProgAreaAdminDT);
	}

	/**
	 * Select a single Secure User record specified by the passed userId
	 * @param userid string i.e. PKS
	 * @return SecureUserDT
	 * @throws NEDSSSystemException
	 */
	public AuthUserDT selectSecureUserDT(Long entryId) throws NEDSSSystemException
	{
		logger.debug("Selecting auth_user for a a Secure User record"+ entryId);

		String SELECT_SECURE_USER_BY_ENTRY_ID = "SELECT auth_user_uid \"authUserUid\" " 
				+",user_id \"userId\" "
				+",user_type \"userType\" "
				+",user_title \"userTitle\" "
				+",user_department \"userDepartment\" "
				+",user_first_nm \"userFirstNm\" "
				+",user_last_nm \"userLastNm\" "
				+",user_work_email \"userWorkEmail\" "
				+",user_work_phone \"userWorkPhone\" "
				+",user_mobile_phone \"userMobilePhone\" "
				+",master_sec_admin_ind \"masterSecAdminInd\" "
				+",prog_area_admin_ind \"progAreaAdminInd\" "
				+",nedss_entry_id \"nedssEntryId\" "
				+",external_org_uid \"externalOrgUid\" "
				+",provider_uid \"providerUid\" "
				+",user_password \"userPassword\" "
				+",user_comments \"userComments\" "
				+",add_time \"addTime\" " 
				+",add_user_id \"addUserId\" "
				+",last_chg_time \"lastChgTime\" "
				+",last_chg_user_id \"lastChgUserId\" "
				+",record_status_cd \"recordStatusCd\" "
				+",record_status_time \"recordStatusTime\" FROM "+
				DataTables.SECURE_USER + " where nedss_Entry_Id = ?";

		//check for invalid entryId
		if (entryId == null) {
			logger.fatal("Invalid entryId  passed to selectSecureUserDT(please check):-"+ entryId);
			throw new NEDSSSystemException("ERROR: selectSecureUserDT.Error: Userid is null. Please check!!!!!!");
		}

		AuthUserDT authUserDT = new AuthUserDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		/**
		 * Selects a user rec from the Secure_user table
		 */
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_SECURE_USER_BY_ENTRY_ID);
			preparedStmt.setLong(1, entryId);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			authUserDT = (AuthUserDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, authUserDT.getClass());
			if(authUserDT != null && authUserDT.getUserId() != null && !authUserDT.getUserId().trim().equals(""))
				authUserDT.setUserId(EncryptorDecryptor.decrypt(authUserDT.getUserId()));
			else
				logger.error("User Id is null for nedss_entry_id = "+entryId);
			if(authUserDT != null){
				authUserDT.setItNew(false);
				authUserDT.setItDirty(false);
				authUserDT.setItDelete(false);
			}
		}
		catch(SQLException sqlException)
		{
			logger.fatal("SQLException while selecting a authUserDT ; entryId = <" +entryId + "> error = " + sqlException.getMessage(), sqlException);
			logger.fatal("ERROR running SELECT_SECURE_USER_BY_ENTRY_ID query:\n"+SELECT_SECURE_USER_BY_ENTRY_ID);
			throw new NEDSSSystemException( "selectSecureUserDT(Long entryId).SQLException while selecting for nedss_Entry_Id: \n"+sqlException);
		}
		catch(Exception ex)
		{
			logger.fatal("Exception while selecting a secureIserId ; entryId = <" +entryId + "> error = " + ex.getMessage(), ex);
			logger.fatal("ERROR running SELECT_SECURE_USER_BY_ENTRY_ID query:\n"+SELECT_SECURE_USER_BY_ENTRY_ID);
			throw new NEDSSSystemException( "selectSecureUserDT(Long entryId).Exception while selecting for nedss_Entry_Id: \n"+ex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returning a entryId object");
		return authUserDT;
	}//end of selecting a user rec via entryId


	/**
	 * Select a single Secure User record specified by the passed userId
	 * @param userid string i.e. PKS
	 * @return SecureUserDT
	 * @throws NEDSSSystemException
	 */
	public AuthUserDT selectSecureUserDT(String secureUserId) throws NEDSSSystemException
	{
		logger.debug("Selecting a Secure User record");
		String userId=EncryptorDecryptor.encrypt(secureUserId);

		String SELECT_SECURE_USER_BY_USER_ID = "SELECT auth_user_uid \"authUserUid\" "
				+",user_id \"userId\" "
				+",user_type \"userType\" "
				+",user_title \"userTitle\" "
				+",user_department \"userDepartment\" "
				+",user_first_nm \"userFirstNm\" "
				+",user_last_nm \"userLastNm\" "
				+",user_work_email \"userWorkEmail\" "
				+",user_work_phone \"userWorkPhone\" "
				+",user_mobile_phone \"userMobilePhone\" "
				+",master_sec_admin_ind \"masterSecAdminInd\" "
				+",prog_area_admin_ind \"progAreaAdminInd\" "
				+",nedss_entry_id \"nedssEntryId\" "
				+",external_org_uid \"externalOrgUid\" "
				+",provider_uid \"providerUid\" "
				+",user_password \"userPassword\" "
				+",user_comments \"userComments\" "
				+",add_time \"addTime\" " 
				+",add_user_id \"addUserId\" "
				+",last_chg_time \"lastChgTime\" "
				+",last_chg_user_id \"lastChgUserId\" "
				+",record_status_cd \"recordStatusCd\" "
				+",record_status_time \"recordStatusTime\" ,jurisdiction_derivation_ind \"jurisdictionDerivationInd\" FROM "+
				DataTables.SECURE_USER + " where user_id = ('"+userId+"')";

		//check for invalid userId
		if (secureUserId == null || secureUserId.isEmpty()) {
			//logger.fatal("Invalid userid passed to selectSecureUserDT"+ secureUserId);
			throw new NEDSSSystemException("Error: Userid is null or empty string.");
		}

		AuthUserDT authUserDT = new AuthUserDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		/**
		 * Selects a user rec from the Secure_user table
		 */
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_SECURE_USER_BY_USER_ID);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			authUserDT = (AuthUserDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, authUserDT.getClass());
			authUserDT.setItNew(false);
			authUserDT.setItDirty(false);
			authUserDT.setItDelete(false);
			authUserDT.setUserId(EncryptorDecryptor.decrypt(authUserDT.getUserId()));

		}
		catch(SQLException sqlException)
		{
			logger.fatal("selectSecureUserDT.SQLException while selecting a authUserDT ;  error = " + sqlException.getMessage(), sqlException);
			logger.fatal("selectSecureUserDT.ERROR running SELECT_SECURE_USER_BY_USER_ID query:\n"+SELECT_SECURE_USER_BY_USER_ID);
			throw new NEDSSSystemException( "selectSecureUserDT(String secureUserId).SQLException while inserting a new entry : \n"+sqlException);
		}
		catch(Exception ex)
		{
			logger.fatal("selectSecureUserDT.Exception while selecting a secureIserId ; error = " + ex.getMessage(), ex);
			logger.fatal("selectSecureUserDT.ERROR running SELECT_SECURE_USER_BY_USER_ID query:\n"+SELECT_SECURE_USER_BY_USER_ID);
			throw new NEDSSSystemException( "selectSecureUserDT(String secureUserId).SQLException while inserting a new entry : \n"+ex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returning a SecureUserDT object");
		return authUserDT;
	}//end of selecting a user rec via userId


	/**
	 * Select realized roles for the passed userId
	 * @param userid string i.e. PKS
	 * @return SecureUserRoleDT(s)
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectSecureUserRoleDT(String secureUserId) throws NEDSSSystemException
	{
		logger.debug("Selecting all the roles a userId has...");


		//For selecting roles by the user_id i.e. PKS
		String SELECT_REALIZED_ROLES_FOR_USER_ID = "SELECT PS.perm_set_nm \"permSetNm\" "
				+",SUR.auth_user_role_uid \"authUserRoleUid\" "
				+",SUR.auth_role_nm \"authRoleNm\" "
				+",SUR.prog_area_cd \"progAreaCd\" "
				+",SUR.jurisdiction_cd \"jurisdictionCd\" "
				+",SUR.auth_user_uid \"authUserUid\" "
				+",SUR.auth_perm_set_uid \"authPermSetUid\" "
				+",SUR.role_guest_ind \"roleGuestInd\" "
				+",SUR.read_only_ind \"readOnlyInd\" "
				+",SUR.disp_seq_nbr \"dispSeqNbr\" "
				+",SUR.add_time \"addTime\" "
				+",SUR.add_user_id \"addUserId\" "
				+",SUR.last_chg_time \"lastChgTime\" "
				+",SUR.last_chg_user_id \"lastChgUserId\" "
				+",SUR.record_status_cd \"recordStatusCd\" "
				+",SUR.record_status_time \"recordStatusTime\"  FROM "+
				DataTables.SECURE_USER_ROLE + " SUR, " +  DataTables.SECURE_PERMISSION_SET + " PS, " +DataTables.SECURE_USER + " SU "+
				" where SUR.auth_user_uid = SU.auth_user_uid "+
				" and PS.auth_perm_set_uid = SUR.auth_perm_set_uid "+
			//	" and UPPER(SU.user_id) = UPPER('"+secureUserId+"')";
		        " and UPPER(SU.user_id) = UPPER(?)";

		ArrayList<Object> rolesList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectSecureUserRoleDT: "+nedssEx.getMessage(), nedssEx);
			throw new NEDSSSystemException( "selectSecureUserRoleDT.NEDSSSystemException while inserting a new entry : \n"+nedssEx);
		}
		try {
			String encryptStringUserId =EncryptorDecryptor.encrypt(secureUserId);
			preparedStmt = dbConnection.prepareStatement(SELECT_REALIZED_ROLES_FOR_USER_ID);
			preparedStmt.setString(1, encryptStringUserId);
			resultSet = preparedStmt.executeQuery();
			rolesList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				rolesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthUserRoleDT.class,
						rolesList);

				logger.debug("returned with User Roles list");
			}

		} catch (SQLException sqlEx) {
			logger.fatal("selectSecureUserRoleDT.SQLException while selecting AuthUserRoleDT collection ; error = " + sqlEx.getMessage(), sqlEx);
			logger.fatal("selectSecureUserRoleDT.ERROR running SELECT_REALIZED_ROLES_FOR_USER_ID query:\n"+SELECT_REALIZED_ROLES_FOR_USER_ID);
			throw new NEDSSSystemException( "selectSecureUserRoleDT.SQLException while inserting a new entry : \n"+sqlEx);

		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectSecureUserRoleDT.SQLException while selecting AuthUserRoleDT collection ; error = " + resultSetUtilEx.getMessage(), resultSetUtilEx);
			logger.fatal("selectSecureUserRoleDT.ERROR running SELECT_REALIZED_ROLES_FOR_USER_ID query:\n"+SELECT_REALIZED_ROLES_FOR_USER_ID);
			logger.fatal("Error in result set handling while selecting user role list collection: DbAuthDAOImpl.selectUserRoleDT.",resultSetUtilEx);
			throw new NEDSSSystemException( "selectSecureUserRoleDT.ResultSetUtilsException while inserting a new entry : \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return rolesList;
	}
	/**
	 * Select any program area admin rights for the user uid
	 * @param Long secureUserUid
	 * @return SecureUserRoleDT(s)
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectSecureProgramAreaAdminList(Long secureUserUID) throws NEDSSSystemException
	{
		logger.debug("Selecting any PA Admin Rights a user has...");


		//For selecting roles by the user_uid
		String SELECT_PA_ADMIN_RIGHTS_FOR_USER =
				"SELECT Auth_prog_area_admin_uid \"AuthProgAreaAdminUid\" "
						+",prog_area_cd \"progAreaCd\" "
						+",Auth_user_ind \"authUserInd\" "
						+",add_time \"addTime\" "
						+",add_user_id \"addUserId\" "
						+",last_chg_time \"lastChgTime\" "
						+",last_chg_user_id \"lastChgUserId\" "
						+",record_status_cd \"recordStatusCd\" "
						+",record_status_time \"recordStatusTime\" FROM "
						+DataTables.SECURE_PROGRAM_AREA_ADMIN + " where auth_user_uid = ?";

		ArrayList<Object> adminList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectSecureProgramAreaAdminDT: "+ nedssEx.getMessage(), nedssEx);
			throw new NEDSSSystemException( "selectSecureProgramAreaAdminList.NEDSSSystemException while getting connection: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_PA_ADMIN_RIGHTS_FOR_USER);
			preparedStmt.setLong(1, secureUserUID);
			resultSet = preparedStmt.executeQuery();
			adminList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				adminList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthProgAreaAdminDT.class,
						adminList);

				logger.debug("returned with User Roles list");
			}

		} catch (SQLException sqlEx) {
			logger.fatal("selectSecureProgramAreaAdminList.SQLException while selecting AuthProgAreaAdminDT collection ; userid = <" +secureUserUID + "> error = " + sqlEx.getMessage(), sqlEx);
			logger.fatal("selectSecureProgramAreaAdminList.ERROR running SELECT_PA_ADMIN_RIGHTS_FOR_USER query:\n"+SELECT_PA_ADMIN_RIGHTS_FOR_USER);
			throw new NEDSSSystemException( "selectSecureProgramAreaAdminList.SQLException while selcting PA list for a user: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectSecureProgramAreaAdminList.SQLException while selecting AuthProgAreaAdminDT collection ; userid = <" +secureUserUID + "> error = " + resultSetUtilEx.getMessage(), resultSetUtilEx);
			logger.fatal("selectSecureProgramAreaAdminList.ERROR running SELECT_PA_ADMIN_RIGHTS_FOR_USER query:\n"+SELECT_PA_ADMIN_RIGHTS_FOR_USER);
			logger.fatal("Error in result set handling while selecting user PA Admin list collection: DbAuthDAOImpl.selectSecureProgramAreaAdminDT.",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "selectSecureProgramAreaAdminList.ResultSetUtilsException while selcting PA list for a user: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return adminList;
	}

	/**
	 * Select all distinct active permission sets for user uid
	 * @param Long secureUserUid
	 * @return SecurePermissionSetDT(s)
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> getDistinctPermissionSetsForUser(Long secureUserUid)
			throws NEDSSSystemException{
		logger.debug("Selecting all permission sets a user has...");


		//get distinct permission sets user_uid
		String SELECT_DISTINCT_PERMISSION_SETS_FOR_USER =
				"SELECT auth_perm_set_uid \"authPermSetUid\" "
						+",perm_set_nm \"permSetNm\" "
						+",perm_set_desc \"permSetDesc\" "
						+",sys_defined_perm_set_ind \"sysDefinedPermSetInd\" "
						+",add_time \"addTime\" "
						+",add_user_id \"addUserId\" "
						+",last_chg_time \"lastChgTime\" "
						+",last_chg_user_id \"lastChgUserId\" "
						+",record_status_cd \"recordStatusCd\" "
						+",record_status_time \"recordStatusTime\"  FROM "+
						DataTables.SECURE_PERMISSION_SET+
						" where auth_perm_set_uid in "+
						"(select distinct auth_perm_set_uid from "+
						DataTables.SECURE_USER_ROLE +
						" where auth_user_uid = ?)";

		ArrayList<Object> permissionSetList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.getDistinctPermissionSetsForUser: "+nedssEx.getMessage(), nedssEx);
			throw new NEDSSSystemException( "getDistinctPermissionSetsForUser.NEDSSSystemException while selcting Permission sets list for a user: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_DISTINCT_PERMISSION_SETS_FOR_USER);
			preparedStmt.setLong(1, secureUserUid);
			resultSet = preparedStmt.executeQuery();
			permissionSetList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				permissionSetList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthPermSetDT.class,
						permissionSetList);

				logger.debug("returned with User Permission Sets");
			}
		} catch (SQLException sqlEx) {
			logger.fatal("getDistinctPermissionSetsForUser.SQLException while selecting AuthPermSetDT collection ; userid = <" +secureUserUid + "> error = " + sqlEx.getMessage(), sqlEx);
			logger.fatal("getDistinctPermissionSetsForUser.ERROR running SELECT_DISTINCT_PERMISSION_SETS_FOR_USER query:\n"+SELECT_DISTINCT_PERMISSION_SETS_FOR_USER);
			throw new NEDSSSystemException( "getDistinctPermissionSetsForUser.SQLException while selcting Permission sets list for a user: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("getDistinctPermissionSetsForUser.SQLException while selecting AuthPermSetDT collection ; userid = <" +secureUserUid + "> error = " + resultSetUtilEx.getMessage(), resultSetUtilEx);
			logger.fatal("getDistinctPermissionSetsForUser.ERROR running SELECT_DISTINCT_PERMISSION_SETS_FOR_USER query:\n"+SELECT_DISTINCT_PERMISSION_SETS_FOR_USER);
			logger.fatal("Error in result set handling while selecting Distinct Permission Sets for User: DbAuthDAOImpl.selectSecureProgramAreaAdminDT.",resultSetUtilEx);
			throw new NEDSSSystemException( "getDistinctPermissionSetsForUser.ResultSetUtilsException while selcting Permission sets list for a user: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return permissionSetList;
	}

	/**
	 * Select max business object offset * nbr objects which specifies size of the array
	 * @param none
	 * @return int size of byte array needed
	 * @throws NEDSSSystemException
	 */

	public int getBusinessObjectOperationsSize()
			throws NEDSSSystemException{
		String maxOperationSequenceFromAuthBusObjType="select count(*) from Auth_bus_obj_type";
		String maxOperationSequenceFromAuthBusOpType="select count(*) from Auth_bus_op_type";
		logger.debug("Selecting size of byte array for NBSSecurityObj...");
		int boCount = 0;
		int opCount = 0;
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet maxBO = null;
		ResultSet maxOP = null;
		try {
			dbConnection = getConnection();
			stmt = dbConnection.createStatement();
			maxBO = stmt.executeQuery(maxOperationSequenceFromAuthBusObjType);
			maxBO.next();
			boCount = maxBO.getInt(1);
			maxOP = stmt.executeQuery(maxOperationSequenceFromAuthBusOpType);
			maxOP.next();
			opCount = maxOP.getInt(1);

		} catch (SQLException sqlEx) {
			logger.fatal("getBusinessObjectOperationsSize.SQLException while max operation_sequence; Query1(maxOperationSequenceFromAuthBusObjType)=\n "+maxOperationSequenceFromAuthBusObjType
					+" \n OR Query2(maxOperationSequenceFromAuthBusOpType)=\n "+maxOperationSequenceFromAuthBusOpType+"\n SQLException="+sqlEx.getMessage(), sqlEx);
			throw new NEDSSSystemException( "getBusinessObjectOperationsSize.SQLException Thrown: \n"+sqlEx);
		} finally {
			closeResultSet(maxBO);
			closeResultSet(maxOP);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		boCount = boCount + 1;  //add one since we start with zero
		if (boCount != 0 && opCount != 0)
			return (boCount * opCount);  //return the number of Objects * Operations for byte array size
		else
			return 0;
	}
	/**
	 * Select max business object offset which specifies the location in the array
	 * @param none
	 * @return int max offset
	 * @throws NEDSSSystemException
	 */
	public int getBusinessOperationsSize() throws NEDSSSystemException
	{
		logger.debug("Selecting size of business operations");
		int opCount = 0;
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet maxOP = null;
		String maxOperationSequenceFromAuthBusOpType="select MAX(operation_sequence) from Auth_bus_op_type";
		try {
			dbConnection = getConnection();
			stmt = dbConnection.createStatement();
			maxOP = stmt.executeQuery(maxOperationSequenceFromAuthBusOpType);			         
			maxOP.next();
			opCount = maxOP.getInt(1);

		} catch (SQLException sqlEx) {
			logger.fatal("getBusinessOperationsSize.SQLException while max operation_sequence; Query(maxOperationSequenceFromAuthBusOpType)=\n "+maxOperationSequenceFromAuthBusOpType+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			throw new NEDSSSystemException( "getBusinessOperationsSize.SQLException Thrown: \n"+sqlEx);
		} finally {
			closeResultSet(maxOP);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		return opCount;
	}

	/**
	 * Select all distinct active permission sets for user uid
	 * @param Long secureUserUid
	 * @return SecurePermissionSetDT(s)
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectBusinessObjectsForPermissionSet(Long securePermissionSetUid)throws NEDSSSystemException
	{
		logger.debug("Selecting all business objects a permission set has specified...");

		String SELECT_BUSINESS_OBJECTS_FOR_PERMISSION_SET = "SELECT bor.auth_bus_obj_rt_uid \"authBusObjRtUid\" "
				+",bor.auth_perm_set_uid \"authPermSetUid\" "
				+",bor.auth_bus_obj_type_uid \"authBusObjTypeUid\" "
				+",bot.operation_sequence \"operationSequence\" "
				+",bor.add_time \"addTime\" "
				+",bor.add_user_id \"addUserId\" "
				+",bor.last_chg_time \"lastChgTime\" "
				+",bor.last_chg_user_id \"lastChgUserId\" "
				+",bor.record_status_cd \"recordStatusCd\" "
				+",bor.record_status_time \"recordStatusTime\" FROM "
				+DataTables.SECURE_BUSINESS_OBJECT_RIGHT + " bor, " + DataTables.SECURE_BUSINESS_OBJECT_TYPE + " bot " 
				+" where bor.auth_bus_obj_type_uid = bot.auth_bus_obj_type_uid and bor.auth_perm_set_uid=? ";




		ArrayList<Object> businessObjectList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectBusinessObjectsForPermissionSet: "+nedssEx.getMessage(), nedssEx);
			throw new NEDSSSystemException( "selectBusinessObjectsForPermissionSet.NEDSSSystemException Thrown: \n"+nedssEx);

		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_BUSINESS_OBJECTS_FOR_PERMISSION_SET);
			preparedStmt.setLong(1, securePermissionSetUid);
			resultSet = preparedStmt.executeQuery();
			businessObjectList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				businessObjectList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthBusObjRtDT.class,
						businessObjectList);
			}
		} catch (SQLException sqlEx) {
			logger.fatal("selectBusinessObjectsForPermissionSet.SQLException while getting AuthBusObjRtDT collection from query=\n "+SELECT_BUSINESS_OBJECTS_FOR_PERMISSION_SET+"\n SQLException="+sqlEx.getMessage(), sqlEx);
			throw new NEDSSSystemException( "selectBusinessObjectsForPermissionSet.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectBusinessObjectsForPermissionSet.SQLException while getting AuthBusObjRtDT collection from query=\n "+SELECT_BUSINESS_OBJECTS_FOR_PERMISSION_SET+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("selectBusinessObjectsForPermissionSet.Error in result set handling while in Select Business Objects For Permission Set: DbAuthDAOImpl.selectBusinessObjectsForPermissionSet.",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "selectBusinessObjectsForPermissionSet.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returned with Bus Object List for Permission Set");
		return businessObjectList;
	}

	/**
	 * Select all distinct active permission sets for user uid
	 * @param Long secureUserUid
	 * @return SecurePermissionSetDT(s)
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectOperationsForBusinessObject(Long secureBusinessObjectRightUid)
			throws NEDSSSystemException
			{
		logger.debug("Selecting all operations present for a business object in a permission set...");

		String SELECT_BUSINESS_OPERATIONS_FOR_BUSINESS_OBJECT ="SELECT bor.Auth_bus_op_rt_uid  \"AuthBusOpRtUid\"  "
				+",bor.auth_bus_op_type_uid \"authBusOpTypeUid\" "
				+",bor.auth_bus_obj_rt_uid \"authBusObjRtUid\" "
				+",bor.bus_op_user_rt  \"busOpUserRt\" "
				+",bor.bus_op_guest_rt \"busOpGuestRt\" "
				+",bot.operation_sequence \"operationSequence\" "
				+",bor.add_time \"addTime\" "
				+",bor.add_user_id \"addUserId\" "
				+",bor.last_chg_time \"lastChgTime\" "
				+",bor.last_chg_user_id \"lastChgUserId\" "
				+",bor.record_status_cd \"recordStatusCd\" "
				+",bor.record_status_time \"recordStatusTime\" FROM "
				+DataTables.SECURE_BUSINESS_OPERATION_RIGHT+" bor, "+ DataTables.SECURE_BUSINESS_OPERATION_TYPE+" bot "
				+" where bor.auth_bus_op_type_uid = bot.auth_bus_op_type_uid and bor.auth_bus_obj_rt_uid = ? ";

		ArrayList<Object> operationList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectOperationsForBusinessObject: ", nedssEx);
			throw new NEDSSSystemException( "selectOperationsForBusinessObject.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_BUSINESS_OPERATIONS_FOR_BUSINESS_OBJECT);
			preparedStmt.setLong(1, secureBusinessObjectRightUid);
			resultSet = preparedStmt.executeQuery();
			operationList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				operationList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthBusOpRtDT.class,
						operationList);
			}
		} catch (SQLException sqlEx) {
			logger.fatal("selectOperationsForBusinessObject.SQLException while getting AuthBusOpRtDT collection from query=\n "+SELECT_BUSINESS_OPERATIONS_FOR_BUSINESS_OBJECT+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			throw new NEDSSSystemException( "selectOperationsForBusinessObject.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectOperationsForBusinessObject.SQLException while getting AuthBusOpRtDT collection from query=\n "+SELECT_BUSINESS_OPERATIONS_FOR_BUSINESS_OBJECT+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("Error in result set handling while selecting operations for a bus obj right: DbAuthDAOImpl.selectOperationsForBusinessObject.",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "selectOperationsForBusinessObject.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		logger.debug("returned with operation list for a bus obj right");
		return operationList;
			}



	/**
	 * Select all business object types i.e. system, patient, provider
	 * @param none
	 * @return collection
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectAllBusinessObjectTypes()throws NEDSSSystemException
	{
		logger.debug("Selecting all business object types...");


		//Note that the business object DT has some fields from the type table
		String SELECT_ALL_BUSINESS_OBJECT_TYPES = "SELECT AUTH_BUS_OBJ_TYPE_UID \"authBusObjTypeUid\", BUS_OBJ_NM \"busObjNm\""
				+",BUS_OBJ_DISP_NM \"busObjDispNm\",PROG_AREA_IND \"progAreaInd\",JURISDICTION_IND \"jurisdictionInd\""
				+",ADD_TIME \"addTime\",ADD_USER_ID \"addUserId\",LAST_CHG_TIME \"lastChgTime\""
				+",LAST_CHG_USER_ID \"lastChgUserId\",RECORD_STATUS_CD \"recordStatusCd\",RECORD_STATUS_TIME \"recordStatusTime\""
				+",OPERATION_SEQUENCE \"operationSequence\" FROM "+
				DataTables.SECURE_BUSINESS_OBJECT_TYPE+
				" order by OPERATION_SEQUENCE";

		ArrayList<Object> businessObjectTypeList = null;
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectAllBusinessObjectTypes: ", nedssEx);
			throw new NEDSSSystemException( "selectAllBusinessObjectTypes.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			stmt = dbConnection.createStatement();
			resultSet = stmt.executeQuery(SELECT_ALL_BUSINESS_OBJECT_TYPES);
			businessObjectTypeList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				businessObjectTypeList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthBusObjTypeDT.class,
						businessObjectTypeList);
			}
		} catch (SQLException sqlEx) {
			logger.fatal("selectAllBusinessObjectTypes.SQLException while getting AuthBusObjTypeDT collection from query=\n "+SELECT_ALL_BUSINESS_OBJECT_TYPES+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			throw new NEDSSSystemException( "selectAllBusinessObjectTypes.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectAllBusinessObjectTypes.SQLException while getting AuthBusObjTypeDT collection from query=\n "+SELECT_ALL_BUSINESS_OBJECT_TYPES+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.selectAllBusinessObjectTypes.",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "selectAllBusinessObjectTypes.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returned with Bus Object Types List");
		return businessObjectTypeList;
	}

	/**
	 * Select all business operation types i.e. view, edit, delete
	 * @param none
	 * @return collection
	 * @throws NEDSSSystemException
	 */
	public Collection<Object> selectAllBusinessOperationTypes()throws NEDSSSystemException
	{
		logger.debug("Selecting all business operation types...");


		//Note that the business object DT has some fields from the type table
		String SELECT_ALL_BUSINESS_OBJECT_TYPES = "select auth_bus_op_type_uid \"authBusOpTypeUid\",bus_op_nm \"busOpNm\","
				+" bus_op_disp_nm \"busOpDispNm\",add_time \"addTime\",add_user_id \"addUserId\",last_chg_time \"lastChgTime\","
				+" last_chg_user_id \"lastChgUserId\",record_status_cd \"recordStatusCd\",record_status_time \"recordStatusTime\",operation_sequence \"operationSequence\" FROM "
				+ DataTables.SECURE_BUSINESS_OPERATION_TYPE+
				" order by operation_sequence";


		ArrayList<Object> businessOperationTypeList = null;
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("selectAllBusinessOperationTypes.SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.selectAllBusinessOperationTypes: ", nedssEx);
			throw new NEDSSSystemException( "selectAllBusinessOperationTypes.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			stmt = dbConnection.createStatement();
			resultSet = stmt.executeQuery(SELECT_ALL_BUSINESS_OBJECT_TYPES);
			businessOperationTypeList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				businessOperationTypeList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthBusOpTypeDT.class,
						businessOperationTypeList);
			}
		} catch (SQLException sqlEx) {
			logger.fatal("selectAllBusinessOperationTypes.SQLException while getting AuthBusObjTypeDT collection from query=\n "+SELECT_ALL_BUSINESS_OBJECT_TYPES+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("selectAllBusinessOperationTypes.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.selectAllBusinessObjectTypes.",sqlEx);
			throw new NEDSSSystemException("selectAllBusinessOperationTypes.SQLException while selecting all business object types "
					+ "DbAuthDAOImpl.selectAllBusinessOperationTypes() "
					+ sqlEx.getMessage());

		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("selectAllBusinessOperationTypes.SQLException while getting AuthBusObjTypeDT collection from query=\n "+SELECT_ALL_BUSINESS_OBJECT_TYPES+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("selectAllBusinessOperationTypes.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.selectAllBusinessObjectTypes.",resultSetUtilEx);
			logger.fatal("selectAllBusinessOperationTypes.Error in result set handling while in Select Business Operation Types: DbAuthDAOImpl.selectBusinessObjectsForPermissionSet.",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "selectAllBusinessOperationTypes.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returned with all Bus Operation List");
		return businessOperationTypeList;
	}

	/**
	 * Select UID of a secure_user set by the user_id
	 * @param none
	 * @return collection
	 * @throws NEDSSSystemException
	 */
	public Long doesUserExist(String userID, boolean checkActive)throws NEDSSSystemException
	{
		logger.debug("Is User, userID= " + userID + " in database already?");
		String encryptString =EncryptorDecryptor.encrypt(userID);
		
		//Note that the business object DT has some fields from the type table
		String SELECT_SECURE_USER = "SELECT auth_user_uid "+
				" FROM " + DataTables.SECURE_USER + " where user_id = ('"+encryptString+"')";
		if(checkActive)
			SELECT_SECURE_USER = SELECT_SECURE_USER+ " and record_status_cd='"+NEDSSConstants.RECORD_STATUS_ACTIVE+"'";
		
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		Long secureUserUid = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.doesUserExist: ", nedssEx);
			throw new NEDSSSystemException( "doesUserExist.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			stmt = dbConnection.createStatement();
			resultSet = stmt.executeQuery(SELECT_SECURE_USER);
			if (resultSet != null)
				if (resultSet.next()) {
					logger.debug("Secure_user Found = " + resultSet.getLong(1));
					secureUserUid=new Long(resultSet.getLong(1));
				}
		} catch (SQLException sqlEx) {
			logger.fatal("doesUserExist.SQLException while checking user exists=\n "+SELECT_SECURE_USER+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("doesUserExist.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.doesUserExist.",sqlEx);
			throw new NEDSSSystemException( "doesUserExist.SQLException Thrown: \n"+sqlEx);
		} catch (Exception ex) {
			logger.fatal("doesUserExist.SQLException while checking user exists=\n "+SELECT_SECURE_USER+"\n SQLException="+ex.getMessage(),ex);
			logger.fatal("doesUserExist.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.doesUserExist.",ex);
			logger.fatal("General error selecting secure_user: DbAuthDAOImpl.doesUserExist - ",ex.getMessage());
			throw new NEDSSSystemException( "doesUserExist.Exception Thrown: \n"+ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returned with secureUserUid");
		return secureUserUid;
	}




	/**
	 * Select UID of a permission set by the name
	 * @param none 
	 * @return collection
	 * @throws NEDSSSystemException
	 */
	public Long doesPermissionSetNameExist(String permissionSetName)throws NEDSSSystemException
	{
		logger.debug("Is Permission Set Name " + permissionSetName + " in database already?");


		//Note that the business object DT has some fields from the type table
		String SELECT_PERMISSION_SET_NAME = "SELECT auth_perm_set_uid \"authPermSetUid\""+
				" FROM "+DataTables.SECURE_PERMISSION_SET+
				" where upper(perm_set_nm) = upper('"+permissionSetName+"')";

		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		Long permissionSetUid = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			throw new NEDSSSystemException( "doesPermissionSetNameExist.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			stmt = dbConnection.createStatement();
			resultSet = stmt.executeQuery(SELECT_PERMISSION_SET_NAME);
			if (resultSet != null)
				if (resultSet.next()) {
					logger.debug("Permission Set Found = " + resultSet.getLong(1));
					permissionSetUid=new Long( resultSet.getLong(1));
				}
		} catch (SQLException sqlEx) {
			logger.fatal("doesPermissionSetNameExist.SQLException while checking user exists=\n "+SELECT_PERMISSION_SET_NAME+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("doesPermissionSetNameExist.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.doesPermissionSetNameExist.",sqlEx);
			throw new NEDSSSystemException( "doesPermissionSetNameExist.SQLException Thrown: \n"+sqlEx);
		} catch (Exception ex) {
			logger.fatal("General error selecting permission set: DbAuthDAOImpl.doesPermissionSetNameExist"+ex.getMessage(),ex);
			throw new NEDSSSystemException( "doesPermissionSetNameExist.Exception Thrown: \n"+ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returned with permissionSetUid");
		return permissionSetUid;
	}

	public Collection<Object> getPermissionSetDTList()
			throws NEDSSSystemException
			{
		logger.debug("Selecting all permission sets...");


		//get distinct permission sets user_uid
		String SELECT_DISTINCT_PERMISSION_SETS =
				"SELECT auth_perm_set_uid \"authPermSetUid\","+
						"perm_set_nm\"permSetNm\","+
						"perm_set_desc\"permSetDesc\","+
						"sys_defined_perm_set_ind \"sysDefinedPermSetInd\","+
						"add_user_id \"addUserId\", add_time \"addTime\", record_status_cd \"recordStatusCd\",record_status_time \"recordStatusTime\","+
						"last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" FROM "+
						DataTables.SECURE_PERMISSION_SET;

		ArrayList<Object> permissionSetList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.getPermissionSetDTList: ", nedssEx);
			throw new NEDSSSystemException( "getPermissionSetDTList.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_DISTINCT_PERMISSION_SETS);

			resultSet = preparedStmt.executeQuery();
			permissionSetList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				permissionSetList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthPermSetDT.class,
						permissionSetList);

				logger.debug("returned with  Permission Sets");
			}
		} catch (SQLException sqlEx) {
			logger.fatal("getPermissionSetDTList.SQLException while checking Permission sets=\n "+SELECT_DISTINCT_PERMISSION_SETS+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("getPermissionSetDTList.Error in result set handling while Permission sets.",sqlEx);
			throw new NEDSSSystemException( "getPermissionSetDTList.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("getPermissionSetDTList.SQLException while checking Permission sets=\n "+SELECT_DISTINCT_PERMISSION_SETS+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("getPermissionSetDTList.Error in result set handling while Permission sets.",resultSetUtilEx);
			logger.fatal("Error in result set handling while selecting Distinct Permission Sets for User: DbAuthDAOImpl.getPermissionSetDTList",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "getPermissionSetDTList.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return permissionSetList;
			}

	public AuthPermSetDT getPermissionSetDT(String permissionSetName){

		logger.debug("Selecting the permission set DT...");
		String GET_PERMISSION_SET =
				"SELECT auth_perm_set_uid \"authPermSetUid\","+
						"perm_set_nm \"permSetNm\","+
						"perm_set_desc \"permSetDesc\","+
						"sys_defined_perm_set_ind \"sysDefinedPermSetInd\","+
						"add_user_id \"addUserId\", add_time \"addTime\", record_status_cd \"recordStatusCd\",record_status_time \"recordStatusTime\","+
						"last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" FROM "+
						DataTables.SECURE_PERMISSION_SET + " WHERE upper(perm_set_nm) = upper(?)";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.getPermissionSetDT: ", nedssEx);
			throw new NEDSSSystemException( "getPermissionSetDT.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		AuthPermSetDT authPermSetDT = new AuthPermSetDT();
		try {
			preparedStmt = dbConnection.prepareStatement(GET_PERMISSION_SET);
			preparedStmt.setString(1, permissionSetName);
			resultSet = preparedStmt.executeQuery();

			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				authPermSetDT =  (AuthPermSetDT)resultSetUtils.mapRsToBean(
						resultSet, resultSetMetaData, AuthPermSetDT.class);

				logger.debug("returned with  SecurePermission Set DT");
			}
		} catch (SQLException sqlEx) {
			logger.fatal("getPermissionSetDT.SQLException while getting Permission sets=\n "+GET_PERMISSION_SET+"\n for PermissionName="+permissionSetName+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("getPermissionSetDT.Error in result set handling while Permission sets.",sqlEx);
			throw new NEDSSSystemException( "getPermissionSetDT.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("getPermissionSetDT.SQLException while getting Permission sets=\n "+GET_PERMISSION_SET+"\n for PermissionName="+permissionSetName+"\n resultSetUtilEx="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("getPermissionSetDT.Error in result set handling while Permission sets.",resultSetUtilEx);
			logger.fatal("Error in result set handling while selecting Distinct Permission Sets for User: DbAuthDAOImpl.getPermissionSetDT",resultSetUtilEx.getMessage());
			throw new NEDSSSystemException( "getPermissionSetDT.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return authPermSetDT;
	}

	public  Long updateSecurePermissionSetDT(AuthPermSetDT authPermSetDT)
			throws NEDSSSystemException{

		logger.debug("Updating new permission set into the secure_permission_set table.");
		/**
		 * Inserting a new permission set
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		
		String UPDATE_SECURE_PERMISSION_SET = "UPDATE " +DataTables.SECURE_PERMISSION_SET+
				" SET PERM_SET_NM = ?,PERM_SET_DESC =?,SYS_DEFINED_PERM_SET_IND = ?,ADD_TIME = ?,ADD_USER_ID = ?,LAST_CHG_TIME = ?,LAST_CHG_USER_ID = ?,RECORD_STATUS_CD = ? ,RECORD_STATUS_TIME = ?"
				+" WHERE AUTH_PERM_SET_UID=? ";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_SECURE_PERMISSION_SET);
			preparedStmt.setString(i++,authPermSetDT.getPermSetNm());		   //1
			preparedStmt.setString(i++,authPermSetDT.getPermSetDesc()); //2
			preparedStmt.setString(i++,authPermSetDT.getSysDefinedPermSetInd());  //3
			preparedStmt.setTimestamp(i++, authPermSetDT.getAddTime());                //4
			preparedStmt.setLong(i++, authPermSetDT.getAddUserId().longValue());		   //5
			preparedStmt.setTimestamp(i++, authPermSetDT.getLastChgTime()); 	       //6
			preparedStmt.setLong(i++, authPermSetDT.getLastChgUserId().longValue());   //7
			preparedStmt.setString(i++, authPermSetDT.getRecordStatusCd());//8
			preparedStmt.setTimestamp(i++, authPermSetDT.getRecordStatusTime());// 9
			preparedStmt.setLong(i++, authPermSetDT.getAuthPermSetUid());//10

			resultCount = preparedStmt.executeUpdate();

			logger.debug("done Updating the permission set < "+authPermSetDT.getPermSetNm() + ">");
			logger.debug("resultCount Updating new perm set is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while Updating " +
					"a new entry in secure_permission_set: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("securePermissionSet DT is "+authPermSetDT.toString());
			logger.fatal("securePermissionSet resultCount is "+resultCount);
			logger.fatal("updateSecurePermissionSetDT.SQLException while updating Permission sets=\n "+UPDATE_SECURE_PERMISSION_SET+"\n for authPermSetDT="+authPermSetDT.toString()+"\n SQLException="+sqlex);
			logger.fatal("updateSecurePermissionSetDT.Error in result set handling while Permission sets.",sqlex);
			throw new NEDSSSystemException( "updateSecurePermissionSetDT.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while Updating into Secure_permission_set TABLE, exception = " + ex.getMessage(),ex);
			logger.fatal("securePermissionSet resultCount is "+resultCount);
			logger.fatal("updateSecurePermissionSetDT.SQLException while updating Permission sets=\n "+UPDATE_SECURE_PERMISSION_SET+"\n for authPermSetDT="+authPermSetDT.toString()+"\n SQLException="+ex);
			logger.fatal("updateSecurePermissionSetDT.Error in result set handling while Permission sets.",ex);
			throw new NEDSSSystemException( "updateSecurePermissionSetDT.Exception Thrown: \n"+ex);
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		return authPermSetDT.getAuthPermSetUid();


	} //PermissionSet

	public Collection<Object>getSecureUserDTList() throws NEDSSSystemException 
	{
		logger.debug("Selecting all permission sets...");			

		//get distinct permission sets user_uid
		String SELECT_USERS = 
				"SELECT auth_user_uid \"authUserUid\",user_id \"userId\",user_type \"userType\",user_title \"userTitle\""
						+",user_department \"userDepartment\",user_first_nm \"userFirstNm\",user_last_nm \"userLastNm\",user_work_email \"userWorkEmail\""
						+",user_work_phone \"userWorkPhone\",user_mobile_phone \"userMobilePhone\",master_sec_admin_ind \"masterSecAdminInd\",prog_area_admin_ind \"progAreaAdminInd\""
						+",nedss_entry_id \"nedssEntryId\",external_org_uid \"externalOrgUid\",user_password \"userPassword\",user_comments \"userComments\""
						+",add_time \"addTime\",add_user_id \"addUserId\",last_chg_time \"lastChgTime\",last_chg_user_id \"lastChgUserId\",record_status_cd \"recordStatusCd\",record_status_time \"recordStatusTime\" from "
						+ DataTables.SECURE_USER;			

		ArrayList<Object> usersDTList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.getSecureUserDTList: ", nedssEx);
			throw new NEDSSSystemException( "getSecureUserDTList.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_USERS);

			resultSet = preparedStmt.executeQuery();
			usersDTList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				usersDTList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthUserDT.class,
						usersDTList);
				logger.debug("returned with  users  Lists");
			}
			if(usersDTList!=null && usersDTList.size()>0){
				Iterator<Object> it  = usersDTList.iterator();
				while(it.hasNext()){
					AuthUserDT userDT = (AuthUserDT)it.next();
					userDT.setUserId(EncryptorDecryptor.decrypt(userDT.getUserId()));
				}
			}
		} catch (SQLException sqlEx) {
			logger.fatal("getSecureUserDTList.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("getSecureUserDTList.Error in result set handling while Permission sets.",sqlEx);
			throw new NEDSSSystemException( "getSecureUserDTList.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("getSecureUserDTList.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("getSecureUserDTList.Error in result set handling while Permission sets.",resultSetUtilEx);
			logger.fatal("Error in result set handling while selecting Distinct Users List: DbAuthDAOImpl.getSecureUserDTList");
			throw new NEDSSSystemException( "getSecureUserDTList.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return usersDTList;
	}


	public Collection<Object>getSecureUserDTListBasedOnProgramArea(String programAreas) throws NEDSSSystemException 
	{
		logger.debug("Selecting all permission sets...");			

		//get distinct permission sets user_uid
		String SELECT_USERS = 
				"SELECT auth_user_uid \"authUserUid\",user_id \"userId\",user_type \"userType\",user_title \"userTitle\""
						+",user_department \"userDepartment\",user_first_nm \"userFirstNm\",user_last_nm \"userLastNm\",user_work_email \"userWorkEmail\""
						+",user_work_phone \"userWorkPhone\",user_mobile_phone \"userMobilePhone\",master_sec_admin_ind \"masterSecAdminInd\",prog_area_admin_ind \"progAreaAdminInd\""
						+",nedss_entry_id \"nedssEntryId\",external_org_uid \"externalOrgUid\",user_password \"userPassword\",user_comments \"userComments\""
						+",add_time \"addTime\",add_user_id \"addUserId\",last_chg_time \"lastChgTime\",last_chg_user_id \"lastChgUserId\",record_status_cd \"recordStatusCd\",record_status_time \"recordStatusTime\" from "
						+ DataTables.SECURE_USER;	
		
		SELECT_USERS+= " where auth_user_uid in (select auth_user_uid from auth_user_role where prog_area_cd in ("+programAreas+"))";

		ArrayList<Object> usersDTList = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.getSecureUserDTList: ", nedssEx);
			throw new NEDSSSystemException( "getSecureUserDTList.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_USERS);

			resultSet = preparedStmt.executeQuery();
			usersDTList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				usersDTList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AuthUserDT.class,
						usersDTList);
				logger.debug("returned with  users  Lists");
			}
			if(usersDTList!=null && usersDTList.size()>0){
				Iterator<Object> it  = usersDTList.iterator();
				while(it.hasNext()){
					AuthUserDT userDT = (AuthUserDT)it.next();
					userDT.setUserId(EncryptorDecryptor.decrypt(userDT.getUserId()));
				}
			}
		} catch (SQLException sqlEx) {
			logger.fatal("getSecureUserDTList.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+sqlEx.getMessage(),sqlEx);
			logger.fatal("getSecureUserDTList.Error in result set handling while Permission sets.",sqlEx);
			throw new NEDSSSystemException( "getSecureUserDTList.SQLException Thrown: \n"+sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("getSecureUserDTList.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+resultSetUtilEx.getMessage(),resultSetUtilEx);
			logger.fatal("getSecureUserDTList.Error in result set handling while Permission sets.",resultSetUtilEx);
			logger.fatal("Error in result set handling while selecting Distinct Users List: DbAuthDAOImpl.getSecureUserDTList");
			throw new NEDSSSystemException( "getSecureUserDTList.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return usersDTList;
	}


	

	public  AuthUserDT updateSecureUserDT(AuthUserDT authUserDT)
			throws NEDSSSystemException{

		logger.debug("Updating new user record into the secure_user table.");
		/**
		 * Inserting a new user
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;


		String UPDATE_SECURE_USER = "UPDATE " +DataTables.SECURE_USER+
				" SET USER_TYPE =?,USER_TITLE = ?,USER_DEPARTMENT = ?,USER_FIRST_NM = ?,USER_LAST_NM = ?,USER_WORK_EMAIL = ?,USER_WORK_PHONE = ? "
				+",USER_MOBILE_PHONE =?,MASTER_SEC_ADMIN_IND = ?,PROG_AREA_ADMIN_IND = ?,NEDSS_ENTRY_ID = ?,USER_PASSWORD = ?,EXTERNAL_ORG_UID = ?, PROVIDER_UID = ?, USER_COMMENTS = ? "
				+",LAST_CHG_USER_ID = ?,LAST_CHG_TIME = ?,RECORD_STATUS_CD = ?,RECORD_STATUS_TIME = ?, JURISDICTION_DERIVATION_IND = ? "
				+" WHERE USER_ID = ?";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_SECURE_USER);
			preparedStmt.setString(i++,authUserDT.getUserType());		 //1
			preparedStmt.setString(i++,authUserDT.getUserTitle());	 //2
			preparedStmt.setString(i++,authUserDT.getUserDepartment());//3
			preparedStmt.setString(i++,authUserDT.getUserFirstNm()); //4
			preparedStmt.setString(i++,authUserDT.getUserLastNm());  //5
			preparedStmt.setString(i++,authUserDT.getUserWorkEmail()); //6
			preparedStmt.setString(i++,authUserDT.getUserWorkPhone()); //7
			preparedStmt.setString(i++,authUserDT.getUserMobilePhone()); //8
			preparedStmt.setString(i++,authUserDT.getMasterSecAdminInd());//9
			preparedStmt.setString(i++,authUserDT.getProgAreaAdminInd());//10
			preparedStmt.setLong(i++, authUserDT.getNedssEntryId().longValue()); // 11

			preparedStmt.setString(i++,authUserDT.getUserPassword());//12
			if(authUserDT.getExternalOrgUid()==null)
				preparedStmt.setNull(i++, Types.INTEGER); // 13
			else		
				preparedStmt.setLong(i++, authUserDT.getExternalOrgUid().longValue()); // 13
			if(authUserDT.getProviderUid()==null)
				preparedStmt.setNull(i++, Types.INTEGER); // 13
			else		
				preparedStmt.setLong(i++, authUserDT.getProviderUid().longValue()); // 13
			preparedStmt.setString(i++,authUserDT.getUserComments());     //14
			preparedStmt.setLong(i++, authUserDT.getLastChgUserId().longValue());//15
			preparedStmt.setTimestamp(i++, authUserDT.getLastChgTime()); 	// 16
			preparedStmt.setString(i++, authUserDT.getRecordStatusCd());//17
			preparedStmt.setTimestamp(i++, authUserDT.getRecordStatusTime()); // 18
			preparedStmt.setString(i++, authUserDT.getJurisdictionDerivationInd()); // 19
			preparedStmt.setString(i++,EncryptorDecryptor.encrypt(authUserDT.getUserId()));//20


			resultCount = preparedStmt.executeUpdate();
			logger.debug("done updating the user < "+authUserDT.getNedssEntryId() + ">");	 

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating " +
					"a new entry in secure_user: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("updateSecureUserDT.SQLException while getting Users=\n "+UPDATE_SECURE_USER+"\n authUserDT is "+authUserDT.toString()+"\n SQLException="+sqlex);
			throw new NEDSSSystemException( "updateSecureUserDT.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating into Secure_user TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("authUserDT DT is "+authUserDT.toString());
			logger.fatal("updateSecureUserDT.SQLException while getting Users=\n "+UPDATE_SECURE_USER+"\n authUserDT is "+authUserDT.toString()+"\n SQLException="+ex);
			logger.fatal("authUserDT resultCount is "+resultCount);
			throw new NEDSSSystemException( "updateSecureUserDT.Exception Thrown: \n"+ex);
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}

		return(authUserDT);
	}

	public void deleteProgramAreaAdmin(Long secureUserId)throws NEDSSDAOSysException, NEDSSSystemException {
		String DELETE_PROGRAM_AREA_ADMIN = "Delete "+DataTables.SECURE_PROGRAM_AREA_ADMIN+" WHERE auth_user_uid= ? ";
		try{
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(secureUserId);

			preparedStmtMethod(null, paramList,
					DELETE_PROGRAM_AREA_ADMIN,
					NEDSSConstants.UPDATE);

		}catch(Exception e){
			logger.fatal("deleteProgramAreaAdmin.Exception while deleting deleteProgramAreaAdmin fir user =\n "+secureUserId+"\n The delete query is "+DELETE_PROGRAM_AREA_ADMIN+"\n SQLException="+e.getMessage(),e);
			logger.error("Error while deleting the SECURE_PROGRAM_AREA_ADMIN Mapping table " + e.getMessage());
			throw new NEDSSSystemException( "deleteProgramAreaAdmin.Exception Thrown: \n"+e);
		}
	}
	public void deleteSecureUserRole(Long secureUserId)throws NEDSSDAOSysException, NEDSSSystemException {
		String DELETE_SECURE_USER_ADMIN = "Delete "+DataTables.SECURE_USER_ROLE+" WHERE auth_user_uid= ? ";
		try{
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(secureUserId);

			preparedStmtMethod(null, paramList,
					DELETE_SECURE_USER_ADMIN,
					NEDSSConstants.UPDATE);

		}catch(Exception e){
			logger.fatal("deleteSecureUserRole.Exception while deleting AUTH_USER_ROLE fir AUTH_USER_ROLE_uid=\n "+secureUserId+"\n The delete query is "+DELETE_SECURE_USER_ADMIN+"\n SQLException="+e.getMessage(), e);
			logger.error("Error while deleting the SECURE_USER_ROLE Mapping table " + e.getMessage());
			throw new NEDSSSystemException( "deleteSecureUserRole.Exception Thrown: \n"+e);
		}
	}


	/**
	 * Updating the ProgramAreaAdmin
	 * @param authProgAreaAdminDT
	 * @return
	 * @throws NEDSSSystemException
	 */
	public  AuthProgAreaAdminDT updateProgramAreaAdminDT(AuthProgAreaAdminDT authProgAreaAdminDT)
			throws NEDSSSystemException{

		logger.debug("Adding a new Program Area Admin record.");
		/**
		 * Inserting a new secure_program_area_admin rec
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;


		String UPDATE_NEW_PROGRAM_AREA_ADMINISTRATOR = "Update " +DataTables.SECURE_PROGRAM_AREA_ADMIN+
				"  SET  PROG_AREA_CD  = ?, AUTH_USER_IND  = ?, ADD_TIME  = ?, ADD_USER_ID  = ?, LAST_CHG_TIME  = ?, "
				+"LAST_CHG_USER_ID  = ?, RECORD_STATUS_CD  = ?, RECORD_STATUS_TIME  = ? "
				+" WHERE AUTH_PROG_AREA_ADMIN_UID=?";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_NEW_PROGRAM_AREA_ADMINISTRATOR);
			preparedStmt.setString(i++,authProgAreaAdminDT.getProgAreaCd());	 //1
			preparedStmt.setString(i++,authProgAreaAdminDT.getAuthUserInd());	 //2
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getAddTime()); 	// 3
			preparedStmt.setLong(i++, authProgAreaAdminDT.getAddUserId().longValue());//4
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getLastChgTime()); 	// 5
			preparedStmt.setLong(i++, authProgAreaAdminDT.getLastChgUserId().longValue());//6
			preparedStmt.setString(i++,authProgAreaAdminDT.getRecordStatusCd());	 //7
			preparedStmt.setTimestamp(i++, authProgAreaAdminDT.getRecordStatusTime()); 	// 8
			preparedStmt.setLong(i++, authProgAreaAdminDT.getAuthProgAreaAdminUid()); 	// 9



			resultCount = preparedStmt.executeUpdate();
			logger.debug("done updating program area admin");
			logger.debug("resultCount updating new prog area admin count is " + resultCount);					

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating " +
					"a  entry in secure program Area Admin table: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("updateProgramAreaAdminDT DT is "+authProgAreaAdminDT.toString());
			logger.fatal("updateProgramAreaAdminDT resultCount is "+resultCount);
			logger.fatal("updateProgramAreaAdminDT.SQLException while getting Users=\n "+UPDATE_NEW_PROGRAM_AREA_ADMINISTRATOR+"\n authProgAreaAdminDT is "+authProgAreaAdminDT.toString()+"\n SQLException="+sqlex);
			throw new NEDSSSystemException( "deleteSecureUserRole.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating into Secure_program_area_admin TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("updateProgramAreaAdminDT DT is "+authProgAreaAdminDT.toString());
			logger.fatal("updateProgramAreaAdminDT resultCount is "+resultCount);
			logger.fatal("updateProgramAreaAdminDT.Exception while getting Users=\n "+UPDATE_NEW_PROGRAM_AREA_ADMINISTRATOR+"\n authProgAreaAdminDT is "+authProgAreaAdminDT.toString()+"\n SQLException="+ex);
			throw new NEDSSSystemException( "deleteSecureUserRole.Exception Thrown: \n"+ex);
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}					
		return(authProgAreaAdminDT);
	}

	public  AuthUserRoleDT updateSecureUserRoleDT(AuthUserRoleDT authUserRoleDT)
			throws NEDSSSystemException{

		logger.debug("Updating a new user role to a permission set in a jurisdiction, program area to a user.");
		/**
		 * Inserting a new realized role
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  secureUserRoleUid = null;

		String UPDATE_SECURE_USER_ROLE = "UPDATE INTO " +DataTables.SECURE_USER_ROLE+
				"(SET AUTH_ROLE_NM = ?,PROG_AREA_CD = ?,JURISDICTION_CD = ?,AUTH_USER_UID = ?,AUTH_PERM_SET_UID = ?,ROLE_GUEST_IND = ?,READ_ONLY_IND = ?,"
				+",DISP_SEQ_NBR = ?,ADD_TIME = ?,ADD_USER_ID = ?,LAST_CHG_TIME = ?,LAST_CHG_USER_ID = ?,RECORD_STATUS_CD = ?,RECORD_STATUS_TIME = ?";

		//todo: Compute oid using call to pajHashList()
		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_SECURE_USER_ROLE);
			preparedStmt.setString(i++,authUserRoleDT.getAuthRoleNm());	        //1
			preparedStmt.setString(i++,authUserRoleDT.getProgAreaCd()); //2
			preparedStmt.setString(i++,authUserRoleDT.getJurisdictionCd());      //3
			preparedStmt.setLong(i++,authUserRoleDT.getAuthUserUid());     //4
			if (authUserRoleDT.getAuthPermSetUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, authUserRoleDT.getAuthPermSetUid().longValue());  //5
			preparedStmt.setString(i++,authUserRoleDT.getRoleGuestInd()); 	//6
			preparedStmt.setString(i++,authUserRoleDT.getReadOnlyInd()); 		//7
			preparedStmt.setInt(i++, authUserRoleDT.getDispSeqNbr().intValue());  //8
			preparedStmt.setTimestamp(i++, authUserRoleDT.getAddTime());                   //9
			preparedStmt.setLong(i++, authUserRoleDT.getAddUserId().longValue());			   //10
			preparedStmt.setTimestamp(i++, authUserRoleDT.getLastChgTime());                   //11
			preparedStmt.setLong(i++, authUserRoleDT.getLastChgUserId().longValue());			   //12
			preparedStmt.setString(i++, authUserRoleDT.getRecordStatusCd());                   //13
			preparedStmt.setTimestamp(i++, authUserRoleDT.getRecordStatusTime());			   //14

			resultCount = preparedStmt.executeUpdate();
			logger.debug("done updating secure user role");
			logger.debug("resultCount updating new user role count is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating " +
					"a new entry in secure_user_role: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("secureUserRole DT is "+authUserRoleDT.toString());
			logger.fatal("secureUserRole resultCount is "+resultCount);
			logger.fatal("updateSecureUserRoleDT.SQLException while updating Users Role=\n "+UPDATE_SECURE_USER_ROLE+"\n authUserRoleDT is "+authUserRoleDT.toString()+"\n SQLException="+sqlex);

			throw new NEDSSSystemException( "updateSecureUserRoleDT.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating into Secure_user_role TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("authUserRoleDT DT is "+authUserRoleDT.toString());
			logger.fatal("secureUserRole resultCount is "+resultCount);
			logger.fatal("updateSecureUserRoleDT.SQLException while updating Users Role=\n "+UPDATE_SECURE_USER_ROLE+"\n authUserRoleDT is "+authUserRoleDT.toString()+"\n SQLException="+ex);
			throw new NEDSSSystemException( "updateSecureUserRoleDT.Exception Thrown: \n"+ex);
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		//set the new uid
		authUserRoleDT.setAuthUserRoleUid(secureUserRoleUid);
		return(authUserRoleDT);
	}


	public  AuthBusObjRtDT updateAuthBusObjRightDT(AuthBusObjRtDT authBusObjRtDT)
			throws NEDSSSystemException{

		logger.debug("updatig new permission set into the secure_business_object_right table.");
		/**
		 * Inserting a new access right to a business object
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rs2 = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;

		String UPDATE_SECURE_BUSINESS_OBJECT_RIGHT = "UPDATE " +DataTables.SECURE_BUSINESS_OBJECT_RIGHT+
				" SET AUTH_PERM_SET_UID =  ?, AUTH_BUS_OBJ_TYPE_UID = ?, ADD_TIME = ?"
				+",ADD_USER_ID = ?,LAST_CHG_TIME = ?,LAST_CHG_USER_ID = ?,RECORD_STATUS_CD = ?,RECORD_STATUS_TIME = ?"
				+" WHERE AUTH_BUS_OBJ_RT_UID = ?";

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_SECURE_BUSINESS_OBJECT_RIGHT);
			preparedStmt.setLong(i++,authBusObjRtDT.getAuthPermSetUid());		 //1
			preparedStmt.setLong(i++,authBusObjRtDT.getAuthBusObjTypeUid());	 //2
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getAddTime()); // 3
			preparedStmt.setLong(i++, authBusObjRtDT.getAddUserId().longValue());//4
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getLastChgTime()); 	// 5
			preparedStmt.setLong(i++, authBusObjRtDT.getLastChgUserId().longValue());//6
			preparedStmt.setString(i++, authBusObjRtDT.getRecordStatusCd());//7
			preparedStmt.setTimestamp(i++, authBusObjRtDT.getRecordStatusTime()); // 8
			preparedStmt.setLong(i++,authBusObjRtDT.getAuthBusObjRtUid());		 //9



			resultCount = preparedStmt.executeUpdate();
			logger.debug("done updating business object access right");
			logger.debug("resultCount updating new bus obj right count is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating " +
					"a new business object to a permission set: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("secureBusinessObjectRight DT is "+authBusObjRtDT.toString());
			logger.fatal("secureBusinessObjectRight resultCount is "+resultCount);
			logger.fatal("updateSecureUserRoleDT.SQLException while updating AUTH_BUS_OBJ_RT=\n "+UPDATE_SECURE_BUSINESS_OBJECT_RIGHT+"\n authBusObjRtDT is "+authBusObjRtDT.toString()+"\n SQLException="+sqlex);
			throw new NEDSSSystemException( "updateAuthBusObjRightDT.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating  into Secure_business_object_right TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("secureBusinessObjectRight DT is "+authBusObjRtDT.toString());
			logger.fatal("secureBusinessObjectRight resultCount is "+resultCount);
			logger.fatal("updateSecureUserRoleDT.SQLException while updating AUTH_BUS_OBJ_RT=\n "+UPDATE_SECURE_BUSINESS_OBJECT_RIGHT+"\n authBusObjRtDT is "+authBusObjRtDT.toString()+"\n SQLException="+ex);
			throw new NEDSSSystemException( "updateAuthBusObjRightDT.Exception Thrown: \n"+ex);
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeResultSet (rs2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		return(authBusObjRtDT);
	}
	/**
	 * Deleting the BusinessOperation Right from the table 
	 * @param secureUserId
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteSecureBusinessOperationRight(Long objRightUid)throws NEDSSDAOSysException, NEDSSSystemException {
		String DELETE_SECURE_BUS_OPER_RIGHT = "Delete "+DataTables.SECURE_BUSINESS_OPERATION_RIGHT+" WHERE AUTH_BUS_OBJ_RT_UID= ? ";
		try{
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(objRightUid);

			preparedStmtMethod(null, paramList,
					DELETE_SECURE_BUS_OPER_RIGHT,
					NEDSSConstants.UPDATE);

		}catch(Exception e){
			logger.fatal("deleteSecureBusinessOperationRight.ERROR:deleteSecureBusinessOperationRight.while deleting from the SECURE_BUSINESS_OPERATION_RIGHT  table(Long objRightUid)="+objRightUid);
			logger.fatal("deleteSecureBusinessOperationRight.ERROR: while deleting from the SECURE_BUSINESS_OPERATION_RIGHT  table " + e.getMessage(), e);
			logger.fatal("deleteSecureBusinessOperationRight.Exception while deleting AUTH_BUS_OP_RT with query=\n "+DELETE_SECURE_BUS_OPER_RIGHT+"\n for objRightUid is "+objRightUid+"\n SQLException="+e);
			throw new NEDSSSystemException( "deleteSecureBusinessOperationRight.Exception Thrown: \n"+e);
		}
	}
public void deleteSecureBusinessOperationRightWithPermSetUid(Long authPermSetUid)throws NEDSSDAOSysException, NEDSSSystemException {
		String DELETE_SECURE_BUS_OPER_RIGHT = "Delete "+DataTables.SECURE_BUSINESS_OPERATION_RIGHT+" WHERE AUTH_BUS_OBJ_RT_UID in (select AUTH_BUS_OBJ_RT_UID from AUTH_BUS_OBJ_RT where AUTH_PERM_SET_UID= ?) ";
		try{
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(authPermSetUid);

			preparedStmtMethod(null, paramList,
					DELETE_SECURE_BUS_OPER_RIGHT,
					NEDSSConstants.UPDATE);

		}catch(Exception e){
			logger.fatal("deleteSecureBusinessOperationRight.ERROR:deleteSecureBusinessOperationRight.while deleting from the SECURE_BUSINESS_OPERATION_RIGHT  table(Long permSetUid)="+authPermSetUid);
			logger.fatal("deleteSecureBusinessOperationRight.ERROR: while deleting from the SECURE_BUSINESS_OPERATION_RIGHT  table " + e.getMessage(), e);
			logger.fatal("deleteSecureBusinessOperationRight.Exception while deleting AUTH_BUS_OP_RT with query=\n "+DELETE_SECURE_BUS_OPER_RIGHT+"\n for permSetUid is "+authPermSetUid+"\n SQLException="+e);
			throw new NEDSSSystemException( "deleteSecureBusinessOperationRight.Exception Thrown: \n"+e);
		}
	}
	
	public void deleteSecureBusinessObjectRightWithPermSetUid(Long authPermSetUid)throws NEDSSDAOSysException, NEDSSSystemException {
		String DELETE_SECURE_BUS_OBJ_RIGHT = "Delete "+DataTables.SECURE_BUSINESS_OBJECT_RIGHT+" WHERE AUTH_PERM_SET_UID = ? ";
		try{
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(authPermSetUid);

			preparedStmtMethod(null, paramList,
					DELETE_SECURE_BUS_OBJ_RIGHT,
					NEDSSConstants.UPDATE);

		}catch(Exception e){
			logger.fatal("deleteSecureBusinessOperationRight.Exception while deleting SECURE_BUSINESS_OBJECT_RIGHT with query=\n "+DELETE_SECURE_BUS_OBJ_RIGHT+"\n for permSetUid is "+authPermSetUid+"\n SQLException="+e.getMessage(), e);
			throw new NEDSSSystemException( "deleteSecureBusinessOperationRight.Exception Thrown: \n"+e);
		}
	}
	
	
	public boolean doesDBUserExist()throws NEDSSSystemException
	{
		logger.debug("doesDBUserExist:Does any user exists in database already?");
		boolean doesDbUserExists = false;

		//Note that the business object DT has some fields from the type table
		String SELECT_SECURE_USER = "SELECT count(*)  FROM " + DataTables.SECURE_USER ;

		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for DbAuthDAOImpl.doesDBUserExist: ", nedssEx);
			throw new NEDSSSystemException( "doesDBUserExist.NEDSSSystemException Thrown: \n"+nedssEx);
		}
		try {
			stmt = dbConnection.createStatement();
			resultSet = stmt.executeQuery(SELECT_SECURE_USER);
			if (resultSet != null)
				if (resultSet.next()) {
					logger.debug("Secure_user Found = " + resultSet.getLong(1));
					int numberOfUsers=resultSet.getInt(1);
					logger.debug("There exisits "+ numberOfUsers +" users in database.");
					if(numberOfUsers>0){
						doesDbUserExists=true;
					}
				}
		} catch (SQLException sqlEx) {
			logger.fatal("doesDBUserExist.SQLException while checking user exists=\n "+SELECT_SECURE_USER+"\n SQLException="+sqlEx.getMessage());
			logger.fatal("doesDBUserExist.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.doesDBUserExist.",sqlEx);
			throw new NEDSSSystemException( "doesUserExist.SQLException Thrown: \n"+sqlEx);
		} catch (Exception ex) {
			logger.fatal("doesDBUserExist.SQLException while checking user exists=\n "+SELECT_SECURE_USER+"\n SQLException="+ex.getMessage());
			logger.fatal("doesDBUserExist.Error in result set handling while in Select Business Object Types List: DbAuthDAOImpl.doesDBUserExist.",ex);
			logger.fatal("General error selecting secure_user: DbAuthDAOImpl.doesDBUserExist - ",ex.getMessage());
			throw new NEDSSSystemException( "doesUserExist.Exception Thrown: \n"+ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returning the flag for DBUSerExsits"+doesDbUserExists);
		return doesDbUserExists;
	}
	
	
	public int updateUserIdWithNewKey(String newKey)throws NEDSSSystemException{

		String SELECT_USERS = "SELECT auth_user_uid \"authUserUid\",user_id \"userId\" from " + DataTables.SECURE_USER;			
		String UPDATE_SECURE_USER = "UPDATE " +DataTables.SECURE_USER+" SET USER_ID = ? WHERE USER_ID = ? AND AUTH_USER_UID=?";
		int resultCount =0;
		PreparedStatement preparedStmt2= null;
		Connection dbConnection = null;
		
		int usersUpdated;
		try {
			usersUpdated = 0;
			ArrayList<Object> usersDTList = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();

			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nedssEx) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for DbAuthDAOImpl.updateUserIdWithNewKey: ", nedssEx);
				throw new NEDSSSystemException( "updateUserIdWithNewKey.NEDSSSystemException Thrown: \n"+nedssEx);
			}
			try {
				preparedStmt = dbConnection.prepareStatement(SELECT_USERS);

				resultSet = preparedStmt.executeQuery();
				usersDTList = new ArrayList<Object> ();
				if (resultSet != null) {
					resultSetMetaData = resultSet.getMetaData();
					usersDTList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
							resultSet, resultSetMetaData, AuthUserDT.class,
							usersDTList);
					logger.debug("returned with  users  Lists");
				}
			}catch (SQLException sqlEx) {
				logger.fatal("updateUserIdWithNewKey.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+sqlEx.getMessage());
				logger.fatal("updateUserIdWithNewKey.Error in result set handling while Permission sets.",sqlEx);
				throw new NEDSSSystemException( "getSecureUserDTList.SQLException Thrown: \n"+sqlEx);
			} catch (ResultSetUtilsException resultSetUtilEx) {
				logger.fatal("updateUserIdWithNewKey.SQLException while getting Users=\n "+SELECT_USERS+"\n SQLException="+resultSetUtilEx.getMessage());
				logger.fatal("updateUserIdWithNewKey.Error in result set handling while Permission sets.",resultSetUtilEx);
				logger.fatal("Error in result set handling while selecting Distinct Users List: DbAuthDAOImpl.updateUserIdWithNewKey");
				throw new NEDSSSystemException( "updateUserIdWithNewKey.ResultSetUtilsException Thrown: \n"+resultSetUtilEx);
			}finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

				
			if(usersDTList!=null && usersDTList.size()>0){
				try {
					dbConnection = getConnection();
				} catch (NEDSSSystemException nedssEx) {
					logger.fatal("SQLException while obtaining database connection "
							+ "for DbAuthDAOImpl.updateUserIdWithNewKey after obtaining usersDTList: ", nedssEx);
					throw new NEDSSSystemException( "updateUserIdWithNewKey.NEDSSSystemException Thrown: \n"+nedssEx);
				}
				Iterator<Object> it  = usersDTList.iterator();
				while(it.hasNext()){
					AuthUserDT authUserDT = (AuthUserDT)it.next();
					
					try
					{
						String userId =EncryptorDecryptor.decrypt(authUserDT.getUserId());
						int i = 1;
						preparedStmt2 = dbConnection.prepareStatement(UPDATE_SECURE_USER);
						preparedStmt2.setString(i++,EncryptorDecryptor.encryptWithNewKey(userId));//1
						preparedStmt2.setString(i++,authUserDT.getUserId());		 //2
						preparedStmt2.setLong(i++,authUserDT.getAuthUserUid());//3


						resultCount = preparedStmt2.executeUpdate();
						logger.debug("done updating the user < "+authUserDT.getAuthUserUid() + ">");	 
						usersUpdated++;
						logger.debug("Number of users already updated with new Encryption key "+usersUpdated);

					}
					catch(SQLException sqlex)
					{
						logger.fatal("updateUserIdWithNewKey.SQLException and resultCount is "+resultCount);
						logger.fatal("SQLException while updating " +"a new entry in secure_user: \n"+ sqlex.getMessage(),sqlex);
						logger.fatal("updateUserIdWithNewKey.SQLException while getting Users=\n "+UPDATE_SECURE_USER+"\n authUserDT is "+authUserDT.toString()+"\n SQLException="+sqlex);
						throw new NEDSSSystemException( "updateUserIdWithNewKey.SQLException Thrown: \n"+sqlex);
					}
					catch(Exception ex)
					{
						logger.fatal("updateUserIdWithNewKey.SQLException and resultCount is "+resultCount);
						logger.fatal("Error while updating into Secure_user TABLE, exception = " + ex.getMessage(), ex);
						logger.fatal("authUserDT DT is "+authUserDT.toString());
						logger.fatal("updateUserIdWithNewKey.SQLException while getting Users=\n "+UPDATE_SECURE_USER+"\n authUserDT is "+authUserDT.toString()+"\n SQLException="+ex);
						throw new NEDSSSystemException( "updateUserIdWithNewKey.Exception Thrown: \n"+ex);
					}
				}
				EncryptorDecryptor.updateEncryptionKey();
			}
		} catch(Exception ex)
		{
			logger.fatal("updateUserIdWithNewKey.SQLException and resultCount is "+resultCount);
			logger.fatal("Error while updating into Secure_user TABLE, exception = " + ex.getMessage());
			throw new NEDSSSystemException( "updateUserIdWithNewKey.Exception Thrown: \n"+ex);
		}finally
		{
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
			return usersUpdated;
	}

	public void updateSecurityLog(AuthUserDT authUserDT) {
		logger.debug("Updating new user record into the secure_user table.");
		/**
		 * Inserting a new user
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount =0;
		String UPDATE_SECURE_USER_LOG="UPDATE " +DataTables.SECURE_Security_log+" SET user_id = ?, nedss_entry_id = ? ,first_nm = ? ,last_nm = ? WHERE user_id =?";
		

		int i = 1;
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_SECURE_USER_LOG);
			preparedStmt.setString(i++,null);//1
			preparedStmt.setLong(i++, authUserDT.getNedssEntryId().longValue()); // 2
			preparedStmt.setString(i++, authUserDT.getUserFirstNm()); // 3
			preparedStmt.setString(i++, authUserDT.getUserLastNm()); // 4
			preparedStmt.setString(i++,authUserDT.getUserId());//5
			resultCount = preparedStmt.executeUpdate();
			logger.debug("\nupdated "+resultCount+" row(s) in the security_log for user < "+authUserDT.getNedssEntryId() + ">");	 

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while updating " +
					"a new entry in secure_user: \n"+sqlex.getMessage(),sqlex);
			logger.fatal("updateSecurityLog.SQLException while getting Users=\n "+UPDATE_SECURE_USER_LOG+"\n authUserDT is "+authUserDT.toString()+"\n SQLException="+sqlex);
			throw new NEDSSSystemException( "updateSecurityLog.SQLException Thrown: \n"+sqlex);
		}
		catch(Exception ex)
		{
			logger.fatal("Error while updating into Secure_user TABLE, exception = " + ex.getMessage(),ex);
			logger.fatal("authUserDT DT is "+authUserDT.toString());
			logger.fatal("updateSecureUserDT.SQLException while getting Users=\n "+UPDATE_SECURE_USER_LOG+"\n authUserDT is "+authUserDT.toString()+"\n Exception="+ex);
			logger.fatal("authUserDT resultCount is "+resultCount);
			throw new NEDSSSystemException( "updateSecureUserDT.Exception Thrown: \n"+ex);
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

}
