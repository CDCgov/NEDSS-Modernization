package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCodeValueGenDT;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTConditionCodeValueGenRootDT;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTLabCodeValueGenRootDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
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




public class SRTCodeValueGenDAOImpl extends DAOBase{
  static final LogUtils logger = new LogUtils(SRTCodeValueGenDAOImpl.class.getName());
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
  
  public static final String DATA_SOURCE_REFERENCE =
      "java:jboss/datasources/NedssDataSource";
  public static final String DATABASE_NAME = "SRT_DEV";

  public static String anatomicLoincSQL ="select  ana.code \"code\", ana.anatomic_site_desc_txt \"codeShortDescTxt\"," +
                  "lc.loinc_cd \"loincCd\"" +
                  " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                  "..ANATOMIC_SITE_CODE ana,  " +
                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LOINC_CODE lc,  " +
                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LOINC_CODE_ANATOMIC_SITE map  " +
                  " WHERE map.loinc_cd = lc.loinc_cd " +
                  " AND map.ANATOMIC_SITE_UID = ana.ANATOMIC_SITE_UID " +
                  " AND ana.code_set_nm = 'ANATOMIC_SITE' " +
                  " order by anatomic_site_desc_txt";

  public static String anatomicSQL = "select  ana.code \"code\", ana.anatomic_site_desc_txt \"codeShortDescTxt\"," +
                  "lt.test_type_cd \"testTypeCd\",lt.lab_test_desc_txt \"labTestDescTxt\", lt.lab_test_cd \"labTestCd\",lt.laboratory_id \"laboratoryId\""+
                  " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..ANATOMIC_SITE_CODE ana,  " +
                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_test lt,  " +
                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LABTEST_SITE_MAPPING map  " +
                  " WHERE lt.test_type_cd in('O','R') " +
                  " AND map.laboratory_id = lt.laboratory_id " +
                  " AND map.lab_test_cd = lt.lab_test_cd " +
                  " AND map.anatomic_site_uid = ana.anatomic_site_uid " +
                  " AND ana.code_set_nm = 'ANATOMIC_SITE' " +
                  " order by anatomic_site_desc_txt";

 
    public static String anatomicAllSQL = "select  ana.code \"code\", ana.anatomic_site_desc_txt \"codeShortDescTxt\"" +
                   " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..anatomic_site_code ana  " +
                   " WHERE ana.code_set_nm = 'ANATOMIC_SITE' " +
                   " order by anatomic_site_desc_txt";

public static String specimenSourceLoincSQL = "select  ssc.code \"code\", ssc.specimen_source_desc_txt \"codeShortDescTxt\"," +
                    "lc.loinc_cd \"loincCd\"" +
                    " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                    "..specimen_source_code ssc,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..loinc_code lc,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..loinc_specimen_source map  " +
                    " WHERE map.loinc_cd = lc.loinc_cd " +
                    " AND map.specimen_source_uid = ssc.specimen_source_uid " +
                    " AND ssc.code_set_nm = 'SPECMN_SRC' " +
                    " order by specimen_source_desc_txt";


   public static String specimenSourceSQL = "select  ssc.code \"code\", ssc.specimen_source_desc_txt \"codeShortDescTxt\"," +
                    "lt.test_type_cd \"testTypeCd\",lt.lab_test_desc_txt \"labTestDescTxt\", lt.lab_test_cd \"labTestCd\",lt.laboratory_id \"laboratoryId\""+
                    " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..specimen_source_code ssc,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_test lt,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_specimen_mapping map  " +
                    " WHERE lt.test_type_cd in('O','R') " +
                    " AND map.laboratory_id = lt.laboratory_id " +
                    " AND map.lab_test_cd = lt.lab_test_cd " +
                    " AND map.specimen_source_uid = ssc.specimen_source_uid " +
                    " AND ssc.code_set_nm = 'SPECMN_SRC' " +
                    " order by specimen_source_desc_txt";

    public static String specimenSourceAllSQL = "select  ssc.code \"code\", ssc.specimen_source_desc_txt  \"codeShortDescTxt\"" +
                  " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..specimen_source_code ssc  " +
                  " WHERE ssc.code_set_nm = 'SPECMN_SRC' " +
                  " order by specimen_source_desc_txt";

public static String unitLoincSQL ="select  uc.code \"code\", uc.UNIT_DESC_TXT \"codeShortDescTxt\"," +
                   "lc.loinc_cd \"loincCd\"" +
                   " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..UNIT_CODE uc,  " +
                   NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..loinc_code lc,  " +
                   NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..loinc_unit map  " +
                   " WHERE map.loinc_cd = lc.loinc_cd " +
                   " AND map.unit_uid = uc.unit_uid " +
                   " AND uc.code_set_nm = 'UNIT_ISO' " +
                   " order by UNIT_DESC_TXT";

