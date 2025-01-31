package gov.cdc.nedss.alert.ejb.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import javax.ejb.EJBException;


import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.alert.dt.AlertEmailMessageDT;
import gov.cdc.nedss.alert.dt.AlertLogDT;
import gov.cdc.nedss.alert.dt.AlertLogDetailDT;
import gov.cdc.nedss.alert.dt.AlertUserDT;
import gov.cdc.nedss.alert.dt.UserEmailAlertDT;
import gov.cdc.nedss.alert.dt.UserEmailDT;
import gov.cdc.nedss.alert.util.AlertStateConstant;
import gov.cdc.nedss.alert.vo.AlertEmailMessageVO;
import gov.cdc.nedss.alert.vo.AlertLogVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

public class AlertEmailMessageDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(AlertEmailMessageDAO.class.getName());
	
	private String SELECT_ALERT_MESSAGE = "SELECT alert_email_message_uid    \"alertEmailMessageUid\", aem.type_cd  \"typeCd\", type  \"type\", aem.severity_cd  \"severityCd\", severity  \"severity\", alr.alert_msg_txt \"alertMsgTxt\", simulated_alert  \"simulatedAlert\", aem.jurisdiction_cd  \"jurisdictionCd\", jurisdiction_description  \"jurisdictionDescription\", associated_condition_cd  \"associatedConditionCd\", associated_condition_desc  \"associatedConditionDesc\", event_add_time  \"eventAddTime\", alert_add_time  \"alertAddTime\", event_local_id  \"eventLocalId\", transmission_status  \"transmissionStatus\", aem.alert_uid  \"alertUid\" FROM Alert_Email_Message aem inner join Alert alr on alr.alert_uid = aem.alert_uid where transmission_status not in ('SENT', 'ERROR',  'QUEUED') "
		+" and simulated_alert = 'N'";
	private static final String QUEUED_ALERT_EMAIL_MESSAGE= "Update "+ DataTables.ALERT_EMAIL_MESSAGE + " SET transmission_status = '"+ AlertStateConstant.TRANSMISSION_QUEUED+"' where alert_email_message_uid = ?";
	private String INSERT_ALERT_MESSAGE="INSERT INTO  Alert_Email_Message(type_cd, type, severity_cd, severity, simulated_alert, jurisdiction_cd, jurisdiction_description, associated_condition_cd, associated_condition_desc, event_add_time, alert_add_time, event_local_id, transmission_status, alert_uid, prog_area_cd, prog_area_description, email_sent_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String INSERT_ALERT_LOG_DETAIL="INSERT INTO Alert_Log_Detail(alert_log_uid, alert_activity_detail_log) VALUES(?,?)";
	private String INSERT_ALERT_LOG="INSERT INTO Alert_Log(alert_uid,event_local_id,activity_log, add_time) VALUES(?,?,?,?)";
	private String INSERT_USER_EMAIL_ALERT="INSERT INTO User_Email_Alert( alert_email_message_uid, nedss_entry_uid,email_id, seq_nbr) VALUES(?,?,?,?)";
	private static final String UPDATE_ALERT_EMAIL_MESSAGE= "Update "+ DataTables.ALERT_EMAIL_MESSAGE + " SET transmission_status = ?,  email_sent_time = ? where alert_email_message_uid = ?";
	private static final String RESET_QUEUED_ALERT_EMAIL_MESSAGES= "Update "+ DataTables.ALERT_EMAIL_MESSAGE + " SET transmission_status = '"+ AlertStateConstant.MESSAGE_NOT_SENT+"' where simulated_alert = 'N' and transmission_status = 'QUEUED'";
	private static final String FIND_MPR_FOR_LAB = "SELECT person_parent_uid from " + DataTables.PERSON_TABLE +  " where person_uid in (SELECT subject_entity_uid from " + DataTables.PARTICIPATION_TABLE + "  where type_cd = 'PATSBJ' and act_uid in (select observation_uid from " + DataTables.OBSERVATION_TABLE + " where local_id = ?))";
	private static final String FIND_SENDING_FACILITY_FOR_CASE = "SELECT sending_facility_nm from " + DataTables.NBS_DOCUMENT_TABLE +  " where local_id = ?";

	public Collection<Object> getAlertEmailMessageVOCollection() {
		Collection<Object> alertEmailMeassageColl = new ArrayList<Object> ();
		try {
			Collection<Object> alertEmailMeassageDTColl = getAlertEmailMessageDTColl();
			if (alertEmailMeassageDTColl != null) {
				Iterator<Object> it = alertEmailMeassageDTColl.iterator();
				while (it.hasNext()) {
					AlertEmailMessageVO alertEmailMessageVO = new AlertEmailMessageVO();
					AlertEmailMessageDT alertEmailMessageDT = (AlertEmailMessageDT) it
					.next();
					queuedAlertEmailMessageVO(alertEmailMessageDT);
					alertEmailMessageVO.setAlertEmailMessageDT(alertEmailMessageDT);
					Long alertUid = alertEmailMessageDT.getAlertUid();
					AlertDAO alertDAO = new AlertDAO();
					Collection<Object> alertUserDTColl = alertDAO
					.getAlertUserDTCollection(alertUid);
					if (alertUserDTColl != null) {
						Iterator<Object> iter = alertUserDTColl.iterator();
						while (iter.hasNext()) {
							AlertUserDT alertUserDT = (AlertUserDT) iter.next();
							Long nedssEntryUid = alertUserDT.getNedssEntryId();
							UserEmailDAO userEmailDAO = new UserEmailDAO();
							Collection<Object> userEmailDTCollection  = userEmailDAO
							.getUserEmailDTCollection(nedssEntryUid);
							alertEmailMessageVO.getUserEmailDTCollection().addAll(userEmailDTCollection);
						}

					}
					alertEmailMeassageColl.add(alertEmailMessageVO);
				}
			}
		} catch (NEDSSSystemException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return alertEmailMeassageColl;
	}

	public void  queuedAlertEmailMessageVO(AlertEmailMessageDT alertEmailMessageDT) throws NEDSSSystemException {
	 	Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        try{
			dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
            logger.fatal("SQLException while obtaining database connection for queuedAlertEmailMessageVO ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
        
        try {
			preparedStmt = dbConnection.prepareStatement(QUEUED_ALERT_EMAIL_MESSAGE);
			int i = 1;
			preparedStmt.setLong(i++, alertEmailMessageDT.getAlertEmailMessageUid().longValue());

			resultCount = preparedStmt.executeUpdate();
             if (resultCount != 1)
                logger.error
                            ("Error: none or more than one alert uid updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException auid)
            {
                logger.fatal("SQLException while updating " +
                    "alert uids, \n", auid);
                throw new NEDSSDAOSysException( auid.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
}// end of update method

	@SuppressWarnings("unchecked")
	private Collection<Object> getAlertEmailMessageDTColl() throws NEDSSSystemException {
		logger.debug("SELECT_ALERT_MESSAGE" + SELECT_ALERT_MESSAGE);
		AlertEmailMessageDT alertEmailMessageDT = new AlertEmailMessageDT();
		ArrayList<Object>  alertEmailMeassageColl = new ArrayList<Object> ();
		try {
			alertEmailMeassageColl = (ArrayList<Object> ) preparedStmtMethod(
					alertEmailMessageDT, alertEmailMeassageColl,
					SELECT_ALERT_MESSAGE, NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.fatal("Exception in getAlertEmailMessageDTColl:  ERROR = "
					+ ex);
			throw new NEDSSSystemException(ex.toString());
		}

		return alertEmailMeassageColl;
	}

	public void  insertEmailsWithLogDetail(AlertLogVO alertLogVO,
			Collection<Object> userEmailColl) {
		logger.debug("insertEmailsWithLogDetail called");
		try{
			if(userEmailColl!=null){
				Iterator<Object> it = userEmailColl.iterator();
				while(it.hasNext()){
					UserEmailAlertDT userEmailAlertDT= (UserEmailAlertDT)it.next();
					logger.debug("insertEmailsWithLogDetail userEmailAlertDT:"+ userEmailAlertDT.stringValue());
					insertUserEmailAlertDT(userEmailAlertDT);
				}
			}
			AlertLogDT alertLogDT=alertLogVO.getAlertLogDT();
			logger.debug("insertEmailsWithLogDetail alertLogDT:"+ alertLogDT.stringValue());
			Long alertLogUid = insertAlertLogDT(alertLogDT);
			Collection<Object> coll =alertLogVO.getAlertLogDetailDTColl();
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()){
				AlertLogDetailDT alertLogDetailDT= (AlertLogDetailDT)it.next();
				alertLogDetailDT.setAlertLogUid(alertLogUid);
				insertAlertDetailDT(alertLogDetailDT);
				logger.debug("insertEmailsWithLogDetail alertLogDetailDT:"+ alertLogDetailDT.stringValue());
			}
			logger.debug("insertEmailsWithLogDetail completed");
		}catch(Exception ex){
			logger.error("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

	}

	private void insertUserEmailAlertDT(UserEmailAlertDT userEmailAlertDT)
	throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_USER_EMAIL_ALERT=" + INSERT_USER_EMAIL_ALERT);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertUserEmailAlertDT ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_USER_EMAIL_ALERT);
			int i = 1;
			preparedStmt.setLong(i++, userEmailAlertDT.getAlertEmailMessageUid().longValue()); // 1
			preparedStmt.setLong(i++, userEmailAlertDT.getNedssEntryUid().longValue()); // 2
			preparedStmt.setString(i++, userEmailAlertDT.getEmailId()); // 3
			preparedStmt.setInt(i++, userEmailAlertDT.getSeqNbr().intValue()); // 4
			resultCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "userEmailAlertDT in insertUserEmailAlertDT:" + userEmailAlertDT.stringValue(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into insertUserEmailAlertDT, alertUserDT="
					+ userEmailAlertDT.stringValue(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of insert method

	private Long  insertAlertLogDT(AlertLogDT alertLogDT)
	throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 =null;
		ResultSet  rs = null;
		Long alertLogUid =null;
		int resultCount = 0;
		logger.debug("INSERT_ALERT_LOG=" + INSERT_ALERT_LOG);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertAlertLogDT ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ALERT_LOG);
			int i = 1;
			preparedStmt.setLong(i++, alertLogDT.getAlertUid().longValue()); // 1
			preparedStmt.setString(i++, alertLogDT.getEventLocalId()); // 2
			preparedStmt.setString(i++, alertLogDT.getActivityLog()); // 3
			preparedStmt.setTimestamp(i++, alertLogDT.getAddTime()); // 4
			resultCount = preparedStmt.executeUpdate();

			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(Alert_Log_uid) from Alert_Log");

			rs = preparedStmt2.executeQuery();
			if (rs.next()) {
				logger.debug("ID = " + rs.getLong(1));
				alertLogUid=new Long( rs.getLong(1));
			}
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "alertLogDT in insertAlertLogDT:" + alertLogDT.stringValue(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into insertAlertLogDT, alertLogDT="
					+ alertLogDT.stringValue(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		return alertLogUid;
	}// end of insert method


	private void insertAlertDetailDT(AlertLogDetailDT alertLogDetailDT)
	throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_ALERT_LOG_DETAIL=" + INSERT_ALERT_LOG_DETAIL);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ALERT_LOG_DETAIL);
			int i = 1;
			preparedStmt.setLong(i++, alertLogDetailDT.getAlertLogUid().longValue()); // 1
			preparedStmt.setString(i++, alertLogDetailDT.getAlertActivityDetailLog()); // 2
			resultCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "alertLogDetailDT in insertAlertDetailDT:" + alertLogDetailDT.stringValue(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into insertAlertDetailDT, alertLogDetailDT="
					+ alertLogDetailDT.stringValue(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of insert method


	public Collection<Object>  getAlertConditionList(LabResultProxyVO labResultProxyVO, NBSSecurityObj securityObj){
		TreeMap<Object,Object> conditionCodeSet= new TreeMap<Object,Object> ();
		TreeMap<Object,Object> conditionCodeForLocal = new TreeMap<Object,Object> ();
		ObservationDT rootDT= getRootDT(labResultProxyVO);
		String electronicInd = rootDT.getElectronicInd();
		String labClia=null;
		if (electronicInd!=null && electronicInd.equalsIgnoreCase(NEDSSConstants.YES))
			labClia=getReportingLabCLIA(labResultProxyVO, securityObj);
		else
			labClia=labResultProxyVO.getLabClia();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getAlertConditionList ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		Collection<ObservationVO> observationColl = labResultProxyVO.getTheObservationVOCollection();
		String SELECT_CONDITION_CODE_SQL = "select condition_cd from nbs_srte..loinc_condition where loinc_cd='";
		String SNOMED_CONDITION_SQL="SELECT CONDITION_CD FROM nbs_srte..SNOMED_CONDITION WHERE SNOMED_CD='";
		String MAPPING_CONDITION_SQL="select condition_cd from nbs_srte..labtest_progarea_mapping where lab_test_cd='";
		String LAB_RESULT_SQL="select default_condition_cd  from nbs_srte..lab_result where lab_result_cd='";
		String NONSNOMED_CONDITION_SQL=" select condition_cd  from nbs_srte..lab_result_snomed sn, nbs_srte..snomed_condition cond where "
				 								+"sn.snomed_cd = cond.snomed_cd and lab_result_cd = '"  ;
		Iterator<ObservationVO> it = observationColl.iterator();
		try {
			while (it.hasNext()) {
				ObservationVO observationVO = (ObservationVO) it.next();
				boolean isConditionFound = false;
				if (observationVO.getTheObservationDT().getObsDomainCdSt1()!=null && observationVO.getTheObservationDT().getObsDomainCdSt1()
						.equalsIgnoreCase(
								NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD)) {

					if(observationVO.getTheObsValueCodedDTCollection()!=null){
						Collection<Object> codedResultColl = observationVO
						.getTheObsValueCodedDTCollection();
						Iterator<Object> iter = codedResultColl.iterator();
						while (iter.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iter
							.next();
							if (obsValueCodedDT.getCodeSystemCd()!=null && obsValueCodedDT.getCodeSystemCd().equalsIgnoreCase(
							"SNM")) {
								logger.debug("SNOMED_CONDITION_SQL SQL Query is " + SNOMED_CONDITION_SQL );
								preparedStmt = dbConnection
								.prepareStatement(SNOMED_CONDITION_SQL
										+ obsValueCodedDT.getCode()+"'");
								ResultSet rs = preparedStmt.executeQuery();
								if (rs.next()) {
									logger.debug("ID = " + rs.getString(1));
									if(rs.getString(1)!=null){
										conditionCodeForLocal.put(rs.getString(1), rs.getString(1));
										isConditionFound=true;
										closeResultSet(rs);
										break;
									}
								}
								if (rs != null)
									closeResultSet(rs);
								if (preparedStmt != null)
									closeStatement(preparedStmt);								
							}
						}
					}
					 if(observationVO.getTheObsValueCodedDTCollection()!=null && !isConditionFound){
						Collection<Object> codedResultColl = observationVO
						.getTheObsValueCodedDTCollection();
						Iterator<Object> iter = codedResultColl.iterator();
						while (iter.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iter
							.next();
							if (obsValueCodedDT.getCodeSystemCd()!=null && !obsValueCodedDT.getCodeSystemCd().equalsIgnoreCase(
							"SNM")) {
								logger.debug("NONSNOMED_CONDITION_SQL SQL Query is " + NONSNOMED_CONDITION_SQL );
								preparedStmt = dbConnection
								.prepareStatement(NONSNOMED_CONDITION_SQL
										+ obsValueCodedDT.getCode() +"' and laboratory_id ='"+labClia+"'");
								ResultSet rs = preparedStmt.executeQuery();
								if (rs.next()) {
									logger.debug("ID = " + rs.getString(1));
									if(rs.getString(1)!=null){
										conditionCodeForLocal.put(rs.getString(1), rs.getString(1));
										isConditionFound=true;
										closeResultSet(rs);
										break;
									}
								}
								if (rs != null)
									closeResultSet(rs);
								if (preparedStmt != null)
									closeStatement(preparedStmt);									
							}
						}
					}
					 if (observationVO.getTheObservationDT().getCdSystemCd()!=null && observationVO.getTheObservationDT().getCdSystemCd()
							.equalsIgnoreCase("LN") && !isConditionFound) {
						 logger.debug("SELECT_CONDITION_CODE_SQL SQL Query is " + SELECT_CONDITION_CODE_SQL );

						preparedStmt = dbConnection
						.prepareStatement(SELECT_CONDITION_CODE_SQL
								+ observationVO.getTheObservationDT().getCd()+"'" );
						ResultSet rs = preparedStmt.executeQuery();
						if (rs.next()) {
							logger.debug("ID = " + rs.getString(1));
							if(rs.getString(1)!=null){
								conditionCodeSet.put(rs.getString(1), rs.getString(1));
								isConditionFound=true;
							}
						}
						if (rs != null)
							closeResultSet(rs);
						//TODO /*select condition_cd from nbs_srte..loinc_condition where loinc_cd= ?*/
					} if (observationVO.getTheObservationDT().getCdSystemCd()!=null && !observationVO.getTheObservationDT().getCdSystemCd()
							.equalsIgnoreCase("LN") && !isConditionFound) {
						 logger.debug("MAPPING_CONDITION_SQL SQL Query is " + MAPPING_CONDITION_SQL );

						preparedStmt = dbConnection
						.prepareStatement(MAPPING_CONDITION_SQL
								+ observationVO.getTheObservationDT().getCd()  + "' and laboratory_id ='"+labClia+"'");
						ResultSet rs = preparedStmt.executeQuery();
						if (rs.next()) {
							logger.debug("ID = " + rs.getString(1));
							if(rs.getString(1)!=null){
								conditionCodeSet.put(rs.getString(1), rs.getString(1));
								isConditionFound=true;
							}
						}
						if (rs != null)
							closeResultSet(rs);
						if (preparedStmt != null)
							closeStatement(preparedStmt);
					 }
					 if(observationVO.getTheObsValueCodedDTCollection()!=null && !isConditionFound){
						Iterator<Object> iterator = observationVO.getTheObsValueCodedDTCollection().iterator();
						while (iterator.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iterator.next();
							/**
							 * civil00017322: The ELRs can be locally defined and the getCodeSystemCd could be any value( in this particular defect it was "L" and the alert was not generated).
							 * After discussing with Stephen, it was decided that we need to check for obsValueCodedDT.getCodeSystemCd() and this value can be anything except "SNM". Hence this change obsValueCodedDT.getCodeSystemCd().equalsIgnoreCase(	"NBS")
							 * to  !obsValueCodedDT.getCodeSystemCd().equalsIgnoreCase("SNM")
							 */
							if ( obsValueCodedDT.getCodeSystemCd()!=null && !obsValueCodedDT.getCodeSystemCd().equalsIgnoreCase("SNM")) {
								 logger.debug("LAB_RESULT_SQL SQL Query is " + LAB_RESULT_SQL );
								preparedStmt = dbConnection
								.prepareStatement(LAB_RESULT_SQL+ obsValueCodedDT.getCode() +"' and laboratory_id = '"+labClia+"'");
								ResultSet rs = preparedStmt.executeQuery();
								if (rs.next()) {
									logger.debug("ID = " + rs.getString(1));
									if(rs.getString(1)!=null){
										conditionCodeSet.put(rs.getString(1), rs.getString(1));
										isConditionFound=true;
										closeResultSet(rs);
										break;
									}
								}
								if (rs != null)
									closeResultSet(rs);
								if (preparedStmt != null)
									closeStatement(preparedStmt);
							}
						}
					}
				}
			}
			if(conditionCodeForLocal.size()>0){
				conditionCodeSet.putAll(conditionCodeForLocal);
			}
		}
		catch (SQLException e) {
			logger.fatal("SQLException while getting getAlertConditionList=", e);
		} catch (Exception ex) {
			logger.fatal("Error while getting getAlertConditionList=", ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return conditionCodeSet.values();
	}


	public Long getAlertUid(String conditionCode){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getAlertUid ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		Long alertUid=null;
		try {
			preparedStmt = dbConnection.prepareStatement("SELECT Alert_uid  from Alert where condition_cd = "+conditionCode);
			ResultSet rs = preparedStmt.executeQuery();

			if (rs.next()) {
				logger.debug("ID = " + rs.getString(1));
				alertUid=new Long( rs.getLong(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
		return alertUid;

	}

	public void insertAlertEmailVO(AlertEmailMessageVO alertEmailMessageVO) throws NEDSSSystemException{
		try{
			AlertEmailMessageDT alertEmailMessageDT= alertEmailMessageVO.getAlertEmailMessageDT();
			Long alertEmailMessageUID = insertAlertEmailMessageDT(alertEmailMessageDT);
			Collection<Object> alertUserEmailCollection  = alertEmailMessageVO.getUserEmailDTCollection();
			insertUserEmailCollection(alertUserEmailCollection, alertEmailMessageUID);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertUserEmailCollection(Collection<Object> alertUserEmailCollection,  Long alertEmailMessageUID){
		try{
			if(alertUserEmailCollection!=null){
				Iterator<Object> it = alertUserEmailCollection.iterator();
				while(it.hasNext()){
					UserEmailDT userEmailDT= (UserEmailDT)it.next();
					UserEmailAlertDT userEmailAlertDT = new UserEmailAlertDT();
					userEmailAlertDT.setAlertEmailMessageUid(alertEmailMessageUID);
					userEmailAlertDT.setNedssEntryUid(userEmailDT.getNedssEntryId());
					userEmailAlertDT.setEmailId(userEmailDT.getEmailId());
					userEmailAlertDT.setSeqNbr(userEmailDT.getSeqNbr());
					logger.debug("insertUserEmailCollection  userEmailAlertDT:"+ userEmailAlertDT.stringValue());
					insertUserEmailAlertDT(userEmailAlertDT);
				}
			}
		}catch(Exception ex){
			logger.error("Exception ="+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	private Long  insertAlertEmailMessageDT(AlertEmailMessageDT alertEmailMessageDT)
	throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 = null;
		Long alertEmailMessageUid = null;
		int resultCount = 0;
		logger.debug("INSERT_ALERT_MESSAGE=" + INSERT_ALERT_MESSAGE);
		
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertAlertEmailMessageDT ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ALERT_MESSAGE);
			int i = 1;
			preparedStmt.setString(i++, alertEmailMessageDT.getTypeCd()); // 1
			preparedStmt.setString(i++, alertEmailMessageDT.getType()); // 2
			preparedStmt.setString(i++, alertEmailMessageDT.getSeverityCd()); // 3
			preparedStmt.setString(i++, alertEmailMessageDT.getSeverity()); // 4
			preparedStmt.setString(i++, alertEmailMessageDT.getSimulatedAlert()); //5
			preparedStmt.setString(i++, alertEmailMessageDT.getJurisdictionCd()); //6
			preparedStmt.setString(i++, alertEmailMessageDT.getJurisdictionDescription()); //7
			preparedStmt.setString(i++, alertEmailMessageDT.getAssociatedConditionCd()); //8
			preparedStmt.setString(i++, alertEmailMessageDT.getAssociatedConditionDesc()); //9
			preparedStmt.setTimestamp(i++, alertEmailMessageDT.getEventAddTime()); //10
			preparedStmt.setTimestamp(i++, alertEmailMessageDT.getAlertAddTime()); //11
			preparedStmt.setString(i++, alertEmailMessageDT.getEventLocalId()); //12
			preparedStmt.setString(i++, alertEmailMessageDT.getTransmissionStatus()); //13
			preparedStmt.setLong(i++, alertEmailMessageDT.getAlertUid().longValue()); //14
			preparedStmt.setString(i++, alertEmailMessageDT.getProgAreaCd()); //15
			preparedStmt.setString(i++, alertEmailMessageDT.getProAreaDescription()); //16
			preparedStmt.setTimestamp(i++, alertEmailMessageDT.getEmailSentTime()); //17



			resultCount = preparedStmt.executeUpdate();
			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(Alert_Email_Message_UID) from Alert_Email_Message");

			ResultSet  rs = preparedStmt2.executeQuery();
			 if (rs.next()) {
			        logger.debug("ID = " + rs.getLong(1));
			        alertEmailMessageUid=new Long( rs.getLong(1));
			      }

		}  catch (Exception ex) {
			logger.fatal("Error while inserting into insertAlertEmailMessageDT, alertEmailMessageDT="
					+ alertEmailMessageDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return alertEmailMessageUid;
	}// end of insert method

	public void insertEmailLogVO(AlertLogVO alertLogVO){
		try{
			AlertLogDT alertLogDT=alertLogVO.getAlertLogDT();
			logger.debug("insertEmailLogVO alertLogDT:"+ alertLogDT.stringValue());
			Long alertLogUid = insertAlertLogDT(alertLogDT);
			Collection<Object> coll =alertLogVO.getAlertLogDetailDTColl();
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()){
				AlertLogDetailDT alertLogDetailDT= (AlertLogDetailDT)it.next();
				alertLogDetailDT.setAlertLogUid(alertLogUid);
				insertAlertDetailDT(alertLogDetailDT);
				logger.debug("insertEmailLogVO alertLogDetailDT:"+ alertLogDetailDT.stringValue());
			}
		}catch(Exception ex){
			logger.error("Exception ="+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}


	public void  updateAlertEmailMessageVO(AlertEmailMessageVO alertEmailMessageVO) throws NEDSSSystemException {
	 	Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for updateAlertEmailMessageVO ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_ALERT_EMAIL_MESSAGE);
			int i = 1;
			preparedStmt.setString(i++, alertEmailMessageVO.getAlertEmailMessageDT().getTransmissionStatus()); // 1
			preparedStmt.setTimestamp(i++, alertEmailMessageVO.getAlertEmailMessageDT().getEmailSentTime()); // 2
			preparedStmt.setLong(i++, alertEmailMessageVO.getAlertEmailMessageDT().getAlertEmailMessageUid().longValue()); // 3

			resultCount = preparedStmt.executeUpdate();
             if (resultCount != 1)
                logger.error
                            ("Error: none or more than one alert uid updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException auid)
            {
                logger.fatal("SQLException while updating " +
                    "alert uids, \n", auid);
                throw new NEDSSDAOSysException( auid.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
            try{
	            insertUserEmailCollection(alertEmailMessageVO.getUserEmailDTCollection(), alertEmailMessageVO.getAlertEmailMessageDT().getAlertEmailMessageUid());
	        	logger.debug("updateAlertEmailMessageVO AlertEmailMessageDT:"+ alertEmailMessageVO.getAlertEmailMessageDT().stringValue());
            }
            catch(Exception ex){
            	logger.error("Exceptio raised in updateAlertEmailMessageVO AlertEmailMessageDT:"+ alertEmailMessageVO.getAlertEmailMessageDT().stringValue());
            }

}// end of update method
	private String getReportingLabCLIA(AbstractVO proxy,
			NBSSecurityObj securityObj) {
		Collection<Object> partColl = null;
		if (proxy instanceof LabResultProxyVO) {
			partColl = ((LabResultProxyVO) proxy)
					.getTheParticipationDTCollection();
		}
		if (proxy instanceof MorbidityProxyVO) {
			partColl = ((MorbidityProxyVO) proxy)
					.getTheParticipationDTCollection();
		}

		// Get the reporting lab
		Long reportingLabUid = this.getUid(partColl,
				NEDSSConstants.ENTITY_UID_LIST_TYPE,
				NEDSSConstants.ORGANIZATION, NEDSSConstants.PAR111_TYP_CD,
				NEDSSConstants.PART_ACT_CLASS_CD,
				NEDSSConstants.RECORD_STATUS_ACTIVE, false);

		OrganizationVO reportingLabVO = null;
		EntityController eController = null;
		try {
			eController = getEntityControllerRemoteInterface();
			if (reportingLabUid != null) {
				reportingLabVO = eController.getOrganization(reportingLabUid,
						securityObj);
			}
		} catch (RemoteException rex) {
			rex.printStackTrace();
			throw new EJBException(
					"Error while retriving reporting organization vo, its uid is: "
							+ reportingLabUid);
		} finally {
			try {
				eController.remove();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Get the CLIA
		String reportingLabCLIA = null;

		if (reportingLabVO != null) {
			Collection<Object> entityIdColl = reportingLabVO
					.getTheEntityIdDTCollection();

			if (entityIdColl != null && entityIdColl.size() > 0) {
				for (Iterator<Object> it = entityIdColl.iterator(); it.hasNext();) {
					EntityIdDT idDT = (EntityIdDT) it.next();
					if (idDT == null) {
						continue;
					}

					String authoCd = idDT.getAssigningAuthorityCd();
					String idTypeCd = idDT.getTypeCd();
					if (authoCd != null
							&& idTypeCd != null
							&& authoCd
									.equalsIgnoreCase(NEDSSConstants.REPORTING_LAB_CLIA)
							&& idTypeCd
									.equalsIgnoreCase(NEDSSConstants.REPORTING_LAB_FI_TYPE)) { // civil00011659
						reportingLabCLIA = idDT.getRootExtensionTxt();
						break;
					}
				}
			}
		}
		return reportingLabCLIA;
	}
	
	private EntityController getEntityControllerRemoteInterface() throws
    EJBException
	{
	
	  EntityController entityController = null;
	  try
	  {
	
	    logger.debug("YOU ARE IN THE getRemoteInterface() method");
	    NedssUtils nu = new NedssUtils();
	    Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
	    EntityControllerHome entityControllerHome = (EntityControllerHome) javax.
	        rmi.PortableRemoteObject.narrow(obj, EntityControllerHome.class);
	    entityController = entityControllerHome.create();
	  }
	  catch (Exception e)
	  {
	    logger.fatal(
	        "Error while creating a EntityController reference in WorkupProxyEJB.",
	        e);
	    throw new EJBException(e.toString());
	  }
	  return entityController;
	} //getEntityControllerRemoteInterface

	private Long getUid(java.util.Collection<Object> associationDTColl,
            String uidListType, String uidClassCd,
            String uidTypeCd, String uidActClassCd,
            String uidRecordStatusCd, boolean act)

{

Long anUid = null;

try
{

Iterator<Object> assocIter = associationDTColl.iterator();

while (assocIter.hasNext())
{

if (!act)
{

ParticipationDT partDT = (ParticipationDT) assocIter.next();

if ( ( (partDT.getSubjectClassCd() != null &&
        partDT.getSubjectClassCd().equalsIgnoreCase(
    uidClassCd)) &&
      (partDT.getTypeCd() != null &&
       partDT.getTypeCd().equalsIgnoreCase(uidTypeCd)) &&
      (partDT.getActClassCd() != null &&
       partDT.getActClassCd().equalsIgnoreCase(
    uidActClassCd)) &&
      (partDT.getRecordStatusCd() != null &&
       partDT.getRecordStatusCd().equalsIgnoreCase(
    uidRecordStatusCd))))
{
  anUid = partDT.getSubjectEntityUid();
}
}
else
{

ActRelationshipDT actRelDT = (ActRelationshipDT) assocIter.next();

if ( ( (actRelDT.getSourceClassCd() != null &&
        actRelDT.getSourceClassCd().equalsIgnoreCase(
    uidClassCd)) &&
      (actRelDT.getTypeCd() != null &&
       actRelDT.getTypeCd().equalsIgnoreCase(
    uidTypeCd)) &&
      (actRelDT.getTargetClassCd() != null &&
       actRelDT.getTargetClassCd().equalsIgnoreCase(
    uidActClassCd)) &&
      (actRelDT.getRecordStatusCd() != null &&
       actRelDT.getRecordStatusCd().equalsIgnoreCase(
    uidRecordStatusCd))))
{

  if (uidListType.equalsIgnoreCase(
      NEDSSConstants.ACT_UID_LIST_TYPE))
  {
    anUid = actRelDT.getTargetActUid();
  }
  else if (uidListType.equalsIgnoreCase(
      NEDSSConstants.SOURCE_ACT_UID_LIST_TYPE))
  {
    anUid = actRelDT.getSourceActUid();
  }
}
}
}
}
catch (Exception ex)
{
ex.printStackTrace();
throw new RuntimeException("Error while retrieving a " + uidListType +
                       " uid. " + ex.toString());
}

return anUid;
}

	 private ObservationVO getRootObservationVO(AbstractVO proxy) throws
     NEDSSSystemException
	 {
	   Collection<ObservationVO> obsColl = null;
	   boolean isLabReport = false;
	   try{
		   if (proxy instanceof LabResultProxyVO)
		   {
		     obsColl = ( (LabResultProxyVO) proxy).getTheObservationVOCollection();
		     isLabReport = true;
		   }
		   if (proxy instanceof MorbidityProxyVO)
		   {
		     obsColl = ( (MorbidityProxyVO) proxy).getTheObservationVOCollection();
		   }
		
		   ObservationVO rootVO = getRootObservationVO(obsColl, isLabReport);
		   
		   if( rootVO != null)
			     return rootVO;
			   throw new IllegalArgumentException(
			       "Expected the proxyVO containing a root observation(e.g., ordered test)");
	   }catch(Exception ex){
		   logger.error("Exception = "+ex.getMessage(),ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
	   
	 } //getRootObservationVO

 private ObservationVO getRootObservationVO(Collection<ObservationVO> obsColl)
 {
   return getRootObservationVO(obsColl, false);
 }
 private ObservationVO getRootObservationVO(Collection<ObservationVO> obsColl, boolean isLabReport)
 {
	 try{
	     if(obsColl == null) return null;
	
	     logger.debug("ObservationVOCollection  is not null");
	     Iterator<ObservationVO> iterator = null;
	     for (iterator = obsColl.iterator(); iterator.hasNext(); )
	     {
	       ObservationVO observationVO = (ObservationVO) iterator.next();
	       if (observationVO.getTheObservationDT() != null &&
	           ( (observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null &&
	              observationVO.getTheObservationDT().getCtrlCdDisplayForm().
	              equalsIgnoreCase(NEDSSConstants.LAB_CTRLCD_DISPLAY))
	            ||
	            (observationVO.getTheObservationDT().getObsDomainCdSt1() != null &&
	             observationVO.getTheObservationDT().getObsDomainCdSt1().
	             equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD) && isLabReport)
	            ||
	            (observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null &&
	             observationVO.getTheObservationDT().getCtrlCdDisplayForm().
	             equalsIgnoreCase(NEDSSConstants.MOB_CTRLCD_DISPLAY))))
	       {
	         logger.debug("found root vo !!");
	         return observationVO;
	       }
	       else
	       {
	         continue;
	       }
	     }
	 }catch(Exception ex){
		 logger.error("Exception = "+ex.getMessage(),ex);
		 throw new NEDSSSystemException(ex.toString(), ex);
	 }
     return null;
 }
	 private ObservationDT getRootDT(AbstractVO proxyVO) throws NEDSSSystemException
	  {
	    ObservationVO rootVO = getRootObservationVO(proxyVO);
	    if (rootVO != null)
	    {
	      return rootVO.getTheObservationDT();
	    }
	    return null;
	  }

	public void resetQueue() {
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for resetQueue ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try {
			preparedStmt = dbConnection.prepareStatement(RESET_QUEUED_ALERT_EMAIL_MESSAGES);
			resultCount = preparedStmt.executeUpdate();
             if (resultCount != 1)
                logger.debug
                            ("Number of Alert Message Id resetted to SENT state, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException auid)
            {
                logger.fatal("SQLException while updating " +
                    "alert uids, \n", auid);
                throw new NEDSSDAOSysException( auid.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
	}
	/*
	 * getMPRForLabReportLocalId - get the Master Patient Record (person) associated with the Lab.
	 * This is needed to get the lab results in the correct format.
	 * @param String LocalId of Lab i.e.OBS10273000GA01 
	 * @return PersonUid associated with Lab or Null if not found
	 */
	public Long getMPRForLabReportLocalId(String labReportLocalId){
		logger.debug("in getMPRForLabReportLocalId(" + labReportLocalId + ")");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getMPRForLabReportLocalId ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		Long mprUid=null;
		try {
			preparedStmt = dbConnection.prepareStatement(FIND_MPR_FOR_LAB);
			preparedStmt.setString(1, labReportLocalId);
			rs = preparedStmt.executeQuery();

			if (rs.next()) {
				logger.debug("MPR Person Uid = " + rs.getString(1));
				mprUid=new Long( rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.warn("AlertEmailMessageDAO unable to get Patient uid for Lab " + labReportLocalId);
			e.printStackTrace();
		} finally {
				closeResultSet(rs);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
		return mprUid;

	}
	/*
	 * getDocumentSendingFacilityName- get the Sending Facility
	 * for the case report.
	 * @param String LocalId of Case Report i.e. CAS10106000GA01 
	 * @return sending facility name or <no sending facility specified> if not found
	 */
	public String getDocumentSendingFacilityName(String caseReportLocalId){
		logger.debug("in getDocumentSendingFacilityName(" + caseReportLocalId + ")");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getDocumentSendingFacilityName ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		String sendingFacility =  "<no sending facility specified>";
		try {
			preparedStmt = dbConnection.prepareStatement(FIND_SENDING_FACILITY_FOR_CASE);
			preparedStmt.setString(1, caseReportLocalId);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				logger.debug("Document Sending Facility = " + rs.getString(1));
				sendingFacility = rs.getString(1);
			}
		} catch (SQLException e) {
			logger.warn("AlertEmailMessageDAO unable to get sending facility for document " + caseReportLocalId);
			logger.warn(e.getMessage());
		} finally {
				closeResultSet(rs);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
		return sendingFacility;
	}
	
	
}
