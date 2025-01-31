/**
* Name:		NonPersonLivingSubjectHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               NonPersonLivingSubjectHist value object inserting into NonPersonLivingSubjectHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS development team
* @version	1.0
*/

package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao;

import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.NonPersonLivingSubjectDT;
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
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;


public class NonPersonLivingSubjectHistDAOImpl extends BMPBase {
  static final LogUtils logger = new LogUtils(NonPersonLivingSubjectHistDAOImpl.class.getName());
  private long nonPersonUid = -1;
  private short versionCtrlNbr = 0;

  private final String SELECT_NONPERSON_VERSION_CTRL_NBR_HIST =
    "select version_ctrl_nbr from non_person_living_subject_hist where non_person_uid = ?";

  private final String INSERT_MATERIAL_HIST =
    "insert into non_person_living_subject_hist("+
    "non_person_uid, "+
    "version_ctrl_nbr, "+
    "add_reason_cd, "+
    "add_time, "+
    "add_user_id, "+
    "birth_sex_cd, "+
    "birth_order_nbr, "+
    "birth_time, "+
    "breed_cd, "+
    "breed_desc_txt, "+
    "cd, "+
    "cd_desc_txt, "+
    "deceased_ind_cd, "+
    "deceased_time, "+
    "description, "+
    "last_chg_reason_cd, "+
    "last_chg_time, "+
    "last_chg_user_id, "+
    "local_id, "+
    "multiple_birth_ind, "+
    "nm, "+
    "record_status_cd, "+
    "record_status_time, "+
    "status_cd, "+
    "status_time, "+
    "taxonomic_classification_cd, "+
    "taxonomic_classification_desc, "+
    "user_affiliation_txt) "+
    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  private final String SELECT_NONPERSON_LIVING_SUBJECT_HIST =
    "select non_person_uid \"nonPersonUid\", "+
    "version_ctrl_nbr \"versionCtrlNbr\", "+
    "add_reason_cd \"addReasonCd\", "+
    "add_time \"addTime\", "+
    "add_user_id \"addUserId\", "+
    "birth_sex_cd \"birthSexCd\", "+
    "birth_order_nbr \"birthOrderNbr\", "+
    "birth_time \"birthTime\", "+
    "breed_cd \"breedCd\", "+
    "breed_desc_txt \"breedDescTxt\", "+
    "cd \"cd\", "+
    "cd_desc_txt \"cdDescTxt\", "+
    "deceased_ind_cd \"deceasedIndCd\", "+
    "deceased_time \"deceasedTime\", "+
    "description \"description\", "+
    "last_chg_reason_cd \"lastChgReasonCd\", "+
    "last_chg_time \"lastChgTime\", "+
    "last_chg_user_id \"lastChgUserId\", "+
    "local_id \"localId\", "+
    "multiple_birth_ind \"multipleBirthInd\", "+
    "nm \"nm\", "+
    "record_status_cd \"recordStatusCd\", "+
    "record_status_time \"recordStatusTime\", "+
    "status_cd \"statusCd\", "+
    "status_time \"statusTime\", "+
    "taxonomic_classification_cd \"taxonomicClassificationCd\", "+
    "taxonomic_classification_desc \"taxonomicClassificationDesc\", "+
    "user_affiliation_txt \"userAffiliationTxt\" "+
    "from non_person_living_subject_hist "+
    "where non_person_uid = ? and version_ctrl_nbr = ?";
  /**
   * Default constructor
  */
  public NonPersonLivingSubjectHistDAOImpl()
  {
  }
   /**
   * Initializes the class attributes to the passed in parameters
   * @param nonPersonUid
   * @param versionCtrlNbr
   */
  public NonPersonLivingSubjectHistDAOImpl(long nonPersonUid, short versionCtrlNbr){
    this.nonPersonUid = nonPersonUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextNonPersonVersionCtrlNbr();
  }//end of constructor
    /**
     * Results in the insertion of a record into the database.
     * @param obj  Represents the dt object to be stored.
     * @throws NEDSSDAOSysException
     */
  public void store(Object obj)
      throws NEDSSDAOSysException {
	  try{
        NonPersonLivingSubjectDT dt = (NonPersonLivingSubjectDT)obj;
        if(dt == null)
           throw new NEDSSDAOSysException("Error: try to store null NonPersonLivingDT object.");
        insertNonPersonLivingSubjectHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }
    }//end of store()

     /**
     * Stores the collection of dt objects
     * @param coll  A collection of dt objects to be inserted into the database.
     * @throws NEDSSDAOSysException
     */
    public void store(Collection<Object> coll)throws NEDSSDAOSysException {
    	try{
		  Iterator<Object>  it = coll.iterator();
	      while(it.hasNext()) {
	        this.store(it.next());
	      }//end of while
    	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSDAOSysException(ex.toString());
  	  	}
    }//end of store

