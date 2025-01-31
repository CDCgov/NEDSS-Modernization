//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\ProgramAreaJurisdictionUtil.java

package gov.cdc.nedss.systemservice.nbssecurity;

import java.util.*;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.util.*;

/**
 * The ProgramAreaJurisdictionUtil provides methods for obtaining program areas
 * and jurisdictions.  It also provides methods for  creating Hash Code values
 * from Program Area Jurisdiction combinations.  All the methods of this class are
 * static.  There should never be a need to create an instance of this class, but
 * it is important that the initialize method be called at system startup.
 */
public class ProgramAreaJurisdictionUtil
{
//For testing
static
{
initializeProgramAreaJurisdictionUtil();
}

    static final LogUtils logger = new LogUtils (ProgramAreaJurisdictionUtil.class.getName());

    /**
     * Constant for Shared = true
     */
     public static final String SHAREDISTRUE = "T";

     /**
      * Constant for Shared = false
      */
      public static final String SHAREDISFALSE = "F";

   /**
    * Constant used to denote ALL jurisdictions.
    */
   public static final String ALL_JURISDICTIONS = "ALL";

   /**
    * Constant used to denote ANY jurisdiction.  Used when it doesn't matter what
    * jurisdiction applies.
    */
   public static final String ANY_JURISDICTION = "ANY";

   /**
    * Constant used to denote ANY program area.  Used when it doesn't matter what
    * program area applies.
    */
   public static final String ANY_PROGRAM_AREA = "ANY";

   /**
    * Constant used to denote program area set to NONE.  Used when program area
    * isn't to be assigned and derivation should be attempted.
    */
   public static final String PROGRAM_AREA_NONE = "NONE";

   /**
    * Constant used to denote jurisdiction set to NONE.  Used when jurisdiction
    * isn't to be assigned and derivation should be attempted.
    */
   public static final String JURISDICTION_NONE = "NONE";

   /**
    * This TreeMap<Object,Object> holds the list of Program Areas available.
    */
   private static TreeMap<Object, Object> programAreaMap;

   /**
    * This TreeMap<Object,Object> holds the list of available Jurisdictions available.
    */
   private static TreeMap<Object, Object> jurisdictionMap;

   /**
    * A TreeMap<Object,Object> consisting of programAreaCodes as the key set and numericIDs as the
    * values.  These numericIDs are unique and are assigned to the program areas at
    * the time they are created.  Once assigned, the value must not change as it is
    * used to create a hashcode for accessing data for the program area.
    */
   private static TreeMap<Object,Object> programAreaNumericIDMap;

   /**
    * This is a TreeMap<Object,Object> consisting of jurisdictionCodes for the key set and
    * numericIds for the values.  The numericIDs are assigned at the time each
    * jurisdiction is created and should not be changed.  They are used to create
    * hashcodes for accessing data for the jurisdiction.
    */
   private static TreeMap<Object, Object> jurisdictionNumericIDMap;

   /**
    * This function returns a hash value for the given program area / jurisdiction
    * combination.  This value is guaranteed unique and consistant as long as the
    * program area and jurisdiction codes do not change.
    * @param programAreaCode
    * @param jurisdictionCode
    * @return long
    * @roseuid 3CDB54150140
    */
   public static long getPAJHash(String programAreaCode, String jurisdictionCode)
   {
        long hashCode = 0;
        
        if(!((programAreaCode==null || programAreaCode.isEmpty()) || (jurisdictionCode==null || jurisdictionCode.isEmpty()))){
	        try
	        {
	//            logger.debug("programAreaCode: " + programAreaCode);
	//            logger.debug("programAreaNumericID = " + programAreaNumericIDMap.get(programAreaCode).toString());
	            Integer programAreaNumericID = new Integer(programAreaNumericIDMap.get(programAreaCode).toString());
	//            logger.debug("jurisdictionCode: " + jurisdictionCode);
	//            logger.debug("jurisdictionNumericID = " + jurisdictionNumericIDMap.get(jurisdictionCode).toString());
	            Integer jurisdictionNumericID = new Integer(jurisdictionNumericIDMap.get(jurisdictionCode).toString());
	            hashCode = (jurisdictionNumericID.longValue() * 100000L) + programAreaNumericID.longValue();
	        }
	        catch(Exception e)
	        {
	            logger.debug("getPAJHash : " + e);
	            e.printStackTrace();
	        }
        }
        return hashCode;
   }

