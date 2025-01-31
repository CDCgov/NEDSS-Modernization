package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao;


import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.ConfirmationMethodDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.ejb.bean.Person;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;



/**
 * PamConversionLegacyDAO
 * Description:
 * Copyright:    Copyright (c) 2008
 * @author Pradeep Sharma
 */

public class PamConversionLegacyDAO extends DAOBase{
    static final LogUtils logger = new LogUtils(PamConversionDAO.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    //private String COUNT_PHC_CASES= "select count(*)  from public_health_case, where cd=? and record_status_cd in ('OPEN')";
    private String COUNT_PHC_LEGACY_CASES= " SELECT  COUNT(*)  FROM   Public_health_case "
                                    +" WHERE cd = ? AND record_status_cd IN ('OPEN')"
    	                            +" AND public_health_case_uid NOT IN "
                                    +" (SELECT  Public_health_case.public_health_case_uid FROM  Public_health_case ,NBS_act_entity "
    	                            +" where Public_health_case.public_health_case_uid = NBS_act_entity.act_uid) ";
    private String COUNT_PHC_PAGEBUILDER_CASES = "select COUNT(*) from public_health_case where cd = ? AND case_type_cd = 'I'";
                                                      
    	private static final String SELECT_PUBLIC_HEALTH_CASE = " public_health_case_uid \"publicHealthCaseUid\","
        +" activity_duration_amt \"activityDurationAmt\","
        +" activity_duration_unit_cd \"activityDurationUnitCd\","
        +" activity_from_time \"activityFromTime\","
        +" activity_to_time \"activityToTime\","
        +" add_reason_cd \"addReasonCd\","
        +" add_time \"addTime\","
        +" add_user_id \"addUserId\","
        +" case_class_cd \"caseClassCd\","
        +" cd \"cd\","
        +" cd_desc_txt \"cdDescTxt\","
        +" cd_system_cd \"cdSystemCd\","
        +" cd_system_desc_txt \"cdSystemDescTxt\","
        +" confidentiality_cd \"confidentialityCd\","
        +" confidentiality_desc_txt \"confidentialityDescTxt\","
        +" detection_method_cd \"detectionMethodCd\","
        +" detection_method_desc_txt \"detectionMethodDescTxt\","
        +" disease_imported_cd \"diseaseImportedCd\","
        +" disease_imported_desc_txt \"diseaseImportedDescTxt\","
        +" effective_duration_amt \"effectiveDurationAmt\","
        +" effective_duration_unit_cd \"effectiveDurationUnitCd\","
        +" effective_from_time \"effectiveFromTime\","
        +" effective_to_time \"effectiveToTime\", "
        +" group_case_cnt \"groupCaseCnt\","
        +" investigation_status_cd \"investigationStatusCd\","
        +" jurisdiction_cd \"jurisdictionCd\","
        +" last_chg_reason_cd \"lastChgReasonCd\","
        +" last_chg_time \"lastChgTime\","
        +" last_chg_user_id \"lastChgUserId\","
        +" local_id \"localId\","
        +" mmwr_week \"mmwrWeek\","
        +" mmwr_year \"mmwrYear\","
       // +" org_access_permis \"orgAccessPermis\","
        +" outbreak_name \"outbreakName\","
        +" outbreak_from_time \"outbreakFromTime\","
        +" outbreak_ind \"outbreakInd\","
        +" outbreak_to_time \"outbreakToTime\","
        +" outcome_cd \"outcomeCd\","
        +" patient_group_id \"patientGroupId\","
       // +" prog_area_access_permis \"progAreaAccessPermis\","
        +" prog_area_cd \"progAreaCd\","
        +" record_status_cd \"recordStatusCd\","
        +" record_status_time \"recordStatusTime\","
        +" repeat_nbr \"repeatNbr\","
        +" rpt_cnty_cd \"rptCntyCd\","
        +" status_cd \"statusCd\","
        +" status_time \"statusTime\","
        +" transmission_mode_cd \"transmissionModeCd\","
        +" transmission_mode_desc_txt \"transmissionModeDescTxt\","
        +" txt \"txt\","
        +" user_affiliation_txt \"userAffiliationTxt\", "
        + "pat_age_at_onset \"patAgeAtOnset\","
        +" pat_age_at_onset_unit_cd \"patAgeAtOnsetUnitCd\","
        +" rpt_form_cmplt_time \"rptFormCmpltTime\","
        +" rpt_source_cd \"rptSourceCd\","
        +" rpt_source_cd_desc_txt \"rptSourceCdDescTxt\","
        +" rpt_to_county_time \"rptToCountyTime\","
        +" rpt_to_state_time \"rptToStateTime\","
        +" diagnosis_time \"diagnosisTime\" ,"
        +" program_jurisdiction_oid \"programJurisdictionOid\" ,"
        +" shared_ind \"sharedInd\" ,"
        +" version_ctrl_nbr \"versionCtrlNbr\" , "                                                          
        +" case_type_cd \"caseTypeCd\" , "
        +" investigator_assigned_time \"investigatorAssignedTime\" ,"
        +" hospitalized_ind_cd \"hospitalizedIndCD\" ,"
        +" hospitalized_admin_time \"hospitalizedAdminTime\" ,"
        +" hospitalized_discharge_time \"hospitalizedDischargeTime\" ,"
        +" hospitalized_duration_amt \"hospitalizedDurationAmt\" ,"
        +" pregnant_ind_cd \"pregnantIndCD\" ,"
        +" day_care_ind_cd \"dayCareIndCD\" ,"
        +" food_handler_ind_cd \"foodHandlerIndCD\" ,"
        +" imported_country_cd \"importedCountryCD\" ,"
        +" imported_state_cd \"importedStateCD\" ,"
        +" imported_city_desc_txt \"importedCityDescTxt\" ,"
        +" imported_county_cd \"importedCountyCD\" ,"
        +" deceased_time \"deceasedTime\" ,"
        +" count_interval_cd \"countIntervalCd\","
        +" priority_cd \"priorityCd\","
        +" infectious_from_date \"infectiousFromDate\","
        +" infectious_to_date \"infectiousToDate\","
        +" contact_inv_status_cd \"contactInvStatus\","
        +" contact_inv_txt \"contactInvTxt\" "
        +" FROM "
        +" public_health_case " ;
    
        private static final String SELECT_FROM_PHC_TABLE_SQL = "Select TOP 1  "+SELECT_PUBLIC_HEALTH_CASE+ " where cd=? and record_status_cd in ('OPEN') and public_health_case_uid not in   (select act_uid from nbs_act_entity) order by public_health_case_uid desc";
    
	/*private  String SELECT_FROM_PHC_TABLE_SQL="Select TOP(100) public_health_case_uid \"publicHealthCaseUid\", cd \"cd\", add_time \"addTime\", last_chg_time \"lastChgTime\", record_status_cd \"recordStatusCd\", 	add_user_id \"addUserId\", last_chg_user_id \"lastChgUserId\", record_status_time  \"recordStatusTime\"  from "+
	" public_health_case where cd=? and record_status_cd in ('OPEN') and public_health_case_uid not in   (select act_uid from nbs_case_answer) order by public_health_case_uid desc";
	private  String SELECT_FROM_PHC_TABLE_ORACLE="Select public_health_case_uid \"publicHealthCaseUid\", cd \"cd\", add_time \"addTime\", last_chg_time \"lastChgTime\", record_status_cd \"recordStatusCd\", add_user_id \"addUserId\", last_chg_user_id \"lastChgUserId\", record_status_time \"recordStatusTime\" from public_health_case " +
				"where cd=? and  ROWNUM <= 100 and record_status_cd in ('OPEN')  and public_health_case_uid not in   (select ACT from nbs_pam_answer) order by public_health_case_uid desc"; */
private String UPDATE_PHC_STATUS="update public_health_case set record_status_cd= ? where public_health_case_uid=?";
	private String UPDATE_ALL_PHC_STATUS="update public_health_case set record_status_cd= ? where record_status_cd=?";
	
	
	public InvestigationProxyVO  getDataFromOldStructure(String cd){
		InvestigationProxyVO investigationProxyVO= new InvestigationProxyVO();
		Collection<Object>  coll  =getPublicHealthDTColl(cd);
		try {
			if(coll!=null && coll.size()>0){
				Iterator<Object> it = coll.iterator();
				while(it.hasNext()){
					PublicHealthCaseDT publicHealthCaseDT= (PublicHealthCaseDT)it.next();
					PublicHealthCaseVO phcVO= new PublicHealthCaseVO();
					phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
					investigationProxyVO.setPublicHealthCaseVO(phcVO);
					ConfirmationMethodDAOImpl confirmationMethodDAOImpl = new ConfirmationMethodDAOImpl();
					Collection<Object>  theConfirmationMethodDTCollection  =confirmationMethodDAOImpl.load(publicHealthCaseDT.getPublicHealthCaseUid().longValue());
					investigationProxyVO.getPublicHealthCaseVO().setTheConfirmationMethodDTCollection(theConfirmationMethodDTCollection);
					investigationProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
					investigationProxyVO.setTheStateDefinedFieldDataDTCollection(new ArrayList<Object>());
					investigationProxyVO.setTheStateDefinedFieldDataDTCollection(getLDFCollection(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), 
							investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd()));

					investigationProxyVO.setTheParticipationDTCollection(selectParticipations(publicHealthCaseDT.getPublicHealthCaseUid()));
					Collection<Object>  obsColl =getObservationCollection(publicHealthCaseDT.getPublicHealthCaseUid());	
					if(obsColl!=null){
						Iterator<Object> obsIter=obsColl.iterator();
						while(obsIter.hasNext()){
							
							ObservationVO observationVO= new ObservationVO();
							Long observationUid=(Long)obsIter.next();
							if (observationUid != null)
								logger.debug("getDataFromOldStructure: retrieving observation " + observationUid.toString());
							else {
								logger.warn("getDataFromOldStructure: observationUid is NULL???? ");
								continue;
							}
								
							ObservationDT observationDT= selectObservation(observationUid.longValue());
							observationVO.setTheObservationDT(observationDT);
							if(observationVO.getTheObservationDT().getCd() == null) {
								logger.warn("getDataFromOldStructure: getTheObservationDT().getCd() == null - Skipping observation =  " + observationUid.toString());
								continue; //bad data
							}
							
							observationVO.setTheObsValueTxtDTCollection(selectObsValueTxts(observationUid.longValue()));
							observationVO.setTheObsValueNumericDTCollection(selectObsValueNumerics(observationUid.longValue()));
							observationVO.setTheObsValueCodedDTCollection(selectObsValueCodeds(observationUid.longValue()));
							observationVO.setTheObsValueDateDTCollection(selectObsValueDates(observationUid.longValue()));
							
							if(observationVO.getTheObservationDT().getCd().equalsIgnoreCase("ItemToRow")){
								
							}
							investigationProxyVO.getTheObservationVOCollection().add(observationVO);
						}
					}
					Collection<Object>  ActRelathionshipColl = getActRelationshipColl(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue());
					investigationProxyVO.setTheActRelationshipDTCollection(ActRelathionshipColl);
				}
			}
		} catch (NEDSSDAOSysException e) {
			logger.fatal("NEDSSDAOSysException in getDataFromOldStructure:  ERROR = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString(), e);
		} catch (NEDSSSystemException e) {
			logger.fatal("NEDSSSystemException in getDataFromOldStructure:  ERROR = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString(), e);		
		}
		return investigationProxyVO;
	}
	
	private Collection<Object>  getActRelationshipColl(long targetActUid){
		Collection<Object>  actColl = new ArrayList<Object> ();
		try{
			Collection<Object>  innerActColl = new ArrayList<Object> ();
			ActRelationshipDAOImpl actRelationshipDAOImpl =new ActRelationshipDAOImpl(); 
			Collection<Object>  actRelationDTColl = actRelationshipDAOImpl.load(targetActUid, "PHCInvForm");
			actColl.addAll(actRelationDTColl);
			Iterator<Object> it = actRelationDTColl.iterator();
			while(it.hasNext()){
				ActRelationshipDT actRelationshipDT =(ActRelationshipDT)it.next();
				Collection<Object>  actRelationDTColl2 = actRelationshipDAOImpl.load(actRelationshipDT.getSourceActUid().longValue(), "InvFrmQ");
				actColl.addAll(actRelationDTColl2);
				Iterator<Object> innerIterator = actColl.iterator();
				while(innerIterator.hasNext()){
					ActRelationshipDT innerActRelationshipDT =(ActRelationshipDT)innerIterator.next();
					Collection<Object>  innerActRelationDTColl = actRelationshipDAOImpl.load(innerActRelationshipDT.getSourceActUid().longValue(), "ItemToRow");
					innerActColl.addAll(innerActRelationDTColl);		
				}
			}
			actColl.addAll(innerActColl);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return actColl;
	}
	@SuppressWarnings("unchecked")
	private  Collection<Object>  getPublicHealthDTColl(String cd ){
		PublicHealthCaseDT publicHealthCaseDT  = new PublicHealthCaseDT();
		ArrayList<Object> publicHealthCaseDTCollection  = new ArrayList<Object> ();
		publicHealthCaseDTCollection.add(cd);
		String query=null;
		try
		{
			PropertyUtil propertyUtil = PropertyUtil.getInstance();
			query=SELECT_FROM_PHC_TABLE_SQL;
			 publicHealthCaseDTCollection  = (ArrayList<Object> )preparedStmtMethod(publicHealthCaseDT, publicHealthCaseDTCollection, query, NEDSSConstants.SELECT);
			Iterator it = publicHealthCaseDTCollection.iterator();
			while(it.hasNext()){
			
				PublicHealthCaseDT PhcCaseDT = (PublicHealthCaseDT)it.next();
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getPublicHealthDT:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		 return publicHealthCaseDTCollection;
	}
		
	public void updatePublicHealthCaseDT (PublicHealthCaseDT publicHealthCaseDT)throws  NEDSSSystemException
    {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_PHC_STATUS="+ UPDATE_PHC_STATUS);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for updatePublicHealthCaseDT ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
					preparedStmt = dbConnection.prepareStatement( UPDATE_PHC_STATUS);
					int i = 1;
				    preparedStmt.setString(i++, publicHealthCaseDT.getRecordStatusCd()); 
					 preparedStmt.setLong(i++, publicHealthCaseDT.getPublicHealthCaseUid().longValue()); 
					 resultCount = preparedStmt.executeUpdate();
					logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				    logger.fatal("SQLException while updatePublicHealthCaseDT: publicHealthCaseDT :"+publicHealthCaseDT.toString(), sqlex);
				    throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
			}
			catch(Exception ex)
			{
			    logger.fatal("Error while update into updatePublicHealthCaseDT: publicHealthCaseDT="+ publicHealthCaseDT.toString(), ex);
			    throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				  closeStatement(preparedStmt);
				  releaseConnection(dbConnection);
			}
	    }//end of update method
	
	
	
	private String SELECT_OBSERVATIONS= "select source_act_uid from act_relationship where target_act_uid in ( "+
     " select source_act_uid from act_relationship where TYPE_CD= 'PHCInvForm' and target_act_uid=?"+
     ") and type_cd='InvFrmQ'";
	private Collection<Object>  getObservationCollection  (Long publicHealthCaseUid) throws NEDSSSystemException
	    {
	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        
	        Collection<Object>  coll = new ArrayList<Object> ();
	        try
	        {
	            dbConnection = getConnection();
	            preparedStmt = dbConnection.prepareStatement(SELECT_OBSERVATIONS);
	            preparedStmt.setLong(1, publicHealthCaseUid.longValue());
	            resultSet = preparedStmt.executeQuery();
	           while(resultSet.next()){
	        	   coll.add(new Long(resultSet.getLong(1)));
	           }
	        }
	        catch(SQLException sex)
	        {
	        	logger.fatal("SQLException while checking for an"
                        + " existing observation's uid in observation table-> " +
                        publicHealthCaseUid, sex);
	        	throw new NEDSSDAOSysException( sex.getMessage(), sex);
	        }
	        catch(Exception ex)
	        {
	            logger.fatal("Exception while checking for an"
	                            + " existing observation's uid in observation table-> " +
	                            publicHealthCaseUid, ex);
	            throw new NEDSSSystemException( ex.getMessage(), ex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	        return coll;
	    }

	 private ObservationDT selectObservation (long observationUID) throws NEDSSSystemException
	    {

		     String SELECT_OBSERVATION = "SELECT observation_uid \"observationUid\", activity_duration_amt \"activityDurationAmt\", "
	                + "activity_duration_unit_cd \"activityDurationUnitCd\", activity_from_time \"activityFromTime\", activity_to_time \"activityToTime\", add_reason_cd \"addReasonCd\", "
	                + "add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", cd_desc_txt \"cdDescTxt\", cd_system_Cd \"cdSystemCd\", "
	                + "cd_system_desc_txt \"cdSystemDescTxt\", confidentiality_cd \"confidentialityCd\", confidentiality_desc_txt \"confidentialityDescTxt\",derivation_exp \"derivationExp\", "   
	                + "effective_duration_amt \"effectiveDurationAmt\", effective_duration_unit_cd \"effectiveDurationUnitCd\", effective_from_time \"effectiveFromTime\",effective_to_time \"effectiveToTime\", "
	                + "group_level_cd \"groupLevelCd\", jurisdiction_cd \"jurisdictionCd\", lab_condition_cd \"labConditionCd\", last_chg_reason_cd \"lastChgReasonCd\", "
	                + "last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", local_id \"localId\",method_cd \"methodCd\", method_desc_txt \"methodDescTxt\", obs_domain_cd \"obsDomainCd\", "
	                + "priority_cd \"priorityCd\", priority_desc_txt \"priorityDescTxt\", prog_area_cd \"progAreaCd\", record_status_cd \"recordStatusCd\", "
	                + "record_status_time \"recordStatusTime\",  repeat_nbr \"repeatNbr\", status_cd \"statusCd\", status_time \"statusTime\", "
	                + "subject_person_uid \"subjectPersonUid\",target_site_cd \"targetSiteCd\",target_site_desc_txt \"targetSiteDescTxt\",txt \"txt\", "
	                + "user_affiliation_txt \"userAffiliationTxt\", electronic_ind \"electronicInd\", ctrl_cd_display_form \"ctrlCdDisplayForm\", ctrl_cd_user_defined_1 \"ctrlCdUserDefined1\", "  
	                + "ctrl_cd_user_defined_2 \"ctrlCdUserDefined2\", ctrl_cd_user_defined_3 \"ctrlCdUserDefined3\", "
	                + "ctrl_cd_user_defined_4 \"ctrlCdUserDefined4\", value_cd \"valueCd\", ynu_cd \"ynuCd\", pnu_cd \"pnuCd\", obs_domain_cd_st_1 \"obsDomainCdSt1\", program_jurisdiction_oid \"programJurisdictionOid\", "
	                + "shared_ind \"sharedInd\", version_ctrl_nbr \"versionCtrlNbr\", "
	                + "alt_cd \"altCd\", alt_cd_desc_txt \"altCdDescTxt\", " 
	                + "alt_cd_system_cd \"altCdSystemCd\", alt_cd_system_desc_txt \"altCdSystemDescTxt\", " 
	                + "cd_derived_ind \"cdDerivedInd\", cd_version \"cdVersion\", "
	                + "rpt_to_state_time \"rptToStateTime\" FROM "
	                + DataTables.OBSERVATION_TABLE
	                + " WHERE observation_uid = ?";          
			ObservationDT observationDT = new ObservationDT();
	        Connection dbConnection= null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();

	        try
	        {
	            dbConnection = getConnection();
	            preparedStmt = dbConnection.prepareStatement(SELECT_OBSERVATION);
	            preparedStmt.setLong(1, observationUID);
	            resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            observationDT = (ObservationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, observationDT.getClass());
	        }
	        catch(SQLException sex)
	        {
	            logger.fatal("SQLException while selecting observation vo  id = " + observationUID, sex);
	            throw new NEDSSDAOSysException( sex.getMessage(), sex);
	        }
	        catch(Exception ex)
	        {
	            logger.fatal("Exception while selecting observation vo; id = " + observationUID , ex);
	            throw new NEDSSSystemException( ex.getMessage(), ex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	        return observationDT;
	    }
	 
	private  String SELECT_OBSVALUETXT = "SELECT observation_uid \"observationUid\",  obs_value_txt_seq \"obsValueTxtSeq\", data_subtype_cd \"dataSubtypeCd\","+
    " encoding_type_cd \"encodingTypeCd\", txt_type_cd \"txtTypeCd\",   value_txt \"valueTxt\" FROM " +
     DataTables.OBS_VALUE_TXT_TABLE + " WHERE observation_uid = ?";
	
	 private Collection<Object>  selectObsValueTxts (long observationUID) throws NEDSSSystemException
	    {
	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ObsValueTxtDT obsValueTxt = new ObsValueTxtDT();
	        try
	        {
	            dbConnection = getConnection();
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection for SELECT_OBSVALUETXT " , nsex);
	            throw new NEDSSSystemException( nsex.getMessage(), nsex);
	        }
	        try
	        {
	            preparedStmt = dbConnection.prepareStatement(SELECT_OBSVALUETXT);
	            preparedStmt.setLong(1, observationUID);
	            resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            ArrayList<Object> otList = new ArrayList<Object> ();
	            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueTxt.getClass(), otList);
	            logger.debug("return OBS_VALUE_TXT collection");
	            return otList;
	        }
	        catch(Exception ex)
	        {
	            logger.fatal("Error in result set handling while selecting obsValueTxt.", ex);
	            throw new NEDSSDAOSysException(ex.toString(), ex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	    }//end of selecting obsValueTxt
	 
	 public static final String SELECT_OBS_VALUE_NUMERIC =
	      "SELECT observation_uid \"observationUid\",  obs_value_numeric_seq \"obsValueNumericSeq\", "+
	      " high_range \"highRange\", low_range \"lowRange\", comparator_cd_1 \"comparatorCd1\", numeric_value_1 \"numericValue1\","+
	     " numeric_value_2 \"numericValue2\", numeric_unit_cd \"numericUnitCd\",  separator_cd \"separatorCd\" FROM " +
	      DataTables.OBS_VALUE_NUMERIC_TABLE +  " WHERE observation_uid = ?";
	 private Collection<Object>  selectObsValueNumerics (long observationUid) throws NEDSSSystemException
	    {
	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ObsValueNumericDT obsValueNumeric= new ObsValueNumericDT();
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        try
	        {
	            dbConnection = getConnection();
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection " +
	                        "for selectPersonNames " , nsex);
	            throw new NEDSSSystemException( nsex.getMessage(), nsex);
	        }
	        try
	        {
	            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_NUMERIC);
	            preparedStmt.setLong(1, observationUid);
	            resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            ArrayList<Object> itemList= new ArrayList<Object> ();
	            itemList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueNumeric.getClass(), itemList);
	            logger.debug("return obsValueNumeric collection");
	            return itemList;
	        }
	        catch(SQLException se)
	        {
	            logger.fatal("SQLException while selecting obsValueNumeric collection; uid = " + observationUid, se);
	            throw new NEDSSDAOSysException( se.getMessage(), se);
	        }
	        catch(Exception ex)
	        {
	            logger.fatal("Exception while selection observation ; uid = " + observationUid, ex);
	            throw new NEDSSSystemException(ex.toString(), ex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	    }
	 public static final String SELECT_OBS_VALUE_CODED = "SELECT observation_uid \"observationUid\", code \"code\",  code_system_cd \"codeSystemCd\", "+
	      "code_system_desc_txt \"codeSystemDescTxt\", code_version \"codeVersion\", display_name \"displayName\", "+
	     " original_txt \"originalTxt\", alt_cd \"altCd\",  alt_cd_desc_txt \"altCdDescTxt\", alt_cd_system_cd \"altCdSystemCd\"," +
	     " alt_cd_system_desc_txt \"altCdSystemDescTxt\", code_derived_ind \"codeDerivedInd\" FROM " +
	      DataTables.OBS_VALUE_CODED_TABLE + " WHERE observation_uid = ?";
	 private Collection<Object>  selectObsValueCodeds (long observationUID) throws NEDSSSystemException
	    {

	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ObsValueCodedDT obsValueCoded = new ObsValueCodedDT();

	        try
	        {
	            dbConnection = getConnection();
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection  for selectObsValueCodeds " , nsex);
	            throw new NEDSSSystemException( nsex.getMessage(), nsex);
	        }
	        try
	        {
	            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_CODED);
	            preparedStmt.setLong(1, observationUID);
	            resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            ArrayList<Object> otList = new ArrayList<Object> ();
	            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueCoded.getClass(), otList);

	            logger.debug("return OBS_VALUE_CODED collection");
	            return otList;
	        }
	        catch(SQLException se)
	        {
	        	logger.fatal("Error in result set handling while selecting obsValueCoded."+se.getMessage(), se);
	            throw new NEDSSDAOSysException("SQLException while selecting " +
	                            "OBS_VALUE_CODED collection; id = " + observationUID + " :\n" + se.getMessage(), se);
	        }
	        catch(ResultSetUtilsException reuex)
	        {
	            logger.fatal("Error in result set handling while selecting obsValueCoded."+reuex.getMessage(), reuex);
	            throw new NEDSSDAOSysException(reuex.toString(), reuex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	    }//end of selecting obsValueCoded

	 public static final String SELECT_OBS_VALUE_DATE = "SELECT observation_uid \"observationUid\", "+
	      " obs_value_date_seq \"obsValueDateSeq\", duration_amt \"durationAmt\",   duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "+
	     " to_time \"toTime\" FROM "+    DataTables.OBS_VALUE_DATE_TABLE + " WHERE observation_uid = ?";
	 private Collection<Object>  selectObsValueDates (long observationUID) throws NEDSSSystemException
	    {

	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ObsValueDateDT obsValueDate = new ObsValueDateDT();

	        try
	        {
	            dbConnection = getConnection();
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection for selectObsValueDates " , nsex);
	            throw new NEDSSSystemException( nsex.getMessage(), nsex);
	        }

	        try
	        {
	            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_DATE);
	            preparedStmt.setLong(1, observationUID);
	            resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            ArrayList<Object> otList = new ArrayList<Object> ();

	            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueDate.getClass(), otList);
	            logger.debug("return OBS_VALUE_DATE collection");
	            return otList;
	        }
	        catch(SQLException se)
	        {
	            throw new NEDSSDAOSysException("SQLException while selecting " +
	                            "OBS_VALUE_DATE collection; id = " + observationUID + " :\n" + se.getMessage(), se);
	        }
	        catch(ResultSetUtilsException reuex)
	        {
	            logger.fatal("Error in result set handling while selecting obsValueDate."+reuex.getMessage(), reuex);
	            throw new NEDSSDAOSysException(reuex.toString(), reuex);
	        }
	        finally
	        {
	            closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }
	    }//end of selecting obsValueDate

	 private static final String SELECT_BY_ACT_UID=  "SELECT subject_entity_uid \"SubjectEntityUid\", act_uid \"ActUid\", subject_class_cd \"SubjectClassCd\", " +
	 "act_class_cd \"ActClassCd\", add_reason_cd \"AddReasonCd\", add_time \"AddTime\", add_user_id \"AddUserId\", awareness_cd \"AwarenessCd\", " +
	 "awareness_desc_txt \"AwarenessDescTxt\", cd \"Cd\", duration_amt \"DurationAmt\", duration_unit_cd \"DurationUnitCd\", from_time \"FromTime\", " +
	 "last_chg_reason_cd \"LastChgReasonCd\", last_chg_time \"LastChgTime\", last_chg_user_id \"LastChgUserId\", record_status_cd \"RecordStatusCd\", " +
	 "record_status_time \"RecordStatusTime\", role_seq \"RoleSeq\", status_cd \"StatusCd\", status_time \"StatusTime\", to_time \"ToTime\", " +
	 "type_cd \"TypeCd\", type_desc_txt \"TypeDescTxt\", user_affiliation_txt \"UserAffiliationTxt\" from Participation where act_uid = ?";

	 private Collection<Object>  selectParticipations(Long uid)  throws NEDSSDAOSysException,    NEDSSSystemException
	 {

//		 PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_ACT_UID);
		 ResultSet resultSet = null;
		 ResultSetMetaData resultSetMetaData = null;
		 ResultSetUtils resultSetUtils = new ResultSetUtils();
		 ParticipationDT dt = new ParticipationDT();
		 PreparedStatement preparedStmt = null;
		 Connection dbConnection = null;

		 try
		 {
			 dbConnection = getConnection();
			 preparedStmt = dbConnection.prepareStatement(SELECT_BY_ACT_UID);
			 preparedStmt.setLong(1, uid.longValue());
			 resultSet = preparedStmt.executeQuery();
			 resultSetMetaData = resultSet.getMetaData();

			 ArrayList<Object> retval = new ArrayList<Object> ();
			 retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
					 resultSetMetaData,
					 dt.getClass(),
					 retval);
			 return retval;
		 }
		 catch (SQLException se)
		 {
			 logger.fatal("SQLException  = "+se.getMessage(), se);
			 throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
					 se.getMessage(), se);
		 }
		 catch (ResultSetUtilsException rsuex)
		 {
			 logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
			 throw new NEDSSSystemException("Error in result set handling while populate ParticipationDTs.", rsuex);
		 }
		 finally
		 {
			 closeResultSet(resultSet);
			 closeStatement(preparedStmt);
			 releaseConnection(dbConnection);
		 }
	 }
	 
	 private final String SELECT_LDF =  "SELECT "+
	    " sf.ldf_uid \"ldfUid\", "+
	    " sf.business_object_nm  \"businessObjNm\", "+
	    " sf.add_time    \"addTime\", "+
	    " sf.business_object_uid  \"businessObjUid\", "+
	    " sf.last_chg_time \"lastChgTime\", "+
	    " sf.ldf_value    \"ldfValue\", "+
	    " sf.version_ctrl_nbr \"versionCtrlNbr\",  "+
	    " sdfmd.field_size \"fieldSize\", "+
	    " sdfmd.data_type  \"dataType\", "+
	     " sdfmd.code_set_nm \"codeSetNm\" "+
	    " from State_defined_field_data sf, "+
	    " state_defined_field_metadata sdfmd "+
	    " where  sf.ldf_uid = sdfmd.ldf_uid "+
	    " and sf.business_object_uid = ?    order by sf.ldf_uid ";
	 @SuppressWarnings("unchecked")
	private Collection<Object>  getLDFCollection(Long busObjectUid, String conditionCode) throws NEDSSSystemException {

	        StateDefinedFieldDataDT stateDefinedFieldDataDT = new StateDefinedFieldDataDT();
	        ArrayList<Object> pList = new ArrayList<Object> ();
	        try
	        {
	            StringBuffer query = new StringBuffer(SELECT_LDF);
	  
	            pList.add(busObjectUid);
	           // if (conditionCode != null)
	            //  pList.add(conditionCode); //set the cond code if it is not null
	            pList = (ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldDataDT, pList,query.toString(), NEDSSConstants.SELECT);

	        }
	        catch(Exception ex)
	        {
	        	logger.fatal("Exception while selecting  StateDefinedField id =   = "+ex.getMessage(), ex);
	            throw new NEDSSSystemException( ex.getMessage(), ex);
	        }
	        return pList;
	    }//end of selecting place

	 
	 public void updatePublicHealthCaseStatus (String oldRecordStatusCd, String newRecordStatusCode)throws  NEDSSSystemException
	    {
			
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_ALL_PHC_STATUS="+ UPDATE_ALL_PHC_STATUS);
			dbConnection = getConnection();
			try{
					preparedStmt = dbConnection.prepareStatement( UPDATE_ALL_PHC_STATUS);
					int i = 1;
				    preparedStmt.setString(i++, oldRecordStatusCd); 
					 preparedStmt.setString(i++, newRecordStatusCode); 
				    resultCount = preparedStmt.executeUpdate();
					logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				    logger.fatal("SQLException while updatePublicHealthCaseStatus: oldRecordStatusCd :"+oldRecordStatusCd+":newRecordStatusCode"+ newRecordStatusCode, sqlex);
				    throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
			}
			catch(Exception ex)
			{
			    logger.fatal("Exception while updatePublicHealthCaseStatus: oldRecordStatusCd :"+oldRecordStatusCd+":newRecordStatusCode"+ newRecordStatusCode, ex);
					    throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				  closeStatement(preparedStmt);
				  releaseConnection(dbConnection);
			}
	    }//end of update method
	 
	 
		public  int getPublicHealthDTCount(String cd, String coversionType){
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			 int numberOfCasesNeedsToBeTransferred = 0;
			int resultCount = 0;
			ResultSet resultSet=null;
			
			dbConnection = getConnection();
			try{
					String query = "";
					
					if(PortPageUtil.PORT_LEGACY.equals(coversionType)){
						query = COUNT_PHC_LEGACY_CASES;
					}else{
						query = COUNT_PHC_PAGEBUILDER_CASES;
					}
					logger.debug(" Query to count PHC cases: "+ query);
					
					preparedStmt = dbConnection.prepareStatement( query);
					int i = 1;
				    preparedStmt.setString(i++, cd); 
					
					 resultSet = preparedStmt.executeQuery();
					  if (resultSet.next())
			            {
						  numberOfCasesNeedsToBeTransferred = resultSet.getInt(1);
			            }
					logger.debug("resultCount is " +numberOfCasesNeedsToBeTransferred);
			}
			catch(SQLException sqlex)
			{
				    logger.fatal("SQLException while getPublicHealthDTCount: cd :"+cd, sqlex);
				    throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
			}
			catch(Exception ex)
			{
			    logger.fatal("Exception while getPublicHealthDTCount: cd :"+cd+":newRecordStatusCode", ex);
					    throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				  closeResultSet(resultSet);
				  closeStatement(preparedStmt);
				  releaseConnection(dbConnection);
			}
			return numberOfCasesNeedsToBeTransferred;
	    }//end
		
		
		
		/*public static final String DELETE_METADATA =
		      "update State_defined_field_metadata "
		      + " set active_ind = 'N', record_status_cd = 'LDF_UPDATE' ,  record_status_time= ? WHERE condition_cd=?";
		public void removeMetaDataByConditionCd(String cd, Timestamp recordStatusTime)
				throws NEDSSSystemException {

				Connection dbConnection = null;
				PreparedStatement preparedStmt = null;
				int resultCount = 0;

				try {
					dbConnection = getConnection();
				} catch (NEDSSSystemException nsex) {
					//logger.debug("SQLException while obtaining database connection for deleting MetaDatas = " + nsex);
					throw new NEDSSSystemException(nsex.getMessage(), nsex);
				}
				try {
					preparedStmt =
						dbConnection.prepareStatement(DELETE_METADATA);
					preparedStmt.setTimestamp(1,recordStatusTime);
					preparedStmt.setString(2,cd);
					resultCount = preparedStmt.executeUpdate();

				} catch (SQLException sex) {
					logger.fatal("SQLException  = "+sex.getMessage(), sex);
					throw new NEDSSSystemException(sex.getMessage(), sex);
				} finally {
					closeStatement(preparedStmt);
					releaseConnection(dbConnection);
				}

			}*/ //end of removing places
		//The condition code update is called from the batch process at the end when all cases have been updated.
		//Added for 4.6: parent_is_cd=null, indent_level_nbr=1
		public static final String UPDATE_CONDITION_CODE_SQL =
		      "update "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code"
		      + " set investigation_form_cd=?, port_req_ind_cd='F', parent_is_cd=null, indent_level_nbr=1, nnd_entity_identifier=?  WHERE condition_cd=?";
		public void updateConditionCode(String cd, WaTemplateDT waTemplateDT, Timestamp recordStatusTime)
				throws NEDSSSystemException, NEDSSSystemException {

				Connection dbConnection = null; 
				PreparedStatement preparedStmt = null;
				int resultCount = 0;
				String codeSql="";	
				
				try {
					codeSql = UPDATE_CONDITION_CODE_SQL;
										
					ArrayList<Object>  paramList = new ArrayList<Object> ();
					paramList.add(waTemplateDT.getFormCd());
					if(waTemplateDT.getNndEntityIdentifier()!=null)
						paramList.add(waTemplateDT.getNndEntityIdentifier());
					else
						paramList.add(waTemplateDT.getMessageId());
					paramList.add(cd);
					preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE,NEDSSConstants.SRT);
				} catch (Exception sqlException) {
					logger.error("Exception while updating PamConversionLegacyDAO.updateConditionCode: ERROR = " + sqlException.getMessage(), sqlException);
					throw new NEDSSSystemException(sqlException.getMessage(), sqlException);
				} finally {
					closeStatement(preparedStmt);
					releaseConnection(dbConnection);
				}

			} //end of removing places

		private static final String FIND_WA_TEMPLATE = "SELECT tem.wa_template_uid \"waTemplateUid\", tem.template_type \"templateType\", " 
			+ "tem.xml_payload \"xmlPayload\", tem.publish_version_nbr \"publishVersionNbr\",   "
			+ "tem.form_cd \"formCd\", PAGE.condition_cd \"conditionCd\",  "
			+ "tem.bus_obj_type \"busObjType\", tem.datamart_nm \"dataMartNm\",  "
			+ "tem.last_chg_user_id \"lastChgUserId\",	tem.last_chg_time \"lastChgTime\",  "
			+ "tem.record_status_cd \"recStatusCd\", tem.record_status_time \"recStatusTime\", "
			+ "tem.desc_txt \"descTxt\",tem.publish_ind_cd \"publishIndCd\", "
			+ "tem.publish_version_nbr \"version\", nnd_entity_identifier \"nndEntityIdentifier\" "
			+ "FROM WA_template tem, PAGE_COND_MAPPING PAGE WHERE    "
			+ "PAGE.WA_TEMPLATE_UID=tem.WA_TEMPLATE_UID  "
			+ "AND PAGE.condition_cd=?";

		@SuppressWarnings("unchecked")
		public WaTemplateDT getWaTemplateByConditionCd(String cdToBeTranslated) 
		throws NEDSSDAOSysException, NEDSSSystemException {

			WaTemplateDT waTemplateDT;
			try {
				waTemplateDT = new WaTemplateDT();
				ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(cdToBeTranslated);
				try {
					paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
							paramList, FIND_WA_TEMPLATE, NEDSSConstants.SELECT);
					if (paramList.size() > 0)
						return (WaTemplateDT) paramList.get(0);
				} catch (Exception ex) {
					logger.fatal("Exception in PamConversionLegacyDAO.getWaTemplateByConditionCd: ERROR = " + ex);
					throw new NEDSSSystemException(ex.toString(), ex);
				}
				if (waTemplateDT != null && waTemplateDT.getConditionCd() != null) {
					CachedDropDownValues cdv = new CachedDropDownValues();
					waTemplateDT.setConditionCdDesc(cdv.getConditionDesc(waTemplateDT.getConditionCd()));
				}
			} catch (Exception exception) {
				logger.fatal("Exception in PamConversionLegacyDAO.getWaTemplateByConditionCd: ERROR = " + exception.getMessage(), exception);
				throw new NEDSSSystemException(exception.toString(), exception);
			}
			return waTemplateDT;
		}
		
		public PersonVO getPatientVO(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) {
			
			Collection<Object> subjParticipationColl = selectSubjOfPHCParticipation(publicHealthCaseUid);
			Iterator<Object> parIter = subjParticipationColl.iterator();
			ParticipationDT subjParticipation = (ParticipationDT) parIter.next(); 
			try {
				PersonVO patientVO = getPerson(subjParticipation.getSubjectEntityUid(), nbsSecurityObj);
				if (patientVO != null) { //get Patient LDFs for NBSCentral 9370 (12-15-2016)
					PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
					//get any LDF's associated with the patient
					try {
						Collection<Object>  patientLDFCollection = pamConversionLegacyDAO.getLDFCollection(patientVO.getThePersonDT().getPersonUid(), null);
						if (patientLDFCollection != null && !patientLDFCollection.isEmpty())
							patientVO.setTheStateDefinedFieldDataDTCollection(patientLDFCollection);
					} catch (EJBException e) {
						logger.error("Error getting LDFs for the patient"+e.getMessage(), e);
					}
				}
				return(patientVO);
			} catch (EJBException e) {
				logger.error("Error finding patient for Public Health Case "+e.getMessage(), e);
			}
			return null; 
		}
		 /**
		  * selectSubjOfPHCParticipation - Select the Patient participation associated with the case.
		  * @param public_health_case_uid
		  * @return Participation for subject of PHC
		  * @throws NEDSSDAOSysException
		  * @throws NEDSSSystemException
		  */
		 private static final String SELECT_BY_ACT_TYPE_UID=  "SELECT subject_entity_uid \"SubjectEntityUid\", act_uid \"ActUid\", subject_class_cd \"SubjectClassCd\", " +
				 "act_class_cd \"ActClassCd\", add_reason_cd \"AddReasonCd\", add_time \"AddTime\", add_user_id \"AddUserId\", awareness_cd \"AwarenessCd\", " +
				 "awareness_desc_txt \"AwarenessDescTxt\", cd \"Cd\", duration_amt \"DurationAmt\", duration_unit_cd \"DurationUnitCd\", from_time \"FromTime\", " +
				 "last_chg_reason_cd \"LastChgReasonCd\", last_chg_time \"LastChgTime\", last_chg_user_id \"LastChgUserId\", record_status_cd \"RecordStatusCd\", " +
				 "record_status_time \"RecordStatusTime\", role_seq \"RoleSeq\", status_cd \"StatusCd\", status_time \"StatusTime\", to_time \"ToTime\", " +
				 "type_cd \"TypeCd\", type_desc_txt \"TypeDescTxt\", user_affiliation_txt \"UserAffiliationTxt\" from Participation where type_cd = 'SubjOfPHC' and act_uid = ?";

				 private Collection<Object>  selectSubjOfPHCParticipation(Long uid)  throws NEDSSDAOSysException,    NEDSSSystemException
				 {

					 ResultSet resultSet = null;
					 ResultSetMetaData resultSetMetaData = null;
					 ResultSetUtils resultSetUtils = new ResultSetUtils();
					 ParticipationDT dt = new ParticipationDT();
					 PreparedStatement preparedStmt = null;
					 Connection dbConnection = null;

					 try
					 {
						 dbConnection = getConnection();
						 preparedStmt = dbConnection.prepareStatement(SELECT_BY_ACT_TYPE_UID);
						 preparedStmt.setLong(1, uid.longValue());
						 resultSet = preparedStmt.executeQuery();
						 resultSetMetaData = resultSet.getMetaData();

						 ArrayList<Object> retval = new ArrayList<Object> ();
						 retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
								 resultSetMetaData,
								 dt.getClass(),
								 retval);
						 return retval;
					 }
					 catch (SQLException se)
					 {
						 logger.fatal("SQLException PamConversionLegacy selectSubjOfPHCParticipation Error: SQLException while selecting  = "+se.getMessage(), se);
						 throw new NEDSSDAOSysException("PamConversionLegacy selectSubjOfPHCParticipation Error: SQLException while selecting \n" +
								 se.getMessage(), se);
					 }
					 catch (ResultSetUtilsException rsuex)
					 {
						 logger.fatal("ResultSetUtilsException PamConversionLegacy selectSubjOfPHCParticipation Error in result set handling while populate ParticipationDTs.  = "+rsuex.getMessage(), rsuex);
						 throw new NEDSSSystemException("PamConversionLegacy selectSubjOfPHCParticipation Error in result set handling while populate ParticipationDTs.", rsuex);
					 }
					 finally
					 {
						 closeResultSet(resultSet);
						 closeStatement(preparedStmt);
						 releaseConnection(dbConnection);
					 }
				 }		
			/**
			 * Find and update the person if anything set to itNew or itDirty
			 * @param personVO
			 * @param nbsSecurityObj
			 * @return
			 * @throws javax.ejb.EJBException
			 * @throws NEDSSConcurrentDataException
			 */
			public PersonVO getPerson(Long personUid, NBSSecurityObj nbsSecurityObj)
					throws javax.ejb.EJBException {
				Long personUID = new Long(-1);
				logger.debug("in PamConversionLegacyDAO.getPerson()");
				try {
					Person person = null;
					NedssUtils nedssUtils = new NedssUtils();
					Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
					logger.debug("PersonEJB lookup = " + obj.toString());
					PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,
						PersonHome.class);
					person = home.findByPrimaryKey(personUid);
					logger.debug("returning from PamConversionLegacyDAO.getPerson()");
					return(person.getPersonVO());
				} catch (Exception e) {
					logger.fatal("PamConversionLegacyDAO.getPerson exception e = " + "\n", e);
					logger.fatal("getPerson throwing generic exception");
					throw new javax.ejb.EJBException(e.toString());
				}
		}
									 
