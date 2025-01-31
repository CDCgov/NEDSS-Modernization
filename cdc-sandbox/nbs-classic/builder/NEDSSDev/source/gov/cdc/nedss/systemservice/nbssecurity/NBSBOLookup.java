//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\NBSBOLookup.java

package gov.cdc.nedss.systemservice.nbssecurity;


import java.util.TreeMap;
import gov.cdc.nedss.util.*;

/**
 * Title:        NBSBOLookup
 * Description:  Business Object Lookup
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jim Verrelli
 * @version 1.0
 */
public class NBSBOLookup
{
   static final LogUtils logger = new LogUtils((NBSBOLookup.class).getName());//Used for logging
   public static final String ASSOCIATEINTERVENTIONVACCINERECORDS = "ASSOCIATEINTERVENTIONVACCINERECORDS";
   public static final String ORGANIZATION = "ORGANIZATION";
   public static final String PATIENT = "PATIENT";
   public static final String OBSERVATIONGENERICOBSERVATION  = "OBSERVATIONGENERICOBSERVATION";
   public static final String OBSERVATIONMORBIDITYREPORT = "OBSERVATIONMORBIDITYREPORT";
   public static final String OBSERVATIONLABREPORT = "OBSERVATIONLABREPORT";
   public static final String OBSERVATIONMORBREPORT = "OBSERVATIONMORBREPORT";
   public static final String REPORTING = "REPORTING";
   public static final String INTERVENTIONVACCINERECORD = "INTERVENTIONVACCINERECORD";
   public static final String SYSTEM = "SYSTEM";
   public static final String NOTIFICATION = "NOTIFICATION";
   public static final String CASEREPORTING = "CASEREPORTING";
   public static final String DOCUMENT = "DOCUMENT";
   public static final String INVESTIGATION = "INVESTIGATION";
   public static final String SUMMARYREPORT = "SUMMARYREPORT";
   public static final String MATERIAL = "material";
   public static final String SRT = "SRT";
   public static final String PROVIDER = "PROVIDER";
   public static final String TREATMENT = "TREATMENT";
   public static final String CT_CONTACT= "CT_CONTACT";
   public static final String QUEUES= "QUEUES";
   public static final String PUBLICQUEUES= "PUBLICQUEUES";
   
   public static final String GLOBAL= "GLOBAL";
   public static final String PLACE= "PLACE";
   public static final String INTERVIEW= "INTERVIEW";
   private static final byte SECURED_BY_JURISDICTION = 1;
   private static final byte SECURED_BY_PROGRAM_AREA = 2;
   private static byte businessObjSecurity[];
   private static int businessObjCount;

   /**
    * A TreeMap<Object,Object> of Business Object indexes.  The Key is a businessObjLookupName (the
    * constants in the class) and the value is the Business Object Index
    */
   private static TreeMap<Object,Object> businessObjIndexMap;

   /**
    * A TreeMap<Object,Object> whose keys are a businessObjLookupNames and whose values are
    * businessObjectNames.
    */
   private static TreeMap<Object,Object> businessObjNameMap;

   /**
    * A TreeMap<Object,Object> of Business Object lookup names.  The Key is a Business Object Index
    * and the value is the Business Object Lookup Name (the constants in the class)
    */
   private static TreeMap<Object,Object> businessObjLookupMap;

   static
   {
    //Initialize the class
    initNBSBOLookup();
   }

   /**
    * Returns the index of the given business object
    * @param businessObjLookupName
    * @return int
    * @roseuid 3CEAF300006F
    */
   public static int getBOIndex(String businessObjLookupName)
   {
       //logger.debug("value of boIndexMap is: " + businessObjIndexMap);
       //logger.debug("Value of teh busName is: " + businessObjLookupName );
       //logger.debug("Value of index is: " + businessObjIndexMap.get(businessObjLookupName));
       return ((Integer)businessObjIndexMap.get(businessObjLookupName.toUpperCase())).intValue();
   }

   /**
    * Returns the Name of the given business object
    * @param businessObjLookupName
    * @return String
    * @roseuid 3CEAF313003A
    */
   public static String getBOName(String businessObjLookupName)
   {
    return (String) businessObjNameMap.get(businessObjLookupName);
   }

   /**
    * @param businessObjLookupName
    * @return boolean
    * @roseuid 3CEAF32C01C7
    */
   public static boolean isSecuredByJurisdiction(String businessObjLookupName)
   {
    return ((businessObjSecurity[getBOIndex(businessObjLookupName)] & SECURED_BY_JURISDICTION) == SECURED_BY_JURISDICTION);
   }

