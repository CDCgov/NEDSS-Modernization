/**
 * Title:       InterventionDAOImpl.java
 * Description: This is the implementation of NEDSSDAOInterface for the
*               Intervention value object in the Intervention entity bean.
*               This class encapsulates all the JDBC calls made by the InterventionEJB
*               for a Intervention object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of InterventionEJB is
*               implemented here.
 * Copyright:   Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author      Pradeep Kumar Sharma & NEDSS Development Team
 * @version     1.0
 */

package gov.cdc.nedss.act.intervention.ejb.dao;


import gov.cdc.nedss.act.intervention.dt.InterventionDT;
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
public class InterventionDAOImpl extends BMPBase
{

   // private static final String ACT_UID = "ACTIVITY_UID";
    private long interventionUID = -1;
    //For logging
    static final LogUtils logger = new LogUtils(InterventionDAOImpl.class.getName());
    public static final String INSERT_ACTIVITY = "INSERT INTO " +
     DataTables.ACTIVITY_TABLE + "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";
    public static final String SELECT_INTERVENTION =
     "SELECT intervention_uid \"interventionUid\","+
     "activity_duration_amt \"activityDurationAmt\","+
     "activity_duration_unit_cd \"activityDurationUnitCd\","+
     "activity_from_time \"activityFromTime\","+
     "activity_to_time \"activityToTime\", "+
      "add_reason_cd \"addReasonCd\", add_time \"addTime\","+
      "add_user_id \"addUserId\", cd \"cd\", cd_desc_txt \"cdDescTxt\","+
      "class_cd \"classCd\", confidentiality_cd \"confidentialityCd\","+
      "confidentiality_desc_txt \"confidentialityDescTxt\","+
      "effective_duration_amt \"effectiveDurationAmt\", "+
      "effective_duration_unit_cd \"effectiveDurationUnitCd\","+
      "effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\","+
      "last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\","+
      "last_chg_user_id \"lastChgUserId\", method_cd \"methodCd\","+
      "method_desc_txt \"methodDescTxt\",  priority_cd \"priorityCd\","+
      "priority_desc_txt \"priorityDescTxt\", qty_amt \"qtyAmt\","+
      "qty_unit_cd \"qtyUnitCd\", reason_cd \"reasonCd\","+
      "reason_desc_txt \"reasonDescTxt\", record_status_cd \"recordStatusCd\","+
      "record_status_time \"recordStatusTime\", repeat_nbr \"repeatNbr\","+
      "status_cd \"statusCd\", status_time \"statusTime\","+
      "target_site_cd \"targetSiteCd\","+
      "target_site_desc_txt \"targetSiteDescTxt\", txt \"txt\","+
      "user_affiliation_txt \"userAffiliationTxt\", local_id \"localId\", "+
      "jurisdiction_cd \"jurisdictionCd\", prog_area_cd \"progAreaCd\","+
      "cd_system_cd \"cdSystemCd\", cd_system_desc_txt \"cdSystemDescTxt\","+
      "version_ctrl_nbr \"versionCtrlNbr\","+
      "program_jurisdiction_oid \"progJurisId\",shared_ind \"sharedInd\", "+
      "material_cd \"materialCd\", age_at_vacc \"ageAtVacc\", "+
      "age_at_vacc_unit_cd \"ageAtVaccUnitCd\", vacc_mfgr_cd \"vaccMfgrCd\", "+
      "material_lot_nm \"materialLotNm\", material_expiration_time \"materialExpirationTime\", "+
      "vacc_dose_nbr \"vaccDoseNbr\", vacc_info_source_cd \"vaccInfoSourceCd\", electronic_ind \"electronicInd\" "+
      "FROM " + DataTables.INTERVENTION_TABLE + " WHERE intervention_uid = ?";

     public static final String SELECT_INTERVENTION_UID =
       "SELECT intervention_uid FROM " + DataTables.INTERVENTION_TABLE +
       " WHERE intervention_uid = ?";


    public static final String DELETE_INTERVENTION =
    "DELETE FROM " + DataTables.INTERVENTION_TABLE + " WHERE intervention_uid = ?";

