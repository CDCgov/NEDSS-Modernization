/**
* Name:        NonPersonLivingSubjectDAOImpl.java
* Description: This is the implementation of NEDSSDAOInterface for the
*              NonPersonLivingSubject value object in the NonPersonLivingSubject entity bean.
*              This class encapsulates all the JDBC calls made by the NonPersonLivingSubjectEJB
*              for a NonPersonLivingSubject object. Actual logic of
*              inserting/reading/updating/deleting the data in relational
*              database tables to mirror the state of NonPersonLivingSubjectEJB is
*              implemented here.
* Copyright:   Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author      NEDSS Development Team
* @version     1.0
*/


package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao;


import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.NonPersonLivingSubjectDT;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
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

public class NonPersonLivingSubjectDAOImpl extends BMPBase
{

         public static final String SELECT_NONPERSON_UID =
              "SELECT "
              + " non_person_uid \"nonPersonUid\" "
              + " FROM "
              + DataTables.NONPERSON_TABLE
              + " WHERE non_person_uid = ?";
         public static final String DELETE_NONPERSON =
              "DELETE FROM "
              + DataTables.NONPERSON_TABLE
              + " WHERE non_person_uid = ?";

         public static final String INSERT_NONPERSON =
              "INSERT INTO "
              + DataTables.NONPERSON_TABLE
              + "(add_reason_cd, "
              + "add_time, "
              + "add_user_id, "
              + " birth_sex_cd,"
              + " birth_order_nbr, "
              + " birth_time, "
              + " breed_cd, "
              + " breed_desc_txt, "
              + " cd, "
              + " cd_desc_txt,"
              + " deceased_ind_cd,"
              + " deceased_time,"
              + " description, "
              + " last_chg_reason_cd, "
              + " last_chg_time, "
              + " last_chg_user_id, "
              + " local_id,"
              + " multiple_birth_ind,"
              + " nm, "
             // + " org_acces_permis,"
            //  + " prog_area_access_permis,"
              + " record_status_cd, "
              + " record_status_time,"
              + " status_cd, "
              + " status_time, "
              + " taxonomic_classification_cd, "
              + " taxonomic_classification_desc, "
              + " user_affiliation_txt,"
              + " non_person_uid , "
              + " vesrion_ctrl_nbr ) "
              + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
              + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
              + " ?, ?, ?, ?, ?, ?, ?, ? )";
    public static final String UPDATE_NONPERSON =
              "UPDATE "
              + DataTables.NONPERSON_TABLE
              + " set add_reason_cd = ?, "
              + " add_time = ?,  "
              + " add_user_id = ?, "
              + " birth_gender_cd = ?,  "
              + " birth_order_nbr = ?, "
              + " birth_time = ?, "
              + " breed_cd = ?, "
              + " breed_desc_txt = ?,"
              + " cd = ?, "
              + " cd_desc_txt = ?, "
              + " deceased_ind_cd = ?, "
              + " deceased_time = ?, "
              + " description = ?, "
              + " last_chg_reason_cd = ?, "
              + " last_chg_time = ?, "
              + " last_chg_user_id = ?, "
              + " multiple_birth_ind = ?,"
              + " nm = ?, "
             // + " org_acces_permis = ?,"
             // + " prog_area_access_permis = ?,"
              + " record_status_cd = ?, "
              + " record_status_time = ?, "
              + " status_cd = ?, "
              + " status_time = ?, "
              + " taxonomic_classification_cd = ?,"
              + " taxonomic_classification_desc = ?,"
              + " user_affiliation_txt = ? "
              + " vesrion_ctrl_nbr = ?  "
              +" WHERE non_person_uid = ? and version_ctrl_nbr =? ";
    public static final String SELECT_NONPERSON =
              "SELECT "
              + " add_reason_cd \"addReasonCd\", "
              + " add_time \"addTime\", "
              + " add_user_id \"addUserId\", "
              + " birth_gender_cd \"birthGenderCd\","
              + " birth_order_nbr \"birthOrderNbr\", "
              + " birth_time \"birthTime\", "
              + " breed_cd \"breedCd\", "
              + " breed_desc_txt \"breedDescTxt\","
              + " cd \"cd\", "
              + " cd_desc_txt \"cdDescTxt\", "
              + " deceased_ind_cd \"deceasedIndCd\", "
              + " deceased_time \"deceasedTime\", "
              + " description \"description\", "
              + " last_chg_reason_cd \"lastChgReasonCd\", "
              + " last_chg_time \"lastChgTime\", "
              + " last_chg_user_id \"lastChgUserId\", "
              + " local_id \"localId\", "
              + " multiple_birth_ind \"multipleBirthInd\","
              + " nm \"nm\", "
            //  + " org_acces_permis \"orgAccesPermis\", "
            //  + " prog_area_access_permis \"progAreaAccessPermis\", "
              + " record_status_cd \"recordStatusCd\", "
              + " record_status_time \"recordStatusTime\", "
              + " status_cd \"statusCd\", "
              + " status_time \"statusTime\", "
              + " taxonomic_classification_cd \"taxonomicClassificationCd\", "
              + " taxonomic_classification_desc \"taxonomicClassificationDesc\", "
              + " user_affiliation_txt \"userAffiliationTxt\", "
              + " non_person_uid \"nonPersonUid\" "
              + " vesrion_ctrl_nbr \"vesrionCtrlNbr\" "
              + " FROM "
              + DataTables.NONPERSON_TABLE
              + " WHERE non_person_uid = ?";
    // for testing - causes console print statments to print if true
    boolean DEBUG = false;

