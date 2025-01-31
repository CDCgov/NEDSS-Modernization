package gov.cdc.nedss.entity.organization.ejb.dao;
/**
* Name:         OrganizationDAOImpl.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               Organization value object in the Organization entity bean.
*               This class encapsulates all the JDBC calls made by the OrganizationEJB
*               for a Organization object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of OrganizationEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:      Computer Sciences Corporation
* @author       NEDSS Development Team
* @version      1.0
*/

import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;


public class OrganizationDAOImpl extends DAOBase
{

    //NedssUtils nu = new NedssUtils();
    //Connection dbConnection = nu.getTestConnection();

   // private static final String ENTITY_UID = "ENTITY_UID";
    private long organizationUID = -1;

    // This costant is used find Organization Uid
    public static final String
                        SELECT_ORGANIZATION_UID = "SELECT organization_uid \"organizationUid\" FROM "
                                                 + DataTables.ORGANIZATION_TABLE
                                                 + " WHERE organization_UID = ?";

   public static final String
                        INSERT_ENTITY = "INSERT INTO "
                                        + DataTables.ENTITY_TABLE
                                        + "(entity_uid, class_cd) VALUES (?, ?)";

   public static final String SELECT_ORGANIZATION_UID_BY_LOCAL_ID = "SELECT organization_UID \"organizationUid\" FROM " +
	DataTables.ORGANIZATION_TABLE + " WHERE local_id = ?";

   
   public static final String INSERT_ORGANIZATION =
                              "INSERT INTO "
                              + DataTables.ORGANIZATION_TABLE
                              + "(add_reason_cd, "
                              + " add_time, "
                              + " add_user_id, "
                              + " cd, cd_desc_txt, "
                              + " description, "
                              + " duration_amt, "
                              + " duration_unit_cd, "
                              + " from_time, "
                              + " last_chg_reason_cd, "
                              + " last_chg_time, "
                              + " last_chg_user_id, "
                              + " record_status_cd, "
                              + " record_status_time, "
                              + " standard_industry_class_cd, "
                              + " standard_industry_desc_txt, "
                              + " status_cd, "
                              + " status_time, "
                              + " to_time, "
                              + " user_affiliation_txt, "
                              + " display_nm, street_addr1, "
                              + " street_addr2, "
                              + " city_cd, "
                              + " city_desc_txt, "
                              + " state_cd, cnty_cd, "
                              + " cntry_cd, "
                              + " zip_cd, "
                              + " phone_nbr, "
                              + " phone_cntry_cd, "
                              + " organization_uid, "
                              + " local_id, "
                              + " edx_ind,"
                              + " version_ctrl_nbr, "
                              + " electronic_ind) "
                              + " VALUES ( "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?, ?, "
                              + " ?, ?, ?, ?,?,?)";

    public static final String UPDATE_ORGANIZATION =
                                "UPDATE " + DataTables.ORGANIZATION_TABLE
                                + " set add_reason_cd = ?, add_time = ?, "
                                + " add_user_id = ?, cd = ?, cd_desc_txt = ?,"
                                + " description = ?, duration_amt = ?, "
                                + " duration_unit_cd = ?, from_time = ?, "
                                + " last_chg_reason_cd = ?, last_chg_time = ?, "
                                + " last_chg_user_id = ?, record_status_cd = ?, "
                                + " record_status_time = ?, standard_industry_class_cd = ?, "
                                + " standard_industry_desc_txt = ?, status_cd = ?, "
                                + " status_time = ?, to_time = ?, user_affiliation_txt = ?, "
                                + " display_nm = ?, street_addr1 = ?, street_addr2 = ?,"
                                + " city_cd = ?, city_desc_txt = ?, state_cd = ?, cnty_cd = ?, "
                                + " cntry_cd = ?, zip_cd = ?, phone_nbr = ?, phone_cntry_cd = ?, "
                                + " edx_ind = ?, "
                                + " version_ctrl_nbr = ? WHERE organization_uid = ? and version_ctrl_nbr = ?";

