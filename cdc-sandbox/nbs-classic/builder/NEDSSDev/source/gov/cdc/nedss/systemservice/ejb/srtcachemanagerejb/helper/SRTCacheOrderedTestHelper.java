package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;

import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.*;
import java.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * <p>Title: SRTCacheOrderedTestHelper (Part of SRTFiltering Requirements for 1.1.3)</p>
 * <p>Description:
 * SRTCacheOrderedTestHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedLabTest(SRTLabTestDT). SRTLabTestDT for each Ordered Test
 * - Hashmaps
 *         cachedProgAreaFacilityList(Key:Program Area,Value:HashMap(Key:ReportingFacility,value:ArrayList(SRTLabTestDT)))
 *         cachedFacilityList(key:ReportingFacility,value:ArrayList(SRTLabTestDT))
 *         cachedConditionCdFacilityList(key:ConditionCode,Value:HashMap(Key:ReportingFacility,value:ArrayList(SRTLabTestDT)))
 *
 * Method getOrderedTests(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.ORDERED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.ORDERED_TEST_CODE,"T-11320");
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getOrderedTests(p0);
 *
 *         2. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.CONDITION_CODE
 *         3. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.PROGRAM_AREA_CODE
 *         4. SRTFilterKeys.REPORTING_FACILITY_ID
 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *
 * </p>
 * <p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Raghu Komanduri
 * @version 1.0
 */

public class SRTCacheOrderedTestHelper extends BaseSRTCacheHelper{
  static final LogUtils logger = new LogUtils(SRTCacheOrderedTestHelper.class.getName());

  private static HashMap<Object,Object> cachedProgAreaFacilityList = null;
  private static HashMap<Object,Object> cachedFacilityList = null;
  private static HashMap<Object,Object> cachedConditionCdFacilityList = null;
  private static ArrayList<Object> cachedLabTest = null;
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  private static SRTCacheOrderedTestHelper SRTSingleton = new SRTCacheOrderedTestHelper();


  private SRTCacheOrderedTestHelper()
  {
     if(cachedLabTest == null)
          loadCache();
  }
  /**
   * getInstance
   * @return returns an instance of SRTCacheOrderedTestHelper
   */
  public static SRTCacheOrderedTestHelper getInstance()
  {
    return SRTSingleton;
  }

  /**
   * loadCache()
   * Loads the cache upon first instance of the class
   * @return
   */
  private ArrayList<Object> loadCache() {
    if (cachedLabTest == null)
    {
      cachedLabTest = getOrderedTestCollection();
      return cachedLabTest;
    }
    else
    {
        return cachedLabTest;
    }
  }
  /**
   * getOrderedTestCollection: used by loadCache to load the cache. Does DAO calls
   * @return
   */
  private ArrayList<Object> getOrderedTestCollection() {

    if (cachedLabTest != null) {
      return cachedLabTest;
    }
    //loads cachedLabTest
    loadCachedLabTestCollection();

    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_PROGAREA_MAPPING);
    if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
    {

      cachedProgAreaFacilityList = new HashMap<Object,Object>();
      cachedConditionCdFacilityList = new HashMap<Object,Object>();

      SRTOrderedTestDAOImpl dao = new SRTOrderedTestDAOImpl();

      String labId = null;
      String programArea = null;
      String conditionCd = null;
      Collection<Object>  ltMap = (ArrayList<Object> ) dao.getLabTestProgAreaMapping();

      if (ltMap != null) {
       Iterator<Object>  it = ltMap.iterator();
        int size = ltMap.size();
        SRTLabTestDT labDt = null;
        while (it.hasNext()) {

          TestResultTestFilterDT testResults = (TestResultTestFilterDT) it.next();
          labDt = convertToSRTLabTestDT(testResults);
          labId = testResults.getLaboratoryId();
          programArea = testResults.getProgAreaCd();
          conditionCd = testResults.getConditionCd();

          if (programArea != null && labId != null) {
            addToCachedProgAreaList(programArea, labId, labDt);
          }
          if (conditionCd != null && labId != null) {
            addToCachedConditionCdList(conditionCd, labId, labDt);
          }
        }
      }
    }

