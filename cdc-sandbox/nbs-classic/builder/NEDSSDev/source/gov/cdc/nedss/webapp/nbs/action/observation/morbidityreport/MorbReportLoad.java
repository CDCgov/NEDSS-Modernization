package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.morbidity.MorbidityForm;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.util.*;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;

import java.util.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.MorbidityUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;


/**
 * Title:        MorbReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class MorbReportLoad
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(MorbReportLoad.class.getName());
  
  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {
	  try {
		  HttpSession session = request.getSession();

		  request.setAttribute("addMorb", "true");
		  String contextAction = request.getParameter("ContextAction");

		  NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
				  "NBSSecurityObject");  
		  TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS195", contextAction);
		  
		  
		  String sCurrTask = NBSContext.getCurrentTask(session);
		  if (sCurrTask.equalsIgnoreCase("AddObservationMorbDataEntry1"))
		  {
			  request.setAttribute("ContextActionLeftNav", tm.get("Submit"));
			  boolean AddObservationMorbDataEntry1 = secObj.getPermission(NBSBOLookup.
					  OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD, "ANY", "ANY");
			  if (AddObservationMorbDataEntry1 == false)
			  {
				  session.setAttribute("error", "Failed at security checking.");
				  throw new ServletException("Failed at security checking.");
			  }

			  //check security for search and clear buttons
			  boolean bSearchPatientButton = secObj.getPermission(NBSBOLookup.PATIENT,
					  NBSOperationLookup.FIND);

			  request.setAttribute("fromFile", "false");
			  request.setAttribute("fromNav","true");

			  String jurisdictionValues = new CachedDropDownValues().getJurisdictionCodedSortedValues();
			  jurisdictionValues = "NONE$Unknown|" + jurisdictionValues;
			  request.setAttribute("jurisdictionCodedSortedValues", jurisdictionValues);


			  request.setAttribute("bSearchPatientButton",
					  String.valueOf(bSearchPatientButton));

			  //if strJurisdiction is false, jurisdiction and share indicator will be read-only
			  boolean strJurisdiction = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					  NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);
			  request.setAttribute("jurisdictionPermission", String.valueOf(strJurisdiction));
			  request.setAttribute("countiesInState",PersonUtil.getCountiesByState(PersonUtil.getDefaultStateCd()));

			  UserProfile userprofile = secObj.getTheUserProfile();
			  User user = userprofile.getTheUser();
			  String userType = user.getUserType();
			  Long userReportingFacilityUid = user.getReportingFacilityUid();


			  boolean searchPermission = secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FIND);
			  // if the user do not have permission to search, do not display the button
			  request.setAttribute("SearchPermission",String.valueOf(searchPermission));

			  if (userType != null)
			  {
				  //if an external user, ReportDeliveryMethod and Reporting field will be read-only and default to Web
				  boolean strReportExternalUser = userType.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
				  request.setAttribute("strReportExternalUser",String.valueOf(strReportExternalUser));

				  if(strReportExternalUser && (userReportingFacilityUid == null))
				  {
					  session.setAttribute("error", "There is no Reporting Facility for this user. Permission denied.");
					  throw new ServletException("There is no Reporting Facility for this user. Permission denied.");
				  }

				  try
				  {
					  if (userReportingFacilityUid != null)
					  {
						  request.setAttribute("reportingOrgUID", userReportingFacilityUid);
						  CommonLabUtil.getDropDownLists(NEDSSConstants.DEFAULT,
								  NEDSSConstants.RESULTED_TEST_LOOKUP,null,
								  NEDSSConstants.PROGRAM_AREA_NONE,
								  null, null,
								  request);
						  Map<Object,Object> results = getOrganization(String.valueOf(userReportingFacilityUid.longValue()), session);
						  if (results != null)
						  {
							  request.setAttribute("reportingSourceDetails", (String) results.get("result"));
						  }
					  }
					  else
					  {
						  request.setAttribute("reportingSourceDetails",
								  "There is no Reporting Facility selected.");
					  }

				  }
				  catch (Exception e)
				  {
					  e.printStackTrace();
				  }

			  }

		  }

		  request.setAttribute("MRB142", "T");
		  request.setAttribute("MRB162", StringUtils.formatDate(new java.sql.Timestamp(new Date().getTime())));


		  request.setAttribute("formHref",
				  "/nbs/" + sCurrTask + ".do?ContextAction=" +
						  tm.get("Submit"));

		  request.setAttribute("cancelButtonHref",
				  "/nbs/" + sCurrTask + ".do?ContextAction=" +
						  tm.get("Cancel"));

		  ErrorMessageHelper.setErrMsgToRequest(request, "ps195");

		  request.setAttribute("hospitalDetails", "There is no Hospital selected.");

		  request.setAttribute("providerSourceDetails",
				  "There is no Provider selected.");

		  request.setAttribute("reporterSourceDetails",
				  "There is no Reporter selected.");

		  request.setAttribute("fromNavBar", "true");

		  String retainPatient = request.getParameter("retainPatient");

		  try
		  {
			  request.setAttribute("showPersonLocalId", "false");
			  if (retainPatient != null && retainPatient.equalsIgnoreCase("T"))
			  {
				  PersonVO retainPersonVO = (PersonVO) NBSContext.retrieve(session, "DSPatientPersonVO");
				  request.setAttribute("showPersonLocalId", "true");
				  request.setAttribute("DSFileTab", "2");

				  createXSP(NEDSSConstants.PATIENT_LDF, retainPersonVO, null, request);

				  convertPersonToRequestObj(retainPersonVO, request);
			  }
			  else
			  {
				  request.setAttribute("showPersonLocalId", "false");
				  request.setAttribute("DEM162",PersonUtil.getDefaultStateCd());
				  request.setAttribute("defaultStateFlag","true");
			  }
		  }
		  catch (NullPointerException ne)
		  {
			  //do nothing, since for the first time load, there will be no personVO in the objectStore
		  }
		  request.getAttribute("largeFiles");
		 /* String hasAttachmentErrors = (session.getAttribute("hasAttachmentErrors")==null)?"false":(String) session.getAttribute("hasAttachmentErrors");
		  if("true".equalsIgnoreCase(hasAttachmentErrors)){
			//  UserProfile userProfile = userForm.getUserProfile();
			  MorbidityForm morbidityForm = (MorbidityForm) aForm;
			  if ( morbidityForm.getAttachment_s(0)!= null)
				{
				  String name = morbidityForm.getAttachment_s(0).getAttachmentVO_s(0).getTheAttachmentDT().getFileNmTxt();
					request.setAttribute("fileNmTxt",name );
					//convertReportToRequestObj(userProfile, secObj,request);
				 // userForm.setUserProfile(userProfile);
				} //end of if
				session.setAttribute("hasAttachmentErrors", null);
				request.setAttribute("hasAttachmentErrors", "true");
				
		  }
			  */
			  
			  
		  new MorbidityUtil().getFilteredProgramAreaJurisdiction(request, secObj);

		  CommonLabUtil commonLabUtil = new CommonLabUtil();
		  commonLabUtil.loadMorbLDFAdd(request);
		  if (retainPatient == null || !retainPatient.equalsIgnoreCase("T"))
			  commonLabUtil.loadPatientLDFAdd(request);
		  PropertyUtil propUtil = PropertyUtil.getInstance();
		  int maxSizeInMB = propUtil.getMaxFileAttachmentSizeInMB();
		  request.setAttribute("maxFileSizeInMB", String.valueOf(maxSizeInMB)); 

		  String allowedExtensions = propUtil.getFileAttachmentExtensions();
		  request.setAttribute("allowedExtensions", allowedExtensions); 
		  // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004  
		  sCurrTask = NBSContext.getCurrentTask(session);
		  if (sCurrTask.equalsIgnoreCase("AddObservationMorbDataEntry1"))
		  {
			  request.setAttribute("DSFileTab", new Integer(NEDSSConstants.EVENT_TAB_PATIENT_ORDER).toString());
		  }
		  else
		  {
			  request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultMorbTabOrder()).toString());
		  }
	  }catch (Exception e) {
		  logger.error("Exception in Morb Report Load: " + e.getMessage());
		  e.printStackTrace();
		  throw new ServletException("Error occurred in Morb Report Load : "+e.getMessage());
	  } 
  return (mapping.findForward("XSP"));
}

