package gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao;
/**
 * <p>Title: UpdatedNotificationDAOImpl</p>
 * <p>Description: This class performs database operations for table UPDATED_NOTIFICATION</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Sree Chadalavada
 * @version 1.1.3 SP2
 * TODO: remove references to nbs_odse when changes are made to ORACLE instance 
 */

import gov.cdc.nedss.act.notification.dt.UpdatedNotificationDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class UpdatedNotificationDAOImpl extends DAOBase {
	
	// Instantiate the logger
	
	static final LogUtils logger =
		new LogUtils((UpdatedNotificationDAOImpl.class).getName());
	
	// SQL statement for insert in table UPDATED_NOTIFICATION
	public static final String INSERT_UPDATED_NOTIFICATION=
		"insert into updated_notification"
			+ " (notification_uid, "
			+ " case_status_chg_ind," 
			+ " case_class_cd, "
			+ " add_user_id ,"
			+ " add_time, version_ctrl_nbr, status_cd) values"
			+ "(?,?,?,?,?,?, 'A')";

	// SQL statement to retrieve the greatest version_ctrl_nbr 
	
	public static final String SELECT_MAX_VERSION_CTRL_NBR_BY_NOTIFICATION_UID=
		"select max(version_ctrl_nbr) \"versionCtrlNbr\" from " +
		"updated_notification where notification_uid=?";

	// SQL statement for update in table UPDATED_NOTIFICATION
	public static final String UPDATE_UPDATED_NOTIFICATION=
		"update updated_notification set"
			+ " last_chg_user_id = ?, "
			+ " last_chg_time = ?, " 
			+ "status_cd = ?, " 
			+ "status_time=? where "
			+ "notification_uid=? and version_ctrl_nbr =?";

	// SQL statement for select in table UPDATED_NOTIFICATION for SQL
	public static final String UPDATED_NOTIFICATIONS_FOR_AUDIT_SQL =
		"select UpdatedNotification.version_ctrl_nbr \"versionCtrlNbr\", " + 
		"UpdatedNotification.notification_uid \"notificationUid\", " +
		"UpdatedNotification.add_user_id \"addUserId\", " +
		"UpdatedNotification.add_time \"addTime\", " +
		"UpdatedNotification.case_status_chg_ind \"caseStatusChg\", " +
		
		"Notification.cd \"notificationCd\", " +
		"Export_receiving_facility.receiving_system_nm \"recipient\", " +
		"code1.nnd_ind \"nndInd\" , " +    
		"Public_health_case.cd \"cd\", " +
		"UpdatedNotification.case_class_cd \"caseClassCd\", " +
		"Public_health_case.jurisdiction_cd \"jurisdictionCd\", " +
		"Public_health_case.shared_ind \"sharedInd\", " +
		"Public_health_case.prog_area_cd \"progAreaCd\", " +
		"code3.code_short_desc_txt \"jurisdictionCdTxt\" , " +
		"Public_health_case.public_health_case_uid \"publicHealthCaseUid\", " +
		"per.last_nm \"lastNm\", " +
		"per.first_nm \"firstNm\" " +
		"from nbs_odse..updated_notification UpdatedNotification with (nolock), " +
		 DataTables.NOTIFICATION_TABLE + " Notification  with (nolock)" +
		"INNER JOIN " + DataTables.ACT_RELATIONSHIP + " act with (nolock)" +
		"ON act.source_act_uid = Notification.notification_uid  " +
		"INNER JOIN " + DataTables.PUBLIC_HEALTH_CASE_TABLE +
		" Public_health_case with (nolock)" +
		"ON Public_health_case.public_health_case_uid = act.target_act_uid " +
		"INNER JOIN " + DataTables.PARTICIPATION_TABLE + " par with (nolock)" +
		"ON par.act_uid = Public_health_case.public_health_case_uid " +
		"INNER JOIN " + DataTables.PERSON_TABLE + " per with (nolock)" +
		"ON per.person_uid = par.subject_entity_uid " +
		
		"LEFT OUTER JOIN Export_receiving_facility with (nolock)" +
		"ON Export_receiving_facility.Export_receiving_facility_uid = Notification.Export_receiving_facility_uid " +
		
		"LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
		"..condition_code code1 with (nolock)" +
		"ON Public_health_case.cd = code1.condition_cd " +
		"LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
		"..code_value_general code2 with (nolock)" +
		"ON code2.code_set_nm = 'PHC_CLASS' "+
		" and Public_health_case.case_class_cd = code2.code " +
		"LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
		"..Jurisdiction_code code3 with (nolock)" +
		"ON Public_health_case.jurisdiction_cd = code3.code " +
		"where act.type_cd = '" + NEDSSConstants.ACT106_TYP_CD + "' " +
        "AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "' " +
		"AND par.type_cd = '" + NEDSSConstants.PHC_PATIENT + "' " +
		" and Notification.notification_uid = UpdatedNotification.notification_uid AND " +
		"UpdatedNotification.status_cd='A'";

	// SQL statement for order by clause for both select statements
	public static final String order_by_statement = 
		" order by UpdatedNotification.case_status_chg_ind DESC, UpdatedNotification.case_class_cd, UpdatedNotification.add_time DESC";

	// SQL statement for select in table UPDATED_NOTIFICATION for ORACLE

	//SQL statement for select by notification_uid
	public static final String SELECT_UPDATED_NOTIFICATION_BY_NOTIFICATION_UID=
		"select notification_uid \"notificationUid\", " +
		"case_status_chg_ind \"caseStatusChg\", " +
		"add_user_id \"addUserId\", " +
		"add_time \"addTime\" " +
		" from updated_notification where notification_uid=?";

	/**
	 * Dummy constructor for UpdatedNotificationDAOImpl
	 */ 
	public UpdatedNotificationDAOImpl() {
	}


	/**
	 * Selects all notifications accessible by the user (security applied)
	 * 
	 * @param nbsSecurityObj Security object for user
	 * @return Notification Summary VO ArrayList
	 * @throws NEDSSSystemException exception while executing SQL
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object>  selectAllActiveUpdatedNotifications(
		NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{

			ArrayList<Object>  arrayList = new ArrayList<Object> ();

			try {
				//Check for permissions
				if (!nbsSecurityObj.getPermission(
						NBSBOLookup.NOTIFICATION,
						NBSOperationLookup.REVIEW,
						ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
						ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
					logger.error(
						"getUpdatedNotifications- NO PERMISSIONS FOR GET NOTIFICATIONS FOR REVIEW");
					throw new NEDSSSystemException("NO PERMISSIONS FOR GET NOTIFICATIONS FOR REVIEW");
				}

				// Apply security profile
				String dataAccessWhereClause =
					nbsSecurityObj.getDataAccessWhereClause(
						NBSBOLookup.NOTIFICATION,
						NBSOperationLookup.REVIEW,
						" Notification ");

				// Concatenate where clause
				if (dataAccessWhereClause == null)
					dataAccessWhereClause = "";
				else
					dataAccessWhereClause = " AND " + dataAccessWhereClause;

				String selectstatement = null;
				
				//Prepare the SQL statement for execution
				selectstatement =
						UPDATED_NOTIFICATIONS_FOR_AUDIT_SQL
							+ dataAccessWhereClause + order_by_statement;
			
				UpdatedNotificationSummaryVO vo = 
					new UpdatedNotificationSummaryVO();
					
				//Execute the SQL	
				arrayList =
					(ArrayList<Object> ) preparedStmtMethod(vo,
						arrayList,
						selectstatement,
						NEDSSConstants.SELECT);
						
				CachedDropDownValues cache = new CachedDropDownValues();
				TreeMap<?,?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
				mapPhcClass = cache.reverseMap(mapPhcClass);
				
				TreeMap<Object, Object> mapConditionCode = cache.getConditionCodes();
				TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();

				UpdatedNotificationSummaryVO updatedNotificationSummaryVO = null;
				
				// Store the code description text values for the codes
				// This is applied to Jurisdiction Codes, Condition Codes 
				// and Case Status Codes
				
				for (Iterator<Object> anIterator = arrayList.iterator();
					anIterator.hasNext();) {
					updatedNotificationSummaryVO=
						(UpdatedNotificationSummaryVO) anIterator.next();
					if (updatedNotificationSummaryVO.getCd() != null
						&& updatedNotificationSummaryVO.getCd().trim().length() != 0) {
							updatedNotificationSummaryVO.setCdTxt(
							((String) mapConditionCode
								.get(updatedNotificationSummaryVO.getCd())));
					}
					if (updatedNotificationSummaryVO.getCaseClassCd() != null
						&& updatedNotificationSummaryVO.getCaseClassCd().trim().length()
							!= 0) {
								updatedNotificationSummaryVO.setCaseClassCdTxt(
									(String) mapPhcClass.get(
									updatedNotificationSummaryVO.getCaseClassCd()));
					}
					if (updatedNotificationSummaryVO.getJurisdictionCd() != null
						&& updatedNotificationSummaryVO.getJurisdictionCd().trim().length()
							!= 0) {
								updatedNotificationSummaryVO.setJurisdictionCdTxt(
									(String) mapJurisdiction.get(
									updatedNotificationSummaryVO.getJurisdictionCd()));
					}
					
					// replace the notification code with description text for it.
					if (updatedNotificationSummaryVO.getNotificationCd() != null
							&& updatedNotificationSummaryVO.getNotificationCd().trim().length() != 0)
					{
						updatedNotificationSummaryVO.setNotificationCd(
								CachedDropDowns.getCodeDescTxtForCd(updatedNotificationSummaryVO.getNotificationCd(),
										"NBS_DOC_PURPOSE"));
					}
				}
			} catch (Exception ex) {
				logger.fatal("Exception in personExists():  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		// Return the result set
		return arrayList;
	}


	/**
	 * Select the Max Version Nbr for notification_uid
	 * @param nbsSecurityObj Security Object
	 * @return Integer versionCtrlNbr
	 * @throws NEDSSSystemException exception while executing SQL
	 */
	@SuppressWarnings("unchecked")
	public Integer selectMaxVersionCtrlNbrByNotificationUid(Long notificationUid) 
		throws NEDSSSystemException{
			
			ArrayList<Object>  arrayList = new ArrayList<Object> ();
			UpdatedNotificationDT dt = new UpdatedNotificationDT();

			Integer versionCtrlNbr = new Integer(0);
			try {

				ArrayList<Object>  parameterList = new ArrayList<Object> ();

				// Add parameters for SQL Statement 
				parameterList.add(notificationUid);

				// Execute the SQL
				arrayList =
					(ArrayList<Object> ) preparedStmtMethod(dt,
						parameterList,
						SELECT_MAX_VERSION_CTRL_NBR_BY_NOTIFICATION_UID,
						NEDSSConstants.SELECT);
						
				Iterator<Object> i = arrayList.iterator();
				
				// Retrieve the UpdatedNotificationDT object
				while(i.hasNext()){
					dt = (UpdatedNotificationDT)i.next(); 
				}
			} catch (Exception ex) {
				logger.fatal("Exception in personExists():  ERROR = " + ex.getMessage() ,ex);
				throw new NEDSSSystemException(ex.toString());
			}

		 	if((dt.getVersionCtrlNbr() != null) &&
		 		(dt.getVersionCtrlNbr().intValue() != 0)){
		 		
		 		versionCtrlNbr = dt.getVersionCtrlNbr();
		 	}
		 	
		 	return versionCtrlNbr;
	}


	/**
	 * Select the Updated Notification by Notification Id
	 * @param nbsSecurityObj Security Object
	 * @return UpdatedNotification object
	 * @throws NEDSSSystemException exception while executing SQL
	 */
	@SuppressWarnings("unchecked")
	public UpdatedNotificationDT selectUpdatedNotificationByNotificationUid(
		NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
			
			ArrayList<Object>  arrayList = new ArrayList<Object> ();
			UpdatedNotificationDT dt = new UpdatedNotificationDT();

			try {
				// Execute the SQL
				arrayList =
					(ArrayList<Object> ) preparedStmtMethod(dt,
						arrayList,
						SELECT_UPDATED_NOTIFICATION_BY_NOTIFICATION_UID,
						NEDSSConstants.SELECT);
						
				Iterator<Object> i = arrayList.iterator();
				
				// Retrieve the UpdatedNotificationDT object
				while(i.hasNext()){
					dt = (UpdatedNotificationDT)i.next(); 
				}
			} catch (Exception ex) {
				logger.fatal("Exception in personExists():  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			
		// Return the dt object
		return dt;
	}

	/**
	 * Update the status for UpdatedNotification
	 * @param updatedNotificationDT UpdatedNotificationDT object
	 * @return update count
	 * @throws NEDSSSystemException
	 */
	public Long updateUpdatedNotification(UpdatedNotificationDT 
		updatedNotificationDT) throws NEDSSSystemException{
			
		Integer resultCount = null;
		
		try {
			ArrayList<Object>  parameterList = new ArrayList<Object> ();

			// Add parameters for SQL Statement 
			parameterList.add(updatedNotificationDT.getLastChgUserId());
			parameterList.add(updatedNotificationDT.getLastChgTime());
			parameterList.add(new String("I"));
			parameterList.add(updatedNotificationDT.getLastChgTime());
			parameterList.add(updatedNotificationDT.getNotificationUid());
			parameterList.add(updatedNotificationDT.getVersionCtrlNbr());

			// Execute the SQL statement
			
			resultCount =
				(Integer) preparedStmtMethod(updatedNotificationDT,
					parameterList,
					UPDATE_UPDATED_NOTIFICATION,
					NEDSSConstants.UPDATE);
			
			// Check for the update count
			 
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException(
					"update failed for UPDATED_NOTIFICATION");
		} catch (Exception ex) {
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		// Return a valid update count
		return new Long(1);
	}


	/**
	 * Insert a row into UpdatedNotification Table
	 * 
	 * @param updatedNotificationDT UpdatedNotificationDT
	 * @return insert count
	 * @throws NEDSSSystemException
	 */
	public Long insertUpdatedNotification(UpdatedNotificationDT 
		updatedNotificationDT) throws NEDSSSystemException{
			
		Integer resultCount = null;
		try {
			
			
			Integer versionCtrlNbr = selectMaxVersionCtrlNbrByNotificationUid(
				updatedNotificationDT.getNotificationUid());
			
			ArrayList<Object>  parameterList = new ArrayList<Object> ();

			// Add the parameters for SQL
			parameterList.add(updatedNotificationDT.getNotificationUid());
			
			if(updatedNotificationDT.isCaseStatusChg()){
				parameterList.add(new String("Y"));				
			} else {
				parameterList.add(new String("N"));				
			}
			parameterList.add(updatedNotificationDT.getCaseClassCd());
			parameterList.add(updatedNotificationDT.getAddUserId());
			parameterList.add(updatedNotificationDT.getAddTime());
			parameterList.add(new Integer(versionCtrlNbr.intValue() + 1));

			// Execute the SQL
			resultCount =
				(Integer) preparedStmtMethod(updatedNotificationDT,
					parameterList,
					INSERT_UPDATED_NOTIFICATION,
					NEDSSConstants.UPDATE);
					
			// Check for the valid insert count
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException(
					"insert failed for UPDATED_NOTIFICATION");
		} catch (Exception ex) {
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		// Return the valid insert count
		return new Long(1);
	}
}

