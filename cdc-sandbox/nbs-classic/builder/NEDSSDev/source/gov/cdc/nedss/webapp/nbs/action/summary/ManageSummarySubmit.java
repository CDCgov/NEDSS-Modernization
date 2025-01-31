package gov.cdc.nedss.webapp.nbs.action.summary;

/**
 * Title:        ManageSummarySubmit.java
 * Description:  This Action class Creates the Summary ReportProxyVO and send it to the EJB
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Nedss Development Team
 * @version 1.0
 */

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.summary.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.FormQACode;


//for the old way using entity




public class ManageSummarySubmit extends Action {

 //For logging
  static final LogUtils logger = new LogUtils(ManageSummarySubmit.class.getName());

  public ManageSummarySubmit() {
  }

/**
 * The method do the actions based on the Context and redirect to the appropriate page
 * binds appropriate varibales to request object
 * @param mapping    the ActionMapping
 * @param form   the ActionForm
 * @param request  the HttpServletRequest
 * @param response  the HttpServletResponse
 * @return  ActionForward
 * @throws IOException
 * @throws ServletException
 */

  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {

      HttpSession session = request.getSession(false);
      NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
      String strContextAction = request.getParameter("ContextAction");
      logger.debug("context action " + strContextAction);
     try{
      if (strContextAction == null)
      {
       strContextAction = (String) request.getAttribute("ContextAction");
       logger.debug("(ManageSummary  Submit)  what is the context action ============ " + strContextAction);
      }
      if(strContextAction.equalsIgnoreCase("Condition"))
      {

       String pubicHealthcaseUID = request.getParameter("publicHealthCaseUID");
       NBSContext.store(session,"DSSummaryReportUID",pubicHealthcaseUID );

     }
    else if(strContextAction.equalsIgnoreCase("AddSummaryReport") )
    {
      // To get the county year and week from the request
       String county = request.getParameter("County");
       String countyCdDesc = request.getParameter("countyCdDesc");
       String mmwrWeek = request.getParameter("MMWRWeek");
       String mmwrYear = request.getParameter("MMWRYear");
       String conditioncd = request.getParameter("Condition");
       String conditionCdDescTxt = request.getParameter("conditionCdDescTxt");
       String  MMWRWeekOptions = request.getParameter("MMWRWeekOptions");

       logger.debug("The countyCdDesc for object---"+ countyCdDesc);
       DSSummaryReportInfo summaryReportInfo = new DSSummaryReportInfo();
       summaryReportInfo.setCounty(county);
       summaryReportInfo.setCountyCdDesc(countyCdDesc);
       summaryReportInfo.setMmwrWeek(mmwrWeek);
       summaryReportInfo.setMmwrYear(mmwrYear);
       summaryReportInfo.setCondition(conditioncd);
       summaryReportInfo.setConditionCdDescTxt(conditionCdDescTxt);
       summaryReportInfo.setMMWRWeekOptions(MMWRWeekOptions);

       NBSContext.store(session,"DSSummaryReportInfo",summaryReportInfo);
       logger.debug("The create Handler is called  from manage summary submit");
       this.createHandler(mapping,form,county,mmwrYear,mmwrWeek,conditioncd,request,session,secObj);
    }
    else if(strContextAction.equalsIgnoreCase("GetSummaryData"))
    {
       String county = request.getParameter("County");
       String countyCdDesc = request.getParameter("countyCdDesc");
       String mmwrWeek = request.getParameter("MMWRWeek");
       String mmwrYear = request.getParameter("MMWRYear");
       String conditioncd = request.getParameter("Condition");
       String conditionCdDescTxt = request.getParameter("conditionCdDescTxt");
       String  MMWRWeekOptions = request.getParameter("MMWRWeekOptions");
       String  MMWRWeekDesc = request.getParameter("MMWRWeekDesc");

      request.setAttribute("MMWRWeekOptions",request.getParameter("MMWRWeekOptions"));
      //##!! System.out.println("mmwr options !!!!!!!!!!!!11   " + request.getParameter("MMWRWeekOptions"));
       logger.debug("The countyCdDesc for object---"+ countyCdDesc);
       DSSummaryReportInfo summaryReportInfo = new DSSummaryReportInfo();
       summaryReportInfo.setCounty(county);
       summaryReportInfo.setCountyCdDesc(countyCdDesc);
       summaryReportInfo.setMmwrWeek(mmwrWeek);
       summaryReportInfo.setMmwrYear(mmwrYear);
       summaryReportInfo.setConditionCdDescTxt(conditionCdDescTxt);
       summaryReportInfo.setMMWRWeekOptions(MMWRWeekOptions);
       NBSContext.store(session,"DSSummaryReportInfo",summaryReportInfo);


    }

    //need to process any parameters that come inside here
    Enumeration<?> params = request.getParameterNames();
    while(params.hasMoreElements())
    {
      String sParamName = (String)params.nextElement();
      request.setAttribute(sParamName,request.getParameter(sParamName));
      //##!! System.out.println("(ManageSummary  Submit) param to attribute name = " +  sParamName + "     value = " + request.getParameter(sParamName));
    }
  }
  catch (Exception e){
	  logger.error("Exception in Manage Summary Submit: " + e.getMessage());
    e.printStackTrace();
    throw new IOException();

  }
    return mapping.findForward(strContextAction);

   }

/**
 * Creating the publicHealthCaseDT, ObservationDTs, ActRelashionShips and setting it to
 * the SummaryReportProxyVO
 *
 * @param mapping  the ActionMapping
 * @param form       the ActionForm
 * @param county     the String
 * @param mmwrYear     the String
 * @param mmwrWeek      the String
 * @param conditioncd     the String
 * @param request         the HttpServletRequest
 * @param session        the HttpSession
 * @param nbsSecurityObj     the NBSSecurityObj
 * @throws ServletException
 * @throws Exception
 */
     private void createHandler(ActionMapping mapping,ActionForm form,String county, String mmwrYear, String mmwrWeek,String conditioncd, HttpServletRequest request,
			       HttpSession session, NBSSecurityObj nbsSecurityObj)
			throws ServletException, Exception
    {

       try
       {
            SummaryReportForm summaryReportForm = (SummaryReportForm)form;
            SummaryReportProxyVO summaryReportProxyVO = new SummaryReportProxyVO();
            PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
            PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
            Collection<Object>  obsColl = new ArrayList<Object> ();
            Collection<Object>  actrelations = new ArrayList<Object> ();

            int tempID = -1;

            TreeMap<Object,Object>   programAreas = null;
            TreeMap<Object,Object>   ConditionCdprogramAreaTm = null;
            String progAreaCd = "";
            programAreas =   nbsSecurityObj.getProgramAreas(NBSBOLookup.SUMMARYREPORT,NEDSSConstants.SUMMARY_REPORT_ADD);
            String strProgramAreas = this.sortTreeMaptoString(programAreas);
            //##!! System.out.println("The string program area from the WUM Util" + strProgramAreas);

            CachedDropDownValues cd = new CachedDropDownValues();
            ConditionCdprogramAreaTm = cd.getSummaryReportConditionCodeProgAreaCd(strProgramAreas);
            if(ConditionCdprogramAreaTm.containsKey(conditioncd))
                 progAreaCd = (String)ConditionCdprogramAreaTm.get(conditioncd);

            //##!! System.out.println(" The progAreaCd = "+ progAreaCd );
            // create  the public health case
            String conditionCdDescTxt = request.getParameter("conditionCdDescTxt");
            logger.info("conditionCdDescTxt: " + conditionCdDescTxt);
            String satWeek ="";
            satWeek = request.getParameter("satWeek");
            logger.info("satWeek:"+satWeek);
            publicHealthCaseDT.setPublicHealthCaseUid(new Long(tempID--));
            publicHealthCaseDT.setCaseTypeCd(NEDSSConstants.CASETYPECD);
            publicHealthCaseDT.setRptCntyCd(county);
            publicHealthCaseDT.setMmwrYear(mmwrYear);
            publicHealthCaseDT.setMmwrWeek(mmwrWeek);
            publicHealthCaseDT.setCd(conditioncd);
            //publicHealthCaseDT.setJurisdictionCd("470001");
            publicHealthCaseDT.setProgAreaCd(progAreaCd);
            publicHealthCaseDT.setCdDescTxt(conditionCdDescTxt);
            publicHealthCaseDT.setRptToStateTime_s(satWeek);
            publicHealthCaseDT.setGroupCaseCnt(new Integer(1));
            publicHealthCaseDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_CONFIRMED);
            publicHealthCaseDT.setItNew(true);
            publicHealthCaseDT.setItDirty(false);

            phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
            phcVO.setItNew(true);
            phcVO.setItDirty(false);

            // Create the first ObservationVO
            ObservationVO busObsVO = new ObservationVO();
            ObservationDT busObsDT = new ObservationDT();
            busObsDT.setObservationUid(new Long(tempID--));
            busObsDT.setCd(NEDSSConstants.SUMMARY_REPORT_FORM);
            FormQACode.putQuestionCode(busObsDT);
            busObsDT.setCdSystemCd(NEDSSConstants.NBS);
            busObsDT.setGroupLevelCd(NEDSSConstants.GROUPLEVELCD);
            busObsDT.setItNew(true);
            busObsDT.setItDirty(false);
            busObsVO.setTheObservationDT(busObsDT);
            busObsVO.setItNew(true);
            busObsVO.setItDirty(false);
            obsColl.add(busObsVO);


            // Second observation
            ObservationVO  observationVO = new ObservationVO();
            ObservationDT observationDT = new ObservationDT();
            observationDT.setCd(NEDSSConstants.SUM107);
            observationDT.setCdDescTxt(NEDSSConstants.TOTAL_COUNT);
            FormQACode.putQuestionCode(observationDT);
            observationDT.setCdSystemCd(NEDSSConstants.NBS);
            observationDT.setObservationUid(new Long(tempID--));
            observationDT.setCtrlCdDisplayForm(NEDSSConstants.SumCount);
            observationDT.setItNew(true);
            observationDT.setItDirty(false);
            observationVO.setTheObservationDT(observationDT);
            observationVO.setItNew(true);
            observationVO.setItDirty(false);

            Collection<Object>  obsNumColl = new ArrayList<Object> ();
            ObsValueNumericDT obsValueNumericDT  = new ObsValueNumericDT();
            obsValueNumericDT.setNumericValue1(new BigDecimal(0));
            obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
            obsValueNumericDT.setItNew(true);
            obsValueNumericDT.setItDirty(false);
            obsValueNumericDT.setObservationUid(observationDT.getObservationUid());
            obsNumColl.add(obsValueNumericDT);
            observationVO.setTheObsValueNumericDTCollection(obsNumColl);
            obsColl.add(observationVO);

           // Setting the Act Relationship from busObsVO to phcVO
            ActRelationshipDT actRel = new ActRelationshipDT();
            actRel.setSourceActUid(busObsVO.getTheObservationDT().getObservationUid());
            actRel.setSourceClassCd(NEDSSConstants.SUMMARY_OBS);
            actRel.setTargetActUid(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
            actRel.setTargetClassCd(NEDSSConstants.SUMMARY_CLASS_CD);
            actRel.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            actRel.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            actRel.setTypeCd(NEDSSConstants.SUMMARY_FORM);
            actRel.setItNew(true);
            actRel.setItDirty(false);

            // add to the collection for the proxy
            actrelations.add(actRel);

            // Setting the Act Relationship from busObsVO to sum107
            ActRelationshipDT actRel2 = new ActRelationshipDT();
            actRel2.setSourceActUid(observationVO.getTheObservationDT().getObservationUid());
            actRel2.setSourceClassCd(NEDSSConstants.SUMMARY_OBS);
            actRel2.setTargetActUid(busObsVO.getTheObservationDT().getObservationUid());
            actRel2.setTargetClassCd(NEDSSConstants.SUMMARY_OBS);
            actRel2.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            actRel2.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            actRel2.setTypeCd(NEDSSConstants.SUMMARY_FORM_Q);
            actRel2.setItNew(true);
            actRel2.setItDirty(false);

            // add to the collection for the proxy
            actrelations.add(actRel2);

            // Setting the values to proxy
           summaryReportProxyVO.setThePublicHealthCaseVO(phcVO);
           summaryReportProxyVO.setTheObservationVOCollection(obsColl);
           summaryReportProxyVO.setTheActRelationshipDTCollection(actrelations);
           summaryReportProxyVO.setItNew(true);
           summaryReportProxyVO.setItDirty(false);


           Long phcUID = null;
           phcUID = sendProxyToInvestigationEJB(summaryReportProxyVO,session,request);
           logger.info(" created summaryReportProxyVO created phcUID: " + phcUID);
       }
       catch(Exception e)
       {
    	   logger.error("Exception in Create Summary Submit: " + e.getMessage());
           e.printStackTrace();
           logger.error("Exception while creating summaryReport " + e);
           throw e;
       }

    }



