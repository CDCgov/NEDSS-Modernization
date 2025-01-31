/**
* Name:		OrganizationHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               OrganizationHist value object inserting into OrganizationHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development team
* @version	1.0
*/
package gov.cdc.nedss.entity.organization.ejb.dao;

import java.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.organization.dt.*;

public class OrganizationHistDAOImpl extends DAOBase{

    /**
    * query to insert the organization History
    */
     public static final String INSERT_ORGANIZATION_HIST =
        "INSERT "
        + " into organization_hist "
        + "(organization_uid, "
        + " version_ctrl_nbr, "
        + " add_reason_cd, "
        + " add_time, "
        + " add_user_id,"
        + " cd, "
        + " cd_desc_txt, "
        + " description, "
        + " duration_amt,"
        + " duration_unit_cd,"
        + " from_time, "
        + " last_chg_reason_cd, "
        + " last_chg_time, "
        + " last_chg_user_id, "
        + " local_id, "
      //  + " org_access_permis, "
    //    + " prog_area_access_permis, "
        + " record_status_cd, "
        + " record_status_time, "
        + " standard_industry_class_cd, "
        + " standard_industry_desc_txt, "
        + " status_cd, "
        + " status_time, "
        + " to_time, "
        + " user_affiliation_txt, "
        + " display_nm, "
        + " street_addr1, "
        + " street_addr2, "
        + " city_cd, "
        + " city_desc_txt, "
        + " state_cd, "
        + " cnty_cd, "
        + " cntry_cd, "
        + " zip_cd, "
        + " phone_nbr, "
        + " edx_ind, "
        + " phone_cntry_cd) "
        + " VALUES(?,?,?,?,?,?,?,?,?,?, " // 10 in aline
        + " ?,?,?,?,?,?,?,?,?,?, "
        + " ?,?,?,?,?,?,?,?,?,?, "
        + " ?,?,?,?,?)";

   /**
    * query to insert the organization History
    */
     public static final String SELECT_ORGANIZATION_HIST =
     "SELECT organization_uid \"OrganizationUid\", "+
     "version_ctrl_nbr \"versionCtrlNbr\", "+
     "add_reason_cd \"AddReasonCd\", "+
     "add_time \"AddTime\", "+
     "add_user_id \"AddUserId\", "+
     "cd \"Cd\", "+
     "cd_desc_txt \"CdDescTxt\", "+
     "description \"Description\", "+
     "duration_amt \"DurationAmt\", "+
     "duration_unit_cd \"DurationUnitCd\", "+
     "from_time \"FromTime\", "+
     "last_chg_reason_cd \"LastChgReasonCd\", "+
     "last_chg_time \"LastChgTime\", "+
     "last_chg_user_id \"LastChgUserId\", "+
     "local_id \"LocalId\", "+
  //   "org_access_permis \"OrgAccessPermis\", "+
  //   "prog_area_access_permis \"ProgAreaAccessPermis\", "+
     "record_status_cd \"RecordStatusCd\", "+
     "record_status_time \"RecordStatusTime\", "+
     "standard_industry_class_cd \"StandardIndustryClassCd\", "+
     "standard_industry_desc_txt \"StandardIndustryDescTxt\", "+
     "status_cd \"StatusCd\", "+
     "status_time \"StatusTime\", "+
     "to_time \"ToTime\", "+
     "user_affiliation_txt \"UserAffiliationTxt\", "+
     "display_nm \"DisplayNm\", "+
     "street_addr1 \"StreetAddr1\", "+
     "street_Addr2 \"StreetAddr2\", "+
     "city_cd \"CityCd\", "+
     "city_desc_txt \"CityDescTxt\", "+
     "state_cd \"StateCd\", "+
     "cnty_cd \"CntyCd\", "+
     "cntry_cd \"CntryCd\", "+
     "zip_cd \"ZipCd\", "+
     "phone_nbr \"PhoneNbr\", "+
     "edx_ind \"edxInd\", "+
     "phone_cntry_cd \"PhoneCntryCd\" "+
     "FROM organization_hist "+
     "WHERE organization_uid = ? and organization_hist_seq = ?";

   /*  public static final String SELECT_ORGANIZATION_SEQ_NUMBER_HIST =
     "SELECT organization_hist_seq from organization_hist where organization_uid = ?";
*/

  static final LogUtils logger = new LogUtils(OrganizationHistDAOImpl.class.getName());

  private long organizationUid = -1;
  private short versionCtrlNbr = 0;

  public OrganizationHistDAOImpl() {
  }//end of constructor

