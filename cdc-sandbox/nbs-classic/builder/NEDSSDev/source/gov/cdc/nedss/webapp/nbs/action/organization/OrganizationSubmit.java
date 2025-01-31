package gov.cdc.nedss.webapp.nbs.action.organization;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.organization.OrganizationForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 * Title:        OrganizationSubmit.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class OrganizationSubmit
    extends CommonAction {

  protected static NedssUtils nedssUtils = null;
  private static final String USERID =  "5150"; // don't know where to get the real useID

  //For logging
  static final LogUtils logger = new LogUtils(OrganizationSubmit.class.getName());

  public OrganizationSubmit() {
  }

  /**
   * Based on the context action setting  values to the HttpServletRequest objects, storing the objects to the NBSContext
   * and redirecting the control to the appropriate page
   * @param mapping  the ActionMapping
   * @param form     the ActionForm
   * @param request    the HttpServletRequest
   * @param response    the HttpServletResponse
   * @return     the ActionForward
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {

    NBSSecurityObj securityObj = null;
    ActionForward forwardNew = null;
  logger.debug("\n\n\n  Organization Submit");
  try {

	  OrganizationForm organizationForm = (OrganizationForm) form;

	  HttpSession session = request.getSession();

	  if (session == null) {
		  logger.debug("error no session");
		  return mapping.findForward("login");
	  }

	  Object obj = session.getAttribute("NBSSecurityObject");
	  if (obj != null) {
		  securityObj = (NBSSecurityObj) obj;
	  }
	  

	  // are we edit or create?
	  String contextAction = request.getParameter("ContextAction");
	  if (contextAction == null) {
		  contextAction = (String) request.getAttribute("ContextAction");

	  }
	  Long UID = null;

	  String strRequest = request.getParameter("uid");
	  if (strRequest == null) {
		  strRequest = (String) request.getAttribute("uid");
	  }
	  if
	  (strRequest == null || strRequest.equals("")) {

	  }

	  else {
		  try {
			  UID = (request.getParameter("uid") == null
					  ? null : new Long(request.getParameter("uid").trim()));
			  
			  if (UID == null) {
				  UID = (request.getAttribute("uid") == null
						  ? null
								  :
									  new Long( ( (String) request.getAttribute("uid")).trim()));
			  }
		  }

		  catch (java.lang.NumberFormatException e) {

		  }
	  }

	  //  CREATE
	  if (contextAction.equalsIgnoreCase("Submit")) {

		  // we need to determine what kind of submit this is: add or edit
		  // determine this from the current task
		  String sCurrentTask = NBSContext.getCurrentTask(session);

		  /** System.out.println("\n\n\n  Handling Organization Submit and sCurrentTask =" + sCurrentTask);**/
		  OrganizationVO organizationVO = null;

		  if (sCurrentTask == null) {
			  session.setAttribute("error",
					  "current task is null, required for organization submit");

			  throw new ServletException("current task is null, required for organization submit");
		  }
		  else if (sCurrentTask.startsWith("AddOrganization")) {
			  organizationVO = createHandler(organizationForm, securityObj,
					  session, request, response);
		  }

		  else if (sCurrentTask.startsWith("EditOrganization")) {
			  //System.out.println("\n\n inside Edit task ");
			  String editOption = request.getParameter("tba");
			  /**        System.out.println("\n\n TBA Value =" + editOption); **/
			  if( editOption != null)
			  {
				  if (editOption.equalsIgnoreCase("n")) {
					  organizationVO = createHandler(organizationForm, securityObj,
							  session, request, response); 
				  }
				  else {
					  organizationVO = editHandler(organizationForm, securityObj,
							  session, request, response);
				  }

			  }
		  }

		  else {
			  session.setAttribute("error",
					  "didn't find a match for current task for the submit action");

			  throw new ServletException("didn't find a match for current task for the submit action");
		  }

		  try {
			  OrganizationVO tempOrganizationVO = organizationVO;
			  session.setAttribute("tempOrganizatioVO",tempOrganizationVO);
			  request.setAttribute("uid", organizationVO.getTheOrganizationDT().getOrganizationUid().toString());
			  UID = sendProxyToEJB(organizationVO, "setorganization",
					  session);
			  NBSContext.store(session,"DSOrganizationUID",UID);
		  }
		  catch (NEDSSAppConcurrentDataException e) {
			  logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ");
			  return mapping.findForward("dataerror");
		  }
		  catch (NEDSSAppException e) {
			  /**       System.out.println("The quick Code Exception = "+ e.toString()); **/
			  if(e.toString().indexOf("The quickCd is not unique for Organization") != -1)
			  {
				  logger.fatal(
						  "ERROR - NEDSSAppException, the quickCd is not unique for Organization ");
				  request.setAttribute("err111", "Quick Code must be a unique Code");

				  return mapping.findForward("SubmitStayOnError");
			  }

		  }

		  catch (Exception e) {
			  logger.fatal("ERROR , The error has occured, please recheck! ", e);
			  e.printStackTrace();
			  return (mapping.findForward("error"));
		  }

		  if (UID == null) {

			  throw new ServletException();
		  }

		  organizationForm.reset();

	  }

	  /*******************************************
	   * EDIT ACTION
	   */
	  else if (contextAction.equalsIgnoreCase("Edit")) {
	  }

	  /*******************************************
	   * CANCEL ACTION
	   */
	  else if (contextAction.equalsIgnoreCase("Cancel")) {
		  organizationForm.reset();
	  }

	  /********************************************
	   * DELETE ACTION
	   */
	  else if (contextAction.equalsIgnoreCase("Inactivate")) {
		  logger.debug("Beginning the Delete organization Functionality");

		  OrganizationVO organizationVO = null;
		  organizationVO = organizationForm.getOrganization();

		  String result = null;

		  try {

			  result = sendProxyToEJBDelete(organizationVO,
					  "inactivateOrganization", session);
		  }
		  catch (NEDSSAppConcurrentDataException e) {
			  logger.fatal(
					  "ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ");

			  return mapping.findForward("dataerror");
		  }

		  catch (Exception e) {
			  logger.fatal("ERROR , The error has occured, please recheck! ", e);
			  e.printStackTrace();
			  return (mapping.findForward("error"));
		  }
		  if (result.equals("viewDelete")) {

			  if (organizationVO != null &&
					  organizationVO.getTheOrganizationDT() != null) {

				  //organizationVO.setItDelete(true);
				  organizationVO.setItDirty(true);
				  organizationVO.setItNew(false);

				  OrganizationDT organizationDT = organizationVO.getTheOrganizationDT();

				  //organizationDT.setItDelete(true);
				  organizationDT.setItDirty(true);
				  organizationDT.setItNew(false);
				  organizationDT.setStatusCd(
						  NEDSSConstants.STATUS_INACTIVE);
				  organizationDT.setRecordStatusCd(
						  NEDSSConstants.RECORD_STATUS_INACTIVE);

			  }

			  organizationForm.reset();

			  return mapping.findForward("Inactivate");
		  }
		  else if (result.equals("deleteDenied")) {

			  contextAction = "DeleteDenied";
		  }
		  else if (result.equals("error")) {
			  session.setAttribute("error", "there is no context action");

			  throw new ServletException("There is no context action");
		  }
		  else {
			  session.setAttribute("error", "there is no context action");

			  throw new ServletException("There is no context action");
		  }
	  }

	  /*******************************************
	   * ReturnToSearchResults action
	   */
	  else if (contextAction.equalsIgnoreCase("ReturnToSearchResults")) {

	  }
	  ActionForward af = mapping.findForward(contextAction);

	  forwardNew = new ActionForward();
	  //  System.out.println("\n\n Action Forward from orgSubmit = " + af);
	  StringBuffer strURL = new StringBuffer(af.getPath());
	  strURL.append("?ContextAction=" + contextAction +
			  "&uid=").append(UID);
	  forwardNew.setPath(strURL.toString());
	  forwardNew.setRedirect(true);


  }catch (Exception e) {
	  logger.error("Exception in Organization Submit: " + e.getMessage());
	  e.printStackTrace();
	  throw new ServletException("General error occurred in Organization Submit : "+e.getMessage());
  }   
    
    return forwardNew;
  }

  /**
   * Sets the values for the attributes of OrganizationVO while creting the organization
   * @param organizationForm    the OrganizationForm
   * @param securityObj     the NBSSecurityObj
   * @param session    the HttpSession
   * @param request    the HttpServletRequest
   * @param response     the HttpServletResponse
   * @return organizationVO      the OrganizationVO
   */
  private OrganizationVO createHandler(OrganizationForm organizationForm,
                                       NBSSecurityObj securityObj,
                                       HttpSession session,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

    OrganizationVO organizationVO = null;

    try {
      organizationVO = organizationForm.getOrganization();
//      System.out.println("\n\n The entity id Collection<Object>  in Create Handler "+organizationVO.getTheEntityIdDTCollection().size());
      organizationVO.setItNew(true);
      organizationVO.setItDirty(false);

      // set up the DT for the EJB
      OrganizationDT organizationDT = organizationVO.getTheOrganizationDT();
      organizationDT.setItNew(true);
      organizationDT.setItDirty(false);
      organizationDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
      organizationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      organizationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      organizationDT.setVersionCtrlNbr(new Integer(1));
      organizationDT.setSharedInd("T");
      organizationDT.setOrganizationUid(new Long( -1));
      organizationDT.setAddTime(new Timestamp(new Date().getTime()));
      organizationDT.setLastChgTime(new Timestamp(new Date().getTime()));
      organizationDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
      organizationDT.setStatusTime(new Timestamp(new Date().getTime()));

      // set up the organization names dt
      setName(organizationVO, organizationForm.getName());
      setIds(organizationVO,organizationForm);
      setAddresses(organizationVO, organizationForm.getAddressCollection());
      setTelephones(organizationVO,
                    organizationForm.getTelephoneCollection());
      setRoles(organizationVO, request);

     if(organizationForm.getLdfCollection()!= null)
     {
         // use the new API to retrieve custom field collection
         // to handle multiselect fields (xz 01/11/2005)
         ArrayList<Object> list = extractLdfDataCollection(organizationForm, request);
       ArrayList<Object> LDFlist = new ArrayList<Object> ();
      Iterator<Object>  it =  list.iterator();
       while(it.hasNext())
       {
         StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
         stateDT.setItDirty(false);
         LDFlist.add(stateDT);
       }
       organizationVO.setTheStateDefinedFieldDataDTCollection(LDFlist);
     }
  

    }
    catch (Exception e) {
    	logger.error("Exception in OrganizationSubmit.createHandler: " + e.getMessage());
      e.printStackTrace();
    }
 
    return organizationVO;
  }

  /**
   * Sets the values for the attributes of OrganizationVO while editing the organization
   * @param organizationForm     the OrganizationForm
   * @param securityObj    the NBSSecurityObj
   * @param session    the HttpSession
   * @param request    the HttpServletRequest
   * @param response   the HttpServletResponse
   * @return organizationVO   the OrganizationVO
   */
  private OrganizationVO editHandler(OrganizationForm organizationForm,
                                     NBSSecurityObj securityObj,
                                     HttpSession session,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

    OrganizationVO organizationVO = null;

    try {
      organizationVO = organizationForm.getOrganization();


        organizationVO.getTheOrganizationDT().setItDirty(true);
        organizationVO.getTheOrganizationDT().setItNew(false);
        organizationVO.setItDirty(true);
        organizationVO.setItNew(false);
        setNameUpdate(organizationVO, organizationForm.getName());

      setIds(organizationVO,organizationForm);
      // setAddress(organizationVO, organizationForm.getAddress());

      setAddresses(organizationVO, organizationForm.getAddressCollection());
      setTelephones(organizationVO,
                    organizationForm.getTelephoneCollection());
      setRoles(organizationVO, request);
    // set up the ldf collection

    if(organizationForm.getLdfCollection()!= null)
    {
        // use the new API to retrieve custom field collection
        // to handle multiselect fields (xz 01/11/2005)
        ArrayList<Object> list = extractLdfDataCollection(organizationForm, request);
      ArrayList<Object> LDFlist = new ArrayList<Object> ();
     Iterator<Object>  it =  list.iterator();
      while(it.hasNext())
      {
        StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
        stateDT.setItDirty(true);
        LDFlist.add(stateDT);
      }
      organizationVO.setTheStateDefinedFieldDataDTCollection(LDFlist);

    }


    }
    catch (Exception e) {
      logger.error("Exception in Org Submit: " + e.getMessage());
      e.printStackTrace();
    }

    return organizationVO;
  }

  /**
   * Sets values to the atttributes of Ids in the OrganizationVO
   * @param organizationVO   the OrganizationVO
   */
  private void setIds(OrganizationVO organizationVO,OrganizationForm organizationForm) {

    Collection<Object>  ids = organizationVO.getTheEntityIdDTCollection();
    Long organizationUID = organizationVO.getTheOrganizationDT().
        getOrganizationUid();
      boolean isQuickCode = false;
          String quickCode = null;
          if(organizationForm.getQuickCodeIdDT()!=null)
             quickCode = organizationForm.getQuickCodeIdDT().getRootExtensionTxt();

          if (ids != null)
          {

             Iterator<Object>  itrCount = ids.iterator();

              //need to find the max seq nbr for existing names
              Integer maxSeqNbr = new Integer(0);

              while (itrCount.hasNext())
              {

               EntityIdDT idDT = (EntityIdDT)itrCount.next();

               if (idDT.getEntityIdSeq() != null && idDT.getEntityIdSeq().longValue()>0)
               {

                   if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) // update the maxSeqNbr when you find a bigger one
                    maxSeqNbr = idDT.getEntityIdSeq();
               }
              }

             Iterator<Object>  itrIds = ids.iterator();

              while (itrIds.hasNext())
              {

               EntityIdDT id = (EntityIdDT)itrIds.next();

               if (id.getEntityUid() == null || id.getEntityUid().longValue()==0) // this is a new one
               {
                   maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
                   id.setEntityIdSeq(maxSeqNbr);

                   //id.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                   if (id.getStatusCd() != null &&
                    id.getStatusCd().equals(
                         NEDSSConstants.STATUS_ACTIVE))
                    id.setRecordStatusCd(
                         NEDSSConstants.RECORD_STATUS_ACTIVE);
                   else
                    id.setRecordStatusCd(
                         NEDSSConstants.RECORD_STATUS_INACTIVE);

                   id.setItNew(true);
                   id.setItDirty(false);
                   id.setAddTime(new Timestamp(new Date().getTime()));
                   id.setEntityUid(organizationUID);
               }
               else { //this is old one
                    //check if ssn exists in the collection already
                    if (id.getTypeCd() != null && id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
                       isQuickCode = true;
                       id.setRootExtensionTxt(quickCode);
                       id.setItNew(false);
                       id.setItDirty(true);
                       id.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                       id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                    }
                    else if (id.getStatusCd() != null &&
                             id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                       id.setItNew(false);
                       id.setItDirty(true);
                       id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

                    }
                    else {
                       id.setItNew(false);
                       id.setItDelete(true);
                       id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
                    }

                 }
                 id.setLastChgTime(new Timestamp(new Date().getTime()));
              }
              if (isQuickCode == false && quickCode != null && !quickCode.trim().equals("")) {
                 EntityIdDT iddt = null;
                 iddt = new EntityIdDT();
                 maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
                 iddt.setEntityIdSeq(maxSeqNbr);
                 iddt.setAddTime(new Timestamp(new Date().getTime()));
                 iddt.setLastChgTime(new Timestamp(new Date().getTime()));
                 iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
                 iddt.setStatusTime(new Timestamp(new Date().getTime()));
                 iddt.setEntityUid(organizationUID);
                 iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                 iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                 iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
                 iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
                 iddt.setRootExtensionTxt(quickCode);
                 ids.add(iddt);
              }

            }
            else if (quickCode != null && !quickCode.trim().equals("")) {
             EntityIdDT iddt = null;
             iddt = new EntityIdDT();
             iddt.setEntityIdSeq(new Integer(0));
             iddt.setAddTime(new Timestamp(new Date().getTime()));
             iddt.setLastChgTime(new Timestamp(new Date().getTime()));
             iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
             iddt.setStatusTime(new Timestamp(new Date().getTime()));
             iddt.setEntityUid(organizationUID);
             iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
             iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
             iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
             iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
             iddt.setRootExtensionTxt(quickCode);
             ArrayList<Object> idList = new ArrayList<Object> ();
             idList.add(iddt);
             organizationVO.setTheEntityIdDTCollection(idList);
          }
          PersonUtil.setAssigningAuthorityforIds(organizationVO.getTheEntityIdDTCollection(),NEDSSConstants.EI_AUTH_ORG);
  }
  private OrganizationVO setquickCode(OrganizationVO organizationVO,OrganizationForm organizationForm)
  {
    if (organizationForm.getQuickCodeIdDT() != null && organizationForm.getQuickCodeIdDT().getRootExtensionTxt()!=null && !organizationForm.getQuickCodeIdDT().getRootExtensionTxt().trim().equals("")) {
      EntityIdDT iddt = new EntityIdDT();
      iddt.setAddTime(new Timestamp(new Date().getTime()));
      iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      iddt.setRecordStatusCd(NEDSSConstants.ACTIVE);
      iddt.setLastChgTime(new Timestamp(new Date().getTime()));
      iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
      iddt.setStatusTime(new Timestamp(new Date().getTime()));
      iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
      iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
      iddt.setRootExtensionTxt(organizationForm.getQuickCodeIdDT().
                               getRootExtensionTxt());
      iddt.setEntityIdSeq(new Integer(0));
      iddt.setEntityUid(organizationVO.getTheOrganizationDT().
                        getOrganizationUid());
      if (organizationVO.getTheEntityIdDTCollection() != null)

        organizationVO.getTheEntityIdDTCollection().add(iddt);
      else {
        ArrayList<Object> idList = new ArrayList<Object> ();
        idList.add(iddt);
        organizationVO.setTheEntityIdDTCollection(idList);
      }

    }
    return organizationVO;
  }


  /**
   * Sets the attributes of the NameDT object in the  OrganizationVO
   * @param organizationVO   the OrganizationVO
   * @param name    the OrganizationNameDT
   */

  private void setName(OrganizationVO organizationVO,
                       OrganizationNameDT name) {

    Long organizationUID = organizationVO.getTheOrganizationDT().
        getOrganizationUid();
 //   System.out.println("The organization  Uid from  setNamr ="+organizationUID);
     if(name!=null && name.getNmTxt()!=null && name.getNmUseCd()!=null && name.getNmTxt().trim().equals("") && name.getNmUseCd().trim().equals("") ){
                     return;
      }

    ArrayList<Object> arrName = (ArrayList<Object> ) organizationVO.
        getTheOrganizationNameDTCollection();

    if (arrName == null) {
      arrName = new ArrayList<Object> ();
    }

    if (name != null) {
      name.setItNew(true);
      name.setItDirty(false);
      name.setOrganizationNameSeq(new Integer(0));
      name.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      name.setOrganizationUid(organizationUID);
      arrName.add(name);
    }

    organizationVO.setTheOrganizationNameDTCollection(arrName);
  }

  /**
   * sets the values for the attributes of the OrganizationNameDT in OrganizationVO for update
   * @param organizationVO   the OrganizationVO
   * @param name    the OrganizationNameDT
   */
  private void setNameUpdate(OrganizationVO organizationVO,
                             OrganizationNameDT name) {

    Long organizationUID = organizationVO.getTheOrganizationDT().
        getOrganizationUid();


    ArrayList<Object> arrName = (ArrayList<Object> ) organizationVO.
        getTheOrganizationNameDTCollection();

    if (arrName == null) {
      arrName = new ArrayList<Object> ();
    }

    if (name != null) {
      name.setItNew(false);
      name.setItDirty(true);
      name.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      name.setOrganizationUid(organizationUID);
      arrName.add(name);
    }

    organizationVO.setTheOrganizationNameDTCollection(arrName);
  }


  /**
   * Sets the values to the attributes of the EntityLocatorParticipationDTs in OrganizationVO
   * @param organizationVO   the OrganizationVO
   * @param addressList  the ArrayList<Object> of address
   */
  private void setAddresses(OrganizationVO organizationVO,
			ArrayList<Object> addressList) {

		//##!! System.out.println("We are inside the AddressCollection");
		Long organizationUID = organizationVO.getTheOrganizationDT()
				.getOrganizationUid();

		if (addressList != null) {

			Iterator<Object> itrAddress = addressList.iterator();

			ArrayList<Object> arrELP = (ArrayList<Object> ) organizationVO
					.getTheEntityLocatorParticipationDTCollection();

			if (arrELP == null) {
				arrELP = new ArrayList<Object> ();

			}
			while (itrAddress.hasNext()) {

				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) itrAddress
						.next();

				if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) {
					if (elp.getStatusCd() != null
							&& elp.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)) {
						elp.setItNew(true);
						elp.setItDirty(false);
						elp.getThePostalLocatorDT().setItNew(true);
						elp.setClassCd("PST");
						elp.getThePostalLocatorDT().setItDirty(false);
						elp.setEntityUid(organizationUID);
						//elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						//elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						arrELP.add(elp);
					}
				} else {
					elp.setItNew(false);
					elp.setItDirty(true);
					elp.getThePostalLocatorDT().setItNew(false);
					elp.getThePostalLocatorDT().setItDirty(true);
					elp.setClassCd("PST");
					//Narendra - 03/18/04 Check if the statusCd is 'I', set the
					// recordStatusCd to 'INACTIVE'

					if (elp.getStatusCd() != null
							&& elp.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE))
						elp
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					else
						elp
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

					arrELP.add(elp);
				}
			}
			organizationVO.setTheEntityLocatorParticipationDTCollection(arrELP);
			gov.cdc.nedss.util.VOTester.createReport(organizationVO,
					"address call-store-pre");
		}
	}

  /**
   * Setting the attributes of the PostalLocatorDT and the
   * EntityLocatorParticipationDT
   * 
   * @param organizationVO
   *            the OrganizationVO
   * @param address
   *            the EntityLocatorParticipationDT
   */
  private void setAddress(OrganizationVO organizationVO,
                          EntityLocatorParticipationDT address) {

    Long organizationUID = organizationVO.getTheOrganizationDT().
        getOrganizationUid();
    PostalLocatorDT postal = address.getThePostalLocatorDT();

    if (postal != null && postal.getCityCd() != null &&
        postal.getStateCd() != null && postal.getCntyCd() != null &&
        postal.getCntryCd() != null &&
        postal.getStreetAddr1() != null &&
        postal.getStreetAddr2() != null &&
        postal.getZipCd() != null &&
        address.getLocatorDescTxt() != null &&
        postal.getCityCd().trim().equals("") &&
        postal.getStateCd().trim().equals("") &&
        postal.getCntyCd().equals("") &&
        postal.getCntryCd().trim().equals("") &&
        postal.getStreetAddr1().trim().equals("") &&
        postal.getStreetAddr2().trim().equals("") &&
        postal.getZipCd().trim().equals("") &&
        address.getLocatorDescTxt().trim().equals("")
        )

    {

      return;
    }

    ArrayList<Object> arrELP = (ArrayList<Object> ) organizationVO.
        getTheEntityLocatorParticipationDTCollection();

    if (arrELP == null) {
      arrELP = new ArrayList<Object> ();
    }

    if (address.getLocatorUid() == null || address.getLocatorUid().longValue()==0) {
      address.setItNew(true);
      address.setItDirty(false);
      address.getThePostalLocatorDT().setItNew(true);
      address.getThePostalLocatorDT().setItDirty(false);
      address.setClassCd("PST");
      address.setEntityUid(organizationUID);
      address.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      address.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      arrELP.add(address);
    }
    else {
      address.setItNew(false);
      address.setItDirty(true);
      address.getThePostalLocatorDT().setItNew(false);
      address.getThePostalLocatorDT().setItDirty(true);
      address.setClassCd("PST");
      address.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      address.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      arrELP.add(address);
    }

    organizationVO.setTheEntityLocatorParticipationDTCollection(arrELP);

  }

  /**
   * Setting the attributes of the  TeleLocatorDTs and the corresponding  EntityLocatorParticipationDTs
   * @param organizationVO    the OrganizationVO
   * @param telephoneList    an ArrayList<Object> of EntityLocatorParticipationDTs
   */

  private void setTelephones(OrganizationVO organizationVO,
			ArrayList<Object> telephoneList) {

		if (telephoneList != null) {

			Long organizationUID = organizationVO.getTheOrganizationDT()
					.getOrganizationUid();
			Iterator<Object> itr = telephoneList.iterator();
			ArrayList<Object> arrELP = (ArrayList<Object> ) organizationVO
					.getTheEntityLocatorParticipationDTCollection();

			if (arrELP == null) {
				arrELP = new ArrayList<Object> ();

			}
			while (itr.hasNext()) {

				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) itr
						.next();

				if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) { // new one
					if (elp.getStatusCd() != null
							&& elp.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)) {
						elp.setClassCd("TELE");
						elp.setItNew(true);
						elp.setItDirty(false);
						elp.getTheTeleLocatorDT().setItNew(true);
						elp.getTheTeleLocatorDT().setItDirty(false);
						elp.setEntityUid(organizationUID);
						//##!! System.out.println("VAlue of statusCode New
						// Telephone: " + elp.getStatusCd());
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						arrELP.add(elp);
					}
				} else {
					elp.setClassCd("TELE");
					elp.setItNew(false);
					elp.setItDirty(true);
					elp.getTheTeleLocatorDT().setItNew(false);
					elp.getTheTeleLocatorDT().setItDirty(true);
					//##!! System.out.println("VAlue of statusCode Edit
					// Telephone: " + elp.getStatusCd());
					if (elp.getStatusCd() != null
							&& elp.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)) {
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					} else {
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
					}
					arrELP.add(elp);
				}
			}
			organizationVO.setTheEntityLocatorParticipationDTCollection(arrELP);
		}
	}

  /**
   * Setting the attributes of RoleDT objects in OrganizationVO
   * 
   * @param organizationVO
   *            the OrganizationVO
   * @param request
   *            the HttpServletRequest
   */

  private void setRoles(OrganizationVO organizationVO,
                        HttpServletRequest request) {

    Long organizationUID = organizationVO.getTheOrganizationDT().
        getOrganizationUid();
    String[] arrRoles = request.getParameterValues("rolesList");
    Long maxSeqNbr = new Long(0);
    ArrayList<Object> roleList = new ArrayList<Object> ();

    Collection<Object>  roleColl = organizationVO.getTheRoleDTCollection();
    if (roleColl != null) {
     Iterator<Object>  iter = roleColl.iterator();
      if (iter != null) {
        while (iter.hasNext()) {
          RoleDT currRoleDT = (RoleDT) iter.next();
          if (currRoleDT != null) {
            currRoleDT.setItNew(false);
            currRoleDT.setItDelete(true);
            currRoleDT.setItDirty(false);
            roleList.add(currRoleDT);
          }
        }
      }
    }

    if (arrRoles != null) {
      for (int i = 0, len = arrRoles.length; i < len; i++) {

        String strVal = arrRoles[i];
        if (! (strVal.length() == 0)) {
          RoleDT roleDT = new RoleDT();
          roleDT.setRecordStatusCd(
              NEDSSConstants.RECORD_STATUS_ACTIVE);
          roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          roleDT.setSubjectEntityUid(organizationUID);
          roleDT.setSubjectClassCd(NEDSSConstants.ORGANIZATION);
          roleDT.setCd(strVal);
          roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          roleDT.setStatusTime(new Timestamp(new Date().getTime()));
          roleDT.setAddUserId(new Long(USERID));
          roleDT.setLastChgUserId(new Long(USERID));
          maxSeqNbr = new Long(maxSeqNbr.intValue() + 1);
          roleDT.setRoleSeq(maxSeqNbr);
          roleDT.setItNew(true);
          roleDT.setItDelete(false);
          roleDT.setItDirty(false);
          roleList.add(roleDT);
        }
      }
    }

    organizationVO.setTheRoleDTCollection(roleList);

  }

  /**
   * Sending OrganizationVO to the database through setOrganization method of EntityProxyEJB
   * @param organization  the OrganizationVO
   * @param paramMethodName     the String
   * @param session   the HttpSession
   * @return      long
   * @throws NEDSSAppConcurrentDataException
   * @throws RemoteException
   * @throws EJBException
   * @throws Exception
   */
  private Long sendProxyToEJB(OrganizationVO organization,
                              String paramMethodName, HttpSession session) throws
      NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException,NEDSSSystemException, Exception {

    /**
     * Call the mainsessioncommand
     */
   

    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "setOrganization";
    Object[] oParams = {
        organization};

    if (msCommand == null) {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
    }

    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
                                              oParams);


    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
 
      Long result = (Long) resultUIDArr.get(0);
       //Reload QuickEntry Cache
		ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(true,"PRV");
		session.setAttribute("qecListORG", qecList);	
      return result;
    }
    else {

      return null;
    }
  }

  /**
   * Sending the organizationUID to database to set the values to delete
   * by using the deleteOrganization in  EntityProxyEJB
   * @param orgUID   the Long
   * @param paramMethodName   the Atring
   * @param session   the HttpSession
   * @return     the String
   * @throws Exception
   * @throws NEDSSAppConcurrentDataException
   */

  private String sendProxyToEJBDelete(OrganizationVO organizationVO,
                                      String paramMethodName,
                                      HttpSession session) throws Exception,
      NEDSSAppConcurrentDataException {

    /**
     * Call the mainsessioncommand
     */
    MainSessionCommand msCommand = null;

    //try
    // {
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = paramMethodName;

    //sBeanJndiName = "EntityProxyEJBRef";
    sMethod = "inactivateOrganization";
    Long organizationUID = null;

    Object[] oParams = {
        organizationVO};

    // if(msCommand == null)
    // {
    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);

    // }
    ArrayList<?> resultArr = new ArrayList<Object> ();
    resultArr = msCommand.processRequest(sBeanJndiName, sMethod,
                                         oParams);
    logger.debug("value of resultArr1 " + resultArr);

    boolean result;
    String deleteFlag = "";

    if ( (resultArr != null) && (resultArr.size() > 0)) {
      result = ( (Boolean) resultArr.get(0)).booleanValue();

      if (result) {
        deleteFlag = "viewDelete";
      }
      else {
        deleteFlag = "deleteDenied";
      }

      return deleteFlag;
    }
    else {
      deleteFlag = "error";

      return deleteFlag;
    }
  }
}