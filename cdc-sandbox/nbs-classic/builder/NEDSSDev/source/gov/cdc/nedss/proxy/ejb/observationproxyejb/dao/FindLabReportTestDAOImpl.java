/**
* Name:		FindLabReportTestImpl.java
* Description:	This is a class determine the DAO implementation based on
*               the information provided in the deployment descriptor.
* Copyright:	Copyright (c) 2001In
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.proxy.ejb.observationproxyejb.dao;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.observation.vo.ObservationSearchVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.entity.person.vo.PersonSrchResultVO;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class FindLabReportTestDAOImpl extends DAOBase

{
    //For logging
    static final LogUtils logger = new LogUtils(FindLabReportTestDAOImpl.class.getName());
    static final int MAX_CACHE_COUNT = 105;
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    public void FindLabReportTestDAOImpl() throws NEDSSSystemException
    {
//System.out.println("testing------------------------------->");
    }


  /**
     * Formats a String by removing specified characters with another set of specified characters
     * @param   toBeRepaced  is the characters to be replaced
     * @param   specialCharacter
     * @param   replacement  are the characters to replace the characters being removed
     * @return  String with characters replaced.
     */
    private String replaceCharacters(String toBeRepaced, String specialCharacter, String replacement) {
    	int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
    	try{
	      
	
	      while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
	          result.append(toBeRepaced.substring(s, e));
	          result.append(replacement);
	          s = e+specialCharacter.length();
	      }
	      result.append(toBeRepaced.substring(s));
    	}catch(Exception ex){
    		logger.error("Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
	      return result.toString();
    }


   

    /**
     * Performs a search for person record according to the search criteria in the ObservationSearchVO object
     * @param   find  which is a ObservationSearchVO
     * @param   cacheNumber which is an int
     * @param   fromIndex  which is an int
     * @return  ArrayList<Object> of person
     * @throws  NEDSSSystemException
     */
  /* This method will run 5 or 7 queries to get person serach result */
    public ArrayList<Object> findPersonsByKeyWords(ObservationSearchVO find, int cacheNumber,
		    int fromIndex) throws NEDSSSystemException
    {
		ArrayList<Object> personNameColl=null;
        ArrayList<Object> personAddressColl=null;
        ArrayList<Object> personTeleColl=null;
        ArrayList<Object> personEntityIdColl=null;
        ArrayList<Object> personEthnicityColl=null;
        ArrayList<Object> personRaceColl=null;
        ArrayList<Object> personRoleColl = null;
        String whereClause=null;
      	DisplayPersonList displayPersonList = null;
      	int totalCount = 0;
      	int listCount = 0;
      	ArrayList<Object> displayList = new ArrayList<Object> ();
      	
      	try{
	        /* Has user entered any person and name field for search? */
	        if ( ( find.getBirthTime() != null && find.getBirthTime().trim().length() !=0 )
	            ||( find.getCurrentSex()!= null && find.getCurrentSex().trim().length() !=0 )
	            ||( find.getLocalID() != null && find.getLocalID().trim().length() !=0)
	            ||(find.getLastName() != null && find.getLastName().trim().length() !=0)
	            ||( find.getFirstName() != null && find.getFirstName().trim().length() !=0))
	        {
				 whereClause = buildNameWhereClause(find);
	             whereClause = whereClause + buildStatusWhereClause(find);
	             personNameColl = runNameQuery(whereClause);
	             if(personNameColl != null)
	                logger.debug("Size of name collection is: " + personNameColl.size());
	
	        }
	
	       /* Has user entered any address information */
	        if ( ( find.getStreetAddr1() != null && find.getStreetAddr1().trim().length() !=0)
	            ||(find.getCityDescTxt() != null && find.getCityDescTxt().trim().length() !=0)
	            ||(find.getState() != null && find.getState().trim().length() !=0)
	            ||(find.getZipCd() != null && find.getZipCd().trim().length() !=0))
	        {
	            whereClause = buildAddressWhereClause(find);
	            whereClause = whereClause + buildStatusWhereClause(find);
	            personAddressColl = runAddressQuery(whereClause);
	            if(personAddressColl != null)
	                logger.debug("Size of address collection is: " + personAddressColl.size());
	        }
	        /* Has user entered telephone number for search */
	        if (find.getPhoneNbrTxt()!= null && find.getPhoneNbrTxt().trim().length() !=0)
	        {
	             whereClause = buildTeleWhereClause(find);
	             whereClause = whereClause + buildStatusWhereClause(find);
	             personTeleColl = runTeleQuery(whereClause);
	             if(personTeleColl != null)
	                logger.debug("Size of phone collection is: " + personTeleColl.size());
	
	        }
	        /* Has user entered any EntityId information */
	        if (find.getRootExtensionTxt() != null && find.getRootExtensionTxt().trim().length() !=0)
	        {
	            whereClause = buildEntityIdWhereClause(find);
	            whereClause = whereClause + buildStatusWhereClause(find);
	            personEntityIdColl = runEntityIdQuery(whereClause);
	            if(personEntityIdColl != null)
	                logger.debug("Size of entity ID collection is: " + personEntityIdColl.size());
	        }
	        /* Has user entered Ethnicity */
	        if (find.getEthnicGroupInd() != null && find.getEthnicGroupInd().trim().length() !=0)
	        {
	            whereClause = buildEthnicityWhereClause(find);
	            whereClause = whereClause + buildStatusWhereClause(find);
	            logger.debug("Ethnicity where clauese is: " + whereClause );
	            personEthnicityColl = runEthnicityQuery(whereClause);
	            if(personEthnicityColl != null)
	                logger.debug("Size of ethnicity collection is: " + personEthnicityColl.size());
	        }
	        /* Has user entered Race */
	        if (find.getRaceCd()!= null && find.getRaceCd().trim().length() !=0)
	        {
	            whereClause = buildRaceWhereClause(find);
	            whereClause = whereClause + buildStatusWhereClause(find);
	            personRaceColl = runRaceQuery(whereClause);
	            if(personRaceColl != null)
	                logger.debug("Size of race collection is: " + personRaceColl.size());
	        }
	        /* Has user entered Role */
	        if (find.getRole()!= null && find.getRole().trim().length() !=0)
	        {
	            whereClause = buildRoleWhereClause(find);
	            whereClause = whereClause + buildStatusWhereClause(find);
	            personRoleColl = runRoleQuery(whereClause);
	            if(personRoleColl != null)
	                logger.debug("Size of role collection is: " + personRoleColl.size());
	        }
	        /* Find common PersonUIDs from all the queries */
	        ArrayList<Object> idList = filterIDs ( personNameColl, personAddressColl,
	                            personTeleColl, personEntityIdColl,
	                            personEthnicityColl, personRaceColl, personRoleColl);
	       // logger.debug("Size of idList is: " + idList.size());
	        logger.debug("Ids are: " + idList);
	
	        /* generate where clause with those common personUIDs */
	        whereClause = generateWhereClause(idList);
	        if (whereClause != null)
	        {
	                personNameColl = runNameQuery(whereClause);
	                personAddressColl = runAddressQuery(whereClause);
	                personTeleColl = runTeleQuery(whereClause);
	                personEntityIdColl = runEntityIdQuery(whereClause);
	        }
	        /* Iterate through IDList and find out attribute for each collection
	           put them as personsearchresultVo and put it in ArrayList<Object> */
	        ArrayList<Object> searchResult = new ArrayList<Object> ();
	       Iterator<Object>  itr = idList.iterator();
	        while (itr.hasNext())
	        {
	          PatientSrchResultVO  srchResultVO = new PatientSrchResultVO();
	          ArrayList<Object> nameList = new ArrayList<Object> ();
	          Long personUid = (Long)itr.next();
	         Iterator<Object>  nameItr = personNameColl.iterator();
	          CachedDropDownValues cache = new CachedDropDownValues();
	          while (nameItr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)nameItr.next();
	            if (tmp.getPersonUid().equals(personUid) )
	            {
					if ( ! (tmp.getNameUseCd() == null))
					{
	                  PersonNameDT nameDT = new PersonNameDT();
					  nameDT.setPersonUid(personUid);
					  logger.debug("Full.. Name " + tmp.getFirstName() + " "  + tmp.getLastName());
					  nameDT.setFirstNm(tmp.getFirstName());
					  nameDT.setLastNm(tmp.getLastName());
	                  if (tmp.getNameUseCd() != null && tmp.getNameUseCd().trim().length() != 0)
	                  {
	                    TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("P_NM_USE");
	                    map = cache.reverseMap(map); // we can add another method that do not do reverse
						nameDT.setNmUseCd((String)map.get(tmp.getNameUseCd()));
					  }
	
					 // nameDT.setNmUseCd(tmp.getNameDesc());
					  nameList.add(nameDT);
					  srchResultVO.setPersonDOB(tmp.getDob());
	                  if (tmp.getCurrentSex() != null && tmp.getCurrentSex().trim().length() != 0)
	                  {
	                    TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("SEX");
	                    map = cache.reverseMap(map); // we can add another method that do not do reverse
						srchResultVO.setCurrentSex((String)map.get(tmp.getCurrentSex()));
					}
	                  srchResultVO.setPersonUID(personUid);
						srchResultVO.setPersonLocalID(tmp.getLocalId());
	
				 }//if ( ! (tmp.getNameUseCd() == null))
	            }//if (tmp.getPersonUid() == personUid)
	          }// while (nameItr.hasNext())
	
	          srchResultVO.setPersonNameColl(nameList);
	          //logger.debug("Number of names added : " + nameList.size());
	          // for address
	          ArrayList<Object> locatorList = new ArrayList<Object> ();
	         Iterator<Object>  addressItr = personAddressColl.iterator();
	          while (addressItr.hasNext())
	          {
	            EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)addressItr.next();
	            if (tmp.getPersonUid().equals(personUid) )
	            {
			if ( tmp.getClassCd() != null)
	                {
		              srchResultVO.setPersonUID(personUid);
	        	      entityLocatorDT.setCd(tmp.getLocatorCd());
			   //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
	                      if (tmp.getLocatorCd() != null && tmp.getLocatorCd().trim().length() != 0)
	                      {
	                        TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE");
	                        map = cache.reverseMap(map); // we can add another method that do not do reverse
			        entityLocatorDT.setCdDescTxt((String)map.get(tmp.getLocatorCd()));
			      }
	        	      entityLocatorDT.setClassCd(tmp.getClassCd());
			      entityLocatorDT.setEntityUid(personUid);
			      entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
			     // entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
	                      if (tmp.getLocatorUseCd() != null && tmp.getLocatorUseCd().trim().length() != 0)
	                      {
	                        TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE");
	                        map = cache.reverseMap(map); // we can add another method that do not do reverse
			        entityLocatorDT.setUseCd((String)map.get(tmp.getLocatorUseCd()));
			      }
	
	                      entityLocatorDT.setCd(tmp.getLocatorCd());
	                      PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
			      postalLocatorDT.setStateCd(tmp.getState());
			      postalLocatorDT.setCityCd(tmp.getCity());
			      postalLocatorDT.setCityDescTxt(tmp.getCity());
			      postalLocatorDT.setStreetAddr1(tmp.getStreetAddr1());
			      postalLocatorDT.setPostalLocatorUid(tmp.getLocatorUid());
			      postalLocatorDT.setZipCd(tmp.getZip());
			      entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
	                      locatorList.add(entityLocatorDT);
	                      logger.debug("Added address locator: " );
	                }
	            }
	          }//while (addressItr.hasNext())
	        //  logger.debug("Number of address added : " + locatorList.size());
	
	
	          // tele locator
	         Iterator<Object>  teleItr = personTeleColl.iterator();
	          while (teleItr.hasNext())
	          {
	            EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)teleItr.next();
	            if (tmp.getPersonUid().equals(personUid))
	            {
			if ( tmp.getClassCd() != null)
	                {
		              srchResultVO.setPersonUID(personUid);
	        	      entityLocatorDT.setCd(tmp.getLocatorCd());
			   //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
	                      if (tmp.getLocatorCd() != null && tmp.getLocatorCd().trim().length() != 0)
	                      {
	                        TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE");
	                        map = cache.reverseMap(map); // we can add another method that do not do reverse
			        entityLocatorDT.setCdDescTxt((String)map.get(tmp.getLocatorCd()));
			      }
			      entityLocatorDT.setClassCd(tmp.getClassCd());
			      entityLocatorDT.setEntityUid(personUid);
			      entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
			  //    entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
	                      if (tmp.getLocatorUseCd() != null && tmp.getLocatorUseCd().trim().length() != 0)
	                      {
	                        TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE");
	                        map = cache.reverseMap(map); // we can add another method that do not do reverse
			        entityLocatorDT.setUseCd((String)map.get(tmp.getLocatorUseCd()));
			      }
	                      entityLocatorDT.setCd(tmp.getLocatorCd());
	
	                      TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
			      teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
			      teleLocatorDT.setExtensionTxt(tmp.getExtensionTxt());
	                      teleLocatorDT.setEmailAddress(tmp.getEmailAddress());
			      entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	                      locatorList.add(entityLocatorDT);
	                      logger.debug("Added tele locator: " );
	                }
	            }
	          } //while (teleItr.hasNext())
	          srchResultVO.setPersonLocatorsColl(locatorList);
	          // For ID
	          ArrayList<Object> entityIdList = new ArrayList<Object> ();
	         Iterator<Object>  idItr = personEntityIdColl.iterator();
	          while (idItr.hasNext())
	          {
	            EntityIdDT entityIdDT = new EntityIdDT();
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)idItr.next();
	            if (tmp.getPersonUid().equals(personUid) )
	            {
	                if (! (tmp.getEiTypeDesc() == null))
	                {
			  //logger.debug("inside typeCd, what is it? "+ typeCd);
			  srchResultVO.setPersonUID(personUid);
			  entityIdDT.setEntityUid(personUid);
	                  if (tmp.getEiTypeDesc() != null && tmp.getEiTypeDesc().trim().length() != 0)
	                  {
	                    TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EI_TYPE");
	                    map = cache.reverseMap(map); // we can add another method that do not do reverse
			    entityIdDT.setTypeCd((String)map.get(tmp.getEiTypeDesc()));
			  }
			  entityIdDT.setRootExtensionTxt(tmp.getEiRootExtensioTxt());
			  entityIdList.add(entityIdDT);
	                }
	            }
	          } //while (idItr.hasNext())
	          srchResultVO.setPersonIdColl(entityIdList);
	          searchResult.add(srchResultVO);
	          totalCount++;
	        }// while (itr.hasNext())
	       	ArrayList<Object> cacheList = new ArrayList<Object> ();
	        for(int j = 0; j < searchResult.size(); j++)
	        {
	            PersonSrchResultVO psvo = new PatientSrchResultVO();
	            psvo =  (PersonSrchResultVO)searchResult.get(j);
	            if(fromIndex > searchResult.size())
		      break;
	            if(cacheNumber == listCount)
		      break;
	            cacheList.add(searchResult.get(j));
		    listCount++;
		    logger.debug("List Counts = " + listCount);
	        }
	
	        
	       	displayPersonList = new DisplayPersonList(totalCount, cacheList, fromIndex, listCount);
	        displayList.add(displayPersonList);
      	}catch(Exception ex){
      		logger.error("Exception ="+ex.getMessage(),ex);
      		throw new NEDSSSystemException(ex.toString(), ex);
      	}
        return displayList;
    }
    /* find common personUids from all collections */
    private ArrayList<Object> filterIDs ( ArrayList<Object> personNameColl,
                                  ArrayList<Object> personAddressColl,
                                  ArrayList<Object> personTeleColl, ArrayList<Object> personEntityIdColl,
                                  ArrayList<Object> personEthnicityColl, ArrayList<Object> personRaceColl,
                                  ArrayList<Object> personRoleColl)
    {
        ArrayList<Object> list = new ArrayList<Object> ();
        list = (ArrayList<Object> )getIDs(personNameColl,personAddressColl);
        list = (ArrayList<Object> )getIDs(personTeleColl, list);
        list = (ArrayList<Object> )getIDs(personEntityIdColl, list);
        list = (ArrayList<Object> )getIDs(personEthnicityColl, list);
        list = (ArrayList<Object> )getIDs(personRaceColl, list);
        list = (ArrayList<Object> )getIDs(personRoleColl, list);
        ArrayList<Object> idList = new ArrayList<Object> ();
        int cacheCount =0;
        
        try{
	       /* get default cache count from property file. If not specified there
	        take one from this file */
	        if (propertyUtil.getNumberOfRows() != 0 )
	         cacheCount= propertyUtil.getNumberOfRows() ;
	        else
	          cacheCount = MAX_CACHE_COUNT;
	
	        if (list != null)
	        {
	           if (list.size() > cacheCount)
	            list.subList(cacheCount, list.size() ).clear();
	         Iterator<Object>  itr = list.iterator();
	          while (itr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
	            idList.add(tmp.getPersonUid());
	          }
	        }
        }catch(Exception ex){
        	logger.error("Exception ="+ex.getMessage(),ex);
        	throw new NEDSSSystemException(ex.toString(), ex);
        }
        return idList;
    }
    /* find common personUids from two lists. */
    private List<Object> getIDs(ArrayList<Object>  list1, ArrayList<Object> list2)
    {
    	ArrayList<Object> list = new ArrayList<Object> ();
   //   int cacheCount =0;
    	try{
	      if (list1 == null && list2 == null)
	        return null;
	      
	      if (list2 == null)
	      {
	          HashMap<Object,Object> map = new HashMap<Object,Object>();
	         Iterator<Object>  itr = list1.iterator();
	          while (itr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)itr.next();
	            map.put(tmp.getPersonUid(), tmp);
	          }
	          Set<Object> set = map.keySet();
	          itr = set.iterator();
	          while(itr.hasNext())
	          {
	              Long uid = (Long) itr.next();
	              list.add(map.get(uid));
	          }
	          return list;
	      }
	      if (list1 == null)
	      {
	          HashMap<Object,Object> map = new HashMap<Object,Object>();
	         Iterator<Object>  itr = list2.iterator();
	          while (itr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)itr.next();
	            map.put(tmp.getPersonUid(), tmp);
	          }
	          Set<Object> set = map.keySet();
	          itr = set.iterator();
	          while(itr.hasNext())
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
	          HashMap<Object,Object> map1 = new HashMap<Object,Object>();
	          HashMap<Object,Object> map2 = new HashMap<Object,Object>();
	          int count =0;
	         Iterator<?>  itr = list1.iterator();
	          while (itr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)itr.next();
	            map1.put(tmp.getPersonUid(), tmp);
	          }
	        // load hashmap for second list
	          itr = list2.iterator();
	          while (itr.hasNext())
	          {
	            PersonSearchResultTmp tmp = (PersonSearchResultTmp)itr.next();
	            map2.put(tmp.getPersonUid(), tmp);
	          }
	          // compare and create list of common
	          Set<?> set = map1.keySet();
	          itr = set.iterator();
	          while(itr.hasNext())
	          {
	            Long uid = (Long) itr.next();
	            if ( map2.containsKey(uid))
	            {
	              list.add(map1.get(uid));
	            }
	          }
	        }
    	}catch(Exception ex){
    		logger.error("Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
        return list;
    }

    /* get name query and run it */
    @SuppressWarnings("unchecked")
	private ArrayList<Object> runNameQuery(String whereClause)
    {
       ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
	       PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
	       query.append(NEDSSSqlQuery.BASENAMEQUERYSQL);
	       query.append(whereClause);
	       logger.debug("query........." + query);
	       list =  (ArrayList<Object> )preparedStmtMethod(searchResultName, null,
	                          query.toString() , NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
    /* get address query and run it */

    @SuppressWarnings("unchecked")
	private ArrayList<Object> runAddressQuery(String whereClause)
    {
       ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
	       PersonSearchResultTmp searchResultAddress = new PersonSearchResultTmp();
	       logger.debug("Address where clause is: " + whereClause);
	      query.append(NEDSSSqlQuery.BASEADDRESSQUERYSQL);
	       query.append(whereClause);
	       list = (ArrayList<Object> )preparedStmtMethod(searchResultAddress, null,
	                          query.toString() , NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
    /* get telephone query and run it */

    @SuppressWarnings("unchecked")
	private ArrayList<Object> runTeleQuery(String whereClause)
    {
       ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
	       PersonSearchResultTmp searchResultTele = new PersonSearchResultTmp();
	       logger.debug("Tele where clause is: " + whereClause);
	       query.append(NEDSSSqlQuery.BASETELEQUERYSQL);
	       query.append(whereClause);
	       list =  (ArrayList<Object> )preparedStmtMethod(searchResultTele, null,
	                           query.toString(), NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
    /* get Entity ID query and run it */

    @SuppressWarnings("unchecked")
	private ArrayList<Object> runEntityIdQuery(String whereClause)
    {
    	ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
	       PersonSearchResultTmp searchResultEntityId = new PersonSearchResultTmp();
	       logger.debug("EntityID where clause is: " + whereClause);
	      query.append(NEDSSSqlQuery.BASEIDQUERYSQL);
	       query.append(whereClause);
	       list = (ArrayList<Object> )preparedStmtMethod(searchResultEntityId, null,
	                          query.toString(), NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
    /* get Ethnicity query and run it */
    @SuppressWarnings("unchecked")
	private ArrayList<Object> runEthnicityQuery(String whereClause)
    {
    	ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
	       PersonSearchResultTmp searchResultEthnicity = new PersonSearchResultTmp();
	       logger.debug("Ethnicity where clause is: " + whereClause);
	      query.append(NEDSSSqlQuery.BASEETHNICITYQUERYSQL);
	       query.append(whereClause);
	        list = (ArrayList<Object> )preparedStmtMethod(searchResultEthnicity, null,
                          query.toString(), NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
    /* get Race query and run it */
    @SuppressWarnings("unchecked")
	private ArrayList<Object> runRaceQuery(String whereClause)
    {
    	ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
       PersonSearchResultTmp searchResultRace = new PersonSearchResultTmp();
       logger.debug("Race where clause is: " + whereClause);
       query.append(NEDSSSqlQuery.BASERACEQUERYSQL);
       query.append(whereClause);
        list = (ArrayList<Object> )preparedStmtMethod(searchResultRace, null,
                          query.toString(), NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
  /* get role query and run it */
    @SuppressWarnings("unchecked")
	private ArrayList<Object> runRoleQuery(String whereClause)
    {
    	ArrayList<Object> list = new ArrayList<Object> ();
       StringBuffer  query = new StringBuffer();
       try{
       PersonSearchResultTmp searchResultRace = new PersonSearchResultTmp();
       logger.debug("Role where clause is: " + whereClause);
      query.append(NEDSSSqlQuery.BASEROLEQUERYSQL);
       query.append(whereClause);
        list = (ArrayList<Object> )preparedStmtMethod(searchResultRace, null,
                          query.toString(), NEDSSConstants.SELECT);
       }catch(Exception ex){
    	   logger.error("Exception ="+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString(), ex);
       }
       return list;
    }
  /* Depending on user input build where clause for person and name query */
    private String buildNameWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      String oper = null;
      String whereAnd = " where " ;
      boolean firstWhere = false;
      try{
	      if (find.getBirthTime() != null)
	      {
	        if (find.getBirthTime().trim().length() != 0)
	        {
		  if (find.getBirthTimeOperator().trim().length() != 0)
		    if (firstWhere)
		      firstWhere = false;
		    else
		      whereAnd = " AND ";
		    	sbuf.append(whereAnd + " p.birth_time  "  + find.getBirthTimeOperator() +  " CONVERT(datetime,'" + find.getBirthTime() + "')") ;
		    }
	      }
	      if (find.getCurrentSex() !=  null)
	      {
	        if (find.getCurrentSex().trim().length() != 0)
	        {
		  if (firstWhere)
		     firstWhere = false;
		   else
		      whereAnd = " AND ";
		   sbuf.append(whereAnd + " p.curr_sex_cd = " +"'" + find.getCurrentSex().trim()+ "' ") ;
	        }
	      }
	      if (find.getLocalID()!= null)
	      {
	        if (find.getLocalID().trim().length() !=0)
	        {
		  if (firstWhere)
		    firstWhere = false;
		  else
		    whereAnd = " AND ";
		  sbuf.append(whereAnd + " p.local_id = " + "'" + find.getLocalID().trim()+ "' ") ;
		}
	      }
	      if (find.getLastName()!= null)
	      {
	      	if (find.getLastName().trim().length() !=0)
	        {
		     oper = find.getLastNameOperator() .trim();
		     if (oper.length() !=0)
		         if (firstWhere)
			   firstWhere = false;
			else
			   whereAnd = " AND ";
			String personLastName = find.getLastName().trim();
	                String specialCharacter ;
	                logger.debug("\n\n\ntoBeEscaped :" + personLastName);
	                if(personLastName.indexOf("'")>0)
	                {
	                    logger.debug("inside if and index of \"'\" is :" + personLastName.indexOf("'"));
	                    specialCharacter = "'";
	                    personLastName = replaceCharacters(personLastName,specialCharacter, "''" );
	                    logger.debug("query is :"+ personLastName);
	
	                }
	                if (oper.equalsIgnoreCase("SL")) // sounds like
			  sbuf.append(whereAnd + " (soundex(upper( PN.last_nm))  =  soundex('"  + personLastName.toUpperCase() + "'))");
			else if (oper.equalsIgnoreCase("CT"))
	                { // contains
			  sbuf.append(whereAnd + "( upper( PN.last_nm)  like '%" +  personLastName.toUpperCase() + "%')"  );
			  //sbuf.append(" and PN.nm_use_cd = '0')");
			}
			else
	                {
			  sbuf.append(whereAnd + " (upper( PN.last_nm) " +  oper + "   '"  + personLastName.toUpperCase()+"')"  );
			  //sbuf.append(" and PN.nm_use_cd = '0')");
			}
		}
	      }
	      if (find.getFirstName()!= null)
	      {
	      	  if (find.getFirstName().trim().length() !=0)
	          {
		      oper = find.getFirstNameOperator() .trim();
		      if (oper.length() !=0)
		       if (firstWhere)
			   firstWhere = false;
			else
			   whereAnd = " AND ";
			String personFirstName = find.getFirstName().trim();
	                String specialCharacter ;
	                logger.debug("personFirstName is :" + personFirstName+"test");
	                logger.debug("\n\n\ntoBeEscaped :" + personFirstName);
	                if(personFirstName.indexOf("'")>0)
	                {
	                    logger.debug("inside if and index of \"'\" is :" + personFirstName.indexOf("'"));
	                    specialCharacter = "'";
	                    personFirstName = replaceCharacters(personFirstName,specialCharacter, "''" );
	                    logger.debug("query is :"+ personFirstName);
	
	                }
	                if (oper.equalsIgnoreCase("SL")) // sounds like
			  sbuf.append(whereAnd + " (soundex(upper( PN.first_nm))  =  soundex('"  + personFirstName.toUpperCase() + "'))");
			else if (oper.equalsIgnoreCase("CT")) // contains
			  sbuf.append(whereAnd + "( upper( PN.first_nm)  like '%" +  personFirstName.toUpperCase() + "%')"  );
			else
			  sbuf.append(whereAnd + " (upper( PN.first_nm) " +  oper + "   '"  + personFirstName.toUpperCase()+"')"  );
	
		  }
	        }
	        //New Code for Point in Time
        
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      
      return sbuf.toString();
    }

    private void addPersonCodeFilterToWhereClause(String whereClause, ObservationSearchVO find){

    	try{
	      StringBuffer strbuf = new StringBuffer(whereClause);
	      String whereAnd = " WHERE ";
	      if (strbuf.length() == 0)
	        whereAnd = " AND ";
	
	      strbuf.append(" p.cd = 'PROV' ");
	      whereClause =  strbuf.toString();
    	}catch(Exception ex){
    		logger.error("Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}

    }
  /* Depending on user input build where clause for address query */
    private String buildAddressWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      String oper = null;
      String whereAnd = " where " ;
      boolean firstWhere = false;

      try{
	      if (find.getStreetAddr1()!= null)
	      {
	        if (find.getStreetAddr1().trim().length() != 0 )
	        {
		  oper =  find.getStreetAddr1Operator().trim();
		  if (oper.length() != 0 )
		       if (firstWhere)
			  firstWhere = false;
		       else
			    whereAnd = " AND ";
	              String streetAddress = find.getStreetAddr1().trim();
	              String specialCharacter ;
	              logger.debug("street is :" + streetAddress+"test");
	              logger.debug("\n\n\ntoBeEscaped :" + streetAddress);
	              if(streetAddress.indexOf("'")>0)
	              {
	                logger.debug("inside if and index of \"'\" is :" + streetAddress.indexOf("'"));
	                specialCharacter = "'";
	                streetAddress = replaceCharacters(streetAddress,specialCharacter, "''" );
	                logger.debug("query is :"+ streetAddress);
	
	              }
		    if (oper.equalsIgnoreCase("SL"))
		      sbuf.append(whereAnd + " soundex (upper(PL.street_addr1)) = soundex('"   + streetAddress.toUpperCase()+"')");
		    else if (oper.equalsIgnoreCase("CT")) // contains
		      sbuf.append(whereAnd + " upper( PL.street_addr1)  like '%" +  streetAddress.toUpperCase() + "%'"  );
		    else
		      sbuf.append(whereAnd + " upper(PL.street_addr1) " + oper.toUpperCase() + " '" +streetAddress.toUpperCase()+"'" ) ;
	        }
	      }
	      if (find.getCityDescTxt() != null)
	      {
	      	if (find.getCityDescTxt().trim().length() != 0)
	        {
		  oper =  find.getCityDescTxtOperator().trim();
		  if (oper.length() != 0)
		       if (firstWhere)
			    firstWhere = false;
		       else
			    whereAnd = " AND ";
		    if (oper.equalsIgnoreCase("SL"))
		      sbuf.append(whereAnd + " soundex (upper(PL.city_desc_txt)) = soundex('"   + find.getCityDescTxt().trim().toUpperCase()+"')");
		    else if (oper.equalsIgnoreCase("CT")) // contains
		      sbuf.append(whereAnd + " upper( PL.city_desc_txt)  like '%" +  find.getCityDescTxt().trim().toUpperCase() + "%'"  );
		    else
		    sbuf.append(whereAnd + " upper(PL.city_desc_txt) " + oper.toUpperCase()+ " '" + find.getCityDescTxt().trim().toUpperCase() +"'" ) ;
	
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
	               sbuf.append(whereAnd + " upper(PL.state_cd) =  '" + find.getState().trim().toUpperCase() +"'" ) ;
	
	          }
	        }
	        if (find.getZipCd() != null)
	        {
	          if (find.getZipCd().trim().length() != 0)
	          {
		    oper =  find.getZipCdOperator().trim();
		    if (oper.length() != 0)
		       if (firstWhere)
			  firstWhere = false;
		       else
			  whereAnd = " AND ";
		      if (oper.equalsIgnoreCase("SL"))
		        sbuf.append(whereAnd + " soundex (upper(PL.zip_cd)) = soundex('"   + find.getZipCd().trim().toUpperCase()+"')");
		      else if (oper.equalsIgnoreCase("CT")) // contains
		        sbuf.append(whereAnd + " upper( PL.zip_cd)  like '%" +  find.getZipCd().trim().toUpperCase() + "%'"  );
		      else
		        sbuf.append(whereAnd + " upper(PL.zip_cd) " + oper + " '" + find.getZipCd().trim().toUpperCase() +"'" ) ;
	          }
	        }
	        //New Code for Point in Time
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();
    }
  /* Depending on user input build where clause for tele query */
    private String buildTeleWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      String oper = null;
      String whereAnd = " where " ;
      boolean firstWhere = false;
      try{
	      if (find.getPhoneNbrTxt() != null)
	      {
	        if (find.getPhoneNbrTxt().trim().length() != 0)
	        {
		  oper = find.getPhoneNbrTxtOperator().trim();
		  if (oper.length() !=0)
		    if (firstWhere)
			firstWhere = false;
		     else
			whereAnd = " AND ";
		    if (oper.equalsIgnoreCase("SL"))
		      sbuf.append(whereAnd + " soundex (upper(TL.Phone_nbr_txt)) = soundex('"   + find.getPhoneNbrTxt().trim().toUpperCase()+"')");
		    else if (oper.equalsIgnoreCase("CT")) // contains
		      sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '%" +  find.getPhoneNbrTxt().trim().toUpperCase() + "%'"  );
		    else
		 sbuf.append(whereAnd + " TL.phone_nbr_txt" + oper + " '" + find.getPhoneNbrTxt() +"'") ;
	        }
	      }
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
     return sbuf.toString();
    }
  /* Depending on user input build where clause for entity id query */
    private String buildEntityIdWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      try{
	        if (find.getRootExtensionTxt() != null && find.getTypeCd() !=  null)
	        {
	          if (find.getTypeCd().trim().length() !=0)
	          {
	            if(find.getRootExtensionTxt().trim().length() !=0)
	            {
		         // There is no operator for ID and value; take default
	              String oper="=";
	              String specialCharacter ;
	              String rootExtension = find.getRootExtensionTxt().trim();
	           //   logger.debug("rootExtension is :" + oper+"test");
	           //   logger.debug("\n\n\ntoBeEscaped :" + oper);
	              if(rootExtension.indexOf("'")>0)
	              {
	                logger.debug("inside if and index of \"'\" is :" + rootExtension.indexOf("'"));
	                specialCharacter = "'";
	                rootExtension = replaceCharacters(rootExtension,specialCharacter, "''" );
	                logger.debug("oper query is :"+ rootExtension);
	              }
	              if (oper.length() !=0)
	            	  sbuf.append( "AND (EI.TYPE_CD = '" + find.getTypeCd().trim() + "')");
	              sbuf.append("AND  upper( EI.root_extension_txt) = '" +  rootExtension.toUpperCase() + "'  "  );
	
		      } // end of 2nd if
	          }
	        }
	        //New Code for Point in Time

      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();
    }
  /* Depending on user input build where clause for ethnicity query */
    private String buildEthnicityWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      try{
	      if (find.getEthnicGroupInd() !=  null)
	      {
	        if (find.getEthnicGroupInd().trim().length() != 0)
	        {
	        	sbuf.append(" where p.ethnic_group_ind = " + "'" + find.getEthnicGroupInd().trim() + "' ") ;
	         //New Code for Point in Time
	
	        }
	      }
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();
    }
  /* Depending on user input build where clause for race query */
    private String buildRaceWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      try{
      if (find.getRaceCd() != null)
      {
        if (find.getRaceCd().trim().length() != 0)
        {
          sbuf.append(" and pr.race_category_cd = '" + find.getRaceCd()+ "'");
          //New Code for Point in Time
         
        }
      }
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();
    }
  /* Depending on user input build where clause for role query */
    private String buildRoleWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      try{
	      if (find.getRole() != null)
	      {
	        if (find.getRole().trim().length() != 0)
	        {
	          sbuf.append(" and r.cd = '" + find.getRole() + "'")  ;
	        }
	      }
	
	      //New Code for Point in Time
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();
    }
  /* Depending on user input build where clause for status.
     This query should be appended to all other queries. */
    private String buildStatusWhereClause(ObservationSearchVO find)
    {
      StringBuffer sbuf = new StringBuffer();
      String oper = null;
      String whereAnd = " where " ;
      boolean firstWhere = false;
      int noCodes = 0;
      try{
	      if (find.isActive() ||  find.isInActive() ||  find.isSuperceded())
	      {
	
	        if (find.isActive())
	        {
	          if (find.getStatusCodeActive().trim().length() != 0)
	          {
		    if (firstWhere)
			 firstWhere = false;
		      else
			 whereAnd = " AND ( ";
	
	              sbuf.append(whereAnd + " (p.record_status_cd =  '" +  find.getStatusCodeActive().trim() + "')");
		      noCodes += 1;
		  }
		}
	      }
	      if(find.isInActive())
	      {
	          if (find.getStatusCodeInActive().trim().length() != 0)
	          {
		    if (firstWhere)
		      firstWhere = false;
		    else
		      if ( noCodes > 0)
			whereAnd = " OR ";
		      else
			whereAnd = " AND ( ";
		      sbuf.append(whereAnd + " ( p.record_status_cd = '" +  find.getStatusCodeInActive().trim() + "')");
		      noCodes += 1;
	
		  }
	      }
	      if(find.isSuperceded())
	      {
	          if (find.getStatusCodeSuperCeded().trim().length() != 0)
	          {
	            if (firstWhere)
		      firstWhere = false;
		    else
		      if ( noCodes > 0)
			whereAnd = " OR ";
		      else
			whereAnd = " AND ( ";
	            sbuf.append(whereAnd + " ( p.record_status_cd = '" +  find.getStatusCodeSuperCeded().trim() + "')");
		      //sbuf.append(whereAnd + " ( p.status_cd = '" +  "S" + "')");
		    noCodes += 1;
	
	          }
	      }
	      sbuf.append(" )  ");
      }catch(Exception ex){
    	  logger.error("Exception ="+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return sbuf.toString();

    }
  /* Generate where clause based on common personUIDs in the list */
    private String generateWhereClause(ArrayList<Object>  list)
    {
    	StringBuffer sbuf = new StringBuffer();
    	try{
	      if (list == null || list.size() == 0)
	        return null;
	      boolean firstComma = true;
	      
	      sbuf.append(" and p.person_uid in ( ") ;
	     Iterator<Object>  itr = list.iterator();
	      while (itr.hasNext())
	      {
	        if (firstComma)
	          firstComma = false;
	        else
	          sbuf.append(" , ");
	        sbuf.append(itr.next());
	      }
	      sbuf.append(")");
    	}catch(Exception ex){
    		logger.error("Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
      return sbuf.toString();
    }
   // }



    public static void main(String [] args){
    try{

	FindLabReportTestDAOImpl search = new FindLabReportTestDAOImpl();
        /*
	ObservationSearchVO searchVO = new ObservationSearchVO();
	 searchVO.setLastName("LastName0");
	 searchVO.setLastNameOperator("=");
	searchVO.setFirstName("Williams");
	searchVO.setFirstNameOperator("=");
	 searchVO.setStreetAddr1("StreetAddr1");
	 searchVO.setStreetAddr1Operator("=");
	  searchVO.setBirthTime("10/13/1988");
	  searchVO.setBirthTimeOperator("=");
	   searchVO.setRootExtensionTxt("Root ext text");
	   searchVO.setRootExtensionTxtOperator("=");
	 searchVO.setPhoneNbrTxt("404-417-3151");
	  searchVO.setPhoneNbrTxtOperator("=");
	  searchVO.setActive(true);
	ArrayList<Object> arr = search.findPersonsByKeyWords(searchVO,0,0);
	StringBuffer testQuery = new StringBuffer(" ");
	testQuery.append(" ORDER BY 1");
	logger.debug("Complete Query is: " + testQuery.toString());
          */
    }
    catch(Exception e)
    {
	e.printStackTrace();
    }
  }



}
