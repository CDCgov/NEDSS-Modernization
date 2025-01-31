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
 * <p>Title: SRTCacheSpecimenSourceHelper(Part of SRT Filtering Requirements 1.1.3</p>
 * <p>Description:
 * * SRTCacheSpecimenSourceHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedCodes(SRTCodeValueGenDT). SRTCodeValueGenDT for each code
 * - Hashmaps
 *         cachedFacilityOrderedTestList(Key:Reporting Facility,Value:HashMap(Key:OrderedTestCode,value:ArrayList(SRTLabTestDT)))
 *         cachedFacilityResultedTestList(key:ReportingFacility,Value:HashMap(Key:ResultedTestCode,value:ArrayList(SRTLabTestDT)))
 *
 *
 * Method getSpecimenSource(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.ORDERED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.ORDERED_TEST_CODE,"T-11320");
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getSpecimenSource(p0);
 *
 *         2. SRTFilterKeys.RESULTED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID

 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *

 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences</p>
 * @author Raghu Komanduri
 * @version 1.0
 */

public class SRTCacheSpecimenSourceHelper {
  static final LogUtils logger = new LogUtils(SRTCacheSpecimenSourceHelper.class.getName());

  private static HashMap<Object,Object> cachedFacilityOrderedTestList = null;
  private static HashMap<Object,Object> cachedFacilityResultedTestList = null;
  private static HashMap<Object,Object> cachedLoincList = null;
  private static ArrayList<Object> cachedCodes = null;

  public static final String ORDERED_TEST = "O";
  public static final String RESULTED_TEST = "R";
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  private static SRTCacheSpecimenSourceHelper SRTSingleton = new SRTCacheSpecimenSourceHelper();

  /**
   * Default constructor. loads cache
   */
  private SRTCacheSpecimenSourceHelper() {
    if(cachedCodes == null)
      loadCache();
  }
  /**
   *
   * @return single instance of the class
   */
  public static SRTCacheSpecimenSourceHelper getInstance()
 {
   return SRTSingleton;
 }
 /**
  * loads the cache
  * @return
  */
  private ArrayList<Object> loadCache() {
    if (cachedCodes == null) {
      cachedCodes = getSpecimenSourceCollection();
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
  private ArrayList<Object> getSpecimenSourceCollection() {

    if (cachedCodes != null) {
      return cachedCodes;
    }

    cachedCodes = new ArrayList<Object> ();



    SRTCodeValueGenDAOImpl dao = new SRTCodeValueGenDAOImpl();

    String testTypeCd = null;
    String testName = null;
    String testId = null;
    String facilityId = null;


    cachedCodes = (ArrayList<Object> ) dao.convertToCodeValueGenDT(dao.getAllSpecimenSourceData());
    buildCachedLoincList();
    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_SPECIMEN_MAPPING);
    if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
    {
      Collection<Object>  specimenMapData = (ArrayList<Object> ) dao.getSpecimenSourceData();
//      System.out.println("Size of collection from DB="+specimenMapData.size());
      cachedFacilityOrderedTestList = new HashMap<Object,Object>();
      cachedFacilityResultedTestList = new HashMap<Object,Object>();
      if (specimenMapData != null) {
       Iterator<Object>  it = specimenMapData.iterator();
        int size = specimenMapData.size();
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
          if (testTypeCd.equalsIgnoreCase(ORDERED_TEST)) {
            addToCachedOrderedTestList(facilityId, testId, codeDt);

          }
          else if (testTypeCd.equalsIgnoreCase(RESULTED_TEST)) {
            addToCachedResultedTestList(facilityId, testId, codeDt);
          }
        }
      }
    }


     return cachedCodes;
  }
  private void buildCachedLoincList()
  {
    ArrayList<Object> list = null;
    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LOINC_SPECIMEN_MAPPING);
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
   * Populates cachedFacilityOrderedTestList hashmap
   * @param facilityId
   * @param testId
   * @param codeDt
   */
 private void addToCachedOrderedTestList(String facilityId,String testId,SRTCodeValueGenDT codeDt)
{
  ArrayList<Object>  otArrayList=null;
  HashMap<Object,Object> otMap = null;

  if(cachedFacilityOrderedTestList != null)
  {
    otMap = (HashMap<Object,Object>) cachedFacilityOrderedTestList.get(facilityId);
    if (otMap == null) {
      otMap = new HashMap<Object,Object>();
      otArrayList= new ArrayList<Object> ();
      otArrayList.add(codeDt);
      otMap.put(testId, otArrayList);
      cachedFacilityOrderedTestList.put(facilityId, otMap);
    }
    else {
      if (otMap.containsKey(testId)) {
        otArrayList= (ArrayList<Object> ) otMap.get(testId);
      }
      else {
        otArrayList= new ArrayList<Object> ();
        otMap.put(testId, otArrayList);
      }
      otArrayList.add(codeDt);
    }
  }
}
/**
 * Populates cachedFacilityResultedTestList hashmap
 * @param facilityId
 * @param testId
 * @param codeDt
 */
private void addToCachedResultedTestList(String facilityId,String testId,SRTCodeValueGenDT codeDt)
{
  ArrayList<Object> rtArrayList=null;
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
 * For the given filter, returns collection of DT's or returns whole set if filter not found
 * in the cache
 * @param filter
 * @return
 * @throws NEDSSSystemException
 * @throws InvalidSRTFilterKeysException
 */

 public Collection<Object>  getSpecimenSource(Map<Object,Object> filter) throws
     NEDSSSystemException, InvalidSRTFilterKeysException {


   //System.out.println("Return list based on filter");
   ArrayList<Object> returnSet = null;
   String orderedTestId = null;
   String resultedTestId = null;
   String facilityId = null;
   orderedTestId = (String)filter.get(SRTFilterKeys.ORDERED_TEST_CODE);
   resultedTestId = (String)filter.get(SRTFilterKeys.RESULTED_TEST_CODE);
   facilityId = (String)filter.get(SRTFilterKeys.REPORTING_FACILITY_ID);
   String loincCd = null;
   loincCd = (String)filter.get(SRTFilterKeys.LOINC_CODE);
   String code = null;
   code = (String)filter.get(SRTFilterKeys.CODE);
     if(filter.size() == 2 && orderedTestId != null && facilityId != null)
       returnSet = (ArrayList<Object> )getCodesByOrderedTestNameAndFacility(orderedTestId,facilityId);
     else if(filter.size() == 2 && resultedTestId != null && facilityId != null)
       returnSet =  (ArrayList<Object> )getCodesByResultedTestNameAndFacility(resultedTestId,facilityId);
     else if(filter.size() == 1 && loincCd != null)
          returnSet = (ArrayList<Object> ) getCodesByLoincCode(loincCd);
     else if(filter.size() == 1 && code != null)
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
  * Static method to validate input filter
  * @param filter
  * @return
  */
 public static boolean validateFilterKeys(Map<Object,Object> filter)
 {
    if(filter == null)
      return false;

    if(filter.size() > 2)
          return false;

    if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.ORDERED_TEST_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      return true;
    else if(filter.size() == 2 && filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE) && filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
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
  * Given ordered test and reporting facility, returns collection of DT's
  * @param orderedTestId
  * @param facilityId
  * @return
  */
private Collection<Object>  getCodesByOrderedTestNameAndFacility(String orderedTestId,String facilityId)
{
  ArrayList<Object> dtList = null;
  HashMap<Object,Object> otMap = null;
  if(cachedFacilityOrderedTestList != null)
  {
    //System.out.println("Return list based on filter ordered_test");
    otMap = (HashMap<Object,Object>) cachedFacilityOrderedTestList.get(facilityId);
    if (otMap != null)
      dtList = (ArrayList<Object> ) otMap.get(orderedTestId);
  }

 return dtList;
}
/**
 * For a given Resulted Test and Reporting facility, returns collection of DT's
 * @param resultedTestCode
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