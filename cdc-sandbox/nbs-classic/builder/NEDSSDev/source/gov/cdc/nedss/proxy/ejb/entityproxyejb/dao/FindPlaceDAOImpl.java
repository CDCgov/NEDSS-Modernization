package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Name: FindPlaceDAOImpl.java Description: This is a class determine the DAO
 * implementation based on the information provided in the deployment
 * descriptor.
 * 
 * @author NEDSS Development Team
 * @version 1.0
 */

public class FindPlaceDAOImpl extends DAOBase
{

    static final LogUtils       logger                = new LogUtils(FindPlaceDAOImpl.class.getName());
    private static PropertyUtil propertyUtil          = PropertyUtil.getInstance();

    /* To test new optimization */
    public static final String  BASENAMEQUERYSQL      = " SELECT o.place_uid \"placeUid\", o.local_id \"localId\", o.nm  \"nm\",  o.cd \"cd\", o.cd_desc_txt \"cdDescTxt\", "
                                                              + " o.record_status_cd \"recordStatusCd\", "
                                                              + " o.version_ctrl_nbr \"versionCtrlNbr\" " 
                                                              + " FROM place o "
                                                              + " where " 
                                                              + "  o.record_status_cd = 'ACTIVE' ";

    public static final String  BASEADDRESSQUERYSQL   = " SELECT o.place_uid \"placeUid\", o.local_id \"localId\", o.nm  \"nm\",  "
                                                              + " o.record_status_cd \"recordStatusCd\", "
                                                              + " o.version_ctrl_nbr \"versionCtrlNbr\", "
                                                              + " pl.postal_locator_uid \"locatorUid\", "
                                                              + " elp1.as_of_date \"asOfDate\","
                                                              + " elp1.class_Cd \"classCd\", "
                                                              + " elp1.cd \"locatorTypeCdDesc\" , "
                                                              + " elp1.use_cd \"useCd\", elp1.cd \"locatorCd\", "
                                                              + " pl.street_addr1 \"streetAddr1\", "
                                                              + " pl.street_addr2 \"streetAddr2\", "
                                                              + " pl.city_desc_txt \"city\", pl.zip_cd \"zip\", pl.cnty_cd \"cntyCd\",  pl.state_cd \"state\" "
                                                              + " from place o, entity_locator_participation elp1, "
                                                              + " postal_locator pl "
                                                              + " where o.place_uid = elp1.entity_uid  "
                                                              + " and elp1.class_cd = 'PST' "
                                                              + " and elp1.status_cd = 'A' "
                                                              + " and elp1.locator_uid = pl.postal_locator_uid "
                                                              + " and o.record_status_cd = 'ACTIVE' ";

    public static final String  BASEIDQUERYSQL        = " SELECT o.place_uid \"placeUid\", o.local_id \"localId\", ei.entity_uid \"entityUid\", o.version_ctrl_nbr \"versionCtrlNbr\" ,  "
                                                              + " ei.root_extension_txt \"rootExtensionTxt\", ei.type_cd \"typeCd\" "
                                                              + " FROM "
                                                              + "   place o,  Entity_id  ei "
                                                              + " WHERE  "
                                                              + "   o.place_uid = ei.entity_uid "
                                                              + "   AND o.record_status_cd = 'ACTIVE' ";

    public static final String  BASETELEQUERYSQL      = " SELECT o.place_uid \"placeUid\", o.local_id \"localId\", "
                                                              + " o.version_ctrl_nbr \"versionCtrlNbr\", "
                                                              + " tl.tele_locator_uid \"locatorUid\", elp2.as_of_date \"asOfDate\", "
                                                              + " elp2.class_cd \"classCd\", "
                                                              + " elp2.use_cd \"useCd\", elp2.cd \"locatorCd\", "
                                                              + " tl.phone_nbr_txt \"phoneNbrTxt\", "
                                                              + " tl.extension_txt \"extensionTxt\",  "
                                                              + " null \"eiRootExtensionTxt\", null \"eiTypeDesc\" "
                                                              + " from "
                                                              + " place o, entity_locator_participation elp2, "
                                                              + " tele_locator tl "
                                                              + " where  "
                                                              + "   o.place_uid = elp2.entity_uid   "
                                                              + "   and elp2.class_cd = 'TELE' "
                                                              + "   and elp2.status_cd= 'A' "
                                                              + "   and elp2.locator_uid  = tl.tele_locator_uid " 
                                                              + " and o.record_status_cd = 'ACTIVE' ";

