package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        MergePersonsCommentsSubmit
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

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.person.vo.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergePersonsCommentsSubmit extends Action
{
	//For logging
	static final LogUtils logger = new LogUtils(MergePersonsCommentsSubmit.class.getName());

	public MergePersonsCommentsSubmit()
	{
	}

	/**
      * Used for submitting the page for entering comments while merging two persons.
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

	  	logger.debug("inside the MergePersonsCommentsSubmit Load");


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

		//##!! System.out.println("Context action inside MergePersonsCommentsSubmit is : " + contextAction);

		if (contextAction.equalsIgnoreCase("Merge"))
		{

			try
			{
				mergePersons(request, session, secObj );
			}
			catch (NEDSSAppConcurrentDataException e)
			{
				logger.fatal("ERROR , During merge persons, the person data has been modified by another user, please recheck! ", e);
				logger.debug("ERROR , During merge persons, the person data has been modified by another user, please recheck! ", e);
				//session.setAttribute("error", "During merge persons, the person data has been modified by another user, please recheck!");
				return mapping.findForward("dataerror");
			}
			catch (NEDSSAppException e)
			{
				logger.fatal("ERROR , During merge persons, a NEDSSAppException was encountered!", e);
				logger.debug("ERROR , During merge persons, a NEDSSAppException was encountered!", e);
				//session.setAttribute("error", "During merge persons, a NEDSSAppException with the following message - '"
				//+ e.getMessage()
				//+ "' was encountered");
				throw new ServletException("ERROR , During merge persons, a NEDSSAppException was encountered!"+e.getMessage(),e);

			}
			catch (Exception e)
			{
				logger.fatal("ERROR , During merge persons, a general exception was encountered!", e);
				//session.setAttribute("error", "During merge persons, a general exception was encountered with the following description - '" + e.toString());
				throw new ServletException("ERROR , During merge persons, a general exception was encountered!"+e.getMessage(),e);
			}

		}

		return mapping.findForward(contextAction);

	}


	/**
      * Merges the two PersonVO's together before sending the end result PersonVO to the backend.
      * @J2EE_METHOD  --  mergePersons
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @return secObj    the  NBSSecurityObj
      * @throws NEDSSAppConcurrentDataException
      * @throws NEDSSAppException
      * @throws Exception
      **/
	private void mergePersons(HttpServletRequest request, HttpSession session,
			NBSSecurityObj secObj ) throws NEDSSAppConcurrentDataException, NEDSSAppException, Exception
	{

		// Retrieve the surviving and superceded Person VOs from the object store
		PersonVO mergePerVOSurv = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_SURVIVING_VO);
		PersonVO mergePerVOSup = (PersonVO)NBSContext.retrieve(session, NEDSSConstants.DS_PERSON_MERGE_SUPERCEDED_VO);


		// Get the comments from the UI
		String mergeComments = request.getParameter("mergeComments");
		mergePerVOSurv.getThePersonDT().setDescription(mergeComments);

		 /**
		 * Call the mainsessioncommand
		 */

		MainSessionCommand msCommand = null;
		try
		{
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "mergePersons";


	    	Object[] oParams = new Object[]{mergePerVOSurv,	mergePerVOSup};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		}
		catch (NEDSSAppConcurrentDataException e)
		{
			throw new NEDSSAppConcurrentDataException(e.getMessage());
		}
		catch (NEDSSAppException e)
		{
			throw new NEDSSAppException(e.getMessage());
		}
		catch (Exception e)
		{
		  if(session == null)
		  {
			 logger.error("Error: no session, please login");
		  	 throw new Exception("Error: no session, please login");
		  }
		  else
		  {
			  logger.fatal("ERROR calling mainsession control " + e.getMessage(), e);
			  throw new Exception(e.toString());
	  	  }
		}

		finally
		{
			msCommand = null;

		}

	}


}