     public static final String UPDATE_INTERVENTION =
     "UPDATE " + DataTables.INTERVENTION_TABLE +
     " set activity_duration_amt = ?, activity_duration_unit_cd = ?, "+
     "activity_from_time = ?, activity_to_time = ?, add_reason_cd = ?, "+
     "add_time = ?, add_user_id = ?, cd = ?, cd_desc_txt = ?,"+
      "class_cd = ?, confidentiality_cd = ?, confidentiality_desc_txt = ?, "+
      "effective_duration_amt = ?, effective_duration_unit_cd = ?, "+
      "effective_from_time = ?, effective_to_time = ?, last_chg_reason_cd = ?,"+
      "last_chg_time = ?, last_chg_user_id = ?, method_cd = ?,"+
      "method_desc_txt = ?, priority_cd = ?, priority_desc_txt = ?,  "+
      "qty_amt = ?, qty_unit_cd = ?, reason_cd = ?, reason_desc_txt = ?,"+
      "record_status_cd = ?, record_status_time = ?, repeat_nbr = ?, "+
      "status_cd = ?, status_time = ?, target_site_cd = ?, "+
      "target_site_desc_txt = ?, txt = ?, user_affiliation_txt = ?,"+
      " local_id = ?, jurisdiction_cd = ?, prog_area_cd = ?, cd_system_cd = ?, "+
      "cd_system_desc_txt = ?, version_ctrl_nbr = ?, program_jurisdiction_oid=?, "+
      "shared_ind = ?, material_cd= ?, age_at_vacc=?, age_at_vacc_unit_cd=?, "+
      "vacc_mfgr_cd = ?, material_lot_nm=?, material_expiration_time=?, "+
      "vacc_dose_nbr = ?, vacc_info_source_cd = ? "+
      "WHERE  intervention_uid = ? AND version_ctrl_nbr = ?";

     public static final String INSERT_INTERVENTION =
     "INSERT INTO " + DataTables.INTERVENTION_TABLE +
      "(intervention_uid, activity_duration_amt, activity_duration_unit_cd,"+
      "activity_from_time, activity_to_time, add_reason_cd, add_time,"+
      "add_user_id, cd, cd_desc_txt, class_cd, confidentiality_cd,"+
      "confidentiality_desc_txt, effective_duration_amt, "+
      "effective_duration_unit_cd, effective_from_time, effective_to_time,"+
      "last_chg_reason_cd, last_chg_time, last_chg_user_id, method_cd, "+
      "method_desc_txt, priority_cd, priority_desc_txt, qty_amt, qty_unit_cd,"+
      "reason_cd, reason_desc_txt, record_status_cd, record_status_time, "+
      "repeat_nbr, status_cd, status_time, target_site_cd, target_site_desc_txt,"+
      "txt, user_affiliation_txt, local_id, jurisdiction_cd, prog_area_cd, "+
      "cd_system_cd, cd_system_desc_txt,version_ctrl_nbr,"+
      "program_jurisdiction_oid,shared_ind, "+
      "material_cd,age_at_vacc,age_at_vacc_unit_cd, "+
      "vacc_mfgr_cd,material_lot_nm,material_expiration_time,vacc_dose_nbr,vacc_info_source_cd, electronic_ind)"+
       " Values (?,?,?,?,?, "+
               "?, ?, ?, ?, ?,"+
               "?, ?, ?, ?, ?,"+
               "?, ?, ?, ?, ?, "+
               "?, ?, ?, ?, ?,"+
               "?, ?, ?, ?, ?, "+
               "?, ?, ?, ?, ?, "+
               "?, ?, ?, ?, ?,"+
               "?, ?, ?, ?,?, "+
               "?, ?, ?, ?, ?, "+
               "?, ?, ?, ?)";


