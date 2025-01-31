package gov.cdc.nedss.webapp.nbs.action.summary;
/**
 * Title:        ManageSummaryLoad.java
 * Description:  This Action class Loads the Summary Notification Page
 *               gets  Summary ReportProxyVO and  display it on the page
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Nedss Development Team
 * @version 1.0
 */

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.summary.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;



import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class ManageSummaryLoad
    extends Action
{

    static LogUtils logger = null;
    /**
     *  Default constructor.
     */
    public ManageSummaryLoad()
    {

        if (logger == null)
        {
            logger = new LogUtils(this.getClass().getName());
        }
     
    }
  /**
   * This method binds appropriate variables to request object.
   * @param ActionMapping mapping
   * @param ActionForm Form
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @return ActionForward
   * @throws IOException, ServletException
   */

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
        PropertyUtil propertyUtil= PropertyUtil.getInstance();
        String strMIN_MMWR_YEAR = propertyUtil.getMIN_MMWR_YEAR();
        String strNBS_STATE_CODE = propertyUtil.getNBS_STATE_CODE();
        //##!! System.out.println("what is the state cd - " + strNBS_STATE_CODE);

        CachedDropDownValues cd = new CachedDropDownValues();
        TreeMap<?,?> tmCounties = cd.getCountyCodes(strNBS_STATE_CODE);
        request.setAttribute("counties",WumUtil.sortTreeMap(tmCounties));
        HttpSession session = request.getSession();

        if (session == null)
        {
            logger.debug("error no session : Please relog in");

            throw new ServletException("error no session : Please relog in");
        }

          // Security Object
        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

        TreeMap<Object,Object> conditionTreemap = null;
        TreeMap<Object,Object>   programAreas = null;
        TreeMap<Object,Object>   ConditionCdprogramAreaTm = null;
        /**
         * Get the programArea Codes
         */
        try{
            programAreas =   secObj.getProgramAreas(NBSBOLookup.SUMMARYREPORT,NEDSSConstants.SUMMARY_REPORT_ADD);
            //##!! System.out.println("the program are is :"+programAreas );

            if(programAreas!=null)
            {
             if(programAreas.isEmpty())
             {
               logger.debug("programAreas is empty {}!!!!");
             }
             else
             {
                String strProgramAreas = this.sortTreeMaptoString(programAreas);

                //##!! System.out.println("The string program area from the WUM Util" + strProgramAreas);

                conditionTreemap = cd.getActiveSummaryReportConditionCode(strProgramAreas);
                String  condition = WumUtil.sortTreeMap(conditionTreemap) ;
                //##!! System.out.println("**********What is in conition*****************"+condition);
                request.setAttribute("condition",condition);

                ConditionCdprogramAreaTm = cd.getSummaryReportConditionCodeProgAreaCd(strProgramAreas);
               }
            }
            else
            {
             //##!! System.out.println("The programAreas is null");
            }
         }
         catch(Exception e)
         {
           e.printStackTrace();
         }



       // To set the MMWRYear in the request
        getDates(request);


        //to check security permission for viewing the Summary page
        boolean checkViewPermission = secObj.getPermission(NBSBOLookup.SUMMARYREPORT,
                                                           NBSOperationLookup.VIEW);
        request.setAttribute("viewSumm", String.valueOf(checkViewPermission));
        if(checkViewPermission == false)
        {
             logger.debug("error no session : Please relog in");
         }

        String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");


        //##!! System.out.println("what is the context action ---- " + contextAction);

        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS063", contextAction);
        ErrorMessageHelper.setErrMsgToRequest(request, "PS063");
        NBSContext.lookInsideTreeMap(tm);

        String sCurrTask = NBSContext.getCurrentTask(session);
        //##!! System.out.println("The Current Task from the context  is "+ sCurrTask);
        request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

        request.setAttribute("Condition",
                                 "/nbs/" + sCurrTask +
                                 ".do?ContextAction=" + tm.get("Condition"));

        // Checking the secirity for Add Summary Report
        boolean bAddButton = secObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD);
        request.setAttribute("bAddButton", String.valueOf(bAddButton));


      if(sCurrTask != null && sCurrTask.trim().equalsIgnoreCase("ManageSummary2"))
       {
            logger.info("currentTask: " + sCurrTask);
            DSSummaryReportInfo summaryReportInfo = (DSSummaryReportInfo)NBSContext.retrieve(session,"DSSummaryReportInfo");
            String county = summaryReportInfo.getCounty();
            String mmwrYear = summaryReportInfo.getMmwrYear();
            String mmwrWeek = summaryReportInfo.getMmwrWeek();
            String  MMWRWeekOptions = request.getParameter("MMWRWeekOptions");
            if(MMWRWeekOptions == null)
               MMWRWeekOptions = summaryReportInfo.getMMWRWeekOptions();
             logger.info("countyValue,  mmwrYear, mmwrWeek " + county + "  :  " + mmwrYear + " : " + " : " + mmwrWeek);

            request.setAttribute("countyValue",county);
            request.setAttribute("MIN_MMWR_YEAR",mmwrYear);
            request.setAttribute("MIN_MMWR_WEEK",mmwrWeek);
            request.setAttribute("MMWRWeekOptions",MMWRWeekOptions);

            Collection<Object>  summaryReportVOColl = this.getSummaryReports(county, mmwrWeek, mmwrYear,
                                                                form, secObj, session,
                                                                    request);
            this.convertSummaryReportVOCollToRequest(summaryReportVOColl,conditionTreemap,session,request);
       }
      // security check to show or hide the aggregate link
	      String conditionCd ="11063";
	      String aggProgAreaCD = getConditionCd(conditionCd, session);
	      boolean isAggregateLink = false;
	      if(programAreas!=null && programAreas.containsKey(aggProgAreaCD))
	      {
	    	  isAggregateLink = true;
	      }
	      request.setAttribute("ShowAggregateLink", isAggregateLink);
	      
	      return mapping.findForward("XSP");
    }
    
    
    
    private String getConditionCd(String conditionCd, HttpSession session)
    {
    	MainSessionCommand msCommand = null;
        String progAreaCd = null;
    	try
    	{

    		
    		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
            String sMethod = "getConditionCd";
            logger.debug("getConditionCd to investigationproxyejb, via mainsession");

            Object[] oParams = {conditionCd};
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            logger.info("mscommand in Load Summary Reports is: " + msCommand);

            
            progAreaCd = (String)msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);
    		
    	}catch(Exception e)
    	{
    		logger.error("Exception in getConditionCd: " + e.getMessage());
    		e.printStackTrace();
    	}
    	return progAreaCd;
    }

    /**
     * Get the SummryReportVoCollection<Object>  from the Database
     * by calling getSummaryReportsForMMWR() in InvestigationProxyEJB
     * @param county    the String
     * @param mmwrWeek   the String
     * @param mmwrYear    the String
     * @param form        the ActionForm
     * @param nbsSecurityObj   the NBSSecurityObj
     * @param session     the HttpSession
     * @param request     the HttpServletRequest
     * @return Collection
     */
    private Collection<Object>  getSummaryReports(String county, String mmwrWeek, String mmwrYear,
                                         ActionForm form,
                                         NBSSecurityObj nbsSecurityObj, HttpSession session,
                                         HttpServletRequest request)
    {

        MainSessionCommand msCommand = null;
        Collection<Object>  resultSummary = new ArrayList<Object> ();
        SummaryReportForm summaryReportForm = (SummaryReportForm)form;

        try
        {

            String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
            String sMethod = "getSummaryReportsForMMWR";
            logger.debug("sending county, mmwrWeek, mmwrYear, to investigationproxyejb, via mainsession");

            Object[] oParams = { county, mmwrWeek, mmwrYear };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            logger.info("mscommand in Load Summary Reports is: " + msCommand);

            // ArrayList<Object> resultArr = new ArrayList<Object> ();
            ArrayList<?> resultArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

            if ((resultArr != null) && (resultArr.size() > 0))
            {
                logger.info("Got the Summary Data !!! ArrayList<Object> = " + resultArr.get(0));
            }

            resultSummary = (Collection<Object>)resultArr.get(0);
            summaryReportForm.setSummaryProxyCollection(resultSummary);


        }
        catch (Exception e)
        {
            logger.fatal("Error calling getSummaryReportsForMMWR:", e);
            e.printStackTrace();
        }

        return resultSummary;
    }

    /**
     *  To convert the SummaryReportProxyVOCollection  to request
     *  @param Collection<Object>    summaryReportVOColl
     *  @param TreeMap<Object,Object>   conditionTreemap
     *  @param HttpSession   session
     *  @param HttpServletRequest   request
     */

    private void convertSummaryReportVOCollToRequest(
			Collection<Object>  summaryReportVOColl, TreeMap<Object,Object> conditionTreemap,
			HttpSession session, HttpServletRequest request) {

		CachedDropDownValues cdv = new CachedDropDownValues();
		WumUtil util = new WumUtil();
		if (summaryReportVOColl != null) {

			StringBuffer summaryReportVOList = new StringBuffer("");
			for (Iterator<Object> anIterator = summaryReportVOColl.iterator(); anIterator
					.hasNext();) {
				SummaryReportProxyVO summaryReportProxyVO = (SummaryReportProxyVO) anIterator
						.next();
				String desc = "";//cdv.getSummaryReportConditionCode();
				String strCount = "";
				String subCount = "0";

				if (conditionTreemap != null) {
					if (!conditionTreemap.isEmpty()) { // Resetting the TreeMap
													   // for the condition
													   // dropdown
						if (conditionTreemap.containsKey(summaryReportProxyVO
								.getThePublicHealthCaseVO()
								.getThePublicHealthCaseDT().getCd())) {
							//##!! System.out.println("\n\n
							// summaryReportProxyVO.getThePublicHealthCaseVO()");
							conditionTreemap.remove(summaryReportProxyVO
									.getThePublicHealthCaseVO()
									.getThePublicHealthCaseDT().getCd());
							String condition = WumUtil
									.sortTreeMap(conditionTreemap);
							request.setAttribute("condition", condition);
						}
					}
				}
				// conditionTreemap =
				// this.compareTreeMaptoTableCondition(conditionTreemap,summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getCdDescTxt());

				ObservationVO observationVO = this.getObservationVO(
						summaryReportProxyVO, NEDSSConstants.SUM107);
				if (observationVO != null) {
					BigDecimal ccount = observationVO.getObsValueNumericDT_s(0)
							.getNumericValue1();
					if (ccount != null) {
						strCount = ccount.toString();
						if (strCount != null && strCount.indexOf(".") != -1)
							subCount = strCount.substring(0, strCount
									.lastIndexOf("."));
						else
							subCount = strCount;
					}
				} else {
					//##!! System.out.println(" count observation is null");
				}

				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
						.getAttribute("NBSSecurityObject");
				boolean addPermission = nbsSecurityObj.getPermission(
						"SUMMARYREPORT", "ADD", summaryReportProxyVO
								.getThePublicHealthCaseVO()
								.getThePublicHealthCaseDT().getProgAreaCd(),
						"ANY");
				logger.debug("The value of permisiion: " + addPermission);
				Iterator<Object> notifiIterator = summaryReportProxyVO
						.getTheNotificationVOCollection().iterator();
				boolean flag = true;
				boolean timeFlag = true;
				String tempRecordStatus = "";
				String tempRptTime = "";
				String recordStatus = null;
				String rptTime = null;
				if (addPermission == true) {
					logger.debug("\n\n size of the notification collection is "
							+ summaryReportProxyVO
									.getTheNotificationVOCollection().size());
					summaryReportVOList.append(NEDSSConstants.BATCH_HELP)
							.append(
									(summaryReportProxyVO
											.getThePublicHealthCaseVO()
											.getThePublicHealthCaseDT()
											.getCdDescTxt() == null) ? ""
											: summaryReportProxyVO
													.getThePublicHealthCaseVO()
													.getThePublicHealthCaseDT()
													.getCdDescTxt()).append(
									NEDSSConstants.BATCH_HELP).append(
									NEDSSConstants.BATCH_SEP);
					summaryReportVOList.append(subCount).append(
							NEDSSConstants.BATCH_SEP);
					summaryReportVOList.append(
							util.formatDate(summaryReportProxyVO
									.getThePublicHealthCaseVO()
									.getThePublicHealthCaseDT()
									.getLastChgTime())).append(
							NEDSSConstants.BATCH_SEP);
					/**
					 * Display the status and Sent time if any notification is
					 * sent display it from the most recent notification by
					 * looking at NotificationDT.last_chg_time
					 */

					Timestamp tempStamp = null;
					while (notifiIterator.hasNext()) {
						flag = true;
						NotificationVO notifiVO = (NotificationVO) notifiIterator
								.next();
						Timestamp stamp = notifiVO.getTheNotificationDT()
								.getLastChgTime();
						tempRecordStatus = notifiVO.getTheNotificationDT()
								.getRecordStatusCd() == null ? " " : notifiVO
								.getTheNotificationDT().getRecordStatusCd();
						tempRptTime = notifiVO.getTheNotificationDT()
								.getRptSentTime() == null ? " " : notifiVO
								.getTheNotificationDT().getRptSentTime()
								.toString();
						//logger.debug("tempRptTime " +
						// notifiVO.getTheNotificationDT().getRptSentTime().toString());

						if (timeFlag) {
							tempStamp = stamp;
							timeFlag = false;
						}
						if (flag && tempStamp != null && stamp != null) {
							if (tempStamp.before(stamp)) {
								recordStatus = notifiVO.getTheNotificationDT()
										.getRecordStatusCd() == null ? " "
										: notifiVO.getTheNotificationDT()
												.getRecordStatusCd().trim();
								rptTime = notifiVO.getTheNotificationDT()
										.getRptSentTime() == null ? " "
										: notifiVO.getTheNotificationDT()
												.getRptSentTime().toString();
								tempStamp = stamp;
								flag = false;
							}
						}
					}
					if (flag) {
						summaryReportVOList.append(
								tempRecordStatus == null ? ""
										: tempRecordStatus.trim()).append(
								NEDSSConstants.BATCH_SEP);
						summaryReportVOList.append(
								tempRptTime == null ? "" : tempRptTime.trim())
								.append(NEDSSConstants.BATCH_SEP);
					} else {
						summaryReportVOList.append(
								recordStatus == null ? tempRecordStatus.trim()
										: recordStatus.trim()).append(
								NEDSSConstants.BATCH_SEP);
						summaryReportVOList.append(
								rptTime == null ? tempRptTime : rptTime)
								.append(NEDSSConstants.BATCH_SEP);
					}
					summaryReportVOList
							.append(NEDSSConstants.BATCH_STAR)
							.append(
									(summaryReportProxyVO
											.getThePublicHealthCaseVO()
											.getThePublicHealthCaseDT()
											.getPublicHealthCaseUid() == null) ? new Long(
											0)
											: summaryReportProxyVO
													.getThePublicHealthCaseVO()
													.getThePublicHealthCaseDT()
													.getPublicHealthCaseUid())
							.append(NEDSSConstants.BATCH_STAR).append(
									NEDSSConstants.BATCH_LINE);

					// Context store here b'coz we are getting the UID only here
					Long summaryReportUID = summaryReportProxyVO
							.getThePublicHealthCaseVO()
							.getThePublicHealthCaseDT()
							.getPublicHealthCaseUid();

					NBSContext.store(session, "DSSummaryReportUID",
							summaryReportUID);
				}
				/*
				 * It you don't have the add permission the String Bufffer
				 * Changes with no links in it
				 */

				else {
					summaryReportVOList
							.append(
									(summaryReportProxyVO
											.getThePublicHealthCaseVO()
											.getThePublicHealthCaseDT()
											.getCdDescTxt() == null) ? ""
											: summaryReportProxyVO
													.getThePublicHealthCaseVO()
													.getThePublicHealthCaseDT()
													.getCdDescTxt()).append(
									NEDSSConstants.BATCH_SEP);
					summaryReportVOList.append(subCount).append(
							NEDSSConstants.BATCH_SEP);
					summaryReportVOList.append(
							util.formatDate(summaryReportProxyVO
									.getThePublicHealthCaseVO()
									.getThePublicHealthCaseDT()
									.getLastChgTime())).append(
							NEDSSConstants.BATCH_SEP);
					/**
					 * Display the status and Sent time if any notification is
					 * sent display it from the most recent notification by
					 * looking at NotificationDT.last_chg_time
					 */

					Timestamp tempStamp = null;
					while (notifiIterator.hasNext()) {
						flag = true;
						NotificationVO notifiVO = (NotificationVO) notifiIterator
								.next();
						Timestamp stamp = notifiVO.getTheNotificationDT()
								.getLastChgTime();
						tempRecordStatus = notifiVO.getTheNotificationDT()
								.getRecordStatusCd() == null ? " " : notifiVO
								.getTheNotificationDT().getRecordStatusCd();
						tempRptTime = notifiVO.getTheNotificationDT()
								.getRptSentTime() == null ? " " : notifiVO
								.getTheNotificationDT().getRptSentTime()
								.toString();
						//logger.debug("tempRptTime " +
						// notifiVO.getTheNotificationDT().getRptSentTime().toString());

						if (timeFlag) {
							tempStamp = stamp;
							timeFlag = false;
						}
						if (flag && tempStamp != null && stamp != null) {
							if (tempStamp.before(stamp)) {
								recordStatus = notifiVO.getTheNotificationDT()
										.getRecordStatusCd() == null ? " "
										: notifiVO.getTheNotificationDT()
												.getRecordStatusCd().trim();
								rptTime = notifiVO.getTheNotificationDT()
										.getRptSentTime() == null ? " "
										: notifiVO.getTheNotificationDT()
												.getRptSentTime().toString();
								tempStamp = stamp;
								flag = false;
							}
						}
					}
					if (flag) {
						summaryReportVOList.append(
								tempRecordStatus == null ? ""
										: tempRecordStatus.trim()).append(
								NEDSSConstants.BATCH_SEP);
						summaryReportVOList.append(
								tempRptTime == null ? "" : tempRptTime.trim())
								.append(NEDSSConstants.BATCH_SEP);
					} else {
						summaryReportVOList.append(
								recordStatus == null ? tempRecordStatus.trim()
										: recordStatus.trim()).append(
								NEDSSConstants.BATCH_SEP);
						summaryReportVOList.append(
								rptTime == null ? tempRptTime : rptTime)
								.append(NEDSSConstants.BATCH_SEP);
					}

					summaryReportVOList.append(NEDSSConstants.BATCH_LINE);

					// Context store here b'coz we are getting the UID only here
					Long summaryReportUID = summaryReportProxyVO
							.getThePublicHealthCaseVO()
							.getThePublicHealthCaseDT()
							.getPublicHealthCaseUid();

					NBSContext.store(session, "DSSummaryReportUID",
							summaryReportUID);
				}

			}

			logger.debug("summaryReportVOList for display is: "
					+ summaryReportVOList.toString());
			request.setAttribute("summaryReportVOList", summaryReportVOList
					.toString());
		}
	}


