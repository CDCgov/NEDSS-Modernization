/**
* Name:		OrganizationNameHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               OrganizationNameHistory value object in the Organization entity bean.
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



public class OrganizationNameHistDAOImpl extends DAOBase{
  static final LogUtils logger = new LogUtils(OrganizationNameHistDAOImpl.class.getName());
  private long organizationUid = -1;
  private long organizationNameSeq = -1;
  private int nextSeqNum = 0;
  public static final  String SELECT_ORGANIZATION_NAME_HIST =
                    "SELECT " +
                    "organization_uid OrganizationUid, " +
                    "organization_name_seq OrganizationNameSeq, " +
                    "nm_txt NmTxt,  " +
                    "nm_use_cd NmUseCd, " +
                    "record_status_cd RecordStatusCd, " +
                    "default_nm_ind DefaultNameInd " +
                    "FROM organization_name_hist " +
                    "WHERE organization_uid = ?  " +
                    "and organization_name_seq = ? ";
  public static final String INSERT_ORGANIZATION_NAME_HIST =
                    "INSERT into organization_name_hist(" +
                    "organization_uid, " +
                    "organization_name_seq, " +
                    "version_ctrl_nbr, " +
                    "nm_txt, " +
                    "nm_use_cd, " +
                    "record_status_cd, " +
                    "default_nm_ind) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";

   /**
   * Default Constructor
   * @param nextSeqNum    the int
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public OrganizationNameHistDAOImpl() {
  }//end of constructor

  public OrganizationNameHistDAOImpl(int nextSeqNum) throws NEDSSDAOSysException, NEDSSSystemException{
    this.nextSeqNum = nextSeqNum;
  }//end of constructor

  /**
  * The method helps to insert the values to OrganizationNameHistory
  * @param obj    the Object
  * @throws NEDSSDAOSysException
  * @throws NEDSSSystemException
  */
  public void store(Object obj)
    throws NEDSSDAOSysException, NEDSSSystemException {
      OrganizationNameDT dt = (OrganizationNameDT)obj;
      if(dt == null)
         throw new NEDSSSystemException("Error: try to store null OrganizationNameDT object.");
     insertOrganizationNameHist(dt);

  }//end of store()

 /**
 * Sets Collection<Object>  of records to OrganizationNameHistory
 * @param coll    the Collection
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
   * Gets the collection of OrganizationNameHistory from the database.
   * The method call the private method selectOrganizationNameHist
   * @param orgNameUid   the Long
   * @param orgNameSeq   the Integer
   * @param orgNameHistSeq   the Integer
   * @return organizationNameDTColl   the collection of OrganizationNameDT
   * @throws NEDSSSystemException
   */
  public Collection<Object>  load(Long orgNameUid, Long orgNameSeq, Integer orgNameHistSeq) throws NEDSSSystemException,
     NEDSSSystemException {
	       logger.info("Starts loadObject() for a organization name history...");
        Collection<Object>  organizationNameDTColl = selectOrganizationNameHist(orgNameUid.longValue(), orgNameSeq.longValue(), orgNameHistSeq.intValue());

        return organizationNameDTColl;

    }//end of load



  ///////////////////////////private class methods/////////////////////////////////////

   /**
   * Gets the OrganizationNameHistory values from the database
   * @param orgNameUid  the Long
   * @param orgNameSeq   the Long
   * @param orgNameHistSeq    the int
   * @return reSetList   the Collection<Object>  of OrganizationNameDT
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
  @SuppressWarnings("unchecked")
private Collection<Object>  selectOrganizationNameHist(long orgNameUid, long orgNameSeq, int orgNameHistSeq)throws NEDSSSystemException,
  NEDSSSystemException {


        OrganizationNameDT orgNameDT = new OrganizationNameDT();
        ArrayList<Object> arrayList = new ArrayList<Object> ();
        ArrayList<Object> returnArrayList= new ArrayList<Object> ();

        try
        {

//            preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.SELECT_ORGANIZATION_NAME_HIST);
            arrayList.add(new Long(orgNameUid));
            arrayList.add(new Long(orgNameSeq));
            arrayList = (ArrayList<Object> )preparedStmtMethod(orgNameDT, arrayList,
                                                      SELECT_ORGANIZATION_NAME_HIST,
                                                      NEDSSConstants.SELECT);


            for(Iterator<Object> anIterator = arrayList.iterator(); anIterator.hasNext(); )
            {
                OrganizationNameDT reSetOrgName = (OrganizationNameDT)anIterator.next();
                reSetOrgName.setItNew(false);
                reSetOrgName.setItDirty(false);

                returnArrayList.add(reSetOrgName);
            }
            logger.debug("return organization name hist collection");
            return returnArrayList;
         }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "organization name vo; id = " + orgNameUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }


  }//end of selectOrganizationNameHist(...)
 /**
 * Sets the OrganizationNameHistory values to the database
 * @param dt   the OrganizationNameDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
  private void insertOrganizationNameHist(OrganizationNameDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
      if(dt.getOrganizationUid() != null && dt.getOrganizationNameSeq() != null) {
        int resultCount = 0;
        ArrayList<Object> arrayList = new ArrayList<Object> ();
            try
            {
                arrayList.add(dt.getOrganizationUid());     //1
                arrayList.add(dt.getOrganizationNameSeq()); //2
                arrayList.add(new Integer(nextSeqNum));     //3
                arrayList.add(dt.getNmTxt());               //4
                arrayList.add(dt.getNmUseCd());             //5
                arrayList.add(dt.getRecordStatusCd());      //6
                arrayList.add(dt.getDefaultNmInd());        //7

                resultCount = ((Integer)preparedStmtMethod(dt, arrayList,
                              INSERT_ORGANIZATION_NAME_HIST, NEDSSConstants.UPDATE)).intValue();



                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(Exception se) {
              se.printStackTrace();
              throw new NEDSSSystemException("Error: SQLException while inserting\n" + se.getMessage());
            }
      }//end of if
  }//end of insertObservationNameHist()
}//end of class
