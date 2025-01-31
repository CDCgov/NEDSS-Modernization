package gov.cdc.nedss.webapp.nbs.action.treatment;

//import gov.cdc.nedss.entity.material.vo.*;
//import gov.cdc.nedss.entity.material.dt.*;
//import gov.cdc.nedss.entity.vo.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentProxyVO;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;

//Added for Facility Pre-population
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.form.treatment.TreatmentForm;



/**
 * Title:        TreatmentLoad
 * Description:  this class retrieves data from EJB and puts them into request object
 * for use in the xml file.
 * Copyright:    Copyright (c) 2001-2002
 * Company:      CSC
 * @author       Aaron Aycock
 * @version 1.0
 */

public class TreatmentCreateLoad
    extends BaseLdf {
   static final LogUtils logger = new LogUtils(TreatmentCreateLoad.class.getName());

   boolean bReports = false;
   public TreatmentCreateLoad() {
   }

   /**
        * An ActionForward represents a destination to which the controller servlet,
    * ActionServlet, might be directed to execute a RequestDispatcher.forward()
        * or HttpServletResponse.sendRedirect() to, as a result of processing activities
    * of an Action class. This is the only public method in TreatmentLoad class
    * that handles the HttpServletRequest request object with ActionMapping and actionForm to generate
    * HttpServletResponse response object.
    * @param mapping ActionMapping object
    * @param aForm ActionForm object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @throws IOException
    * @throws ServletException
    * @return : ActionForward object
    */
   public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {

      TreatmentForm form = (TreatmentForm) aForm;
      //security checks including check for session
      HttpSession session = request.getSession(false);
      if (session == null) {
         logger.debug("error no session");
         throw new ServletException("error no session");
      }

      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
      if (nbsSecurityObj == null) {
         logger.fatal(
             "Error: No securityObj in the session, go back to login screen");
         return mapping.findForward("login");
      }

      // Get Context Action
      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

      }

      logger.debug("TreatmentLoad: ContextAction = " + contextAction);

      Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
      request.setAttribute("patientPersonLocalID",PersonUtil.getDisplayLocalID((String) NBSContext.retrieve(session,
          "DSPatientPersonLocalID")));

	  //fix for civ8332
	  PersonSummaryVO personSummaryVO = this.getPersonSummaryVO(mprUid, session);
	  String strFirstName = ((personSummaryVO.getFirstName() == null)
					 ? "" : personSummaryVO.getFirstName());
      String strLastName = ((personSummaryVO.getLastName() == null)
					 ? "" : personSummaryVO.getLastName());
	//request.setAttribute("patientName", strPatientName);
	request.setAttribute("legalFirstName", strFirstName);
    request.setAttribute("legalLastName", strLastName);
	request.setAttribute("birthTime", WumUtil.formatDate(personSummaryVO.getDOB()));
	request.setAttribute("currSexCd", personSummaryVO.getCurrentSex());
	//request.setAttribute("jurisdiction", personSummaryVO.getDefaultJurisdictionCd());
	//request.setAttribute("personLocalID", personSummaryVO.getLocalId());

	//fix for civ8328
	//Pre-populate reporting facility if profile contains FacilityUID

    Long userReportingFacilityUid = nbsSecurityObj.getTheUserProfile().getTheUser().
    getReportingFacilityUid();

    try{
	   if (userReportingFacilityUid != null) {
          Map<Object,Object> results = getOrganization(String.valueOf(userReportingFacilityUid.longValue()), session);
          request.setAttribute("reportingFacilityUID",userReportingFacilityUid.toString());

            if (results != null) {
               request.setAttribute("reportingSourceDetails",
                                         (String) results.get("result"));
            }
            else {
               request.setAttribute("reportingSourceDetails",
                      "There is no Reporting Facility for this user.");
            } //end if results check
	   }//end if facility check
	} //end try
    catch (Exception e) {
       e.printStackTrace();
    }


	//End fix for civ8328

      boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
          NBSOperationLookup.MANAGE);
      if (!check1) {
         logger.fatal("Error: Do not have permission to create treatment.");
         throw new ServletException("Error: Do not have permission to create treatment.");
      }

      ErrorMessageHelper.setErrMsgToRequest(request, "ps199");


      //sPersonUID = request.getParameter("personUID");
      // if (bReports)

      // context layer
      ArrayList<Object> stateList = new ArrayList<Object> ();
     // String conditionCode = "";
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS199", contextAction);
      String sCurrTask = NBSContext.getCurrentTask(session);

      
      //Check if Co-Infection is applicable for the investigation/condition
      // 

      Long investigationUid = null;
      try {
    	  String investigationUidStr = (String)  request.getAttribute("DSInvestigationUID");
    	  if (investigationUidStr== null)
    		  investigationUidStr= (String) NBSContext.retrieve(session, "DSInvestigationUID");
    	  if (investigationUidStr != null)
    		  investigationUid = new Long(investigationUidStr);
		} catch (Exception e) {
			logger.warn("In AddTreatment - expected Investigation Uid not in scope to check for coinfections..");
		}
       Integer coinfectionInvList = null;
       if (investigationUid != null)
		try {
			coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListCount(investigationUid, request);
		} catch (NEDSSAppConcurrentDataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
      // if we're in a co-infection situation and there are other invs
      // we may want them to be linked to this treatment as well
      if (coinfectionInvList!=null && coinfectionInvList.intValue() > 1) {
    	  request.setAttribute(NEDSSConstants.COINFECTION_INV_EXISTS, true);
    	  //request.setAttribute("coInfectionInvestigationList", coinfectionInvList);
      } else request.setAttribute(NEDSSConstants.COINFECTION_INV_EXISTS, false);    
      
      /**
         * Getting the treatment dropdown values from the new SRT filtering methods for Rel 1.1.3
         * Commented out Treatment and treatment Drugs because of the roll back from Rel1.1.3
      */
     /* String treatmentDropdown = NedssCodeLookupServlet.convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_VALUES, NEDSSConstants.TREATMENT_SRT_CREATE,null,conditionCode);
      request.setAttribute("treatmentdropdown",treatmentDropdown); */
      //* Getting thr values for the Treatment Drugs *//
      /*String treatmentDrugsDropdown = NedssCodeLookupServlet.convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_DRUGS, NEDSSConstants.TREATMENT_SRT_CREATE,null,conditionCode);
      request.setAttribute("teatmentDrugDropdown",treatmentDrugsDropdown); */
      request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
      request.setAttribute("ContextAction", tm.get("Submit"));
      request.setAttribute("cancelButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("Cancel"));
      TreatmentProxyVO treatmentProxyVO = new TreatmentProxyVO();
      createXSP(NEDSSConstants.TREATMENT_LDF, treatmentProxyVO, null, request ) ;
      // Forward to XSP
      return mapping.findForward("XSP");

   }

   private Map<Object,Object> getOrganization(String organizationUid, HttpSession session)
      throws    Exception
  {
    Map<Object,Object> returnMap = new HashMap<Object,Object>();
    StringBuffer result = new StringBuffer("");
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "getOrganization";
    Object[] oParams = new Object[] {
        new Long(organizationUid)};

    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
    if (organizationVO != null) {

      if (organizationVO.getTheOrganizationNameDTCollection() != null) {
       Iterator<Object>  orgNameIt = organizationVO.
            getTheOrganizationNameDTCollection().
            iterator();
        while (orgNameIt.hasNext()) {
          OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.
              next();
          result.append(orgName.getNmTxt());
        }
      }
      if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
        if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
         Iterator<Object>  orgLocatorIt = organizationVO.
              getTheEntityLocatorParticipationDTCollection().iterator();
          while (orgLocatorIt.hasNext()) {
            EntityLocatorParticipationDT entityLocatorDT = (
                EntityLocatorParticipationDT) orgLocatorIt.next();
            if (entityLocatorDT != null) {
              PostalLocatorDT postaLocatorDT = entityLocatorDT.
                  getThePostalLocatorDT();
              if (postaLocatorDT != null) {
                if (postaLocatorDT.getStreetAddr1() != null) {
                  result.append("<br/>").append(postaLocatorDT.
                                               getStreetAddr1());
                }
                if (postaLocatorDT.getStreetAddr2() != null) {
                  result.append("<br/>").append(postaLocatorDT.
                                               getStreetAddr2());
                }
                if (postaLocatorDT.getCityCd() != null) {
                  result.append("<br/>").append(postaLocatorDT.getCityCd());

                }
                if (postaLocatorDT.getStateCd() != null) {
                  result.append(", ").append(postaLocatorDT.getStateCd());

                }
                if (postaLocatorDT.getZipCd() != null) {
                  result.append(" ").append(postaLocatorDT.getZipCd());

                }
              }
            }
            TeleLocatorDT telelocatorDT = entityLocatorDT.
                getTheTeleLocatorDT();
            if (telelocatorDT != null) {
              if (telelocatorDT.getPhoneNbrTxt() != null) {
                result.append("<br/>").append(telelocatorDT.
                                             getPhoneNbrTxt());
              }
              if (telelocatorDT.getExtensionTxt() != null) {
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

  /**
     * retreve the PersonSummaryVO to populate the Treatment with person summary
	* @return personUID -- UID for the person to be populated
	* @return session -- The http session
     */
    private PersonSummaryVO getPersonSummaryVO(Long personUID, HttpSession session)
    {

	PersonSummaryVO personSummaryVO = null;
	MainSessionCommand msCommand = null;

	try
	{

	    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    String sMethod = "getPersonSummary";
	    Object[] oParams = new Object[] { personUID };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);

	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    personSummaryVO = (PersonSummaryVO)arr.get(0);
	}
	catch (Exception ex)
	{

	    if (session == null)
	    {
		logger.error("Error: no session, please login");
	    }

	    logger.fatal("personSummaryVO: ", ex);
	}

	return personSummaryVO;
    }

}
