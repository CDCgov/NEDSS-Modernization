package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.NETSSTransportQOutDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import java.util.ArrayList;
import java.util.Collection;



/**
* Name:		NETSSTransportQOutDAO.java
* Description:	DAO Object for temporary STD NETSS Interface
*    Rhapsody inserts records into the table during weekly processing.
* Copyright:	Copyright (c) 2015
* Company: 	SRA International
* Author:	Gregory Tucker
*/

public class NETSSTransportQOutDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(NETSSTransportQOutDAO.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static String SELECT_NETSS_CASE_DATA_COLLECTION =
			 "SELECT NETSS_TransportQ_out_uid \"netssTransportqOutUid\", " +
					"record_type_cd \"recordTypeCd\", " +
				    "mmwr_year \"mmwrYear\", " +
				    "mmwr_week \"mmwrWeek\", " +
				    "netss_case_id \"netssCaseId\", " +
				    "phc_local_id \"phcLocalId\", " +
				    "notification_local_id \"notificationLocalId\", " +
				    "add_time \"addTime\", " +
				    "payload \"payload\", " +
				    "record_status_cd \"recordStatusCd\" " +
				    "FROM " +
			"(SELECT NETSS_TransportQ_out_uid,  " +
					"record_type_cd, "  +
				    "mmwr_year, " +
				    "mmwr_week, " +
				    "netss_case_id , " +
				    "phc_local_id, " +
				    "notification_local_id, " +
				    "add_time, " +
				    "payload, " +
				    "record_status_cd, " +
					"ROW_NUMBER() OVER (PARTITION BY netss_case_id  ORDER BY add_time desc) as rn ";
	
											    
	private static String FROM_CLAUSE_SQL = "FROM NETSS_TransportQ_out) nt ";
	
	
	private static String WHERE_CLAUSE_YTD = "WHERE rn = 1 " +
		    " AND mmwr_Year = ? " +
		    " AND mmwr_Week <= ? " +
		    " AND record_Status_Cd != 'LOG_DEL' " +
		    " ORDER BY mmwr_Year, mmwr_Week";

	private static String WHERE_CLAUSE_YTD_AND_PRIOR_YEAR = "WHERE rn = 1 " +
			" AND ((mmwr_Year = ? and mmwr_Week <= ?) or (mmwr_Year = ?)) " +
			" AND record_Status_Cd != 'LOG_DEL' " +
			" ORDER BY mmwr_Year, mmwr_Week";	
	
	

	/**
	 *  Get NETSSTransportQOutDT collection. Only get the most recent one and 
	 * 
	 * @param includePriorYear 
	 * @return Collection<Object>  of CaseNotificationDataDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getNETSSTransportQOutDTCollectionForYear(Short mmwrYear, Short mmwrWeek, Boolean includePriorYear) throws NEDSSSystemException{
		String sql;
           	if (includePriorYear)
        		sql = SELECT_NETSS_CASE_DATA_COLLECTION  + FROM_CLAUSE_SQL + WHERE_CLAUSE_YTD_AND_PRIOR_YEAR;
        	else
        		sql = SELECT_NETSS_CASE_DATA_COLLECTION + FROM_CLAUSE_SQL + WHERE_CLAUSE_YTD;

		
		logger.debug("NETSS Select Case Data SQL = "+sql);

		NETSSTransportQOutDT nedssTransportQOutDT  = new NETSSTransportQOutDT();
		ArrayList<Object> netssDTCollectionForYear  = new ArrayList<Object> ();
		netssDTCollectionForYear.add(mmwrYear);
		netssDTCollectionForYear.add(mmwrWeek);
		if (includePriorYear)
			netssDTCollectionForYear.add(mmwrYear-1);
		
		try{
			netssDTCollectionForYear = (ArrayList<Object> )preparedStmtMethod(nedssTransportQOutDT, netssDTCollectionForYear, sql, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT );
		}
		catch (Exception ex) {
			String exString = "Exception in NETSSTransportQOutDAO.getNETSSTransportQOutDTCollectionForYear:  ERROR = " + ex.toString();
			throw new NEDSSSystemException(exString);
		}
		
		return netssDTCollectionForYear;
	}
}