   /**
    * Populated and returns a DT object based on the parameters passed in.
    * @param nonPersonUid   Is a Long object.
    * @param versionCtrlNbr  A Integer object.
    * @return NonPersonLivingSubjectDT
    * throws NEDSSSystemException
    */
    public NonPersonLivingSubjectDT load(Long nonPersonUid, Integer versionCtrlNbr) throws NEDSSSystemException
      {
    	try{
		    logger.info("Starts loadObject() for a nonperson living subject history...");
	        NonPersonLivingSubjectDT nplsDT = selectNonPersonLivingSubjectHist(nonPersonUid.longValue(), versionCtrlNbr.intValue());
	        nplsDT.setItNew(false);
	        nplsDT.setItDirty(false);
	        logger.info("Done loadObject() for a nonperson living subject history - return: " + nplsDT.toString());
	        return nplsDT;
    	}catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	    }
    }//end of load

    /**
     * Returns the versionCtrlNbr which has been used to initialize this object
     * @return versionCtrlNbr : Short  The versionCtrlNbr which is a class attribute.
    */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  ////////////////////////////private class methods////////////////////
   /**
     * Loads the nonPerson record with the specified nonPersonUid and versionCtrlNbr
     * @param nonPersonUid long  The nonPerson uid of the record to be loaded
     * @param versionCtrlNbr int  The versionCtrlNbr of the record to be loaded.
     * @return NonPersonLivingSubjectDT   The nonPersonLivingSubjectDT of the specified search criteria.
     * throws NEDSSSystemException
   */
   private NonPersonLivingSubjectDT selectNonPersonLivingSubjectHist(long nonPersonUid, int versionCtrlNbr)throws NEDSSSystemException
   {

        NonPersonLivingSubjectDT nplsDT = new NonPersonLivingSubjectDT();
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
                            "for selectNonPersonLivingSubjectHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_NONPERSON_LIVING_SUBJECT_HIST);
            preparedStmt.setLong(1, nonPersonUid);
            preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

            logger.debug("MaterialDT object for nonPerson Living Subject History: nonPersonUid = " + nonPersonUid);

            resultSetMetaData = resultSet.getMetaData();

            nplsDT = (NonPersonLivingSubjectDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, nplsDT.getClass());

            nplsDT.setItNew(false);
            nplsDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "NonPerson Living Subject history; NonPersonUid = " + nonPersonUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "nonperson living subject dt; nonPersonUid = " + nonPersonUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return nonPersonLivingSubjectDT for nonperson living subject history");

        return nplsDT;
  }//end of selectNonPersonLivingSubjectHist(...)

    /**
     * Inserts the nonPersonLivingSubject dt record into the database
     * @param dt The nonPersonLivingSubjectDT to be inserted into the database.
     * @return void
     * @throws NEDSSDAOSysException
     */
   private void insertNonPersonLivingSubjectHist(NonPersonLivingSubjectDT dt)throws NEDSSDAOSysException
     {
    if(dt.getNonPersonUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_MATERIAL_HIST);
              pStmt.setLong(i++, dt.getNonPersonUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

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

              if(dt.getBirthSexCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getBirthSexCd());

              if(dt.getBirthOrderNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getBirthOrderNbr().shortValue());

              if(dt.getBirthTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getBirthTime());

              if(dt.getBreedCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getBreedCd());

              if(dt.getBreedDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getBreedDescTxt());

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getDeceasedIndCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDeceasedIndCd());

              if(dt.getDeceasedTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getDeceasedTime());

              if(dt.getDescription() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDescription());

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

              if(dt.getMultipleBirthInd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMultipleBirthInd());

              if(dt.getNm() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getNm());

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

              if(dt.getTaxonomicClassificationCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTaxonomicClassificationCd());

              if(dt.getTaxonomicClassificationDesc() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTaxonomicClassificationDesc());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSDAOSysException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertMaterialEncounterHist()

  /**
     * Gets the nextNonPersonVersionCtrlNbr
     * @return void
  */
  private void getNextNonPersonVersionCtrlNbr() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_NONPERSON_VERSION_CTRL_NBR_HIST);
        pStmt.setLong(1, nonPersonUid);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("NonPersonHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > versionCtrlNbr ) versionCtrlNbr = seqTemp;
        }
        ++versionCtrlNbr;
        logger.debug("NonPersonHistDAOImpl--versionCtrlNbr: " + versionCtrlNbr);
    } catch(SQLException se) {
    	logger.fatal("SQLException  = "+se.getMessage(), se);
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextNonPersonVersionCtrlNbr()
}//end of NonPersonLivingSubjectHistDAOImpl}//end of NonPersonLivingSubjectHistDAOImpl