package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        PersonHistorySumLoad
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Ning Peng
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import javax.rmi.PortableRemoteObject;

public class PersonHistorySumLoad extends Action {

   static final LogUtils logger = new LogUtils(PersonHistorySumLoad.class.getName());

   public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {
      logger.info("inside the PersonHistorySumLoad");

      HttpSession session = request.getSession(false);
/*      if (session == null)
      {
          logger.fatal("error no session, go to error screen");
          return mapping.findForward("error");
      }
*/
      NBSSecurityObj securityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

      String personUID = request.getParameter("personUID");

      if (personUID == null)
         personUID = (String) request.getAttribute("personUID");

      Collection<?>  personHistItems =(Collection<?>)this.getPersonHistSum(personUID);

      request.setAttribute("personHistItems", personHistItems);

      return (mapping.findForward("next"));
   }

    private Collection<?>  getPersonHistSum(String personUID)
    {

      logger.debug("getPersonHistSum has been called, personUID = "+ personUID);
      Collection<?>  personHistItemsColl = null;
      try
      {
	NedssUtils nedssUtils = new NedssUtils();
	//go through msc
	String sBeanName = JNDINames.MAIN_CONTROL_EJB;
	Object objref1 = nedssUtils.lookupBean(sBeanName);
        //##!! System.out.println("objref1 = " + objref1.toString());
        MainSessionCommandHome home =(MainSessionCommandHome)PortableRemoteObject.narrow(objref1,MainSessionCommandHome.class);
        MainSessionCommand msc = home.create();
        //##!! System.out.println("msc = " + msc.getClass());

	//for testing
	personUID = "605062583";

	Object[] objArry = {new Long(personUID)};
	ArrayList<?> ar1 = new ArrayList<Object> ();
	String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        ar1 = msc.processRequest(sBeanJndiName, "getPersonHistItems", objArry);
	personHistItemsColl =(Collection<?>)ar1.get(0);
       }
       catch(Exception ex)
       {
          logger.fatal("getPersonHistSum: ", ex);
       }

    return personHistItemsColl;

  }

}