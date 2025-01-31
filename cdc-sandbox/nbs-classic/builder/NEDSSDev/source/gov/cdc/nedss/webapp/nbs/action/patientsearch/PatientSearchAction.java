package gov.cdc.nedss.webapp.nbs.action.patientsearch;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonInvestgationSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchResultsVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSrchResultVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.patientsearch.PatientSearchForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;


public class PatientSearchAction extends DispatchAction
{
	static final LogUtils logger = new LogUtils(PatientSearchAction.class.getName());
	private static CachedDropDownValues srtValues = new CachedDropDownValues();
    private static TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    	
		public ActionForward searchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
			  
			  
			try {
				PatientSearchForm psForm = (PatientSearchForm) form;

				  psForm.clearSelections();

					//Prepopulate Demographic attributes
				  psForm.getPatientSearchVO().setLastNameOperator("CT");
				  psForm.getPatientSearchVO().setFirstNameOperator("CT");
				  psForm.getPatientSearchVO().setBirthTimeOperator("=");  
				  psForm.getPatientSearchVO().setPatientIDOperator("=");
				  psForm.getPatientSearchVO().setActive(true);
				  psForm.setPatientSearch("pat");
				 
				  String caseLocalId = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
				  String programArea =  (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea);
				
				  logger.debug("Program Area is ; " + programArea);
				  psForm.getAttributeMap().put("isStdOrHivProgramArea", PropertyUtil.isStdOrHivProgramArea(programArea));
				  NBSContext.store(request.getSession(), "DSInvestigationUID", caseLocalId);
				  request.setAttribute("caseLocalId", HTMLEncoder.encodeHtml(caseLocalId));
				  
				  Long personMprUid = (Long)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientMPRUID);
				  psForm.setPersonMprUid(personMprUid.toString());
				  //check request and parameter if other infected patient
				  String contactPatientIdentifier = (String) request.getAttribute("contactPatientEle");
				  if (contactPatientIdentifier != null)
						  psForm.getAttributeMap().put("contactPatientEle", contactPatientIdentifier);
				  contactPatientIdentifier=request.getParameter("contactPatientEle");
				  if (contactPatientIdentifier != null)
					  psForm.getAttributeMap().put("contactPatientEle", contactPatientIdentifier);
				  