    static final int            MAX_CACHE_COUNT       = 105; 
    
    public FindPlaceDAOImpl() throws NEDSSSystemException
    {
    }

    /**
     * Passes querry to the databse with the search Criteria and gets the
     * Organization Details and set it ot an arryList of SearchResultVO for the
     * display
     * 
     * @param find
     *            the PlaceSearchVO object
     * @param cacheNumber
     *            the int
     * @param fromIndex
     *            the int
     * @return displayList the ArrayList
     * @throws NEDSSSystemException
     */
    public ArrayList<Object> findPlacesByKeyWords(PlaceSearchVO find, int cacheNumber, int fromIndex)
            throws NEDSSSystemException
    {
        ArrayList<Object> placeNameColl = null;
        ArrayList<Object> placeAddressColl = null;
        ArrayList<Object> placeTeleColl = null;
        ArrayList<Object> placeEntityIdColl = null;
        String whereClause = null; 
 
        /* Has user entered any place and name field for search? */
        if (!StringUtils.isEmpty(find.getNm()) || (find.getPlaceUid() != null ) || !StringUtils.isEmpty(find.getTypeCd()) )
        {
            whereClause = buildNameWhereClause(find); 
            placeNameColl = runNameQuery(whereClause);
        }
        
        /* Has user entered any address information */
        if ( !StringUtils.isEmpty(find.getStreetAddr1())  || !StringUtils.isEmpty(find.getCity())
                || !StringUtils.isEmpty(find.getState())  || !StringUtils.isEmpty(find.getZip()) )
        {
            whereClause = buildAddressWhereClause(find); 
            placeAddressColl = runAddressQuery(whereClause);
        }
        
        /* Has user entered telephone number for search */
        if ( !StringUtils.isEmpty(find.getPhoneNbrTxt()) )
        {
            whereClause = buildTeleWhereClause(find); 
            placeTeleColl = runTeleQuery(whereClause);
        }
        
        /* Has user entered any EntityId information */
        if (!StringUtils.isEmpty(find.getRootExtensionTxt()))
        {
            whereClause = buildEntityIdWhereClause(find);
            placeEntityIdColl = runEntityIdQuery(whereClause);
        }
        
        /* Find common placeUIDs from all the queries */
        ArrayList<Object> idList = filterIDs(placeNameColl, placeAddressColl, placeTeleColl, placeEntityIdColl); 
         
        /* generate where clause with those common placeUIDs */
        whereClause = generateWhereClause(idList);
        ArrayList<Object> searchResult = new ArrayList<Object>();
        /* If there is any Common UID */
        if (whereClause != null)
        {
            placeNameColl = runNameQuery(whereClause);
            placeAddressColl = runAddressQuery(whereClause);
            placeTeleColl = runTeleQuery(whereClause);
            placeEntityIdColl = runEntityIdQuery(whereClause);
             
            /*
             * Iterate through NameCollection as sorting is to be done by name)
             * and find out attribute for each collection put them as
             * placesearchresultVo and put it in ArrayList<Object>
             */ 
            
            Iterator iter = placeNameColl.iterator();
            Map mKeys = new HashMap();
            while( iter.hasNext() )
            {
                PlaceSearchVO ps = (PlaceSearchVO)iter.next();
                PlaceVO place = null;
                if( mKeys.get(ps.getPlaceUid()) == null )
                {
                    place = new PlaceVO();
                    try
                    {
                        BeanUtils.copyProperties(place, ps);
                    }
                    catch (Exception ite)
                    {
                        logger.error("Error in copying properties ", ite);
                    }
                    mKeys.put(ps.getPlaceUid(), place);
                    searchResult.add(place);
                }
                else
                {
                    place = (PlaceVO)mKeys.get(ps.getPlaceUid());
                } 
                Iterator aIter = placeAddressColl.iterator();
                while( aIter.hasNext() )
                {
                    PlaceSearchVO a = (PlaceSearchVO)aIter.next();
                    if( a.getPlaceUid().equals(ps.getPlaceUid()))
                    {
                        if( place.getTheParticipationDTCollection() == null )
                        {
                            place.setTheParticipationDTCollection(new ArrayList());
                            
                        }
                        place.getTheParticipationDTCollection().add(a);
                    }
                }
                Iterator tIter = placeTeleColl.iterator();
                while( tIter.hasNext() )
                {
                    PlaceSearchVO t = (PlaceSearchVO)tIter.next();
                    if( t.getPlaceUid().equals(ps.getPlaceUid()))
                    {
                        if( place.getTheParticipationDTCollection() == null )
                        {
                            place.setTheParticipationDTCollection(new ArrayList());
                            
                        }
                        place.getTheParticipationDTCollection().add(t);
                    }
                }
                
                Iterator eIter = placeEntityIdColl.iterator();
                while( eIter.hasNext())
                {
                    PlaceSearchVO eid = (PlaceSearchVO)eIter.next();
                    if( eid.getPlaceUid().equals(ps.getPlaceUid()))
                    { 
                        EntityIdDT edt = new EntityIdDT();
                        edt.setRootExtensionTxt(eid.getRootExtensionTxt());
                        edt.setEntityUid(eid.getPlaceUid());
                        place.getTheEntityIdDTCollection().add(edt);
                    }
                }
            }
        }
        return searchResult;

    }

