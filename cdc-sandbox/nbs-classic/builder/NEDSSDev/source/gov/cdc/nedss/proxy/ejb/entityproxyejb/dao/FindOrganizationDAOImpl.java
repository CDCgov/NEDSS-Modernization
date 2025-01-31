package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchResultTmp;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
* Name:		FindOrganizationDAOImpl.java
* Description:	This is a class determine the DAO implementation based on
*               the information provided in the deployment descriptor.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

public class FindOrganizationDAOImpl extends DAOBase

{

 //For logging

  static final LogUtils logger = new LogUtils(FindOrganizationDAOImpl.class.getName());
  PropertyUtil propertyUtil= PropertyUtil.getInstance();

  /* To test new optimization */
  public static final String BASENAMEQUERYSQL =
      " SELECT o.organization_uid \"organizationUid\", o.local_id \"localId\", onm.nm_txt \"name\",  "+
      " o.record_status_cd \"recordStatusCd\", "+
      " o.version_ctrl_nbr \"versionCtrlNbr\", " +
      " onm.nm_use_cd \"nameUseCd\" " +
      " FROM organization o with (nolock), organization_name onm with (nolock)  "+
      " where " +
      "	o.organization_uid = onm.organization_uid "+
      " and o.electronic_ind = 'N' "+
      " and (o.edx_ind is null or o.edx_ind != 'Y') "+
      " and o.record_status_cd = 'ACTIVE' " ;

  public static final String BASEADDRESSQUERYSQL =
      " SELECT o.organization_uid \"organizationUid\", o.local_id \"localId\", "+
      " o.record_status_cd \"recordStatusCd\", "+
      " o.version_ctrl_nbr \"versionCtrlNbr\", " +
      " pl.postal_locator_uid \"locatorUid\", "+
      "	elp1.class_Cd \"classCd\", "+
      " elp1.cd \"locatorTypeCdDesc\" , " +
      "	elp1.use_cd \"locatorUseCd\", elp1.cd \"locatorCd\", "+
      " pl.street_addr1 \"streetAddr1\", "+
      " pl.street_addr2 \"streetAddr2\", "+
      "	pl.city_desc_txt \"city\", pl.zip_cd \"zip\", pl.cnty_cd \"cntyCd\",  pl.state_cd \"state\" "+
      "	from organization o with (nolock), entity_locator_participation elp1 with (nolock), " +
      "	postal_locator pl with (nolock) "+
      "	where o.organization_uid = elp1.entity_uid  "+
      "	and elp1.class_cd = 'PST' "+
      "	and elp1.status_cd = 'A' "+
      "	and elp1.locator_uid = pl.postal_locator_uid " +
      " and o.electronic_ind = 'N' "+
      " and (o.edx_ind is null or o.edx_ind != 'Y') "+
      " and o.record_status_cd = 'ACTIVE' " ;


  public static final String BASEIDQUERYSQL =
      " SELECT o.organization_uid \"organizationUid\", o.local_id \"localId\", o.version_ctrl_nbr \"versionCtrlNbr\","+
      " ei.root_extension_txt \"eiRootExtensionTxt\" ,  " +
      " ei.type_cd \"eiTypeCd\", " +
      " ei.assigning_authority_cd \"eiAssigningAuthorityCd\", " +
      " ei.assigning_authority_desc_txt \"eiAssigningAuthorityDescTxt\", " +
      " ei.type_cd \"eiTypeDesc\" , ei.type_desc_txt \"eiTypeDescTxt\" " +
      "	from " +
      "	organization o with (nolock) ,  Entity_id ei with (nolock) "+
      "	where  "+
      "	o.organization_uid = ei.entity_uid and ei.status_cd = 'A' " +
      " and o.electronic_ind = 'N' "+
      " and (o.edx_ind is null or o.edx_ind != 'Y') "+
      " and o.record_status_cd = 'ACTIVE' " ;

  public static final String BASETELEQUERYSQL =
      "	SELECT o.organization_uid \"organizationUid\", o.local_id \"localId\", "+
      " o.version_ctrl_nbr \"versionCtrlNbr\", " +
      " tl.tele_locator_uid \"locatorUid\","+
      " elp2.class_cd \"classCd\", "+
      " elp2.use_cd \"locatorUseCd\", elp2.cd \"locatorCd\", "+
      " tl.phone_nbr_txt \"telephoneNbr\", " +
      "	tl.extension_txt \"extensionTxt\",  "+
      "	null \"eiRootExtensionTxt\", null \"eiTypeDesc\" "+
      "	from " +
      "	organization o with (nolock) , entity_locator_participation elp2 with (nolock) , " +
      "	tele_locator tl with (nolock) "+
      "  where  "+
      "	o.organization_uid = elp2.entity_uid   "+
      "	and elp2.class_cd = 'TELE' "+
      "	and elp2.status_cd= 'A' "+
      "	and elp2.locator_uid  = tl.tele_locator_uid "+
      " and o.electronic_ind = 'N' "+
      " and (o.edx_ind is null or o.edx_ind != 'Y') "+
      " and o.record_status_cd = 'ACTIVE' " ;



  public static final String BASEROLEQUERYSQL =
      " SELECT  o.organization_uid \"organizationUid\", o.local_id \"localId\", "+
      " o.version_ctrl_nbr \"versionCtrlNbr\" " +
      " FROM organization o with (nolock) , role r with (nolock) " +
      " where o.organization_uid = r.subject_entity_uid " +
      " and o.electronic_ind = 'N' "+
      " and o.record_status_cd = 'ACTIVE' " ;

/* Queries for Oracle */



  static final int MAX_CACHE_COUNT = 105;
   // private String baseQueryOrcl = CdmSqlUtil.getBaseQueryOrcl();

    public FindOrganizationDAOImpl() throws NEDSSSystemException
    {
    }

