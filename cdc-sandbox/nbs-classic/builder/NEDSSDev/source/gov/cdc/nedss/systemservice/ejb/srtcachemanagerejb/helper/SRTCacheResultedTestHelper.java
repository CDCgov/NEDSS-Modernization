package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;
/**
 * <p>Title: SRTCacheResultedTestHelper(Part of SRT Filtering Requirements 1.1.3)</p>
 * <p>Description:
 * SRTCacheResultedTestHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedCodes(SRTCodeValueGenDT). SRTCodeValueGenDT for each code
 * - Hashmaps
 *       cachedConditionCdMap(Key:Reporting Facility,Value:HashMap(Key:ConditionCdMap,value:ArrayList(SRTLabTestDT)))
 *       cachedProgAreaCdMap(Key:Reporting Facility,Value:HashMap(Key:ProgAreaCdMap,value:ArrayList(SRTLabTestDT)))
 *       cachedFacilityMap(Key:Reporting Facility,Value:ArrayList(SRTLabTestDT))
 *       cachedOrderedTestMap(Key:Reporting Facility,Value:HashMap(Key:LabTestCd,value:ArrayList(SRTLabTestDT))
 * Method getResultedTests(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.ORDERED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.ORDERED_TEST_CODE,"T-11320");
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getResultedTests(p0);
 *
 *         2. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.CONDITION_CODE
 *         example:
 *            String facCode = "11X0703694";
 *             String condCode = "10054";
 *            HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *             p0.put(SRTFilterKeys.REPORTING_FACILITY_ID, facCode);
 *             p0.put(SRTFilterKeys.CONDITION_CODE, condCode);
 *              Collection<Object>  col = sRTCacheManager.getResultedTests(p0);
 *
 *         3. SRTFilterKeys.REPORTING_FACILITY_ID
*             SRTFilterKeys.PROGRAM_AREA_CODE
*         example:
*             HashMap<Object,Object> p0 = new HashMap<Object,Object>();
*              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID, facCode);
*              p0.put(SRTFilterKeys.PROGRAM_AREA_CODE, progAreaCode);
*              Collection<Object>  col = sRTCacheManager.getResultedTests(p0);
*
*         4. SRTFilterKeys.REPORTING_FACILITY_ID*
*         example:
*             HashMap<Object,Object> p0 = new HashMap<Object,Object>();
*              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID, facCode);
*              Collection<Object>  col = sRTCacheManager.getResultedTests(p0);
*
 *
 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *

 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Mark Hankey
 * @version 1.0
 */


import java.util.*;

import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerEJB;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao.SRTResultedTestDAOImpl;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import gov.cdc.nedss.util.*;

import gov.cdc.nedss.systemservice.util.*;


public class SRTCacheResultedTestHelper extends BaseSRTCacheHelper  {
  private HashMap<Object,Object> cachedFacilityMap = new HashMap<Object, Object>(); //->srtLabTestList
  private HashMap<Object,Object> cachedProgAreaMap = new HashMap<Object, Object>(); //->facMap->srtLabTestList
  private HashMap<Object,Object> cachedConditionCdMap = new HashMap<Object, Object>(); //->facMap->srtLabTestList
  private HashMap<Object,Object> cachedOrderedTestMap = new HashMap<Object, Object>(); //->facMap->srtLabTestList
  /* hold data from direct query of lab_test for all resulted tests.*/
  private Collection<Object>  cachedRtAllList = null;
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  Collection<Object>   resultCollection  = null;
  static final LogUtils logger = new LogUtils (SRTCacheResultedTestHelper.class.getName());
  private static SRTCacheResultedTestHelper instance = new SRTCacheResultedTestHelper();

  public SRTCacheResultedTestHelper() {
      if (cachedRtAllList == null)
        loadCache();
  }

  public static SRTCacheResultedTestHelper getInstance(){
    return instance;
  }

