package gov.cdc.nedss.util;

import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.*;


/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:      jay kim
 * @version 1.0
 */
public class WumUtil
{

    static final LogUtils logger = new LogUtils(WumUtil.class.getName());

    /**
     * Creates a new WumUtil object.
     */
    public WumUtil()
    {
    }

    /**
     * @method      : progAreaCdOnSecurity
     * @params      : javax.servlet.HttpSession, gov.cdc.nedss.nbssecurity.helpers.NBSSecurityObj
     * @returnType  : java.lang.String
     * @description : returns  state programAreas for the user returned by NBSSecurityObj
     */
    public String getProgramAreaJurisdictions(String allowedJurisdictions,
                                              NBSSecurityObj securityObj)
    {

        String s = allowedJurisdictions;
        String result = s;
        String dollar = "$";
        String hipen = "-";
        String finalResult = null;

        //TB$470001|CEDS$470001|
        StringTokenizer stoken = new StringTokenizer(s, "|");

        while (stoken.hasMoreTokens())
        {

            String st = stoken.nextToken();

            //##!! System.out.println("1288: " + st);
            int i = st.indexOf("$");
            String st1 = st.substring(i + 1);
            String st2 = st.substring(0, i);
            result = searchReplace(result, st2, st1.concat("$").concat(st2));

            //##!! System.out.println("result: " + result);
            finalResult = searchReplace(result, dollar.concat(st1),
                                        hipen.concat(getJurisdiction(st1)));
        }

        //##!! System.out.println("result from getProgramAreaJurisdictions is: " + finalResult);
        return finalResult == null ? null : finalResult;
    } //getProgramAreaJurisdictions

    private String searchReplace(String data, String find, String replace)
    {

        StringBuffer sb = new StringBuffer();
        int a = 0;
        int b;
        int findLength = find.length();

        while ((b = data.indexOf(find, a)) != -1)
        {
            sb.append(data.substring(a, b));
            sb.append(replace);
            a = b + findLength;
        }

        if (a < data.length())
        {
            sb.append(data.substring(a));
        }

        return sb.toString();
    } //searchReplace

    private String getJurisdiction(String code)
    {

        CachedDropDownValues cdv = new CachedDropDownValues();

        //##!! System.out.println("code ie. Jurisdiction Code is: " + code);
        String desc = cdv.getDescForCode("S_JURDIC_C", code);

        //##!! System.out.println("Value for code passed is: " + desc);
        return desc;
    } //getSelectedTest

    /**
     * @method      : progAreaCdOnSecurity
     * @params      : javax.servlet.HttpSession, gov.cdc.nedss.nbssecurity.helpers.NBSSecurityObj
     * @returnType  : java.lang.String
     * @description : returns  state programAreas for the user returned by NBSSecurityObj
     */
    public String progAreaCdOnSecurity(NBSSecurityObj securityObj,
                                       String ObsType)
    {

        TreeMap<Object,Object> tMapFromSRT = null;
        String result = null;
        String resultFromSecurity = null;
        StringBuffer sBuff = new StringBuffer();
        SRTMap srtMapEjb = null;
        NedssUtils nedssUtils = new NedssUtils();

        try
        {

            Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
            logger.debug("SRT EJB = " + obj.toString());

            SRTMapHome home = (SRTMapHome)PortableRemoteObject.narrow(obj,
                                                                      SRTMapHome.class);
            srtMapEjb = home.create();
            tMapFromSRT = srtMapEjb.getProgramAreaCodedValue();
            logger.debug("tMapFromSRT ..... " + tMapFromSRT.toString());

            TreeMap<Object,Object> resultMap = securityObj.getProgramAreas(ObsType,
                                                            NBSOperationLookup.ADD);
            logger.debug("resultMap ..... " + resultMap.toString());

           Iterator<Object>  it = resultMap.keySet().iterator();

            while (it.hasNext())
            {
                result = (String)it.next();
                logger.debug("resultValue is : " + result);

                String NewString = result;

                if (tMapFromSRT.containsKey(NewString))
                {
                    logger.debug("tMapFromSRT contains result !!! ");
                    sBuff.append(NewString).append("$");
                    sBuff.append(tMapFromSRT.get(NewString)).append("|");
                }
            }
        }
        catch (Exception ex)
        {
            logger.fatal("progAreaCdOnSecurity: ", ex);
        }

        logger.debug("\n\n\n\n sBuff value is...." + sBuff);

        return sBuff.toString();
    } //progAreaCdOnSecurity