    /**
     * Passes querry to the databse with the search Criteria and gets the Organization Details
     * and set it ot an arryList of SearchResultVO for the display
     * @param find    the OrganizationSearchVO object
     * @param cacheNumber    the int
     * @param fromIndex     the int
     * @return displayList  the ArrayList
     * @throws NEDSSSystemException
     */
    public ArrayList<Object> findOrganizationsByKeyWords(OrganizationSearchVO find, int cacheNumber,
		    int fromIndex) throws NEDSSSystemException
    {
      ArrayList<Object> organizationNameColl = null;
      ArrayList<Object> organizationAddressColl = null;
      ArrayList<Object> organizationTeleColl = null;
      ArrayList<Object> organizationEntityIdColl = null;
      String whereClause = null;
      DisplayOrganizationList displayOrganizationList = null;

      int totalCount = 0;
      int listCount = 0;
      try{
		      /* Has user entered any organization and name field for search? */
		      if (find.getNmTxt() != null && find.getNmTxt().trim().length() != 0) {
		        whereClause = buildNameWhereClause(find);
		        organizationNameColl = runNameQuery(whereClause);
		        if (organizationNameColl != null)
		          logger.debug("Size of name collection is: " + organizationNameColl.size());

		      }
		      /* Has user entered any address information */
		      if ( (find.getStreetAddr1() != null &&
		            find.getStreetAddr1().trim().length() != 0)
		          ||
		          (find.getCityDescTxt() != null &&
		           find.getCityDescTxt().trim().length() != 0)
		          || (find.getStateCd() != null && find.getStateCd().trim().length() != 0)
		          || (find.getZipCd() != null && find.getZipCd().trim().length() != 0)) {
		        whereClause = buildAddressWhereClause(find);
		        //         whereClause = whereClause + buildStatusWhereClause(find);
		        organizationAddressColl = runAddressQuery(whereClause);
		        if (organizationAddressColl != null)
		          logger.debug("Size of address collection is: " +
		                       organizationAddressColl.size());
		      }
		      /* Has user entered telephone number for search */
		      if (find.getPhoneNbrTxt() != null &&
		          find.getPhoneNbrTxt().trim().length() != 0) {
		        whereClause = buildTeleWhereClause(find);
		        //         whereClause = whereClause + buildStatusWhereClause(find);
		        organizationTeleColl = runTeleQuery(whereClause);
		        if (organizationTeleColl != null)
		          logger.debug("Size of phone collection is: " +
		                       organizationTeleColl.size());

		      }
		      /* Has user entered any EntityId information */
		      if (find.getRootExtensionTxt() != null &&
		          find.getRootExtensionTxt().trim().length() != 0) {
		        whereClause = buildEntityIdWhereClause(find);
		        //whereClause = whereClause + buildStatusWhereClause(find);
		        //add organization code filter to where clause
		        // addorganizationCodeFilterToWhereClause(whereClause, find);
		        //end

		        organizationEntityIdColl = runEntityIdQuery(whereClause);
		        if (organizationEntityIdColl != null)
		          logger.debug("Size of entity ID collection is: " +
		                       organizationEntityIdColl.size());
		      }
		      /* Find common organizationUIDs from all the queries */
		      ArrayList<Object> idList = filterIDs(organizationNameColl, organizationAddressColl,
		                                   organizationTeleColl, organizationEntityIdColl);
		      // logger.debug("Size of idList is: " + idList.size());
		      logger.debug("Ids are: " + idList);

		      /* generate where clause with those common organizationUIDs */
		      whereClause = generateWhereClause(idList);
		      ArrayList<Object> searchResult = new ArrayList<> ();
		      /* If there is any Common UID */
		      if (whereClause != null) {
		        organizationNameColl = runNameQuery(whereClause);
		        organizationAddressColl = runAddressQuery(whereClause);
		        organizationTeleColl = runTeleQuery(whereClause);
		        organizationEntityIdColl = runEntityIdQuery(whereClause);
		        //}
		        /* Iterate through NameCollection  as sorting is to be done by name) and
		          find out attribute for each collection
		          put them as organizationsearchresultVo and put it in ArrayList<Object> */
		        //Iterator itr = idList.iterator();
		       Iterator<Object>  itr = organizationNameColl.iterator();
		        while (itr.hasNext()) {
		          OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
		          ArrayList<Object> nameList = new ArrayList<> ();
		          OrganizationSearchResultTmp tmp1 = (OrganizationSearchResultTmp) itr.
		              next();
		          Long organizationUid = tmp1.getOrganizationUid();
		          // Long organizationUid = (Long)itr.next();
		         Iterator<Object>  nameItr = organizationNameColl.iterator();
		          CachedDropDownValues cache = new CachedDropDownValues();
		          while (nameItr.hasNext()) {
		            OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
		                nameItr.next();
		            if (tmp.getOrganizationUid().equals(organizationUid)) {
		              if (! (tmp.getNameUseCd() == null)) {
		                OrganizationNameDT nameDT = new OrganizationNameDT();
		                nameDT.setOrganizationUid(organizationUid);
		                nameDT.setNmTxt(tmp.getName());
		                if (tmp.getNameUseCd() != null &&
		                    tmp.getNameUseCd().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("O_NM_USE");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  nameDT.setNmUseCd( (String) map.get(tmp.getNameUseCd()));
		                }
		                // nameDT.setNmUseCd(tmp.getNameDesc());
		                nameList.add(nameDT);
		                srchResultVO.setOrganizationUID(organizationUid);
		                srchResultVO.setOrganizationId(tmp.getLocalId());
		                srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
		              } //if ( ! (tmp.getNameUseCd() == null))
		            } //if (tmp.getOrganizationUid() == organizationUid)
		          } // while (nameItr.hasNext())
		          srchResultVO.setOrganizationNameColl(nameList);
		          //logger.debug("Number of names added : " + nameList.size());
		          ArrayList<Object> locatorList = new ArrayList<> ();
		          // for address
		          if(organizationAddressColl!=null)
		          {
		         Iterator<Object>  addressItr = organizationAddressColl.iterator();
		          while (addressItr.hasNext()) {
		            EntityLocatorParticipationDT entityLocatorDT = new
		                EntityLocatorParticipationDT();
		            OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
		                addressItr.next();
		            if (tmp.getOrganizationUid().equals(organizationUid)) {
		              if (tmp.getClassCd() != null) {
		                srchResultVO.setOrganizationUID(organizationUid);
		                srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
		                entityLocatorDT.setCd(tmp.getLocatorCd());
		                //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
		                if (tmp.getLocatorCd() != null &&
		                    tmp.getLocatorCd().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE_PST_ORG");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  entityLocatorDT.setCdDescTxt( (String) map.get(tmp.getLocatorCd()));
		                }
		                entityLocatorDT.setClassCd(tmp.getClassCd());
		                entityLocatorDT.setEntityUid(organizationUid);
		                entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
		                // entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
		                if (tmp.getLocatorUseCd() != null &&
		                    tmp.getLocatorUseCd().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE_PST_ORG");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  entityLocatorDT.setUseCd( (String) map.get(tmp.getLocatorUseCd()));
		                }

		                entityLocatorDT.setCd(tmp.getLocatorCd());
		                PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
		                postalLocatorDT.setStateCd(tmp.getState());
		                postalLocatorDT.setCityCd(tmp.getCity());
		                postalLocatorDT.setCityDescTxt(tmp.getCity());
		                postalLocatorDT.setStreetAddr1(tmp.getStreetAddr1());
		                postalLocatorDT.setStreetAddr2(tmp.getStreetAddr2());
		                postalLocatorDT.setPostalLocatorUid(tmp.getLocatorUid());
		                postalLocatorDT.setZipCd(tmp.getZip());
		                postalLocatorDT.setCntyCd(tmp.getCntyCd());
		                entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
		                locatorList.add(entityLocatorDT);
		                logger.debug("Added address locator: ");
		              }
		            }
		          } //while (addressItr.hasNext())
		          //  logger.debug("Number of address added : " + locatorList.size());
		         }

		          // tele locator
		         Iterator<Object>  teleItr = organizationTeleColl.iterator();
		          while (teleItr.hasNext()) {
		            EntityLocatorParticipationDT entityLocatorDT = new
		                EntityLocatorParticipationDT();
		            OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
		                teleItr.next();
		            if (tmp.getOrganizationUid().equals(organizationUid)) {
		              if (tmp.getClassCd() != null) {
		                srchResultVO.setOrganizationUID(organizationUid);
		                srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
		                entityLocatorDT.setCd(tmp.getLocatorCd());
		                //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
		                if (tmp.getLocatorCd() != null &&
		                    tmp.getLocatorCd().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE_TELE_ORG");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  entityLocatorDT.setCdDescTxt( (String) map.get(tmp.getLocatorCd()));
		                }
		                entityLocatorDT.setClassCd(tmp.getClassCd());
		                entityLocatorDT.setEntityUid(organizationUid);
		                entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
		                //    entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
		                if (tmp.getLocatorUseCd() != null &&
		                    tmp.getLocatorUseCd().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE_TELE_ORG");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  entityLocatorDT.setUseCd( (String) map.get(tmp.getLocatorUseCd()));
		                }
		                entityLocatorDT.setCd(tmp.getLocatorCd());

		                TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
		                teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
		                teleLocatorDT.setExtensionTxt(tmp.getExtensionTxt());
		                entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
		                locatorList.add(entityLocatorDT);
		                logger.debug("Added tele locator: ");
		              }
		            }
		          } //while (teleItr.hasNext())
		          srchResultVO.setOrganizationLocatorsColl(locatorList);
		          // For ID
		          ArrayList<Object> entityIdList = new ArrayList<> ();
		         Iterator<Object>  idItr = organizationEntityIdColl.iterator();
		          while (idItr.hasNext()) {
		            EntityIdDT entityIdDT = new EntityIdDT();
		            OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp) idItr.
		                next();
		            if (tmp.getOrganizationUid().equals(organizationUid)) {
		              if (! (tmp.getEiTypeDesc() == null)) {
		                //logger.debug("inside typeCd, what is it? "+ typeCd);
		                srchResultVO.setOrganizationUID(organizationUid);
		                entityIdDT.setEntityUid(organizationUid);
		                srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());

		                if (tmp.getEiTypeDesc() != null &&
		                    tmp.getEiTypeDesc().trim().length() != 0) {
		                  TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EI_TYPE_ORG");
		                  map = cache.reverseMap(map); // we can add another method that do not do reverse
		                  if (map.get(tmp.getEiTypeDesc()) != null)
		                    entityIdDT.setTypeCd( (String) map.get(tmp.getEiTypeDesc()));
		                  else // if SRT does not have code get it from Desc.
		                    entityIdDT.setTypeCd(tmp.getEiTypeDescTxt());
		                }
		                //   entityIdDT.setTypeCd(tmp.getEiTypeDesc());
		                entityIdDT.setTypeDescTxt(tmp.getEiTypeCd());
		                entityIdDT.setAssigningAuthorityCd(tmp.getEiAssigningAuthorityCd());
		                entityIdDT.setAssigningAuthorityDescTxt(tmp.getEiAssigningAuthorityDescTxt());
		                entityIdDT.setRootExtensionTxt(tmp.getEiRootExtensioTxt());
		                entityIdList.add(entityIdDT);
		              }
		            }
		          } //while (idItr.hasNext())
		          srchResultVO.setOrganizationIdColl(entityIdList);
		          searchResult.add(srchResultVO);
		          totalCount++;
		        } // while (itr.hasNext())
		      }

