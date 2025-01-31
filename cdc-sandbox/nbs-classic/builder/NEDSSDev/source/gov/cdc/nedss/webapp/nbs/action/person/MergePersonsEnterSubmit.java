package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        MergePersonsEnterSubmit
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
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergePersonsEnterSubmit extends Action {

	//For logging
	static final LogUtils logger = new LogUtils(MergePersonsEnterSubmit.class.getName());

	public MergePersonsEnterSubmit() {
	}


	/**
      * Handles the identifying of the two records to be merged.
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

	  	logger.debug("inside the MergePersonsEnterSubmit Load");

		HttpSession session = request.getSession(false);
		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
						"NBSSecurityObject");

		String strType = request.getParameter("OperationType");

		if (strType == null) {
			strType = (String)request.getAttribute("OperationType");
		}

		String contextAction = request.getParameter("ContextAction");

		//##!! System.out.println("Context action inside MergePersonsEnterSubmit is : " + contextAction);

		if (contextAction == null) {
			contextAction = (String)request.getAttribute("ContextAction");
		}

		if (contextAction.equalsIgnoreCase("Submit"))
		{

			PersonVO mergePerVO1 = null;
			PersonVO mergePerVO2 = null;

			try
			{
				// Get the collection of PersonVOs for the 2 persons
				ArrayList<Object> personVOs = (ArrayList<Object> )getPersonVOs(request, session, secObj);


				if (personVOs.size() == 2)
				{
					// Loop through the collection of Person VOs
					Iterator<Object> itr = null;
					itr = personVOs.iterator();
					int index = -1;

					while (itr.hasNext())
					{
						index++;

							// First one is PersonVO1
							if (index == 0)
							{

							  mergePerVO1 = (PersonVO)itr.next();

							}
							// Second one is PersonVO2
							else if (index == 1)
							{
							  mergePerVO2 = (PersonVO)itr.next();
							}

					}
					if ((mergePerVO1 != null) && (mergePerVO2 != null))
					{
						// Add the PersonVOs to object store
						NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_VO1, mergePerVO1);
						NBSContext.store(session, NEDSSConstants.DS_PERSON_MERGE_VO2, mergePerVO2);
					}

				}
			}
			catch(ClassCastException e)
			{
				logger.error("ClassCastException Error while submitting in MergePersonsEnterSubmit: " + e);
				e.printStackTrace();

			}
			catch(Exception e)
			{
				logger.error("Error while submitting in MergePersonsEnterSubmit: " + e);
				e.printStackTrace();

			}
		}

		return mapping.findForward(contextAction);

	}

	/**
      * Retrieves the personVOs of the persons to be merged.
      * @J2EE_METHOD  --  getPersonVOs
      * @param request       the HttpServletRequest
      * @return session    the  HttpSession
      * @return secObj    the  NBSSecurityObj
      **/
	private Collection<Object>  getPersonVOs (HttpServletRequest request, HttpSession session,
		NBSSecurityObj secObj)
	{

		String localIdPer1 = null;
		String localIdPer2 = null;
		PersonVO mergePerVO1 = null;
		PersonVO mergePerVO2 = null;
		Collection<Object>  retPerVOColl = null;
		Collection<Object>  sysIdCollection  = null;
		//------------
		// Get the person system id from the request for the 2 persons
		//------------

		Long sysIdPer1 = new Long(request.getParameter("personId1"));
		Long sysIdPer2 = new Long(request.getParameter("personId2"));

		//Temporary hardcoding...TO BE REMOVED------------
		//Long sysIdPer1 = new Long(605404230);
		//Long sysIdPer2 = new Long(605328958);
		//-------------------------------------------------
		//##!! System.out.println("Person System Id 1= " + sysIdPer1.toString());
		//##!! System.out.println("Person System Id 2= " + sysIdPer2.toString());

		if ((sysIdPer1 != null) && (sysIdPer2 != null))
		{

			//------------
			// Create a collection of the 2 system ids
			//------------
			sysIdCollection  = new ArrayList<Object> ();
			sysIdCollection.add(sysIdPer1);
			sysIdCollection.add(sysIdPer2);

			//------------
			// From Entity Proxy, retrieve the PersonVOs collection by
			// sending the system id collection
			//------------
			try
			{
				retPerVOColl = sendProxyToGetPersons(sysIdCollection, session, secObj);
			}
			catch (NEDSSAppConcurrentDataException e)
			{
					logger.fatal("ERROR , The data has been modified by another user, please recheck! ", e);
					session.setAttribute("error", "This page has been modified by another user, please recheck");
					return null;
			}

		}
		else
		{
			// throw exception
		}

		return retPerVOColl;

	}

	/**
      * Retrieves the personVOs of the persons to be merged.
      * @J2EE_METHOD  --  getPersonVOs
      * @param sysIdCollection        the Collection
      * @return session    the  HttpSession
      * @return secObj    the  NBSSecurityObj
      * @throws NEDSSAppConcurrentDataException
      **/
	private Collection<Object>  sendProxyToGetPersons(Collection<Object> sysIdCollection, HttpSession session,
			NBSSecurityObj secObj) throws NEDSSAppConcurrentDataException
	{
		 /**
		 * Call the mainsessioncommand
		 */

		 MainSessionCommand msCommand = null;
		 try
		 {
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getPersons";

			//##!! System.out.println("Number of elements in UID collection that is passed to EntityProxy.getPersons is " +	sysIdCollection.size());

	    	Object[] oParams = new Object[]{sysIdCollection};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			//##!! System.out.println("Jndi name= " + sBeanJndiName + " Method= " + sMethod);

			Collection<Object>  resultPerVOs = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);

			if ((resultPerVOs != null)) //&& (resultPerVOs.size() > 0))
			{
				logger.debug("Found PersonVOs");
				return resultPerVOs;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
		  if(session == null)
		  {
			 logger.error("Error: no session, please login");
			 return null;
		  }
		  logger.fatal("ERROR calling mainsession control " + e.getMessage(), e);
		  return null;
		}

		finally
		{
			msCommand = null;

		}

	}

/*	private Long getPersonSystemId (String perLocalId, HttpServletRequest request,
		HttpSession session, NBSSecurityObj secObj)
	{

		Long systemId = null;

		// Construct a PersonSearchVO and populate systemId into it
		PersonSearchVO perSearchVO = new PersonSearchVO();
		perSearchVO.setLocalID(perLocalId);

		// From Entity Proxy's findPerson to get a single PersonSrchResultVO

		if (perSearchVO != null)
		{

			NedssUtils nedssUtils = null;
			MainSessionCommand msCommand = null;
			String sBeanJndiName = "EntityProxyEJBRef";
			String sMethod = "findPerson";
			Object[] oParams = new Object[]
			{
				perSearchVO, new Integer(1), new Integer(1), secObj
			};

			try
			{

				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);

				ArrayList<Object> arrList = msCommand.processRequest(sBeanJndiName,
										 sMethod, oParams);

				if (arrList.size() == 1)
				{
					PersonSrchResultVO srchResVO = (PersonSrchResultVO)arrList.get(0);
					systemId = srchResVO.getPersonUID();
				}
				else
				{
					// throw an exception
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// Return the system id from this search vo
		return systemId;

	}*/

}