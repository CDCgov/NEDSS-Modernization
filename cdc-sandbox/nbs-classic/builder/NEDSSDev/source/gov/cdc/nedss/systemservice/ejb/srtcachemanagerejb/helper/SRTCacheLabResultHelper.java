package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;
/**
 * <p>Title: SRTCacheResultedTestHelper(Part of SRT Filtering Requirements 1.1.3)</p>
 * <p>Description:
 * SRTCacheResultedTestHelper is a Singleton class. It caches the following upon first hit
 * - ArrayList<Object> cachedLabResult(SRTLabResultDT). SRTLabResultDT for each code
 * - Hashmaps
 *       cachedLabResultMap(Key:Reporting Facility,value:ArrayList(SRTLabResultDT))
 *       cachedLabResultOrgMap(Key:Reporting Facility,value:ArrayList(SRTLabResultDT))
 *       cachedResultedTestMap(Key:Reporting Facility,Value:HashMap(Key:LabTestCdMap,value:ArrayList(SRTLabResultDT))
 *       cachedResultedTestOrgMap(Key:Reporting Facility,Value:HashMap(Key:LabTestCdMap,value:ArrayList(SRTLabResultDT))
 * Method getLabResults(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getLabResults(p0);
 *
 *         2. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.RESULTED_TEST_CODE
 *         example:
 *            String restestCode = "10054";
 *            HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *             p0.put(SRTFilterKeys.REPORTING_FACILITY_ID, facCode);
 *             p0.put(SRTFilterKeys.RESULTE_TEST_CODE, restestCode);
 *             Collection<Object>  col = sRTCacheManager.getLabResults(p0);
 *
 *  * Method getOrganisms(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.REPORTING_FACILITY_ID
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
 *              Collection<Object>  coll = sRTCacheManager.getOrganisms(p0);
 *
 *         2. SRTFilterKeys.REPORTING_FACILITY_ID
 *            SRTFilterKeys.RESULTED_TEST_CODE
 *         example:
 *            String restestCode = "10054";
 *            HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *             p0.put(SRTFilterKeys.REPORTING_FACILITY_ID, facCode);
 *             p0.put(SRTFilterKeys.RESULTE_TEST_CODE, restestCode);
 *             Collection<Object>  col = sRTCacheManager.getOrganisms(p0);

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
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao.*;
import java.util.ArrayList;



public class SRTCacheLabResultHelper {
  /*ArrayList<Object> of  LabResultDT.*/
  private ArrayList<Object>  cachedLabResult = null;//storesArrayList


  /*keys are lab_test_cd(Resulted Test) and the values are ArrayList<Object>  of
      LabResultDT. */
  private Hashtable<Object,Object> cachedResultedTestMap = new Hashtable<Object,Object>(); //facMap->resTestCdMap->cachedLabResult
  private Hashtable<Object,Object> cachedResultedTestOrgMap = new Hashtable<Object,Object>(); //facMap->resTestCdMap->cachedLabResult(organisms)
  /*stores lab_results related to facility */
  private Hashtable<Object,Object> cachedLabResultMap = new Hashtable<Object,Object>();//facMap->cachedLabResult
  private Hashtable<Object,Object> cachedLabResultOrgMap = new Hashtable<Object,Object>();//facMap->cachedLabResult(organisms)
  private static SRTCacheLabResultHelper instance = new SRTCacheLabResultHelper();

  static final LogUtils logger = new LogUtils (SRTCacheLabResultHelper.class.getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  Collection<Object>  resultCollection  = null;
  public SRTCacheLabResultHelper() {
    if (cachedLabResult == null)
        loadCache();
  }

  public static boolean validateKeys(Map<Object,Object> filter){
    boolean validkeys = false;
    if((filter.size() == 2)&&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)
       &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)){
      validkeys = true;
    }
    else if((filter.size() == 1)&&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID))
      validkeys = true;
    if((filter.size() == 2)&&filter.containsKey(SRTFilterKeys.LAB_RESULT_CODE)
       &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)){
      validkeys = true;
    }


    return validkeys;
}


  private void loadCache() {
   if (cachedLabResult == null) {
     SRTLabResultDAOImpl srtLabResultDAO = new SRTLabResultDAOImpl();
     Collection<Object> allLabResultsColl = (Collection<Object>) srtLabResultDAO.
         getAllLabResults();
     cachedLabResult = (ArrayList<Object> ) convertToLabResultDT(allLabResultsColl);
     Iterator<Object> iter = cachedLabResult.iterator();
     while (iter.hasNext()) {
       SRTLabResultDT labResultDT = (SRTLabResultDT) iter.next();
       if (labResultDT.getOrganismNameInd() == null ||
           labResultDT.getOrganismNameInd().equals("N"))
         loadLabResult(cachedLabResultMap, labResultDT);
       else
         loadLabResult(cachedLabResultOrgMap, labResultDT);
     }

     String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.
         LABTEST_LABRESULT_MAPPING);
     if (mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES)) {
       cachedResultedTestMap = new Hashtable<Object,Object>();
       Collection<Object> srtLabTestLabResultMappingColl = srtLabResultDAO.
           getLabResultsSRTLabTestLabResultMapping();
       Iterator<Object> srtLabTestLabResultMappingCollIter =
           srtLabTestLabResultMappingColl.iterator();

       while (srtLabTestLabResultMappingCollIter.hasNext()) {
         SRTLabTestLabResultMappingDT srtLabTestLabResultMappingDT = (
             SRTLabTestLabResultMappingDT)
             srtLabTestLabResultMappingCollIter.next();
         if (srtLabTestLabResultMappingDT.getLabResultOrganismNameInd() == null ||
             srtLabTestLabResultMappingDT.getLabResultOrganismNameInd().equals(
             "N")) {
           loadResultedTestMap(cachedResultedTestMap,
                               srtLabTestLabResultMappingDT);
         }
         else if (srtLabTestLabResultMappingDT.getLabResultOrganismNameInd().
                  equals(
             "Y")) {
           loadResultedTestMap(cachedResultedTestOrgMap,
                               srtLabTestLabResultMappingDT);
         }
       }
     }
   }

   }

  private void loadOrganism(){



  }

  private void loadLabResult(){



  }

  private void loadResultedTestMap(Hashtable<Object,Object> cachedResultedTestMap,SRTLabTestLabResultMappingDT srtLabTestLabResultMappingDT){
    //load cachedResultedTest
         if (srtLabTestLabResultMappingDT.getLabTestCd() != null &&
             srtLabTestLabResultMappingDT.getLaboratoryId() != null) {
           SRTLabResultDT srtLabResultDT = new SRTLabResultDT(
               srtLabTestLabResultMappingDT);

           //adding to cachedFacility
           if (cachedResultedTestMap.containsKey(
               srtLabTestLabResultMappingDT.getLaboratoryId())) {
             Map<Object,Object> labCdTempMap = (Map<Object,Object>) cachedResultedTestMap.get(
                 srtLabTestLabResultMappingDT.getLaboratoryId());
             if (labCdTempMap.containsKey(srtLabTestLabResultMappingDT.
                                          getLabTestCd())) {
               ArrayList<Object>  labCdTempList = (ArrayList<Object> ) labCdTempMap.get(
                   srtLabTestLabResultMappingDT.getLabTestCd());
               labCdTempList.add(srtLabResultDT);
             }
             else {
               ArrayList<Object>  labCdTempList = new ArrayList<Object> ();
               labCdTempList.add(srtLabResultDT);
               labCdTempMap.put(srtLabTestLabResultMappingDT.getLabTestCd(),
                                labCdTempList);
             }
           }
           else {
             ArrayList<Object>  labCdTempList = new ArrayList<Object> ();
             labCdTempList.add(srtLabResultDT);
             Hashtable<Object,Object> labCdTempMap = new Hashtable<Object,Object>();
             labCdTempMap.put(srtLabTestLabResultMappingDT.getLabTestCd(),
                              labCdTempList);
             cachedResultedTestMap.put(srtLabTestLabResultMappingDT.
                                          getLaboratoryId(),
                                          labCdTempMap);
           }
         }

  }
  public static SRTCacheLabResultHelper getInstance(){
      return instance;
    }

  private void getCachedResultedTestMap(Map<Object,Object> filter){
    Map<Object,Object> labCdMap  = (Map<Object,Object>)cachedResultedTestMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
    if(labCdMap!= null)
      resultCollection  = (Collection<Object>) labCdMap.get(filter.get(SRTFilterKeys.RESULTED_TEST_CODE));
  }

  private void getCachedResultedTestOrgMap(Map<Object,Object> filter){
  Map<Object,Object> labCdMap  = (Map<Object,Object>)cachedResultedTestOrgMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
  if(labCdMap!= null)
    resultCollection  = (Collection<Object>) labCdMap.get(filter.get(SRTFilterKeys.RESULTED_TEST_CODE));
  }

  private void getCachedLabResultMap(Map<Object,Object> filter){
     resultCollection  = (Collection<Object>) cachedLabResultMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
  }

  private void getCachedLabResultOrgMap(Map<Object,Object> filter){
   resultCollection  = (Collection<Object>) cachedLabResultOrgMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
  }
  private void getLabResultsByLabResultCodeAndReportingSource(Map<Object,Object> filter)
  {
    Collection<Object> resultSet = new ArrayList<Object> ();
    Collection<Object> labResults=null;
    labResults = (Collection<Object>) cachedLabResultMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
    String labResultCd = (String)filter.get(SRTFilterKeys.LAB_RESULT_CODE);
    if(labResults != null)
    {
      Iterator<Object> iter = labResults.iterator();
      while(iter.hasNext())
      {
        SRTLabResultDT labResultDT = (SRTLabResultDT)iter.next();
        if(labResultCd != null && labResultDT.getLabResultCd() != null && labResultDT.getLabResultCd().equalsIgnoreCase(labResultCd))
        {
          resultSet.add(labResultDT);
          break;
        }
      }
    }
    resultCollection  = resultSet;
  }
  private void getOrganismsByLabResultCodeAndReportingSource(Map<Object,Object> filter)
  {
    Collection<Object> resultSet = new ArrayList<Object> ();
    Collection<Object> labResults=null;
    labResults = (Collection<Object>) cachedLabResultOrgMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
    String labResultCd = (String)filter.get(SRTFilterKeys.LAB_RESULT_CODE);
    if(labResults != null)
    {
      Iterator<Object> iter = labResults.iterator();
      while(iter.hasNext())
      {
        SRTLabResultDT labResultDT = (SRTLabResultDT)iter.next();
        if(labResultCd != null && labResultDT.getLabResultCd() != null && labResultDT.getLabResultCd().equalsIgnoreCase(labResultCd))
        {
          resultSet.add(labResultDT);
          break;
        }
      }
    }
    resultCollection  = resultSet;
  }

  public Collection<Object> getOrganisms(Map<Object,Object> filter){
    resultCollection  = new ArrayList<Object> ();

    if((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedResultedTestOrgMap.size()!=0){
          getCachedResultedTestOrgMap(filter);
          if(resultCollection  ==null||resultCollection.size() == 0){
            filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
            getCachedResultedTestOrgMap(filter);
          }
    }
    if((filter.size() ==2)
        &&filter.containsKey(SRTFilterKeys.LAB_RESULT_CODE)
        &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
        &&cachedLabResultOrgMap.size()!=0){
      getOrganismsByLabResultCodeAndReportingSource(filter);
      if(resultCollection  ==null||resultCollection.size() == 0){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
        getCachedLabResultOrgMap(filter);
      }
}

    if(((filter.size() ==1)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedLabResultOrgMap.size()!=0)||resultCollection  ==null||resultCollection.size() == 0){
      getCachedLabResultOrgMap(filter);
      if(resultCollection  == null||resultCollection.size() == 0){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
        getCachedLabResultOrgMap(filter);
      }
    }
    if(resultCollection==null){
      resultCollection  = new ArrayList<Object> ();
    }
    return resultCollection;
  }

  public Collection<Object> getLabResults(Map<Object,Object> filter){
    resultCollection  = new ArrayList<Object> ();

    if((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedResultedTestMap.size()!=0){
          getCachedResultedTestMap(filter);
          if(resultCollection  ==null||resultCollection.size() == 0){
            filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
            getCachedResultedTestMap(filter);
          }
    }
    if(((filter.size() ==1)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedLabResultMap.size()!=0)||resultCollection  == null||resultCollection.size()==0){
      getCachedLabResultMap(filter);
      if(resultCollection  == null||resultCollection.size() == 0){
        filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
        getCachedLabResultMap(filter);
      }
    }
    if((filter.size() ==2)
            &&filter.containsKey(SRTFilterKeys.LAB_RESULT_CODE)
            &&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
            &&cachedLabResultMap.size()!=0){
          getLabResultsByLabResultCodeAndReportingSource(filter);
          if(resultCollection  ==null||resultCollection.size() == 0){
            filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
            getCachedLabResultMap(filter);
          }
    }

    if(resultCollection==null){
      resultCollection  = new ArrayList<Object> ();
    }
    return resultCollection;
  }


  private ArrayList<Object>  convertToLabResultDT(Collection<Object> resultedLabTestColl){
    Iterator<Object> iter = resultedLabTestColl.iterator();
    ArrayList<Object>  newArrayList= new ArrayList<Object> ();
    while(iter.hasNext()){
       SRTLabTestLabResultMappingDT oldDT =  (SRTLabTestLabResultMappingDT)iter.next();
       SRTLabResultDT newDT = new SRTLabResultDT();
       newDT.setLaboratoryId(oldDT.getLabResultLaboratoryId());
       newDT.setLabResultCd(oldDT.getLabResultTestCd());
       newDT.setLabResultDescTxt(oldDT.getLabResultDescTxt());
       newDT.setOrganismNameInd(oldDT.getLabResultOrganismNameInd());
       newArrayList.add(newDT);
    }
    return newArrayList;
  }

  private void loadLabResult(Map<Object,Object> cachedLabResultMap, SRTLabResultDT labResultDT){

     if(labResultDT.getLaboratoryId()!=null){
       if(cachedLabResultMap.containsKey(labResultDT.getLaboratoryId())){
           Collection<Object> tempColl = (Collection<Object>)cachedLabResultMap.get(labResultDT.getLaboratoryId());
           tempColl.add(labResultDT);
       }
       else{
         Collection<Object> tempColl = new ArrayList<Object> ();
         tempColl.add(labResultDT);
         cachedLabResultMap.put(labResultDT.getLaboratoryId(),tempColl);

       }
     }

  }


}