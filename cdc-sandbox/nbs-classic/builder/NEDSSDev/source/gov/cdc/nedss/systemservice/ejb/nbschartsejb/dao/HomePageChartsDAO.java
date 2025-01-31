package gov.cdc.nedss.systemservice.ejb.nbschartsejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * HomePageChartsDAO.java
 * Sep 17, 2009
 * @version
 */
public class HomePageChartsDAO extends DAOBase {

	private static final Logger logger = Logger.getLogger(HomePageChartsDAO.class);
	private static final PropertyUtil np = PropertyUtil.getInstance();
    
   
    
	private String _getDataAccessWhereClause(NBSSecurityObj nbsSecurityObj, ChartReportMetadataDT cmDT) {
		
		String whereClause = "";
		String objNm = cmDT.getObject_nm() == null ? "" : cmDT.getObject_nm().toUpperCase();
		String operNm = cmDT.getOperation_nm() == null ? "" : cmDT.getOperation_nm().toUpperCase();
		 
		try {
			whereClause =  nbsSecurityObj.getDataAccessWhereClause(objNm , operNm);
		} catch (Exception e) {			
			logger.error("Warning !! ObjectName and OperationNm for chart: " + cmDT.getChart_report_cd() + " are not right.\t" + e.getMessage(), e);
		}
		
		return whereClause;
	}
    
