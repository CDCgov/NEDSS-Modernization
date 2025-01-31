package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;
/**
 * <p>Title: SRTCacheTreatmentHelper(Part of SRT Filtering Requirements 1.1.3)</p>
 * <p>Description:
 * SRTCacheTreatmentHelper is a Singleton class. It caches the following upon first hit
 * - ArrayLists cachedTreatmentList(SRTTreatmentDT). SRTTreatmentDT for each code
 *              cachedTreatmentDrugsList(SRTTreatmentDrugDT). SRTTreatmentDrugDT for each code
 * - Hashmaps
 *       conditionCdTreatmentMap(Key:Condition Code,value:ArrayList(SRTTreatmentDT))
 *       progAreaTreatmentMap(Key:Program Area Code,value:ArrayList(SRTTreatmentDT))
 *  *    conditionCdTreatmentDrugMap(Key:Condition Code,value:ArrayList(SRTTreatmentDrugDT))
 *       progAreaTreatmentDrugMap(Key:Program Area Code,value:ArrayList(SRTTreatmentDrugDT))

 * Method getTreatments(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.CONDITION_CODE
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.CONDITION_CODE,"T-11320");
 *              Collection<Object>  coll = sRTCacheManager.getTreatments(p0);
 *         2. SRTFilterKeys.PROGRAM_AREA_CODE
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.PROGRAM_AREA_CODE,"T-11320");
 *              Collection<Object>  coll = sRTCacheManager.getTreatments(p0);
 *
 *  * Method getTreatmentDrugs(HashMap<Object,Object> filter) is the public method that SRTCacheManagerEJB calls
 *    User builds the hashmap using filter keys defined in SRTFilterKeys class
 *    Allowed combinations
 *         1. SRTFilterKeys.CONDITION_CODE
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.CONDITION_CODE,"T-11320");
 *              Collection<Object>  coll = sRTCacheManager.getTreatmentDrugs(p0);
 *         2. SRTFilterKeys.PROGRAM_AREA_CODE
 *            example:
 *              lets say sRTCacheManager is the remote reference to SRTCacheManagerEJB
 *              HashMap<Object,Object> p0 = new HashMap<Object,Object>();
 *              p0.put(SRTFilterKeys.PROGRAM_AREA_CODE,"T-11320");
 *              Collection<Object>  coll = sRTCacheManager.getTreatmentDrugs(p0);
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



public class SRTCacheTreatmentHelper {
  private Collection<Object>  treatmentList; //ArrayList
  private Collection<Object>  drugList; //ArrayList
  private HashMap<Object,Object> conditionCdTreatmentMap = new HashMap<Object,Object>();
  private HashMap<Object,Object> progAreaTreatmentMap = new HashMap<Object,Object>();
  private HashMap<Object,Object> conditionCdDrugMap = new HashMap<Object,Object>();
  private HashMap<Object,Object> progAreaDrugMap = new HashMap<Object,Object>();
  private static SRTCacheTreatmentHelper instance = new SRTCacheTreatmentHelper();

  public SRTCacheTreatmentHelper() {
    loadCache();
  }

  public static SRTCacheTreatmentHelper getInstance(){
  return instance;
  }

  /**
   *
   * @param filter
   * @return
   */
  public static boolean validateKeys(Map<Object,Object> filter){
  boolean validkeys = false;
  if((filter.size() == 2)&&filter.containsKey(SRTFilterKeys.CONDITION_CODE)
     &&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE)){
    validkeys = true;
  }
  else if((filter.size() == 1)&&filter.containsKey(SRTFilterKeys.CONDITION_CODE))
    validkeys = true;
  else if((filter.size() == 1)&&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE))
    validkeys = true;
  else if((filter.size() == 1)&&filter.containsKey(SRTFilterKeys.TREATMENT_CODE))
    validkeys = true;
  else if(filter.size() ==0)
    validkeys = true;

  return validkeys;
}



