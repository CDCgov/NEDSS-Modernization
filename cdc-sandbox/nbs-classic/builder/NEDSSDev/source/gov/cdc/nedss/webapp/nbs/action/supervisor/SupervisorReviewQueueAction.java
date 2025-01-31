package gov.cdc.nedss.webapp.nbs.action.supervisor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.vo.SupervisorReviewVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil; 
import gov.cdc.nedss.webapp.nbs.form.supervisor.SupervisorReviewForm;

public class SupervisorReviewQueueAction extends DispatchAction
{
	static final LogUtils	                 logger	              = new LogUtils(
	                                                                      SupervisorReviewQueueAction.class.getName());
	PropertyUtil	                         properties	          = PropertyUtil.getInstance();
	QueueUtil	                             queueUtil	          = new QueueUtil();
	private static final String[]	         FILTER_KEYS	      = { "CONDITION", "PATIENT", "STARTDATE","SUPERVISOR", "INVESTIGATOR", "REFERRALBASIS","TYPE"};
	private static final String              INVESTIGATION_LIST = "investigationList";
	 
	
	private static final Map<String, String>	sortMethodMapping	= new HashMap<String, String>();

	static
	{
		sortMethodMapping.put("getSubmitDate", "Submit Date");
		sortMethodMapping.put("getPatientFullName", "Patient");
		sortMethodMapping.put("getSupervisorFullName", "Supervisor");
		sortMethodMapping.put("getInvestigatorFullName", "Investigator");
		sortMethodMapping.put("getCondition", "Condition");
		sortMethodMapping.put("getReferralBasisCd", "Referral Basis");
		sortMethodMapping.put("getActivityType", "Type");
	}