		/**
		 * Find and update the person if anything set to itNew or itDirty
		 * @param personVO
		 * @param nbsSecurityObj
		 * @return
		 * @throws javax.ejb.EJBException
		 * @throws NEDSSConcurrentDataException
		 */
		public Long setPerson(PersonVO personVO, NBSSecurityObj nbsSecurityObj)
				throws javax.ejb.EJBException, NEDSSConcurrentDataException {
			Long personUID = new Long(-1);
	
			try {
				Person person = null;
				NedssUtils nedssUtils = new NedssUtils();
				Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
				logger.debug("PersonEJB lookup = " + obj.toString());
				PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,
					PersonHome.class);
				person = home.findByPrimaryKey(personVO.getThePersonDT()
						.getPersonUid());
				person.setPersonVO(personVO);
				personUID = personVO.getThePersonDT().getPersonUid();
				logger.debug(" EntityControllerEJB.setPerson() Person Updated");

			} catch (NEDSSConcurrentDataException ex) {
				logger.fatal("The entity cannot be updated as concurrent access is not allowed!"+ex.getMessage(), ex);
				logger.error("PamConversionLegacy.setPerson throwing NEDSSConcurrentDataException");
				throw new NEDSSConcurrentDataException(
					"Concurrent access occurred in PersonEJB : "
							+ ex.toString());
			} catch (Exception e) {
				if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
					logger.fatal("PamConversionLegacy.setPerson throwing NEDSSConcurrentDataException"+e.getMessage(), e);
					throw new NEDSSConcurrentDataException(e.toString());
			} else {
				logger.fatal("PamConversionLegacy.setPerson exception e = " + e.getMessage()
						+ "\n", e);
				logger.error("SetPerson throwing generic exception");
				throw new javax.ejb.EJBException(e.toString());
			}
		}
		return personUID;
	}		
		
		
	/**
	 * Deletes unwanted mapping after successful legacy to PageBuilder conversion.
	 * Mapping deletion helps to correct back translated master message.
	 * 
	 * @param conditionCd
	 * @return
	 */
	public boolean postConversionMappingCorrections(String conditionCd){
		logger.debug("conditionCd: "+conditionCd);
		Connection conn = null;
		CallableStatement cs = null;
		boolean success = false;
		try{
			conn = getConnection();
			cs = conn.prepareCall("{call PostConversionMappingUpdate(?)}");
	        cs.setString(1, conditionCd);
	        cs.execute();
	        success = true;
		}catch (Exception ex) {
			logger.fatal("Error while correcting Metadata for conditionCd: "+conditionCd+", Exception: "+ex.getMessage(),ex);
			throw new NEDSSDAOSysException(ex.toString());
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
		return success;
	}
}
