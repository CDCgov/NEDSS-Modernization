package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

import org.apache.struts.action.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.io.*;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class UsersManage extends Action{

    public UsersManage() {
    }

    static final LogUtils logger = new LogUtils (UsersManage.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {
      logger.info("inside UsersManage action class77777777777777");

      HttpSession session = request.getSession(false);
      MainSessionCommand msCommand = null;
      MainSessionHolder mainSessionHolder = new MainSessionHolder();
      
   // Return Link
      String strLinkName = "Return to System Management Main Menu";
      String strLinkAddr = "/nbs/SystemAdmin.do?focus=systemAdmin6";
      request.setAttribute("LinkName", strLinkName);
      request.setAttribute("LinkAddr", strLinkAddr);

      session.getAttribute("NBSSecurityObject");
      if (session == null)
      {
          logger.fatal("error no session, go back to login screen");
          return mapping.findForward("login");
      }


      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null)
          contextAction = (String)request.getAttribute("ContextAction");

	logger.debug("before call getPageContext with contextAction of: " + contextAction);


	// onReviewNotifPage is preserved while doing sorts and navigating pages.
	// If it is not present, we need to reload collection from EJB.
	String onUserListPage = request.getParameter("onUserListPage");
//	logger.debug( "onUserListPage = " + onUserListPage );


	/* Hit EJB if onUserListPage=null (meaning we either visiting it for the 1st time,
	 * or came back to it after navigating other pages)
	 */

     if ( onUserListPage == null) {
      try{
        boolean checkpoint = true;


	  if(!checkpoint)
	  {
	    logger.fatal("Error: You do not have access.");
	    throw new ServletException("Error: You do not have access.");
	  }

	    String sBeanJndiName = "";
	    String sMethod = "";

	  //debugging tester
	  Collection<Object> users   = new ArrayList<Object>();	  
		  sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
		  sMethod = "getSecureUserDTList";
		  msCommand = mainSessionHolder.getMainSessionCommand(session);
		  logger.info("About to call processRequest.");
	
		  ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
	          logger.debug("Checkpoint - #2");
	          Collection<Object>  secureUsrDTColl = (Collection)arr.get(0);
		  // Converting the SecureUserDTCollection  to  User object		  
		  convertSecureUsrtoUserColl(secureUsrDTColl,users);
		  // If the security is from LDAP
	//  sortMethod = "getLastName";
	 // util.sortObjectByColumn(sortMethod, users, true);
	  StringBuffer sbUser = new StringBuffer(""); 
	  if(users!=null && users.size()>0)
	  {
	  	Iterator ite = users.iterator();
	  	while(ite.hasNext())
	  	{
	  User user = (User) ite.next();
      //if (user.getReadOnly().equals("true")) {
        //sysPerms.add(perm);
      	sbUser.append(user.getLastName()).append("$");
      	sbUser.append(user.getFirstName()).append("$--")
		      .append(user.getUserID()).append("--$")
               .append(user.getStatus()).append("$").append(
            "**").append(java.net.URLEncoder.encode(user.getUserID(),"UTF-8")).
            append("**").append("|");
      }
    }


    request.setAttribute("usersList",
    		sbUser.toString());
 
	  session.setAttribute("usersCollection", users);
	  return (mapping.findForward("Next"));
      }
      catch(NEDSSSystemException nae){
	logger.error("Error: " + nae.toString());
	return mapping.findForward("Error: " + nae.toString());
      }
      catch(Exception e){
	logger.error("Error: " + e.toString());
	return mapping.findForward("Error: " + e.toString());
      }

   }
    return (mapping.findForward("Next"));
  }

    private void convertSecureUsrtoUserColl(Collection<Object> secureUsrDTColl, Collection<Object> users){
    	if(secureUsrDTColl != null && secureUsrDTColl.size()>0){
    		Iterator<Object> iter = secureUsrDTColl.iterator(); 
    		while(iter.hasNext()){
    			AuthUserDT secureUserDT = (AuthUserDT)iter.next();
    			User user = new User();
    			user.setUserID(secureUserDT.getUserId());
    			if(secureUserDT.getNedssEntryId() != null)
    				user.setEntryID(String.valueOf(secureUserDT.getNedssEntryId()));
    			user.setComments(secureUserDT.getUserComments());
    			user.setFirstName(secureUserDT.getUserFirstNm());
    			user.setLastName(secureUserDT.getUserLastNm());
    			user.setUserType(secureUserDT.getUserType()); 
    			if(secureUserDT.getRecordStatusCd()!=null && secureUserDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
    				user.setStatus(NEDSSConstants.RECORD_STATUS_Active);
    			else
    				user.setStatus(NEDSSConstants.RECORD_STATUS_Inactive);
    			//adding the user to the users collection
    			users.add(user);
    			
    		}
    	}
    	
    }
}
