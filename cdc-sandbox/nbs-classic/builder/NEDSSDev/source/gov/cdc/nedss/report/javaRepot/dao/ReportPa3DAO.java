package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.CountIndexDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * PA3 DAO class for report
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportPa3DAO extends DAOBase {
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	static final LogUtils logger = new LogUtils(ReportPa3DAO.class.getName());
		
	public static String QueryA="SELECT COUNT(*) AS COUNTER FROM STD_HIV_DATAMART WHERE STD_HIV_DATAMART.INV_CASE_STATUS in ('Probable', 'Confirmed')  and CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=?  and DIAGNOSIS_CD in (DXLIST)";
	public static String QueryG="SELECT COUNT(*) AS COUNTER FROM STD_HIV_DATAMART WHERE STD_HIV_DATAMART.INV_CASE_STATUS in ('Probable', 'Confirmed')   AND internet_foll_up='Y' and CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=?  and DIAGNOSIS_CD in (DXLIST)";
	public static String QueryB="SELECT COUNT(*) FROM f_CONTACT_RECORD_CASE INNER JOIN STD_HIV_DATAMART "
							+" ON   f_CONTACT_RECORD_CASE.CONTACT_INVESTIGATION_KEY = STD_HIV_DATAMART.INVESTIGATION_KEY "
							+" INNER join D_CONTACT_RECORD "
							+" on  "
							+" D_CONTACT_RECORD.D_CONTACT_RECORD_KEY =f_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY "
							+" AND STD_HIV_DATAMART.INVESTIGATION_KEY IN ( "
							+" SELECT INVESTIGATION_KEY FROM  STD_HIV_DATAMART WHERE STD_HIV_DATAMART.INV_CASE_STATUS in ('Probable', 'Confirmed')  and CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=?  and DIAGNOSIS_CD in (DXLIST) "
							+" and REFERRAL_BASIS in (RFLIST) ) "
							+" AND D_CONTACT_RECORD.PROCESSING_DECSN_DESCRIPTION IN ('Field Follow-up','Surveillance Follow-up','Secondary Referral') AND CONTACT_INTERVIEW_KEY IS NOT NULL  ";

	public static String INTERNET_QUERY_CHECK=" AND internet_foll_up='Y' ";
	public int getCountCases(Timestamp fromConfirmTime, Timestamp toConfirmTime, String diagnosis,String referralBasis,String query, boolean contactInternetFolloUpInd,boolean selfInternetFolloUpInd, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
    {
		logger.debug("queryparameters are:=   1. fromConfirmTime"+ fromConfirmTime+"  2. toConfirmTime" +toConfirmTime + "  3. diagnosis:"+diagnosis+ "   4. referralBasis:"+referralBasis);
		logger.debug("   5. query: "+query+ "   6. contactInternetFolloUpInd:"+contactInternetFolloUpInd+ "   7. selfInternetFolloUpInd:"+selfInternetFolloUpInd);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		
		
    	if(query.contains("DXLIST")){
    		query= query.replace("DXLIST", diagnosis);
    	}
    	if(query.contains("RFLIST")){
    		query= query.replace("RFLIST", referralBasis);
    	}
			

		String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause( NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
	        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
	        if (dataAccessWhereClause == null) {
	          dataAccessWhereClause = "";
	        }
	        else {
	        	if(query.contains("D_CONTACT_RECORD")){
	        		dataAccessWhereClause=dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "D_CONTACT_RECORD.program_jurisdiction_oid");
	        	}
	        	dataAccessWhereClause = " AND " + dataAccessWhereClause ;
	        }
	        dbConnection = getConnection(NEDSSConstants.RDB);
		int counter=0;
		ResultSet resultSet = null;
		try
		{
			if(contactInternetFolloUpInd){
				query = query+INTERNET_QUERY_CHECK;
				logger.debug("The internetFolloUpInd is true and the query is :"+ query);
			}
			query =query+dataAccessWhereClause;
			logger.debug("The fianal query is before preparedStaement is:"+ query);
			preparedStmt = dbConnection.prepareStatement(query);
	        int i = 1;
	        if(fromConfirmTime!=null)
	        	preparedStmt.setTimestamp(i++, fromConfirmTime);
	        if(toConfirmTime!=null)
	        	preparedStmt.setTimestamp(i++, toConfirmTime);
	        
	        resultSet = preparedStmt.executeQuery();
	        if (resultSet.next())
	          {
				  counter = resultSet.getInt(1);
	          }
			logger.debug("counter is " +counter);
			logger.debug("Number of numberOfAssociatedInvestigations :"+ counter);
		} catch(SQLException sqlex) {
			logger.fatal("Sql query is "+query);
			logger.fatal("getCountCases:SQLException while getCountCases with fromConfirmTime: "+fromConfirmTime+" toConfirmTime:"+toConfirmTime
					+" diagnosis:"+diagnosis+ " referralBasis:"+referralBasis, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
			logger.fatal("Sql query is "+query);
			logger.fatal("getCountCases:SQLException while getCountCases with fromConfirmTime: "+fromConfirmTime+" toConfirmTime:"+toConfirmTime
					+" diagnosis:"+diagnosis+ " referralBasis:"+referralBasis, ex);
			throw new NEDSSDAOSysException( ex.toString() );
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return counter;
	
    }
	
	
	public static String distinctCount ="select count(*) \"intValue\", FL_FUP_INTERNET_OUTCOME \"value\" from STD_HIV_DATAMART where CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=?  "
								+" and DIAGNOSIS_CD in (DXLIST) and  STD_HIV_DATAMART.REFERRAL_BASIS in (RFLIST)  ";
	public CountIndexDT getCountCasesByIntFollup(Timestamp fromConfirmTime, Timestamp toConfirmTime, String diagnosis,String referralBasis, String queryDistinctCount, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
    {
		CountIndexDT countIndexDT = new CountIndexDT();
		countIndexDT.setCount1("0");
    	countIndexDT.setCount2("0");
    	countIndexDT.setCount3("0");
    	countIndexDT.setCount4("0");
    	countIndexDT.setCount5("0");
    	countIndexDT.setCount6("0");
    	countIndexDT.setCount7("0");
		logger.debug("queryparameters are:=   1. fromConfirmTime"+ fromConfirmTime+"  2. toConfirmTime" +toConfirmTime + "  3. diagnosis:"+diagnosis);
		
		if(queryDistinctCount.contains("DXLIST")){
			queryDistinctCount= queryDistinctCount.replace("DXLIST", diagnosis);
    	}
		if(queryDistinctCount.contains("RFLIST")){
			queryDistinctCount= queryDistinctCount.replace("RFLIST", referralBasis);
    	}
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause( NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
	        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
	        if (dataAccessWhereClause == null) {
	          dataAccessWhereClause = "";
	        }
	        else {
	          dataAccessWhereClause = " AND " + dataAccessWhereClause +" group by FL_FUP_INTERNET_OUTCOME ";
	        }
	        dbConnection = getConnection(NEDSSConstants.RDB);
	    DropDownCodeDT dropDownCodeDT  =new DropDownCodeDT();
		int counter=0;
		ResultSet resultSet = null;
		    ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
	     try
		{
	    	 queryDistinctCount =queryDistinctCount+dataAccessWhereClause;
	    	 logger.debug("The fianal query is before preparedStaement is:"+ queryDistinctCount);
	    	 preparedStmt = dbConnection.prepareStatement(queryDistinctCount);
	    	 int i = 1;
	        //preparedStmt = dbConnection.prepareStatement(queryDistinctCount);
	        if(fromConfirmTime!=null)
	        	preparedStmt.setTimestamp(i++, fromConfirmTime);
	        if(toConfirmTime!=null)
	        	preparedStmt.setTimestamp(i++, toConfirmTime);

	        resultSet = preparedStmt.executeQuery();
	        resultSetMetaData = resultSet.getMetaData();
	        retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dropDownCodeDT.getClass(), retval);
	        
	        if (retval!=null && retval.size()>0)
	          {
	        	Iterator it = retval.iterator();
	        	while(it.hasNext()){
		        	DropDownCodeDT dropDownCodeDT2  = (DropDownCodeDT)it.next();
		        	if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I1")){
		        		countIndexDT.setCount1(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I2")){
		        		countIndexDT.setCount2(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I3")){
		        		countIndexDT.setCount3(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I4")){
		        		countIndexDT.setCount4(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I5")){
		        		countIndexDT.setCount5(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I6")){
		        		countIndexDT.setCount6(dropDownCodeDT2.getIntValue()+"");
		        	}else if(dropDownCodeDT2.getValue()!=null && dropDownCodeDT2.getValue().startsWith("I7")){
		        		countIndexDT.setCount7(dropDownCodeDT2.getIntValue()+"");
		        	}
		        	        		
	        	}
	          }
			logger.debug("counter is " +counter);
			logger.debug("Number of numberOfAssociatedInvestigations :"+ counter);
		} catch(SQLException sqlex) {
			logger.fatal("Sql query is "+queryDistinctCount);
			logger.fatal("getCountCasesByIntFollup. while getCountCases with fromConfirmTime: "+fromConfirmTime+" toConfirmTime:"+toConfirmTime
					+" diagnosis:"+diagnosis, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
			logger.fatal("Sql query is "+queryDistinctCount);
			logger.fatal("getCountCasesByIntFollup:SQLException while getCountCases with fromConfirmTime: "+fromConfirmTime+" toConfirmTime:"+toConfirmTime
					+" diagnosis:"+diagnosis, ex);
			throw new NEDSSDAOSysException( ex.toString() );
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return countIndexDT;
	
    }

}
