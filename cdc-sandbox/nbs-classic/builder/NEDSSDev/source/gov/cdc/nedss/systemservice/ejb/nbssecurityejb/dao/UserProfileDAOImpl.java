package gov.cdc.nedss.systemservice.ejb.nbssecurityejb.dao;
/**
 * <p>Title: UserProfileDAOImpl</p>
 * <p>Description: This class performs database operations for table USER_PROFILE</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Sree Chadalavada
 * @version 1.0
 */

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.dt.UserProfileDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserProfileDAOImpl extends DAOBase {
	static final LogUtils logger =
		new LogUtils((UserProfileDAOImpl.class).getName());
		
	public static final String INSERT_USER_PROFILE =
		"insert into user_profile"
			+ " (nedss_entry_id, "
			+ " first_nm ,"
			+ " last_nm ,"
			+ " last_upd_time) "
			+ " values "
			+ "(?,?,?,?)";

	public static final String DELETE_ALL_ROWS_IN_USER_PROFILE=
		"delete from user_profile";

	public UserProfileDAOImpl() {
	}

	/*
	 * Inserts rows in table USER_PROFILE
	 * @param entryId 		entry id of the user
	 * @param firstName 	first name of the user
	 * @param lastName 		last name of the user
	 * @param nedssUserId 	NEDSS user id of the user
	 * @return number of rows affected
	 * @exception throws exception
	 */
	public Long insertUserProfile(String entryId, String firstName, 
		String lastName) throws Exception 
		{
		Integer resultCount = null;
		try {
			ArrayList<Object> parameterList = new ArrayList<Object> ();

			parameterList.add(entryId);
			parameterList.add(firstName);
			parameterList.add(lastName);
			
			parameterList.add(new Timestamp(System.currentTimeMillis()));

			UserProfileDT userProfileDT=
				new UserProfileDT();
			resultCount =
				(Integer) preparedStmtMethod(userProfileDT,
					parameterList,
					INSERT_USER_PROFILE,
					"INSERT");
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException("insert failed for User Profile");
		} catch (Exception ex) {
			logger.fatal("entryId: "+entryId+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return new Long(1);
	}

	/*
	 * Deletes all rows in table USER_PROFILE
	 * 
	 * @return number of rows affected
	 * @exception throws exception
	 */
	public Integer deleteUserProfiles() throws Exception 
		{

		Integer resultCount = null;
		try {
			ArrayList<Object> parameterList = new ArrayList<Object> ();

			UserProfileDT userProfileDT=
				new UserProfileDT();
			resultCount =
				(Integer) preparedStmtMethod(userProfileDT,
					parameterList,
					DELETE_ALL_ROWS_IN_USER_PROFILE,
					"DELETE");
		} catch (Exception ex) {
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return resultCount;
	}

}