    /*
     * Depending on user input build where clause for place and name
     * query
     */
    private String buildNameWhereClause(PlaceSearchVO find)
    {
        StringBuffer sbuf = new StringBuffer();
        String oper = null;
        String whereAnd = " where ";
        boolean firstWhere = false;
        if (find.getNm() != null)
        {
            if (find.getNm().trim().length() != 0)
            {
                oper = find.getNmOperator().trim();
                if (oper.length() != 0)
                    if (firstWhere)
                        firstWhere = false;
                    else
                        whereAnd = " AND ";
                String placeName = find.getNm().trim();
                String specialCharacter;
                logger.debug("\n\n\ntoBeEscaped :" + placeName);
                if (placeName.indexOf("'") > 0)
                {
                    logger.debug("inside if and index of \"'\" is :" + placeName.indexOf("'"));
                    specialCharacter = "'";
                    placeName = replaceCharacters(placeName, specialCharacter, "''");
                    logger.debug("query is :" + placeName);

                }
                if (oper.equalsIgnoreCase("SL")) // sounds like
                    sbuf.append(whereAnd + " (soundex(upper( o.nm ))  =  soundex('"
                            + placeName.toUpperCase() + "'))");
                else if (oper.equalsIgnoreCase("CT"))
                { // contains
                    sbuf.append(whereAnd + "( upper( o.nm )  like '%" + placeName.toUpperCase() + "%')");
                    // sbuf.append(" and PN.nm_use_cd = '0')");
                }
                else if (oper.equalsIgnoreCase("SW"))
                { // contains
                    sbuf.append(whereAnd + "( upper( o.nm )  like '" + placeName.toUpperCase() + "%')");
                     
                }
                else
                {
                    sbuf.append(whereAnd + " (upper( o.nm ) " + oper + " '" + placeName.toUpperCase() + "')");
                }
            }
        }
        if( find.getPlaceUid() != null )
        { 
            sbuf.append(  " AND   o.place_uid = " + find.getPlaceUid() );
        }
        
        if( !StringUtils.isEmpty(find.getTypeCd()))
        {
            sbuf.append("  AND o.cd = '" + find.getTypeCd() + "' " ) ; 
        }
        return sbuf.toString();
    }

