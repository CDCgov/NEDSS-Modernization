/**
 * Title:        PermissionSetLoad
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      CSC
 * @author: Brent Chen
 * @version 1.0
 */

package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.action.message.MessageQueueAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;


public class PermissionSetLoad extends Action
{
   public PermissionSetLoad()
   {
   }
   static final LogUtils         logger       = new LogUtils(PermissionSetLoad.class.getName());
   
   private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
   public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
				throws IOException, ServletException
   {
       ErrorMessageHelper.setErrMsgToRequest(request, "ps147");
       PermissionSetForm perSetForm = (PermissionSetForm)form;

       HttpSession session = request.getSession(false);
       if (session == null)
       {
    	   throw new ServletException("session is null");
       }

       try {
    	   NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
    	   String strType = (String)request.getParameter("OperationType");
    	   String sObjectType = (String)request.getParameter("ObjectType");
    	   String perSetName = (String)request.getParameter("RoleName");
    	   if(perSetName!=null)
    		   perSetName = XMLRequestHelper.urlDecode(perSetName);
    	   String templatePermSet = (String)request.getParameter("permSets");
    	   if(templatePermSet!=null)
    		   templatePermSet = XMLRequestHelper.urlDecode(templatePermSet);
    	   String templateFlag = (String)request.getParameter("input");
    	   String templatePermSetFromViewScreen = (String)request.getParameter("permSetAsTemplate");
    	   if(templatePermSetFromViewScreen!=null)
    		   templatePermSetFromViewScreen = XMLRequestHelper.urlDecode(templatePermSetFromViewScreen);
    	   String templateFlagFromViewScreen = (String)request.getParameter("templateFlag");
    	   //backtrack variable is used if user tries to create a PermissionSet without a unique name
    	   //This will indicate that the user needs to go back to the create screen but
    	   //we want the values they entered to populate so they don't have to do it again.
    	   String backTrack = (String)request.getParameter("BackTrack");

    	   //Check user's permission to admin system security
    	   boolean msaPermit = secObj.isUserMSA();
    	   boolean paaPermit = secObj.isUserPAA();
    	   boolean permit = false;
    	   if(msaPermit || paaPermit)
    	   {
    		   permit = true;
    	   }

    	   if(!permit)
    	   {
    		   throw new ServletException();
    	   }
    	   request.setAttribute("permit", String.valueOf(permit));
    	   request.setAttribute("isUserPAA", String.valueOf(paaPermit));

    	   //This block of code is for when you are creating a new Permission Set from an old one
    	   //as a template
    	   if(permit){
    		   if(templateFlag!=null && templateFlag.equals("y")){
    			   if(strType.equalsIgnoreCase(NEDSSConstants.CREATE) && (templatePermSet==null|| templatePermSet.equals(""))){
    				   ActionForward af = mapping.findForward("reselect");
    				   ActionForward forwardReselect = new ActionForward();
    				   String strURL = af.getPath();
    				   strURL = strURL + "?OperationType=" + NEDSSConstants.SELECT_PERMISSION_SET;
    				   request.setAttribute("ERR001", "Please select a Permission Set and try again.");
    				   request.setAttribute("ERR015", "One or more fields are not valid. You may need to scroll down the page to see the fields and error messages. Please make the requested changes and try again.");

    				   forwardReselect.setPath(strURL);
    				   forwardReselect.setRedirect(false);
    				   return forwardReselect;
    			   }
    			   else{
    				   perSetForm.reset();
    				   PermissionSet perSet = getOldPermissionSet(templatePermSet, perSetForm, session);
    				   request.setAttribute("OperationType", NEDSSConstants.CREATE);
    				   byte[] opAr = perSet.getBusinessObjectOperations();

    				   if(perSet != null)
    				   {
    					   boolean converted = convertPermissionSetToRequestObj(perSet, request);

    					   if(!converted)
    					   {
    						   return (mapping.findForward("error"));
    					   }
    					   else if(converted)
    					   {
    						   request.removeAttribute("RoleName");
    						   request.removeAttribute("RoleDescription");
    						   return (mapping.findForward("create"));
    					   }
    				   }
    			   }
    		   }
    	   }

    	   //This block of code is for when you are creating a new Permission Set from an old one
    	   //as a template, but from the View Permission Set screen
    	   if(permit){
    		   if(templateFlagFromViewScreen!=null && templateFlagFromViewScreen.equals("y")){
    			   perSetForm.reset();
    			   PermissionSet perSet = getOldPermissionSet(templatePermSetFromViewScreen, perSetForm, session);
    			   request.setAttribute("OperationType", NEDSSConstants.CREATE);
    			   byte[] opAr = perSet.getBusinessObjectOperations();

    			   if(perSet != null)
    			   {
    				   boolean converted = convertPermissionSetToRequestObj(perSet, request);

    				   if(!converted)
    				   {
    					   return (mapping.findForward("error"));
    				   }
    				   else if(converted)
    				   {
    					   request.removeAttribute("RoleName");
    					   request.removeAttribute("RoleDescription");
    					   return (mapping.findForward("create"));
    				   }
    			   }
    		   }
    	   }
    	   if(permit){
    		   if(backTrack!=null && backTrack.equals("y"))
    		   {
    			   PermissionSet perSet =createHandler(perSetForm, secObj, session, request, response);
    			   if(perSet != null)
    			   {
    				   boolean converted = convertPermissionSetToRequestObj(perSet, request);

    				   if(!converted)
    				   {
    					   return (mapping.findForward("error"));
    				   }
    				   else if(converted)
    				   {
    					   request.removeAttribute("RoleName");
    					   request.setAttribute("errorMessage", "This Permission Set Name is already in use.");
    					   request.setAttribute(PageConstants.OPERATIONTYPE, NEDSSConstants.CREATE);
    					   return (mapping.findForward("create"));
    				   }
    			   }
    		   }
    	   }

    	   //Retrieves the permission set
    	   if(permit && strType.equalsIgnoreCase(NEDSSConstants.VIEW))
    	   {
    		   perSetForm.reset();
    		   PermissionSet perSet = getOldPermissionSet(perSetName, perSetForm, session);
    		   request.setAttribute("OperationType", NEDSSConstants.VIEW);
    		   byte[] opAr = perSet.getBusinessObjectOperations();

    		   if(perSet != null)
    		   {
    			   boolean converted = convertPermissionSetToRequestObj(perSet, request);

    			   if(!converted)
    			   {
    				   return (mapping.findForward("error"));
    			   }
    			   else if(converted)
    			   {
    				   request.setAttribute("editButtonHref",
    						   "loadPermissionSet.do?" +
    								   PageConstants.OPERATIONTYPE +"=" + NEDSSConstants.EDIT);
    				   request.setAttribute(PageConstants.OPERATIONTYPE, NEDSSConstants.VIEW);
    				   return (mapping.findForward("view"));
    			   }
    		   }
    	   }
    	   else if (permit && strType.equalsIgnoreCase(NEDSSConstants.CREATE)&& paaPermit)
    	   {
    		   perSetForm.reset();

    		   request.setAttribute("OperationType", NEDSSConstants.CREATE);
    		   request.setAttribute("ReadOnly", "false");
    		   return (mapping.findForward("create"));
    	   }
    	   else if (permit && strType.equalsIgnoreCase(NEDSSConstants.EDIT)&& paaPermit)
    	   {
    		   PermissionSet oldSet = getOldPermissionSet(perSetName,perSetForm, session);
    		   boolean converted = convertPermissionSetToRequestObj(oldSet, request);

    		   request.setAttribute("OperationType", NEDSSConstants.EDIT);
    		   request.setAttribute("ReadOnly", "false");
    		   perSetForm.setPermissionSet(oldSet);

    		   if(!converted)
    		   {
    			   return (mapping.findForward("error"));
    		   }
    		   else
    		   {
    			   return (mapping.findForward("edit"));
    		   }
    	   }
       }catch (Exception e) {
    	   logger.error("Exception in Permission Set Load: " + e.getMessage());
    	   e.printStackTrace();
    	   throw new ServletException("Error occurred in Permission Set Load : "+e.getMessage());
       }
       return (mapping.findForward("error"));
  }

