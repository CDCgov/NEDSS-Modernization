package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTLabTestLabResultMappingDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Mark Hankey
 * @version 1.0
 */

public class SRTLabResultDAOImpl extends DAOBase {
	private static final LogUtils logger = new LogUtils(SRTLabResultDAOImpl.class.getName());
 private static PropertyUtil propertyUtil= PropertyUtil.getInstance();	
 
  private String srtLabTestLabResultMappingSQL =
      " select rt.LAB_TEST_CD  \"labTestCd\", "+
      " rt.LABORATORY_ID  \"laboratoryId\", "+
      " lr.LAB_RESULT_CD  \"labResultTestCd\", "+
      " lr.LABORATORY_ID  \"labResultLaboratoryId\", "+
      " lr.LAB_RESULT_DESC_TXT \"labResultDescTxt\", "+
      " lr.ORGANISM_NAME_IND \"labResultOrganismNameInd\", "+
      " lr.NBS_UID \"labResultNbsUid\" "+
      " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_result lr, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_test rt, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..labtest_labresult_mapping rtlr "+
      " where rt.LAB_TEST_CD = rtlr.lab_test_cd and "+
      " rt.LABORATORY_ID = rtlr.laboratory_id and "+
      " rtlr.lab_result_cd = lr.LAB_RESULT_CD and "+
      " rtlr.lab_result_laboratory_id = lr.LABORATORY_ID ";

   private String allLabResultsQuerySQL =
       "select lr.LAB_RESULT_CD  \"labResultTestCd\", "+
       " lr.LABORATORY_ID \"labResultLaboratoryId\", "+
       " lr.LAB_RESULT_DESC_TXT \"labResultDescTxt\", "+
       " lr.ORGANISM_NAME_IND \"labResultOrganismNameInd\", "+
       " lr.NBS_UID \"labResultNbsUid\" " +
       " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_result lr "+
       " order by lr.LAB_RESULT_DESC_TXT ";


  public SRTLabResultDAOImpl() {
  }

  /*Returns a collection of OrderedResultedTestDTs, sorted by ordered test*/
@SuppressWarnings("unchecked")
public Collection<Object> getLabResultsSRTLabTestLabResultMapping(){
	  ArrayList<Object>  list = new ArrayList<Object>();
	  String query = null;
	  try{
		  query = srtLabTestLabResultMappingSQL;
		   SRTLabTestLabResultMappingDT srtLabTestLabResultMappingDT = new SRTLabTestLabResultMappingDT();
		 list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestLabResultMappingDT, null,
		      query, NEDSSConstants.SELECT);
	  }catch(Exception ex){
		  logger.error("Exception ="+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString(), ex);
	  }
	  return list;

}

/*Returns a collection of OrderedResultedTestDTs*/
@SuppressWarnings("unchecked")
public Collection<Object> getAllLabResults(){
	ArrayList<Object>  list = new ArrayList<Object>();
	String query = null;
	try{
		query = allLabResultsQuerySQL;
		
		SRTLabTestLabResultMappingDT srtLabTestLabResultMappingDT = new SRTLabTestLabResultMappingDT();
		list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestLabResultMappingDT, null,
		    query, NEDSSConstants.SELECT);
	}catch(Exception ex){
		logger.error("Exception ="+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString(), ex);
	}
	return list;

}



}