   public static String unitSQL = "select  uc.code \"code\", uc.UNIT_DESC_TXT \"codeShortDescTxt\"," +
                    "lt.test_type_cd \"testTypeCd\",lt.lab_test_desc_txt \"labTestDescTxt\", lt.lab_test_cd \"labTestCd\",lt.laboratory_id \"laboratoryId\""+
                    " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..UNIT_CODE uc,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_test lt,  " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_unit_mapping map  " +
                    " WHERE lt.test_type_cd ='R' " +
                    " AND map.laboratory_id = lt.laboratory_id " +
                    " AND map.lab_test_cd = lt.lab_test_cd " +
                    " AND map.unit_uid = uc.unit_uid " +
                    " AND uc.code_set_nm = 'UNIT_ISO' " +
                    " order by UNIT_DESC_TXT";

   public static String unitAllSQL = "select  uc.code \"code\", uc.UNIT_DESC_TXT \"codeShortDescTxt\"" +
                 " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..UNIT_CODE uc  " +
                 " WHERE uc.code_set_nm = 'UNIT_ISO' " +
                 " order by UNIT_DESC_TXT";

   public static String reportingSourceSQL = "select  rsc.code \"code\", rsc.reporting_source_desc_txt \"codeShortDescTxt\"," +
                 "cond.condition_cd \"conditionCd\",cond.prog_area_cd \"progAreaCd\"" +
                 " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..reporting_source_code rsc,  " +
                 NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_reporting_source map,  " +
                 NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code cond" +
                 " WHERE  " +
                 " map.condition_cd = cond.condition_cd " +
                 " AND map.reporting_source_uid = rsc.reporting_source_uid " +
                 " AND rsc.code_set_nm = 'PHC_RPT_SRC_T' " +
                 " order by reporting_source_desc_txt";



    public static String reportingSourceAllSQL = "select  rsc.code \"code\", rsc.reporting_source_desc_txt \"codeShortDescTxt\"" +
                  " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..reporting_source_code rsc  " +
                  " WHERE  rsc.code_set_nm = 'PHC_RPT_SRC_T' " +
                   " order by reporting_source_desc_txt";







  public SRTCodeValueGenDAOImpl() {
  }

