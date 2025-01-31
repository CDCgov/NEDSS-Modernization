package gov.cdc.nedss.webapp.nbs.action.person;
/**
 * Title:        MergePersonsCompareSubmit
 * Description:  This class interacts with the user and retrieves and submits to the back end to Merge Persons.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Karthik Murthy
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.person.vo.*;



//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergePersonsCompareSubmit extends Action {

	//For logging
	static final LogUtils logger = new LogUtils(MergePersonsCompareSubmit.class.getName());

	public MergePersonsCompareSubmit() {
	}

	   /**
      * Handles the identiying of the surviving record for the merge, or allows a cancel.
      * @J2EE_METHOD  --  perform
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException
	{

	  	logger.debug("inside the MergePersonsCompareSubmit Load");

		HttpSession session = request.getSession(false);
		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
						"NBSSecurityObject");

		String strType = request.getParameter("OperationType");

		if (strType == null) {
			strType = (String)request.getAttribute("OperationType");
		}

		String contextAction = request.getParameter("ContextAction");

		if (contextAction == null) {
			contextAction = (String)request.getAttribute("ContextAction");
		}

		//##!! System.out.println("Context action inside MergePersonsCompareSubmit is : " + contextAction);

		if (contextAction.equalsIgnoreCase("Merge"))
		{

			try
			{

				// Retrive the 2 PersonVOs from the obj store
				PersonVO mergePerVO1 = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_VO1);
				PersonVO mergePerVO2 = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_VO2);

				if ((mergePerVO1 != null) && (mergePerVO2 != null))
				{

					//##!! System.out.println("EntityTop1 is "+ request.getParameter("entityTop1"));
					//##!! System.out.println("EntityTop2 is " + request.getParameter("entityTop2"));

					logger.debug("EntityTop1 check box is " + request.getParameter("entityTop1"));
					logger.debug("EntityTop2 check box is " + request.getParameter("entityTop2"));

					// Person 1 is the surviving record
					if (request.getParameter("entityTop1") != null)
					{
						if (request.getParameter("entityTop1").equals("on"))
						{
							logger.debug("Person 1 is the surviving record");
							// Add Person1 VO as surviving rec and Perso2 VO as superceeded record
							NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_SURVIVING_VO, mergePerVO1);
							NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_SUPERCEDED_VO, mergePerVO2);

						} // Person 2 is the surviving record
					}
					else if (request.getParameter("entityTop2") != null)
					{
						if (request.getParameter("entityTop2").equals("on"))
						{
							logger.debug("Person 2 is the surviving record");
							// Add Person2 VO as surviving rec and Perso1 VO as superceeded record
							NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_SURVIVING_VO, mergePerVO2);
							NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_SUPERCEDED_VO, mergePerVO1);

						}
					}
				}

			}
			catch(Exception e)
			{
				logger.error("Error while submitting in MergePersonsCompareSubmit: " + e);
				e.printStackTrace();

			}
		}

		return mapping.findForward(contextAction);

	}

}