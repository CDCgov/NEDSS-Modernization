package gov.cdc.nedss.webapp.nbs.logicsheet.helper;


/**
 * Title:  CachedDropDownValues
 * Description:  Makes cached SRT codes available in Web Server
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author:  Roger Wilson and Nedss Dev. Team
 * @version 1.0
 */
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.PreDefinedTreatmentDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.rmi.PortableRemoteObject;

public class CachedDropDownValues
{

    public static final LogUtils logger = new LogUtils(CachedDropDownValues.class.getName());
    private SRTMap srtm = null;
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
    
    private static Map<Object, Object> map = new TreeMap<Object,Object>();
    private static Map<Object, Object> codeShortDescMap = new TreeMap<Object,Object>();
    private static Map<Object, Object> saicMap = new TreeMap<Object,Object>();
    private static HashMap<Object, Object> mapForDesc = new HashMap<Object, Object>();
    private static TreeMap<Object,Object> statCodeList = null;
    private static TreeMap<Object,Object> cachedConditionCodeMap = null;
    private static HashMap<Object, Object> cachedLDFMap = new HashMap<Object, Object>();
    private static TreeMap<Object,Object> cachedCountyReportingSources = null;
    private static TreeMap<Object,Object> stateCodeDescTxtTreeMap = null;
    private static TreeMap<Object,Object> stateAbbreviationsByCode = null;
    private static TreeMap<Object,Object> stateCodesByAbbreviation = null;
    private static TreeMap<Object,Object> cachedCountyCodes = null;
    private static TreeMap<Object,Object> cachedAllCountyCodesReversed = null;
    private static TreeMap<Object,Object> cachedAllCountyCodes = null;
    private static TreeMap<Object,Object> cachedNAICSIndustryCodes = null;
    private static TreeMap<Object,Object> cachedRaces = null;
    private static TreeMap<Object,Object> cachedConditionTreeMap = null;
    private static TreeMap<Object,Object> cachedResultedTestTreeMap = null;
    private static TreeMap<Object,Object> cachedCodedResultValueTreeMap = null;
    private static TreeMap<Object,Object> cachedCodedResultValueTreeMapLab = null;
    private static TreeMap<Object,Object> reportBasicPageConditionMap = null;
    private static TreeMap<Object,Object> cachedTreatmentDescription = null;
    private static TreeMap<Object,Object> cachedDiagnosisCodeMap = null;
    
    
    private static String cachedAddressType = null;
    private static String cachedLdfDropDown = null;
    private static String cachedAddressUse = null;
    private static String cachedConditionCode = null;
    private static String cachedDiagnosisCode = null;
    private static String cachedCodedResultCd = null;
    private static String cachedCodedResultSuscCd = null;
    private static String cachedResultMethodSuscCd = null;
    private static String cachedPACV = null;
    private static String cachedLanguageCode = null;
    private static String cachedIndustryCode = null;
    private static String cachedOccupationCode = null;
    private static String cachedLabtestCodes = null;
    private static String cachedSusptestCodes = null;
    private static String cachedJurisdictionCode = null;
    private static String jurisdictionCode = null; // for sec admin
    private static String cachedCountryCodes = null;
    private static String cachedBMDConditionCode = null;
    private static String cachedResultTest = null;
    private static String cachedDrugNames = null;
    private static String cachedTreatmentDesc = null;
    private static String cachedTreatmentDrug = null;
    private static String cachedOrganismList = null;
    private static StringBuffer jurisdictionUnknown = null;
    private static HashMap<Object, Object> cachedMap = new HashMap<Object, Object>();
    private static String cachedJurisdictionCodeNoExport = null;
    private static TreeMap<Object,Object> cachedContactTracingEnableIND = null;
    private static ArrayList<Object> defaultDispCntrlList = null;
    private static TreeMap<Object,Object>  programAreaTreeMapIncCond = null;
    

    
    public CachedDropDownValues()
    {
    }

    /**
     * Resets the Cache
     * @param classNm
     * @throws Exception
     */
    public static void resetSRTCache() throws Exception {

    	String classNm = CachedDropDownValues.class.getName();
    	Field fields[] = CachedDropDownValues.class.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			Field field = (Field) fields[i];
			String fieldTypeNm = field.getType().getName();
			if (fieldTypeNm.equals("java.util.Map")) {
				fields[i].set(classNm, new TreeMap<Object,Object>());
			} else if (fieldTypeNm.equals("java.util.TreeMap")) {
				fields[i].set(classNm, null);
			} else if (fieldTypeNm.equals("java.util.HashMap")) {
				fields[i].set(classNm, new HashMap<Object, Object>());
			} else if (fieldTypeNm.equals("java.lang.String")) {
				fields[i].set(classNm, null);
			} else if (fieldTypeNm.equals("java.util.ArrayList")) {
				fields[i].set(classNm, null);
			} else if (fieldTypeNm.equals("java.lang.StringBuffer")) {
				fields[i].set(classNm, null);
			}
		}
	}    
    private SRTMap getSRTMapEJBRef() throws Exception
    {

        if(srtm == null)
        {
          NedssUtils nu = new NedssUtils();
//	try
//	{

            Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);
            if (objref != null)
            {
              //logger.debug("objref " + objref);
              SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref,
                  SRTMapHome.class);
              srtm = home.create();

            }
//	}
//	catch (Exception e)
//	{
//	    e.printStackTrace();

