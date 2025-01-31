package gov.cdc.nedss.webapp.nbs.action.message;

import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.queue.vo.MessageLogVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.message.MessageLogForm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.google.gson.JsonObject;

public class MessageQueueAction extends DispatchAction
{
    static final LogUtils         logger       = new LogUtils(MessageQueueAction.class.getName());
    private static final String   MESSAGE_LIST = "messageList";
    PropertyUtil                  properties   = PropertyUtil.getInstance();
    QueueUtil                     queueUtil    = new QueueUtil();
    private static final String[] FILTER_KEYS  = { "MESSAGESTATUS", "CONDITION", "SUBMITTEDBY", "PATIENT",
            "MESSAGETEXT", "STARTDATE"        };

    public ActionForward loadQueue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {

        MessageLogForm messageForm = (MessageLogForm) form;

        boolean forceEJBcall = false;
        
        // get page context
        String contextAction = request.getParameter(NEDSSConstants.CONTEXT_ACTION);
        if ("MessageQueue".equals(contextAction))
        { 
            DSQueueObject dSQueueObject = new DSQueueObject();
            dSQueueObject.setDSSortColumn("getAddTime");
            dSQueueObject.setDSSortDirection("true");
            dSQueueObject.setDSFromIndex("0");
            dSQueueObject.setDSQueueType(NEDSSConstants.MESSAGE_QUEUE);
            NBSContext.store(request.getSession(), "DSQueueObject", dSQueueObject);
        }
        else if ("ReturnToMessageQueue".equals(contextAction))
        {
            forceEJBcall = true; 
        }
        
        TreeMap<Object,Object> tm = NBSContext.getPageContext(request.getSession(), "PS501", contextAction);
        
        String sCurrTask = NBSContext.getCurrentTask(request.getSession());
        
        // Reset Pagination first time
        boolean initLoad = request.getParameter(NEDSSConstants.INIT_LOAD) == null ? false : Boolean.valueOf(
                request.getParameter(NEDSSConstants.INIT_LOAD)).booleanValue();

        if (initLoad && !PaginationUtil._dtagAccessed(request))
        {
            forceEJBcall = true;
            messageForm.clearAll();
            // get the number of records to be displayed per page
            Integer queueSize = properties.getQueueSize(NEDSSConstants.MESSAGE_QUEUE_SIZE);
            messageForm.getAttributeMap().put(NEDSSConstants.QUEUE_SIZE, queueSize);
        }
 
        // get security object
        NBSSecurityObj secObj = getNBSSecurityObj(request); 

        try
        {
            populateDropdowns(messageForm);
            List messageVOs = new ArrayList();
            if (forceEJBcall)
            {
                Long id = secObj.getTheUserProfile().getTheUser().getProviderUid();
                Object[] oParams = new Object[] {id};
                String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
                String sMethod = "getMessageQueue";
                Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
                messageVOs =(List)obj;
            
                setConditionList(messageForm, messageVOs);
                messageForm.getAttributeMap().put(MESSAGE_LIST, messageVOs);
            }
            else
            {
                messageVOs = (ArrayList) messageForm.getAttributeMap().get(MESSAGE_LIST);
            }

            if (messageForm.getSearchCriteriaArrayMap().size() == 0)
                request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));

            messageVOs = (ArrayList) getFilteredMessages(messageVOs, messageForm.getSearchCriteriaArrayMap(),request);
            sortMessagesHelper(messageForm, messageVOs, true, request);

            request.setAttribute(MESSAGE_LIST, messageVOs);
            request.setAttribute("queueCount", String.valueOf(messageVOs.size()));

