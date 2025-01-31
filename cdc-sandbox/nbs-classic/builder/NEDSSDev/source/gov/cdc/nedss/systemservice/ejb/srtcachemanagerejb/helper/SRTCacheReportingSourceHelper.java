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
 * <p>Title: SRTCacheReportingSourceHelper(Part of SRT Filtering Requirements 1.1.3</p>
 * <p>Description:
 * * SRTCacheReportingSourceHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedCodes(SRTCodeValueGenDT). SRTCodeValueGenDT for each code
 * - Hashmaps
 *      cachedConditionCdList(Key:ConditionCode,Value: ArrayList(SRTCodeValueGenDT))
 *      cachedProgAreaCdList(Key:ProgramAreaCode,Value: ArrayList(SRTCodeValueGenDT))
 *
 *
 * Method getInvestigationReportingSource(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.CONDITION_CODE
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.CONDITION_CODE,"11320");
 *              Collection<Object>  coll = sRTCacheManager.getInvestigationReportingSource(p0);
 *
 *         2. SRTFilterKeys.PROGRAM_AREA_CODE
 *
 * Method ValidateFilterKeys(HashMap<Object,Object> filter) is a static method that validates the filter
 *
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences</p>
 * @author Raghu Komanduri
 * @version 1.0
 */


public class SRTCacheReportingSourceHelper {
  static final LogUtils logger = new LogUtils(SRTCacheReportingSourceHelper.class.getName());

   private static HashMap<Object,Object> cachedConditionCdList = null;
   private static HashMap<Object,Object> cachedProgAreaCdList = null;
   private static ArrayList<Object> cachedCodes = null;


   private static SRTCacheReportingSourceHelper SRTSingleton = new SRTCacheReportingSourceHelper();
   private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
   /**
    * private constructor. Loads cache upon first instantiation
    */
   private SRTCacheReportingSourceHelper() {
     if(cachedCodes == null)
       loadCache();
   }

   /**
    *
    * @return single instance of the class
    */
   public static SRTCacheReportingSourceHelper getInstance()
   {
     return SRTSingleton;
   }

   /**
    * Loads cache upon first call. Subsequent calls use cache
    * @return
    */
   private ArrayList<Object> loadCache() {
     if (cachedCodes == null) {
       cachedCodes = getReportingSourceCollection();
       return cachedCodes;
     }
     else {
         return cachedCodes;
     }
   }
   /**
    * Makes Dao calls and delegates population of cache to the corresponding methods
    * @return
    */
   private ArrayList<Object> getReportingSourceCollection() {

     if (cachedCodes != null) {
       return cachedCodes;
     }

     cachedCodes = new ArrayList<Object> ();



     SRTCodeValueGenDAOImpl map = new SRTCodeValueGenDAOImpl();

     cachedCodes = (ArrayList<Object> ) (map.convertConditionToCodeValueGenDT(map.getAllReportingSourceData()));

     String conditionCd = null;
     String progAreaCd = null;
     String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.CONDITION_REPORTING_SOURCE);

     if(mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES))
     {
       cachedConditionCdList = new HashMap<Object,Object>();
       cachedProgAreaCdList = new HashMap<Object,Object>();
       Collection<Object>  condCvgMap = (ArrayList<Object> ) map.getReportingSourceData();
       if (condCvgMap != null) {
        Iterator<Object>  it = condCvgMap.iterator();
         int size = condCvgMap.size();
         while (it.hasNext()) {

           SRTConditionCodeValueGenRootDT rootDt = (
               SRTConditionCodeValueGenRootDT) it.next();

          Iterator<Object>  iter = cachedCodes.iterator();
           SRTCodeValueGenDT codeDt = null;
           while (iter.hasNext()) {
             codeDt = (SRTCodeValueGenDT) iter.next();
             if (codeDt.getCode().equalsIgnoreCase(rootDt.getCode()))
               break;
           }
           conditionCd = rootDt.getConditionCd();
           progAreaCd = rootDt.getProgAreaCd();
           if (conditionCd != null)
             addToCachedConditionCdList(conditionCd, codeDt);
           if (progAreaCd != null)
             addToCachedProgAreaCdList(progAreaCd, codeDt);
         }
       }
     }


      return cachedCodes;
   }
   /**
    * Loads hashmap cachedConditionCdList
    * @param conditionCd
    * @param codeDt
    */
   private void addToCachedConditionCdList(String conditionCd,SRTCodeValueGenDT codeDt)
   {
     ArrayList<Object> ccArrayList=null;
     if(cachedConditionCdList != null)
     {
       ccArrayList = (ArrayList<Object> ) cachedConditionCdList.get(conditionCd);
       if (ccArrayList == null) {
         ccArrayList = new ArrayList<Object> ();
         ccArrayList.add(codeDt);
         cachedConditionCdList.put(conditionCd, ccArrayList);
       }
       else {
         ccArrayList.add(codeDt);
       }
     }

   }
   /**
    * Loads hashmap cachedProgAreaCdList
    * @param progAreaCd
    * @param codeDt
    */
   private void addToCachedProgAreaCdList(String progAreaCd,SRTCodeValueGenDT codeDt)
   {
     ArrayList<Object> paArrayList=null;
     if(cachedProgAreaCdList != null)
     {
       paArrayList = (ArrayList<Object> ) cachedProgAreaCdList.get(progAreaCd);
       if (paArrayList == null) {
         paArrayList = new ArrayList<Object> ();
         paArrayList.add(codeDt);
         cachedProgAreaCdList.put(progAreaCd, paArrayList);
       }
       else {
         paArrayList.add(codeDt);
       }
     }

   }

   /**
    * Public access point
    * For the given filter, returns collection of DT's from the cache. If filter value is
    * not  in the cache, returns the whole set
    * @param filter
    * @return
    * @throws NEDSSSystemException
    * @throws InvalidSRTFilterKeysException
    */

  public Collection<Object>  getInvestigationReportingSource(Map<Object,Object> filter) throws
      NEDSSSystemException , InvalidSRTFilterKeysException {



   ArrayList<Object> returnSet = null;
   String conditionCd = null;
   String progAreaCd = null;

   conditionCd = (String)filter.get(SRTFilterKeys.CONDITION_CODE);
   progAreaCd = (String)filter.get(SRTFilterKeys.PROGRAM_AREA_CODE);
   String code = null;
  code = (String)filter.get(SRTFilterKeys.CODE);


     if(conditionCd != null)
       returnSet = (ArrayList<Object> )getCodesByCondition(conditionCd);
     else if(progAreaCd != null)
     {
       returnSet = (ArrayList<Object> )getCodesByProgArea(progAreaCd);
     }
     else if(filter.size() == 1 && code != null)
            returnSet = (ArrayList<Object> ) getCodesByCode(code);
     else if(filter.size() != 0)
     {
       throw new NEDSSSystemException("Invalid Filter Exception");

     }
   if(returnSet == null || filter.size() == 0)
     return cachedCodes;
   else
     return returnSet;
 }
 /**
  * Static method to validate the filter key combinations
  * @param filter
  * @return
  */
 public static boolean validateFilterKeys(Map<Object,Object> filter)
 {
    if(filter == null)
      return false;

    if(filter.size() > 1)
          return false;

    if(filter.containsKey(SRTFilterKeys.CODE) || filter.containsKey(SRTFilterKeys.CONDITION_CODE) || filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE))
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
  * For a given condition code, returns collection of DT's
  * @param conditionCd
  * @return
  */
 private Collection<Object>  getCodesByCondition(String conditionCd)
 {
   ArrayList<Object> dtList = null;

   if(cachedConditionCdList != null)
     dtList = (ArrayList<Object> )cachedConditionCdList.get(conditionCd);
   return dtList;
 }
 /**
  * For a given program area code, returns collection of DT's
  * @param progAreaCd
  * @return
  */
 private Collection<Object>  getCodesByProgArea(String progAreaCd)
 {
   ArrayList<Object> dtList = null;
   if(cachedProgAreaCdList != null)
     dtList = (ArrayList<Object> )cachedProgAreaCdList.get(progAreaCd);
   return dtList;
 }

}