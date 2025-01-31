/**
 * Name:		MaterialDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for the
 *               Material value object in the Material entity bean.
 *               This class encapsulates all the JDBC calls made by the MaterialEJB
 *               for a Material object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of MaterialEJB is
 *               implemented here.
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/05/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     11/20/2001 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.entity.material.ejb.dao;

// java.* imports
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
// gov.cdc.nedss.* imports
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
// javax.* imports


public class MaterialDAOImpl extends BMPBase
{
   /* static {
      DEBUG_MODE = false;
//      DEBUG_MODE = true;
    }*/
    //private static final String ENTITY_UID = "ENTITY_UID";
    private long materialUid = -1;
    static final LogUtils logger = new LogUtils(MaterialDAOImpl.class.getName());

    private static final String SPACE = " ";
    private static final String COMMA = ",";

    public static final String SELECT_MATERIAL_UID =
        "SELECT"+ SPACE+
            "material_uid AS MaterialUid"+ SPACE+
        "FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
        "material_uid = ? ";
    public static final String INSERT_ENTITY = "INSERT INTO " + DataTables.ENTITY_TABLE + "(entity_uid, class_cd) VALUES (?, ?)";

    public static final String SELECT_MATERIAL =
         "SELECT"+ SPACE+
            "material_uid AS \"materialUid\" "+ COMMA+
            "add_reason_cd AS \"addReasonCd\" "+ COMMA+
            "add_time AS \"addTime\" "+ COMMA+
            "add_user_id AS \"addUserId\" "+ COMMA+
            "cd AS \"cd\" "+ COMMA+
            "cd_desc_txt AS \"cdDescTxt\" "+ COMMA+
            "description AS \"description\" "+ COMMA+
            "effective_duration_amt AS \"effectiveDurationAmt\" "+ COMMA+
            "effective_duration_unit_cd AS \"effectiveDurationUnitCd\" "+ COMMA+
            "effective_from_time AS \"effectiveFromTime\" "+ COMMA+
            "effective_to_time AS \"effectiveToTime\" "+ COMMA+
            "form_cd AS \"formCd\" "+ COMMA+
            "form_desc_txt AS \"formDescTxt\" "+ COMMA+
            "handling_cd AS \"handlingCd\" "+ COMMA+
            "handling_desc_txt AS \"handlingDescTxt\" "+ COMMA+
            "last_chg_reason_cd AS \"lastChgReasonCd\" "+ COMMA+
            "last_chg_time AS \"lastChgTime\" "+ COMMA+
            "last_chg_user_id AS \"lastChgUserId\" "+ COMMA+
            "local_id AS \"localId\" "+ COMMA+
            "nm AS \"nm\" "+ COMMA+
            "qty AS \"qty\" "+ COMMA+
            "qty_unit_cd AS \"qtyUnitCd\" "+ COMMA+
            "record_status_cd AS \"recordStatusCd\" "+ COMMA+
            "record_status_time AS \"recordStatusTime\" "+ COMMA+
            "risk_cd AS \"riskCd\" "+ COMMA+
            "risk_desc_txt AS \"riskDescTxt\" "+ COMMA+
            "status_cd AS \"statusCd\" "+ COMMA+
            "status_time AS \"statusTime\" "+ COMMA+
            "user_affiliation_txt AS \"userAffiliationTxt\" "+ COMMA+
            "version_ctrl_nbr AS \"versionCtrlNbr\" "+ SPACE+
        "FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ?";

    public static final String DELETE_MATERIAL =
        "DELETE FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ?";

         public static final String INSERT_MATERIAL =
        "INSERT INTO"+ SPACE +
            DataTables.MATERIAL_TABLE+ SPACE+
          "("+
            "material_uid"+ COMMA+
            "add_reason_cd"+ COMMA+
            "add_time"+ COMMA+
            "add_user_id"+ COMMA+
            "cd"+ COMMA+
            "cd_desc_txt"+ COMMA+
            "description"+ COMMA+
            "effective_duration_amt"+ COMMA+
            "effective_duration_unit_cd"+ COMMA+
            "effective_from_time"+ COMMA+
            "effective_to_time"+ COMMA+
            "form_cd"+ COMMA+
            "form_desc_txt"+ COMMA+
            "handling_cd"+ COMMA+
            "handling_desc_txt"+ COMMA+
            "last_chg_reason_cd"+ COMMA+
            "last_chg_time"+ COMMA+
            "last_chg_user_id"+ COMMA+
            "local_id"+ COMMA+
            "nm"+ COMMA+
            "qty"+ COMMA+
            "qty_unit_cd"+ COMMA+
            "record_status_cd"+ COMMA+
            "record_status_time"+ COMMA+
            "risk_cd"+ COMMA+
            "risk_desc_txt"+ COMMA+
            "status_cd"+ COMMA+
            "status_time"+ COMMA+
            "user_affiliation_txt"+ COMMA+
            "version_ctrl_nbr"+ SPACE+
          ")"+
        "VALUES"+ SPACE+
          "("+
            "?"+ COMMA+ //             "material_uid"
            "?"+ COMMA+ //             "add_reason_cd"
            "?"+ COMMA+ //             "add_time"
            "?"+ COMMA+ //             "add_user_id"
            "?"+ COMMA+ //             "cd"
            "?"+ COMMA+ //             "cd_desc_txt"
            "?"+ COMMA+ //             "description"
            "?"+ COMMA+ //             "effective_duration_amt"
            "?"+ COMMA+ //             "effective_duration_unit_cd"
            "?"+ COMMA+ //             "effective_from_time"
            "?"+ COMMA+ //             "effective_to_time"
            "?"+ COMMA+ //             "form_cd"
            "?"+ COMMA+ //             "form_desc_txt"
            "?"+ COMMA+ //             "handling_cd"
            "?"+ COMMA+ //             "handling_desc_txt"
            "?"+ COMMA+ //             "last_chg_reason_cd"
            "?"+ COMMA+ //             "last_chg_time"
            "?"+ COMMA+ //             "last_chg_user_id"
            "?"+ COMMA+ //             "local_id"
            "?"+ COMMA+ //             "nm"
            "?"+ COMMA+ //             "qty"
            "?"+ COMMA+ //             "qty_unit_cd"
            "?"+ COMMA+ //             "record_status_cd"
            "?"+ COMMA+ //             "record_status_time"
            "?"+ COMMA+ //             "risk_cd"
            "?"+ COMMA+ //             "risk_desc_txt"
            "?"+ COMMA+ //             "status_cd"
            "?"+ COMMA+ //             "status_time"
            "?"+ COMMA+ //             "user_affiliation_txt"
            "?"+        //             "version_ctrl_nbr"
          ")";

     public static final String UPDATE_MATERIAL =
        "UPDATE"+ SPACE +
            DataTables.MATERIAL_TABLE+ SPACE+
        "SET"+ SPACE+
            "add_reason_cd = ?"+ COMMA+
            "add_time = ?"+ COMMA+
            "add_user_id = ?"+ COMMA+
            "cd = ?"+ COMMA+
            "cd_desc_txt = ?"+ COMMA+
            "description = ?"+ COMMA+
            "effective_duration_amt = ?"+ COMMA+
            "effective_duration_unit_cd = ?"+ COMMA+
            "effective_from_time = ?"+ COMMA+
            "effective_to_time = ?"+ COMMA+
            "form_cd = ?"+ COMMA+
            "form_desc_txt = ?"+ COMMA+
            "handling_cd = ?"+ COMMA+
            "handling_desc_txt = ?"+ COMMA+
            "last_chg_reason_cd = ?"+ COMMA+
            "last_chg_time = ?"+ COMMA+
            "last_chg_user_id = ?"+ COMMA+
            "local_id = ?"+ COMMA+
            "nm = ?"+ COMMA+
            "qty = ?"+ COMMA+
            "qty_unit_cd = ?"+ COMMA+
            "record_status_cd = ?"+ COMMA+
            "record_status_time = ?"+ COMMA+
            "risk_cd = ?"+ COMMA+
            "risk_desc_txt = ?"+ COMMA+
            "status_cd = ?"+ COMMA+
            "status_time = ?"+ COMMA+
            "user_affiliation_txt = ?"+ COMMA+
            "version_ctrl_nbr = ?"+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ? AND version_ctrl_nbr = ?";




    /**
     * Constructor
     * @return    An Instance of MaterialDAOImpl
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAODupKeyException NEDSS Material Duplicate Key Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     */
    public MaterialDAOImpl()
      throws
	NEDSSDAOAppException,
