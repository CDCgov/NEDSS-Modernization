package gov.cdc.nedss.webapp.nbs.action.organization;

import gov.cdc.nedss.entity.person.util.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.webapp.nbs.form.organization.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.action.util.*;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 * Name:		OrganizationLoad.java
 * Description:	This is a action class for the structs implementation for loading the organization page
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class OrganizationLoad
    extends BaseLdf {

  //For logging
  static final LogUtils logger = new LogUtils(OrganizationLoad.class.getName());


  public OrganizationLoad() {
  }

  /** Based on the contextaction, sets  values to the request,stores the objects to the NBSContext
   * and redirects to the appropriate page
   * @param mapping   the ActionMapping
   * @param form    the  ActionForm
   * @param request   the HttpServletRequest
   * @param response    the HttpServletResponse
   * @return   ActionForward
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    request.getSession().removeAttribute("result");
    logger.debug("inside the OrganizationLoad");

    OrganizationForm organizationForm = (OrganizationForm) form;
    
    try {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		throw new ServletException("Session is null");
    	}

    	NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
    			"NBSSecurityObject");
    	String contextAction = request.getParameter("ContextAction");

    	if (contextAction == null) {
    		contextAction = (String) request.getAttribute("ContextAction");

    	}
    	String strUID = request.getParameter("uid");
    	//strUID could be a String "null"
    	if (strUID == null || strUID.equalsIgnoreCase("null")) {
    		strUID = (String) request.getAttribute("uid");
    	}

    	//  VIEW action
    	if (contextAction.equalsIgnoreCase("View")
    			|| contextAction.equalsIgnoreCase("Submit")
    			|| contextAction.equalsIgnoreCase("Cancel")
    			|| contextAction.equalsIgnoreCase("ReturnToViewOrganization")) {

    		if(contextAction.equalsIgnoreCase("Cancel")){
    			strUID = (String) session.getAttribute("strUID");
    		}
    		String err111 = (String) request.getAttribute("err111");

    		if (err111 != null) {
    			contextAction = "SubmitStayOnError";
    			TreeMap<Object,Object> tm = null;
    			String sCurrTask = NBSContext.getCurrentTask(session);

    			//context for Add
    			if (sCurrTask.startsWith("AddOrganization")) {
    				tm = NBSContext.getPageContext(session, "PS040", contextAction);
    				NBSContext.lookInsideTreeMap(tm);
    				ErrorMessageHelper.setErrMsgToRequest(request, "PS040");
    			}
    			//context for Edit
    			else if (sCurrTask.startsWith("EditOrganization")) {
    				tm = NBSContext.getPageContext(session, "PS099", contextAction);
    				NBSContext.lookInsideTreeMap(tm);
    				String editOptions = "editOptions";
    				request.setAttribute("editErr", editOptions);
    				ErrorMessageHelper.setErrMsgToRequest(request, "PS099");
    			}

    			sCurrTask = NBSContext.getCurrentTask(session);
    			request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
    			request.setAttribute("ContextAction", tm.get("Submit"));
    			request.setAttribute("cancelButtonHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("Cancel"));
    			convertOrganizationToRequestObj(organizationForm.getOrganization(),
    					request);
    			OrganizationVO orgVO = organizationForm.getOrganization();
    			createXSP(NEDSSConstants.ORGANIZATION_LDF,
    					orgVO.getTheOrganizationDT().getOrganizationUid(), orgVO, null,
    					request);
    			// use the new API to retrieve custom field collection
    			// to handle multiselect fields (xz 01/11/2005)
    			Collection<Object>  coll = extractLdfDataCollection(organizationForm, request);
    			if (coll!= null)
    			{
    				orgVO.setTheStateDefinedFieldDataDTCollection(coll);
    			}
    			orgVO.setTheOrganizationNameDTCollection(null);
    			orgVO.setTheEntityIdDTCollection(null);
    			orgVO.setTheEntityLocatorParticipationDTCollection(null);
    			organizationForm.setOrganization(orgVO);
    			organizationForm.reset();
    		}
    		/**
    		 * End of server side Validation
    		 */
    		else {
    			//check security for buttons
    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS106", contextAction);
    			NBSContext.lookInsideTreeMap(tm);
    			/**
    			 * Added for the error Messages
    			 */
    			ErrorMessageHelper.setErrMsgToRequest(request, "PS106");

    			String sCurrTask = NBSContext.getCurrentTask(session);
    			request.setAttribute("addButtonHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("Add"));
    			request.setAttribute("editButtonHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("Edit"));
    			request.setAttribute("cancelButtonHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("Cancel"));
    			request.setAttribute("deleteButtonHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("Inactivate"));
    			request.setAttribute(
    					"returnToSearchResultsHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ReturnToSearchResults"));
    			organizationForm.reset();

    			OrganizationVO organizationVO = new OrganizationVO();

    			if (strUID != null) {
    				if ( (!strUID.equals("")) || (!strUID.equalsIgnoreCase("null"))) {

    					organizationVO = getOldOrganizationObject(strUID, organizationForm,
    							session);
    				}
    			}
    			if (organizationVO != null) {

    				try {

    					boolean bEditButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
    							NBSOperationLookup.MANAGE);
    					boolean bDeleteButton = secObj.getPermission(NBSBOLookup.
    							ORGANIZATION, NBSOperationLookup.MANAGE);

    					if (organizationVO.getTheOrganizationDT().getRecordStatusCd() != null
    							&&
    							organizationVO.getTheOrganizationDT().getRecordStatusCd().trim().
    							equals(
    									NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE)) {
    						bEditButton = false;
    						bDeleteButton = false;
    					}
    					if (organizationVO.getTheOrganizationDT().getElectronicInd() != null
    							&&
    							organizationVO.getTheOrganizationDT().getElectronicInd().trim().
    							equalsIgnoreCase(
    									NEDSSConstants.YES)) {
    						bEditButton = false;
    						bDeleteButton = false;
    					}

    					request.setAttribute("deleteButton", String.valueOf(bDeleteButton));
    					request.setAttribute("editButton", String.valueOf(bEditButton));

    					boolean bFileButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
    							NBSOperationLookup.VIEWWORKUP);
    					request.setAttribute("fileButton", String.valueOf(bFileButton));

    					boolean bAddButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
    							NBSOperationLookup.MANAGE);
    					request.setAttribute("addButton", String.valueOf(bAddButton));
    				}
    				catch (Exception e) {
    					e.printStackTrace();
    					logger.error("getting permissions for the buttons failed");
    				}

    				convertOrganizationToRequestObj(organizationVO, request);
    				organizationForm.setOrganization(organizationVO);
    				createXSP(NEDSSConstants.ORGANIZATION_LDF,
    						organizationVO.getTheOrganizationDT().getOrganizationUid(),
    						organizationVO, null, request);
    			}
    			else {

    				throw new ServletException();
    			}
    		}
    		return mapping.findForward("XSP");
    	}

    	//  CREATE
    	else if (contextAction.equalsIgnoreCase("Add")) {

    		//context
    		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS040", contextAction);
    		NBSContext.lookInsideTreeMap(tm);
    		session.setAttribute("strUID", strUID);
    		/**
    		 * Added for the error Messages
    		 */
    		ErrorMessageHelper.setErrMsgToRequest(request, "PS040");
    		String sCurrTask = NBSContext.getCurrentTask(session);
    		request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
    		request.setAttribute("ContextAction", tm.get("Submit"));
    		request.setAttribute("cancelButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get("Cancel"));

    		/* Prepopulating the value that is there in the search Criteria */
    		if (! (sCurrTask.equalsIgnoreCase("AddOrganization3"))) {
    			OrganizationSearchVO organizationSearchVO = (OrganizationSearchVO)
    					NBSContext.retrieve(session, "DSSearchCriteria");
    			if (organizationSearchVO.getNmTxtOperator() != null &&
    					(organizationSearchVO.getNmTxtOperator().equals("=") ||
    							organizationSearchVO.getNmTxtOperator().equals("CT")) ||
    							organizationSearchVO.getNmTxtOperator().equals("SW")) {
    				request.setAttribute("organizationName",
    						organizationSearchVO.getNmTxt());
    			}
    			if ( (organizationSearchVO.getStreetAddr1Operator().equals("=") ||
    					organizationSearchVO.getStreetAddr1Operator().equals("CT")) &&
    					organizationSearchVO.getStreetAddr1() != null) {
    				request.setAttribute("addressOne",
    						organizationSearchVO.getStreetAddr1());
    			}
    			if (organizationSearchVO.getCityDescTxtOperator() != null) {
    				if (organizationSearchVO.getCityDescTxtOperator().equals("=") ||
    						organizationSearchVO.getCityDescTxtOperator().equals("CT")) {
    					request.setAttribute("cityOne", organizationSearchVO.getCityDescTxt());
    					/*if (organizationSearchVO.getStateCdOperator() != null)
                 if (organizationSearchVO.getStateCdOperator().equals("=") && */
    				}
    			}
    			if (organizationSearchVO.getStateCd() != null) {
    				request.setAttribute("stateOne", organizationSearchVO.getStateCd());
    				request.setAttribute("addressCounties",
    						getCountiesByState(organizationSearchVO.
    								getStateCd()));
    			}
    			/*if (organizationSearchVO.getZipCdOperator() != null)
          if (organizationSearchVO.getZipCdOperator().equals("=") &&*/
    			if (organizationSearchVO.getZipCd() != null) {
    				request.setAttribute("zipOne", organizationSearchVO.getZipCd());
    				/*if (organizationSearchVO.getTypeCdOperator() != null)
            if (organizationSearchVO.getTypeCdOperator().equals("=") &&*/
    			}
    			if (organizationSearchVO.getTypeCd() != null) {
    				request.setAttribute("typeOne", organizationSearchVO.getTypeCd());
    			}
    			if (organizationSearchVO.getRootExtensionTxt() != null) {
    				request.setAttribute("idValueOne",
    						organizationSearchVO.getRootExtensionTxt());

    			}
    		}

    		// need to put something in the county drop down
    		request.setAttribute("birthCountiesInState", "+|");
    		request.setAttribute("deathCountiesInState", "+|");
    		OrganizationVO organizationVO = new OrganizationVO();
    		createXSP(NEDSSConstants.ORGANIZATION_LDF, organizationVO, null, request);

    		return mapping.findForward("XSP");
    	}

    	//  EDIT
    	else if (contextAction.equalsIgnoreCase("EditOrganization") ||
    			contextAction.equalsIgnoreCase("Edit")) {

    		//context
    		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS099", contextAction);
    		NBSContext.lookInsideTreeMap(tm);
    		/**
    		 * Added for the error Messages
    		 */
    		ErrorMessageHelper.setErrMsgToRequest(request, "PS099");

    		String sCurrTask = NBSContext.getCurrentTask(session);
    		request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
    		request.setAttribute("ContextAction", tm.get("Submit"));
    		request.setAttribute("cancelButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get("Cancel"));
    		if (strUID != null) {
    			if ( (!strUID.equals("")) ||
    					(!strUID.equalsIgnoreCase("null"))) {

    				OrganizationVO organizationVO = getOldOrganizationObject(strUID,
    						organizationForm, session);
    				session.setAttribute("strUID", strUID);
    				convertOrganizationToRequestObj(organizationVO, request);
    				// going to reset the name collection
    				organizationVO.setTheOrganizationNameDTCollection(null);
    				organizationVO.setTheEntityIdDTCollection(null);
    				organizationVO.setTheEntityLocatorParticipationDTCollection(null);

    				// store the proxy object in the form, so that when we submit
    				// form, struts will write directly into the form
    				organizationForm.setOrganization(organizationVO);
    				createXSP(NEDSSConstants.ORGANIZATION_LDF,
    						organizationVO.getTheOrganizationDT().getOrganizationUid(),
    						organizationVO, null, request);
    			}
    		}
    		return mapping.findForward("XSP");
    	}
    	else if (contextAction.equalsIgnoreCase("DeleteDenied")) {

    		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS130", contextAction);


    		String sCurrTask = NBSContext.getCurrentTask(session);
    		request.setAttribute(
    				"returnToViewOrganizationHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get("ReturnToViewOrganization"));
    		return mapping.findForward("XSP");
    	}

    	//  NO OPERATION TYPE
    	else {
    		return (mapping.findForward("error"));
    	}
    }catch (Exception e) {
    	logger.error("Exception in Organization Load: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("General error occurred in Organization Load : "+e.getMessage());
    }
  }

  /**
   * Gets the addresses in an ArrayList<Object> from  OrganizationVO
   * @param organizationVO   the OrganizationVO
   * @return  addresses      the ArrayList<Object> of addresses
   */
  private ArrayList<Object> getAddressCollection(OrganizationVO organizationVO) {

    ArrayList<Object> addresses = new ArrayList<Object> ();

    return addresses;
  }

  /**
   * Gets the telephones in an ArrayList<Object> from OrganizationVO
   * @param organizationVO    the OrganizationVO
   * @return      the ArrayList<Object> of telephones
   */
  private ArrayList<Object> getTelephoneCollection(OrganizationVO organizationVO) {

    ArrayList<Object> telephones = new ArrayList<Object> ();

    return telephones;
  }

  /**
   * Formats the TimeStamp to MM/DD/YYYY form
   * @param timestamp    the java.sql.Timestamp
   * @return    the String of Date in MM/DD/YYYY format
   */
  private String formatDate(java.sql.Timestamp timestamp) {

    Date date = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    logger.info("InvestigationMeaslesPreAction.formatDate: date: " + timestamp);

    if (timestamp != null) {
      date = new Date(timestamp.getTime());

    }
    logger.info("InvestigationMeaslesPreAction.formatDate: date: " + date);

    if (date == null) {

      return "";
    }
    else {

      return formatter.format(date);
    }
  }

  /**
   * Gets the the values of OrganizationVO from the database by sending the OragnizationUID
   * @param strUID   the String of UID
   * @param form     the OrganizationForm
   * @param session   the HttpSession
   * @return        the OrganizationVO
   */
  private OrganizationVO getOldOrganizationObject(String strUID,
                                                  OrganizationForm form,
                                                  HttpSession session) {

    OrganizationVO organization = null;
    MainSessionCommand msCommand = null;

    if ( (strUID != null) || (!strUID.equals("")) ||
        (!strUID.equalsIgnoreCase("null"))) {

      try {

        Long UID = new Long(strUID.trim());
        String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        String sMethod = "getOrganization";
        Object[] oParams = new Object[] {
            UID};

        //  if(msCommand == null)
        //{
        MainSessionHolder holder = new MainSessionHolder();
        msCommand = holder.getMainSessionCommand(session);

        // }
        ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
                                                 oParams);
        organization = (OrganizationVO) arr.get(0);
        form.setOrganization(organization);
      }
      catch (NumberFormatException e) {
        logger.error("Error: no organization UID");

        return null;
      }
      catch (Exception ex) {

        if (session == null) {
          logger.error("Error: no session, please login");
        }

        logger.fatal("getOldOrganizationObject: ", ex);

        return null;
      }
      finally {
        msCommand = null;
      }
    }
    else { // if the request didn't povide the organizationUid, look in form
      organization = form.getOrganization();
    }
    return organization;
  }

  /**
   * Converts the values of OrganizationVO to  HttpServletRequest
   * @param organizationVO   the OrganizationVO
   * @param request     the HttpServletRequest
   */
  private void convertOrganizationToRequestObj(OrganizationVO organizationVO,
                                               HttpServletRequest request) {

    Long organizationUID = null;
    String localID = "";
    String organizationName = "";
    String orgNmUseCd = "";
    Integer orgNmSeq = null;
    logger.info("organizationVO class is - " + organizationVO);

    if (organizationVO != null && organizationVO.getTheOrganizationDT() != null) {

      //logger.debug("organizationVO class is - " + organizationVO);
      OrganizationDT organization = organizationVO.getTheOrganizationDT();

      //for the top bar
      request.setAttribute("organizationLocalID", organization.getLocalId());
      localID = organization.getLocalId();

      //to persist this information for query string or input element
      request.setAttribute("organizationUID",
                           String.valueOf(organization.getUid()));
      organizationUID = organization.getUid();
      request.setAttribute("lastChgTime",
                           formatDate(organization.getLastChgTime()));
      request.setAttribute("lastChgUserId",
                           String.valueOf(organization.getLastChgUserId()));
      request.setAttribute("lastChgReasonCd", organization.getLastChgReasonCd());
      request.setAttribute("recordStatusCd", organization.getRecordStatusCd());
      request.setAttribute("adminComments", organization.getDescription());
      request.setAttribute("adminCd", organization.getCd());
      request.setAttribute("industryCd",
                           organization.getStandardIndustryClassCd());

      // set up the roles information
      Collection<Object>  roles = organizationVO.getTheRoleDTCollection();

      if (roles != null) {

        StringBuffer rolesBuffer = new StringBuffer("");
       Iterator<Object>  iter = roles.iterator();

        if (iter != null) {

          while (iter.hasNext()) {

            RoleDT roleDT = (RoleDT) iter.next();

            if (roleDT != null
                && roleDT.getRecordStatusCd() != null
                &&
                roleDT.getRecordStatusCd().equals(NEDSSConstants.
                                                  RECORD_STATUS_ACTIVE)
                && roleDT.getCd() != null) {
              rolesBuffer.append(roleDT.getCd()).append(NEDSSConstants.
                  BATCH_LINE);
            }
          }
        }

        request.setAttribute("roleList", rolesBuffer.toString());
      }
      else {
        logger.error("THE ROLES COLLECTION IS NULL");

        // create the organization name parsed string for the batch entry javascript
      }
      Collection<Object>  names = organizationVO.getTheOrganizationNameDTCollection();

      if (names == null) {
        logger.debug("Names = null");
      }

      if (names != null) {

       Iterator<Object>  iter = names.iterator();
        StringBuffer sNamesCombined = new StringBuffer("");

        while (iter.hasNext()) {

          OrganizationNameDT name = (OrganizationNameDT) iter.next();

          if (name != null) {
            logger.debug("Inside name dt");

            // for organizationInfo
            if (name.getNmTxt() != null) {
              organizationName = name.getNmTxt();
              request.setAttribute("organizationName", organizationName);
            }
            if (name.getNmUseCd() != null) {
              orgNmUseCd = name.getNmUseCd();
              request.setAttribute("orgNmUseCd", orgNmUseCd);
            }

            if (name.getOrganizationNameSeq() != null) {
              orgNmSeq = name.getOrganizationNameSeq();
              request.setAttribute("orgNmSeq", orgNmSeq);
            }

          }
        }
      }

      // create the entity id parsed string for the batch entry javascript
      Collection<Object>  ids = organizationVO.getTheEntityIdDTCollection();

      if (ids != null) {

       Iterator<Object>  iter = ids.iterator();
        StringBuffer combinedIds = new StringBuffer("");

        while (iter.hasNext()) {

          EntityIdDT id = (EntityIdDT) iter.next();

          if (id != null) {

            if (id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
              request.setAttribute("quickCode", id.getRootExtensionTxt());
            }

            combinedIds.append("organization.entityIdDT_s[i].typeCd").append(
                NEDSSConstants.BATCH_PART);

            if (id.getTypeCd() != null) {
              combinedIds.append(id.getTypeCd());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].statusCd").append(
                NEDSSConstants.BATCH_PART);

            if (id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
              combinedIds.append("I");
            }
            else {
              combinedIds.append(id.getStatusCd());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].typeDescTxt").append(
                NEDSSConstants.BATCH_PART);

            if (id.getTypeDescTxt() != null) {
              combinedIds.append(id.getTypeDescTxt());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].assigningAuthorityCd").append(
                NEDSSConstants.BATCH_PART);

            if (id.getAssigningAuthorityCd() != null) {
              combinedIds.append(id.getAssigningAuthorityCd());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].assigningAuthorityDescTxt").
                append(
                NEDSSConstants.BATCH_PART);

            if (id.getAssigningAuthorityDescTxt() != null) {
              combinedIds.append(id.getAssigningAuthorityDescTxt());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].statusCd").append(
                NEDSSConstants.BATCH_PART);

            if (id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
              combinedIds.append("I");
            }
            else {
              combinedIds.append(id.getStatusCd());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].rootExtensionTxt").append(
                NEDSSConstants.BATCH_PART);

            if (id.getRootExtensionTxt() != null) {
              combinedIds.append(id.getRootExtensionTxt());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].validFromTime_s").append(
                NEDSSConstants.BATCH_PART);

            if (id.getValidFromTime() != null) {
              combinedIds.append(formatDate(id.getValidFromTime()));

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].validToTime_s").append(
                NEDSSConstants.BATCH_PART);

            if (id.getValidToTime() != null) {
              combinedIds.append(formatDate(id.getValidToTime()));

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].entityIdSeq").append(
                NEDSSConstants.BATCH_PART);

            if (id.getEntityIdSeq() != null) {
              combinedIds.append(id.getEntityIdSeq());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                "organization.entityIdDT_s[i].entityUid").append(
                NEDSSConstants.BATCH_PART);

            if (id.getEntityUid() != null) {
              combinedIds.append(id.getEntityUid().toString());

            }
            combinedIds.append(NEDSSConstants.BATCH_SECT).append(NEDSSConstants.
                BATCH_LINE);

          }
        }

        request.setAttribute("ids", combinedIds.toString());
      }

      StringBuffer sParsedAddresses = new StringBuffer("");
      StringBuffer sParsedTeles = new StringBuffer("");
      StringBuffer sParsedLocators = new StringBuffer("");
      Collection<Object>  addresses = organizationVO.
          getTheEntityLocatorParticipationDTCollection();
      if (addresses != null) {

       Iterator<Object>  iter = addresses.iterator();

        while (iter.hasNext()) {

          EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
              iter.next();

          if (elp != null) {

            if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                elp.getClassCd().equals("PST")) {

              PostalLocatorDT postal = elp.getThePostalLocatorDT();

              logger.info("Creating parsed string for addresses");
              //create an array of states
              ArrayList<Object> stateList = new ArrayList<Object> ();
              if (postal.getStateCd() != null) {
                stateList.add(postal.getStateCd());
              }
              prepareAddressCounties(request, stateList);

              sParsedAddresses.append("address[i].cd").append(NEDSSConstants.
                  BATCH_PART);

              if (elp.getCd() != null) {
                sParsedAddresses.append(elp.getCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].useCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getUseCd() != null) {
                sParsedAddresses.append(elp.getUseCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].statusCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getStatusCd() != null) {
                sParsedAddresses.append(elp.getStatusCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].durationUnitCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getDurationUnitCd() != null) {
                sParsedAddresses.append(elp.getDurationUnitCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].cdDescTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getCdDescTxt() != null) {
                sParsedAddresses.append(elp.getCdDescTxt());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.streetAddr1").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getStreetAddr1() != null) {
                sParsedAddresses.append(postal.getStreetAddr1());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.streetAddr2").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getStreetAddr2() != null) {
                sParsedAddresses.append(postal.getStreetAddr2());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.cityDescTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getCityDescTxt() != null) {
                sParsedAddresses.append(postal.getCityDescTxt());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.stateCd").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getStateCd() != null) {
                sParsedAddresses.append(postal.getStateCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.zipCd").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getZipCd() != null) {
                sParsedAddresses.append(postal.getZipCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.cntyCd").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getCntyCd() != null) {
                sParsedAddresses.append(postal.getCntyCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.cntryCd").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getCntryCd() != null) {
                sParsedAddresses.append(postal.getCntryCd());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].fromTime_s").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getFromTime() != null) {
                sParsedAddresses.append(formatDate(elp.getFromTime()));

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].toTime_s").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getToTime() != null) {
                sParsedAddresses.append(formatDate(elp.getToTime()));

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].durationAmt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getDurationAmt() != null) {
                sParsedAddresses.append(elp.getDurationAmt());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].validTimeTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getValidTimeTxt() != null) {
                sParsedAddresses.append(elp.getValidTimeTxt());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].locatorDescTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getLocatorDescTxt() != null) {
                sParsedAddresses.append(elp.getLocatorDescTxt());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].thePostalLocatorDT_s.postalLocatorUid").append(
                  NEDSSConstants.BATCH_PART);

              if (postal.getPostalLocatorUid() != null) {
                sParsedAddresses.append(postal.getPostalLocatorUid().toString());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].entityUid").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getEntityUid() != null) {
                sParsedAddresses.append(elp.getEntityUid().toString());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  "address[i].locatorUid").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getLocatorUid() != null) {
                sParsedAddresses.append(elp.getLocatorUid().toString());

              }
              sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                  NEDSSConstants.BATCH_LINE);

            }

            //create the telephone parsed string
            else if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                     elp.getClassCd().equals("TELE")) {

              TeleLocatorDT tele = elp.getTheTeleLocatorDT();
              
              sParsedTeles.append(
                  "telephone[i].useCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getUseCd() != null) {
                sParsedTeles.append(elp.getUseCd());

              }
              
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
            		  "telephone[i].cd").append(NEDSSConstants.
                  BATCH_PART);

              if (elp.getCd() != null) {
                sParsedTeles.append(elp.getCd());

              }
              
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].statusCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getStatusCd() != null) {
                sParsedTeles.append(elp.getStatusCd());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].durationUnitCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getDurationUnitCd() != null) {
                sParsedTeles.append(elp.getDurationUnitCd());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].cdDescTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getCdDescTxt() != null) {
                sParsedTeles.append(elp.getCdDescTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.cntryCd").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getCntryCd() != null) {
                sParsedTeles.append(tele.getCntryCd());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.phoneNbrTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getPhoneNbrTxt() != null) {
                sParsedTeles.append(tele.getPhoneNbrTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.extensionTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getExtensionTxt() != null) {
                sParsedTeles.append(tele.getExtensionTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.emailAddress").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getEmailAddress() != null) {
                sParsedTeles.append(tele.getEmailAddress());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.urlAddress").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getUrlAddress() != null) {
                sParsedTeles.append(tele.getUrlAddress());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].fromTime_s").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getFromTime() != null) {
                sParsedTeles.append(formatDate(elp.getFromTime()));

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].toTime_s").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getToTime() != null) {
                sParsedTeles.append(formatDate(elp.getToTime()));

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].durationAmt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getDurationAmt() != null) {
                sParsedTeles.append(elp.getDurationAmt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].validTimeTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getValidTimeTxt() != null) {
                sParsedTeles.append(elp.getValidTimeTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].locatorDescTxt").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getLocatorDescTxt() != null) {
                sParsedTeles.append(elp.getLocatorDescTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.teleLocatorUid").append(
                  NEDSSConstants.BATCH_PART);

              if (tele.getTeleLocatorUid() != null) {
                sParsedTeles.append(tele.getTeleLocatorUid().toString());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].locatorUid").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getLocatorUid() != null) {
                sParsedTeles.append(elp.getLocatorUid().toString());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].entityUid").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getEntityUid() != null) {
                sParsedTeles.append(elp.getEntityUid().toString());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  NEDSSConstants.BATCH_LINE);
            }
          }
        }

        request.setAttribute("addresses", sParsedAddresses.toString());
        request.setAttribute("parsedTelephoneString", sParsedTeles.toString());
        request.setAttribute("parsedLocatorsString", sParsedLocators.toString());
      }
    }
  }

  /**
   * Gets all the counties from the database based on the stateCd
   * @param stateCd   the String
   * @return    the String
   */
  private String getCountiesByState(String stateCd) {

    StringBuffer parsedCodes = new StringBuffer("");

    if (stateCd != null) {

      //SRTValues srtValues = new SRTValues();
      CachedDropDownValues srtValues = new CachedDropDownValues();
      TreeMap<?,?> treemap = null;
      treemap = srtValues.getCountyCodes(stateCd);

      if (treemap != null) {

        Set<?> set = treemap.keySet();
       Iterator<?>  itr = set.iterator();

        while (itr.hasNext()) {

          String key = (String) itr.next();
          String value = (String) treemap.get(key);
          parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
              value.trim()).append(
              NEDSSConstants.SRT_LINE);
        }
      }
    }

    return parsedCodes.toString();
  }

  /**
   * Sets all the counties in the specific state to the request attribute
   * @param request    the HttpServletRequest
   */
  private void prepareAddressCounties(HttpServletRequest request,
                                      ArrayList<Object> stateList) {

    if (stateList != null) {

      StringBuffer totalCounties = new StringBuffer();
      List<Object> unique = new ArrayList<Object> ();
     Iterator<Object>  i = stateList.iterator();

      while (i.hasNext()) {

        Object current = i.next();

        if (!unique.contains(current)) {
          unique.add(current);
        }
      }

     Iterator<Object>  states = unique.iterator();

      while (states.hasNext()) {
        totalCounties.append(getCountiesByState( (String) states.next()));
      }
      request.setAttribute("addressCounties", totalCounties.toString());
    }
  }

}