/**
 * Get the Total Count from the observation which has the code as "SUM107"
 * 
 * @param obsVOcoll
 *            the Collection
 * @return double
 */

    private double getTotalCount(Collection<Object> obsVOcoll)
    {
                 double count = 0;


          ArrayList<Object> obsArr = (ArrayList<Object> )obsVOcoll;
               ObservationVO observationVO = (ObservationVO)obsArr.get(0);
                 if(observationVO.getTheObservationDT().getCd()!= null )
                    {    String phcCode = observationVO.getTheObservationDT().getCd();

                    if(phcCode.equalsIgnoreCase(NEDSSConstants.SUM107))
                    {
                        Collection<Object>  obsValueNumericDTColl = observationVO.getTheObsValueNumericDTCollection();
                        ArrayList<Object> obsNumArr = (ArrayList<Object> )obsValueNumericDTColl;
                        ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)obsNumArr.get(0);
                        count =  count + obsValueNumericDT.getNumericValue1().doubleValue();

                  }
                }
               return count;
    }

 /**
  * Select an observation based on the Code
  * 
  * @param summaryReportProxyVO
  *            the SummaryReportProxyVO
  * @param SummaryReportFormCd
  *            the String
  * @return ObservationVO
  */


    private ObservationVO getObservationVO(SummaryReportProxyVO summaryReportProxyVO, String SummaryReportFormCd)
    {
	//##!! System.out.println("Hi we are in the method of getting the observation from summaryVO");
        String observationCode = "";
	ObservationVO observationVO = null;
	Iterator<Object> anIterator = null;
	Collection<Object>  obsCollection  = summaryReportProxyVO.getTheObservationVOCollection();
        //##!! System.out.println("size of obsvocoll in the method"+obsCollection);
	if(obsCollection  != null)
	{
	    anIterator = obsCollection.iterator();
	    while(anIterator.hasNext())
	    {
		 observationVO = (ObservationVO)anIterator.next();
                 //##!! System.out.println("observationVO.cd: "+observationVO.getTheObservationDT().getCd());
		 if(observationVO.getTheObservationDT().getCd() != null && observationVO.getTheObservationDT().getCd().trim().equals(SummaryReportFormCd.trim()))
		 {
		   //##!! System.out.println("getObservation Cd from  getObservationVO method =======: " + observationVO.getTheObservationDT().getCd());
		    return observationVO;
		 }
	    }
	}
	return null;
    }

  /**
   * Sort the TreeMap<Object,Object> and make a comma separated String
   * to pass as a parameter to the where clause of the querry in the SRT
   * @param treeMap  the TreeMap
   * @return String
   */


   private String sortTreeMaptoString(TreeMap<Object,Object> treeMap)
    {
	class StringComparator implements Comparator
	{
	    public int compare(Object a, Object b)
	    {
		 Map.Entry m1 = (Map.Entry)a;
		 Map.Entry m2 = (Map.Entry)b;

		 return (m1.getValue()).toString().compareTo(
			(m2.getValue()).toString());
	    }
	}

	StringBuffer sb = new StringBuffer();
        Set s = treeMap.entrySet();
	Map.Entry[] entries = (Map.Entry[])s.toArray(new Map.Entry[s.size()]);
	Arrays.sort(entries, new StringComparator());
	if(s.size()>0)
        {
            for(Iterator<Object> setIterator = s.iterator(); setIterator.hasNext();)
            //for (int i = 0; i < s.size(); i++)
            {
                Map.Entry m = (Map.Entry)setIterator.next();
                sb.append(NEDSSConstants.STR_TOKEN).append(m.getKey()).append(NEDSSConstants.STR_TOKEN).append(NEDSSConstants.STR_COMMA);
                logger.info(m.getKey()+" => "+m.getValue());
            }

        }

         if(sb == null) sb= sb.append(NEDSSConstants.STR_000);
         return sb.toString().substring(0,(sb.toString()).lastIndexOf(NEDSSConstants.STR_COMMA));

      }


   /**
    * compareTreeMaptoTableCondition  (Not using this function)
    * @param treeMap   the TreeMap
    * @param conditionCd    the String
    * @return TreeMap
    */

    private TreeMap<Object,Object> compareTreeMaptoTableCondition(TreeMap treeMap,String conditionCd)
    {
	//##!! System.out.println("In compareTreeMaptoTableCondition method");
        class StringComparator implements Comparator
	{
	    public int compare(Object a, Object b)
	    {
		 Map.Entry m1 = (Map.Entry)a;
		 Map.Entry m2 = (Map.Entry)b;

		 return (m1.getValue()).toString().compareTo(
			(m2.getValue()).toString());
	    }
	}

	TreeMap<Object,Object> resultMap = new TreeMap<Object,Object>();
        StringBuffer sb = new StringBuffer();
	Set<Object> s = treeMap.entrySet();
	Map.Entry[] entries = (Map.Entry[])s.toArray(new Map.Entry[s.size()]);
	Arrays.sort(entries, new StringComparator());
	//if(treeMap.containsKey(conditionCd)
        for (int i = 0; i < s.size(); i++)
	{
	    Map.Entry m = (Map.Entry)entries[i];


	}
	return resultMap;
      }

    /**
     * Get the MMwrYears  for the dropdown box
     * @param request    the  HttpServletRequest
     */
    private void getDates(HttpServletRequest request)
    {

        String strName = null;
        String strID = null;
        String strValue = null;
        String strSelected = null;
        int intFirstYear = 1950;
        GregorianCalendar cal = new GregorianCalendar();
        int intCurrentYear = cal.get(Calendar.YEAR);
        StringBuffer sb = new StringBuffer("");
        for (int x = intCurrentYear; x >=intFirstYear ; x--)
        {
            strName = Integer.toString(x);

            sb.append(strName).append("$").append(strName).append("|");
        }

      request.setAttribute("MIN_MMWR_YEAR",String.valueOf(intCurrentYear));
      request.setAttribute("MMWRYear",sb.toString());
    }

}