//	NEDSSMaterialDAODupKeyException,
//	NEDSSMaterialDAOUpdateException,
	NEDSSDAOSysException
    {
    }

    /**
     * Creates(Inserts) a Material entry in the database
     * @param obj Material Object
     *
     * @return    Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAODupKeyException NEDSS Material Duplicate Key Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public long create(Object obj)
      throws
	NEDSSSystemException,
	NEDSSDAOSysException

    {
    	try{
			logger.debug("Creates a material in dao."); // debugging
		
		      materialUid = insertMaterial((MaterialDT)obj);
			logger.debug("(From inserting "+  DataTables.MATERIAL_TABLE +")Material UID = " + materialUid); // debugging
			((MaterialDT)obj).setItNew(false);
			((MaterialDT)obj).setItDirty(false);
			return materialUid;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Stores(Updates) a Material entry in the database
     * @param obj Material Object
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSConcurrentDataException, NEDSSSystemException
    {
    	try{
    		updateMaterial((MaterialDT)obj);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Removes(Deletes) a Material entry in the database
     * @param materialUid Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
     public void remove(long materialUid)
      throws NEDSSSystemException, NEDSSDAOSysException
    {
    	 try{
    		 removeMaterial(materialUid);
    	 }catch(NEDSSDAOSysException ex){
     		logger.fatal("materialUid: "+materialUid+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
     		throw new NEDSSDAOSysException(ex.toString());
     	 }catch(Exception ex){
     		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
     		throw new NEDSSSystemException(ex.toString());
     	 }
    }

    /**
     * Loads(Selects) a Material entry from the database
     * @param materialUid Material UID
     *
     * @return    Material Object
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOFinderException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public Object loadObject(long materialUid)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
			MaterialDT materialDT = selectMaterial(materialUid);
			materialDT.setItNew(false);
			materialDT.setItDirty(false);
			return materialDT;
    	}catch(NEDSSDAOSysException ex){
     		logger.fatal("materialUid: "+materialUid+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
     		throw new NEDSSDAOSysException(ex.toString());
     	}catch(Exception ex){
     		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
     		throw new NEDSSSystemException(ex.toString());
     	}
    }

    /**
     * Finds a Metrial entry in the database using its materialUid as the primary key
     * @param materialUid Material UID
     *
     * @return    Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOFinderException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public Long findByPrimaryKey(long materialUid)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
			if (materialExists(materialUid))
			    return (new Long(materialUid));
			else
			    logger.error("No material found for this primary key :" + materialUid);
			    return null;
    	}catch(NEDSSDAOSysException ex){
     		logger.fatal("materialUid: "+materialUid+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
     		throw new NEDSSDAOSysException(ex.toString());
     	}catch(Exception ex){
     		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
     		throw new NEDSSSystemException(ex.toString());
     	}
    }

    /**
     * Checks the existance of a Metrial entry in the database or not
     * @param materialUid Material UID
     *
     * @return    <code>true</code> if exists else <code>false</code>
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    protected boolean materialExists (long materialUid)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	boolean returnValue = false;

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("Error obtaining dbConnection " +
		"for checking material existence.", nsex);
	    throw new NEDSSSystemException(nsex.toString());
	}

	try
	{

	    preparedStmt = dbConnection.prepareStatement(SELECT_MATERIAL_UID);

	      logger.debug("materialUid = " + materialUid); // debugging
	      logger.debug("--> SQL Statement:\n" + SELECT_MATERIAL_UID);  // debugging
	    preparedStmt.setLong(1, materialUid);
	    resultSet = preparedStmt.executeQuery();

	    if (!resultSet.next())
	    {
		returnValue = false;
	    }
	    else
	    {
		materialUid = resultSet.getLong(1);
		returnValue = true;
	    }
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while checking for an"
			    + " existing material's uid in material table-> " + materialUid, sex);
	    throw new NEDSSDAOSysException( sex.toString());
	}
	catch(Exception ex)
	{
	    logger.fatal("Exception while checking for an"
			    + " existing material's uid in material table-> " +
			    materialUid, ex);
	    throw new NEDSSDAOSysException( ex.toString());
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
     * Inserts a Metrial record into the database
     * @param materialDT Material Data Table
     *
     * @return    Material Uid
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private long insertMaterial(MaterialDT materialDT)
      throws NEDSSSystemException, NEDSSDAOSysException
    {
	/*
	 * Starts inserting a new material
	 */
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	String localUID = null;
	UidGeneratorHelper uidGen = null;
	int resultCount = 0;

	try
	{
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining db connection " +
                    "while inserting into material table", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                /*
                 * Inserts into entity table for material
                 */
                uidGen = new UidGeneratorHelper();
                materialUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                logger.debug("--> MaterialUid:\n" + materialUid);  // debugging
                preparedStmt = dbConnection.prepareStatement(INSERT_ENTITY);

                  logger.debug("--> SQL Statement:\n" + INSERT_ENTITY);  // debugging


                int i = 1;
                preparedStmt.setLong(i++, materialUid);
                preparedStmt.setString(i++, NEDSSConstants.MATERIAL);
                resultCount = preparedStmt.executeUpdate();

                // close statement before it is reused
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);

            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for " + DataTables.MATERIAL_TABLE, sex);
                throw new NEDSSDAOSysException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ENTITY_TABLE, id = " + materialUid, ex);
                throw new NEDSSSystemException(ex.toString());
            }

            try
            {
                if (resultCount != 1)
                {
                    throw new NEDSSDAOSysException("Error while inserting " +
                            "uid into " + DataTables.ENTITY_TABLE + " for a new material, resultCount = " +
                            resultCount);
                }
                /*
                 * inserts into MATERIAL_TABLE
                 */


                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_MATERIAL);
                uidGen = new UidGeneratorHelper();
                logger.debug("--> After the new instance of uidGen - material UID:\n" + materialUid);  // debugging
                localUID = uidGen.getLocalID(UidClassCodes.MATERIAL_CLASS_CODE);
                logger.debug("--> Local UID:\n" + localUID);  // debugging
                  logger.debug("--> SQL Statement:\n" + INSERT_MATERIAL);  // debugging

                int i = 1;

                preparedStmt.setLong(i++, materialUid);


                if (materialDT.getAddReasonCd() == null)  // default value (Brent)
                  preparedStmt.setString(i++, "Add");
                else
                  preparedStmt.setString(i++, materialDT.getAddReasonCd());
                if(materialDT.getAddTime() == null)
                {
                     preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                  preparedStmt.setTimestamp(i++, materialDT.getAddTime());
                if(materialDT.getAddUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++, materialDT.getAddUserId().longValue());

                preparedStmt.setString(i++, materialDT.getCd());

                preparedStmt.setString(i++, materialDT.getCdDescTxt());

                preparedStmt.setString(i++, materialDT.getDescription());

                preparedStmt.setString(i++, materialDT.getEffectiveDurationAmt());

                preparedStmt.setString(i++, materialDT.getEffectiveDurationUnitCd());

                if (materialDT.getEffectiveFromTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, materialDT.getEffectiveFromTime());

                if (materialDT.getEffectiveToTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, materialDT.getEffectiveToTime());

                preparedStmt.setString(i++, materialDT.getFormCd());

                preparedStmt.setString(i++, materialDT.getFormDescTxt());

                preparedStmt.setString(i++, materialDT.getHandlingCd());

                preparedStmt.setString(i++, materialDT.getHandlingDescTxt());

                preparedStmt.setString(i++, materialDT.getLastChgReasonCd());

                if (materialDT.getLastChgTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, materialDT.getLastChgTime());

                if(materialDT.getLastChgUserId() == null)
                  preparedStmt.setNull(i++, Types.INTEGER);
                else
                  preparedStmt.setLong(i++, materialDT.getLastChgUserId().longValue());

                preparedStmt.setString(i++, localUID);

                preparedStmt.setString(i++, materialDT.getNm());

            //    preparedStmt.setString(i++, materialDT.getOrgAccessPermis());

            //    preparedStmt.setString(i++, materialDT.getProgAreaAccesPermis());

                preparedStmt.setString(i++, materialDT.getQty());

                preparedStmt.setString(i++, materialDT.getQtyUnitCd());

                preparedStmt.setString(i++, materialDT.getRecordStatusCd());

                if(materialDT.getRecordStatusTime() == null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                  preparedStmt.setTimestamp(i++, materialDT.getRecordStatusTime());

                preparedStmt.setString(i++, materialDT.getRiskCd());

                preparedStmt.setString(i++, materialDT.getRiskDescTxt());

                if(materialDT.getStatusCd() == null)
                  preparedStmt.setString(i++, materialDT.getStatusCd());
                else
                   preparedStmt.setString(i++, materialDT.getStatusCd().trim());

                if(materialDT.getStatusTime() == null)
                {
                     preparedStmt.setNull(i++, Types.TIMESTAMP);                    //19
                }
                else
                   preparedStmt.setTimestamp(i++, materialDT.getStatusTime());

                preparedStmt.setString(i++, materialDT.getUserAffiliationTxt());
                //preparedStmt.setInt(i++, materialDT.getVersionCtrlNbr().intValue());
    /*
                    if(materialDT.getVersionCtrlNbr() == null) {
                            logger.debug("VersionCtrlNbr is null *** ");
                            preparedStmt.setInt(i++, 1);
                    } else {
                            logger.debug("VersionCtrlNbr exists");
                            preparedStmt.setInt(i++, materialDT.getVersionCtrlNbr().intValue());
                    }

    */
                preparedStmt.setInt(i++, materialDT.getVersionCtrlNbr().intValue());
                resultCount = preparedStmt.executeUpdate();
                  logger.debug("done insert material! materialUid = " + materialUid); // debugging
                return materialUid;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "material into "+ DataTables.MATERIAL_TABLE, sex);
                throw new NEDSSDAOSysException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into "+ DataTables.MATERIAL_TABLE + ", id = " + materialUid, ex);
                throw new NEDSSSystemException(ex.toString());
            }
        }
	finally
	{
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}

    }//end of inserting material

   /**
     * Updates a Metrial record in the database
     * @param materialDT Material Data Table
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void updateMaterial (MaterialDT materialDT)
      throws NEDSSDAOSysException, NEDSSConcurrentDataException, NEDSSSystemException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	int resultCount = 0;

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("Error obtaining db connection " +
		"while updating material table", nsex);
	    throw new NEDSSSystemException(nsex.toString());
	}

	try
	{
	    //Updates material table
	  if (materialDT != null)
	    {
	      logger.debug("Updating materialDT: UID = " + materialDT.getMaterialUid().longValue());  // debugging
	    preparedStmt = dbConnection.prepareStatement(UPDATE_MATERIAL);
	      logger.debug("--> SQL Statement:\n" + UPDATE_MATERIAL);  // debugging

	    int i = 1;

	    if (materialDT.getAddReasonCd() == null)  // default value (Brent)
	      preparedStmt.setString(i++, "Add");
	    else
	      preparedStmt.setString(i++, materialDT.getAddReasonCd());

	    if (materialDT.getAddTime() == null)
	         preparedStmt.setNull(i++, Types.TIMESTAMP);
	    else
	      preparedStmt.setTimestamp(i++, materialDT.getAddTime());

	    if(materialDT.getAddUserId() == null)
	      preparedStmt.setNull(i++, Types.INTEGER);
	    else
	      preparedStmt.setLong(i++, materialDT.getAddUserId().longValue());

	    preparedStmt.setString(i++, materialDT.getCd());

	    preparedStmt.setString(i++, materialDT.getCdDescTxt());

	    preparedStmt.setString(i++, materialDT.getDescription());

	    preparedStmt.setString(i++, materialDT.getEffectiveDurationAmt());

	    preparedStmt.setString(i++, materialDT.getEffectiveDurationUnitCd());

	    if (materialDT.getEffectiveFromTime() == null)
	      preparedStmt.setNull(i++, Types.TIMESTAMP);
	    else
	      preparedStmt.setTimestamp(i++, materialDT.getEffectiveFromTime());

	    if (materialDT.getEffectiveToTime() == null)
	      preparedStmt.setNull(i++, Types.TIMESTAMP);
	    else
	      preparedStmt.setTimestamp(i++, materialDT.getEffectiveToTime());

	    preparedStmt.setString(i++, materialDT.getFormCd());

	    preparedStmt.setString(i++, materialDT.getFormDescTxt());

	    preparedStmt.setString(i++, materialDT.getHandlingCd());

	    preparedStmt.setString(i++, materialDT.getHandlingDescTxt());

	    preparedStmt.setString(i++, materialDT.getLastChgReasonCd());

	    if (materialDT.getLastChgTime() == null)
	      preparedStmt.setNull(i++, Types.TIMESTAMP);
	    else
	      preparedStmt.setTimestamp(i++, materialDT.getLastChgTime());

	    if(materialDT.getLastChgUserId() == null)
	      preparedStmt.setNull(i++, Types.INTEGER);
	    else
	      preparedStmt.setLong(i++, materialDT.getLastChgUserId().longValue());

	    preparedStmt.setString(i++, materialDT.getLocalId());

	    preparedStmt.setString(i++, materialDT.getNm());

	  //  preparedStmt.setString(i++, materialDT.getOrgAccessPermis());

	  //  preparedStmt.setString(i++, materialDT.getProgAreaAccesPermis());

	    preparedStmt.setString(i++, materialDT.getQty());

	    preparedStmt.setString(i++, materialDT.getQtyUnitCd());

	    preparedStmt.setString(i++, materialDT.getRecordStatusCd());

	    if(materialDT.getRecordStatusTime() == null)
	      preparedStmt.setNull(i++, Types.TIMESTAMP);
	    else
	      preparedStmt.setTimestamp(i++, materialDT.getRecordStatusTime());

	    preparedStmt.setString(i++, materialDT.getRiskCd());

	    preparedStmt.setString(i++, materialDT.getRiskDescTxt());

	    if(materialDT.getStatusCd() == null)
	      preparedStmt.setString(i++, materialDT.getStatusCd());
            else
               preparedStmt.setString(i++, materialDT.getStatusCd().trim());
	    if(materialDT.getStatusTime() == null)
            {
              preparedStmt.setNull(i++, Types.TIMESTAMP);
            }
	    else
	       preparedStmt.setTimestamp(i++, materialDT.getStatusTime());

	    preparedStmt.setString(i++, materialDT.getUserAffiliationTxt());
            preparedStmt.setInt(i++, materialDT.getVersionCtrlNbr().intValue());

	    preparedStmt.setLong(i++, materialDT.getMaterialUid().longValue());
            preparedStmt.setInt(i++, materialDT.getVersionCtrlNbr().intValue()-1);


	    resultCount = preparedStmt.executeUpdate();
	      logger.debug("Done updating material, UID = " + (materialDT.getMaterialUid()).longValue());  // debugging
	    if ( resultCount != 1 ){
	      logger.error
		       ("Error: none or more than one material updated at a time, " +
			"resultCount = " + resultCount);
                        throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: MaterialDAOImpl.UpdateMaterial The data has been modified by other user, please verify!");
           }
	  }
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while updating " +
		    "material into "+ DataTables.MATERIAL_TABLE, sex);
	    throw new NEDSSDAOSysException( sex.toString() );
	}
	finally
	{
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}

    }//end of updating material table

   /**
     * Selects a Metrial record in the database
     * @param materialUid Material UID
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     */
    private MaterialDT selectMaterial (long materialUid)
      throws NEDSSDAOSysException
    {
	MaterialDT materialDT = new MaterialDT();
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
			    "for selectMaterial ", nsex);
	    throw new NEDSSSystemException( nsex.toString());
	}

	/*
	 * Selects material from Material table
	 */

	try
	{
	    preparedStmt = dbConnection.prepareStatement(SELECT_MATERIAL);
	      logger.debug("--> SQL Statement:\n" + SELECT_MATERIAL);  // debugging

	    preparedStmt.setLong(1, materialUid);
	    resultSet = preparedStmt.executeQuery();
	      logger.debug("MaterialDT object for: materialUid = " + materialUid);  // debugging

	    resultSetMetaData = resultSet.getMetaData();

	    materialDT = (MaterialDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, materialDT.getClass());
	    materialDT.setItNew(false);
	    materialDT.setItDirty(false);
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while selecting " +
			    "material vo; id = " + materialUid, sex);
	    throw new NEDSSDAOSysException( sex.toString());
	}
	catch(Exception ex)
	{
	    logger.fatal("Exception while selecting " +
			    "material vo; id = " + materialUid, ex);
	    throw new NEDSSDAOSysException(ex.toString());
	}
	finally
	{
	    closeResultSet(resultSet);
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
	logger.debug("return material object"); // debugging
	return materialDT;
    }//end of selecting material ethnic groups

   /**
     * Removes a Metrial record from the database
     * @param materialUid Material UID
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     */
    private void removeMaterial (long materialUid)
      throws NEDSSDAOSysException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	int resultCount = 0;

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("SQLException while obtaining database connection " +
			    "for deleting material ethnic groups ", nsex);
	    throw new NEDSSSystemException( nsex.toString());
	}

	/*
	 * Deletes material
	 */
	try
	{
	    preparedStmt = dbConnection.prepareStatement(DELETE_MATERIAL);
	      logger.debug("--> :\n" + DELETE_MATERIAL);  // debugging

	    preparedStmt.setLong(1, materialUid);
	    resultCount = preparedStmt.executeUpdate();

	    if (resultCount != 1)
	    {
		logger.error
		("Error: cannot delete material from "+ DataTables.MATERIAL_TABLE + "!! resultCount = " +
		 resultCount);
	    }
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while removing " +
			    "material; id = " + materialUid, sex);
	    throw new NEDSSDAOSysException( sex.toString());
	}
	finally
	{
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
    }//end of removing material


   /**
     * Removes a Metrial record from the database
     * @param materialUid Material UID
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     */
    private void test() {

    }//end of removing material



}//end of MaterialDAOImpl class