/**
 * This method is to convert the PersonVO object
 * into Request attributes
 *
 * @param personVO PersonVO
 * @param request HttpServletRequest
 * @param action String
 * @param stateList ArrayList
 */
public void convertPersonToRequestObj(PersonVO personVO,
                                      HttpServletRequest request)
{

  if (personVO != null && personVO.getThePersonDT() != null)
  {

    PersonDT personDT = personVO.getThePersonDT();

    request.setAttribute("patientLocalId", personDT.getLocalId());
    request.setAttribute("patientDisplayLocalId", PersonUtil.getDisplayLocalID(personDT.getLocalId()));
    request.setAttribute("personUid", personDT.getPersonParentUid());
    request.setAttribute("DEM115",
                         StringUtils.formatDate(personDT.getBirthTime()));
    request.setAttribute("DEM216",
                         (String) personDT.getAgeReported());
    request.setAttribute("DEM218",
                         personDT.getAgeReportedUnitCd());
    request.setAttribute("DEM128",
                         StringUtils.formatDate(personDT.getDeceasedTime()));
    request.setAttribute("DEM113", personDT.getCurrSexCd());

    request.setAttribute("DEM140", personDT.getMaritalStatusCd());

    request.setAttribute("DEM196", personDT.getDescription());

    request.setAttribute("DEM155", personDT.getEthnicGroupInd());

    // for patient info at top of page
    request.setAttribute("birthTime", StringUtils.formatDate(personDT.getBirthTime()));
    request.setAttribute("currSexCd", personDT.getCurrSexCd());

    this.setPersonELPToRequest(personVO, request);
//      this.setPersonEthnicity(personVO, request);
    this.setPersonNameToRequest(personVO, request);
    this.setPersonRaceToRequest(personVO, request);
    this.setPersonSSNToRequest(personVO, request);

  }
}

