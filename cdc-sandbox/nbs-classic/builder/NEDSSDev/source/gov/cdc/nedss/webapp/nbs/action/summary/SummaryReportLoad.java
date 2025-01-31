package gov.cdc.nedss.webapp.nbs.action.summary;
/**
 * Title:        SummaryReportLoad.java
 * Description:  This Action class Loads the Summary Report Page
 *               which display the details of the SummaryReportProxyVO
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
import gov.cdc.nedss.webapp.nbs.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class SummaryReportLoad
    extends Action
{

    static LogUtils logger = null;

    /**
     *  Default constructor.
     */
    public SummaryReportLoad()
    {

        if (logger == null)
        {
            logger = new LogUtils(this.getClass().getName());
        }
    }
/**
 * The method do the actions based on the Context and redirect to the appropriate page
 * @param mapping    the ActionMapping
 * @param form   the ActionForm
 * @param request  the HttpServletRequest
 * @param response  the HttpServletResponse
 * @return  ActionForward
 * @throws IOException
 * @throws ServletException
 */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {


        WumUtil util = new WumUtil();
        SummaryReportForm summaryReportForm = (SummaryReportForm)form;
        HttpSession session = request.getSession();

        if (session == null)
        {
            logger.debug("error no session");

            throw new ServletException("error no session");
        }


        // Security Object
        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

        //to check security permission for addition  the Summary page
        boolean checkViewPermission = secObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD);
        request.setAttribute("addSumm", String.valueOf(checkViewPermission));

        boolean notificationButton = secObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATESUMMARYNOTIFICATION);
        request.setAttribute("notificationButton", String.valueOf(notificationButton));

        String contextAction = request.getParameter("ContextAction");
         

        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS139", contextAction);
        ErrorMessageHelper.setErrMsgToRequest(request, "PS139");
        NBSContext.lookInsideTreeMap(tm);

        String sCurrTask = NBSContext.getCurrentTask(session);
        request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextAction", tm.get("Submit"));
        request.setAttribute("Cancel",  "/nbs/" + sCurrTask +  ".do?ContextAction=" + tm.get("Cancel"));

        // Retrieving from the summaryReportInfo from the NBSContext
        DSSummaryReportInfo summaryReportInfo = (DSSummaryReportInfo)NBSContext.retrieve(session, NBSConstantUtil.DSSummaryReportInfo);
        logger.debug("counties from Object Store "+ summaryReportInfo.getCounty());
        logger.debug("MMWRYear from Object Store "+ summaryReportInfo.getMmwrYear());
        logger.debug("MMWRWeek from Object Store "+ summaryReportInfo.getMmwrWeek());
        logger.debug("MMWRWeekOprtions from Object Store"+summaryReportInfo.getMMWRWeekOptions());

        // To store the countyCdDescription from the request
        String countyCdDescription = request.getParameter("countyCdDesc");
         
        logger.info("countyCdDescription.....: " + countyCdDescription);
        String strNBS_STATE_CODE = PropertyUtil.getInstance().getNBS_STATE_CODE();
         

        CachedDropDownValues cd = new CachedDropDownValues();
        TreeMap<?,?> tmCounties = cd.getCountyCodes(strNBS_STATE_CODE);
        request.setAttribute("counties",WumUtil.sortTreeMap(tmCounties));

        request.setAttribute("countyValue",summaryReportInfo.getCounty());
        request.setAttribute("countyCdDesc",summaryReportInfo.getCountyCdDesc());
        request.setAttribute("MMWRYear",summaryReportInfo.getMmwrYear());
        request.setAttribute("MMWRWeek",summaryReportInfo.getMmwrWeek());
        request.setAttribute("MIN_MMWR_WEEK",summaryReportInfo.getMmwrWeek());
        request.setAttribute("MMWRWeekOptions",summaryReportInfo.getMMWRWeekOptions());
        String conditionSummaryReport = summaryReportInfo.getConditionCdDescTxt()+" Summary Report";
        request.setAttribute("conditionSummaryReport ", conditionSummaryReport);

        String sSummarReportUid = NBSContext.retrieve(session,NBSConstantUtil.DSSummaryReportUID).toString();

        Long summaryReportUID =  new Long(sSummarReportUid);
        logger.info("summaryReportUID from the objectStore: " + summaryReportUID );

        SummaryReportProxyVO summaryReportProxyVO = this.getSummaryReportProxyVO(summaryReportUID, form,secObj, session, request);
         
        // To set the value for the Source
        String countyCode =  summaryReportInfo.getCounty();

        if(countyCode == null) countyCode = "";
        CachedDropDownValues cond = new CachedDropDownValues();
        TreeMap<Object,Object> sourcefilter = cond.getSRCountyConditionCodeTreeMap(countyCode);
        logger.debug("\n\nThe size of the treemap"+sourcefilter.size());
        String strSourcefilter = util.sortTreeMap(sourcefilter);
        request.setAttribute("condSource",strSourcefilter);

        logger.debug("we are doing batch entry test");
        this.setBatchEntryToFormForView(summaryReportForm);
        this.convertBatchEntryToRequest(summaryReportForm,sourcefilter,request);

        return mapping.findForward("XSP");
    }
   /**
     * The method gets the values of the SummaryReportProxyVO
     * using the method  getSummaryReportProxy in  InvestigationProxyEJB
     * @param summaryReportUID    the Long
     * @param form    the ActionForm
     * @param nbsSecurityObj    the NBSSecurityObj
     * @param session    the HttpSession
     * @param request    the HttpServletRequest
     * @return      the SummaryReportProxyVO
     */
    private SummaryReportProxyVO getSummaryReportProxyVO(Long summaryReportUID, ActionForm form,
                                         NBSSecurityObj nbsSecurityObj, HttpSession session,
                                         HttpServletRequest request)
    {

        MainSessionCommand msCommand = null;
        SummaryReportForm summaryReportForm = (SummaryReportForm)form;
        summaryReportForm.reset();
        summaryReportForm.setReportCollection(null);
        SummaryReportProxyVO summaryReportProxyVO = null;

        try
        {

            String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
            String sMethod = "getSummaryReportProxy";
            logger.debug("sending summaryReportUID to investigationproxyejb, via mainsession");

            Object[] oParams = { summaryReportUID };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            logger.info("mscommand in Load Summary Reports is: " + msCommand);

            // ArrayList<Object> resultArr = new ArrayList<Object> ();
            ArrayList<?> resultArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

            if ((resultArr != null) && (resultArr.size() > 0))
            {
                logger.info("Got the Summary Data !!! ArrayList<Object> = " + resultArr.get(0));
            }

            summaryReportProxyVO = (SummaryReportProxyVO)resultArr.get(0);
            // Setting condition Description to the request
            String ConditionSummaryReport = summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getCdDescTxt()+" Summary Report";
            request.setAttribute("conditionSummaryReport",ConditionSummaryReport);

            // setting the proxy to the Form

            summaryReportForm.setProxy(summaryReportProxyVO);
            //context store can be here

            logger.info("The SummaryReportProxyVO from the investigationProxy  " + summaryReportProxyVO);
        }
        catch (Exception e)
        {
            logger.fatal("ERROR calling getSummaryReportProxy: " + e.getMessage());
            e.printStackTrace();
        }

        return summaryReportProxyVO;
    }

  /**
   * The method get the TotalCount from the corresponding Observation
   * @param obsVOcoll   the Collection<Object>  of ObservationVOs
   * @return    the double
   */

    private double getTotalCount(Collection<Object> obsVOcoll)
    {
                 double count = 0;
          for (Iterator<Object> anIterator1 = obsVOcoll.iterator(); anIterator1.hasNext();)
          {
              ObservationVO observationVO = (ObservationVO)anIterator1.next();
               if(observationVO.getTheObservationDT().getCd()!= null )
               {    String phcCode = observationVO.getTheObservationDT().getCd();
               if(phcCode.equalsIgnoreCase(NEDSSConstants.SUM107))
                {
                  Collection<Object>  obsValueNumericDTColl = observationVO.getTheObsValueNumericDTCollection();
                  for (Iterator<Object> anIterator2 = obsValueNumericDTColl.iterator(); anIterator2.hasNext();)
                  {
                       ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)anIterator2.next();
                       count =  count + obsValueNumericDT.getNumericValue1().doubleValue();
                  }

                }
              }
            }

       return count;
    }
   /**
    * The method takes the values from the SummaryReportForm and set it in the BatchEntryHelper
    * @param summaryReportForm   the SummaryReportForm
    */
    private void setBatchEntryToFormForView(SummaryReportForm summaryReportForm)
    {
        SummaryReportProxyVO summaryReportProxyVO = summaryReportForm.getProxy();
        logger.debug("inside setBatchEntryToForm");
        Collection<Object>  batchEntryCollection  = new ArrayList<Object> ();

        Collection<Object>  arCollection  = null;
        ObservationVO obsVO = null;
        ActRelationshipDT actRelationshipDT = null;


        Collection<Object>  observationVOCollection  = summaryReportProxyVO.getTheObservationVOCollection();
       Iterator<Object>  anIterator = observationVOCollection.iterator();
        while(anIterator.hasNext())
        {
            obsVO = (ObservationVO)anIterator.next();
            //##!! System.out.println(" obsVO:Uid " + obsVO.getTheObservationDT().getUid()  + " cd: " + obsVO.getTheObservationDT().getCd() + " ccdf: " + obsVO.getTheObservationDT().getCtrlCdDisplayForm());
            if(obsVO.getTheObservationDT().getCtrlCdDisplayForm() != null && obsVO.getTheObservationDT().getCtrlCdDisplayForm().trim().equals("SOURCESROW"))
            {

               Collection<ObservationVO>  itemObseravtionVOCollection  = new ArrayList<ObservationVO> ();
               arCollection  = obsVO.getTheActRelationshipDTCollection();
              Iterator<Object>  arItor = arCollection.iterator();
               while(arItor.hasNext())
               {
                  actRelationshipDT = (ActRelationshipDT)arItor.next();
                  ObservationVO itemObs = this.getObservationByUid(observationVOCollection, actRelationshipDT.getSourceActUid());
                  logger.debug(" itemObs : " + (itemObs == null ? "" : itemObs.getTheObservationDT().getCd()));
                  itemObseravtionVOCollection.add(itemObs);
               }

               BatchEntryHelper batchEntryHelper = new BatchEntryHelper();
               batchEntryHelper.setObservationVOCollection(itemObseravtionVOCollection);
               ////##!! System.out.println("\n The batch Entry UID is setting as: "+obsVO.getTheObservationDT().getUid()+"\n\n");
               batchEntryHelper.setUid(obsVO.getTheObservationDT().getUid());

               batchEntryCollection.add(batchEntryHelper);

            }
        }

        if(batchEntryCollection.size() > 0)
        {   
              summaryReportForm.setReportCollection(batchEntryCollection);
              logger.debug("The  size of the batchEntryCollection  while viewing : " +batchEntryCollection.size());
        }
        else
           logger.debug("no reports found for this summary Report ");
    }
  /**
   * Converting the BatchEntry to the request Attribute
   * @param summaryReportForm     the SummaryReportForm
   * @param sourcefilter    the TreeMap
   * @param request     the HttpServletRequest
   */

    private void convertBatchEntryToRequest(SummaryReportForm summaryReportForm,TreeMap<Object,Object> sourcefilter, HttpServletRequest request)
    {


        if(summaryReportForm.getReportCollection() == null)
	logger.debug("batchEntryCollection  is null");
	else
	{
            Collection<Object>  batchEntryCollection  = summaryReportForm.getReportCollection();
	    StringBuffer batchEntryList = new StringBuffer("");
	   Iterator<Object>  itr = batchEntryCollection.iterator();
	    while(itr.hasNext())
	    {
		BatchEntryHelper batchEntry = (BatchEntryHelper) itr.next();
               Iterator<ObservationVO>  it = batchEntry.getObservationVOCollection().iterator();
                if(it.hasNext()){
                 ObservationVO obVO = (ObservationVO) it.next();
                 if(obVO.getTheObservationDT().getRecordStatusCd() != null){
                  if(obVO.getTheObservationDT().getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE))
                  {
                    if(batchEntry.getObservationVOCollection() != null)
                    {
                        String count = "0";
                        BigDecimal  dCount = batchEntry.getObservationVO_s(1).getObsValueNumericDT_s(0).getNumericValue1();
                        if(dCount != null)
                        count = String.valueOf(dCount.intValue());
                        logger.debug("\n\n The value of getObsValueCodedDT_s(0).getCode()" + batchEntry.getObservationVO_s(0).getObsValueCodedDT_s(0).getCode());
                        batchEntryList.append("report[i].observationVO_s[0].obsValueCodedDT_s[0].code");
                        if(batchEntry.getObservationVO_s(0).getObsValueCodedDT_s(0).getCode()!= null)
                          batchEntryList.append(NEDSSConstants.BATCH_PART).append(batchEntry.getObservationVO_s(0).getObsValueCodedDT_s(0).getCode()).append(NEDSSConstants.BATCH_SECT);
                        else
                          batchEntryList.append(NEDSSConstants.BATCH_PART).append("").append(NEDSSConstants.BATCH_SECT);
                        batchEntryList.append("report[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue1_s");
                        batchEntryList.append(NEDSSConstants.BATCH_PART).append(count).append(NEDSSConstants.BATCH_SECT);
                        batchEntryList.append("report[i].observationVO_s[2].obsValueTxtDT_s[0].valueTxt");
                        if(batchEntry.getObservationVO_s(2).getObsValueTxtDT_s(0).getValueTxt()!= null)
                          batchEntryList.append(NEDSSConstants.BATCH_PART).append(batchEntry.getObservationVO_s(2).getObsValueTxtDT_s(0).getValueTxt()).append(NEDSSConstants.BATCH_SECT);
                        else
                          batchEntryList.append(NEDSSConstants.BATCH_PART).append("").append(NEDSSConstants.BATCH_SECT);
                        batchEntryList.append("report[i].statusCd");
                        batchEntryList.append(NEDSSConstants.BATCH_PART).append(obVO.getTheObservationDT().getStatusCd()).append(NEDSSConstants.BATCH_SECT);

                        batchEntryList.append(NEDSSConstants.BATCH_LINE);

                    }
                  }
                 }
              }
	    }
	    logger.debug("batchEntryList for display is: " + batchEntryList.toString());//report[i].statusCd
	    request.setAttribute("batchEntryList", batchEntryList.toString());
	  }

          ObservationVO totalObservationVO = this.getObservationVO(summaryReportForm.getProxy(), NEDSSConstants.SUM107);
          if(totalObservationVO != null)
          {
          String totalCount = String.valueOf(totalObservationVO.getObsValueNumericDT_s(0).getNumericValue1().intValue());
          request.setAttribute("totalCount", totalCount);
          }

    }
 /**
   * get the Observation which has the specific UID
   * @param theObservationVOCollection    the Collection<Object>  of Observations
   * @param observationUid    the  Long
   * @return   the ObservationVO object
   */

    private ObservationVO getObservationByUid(Collection<Object> theObservationVOCollection, Long observationUid)
    {
	String observationCode = "";
	ObservationVO observationVO = null;
	Iterator<Object> anIterator = null;

	if(theObservationVOCollection  != null)
	{
	    anIterator = theObservationVOCollection.iterator();
	    while(anIterator.hasNext())
	    {
		 observationVO = (ObservationVO)anIterator.next();
		 if(observationVO.getTheObservationDT().getObservationUid() != null && observationVO.getTheObservationDT().getObservationUid().equals(observationUid))
		 {
		    logger.debug("getObservationByUid: " + observationVO.getTheObservationDT().getObservationUid());
		    return observationVO;
		 }
	    }
	}
	return null;
    }

   /**
    * The method stores the years between 1950 and the current year to a StringBuffer
    * and set to request attribute
    * @param request   the HttpServletRequest
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
        for (int x = intFirstYear; x <= intCurrentYear; x++)
        {
            strName = Integer.toString(x);

            sb.append(strName).append("$").append(strName).append("|");
        }
        //##!! System.out.println("what is in the buffer  - " + sb.toString());
      request.setAttribute("MMWRYear",sb.toString());
    }
   /**
     * Selects and returns the ObservationVO from
     * a collection of ObservationVOs, which has the specific form code(cd)
     * @param summaryReportProxyVO   the SummaryReportProxyVO
     * @param SummaryReportFormCd   the String
     * @return   the ObservationVO object
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

}