   /**
    * @param businessObjLookupName
    * @return boolean
    * @roseuid 3CEAF3460188
    */
   public static boolean isSecuredByProgramArea(String businessObjLookupName)
   {
	   int i = businessObjSecurity[getBOIndex(businessObjLookupName)];
    return ((businessObjSecurity[getBOIndex(businessObjLookupName)] & SECURED_BY_PROGRAM_AREA) == SECURED_BY_PROGRAM_AREA);
   }

   /**
    * @param businessObjLookupName
    * @return boolean
    * @roseuid 3CEAF35702E1
    */
   public static boolean isSecuredByProgramAreaJurisdiction(String businessObjLookupName)
   {
    return ((businessObjSecurity[getBOIndex(businessObjLookupName)] & (SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION)) == (SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION));
   }

   /**
    * Initializes all the internal data structures for NBSBOLookup.  Called from the
    * Static Initializer.
    * @roseuid 3CEAF42700CD
    */
   private static void initNBSBOLookup()
   {
    // Create businessObjIndexMap
     businessObjIndexMap = new TreeMap<Object,Object>();
     businessObjIndexMap.put(SYSTEM,new Integer(0));
     businessObjIndexMap.put(PATIENT,new Integer(1));
     businessObjIndexMap.put(ORGANIZATION,new Integer(2));
     businessObjIndexMap.put(INVESTIGATION,new Integer(3));
     businessObjIndexMap.put(SUMMARYREPORT,new Integer(4));
     businessObjIndexMap.put(INTERVENTIONVACCINERECORD,new Integer(5));
     businessObjIndexMap.put(OBSERVATIONLABREPORT,new Integer(6));
     businessObjIndexMap.put(OBSERVATIONMORBIDITYREPORT,new Integer(7));
     businessObjIndexMap.put(REPORTING,new Integer(8));
     businessObjIndexMap.put(NOTIFICATION,new Integer(9));
     businessObjIndexMap.put(SRT,new Integer(10));
     businessObjIndexMap.put(PROVIDER, new Integer(11));
     businessObjIndexMap.put(TREATMENT, new Integer(12));
     businessObjIndexMap.put(CASEREPORTING,new Integer(13));
     businessObjIndexMap.put(DOCUMENT,new Integer(14));
     businessObjIndexMap.put(CT_CONTACT,new Integer(15));
     businessObjIndexMap.put(QUEUES,new Integer(16));
     businessObjIndexMap.put(GLOBAL,new Integer(17));
     businessObjIndexMap.put(PLACE,new Integer(18));
     businessObjIndexMap.put(INTERVIEW,new Integer(19));
     businessObjIndexMap.put(PUBLICQUEUES,new Integer(20));
     
     // Initialize businessObjCount
     businessObjCount = businessObjIndexMap.size();

    // Create businessObjLookupMap
     businessObjLookupMap = new TreeMap<Object,Object>();
     businessObjLookupMap.put(new Integer(0),SYSTEM);
     businessObjLookupMap.put(new Integer(1),PATIENT);
     businessObjLookupMap.put(new Integer(2),ORGANIZATION);
     businessObjLookupMap.put(new Integer(3),INVESTIGATION);
     businessObjLookupMap.put(new Integer(4),SUMMARYREPORT);
     businessObjLookupMap.put(new Integer(5),INTERVENTIONVACCINERECORD);
     businessObjLookupMap.put(new Integer(6),OBSERVATIONLABREPORT);
     businessObjLookupMap.put(new Integer(7),OBSERVATIONMORBIDITYREPORT);
     businessObjLookupMap.put(new Integer(8),REPORTING);
     businessObjLookupMap.put(new Integer(9),NOTIFICATION);
     businessObjLookupMap.put(new Integer(10),SRT);
     businessObjLookupMap.put(new Integer(11),PROVIDER);
     businessObjLookupMap.put(new Integer(12),TREATMENT);
     businessObjLookupMap.put(new Integer(13),CASEREPORTING);
     businessObjLookupMap.put(new Integer(14),DOCUMENT);
     businessObjLookupMap.put(new Integer(15),CT_CONTACT);
     businessObjLookupMap.put(new Integer(16),QUEUES);
     businessObjLookupMap.put(new Integer(17),GLOBAL);
     businessObjLookupMap.put(new Integer(18),PLACE);
     businessObjLookupMap.put(new Integer(19),INTERVIEW);
     businessObjLookupMap.put(new Integer(20),PUBLICQUEUES);
     

    // Create businessObjNameMap
     businessObjNameMap = new TreeMap<Object,Object>();
     businessObjNameMap.put(SYSTEM,"System");
     businessObjNameMap.put(PATIENT,"Patient");
     businessObjNameMap.put(ORGANIZATION,"Organization");
     businessObjNameMap.put(INVESTIGATION,"Investigation");
     businessObjNameMap.put(SUMMARYREPORT,"Summary Report");
     businessObjNameMap.put(INTERVENTIONVACCINERECORD,"Vaccination Record");
     businessObjNameMap.put(OBSERVATIONLABREPORT,"Lab Report");
     businessObjNameMap.put(OBSERVATIONMORBIDITYREPORT,"Morbidity Report");
     businessObjNameMap.put(REPORTING,"Report");
     businessObjNameMap.put(NOTIFICATION,"Notification");
     businessObjNameMap.put(SRT,"SRT");
     businessObjNameMap.put(PROVIDER,"Provider");
     businessObjNameMap.put(TREATMENT,"Treatment");
     businessObjNameMap.put(CASEREPORTING,"CaseReporting");
     businessObjNameMap.put(DOCUMENT,"Document");
     businessObjNameMap.put(CT_CONTACT,"Contact Record");
     businessObjNameMap.put(QUEUES,"Queues");
     businessObjNameMap.put(GLOBAL,"Global");
     businessObjNameMap.put(PLACE,"Place");
     businessObjNameMap.put(INTERVIEW,"Interview");
     businessObjNameMap.put(PUBLICQUEUES,"Public Queues");
     

     //Create and populate businessObjSecurity
     businessObjSecurity = new byte[businessObjCount];
     businessObjSecurity[getBOIndex(SYSTEM)] = 0;
     businessObjSecurity[getBOIndex(PATIENT)] = 0;
     businessObjSecurity[getBOIndex(ORGANIZATION)] = 0;
     businessObjSecurity[getBOIndex(INVESTIGATION)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(SUMMARYREPORT)] = SECURED_BY_PROGRAM_AREA;
     businessObjSecurity[getBOIndex(INTERVENTIONVACCINERECORD)] = 0;
     businessObjSecurity[getBOIndex(OBSERVATIONLABREPORT)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(OBSERVATIONMORBIDITYREPORT)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(REPORTING)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(NOTIFICATION)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(SRT)] = 0;
     businessObjSecurity[getBOIndex(PROVIDER)] = 0;
     businessObjSecurity[getBOIndex(TREATMENT)] = SECURED_BY_PROGRAM_AREA;
     businessObjSecurity[getBOIndex(CASEREPORTING)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(DOCUMENT)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     businessObjSecurity[getBOIndex(CT_CONTACT)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     //TODO: validate that queues need to be secured by program area and Jurisdiction
     businessObjSecurity[getBOIndex(QUEUES)] = SECURED_BY_PROGRAM_AREA + SECURED_BY_JURISDICTION;
     
     businessObjSecurity[getBOIndex(PLACE)] = 0;
     businessObjSecurity[getBOIndex(INTERVIEW)] = 0;
     businessObjSecurity[getBOIndex(GLOBAL)] = 0;
     businessObjSecurity[getBOIndex(PUBLICQUEUES)] = 0;
     
   }

   /**
    * Access method for the businessObjIndexMap property.
    * @return   the current value of the businessObjIndexMap property
    * @roseuid 3CEB0EF202ED
    */
   public static TreeMap<Object,Object> getBusinessObjIndexMap()
   {
      return businessObjIndexMap;
   }

   /**
    * Access method for the businessObjNameMap property.
    * @return   the current value of the businessObjNameMap property
    * @roseuid 3CEB0EF203AB
    */
   public static TreeMap<Object,Object> getBusinessObjNameMap()
   {
      return businessObjNameMap;
   }

   /**
    * Access method for the businessObjCount property.
    * @return   the current value of the businessObjCount property
    * @roseuid 3CEB0EF300A0
    */
   public static int getBusinessObjCount()
   {
      return businessObjCount;
   }

   /**
    * Returns the lookup name of the business object.
    * @param index
    * @return String
    * @roseuid 3CEB1D6101CE
    */
   public static String getBOLookupName(int index)
   {
    return (String)businessObjLookupMap.get(new Integer(index));
   }

   /**
    * Access method for the businessObjIndexMap property.
    * @return   the current value of the businessObjIndexMap property
    * @roseuid 3CEB20B4033A
    */
   public static TreeMap<Object,Object> getBusinessObjLookupMap()
   {
      return businessObjLookupMap;
   }

   //A main for testing
   public static void main(String [] a)
   {
      logger.setLogLevel(6);

      logger.debug("businessObjSecurity[getBOIndex(OBSERVATIONLABREPORT)]: " +businessObjSecurity[getBOIndex("OBSERVATIONLABREPORT")]);
      logger.debug("isSecuredByProgramArea: " + isSecuredByProgramArea("OBSERVATIONLABREPORT"));
      logger.debug("isSecuredByJurisdiction: " + isSecuredByJurisdiction("OBSERVATIONLABREPORT"));
   }
}
