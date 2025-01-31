package gov.cdc.nedss.act.clinicaldocument.ejb.dao;

import gov.cdc.nedss.act.clinicaldocument.dt.ClinicalDocumentDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
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

public class ClinicalDocumentHistDAOImpl extends BMPBase {
  static final LogUtils logger = new LogUtils(ClinicalDocumentHistDAOImpl.class.getName());
  private long clinicalDocumentUid = -1;
  private short versionCtrlNbr = 0;

  public static final String INSERT_CLINICAL_DOCUMENT_HIST =
                        "INSERT into clinical_document_hist("+
                        "clinical_document_uid, "+
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
                        "confidentiality_cd, "+
                        "confidentiality_desc_txt, "+
                        "copy_from_time, "+
                        "copy_to_time, "+
                        "effective_duration_amt, "+
                        "effective_duration_unit_cd, "+
                        "effective_from_time, "+
                        "effective_to_time, "+
                        "last_chg_reason_cd, "+
                        "last_chg_time, "+
                        "last_chg_user_id, "+
                        "local_id, "+
                        //"org_access_permis, "+
                        "practice_setting_cd, "+
                        "practice_setting_desc_txt, "+
                        //"prog_area_access_permis, "+
                        "record_status_cd, "+
                        "record_status_time, "+
                        "status_cd, "+
                        "status_time, "+
                        "txt, "+
                        "user_affiliation_txt, "+
                        "version_nbr, "+
                        "program_jurisdiction_oid, "+
                        "shared_ind) "+
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


  public static final String SELECT_CLINICAL_DOCUMENT_HIST =
                        "Select clinical_document_uid \"clinicalDocumentUid\", "+
                        "activity_duration_amt \"activityDurationAmt\", "+
                        "activity_duration_unit_cd \"activityDurationUnitCd\", "+
                        "activity_from_time \"activityFromTime\", "+
                        "activity_to_time \"activityToTime\", "+
                        "add_reason_cd \"addReasonCd\", "+
                        "add_time \"addTime\", "+
                        "add_user_id \"addUserId\", "+
                        "cd \"cd\", "+
                        "cd_desc_txt \"cdDescTxt\", "+
                        "confidentiality_cd \"confidentialityCd\", "+
                        "confidentiality_desc_txt \"confidentialityDescTxt\", "+
                        "copy_from_time \"copyFromTime\", "+
                        "copy_to_time \"copyToTime\", "+
                        "effective_duration_amt \"effectiveDurationAmt\", "+
                        "effective_duration_unit_cd \"effectiveDurationUnitCd\", "+
                        "effective_from_time \"effectiveFromTime\", "+
                        "effective_to_time \"effectiveToTime\", "+
                        "last_chg_reason_cd \"lastChgReasonCd\", "+
                        "last_chg_time \"lastChgTime\", "+
                        "last_chg_user_id \"lastChgUserId\", "+
                        "local_id \"localId\", "+
                        //"org_access_permis \"orgAccessPermis\", "+
                        "practice_setting_cd \"practiceSettingCd\", "+
                        "practice_setting_desc_txt \"practiceSettingDescTxt\", "+
                        //"prog_area_access_permis \"progAreaAccessPermis\", "+
                        "record_status_cd \"recordStatusCd\", "+
                        "record_status_time \"recordStatusTime\", "+
                        "status_cd \"statusCd\", "+
                        "status_time \"statusTime\", "+
                        "txt \"Txt\", "+
                        "user_affiliation_txt \"userAffiliationTxt\", "+
                        "version_ctrl_nbr \"versionCtrlNbr\", "+
                        "version_nbr \"versionNbr\", "+
                        "program_jurisdiction_oid \"programJurisdictionOid\", "+
                        "shared_ind \"sharedInd\" "+
                        "from clinical_document_hist where clinical_document_uid = ? and version_ctrl_nbr = ?";


  public ClinicalDocumentHistDAOImpl() {

  }//end of constructor

  public ClinicalDocumentHistDAOImpl(long clinicalDocumentUid, short versionCtrlNbr){
    this.clinicalDocumentUid = clinicalDocumentUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextClinicalDocumentHistId();
  }//end of constructor

  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ClinicalDocumentDT dt = (ClinicalDocumentDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null PatientEncounterDT object.");
        insertClinicalDocumentHist(dt);
	  }catch(NEDSSSystemException ex){
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
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }//end of store(Collection)

    public ClinicalDocumentDT load(Long cdUid, Integer versionCtrlNbr) throws NEDSSSystemException,
     NEDSSSystemException {
    	try{
		    logger.info("Starts loadObject() for a clinical document history...");
	        ClinicalDocumentDT cdDT = selectClinicalDocumentHist(cdUid.longValue(), versionCtrlNbr.intValue());
	        cdDT.setItNew(false);
	        cdDT.setItDirty(false);
	        logger.info("Done loadObject() for a clinical document history - return: " + cdDT.toString());
	        return cdDT;
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }//end of load

    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

    //////////////////////////private class methods///////////////////////

    private ClinicalDocumentDT selectClinicalDocumentHist(long cdUid, int cdHistSeq)throws NEDSSSystemException,
  NEDSSSystemException {


        ClinicalDocumentDT cdDT = new ClinicalDocumentDT();
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
                            "for selectClinicalDocumentHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_CLINICAL_DOCUMENT_HIST);
            preparedStmt.setLong(1, cdUid);
            preparedStmt.setLong(2, cdHistSeq);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ClinicalDocumentDT object for Clinical Document history: clinicalDocumentUid = " + cdUid);

            resultSetMetaData = resultSet.getMetaData();

            cdDT = (ClinicalDocumentDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, cdDT.getClass());

            cdDT.setItNew(false);
            cdDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "clinical document history; id = " + cdUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "clinical document vo; id = " + cdUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return clinicalDocumentDT for clinical document history");

        return cdDT;


  }//end of selectClinicalDocumentHist(...)

    private void insertClinicalDocumentHist(ClinicalDocumentDT dt)throws
    NEDSSSystemException {
    if(dt.getClinicalDocumentUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_CLINICAL_DOCUMENT_HIST);
              pStmt.setLong(i++, dt.getClinicalDocumentUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

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

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityDescTxt());

              if(dt.getCopyFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getCopyFromTime());

              if(dt.getCopyToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getCopyToTime());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveToTime());

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

              if(dt.getPracticeSettingCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPracticeSettingCd());

              if(dt.getPracticeSettingDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPracticeSettingDescTxt());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              pStmt.setString(i++, dt.getStatusCd());

              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              if(dt.getVersionNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getVersionNbr().shortValue());

              if(dt.getProgramJurisdictionOid() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

              pStmt.setString(i++, dt.getSharedInd());

              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
            logger.fatal("Error: SQLException while inserting "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertClinicalDocumentHist()

}//end of class
