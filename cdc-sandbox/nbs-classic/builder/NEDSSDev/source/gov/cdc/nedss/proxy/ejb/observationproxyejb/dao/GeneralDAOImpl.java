/**
* Name:		GeneralDAOImpl.java
* Description:	For those not needed a separate dao.
* Copyright:	Copyright (c) 2001In
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.proxy.ejb.observationproxyejb.dao;

import java.util.*;
import java.sql.*;





import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;




public class GeneralDAOImpl extends DAOBase

{
    //For logging 
    static final LogUtils logger = new LogUtils(FindLabReportTestDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    public void GeneralDAOImpl() throws NEDSSSystemException
    {
    }

    public List<Object> findProgramAreaCdByConditionCd(String conditionCd)
    {
      String aQuery = null;
        aQuery = "SELECT prog_area_cd FROM  nbs_srte..condition_code WHERE condition_cd = ?";
         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         ResultSet resultSet = null;
         List<Object> paList = new ArrayList<Object> ();
         try
         {
             dbConnection = getConnection();

             preparedStmt = dbConnection.prepareStatement(aQuery);
             preparedStmt.setString(1, conditionCd);
             resultSet = preparedStmt.executeQuery();
             String paCd = null;

             while ( resultSet.next() )
             {
                 paCd = resultSet.getString(1);
                 paList.add(paCd);
             }
             logger.debug("paList.size(): " + paList.size());

         }
         catch(Exception e)
         {
                 e.printStackTrace();

         }
         finally
         {
           closeResultSet(resultSet);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
         }

       return paList;
   }

   public Long findPatientMprUidByObservationUid(Long rootObsUid)
    {
      String aQuery = "SELECT subject_entity_uid FROM " +
             DataTables.PARTICIPATION_TABLE + " WHERE subject_class_cd = ? AND type_cd = ? AND act_uid = ?";

         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         ResultSet resultSet = null;
         Long patientMprUid = null;

         try
         {
             dbConnection = getConnection();

             preparedStmt = dbConnection.prepareStatement(aQuery);
             preparedStmt.setString(1, NEDSSConstants.PERSON);
             preparedStmt.setString(2, NEDSSConstants.PAR110_TYP_CD);
             preparedStmt.setLong(3, rootObsUid.longValue());
             resultSet = preparedStmt.executeQuery();


             while ( resultSet.next() )
             {
                 patientMprUid = new Long(resultSet.getLong(1));
             }
         }
         catch(Exception e)
         {
                 e.printStackTrace();

         }
         finally
         {
           closeResultSet(resultSet);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
         }

       return patientMprUid;
   }

}
