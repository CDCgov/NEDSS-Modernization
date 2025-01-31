package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;

import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.SRTFilterKeys;
import java.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import java.util.ArrayList;

/**
 * <p>Title: SRTCacheUnitsHelper(Part of SRT Filtering Requirements 1.1.3</p>
 * <p>Description:
 * * SRTCacheUnitsHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedCodes(SRTCodeValueGenDT). SRTCodeValueGenDT for each code
 * - Hashmaps
 *     cachedFacilityResultedTestList(key:ReportingFacility,Value:HashMap(Key:ResultedTestCode,value:ArrayList(SRTLabTestDT)))
 *
 *
 * Method getUnits(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.RESULTED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.RESULTED_TEST_CODE,"T-11320");
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getUnits(p0);
 *
 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences</p>
 * @author Raghu Komanduri
 * @version 1.0
 */


public class SRTCacheUnitsHelper {
  static final LogUtils logger = new LogUtils(SRTCacheUnitsHelper.class.getName());

  private static HashMap<Object,Object> cachedFacilityResultedTestList = null;
  private static HashMap<Object,Object> cachedLoincList = null;
  private static ArrayList<Object> cachedCodes = null;

  public static final String RESULTED_TEST = "R";

  private static SRTCacheUnitsHelper SRTSingleton = new SRTCacheUnitsHelper();
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  
  /**
   * Default constructor. Loads cache upon first instantiation
   */
  private SRTCacheUnitsHelper() {
    if(cachedCodes == null)
      loadCache();
  }
  /**
   * Returns single instance of the class
   * @return
   */
  public static SRTCacheUnitsHelper getInstance()
  {
    return SRTSingleton;
  }
  /**
   * loads the cache
   * @return
   */
  private ArrayList<Object> loadCache() {
     if (cachedCodes == null) {
       cachedCodes = getUnitsCollection();
       return cachedCodes;
     }
     else {
         return cachedCodes;
     }
   }
   /**
    * Makes DAO calls and delegates population of the cache to corresponding methods
    * @return
    */
   private ArrayList<Object> getUnitsCollection() {

     if (cachedCodes != null) {
       return cachedCodes;
     }

     cachedCodes = new ArrayList<Object> ();


     SRTCodeValueGenDAOImpl dao = new SRTCodeValueGenDAOImpl();

     String testTypeCd = null;
     String testName = null;
     String testId=null;
     String facilityId=null;
     cachedCodes = (ArrayList<Object> ) dao.convertToCodeValueGenDT(dao.getAllUnitsData());
     buildCachedLoincList();
     String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_UNIT_MAPPING);

     if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
     {
       cachedFacilityResultedTestList = new HashMap<Object,Object>();
       Collection<Object>  unitMapData = (ArrayList<Object> ) dao.getUnitsData();
       if (unitMapData != null) {
        Iterator<Object>  it = unitMapData.iterator();
         int size = unitMapData.size();
         while (it.hasNext()) {

           SRTLabCodeValueGenRootDT rootDt = (SRTLabCodeValueGenRootDT) it.next();

          Iterator<Object>  iter = cachedCodes.iterator();
           SRTCodeValueGenDT codeDt = null;
           while (iter.hasNext()) {
             codeDt = (SRTCodeValueGenDT) iter.next();
             if (codeDt.getCode().equalsIgnoreCase(rootDt.getCode()))
               break;
           }
           testTypeCd = rootDt.getTestTypeCd();
           testId = rootDt.getLabTestCd();
           facilityId = rootDt.getLaboratoryId();
           addToCachedResultedTestList(facilityId, testId, codeDt);
         }

       }
     }