     return cachedLabTest;
  }
  /**
   * Converts RootDTInterface that DAO returns to a simple DT
   * @param oldDT
   * @return
   */
  private SRTLabTestDT convertToSRTLabTestDT(TestResultTestFilterDT oldDT){
    SRTLabTestDT newDT = new SRTLabTestDT(oldDT);
    return newDT;

  }

  /**
   * loads cachedFacilityList hashmap
   * @param labId
   * @param labTestDt
   */
  private void addToCachedFacilityList(String labId,SRTLabTestDT labTestDt)
  {
    ArrayList<Object> ofArrayList=null;
    if(cachedFacilityList != null)
    {
      ofArrayList= (ArrayList<Object> ) cachedFacilityList.get(labId);
      if (ofArrayList== null) {
        ofArrayList= new ArrayList<Object> ();
        ofArrayList.add(labTestDt);
        cachedFacilityList.put(labId, ofArrayList);
      }
      else {
        ofArrayList.add(labTestDt);
      }
    }

  }
  /**
   * addToCachedProgAreaList:
   * loads cachedProgAreaFacilityList
   * @param programArea
   * @param facilityId
   * @param labTestDt
   */
  private void addToCachedProgAreaList(String programArea,String facilityId,SRTLabTestDT labTestDt)
  {
    ArrayList<Object>  otArrayList=null;
    HashMap<Object,Object> ofMap = null;
    if(cachedProgAreaFacilityList != null)
    {
      ofMap = (HashMap<Object,Object>) cachedProgAreaFacilityList.get(programArea);
      if (ofMap == null) {
        ofMap = new HashMap<Object,Object>();
        otArrayList= new ArrayList<Object> ();
        otArrayList.add(labTestDt);
        ofMap.put(facilityId, otArrayList);
        cachedProgAreaFacilityList.put(programArea, ofMap);
      }
      else {
        if (ofMap.containsKey(facilityId)) {
          otArrayList= (ArrayList<Object> ) ofMap.get(facilityId);
        }
        else {
          otArrayList= new ArrayList<Object> ();
          ofMap.put(facilityId, otArrayList);
        }
        otArrayList.add(labTestDt);

      }
    }

  }
  /**
   * addToCachedConditionCdList
   * loads hashmap cachedConditionCdFacilityList
   * @param conditionCd
   * @param facilityId
   * @param labTestDt
   */
  private void addToCachedConditionCdList(String conditionCd,String facilityId,SRTLabTestDT labTestDt)
  {
    ArrayList<Object>  otArrayList=null;
    HashMap<Object,Object> ofMap = null;
    if(cachedConditionCdFacilityList != null)
    {
      ofMap = (HashMap<Object,Object>) cachedConditionCdFacilityList.get(conditionCd);
      if (ofMap == null) {
        ofMap = new HashMap<Object,Object>();
        otArrayList= new ArrayList<Object> ();
        otArrayList.add(labTestDt);
        ofMap.put(facilityId, otArrayList);
        cachedConditionCdFacilityList.put(conditionCd, ofMap);
      }
      else {
        if (ofMap.containsKey(facilityId)) {
          otArrayList= (ArrayList<Object> ) ofMap.get(facilityId);
        }
        else {
          otArrayList= new ArrayList<Object> ();
          ofMap.put(facilityId, otArrayList);
        }
        otArrayList.add(labTestDt);

      }
    }


  }

  /**
   * Access Point: getOrderedTestNames
   * Based on the filter, returns arraylist of SRTLabTestDT's from the cache
   * If filter is not found, returns the whole list
   * @param filter
   * @return
   * @throws NEDSSSystemException
   * @throws InvalidSRTFilterKeysException
   */
 public Collection<Object>  getOrderedTestNames(Map<String,String> filter) throws
     NEDSSSystemException , InvalidSRTFilterKeysException {



   ArrayList<Object> returnSet = null;
   String programArea = null;
   String condition = null;
   String orderingFacility = null;
   String orderedTest = null;

   programArea = (String) filter.get(SRTFilterKeys.PROGRAM_AREA_CODE);
   condition = (String) filter.get(SRTFilterKeys.CONDITION_CODE);
   orderingFacility = (String) filter.get(SRTFilterKeys.REPORTING_FACILITY_ID);
   orderedTest = (String) filter.get(SRTFilterKeys.ORDERED_TEST_CODE);

   if (filter.size() == 1) {
     if (orderingFacility != null)
       returnSet = (ArrayList<Object> ) getOrderedTestNamesByOrderingFacility(orderingFacility);
     else {
       throw new InvalidSRTFilterKeysException("Invalid Filter Key Exception");

     }
   }
   else
   {
     if (programArea != null && orderingFacility != null)
     {
       returnSet = (ArrayList<Object> ) getOrderedTestNamesByPA(programArea, orderingFacility);
       if(returnSet == null)
         returnSet = (ArrayList<Object> ) getOrderedTestNamesByPA(programArea, "DEFAULT");
     }

     else if (condition != null && orderingFacility != null)
     {
       returnSet = (ArrayList<Object> ) getOrderedTestNamesByCondition(condition, orderingFacility);
       if(returnSet == null)
         returnSet = (ArrayList<Object> ) getOrderedTestNamesByCondition(condition, "DEFAULT");
     }

     else if(orderedTest != null && orderingFacility != null)
     {
       returnSet = (ArrayList<Object> ) getOrderedTestNamesByOrderingFacilityAndTest(orderingFacility, orderedTest);
       if(returnSet == null)
         returnSet = (ArrayList<Object> ) getOrderedTestNamesByOrderingFacilityAndTest("DEFAULT",orderedTest);
     }
     else
       throw new NEDSSSystemException("Invalid Filter Key Exception");

   }

   if (returnSet == null)
     return getOrderedTestNamesByOrderingFacility("DEFAULT");
   else
     return returnSet;
}
 /**
  * validateFilterKeys- static method to validate filter
  * @param filter
  * @return
  */