  private PermissionSet getOldPermissionSet(String perSetName, PermissionSetForm perSetForm, HttpSession session)
  {
      PermissionSet set = new PermissionSet();;
      //SecurePermissionSetDT securePermDTset = null;
      MainSessionCommand msCommand = null;

      if (perSetName != null)
      {
	try
	{
		String  sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
	    String sMethod = "getPermissionSet";
	    Object[] oParams = new Object[]{perSetName};
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    set = (PermissionSet)arr.get(0);
	    //setPermissionSet(securePermDTset,set);
	    perSetForm.setOldPermissionSet(set);
	    perSetForm.setPermissionSet(set);
	    perSetForm.setLoadedFromDB(true);
       }
       catch(Exception ex)
       {
			logger.error("Error in getOldPermissionSet: "+ex.getMessage());
			ex.printStackTrace();
	 	   throw new NEDSSSystemException("Error while calling the getPermissionSet method "+ex.getMessage());
       }
    }
    else
    {
	//If the request didn't povide the perSetName, look in form
	set = perSetForm.getPermissionSet();
    }
    return set;
  }

private boolean convertPermissionSetToRequestObj(PermissionSet perSet, HttpServletRequest request)
{
    boolean returnValue = false;

    HttpSession session = request.getSession(false);
    NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

    if(perSet != null)
    {
	try
	{
	    //Convert string and primitive attributes
	    request.setAttribute("RoleName", perSet.getRoleName());
	    request.setAttribute("RoleDescription", perSet.getDescription());
	    request.setAttribute("ReadOnly", new Boolean(perSet.getReadOnly()));

	    //Convert operation numeric value into "U" and "G" for user and guest
	    convertOperationsToRequestObj(perSet, request);

	    returnValue = true;
	}
	catch(Exception ex)
	{
	}
    }
    return returnValue;
  }

  private PermissionSet createHandler(PermissionSetForm psForm,
						NBSSecurityObj securityObj,
					      HttpSession session,
					      HttpServletRequest request,
					      HttpServletResponse response)   {
      PermissionSet ps = psForm.getPermissionSet();
      ps.setReadOnly(false);
      byte[] opArray = new byte[BusinessObjOperationUtil.getBusinessObjOperationStoreSize()];
      makeArray(opArray, request);
      ps.setBusinessObjectOperations(opArray);
      ps.setItDirty(false);
      ps.setItNew(true);
      return ps;
  }