      return cachedCodes;
   }
   private void buildCachedLoincList()
 {
   ArrayList<Object> list = null;
   String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LOINC_UNIT_MAPPING);
   if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
    {

      SRTCodeValueGenDAOImpl dao = new SRTCodeValueGenDAOImpl();
      list = (ArrayList<Object> ) dao.getLoincSpecimenSourceData();
      if (list != null) {
        cachedLoincList = new HashMap<Object,Object>();
       Iterator<Object>  it = list.iterator();
        int size = list.size();
        while (it.hasNext()) {

          SRTLabCodeValueGenRootDT rootDt = (SRTLabCodeValueGenRootDT) it.next();
          SRTCodeValueGenDT codeDt = null;
          String loincCd = rootDt.getLoincCd();
          if (loincCd != null) {
            codeDt = new SRTCodeValueGenDT(rootDt);
            ArrayList<Object> dtArrayList= null;
            if (cachedLoincList != null) {
              dtArrayList= (ArrayList<Object> ) cachedLoincList.get(loincCd);
              if (dtArrayList== null) {
                dtArrayList= new ArrayList<Object> ();
                dtArrayList.add(codeDt);
                cachedLoincList.put(loincCd, dtArrayList);
              }
              else {
                dtArrayList.add(codeDt);
              }
            }
          }
        }
      }
    }
 }
 private ArrayList<Object> getCodesByLoincCode(String loincCd) {
   ArrayList<Object> dtList = null;
   if(cachedLoincList != null)
     dtList = (ArrayList<Object> ) cachedLoincList.get(loincCd);

   return dtList;
 }

   /**
    * Populates hashmap cachedFacilityResultedTestList
    * @param facilityId
    * @param testId
    * @param codeDt
    */
  private void addToCachedResultedTestList(String facilityId,String testId,SRTCodeValueGenDT codeDt)
{
    ArrayList<Object> rtArrayList= null;
    HashMap<Object,Object> rtMap = null;

    if(cachedFacilityResultedTestList != null)
    {
      rtMap = (HashMap<Object,Object>) cachedFacilityResultedTestList.get(facilityId);
      if (rtMap == null) {
        rtMap = new HashMap<Object,Object>();
        rtArrayList= new ArrayList<Object> ();
        rtArrayList.add(codeDt);
        rtMap.put(testId, rtArrayList);
        cachedFacilityResultedTestList.put(facilityId, rtMap);
      }
      else {
        if (rtMap.containsKey(testId)) {
          rtArrayList= (ArrayList<Object> ) rtMap.get(testId);
        }
        else {
          rtArrayList= new ArrayList<Object> ();
          rtMap.put(testId, rtArrayList);
        }
        rtArrayList.add(codeDt);
      }
    }
  }



  /**
   * Public access point
   * For the given filter, sends corresponding collection of DT's or if not found in the Hashmap's,
   * sends the whole set
   * @param filter
   * @return
   * @throws NEDSSSystemException
   * @throws InvalidSRTFilterKeysException
   */
  public Collection<Object>  getUnits(Map<Object,Object> filter) throws
      NEDSSSystemException , InvalidSRTFilterKeysException {


    //System.out.println("Return list based on filter");
    ArrayList<Object> returnSet = null;
    String resultedTestId = null;
    String facilityId = null;
    resultedTestId = (String)filter.get(SRTFilterKeys.RESULTED_TEST_CODE);
    facilityId = (String)filter.get(SRTFilterKeys.REPORTING_FACILITY_ID);
    String loincCd = null;
    loincCd = (String)filter.get(SRTFilterKeys.LOINC_CODE);
    String code = null;
    code = (String)filter.get(SRTFilterKeys.CODE);
    if(filter.size() == 2 && resultedTestId != null && facilityId != null)
       returnSet =  (ArrayList<Object> )getCodesByResultedTestNameAndFacility(resultedTestId,facilityId);
    else if(filter.size() == 1 && loincCd != null)
          returnSet = (ArrayList<Object> ) getCodesByLoincCode(loincCd);
    else if (filter.size() == 1 && code != null)
          returnSet = (ArrayList<Object> ) getCodesByCode(code);
    else if(filter.size() != 0)
    {
        throw new InvalidSRTFilterKeysException();
    }
    //Return the whole set if filter key is not found
    if(returnSet == null || filter.size() == 0)
      return cachedCodes;
    else
      return returnSet;

 }
 /**
  * Static method to validate the filter
  * @param filter
  * @return
  */
 public static boolean validateFilterKeys(Map<Object,Object> filter)
 {
    if(filter == null)
      return false;

    if(filter.size() > 2)
          return false;

    if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.LOINC_CODE))
     return true;
   else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.CODE))
     return true;
    else if(filter.size() == 0)
      return true;
    else
      return false;

 }
 private ArrayList<Object> getCodesByCode(String code) {
    ArrayList<Object> dtList = null;
   Iterator<Object>  iter = cachedCodes.iterator();
    SRTCodeValueGenDT codeDt =null;
    while(iter.hasNext())
    {
      codeDt = (SRTCodeValueGenDT) iter.next();
      if(codeDt.getCode().equalsIgnoreCase(code))
      {
        dtList = new ArrayList<Object> ();
        dtList.add(codeDt);
        break;
      }
    }
    return dtList;
  }

 /**
  * For a given resultedTestCode and Reporting facility, return collection of DTs
  * @param resultedTestName
  * @param facilityId
  * @return
  */
 private Collection<Object>  getCodesByResultedTestNameAndFacility(String resultedTestCode,String facilityId)
 {
   ArrayList<Object> dtList = null;
   HashMap<Object,Object> rtMap = null;

   if(cachedFacilityResultedTestList != null)
   {
     //System.out.println("Return list based on filter resulted_test");
     rtMap = (HashMap<Object,Object>) cachedFacilityResultedTestList.get(facilityId);
     if (rtMap != null)
       dtList = (ArrayList<Object> ) rtMap.get(resultedTestCode);
   }

   return dtList;

 }

}