		          ArrayList<Object> cacheList = new ArrayList<> ();
		          for (int j = 0; j < searchResult.size(); j++) {
		            OrganizationSrchResultVO psvo = new OrganizationSrchResultVO();
		            psvo = (OrganizationSrchResultVO) searchResult.get(j);
		            if ((fromIndex > searchResult.size()) || (cacheNumber == listCount))
		              break;
		            cacheList.add(searchResult.get(j));
		            listCount++;
		            logger.debug("List Counts = " + listCount);
		          }
		  ArrayList<Object> displayList = new ArrayList<> ();
		  displayOrganizationList = new DisplayOrganizationList(totalCount, cacheList, fromIndex, listCount);
		  logger.debug("Return a display list: totalCount = " + totalCount + ", Start index = " +
		                 fromIndex + ", List<Object> Count = " + listCount +"Size from list is: " + cacheList.size());
		  displayList.add(displayOrganizationList);
		  return displayList;
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
    }

    /* Depending on user input build where clause for organization and name query */
     private String buildNameWhereClause(OrganizationSearchVO find)
     {
        StringBuffer sbuf = new StringBuffer();
        String oper = null;
        String whereAnd = " where " ;
        boolean firstWhere = false;
        try{
	        if (find.getNmTxt()!= null)
	        {
	                if (find.getNmTxt().trim().length() !=0)
	           {
	               oper = find.getNmTxtOperator() .trim();
	               if (oper.length() !=0)
	                   if (firstWhere)
	                     firstWhere = false;
	                  else
	                     whereAnd = " AND ";
	                  String organizationName = find.getNmTxt().trim();
	                  String specialCharacter ;
	                  logger.debug("\n\n\ntoBeEscaped :" + organizationName);
	                  if(organizationName.indexOf("'")>0)
	                  {
	                      logger.debug("inside if and index of \"'\" is :" + organizationName.indexOf("'"));
	                      specialCharacter = "'";
	                      organizationName = replaceCharacters(organizationName,specialCharacter, "''" );
	                      logger.debug("query is :"+ organizationName);

	                  }
	                  if (oper.equalsIgnoreCase("SL")) // sounds like
	                    sbuf.append(whereAnd + " (soundex(upper( ONM.nm_txt))  =  soundex('"  + organizationName.toUpperCase() + "'))");
	                  else if (oper.equalsIgnoreCase("CT"))
	                  { // contains
	                    sbuf.append(whereAnd + "( upper( ONM.nm_txt)  like '%" +  organizationName.toUpperCase() + "%')"  );
	                    //sbuf.append(" and PN.nm_use_cd = '0')");
	                  }
	                  else if (oper.equalsIgnoreCase("SW"))
	                  { // contains
	                    sbuf.append(whereAnd + "( upper( ONM.nm_txt)  like '" +  organizationName.toUpperCase() + "%')"  );
	                    //sbuf.append(" and PN.nm_use_cd = '0')");
	                  }
	                  else
	                  {
	                    sbuf.append(whereAnd + " (upper( ONM.nm_txt) " +  oper + "   '"  + organizationName.toUpperCase()+"')"  );
	                    //sbuf.append(" and PN.nm_use_cd = '0')");
	                  }
	            }
	        }
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
        return sbuf.toString();
      }
      /* Depending on user input build where clause for address query */
      private String buildAddressWhereClause(OrganizationSearchVO find)
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
	            if (find.getStateCd() != null)
	            {
	              if (find.getStateCd().trim().length() != 0)
	              {
	                   if (firstWhere)
	                        firstWhere = false;
	                   else
	                      whereAnd = " AND ";
	                   sbuf.append(whereAnd + " upper(PL.state_cd) =  '" + find.getStateCd().trim().toUpperCase() +"'" ) ;

	              }
	            }
	            if (find.getZipCd() != null)
	            {
	              if (find.getZipCd().trim().length() != 0)
	              {
	                  if (firstWhere)
	                      firstWhere = false;
	                   else
	                      whereAnd = " AND ";
	                 sbuf.append(whereAnd + " upper(PL.zip_cd) = '" + find.getZipCd().trim().toUpperCase() +"'" ) ;
	              }
	            }
          }catch(Exception ex){
        	  logger.fatal("Exception  = "+ex.getMessage(), ex);
        	  throw new NEDSSSystemException(ex.toString(), ex);
          }
          return sbuf.toString();
        }

        /* Depending on user input build where clause for tele query */
          private String buildTeleWhereClause(OrganizationSearchVO find)
          {
            StringBuffer sbuf = new StringBuffer();
            String oper = " = ";
            String whereAnd = " where " ;
            boolean firstWhere = false;
            try{
            	if (find.getPhoneNbrTxt() != null)
	            {
	              if (find.getPhoneNbrTxt().trim().length() != 0)
	              {
	               // oper = find.getPhoneNbrTxtOperator().trim();
	                if (oper.length() !=0)
	                  if (firstWhere)
	                      firstWhere = false;
	                   else
	                      whereAnd = " AND ";
	                  String phoneNumber = find.getPhoneNbrTxt().trim();
	                  String formattedPhone =   formatPhoneNumber(phoneNumber);
	                  if(formattedPhone.indexOf("-") == 3 && formattedPhone.lastIndexOf("-") ==  4 && formattedPhone.length() == 9){
	                    logger.debug("formattedPhone.substring()= " +formattedPhone.substring(0, 4)+" "+formattedPhone.substring(5, 9));
	                    sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '" +
	                                formattedPhone.substring(0, 4).toUpperCase() + "%'" +
	                                " and upper( TL.phone_nbr_txt)  like '%" +
	                                formattedPhone.substring(5, 9).toUpperCase() + "%'");
	                  }
	                  else if(formattedPhone.indexOf("-") == 3 && formattedPhone.lastIndexOf("-") ==  3 && formattedPhone.length() == 4){
	                      sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '" +  formattedPhone.trim().toUpperCase() + "%'"  );
	                  }
	                  else
	                      sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '%" +  formattedPhone.trim().toUpperCase() + "%'"  );
	              }
	            }
            }catch(Exception ex){
          	  logger.fatal("Exception  = "+ex.getMessage(), ex);
          	  throw new NEDSSSystemException(ex.toString(), ex);
            }
           return sbuf.toString();
          }
        /* Depending on user input build where clause for entity id query */
          private String buildEntityIdWhereClause(OrganizationSearchVO find)
          {
            StringBuffer sbuf = new StringBuffer();
            try{
	            if (find.getRootExtensionTxt() != null && find.getTypeCd() != null) {
	              if (find.getTypeCd().trim().length() != 0) {
	                if (find.getRootExtensionTxt().trim().length() != 0) {
	                  // There is no operator for ID and value; take default
	                  String oper = "=";
	                  String specialCharacter;
	                  String rootExtension = find.getRootExtensionTxt().trim();
	                  //   logger.debug("rootExtension is :" + oper+"test");
	                  //   logger.debug("\n\n\ntoBeEscaped :" + oper);
	                  if (rootExtension.indexOf("'") > 0) {
	                    logger.debug("inside if and index of \"'\" is :" +
	                                 rootExtension.indexOf("'"));
	                    specialCharacter = "'";
	                    rootExtension = replaceCharacters(rootExtension, specialCharacter,
	                                                      "''");
	                    logger.debug("oper query is :" + rootExtension);
	                  }
	                  if (oper.length() != 0)
	                    sbuf.append("AND (EI.TYPE_CD = '" + find.getTypeCd().trim() + "')");
	                  sbuf.append("AND  upper( EI.root_extension_txt) = '" +
	                              rootExtension.toUpperCase() + "'  ");

	                } // end of 2nd if
	              }
	            }
            }catch(Exception ex){
          	  logger.error("Exception  = "+ex.getMessage(), ex);
          	  throw new NEDSSSystemException(ex.toString(), ex);
            }
            return sbuf.toString();
          }

	private String constructUidWhereInClause(String fieldName, ArrayList<Object> uidsStringArray)
	{
		//EXAMPLE: "p.person_uid in (uid1, uid2, ...., uid1000) or p.person_uid(uid1001, uid1002)"
		//there should be exceptions thrown when fieldName is empty or null and when uidsStringArray is empty or null
		StringBuffer strbResult = new StringBuffer();
		try{
			if(uidsStringArray != null && !uidsStringArray.isEmpty())
			{
				int size = uidsStringArray.size();
				boolean first = true;

				for (int i = 0; i<size; i++)
				{
					if(first)
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
		}catch(Exception ex){
      	  logger.error("fieldName: "+fieldName+" Exception  = "+ex.getMessage(), ex);
        }
		return strbResult.toString();
	}


	private ArrayList<Object> createUidsCsvStringArray(ArrayList<Object>  personParentUidList)
	{
		 ArrayList<Object> result = null;
   		 try{
			 if (personParentUidList != null && !personParentUidList.isEmpty())
			 {
				 result = new ArrayList<> ();
				 int maxNum = 1000;
				 Long curUid = null;
				 int size = personParentUidList.size();
				 int numOfIterations = (size/maxNum) + 1;
				 int maxForThisIteration = size;

				 for(int k = 0; k < numOfIterations; k++)
				 {
					 ArrayList<Object> uidCollector = new ArrayList<> ();
					 int counterStartingPoint = k * maxNum;

					 if((k+1)*maxNum > size)
					 {
						maxForThisIteration = size;
					 }
					 else
					 {
						maxForThisIteration = (k+1)*maxNum;
					 }

					 for (int counter = counterStartingPoint; counter < maxForThisIteration; counter++)
					 {
						 curUid = (Long)personParentUidList.get(counter);
						 uidCollector.add(curUid);
					 }

					 result.add(createCsvString(uidCollector));
				 }
			 }
   		 }catch(Exception ex){
   			 logger.error("Exception  = "+ex.getMessage(), ex);
   			 throw new NEDSSSystemException(ex.toString(), ex);
   		 }
		 return result;
	}

	private String createCsvString(ArrayList<Object>  uids)
	{
		StringBuffer strbResult = new StringBuffer("");
   		try{
			 if(uids != null && !uids.isEmpty())
			 {
				 boolean firstId = true;

				 if(uids != null && !uids.isEmpty())
				 {
					Iterator<Object>  it = uids.iterator();

					 if(it != null)
					 {
						 Long cur = null;

						 while(it.hasNext())
						 {
							 cur = (Long)it.next();

							 if(firstId)
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
   		}catch(Exception ex){
      	  logger.error("Exception  = "+ex.getMessage(), ex);
      	  throw new NEDSSSystemException(ex.toString(), ex);
        }

   		return strbResult.toString();
	}


          /* Generate where clause based on common organizationUIDs in the list */
          private String generateWhereClause(ArrayList<Object>  list)
          {
          	String result = null;
          	try{
	            if (list != null && list.size() > 0)
	            {
	            	ArrayList<Object> csvChunks = createUidsCsvStringArray(list);
		            StringBuffer sbuf = new StringBuffer();
		            sbuf.append(" and (");
		            sbuf.append(constructUidWhereInClause("o.organization_uid", csvChunks));
		            sbuf.append(") ");
		            result = sbuf.toString();
	            }
          	}catch(Exception ex){
          	  logger.error("Exception  = "+ex.getMessage(), ex);
          	  throw new NEDSSSystemException(ex.toString(), ex);
            }
            return result;
          }

          /* find common organizationUids from all collections */
          private ArrayList<Object> filterIDs ( ArrayList<Object> organizationNameColl,
                                        ArrayList<Object> organizationAddressColl,
                                        ArrayList<Object> organitionTeleColl,
                                        ArrayList<Object> organizationEntityIdColl)
          {
              ArrayList<Object> list = new ArrayList<> ();
              list = (ArrayList<Object> )getIDs(organizationNameColl,organizationAddressColl);
              list = (ArrayList<Object> )getIDs(organitionTeleColl, list);
              list = (ArrayList<Object> )getIDs(organizationEntityIdColl, list);
              ArrayList<Object> idList = new ArrayList<> ();
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
	                  OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp) itr.next();
	                  idList.add(tmp.getOrganizationUid());
	                }
	              }
              }catch(Exception ex){
            	  logger.error("Exception  = "+ex.getMessage(), ex);
            	  throw new NEDSSSystemException(ex.toString(), ex);
              }
              return idList;
          }
          /* find common organizationUids from two lists. */
          private List<Object> getIDs(ArrayList<Object>  list1, ArrayList<Object> list2)
          {
         //   int cacheCount =0;
            if (list1 == null && list2 == null)
              return null;
            ArrayList<Object> list = new ArrayList<> ();
            try{
	            if (list2 == null)
	            {
	                HashMap<Object,Object> map = new HashMap<>();
	               Iterator<Object>  itr = list1.iterator();
	                while (itr.hasNext())
	                {
	                  OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)itr.next();
	                  map.put(tmp.getOrganizationUid(), tmp);
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
	                HashMap<Object,Object> map = new HashMap<>();
	               Iterator<Object>  itr = list2.iterator();
	                while (itr.hasNext())
	                {
	                  OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)itr.next();
	                  map.put(tmp.getOrganizationUid(), tmp);
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
	                HashMap<Object,Object> map1 = new HashMap<>();
	                HashMap<Object,Object> map2 = new HashMap<>();
	                int count =0;
	               Iterator<Object>  itr = list1.iterator();
	                while (itr.hasNext())
	                {
	                  OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)itr.next();
	                  map1.put(tmp.getOrganizationUid(), tmp);
	                }
	              // load hashmap for second list
	                itr = list2.iterator();
	                while (itr.hasNext())
	                {
	                  OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)itr.next();
	                  map2.put(tmp.getOrganizationUid(), tmp);
	                }
	                // compare and create list of common
	                Set<Object> set = map1.keySet();
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
          	  logger.error("Exception  = "+ex.getMessage(), ex);
          	  throw new NEDSSSystemException(ex.toString(), ex);
            }
              return list;
          }


          /* get name query and run it */
          @SuppressWarnings("unchecked")
		private ArrayList<Object> runNameQuery(String whereClause)
          {
        	 ArrayList<Object> list = new ArrayList<>();
             StringBuffer  query = new StringBuffer();
             try{
	             OrganizationSearchResultTmp searchResultName = new OrganizationSearchResultTmp();
	             logger.debug("Name where clause is: " + whereClause);
	             query.append(BASENAMEQUERYSQL);
	             query.append(whereClause + " order by onm.nm_txt ");

	             logger.info(query.toString());
	             list = (ArrayList<Object> )preparedStmtMethod(searchResultName, null,
	                                query.toString() , NEDSSConstants.SELECT);
             }catch(Exception ex){
            	 logger.error("whereClause: "+whereClause+" Exception  = "+ex.getMessage(), ex);
             }
             return list;
          }





          /* get address query and run it */

          @SuppressWarnings("unchecked")
		private ArrayList<Object> runAddressQuery(String whereClause)
          {
        	 ArrayList<Object> list = new ArrayList<>();
             StringBuffer  query = new StringBuffer();
             try{
	             OrganizationSearchResultTmp searchResultAddress = new OrganizationSearchResultTmp();
	             logger.debug("Address where clause is: " + whereClause);
	            query.append(BASEADDRESSQUERYSQL);
	             query.append(whereClause);
	             logger.info(query.toString());
	             list = (ArrayList<Object> )preparedStmtMethod(searchResultAddress, null,
	                                query.toString() , NEDSSConstants.SELECT);
             }catch(Exception ex){
            	 logger.error("Exception  = "+ex.getMessage(), ex);
            	 throw new NEDSSSystemException(ex.toString(), ex);
             }
             return list;
          }
          /* get telephone query and run it */

          @SuppressWarnings("unchecked")
		private ArrayList<Object> runTeleQuery(String whereClause)
          {
        	 ArrayList<Object> list = new ArrayList<>();
             StringBuffer  query = new StringBuffer();
             try{
	             OrganizationSearchResultTmp searchResultTele = new OrganizationSearchResultTmp();
	             logger.debug("Tele where clause is: " + whereClause);
	             query.append(BASETELEQUERYSQL);
	             query.append(whereClause);
	             list = (ArrayList<Object> )preparedStmtMethod(searchResultTele, null,
	                                 query.toString(), NEDSSConstants.SELECT);
             }catch(Exception ex){
            	 logger.error("whereClause: "+whereClause+" Exception  = "+ex.getMessage(), ex);
             }
             return list;
          }
          /* get Entity ID query and run it */

          @SuppressWarnings("unchecked")
		private ArrayList<Object> runEntityIdQuery(String whereClause)
          {
        	 ArrayList<Object> list = new ArrayList<>();
             StringBuffer  query = new StringBuffer();
             try{
	             OrganizationSearchResultTmp searchResultEntityId = new OrganizationSearchResultTmp();
	             logger.debug("EntityID where clause is: " + whereClause);
	             query.append(BASEIDQUERYSQL);
	             query.append(whereClause);
	             list = (ArrayList<Object> )preparedStmtMethod(searchResultEntityId, null,
	                                query.toString(), NEDSSConstants.SELECT);
             }catch(Exception ex){
            	 logger.error("whereClause: "+whereClause+" Exception  = "+ex.getMessage(), ex);
             }
             return list;
          }


  /**
   * Generates the search criteria with the like statements --Not using this method-
   * @param columnName   teh String
   * @param find    the OrganizationSearchVO
   * @return  String
   */
  private String generatePrivQuery(String columnName, OrganizationSearchVO find) {
	  String accPrivQuery = "";
	  String tokens = "";
	  String accPriv = "";
	  try{
		  if (columnName.equals(NEDSSConstants.ORG_PRIV)) {
		     accPriv = find.getOrgAccessPriv();
		     tokens = "@#$";
	      } else if (columnName.equals(NEDSSConstants.PROG_AREA_PRIV))   {
		       accPriv = find.getProgAreaAccessPriv();
		       tokens = "!";
	      }

		  StringTokenizer sT = new StringTokenizer(accPriv,tokens);
		  accPrivQuery += " AND (" + columnName + " Like '%"  + sT.nextToken().toString() + "%'";
		  while (sT.hasMoreTokens()) {
			accPrivQuery +=  "OR " + columnName + " Like '%"  + sT.nextToken().toString() + "%'";
	      }
	  }catch(Exception ex){
		  logger.error("columnName: "+columnName+" Exception  = "+ex.getMessage(), ex);
	  }
      return  accPrivQuery += ")";
  }

    /**
     * To replace a special character in the String
     * @param toBeRepaced    the String in which the special charecters to be replaced
     * @param specialCharacter   the String to be replaced
     * @param replacement      the String to be replaced with
     * @return   the String    the String after the replacement fo the special charecter
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
    	  logger.error("toBeRepaced: "+toBeRepaced+" specialCharacter:"+specialCharacter+" replacement:"+replacement);
    	  logger.error("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString(), ex);
      }
      return result.toString();
    }

    private String formatPhoneNumber(String phoneNumber){
	    String formattedPhoneNumber = "";
	    try{
		    if(phoneNumber.indexOf("-") == 3 && phoneNumber.lastIndexOf("-") ==  4 && phoneNumber.length() == 5)
		    {
		      formattedPhoneNumber = phoneNumber.substring(0, 4);
		      logger.debug("\n The  formattedPhoneNumber =  "+formattedPhoneNumber);
		    }
		    else if (phoneNumber.indexOf("-") == 0 && phoneNumber.lastIndexOf("-") == 1)
		    {
		      formattedPhoneNumber = phoneNumber.substring(1, 6);
		      logger.debug("\n The  formattedPhoneNumber =  "+formattedPhoneNumber);
		    }
		    else
		    	formattedPhoneNumber = phoneNumber ;
	    }catch(Exception ex){
	    	logger.error("phoneNumber: "+phoneNumber+" Exception  = "+ex.getMessage(), ex);
	    }
	    return formattedPhoneNumber;

    }

 public static void main(String [] args){
    try{
		FindOrganizationDAOImpl search = new FindOrganizationDAOImpl();
		OrganizationSearchVO searchVO = new OrganizationSearchVO();

		StringBuffer testQuery = new StringBuffer(" ");
		testQuery.append(" ORDER BY 1");
		logger.debug("Complete Query is :  " + testQuery.toString());
	    }
    catch(Exception e)
    {
    	logger.error("Exception  = "+e.getMessage(), e);
    	throw new NEDSSSystemException(e.toString(), e);
    }
  }

 public ArrayList<Object> findOrganizations() throws NEDSSSystemException
 {
   ArrayList<Object> organizationNameColl = null;
   ArrayList<Object> organizationAddressColl = null;
   ArrayList<Object> organizationTeleColl = null;
   ArrayList<Object> organizationEntityIdColl = null;
   String whereClause = null;
   DisplayOrganizationList displayOrganizationList = null;

   int totalCount = 0;
   int listCount = 0;
   /* Has user entered any organization and name field for search? */
   try{
	     organizationNameColl = runNameQuery(null);
	     if (organizationNameColl != null)
	       logger.debug("Size of name collection is: " + organizationNameColl.size());


	   /* Has user entered any address information */

	     organizationAddressColl = runAddressQuery(null);
	     if (organizationAddressColl != null)
	       logger.debug("Size of address collection is: " +
	                    organizationAddressColl.size());

	   /* No whereclause for the method */

	     organizationTeleColl = runTeleQuery(null);
	     if (organizationTeleColl != null)
	       logger.debug("Size of phone collection is: " +
	                    organizationTeleColl.size());


	   /* Has user entered any EntityId information */


	     organizationEntityIdColl = runEntityIdQuery(null);
	     if (organizationEntityIdColl != null)
	       logger.debug("Size of entity ID collection is: " +
	                    organizationEntityIdColl.size());

	   /* Find common organizationUIDs from all the queries */
	   ArrayList<Object> idList = filterIDs(organizationNameColl, organizationAddressColl,
	                                organizationTeleColl, organizationEntityIdColl);
	   // logger.debug("Size of idList is: " + idList.size());
	   logger.debug("Ids are: " + idList);

	   /* generate where clause with those common organizationUIDs */
	   whereClause = generateWhereClause(idList);
	   ArrayList<Object> searchResult = new ArrayList<> ();
	   /* If there is any Common UID */
	   if (whereClause != null) {
	     organizationNameColl = runNameQuery(whereClause);
	     organizationAddressColl = runAddressQuery(whereClause);
	     organizationTeleColl = runTeleQuery(whereClause);
	     organizationEntityIdColl = runEntityIdQuery(whereClause);
	     //}
	     /* Iterate through NameCollection  as sorting is to be done by name) and
	       find out attribute for each collection
	       put them as organizationsearchresultVo and put it in ArrayList<Object> */
	     //Iterator itr = idList.iterator();
	    Iterator<Object>  itr = organizationNameColl.iterator();
	     while (itr.hasNext()) {
	       OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
	       ArrayList<Object> nameList = new ArrayList<> ();
	       OrganizationSearchResultTmp tmp1 = (OrganizationSearchResultTmp) itr.
	           next();
	       Long organizationUid = tmp1.getOrganizationUid();
	       // Long organizationUid = (Long)itr.next();
	      Iterator<Object>  nameItr = organizationNameColl.iterator();
	       CachedDropDownValues cache = new CachedDropDownValues();
	       while (nameItr.hasNext()) {
	         OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
	             nameItr.next();
	         if (tmp.getOrganizationUid().equals(organizationUid)) {
	           if (! (tmp.getNameUseCd() == null)) {
	             OrganizationNameDT nameDT = new OrganizationNameDT();
	             nameDT.setOrganizationUid(organizationUid);
	             nameDT.setNmTxt(tmp.getName());
	             if (tmp.getNameUseCd() != null &&
	                 tmp.getNameUseCd().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("O_NM_USE");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               nameDT.setNmUseCd( (String) map.get(tmp.getNameUseCd()));
	             }
	             // nameDT.setNmUseCd(tmp.getNameDesc());
	             nameList.add(nameDT);
	             srchResultVO.setOrganizationUID(organizationUid);
	             srchResultVO.setOrganizationId(tmp.getLocalId());
	             srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
	           } //if ( ! (tmp.getNameUseCd() == null))
	         } //if (tmp.getOrganizationUid() == organizationUid)
	       } // while (nameItr.hasNext())
	       srchResultVO.setOrganizationNameColl(nameList);
	       //logger.debug("Number of names added : " + nameList.size());
	       ArrayList<Object> locatorList = new ArrayList<> ();
	       // for address
	       if(organizationAddressColl!=null)
	       {
	      Iterator<Object>  addressItr = organizationAddressColl.iterator();
	       while (addressItr.hasNext()) {
	         EntityLocatorParticipationDT entityLocatorDT = new
	             EntityLocatorParticipationDT();
	         OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
	             addressItr.next();
	         if (tmp.getOrganizationUid().equals(organizationUid)) {
	           if (tmp.getClassCd() != null) {
	             srchResultVO.setOrganizationUID(organizationUid);
	             srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
	             entityLocatorDT.setCd(tmp.getLocatorCd());
	             //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
	             if (tmp.getLocatorCd() != null &&
	                 tmp.getLocatorCd().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE_PST_ORG");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               entityLocatorDT.setCdDescTxt( (String) map.get(tmp.getLocatorCd()));
	             }
	             entityLocatorDT.setClassCd(tmp.getClassCd());
	             entityLocatorDT.setEntityUid(organizationUid);
	             entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
	             // entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
	             if (tmp.getLocatorUseCd() != null &&
	                 tmp.getLocatorUseCd().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE_PST_ORG");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               entityLocatorDT.setUseCd( (String) map.get(tmp.getLocatorUseCd()));
	             }

	             entityLocatorDT.setCd(tmp.getLocatorCd());
	             PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
	             postalLocatorDT.setStateCd(tmp.getState());
	             postalLocatorDT.setCityCd(tmp.getCity());
	             postalLocatorDT.setCityDescTxt(tmp.getCity());
	             postalLocatorDT.setStreetAddr1(tmp.getStreetAddr1());
	             postalLocatorDT.setStreetAddr2(tmp.getStreetAddr2());
	             postalLocatorDT.setPostalLocatorUid(tmp.getLocatorUid());
	             postalLocatorDT.setZipCd(tmp.getZip());
	             postalLocatorDT.setCntyCd(tmp.getCntyCd());
	             entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
	             locatorList.add(entityLocatorDT);
	             logger.debug("Added address locator: ");
	           }
	         }
	       } //while (addressItr.hasNext())
	       //  logger.debug("Number of address added : " + locatorList.size());
	      }

	       // tele locator
	      Iterator<Object>  teleItr = organizationTeleColl.iterator();
	       while (teleItr.hasNext()) {
	         EntityLocatorParticipationDT entityLocatorDT = new
	             EntityLocatorParticipationDT();
	         OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp)
	             teleItr.next();
	         if (tmp.getOrganizationUid().equals(organizationUid)) {
	           if (tmp.getClassCd() != null) {
	             srchResultVO.setOrganizationUID(organizationUid);
	             srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
	             entityLocatorDT.setCd(tmp.getLocatorCd());
	             //   entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
	             if (tmp.getLocatorCd() != null &&
	                 tmp.getLocatorCd().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_TYPE_TELE_ORG");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               entityLocatorDT.setCdDescTxt( (String) map.get(tmp.getLocatorCd()));
	             }
	             entityLocatorDT.setClassCd(tmp.getClassCd());
	             entityLocatorDT.setEntityUid(organizationUid);
	             entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
	             //    entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
	             if (tmp.getLocatorUseCd() != null &&
	                 tmp.getLocatorUseCd().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EL_USE_TELE_ORG");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               entityLocatorDT.setUseCd( (String) map.get(tmp.getLocatorUseCd()));
	             }
	             entityLocatorDT.setCd(tmp.getLocatorCd());

	             TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
	             teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
	             teleLocatorDT.setExtensionTxt(tmp.getExtensionTxt());
	             entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	             locatorList.add(entityLocatorDT);
	             logger.debug("Added tele locator: ");
	           }
	         }
	       } //while (teleItr.hasNext())
	       srchResultVO.setOrganizationLocatorsColl(locatorList);
	       // For ID
	       ArrayList<Object> entityIdList = new ArrayList<> ();
	      Iterator<Object>  idItr = organizationEntityIdColl.iterator();
	       while (idItr.hasNext()) {
	         EntityIdDT entityIdDT = new EntityIdDT();
	         OrganizationSearchResultTmp tmp = (OrganizationSearchResultTmp) idItr.
	             next();
	         if (tmp.getOrganizationUid().equals(organizationUid)) {
	           if (! (tmp.getEiTypeDesc() == null)) {
	             //logger.debug("inside typeCd, what is it? "+ typeCd);
	             srchResultVO.setOrganizationUID(organizationUid);
	             entityIdDT.setEntityUid(organizationUid);
	             srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());

	             if (tmp.getEiTypeDesc() != null &&
	                 tmp.getEiTypeDesc().trim().length() != 0) {
	               TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EI_TYPE_ORG");
	               map = cache.reverseMap(map); // we can add another method that do not do reverse
	               if (map.get(tmp.getEiTypeDesc()) != null)
	                 entityIdDT.setTypeCd( (String) map.get(tmp.getEiTypeDesc()));
	               else // if SRT does not have code get it from Desc.
	                 entityIdDT.setTypeCd(tmp.getEiTypeDescTxt());
	             }
	             //   entityIdDT.setTypeCd(tmp.getEiTypeDesc());
	             entityIdDT.setRootExtensionTxt(tmp.getEiRootExtensioTxt());
	             entityIdList.add(entityIdDT);
	           }
	         }
	       } //while (idItr.hasNext())
	       srchResultVO.setOrganizationIdColl(entityIdList);
	       searchResult.add(srchResultVO);
	       totalCount++;
	     } // while (itr.hasNext())
	   }

	       ArrayList<Object> cacheList = new ArrayList<> ();
	       for (int j = 0; j < searchResult.size(); j++) {
	         OrganizationSrchResultVO orgVO = new OrganizationSrchResultVO();
	         orgVO = (OrganizationSrchResultVO) searchResult.get(j);
	         cacheList.add(searchResult.get(j));
	         listCount++;
	         logger.debug("List Counts = " + listCount);
	       }
		logger.debug("Return a display list: totalCount = " + totalCount + ", Start index = " +
		               ", List<Object> Count = " + listCount +"Size from list is: " + cacheList.size());

		return cacheList;
   }catch(Exception ex){
	   logger.fatal("Exception  = "+ex.getMessage(), ex);
	   throw new NEDSSSystemException(ex.toString());
   }
 }

 public ArrayList<Object> getOrganizationUids() throws NEDSSSystemException{
	 OrganizationDT orgDT = new OrganizationDT();
	 String query;
	 PropertyUtil propUtil = PropertyUtil.getInstance();

     //// Added for Electronic indicator flag  reading from NEDSS.Preperties file-Sathya


		 query = "SELECT o.organization_uid \"organizationUid\" "+
		             "from organization o  "+
					 "WHERE "+
					 "(o.Edx_ind<>'" +NEDSSConstants.EDX_IND+ "' OR o.edx_ind is null)  and "+
					 "o.electronic_ind='"+NEDSSConstants.ELECTRONIC_IND_ENTITY_MATCH+"'";
		 try{
	   return (ArrayList<Object> )preparedStmtMethod(orgDT, null,
			   query , NEDSSConstants.SELECT);
	 }catch(Exception e){
		 logger.error("Error in getting the organization UID "+e.getMessage(), e);
		 throw new NEDSSSystemException(e.getMessage());
	 }

 }

}
