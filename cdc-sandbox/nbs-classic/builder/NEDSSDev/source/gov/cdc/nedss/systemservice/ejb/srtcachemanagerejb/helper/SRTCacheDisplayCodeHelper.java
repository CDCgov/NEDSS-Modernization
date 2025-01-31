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
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import gov.cdc.nedss.util.*;



public class SRTCacheDisplayCodeHelper {
  private Collection<Object>  displayCodeList; //ArrayList
  private HashMap<Object,Object> facilityIdMap = new HashMap<Object,Object>();
  private HashMap<Object,Object> facilityOnlyMap = new HashMap<Object,Object>();
  private HashMap<Object,Object> cachedLoincList = new HashMap<Object,Object>();

  private static SRTCacheDisplayCodeHelper instance = new SRTCacheDisplayCodeHelper();
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  Collection<Object>  resultCollection  = new ArrayList<Object> ();
  public SRTCacheDisplayCodeHelper() {
    loadCache();
  }

  public static SRTCacheDisplayCodeHelper getInstance(){
    return instance;
  }

  public static boolean validateKeys(Map<Object,Object> filter){
    boolean validkeys;
    if((filter.size() == 2)&&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
       &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)){
      validkeys = true;
    }
    else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.LOINC_CODE))
     return true;
    else if(filter.size() == 0)
      validkeys = true;
    else validkeys = false;
    return validkeys;
  }

  private void loadCache(){
    if (displayCodeList == null) {
      SRTDisplayCodeDAOImpl srtDisplayCodeDAOImpl = new
          SRTDisplayCodeDAOImpl();

      displayCodeList = convertToSRTDisplayCodeDTs(srtDisplayCodeDAOImpl.
          getAllDisplayCodes());
      buildCachedLoincList();
      String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LABTEST_DISPLAY_MAPPING);
      if (mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES)) {
        Collection<Object>  srtLabTestDisplayMappingDTList = srtDisplayCodeDAOImpl.
            getLabTestDisplayMappingDTs(); ;
       Iterator<Object>  srtLabTestDisplayMappingDTListIter =
            srtLabTestDisplayMappingDTList.iterator();
        while (srtLabTestDisplayMappingDTListIter.hasNext()) {
          SRTLabTestDisplayMappingDT srtLabTestDisplayMappingDT = (
              SRTLabTestDisplayMappingDT) srtLabTestDisplayMappingDTListIter.
              next();
          if (srtLabTestDisplayMappingDT.getLaboratoryId() != null &&
              srtLabTestDisplayMappingDT.getLabTestCd() != null) {
            SRTDisplayCodeDT srtNBSDisplayElementDT =
                convertToSRTNBSDisplayElementDT(srtLabTestDisplayMappingDT);

            //add to facilityIdMap
            Map<Object,Object> rtLabTestCdTempMap = null;
            if (facilityIdMap.containsKey(srtLabTestDisplayMappingDT.
                                          getLaboratoryId())) {
              rtLabTestCdTempMap = (Map) facilityIdMap.get(
                  srtLabTestDisplayMappingDT.getLaboratoryId());
            }
            else {
              rtLabTestCdTempMap = new HashMap<Object,Object>();
              facilityIdMap.put(srtLabTestDisplayMappingDT.getLaboratoryId(),
                                rtLabTestCdTempMap);
            }
            if (rtLabTestCdTempMap.containsKey(srtLabTestDisplayMappingDT.
                                               getLabTestCd())) {
              ArrayList<Object> srtNBSDisplayElementListTemp = (ArrayList<Object> )
                  rtLabTestCdTempMap.get(srtLabTestDisplayMappingDT.
                                         getLabTestCd());
              srtNBSDisplayElementListTemp.add(srtNBSDisplayElementDT);
            }
            else {
              ArrayList<Object> srtNBSDisplayElementListTemp = new ArrayList<Object> ();
              srtNBSDisplayElementListTemp.add(srtNBSDisplayElementDT);
              rtLabTestCdTempMap.put(srtLabTestDisplayMappingDT.getLabTestCd(),
                                     srtNBSDisplayElementListTemp);
            }
            //add to FacilityOnlyMap
            if (facilityOnlyMap.containsKey(srtLabTestDisplayMappingDT.
                                            getLaboratoryId())) {
              ArrayList<Object> facilityOnlyTempList = (ArrayList<Object> ) facilityOnlyMap.get(
                  srtLabTestDisplayMappingDT.getLaboratoryId());
              facilityOnlyTempList.add(srtNBSDisplayElementDT);
            }
            else {
              ArrayList<Object> facilityOnlyTempList = new ArrayList<Object> ();
              facilityOnlyTempList.add(srtNBSDisplayElementDT);
              facilityOnlyMap.put(srtLabTestDisplayMappingDT.getLaboratoryId(),
                                  facilityOnlyTempList);
            }
          } //end if
        } //end while
      } //end if
    }//end mapIf
    }//end method

    private void buildCachedLoincList()
   {
     ArrayList<Object> list = null;
     String mapFlag = propertyUtil.getSRTFilterProperty(NEDSSConstants.LOINC_DISPLAY_MAPPING);
     if (mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES)) {
       SRTDisplayCodeDAOImpl dao = new SRTDisplayCodeDAOImpl();
       list = (ArrayList<Object> ) dao.getLoincDisplayMappingDTs();
       if (list != null) {
         cachedLoincList = new HashMap<Object,Object>();
        Iterator<Object>  it = list.iterator();
         int size = list.size();
         while (it.hasNext()) {

           SRTLabTestDisplayMappingDT rootDt = (SRTLabTestDisplayMappingDT) it.
               next();
           SRTDisplayCodeDT codeDt = null;
           String loincCd = rootDt.getLoincCd();
           if (loincCd != null) {
             codeDt = convertToSRTNBSDisplayElementDT(rootDt);
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

  private SRTDisplayCodeDT convertToSRTNBSDisplayElementDT(SRTLabTestDisplayMappingDT oldDT){
    SRTDisplayCodeDT newDT = new SRTDisplayCodeDT();
    newDT.setDisplayCd(oldDT.getDisplayCd());
    return newDT;
  }

  private Collection<Object>  convertToSRTDisplayCodeDTs(Collection<Object> oldDTColl){
   Iterator<Object>  iter = oldDTColl.iterator();
    Collection<Object>  newDTColl = new ArrayList<Object> ();
    while(iter.hasNext()){
      SRTLabTestDisplayMappingDT oldDT = (SRTLabTestDisplayMappingDT)iter.next();
      SRTDisplayCodeDT newDT = new SRTDisplayCodeDT();
      newDT.setDisplayCd(oldDT.getDisplayCd());
      newDTColl.add(newDT);
    }
    return newDTColl;
  }

  public void getResultTestCdMap(Map<Object,Object> filter){

    Map<Object,Object> resultTestCdMap =  (Map<Object,Object>)facilityIdMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
     if(resultTestCdMap != null){
       resultCollection   = (Collection<Object>)resultTestCdMap.get(filter.get(SRTFilterKeys.RESULTED_TEST_CODE));
     }//end if
     else{
       filter.put(SRTFilterKeys.REPORTING_FACILITY_ID,"DEFAULT");
       Map<Object,Object> resultTestCdMapDefault =  (Map<Object,Object>)facilityIdMap.get(filter.get(SRTFilterKeys.REPORTING_FACILITY_ID));
       if(resultTestCdMapDefault != null){
         resultCollection   = (Collection<Object>)resultTestCdMapDefault.get(filter.get(SRTFilterKeys.RESULTED_TEST_CODE));
       }//end if
     }
  }

  public Collection<Object>  getNBSDisplayElements(Map<Object,Object> filter){
    String loincCd = null;
    loincCd = (String)filter.get(SRTFilterKeys.LOINC_CODE);
    resultCollection  = new ArrayList<Object> ();
    if((filter.size() >=1)&&filter.containsKey(SRTFilterKeys.REPORTING_FACILITY_ID)
       &&filter.containsKey(SRTFilterKeys.RESULTED_TEST_CODE)){
     getResultTestCdMap(filter);
    }//end else if
    else if(filter.size() == 1 && loincCd != null)
     resultCollection  = (ArrayList<Object> ) getCodesByLoincCode(loincCd);
    if(resultCollection  ==null||resultCollection.size()==0){
      resultCollection   = this.displayCodeList;
    }
    return resultCollection;
  }//end getNBSDisplayElements
  private ArrayList<Object> getCodesByLoincCode(String loincCd) {
   ArrayList<Object> dtList = null;
   if(cachedLoincList != null)
     dtList = (ArrayList<Object> ) cachedLoincList.get(loincCd);

   return dtList;
 }


}//end class