    /* Depending on user input build where clause for address query */
    private String buildAddressWhereClause(PlaceSearchVO find)
    {
        StringBuffer sbuf = new StringBuffer();
        String oper = null;
        String whereAnd = " where ";
        boolean firstWhere = false;

        if (find.getStreetAddr1() != null)
        {
            if (find.getStreetAddr1().trim().length() != 0)
            {
                oper = find.getStreetAddr1Operator().trim();
                if (oper.length() != 0)
                    if (firstWhere)
                        firstWhere = false;
                    else
                        whereAnd = " AND ";
                String streetAddress = find.getStreetAddr1().trim();
                String specialCharacter; 
                if (streetAddress.indexOf("'") > 0)
                {
                    specialCharacter = "'";
                    streetAddress = replaceCharacters(streetAddress, specialCharacter, "''");
                    logger.debug("query is :" + streetAddress);

                }
                if (oper.equalsIgnoreCase("SL"))
                    sbuf.append(whereAnd + " soundex (upper(PL.street_addr1)) = soundex('"
                            + streetAddress.toUpperCase() + "')");
                else if (oper.equalsIgnoreCase("CT")) // contains
                    sbuf.append(whereAnd + " upper( PL.street_addr1)  like '%" + streetAddress.toUpperCase() + "%'");
                else
                    sbuf.append(whereAnd + " upper(PL.street_addr1) " + oper.toUpperCase() + " '"
                            + streetAddress.toUpperCase() + "'");
            }
        }
        if (find.getCity() != null)
        {
            if (find.getCity().trim().length() != 0)
            {
                oper = find.getCityOperator().trim();
                if (oper.length() != 0)
                    if (firstWhere)
                        firstWhere = false;
                    else
                        whereAnd = " AND ";
                if (oper.equalsIgnoreCase("SL"))
                    sbuf.append(whereAnd + " soundex (upper(PL.city_desc_txt)) = soundex('"
                            + find.getCity().trim().toUpperCase() + "')");
                else if (oper.equalsIgnoreCase("CT")) // contains
                    sbuf.append(whereAnd + " upper( PL.city_desc_txt)  like '%"
                            + find.getCity().trim().toUpperCase() + "%'");
                else
                    sbuf.append(whereAnd + " upper(PL.city_desc_txt) " + oper.toUpperCase() + " '"
                            + find.getCity().trim().toUpperCase() + "'");

            }
        }
        if (find.getState() != null)
        {
            if (find.getState().trim().length() != 0)
            {
                if (firstWhere)
                    firstWhere = false;
                else
                    whereAnd = " AND ";
                sbuf.append(whereAnd + " upper(PL.state_cd) =  '" + find.getState().trim().toUpperCase() + "'");

            }
        }
        if (find.getZip() != null)
        {
            if (find.getZip().trim().length() != 0)
            {
                if (firstWhere)
                    firstWhere = false;
                else
                    whereAnd = " AND ";
                sbuf.append(whereAnd + " upper(PL.zip_cd) = '" + find.getZip().trim().toUpperCase() + "'");
            }
        }
        return sbuf.toString();
    }

