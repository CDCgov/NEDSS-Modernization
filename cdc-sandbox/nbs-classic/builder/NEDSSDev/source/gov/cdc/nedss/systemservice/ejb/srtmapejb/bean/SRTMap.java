package gov.cdc.nedss.systemservice.ejb.srtmapejb.bean;

import java.rmi.*;
/**
 * Title: SRTMap.
 * Description: This class is the Remote interface for SRTMap session bean.
 * Copyright:    Copyright (c) 2001
 * Company:csc
 * @author: Roger Wilson
 */
import java.util.*;

import javax.ejb.*;

import gov.cdc.nedss.nnd.dt.CodeLookupDT;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.*;

public interface SRTMap
    extends EJBObject {


   public TreeMap<Object, Object> getCodedValues(String code) throws RemoteException, Exception;
   public ArrayList<Object>  getCodedValuesList(String code) throws RemoteException, Exception;

   public TreeMap<Object, Object> getCityCodes(String state) throws RemoteException, Exception;

   public TreeMap<Object,Object> getStateCodes(String country) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getStateCodes1(String country) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getStateCodesAndAbbreviations(String country) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getCountyCodes(String state) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getCountyCodes() throws RemoteException, Exception;
   
   public String getCountyDesc(String code) throws RemoteException, Exception;

   public TreeMap<Object,Object> getCountryCodes() throws RemoteException, Exception;

   public TreeMap<Object,Object> getRaceCodesAndCategory() throws RemoteException,Exception;

   public TreeMap<Object,Object> getEthnicityAndGroup() throws RemoteException,Exception;
   /**
    *
    * @param superCode
    * @return
    * @throws RemoteException
    * @throws Exception
    * this method return shor description txt of country
    */
   public TreeMap<Object,Object> getCountryShortDescTxt() throws RemoteException, Exception;

   public TreeMap<Object,Object> getRaceCodes(String superCode) throws RemoteException,
       Exception;
   public TreeMap<Object,Object> getRaceCodesByCodeSet(String codeSetName) throws RemoteException,
       Exception;
   public TreeMap<Object,Object> getRaceCodes() throws RemoteException, Exception;

   public TreeMap<Object,Object> getAddressType() throws RemoteException, Exception;

   public TreeMap<Object,Object> getAddressUse() throws RemoteException, Exception;

   public TreeMap<Object,Object> getRegionCodes() throws RemoteException, Exception;

   public String getProgramAreaCd(String code) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getProgramAreaConditions(String code) throws RemoteException,
       Exception;
   public ProgramAreaVO  getProgramAreaForCondition(String conditionCode) throws RemoteException,
   Exception;
   
   public ArrayList<Object>  getConditionFamilyList(String conditionCode) throws RemoteException,
   Exception;

   public TreeMap<Object,Object> getProgramAreaConditions(String programAreas,
                                           int indentLevelNbr) throws
       RemoteException, Exception;
   public TreeMap<Object,Object> getActiveProgramAreaConditions(String code) throws RemoteException,
       Exception;

   public TreeMap<Object,Object> getActiveProgramAreaConditions(String programAreas,
                                           int indentLevelNbr) throws
       RemoteException, Exception;

   public TreeMap<Object,Object> getProgramAreaCodedValue() throws RemoteException, Exception;

   public TreeMap<Object,Object> getAllCountryCodes() throws RemoteException, Exception;
   
   public ArrayList<Object> getAllCountryCodesOrderByShortDesc() throws RemoteException, Exception;

   // for build d security
   public TreeMap<Object,Object> getProgramAreaCodedValues() throws RemoteException, Exception;

   public TreeMap<Object,Object> getCodedResultValue() throws RemoteException, Exception;
   public TreeMap<Object,Object> getCodedResultValueSusc() throws RemoteException, Exception;
   public TreeMap<Object,Object> getResultMethodValueSusc() throws RemoteException, Exception;
   
   
   
   public ArrayList<Object> getCodedResultValueList() throws RemoteException, Exception;

   public TreeMap<Object,Object> getProgramAreaNumericIDs() throws RemoteException, Exception;

   public TreeMap<Object,Object> getJurisdictionCodedValues() throws RemoteException,
   Exception;
   public TreeMap<Object,Object> getJurisdictionNoExpCodedValues() throws RemoteException,
   Exception;
   public Collection<Object> getExportJurisdictionCodedValues() throws RemoteException,
   Exception;
   public TreeMap<Object,Object> getJurisdictionNumericIDs() throws RemoteException, Exception;

   //------------------
   public String getCodeDescTxt(String code) throws RemoteException, Exception;

   public TreeMap<Object,Object> getConditionCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> getDiagnosisCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> getDiagnosisCodeAltValue() throws RemoteException, Exception;
   
   public TreeMap<Object,Object> getDiagnosisCodeFilteredOnPA(String programAreas) throws RemoteException, Exception;

   public TreeMap<Object,Object> getLanguageCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> getIndustryCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> getOccupationCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> NAICSgetIndustryCode() throws RemoteException, Exception;
   
   public TreeMap<Object, Object> getSTDHIVWorkers() throws RemoteException,	Exception;

   public TreeMap<Object,Object> getLabTestCodes() throws RemoteException, Exception;

   public TreeMap<Object,Object> getSuspTestCodes() throws RemoteException, Exception;

   public String getCurrentSex(String currentSex) throws RemoteException,
       Exception;

   public int getCountOfCdNotNull(String cityCD) throws RemoteException,
       Exception;

   public String getJurisditionCD(String zipCD, String typeCD) throws
       RemoteException, Exception;

   public TreeMap<Object,Object> getBMDConditionCode() throws RemoteException, Exception;

   public TreeMap<Object,Object> getSRCountyConditionCode(String countyCd) throws
       RemoteException, Exception;

   public TreeMap<Object,Object> getSummaryReportConditionCode(String InConditionForProgArea) throws
       RemoteException, Exception;

   public TreeMap<Object,Object> getActiveSummaryReportConditionCode(String code) 
	   throws RemoteException, Exception;

   public TreeMap<Object,Object> getSummaryReportConditionCodeProgAreCd(String
       InConditionForProgArea) throws RemoteException, Exception;

   /* code added to support LDFs */

   public TreeMap<Object,Object> getLDFDropdowns() throws RemoteException, Exception;
   public HashMap<Object, Object> getLDFValues(String ldfType) throws RemoteException, Exception;
/* Created generic method getLDFValues to replace the following three methods*/
/*
   public HashMap<Object,Object> getLDFValidations() throws RemoteException, Exception;
   public HashMap<Object,Object> getLDFDataTypes() throws RemoteException, Exception;
   public HashMap<Object,Object> getLDFClassCodes() throws RemoteException, Exception;
*/
   public ArrayList<Object>  getLDFPages() throws RemoteException, Exception;

   public ArrayList<Object>  getLDFPageIDs() throws RemoteException, Exception;
   
   public ArrayList<Object>  getLDFPageIDsHome() throws RemoteException, Exception;

   public TreeMap<Object,Object> getOrderedTestAndResultValues(String code) throws
       RemoteException, Exception;

   public TreeMap<Object,Object> getDrugNames() throws RemoteException, Exception;

   public TreeMap<Object,Object> getTreatmentDesc() throws RemoteException, Exception;

   public TreeMap<Object,Object> getTreamentDrug() throws RemoteException, Exception;

   public PreDefinedTreatmentDT getTreamentPreDefined(String pTreatmentCd) throws
       RemoteException, Exception;
   public TreeMap<Object,Object> getResultedTest() throws RemoteException, Exception;

   public TreeMap<Object,Object> getResultedTestLab(String labId,String testCd) throws RemoteException, Exception;
   public TreeMap<Object,Object> getOrganismList() throws RemoteException, Exception;
   public TreeMap<Object,Object> getOrganismListSNM() throws RemoteException, Exception;
   public TreeMap<Object,Object> getStateCodeList() throws RemoteException, Exception;

   //10-21-2003 CD
   public TreeMap<Object,Object> getCountyShortDescTxt() throws RemoteException, Exception;
   public TreeMap<Object,Object> getConditionCdAndProgramAreaCd() throws RemoteException, Exception;
   //10-30-2003 CD
   public TreeMap<Object,Object> getConditionCdAndInvFormCd() throws RemoteException, Exception;

   //10-28-2003
   public TreeMap<Object,Object> getACountysReportingSources (String countyCd) throws RemoteException, Exception;

   //11-07-2003 CD
   public TreeMap<Object,Object> getAllCodedValuesForBMD120AndBMD121() throws RemoteException, Exception;

   //11-17-2003
   public TreeMap<Object,Object> getAllCodedValuesForVacNm() throws RemoteException, Exception;

  //12-09-2003 CD
  public TreeMap<Object,Object> getStateDefinedSRTCodeSetName() throws RemoteException, Exception;
  //02-23-04 RK
  public TreeMap<Object,Object> getSAICDefinedCodedValues(String type) throws RemoteException, Exception;

  //12-23-2003 CD
  public TreeMap<Object,Object> getAllCodesForBMD120AndBMD121() throws RemoteException, Exception;
  
  /**
   * Description for the selected Lab Test Cd 
   * @param code
   * @return
   * @throws RemoteException
   * @throws Exception
   */
  public String getLabTestDesc(String code) throws RemoteException, Exception;
  
  public String getAllFilterOperators() throws RemoteException, Exception;
  public ArrayList<Object>  getCountryCodesList() throws RemoteException, Exception;
  public ArrayList<Object>  getRaceCodesList(String superCode) throws RemoteException,Exception;
  public ArrayList<Object>  getCountyCodeList(String state) throws RemoteException,Exception;
  public ArrayList<Object>  getAllCityCodes(String stateCd) throws RemoteException, Exception;
  public ArrayList<Object>  getAllQECodes(String Type) throws RemoteException, Exception;
  public ArrayList<Object>  getlaboratoryIds() throws RemoteException, Exception;
  public ArrayList<Object>  getAllConditionCodes() throws RemoteException, Exception;
  public ArrayList<Object>  getAllCodeSetNms() throws RemoteException, Exception;
  public ArrayList<Object>  getNedssUserlist() throws RemoteException, Exception;
  public ArrayList<Object>  getUsersWithValidEmailLst() throws RemoteException, Exception;
  public ArrayList<Object>  getUsersWithActiveAlerts() throws RemoteException, Exception;
  public ArrayList<Object>  getAllCodeSystemCdDescs(String codeSetNm) throws RemoteException, Exception;
  public ArrayList<Object>  getSRTAdminCodedValue(String codeSetNm) throws RemoteException, Exception;
  public ArrayList<Object>  getCodingSystemCodes(String assignAuth) throws RemoteException, Exception;
  public ArrayList<Object>  getLdfHtmlTypes(String formCd) throws RemoteException, Exception;
  public ArrayList<Object>  getAvailableTabs(String ldfPageId) throws RemoteException, Exception;
  public ArrayList<Object>  getLDFSections(Long tabId) throws RemoteException, Exception;
  public ArrayList<Object>  getLDFSubSections(Long sectionId) throws RemoteException, Exception;
  public ArrayList<Object>  getCodesetNames() throws RemoteException, Exception;
  public TreeMap<Object,Object> getInvFrmCdLdfPgIdMap() throws RemoteException, Exception;
  public ArrayList<Object>  getIsoCountryList() throws RemoteException, Exception;
  public ArrayList<Object>  getCodedValueOrderdByNbsUid(String type) throws RemoteException, Exception;
  public ArrayList<Object>  getExportReceivingFacilityList() throws RemoteException, Exception;
  public String getCodeShortDescTxt(String code, String codeSetNm) throws RemoteException, Exception;
  public TreeMap<Object,Object> getCountyCodesInclStateWide(String state) throws RemoteException,  Exception;
  public ArrayList<Object>  getAggregateSummaryReportConditionCode(String code) throws RemoteException, Exception;
  public TreeMap<Object,Object> getConditionTracingEnableInd() throws RemoteException, Exception;
  public ArrayList<Object>  getAllActiveCodeSetNms() throws RemoteException, Exception;
  public ArrayList<Object>  getDefaultDisplayControl(String code) throws RemoteException, Exception;
  public ArrayList<Object>  getDefaultDisplayControlDesc() throws RemoteException, Exception;
  public ArrayList<Object>  getAllActiveTemplates(String busObjType) throws RemoteException, Exception;
  public ArrayList<Object>  getAllActiveCodeSetNmsByGroupId() throws RemoteException, Exception;
  public ArrayList<Object>  getFilteredConditionCode() throws RemoteException, Exception;
  public ArrayList<Object>  getAvailableConditionCode(String busObjType) throws RemoteException, Exception;
  public ArrayList<Object>  getvalueSetTypeCdNoSystemStrd() throws RemoteException, Exception;
  public ArrayList<Object>  getNbsUnitsType() throws RemoteException, Exception;
  public TreeMap<Object,Object>  getStandredConceptCVGList() throws RemoteException,Exception;
  public ArrayList<Object>  getConditionWithNoPortReqInd() throws RemoteException, Exception;
  public ArrayList<Object>  getSendingSystemList(String systemType) throws RemoteException, Exception;
  public ArrayList<Object>  getInvestigationTypeRelatedPage() throws RemoteException, Exception;
  public ArrayList<Object>  getConditionDropDown(String relatedPage) throws RemoteException, Exception;
  public ArrayList<Object>  getConditionDropDown(Long waTemplateUid) throws RemoteException, Exception;
  public ArrayList<Object>  getPublishedConditionDropDown() throws RemoteException, Exception;
  public ArrayList<Object>  getParticipationTypes(String actClassCode) throws RemoteException, Exception;
  public ArrayList<Object> getXSSFilterPatterns() throws RemoteException,Exception;
  public Map<Object, Object> getConditionCoinfectionMap() throws RemoteException, Exception;
  public ArrayList<Object> getPlaceList() throws RemoteException, Exception;
  public TreeMap<Object, Object> getPlaceMap() throws RemoteException, Exception;
  public Map<String, String> getNBSCodeFromPHINCodes() throws RemoteException, Exception;
  public ArrayList<Object>  getNullFlavorCodedValuesList(String code) throws RemoteException, Exception;
  public CodeLookupDT getLabCodeSystem(String labCode, String laboratoryID) throws RemoteException, Exception;
  public Map<String, String> getAOELOINCCodes() throws RemoteException, Exception;
  public String findToCode(String fromCodeSetNm, String fromCode, String toCodeSetNm)throws RemoteException, Exception ;
}