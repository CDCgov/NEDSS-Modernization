
/**
* Name:		clinical_documentDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               clinical_document value object in the clinical_document entity bean.
*               This class encapsulates all the JDBC calls made by the clinical_documentEJB
*               for a clinical_document object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of clinical_documentEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Keith Welch  Matthew Pease
* @version	1.0
*/

package gov.cdc.nedss.act.clinicaldocument.ejb.dao;

import gov.cdc.nedss.act.clinicaldocument.dt.ClinicalDocumentDT;
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

public class ClinicalDocumentDAOImpl extends BMPBase
{
     private static final String ACT_UID = "ACTIVITY_UID";

     //For logging
     static final LogUtils logger = new LogUtils(ClinicalDocumentDAOImpl.class.getName());

     public static final String SELECT_NEW_ACTIVITY_UID = "SELECT MAX (act_uid) FROM " +
          DataTables.ACTIVITY_TABLE;


     public static final String INSERT_ACTIVITY = "INSERT INTO "
       + DataTables.ACTIVITY_TABLE + "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";

      public static final String SELECT_CLINICALDOCUMENT_UID =
                        "SELECT clinical_document_uid " +
                        "FROM clinical_document " +
                        "WHERE clinical_document_uid = ?";
      public static final String INSERT_CLINICALDOCUMENT =
                        "INSERT INTO clinical_document ( " +
                        "clinical_document_uid, " +         //1
                        "activity_duration_amt, " +         //2
                        "activity_duration_unit_cd, " +     //3
                        "activity_from_time, " +            //4
                        "activity_to_time, " +              //5
                        "add_reason_cd, " +                 //6
                        "add_time, " +                      //7
                        "add_user_id, " +                   //8
                        "cd, " +                            //9
                        "cd_desc_txt, " +                   //10
                        "confidentiality_cd, " +            //11
                        "confidentiality_desc_txt, " +      //12
                        "copy_from_time, " +                //13
                        "copy_to_time, " +                  //14
//                        "copy_time, " +
                        "effective_duration_amt, " +        //15
                        "effective_duration_unit_cd, " +    //16
                        "effective_from_time, " +           //17
                        "effective_to_time, " +             //18
//                        "encounter_from_time, " +
//                        "encounter_to_time, " +
                        "last_chg_reason_cd, " +            //19
                        "last_chg_time, " +                 //20
                        "last_chg_user_id, " +              //21
                        "local_id, " +                      //22
//                        "org_access_permis, " +             //23
                        "practice_setting_cd, " +           //24
                        "practice_setting_desc_txt, " +     //25
//                        "prog_area_access_permis, " +       //26
                        "record_status_cd, " +              //27
                        "record_status_time, " +            //28
                        "status_cd, " +                     //29
                        "status_time, " +                   //30
                        "txt, " +                           //31
                        "user_affiliation_txt, " +          //32
                        "version_nbr, " +                   //33
                        "version_ctrl_nbr," +
                        "program_jurisdiction_oid,"+
                        "shared_ind) "+
                        "VALUES (" +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?)";

	public static final String UPDATE_CLINICALDOCUMENT =
                        "UPDATE clinical_document SET " +
                        "activity_duration_amt = ?, " +     //1
                        "activity_duration_unit_cd = ?, " + //2
                        "activity_from_time = ?, " +        //3
                        "activity_to_time = ?, " +          //4
                        "add_reason_cd = ?, " +             //5
                        "add_time = ?, " +                  //6
                        "add_user_id = ?, " +               //7
                        "cd = ?, " +                        //8
                        "cd_desc_txt = ?, " +               //9
                        "confidentiality_cd = ?, " +        //10
                        "confidentiality_desc_txt = ?, " +  //11
                        "copy_from_time = ?, " +            //12
                        "copy_to_time = ?, " +              //13
//                        "copy_time = ?, " +
                        "effective_duration_amt = ?, " +    //14
                        "effective_duration_unit_cd = ?, " +//15
                        "effective_from_time = ?, " +       //16
                        "effective_to_time = ?, " +         //17
