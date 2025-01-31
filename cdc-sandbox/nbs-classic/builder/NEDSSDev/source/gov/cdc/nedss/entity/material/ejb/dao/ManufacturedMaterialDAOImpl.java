package gov.cdc.nedss.entity.material.ejb.dao;

/**
* Name:		ManufacturedMaterialDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               manufactured_material value object in the material entity bean.
*               This class encapsulates all the JDBC calls made by the material ejb
*               for a manufactured_material object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of manufactured_material is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen
* @version	1.0
*/

import gov.cdc.nedss.entity.material.dt.ManufacturedMaterialDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
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


public class ManufacturedMaterialDAOImpl extends BMPBase
{
 	private static final String SPACE = " ";
        private static final String COMMA = ",";

        private static final String SELECT_MANUFACTUREDMATERIAL_UID =
        "SELECT"+ SPACE+
        "material_uid"+ SPACE+
         "FROM"+ SPACE+
         DataTables.MANUFACT_MATERIAL_TABLE+ SPACE+
          "WHERE"+ SPACE+
        "material_uid = ?";

	private static final String INSERT_MANUFACTUREDMATERIAL =
        "INSERT INTO"+ SPACE+
          DataTables.MANUFACT_MATERIAL_TABLE+ SPACE+
        "("+
        "material_uid"+ COMMA+
        "manufactured_material_seq"+ COMMA+
        "add_reason_cd"+ COMMA+
        "add_time"+ COMMA+
        "add_user_id"+ COMMA+
        "expiration_time"+ COMMA+
        "last_chg_reason_cd"+ COMMA+
        "last_chg_time"+ COMMA+
        "last_chg_user_id"+ COMMA+
        "lot_nm"+ COMMA+
        "record_status_cd"+ COMMA+
        "record_status_time"+ COMMA+
        "user_affiliation_txt"+ COMMA+
        "stability_from_time"+ COMMA+
        "stability_to_time"+ COMMA+
        "stability_duration_cd"+ COMMA+
        "stability_duration_unit_cd"+ SPACE+
        ")"+
        "VALUES"+ SPACE+
        "("+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+ COMMA+
        "?"+
        ")";

	private static final String UPDATE_MANUFACTUREDMATERIAL =
        "UPDATE"+ SPACE+
         DataTables.MANUFACT_MATERIAL_TABLE+ SPACE+
         "SET"+ SPACE+
         "add_reason_cd = ?"+ COMMA+
         "add_time = ?"+ COMMA+
         "add_user_id = ?"+ COMMA+
         "expiration_time = ?"+ COMMA+
         "last_chg_reason_cd = ?"+ COMMA+
         "last_chg_time = ?"+ COMMA+
         "last_chg_user_id = ?"+ COMMA+
         "lot_nm = ?"+ COMMA+
         "record_status_cd = ?"+ COMMA+
         "record_status_time = ?"+ COMMA+
         "user_affiliation_txt = ?"+ COMMA+
         "stability_from_time = ?"+ COMMA+
         "stability_to_time = ?"+ COMMA+
         "stability_duration_cd = ?"+ COMMA+
         "stability_duration_unit_cd = ?"+ SPACE+
         "WHERE"+ SPACE+
         "material_uid = ?"+ SPACE+
         "AND"+ SPACE+
         "manufactured_material_seq = ?";
	private static final String SELECT_MANUFACTUREDMATERIAL =
        "SELECT"+ SPACE+
        "material_uid AS \"materialUid\" "+ COMMA+
        "manufactured_material_seq AS \"manufacturedMaterialSeq\" "+ COMMA+
        "add_reason_cd AS \"addReasonCd\" "+ COMMA+
        "add_time AS \"addTime\" "+ COMMA+
        "add_user_id AS \"addUserId\" "+ COMMA+
        "expiration_time AS \"expirationTime\" "+ COMMA+
        "last_chg_reason_cd AS \"lastChgReasonCd\" "+ COMMA+
        "last_chg_time AS \"lastChgTime\" "+ COMMA+
        "last_chg_user_id AS \"lastChgUserId\" "+ COMMA+
        "lot_nm AS \"lotNm\" "+ COMMA+
        "record_status_cd AS \"recordStatusCd\" "+ COMMA+
        "record_status_time AS \"recordStatusTime\" "+ COMMA+
        "user_affiliation_txt AS \"userAffiliationTxt\" "+ COMMA+
        "stability_from_time AS \"stabilityFromTime\" "+ COMMA+
        "stability_to_time AS \"stabilityToTime\" "+ COMMA+
        "stability_duration_cd AS \"stabilityDurationCd\" "+ COMMA+
        "stability_duration_unit_cd AS \"stabilityDurationUnitCd\" "+ SPACE+
        "FROM"+ SPACE+
        DataTables.MANUFACT_MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
        "material_uid = ?";

