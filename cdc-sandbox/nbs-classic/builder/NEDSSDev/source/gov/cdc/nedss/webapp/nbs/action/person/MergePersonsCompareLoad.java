package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        MergePersonsCompareLoad
 * Description:  This class interacts with the user and retrieves and submits to the back end to Merge Persons.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Karthik Murthy
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;

import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.association.dt.RoleDT;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergePersonsCompareLoad extends Action
{

	//For logging
	static final LogUtils logger = new LogUtils(MergePersonsCompareLoad.class.getName());

	public MergePersonsCompareLoad()
	{
	}

	   /**
      * Handles the loading of the page for comparing the two records for merging.
      * @J2EE_METHOD  --  perform
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException
	{

	  	logger.debug("inside the MergePersonsCompareLoad Load");

		HttpSession session = request.getSession(false);
		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
						"NBSSecurityObject");

		String contextAction = request.getParameter("ContextAction");

		if (contextAction == null) {
			contextAction = (String)request.getAttribute("ContextAction");
		}

	    //context
	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS044",
						   contextAction);

	    String sCurrTask = NBSContext.getCurrentTask(session);
	    request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do"
	    	+ "?ContextAction=" + tm.get("Merge"));
	    //request.setAttribute("ContextAction", tm.get("Merge"));
	    request.setAttribute("cancelButtonHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Cancel"));


		//##!! System.out.println("URL with Context action inside MergePersonsCompareLoad is : " + request.getAttribute("formHref"));

	    boolean bStatusSubmitButton = secObj.getPermission(NBSBOLookup.PATIENT,
							   NBSOperationLookup.MERGE);
	    request.setAttribute("sec-status", String.valueOf(bStatusSubmitButton));


		// Fill the request object with the compare data for 2 persons
		fillRequest(request, session, secObj);

		return mapping.findForward("XSP");
	}


	   /**
      * Handles transfering of data from the two personVOs to the request object to populate the
      * compare page.
      * @J2EE_METHOD  --  fillRequest
      * @return request    the  HttpServletRequest
      * @return session    the  HttpSession
      * @return secObj    the  NBSSecurityObj
      **/
	private void fillRequest(HttpServletRequest request, HttpSession session,
			NBSSecurityObj secObj )
	{

		// Retrieve the 2 Person VOs from the object store
		PersonVO mergePerVO1 = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_VO1);
		PersonVO mergePerVO2 = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_VO2);

		//---- ENTITY  ----
		/*String entity1 = "PSN605254644PSN";
		String entity2 = "PSN605273819PSN";*/

		String entity1 = mergePerVO1.getThePersonDT().getLocalId();
		String entity2 = mergePerVO2.getThePersonDT().getLocalId();
		request.setAttribute("entity1",  entity1);
		request.setAttribute("entity2",  entity2);

		//---- NAME TYPE AND NAME ----
		/*ArrayList<Object> nameTypes_Per1 = new ArrayList<Object> ();
		nameTypes_Per1.add(new TypeValue("Legal", "John Arthur William Baxter Middleton"));
		nameTypes_Per1.add(new TypeValue("Coded Pseudo", "Mary Emmaculate Sarah Jessica Parker"));
		nameTypes_Per1.add(new TypeValue("Name of partner/spouse", "Amy Clarence Lizzy Jessica Julie"));*/
		ArrayList<Object> nameTypes_Per1 = getPersonNameTypes(mergePerVO1);


		/*ArrayList<Object> nameTypes_Per2 = new ArrayList<Object> ();
		nameTypes_Per2.add(new TypeValue("Legal", "John William Arthur Baxter Middleton"));
		nameTypes_Per2.add(new TypeValue("Coded Pseudo", "Mary Sarah Emmaculate Jessica Parker"));
		nameTypes_Per2.add(new TypeValue("Name of partner/spouse", "Amy Lizzy Clarence Jessica Julie"));*/
		ArrayList<Object> nameTypes_Per2 = getPersonNameTypes(mergePerVO2);

		request.setAttribute("nameTypes_Per1",  nameTypes_Per1);
		request.setAttribute("nameTypes_Per2",  nameTypes_Per2);

		//---- CURRENT SEX ----
		/*String currentSex_Per1 = "Male";
		String currentSex_Per2 = "Male";*/

		String currentSex_Per1 = mergePerVO1.getThePersonDT().getCurrSexCd();
		String currentSex_Per2 = mergePerVO2.getThePersonDT().getCurrSexCd();
		request.setAttribute("currentSex_Per1",  currentSex_Per1);
		request.setAttribute("currentSex_Per2",  currentSex_Per2);

		//---- BIRTH SEX ----
		/*String birthSex_Per1 = "Male";
		String birthSex_Per2 = "Male";*/

		String birthSex_Per1 = mergePerVO1.getThePersonDT().getBirthGenderCd();
		String birthSex_Per2 = mergePerVO2.getThePersonDT().getBirthGenderCd();
		request.setAttribute("birthSex_Per1",  birthSex_Per1);
		request.setAttribute("birthSex_Per2",  birthSex_Per2);

		//---- DOB  ----
		/*String DOB_Per1 = "12/12/1980";
		String DOB_Per2 = "12/11/1980";*/

		String DOB_Per1 = StringUtils.formatDate(mergePerVO1.getThePersonDT().getBirthTime());
		String DOB_Per2 = StringUtils.formatDate(mergePerVO2.getThePersonDT().getBirthTime());
		request.setAttribute("DOB_Per1", DOB_Per1 );
		request.setAttribute("DOB_Per2", DOB_Per2 );

		//---- IS PERSON DECEASED ----
		/*String isPersonDeceased_Per1 = "Yes";
		String isPersonDeceased_Per2 = "Yes";*/

		String isPersonDeceased_Per1 = mergePerVO1.getThePersonDT().getDeceasedIndCd();
		String isPersonDeceased_Per2 = mergePerVO2.getThePersonDT().getDeceasedIndCd();
		request.setAttribute("isPersonDeceased_Per1", isPersonDeceased_Per1 );
		request.setAttribute("isPersonDeceased_Per2", isPersonDeceased_Per2 );

		//---- DECEASED DATE ----
		/*String deceasedDate_Per1 = "05/25/2002";
		String deceasedDate_Per2 = "05/25/2002";*/

		String deceasedDate_Per1 = StringUtils.formatDate(mergePerVO1.getThePersonDT().getDeceasedTime());
		String deceasedDate_Per2 = StringUtils.formatDate(mergePerVO2.getThePersonDT().getDeceasedTime());
		request.setAttribute("deceasedDate_Per1", deceasedDate_Per1 );
		request.setAttribute("deceasedDate_Per2", deceasedDate_Per2 );


		//---- SSN ----
		/*String SSN_Per1 = "111-11-1111";
		String SSN_Per2 = "111-11-1111";*/

		String SSN_Per1 = mergePerVO1.getThePersonDT().getSSN();
		String SSN_Per2 = mergePerVO2.getThePersonDT().getSSN();
		request.setAttribute("SSN_Per1", SSN_Per1 );
		request.setAttribute("SSN_Per2", SSN_Per2 );


		//---- MOTHER'S MAIDEN NAME ----
		/*String mothersMaidenName_Per1="Mary";
		String mothersMaidenName_Per2="Marianne";*/

		String mothersMaidenName_Per1=mergePerVO1.getThePersonDT().getMothersMaidenNm();
		String mothersMaidenName_Per2=mergePerVO2.getThePersonDT().getMothersMaidenNm();
		request.setAttribute("mothersMaidenName_Per1", mothersMaidenName_Per1 );
		request.setAttribute("mothersMaidenName_Per2", mothersMaidenName_Per2 );

		//---- HIGHEST LEVEL OF EDUCATION RECEIVED ----
		/*String highestLevelEducationReceived_Per1="12th grade, no diploma";
		String highestLevelEducationReceived_Per2="12th grade, no diploma";*/

		String highestLevelEducationReceived_Per1=mergePerVO1.getThePersonDT().getEducationLevelCd();
		String highestLevelEducationReceived_Per2=mergePerVO2.getThePersonDT().getEducationLevelCd();
		request.setAttribute("highestLevelEducationReceived_Per1", highestLevelEducationReceived_Per1 );
		request.setAttribute("highestLevelEducationReceived_Per2", highestLevelEducationReceived_Per2 );


		//---- PRIMARY OCCUPATION ----
		/*String primaryOccupation_Per1="Arts, Entertainment, and Recreation";
		String primaryOccupation_Per2="Arts, Entertainment, and Recreation";*/

		String primaryOccupation_Per1=mergePerVO1.getThePersonDT().getOccupationCd();
		String primaryOccupation_Per2=mergePerVO2.getThePersonDT().getOccupationCd();
		request.setAttribute("primaryOccupation_Per1", primaryOccupation_Per1 );
		request.setAttribute("primaryOccupation_Per2", primaryOccupation_Per2 );

		//---- MARITAL STATUS ----
		/*String maritalStatus_Per1="Married";
		String maritalStatus_Per2="Divorced";*/

		String maritalStatus_Per1=mergePerVO1.getThePersonDT().getMaritalStatusCd();
		String maritalStatus_Per2=mergePerVO2.getThePersonDT().getMaritalStatusCd();
		request.setAttribute("maritalStatus_Per1", maritalStatus_Per1 );
		request.setAttribute("maritalStatus_Per2", maritalStatus_Per2 );

		//---- PRIMARY LANGUAGE ----
		/*String primaryLanguage_Per1="English, Middle (1100-1500)";
		String primaryLanguage_Per2="English";*/

		String primaryLanguage_Per1=mergePerVO1.getThePersonDT().getPrimLangCd();
		String primaryLanguage_Per2=mergePerVO2.getThePersonDT().getPrimLangCd();
		request.setAttribute("primaryLanguage_Per1", primaryLanguage_Per1 );
		request.setAttribute("primaryLanguage_Per2", primaryLanguage_Per2 );

		//---- IDENTIFICATION TYPE AND IDENTIFICATION ----
		/*ArrayList<Object> identificationTypes_Per1 = new ArrayList<Object> ();
		identificationTypes_Per1.add(new TypeValue("Bureau of the Census", "12345"));
		identificationTypes_Per1.add(new TypeValue("Employee Number", "67890"));
		identificationTypes_Per1.add(new TypeValue("Alias social security number", "123-456-789"));*/
		ArrayList<Object> identificationTypes_Per1 = getIdentificationTypes(mergePerVO1);

		/*ArrayList<Object> identificationTypes_Per2 = new ArrayList<Object> ();
		identificationTypes_Per2.add(new TypeValue("Bureau of the Census", "12345"));
		identificationTypes_Per2.add(new TypeValue("CHIP Identification Number", "4578899"));*/
		ArrayList<Object> identificationTypes_Per2 = getIdentificationTypes(mergePerVO2);

		request.setAttribute("identificationTypes_Per1",  identificationTypes_Per1);
		request.setAttribute("identificationTypes_Per2",  identificationTypes_Per2);

		//---- ETHNICITY TYPE ----
		/*String ethnicityType_Per1="Hispanic or Latino:";
		String ethnicityType_Per2="Hispanic or Latino:";*/
		String ethnicityType_Per1=mergePerVO1.getThePersonDT().getEthnicGroupInd();
		String ethnicityType_Per2=mergePerVO2.getThePersonDT().getEthnicGroupInd();

		request.setAttribute("ethnicityType_Per1", ethnicityType_Per1 );
		request.setAttribute("ethnicityType_Per2", ethnicityType_Per2 );

		//---- ETHNICITY VALUES ----
		/*String ethnicityValues_Per1="Spaniard, Central American, South American, Latin American, Puerto Rican";
		String ethnicityValues_Per2="Spaniard, Central American, South American";*/
		String ethnicityValues_Per1 = getEthnicityValues(mergePerVO1);
		String ethnicityValues_Per2 = getEthnicityValues(mergePerVO2);
		request.setAttribute("ethnicityValues_Per1", ethnicityValues_Per1 );
		request.setAttribute("ethnicityValues_Per2", ethnicityValues_Per2 );

		logger.debug("/////----- Merge Persons Compare load's Ethnicity String for person-1 with local id "
		+ mergePerVO1.getThePersonDT().getLocalId() + " is " + ethnicityValues_Per1);
		logger.debug("/////----- Merge Persons Compare load's Ethnicity String for person-2 with local id "
		+ mergePerVO2.getThePersonDT().getLocalId() + " is " + ethnicityValues_Per2);


		//---- RACE TYPE ----
		/*ArrayList<Object> raceTypes_Per1 = new ArrayList<Object> ();
		raceTypes_Per1.add(new TypeValue("Asian", "Bangladeshi, Cambodian, Filipino, Indonesian"));
		raceTypes_Per1.add(new TypeValue("White", "Causasian, English, Polish, Russian"));*/
		ArrayList<Object> raceTypes_Per1 = getRaceTypes(mergePerVO1);

		/*ArrayList<Object> raceTypes_Per2 = new ArrayList<Object> ();
		raceTypes_Per2.add(new TypeValue("Asian", "Filipino, Indonesian"));
		raceTypes_Per2.add(new TypeValue("White", "Polish, Russian"));*/
		ArrayList<Object> raceTypes_Per2 = getRaceTypes(mergePerVO2);

		request.setAttribute("raceTypes_Per1", raceTypes_Per1 );
		request.setAttribute("raceTypes_Per2", raceTypes_Per2 );

		//---- ADDRESS TYPE AND ADDRESS ----
		/*ArrayList<Object> addressTypes_Per1 = new ArrayList<Object> ();
		addressTypes_Per1.add(new TypeValue("Dormitory", "111, One St", "Suite 111", "Atlanta Georgia 11111"));
		addressTypes_Per1.add(new TypeValue("Foster Home", "222, Second St", "Suite 222", "Atlanta Georgia 22222"));
		addressTypes_Per1.add(new TypeValue("Bad Address", "333, Third St", "Suite 333", "Atlanta Georgia 33333"));*/
		ArrayList<Object> addressTypes_Per1 = getAddressTypes(mergePerVO1);

		/*ArrayList<Object> addressTypes_Per2 = new ArrayList<Object> ();
		addressTypes_Per2.add(new TypeValue("Dormitory", "111, First St", "Suite 111", "Atlanta Georgia 11111"));
		addressTypes_Per2.add(new TypeValue("Foster Home", "222, Two St", "Suite 222", "Atlanta Georgia 22222"));
		addressTypes_Per2.add(new TypeValue("Bad Address", "333, Three St", "Suite 333", "Atlanta Georgia 33333"));*/
		ArrayList<Object> addressTypes_Per2 = getAddressTypes(mergePerVO2);

		request.setAttribute("addressTypes_Per1",  addressTypes_Per1);
		request.setAttribute("addressTypes_Per2",  addressTypes_Per2);

		//---- TELEPHONE TYPE AND TELEPHONE ----
		/*ArrayList<Object> telephoneTypes_Per1 = new ArrayList<Object> ();
		telephoneTypes_Per1.add(new TypeValue("Current", "1, 111-111-1111, 1111", "abc@def.com", "www.mycompany11.com"));
		telephoneTypes_Per1.add(new TypeValue("Current", "2, 222-222-2222, 2222", "abc1@def.com", "www.mycompany12.com"));*/
		ArrayList<Object> telephoneTypes_Per1 = getTelephoneTypes(mergePerVO1);

		/*ArrayList<Object> telephoneTypes_Per2 = new ArrayList<Object> ();
		telephoneTypes_Per2.add(new TypeValue("Current", "3, 333-333-3333, 3333", "abc@def.com", "www.mycompany21.com"));
		telephoneTypes_Per2.add(new TypeValue("Current", "4, 444-444-4444, 4444", "abc2@def.com", "www.mycompany22.com"));*/
		ArrayList<Object> telephoneTypes_Per2 = getTelephoneTypes(mergePerVO2);

		request.setAttribute("telephoneTypes_Per1",  telephoneTypes_Per1);
		request.setAttribute("telephoneTypes_Per2",  telephoneTypes_Per2);

		//---- LOCATION TYPE AND ADDRESS ----
		/*ArrayList<Object> locationTypes_Per1 = new ArrayList<Object> ();
		locationTypes_Per1.add(new TypeValue("Emergency Contact", "Location description 1"));
		locationTypes_Per1.add(new TypeValue("Bad address", "Moving..this is bad address"));*/
		ArrayList<Object> locationTypes_Per1 = getPhysicalLocations(mergePerVO1);

		/*ArrayList<Object> locationTypes_Per2 = new ArrayList<Object> ();
		locationTypes_Per2.add(new TypeValue("Emergency Contact", "Better description here"));
		locationTypes_Per2.add(new TypeValue("Bad address", "I am thinking of moving"));*/
		ArrayList<Object> locationTypes_Per2 = getPhysicalLocations(mergePerVO2);

		request.setAttribute("locationTypes_Per1",  locationTypes_Per1);
		request.setAttribute("locationTypes_Per2",  locationTypes_Per2);

		//---- ADMIN ROLE ----
		/*String adminRole_Per1="Cardiology Clinic";
		String adminRole_Per2="Drug manufacturer";*/

		String adminRole_Per1=getRolesString(mergePerVO1);
		String adminRole_Per2=getRolesString(mergePerVO2);
		request.setAttribute("adminRole_Per1", adminRole_Per1 );

		logger.debug("/////----- Merge Persons Compare load's Role String for person-1 with local id "
		+ mergePerVO1.getThePersonDT().getLocalId() + " is " + adminRole_Per1);

		request.setAttribute("adminRole_Per2", adminRole_Per2 );

		logger.debug("/////----- Merge Persons Compare load's Role String for person-2 with local id "
		+ mergePerVO2.getThePersonDT().getLocalId() + " is " + adminRole_Per2);

		//---- GENERAL COMMENTS ----
		/*String generalComments_Per1="This is a test role description";
		String generalComments_Per2="Make the best drugs in the world";*/

		String generalComments_Per1=mergePerVO1.getThePersonDT().getDescription();
		String generalComments_Per2=mergePerVO2.getThePersonDT().getDescription();
		request.setAttribute("generalComments_Per1", generalComments_Per1 );
		request.setAttribute("generalComments_Per2", generalComments_Per2 );


	}

	 /**
      * Handles the retrieving of the roles from the given personVO
      * @J2EE_METHOD  --  getRolesString
      * @return personVO    the  PersonVO
      **/
	private String getRolesString(PersonVO mergePerVO)
	{
		Collection<Object>  roles = mergePerVO.getTheRoleDTCollection();
		StringBuffer rolesBuffer = new StringBuffer("");

		if(roles!=null)
		{

			Iterator<Object> iter = roles.iterator();

			if (iter != null)
			{
				while (iter.hasNext())
				{
					RoleDT roleDT  = (RoleDT) iter.next();

					if (roleDT!=null)
					{
						/*if (rolesBuffer.length() > 0)
						{
							rolesBuffer.append(", ").append(roleDT.getCd());
							rolesBuffer.append("|").append(roleDT.getCd());
						}
						else
						{
							rolesBuffer.append(roleDT.getCd());
						}*/
				  		rolesBuffer.append(roleDT.getCd()).append("|");
					}
				}
			}
		}
		return rolesBuffer.toString();
	}

	/**
      * Handles the setting of a string to blank if value is null.  This is to support the blank choice
      * in a dropdown box.
      * @J2EE_METHOD  --  makeBlankIfNull
      * @return value    the  String
      **/
	private String makeBlankIfNull(String value)
	{
		String retValue = "";

		if (value != null)
			retValue = value;

		return retValue;

	}

	 /**
      * Handles the retrieving of the personNameTypes from the given personVO
      * @J2EE_METHOD  --  getPersonNameTypes
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getPersonNameTypes(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();

		Collection<Object>  names = personVO.getThePersonNameDTCollection();
		if (names!=null)
		{
			Iterator<Object> iter = names.iterator();

			while (iter.hasNext())
			{
				PersonNameDT name = (PersonNameDT) iter.next();
				if (name!=null)
				{
					if (name.getNmUseCd() != null)
					{

						StringBuffer sNamesCombined = new StringBuffer("");

						// Concatenate the names
						sNamesCombined.append(makeBlankIfNull(name.getFirstNm()));
						sNamesCombined.append(" ").append(makeBlankIfNull(name.getMiddleNm()));
						sNamesCombined.append(" ").append(makeBlankIfNull(name.getMiddleNm2()));
						sNamesCombined.append(" ").append(makeBlankIfNull(name.getLastNm()));
						sNamesCombined.append(" ").append(makeBlankIfNull(name.getLastNm2()));


						//Construct typevalue obj and add to arraylist
						TypeValue tv = new TypeValue(name.getNmUseCd(), sNamesCombined.toString());
						retArrList.add(tv);

					}
				}
			}
		}

		return retArrList;
	}


	 /**
      * Handles the retrieving of the identificationTypes from the given personVO
      * @J2EE_METHOD  --  getIdentificationTypes
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getIdentificationTypes(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();

		Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
		if (ids!=null)
		{
			Iterator<Object> iter = ids.iterator();
			while (iter.hasNext())
			{
				EntityIdDT id = (EntityIdDT) iter.next();
				if (id!=null)
				{
					if (id.getTypeCd() != null)
					{

						logger.debug("Identification TypeCd is " + id.getTypeCd()
							+ " and Identification Root Ext Txt is " + id.getRootExtensionTxt());

						// Since SSN that is entered through the SSN field is also added to
						// the entityid table, it will show up here. But, since we display SSN on the
						// merge compare screen, we don't need to show it again here. So we filter it.
						//if (!id.getTypeCd().equals("SS"))
						//{
							//Construct typevalue obj and add to arraylist
							TypeValue tv = new TypeValue(id.getTypeCd(), id.getRootExtensionTxt());
							retArrList.add(tv);
						//}

					}

				}
			}
		}

		return retArrList;
	}

   /**
      * Handles the retrieving of the addressTypes from the given personVO
      * @J2EE_METHOD  --  getAddressTypes
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getAddressTypes(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();

		Collection<Object>  addresses = personVO.getTheEntityLocatorParticipationDTCollection();
		if (addresses!=null)
		{
			Iterator<Object> iter = addresses.iterator();
			while (iter.hasNext())
			{
				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.next();

				if (elp!=null)
				{
					if (elp.getStatusCd()!=null && elp.getClassCd()!=null && elp.getUseCd()!=null && elp.getStatusCd().equals("A") &&  elp.getClassCd().equals("PST") && elp.getCd().equals("BDL") && elp.getUseCd().equals("BIR"))
					{
						//do nothing
					}
					else if (elp.getStatusCd()!=null && elp.getClassCd()!=null && elp.getUseCd()!=null && elp.getStatusCd().equals("A") && elp.getClassCd().equals("PST") && elp.getCd().equals("U") && elp.getUseCd().equals("DTH"))
					{
						//do nothing
					}
					else if (elp.getStatusCd()!=null && elp.getClassCd()!=null
  						        && elp.getClassCd().equals("PST") )
					{
						StringBuffer sAddressesCombined = new StringBuffer("");
						PostalLocatorDT postDT = elp.getThePostalLocatorDT();

						if (postDT != null)
						{
						    // Concatenate the city state and zip
						    if (postDT.getCityDescTxt() != null) {
								sAddressesCombined.append(postDT.getCityDescTxt());
							}

						    if (postDT.getStateCd() != null) {
								sAddressesCombined.append(" ").append(getStateDescTxt(postDT.getStateCd()));
							}

						    if (postDT.getZipCd() != null) {
								sAddressesCombined.append(" ").append(postDT.getZipCd());
							}

							String cd = ((elp.getCd() != null) ? elp.getCd() : "");
							String addr1 = ((postDT.getStreetAddr1() != null) ? postDT.getStreetAddr1() : "");
							String addr2 = ((postDT.getStreetAddr2() != null) ? postDT.getStreetAddr2() : "");

						    // Add the Type, Addr1, Addr2, CityStateZip TypeValue obj to arraylist
						    TypeValue tv = new TypeValue(cd,
							    addr1,
							    addr2,
							    sAddressesCombined.toString());

						    //Construct typevalue obj and add to arraylist
						    retArrList.add(tv);

						}

					}

				}
			}
		}

		return retArrList;
	}

     /**
      * Handles the retrieving of the state description from the given state code.
      * @J2EE_METHOD  --  getStateDescTxt
      * @return sStateCd    the  String
      **/
    private String getStateDescTxt(String sStateCd)
    {

	CachedDropDownValues srtValues = new CachedDropDownValues();
	TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
	String desc = "";

	if (sStateCd != null && treemap.get(sStateCd) != null)
	    desc = (String)treemap.get(sStateCd);

	return desc;
    }

	/**
      * Handles the retrieving of the telephoneTypes from the given personVO.
      * @J2EE_METHOD  --  getTelephoneTypes
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getTelephoneTypes(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();

		Collection<Object>  addresses = personVO.getTheEntityLocatorParticipationDTCollection();
		if (addresses!=null)
		{
			Iterator<Object> iter = addresses.iterator();
			while (iter.hasNext())
			{
				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.next();
				if (elp!=null)
				{
					if (elp.getStatusCd()!=null && elp.getClassCd()!=null && elp.getClassCd().equals("TELE"))
					{

						TeleLocatorDT teleDT = elp.getTheTeleLocatorDT();

						if (teleDT != null)
						{
							StringBuffer sTelephoneNumsCombined = new StringBuffer("");

							// Concatenate the country, phone num, ext
							if (teleDT.getCntryCd() != null) {
								sTelephoneNumsCombined.append(teleDT.getCntryCd());
							}

							if (teleDT.getPhoneNbrTxt() != null) {
								sTelephoneNumsCombined.append(", ").append(teleDT.getPhoneNbrTxt());
							} else {
								sTelephoneNumsCombined.append(", ");
							}

							if (teleDT.getExtensionTxt() != null) {
								sTelephoneNumsCombined.append(", ").append(teleDT.getExtensionTxt());
							} else {
								sTelephoneNumsCombined.append(", ");
							}

							String cd = ((elp.getCd() != null) ? elp.getCd() : "");
							String email = ((teleDT.getEmailAddress() != null) ? teleDT.getEmailAddress()
														: "");
							String url = ((teleDT.getUrlAddress() != null) ? teleDT.getUrlAddress()
														: "");

							// Add the Type and values to TypeValue obj and then to arraylist
							TypeValue tv = new TypeValue(cd,
								sTelephoneNumsCombined.toString(),
								email,
								url
								);

							//Construct typevalue obj and add to arraylist
							retArrList.add(tv);
						}

					}

				}
			}
		}

		return retArrList;
	}

	 /**
      * Handles the retrieving of the physicalLocationTypes from the given personVO
      * @J2EE_METHOD  --  getPhysicalLocations
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getPhysicalLocations(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();

		Collection<Object>  locations = personVO.getTheEntityLocatorParticipationDTCollection();
		if (locations!=null)
		{
			Iterator<Object> iter = locations.iterator();
			while (iter.hasNext())
			{
				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.next();
				if (elp!=null)
				{
					if (elp.getStatusCd()!=null && elp.getClassCd()!=null && elp.getClassCd().equals("PHYS"))
					{

						String cd = ((elp.getCd() != null) ? elp.getCd() : "");
						String cdDesc = ((elp.getCdDescTxt() != null) ? elp.getCdDescTxt() : "");

						// Add the Type and values to TypeValue obj and then to arraylist
						TypeValue tv = new TypeValue(cd, cdDesc);

						//Construct typevalue obj and add to arraylist
						retArrList.add(tv);

					}

				}
			}
		}

		return retArrList;
	}

	 /**
      * Handles the retrieving of the raceTypes from the given personVO
      * @J2EE_METHOD  --  getRaceTypes
      * @return personVO    the  PersonVO
      **/
	private ArrayList<Object> getRaceTypes(PersonVO personVO)
	{
		ArrayList<Object> retArrList = new ArrayList<Object> ();
		StringBuffer sAmericanIndian= new StringBuffer("");
		String sAmericanIndianController="";
		StringBuffer sWhite=new StringBuffer("");
		String sWhiteController="";
		StringBuffer sAfricanAmerican=new StringBuffer("");
		String sAfricanAmericanController="";
		StringBuffer sAsian=new StringBuffer("");
		String sAsianController="";
		StringBuffer sHawaiian=new StringBuffer("");
		String sHawaiianController="";
		StringBuffer sOtherRace= new StringBuffer("");
		String sOtherRaceController="";
		String sOtherRaceDescText="";
		StringBuffer unknownRace =new StringBuffer("");
		String unknownRaceController = "";
		TypeValue tv = null;

		Collection<Object>  races = personVO.getThePersonRaceDTCollection();

		if (races!=null)
		{
			logger.debug("Number of races in collection is " + races.size());
			Iterator<Object> iter = races.iterator();
			while (iter.hasNext())
			{
				PersonRaceDT race = (PersonRaceDT) iter.next();
				if (race!=null)
				{
					if (race.getRaceCategoryCd() != null)
					{
						String recStatus = "";
						if (race.getRecordStatusCd() != null) {
							recStatus = race.getRecordStatusCd().trim();
						}
						String raceCatCode = "";
						if (race.getRaceCategoryCd() != null) {
							raceCatCode = race.getRaceCategoryCd().trim();
						}

						String raceDesc = "";
						if (race.getRaceDescTxt() != null) {
							raceDesc = race.getRaceDescTxt().trim();
						}


						// Unknown
						if (recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals("U")){
							if (unknownRace.toString().trim().length() > 0){
								unknownRace.append(", ").append(raceDesc);
							} else {
								unknownRaceController = "Unknown";
								unknownRace.append(raceDesc);
							}

						}// American Indian
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)){

							if (sAmericanIndian.toString().trim().length() > 0){
								sAmericanIndian.append(", ").append(raceDesc);
							} else {
								sAmericanIndianController = "American Indian or Alaskan Native";
								sAmericanIndian.append(raceDesc);
							}
						} // White
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.WHITE)){

							if (sWhite.toString().trim().length() > 0){
								sWhite.append(", ").append(raceDesc);
							} else {
								sWhiteController = "White";
								sWhite.append(raceDesc);
							}

						} // African American
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.AFRICAN_AMERICAN)){

							if (sAfricanAmerican.toString().trim().length() > 0){
								sAfricanAmerican.append(", ").append(raceDesc);
							} else {
								sAfricanAmericanController = "Black or African American";
								sAfricanAmerican.append(raceDesc);
							}
						} // Asian
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.ASIAN)){

							if (sAsian.toString().trim().length() > 0){
								sAsian.append(", ").append(raceDesc);
							} else {
								sAsianController = "Asian";
								sAsian.append(raceDesc);
							}

						} // Hawaiian
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)){

							if (sHawaiian.toString().trim().length() > 0){
								sHawaiian.append(", ").append(raceDesc);
							} else {
								sHawaiianController = "Native Hawaiian or Other Pacific Islander";
								sHawaiian.append(raceDesc);
							}

						} // Other Race
						else if(recStatus.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && raceCatCode.equals(NEDSSConstants.OTHER_RACE)){
							if (sOtherRace.toString().trim().length() > 0){
								sOtherRace.append(", ").append(raceDesc);
							} else {
								sOtherRaceController = "Other";
								sOtherRace.append(raceDesc);
							}
						}

					}//if (race.getRaceCategoryCd() != null)

				}//if (race!=null)
			}//while (iter.hasNext())

			if (unknownRaceController.length() > 0){
				tv = new TypeValue(unknownRaceController, unknownRace.toString());
				retArrList.add(tv);
			}

			if (sAmericanIndianController.length() > 0){
				tv = new TypeValue(sAmericanIndianController, sAmericanIndian.toString());
				retArrList.add(tv);
			}

			if (sWhiteController.length() > 0){
				tv = new TypeValue(sWhiteController, sWhite.toString());
				retArrList.add(tv);
			}

			if (sAfricanAmericanController.length() > 0){
				tv = new TypeValue(sAfricanAmericanController, sAfricanAmerican.toString());
				retArrList.add(tv);
			}

			if (sAsianController.length() > 0){
				tv = new TypeValue(sAsianController, sAsian.toString());
				retArrList.add(tv);
			}

			if (sHawaiianController.length() > 0){
				tv = new TypeValue(sHawaiianController, sHawaiian.toString());
				retArrList.add(tv);
			}

			if (sOtherRaceController.length() > 0){
				tv = new TypeValue(sOtherRaceController, sOtherRace.toString());
				retArrList.add(tv);
			}

		}//if (races!=null)

		return retArrList;
	}


	 /**
      * Handles the retrieving of the ethnicityTypes from the given personVO
      * @J2EE_METHOD  --  getEthnicityValues
      * @return personVO    the  PersonVO
      **/
	private String getEthnicityValues(PersonVO personVO)
	{
		/*StringBuffer otherEthnic =new StringBuffer("");
		String otherEthnicController = "";*/
		StringBuffer hispEthnic =new StringBuffer("");
		String hispEthnicController = "";

		Collection<Object>  ethnicities = personVO.getThePersonEthnicGroupDTCollection();

		if (ethnicities!=null)
		{
			Iterator<Object> iter = ethnicities.iterator();
			while (iter.hasNext())
			{
			   	PersonEthnicGroupDT ethnic  = (PersonEthnicGroupDT) iter.next();
				if (ethnic!=null)
				{
					String ethGpCd = ethnic.getEthnicGroupCd().trim();

					if (ethnic.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && (!ethGpCd.equals("O")))
					{
						/*
						if (hispEthnic.length() > 0){
							hispEthnic.append(", ").append(ethGpCd);
						} else {
							hispEthnic.append(ethGpCd);
						} */
			      		hispEthnic.append(ethGpCd).append("|");
					}


				}//if (ethnic!=null)
			}//while (iter.hasNext())
		}//if (ethnicities!=null)

		return hispEthnic.toString();
	}

}