    //For Log4J logging
    static final LogUtils logger = new LogUtils(NonPersonLivingSubjectDAOImpl.class.getName());


    //private static final String ENTITY_UID = "ENTITY_UID";
    private long nonPersonLivingSubjectUID = -1;

    public NonPersonLivingSubjectDAOImpl() throws NEDSSDAOSysException, NEDSSDAOAppException
    {
    }

    /**
      * This method creates a new nonPersonLivingSubject record and returns the nonPersonLivingSubjectUID for this nonPersonLivingSubject.
      * @J2EE_METHOD  --  create
      * @param obj       the Object
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
    **/
    public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in create.");
	        nonPersonLivingSubjectUID = insertNonPersonLivingSubject((NonPersonLivingSubjectDT)obj);
	        if(DEBUG) logger.debug("(done inserting nonPersonLivingSubject_table)nonPersonLivingSubject UID = " + nonPersonLivingSubjectUID);
	        ((NonPersonLivingSubjectDT)obj).setItNew(false);
	        ((NonPersonLivingSubjectDT)obj).setItDirty(false);
	        return nonPersonLivingSubjectUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
     /**
      * This method is used to update a nonPersonLivingSubject record.
      * @J2EE_METHOD  --  store
      * @param obj       the Object
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
      **/
    public void store(Object obj) throws NEDSSDAOSysException,
                                         NEDSSSystemException,
                                         NEDSSConcurrentDataException
    {
    	try{
    		updateNonPersonLivingSubject((NonPersonLivingSubjectDT)obj);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
      * This method is used to delete a nonPersonLivingSubjectUID record.
      * @J2EE_METHOD  --  remove
      * @param nonPersonLivingSubjectUID long
      * @throws NEDSSDAOSysException
    **/
    public void remove(long NonPersonLivingSubjectUID) throws NEDSSDAOSysException
    {
    	try{
    		removeNonPersonLivingSubject(nonPersonLivingSubjectUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
     /**
      * This method loads a NonPersonLivingSubjectDT object for a given nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  loadObject
      * @param nonPersonLivingSubjectUID   the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Object loadObject(long nonPersonLivingSubjectUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl - loadObject nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
	        NonPersonLivingSubjectDT nonPersonLivingSubjectDT = selectNonPersonLivingSubject(nonPersonLivingSubjectUID);
	        nonPersonLivingSubjectDT.setItNew(false);
	        nonPersonLivingSubjectDT.setItDirty(false);
	        return nonPersonLivingSubjectDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
      /**
      * This method is used to determine is a nonPersonLivingSubject record exists for a given nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  findByPrimaryKey
      * @param nonPersonLivingSubjectUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Long findByPrimaryKey(long nonPersonLivingSubjectUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl - findByPrimaryKey nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
	        if (nonPersonLivingSubjectExists(nonPersonLivingSubjectUID))
	            return (new Long(nonPersonLivingSubjectUID));
	        else
	            logger.error("No nonPersonLivingSubject found for this primary key :" + nonPersonLivingSubjectUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
     /**
      * This method is used determine is a user record exists for a given nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  nonPersonLivingSubjectExists
      * @param nonPersonLivingSubjectUID       the long
      * @return boolean
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/

    protected boolean nonPersonLivingSubjectExists (long nonPersonLivingSubjectUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            if(DEBUG)logger.debug("NonPersonLivingSubjectDAO nonPersonLivingSubjectExists- got a connection");
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " +
                "for checking nonPersonLivingSubject existence.", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in nonPersonLivingSubjectExists, nonPersonLivingSubjectID = " + nonPersonLivingSubjectUID);
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in nonPersonLivingSubjectExists, NEDSSSqlQuery.SELECT_NONPERSON_UID = " + NEDSSSqlQuery.SELECT_NONPERSON_UID);
            preparedStmt = dbConnection.prepareStatement(SELECT_NONPERSON_UID);
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in nonPersonLivingSubjectExists, done with NEDSSSqlQuery.SELECT_NONPERSON_UID nonPersonLivingSubjectID = " + nonPersonLivingSubjectUID);
            preparedStmt.setLong(1, nonPersonLivingSubjectUID);
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in nonPersonLivingSubjectExists before execute query - NEDSSSqlQuery.SELECT_NONPERSON_UID="+NEDSSSqlQuery.SELECT_NONPERSON_UID);
            resultSet = preparedStmt.executeQuery();
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl in nonPersonLivingSubjectExists after execute query");

            if (!resultSet.next())
            {
                if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl.nonPersonLivingSubjectExists nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
                returnValue = false;
            }
            else
            {
                if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl.nonPersonLivingSubjectExists nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
                nonPersonLivingSubjectUID = resultSet.getLong(1);
                if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl.nonPersonLivingSubjectExists nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing nonPersonLivingSubject's uid in nonPersonLivingSubject table-> " + nonPersonLivingSubjectUID, sex);
            throw new NEDSSSystemException( sex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing nonPersonLivingSubject's uid in nonPersonLivingSubject table-> " +
                            nonPersonLivingSubjectUID, ex);
            throw new NEDSSSystemException( ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

     /**
      * This method is used to insert a new nonPersonLivingSubject record.
      * @J2EE_METHOD  --  insertNonPersonLivingSubject
      * @param nonPersonLivingSubjectDT       the NonPersonLivingSubjectDT
      * @return long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    private long insertNonPersonLivingSubject(NonPersonLivingSubjectDT nonPersonLivingSubjectDT)
                throws NEDSSDAOSysException, NEDSSSystemException
    {
        /**
         * Starts inserting a new nonPersonLivingSubject
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        //long nonPersonLivingSubjectUID = -1;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
            try
            {
                dbConnection = getConnection();
                if(DEBUG)logger.debug("NonPersonLivingSubjectDAO insertNonPersonLivingSubject - got a connection");
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining db connection " +
                    "while inserting into nonPersonLivingSubject table", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                /**
                 * Inserts into entity table for nonPersonLivingSubject
                 */
                uidGen = new UidGeneratorHelper();
                nonPersonLivingSubjectUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

                if(DEBUG)logger.debug("NonPersonLivingSubjectDAO insertNonPersonLivingSubject - NEDSSSqlQuery.INSERT_ENTITY = " + NEDSSSqlQuery.INSERT_ENTITY);
                preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);
                if(DEBUG)logger.debug("NonPersonLivingSubjectDAO insertNonPersonLivingSubject - NEDSSSqlQuery.INSERT_ENTITY = " + NEDSSSqlQuery.INSERT_ENTITY);

                int i = 1;
                preparedStmt.setLong(i++, nonPersonLivingSubjectUID);
                preparedStmt.setString(i++, NEDSSConstants.NONPERSONLIVINGSUBJECT);
                resultCount = preparedStmt.executeUpdate();

                // close statement before it is reused
                preparedStmt.close();
                preparedStmt = null;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for NONPERSON_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ENTITY_TABLE, nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }

            try
            {
                if (resultCount != 1)
                {
                    logger.error("Error while inserting " +
                            "uid into ENTITY_TABLE for a new nonPersonLivingSubject, resultCount = " +
                            resultCount);
                }

                /**
                 * inserts into nonPersonLivingSubject_TABLE
                 */

                preparedStmt = dbConnection.prepareStatement(INSERT_NONPERSON);
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.NON_LIVING_SUBJECT_CLASS_CODE);
                if(DEBUG) logger.debug("NonPersonLivingSubjectDAO insertNonPersonLivingSubject -  - after NEDSSSqlQuery.INSERT_NONPERSON");
                int i = 1;
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getAddReasonCd());       //1
                if(DEBUG) logger.debug("1");
                if (nonPersonLivingSubjectDT.getAddTime() == null)                            //2
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getAddTime());
                if(DEBUG) logger.debug("2");
                if(nonPersonLivingSubjectDT.getAddUserId() == null)                           //3
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (nonPersonLivingSubjectDT.getAddUserId()).longValue());
                if(DEBUG) logger.debug("3");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBirthSexCd());     //4
                if(DEBUG) logger.debug("4");
                if(nonPersonLivingSubjectDT.getBirthOrderNbr() == null)                       //5
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                preparedStmt.setInt(i++, nonPersonLivingSubjectDT.getBirthOrderNbr().intValue());
                if(DEBUG) logger.debug("5");
                if (nonPersonLivingSubjectDT.getBirthTime() == null)                          //6
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getBirthTime());
                if(DEBUG) logger.debug("6");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBreedCd());           //7
                if(DEBUG) logger.debug("7");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBreedDescTxt());      //8
                if(DEBUG) logger.debug("8");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getCd());                //9
                if(DEBUG) logger.debug("9");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getCdDescTxt());         //10
                if(DEBUG) logger.debug("10");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getDeceasedIndCd());     //11
                if(DEBUG) logger.debug("11");
                if (nonPersonLivingSubjectDT.getDeceasedTime() == null)                        //12
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getDeceasedTime());
                if(DEBUG) logger.debug("12");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getDescription());       //13
                if(DEBUG) logger.debug("13");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getLastChgReasonCd());   //14
                if(DEBUG) logger.debug("14");
                if (nonPersonLivingSubjectDT.getLastChgTime() == null)                        //15
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getLastChgTime());
                if(DEBUG) logger.debug("15");
                if(nonPersonLivingSubjectDT.getLastChgUserId() == null)                       //16
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, nonPersonLivingSubjectDT.getLastChgUserId().longValue());
                if(DEBUG) logger.debug("16");
                preparedStmt.setString(i++, localUID);           //17
                if(DEBUG) logger.debug("17");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getMultipleBirthInd());  //18
                if(DEBUG) logger.debug("18");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getNm());                //19
              /*  if(DEBUG) logger.debug("19");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getOrgAccessPermis());    //20
                if(DEBUG) logger.debug("20");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getProgAreaAccessPermis());//21
                */
                if(DEBUG) logger.debug("21");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getRecordStatusCd());    //21
                if(DEBUG) logger.debug("22");
                if (nonPersonLivingSubjectDT.getRecordStatusTime() == null)                         //23
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getRecordStatusTime());
                if(DEBUG) logger.debug("23");
                  preparedStmt.setString(i++, nonPersonLivingSubjectDT.getStatusCd());
                if(DEBUG) logger.debug("24");
                if (nonPersonLivingSubjectDT.getStatusTime() == null)                         //25
                {
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getStatusTime());
                if(DEBUG) logger.debug("25");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getTaxonomicClassificationCd());//26
                if(DEBUG) logger.debug("26");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getTaxonomicClassificationDesc());//27
                if(DEBUG) logger.debug("27");
                preparedStmt.setString(i++, nonPersonLivingSubjectDT.getUserAffiliationTxt()); //28
                if(DEBUG) logger.debug("28");
                preparedStmt.setLong(i++, nonPersonLivingSubjectUID);//29
                if(DEBUG) logger.debug("29");

                preparedStmt.setInt(i++, nonPersonLivingSubjectDT.getVersionCtrlNbr().intValue());//30
                if(DEBUG) logger.debug("30");

                if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl - before preparedStmt.executeUpdate()");
                resultCount = preparedStmt.executeUpdate();
                if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl - done insert nonPersonLivingSubject! nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
                return nonPersonLivingSubjectUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "nonPersonLivingSubject into nonPersonLivingSubject_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into nonPersonLivingSubject_TABLE, id = " + nonPersonLivingSubjectUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of inserting nonPersonLivingSubject

    /**
      * This method is used update a nonPersonLivingSubject record with a given NonPersonLivingSubjectDT.
      * @J2EE_METHOD  --  updateNonPersonLivingSubject
      * @param nonPersonLivingSubjectDT    the NonPersonLivingSubjectDT
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
    **/
    private void updateNonPersonLivingSubject
              (NonPersonLivingSubjectDT nonPersonLivingSubjectDT)
              throws NEDSSDAOSysException,
                      NEDSSSystemException,
                      NEDSSConcurrentDataException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
            if(DEBUG)logger.debug("NonPersonLivingSubjectDAO updateNonPersonLivingSubject- got a connection");
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining db connection " +
                "while updating nonPersonLivingSubject table", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            if(DEBUG)logger.debug("NonPersonLivingSubjectDAO updateNonPersonLivingSubject- Updating");
            //Updates nonPersonLivingSubject table
            if (nonPersonLivingSubjectDT != null)
            {
                if(DEBUG) logger.debug("Updating nonPersonLivingSubjectDT: UID = " + nonPersonLivingSubjectDT.getNonPersonUid());
                preparedStmt = dbConnection.prepareStatement(UPDATE_NONPERSON);
                if(DEBUG) logger.debug("Updating nonPersonLivingSubjectDT: NEDSSSqlQuery.UPDATE_NONPERSON = " + NEDSSSqlQuery.UPDATE_NONPERSON);
                int i = 1;
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getAddReasonCd());       //1
            if (nonPersonLivingSubjectDT.getAddTime() == null)                            //2
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getAddTime());
            if(nonPersonLivingSubjectDT.getAddUserId() == null)                           //3
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (nonPersonLivingSubjectDT.getAddUserId()).longValue());
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBirthSexCd());     //4
            if(nonPersonLivingSubjectDT.getBirthOrderNbr() == null)                       //5
                preparedStmt.setNull(i++, Types.INTEGER);
            else
            preparedStmt.setInt(i++, nonPersonLivingSubjectDT.getBirthOrderNbr().intValue());
            if (nonPersonLivingSubjectDT.getBirthTime() == null)                          //6
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getBirthTime());
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBreedCd());           //7
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getBreedDescTxt());      //8
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getCd());                //9
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getCdDescTxt());         //10
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getDeceasedIndCd());     //11
            if (nonPersonLivingSubjectDT.getDeceasedTime() == null)                        //12
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getDeceasedTime());
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getDescription());       //13
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getLastChgReasonCd());   //14
            if (nonPersonLivingSubjectDT.getLastChgTime() == null)                        //15
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getLastChgTime());
            if(nonPersonLivingSubjectDT.getLastChgUserId() == null)                       //16
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, nonPersonLivingSubjectDT.getLastChgUserId().longValue());
            //preparedStmt.setString(i++, nonPersonLivingSubjectDT.getLocalId());           //17
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getMultipleBirthInd());  //18
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getNm());                //19
          //  preparedStmt.setString(i++, nonPersonLivingSubjectDT.getOrgAccessPermis());    //20
         //   preparedStmt.setString(i++, nonPersonLivingSubjectDT.getProgAreaAccessPermis());//21
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getRecordStatusCd());    //22
            if (nonPersonLivingSubjectDT.getRecordStatusTime() == null)                         //23
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getRecordStatusTime());
              preparedStmt.setString(i++, nonPersonLivingSubjectDT.getStatusCd());
            if (nonPersonLivingSubjectDT.getStatusTime() == null)                         //25
            {
              preparedStmt.setNull(i++, Types.TIMESTAMP);
            }
            else
                preparedStmt.setTimestamp(i++, nonPersonLivingSubjectDT.getStatusTime());
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getTaxonomicClassificationCd());//26
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getTaxonomicClassificationDesc());//27
            preparedStmt.setString(i++, nonPersonLivingSubjectDT.getUserAffiliationTxt()); //28
            preparedStmt.setInt(i++, nonPersonLivingSubjectDT.getVersionCtrlNbr().intValue());//29

            if(DEBUG)
            logger.debug("Updating nonPersonLivingSubjectDT: UID = " + nonPersonLivingSubjectDT.getNonPersonUid().longValue());
            preparedStmt.setLong(i++, nonPersonLivingSubjectDT.getNonPersonUid().longValue());//30
            preparedStmt.setInt(i++, nonPersonLivingSubjectDT.getVersionCtrlNbr().intValue()-1);//31
            if(DEBUG) logger.debug("Getting ready to do preparedStmt.executeUpdate");
            resultCount = preparedStmt.executeUpdate();
            if(DEBUG) logger.debug("Done updating nonPerson, UID = " + nonPersonLivingSubjectDT.getNonPersonUid());
            if ( resultCount != 1 )
                 logger.error
                    ("Error: none or more than one nonPersonLivingSubject updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "nonPersonLivingSubject into nonPersonLivingSubject_TABLE: \n", sex);
            throw new NEDSSDAOSysException(sex.toString() );
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of updating nonPersonLivingSubject table
    /**
      * This method is used retrieve a NonPersonLivingSubjectDT for a specific NonPersonLivingSubject record, given the nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  selectNonPersonLivingSubject
      * @param nonPersonLivingSubjectUID       the long
      * @return NonPersonLivingSubjectDT
      * @throws NEDSSDAOSysException
    **/
    private NonPersonLivingSubjectDT selectNonPersonLivingSubject (long nonPersonLivingSubjectUID) throws NEDSSDAOSysException
    {
        if(DEBUG) logger.debug("NonPersonLivingSubjectDAOImpl - in selectNonPersonLivingSubject nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
        NonPersonLivingSubjectDT nonPersonLivingSubjectDT = new NonPersonLivingSubjectDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
            if(DEBUG)logger.debug("NonPersonLivingSubjectDAO selectNonPersonLivingSubject- got a connection");
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectnonPersonLivingSubject ", nsex);

            throw new NEDSSSystemException(nsex.toString());
        }

        /**
         * Selects nonPersonLivingSubject from nonPersonLivingSubject table
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_NONPERSON);
            if(DEBUG) logger.debug("NonPersonLivingSubjectDAO selectNonPersonLivingSubject - NEDSSSqlQuery.SELECT_NONPERSON = " + NEDSSSqlQuery.SELECT_NONPERSON);
            preparedStmt.setLong(1, nonPersonLivingSubjectUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            nonPersonLivingSubjectDT = (NonPersonLivingSubjectDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, nonPersonLivingSubjectDT.getClass());
            nonPersonLivingSubjectDT.setItNew(false);
            nonPersonLivingSubjectDT.setItDirty(false);

            if(DEBUG) logger.debug("nonPersonLivingSubjectDT object for: nonPersonLivingSubjectUID = " + nonPersonLivingSubjectUID);
            if(DEBUG) logger.debug("nonPersonLivingSubjectDT after mapRsToBean - NonPersonUid = " + nonPersonLivingSubjectDT.getNonPersonUid());
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " +
                            "nonPersonLivingSubject vo; id = " + nonPersonLivingSubjectUID, sex);

            throw new NEDSSDAOSysException( sex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "nonPersonLivingSubject vo; id = " + nonPersonLivingSubjectUID, ex);
            throw new NEDSSSystemException( ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        if(DEBUG) logger.debug("return nonPersonLivingSubject object");
        return nonPersonLivingSubjectDT;
    }//end of selecting nonPersonLivingSubject ethnic groups
     /**
      * This method is used delete a nonPersonLivingSubject record using its nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  removeNonPersonLivingSubject
      * @param nonPersonLivingSubjectUID       the long
      * @throws NEDSSDAOSysException
      **/
    private void removeNonPersonLivingSubject (long nonPersonLivingSubjectUID) throws NEDSSDAOSysException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
            if(DEBUG)logger.debug("NonPersonLivingSubjectDAO removeNonPersonLivingSubject- got a connection");
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for deleting nonPersonLivingSubject ethnic groups ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        /**
         * Deletes nonPersonLivingSubject ethnic groups
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_NONPERSON);
            preparedStmt.setLong(1, nonPersonLivingSubjectUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete nonPersonLivingSubject from nonPersonLivingSubject_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "nonPersonLivingSubject; id = " + nonPersonLivingSubjectUID, sex);
            throw new NEDSSDAOSysException( sex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing nonPersonLivingSubject

/*  override getConnection for testing purposes
    protected synchronized Connection getConnection() throws NEDSSSystemException
    {
      if(DEBUG)logger.debug("NonPersonLivingSubjectDAO - getting a connection");
      return nu.getTestConnection();
    }*/
}//end of nonPersonLivingSubjectDAOImpl class
