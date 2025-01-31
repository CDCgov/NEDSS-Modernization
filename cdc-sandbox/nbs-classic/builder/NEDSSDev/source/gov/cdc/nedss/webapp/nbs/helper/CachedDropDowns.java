package gov.cdc.nedss.webapp.nbs.helper;

import gov.cdc.nedss.entity.person.vo.PersonRaceVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CodeLookupDT;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Comparator;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;

/**
 * Title: CachedDropDowns Description: Makes cached SRT codes available in
 * Web Server
 *
 * @author: Pradeep Sharma
 * @version 1.0 PAM development
 */
public class CachedDropDowns {
    private static SRTMap srtm = null;
    private static HashMap<Object,Object> cachedMap = new HashMap<Object,Object>();
    private static HashMap<Object,Object> dropDownListMap = new HashMap<Object,Object>();
    private static ArrayList<Object> countryList = new ArrayList<Object> ();
    private static ArrayList<Object> birthCountryList = new ArrayList<Object> ();
    private static HashMap<Object,Object> conditionPageMap = new HashMap<Object,Object>();
    private static HashMap<Object,Object> coinfectionConditionMap = new HashMap<Object,Object>();
    private static Map<String, String> phinToNBSCodesMap = new HashMap<String, String>();
    private static Map<String, CodeLookupDT> labCodingSystem = new HashMap<String, CodeLookupDT>();
    private static HashMap<Object, Object> elrXREFMap = new HashMap<Object,Object>(); 

    public static final LogUtils logger = new LogUtils(CachedDropDowns.class.getName());