	private static final String DELETE_MANUFACTUREDMATERIAL = "";

        //For debugging
        private static final LogUtils logger = new LogUtils(ManufacturedMaterialDAOImpl.class.getName());

    public ManufacturedMaterialDAOImpl()
    {
    }

    public long create(long materialUid, Collection<Object>  coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
			logger.debug("Create method called");
			insertCollection(materialUid, coll);

			return materialUid;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		updateCollection(coll);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object>  load(long materialUid)  throws NEDSSDAOSysException,
		 NEDSSSystemException
    {
    	try{
	        Collection<Object>  coll = selectCollection(materialUid);
	        return coll;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long materialUid) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if (itemExists(materialUid))
	            return (new Long(materialUid));
	        else
	            throw new NEDSSDAOSysException("Primary key not found in MANUFACTURED_MATERIAL_TABLE:"
	            + materialUid);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    private void insertCollection(long materialUid,  Collection<Object>  coll) throws NEDSSSystemException
    {
       Iterator<Object>  anIterator = null;
        ArrayList<Object> collList = (ArrayList<Object> )coll;

        try
        {
            anIterator = collList.iterator();

            while(anIterator.hasNext())
            {
                ManufacturedMaterialDT manufacturedMaterial = (ManufacturedMaterialDT)anIterator.next();

                if (manufacturedMaterial != null)
                    insertManufacturedMaterial(materialUid, manufacturedMaterial);

                manufacturedMaterial.setMaterialUid(new Long(materialUid));
            }
        }
        catch(Exception ex)
        {
            logger.fatal("materialUid:"+materialUid+" Error while inserting manufactured material", ex);
            throw new NEDSSSystemException("Exception while inserting " +
                    "manufactured materials into MANUFACTURED_MATERIAL_TABLE: \n" + ex.toString() +
                    " \n" + ex.getMessage());
        }
        logger.info("Done inserting all manufactired materials");
    }//end of inserting manufactured materials

    private void insertManufacturedMaterial(long materialUid,
            ManufacturedMaterialDT manufacturedMaterialDT)
            throws NEDSSSystemException
    {
        logger.debug("/n/n/ninsertManufactured material Manufacturedmaterial is it new : "+ manufacturedMaterialDT.isItNew());
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("materialUid:"+materialUid+" Error obtaining dbConnection " +
                "for updating MANUFACTURED_MATERIAL_TABLE", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {

            preparedStmt = dbConnection.prepareStatement(this.INSERT_MANUFACTUREDMATERIAL);

            int i = 1;
			// set FKs
			preparedStmt.setLong(i++,materialUid);
			
			// set nonFKs
			if (manufacturedMaterialDT.getManufacturedMaterialSeq() == null)
                 throw new NEDSSSystemException("field manufacturedMaterialSeq is null and the database tables don't allow it.");
            else
                 preparedStmt.setInt(i++,manufacturedMaterialDT.getManufacturedMaterialSeq().intValue());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getAddReasonCd());
            if (manufacturedMaterialDT.getAddTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getAddTime());
            if (manufacturedMaterialDT.getAddUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,manufacturedMaterialDT.getAddUserId().longValue());
            if (manufacturedMaterialDT.getExpirationTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getExpirationTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getLastChgReasonCd());
            if (manufacturedMaterialDT.getLastChgTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getLastChgTime());
            if (manufacturedMaterialDT.getLastChgUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,manufacturedMaterialDT.getLastChgUserId().longValue());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getLotNm());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getRecordStatusCd());
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getRecordStatusTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getUserAffiliationTxt());
            if (manufacturedMaterialDT.getStabilityFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getStabilityFromTime());
            if (manufacturedMaterialDT.getStabilityToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getStabilityToTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getStabilityDurationCd());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getStabilityDurationUnitCd());


            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
            {
                   logger.error("Error: none or more than one manufactured materials inserted at a time, " +
                        "resultCount = " + resultCount);
            }
            else
            {
                manufacturedMaterialDT.setItNew(false);
                manufacturedMaterialDT.setItDirty(false);
                manufacturedMaterialDT.setItDelete(false);
            }
            //logger.error("New lag is: " + manufacturedMaterialDT.isItNew());
        }
        catch(SQLException sex)
        {
            logger.fatal("materialUid:"+materialUid+" SQLException while inserting " +
                    "a manufactured material into MANUFACTURED_MATERIAL_TABLE", sex);
            throw new NEDSSSystemException(sex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    private void updateCollection(Collection<Object> coll) throws NEDSSSystemException
    {
       Iterator<Object>  anIterator = null;
        //ManufacturedMaterialDT manufacturedMaterial = null;

        if(coll != null)
        {
            try
            {
                for(anIterator = coll.iterator(); anIterator.hasNext(); )
                {
                    ManufacturedMaterialDT manufacturedMaterial = (ManufacturedMaterialDT)anIterator.next();
                    if(manufacturedMaterial == null)
                    throw new NEDSSSystemException("Error: Empty manufactured material collection");
                    logger.debug("/n/n/nUpDate Collection<Object>  :Manufacturedmaterial is it new : "+ manufacturedMaterial.isItNew());
                    if(manufacturedMaterial.isItNew())
                        insertManufacturedMaterial((manufacturedMaterial.getMaterialUid()).longValue(), manufacturedMaterial);

                    if(manufacturedMaterial.isItDirty())
                        updateManufacturedMaterial(manufacturedMaterial);
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "manufactured materials"+ex.getMessage(), ex);
                throw new NEDSSSystemException( ex.toString() );
            }
        }
    }//end of updating manufactured material table

    private void updateManufacturedMaterial(ManufacturedMaterialDT manufacturedMaterialDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a manufactured material
         */

        if(manufacturedMaterialDT != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating manufactured materials", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(this.UPDATE_MANUFACTUREDMATERIAL);

                int i = 1;

                     preparedStmt.setString(i++,manufacturedMaterialDT.getAddReasonCd());
            if (manufacturedMaterialDT.getAddTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getAddTime());
            if (manufacturedMaterialDT.getAddUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,manufacturedMaterialDT.getAddUserId().longValue());
            if (manufacturedMaterialDT.getExpirationTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getExpirationTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getLastChgReasonCd());
            if (manufacturedMaterialDT.getLastChgTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getLastChgTime());
            if (manufacturedMaterialDT.getLastChgUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,manufacturedMaterialDT.getLastChgUserId().longValue());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getLotNm());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getRecordStatusCd());
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getRecordStatusTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getUserAffiliationTxt());
            if (manufacturedMaterialDT.getStabilityFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getStabilityFromTime());
            if (manufacturedMaterialDT.getStabilityToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,manufacturedMaterialDT.getStabilityToTime());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getStabilityDurationCd());
                 preparedStmt.setString(i++,manufacturedMaterialDT.getStabilityDurationUnitCd());
            if (manufacturedMaterialDT.getMaterialUid() == null)
                 throw new NEDSSSystemException("field materialUid is null and the database tables don't allow it.");
            else
                 preparedStmt.setLong(i++,manufacturedMaterialDT.getMaterialUid().longValue());
            if (manufacturedMaterialDT.getManufacturedMaterialSeq() == null)
                 throw new NEDSSSystemException("field manufacturedMaterialSeq is null and the database tables don't allow it.");
            else
                 preparedStmt.setInt(i++,manufacturedMaterialDT.getManufacturedMaterialSeq().intValue());


                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1)
                logger.error("Error: none or more than one manufactured material updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "manufactured materials"+sex.getMessage(), sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating MANUFACTURED_MATERIAL_TABLE

    protected boolean itemExists (long materialUid) throws NEDSSDAOSysException, NEDSSSystemException

    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(this.SELECT_MANUFACTUREDMATERIAL_UID);
            preparedStmt.setLong(1, materialUid);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                materialUid =   resultSet.getLong(1);

                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
           logger.fatal("SQLException while checking for an"
                            + " existing material's uid in MANUFACTURED_MATERIAL_TABLE"  + materialUid, sex);
            throw new NEDSSDAOSysException(   sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing material's uid " + materialUid, nsex);
            throw new NEDSSDAOSysException( nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

    private Collection<Object>  selectCollection(long materialUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ManufacturedMaterialDT manufacturedMaterial= new ManufacturedMaterialDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                        "for selectManufacturedMaterials ", nsex);
            throw new NEDSSSystemException(  nsex.getMessage());
        }

        /**
         * Selects manufactured materials
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(this.SELECT_MANUFACTUREDMATERIAL);

            preparedStmt.setLong(1, materialUid);

            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object> itemList = new ArrayList<Object> ();
            ArrayList<Object> reSetList = new ArrayList<Object> ();

            itemList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, manufacturedMaterial.getClass(), itemList);


           /* if (pnList.isEmpty())
                throw new NEDSSSystemException("No record for this materialUid: " + materialUid);
            else */

            for(Iterator<Object> anIterator = itemList.iterator(); anIterator.hasNext(); )
            {
                ManufacturedMaterialDT reSetName = (ManufacturedMaterialDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetName.setItDelete(false);

                reSetList.add(reSetName);
            }

            logger.info("return manufactured material collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "manufactured material collection; uid = " + materialUid, se);
            throw new NEDSSSystemException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting manufactured materials; materialUid: "+materialUid, rsuex);
            throw new NEDSSSystemException(rsuex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "manufactured materials; uid = " + materialUid, ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

}
