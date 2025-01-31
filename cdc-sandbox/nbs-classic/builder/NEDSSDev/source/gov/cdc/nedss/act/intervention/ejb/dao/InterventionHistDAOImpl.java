package gov.cdc.nedss.act.intervention.ejb.dao;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class InterventionHistDAOImpl extends BMPBase{

     public static final String SELECT_INTERVENTION_HIST =
     "SELECT intervention_uid \"InterventionUid\", "+
     "version_ctrl_nbr \"versionCtrlNbr\", "+
     "activity_duration_amt \"ActivityDurationAmt\", "+
     "activity_duration_unit_cd \"ActivityDurationUnitCd\", "+
     "activity_from_time \"ActivityFromTime\", "+
     "activity_to_time \"ActivityToTime\", "+
     "add_reason_cd \"AddReasonCd\", "+
     "add_time \"AddTime\", "+
     "add_user_id \"AddUserId\", "+
     "cd \"Cd\", "+
     "cd_desc_txt \"CdDescTxt\", "+
     "cd_system_cd \"CdSystemCd, "+
     "cd_system_desc_txt \"CdSystemDescTxt\", "+
     "class_cd \"ClassCd\", "+
     "confidentiality_cd \"ConfidentialityCd\", "+
     "confidentiality_desc_txt \"ConfidentialityDescTxt\", "+
     "effective_duration_amt \"EffectiveDurationAmt\", "+
     "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "+
     "effective_from_time \"EffectiveFromTime\", "+
     "effective_to_time \"EffectiveToTime\", "+
     "jurisdiction_cd \"JurisdictionCd\", "+
     "last_chg_reason_cd \"LastChgReasonCd\", "+
     "last_chg_time \"LastChgTime\", "+
     "last_chg_user_id \"LastChgUserId\", "+
     "local_id \"LocalId\", "+
     "method_cd \"MethodCd\", "+
     "method_desc_txt \"MethodDescTxt\", "+
  //   "org_access_permis \"OrgAccessPermis\", "+
     "priority_cd \"PriorityCd\", "+
     "priority_desc_txt \"PriorityDescTxt\", "+
  //   "prog_area_access_permis \"ProgAreaAccessPermis\", "+
     "prog_area_cd \"ProgAreaCd\", "+
     "qty_amt \"QtyAmt\", "+
     "qty_unit_cd \"QtyUnitCd\", "+
     "reason_cd \"ReasonCd\", "+
     "reason_desc_txt \"ReasonDescTxt\", "+
     "record_status_cd \"RecordStatusCd\", "+
     "record_status_time \"RecordStatusTime\", "+
     "repeat_nbr \"RepeatNbr\", "+
     "status_cd \"StatusCd\", "+
     "status_time \"StatusTime\", "+
     "target_site_cd \"TargetSiteCd\", "+
     "target_site_desc_txt \"TargetSiteDescTxt\", "+
     "txt \"Txt\", "+
     "shared_ind \"sharedInd\", "+
     "program_jurisdiction_oid \"programJurisdictionOid\", "+
     "user_affiliation_txt \"UserAffiliationTxt\" "+
     "FROM intervention_hist "+
     "WHERE intervention_uid = ? and version_ctrl_nbr = ?";

     public static final String INSERT_INTERVENTION_HIST =
     "INSERT into intervention_hist(intervention_uid, "+
     "version_ctrl_nbr, "+
     "activity_duration_amt, "+
     "activity_duration_unit_cd, "+
     "activity_from_time, "+
     "activity_to_time, "+
     "add_reason_cd, "+
     "add_time, "+
     "add_user_id, "+
     "cd, "+
     "cd_desc_txt, "+
     "cd_system_cd, "+
     "cd_system_desc_txt, "+
     "class_cd, "+
     "confidentiality_cd, "+
     "confidentiality_desc_txt, "+
     "effective_duration_amt, "+
     "effective_duration_unit_cd, "+
     "effective_from_time, "+
     "effective_to_time, "+
     "jurisdiction_cd, "+
     "last_chg_reason_cd, "+
     "last_chg_time, "+
     "last_chg_user_id, "+
     "local_id, "+
     "method_cd, "+
     "method_desc_txt, "+
 //    "org_access_permis, "+
     "priority_cd, "+
     "priority_desc_txt, "+
 //    "prog_area_access_permis, "+
     "prog_area_cd, "+
     "qty_amt, "+
     "qty_unit_cd, "+
     "reason_cd, "+
     "reason_desc_txt, "+
     "record_status_cd, "+
     "record_status_time, "+
     "repeat_nbr, "+
     "status_cd, "+
     "status_time, "+
     "target_site_cd, "+
     "target_site_desc_txt, "+
     "txt, "+
     "shared_ind, "+
     "program_jurisdiction_oid, "+
     "user_affiliation_txt, "+
     "electronic_ind, "+
     "material_cd, "+
     "age_at_vacc, "+
     "age_at_vacc_unit_cd, "+
     "vacc_mfgr_cd, "+
     "material_lot_nm, "+
     "material_expiration_time, "+
     "vacc_dose_nbr, "+
     "vacc_info_source_cd) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
     "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



  static final LogUtils logger = new LogUtils(InterventionHistDAOImpl.class.getName());

  private long interventionUid = -1;
  private short versionCtrlNbr = 0;

  public InterventionHistDAOImpl() throws  NEDSSSystemException {

  }//end of constructor

  public InterventionHistDAOImpl(long interventionUid, short versionCtrlNbr)throws
  NEDSSSystemException {
    this.interventionUid = interventionUid;
    this.versionCtrlNbr = versionCtrlNbr;
   // getNextInterventionHistId();
  }//end of constructor

  public void store(Object obj)
      throws  NEDSSSystemException {
	  	try{
	        InterventionDT dt = (InterventionDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null InterventionDT object.");
	          insertInterventionHist(dt);
	  	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of store()

    public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
    	try{
    		Iterator<Object>  iterator = null;
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
		        	store(iterator.next());
		        }//end of while
	        }//end of if
    	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of store(Collection)

    public InterventionDT load(Long intUid, Integer intHistSeq) throws NEDSSSystemException {
    	try{
    		logger.info("Starts loadObject() for a intervention history...");
	        InterventionDT intDT = selectInterventionHist(intUid.longValue(),intHistSeq.intValue());
	        intDT.setItNew(false);
	        intDT.setItDirty(false);
	        logger.info("Done loadObject() for a intervention history - return: " + intDT.toString());
	        return intDT;
    	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of load

   public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  ////////////////////////////////////private class methods///////////////////////////

  private InterventionDT selectInterventionHist(long intUid, int versionCtrlNbr)throws NEDSSSystemException {


        InterventionDT intDT = new InterventionDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectInterventionHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_INTERVENTION_HIST);
            preparedStmt.setLong(1, intUid);
	           preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("InterventionDT object for Intervention history: intUid = " + intUid);

            resultSetMetaData = resultSet.getMetaData();

            intDT = (InterventionDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, intDT.getClass());

            intDT.setItNew(false);
            intDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "intervention history; id = " + intUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "intervention vo; id = " + intUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return interventionDT for intervention history");

        return intDT;


  }//end of selectInterventionHist(...)

  private void insertInterventionHist(InterventionDT dt)throws
    NEDSSSystemException {
    if(dt.getInterventionUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {

              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_INTERVENTION_HIST);
              pStmt.setLong(i++, dt.getInterventionUid().longValue());
              pStmt.setInt(i++, dt.getVersionCtrlNbr().intValue());

              if(dt.getActivityDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationAmt());

              if(dt.getActivityDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationUnitCd());

              if(dt.getActivityFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityFromTime());

              if(dt.getActivityToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityToTime());

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getCdSystemCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdSystemCd());

              if(dt.getCdSystemDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdSystemDescTxt());

              if(dt.getClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getClassCd());

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityDescTxt());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveToTime());

              if(dt.getJurisdictionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getJurisdictionCd());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getMethodCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMethodCd());

              if(dt.getMethodDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMethodDescTxt());

          /*    if(dt.getOrgAccessPermis() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getOrgAccessPermis()); */

              if(dt.getPriorityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPriorityCd());

              if(dt.getPriorityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPriorityDescTxt());

         /*     if(dt.getProgAreaAccessPermis() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProgAreaAccessPermis());*/

              if(dt.getProgAreaCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProgAreaCd());

              if(dt.getQtyAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getQtyAmt());

              if(dt.getQtyUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getQtyUnitCd());

              if(dt.getReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReasonCd());

              if(dt.getReasonDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReasonDescTxt());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRepeatNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getRepeatNbr().shortValue());

                pStmt.setString(i++, dt.getStatusCd());

              if(dt.getStatusTime() == null)
                pStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
              else
                pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTargetSiteCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTargetSiteCd());

              if(dt.getTargetSiteDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTargetSiteDescTxt());

              if(dt.getTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTxt());

              pStmt.setString(i++, dt.getSharedInd());

              if(dt.getProgramJurisdictionOid() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              pStmt.setString(i++, dt.getElectronicInd());
              
              pStmt.setString(i++, dt.getMaterialCd());
              if(dt.getAgeAtVacc() == null)
            	  pStmt.setNull(i++, Types.INTEGER);
              else
            	  pStmt.setInt(i++, (dt.getAgeAtVacc()).intValue());
              pStmt.setString(i++, dt.getAgeAtVaccUnitCd());
              pStmt.setString(i++, dt.getVaccMfgrCd());
              pStmt.setString(i++, dt.getMaterialLotNm());
              if (dt.getMaterialExpirationTime() == null)
            	  pStmt.setNull(i++, Types.TIMESTAMP);
              else
            	  pStmt.setTimestamp(i++, dt.getMaterialExpirationTime());
              if(dt.getVaccDoseNbr() == null)
            	  pStmt.setNull(i++, Types.INTEGER);
              else
            	  pStmt.setInt(i++, (dt.getVaccDoseNbr()).intValue());
              pStmt.setString(i++, dt.getVaccInfoSourceCd());
              
              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertInterventionHist()

}//end of class