 /**
 * Constructor
 * @param  organizationUid   the long
 * @param  versionCtrlNbr    the short
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
  public OrganizationHistDAOImpl(long organizationUid, short versionCtrlNbr)throws NEDSSDAOSysException,
  NEDSSSystemException {
    this.organizationUid = organizationUid;
    this.versionCtrlNbr = versionCtrlNbr;
 //   getNextOrganizationHistId();
  }//end of constructor

 /**
  * To store the organization History object
  * @param  obj    the Object
  * @throws NEDSSDAOSysException
  * @throws NEDSSSystemException
  */
  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
        OrganizationDT dt = (OrganizationDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null OrganizationDT object.");
          insertOrganizationHist(dt);

    }//end of store()
   /**
   * To store the collection of organization objects
   * @param coll  the Collection
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
    public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException {
       Iterator<Object>  iterator = null;
        if(coll != null)
        {
        	iterator = coll.iterator();
	        while(iterator.hasNext())
	        {
                  store(iterator.next());
	        }//end of while
        }//end of if
    }//end of store(Collection)
  /**
   * This method helps to get the organization history object from the database
   * @param  orgUid    the Long
   * @param  versionCtrlNbr   the Integer
   * @return  OrganizationDT
   * @throws NEDSSSystemException
   */
    public OrganizationDT load(Long orgUid, Integer versionCtrlNbr) throws NEDSSSystemException,
     NEDSSSystemException {
	       logger.info("Starts loadObject() for a organization history...");
        OrganizationDT orgDT = selectOrganizationHist(orgUid.longValue(),versionCtrlNbr.shortValue());
        orgDT.setItNew(false);
        orgDT.setItDirty(false);
        logger.info("Done loadObject() for a organization history - return: " + orgDT.toString());
        return orgDT;

    }//end of load

    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }


  ////////////////////////////private class methods//////////////////////////////////
   /**
   * This method gets the values to the OragnizationHistItemVO
   * @param  orgUid    the Long
   * @param  sqlQuery  the String
   * @return OrganizationDT
   * @throws NEDSSSystemException
  */
  @SuppressWarnings("unchecked")
private OrganizationDT selectOrganizationHist(long orgUid, short versionCtrlNbr)throws NEDSSSystemException,
  NEDSSSystemException {


        OrganizationDT orgDT = new OrganizationDT();
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        arrayList.add(new Long(orgUid));
        arrayList.add(new Short(versionCtrlNbr));
        try
        {
         arrayList = (ArrayList<Object> )preparedStmtMethod(orgDT, arrayList,
                      SELECT_ORGANIZATION_HIST, NEDSSConstants.SELECT);
         logger.debug("OrganizationsDT object for Organization history: organizationUid = " + orgUid);
         if(arrayList.size() != 0)
            orgDT = (OrganizationDT) arrayList.get(0);
            orgDT.setItNew(false);
            orgDT.setItDirty(false);
         }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "organization vo; id = " + orgUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        logger.info("return organizationDT for organization history");

        return orgDT;


  }//end of selectOrganizationHist(...)

  /**
   * To store the organization History Object to the database
   * @param dt   the OrganizationDT
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  private void insertOrganizationHist(OrganizationDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getOrganizationUid() != null) {
      int resultCount = 0;

          int i = 1;
          int count =0;
          try
          {
              ArrayList<Object> arrayList = new ArrayList<Object> ();


              arrayList.add(dt.getOrganizationUid());     //1
              arrayList.add(dt.getVersionCtrlNbr());      //2
              arrayList.add(dt.getAddReasonCd());         //3
              arrayList.add(dt.getAddTime());             //4
              arrayList.add(dt.getAddUserId());           //5
              arrayList.add(dt.getCd());                  //6
              arrayList.add(dt.getCdDescTxt());           //7
              arrayList.add(dt.getDescription());         //8
              arrayList.add(dt.getDurationAmt());         //9
              arrayList.add(dt.getDurationUnitCd());      //10
              arrayList.add(dt.getFromTime());            //11
              arrayList.add(dt.getLastChgReasonCd());     //12
              arrayList.add(dt.getLastChgTime());         //13
              arrayList.add(dt.getLastChgUserId());       //14
              arrayList.add(dt.getLocalId());             //15
              arrayList.add(dt.getRecordStatusCd());      //16
              arrayList.add(dt.getRecordStatusTime());    //17
              arrayList.add(dt.getStandardIndustryClassCd());//18
              arrayList.add(dt.getStandardIndustryDescTxt());//19
              arrayList.add(dt.getStatusCd());            //20
              arrayList.add(dt.getStatusTime());          //21
              arrayList.add(dt.getToTime());              //22
              arrayList.add(dt.getUserAffiliationTxt());  //23
              arrayList.add(dt.getDisplayNm());           //24
              arrayList.add(dt.getStreetAddr1());         //25
              arrayList.add(dt.getStreetAddr2());         //26
              arrayList.add(dt.getCityCd());              //27
              arrayList.add(dt.getCdDescTxt());           //28
              arrayList.add(dt.getStateCd());             //29
              arrayList.add(dt.getCntyCd());              //30
              arrayList.add(dt.getCntryCd());             //31
              arrayList.add(dt.getZipCd());               //32
              arrayList.add(dt.getPhoneNbr());            //33
              arrayList.add(dt.getEdxInd());         //34 - New on Jan 18th 2011- Release 411_INV_AUTOCREATE
              arrayList.add(dt.getPhoneCntryCd());        //35

              resultCount = ((Integer)preparedStmtMethod(dt, arrayList,
                              INSERT_ORGANIZATION_HIST, NEDSSConstants.UPDATE)).intValue();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(Exception se) {
            se.printStackTrace();
            throw new NEDSSSystemException("Error: SQLException while inserting\n" + se.getMessage());
          }
    }//end of if
  }//end of insertOrganizationHist()

 /* private void getNextOrganizationHistId() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_ORGANIZATION_SEQ_NUMBER_HIST);
        pStmt.setLong(1, organizationUid);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("OrganizationHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > nextSeqNum ) nextSeqNum = seqTemp;
        }
        ++nextSeqNum;
        logger.debug("OrganizationHistDAOImpl--nextSeqNum: " + nextSeqNum);
    } catch(SQLException se) {
        se.printStackTrace();
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextOrganizationHistId()*/

}//end of OrganizationHistDAOImpl
