package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.TestResultTestFilterDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SRTOrderedTestDAOImpl extends DAOBase{
  static final LogUtils logger = new LogUtils(SRTOrderedTestDAOImpl.class.getName());
  private boolean verbose = false; // indicates whether commments should be printed to the console
  private boolean standardizeNames = false; // indicates whether method names should follow naming  // standards --- note: this flag insures backward compatibility
  private Map<Object,Object> classMap = new HashMap<Object,Object>();
  private StringUtils stringUtils = new StringUtils();
  private String query = null;
  protected static boolean DEBUG_MODE = false; // Used for debugging
  protected static Properties ps = null;
  static Map<Object,Object> beanMap = new HashMap<Object,Object>();
  protected static Hashtable ht = null;
  protected static DataSource dataSource = null;
  public static InitialContext jndiCntx = null;
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  /*query all ordered tests in the lab test table.*/
   private static String AllOtQuerySQL =
        " select lt.laboratory_id \"laboratoryId\" , "+
        " lt.lab_test_cd \"labTestCd\" , "+
        " lt.indent_level_nbr \"indentLevel\" , "+
        " lt.lab_test_desc_txt \"labTestDescTxt\" from "+
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_test lt "+
        " where lt.test_type_cd = 'O'   "+
    //    " and lt.indent_level_nbr = '1' "+
        " order by lab_test_desc_txt ";


//  public static final String DATA_SOURCE_REFERENCE ="java:jboss/datasources/NedssDataSource";

  public SRTOrderedTestDAOImpl() {
  }
  @SuppressWarnings("unchecked")
public Collection<Object>  getLabTestProgAreaMapping() throws NEDSSSystemException {
      String result = null;
      ArrayList<Object> list = new ArrayList<Object>();
      try{
	      
	        result = "select lab_test_cd \"labTestCd\",laboratory_id laboratoryId, lab_test_desc_txt labTestDescTxt, test_type_cd testTypeCd," +
	                  " condition_cd \"conditionCd\", condition_desc_txt conditionDescTxt, prog_area_cd progAreaCd,indent_level_nbr \"indentLevel\"  from " +
	                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping " +
	  //                " WHERE indent_level_nbr = '1' AND test_type_cd = 'O' " +
	                  " WHERE test_type_cd = 'O' " +
	                  " order by laboratory_id, lab_test_desc_txt";
	       ///end of else
	      TestResultTestFilterDT testResultFilter = new TestResultTestFilterDT();
	      list = (ArrayList<Object> ) preparedStmtMethod(testResultFilter, null,
	          result, NEDSSConstants.SELECT);
      }catch(Exception ex){
    	  logger.fatal("Exception = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return list;
   } //end of getLabTestProgAreaMapping

   @SuppressWarnings("unchecked")
public Collection<Object>  getLabTestNotMappedToPAMapping() throws NEDSSSystemException {
      String result = null;
      try{
	        result = "select Lab_test.lab_test_cd \"labTestCd\",Lab_test.LABORATORY_ID laboratoryId, Lab_test.LAB_TEST_DESC_TXT labTestDescTxt, Lab_test.test_type_cd testTypeCd "+
	            " From "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_test Lab_test "+
	            //--where (Lab_test.LABORATORY_ID = 'abc')AND
	            " where laboratory_id = 'DEFAULT' "+
	            " AND lab_test.TEST_TYPE_CD = 'O' "+
	            " AND not exists(select 1 from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping b "+
	                           " where b.LAB_TEST_CD = lab_test.lab_test_cd ) "+
	            " order by "+
	            " laboratory_id, test_type_cd";
	
	      ///end of else
	      TestResultTestFilterDT testResultFilter = new TestResultTestFilterDT();
	      ArrayList<Object> inputArgs = new ArrayList<Object> ();
	      ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(testResultFilter, inputArgs,
	      result, NEDSSConstants.SELECT);
	      return list;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
   } //end of

   /*Returns a collection of OrderedTestDTs, sorted by ordered test*/
	@SuppressWarnings("unchecked")
	public Collection<Object>  getAllOrderedTests(){
	  String query = null;
	  ArrayList<Object> list = new ArrayList<Object>();
	  try{
		  query = AllOtQuerySQL;
		   ///end of else
		  TestResultTestFilterDT testResultTestFilterDT = new TestResultTestFilterDT();
		  list = (ArrayList<Object> ) preparedStmtMethod(testResultTestFilterDT, null,
		      query, NEDSSConstants.SELECT);
	  }catch(Exception ex){
		  logger.fatal("Exception = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
	  return list;
	}

}