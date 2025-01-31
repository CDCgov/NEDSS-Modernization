package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;
/**
 * <p>Title: SRTResultedTestDAOImpl</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corp.</p>
 * @author Mark Hankey
 *
 */
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTLabTestRelationshipDT;
import gov.cdc.nedss.systemservice.util.TestResultTestFilterDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;



public class SRTResultedTestDAOImpl extends DAOBase{
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  static final LogUtils logger = new LogUtils((SRTResultedTestDAOImpl.class).getName());
  public SRTResultedTestDAOImpl() {
  }

  private static String labTestRelationshipDTQuerySQL =
    "select ltr.ordered_lab_test_cd \"orderedLabTestCd\", "+
    "ot.LAB_TEST_DESC_TXT \"orderedLabTestDescTxt\", "+
    "ot.laboratory_id \"orderedLaboratoryId\", "+
    "ltr.result_lab_test_cd \"resultLabTestCd\" , "+
    "rt.lab_test_desc_txt \"resultLabTestDescTxt\" , "+
    "rt.laboratory_id \"resultLaboratoryId\" , "+
    "rt.indent_level_nbr \"indentLevel\",  "+
    "rt.test_type_cd \"resultTestTypeCd\",  "+
    "rt.drug_test_ind \"drugTestInd\" "+
    "from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..labtest_relationship ltr, "+
    " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_test ot, "+
    " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_test rt "+
        "where  "+
        "ot.LAB_TEST_CD = ltr.ordered_lab_test_cd and  "+
        "ot.laboratory_id = ltr.ordered_laboratory_id and "+
        "rt.lab_test_cd = ltr.result_lab_test_cd and "+
        "rt.laboratory_id = ltr.result_laboratory_id and "+
        "ot.test_type_cd = 'O' and "+
        "rt.test_type_cd = 'R' "+
        "order by rt.lab_test_desc_txt ";

  /*query all resulted tests in the lab test table.*/
   private static String AllRtQuerySQL =
    " select lt.laboratory_id \"laboratoryId\" , "+
    " lt.lab_test_cd \"labTestCd\" , "+
    " lt.lab_test_desc_txt \"labTestDescTxt\", "+
    " lt.test_type_cd \"testTypeCd\",  "+
    " lt.indent_level_nbr \"indentLevel\",  "+
    " lt.drug_test_ind \"drugTestInd\" "+
    " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_test lt"+
    " where lt.test_type_cd = 'R'  "+
//    " and lt.indent_level_nbr = '1' "+
    " order by lab_test_desc_txt ";



  @SuppressWarnings("unchecked")
public Collection<Object>  getLabTestProgAreaMapping() throws NEDSSSystemException {
     String result = null;
     try{
	     result = "select laboratory_id \"laboratoryId\", lab_test_desc_txt \"labTestDescTxt\", test_type_cd \"testTypeCd\"," +
	                 " lab_test_cd \"labTestCd\", condition_cd \"conditionCd\", condition_desc_txt \"conditionDescTxt\", prog_area_cd \"progAreaCd\",indent_level_nbr \"indentLevel\" from " +
	//                 NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping  WHERE indent_level_nbr = '1' and test_type_cd = 'R' "+
	                 NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping  WHERE test_type_cd = 'R' "+
	                 "order by laboratory_id, lab_test_desc_txt";
	      ///end of else
	
	     TestResultTestFilterDT testResultFilter = new TestResultTestFilterDT();
	     ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(testResultFilter, null,
	         result, NEDSSConstants.SELECT);
	     return list;
     }catch(Exception ex){
    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
    	 throw new NEDSSSystemException(ex.toString());
     }
  } //end of getLabTestProgAreaMapping


  /*Returns a collection of OrderedResultedTestDTs, sorted by ordered test*/
  @SuppressWarnings("unchecked")
public Collection<Object>  getOrderedResultedTests(){
    String query = null;
    ArrayList<Object> list = new ArrayList<Object>();
    try{
	   query = labTestRelationshipDTQuerySQL;
	   ///end of else
	
	    SRTLabTestRelationshipDT srtLabTestRelationshipDT = new SRTLabTestRelationshipDT();
	    list = (ArrayList<Object> ) preparedStmtMethod(srtLabTestRelationshipDT, null,
	        query, NEDSSConstants.SELECT);
    }catch(Exception ex){
   	 	logger.fatal("Exception  = "+ex.getMessage(), ex);
   	 	throw new NEDSSSystemException(ex.toString());
    }
    return list;

  }


    /*Returns a collection of OrderedResultedTestDTs, sorted by ordered test*/
	@SuppressWarnings("unchecked")
	public Collection<Object>  getAllResultedTests(){
	  String query = null;
	  ArrayList<Object> list = new ArrayList<Object>();
	  try{
		 query = AllRtQuerySQL;
		 //end of else
		
		  TestResultTestFilterDT testResultTestFilterDT = new TestResultTestFilterDT();
		  list = (ArrayList<Object> ) preparedStmtMethod(testResultTestFilterDT, null,
		      query, NEDSSConstants.SELECT);
	  }catch(Exception ex){
	 	 	logger.fatal("Exception  = "+ex.getMessage(), ex);
	 	 	throw new NEDSSSystemException(ex.toString());
	  }
	  return list;
	
	}


}