	public ActionForward loadQueue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response) throws IOException, ServletException
	{ 
		SupervisorReviewForm reviewForm = (SupervisorReviewForm) form;

		boolean forceEJBcall = false;

		String contextAction = request.getParameter(NEDSSConstants.CONTEXT_ACTION);
		if ("SupervisorReviewQueue".equals(contextAction))
		{
			DSQueueObject dSQueueObject = new DSQueueObject();
			dSQueueObject.setDSSortColumn("getAddTime");
			dSQueueObject.setDSSortDirection("true");
			dSQueueObject.setDSFromIndex("0");
			dSQueueObject.setDSQueueType(NEDSSConstants.SUPERVISOR_REVIEW_QUEUE);
			NBSContext.store(request.getSession(), "DSQueueObject", dSQueueObject);
		}
		else if ("ReturnToSupervisorReviewQueue".equals(contextAction))
		{
			forceEJBcall = true;
		}

		TreeMap<Object, Object> tm = NBSContext.getPageContext(request.getSession(), "PS502", contextAction);

		String sCurrTask = NBSContext.getCurrentTask(request.getSession());

		// Reset Pagination first time
		boolean initLoad = request.getParameter("initLoad") == null ? false : Boolean.valueOf(
		        request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request))
		{
			reviewForm.clearAll();

			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.SUPERVISOR_REVIEW_QUEUE_SIZE);
			reviewForm.getAttributeMap().put("queueSize", queueSize);
		}
		try
		{
			List srList = null;
			if (forceEJBcall)
			{
				PublicHealthCaseDAOImpl phcDao = new PublicHealthCaseDAOImpl();
				srList = phcDao.getPublicHealthCasesBySupervisor(getNBSSecurityObj(request));
				reviewForm.setSupervisorVos(srList);
				reviewForm.initializeDropDowns();
				reviewForm.getAttributeMap().put(INVESTIGATION_LIST, srList);
			}
			else
			{
				srList = (ArrayList) reviewForm.getAttributeMap().get(INVESTIGATION_LIST);
			}
			
			srList = (ArrayList) getFilteredRecords(srList, reviewForm.getSearchCriteriaArrayMap());
			request.setAttribute("queueCount", String.valueOf(srList.size()));
			sortMessagesHelper(reviewForm, srList, request);
			request.setAttribute(INVESTIGATION_LIST, srList);
			request.setAttribute(NEDSSConstants.PAGE_TITLE, "Supervisor Review Queue");
			 
		}
		catch (Exception e)
		{
			logger.error("Error in loadQueue() in getting Supervisor Review Records: " + e.getMessage());
			e.printStackTrace();
			return mapping.findForward(NEDSSConstants.ERROR_PAGE);
		}

		return PaginationUtil.paginate(reviewForm, request, "SupervisorReview", mapping);
	}

	@SuppressWarnings("unchecked")
    public ActionForward updateInvestigationClosure(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response) throws IOException, ServletException
	{
		SupervisorReviewForm reviewForm = (SupervisorReviewForm) form;
		
		if( NEDSSConstants.ACCEPT.equalsIgnoreCase(reviewForm.getSupervisorVO().getCaseReviewStatus()))
		{
		    reviewForm.getSupervisorVO().setCaseReviewStatus(NEDSSConstants.ACCEPT);
		}
		else if( NEDSSConstants.REJECT.equalsIgnoreCase(reviewForm.getSupervisorVO().getCaseReviewStatus()))
		{
		    reviewForm.getSupervisorVO().setCaseReviewStatus(NEDSSConstants.REJECT);
		}
		
		Object[] oParams = new Object[] {reviewForm.getSupervisorVO()};
        String sBeanJndiName = JNDINames.QUEUE_EJB;
        String sMethod = "updateInvestigationClosure";
        Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
        if( obj != null )
        {
            request.setAttribute("confirmationMessage", "Investigation updated successfully...");
            removeFromArray((ArrayList<SupervisorReviewVO>)reviewForm.getAttributeMap().get(INVESTIGATION_LIST), 
                            reviewForm.getSupervisorVO()); 
        } 
		return loadQueue(mapping, form, request, response);
	} 

	public ActionForward viewInvestigation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response) throws IOException, ServletException
	{

		HttpSession session = request.getSession();

		// get page context for debugging purposes
		String contextAction = request.getParameter(NEDSSConstants.CONTEXT_ACTION);
		if (contextAction == null)
			contextAction = (String) request.getAttribute(NEDSSConstants.CONTEXT_ACTION);

		String investigationID = request.getParameter("publicHealthCaseUID");
		String currentIndex = request.getParameter("currentIndex");

		String sortColumn = request.getParameter("sortMethod");
		String sortDirection = request.getParameter("direction");

		if (currentIndex == null)
			currentIndex = (String) request.getAttribute("currentIndex");

		DSQueueObject dsQueueObject = (DSQueueObject) NBSContext.retrieve(session, "DSQueueObject");
		if (currentIndex != null)
			dsQueueObject.setDSFromIndex(currentIndex);
		if (sortColumn != null)
			dsQueueObject.setDSSortColumn(sortColumn);
		if (sortDirection != null)
			dsQueueObject.setDSSortDirection(sortDirection);

		NBSSecurityObj nbsSecurityObj = getNBSSecurityObj(request);

		if (!nbsSecurityObj.getPermission(NBSBOLookup.QUEUES, NBSOperationLookup.MESSAGE,
		        ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
		{
			logger.fatal("Error: no permisstion to review notification, go back to login screen");
			throw new ServletException("Error: no permisstion to review notification, go back to login screen");
		}

		try
		{
			if ("InvestigationID".equals(contextAction))
			{
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, investigationID);
				return mapping.findForward(contextAction);
			}
			else if ( "ViewFile".equalsIgnoreCase(contextAction))
			{
				String MPRUidString = (String) request.getParameter("MPRUid");
				Long MPRUid = new Long(MPRUidString);
				NBSContext.store(session, "DSPatientPersonUID", MPRUid);
				NBSContext.store(session, "DSFileTab", "1");
				return mapping.findForward(contextAction);

			}
			else
			{
				logger.info("Nothing matched. Simply forwarding on using ContextAction");
				return mapping.findForward(contextAction);
			}
		} 
		catch (Exception e)
		{
			logger.error("Problem happened in the SupervisorReviewAction struts class", e);
			throw new ServletException("Problem happened in the SupervisorReviewAction struts class" + e.getMessage(),
			        e);
		}
	}

	/**
	 * Helper method to sort the messages based on sort method and direction.
	 * 
	 * @param supervisorForm
	 * @param notificationVOs
	 * @param request
	 */
	private void sortMessagesHelper(SupervisorReviewForm form, List supervisorVOs, HttpServletRequest request)
	{
		String sortMethod = getSortMethod(request, form);
		String direction = getSortDirection(request, form);

		boolean invDirectionFlag = true;
		if ("2".equals(direction))
		{
			invDirectionFlag = false;
		}

		// Read from properties file to determine default sort order
		if (sortMethod == null || "none".equals(sortMethod))
		{
			sortMethod = "getSubmitDate";
			invDirectionFlag = true;
		}

		NedssUtils util = new NedssUtils();
		if (sortMethod != null && supervisorVOs != null && supervisorVOs.size() > 0)
		{
			util.sortObjectByColumn(sortMethod, (Collection<Object>) supervisorVOs, invDirectionFlag);
		}
		// Finally put sort criteria in form
		String sortOrderParam = getSortCriteriaDescription(invDirectionFlag ? "1" : "2", sortMethod);
		Map searchCriteriaColl = new TreeMap();
		searchCriteriaColl.put("sortSt", sortOrderParam);
		form.getAttributeMap().put("searchCriteria", searchCriteriaColl);
	}

	public String getSortCriteriaDescription(String sortOrder, String methodName)
	{
		String sortOrdrStr = null;
		if (methodName != null)
		{
			sortOrdrStr = (String) sortMethodMapping.get(methodName);
		}
		else
		{
			sortOrdrStr = "Date";
		}

		if (sortOrder == null || "1".equals(sortOrder))
			sortOrdrStr = sortOrdrStr + " in ascending order ";
		else if ("2".equals(sortOrder))
			sortOrdrStr = sortOrdrStr + " in descending order ";

		return sortOrdrStr;
	}

	/**
	 * Helper method to get the sort method as a string.
	 * 
	 * @param request
	 * @param supervisorForm
	 * @return
	 */
	private String getSortMethod(HttpServletRequest request, SupervisorReviewForm supervisorForm)
	{
		if (PaginationUtil._dtagAccessed(request))
		{
			return request.getParameter((new ParamEncoder("parent"))
			        .encodeParameterName(TableTagParameters.PARAMETER_SORT));
		}
		else
		{
			return supervisorForm.getAttributeMap().get("methodName") == null ? null : (String) supervisorForm
			        .getAttributeMap().get("methodName");
		}
	}

	/**
	 * Helper method to get the sort direction as a string.
	 * 
	 * @param request
	 * @param supervisorForm
	 * @return
	 */
	private String getSortDirection(HttpServletRequest request, SupervisorReviewForm supervisorForm)
	{
		if (PaginationUtil._dtagAccessed(request))
		{
			return request.getParameter((new ParamEncoder("parent"))
			        .encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		}
		else
		{
			return supervisorForm.getAttributeMap().get("sortOrder") == null ? "1" : (String) supervisorForm
			        .getAttributeMap().get("sortOrder");
		}
	}

	/**
	 * Apply the filters in the search criteria map to the existing set of
	 * notifications and return the filtered results.
	 * 
	 * @param messageVOs
	 * @param searchCriteriaMap
	 * @return
	 */
	public Collection<Object> getFilteredRecords(Collection<Object> recordVOs, Map searchCriteriaMap)
	{
		for (String key : FILTER_KEYS)
		{
			String[] filterArr = (String[]) searchCriteriaMap.get(key);
			if (filterArr != null && filterArr.length > 0)
			{
				recordVOs = filterRecordsByKey(recordVOs, filterArr, key);
			}
		}
		return recordVOs;
	}

	/**
	 * Helper method to filter notifications by condition
	 * 
	 * @param messageVOs
	 * @param testMap
	 * @return
	 */
	private Collection<Object> filterRecordsByKey(Collection<Object> messageVOs, String[] filterArr, String key)
	{
		Map filteredMessages = new HashMap();

		for (String filter : filterArr)
		{
			if (filter != null && filter.trim().length() > 0)
			{
				Iterator iter2 = messageVOs.iterator();

				while (iter2.hasNext())
				{
					SupervisorReviewVO review = (SupervisorReviewVO) iter2.next();
					Long mapKey = review.getPublicHealthCaseUid();
					if ("STARTDATE".equalsIgnoreCase(key) && !NEDSSConstants.DATE_BLANK_KEY.equals(filter))
					{
						if (review.getSubmitDate() != null && queueUtil.isDateinRange(review.getSubmitDate(), filter))
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if ("STARTDATE".equalsIgnoreCase(key) && NEDSSConstants.DATE_BLANK_KEY.equals(filter))
					{
						if (review.getSubmitDate() == null )
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if ("PATIENT".equalsIgnoreCase(key))
					{
						if (contains ( review.getPatientFullName(), filter.toUpperCase() ) )
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if ("INVESTIGATOR".equalsIgnoreCase(key))
					{
						if (contains ( review.getInvestigatorFullName(), filter) )
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if ("SUPERVISOR".equalsIgnoreCase(key))
					{
						if (contains ( review.getSupervisorFullName(), filter) )
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if ("CONDITION".equalsIgnoreCase(key))
					{
						if (contains( review.getCondition(), filter) )
						{
							filteredMessages.put(mapKey, review);
						}
					}
					else if( "REFERRALBASIS".equalsIgnoreCase(key))
                    {
                        if ( contains( review.getReferralBasisCd(), filter))
                        {
                            filteredMessages.put(mapKey, review);
                        }
                    }
					else if("TYPE".equalsIgnoreCase(key)){
						 if ( contains( review.getActivityType(), filter))
	                        {
	                            filteredMessages.put(mapKey, review);
	                        }
					}
				}
			}
		}

		return convertMaptoCollection(filteredMessages);
	}

	private Collection<Object> convertMaptoCollection(Map map)
	{
		Collection collection = new ArrayList();
		if (map != null && map.size() > 0)
		{
			Collection<Object> invKeyColl = map.keySet();
			Iterator<Object> iter = invKeyColl.iterator();
			while (iter.hasNext())
			{
				collection.add(map.get(iter.next()));
			}
		}
		return collection;
	}
	
	private boolean contains(String value, String key)
	{
	    if( value != null && key != null )
	    {
	        return ( value.toUpperCase().contains(key.toUpperCase()));
	    }
	    return false;
	}

	public NBSSecurityObj getNBSSecurityObj(HttpServletRequest request)
	{
		return (NBSSecurityObj) request.getSession().getAttribute(NEDSSConstants.NBS_SECURITY_OBJECT);
	}
	
	public ArrayList removeFromArray(ArrayList<SupervisorReviewVO> arList, SupervisorReviewVO currentVO)
	{
	    Iterator<SupervisorReviewVO> iter = arList.iterator();
	    while(iter.hasNext())
        {
	        SupervisorReviewVO sr = iter.next();
            if( sr.getPublicHealthCaseUid() != null && sr.getPublicHealthCaseUid().equals(currentVO.getPublicHealthCaseUid()))
            {
                iter.remove();
            }
        }
	    return arList;
	}

}
