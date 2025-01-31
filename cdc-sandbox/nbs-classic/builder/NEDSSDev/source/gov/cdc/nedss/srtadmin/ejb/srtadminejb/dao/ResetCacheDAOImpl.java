package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ResetCacheDAOImpl finds an existing lab test by name, inserts and updates
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LabTestDAOImpl.java
 * Jun 20, 2008
 * @version
 */
public class ResetCacheDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(ResetCacheDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private static final String LAB_MAPPING_CACHE_SQL = "{call " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Labtest_Progarea_sp(?)}";
	
	/**
	 * resetLabMappingCache runs the nbs_srte.Labtest_Progarea_sp stored procedure
	 * to populate the nbs_srte.Labtest_Progarea_Mapping table appropriately.
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Collection<Object>  resetLabMappingCache() throws NEDSSDAOSysException, NEDSSSystemException {
		
		String sQuery =LAB_MAPPING_CACHE_SQL;
		
      	
        try {
            ArrayList<Object>  inArrayList  = new ArrayList<Object> ();
            ArrayList<Object>  outArrayList = new ArrayList<Object> ();
            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
            
            logger.info("Before calling resetLabMappingCache, time: " + new Timestamp(System.currentTimeMillis()));            
            ArrayList<Object>  arrayList = (ArrayList<Object> ) callStoredProcedureMethod(sQuery,inArrayList,outArrayList);

            String msg = "";            
            if(arrayList != null){
            	msg = arrayList.get(0).toString();
            	if(msg.indexOf("FAILURE") != -1)
            		logger.error("Labtest_Progarea_sp failed to run : " + msg);
            }
            logger.info("After calling resetLabMappingCache, time: " + new Timestamp(System.currentTimeMillis()));
            
            return arrayList;
            
        } catch (Exception ex) {
			logger.fatal("Exception in resetLabMappingCache: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}	
		
	}
	
}