  @SuppressWarnings("unchecked")
public Collection<Object>  getAnatomicSiteData() throws NEDSSSystemException {
      String query = null;
      try{
	      query = anatomicSQL;
	      SRTLabCodeValueGenRootDT anatomicFilter = new SRTLabCodeValueGenRootDT();
	      ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(anatomicFilter, null,
	          query, NEDSSConstants.SELECT);
	      return list;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
   } //end of getAnatomicSiteData
   @SuppressWarnings("unchecked")
public Collection<Object>  getLoincAnatomicSiteData() throws NEDSSSystemException {
       try{
    	   String query =anatomicLoincSQL;
    	   SRTLabCodeValueGenRootDT anatomicFilter = new SRTLabCodeValueGenRootDT();
	       ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(anatomicFilter, null,
	           query, NEDSSConstants.SELECT);
	       return list;
       }catch(Exception ex){
     	  logger.fatal("Exception  = "+ex.getMessage(), ex);
     	  throw new NEDSSSystemException(ex.toString());
       }
    } //end of getAnatomicSiteData

   @SuppressWarnings("unchecked")
public Collection<Object>  getAllAnatomicSiteData() throws NEDSSSystemException {
      String query = null;
      try{
	         query = anatomicAllSQL;
	      SRTLabCodeValueGenRootDT anatomicFilter = new SRTLabCodeValueGenRootDT();
	      ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(anatomicFilter, null,
	          query, NEDSSConstants.SELECT);
	      return list;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
   } //end of getAnatomicSiteData


   @SuppressWarnings("unchecked")
public Collection<Object>  getSpecimenSourceData() throws NEDSSSystemException {
     String query = null;
     try{
	     query = specimenSourceSQL;
	     SRTLabCodeValueGenRootDT specimenFilter = new SRTLabCodeValueGenRootDT();
	     ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(specimenFilter, null,
	         query, NEDSSConstants.SELECT);
	     return list;
     }catch(Exception ex){
    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
   	  	throw new NEDSSSystemException(ex.toString());
     }
  } //end of getSpecimenSourceData
  @SuppressWarnings("unchecked")
public Collection<Object>  getLoincSpecimenSourceData() throws NEDSSSystemException {
     String query = null;
     try{
	    query = specimenSourceLoincSQL;
	     SRTLabCodeValueGenRootDT specimenFilter = new SRTLabCodeValueGenRootDT();
	     ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(specimenFilter, null,
	         query, NEDSSConstants.SELECT);
	     return list;
     }catch(Exception ex){
    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
   	  	throw new NEDSSSystemException(ex.toString());
     }
  } //end of getSpecimenSourceData


  @SuppressWarnings("unchecked")
public Collection<Object>  getAllSpecimenSourceData() throws NEDSSSystemException {
    String query = null;
    try{
	    query = specimenSourceAllSQL;
	  SRTLabCodeValueGenRootDT specimenFilter = new SRTLabCodeValueGenRootDT();
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(specimenFilter, null,
	        query, NEDSSConstants.SELECT);
	    return list;
    }catch(Exception ex){
   	 logger.fatal("Exception  = "+ex.getMessage(), ex);
  	  	throw new NEDSSSystemException(ex.toString());
    }
 } //end of getSpecimenSourceData

  @SuppressWarnings("unchecked")
public Collection<Object>  getUnitsData() throws NEDSSSystemException {
    String query = null;
    try{
	 query = unitSQL;
	 SRTLabCodeValueGenRootDT unitsFilter = new SRTLabCodeValueGenRootDT();
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(unitsFilter, null,
	        query, NEDSSConstants.SELECT);
	    return list;
    }catch(Exception ex){
   	 logger.fatal("Exception  = "+ex.getMessage(), ex);
  	  	throw new NEDSSSystemException(ex.toString());
    }
 } //end of getUnitData
 @SuppressWarnings("unchecked")
public Collection<Object>  getLoincUnitsData() throws NEDSSSystemException {
   String query = null;
   try{
	      query = unitLoincSQL;
	   SRTLabCodeValueGenRootDT unitsFilter = new SRTLabCodeValueGenRootDT();
	   ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(unitsFilter, null,
	       query, NEDSSConstants.SELECT);
	   return list;
   }catch(Exception ex){
  	 logger.fatal("Exception  = "+ex.getMessage(), ex);
 	 throw new NEDSSSystemException(ex.toString());
   }
} //end of getUnitData

 @SuppressWarnings("unchecked")
public Collection<Object>  getAllUnitsData() throws NEDSSSystemException {
   String query = null;
   try{
	   query = unitAllSQL;
	   SRTLabCodeValueGenRootDT unitsFilter = new SRTLabCodeValueGenRootDT();
	   ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(unitsFilter, null,
	       query, NEDSSConstants.SELECT);
	   return list;
   }catch(Exception ex){
  	 logger.fatal("Exception  = "+ex.getMessage(), ex);
 	 throw new NEDSSSystemException(ex.toString());
   }
} //end of getUnitData

 @SuppressWarnings("unchecked")
public Collection<Object>  getReportingSourceData() throws NEDSSSystemException {
    String query = null;
    try{
	    query = reportingSourceSQL;
	    SRTConditionCodeValueGenRootDT rsFilter = new SRTConditionCodeValueGenRootDT();
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(rsFilter, null,
	        query, NEDSSConstants.SELECT);
	    return list;
    }catch(Exception ex){
   	 	logger.fatal("Exception  = "+ex.getMessage(), ex);
  	  	throw new NEDSSSystemException(ex.toString());
    }
 } //end of getReportingSourceData

 @SuppressWarnings("unchecked")
public Collection<Object>  getAllReportingSourceData() throws NEDSSSystemException {
    String query = null;
    try{
	    query = reportingSourceAllSQL;
	    SRTConditionCodeValueGenRootDT rsFilter = new SRTConditionCodeValueGenRootDT();
	    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(rsFilter, null,
	        query, NEDSSConstants.SELECT);
	    return list;
    }catch(Exception ex){
   	 	logger.fatal("Exception  = "+ex.getMessage(), ex);
  	  	throw new NEDSSSystemException(ex.toString());
    }
 } //end of getReportingSourceData

 public Collection<Object>  convertToCodeValueGenDT(Collection<Object> list)
   {
     Collection<Object>  returnList = new ArrayList<Object> ();
     try{
	     Iterator<Object>  iter = list.iterator();
	     SRTLabCodeValueGenRootDT codeValueGenRootDT=null;
	     while(iter.hasNext())
	     {
	       codeValueGenRootDT = (SRTLabCodeValueGenRootDT) iter.next();
	       returnList.add(new SRTCodeValueGenDT(codeValueGenRootDT));
	     }
     }catch(Exception ex){
    	 logger.fatal("Exception = "+ex.getMessage(), ex);
    	 throw new NEDSSSystemException(ex.toString());
     }
     return returnList;
   }

   public Collection<Object>  convertConditionToCodeValueGenDT(Collection<Object> list)
   {
       Collection<Object>  returnList = new ArrayList<Object> ();
       try{
    	    Iterator<Object>  iter = list.iterator();
            SRTConditionCodeValueGenRootDT codeValueGenRootDT=null;
            while(iter.hasNext())
            {
              codeValueGenRootDT = (SRTConditionCodeValueGenRootDT) iter.next();
              returnList.add(new SRTCodeValueGenDT(codeValueGenRootDT));
            }
       }catch(Exception ex){
    	   logger.fatal("Exception = "+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString());
       }
       return returnList;
   }


}