/**
 *
 */
  private void loadCache(){
   if( treatmentList == null){
     SRTTreatmentDAOImpl srtTreatDAO = new SRTTreatmentDAOImpl();
     treatmentList = convertToSRTTreatmentDT(srtTreatDAO.getAllTreatments());
     drugList = convertToSRTDrugDT(srtTreatDAO.getAllDrugs());

     String mapFlag = PropertyUtil.getInstance().getSRTFilterProperty(NEDSSConstants.TREATMENT_CONDITION);
     if (mapFlag != null && mapFlag.equalsIgnoreCase(NEDSSConstants.YES)) {
       //load treatmentList
       Collection<Object>  conditionCdTreatmentColl = srtTreatDAO.getTreatments();
      Iterator<Object>  treatIter = conditionCdTreatmentColl.iterator();
       while (treatIter.hasNext()) {
         SRTTreatmentConditionDT srtTreatmentConditionDT = (
             SRTTreatmentConditionDT) treatIter.next();
         //find SRTTreatmentDT from allTreatementsList
        Iterator<Object>  treatmentListIter = treatmentList.iterator();
         SRTTreatmentDT srtTreatmentDT = null;
         while (treatmentListIter.hasNext()) {
           srtTreatmentDT = (SRTTreatmentDT) treatmentListIter.next();
           if (srtTreatmentDT.getTreatmentCd().equals(srtTreatmentConditionDT.
               getTreatmentCd()))
             break;
         }

         if (srtTreatmentConditionDT.getProgAreaCd() != null) {
           //adding to progAreaTreatmentMap
           if (progAreaTreatmentMap.containsKey(srtTreatmentConditionDT.
                                                getProgAreaCd())) {
             ArrayList<Object> paTempList = (ArrayList<Object> ) progAreaTreatmentMap.get(
                 srtTreatmentConditionDT.getProgAreaCd());
             paTempList.add(srtTreatmentDT);
           }
           else {
             ArrayList<Object> paTempList = new ArrayList<Object> ();
             paTempList.add(srtTreatmentDT);
             progAreaTreatmentMap.put(srtTreatmentConditionDT.getProgAreaCd(),
                                      paTempList);
           }
         }

         if (srtTreatmentConditionDT.getConditionCd() != null) {
           //adding to conditionCdTreatmentMap
           if (conditionCdTreatmentMap.containsKey(srtTreatmentConditionDT.
               getConditionCd())) {
             ArrayList<Object> ccTempList = (ArrayList<Object> ) conditionCdTreatmentMap.get(
                 srtTreatmentConditionDT.getConditionCd());
             ccTempList.add(srtTreatmentDT);
           }
           else {
             ArrayList<Object> ccTempList = new ArrayList<Object> ();
             ccTempList.add(srtTreatmentDT);
             conditionCdTreatmentMap.put(srtTreatmentConditionDT.
                                         getConditionCd(), ccTempList);
           }
         }

       }

       //drugs

       Collection<Object>  conditionCdDrugColl = srtTreatDAO.getDrugs();
      Iterator<Object>  drugIter = conditionCdDrugColl.iterator();
       while (drugIter.hasNext()) {
         SRTTreatmentConditionDT SRTTreatmentConditionDT = (
             SRTTreatmentConditionDT) drugIter.next();
         //find SRTDrugDT from allDrugList
        Iterator<Object>  drugListIter = drugList.iterator();
         SRTTreatmentDrugDT srtTreatmentDrugDT = null;
         while (drugListIter.hasNext()) {
           srtTreatmentDrugDT = (SRTTreatmentDrugDT) drugListIter.next();
           if (srtTreatmentDrugDT.getDrugCd().equals(SRTTreatmentConditionDT.
               getTreatmentCd()))
             break;
         }

         if (SRTTreatmentConditionDT.getProgAreaCd() != null) {
           //adding to progAreaDrugMap
           if (progAreaDrugMap.containsKey(SRTTreatmentConditionDT.
                                           getProgAreaCd())) {
             ArrayList<Object> paTempList = (ArrayList<Object> ) progAreaDrugMap.get(
                 SRTTreatmentConditionDT.getProgAreaCd());
             paTempList.add(srtTreatmentDrugDT);
           }
           else {
             ArrayList<Object> paTempList = new ArrayList<Object> ();
             paTempList.add(srtTreatmentDrugDT);
             progAreaDrugMap.put(SRTTreatmentConditionDT.getProgAreaCd(),
                                 paTempList);
           }
         }

         if (SRTTreatmentConditionDT.getConditionCd() != null) {
           //adding to conditionCdDrugMap
           if (conditionCdDrugMap.containsKey(SRTTreatmentConditionDT.
                                              getConditionCd())) {
             ArrayList<Object> ccTempList = (ArrayList<Object> ) conditionCdDrugMap.get(
                 SRTTreatmentConditionDT.getConditionCd());
             ccTempList.add(srtTreatmentDrugDT);
           }
           else {
             ArrayList<Object> ccTempList = new ArrayList<Object> ();
             ccTempList.add(srtTreatmentDrugDT);
             conditionCdDrugMap.put(SRTTreatmentConditionDT.getConditionCd(),
                                    ccTempList);
           }//end else
         }//end if
       }//end while
     }//end mapFlag if
    }//end treatList=null if
   }//end method

  /**
   *
   * @param SRTTreatmentConditionDTColl
   * @return Collection
   */
  private Collection<Object>  convertToSRTDrugDT(Collection<Object> SRTTreatmentConditionDTColl){
   Iterator<Object>  iter = SRTTreatmentConditionDTColl.iterator();
    Collection<Object>  newColl = new ArrayList<Object> ();
    while(iter.hasNext()){
      SRTTreatmentDrugDT srtDrugDT = new SRTTreatmentDrugDT();
      SRTTreatmentConditionDT srtTreatmentConditionDT =(SRTTreatmentConditionDT)iter.next();
      srtDrugDT.setDrugCd(srtTreatmentConditionDT.getTreatmentCd());
      srtDrugDT.setDrugDescTxt(srtTreatmentConditionDT.getTreatmentDescTxt());
      newColl.add(srtDrugDT);
    }
    return newColl;
  }

  /**
   *
   * @param SRTTreatmentConditionDTColl
   * @return Collection
   */
  private Collection<Object>  convertToSRTTreatmentDT(Collection<Object> SRTTreatmentConditionDTColl){
   Iterator<Object>  iter = SRTTreatmentConditionDTColl.iterator();
    Collection<Object>  newColl = new ArrayList<Object> ();
    while(iter.hasNext()){
      SRTTreatmentDT srtTreatmentDT = new SRTTreatmentDT();
      SRTTreatmentConditionDT srtTreatmentConditionDT =(SRTTreatmentConditionDT)iter.next();
      srtTreatmentDT.setTreatmentCd(srtTreatmentConditionDT.getTreatmentCd());
      srtTreatmentDT.setTreatmentDescTxt(srtTreatmentConditionDT.getTreatmentDescTxt());
      newColl.add(srtTreatmentDT);
    }
    return newColl;

  }

  /**
   *
   * @param filter
   * @return Collection
   */
  public Collection<Object>  getTreatments(Map<Object,Object> filter){
    Collection<Object>  resultCollection  = new ArrayList<Object> ();

    if((filter.size() >=1)
       &&filter.containsKey(SRTFilterKeys.CONDITION_CODE)
       &&conditionCdTreatmentMap.size()!=0){
      resultCollection  =  (Collection<Object>)conditionCdTreatmentMap.get(filter.get(SRTFilterKeys.CONDITION_CODE));
    }
    else if((filter.size() >=1)
            &&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE)
            &&progAreaTreatmentMap.size()!=0){
      resultCollection  =  (Collection<Object>)progAreaTreatmentMap.get(filter.get(SRTFilterKeys.PROGRAM_AREA_CODE));
    }
    else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.TREATMENT_CODE))
      resultCollection  = (Collection<Object>)getTreatmentsByTreatmentCode((String)filter.get(SRTFilterKeys.TREATMENT_CODE));
    if(resultCollection  ==null||resultCollection.size()==0){
       resultCollection  = treatmentList;
    }
    return resultCollection;
  }

  /**
   *
   * @param filter
   * @return Collection
   */
  public Collection<Object>  getDrugs(Map<Object,Object> filter){
    Collection<Object>  resultCollection  = new ArrayList<Object> ();

    if((filter.size() >=1)
       &&filter.containsKey(SRTFilterKeys.CONDITION_CODE)
       &&conditionCdDrugMap.size()!=0){
      resultCollection  =  (Collection<Object>)conditionCdDrugMap.get(filter.get(SRTFilterKeys.CONDITION_CODE));
    }
    else if((filter.size() >=1)
            &&filter.containsKey(SRTFilterKeys.PROGRAM_AREA_CODE)
            &&progAreaDrugMap.size()!=0){
      resultCollection  =  (Collection<Object>)progAreaDrugMap.get(filter.get(SRTFilterKeys.PROGRAM_AREA_CODE));
    }
    else if(filter.size() == 1 && filter.containsKey(SRTFilterKeys.TREATMENT_CODE))
      resultCollection  = (Collection<Object>)getDrugsByTreatmentCode((String)filter.get(SRTFilterKeys.TREATMENT_CODE));
    if(resultCollection  ==null||resultCollection.size()==0){
      resultCollection  = drugList;
    }

    return resultCollection;

  }
  private Collection<Object>  getDrugsByTreatmentCode(String treatmentCode)
  {
    ArrayList<Object> dtList = null;
   Iterator<Object>  iter = drugList.iterator();
    SRTTreatmentDrugDT drugDt =null;
    while(iter.hasNext())
    {
      drugDt = (SRTTreatmentDrugDT) iter.next();
      if(drugDt.getDrugCd().equalsIgnoreCase(treatmentCode))
      {
        dtList = new ArrayList<Object> ();
        dtList.add(drugDt);
        break;
      }
    }
    return dtList;

  }
  private Collection<Object>  getTreatmentsByTreatmentCode(String treatmentCode)
 {
   ArrayList<Object> dtList = null;
  Iterator<Object>  iter = treatmentList.iterator();
   SRTTreatmentDT treatmentDt =null;
   while(iter.hasNext())
   {
     treatmentDt = (SRTTreatmentDT) iter.next();
     if(treatmentDt.getTreatmentCd().equalsIgnoreCase(treatmentCode))
     {
       dtList = new ArrayList<Object> ();
       dtList.add(treatmentDt);
       break;
     }
   }
   return dtList;

 }

}