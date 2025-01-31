package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util;

import gov.cdc.nedss.entity.observation.dt.LoincResultDT;
import gov.cdc.nedss.entity.observation.dt.ObservationNameDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DSMSummaryDisplay;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DecisionSupportLibraryForm;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;



import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class DSMLibraryActionUtil {
       static final LogUtils logger = new LogUtils(DSMLibraryActionUtil.class.getName());
       //////////////////////////////////////////////////////////////////////////////////////
       //the following are the codesets for the 
         // Event Action - CRINV, CRINVNOT
         // INV Action  - CRNNDNOT, CRSHRNOT - create NND create Share
         // Public Health Event Lab Rpts(11648893), Morb Rpt(CMR) and Investigation(NEDSSConstants.PHC_236)
       //////////////////////////////////////////////////////////////////////////////////////
       public static String NBS_EVENT_ACTION = "NBS_EVENT_ACTION";
       public static String NBS_INV_ACTION = "NBS_INV_ACTION";
       public static String PUBLIC_HEALTH_EVENT = "PUBLIC_HEALTH_EVENT";

       QueueUtil queueUtil = new QueueUtil();

       //PHC, Lab Rpt and/or Morb Rpt
       public ArrayList<Object> getEventType(Collection<Object>  algorithmList)throws Exception {
             Map<Object, Object> typeMap = new HashMap<Object,Object>();
             if (algorithmList != null) {
                    java.util.Iterator<?> iter = algorithmList.iterator();
                    while (iter.hasNext()) {
                           DSMAlgorithmDT dt = (DSMAlgorithmDT) iter.next();
                           if (dt.getEventType()!= null) {
                                 typeMap.put(dt.getEventType(), dt.getEventType());
                           }
                           if(dt.getEventType() == null || dt.getEventType().equals("")){
                                 typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
                           }
                    }
             }
             return queueUtil.getUniqueValueFromMap(typeMap);
       }
       
       /**
       * getAlgorithmSummaries
       * @param session
       * @return DSMSummaryDisplay records for all DSMAlgorithmDTs
       * @throws Exception
       */    
       public Collection<Object> getAlgorithmSummaries(HttpSession session) throws Exception{

             MainSessionCommand msCommand = null;
             Collection<Object> dsmDTList = new ArrayList<Object>();
             Collection<Object> displayList = new ArrayList<Object>();
       try{
             String sBeanJndiName = JNDINames.DSMAlgorithmEJB;
             String sMethod = "selectDSMAlgorithmList";
             Object[] oParams =new Object[]{};
             MainSessionHolder holder = new MainSessionHolder();
             msCommand = holder.getMainSessionCommand(session);
             ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
             if(aList != null && aList.size()>0){
                    dsmDTList = (ArrayList<Object>)aList.get(0);
                    displayList = listWithRelatedConditionsandLinks((ArrayList<Object>) dsmDTList, session);

             }
         }catch (Exception ex) {
                    logger.fatal("Error in getting the page Summary in Action Util: ", ex.getMessage());
                    throw new Exception(ex.toString());
         }
         return displayList;
       }
       /**
       * listWithRelatedConditionsandLinks - gets names for codes for Library display
       * @param dtlist, session
       * @return DSMSummaryDisplay records for all DSMAlgorithmDTs
       * @throws Exception
       */    
       private Collection<Object> listWithRelatedConditionsandLinks(ArrayList<Object> dtList, HttpSession session) throws Exception{

             if(dtList==null || dtList.size()==0)
                    return null;
             ArrayList<Object> displayList = new ArrayList<Object>();
             Iterator<Object> ite = dtList.iterator();
             CachedDropDownValues cv = new CachedDropDownValues();
             while(ite.hasNext()){
                    DSMAlgorithmDT dsmDT = (DSMAlgorithmDT)ite.next();
                    DSMSummaryDisplay displayObject = new DSMSummaryDisplay();
                    String eventAction=null;
                    if(dsmDT.getEventAction()!=null){
                           eventAction = cv.getDescForCode(NBS_EVENT_ACTION,dsmDT.getEventAction());
                           if(eventAction==null){
                                 eventAction = cv.getDescForCode(NBS_INV_ACTION,dsmDT.getEventAction());
                           }
                    }
                    displayObject.setAction(eventAction!=null? eventAction:"");
                    displayObject.setEventType(dsmDT.getEventType()!=null?cv.getDescForCode(PUBLIC_HEALTH_EVENT,dsmDT.getEventType()):"");
                    displayObject.setAlgorithmName(dsmDT.getAlgorithmNm()!=null?dsmDT.getAlgorithmNm():"");
                    displayObject.setAdvancedCriteria("");
                    displayObject.setLastChgTime(dsmDT.getLastChgTime());
                    displayObject.setRelatedConditions(setRelatedConditions(dsmDT.getConditionList(), dsmDT.getResultedTestList(), session));
                    displayObject.setRelatedConditionsPrint(setRelatedConditions(dsmDT.getConditionList(), dsmDT.getResultedTestList(), session).replaceAll("</br>"," "));
                    makeAlgorithmLibraryLink(displayObject,dsmDT);
                    displayObject.setStatus((dsmDT.getStatusCd()!=null&&dsmDT.getStatusCd().equals("A"))?NEDSSConstants.RECORD_STATUS_Active:NEDSSConstants.RECORD_STATUS_Inactive);
                    displayList.add(displayObject);
             }
             return displayList;
       }
       /**
       * makeAlgorithmLibraryLink - make the view and edit links for the Library display
       * @param DSMSummaryDisplay, DSMAlgorithmDT
       * @return updated DSMSummaryDisplay records
       * @throws Exception
       */    
       public static void makeAlgorithmLibraryLink(DSMSummaryDisplay displayObject,DSMAlgorithmDT dsmDT) throws Exception {
             try{
             if (displayObject == null ) {
                    return;
             }
                           Long uid= dsmDT.getDsmAlgorithmUid();
                           String viewUrl = "<a href='javascript:viewAlgorithm(\"/nbs/DecisionSupport.do?algorithmUid="+ uid +"&method=viewLoad\")'><img src=\"page_white_text.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View\" alt=\"View\"/></a>";
                           displayObject.setViewLink(viewUrl);
                           String editUrl = null;
                           if(dsmDT.getStatusCd()!=null && dsmDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                                 editUrl = "<img src=\"page_white_edit_disabled.gif\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
                           else
                                 editUrl= "<a href='javascript:editAlgorithm(\"/nbs/DecisionSupport.do?algorithmUid="+ uid +"&method=editLoad\")'><img src=\"page_white_edit.gif\" tabindex=\"0\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"Edit\" alt=\"Edit\"/></a>";
                    displayObject.setEditLink(editUrl);
             }catch (Exception ex) {
                    logger.fatal("Error in makeAlgorithmLink in Action Util: ", ex);
                    throw new Exception(ex.toString());
             }
       }
       /**
       * setRelatedConditions - get the condition names for the summary display
       * @param conditions list
       * @return conditions descriptive list
       * @throws Exception 
        */    
       public static String setRelatedConditions(String conditionsList, String resultedTestList, HttpSession session) throws Exception {
             StringBuffer sb1 = new StringBuffer();
             if (conditionsList != null && !conditionsList.equals(""))
             {
                    StringTokenizer st = new StringTokenizer(conditionsList, "^");
                    boolean flag = false;
                    int i = 0;
                    CachedDropDownValues cv = new CachedDropDownValues();
                    if (st != null) {
                           while (st.hasMoreTokens()) {
                                 String condition = st.nextToken();
                                 String conditionDesc = cv.getConditionDesc(condition);
                                 if (flag) {
                                        if (i < 4)
                                               sb1.append("</br>");
                                 }
                                 if (condition != null && i < 4) {
                                        sb1.append(conditionDesc);
                                        sb1.append(" (").append(condition).append(")");
                                 }
                                 flag = true;
                                 i++;
                           }
                           if (i > 4) {
                                 sb1.append("</br>......");
                           }
                    }
             }
             
             if(resultedTestList != null)
             {
                    StringTokenizer testSt = new StringTokenizer(resultedTestList, "^");
                    StringBuffer testSb1 = new StringBuffer();
                    boolean testFlag = false;
                    int testCount = 0;
                    
                    if (testSt != null) {
                           while (testSt.hasMoreTokens()) {
                                 String resultedTest = testSt.nextToken();
                                 
                                 String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
                                 String sMethod = "findLabResultedTestByCode";
                                 Object[] oParams = new Object[]{"DEFAULT", null, resultedTest, null};
                                 MainSessionHolder holder = new MainSessionHolder();
                                 MainSessionCommand msCommand = holder.getMainSessionCommand(session);
                                 ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                                 ArrayList<?> list = null;
                                 String resultedTestDesc = "";
                                 if(arrList != null && !arrList.isEmpty())
                                        list = (ArrayList<?> )arrList.get(0);                
                                 if(list != null && !list.isEmpty() && list.size() == 1)
                                 {
                                        if(list.get(0) instanceof ObservationNameDT)
                                        {
                                               resultedTestDesc = ((ObservationNameDT)list.get(0)).getLabTestDescription();
                                        }else if (list.get(0) instanceof LoincResultDT)
                                        {
                                               resultedTestDesc = ((LoincResultDT)list.get(0)).getLoincComponentName();
                                        }
                                 }
                                 
                                 if (testFlag) {
                                        if (testCount < 4)
                                               testSb1.append("</br>");
                                 }
                                 if (resultedTest != null && testCount < 4) {
                                        testSb1.append(resultedTestDesc);
                                        testSb1.append(" (").append(resultedTest).append(")");
                                 }
                                 testFlag = true;
                                 testCount++;
                           }
                           if (testCount > 4) {
                                 testSb1.append("</br>......");
                           }
                           if (conditionsList != null && !conditionsList.equals(""))
                           {
                                 sb1.append("</br>" + testSb1);
                           }else
                           {
                                 sb1.append(testSb1);
                           }
                    }
             }
             return sb1.toString();
       }
       
       /**
       * getEventTypeDropDowns - get the events case, lab, morb that are present for the filter list
       * @param DSMSummaryDisplay list, 
        * @return events present descriptive list
       */          
       public ArrayList<Object> getEventTypeDropDowns(Collection<Object> algorithmList) {
             Map<Object, Object>  eventTypeMap = new HashMap<Object,Object>();
             if (algorithmList != null) {
                    Iterator<Object>  iter = algorithmList.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dsmSummary = (DSMSummaryDisplay) iter.next();
                           if (dsmSummary.getEventType() != null) {
                                 eventTypeMap.put(dsmSummary.getEventType(), 
                                               dsmSummary.getEventType());
                           }
                    }
             }
             return queueUtil.getUniqueValueFromMap(eventTypeMap);
       }
       /**
       * getActionDropDowns - Create Case, Create Case with Notif
       * @param DSMSummaryDisplay list, 
        * @return actions present descriptive list
       */    
       public ArrayList<Object> getActionDropDowns(Collection<Object> algorithmList) {
             Map<Object, Object>  eventActionMap = new HashMap<Object,Object>();
             if (algorithmList != null) {
                    Iterator<Object>  iter = algorithmList.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dsmSummary = (DSMSummaryDisplay) iter.next();
                           if (dsmSummary.getAction() != null) {
                                 eventActionMap.put(dsmSummary.getAction(), 
                                                                   dsmSummary.getAction());
                           }
                    }
             }
             return queueUtil.getUniqueValueFromMap(eventActionMap);
       }
       public ArrayList<Object> getStartDateDropDownValues() {
             return queueUtil.getStartDateDropDownValues();
       }
       /**
       * getStatusDropDowns - active and/or inactive
       * @param DSMSummaryDisplay list, 
        * @return status present descriptive list
       */    
       public ArrayList<Object> getStatusDropDowns(Collection<Object> algorithmList) {
             Map<Object, Object>  statusMap = new HashMap<Object,Object>();
             if (algorithmList != null) {
                    Iterator<Object>  iter = algorithmList.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dsmSummary = (DSMSummaryDisplay) iter.next();
                           if (dsmSummary.getStatus() != null) {
                                 statusMap.put(dsmSummary.getStatus(), dsmSummary.getStatus());
                           }
                    }
             }
             return queueUtil.getUniqueValueFromMap(statusMap);
       }
       
       /**
       * filterDSMLibrary - return the DSM Summary events that should be displayed based on 
        *    the current filters that are set and sets the sort/icon criteria
       * @param DSMLibForm
       * @param request
       * @return filtered list or null if no need to filter and repaint screen
       */    
       public static Collection<Object>  filterDSMLibrary(DecisionSupportLibraryForm dsmLibForm, HttpServletRequest request) throws Exception {

             Collection<Object>  dsmLibList = new ArrayList<Object> ();

             Map<Object, Object> searchCriteriaMap = dsmLibForm.getSearchCriteriaArrayMap();
             ArrayList<Object> dsmSummaryColl = (ArrayList<Object> )        dsmLibForm.getAlgorithmList();

             try {
                    //filter the list based on any filters set
                    dsmLibList = getFilteredDSMLib(dsmSummaryColl, searchCriteriaMap, dsmLibForm);
             } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error while filtering the filterDSMLib: "+ e.toString());
                    throw new Exception(e.getMessage());
             }
             
             //check and see if any filters we're set and don't filter if none are set
             try {
                    if (setSearchSortCriteria(dsmLibForm, searchCriteriaMap, request) == false)
                           return null; //nothing to sort

             } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error occurred while setting sort/search criteria in Decision Support: "+ e.toString());
                    throw new Exception(e.getMessage());
             }
             
             return dsmLibList; //filtered by criteria
       }
       
       
       /**
       * setSearchSortCriteria - set the sort/icon criteria
       * @param DSMLibForm
       * @param searchCriteriaMap
       * @param request
       * @return need to repaint screen? true or false
       */    
       public static boolean  setSearchSortCriteria(DecisionSupportLibraryForm dsmLibForm, Map<Object, Object> searchCriteriaMap, HttpServletRequest request) throws Exception {
             
             String sortOrderParam = null;
             //check and see if any filters we're set and don't filter if none are set
             try {
             
              String[] eventType = (String[]) searchCriteriaMap.get("EVENTTYPE");
                    String[] action = (String[]) searchCriteriaMap.get("ACTION");
                    String[] status = (String[]) searchCriteriaMap.get("STATUS");
                    String[] lastUpdated = (String[]) searchCriteriaMap.get("LASTUPDATED");
                    String filterByUniqueAlgorithmName = null;
                    String filterByConditionTest = null;
                    if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
                           filterByUniqueAlgorithmName = String.valueOf(searchCriteriaMap.get("SearchText1_FILTER_TEXT"));
                    }
                    if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
                           filterByConditionTest = String.valueOf(searchCriteriaMap.get("SearchText2_FILTER_TEXT"));
                    }
             Integer eventTypeCount = new Integer(eventType == null ? 0 : eventType.length);
             Integer actionCount = new Integer(action == null ? 0 : action.length);
             Integer statusCount = new Integer(status == null ? 0 : status.length);
             Integer lastUpdatedCount = new Integer(lastUpdated == null ? 0 : lastUpdated.length);


             // Do not filter if the selected values for filter is same as filtered list,
             // but put the sortMethod, direction and criteria stuff
              if(eventTypeCount.equals(dsmLibForm.getAttributeMap().get("eventType")) &&
                           actionCount.equals(dsmLibForm.getAttributeMap().get("action")) &&
                           statusCount.equals(dsmLibForm.getAttributeMap().get("status")) &&
                           lastUpdatedCount.equals(dsmLibForm.getAttributeMap().get("lastUpdated")) &&
                           filterByUniqueAlgorithmName == null &&
                           filterByConditionTest == null)
             {

                    String sortMethod = getSortMethod(request, dsmLibForm);
                    String direction = getSortDirection(request, dsmLibForm);
                    if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
                           Map<?,?> sColl =  dsmLibForm.getAttributeMap().get("searchCriteria") == null ?
                                        new TreeMap<Object, Object>() : (TreeMap<?,?>) dsmLibForm.getAttributeMap().get("searchCriteria");
                           sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
                    } else {
                           sortOrderParam = DSMLibraryActionUtil.getSortDSMLibrary(direction, sortMethod);
                    }
                    Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
                    searchCriteriaColl.put("sortSt", sortOrderParam);
                    dsmLibForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
                    request.setAttribute("SORTORDERPARAM", sortOrderParam);
                    //no need to repaint
                    return false;
             }
             //setup criteria for sorting and icon display
             Collection<Object> eventTypeList = dsmLibForm.getEventType();
             Collection<Object> actionList = dsmLibForm.getAction();
             Collection<Object> statusList = dsmLibForm.getStatus();
             Collection<Object> dateFilterList = dsmLibForm.getDateFilterList();
             

             Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
             String sortMethod = getSortMethod(request, dsmLibForm);
             String direction = getSortDirection(request, dsmLibForm);
             if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
                    Map<Object, Object> sColl =  dsmLibForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() :(TreeMap<Object, Object>) dsmLibForm.getAttributeMap().get("searchCriteria");
                    sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
             } else {
                    sortOrderParam = DSMLibraryActionUtil.getSortDSMLibrary(direction, sortMethod);
             }

             QueueUtil queueUtil = new QueueUtil();
             String srchCriteriaEventType = null;
             srchCriteriaEventType = queueUtil.getSearchCriteria((ArrayList<Object>)eventTypeList, eventType, NEDSSConstants.FILTERBYEVENTTYPE);

             String srchCriteriaAction = null;
             srchCriteriaAction = queueUtil.getSearchCriteria((ArrayList<Object>)actionList, action, NEDSSConstants.FILTERBYDSMACTION);

             String srchCriteriaStatus = null;
             srchCriteriaStatus= queueUtil.getSearchCriteria((ArrayList<Object>)statusList, status, NEDSSConstants.FILTERBYDSMSTATUS);
             
             String srchCriteriaLastUpdated = null;
             srchCriteriaLastUpdated= queueUtil.getSearchCriteria((ArrayList<Object>)dateFilterList, lastUpdated, NEDSSConstants.FILTERBYLASTUPDATED);


             
             //set the criteria to request and the form
             if(sortOrderParam != null) {
                    searchCriteriaColl.put("sortSt", HTMLEncoder.encodeHtml(srchCriteriaLastUpdated));
                    request.setAttribute("SORTORDERPARAM", HTMLEncoder.encodeHtml(sortOrderParam));
             }
             if(srchCriteriaEventType != null)  {
                    request.setAttribute("SRCHCRITERIAEVENTTYPE", HTMLEncoder.encodeHtml(srchCriteriaEventType));
                    searchCriteriaColl.put("INV111", HTMLEncoder.encodeHtml(srchCriteriaEventType));
             }
             if(srchCriteriaAction != null) {
                    request.setAttribute("SRCHCRITERIAACTION", HTMLEncoder.encodeHtml(srchCriteriaAction));
                    searchCriteriaColl.put("INV222", HTMLEncoder.encodeHtml(srchCriteriaAction));
             }
             if(srchCriteriaStatus != null) {
                    request.setAttribute("SRCHCRITERIASTATUS", HTMLEncoder.encodeHtml(srchCriteriaStatus));
                    searchCriteriaColl.put("INV444", HTMLEncoder.encodeHtml(srchCriteriaStatus));
             }
             if(srchCriteriaLastUpdated != null) {
                    request.setAttribute("SRCHCRITERIALASTUPDATED", HTMLEncoder.encodeHtml(srchCriteriaLastUpdated));
                    searchCriteriaColl.put("INV333", HTMLEncoder.encodeHtml(srchCriteriaLastUpdated));
             }
             if(filterByUniqueAlgorithmName != null) {
                    request.setAttribute("FILTERBYUNIQUEALGORITHMNAME", HTMLEncoder.encodeHtml(filterByUniqueAlgorithmName));
                    searchCriteriaColl.put("INV001", HTMLEncoder.encodeHtml(filterByUniqueAlgorithmName));
             }
             if(filterByConditionTest != null) {
                    request.setAttribute("FILTERBYCONDITIONTEST", HTMLEncoder.encodeHtml(filterByConditionTest));
                    searchCriteriaColl.put("INV002", HTMLEncoder.encodeHtml(filterByConditionTest));
             }
             dsmLibForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
             
             //set the criteria to the form
             /*
             if(sortOrderParam != null)
                    searchCriteriaColl.put("sortSt", sortOrderParam);
             if(srchCriteriaEventType != null)
                    searchCriteriaColl.put("INV111", srchCriteriaEventType);
             if(srchCriteriaAction != null)
                    searchCriteriaColl.put("INV222", srchCriteriaAction);
             if(srchCriteriaStatus != null)
                    searchCriteriaColl.put("INV333", srchCriteriaStatus);
             if(filterByUniqueAlgorithmName != null)
                    searchCriteriaColl.put("INV001", filterByUniqueAlgorithmName);
             if(filterByConditionTest != null)
                    searchCriteriaColl.put("INV002", filterByConditionTest);

             dsmLibForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
          */
       } catch (Exception e) {
             e.printStackTrace();
             logger.error("Error occurred while setting sort/search criteria in Decision Support: "+ e.toString());
             throw new Exception(e.getMessage());
       }
             return true;
       }
       /**
       * getFilteredDSMLib - returns the DSM Summary events that should be displayed based on 
        *    the current filters that are set
       * @param DSMLibForm
       * @param request
       * @return filtered list
       */    
       public static Collection<Object> getFilteredDSMLib(Collection<Object> dsmSummaryColl, 
                                               Map<Object, Object> searchCriteriaMap, DecisionSupportLibraryForm dsmLibForm)
                                               throws Exception {
       
               QueueUtil queueUtil = new QueueUtil();
             try{
                    String[] eventType = (String[]) searchCriteriaMap.get("EVENTTYPE");
                           String[] action = (String[]) searchCriteriaMap.get("ACTION");
                           String[] status = (String[]) searchCriteriaMap.get("STATUS");
                           String[] lastUpdated = (String[]) searchCriteriaMap.get("LASTUPDATED");
                           String filterByUniqueAlgolrithmName = null;
                           String filterByConditionTest = null;
                           if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
                                 filterByUniqueAlgolrithmName = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
                           }
                           if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
                                 filterByConditionTest = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
                           }            
                                 
                           Map<Object, Object> eventTypeMap = new HashMap<Object,Object>(); //Morb, Case, Lab
                           Map<Object, Object> actionMap = new HashMap<Object,Object>(); //Create Case, Create with Notif
                           Map<Object, Object> statusMap = new HashMap<Object,Object>(); //Active or Inactive
                           Map<Object, Object>  dateMap = new HashMap<Object,Object>();
                           
                     try{
                          // Event Type of Lab Rpt, Morb Rpt or Case Investigation
                                 if (eventType != null && eventType.length >0){
                                        eventTypeMap = queueUtil.getMapFromStringArray(eventType);
                                 }
                                 if(eventType != null && eventTypeMap != null && eventTypeMap.size()>0){
                                        dsmSummaryColl = DSMLibraryActionUtil.filterEventType(dsmSummaryColl, eventTypeMap);
                                 }

                                 //action of Create Inves or Create Inves with Notif
                                 if (action != null && action.length >0){
                                        actionMap = queueUtil.getMapFromStringArray(action);
                                 }
                                 if(action != null && actionMap != null && actionMap.size()>0){
                                        dsmSummaryColl = DSMLibraryActionUtil.filterAction(dsmSummaryColl, actionMap);
                                 }
               
                                 // status of Active or Inactive
                                 if (status != null && status.length >0){
                                        statusMap = queueUtil.getMapFromStringArray(status);
                                 }
                                 if(status != null && statusMap != null && statusMap.size()>0){
                                        dsmSummaryColl = DSMLibraryActionUtil.filterStatus(dsmSummaryColl, statusMap);
                                 }
                                 if (lastUpdated != null && lastUpdated.length >0)
                                        dateMap = queueUtil.getMapFromStringArray(lastUpdated);
                                 if(dateMap != null && dateMap.size()>0)
                                        dsmSummaryColl = filterLibrarybyLastUpdated(dsmSummaryColl,dateMap);

                                 if(filterByUniqueAlgolrithmName!= null){
                                        dsmSummaryColl = filterByText(dsmSummaryColl, filterByUniqueAlgolrithmName, NEDSSConstants.UNIQUE_NAME);
                                 }
                                 if(filterByConditionTest!= null){
                                        dsmSummaryColl = filterByText(dsmSummaryColl, filterByConditionTest, NEDSSConstants.FILTERBYCONDITION);
                                 }                                

                     }catch (Exception e) {
                                 e.printStackTrace();
                                 logger.error("Error while filtering in getFilteredDSMLib: "+ e.toString());
                                 throw new Exception(e.getMessage());
                     }                   
                     
               }catch (Exception e) {
                           e.printStackTrace();
                           logger.error("Error while filtering the getFilteredDSMLib: "+ e.toString());
                           throw new Exception(e.getMessage());
                    }
                    return dsmSummaryColl;
       }

       /**
       * filterByText - user entered text to filter on Algorithm Name or Condition/Test
       * @param DSMSummaryDisplay list, text to filter on, type
       * @param request
       * @return filtered list
       */    
       public static Collection<Object>  filterByText(
                    Collection<Object>  dsmSummaryDisplay, String filterByText,String column) {
             Collection<Object>  newTypeColl = new ArrayList<Object> ();
             try{
             if (dsmSummaryDisplay != null) {
                    Iterator<Object> iter = dsmSummaryDisplay.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dt = (DSMSummaryDisplay) iter.next();
                           if(column.equals(NEDSSConstants.UNIQUE_NAME) && dt.getAlgorithmName()!= null && dt.getAlgorithmName().toUpperCase().contains(filterByText.toUpperCase())){
                                 newTypeColl.add(dt);
                           }
                           if(column.equals(NEDSSConstants.FILTERBYCONDITION) && dt.getRelatedConditions()!= null && dt.getRelatedConditions().toUpperCase().contains(filterByText.toUpperCase())){
                                 newTypeColl.add(dt);
                           }
                    }
             }
             }catch(Exception ex){
                    logger.error("Error filtering the filterByText : "+ex.getMessage());
                    throw new NEDSSSystemException(ex.getMessage());
             }
             return newTypeColl;
       }

       /**
       * filterAction - Filter on the event type of Laboratory Reporting, Confidential Morbidity Report and/or Case or Outbreak Investigation
       * @param dsmSummaryColl
       * @param eventTypeMap - events user wants to see
       * @return filtered list containing only events user wants
       */    
       public static Collection<Object>  filterEventType(
                    Collection<Object>  dsmSummaryColl, Map<Object,Object> eventTypeMap) throws Exception {
             Collection<Object>  newEventTypeColl = new ArrayList<Object> ();
             try{
             if (dsmSummaryColl != null) {
                    Iterator<Object> iter = dsmSummaryColl.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dt = (DSMSummaryDisplay) iter.next();
                           String eventTypeStr = dt.getEventType(); 
                           if (eventTypeStr != null && eventTypeMap != null
                                        && eventTypeMap.containsKey(eventTypeStr))
                           {
                                 newEventTypeColl.add(dt);
                           }
                           if(eventTypeStr == null || eventTypeStr.equals("")){
                                 logger.warn("Filter DWM Event Type: event type is blank?");
                                 }
                           }
                    }
             }catch(Exception ex){
                    logger.error("Error in filterEventType : "+ex.getMessage());
                    throw new NEDSSSystemException(ex.getMessage());
             }
             return newEventTypeColl;
       }
       
       /**
       * filterAction - Filter on the action is Create Investigation or Create Investigation Notification
       * @param dsmSummaryColl
       * @param eventTypeMap - actions user wants to see
       * @return filtered list containing only actions user wants
       */    
       public static Collection<Object>  filterAction(
                    Collection<Object>  dsmSummaryColl, Map<Object,Object> actionMap) throws Exception {
             Collection<Object>  newActionColl = new ArrayList<Object> ();
             try{
             if (dsmSummaryColl != null) {
                    Iterator<Object> iter = dsmSummaryColl.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dt = (DSMSummaryDisplay) iter.next();
                           String actionStr = dt.getAction(); 
                           if (actionStr != null && actionMap != null
                                        && actionMap.containsKey(actionStr))
                           {
                                 newActionColl.add(dt);
                           }
                           if(actionStr == null || actionStr.equals("")){
                                 logger.warn("Filter DWM Action: action is blank?");
                                 }
                           }
                    }
             }catch(Exception ex){
                    logger.error("Error in filterEventType : "+ex.getMessage());
                    throw new NEDSSSystemException(ex.getMessage());
             }
             return newActionColl;
       }      
       
       public static Collection<Object>  filterLibrarybyLastUpdated(Collection<Object> dsmSummaryColl, Map<Object, Object> dateMap) {
             Collection<Object>  newColl = new ArrayList<Object> ();
             QueueUtil queueUtil = new QueueUtil();
             String strDateKey = null;
             if (dsmSummaryColl != null) {
                    Iterator<Object>  iter = dsmSummaryColl.iterator();
                    while (iter.hasNext()) {
                           boolean isNextValue = true;
                           DSMSummaryDisplay dt = (DSMSummaryDisplay) iter.next();
                           if (dt.getLastChgTime() != null && dateMap != null
                                        && (dateMap.size()>0 )) {
                                 Collection<Object>  dateSet = dateMap.keySet();
                                 if(dateSet != null){
                                        Iterator<Object>  iSet = dateSet.iterator();
                                 while (iSet.hasNext() && isNextValue){
                                        strDateKey = (String)iSet.next();
                                        if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                              if(queueUtil.isDateinRange(dt.getLastChgTime(),strDateKey)){
                                    newColl.add(dt);
                                    break;
                              }   
                                        
                                        }  
                       }
                                   }
                                 }
             
                           if(dt.getLastChgTime() == null || dt.getLastChgTime().equals("")){
                                 if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
                                        newColl.add(dt);
                                 }
                           }

                    }
             }      

             return newColl;
       }
       
   private static Collection<Object>  convertDSMMaptoColl(Map<Object, Object>  invMap){
          Collection<Object>  invColl = new ArrayList<Object> ();
          if(invMap !=null && invMap.size()>0){
                Collection<Object>  invKeyColl = invMap.keySet();
               Iterator<Object>  iter = invKeyColl.iterator();
                while(iter.hasNext()){
                       String invKey = (String)iter.next();
                       invColl.add(invMap.get(invKey));
                }
          }
          return invColl;
   }
       
       /**
       * filterStatus - Filter Active or Inactive
       * @param dsmSummaryColl
       * @param eventTypeMap - status user wants to see
       * @return filtered list containing only status user wants
       */    
       public static Collection<Object>  filterStatus(
                    Collection<Object>  dsmSummaryColl, Map<Object,Object> statusMap) throws Exception {
             Collection<Object>  newStatusColl = new ArrayList<Object> ();
             try{
             if (dsmSummaryColl != null) {
                    Iterator<Object> iter = dsmSummaryColl.iterator();
                    while (iter.hasNext()) {
                           DSMSummaryDisplay dt = (DSMSummaryDisplay) iter.next();
                           String statusStr = dt.getStatus();
                           if (statusStr != null && statusMap != null
                                        && statusMap.containsKey(statusStr))
                           {
                                 newStatusColl.add(dt);
                           }
                           if(statusStr == null || statusStr.equals("")){
                                 logger.warn("Filter DWM Action: status is blank?");
                                 }
                           }
                    }
             }catch(Exception ex){
                    logger.error("Error in filterEventType : "+ex.getMessage());
                    throw new NEDSSSystemException(ex.getMessage());
             }
             return newStatusColl;
       }
       
       public static void sortDSMLibarary(DecisionSupportLibraryForm dsmLibForm, Collection<Object>  codesetList, boolean existing, HttpServletRequest request) throws Exception {

             // Retrieve sort-order and sort-direction from display tag parameters
             String sortMethod = getSortMethod(request, dsmLibForm);
             String direction = getSortDirection(request, dsmLibForm);
             NedssUtils util = new NedssUtils();
             
             boolean invDirectionFlag = true;
             if (direction != null && direction.equals("2"))
                    invDirectionFlag = false;


             //Read from properties file to determine default sort order
             if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
                    sortMethod = "getAlgorithmName";
                    invDirectionFlag = true;
             request.setAttribute("SORTORDERPARAM", DSMLibraryActionUtil.getSortDSMLibrary(direction, sortMethod));
             }

             if (sortMethod != null && codesetList != null && codesetList.size() > 0) {
            	 updateDSMSummaryVObeforeSort(codesetList);   
            	 util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) codesetList, invDirectionFlag);
            	 updateDSMSummaryVOAfterSort(codesetList);
             }
             if(!existing) {
                    //Finally put sort criteria in form
                    String sortOrderParam = DSMLibraryActionUtil.getSortDSMLibrary(invDirectionFlag == true ? "1" : "2", sortMethod);
                    Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
                    searchCriteriaColl.put("sortSt", sortOrderParam);
                    dsmLibForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
             }
       }
       
       public static String getSortDSMLibrary(String sortOrder, String methodName)throws Exception{
             try{
             String sortOrdrStr = null;
             if(methodName != null) {
                    if(methodName.equals("getAlgorithmName"))
                           sortOrdrStr = "Algorithm Name";
                    else if(methodName.equals("getEventType"))
                           sortOrdrStr = "Event Type";
                    else if(methodName.equals("getRelatedConditions"))
                           sortOrdrStr = "Related Condition(s)/Test(s)";
                    else if(methodName.equals("getAdvancedCriteria"))
                           sortOrdrStr = "Advanced Criteria";
                    else if(methodName.equals("getAction"))
                           sortOrdrStr = "Action";
                    else if(methodName.equals("getLastChgTime"))
                           sortOrdrStr = "Last Updated";
                    else if(methodName.equals("getStatus"))
                           sortOrdrStr = "Status";
             } else {
                           sortOrdrStr = "Algorithm Name"; //default sort order
             }

             if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
                    sortOrdrStr = sortOrdrStr+" @ in ascending order ";
             else if(sortOrder != null && sortOrder.equals("2"))
                    sortOrdrStr = sortOrdrStr+" @ in descending order ";

             return sortOrdrStr;
             }catch (Exception ex) {
                    logger.fatal("Error in getSortDSMLibrary in Action Util: ", ex);
                    throw new Exception(ex.toString());

             }

       }

       
       private static String getSortMethod(HttpServletRequest request, DecisionSupportLibraryForm dsmLibForm ) {
             if (PaginationUtil._dtagAccessed(request)) {
                    return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
       } else{
             return dsmLibForm.getAttributeMap().get("methodName") == null ? null : (String) dsmLibForm.getAttributeMap().get("methodName");
             }

       }

       private static String getSortDirection(HttpServletRequest request, DecisionSupportLibraryForm dsmLibForm) {
             if (PaginationUtil._dtagAccessed(request)) {
                    return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
             } else{
                    return dsmLibForm.getAttributeMap().get("sortOrder") == null ? "1": (String) dsmLibForm.getAttributeMap().get("sortOrder");
             }

       }
       private static void updateDSMSummaryVObeforeSort(Collection<Object> displayObjects) {
   		Iterator<Object> iter = displayObjects.iterator();
   		while (iter.hasNext()) {
   			DSMSummaryDisplay displayObject = (DSMSummaryDisplay)iter.next();
   			if (displayObject.getRelatedConditions() == null || (displayObject.getRelatedConditions() != null && displayObject.getRelatedConditions().equals(""))) {
   				displayObject.setRelatedConditions("ZZZZZ");
   			}

   			
   			
   		
   		}
   		
   	}
   	private static void updateDSMSummaryVOAfterSort(Collection<Object> displayObjects) {
   		Iterator<Object> iter = displayObjects.iterator();
   		while (iter.hasNext()) {
   			DSMSummaryDisplay displayObject = (DSMSummaryDisplay)iter.next();
   			if (displayObject.getRelatedConditions() != null && displayObject.getRelatedConditions().equals("ZZZZZ")) {
   				displayObject.setRelatedConditions("");
   			}
   			
   		}
   		
   	}
}