    public InterventionDAOImpl()
    {
    }

    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        interventionUID = insertIntervention((InterventionDT)obj);
	        logger.debug("(From inserting intervention_table)intervention UID = " + interventionUID);
	        ((InterventionDT)obj).setItNew(false);
	        ((InterventionDT)obj).setItDirty(false);
	        return interventionUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void storeIntervention(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
    		updateIntervention((InterventionDT)obj);
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long interventionUID) throws NEDSSSystemException
    {
    	try{
    		removeIntervention(interventionUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long InterventionUID) throws NEDSSSystemException
    {
    	try{
	         InterventionDT interventionDT = selectIntervention(InterventionUID);
	         interventionDT.setItNew(false);
	         interventionDT.setItDirty(false);
	         return interventionDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        if (interventionExists(interventionUID))
	            return (new Long(interventionUID));
	        else
	            logger.error("No intervention found for this primary key :" + interventionUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    protected boolean interventionExists (long interventionUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_INTERVENTION_UID);
		    logger.debug("interventionID = " + interventionUID);
            preparedStmt.setLong(1, interventionUID);
			resultSet = preparedStmt.executeQuery();
            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                interventionUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing intervention's uid in intervention table-> " + interventionUID , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing intervention's uid in intervention table-> " +
                            interventionUID , ex);
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


    private long insertIntervention(InterventionDT interventionDT)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new intervention
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        //long interventionUID = -1;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

                /**
                 * Inserts into act table for intervention
                 */
                try
                {
                  uidGen = new UidGeneratorHelper();
                  interventionUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                  dbConnection = getConnection();
                  preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);

                  int i = 1;
                  preparedStmt.setLong(i++, interventionUID);
                  preparedStmt.setString(i++, NEDSSConstants.INTERVENTION_CLASS_CODE);
                  preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
                  resultCount = preparedStmt.executeUpdate();

                }
                catch(Exception e)
                {
                	logger.fatal("Exception  = "+e.getMessage(), e);
                	throw new NEDSSDAOSysException( e.getMessage());
                }
                finally
                {
                // close statement before reuse
                  closeStatement(preparedStmt);
                  releaseConnection(dbConnection);

                }
                if (resultCount != 1)
                {
                    logger.error("Error while inserting " +
                            "uid into ACT_TABLE for a new intervention, resultCount = " +
                            resultCount);
                }

                /**
                 * inserts into INTERVENTION_TABLE
                 */
              try
              {
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_INTERVENTION);
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.INTTERVENTION_CLASS_CODE);

                int i = 1;

                preparedStmt.setLong(i++, interventionUID);
                preparedStmt.setString(i++, interventionDT.getActivityDurationAmt());
                preparedStmt.setString(i++, interventionDT.getActivityDurationUnitCd());
                if (interventionDT.getActivityFromTime() == null)
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                        else
                    preparedStmt.setTimestamp(i++, interventionDT.getActivityFromTime());
                if (interventionDT.getActivityToTime() == null)
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getActivityToTime());
                preparedStmt.setString(i++, interventionDT.getAddReasonCd());
                if (interventionDT.getAddTime() == null)
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getAddTime());
                if(interventionDT.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (interventionDT.getAddUserId()).longValue());
                preparedStmt.setString(i++, interventionDT.getCd());
                preparedStmt.setString(i++, interventionDT.getCdDescTxt());
                preparedStmt.setString(i++, interventionDT.getClassCd());
                preparedStmt.setString(i++, interventionDT.getConfidentialityCd());
                preparedStmt.setString(i++, interventionDT.getConfidentialityDescTxt());
                preparedStmt.setString(i++, interventionDT.getEffectiveDurationAmt());
                preparedStmt.setString(i++, interventionDT.getEffectiveDurationUnitCd());
                if (interventionDT.getEffectiveFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getEffectiveFromTime());
                if (interventionDT.getEffectiveToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getEffectiveToTime());
                preparedStmt.setString(i++, interventionDT.getLastChgReasonCd());
                if (interventionDT.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getLastChgTime());
                if(interventionDT.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (interventionDT.getLastChgUserId()).longValue());
                //preparedStmt.setString(i++, interventionDT.getLocalId());
                preparedStmt.setString(i++, interventionDT.getMethodCd());
                preparedStmt.setString(i++, interventionDT.getMethodDescTxt());

                preparedStmt.setString(i++, interventionDT.getPriorityCd());
                preparedStmt.setString(i++, interventionDT.getPriorityDescTxt());