//                        "encounter_from_time = ?, " +
//                        "encounter_to_time = ?, " +
                        "last_chg_reason_cd = ?, " +        //18
                        "last_chg_time = ?, " +             //19
                        "last_chg_user_id = ?, " +          //20
                        "local_id = ?, " +                  //21
//                        "org_access_permis = ?, " +         //22
                        "practice_setting_cd = ?, " +       //23
                        "practice_setting_desc_txt = ?, " + //24
//                        "prog_area_access_permis = ?, " +   //25
                        "record_status_cd = ?, " +          //26
                        "record_status_time = ?, " +        //27
                        "status_cd = ?, " +                 //28
                        "status_time = ?, " +               //29
                        "txt = ?, " +                       //30
                        "user_affiliation_txt = ?, " +      //31
                        "version_nbr = ?, " +                //32
                        "version_ctrl_nbr = ?," +
                        "program_jurisdiction_oid= ?,"+
                        "shared_ind= ? "+
                        "WHERE clinical_document_uid = ? and " +
                        "version_ctrl_nbr = ? ";  //33

	public static final String SELECT_CLINICALDOCUMENT =
                        "SELECT " +
                        "clinical_document_uid \"clinicalDocumentUid\", " +
                        "activity_duration_amt \"activityDurationAmt\", " +
                        "activity_duration_unit_cd \"activityDurationUnitCd\", " +
                        "activity_from_time \"activityFromTime\", " +
                        "activity_to_time \"activityToTime\", " +
                        "add_reason_cd \"addReasonCd\", " +
                        "add_time \"addTime\", " +
                        "add_user_id \"addUserId\", " +
                        "cd \"cd\", " +
                        "cd_desc_txt \"cdDescTxt\", " +
                        "confidentiality_cd \"confidentialityCd\", " +
                        "confidentiality_desc_txt \"confidentialityDescTxt\", " +
                        "copy_from_time \"copyFromTime\", " +
                        "copy_to_time \"copyToTime\", " +
//                        "copy_time \"copyTime\", " +
                        "effective_duration_amt \"effectiveDurationAmt\", " +
                        "effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
                        "effective_from_time \"effectiveFromTime\", " +
                        "effective_to_time \"effectiveToTime\", " +
//                        "encounter_from_time \"encounterFromTime\", " +
//                        "encounter_to_time \"encounterToTime\", " +
                        "last_chg_reason_cd \"lastChgReasonCd\", " +
                        "last_chg_time \"lastChgTime\", " +
                        "last_chg_user_id \"lastChgUserId\", " +
                        "local_id \"localId\", " +
//                        "org_access_permis \"orgAccessPermis\", " +
                        "practice_setting_cd \"practiceSettingCd\", " +
                        "practice_setting_desc_txt \"practiceSettingDescTxt\", " +
//                        "prog_area_access_permis \"progAreaAccessPermis\", " +
                        "record_status_cd \"recordStatusCd\", " +
                        "record_status_time \"recordStatusTime\", " +
                        "status_cd \"statusCd\", " +
                        "status_time \"statusTime\", " +
                        "txt \"txt\", " +
                        "user_affiliation_txt \"userAffiliationTxt\", " +
                        "version_nbr \"versionNbr\"," +
                        "version_ctrl_nbr \"versionControlNumber\", " +
                        "program_jurisdiction_oid \"progJurisId\", " +
                        "shared_ind \"sharedInd\" " +
                          "FROM " +
                        "clinical_document WHERE clinical_document_uid = ?";

	public static final String DELETE_CLINICALDOCUMENT = "";

     private long observationUID = -1;


    //for testing
