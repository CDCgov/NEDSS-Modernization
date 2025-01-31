package gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

  public class SummaryDataDAOImpl extends BMPBase
  {

    private static final LogUtils logger = new LogUtils(RetrieveSummaryVO.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    public static final String SELECT_PUBLCI_HEALTH_CASE=
                               "SELECT PUBLIC_HEALTH_CASE_UID \"PublicHealthCaseUid\" FROM PUBLIC_HEALTH_CASE " +
                               "WHERE RPT_CNTY_CD=? AND MMWR_WEEK=? AND MMWR_YEAR=?" +  " AND " +
                               "CASE_TYPE_CD=?";
    public static final String SELECT_PROG_AREA_CD_SQL = "SELECT PROG_AREA_CD FROM  NBS_SRTE..CONDITION_CODE WHERE CONDITION_CD=?";
    public SummaryDataDAOImpl() {
    }

    public Collection<Object>  retrieveSummaryReportList(String rpt_cnty_cd,String mmwr_week,
                                               String mmwr_year,String case_type_cd,
                                               NBSSecurityObj nbsSecurityObj)
                                               throws NEDSSSystemException
    {
    	try{
	      ArrayList<Object> phcUIDColl = new ArrayList<Object> ();
	      case_type_cd = "S";
	
	      if(!nbsSecurityObj.getPermission(NBSBOLookup.SUMMARYREPORT, NEDSSConstants.SUMMARY_REPORT_VIEW))
	      {
	        logger.info("INVESTIGATION = " + NBSBOLookup.SUMMARYREPORT + ",  VIEW = "
	                                         +  NEDSSConstants.SUMMARY_REPORT_VIEW);
	        throw new NEDSSSystemException("no permissions to VIEW a notification");
	      }
	      String statement =  SummaryDataDAOImpl.SELECT_PUBLCI_HEALTH_CASE;
	      logger.debug("statement in summaryDataDAOImpl = " + statement);
	
	      Connection dbConnection = null;
	      PreparedStatement preparedStmt = null;
	      ResultSet resultSet = null;
	      ResultSetMetaData resultSetMetaData = null;
	      ResultSetUtils resultSetUtils = new ResultSetUtils();
	      PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
	      try
	      {
		       dbConnection = getConnection();
		       preparedStmt = dbConnection.prepareStatement(statement);
		       preparedStmt.setString(1, rpt_cnty_cd);
		       preparedStmt.setString(2, mmwr_week);
		       preparedStmt.setString(3, mmwr_year);
		       preparedStmt.setString(4, case_type_cd);
		       logger.info("preparedStmt = " + preparedStmt.toString());
		       resultSet = preparedStmt.executeQuery();
		       logger.debug("get resultSet " + resultSet.toString());
		       resultSetMetaData = resultSet.getMetaData();
		       phcUIDColl = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
		                                                              phcDT.getClass(), phcUIDColl);
	       }
	       catch(SQLException se)
	       {
	         logger.fatal("Error: SQLException while selecting \n"+se.getMessage() , se);
	         throw new NEDSSSystemException( se.getMessage());
	       }
	       catch(ResultSetUtilsException rsuex)
	       {
	          logger.fatal("Error in result set handling while populate RoleDTs."+rsuex.getMessage(), rsuex);
	          throw new NEDSSSystemException(rsuex.toString());
	       }
	       finally
	       {
	          closeResultSet(resultSet);
	          closeStatement(preparedStmt);
	          releaseConnection(dbConnection);
	       }
	      	return phcUIDColl;
    	}catch(Exception ex){
    		logger.fatal("rpt_cnty_cd:"+rpt_cnty_cd+", mmwr_week:"+mmwr_week+", mmwr_year:"+mmwr_year+", case_type_cd:"+case_type_cd);
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    
    public String getProgAreaCd(String conditionCd)
    {
    	
    	Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        String progAreaCd = null;

        try
        {
        	String codeSql = null;
    		codeSql = SELECT_PROG_AREA_CD_SQL;
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(codeSql);
            preparedStmt.setString(1, conditionCd);
            resultSet = preparedStmt.executeQuery();
            
            while ( resultSet.next() )
            {
           	progAreaCd =   resultSet.getString(1);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an existing ProgramAreaCd " + progAreaCd+", conditionCd: "+conditionCd, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an existing ProgramAreaCd " + progAreaCd+", conditionCd: "+conditionCd, nsex);
            throw new NEDSSDAOSysException( nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return progAreaCd;
    }
  }