				  return (mapping.findForward("searchPatient"));
			} catch (Exception e) {
				logger.error("Exception in PatientSearchAction.searchLoad: " + e.getMessage());
				e.printStackTrace();
				throw new ServletException("Error while  searchLoad: "+e.getMessage(),e);
			}
			}
			
		public ActionForward patientSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException{
		  
        try {
			ArrayList<?> personList = new ArrayList<Object> ();
			PatientSearchForm psForm = (PatientSearchForm)form;
			HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
			String contactPatientIdentifier = psForm.getAttributeMap().get("contactPatientEle")== null ? "" :HTMLEncoder.encodeHtml((String) psForm.getAttributeMap().get("contactPatientEle"));
			String investigationUID = HTMLEncoder.encodeHtml((String)NBSContext.retrieve(request.getSession(),"DSInvestigationUID"));
			String epiLinkId = null;
			try{
				epiLinkId = (String)NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSLotNbr);
			}catch(Exception ex){
				logger.debug("EPI Link Id not in context for this investigation Uid: "+investigationUID);
			}
			Long patientPersonUID  = 0L;
			 if(!psForm.getPatientSearch().equals("eplnk")){
				 patientPersonUID =  (Long)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientMPRUID);
			 }
		
			PatientSearchVO psVO = (PatientSearchVO)psForm.getPatientSearchVO();
			   psVO.setLastNameOperator("CT");
			   psVO.setFirstNameOperator("CT");
			   psVO.setBirthTimeOperator("=");  
			   psVO.setPatientIDOperator("CT");
			// On create of new patient, we need to prepopulate the CR Patient with search criteria.
			Map<Object,Object> searchMap = new HashMap<Object, Object>();
			searchMap.put("LASTNM", psVO.getLastName()== null ? "" :psVO.getLastName());
			searchMap.put("FIRSTNM", psVO.getFirstName()== null ? "" :psVO.getFirstName());
			searchMap.put("DOB", psVO.getBirthTime()== null ? "" :psVO.getBirthTime());
			searchMap.put("SEX", psVO.getCurrentSex()== null ? "" :psVO.getCurrentSex());
			_populateBirthTime(psVO);
			NBSContext.store(request.getSession(), NBSConstantUtil.DSPatientMap,searchMap);
			String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
			request.setAttribute("identifier", identifier);
			psVO.setActive(true);
			psVO.setContactTracing(true);
			psVO.setStatusCodeActive("ACTIVE");
			if (psVO != null)
			{
				
			    MainSessionCommand msCommand = null;
			    String sBeanJndiName = "";
			    String sMethod = "";
			    Object[] oParams = null;
			    sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			    	if(psForm.getPatientSearch().equals("pat")){
			    		
			    		psVO.setEpilink(false);
			    		psVO.setEpiLinkId(null);
			    	}
			    	if(psForm.getPatientSearch().equals("evt")){
			    		
			    		psVO.setEpilink(false);
			    		psVO.setEpiLinkId(null);
			    	}
					if(psForm.getPatientSearch().equals("eplnk")){
			    		
			    		psVO.setEpilink(true);
			    		//psVO.setActId(investigationUID);
			    		psVO.setEpiLinkId(epiLinkId);
			    	}


			    		sMethod = "findPatient";
			    		  oParams = new Object[]
			    				    {
			    				        psVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
			    				        new Integer(0)
			    				    };
			    	

			    MainSessionHolder holder = new MainSessionHolder();
			    msCommand = holder.getMainSessionCommand(request.getSession());
			    ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			    personList = (ArrayList<?> )arrList.get(0);

		    	 DisplayPersonList  list = (DisplayPersonList)personList.get(0);
				    ArrayList<Object> personSrchResultVOList = list.getList();
				    int totalCount = list.getTotalCounts();
				    
				String scString = this.buildSearchCriteriaString(psVO);
		    	request.setAttribute("SearchCriteria", scString);
				
			    request.getSession().setAttribute("psVO", psVO);
			    
			    request.getSession().setAttribute("PatientSrchResults", personSrchResultVOList);
			    request.setAttribute("patientsList", makePatientResults(personSrchResultVOList,identifier, HTMLEncoder.encodeHtml(patientPersonUID.toString()),request,totalCount,contactPatientIdentifier));
			 // add button security
    			boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.ADD);
    			request.setAttribute("addButton", HTMLEncoder.encodeHtml(String.valueOf(bAddButton)));
			    String otherIdentifier = "";
			    if (!contactPatientIdentifier.isEmpty())
			    	otherIdentifier = "&contactPatientEle=" +HTMLEncoder.encodeHtml(contactPatientIdentifier);
			    request.setAttribute("RefineSearchLink", "PatientSearch.do?method=refineSearch&identifier="+identifier+otherIdentifier);
			    request.setAttribute("NewSearchLink", "PatientSearch.do?method=searchLoad&identifier="+identifier+otherIdentifier);
			   
			}
			
			} catch (RemoteException e) {
				logger.error("Error RemoteException:", e.getMessage());
			throw new ServletException("Error while cpatientSearchSubmit: "+e.getMessage(),e);
			} catch (NEDSSConcurrentDataException e) {
				logger.error("Error NEDSSConcurrentDataException:", e.getMessage());
			throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} catch (EJBException e) {
				logger.error("Error EJBException:", e.getMessage());
			throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} 
			catch (NEDSSAppException e) {
				logger.error("Error NEDSSAppException:", e.getMessage());
				throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} catch (CreateException e) {
				logger.error("Error CreateException:", e.getMessage());
			throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} catch (NamingException e) {
				logger.error("Error NamingException:", e.getMessage());
				throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} catch (InvocationTargetException e) {
				logger.error("Error InvocationTargetException:", e.getMessage());
				throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} catch (IllegalAccessException e) {
				logger.error("Error IllegalAccessException:", e.getMessage());
				throw new ServletException("Error while patientSearchSubmit: "+e.getMessage(),e);
			} 
			catch (Exception e) {
				logger.error("Exception in PatientSearchAction.patientSearchSubmit " + e.getMessage());
				e.printStackTrace();
				ActionErrors errors = (ActionErrors)request.getAttribute("error_messages");
				if(errors == null) errors = new ActionErrors();
			    errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.required","At least one field" ));
				request.setAttribute("error_messages", errors);
				return (mapping.findForward("searchPatient"));
			}
			return (mapping.findForward("searchResultsPatient"));
			}

	public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
		try {
			String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
			request.setAttribute("identifier", identifier);
			String contactPatientEle = HTMLEncoder.encodeHtml(request.getParameter("contactPatientEle"));
			if (contactPatientEle != null)
				  request.setAttribute("contactPatientEle", contactPatientEle); 
		} catch (Exception e) {
			logger.error("Exception in PatientSearchAction.refineSearch: " + e.getMessage());
			throw new ServletException("Error while refineSearch: "+e.getMessage(),e);
		}
		return (mapping.findForward("searchPatient"));
	}
	
	public ActionForward loadProvider(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		return (mapping.findForward("loadResults"));
	}



	//	ViewFile1
	public ActionForward loadPatientEvents(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		return (mapping.findForward("viewPatientEvents"));
	}


	public ActionForward patientInvestigationListLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
  
		try {
			PatientSearchForm psForm = (PatientSearchForm) form;
			String selectedPatientUid = request.getParameter("selectedPatientUid");
			Long patientUid = new Long(selectedPatientUid);
			Collection<Object> patientInvestigations = getInvestigationSummary(request, patientUid);
			setPatientInvestigationSummary(psForm, selectedPatientUid, patientInvestigations, request);
			setPatientInvestigationSelectedInfo(selectedPatientUid, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error while patientInvestigationListLoad: "+e.getMessage(),e);
		}
		return (mapping.findForward("patientInvestigations"));
	}
	
	/*
	 * Get the Selected Patient Name for use in the Investigation Select Screen for Contact STD Other Person
	 */
	private void setPatientInvestigationSelectedInfo(String selectedPatientUid,
			HttpServletRequest request) {
		logger.debug(" In setPatientInvestigationSelectedInfo()");
		String patientName = "(No Name)";
		StringBuffer sbAddress = new StringBuffer(""); 
		String cellPhone = "";
		String homePhone = "";
		String workPhone = "";
		String emailAddress = "";
		
		try {
			ArrayList<Object> personSrchResultVOList = (ArrayList<Object>) request.getSession().getAttribute("PatientSrchResults");
			if (personSrchResultVOList != null) {
				  Iterator<Object> resultsIter = personSrchResultVOList.iterator();
				  while (resultsIter.hasNext()) {
					  	PersonSrchResultVO resultsVO = (PersonSrchResultVO) resultsIter.next();
					  	if(resultsVO.getPersonUID().toString().equals(selectedPatientUid)) {
					  			Collection<Object>  namesColl = resultsVO.getPersonNameColl();
					  				if(namesColl != null){
					  					Iterator<Object> namesIter = namesColl.iterator();
					  					while(namesIter.hasNext()) {
					  							PersonNameDT name = (PersonNameDT) namesIter.next();
					  								if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("Legal")){
					  											patientName = name.getFirstNm() + " " + name.getLastNm();
					  								}
					  					}//while name 
					  				} //namesColl not null
					  				Collection<Object>  locatorsColl = resultsVO.getPersonLocatorsColl();
					  				if (locatorsColl != null) {
					  					Iterator<Object> locatorIter = locatorsColl.iterator();
					  					while(locatorIter.hasNext()) {
					  						EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) locatorIter.next();
					  						//get Home Address if there
					  						if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("PST") 
					  								&& elpDT.getUseCd().equalsIgnoreCase("Home") ) {
					  								PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
					  									if (postal != null && (postal.getStreetAddr1() != null || postal.getCityCd() != null || postal.getZipCd() != null)) {
					  										String streetAdd1 = postal.getStreetAddr1();
					  										String streetAdd2 = postal.getStreetAddr2();
					  										String cityCd = postal.getCityCd();
					  										String stateDesc = getStateDescTxt(postal.getStateCd());
					  										String zipCd = postal.getZipCd();
					  										if(streetAdd1 != null && streetAdd1.trim().length() > 0)
					  											sbAddress.append(streetAdd1);
					  										if(streetAdd2!= null && streetAdd2.trim().length() > 0)
					  											sbAddress.append(streetAdd2).append("</br>");
					  										else
					  											sbAddress.append("</br>");
					  										if(cityCd != null && cityCd.trim().length() > 0)
					  											sbAddress.append(cityCd).append(", ");
					  										if(stateDesc != null && stateDesc.trim().length() > 0)
					  											sbAddress.append(stateDesc).append(" ");
					  										if(zipCd != null && zipCd.trim().length() > 0)	
					  											sbAddress.append(zipCd);
					  									} //if address data present
					  						} //if home adr
					  				  		//get Tele Locator
					  				  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("TELE")) {
					  				  			TeleLocatorDT tele = elpDT.getTheTeleLocatorDT();
					  				  			if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("CP" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
					  					  			cellPhone = tele.getPhoneNbrTxt();
					  				  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("PH" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
						  					  		homePhone = tele.getPhoneNbrTxt();
					  				  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("WK" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
					  					  			workPhone = tele.getPhoneNbrTxt();						  					  			
					  				  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("NET" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
					  					  			emailAddress = tele.getEmailAddress();						  					  			
					  				  			}
					  				  		}
					  					} //locator has next
					  				} 
					  	} //if selected patient			  	
					  	
				  } //search result iter has next
			} //VO list not null
		} catch (Exception e) {
			logger.warn("Warning: can't find patient contact search list in session??:", e.getMessage());
		}
		//build string for the display
		StringBuffer sbDisplay = new StringBuffer(""); 
		sbDisplay.append(patientName).append("</br>");
		if (sbAddress.length() > 0)
			sbDisplay.append(sbAddress).append("</br>");
		if (!homePhone.isEmpty())
			sbDisplay.append("hm:").append(homePhone).append("</br>");
		if (!cellPhone.isEmpty())
			sbDisplay.append("cell:").append(cellPhone).append("</br>");
		if (!workPhone.isEmpty())
			sbDisplay.append("wk:").append(workPhone).append("</br>");;		
		if (!emailAddress.isEmpty())		
			sbDisplay.append(emailAddress);
		request.setAttribute("ContactPatientName", HTMLEncoder.encodeHtml(patientName));
		request.getSession().setAttribute(CTConstants.ContactPatientDetails, sbDisplay.toString());
	}

	/*
	 * setInvestigationSummary - Set the list of investigations for the supplemental page
	 * retrieved using wumSqlQuery into Request
	 * @param investigationSummaryVOCollection
	 * @param request
	 */
	public static void setPatientInvestigationSummary(
			PatientSearchForm psForm,
			String patientUid,
			Collection<Object> investigationSummaryVOCollection,
			HttpServletRequest request) {
		Collection<PersonInvestgationSummaryDT> investigationEventList = new ArrayList<PersonInvestgationSummaryDT>();
		CachedDropDownValues cddV = new CachedDropDownValues();
		boolean atLeastOneSelect = false;
		logger.debug(" In setPatientInvestigationSummary()");
		String srcCondition = null;
		String srcLotNbr = null;
		try {
			srcCondition = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationCondition);
			srcLotNbr = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSLotNbr);
		} catch (Exception e) {
	            logger.error("PatientSearchAction. setPatientInvestigationSummary condition and lot lookup exception?:" + e);
	    }
		
		if (investigationSummaryVOCollection == null) {
			logger.debug("investigation summary collection arraylist is null");
		} else {
			Iterator<Object> itr = investigationSummaryVOCollection.iterator();

			while (itr.hasNext()) {
				PersonInvestgationSummaryDT dt = new PersonInvestgationSummaryDT();

				InvestigationSummaryVO investigation = (InvestigationSummaryVO) itr
						.next();

				if (investigation != null
						&& investigation.getPublicHealthCaseUid() != null) {

					String strStartDate = investigation.getActivityFromTime() == null ? "No Date " : StringUtils.formatDate(investigation.getActivityFromTime());					
					dt.setStartDate(strStartDate);
					String investigatorFirstName = investigation
							.getInvestigatorFirstName() == null ? ""
							: investigation.getInvestigatorFirstName();
					String investigatorLastName = investigation
							.getInvestigatorLastName() == null ? ""
							: investigation.getInvestigatorLastName();
					dt.setInvestigator(investigatorFirstName + " "
							+ investigatorLastName);
					dt.setConditions((investigation.getConditionCodeText() == null) ? ""
							: investigation.getConditionCodeText());

					dt.setCaseStatus((investigation.getCaseClassCodeTxt() == null) ? ""
							: investigation.getCaseClassCodeTxt());

					dt.setJurisdiction((investigation.getJurisdictionDescTxt() == null) ? ""
							: investigation.getJurisdictionDescTxt());

					dt.setInvestigationId((investigation.getLocalId() == null) ? ""
							: investigation.getLocalId());
					if (investigation.getInvestigationStatusCd().equals("O")) {
						String status = "<b><font color=\"#006000\">Open</font></b>";
						dt.setStatus(status);
					} else {
						dt.setStatus(investigation
								.getInvestigationStatusDescTxt());
					}
				
					String theDispoCd = investigation.getDisposition();
					String theDispoDesc = "";
					if (theDispoCd != null && !theDispoCd.isEmpty()) {
						theDispoDesc = cddV.getDescForCode( CTConstants.StdNbsDispositionLookupCodeset,theDispoCd);
					}
					dt.setDisposition(theDispoDesc);
					String startsWith = "";
					String conditionCd = investigation.getCd(); 
					if (conditionCd.startsWith("1031"))
						startsWith = "1031"; //syph
					else if (conditionCd.startsWith("1056"))
						startsWith = "1056"; //hiv
					else startsWith = conditionCd;
					//same condition family
					//if same condition, and open and infected and interviewed then can select
					//tbd: change startsWith logic
					if (srcCondition != null && (srcCondition.startsWith(startsWith) || (srcCondition.equals("900") && conditionCd.startsWith("1056")) ) ) {
						if (investigation.getInvestigationStatusCd() != null && 
								investigation.getInvestigationStatusCd().equals("O") && 
								theDispoCd != null
								&& (theDispoCd.equals("C") || theDispoCd.equals("D") || theDispoCd.equals("E")||
										theDispoCd.equals("1") || theDispoCd.equals("2") || theDispoCd.equals("5")) &&
								investigation.getPatIntvStatusCd() != null && 
								investigation.getPatIntvStatusCd().equals(CTConstants.PatientInterviewedStatusCd)) {
							//add the select action link
							String differentLot = "false";
							String lotNbr = investigation.getEpiLinkId();
							if (lotNbr != null && srcLotNbr != null && !srcLotNbr.equalsIgnoreCase(lotNbr))
									differentLot = "true";
							String patientRevisionUid = investigation.getPatientRevisionUid().toString();
							String id = (String) psForm.getAttributeMap().get("contactPatientEle");
							
							Long investigatorId = investigation.getInvestigatorMPRUid();
							String url = "<a href=\"javascript:populateOtherPatient('" + patientRevisionUid   + "','" + differentLot + "','" + investigation.getPublicHealthCaseUid().toString() + "','" + id +"','" +investigatorId.toString() + "');\">Select</a>";
							dt.setActionLink(url);
							atLeastOneSelect = true;
						}
					}
					investigationEventList.add(dt);
				} //not null
			} //while hasNext
			request.setAttribute("InvestigationsToSelect", atLeastOneSelect);
			request.setAttribute("patientInvestigationList",
					investigationEventList);
			//request.getSession().setAttribute("strContactInvestigationList",
			//		investigationEventList);
			NBSContext.store(request.getSession(), "thirdPartyInvestigationSummaryVOCollection", investigationSummaryVOCollection);
			request.getSession().setAttribute("thirdPartyInvestigationSummaryVOCollection", investigationSummaryVOCollection);
			request.setAttribute("patientInvestigationListSize",
					investigationEventList.size() == 0 ? "0" : new Integer(
							investigationEventList.size()).toString());
		}
		return;
	}	
	/*
	 * getInvestigationSummary - load summary of the contacts investigations
	 * 		for the supplemental page
	 */
	public static Collection<Object> getInvestigationSummary(HttpServletRequest request, Long personUID)  {
		ArrayList<Object> invSumAR = null;
		try {		
	  String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
      String sMethod = "getPersonInvestigationSummary";
      Object[] oParams = new Object[] {personUID};
      MainSessionHolder holder = new MainSessionHolder();
      MainSessionCommand msCommand = holder.getMainSessionCommand(request.getSession());
      ArrayList<?> arr;

		arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    invSumAR  = (ArrayList<Object>) arr.get(0);
		if(invSumAR!=null && invSumAR.size()>0){
			NedssUtils nUtil = new NedssUtils();
			nUtil.sortObjectByColumn("getInvestigationStatusCd", invSumAR, false);
		}

		} catch (NEDSSConcurrentDataException e) {
			logger.error("Data concurrency error while loading Investigation Summary for Contact: " + e.toString());
		} catch (Exception e) {
			logger.error("Exception occurred while loading Investigation Summary for Contact: " + e.toString());
		}
		return invSumAR;
	}
	
	private ArrayList<Object> makePatientResults(ArrayList<Object> results, String id, String patientPersonUID,HttpServletRequest request,int totalCount, String contactPatientIdentifier) throws ServletException {
		
		ArrayList<Object> returnList = new ArrayList<Object> ();
		try {
			StringBuffer sb = null;
			String programArea = null;
			
			Iterator<Object> mainIter = results.iterator();
			Collection<?> contactCol = new ArrayList<Object>();
			try {
				contactCol =  (ArrayList<?>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSContactColl);
			} catch (Exception ex) {
				logger.debug("PatientSearchAction: DSContactColl not in scope");
			}
			try {
				programArea =  (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea);
			} catch (Exception ex) {
					logger.debug("PatientSearchAction: DSInvestigationProgramArea not in scope");
			}				
			boolean isStdContact = false;
			if (programArea != null)
				isStdContact = PropertyUtil.isStdOrHivProgramArea(programArea);
			boolean contactExists;
			
			while(mainIter.hasNext()) {
				
				
				sb = new StringBuffer("");
				PatientSearchResultsVO displayVO = new PatientSearchResultsVO();
				
				PersonSrchResultVO resultsVO = (PersonSrchResultVO) mainIter.next();
				//set PersonUID
				displayVO.setPersonUID(resultsVO.getPersonUID());
				//Adding logic for excluding contacts already named by patient from search result.
				contactExists=false;
				if (!isStdContact) { // STD/HIV according to Tammy allow the same contact in the list more than once 
					Iterator<?> conIter = contactCol.iterator();	  		
					while(conIter.hasNext())
					{ 
						CTContactSummaryDT  conDT = (CTContactSummaryDT) conIter.next();	  			
						if(conDT.isContactNamedByPatient() && conDT.getContactMprUid()!= null && conDT.getContactMprUid().toString().equals(resultsVO.getPersonUID().toString()))
						{
							contactExists = true;
							totalCount=totalCount-1;
							break;
						}
					}
				}
				// Don't show contacts with no investigations for Contact Other Infected Person
				//if (isStdContact && contactPatientIdentifier != null && !contactPatientIdentifier.isEmpty()) { 
				//	if (resultsVO.getConditionCdColl() == null || resultsVO.getConditionCdColl().isEmpty()) {
				//		hasInvestigations = false;
				//		totalCount=totalCount-1;
				//	}
				//}
				
								
				if(resultsVO.getPersonUID().toString().equals(patientPersonUID))
					totalCount=totalCount-1;
				
				 if(!resultsVO.getPersonUID().toString().equals(patientPersonUID) && !contactExists){	
				  		Collection<Object>  namesColl = resultsVO.getPersonNameColl();
				  		Collection<Object>  locatorsColl = resultsVO.getPersonLocatorsColl();
				  		Collection<Object>  idColl = resultsVO.getPersonIdColl();
				  		Collection<Object>  conditionColl = resultsVO.getConditionCdColl();
				
			if(namesColl != null){
				Iterator<Object> namesIter = namesColl.iterator();
				sb.append("<table role=\"presentation\">");
				while(namesIter.hasNext()) {
					PersonNameDT name = (PersonNameDT) namesIter.next();
					if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("Legal")){

						String useCd = name.getNmUseCd();
			  			String lastNm = name.getLastNm();
			  			String firstNm = name.getFirstNm();
			  			String fullName="";
			  			if(useCd != null && useCd.trim().length() > 0)
			  				sb.append("<tr><td><i>").append(useCd).append("</i></td></tr>");
			  			if(lastNm != null && lastNm.trim().length() > 0)
			  				fullName =lastNm.trim();
			  			if((lastNm != null && lastNm.trim().length() > 0) && (firstNm != null && firstNm.trim().length() > 0))
			  				fullName = fullName + ",";
			  			if(firstNm != null && firstNm.trim().length() > 0)
			  				fullName = fullName + firstNm.trim();
			  			if(fullName.trim().length() > 0){
			  				sb.append("<tr><td><a href =\"javascript:viewPatientEvents('/nbs/LoadPatientSearch.do?method=loadPatientEvents&ContextAction=viewFile&uid="+resultsVO.getPersonParentUid()+"');\">");
		  				sb.append(fullName).append("</a>").append("</td></tr>");
			  			}


					}
				}
				sb.append("</table>");
				displayVO.setFullName(sb.toString());
				
			  }
			
			if((resultsVO.getAgeReported() != null) || (resultsVO.getPersonDOB() != null) || (resultsVO.getCurrentSex() != null)){
				sb = new StringBuffer("<table role=\"presentation\">"); 
				if(resultsVO.getAgeReported() != null)
				sb.append("<tr><td>").append(resultsVO.getAgeReported()).append(" ").append(resultsVO.getAgeUnit()).append("</td></tr>");	 
				if(resultsVO.getPersonDOB() != null)	
				sb.append("<tr><td>").append(resultsVO.getPersonDOB()).append("</td></tr>");	
				if(resultsVO.getCurrentSex() != null)	
			  	sb.append("<tr><td>").append(resultsVO.getCurrentSex()).append("</td></tr>");
				sb.append("</table>");
				displayVO.setAgeSexDOB(sb.toString());
			}
			
				//Full Name
			if(locatorsColl != null){
				
				sb = new StringBuffer("<table role=\"presentation\">"); 		
				StringBuffer sb1 = new StringBuffer("<table role=\"presentation\">");
			  	Iterator<Object> locatorIter = locatorsColl.iterator();
			  	while(locatorIter.hasNext()) {
			  		EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) locatorIter.next();
			  		//Postal Locator
			  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("PST")) {
			  			String useCd = elpDT.getUseCd();
			  			if(useCd != null && useCd.trim().length() > 0)
			  				sb.append("<tr><td><i>").append(useCd).append("</i></td></tr>");
			  			PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
			  			if (postal != null && (postal.getStreetAddr1() != null || postal.getCityCd() != null || postal.getStateCd() != null || postal.getZipCd() != null)) {

			  				String streetAdd1 = postal.getStreetAddr1();
				  			String streetAdd2 = postal.getStreetAddr2();
				  			String cityCd = postal.getCityCd();
				  			String stateDesc = getStateDescTxt(postal.getStateCd());
				  			String zipCd = postal.getZipCd();
				  			if(streetAdd1 != null && streetAdd1.trim().length() > 0)
				  				sb.append("<tr><td>").append(streetAdd1).append("</td></tr>");
				  			if(streetAdd2!= null && streetAdd2.trim().length() > 0)
				  				sb.append("<tr><td>").append(streetAdd2).append("</td></tr>");
				  			sb.append("<tr><td>");
				  			if(cityCd != null && cityCd.trim().length() > 0)
				  				sb.append(cityCd).append(", ");
				  			if(stateDesc != null && stateDesc.trim().length() > 0)
				  				sb.append(stateDesc).append(" ");
				  			if(zipCd != null && zipCd.trim().length() > 0)	
				  				sb.append(zipCd);
				  			sb.append("</td></tr>");
			  			}
			  		} 
			  		//Tele Locator
			  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("TELE")) {
			  			TeleLocatorDT tele = elpDT.getTheTeleLocatorDT();
			  			if(elpDT.getUseCd() != null && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
				  			String useCd = elpDT.getUseCd();
				  			String phoneNbr = tele.getPhoneNbrTxt();
				  			sb1.append("<tr><td><i>").append(useCd).append("</i></td></tr>");		  			
				  			sb1.append("<tr><td>").append(phoneNbr).append("</td></tr>");
			  			}
			  		}
			  	}
			  	sb.append("</table>");
			  	sb1.append("</table>");
			  	
			  	
			  	//Address
			  	displayVO.setAddress(sb.toString());
			  	//Telephone
			  	displayVO.setTelephone(sb1.toString());
			}
			 if(conditionColl != null){
				Iterator<Object> condsIter = conditionColl.iterator();
				sb = new StringBuffer("<table role=\"presentation\">"); 	
				
				while(condsIter.hasNext()) {
					   //PersonConditionDT dt = (PersonConditionDT) condsIter.next();	
						String conditionDesc = condsIter.next().toString();		  					  			
			  			if(conditionDesc != null && conditionDesc.length()> 0)
			  				sb.append("<tr><td>").append( conditionDesc).append("</td></tr>");;
			  						
					}
				
				sb.append("</table>");
				displayVO.setConditions(sb.toString());
			  }
			  	//add DisplayVO to the returnList
			  	returnList.add(displayVO);
			}
			
			  	//add DisplayVO to the returnList
			  	//returnList.add(displayVO);  -- gst was getting blank line
			}
			Iterator<Object> ite = returnList.iterator();
			while (ite.hasNext()) {
				
				PatientSearchResultsVO  psrVO = (PatientSearchResultsVO) ite.next();
				if(psrVO.getFullName()!= null && contactPatientIdentifier.isEmpty()){
					String url = "<a href=\"javascript:populatePatient('" + psrVO.getPersonUID().toString() + "','" + id + "');\"><img alt=\"Accept\" title=\"Accept\" src=\"accept.gif\" /></a>";
					psrVO.setActionLink(url);
				} else {  //will show investigations for selected person next..
					String url = "<a href=\"javascript:showPatientInvestigations('" + psrVO.getPersonUID().toString() + "','" + contactPatientIdentifier + "');\"><img alt=\"Accept\" title=\"Accept\" src=\"accept.gif\" /></a>";
					psrVO.setActionLink(url);
				}
				
			} 
			request.setAttribute("ResultsCount", HTMLEncoder.encodeHtml(String.valueOf(totalCount)));
		} catch (Exception e) {
			logger.error("Error Exception:", e.getMessage());
			throw new ServletException("Error while makePatientResults: "+e.getMessage(),e);
		}
		return returnList;
	}


   private String getStateDescTxt(String sStateCd) throws ServletException {
   		String desc= null;
      	try {
			if (treemap != null){
			  if(sStateCd!=null && treemap.get(sStateCd)!=null)
			  	desc = (String)treemap.get(sStateCd);
			}
		} catch (Exception e) {
			logger.error("Error Exception:", e.getMessage());
			throw new ServletException("Error while getStateDescTxt: "+e.getMessage(),e);
		}
      	return desc;
    }	
   private String buildSearchCriteriaString(PatientSearchVO psVO) throws ServletException {
	    //  build the criteria string
	StringBuffer sQuery = new StringBuffer("");
	String sQuerynew;
	try {
		CachedDropDownValues cache = new CachedDropDownValues();

		if (psVO.getLastName() != null &&
		    !psVO.getLastName().equals(""))
		    sQuery.append("Last Name").append(
		            " " +
		            cache.getDescForCode("SEARCH_SNDX",
		                                 psVO.getLastNameOperator()).toLowerCase() +
		            " ").append("'" + psVO.getLastName() + "'").append(
		            ", ");

		if (psVO.getFirstName() != null &&
		    !psVO.getFirstName().equals(""))
		    sQuery.append("First Name").append(
		            " " +
		            cache.getDescForCode("SEARCH_SNDX",
		                                 psVO.getFirstNameOperator()).toLowerCase() +
		            " ").append("'" + psVO.getFirstName() + "'").append(
		            ", ");
		
		if (psVO.getLocalID() != null && !psVO.getLocalID().equals(""))
		    sQuery.append("Patient ID = ").append(
		            "'" + psVO.getLocalID() + "'").append(", ");	
		
		if (psVO.getCurrentSex() != null && !psVO.getCurrentSex().equals(""))
		    sQuery.append("Current Sex = ").append(
		            "'" + getSexDesc(psVO.getCurrentSex()) + "'").append(", ");
		
		if (psVO.getBirthTime() != null && !psVO.getBirthTime().equals(""))
		    sQuery.append("Date of Birth = ").append(
		            "'" + psVO.getBirthTime() + "'").append(", ");

		if (psVO.getActType() != null && !psVO.getActType().equals(""))
		    sQuery.append("Event ID Type = ").append(
		            "'" +
		            cache.getDescForCode("PHVS_EVN_SEARCH_ABC", psVO.getActType()) +
		            "'").append(", ");
		
		if (psVO.getActId() != null && !psVO.getActId().equals(""))
		    sQuery.append("Event ID = ").append(
		            "'" + psVO.getActId() + "'").append(", ");
		if (psVO.getEpiLinkId() != null && !psVO.getEpiLinkId().equals(""))
		    sQuery.append("EPI Link ID = ").append(
		            "'" + psVO.getEpiLinkId() + "'").append(", ");
		sQuerynew = sQuery.substring(0, sQuery.length()-2);
	} catch (Exception e) {
		logger.error("Error Exception:", e.getMessage());
		throw new ServletException("Error while buildSearchCriteriaString: "+e.getMessage(),e);
	}
    
	return sQuerynew;

	}   

   private String getIDDescTxt(String id)
   {
      
	   CachedDropDownValues cache = new CachedDropDownValues();
       TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EI_TYPE_PRV");
       map = cache.reverseMap(map); // we can add another method that do not do reverse
       String desc = (String)map.get(id);

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
   
   public ActionForward Cancel(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {		
		String actionForward = "";
		
		try {			    
	          
	        
			actionForward= (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
		//	CTContactForm ctContactForm = (CTContactForm) form;
		//	String contextAction = request.getParameter("ContextAction");			
		//	String noReqForNotifCheck =null;	
			String formCd = NBSConstantUtil.CONTACT_REC;				
			request.setAttribute("formCode", formCd);
			request.setAttribute("ContactTabtoFocus", "ContactTabtoFocus");
			String invPath = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
			if(invPath.equalsIgnoreCase("DSFilePath")){
				actionForward = "ViewFile";
			}else{
			String strPhcLocalUID = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationLocalID);
			request.setAttribute("phcUID", HTMLEncoder.encodeHtml(strPhcLocalUID));	
			}
			//request.setAttribute("ContextAction","InvestigationID");
						
		} catch (Exception e) {
			logger.error("Error Exception:", e.getMessage());
			throw new ServletException("Error while Cancel: "+e.getMessage(),e);
		}
		return (mapping.findForward(actionForward));
	}
   
   private String getSexDesc(String sex)
   {
      String desc="";
	  if(sex.equals("M"))
		  desc = "Male";
	  if(sex.equals("F"))
		  desc = "Female";
	  if(sex.equals("U"))
		  desc = "Unknown";

       return desc;
   }
   
   /**
    * Method used in HomePage PatientSearch Process
    * @param psVO
    */
   private void _populateBirthTime(PatientSearchVO psVO) {
   	String birthTime = psVO.getBirthTime() == null ? "" : psVO.getBirthTime();
   	if(!birthTime.equals("")) {
   		String month = birthTime.substring(0,2); 
   		String day = birthTime.substring(3,5); 
   		String year = birthTime.substring(6);
   		
   		psVO.setBirthTimeMonth(month);
   		psVO.setBirthTimeDay(day);
   		psVO.setBirthTimeYear(year);
   	}
   	
   }
   
}






