package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;

public class UserStore
    extends Action {

  static final LogUtils logger = new LogUtils(UserStore.class.getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  public UserStore() {
  }

  private void initDebug() {

  }

  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    int index = 0;
    UserForm userForm = (UserForm) form;
    ErrorMessageHelper.setErrMsgToRequest(request, "ps143");
    HttpSession session = request.getSession();
    NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
    boolean isUserMSA = secObj.isUserMSA();

    if (session == null) {
      logger.debug("error no session");
      return mapping.findForward("login");
    }
    ActionForward forwardNew = new ActionForward();
    try {
    	UserProfile userProfile = (UserProfile) userForm.getUserProfile();
    	String strType = request.getParameter("OperationType");

    	if (strType.equalsIgnoreCase(NEDSSConstants.CREATE) ||(strType.equalsIgnoreCase(NEDSSConstants.EDIT)&& isUserMSA )){
    		String[] paaProgramArea = request.getParameterValues("paaProgramArea");
    		if (paaProgramArea != null) {
    			StringBuffer sbPaaProgramArea = new StringBuffer();
    			for (int i = 0; i < paaProgramArea.length; i++) {
    				sbPaaProgramArea.append(paaProgramArea[i]);
    				//	if(i != (paaProgramArea.length - 1))
    				sbPaaProgramArea.append("|");
    			}
    			userProfile.getTheUser().setPaaProgramArea(sbPaaProgramArea.toString());

    		}
    		else {
    			userProfile.getTheUser().setPaaProgramArea("");
    		}
    	}

    	String UID = null;
    	Iterator<Object>  itr = null;

    	if (userProfile.getTheRealizedRoleCollection() != null) {
    		itr = userProfile.getTheRealizedRoleCollection().iterator();

    	}
    	ArrayList<Object> rRolesWithOnlyActive = new ArrayList<Object> ();
    	RealizedRole rr = null;

    	//  CREATE
    	if ( (strType.equalsIgnoreCase(NEDSSConstants.CREATE))) {
    		try {

    			String facilityDetails = request.getParameter(
    					"Org-ReportingOrganizationUID-values");
    			String facilityID = request.getParameter("Org-ReportingOrganizationUID");
    			if (facilityID != null && !facilityID.equals("")) {
    				long facIdAsLong = Long.parseLong(facilityID.trim());
    				userProfile.theUser.setReportingFacilityUid(new Long(facIdAsLong));
    			}
    			else {
    				userProfile.theUser.setReportingFacilityUid(null);
    			}

    			String providerID = request.getParameter("Prv-providerUID");
    			if (providerID != null && !providerID.equals("")) {
    				long prvIdAsLong = Long.parseLong(providerID.trim());
    				userProfile.theUser.setProviderUid(new Long(prvIdAsLong));
    			}
    			else {
    				userProfile.theUser.setProviderUid(null);
    			}

    			userProfile.setItNew(true);
    			userProfile.setItDirty(false);
    			userProfile.theUser.setItNew(true);
    			userProfile.theUser.setItDirty(false);

    			//Set the facility info to the user object
    			if (userProfile.theUser.getUserType() != null &&
    					userProfile.theUser.getUserType().trim().equalsIgnoreCase("Y")) {
    				userProfile.theUser.setUserType(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
    			}
    			else {
    				userProfile.theUser.setUserType(NEDSSConstants.SEC_USERTYPE_INTERNAL);
    			}

    			//Set the adminUserType
    			setAdminUserType(userProfile, strType,isUserMSA);

    			if (itr != null) {
    				while (itr.hasNext()) {
    					rr = (RealizedRole) itr.next();
    					//Check to see if realized role was removed from batch
    					//If so, skip over it in the create
    					if (rr.getStatusCd().equals("I")) {
    						logger.debug(
    								"This realized role was removed from batch table before submit");
    					}
    					else {
    						rr.setItNew(true);
    						rr.setItDirty(false);

    						if (rr.getGuestString().equals("Y")) {
    							rr.setGuest(true);
    						}
    						else {
    							rr.setGuest(false);
    						}
    						rRolesWithOnlyActive.add(rr);
    					} //end of else
    				} // end of while
    				userProfile.setTheRealizedRoleCollection(rRolesWithOnlyActive);
    			}
    			else {
    				userProfile.setTheRealizedRoleCollection(null);
    			}
    			userProfile.getTheUser().setComments("");
    			userProfile.getTheUser().setEntryID("");
    			userProfile.getTheUser().setPassword("");
    			userProfile.getTheUser().setItDelete(false);

    			// check if user exists
    			Object[] oParams1 = new Object[] {
    					userProfile.getTheUser_s().getUserID()};

    			MainSessionCommand msCommand1 = null;
    			String sBeanJndiName1 = JNDINames.NBS_DB_SECURITY_EJB; 

    			String sMethod1 = "doesUserExist";

    			MainSessionHolder holder1 = new MainSessionHolder();
    			msCommand1 = holder1.getMainSessionCommand(session);
    			ArrayList<?> arr1 = msCommand1.processRequest(sBeanJndiName1, sMethod1,
    					oParams1);
    			Boolean flag = (Boolean) arr1.get(0);
    			if (flag.booleanValue()) {
    				request.setAttribute("errorvalue", "true");
    				session.setAttribute("duperrorvalue", "true");
    				request.setAttribute("dupuserid",
    						userProfile.getTheUser_s().getUserID());
    				return mapping.findForward("duperror");

    			}
    			else {
    				MainSessionCommand msCommand = null;
    				String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;          
    				String sMethod = "addUser";

    				Object[] oParams = new Object[] {
    						userProfile};

    				MainSessionHolder holder = new MainSessionHolder();
    				msCommand = holder.getMainSessionCommand(session);
    				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
    						oParams);
    			}
    		}
    		catch (Exception e) {
    			logger.error("Exception in UserStore1: " + e.getMessage());
    			e.printStackTrace();
    		}
    		//reset the form
    		userForm.reset();
    	}

    	//  EDIT
    	else if (strType.equalsIgnoreCase(NEDSSConstants.EDIT)) {
    		try {
    			setAdminUserType(userProfile, strType,isUserMSA);
    			userProfile.setItDirty(true);

    			//Get facility info
    			String facilityID = request.getParameter("Org-ReportingOrganizationUID");
    			if (facilityID != null && !facilityID.equals("")) {
    				long facIdAsLong = Long.parseLong(facilityID.trim());
    				userProfile.theUser.setReportingFacilityUid(new Long(facIdAsLong));
    			}
    			else {
    				userProfile.theUser.setReportingFacilityUid(null);
    			}

    			String providerID = request.getParameter("Prv-providerUID");
    			if (providerID != null && !providerID.equals("")) {
    				long prvIdAsLong = Long.parseLong(providerID.trim());
    				userProfile.theUser.setProviderUid(new Long(prvIdAsLong));
    			}
    			else {
    				userProfile.theUser.setProviderUid(null);
    			}

    			String external = request.getParameter("userProfile.theUser_s.userType");
    			if (external != null && external.equals("Y")) {
    				userProfile.theUser.setUserType(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
    			}
    			else {
    				userProfile.theUser.setUserType(NEDSSConstants.SEC_USERTYPE_INTERNAL);
    			}

    			userProfile.theUser.setJurisdictionDerivationInd(request.getParameter("userProfile.theUser_s.jurisdictionDerivationInd"));

    			//hardcoding necessary fields for ldap
    			userProfile.getTheUser().setComments("");
    			userProfile.getTheUser().setEntryID("");
    			userProfile.getTheUser().setPassword("");
    			userProfile.getTheUser().setItDirty(true);

    			itr = userProfile.getTheRealizedRoleCollection().iterator();

    			while (itr.hasNext()) {

    				rr = (RealizedRole) itr.next();

    				//       check if there is a new one added or existing one needs to be deleted
    				if (rr.getStatusCd().equals("I")) {
    					// delete the role
    					rr.setItDelete(true);
    					rr.setItNew(false);
    					rr.setItDirty(false);
    				}
    				else { // else 1 - not a delete
    					if (rr.getSeqNum() < 1) {
    						// new role
    						rr.setItDirty(false);
    						rr.setItNew(true);
    						rr.setItDelete(false);

    						if (rr.getGuestString().equals("Y")) {
    							rr.setGuest(true);
    						}
    						else {
    							rr.setGuest(false);
    						}
    					}
    					else { // else 2 - not a new role - update the existing roles
    						if (rr.getGuestString().trim().equals("Y")) {
    							rr.setGuest(true);
    						}
    						else {
    							rr.setGuest(false);
    						}

    						rr.setItDirty(true);
    						rr.setItNew(false);
    						rr.setItDelete(false);

    					} // end of else 2

    				} // end of else 1

    			} // end of while

    			userForm.reset();

    			MainSessionCommand msCommand = null;

    			String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
    			String sMethod = "setUser";
    			Object[] oParams = new Object[] {
    					userProfile};

    			MainSessionHolder holder = new MainSessionHolder();
    			msCommand = holder.getMainSessionCommand(session);

    			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
    					oParams);

    		}
    		catch (Exception e) {
    			logger.error("Exception in UserStore2: " + e.getMessage());
    			e.printStackTrace();
    		}
    	}
    	ActionForward af = mapping.findForward("next");
    	forwardNew = new ActionForward();
    	StringBuffer strURL = new StringBuffer(af.getPath());
    	UID = userProfile.getTheUser_s().getUserID();

    	strURL.append("&userID=").append(UID);

    	forwardNew.setPath(strURL.toString());
    	forwardNew.setRedirect(true);
    }catch (Exception e) {
    	logger.error("Exception in UserStore: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("Error occurred in UserStore : "+e.getMessage());
    }   
    return forwardNew;
  }

  private void setAdminUserType(UserProfile userProfile, String strType, boolean isUserMSA) {
    //Set the adminUserType
    if (strType.equalsIgnoreCase(NEDSSConstants.CREATE) ||(strType.equalsIgnoreCase(NEDSSConstants.EDIT)&& isUserMSA )){

    String msa = userProfile.getTheUser().getMsa();
    String paa = userProfile.getTheUser().getPaa();
    StringBuffer sb = new StringBuffer("");

    if (msa != null && msa.equalsIgnoreCase("Y")) {
      sb.append("MSA");
    }
    if (paa != null && paa.equalsIgnoreCase("Y")) {
      sb.append("PAA");
    }
    if (sb.length() > 3) {
      sb.insert(3, "-");
    }
    userProfile.getTheUser().setAdminUserType(sb.toString());
    }
  }

}
