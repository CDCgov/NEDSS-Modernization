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
import java.util.HashMap;
import gov.cdc.nedss.systemservice.exception.InvalidSRTFilterKeysException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCodeValueGenDT;

/**
 * <p>Title: SRTCacheAnatomicSiteHelper(Part of SRT Filtering Requirements 1.1.3)</p>
 * <p>Description:
 * SRTCacheAnatomicSiteHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedCodes(SRTCodeValueGenDT). SRTCodeValueGenDT for each code
 * - Hashmaps
 *         cachedFacilityOrderedTestList(Key:Reporting Facility,Value:HashMap(Key:OrderedTestCode,value:ArrayList(SRTLabTestDT)))
 *         cachedFacilityResultedTestList(key:ReportingFacility,Value:HashMap(Key:ResultedTestCode,value:ArrayList(SRTLabTestDT)))
 *
 *
 * Method getAnatomicSites(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.ORDERED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.ORDERED_TEST_CODE,"T-11320");
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getAnatomicSites(p0);
 *
 *         2. SRTFilterKeys.RESULTED_TEST_CODE
 *            SRTFilterKeys.REPORTING_FACILITY_ID

 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *

 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Raghu Komanduri
 * @version 1.0
 */

public class SRTCacheAnatomicSiteHelper {
  static final LogUtils logger = new LogUtils(SRTCacheAnatomicSiteHelper.class.getName());

  private static HashMap<Object,Object> cachedFacilityOrderedTestList = null;
  private static HashMap<Object,Object> cachedFacilityResultedTestList = null;
  private static ArrayList<Object> cachedCodes = null;
  private static HashMap<Object,Object> cachedLoincList = null;

  public static final String ORDERED_TEST = "O";
  public static final String RESULTED_TEST = "R";

  private static SRTCacheAnatomicSiteHelper SRTSingleton = new SRTCacheAnatomicSiteHelper();
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  /**
   * Constructor
   * loads the cache
   */
  public SRTCacheAnatomicSiteHelper() {
    if(cachedCodes == null)
    {
      //System.out.println("Will load cache");
      loadCache();
    }

  }
  /**
   *
   * @return a single instance of the class
   */
  public static SRTCacheAnatomicSiteHelper getInstance() {
   return SRTSingleton;
  }

  /**
   * loads the cache
   * @return
   */
  private ArrayList<Object> loadCache() {
    if (cachedCodes == null) {
          //System.out.println("Will load cachedcodes");
      cachedCodes = getAnatomicSiteCollection();
      return cachedCodes;
    }
    else {
        return cachedCodes;
    }
  }

  /**
   * Makes DAO calls and delegates populating cache to corresponding methods
   * @return
   */
  private ArrayList<Object> getAnatomicSiteCollection() {
    //System.out.println("entering getAnatomicSiteCollection");

    if (cachedCodes != null) {
      return cachedCodes;
    }



    cachedCodes = new ArrayList<Object> ();
    SRTCodeValueGenDAOImpl dao = new SRTCodeValueGenDAOImpl();
    cachedCodes = (ArrayList<Object> ) dao.convertToCodeValueGenDT(dao.getAllAnatomicSiteData());

    buildCachedLoincList();
    String testTypeCd = null;
    String testId = null;
    String facilityId = null;

    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_SITE_MAPPING);
    if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
    {
      Collection<Object>  anatomicMapData = (ArrayList<Object> ) dao.getAnatomicSiteData();

      //System.out.println("Size of collection from DB=" + anatomicMapData.size());
      cachedFacilityOrderedTestList = new HashMap<Object,Object>();
      cachedFacilityResultedTestList = new HashMap<Object,Object>();

      if (anatomicMapData != null) {
       Iterator<Object>  it = anatomicMapData.iterator();
        int size = anatomicMapData.size();
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

  /**
   * populates cachedFacilityOrderedTestList hashmap
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
   * populates cachedFacilityResultedTestList hashmap
   * @param facilityId
   * @param testId
   * @param codeDt
   */
  private void addToCachedResultedTestList(String facilityId,String testId,SRTCodeValueGenDT codeDt)
  {
    ArrayList<Object> rtArrayList=null;
    HashMap<Object,Object> rtMap = null;
    if(cachedFacilityResultedTestList  != null)
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
   * public access point
   * @param filter
   * @return collection of DT's for given filters or return the whole set
   * @throws NEDSSSystemException
   * @throws InvalidSRTFilterKeysException
   */
 public Collection<Object>  getAnatomicSites(Map<Object,Object> filter) throws
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
          throw new InvalidSRTFilterKeysException();


  //Return the whole set if filter key is not found or map size is 0
  if(returnSet == null || filter.size() == 0)
    return cachedCodes;
  else
    return returnSet;
}
/**
 * Static method to validate the filter combinations
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
 /**
  * Given Ordered Test Code and Reporting Facility, returns COllection of DT's from the cache
  * @param orderedTestId
  * @param facilityId
  * @return
  */
private Collection<?>  getCodesByOrderedTestNameAndFacility(String orderedTestId,String facilityId)
{
  ArrayList<?> dtList = null;
  HashMap<?,?> otMap = null;
  //System.out.println("Return list based on filter ordered_test");
  if(cachedFacilityOrderedTestList != null)
  {
    otMap = (HashMap<?,?>)cachedFacilityOrderedTestList.get(facilityId);
    if(otMap != null)
      dtList = (ArrayList<?> ) otMap.get(orderedTestId);


  }
  return dtList;
}
  private void buildCachedLoincList()
  {
    ArrayList<Object> list = null;
    String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LOINC_SITE_MAPPING);
    if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
    {

      SRTCodeValueGenDAOImpl dao = new SRTCodeValueGenDAOImpl();
      list = (ArrayList<Object> ) dao.getLoincAnatomicSiteData();
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


  private ArrayList<?> getCodesByLoincCode(String loincCd) {
    ArrayList<?> dtList = null;
    if(cachedLoincList != null)
      dtList = (ArrayList<?> ) cachedLoincList.get(loincCd);

    return dtList;
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
     * Given Resulted Test Code and Reporting Facility, returns collection of DT's from the cache
     * @param resultedTestCode
     * @param facilityId
     * @return
     */

  private Collection<?>  getCodesByResultedTestNameAndFacility(String resultedTestCode,String facilityId)
{
  ArrayList<?> dtList = null;
  HashMap<?,?> rtMap = null;

  if(cachedFacilityResultedTestList != null)
  {
  //System.out.println("Return list based on filter resulted_test");
  rtMap = (HashMap<?,?>)cachedFacilityResultedTestList.get(facilityId);
  if(rtMap != null)
    dtList = (ArrayList<?> ) rtMap.get(resultedTestCode);
  }
  return dtList;

}



}