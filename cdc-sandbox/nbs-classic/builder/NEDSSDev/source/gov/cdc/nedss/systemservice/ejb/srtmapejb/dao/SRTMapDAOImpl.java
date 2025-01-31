package gov.cdc.nedss.systemservice.ejb.srtmapejb.dao;

/**
 * Title: SRTMapDAOImpl.java
 * Description: SRTMapEJB is a stateless session EJB for SRT values.
 * Copyright:   Copyright (c) 2001
 * Company: CSC
 * @author: Roger Wilson
 * @updateAuthor: Pradeep Sharma
 * Description: 
 */
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.observation.dt.LoincResultDT;
import gov.cdc.nedss.entity.observation.vo.LoincSearchResultTmp;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldURLMapDT;
import gov.cdc.nedss.nnd.dt.CodeLookupDT;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.srtadmin.dt.LoincDT;
import gov.cdc.nedss.srtadmin.dt.SnomedDT;
import gov.cdc.nedss.systemservice.dt.XSSFilterPatternDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.sqlscript.SRTSQLQuery;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.PreDefinedTreatmentDT;
import gov.cdc.nedss.systemservice.util.TestResultTestFilterDT;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.util.StringUtils;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;
public class SRTMapDAOImpl
    extends DAOBase {

   static final LogUtils logger = new LogUtils(SRTMapDAOImpl.class.getName());
   private boolean verbose = false; // indicates whether commments should be printed to the console
   private boolean standardizeNames = false; // indicates whether method names should follow naming  // standards --- note: this flag insures backward compatibility
   private Map<Object, Object> classMap = new HashMap<Object, Object>();
   private StringUtils stringUtils = new StringUtils();
   private String query = null;
   protected static boolean DEBUG_MODE = false; // Used for debugging
   protected static Properties ps = null;
   static Map<Object, Object> beanMap = new HashMap<Object, Object>();
   protected static Hashtable<Object, Object> ht = null;
   protected static DataSource dataSource = null;
   public static InitialContext jndiCntx = null;
   private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
   public static final String DATA_SOURCE_REFERENCE =
       "java:jboss/datasources/NedssDataSource";
   private static  ArrayList<Object> cacheSnomedCodes = null;
   public SRTMapDAOImpl() {
   }
   
   @SuppressWarnings("unchecked")
public ArrayList<Object>  getCodedValuesList(String code, String sql) throws Exception {
	      logger.debug("Starts getCodeValues(code, sql).");
	      String codeSql = null;
	      ArrayList<Object>  list = null;
	      try{
		      //  logic to check if code has seperate table
		     
		         /*if (code != null && code.equalsIgnoreCase("PHC_IMPRT")) {
		            logger.debug("1 code: " + code + "sql: " + codeSql);
		            codeSql = SRTSQLQuery.PHCQUERYSQL;
		         }
		         else if (code != null && !code.equalsIgnoreCase("PHC_IMPRT")) {
		            logger.debug("code: " + code + "sql: " + codeSql);*/
		            if(ELRConstants.ELR_LOG_PROCESS.equals(code)) {
		              codeSql = SRTSQLQuery.CODEQUERYSQL_FOR_DESCRIPTION_TXT;
		            } else {
		              codeSql = SRTSQLQuery.CODEQUERYSQL;
		            }
	
		         logger.debug("codesql set to code_query_sql");
		     // }
		      if (code == null) {
		         codeSql = sql; //ent table
		         // else, code is in common table
	
		      }
		      ArrayList<Object>  arrayList = new ArrayList<Object> ();
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		      if (code != null) {
		         arrayList.add(code);
		      }
		      list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
		          codeSql, NEDSSConstants.SELECT);
	      
	      }catch(Exception e){
		      logger.fatal("Exception  = "+e.getMessage(), e);
			  throw new NEDSSSystemException(e.toString());
	      }
	      
	      return list;
	   }

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getNullFlavorCodedValuesList(String code)
			throws Exception {
		logger.debug("Starts getNullFlavourCodedValuesList(code, sql).");
		String codeSql = null;
		ArrayList<Object> list = null;
		try {
			// logic to check if code has seperate table
			codeSql = SRTSQLQuery.NULL_FLAVOR_CODED_VALUES;
			ArrayList<Object> arrayList = new ArrayList<Object>();
			DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			if (code != null) {
				arrayList.add(code);
			}
			list = (ArrayList<Object>) preparedStmtMethod(dropDownCodeDT,
					arrayList, codeSql, NEDSSConstants.SELECT);

		} catch (Exception e) {
			logger.fatal("Exception  = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}

		return list;
	}
   
   @SuppressWarnings("unchecked")
public TreeMap<Object, Object> getCodedValues(String code, String sql) throws Exception {
      logger.debug("Starts getCodeValues(code, sql).");
      TreeMap<Object, Object> codeMap = null;
      String codeSql = null;
      
      try{
	      //  logic to check if code has seperate table
	        /* if (code != null && code.equalsIgnoreCase("PHC_IMPRT")) {
	            logger.debug("1 code: " + code + "sql: " + codeSql);
	            codeSql = SRTSQLQuery.PHCQUERYSQL;
	         }
	         else if (code != null && !code.equalsIgnoreCase("PHC_IMPRT")) {
	            logger.debug("code: " + code + "sql: " + codeSql);*/
	            if(ELRConstants.ELR_LOG_PROCESS.equals(code)) {
	              codeSql = SRTSQLQuery.CODEQUERYSQL_FOR_DESCRIPTION_TXT;
	            } else {
	              codeSql = SRTSQLQuery.CODEQUERYSQL;
	            }
	         logger.debug("codesql set to code_query_sql");
	     // }
	      if (code == null) {
	         codeSql = sql; //ent table
	         // else, code is in common table
	
	      }
	      ArrayList<Object>  arrayList = new ArrayList<Object> ();
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      if (code != null) {
	         arrayList.add(code);
	      }
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object, Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      
      }catch(Exception ex){
	      logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
      }
      return codeMap;
   }
   
   
   @SuppressWarnings("unchecked")
   public TreeMap<Object, Object> getCodedAltValues(String code, String sql) throws Exception {
         logger.debug("Starts getCodeValues(code, sql).");
         TreeMap<Object, Object> codeMap = null;
         String codeSql = null;
         
         try{
	         //  logic to check if code has seperate table
	           /* if (code != null && code.equalsIgnoreCase("PHC_IMPRT")) {
	               logger.debug("1 code: " + code + "sql: " + codeSql);
	               codeSql = SRTSQLQuery.PHCQUERYSQL;
	            }
	            else if (code != null && !code.equalsIgnoreCase("PHC_IMPRT")) {
	               logger.debug("code: " + code + "sql: " + codeSql);*/
	               if(ELRConstants.ELR_LOG_PROCESS.equals(code)) {
	                 codeSql = SRTSQLQuery.CODEQUERYSQL_FOR_DESCRIPTION_TXT;
	               } else {
	                 codeSql = SRTSQLQuery.CODEQUERYSQL;
	               }
	            logger.debug("codesql set to code_query_sql");
	        // }
	         if (code == null) {
	            codeSql = sql; //ent table
	            // else, code is in common table
	
	         }
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         if (code != null) {
	            arrayList.add(code);
	         }else{
	        	 arrayList.add("CASE_DIAGNOSIS");
	         }
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	             codeSql, NEDSSConstants.SELECT);
	         codeMap = new TreeMap<Object, Object>();
	         for (int count = 0; count < list.size(); count++) {
	            DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	            if(dt.getAltValue() != null && !dt.getAltValue().isEmpty()){
	            	codeMap.put(dt.getAltValue(),dt.getKey() );
	            }
	         }
         
         }catch(Exception ex){
	         logger.fatal("Exception  = "+ex.getMessage(),ex);
	 		 throw new NEDSSSystemException(ex.toString());
         }
         return codeMap;
      }
   @SuppressWarnings("unchecked") 
   public TreeMap<Object, Object> getStandredConceptCVGList(String code, String sql) throws Exception {
	      logger.info("Starts getStandredConceptCVGList(code, sql).");
	      TreeMap<Object, Object> codeSystemNmMap = null;
	      ArrayList<Object>  arrayList = new ArrayList<Object> ();
	      try{
	      CodeValueGeneralCachedDT codeValueGeneralCachedDt = new CodeValueGeneralCachedDT();
	      if (code != null) {
	         arrayList.add(code);
	      }
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(codeValueGeneralCachedDt, arrayList,
	    		  sql, NEDSSConstants.SELECT);
	      codeSystemNmMap = new TreeMap<Object, Object>();
	      for (int count = 0; count < list.size(); count++) {
	    	 CodeValueGeneralCachedDT dt = (CodeValueGeneralCachedDT) list.get(count);
	    	 codeSystemNmMap.put(dt.getCode(), dt);
	      }
	      }catch(Exception ex){
		      logger.fatal("Exception  = "+ex.getMessage(), ex);
			  throw new NEDSSSystemException(ex.toString());
	      }
	      return codeSystemNmMap;
   }
   
   public String getConceptCode(Long codeSetGroupId) throws Exception
   {
	   PropertyUtil propUtil= PropertyUtil.getInstance();
	   String codesetQuery="";
	   String codeSetNm="";
	   Connection dbConnection = null;
       ResultSet resultSet = null;
       ResultSet resultSetCode = null;
       PreparedStatement preparedStmt = null;
       try{
		   codesetQuery = "select code_set_nm from  nbs_srte..codeset where code_set_group_id=?";
		   dbConnection = getConnection();
	       preparedStmt = dbConnection.prepareStatement(codesetQuery);
	       preparedStmt.setLong(1, codeSetGroupId);
	       resultSet = preparedStmt.executeQuery();
	       resultSet.next();
	       codeSetNm = resultSet.getString(1);
       }
       catch(Exception ex)
       {
           logger.fatal("Error getting getConceptCode. ", ex);
           throw new Exception(ex);
       }
       finally
       {
           closeResultSet(resultSet);
           closeResultSet(resultSetCode);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
       }
       return codeSetNm;
   }

   public String getConceptToCode(String code, Long codeSetGroupId,String codeSystemCode, boolean isConceptCode) throws Exception {
	   String tableName="" ;
	   String nbsCode="" ;
       Connection dbConnection = null;
       ResultSet resultSet = null;
       ResultSet resultSetCode = null;
       PreparedStatement preparedStmt = null;
       PreparedStatement preparedStmtCode = null;
       String codesetQuery="";
       String codeQuery="";
       String codeSetNm="";
       
       PropertyUtil propUtil= PropertyUtil.getInstance();
       codesetQuery = "select class_cd,code_set_nm from nbs_srte..codeset where code_set_group_id=?";
	  	
       
       try
       {
           dbConnection = getConnection();
           preparedStmt = dbConnection.prepareStatement(codesetQuery);
           preparedStmt.setLong(1, codeSetGroupId);
           resultSet = preparedStmt.executeQuery();
           resultSet.next();
           tableName = resultSet.getString(1);
           codeSetNm = resultSet.getString(2);
       
           if(!(tableName.equalsIgnoreCase("code_value_general")||
        		 tableName.equalsIgnoreCase("V_Condition_Code")||
        		 tableName.equalsIgnoreCase("V_Jurisdiction_code")||
        		 tableName.equalsIgnoreCase("v_Lab_Result")||
        		 tableName.equalsIgnoreCase("V_Race_code")||
        		 tableName.equalsIgnoreCase("V_State_code")||
        		 tableName.equalsIgnoreCase("V_State_county_code_value"))){
        	   return NEDSSConstants.TABLE_NOT_MAPPED;
           }
           if(tableName.equalsIgnoreCase("Country_code_iso")){
        	   tableName="V_Country_code";
           }
           //Following Not mapped
           //Code_value_clinical,Country_code_iso,Investigation_code,LAB_TEST,Language_code,LOINC_code,Naics_industry_code,
           //Occupation_code,program_area_code,Snomed_code,Treatment_code,V_Condition_Code,Zip_code_value
           
       	   if(isConceptCode)
       		   codeQuery = "select code from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".."+tableName+" where concept_code=? and code_set_nm=? and code_system_cd=?";
       	   else
       		   codeQuery = "select code from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".."+tableName+" where code=? and code_set_nm=? and code_system_cd=?";
      
           
           preparedStmtCode = dbConnection.prepareStatement(codeQuery);
           preparedStmtCode.setString(1, code);
           preparedStmtCode.setString(2, codeSetNm);
           preparedStmtCode.setString(3, codeSystemCode);
           resultSetCode = preparedStmtCode.executeQuery();
//           resultSetCode.next();
//           nbsCode = resultSetCode.getString(1);

           if (resultSetCode.next()) {        	   
        	   nbsCode = resultSetCode.getString(1);
           }

       }
        catch(Exception ex)
       {
           logger.fatal("Error getting max act UID. ", ex);
           throw new Exception( ex);
       }
       finally
       {
           closeResultSet(resultSet);
           closeResultSet(resultSetCode);
           closeStatement(preparedStmt);
           closeStatement(preparedStmtCode);
           releaseConnection(dbConnection);
       }
       return nbsCode;
   }
   @SuppressWarnings("unchecked")
public Collection<Object> getCodedValuesColl(String code, String sql) throws Exception {
      String codeSql = null;
      ArrayList<Object>  list = null;
      try{
      //  logic to check if code has seperate table
       /*  if (code != null && code.equalsIgnoreCase("PHC_IMPRT")) {
            codeSql = SRTSQLQuery.PHCQUERYSQL;
         }
         else if (code != null && !code.equalsIgnoreCase("PHC_IMPRT")) {*/
            codeSql = SRTSQLQuery.CODEQUERYSQL;
    //  }
      if (code == null) {
         codeSql = sql; //ent table
         // else, code is in common table

      }
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      if (code != null) {
         arrayList.add(code);
      }
      list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
          codeSql, NEDSSConstants.SELECT);
      
      }catch(Exception ex){
	      logger.fatal("Exception  ERROR = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
      }
      return list;
   }

   /**
    * This method used to get the code and description from LOINC_CODE table
    * and it i sused to get the code and description from LAB_TEST also,
    * Since we are passing the code as null it will always take  the query
    * which we pass it as the parameter.
    * @param code
    * @param sql
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getCodedValuesResultedTest(String code, String sql) throws Exception {
   logger.info("Starts getCodedValuesResultedTest(code, sql).");
   TreeMap<Object,Object> codeMap = null;
   String codeSql = null;
   try{
	   //  logic to check if code has seperate table
	   codeSql = SRTSQLQuery.RESULTDTESTDESCSQL;
	   
	  if (code == null) {
	      codeSql = sql; //end table
	    }
	   ArrayList<Object>  arrayList = new ArrayList<Object> ();
	   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	   if (code != null) {
	      arrayList.add(code);
	   }
	   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	       codeSql, NEDSSConstants.SELECT);
	   codeMap = new TreeMap<Object,Object>();
	   for (int count = 0; count < list.size(); count++) {
	      DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	      codeMap.put(dt.getKey(), dt.getValue());
	   }
   }catch(Exception ex){
	   logger.fatal("Exception  = "+ex.getMessage(), ex);
	   throw new NEDSSSystemException(ex.toString());
   }
   return codeMap;
}


   /**
    * @method   :   getLabTestCd
    * @param labTestSql  String
    * @param labTestOracleSql
    * @return   :   java.lang.String
    * @throws Exception
    */
   public TreeMap<Object,Object> getLabTestCodes(String labTestSql) throws
       Exception {

      logger.debug("Inside method getLabTestCd...");
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      try{
	      codeSql = labTestSql;
	      logger.debug("codeSql = labTestOracleSql");
	      
	      logger.debug("codeSql is: " + codeSql);
	      // else, code is in common table
	      codeMap = getCodedValues(null, codeSql);
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      
      return codeMap;
   } //getLabTestCd

   /**
    * @method   :   getSuspTestCodes
    * @param suspTestSql  String
    * @param suspTestOracleSql  String
    * @return   :   java.lang.String
    * @throws Exception
    */
   public TreeMap<Object,Object> getSuspTestCodes(String suspTestSql) throws
       Exception {

      logger.debug("Inside method getSuspTestCodes...");
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	     codeSql = suspTestSql;
	     logger.debug("codeSql = suspTestOracleSql");
	      logger.debug("codeSql is: " + codeSql);
	      // else, code is in common table
	      codeMap = getCodedValues(null, codeSql);
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return codeMap;
   } //getSuspTestCodes

   /**
    * To be used by assignProgramAreaCd(...)
    * @param code String
    * @param type String
    * @param clia
    * @return Collection
    * @throws NEDSSDAOSysException
    */
   public Collection<Object> getSnomed(String code, String type, String clia) throws
       NEDSSDAOSysException {
      Connection conn = null;
      CallableStatement cs = null;
      ArrayList<Object>  toReturn = new ArrayList<Object> ();
      int i = 1;
      try {
         conn = getConnection(NEDSSConstants.ODS);
         cs = conn.prepareCall(ELRConstants.MSG_PROG_AREA_LOINC_SNOMED_SP);

         cs.setString(i++, code);
         cs.setString(i++, type);
         cs.setString(i++, clia);
         cs.registerOutParameter(i++, java.sql.Types.VARCHAR);
         cs.registerOutParameter(i++, java.sql.Types.INTEGER);

         cs.execute();
         //Integer resultSetCount = (Integer)cs.getObject(5);
         String sno = (String) cs.getObject(4);
         if (sno != null) {
            sno = sno.trim();
         }
         toReturn.add(0, sno);
         toReturn.add(1, new Integer(cs.getInt(5)));

      }
      catch (Exception e) {
    	  logger.fatal("Exception  = "+e.getMessage(), e);
         throw new NEDSSDAOSysException(e.toString(), e);
      }
      finally {
         closeCallableStatement(cs);
         releaseConnection(conn);
      }
      return toReturn;
   } //end of getSnomed(...)

   public boolean removePADerivationExcludedSnomedCodes(String snomedCd){
	   String selectSql="";;
	   selectSql = SRTSQLQuery.SELECT_SNOMED_PROGRAM_AREA_EXCLUSION_SQL;
	   
	   //Create WHERE clause for snomed codes to look up 
	    if (snomedCd != null && snomedCd.trim().length() > 0)    selectSql =selectSql+" where snomed_cd = ?";

	   SnomedDT snomedDT = new SnomedDT();
	   Connection dbConnection = null;
	   PreparedStatement preparedStmt = null;
	   ResultSet resultSet = null;
	   ResultSetMetaData resultSetMetaData = null;
	   ResultSetUtils resultSetUtils = new ResultSetUtils();
	   ArrayList<Object>  pList = new ArrayList<Object> ();

	   /**
	    * Selects snomed code and pa_derivation_exclude_cd from Snomed_code table
	    */
	   try
	   {
		   dbConnection = getConnection();
		   preparedStmt = dbConnection.prepareStatement(selectSql);
		   preparedStmt.setString(1, snomedCd);
		   resultSet = preparedStmt.executeQuery();
		   resultSetMetaData = resultSet.getMetaData();
		   pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
				   resultSetMetaData,
				   snomedDT.getClass(),
				   pList);

		   for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
		   {
			   snomedDT = (SnomedDT)anIterator.next();
			   //If a code should be excluded, we return true
			   if (snomedDT.getPaDerivationExcludeCd() != null && snomedDT.getPaDerivationExcludeCd().equals(NEDSSConstants.YES))
				   return true;

			   else 
				   return false;
		   }

		   return false;

	   }
	   catch (SQLException sqlex)
	   {
		   logger.fatal(
				   "SQLException while selecting " +
				   "SnomedDT; snomed_cd = " + snomedDT.getSnomedCd(), sqlex);
		   throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
	   }
	   catch (Exception ex)
	   {
		   logger.fatal(
				   "Exception while selecting SnomedDT; snomed_cd = " +
				   snomedDT.getSnomedCd(), ex);
		   throw new NEDSSSystemException(ex.getMessage(), ex);
	   }
	   finally
	   {
		   closeResultSet(resultSet);
		   closeStatement(preparedStmt);
		   releaseConnection(dbConnection);
	   }

   }
   

   public boolean removePADerivationExcludedLoincCodes(String loincCd){
	   String selectSql;
	   selectSql = SRTSQLQuery.SELECT_LOINC_PROGRAM_AREA_EXCLUSION_SQL;
	   
	   if (loincCd != null && loincCd.length() > 0)    selectSql = selectSql+" where loinc_cd = ? ";

	   LoincDT loincDT = new LoincDT();
	   Connection dbConnection = null;
	   PreparedStatement preparedStmt = null;
	   ResultSet resultSet = null;
	   ResultSetMetaData resultSetMetaData = null;
	   ResultSetUtils resultSetUtils = new ResultSetUtils();
	   ArrayList<Object>  pList = new ArrayList<Object> ();

	   /**
	    * Selects loinc code and pa_derivation_exclude_cd from LOINC_code table
	    */
	   try
	   {
		   dbConnection = getConnection();
		   preparedStmt = dbConnection.prepareStatement(selectSql);
		   preparedStmt.setString(1, loincCd);
		   resultSet = preparedStmt.executeQuery();
		   resultSetMetaData = resultSet.getMetaData();
		   pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
				   resultSetMetaData,
				   loincDT.getClass(),
				   pList);

		   for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
		   {
			   loincDT = (LoincDT)anIterator.next();
			   //If a code should be excluded, we return true
			   if (loincDT.getPaDerivationExcludeCd() != null && loincDT.getPaDerivationExcludeCd().equals(NEDSSConstants.YES))
				   return true;
			   
			   else 
				   return false;
		   }
  
	   	   return false;
	   }
	   catch (SQLException sqlex)
	   {
		   logger.fatal(
				   "SQLException while selecting " +
				   "LoincDT; loinc_cd = " + loincDT.getLoincCd(), sqlex);
		   throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
	   }
	   catch (Exception ex)
	   {
		   logger.fatal(
				   "Exception while selecting LoincDT; loinc_cd = " +
				   loincDT.getLoincCd(), ex);
		   throw new NEDSSSystemException(ex.getMessage(), ex);
	   }
	   finally
	   {
		   closeResultSet(resultSet);
		   closeStatement(preparedStmt);
		   releaseConnection(dbConnection);
	   }

   }
   public boolean removePADerivationExcludedLabTestCodes(String labTestCd, String reportingLabCLIA){
	   String selectSql;
	   selectSql = SRTSQLQuery.SELECT_LAB_TEST_PROGRAM_AREA_EXCLUSION_SQL;
	   
	   //Create WHERE clause for lab test codes to look up 
	   selectSql = selectSql+" where lab_test_cd = ? AND laboratory_id = ?";

	   LabTestDT labTestDT = new LabTestDT();
	   Connection dbConnection = null;
	   PreparedStatement preparedStmt = null;
	   ResultSet resultSet = null;
	   ResultSetMetaData resultSetMetaData = null;
	   ResultSetUtils resultSetUtils = new ResultSetUtils();
	   ArrayList<Object>  pList = new ArrayList<Object> ();

	   /**
	    * Selects lab test code, lab clia # and pa_derivation_exclude_cd from Snomed_code table
	    */
	   try
	   {
		   dbConnection = getConnection();
		   preparedStmt = dbConnection.prepareStatement(selectSql);
		   preparedStmt.setString(1, labTestCd);
		   preparedStmt.setString(2, reportingLabCLIA);
		   resultSet = preparedStmt.executeQuery();
		   resultSetMetaData = resultSet.getMetaData();
		   pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
				   resultSetMetaData,
				   labTestDT.getClass(),
				   pList);

		   for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
		   {
			   labTestDT = (LabTestDT)anIterator.next();
			   //If a code should be excluded, we return true
			   if (labTestDT.getPaDerivationExcludeCd() != null && labTestDT.getPaDerivationExcludeCd().equals(NEDSSConstants.YES))
				   return true;
			   else
				   return false;
		   }

		   return false;
	   }
	   catch (SQLException sqlex)
	   {
		   logger.fatal(
				   "SQLException while selecting " +
				   "LabTestDT; lab_test_cd = " + labTestDT.getLabTestCd(), sqlex);
		   throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
	   }
	   catch (Exception ex)
	   {
		   logger.fatal(
				   "Exception while selecting LabTestDT; lab_test_cd = " +
				   labTestDT.getLabTestCd(), ex);
		   throw new NEDSSSystemException(ex.getMessage(), ex);
	   }
	   finally
	   {
		   closeResultSet(resultSet);
		   closeStatement(preparedStmt);
		   releaseConnection(dbConnection);
	   }

   }
   public boolean removePADerivationExcludedLabResultCodes(String labResultCd, String reportingLabCLIA){
	   String selectSql;
	   selectSql = SRTSQLQuery.SELECT_LAB_RESULT_PROGRAM_AREA_EXCLUSION_SQL;
	   
	   selectSql = selectSql+" Where lab_result_cd = ? AND laboratory_id = ?";

	   LabResultDT labResultDT = new LabResultDT();
	   Connection dbConnection = null;
	   PreparedStatement preparedStmt = null;
	   ResultSet resultSet = null;
	   ResultSetMetaData resultSetMetaData = null;
	   ResultSetUtils resultSetUtils = new ResultSetUtils();
	   ArrayList<Object>  pList = new ArrayList<Object> ();

	   /**
	    * Selects lab result code, lab clia # and pa_derivation_exclude_cd from Snomed_code table
	    */
	   try
	   {
		   dbConnection = getConnection();
		   preparedStmt = dbConnection.prepareStatement(selectSql);
		   preparedStmt.setString(1, labResultCd);
		   preparedStmt.setString(2, reportingLabCLIA);
		   resultSet = preparedStmt.executeQuery();
		   resultSetMetaData = resultSet.getMetaData();
		   pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
				   resultSetMetaData,
				   labResultDT.getClass(),
				   pList);

		   for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
		   {
			   labResultDT = (LabResultDT)anIterator.next();
			   //If a code should be excluded, we need to remove it from the labResultVector
			   if (labResultDT.getPaDerivationExcludeCd() != null && labResultDT.getPaDerivationExcludeCd().equals(NEDSSConstants.YES)) 
				   return true;
			   else 
				   return false; 
		   }
			   
		   return false;
	   }
	   catch (SQLException sqlex)
	   {
		   logger.fatal(
				   "SQLException while selecting " +
				   "LabResultDT; lab_result_cd = " + labResultDT.getLabResultCd(), sqlex);
		   throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
	   }
	   catch (Exception ex)
	   {
		   logger.fatal(
				   "Exception while selecting LabResultDT; lab_result_cd = " +
				   labResultDT.getLabResultCd(), ex);
		   throw new NEDSSSystemException(ex.getMessage(), ex);
	   }
	   finally
	   {
		   closeResultSet(resultSet);
		   closeStatement(preparedStmt);
		   releaseConnection(dbConnection);
	   }

   }

   
   public Collection<Object> getMsgProgAreaLoincSnomed(String code, String type,
                                               String clia) throws
       NEDSSDAOSysException {
      Connection conn = null;
      CallableStatement cs = null;
      ArrayList<Object>  toReturn = new ArrayList<Object> ();
      int i = 1;
      try {
         conn = getConnection(NEDSSConstants.ODS);
         cs = conn.prepareCall(ELRConstants.MSG_PROG_AREA_LOINC_SNOMED);

         cs.setString(i++, code);
         cs.setString(i++, type);
         cs.setString(i++, clia);
         cs.registerOutParameter(i++, java.sql.Types.VARCHAR);
         cs.registerOutParameter(i++, java.sql.Types.INTEGER);

         cs.execute();
         String value = (String) cs.getObject(4);
         if (value != null) {
            value = value.trim();
         }
         toReturn.add(0, value);
         toReturn.add(1, new Integer(cs.getInt(5)));
      }
      catch (Exception e) {
    	  logger.fatal("Exception  = "+e.getMessage(), e);
          throw new NEDSSDAOSysException(e.toString(), e);
      }
      finally {
         closeCallableStatement(cs);
         releaseConnection(conn);
      }
      return toReturn;
   }

   @SuppressWarnings("unchecked")