                preparedStmt.setString(i++, interventionDT.getQtyAmt());
                preparedStmt.setString(i++, interventionDT.getQtyUnitCd());
                preparedStmt.setString(i++, interventionDT.getReasonCd());
                preparedStmt.setString(i++, interventionDT.getReasonDescTxt());
                preparedStmt.setString(i++, interventionDT.getRecordStatusCd());
                if (interventionDT.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getRecordStatusTime());
                if(interventionDT.getRepeatNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getRepeatNbr()).intValue());
                if (interventionDT.getStatusCd() == null)
                  preparedStmt.setString(i++, interventionDT.getStatusCd());
                else
                  preparedStmt.setString(i++, interventionDT.getStatusCd().trim());
                if (interventionDT.getStatusTime() == null)
                {
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getStatusTime());
                preparedStmt.setString(i++, interventionDT.getTargetSiteCd());
                preparedStmt.setString(i++, interventionDT.getTargetSiteDescTxt());
                preparedStmt.setString(i++, interventionDT.getTxt());
                preparedStmt.setString(i++, interventionDT.getUserAffiliationTxt());
                preparedStmt.setString(i++, localUID);
                preparedStmt.setString(i++, interventionDT.getJurisdictionCd());
                preparedStmt.setString(i++, interventionDT.getProgAreaCd());
                preparedStmt.setString(i++, interventionDT.getCdSystemCd());
                preparedStmt.setString(i++, interventionDT.getCdSystemDescTxt());
                
                //new ones for security, concurrence
                logger.debug("The version control number :" + interventionDT.getVersionCtrlNbr());
                preparedStmt.setInt(i++,interventionDT.getVersionCtrlNbr().intValue());
                logger.debug("The program Jridiction code number :" + interventionDT.getProgramJurisdictionOid());
                if(interventionDT.getProgramJurisdictionOid() == null)
                  preparedStmt.setNull(i++,Types.INTEGER);
                else
                  preparedStmt.setLong(i++,interventionDT.getProgramJurisdictionOid().longValue());
                preparedStmt.setString(i++,interventionDT.getSharedInd());
                
                preparedStmt.setString(i++, interventionDT.getMaterialCd());
                if(interventionDT.getAgeAtVacc() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getAgeAtVacc()).intValue());
                preparedStmt.setString(i++, interventionDT.getAgeAtVaccUnitCd());
                preparedStmt.setString(i++, interventionDT.getVaccMfgrCd());
                preparedStmt.setString(i++, interventionDT.getMaterialLotNm());
                if (interventionDT.getMaterialExpirationTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getMaterialExpirationTime());
                if(interventionDT.getVaccDoseNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getVaccDoseNbr()).intValue());
                preparedStmt.setString(i++, interventionDT.getVaccInfoSourceCd());
                
                preparedStmt.setString(i++, interventionDT.getElectronicInd());
                
                resultCount = preparedStmt.executeUpdate();
                        logger.debug("done insert intervention! interventionUID = " + interventionUID);
                return interventionUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "intervention into INTERVENTION_TABLE: \n", sex);
                throw new NEDSSSystemException(  sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into INTERVENTION_TABLE, id = " + interventionUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
           }
    }//end of inserting intervention


    private void updateIntervention (InterventionDT interventionDT) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            //Updates intervention table
            if (interventionDT != null)
            {
                dbConnection = getConnection();
                logger.debug("Updating interventionDT: UID = " + interventionDT.getInterventionUid().longValue());
                preparedStmt = dbConnection.prepareStatement(UPDATE_INTERVENTION);

                int i = 1;

                preparedStmt.setString(i++, interventionDT.getActivityDurationAmt()); //1
                preparedStmt.setString(i++, interventionDT.getActivityDurationUnitCd());//2
                if (interventionDT.getActivityFromTime() == null)   //3
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getActivityFromTime());
                if (interventionDT.getActivityToTime() == null)//4
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getActivityToTime());
                preparedStmt.setString(i++, interventionDT.getAddReasonCd());//5
                if (interventionDT.getAddTime() == null)//6
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getAddTime());
                if(interventionDT.getAddUserId() == null)//7
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (interventionDT.getAddUserId()).longValue());
                preparedStmt.setString(i++, interventionDT.getCd());//8
                preparedStmt.setString(i++, interventionDT.getCdDescTxt());//9
                preparedStmt.setString(i++, interventionDT.getClassCd());//10
                preparedStmt.setString(i++, interventionDT.getConfidentialityCd());//11
                preparedStmt.setString(i++, interventionDT.getConfidentialityDescTxt());//12
                preparedStmt.setString(i++, interventionDT.getEffectiveDurationAmt());//13
                preparedStmt.setString(i++, interventionDT.getEffectiveDurationUnitCd());//14
                if (interventionDT.getEffectiveFromTime() == null)//15
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getEffectiveFromTime());
                if (interventionDT.getEffectiveToTime() == null)//16
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getEffectiveToTime());
                preparedStmt.setString(i++, interventionDT.getLastChgReasonCd());//17
                if (interventionDT.getLastChgTime() == null)//18
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getLastChgTime());
                if(interventionDT.getLastChgUserId() == null)//19
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (interventionDT.getLastChgUserId()).longValue());
                //preparedStmt.setString(i++, interventionDT.getLocalId());
                preparedStmt.setString(i++, interventionDT.getMethodCd());//20
                preparedStmt.setString(i++, interventionDT.getMethodDescTxt());//21
                preparedStmt.setString(i++, interventionDT.getPriorityCd());//22
                preparedStmt.setString(i++, interventionDT.getPriorityDescTxt());//23
                preparedStmt.setString(i++, interventionDT.getQtyAmt());//24
                preparedStmt.setString(i++, interventionDT.getQtyUnitCd());//25
                preparedStmt.setString(i++, interventionDT.getReasonCd());//26
                preparedStmt.setString(i++, interventionDT.getReasonDescTxt());//27
                preparedStmt.setString(i++, interventionDT.getRecordStatusCd());//28
                if (interventionDT.getRecordStatusTime() == null)//29
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getRecordStatusTime());
                if(interventionDT.getRepeatNbr() == null)//30
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getRepeatNbr()).intValue());

                if (interventionDT.getStatusCd() == null)
                  preparedStmt.setString(i++, interventionDT.getStatusCd());
              else
                preparedStmt.setString(i++, interventionDT.getStatusCd().trim());
           	if (interventionDT.getStatusTime() == null)//32
                {
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getStatusTime());
                preparedStmt.setString(i++, interventionDT.getTargetSiteCd());//33
                preparedStmt.setString(i++, interventionDT.getTargetSiteDescTxt());//34
                preparedStmt.setString(i++, interventionDT.getTxt());//35
                preparedStmt.setString(i++, interventionDT.getUserAffiliationTxt());//36
                preparedStmt.setString(i++, interventionDT.getLocalId());//37
                preparedStmt.setString(i++, interventionDT.getJurisdictionCd());//38
                preparedStmt.setString(i++, interventionDT.getProgAreaCd());//39
                preparedStmt.setString(i++, interventionDT.getCdSystemCd());//40
                preparedStmt.setString(i++, interventionDT.getCdSystemDescTxt());//41
                if(interventionDT.getVersionCtrlNbr() == null)//42
                {
                        logger.error("** VersionCtrlNbr cannot be null *** :" + interventionDT.getVersionCtrlNbr());
                } else
                {
                        logger.debug("VersionCtrlNbr exists" + interventionDT.getVersionCtrlNbr());
                        preparedStmt.setInt(i++,(interventionDT.getVersionCtrlNbr().intValue()));
                        logger.debug("new versioncontrol number:" +(interventionDT.getVersionCtrlNbr().intValue()));
                }
                if(interventionDT.getProgramJurisdictionOid() == null)//43
                  preparedStmt.setNull(i++,Types.BIGINT);
                else
                  preparedStmt.setLong(i++,interventionDT.getProgramJurisdictionOid().longValue());
                preparedStmt.setString(i++,interventionDT.getSharedInd());//44
                
                preparedStmt.setString(i++, interventionDT.getMaterialCd());
                if(interventionDT.getAgeAtVacc() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getAgeAtVacc()).intValue());
                preparedStmt.setString(i++, interventionDT.getAgeAtVaccUnitCd());
                preparedStmt.setString(i++, interventionDT.getVaccMfgrCd());
                preparedStmt.setString(i++, interventionDT.getMaterialLotNm());
                if (interventionDT.getMaterialExpirationTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, interventionDT.getMaterialExpirationTime());
                if(interventionDT.getVaccDoseNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, (interventionDT.getVaccDoseNbr()).intValue());
                preparedStmt.setString(i++, interventionDT.getVaccInfoSourceCd());
                
                if(interventionDT.getInterventionUid() == null)//45
                   preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++, (interventionDT.getInterventionUid()).longValue());
                if(interventionDT.getVersionCtrlNbr() == null)//46
                {
                  logger.error("** VersionCtrlNbr cannot be null here :" + interventionDT.getVersionCtrlNbr());
                }
                else
                {
                  logger.debug("VersionCtrlNbr exists here:" + interventionDT.getVersionCtrlNbr());
                  preparedStmt.setInt(i++,interventionDT.getVersionCtrlNbr().intValue()-1);
                }
                logger.debug("sql query is :"+preparedStmt.toString());

                resultCount = preparedStmt.executeUpdate();
      	        logger.debug("Done updating intervention, UID = " + (interventionDT.getInterventionUid()).longValue());
                if ( resultCount <= 0 )
                {
                    logger.error
                            ("Error: none or more than one intervention updated at a time, " +
                              "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "intervention into INTERVENTION_TABLE: \n", sex);
            throw new NEDSSDAOSysException( sex.toString() );
        }
        finally
        {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);

        }
     }//end of updating intervention table

    private InterventionDT selectIntervention (long interventionUID) throws NEDSSSystemException
    {
        InterventionDT interventionDT = new InterventionDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

       /**
         * Selects intervention from Intervention table
         */

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_INTERVENTION);
            preparedStmt.setLong(1, interventionUID);
            resultSet = preparedStmt.executeQuery();
		    logger.debug("InterventionDT object for: interventionUID = " + interventionUID);
           /* if(!resultSet.next())
                throw new NEDSSSystemException("No record for this primary key, PK = " + interventionUID);
            */
            int i = 1;
            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, interventionDT.getClass(), pList);
           // logger.debug("Size of intervention list in interventionDaoImpl is: " +  pList.size());
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                interventionDT = (InterventionDT)anIterator.next();
                interventionDT.setItNew(false);
                interventionDT.setItDirty(false);
            }


        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " +
                            "intervention vo; id = " + interventionUID , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "intervention vo; id = " + interventionUID, ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	logger.debug("return intervention object");
        return interventionDT;
    }//end of selecting intervention ethnic groups

    private void removeIntervention (long interventionUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Deletes intervention ethnic groups
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_INTERVENTION);
            preparedStmt.setLong(1, interventionUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete intervention from INTERVENTION_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "intervention; id = " + interventionUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing intervention

/**    protected synchronized Connection getConnection(){
      Connection conn = null;
      try{
        logger.debug("test21");
        Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
        logger.debug("test1");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "qauser", "qauser");
      logger.debug("test2");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
    }

    public static void main(String args[]){
      //NedssUtils nu = new NedssUtils();
      //Connection dbConnection = nu.getTestConnection();
      logger.debug("InterventionDAOImpl - Doing the main thing");

      //Connection dbConnection = null;

      try{

      // To test update
  /**      InterventionDAOImpl obsDAOI = new InterventionDAOImpl();
        InterventionDAOImpl obsNameDAOI = new InterventionDAOImpl();
        Long uid = new Long(10);
        InterventionDT obsDT = new InterventionDT();
        obsDT.setActivityDurationAmt("OBS");
        obsDT.setActivityDurationUnitCd("OBS  Test");
        obsDT.setInterventionUid(uid);
        Timestamp t = new Timestamp(new java.util.Date().getTime());
        obsDT.setActivityFromTime(t);
        obsDT.setActivityToTime(t);
        obsDT.setAddReasonCd("OBSReason");
        Timestamp y = new Timestamp(1231267673);
        obsDT.setAddTime(y);
        obsDT.setAddUserId(uid);
        obsDT.setCd("OBS");
        obsDT.setCdDescTxt("OBS full");
        obsDT.setConfidentialityCd("OBS 321");
        obsDT.setConfidentialityDescTxt("OBS Test");
        obsDT.setEffectiveDurationAmt("OBS test");
        obsDT.setEffectiveDurationUnitCd("OBS test");
        obsDT.setEffectiveFromTime(y);
        obsDT.setEffectiveToTime(t);
        obsDT.setLastChgReasonCd("OBS reason");
        obsDT.setLastChgTime(y);
        obsDT.setLastChgUserId(uid);
        obsDT.setLocalId("OBS Test");
        obsDT.setMethodCd("OBS Method");
        obsDT.setMethodDescTxt("OBS Desc Txt");
        obsDT.setInterventionUid(uid);
        obsDT.setOrgAccessPermis("Org Permission");
        obsDT.setPriorityCd("OBS Priority");
        obsDT.setProgAreaAccessPermis("OBS ProgAreaAccessPermis");
        obsDT.setRecordStatusCd("OBS Status");
        obsDT.setRecordStatusTime(t);
        obsDT.setRepeatNbr(new Integer(1));
        obsDT.setStatusCd("C");
        obsDT.setTargetSiteCd("OBS Target");
        obsDT.setTargetSiteDescTxt("OBS TargetDesc Txt");
        obsDT.setTxt("OBS text");
        obsDT.setItNew(true);
        obsDT.setItDirty(false);
        Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        cObsNameDT.add(obsDT);
      InterventionDAOImpl Obs = new   InterventionDAOImpl();
      Obs.store(obsDT);
      //Obs.updateIntervention(obsDT);
*/
 //To Test create
 /**       Long uid = new Long(10);
        InterventionDT obsDT = new InterventionDT();
        obsDT.setActivityDurationAmt("OBS test");
        obsDT.setActivityDurationUnitCd("OBS Activity CD");
        obsDT.setInterventionUid(uid);
        Timestamp t = new Timestamp(new java.util.Date().getTime());
        obsDT.setActivityFromTime(t);
        obsDT.setActivityToTime(t);
        obsDT.setAddReasonCd("OBS Reason");
        Timestamp y = new Timestamp(123132323);
        obsDT.setAddTime(y);
        obsDT.setAddUserId(uid);
        obsDT.setCd("OBS");
        obsDT.setCdDescTxt("OBS full");
        obsDT.setConfidentialityDescTxt("OBS Test");
        obsDT.setEffectiveToTime(t);
        obsDT.setLastChgReasonCd("OBS reason");
        obsDT.setLastChgTime(y);
        obsDT.setLastChgUserId(uid);
        obsDT.setLocalId("OBS Test");
        obsDT.setMethodCd("OBS Method");
        obsDT.setMethodDescTxt("OBS Desc Txt");
        obsDT.setInterventionUid(uid);
        obsDT.setOrgAccessPermis("Org Permission");
        obsDT.setPriorityCd("OBS Priority");
        obsDT.setRecordStatusCd("OBS Status");
        obsDT.setRecordStatusTime(t);
        obsDT.setRepeatNbr(new Integer(1));
        obsDT.setStatusCd("C");

        obsDT.setTargetSiteCd("OBS Target");
        obsDT.setTargetSiteDescTxt("OBS TargetDesc Txt");
        obsDT.setTxt("OBS text");
        obsDT.setItNew(true);
        obsDT.setItDirty(false);
       // Collection<Object>  cObsNameDT = new ArrayList<Object> ();

        //cObsNameDT.add(obsDT);
        InterventionDAOImpl intervention1 = new   InterventionDAOImpl();
        intervention1.create(obsDT);


//For findByPrimaryKey
/**        InterventionDAOImpl Obs1 = new   InterventionDAOImpl();
        Long l = Obs1.findByPrimaryKey(47);
*/
        //for Remove method
/**
        InterventionDAOImpl Obs = new   InterventionDAOImpl();
        Obs.remove(42);
*/
/**
      }catch(Exception e){
        logger.debug("\n\nInterventionDAOImpl ERROR : Not working \n" + e);
      }

}
*/


}//end of InterventionDAOImpl class