   /**
    * Returns a TreeMap<Object,Object> of all the Program  Areas.
    * @return TreeMap
    * @roseuid 3CE113980314
    */
   public static TreeMap<Object, Object> getProgramAreas()
   {
    return programAreaMap;
   }

   /**
    * Returns a TreeMap<Object,Object> of all the Jurisdictions
    * @return TreeMap
    * @roseuid 3CE113CB005B
    */
   public static TreeMap<Object, Object> getJurisdictions()
   {
    return jurisdictionMap;
   }

   /**
    * Returns a collection of Long objects representing the list of Hash Codes for
    * the given program area and jurisdiction.  This will usually be a single hash
    * code, but when the jurisdiction code is ALL, a hash code for each jurisdiction
    * in the jurisdictionMap is created.
    * @param programAreaCode
    * @param jurisdictionCode
    * @return Collection
    * @roseuid 3CE115FC0260
    */
   public static Collection<Object> getPAJHashList(String programAreaCode, String jurisdictionCode)
   {
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        if(jurisdictionCode.equals(ALL_JURISDICTIONS))
        {
		//get key set
		Set<Object> jurisdictionKeys = jurisdictionNumericIDMap.keySet();
		Iterator<Object> itrJKeys = jurisdictionKeys.iterator();
		while(itrJKeys.hasNext())
		{
		  String jCode = (String) itrJKeys.next();
            arrayList.add(new Long(getPAJHash(programAreaCode, jCode)));
          }
        }
        else
        {
            arrayList.add(new Long(getPAJHash(programAreaCode, jurisdictionCode)));
        }
        return arrayList;
   }

   /**
    * This function initializes the static attributes of the class.  It should be
    * called at system startup.
    * @param theProgramAreaMap
    * @param theJurisdictionMap
    * @roseuid 3CE4253403CA
    */
   public static void initializeProgramAreaJurisdictionUtil()
   {
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
        programAreaMap = cachedDropDownValues.getProgramAreaCodedValues();
        programAreaNumericIDMap = cachedDropDownValues.getProgramAreaNumericIDs();
        jurisdictionMap = cachedDropDownValues.getJurisdictionCodedValues();
        jurisdictionNumericIDMap = cachedDropDownValues.getJurisdictionNumericIDs();
        //createPAJMaps();
   }

   //For testing
   private static void createPAJMaps()
   {
      //programAreaMap
     programAreaMap = new TreeMap<Object, Object>();
     programAreaMap.put("STD", "");
     programAreaMap.put("HIV", "");
     programAreaMap.put("NIP", "");
     programAreaMap.put("GCD", "");
     programAreaMap.put("BMIRD", "");
     programAreaMap.put("ANY", "");

     //jurisdictionMap
     jurisdictionMap = new TreeMap<Object, Object>();
     jurisdictionMap.put("COBB", "");
     jurisdictionMap.put("ANY", "");
     jurisdictionMap.put("47003", "");

     //programAreaNumericIDMap
     programAreaNumericIDMap = new TreeMap<Object, Object>();
     programAreaNumericIDMap.put("STD", new Integer(100));
     programAreaNumericIDMap.put("HIV", new Integer(101));
     programAreaNumericIDMap.put("NIP", new Integer(102));
     programAreaNumericIDMap.put("GCD", new Integer(103));
     programAreaNumericIDMap.put("BMIRD", new Integer(104));
     programAreaNumericIDMap.put("ANY", new Integer(105));

     //jurisdictionNumericIDMap
     jurisdictionNumericIDMap = new TreeMap<Object, Object>();
     jurisdictionNumericIDMap.put("COBB", new Integer(1000));
     jurisdictionNumericIDMap.put("ANY", new Integer(1001));
     jurisdictionNumericIDMap.put("47003", new Integer(1002));

   }

   public static void main(String[] a)
   {
      logger.setLogLevel(6);
      logger.debug("getPAJHash(): " + getPAJHash("GCD", "470010"));
      logger.debug("getPAJHashList(): " + getPAJHashList("GCD", "470012"));
   }
}
