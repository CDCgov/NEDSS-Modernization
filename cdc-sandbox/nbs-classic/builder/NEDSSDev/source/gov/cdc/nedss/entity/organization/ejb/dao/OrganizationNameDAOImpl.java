/**
* Name:		OrganizationNameDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               OrganizationName value object in the Organization entity bean.
*               This class encapsulates all the JDBC calls made by the OrganizationEJB
*               for a OrganizationName object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of OrganizationEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/
package gov.cdc.nedss.entity.organization.ejb.dao;

import java.util.*;
import java.sql.*;
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.sql.SQLException;
import java.math.BigDecimal;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.entity.sqlscript.*;

public class OrganizationNameDAOImpl extends DAOBase
{
    //NedssUtils nu = new NedssUtils();
    //Connection dbConnection = nu.getTestConnection();

    //For log4J logging
    static final LogUtils logger = new LogUtils(OrganizationNameDAOImpl.class.getName());
    public static final String SELECT_ORGANIZATION_NAME_UID =
                              "SELECT organization_UID FROM "
                              + DataTables.ORGANIZATION_NAME_TABLE
                              + " WHERE organization_UID = ?";
    public  static final String INSERT_ORGANIZATION_NAME =
                                "INSERT INTO "
                                + DataTables.ORGANIZATION_NAME_TABLE
                                + "(organization_uid, "
                                + "organization_name_seq, "
                                + "nm_txt, "
                                + "nm_use_cd, "
                                + "record_status_cd, "
                                + "default_nm_ind) "
                                + "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ORGANIZATION_NAME =
                                "UPDATE "
                                + DataTables.ORGANIZATION_NAME_TABLE
                                + " set nm_txt = ?, "
                                + "nm_use_cd = ?, "
                                + "record_status_cd = ?, "
                                + "default_nm_ind = ? "
                                + "WHERE organization_uid = ? "
                                + "AND organization_name_seq = ?";
    public static final String SELECT_ORGANIZATION_NAMES = "SELECT " +
                    "organization_UID \"organizationUid\", " +
                    "organization_name_seq \"organizationNameSeq\", " +
                    "nm_txt \"nmTxt\", " +
                    "nm_use_cd \"nmUseCd\", " +
                    "record_status_cd \"recordStatusCd\", " +
                    "default_nm_ind \"defaultNmInd\" " +
                    "FROM " + DataTables.ORGANIZATION_NAME_TABLE + " WHERE " +
                    "organization_UID = ?";

   public static final String DELETE_ORGANIZATION_NAMES = "DELETE FROM " +
                    DataTables.ORGANIZATION_NAME_TABLE +
                    " WHERE organization_uid = ?";

    public OrganizationNameDAOImpl()
    {
    }
    /**
    * Inserts the organization Name to the database by calling the
    * private method  insertOrganizationNames
    * @param organizationUID    the long
    * @param coll   the Collection
    * @return organizationUID   the long
    * @throws NEDSSSystemException
    */
    public long create(long organizationUID, Collection<Object>  coll) throws NEDSSSystemException
    {
    	try{
	        insertOrganizationNames(organizationUID, coll);
	        return organizationUID;
    	}catch(Exception ex){
    		logger.fatal("organizationUID: "+organizationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
   /**
    * updates the Collection<Object>  of organizationNames
    * @param coll   the Collection
    * @throws NEDSSSystemException
    */
    public void store(Collection<Object> coll) throws  NEDSSSystemException
    {
        updateOrganizationNames(coll);
    }
  /**
   * updates a record in organizationName table
   * @param organizationName    the OrganizationNameDT
   * @throws NEDSSSystemException
   */
    public void update(OrganizationNameDT organizationName) throws NEDSSSystemException
    {
        updateOrganizationName(organizationName);
    }
   /**
    * Returns the collection of Organization names based on the OrganizationUID
    * @param organizationUID    the long
    * @return  Collection
    * @throws NEDSSSystemException
    */
    public Collection<Object>  load(long organizationUID) throws NEDSSSystemException
    {
        Collection<Object>  pnColl = selectOrganizationNames(organizationUID);
        return pnColl;
    }
    /**
     * Removes Organization Names from the database based on the OrganizationUID
     * by using the private method removeOrganizationNames(long organizationUID)
     * @param organizationUID long
     * @throws NEDSSSystemException
     */
    public void remove(long organizationUID) throws NEDSSSystemException
    {
        removeOrganizationNames(organizationUID);
    }
    /**
     * Returns organizationUID if found (takes primitive and returns an object )
     * @param organizationUID    the long
     * @return Long
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long organizationUID) throws NEDSSSystemException
    {
        if (organizationNameExists(organizationUID))
            return (new Long(organizationUID));
        else
            logger.error("Primary key not found in Organization_NAME_TABLE:"
            + organizationUID);
            return null;
    }

    /**
     * Returns true if organization name exist
     * @param organizationUID  the long
     * @return boolean
     * @throws NEDSSSystemException
     * @throws NEDSSOrganizationDAOSysException
     */
    @SuppressWarnings("unchecked")

    protected boolean organizationNameExists (long organizationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;
        ArrayList<Object> arrayList  = new ArrayList<Object> ();
        arrayList.add(new Long(organizationUID));
        OrganizationDT orgDT = new OrganizationDT();
        try
        {
            arrayList = (ArrayList<Object> ) preparedStmtMethod(orgDT,arrayList,SELECT_ORGANIZATION_NAME_UID,
                                                            NEDSSConstants.SELECT);
            if (arrayList.size() != 0 )
            {
                orgDT = (OrganizationDT) arrayList.get(0);
                organizationUID = orgDT.getOrganizationUid().longValue();
                returnValue = true;
            }
        }
        catch(Exception nsex)
        {
            logger.fatal( " existing organizationUID -> " + organizationUID, nsex);
            throw new NEDSSSystemException( nsex.toString());
        }
        return returnValue;
    }

   /**
    * This method stores the collection of OrganizationName
    * @param organizationUID   the long
    * @param organizationNames
    * @throws NEDSSSystemException
    */
    private void insertOrganizationNames(long organizationUID, Collection<Object>  organizationNames)
                throws NEDSSSystemException
    {
         logger.debug("insertOrganizationNames(long organizationUID, Collection<Object>  organizationNames)");
       Iterator<Object>  anIterator = null;
        ArrayList<Object> organizationList = (ArrayList<Object> )organizationNames;
        try
        {
            /**
             * Inserts Organization names
             */
            anIterator = organizationList.iterator();
            while(anIterator.hasNext())
            {
              OrganizationNameDT organizationName = (OrganizationNameDT)anIterator.next();

                if(organizationName.getOrganizationNameSeq()==null)
                  organizationName.setOrganizationNameSeq(new Integer(3));

                if (organizationName != null){
                    insertOrganizationName(organizationUID, organizationName);
                }
                organizationName.setOrganizationUid(new Long(organizationUID));
                organizationName.setItNew(false);
                organizationName.setItDirty(false);
            }
        }
        catch(Exception ex)
        {
            logger.fatal("organizationUID: "+organizationUID+" Exception while inserting " +
                    "Organization names into ORGINIZATION_NAME_TABLE: \n", ex);
            throw new NEDSSDAOSysException( ex.toString() );
        }
         logger.debug("OrganizationNameDAOImpl - Done inserting all Organization names");
    }//end of inserting Organization names

    /**
     * insert a record of organizationName
     * @param organizationUID  the long
     * @param organizationName   the OrganizationNameDT
     * @throws NEDSSOrganizationDAOSysException
     */
    private void insertOrganizationName(long organizationUID, OrganizationNameDT organizationName)
            throws NEDSSDAOSysException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        ArrayList<Object> arrayList = new ArrayList<Object> ();

        try
        {


            arrayList.add(new Long(organizationUID));//1

            arrayList.add(organizationName.getOrganizationNameSeq()); //2

            arrayList.add(organizationName.getNmTxt()); //3

            arrayList.add(organizationName.getNmUseCd()); //4

            arrayList.add(organizationName.getRecordStatusCd());  //5

            arrayList.add(organizationName.getDefaultNmInd());  //6

            resultCount = ((Integer)preparedStmtMethod(organizationName, arrayList,
                              INSERT_ORGANIZATION_NAME, NEDSSConstants.UPDATE)).intValue();

            if ( resultCount != 1 )
                    logger.error
                        ("Error: none or more than one organization names inserted at a time, " +
                        "resultCount = " + resultCount);
            else
            {
              organizationName.setItNew(false);
              organizationName.setItDirty(false);
            }
        }
        catch(Exception sex)
        {
            logger.fatal("SQLException while inserting " +
                    "a organization name into ORGANIZATION_NAME_TABLE: \n", sex);
            throw new NEDSSSystemException("Table Name : "+ DataTables.ORGANIZATION_NAME_TABLE + "  For organizationUID: "+organizationUID+"  "+sex.toString(), sex );
        }
    }

    /**
     * This method updates the organization Names
     * @param organizationNames
     * @throws NEDSSSystemException
     */
    private void updateOrganizationNames(Collection<Object> organizationNames) throws NEDSSSystemException
    {

       Iterator<Object>  anIterator = null;
        /**
         * Updates Organization names
         */
        if(organizationNames != null)
        {
          for(anIterator = organizationNames.iterator(); anIterator.hasNext(); )
                {

                    OrganizationNameDT organizationName = (OrganizationNameDT)anIterator.next();
                    if(organizationName == null){
                     logger.error("Error: Empty organization name collection");
                    }

                }
            try
            {
                for(anIterator = organizationNames.iterator(); anIterator.hasNext(); )
                {
                    OrganizationNameDT organizationName = (OrganizationNameDT)anIterator.next();
                    if(organizationName == null){
                     logger.error("Error: Empty organization name collection");
                    }

                    if(organizationName.isItNew()){
                      insertOrganizationName((organizationName.getOrganizationUid()).longValue(), organizationName);
                    }
                    if(organizationName.isItDirty()){
                      updateOrganizationName(organizationName);
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "Organization names, \n", ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
    }//end of updating Organization name table

   /**
   * Updates a Organization name
   * @param organizationName  the OrganizationNameDT
   * @throws NEDSSSystemException
   */
    private void updateOrganizationName(OrganizationNameDT organizationName) throws NEDSSSystemException
    {
        int resultCount = 0;
        ArrayList<Object> arrayList = new ArrayList<Object> ();

        if(organizationName != null)
        {
            try
            {
                arrayList.add(organizationName.getNmTxt());//1
                arrayList.add(organizationName.getNmUseCd());//2
                arrayList.add(organizationName.getRecordStatusCd());//3
                arrayList.add(organizationName.getDefaultNmInd());//4
                arrayList.add(organizationName.getOrganizationUid());//5
                arrayList.add(organizationName.getOrganizationNameSeq());//6
                resultCount = ((Integer)preparedStmtMethod(organizationName, arrayList,
                              UPDATE_ORGANIZATION_NAME, NEDSSConstants.UPDATE)).intValue();

                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one Organization name updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(Exception sex)
            {
                logger.fatal("SQLException while updating " +
                    "Organization names, \n", sex);
                throw new NEDSSSystemException("Table Name : "+ DataTables.ORGANIZATION_NAME_TABLE +"  "+sex.toString(), sex);
            }
        }
    }//end of updating Organization name table

   /**
   * Selects Organization names
   * @param organizationUID
   * @return Collection
   * @throws NEDSSOrganizationDAOSysException
   */
    @SuppressWarnings("unchecked")
	private Collection<Object>  selectOrganizationNames (long organizationUID) throws NEDSSDAOSysException
    {
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        OrganizationNameDT organizationName = new OrganizationNameDT();
        ArrayList<Object> returnArrayList = new ArrayList<Object> ();
        try
        {
            arrayList.add(new Long(organizationUID));
            arrayList = (ArrayList<Object> )preparedStmtMethod(organizationName, arrayList,
                              SELECT_ORGANIZATION_NAMES, NEDSSConstants.SELECT);
            for(Iterator<Object> anIterator = arrayList.iterator(); anIterator.hasNext(); )
            {
                OrganizationNameDT reSetName = (OrganizationNameDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                returnArrayList.add(reSetName);
            }

            return returnArrayList;
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "Organization names; uid = " + organizationUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
    }

   /**
    * This method deletes Organization Names
    * and is called by remove() method
    * @param organizationUID long
    * @throws  NEDSSOrganizationDAOSysException
    */
    private void removeOrganizationNames (long organizationUID) throws NEDSSDAOSysException
    {
        OrganizationNameDT organizationName = new OrganizationNameDT();
        int resultCount = 0;
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        try
        {
            arrayList.add(new Long(organizationUID));
            resultCount = ((Integer)preparedStmtMethod(organizationName, arrayList,
                              DELETE_ORGANIZATION_NAMES, NEDSSConstants.UPDATE)).intValue();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete Organization names from ORGANIZATION_NAME_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(Exception sex)
        {
            logger.fatal("Exception while removing " +
                            "Organization name; id = " + organizationUID, sex);
            throw new NEDSSSystemException( sex.toString());
        }
    }

}//end of OrganizationNameDAOImpl class


