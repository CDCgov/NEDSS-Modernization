package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;

import java.util.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;

/**
 * <p>Title: SRTCache</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class SRTCache {
  static final LogUtils logger = new LogUtils(SRTCache.class.getName());
  private static Hashtable<Object,Object> cachedLabTest = null;
  private static SRTCache SRTSingleton = null;

  private SRTCache() {
    loadCache();
  }

  private Hashtable<Object,Object> loadCache() {
    if (cachedLabTest == null) {
      cachedLabTest = getHashedResultedTest();
      return cachedLabTest;
    }
    else {
      //cachedLabTest= getHashedResultedTest();
      return cachedLabTest;
    }
  }

  public static Collection<Object>  getOrderedTestNames(String labCLIAId,
                                               String programAreaCD) throws
      NEDSSSystemException {
    if (SRTSingleton == null) {
      SRTSingleton = new SRTCache();
    }
    TreeSet<Object> list = (TreeSet<Object>) SRTSingleton.testsReturned(labCLIAId,
        programAreaCD, NEDSSConstants.ORDERED_TEST);
    return list;
  }

  public static Collection<Object>  getOrderedTestNames(String labCLIAId) throws
      NEDSSSystemException {
    if (SRTSingleton == null) {
      SRTSingleton = new SRTCache();
    }
    TreeSet<Object> list = (TreeSet<Object>) SRTSingleton.testsReturned(labCLIAId, NEDSSConstants.PROGRAM_AREA_NONE,
        NEDSSConstants.ORDERED_TEST);
    return list;
  }

  public static Collection<Object>  getResultedTestNames(String labCLIAId,
                                                String programAreaCD) throws
      NEDSSSystemException {
    if (SRTSingleton == null) {
      SRTSingleton = new SRTCache();
    }
    TreeSet<Object>list = (TreeSet<Object>) SRTSingleton.testsReturned(labCLIAId,
        programAreaCD, NEDSSConstants.RESULTED_TEST);
    return list;
  }

  public static Collection<Object>  getResultedTestNames(String labCLIAId) throws
      NEDSSSystemException {
    if (SRTSingleton == null) {
      SRTSingleton = new SRTCache();
    }
    TreeSet<Object> list = (TreeSet<Object>) SRTSingleton.testsReturned(labCLIAId, NEDSSConstants.PROGRAM_AREA_NONE,
        NEDSSConstants.RESULTED_TEST);
    return list;
  }

  private Hashtable<Object,Object> getHashedResultedTest() {

    if (cachedLabTest != null) {
      return cachedLabTest;
    }
    else {
      HashMap<Object,Object> hashmap = getHashedResultWithPANone();

      Hashtable<?,?> htOrderedWithPANone = (Hashtable<?,?>) hashmap.get(NEDSSConstants.ORDERED_TEST_LOOKUP);

      Hashtable<?,?> htResultedWithPANone = (Hashtable<?,?>) hashmap.get(NEDSSConstants.RESULTED_TEST_LOOKUP);

      cachedLabTest = new Hashtable<Object,Object>();
      SRTMapDAOImpl map = new SRTMapDAOImpl();
      String labId = null;
      String progAreaCd = null;
      Collection<Object>  coll = (ArrayList<Object> ) map.getLabTestProgAreaMapping();
      //ArrayList<Object> progAreasColl = new ArrayList<Object> ();
      ArrayList<Object> orderedColl = new ArrayList<Object> ();
      ArrayList<Object> resultedColl = new ArrayList<Object> ();
      String orderedLabTestDesc = null;
      String resultedLabTestDesc = null;
      Hashtable<Object,Object> htProgToCond = new Hashtable<Object,Object>();
      Hashtable<Object,Object> commonOrRtHt = new Hashtable<Object,Object>();
      Hashtable<Object,Object> htProgToTest = new Hashtable<Object,Object>();

      try {
        if (coll != null) {
         Iterator<Object>  it = coll.iterator();
          int size = coll.size();
          while (it.hasNext()) {

            TestResultTestFilterDT testResults = (TestResultTestFilterDT) it.
                next();
            if (!testResults.getLaboratoryId().equalsIgnoreCase(labId)) { // && testResults.getProgAreaCd().equalsIgnoreCase(progAreaCd) )
              if (labId != null) {
                if (orderedColl.size() > 0) {
                  htProgToTest.put(NEDSSConstants.ORDERED_TEST, orderedColl);
                }
                if (resultedColl.size() > 0) {
                  htProgToTest.put(NEDSSConstants.RESULTED_TEST, resultedColl);
                }
                htProgToCond.put(progAreaCd, htProgToTest);
                cachedLabTest.put(labId, htProgToCond);
                htProgToCond = new Hashtable<Object,Object>();
                htProgToTest = new Hashtable<Object,Object>();
                orderedColl = new ArrayList<Object> ();
                resultedColl = new ArrayList<Object> ();
                commonOrRtHt = new Hashtable<Object,Object>();
              }
              else {
                 logger.debug("##########This is a condition that should happen only once");
              }

              labId = testResults.getLaboratoryId();
              progAreaCd = testResults.getProgAreaCd();

              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_DBVALUE)) {
                orderedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                orderedColl.add(orderedLabTestDesc);
              }
              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_DBVALUE)) {
                resultedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                resultedColl.add(resultedLabTestDesc);
              }

            }
            else if (testResults.getLaboratoryId().equalsIgnoreCase(labId) &&
                     !testResults.getProgAreaCd().equalsIgnoreCase(progAreaCd)) {

              if (orderedColl.size() > 0) {
                htProgToTest.put(NEDSSConstants.ORDERED_TEST, orderedColl);
              }
              if (resultedColl.size() > 0) {
                htProgToTest.put(NEDSSConstants.RESULTED_TEST, resultedColl);

              }
              htProgToCond.put(progAreaCd, htProgToTest);
              htProgToTest = new Hashtable<Object,Object>();
              orderedColl = new ArrayList<Object> ();
              resultedColl = new ArrayList<Object> ();

              progAreaCd = testResults.getProgAreaCd();
              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_DBVALUE)) {
                orderedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                orderedColl.add(orderedLabTestDesc);
              }
              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_DBVALUE)) {
                resultedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                resultedColl.add(resultedLabTestDesc);
              }

            }
            else if (testResults.getLaboratoryId().equalsIgnoreCase(labId) &&
                     testResults.getProgAreaCd().equalsIgnoreCase(progAreaCd)) {

              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_DBVALUE)) {
                orderedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                orderedColl.add(orderedLabTestDesc);
              }
              if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_DBVALUE)) {
                resultedLabTestDesc = testResults.getLabTestDescTxt() +
                    (testResults.getConditionDescTxt() == null ? "" :
                     (NEDSSConstants.CACHED_TESTNAME_DELIMITER + testResults.getConditionDescTxt()));
                resultedColl.add(resultedLabTestDesc);
              }
            }

            if (!it.hasNext()) {
              if (orderedColl.size() > 0) {
                htProgToTest.put(NEDSSConstants.ORDERED_TEST, orderedColl);
              }
              if (resultedColl.size() > 0) {
                htProgToTest.put(NEDSSConstants.RESULTED_TEST, resultedColl);

              }
              htProgToCond.put(progAreaCd, htProgToTest);
              cachedLabTest.put(labId, htProgToCond);
              htProgToTest = null;
              htProgToCond = null;

            }

          }

          /**
          try {

            Hashtable newHashtable = new Hashtable();
            newHashtable.putAll(cachedLabTest);
            ArrayList<Object> collections = new ArrayList<Object> ();
            htProgToTest = new Hashtable();
            //Hashtable htProgToResTest = new Hashtable();
            htProgToCond = new Hashtable();
            if (htOrderedWithPANone.keys() != null ||
                htOrderedWithPANone.keys().hasMoreElements()) {
              Enumeration keys = htOrderedWithPANone.keys();
              while (keys.hasMoreElements()) {
                String keyLabId = (String) keys.nextElement();
                ArrayList<Object> result = new ArrayList<Object> ();
                if(newHashtable.get(keyLabId) != null)
                {
                  ArrayList<Object> resultIt = new ArrayList<Object> ();
                  Hashtable programAreaHt = (Hashtable) newHashtable.get(
                        keyLabId);

                    Hashtable otToOrderedTest = null;
                    Enumeration keysElement = programAreaHt.keys();
                    while (keysElement.hasMoreElements()) {
                      String paKey = (String) keysElement.nextElement();
                      if( programAreaHt.get(paKey)!= null)
                      {

                        otToOrderedTest = (Hashtable) programAreaHt.get(paKey);
                        if (otToOrderedTest.get(NEDSSConstants.ORDERED_TEST) != null) {
                          resultIt = (ArrayList<Object> ) otToOrderedTest.get(
                              NEDSSConstants.ORDERED_TEST);
                          result.addAll(resultIt);

                        }
                      }
                    }
                }


                collections = (ArrayList<Object> ) htOrderedWithPANone.get(keyLabId);
                collections.addAll(result);
              }
            }
            if (htResultedWithPANone.keys() != null ||
                htResultedWithPANone.keys().hasMoreElements()) {
              Enumeration keys = htResultedWithPANone.keys();
              htProgToTest = new Hashtable();
              htProgToCond = new Hashtable();

              while (keys.hasMoreElements()) {
                String keyLabId = (String) keys.nextElement();
                ArrayList<Object> result = new ArrayList<Object> ();
                if(newHashtable.get(keyLabId) != null)
                {
                  ArrayList<Object> resultIt = new ArrayList<Object> ();
                  Hashtable programAreaHt = (Hashtable)newHashtable.get(keyLabId);
                  Hashtable otToOrderedTest = null;
                  Enumeration keysElement = programAreaHt.keys();
                   while (keysElement.hasMoreElements()) {
                     String paKey = (String) keysElement.nextElement();
                     if (programAreaHt.get(paKey) != null) {
                       otToOrderedTest = (Hashtable) programAreaHt.get(paKey);
                       if (otToOrderedTest.get(NEDSSConstants.RESULTED_TEST) != null) {
                         resultIt = (ArrayList<Object> ) otToOrderedTest.get(NEDSSConstants.RESULTED_TEST);
                         result.addAll(resultIt);
                       }
                     }
                   }
                  }

                ArrayList<Object> collectionResults = (ArrayList<Object> ) htResultedWithPANone.get(keyLabId);
                collections.addAll(result);
                htProgToTest.put(NEDSSConstants.ORDERED_TEST, collections);
                htProgToTest.put(NEDSSConstants.RESULTED_TEST, collectionResults);
                htProgToCond = (Hashtable)cachedLabTest.get(keyLabId);
                htProgToCond.put(NEDSSConstants.PROGRAM_AREA_NONE, htProgToTest);

                cachedLabTest.put(keyLabId, htProgToCond);
              }
            }
          }
          catch (Exception ex) {
            logger.error("Nullpointer has been caught here:" + ex);

          }*/
        }
      }
      catch (Exception ex1) {
        logger.error("Nullpointer has been caught here in the outerloop :" +
                     ex1);
      }

      try {
        java.io.FileOutputStream fout = new java.io.FileOutputStream(
            "testName.ser");
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fout);
        oos.writeObject(cachedLabTest);
        oos.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      return cachedLabTest;
    }
  }

  private HashMap<Object,Object> getHashedResultWithPANone() {
    HashMap<Object,Object> map = new HashMap<Object,Object>();
    Hashtable<Object,Object> cachedNoneOrdPaHt = new Hashtable<Object,Object>();
    Hashtable<Object,Object> cachedNoneResPaHt = new Hashtable<Object,Object>();
    //to get data for OrderedTests
    SRTMapDAOImpl srtMap = new SRTMapDAOImpl();
    ArrayList<Object> orderedList = (ArrayList<Object> ) srtMap.getLabTestNotMappedToPAMapping();
    String progAreaCd = NEDSSConstants.PROGRAM_AREA_NONE;
    //ArrayList<Object> progAreasColl = new ArrayList<Object> ();
    ArrayList<Object> orderedColl = new ArrayList<Object> ();
    ArrayList<Object> resultedColl = new ArrayList<Object> ();
    String orderedLabTestDesc = null;
    String resultedLabTestDesc = null;
   Iterator<Object>  iterator = orderedList.iterator();
    String labId = null;

    while (iterator.hasNext()) {

      TestResultTestFilterDT testResults = (TestResultTestFilterDT) iterator.
          next();
      if (!testResults.getLaboratoryId().equalsIgnoreCase(labId)) { // && testResults.getProgAreaCd().equalsIgnoreCase(progAreaCd) )
        if (labId != null) {
          if (orderedColl.size() > 0) {
            cachedNoneOrdPaHt.put(labId, orderedColl);
            orderedColl = new ArrayList<Object> ();
          }
          if (resultedColl.size() > 0) {
            cachedNoneResPaHt.put(labId, resultedColl);
            resultedColl = new ArrayList<Object> ();
          }
        }
        else {
          logger.debug(
              "##########This is a condition that should happen only once");
        }

        labId = testResults.getLaboratoryId();

        if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_DBVALUE)) {
          orderedLabTestDesc = testResults.getLabTestDescTxt();
          orderedColl.add(orderedLabTestDesc);
        }
        if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_DBVALUE)) {
          resultedLabTestDesc = testResults.getLabTestDescTxt();
          resultedColl.add(resultedLabTestDesc);
        }

      }
      else if (testResults.getLaboratoryId().equalsIgnoreCase(labId)) {
        if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_DBVALUE)) {
          orderedLabTestDesc = testResults.getLabTestDescTxt();
          orderedColl.add(orderedLabTestDesc);
        }
        if (testResults.getTestTypeCd().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_DBVALUE)) {
          resultedLabTestDesc = testResults.getLabTestDescTxt();
          resultedColl.add(resultedLabTestDesc);
        }

      }

      if (!iterator.hasNext()) {
        if (orderedColl.size() > 0) {
          cachedNoneOrdPaHt.put(labId, orderedColl);
        }
        if (resultedColl.size() > 0) {
          cachedNoneResPaHt.put(labId, resultedColl);
        }
      }
    }

    map.put(NEDSSConstants.ORDERED_TEST_LOOKUP, cachedNoneOrdPaHt);

    Object test = cachedNoneOrdPaHt.get(NEDSSConstants.DEFAULT);

    map.put(NEDSSConstants.RESULTED_TEST_LOOKUP, cachedNoneResPaHt);
    return map;
  }

  private static Collection<Object>  testsReturned(String labCLIAid,
                                          String programAreaCD, String type) {

    Hashtable<?,?> progAreaHt = null;
    if(labCLIAid == null)
      labCLIAid = NEDSSConstants.DEFAULT;
    else if(cachedLabTest.get(labCLIAid) == null)
    {
      labCLIAid = NEDSSConstants.DEFAULT;
    }
    progAreaHt = (Hashtable<?,?>) cachedLabTest.get(labCLIAid);
    Hashtable<?,?> totalTest = new Hashtable<Object,Object>();
    ArrayList<Object> result = new ArrayList<Object> ();
    if(!programAreaCD.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE))
    {
      //System.out.println("The progArea is not null :" +  programAreaCD);
      totalTest = (Hashtable<?,?>) progAreaHt.get(programAreaCD);
      result = (ArrayList<Object> ) totalTest.get(type);
    }
    else
    {
      Enumeration<?>  progAreaKeys = progAreaHt.keys();
      while(progAreaKeys.hasMoreElements())
      {
        String progArea = progAreaKeys.nextElement().toString();
        //System.out.println("The progArea is not null :" +  progArea + "\n:type :" + type);
        totalTest = (Hashtable<?,?>)progAreaHt.get(progArea);
        ArrayList<?> noProgAreaList = (ArrayList<?> )totalTest.get(type);
        if(noProgAreaList!= null)
          result.addAll(noProgAreaList);
      }
    }


    /**Enumeration enum1 = cachedLabTest.keys();
    while (enum1.hasMoreElements()) {
      Object obj = enum1.nextElement();
      System.out.println("enum1 is :" + obj + ":class:" + obj.getClass());
    }

    Enumeration enum2 = progAreaHt.keys();
    while (enum2.hasMoreElements()) {
      Object obj = enum2.nextElement();
      System.out.println("enum2 is :" + obj + ":class:" + obj.getClass());
    }

    Enumeration enum = totalTest.keys();
    while (enum.hasMoreElements()) {
      String key = (String)enum.nextElement();
      System.out.println("Obj is :" + key + ":class:" + key.getClass());
    }*/

    TreeSet<Object> returnSet = new TreeSet<Object>();
    if(result != null)
    {
      if(result.size()>NEDSSConstants.CACHED_DROPDOWN_END_SIZE500)
      {
        ArrayList<Object> modifiedListForSize = new ArrayList<Object>(result.subList(
            NEDSSConstants.CACHED_DROPDOWN_BEGINING_SIZE,
            NEDSSConstants.CACHED_DROPDOWN_END_SIZE500));
      }

     Iterator<Object>  it = result.iterator();
      String value = null;
      String labTestDescTxt = null;
      while(it.hasNext())
      {
        value = (String)it.next();
        //System.out.println("value is :" + value);

        if(value.indexOf(NEDSSConstants.CACHED_TESTNAME_DELIMITER)>0)
        {
          labTestDescTxt = value.substring(0,
              value.indexOf(NEDSSConstants.CACHED_TESTNAME_DELIMITER));
          if (labTestDescTxt != null)
            labTestDescTxt.trim();
        }
        else
          labTestDescTxt = value;


       //System.out.println("The labTestDescTxt is :" + labTestDescTxt);

        returnSet.add(labTestDescTxt);
      }
    }

    //System.out.println("The values returned are labCLIAid:"+labCLIAid +":programAreaCD:"+programAreaCD+":type:"+ type);
    return returnSet;
  }

}