package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

/**
 * Title:        SelectPermissionSet
 * Description:  Struts Action Class
 * Copyright:    Copyright (c) 2002
 * Company:      CSC
 * @author       Wade Steele
 * @version 1.0
 */
import org.apache.struts.action.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.io.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthPermSetDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;
import gov.cdc.nedss.util.*;

public class SelectPermissionSet
    extends Action {

  public SelectPermissionSet() {
  }

  
  static final LogUtils logger = new LogUtils(SelectPermissionSet.class.getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    logger.info("inside SelectPermissionSet action class");
    
    String sBeanJndiName = "";
    String sMethod = "";
    Object[] oParams = null;
 
    HttpSession session = request.getSession(false);
    MainSessionCommand msCommand = null;
    MainSessionHolder mainSessionHolder = new MainSessionHolder();
 // Return Link
    String strLinkName = "Return to System Management Main Menu";
    String strLinkAddr = "/nbs/SystemAdmin.do?focus=systemAdmin6";
    request.setAttribute("LinkName", strLinkName);
    request.setAttribute("LinkAddr", strLinkAddr);
    
    NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    if (session == null) {
      logger.fatal("error no session, go back to login screen");
      return mapping.findForward("login");
    }

    try {

      boolean paaPermission = secObj.isUserPAA();
      if (paaPermission) {
        request.setAttribute("paaPermission", "true");
      }
      else {
        request.setAttribute("paaPermission", "false");

      }
      String operation = request.getParameter(PageConstants.OPERATIONTYPE);

      int int_operation;

      if (operation != null) {
        try {
          int_operation = (new Integer(operation).intValue());
        }
        catch (Exception ne) {
          logger.fatal("", ne);
          ne.printStackTrace();
          int_operation = NEDSSConstants.UNKNOWN_COMMAND;
        }
      }
      else {
        int_operation = NEDSSConstants.UNKNOWN_COMMAND;

      }
      logger.debug("OperationType for permSet is: " + operation);
      
      if (int_operation == NEDSSConstants.SELECT_PERMISSION_SET) {
      		sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;  
        
        sMethod = "getPermissionSetList";
        msCommand = mainSessionHolder.getMainSessionCommand(session);
        logger.info("About to call processRequest.");

        ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName,
            sMethod, null);
        logger.debug("Checkpoint - #2");
        // Do the translation here from secure DT to permissionset 03/20 by hon  
        Collection<Object> allPermsColl = (Collection<Object>)arr.get(0);
       

        StringBuffer sb = new StringBuffer();
       Iterator<Object>  iter = allPermsColl.iterator();
        String permsStr = "";
        while (iter.hasNext()) {
          permsStr = (String) iter.next();
          sb.append(permsStr).append("$").append(permsStr).append("|");
          logger.info(permsStr + " => " + permsStr);
        }
        permsStr = sb.toString();
        request.setAttribute("AllPermissionSetsStr", permsStr);
        return (mapping.findForward("select"));
      }
      else if (int_operation == NEDSSConstants.LOAD_PERMISSION_SETS) {
    		sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;  
    	
        sMethod = "getPermissionSets";
        msCommand = mainSessionHolder.getMainSessionCommand(session);
        logger.info("About to call processRequest.");

        ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName,
            sMethod, null);
        logger.debug("Checkpoint - #2");
       
        Collection<Object>  permSetColl = (Collection<Object>) arr.get(0);
               
        Collection<Object>  sysPerms = new ArrayList<Object> ();
        Collection<Object>  userDefPerms = new ArrayList<Object> ();
        
		if (permSetColl  != null && permSetColl .size() != 0) {
          logger.debug("GOING TO SORT THE PERM SETS");
          NedssUtils util = new NedssUtils();
          String sortMethod = "getRoleName";
          util.sortObjectByColumn(sortMethod, (Collection<Object>) permSetColl, true);
        }
        StringBuffer sbSysDefPermSets = new StringBuffer("");
        StringBuffer sbUserDefPermSets = new StringBuffer("");

       Iterator<Object>  iter = permSetColl.iterator();
        while (iter.hasNext()) {
          PermissionSet perm = (PermissionSet) iter.next();
          if (perm.getReadOnly()) {
            //sysPerms.add(perm);
            sbSysDefPermSets.append("--").append(perm.getRoleName()).append(
                "--").append("$");
            sbSysDefPermSets.append(perm.getDescription()).append("$").append(
                "**").append(XMLRequestHelper.urlEncode(perm.getRoleName())).
                append("**").append("|");

          }
          else {
            //userDefPerms.add(perm);
            sbUserDefPermSets.append("--").append(perm.getRoleName()).append(
                "--").append("$");
            sbUserDefPermSets.append(perm.getDescription()).append("$").append(
                "**").append(XMLRequestHelper.urlEncode(perm.getRoleName())).
                append("**").append("|");

          }
        }

        request.setAttribute("sysDefinedPermissionSets",
                             sbSysDefPermSets.toString());
        request.setAttribute("userDefinedPermissionSets",
                             sbUserDefPermSets.toString());

        return (mapping.findForward("load"));

      }
      return mapping.findForward("Error: No if statements were true!!!  Look at the SelectPermissionSet action class");

    }
    catch (NEDSSSystemException nae) {
      logger.error("Error: " + nae.toString());
      return mapping.findForward("Error: " + nae.toString());
    }
    catch (Exception e) {
      logger.error("Error: " + e.toString());
      return mapping.findForward("Error: " + e.toString());
    }
  }
  
  private void setallPermsColl(Collection<Object> permSetDTColl, Collection<Object> allPermsColl) throws Exception{ 
	  
	  try{
		  if(permSetDTColl != null && permSetDTColl.size()>0){
			  Iterator<Object>  permIter = permSetDTColl.iterator();
			  while(permIter.hasNext()){
				  AuthPermSetDT securePermDT = (AuthPermSetDT)permIter.next();
				  PermissionSet permSet = new PermissionSet();
				  permSet.setRoleName(securePermDT.getPermSetNm());
				  permSet.setDescription(securePermDT.getPermSetDesc());
				  if(securePermDT.getSysDefinedPermSetInd()!= null && securePermDT.getSysDefinedPermSetInd().equals("T"))
					  permSet.setReadOnly(true);
				  else 
					  permSet.setReadOnly(false);
				  allPermsColl.add(permSet);
			  }
	      }
	     }catch(Exception e){
	    	 logger.error("Error in setting the securePermission Coll to PermissionSet" );
	    	 throw new Exception(e.getMessage());
	     }

	}
  
}

 