//	}
        }
        return srtm;
    }
    private void performCaseInsensitiveSortAndFormat(TreeMap<?,?> treeMap,StringBuffer other, StringBuffer values)
    {
      TreeMap<Object,Object> filter = new TreeMap<Object,Object>();
     Iterator<?>  iter = treeMap.entrySet().iterator();
      while(iter.hasNext())
      {
        Map.Entry<?,?> map = (Map.Entry<?,?>) iter.next();
        String key = (String) map.getKey();
        String value = (String) map.getValue();
        	if(key!=null && 
        			(key.trim().equals(NEDSSConstants.HIV_SRT_CODE) || 
        					key.trim().equals(NEDSSConstants.AIDS_SRT_CODE))){
        		if(!PropertyUtil.getInstance().getHIVAidsDisplayInd().equals("Y")){
        	        		continue;
        	}		
        }
        if(value == null || value.trim().equals(""))
          continue;
        else
          filter.put(key,value);
      }
      Set<Object> valueSet = new TreeSet<Object>(filter.values());
      Iterator<Object> valueIter = valueSet.iterator();
      Set<Object> s = new TreeSet<Object>();
      while(valueIter.hasNext())
      {
        String value = null;
        String temp = (String)valueIter.next();
        if(temp != null)
        {
          value = temp.toUpperCase();
          s.add(value);
        }
      }

      Iterator<Object> it = s.iterator();

      while (it.hasNext()) {

        String sortedValue = (String) it.next();
       Iterator<?>   anIterator = treeMap.entrySet().iterator();

        while (anIterator.hasNext()) {

          Map.Entry<?,?> map = (Map.Entry<?,?>) anIterator.next();
          String mapEntryValue = null;
          if(map.getValue() != null)
              mapEntryValue = ((String) map.getValue()).toUpperCase();

          if(mapEntryValue != null)
          {
            if (mapEntryValue.equals(sortedValue)) {

              if ( ( (String) map.getValue()).equalsIgnoreCase("Other")) {
                String key = (String) map.getKey();
                String value = (String) map.getValue();
                other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
                    value.trim())
                    .append(NEDSSConstants.SRT_LINE);

              }
              else {

                String key = (String) map.getKey();
                String value = (String) map.getValue();
                values.append(key.trim()).append(NEDSSConstants.SRT_PART).
                    append(value.trim())
                    .append(NEDSSConstants.SRT_LINE);
              }
              break;
            }
          }
        }
      }


    }
    
    public ArrayList<Object> getCodedValuesList(String type)
    {
    	ArrayList<Object> valueList = new ArrayList<Object>();
    	 try {
			if (getSRTMapEJBRef() != null)
				 valueList = getSRTMapEJBRef().getCodedValuesList(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return valueList;
    }
    public String getCodedValues(String type)
    {

        StringBuffer codedValues = new StringBuffer("");
        String strMaps = (String)map.get(type);

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            //  logger.debug("Cached values for SRT called");
            return strMaps;
        }

        //   logger.debug("EJB for SRT values called");
        StringBuffer other = new StringBuffer("");
        TreeMap<Object,Object> treeMap = null;

        try
        {
          if (getSRTMapEJBRef() != null)
            treeMap = getSRTMapEJBRef().getCodedValues(type);
          if (treeMap != null && treeMap.values() != null) {
            performCaseInsensitiveSortAndFormat(treeMap,other, codedValues);

/*            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext()) {

              String sortedValue = (String) it.next();
              Iterator<Object>  anIterator = treeMap.entrySet().iterator();

              while (anIterator.hasNext()) {

                Map.Entry map = (Map.Entry) anIterator.next();

                if ( (String) map.getValue() == sortedValue) {

                  if ( ( (String) map.getValue()).equalsIgnoreCase("Other")) {
                    //logger.debug(
                    //  "Other getCodedValues Value is  :" + map.getValue() +
                    //  " and key is :" + map.getKey());

                    String key = (String) map.getKey();
                    String value = (String) map.getValue();
                    sbuff.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
                        value.trim())
                        .append(NEDSSConstants.SRT_LINE);
                  }
                  else {
                    //logger.debug(
                    //    "Value getCodedValues is  :" + map.getValue() +
                    //    " and key is :" + map.getKey());

                    String key = (String) map.getKey();
                    String value = (String) map.getValue();
                    codedValues.append(key.trim()).append(NEDSSConstants.SRT_PART).
                        append(value.trim())
                        .append(NEDSSConstants.SRT_LINE);
                  }
                }
              }
            }
 */
          }
        //logger.debug("\n\n\ncoded values :" + codedValues);
        codedValues.append(other);
        map.put(type, codedValues.toString());
    }
    catch (Exception e)
    {
         e.printStackTrace();
    }
        return codedValues.toString();
    }
    public String getLDFDropdowns()
    {

        String ldfValues = new String("");
        String strMaps = cachedLdfDropDown;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            //  logger.debug("Cached values for SRT called");
            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
          if (getSRTMapEJBRef() != null)
            treeMap = getSRTMapEJBRef().getLDFDropdowns();
          if (treeMap != null && treeMap.values() != null)
          {
            ldfValues = getLdfSrt(treeMap);
            cachedLdfDropDown = ldfValues;
          }

    }
    catch (Exception e)
    {
         e.printStackTrace();
    }
        return ldfValues;
    }
    /**
   *
   * @param treemap - stores key value information from database
   * @return the parsed treemap for xsl translation
   */
  private String getLdfSrt(TreeMap<Object,Object> treeMap) {

    final int ALLOWED_LENGTH = 85;
    StringBuffer other = new StringBuffer("");
    StringBuffer values = new StringBuffer("");
    TreeMap<Object,Object> filter = new TreeMap<Object,Object>();
    Iterator<?>   iter = treeMap.entrySet().iterator();
     while(iter.hasNext())
     {
       Map.Entry<?,?> map = (Map.Entry<?,?>) iter.next();
       String key = (String) map.getKey();
       String value = (String) map.getValue();
       if(value == null || value.trim().equals(""))
         continue;
       else
         filter.put(key,value);
     }
     Set<Object> valueSet = new TreeSet<Object>(filter.values());
     Iterator<Object> valueIter = valueSet.iterator();
     Set<Object> s = new TreeSet<Object>();
     while(valueIter.hasNext())
     {
       String value = null;
       String temp = (String)valueIter.next();
       if(temp != null)
       {
         value = temp.toUpperCase();
         s.add(value);
       }
     }

     Iterator<Object> it = s.iterator();

     while (it.hasNext()) {

       String sortedValue = (String) it.next();
      Iterator<?>   anIterator = treeMap.entrySet().iterator();

       while (anIterator.hasNext()) {

         Map.Entry<?,?> map = (Map.Entry<?,?>) anIterator.next();
         String mapEntryValue = null;
         if(map.getValue() != null)
             mapEntryValue = ((String) map.getValue()).toUpperCase();

         if(mapEntryValue != null)
         {
           if (mapEntryValue.equals(sortedValue)) {

             if ( ( (String) map.getValue()).equalsIgnoreCase("Other")) {
               String key = (String) map.getKey();
               String value = (String) map.getValue();
               value = (value.length() >= ALLOWED_LENGTH ? value.substring(0,ALLOWED_LENGTH):value);
               other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
                   value.trim())
                   .append(NEDSSConstants.SRT_LINE);

             }
             else {

               String key = (String) map.getKey();
               String value = (String) map.getValue();
               value = (value.length() >= ALLOWED_LENGTH ? value.substring(0,ALLOWED_LENGTH):value);
               values.append(key.trim()).append(NEDSSConstants.SRT_PART).
                   append(value.trim())
                   .append(NEDSSConstants.SRT_LINE);
             }
             break;
           }
         }
       }
     }
     values.append(other);



    return values.toString();
  }


    public String getSAICDefinedCodedValues(String type)
    {

      StringBuffer codedValues = new StringBuffer("");
      String strMaps = (String) saicMap.get(type);
      StringBuffer other = new StringBuffer("");
      if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH) {
        return strMaps;
      }
      else {
        TreeMap<Object,Object> treeMap = null;

        try {
          if (getSRTMapEJBRef() != null)
            treeMap = getSRTMapEJBRef().getSAICDefinedCodedValues(type);
          if (treeMap != null && treeMap.values() != null) {
            //performCaseInsensitiveSortAndFormat(treeMap, other, codedValues);
            Set<Object> s = new TreeSet<Object>(treeMap.values());
            Iterator<Object> it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<?>   anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry<?,?> map = (Map.Entry<?,?>)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                                  //  "Other Value is  :" + map.getValue() + " and key is :" +
                                   // map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                            //logger.debug(
                                //    "Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            codedValues.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                       .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }

            codedValues.append(other);
            saicMap.put(type, codedValues.toString());
            return codedValues.toString();
          }

        }
        catch (Exception e) {
          e.printStackTrace();
        }

      }
      return null;
    }

    public String getCodesForDesc(String type)
    {

        StringBuffer codedValues = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = (String)map.get(type);

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            //  logger.debug("Cached values for SRT called");
            return strMaps;
        }

        //   logger.debug("EJB for SRT values called");
        TreeMap<Object,Object> treeMap = null;

        try
        {
            if (getSRTMapEJBRef() != null)
              treeMap = getSRTMapEJBRef().getCodedValues(type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
          performCaseInsensitiveSortAndFormat(treeMap,others, codedValues);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                                  //  "Other Value is  :" + map.getValue() + " and key is :" +
                                   // map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(key.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                            //logger.debug(
                                //    "Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            codedValues.append(key.trim()).append(NEDSSConstants.SRT_PART).append(key.trim())
                                       .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        codedValues = codedValues.append(others);
        map.put(type, codedValues.toString());

        return codedValues.toString();
    }

    public String getStateCodes(String type)
    {

        StringBuffer stateCodes = new StringBuffer("");
        String strMaps = (String)map.get(type);
        StringBuffer others = new StringBuffer("");
        
        logger.debug("Getting the list of states for :"+type);
        logger.debug("State List from cache"+strMaps);
        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getStateCodes(type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,others, stateCodes);
            /*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                           // logger.debug(
                                //    "Other Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                          //  logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            stateCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                      .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
*/
            logger.debug("State List from database"+stateCodes.toString());
        }

        stateCodes.append(others);
        map.put(type, stateCodes.toString());

        return stateCodes.toString();
    }

    public String getRaceCodes(String type)
    {

        StringBuffer raceCodes = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = (String)map.get(type);

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getRaceCodes(type);

            //required for getting descs for code for race codes
            mapForDesc.put(type, treeMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {

            performCaseInsensitiveSortAndFormat(treeMap,others, raceCodes);
            /*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                           // logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            raceCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                     .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
*/
        }

        raceCodes.append(others);
        map.put(type, raceCodes.toString());

        return raceCodes.toString();
    }

    public String getRaceCodesByCodeSet(String type)
   {

       StringBuffer raceCodes = new StringBuffer("");
       StringBuffer others = new StringBuffer("");
       String strMaps = (String)map.get(type);

       if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
       {

           return strMaps;
       }

       TreeMap<Object,Object> treeMap = null;

       try
       {
            if (getSRTMapEJBRef() != null)
              treeMap = getSRTMapEJBRef().getRaceCodesByCodeSet(type);

           //required for getting descs for code for race codes
           mapForDesc.put(type, treeMap);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       if (treeMap != null)
       {
           performCaseInsensitiveSortAndFormat(treeMap,others, raceCodes);
           /*
           Set s = new TreeSet(treeMap.values());
          Iterator<Object>  it = s.iterator();

           while (it.hasNext())
           {

               String sortedValue = (String)it.next();
              Iterator<Object>  anIterator = treeMap.entrySet().iterator();

               while (anIterator.hasNext())
               {

                   Map.Entry map = (Map.Entry)anIterator.next();

                   if ((String)map.getValue() == sortedValue)
                   {

                       if (((String)map.getValue()).equalsIgnoreCase("Other"))
                       {
                          // logger.debug(
                       //	    "Other Value is  :" + map.getValue() + " and key is :" +
                               //    map.getKey());

                           String key = (String)map.getKey();
                           String value = (String)map.getValue();
                           others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                       }
                       else
                       {
                          // logger.debug(
                       //	    "Value is  :" + map.getValue() + " and key is :" +
                       //	    map.getKey());

                           String key = (String)map.getKey();
                           String value = (String)map.getValue();
                           raceCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                    .append(NEDSSConstants.SRT_LINE);
                       }
                   }
               }
           }
*/
       }

       raceCodes.append(others);
       map.put(type, raceCodes.toString());

       return raceCodes.toString();
   }


    public String getProgramAreaCd(String code)
    {

        String programAreaCd = "";
        String strMaps = (String)map.get(code);

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               programAreaCd = getSRTMapEJBRef().getProgramAreaCd(code);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //logger.debug(
                //    "NedssUtils.getProgramAreaCd Exception occured while getting programAreaCd ");

            return "";
        }

        map.put(code, programAreaCd);

        return programAreaCd;
    }

    public String getCodeDescTxt(String code)
    {

        String codeDescTxt = "";
        String strMaps = (String)map.get(code);

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               codeDescTxt = getSRTMapEJBRef().getCodeDescTxt(code);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map.put(code, codeDescTxt);

        return codeDescTxt;
    }

    public String getAddressType()
    {

        StringBuffer addressType = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = cachedAddressType;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getAddressType();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,others, addressType);
            /*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                        //    logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            addressType.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                       .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
        */
        }

        addressType.append(others);
        cachedAddressType = addressType.toString();

        return addressType.toString();
    }

    public String getAddressUse()
    {

        StringBuffer addressUse = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedAddressUse;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getAddressUse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, addressUse);
            /*

            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                          //  logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            addressUse.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                      .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
         */
        }

        addressUse.append(other);
        cachedAddressUse = addressUse.toString();

        return addressUse.toString();
    }

    /*
     *   getProgramAreaDesc
     *   returns a single Desc, based on valid ProgramAreaCd
     */

    public String getProgramAreaDesc(String cd)
    {
        TreeMap<Object,Object> treeMap = null;
        String result = "";
        Object obj = null;

        try
        {
         treeMap = this.getProgramAreaCodedValues();
         if (treeMap != null && cd != null && cd.trim().length() > 0){
          obj = treeMap.get(cd);
          if(obj != null)
          result = (String)obj;
         }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


     return result;
    }



    /*
     *   String getProgramAreaCodedValue()
     */
    public String getProgramAreaCodedValue()
    {

        StringBuffer pacv = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedPACV;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getProgramAreaCodedValues();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, pacv);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                          //  logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            pacv.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        pacv.append(other);
        cachedPACV = pacv.toString();

        return pacv.toString();
    }

    /*
     *   String getCodedResultValue()
     */
    public String getCodedResultValue()
    {

        StringBuffer crv = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedCodedResultCd;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getCodedResultValue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, crv);
            /*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                          //  logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            crv.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
*/
        }

        crv.append(other);
        cachedCodedResultCd = crv.toString();

        return crv.toString();
    }
    
    
    public String getCodedResultValueSusc()
    {

        StringBuffer crv = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedCodedResultSuscCd;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getCodedResultValueSusc();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, crv);
        
        }

        crv.append(other);
        cachedCodedResultSuscCd = crv.toString();

        return crv.toString();
    }
    
    
    
    public String getResultMethodValueSusc()
    {

        StringBuffer crv = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedResultMethodSuscCd;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getResultMethodValueSusc();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, crv);
        
        }

        crv.append(other);
        cachedResultMethodSuscCd = crv.toString();

        return crv.toString();
    }
    
    
    
    public ArrayList<Object> getCodedResultValueList()
    {
        TreeMap<Object,Object> treeMap = null;
        ArrayList<Object> codedResultValueList = new ArrayList<Object>();

        try
        {
             if (getSRTMapEJBRef() != null)
            	 codedResultValueList = getSRTMapEJBRef().getCodedResultValueList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return codedResultValueList;
    }
    /*
     *   String getCodedResultValueDesc()
     */
    public String getCodedResultDesc(String cd)
    {

      String result = null;

       try {
         if (cachedCodedResultValueTreeMap == null) {
           if (getSRTMapEJBRef() != null)
             cachedCodedResultValueTreeMap = getSRTMapEJBRef().getCodedResultValue();
         }
        }
        catch (Exception e) {
         e.printStackTrace();
        }



         if (cachedCodedResultValueTreeMap != null)
          {
           Object tempObject = cachedCodedResultValueTreeMap.get(cd);
           if(tempObject != null)
           result = (String) tempObject;
          }


       return result;
    }
    /*
     *   String getCodedResultValueDesc()
     */
    public String getResultedTestDescLab(String labId,String cd)
    {

      String result = null;

       try {
         if (cachedCodedResultValueTreeMapLab == null) {
           if (getSRTMapEJBRef() != null)
             cachedCodedResultValueTreeMapLab = getSRTMapEJBRef().getResultedTestLab(labId,cd);
         }
        }
        catch (Exception e) {
         e.printStackTrace();
        }



         if (cachedCodedResultValueTreeMapLab != null)
          {
           Object tempObject = cachedCodedResultValueTreeMapLab.get(cd);
           if(tempObject != null)
           result = (String) tempObject;
          }

                                        cachedCodedResultValueTreeMapLab = null;


       return result;
    }



    public String getConditionCode()
    {

        StringBuffer conditionCode = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedConditionCode;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object, Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getConditionCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
          performCaseInsensitiveSortAndFormat(treeMap,other, conditionCode);
/*
          Set s = new TreeSet(treeMap.values());
         Iterator<Object>  it = s.iterator();

           while (it.hasNext())
           {

               String sortedValue = (String)it.next();
              Iterator<Object>  anIterator = treeMap.entrySet().iterator();

               while (anIterator.hasNext())
               {

                   Map.Entry map = (Map.Entry)anIterator.next();

                   if ((String)map.getValue() == sortedValue)
                   {

                       if (((String)map.getValue()).equalsIgnoreCase("Other"))
                       {
                        //   logger.debug(
                       //	    "Other Value is  :" + map.getValue() + " and key is :" +
                       //	    map.getKey());

                           String key = (String)map.getKey();
                           String value = (String)map.getValue();
                           other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                .append(NEDSSConstants.SRT_LINE);
                       }
                       else
                       {
                         //  logger.debug(
                       //	    "Value is  :" + map.getValue() + " and key is :" +
                       //	    map.getKey());

                           String key = (String)map.getKey();
                           String value = (String)map.getValue();
                           conditionCode.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                        .append(NEDSSConstants.SRT_LINE);
                       }
                   }
               }
           }
 */
       }

       conditionCode.append(other);


        cachedConditionCode = conditionCode.toString();

        return conditionCode.toString();
    }

    // LANGUAGE for person pages
    public String getLanguageCode()
    {

        StringBuffer languageCodes = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedLanguageCode;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object, Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getLanguageCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, languageCodes);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                          //  logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                         //   logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            languageCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        languageCodes.append(other);
        cachedLanguageCode = languageCodes.toString();

        return languageCodes.toString();
    }

    public TreeMap<Object, Object> getNAICSGetIndustryCodeAsTreeMap() {
    	
    		if(cachedNAICSIndustryCodes != null && cachedNAICSIndustryCodes.size() > 0)
    			return cachedNAICSIndustryCodes;
    		
    	   TreeMap<Object, Object> treeMap = null;
           try
           {
                if (getSRTMapEJBRef() != null) {
                  treeMap = getSRTMapEJBRef().NAICSgetIndustryCode();
                  cachedNAICSIndustryCodes = treeMap;
                }
                	
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }

    	   return treeMap;    	
    }
    
    public TreeMap<Object, Object> getSTDHIVWorkersTreeMap() {
    	
		
	   TreeMap<Object, Object> treeMap = null;
       try
       {
            if (getSRTMapEJBRef() != null) {
              treeMap = getSRTMapEJBRef().getSTDHIVWorkers();
            }
            	
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

	   return reverseMap(treeMap);    	
}
    
    public String getNAICSGetIndustryCode()
    {

        StringBuffer industryCodes = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedIndustryCode;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object, Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().NAICSgetIndustryCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, industryCodes);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                         //   logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                        //  logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            industryCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        industryCodes.append(other);
        cachedIndustryCode = industryCodes.toString();

        return industryCodes.toString();
    }


        public String getOccupationCode() {

                StringBuffer occupationCodes = new StringBuffer("");
                StringBuffer other = new StringBuffer("");
                String strMaps = cachedOccupationCode;

                if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
                {

                    return strMaps;
                }

                TreeMap<Object, Object> treeMap = null;

                try
                {
                     if (getSRTMapEJBRef() != null)
                       treeMap = getSRTMapEJBRef().getOccupationCode();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if (treeMap != null)
                {
                    performCaseInsensitiveSortAndFormat(treeMap,other, occupationCodes);
/*
                    Set s = new TreeSet(treeMap.values());
                   Iterator<Object>  it = s.iterator();

                    while (it.hasNext())
                    {

                        String sortedValue = (String)it.next();
                       Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                        while (anIterator.hasNext())
                        {

                            Map.Entry map = (Map.Entry)anIterator.next();

                            if ((String)map.getValue() == sortedValue)
                            {

                                if (((String)map.getValue()).equalsIgnoreCase("Other"))
                                {

                                    String key = (String)map.getKey();
                                    String value = (String)map.getValue();
                                    other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                                }
                                else
                                {

                                    String key = (String)map.getKey();
                                    String value = (String)map.getValue();
                                    occupationCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                                 .append(NEDSSConstants.SRT_LINE);
                                }
                            }
                        }
                    }
 */
                }

                occupationCodes.append(other);
                cachedOccupationCode = occupationCodes.toString();

                return occupationCodes.toString();

        } //getOccupationCode


  /**
   *
   * @return TreeMap<Object,Object> Object
   * this methods return TreeMap<Object,Object> object. Key value pair are code and short
   * description txt for each corressponding code.
   */
  public TreeMap<Object, Object> getCountyShortDescTxtCode()
  {

      TreeMap<Object, Object> treeMap = null;

      try
      {
           if (getSRTMapEJBRef() != null)
             treeMap = getSRTMapEJBRef().getCountryShortDescTxt();
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

      return treeMap;
  }



    public TreeMap<?, ?> getCountyCodes(String StateCode) //only single state in beta release
    {

      TreeMap<?, ?> stateTreeMap = null;
       if(cachedCountyCodes==null||cachedCountyCodes.isEmpty())//nothing in the Map
        {
    	   if(cachedCountyCodes==null){
    		   cachedCountyCodes = new TreeMap<Object,Object>();
    	   }
          try
          {
            if (getSRTMapEJBRef() != null){
              stateTreeMap= getSRTMapEJBRef().getCountyCodes(StateCode);
              cachedCountyCodes.put(StateCode, stateTreeMap);
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
        else if (!cachedCountyCodes.containsKey(StateCode))//no StateCode key found
        {
          try
          {
           if (getSRTMapEJBRef() != null)
           {
             stateTreeMap = getSRTMapEJBRef().getCountyCodes(StateCode);
             cachedCountyCodes.put(StateCode,stateTreeMap);
           }
         }
         catch (Exception e) {
           e.printStackTrace();
         }
        }
        else if(cachedCountyCodes.containsKey(StateCode)){//StateCode key found
          stateTreeMap = (TreeMap<?, ?>)cachedCountyCodes.get(StateCode);
        }
        return stateTreeMap;
    }

    //method to return all County codes for all states
    public TreeMap<Object, Object> getCountyCodes()
    {
      if (cachedAllCountyCodes==null||cachedAllCountyCodes.isEmpty())
        {
          try
          {
            if (getSRTMapEJBRef() != null)
              cachedAllCountyCodes = getSRTMapEJBRef().getCountyCodes();
          }
          catch (Exception e)
        {
            e.printStackTrace();
        }
      }


        return cachedAllCountyCodes;
    }
    
    public TreeMap<Object, Object> getCountyCodesReversed()
    {
      if (cachedAllCountyCodesReversed==null||cachedAllCountyCodesReversed.isEmpty())
        {
          try
          {
            if (getSRTMapEJBRef() != null)
            	cachedAllCountyCodesReversed = reverseMapUpper(getCountyCodes());
          }
          catch (Exception e)
        {
            e.printStackTrace();
        }
      }


        return cachedAllCountyCodesReversed;
    }
    
    public TreeMap<Object, Object> getCountyCodesByStateReversed(String stateCd){
    	return reverseMapUpper(getCountyCodes(stateCd));
    }
    
	public String getCountyCdByDesc(String county, String stateCd) {
		if (county == null)
			return null;
		String cnty = county.toUpperCase();
		if (!cnty.endsWith("COUNTY")) 
			cnty = cnty + " COUNTY";
		if(stateCd==null || stateCd.trim().equals(""))
			return (String) getCountyCodesReversed().get(cnty);
		else
			return (String)getCountyCodesByStateReversed(stateCd).get(cnty);
	}

    public String getCountiesByStateString(String stateCd) {

       StringBuffer parsedCodes = new StringBuffer("");

       if (stateCd != null && stateCd.trim().length() > 0) {

          TreeMap<?, ?> treemap = null;
          treemap = this.getCountyCodes(stateCd);

          if (treemap != null) {

             Set<?> set = treemap.keySet();
             Iterator<?> itr = set.iterator();

             while (itr.hasNext()) {

                String key = (String) itr.next();
                String value = (String) treemap.get(key);
                parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).
                    append(value.trim()).append(NEDSSConstants.SRT_LINE);
             }
          }
       }
       logger.info("length of county String: " + parsedCodes.toString().length());
       return parsedCodes.toString();
    }

    public TreeMap<Object, Object> getConditionCodes()
    {

        TreeMap<Object, Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getConditionCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }
    /*
     *   getConditionDesc
     *   returns a single Desc, based on valid getConditionDesc
     */

    public String getConditionDesc(String cd)
    {
        String result = "";
        Object tempObj = null;

        try
        { // if(cachedConditionTreeMap == null)
          //{
            if (getSRTMapEJBRef() != null)
              cachedConditionTreeMap = getSRTMapEJBRef().getConditionCode();
         // }
          //if (cachedConditionTreeMap != null && cd != null && cd.trim().length() > 0){
          tempObj = cachedConditionTreeMap.get(cd);
          if(tempObj != null)
           result = (String) tempObj;
          //}

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



       return result;
    }
    /*
      *   getConditionDesc
      *   returns a single Desc, based on valid getConditionDesc
      */

     public String getResultedTestDesc(String cd)
     {
         String result = "";
         Object tempObj = null;

         try
         {  if(cachedResultedTestTreeMap == null)
           {
             if (getSRTMapEJBRef() != null)
            	 cachedResultedTestTreeMap = getSRTMapEJBRef().getResultedTest();
           }
           if (cachedResultedTestTreeMap != null && cd != null && cd.trim().length() > 0){
           tempObj = cachedResultedTestTreeMap.get(cd);
           if(tempObj != null)
            result = (String) tempObj;
           }

         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

                                cachedCodedResultValueTreeMapLab = null;
        return result;
     }


    /***********************
     * get the desc text for the code for code value general
     */
    public String getDescForCode(String type, String code)
    {

        String desc = "";

        if (type != null)
        {

            TreeMap<?,?> treeMaps = (TreeMap<?,?>)mapForDesc.get(type);

            if (treeMaps != null)
            {
                //logger.debug("getting the getdescforcode treemap from static class level variable");

                if (code != null)
                    desc = (String)treeMaps.get(code);
                else
                    code = "";

                return desc;
            }
        }

        //   logger.debug("EJB for SRT values called");
        TreeMap<Object,Object> treeMap = null;

        try
        {

            if (type.equalsIgnoreCase("LAB_TEST") && getSRTMapEJBRef() != null)
                   treeMap = getSRTMapEJBRef().getLabTestCodes();
            else if (type.equalsIgnoreCase("S_JURDIC_C") && getSRTMapEJBRef() != null)
                   treeMap = getSRTMapEJBRef().getJurisdictionCodedValues();
            else
                 if (getSRTMapEJBRef() != null)
                   treeMap = getSRTMapEJBRef().getCodedValues(type);

            //logger.debug("getting the getdescforcode treemap from EJB");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {

            if (code != null && treeMap.get(code) != null)
                desc = (String)treeMap.get(code);
            else
                desc = "";
        }

        mapForDesc.put(type, treeMap);

        return desc;
    }

    //method to return all state code and name pairs
    public TreeMap<Object,Object> getStateCodes2(String country)
    {

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getStateCodes1(country);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }

    public TreeMap<Object,Object> getStateTreeMap()
    {
        try
        {
             if (stateCodeDescTxtTreeMap == null)
               stateCodeDescTxtTreeMap = getStateCodes2("USA");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return stateCodeDescTxtTreeMap;
    }

    /**
     * Returns an ordered map of two-letter state abbreviations
     * indexed by FIPS code.
     * 
     * @param country
     * @return
     */
    public TreeMap<Object,Object> getStateAbbreviationsByCode(String country)
    {
        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getStateCodesAndAbbreviations(country);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }

    /**
     * Returns an ordered map of two-letter U.S. state abbreviations
     * indexed by FIPS code.
     * 
     * @return
     */
    public TreeMap<Object,Object> getStateAbbreviationsByCode()
    {
        try
        {
             if (stateAbbreviationsByCode == null)
            	 stateAbbreviationsByCode = getStateAbbreviationsByCode("USA");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return stateAbbreviationsByCode;
    }

    /**
     * Returns the two-letter U.S. state abbreviations corresponding to the
     * indicated FIPS code.
     * 
     * @param code FIPS code
     * @return Two-letter state abbreviation
     */
    public String getStateAbbreviationByCode(String code)
    {
    	String result = null;

    	if (code != null) {
        	TreeMap<Object,Object> map = getStateAbbreviationsByCode();
        	if (map != null) {
        		result = (String) map.get(code);
        	}
    	}
    	return result;
    }

    /**
     * Returns an ordered map of state FIPS codes indexed by
     * two-letter state abbreviations.  (This is the reverse of the
     * map returned by <code>getStateAbbreviationsByCode()</code>.)
     * 
     * @param country
     * @return
     */
    public TreeMap<Object,Object> getStateCodesByAbbreviation(String country)
    {
        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null) {
                 treeMap = getSRTMapEJBRef().getStateCodesAndAbbreviations(country);
                 treeMap = reverseMap(treeMap);
             }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }

    /**
     * Returns an ordered map of U.S. state FIPS codes indexed by
     * two-letter state abbreviations.  (This is the reverse of the
     * map returned by <code>getStateAbbreviationsByCode()</code>.)
     * 
     * @return
     */
    public TreeMap<Object,Object> getStateCodesByAbbreviation()
    {
        try
        {
             if (stateCodesByAbbreviation == null)
            	 stateCodesByAbbreviation = getStateCodesByAbbreviation("USA");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return stateCodesByAbbreviation;
    }

    /**
     * Returns the FIPS code corresponding to the indicated two-letter
     * U.S. state abbreviation.  (Note: Letter case must be factored in.)
     * 
     * @param abbreviation Two-letter state abbreviation
     * @return FIPS code
     */
    public String getStateCodeByAbbreviation(String abbreviation)
    {
    	String result = null;

    	if (abbreviation != null) {
        	TreeMap<Object,Object> map = getStateCodesByAbbreviation();
        	if (map != null) {
        		result = (String) map.get(abbreviation);
        	}
    	}
    	return result;
    }


    /**
     *  Reverses keys and values in a map.
     *  @param pMap the map to reverse.
     *  @return a copy of the map with the keys and values switched.
     *  @author Ed Jenkins
     */
    public TreeMap<Object, Object> reverseMap(TreeMap<?,?> pMap)
    {
        //  Create return variable.
        TreeMap<Object,Object> map = new TreeMap<Object,Object>();
        //  Verify parameters
        if (pMap == null)
        {
            return map;
        }
        //  Reverse keys and values.
        Set<?> set = pMap.keySet();
        Iterator<?> i = set.iterator();
        while (i.hasNext())
        {
            String strKey = (String)i.next();
            String strVal = (String)pMap.get(strKey);
            map.put(strVal, strKey);
        }
        //  Return result.
        return map;
    }
    
    public TreeMap<Object, Object> reverseMapUpper(TreeMap<?,?> pMap)
    {
        //  Create return variable.
        TreeMap<Object,Object> map = new TreeMap<Object,Object>();
        //  Verify parameters
        if (pMap == null)
        {
            return map;
        }
        //  Reverse keys and values.
        Set<?> set = pMap.keySet();
        Iterator<?> i = set.iterator();
        while (i.hasNext())
        {
            String strKey = (String)i.next();
            String strVal = (String)pMap.get(strKey);
            map.put(strVal.toUpperCase(), strKey);
        }
        //  Return result.
        return map;
    }

    /**
     *  Gets codeset values.
     *  @return the drop down values for the disease for report basic filter page.
     *  @author Ed Jenkins
     */
    public TreeMap<Object,Object> getReportBasicPageConditionMap()
    {
        if(reportBasicPageConditionMap != null)
        {
            return reportBasicPageConditionMap;
        }
        //  Create temp variables.
        TreeMap<Object,Object> map = new TreeMap<Object,Object>();
        //  Get the codeset.
        try
        {
             if (getSRTMapEJBRef() != null)
               map = getSRTMapEJBRef().getConditionCode();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //  Reverse the map.
        TreeMap<Object,Object> tm = reverseMap(map);
        reportBasicPageConditionMap = tm;
        //  Return result.
        return tm;
    }

    /**
     *  Gets codeset values.
     *  @param pType the code_set_nm.
     *  @return the values for the given codeset.
     *  @author Ed Jenkins
     */
    public TreeMap<?, ?> getCodedValuesAsTreeMap(String pType) throws NEDSSSystemException
    {
        //  Create return variable.
        TreeMap<?,?> r = new TreeMap<Object,Object>();
        //  Verify parameters.
        if(pType == null)
        {
            return r;
        }
        //  Create key.
        String strKey = "TreeMap." + pType;
        //  See if it's in the cache first.
        r = (TreeMap<?,?>)map.get(strKey);
        //  If it is, then return it.
        if(r != null)
        {
            return r;
        }
        //  If not, then get it.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        try
        {
            if(pType.equals("S_JURDIC_C"))
            {
                 if (getSRTMapEJBRef() != null)
                   tm = getSRTMapEJBRef().getJurisdictionCodedValues();
            }
            else
            {
                 if (getSRTMapEJBRef() != null)
                   tm = getSRTMapEJBRef().getCodedValues(pType);
            }
        //  Reverse the map.
        r = reverseMap(tm);
        //  And put it in the cache.
        map.put(strKey, r);
        //  Return result.
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
          throw new NEDSSSystemException (ex.getMessage());
      }
        return r;
    }

    /**
     *  Gets a map of state codes and names.
     *  @param pCountry the country code.
     *  @return a map of state codes and names.
     *  @author Ed Jenkins
     */
    public TreeMap<?,?> getStateCodes1(String pCountry)
    {
        //  Create return variable.
        TreeMap<?,?> r = new TreeMap<Object,Object>();
        //  Verify parameters.
        if(pCountry == null)
        {
            return r;
        }
        //  Create key.
        String strKey = "TreeMap." + pCountry;
        //  See if it's in the cache first.
        r = (TreeMap<?,?>)map.get(strKey);
        //  If it is, then return it.
        if(r != null)
        {
            return r;
        }
        //  If not, then get it.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        try
        {
             if (getSRTMapEJBRef() != null)
               tm = getSRTMapEJBRef().getStateCodes1(pCountry);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //  Reverse the map.
        r = reverseMap(tm);
        //  And put it in the cache.
        map.put(strKey, r);
        //  Return result.
        return r;
    }

    /**
     *  Gets a map of county codes and names.
     *  @param pState the state code.
     *  @return a map of county codes and names.
     *  @author Ed Jenkins
     */
    public TreeMap<?,?> getCountyCodesAsTreeMap(String pState)
    {
        //  Create return variable.
        TreeMap<?,?> r = new TreeMap<Object,Object>();
        //  Verify parameters.
        if(pState == null)
        {
            return r;
        }
        //  Create key.
        String strKey = "TreeMap." + pState;
        //  See if it's in the cache first.
        r = (TreeMap<?,?>)map.get(strKey);
        //  If it is, then return it.
        if(r != null)
        {
            return r;
        }
        //  If not, then get it.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        try
        {
             if (getSRTMapEJBRef() != null)
               tm = getSRTMapEJBRef().getCountyCodes(pState);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //  Reverse the map.
        r = reverseMap(tm);
        //  And put it in the cache.
        map.put(strKey, r);
        //  Return result.
        return r;
    }

    /**
     *  Gets a map of region codes and names.
     *  @return a map of region codes and names.
     *  @author Ed Jenkins
     */
    public TreeMap<?,?> getRegionCodes()
    {
        //  Create return variable.
        TreeMap<?,?> r = new TreeMap<Object,Object>();
        //  Create key.
        String strKey = "TreeMap.RegionCodes";
        //  See if it's in the cache first.
        r = (TreeMap<?,?>)map.get(strKey);
        //  If it is, then return it.
        if(r != null)
        {
            return r;
        }
        //  If not, then get it.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        try
        {
             if (getSRTMapEJBRef() != null)
               tm = getSRTMapEJBRef().getRegionCodes();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //  Reverse the map.
        r = reverseMap(tm);
        //  And put it in the cache.
        map.put(strKey, r);
        //  Return result.
        return r;
    }

    /**
     * @method      :   getLabTestCodes
     * @return      :   java.lang.String
     * @description :   This method fetches LabTestCodes from Lab_Test table and caches them
     *                  if none changed by comparision
     */
    public String getLabTestCodes()
    {
        //logger.debug("inside getLabTestCodes");

        StringBuffer labTestCodes = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = cachedLabtestCodes;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getLabTestCodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
          performCaseInsensitiveSortAndFormat(treeMap,others, labTestCodes);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {


                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                                //    "Other Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            labTestCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                        .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        labTestCodes.append(others);
        cachedLabtestCodes = labTestCodes.toString();

        //map.put(type, stateCodes.toString());
        return labTestCodes.toString();
    } //getLabTestCodes()
    /**
     * @method      :   getLabTestCodesTreeMap
     * @return      :   TreeMap<Object,Object>
     * @description :   This method fetches LabTestCodes from Lab_Test table and caches them
     *                  if none changed by comparision
     */
    public TreeMap<Object,Object> getLabTestCodesTreeMap()
    {
        //logger.debug("inside getLabTestCodes");

        TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getLabTestCodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return treeMap;
    }
    /**
     * @method      :   getSuspTestCodes
     * @return      :   java.lang.String
     * @description :   This method fetches DrugTestCodes from NBS_susp_test table and caches them
     *                  if none changed by comparision
     */
    public String getSuspTestCodes()
    {
        //logger.debug("inside getSuspTestCodes");

        StringBuffer suspTestCodes = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = cachedSusptestCodes;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getSuspTestCodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
           performCaseInsensitiveSortAndFormat(treeMap,others, suspTestCodes);
          /*

            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                          //  logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            suspTestCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
*/
        }

        suspTestCodes.append(others);
        cachedSusptestCodes = suspTestCodes.toString();

        //map.put(type, stateCodes.toString());
        return suspTestCodes.toString();
    } //getLabTestCodes()

    /*
     *   getJurisdictionCodedSortedValues
     */
    public String getJurisdictionCodedSortedValues()
    {

        String type = "getJurisdictionCodedSortedValues";
        StringBuffer sb = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        TreeMap<?,?> treeMap = (TreeMap<?,?>)map.get(type);

        if (cachedJurisdictionCode != null &&
            cachedJurisdictionCode.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return cachedJurisdictionCode;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getJurisdictionCodedValues();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, sb);
        }

        sb.append(other);
        jurisdictionUnknown = sb;
        cachedJurisdictionCode = sb.toString();

        return cachedJurisdictionCode;
    }
    public  ArrayList<Object>  getJurisdictionList() {

		ArrayList<Object>  list = null;
		try {
			if (cachedMap.get(NEDSSConstants.JURIS_LIST) == null) {
				list = new ArrayList<Object> ();
				if (getSRTMapEJBRef() != null) {
					TreeMap<Object,Object> juris = getSRTMapEJBRef()
							.getJurisdictionCodedValues();
					if (juris != null && juris.size() > 0) {
					//	Set<Object> keyset = juris.keySet();
						TreeSet<Object> set = new TreeSet<Object>(new Comparator<Object>() {
				            public int compare(Object obj, Object obj1) {
				                return ((Comparable<Object>) ((Map.Entry<Object,Object>) obj).getValue()).compareTo(((Map.Entry<Object,Object>) obj1).getValue());
				            }
				        });
				        
				        set.addAll(juris.entrySet());
				        DropDownCodeDT dDownDT = new DropDownCodeDT();
						dDownDT.setKey(""); dDownDT.setValue("");
						list.add(dDownDT);
				        for (Iterator<Object> i = set.iterator(); i.hasNext();) {
				            Map.Entry<?,?> entry = (Map.Entry<?,?>) i.next();
				            dDownDT = new DropDownCodeDT();
							dDownDT.setKey((String) entry.getKey());
							dDownDT.setValue((String) entry.getValue());
							list.add(dDownDT);
				        }

						if (list.size() > 0) {
							cachedMap.put(NEDSSConstants.JURIS_LIST, list);
						}

					}
				}
			} else {
				list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.JURIS_LIST);
			}
		} catch (Exception ex) {
			logger
					.error("Error while loading jurisdictions in getJurisdictionList: CachedDropDowns. ");
		}
		return list;
	}


public String getJurisdictionWithUnknown()
    {
      if (jurisdictionUnknown == null || jurisdictionUnknown.equals(""))
      {
        //inialize jurisdictionUnknown
        getJurisdictionCodedSortedValues();
      }
      //add Unknown only when it is not in the StringBuffer "jurisdictionUnknown"
      if(jurisdictionUnknown != null && jurisdictionUnknown.toString().indexOf("Unknown") < 0)
      {
        String akey = "NONE";
        String value = "Unknown";

        jurisdictionUnknown.insert(0, NEDSSConstants.SRT_LINE);
        jurisdictionUnknown.insert(0, value.trim());
        jurisdictionUnknown.insert(0, NEDSSConstants.SRT_PART);
        jurisdictionUnknown.insert(0, akey.trim());
      }

      if(jurisdictionUnknown !=null)

              return jurisdictionUnknown.toString();
      else
       return "";
    }


// all appended to the String
 public String getJurisdictionCodedSortedValuesWithAll()
    {
//test
        //String type = "getJurisdictionCodedSortedValuesWithAll";
        StringBuffer sb = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getJurisdictionCodedValues();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            treeMap.put(NEDSSConstants.ALL, NEDSSConstants.ALL);
            performCaseInsensitiveSortAndFormat(treeMap,other, sb);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {

                            // logger.debug("Other Value is  :" + map.getValue() + " and key is :" + map.getKey());
                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {

                            // logger.debug("Value is  :" + map.getValue() + " and key is :" + map.getKey());
                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            sb.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                              .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        sb.append(other);

      //  sb.append(NEDSSConstants.ALL).append(NEDSSConstants.SRT_PART).append(NEDSSConstants.ALL).append(NEDSSConstants.SRT_LINE);

        jurisdictionCode = sb.toString();

        return jurisdictionCode;
    }

    /**
     * getCountry Codes
     */
    public String getCountryCodesAsString()
    {
        //logger.debug("inside getCountryCodes");

        StringBuffer countryCodes = new StringBuffer("");
        StringBuffer others = new StringBuffer("");
        String strMaps = cachedCountryCodes;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getAllCountryCodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,others, countryCodes);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {

                            // logger.debug("Other Value is  :" + map.getValue() + " and key is :" + map.getKey());
                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            others.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {

                            // logger.debug("Value is  :" + map.getValue() + " and key is :" + map.getKey());
                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            countryCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                        .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        countryCodes.append(others);
        cachedCountryCodes = countryCodes.toString();

        return countryCodes.toString();
    } //getCountryCodes()

    //--------------security stuff ----------------------

    /*
     *   getProgramAreaCodedValues
     */
    public TreeMap<Object,Object> getProgramAreaCodedValues()
    {

        //this will never be anyother type
        String type = "programAreaCodedValues";
        //StringBuffer codedValues = new StringBuffer("");
        TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>)map.get(type);

        if (treeMap != null && treeMap.size() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
            //logger.debug("getProgramAreaCodedValues cached values for SRT called");

            return treeMap;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getProgramAreaCodedValues();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map.put(type, treeMap);

        return treeMap;
    }

    /*
     *   getProgramAreaNumericIDs
     */
    public TreeMap<Object,Object> getProgramAreaNumericIDs()
    {

        String type = "programAreaNumericIDs";
        //StringBuffer codedValues = new StringBuffer("");
        TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>)map.get(type);

        if (treeMap != null && treeMap.size() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
           // logger.debug("getProgramAreaNumericIDs cached values for SRT called");

            return treeMap;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getProgramAreaNumericIDs();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map.put(type, treeMap);

        return treeMap;
    }

    /*
     *   getJurisdictionCodedValues
     *   returns a single Desc, based on valid JurisdictionCD
     */

    public String getJurisdictionDesc(String cd)
    {
        TreeMap<?,?> treeMap = null;
        String result = "";
        Object tempObj = null;

        try
        {
         treeMap = this.getJurisdictionCodedValues();
         if (treeMap != null && cd != null && cd.trim().length() > 0){
          tempObj =  treeMap.get(cd);
          if( tempObj != null)
           result = (String)tempObj;
         }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


       return result;
    }

    /*
     *   getJurisdictionCodedValues
     */
    public TreeMap<Object,Object> getJurisdictionCodedValues()
    {

        String type = "jurisdictionCodedValues";
        //StringBuffer codedValues = new StringBuffer("");
        TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>)map.get(type);

        if (treeMap != null && treeMap.size() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
            //logger.debug("getJurisdictionCodedValues cached values for SRT called");

            return treeMap;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getJurisdictionCodedValues();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map.put(type, treeMap);

        return treeMap;
    }
    
    public TreeMap<Object,Object> getPlacesWithQEC()
    {
    	 TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
       try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getPlaceMap();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }

   public TreeMap<Object, Object> getJurisdictionCodedValuesWithAll()
    {

        //String type = "jurisdictionCodedValuesWithAll";
        //StringBuffer codedValues = new StringBuffer("");
        TreeMap<Object,Object> treeMap = null;
/*	TreeMap treeMap = (TreeMap)map.get(type);

        if (treeMap != null && treeMap.size() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
            logger.debug("getJurisdictionCodedValues cached values for SRT called");

            return treeMap;
        }
*/
        try
        {
             if (getSRTMapEJBRef() != null){
               treeMap = getSRTMapEJBRef().getJurisdictionCodedValues();
               treeMap.put(NEDSSConstants.ALL, NEDSSConstants.ALL);
             }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//	map.put(type, treeMap);

        return treeMap;
    }

    /*
     *   getJurisdictionNumericIDs
     */
    public TreeMap<Object,Object> getJurisdictionNumericIDs()
    {

        String type = "jurisdictionNumericIDs";
        //StringBuffer codedValues = new StringBuffer("");
        TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>)map.get(type);

        if (treeMap != null && treeMap.size() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
           // logger.debug("getJurisdictionNumericIDs cached values for SRT called");

            return treeMap;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getJurisdictionNumericIDs();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map.put(type, treeMap);

        return treeMap;
    }

    public String getCurrentSex(String currentSex)
                        
    {

        String currSex = "";

        try
        {
             if (getSRTMapEJBRef() != null)
               currSex = getSRTMapEJBRef().getCurrentSex(currentSex);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //logger.debug("The CurrSex is :" + currSex);

        return currSex;
    }

    public int getCountOfCdNotNull(String cityCD)
                            throws Exception
    {

        int count = 0;

        try
        {
             if (getSRTMapEJBRef() != null)
               count = getSRTMapEJBRef().getCountOfCdNotNull(cityCD);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //logger.debug("count in getCountOfCdNotNull is:" + count);

        return count;
    }

    public String getJurisditionCD(String zipCD, String typeCD)
                            throws Exception
    {

        String jurisdictionCD = "";

        try
        {
             if (getSRTMapEJBRef() != null)
               jurisdictionCD = getSRTMapEJBRef().getJurisditionCD(zipCD, typeCD);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //logger.debug("jurisdition value in jurisdictionCode is:" + jurisdictionCD);

        return jurisdictionCD;
    }

    public  String getDiagnosisCodeForConceptCode(String conceptKey){
    	 TreeMap<Object,Object> treeMap = cachedDiagnosisCodeMap;

         try
         {
              if (getSRTMapEJBRef() != null && cachedDiagnosisCodeMap == null )
                treeMap = getSRTMapEJBRef().getDiagnosisCodeAltValue();
              cachedDiagnosisCodeMap = treeMap;
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
          if(treeMap.containsKey(conceptKey)){
        	   return (String)treeMap.get(conceptKey);
          }
          return null;
    }
    
    /**
     * retrieves the diagnosis codes from database
     */
    public String getDiagnosisCode()
    {

        StringBuffer diagnosisCode = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedDiagnosisCode;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null  )
               treeMap = getSRTMapEJBRef().getDiagnosisCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
           performCaseInsensitiveSortAndFormat(treeMap,other, diagnosisCode);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                        //	    "Other Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                         //   logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            diagnosisCode.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        diagnosisCode.append(other);
        cachedDiagnosisCode = diagnosisCode.toString();

        return diagnosisCode.toString();
    }
    /**
     * retrieves the diagnosis codes from database based on programArea from security obj
     */
    public String getDiagnosisCodeFilteredOnPA(String programAreas)
    {

        StringBuffer diagnosisCode = new StringBuffer("");

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas, 2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {

            Set<Object> s = new TreeSet<Object>(treeMap.values());
            Iterator<Object> it = s.iterator();

            while (it.hasNext())
            {

                ProgramAreaVO programAreaVO = (ProgramAreaVO)it.next();
                //Iterator anIterator = treeMap.entrySet().iterator();
                String key = programAreaVO.getConditionCd();
                String value = programAreaVO.getConditionShortNm();
                diagnosisCode.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                             .append(NEDSSConstants.SRT_LINE);

            }

        }

        return diagnosisCode.toString();
    }



    public static void main(String[] args)
    {

        //CachedDropDownValues srtc = new CachedDropDownValues();
        //logger.debug(srtc.getDiagnosisCode());

/*	String countryCodes = srtc.getCountryCodesAsString();
        logger.debug("countryCodes = " + countryCodes);

        String jurisdictionCodedSortedValues = srtc.getJurisdictionCodedSortedValues();
        logger.debug("jurisdictionCodedSortedValues = " + jurisdictionCodedSortedValues);


           TreeMap<Object,Object> treeMap = srtc.getProgramAreaCodedValues();
          Iterator<Object>  it = treeMap.entrySet().iterator();
           logger.debug("---- getProgramAreaCodedValues");
           while(it.hasNext())
           {
               logger.debug(it.next());
           }
           treeMap = srtc.getProgramAreaNumericIDs();
           it = treeMap.entrySet().iterator();
           logger.debug("---- getProgramAreaNumericIDs");
           while(it.hasNext())
           {
               logger.debug(it.next());
           }
           treeMap = srtc.getJurisdictionCodedValues();
           it = treeMap.entrySet().iterator();
           logger.debug("---- getJurisdictionCodedValues");
           while(it.hasNext())
           {
               logger.debug(it.next());
           }
           treeMap = srtc.getJurisdictionNumericIDs();
           it = treeMap.entrySet().iterator();
           logger.debug("---- getJurisdictionNumericIDs");
           while(it.hasNext())
           {
               logger.debug(it.next());
           }
           String sortedJurisdictionCodes = srtc.getJurisdictionCodedSortedValues();
           logger.debug("---- getJurisdictionCodedSortedValues");
           logger.debug(sortedJurisdictionCodes);
           logger.debug(srtc.getCodedValues("LAB_TEST"));
           logger.debug(srtc.getDescForCode("LAB_TEST","243"));
           logger.debug(srtc.getStateCodes("USA"));
           logger.debug("" + srtc.map.size());
           logger.debug("1st CodedValues  " + srtc.getCodedValues("EI_TYPE"));
           logger.debug("codes = " + srtc.getCodedValues("EL_TYPE"));
           logger.debug("2nd CodedValues  " + srtc.getCodedValues("EI_TYPE"));
           logger.debug("RaceCodes  " + srtc.getRaceCodes("2028-9"));
           logger.debug("First Time AddressType  " + srtc.getAddressType());
           logger.debug("Condition Code  " + srtc.getConditionCode());
           logger.debug("Condition Code Second time " + srtc.getConditionCode());
           logger.debug("Second time around AddressType  " + srtc.getAddressType());
           logger.debug("AddressType  " + srtc.getAddressType());
           logger.debug("language codes = " + srtc.getLanguageCode());
           logger.debug("industry codes = " + srtc.getNAICSGetIndustryCode());
         */
    }


    public String getBMDConditionCode()
    {
        StringBuffer conditionCode = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedBMDConditionCode;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getBMDConditionCode();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, conditionCode);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                           // logger.debug(
                                //    "Other Value is  :" + map.getValue() + " and key is :" +
                                //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            conditionCode.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        conditionCode.append(other);
        cachedBMDConditionCode = conditionCode.toString();
        //logger.debug("cachedBMDConditionCode is :"+cachedBMDConditionCode);
        return cachedBMDConditionCode.toString();
    }


    public TreeMap<Object,Object> getSummaryReportConditionCode(String InConditionForProgArea) throws java.rmi.RemoteException, Exception
    {
      TreeMap<Object,Object> treeMap = null;
      try
         {
               if (getSRTMapEJBRef() != null)
                 treeMap = (getSRTMapEJBRef().getSummaryReportConditionCode(InConditionForProgArea));
         }
      catch (Exception e)
      {
          e.printStackTrace();
      }
     return treeMap;
    }

    public TreeMap<Object,Object> getActiveSummaryReportConditionCode(String InConditionForProgArea) throws java.rmi.RemoteException, Exception
    {
      TreeMap<Object,Object> treeMap = null;
      try
         {
               if (getSRTMapEJBRef() != null)
                 treeMap = (getSRTMapEJBRef().getActiveSummaryReportConditionCode(InConditionForProgArea));
         }
      catch (Exception e)
      {
          e.printStackTrace();
      }
     return treeMap;
    }

     public TreeMap<Object,Object> getSummaryReportConditionCodeProgAreaCd(String InConditionForProgArea) throws java.rmi.RemoteException, Exception
    {
      TreeMap<Object,Object> treeMap = null;
      try
         {
               if (getSRTMapEJBRef() != null)
                 treeMap = (getSRTMapEJBRef().getSummaryReportConditionCodeProgAreCd(InConditionForProgArea));
         }
      catch (Exception e)
      {
          e.printStackTrace();
      }
     return treeMap;
    }

    public String getSRCountyConditionCode(String countyCd)
    {
        String SRConditionCode = null;
        StringBuffer conditionCode = new StringBuffer("");
        StringBuffer other = new StringBuffer("");

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getSRCountyConditionCode(countyCd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, conditionCode);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {
                            //logger.debug(
                                   // "Other Value is  :" + map.getValue() + " and key is :" +
                                  //  map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                 .append(NEDSSConstants.SRT_LINE);
                        }
                        else
                        {
                           // logger.debug(
                        //	    "Value is  :" + map.getValue() + " and key is :" +
                        //	    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            conditionCode.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                         .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
            }
 */
        }

        conditionCode.append(other);
        SRConditionCode = conditionCode.toString();
        //logger.debug("cachedBMDConditionCode is :"+ SRConditionCode);
        return SRConditionCode.toString();
    }
    public TreeMap<Object,Object> getSRCountyConditionCodeTreeMap(String countyCd)
    {
      TreeMap<Object,Object> treeMap = null;
      try
         {
               if (getSRTMapEJBRef() != null)
                 treeMap = getSRTMapEJBRef().getSRCountyConditionCode(countyCd);
         }
      catch (Exception e)
      {
          e.printStackTrace();
      }
     return treeMap;
    }

    public TreeMap<Object,Object> getCountryCodesAsTreeMap()
    {
        //logger.debug("inside getCountryCodes");

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getAllCountryCodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return treeMap;
    }


    public String getOrderedTests()
  {

      StringBuffer orderedTests = new StringBuffer("");
      StringBuffer other = new StringBuffer("");

      //if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
      //{

        //  return strMaps;
      //}

      TreeMap<Object,Object> treeMap = null;

      try
      {
           if (getSRTMapEJBRef() != null)
             treeMap = getSRTMapEJBRef().getOrderedTestAndResultValues("O");
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

      if (treeMap != null)
      {
          performCaseInsensitiveSortAndFormat(treeMap,other, orderedTests);
/*
          Set s = new TreeSet(treeMap.values());
         Iterator<Object>  it = s.iterator();

          while (it.hasNext())
          {

              String sortedValue = (String)it.next();
             Iterator<Object>  anIterator = treeMap.entrySet().iterator();

              while (anIterator.hasNext())
              {

                  Map.Entry map = (Map.Entry)anIterator.next();

                  if ((String)map.getValue() == sortedValue)
                  {

                      if (((String)map.getValue()).equalsIgnoreCase("Other"))
                      {
                          //logger.debug(
                      //	    "Other Value is  :" + map.getValue() + " and key is :" +
                              //    map.getKey());

                          String key = (String)map.getKey();
                          String value = (String)map.getValue();
                          other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                               .append(NEDSSConstants.SRT_LINE);

                      }
                      else
                      {
                        //  logger.debug(
                      //	    "Value is  :" + map.getValue() + " and key is :" +
                      //	    map.getKey());

                      String key = (String)map.getKey();
                      String value = (String)map.getValue();
                      other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                           .append(NEDSSConstants.SRT_LINE);

                      }
                  }
              }
          }
 */
      }

      orderedTests.append(other);
      orderedTests.toString();

      return orderedTests.toString();
  }

  public String getResultTests()
  {

    StringBuffer resultTests = new StringBuffer("");
    StringBuffer other = new StringBuffer("");
    String strMaps = cachedResultTest;

    if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
    {
        return strMaps;
    }

    TreeMap<Object,Object> treeMap = null;

    try
    {
         if (getSRTMapEJBRef() != null)
           treeMap = getSRTMapEJBRef().getOrderedTestAndResultValues("R");
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    if (treeMap != null)
    {
        performCaseInsensitiveSortAndFormat(treeMap,other, resultTests);
/*
        Set s = new TreeSet(treeMap.values());
       Iterator<Object>  it = s.iterator();

        while (it.hasNext())
        {

            String sortedValue = (String)it.next();
           Iterator<Object>  anIterator = treeMap.entrySet().iterator();

            while (anIterator.hasNext())
            {

                Map.Entry map = (Map.Entry)anIterator.next();

                if ((String)map.getValue() == sortedValue)
                {

                    if (((String)map.getValue()).equalsIgnoreCase("Other"))
                    {
                        //logger.debug(
                    //	    "Other Value is  :" + map.getValue() + " and key is :" +
                            //    map.getKey());

                            String key = (String)map.getKey();
                            String value = (String)map.getValue();
                            other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                  .append(NEDSSConstants.SRT_LINE);


                    }
                    else
                    {
                      //  logger.debug(
                    //	    "Value is  :" + map.getValue() + " and key is :" +
                    //	    map.getKey());

                    String key = (String)map.getKey();
                    String value = (String)map.getValue();
                    other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                        .append(NEDSSConstants.SRT_LINE);


                    }
                }
            }
        }
 */
    }

    resultTests.append(other);
    cachedResultTest = resultTests.toString();

    return resultTests.toString();
}

   public String getDrugNames()
   {

      StringBuffer drugNames = new StringBuffer("");
      StringBuffer other = new StringBuffer("");
      String strMaps = cachedDrugNames;

      if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
      {
         return strMaps;
      }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getDrugNames();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, drugNames);

        }

        drugNames.append(other);
        cachedDrugNames = drugNames.toString();

        return drugNames.toString();
    }
    public String getTreatmentDesc()
    {

      StringBuffer treatmentDesc = new StringBuffer("");
      StringBuffer other = new StringBuffer("");
      String strMaps = cachedTreatmentDesc;

      if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
      {

          return strMaps;
      }

      TreeMap<Object,Object> treeMap = null;

      try
      {
           if (getSRTMapEJBRef() != null)
             treeMap = getSRTMapEJBRef().getTreatmentDesc();
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

      if (treeMap != null)
      {
        performCaseInsensitiveSortAndFormat(treeMap,other, treatmentDesc);
/*
          Set s = new TreeSet(treeMap.values());
         Iterator<Object>  it = s.iterator();

          while (it.hasNext())
          {

              String sortedValue = (String)it.next();
             Iterator<Object>  anIterator = treeMap.entrySet().iterator();

              while (anIterator.hasNext())
              {

                  Map.Entry map = (Map.Entry)anIterator.next();

                  if ((String)map.getValue() == sortedValue)
                  {

                      if (((String)map.getValue()).equalsIgnoreCase("Other"))
                      {
                          //logger.debug(
                      //	    "Other Value is  :" + map.getValue() + " and key is :" +
                              //    map.getKey());

                              String key = (String)map.getKey();
                              String value = (String)map.getValue();
                              other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                    .append(NEDSSConstants.SRT_LINE);


                      }
                      else
                      {
                        //  logger.debug(
                      //	    "Value is  :" + map.getValue() + " and key is :" +
                      //	    map.getKey());

                      String key = (String)map.getKey();
                      String value = (String)map.getValue();
                      other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                          .append(NEDSSConstants.SRT_LINE);


                      }
                  }
              }
          }
 */
      }

      treatmentDesc.append(other);
      cachedTreatmentDesc = treatmentDesc.toString();

      return treatmentDesc.toString();
  }
  public String getTreatmentDrug()
      {

        StringBuffer treatmentDrug = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        String strMaps = cachedTreatmentDrug;

        if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {
            return strMaps;
        }

        TreeMap<Object,Object> treeMap = null;

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getTreamentDrug();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, treatmentDrug);
/*
            Set s = new TreeSet(treeMap.values());
           Iterator<Object>  it = s.iterator();

            while (it.hasNext())
            {

                String sortedValue = (String)it.next();
               Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {

                    Map.Entry map = (Map.Entry)anIterator.next();

                    if ((String)map.getValue() == sortedValue)
                    {

                        if (((String)map.getValue()).equalsIgnoreCase("Other"))
                        {


                                String key = (String)map.getKey();
                                String value = (String)map.getValue();
                                other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                      .append(NEDSSConstants.SRT_LINE);


                        }
                        else
                        {


                        String key = (String)map.getKey();
                        String value = (String)map.getValue();
                        other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                            .append(NEDSSConstants.SRT_LINE);


                        }
                    }
                }
            }
 */
        }

        treatmentDrug.append(other);
        cachedTreatmentDrug = treatmentDrug.toString();

        return treatmentDrug.toString();
    }
    public PreDefinedTreatmentDT getPreDefinedTreatmentDT(String treatmentCd) //only single state in beta release
        {

            PreDefinedTreatmentDT preDefinedTreatmentDT = null;

            try
            {
                 if (getSRTMapEJBRef() != null)
                   preDefinedTreatmentDT = getSRTMapEJBRef().getTreamentPreDefined(treatmentCd);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return preDefinedTreatmentDT;
        }

 public TreeMap<Object,Object> getRaceCodes()
 {

     if (cachedRaces != null)
     {

         return cachedRaces;
     }

     else
     {
       TreeMap<Object,Object> treeMap = null;
       try {
         if (getSRTMapEJBRef() != null)
           treeMap = getSRTMapEJBRef().getRaceCodes();
       }
       catch (Exception e) {
         e.printStackTrace();
       }
       return treeMap;
     }
 }

	public String getOrganismListDesc(String code) {

		String organismDesc = null;

		if (organismDesc != null && organismDesc.length() <= NEDSSConstants.SRT_MAX_LENGTH) {
			return organismDesc;
		}

		TreeMap<Object, Object> treeMap = (TreeMap<Object, Object>) mapForDesc.get("ORGANISM_LIST");
		if (treeMap == null || treeMap.size() == 0) {
			try {
				if (getSRTMapEJBRef() != null)
					treeMap = getSRTMapEJBRef().getOrganismList();
				mapForDesc.put("ORGANISM_LIST", treeMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (treeMap != null && code != null && treeMap.get(code) != null) {
			organismDesc = (String) treeMap.get(code);
		}
		return organismDesc;
	}

	public String getOrganismListDescSNM(String code) {

		String organismDesc = null;

		if (organismDesc != null && organismDesc.length() <= NEDSSConstants.SRT_MAX_LENGTH) {
			return organismDesc;
		}

		TreeMap<Object, Object> treeMap = (TreeMap<Object, Object>) mapForDesc.get("ORGANISM_SNM_LIST");
		if ((treeMap == null || treeMap.size() == 0) || (treeMap!=null && (String) treeMap.get(code)==null)) {//In case there's a new snomed code, and it is not part of the cache, we need to go to the DB and retrieve the value.
			try {
				if (getSRTMapEJBRef() != null)
					treeMap = getSRTMapEJBRef().getOrganismListSNM();
				mapForDesc.put("ORGANISM_SNM_LIST", treeMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (treeMap != null && code != null && treeMap.get(code) != null) {
			organismDesc = (String) treeMap.get(code);
		}
		return organismDesc;
	}


 public TreeMap<Object,Object> getOrganismListDesc()
{

   TreeMap<Object,Object> organismDescMap = null;

   if (organismDescMap != null)
   {
      return organismDescMap;
   }

    try
     {
          if (getSRTMapEJBRef() != null)
            organismDescMap = getSRTMapEJBRef().getOrganismList();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
     return organismDescMap;
}


 public String getOrganismList()
  {

     StringBuffer cachedOrganismListBuffer = new StringBuffer("");
     StringBuffer other = new StringBuffer("");
     String strMaps = cachedOrganismList;

     if (strMaps != null && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
     {
        return strMaps;
     }

       TreeMap<Object,Object> treeMap = null;

       try
       {
            if (getSRTMapEJBRef() != null)
              treeMap = getSRTMapEJBRef().getOrganismList();
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       if (treeMap != null)
       {
           performCaseInsensitiveSortAndFormat(treeMap,other, cachedOrganismListBuffer);
/*
           Set s = new TreeSet(treeMap.values());
          Iterator<Object>  it = s.iterator();
           while (it.hasNext())
           {

               String sortedValue = (String)it.next();
              Iterator<Object>  anIterator = treeMap.entrySet().iterator();

               while (anIterator.hasNext())
               {

                   Map.Entry map = (Map.Entry)anIterator.next();

                   if ((String)map.getValue() == sortedValue)
                   {

                       if (((String)map.getValue()).equalsIgnoreCase("Other"))
                       {
                           //logger.debug(
                       //	    "Other Value is  :" + map.getValue() + " and key is :" +
                               //    map.getKey());

                               String key = (String)map.getKey();
                               String value = (String)map.getValue();
                               other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                                     .append(NEDSSConstants.SRT_LINE);

                       }
                       else
                       {
                         String key = (String)map.getKey();
                         String value = (String)map.getValue();
                         other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                             .append(NEDSSConstants.SRT_LINE);

                       }
                   }
               }
           }
 */
       }

       cachedOrganismListBuffer.append(other);
       cachedOrganismList = cachedOrganismListBuffer.toString();

       return cachedOrganismList.toString();
   }




 
 
 	public TreeMap<Object,Object> getCachedStateCodeList()
   {
     if(statCodeList == null)
     {
      try {
        statCodeList = getSRTMapEJBRef().getStateCodeList();
      }
      catch (Exception ex)
       {
          return new TreeMap<Object,Object>();
       }
     }

     return statCodeList;
   }

   public String getCachedTreatmentDescription(String code)
   {
     String Description = "";
     if(cachedTreatmentDescription == null)
     {
       try{
         cachedTreatmentDescription = getSRTMapEJBRef().getTreatmentDesc();
       }
       catch(Exception ex)
       {
         logger.error("There was exception in accessing getTreatmentDesc from srt map EJB");
       }
     }
     Description = (String)cachedTreatmentDescription.get(code);

     return Description;
   }
   public String getCachedConditionCodeList(String code)
   {
     String conditionCode = "";
     if(cachedConditionCodeMap == null)
     {
       try{
         cachedConditionCodeMap = getSRTMapEJBRef().getConditionCode();
       }
       catch(Exception ex)
       {
         logger.error("There was exception in accessing getConditionCode from srt map EJB");
       }
     }
     conditionCode = (String)cachedConditionCodeMap.get(code);

     return conditionCode;
   }


   /**
    * getIndustryCode for IndustryCodes
    * @return java.util.TreeMap<Object,Object>
    */
   public TreeMap<Object,Object> getIndustryCode() {

    TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
    try
    {
         if (getSRTMapEJBRef() != null)
           treeMap = getSRTMapEJBRef().getIndustryCode();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return treeMap;
  }

  /**
   * getLanguageCodeAsTreeMap for LanguageCodes
   * @return java.util.TreeMap<Object,Object>
   */
  public TreeMap<Object,Object> getLanguageCodeAsTreeMap() {

   TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
   try
   {
        if (getSRTMapEJBRef() != null)
          treeMap = getSRTMapEJBRef().getLanguageCode();
   }
   catch (Exception e)
   {
       e.printStackTrace();
   }
   return treeMap;
 }

 public String getLDFMap(String conditionCd) {
   String businessObjNm = null;
 if(cachedLDFMap!= null && cachedLDFMap.size() == 0)
 {
   try {
     if (getSRTMapEJBRef() != null) {
       ArrayList<Object>  list = getSRTMapEJBRef().getLDFPageIDs();
       if (list != null) {
         cachedLDFMap = new HashMap<Object, Object>();
         Iterator<Object> it = list.iterator();
         while (it.hasNext()) {
           LdfPageSetDT ldfPageSet = (LdfPageSetDT) it.next();
           cachedLDFMap.put(ldfPageSet.getConditionCd(),
                            ldfPageSet.getBusinessObjNm());

         }
       }
     }
   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }

 if(cachedLDFMap.get(conditionCd)!= null)
  businessObjNm = (String)cachedLDFMap.get(conditionCd);
  return businessObjNm;
}


    public String getACountysReportingSources(String countyCd) {
        StringBuffer other = new StringBuffer("");
        if (cachedCountyReportingSources == null)
            cachedCountyReportingSources = new TreeMap<Object,Object>();

        String codedValues = (String) cachedCountyReportingSources.get(countyCd);
        if (codedValues != null)
            return codedValues;

        TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
        StringBuffer strBuffer = new StringBuffer();

        try {
            if (getSRTMapEJBRef() != null)
                treeMap = getSRTMapEJBRef().getACountysReportingSources(countyCd);

            if (treeMap != null && treeMap.values() != null) {
                performCaseInsensitiveSortAndFormat(treeMap,other, strBuffer);
/*
                Set s = new TreeSet(treeMap.values());
               Iterator<Object>  it = s.iterator();

                while (it.hasNext()) {

                    String sortedValue = (String) it.next();
                   Iterator<Object>  anIterator = treeMap.entrySet().iterator();

                    while (anIterator.hasNext()) {

                        Map.Entry map = (Map.Entry) anIterator.next();

                        if ((String) map.getValue() == sortedValue) {

                            String key = (String) map.getKey();
                            String value = (String) map.getValue();
                            strBuffer.append(key.trim()).append(NEDSSConstants.SRT_PART).
                                    append(value.trim())
                                    .append(NEDSSConstants.SRT_LINE);
                        }
                    }
                }
 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        codedValues = strBuffer.toString();
        if ((codedValues != null) && (codedValues.length() > 0)) {
            cachedCountyReportingSources.put(countyCd, codedValues);
        }

        return codedValues;
    }

    public ProgramAreaVO getProgramAreaCondition(String programAreas, String conditionCd)
    {
       TreeMap<Object,Object> programAreaTreeMap = null;
       ProgramAreaVO programAreaVO = null;
       try
       {
           programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas);
           programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);
        }
        catch(Exception e)
        {

           e.printStackTrace();
        }

        return   programAreaVO;
    }
    
    /**
     * getProgramAreaForCOnditionWithoutIndentLevel - for DMB had a problem with HEP indent level
     * @return java.util.ProgramAreaVO
     */
    public ProgramAreaVO getProgramAreaForConditionWOIndentLevel(String programAreas, String conditionCd)
    {
       ProgramAreaVO programAreaVO = null;
       try
       {
    	   programAreaVO =  getSRTMapEJBRef().getProgramAreaForCondition(conditionCd);
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }

        return   programAreaVO;
    }
    
    /**
     * this methods gets only active Condtions for the given programAreas
     * @param programAreas : String
     * @return : TreeMap<Object,Object>
     */
    public TreeMap<Object,Object> getActiveProgramAreaConditions(String programAreas)
    {
	       TreeMap<Object,Object> programAreaTreeMap = null;
		       try
		       {
		    	   programAreaTreeMap = getSRTMapEJBRef().getActiveProgramAreaConditions(programAreas);
	           
		        }
		        catch(Exception e)
		        {
		           e.printStackTrace();
		        }
	     return   programAreaTreeMap;
    }


    /**
     * this methods gets all Condtions including inactive for the given programAreas
     * @param programAreas : String
     * @return : TreeMap<Object,Object>
     */
    public TreeMap<Object,Object> getAllProgramAreaConditions(String programAreas)
    {
       TreeMap<Object,Object> programAreaTreeMap = null;
       try
       {
           programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas);
           programAreaTreeMapIncCond = programAreaTreeMap;
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }

        return   programAreaTreeMap;
    }

    public String getAllFilterOperators() {
    	
        String filterOperators = null;
        try
        {
        	filterOperators = getSRTMapEJBRef().getAllFilterOperators();
         }
         catch(Exception e)
         {
            e.printStackTrace();
         }

         return   filterOperators;
    }

    /**
     * this methods gets only active Condtions for the given programAreas and indent level
     * @param programAreas : String
     * @param indentlevel : int
     * @return : TreeMap<Object,Object>
     */
    public TreeMap<Object,Object> getActiveProgramAreaCondition(String programAreas, int indentlevel)
    {
      TreeMap<Object,Object> programAreaTreeMap = null;
      try
      {
        programAreaTreeMap = getSRTMapEJBRef().getActiveProgramAreaConditions(
            programAreas, indentlevel);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

      return programAreaTreeMap;
    }



    public ProgramAreaVO getProgramAreaCondition(String programAreas, int indentLevelNbr, String conditionCd)
    {
       TreeMap<Object,Object> programAreaTreeMap = null;
       ProgramAreaVO programAreaVO = null;
       try
       {
           programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas, indentLevelNbr);
           programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);

        }
        catch(Exception e)
        {

           e.printStackTrace();
        }
        return programAreaVO;
    }

    public ProgramAreaVO getProgramAreaConditionLevel(String programAreaCd, String conditionCd)
    {
       TreeMap<Object,Object> programAreaTreeMap = null;
       ProgramAreaVO programAreaVO = null;
       try
       {
           programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions("('" + programAreaCd + "')");
           programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);
           if(programAreaVO == null)
           programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions("('" + programAreaCd + "')", 2);
           programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);

        }
        catch(Exception e)
        {

           e.printStackTrace();
        }

        return   programAreaVO;
    }

    /**
     * @description retrive desc for lab test code
     * @param code
     * @return
     */
    public String getLabTestDesc(String code)
    {
       String desc = "";
       try
       {
           desc = getSRTMapEJBRef().getLabTestDesc(code);
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }

        return desc;
    }
    
    /**
     * 
     * @param pType
     * @return
     * @throws NEDSSSystemException
     */
    public TreeMap<?,?> getSAICDefinedCodedValuesAsTreeMap(String pType) throws NEDSSSystemException
    {
        TreeMap<?,?> r = new TreeMap<Object,Object>();
        if(pType == null)
        {
            return r;
        }
        //  Create key.
        String strKey = "TreeMap." + pType;
        r = (TreeMap<?,?>)map.get(strKey);
        if(r != null)
        {
            return r;
        }
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        try
        {
		 if (getSRTMapEJBRef() != null)
		   tm = getSRTMapEJBRef().getSAICDefinedCodedValues(pType);

        r = reverseMap(tm);
        map.put(strKey, r);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
          throw new NEDSSSystemException (ex.getMessage());
      }
        return r;
    }
    public  String getCntyDescTxt(String code){
		String cntydesc="";
    	   try
    	   {
    	        if (getSRTMapEJBRef() != null)
    	        	cntydesc = getSRTMapEJBRef().getCountyDesc(code);
    	   }
    	   catch (Exception e)
    	   {
    	       e.printStackTrace();
    	   }
    	   return cntydesc;
    	
}	 
    public String getCodeShortDescTxt(String code, String codeSetNm)
    {

        String codeDescTxt = "";
        String strMaps = (String)codeShortDescMap.get(code+"^"+codeSetNm);

        if (strMaps != null && !strMaps.equals("") && strMaps.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return strMaps;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               codeDescTxt = getSRTMapEJBRef().getCodeShortDescTxt(code, codeSetNm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        codeShortDescMap.put(code+"^"+codeSetNm, codeDescTxt);

        return codeDescTxt;
    }
    
    public String getJurisdictionCodedSortedValuesNoExport()
    {

        String type = "getJurisdictionCodedSortedValuesNoExport";
        StringBuffer sb = new StringBuffer("");
        StringBuffer other = new StringBuffer("");
        TreeMap<?,?> treeMap = (TreeMap<?,?>)map.get(type);

        if (cachedJurisdictionCodeNoExport != null &&
        		cachedJurisdictionCodeNoExport.length() <= NEDSSConstants.SRT_MAX_LENGTH)
        {

            return cachedJurisdictionCodeNoExport;
        }

        try
        {
             if (getSRTMapEJBRef() != null)
               treeMap = getSRTMapEJBRef().getJurisdictionNoExpCodedValues();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (treeMap != null)
        {
            performCaseInsensitiveSortAndFormat(treeMap,other, sb);

        }

        sb.append(other);
        jurisdictionUnknown = sb;
        cachedJurisdictionCodeNoExport = sb.toString();

        return cachedJurisdictionCodeNoExport;
    }
    
    public TreeMap<Object,Object> getConditionTracingEnableInd()
    {
   	  if(cachedContactTracingEnableIND == null)
       {
         try {
        	 cachedContactTracingEnableIND = getSRTMapEJBRef().getConditionTracingEnableInd();
         }
         catch (Exception ex)
          {
             return new TreeMap<Object,Object>();
          }
        }

        return cachedContactTracingEnableIND;
    }
    
    public ArrayList<Object> getDefaultDispCntrlList()
    {
   	  if(defaultDispCntrlList == null){
   		  
         try {
        	 defaultDispCntrlList = (ArrayList)getSRTMapEJBRef().getDefaultDisplayControlDesc();
        	
        	 }
          catch (Exception ex)
          {
        	  logger.error(ex.getMessage());
        	  ex.printStackTrace();
          }
   	  }

        return defaultDispCntrlList;
    }
    public String getDefaultDispCntrlDesc(String code){
    	ArrayList<Object> list = getDefaultDispCntrlList();
    	String desc =null;
    	if(list != null){
    		 Iterator iter = list.iterator();
    		 while(iter.hasNext()){
    			 DropDownCodeDT dt = (DropDownCodeDT)iter.next(); 
    			 if(dt.getKey().toString().equals(code)){
    				 desc = dt.getValue();
    			 }
    		 }
    	}
    	return desc;
    }
    
    public Map getCodesetnmMap(){
    	Map<Object, Object> codeSetmap = new HashMap();
    	try{
    	ArrayList<Object>  codeSetList = getSRTMapEJBRef().getAllActiveCodeSetNmsByGroupId();
    	if(codeSetList != null){
    		Iterator<Object> iter = codeSetList.iterator();
    		while(iter.hasNext()){
    			DropDownCodeDT dDownDT = (DropDownCodeDT)iter.next();
    			codeSetmap.put(dDownDT.getKey(), dDownDT.getValue());
    			
    		}
    	}
    	}catch(Exception e){
    		logger.error("Error while getting the codesetnm from the group Id"+e.getMessage());
    	}
    	return codeSetmap;
    }
    
    public  String getTheCodeSetNm(Long codeSetGroupId){
    	Map<Object, Object> codeSetmap = new HashMap();
    	try{
    		codeSetmap = getCodesetnmMap();
    	}catch(Exception e){
    		logger.error("Error while getting the codesetnm from the group Id"+e.getMessage());
    	}
    	return (String)codeSetmap.get(codeSetGroupId.toString());
    }
    
    public String getConditionTracingEnableInd(String conditionCd)
    {
    	//TODO:Need to get it from cache once porting is finished for DMB
         try {
        	 cachedContactTracingEnableIND = getSRTMapEJBRef().getConditionTracingEnableInd();
         }
         catch (Exception ex)
          {
        	 ex.printStackTrace();
        	 return null;
          }
        return (String)cachedContactTracingEnableIND.get(conditionCd);
    }
    
    public String getCdForCodedResultDescTxt(String descTxt, String codeSetNm) 
    {
        ArrayList<Object> aList = getCodedResultValueList();
        StringBuffer cd = new StringBuffer("");
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getValue().equals(descTxt)) {
                    cd.append(ddcDT.getKey());
                    break;
                }
            }
        }
        return cd.toString();
    }

	
	

}