    /**
     * Resets the Cache
     * @param classNm
     * @throws Exception
     */
    public static void resetSRTCache() throws Exception
    {
        String classNm = CachedDropDowns.class.getName();
        Field fields[] = CachedDropDowns.class.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i)
        {
            Field field = fields[i];
            String fieldTypeNm = field.getType().getName();
            if (fieldTypeNm.equals("java.util.Map")) {
                fields[i].set(classNm, new TreeMap<Object,Object>());
            } else if (fieldTypeNm.equals("java.util.TreeMap")) {
                fields[i].set(classNm, new TreeMap<Object,Object>());
            } else if (fieldTypeNm.equals("java.util.HashMap")) {
                fields[i].set(classNm, new HashMap<Object,Object>());
            } else if (fieldTypeNm.equals("java.lang.String")) {
                fields[i].set(classNm, null);
            } else if (fieldTypeNm.equals("java.util.ArrayList")) {
                fields[i].set(classNm, new ArrayList<Object>());
            }
        }
    }

    /** Returns the map with the appropriate key. */
    public static ArrayList<Object> getDropDownList(String key)
    {
        return (ArrayList<Object> ) dropDownListMap.get(key);
    }

    public static ArrayList<Object> getCachedDropDownList(String key)
    {
        if(key.equals(NEDSSConstants.PROG_AREA))
            getProgramAreaList();
        if(key.equals(NEDSSConstants.STATE_LIST))
            getStateList();
        else if(key.equals(NEDSSConstants.JURIS_LIST))
            getJurisdictionList();
        else if(key.equals(NEDSSConstants.JURIS_ALERT_LIST))
            getAlertJurisdictionList();
        else if(key.equals(NEDSSConstants.ASIAN) || key.equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
            getRaceCodes(key);
        else if (key.equals("PHC_TYPE")) 
			return getPublishedConditionDropDown("");
        else if(key.equals(NEDSSConstants.CODE_SET_NMS))
            return getAllActiveCodeSetNms();
        return (ArrayList<Object> ) getCodedValueForType(key);
    }

    public  static ArrayList<Object> getCountryList()
    {
        try {
            if (cachedMap.get(NEDSSConstants.COUNTRY_LIST) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = null;
                    dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    countryList.add(dDownDT);
                    ArrayList<Object> cList = getSRTMapEJBRef().getCountryCodesList();
                    //iterate through the list and get US out and add it as first in the list
                    DropDownCodeDT usDT = new DropDownCodeDT();
                    int remove = 0;
                    Iterator<Object>  iter = cList.iterator();
                    while(iter.hasNext()) {
                        DropDownCodeDT dt = (DropDownCodeDT) iter.next();
                        if(dt.getKey().equals("840") ) {
                            usDT = dt; remove = cList.indexOf(dt);
                        }
                    }
                    countryList.add(usDT); cList.remove(remove);
                    countryList.addAll(cList);
                    cachedMap.put(NEDSSConstants.COUNTRY_LIST, countryList);
                }
            } else {
                countryList = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.COUNTRY_LIST);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getCountryList");
        }
        return countryList;
    }

    public  static ArrayList<Object> getRaceCodes(String type)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(type) == null) {
                if (getSRTMapEJBRef() != null) {

                    DropDownCodeDT dDownDT = null;
                    dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getRaceCodesList(type));
                    cachedMap.put(type, list);
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(type);
            }
        } catch (Exception ex) {
            logger.error("There is no value for Race code " + type);
        }
        return list;
    }

    public  static PersonRaceVO  getRaceAndCategoryCode(String raceCd)
    {
        PersonRaceVO personRaceVO = new PersonRaceVO();
        TreeMap<Object,Object> map = new TreeMap<Object,Object>();
        try {
            if (cachedMap.get("RACE_CATEGORY") == null) {
                if (getSRTMapEJBRef() != null) {
                    map =(getSRTMapEJBRef().getRaceCodesAndCategory());
                    cachedMap.put("RACE_CATEGORY", map);
                }
            }
            map= (TreeMap<Object,Object>) cachedMap.get("RACE_CATEGORY");
            if(map.get(raceCd)!=null)
                personRaceVO=(PersonRaceVO) map.get(raceCd);
        } catch (Exception ex) {
            logger.error("There is no value for Race code " + personRaceVO);
        }
        return personRaceVO;
    }


    public  static ArrayList<Object> getCountyCodes(String type)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        String stateCounty = type+"county";
        if (cachedMap.get(stateCounty) == null) {
            try {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> counties = getSRTMapEJBRef().getCountyCodes(type);
                    if (counties != null && counties.size() > 0) {
                        Set<Object> keyset = counties.keySet();
                        Iterator<Object>  ite = keyset.iterator();

                        DropDownCodeDT dDownDT = null;
                        while (ite.hasNext()) {
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) ite.next());
                            dDownDT.setValue((String)counties.get(dDownDT.getKey()));
                            list.add(dDownDT);
                        }
                        //HttpSession session = request.getSession();
                        if (list.size() > 0){
                            //session.setAttribute(type, list);
                            cachedMap.put(stateCounty, list);
                            dropDownListMap.put(stateCounty,list);
                        }
                        else
                            logger.error("There is no value for country code " + type );
                    }
                    else {
                        ArrayList<Object> countyList = (ArrayList<Object> ) cachedMap.get(stateCounty);
                        //request.getSession().setAttribute(type, countryList);
                        return countyList;
                    }
                }
            } catch (Exception ex) {
                logger.error("there is no getCountryCodes for type " + type );
            }
        }
        else{
            list = (ArrayList<Object> )cachedMap.get(stateCounty);
        }
        return list;
    }

    private  static SRTMap getSRTMapEJBRef() throws Exception
    {
        if (srtm == null) {
            NedssUtils nu = new NedssUtils();
            Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);
            if (objref != null) {
                SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref, SRTMapHome.class);
                srtm = home.create();
            }
        }
        return srtm;
    }


    public  static void setArrayIntoList(String[][] array, String type, HttpServletRequest request)
    {
        ArrayList<Object> cachedList = null;
        boolean updateCachedList = !dropDownListMap.containsKey(type);
        if (cachedMap.get(type) == null) {
            if (array != null && array.length > 0) {
                ArrayList<Object> list = new ArrayList<Object> ();
                if (updateCachedList) {
                    cachedList = new ArrayList<Object> ();
                    dropDownListMap.put(type, cachedList);
                }
                DropDownCodeDT dDownDT = null;
                for (int i = 0; i < array.length; i++) {
                    dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(array[i][0]);
                    dDownDT.setValue(array[i][1]);
                    list.add(dDownDT);
                    if (updateCachedList) {
                        cachedList.add((dDownDT));
                    }
                    if (list.size() > 0)
                        request.getSession().setAttribute(type, list);
                    else
                        logger.error("There is no value for code_set_nm "
                                + type + " in code_Value_General ");
                }
            }
        }
    }


    public  static ArrayList<Object> getAllCitiesList(String stateCd)
    {
        ArrayList<Object> allCityCodeList= new ArrayList<Object> ();
        ArrayList<Object> allCityList= new ArrayList<Object> ();
        try {
            if (cachedMap.get(stateCd)==null) {
                if (getSRTMapEJBRef() != null) {
                    allCityCodeList= getSRTMapEJBRef().getAllCityCodes(stateCd);
                }
                if(allCityCodeList!=null && allCityCodeList.size()>0){
                    Iterator<Object>  it = allCityCodeList.iterator();
                    while(it.hasNext()){
                        DropDownCodeDT dropDownCodeDT = (DropDownCodeDT)it.next();
                        String text = dropDownCodeDT.getValue();
                        String newText = text.substring(0, text.indexOf(","));
                        dropDownCodeDT.setValue(newText);
                        allCityList.add(dropDownCodeDT);
                    }
                }
                cachedMap.put(stateCd, allCityList);
            }
            else{
                allCityList =(ArrayList<Object> )cachedMap.get(stateCd);
            }
        }catch (Exception ex) {
            logger.error("there is no getAllCitiesList");
        }
        return allCityList;
    }
    /*
    private static void addEmptyValue(ArrayList<Object>  srtList, HttpServletRequest req, String type) {

        ArrayList<Object> list = new ArrayList<Object> ();
        DropDownCodeDT dDownDT = null;
        dDownDT = new DropDownCodeDT();
        dDownDT.setKey(""); dDownDT.setValue("");
        list.add(dDownDT);
        list.addAll((ArrayList<Object> )cachedMap.get(type));
        req.getSession().setAttribute(type, list);

    }
    */

    public static ArrayList<Object> getCodedValueForType(String type)
    {

        ArrayList<Object> list = new ArrayList<Object> ();
        if(type == null) return list;
        try {
            if (cachedMap.get(type) != null) {
                list = (ArrayList<Object> ) cachedMap.get(type);
            } else {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = null;
                    dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getCodedValuesList(type));
                    if (list != null && list.size() > 0) {
                        cachedMap.put(type, list);
                    } else
                        logger.error("There is no value for code_set_nm "
                                + type + " in code_Value_General ");
                }

            }
        } catch (Exception ex) {
            logger.error("there is no code_set_nm type " + type
                    + " in code_Value_General ");
        }
        return list;
    }
    
    public static ArrayList<Object> getNullFlavorCodedValueForType(String type)
    {
        
        ArrayList<Object> list = new ArrayList<Object> ();
        if(type == null) return list;
        try {
        	String nullFlavourType = type+NEDSSConstants.NULL_FLAVOUR_OID;
            if (cachedMap.get(nullFlavourType) != null) {
                list = (ArrayList<Object> ) cachedMap.get(nullFlavourType);
            } else {
                if (getSRTMapEJBRef() != null) {
                    list.addAll(getSRTMapEJBRef().getNullFlavorCodedValuesList(type));
                    if (list != null && list.size() > 0) {
                        cachedMap.put(nullFlavourType, list);
                    } else
                        logger.error("There is no value for code_set_nm "
                                + type + " in code_Value_General ");
                }

            }
        } catch (Exception ex) {
            logger.error("there is no code_set_nm type " + type
                    + " in code_Value_General ");
        }
        return list;
    }

    /**
     *
     * @param type
     * @param flag if you need empty space in you drop down then pass is true.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Object> getCodedValueForType(String type, boolean flag)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        if(type == null) return list;
        try {
            if (cachedMap.get(type) != null) {
                list = (ArrayList<Object> ) cachedMap.get(type);
            } else {
                if (getSRTMapEJBRef() != null) {
                    if(flag){
                        DropDownCodeDT dDownDT = null;
                        dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                    }
                    list.addAll(getSRTMapEJBRef().getCodedValuesList(type));
                    if (list != null && list.size() > 0) {
                        cachedMap.put(type, list);
                    } else
                        logger.error("There is no value for code_set_nm "
                                + type + " in code_Value_General ");
                }

            }
        } catch (Exception ex) {
            logger.error("there is no code_set_nm type " + type
                    + " in code_Value_General ");
        }
        return list;
    }

	public static ArrayList<Object> getStateList()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.STATE_LIST) == null) {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> map = getSRTMapEJBRef().getStateCodes("");
                    if (map != null && map.size() > 0) {
                        Set<Object> keyset = map.keySet();
                        Iterator<Object>  ite = keyset.iterator();
                        list = new ArrayList<Object> ();
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        while (ite.hasNext()) {
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) ite.next());
                            dDownDT
                                    .setValue((String) map
                                            .get(dDownDT.getKey()));
                            list.add(dDownDT);
                        }

                    }
                    cachedMap.put(NEDSSConstants.STATE_LIST, list);
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.STATE_LIST);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getStateList:");
        }
        return list;
    }

    public static ArrayList<Object> getProgramAreaList()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.PROG_AREA) == null) {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> pAreas = getSRTMapEJBRef()
                            .getProgramAreaCodedValues();
                    if (pAreas != null && pAreas.size() > 0) {
                        Set<Object> keyset = pAreas.keySet();
                        Iterator<Object>  ite = keyset.iterator();
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        while (ite.hasNext()) {
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) ite.next());
                            dDownDT.setValue((String) pAreas.get(dDownDT
                                    .getKey()));
                            list.add(dDownDT);
                        }
                        if (list.size() > 0) {
                            cachedMap.put(NEDSSConstants.PROG_AREA, list);
                        }

                    }
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.PROG_AREA);
            }
        } catch (Exception ex) {
            logger.error("Exception in getProgramAreas in CachedDropDowns.");
        }
        return list;
    }

    public static ArrayList<Object> getJurisdictionList()
    {
        ArrayList<Object> list = null;
        try {
            if (cachedMap.get(NEDSSConstants.JURIS_LIST) == null) {
                list = new ArrayList<Object> ();
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> juris = getSRTMapEJBRef()
                            .getJurisdictionCodedValues();
                    if (juris != null && juris.size() > 0) {
                        Set<Object> keyset = juris.keySet();
                        TreeSet<Object>set = new TreeSet<Object>(new Comparator<Object>() {
                            public int compare(Object obj, Object obj1) {
                                return ((Comparable<Object>) ((Map.Entry) obj).getValue()).compareTo(((Map.Entry) obj1).getValue());
                            }
                        });

                        set.addAll(juris.entrySet());
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        for (Iterator<Object> i = set.iterator(); i.hasNext();) {
                            Map.Entry entry = (Map.Entry) i.next();
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


    public static ArrayList<Object> getJurisdictionNoExpList()
    {
        ArrayList<Object> list = null;
        try {
            if (cachedMap.get(NEDSSConstants.JURIS_NO_EXPORT_LIST) == null) {
                list = new ArrayList<Object> ();
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> juris = getSRTMapEJBRef()
                            .getJurisdictionNoExpCodedValues();
                    if (juris != null && juris.size() > 0) {
                        Set<Object> keyset = juris.keySet();
                        TreeSet<Object>set = new TreeSet<Object>(new Comparator<Object>() {
                            public int compare(Object obj, Object obj1) {
                                return ((Comparable<Object>) ((Map.Entry) obj).getValue()).compareTo(((Map.Entry) obj1).getValue());
                            }
                        });

                        set.addAll(juris.entrySet());
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        for (Iterator<Object> i = set.iterator(); i.hasNext();) {
                            Map.Entry entry = (Map.Entry) i.next();
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) entry.getKey());
                            dDownDT.setValue((String) entry.getValue());
                            list.add(dDownDT);
                        }

                        if (list.size() > 0) {
                            cachedMap.put(NEDSSConstants.JURIS_NO_EXPORT_LIST, list);
                        }

                    }
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.JURIS_NO_EXPORT_LIST);
            }
        } catch (Exception ex) {
            logger
                    .error("Error while loading jurisdictions in getJurisdictionList: CachedDropDowns. ");
        }
        return list;
    }

    public static ArrayList<Object> getExportJurisdictionList()
    {
        ArrayList<Object> list = null;
        try {
            if (cachedMap.get(NEDSSConstants.EXPORT_JURIS_LIST) == null) {
                list = new ArrayList<Object> ();
                if (getSRTMapEJBRef() != null) {
                    Collection<Object>  exportJuris = getSRTMapEJBRef()
                            .getExportJurisdictionCodedValues();
                    if (exportJuris != null && exportJuris.size() > 0) {
                        //DropDownCodeDT dDownDT = new DropDownCodeDT();
                        //dDownDT.setKey(""); dDownDT.setValue("");
                        //list.add(dDownDT);
                        Iterator<Object>  it = exportJuris.iterator();
                        while(it.hasNext()){
                            DropDownCodeDT dDT = (DropDownCodeDT)(it.next());
                            if(dDT.getAltValue()!=null && dDT.getAltValue().equalsIgnoreCase("T")){
                                DropDownCodeDT dropdownDT = new DropDownCodeDT();
                                dropdownDT.setKey(dDT.getKey());
                                dropdownDT.setValue(dDT.getValue());
                                list.add(dDT);
                            }
                        }
                        if (list.size() > 0) {
                            cachedMap.put(NEDSSConstants.EXPORT_JURIS_LIST, list);
                        }
                    }
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.EXPORT_JURIS_LIST);
            }
        } catch (Exception ex) {
            logger
                    .error("Error while loading Export jurisdictions in getExportJurisdictionList: CachedDropDowns. ");
        }
        return list;
    }

    public static ArrayList<Object> getConditionFamilyList(String conditionCd)
    {
        ArrayList<Object> conditionFamilyList = new ArrayList<Object> ();
        try {
                if (getSRTMapEJBRef() != null) {
                        DropDownCodeDT dDownDT = null;
                        dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        conditionFamilyList.add(dDownDT);
                        conditionFamilyList.addAll(getSRTMapEJBRef().getConditionFamilyList(conditionCd));
                 }



        } catch (Exception ex) {
            logger
                    .error("Error while loading Condition Family in getConditionFamilyList: CachedDropDowns. ");
        }

        //should be greater than 1, remove the original condition code
        Iterator<Object> ite = conditionFamilyList.iterator();
        while (ite.hasNext()) {
            DropDownCodeDT dropDownDT = (DropDownCodeDT) ite.next();
            if (dropDownDT.getKey().matches(conditionCd))
                ite.remove();
        }
        return conditionFamilyList;
    }

    public static TreeMap<Object,Object> reverseMap(TreeMap<Object,Object> pMap)
    {

        DropDownCodeDT dropdownDT= new DropDownCodeDT();
        Set<Object> keyset = pMap.keySet();
        //  Reverse keys and values.
        TreeMap<Object,Object> reversedMap = new TreeMap<Object,Object>();
        Iterator<Object>  i = keyset.iterator();
        while (i.hasNext())
        {
            dropdownDT= new DropDownCodeDT();
            String key= (String)i.next();
            //String value = (String)pMap.get(i);
            String value = new String();
            value = (String)pMap.get(key);
            dropdownDT.setValue(value);
            dropdownDT.setKey(key);
            reversedMap.put(value,dropdownDT);
        }
        return reversedMap;
    }

    private static Collection<Object>  reverseList(ArrayList<Object>  list)
    {
        ArrayList<Object> coll = new ArrayList<Object> ();
        if(list!=null){
            Iterator<Object>  it = list.iterator();
            TreeMap<Object,Object> map = new TreeMap<Object,Object>();
            while (it.hasNext())
            {
                UserProfileDT  userProfileDT = (UserProfileDT)it.next();
                String key= userProfileDT.getFULL_NM().toLowerCase();
                map.put(key, userProfileDT);
            }
            coll.addAll(map.values());
        }
        return coll;
    }

    public static ArrayList<Object> getAlertJurisdictionList()
    {
        //ArrayList<Object> list = null;
        ArrayList<Object> modifiedList = null;
        try {
            if (cachedMap.get(NEDSSConstants.JURIS_ALERT_LIST) == null) {
                modifiedList = new ArrayList<Object> ();
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> juris = getSRTMapEJBRef()
                            .getJurisdictionCodedValues();
                    juris = reverseMap(juris);
                    if (juris != null && juris.size() > 0) {
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey("ALL");
                        dDownDT.setValue("ALL");
                        juris.put(dDownDT.getValue(), dDownDT);
                        DropDownCodeDT dDownDT1 = new DropDownCodeDT();
                        dDownDT1.setKey("Not Assigned");
                        dDownDT1.setValue("Not Assigned");
                        juris.put(dDownDT1.getValue(), dDownDT1);
                        modifiedList.addAll(juris.values());
                        cachedMap.put(NEDSSConstants.JURIS_ALERT_LIST, modifiedList);
                    }
                }
            } else {
                modifiedList = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.JURIS_ALERT_LIST);
            }
        } catch (Exception ex) {
            logger
                    .error("Error while loading jurisdictions in getAlertJurisdictionList: CachedDropDowns. "+ex.getMessage());
        }
        return modifiedList;
    }

    public static ArrayList<Object> getAllQECodes(boolean reload, String Type)
    {
        ArrayList<Object> allQECodes= new ArrayList<Object> ();
        try {
            if(Type.equalsIgnoreCase("PRV")){
                if (cachedMap.get("QECodes")==null || reload) {
                    if (getSRTMapEJBRef() != null) {
                        allQECodes= getSRTMapEJBRef().getAllQECodes(Type);
                    }
                    cachedMap.put("QECodes", allQECodes);
                }
                else{
                    allQECodes =(ArrayList<Object> )cachedMap.get("QECodes");
                }
            }else if(Type.equalsIgnoreCase("ORG")){
                if (cachedMap.get("QECodesORG")== null || reload) {
                //if(true){
                    if (getSRTMapEJBRef() != null) {
                        allQECodes= getSRTMapEJBRef().getAllQECodes(Type);
                    }
                    cachedMap.put("QECodesORG", allQECodes);
                }
                else{
                    allQECodes =(ArrayList<Object> )cachedMap.get("QECodesORG");
                    }
            }
        }catch (Exception ex) {
            logger.error("Error in retrieving All QuickEntry Codes");
        }
        return allQECodes;
    }

    public  static ArrayList<Object> getAllConditions()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            //if (cachedMap.get(NEDSSConstants.CONDITION_CD) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getAllConditionCodes());
                }
                    cachedMap.put(NEDSSConstants.CONDITION_CD, list);
            /*} else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.CONDITION_CD);
            }*/
        } catch (Exception ex) {
            logger.error("Error raised in getAllConditions:");
        }
        return list;
    }

    public  static ArrayList<Object> getAvailableConditions(String busObjType)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    //dDownDT.setKey(""); dDownDT.setValue("");
                    //list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getAvailableConditionCode(busObjType));
                }
        } catch (Exception ex) {
            logger.error("Error raised in getAvailableConditions:");
        }
        return list;
    }

    public static ArrayList<Object> getLaboratoryIds()
    {
        ArrayList<Object> allLabIds = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.LABORATORY_IDS) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    allLabIds.add(dDownDT);
                    allLabIds.addAll(getSRTMapEJBRef().getlaboratoryIds());
                    Iterator<Object>  iter = allLabIds.iterator();
                    while (iter.hasNext()) {
                        DropDownCodeDT dt = (DropDownCodeDT) iter.next();
                        String value = dt.getValue();
                        if (value.length() > 50)
                            value = value.substring(0, 50).concat("...");
                        dt.setValue(value);
                    }
                    //cachedMap.put(NEDSSConstants.LABORATORY_IDS, allLabIds);
                }
            }else {
                allLabIds = (ArrayList<Object> ) cachedMap
                        .get(NEDSSConstants.LABORATORY_IDS);
            }
        } catch (Exception ex) {
            logger.error("Error in retrieving All Laboratory Ids");
        }
        return allLabIds;
    }

    public static ArrayList<Object> getTestTypeList() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();

        if (cachedMap.get(NEDSSConstants.LAB_TEST_TYPES) == null) {
            DropDownCodeDT dDownDT = new DropDownCodeDT();
            dDownDT.setKey(""); dDownDT.setValue(""); list.add(dDownDT);
            DropDownCodeDT dt1 = new DropDownCodeDT();
            dt1.setKey("O"); dt1.setValue("Ordered"); list.add(dt1);
            DropDownCodeDT dt2 = new DropDownCodeDT();
            dt2.setKey("R"); dt2.setValue("Resulted"); list.add(dt2);

            cachedMap.put(NEDSSConstants.LAB_TEST_TYPES, list);
        } else {
            list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.LAB_TEST_TYPES);
        }

        return list; 
    }
    
    public  static ArrayList<Object> getAllCodeSetNms() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getAllCodeSetNms());
            }
                cachedMap.put(NEDSSConstants.CODE_SET_NMS, list);

        } catch (Exception ex) {
            logger.error("Error raised in getAllCodeSetNms:");
        }
        return list;
    }

    public  static ArrayList<Object> getEmailUsers() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
             logger.debug("Entered into the getEmailUsers()of CachedDropDowns");
            if (cachedMap.get(NEDSSConstants.Nedss_Entry_Id) == null) {
                    list.addAll(getSRTMapEJBRef().getNedssUserlist());
                    list=(ArrayList<Object> )reverseList(list);
                    cachedMap.put(NEDSSConstants.Nedss_Entry_Id, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.Nedss_Entry_Id);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getEmailUsers:");
        }

        return list;
    }
    
    public  static ArrayList<Object> getUsersWithValidEmailLst() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
             logger.debug("Entered into the getUsersWithValidEmailLst()of CachedDropDowns");
            if (cachedMap.get(NEDSSConstants.Users_With_Valid_Email) == null) {
                    list.addAll(getSRTMapEJBRef().getUsersWithValidEmailLst());
                    list =(ArrayList<Object> )reverseList(list);
                    Iterator<Object>  iter = list.iterator();
                    while (iter.hasNext()) {
                        UserProfileDT dt = (UserProfileDT) iter.next();
                        String full_nm = dt.getFULL_NM();
                        if (full_nm.length() > 50)
                            full_nm = full_nm.substring(0, 50).concat("...");
                        dt.setFULL_NM(full_nm);
                    }
                    cachedMap.put(NEDSSConstants.Users_With_Valid_Email, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.Users_With_Valid_Email);
                list =(ArrayList<Object> )reverseList(list);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getUsersWithValidEmailLst:");
        }
        return list;
    }
    
    public  static ArrayList<Object> getUsersWithActiveAlerts() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
             logger.debug("Entered into the getUsersWithActiveAlerts()of CachedDropDowns");
            if (cachedMap.get(NEDSSConstants.Users_With_Active_Alert) == null) {
                    list.addAll(getSRTMapEJBRef().getUsersWithActiveAlerts());
                    cachedMap.put(NEDSSConstants.Users_With_Active_Alert, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.Users_With_Active_Alert);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getUsersWithActiveAlerts:");
        }
        return list;
    }
    
    public  static ArrayList<Object> getAllCodeSystemCdDescs(String codeSetNm) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        String cdSysDescTxtKey = codeSetNm + "key";
        try {
            if (cachedMap.get(cdSysDescTxtKey) == null) {
                if (getSRTMapEJBRef() != null) {
                    //DropDownCodeDT dDownDT = new DropDownCodeDT();
                    //dDownDT.setKey(""); dDownDT.setValue("");
                    //list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getAllCodeSystemCdDescs(codeSetNm));
                }
                    cachedMap.put(cdSysDescTxtKey, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(cdSysDescTxtKey);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getAllCodeSystemCdDescs:");
        }
        return list;
    }
    
    // This Method is a temp fix for  alerting Phc-Event
    public static ArrayList<Object> getPhcEvents() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();

        try {
            DropDownCodeDT dDownDT = new DropDownCodeDT();
            dDownDT.setKey(""); dDownDT.setValue("");
            dDownDT.setKey("11648804");
            dDownDT.setValue("Laboratory Report");
            list.add(dDownDT);
            cachedMap.put("PUBLIC_HEALTH_EVENT", list);
            list = (ArrayList<Object> ) cachedMap.get("PUBLIC_HEALTH_EVENT");

        } catch (Exception ex) {
            logger.error("Error raised in getPhcEvents:");
        }
        return list;

    }

    public static void resetCachedValues(String code)
    {
        cachedMap.remove(code);
    }
    
    public  static String getCodeDescTxtForCd(String code, String codeSetNm) 
    {
        ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSetNm);
        StringBuffer desc = new StringBuffer("");
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    desc.append(ddcDT.getValue());
                    break;
                }
            }
        }
        return desc.toString();
    }
    
    public  static String getCdForCdDescTxt(String descTxt, String codeSetNm) 
    {
        ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSetNm);
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
    
    public  static String getJurisdictionDesc(String code) 
    {
        String value=null;
        Collection<Object>  jurisdictionList = CachedDropDowns.getJurisdictionList();
        if (jurisdictionList != null && jurisdictionList.size() > 0) {
            Iterator<Object>  ite = jurisdictionList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    value=ddcDT.getValue();
                    break;
                }
            }
        }
        return value;
    }
    
    public  static String getProgAreadDesc(String code) 
    {
        String value=null;
        Collection<Object>  programAreaList = CachedDropDowns.getProgramAreaList();
        if (programAreaList != null && programAreaList.size() > 0) {
            Iterator<Object>  ite = programAreaList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    value=ddcDT.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static String getConditionDesc(String code) 
    {
        String value=null;
        Collection<Object>  conditionList = CachedDropDowns.getAllConditions();
        if (conditionList != null && conditionList.size() > 0) {
            Iterator<Object>  ite = conditionList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    value=ddcDT.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static TreeMap<Object,Object> getConditionCdAndInvFormCd()
    {
             TreeMap<Object,Object> treeMap = new TreeMap<Object,Object>();
               try
               {
                    if (getSRTMapEJBRef() != null)
                      treeMap = getSRTMapEJBRef().getConditionCdAndInvFormCd();
               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }
               return treeMap;
             }
    
    
    
    /**
     * This method returns a list of DropDownCodeDT with desc from code_short_desc_txt (Standard way)
     * and code from code_desc_txt (Special way as column 'cd' cannot accomodate lengthy values)
     * @param type java.lang.String
     * @return java.util.ArrayList
     */
    private static ArrayList<Object> getSRTAdminCodedValue(String codeSetNm) 
    {

        ArrayList<Object> list = new ArrayList<Object> ();
        if (codeSetNm == null)
            return list;
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = null;
                dDownDT = new DropDownCodeDT();
                dDownDT.setKey("");
                dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getSRTAdminCodedValue(codeSetNm));
                if (list != null && list.size() == 0)
                    logger.error("There is no value for code_set_nm " + codeSetNm + " in code_Value_General ");
            }
        } catch (Exception ex) {
            logger.error("there is no code_set_nm type " + codeSetNm + " in code_Value_General ");
        }
        return list;
    }
    
    public static ArrayList<Object> getSRTAdminAssignAuth()
    {
        return getSRTAdminCodedValue("ASSGN_AUTHORITY");
    }
    
    public static ArrayList<Object> getSRTAdminCodingSysCd()
    {
        return getSRTAdminCodedValue("CODE_SYSTEM");
    }

//    public static ArrayList<Object> getConditionCoInfGroup(){
//        return getSRTAdminCodedValue("CONDITION_COINFGRP");
//    }
    public static String getSRTAdminCVGDesc(String code, String codeSetNm) 
    {
        String value=null;
        Collection<Object>  list = getSRTAdminCodedValue(codeSetNm);
        if (list != null && list.size() > 0) {
            Iterator<Object>  ite = list.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    value=ddcDT.getValue();
                    break;
                }
            }
        }
        return value;
    }
    
    public static ArrayList<Object> getCodingSystemCodes(String assignAuth) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getCodingSystemCodes(assignAuth));
            }
        } catch (Exception ex) {
            logger.error("Error raised in getCodingSystemCodes:");
        }
        return list;
    }


     public static ProgramAreaVO getProgramAreaForCondition(String conditionCode) throws NEDSSSystemException
     {
            ProgramAreaVO programAreaVO = new ProgramAreaVO();

            try {
                if (getSRTMapEJBRef() != null){
                    programAreaVO=getSRTMapEJBRef().getProgramAreaForCondition(conditionCode);
                 }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return programAreaVO;
    }

     /**
      * getLdfHtmlTypes returns all the available html types from NBS_UI_Component
      * @return
      * @throws NEDSSSystemException
      */
    public static ArrayList<Object> getLdfHtmlTypes(String formCd) throws NEDSSSystemException 
    {
         ArrayList<Object> list = null;
             list = new ArrayList<Object> ();
                try {
                    if (getSRTMapEJBRef() != null) {
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        list.addAll(getSRTMapEJBRef().getLdfHtmlTypes(formCd));
                    }
                } catch (Exception ex) {
                    logger.error("Error raised in getLdfHtmlTypes:");
                }

         return list;
    }
    
    public static ArrayList<Object> getAvailableTabs(String ldfPageId) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getAvailableTabs(ldfPageId));
            }
        } catch (Exception ex) {
            logger.error("Error raised in getAvailableTabs:");
        }
        return list;
    }
    
    public static ArrayList<Object> getLDFSections(Long tabId) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getLDFSections(tabId));
            }
        } catch (Exception ex) {
            logger.error("Error raised in getLDFSections:");
        }
        return list;
    }

    public static ArrayList<Object> getLDFSubSections(Long sectionId) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getLDFSubSections(sectionId));
            }
        } catch (Exception ex) {
            logger.error("Error raised in getLDFSections:");
        }
        return list;
    }

    public static ArrayList<Object> getCodesetNames() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getCodesetNames());
            }
        } catch (Exception ex) {
            logger.error("Error raised in getCodesetNames:");
        }
        return list;
    }

    public static TreeMap<Object,Object> getInvFrmCdLdfPgIdMap() 
    {
        TreeMap<Object,Object> returnMap = null;
        try {
            if (cachedMap.get("InvFrmCdLdfPgIdMap") == null) {
                if (getSRTMapEJBRef() != null) {
                    returnMap = getSRTMapEJBRef().getInvFrmCdLdfPgIdMap();
                        if (returnMap.size() > 0)
                            cachedMap.put("InvFrmCdLdfPgIdMap", returnMap);
                }
            } else {
                return (TreeMap<Object,Object>)cachedMap.get("InvFrmCdLdfPgIdMap");
            }
        } catch (Exception ex) {
            logger.error("Error while loading getInvFrmCdLdfPgIdMap: CachedDropDowns. ");
        }
        return returnMap;
    }

    /**
     * ISO Country List<Object>
     * @return
     */
    public static ArrayList<Object> getIsoCountryList() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.ISO_COUNTRY_LIST) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getIsoCountryList());
                    cachedMap.put(NEDSSConstants.ISO_COUNTRY_LIST, list);
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.ISO_COUNTRY_LIST);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getIsoCountyList:");
        }
        return list;
    }

    public static ArrayList<Object> getCodedValueOrderdByNbsUid(String type) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        if(type == null) return list;
        try {
            if (cachedMap.get(type) != null) {
                list = (ArrayList<Object> ) cachedMap.get(type);
            } else {
                if (getSRTMapEJBRef() != null) {
                    list.addAll(getSRTMapEJBRef().getCodedValueOrderdByNbsUid(type));
                    if (list != null && list.size() > 0) {
                        cachedMap.put(type, list);
                    } else
                        logger.error("There is no value for code_set_nm "
                                + type + " in code_Value_General ");
                }

            }
        } catch (Exception ex) {
            logger.error("there is no code_set_nm type " + type
                    + " in code_Value_General ");
        }
        return list;
    }


    /**
     * ISO Country List<Object>
     * @return
     */
    public static ArrayList<Object> getExportFacilityListForTransferOwnership() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
             // This should not be cached as every time user creates a receiving facility
             //the drop down needs to be refreshed
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getExportReceivingFacilityList());
                }
        } catch (Exception ex) {
            logger.error("Error raised in getExportFacilityListForTransferOwnership:"+ ex.toString());
        }
        return list;
    }

    public  static String getReceivingFacilityDescTxt(String recFacilityCd) 
    {
        ArrayList<Object> aList = CachedDropDowns.getExportFacilityListForTransferOwnership();
        StringBuffer desc = new StringBuffer("");
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(recFacilityCd)) {
                    desc.append(ddcDT.getValue());
                    break;
                }
            }
        }
        return desc.toString();
    }

    public  static ArrayList<Object> getCountyCodesInclStateWide(String type) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        String stateCounty = type+"countyInclStateWide";
        if (cachedMap.get(stateCounty) == null) {
            try {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> counties = getSRTMapEJBRef().getCountyCodesInclStateWide(type);
                    if (counties != null && counties.size() > 0) {
                        Set<Object> keyset = counties.keySet();
                        Iterator<Object>  ite = keyset.iterator();

                        DropDownCodeDT dDownDT = null;
                        while (ite.hasNext()) {
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) ite.next());
                            dDownDT.setValue((String)counties.get(dDownDT.getKey()));
                            list.add(dDownDT);
                        }
                        if (list.size() > 0){
                            cachedMap.put(stateCounty, list);
                            dropDownListMap.put(stateCounty,list);
                        }
                        else
                            logger.error("There is no value for CountyCodes for State: " + type );
                    }
                    else {
                        ArrayList<Object> countyList = (ArrayList<Object> ) cachedMap.get(stateCounty);
                        return countyList;
                    }
                }
            } catch (Exception ex) {
                logger.error("there is no getCountyCodesInclStateWide for type " + type );
            }
        }
        else{
            list = (ArrayList<Object> )cachedMap.get(stateCounty);
        }
        return list;
    }

    /**
     *
     * @param InConditionForProgArea
     * @return
     */
    public  static ArrayList<Object> getAggregateSummaryReportConditionCode(String InConditionForProgArea) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.AGG_SUM_CONDITION_CD) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getAggregateSummaryReportConditionCode(InConditionForProgArea));
                }
                    cachedMap.put(NEDSSConstants.AGG_SUM_CONDITION_CD, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.AGG_SUM_CONDITION_CD);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getAggregateSummaryReportConditionCode:");
        }
        return list;
    }

    public static ArrayList<Object> getBirthCountryCodes() 
    {
        try {
            if (cachedMap.get(NEDSSConstants.PSL_CNTRY) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = null;
                    dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    birthCountryList.add(dDownDT);
                    ArrayList<Object> cList = getSRTMapEJBRef().getAllCountryCodesOrderByShortDesc();
                    birthCountryList.addAll(cList);
                    cachedMap.put(NEDSSConstants.PSL_CNTRY, birthCountryList);
                }
            } else {
                birthCountryList = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.PSL_CNTRY);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getBirthCountryList");
        }
        return birthCountryList;
    }

    public static ArrayList<Object> getLanguageCodes() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.P_LANG) == null) {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> map = getSRTMapEJBRef().getLanguageCode();
                     ArrayList<Object> as = new ArrayList<Object>( map.entrySet() );

                    Collections.sort( as , new Comparator<Object>() {
                        public int compare( Object o1 , Object o2 )
                        {
                            Map.Entry<Object,Object> e1 = (Map.Entry<Object,Object>)o1;
                            Map.Entry<Object,Object> e2 = (Map.Entry<Object,Object>)o2 ;
                            String first = (String)e1.getValue();
                            String second = (String)e2.getValue();
                            return first.compareTo( second );
                        }
                    });
                     list = new ArrayList<Object> ();
                     DropDownCodeDT dDownDT = new DropDownCodeDT();
                     dDownDT.setKey(""); dDownDT.setValue("");list.add(dDownDT);
                    for(int i=0;i<as.size();i++){
                       String keyVal = as.get(i).toString();
                       String keyCode = (keyVal.substring(0, keyVal.indexOf("=")).trim()).replaceAll("^\\s+", "");
                       String val = (keyVal.substring(keyVal.indexOf("=")+1).trim()).replaceAll("^\\s+", "");
                             dDownDT = new DropDownCodeDT();
                            dDownDT.setKey(keyCode);
                            dDownDT.setValue(val);
                            list.add(dDownDT);

                    }
                    cachedMap.put(NEDSSConstants.P_LANG, list);
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.P_LANG);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getBirthCountryCodes:");
        }
        return list;
    }

    public static ArrayList<Object> getPrimaryOccupationCodes() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.O_NAICS) == null) {
                if (getSRTMapEJBRef() != null) {
                    TreeMap<Object,Object> map = getSRTMapEJBRef().NAICSgetIndustryCode();
                    if (map != null && map.size() > 0) {
                        Set<Object> keyset = map.keySet();
                        Iterator<Object>  ite = keyset.iterator();
                        list = new ArrayList<Object> ();
                        DropDownCodeDT dDownDT = new DropDownCodeDT();
                        dDownDT.setKey(""); dDownDT.setValue("");
                        list.add(dDownDT);
                        while (ite.hasNext()) {
                            dDownDT = new DropDownCodeDT();
                            dDownDT.setKey((String) ite.next());
                            dDownDT
                                    .setValue((String) map
                                            .get(dDownDT.getKey()));
                            list.add(dDownDT);
                        }

                    }
                    cachedMap.put(NEDSSConstants.O_NAICS, list);
                }
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.O_NAICS);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getBirthCountryCodes:");
        }
        return list;
    }

    public  static ArrayList<Object> getAllActiveCodeSetNms() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getAllActiveCodeSetNms());
            }
                //cachedMap.put(NEDSSConstants.CODE_SET_NMS, list);

        } catch (Exception ex) {
            logger.error("Error raised in getAllCodeSetNms:");
        }
        return list;
    }

    public  static ArrayList<Object> getNbsUnitsType() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getNbsUnitsType());
            }
                cachedMap.put(NEDSSConstants.NBS_UNITS_TYPE, list);

        } catch (Exception ex) {
            logger.error("Error raised in getAllCodeSetNms:");
        }
        return list;
    }

    public  static ArrayList<Object> getDefaultDisplayControl(String code) 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        HashMap<Object,Object>  defaultMap = new HashMap<Object,Object>() ;
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getDefaultDisplayControl(code));
            }
            defaultMap.put(NEDSSConstants.CODE_SET_NMS, list);

        } catch (Exception ex) {
            logger.error("Error raised in getDefaultDisplayControl:");
        }
        return list;
    }

    /**
     *
     * @param InConditionForProgArea
     * @return
     */
    public  static ArrayList<Object> getConditionCode() 
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        ArrayList<Object> nlist = new ArrayList<Object> ();

        TreeMap ccMap = new TreeMap();
        try {

                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    nlist = (getSRTMapEJBRef().getFilteredConditionCode());
                    list.addAll(nlist);
                }
                /* Set<Object> dDkeys =  ccMap.keySet();
                 if(dDkeys != null){
                     Iterator<Object> fIte = dDkeys.iterator();
                     while(fIte.hasNext()){
                         String key = (String) fIte.next();
                         DropDownCodeDT dt = new DropDownCodeDT();
                         dt.setKey(key);
                         dt.setValue((String)ccMap.get(key));
                         list.add(dt);

                     }

                 }*/

                    cachedMap.put(NEDSSConstants.CONDITION_CD, list);

        } catch (Exception ex) {
            logger.error("Error raised in getting the ConditionCode:");
        }
        return list;
    }


    /**
     * This method is to get the template info in DropDownCodeDTs
     * @return
     */
    public  static ArrayList<Object> getActiveTemplates( String busObjType)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        ArrayList<Object> nlist = new ArrayList<Object> ();

        try
        {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list = (getSRTMapEJBRef().getAllActiveTemplates(busObjType));
                }
        } catch (Exception ex) {
            logger.error("Error raised in getting the Template:");
        }
        return list;
    }

    public  static TreeMap<Object,Object> getAllConditionsforDesc()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        TreeMap<Object,Object> conditionMap = new TreeMap<Object,Object>();
        try
        {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list = getSRTMapEJBRef().getAllConditionCodes();
            }
            if(list != null){

            }


        } catch (Exception ex) {
            logger.error("Error raised in getAllConditionsforDesc:");
        }
        return conditionMap;
    }

    public  static ArrayList<Object> getvalueSetTypeCdNoSystemStrd()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.VALUE_SET_TYPE_NO_SYSTEM_STAD) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getvalueSetTypeCdNoSystemStrd());
                }
                    cachedMap.put(NEDSSConstants.VALUE_SET_TYPE_NO_SYSTEM_STAD, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.VALUE_SET_TYPE_NO_SYSTEM_STAD);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getvalueSetTypeCdNoSystemStrd:");
        }
        return list;
    }

    public static TreeMap<Object,Object> getStandredConceptCVGCodes()
    {
        TreeMap<Object,Object> returnMap = null;
        try {
            if (cachedMap.get("codeSystemNmMap") == null) {
                if (getSRTMapEJBRef() != null) {
                    returnMap = getSRTMapEJBRef().getStandredConceptCVGList();
                        if (returnMap.size() > 0)
                            cachedMap.put("codeSystemNmMap", returnMap);
                }
            } else {
                return (TreeMap<Object,Object>)cachedMap.get("codeSystemNmMap");
            }
        } catch (Exception ex) {
            logger.error("Error while loading getStandredConceptCVGCodes: CachedDropDowns. ");
        }
        return returnMap;
    }

    public  static ArrayList<Object> getConditionWithNoPortReqInd()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (cachedMap.get(NEDSSConstants.CONDITIONS_PORT_REQ_IND_FALSE) == null) {
                if (getSRTMapEJBRef() != null) {
                    DropDownCodeDT dDownDT = new DropDownCodeDT();
                    dDownDT.setKey(""); dDownDT.setValue("");
                    list.add(dDownDT);
                    list.addAll(getSRTMapEJBRef().getConditionWithNoPortReqInd());
                }
                    cachedMap.put(NEDSSConstants.CONDITIONS_PORT_REQ_IND_FALSE, list);
            } else {
                list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.CONDITIONS_PORT_REQ_IND_FALSE);
            }
        } catch (Exception ex) {
            logger.error("Error raised in getConditionWithNoPortReqInd:");
        }
        return list;
    }

    public  static ArrayList<Object> getInvestigationTypeRelatedPage()
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getInvestigationTypeRelatedPage());
                addTBAndVaricella(list);
        		if (list != null && list.size() > 1)  //#5409 cond list not sorted correctly
        			Collections.sort(list, new Comparator() {
        				public int compare(Object a, Object b) {
        					return (((DropDownCodeDT) a).getValue())
        							.compareToIgnoreCase(((DropDownCodeDT) b)
        									.getValue());
        				}
        			});
            }
            cachedMap.put(NEDSSConstants.INVESTIGATION_TYPE_RELATED_PAGE, list);
        } catch (Exception ex) {
            logger.error("Error raised in getInvestigationTypeRelatedPage:");
        }
        return list;
    }

    private static void addTBAndVaricella(ArrayList<Object> list)
    {
        DropDownCodeDT vaDT = new DropDownCodeDT();
        vaDT.setKey(NBSConstantUtil.INV_FORM_VAR);
        vaDT.setValue(NEDSSConstants.VARICELLA_VALUE);
        list.add(vaDT);
        DropDownCodeDT tbDT = new DropDownCodeDT();
        tbDT.setKey(NBSConstantUtil.INV_FORM_RVCT);
        tbDT.setValue(NEDSSConstants.TUBERCULOSIS_VALUE);
        list.add(tbDT);
    }

    public  static ArrayList<Object> getSendingSystemList(String systemType)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getSendingSystemList(systemType));
            }
            cachedMap.put(NEDSSConstants.SENDING_SYSTEM_LIST, list);
            list = (ArrayList<Object> ) cachedMap.get(NEDSSConstants.SENDING_SYSTEM_LIST);
        } catch (Exception ex) {
            logger.error("Error raised in getSendingSysList: "+ex+"\n"+stackTraceToString(ex));
            ex.printStackTrace();
        }
        return list;
    }

    public  static ArrayList<Object> getConditionDropDown(String relatedPage)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
       if (relatedPage == null) {
       	logger.debug("Related Page is null in call to getConditionDropDown - Previewing Page?");
        	return list;
        }
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getConditionDropDown(relatedPage));
                if(NBSConstantUtil.INV_FORM_VAR.equals(relatedPage))
                {
                    DropDownCodeDT vaDT = new DropDownCodeDT();
                    vaDT.setKey(NEDSSConstants.VARICELLA_KEY);
                    vaDT.setValue(NEDSSConstants.VARICELLA_VALUE);
                    list.add(vaDT);
                }if(NBSConstantUtil.INV_FORM_RVCT.equals(relatedPage))
                {
                    DropDownCodeDT tbDT = new DropDownCodeDT();
                    tbDT.setKey(NEDSSConstants.TUBERCULOSIS_KEY);
                    tbDT.setValue(NEDSSConstants.TUBERCULOSIS_VALUE);
                    list.add(tbDT);
                }

            }
    		if (list != null && list.size() > 1)  //#5409 cond list not sorted correctly
    			Collections.sort(list, new Comparator() {
    				public int compare(Object a, Object b) {
    					return (((DropDownCodeDT) a).getValue())
    							.compareToIgnoreCase(((DropDownCodeDT) b)
    									.getValue());
    				}
    			});
    		
            cachedMap.put(NEDSSConstants.CONDITION_LIST, list);

        } catch (Exception ex) {
            logger.error("Error raised in getConditionDropDown:" + relatedPage);
        }
        return list;
    }
    public  static ArrayList<Object> getConditionDropDownForTemplate(Long waTemplateUid)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try {
            if (getSRTMapEJBRef() != null) {
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll(getSRTMapEJBRef().getConditionDropDown(waTemplateUid));
            }
            cachedMap.put(NEDSSConstants.TEMPLATE_CONDITION_LIST, list);

        } catch (Exception ex) {
            logger.error("Error raised in getConditionDropDown:" + ex.getMessage());
        }
        return list;
    }
    public  static ArrayList<Object> getPublishedConditionDropDown(String dummy)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        try
        {
            if (getSRTMapEJBRef() != null)
            {
                ArrayList<Object> returnList = getSRTMapEJBRef().getPublishedConditionDropDown();
                DropDownCodeDT dDownDT = new DropDownCodeDT();
                dDownDT.setKey(""); dDownDT.setValue("");
                list.add(dDownDT);
                list.addAll((ArrayList<Object>)returnList.get(0));

                DropDownCodeDT vaDT = new DropDownCodeDT();
                vaDT.setKey(NEDSSConstants.VARICELLA_KEY);
                vaDT.setValue(NEDSSConstants.VARICELLA_VALUE);
                list.add(vaDT);

                DropDownCodeDT tbDT = new DropDownCodeDT();
                tbDT.setKey(NEDSSConstants.TUBERCULOSIS_KEY);
                tbDT.setValue(NEDSSConstants.TUBERCULOSIS_VALUE);
                list.add(tbDT);
        		if (list != null && list.size() > 1)  //#5409 cond list not sorted correctly
        			Collections.sort(list, new Comparator() {
        				public int compare(Object a, Object b) {
        					return (((DropDownCodeDT) a).getValue())
        							.compareToIgnoreCase(((DropDownCodeDT) b)
        									.getValue());
        				}
        			});
                cachedMap.put(NEDSSConstants.PUBLISHED_CONDITION_LIST, list);
                conditionPageMap = (HashMap<Object,Object>)returnList.get(1);

            }
        }
        catch (Exception ex)
        {
            logger.error("Error raised in getSendingSysList:");
        }
        return list;
    }

    public  static DropDownCodeDT getConditionPage(String conditionCode)
    {
        DropDownCodeDT contionPageDT = (DropDownCodeDT)conditionPageMap.get(conditionCode);
        if(contionPageDT == null)
        {
            if(NEDSSConstants.VARICELLA_KEY.equals(conditionCode))
            {
                contionPageDT = new DropDownCodeDT();
                contionPageDT.setKey(NBSConstantUtil.INV_FORM_VAR);
                contionPageDT.setValue(NEDSSConstants.VARICELLA_VALUE);
            }
            else if(NEDSSConstants.TUBERCULOSIS_KEY.equals(conditionCode))
            {
                contionPageDT = new DropDownCodeDT();
                contionPageDT.setKey(NBSConstantUtil.INV_FORM_RVCT);
                contionPageDT.setValue(NEDSSConstants.TUBERCULOSIS_VALUE);
            }
            else
            {
                getPublishedConditionDropDown("dummyData");
                contionPageDT = (DropDownCodeDT)conditionPageMap.get(conditionCode);
            }
        }

        return contionPageDT;
    }

    /*
     * Get the Participation Types for Case from the SRT Participation_Type table.
     * Used by PageBuilder for the Entity types.
     * @return ArrayList of ParticipationTypeVO from the SRT table Participation_type
     */
    public static TreeMap<Object,Object> getParticipationTypeList()
    {

        ArrayList<Object>  participationTypeCaseList = null;
        try {
            if (cachedMap.get(NEDSSConstants.PARTICIPATION_TYPE_LIST) == null)
            {
                if (getSRTMapEJBRef() != null)
                {
                        participationTypeCaseList = getSRTMapEJBRef()
                            .getParticipationTypes(NEDSSConstants.CLASS_CD_CASE);
                        if (participationTypeCaseList.size() > 0) {
                            cachedMap.put(NEDSSConstants.PARTICIPATION_TYPE_LIST, participationTypeCaseList);
                        }
                }

                //get from cache
            } else
                participationTypeCaseList = (ArrayList<Object>) cachedMap.get(NEDSSConstants.PARTICIPATION_TYPE_LIST);
        } catch (Exception ex) {
            logger.error("Error while loading Participation Types in getCaseParticipationTypeList: CachedDropDowns. ");
        }
        //move the list into a TreeMap
        TreeMap<Object, Object> participationTypeCaseMap = new TreeMap<Object, Object>();
        int listSize = participationTypeCaseList.size();
        for (int curCount = 0; curCount < listSize; curCount++)
        {
             ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseList.get(curCount);
             participationTypeCaseMap.put(parTypeVO.getTypeCd()+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+parTypeVO.getQuestionIdentifier(), parTypeVO);
        }
        return participationTypeCaseMap;
    }
    
    
	public static ArrayList<Object> getParticipationTypes() {

		ArrayList<Object> participationTypeCaseList = null;
		try {
			if (cachedMap.get(NEDSSConstants.PARTICIPATION_TYPE_LIST) == null) {
				if (getSRTMapEJBRef() != null) {
					participationTypeCaseList = getSRTMapEJBRef()
							.getParticipationTypes(NEDSSConstants.CLASS_CD_CASE);
					if (participationTypeCaseList.size() > 0) {
						cachedMap.put(NEDSSConstants.PARTICIPATION_TYPE_LIST,
								participationTypeCaseList);
					}
				}
			} else
				participationTypeCaseList = (ArrayList<Object>) cachedMap
						.get(NEDSSConstants.PARTICIPATION_TYPE_LIST);
		} catch (Exception ex) {
			logger.error("Error while loading Participation Types in getCaseParticipationTypeList: CachedDropDowns. ");
		}
		return participationTypeCaseList;
	}

    public static ArrayList<Object> getXSSFilterPatternList()
    {
        ArrayList<Object> xssFilterPatternList = null;
        try
        {
            if (cachedMap.get(NEDSSConstants.XSS_FILTER_PATTERN) == null)
            {
                if (getSRTMapEJBRef() != null)
                {
                    xssFilterPatternList = getSRTMapEJBRef().getXSSFilterPatterns();
                    if (xssFilterPatternList.size() > 0)
                    {
                        cachedMap.put(NEDSSConstants.XSS_FILTER_PATTERN, xssFilterPatternList);
                    }
                }
                // get from cache
            } else
                xssFilterPatternList = (ArrayList<Object>) cachedMap
                        .get(NEDSSConstants.XSS_FILTER_PATTERN);
        }
        catch (Exception ex)
        {
            logger.error("Error while loading XSS Filter List: CachedDropDowns. ");
        }
        return xssFilterPatternList;
    }


    public static String getStateName(String stateCd)
    {
        ArrayList ac = getStateList();
        for( int i = 0; i < ac.size(); i++ )
        {
            DropDownCodeDT d = (DropDownCodeDT)ac.get(i);
            if( d.getKey().equalsIgnoreCase( stateCd ))
            {
                return d.getValue() ;
            }
        }
        return stateCd;
    }
    
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> getConditionCoinfectionMap() {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		try {
			if (getSRTMapEJBRef() != null) {

				returnMap = (Map<Object, Object>) getSRTMapEJBRef()
						.getConditionCoinfectionMap();
			}

		} catch (Exception ex) {
			logger.error("Exception while retreiving co-infection conditions ");
		}
		return returnMap;
	}

    private static String stackTraceToString(Exception e)
    {
        String ret = "";
        StackTraceElement[] stes = e.getStackTrace();
        for (StackTraceElement ste : stes) {
            ret += ste.toString() + "\n";
        }
        return ret;
    }

	public static ArrayList<Object> getfilteredStatesByCountry(String contryCode) {
		ArrayList<Object> srtValues = new ArrayList<Object>();
		if (contryCode == null)
			return srtValues;
		else if (contryCode.equals(NEDSSConstants.USA_840) || contryCode.equals(NEDSSConstants.USA.USA)) {
			ArrayList<Object> usStates = new ArrayList<Object>();
			if (cachedMap.get(NEDSSConstants.USA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_US) != null)
				return (ArrayList<Object>) cachedMap
						.get(NEDSSConstants.USA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_US);
			srtValues = (ArrayList<Object>) getCodedValueForType(NEDSSConstants.PHVS_STATEPROVINCEOFEXPOSURE_CDC);
			for (Object ddcDT : srtValues) {
				DropDownCodeDT cDT = (DropDownCodeDT) ddcDT;
				if (cDT.getKey() != null && !cDT.getKey().startsWith(NEDSSConstants.CANADA.CA.toString())
						&& !cDT.getKey().startsWith(NEDSSConstants.MEXICO.MX.toString()))
					usStates.add(cDT);
			}
			if (usStates.size() > 0) {
				DropDownCodeDT dDownDT = new DropDownCodeDT();
				dDownDT.setKey("");
				dDownDT.setValue("");
				usStates.add(dDownDT);
			}
			cachedMap.put(NEDSSConstants.USA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_US, usStates);
			return usStates;
		} else if (contryCode.equals(NEDSSConstants.CANADA_124) || contryCode.equals(NEDSSConstants.CANADA.CAN)) {
			ArrayList<Object> canadaStates = new ArrayList<Object>();
			if (cachedMap.get(NEDSSConstants.CANADA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_CAN) != null)
				return (ArrayList<Object>) cachedMap
						.get(NEDSSConstants.CANADA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_CAN);
			srtValues = (ArrayList<Object>) getCodedValueForType(NEDSSConstants.PHVS_STATEPROVINCEOFEXPOSURE_CDC);
			for (Object ddcDT : srtValues) {
				DropDownCodeDT cDT = (DropDownCodeDT) ddcDT;
				if (cDT.getKey() != null && cDT.getKey().startsWith(NEDSSConstants.CANADA.CA.toString()))
					canadaStates.add(cDT);
			}
			if (canadaStates.size() > 0) {
				DropDownCodeDT dDownDT = new DropDownCodeDT();
				dDownDT.setKey("");
				dDownDT.setValue("");
				canadaStates.add(dDownDT);
			}
			cachedMap.put(NEDSSConstants.CANADA.PHVS_STATEPROVINCEOFEXPOSURE_CDC_CAN,
					canadaStates);
			return canadaStates;
		} else if (contryCode.equals(NEDSSConstants.MX_484) || contryCode.equals(NEDSSConstants.MEXICO.MEX)) {
			ArrayList<Object> mexicoStates = new ArrayList<Object>();
			if (cachedMap.get(NEDSSConstants.MEXICO.PHVS_STATEPROVINCEOFEXPOSURE_CDC_MEX) != null)
				return (ArrayList<Object>) cachedMap
						.get(NEDSSConstants.MEXICO.PHVS_STATEPROVINCEOFEXPOSURE_CDC_MEX);
			srtValues = (ArrayList<Object>) getCodedValueForType(NEDSSConstants.PHVS_STATEPROVINCEOFEXPOSURE_CDC);
			for (Object ddcDT : srtValues) {
				DropDownCodeDT cDT = (DropDownCodeDT) ddcDT;
				if (cDT.getKey() != null && cDT.getKey().startsWith(NEDSSConstants.MEXICO.MX.toString()))
					mexicoStates.add(cDT);
			}
			if (mexicoStates.size() > 0) {
				DropDownCodeDT dDownDT = new DropDownCodeDT();
				dDownDT.setKey("");
				dDownDT.setValue("");
				mexicoStates.add(dDownDT);
			}
			cachedMap.put(NEDSSConstants.MEXICO.PHVS_STATEPROVINCEOFEXPOSURE_CDC_MEX,
					mexicoStates);
			return mexicoStates;
		} else
			return getCodedValueForType(NEDSSConstants.PHVS_STATEPROVINCEOFEXPOSURE_CDC);
	}
	public  static ArrayList<Object> getDefaultCountyCodes() {
		return getCountyCodes(PropertyUtil.getInstance()
				.getNBS_STATE_CODE()); 
	}
	
	public static boolean doesConceptCodeBelongToCodeSet(String conceptCode, String codeSetNm){
        ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSetNm);
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getAltValue()!=null && ddcDT.getAltValue().equals(conceptCode)) {
                    return true;
                }
            }
        }
		return false;
	}
	public static boolean doesCodeBelongToCodeSet(String code, String codeSetNm){
        ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSetNm);
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                    return true;
                }
            }
        }
		return false;
	}
    public static ArrayList<Object> getPlaceList()
    {
    	ArrayList<Object> valueList = new ArrayList<Object>();
    	 try {
			if (getSRTMapEJBRef() != null)
				 valueList = getSRTMapEJBRef().getPlaceList();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return valueList;
    }
    
    public static Map<String, String> getNBSCodeFromPHINCodes()
    {
    	if(phinToNBSCodesMap.size()>0)
    		return phinToNBSCodesMap;
    	 try {
			if (getSRTMapEJBRef() != null)
				phinToNBSCodesMap = getSRTMapEJBRef().getNBSCodeFromPHINCodes();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return phinToNBSCodesMap;
    }

    /**
     * If someone is going to alter a list, they need to make a copy first
     * so they don't mess up the cache.
     * @param DropDown ArrayList
     * @return copy of DropDown array list
     */
    public static ArrayList<Object> copyDropDown(ArrayList<Object> srtValues) {
		ArrayList newList = new ArrayList();
		Iterator<Object> srtIter = srtValues.iterator();
		while (srtIter.hasNext()) {
			DropDownCodeDT ddOld = (DropDownCodeDT) srtIter.next();
			DropDownCodeDT ddNew = new DropDownCodeDT();
			if (ddOld.getKey() != null) ddNew.setKey(ddOld.getKey());
			if (ddOld.getValue() != null) ddNew.setValue(ddOld.getValue());
			if (ddOld.getIntValue() != null) ddNew.setIntValue(ddOld.getIntValue());
			if (ddOld.getAltValue() != null) ddNew.setAltValue(ddOld.getAltValue());
			if (ddOld.getLongKey() != null) ddNew.setLongKey(ddOld.getLongKey());
			if (ddOld.getStatusCd() != null) ddNew.setStatusCd(ddOld.getStatusCd());
			if (ddOld.getEffectiveToTime() != null) ddNew.setEffectiveToTime(ddOld.getEffectiveToTime());
			newList.add(ddNew);
		}
		return newList;
	}
    
	public static CodeLookupDT getLabCodeSystem(String labCode,
			String laboratoryID) throws Exception {
		CodeLookupDT codeLookupDT = null;
		if (laboratoryID == null)
			laboratoryID = NEDSSConstants.DEFAULT;
		if (labCode == null)
			labCode = NEDSSConstants.NOINFORMATIONGIVEN_CODE;
		if (labCodingSystem.get(labCode + laboratoryID) != null)
			return labCodingSystem.get(labCode + laboratoryID);
		try {
			if (getSRTMapEJBRef() != null) {
				codeLookupDT = getSRTMapEJBRef().getLabCodeSystem(labCode,
						laboratoryID);
				if (codeLookupDT.getCodedValueCodingSystem() != null
						&& codeLookupDT.getCodedValueDescription() != null)
					labCodingSystem.put(labCode + laboratoryID, codeLookupDT);
			}
		} catch (Exception e) {
			String message = " Exception while getting coding system for lab for labID: "
					+ laboratoryID + " and lab code: " + labCode;
			logger.error(message);
			throw new NEDSSSystemException(e.getMessage() + message, e);
		}
		return codeLookupDT;
	}
    
	@SuppressWarnings("unchecked")
	public static Map<String, String> getAOELOINCCodes() {
		Map<String, String> returnMap = null;
		try {
			if (cachedMap.get(NEDSSConstants.AOE_LOINC_LIST) == null) {
				if (getSRTMapEJBRef() != null) {
					returnMap = getSRTMapEJBRef().getAOELOINCCodes();
					if (returnMap != null && returnMap.size() > 0) {
						cachedMap.put(NEDSSConstants.AOE_LOINC_LIST, returnMap);
					}
				}
			} else
				returnMap = (Map<String, String>) cachedMap.get(NEDSSConstants.AOE_LOINC_LIST);
		} catch (Exception ex) {
			logger.error("Error while AOE LOINC Mapping: getAOELOINCCodes. ");
		}
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	public static String findToCode(String fromCodeSetNm, String fromCode, String toCodeSetNm) throws NEDSSSystemException {
		 String toCode = null;
		 String key = fromCodeSetNm + '^' + fromCode + '^' + toCodeSetNm;
		try {
			if (cachedMap.get(key) == null) {
				if (getSRTMapEJBRef() != null) {
					toCode = getSRTMapEJBRef().findToCode(fromCodeSetNm,fromCode,toCodeSetNm);
					
						cachedMap.put(key, toCode);
					}
			} else
				toCode = (String) cachedMap.get(key);
		} catch (Exception ex) {
			logger.error("Error while getting to code from ELR_XREF: findToCode. ");
		}
		return toCode;
	}

}