  public static boolean validateKeys(Map<Object,Object> filter){
  boolean validkeys = false;
  if((filter.size() == 2)
          &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
          &&filter.containsKey(SRTFilterKeys.CONDITION_CODE))
    validkeys = true;
  else if((filter.size() == 2)
          &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
          &&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE))
    validkeys = true;
  else if((filter.size() == 2)
        &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
        &&filter.containsKey(SRTFilterKeys.ORDERED_TEST_CODE))
        validkeys = true;
  else if((filter.size() == 2)
    &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
    &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE))
      validkeys = true;
  else if((filter.size() == 1)
          &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
           validkeys = true;

  return validkeys;
}


  private void loadCache() {
  if (cachedRtAllList == null) {

    loadCachedRtAll();

    SRTResultedTestDAOImpl srtResDAO = new SRTResultedTestDAOImpl();

    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_PROGAREA_MAPPING);
    if (mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES)) {
       Collection<Object>  labTestColl = srtResDAO.getLabTestProgAreaMapping();
       logger.debug("SRTCacheResultedTestHelper.loadCache The labTestColl size is :"+ labTestColl.size());
      Iterator<Object>  labTestCollIter = labTestColl.iterator();
       while (labTestCollIter.hasNext()) {
         TestResultTestFilterDT testResultTestFilterDT = (TestResultTestFilterDT) labTestCollIter.next();
         //load Prog area hashtable
         SRTLabTestDT srtLabTestDT = lookupLabTestDTFromCachedRtAllList(testResultTestFilterDT.getLaboratoryId(),testResultTestFilterDT.geLabTestCd());
         if (testResultTestFilterDT.getProgAreaCd() != null) {
           //adding to cachedProgAreaMap
           Map<Object,Object> progAreaTempMap = null;
           List<Object> srtLabTestList = new ArrayList<Object> ();
           if (cachedProgAreaMap.containsKey(testResultTestFilterDT.getLaboratoryId())) {
             progAreaTempMap = (Map<Object,Object>) cachedProgAreaMap.get(testResultTestFilterDT.getLaboratoryId());

           }
           else{
             progAreaTempMap = new HashMap<Object,Object>();
             cachedProgAreaMap.put(testResultTestFilterDT.getLaboratoryId(),progAreaTempMap);
           }
           if (progAreaTempMap.isEmpty()||!progAreaTempMap.containsKey(testResultTestFilterDT.getProgAreaCd())) {
             progAreaTempMap.put(testResultTestFilterDT.getProgAreaCd(),srtLabTestList);
           }
           else {
             srtLabTestList = (List<Object>) progAreaTempMap.get(testResultTestFilterDT.getProgAreaCd());
           }
           if (srtLabTestList != null && srtLabTestDT != null && !srtLabTestList.contains(srtLabTestDT)) {
             srtLabTestList.add(srtLabTestDT);
           }//end if
         }//end if (testResultTestFilterDT.getProgAreaCd() != null)

         //load cachedConditionCdMap
         if (testResultTestFilterDT.getConditionCd() != null) {
           Map<Object,Object> condCdTempMap = null;
           List<Object> srtLabTestList = new ArrayList<Object> ();
           if (cachedConditionCdMap.containsKey(testResultTestFilterDT.getLaboratoryId())) {
             condCdTempMap = (Map<Object,Object>) cachedConditionCdMap.get(testResultTestFilterDT.getLaboratoryId());
           }
           else{
             condCdTempMap = new HashMap<Object,Object>();
             cachedConditionCdMap.put(testResultTestFilterDT.getLaboratoryId(),condCdTempMap);
           }
           if (condCdTempMap.isEmpty()||!condCdTempMap.containsKey(testResultTestFilterDT.getConditionCd())) {
             condCdTempMap.put(testResultTestFilterDT.getConditionCd(),srtLabTestList);
           }
           else {
             srtLabTestList = (List<Object>) condCdTempMap.get(testResultTestFilterDT.getConditionCd());
           }
           if (srtLabTestList != null && srtLabTestDT != null && !srtLabTestList.contains(srtLabTestDT)) {
             srtLabTestList.add(srtLabTestDT);
           } //end if
         }// end if

       }//end if(labTestCollIter.hasNext())
   
    }
    logger.debug("SRTCacheResultedTestHelper.loadCache  The  ResultedCache is Loaded");

    String mapFlagLabTestRelationship = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_RELATIONSHIP);
    if (mapFlagLabTestRelationship != null && mapFlagLabTestRelationship.equalsIgnoreCase(NEDSSConstants.YES)) {
      SRTResultedTestDAOImpl srtResultedTestDAO = new SRTResultedTestDAOImpl();
      Collection<Object>  orderedResultedTestsColl = srtResultedTestDAO.getOrderedResultedTests();
     Iterator<Object>  orderedResultedTestsIter = orderedResultedTestsColl.iterator();
      Map<Object,Object> labCdMap = null;
      while(orderedResultedTestsIter.hasNext()){
        SRTLabTestRelationshipDT srtLabTestRelationshipDT = (SRTLabTestRelationshipDT)orderedResultedTestsIter.next();

        //load cachedOrderedTestMap
        if(cachedOrderedTestMap.containsKey(srtLabTestRelationshipDT.getOrderedLaboratoryId())){
          labCdMap = (Map<Object,Object>) cachedOrderedTestMap.get(srtLabTestRelationshipDT.
              getOrderedLaboratoryId());
        }
        else{
          labCdMap = new HashMap<Object,Object>();
          cachedOrderedTestMap.put(srtLabTestRelationshipDT.getOrderedLaboratoryId(),labCdMap);
        }

        ArrayList<Object>  labCdTempList = null;
        if(labCdMap.containsKey(srtLabTestRelationshipDT.getOrderedLabTestCd())){
          labCdTempList = (ArrayList<Object> )labCdMap.get(srtLabTestRelationshipDT.getOrderedLabTestCd());
        }
        else{
          labCdTempList = new ArrayList<Object> ();
          labCdMap.put(srtLabTestRelationshipDT.getOrderedLabTestCd(),labCdTempList);
        }
        SRTLabTestDT srtLabTestDT = convertToSRTLabTestDT(srtLabTestRelationshipDT);
        labCdTempList.add(srtLabTestDT);
        //end cachedOrderedTestMap

      }//end while
    }//end if
  }//end
 logger.debug("exiting SRTCacheResultedTestHelper.loadCache");

}//end method

  private void getCachedConditionCdMap(Map<Object,Object> filter){
    Map<Object,Object> condCdTempMap =  (Map<Object,Object>)cachedConditionCdMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
    if(condCdTempMap!=null&&condCdTempMap.size()!=0){
      resultCollection  = (Collection<Object>)condCdTempMap.get(filter.get(SRTFilterKeys.CONDITION_CODE));
    }
  }

  private void getCachedProgAreaMap(Map<Object,Object> filter){
    if(cachedOrderedTestMap != null)
    {
      Map<Object,Object> progAreaTempMap = (Map<Object,Object>) cachedProgAreaMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
      if (progAreaTempMap != null && progAreaTempMap.size() != 0) {
        resultCollection  = (Collection<Object>) progAreaTempMap.get(filter.get(SRTFilterKeys.PROGRAM_AREA_CODE));
      }
    }
  }

  private void getCachedOrderedTestMap(Map<Object,Object> filter){
    if(cachedOrderedTestMap != null)
    {
      Map<Object,Object> labTestCdMap =  (Map<Object,Object>)cachedOrderedTestMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
      if(labTestCdMap!=null && labTestCdMap.size()!=0){
        resultCollection  = (Collection<Object>) labTestCdMap.get(filter.get(SRTFilterKeys.ORDERED_TEST_CODE));
      }
    }

}
 private void getCachedFacilityMap(Map<Object,Object> filter){
   if(cachedFacilityMap != null)
   {
     List<Object> labTestDTList = (ArrayList<Object> ) cachedFacilityMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
     if(labTestDTList != null)
     {
       if (!labTestDTList.isEmpty()) {
        Iterator<Object>  labTestDTListIter = labTestDTList.iterator();
         while (labTestDTListIter.hasNext()) {
           SRTLabTestDT srtLabTestDT = (SRTLabTestDT) labTestDTListIter.next();
           if (srtLabTestDT.getLabTestCd().equals(filter.get(SRTFilterKeys.RESULTED_TEST_CODE))) {
             resultCollection.add(srtLabTestDT);
             break;
           } //end if
         } //end while
       } //end if
     }
   }
   }//end method

    public Collection<Object>  getResultedTests(Map<Object,Object> filter, boolean drugIndicator){

    resultCollection  = new ArrayList<Object> ();
    if((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.ORDERED_TEST_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedOrderedTestMap.size()!=0){
      getCachedOrderedTestMap(filter);

      if(resultCollection==null||resultCollection.size()==0){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
        getCachedOrderedTestMap(filter);
      }

    }

    if(((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.CONDITION_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedConditionCdMap.size()!=0)
            &&(resultCollection  ==null||resultCollection.size() ==0)){
      getCachedConditionCdMap(filter);

      if(resultCollection==null||resultCollection.size()==0){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
        getCachedConditionCdMap(filter);
      }

    }
    else if(((filter.size() ==2)
        &&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE)
        &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
        &&cachedProgAreaMap.size()!=0)
        &&(resultCollection  ==null||resultCollection.size() ==0)){
         getCachedProgAreaMap(filter);

        if(resultCollection==null||resultCollection.size()==0){
           filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
           getCachedProgAreaMap(filter);
        }

    }

    if(((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedFacilityMap.size()!=0)
            &&(resultCollection  ==null||resultCollection.size() ==0)){
      getCachedFacilityMap(filter);

      if(resultCollection  == null || resultCollection.isEmpty()){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID, "DEFAULT");
        getCachedFacilityMap(filter);
      }

    }
    if(filter.size()==2)
    {
      if (resultCollection  == null || resultCollection.isEmpty()) {
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID, "DEFAULT");
        resultCollection  = (Collection<Object>) cachedFacilityMap.get(filter.get(
            SRTFilterKeys.REPORTING_FACILITY_ID));
      }

      resultCollection  = filterDrugs(resultCollection, drugIndicator);
    }
    if(((filter.size() == 1||filter.size()==2)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedFacilityMap.size()!=0)
            &&(resultCollection  ==null||resultCollection.size() ==0)){
      resultCollection  =  (Collection)cachedFacilityMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
      if(resultCollection  == null || resultCollection.isEmpty()){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID, "DEFAULT");
        resultCollection  =  (Collection)cachedFacilityMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
      }
    }
    if(resultCollection  == null){
      resultCollection  = new ArrayList<Object> ();
    }
      resultCollection  = filterDrugs(resultCollection,drugIndicator);
      return resultCollection;
  }


  public SRTLabTestDT convertToSRTLabTestDT(SRTLabTestRelationshipDT oldDT){
    SRTLabTestDT newDT = new SRTLabTestDT();
    newDT.setLabTestCd(oldDT.getResultLabTestCd());
    newDT.setLabTestDescTxt(oldDT.getResultLabTestDescTxt());
    newDT.setLaboratoryId(oldDT.getResultLaboratoryId());
    newDT.setTestTypeCd(oldDT.getResultTestTypeCd());
    newDT.setIndentLevel(oldDT.getIndentLevel());

    return newDT;
  }

  private void loadCachedRtAll(){
    cachedFacilityMap = new HashMap<Object,Object>();
    SRTResultedTestDAOImpl srtRtDAO = new SRTResultedTestDAOImpl();
    Collection<Object>  rtAllTempColl = srtRtDAO.getAllResultedTests();
    cachedRtAllList = convertToSRTLabTestDT(rtAllTempColl);

    //load cachedFacilityMap
   Iterator<Object>  iter = cachedRtAllList.iterator();
    while(iter.hasNext()){
     SRTLabTestDT srtLabTestDT =  (SRTLabTestDT)iter.next();
     if(srtLabTestDT.getLaboratoryId()!= null ){
       if(cachedFacilityMap.containsKey(srtLabTestDT.getLaboratoryId())){
         Collection<Object>  facIdColl = (Collection<Object>) cachedFacilityMap.get(
             srtLabTestDT.getLaboratoryId());
        facIdColl.add(srtLabTestDT);
       }//end if
       else{
         Collection<Object>  facIdColl = new ArrayList<Object> ();
         facIdColl.add(srtLabTestDT);
         cachedFacilityMap.put(srtLabTestDT.getLaboratoryId(),facIdColl);
       }//end else
      }// end if labId !=null
    }//end while
  }//endd cachedFacilityMap

  private SRTLabTestDT convertToSRTLabTestDT(TestResultTestFilterDT oldDT){
    SRTLabTestDT newDT = new SRTLabTestDT();
      newDT.setLaboratoryId(oldDT.getLaboratoryId());
      newDT.setLabTestCd(oldDT.geLabTestCd());
      newDT.setLabTestDescTxt(oldDT.getLabTestDescTxt());
      newDT.setTestTypeCd(oldDT.getTestTypeCd());
      newDT.setDrugTestInd(oldDT.getDrugTestInd());
      newDT.setIndentLevel(oldDT.getIndentLevel());
      return newDT;

  }

  private SRTLabTestDT lookupLabTestDTFromCachedRtAllList(String laboratoryId, String labTestCd){
   Iterator<Object>  iter = cachedRtAllList.iterator();
    SRTLabTestDT srtLabTestDT = null;
    while(iter.hasNext()){
      srtLabTestDT = (SRTLabTestDT)iter.next();
      if(srtLabTestDT.getLaboratoryId().equals(laboratoryId)
        &&(srtLabTestDT.getLabTestCd().equals(labTestCd))){
       break;
     }//end if
    }// end while
    return srtLabTestDT;
  }

  private Collection<Object>  filterDrugs(Collection<Object> list, boolean drugIndicator){
   Iterator<Object>  iter = list.iterator();
    Collection<Object>   newCollection  = new ArrayList<Object> ();
    while(iter.hasNext()){
      SRTLabTestDT srtLabTestDT = (SRTLabTestDT)iter.next();
      if(drugIndicator == true){
        if (srtLabTestDT.getDrugTestInd()!=null&&srtLabTestDT.getDrugTestInd().equals("Y"))
          newCollection.add(srtLabTestDT);
      }
      else if(drugIndicator == false){
        if (srtLabTestDT.getDrugTestInd()==null||srtLabTestDT.getDrugTestInd().equals("N"))
          newCollection.add(srtLabTestDT);
      }
    }
    return newCollection;
  }

}