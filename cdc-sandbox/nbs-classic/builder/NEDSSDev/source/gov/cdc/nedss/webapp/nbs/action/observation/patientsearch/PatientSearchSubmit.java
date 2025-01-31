package gov.cdc.nedss.webapp.nbs.action.observation.patientsearch;


import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



/**
 * Title:        Actions
 * Description:  Patient Search Submit
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit;


public class PatientSearchSubmit
    extends Action
{
    public PatientSearchSubmit()
    {
    }
    //For logging
    static final LogUtils logger = new LogUtils(PatientSearchSubmit.class.getName());
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {
    	try {
    		HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
    		String contextAction = request.getParameter("ContextAction");

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");


    		//pass the VOLookup for doing VO for entity search
    		request.setAttribute("VOLookup",
    				(request.getAttribute("VOLookup") == null
    				? "" : (String)request.getAttribute("VOLookup")));


    		/*****************************
    		 * SUBMIT ACTION
    		 */
    		// !!** System.out.println.println("\n\n\nACtion class contextAction is :" + contextAction);
    		String mode = request.getParameter("mode");
    		// !!** System.out.println.println("\n\n\nACtion class mode is :" + mode);

    		if( (contextAction.equalsIgnoreCase("Submit") || contextAction.equalsIgnoreCase("EntitySearch")) && mode == null)
    		{

    			findPeople(form, session, request, false);

    			// add button security
    			boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.ADD);
    			request.setAttribute("addButton", String.valueOf(bAddButton));
    			/*
        		*/
    			String strCurrentIndex = (String)request.getParameter("currentIndex");
    			////##!! System.out.println("waht is the current index - submity    " + strCurrentIndex);

    			request.setAttribute("DSFromIndex",strCurrentIndex);
    			request.setAttribute("mode",request.getParameter("mode"));

    			String PersonName  = request.getParameter("perTitle");

    			//	//##!! System.out.println("What is PersonTitle in PersonSearchSubmit  = "+ PersonName);
    			request.setAttribute("PersonName", PersonName);
    			session.setAttribute("PersonName",PersonName);
    			System.out.println("\n\n\nThe conext action is :" + contextAction);

    			return mapping.findForward(contextAction);

    		}
    		/****************************
    		 * NEXT action
    		 */
    		else if (contextAction.equalsIgnoreCase("Next")  && mode == null)
    		{
    			String strCurrentIndex = (String)request.getParameter(
    					"currentIndex");

    			NBSContext.store(session,"DSFromIndex",strCurrentIndex);
    			return mapping.findForward(contextAction);
    		}
    		/*****************************************
    		 * PREVIOUS ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("Prev"))
    		{
    			String strCurrentIndex = (String)request.getParameter(
    					"currentIndex");

    			NBSContext.store(session,"DSFromIndex",strCurrentIndex);
    			return mapping.findForward(contextAction);
    		}
			/****************************
    		 * ADD action
    		 */
    		else if (contextAction.equalsIgnoreCase("Add"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		/******************************
    		 * refine search action
    		 */
    		else if (contextAction.equalsIgnoreCase("RefineSearch"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		/******************************
    		 * NEW SEARCH ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("NewSearch"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		/*********************************
    		 * VIEW ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("View"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		//View File Action
    		else if (contextAction.equalsIgnoreCase("ViewFile"))
    		{
    			String strUID = request.getParameter("uid");
    			//System.out.println("********** inside view file in ps submit and uid = " + strUID);

    			PersonSummaryVO personSummaryVO = null;
    			MainSessionCommand msCommand = null;
    			try
    			{
    				String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    				String sMethod = "getPersonSummary";
    				Object[] oParams = new Object[] { new Long(strUID) };
    				MainSessionHolder holder = new MainSessionHolder();
    				msCommand = holder.getMainSessionCommand(session);
    				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    				personSummaryVO = (PersonSummaryVO)arr.get(0);
    			}
    			catch (Exception ex)
    			{
    				logger.error("Exception in Patient Search Submit:  " + ex.getMessage());
    				ex.printStackTrace();
    			}
    			NBSContext.store(session, "DSPatientPersonUID", personSummaryVO.getPersonUid());
    			NBSContext.store(session,"DSFileTab","2");
    			return mapping.findForward(contextAction);
    		}

    		else if(mode.equalsIgnoreCase("Next"))
    		{
    			String strCurrentIndex = (String)request.getParameter("currentIndex");
    			request.setAttribute("mode",request.getParameter("mode"));
    			//##!! System.out.println("Current index of Submit is: " + strCurrentIndex);

    			request.setAttribute("DSFromIndex",strCurrentIndex);
    			request.setAttribute("mode",request.getParameter("mode"));
    			return mapping.findForward(contextAction);
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Patient Search Submit: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Patient Search Submit : "+e.getMessage());
    	}
	return null;
    }

    private void findPeople(ActionForm form, HttpSession session,
			    HttpServletRequest request, boolean bEntitySearch)
    {

	ArrayList<?> personList = new ArrayList<Object> ();
	PersonSearchForm psForm = (PersonSearchForm)form;
	PatientSearchVO psVO = (PatientSearchVO)psForm.getPersonSearch();
        psVO.setActive(true);
        if(psVO.getBirthTime() != null && psVO.getBirthTime().trim().length() > 0) {
          this.setAgeAndUnits(psVO);
        }

        if (psVO.getLocalID() != null && !psVO.getLocalID().trim().equals("") &&
        psVO.getPatientIDOperator() != null &&
       !psVO.getPatientIDOperator().equals("CT"))
        {
            SearchResultPersonUtil personSearchSubmit = new SearchResultPersonUtil();
            personSearchSubmit.replaceDisplayLocalIDWithActualLocalID(psVO);
        }



        String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");
            //System.out.println("Context Action in search Submit :"+contextAction);

	//VOTester.createReport(psVO, "findPEOPLE!!!!!!!-view-load");
        //Set temperory for mergeperson
        if(request.getParameter("Mode1")!=null && request.getParameter("Mode1").equals("ManualMerge"))
        {
          psVO.setActive(true);
          psVO.setLogicalDeleted(false);
          psVO.setSuperceded(false);
        }
        session.setAttribute("DSSearchCriteria", psVO);
	NBSContext.store(session, "DSSearchCriteria", psVO);
	session.setAttribute("SearchCriteria",psVO);
       // //##!! System.out.println("Role in search is: "+ psVO.getRole());
	if (psVO != null)
	{

	    NedssUtils nedssUtils = null;
	    MainSessionCommand msCommand = null;
	    String sBeanJndiName = "";
	    String sMethod = "";
	    Object[] oParams = null;
	    sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    sMethod = "findPatient";
	    oParams = new Object[]
	    {
		psVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
		new Integer(0)
	    };

	    try
	    {

		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
                ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName,
							     sMethod, oParams);
		personList = (ArrayList<?> )arrList.get(0);
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }

	    //session.setAttribute("result", personList);
	    session.setAttribute("DSSearchResults", personList);
            //set temperory for merge person
            session.setAttribute("PerMerge",personList);
	    NBSContext.store(session, "DSSearchResults", personList);


	    //  build the criteria string
	    StringBuffer sQuery = new StringBuffer("");
	    CachedDropDownValues cache = new CachedDropDownValues();

	    if (psVO.getLastName() != null &&
		!psVO.getLastName().equals(""))
		sQuery.append("Last Name").append(
			" " +
			cache.getDescForCode("SEARCH_SNDX",
					     psVO.getLastNameOperator()) +
			" ").append("'" + psVO.getLastName() + "'").append(
			", ");

	    if (psVO.getFirstName() != null &&
		!psVO.getFirstName().equals(""))
		sQuery.append("First Name").append(
			" " +
			cache.getDescForCode("SEARCH_SNDX",
					     psVO.getFirstNameOperator()) +
			" ").append("'" + psVO.getFirstName() + "'").append(
			", ");

	    if (psVO.getBirthTime() != null &&
		!psVO.getBirthTime().equals(""))
		sQuery.append("DOB").append(
			" " +
			cache.getDescForCode("SEARCH_NUM",
					     psVO.getBirthTimeOperator()) +
			" ").append("'" + psVO.getBirthTime() + "'").append(
			", ");

	    if (psVO.getCurrentSex() != null &&
		!psVO.getCurrentSex().equals(""))
		sQuery.append("Current Sex Equal ").append(
			"'" +
			cache.getDescForCode("SEX", psVO.getCurrentSex()) +
			"'").append(", ");

	    if (psVO.getStreetAddr1() != null &&
		!psVO.getStreetAddr1().equals(""))
		sQuery.append("Street Address").append(
			" " +
			cache.getDescForCode("SEARCH_SNDX",
					     psVO.getStreetAddr1Operator()) +
			" ").append("'" + psVO.getStreetAddr1() + "'").append(
			", ");

	    if (psVO.getCityDescTxt() != null &&
		!psVO.getCityDescTxt().equals(""))
		sQuery.append("City").append(
			" " +
			cache.getDescForCode("SEARCH_SNDX",
					     psVO.getCityDescTxtOperator()) +
			" ").append("'" + psVO.getCityDescTxt() + "'").append(
			", ");

	    if (psVO.getState() != null && !psVO.getState().equals(""))
		sQuery.append("State Equal ").append(
			"'" + getStateDescTxt(psVO.getState()) + "'").append(
			", ");

	    if (psVO.getZipCd() != null && !psVO.getZipCd().equals(""))
		sQuery.append("Zip").append(
			" " +
			cache.getDescForCode("SEARCH_ALPHA",
					     psVO.getZipCdOperator()) + " ").append(
			"'" + psVO.getZipCd() + "'").append(", ");

	    if (psVO.getLocalID() != null && !psVO.getLocalID().equals(""))
		sQuery.append("Patient ID Equal ").append(
			"'" + psVO.getLocalID() + "'").append(", ");

	    if (psVO.getRootExtensionTxt() != null &&
		!psVO.getRootExtensionTxt().equals(""))
	    {

		if (psVO.getTypeCd() != null &&
		    !psVO.getTypeCd().equals(""))
		    sQuery.append(getIDDescTxt(psVO.getTypeCd())).append(
			    " Equal ").append("'" + psVO.getRootExtensionTxt() +
					"'").append(", ");
	    }

            //System.out.println("Value of SSN is: " + psVO.getSsn());

             if (psVO.getSsn()!= null &&
                 !psVO.getSsn().trim().equals(""))
                 sQuery.append(NEDSSConstants.SNUMBER_LABEL).append(" Equal ").append(psVO.getSsn()).append(",");


            if (psVO.getPhoneNbrTxt() != null &&
		!psVO.getPhoneNbrTxt().equals(""))
		sQuery.append("Telephone").append(
			" " +
			cache.getDescForCode("SEARCH_SNDX",
					     psVO.getPhoneNbrTxtOperator()) +
			" ").append("'" + psVO.getPhoneNbrTxt() + "'").append(
			", ");

            String findRace = getRaceDescTxt(psVO.getRaceCd(),cache.getRaceCodes("ROOT"));

	    if (psVO.getRaceCd() != null && !psVO.getRaceCd().equals(""))
		sQuery.append("Race Equal ").append(
			"'" +
			cache.getDescForCode("ROOT", psVO.getRaceCd()) +
			"'").append(", ");

	    if (psVO.getEthnicGroupInd() != null &&
		!psVO.getEthnicGroupInd().equals(""))
		sQuery.append("Ethnicity Equal ").append(
			"'" +
			cache.getDescForCode("P_ETHN_GRP",
					     psVO.getEthnicGroupInd()) +
			"'").append(", ");

            String findRole = getRoleDescTxt(psVO.getRole(),cache.getCodedValues("RL_TYPE"));

	    if (psVO.getRole() != null && !psVO.getRole().equals(""))
		sQuery.append("Role Equal ").append(
			"'" +
			cache.getDescForCode("RL_TYPE", psVO.getRole()) +
			"'").append(", ");


            session.setAttribute("DSSearchCriteriaString",sQuery.toString());
	}
    }


    private String getRaceDescTxt(String raceCode, String descTxt){

      String returnStr ="";
      if (raceCode != null){
        if(!raceCode.equals("") && descTxt != null){
          String findRace = descTxt.substring(descTxt.indexOf(raceCode));
          String s = findRace.substring(findRace.indexOf("$")+1);
          ////##!! System.out.println("\n\n\n ---------------" + s.substring(0,s.indexOf("|")));
          return s.substring(0,s.indexOf("|")+1);
        }
      }
      return returnStr;
    }

    private String getStateDescTxt(String sStateCd)
    {

	CachedDropDownValues srtValues = new CachedDropDownValues();
	TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
	String desc = "";

	if (sStateCd != null && treemap.get(sStateCd) != null)
	    desc = (String)treemap.get(sStateCd);

	return desc;
    }

    private String getIDDescTxt(String id)
    {



	CachedDropDownValues srtValues = new CachedDropDownValues();
	String desc = srtValues.getCodeDescTxt(id);

	return desc;
    }

    private String getRoleDescTxt(String role , String descTxt)
    {
         String returnStr ="";
         if (role != null){
	  if(!role.equals("") && descTxt != null){
            String findRole = descTxt.substring(descTxt.indexOf(role));
            String s = findRole.substring(findRole.indexOf("$")+1);
            return s.substring(0,s.indexOf("|")+1);
          }
        }
      return returnStr;
    }

    private void setAgeAndUnits(PatientSearchVO psVO) {

      String currentAgeAndUnits = PersonUtil.displayAge(psVO.getBirthTime());

      if(currentAgeAndUnits!=null ||currentAgeAndUnits!="")
      {
        //System.out.println("currentAge and units = " + currentAgeAndUnits);
        int pipe = currentAgeAndUnits.indexOf('|');
        psVO.setAgeReported(currentAgeAndUnits.substring(0, pipe));
        psVO.setAgeReportedUnitCd(currentAgeAndUnits.substring(pipe + 1));
      }

    }//setAgeAndUnits


}