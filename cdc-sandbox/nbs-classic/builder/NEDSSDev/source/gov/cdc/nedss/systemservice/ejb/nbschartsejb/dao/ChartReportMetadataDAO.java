package gov.cdc.nedss.systemservice.ejb.nbschartsejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author vsiram2
 *
 */
public class ChartReportMetadataDAO extends DAOBase {

	private static final Logger logger = Logger.getLogger(ChartReportMetadataDAO.class);
	private static final PropertyUtil np = PropertyUtil.getInstance();
	private static TreeMap<Object, Object> chartMap =  new TreeMap<Object, Object>();
    
    
    
    
	@SuppressWarnings("unchecked")
	public Collection<Object> selectChartMetaData(NBSSecurityObj nbsSecurityObj) throws NEDSSDAOSysException {
		//This value would eventually be retrieved from Chart_report_metadata Table
		
		ArrayList<Object> returnColl = new ArrayList<Object>();
		if (chartMap != null && chartMap.get("CHART_METADATA") != null) {
			returnColl.add(chartMap.get("CHART_METADATA"));
			return returnColl;
		}
					

	    String sql = "SELECT CHART_REPORT_METADATA_UID \"chart_report_metadata_uid\"," +
	    			 " CHART_REPORT_CD \"chart_report_cd\"," +
	    			 " CHART_REPORT_SHORT_DESC_TXT \"chart_report_short_desc_txt\"," +
	    			 " EXTERNAL_CLASS_NM \"external_class_nm\"," +
	    			 " EXTERNAL_METHOD_NM \"external_method_nm\"," +
	    			 " X_AXIS_TITLE \"x_axis_title\"," +
	    			 " Y_AXIS_TITLE \"y_axis_title\"," +
	    			 " SECURED_IND_CD \"secured_ind_cd\"," +
	    			 " CHART_TYPE_UID \"chart_type_uid\"," +
	    			 " RECORD_STATUS_CD \"record_status_cd\"," +
	    			 " OBJECT_NM \"object_nm\"," +  		
	    			 " OPERATION_NM \"operation_nm\"," + 		
	    			 " SECURED_BY_OBJECT_OPERATION \"secured_by_object_operation\"," +	
	    			 " DEFAULT_IND_CD \"defaultIndCd\"" +
	    			 " FROM CHART_REPORT_METADATA WHERE RECORD_STATUS_CD='ACTIVE'";
	    
	    ChartReportMetadataDT dt = new ChartReportMetadataDT();
	    ArrayList<Object>  coll = new ArrayList<Object> ();
		try {			
			coll = (ArrayList<Object> ) preparedStmtMethod(dt, coll, sql, NEDSSConstants.SELECT, NEDSSConstants.ODS);
			
		} catch (Exception ex) {
			logger.fatal("Exception in selectOpenInvsPast7Days: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		try{
			Map <Object, Object> retMap = new TreeMap<Object, Object>();
			if(coll != null && coll.size() > 0) {
				Iterator<Object> iter = coll.iterator();
				while(iter.hasNext()) {
					ChartReportMetadataDT cmDT = (ChartReportMetadataDT) iter.next();
					retMap.put(cmDT.getChart_report_cd(), cmDT);				
				}
				chartMap.put("CHART_METADATA", retMap);
			}
	
			returnColl.add(retMap);
		}catch(Exception ex){
			logger.error("Exception ="+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return returnColl;
	}
	
	/**
	 * Returns all the available Charts configured for HomePage 
	 * @return
	 */
	public Collection<Object> getAvailableChartList() {

		if (chartMap != null && chartMap.get("CHART_LIST") != null) {
			return (ArrayList<Object>) chartMap.get("CHART_LIST");
		}
		String sql = "SELECT CHART_REPORT_CD \"key\",CHART_REPORT_SHORT_DESC_TXT \"value\" FROM CHART_REPORT_METADATA WHERE RECORD_STATUS_CD='ACTIVE'";

		DropDownCodeDT dt = new DropDownCodeDT();
		ArrayList<Object> coll = new ArrayList<Object>();
		try {
			coll = (ArrayList<Object>) preparedStmtMethod(dt, coll, sql, NEDSSConstants.SELECT, NEDSSConstants.ODS);
			chartMap.put("CHART_LIST", coll);
		} catch (Exception ex) {
			logger.fatal("Exception in getAvailableChartList: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

		return coll;
	}
	
}