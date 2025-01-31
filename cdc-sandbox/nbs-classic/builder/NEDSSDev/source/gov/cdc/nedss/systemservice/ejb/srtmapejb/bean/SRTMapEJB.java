package gov.cdc.nedss.systemservice.ejb.srtmapejb.bean;

import gov.cdc.nedss.entity.person.vo.PersonEthnicityVO;
import gov.cdc.nedss.entity.person.vo.PersonRaceVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CodeLookupDT;
import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.sqlscript.SRTSQLQuery;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.PreDefinedTreatmentDT;
/**
 * Title: Session EJB Class using ResultSetUtil.
 * Description: This class is the EJB for SRTMap session bean.
 * Copyright:    Copyright (c) 2001
 * Company:csc
 * @author: Roger Wilson and every one else in development :)
 */
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SRTMapEJB
    implements SessionBean {

   //For logging
   static final LogUtils logger = new LogUtils(SRTMapEJB.class.getName());
   boolean verbose = false;
   private static PropertyUtil propUtil= PropertyUtil.getInstance();

   public TreeMap<Object, Object> getCodedValues(String code) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(code, null);
		   
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public ArrayList<Object>  getCodedValuesList(String code) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = util.getCodedValuesList(code, null);

	      return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public ArrayList<Object>  getNullFlavorCodedValuesList(String code) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = util.getNullFlavorCodedValuesList(code);

	      return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public TreeMap<Object,Object> getCityCodes(String state) throws RemoteException, Exception {

	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	
	
	      map = util.getCodedValues(null, SRTSQLQuery.CITYSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getStateCodes(String country) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	
	
	     map = util.getCodedValues(null, SRTSQLQuery.STATESQL);
	     
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /**
    * @param : country
    * @return: treemap
    * Description : Takes STATESQL1 as sql for code and short_desc_txt display
    * author   : Nedss Development Team
    */
   public TreeMap<Object,Object> getStateCodes1(String country) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	
	      //  logic to check if code has seperate table
	      map = util.getCodedValues(null, SRTSQLQuery.STATESQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /**
    * Retrieves an ordered map of all state FIPS code / two-letter abbreviation pairs.
    * 
    * @param country
    * @return
    * @throws RemoteException
    * @throws Exception
    */
   public TreeMap<Object,Object> getStateCodesAndAbbreviations(String country) throws RemoteException, Exception {

	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	      map = util.getCodedValues(null, SRTSQLQuery.STATEABSQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /**
    *
    */
   public TreeMap<Object,Object> getCountyCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      String tempCOUNTYSQL = null;
	      TreeMap<Object,Object> map = null;
	      tempCOUNTYSQL = SRTSQLQuery.COUNTYSQL;
	      map = util.getCodedValues(null, tempCOUNTYSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public String getCountyDesc(String code) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      String tempCOUNTYSQL = null;
	      String countyDesc = null;

	         tempCOUNTYSQL = SRTSQLQuery.COUNTY_DESC_SQL +"'"+code+"'";
	         countyDesc  = util.getDescForCountyCode(tempCOUNTYSQL);
	      return countyDesc;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   
   public TreeMap<Object,Object> getCountyCodes(String state) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      String whereClause = " and parent_is_cd = '" + state + "' order by code_desc_txt";
	      String tempCOUNTYSQL = null;
	      TreeMap<Object,Object> map = null;
	      tempCOUNTYSQL = SRTSQLQuery.COUNTYSQL + whereClause;
	      map = util.getCodedValues(null, tempCOUNTYSQL);
	      
	      map.put("", ""); // an empty record
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public ArrayList<Object>  getCountryCodesList() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = null;

	      //  logic to check if code has seperate table
	      list = util.getCodedValuesList(null, SRTSQLQuery.COUNTRYSQL);
	      return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
	   
	   
	      public ArrayList<Object>  getRaceCodesList(String superCode) throws RemoteException,
	          Exception {
	    	  try{
		         logger.debug("Inside getRaceCodes ");
		   
		         String whereClause = "where status_cd='A' and parent_is_cd = '" + superCode + "'";
		         String tempRACESQL = null;
		   
		         //  logic to check if code has seperate table
		         tempRACESQL = SRTSQLQuery.RACESQL + whereClause;
		         
		         SRTMapDAOImpl util = new SRTMapDAOImpl();
		         ArrayList<Object>  list = util.getCodedValuesList(null, tempRACESQL);
		   
		         return list;
	    	  }catch(RemoteException ex){
	   		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
	   		   throw new RemoteException(ex.toString(),ex);
	   	   }catch(Exception ex){
	   		   logger.fatal("Exception  = "+ex.getMessage(), ex);
	   		   throw new Exception(ex.toString(),ex);
	   	   }
	      }


   public ArrayList<Object>  getCountyCodeList(String state) throws RemoteException,
   Exception {
	   try{
		   	  SRTMapDAOImpl util = new SRTMapDAOImpl();
		      String tempCOUNTYSQL = null;
		      ArrayList<Object>  list= null;
		      String whereClause = " and parent_is_cd = '" + state + "'";
		      
		     tempCOUNTYSQL = SRTSQLQuery.COUNTYSQL;
	         list = util.getCodedValuesList(state, tempCOUNTYSQL);
		      return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
}
   /**
    *
    * @return
    * @throws RemoteException
    * @throws Exception
    * this method retun tree map. It contain code and short desc txt as key/value.
    */
   public TreeMap<Object,Object> getCountryShortDescTxt() throws RemoteException, Exception{
	   try{
	     SRTMapDAOImpl util = new SRTMapDAOImpl();
	     TreeMap<Object,Object> map = null;
	
	     //  logic to check if code has seperate table
	     map = util.getCodedValues(null, SRTSQLQuery.COUNTRYSHORTDESCSQL);
	    
	     return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }


   public TreeMap<Object,Object> getCountryCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	
	      //  logic to check if code has seperate table
	      map = util.getCodedValues(null, SRTSQLQuery.COUNTRYSQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getRaceCodes(String superCode) throws RemoteException,
       Exception {
      logger.debug("Inside getRaceCodes ");
      try{
	      String whereClause = "where status_cd='A' and parent_is_cd = '" + superCode + "'";
	      String tempRACESQL = null;
	
	      //  logic to check if code has seperate table
	      tempRACESQL = SRTSQLQuery.RACESQL + whereClause;
	      
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempRACESQL);
	
	      return map;
      }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public TreeMap<Object,Object> getRaceCodesByCodeSet(String codeSetName) throws RemoteException,
       Exception
   {
     logger.debug("Inside getRaceCodesByCodeSet ");
     try{
	     String whereClause = "where status_cd='A' and code_set_nm = '" + codeSetName + "'";
	     String tempRACESQL = null;
	
	     //  logic to check if code has seperate table
	     tempRACESQL = SRTSQLQuery.RACESQL + whereClause;
	     
	     SRTMapDAOImpl util = new SRTMapDAOImpl();
	     TreeMap<Object,Object> map = util.getCodedValues(null, tempRACESQL);
	
	     return map;
     }catch(RemoteException ex){
	   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
	   throw new RemoteException(ex.toString(),ex);
	 }catch(Exception ex){
	   logger.fatal("Exception  = "+ex.getMessage(), ex);
	   throw new Exception(ex.toString(),ex);
	 }

   }

   public TreeMap<Object,Object> getAddressType() throws RemoteException, Exception {
      logger.debug("Inside getAddressType ");
      try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	
	      //  logic to check if code has seperate table
	      map = util.getCodedValues(null, SRTSQLQuery.GETADDRTYPESQL);
	    
	      return map;
      }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getAddressUse() throws RemoteException, Exception {
      logger.debug("Inside getAddressUse ");
      try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	
	      map = util.getCodedValues(null, SRTSQLQuery.GETADDRUSESQL);
	      
	      return map;
      }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getRegionCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl utils = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = utils.getCodedValues(null, SRTSQLQuery.REGIONSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public String getProgramAreaCd(String code) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      String programAreaCd = util.getProgramAreaCd(code,
	          SRTSQLQuery.PROGRAMAREASQL);
	
	      return programAreaCd;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getProgramAreaConditions(String programAreas) throws
       RemoteException, Exception {
	   try{
	      TreeMap<Object,Object> programAreaConditionsTreeMap = this.getProgramAreaConditions(
	          programAreas,1);
	      return programAreaConditionsTreeMap;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }
   public TreeMap<Object,Object> getProgramAreaConditionWOIndentLevel(String programAreas) throws
   RemoteException, Exception {

	   try{
		  TreeMap<Object,Object> programAreaConditionsTreeMap = this.getProgramAreaConditions(
		      programAreas);
		  return programAreaConditionsTreeMap;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public ProgramAreaVO getProgramAreaForCondition(String conditionCode) throws
	RemoteException, Exception {
	try{
		SRTMapDAOImpl util = new SRTMapDAOImpl();
		ProgramAreaVO programAreaVO= util.getProgramAreaForCondition(
				conditionCode,  SRTSQLQuery.PROGRAMAREACONDITIONSSQLWOINDENT);
		
		return programAreaVO;
	}catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
	}

   public TreeMap<Object,Object> getProgramAreaConditions(String programAreas,
                                           int indentLevelNbr) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> programAreaConditionsTreeMap = util.getProgramAreaConditions(
	          programAreas, indentLevelNbr, SRTSQLQuery.PROGRAMAREACONDITIONSSQL);
	
	      return programAreaConditionsTreeMap;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /**
    * this methods gets only active Condtions for the given programAreas
    * @param programAreas : String
    * @return : TreeMap<Object,Object>
    * @throws RemoteException
    * @throws Exception
    */

   public TreeMap<Object,Object> getActiveProgramAreaConditions(String programAreas) throws
       RemoteException, Exception {
	   try{
	      TreeMap<Object,Object> programAreaConditionsTreeMap = this.getActiveProgramAreaConditions(
	          programAreas,1);
	      return programAreaConditionsTreeMap;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public TreeMap<Object,Object> getActiveSummaryReportConditionCode(String code) 
	   throws RemoteException, Exception{
	   try{
		  SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      map = util.getActiveSDataCodeValues(code);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }


   /**
    * this methods gets only active Condtions for the given programAreas and indent level
    * @param programAreas : String
    * @param indentLevelNbr : int
    * @return : TreeMap<Object,Object>
    * @throws RemoteException
    * @throws Exception
    */


   public TreeMap<Object,Object> getActiveProgramAreaConditions(String programAreas,
                                           int indentLevelNbr) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> programAreaConditionsTreeMap = util.getProgramAreaConditions(
	          programAreas, indentLevelNbr, SRTSQLQuery.ACTIVE_PROGRAMAREA_CONDITIONS_SQL);
	
	      return programAreaConditionsTreeMap;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }


   /**
    * @method   :   getLabTestCodes
    * @return   :   TreeMap<Object,Object>
    */
   public TreeMap<Object,Object> getLabTestCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getLabTestCodes(SRTSQLQuery.LAB_TEST_SQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /**
    * @method   :   getSuspTestCodes
    * @return   :   TreeMap<Object,Object>
    */
   public TreeMap<Object,Object> getSuspTestCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getSuspTestCodes(SRTSQLQuery.SUSP_TEST_SQL);
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getProgramAreaCodedValue() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getProgramAreaCodedValue(SRTSQLQuery.
	          PROGRAM_AREA_LIST_SQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getAllCountryCodes() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> allCountryCodes = util.getAllCountryCodes(SRTSQLQuery.
	          COUNTRY_CODE_SQL);
	      return allCountryCodes;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   public  ArrayList<Object> getAllCountryCodesOrderByShortDesc() throws RemoteException, Exception {
	   	
	   	try{
		      SRTMapDAOImpl util = new SRTMapDAOImpl();
		      ArrayList<Object>  list = null;

		      //  logic to check if code has seperate table
		      list = util.getCodedValuesList(null, SRTSQLQuery.
		   	          COUNTRY_CODE_SQL);
		      
		      return list;
		      
	   	}catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
	      
	    

   //------------for security--------------------
   public TreeMap<Object,Object> getProgramAreaCodedValues() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getProgramAreaCodedValues();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   public TreeMap<Object,Object> getCodedResultValue() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedResultValue();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   public TreeMap<Object,Object> getCodedResultValueSusc() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedResultSuscValue();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   
   
   public TreeMap<Object,Object> getResultMethodValueSusc() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getResultMethodSuscValue();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   
   
   
   public ArrayList<Object> getCodedResultValueList() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object> codeList = util.getCodedResultValueList();
	
	      return codeList;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }


   public TreeMap<Object,Object> getProgramAreaNumericIDs() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getProgramAreaNumericIDs();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getJurisdictionCodedValues() throws RemoteException,
   Exception {
	   try{
		  SRTMapDAOImpl util = new SRTMapDAOImpl();
		  TreeMap<Object,Object> map = util.getJurisdictionCodedValues();
		
		  return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
	}
   public TreeMap<Object,Object> getJurisdictionNoExpCodedValues() throws RemoteException,
   Exception {
	   try{
		  SRTMapDAOImpl util = new SRTMapDAOImpl();
		  TreeMap<Object,Object> map = util.getJurisdictionCodedNoExpValues();
		
		  return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
 public Collection<Object> getExportJurisdictionCodedValues() throws RemoteException,
   Exception {
	 try{
		  SRTMapDAOImpl util = new SRTMapDAOImpl();
		  Collection<Object> coll = util.getExportJurisdictionCodedValues();
		
		  return coll;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
 }

   public TreeMap<Object,Object> getJurisdictionNumericIDs() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getJurisdictionNumericIDs();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   //------------------------------------
   public String getCodeDescTxt(String code) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      String codeDescTxt = util.getCodeDescTxt(code,
	                                               SRTSQLQuery.CODE_DESC_TXT_SQL);
	
	      return codeDescTxt;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getConditionCode() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String tempSQL = null;
	      
	         tempSQL = SRTSQLQuery.CONDTION_CODE_SQL +
	                   "WHERE condition_codeset_nm = 'PHC_TYPE'";
	         map = util.getCodedValues(null, tempSQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   /* To get the description of Resulted test from LOINC table */
   public TreeMap<Object,Object> getResultedTest() throws RemoteException, Exception {
	   try{
	       SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	       //  logic to check if code has seperate table
	       TreeMap<Object,Object> map = null;
	       String tempSQL = null;
	   
	       tempSQL = SRTSQLQuery.RESULTDTESTDESCSQL;
	       map = util.getCodedValuesResultedTest(null, tempSQL);
	       
	       return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
    }
    /* To get the description of Resulted test from LAB_TEST table */
 public TreeMap<Object,Object> getResultedTestLab(String labId,String testCd) throws RemoteException, Exception {
	 try{
	     SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	     //  logic to check if code has seperate table
	     TreeMap<Object,Object> map = null;
	     String tempSQL = null;
	     String whereClause = " WHERE lab_test_cd = '"+ testCd + "' and laboratory_id = '"+labId+ "'" ;
	    
	     tempSQL = SRTSQLQuery.RESULTDTESTDESCLABSQL+whereClause;
	     map = util.getCodedValuesResultedTest(null, tempSQL);
	     
	     return map;
	 }catch(RemoteException ex){
		 logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		 throw new RemoteException(ex.toString(),ex);
	 }catch(Exception ex){
		 logger.fatal("Exception  = "+ex.getMessage(), ex);
		 throw new Exception(ex.toString(),ex);
	 }
  }


   /**
    * retrieves the condition codes for diagnosis
    */
   public TreeMap<Object,Object> getDiagnosisCode() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String tempSQL = null;
	     
	      tempSQL = SRTSQLQuery.CONDTION_CODE_SQL +
	                   "WHERE parent_is_cd = 999999";
	         map = util.getCodedValues(null, tempSQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   
   public TreeMap<Object,Object> getDiagnosisCodeAltValue() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();

	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String tempSQL = null;
	     
	      
	     tempSQL = SRTSQLQuery.CODEQUERYSQL ;
	     map = util.getCodedAltValues(null, tempSQL);
	      
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
   
   /**
    * retrieves the condition codes for diagnosis
    */
   public TreeMap<Object,Object> getDiagnosisCodeFilteredOnPA(String programAreas) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String tempSQL = null;
	      
	         tempSQL = SRTSQLQuery.CONDTION_CODE_SQL +
	                   "WHERE parent_is_cd = 999999 AND prog_area_cd IN " + programAreas;
	         map = util.getCodedValues(null, tempSQL);

	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /* get language code from seperate table */
   public TreeMap<Object,Object> getLanguageCode() throws RemoteException, Exception {
	   try{
	      String tempLanguageSQL = null;
	      tempLanguageSQL = SRTSQLQuery.LANGUAGESQL; //SQLServer
	      
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempLanguageSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /* get Industry code from seperate table */
   public TreeMap<Object,Object> getIndustryCode() throws RemoteException, Exception {
	   try{
	      String tempIndustryCodeeSQL = null;
	
	         tempIndustryCodeeSQL = SRTSQLQuery.INDUSTRYCODEESQL; //SQLServer
	
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempIndustryCodeeSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /* get Occupation code from seperate table */
   public TreeMap<Object,Object> getOccupationCode() throws RemoteException, Exception {
	   try{
	      String tempOccupationCodeSQL = null;
	
	      tempOccupationCodeSQL = SRTSQLQuery.OCCUPATIONCODEESQL; //SQLServer
	      
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempOccupationCodeSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /* get NAICS Industry code from seperate table */
   public TreeMap<Object,Object> NAICSgetIndustryCode() throws RemoteException, Exception {
	   try{
	      String tempNAICSIndustryCodeeSQL = null;
	
	 
	      tempNAICSIndustryCodeeSQL = SRTSQLQuery.NAICSINDUSTRYCODEESQL; //SQLServer
	      
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempNAICSIndustryCodeeSQL);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

	public TreeMap<Object, Object> getSTDHIVWorkers() throws RemoteException,
			Exception {
		try {
			String STDHIVWorkersSQL = null;

			STDHIVWorkersSQL = SRTSQLQuery.CASEWORKERSSQL; // SQLServer
			String delim = ",";
			StringBuffer stringBuffer = new StringBuffer("");
			String line = propUtil.getSTDProgramAreas() + ","
					+ propUtil.getHIVProgramAreas();
			StringTokenizer tokens = new StringTokenizer(line, delim, false);
			stringBuffer.append("(");
			while (tokens.hasMoreTokens()) {
				stringBuffer.append("'")
						.append(tokens.nextToken().toUpperCase().trim())
						.append("'").append(delim);
			}
			String PAs = stringBuffer.substring(0, stringBuffer.length()-1)+")";

			SRTMapDAOImpl util = new SRTMapDAOImpl();
			TreeMap<Object, Object> map = util.getCodedValues(null,	STDHIVWorkersSQL + PAs);
			
			return map;
		} catch (RemoteException ex) {
			logger.fatal("RemoteException  = " + ex.getMessage(), ex);
			throw new RemoteException(ex.toString(), ex);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new Exception(ex.toString(), ex);
		}
	}

   public String getCurrentSex(String currentSex) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl srtMap = new SRTMapDAOImpl();
	      String currSex = srtMap.getCurrentSex(currentSex);
	      return currSex;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public int getCountOfCdNotNull(String cityCD) throws RemoteException,
       Exception {
	   try{
	      SRTMapDAOImpl srtMap = new SRTMapDAOImpl();
	      int count = srtMap.getCountOfCdNotNull(cityCD);
	      return count;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public String getJurisditionCD(String zipCD, String typeCD) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl srtMap = new SRTMapDAOImpl();
	      String jurisdiction = srtMap.getJurisditionCD(zipCD, typeCD);
	      return jurisdiction;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getBMDConditionCode() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String code = null;
	
	      code = "BM_SPEC_ISOL";
	      map = util.getBMDCodeValues(code);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getSummaryReportConditionCode(String InConditionForProgArea) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String code = null;
	      map = util.getSDataCodeValues(InConditionForProgArea);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getSummaryReportConditionCodeProgAreCd(String
       InConditionForProgArea) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String code = null;
	      map = util.getCountyCodeProgAreaCd(InConditionForProgArea);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   public TreeMap<Object,Object> getSRCountyConditionCode(String countyCd) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      String code = null;
	      map = util.getSDCodeValues(countyCd);
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

   /* code added to support LDFs*/

   public TreeMap<Object,Object> getLDFDropdowns() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = null;
	      map = util.getLdfRules();
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public HashMap<Object, Object> getLDFValues(String ldfType) throws RemoteException, Exception {
	   try{
	     SRTMapDAOImpl util = new SRTMapDAOImpl();
	     HashMap<Object, Object> map = null;
	     map = util.getLdfRules(ldfType);
	     return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

  }

   public ArrayList<Object>  getLDFPages() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = null;
	      list = util.getLdfPages();
	      return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public ArrayList<Object>  getLDFPageIDs() throws RemoteException, Exception {
	   try{
		   SRTMapDAOImpl util = new SRTMapDAOImpl();
		   ArrayList<Object>  list = null;
		   list = util.getLdfPageIDs();
		   return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }
   public ArrayList<Object>  getLDFPageIDsHome() throws RemoteException, Exception {
	   try{
		   SRTMapDAOImpl util = new SRTMapDAOImpl();
		   ArrayList<Object>  list = null;
		   list = util.getLdfPageIDsHome();
		   return list;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }
   public TreeMap<Object,Object> getOrderedTestAndResultValues(String code) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getOrderedAndResultTest(code);
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public TreeMap<Object,Object> getDrugNames() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getDrugNames();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public TreeMap<Object,Object> getTreatmentDesc() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getTreamentDesc();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public TreeMap<Object,Object> getTreamentDrug() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getTreamentDrug();
	
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public PreDefinedTreatmentDT getTreamentPreDefined(String pTreatmentCd) throws
       RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      PreDefinedTreatmentDT preDefinedTreatmentDT = util.
	          getTreatmentPreDefinedFields(pTreatmentCd);
	
	      return preDefinedTreatmentDT;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }
   public TreeMap<Object,Object> getRaceCodes() throws RemoteException,
       Exception {
      logger.debug("Inside getRaceCodes ");
      try{
	      String tempRACESQL = null;
	      String whereClause = " where status_cd='A'";
	
	      //  logic to check if code has seperate table
	      
	     tempRACESQL = SRTSQLQuery.RACESQL + whereClause;
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getCodedValues(null, tempRACESQL);
	
	      return map;
      }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	  }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	  }
   }
   /* The method is used to get both Race code and category in one object
      It then converts it in to HashMap<Object,Object> with key as race description and
      value as object having race code and race category code.
      At present used by Data migration */

   public TreeMap<Object,Object> getRaceCodesAndCategory() throws RemoteException,
       Exception {
	   try{
	      String tempRACESQL = null;
	      tempRACESQL = SRTSQLQuery.RACECODEANDCATEGORYSQL;
	      
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = (ArrayList<Object> )util.getCodedValuesColl(null, tempRACESQL);
	      TreeMap<Object,Object> map = new TreeMap<Object,Object>();
	      if (list != null){
	        for (int count =0; count < list.size(); count++){
	          DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	          PersonRaceVO raceVO = new PersonRaceVO();
	          raceVO.setRaceCd(dt.getKey());
	          raceVO.setRaceCategoryCd(dt.getValue());
	          if (raceVO.getRaceCategoryCd().equalsIgnoreCase("ROOT")){
	            raceVO.setRaceCategoryCd(raceVO.getRaceCd());
	          }
	          map.put(dt.getKey(),raceVO);
	        }
	      }
	      return map;
	   }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }

 public TreeMap<Object,Object> getEthnicityAndGroup() throws RemoteException,Exception{
	 try{
	   String tempEthnicitySQL = null;
	   tempEthnicitySQL = SRTSQLQuery.ETHNICITYCODEANDGROUOPSQL;
	   
	   SRTMapDAOImpl util = new SRTMapDAOImpl();
	   ArrayList<Object>  list = (ArrayList<Object> )util.getCodedValuesColl(null, tempEthnicitySQL);
	   TreeMap<Object,Object> map = new TreeMap<Object,Object>();
	   if (list != null){
	     for (int count =0; count < list.size(); count++){
	       DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
	       PersonEthnicityVO ethnicityVO = new PersonEthnicityVO();
	       ethnicityVO.setEthnicGroupDescTxt(dt.getKey());
	       ethnicityVO.setEthnicGroupCd(dt.getValue());
	       ethnicityVO.setEthnicGroupInd(dt.getAltValue());
	     /*  if (ethnicityVO.getEthnicGroupInd().equals(ethnicityVO.getEthnicGroupCd())){
	         ethnicityVO.setEthnicGroupCd(null);
	       }*/
	       map.put(dt.getKey(),ethnicityVO);
	     }
	   }
	   return map;
	 }catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

 }
   public TreeMap<Object,Object> getOrganismList() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getOrganismList();
	
	      return map;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }

   }

   public TreeMap<Object,Object> getOrganismListSNM() throws RemoteException, Exception {
	   try{
	     SRTMapDAOImpl util = new SRTMapDAOImpl();
	     TreeMap<Object,Object> map = util.getOrganismListSNM();
	
	     return map;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	   }
   }
    public TreeMap<Object,Object> getStateCodeList() throws RemoteException, Exception{
    	try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      TreeMap<Object,Object> map = util.getStateCodeDescTxt();
	
	      return map;
    	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	   }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	   }
    }

    public TreeMap<Object,Object> getCountyShortDescTxt() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	        return util.getAllCountyNamesAndAssociatedStateCodesTogether();
    	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
    }

    public TreeMap<Object,Object> getConditionCdAndProgramAreaCd() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	        return util.getConditionCodesAndProgramAreaCodes();
    	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }

    }

    public TreeMap<Object,Object> getConditionCdAndInvFormCd() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	        return util.getConditionCodesAndInvFormCodes();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }

    }
    
    public ArrayList<Object>  getConditionFamilyList(String conditionCd) throws RemoteException, Exception 
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	        return util.getConditionCodeFamilyList(conditionCd);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }

    }
    public TreeMap<Object,Object> getACountysReportingSources (String countyCd) throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getACountysReportingSources(countyCd);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }

    public TreeMap<Object,Object> getAllCodedValuesForBMD120AndBMD121() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCodeShortDescsForBMD120AndBMD121();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }

    public TreeMap<Object,Object> getAllCodedValuesForVacNm() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCodedValuesForVacNm();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }

    public TreeMap<Object,Object> getStateDefinedSRTCodeSetName() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getStateDefinedSRTCodeSetName();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public TreeMap<Object,Object> getSAICDefinedCodedValues(String type) throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getSAICDefinedSRTCodesForCodeSetName(type);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }


    public TreeMap<Object,Object> getAllCodesForBMD120AndBMD121() throws RemoteException, Exception
    {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCodedsForBMD120AndBMD121();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }

    /**
     * Description for the selected Lab Test Cd 
     * @param code
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String getLabTestDesc(String code) throws RemoteException, Exception {
        try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	
	        //  logic to check if code has seperate table
	        TreeMap<Object,Object> map = null;
	        String tempSQL = null;
	        String whereClause = " WHERE lab_test_cd = ?";
	        String desc = "";
	        tempSQL = SRTSQLQuery.LAB_TEST_DESC_SQL + whereClause;
	           desc = util.getLabTestDescForCode(tempSQL,code);
	        
	        return desc;
        }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    
    /**
     * This method loads all the available ColumnTypes and corresponding rOperators for Reporting Subsystem(Advanced Filter)
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String getAllFilterOperators() throws RemoteException, Exception {
    	try{
	        SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllFilterOperators();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    
    public ArrayList<Object>  getAllCityCodes(String stateCd) throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCityCodes(stateCd);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    /**
     * This method loads all the available Provider specific QuickEntryCodes 
     * @return java.util.ArrayList<Object> 
     * @throws RemoteException
     * @throws Exception
     */
    public ArrayList<Object>  getAllQECodes(String Type) throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllQECodes(Type);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }

    public ArrayList<Object>  getlaboratoryIds() throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getLaboratoryIds();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getAllConditionCodes() throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllConditionCodes();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getAllCodeSetNms() throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCodeSetNms();
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getAllCodeSystemCdDescs(String codeSetNm) throws RemoteException, Exception {
    	try{
	    	SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllCodeSystemCdDescs(codeSetNm);
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getNedssUserlist() throws RemoteException, Exception {
    	try{
	    	  logger.debug("Entered into the getEmailUsers()of SRTMAPEJB");
	    	  UserProfileDAO util = new UserProfileDAO();
		      ArrayList<Object>  list = util.list();
		      return list;
    	}catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    
    public ArrayList<Object>  getUsersWithValidEmailLst() throws RemoteException, Exception {
    	try{
	   	  logger.debug("Entered into the getUsersWithValidEmailLst()of SRTMAPEJB");
	   	  UserProfileDAO util = new UserProfileDAO();
	      ArrayList<Object>  list = util.getUsersWithValidEmailLst();

	      return list;
    	}catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getUsersWithActiveAlerts() throws RemoteException, Exception {
    	try{
	   	  logger.debug("Entered into the getUsersWithActiveAlerts()of SRTMAPEJB");
	   	  UserProfileDAO util = new UserProfileDAO();
	      ArrayList<Object>  list = util.getUsersWithActiveAlerts();

	      return list;
    	}catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    
    /**
     * This method returns a list of DropDownCodeDT with desc from code_short_desc_txt (Standard way)
     * and code from code_desc_txt (Special way as column 'cd' cannot accomodate lengthy values) 
     * @param codeSetNm
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public ArrayList<Object>  getSRTAdminCodedValue(String codeSetNm) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getSRTAdminCodedValuesList(codeSetNm);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
	}
    public ArrayList<Object>  getCodingSystemCodes(String assignAuth) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getCodingSystemCodes(assignAuth);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getLdfHtmlTypes(String formCd) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getLdfHtmlTypes(formCd);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getAvailableTabs(String ldfPageId) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getAvailableTabs(ldfPageId);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getLDFSections(Long tabId) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getLDFSections(tabId);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getLDFSubSections(Long sectionId) throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getLDFSubSections(sectionId);
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    
    public ArrayList<Object>  getCodesetNames() throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getCodesetNames();
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public TreeMap<Object,Object> getInvFrmCdLdfPgIdMap() throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			TreeMap<Object,Object> map = util.getInvFrmCdLdfPgIdMap();
			return map;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
    public ArrayList<Object>  getCodedValueOrderdByNbsUid(String code) throws RemoteException, Exception {
    	try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = util.getCodedValueOrderdByNbsUid(code);

	      return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }    
    
    /**
     * getIsoCountyList returns ISO Country List
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public ArrayList<Object>  getIsoCountryList() throws RemoteException, Exception {
    	try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object>  list = util.getIsoCountryList();
			return list;
    	}catch(RemoteException ex){
  		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
  		   throw new RemoteException(ex.toString(),ex);
  	    }catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }
  
    public ArrayList<Object>  getExportReceivingFacilityList() throws RemoteException, Exception {
    	try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();
	      ArrayList<Object>  list = util.getExportReceivingFacilityList();

	      return list;
    	}catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new Exception(ex.toString(),ex);
  	    }
    }  
    
   public SRTMapEJB() {
   }

   
   public void ejbCreate() {
   }

   public void ejbRemove() {
   }

   public void ejbActivate() {
   }

   public void ejbPassivate() {
   }

   public void setSessionContext(SessionContext sc) {
   }
   
   public TreeMap<Object,Object> getRaceCategory() throws RemoteException,
   Exception {
	   try{
		    String RACECATEGORYORACLE = "select parent_is_cd \"value\",  "+
	       " code \"key\"  "+
	       " from "+
	       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	       ".race_code where status_cd= 'A' and indent_level_nbr in  (1 ,2) order by parent_is_cd ";
	    
	    	String RACECATEGORYSQL = "select parent_is_cd \"value\",  "+
	       " code \"key\"  "+
	       " from "+
	       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	       "..race_code where status_cd= 'A' and  indent_level_nbr in  (1 ,2) order by parent_is_cd ";
	    
		  String tempRACESQL = null;
		  tempRACESQL = RACECATEGORYSQL;
		  
		  SRTMapDAOImpl util = new SRTMapDAOImpl();
		  ArrayList<Object>  list = (ArrayList<Object> )util.getCodedValuesColl(null, tempRACESQL);
		  TreeMap<Object,Object> map = new TreeMap<Object,Object>();
		  if (list != null){
		    for (int count =0; count < list.size(); count++){
		      DropDownCodeDT dt = (DropDownCodeDT) list.get(count);
		      map.put(dt.getValue(),dt);
		    }
		  }
		  return map;
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
}
   
 //------------------------------------
   public String getCodeShortDescTxt(String code, String codeSetNm) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl srtDao = new SRTMapDAOImpl();
	      String codeDescTxt = srtDao.getCodeShortDescTxt(code,codeSetNm,
	                                               SRTSQLQuery.CODE_DESC_SHORT_TXT_SQL);
	
	      return codeDescTxt;
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	   }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	   }
   }
   
   /**
    * Returns Counties for a give State, with a STATEWIDE value too
    * @param state
    * @return
    * @throws RemoteException
    * @throws Exception
    */
   public TreeMap<Object,Object> getCountyCodesInclStateWide(String state) throws RemoteException, Exception {
	   try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			String whereClause = " and parent_is_cd = '" + state + "' or parent_is_cd = '9999' order by parent_is_cd, code_desc_txt";
			String tempCOUNTYSQL = null;
			TreeMap<Object,Object> map = null;
	
			tempCOUNTYSQL = SRTSQLQuery.COUNTYSQL + whereClause;
				map = util.getCodedValues(null, tempCOUNTYSQL);
			
			map.put("", ""); // an empty record
			return map;
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}

   /**
    * 
    * @param code
    * @return
    * @throws RemoteException
    * @throws Exception
    */
   public ArrayList<Object>  getAggregateSummaryReportConditionCode(String code)
			throws RemoteException, Exception {
	   try{
		   	SRTMapDAOImpl util = new SRTMapDAOImpl();
		    return util.getActiveAggregateSDataCodeValues(code);
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	   }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	   }
	}
   
   /**
    * 
    * @param code
    * @return
    * @throws RemoteException
    * @throws Exception
    */
   public TreeMap<Object,Object> getConditionTracingEnableInd() throws RemoteException, Exception {
	   try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
		    return util.getConditionTracingEnableInd();
	   }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	   }
   }
   public ArrayList<Object>  getAllActiveCodeSetNms() throws RemoteException, Exception {
	   try{
	   	   SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getAllActiveCodeSetNms();
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getNbsUnitsType() throws RemoteException, Exception {
	   	try{
		    SRTMapDAOImpl util = new SRTMapDAOImpl();
		    return util.getNbsUnitsType();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
    }
   
   public ArrayList<Object>  getDefaultDisplayControl(String code) throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getDefaultDisplayControl(code);
	   }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   public ArrayList<Object>  getDefaultDisplayControlDesc() throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getDefaultDisplayControlDesc(null);
	    }catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   } 
   
   public ArrayList<Object>  getAllActiveTemplates(String busObjectType) throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getAllActiveTemplates(busObjectType);
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getAllActiveCodeSetNmsByGroupId() throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	        return util.getAllActiveCodeSetNmsByGroupId();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public  ArrayList<Object> getFilteredConditionCode() throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();

	      //  logic to check if code has seperate table
	      TreeMap<Object,Object> map = null;
	      ArrayList<Object> list =  new ArrayList<Object>();
	      String tempSQL = null;
	      

	      tempSQL = "SELECT condition_cd \"key\", " + " condition_short_nm \"value\" FROM " +
  			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code "+
            "WHERE condition_cd NOT IN  (select condition_cd from nbs_odse..page_cond_mapping " +
            "WHERE condition_cd IS NOT NULL) and status_cd='A' and condition_cd not in ('10220','10030') order by condition_short_nm";
	         list = util.getFilteredConditionCode(tempSQL);
	      
	      return list;
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getvalueSetTypeCdNoSystemStrd() throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	   		return util.getvalueSetTypeCdNoSystemStrd();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
  
   public TreeMap<Object,Object>  getStandredConceptCVGList() throws RemoteException,Exception{
	   try{
		   SRTMapDAOImpl util = new SRTMapDAOImpl();

	      TreeMap<Object,Object> map = null;
	      String tempSQL = null;

	     tempSQL = "SELECT code_set_nm \"codeSetNm\", " + " code \"code\", " + "code_desc_txt \"codeDescTxt\", " + 
			       	  "code_short_desc_txt \"codeShortDescTxt\", " + "code_system_cd \"codeSystemCd\", " +
			    	  "code_system_desc_txt \"codeSystemDescTxt\", " + "concept_code \"conceptCode\", " +
			    	  "concept_nm \"conceptNm\", " + "concept_preferred_nm \"conceptPreferredNm\", " +
			    	  "nbs_uid \"nbsUid\" " + " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general WHERE code_set_nm='CODE_SYSTEM'";
	         map = util.getStandredConceptCVGList(null, tempSQL);
	     
	      return map;
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   public  ArrayList<Object> getAvailableConditionCode(String busObjType) throws RemoteException, Exception {
	   try{
	      SRTMapDAOImpl util = new SRTMapDAOImpl();

	      //  logic to check if code has separate table
	      TreeMap<Object,Object> map = null;
	      ArrayList<Object> list =  new ArrayList<Object>();
	      String tempSQL = null;
	      String filterPBpages ="";
	      String filterTBVar="";
	      if(busObjType!=null && busObjType.equals("INV"))
	    	  filterTBVar=" and condition_cd not in ('10220','10030')";
	      if(busObjType.equals("IXS"))
	    		  filterPBpages=  " and investigation_form_cd like 'PG_%' ";
	      tempSQL = 	"SELECT condition_cd \"key\", " + " condition_short_nm \"value\" FROM " +
	        		 	NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code "+
						"WHERE condition_cd NOT IN  (select pcm.condition_cd from nbs_odse..page_cond_mapping pcm, nbs_odse..wa_template wt WHERE pcm.wa_template_uid = wt.wa_template_uid and wt.bus_obj_type='"+busObjType+"' and pcm.condition_cd IS NOT NULL) " +
						filterTBVar + filterPBpages +
						"order by condition_short_nm";
	         list = util.getFilteredConditionCode(tempSQL);
	      return list;
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getConditionWithNoPortReqInd() throws RemoteException, Exception {
	   try{
		   SRTMapDAOImpl util = new SRTMapDAOImpl();
		   return util.getConditionWithNoPortReqInd();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getSendingSystemList(String systemType) throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getSendingSysList(systemType);
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   public ArrayList<Object>  getInvestigationTypeRelatedPage() throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getInvestigationTypeRelatedPage();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   public ArrayList<Object>  getConditionDropDown(String relatedPage) throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getConditionDropDown(relatedPage);
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getConditionDropDown(Long waTemplateUid) throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getConditionDropDown(waTemplateUid);
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   
   public ArrayList<Object>  getPublishedConditionDropDown() throws RemoteException, Exception {
	   try{
	   		SRTMapDAOImpl util = new SRTMapDAOImpl();
	       return util.getPublishedConditionDropDown();
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   /*
    * Get the list of Participation Types for the specified Act Class Cd i.e.InvestgrOfPHC, HospOfADT
    * @param actClassCode i.e. CASE
    * @return ParticipationTypeVO ArrayList
    */
   public ArrayList<Object>  getParticipationTypes(String actClassCode) throws
   RemoteException, Exception {
	   try{
		   SRTMapDAOImpl util = new SRTMapDAOImpl();
		   ArrayList<Object>   participationTypesList = util.getTheParticipationTypes(
				   actClassCode, SRTSQLQuery.PARTICIPATIONTYPESSQL);
	
		   return participationTypesList;
	   	}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
   }
   /*
    * Get the list of XSS Filter Patters for the 
    * @return xss filter pattern ArrayList
    */
	public ArrayList<Object> getXSSFilterPatterns() throws RemoteException,
			Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object> list = util.getXSSFilterPatterns();
			return list;
		}catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}

	public Map<Object, Object> getConditionCoinfectionMap()
			throws RemoteException, Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			Map<Object, Object> conditionCoinfectionMap = util
					.getCoinfectionCondition();
			return conditionCoinfectionMap;
		}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}
	
	public ArrayList<Object> getPlaceList() throws RemoteException,
		Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			ArrayList<Object> list = util.getPlaceList();
			return list;
		}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}
	
	public TreeMap<Object, Object> getPlaceMap() throws RemoteException,
	Exception {
	try{
		SRTMapDAOImpl util = new SRTMapDAOImpl();
		TreeMap<Object, Object> map = util.getPlaceMap();
		return map;
	}catch(RemoteException ex){
		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
		   throw new RemoteException(ex.toString(),ex);
	    }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new Exception(ex.toString(),ex);
	    }
}
	
	public Map<String, String> getNBSCodeFromPHINCodes() throws RemoteException, Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			Map<String, String> map = util.getNBSCodeFromPHINCodes();
			return map;
		}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}
	
	public CodeLookupDT getLabCodeSystem(String labCode, String laboratoryID) throws RemoteException, Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			return util.getLabCodeSystem(labCode, laboratoryID);
		}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}
	
	public Map<String, String> getAOELOINCCodes() throws RemoteException, Exception {
		try{
			SRTMapDAOImpl util = new SRTMapDAOImpl();
			Map<String, String> map = util.getAOELOINCCodes();
			return map;
		}catch(RemoteException ex){
 		   logger.fatal("RemoteException  = "+ex.getMessage(), ex);
 		   throw new RemoteException(ex.toString(),ex);
 	    }catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new Exception(ex.toString(),ex);
 	    }
	}
	
	public static String findToCode(String fromCodeSetNm, String fromCode, String toCodeSetNm)
			throws RemoteException, Exception {
		try {
			ELRXRefDAOImpl util = new ELRXRefDAOImpl();
			String toCode = util.findToCode(fromCodeSetNm, fromCode, toCodeSetNm);
			return toCode;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new Exception(ex.toString(), ex);
		}
	}
	
}