    /* Depending on user input build where clause for tele query */
    private String buildTeleWhereClause(PlaceSearchVO find)
    {
        StringBuffer sbuf = new StringBuffer();
        String oper = " = ";
        String whereAnd = " where ";
        boolean firstWhere = false;

        if (find.getPhoneNbrTxt() != null)
        {
            if (find.getPhoneNbrTxt().trim().length() != 0)
            {
                if (oper.length() != 0)
                    if (firstWhere)
                        firstWhere = false;
                    else
                        whereAnd = " AND ";
                String phoneNumber = find.getPhoneNbrTxt().trim();
                String formattedPhone = formatPhoneNumber(phoneNumber);
                if (formattedPhone.indexOf("-") == 3 && formattedPhone.lastIndexOf("-") == 4
                        && formattedPhone.length() == 9)
                { 
                    sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '"
                            + formattedPhone.substring(0, 4).toUpperCase() + "%'"
                            + " and upper( TL.phone_nbr_txt)  like '%" + formattedPhone.substring(5, 9).toUpperCase()
                            + "%'");
                }
                else if (formattedPhone.indexOf("-") == 3 && formattedPhone.lastIndexOf("-") == 3
                        && formattedPhone.length() == 4)
                {
                    sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '" + formattedPhone.trim().toUpperCase()
                            + "%'");
                }
                else
                    sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '%" + formattedPhone.trim().toUpperCase()
                            + "%'");
            }
        }
        return sbuf.toString();
    }
 

    private String constructUidWhereInClause(String fieldName, ArrayList<Object> uidsStringArray)
    {
        // EXAMPLE:
        // "p.person_uid in (uid1, uid2, ...., uid1000) or p.person_uid(uid1001, uid1002)"
        // there should be exceptions thrown when fieldName is empty or null and
        // when uidsStringArray is empty or null
        StringBuffer strbResult = new StringBuffer();

        if (uidsStringArray != null && !uidsStringArray.isEmpty())
        {
            int size = uidsStringArray.size();
            boolean first = true;

            for (int i = 0; i < size; i++)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    strbResult.append(" OR ");
                }

                strbResult.append(fieldName);
                strbResult.append(" in (");
                strbResult.append(uidsStringArray.get(i));
                strbResult.append(") ");
            }
        }

        return strbResult.toString();
    }

    private ArrayList<Object> createUidsCsvStringArray(ArrayList<Object> personParentUidList)
    {
        ArrayList<Object> result = null;

        if (personParentUidList != null && !personParentUidList.isEmpty())
        {
            result = new ArrayList<Object>();
            int maxNum = 1000;
            Long curUid = null;
            int size = personParentUidList.size();
            int numOfIterations = ((int) size / maxNum) + 1;
            int maxForThisIteration = size;

            for (int k = 0; k < numOfIterations; k++)
            {
                ArrayList<Object> uidCollector = new ArrayList<Object>();
                int counterStartingPoint = k * maxNum;

                if ((k + 1) * maxNum > size)
                {
                    maxForThisIteration = size;
                }
                else
                {
                    maxForThisIteration = (k + 1) * maxNum;
                }

                for (int counter = counterStartingPoint; counter < maxForThisIteration; counter++)
                {
                    curUid = (Long) personParentUidList.get(counter);
                    uidCollector.add(curUid);
                }

                result.add(createCsvString(uidCollector));
            }
        }

        return result;
    }

    private String createCsvString(ArrayList<Object> uids)
    {
        StringBuffer strbResult = new StringBuffer("");

        if (uids != null && !uids.isEmpty())
        {
            boolean firstId = true;

            if (uids != null && !uids.isEmpty())
            {
                Iterator<Object> it = uids.iterator();

                if (it != null)
                {
                    Long cur = null;

                    while (it.hasNext())
                    {
                        cur = (Long) it.next();

                        if (firstId)
                        {
                            firstId = false;
                        }
                        else
                        {
                            strbResult.append(",");
                        }

                        strbResult.append(cur.longValue());
                    }
                }
            }
        }

        return strbResult.toString();
    }

    /* Generate where clause based on common placeUIDs in the list */
    private String generateWhereClause(ArrayList<Object> list)
    {
        String result = null;

        if (list != null && list.size() > 0)
        {
            ArrayList<Object> csvChunks = createUidsCsvStringArray(list);
            StringBuffer sbuf = new StringBuffer();
            sbuf.append(" and (");
            sbuf.append(constructUidWhereInClause("o.place_uid", csvChunks));
            sbuf.append(") ");
            result = sbuf.toString();
        }

        return result;
    }

    /* find common placeUids from all collections */
    private ArrayList<Object> filterIDs(ArrayList<Object> placeNameColl,
            ArrayList<Object> placeAddressColl, ArrayList<Object> placeTeleColl,
            ArrayList<Object> placeEntityIdColl)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        list = (ArrayList<Object>) getIDs(placeNameColl, placeAddressColl);
        list = (ArrayList<Object>) getIDs(placeTeleColl, list);
        list = (ArrayList<Object>) getIDs(placeEntityIdColl, list);
        ArrayList<Object> idList = new ArrayList<Object>();
        int cacheCount = 0;
        /*
         * get default cache count from property file. If not specified there
         * take one from this file
         */
        if (propertyUtil.getNumberOfRows() != 0)
            cacheCount = propertyUtil.getNumberOfRows();
        else
            cacheCount = MAX_CACHE_COUNT;

        if (list != null)
        {
            if (list.size() > cacheCount)
                list.subList(cacheCount, list.size()).clear();
            Iterator<Object> itr = list.iterator();
            while (itr.hasNext())
            {
                PlaceSearchVO tmp = (PlaceSearchVO)itr.next();
                idList.add(tmp.getPlaceUid());
            }
        }
        return idList;
    }

    /* find common placeUids from two lists. */
    private List<Object> getIDs(ArrayList<Object> list1, ArrayList<Object> list2)
    {
        // int cacheCount =0;
        if (list1 == null && list2 == null)
            return null;
        ArrayList<Object> list = new ArrayList<Object>();
        if (list2 == null)
        {
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            Iterator<Object> itr = list1.iterator();
            while (itr.hasNext())
            {
                PlaceSearchVO tmp = (PlaceSearchVO) itr.next();
                map.put(tmp.getPlaceUid(), tmp);
            }
            Set<Object> set = map.keySet();
            itr = set.iterator();
            while (itr.hasNext())
            {
                Long uid = (Long) itr.next();
                list.add(map.get(uid));
            }
            return list;
        }
        if (list1 == null)
        {
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            Iterator<Object> itr = list2.iterator();
            while (itr.hasNext())
            {
                PlaceSearchVO tmp = (PlaceSearchVO) itr.next();
                map.put(tmp.getPlaceUid(), tmp);
            }
            Set<Object> set = map.keySet();
            itr = set.iterator();
            while (itr.hasNext())
            {
                Long uid = (Long) itr.next();
                list.add(map.get(uid));
            }
            return list;
        }
        /* if both list1 and list2 are there */
        // load hashmap for first list
        if (list1 != null && list2 != null)
        {
            HashMap<Object, Object> map1 = new HashMap<Object, Object>();
            HashMap<Object, Object> map2 = new HashMap<Object, Object>(); 
            Iterator<Object> itr = list1.iterator();
            while (itr.hasNext())
            {
                PlaceSearchVO tmp = (PlaceSearchVO) itr.next();
                map1.put(tmp.getPlaceUid(), tmp);
            }
            // load hashmap for second list
            itr = list2.iterator();
            while (itr.hasNext())
            {
                PlaceSearchVO tmp = (PlaceSearchVO) itr.next();
                map2.put(tmp.getPlaceUid(), tmp);
            }
            // compare and create list of common
            Set<Object> set = map1.keySet();
            itr = set.iterator();
            while (itr.hasNext())
            {
                Long uid = (Long) itr.next();
                if (map2.containsKey(uid))
                {
                    list.add(map1.get(uid));
                }
            }
        }
        return list;
    }

    /* get name query and run it */
    @SuppressWarnings("unchecked")
    private ArrayList<Object> runNameQuery(String whereClause)
    {
        StringBuffer query = new StringBuffer();
        PlaceSearchVO searchResultName = new PlaceSearchVO();
        logger.debug("Name where clause is: " + whereClause);
        query.append(BASENAMEQUERYSQL);
        query.append(whereClause + " order by o.nm ");

        logger.info(query.toString());
        return executeQuery(query.toString(), searchResultName);
    }

    /* get address query and run it */

    @SuppressWarnings("unchecked")
    private ArrayList<Object> runAddressQuery(String whereClause)
    {
        StringBuffer query = new StringBuffer();
        PlaceSearchVO searchResultAddress = new PlaceSearchVO();
        logger.debug("Address where clause is: " + whereClause);
        query.append(BASEADDRESSQUERYSQL);
        query.append(whereClause);
        logger.info(query.toString());
        return  executeQuery(query.toString(), searchResultAddress);
    }

    /* get telephone query and run it */

    @SuppressWarnings("unchecked")
    private ArrayList<Object> runTeleQuery(String whereClause)
    {
        StringBuffer query = new StringBuffer();
        PlaceSearchVO searchResultTele = new PlaceSearchVO();
        logger.debug("Tele where clause is: " + whereClause);
        query.append(BASETELEQUERYSQL);
        query.append(whereClause);
        return executeQuery(query.toString(),  searchResultTele);
    }

    /* get Entity ID query and run it */

    @SuppressWarnings("unchecked")
    private ArrayList<Object> runEntityIdQuery(String whereClause)
    {
        StringBuffer query = new StringBuffer();
        PlaceSearchVO searchResultEntityId = new PlaceSearchVO();
        logger.debug("EntityID where clause is: " + whereClause);
        query.append(BASEIDQUERYSQL);
        query.append(whereClause);
        return  executeQuery(query.toString(), searchResultEntityId );
    }


    /**
     * To replace a special character in the String
     * 
     * @param toBeRepaced
     *            the String in which the special charecters to be replaced
     * @param specialCharacter
     *            the String to be replaced
     * @param replacement
     *            the String to be replaced with
     * @return the String the String after the replacement fo the special
     *         charecter
     */
    private String replaceCharacters(String toBeRepaced, String specialCharacter, String replacement)
    {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0)
        {
            result.append(toBeRepaced.substring(s, e));
            result.append(replacement);
            s = e + specialCharacter.length();
        }
        result.append(toBeRepaced.substring(s));
        return result.toString();
    }

    private String formatPhoneNumber(String phoneNumber)
    {
        String formattedPhoneNumber = "";
        if (phoneNumber.indexOf("-") == 3 && phoneNumber.lastIndexOf("-") == 4 && phoneNumber.length() == 5)
        {
            formattedPhoneNumber = phoneNumber.substring(0, 4); 
        }
        else if (phoneNumber.indexOf("-") == 0 && phoneNumber.lastIndexOf("-") == 1)
        {
            formattedPhoneNumber = phoneNumber.substring(1, 6); 
        }
        else
            formattedPhoneNumber = phoneNumber;
        return formattedPhoneNumber;

    }

    private ArrayList executeQuery(String query, Object obj)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        ArrayList arBeans = new ArrayList();
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        try
        {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery(); 
            resultSetUtils.mapRsToBeanList(resultSet, resultSet.getMetaData(), obj.getClass(), arBeans);
        }
        catch (SQLException sex)
        {
            sex.printStackTrace();
            logger.error("SQLException in FindPlaceDAO: \n" + sex);
            throw new NEDSSSystemException(sex.toString(), sex);
        }
        catch (Exception ex)
        {
            logger.error("Exception in FindPlaceDAO = " + ex);
            throw new NEDSSSystemException(ex.toString(), ex);

        }   finally  {
        	closeResultSet(resultSet);
        	closeStatement(pstmt);
        	releaseConnection(connection);
        }
        return arBeans;
    }
    
    private String buildEntityIdWhereClause(PlaceSearchVO find)
    {
        StringBuffer sbuf = new StringBuffer();
        if (!StringUtils.isEmpty(find.getRootExtensionTxt()))
        {
            String oper = "=";
            String specialCharacter;
            String rootExtension = find.getRootExtensionTxt().trim();
            if (rootExtension.indexOf("'") > 0)
            {
                specialCharacter = "'";
                rootExtension = replaceCharacters(rootExtension, specialCharacter, "''");
            }
            if (oper.length() != 0)
                sbuf.append("AND (EI.TYPE_CD = '" + NEDSSConstants.ENTITY_TYPECD_QEC + "')");
            sbuf.append("AND  upper( EI.root_extension_txt) = '" + rootExtension.toUpperCase() + "'  ");

        }
        return sbuf.toString();
    }
}