  private void convertOperationsToRequestObj(PermissionSet perSet, HttpServletRequest request)
  {
      byte[] opArray = perSet.getBusinessObjectOperations();//All operations
      //System admin attribute
/*    request.setAttribute("SecAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.SECURITYADMINISTRATION)], true));
      request.setAttribute("SecAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.SECURITYADMINISTRATION)], false));
*/
      request.setAttribute("SecAdminUser", "U");
      request.setAttribute("SecAdminGuest", "G");
      //LDF admin attribute
      request.setAttribute("LDFAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION)], true));
      request.setAttribute("LDFAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION)], false));

    //Epilink admin attribute
      request.setAttribute("EpilinkAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)], true));
      request.setAttribute("EpilinkAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)], false));

      //LDF admin attribute
      request.setAttribute("GeocodingUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.GEOCODING)], true));
      request.setAttribute("GeocodingGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.GEOCODING)], false));

      //Report Admin attribute
      request.setAttribute("ReportAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.REPORTADMIN)], true));
      request.setAttribute("ReportAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.REPORTADMIN)], false));
      
      //SRT Admin attribute
      request.setAttribute("SRTAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.SRTADMIN)], true));
      request.setAttribute("SRTAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.SRTADMIN)], false));
      
      //ALERT Admin attribute
      request.setAttribute("ALERTAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN)], true));
      request.setAttribute("ALERTAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN)], false));
      
    //Epilink Admin attribute
      request.setAttribute("EpilinkAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)], true));
      request.setAttribute("EpilinkAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)], false));
      
      //Import and Export Admin attribute
      request.setAttribute("ImportExportAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.IMPORTEXPORTADMIN)], true));
      request.setAttribute("ImportExportAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.IMPORTEXPORTADMIN)], false));
      //Global Attributes
      
      request.setAttribute("HIVQuestionsUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS)], true));
      request.setAttribute("HIVQuestionsGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS)], false));
      //Patient attributes
	    request.setAttribute("PatientAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ADD)], true));
	    request.setAttribute("PatientAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ADD)], false));
	    request.setAttribute("PatientEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("PatientEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("PatientViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("PatientViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("PatientViewPatientFileUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEWWORKUP)], true));
	    request.setAttribute("PatientViewPatientFileGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEWWORKUP)], false));
	    request.setAttribute("PatientDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("PatientDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("PatientFindUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FIND)], true));
	    request.setAttribute("PatientFindGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FIND)], false));
	    request.setAttribute("PatientFindInactiveUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FINDINACTIVE)], true));
	    request.setAttribute("PatientFindInactiveGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FINDINACTIVE)], false));
	    request.setAttribute("PatientMergeUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.MERGE)], true));
	    request.setAttribute("PatientMergeGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.MERGE)], false));
	    request.setAttribute("PatientSubjectRoleAdminUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ROLEADMINISTRATION)], true));
	    request.setAttribute("PatientSubjectRoleAdminGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ROLEADMINISTRATION)], false));

	    //Provider attributes
	    request.setAttribute("ProviderManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PROVIDER, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("ProviderManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PROVIDER, NBSOperationLookup.MANAGE)], false));
	    
	    //Organization
	    request.setAttribute("OrganizationManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("OrganizationManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.MANAGE)], false));
	    
	    //Place	    
	    request.setAttribute("PlaceManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PLACE, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("PlaceManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PLACE, NBSOperationLookup.MANAGE)], false));

	    //Treatment attributes
	    request.setAttribute("TreatmentManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("TreatmentManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.MANAGE)], false));
	    request.setAttribute("TreatmentViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("TreatmentViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)], false));
	    
	    //Document attributes
	    request.setAttribute("DocumentViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("DocumentViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("DocumentDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("DocumentDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("DocumentTransferOwnershipUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.TRANSFERPERMISSIONS)], true));
	    request.setAttribute("DocumentTransferOwnershipGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.TRANSFERPERMISSIONS)], false));
	    request.setAttribute("DocumentAssignSecurityForUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY)], true));
	    request.setAttribute("DocumentAssignSecurityForGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY)], false));
	    request.setAttribute("DocumentAutoCreateUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.AUTOCREATE)], true));//AutoCreate
	    request.setAttribute("DocumentAutoCreateGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.AUTOCREATE)], false));//AutoCreate
	    request.setAttribute("DocumentMarkAsReviewedUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.MARKREVIEWED)], true));
	    request.setAttribute("DocumentMarkAsReviewedGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.MARKREVIEWED)], false));
	    


	    //Investigation attributes
	    request.setAttribute("InvestigationAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD)], true));
	    request.setAttribute("InvestigationAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD)], false));
	    request.setAttribute("InvestigationEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("InvestigationEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("InvestigationViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("InvestigationViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("InvestigationDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("InvestigationDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("InvestigationTransferOwnershipUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.TRANSFERPERMISSIONS)], true));
	    request.setAttribute("InvestigationTransferOwnershipGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.TRANSFERPERMISSIONS)], false));
	    request.setAttribute("InvestigationAssociateLabReportsUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS)], true));
	    request.setAttribute("InvestigationAssociateLabReportsGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS)], false));
	    request.setAttribute("InvestigationAssociateMorbidityReportsUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS)], true));
	    request.setAttribute("InvestigationAssociateMorbidityReportsGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS)], false));
	    request.setAttribute("InvestigationAssociateVaccinationsUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS)], true));
	    request.setAttribute("InvestigationAssociateVaccinationsGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS)], false));
	    request.setAttribute("InvestigationAssociateTreatmentsUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTS)], true));
	    request.setAttribute("InvestigationAssociateTreatmentsGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTS)], false));
	    request.setAttribute("InvestigationChangeConditionUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.CHANGECONDITION)], true));
	    request.setAttribute("InvestigationChangeConditionGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.CHANGECONDITION)], false));
	    request.setAttribute("InvestigationMergeInvestigationUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.MERGEINVESTIGATION)], true));
	    request.setAttribute("InvestigationMergeInvestigationGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.MERGEINVESTIGATION)], false));
	    request.setAttribute("TBHIVSecurityUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.INVESTIGATIONRVCTHIV)], true));
		request.setAttribute("TBHIVSecurityGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.INVESTIGATIONRVCTHIV)], false));
		request.setAttribute("InvestigationAssociateDocumentUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEDOCUMENTS)], true));
		request.setAttribute("InvestigationAssociateDocumentGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEDOCUMENTS)], false));
		request.setAttribute("InvestigationDisassociateInitiatingEventUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.DISASSOCIATEINITIATINGEVENT)], true));
		request.setAttribute("InvestigationDisassociateInitiatingEventGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.DISASSOCIATEINITIATINGEVENT)], false));


	    //Summary report attributes
	    request.setAttribute("SummaryReportAddEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD)], true));
	    request.setAttribute("SummaryReportAddEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD)], false));
	    request.setAttribute("SummaryReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("SummaryReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("SummaryReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("SummaryReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.DELETE)], false));

	    //Vaccination record attributes
	    request.setAttribute("VaccinationAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.ADD)], true));
	    request.setAttribute("VaccinationAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.ADD)], false));
	    request.setAttribute("VaccinationEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("VaccinationEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("VaccinationViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("VaccinationViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("VaccinationDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("VaccinationDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.DELETE)], false));
	    
	    //Queues
	    request.setAttribute("MessageQueueUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.MESSAGE)], true));
	    request.setAttribute("MessageQueueGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.MESSAGE)], false));
	    request.setAttribute("SuperviserReviewQueueUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.SUPERVISORREVIEW)], true));
	    request.setAttribute("SuperviserReviewQueueGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.SUPERVISORREVIEW)], false));
	    
	    //Public Queues
	    
	    request.setAttribute("AddInactivatePublicQueueUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PUBLICQUEUES, NBSOperationLookup.ADDINACTIVATE)], true));
	    request.setAttribute("AddInactivatePublicQueueGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PUBLICQUEUES, NBSOperationLookup.ADDINACTIVATE)], false));
	
	  //Contact record attributes
	    request.setAttribute("ContactRecordAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.ADD)], true));
	    request.setAttribute("ContactRecordAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.ADD)], false));
	    request.setAttribute("ContactRecordEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("ContactRecordEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("ContactRecordViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("ContactRecordViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("ContactRecordDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("ContactRecordDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("ContactRecordManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("ContactRecordManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.MANAGE)], false));

	  //Interview attributes
	    request.setAttribute("InterviewRecordAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.ADD)], true));
	    request.setAttribute("InterviewRecordAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.ADD)], false));
	    request.setAttribute("InterviewRecordEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("InterviewRecordEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("InterviewRecordViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("InterviewRecordViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("InterviewRecordDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("InterviewRecordDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.DELETE)], false));
	   
	    //Lab report attributes
	    request.setAttribute("LabReportAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD)], true));
	    request.setAttribute("LabReportAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD)], false));
	    request.setAttribute("LabReportEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("LabReportEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("LabReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("LabReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("LabReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("LabReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("LabReportAssignSecurityUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY)], true));
	    request.setAttribute("LabReportAssignSecurityGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY)], false));
	    request.setAttribute("LabReportTransferOwnershipUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)], true));
	    request.setAttribute("LabReportTransferOwnershipGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)], false));
	    request.setAttribute("LabReportViewELRActivityLogUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEWELRACTIVITY)], true));
	    request.setAttribute("LabReportViewELRActivityLogGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEWELRACTIVITY)], false));
	    request.setAttribute("LabReportAssignSecurityForDataEntryUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)], true));
	    request.setAttribute("LabReportAssignSecurityForDataEntryGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)], false));
	    request.setAttribute("LabReportDeleteExternalUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETEEXTERNAL)], true));
	    request.setAttribute("LabReportDeleteExternalGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETEEXTERNAL)], false));
	    //Mark As Reviewed
	    request.setAttribute("LabReportMarkAsReviewedUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.MARKREVIEWED)], true));
	    request.setAttribute("LabReportMarkAsReviewedGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.MARKREVIEWED)], false));
   


	    //Morbidity report attributes
	    request.setAttribute("MorbidityReportAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD)], true));
	    request.setAttribute("MorbidityReportAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD)], false));
	    request.setAttribute("MorbidityReportEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.EDIT)], true));
	    request.setAttribute("MorbidityReportEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.EDIT)], false));
	    request.setAttribute("MorbidityReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("MorbidityReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW)], false));
	    request.setAttribute("MorbidityReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETE)], true));
	    request.setAttribute("MorbidityReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETE)], false));
	    request.setAttribute("MorbidityReportTransferOwnershipUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)], true));
	    request.setAttribute("MorbidityReportTransferOwnershipGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)], false));
	    request.setAttribute("InvestigationAutoCreateUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE)], true));//AutoCreate
	    request.setAttribute("InvestigationAutoCreateGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE)], false));//AutoCreate
	    request.setAttribute("MorbidityReportAssignSecurityUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY)], true));
	    request.setAttribute("MorbidityReportAssignSecurityGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY)], false));
	    request.setAttribute("MorbidityReportAssignSecurityForDataEntryUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)], true));
	    request.setAttribute("MorbidityReportAssignSecurityForDataEntryGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)], false));
	    request.setAttribute("MorbidityReportDeleteExternalUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETEEXTERNAL)], true));
	    request.setAttribute("MorbidityReportDeleteExternalGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETEEXTERNAL)], false));
	    //Mark As Reviewed
	    request.setAttribute("MorbidityReportMarkAsReviewedUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.MARKREVIEWED)], true));
	    request.setAttribute("MorbidityReportMarkAsReviewedGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.MARKREVIEWED)], false));
	    

	    //Treatment attributes
	    request.setAttribute("TreatmentManageUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.MANAGE)], true));
	    request.setAttribute("TreatmentManageGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.MANAGE)], false));
	    request.setAttribute("TreatmentViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)], true));
	    request.setAttribute("TreatmentViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)], false));

	    //Notification attributes
	    request.setAttribute("NotificationCreateUser", convertNotifOperationsToCheckboxValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)], opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL)], true));
	      request.setAttribute("NotificationCreateNeedsApprovalUser", convertNeedApprovalToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL)], true));
	    request.setAttribute("NotificationCreateGuest", convertNotifOperationsToCheckboxValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)], opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL)], false));
	      request.setAttribute("NotificationCreateNeedsApprovalGuest", convertNeedApprovalToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL)], false));
	    request.setAttribute("NotificationReviewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW)], true));
	    request.setAttribute("NotificationReviewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW)], false));
	    request.setAttribute("NotificationCreateSummaryNotificationUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATESUMMARYNOTIFICATION)], true));
	    request.setAttribute("NotificationCreateSummaryNotificationGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATESUMMARYNOTIFICATION)], false));

	  //case reporting attributes
	    request.setAttribute("CaseReportingCreateUser", convertNotifOperationsToCheckboxValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATE)], opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)], true));
	    request.setAttribute("CaseReportingCreateNeedsApprovalUser", convertNeedApprovalToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)], true));
	    request.setAttribute("CaseReportingCreateGuest", convertNotifOperationsToCheckboxValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATE)], opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)], false));
	    request.setAttribute("CaseReportingCreateNeedsApprovalGuest", convertNeedApprovalToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)], false));
	    request.setAttribute("CaseReportViewPHCRActivityLogUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.VIEWPHCRACTIVITY)], true));
	    request.setAttribute("CaseReportViewPHCRActivityLogGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.VIEWPHCRACTIVITY)], false));


	    //Reporting attributes
	    request.setAttribute("ExportReportUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EXPORTREPORT)], true));
	    request.setAttribute("ExportReportGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EXPORTREPORT)], false));
	    request.setAttribute("RunReportUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.RUNREPORT)], true));
	    request.setAttribute("RunReportGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.RUNREPORT)], false));

		//Template report
		request.setAttribute("TemplateReportSelectCriteriaUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIATEMPLATE)], true));
		request.setAttribute("TemplateReportSelectCriteriaGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIATEMPLATE)], false));
		request.setAttribute("TemplateReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE)], true));
		request.setAttribute("TemplateReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE)], false));

		//Public report
		request.setAttribute("PublicReportAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPUBLIC)], true));
		request.setAttribute("PublicReportAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPUBLIC)], false));
		request.setAttribute("PublicReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPUBLIC)], true));
		request.setAttribute("PublicReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPUBLIC)], false));
		request.setAttribute("PublicReportEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPUBLIC)], true));
		request.setAttribute("PublicReportEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPUBLIC)], false));
		request.setAttribute("PublicReportSelectFilterUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC)], true));
		request.setAttribute("PublicReportSelectFilterGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC)], false));
		request.setAttribute("PublicReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC)], true));
		request.setAttribute("PublicReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC)], false));

		//Private report
		request.setAttribute("PrivateReportAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPRIVATE)], true));
		request.setAttribute("PrivateReportAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPRIVATE)], false));
		request.setAttribute("PrivateReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPRIVATE)], true));
		request.setAttribute("PrivateReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPRIVATE)], false));
		request.setAttribute("PrivateReportEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPRIVATE)], true));
		request.setAttribute("PrivateReportEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPRIVATE)], false));
		request.setAttribute("PrivateReportSelectFilterUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPRIVATE)], true));
		request.setAttribute("PrivateReportSelectFilterGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPRIVATE)], false));
		request.setAttribute("PrivateReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE)], true));
		request.setAttribute("PrivateReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE)], false));

		//ReportingFacility report
		request.setAttribute("ReportingFacilityReportAddUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTREPORTINGFACILITY)], true));
		request.setAttribute("ReportingFacilityReportAddGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTREPORTINGFACILITY)], false));
		request.setAttribute("ReportingFacilityReportDeleteUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTREPORTINGFACILITY)], true));
		request.setAttribute("ReportingFacilityReportDeleteGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTREPORTINGFACILITY)], false));
		request.setAttribute("ReportingFacilityReportEditUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTREPORTINGFACILITY)], true));
		request.setAttribute("ReportingFacilityReportEditGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTREPORTINGFACILITY)], false));
		request.setAttribute("ReportingFacilityReportSelectFilterUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY)], true));
		request.setAttribute("ReportingFacilityReportSelectFilterGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY)], false));
		request.setAttribute("ReportingFacilityReportViewUser", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY)], true));
		request.setAttribute("ReportingFacilityReportViewGuest", convertOperationToAttributeValue(opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY)], false));
		
		
  }

  private String convertOperationToAttributeValue(byte opVal, boolean user)
  {
      String returnVal = null;

      if(user)
      {
	  if (opVal == BusinessObjOperationUtil.OPERATION_AVAILABLE || opVal == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "U";
	  }
	  return returnVal;
      }
      else
      {
	  if (opVal == BusinessObjOperationUtil.OPERATION_GUEST || opVal == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "G";
	  }
	  return returnVal;
      }
  }

  private String convertNotifOperationsToCheckboxValue(byte create, byte createNeedApproval, boolean user)
  {
      String returnVal = null;

      if(user)
      {
	  if (create == BusinessObjOperationUtil.OPERATION_AVAILABLE || create == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL || createNeedApproval == BusinessObjOperationUtil.OPERATION_AVAILABLE || createNeedApproval == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "U";
	  }
	  return returnVal;
      }
      else
      {
	  if (create == BusinessObjOperationUtil.OPERATION_GUEST || create == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL || createNeedApproval == BusinessObjOperationUtil.OPERATION_GUEST || createNeedApproval == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "G";
	  }
	  return returnVal;
      }
  }

  private String convertNeedApprovalToAttributeValue(byte approval, boolean user)
  {
      String returnVal = null;
      if(user)
      {
	  if (approval == BusinessObjOperationUtil.OPERATION_AVAILABLE || approval == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "y";
	  }
	  return returnVal;
      }
      else
      {
	  if (approval == BusinessObjOperationUtil.OPERATION_GUEST || approval == BusinessObjOperationUtil.OPERATION_AVAILABLE_ALL)
	  {
	      returnVal = "y";
	  }
	  return returnVal;
      }
  }

  private void makeArray(byte[] opArray, HttpServletRequest request)
  {
      //System
	  //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.SECURITYADMINISTRATION)] = setOperationValue(request.getParameter("SecAdminUser"), request.getParameter("SecAdminGuest"));
	  //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION)] = setOperationValue(request.getParameter("LDFAdminUser"), request.getParameter("LDFAdminGuest"));
      
	  opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN)] = setOperationValue(request.getParameter("EpilinkAdminUser"), request.getParameter("EpilinkAdminGuest"));

	  // Global
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS)] = setOperationValue(request.getParameter("HIVQuestionsUser"), request.getParameter("HIVQuestionsGuest"));

      //Patient
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("PatientAddUser"), request.getParameter("PatientAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("PatientEditUser"), request.getParameter("PatientEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("PatientViewUser"), request.getParameter("PatientViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.VIEWWORKUP)] = setOperationValue(request.getParameter("PatientViewPatientFileUser"), request.getParameter("PatientViewPatientFileGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("PatientDeleteUser"), request.getParameter("PatientDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FIND)] = setOperationValue(request.getParameter("PatientFindUser"), request.getParameter("PatientFindGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.FINDINACTIVE)] = setOperationValue(request.getParameter("PatientFindInactiveUser"), request.getParameter("PatientFindInactiveGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.MERGE)] = setOperationValue(request.getParameter("PatientMergeUser"), request.getParameter("PatientMergeGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PATIENT, NBSOperationLookup.ROLEADMINISTRATION)] = setOperationValue(request.getParameter("PatientSubjectRoleAdminUser"), request.getParameter("PatientSubjectRoleAdminGuest"));

      //Provider
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PROVIDER, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("ProviderManageUser"), request.getParameter("ProviderManageGuest"));

      //Organization
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("OrganizationAddUser"), request.getParameter("OrganizationAddGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("OrganizationEditUser"), request.getParameter("OrganizationEditGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("OrganizationViewUser"), request.getParameter("OrganizationViewGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("OrganizationDeleteUser"), request.getParameter("OrganizationDeleteGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.FIND)] = setOperationValue(request.getParameter("OrganizationFindUser"), request.getParameter("OrganizationFindGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.FINDINACTIVE)] = setOperationValue(request.getParameter("OrganizationFindInactiveUser"), request.getParameter("OrganizationFindInactiveGuest"));
      //opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.ROLEADMINISTRATION)] = setOperationValue(request.getParameter("OrganizationSubjectRoleAdminUser"), request.getParameter("OrganizationSubjectRoleAdminGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.ORGANIZATION, NBSOperationLookup.MANAGE)] = setOperationValue(request.getParameter("OrganizationManageUser"), request.getParameter("OrganizationManageGuest"));

      //Place
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PLACE, NBSOperationLookup.MANAGE)] = setOperationValue(request.getParameter("PlaceManageUser"), request.getParameter("PlaceManageGuest"));

      // Document
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("DocumentViewUser"), request.getParameter("DocumentViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("DocumentDeleteUser"), request.getParameter("DocumentDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.TRANSFERPERMISSIONS)] = setOperationValue(request.getParameter("DocumentTransferOwnershipUser"), request.getParameter("DocumentTransferOwnershipGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY)] = setOperationValue(request.getParameter("DocumentAssignSecurityForUser"), request.getParameter("DocumentAssignSecurityForGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.DOCUMENT, NBSOperationLookup.AUTOCREATE)] = setOperationValue(request.getParameter("DocumentAutoCreateUser"), request.getParameter("DocumentAutoCreateGuest"));


      //Investigation
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("InvestigationAddUser"), request.getParameter("InvestigationAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("InvestigationEditUser"), request.getParameter("InvestigationEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("InvestigationViewUser"), request.getParameter("InvestigationViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.TRANSFERPERMISSIONS)] = setOperationValue(request.getParameter("InvestigationTransferOwnershipUser"), request.getParameter("InvestigationTransferOwnershipGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS)] = setOperationValue(request.getParameter("InvestigationAssociateLabReportsUser"), request.getParameter("InvestigationAssociateLabReportsGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS)] = setOperationValue(request.getParameter("InvestigationAssociateMorbidityReportsUser"), request.getParameter("InvestigationAssociateMorbidityReportsGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS)] = setOperationValue(request.getParameter("InvestigationAssociateVaccinationsUser"), request.getParameter("InvestigationAssociateVaccinationsGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTS)] = setOperationValue(request.getParameter("InvestigationAssociateTreatmentsUser"), request.getParameter("InvestigationAssociateTreatmentsGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.CHANGECONDITION)] = setOperationValue(request.getParameter("InvestigationChangeConditionUser"), request.getParameter("InvestigationChangeConditionGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.MERGEINVESTIGATION)] = setOperationValue(request.getParameter("InvestigationMergeInvestigationUser"), request.getParameter("InvestigationMergeInvestigationGuest"));
      
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEDOCUMENTS)] = setOperationValue(request.getParameter("InvestigationAssociateDocumentUser"), request.getParameter("InvestigationAssociateDocumentGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.DISASSOCIATEINITIATINGEVENT)] = setOperationValue(request.getParameter("InvestigationDisassociateInitiatingEventUser"), request.getParameter("InvestigationDisassociateInitiatingEventGuest"));
      
      //Summary report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("SummaryReportAddEditUser"), request.getParameter("SummaryReportAddEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("SummaryReportViewUser"), request.getParameter("SummaryReportViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("SummaryReportDeleteUser"), request.getParameter("SummaryReportDeleteGuest"));

      //Vaccination records
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("VaccinationAddUser"), request.getParameter("VaccinationAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("VaccinationEditUser"), request.getParameter("VaccinationEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("VaccinationViewUser"), request.getParameter("VaccinationViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("VaccinationDeleteUser"), request.getParameter("VaccinationDeleteGuest"));
      
    //QUEUES
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.MESSAGE)] = setOperationValue(request.getParameter("MessageQueueUser"), request.getParameter("MessageQueueGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.QUEUES, NBSOperationLookup.SUPERVISORREVIEW)] = setOperationValue(request.getParameter("SuperviserReviewQueueUser"), request.getParameter("SuperviserReviewQueueGuest"));
       
      //Public Queues
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.PUBLICQUEUES, NBSOperationLookup.ADDINACTIVATE)] = setOperationValue(request.getParameter("AddInactivatePublicQueueUser"), request.getParameter("AddInactivatePublicQueueGuest"));
	    
      //Contact records
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("ContactRecordAddUser"), request.getParameter("ContactRecordAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("ContactRecordEditUser"), request.getParameter("ContactRecordEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("ContactRecordViewUser"), request.getParameter("ContactRecordViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("ContactRecordDeleteUser"), request.getParameter("ContactRecordDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CT_CONTACT, NBSOperationLookup.MANAGE)] = setOperationValue(request.getParameter("ContactRecordManageUser"), request.getParameter("ContactRecordManageGuest"));

      
    //Interview records
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("InterviewRecordAddUser"), request.getParameter("InterviewRecordAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("InterviewRecordEditUser"), request.getParameter("InterviewRecordEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("InterviewRecordViewUser"), request.getParameter("InterviewRecordViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INTERVIEW, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("InterviewRecordDeleteUser"), request.getParameter("InterviewRecordDeleteGuest"));
     
      //Lab reports
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("LabReportAddUser"), request.getParameter("LabReportAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("LabReportEditUser"), request.getParameter("LabReportEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("LabReportViewUser"), request.getParameter("LabReportViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("LabReportDeleteUser"), request.getParameter("LabReportDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY)] = setOperationValue(request.getParameter("LabReportAssignSecurityUser"), request.getParameter("LabReportAssignSecurityGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)] = setOperationValue(request.getParameter("LabReportTransferOwnershipUser"), request.getParameter("LabReportTransferOwnershipGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEWELRACTIVITY)] = setOperationValue(request.getParameter("LabReportViewELRActivityLogUser"), request.getParameter("LabReportViewELRActivityLogGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)] = setOperationValue(request.getParameter("LabReportAssignSecurityForDataEntryUser"), request.getParameter("LabReportAssignSecurityForDataEntryGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.DELETEEXTERNAL)] = setOperationValue(request.getParameter("LabReportDeleteExternalUser"), request.getParameter("LabReportDeleteExternalGuest"));

      //Morbidity report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD)] = setOperationValue(request.getParameter("MorbidityReportAddUser"), request.getParameter("MorbidityReportAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.EDIT)] = setOperationValue(request.getParameter("MorbidityReportEditUser"), request.getParameter("MorbidityReportEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("MorbidityReportViewUser"), request.getParameter("MorbidityReportViewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETE)] = setOperationValue(request.getParameter("MorbidityReportDeleteUser"), request.getParameter("MorbidityReportDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.TRANSFERPERMISSIONS)] = setOperationValue(request.getParameter("MorbidityReportTransferOwnershipUser"), request.getParameter("MorbidityReportTransferOwnershipGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE)] = setOperationValue(request.getParameter("InvestigationAutoCreateUser"), request.getParameter("InvestigationAutoCreateGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY)] = setOperationValue(request.getParameter("MorbidityReportAssignSecurityForDataEntryUser"), request.getParameter("MorbidityReportAssignSecurityForDataEntryGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.DELETEEXTERNAL)] = setOperationValue(request.getParameter("MorbidityReportDeleteExternalUser"), request.getParameter("MorbidityReportDeleteExternalGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY)] = setOperationValue(request.getParameter("MorbidityReportAssignSecurityUser"), request.getParameter("MorbidityReportAssignSecurityGuest"));

      //Treatment
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.MANAGE)] = setOperationValue(request.getParameter("TreatmentManageUser"), request.getParameter("TreatmentManageGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW)] = setOperationValue(request.getParameter("TreatmentViewUser"), request.getParameter("TreatmentViewGuest"));

      //Notification
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)] = setNotifOperationValue(request.getParameter("NotificationCreateUser"), request.getParameter("NotificationCreateGuest"), request.getParameter("NotificationCreateNeedsApprovalUser"), request.getParameter("NotificationCreateNeedsApprovalGuest"), false);
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL)] = setNotifOperationValue(request.getParameter("NotificationCreateUser"), request.getParameter("NotificationCreateGuest"), request.getParameter("NotificationCreateNeedsApprovalUser"), request.getParameter("NotificationCreateNeedsApprovalGuest"), true);
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW)] = setOperationValue(request.getParameter("NotificationReviewUser"), request.getParameter("NotificationReviewGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATESUMMARYNOTIFICATION)] = setOperationValue(request.getParameter("NotificationCreateSummaryNotificationUser"), request.getParameter("NotificationCreateSummaryNotificationGuest"));
      
     //Case Reporting
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATE)] = setNotifOperationValue(request.getParameter("CaseReportingCreateUser"), request.getParameter("CaseReportingCreateGuest"), request.getParameter("CaseReportingCreateNeedsApprovalUser"), request.getParameter("CaseReportingCreateNeedsApprovalGuest"), false);
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)] = setNotifOperationValue(request.getParameter("CaseReportingCreateUser"), request.getParameter("CaseReportingCreateGuest"), request.getParameter("CaseReportingCreateNeedsApprovalUser"), request.getParameter("CaseReportingCreateNeedsApprovalGuest"), true);
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.CASEREPORTING, NBSOperationLookup.VIEWPHCRACTIVITY)] = setOperationValue(request.getParameter("CaseReportViewPHCRActivityLogUser"), request.getParameter("CaseReportViewPHCRActivityLogGuest"));

      //Reports
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EXPORTREPORT)] = setOperationValue(request.getParameter("ExportReportUser"), request.getParameter("ExportReportGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.RUNREPORT)] = setOperationValue(request.getParameter("RunReportUser"), request.getParameter("RunReportGuest"));

      //Template report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIATEMPLATE)] = setOperationValue(request.getParameter("TemplateReportSelectCriteriaUser"), request.getParameter("TemplateReportSelectCriteriaGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE)] = setOperationValue(request.getParameter("TemplateReportViewUser"), request.getParameter("TemplateReportViewGuest"));

      //Public report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPUBLIC)] = setOperationValue(request.getParameter("PublicReportAddUser"), request.getParameter("PublicReportAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPUBLIC)] = setOperationValue(request.getParameter("PublicReportDeleteUser"), request.getParameter("PublicReportDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPUBLIC)] = setOperationValue(request.getParameter("PublicReportEditUser"), request.getParameter("PublicReportEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC)] = setOperationValue(request.getParameter("PublicReportSelectFilterUser"), request.getParameter("PublicReportSelectFilterGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC)] = setOperationValue(request.getParameter("PublicReportViewUser"), request.getParameter("PublicReportViewGuest"));

      //Private report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPRIVATE)] = setOperationValue(request.getParameter("PrivateReportAddUser"), request.getParameter("PrivateReportAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPRIVATE)] = setOperationValue(request.getParameter("PrivateReportDeleteUser"), request.getParameter("PrivateReportDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPRIVATE)] = setOperationValue(request.getParameter("PrivateReportEditUser"), request.getParameter("PrivateReportEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPRIVATE)] = setOperationValue(request.getParameter("PrivateReportSelectFilterUser"), request.getParameter("PrivateReportSelectFilterGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE)] = setOperationValue(request.getParameter("PrivateReportViewUser"), request.getParameter("PrivateReportViewGuest"));

      //ReportingFacility report
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTREPORTINGFACILITY)] = setOperationValue(request.getParameter("ReportingFacilityReportAddUser"), request.getParameter("ReportingFacilityReportAddGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTREPORTINGFACILITY)] = setOperationValue(request.getParameter("ReportingFacilityReportDeleteUser"), request.getParameter("ReportingFacilityReportDeleteGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTREPORTINGFACILITY)] = setOperationValue(request.getParameter("ReportingFacilityReportEditUser"), request.getParameter("ReportingFacilityReportEditGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY)] = setOperationValue(request.getParameter("ReportingFacilityReportSelectFilterUser"), request.getParameter("ReportingFacilityReportSelectFilterGuest"));
      opArray[BusinessObjOperationUtil.getBusinesObjOperationIndex(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY)] = setOperationValue(request.getParameter("ReportingFacilityReportViewUser"), request.getParameter("ReportingFacilityReportViewGuest"));
      
  }
  private byte setOperationValue(String user, String guest)
  {
      byte returnOpVal = -1;

      if((user!= null && user.equalsIgnoreCase("U"))  && (guest != null && guest.equalsIgnoreCase("G")))
      {
	returnOpVal = 3;
      }
      else if((user == null || !user.equalsIgnoreCase("U"))  && (guest != null && guest.equalsIgnoreCase("G")))
      {
	  returnOpVal = 2;
      }
      else if((user != null && user.equalsIgnoreCase("U"))  && (guest == null || !guest.equalsIgnoreCase("G")))
      {
	  returnOpVal = 1;
      }
      else if((user == null || !user.equalsIgnoreCase("U"))  && (guest == null || !guest.equalsIgnoreCase("G")))
      {
	  returnOpVal = 0;
      }

      return returnOpVal;
  }


   private byte setNotifOperationValue(String user, String guest, String userApp, String guestApp, boolean createNeedApp)
  {
      byte returnOpVal = -1;

      if((user!= null && user.equalsIgnoreCase("U"))  && (guest != null && guest.equalsIgnoreCase("G")))//Both checkboxes are checked
      {
	  if((userApp!= null && userApp.equalsIgnoreCase("y")) && (guestApp!= null && guestApp.equalsIgnoreCase("y")))
	  {
	      returnOpVal = (byte)(createNeedApp ? 3:0);
	  }
	  if((userApp!= null && !userApp.equalsIgnoreCase("y")) && (guestApp!= null && !guestApp.equalsIgnoreCase("y")))
	  {
	      returnOpVal = (byte)(createNeedApp ? 0:3);
	  }
	  if((userApp!= null && userApp.equalsIgnoreCase("y")) && (guestApp!= null && !guestApp.equalsIgnoreCase("y")))
	  {
	      returnOpVal = (byte)(createNeedApp ? 1:2);
	  }
	  if((userApp!= null && !userApp.equalsIgnoreCase("y")) && (guestApp!= null && guestApp.equalsIgnoreCase("y")))
	  {
	      returnOpVal = (byte)(createNeedApp ? 2:1);
	  }
      }
      else if((user == null || !user.equalsIgnoreCase("U"))  && (guest != null && guest.equalsIgnoreCase("G")))//Guest checkbox is checked
      {
	  if(guestApp != null && guestApp.equalsIgnoreCase("y"))
	  {
	      returnOpVal = (byte)(createNeedApp ? 2:0);
	  }
	  else
	  {
	      returnOpVal = (byte)(createNeedApp ? 0:2);
	  }
      }
      else if((user != null && user.equalsIgnoreCase("U"))  && (guest == null || !guest.equalsIgnoreCase("G")))//User checkbox is checked
      {
	  if(userApp != null && userApp.equalsIgnoreCase("y"))
	  {
	      returnOpVal = (byte)(createNeedApp ? 1:0);
	  }
	  else
	  {
	      returnOpVal = (byte)(createNeedApp ? 0:1);
	  }
      }
      else if((user == null || !user.equalsIgnoreCase("U"))  && (guest == null || !guest.equalsIgnoreCase("G")))//No checkbox is checked
      {
	  returnOpVal = 0;
      }

      return returnOpVal;
  }
   
   private PermissionSet setPermissionSet(AuthPermSetDT securePermSetDT, PermissionSet set){
	   set.setDescription(securePermSetDT.getPermSetDesc());
	   set.setRoleName(securePermSetDT.getPermSetNm());
	   if(securePermSetDT.getSysDefinedPermSetInd() != null && securePermSetDT.getSysDefinedPermSetInd().equals("T"))
		   	  set.setReadOnly(true);
		  else 
			  set.setReadOnly(false);
	   return set;
   }
}