public Collection<Object> getProgAreaCdLocalDefault(String code, String clia,
                                               String sql) throws
       NEDSSDAOSysException {

      Vector<Object> defaultProgAreaCd = new Vector<Object>();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(clia);
      arrayList.add(code);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sql, NEDSSConstants.SELECT);
	      for (int count = 0; count < list.size(); count++) {
	        String key = ( (DropDownCodeDT) list.get(count)).getKey();
	        if(key != null)
	         defaultProgAreaCd.add(key);
	      }
	      
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return defaultProgAreaCd;
   } //end of getProgAreaCdLocalDefault(...)

   public Collection<Object> getProgAreaCd(String code, String type, String clia) throws
       NEDSSDAOSysException {
      Connection conn = null;
      CallableStatement cs = null;
      ArrayList<Object>  toReturn = new ArrayList<Object> ();
      int i = 1;
      try {
         conn = getConnection(NEDSSConstants.ODS);
         cs = conn.prepareCall(ELRConstants.MSG_PROG_AREA_SP);

         cs.setString(i++, code);
         cs.setString(i++, type);
         cs.setString(i++, clia);
         cs.registerOutParameter(i++, java.sql.Types.VARCHAR);
         cs.registerOutParameter(i++, java.sql.Types.INTEGER);

         cs.execute();
         //Integer resultSetCount = (Integer)cs.getObject(5);
         String progAreaCd = (String) cs.getObject(4);
         if (progAreaCd != null) {
            progAreaCd = progAreaCd.trim();
         }
         toReturn.add(0, progAreaCd);
         toReturn.add(1, new Integer(cs.getInt(5)));

      }
      catch (Exception e) {
    	  logger.fatal("Exception  = "+e.getMessage(), e);
         throw new NEDSSDAOSysException(e.toString(), e);
      }
      finally {
         closeCallableStatement(cs);
         releaseConnection(conn);
      }
      return toReturn;
   } //end of getProgAreaCd(...);

   @SuppressWarnings("unchecked")