    /**
     * 
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
	private Collection<Object> _fetchAndReturnResults(String sql) {
    	
	    ChartElementDT dt = new ChartElementDT();
	    ArrayList<Object>  coll = new ArrayList<Object> ();
		try {			
			coll = (ArrayList<Object> ) preparedStmtMethod(dt, coll, sql, NEDSSConstants.SELECT, NEDSSConstants.ODS);
			
		} catch (Exception ex) {
			logger.fatal("Exception while fetching results for query: \n" + sql + "\n: ERROR = " + ex.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return coll;	    
    	
    }
    
	/**
	 * Selects Open Investigations From Last x days
	 * @return java.util.Collection<Object>
	 * @throws NEDSSDAOSysException
	 */
	public Collection<Object> selectOpenInvsPastXDays(NBSSecurityObj nbsSecurityObj, boolean secured, ChartReportMetadataDT dt) throws NEDSSDAOSysException {

		try{
			String dataAccessWhereClause = _getDataAccessWhereClause(nbsSecurityObj, dt);
	
		    String sql = "";
		    	sql = "SELECT CONVERT(VARCHAR, t1.myDate,101) as hitDate, ISNULL(t2.hitCount,0) as hitCount  FROM ufn_getDates(7) t1 LEFT JOIN (" +
		    			 " SELECT COUNT(*) \"hitCount\", CONVERT(VARCHAR,ADD_TIME,101)  \"hitDate\"" + 
			 			 " FROM PUBLIC_HEALTH_CASE WHERE (INVESTIGATION_STATUS_CD = 'O' OR INVESTIGATION_STATUS_CD = 'C') AND CASE_TYPE_CD='I' AND RECORD_STATUS_CD <> 'LOG_DEL'" + 
			 			 " AND DATEDIFF(d, ADD_TIME, getdate())<7";
			    if(secured && dataAccessWhereClause.length() > 0)
			    	sql += " AND " + dataAccessWhereClause;
				sql+= " GROUP BY CONVERT(VARCHAR,ADD_TIME,101)";
				sql+= " ) t2 ON t1.mydate=t2.hitDate order by t1.mydate";
		    logger.debug("Query for selectOpenInvsPastXDays:" + sql);
		    return _fetchAndReturnResults(sql);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
		
	}
    
    
	/**
	 * Selects Lab Reports From Last x days
	 * @return java.util.Collection<Object>
	 * @throws NEDSSDAOSysException
	 */
	public Collection<Object> selectLabsPastXDays(NBSSecurityObj nbsSecurityObj, boolean secured, ChartReportMetadataDT dt) throws NEDSSDAOSysException {
		
		try{
			String dataAccessWhereClause = _getDataAccessWhereClause(nbsSecurityObj, dt);
	
		    String sql = "";
		        sql = 	"SELECT	CONVERT(VARCHAR, t1.myDate,101) as hitDate," + 
						" ISNULL(t2.hitCount,0) as hitCount," +  
						" t3.section" +
						" FROM ufn_getDates(7) t1 CROSS JOIN (SELECT 'Manual' AS section UNION ALL SELECT 'ELR' AS section) t3 LEFT JOIN" + 
						" ( SELECT COUNT(*) \"hitCount\"," + 
						" CASE ELECTRONIC_IND WHEN 'Y' THEN CONVERT(VARCHAR,RPT_TO_STATE_TIME,101) ELSE CONVERT(VARCHAR,ADD_TIME,101)  END \"hitDate\"," + 
						" CASE ELECTRONIC_IND WHEN 'Y' THEN 'ELR' ELSE 'Manual' END \"section\" FROM OBSERVATION WHERE CTRL_CD_DISPLAY_FORM='LabReport' and OBS_DOMAIN_CD_ST_1='Order' AND" + 
						" (DATEDIFF(d, ADD_TIME, getdate())<7 OR DATEDIFF(d, RPT_TO_STATE_TIME, getdate())<7)  AND RECORD_STATUS_CD <> 'LOG_DEL'"; 
			    
			    if(secured && dataAccessWhereClause.length() > 0)
					sql += " AND " + dataAccessWhereClause;
				
				sql += 	" GROUP BY CASE ELECTRONIC_IND WHEN 'Y' THEN CONVERT(VARCHAR,RPT_TO_STATE_TIME,101) ELSE CONVERT(VARCHAR,ADD_TIME,101) END, CASE ELECTRONIC_IND WHEN 'Y' THEN 'ELR' ELSE 'Manual' END ) t2" + 
						" ON t1.mydate=t2.hitDate AND t3.section = t2.section order by \"hitDate\"";
						//" order by hitdate, t3.section desc"; 	
		    
	    	logger.debug("Query for selectLabsPastXDays:" + sql);
		    return _fetchAndReturnResults(sql);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}
	
	/**
	 * 
	 * @param nbsSecurityObj
	 * @param secured
	 * @return java.util.Collection<Object>
	 * @throws NEDSSDAOSysException
	 */
	public Collection<Object> selectInvAssignPastXDays(NBSSecurityObj nbsSecurityObj, boolean secured, ChartReportMetadataDT dt) throws NEDSSDAOSysException {
		
		try{
			String dataAccessWhereClause = _getDataAccessWhereClause(nbsSecurityObj, dt);
	
		    String sql = "";
		        sql = "SELECT ISNULL(PERSON.LAST_NM,'No Last') + ', ' + ISNULL(PERSON.FIRST_NM,'No First') \"xAxis\", COUNT(*) \"hitCount\"," +
			    " CASE PHC.INVESTIGATION_STATUS_CD WHEN 'C' THEN 'Closed' WHEN 'O' THEN 'Open' END \"section\"" +
			    " FROM PUBLIC_HEALTH_CASE PHC INNER JOIN PARTICIPATION P ON PHC.PUBLIC_HEALTH_CASE_UID = P.ACT_UID INNER JOIN" +
			    " PERSON ON P.SUBJECT_ENTITY_UID = PERSON.PERSON_UID WHERE (P.TYPE_CD = '" + NEDSSConstants.PHC_INVESTIGATOR + "') AND PHC.CASE_TYPE_CD='I'" +
			    " AND DATEDIFF(d, ISNULL(P.FROM_TIME,PHC.INVESTIGATOR_ASSIGNED_TIME), getdate())<14";
			    if(secured && dataAccessWhereClause.length() > 0)
			    	sql += " AND " + dataAccessWhereClause;
			    sql += " GROUP BY ISNULL(PERSON.LAST_NM,'No Last') + ', ' + ISNULL(PERSON.FIRST_NM,'No First'),PHC.INVESTIGATION_STATUS_CD ORDER BY xAxis";	    	
		    logger.debug("Query for selectInvAssignPastXDays:" + sql);
		    return _fetchAndReturnResults(sql);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}	
	/**
	 * Query to select MMWR Week & Year for All "CONFIRMED" Investigations with CASE_TYPE_CD = 'I' or 'S' or 'A' AND ACTIVE ones
	 * @param nbsSecurityObj
	 * @param wY
	 * @param secured
	 * @return java.util.Collection<Object>
	 * @throws NEDSSDAOSysException
	 */	
	public Collection<Object> selectCaseCountsForMmwr(NBSSecurityObj nbsSecurityObj, int[] wY, boolean secured, ChartReportMetadataDT dt) throws NEDSSDAOSysException {
		
		try{
			String dataAccessWhereClause = _getDataAccessWhereClause(nbsSecurityObj, dt);
	
			String sql = "SELECT \"section\", \"hitCount\", \"hitCount2\" FROM" +
						 " (SELECT    cd_desc_txt \"section\"," + 
						 " COUNT(CASE WHEN RECORD_STATUS_CD <> 'LOG_DEL' AND CASE_CLASS_CD = 'C' AND CASE_TYPE_CD IS NOT NULL  AND MMWR_YEAR =" +  wY[1]  + " AND MMWR_WEEK =" +  wY[0]  + " THEN 1 ELSE NULL END) AS \"hitCount\"," +
						 " COUNT(CASE WHEN  RECORD_STATUS_CD <> 'LOG_DEL' AND CASE_CLASS_CD = 'C' AND CASE_TYPE_CD IS NOT NULL AND MMWR_YEAR =" +  wY[1]  + " THEN 1 ELSE NULL END) AS \"hitCount2\"" +
						 " FROM PUBLIC_HEALTH_CASE WHERE COALESCE(cd_desc_txt, ' ')<> ' '";
			if(secured && dataAccessWhereClause.length() > 0)
		    	sql += " AND " + dataAccessWhereClause;		
	
	   	    sql += " GROUP BY cd_desc_txt ) t1 WHERE \"hitCount\" + \"hitCount2\" >0 ORDER BY \"section\"";
	   	    logger.debug("Query for selectCaseCountsForMmwr:" + sql);
		    return _fetchAndReturnResults(sql);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}
	
}
