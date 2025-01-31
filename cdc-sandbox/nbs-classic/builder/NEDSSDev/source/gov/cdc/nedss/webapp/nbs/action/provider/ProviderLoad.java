package gov.cdc.nedss.webapp.nbs.action.provider;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.webapp.nbs.form.provider.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

public class ProviderLoad
    extends BaseLdf{

  //For logging
  static final LogUtils logger = new LogUtils(ProviderLoad.class.getName());


  public ProviderLoad() {
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
    
    logger.debug(
        "remove the result session holding the providerSearch object");
    request.getSession().removeAttribute("result");
    logger.debug("inside the ProviderLoad");
    ProviderForm providerForm = (ProviderForm) form;
    HttpSession session = request.getSession(false);
    
    ArrayList<Object> stateList =  new ArrayList<Object> ();
    
    if (session == null) {

    	throw new ServletException("Session is null");
    }

    NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    String contextAction = request.getParameter("ContextAction");

    if (contextAction == null) {
      contextAction = (String) request.getAttribute("ContextAction");

    }

    String strUID = request.getParameter("providerUID");

    if (strUID == null) {
      strUID = (String) request.getAttribute("providerUID");
    }
    if(strUID == null)
      strUID = (String) request.getParameter("uid");


    // check the security for role select box
    request.setAttribute("roleSecurity",
                         String.valueOf(secObj.getPermission(
        NBSBOLookup.PROVIDER,
       "MANAGE")));


    //  VIEW action
    if (contextAction.equalsIgnoreCase("View") ||
        contextAction.equalsIgnoreCase("Submit") ||
        contextAction.equalsIgnoreCase("Cancel") ||
        contextAction.equalsIgnoreCase("ReturnToViewProvider")||
        contextAction.equalsIgnoreCase("SubmitStayOnError")
        ) {

      /**
    * For server Side Validations
    */
   String err111 = (String)request.getAttribute("err111");
   if(err111 != null) {
     contextAction = "SubmitStayOnError";


     TreeMap<Object,Object> tm = null;
     String sCurrTask = NBSContext.getCurrentTask(session);

      //context for Add
     if(sCurrTask.startsWith("Add"))
        {
          tm = NBSContext.getPageContext(session, "PS178",
                                            contextAction);
            NBSContext.lookInsideTreeMap(tm);
            /**
             * Added for the error Messages
             */
            ErrorMessageHelper.setErrMsgToRequest(request, "PS178");
        }
        //context for Edit
          if(sCurrTask.startsWith("Edit")) {
          tm = NBSContext.getPageContext(session, "PS179",
                                         contextAction);
          NBSContext.lookInsideTreeMap(tm);
          String editOptions = "editOptions";
          request.setAttribute("editErr",editOptions);
          /**
           * Added for the error Messages
           */
          ErrorMessageHelper.setErrMsgToRequest(request, "PS179");
        }

        sCurrTask = NBSContext.getCurrentTask(session);

        request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextAction", tm.get("Submit"));
        request.setAttribute("cancelButtonHref",
                          "/nbs/" + sCurrTask +
                     ".do?ContextAction=" + tm.get("Cancel"));

      convertProviderToRequestObj(providerForm.getProvider(),request,stateList);
      PersonVO perVO = providerForm.getProvider();
      perVO.setThePersonNameDTCollection(null);
      perVO.setTheEntityIdDTCollection(null);
      perVO.setTheEntityLocatorParticipationDTCollection(null);
      providerForm.setProvider(perVO);
      providerForm.reset();
      createXSP(NEDSSConstants.PROVIDER_LDF, perVO.getThePersonDT().getPersonUid(), perVO, null, request ) ;


    }
    /**
     * End of server side Validation
     */
    else{
      //check security for buttons
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS180",
                                             contextAction);
      NBSContext.lookInsideTreeMap(tm);

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("addButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Add"));
      request.setAttribute("editButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Edit"));

      request.setAttribute("deleteButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Inactivate"));
      request.setAttribute("returnToSearchResultsHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" +
                           tm.get("ReturnToSearchResults"));
      request.setAttribute("sCurrTask", sCurrTask);
      }


     PersonVO person = new PersonVO();
	         // use the new API to retrieve custom field collection
	         // to handle multiselect fields (xz 01/11/2005)
			Collection<Object>  coll = extractLdfDataCollection(providerForm, request);
        if (coll!= null)
        {
          person.setTheStateDefinedFieldDataDTCollection(coll);
        }
     /* if ((strUID == null)||(err111!=null)){
         person = (PersonVO) session.getAttribute(
             "tempProviderVO");
       }*/
     if(strUID!=null)
     {
       if (!strUID.equals("")) {
         person = getOldProviderObject(strUID,
                                       providerForm,
                                       session);
       }
     }
        if (person != null) {

          try {
            logger.debug("We are inside View");

            boolean bEditButton = secObj.getPermission(
                NBSBOLookup.PROVIDER,
                NBSOperationLookup.MANAGE);
            boolean bDeleteButton = secObj.getPermission(
                NBSBOLookup.PROVIDER,
                NBSOperationLookup.MANAGE);

            if (person.getThePersonDT().getRecordStatusCd() != null &&
                person.getThePersonDT().getRecordStatusCd()
                .trim().equals(
                NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE)) {
              bEditButton = false;
              bDeleteButton = false;
            }
            if(person.getThePersonDT().getElectronicInd()!= null
            && person.getThePersonDT().getElectronicInd().trim().equalsIgnoreCase(NEDSSConstants.YES))
            {
              bEditButton = false;
              bDeleteButton = false;
            }

            request.setAttribute("deleteButton",
                                 String.valueOf(bDeleteButton));
            request.setAttribute("editButton",
                                 String.valueOf(bEditButton));

            boolean bFileButton = secObj.getPermission(
                NBSBOLookup.ORGANIZATION,
                NBSOperationLookup.VIEWWORKUP);
            request.setAttribute("fileButton",
                                 String.valueOf(bFileButton));

            boolean bAddButton = secObj.getPermission(
                NBSBOLookup.PROVIDER,
                NBSOperationLookup.MANAGE);
            request.setAttribute("addButton",
                                 String.valueOf(bAddButton));
          }
          catch (Exception e) {
            e.printStackTrace();
            logger.error(
                "ProviderLoad: getting permissions for the buttons failed");
          }

          

          convertProviderToRequestObj(person, request,stateList);
          providerForm.setProvider(person);
          prepareAddressCounties(request,stateList);
          createXSP(NEDSSConstants.PROVIDER_LDF, person.getThePersonDT().getPersonUid(), person, null, request ) ;
        }
        else {

        	throw new ServletException();
        }


      return mapping.findForward("XSP");
    }

    //  CREATE
    else if (contextAction.equalsIgnoreCase("Add")) {

      //context
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS178",
                                             contextAction);
      NBSContext.lookInsideTreeMap(tm);
      ErrorMessageHelper.setErrMsgToRequest(request, "ps178");
      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
      request.setAttribute("ContextAction", tm.get("Submit"));
      request.setAttribute("cancelButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Cancel"));

      request.setAttribute("addButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Submit"));
      /* Prepopulating the value that is there in the search Criteria */
  if(sCurrTask.equals("AddProvider1")){
  ProviderSearchVO providerSearchVO = (ProviderSearchVO)NBSContext.retrieve(session,"DSSearchCriteria");
  if (providerSearchVO.getFirstNameOperator() != null && (providerSearchVO.getFirstNameOperator().equals("=") || providerSearchVO.getFirstNameOperator().equals("CT") || providerSearchVO.getFirstNameOperator().equals("SW"))&& providerSearchVO.getFirstName()!=null)

     request.setAttribute("firstNameOne",providerSearchVO.getFirstName());
   if (providerSearchVO.getLastNameOperator() != null && (providerSearchVO.getLastNameOperator().equals("=") || providerSearchVO.getLastNameOperator().equals("CT") || providerSearchVO.getLastNameOperator().equals("SW")) && providerSearchVO.getLastName()!=null)
     request.setAttribute("lastNameOne",providerSearchVO.getLastName());
    if ((providerSearchVO.getStreetAddr1Operator().equals("=") || providerSearchVO.getStreetAddr1Operator().equals("CT"))&& providerSearchVO.getStreetAddr1()!=null )
     request.setAttribute("addressOne",providerSearchVO.getStreetAddr1());
  if(providerSearchVO.getCityDescTxtOperator()!=null)
    if(providerSearchVO.getCityDescTxtOperator().equals("=") || providerSearchVO.getCityDescTxtOperator().equals("CT"))
     request.setAttribute("cityOne",providerSearchVO.getCityDescTxt());
  if(providerSearchVO.getState()!=null)
  {   request.setAttribute("stateOne",providerSearchVO.getState());
    request.setAttribute("addressCounties",
                         getCountiesByState(providerSearchVO.getState()));
  }
  if(providerSearchVO.getZipCd()!=null)
      request.setAttribute("zipOne",providerSearchVO.getZipCd());
  if(providerSearchVO.getTypeCd()!=null)
    request.setAttribute("typeOne",providerSearchVO.getTypeCd());
  if(providerSearchVO.getRootExtensionTxt()!=null)
    request.setAttribute("idValueOne",providerSearchVO.getRootExtensionTxt());

  }


      // need to put something in the county drop down
      request.setAttribute("birthCountiesInState", "+|");
      request.setAttribute("deathCountiesInState", "+|");

      PersonVO perVO1 = new PersonVO();
       createXSP(NEDSSConstants.PROVIDER_LDF, perVO1, null, request ) ;
      return mapping.findForward("XSP");
    }

    //  EDIT
    else if (contextAction.equalsIgnoreCase("EditProvider") ||
             contextAction.equalsIgnoreCase("Edit")) {

      //context
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS179",
                                             contextAction);
      NBSContext.lookInsideTreeMap(tm);
      ErrorMessageHelper.setErrMsgToRequest(request, "ps179");
      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
      request.setAttribute("ContextAction", tm.get("Submit"));
      request.setAttribute("cancelButtonHref",
                           "/nbs/" + sCurrTask +
                           ".do?ContextAction=" + tm.get("Cancel"));

      if (!strUID.equals(null)) {

        PersonVO personVO = getOldProviderObject(
            strUID,
            providerForm,
            session);
        convertProviderToRequestObj(personVO, request,stateList);

        // going to reset the name collection
        personVO.setThePersonNameDTCollection(null);
        personVO.setTheEntityIdDTCollection(null);
        personVO.setTheEntityLocatorParticipationDTCollection(
            null);

        // store the proxy object in the form, so that when we submit
        // form, struts will write directly into the form
        providerForm.setProvider(personVO);
        createXSP(NEDSSConstants.PROVIDER_LDF, personVO.getThePersonDT().getPersonUid(), personVO, null, request ) ;
        return mapping.findForward("XSP");
      }

      else {

    	  throw new ServletException();
      }
    }

    else if (contextAction.equalsIgnoreCase("DeleteDenied")) {
      //context
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS130",
                                             contextAction);
     

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("returnToViewOrganizationHref", "/nbs/" + sCurrTask +
                           ".do?ContextAction=" +
                           tm.get("ReturnToViewOrganization"));


      return mapping.findForward("XSP");
    }
    //  NO OPERATION TYPE
    else {
      return (mapping.findForward("error"));
    }
  }

  /**
   * Gets the addresses in an ArrayList<Object> from  OrganizationVO
   * @param organizationVO   the OrganizationVO
   * @return  addresses      the ArrayList<Object> of addresses
   */
  private ArrayList<Object> getAddressCollection(PersonVO personVO) {

    ArrayList<Object> addresses = new ArrayList<Object> ();

    return addresses;
  }

  /**
   * Gets the telephones in an ArrayList<Object> from OrganizationVO
   * @param organizationVO    the OrganizationVO
   * @return      the ArrayList<Object> of telephones
   */
  private ArrayList<Object> getTelephoneCollection(PersonVO personVO) {


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
    logger.info(
        "InvestigationMeaslesPreAction.formatDate: date: " +
        timestamp);

    if (timestamp != null) {
      date = new Date(timestamp.getTime());

    }
    logger.info("InvestigationMeaslesPreAction.formatDate: date: " +
                date);

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
  private PersonVO getOldProviderObject(String strUID,
                                                  ProviderForm form,
                                                  HttpSession session) {

    PersonVO person = null;
    MainSessionCommand msCommand = null;

    if (strUID != null) {

      try {

        Long UID = new Long(strUID.trim());
        String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        String sMethod = "getProvider";
        Object[] oParams = new Object[] {
            UID};

        //  if(msCommand == null)
        //{
        MainSessionHolder holder = new MainSessionHolder();
        msCommand = holder.getMainSessionCommand(session);

        // }
        ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                                 sMethod, oParams);
        person = (PersonVO) arr.get(0);
        form.setProvider(person);
      }
      catch (NumberFormatException e) {
        logger.error("Error: no provider UID");

        return null;
      }
      catch (Exception ex) {

        if (session == null) {
          logger.error("Error: no session, please login");
        }

        logger.fatal("getOldProviderObject: ", ex);
        ex.printStackTrace();

        return null;
      }
      finally {
        msCommand = null;
      }
    }
    else { // if the request didn't provide the organizationUid, look in form
      person = form.getProvider();
    }

    return person;
  }

  /**
   * Converts the values of OrganizationVO to  HttpServletRequest
   * @param organizationVO   the OrganizationVO
   * @param request     the HttpServletRequest
   */
  private void convertProviderToRequestObj(PersonVO personVO,
                                               HttpServletRequest request , ArrayList<Object> stateList)  {

    logger.info("personVO class is - " + personVO);

    if (personVO != null &&
        personVO.getThePersonDT() != null) {
    	
    	try {

      //logger.debug("organizationVO class is - " + organizationVO);
      PersonDT personDT = personVO.getThePersonDT();

      //for the top bar
      request.setAttribute("personLocalID",
                           personDT.getLocalId());
      personDT.getLocalId();

      //to persist this information for query string or input element
      request.setAttribute("providerUID",
                           String.valueOf(personDT.getUid()));
      personDT.getUid();

      request.setAttribute("lastChgTime",
                           formatDate(personDT.getLastChgTime()));
      request.setAttribute("lastChgUserId",
                           String.valueOf(personDT.getLastChgUserId()));
      request.setAttribute("lastChgReasonCd",
                           personDT.getLastChgReasonCd());
      request.setAttribute("recordStatusCd",
                           personDT.getRecordStatusCd());
      request.setAttribute("description",
                           personDT.getDescription());
      request.setAttribute("adminCd", personDT.getCd());
   

     //set the names attributes
     Collection<Object>  nms = personVO.getThePersonNameDTCollection();
     if(nms != null)
     {
      Iterator<Object>  itname = nms.iterator();
       while (itname.hasNext()) {
         PersonNameDT nameDT = (PersonNameDT) itname.next();
         if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
           request.setAttribute("firstNameOne", nameDT.getFirstNm());
           request.setAttribute("lastNameOne", nameDT.getLastNm());
           request.setAttribute("middleName", nameDT.getMiddleNm());
           request.setAttribute("suffix", nameDT.getNmSuffix());
           request.setAttribute("prefix", nameDT.getNmPrefix());
           request.setAttribute("degree", nameDT.getNmDegree());
           break;
         }
       }
     }

      // set up the roles information
      Collection<Object>  roles = personVO.getTheRoleDTCollection();

      if (roles != null) {

        StringBuffer rolesBuffer = new StringBuffer("");
       Iterator<Object>  iter = roles.iterator();

        if (iter != null) {

          while (iter.hasNext()) {

            RoleDT roleDT = (RoleDT) iter.next();

            if (roleDT != null &&
                roleDT.getRecordStatusCd() != null &&
                roleDT.getRecordStatusCd().equals(
                NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                roleDT.getCd() != null) {
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
     //Set the Quick Code and remove entry from entity id collection
      Collection<Object>  eidColl = personVO.getTheEntityIdDTCollection();
      if(eidColl != null)
      {
       Iterator<Object>  eidCollIter = eidColl.iterator();
        while (eidCollIter.hasNext()) {
          EntityIdDT eidt = (EntityIdDT) eidCollIter.next();

          if (eidt.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
             if(eidt.getRootExtensionTxt()!=null && !eidt.getRootExtensionTxt().trim().equals("") )
            request.setAttribute("quickCode", eidt.getRootExtensionTxt());
            else
               request.setAttribute("quickCode", " ");
          }

        }

          // create the entity id parsed string for the batch entry javascript
        Collection<Object>  ids = personVO.getTheEntityIdDTCollection();

        if (ids != null) {

         Iterator<Object>  iter = ids.iterator();
          StringBuffer combinedIds = new StringBuffer("");

          while (iter.hasNext()) {

            EntityIdDT id = (EntityIdDT) iter.next();

            if (id != null) {

     
              combinedIds.append(
                  "provider.entityIdDT_s[i].typeCd").append(
                  NEDSSConstants.BATCH_PART);

              if (id.getTypeCd() != null) {
                combinedIds.append(id.getTypeCd());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].statusCd").append(
                  NEDSSConstants.BATCH_PART);

      if (id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
      combinedIds.append("I");
       }
      else
       combinedIds.append(id.getStatusCd());

              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].typeDescTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getTypeDescTxt() != null) {
                combinedIds.append(id.getTypeDescTxt());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].assigningAuthorityCd")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getAssigningAuthorityCd() != null) {
                combinedIds.append(id.getAssigningAuthorityCd());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].assigningAuthorityDescTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getAssigningAuthorityDescTxt() != null) {
                combinedIds.append(id.getAssigningAuthorityDescTxt());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].statusCd").append(
                  NEDSSConstants.BATCH_PART);
              if (id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
              combinedIds.append("I");
               }
              else
               combinedIds.append(id.getStatusCd());


              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].rootExtensionTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getRootExtensionTxt() != null) {
                combinedIds.append(id.getRootExtensionTxt());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].validFromTime_s")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getValidFromTime() != null) {
                combinedIds.append(formatDate(id.getValidFromTime()));

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].validToTime_s")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getValidToTime() != null) {
                combinedIds.append(formatDate(id.getValidToTime()));

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].entityIdSeq")
                  .append(NEDSSConstants.BATCH_PART);

              if (id.getEntityIdSeq() != null) {
                combinedIds.append(id.getEntityIdSeq());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  "provider.entityIdDT_s[i].entityUid").append(
                  NEDSSConstants.BATCH_PART);

              if (id.getEntityUid() != null) {
                combinedIds.append(id.getEntityUid().toString());

              }
              combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                  NEDSSConstants.
                  BATCH_LINE);
              // }
            }
          }

          request.setAttribute("ids", combinedIds.toString());

        }
      }

      StringBuffer sParsedAddresses = new StringBuffer("");
      StringBuffer sParsedTeles = new StringBuffer("");
      StringBuffer sParsedLocators = new StringBuffer("");
      Collection<Object>  addresses = personVO.
          getTheEntityLocatorParticipationDTCollection();

      if (addresses != null) {

       Iterator<Object>  iter = addresses.iterator();

        while (iter.hasNext()) {

          EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
              iter.next();

          if (elp != null) {

            if (elp.getStatusCd() != null &&
                elp.getClassCd() != null &&
                elp.getClassCd().equals("PST")) {

              PostalLocatorDT postal = elp.getThePostalLocatorDT();

              sParsedAddresses.append("address[i].cd").append(
                                            NEDSSConstants.BATCH_PART);

              if (elp.getCd() != null)
              sParsedAddresses.append(elp.getCd());

               sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].useCd").append(NEDSSConstants.BATCH_PART);

                if (elp.getUseCd() != null)
                    sParsedAddresses.append(elp.getUseCd());

                sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].statusCd").append(NEDSSConstants.BATCH_PART);

                if (elp.getStatusCd() != null)
                  sParsedAddresses.append(elp.getStatusCd());

                sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].durationUnitCd").append(NEDSSConstants.BATCH_PART);

                if (elp.getDurationUnitCd() != null)
                  sParsedAddresses.append(elp.getDurationUnitCd());

                sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].cdDescTxt").append(NEDSSConstants.BATCH_PART);

                if (elp.getCdDescTxt() != null)
                  sParsedAddresses.append(elp.getCdDescTxt());

                sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.streetAddr1").append(NEDSSConstants.BATCH_PART);

                 if (postal.getStreetAddr1() != null)
                   sParsedAddresses.append(postal.getStreetAddr1());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.streetAddr2").append(NEDSSConstants.BATCH_PART);

                 if (postal.getStreetAddr2() != null)
                   sParsedAddresses.append(postal.getStreetAddr2());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.cityDescTxt").append(NEDSSConstants.BATCH_PART);

                 if (postal.getCityDescTxt() != null)
                   sParsedAddresses.append(postal.getCityDescTxt());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.stateCd").append(NEDSSConstants.BATCH_PART);

                 if (postal.getStateCd() != null){
                   sParsedAddresses.append(postal.getStateCd());
                   if(stateList==null)
                	   stateList = new ArrayList<Object> ();
                   stateList.add(postal.getStateCd());
                 }

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.zipCd").append(NEDSSConstants.BATCH_PART);

                 if (postal.getZipCd() != null)
                   sParsedAddresses.append(postal.getZipCd());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.cntyCd").append(NEDSSConstants.BATCH_PART);

                 if (postal.getCntyCd() != null)
                   sParsedAddresses.append(postal.getCntyCd());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.cntryCd").append(NEDSSConstants.BATCH_PART);

                 if (postal.getCntryCd() != null)
                   sParsedAddresses.append(postal.getCntryCd());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].fromTime_s").append(NEDSSConstants.BATCH_PART);

                 if (elp.getFromTime() != null)
                   sParsedAddresses.append(formatDate(elp.getFromTime()));

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].toTime_s").append(NEDSSConstants.BATCH_PART);

                 if (elp.getToTime() != null)
                   sParsedAddresses.append(formatDate(elp.getToTime()));

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].durationAmt").append(NEDSSConstants.BATCH_PART);

                 if (elp.getDurationAmt() != null)
                   sParsedAddresses.append(elp.getDurationAmt());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].validTimeTxt").append(NEDSSConstants.BATCH_PART);

                 if (elp.getValidTimeTxt() != null)
                   sParsedAddresses.append(elp.getValidTimeTxt());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].locatorDescTxt").append(NEDSSConstants.BATCH_PART);

                 if (elp.getLocatorDescTxt() != null)
                   sParsedAddresses.append(elp.getLocatorDescTxt());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].thePostalLocatorDT_s.postalLocatorUid").append(NEDSSConstants.BATCH_PART);

                 if (postal.getPostalLocatorUid() != null)
                   sParsedAddresses.append(postal.getPostalLocatorUid().toString());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].entityUid").append(NEDSSConstants.BATCH_PART);

                 if (elp.getEntityUid() != null)
                   sParsedAddresses.append(elp.getEntityUid().toString());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append("address[i].locatorUid").append(NEDSSConstants.BATCH_PART);

                 if (elp.getLocatorUid() != null)
                   sParsedAddresses.append(elp.getLocatorUid().toString());

                 sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(NEDSSConstants.BATCH_LINE);


              
            }

            //create the telephone parsed string
            else if (elp.getStatusCd() != null &&
                     elp.getClassCd() != null &&
                     elp.getClassCd().equals("TELE")) {

              TeleLocatorDT tele = elp.getTheTeleLocatorDT();
             
              sParsedTeles.append(
                  "telephone[i].useCd").append(
                  NEDSSConstants.BATCH_PART);

              if (elp.getUseCd() != null) {
                sParsedTeles.append(elp.getUseCd());

              }
              
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append("telephone[i].cd").append(
                  NEDSSConstants.BATCH_PART);

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
                  "telephone[i].durationUnitCd")
                  .append(NEDSSConstants.BATCH_PART);

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
                  "telephone[i].theTeleLocatorDT_s.cntryCd")
                  .append(NEDSSConstants.BATCH_PART);

              if (tele.getCntryCd() != null) {
                sParsedTeles.append(tele.getCntryCd());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.phoneNbrTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (tele.getPhoneNbrTxt() != null) {
                sParsedTeles.append(tele.getPhoneNbrTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.extensionTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (tele.getExtensionTxt() != null) {
                sParsedTeles.append(tele.getExtensionTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.emailAddress")
                  .append(NEDSSConstants.BATCH_PART);

              if (tele.getEmailAddress() != null) {
                sParsedTeles.append(tele.getEmailAddress());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.urlAddress")
                  .append(NEDSSConstants.BATCH_PART);

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
                  "telephone[i].locatorDescTxt")
                  .append(NEDSSConstants.BATCH_PART);

              if (elp.getLocatorDescTxt() != null) {
                sParsedTeles.append(elp.getLocatorDescTxt());

              }
              sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                  "telephone[i].theTeleLocatorDT_s.teleLocatorUid")
                  .append(NEDSSConstants.BATCH_PART);

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
        request.setAttribute("parsedTelephoneString",
                             sParsedTeles.toString());
        request.setAttribute("parsedLocatorsString",
                             sParsedLocators.toString());
      }
		} catch (Exception ex) {
			logger.error("Error in convertProviderToRequestObj: "+ex.getMessage());
			ex.printStackTrace();
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

    
      CachedDropDownValues srtValues = new CachedDropDownValues();
      TreeMap<?,?> treemap = null;
      treemap = srtValues.getCountyCodes(stateCd);

      if (treemap != null) {

        Set<?> set = treemap.keySet();
       Iterator<?>  itr = set.iterator();

        while (itr.hasNext()) {

          String key = (String) itr.next();
          String value = (String) treemap.get(key);
          parsedCodes.append(key.trim()).append(
              NEDSSConstants.SRT_PART).append(value.trim()).append(
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
  private void prepareAddressCounties(HttpServletRequest request ,  ArrayList<Object> stateList) {

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
