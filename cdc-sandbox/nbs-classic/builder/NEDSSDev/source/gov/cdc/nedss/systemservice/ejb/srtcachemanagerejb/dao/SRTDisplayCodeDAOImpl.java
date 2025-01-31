package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;
/**
 * <p>Title: SRTDisplayCodeDAOImpl </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC </p>
 * @author Mark Hankey
 * @
 */

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTLabTestDisplayMappingDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;




public class SRTDisplayCodeDAOImpl extends DAOBase {
 private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
 private static final LogUtils logger = new LogUtils(SRTDisplayCodeDAOImpl.class.getName());
    private String SRTLoincDisplayMappingSQL =
      " select dm.loinc_cd \"loincCd\", "+
      " dc.DISPLAY_CD \"displayCd\" "+
      " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..display_code dc , "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..loinc_display dm "+
      " where dc.display_code_uid = dm.display_code_uid ";



  private String SRTLabTestDisplayMappingSQL =
    " select dm.LABORATORY_ID \"laboratoryId\", "+
    " dm.LAB_TEST_CD \"labTestCd\", "+
    " dc.DISPLAY_CD \"displayCd\" "+
    " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..display_code dc , "+
    " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..labtest_display_mapping dm "+
    " where dc.display_code_uid = dm.display_code_uid ";
private String AllDisplayCodesSQL =
    " select dc.DISPLAY_CD \"displayCd\" "+
    " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..display_code dc ";


  public SRTDisplayCodeDAOImpl() {
  }

  /*Returns a collection of SRTNBSDisplayElementDT's*/
@SuppressWarnings("unchecked")
public Collection<Object>  getLabTestDisplayMappingDTs(){
	String query = null;
	ArrayList<Object> list = new ArrayList<Object>();
	try{
		  query = SRTLabTestDisplayMappingSQL;
		SRTLabTestDisplayMappingDT srtLabTestDisplayMappingDT = new SRTLabTestDisplayMappingDT();
		list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestDisplayMappingDT, null,
		    query, NEDSSConstants.SELECT);
	}catch(Exception ex){
		logger.fatal("Exception = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
	return list;
}
  /*Returns a collection of SRTNBSDisplayElementDT's*/
@SuppressWarnings("unchecked")
public Collection<Object>  getLoincDisplayMappingDTs(){
	String query = null;
	ArrayList<Object> list = new ArrayList<Object>();
	try{
		query = SRTLoincDisplayMappingSQL;
		SRTLabTestDisplayMappingDT srtLabTestDisplayMappingDT = new SRTLabTestDisplayMappingDT();
		list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestDisplayMappingDT, null,
		    query, NEDSSConstants.SELECT);
	}catch(Exception ex){
		logger.fatal("Exception = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
	return list;
}

  /*Returns a collection of SRTNBSDisplayElementDT's*/
@SuppressWarnings("unchecked")
public Collection<Object>  getAllDisplayCodes(){
	String query = null;
	ArrayList<Object> list = new ArrayList<Object>();
	try{
		query = AllDisplayCodesSQL;
		SRTLabTestDisplayMappingDT srtLabTestDisplayMappingDT = new SRTLabTestDisplayMappingDT();
		list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestDisplayMappingDT, null,
		  query, NEDSSConstants.SELECT);
	}catch(Exception ex){
		logger.fatal("Exception = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
	return list;
}



}