   /**
    * Setting the values to the Database by calling
    * the method setSummaryReportProxy() in InvestigationProxyEJB
    * @param summaryReportProxyVO    the SummaryReportProxyVO
    * @param session    the HttpSession
    * @param request   the HttpServletRequest
    * @return    publicHealthCaseUID     long
    * @throws Exception
    */

    private Long sendProxyToInvestigationEJB(SummaryReportProxyVO summaryReportProxyVO,
					     HttpSession session, HttpServletRequest request)
				      throws Exception
    {

	MainSessionCommand msCommand = null;
	Long publicHealthCaseUID = null;

	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "setSummaryReportProxy";
	    logger.debug("sending SummaryReportProxyVO to investigationproxyejb, via mainsession");

	    Object[] oParams = { summaryReportProxyVO };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    logger.info("mscommand in InvestigationStore is: " + msCommand);

	    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

	    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	    {
		logger.info("Create summaryReport worked!!! publicHealthCaseUID = " +
			resultUIDArr.get(0));
		publicHealthCaseUID = (Long)resultUIDArr.get(0);
	    }



	}
	catch (Exception e)
	{
	    e.printStackTrace();
            logger.error("ERROR calling setSummaryReportProxy: "+ e.getMessage());
            e.printStackTrace();
	    throw e;
	}