    public static final String SELECT_ORGANIZATION =
                                  " SELECT add_reason_cd \"addReasonCd\", "
                                + " add_time \"addTime\", "
                                + " add_user_id \"addUserId\", "
                                + " cd \"cd\", "
                                + " cd_desc_txt \"cdDescTxt\", "
                                + " description \"description\", "
                                + " duration_amt \"durationAmt\", "
                                + " duration_unit_cd \"durationUnitCd\", "
                                + " from_time \"fromTime\", "
                                + " last_chg_reason_cd \"lastChgReasonCd\", "
                                + " last_chg_time \"lastChgTime\", "
                                + " last_chg_user_id \"lastChgUserId\", "
                                + " record_status_cd \"recordStatusCd\", "
                                + " record_status_time \"recordStatusTime\", "
                                + " standard_industry_class_cd \"standardIndustryClassCd\", "
                                + " standard_industry_desc_txt \"standardIndustryDescTxt\", "
                                + " status_cd \"statusCd\", "
                                + " status_time \"statusTime\", "
                                + " to_time \"toTime\", "
                                + " user_affiliation_txt \"userAffiliationTxt\", "
                                + " display_nm \"displayNm\", "
                                + " street_addr1 \"streetAddr1\", "
                                + " street_addr2 \"streetAddr2\", "
                                + " city_cd \"cityCd\", "
                                + " city_desc_txt \"cityDescTxt\", "
                                + " state_cd \"stateCd\", "
                                + " cnty_cd \"cntyCd\", "
                                + " cntry_cd \"cntryCd\", "
                                + " zip_cd \"zipCd\", "
                                + " phone_nbr \"phoneNbr\", "
                                + " phone_cntry_cd \"phoneCntryCd\", "
                                + " organization_uid \"organizationUid\", "
                                + " local_id \"localId\", "
                                + " electronic_ind \"electronicInd\", "
                                + " edx_ind \"edxInd\", "
                                + " version_ctrl_nbr \"versionCtrlNbr\" FROM "
                                + DataTables.ORGANIZATION_TABLE
                                + " WHERE organization_UID = ?";

    public static final String DELETE_ORGANIZATION =
                                "DELETE FROM "
                                + DataTables.ORGANIZATION_TABLE
                                + " WHERE organization_uid = ?";

    static final LogUtils logger = new LogUtils(OrganizationDAOImpl.class.getName());

    public OrganizationDAOImpl()
    {
    }

  /**
   * This method creates (passes) the Organization Object to the database
   * @param obj     the Object
   * @return long - UID of from the database
   * @throws NEDSSSystemException
   */
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        organizationUID = insertOrganization((OrganizationDT)obj);
	        ((OrganizationDT)obj).setItNew(false);
	        ((OrganizationDT)obj).setItDirty(false);
	        return organizationUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