            request.setAttribute(NEDSSConstants.PAGE_TITLE, "My Messages");

        }
        catch (Exception e)
        {
            logger.error("Error in getting messageLogVOs from EJB: " + e.getMessage());
            return mapping.findForward(NEDSSConstants.ERROR_PAGE);
        }
        return PaginationUtil.paginate(messageForm, request, "ViewMessageQueue", mapping);
    }
    
    public ActionForward viewSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException 
    {
        
        NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid, request.getParameter("publicHealthCaseUID")); 
        return (mapping.findForward("InvestigationID"));
    }

    public ActionForward deleteMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        try 
        {
            MessageLogForm messageForm = (MessageLogForm) form;
            // get security object
            NBSSecurityObj secObj = getNBSSecurityObj(request);

            MessageLogVO messageVO = messageForm.getMessageLogVO();

            messageVO.getMessageLogDT().setLastChgUserId(Long.valueOf(secObj.getEntryID()));
            messageVO.getMessageLogDT().setAssignedToUid(secObj.getTheUserProfile().getTheUser().getProviderUid());
            messageVO.getMessageLogDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
      
            Object[] oParams = new Object[] {messageVO};
            String sBeanJndiName = JNDINames.QUEUE_EJB;
            String sMethod = "deleteMessage";
            Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
            Integer resultCount = (Integer)(obj);
            logger.debug("resultCount obtained from MessageQueueAction.deleteMessage is "+ resultCount);
            return loadQueue(mapping, form, request, response);
        } 
        catch (Exception e) 
        {
            logger.error("ERROR obtained in MessageQueueAction.deleteMessage is " + e);
        }
        return null;
        
    }

    public ActionForward markMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {

        try 
        {
            MessageLogForm messageForm = (MessageLogForm) form;

            // get security object
            NBSSecurityObj secObj = getNBSSecurityObj(request);

            MessageLogVO messageVO = messageForm.getMessageLogVO();

            messageVO.getMessageLogDT().setAssignedToUid(secObj.getTheUserProfile().getTheUser().getProviderUid());
            messageVO.getMessageLogDT().setMessageStatusCd(MessageConstants.R);
            
            Object[] oParams = new Object[] {messageVO};
            String sBeanJndiName = JNDINames.QUEUE_EJB;
            String sMethod = "updateMessage";
            Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
            Integer resultCount = (Integer)(obj);
            if( resultCount == 1)
            {
                // Update the status in session list 
                ArrayList<MessageLogVO> messageVOs = (ArrayList) messageForm.getAttributeMap().get(MESSAGE_LIST);
                Long mid =  ( messageVO != null ? messageVO.getMessageLogDT().getMessageLogUid() : null );
                for( MessageLogVO m : messageVOs)
                {
                    if(mid != null && mid.equals(m.getMessageLogDT().getMessageLogUid()) )
                    {
                        m.getMessageLogDT().setMessageStatusCd(MessageConstants.R);
                    }
                }
                
            }
            
            
            response.setContentType("application/json");
            // Get the printwriter object from response to write the required json
            // object to the output stream
            PrintWriter out = response.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("result", resultCount == 1 ? "success" : "error");
            jsonObject.addProperty("messageLogUid", messageForm.getMessageLogVO().getMessageLogDT().getMessageLogUid());
            out.print(jsonObject);
            out.flush();
        } 
        catch (Exception e) 
        {
            logger.error("ERROR obtained in MessageQueueAction.markMessage is " + e);
        }
        return null;
    }

    /**
     * Helper method to sort the messages based on sort method and direction.
     * 
     * @param messageForm
     * @param notificationVOs
     * @param existing
     * @param request
     */
    private void sortMessagesHelper(MessageLogForm messageForm, Collection<Object> messageVOs, boolean existing,
            HttpServletRequest request)
    {
        try 
        {
            String sortMethod = getSortMethod(request, messageForm);
            String direction = getSortDirection(request, messageForm);

            boolean invDirectionFlag = true;
            if ( "2".equals(direction))
            {
                invDirectionFlag = false;
            }

            // Read from properties file to determine default sort order
            if (sortMethod == null ||  "none".equals(sortMethod) )
            {
                sortMethod = "getAddTime";
                invDirectionFlag = true;
            }

            NedssUtils util = new NedssUtils();
            if (sortMethod != null && messageVOs != null && messageVOs.size() > 0)
            {
                util.sortObjectByColumn(sortMethod, (Collection<Object>) messageVOs, invDirectionFlag);
            }
            // Finally put sort criteria in form
            String sortOrderParam = getSortCriteriaDescription(invDirectionFlag ? "1" : "2", sortMethod);
            Map searchCriteriaColl = new TreeMap();
            searchCriteriaColl.put("sortSt", sortOrderParam);
            messageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
        } 
        catch (Exception e) 
        {
            logger.error("ERROR obtained in MessageQueueAction.sortMessagesHelper is " + e);
        }
    }

    public String getSortCriteriaDescription(String sortOrder, String methodName)
    {
        String sortOrdrStr = null;
        try 
        {
            if (methodName != null)
            {
                if (methodName.equals("getAddTime"))
                    sortOrdrStr = "Date";
                else if (methodName.equals("getUserFullName"))
                    sortOrdrStr = "Submitted By";
                else if (methodName.equals("getMessageTxt"))
                    sortOrdrStr = "Message";
                else if (methodName.equals("getFullName"))
                    sortOrdrStr = "Patient";
                else if (methodName.equals("getConditionCd"))
                    sortOrdrStr = "Condition";
                else if (methodName.equals("getMessageStatus"))
                {
                    sortOrdrStr = "New?";
                }
            }
            else
            {
                sortOrdrStr = "Date";
            }

            if (sortOrder == null || "1".equals(sortOrder) )
                sortOrdrStr = sortOrdrStr + " in ascending order ";
            else if ("2".equals(sortOrder))
                sortOrdrStr = sortOrdrStr + " in descending order ";
        } 
        catch (Exception e) 
        {
            logger.error("ERROR obtained in MessageQueueAction.getSortCriteriaDescription is " + e);
        }

        return sortOrdrStr;
    }

    /**
     * Helper method to get the sort method as a string.
     * 
     * @param request
     * @param messageForm
     * @return
     */
    private String getSortMethod(HttpServletRequest request, MessageLogForm messageForm)
    {
        if (PaginationUtil._dtagAccessed(request))
        {
            return request.getParameter((new ParamEncoder("parent"))
                    .encodeParameterName(TableTagParameters.PARAMETER_SORT));
        }
        else
        {
            return messageForm.getAttributeMap().get("methodName") == null ? null : (String) messageForm
                    .getAttributeMap().get("methodName");
        }
    }

    /**
     * Helper method to get the sort direction as a string.
     * 
     * @param request
     * @param messageForm
     * @return
     */
    private String getSortDirection(HttpServletRequest request, MessageLogForm messageForm)
    {
        if (PaginationUtil._dtagAccessed(request))
        {
            return request.getParameter((new ParamEncoder("parent"))
                    .encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        }
        else
        {
            return messageForm.getAttributeMap().get("sortOrder") == null ? "1" : (String) messageForm
                    .getAttributeMap().get("sortOrder");
        }
    }

    private void populateDropdowns(MessageLogForm messageForm)
    {
        messageForm.initializeDropDowns();
    }

    /**
     * Apply the filters in the search criteria map to the existing set of
     * notifications and return the filtered results.
     * 
     * @param messageVOs
     * @param searchCriteriaMap
     * @return
     */
    public Collection<Object> getFilteredMessages(Collection<Object> messageVOs, Map searchCriteriaMap,HttpServletRequest request )
    {
        for (String key : FILTER_KEYS)
        {
            String[] filterArr = (String[]) searchCriteriaMap.get(key);
            if (filterArr != null && filterArr.length > 0)
            {
                messageVOs = filterMessagesByKey(messageVOs, filterArr, key,request);
            }
        }
        return messageVOs;
    }

    /**
     * Helper method to filter notifications by condition
     * 
     * @param messageVOs
     * @param testMap
     * @return
     */
    private Collection<Object> filterMessagesByKey(Collection<Object> messageVOs, String[] filterArr, String key,HttpServletRequest request)
    {
        try {
            Map filteredMessages = new HashMap();

            for (String filter : filterArr)
            {
                if (filter != null && filter.trim().length() > 0)
                {
                    Iterator iter2 = messageVOs.iterator();

                    while (iter2.hasNext())
                    {
                        MessageLogVO message = (MessageLogVO) iter2.next();
                        Long mid = message.getMessageLogDT().getMessageLogUid();
                        if ("MESSAGESTATUS".equals(key) && message.getMessageLogDT().getMessageStatusCd() != null
                                && message.getMessageLogDT().getMessageStatusCd().equals(filter))
                        {
                            filteredMessages.put(mid, message);
                        }
                        else if ("STARTDATE".equalsIgnoreCase(key) && !NEDSSConstants.DATE_BLANK_KEY.equals(filter))
                        {
                            if (queueUtil.isDateinRange(new Timestamp(message.getMessageLogDT().getAddTime().getTime()), filter))
                            {
                                filteredMessages.put(mid, message);
                            }
                        }
                        else if ("PATIENT".equalsIgnoreCase(key))
                        {
                            if (message.getFullName().toUpperCase().contains(filter.toUpperCase()))
                            {
                                filteredMessages.put(mid, message);
                            }
                        }
                        else if ("SUBMITTEDBY".equalsIgnoreCase(key))
                        {
                            if (message.getUserFullName().toUpperCase().contains(filter.toUpperCase()))
                            {
                                filteredMessages.put(mid, message);
                            }
                        }
                        else if( "MESSAGETEXT".equalsIgnoreCase(key))
                        {
                            String mt = message.getMessageTxt();
                            if (mt != null && mt.toUpperCase().contains(filter.toUpperCase()))
                            {
                                filteredMessages.put(mid, message);
                            }
                        }
                        else if( "CONDITION".equalsIgnoreCase(key))
                        {
                            String mt = message.getMessageLogDT().getConditionCd();
                            if (mt != null && mt.toUpperCase().contains(filter.toUpperCase()))
                            {
                                filteredMessages.put(mid, message);
                            }
                           request.setAttribute("CONDITIONFILTER", "True");
                           
                        }
                    }
                }
            }

            return convertMaptoCollection(filteredMessages);
        } 
        catch (Exception e) 
        {
            logger.error("ERROR obtained in MessageQueueAction.filterMessagesByKey is " + e);
        }
        return null;
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
    
    public NBSSecurityObj getNBSSecurityObj(HttpServletRequest request)
    {
        return (NBSSecurityObj) request.getSession().getAttribute(NEDSSConstants.NBS_SECURITY_OBJECT);
    }
    
    private void setConditionList(MessageLogForm messageForm, List<MessageLogVO> messageVOs)
    {
        try
        {
            Map m = new HashMap();
            ArrayList al = new ArrayList();
            for (MessageLogVO mVO : messageVOs)
            {
                MessageLogDT mldt = mVO.getMessageLogDT();
                if (!m.containsKey(mldt.getConditionCd()))
                {
                    DropDownCodeDT cdDT = new DropDownCodeDT();
                    cdDT.setKey(mldt.getConditionCd());
                    cdDT.setValue(mldt.getConditionCd());
                    m.put(mldt.getConditionCd(), cdDT);
                    al.add(cdDT);
                }
                
                String phcLink = mldt.getConditionCd();
                mldt.setConditionCd(phcLink);
            }
            messageForm.setConditionList(al);
        }
        catch (Exception e)
        {
            logger.error("ERROR obtained in MessageQueueAction.setConditionList is " + e);
        }
    }

}