public static boolean validateFilterKeys(Map<String,String> filter)
 {
    if(filter == null)
      return false;

    if(filter.size() > 2)
          return false;

    if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.CONDITION_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.ORDERED_TEST_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else
      return false;

 }
 /**
  * getOrderedTestNamesByPA: For the given PA and reporting facility, returns Collection<Object>  fo SRTLabTestDT
  * @param programArea
  * @param labId
  * @return
  */
private Collection<Object>  getOrderedTestNamesByPA(String programArea,String labId)
{

  ArrayList<Object> dtList = null;
  HashMap<Object,Object> ofMap = null;

  if(cachedProgAreaFacilityList != null)
  {
    ofMap = (HashMap<Object,Object>) cachedProgAreaFacilityList.get(programArea);

    if (ofMap != null)
          dtList = (ArrayList<Object> ) ofMap.get(labId);
  }
  return dtList;
}
/**
 * getOrderedTestNamesByOrderingFacility
 * For a given Reporting facility, returns collection of SRTLabTestDT
 * @param labCLIAid
 * @return
 */
private Collection<Object>  getOrderedTestNamesByOrderingFacility(String labCLIAid)
{
    ArrayList<Object> dtList = null;
    if(cachedFacilityList != null)
        dtList = (ArrayList<Object> )cachedFacilityList.get(labCLIAid);
    return dtList;

 }
 /**
  * loadCachedLabTestCollection: used by loadCache method to load all ordered tests
  * into cache
  */
 private void loadCachedLabTestCollection()
  {
    cachedFacilityList = new HashMap<Object,Object>();
    SRTOrderedTestDAOImpl dao = new SRTOrderedTestDAOImpl();
    cachedLabTest= (ArrayList<Object> ) convertToSRTLabTestDT(dao.getAllOrderedTests());

    //load cachedFacilityList
   Iterator<Object>  iter = cachedLabTest.iterator();
    while(iter.hasNext()){
     SRTLabTestDT srtLabTestDT =  (SRTLabTestDT)iter.next();
     if(srtLabTestDT.getLaboratoryId()!= null ){
       this.addToCachedFacilityList(srtLabTestDT.getLaboratoryId(),srtLabTestDT);
      }// end if labId !=null
    }//end while

  }
  /**
   * getOrderedTestNamesByOrderingFacilityAndTest
   * for a given Reporting facility and Ordered Test, sends a collection of SRTLabTestDT
   * This collection will have one DT
   * @param orderingFacility
   * @param orderedTest
   * @return
   */
  private Collection<Object>  getOrderedTestNamesByOrderingFacilityAndTest(String
      orderingFacility, String orderedTest) {

    ArrayList<Object> dtList = null;
    ArrayList<Object> returnList = null;
    if(cachedFacilityList != null)
    {
      dtList = (ArrayList<Object> ) cachedFacilityList.get(orderingFacility);
      if(dtList != null)
      {
       Iterator<Object>  it = dtList.iterator();
        SRTLabTestDT srtLabTestDT = null;

        while (it.hasNext()) {
          srtLabTestDT = (SRTLabTestDT) it.next();
          if ( (srtLabTestDT.getLabTestCd()).equalsIgnoreCase(orderedTest)) {
            returnList = new ArrayList<Object> ();
            returnList.add(srtLabTestDT);
            break;
          }
        }
      }
    }
    return returnList;
  }
  /**
   * getOrderedTestNamesByCondition
   * For a given COndition Code and Reporting Facility,returns a collection of SRTLabTestDT
   * @param condition
   * @param labId
   * @return
   */
  private Collection<Object>  getOrderedTestNamesByCondition(String condition,String labId)
 {
   ArrayList<Object> dtList = null;
   HashMap<Object,Object> ofMap = null;
   if(cachedConditionCdFacilityList != null)
   {
     ofMap = (HashMap<Object,Object>) cachedConditionCdFacilityList.get(condition);

     if (ofMap != null)
       dtList = (ArrayList<Object> ) ofMap.get(labId);

   }
   return dtList;
 }
}