private void setPersonNameToRequest(PersonVO personVO,
                                    HttpServletRequest request)
{
  //personName collection
  PersonDT personDT = personVO.getThePersonDT();
  Collection<Object>  names = personVO.getThePersonNameDTCollection();
  if (names != null)
  {
   Iterator<Object>  iter = names.iterator();
    while (iter.hasNext())
    {
      PersonNameDT name = (PersonNameDT) iter.next();
      if (name != null)
      {
        if (name != null && name.getNmUseCd() != null &&
            name.getNmUseCd().equals(NEDSSConstants.LEGAL))
        {
          request.setAttribute("DEM102", name.getLastNm());
          request.setAttribute("DEM104", name.getFirstNm());
          request.setAttribute("DEM105", name.getMiddleNm());
          request.setAttribute("DEM107", name.getNmSuffix());

          // for patient info at top of page
          StringBuffer sbName = new StringBuffer();
          sbName.append(name.getFirstNm());
          sbName.append(" ");
          sbName.append(name.getLastNm());
          request.setAttribute("patientName", sbName.toString());
        }
      }
    }
  }
}

private void setPersonRaceToRequest(PersonVO personVO,
                                    HttpServletRequest request)
{
  Collection<Object>  races = personVO.getThePersonRaceDTCollection();
  if (races != null)
  {
   Iterator<Object>  iter = races.iterator();
    while (iter.hasNext())
    {
      PersonRaceDT raceDT = (PersonRaceDT) iter.next();

      if (raceDT.getRaceCd().equals("U"))
      {
        request.setAttribute("unknownRace", "y");
      }
      if (raceDT.getRaceCd().equals("1002-5"))
      {
        request.setAttribute("americanIndianController", "y");
      }
      if (raceDT.getRaceCd().equals("2028-9"))
      {
        request.setAttribute("asianController", "y");
      }
      if (raceDT.getRaceCd().equals("2054-5"))
      {
        request.setAttribute("africanAmericanController", "y");
      }
      if (raceDT.getRaceCd().equals("2076-8"))
      {
        request.setAttribute("hawaiianController", "y");
      }
      if (raceDT.getRaceCd().equals("2106-3"))
      {
        request.setAttribute("whiteController", "y");
      }
      if (raceDT.getRaceCd().equals("2131-1"))
      {
        request.setAttribute("OtherRace", "y");
        request.setAttribute("OtherRaceDescText", raceDT.getRaceDescTxt());
      }
    }
  }
}

private void setPersonSSNToRequest(PersonVO personVO,
                                   HttpServletRequest request)
{
  //get SSN from entityId collection
  Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
  if (ids != null)
  {
   Iterator<Object>  iter = ids.iterator();
    String entity = null;
    while (iter.hasNext())
    {
      EntityIdDT id = (EntityIdDT) iter.next();
      if (id != null)
      {
        request.setAttribute("DEM133", id.getRootExtensionTxt());
        // info for top of page
        request.setAttribute("patientSSN", id.getRootExtensionTxt());
      }
    }
  }
}