    /**
     * A date formatter
     * @param timestamp A TimeStamp
     * @return   String
     */
    public static String formatDate(java.sql.Timestamp timestamp)
    {

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if (timestamp != null)
            date = new Date(timestamp.getTime());

        if (date == null)

            return "";
        else

            return formatter.format(date);
    }

    /**
     * Sorts a TreeMap
     * @param treeMap the TreeMap<Object,Object> to be sorted
     * @return String
     */
    public static String sortTreeMap(TreeMap<?,?> treeMap)
    {
        class StringComparator
            implements Comparator<Object>
        {
            public int compare(Object a, Object b)
            {

                Map.Entry<?,?> m1 = (Map.Entry<?,?>)a;
                Map.Entry<?,?> m2 = (Map.Entry<?,?>)b;

                return (m1.getValue()).toString().toUpperCase().compareTo((m2.getValue()).toString().toUpperCase());
            }
        }

        StringBuffer sb = new StringBuffer();
        Set<?> s = treeMap.entrySet();
        Map.Entry<?,?>[] entries = (Map.Entry<?,?>[])s.toArray(new Map.Entry[s.size()]);
        Arrays.sort(entries, new StringComparator());

        for (int i = 0; i < s.size(); i++)
        {

            Map.Entry<?,?> m = (Map.Entry<?,?>)entries[i];
            sb.append(m.getKey()).append("$").append(m.getValue()).append("|");
            logger.info(m.getKey() + " => " + m.getValue());
        }

        return sb.toString();
    }

