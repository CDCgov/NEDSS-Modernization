/**
 * Title: ObservationDAOImpl.java
 * Description:This is the implementation of NEDSSDAOInterface for the
*               Observation value object in the Observation entity bean.
*               This class encapsulates all the JDBC calls made by the ObservationEJB
*               for a Observation object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of ObservationEJB is
*               implemented here.
 * Copyright: Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author Pradeep Sharma & NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.act.observation.ejb.dao;


import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportConditionSummaryVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ObservationDAOImpl extends DAOBase
{

   // private static final String ACT_UID = "ACTIVITY_UID";
    private long observationUID = -1;
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    //For logging
    static final LogUtils logger = new LogUtils(ObservationDAOImpl.class.getName());

    /**
      * This function is for ELR to match an observation in ODS to an observation
      * in MsgIn the following matching criteria
      * type_cd="FN" and root_extension_txt=current filler number
      * return: An ODS ObservationDT
      *         or null if does not find a match or find multiple match
      */
    public static final String ELR_MATCH_OBSERVATION = "Select ob.observation_uid "+
          "from observation ob WITH (NOLOCK), (Select act_uid from Act_id WITH (NOLOCK) where type_cd='FN' "+
          "and root_extension_txt = ?)a , (select obs.observation_uid "+
          "from observation obs WITH (NOLOCK), participation par WITH (NOLOCK),  entity_id eid "+
          "where obs.observation_uid = par.ACT_UID "+
          "and par.SUBJECT_ENTITY_UID = eid.ENTITY_UID "+
          "and par.ACT_CLASS_CD = 'OBS' "+
          "and par.SUBJECT_CLASS_CD = 'ORG' "+
          "and par.TYPE_CD = 'AUT' "+
          "and par.add_reason_cd = ? "+
          "and eid.ROOT_EXTENSION_TXT = ? "+
          "and eid.ASSIGNING_AUTHORITY_CD = 'CLIA' "+
          "and eid.TYPE_CD = 'FI') b "+
          "where ob.observation_uid = a.act_uid "+
          "and ob.OBSERVATION_UID = b.observation_uid "+
          "and ob.obs_domain_cd_st_1='Order' and ob.electronic_ind = 'Y'" +
          "and ob.cd = ? and ob.effective_from_time = ?";
     
    private static final String SELECT_OBSERVATION = "SELECT observation_uid \"observationUid\", "
             + "activity_duration_amt \"activityDurationAmt\", "
             + "activity_duration_unit_cd \"activityDurationUnitCd\", "
             + "activity_from_time \"activityFromTime\", "
             + "activity_to_time \"activityToTime\", " //5
             + "add_reason_cd \"addReasonCd\", "
             + "add_time \"addTime\", "
             + "add_user_id \"addUserId\", "
             + "cd \"cd\", "
             + "cd_desc_txt \"cdDescTxt\", " //10
             + "cd_system_Cd \"cdSystemCd\", "
             + "cd_system_desc_txt \"cdSystemDescTxt\", "//12
             + "confidentiality_cd \"confidentialityCd\", "
             + "confidentiality_desc_txt \"confidentialityDescTxt\", "
             + "derivation_exp \"derivationExp\", "  //15
             + "effective_duration_amt \"effectiveDurationAmt\", "
             + "effective_duration_unit_cd \"effectiveDurationUnitCd\", "
             + "effective_from_time \"effectiveFromTime\", "
             + "effective_to_time \"effectiveToTime\", "
             + "group_level_cd \"groupLevelCd\", "  //20
             + "jurisdiction_cd \"jurisdictionCd\", "
             + "lab_condition_cd \"labConditionCd\", "
             + "last_chg_reason_cd \"lastChgReasonCd\", "
             + "last_chg_time \"lastChgTime\", "
             + "last_chg_user_id \"lastChgUserId\", " //25
             + "local_id \"localId\", "
             + "method_cd \"methodCd\", "
             + "method_desc_txt \"methodDescTxt\", "
             + "obs_domain_cd \"obsDomainCd\", "
             //+ "org_access_permis \"orgAccessPermis\", "
             + "priority_cd \"priorityCd\", "  //30
             + "priority_desc_txt \"priorityDescTxt\", "
             //+ "prog_area_access_permis \"progAreaAccessPermis\", "
             + "prog_area_cd \"progAreaCd\", "
             + "record_status_cd \"recordStatusCd\", "
             + "record_status_time \"recordStatusTime\",  "
             + "repeat_nbr \"repeatNbr\", "  //35
             + "status_cd \"statusCd\", "
             + "status_time \"statusTime\", "
             + "subject_person_uid \"subjectPersonUid\", "
             + "target_site_cd \"targetSiteCd\", "
             + "target_site_desc_txt \"targetSiteDescTxt\", " //40
             + "txt \"txt\", "
             + "user_affiliation_txt \"userAffiliationTxt\", "
             + "electronic_ind \"electronicInd\", "
             + "ctrl_cd_display_form \"ctrlCdDisplayForm\", "
             + "ctrl_cd_user_defined_1 \"ctrlCdUserDefined1\", " //45
             + "ctrl_cd_user_defined_2 \"ctrlCdUserDefined2\", "
             + "ctrl_cd_user_defined_3 \"ctrlCdUserDefined3\", "
             + "ctrl_cd_user_defined_4 \"ctrlCdUserDefined4\", "
             + "value_cd \"valueCd\", "
             + "ynu_cd \"ynuCd\", "             //50
             + "pnu_cd \"pnuCd\", "
             + "obs_domain_cd_st_1 \"obsDomainCdSt1\", "
             + "program_jurisdiction_oid \"programJurisdictionOid\", "
             + "shared_ind \"sharedInd\", "
             + "version_ctrl_nbr \"versionCtrlNbr\", " //55
             + "alt_cd \"altCd\", " //56
             + "alt_cd_desc_txt \"altCdDescTxt\", " //57
             + "alt_cd_system_cd \"altCdSystemCd\", " //58
             + "alt_cd_system_desc_txt \"altCdSystemDescTxt\", " //59
             + "cd_derived_ind \"cdDerivedInd\", " //60
             + "cd_version \"cdVersion\", "
             + "rpt_to_state_time \"rptToStateTime\", " //61
             + "processing_decision_cd \"processingDecisionCd\",  " //62
             + "pregnant_ind_cd \"pregnantIndCd\",  " //63
             + "pregnant_week \"pregnantWeek\",  " //64
             + "processing_decision_txt \"processingDecisionTxt\"  " //65
             + "FROM "
             + DataTables.OBSERVATION_TABLE
             + " WITH (NOLOCK) WHERE observation_uid = ?";          //65

    private static final String INSERT_OBSERVATION = "INSERT INTO "
                 + DataTables.OBSERVATION_TABLE
                 + "(observation_uid, "                //1
                 + "activity_duration_amt, "       //2
                 + "activity_duration_unit_cd, "
                 + "activity_from_time, "
                 + "activity_to_time, "            //5
                 + "add_reason_cd, "
                 + "add_time, "
                 + "add_user_id, "
                 + "cd, "
                 + "cd_desc_txt, "               //10
                 + "cd_system_cd, "
                 + "cd_system_desc_txt, "
                 + "confidentiality_cd, "
                 + "confidentiality_desc_txt, "
                 + "ctrl_cd_display_form, "      //15
                 + "ctrl_cd_user_defined_1, "
                 + "ctrl_cd_user_defined_2, "
                 + "ctrl_cd_user_defined_3, "
                 + "ctrl_cd_user_defined_4, "
                 + "derivation_exp, "            //20
                 + "effective_duration_amt, "
                 + "effective_duration_unit_cd, "
                 + "effective_from_time, "
                 + "effective_to_time, "
                 + "electronic_ind, "            //25
                 + "group_level_cd, "
                 + "jurisdiction_cd, "
                 + "lab_condition_cd, "
                 + "last_chg_reason_cd, "
                 + "last_chg_time, "             //30
                 + "last_chg_user_id, "
                 + "local_id, "
                 + "method_cd, "
                 + "method_desc_txt, "
                 + "obs_domain_cd, "             //35
                 + "obs_domain_cd_st_1, "
                 + "pnu_cd,  "
                 + "priority_cd, "
                 + "priority_desc_txt, "
                 + "prog_area_cd, "              //40
                 + "record_status_cd, "
                 + "record_status_time, "
                 + "repeat_nbr, "
                 + "status_cd, "
                 + "status_time, "             //45
                 + "subject_person_uid, "
                 + "target_site_cd, "
                 + "target_site_desc_txt, "
                 + "txt, "
                 + "user_affiliation_txt, "    //50
                 + "value_cd, "
                 + "ynu_cd, "
                 + "program_jurisdiction_oid, "
                 + "shared_ind, "
                 + "version_ctrl_nbr, "       //55
                 + "alt_cd, "
                 + "alt_cd_desc_txt, "
                 + "alt_cd_system_cd, "
                 + "alt_cd_system_desc_txt, "
                 + "cd_derived_ind, "         // 60
                 + "cd_version, "
                 + "rpt_to_state_time, "
                 + "processing_decision_cd, "
                 + "processing_decision_txt, "
                 + "pregnant_ind_cd, "
                 + "pregnant_week) "          // 65
                 + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                           "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                           "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                           "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                           "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                           "?,?,?,?,?,?,?,?, ?, ?, ?)";
     
    private static final String UPDATE_OBSERVATION = "UPDATE "   + DataTables.OBSERVATION_TABLE
             + " set activity_duration_amt = ?, " //1
             + "activity_duration_unit_cd = ?, "  //2
             + "activity_from_time = ?, "         //3
             + "activity_to_time = ?, "           //4
             + "add_reason_cd = ?, "              //5
             + "add_time = ?, "                   //6
             + "add_user_id = ?, "                //7
             + "cd = ?, "                         //8
             + "cd_desc_txt = ?, "                //9
             + "cd_system_cd = ?, "               //10
             + "cd_system_desc_txt = ?, "         //11
             + "confidentiality_cd = ?, "         //12
             + "confidentiality_desc_txt = ?, "   //13
             + "derivation_exp = ?, "             //14
             + "effective_duration_amt = ?, "     //15
             + "effective_duration_unit_cd = ?, " //16
             + "effective_from_time = ?, "        //17
             + "effective_to_time = ?, "          //18
             + "group_level_cd = ?, "             //19
             + "jurisdiction_cd = ?, "            //20
             + "lab_condition_cd = ?, "           //21
             + "last_chg_reason_cd = ?, "         //22
             + "last_chg_time = ?, "              //23
             + "last_chg_user_id = ?, "           //24
             + "method_cd = ?, "                  //25
             + "method_desc_txt = ?, "            //26
             + "obs_domain_cd = ?, "              //27
             //+ "org_access_permis = ?, "
             + "priority_cd = ?, "                //28
             + "priority_desc_txt = ?, "          //29
             //+ "prog_area_access_permis = ?, "
             + "prog_area_cd = ?, "               //30
             + "record_status_cd = ?, "           //31
             + "record_status_time = ?,"          //32
             + " repeat_nbr = ?, "                //33
             + "status_cd = ?, "                  //34
             + "status_time = ?, "                //35
             + "target_site_cd = ?, "             //36
             + "target_site_desc_txt = ?, "       //37
             + "txt = ?, "                        //38
             + "user_affiliation_txt = ?, "       //39
             + "electronic_ind = ?, "             //40
             + "ctrl_cd_display_form = ?, "       //41
             + "ctrl_cd_user_defined_1 = ?, "     //42
             + "ctrl_cd_user_defined_2 = ?, "     //43
             + "ctrl_cd_user_defined_3 = ?, "     //44
             + "ctrl_cd_user_defined_4 = ?, "     //45
             + "value_cd = ?, "                   //46
             + "ynu_cd = ?, "                     //47
             + "pnu_cd = ?, "                     //48
             + "obs_domain_cd_st_1 = ?, "         //49
             + "program_jurisdiction_oid = ?, "   //50
             + "shared_ind = ?, "                 //51
             + "version_ctrl_nbr = ?, "            //52
             + "alt_cd = ?, "                      //53
             + "alt_cd_desc_txt = ?, "             //54
             + "alt_cd_system_cd = ?, "            //55
             + "alt_cd_system_desc_txt = ?, "      //56
             + "cd_derived_ind = ?, "              //57
             + "cd_version = ?, "                  //58
             + "rpt_to_state_time = ?, "           //59
             + "processing_decision_cd = ? ,"      //60
             + "pregnant_ind_cd = ?, "             //61
             + "pregnant_week = ?, "                 //62
             + "processing_decision_txt = ? "                 //63
             + "WHERE  observation_uid = ? "
             + "AND version_ctrl_nbr = ?";
    
	@SuppressWarnings("unchecked")
	public EDXEventProcessDT getEDXEventProcessDTBySourceId(String sourceId) {
		EDXEventProcessDT edxEventProcessDT = null;
		try {
			ArrayList<Object> inArrayList= new ArrayList<Object> ();
	        ArrayList<Object>  outArrayList= new ArrayList<Object> ();
	        ArrayList<Object> arrayList = new ArrayList<Object> ();
	        inArrayList.add(sourceId);//source_event_id
	        outArrayList.add(new Integer(java.sql.Types.BIGINT)); //edx_event_process_uid
	        outArrayList.add(new Integer(java.sql.Types.BIGINT)); //nbs_document_uid
	        outArrayList.add(new Integer(java.sql.Types.BIGINT)); //nbs_event_uid
	        outArrayList.add(java.sql.Types.VARCHAR);//source_event_id
	        outArrayList.add(java.sql.Types.VARCHAR); //doc_event_type_cd
	        outArrayList.add(new Integer(java.sql.Types.BIGINT)); //add_user_id
	        outArrayList.add(java.sql.Types.TIMESTAMP); //add_time

			String sQuery  = "{call GETEDXEVENTPROCESS_SP(?,?,?,?,?,?,?,?)}";
			arrayList = (ArrayList<Object> )callStoredProcedureMethod(sQuery,inArrayList,outArrayList);

			if(arrayList != null && arrayList.size() > 0)
			{
				edxEventProcessDT = new EDXEventProcessDT();
				edxEventProcessDT.seteDXEventProcessUid((Long)arrayList.get(0));
				edxEventProcessDT.setNbsDocumentUid((Long)arrayList.get(1));
				edxEventProcessDT.setNbsEventUid((Long)arrayList.get(2));
				edxEventProcessDT.setSourceEventId((String)arrayList.get(3));
				edxEventProcessDT.setDocEventTypeCd((String)arrayList.get(4));
				edxEventProcessDT.setAddUserId((Long)arrayList.get(5));
				edxEventProcessDT.setAddTime((Timestamp)arrayList.get(6));
			}
		} catch (Exception e) {
			logger.fatal("Error in fetching event process data for document "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString(), e);
		}
		return edxEventProcessDT;
	}


	public ObservationDT matchingObservation(EdxLabInformationDT edxLabInformationDT) throws NEDSSSystemException {
		ObservationVO observationVO = edxLabInformationDT.getRootObservationVO();
		String clia = edxLabInformationDT.getSendingFacilityClia();
		String fillerNumber = edxLabInformationDT.getFillerNumber();
		Timestamp specimenCollectionDate = observationVO.getTheObservationDT().getEffectiveFromTime(); 
		String orderedTestCode=observationVO.getTheObservationDT().getCd();
		if (fillerNumber == null || specimenCollectionDate==null || orderedTestCode==null || clia==null )
			return null; // no match

		// try to find a match
		Long matchedUID = null;
		try {
			
			PropertyUtil propertyUtil =PropertyUtil.getInstance();
			String numberOfYears = propertyUtil.getBackYearsForELRMatching();
			int numberOfNumberInt = Integer.parseInt(numberOfYears);
			
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(fillerNumber);
			inArrayList.add(clia);
			inArrayList.add(orderedTestCode);
			inArrayList.add(specimenCollectionDate);
			inArrayList.add(numberOfNumberInt);
			outArrayList.add(new Integer(java.sql.Types.BIGINT));

			String sQuery = "{call GetObservationMatch_SP(?,?,?,?,?,?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList, NEDSSConstants.MSGOUT);

			if (arrayList != null && arrayList.size() > 0) {

				matchedUID = (Long) arrayList.get(0);
			}
		} catch (Exception ex) {
			logger.fatal("An Exception occurred when matching an observation with fillerNbr=->" + fillerNumber, ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return (matchedUID == null) ? null : selectObservation(matchedUID.longValue());
	}

	/**
     * constructor
     */
    public ObservationDAOImpl()
    {
    }

    /**
     * @method    : create
     * @params    : java.lang.Object
     * @returnType: java.lang.Long
     */

    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        observationUID = insertObservation((ObservationDT)obj);
	        //logger.debug("(From inserting observation_table)Observation UID = " + observationUID);
	        return observationUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
     * @method      : store
     * @params      : java.lang.Object
     */

    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
    		updateObservation((ObservationDT) obj);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long observationUID) throws NEDSSSystemException
    {
    	try{
    		removeObservation(observationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long ObservationUID) throws NEDSSSystemException
    {
    	try{
	        ObservationDT observationDT = selectObservation(ObservationUID);
	        observationDT.setItNew(false);
	        observationDT.setItDirty(false);
	        return observationDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long observationUID) throws NEDSSSystemException
    {
    	try{
	        if (observationExists(observationUID))
	            return (new Long(observationUID));
	        else
	            logger.error("No observation found for this primary key :" + observationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
        return null;
    }


    protected boolean observationExists (long observationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_UID);
            //logger.debug("observationID = " + observationUID);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                observationUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an existing observation's uid in observation table-> " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an existing observation's uid in observation table-> " +
                            observationUID, ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }


    private long insertObservation(ObservationDT observationDT) throws  NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        logger.debug("INSERT_OBSERVATION2=" + INSERT_OBSERVATION);
        try
        {
            /**
             * Inserts into act table for observation
             */
            uidGen = new UidGeneratorHelper();
            observationUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_ACTIVITY);

            int i = 1;
            preparedStmt.setLong(i++, observationUID);
            preparedStmt.setString(i++, NEDSSConstants.OBSERVATION_CLASS_CODE);
            preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);

            resultCount = preparedStmt.executeUpdate();

        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while generating " + "uid for OBSERVATION_TABLE: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+DataTables.ACTIVITY_TABLE+" "+sex.toString(), sex);
        }
        catch (Exception ex)
        {
            logger.fatal("Error while inserting into ACTIVITY_TABLE, observationUID = " + observationUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        try
        {
            if (resultCount != 1)
            {
                logger.error("Error while inserting " + "uid into ACTIVITY_TABLE for a new observation, resultCount = "
                        + resultCount);
            }
            localUID = uidGen.getLocalID(UidClassCodes.OBSERVATION_CLASS_CODE);
            observationDT.setLocalId(localUID);

            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT_OBSERVATION);
            int i = 1;
            preparedStmt.setLong(i++, observationUID); // 1
            preparedStmt.setString(i++, observationDT.getActivityDurationAmt());
            preparedStmt.setString(i++, observationDT.getActivityDurationUnitCd());
            if (observationDT.getActivityFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getActivityFromTime());

            if (observationDT.getActivityToTime() == null) // 5
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getActivityToTime());

            preparedStmt.setString(i++, observationDT.getAddReasonCd());
            if (observationDT.getAddTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getAddTime());
            if (observationDT.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (observationDT.getAddUserId()).longValue());
            preparedStmt.setString(i++, observationDT.getCd());
            preparedStmt.setString(i++, observationDT.getCdDescTxt()); // 10
            preparedStmt.setString(i++, observationDT.getCdSystemCd());
            preparedStmt.setString(i++, observationDT.getCdSystemDescTxt());
            preparedStmt.setString(i++, observationDT.getConfidentialityCd());
            preparedStmt.setString(i++, observationDT.getConfidentialityDescTxt());
            preparedStmt.setString(i++, observationDT.getCtrlCdDisplayForm()); // 15
            preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined1());
            preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined2());
            preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined3());
            preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined4());

            if (observationDT.getDerivationExp() == null) // 20
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setInt(i++, (observationDT.getDerivationExp()).intValue());

            preparedStmt.setString(i++, observationDT.getEffectiveDurationAmt());
            preparedStmt.setString(i++, observationDT.getEffectiveDurationUnitCd());
            if (observationDT.getEffectiveFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getEffectiveFromTime());
            if (observationDT.getEffectiveToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getEffectiveToTime());

            if (observationDT.getElectronicInd() == null)
                preparedStmt.setString(i++, observationDT.getElectronicInd()); // 25
            else
                preparedStmt.setString(i++, observationDT.getElectronicInd().trim()); // 25
            preparedStmt.setString(i++, observationDT.getGroupLevelCd());
            preparedStmt.setString(i++, observationDT.getJurisdictionCd());
            preparedStmt.setString(i++, observationDT.getLabConditionCd());
            preparedStmt.setString(i++, observationDT.getLastChgReasonCd());
            if (observationDT.getLastChgTime() == null) // 30
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getLastChgTime());
            if (observationDT.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (observationDT.getLastChgUserId()).longValue());
            preparedStmt.setString(i++, localUID);
            preparedStmt.setString(i++, observationDT.getMethodCd());
            preparedStmt.setString(i++, observationDT.getMethodDescTxt());
            preparedStmt.setString(i++, observationDT.getObsDomainCd()); // 35
            preparedStmt.setString(i++, observationDT.getObsDomainCdSt1()); // 36
            if (observationDT.getPnuCd() == null)
                preparedStmt.setString(i++, observationDT.getPnuCd());
            else
                preparedStmt.setString(i++, observationDT.getPnuCd().trim());
            preparedStmt.setString(i++, observationDT.getPriorityCd());
            preparedStmt.setString(i++, observationDT.getPriorityDescTxt());
            preparedStmt.setString(i++, observationDT.getProgAreaCd()); // 40
            if (observationDT.getRecordStatusCd() == null)
                preparedStmt.setString(i++, observationDT.getRecordStatusCd());
            else
                preparedStmt.setString(i++, observationDT.getRecordStatusCd().trim());
            if (observationDT.getRecordStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getRecordStatusTime());
            if (observationDT.getRepeatNbr() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setInt(i++, (observationDT.getRepeatNbr()).intValue());
            if (observationDT.getStatusCd() == null)
                preparedStmt.setString(i++, observationDT.getStatusCd());
            else
                preparedStmt.setString(i++, observationDT.getStatusCd().trim());
            // preparedStmt.setString(i++, observationDT.getStatusCd());
            if (observationDT.getStatusTime() == null) // 45
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, observationDT.getStatusTime());
            if (observationDT.getSubjectPersonUid() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (observationDT.getSubjectPersonUid()).longValue());
            preparedStmt.setString(i++, observationDT.getTargetSiteCd());
            preparedStmt.setString(i++, observationDT.getTargetSiteDescTxt());
            preparedStmt.setString(i++, observationDT.getTxt());
            preparedStmt.setString(i++, observationDT.getUserAffiliationTxt()); // 50
            preparedStmt.setString(i++, observationDT.getValueCd());
            if (observationDT.getYnuCd() == null)
                preparedStmt.setString(i++, observationDT.getYnuCd());
            else
                preparedStmt.setString(i++, observationDT.getYnuCd().trim());
            if (observationDT.getProgramJurisdictionOid() == null)
                preparedStmt.setLong(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, observationDT.getProgramJurisdictionOid().longValue());
            // preparedStmt.setString(i++, observationDT.getSharedInd() == null
            // ? "T" : observationDT.getSharedInd());
            preparedStmt.setString(i++, observationDT.getSharedInd());
            // preparedStmt.setInt(i++, observationDT.getVersionCtrlNbr() ==
            // null ? 1 : observationDT.getVersionCtrlNbr().intValue()); //55
            preparedStmt.setInt(i++, observationDT.getVersionCtrlNbr().intValue()); // 55
            preparedStmt.setString(i++, observationDT.getAltCd());
            preparedStmt.setString(i++, observationDT.getAltCdDescTxt());
            preparedStmt.setString(i++, observationDT.getAltCdSystemCd());
            preparedStmt.setString(i++, observationDT.getAltCdSystemDescTxt());
            preparedStmt.setString(i++, observationDT.getCdDerivedInd());
            preparedStmt.setString(i++, observationDT.getCdVersion());
            preparedStmt.setTimestamp(i++, observationDT.getRptToStateTime());
            preparedStmt.setString(i++, observationDT.getProcessingDecisionCd());
            preparedStmt.setString(i++, observationDT.getProcessingDecisionTxt());
            

            preparedStmt.setString(i++, observationDT.getPregnantIndCd());
            if( observationDT.getPregnantWeek() == null )
            {
                preparedStmt.setNull(i++, Types.INTEGER);
            }
            else
            {
                preparedStmt.setInt(i++, observationDT.getPregnantWeek());
            }

            resultCount = preparedStmt.executeUpdate();
            observationDT.setItNew(false);
            observationDT.setItDirty(false);
            observationDT.setItDelete(false);
            // logger.debug("done insert observation! observationUID = " +
            // observationUID);
            return observationUID;
        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while inserting " + "observation into OBSERVATION_TABLE: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+DataTables.OBSERVATION_TABLE+" "+sex.toString(), sex);
        }
        catch (Exception ex)
        {
            logger.fatal("Error while inserting into OBSERVATION_TABLE, id = " + observationUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of inserting observation


    private void updateObservation (ObservationDT observationDT) throws
              NEDSSSystemException,
              NEDSSConcurrentDataException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            // Updates observation table
            if (observationDT != null && observationDT.getObservationUid() != null)
            {

                dbConnection = getConnection();

                preparedStmt = dbConnection.prepareStatement(UPDATE_OBSERVATION);

                int i = 1;

                preparedStmt.setString(i++, observationDT.getActivityDurationAmt()); //1
                preparedStmt.setString(i++, observationDT.getActivityDurationUnitCd()); //2
                if (observationDT.getActivityFromTime() == null) //3
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getActivityFromTime());
                if (observationDT.getActivityToTime() == null)  //4
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getActivityToTime());
                preparedStmt.setString(i++, observationDT.getAddReasonCd()); //5
                if (observationDT.getAddTime() == null)  //6
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getAddTime());
                if(observationDT.getAddUserId() == null) //7
                     preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (observationDT.getAddUserId()).longValue());
                preparedStmt.setString(i++, observationDT.getCd()); //8
                preparedStmt.setString(i++, observationDT.getCdDescTxt()); //9
                preparedStmt.setString(i++, observationDT.getCdSystemCd());//10
                preparedStmt.setString(i++, observationDT.getCdSystemDescTxt()); //11
                preparedStmt.setString(i++, observationDT.getConfidentialityCd());//12
                preparedStmt.setString(i++, observationDT.getConfidentialityDescTxt()); //13
                if(observationDT.getDerivationExp() == null) //14
                     preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (observationDT.getDerivationExp()).intValue());
                preparedStmt.setString(i++, observationDT.getEffectiveDurationAmt()); //15
                preparedStmt.setString(i++, observationDT.getEffectiveDurationUnitCd()); //16
                if (observationDT.getEffectiveFromTime() == null) //17
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getEffectiveFromTime());
                if (observationDT.getEffectiveToTime() == null) //18
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getEffectiveToTime());

                preparedStmt.setString(i++, observationDT.getGroupLevelCd()); //19
                preparedStmt.setString(i++, observationDT.getJurisdictionCd()); //20
                preparedStmt.setString(i++, observationDT.getLabConditionCd()); //21
                preparedStmt.setString(i++, observationDT.getLastChgReasonCd()); //22
                if (observationDT.getLastChgTime() == null)  //23
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getLastChgTime());
                if(observationDT.getLastChgUserId() == null) //24
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (observationDT.getLastChgUserId()).longValue());
                preparedStmt.setString(i++, observationDT.getMethodCd()); //25
                preparedStmt.setString(i++, observationDT.getMethodDescTxt()); //26
                preparedStmt.setString(i++, observationDT.getObsDomainCd()); //27
                //preparedStmt.setString(i++, observationDT.getOrgAccessPermis());
                preparedStmt.setString(i++, observationDT.getPriorityCd()); //28
                preparedStmt.setString(i++, observationDT.getPriorityDescTxt()); //29
                //preparedStmt.setString(i++, observationDT.getProgAreaAccessPermis());
                preparedStmt.setString(i++, observationDT.getProgAreaCd()); //30
                preparedStmt.setString(i++, observationDT.getRecordStatusCd());//31
                if(observationDT.getRecordStatusTime() == null) //32
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, observationDT.getRecordStatusTime());
                if(observationDT.getRepeatNbr() == null)//33
                     preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (observationDT.getRepeatNbr()).intValue());
                if (observationDT.getStatusCd() == null) //34
                {
                  preparedStmt.setString(i++, observationDT.getStatusCd());
                }
                else
                    preparedStmt.setString(i++, observationDT.getStatusCd().trim());
                if (observationDT.getStatusTime() == null) //35
                {
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, observationDT.getStatusTime());
                preparedStmt.setString(i++, observationDT.getTargetSiteCd()); //36
                preparedStmt.setString(i++, observationDT.getTargetSiteDescTxt());//37
                preparedStmt.setString(i++, observationDT.getTxt());//38
                preparedStmt.setString(i++, observationDT.getUserAffiliationTxt()); //39
                if (observationDT.getElectronicInd() == null)
                  preparedStmt.setString(i++, observationDT.getElectronicInd());//40
                else
                  preparedStmt.setString(i++, observationDT.getElectronicInd().trim());//40
                preparedStmt.setString(i++, observationDT.getCtrlCdDisplayForm());//41
                preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined1());//42
                preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined2());//43
                preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined3());//44
                preparedStmt.setString(i++, observationDT.getCtrlCdUserDefined4());//45
                preparedStmt.setString(i++, observationDT.getValueCd());//46
                if(observationDT.getYnuCd() == null)
                  preparedStmt.setString(i++, observationDT.getYnuCd());//47
                else
                  preparedStmt.setString(i++, observationDT.getYnuCd().trim());//47
                if (observationDT.getPnuCd() == null)
                  preparedStmt.setString(i++, observationDT.getPnuCd());//48
                else
                  preparedStmt.setString(i++, observationDT.getPnuCd().trim());//48
                preparedStmt.setString(i++, observationDT.getObsDomainCdSt1());//49

                if(observationDT.getProgramJurisdictionOid() == null)
                    preparedStmt.setLong(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, observationDT.getProgramJurisdictionOid().longValue());//50
                if (observationDT.getSharedInd() == null)
                  preparedStmt.setString(i++, observationDT.getSharedInd());//51
                else
                  preparedStmt.setString(i++, observationDT.getSharedInd().trim());//51

                if (observationDT.getVersionCtrlNbr() == null)
                { // 52
                    logger.error("** VersionCtrlNbr cannot be null here :" + observationDT.getVersionCtrlNbr());
                }
                else
                {
                    logger.debug("VersionCtrlNbr exists here:" + observationDT.getVersionCtrlNbr());
                    preparedStmt.setInt(i++, (observationDT.getVersionCtrlNbr().intValue())); // 53
                    logger.debug("new versioncontrol number:" + (observationDT.getVersionCtrlNbr().intValue()));
                }
                //changes for NPR UPdate
                preparedStmt.setString(i++, observationDT.getAltCd());//53
                preparedStmt.setString(i++, observationDT.getAltCdDescTxt());//54
                preparedStmt.setString(i++, observationDT.getAltCdSystemCd());//55
                preparedStmt.setString(i++, observationDT.getAltCdSystemDescTxt());//56
                preparedStmt.setString(i++, observationDT.getCdDerivedInd());//57
                preparedStmt.setString(i++, observationDT.getCdVersion());//58
                preparedStmt.setTimestamp(i++, observationDT.getRptToStateTime());//59
                preparedStmt.setString(i++, observationDT.getProcessingDecisionCd());//60

                preparedStmt.setString(i++, observationDT.getPregnantIndCd());
                
                if(observationDT.getPregnantWeek() == null)//33
                    preparedStmt.setNull(i++, Types.INTEGER);
               else
            	   preparedStmt.setInt(i++, observationDT.getPregnantWeek());
                preparedStmt.setString(i++, observationDT.getProcessingDecisionTxt());  //63
                preparedStmt.setLong(i++, observationDT.getObservationUid().longValue());  //63
                preparedStmt.setInt(i++,(observationDT.getVersionCtrlNbr().intValue()-1)); //64
                logger.debug("sql query is :" + preparedStmt.toString());

                resultCount = preparedStmt.executeUpdate();
                if (resultCount != 1)
                {
                    logger.error("Error: none or more than one observation updated at a time, " + "resultCount = "
                            + resultCount);
                    throw new NEDSSConcurrentDataException(
                            "NEDSSConcurrentDataException:-- The Obs data has been modified by other user, please verify!");
                }
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " + "observation into OBSERVATION_TABLE: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+DataTables.OBSERVATION_TABLE+" "+sex.toString(), sex);
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of updating observation table

    private ObservationDT selectObservation (long observationUID) throws NEDSSSystemException
    {
        ObservationDT observationDT = new ObservationDT();
        Connection dbConnection= null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        /**
         * Selects observation from Observation table
         */

        try
        {
            dbConnection = getConnection();
            //NedssUtils nu = new NedssUtils();
            //dbConnection = nu.getTestConnection();

            preparedStmt = dbConnection.prepareStatement(SELECT_OBSERVATION);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            // logger.debug("ObservationDT object for: observationUID = " +
            // observationUID);

            resultSetMetaData = resultSet.getMetaData();

            observationDT = (ObservationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, observationDT.getClass());
            observationDT.setItNew(false);
            observationDT.setItDirty(false);
            observationDT.setItDelete(false);
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " + "observation vo; id = " + observationUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " + "observation vo; id = " + observationUID , ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        //logger.debug("return observation object");
        return observationDT;
    }//end of selecting observation ethnic groups

    private void removeObservation (long observationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Deletes observation ethnic groups
         */
        try
        {
            dbConnection = getConnection();

            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBSERVATION);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error("Error: cannot delete observation from OBSERVATION_TABLE!! resultCount = " + resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " + "observation; id = " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing observation



/*    public static void main(String args[]){
      //NedssUtils nu = new NedssUtils();
      //Connection dbConnection = nu.getTestConnection();
      //logger.debug("ObservationDAOImpl - Doing the main thing");

      //Connection dbConnection = null;

      try{

      // To test update
/**        ObservationDAOImpl obsDAOI = new ObservationDAOImpl();
        ObservationDAOImpl obsNameDAOI = new ObservationDAOImpl();
        Long uid = new Long(10);
        Long person_uid = new Long(1);
        ObservationDT obsDT = new ObservationDT();
        obsDT.setActivityDurationAmt("OBS");
        obsDT.setActivityDurationUnitCd("OBS  Test");
        obsDT.setObservationUid(uid);
        Timestamp t = new Timestamp(new java.util.Date().getTime());
        obsDT.setActivityFromTime(t);
        obsDT.setActivityToTime(t);
        obsDT.setAddReasonCd("OBSReason");
        Timestamp y = new Timestamp(1231267673);
        obsDT.setAddTime(y);
        obsDT.setAddUserId(uid);
        obsDT.setCd("OBS");
        obsDT.setCdDescTxt("OBS full");
        obsDT.setCdSystemCd("OBS Test");
        obsDT.setCdSystemDescTxt("OBS Test");
        obsDT.setConfidentialityCd("OBS 321");
        obsDT.setConfidentialityDescTxt("OBS Test");
        obsDT.setDerivationExp(new Integer(1));
        obsDT.setEffectiveDurationAmt("OBS test");
        obsDT.setEffectiveDurationUnitCd("OBS test");
        obsDT.setEffectiveFromTime(y);
        obsDT.setEffectiveToTime(t);
        obsDT.setGroupLevelCd("A");
        obsDT.setJurisdictionCd("OBS TEST");
        obsDT.setLabConditionCd("OBS Lab");
        obsDT.setLastChgReasonCd("OBS reason");
        obsDT.setLastChgTime(y);
        obsDT.setLastChgUserId(uid);
        obsDT.setLocalId("OBS Test");
        obsDT.setMethodCd("OBS Method");
        obsDT.setMethodDescTxt("OBS Desc Txt");
        obsDT.setObsDomainCd("OBS Domain");
        obsDT.setObservationUid(uid);
        obsDT.setOrgAccessPermis("Org Permission");
        obsDT.setPriorityCd("OBS Priority");
        obsDT.setPriorityDescTxt("OBS Desc Txt");
        obsDT.setProgAreaAccessPermis("OBS ProgAreaAccessPermis");
        obsDT.setRecordStatusCd("OBS Status");
        obsDT.setRecordStatusTime(t);
        obsDT.setRepeatNbr(new Integer(1));
        obsDT.setStatusCd("C");
        obsDT.setTargetSiteCd("OBS Target");
        obsDT.setTargetSiteDescTxt("OBS TargetDesc Txt");
        obsDT.setTxt("OBS text");
        obsDT.setSubjectPersonUid(person_uid);
        obsDT.setItNew(true);
        obsDT.setItDirty(false);
        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
      ObservationDAOImpl Obs = new   ObservationDAOImpl();
      Obs.store(obsDT);
      //Obs.updateObservation(obsDT);
*/

 //To Test create
/**        Long uid = new Long(10);
        Long person_uid = new Long(1);
        ObservationDT obsDT = new ObservationDT();
        obsDT.setActivityDurationAmt("OBS test");
        obsDT.setActivityDurationUnitCd("OBS Activity CD");
        obsDT.setObservationUid(uid);
        Timestamp t = new Timestamp(new java.util.Date().getTime());
        obsDT.setActivityFromTime(t);
        obsDT.setActivityToTime(t);
        obsDT.setAddReasonCd("OBS Reason");
        Timestamp y = new Timestamp(123132323);
        obsDT.setAddTime(y);
        obsDT.setAddUserId(uid);
        obsDT.setCd("OBS");
        obsDT.setCdDescTxt("OBS full");
        obsDT.setCdSystemCd("OBS Test");
        obsDT.setCdSystemDescTxt("OBS Test");
        obsDT.setConfidentialityCd("OBS 321");
        obsDT.setConfidentialityDescTxt("OBS Test");
        obsDT.setDerivationExp(new Integer(1));
        //obsDT.setEffectiveDurationAmt("OBS test");
        //obsDT.setEffectiveDurationUnitCd("OBS test");
        //obsDT.setEffectiveFromTime(y);
        obsDT.setEffectiveToTime(t);
        obsDT.setGroupLevelCd("A");
        obsDT.setJurisdictionCd("OBS TEST");
        obsDT.setLabConditionCd("OBS Lab");
        obsDT.setLastChgReasonCd("OBS reason");
        obsDT.setLastChgTime(y);
        obsDT.setLastChgUserId(uid);
        obsDT.setLocalId("OBS Test");
        obsDT.setMethodCd("OBS Method");
        obsDT.setMethodDescTxt("OBS Desc Txt");
        obsDT.setObsDomainCd("OBS Domain");
        obsDT.setObservationUid(uid);
        obsDT.setOrgAccessPermis("Org Permission");
        obsDT.setPriorityCd("OBS Priority");
        obsDT.setPriorityDescTxt("OBS Desc Txt");
        //obsDT.setProgAreaAccessPermis("OBS ProgAreaAccessPermis");
        obsDT.setRecordStatusCd("OBS Status");
        obsDT.setRecordStatusTime(t);
        obsDT.setRepeatNbr(new Integer(1));
        obsDT.setStatusCd("C");
        //obsDT.setStatusTime(l);

        obsDT.setTargetSiteCd("OBS Target");
        obsDT.setTargetSiteDescTxt("OBS TargetDesc Txt");
        obsDT.setTxt("OBS text");
        obsDT.setSubjectPersonUid(person_uid);
        obsDT.setItNew(true);
        obsDT.setItDirty(false);
        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
        ObservationDAOImpl Obs = new   ObservationDAOImpl();
        Obs.create(obsDT);
*/

//For findByPrimaryKey
/**        ObservationDAOImpl Obs1 = new   ObservationDAOImpl();
        Long l = Obs1.findByPrimaryKey(47);
*/
        //for Remove method
/**
        ObservationDAOImpl Obs = new   ObservationDAOImpl();
        Obs.remove(42);
*/
/**
      }catch(Exception e){
        //logger.debug("\n\nObservationDAOImpl ERROR : Not working \n" + e);
      }
}

*/
    
    public Collection<Object> openPublicHealthCaseForLab (Long uid, boolean alreadyAssociated) throws NEDSSSystemException {
    	PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
    	   ArrayList<Object> returnColl =  new ArrayList<Object> ();
    	   Connection dbConnection = null;
    	   PreparedStatement preparedStmt = null;
    	   ResultSet resultSet = null;
    	    ResultSetMetaData resultSetMetaData = null;
            ResultSetUtils resultSetUtils = new ResultSetUtils();
    	   try
    	   {
    		   String SELECT_ASSOCIATED_PHC_FOR_LAB="";
    		
    		  if(!alreadyAssociated){
    			  SELECT_ASSOCIATED_PHC_FOR_LAB=" select  public_health_case.public_health_case_uid \"publicHealthCaseUid\", public_health_case.cd  \"cd\", prog_area_cd \"progAreaCd\",  pat.subject_entity_uid  \"currentPatientUid\", " 
        				  	+ " prov.subject_entity_uid  \"currentInvestigatorUid\" "
        				  	+ " from Public_health_case public_health_case "
        				  	+ " left outer join Participation pat "
    			    		+ " on pat.act_uid=public_health_case.public_health_case_uid "
    			    		+ " and pat.type_cd='SubjOfPHC' "
    			    		+ " left outer join Participation prov "
    			    		+ " on prov.act_uid=public_health_case.public_health_case_uid "
    			    		+ " and prov.type_cd='InvestgrOfPHC' "
    			    		+ " where   investigation_status_cd='O' ";
    			  
    		  }else{
        		  SELECT_ASSOCIATED_PHC_FOR_LAB=" select  public_health_case.public_health_case_uid \"publicHealthCaseUid\", public_health_case.cd  \"cd\", prog_area_cd \"progAreaCd\",  pat.subject_entity_uid  \"currentPatientUid\", " 
      				  	+ " prov.subject_entity_uid  \"currentInvestigatorUid\" "
      				  	+ " from Public_health_case public_health_case "
      				  	+ " left outer join Participation pat "
  			    		+ " on pat.act_uid=public_health_case.public_health_case_uid "
  			    		+ " and pat.type_cd='SubjOfPHC' "
  			    		+ " left outer join Participation prov "
  			    		+ " on prov.act_uid=public_health_case.public_health_case_uid "
  			    		+ " and prov.type_cd='InvestgrOfPHC' "
  			    		+ " inner join Act_relationship ar "
  			    		+ " on ar.source_act_uid =? "
  			    		+ " and ar.target_act_uid= public_health_case.public_health_case_uid "
  			    		+ " where   investigation_status_cd='O' ";
    			  
    		  }
    		dbConnection = getConnection();
               preparedStmt = dbConnection.prepareStatement(SELECT_ASSOCIATED_PHC_FOR_LAB);
               if(alreadyAssociated)
            	   preparedStmt.setLong(1, uid);
               resultSet = preparedStmt.executeQuery();
               resultSetMetaData = resultSet.getMetaData();
    		   
    		   
               returnColl = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                                  resultSetMetaData,
                                                                  phcDT.getClass(),
                                                                  returnColl);

    		  
    	   }
    	   catch(SQLException sqlException)
    	   {
    		   logger.fatal("ObservationDAOImpl.openPublicHealthCaseForLab sqlException while getting dbConnection for checking for an existing phcDT " ,sqlException);
    		   throw new NEDSSDAOSysException( sqlException.getMessage());
    	   } catch(NEDSSSystemException nedssException)
    	   {
    		   logger.fatal("ObservationDAOImpl.openPublicHealthCaseForLabException while getting dbConnection for checking for an existing phcDT " ,nedssException);
    		   throw new NEDSSDAOSysException( nedssException.getMessage());
    	   }
           catch (ResultSetUtilsException resultsetException)
           {
        	   resultsetException.printStackTrace();
               logger.error(
                       "ObservationDAOImpl.openPublicHealthCaseForLab openPublicHealthCaseForLab():Error in result set handling while populate openPublicHealthCaseForLab.");
               throw new NEDSSDAOSysException("Error in result set handling while populate openPublicHealthCaseForLab.");
           }
           finally
           {
               closeResultSet(resultSet);
               closeStatement(preparedStmt);
               releaseConnection(dbConnection);
           }

    	   return returnColl;
       }

    
	public Collection<Object> associatedPublicHealthCaseForMprForCondCd(long mprUid, String conditionCode)
			throws NEDSSSystemException {
		PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
		ArrayList<Object> returnColl = new ArrayList<Object>();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ArrayList<Object> inArrayList = new ArrayList<Object>();
		inArrayList.add(conditionCode);
		inArrayList.add(mprUid);

		String associatedInvQuery;

		try {
			associatedInvQuery = "{call ASSO_PHC_FOR_MPR_COND_SP(?,?)}";
			returnColl = callStoredProcedureMethodWithResultSet(phcDT, inArrayList, associatedInvQuery);
		} catch (NEDSSSystemException nedssException) {
			logger.fatal(
					"ObservationDAOImpl.openPublicHealthCaseForLabException while getting dbConnection for checking for an existing phcDT ",
					nedssException);
			throw new NEDSSDAOSysException(nedssException.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return returnColl;
	}
}// end of ObservationDAOImpl class