  /**
   * This method updates the corresponding table (organization)
   * @param obj   the Object
   * @throws NEDSSConcurrentDataException
   * @throws NEDSSSystemException
   */
    public void store(Object obj) throws  NEDSSConcurrentDataException, NEDSSSystemException
    {
    	try{
    		updateOrganization((OrganizationDT)obj);
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

   /**
   * To remove the organization record
   * @param OrganizationUID
   * @throws NEDSSSystemException
   */
    public void remove(long OrganizationUID) throws NEDSSSystemException
    {
    	try{
    		removeOrganization(organizationUID);
    	}catch(Exception ex){
    		logger.fatal("OrganizationUID: "+OrganizationUID+ " Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

   /**
   * Select the organization and load it to the organization Object
   * @param organizationUID    the long
   * @return Object     the Object
   * @throws NEDSSSystemException
   */
    public Object loadObject(long organizationUID) throws NEDSSSystemException
    {
    	try{
	    	OrganizationDT organizationDT = selectOrganization(organizationUID);
	        organizationDT.setItNew(false);
	        organizationDT.setItDirty(false);
	        return organizationDT;
    	}catch(Exception ex){
    		logger.fatal("organizationUID: "+organizationUID+ " Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

   /**
   * This method is to find the organization using the primarykey
   * @param organizationUID    the long
   * @return organizationUID  the Long
   * @throws NEDSSSystemException
   */
    public Long findByPrimaryKey(long organizationUID) throws NEDSSSystemException
    {
    	try{
	    	if (organizationExists(organizationUID))
	            return (new Long(organizationUID));
	        else
	            logger.error("No organization found for this primary key :" + organizationUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("organizationUID: "+organizationUID+ " Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * This method is to check whether UID exist in the database
     * @param organizationUID    the long
     * @return boolean    the boolean
     * @throws NEDSSSystemException
     */
    @SuppressWarnings("unchecked")
	protected boolean organizationExists (long organizationUID) throws NEDSSSystemException
    {

        boolean returnValue = false;
        OrganizationDT organizationDT = new OrganizationDT();
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        Long organizationUid = new Long(organizationUID);
        arrayList.add(organizationUid);
        try
        {

            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist uid for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(organizationDT, arrayList, SELECT_ORGANIZATION_UID, NEDSSConstants.SELECT);
            if(arrayList.size()!=0)
                organizationDT = (OrganizationDT)arrayList.get(0);
            if(organizationDT!=null)
                if(organizationDT.getOrganizationUid()!=null)
                    if (organizationDT.getOrganizationUid().equals(organizationUid))
                        returnValue = true; //only true if it exist

        }
         catch (Exception ex) {
            logger.fatal("organizationUID: "+organizationUID+" Exception in personExists():  ERROR = " + ex);
            throw new NEDSSSystemException(ex.toString());
        }
        return returnValue;
    }


   /**
    * This method gets the database connection and inserts organization entity to the database.
    * @param organizationDT   the OrganizationDT
    * @return organizationUID   the long
    * @throws NEDSSSystemException
    */
    private long insertOrganization(OrganizationDT organizationDT)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new organization
         */
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        Long organizationUid = new Long(0);
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        try
        {
            /**
             * Inserts into entity table for organization
             */
            uidGen = new UidGeneratorHelper();
            // new Organization Uid
            organizationUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE);

            organizationDT.setOrganizationUid(organizationUid);
            arrayList.add(organizationUid);
            arrayList.add(NEDSSConstants.ORGANIZATION);


            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            // with all the parameters for the sql statement
            // pass in the sql you wish to use
            // update is the same as insert so it uses the same opperation constant
            int resultCount = ((Integer)preparedStmtMethod(organizationDT, arrayList, INSERT_ENTITY, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1)
            {
                logger.error("Error creating new Organization UID, " + "resultCount = " + resultCount);
                throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
            // ******************int i = 1;


        }
        catch(Exception ex)
        {
            logger.fatal("Error while inserting into ENTITY_TABLE, organizationUID = " + organizationUid, ex);
            throw new NEDSSSystemException("Table Name : "+DataTables.ENTITY_TABLE+" "+ex.toString(), ex);
        }

        // clear out the previous values
        arrayList.clear();

        try
        {
            /**
             * inserts into organization_TABLE
             */
            uidGen = new UidGeneratorHelper();
            localUID = uidGen.getLocalID(UidClassCodes.ORGANIZATION_CLASS_CODE);
            logger.debug("OrganizationDAOImpl - after NEDSSSqlQuery.INSERT_ORGANIZATION");
            int i = 1;
            if(organizationDT != null){

              arrayList.add(organizationDT.getAddReasonCd()); // 1
              arrayList.add(organizationDT.getAddTime());  // 2
              arrayList.add(organizationDT.getAddUserId()); // 3
              arrayList.add(organizationDT.getCd()); // 4
              arrayList.add(organizationDT.getCdDescTxt()); // 5
              arrayList.add(organizationDT.getDescription()); // 6
              arrayList.add(organizationDT.getDurationAmt()); // 7
              arrayList.add(organizationDT.getDurationUnitCd()); // 8
              arrayList.add(organizationDT.getFromTime()); //9
              arrayList.add(organizationDT.getLastChgReasonCd()); // 10
              arrayList.add(organizationDT.getLastChgTime()); // 11
              arrayList.add(organizationDT.getLastChgUserId()); // 12
              arrayList.add(organizationDT.getRecordStatusCd()); // 13
              arrayList.add(organizationDT.getRecordStatusTime()); // 14
              arrayList.add(organizationDT.getStandardIndustryClassCd()); // 15
              arrayList.add(organizationDT.getStandardIndustryDescTxt()); // 16
              arrayList.add(organizationDT.getStatusCd()); // 17
              arrayList.add(organizationDT.getStatusTime()); // 18
              arrayList.add(organizationDT.getToTime()); // 19
              arrayList.add(organizationDT.getUserAffiliationTxt()); // 20
              arrayList.add(organizationDT.getDisplayNm()); // 21
              arrayList.add(organizationDT.getStreetAddr1()); // 22
              arrayList.add(organizationDT.getStreetAddr2()); // 23
              arrayList.add(organizationDT.getCityCd()); // 24
              arrayList.add(organizationDT.getCityDescTxt()); // 25
              arrayList.add(organizationDT.getStateCd()); // 26
              arrayList.add(organizationDT.getCntyCd()); // 27
              arrayList.add(organizationDT.getCntryCd()); // 28
              arrayList.add(organizationDT.getZipCd()); // 29
              arrayList.add(organizationDT.getPhoneNbr()); // 30
              arrayList.add(organizationDT.getPhoneCntryCd()); // 31
              arrayList.add(organizationUid); // 32
              arrayList.add(localUID); // 33
              arrayList.add(organizationDT.getEdxInd());// 34 - Added new on Jan 18th 2011 Release 411_INV_AutoCreate
              arrayList.add(organizationDT.getVersionCtrlNbr()); // 35
              arrayList.add(organizationDT.getElectronicInd()); // 36
            }

            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            // with all the parameters for the sql statement
            // pass in the sql you wish to use
            // update is the same as insert so it uses the same opperation constant
            int resultCount = ((Integer)preparedStmtMethod(organizationDT, arrayList, INSERT_ORGANIZATION, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1)
            {
                logger.error("Error: none or more than one ogranization updated at a time, " + "resultCount = " + resultCount);
                throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
        }
        catch(Exception ex)
        {
            logger.fatal("Error while inserting into organization_TABLE, id = " + organizationUid, ex);

            throw new NEDSSSystemException("Table Name : "+DataTables.ORGANIZATION_TABLE+" "+ex.toString(), ex);
        }
        return organizationUid.longValue();

    }//end of inserting organization

   /**
    * This method will update the organization Table
    * @param organizationDT
    * @throws NEDSSConcurrentDataException
    * @throws NEDSSSystemException
    */
    private void updateOrganization (OrganizationDT organizationDT) throws   NEDSSConcurrentDataException,  NEDSSSystemException
    {
        int resultCount = 0;

        ArrayList<Object> arrayList = new ArrayList<Object> ();

        try
        {
            //Updates organization table
            if (organizationDT != null)
            {
                logger.debug("Updating organizationDT: UID = " + organizationDT.getOrganizationUid().longValue());
                arrayList.add(organizationDT.getAddReasonCd());       //1
                arrayList.add(organizationDT.getAddTime());            //2
                arrayList.add(organizationDT.getAddUserId());          //3
                arrayList.add(organizationDT.getCd());                //4
                arrayList.add(organizationDT.getCdDescTxt());         //5
                arrayList.add(organizationDT.getDescription());       //6
                arrayList.add(organizationDT.getDurationAmt());       //7
                arrayList.add(organizationDT.getDurationUnitCd());    //8
                arrayList.add(organizationDT.getFromTime());          //9
                arrayList.add(organizationDT.getLastChgReasonCd());   //10
                arrayList.add(organizationDT.getLastChgTime());       //11
                arrayList.add(organizationDT.getLastChgUserId());     //12
                arrayList.add(organizationDT.getRecordStatusCd());    //13
                arrayList.add(organizationDT.getRecordStatusTime());  //14
                arrayList.add(organizationDT.getStandardIndustryClassCd());//15
                arrayList.add(organizationDT.getStandardIndustryDescTxt());//16
                arrayList.add(organizationDT.getStatusCd()); //17
                arrayList.add(organizationDT.getStatusTime());                         //18
                arrayList.add(organizationDT.getToTime());                         //19
                arrayList.add(organizationDT.getUserAffiliationTxt());//20
                arrayList.add(organizationDT.getDisplayNm());         //21
                arrayList.add(organizationDT.getStreetAddr1());       //22
                arrayList.add(organizationDT.getStreetAddr2());       //23
                arrayList.add(organizationDT.getCityCd());            //24
                arrayList.add(organizationDT.getCityDescTxt());       //25
                arrayList.add(organizationDT.getStateCd());           //26
                arrayList.add(organizationDT.getCntyCd());            //27
                arrayList.add(organizationDT.getCntryCd());           //28
                arrayList.add(organizationDT.getZipCd());             //29
                arrayList.add(organizationDT.getPhoneNbr());          //30
                arrayList.add(organizationDT.getPhoneCntryCd());      //31
                arrayList.add(organizationDT.getEdxInd());    //32 - New added on Jan 18 2011 (Release 411_INV_Autocreate Release)
                arrayList.add(organizationDT.getVersionCtrlNbr());//33
                arrayList.add(organizationDT.getOrganizationUid());//34
                arrayList.add(new Integer(organizationDT.getVersionCtrlNbr().intValue()-1)); //35


                resultCount = ((Integer)preparedStmtMethod(organizationDT, arrayList, UPDATE_ORGANIZATION, NEDSSConstants.UPDATE)).intValue();
                logger.debug("Done updating organization, UID = " + (organizationDT.getOrganizationUid()).longValue());
                logger.debug("Value of Result Count ::::::"+ resultCount);
                if (resultCount != 1)
                {
                logger.error("Error: none or more than one ogranization updated at a time, " + "resultCount = " + resultCount);
                throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
        catch(Exception ex)
        {
            logger.fatal("Error while updaeting into organization_TABLE, id = " + organizationDT.getOrganizationUid(), ex);

            throw new NEDSSSystemException("Table Name : "+DataTables.ORGANIZATION_TABLE+" "+ex.toString(), ex);
        }
    }//end of updating organization table

   /**
    * This method calls the query to select the record from the organization Table
    * @param organizationUID  the organization uid on organization table
    * @return OrganizationDT  the record from OrganizationTable
    * @throws NEDSSOrganizationDAOSysException
    */
    @SuppressWarnings("unchecked")
	private OrganizationDT selectOrganization (long organizationUID) throws NEDSSDAOSysException
    {

        OrganizationDT organizationDT = new OrganizationDT();
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        arrayList.add(new Long(organizationUID));

         /**
         * Selects organization from organization table
         */
        try
        {
          arrayList = (ArrayList<Object> )preparedStmtMethod(organizationDT, arrayList, SELECT_ORGANIZATION, NEDSSConstants.SELECT);
          if(arrayList.size()!=0) {
              organizationDT = (OrganizationDT)arrayList.get(0);
              organizationDT.setItNew(false);
	      organizationDT.setItDirty(false);
          }
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "organization vo; id = " + organizationUID, ex);

           throw new NEDSSSystemException(ex.toString());
        }
        logger.debug("return organization object");
        return organizationDT;
    }//end of selecting organization ethnic groups

    /**
     * This method is used retrieve a organization_uid with a given local_id
     * @J2EE_METHOD  --  retrieveOrganizationUIDByLocalId
     * @param localID String
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     **/
   @SuppressWarnings("unchecked")
	public Long retrieveOrganizationUIDByLocalId(String localId) throws NEDSSDAOSysException, NEDSSSystemException
   {
       OrganizationDT organizationDT = new OrganizationDT();
       ArrayList<Object>  arrayList = new ArrayList<Object> ();
       arrayList.add(localId);

       try
       {
           // using the preparedStmtMethod from DAOBase
           // pass the dt in to hold the uid for comparison
           // pass in an arraylist uid for the sql statement
           // pass in the sql you wish to use
           // pass in queryType constant so it will return a RootDTInterface
           arrayList = (ArrayList<Object> )preparedStmtMethod(organizationDT, arrayList, SELECT_ORGANIZATION_UID_BY_LOCAL_ID, NEDSSConstants.SELECT);
           if(arrayList.size()!=0)
        	   organizationDT = (OrganizationDT)arrayList.get(0);
           if(organizationDT!=null)
               if(organizationDT.getOrganizationUid()!=null)
                   return organizationDT.getOrganizationUid(); //only true if it exist
       }
       catch (Exception ex) {
           logger.fatal("localId: "+localId+" Exception in personExists():  ERROR = " + ex.getMessage(), ex);
           throw new NEDSSSystemException(ex.toString());
       }

       return null;
   }

    
    
   /**
   * This method get the connection to the database and
   * call the querry to delete the record from the organization Datatable.
   * @param organizationUID
   * @throws NEDSSOrganizationDAOSysException
   */
    private void removeOrganization (long organizationUID) throws NEDSSDAOSysException

    {
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        OrganizationDT organizationDT = new OrganizationDT();
        arrayList.add(new Long(organizationUID));
        int resultCount = 0;


        /**
         * Deletes organization ethnic groups
         */
        try
        {
            resultCount = ((Integer)preparedStmtMethod(organizationDT, arrayList, DELETE_ORGANIZATION, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete organization from organization_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(Exception sex)
        {
            logger.fatal("SQLException while removing " +
                            "organization; id = " + organizationUID, sex);
            throw new NEDSSSystemException(sex.toString());
        }
    }//end of removing organization

 /**
 * The method calls the stored procedure checkDeletePerson_sp
 * which will decide whether the organization can be deleted
 * @param organizationUID
 * @return int
 * @throws NEDSSSystemException
 */
  public  int checkDeleteOrganization (Long organizationUID)
    {

        try
        {
            System.out.println("About to call stored procedure");
            String sQuery  = "{call checkDeletePerson_sp(?,?)}";
            logger.info("sQuery = " + sQuery);

            ArrayList<Object> inArrayList = new ArrayList<Object> ();
            ArrayList<Object> outArrayList = new ArrayList<Object> ();
            inArrayList.add(organizationUID);
            System.out.println(" inArrayList" + inArrayList.size());
            outArrayList.add(new Integer(java.sql.Types.INTEGER));
            ArrayList<Object> arrayList = (ArrayList<Object> ) callStoredProcedureMethod(sQuery,inArrayList,outArrayList);
            logger.debug("after execute");

            //int count = null;
            int count = 0;
            if(arrayList != null){
             count = Integer.parseInt(arrayList.get(0).toString());
            }
                        System.out.println("\n\n count is == " + count);
            return count;
        }
        catch(Exception nse)
        {

        	logger.fatal("Exception  = "+nse.getMessage(), nse);
            throw new NEDSSSystemException("Error: NEDSSSystemException while obtaining database connection.\n" + nse.getMessage());
        }
    }//end of checking delete function

     /**
      * The static variable for a select statement
      */
     public static final String ELR_MATCH_ORDER_FACILITY
     = " select org.organization_uid "
	+ "from organization org, organization_name orgn, entity_locator_participation elp, "
	+ "tele_locator tele "
	+ "where org.organization_uid = orgn.organization_uid "
	+ "and   org.organization_uid = elp.entity_uid "
	+ "and elp.locator_uid= tele.tele_locator_uid "
	+ "and orgn.nm_use_cd='L' "
	+ "and elp.use_cd='WP' "
	+ "and elp.class_cd='TELE' "
	+ "and orgn.nm_txt =? "
	+ "and tele.phone_nbr_txt=? ";

   /**
   * This function to match a organization(Ordering Facility)  to a organization
   * by certain matching criteria
   * @param organizationNameDT   the OrganizationNameDT
   * @param teleDT   the TeleLocatorDT
   * @return organizationUID   long
   * @throws NEDSSSystemException
   */
    @SuppressWarnings("unchecked")
	public Long matchingOrderingFacility(OrganizationNameDT organizationNameDT, TeleLocatorDT teleDT)
    {

        ArrayList<Object> arrayList = new ArrayList<Object> ();

        Long odsOrganizationUid = null;

        if (teleDT == null || organizationNameDT == null)
          return null;

        if (organizationNameDT.getNmTxt() == null || teleDT.getPhoneNbrTxt() == null)
	    return null;

        arrayList.add(new String(organizationNameDT.getNmTxt()));
        arrayList.add(new String(teleDT.getPhoneNbrTxt()));
        try
        {

            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist uid for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(organizationNameDT, arrayList, ELR_MATCH_ORDER_FACILITY, NEDSSConstants.SELECT);

          if(arrayList.size()!=0)
                organizationNameDT = (OrganizationNameDT)arrayList.get(0);
            if(organizationNameDT!=null)
                if(organizationNameDT.getOrganizationUid()!=null)
                     odsOrganizationUid = organizationNameDT.getOrganizationUid();

        }
        catch (Exception e)
        {
        	logger.fatal("Exception  = "+e.getMessage(), e);
            throw new NEDSSSystemException("exception = " + e.toString());
        }
          return odsOrganizationUid;
    }

   /**
    * The static variable for the select statement
    */
   public static final String ELR_MATCH_ORGANIZATION
     = "select entity_uid from Entity_id where assigning_authority_cd='CLIA' and type_cd='FI' "
     + " and root_extension_txt = ? ";

    /**
    * This function is for ELR to match a organization(reporting lab OR performing lab) in ODS to a organization in MsgIn
    * by certain matching criteria
    * return: Long (organizationUid in ODS)
    *         or null if does not find a match or find multiple match
    */
    @SuppressWarnings("unchecked")
	public Long matchingOrganization(String rootExtensionTxt)
    {

        ArrayList<Object> arrayList = new ArrayList<Object> ();
        Long odsOrganizationUid = null;
        OrganizationNameDT organizationNameDT = new OrganizationNameDT();
        if (rootExtensionTxt == null)
          return null;

      try
        {
            arrayList.add(rootExtensionTxt);
            arrayList = (ArrayList<Object> )preparedStmtMethod(organizationNameDT, arrayList, ELR_MATCH_ORGANIZATION, NEDSSConstants.SELECT);

            if(arrayList.size()!=0)
                organizationNameDT = (OrganizationNameDT)arrayList.get(0);
            if(organizationNameDT!=null)
                if(organizationNameDT.getOrganizationUid()!=null)
                     odsOrganizationUid = organizationNameDT.getOrganizationUid();

              return odsOrganizationUid;
        }
        catch (Exception e)
        {
        	logger.fatal("Exception  = "+e.getMessage(), e);
            throw new NEDSSSystemException("exception = " + e.toString());
        }
    }

    public static void main(String[] strg)
    {
    	logger.debug("main start");
        logger.setLogLevel(6);

        String str = "Root ext text";
        OrganizationDAOImpl organizationDAOImpl = new OrganizationDAOImpl();
        Long uid= null;
        OrganizationDT organizationDT = new OrganizationDT();
// insertOrganization

        long organizationUidLong = 0;
        try
        {
            //**** sql server personuid
            organizationUidLong = 470023000;
            organizationDT.setOrganizationUid(new Long(organizationUidLong));



            {
                System.out.println("-----------start-----------------");
                {

				uid = organizationDAOImpl.matchingOrganization(str);
				logger.debug("********************organizationUidLong-------------------"
				                   + uid);
				
				logger.debug("********************End-------------------");
                //System.out.println(organizationDAOImpl.get));


//                    logger.debug("personDT.getPersonUid() = " + personDT.getPersonUid());
//                    logger.debug("personDT.getFirstNm() = " + personDT.getFirstNm());
//                    logger.debug("personDT.getLastNm() = " + personDT.getLastNm());
//                    logger.debug("personDT.getAddTime() = " + personDT.getAddTime());
//                    logger.debug("personDT.getRecordStatusTime() = " + personDT.getRecordStatusTime());
//                    logger.debug("personDT.getLocalId() = " + personDT.getLocalId());

//                    personDT.setPersonUid(new Long(personUidLong));
//                    personDT.setFirstNm("John");
//                    personDT.setLastNm("Paul");
//                    personDAOImpl.updatePerson(personDT);
                }
                logger.debug("-----------finish-----------------");
            }

        }
         catch (Exception ex) {
            logger.fatal("Exception in main():  ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
    }// end of main - for testing

}//end of organizationDAOImpl class