//    NedssUtils nu = new NedssUtils();
//    Connection dbConnection = nu.getTestConnection();


	private long clinicaldocumentUID = -1;

	public ClinicalDocumentDAOImpl()

	{

	}

	 public long create(Object obj ) throws   NEDSSSystemException
	 {
		 try{
		 	 ClinicalDocumentDT clinicalDocumentDT = (ClinicalDocumentDT) obj;
	
		     clinicaldocumentUID = insertItem(clinicalDocumentDT);
		     clinicalDocumentDT.setItNew(false);
		     clinicalDocumentDT.setItDirty(false);
		     clinicalDocumentDT.setItDelete(false);
		     return clinicaldocumentUID;
		 }catch(NEDSSSystemException ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	 }

	public void store(Object obj)
		throws  NEDSSSystemException, NEDSSConcurrentDataException

	{
	    updateItem( (ClinicalDocumentDT) obj);
	}

	public void remove(long clinicaldocumentUID)
		throws  NEDSSSystemException
	{
	    removeItem(clinicaldocumentUID);
	}

    public Object loadObject(long clinicaldocumentUID) throws
		 NEDSSSystemException
    {
        ClinicalDocumentDT clinicalDocumentDT = selectItem(clinicaldocumentUID);
        clinicalDocumentDT.setItNew(false);
        clinicalDocumentDT.setItDirty(false);
        clinicalDocumentDT.setItDelete(false);
        return clinicalDocumentDT;
    }

    public Long findByPrimaryKey(long clinicaldocumentUID)
    	throws  NEDSSSystemException
    {
        if (itemExists(clinicaldocumentUID))
            return (new Long(clinicaldocumentUID));
        else
            logger.error("No clinicaldocument found for this primary key :" + clinicaldocumentUID);
            return null;
    }


    protected boolean itemExists (long clinicaldocumentUID) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;

        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;


        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CLINICALDOCUMENT_UID);
            preparedStmt.setLong(1, clinicaldocumentUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                clinicaldocumentUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing clinicaldocument's uid in clinicaldocument table-&gt; " + clinicaldocumentUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing clinicaldocument's uid in clinicaldocument table-&gt; " +
                            clinicaldocumentUID, ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

    private long insertItem(ClinicalDocumentDT clinicalDocumentDT)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new clinicaldocument
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        long clinicaldocumentUID = -1;
        int resultCount = 0;


            try
            {

                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);
                UidGeneratorHelper uidGen = new UidGeneratorHelper();
      //	    notificationUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                clinicaldocumentUID = uidGen.getNbsIDLong(UidClassCodes.CLINICAL_DOCUMENT_CLASS_CODE).longValue();
                int i = 1;
    //            preparedStmt.setInt(i++, NEDSSUIDGenerator.getNextUID(ACT_UID, dbConnection));
                preparedStmt.setLong(i++, clinicaldocumentUID);
                preparedStmt.setString(i++,  NEDSSConstants.CLINICAL_DOCUMENT_CLASS_CODE);
                preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
                resultCount = preparedStmt.executeUpdate();
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for CLINICALDOCUMENT_TABLE: \n", sex);
                throw new NEDSSSystemException(sex.toString());
            }
            catch(Exception e)
            {
                logger.fatal("SQLException while generating uid for CLINICALDOCUMENT_TABLE: \n", e);
                throw new NEDSSSystemException(e.toString());
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
                    logger.error("Error while inserting " +
                            "uid into ACTIVITY_TABLE for a new clinicaldocument, resultCount = " +
                            resultCount);
                }
                resultCount = 0;


                // Get the new entity UID for this clinicaldocument

                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(SELECT_NEW_ACTIVITY_UID);

                resultSet = preparedStmt.executeQuery();
                if (!resultSet.next())
                {
                    logger.error("Error in selecting new uid " +
                          "from ACTIVITY_TABLE for a clinicaldocument");
                }

                clinicaldocumentUID = resultSet.getInt(1);


            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while reading " +
                        "uid from ACTIVITY_TABLE for clinicaldocument entry: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            finally
            {
               closeResultSet(resultSet);
               closeStatement(preparedStmt);
               releaseConnection(dbConnection);
            }

            try
            {
                if (clinicaldocumentUID < 1)
                {
                    logger.error(
                              "Error in reading new entity uid, entity uid = " + clinicaldocumentUID);
                }
                dbConnection = getConnection();
                // insert into CLINICALDOCUMENT_TABLE
                logger.info(INSERT_CLINICALDOCUMENT);
                preparedStmt = dbConnection.prepareStatement(INSERT_CLINICALDOCUMENT);
                logger.info(INSERT_CLINICALDOCUMENT);
                int i = 1;
                // Set auto generated PK field
                 preparedStmt.setLong(i++,clinicaldocumentUID);
                // Set all non generated fields
                 preparedStmt.setString(i++,clinicalDocumentDT.getActivityDurationAmt());
                 preparedStmt.setString(i++,clinicalDocumentDT.getActivityDurationUnitCd());
                 if (clinicalDocumentDT.getActivityFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getActivityFromTime());
                if (clinicalDocumentDT.getActivityToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getActivityToTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getAddReasonCd());
                if (clinicalDocumentDT.getAddTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getAddTime());
                if (clinicalDocumentDT.getAddUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,clinicalDocumentDT.getAddUserId().longValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getCdDescTxt());
                preparedStmt.setString(i++,clinicalDocumentDT.getConfidentialityCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getConfidentialityDescTxt());
    //            if (clinicalDocumentDT.getCopyTime() == null)
    //                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
    //            else
    //                 preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyTime());
                if (clinicalDocumentDT.getCopyFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyFromTime());
                if (clinicalDocumentDT.getCopyToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyToTime());
                preparedStmt.setString(i++,clinicalDocumentDT.getEffectiveDurationAmt());
                preparedStmt.setString(i++,clinicalDocumentDT.getEffectiveDurationUnitCd());
                if (clinicalDocumentDT.getEffectiveFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEffectiveFromTime());
                if (clinicalDocumentDT.getEffectiveToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEffectiveToTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getLastChgReasonCd());
                if (clinicalDocumentDT.getLastChgTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getLastChgTime());
                if (clinicalDocumentDT.getLastChgUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,clinicalDocumentDT.getLastChgUserId().longValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getLocalId());
                preparedStmt.setString(i++,clinicalDocumentDT.getPracticeSettingCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getPracticeSettingDescTxt());
                preparedStmt.setString(i++,clinicalDocumentDT.getRecordStatusCd());
                if (clinicalDocumentDT.getRecordStatusTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getRecordStatusTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getStatusCd());
                if (clinicalDocumentDT.getStatusTime() == null)
                     preparedStmt.setNull(i++,Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getStatusTime());
                preparedStmt.setString(i++,clinicalDocumentDT.getTxt());
                preparedStmt.setString(i++,clinicalDocumentDT.getUserAffiliationTxt());
                if (clinicalDocumentDT.getVersionNbr() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setInt(i++,clinicalDocumentDT.getVersionNbr().intValue());

                preparedStmt.setInt(i++,clinicalDocumentDT.getVersionCtrlNbr().intValue());
                preparedStmt.setInt(i++,clinicalDocumentDT.getProgramJurisdictionOid().intValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getSharedInd());

                resultCount = preparedStmt.executeUpdate();
                logger.debug("done insert clinicaldocument! clinicaldocumentUID = " + clinicaldocumentUID);

                return clinicaldocumentUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "clinicaldocument into CLINICALDOCUMENT_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into CLINICALDOCUMENT_TABLE, id = " + clinicaldocumentUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
               closeStatement(preparedStmt);
               releaseConnection(dbConnection);
            }

    }//end of inserting clinicaldocument


    private void updateItem(ClinicalDocumentDT clinicalDocumentDT) throws NEDSSSystemException, NEDSSConcurrentDataException

    {
       Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        try
        {
            //Updates ClinicalDocumentDT table
            if (clinicalDocumentDT != null)
            {

                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(UPDATE_CLINICALDOCUMENT);

		int i = 1;

		// first set non-PK on UPDATE statement
                  preparedStmt.setString(i++,clinicalDocumentDT.getActivityDurationAmt());
                 preparedStmt.setString(i++,clinicalDocumentDT.getActivityDurationUnitCd());
                if (clinicalDocumentDT.getActivityFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getActivityFromTime());
                if (clinicalDocumentDT.getActivityToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getActivityToTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getAddReasonCd());
                if (clinicalDocumentDT.getAddTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getAddTime());
                if (clinicalDocumentDT.getAddUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,clinicalDocumentDT.getAddUserId().longValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getCdDescTxt());
                preparedStmt.setString(i++,clinicalDocumentDT.getConfidentialityCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getConfidentialityDescTxt());
    //            if (clinicalDocumentDT.getCopyTime() == null)
    //                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
    //            else
    //                 preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyTime());
                if (clinicalDocumentDT.getCopyFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyFromTime());
                if (clinicalDocumentDT.getCopyToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getCopyToTime());
                preparedStmt.setString(i++,clinicalDocumentDT.getEffectiveDurationAmt());
                preparedStmt.setString(i++,clinicalDocumentDT.getEffectiveDurationUnitCd());
                if (clinicalDocumentDT.getEffectiveFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEffectiveFromTime());
                if (clinicalDocumentDT.getEffectiveToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEffectiveToTime());
    //            if (clinicalDocumentDT.getEncounterFromTime() == null)
    //                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
    //            else
    //                 preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEncounterFromTime());
    //            if (clinicalDocumentDT.getEncounterToTime() == null)
    //                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
    //            else
    //                 preparedStmt.setTimestamp(i++,clinicalDocumentDT.getEncounterToTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getLastChgReasonCd());
                if (clinicalDocumentDT.getLastChgTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getLastChgTime());
                if (clinicalDocumentDT.getLastChgUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,clinicalDocumentDT.getLastChgUserId().longValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getLocalId());
                preparedStmt.setString(i++,clinicalDocumentDT.getPracticeSettingCd());
                preparedStmt.setString(i++,clinicalDocumentDT.getPracticeSettingDescTxt());
                preparedStmt.setString(i++,clinicalDocumentDT.getRecordStatusCd());
                if (clinicalDocumentDT.getRecordStatusTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getRecordStatusTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getStatusCd());
                if (clinicalDocumentDT.getStatusTime() == null)
                     preparedStmt.setNull(i++,Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,clinicalDocumentDT.getStatusTime());
                     preparedStmt.setString(i++,clinicalDocumentDT.getTxt());
                     preparedStmt.setString(i++,clinicalDocumentDT.getUserAffiliationTxt());
                if (clinicalDocumentDT.getVersionNbr() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setInt(i++,clinicalDocumentDT.getVersionNbr().intValue());

                preparedStmt.setInt(i++,clinicalDocumentDT.getVersionCtrlNbr().intValue());
                preparedStmt.setInt(i++,clinicalDocumentDT.getProgramJurisdictionOid().intValue());
                preparedStmt.setString(i++,clinicalDocumentDT.getSharedInd());
                preparedStmt.setLong(i++,clinicalDocumentDT.getClinicalDocumentUid().longValue());
                preparedStmt.setInt(i++,clinicalDocumentDT.getVersionNbr().intValue()-1); // for Data concurrency

             resultCount = preparedStmt.executeUpdate();
               if ( resultCount <= 0 )
                {
                    logger.error
                            ("Error: none or more than one Clinical Documnet updated at a time, " +
                              "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
             }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "clinicaldocument into CLINICALDOCUMENT_TABLE: \n", sex);
            throw new NEDSSSystemException( sex.toString() );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private ClinicalDocumentDT selectItem(long clinicaldocumentUID)
    	throws NEDSSSystemException
    {
        ClinicalDocumentDT clinicalDocumentDT = new ClinicalDocumentDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        /**
         * Selects clinicaldocument from clinical_document table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CLINICALDOCUMENT);
            preparedStmt.setLong(1, clinicaldocumentUID);
            resultSet = preparedStmt.executeQuery();

logger.debug("clinicalDocumentDT object for: clinicaldocumentUID = " + clinicaldocumentUID);

            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, clinicalDocumentDT.getClass(), pList);

            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                clinicalDocumentDT = (ClinicalDocumentDT)anIterator.next();
                clinicalDocumentDT.setItNew(false);
                clinicalDocumentDT.setItDirty(false);
                clinicalDocumentDT.setItDelete(false);
            }

        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " +
                            "clinicalDocument vo; id = " + clinicaldocumentUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "clinicalDocument vo; id = " + clinicaldocumentUID , ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

		logger.debug("returning clinicalDocument DT object");
        return clinicalDocumentDT;
    }//end of selecting item

    private void removeItem(long clinicaldocumentUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        /**
         * Deletes clinicaldocuments
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_CLINICALDOCUMENT);
            preparedStmt.setLong(1, clinicaldocumentUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete clinicaldocument from CLINICALDOCUMENT_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "clinicaldocument; id = " + clinicaldocumentUID , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }



}