private void setPersonELPToRequest(PersonVO personVO,
                                   HttpServletRequest request)
{
  Collection<Object>  addresses = personVO.
      getTheEntityLocatorParticipationDTCollection();

  if (addresses != null)
  {
   Iterator<Object>  iter = addresses.iterator();
    while (iter.hasNext())
    {
      EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.
          next();
      if (elp != null)
      {
        if (
            elp.getClassCd().equals("PST"))
        {
          PostalLocatorDT postal = elp.getThePostalLocatorDT();
          if (postal != null)
          {
            request.setAttribute("DEM159", postal.getStreetAddr1());
            request.setAttribute("DEM161", postal.getCityDescTxt());
            request.setAttribute("DEM162", postal.getStateCd());
            request.setAttribute("DEM163", postal.getZipCd());
            request.setAttribute("DEM165", postal.getCntyCd());
            request.setAttribute("countiesInState",
                                 PersonUtil.
                                 getCountiesByState(postal.getStateCd()));
            request.setAttribute("defaultStateFlag","false");
            // for patient info at top of page
            StringBuffer sb = new StringBuffer();
            sb.append(postal.getStreetAddr1());
            sb.append(", ");
            sb.append(postal.getCityDescTxt());
            sb.append(", ");
            String stateCd = postal.getStateCd();
            CachedDropDownValues cdv = new CachedDropDownValues();
            TreeMap<Object,Object> tm = null;
            try
            {
              tm = cdv.getCachedStateCodeList();
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
            String stateNm = (String)tm.get(stateCd);
            sb.append(stateNm);
            sb.append(" ");
            sb.append(postal.getZipCd());
            request.setAttribute("patientAddress", sb.toString());
          }
        }
      }
      if (elp.getClassCd().equals("TELE"))
      {
        TeleLocatorDT tele = elp.getTheTeleLocatorDT();
        if (tele != null)
        {
          request.setAttribute("DEM177", tele.getPhoneNbrTxt());
        }
      }
    }
  }
}

private void setPersonEthnicity(PersonVO personVO,
                                HttpServletRequest request)
{
  //set ethnicity
  Collection<Object>  ethnicities = personVO.getThePersonEthnicGroupDTCollection();
  if (ethnicities != null)
  {
   Iterator<Object>  iter = ethnicities.iterator();

    if (iter != null)
    {
      while (iter.hasNext())
      {
        PersonEthnicGroupDT ethnic = (PersonEthnicGroupDT) iter.
            next();
        request.setAttribute("DEM155",
                             ethnic.getEthnicGroupDescTxt());

      }
    }
  }
}

private Map<Object,Object> getOrganization(String organizationUid, HttpSession session) throws
    Exception
{
  Map<Object,Object> returnMap = new HashMap<Object,Object>();
  StringBuffer result = new StringBuffer("");
  MainSessionCommand msCommand = null;
  String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
  String sMethod = "getOrganization";
  Object[] oParams = new Object[]
      {
      new Long(organizationUid)};

  MainSessionHolder holder = new MainSessionHolder();
  msCommand = holder.getMainSessionCommand(session);
  ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
  OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
  if (organizationVO != null)
  {

    if (organizationVO.getTheOrganizationNameDTCollection() != null)
    {
     Iterator<Object>  orgNameIt = organizationVO.
          getTheOrganizationNameDTCollection().
          iterator();
      while (orgNameIt.hasNext())
      {
        OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.
            next();
        result.append(orgName.getNmTxt());
      }
    }
    if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null)
    {
      if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null)
      {
       Iterator<Object>  orgLocatorIt = organizationVO.
            getTheEntityLocatorParticipationDTCollection().iterator();
        while (orgLocatorIt.hasNext())
        {
          EntityLocatorParticipationDT entityLocatorDT = (
              EntityLocatorParticipationDT) orgLocatorIt.next();
          if (entityLocatorDT != null)
          {
            PostalLocatorDT postaLocatorDT = entityLocatorDT.
                getThePostalLocatorDT();
            if (postaLocatorDT != null)
            {
              if (postaLocatorDT.getStreetAddr1() != null)
              {
                result.append("<br/>").append(postaLocatorDT.
                                             getStreetAddr1());
              }
              if (postaLocatorDT.getStreetAddr2() != null)
              {
                result.append("<br/>").append(postaLocatorDT.
                                             getStreetAddr2());
              }
              if (postaLocatorDT.getCityDescTxt() != null)
              {
                result.append("<br/>").append(postaLocatorDT.getCityDescTxt());

              }
              if (postaLocatorDT.getStateCd() != null)
              {
                String stateCd = postaLocatorDT.getStateCd();
                CachedDropDownValues cdv = new CachedDropDownValues();
                TreeMap<Object,Object> tm = cdv.getCachedStateCodeList();
                String stateDesc = (String)tm.get(stateCd);
                result.append(", ").append(stateDesc);
              }
              if (postaLocatorDT.getZipCd() != null)
              {
                result.append(" ").append(postaLocatorDT.getZipCd());

              }
            }
          }
          TeleLocatorDT telelocatorDT = entityLocatorDT.
              getTheTeleLocatorDT();
          if (telelocatorDT != null)
          {
            if (telelocatorDT.getPhoneNbrTxt() != null)
            {
              result.append("<br/>").append(telelocatorDT.
                                           getPhoneNbrTxt());
            }
            if (telelocatorDT.getExtensionTxt() != null)
            {
              result.append(" Ext. ").append(telelocatorDT.
                                             getExtensionTxt());
            }
            break;
          }
        }
      }
    }
  }

  returnMap.put("UID", organizationUid);
  returnMap.put("result", result.toString());
  return returnMap;

}

}
