package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.justformspdf.pdf.Form;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
//import gov.cdc.nedss.act.observation.vo.ResultedTestVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class LabEditCommonUtil {
	  //For logging
	static final LogUtils logger = new LogUtils(LabEditCommonUtil.class.getName());
	public static final String ACTION_PARAMETER = "method";
	public void editLabLoad(ActionMapping mapping, PageForm pageForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException,ServletException, NEDSSAppException 
	{

	try {
		HttpSession session = request.getSession();
		if (session == null) {
		logger.fatal("error no session");
		
		//return mapping.findForward("login");
		}
		request.getSession().setAttribute(NEDSSConstants.SUBFORM_HASHMAP, null);
		request.getSession().setAttribute(NEDSSConstants.CURRENT_KEY, null);
		NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
		/**@TODO PKS: maybe required for external user intergration
		String userTypeValue = secObj.getTheUserProfile().getTheUser().getUserType();
		// boolean retainCheckbox;
		
		if (userTypeValue != null) {
		boolean reportExteranlUser = userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
		boolean displayFacility =    userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
		request.setAttribute("displayFacility",String.valueOf(displayFacility));
		if(reportExteranlUser == true)
		reportExteranlUser=false;
		request.setAttribute("ReportExteranlUser",String.valueOf(reportExteranlUser));
			try {
				Long userReportingFacilityUid = secObj.getTheUserProfile().getTheUser().
				getReportingFacilityUid();
				if (userReportingFacilityUid != null) {
				Map<Object,Object> results = LabCommonUtil.getOrganization(String.valueOf(
				userReportingFacilityUid.longValue()), session);
				request.setAttribute("reportingFacilityUID",userReportingFacilityUid.toString());
				if (results != null) {
				request.setAttribute("reportingSourceDetails",(String) results.get("result"));
				}
				}
				else {
				request.setAttribute("reportingSourceDetails","There is no Reporting Facility selected.");
				}
				
			}
			catch (Exception e) {
				logger.error("Exception in EditLabReportLoad: " + e.getMessage());
				e.printStackTrace();
			}
		}
		*/
		
		String contextAction = request.getParameter("ContextAction");
		request.setAttribute("currentTask", "EditObservation");
		//Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
		
		Long observationUID = new Long(NBSContext.retrieve(session, "DSObservationUID").toString());
		//Long observationUID = new Long(obsUID);
		Long lab112UID = null;
			try {
			//labResultProxyVO = getLabResultProxyVO(observationUID, session);
			
				PageActProxyVO pageActProxyVO = LabCommonUtil.getLabResultProxyVO(observationUID, session);
				pageForm.getPageClientVO().setOldPageProxyVO(pageActProxyVO);
	
				ObservationVO rootObservationVO = LabCommonUtil.getRootObservationVOByUid(observationUID.toString(), (LabResultProxyVO)pageActProxyVO);
				//ObservationVO obsLAB112 = (ObservationVO) convertProxyToObs112(labResultProxyVO, request);
				PersonVO patientVO =LabCommonUtil.getPatientVO( (LabResultProxyVO)pageActProxyVO,   rootObservationVO);
				
				ClientUtil.setPatientInformation(pageForm.getActionMode(), patientVO, pageForm.getPageClientVO(), request, pageForm.getPageFormCd());
				
				
				lab112UID = rootObservationVO.getTheObservationDT().getObservationUid();
				ArrayList<String> conditionList = ((LabResultProxyVO)pageActProxyVO).getTheConditionsList();
				if (conditionList != null) 
				NBSContext.store(session, "DSConditionList",conditionList);
				if ((rootObservationVO.getTheObservationDT().getProgAreaCd() != null) && !rootObservationVO.getTheObservationDT().getProgAreaCd().isEmpty()) 
				NBSContext.store(session, "DSProgramArea",rootObservationVO.getTheObservationDT().getProgAreaCd()); 	
				CommonAction ca = new CommonAction();
				pageForm.getAttributeMap().put("PDLogic", ca.checkIfSyphilisIsInConditionList(rootObservationVO.getTheObservationDT().getProgAreaCd(), conditionList, NEDSSConstants.LAB_REPORT));

				//ParticipationDT part110 = LabPageUtil.getParticipationDT(rootObservationVO.getTheParticipationDTCollection(), "PSN","PATSBJ","Patient Subject");
				//PersonVO vo = LabCommonUtil.getPatientVO(labResultProxyVO, rootObservationVO);
				
				//obsForm.setPatientRevision(vo);
				//obsForm.setPatient(vo);
				LabResultProxyVO oldLabResultProxyVO =	(LabResultProxyVO)pageActProxyVO.deepCopy();
				
				pageForm.getPageClientVO().setOldPageProxyVO(oldLabResultProxyVO);
				String formCd = pageForm.getPageFormCd();
				logger.debug("viewGenericLoadUtil--> Begin loading View Page for Lab withForm Cd=: " + formCd);
				// Get the metadata
				PageLoadUtil pageLoadUtil = new PageLoadUtil();
				pageLoadUtil.loadQuestions(formCd);
				pageLoadUtil.loadQuestionKeys(formCd);
				Map<Object, Object> questionMap=pageLoadUtil.getQuestionMap();
				
				
				pageActProxyVO.setTheParticipationDTCollection(rootObservationVO.getTheParticipationDTCollection());
				
				pageLoadUtil.loadGenericEntities((BaseForm) pageForm, (LabResultProxyVO)pageActProxyVO, questionMap, request);
	
				
				boolean permissionCheck = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
						NBSOperationLookup.EDIT,rootObservationVO.getTheObservationDT().getProgAreaCd(),
						rootObservationVO.getTheObservationDT().getJurisdictionCd(),rootObservationVO.getTheObservationDT().getSharedInd());
				String electronicInd = rootObservationVO.getTheObservationDT().getElectronicInd();
				
				if (permissionCheck == false || (electronicInd != null && electronicInd.equalsIgnoreCase("Y"))) {
				session.setAttribute("error", "Failed at security checking. OR ELR perminssion");
				throw new ServletException("Failed at security checking. OR ELR perminssion");
				}
				String sCurrTask = NBSContext.getCurrentTask(session);
				
				TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS095", contextAction);
				sCurrTask = NBSContext.getCurrentTask(session);
				
				ArrayList<Object> list = new ArrayList<Object> ();
				list.add("ps187");
				list.add("PS095");
				ErrorMessageHelper.setErrMsgToRequest(request,list);
				pageForm.getAttributeMap().put("Submit","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Submit")+"&method=editGenericSubmit");
				pageForm.getAttributeMap().put("Cancel","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Cancel")+"&method=viewGenericLoad");
				pageForm.getAttributeMap().put("method", "editGenericSubmit");
				pageForm.getAttributeMap().put("ReadOnlyJursdiction", "true");
				pageForm.getAttributeMap().put("ReadOnlyProgramArea", "true");
				pageForm.getAttributeMap().put("readOnlyParticipant", PageConstants.LAB_REPORTING_FACILITY) ;
				LabCommonUtil.setObservationInformationOnForm(observationUID.toString(), pageForm.getPageClientVO(), pageForm, (LabResultProxyVO)pageActProxyVO, request);
				Map<String, Map<Long, Object>> consolidatedMap = LabPageUtil.findResultedTestsObservation( (LabResultProxyVO)pageActProxyVO, rootObservationVO);
				ParticipationDT reportingSourceLabPartDT = LabPageUtil.getParticipationDT(pageActProxyVO.getTheParticipationDTCollection(), "ORG", "AUT", "Author");
				String reportingSourceLabClia  =LabCommonUtil.getCliaValue(reportingSourceLabPartDT.getSubjectEntityUid(), request.getSession());
		    	((LabResultProxyVO)pageActProxyVO).setLabClia(reportingSourceLabClia);
				
				ResultedTestUtil resultedTestUtil = new ResultedTestUtil(reportingSourceLabClia, null, rootObservationVO, (LabResultProxyVO) pageActProxyVO, consolidatedMap);
				resultedTestUtil.setResultedTestObservationForUI(pageForm, request);
				
				String userTypeValue = secObj.getTheUserProfile().getTheUser()
						.getUserType();

				if (userTypeValue != null) {
					boolean reportExteranlUser = userTypeValue
							.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
					if (reportExteranlUser) {
						pageForm.getAttributeMap().put("readOnlyParticipant",
								PageConstants.LAB_REPORTING_FACILITY);
					}
				}
				
				if (rootObservationVO.getTheObservationDT().getElectronicInd() != null
						&& rootObservationVO.getTheObservationDT()
								.getElectronicInd()
								.equals(NEDSSConstants.EXTERNAL_USER_IND))
					pageForm.getAttributeMap().put("readOnlyParticipant",
							PageConstants.LAB_REPORTING_FACILITY);
				
				Map<Long, Object> observationPatientStatusVOMap  =consolidatedMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION);
				Map<Long, Object> observationLabResultVOMap  =((Map<Long, Object>)consolidatedMap.get(PageConstants.LAB_RESULT_COMMENT));
				
				LabCommonUtil.setPatientStatusInformationOnForm(observationPatientStatusVOMap, pageForm.getPageClientVO());
				LabCommonUtil.setLabCommentInformationOnForm(observationLabResultVOMap, pageForm.getPageClientVO());
				
				LabViewCommonUtil.populatePatientSummary(pageForm, patientVO, rootObservationVO, pageForm.getBusinessObjectType(), request);
				pageLoadUtil.setMSelectCBoxAnswersForViewEdit((BaseForm) pageForm,
						pageLoadUtil.updateMapWithQIds(pageActProxyVO.getPageVO().getAnswerDTMap()),
						(ClientVO) pageForm.getPageClientVO());
				
				pageLoadUtil.populateBatchRecords(pageForm, formCd, session, pageActProxyVO.getPageVO()
						.getPageRepeatingAnswerDTMap());
				

			}
			catch (Exception e) {
			logger.error("The Exception thrown in LabEditCommonUtil:" + e.getMessage());
			e.printStackTrace();
			}
			//sets the ProgramArea and Jurisdiction to request from security object
			//super.getNBSSecurityJurisdictionsPA(request, secObj, contextAction);
			request.setAttribute("mode", "edit");
			/*
			*  Added business ObjectUID as a parameter for CreateXSP method in all Edit and View Load
			*  as a part of CDF changes
			*/
			
			/**
			* @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
			loadLabLDFEdit(labResultProxyVO, lab112UID, request);
			createXSP(NEDSSConstants.PATIENT_LDF,obsForm.getPatientRevision().getThePersonDT().getPersonUid(),obsForm.getPatientRevision(), null, request);
			*/
			
			// set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004 
			request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultLabTabOrder()).toString());
			
			//return (mapping.findForward("XSP"));
		} catch (NumberFormatException e) {
			logger.error("LabEditCommonUtil.editLabLoad Exception thrown" +  e); 
			logger.error("LabEditCommonUtil.editLabLoad Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	
	
	 public String editSubmit(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request) 
			 throws ServletException, NEDSSAppException
	 {

		 Long tempId = -1L;
		 LabResultProxyVO labResultProxyVO = null;
		 //Long reportingSourceLabUid=null;
		 try {
		  	String sContextAction = request.getParameter("ContextAction");

			 Long rootObservationUID= new Long(NBSContext.retrieve(request.getSession(), "DSObservationUID").toString());
			 PageForm obsForm =(PageForm)actionForm;
			 //obsForm.getAttributeMap().put(ACTION_PARAMETER, "editGenericSubmit");
				

			 NBSSecurityObj securityObj = (NBSSecurityObj)request.getSession().getAttribute("NBSSecurityObject");
			 labResultProxyVO = obsForm.getPageClientVO().getLabResultProxyVO();
	  		 
	    	  
		  	 String userId = securityObj.getTheUserProfile().getTheUser().getEntryID();
		  	 Long providerUid= securityObj.getTheUserProfile().getTheUser().getProviderUid();
			 LabResultProxyVO oldLabResultProxyVO = (LabResultProxyVO)obsForm.getPageClientVO().getOldPageProxyVO();
			 Collection<ObservationVO>  oldObsCollection  = oldLabResultProxyVO.getTheObservationVOCollection();
			 ObservationVO oldRootObservationVO = findObservationLAB112(oldObsCollection,rootObservationUID);
			 ObservationVO rootObservationVO =LabPageUtil.populateObservationObject(tempId, labResultProxyVO, oldRootObservationVO, obsForm, request);
			 tempId--;
			 labResultProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
			 rootObservationVO.setItDirty(true);
	    	 labResultProxyVO.getTheObservationVOCollection().add(rootObservationVO);
	    	// LabCommonUtil.createDummyResultedTest(labResultProxyVO,rootObservationVO, new Long(tempId--));	    	  
			 setEntitiesForEdit(obsForm.getPageClientVO().getLabResultProxyVO(), obsForm, oldRootObservationVO, userId, request);
			 Collection<Object>  oldParticipation = oldRootObservationVO.getTheParticipationDTCollection();

		    	ParticipationDT part110 = getParticipationDT(oldParticipation,
		    			"PSN","PATSBJ","Patient Subject");
		    	if (part110 != null) {
		    		part110.setItDirty(true);
		    		part110.setItNew(false);
		    		part110.setItDelete(false);
		    		labResultProxyVO.getTheParticipationDTCollection().add(part110);
		    	}
 			//labResultProxyVO = obsForm.getProxy();
			// ResultedTestVO oldResultSetVO = null;
		  	PersonVO patientVO =LabCommonUtil.getPatientVO( oldLabResultProxyVO,   oldRootObservationVO);
		  	//NOTE: This patientVO is OldPatientVO that is updated with user edits
		  	PageCreateHelper.setPatientForEventEdit(obsForm, patientVO, labResultProxyVO, request, userId);
		  	//PersonVO patientVO =LabCommonUtil.getPatientVO( labResultProxyVO,   rootObservationVO);
			String labFormCode = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
			
			/**@PKS REMOVE AFTER WORKING
			Map<String, Map<Long, Object>> oldMap =LabCommonUtil.resultedTestMap(oldObsCollection, rootObservationUID);
			Map<Long, Object> oldResultedtTestMap  = (Map<Long, Object>) oldMap.get(PageConstants.LAB_RESULTED_TEST);
			Map<Long, Object> oldObservationPatientStatusVOMap  = (Map<Long, Object>) oldMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION);
			ResultedTestUtil resultedTestUtil = new ResultedTestUtil(labFormCode, rootObservationVO, labResultProxyVO, oldMap);
			*/
			
			Map<String, Map<Long, Object>> consolidatedMap = LabPageUtil.findResultedTestsObservation( oldLabResultProxyVO, oldRootObservationVO);
			Map<Long, Object> labCommentVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.LAB_COMMENT);
			Map<Long, Object> oldLabResultCommentVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.LAB_RESULT_COMMENT);
			ParticipationDT reportingSourceLabPartDT = LabPageUtil.getParticipationDT(labResultProxyVO.getTheParticipationDTCollection(), "ORG", "AUT", "Author");
			String reportingSourceLabClia  =LabCommonUtil.getCliaValue(reportingSourceLabPartDT.getSubjectEntityUid(), request.getSession());
			
			ResultedTestUtil resultedTestUtil = new ResultedTestUtil(reportingSourceLabClia, labFormCode, rootObservationVO, labResultProxyVO, consolidatedMap);
			Map<Long, Object> oldObservationPatientStatusVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION);
				
			
			
			
			//Map<Long, Object> observationPatientStatusVOMap  =consolidatedMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION);

			tempId =resultedTestUtil.setRepeatingBatchQuestion(tempId, obsForm, request);
		  	if(labResultProxyVO.getThePersonVOCollection()==null) {
		  		labResultProxyVO.setThePersonVOCollection(new ArrayList<Object>());
		  	}
		  	labResultProxyVO.getThePersonVOCollection().add(patientVO);
			// ObservationVO oldLAB214 = findObservationByCode(oldObsCollection,"LAB214");
			// ObservationVO oldTemp214 = findObservationByCode(oldObsCollection,"NI");
			 //Collection<Object>  OldResultedTestVOCollection  = obsForm.getOldResultedTestVOCollection();
			 
			 Long patientUID = (Long)NBSContext.retrieve(request.getSession(), "DSPatientPersonUID");
			 this.setMaterialForEdit( userId, oldLabResultProxyVO, labResultProxyVO, oldParticipation, rootObservationVO);
			
			 ((PageActProxyVO)obsForm.getPageClientVO().getOldPageProxyVO()).setTheParticipationDTCollection(oldParticipation);
			 PageCreateHelper pch = new PageCreateHelper();
			 pch.loadQuestions(labFormCode);
			//process answerDT questions

				Map<Object, Object> oldAnswers = oldLabResultProxyVO.getPageVO().getAnswerDTMap();
				
				Map<Object, Object> newAnswers = pch
						.setPageSpecifcAnswersForCreateEdit((BaseForm) obsForm,
								(ClientVO) obsForm.getPageClientVO(),
								request.getSession(), userId);
				LabCommonUtil.removeCheckboxesAnswerFromAnswerMapforLab(newAnswers);
				labResultProxyVO.getPageVO().setAnswerDTMap(newAnswers);

				PageCreateHelper.updateNbsAnswersForDirty(obsForm, labResultProxyVO);
				pch.setRepeatingQuestionsBatch(obsForm,labResultProxyVO,obsForm.getPageFormCd(),userId, providerUid);
			 
			 
			 
			 tempId=LabPageUtil.setPatientStatusVO(rootObservationVO, obsForm, tempId, labResultProxyVO, oldObservationPatientStatusVOMap);
			 tempId =LabPageUtil.setLabCommentVO(rootObservationVO, obsForm, tempId, labResultProxyVO, oldLabResultCommentVOMap);
			 ((LabResultProxyVO)labResultProxyVO).setAssociatedNotificationInd(oldLabResultProxyVO.associatedNotificationInd);

			 //ObservationVO newLAB214 = new ObservationVO();
			
			  
			 LabCommonUtil labCommonUtil = new LabCommonUtil();
			 labCommonUtil.setLAB112(rootObservationVO, reportingSourceLabClia);

			 labResultProxyVO.setLabClia(reportingSourceLabClia);
			 labResultProxyVO.setManualLab(true);
			 labResultProxyVO.setItDirty(true);
			 if (labResultProxyVO != null)
		        {
		            try
		            {
		        	    String sCurrTask = NBSContext.getCurrentTask(request.getSession());
		        	    Long uidMRP = null;

		        	    Map<?,?> mprUid = LabCommonUtil.sendProxyToEJB(labResultProxyVO, request.getSession(),sCurrTask);
		        	    uidMRP = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
		  	          Long obsUid = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
		  	          NBSContext.store(request.getSession(),"DSObservationUID",obsUid);
		  	          
		            }
		            catch (NEDSSAppConcurrentDataException e)
		            {
		            	request.getSession().setAttribute("error", "");
		                logger.error("Data error in Edit Lab Report Submit: "+e.getMessage());
		                return "dataerror";
		            }
		            catch (javax.ejb.EJBException ex)
		            {
		            	request.getSession().setAttribute("error", "");
		                logger.error("EJB error in Edit Lab Report Submit: "+ex.getMessage());
		                return "error";
		            }
		            catch (Exception ex)
		            {
		                request.getSession().setAttribute("error", "");
		                logger.error("General error in Edit Lab Report Submit: "+ex.getMessage());
		                return "error";
		            }
		        }
			 
				
			 return sContextAction;
		 }catch (Exception e) {
			logger.error("LabEditCommonUtil.editSubmit Exception thrown" +  e); 
			logger.error("LabEditCommonUtil.editSubmit Exception " +  e.getMessage()); 
			 e.printStackTrace();
			 throw new NEDSSAppException(e.getMessage(), e);
		 }   
	 }

	 private static ObservationVO findObservationByCode(Collection<ObservationVO>  coll, String strCode)
	 {
	   if (coll == null)
	     return null;

	  Iterator<ObservationVO>  itor = coll.iterator();

	   while (itor.hasNext()) {
	     ObservationVO obsVO = (ObservationVO) itor.next();
	     ObservationDT obsDT = obsVO.getTheObservationDT();

	     if (obsDT == null)
	       continue;

	     if (obsDT.getCd() == null) {
	       continue;
	     }
	     if (obsDT.getCd().trim().equalsIgnoreCase(strCode.trim()))
	       return obsVO; // found it!
	   }

	   // didn't find one
	   return null;
	 }
	 
	 
	 private ObservationVO findObservationLAB112(Collection<ObservationVO>  coll, Long oldObsUID)
	  {
	    if (coll == null)
	      return null;

	   Iterator<ObservationVO>  itor = coll.iterator();

	    while (itor.hasNext()) {
	      ObservationVO obsVO = (ObservationVO) itor.next();
	      ObservationDT obsDT = obsVO.getTheObservationDT();

	      if (obsDT == null)
	        continue;


	      if (obsDT.getObservationUid().equals(oldObsUID))
	        return obsVO; // found it!
	    }

	    // didn't find one
	    return null;
	  }
	 
	 private void   setMaterialForEdit( String userId, LabResultProxyVO oldLabResultProxyVO, LabResultProxyVO labResultProxyVO, Collection<Object> participationColl, 
			 ObservationVO rootObservationVO) throws NEDSSAppException{
		try {
			if(oldLabResultProxyVO.getTheMaterialVOCollection() != null){
				Iterator<Object>  itor = oldLabResultProxyVO.getTheMaterialVOCollection().iterator();
				while (itor.hasNext())
				{
					MaterialVO materialVO = (MaterialVO)itor.next();
					ParticipationDT participationDT104 = LabCommonUtil.genericParticipationCreate(userId, "OBS",rootObservationVO.getTheObservationDT().getObservationUid(),
								rootObservationVO.getTheObservationDT().getEffectiveFromTime(),"ACTIVE",
                                "A","MAT",materialVO.getTheMaterialDT().getMaterialUid(),"SPC",
                                "Specimen");
					labResultProxyVO.getTheParticipationDTCollection().add(participationDT104);
				}
			} // End of while loop
		} catch (Exception e) {
			logger.error("LabEditCommonUtil.setMaterialForEdit Exception thrown" +  e); 
			logger.error("LabEditCommonUtil.setMaterialForEdit Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	 }
	 private String getDescTxt(String srt,String code){
		 String descTxt=null;
		 try{
			 CachedDropDownValues cdv = new CachedDropDownValues();
			 descTxt= cdv.getDescForCode(srt,code);
		 }catch(Exception e){
			 //logger.debug("Error found");
		 }
		 return descTxt;
	 }
	 
	/**TODO PKS : commented out form patient integration for edit 
	private void setPersonForEdit(PersonVO patientVO, PersonVO oldPatientVO,  Collection<Object>  personColl, HttpServletRequest request){
			NEDSSVOUtils voUtils = NEDSSVOUtils.getInstance();
			boolean isRevisionChanged = false;
			try {
			
				isRevisionChanged = voUtils.hasVOFieldChanged(patientVO, oldPatientVO);
				//logger.debug("isRevisionChanged  = " + isRevisionChanged);
			}
			catch (Exception e) {
				//logger.debug(" !!!!!!!!! NEDSSVOUtils error with Inv EditSubmit" + e.toString());
				e.printStackTrace();
			}
			if (isRevisionChanged) {
				PersonUtil.setPatientForEventEdit(patientVO, request);
				patientVO.setItDirty(true);
				patientVO.setItNew(false);
				patientVO.getThePersonDT().setItDirty(true);
				patientVO.getThePersonDT().setItNew(false);
				personColl.add(patientVO);
			}
	
	}
	*/
	 
	
	 private void setObsValueCodedToDefaultValues(Collection<ObservationVO>  observations) throws NEDSSAppException
	 {

	    try {
			Iterator<ObservationVO>  collItor = observations.iterator();

			 if (collItor == null)

			     return;

			 String _blank = "";

			 while (collItor.hasNext())
			 {

			     ObservationVO obsVO = (ObservationVO)collItor.next();
			     Collection<Object>  obsValDTs = obsVO.getTheObsValueCodedDTCollection();

			     if (obsValDTs == null)

			         continue;

			    Iterator<Object>  dtItor = obsValDTs.iterator();

			     while (dtItor.hasNext())
			     {

			         ObsValueCodedDT dt = (ObsValueCodedDT)dtItor.next();

			         if ((dt.getCode() == null) || (dt.getCode().trim().length() == 0))
			             dt.setCode("NI");
			     }
			 }
		} catch (Exception e) {
			logger.error("LabEditCommonUtil.setObsValueCodedToDefaultValues Exception thrown" +  e); 
			logger.error("LabEditCommonUtil.setObsValueCodedToDefaultValues Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	 }

	    public static void setEntitiesForEdit(PageActProxyVO proxyVO, PageForm form, ObservationVO oldRootObservationVO,
	            String userId, HttpServletRequest request) throws NEDSSAppException
	    {
	        try {

				//String programArea = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
				Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();

				PropertyUtil properties = PropertyUtil.getInstance();
				// ///////////////////////////////////////////////////////////////////////////////
				// Iterate through the Case Participation Types to see if they are
				// present
				// and if they are put them in the attribute map
				// ///////////////////////////////////////////////////////////////////////////////
				 
				TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
				Iterator parTypeIt = participationTypeCaseMap.values().iterator();
				while (parTypeIt.hasNext())
				{
				    ParticipationTypeVO parTypeVO = (ParticipationTypeVO) parTypeIt.next();
				    String entityUid = (form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null || form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid").equals("")) ? null
				            : (String) form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid");

				    createOrDeleteParticipation(entityUid, form, proxyVO, oldRootObservationVO, parTypeVO.getTypeCd(), parTypeVO.getSubjectClassCd(), userId, providerUid);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.fatal("Exception occured in LabEditCommonUtil.setEntitiesForEdit: userId: " + userId + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
	        	throw new NEDSSAppException(e.getMessage(), e);
			}

	    }

	    public static void createOrDeleteParticipation(String newEntityUid, PageForm form, PageActProxyVO proxyVO, ObservationVO oldRootObservationVO, String typeCd, String classCd, String userId, Long providerUid) throws NEDSSAppException
	    {
	        try {
				logger.debug(" newEntityUid = " + newEntityUid + " old");
				if(newEntityUid != null && newEntityUid.indexOf("|") == -1) {
					newEntityUid = newEntityUid + "|1";
				}
				String uid = null;
				String versionCtrlNbr = "1";
				if (newEntityUid != null) {
					uid = PageCreateHelper.splitUid(newEntityUid);
					versionCtrlNbr = PageCreateHelper.splitVerCtrlNbr(newEntityUid);
				}

				//PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
				//sString programArea = rootObservationVO.getTheObservationDT().getProgAreaCd();
				Collection<Object>  oldParCollection  = oldRootObservationVO.getTheParticipationDTCollection();
				Long publicHealthCaseUID = oldRootObservationVO.getTheObservationDT().getObservationUid();
				Collection<Object>  newParCollection  = new ArrayList<Object> ();
		
				ParticipationDT oldParticipationDT = PageCreateHelper.getParticipation(typeCd, classCd, oldParCollection);
	      
	        
				if(oldParticipationDT == null)
				{
				    if(uid != null && !uid.trim().equals(""))
				    {
				        logger.info("old par in null and create new only: " + uid);
				        newParCollection.add(PageCreateHelper.createParticipation(oldRootObservationVO.getTheObservationDT().getAddUserId(),
				        		oldRootObservationVO.getTheObservationDT().getAddTime(),userId,publicHealthCaseUID, uid, classCd, typeCd, NEDSSConstants.OBS));
				    }
				} else {
					Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
				    if(uid != null)
				    {
				        logger.info("participation changed newEntityUid: " + uid + " for typeCd " + typeCd);
				        newParCollection.add(PageCreateHelper.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
				        newParCollection.add(PageCreateHelper.createParticipation(oldRootObservationVO.getTheObservationDT().getAddUserId(),
				        		oldRootObservationVO.getTheObservationDT().getAddTime(),
				        		userId, publicHealthCaseUID, uid, classCd, typeCd, NEDSSConstants.OBS));

				    }
				    else if((uid == null || (uid != null && uid.trim().equals("")))) 
				    {
				        logger.warn("no EntityUid (is cleared) or not set for typeCd: " + typeCd);
				        newParCollection.add(PageCreateHelper.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
				    }
				}
				if(newParCollection.size() > 0) {
					if(proxyVO.getTheParticipationDTCollection()==null) {
						proxyVO.setTheParticipationDTCollection(newParCollection);
					}else
					proxyVO.getTheParticipationDTCollection().addAll(newParCollection);
				}
			} catch (NumberFormatException e) {
				logger.error(e);
				logger.error("LabEditCommonUtil.createOrDeleteParticipation NumberFormatException thrown, "
						+ e);
				throw new NEDSSAppException(e.toString(), e);
			} catch (NEDSSAppException e) {
				logger.error(e);
				logger.error("LabEditCommonUtil.createOrDeleteParticipation NEDSSAppException thrown, "
						+ e);
				throw new NEDSSAppException(e.toString(),e);
			}
	    }
	    
	    public ParticipationDT getParticipationDT(Collection<Object> partici, String subject, String type_cd,
	            String type_desc) throws NEDSSAppException
	    {

	        try {
				if (partici != null)
				{
				    Iterator<Object> it = partici.iterator();
				    while (it.hasNext())
				    {
				        ParticipationDT part = (ParticipationDT) it.next();
				        // System.out.println("outside" + part.getTypeCd());
				        if (part.getTypeCd().equalsIgnoreCase(type_cd) && part.getSubjectClassCd().equalsIgnoreCase(subject)
				                && part.getTypeDescTxt().equalsIgnoreCase(type_desc))
				        {
				            // System.out.println("Inside");
				            return part;
				        }
				    }
				}
				return null;
			} catch (Exception e) {
				logger.error("LabEditCommonUtil.getParticipationDT Exception thrown" +  e); 
				logger.error("LabEditCommonUtil.getParticipationDT Exception " +  e.getMessage()); 
				throw new NEDSSAppException(e.getMessage(), e);
			}
	    }
	    
	    private void setLAB112(ObservationVO rootObservationVO){
	        ObservationDT obsDT = rootObservationVO.getTheObservationDT();
	        if(obsDT.getCd()==null || obsDT.getCd().equals(""))
	        {
	          PropertyUtil propertyUtil = PropertyUtil.getInstance();
	          String lab112Cd =  propertyUtil.getLab112Cd();
	          obsDT.setCd(lab112Cd);
	          //obsDT.setCd(newLAB112.getTheObservationDT().getTxt());
	         obsDT.setTxt("");
	        }

	   }
   
}