public String getProgramAreaCd(String code, String programAreaSql) throws Exception {
      String codeSql;
      String codeValue = null;
      try{
	      //  logic to check if code has seperate table
	      
	      codeSql = programAreaSql;
	      logger.debug("codeSQL is" + codeSql);
	      if (code == null) {
	         codeSql = null; //ent table
	         logger.error("getProgramArea: Input code == null");
	      }
	      else {
	         // else, code is in common table
	
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         arrayList.add(code);
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	         dropDownCodeDT = (DropDownCodeDT) list.get(0);
	         //codeValue = dropDownCodeDT.getKey();
	         codeValue = dropDownCodeDT.getValue();
	
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return codeValue;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getProgramAreaConditions(String programAreas,
                                           int indentLevelNbr,
                                           String programAreaConditonsSql) throws
       Exception {
      String codeSql;
      TreeMap<Object,Object> programAreaTreeMap = null;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql = programAreaConditonsSql + programAreas;
	      if (programAreas == null) {
	         codeSql = null; //ent table
	         logger.error("getProgramAreaConditions: Input programAreas == null");
	      }
	      else {
	         // else, code is in common table
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         ProgramAreaVO programAreaVO = new ProgramAreaVO();
	         arrayList.add(new Integer(indentLevelNbr));
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(programAreaVO,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	         programAreaTreeMap = new TreeMap<Object,Object>();
	         for (int count = 0; count < list.size(); count++) {
	            ProgramAreaVO vo = (ProgramAreaVO) list.get(count);
	            programAreaTreeMap.put(vo.getConditionCd(), vo);
	
	         }
	      }
      
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return programAreaTreeMap;
   }
   @SuppressWarnings("unchecked")
public ProgramAreaVO getProgramAreaForCondition(String conditionCode,
           String programAreaConditonsSql) throws
           Exception {
	   		String codeSql;
	   		ProgramAreaVO programAreaVO = new ProgramAreaVO();
			//  logic to check if code has seperate table
			codeSql = programAreaConditonsSql;
			if (conditionCode == null) {
				codeSql = null; //ent table
				logger.error("getProgramAreaConditionsWOIndent: Input conditionCode == null");
			}
			else {
				// else, code is in common table
				ArrayList<Object>  arrayList = new ArrayList<Object> ();
			
				arrayList.add(conditionCode);
				ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(programAreaVO,
						arrayList, codeSql, NEDSSConstants.SELECT);
				for (int count = 0; count < list.size(); count++) {
					programAreaVO = (ProgramAreaVO) list.get(count);
				}
			}
			return programAreaVO;
}

   @SuppressWarnings("unchecked")
public Collection<Object> findJurisdiction(String zipCd, String sqlQuery,
                                      String typeCd) throws
       NEDSSSystemException {
      Vector<Object> v = new Vector<Object>();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(zipCd);
      arrayList.add(typeCd);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sqlQuery, NEDSSConstants.SELECT);
	      for (int count = 0; count < list.size(); count++) {
	         v.add( ( (DropDownCodeDT) list.get(count)).getKey());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return v;
   } //end of findJurisdiction

   @SuppressWarnings("unchecked")
public Collection<Object> findJurisdictionForCity(String cityDescTxt, String stateCd,
                                             String sqlQuery, String typeCd) throws
       NEDSSSystemException {
      Vector<Object> v = new Vector<Object>();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(typeCd);
      arrayList.add(cityDescTxt);
      arrayList.add(stateCd);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sqlQuery, NEDSSConstants.SELECT);
	      for (int count = 0; count < list.size(); count++) {
	         v.add( ( (DropDownCodeDT) list.get(count)).getKey());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return v;
   } //end of findJurisdiction

   /**
    *
    * @param cityDescTxt
    * @param sqlQuery
    * @param typeCd
    * @return
    * @throws NEDSSSystemException
    * This method used to get jurisdiction code from city desc txt
    */
   @SuppressWarnings("unchecked")
public Collection<Object>dmFindJurisdictionForCity(String cntyDescTxt,
                                             String sqlQuery, String typeCd)
                                             throws NEDSSSystemException {
      Vector<Object> v = new Vector<Object>();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(typeCd);
      arrayList.add(cntyDescTxt);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sqlQuery, NEDSSConstants.SELECT);
	      for (int count = 0; count < list.size(); count++) {
	         v.add( ( (DropDownCodeDT) list.get(count)).getKey());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return v;
   } //end of findJurisdiction

   @SuppressWarnings("unchecked")
public Collection<Object> getProgramArea(String loincValue, String sql) throws
       NEDSSSystemException {
      Vector<Object> v = new Vector<Object>();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(loincValue);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sql, NEDSSConstants.SELECT);
	      for (int count = 0; count < list.size(); count++) {
	         v.add( ( (DropDownCodeDT) list.get(count)).getKey());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return v;
   } //end of getProgramArea

   @SuppressWarnings("unchecked")
public String getStateCd(String sql, String stateNm) throws
       NEDSSSystemException {
      String stateCd = null;
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(stateNm);
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      try{
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          sql, NEDSSConstants.SELECT);
	      if (list.size() > 0) {
	         stateCd = ( (DropDownCodeDT) list.get(0)).getKey();
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return stateCd;
   }

   /*
    *-------getProgramAreaCodedValue--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getProgramAreaCodedValue(String programAreaCodedSql) throws
       Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
	     codeSql = programAreaCodedSql;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return codeMap;
   } //getProgramAreaCodedValue

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getAllCountryCodes(String programAreaCodedSql) throws
       Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;

      try{
	      codeSql = programAreaCodedSql;

	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return codeMap;
   } //getProgramAreaCodedValue
   
   
   @SuppressWarnings("unchecked")
   public TreeMap<Object,Object> getAllCountryCodesOrderByShortDesc(String programAreaCodedSql,
                                        String programAreaCodedOracleSql) throws
          Exception {
         TreeMap<Object,Object> codeMap = null;
         String codeSql;
         try{
	         codeSql = programAreaCodedSql;
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	             codeSql, NEDSSConstants.SELECT);
	         codeMap = new TreeMap<Object,Object>();
	         for (int count = 0; count < list.size(); count++) {
	            DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	            codeMap.put(dt.getKey(), dt.getValue());
	         }
         }catch(Exception ex){
        	 logger.fatal("Exception  = "+ex.getMessage(), ex);
        	 throw new Exception(ex.toString());
         }
         return codeMap;
      }

   // ----------Stuff for security -
   /*
    *-------getProgramAreaCodedValues--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getProgramAreaCodedValues() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
    	  codeSql = SRTSQLQuery.PROGRAM_AREA_CODED_VALUES_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   } //getProgramAreaCodedValue
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getCodedResultValue() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
	     codeSql = SRTSQLQuery.CODED_RESULT_VALUES_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   } //getProgramAreaCodedValue
   
   
   public TreeMap<Object,Object> getCodedResultSuscValue() throws Exception {
	      TreeMap<Object,Object> codeMap = null;
	      String codeSql;
	      
	      try{
		      //  logic to check if code has seperate table
		      codeSql = SRTSQLQuery.CODED_RESULT_VALUES_SUSC_SQL;
		      logger.debug("codeSQL is" + codeSql);
		
		      // else, code is in common table
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		          codeSql, NEDSSConstants.SELECT);
		      codeMap = new TreeMap<Object,Object>();
		      for (int count = 0; count < list.size(); count++) {
		         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		         codeMap.put(dt.getKey(), dt.getValue());
		      }
	      }catch(Exception ex){
	    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	  throw new Exception(ex.toString());
	      }
	      return codeMap;
	   } //getProgramAreaCodedValue
	   
   
   
   public TreeMap<Object,Object> getResultMethodSuscValue() throws Exception {
	      TreeMap<Object,Object> codeMap = null;
	      String codeSql;
	      
	      try{
		      //  logic to check if code has seperate table
		      codeSql = SRTSQLQuery.RESULT_METHOD_VALUES_SUSC_SQL;
		      logger.debug("codeSQL is" + codeSql);
		
		      // else, code is in common table
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		          codeSql, NEDSSConstants.SELECT);
		      codeMap = new TreeMap<Object,Object>();
		      for (int count = 0; count < list.size(); count++) {
		         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		         codeMap.put(dt.getKey(), dt.getValue());
		      }
	      }catch(Exception ex){
	    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	  throw new Exception(ex.toString());
	      }
	      return codeMap;
	   } //getProgramAreaCodedValue
	   
   
   
   @SuppressWarnings("unchecked")
   public ArrayList<Object> getCodedResultValueList() throws Exception {
         String codeSql;
         try{
	         //  logic to check if code has seperate table
	         codeSql = SRTSQLQuery.CODED_RESULT_VALUES_SQL;
	         logger.debug("codeSQL is" + codeSql);
	
	         // else, code is in common table
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	             codeSql, NEDSSConstants.SELECT);
	         return new ArrayList<Object>(list);
        
         }catch(Exception ex){
        	 logger.fatal("Exception  = "+ex.getMessage(), ex);
        	 throw new Exception(ex.toString());
         }
      } //getProgramAreaCodedValue

   /*
    *-------getProgramAreaNumericIDs--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getProgramAreaNumericIDs() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.PROGRAM_AREA_IDS_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getIntValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;

   } //getProgramAreaNumericIDs

   /*
    *-------getJurisdictionCodedValues--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getJurisdictionCodedValues() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.JURISDICTION_CODED_VALUES_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   } //getJurisdictionCodedValues
   
   
   /*
    *-------getJurisdictionCodedNoExpValues(No Export)--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getJurisdictionCodedNoExpValues() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.JURISDICTION_CODED_VALUES_FOR_DOC_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   } //getJurisdictionCodedValues

   /*
    *-------getExportJurisdictionCodedValues--------------------------------
    */
   @SuppressWarnings("unchecked")
public Collection<Object> getExportJurisdictionCodedValues() throws Exception {
	   String codeSql =null;
	   try{
		 codeSql = SRTSQLQuery.JURISDICTION_CODED_VALUES_SQL;
	      logger.debug("codeSQL is" + codeSql);
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list=new ArrayList<Object> ();
	      list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      return list;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	   }
   } //getExportJurisdictionCodedValues
   /*
    *-------getJurisdictionNumericIDs--------------------------------
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getJurisdictionNumericIDs() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.JURISDICTION_NUMERIC_IDS_SQL;
	      logger.debug("codeSQL is" + codeSql);
	
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getIntValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   } //getJurisdictionNumericIDs

//---------------------------------------

   @SuppressWarnings("unchecked")
public String getCodeDescTxt(String code, String CODE_DESC_TXT_SQL) throws
       Exception {
      String codeDescTxt = "";
      String codeSql;
      try{
	      codeSql = CODE_DESC_TXT_SQL;
	      logger.debug("getCodeDescTxt - codeSQL is" + codeSql);
	      if (code == null) {
	         codeSql = null; //ent table
	         logger.error("getCodeDescTxt: Input code == null");
	      }
	      else { // else, code is in common table
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         arrayList.add(code);
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	         if (list.size() > 0) {
	            dropDownCodeDT = (DropDownCodeDT) list.get(0);
	            codeDescTxt = dropDownCodeDT.getKey();
	         }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeDescTxt;
   }

   @SuppressWarnings("unchecked")
public String getJurisditionCD(String zipCD, String typeCD) {

      String resultCount = null;
      String result = null;

      try{
	      result = "SELECT  c.jurisdiction_cd 'key', null 'value' from " +
	                  NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	                  "..jurisdiction_participation c where c.type_cd = " + "\'" +
	                  typeCD + "\'" + " and c.fips_cd = " + "\'" + zipCD + "\'";
	      
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          result, NEDSSConstants.SELECT);
	      if (list.size() > 0) {
	         dropDownCodeDT = (DropDownCodeDT) list.get(0);
	         resultCount = dropDownCodeDT.getKey();
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return resultCount;
   }

   @SuppressWarnings("unchecked")
public int getCountOfCdNotNull(String cityCD) {
      int resultCount = 0;
      String result = null;
      try{
	      result = "select count(*) 'key', null 'value' from " +
	                  NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	                  "..Jurisdiction_participation where fips_cd = '" + cityCD +
	                  "'" + " and type_cd ='C' ";
	      logger.debug("cityCD is: " + cityCD);
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          result, NEDSSConstants.SELECT);
	      if (list.size() > 0) {
	         dropDownCodeDT = (DropDownCodeDT) list.get(0);
	         String resultStr = dropDownCodeDT.getKey();
	         logger.debug("resultstr is: " + resultStr);
	         if (resultStr != null && resultStr.trim().length() != 0) {
	            resultCount = new Integer(resultStr).intValue();
	         }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
      }
      return resultCount;
   }

   @SuppressWarnings("unchecked")
public String getCurrentSex(String currentSex) {
      String currentSexDesc = null;
      String result = null;
      try{
	      result =
	             "SELECT  code_short_desc_txt 'key', code_set_nm 'value' " +
	             "FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	             "..Code_value_general " +
	             " WHERE (code_set_nm = 'sex') AND (code = '" + currentSex + "')";
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          result, NEDSSConstants.SELECT);
	      if (list.size() > 0) {
	         dropDownCodeDT = (DropDownCodeDT) list.get(0);
	         currentSexDesc = dropDownCodeDT.getKey();
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
      }
      return currentSexDesc;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getBMDCodeValues(String code) throws Exception {

      logger.info("Starts getBMDCodeValues(code, sql).");

      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.BMDCODEQUERYSQL;
	      logger.debug("codeSQL here is" + codeSql);
	      if (code == null) {
	         logger.error("The code cannot be null: GetCodedValues: Input code = " +
	                      code);
	      }
	      // else, code is in common table
	      else {
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         arrayList.add(code);
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	
	         codeMap = new TreeMap<Object,Object>();
	         for (int count = 0; count < list.size(); count++) {
	            DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	            codeMap.put(dt.getKey(), dt.getValue());
	         }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getSDCodeValues(String code) throws Exception {

      logger.info("Starts getBMDCodeValues(code, sql).");
      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
	      if (code != null) {
	         logger.debug("test1: code: " + code + "sql: " + codeSql);
	         codeSql = SRTSQLQuery.REPORTSUMMARYQUERYSQL;
	       }
	      if (code == null) {
	         logger.error("The code cannot be null: Input code = " + code);
	      }
	      else {
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	         arrayList.add(code);
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	
	         codeMap = new TreeMap<Object,Object>();
	         for (int count = 0; count < list.size(); count++) {
	            DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	            codeMap.put(dt.getKey(), dt.getValue());
	         }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getSDataCodeValues(String InConditionForProgArea) throws
       Exception {

      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
    	  codeSql = SRTSQLQuery.RSUMMARYDATACODEQUERYSQL;
	     codeSql = codeSql + " where prog_area_cd IN (" + InConditionForProgArea +
	                ")  and upper(reportable_summary_ind) = 'Y' ";
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getActiveSDataCodeValues(String InConditionForProgArea) throws
       Exception {

      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      
      try{
	      codeSql = SRTSQLQuery.RSUMMARYDATACODEQUERYSQL;
	
	      //R3.0 summary_investigation_form_cd IS POPULATED for AggregateReporting Summary. So, exclude this for Old Summary Forms
	      codeSql = codeSql + " where prog_area_cd IN (" + InConditionForProgArea +
	                ")  and upper(reportable_summary_ind) = 'Y' and upper(status_cd) = 'A' and summary_investigation_form_cd IS NULL";
	      // else, code is in common table
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getCountyCodeProgAreaCd(String InConditionForProgArea) throws
       Exception {

      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
	     codeSql = SRTSQLQuery.RSUMMARYCONDITIONQUERYSQL;
	     codeSql = codeSql + " where prog_area_cd IN (" + InConditionForProgArea +
	                ")  and upper(reportable_summary_ind) = 'Y' ";
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   @SuppressWarnings("unchecked")
public HashMap<Object, Object> getLdfRules(String ldfType) throws Exception {
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
      arrayList.add(ldfType);
      String codeSql = null;
      
      try{
	     codeSql = SRTSQLQuery.GETLDFDROPDOWN_SQL;
	     
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          codeSql, NEDSSConstants.SELECT);
	
	      HashMap<Object, Object> map = new HashMap<Object, Object>();
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         map.put(dt.getKey(), dt.getValue());
	
	      }
	      return map;
      
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }

   }

   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getLdfRules() throws Exception {
	   ArrayList<Object>  arrayList = new ArrayList<Object> ();
	   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	   String codeSql = null;
	   
	   try{
		  codeSql = SRTSQLQuery.GETLDFSRTDROPDOWN_SQL;
		   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
				   codeSql, NEDSSConstants.SELECT);
	
		   TreeMap<Object,Object> map = new TreeMap<Object,Object>();
		   for (int count = 0; count < list.size(); count++) {
		      DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		      map.put(dt.getKey(), dt.getValue());
	
		   }
		   return map;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	   }
	}



   @SuppressWarnings("unchecked")
public ArrayList<Object>  getLdfPages() throws Exception {
      ArrayList<Object>  list = null;
      StateDefinedFieldURLMapDT ldfPageDt = new StateDefinedFieldURLMapDT();
      try{
	      list = (ArrayList<Object> ) preparedStmtMethod(ldfPageDt, null,
	                                            SRTSQLQuery.GETLDFPAGES,
	                                            NEDSSConstants.SELECT);
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return list;

   }
   @SuppressWarnings("unchecked")
public ArrayList<Object>  getLdfPageIDs() throws Exception {
       ArrayList<Object>  list = null;
       String query = null;
       LdfPageSetDT ldfPageSetDT = new LdfPageSetDT();
       try{
	       query = SRTSQLQuery.GETLDFPAGEIDS_SQL;
	       list = (ArrayList<Object> ) preparedStmtMethod(ldfPageSetDT, null,
	                                             query,
	                                             NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return list;

    }
   
   @SuppressWarnings("unchecked")
   public ArrayList<Object>  getLdfPageIDsHome() throws Exception {
          ArrayList<Object>  list = null;
          String query = null;
          LdfPageSetDT ldfPageSetDT = new LdfPageSetDT();
          try{
          query = SRTSQLQuery.GETLDFPAGEIDSHOME_SQL;
          list = (ArrayList<Object> ) preparedStmtMethod(ldfPageSetDT, null,
                                                query,
                                                NEDSSConstants.SELECT);
          }catch(Exception ex){
        	  logger.fatal("Exception  = "+ex.getMessage(), ex);
        	  throw new Exception(ex.toString());
          }
          return list;

       }
   @SuppressWarnings("unchecked")
public Collection<Object>  getLabTestProgAreaMapping() throws NEDSSSystemException {
      String result = null;

      try{
	      
	        result = "select laboratory_id laboratoryId, lab_test_desc_txt labTestDescTxt, test_type_cd testTypeCd," +
	                  " condition_desc_txt conditionDescTxt, prog_area_cd progAreaCd from " +
	                  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping  WHERE indent_level_nbr = '1' "+
	                  "order by laboratory_id, prog_area_cd, test_type_cd";
	      TestResultTestFilterDT testResultFilter = new TestResultTestFilterDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(testResultFilter, null,
	          result, NEDSSConstants.SELECT);
	      return list;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
   } //end of getLabTestProgAreaMapping

   @SuppressWarnings("unchecked")
public Collection<Object>  getLabTestNotMappedToPAMapping() throws NEDSSSystemException {
      String result = null;

      try{
	      
	        result = "select Lab_test.LABORATORY_ID laboratoryId, Lab_test.LAB_TEST_DESC_TXT labTestDescTxt, Lab_test.test_type_cd testTypeCd "+
	            " From "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_test Lab_test "+
	            //--where (Lab_test.LABORATORY_ID = 'abc')AND
	            " where laboratory_id = 'DEFAULT' "+
	            " AND lab_test.TEST_TYPE_CD IN ('O', 'R') "+
	            " AND not exists(select 1 from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping b "+
	                           " where b.LAB_TEST_CD = lab_test.lab_test_cd ) "+
	            " order by "+
	            " laboratory_id, test_type_cd";
	
	
	      TestResultTestFilterDT testResultFilter = new TestResultTestFilterDT();
	      ArrayList<Object>  inputArgs = new ArrayList<Object> ();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(testResultFilter, inputArgs,
	      result, NEDSSConstants.SELECT);
	      return list;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
   } //end of

   /**
    * To get the Ordered and Resulted Test Name
    * @param code
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getOrderedAndResultTest(String code) throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
	      //  logic to check if code has seperate table
	         codeSql =
	             "SELECT distinct LAB_TEST_CD 'key', LAB_TEST_DESC_TXT 'value'   from " +
	             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	             "..Lab_test where test_type_cd ='" + code + "'";
	      
	      ArrayList<Object>  arrayList = new ArrayList<Object> ();
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      int counter = 0;
	      if (list.size() > 100) {
	         counter = 100;
	      }
	      else {
	         counter = list.size();
	
	      }
	
	      for (int count = 0; count < counter; count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   /**
    * To get the Drug Names
    * @param code
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getDrugNames() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
	      //  logic to check if code has seperate table
	      codeSql =
	             "SELECT LAB_TEST_CD 'key', LAB_TEST_DESC_TXT 'value'  FROM  " +
	             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	             "..LAB_TEST where DRUG_TEST_IND='Y' AND TEST_TYPE_CD='R' AND LABORATORY_ID='DEFAULT'";
	      
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      int counter = 0;
	      /**if (list.size() > 100) {
	         counter = 100;
	      }
	      else {
	         counter = list.size();
	
	      }*/
	      for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	
	      }
	      return codeMap;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
   }

   /**
    * To get the treatment code
    * @param code
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getTreamentDesc() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      try{
	      //  logic to check if code has seperate table
	     codeSql =
	             "SELECT distinct treatment_cd 'key', treatment_desc_txt 'value' FROM " +
	             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	             "..treatment_code where treatment_type_cd='C'";
	     
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	      // int counter = 0;
	      //if(list.size() >100)
	      //   counter = 100;
	      // else
	      int counter = list.size();
	
	      for (int count = 0; count < counter; count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	
	      }
	      return codeMap;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
   }

   /**
    * To get the treatment code
    * @param code
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getTreamentDrug() throws Exception {
      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      
      try{
	      //  logic to check if code has seperate table
	      codeSql =
	             "SELECT distinct treatment_cd 'key', treatment_desc_txt 'value' FROM " +
	             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	             "..treatment_code where treatment_type_cd='D'";
	      
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	
	      int counter = list.size();
	
	      for (int count = 0; count < counter; count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return codeMap;
   }

   /**
    * To get the treatment code
    * @param code
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
public PreDefinedTreatmentDT getTreatmentPreDefinedFields(String treatmentCd) throws
       Exception {


      TreeMap<Object,Object> codeMap = null;
      String codeSql = null;
      PreDefinedTreatmentDT dt=null;
      try{
	      //  logic to check if code has seperate table
	      codeSql = SRTSQLQuery.TREATMENTPREDEFINEDQUERYSQL;
	      
	      if (treatmentCd == null) {
	         logger.error("The code cannot be null: GetCodedValues: Input code = " +
	                      treatmentCd);
	      }
	      // else, code is in common table
	      else {
	         ArrayList<Object>  arrayList = new ArrayList<Object> ();
	         PreDefinedTreatmentDT preDefinedTreatmentDT = new PreDefinedTreatmentDT();
	         arrayList.add(treatmentCd);
	         ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(preDefinedTreatmentDT,
	             arrayList, codeSql, NEDSSConstants.SELECT);
	
	         codeMap = new TreeMap<Object,Object>();
	         for (int count = 0; count < list.size(); count++) {
	            dt = (PreDefinedTreatmentDT) list.get(count);
	         }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
      return dt;
   }

   @SuppressWarnings("unchecked")
public String getOrganismReqdIndicatorForResultedTest(String rtTestCode)
  {
    String aQuery = "SELECT organism_result_test_ind FROM nbs_srte..lab_test WHERE lab_test_cd = ?";
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    String indicator = null;

    try
    {
      dbConnection = getConnection();

      preparedStmt = dbConnection.prepareStatement(aQuery);
      preparedStmt.setString(1, rtTestCode);
      resultSet = preparedStmt.executeQuery();

      while ( resultSet.next() )
      {
        indicator = resultSet.getString(1);
      }
      
      if(indicator == null || indicator.equals("N")){ // Check in the LOINC code table 
				StringBuffer newQuery = new StringBuffer(
						NEDSSSqlQuery.SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL);
				String where = "and ( Loinc_code.loinc_cd  = '" + rtTestCode
						+ "') order by Loinc_code.component_name";
				newQuery.append(where);

				LoincSearchResultTmp loincResult = new LoincSearchResultTmp();

				ArrayList<Object> resultList = (ArrayList<Object>) preparedStmtMethod(
						loincResult, null, newQuery.toString(),
						NEDSSConstants.SELECT);
				Iterator<Object> nameItr1 = resultList.iterator();

				while (nameItr1.hasNext()) {

					LoincSearchResultTmp tmp = (LoincSearchResultTmp) nameItr1
							.next();
					if (!(tmp.getLoincCd() == null)) {
						if (tmp.getLoincProperty() != null
								&& tmp.getLoincProperty().equalsIgnoreCase(
										"PRID")
								&& tmp.getRelatedClassCd() != null
								&& tmp.getRelatedClassCd().equalsIgnoreCase(
										"MICRO")
								&& tmp.getLoincMethod() != null) {

							String s = tmp.getLoincMethod();
							int length = s.length();
							for (int i = 0; i < length; i++) {
								char c1 = s.charAt(i);
								if (c1 == 'C') {
									int temp = i + 1;
									if (temp != length) {
										char c2 = s.charAt(temp);
										if (c2 == 'U') {
											indicator = "Y";
											break;
										}
									}
								}
							}
						}
					}
				}
			}
    }
    catch(Exception e)
    {
      logger.fatal("Exception ="+e.getMessage(), e);
      throw new NEDSSSystemException(e.toString());
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return indicator;
  }




  @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getOrganismList() {
    TreeMap<Object,Object> codeMap = null;
    String codeSql = null;
    try{
	     
	        codeSql =
	            "Select  Lab_result.LAB_RESULT_CD 'key', lab_result_desc_txt 'value' FROM "
	            + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_result Lab_result, "
	            + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_coding_system Lab_coding_system WHERE "+
	            " Lab_coding_system.laboratory_id = 'DEFAULT' and "+
	            " Lab_result.organism_name_ind = 'Y'";
	     DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	          codeSql, NEDSSConstants.SELECT);
	      codeMap = new TreeMap<Object,Object>();
	
	      int counter = list.size();
	
	      for (int count = 0; count < counter; count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	
	      }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
     return codeMap;
  }

  @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getOrganismListSNM() {
  TreeMap<Object,Object> codeMap = null;
  String codeSql = null;
  ArrayList<Object>  list = null;
  
  try{
	  
	  DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		
		
	  if(cacheSnomedCodes==null){
	   codeSql =
	          codeSql = "Select SNOMED_CD \"key\",SNOMED_DESC_TXT \"value\" " +
	     " from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..SNOMED_CODE " ;
	 
	   list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	        codeSql, NEDSSConstants.SELECT);
	   
	   cacheSnomedCodes=list;
	  }else{
		  
		  list=cacheSnomedCodes;
	  }
	  
	  
	  
	    codeMap = new TreeMap<Object,Object>();
	
	    int counter = list.size();
	
	    for (int count = 0; count < counter; count++) {
	       DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	       codeMap.put(dt.getKey(), dt.getValue());
	
	    }
  }catch(Exception ex){
	  logger.fatal("Exception  = "+ex.getMessage(), ex);
	  throw new NEDSSSystemException(ex.toString());
  }
   return codeMap;
}


  @SuppressWarnings("unchecked")
public TreeMap<Object,Object> getStateCodeDescTxt() throws
      Exception {
    String codeSql;
    TreeMap<Object,Object> stateTreeMap = new TreeMap<Object,Object>();
    try{
	    //  logic to check if code has seperate table
	    
	      codeSql = "select state_cd 'key', state_nm 'value' from " +
	          NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..state_code";
	      // else, code is in common table
	      ArrayList<Object>  arrayList = new ArrayList<Object> ();
	      ProgramAreaVO programAreaVO = new ProgramAreaVO();
	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	          codeSql, NEDSSConstants.SELECT);
	      Iterator<Object> it = list.iterator();
	      while(it.hasNext())
	      {
	      DropDownCodeDT dropDownDtdt = (DropDownCodeDT)it.next();
	      stateTreeMap.put(dropDownDtdt.getKey(), dropDownDtdt.getValue());
	      }
    }catch(Exception ex){
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new Exception(ex.toString());
    }
      return stateTreeMap;
    }

    public List<Object> findLoincCds(String labClia, String obsCd)
    {
      String aQuery = null;
      
      aQuery = "SELECT loinc_cd FROM nbs_srte..labtest_loinc WHERE laboratory_id = ? and lab_test_cd = ?";
      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      List<Object> loincCdList = null;

    try
    {
      dbConnection = getConnection();

      preparedStmt = dbConnection.prepareStatement(aQuery);
      preparedStmt.setString(1, labClia);
      preparedStmt.setString(2, obsCd);
      resultSet = preparedStmt.executeQuery();

      while ( resultSet.next() )
      {
        if(loincCdList == null) loincCdList = new ArrayList<Object> ();
        loincCdList.add(resultSet.getString(1));
      }
    }
    catch(Exception e)
    {
    	logger.fatal("Exception  = "+e.getMessage(), e);
    	throw new NEDSSSystemException(e.toString());
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return loincCdList;
  }

  public List<Object> findSnomedCds(String labClia, String obsCd)
    {
      String aQuery = null;
      aQuery = "SELECT snomed_cd FROM nbs_srte..lab_result_snomed WHERE laboratory_id = ? and lab_result_cd = ?";
      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      List<Object> snomedCdList = null;

    try
    {
      dbConnection = getConnection();

      preparedStmt = dbConnection.prepareStatement(aQuery);
      preparedStmt.setString(1, labClia);
      preparedStmt.setString(2, obsCd);
      resultSet = preparedStmt.executeQuery();

      while ( resultSet.next() )
      {
        if(snomedCdList == null) snomedCdList = new ArrayList<Object> ();
        snomedCdList.add(resultSet.getString(1));
      }
    }
    catch(Exception e)
    {
    	logger.fatal("Exception  = "+e.getMessage(), e);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return snomedCdList;
  }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getAllCountyNamesAndAssociatedStateCodesTogether() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;
       try{
	       codeSql = SRTSQLQuery.COUNTYSHORTDESCSQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    } //getAllCountyCodesAndAssociatedStateCodesTogether

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getACountysReportingSources(String countyCd) throws RemoteException, Exception
    {

       TreeMap<Object,Object> codeMap = null;
       String codeSql;

       try{
	       codeSql = SRTSQLQuery.REPORTSOURCESINCOUNTYSQl;
	       
	       ArrayList<Object>  list = new ArrayList<Object> ();
	       list.add(countyCd);
	
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	
	      list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, list, codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getConditionCodesAndProgramAreaCodes() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;

       try{
	       codeSql = SRTSQLQuery.CONDTIONANDPROGRAMAREA_CODE_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getConditionCodesAndInvFormCodes() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;

       try{
	       codeSql = SRTSQLQuery.CONDTIONANDINVFORM_CODE_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }


    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getConditionCodeFamilyList(String conditionCd) throws Exception
    {
        String codeSql;
        
        try{
	        codeSql = "SELECT condition_cd \"key\", " + " condition_short_nm \"value\" FROM " +
	            NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code " +
	            "where family_cd in (select family_cd from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + 
	            "..Condition_Code " + "where condition_cd='"+conditionCd+"') " +
	            " and status_cd='A' " +
	            " and port_req_ind_cd='F' " +
	            "and condition_cd in (select page_cond_mapping.condition_cd from wa_template, page_cond_mapping where wa_template.wa_template_uid = page_cond_mapping.wa_template_uid and template_type in ('Published' ,'Published With Draft'))" +
	            " order by condition_short_nm";
	        
	        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	            codeSql, NEDSSConstants.SELECT);   
	        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
       }         

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getAllCodeShortDescsForBMD120AndBMD121() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;

       try{
	       codeSql = SRTSQLQuery.BM_OTHER_BAC_SP_AND_BM_SPEC_ISOL_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }
    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getAllCodedValuesForVacNm() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;

       try{
	      codeSql = SRTSQLQuery.VAC_NM_CODE_SQL;
	      
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
	       return codeMap;
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
    }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getStateDefinedSRTCodeSetName() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;
       try{
	       codeSql = SRTSQLQuery.STATE_DEFINED_SRT_CODESET_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getSAICDefinedSRTCodeSets() throws Exception
    {
       TreeMap<Object,Object> codeSetMap = null;
       TreeMap<Object,Object> codeMap = null;
       String codeSql;
       
       try{
	       codeSql = SRTSQLQuery.SAIC_DEFINED_SRT_CODESET_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeSetMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          String codeSetName = dt.getKey();
	          codeMap = getSAICDefinedSRTCodesForCodeSetName(codeSetName);
	          codeSetMap.put(dt.getKey(), codeMap);
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeSetMap;
    }
    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getSAICDefinedSRTCodesForCodeSetName(String codeSetName) throws Exception
    {
      TreeMap<Object,Object> codeMap = null;
      String codeSql;
      try{
    	  	codeSql = SRTSQLQuery.SAIC_DEFINED_SRT_CODES_SQL;
	     
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  arrayList = new ArrayList<Object> ();
	       arrayList.add(codeSetName);
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	         codeMap.put(dt.getKey(), dt.getValue());
	       }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new Exception(ex.toString());
      }
       return codeMap;
}




    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getAllCodedsForBMD120AndBMD121() throws Exception
    {
       TreeMap<Object,Object> codeMap = null;
       String codeSql;
       try{
	       codeSql = SRTSQLQuery.BM_OTHER_BAC_SP_AND_BM_SPEC_ISOL_CODE_DESC_TEXT_KEY_SQL;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);
	       codeMap = new TreeMap<Object,Object>();
	       for (int count = 0; count < list.size(); count++) {
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          codeMap.put(dt.getKey(), dt.getValue());
	       }
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
       return codeMap;
    }


    public String getLabTestDescForCode(String qry,String code) throws NEDSSDAOSysException {

      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      String desc = "";
      try {
          dbConnection = getConnection();
          preparedStmt = dbConnection.prepareStatement(qry);
          preparedStmt.setString(1, code);
          resultSet = preparedStmt.executeQuery();
      	  while(resultSet.next()) {
      		desc = resultSet.getString(1);
      	  }

      } catch(Exception e) {
    	  logger.fatal("Exception  = "+e.getMessage(), e);
    	  throw new NEDSSSystemException(e.toString());
      } finally {
    	closeResultSet(resultSet);
  		closeStatement(preparedStmt);
  		releaseConnection(dbConnection);
  	}
      return desc;
    }
    
    /**
     * Used for Reporting Subsystem(Advance Filters)Can be extended for other parts of application as required
     * @return java.lang.String
     * @throws Exception
     */
    public String getAllFilterOperators()  throws Exception {
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("var aOperators_STRING = ").append(getFilterOperator(ReportConstantUtil.COLUMNTYPE_STRING)).append("\n");
    	sb.append("var aOperators_INTEGER = ").append(getFilterOperator(ReportConstantUtil.COLUMNTYPE_INTEGER)).append("\n");
    	sb.append("var aOperators_DATETIME = ").append(getFilterOperator(ReportConstantUtil.COLUMNTYPE_DATETIME)).append("\n");
    	
    	return sb.toString();
    }
    //Returns results in this format: [ ["EQ","Equal"], ["NE","Not Equal"] ];    
    private String getFilterOperator(String type) throws Exception
    {
    	Connection dbConnection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer("[ ");
        StringBuffer iSb = new StringBuffer();
        try {
            dbConnection = getConnection();
            stmt = dbConnection.prepareStatement(SRTSQLQuery.REPORT_FILTER_OPERATORS);
            stmt.setString(1, type);
        	resultSet = stmt.executeQuery();
        	while(resultSet.next()) {
        		iSb.append("[\"");
        		iSb.append(resultSet.getString(1));
        		iSb.append("\",\"");
        		iSb.append(resultSet.getString(2));
        		iSb.append("\"]");
        		iSb.append(", ");
        	}
        	sb.append(iSb.substring(0, iSb.length()-2));
        	sb.append(" ];");

        } catch(Exception e) {
        	logger.fatal("Exception  = "+e.getMessage(), e);
        	throw new NEDSSSystemException(e.toString());
        } finally {
    		closeResultSet(resultSet);
    		closeStatement(stmt);
    		releaseConnection(dbConnection);
    	}
        return sb.toString();
    }    
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAllCityCodes(String state_cd) throws Exception
    {
       String codeSql;

       try{
	       codeSql = "select code \"key\", code_desc_txt \"value\" from nbs_srte..city_code_value  where parent_is_cd = "+ "'"+state_cd+"' order by code_desc_txt" ;
	       
	       DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);   
	       return list;
	       }catch(Exception ex){
	    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	   throw new Exception(ex.toString());
	       }
       }
 
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAllQECodes(String Type) throws Exception
    {
       String codeSql= null;
       try{
	       if(Type.equalsIgnoreCase("PRV"))
	       codeSql = "SELECT ei.root_extension_txt \"key\", ei.root_extension_txt \"value\" from person p with (nolock), Entity_id ei  with (nolock) where p.person_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'AND (EI.TYPE_CD = 'QEC') AND  ei.record_status_cd = 'ACTIVE'  AND  p.electronic_ind = 'N'  AND  p.cd = 'PRV' ";
	       else if(Type.equalsIgnoreCase("ORG"))
	      // codeSql = "SELECT ei.root_extension_txt \"key\", ei.root_extension_txt \"value\" from organization p, Entity_id ei where p.ORGANIZATION_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'AND (EI.TYPE_CD = 'QEC') AND  ei.record_status_cd = 'ACTIVE'  AND  p.electronic_ind = 'N'  AND  p.cd = 'ORG'";
	    	 codeSql = "SELECT ei.root_extension_txt \"key\", ei.root_extension_txt \"value\" from organization p  with (nolock), Entity_id ei with (nolock) where p.ORGANIZATION_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'AND (EI.TYPE_CD = 'QEC') AND  ei.record_status_cd = 'ACTIVE'  AND  p.electronic_ind = 'N' ";
	    	 DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	           codeSql, NEDSSConstants.SELECT);   
	       return list;
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new Exception(ex.toString());
       }
      }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getLaboratoryIds() throws Exception
    {
        String codeSql;

        try{
	        codeSql = "select laboratory_id \"key\", laboratory_system_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_coding_system order by laboratory_system_desc_txt;";
	        
	        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	            codeSql, NEDSSConstants.SELECT);   
	        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
       }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAllConditionCodes() throws Exception
    {
        String codeSql;
        try{
        codeSql = "SELECT condition_cd \"key\", condition_short_nm \"value\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code order by condition_short_nm;";            
        
        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
            codeSql, NEDSSConstants.SELECT);   
        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
       }  
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAllCodeSetNms() throws Exception
    {
        String codeSql;
        try{
        codeSql = "SELECT distinct code_set_nm \"key\", code_set_nm \"value\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset where class_cd='code_value_general'";
        
        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
            codeSql, NEDSSConstants.SELECT);   
        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
       } 
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAllCodeSystemCdDescs(String codeSetNm) throws Exception
    {
        String codeSql;
        try{
	        codeSql = "SELECT distinct code_system_desc_txt \"key\", code_system_desc_txt \"value\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Code_value_general WHERE code_set_nm = '"+codeSetNm+"'";          
	        
	        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
	            codeSql, NEDSSConstants.SELECT);   
	        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
       }         

    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getSRTAdminCodedValuesList(String codeSetNm) throws Exception {
    	
    	   String sql = "Select code_desc_txt \"key\", code_short_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_Value_General where upper(code_set_nm) = '"+codeSetNm+"' order by code_short_desc_txt ";
    	   
    	   try{
			    
			    ArrayList<Object>  arrayList = new ArrayList<Object> ();
			    DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			    ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
			    return list;
    	   }catch(Exception ex){
    		   logger.fatal("Exception  = "+ex.getMessage(), ex);
    		   throw new Exception(ex.toString());
    	   }
	   }    
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getCodingSystemCodes(String assignAuth) throws Exception {
    	
 	   String sql = "Select code_desc_txt \"key\", code_short_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_Value_General where upper(code_set_nm) = 'CODE_SYSTEM' and assigning_authority_cd='" + assignAuth + "' order by code_short_desc_txt ";
 	   
 	   try{
			  ArrayList<Object>  arrayList = new ArrayList<Object> ();
			  DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			  ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
			  return list;
 	   }catch(Exception ex){
 		  logger.fatal("Exception  = "+ex.getMessage(), ex);
 		  throw new Exception(ex.toString());
 	   }
    }     
    public ArrayList<Object>  getLdfHtmlTypes(String formCd) throws Exception {
    	
   	   ArrayList<Object>  arrayList = new ArrayList<Object> ();
   	   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
   	   String where = "";
   	   
   	   try{
	    	//Step1: See if any new LDF Tab is added, if so then doent display the 'Tab' Component at all
	    	String qry = "select count(*) from nbs_ui_metadata where nbs_ui_component_uid=1010 and ldf_page_id is not null and record_status_cd='Active' and investigation_form_cd='" + formCd + "' ";
	    	int count = 0;
	    	count =  ((Integer) preparedStmtMethod(dropDownCodeDT, arrayList, qry, NEDSSConstants.SELECT_COUNT)).intValue();    	
	    	if(count > 0) 
	    		where = " and nbs_ui_component_uid <> 1010 ";
	    		
	  	   String sql = "Select CAST(nbs_ui_component_uid as varchar) \"key\", type_cd_desc \"value\" from NBS_UI_Component where ldf_available_ind='Y'" + where + " order by display_order ";
	  	   
	   	   
	  	   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
	  	   return list;
   	   }catch(Exception ex){
	   		logger.fatal("Exception  = "+ex.getMessage(), ex);
	   		throw new Exception(ex.toString());
   	   }
    }
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getAvailableTabs(String formCd) throws Exception {
    	
   	   String sql = "Select CAST(nbs_ui_metadata_uid as varchar) \"key\", question_label \"value\" from NBS_UI_Metadata where nbs_ui_component_uid=1010 and investigation_form_cd ='" + formCd + "' and record_status_cd='Active' and  question_label <> 'Patient' order by nbs_ui_metadata_uid ";
   	   
   	   try{
	   	   ArrayList<Object>  arrayList = new ArrayList<Object> ();
	   	   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	   	   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
	   	   return list;
   	   }catch(Exception ex){
	   		logger.fatal("Exception  = "+ex.getMessage(), ex);
	   		throw new Exception(ex.toString());
   	   }
    }     
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getLDFSections(Long tabId) throws Exception {
    	
	   String sql = "Select CAST(nbs_ui_metadata_uid as varchar) \"key\", question_label \"value\" from NBS_UI_Metadata where record_status_cd='Active' and nbs_ui_component_uid=1015 and parent_uid=" + tabId + " order by order_nbr ";
	  
	   try{
		   
		   ArrayList<Object>  arrayList = new ArrayList<Object> ();
		   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
		   return list;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	   }
     }     
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getLDFSubSections(Long sectionId) throws Exception {
    	
 	   String sql = "Select CAST(nbs_ui_metadata_uid as varchar) \"key\", question_label \"value\" from NBS_UI_Metadata where record_status_cd='Active' and nbs_ui_component_uid=1016 and parent_uid=" + sectionId + " order by order_nbr ";
 	  
 	   try{
	 	   ArrayList<Object>  arrayList = new ArrayList<Object> ();
	 	   DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	 	   ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
	 	   return list;
 	   }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString());
 	   }
      } 
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getCodesetNames() throws Exception {
    	String sql = "SELECT CAST(code_set_group_id as varchar) \"key\", code_set_short_desc_txt \"value\" from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset_Group_Metadata  where ldf_picklist_ind_cd='Y' order by code_set_short_desc_txt ";
    	
    	try{
	    
	    	ArrayList<Object>  arrayList = new ArrayList<Object> ();
	    	DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	    	ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);
	    	return list;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new Exception(ex.toString());
    	}
    	
    }

    @SuppressWarnings("unchecked")
	public TreeMap<Object,Object> getInvFrmCdLdfPgIdMap() throws Exception {
    	TreeMap<Object,Object> codeMap = null;
  	   	String sql = "Select a.ldf_page_id \"key\", b.investigation_form_cd \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..ldf_page_set a, "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code b  where a.condition_cd = b.condition_cd ";
  	   	
  	   	try{
	  	      ArrayList<Object>  arrayList = new ArrayList<Object> ();
	 	      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	 	      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, sql, NEDSSConstants.SELECT);	      
	 	      codeMap = new TreeMap<Object,Object>();
	 	      for (int count = 0; count < list.size(); count++) {
	 	         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	 	         codeMap.put(dt.getKey(), dt.getValue());
	 	      }
	 	      return codeMap;
  	   	}catch(Exception ex){
	  	   	logger.fatal("Exception  = "+ex.getMessage(), ex);
	  	   	throw new Exception(ex.toString());
  	   	}
     } 
    
    /**
     * getIsoCountyList returns ISO Country List
     * @return java.util.ArrayList<Object> 
     */
    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getIsoCountryList() throws Exception {
        String codeSql;
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        
        try{
	        codeSql = "SELECT  code \"key\", code_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Country_Code_ISO  where status_cd='A' order by code_desc_txt ";
	
	        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, codeSql, NEDSSConstants.SELECT);   
	        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
    }
    @SuppressWarnings("unchecked")
	public String getDescForCountyCode(String query) throws Exception{
    	String countyDesc="";
    	ArrayList<Object>  arrayList = new ArrayList<Object> ();
    	boolean valueSet= false;
    	DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
    	try{
	    	ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, query, NEDSSConstants.SELECT);	  
	    	for (int count = 0; count < list.size(); count++) {
	    		if(valueSet){
	    			logger.debug("County value already set for the sql query:" + query);
	    			return countyDesc;
	    		}
		         DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		         countyDesc=dt.getValue();
		         valueSet= true;
		      }
	    	return countyDesc;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new Exception(ex.toString());
    	}
    }

    @SuppressWarnings("unchecked")
	public ArrayList<Object>  getCodedValueOrderdByNbsUid(String type) throws Exception {
        String codeSql;
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        
        try{
	        codeSql = SRTSQLQuery.CODEQUERY_SQL_ORDERED;
	        
	        if (type != null)
	        	arrayList.add(type);
	        
	        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
	        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList, codeSql, NEDSSConstants.SELECT);   
	        return list;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new Exception(ex.toString());
        }
    }

	@SuppressWarnings("unchecked")
	public ArrayList<Object>  getExportReceivingFacilityList() {
		String codeSql=SRTSQLQuery.GET_EXPORT_LIST;
		 ArrayList<Object>  arrayList = new ArrayList<Object> ();
			DropDownCodeDT dcodeDT = new DropDownCodeDT();
		try{
			ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dcodeDT, arrayList, codeSql, NEDSSConstants.SELECT);   
			ArrayList<Object>  alist= new ArrayList<Object> (); 
			if(list!=null){
		        	Iterator<Object> it = list.iterator();
		        	while(it.hasNext()){
		        		DropDownCodeDT dt = (DropDownCodeDT) it.next();
		        		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        		dropDownCodeDT.setKey(dt.getLongKey().toString());
		        		dropDownCodeDT.setValue(dt.getValue());
		        		alist.add(dropDownCodeDT);
		        	}
	        }
	        return alist;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
		}
		return null;
	}   
	
	@SuppressWarnings("unchecked")
	public String getCodeShortDescTxt(String code,String codeSetNm, String CODE_SHORT_DESC_TXT_SQL) throws
		Exception {
		String codeSrtDescTxt = "";
		String codeSql;
		try{
			codeSql = CODE_SHORT_DESC_TXT_SQL;
			logger.debug("getCodeShortDescTxt - codeSQL is" + codeSql);
			if (code == null) {
				codeSql = null; //ent table
				logger.error("getCodeShortDescTxt: Input code == null");
			}
			else { // else, code is in common table
				ArrayList<Object>  arrayList = new ArrayList<Object> ();
				DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
				arrayList.add(code);
				arrayList.add(codeSetNm);
				ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT,
				arrayList, codeSql, NEDSSConstants.SELECT);
			if (list.size() > 0) {
				dropDownCodeDT = (DropDownCodeDT) list.get(0);
				codeSrtDescTxt = dropDownCodeDT.getKey();
			}
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return codeSrtDescTxt;
}
	
	/**
	 * returns Active Summary Specific Condition List<Object> for Aggregate Reporting
	 * @param InConditionForProgArea
	 * @return java.util.ArrayList<Object> 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object>  getActiveAggregateSDataCodeValues(String InConditionForProgArea)
			throws Exception {

		String codeSql = null;
		
		try{
			codeSql = SRTSQLQuery.RSUMMARYDATACODEQUERYSQL;
			//R3.0 summary_investigation_form_cd IS POPULATED for AggregateReporting Summary. So, exclude this for Old Summary Forms
			codeSql = codeSql
					+ " where prog_area_cd IN ("
					+ InConditionForProgArea
					+ ")  and upper(reportable_summary_ind) = 'Y' and upper(status_cd) = 'A' and summary_investigation_form_cd IS NOT NULL";
	
		     DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		            codeSql, NEDSSConstants.SELECT);   
	        return list;	       
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	 @SuppressWarnings("unchecked")
	 public TreeMap<Object,Object> getConditionTracingEnableInd() {
	     TreeMap<Object,Object> conditionMap = null;
	     String codeSql = null;
	     try{
		     codeSql =	SRTSQLQuery.CONTACTTRACINGENABLEINDSQL;
	
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		       ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		           codeSql, NEDSSConstants.SELECT);
		       conditionMap = new TreeMap<Object,Object>();
	
		       int counter = list.size();
	
		       for (int count = 0; count < counter; count++) {
		          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		          conditionMap.put(dt.getKey(), dt.getValue());
	
		       }
	     }catch(Exception ex){
	    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
	     }
	      return conditionMap;
	   }
		public ArrayList<Object>  getAllActiveCodeSetNms() throws Exception
	    {
	        String codeSql;

	        try{
		        
		        	//codeSql = "SELECT CAST(code_set_group_id as varchar) \"key\"," +
		            //"code_set_short_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Codeset_Group_Metadata  where ldf_picklist_ind_cd='Y' order by code_set_short_desc_txt" ;
		            
		            // NOTE: retrieving code_value_general notes only. This is probably a temporary thing for NBS 4.0
		            // Refer to defect civil00018878 in clear quest for more details.
		        	
	                codeSql = "SELECT CAST(code_set_group_id AS varchar) " + 
	                    "\"key\", code_set_short_desc_txt \"value\" FROM " + 
	                    NEDSSConstants.SYSTEM_REFERENCE_TABLE +  "..Codeset_Group_Metadata " +
	                    "WHERE ldf_picklist_ind_cd = 'Y' AND code_set_group_id IN (select code_set_group_id FROM " +
	                    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..codeset " +
	                    "WHERE class_cd = 'code_value_general' and status_cd = 'A') ORDER BY code_set_short_desc_txt;";
		       
		        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		            codeSql, NEDSSConstants.SELECT);
		             
		        return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new Exception(ex.toString());
	        }
	    }
		
		public ArrayList<Object>  getNbsUnitsType() throws Exception
	    {
	        String codeSql;
	        
	        try{
		        codeSql = "select code \"key\", code_desc_txt \"value\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where code_set_nm='NBS_UNIT_TYPE'";
		        
		        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		            codeSql, NEDSSConstants.SELECT);
		             
		        return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new Exception(ex.toString());
	        }
	    }
		
		public ArrayList<Object>  getDefaultDisplayControl(String compBeh) throws Exception
	    {
			try{
				if(compBeh.equals(NEDSSConstants.CODED)){
					compBeh = NEDSSConstants.CODED_DATA;
				} else if (compBeh.equals(NEDSSConstants.DATE_DATATYPE)
					|| compBeh.equals(NEDSSConstants.DATETIME_DATATYPE)
					|| compBeh.equals(NEDSSConstants.NUMERIC_DATATYPE)
					|| compBeh.equals(NEDSSConstants.TEXT_DATATYPE)) {
					compBeh = NEDSSConstants.TEXT_DATA;
				}else if(compBeh.equals(NEDSSConstants.PART_DATATYPE)){
					compBeh = NEDSSConstants.PARTICIPATION_DATA;
				} 
		        String codeSql;
	
		        codeSql =  "SELECT CAST(nbs_ui_component_uid as varchar) \"key\", type_cd_desc \"value\" from  nbs_ui_component where component_behavior=?";
		        ArrayList<Object>  arrayList = new ArrayList<Object> ();
			      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			      if (compBeh != null) {
			         arrayList.add(compBeh);
			      }
			      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
			          codeSql, NEDSSConstants.SELECT);
			      
			      return list;
			}catch(Exception ex){
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
	       
	    }
		
		public ArrayList<Object>  getDefaultDisplayControlDesc(String code) throws Exception
	    {
			
	        String codeSql;

	        try{
	        codeSql =  "SELECT CAST(nbs_ui_component_uid as varchar) \"key\", type_cd_desc \"value\" from  nbs_ui_component ";
	        ArrayList<Object>  arrayList = new ArrayList<Object> ();
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		      if (code != null) {
		         arrayList.add(code);
		      }
		      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
		          codeSql, NEDSSConstants.SELECT);
		      
		      return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new NEDSSSystemException(ex.toString());
	        }
	       
	    }
		
		public ArrayList<Object>  getRelatedUnit(String code) throws Exception
	    {
			
	        String codeSql;

	        try{
		        codeSql =  "SELECT type_cd \"key\", type_cd_desc \"value\" from  nbs_ui_component ";

		        ArrayList<Object>  arrayList = new ArrayList<Object> ();
			      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			      if (code != null) {
			         arrayList.add(code);
			      }
			      ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
			          codeSql, NEDSSConstants.SELECT);
			      
			      return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new NEDSSSystemException(ex.toString());
	        }
	       
	    }
		
		public ArrayList<Object>  getAllActiveTemplates(String busObjectType) throws Exception
	    {
	        String codeSql=SRTSQLQuery.GETTemplate;
	        
	        try{
		        codeSql = "SELECT CAST(wa_template_uid as varchar) \"key\"," +
		        	"template_nm \"value\" from wa_template where upper(record_status_cd) = '"+NEDSSConstants.RECORD_STATUS_ACTIVE+"' and upper(template_type) = '"+NEDSSConstants.TEMPLATE+"' and bus_obj_type='"+busObjectType+"' order by template_nm" ;

		        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		            codeSql, NEDSSConstants.SELECT);
		             
		        return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new NEDSSSystemException(ex.toString());
	        }
	    }
		public ArrayList<Object>  getAllActiveCodeSetNmsByGroupId() throws Exception
	    {
	        String codeSql;
	        try{
		        codeSql = "SELECT CAST(code_set_group_id as varchar) \"key\"," +
		            "code_set_nm \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Codeset_Group_Metadata  order by code_set_short_desc_txt" ;
		        
		        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
		            codeSql, NEDSSConstants.SELECT);
		             
		        return list;
	        }catch(Exception ex){
	        	logger.fatal("Exception  = "+ex.getMessage(), ex);
	        	throw new NEDSSSystemException(ex.toString());
	        }
	    }
	    
		 @SuppressWarnings("unchecked")
			public ArrayList<Object>  getvalueSetTypeCdNoSystemStrd() throws Exception
		    {
		        String codeSql;

		        try{
			        codeSql = "select code \"key\", code_desc_txt \"value\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where code_set_nm='NBS_QUESTION_TYPE' and code<>'SYS'";            
			        
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
			            codeSql, NEDSSConstants.SELECT);   
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       } 
		 
		 public ArrayList<Object>  getFilteredConditionCode(String sql) throws Exception
		    { 

		        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		        try{
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
			        		sql, NEDSSConstants.SELECT);   
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       } 
		 
		 @SuppressWarnings("unchecked")
			public ArrayList<Object>  getConditionWithNoPortReqInd() throws Exception
		    {
		        String codeSql;
		        try{
			        codeSql = "SELECT Page_cond_mapping.condition_cd \"key\", "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt \"value\""+
			            " FROM Page_cond_mapping INNER JOIN "+
			            NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code ON Page_cond_mapping.condition_cd = "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_cd where" +   
			            " Page_cond_mapping.wa_template_uid =(select wa_template_uid from wa_template where form_cd=?) order by "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt";            
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
			            codeSql, NEDSSConstants.SELECT);   
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       }
		 @SuppressWarnings("unchecked")
			public ArrayList<Object>  getInvestigationTypeRelatedPage() throws Exception
		    {
		        String codeSql;

		        try{
			       codeSql = "SELECT form_cd \"key\", TEMPLATE_NM \"value\" FROM WA_TEMPLATE where bus_obj_type = 'INV' and TEMPLATE_TYPE in ('Published With Draft', 'Published') order by template_nm";            
	
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, null,
			            codeSql, NEDSSConstants.SELECT);   
			        return list;
			        }catch(Exception ex){
			        	logger.fatal("Exception  = "+ex.getMessage(), ex);
			        	throw new NEDSSSystemException(ex.toString());
			        }
		       }
		 public ArrayList<Object>  getSendingSysList(String systemType) throws Exception
		    {
		        String codeSql;
		        
		        try{
		        	codeSql = "select receiving_system_short_nm \"key\", receiving_system_short_nm \"value\" from Export_receiving_facility where sending_ind_cd='Y' and type_cd = ? order by receiving_system_nm";            
			        
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        ArrayList<Object>  arrayList = new ArrayList<Object> ();
			        if (systemType != null) {
				           arrayList.add(systemType);
				        }
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
			            codeSql, NEDSSConstants.SELECT);   
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       }
		 
		 public ArrayList<Object>  getConditionDropDown(String relatedPage) throws Exception
		    {
		        String codeSql;

		        try{
			        codeSql = "SELECT Page_cond_mapping.condition_cd \"key\", "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt \"value\""+
			            ", 'A' \"statusCd\" FROM Page_cond_mapping INNER JOIN "+
			            NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code ON Page_cond_mapping.condition_cd = "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_cd where" +   
			            " Page_cond_mapping.wa_template_uid =(select wa_template_uid from wa_template where form_cd=? and publish_ind_cd='T') order by "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt";            
			        
			        ArrayList<Object>  arrayList = new ArrayList<Object> ();
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        ArrayList<Object>  list = null;
			        if (relatedPage != null) {
			           arrayList.add(relatedPage);
			           list= (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
					            codeSql, NEDSSConstants.SELECT);   
			        }
			           
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       }
		 /**
		  * Get the dropdown list of associated conditions for the draft or published page.
		  * @param waTemplateUid
		  * @return dropdown array of Condition Cd, Condition Desc
		  * @throws Exception
		  */
		 public ArrayList<Object>  getConditionDropDown(Long waTemplateUid) throws Exception
		    {
		        String codeSql;
		        try{
			        codeSql = "SELECT Page_cond_mapping.condition_cd \"key\", "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt \"value\""+
			            ", 'A' \"statusCd\"  FROM Page_cond_mapping INNER JOIN "+
			            NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code ON Page_cond_mapping.condition_cd = "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_cd where" +   
			            " Page_cond_mapping.wa_template_uid = ? order by "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code.condition_desc_txt";            
			        
			        ArrayList<Object>  arrayList = new ArrayList<Object> ();
			        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			        if (waTemplateUid != null) {
			           arrayList.add(waTemplateUid);
			        }
			        ArrayList<Object>  list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
			            codeSql, NEDSSConstants.SELECT);   
			        return list;
		        }catch(Exception ex){
		        	logger.fatal("Exception  = "+ex.getMessage(), ex);
		        	throw new NEDSSSystemException(ex.toString());
		        }
		       }	 
	public ArrayList<Object> getPublishedConditionDropDown() throws Exception {

		PropertyUtil propUtil = PropertyUtil.getInstance();
		String codesetQuery = "";
		Connection dbConnection = null;
		ResultSet resultSet = null;
		ArrayList<Object> toReturn = new ArrayList<Object>();
		ArrayList<Object> conditionList = new ArrayList<Object>();
		HashMap<Object, Object> conditionPageMap = new HashMap <Object, Object>();
		PreparedStatement preparedStmt = null;
		try {
			codesetQuery = "select page_cond_mapping.condition_cd,nbs_srte..Condition_code.condition_desc_txt, wa_template.form_cd , wa_template.TEMPLATE_NM from WA_TEMPLATE,page_cond_mapping, nbs_srte..Condition_code where wa_template.wa_template_uid = " +
					"page_cond_mapping.wa_template_uid and page_cond_mapping.condition_cd = nbs_srte..Condition_code.condition_cd and publish_ind_cd='T' and  nbs_srte..condition_code.port_req_ind_cd='F' and wa_template.bus_obj_type='INV' order by nbs_srte..Condition_code.condition_desc_txt";            
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(codesetQuery);
			resultSet = preparedStmt.executeQuery();
			
			while (resultSet.next()) {
				DropDownCodeDT conditionDT = new DropDownCodeDT();
				DropDownCodeDT pageDT = new DropDownCodeDT();
				
				conditionDT.setKey(resultSet.getString(1));
				conditionDT.setValue(resultSet.getString(2));
				pageDT.setKey(resultSet.getString(3));
				pageDT.setValue(resultSet.getString(4));
				
				conditionList.add(conditionDT);
				conditionPageMap.put(conditionDT.getKey(), pageDT);
		    }

			toReturn.add(0, conditionList);
			toReturn.add(1, conditionPageMap);

		} catch (Exception e) {
			 logger.error(
					   "Exception while selecting published conditions", e);
			   throw new NEDSSSystemException(e.getMessage(), e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return toReturn;
	} //getPublishedConditionDropDown()
/*
 * getConditionForSnomedCode - return the ConditionCd for the passed Snomed Resulted Test Code
 * @param snomedCd
 * @return conditionCd
 * 
 */
@SuppressWarnings("unchecked")
public String getConditionForSnomedCode(String snomedCd) throws NEDSSDAOSysException  {
	String conditionCd="" ;
     Connection dbConnection = null;
     ResultSet resultSet = null;
     PreparedStatement preparedStmt = null;
     String codesetQuery="";

     
     PropertyUtil propUtil= PropertyUtil.getInstance();
     codesetQuery = "SELECT  distinct condition_cd from  nbs_srte..snomed_condition where snomed_cd=?";
	 
     try
     {
         dbConnection = getConnection();
         preparedStmt = dbConnection.prepareStatement(codesetQuery);
         preparedStmt.setString(1, snomedCd);
         resultSet = preparedStmt.executeQuery();
         if (resultSet.next())
         	conditionCd = resultSet.getString(1); //Assumption is only one conditionCd for a snomed code
     }
      catch(Exception ex)
     {
         logger.warn("Error while getting the conditionCd for a Snomed Resulted Test ", ex);
         throw new NEDSSDAOSysException(ex.toString(), ex);
     }
     finally
     {
         closeResultSet(resultSet);
         closeStatement(preparedStmt);
         releaseConnection(dbConnection);
     }
     return conditionCd;
   }
/*
 * getConditionForLoincCode - return the ConditionCd for the passed Loinc Test Code
 * @param loincCd
 * @return conditionCd
 * 
 */
@SuppressWarnings("unchecked")
public String getConditionForLoincCode(String loincCd) throws NEDSSDAOSysException  {
	String conditionCd="" ;
     Connection dbConnection = null;
     ResultSet resultSet = null;
     PreparedStatement preparedStmt = null;
     String codesetQuery="";

     
     PropertyUtil propUtil= PropertyUtil.getInstance();
     codesetQuery = "SELECT  distinct condition_cd from  nbs_srte..loinc_code loc inner join nbs_srte..loinc_condition locc on loc.loinc_cd = locc.loinc_cd where loc.loinc_cd=?";
	 try
     {
         dbConnection = getConnection();
         preparedStmt = dbConnection.prepareStatement(codesetQuery);
         preparedStmt.setString(1, loincCd);
         resultSet = preparedStmt.executeQuery();
         if (resultSet.next())
         	conditionCd = resultSet.getString(1); //Assumption is only one conditionCd for a snomed code
     }
      catch(Exception ex)
     {
         logger.warn("Error while getting the conditionCd for a Snomed Resulted Test ", ex);
         throw new NEDSSDAOSysException(ex.toString(), ex);
     }
     finally
     {
         closeResultSet(resultSet);
         closeStatement(preparedStmt);
         releaseConnection(dbConnection);
     }
     return conditionCd;
   } //getConditionForLoincCode
 
/*
 * getDefaultConditionForLocalResultCode - if it exists, return the default ConditionCd for the local test
 *			from the lab_result table
 * @param labResultCd
 * @param lab_id
 * @return conditionCd
 */
@SuppressWarnings("unchecked")
public String getDefaultConditionForLocalResultCode(String labResultCd, String laboratoryId) throws NEDSSDAOSysException  {
	String conditionCd="" ;
     Connection dbConnection = null;
     ResultSet resultSet = null;
     PreparedStatement preparedStmt = null;
     String codesetQuery="";

     
     PropertyUtil propUtil= PropertyUtil.getInstance();
     codesetQuery = "SELECT default_condition_cd FROM nbs_srte..lab_result where lab_result_cd=? and laboratory_id =? ";
	  
     try
     {
         dbConnection = getConnection();
         preparedStmt = dbConnection.prepareStatement(codesetQuery);
         preparedStmt.setString(1, labResultCd);
         preparedStmt.setString(2, laboratoryId);
         resultSet = preparedStmt.executeQuery();
         if (resultSet.next())
         	conditionCd = resultSet.getString(1); //Assumption is only one default conditionCd for a lab result code
     }
      catch(Exception ex)
     {
         logger.warn("Error while getting the default conditionCd for a Lab Result.", ex);
         throw new NEDSSDAOSysException(ex.toString(), ex);
     }
     finally
     {
         closeResultSet(resultSet);
         closeStatement(preparedStmt);
         releaseConnection(dbConnection);
     }
     return conditionCd;
   } //getDefaultConditionForLocalResultCode

/*
 * getDefaultConditionForLabTest - if it exists, return the default ConditionCd for the lab test
 *			from the lab_test table
 * @param labTestCd
 * @return defaultConditionCd
 */ 
public String getDefaultConditionForLabTest(String labTestCd, String laboratoryId)
{

 String aQuery = null;
 
 aQuery = "SELECT default_condition_cd FROM nbs_srte..lab_test WHERE laboratory_id =? and lab_test_cd =?";
 
 Connection dbConnection = null;
 PreparedStatement preparedStmt = null;
 ResultSet resultSet = null;
 String defaultConditionCd = null;

 try
 {
   dbConnection = getConnection();

   preparedStmt = dbConnection.prepareStatement(aQuery);
   preparedStmt.setString(1, laboratoryId);
   preparedStmt.setString(2, labTestCd);
   resultSet = preparedStmt.executeQuery();

   while ( resultSet.next() )
   {
     defaultConditionCd = resultSet.getString(1);
   }
 }
 catch(Exception e)
 {
	 logger.fatal("Exception = "+e.getMessage(), e);
	 throw new NEDSSSystemException(e.toString());
 }
 finally
 {
   closeResultSet(resultSet);
   closeStatement(preparedStmt);
   releaseConnection(dbConnection);
 }
 return defaultConditionCd;
}

 /*
  * getTheParticipationTypes()
  * Get the list of Participation Types for the specified Act Class Cd i.e.InvestgrOfPHC, HospOfADT
  * @param actClassCode i.e. CASE
  * @param SQL for MS SQL
  * @param SQL for Oracle
  * @return ParticipationTypeVO ArrayList
  */
@SuppressWarnings("unchecked")
public ArrayList<Object> getTheParticipationTypes(String actClassCode,
                                        String participationTypesSql) 
                                        throws Exception {
   String codeSql;
   ArrayList<Object>  arrayList = new ArrayList<Object> ();
   try{
	   //  logic to check if Oracle or SQL Server
	   codeSql = participationTypesSql;
	   //actClassCode no longer used
	    ParticipationTypeVO participationTypeVO = new ParticipationTypeVO();
	    ArrayList<Object>  parTypeList = (ArrayList<Object> ) preparedStmtMethod(participationTypeVO,
	          arrayList, codeSql, NEDSSConstants.SELECT);
	    return parTypeList;
   }catch(Exception ex){
	   logger.fatal("Exception  = "+ex.getMessage(), ex);
	   throw new NEDSSSystemException(ex.toString());
   }
   

} // getTheParticipationTypes()


	@SuppressWarnings("unchecked")
	public Map<Object, Object> getCoinfectionCondition() throws Exception {
		String codeSql;
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		// logic to check if Oracle or SQL Server
		codeSql = SRTSQLQuery.COINFECTION_CONDITION_SQL;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();

			preparedStmt = dbConnection.prepareStatement(codeSql);

			resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {
				returnMap.put(resultSet.getString(1), resultSet.getString(2));
			}
		} catch (Exception e) {
			logger.fatal("Exception  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return returnMap;
	}

public ArrayList<Object> getXSSFilterPatterns() {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData = null;
	ResultSetUtils resultSetUtils = new ResultSetUtils();
	String xssQuery = "";

	try {

		dbConnection = getConnection(NEDSSConstants.SRT);

		xssQuery = " SELECT "
				+ " xss_filter_pattern_uid \"XSSFilterPatternUid\","
				+ " reg_exp  \"regExp\"," + " flag \"flag\","
				+ " desc_txt  \"descTxt\"," + " status_cd \"statusCd\","
				+ " status_time \"statusTime\"" + " FROM "
				+ DataTables.XSS_FILTER_PATTERN + " where status_cd='A'";

		preparedStmt = dbConnection.prepareStatement(xssQuery);

		resultSet = preparedStmt.executeQuery();
		ArrayList<Object> xssFilterPatternList = new ArrayList<Object>();

		if (resultSet != null)
			resultSetMetaData = resultSet.getMetaData();

		xssFilterPatternList = (ArrayList<Object>) resultSetUtils
				.mapRsToBeanList(resultSet, resultSetMetaData,
						XSSFilterPatternDT.class, xssFilterPatternList);
		return xssFilterPatternList;

	} catch (Exception e) {
		logger.error("Error in fetching XSS Filter Pattern Metadata  ");
		throw new NEDSSSystemException(e.toString(), e);
	} finally {
		closeResultSet(resultSet);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	} // end of finally

}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getPlaceList() throws Exception {
		String codeSql;
		codeSql = SRTSQLQuery.PLACE_LIST;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			dbConnection = getConnection();

			preparedStmt = dbConnection.prepareStatement(codeSql);

			resultSet = preparedStmt.executeQuery();

			resultSet = preparedStmt.executeQuery();
			DropDownCodeDT ddcDT = new DropDownCodeDT();
			if (resultSet.getFetchSize() > 0) {
				ddcDT.setKey("");
				ddcDT.setValue("");
				list.add(ddcDT);
			}
			while (resultSet.next()) {
				ddcDT = new DropDownCodeDT();
				ddcDT.setKey(String.valueOf(resultSet.getString(1)));
				ddcDT.setValue(resultSet.getString(2));
				list.add(ddcDT);
			}

		} catch (Exception e) {
			logger.fatal("Exception  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<Object, Object> getPlaceMap() throws Exception {
		String codeSql;
		codeSql = SRTSQLQuery.PLACE_LIST;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		TreeMap<Object, Object> codeMap = new TreeMap<Object, Object>();
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(codeSql);
			resultSet = preparedStmt.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					codeMap.put(resultSet.getString(1), resultSet.getString(2));
				}
			}

		} catch (Exception e) {
			logger.fatal("Exception  = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return codeMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getNBSCodeFromPHINCodes() throws Exception {
		String codeSql;
		Map<String, String> returnMap = new HashMap<String, String>();
		// logic to check if Oracle or SQL Server
		codeSql = SRTSQLQuery.PHIN_TO_NBS_CODE_SQL;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();

			preparedStmt = dbConnection.prepareStatement(codeSql);

			resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {
				returnMap.put(resultSet.getString(1), resultSet.getString(2));
			}
		} catch (Exception e) {
			logger.fatal("Exception  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return returnMap;
	}
	
	
	public CodeLookupDT getLabCodeSystem(String labCode, String laboratoryID)
			throws Exception {
		CodeLookupDT codeLookupDT = null;
		try {
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(laboratoryID);// Lab Id/Clia
			inArrayList.add(labCode);// lab code
			outArrayList.add(java.sql.Types.VARCHAR); // code description
			outArrayList.add(java.sql.Types.VARCHAR); // code system code
			outArrayList.add(java.sql.Types.VARCHAR); // code system description
														// text
			outArrayList.add(java.sql.Types.VARCHAR); // alt code
			outArrayList.add(java.sql.Types.VARCHAR); // alt code description
														// text
			outArrayList.add(java.sql.Types.VARCHAR); // alt code system code
			outArrayList.add(java.sql.Types.VARCHAR); // alt code system
														// description text
			outArrayList.add(java.sql.Types.VARCHAR); // lab id description text

			String sQuery = "{call usp_getLabCodingSystem(?,?,?,?,?,?,?,?,?,?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery,
					inArrayList, outArrayList, NEDSSConstants.SRT);

			if (arrayList != null && arrayList.size() > 0) {
				codeLookupDT = new CodeLookupDT();
				codeLookupDT.setCodedValue(labCode);
				codeLookupDT
						.setCodedValueDescription((String) arrayList.get(0));
				codeLookupDT.setCodedValueCodingSystem((String) arrayList
						.get(1));
				codeLookupDT
						.setCodedValueCodingSystemDescTxt((String) arrayList
								.get(2));
				codeLookupDT.setLocalCodedValue((String) arrayList.get(3));
				codeLookupDT.setLocalCodedValueDescription((String) arrayList
						.get(4));
				codeLookupDT.setLocalCodedValueCodingSystem((String) arrayList
						.get(5));
				codeLookupDT
						.setLocalCodedValueCodingSystemDescTxt((String) arrayList
								.get(6));
				codeLookupDT
				.setDescTxt((String) arrayList
				.get(7));
			}
		} catch (Exception e) {
			logger.fatal(
					"Error in fetching coding system for lab " + e.getMessage(),
					e);
			throw new NEDSSSystemException(e.toString(), e);
		}
		return codeLookupDT;
	}
	
	public Map<String, String> getAOELOINCCodes() throws Exception {
		String codeSql;
		Map<String, String> returnMap = new HashMap<String, String>();
			codeSql = SRTSQLQuery.AOE_LOINC_CODES;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try {
			dbConnection = getConnection();

			preparedStmt = dbConnection.prepareStatement(codeSql);

			resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {
				returnMap.put(resultSet.getString(1), resultSet.getString(2));
			}
		} catch (Exception e) {
			logger.fatal("Exception  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return returnMap;
	}


} //class