	return publicHealthCaseUID;
    }
 /**
   * Sorting the treemap to a String of a specific format
   * @param treeMap     the TreeMap
   * @return    the String with the specified format
   */
     private String sortTreeMaptoString(TreeMap<Object,Object> treeMap)
    {
	class StringComparator implements Comparator<Object>
	{
	    public int compare(Object a, Object b)
	    {
		 Map.Entry<?,?> m1 = (Map.Entry<?,?>)a;
		 Map.Entry<?,?> m2 = (Map.Entry<?,?>)b;

		 return (m1.getValue()).toString().compareTo(
			(m2.getValue()).toString());
	    }
	}

	StringBuffer sb = new StringBuffer();
	Set<?> s = treeMap.entrySet();
	Map.Entry<?,?>[] entries = (Map.Entry[])s.toArray(new Map.Entry<?,?>[s.size()]);
	Arrays.sort(entries, new StringComparator());
	for (int i = 0; i < s.size(); i++)
	{
	    Map.Entry<?,?> m = (Map.Entry<?,?>)entries[i];
	    sb.append(NEDSSConstants.STR_TOKEN).append(m.getKey()).append(NEDSSConstants.STR_TOKEN).append(NEDSSConstants.STR_COMMA);
	    logger.info(m.getKey()+" => "+m.getValue());
	}
         return sb.toString().substring(0,sb.toString().lastIndexOf(NEDSSConstants.STR_COMMA));
      }


 }