    /**
     * Extracts a PersonVOobject from an InvestigationProxyVO object
     * @param type_cd the person type code
     * @param investigationProxyVO the InvestigationProxyVO object containing the PersonVO to be extracted
     * @return the PersonVO object
     */
    public PersonVO getPersonVO(String type_cd,
                                InvestigationProxyVO investigationProxyVO)
    {

        Collection<Object>  participationDTCollection  = null;
        Collection<Object>  personVOCollection  = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;

        //Long phcUID = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO()
                    .getTheParticipationDTCollection();

        //logger.debug("convertProxyToRequestObj() after participationDTCollection  size: " + participationDTCollection.size());
        personVOCollection  = investigationProxyVO.getThePersonVOCollection();

        //logger.debug("convertProxyToRequestObj() after personVOCollection  size: " + personVOCollection.size());
        if (participationDTCollection  != null)
        {

           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;

            for (anIterator1 = participationDTCollection.iterator();
                 anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();

                if (participationDT.getTypeCd() != null &&
                    (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {

                    //logger.debug("convertProxyToRequestObj() got participationDT for  type_cd: " + participationDT.getTypeCd());
                    for (anIterator2 = personVOCollection.iterator();
                         anIterator2.hasNext();)
                    {
                        personVO = (PersonVO)anIterator2.next();

                        if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT
                                       .getSubjectEntityUid().longValue())
                        {

                            //logger.debug("convertProxyToRequestObj() got personVO for  person_uid: " + personVO.getThePersonDT().getPersonUid().longValue() + " and type_cd " + participationDT.getTypeCd());
                            return personVO;
                        }
                        else

                            continue;
                    }
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Extracts a PersonNameDT object from a PersonVO object
     * @param nameUserCd the name user code
     * @param personVO the PersonVO object containing the PersonNameDT to be extracted
     * @return the PersonNameDT object
     */
    public PersonNameDT getPersonNameDT(String nameUserCd, PersonVO personVO)
    {

        PersonNameDT personNameDT = null;

        //logger.debug("getPersonNameDT().getThePersonNameDTCollection: " + personVO.getThePersonNameDTCollection());
        Collection<Object>  personNameDTCollection  = personVO.getThePersonNameDTCollection();

        if (personNameDTCollection  != null)
        {

            //logger.info("getPersonNameDT().getThePersonNameDTCollection: " + personVO.getThePersonNameDTCollection());
           Iterator<Object>  anIterator = null;

            for (anIterator = personNameDTCollection.iterator();
                 anIterator.hasNext();)
            {

                PersonNameDT temp = (PersonNameDT)anIterator.next();
                logger.debug(
                        "getPersonNameDT.getThePersonNameDT: " +
                        temp.getNmUseCd());

                if (temp.getNmUseCd() != null &&
                    temp.getNmUseCd().equalsIgnoreCase(nameUserCd))
                {
                    logger.info(
                            "getPersonNameDT.getPersonNameDT: " +
                            temp.getFirstNm() + " nameType: " +
                            temp.getNmUseCd());
                    personNameDT = temp;
                }
                else

                    continue;
            }
        }

        return personNameDT;
    }

    /**
     * Extracts an organization name from the organization object
     * @param nameUserCd the name user code
     * @param organizationVO the organization object containing the organization name to be extracted
     * @return an organization name object
     */
    public OrganizationNameDT getOrganizationNameDT(String nameUserCd,
                                                    OrganizationVO organizationVO)
    {

        OrganizationNameDT organizationNameDT = null;
        Collection<Object>  organizationNameDTCollection  = organizationVO.getTheOrganizationNameDTCollection();

        if (organizationNameDTCollection  != null)
        {

           Iterator<Object>  anIterator = null;

            for (anIterator = organizationNameDTCollection.iterator();
                 anIterator.hasNext();)
            {
                organizationNameDT = (OrganizationNameDT)anIterator.next();

                if (organizationNameDT.getNmUseCd() != null &&
                    organizationNameDT.getNmUseCd().equalsIgnoreCase(
                            nameUserCd))
                {
                    logger.debug(
                            "getOrganizationNameDT.organizationNameDT: nameUseCd: " +
                            organizationNameDT.getNmUseCd());

                    return organizationNameDT;
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Gets the organization's locator by type
     * @param locatorType the locator type
     * @param organizationVO the OrganizationVO object
     * @return an EntityLocatorParticipationDT object
     */
    public EntityLocatorParticipationDT getOrganizationLocatorDT(String locatorType,
                                                                 OrganizationVO organizationVO)
    {

        Collection<Object>  entityLocatorParticipationDTCollection  =
                organizationVO.getTheEntityLocatorParticipationDTCollection();

        if (entityLocatorParticipationDTCollection  != null)
        {

           Iterator<Object>  anIterator = null;

            for (anIterator = entityLocatorParticipationDTCollection.iterator();
                 anIterator.hasNext();)
            {

                EntityLocatorParticipationDT entityLocatorParticipationDT =
                        (EntityLocatorParticipationDT)anIterator.next();

                if (entityLocatorParticipationDT.getClassCd() != null &&
                    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                            locatorType))
                {
                    logger.info(
                            "InvestigationMeaslesPreAction.getOrganizationLocatorDT: locatorType: " +
                            entityLocatorParticipationDT.getClassCd());

                    return entityLocatorParticipationDT;
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Extracts a PersonVO object from a MorbidityProxyVO object
     * @param type_cd the person type code
     * @param proxy the MorbidityProxyVO containing the PersonVO to be extracted
     * @return the PersonVO object
     */
    public PersonVO getPersonVO(String type_cd, MorbidityProxyVO proxy)
    {

        Collection<Object>  participationDTCollection  = null;
        Collection<Object>  personVOCollection  = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;

        //Long phcUID = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection  = proxy.getTheParticipationDTCollection();

        //logger.debug("convertProxyToRequestObj() after participationDTCollection  size: " + participationDTCollection.size());
        personVOCollection  = proxy.getThePersonVOCollection();

        //logger.debug("convertProxyToRequestObj() after personVOCollection  size: " + personVOCollection.size());
        if (participationDTCollection  != null)
        {

           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;

            for (anIterator1 = participationDTCollection.iterator();
                 anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();

                if (participationDT.getTypeCd() != null &&
                    (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {

                    //logger.debug("convertProxyToRequestObj() got participationDT for  type_cd: " + participationDT.getTypeCd());
                    for (anIterator2 = personVOCollection.iterator();
                         anIterator2.hasNext();)
                    {
                        personVO = (PersonVO)anIterator2.next();

                        if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT
                                       .getSubjectEntityUid().longValue())
                        {

                            //logger.debug("convertProxyToRequestObj() got personVO for  person_uid: " + personVO.getThePersonDT().getPersonUid().longValue() + " and type_cd " + participationDT.getTypeCd());
                            return personVO;
                        }
                        else

                            continue;
                    }
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Extracts an OrganizationVO object from a MorbidityProxyVO object
     * @param type_cd the organization type code
     * @param proxy the MorbidityProxyVO object containing the organization to be extracted
     * @return the OrganizationVO object
     */
    public OrganizationVO getOrganizationVO(String type_cd,
                                            MorbidityProxyVO proxy)
    {

        Collection<Object>  participationDTCollection  = null;
        Collection<Object>  organizationVOCollection  = null;
        ParticipationDT participationDT = null;
        OrganizationVO organizationVO = null;
        participationDTCollection  = proxy.getTheParticipationDTCollection();
        organizationVOCollection  = proxy.getTheOrganizationVOCollection();

        if (participationDTCollection  != null &&
            organizationVOCollection  != null)
        {

           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;

            for (anIterator1 = participationDTCollection.iterator();
                 anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();

                if (participationDT.getTypeCd() != null &&
                    (participationDT.getTypeCd().trim()).equals(type_cd))
                {

                    for (anIterator2 = organizationVOCollection.iterator();
                         anIterator2.hasNext();)
                    {
                        organizationVO = (OrganizationVO)anIterator2.next();

                        if (organizationVO.getTheOrganizationDT().getOrganizationUid()
                                      .longValue() == participationDT.getSubjectEntityUid()
                                       .longValue())
                        {

                            return organizationVO;
                        }
                        else

                            continue;
                    }
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Gets all counties for a state
     * @param stateCd the state code
     * @return String
     */
    public String getCountiesByState(String stateCd)
    {

        StringBuffer parsedCodes = new StringBuffer("");

        if (stateCd != null)
        {

            //SRTValues srtValues = new SRTValues();
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<?,?> treemap = null;
            treemap = srtValues.getCountyCodes(stateCd);

            if (treemap != null)
            {

                Set<?> set = treemap.keySet();
               Iterator<?>  itr = set.iterator();

                while (itr.hasNext())
                {

                    String key = (String)itr.next();
                    String value = (String)treemap.get(key);
                    parsedCodes.append(key.trim()).append(
                            NEDSSConstants.SRT_PART).append(value.trim()).append(
                            NEDSSConstants.SRT_LINE);
                }
            }
        }

        return parsedCodes.toString();
    }

    /**
     * Extracts an EntityLocatorParticipationDT object from a PersonVO object
     * @param locatorType the locator type
     * @param personVO the PersonVO object containing the EntityLocatorParticipationDT to be extracted
     * @return the EntityLocatorParticipationDT object
     */
    public EntityLocatorParticipationDT getPersonLocatorDT(String locatorType,
                                                           PersonVO personVO)
    {

        Collection<Object>  entityLocatorParticipationDTCollection  =
                personVO.getTheEntityLocatorParticipationDTCollection();

        if (entityLocatorParticipationDTCollection  != null)
        {

           Iterator<Object>  anIterator = null;

            for (anIterator = entityLocatorParticipationDTCollection.iterator();
                 anIterator.hasNext();)
            {

                EntityLocatorParticipationDT entityLocatorParticipationDT =
                        (EntityLocatorParticipationDT)anIterator.next();

                if (entityLocatorParticipationDT.getClassCd() != null &&
                    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                            locatorType))
                {
                    logger.debug(
                            "InvestigationMeaslesPreAction.getPersonLocatorDT: locatorType: " +
                            entityLocatorParticipationDT.getClassCd());

                    return entityLocatorParticipationDT;
                }
                else

                    continue;
            }
        }

        return null;
    }

    /**
     * Gets the observation code
     * @param investigationProxyVO an InvestigationProxyVO object
     * @param strCd the code
     * @return String
     */
    public static String getObservationCode(InvestigationProxyVO investigationProxyVO,
                                     String strCd)
    {

        String obsCode = "";
        String observationCode = "";
        ObsValueCodedDT obsValueCodedDT = null;
        Collection<ObservationVO>  observationVOCollection  = investigationProxyVO.getTheObservationVOCollection();
        logger.debug(
                "convertProxyToRequestObj() before observationVOCollection  ");

        if (observationVOCollection  != null)
        {

           Iterator<ObservationVO>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;
            logger.debug(
                    "convertProxyToRequestObj() observationVOCollection  size: " +
                    observationVOCollection.size());

            for (anIterator1 = observationVOCollection.iterator();
                 anIterator1.hasNext();)
            {

                ObservationVO observationVO = (ObservationVO)anIterator1.next();
                obsCode = observationVO.getTheObservationDT().getCd();

                if ((strCd != null) && (obsCode != null) && (obsCode.equals(strCd)))
                {

                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();

                    if (obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();

                        if (anIterator2.hasNext())
                        {
                            obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();

                            if (obsValueCodedDT != null &&
                                obsValueCodedDT.getCode() != null &&
                                !obsValueCodedDT.getCode().equals("NI"))
                            {
                                logger.debug(
                                        "getCode: " +
                                        obsValueCodedDT.getCode() +
                                        " for observationCd: " +
                                        observationVO.getTheObservationDT().getCd());
                                obsCode = obsValueCodedDT.getCode();

                                break;
                            }
                        }
                    }
                }
            }
        }

        return obsCode;
    }

    public static String getProgramAreaFromSecurityObj(NBSSecurityObj nbsSecurityObj)
    {
      String programAreas = "";
      StringBuffer stingBuffer = new StringBuffer();

      TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>) nbsSecurityObj.getProgramAreas(NBSBOLookup.
          INVESTIGATION, NBSOperationLookup.ADD);
      if (treeMap != null && treeMap.size() > 0) {
        logger.debug("nbsSecurityObj.getProgramAreas(): " +
                     nbsSecurityObj.
                     getProgramAreas(NBSBOLookup.INVESTIGATION,
                                     NBSOperationLookup.ADD));
       Iterator<Object>  anIterator = treeMap.keySet().iterator();
        stingBuffer.append("(");
        while (anIterator.hasNext()) {

          String token = (String) anIterator.next();
          logger.debug("resultValue is : " + token);
          String NewString = "";
          if (token.lastIndexOf("!") < 0) {
            NewString = token;
          }
          else {
            NewString = token.substring(0, token.lastIndexOf("!"));
            logger.debug("NewString Value: " + NewString);
          }

          stingBuffer.append("'").append(NewString).append("',");
        }
        stingBuffer.replace(stingBuffer.length() - 1, stingBuffer.length(), "");
        stingBuffer.append(")");
        programAreas = stingBuffer.toString();
        logger.debug("programAreas: " + programAreas);

      }

      return programAreas;

    }

}