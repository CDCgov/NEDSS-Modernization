package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;

/**
 * SRTAdminUtilDAOImpl finds a case sensitive primary key before insert
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LabTestDAOImpl.java
 * July 16, 2008
 * @version
 */
public class SRTAdminUtilDAOImpl extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(SRTAdminUtilDAOImpl.class.getName());
	private static SRTAdminUtilDAOImpl instance = null;
	
	
	public static SRTAdminUtilDAOImpl getInstance() {
		if (instance == null) {
			instance = new SRTAdminUtilDAOImpl();
		}
		return instance;
	}
	
/**
 * This method is oracle specific as oracle treats case sensitive primary
 * keys as distinct and NBS Application needs to avoid the scenairo.
 * @param tableName
 * @param whereClause
 */
public void checkDuplicate(String tableName, String whereClause){
	
	String codeSql = "select count(*) from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "." + tableName + " where ";
	codeSql = codeSql + whereClause;
	Integer count = null;
	ArrayList<Object>  paramList = new ArrayList<Object> ();	
	RootDTInterface rootDTInterface = null;
	
		try {
			count =  (Integer) preparedStmtMethod(rootDTInterface, paramList, codeSql, NEDSSConstants.SELECT_COUNT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.error("Exception while checkDuplicate(ORACLE) in table " + tableName + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			paramList = new ArrayList<Object> ();
		}
		//throw a message if count is >=1 (record is found)
		if(count != null && count.intValue() >= 1) {
			throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code ");
		}		
	} 	

}
