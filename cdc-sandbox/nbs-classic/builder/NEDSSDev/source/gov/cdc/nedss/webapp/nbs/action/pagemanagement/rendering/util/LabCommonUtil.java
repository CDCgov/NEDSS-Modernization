package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nnd.dt.CodeLookupDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSObjectStore;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ObservationUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.observation.common.LabReportFieldMappingBuilder;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;


/** 
 * This is a utility class to create manual labs. This code is based on legacy XSP code modified for 
 * Page Builder Functionality.
 * @author Pradeep Kumar Sharma
 *
 */

public class LabCommonUtil {

  //For logging
  static final LogUtils logger = new LogUtils(LabCommonUtil.class.getName());
  
  public LabCommonUtil() {
  } 


  public ActionForward CreataLab(String contextAction, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NEDSSAppException
  {
	  
	  HttpSession session = request.getSession(false);
	  if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
      {
        NBSContext.store(session, "DSFileTab", "3");
        return (mapping.findForward(contextAction));
      }
	  //Long organizationReportingUid = null;
	  try
	    {
		
			Long tempID=-1L;
		    PageForm obsForm =(PageForm)form;
		    LabResultProxyVO labResultProxyVO = obsForm.getPageClientVO().getLabResultProxyVO();
	  	  	ObservationVO rootObservationVO =LabPageUtil.populateObservationObject(tempID, labResultProxyVO, null, obsForm, request); 
	  	  	tempID--;	    
		    if(rootObservationVO.getTheObservationDT().getCd()==null || rootObservationVO.getTheObservationDT().getCd().trim().length()==0) {
		    	rootObservationVO.getTheObservationDT().setCd("LAB112");
	    }
	    rootObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
	    obsForm.getPageClientVO().setOldPageProxyVO(null);
	    obsForm.getPageClientVO().setOldResultedTestVOCollection(null);
	    
	    
	    //LabResultProxyVO labResultProxyVO;
	    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	    String sCurrTask = NBSContext.getCurrentTask(session);
	    
	      if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
	      {
	        NBSContext.store(session, "DSFileTab", "3");
	        return (mapping.findForward(contextAction));
	      }
	      else
	      {
	    	 // organizationReportingUid = LabPageUtil.getParticipationUidFromClientVO("NBS_LAB365", obsForm, request);
	    	 // labResultProxyVO = obsForm.getPageClientVO().getLabResultProxyVO();
	          String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

	    	  LabPageUtil.setParticipationUidFromClientVO(labResultProxyVO, tempID, userId,rootObservationVO.getTheObservationDT().getObservationUid(), 
	    			  	rootObservationVO.getTheObservationDT().getProgAreaCd(),obsForm, request);
	    	  
			  ParticipationDT reportingSourceLabPartDT = LabPageUtil.getParticipationDT(labResultProxyVO.getTheParticipationDTCollection(), "ORG", "AUT", "Author");
			  String reportingSourceLabClia  =getCliaValue(reportingSourceLabPartDT.getSubjectEntityUid(), request.getSession());
	    	  
	    	    
			    
	    	  //String markAsReviewReason = (String) request.getParameter("markAsReviewReason");
	    	  obsForm.getPageClientVO().getLabResultProxyVO().setLabClia(reportingSourceLabClia);
	    	  obsForm.getPageClientVO().getLabResultProxyVO().setManualLab(true);
	    	  //labResultProxyVO = obsForm.getPageClientVO().getLabResultProxyVO();
	    	  labResultProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
	    	  labResultProxyVO.getTheObservationVOCollection().add(rootObservationVO);
	    	  
	  	    	  	    
	  	    
	          //mainObservation = findObservationByCode(labResultProxy.getTheObservationVOCollection(), "LAB112");
	          String processingDecision = rootObservationVO.getTheObservationDT().getProcessingDecisionCd();
	          rootObservationVO.getTheObservationDT().setProcessingDecisionCd(null);
	          
	        if(sCurrTask != null && sCurrTask.equals("AddObservationLab2"))
	        {
	
	           String dSInvestigationProgramArea = rootObservationVO.getTheObservationDT().getProgAreaCd();
	           String dSInvestigationJurisdiction = rootObservationVO.getTheObservationDT().getJurisdictionCd();
	
	           NBSContext.store(session, NBSConstantUtil.DSJurisdiction, dSInvestigationJurisdiction);
	           NBSContext.store(session, "DSInvestigationJurisdiction", dSInvestigationJurisdiction);
	           NBSContext.store(session,NBSConstantUtil.DSInvestigationProgramArea, dSInvestigationProgramArea);
	           NBSContext.store(session, NBSConstantUtil.DSProgramArea, dSInvestigationProgramArea);
	           NBSContext.store(session, "DSObservationTypeCd",
	                     NEDSSConstants.LABRESULT_CODE);
	           request.setAttribute("programAreaCd1",dSInvestigationProgramArea);
	           request.setAttribute("LabReport","LabReport");
	        }
	        
	        labResultProxyVO = this.createHandler(mapping, obsForm, reportingSourceLabClia, rootObservationVO,  tempID,  request,
	                                              nbsSecurityObj,contextAction);
	
	
	        Long uidMRP = null;
	        PersonVO personVO = (PersonVO)obsForm.getAttributeMap().get("Patient");
	        //PageForm pageForm = (PageForm)form;
	        //PersonVO personVO = aform.getPatient();
	        // set up the ldf collection
	        try
	        {
	           Map<?,?> mprUid = (Map<?,?>)sendProxyToEJB(labResultProxyVO, session, sCurrTask);
	          uidMRP = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
	          Long obsUid = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
	          NBSContext.store(session,"DSObservationUID",obsUid);
	          logger.debug("LabCommonUtil.CreataLab uidMRP is "+ uidMRP);
	          
	          if(personVO != null){
	                personVO.getThePersonDT().setPersonParentUid(uidMRP);
	                //this.findMasterPatientRecord(personVO,uidMRP,session,nbsSecurityObj);
	                personVO.getThePersonDT().setLocalId(mprUid.get(NEDSSConstants.SETLAB_RETURN_MPR_LOCAL).toString());

	              }
	          // for SubmitAndCreateInvestigation need to prepopulate reportingSource and Ordering Physician
	          if(contextAction.equals("SubmitAndCreateInvestigation"))
	          {
	            this.defaultValuesForCreateInvestigation(session, obsUid, processingDecision);
	          }
	
	        }
	        catch (Exception e) {
	          labResultProxyVO = null;
	          /*@TODO: reset for may not be required
	          labResultProxyVO.reset();
	          obsForm.reset();*/
	          obsForm = null;
	          logger.error("Exception in Lab Report Submit: " + e.getMessage());
	          e.printStackTrace();
	          session.setAttribute("error", "");
	          throw new ServletException(e);
	        }
	        // VOTester.createReport(labResultProxyVO, "labResultProxyVO from backend");
	        	
				String retainPatientValue = PageCreateHelper.getVal(obsForm
						.getPageClientVO().getAnswerMap()
						.get(PageConstants.RETAIN_PATIENT));
				if (retainPatientValue != null
						&& retainPatientValue.trim().length() > 0
						&& retainPatientValue.equalsIgnoreCase("1"))
					NBSContext.store(session, "DSPatientPersonVO", personVO);
				
	            String retainReportingValue = PageCreateHelper.getVal(obsForm
						.getPageClientVO().getAnswerMap()
						.get(PageConstants.RETAIN_REPORTING_FACILITY));

	                if (retainReportingValue != null &&
	                    retainReportingValue.equalsIgnoreCase("1")) {
	                  NBSContext.store(session, "DSLabInformation", reportingSourceLabPartDT.getSubjectEntityUid());
	                }
	      }
	    }
	    catch(NEDSSAppConcurrentDataException e)
	    {
	      logger.error("LabCommonUtil.CreataLab NEDSSAppConcurrentDataException thrown" +  e); 
			logger.error("LabCommonUtil.CreataLab NEDSSAppConcurrentDataException " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
	    }
	    catch (javax.ejb.EJBException e)
	    {
	        logger.error("LabCommonUtil.CreataLab EJBException thrown" +  e); 
			logger.error("LabCommonUtil.CreataLab EJBException " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
	    }
	    catch (Exception e)
	    {
	    	logger.error("LabCommonUtil.CreataLab Exception thrown" +  e); 
			logger.error("LabCommonUtil.CreataLab Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);	        
		}
	
	
   return mapping.findForward(contextAction);
  }

  private LabResultProxyVO createHandler(ActionMapping mapping, PageForm obsForm,String reportingSourceLabClia, ObservationVO rootObservationVO,  Long tempID,  
		  HttpServletRequest request, NBSSecurityObj nbsSecurityObj,String contextAction)
	                        throws NEDSSAppConcurrentDataException, ServletException, NEDSSAppException
	{
	
	
	      try {
			String userTypeValue = nbsSecurityObj.getTheUserProfile().getTheUser().getUserType();
			  boolean reportExteranlUser= false;
			  if (userTypeValue != null) {
			     reportExteranlUser = userTypeValue.equalsIgnoreCase(
			         NEDSSConstants.SEC_USERTYPE_EXTERNAL);
			  }

			  //int obsUid =-1;
			//persist PatientRevision from Answers
			Long patientUid = null;
			String personParentUid = null;
			if (contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
				/*personParentUid = PageCreateHelper.getVal(obsForm
						.getPageClientVO().getAnswerMap()
						.get(PageConstants.PERSON_PARENT_UID));*/
				personParentUid = (String)obsForm.getAttributeMap().get(PageConstants.LAB_PATIENT + "Uid");
				if (personParentUid != null
						&& personParentUid.trim().length() > 0){
					personParentUid = personParentUid.substring(0, personParentUid.indexOf("|"));
					patientUid = new Long(personParentUid);
				}
				else
					patientUid = null;
			} else
				patientUid = (Long) NBSContext.retrieve(request.getSession(),
						NBSConstantUtil.DSPersonSummary);
			String userId= nbsSecurityObj.getEntryID();
			Long providerUid= nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
			String pageFormCd = obsForm.getPageFormCd();
			obsForm.getPageClientVO().getLabResultProxyVO().setPageProxyTypeCd(NEDSSConstants.OBS_ADD_REASON_CD);
			PersonVO patient = PageCreateHelper.setPatientForEventCreate(patientUid, tempID--, obsForm.getPageClientVO().getLabResultProxyVO(), obsForm, request, userId);
			ParticipationDT patientPartDT = LabPageUtil.getParticipationDT(obsForm.getPageClientVO().getLabResultProxyVO().getTheParticipationDTCollection(), "PSN", "PATSBJ", "Patient subject");
			if(patientPartDT!=null)
				patientPartDT.setSubjectEntityUid(patient.getThePersonDT().getPersonUid());
			else {
				//This will cover the scenario where lab is created from data entry and patient is not searched, instead patient information is added manually
				PageCreateHelper
						._setEntitiesForCreate(obsForm.getPageClientVO().getLabResultProxyVO(),obsForm, userId, String.valueOf(patient.getThePersonDT().getPersonUid().longValue()),
								"PATSBJ", "PSN", rootObservationVO
										.getTheObservationDT()
										.getObservationUid(), rootObservationVO
										.getTheObservationDT().getProgAreaCd(),
										
								"OBS", request);
			}
     		  // this is from entity serach OR retain patient
			  if(personParentUid != null && !personParentUid.equals("")){
			    patient.getThePersonDT().setPersonParentUid(new Long(personParentUid));
			  }
			
			  //electrinicInd is always set to "N" unless it is ELR as per defect 9169
			  patient.getThePersonDT().setElectronicInd("N");
			  
			  LabResultProxyVO labResultProxyVO = obsForm.getPageClientVO().getLabResultProxyVO();
			  ArrayList<ObservationVO> obsCollection  = new ArrayList<ObservationVO> ();
			  ArrayList<Object> roleCollection  = new ArrayList<Object> ();
			  //ArrayList<Object> participationColl = new ArrayList<Object> ();
			  ArrayList<Object> actRelationColl = new ArrayList<Object> ();
			  ArrayList<Object> actIdColl = new ArrayList<Object> ();
			  ArrayList<Object> personsColl = new ArrayList<Object> ();
			  Collection<Object> materialColl = labResultProxyVO.getTheMaterialVOCollection();
			  personsColl.add(patient);
			
			 
			  
			  tempID = this.setObservationForCreate(labResultProxyVO.getTheObservationVOCollection(), 
			                                        obsCollection,tempID,reportExteranlUser,rootObservationVO,
			                                        request,nbsSecurityObj, reportingSourceLabClia);
			  
			  
			  if(rootObservationVO.getTheActIdDTCollection()!=null)
				  this.setActIdForCreate(reportingSourceLabClia, rootObservationVO,actIdColl, request);
			
			  if (contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
				obsForm.getAttributeMap().put("Patient", patient);
			    RoleDT roleDT115 = this.genericRoleCreate("P", "patient", "ACTIVE", null, null, null,
			                                              "A", "PAT",
			                                              patient.getThePersonDT().
			                                              getPersonUid());
			    roleDT115.setRoleSeq(new Long(0));
			    roleCollection.add(roleDT115);
			  }
			
			  if(materialColl != null){
			   Iterator<Object>  materialIt = materialColl.iterator();
			    while (materialIt.hasNext()) {
			      MaterialVO materialVO = (MaterialVO) materialIt.next();
			      RoleDT roleDT109 = this.genericRoleCreate("NI","No Information Given",
			                                                "ACTIVE","PSN",patient.getThePersonDT().getPersonUid(),"PAT",
			                                                "A","SPEC",materialVO.getTheMaterialDT().getMaterialUid());
			      roleDT109.setRoleSeq(new Long(0));
			      roleCollection.add(roleDT109);
			    }
			
			  }
			 ObservationDT observationDTLab112 = rootObservationVO.getTheObservationDT();
			ParticipationDT participationDT104 =null;
			  if(materialColl != null ){
			   Iterator<Object>  materialIt = materialColl.iterator();
			    while (materialIt.hasNext()) {
			      MaterialVO materialVO = (MaterialVO) materialIt.next();
			      participationDT104 = genericParticipationCreate(userId, "OBS",observationDTLab112.getObservationUid(),
			                                                 observationDTLab112.getEffectiveFromTime(),"ACTIVE",
			                                                 "A","MAT",materialVO.getTheMaterialDT().getMaterialUid(),"SPC",
			                                                 "Specimen");
			      if(participationDT104!=null) {
			    	  labResultProxyVO.getTheParticipationDTCollection().add(participationDT104);
					  
			      }
			    }
			  }
			  labResultProxyVO.setThePersonVOCollection(personsColl);
			  labResultProxyVO.setTheObservationVOCollection(obsCollection);
			  labResultProxyVO.setTheRoleDTCollection(roleCollection);
			  labResultProxyVO.setTheActIdDTCollection(actIdColl);
			  labResultProxyVO.setTheActRelationshipDTCollection(actRelationColl);
			  Map<String, Map<Long, Object>> consolidatedMap = LabPageUtil.findResultedTestsObservation( labResultProxyVO, rootObservationVO);
			  Map<Long, Object> oldObservationPatientStatusVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION);
			 // Map<Long, Object> labCommentVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.LAB_COMMENT);
			  Map<Long, Object> oldLabResultCommentVOMap  = (Map<Long, Object>) consolidatedMap.get(PageConstants.LAB_RESULT_COMMENT);
				
			  tempID =LabPageUtil.setPatientStatusVO(rootObservationVO, obsForm, tempID, labResultProxyVO, oldObservationPatientStatusVOMap);
			  tempID =LabPageUtil.setLabCommentVO(rootObservationVO, obsForm, tempID, labResultProxyVO, oldLabResultCommentVOMap);
			  
			  String labFormCode = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
			  ResultedTestUtil ResultedTestUtil = new ResultedTestUtil(reportingSourceLabClia, labFormCode, rootObservationVO, labResultProxyVO, consolidatedMap);
			  
			  ResultedTestUtil.setRepeatingBatchQuestion(tempID,  obsForm, request);
			  	
			  labResultProxyVO.setItNew(true);
			  labResultProxyVO.setItDirty(false);
			  this.setLAB112(rootObservationVO, reportingSourceLabClia);
			  PageCreateHelper pch = new PageCreateHelper();
			  pch.loadQuestions(labFormCode);

              // persist dynamic answers
              Map<Object, Object> newAnswers = pch.setPageSpecifcAnswersForCreateEdit(
                      (BaseForm) obsForm, (ClientVO) obsForm.getPageClientVO(), request.getSession(), userId);
              removeCheckboxesAnswerFromAnswerMapforLab(newAnswers);
              labResultProxyVO.getPageVO().setAnswerDTMap(newAnswers);
			  //PageCreateHelper.setRepeatingQuestionsBatch(obsForm, pageFormCd, userId, providerUid);
              pch.setRepeatingQuestionsBatch(obsForm, labResultProxyVO, pageFormCd, userId, providerUid);
			  tempID = -1L;
			  
			  return labResultProxyVO;
		} catch (NumberFormatException e) {
			logger.error("LabCommonUtil.createHandler NumberFormatException error thrown" +  e); 
			logger.error("LabCommonUtil.createHandler NumberFormatException error " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}catch (Exception e) {
			logger.error("LabCommonUtil.createHandler Exception thrown" +  e); 
			logger.error("LabCommonUtil.createHandler Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	
	
	public static ParticipationDT genericParticipationCreate(String userId,String actClassCd,
	    Long actUid, Timestamp fromTime, String recordStatusCd, String statusCd,
	    String subjectClassCd, Long subjectEntityUid, String typeCd, String typeDescTxt)
	{
		ParticipationDT participationDT = new ParticipationDT();
		participationDT.setActClassCd(actClassCd);
		participationDT.setActUid(actUid);
		participationDT.setFromTime(fromTime);
		participationDT.setRecordStatusCd(recordStatusCd);
		participationDT.setStatusCd(statusCd);
		participationDT.setAddUserId(Long.valueOf(userId));
		participationDT.setAddTime(new Timestamp(new Date().getTime()));
		participationDT.setLastChgUserId(Long.valueOf(userId));
		participationDT.setLastChgTime(new Timestamp(new Date().getTime()));
		participationDT.setStatusTime(new Timestamp(new Date().getTime()));
		participationDT.setSubjectClassCd(subjectClassCd);
		participationDT.setSubjectEntityUid(subjectEntityUid);
		participationDT.setTypeCd(typeCd);
		participationDT.setTypeDescTxt(typeDescTxt);
		participationDT.setItNew(true);
	  return participationDT;
	}
	
	private RoleDT genericRoleCreate(String cd,
	    String cdDescTxt, String recordStatusCd, String scopingClassCd, Long entityUid,
	    String scopingRoleCd, String statusCd, String subjectClassCd, Long subjectEntityUid)
	{
	  RoleDT roleDT = new RoleDT();
	  roleDT.setCd(cd);
	  roleDT.setCdDescTxt(cdDescTxt);
	  roleDT.setRecordStatusCd(recordStatusCd);
	  roleDT.setScopingClassCd(scopingClassCd);
	  roleDT.setScopingEntityUid(entityUid);
	  roleDT.setScopingRoleCd(scopingRoleCd);
	  roleDT.setStatusCd(statusCd);
	  roleDT.setSubjectClassCd(subjectClassCd);
	  roleDT.setSubjectEntityUid(subjectEntityUid);
	  roleDT.setItNew(true);
	
	
	  return roleDT;
	}
	
	private String getSystemDescTxt(String systemValue, HttpSession session) throws
	NEDSSAppConcurrentDataException, java.rmi.RemoteException,
	javax.ejb.EJBException, Exception
	{

		try {
			MainSessionCommand msCommand = null;
			String systemDescTxt = null;
			//try {
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = null;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = null;

			sMethod = "getLaboratorySystemDescTxt";
			Object[] oParams ={systemValue};
			msCommand = holder.getMainSessionCommand(session);
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			for (int i = 0; i < resultUIDArr.size(); i++)
			{
				logger.info("Result " + i + " is: " + resultUIDArr.get(i));
			}

			if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
			{
				/*System.out.println("calling getLaboratorySystemDescTxt worked!!! getLaboratorySystemDescTxt = " +
		                 resultUIDArr.get(0)); */


				systemDescTxt = (String) resultUIDArr.get(0);

				return systemDescTxt;
			}

			return null;
		} catch (Exception e) {
			logger.error("LabCommonUtil.getSystemDescTxt Exception thrown" +  e); 
			logger.error("LabCommonUtil.getSystemDescTxt Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	} //sendProxyToEJB
	
	
	public static Map<?,?> sendProxyToEJB(LabResultProxyVO proxy, HttpSession session, String sCurrTask) throws
	    NEDSSAppConcurrentDataException, java.rmi.RemoteException,
	    javax.ejb.EJBException, Exception
	{
	
	  try {
		MainSessionCommand msCommand = null;
		
		  //try {
		  String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		  String sMethod = null;
		  Long obsUid = null;
		  MainSessionHolder holder = new MainSessionHolder();
		  ArrayList<?> resultUIDArr = null;
		
		
		
		  msCommand = holder.getMainSessionCommand(session);
		  if(sCurrTask != null && (sCurrTask.equalsIgnoreCase("AddObservationLab4")
		                           || sCurrTask.equalsIgnoreCase("AddObservationLab1")
		                           || sCurrTask.equalsIgnoreCase("AddObservationLab3")))
		  {
		    //AddObservationLab4 is from manage observation to Add morb, need to assoc with investigation
		    String sInvestigationUid = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
		    Long investigationUid = new Long(sInvestigationUid);
		    sMethod = "setLabResultProxyWithAutoAssoc";
		    Object[] oParams = {proxy, investigationUid};
		    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		  }
		  else
		  {
		    sMethod = "setLabResultProxy";
		    Object[] oParams = {proxy};
		    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		  }
		
		
		
		  for (int i = 0; i < resultUIDArr.size(); i++)
		  {
		    logger.info("Result " + i + " is: " + resultUIDArr.get(i));
		  }
		
		  if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
		  {
		    logger.debug("store observation worked!!! observationUID = " +
		                 resultUIDArr.get(0));
		
		
		    Map<?,?> result = (Map<?,?>) resultUIDArr.get(0);
		
		    //obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
		    obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
		    logger.debug( "LabCommonUtil.sendProxyToEJB obsUid is:"+obsUid);
		   return result;
		
		  }
		
		  return null;
	} catch (Exception e) {
		logger.error("LabCommonUtil.sendProxyToEJB Exception thrown" +  e); 
		logger.error("LabCommonUtil.sendProxyToEJB Exception " +  e.getMessage()); 
		throw new NEDSSAppException(e.getMessage(), e);
	}
	} //sendProxyToEJB
	
	private void defaultValuesForCreateInvestigation(HttpSession session, Long labUid, String processingDecision) throws NEDSSAppException
	{
		try {
			/**
			   * Create a map and store in object store for use in investigation
			   */
			
			  LabResultProxyVO labResultProxy = (LabResultProxyVO)getLabResultProxyVO(labUid, session);
			  LabReportFieldMappingBuilder mapBuilder = new LabReportFieldMappingBuilder();
			
			  //Call getLabResultProxyVO to get Org Facility /Provider / Reporter Info
			  // which are really missing in the form Proxy !
			
			  TreeMap<Object,Object> loadTreeMap = null;
			
			  if (labResultProxy != null)
			  {
			    loadTreeMap = mapBuilder.createLabReportLoadTreeMap(labResultProxy, labUid, processingDecision);
			  }
			  NBSContext.store(session, "DSLabMap", loadTreeMap);
		} catch (Exception e) {
			logger.error("LabCommonUtil.defaultValuesForCreateInvestigation Exception thrown" +  e); 
			logger.error("LabCommonUtil.defaultValuesForCreateInvestigation Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	
	}
	
	
	

	    /*
	 * sets negative tempId and newFlag to true for observations
	 */
	private Long setObservationForCreate(Collection<ObservationVO>  obsColl,Collection<ObservationVO>  obsCollection, 
	                                    Long tempID,boolean reportExteranlUser, ObservationVO rootObservationVO,
	                                    HttpServletRequest request,
	                                    NBSSecurityObj nbsSecurityObj, String reportingSourceLabClia) throws Exception{
	
		try {
			if(obsColl != null){
				//Remove observation LAB235 if processingDecision is not selected
				//ObservationVO removeLAB235 = null;
				Iterator<ObservationVO>  it = obsColl.iterator();
				while(it.hasNext()){
					ObservationVO obsVO = (ObservationVO)it.next();
					ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
					ObservationDT obsDT = obsVO.getTheObservationDT();
					if(obsVO.getTheObsValueCodedDTCollection() != null){
						Iterator<Object>  obsValueCode = obsVO.getTheObsValueCodedDTCollection().iterator();
						while(obsValueCode.hasNext()){
							ObsValueCodedDT obsValueDT = (ObsValueCodedDT) obsValueCode.next();
							if(obsValueDT.getCode() == null || obsValueDT.getCode().trim().length() == 0)
								obsValueDT.setCode("NI");
							obsValueDT.setItNew(true);
						}
					}
					if(obsVO.getTheObsValueDateDTCollection()!=null){
						Iterator<Object>  obsValueDate = obsVO.getTheObsValueDateDTCollection().iterator();
						while(obsValueDate.hasNext()){
							ObsValueDateDT obsValueDT =(ObsValueDateDT) obsValueDate.next();
							obsValueDT.setItNew(true);
							//obsValueDT.setItDirty(true);
						}
					}
					if (obsVO.getTheObsValueNumericDTCollection() != null)
					{
						Iterator<Object>  obsValueNumeric = obsVO.getTheObsValueNumericDTCollection().iterator();
						while (obsValueNumeric.hasNext()){
							ObsValueNumericDT obsValueDT = (ObsValueNumericDT)obsValueNumeric.next();
							obsValueDT.setItNew(true);
						}
					}

					if (obsVO.getTheObsValueTxtDTCollection() != null)
					{
						Iterator<Object>  obsValueText = obsVO.getTheObsValueTxtDTCollection().iterator();
						while (obsValueText.hasNext()) {

							ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
							obsValueDT.setItNew(true);
							if (obsDT.getCd()!=null && obsDT.getCd().equalsIgnoreCase("LAB214")) {
								obsValueDT.setTxtTypeCd("N");
								//LabReportComment(LAB214) Date goes as Observation.activity_to_time
								obsDT.setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
							}
						}

					}
					obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));

					/**PKS This should never happen, We should always set the uid for observation!!!
					if (!obsDT.getCd().equalsIgnoreCase("LAB112")) {
						//obsDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
						if(obsDT.getObservationUid()==null || obsDT.getObservationUid()>=0)
							obsDT.setObservationUid(new Long(tempID--));
					}
					obsDT.setItNew(true);

					*/
					if(obsDT.getCd() != null){
						if (obsDT.getObservationUid().compareTo(rootObservationVO.getTheObservationDT().getObservationUid())==0) {

							obsDT.setObsDomainCdSt1(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD);
							obsDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
							//CodeLookupDT CodeLookupDT = CachedDropDowns.getLabCodeSystem(obsDT.getCd(), reportingSourceLabClia);
							//  obsDT.setLastChgTime(new java.sql.Timestamp(new Date().getTime()));  // Don't set this for create

							String cdSystemCdOT = obsDT.getCdSystemCdOT();
							String labId = (String) request.getParameter("labId");

							// If user select from search Button
							if (obsDT.getSearchResultOT() != null &&
									obsDT.getSearchResultOT().length() != 0) {
								String ordered = obsDT.getSearchResultOT();
								obsDT.setCdDescTxt(ordered);
								obsDT.setTxt("");

								if(cdSystemCdOT.equalsIgnoreCase("LN"))
									obsDT.setCdSystemDescTxt("LOINC");
								else if(labId != null && !labId.equals("")&&!labId.equals("null")){
									try{
										obsDT.setCdSystemDescTxt(getSystemDescTxt(labId,request.getSession()));
									}catch(Exception e){}
								}

								if (cdSystemCdOT != null && !cdSystemCdOT.equals("")) {
									obsDT.setCdSystemCd(cdSystemCdOT);
								}
								else {
									if (labId != null && !labId.equals("") && !labId.equals("null")) {
										obsDT.setCdSystemCd(labId);
									}
								}
							}
							// If user select from Drop down
							else if(obsDT.getTxt() != null && obsDT.getTxt().length() !=0)
							{
								obsDT.setCdDescTxt(obsDT.getTxt());
								//obsDT.setTxt("");
								if (labId != null && !labId.equals("")&& !labId.equals("null")) {
									obsDT.setCdSystemCd(labId);
									try{
										obsDT.setCdSystemDescTxt(getSystemDescTxt(labId,
												request.getSession()));
									}catch(Exception e){}

								}
							}
							else if(obsDT.getCdDescTxt()==null || obsDT.getCdDescTxt().length()==0)
							{
								obsDT.setCdDescTxt("No Information Given");
								//obsDT.setCd("NI");
								obsDT.setCdSystemCd("2.16.840.1.113883");
								obsDT.setCdSystemDescTxt("Health Level Seven");
							}
							//obsDT.setCdSystemDescTxt("Health Level Seven");
							obsDT.setStatusCd("D");
							obsDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
							if (obsDT.getTargetSiteCd() == null ||
									obsDT.getTargetSiteCd().equals("")) {
								obsDT.setTargetSiteCd("NI");

							}
							if (reportExteranlUser == true)
								obsDT.setElectronicInd("E");
							else
								obsDT.setElectronicInd("N");

							String programAreaCd= request.getParameter("programAreaCd1");
							if(programAreaCd != null  && !programAreaCd.equals(""))
								obsDT.setProgAreaCd(programAreaCd);

							if (obsDT.getProgAreaCd() == null) {
								obsDT.setProgAreaCd("ANY");
							}
							if (obsDT.getProgAreaCd().equalsIgnoreCase("NONE")) {
								obsDT.setProgAreaCd("ANY");
							}

							String jurisdictionCd= request.getParameter("jurisdictionCd1");
							if(jurisdictionCd != null && !jurisdictionCd.equals(""))
								obsDT.setJurisdictionCd(jurisdictionCd);

							if(obsDT.getJurisdictionCd() == null)
								obsDT.setJurisdictionCd("ANY");


							/*
							 * AS per Rel1.1.3 we are not doing a reverse translation for ordered test rather
							 * we are translating the code to descrition and setting the Description to cdDescTxt.
							 * We are storing the orderedtest Code in txt field in lab XSP
							 */
							if(programAreaCd == null)
								programAreaCd = obsDT.getProgAreaCd();

							//SRTCode  srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,NEDSSConstants.ORDERED_TEST_LOOKUP,null,obsDT.getTxt(),null,request);

							//if ( srtcodeDT != null && srtcodeDT.getCode()!= null && srtcodeDT.getCode().equalsIgnoreCase(obsDT.getTxt())) 
							//  obsDT.setCdDescTxt(srtcodeDT.getDesc());

							//BB - updated for R1.1.4 - There is no need to make this call unless obsDT.cdDescTxt 
							//needs to be populated.  Also, in some cases the code to lookup will be in obsDT.cd 
							//rather than obsDT.txt
							if ( !obsDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN) &&
									(obsDT.getTxt() != null || obsDT.getCd() != null) && 
									(obsDT.getCdDescTxt() == null || obsDT.getCdDescTxt().trim().length() == 0) ) {

								SRTCode srtcodeDT = null;

								if (obsDT.getTxt() != null && obsDT.getTxt().trim().length() > 0)
									srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,
											NEDSSConstants.ORDERED_TEST_LOOKUP, null, obsDT.getTxt(), null, 
											request);
								else if (obsDT.getCd() != null && obsDT.getCd().trim().length() > 0)
									srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,
											NEDSSConstants.ORDERED_TEST_LOOKUP, null, obsDT.getCd(), null, 
											request);

								if (srtcodeDT != null && srtcodeDT.getCode() != null &&
										srtcodeDT.getCode().equalsIgnoreCase(obsDT.getTxt()))
									obsDT.setCdDescTxt(srtcodeDT.getDesc());
							}

							boolean permissionForProgJuriShar = nbsSecurityObj.getPermission(
									NBSBOLookup.OBSERVATIONLABREPORT,
									NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);


							if(permissionForProgJuriShar == false)
								obsVO.getTheObservationDT().setSharedInd("T");

							String targetSite = obsDT.getTargetSiteCd();
							if(targetSite != null && !targetSite.equals("NI"))
							{
								obsDT.setTargetSiteDescTxt(getDescTxt("ANATOMIC_SITE",targetSite));
							}
						}
						if (obsDT.getCd().equalsIgnoreCase("LAB214")) {


							obsDT.setObsDomainCdSt1(NEDSSConstants.C_RESULT);
							obsDT.setCtrlCdDisplayForm(NEDSSConstants.LABCOMMENT);
							obsDT.setCdDescTxt("User Report Comment");
							obsDT.setCdSystemCd("NBS");
							obsDT.setCdSystemDescTxt("NEDSS Base System");
							obsDT.setStatusCd("D");
							//obsDT.setRecordStatusCd(NEDSSConstants.ACTIVE);

						}

					}
					String addUserID= nbsSecurityObj.getEntryID();
					if (addUserID != null)
						obsDT.setAddUserId(new Long(addUserID));

					obsVO.setTheObservationDT(obsDT);
					obsVO.setItNew(true);
					obsVO.setItDirty(false);
					obsCollection.add(obsVO);
					setObsValueCodedToDefaultValues(obsCollection);
				}// End of while loop

			}
			return tempID;
		} catch (NumberFormatException | NEDSSAppException e) {
			logger.error("LabCommonUtil.setObservationForCreate Exception thrown" +  e); 
			logger.error("LabCommonUtil.setObservationForCreate Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	
	

	   private void setActIdForCreate(String reportingSourceLabClia, ObservationVO rootObservationVO,Collection<Object>  outColl,  HttpServletRequest request) throws NEDSSAppException
	   {
		 try {
			Collection<Object>  collLab112 = rootObservationVO.getTheActIdDTCollection();
			 if( rootObservationVO != null){
			 Iterator<Object>  itor = collLab112.iterator();
			  while (itor.hasNext())
			  {
			    ActIdDT actIdDT = (ActIdDT)itor.next();
			    actIdDT.setActUid(rootObservationVO.getTheObservationDT().getObservationUid());
			    //actIdDT.setAssigningAuthorityCd("CLIA");
			
			
				 TreeMap<?,?> treeMap = (TreeMap<?,?>)getCodeSystemDescription(reportingSourceLabClia, request.getSession());
						actIdDT.setAssigningAuthorityCd(request.getParameter("labId"));
			    actIdDT.setAssigningAuthorityDescTxt((String)treeMap.get(NEDSSConstants.KEY_CODESYSTEMDESCTXT));
			    actIdDT.setRecordStatusCd("ACTIVE");
			    actIdDT.setRootExtensionTxt(actIdDT.getRootExtensionTxt());
			    actIdDT.setStatusCd("A");
			    actIdDT.setStatusTime(new Timestamp(new Date().getTime()));
			    actIdDT.setTypeCd("FN");
			    actIdDT.setTypeDescTxt("Filler Number");
			        actIdDT.setItNew(true);
			        actIdDT.setActIdSeq(new Integer(0));
			        outColl.add(actIdDT);
			      }
			 }
		} catch (Exception e) {
			logger.error("LabCommonUtil.setActIdForCreate Exception thrown" +  e); 
			logger.error("LabCommonUtil.setActIdForCreate Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	   }
	    /**
	     * @method : getCodeSystemDescription
	     * @params : String, HttpSession
	     * @returnType : Treemap
	     */
	    public static TreeMap<?, ?> getCodeSystemDescription(String Laboratory_id, HttpSession session)
	    {
	        TreeMap<?, ?> treeMap = null;
	        MainSessionCommand msCommand = null;

	        if (Laboratory_id == null)
	        {
	            logger.error("Laboratory_id is null inside getCodeSystemDescription method");
	            return null;
	        }
	        else if (Laboratory_id != null)
	        {
	            try
	            {
	                logger.debug("Laboratory_id inside getCodeSystemDescription method is: " + Laboratory_id);

	                String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	                String sMethod = "getCodeSystemDescription";
	                Object[] oParams = new Object[] { Laboratory_id };
	                MainSessionHolder holder = new MainSessionHolder();
	                msCommand = holder.getMainSessionCommand(session);

	                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	                treeMap = (TreeMap<?, ?>) arr.get(0);
	            }
	            catch (Exception ex)
	            {

	                if (session == null)
	                {
	                    logger.debug("Error: no session, please login");

	                }
	                logger.fatal("getCodeSystemDescription: ", ex);
	            }
	        }
	        return treeMap;
	    }

	/*
	 *   checks each observation collection & ensures that for each, each
	 *     obsvaluecodedDT has at least a default value.   This will not
	 *     overwrite default values.
	 */
	private void setObsValueCodedToDefaultValues(Collection<ObservationVO>  observations) throws NEDSSAppException
	{
	
	   try {
		Iterator<ObservationVO>  collItor = observations.iterator();
		
		    if (collItor == null)
		
		        return;
		
		    //String _blank = "";
		
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
		logger.error("LabCommonUtil.setObsValueCodedToDefaultValues Exception thrown" +  e); 
		logger.error("LabCommonUtil.setObsValueCodedToDefaultValues Exception " +  e.getMessage()); 
		throw new NEDSSAppException(e.getMessage(), e);
	   }
	}
	
	   
	/**
	*
	* @param obsCOll Collection<Object>  object
	 * @throws NEDSSAppException 
	*/
	public void setLAB112(ObservationVO obsVOLab112, String reportingSourceLabClia) throws NEDSSAppException{
		try {
			ObservationDT obsDT = obsVOLab112.getTheObservationDT();
			if(obsDT.getHiddenCd()!= null && !obsDT.getHiddenCd().equals("")){
			              obsDT.setCd(obsDT.getHiddenCd());
			 }
			 /*
			    Setting the code for "No Information Given" for Lab112, Added the Cd in the Property File
			  */
			 else if(obsDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN))
			 {
			   PropertyUtil propertyUtil = PropertyUtil.getInstance();
			   String lab112Cd =  propertyUtil.getLab112Cd();
			   obsDT.setCd(lab112Cd);
			 }
			else if(obsDT.getTxt()!= null && !obsDT.getTxt().equals("")) {    	
				obsDT.setCd(obsDT.getTxt());
				CachedDropDownValues cdv = new CachedDropDownValues();
				String desc = cdv.getLabTestDesc(obsDT.getTxt());
				if(desc != "")
					obsDT.setCdDescTxt(desc);
			    obsDT.setTxt("");
			}
			if(obsDT.getCtrlCdDisplayForm()==null || obsDT.getCtrlCdDisplayForm().trim().equals("")) {
				obsDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_DISPALY_FORM);
			}
			if(obsDT.getStatusTime()==null ) {
				obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
			}
			if(obsDT.getCd()!=null)
				LabCommonUtil.updateObsVOForHiddenVarible(obsVOLab112, reportingSourceLabClia);
			
			
		} catch (Exception e) {
			logger.error("LabCommonUtil.setLAB112 Exception thrown" +  e); 
			logger.error("LabCommonUtil.setLAB112 Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
		
	}
	  
	 private String getDescTxt(String srt,String code) throws NEDSSAppException{
	  String descTxt=null;
	  try{
	  CachedDropDownValues cdv = new CachedDropDownValues();
	  descTxt= cdv.getDescForCode(srt,code);
	  }catch(Exception e){
		  logger.error("LabCommonUtil.getDescTxt Exception thrown" +  e); 
			logger.error("LabCommonUtil.getDescTxt Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
	  }
	  return descTxt;
	 }
	/**
	 * @throws NEDSSAppException 
	 * @method      : getLabResultProxyVO
	 * @params      : String, HttpSession
	 * @returnType  : LabResultProxyVO
	 */
	public static PageActProxyVO getLabResultProxyVO(Long observationUID, HttpSession session) throws NEDSSAppException {
	  //LabResultProxyVO labResultProxyVO = null;
	  MainSessionCommand msCommand = null;
	  PageActProxyVO pageActProxyVO = null;
	  if (observationUID == null) {
	    logger.error("LabCommomUitl.getLabResultProxyVO observationUID is null inside getLabResultProxyVO method");
	    return null;
	  }
	  else if (observationUID != null) {
	    try {
	      logger.debug("LabCommomUitl.getLabResultProxyVO: observationUID inside getLabResultProxyVO method is: " +
	                   observationUID);
	
	      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	      String sMethod = "getLabResultProxy";
	      Object[] oParams = new Object[] {
	          observationUID};
	      MainSessionHolder holder = new MainSessionHolder();
	      msCommand = holder.getMainSessionCommand(session);
	
	      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
	                                               sMethod, oParams);
	      pageActProxyVO = (PageActProxyVO) arr.get(0);
	    }
	    catch (Exception ex) {
	
	      if (session == null) {
	        logger.debug("LabCommomUitl.getLabResultProxyVO : Error: no session, please login");
	
	      }
	      logger.fatal("LabCommomUitl.getLabResultProxyVO: getLabResultProxyVO: ", ex);
	      throw new NEDSSAppException("LabCommomUitl.getLabResultProxyVO: getLabResultProxyVO: ", ex);
	    }
	  } 
	  return pageActProxyVO;
	}
	
	
	
	 public static String getCliaValue(Long orgUIDLong, HttpSession session) {
		    MainSessionCommand msCommand = null;
		    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
		    String sMethod = "organizationCLIALookup";
		    Object[] oParams = new Object[] {orgUIDLong};

		    String cliaNumber = null;
		    try {
		      MainSessionHolder holder = new MainSessionHolder();
		      msCommand = holder.getMainSessionCommand(session);
		      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		      cliaNumber = (String) arr.get(0);
		      
		      if (cliaNumber == null || cliaNumber == "") {
		          cliaNumber = NEDSSConstants.DEFAULT;
		        }
		    }
		    catch (Exception ex) {
		      logger.error("NedssCodeLookupServlet:There was some error in getting clia number from the database"+ ex);
		    }
		    return cliaNumber;

		  }

	 /**
	  * Sets the root obervtion information on the NBS UI
	  * @param observationUidStr
	  * @param clientVO
	  * @param form
	  * @param pageActProxyVO
	  * @throws NEDSSAppException
	  */
	public static void setObservationInformationOnForm(String observationUidStr, PageClientVO clientVO, PageForm form, PageActProxyVO pageActProxyVO, HttpServletRequest request) throws NEDSSAppException {
		boolean electronicIndicator = false;
		
		 HttpSession session = request.getSession();
		 
		 
		try {
			ObservationVO rootObservationVO = getRootObservationVOByUid(observationUidStr, (LabResultProxyVO) pageActProxyVO);
			ObservationDT observationDT  = rootObservationVO.getTheObservationDT();
			if (observationDT.getElectronicInd()!=null && observationDT.getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)){
				electronicIndicator = true;
			}
			//PageClientVO clientVO = form.getPageClientVO();
			if (clientVO.getAnswer(PageConstants.LAB_JURISDICTION_CD) != null)
				clientVO.setAnswer(PageConstants.LAB_JURISDICTION_CD, observationDT.getJurisdictionCd());
			if(observationDT.getProgAreaCd()!=null)
				clientVO.setAnswer(PageConstants.PROGRAM_AREA, observationDT.getProgAreaCd()); 
			//CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
			clientVO.setAnswer(PageConstants.LAB_JURISDICTION_CD, observationDT.getJurisdictionCd());
			//if (observationDT.getPregnantIndCd() != null) {
			//	clientVO.setAnswer(PageConstants.LAB_PREGNANT_IND_CD,observationDT.getPregnantIndCd());
			//}
			if (rootObservationVO.getObsValueNumericDT_s(0).getNumericUnitCd() != null) {
				clientVO.setAnswer(PageConstants.LAB_NUMERIC_UNIT_CD,rootObservationVO.getObsValueNumericDT_s(0).getNumericUnitCd());
			}if (observationDT.getEffectiveFromTime() != null) {
				if (electronicIndicator) {
					clientVO.setAnswer(PageConstants.LAB_EFFECTIVE_FROM_TIME,observationDT.getEffectiveFromTime()+"");
				}else
					clientVO.setAnswer(PageConstants.LAB_EFFECTIVE_FROM_TIME,StringUtils.formatDate(observationDT.getEffectiveFromTime()));
			}if (observationDT.getCdDescTxt() != null) {
				if (electronicIndicator) {
					String maskedDescription =ObservationDescriptionMaskForELR(observationDT);
					clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT,maskedDescription); 
					
				}else {
					clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT,observationDT.getCdDescTxt());
				}
			}
			if (observationDT.getElectronicInd() != null) {
				clientVO.setAnswer(PageConstants.LAB_ELR_IND,observationDT.getElectronicInd());
				clientVO.setAnswer(PageConstants.LAB_RSLT_ELR_IND,observationDT.getElectronicInd());
				session.setAttribute("electronicIndicator", observationDT.getElectronicInd());
				
			}
			if (rootObservationVO.getObservationReasonDT_s(0).getReasonCd() != null) {
				clientVO.setAnswer(PageConstants.LAB_REASON_CD,rootObservationVO.getObservationReasonDT_s(0).getReasonDescTxt() + "("
			+rootObservationVO.getObservationReasonDT_s(0).getReasonCd()+")");
			}
			if (observationDT.getTargetSiteCd() != null) {
				if (electronicIndicator) {
					clientVO.setAnswer(PageConstants.LAB_TARGET_SITE_CD,observationDT.getTargetSiteDescTxt() + "("+observationDT.getTargetSiteCd() + ")"); 
				}else {
					if(observationDT.getTargetSiteCd()!=null && observationDT.getTargetSiteCd().equals(NEDSSConstants.NOINFORMATIONGIVEN_CODE)) {
						clientVO.setAnswer(PageConstants.LAB_TARGET_SITE_CD,""); 
					}else
						clientVO.setAnswer(PageConstants.LAB_TARGET_SITE_CD,observationDT.getTargetSiteCd()); 
				}
			}
			if (observationDT.getActivityToTime() != null) {
				clientVO.setAnswer(PageConstants.LAB_ACTIVITY_TO_TIME,StringUtils.formatDate(observationDT.getActivityToTime()));
			}
			if (observationDT.getRptToStateTime() != null) {
				clientVO.setAnswer(PageConstants.LAB_RPT_TO_STATE_TIME,StringUtils.formatDate(observationDT.getRptToStateTime()));
			}
			
			/*
			if (rootObservationVO.getObsValueTxtDT_s(0).getValueTxt()!= null) {
				clientVO.setAnswer(PageConstants.LAB_VALUE_TXT,rootObservationVO.getObsValueTxtDT_s(0).getValueTxt());
			}
			*/
			if (observationDT.getTxt() != null) {
				clientVO.setAnswer(PageConstants.LAB_TXT,observationDT.getTxt());
			}
			if (observationDT.getPregnantIndCd() != null) {
				clientVO.setAnswer(PageConstants.LAB_PREGNANT_IND_CD,observationDT.getPregnantIndCd());
			}
			if (observationDT.getPregnantWeek()!= null && observationDT.getPregnantWeek().intValue()>0) {
				clientVO.setAnswer(PageConstants.LAB_PREGNANT_WEEK,observationDT.getPregnantWeek().toString());
			}
			if (observationDT.getPriorityCd() != null) {
				clientVO.setAnswer(PageConstants.LAB_PRIORITY_CD,observationDT.getPriorityCd());
			}
			if (observationDT.getElectronicInd()!=null && observationDT.getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)) {
				clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT+PageConstants.CODEID , observationDT.getCd());
				
			}		
			if (observationDT.getElectronicInd()!=null && observationDT.getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)) {
				
				String mergedCodedValue= observationMaskForELR(observationDT);
				clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT+PageConstants.CODEID , mergedCodedValue);
				
			}if (observationDT.getElectronicInd()!=null && observationDT.getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)) {
				String mergedCodedValue= obsTestCodeMaskForELR(observationDT);
				clientVO.setAnswer(PageConstants.LAB_ORDERED_TEST_CD , mergedCodedValue);
				
			}
			
			else if (observationDT.getCd() != null) {
				/**Pradeep : Search related question are special type of questions required special mapping*/
				clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT+PageConstants.CODEID , observationDT.getCd());
				
				clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT+PageConstants.DESCRIPTIONID ,observationDT.getCdDescTxt() +"("+ observationDT.getCd()+")"); 
				
				clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT+PageConstants.DESCRIPTION ,observationDT.getCdDescTxt());
				if(form.getActionMode().equals("View"))
					clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT, observationDT.getCdDescTxt());
				else
					clientVO.setAnswer(PageConstants.LAB_CD_DESC_TXT, observationDT.getCd() +"$$"+observationDT.getCdDescTxt());
				
			}
			if (observationDT.getTxt() != null) {
				clientVO.setAnswer(PageConstants.ELR_ROOT_OBS_COMMENT,observationDT.getTxt());
			}
			
			if (observationDT.getLocalId() != null) {
				clientVO.setAnswer(PageConstants.LAB_LOCAL_ID,observationDT.getLocalId());
			}
			if (observationDT.getTxt()!= null) {
				clientVO.setAnswer(PageConstants.LAB_LOCAL_ID,observationDT.getTxt());
			}
			String sharedInd = null;
			if (observationDT.getSharedInd() != null && observationDT.getSharedInd().equals("T"))
				sharedInd = "1";
			if (clientVO.getAnswer(PageConstants.SHARED_IND) == null)
				clientVO.setAnswer(PageConstants.SHARED_IND, sharedInd);

			if (observationDT.getStatusCd() != null)
            {
				clientVO.setAnswer(PageConstants.LAB_STATUS_CD, observationDT.getStatusCd());
            }
			if (observationDT.getPriorityCd() != null)
            {
				clientVO.setAnswer(PageConstants.LAB_PRIORITY_CD, observationDT.getPriorityCd());
            }
			Collection<Object> coll = rootObservationVO.getTheActIdDTCollection();
			if (coll != null && coll.size() > 0) {
				Iterator<Object> iter = coll.iterator();
				while (iter.hasNext()) {
					ActIdDT adt = (ActIdDT) iter.next();
					String typeCd = adt.getTypeCd() == null ? "" : adt
							.getTypeCd();
					String value = adt.getRootExtensionTxt() == null ? "" : adt
							.getRootExtensionTxt();
					if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.STATE_CASE, value);
					} else if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_CITY_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.COUNTY_CASE, value);
					} else if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.LEGACY_CASE_ID, value);
					}else if (typeCd.equalsIgnoreCase(NEDSSConstants.FILLER_NUMBER_FOR_ACCESSION_NUMBER)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.FILLER_ORDR_NBR_ACCESSION_NUMBER, value);
					}else if (typeCd.equalsIgnoreCase(NEDSSConstants.MESSAGE_CTRL_ID_CODE)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.MESSAGE_CTRL_ID_NUMBER, value);
					}
					
				}
			}
			Collection<Object> matColl = ((LabResultProxyVO)pageActProxyVO).getTheMaterialVOCollection();
			if(matColl!=null) {
				Iterator<Object> iter = matColl.iterator();
				while (iter.hasNext()) {
					MaterialVO materialVO = (MaterialVO) iter.next();
					
					if(materialVO.getTheMaterialDT().getDescription()!=null && electronicIndicator)
						clientVO.setAnswer(PageConstants.SPECIMEN_DETAILS, materialVO.getTheMaterialDT().getDescription());
					
					if(materialVO.getTheMaterialDT().getCd()!=null) {
						if (electronicIndicator) {
							clientVO.setAnswer(PageConstants.SPECIMEN_SOURCE, materialVO.getTheMaterialDT().getCdDescTxt());
						}
						else
						clientVO.setAnswer(PageConstants.SPECIMEN_SOURCE, materialVO.getTheMaterialDT().getCd());
					}
					if(electronicIndicator) {
						clientVO.setAnswer(PageConstants.LAB_COLL_VOL, materialVO.getTheMaterialDT().getQty());
						clientVO.setAnswer(PageConstants.LAB_COLL_VOL_UNITS, materialVO.getTheMaterialDT().getQtyUnitCd());
						String riskDesc="";
						if(materialVO.getTheMaterialDT().getRiskCd()!=null) {
							riskDesc=materialVO.getTheMaterialDT().getRiskCd();
						}else{  
							riskDesc="N/A";
						}
						if(materialVO.getTheMaterialDT().getRiskDescTxt()!=null) {
							riskDesc = materialVO.getTheMaterialDT().getRiskDescTxt()+"("+riskDesc+")";
						}else {
							riskDesc = riskDesc+" (N/A)";
							
						}
						clientVO.setAnswer(PageConstants.ELR_DANGER_DESC, riskDesc);
						
					}
				}
			}
			
			
		} catch (Exception e) {
        	logger.error("LabCommonUtil.setInvestigationInformationOnForm Exception thrown" +  e); 
    		logger.error("LabCommonUtil.setInvestigationInformationOnForm Exception " +  e.getMessage()); 
    		throw new NEDSSAppException(e.getMessage(), e);
		}

	}
	
	/**
	 * Sets the Patient Patient Status at Specimen Collection for the UI
	 * @param observationPatientStatusVOMap
	 * @param obsForm
	 * @throws NEDSSAppException
	 */
	public static void setPatientStatusInformationOnForm(Map<Long, Object> observationPatientStatusVOMap, ClientVO clientVO) throws NEDSSAppException {
		
		try {
			Collection<Object> coll = observationPatientStatusVOMap.values();
			if(coll!=null && coll.size()>0) {
				ObservationVO patientStatusVO =(ObservationVO)LabPageUtil.getFirstElementOfCollection(coll);
				Collection<Object> obsValueCodedColl = patientStatusVO.getTheObsValueCodedDTCollection();
				if(obsValueCodedColl!=null) {
					Iterator<Object> iter = obsValueCodedColl.iterator();
					while (iter.hasNext()) {
						ObsValueCodedDT obsCodedDt = (ObsValueCodedDT) iter.next();
						if(obsCodedDt.getCode()!=null) {
							clientVO.setAnswer(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION, obsCodedDt.getCode());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("LabCommonUtil.setPatientStatusInformationOnForm Exception thrown" +  e);
			throw new NEDSSAppException("LabCommonUtil.setPatientStatusInformationOnForm Exception thrown" , e);
		}
	}
	
	/**
	 * Util method to set comments on Lab	
	 * @param labResultCommentVOMap
	 * @param clientVO
	 * @throws NEDSSAppException
	 */
	public static void setLabCommentInformationOnForm(Map<Long, Object> labResultCommentVOMap, ClientVO clientVO) throws NEDSSAppException {
			
			try {
				Collection<Object> coll = labResultCommentVOMap.values();
				if(coll!=null && coll.size()>0) {
					ObservationVO labCommentVO =(ObservationVO)LabPageUtil.getFirstElementOfCollection(coll);
					Collection<Object> obsValueTextColl = labCommentVO.getTheObsValueTxtDTCollection();
					if(obsValueTextColl!=null) {
						Iterator<Object> iter = obsValueTextColl.iterator();
						while (iter.hasNext()) {
							ObsValueTxtDT obsValueTxt= (ObsValueTxtDT) iter.next();
							if(obsValueTxt.getValueTxt()!=null) {
								clientVO.setAnswer(PageConstants.NBS460, obsValueTxt.getValueTxt());
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("LabCommonUtil.setPatientStatusInformationOnForm Exception thrown" +  e);
				throw new NEDSSAppException("LabCommonUtil.setPatientStatusInformationOnForm Exception thrown" , e);
			}
		}
	/**
	 * Gets root observation from LabResultProxyVO based on Observation_UID
	 * @param observationUidStr
	 * @param labResultProxyVO
	 * @return
	 * @throws NEDSSAppException
	 */
	public static ObservationVO getRootObservationVOByUid(String observationUidStr, LabResultProxyVO labResultProxyVO) throws NEDSSAppException {
		ObservationVO observationVO=null;
		try {
			Collection<?> coll =  labResultProxyVO.getTheObservationVOCollection();
			if(coll!=null) {
				Iterator<?> it  = coll.iterator();
				while(it.hasNext()) {
					observationVO = (ObservationVO)it.next();
					if(observationVO.getTheObservationDT().getObservationUid().toString().equalsIgnoreCase(observationUidStr)) {
						return observationVO;
					}
				}
			}
			logger.debug("NO Matching ObservationVO found in collection for matching code"+ observationUidStr+"!!!");
		} catch (Exception e) {
			logger.error("LabCommonUtil.getRootObservationVOByUid Exception thrown" +  e); 
			logger.error("LabCommonUtil.getRootObservationVOByUid Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
		return observationVO;
		
	}
	
	/**
	 * gets Organization based on Organization_uid
	 * @param organizationUid
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static Map<Object,Object> getOrganization(String organizationUid, HttpSession session)
		    throws    Exception
		{
		  try {
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			  StringBuffer result = new StringBuffer("");
			  MainSessionCommand msCommand = null;
			  String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			  String sMethod = "getOrganization";
			  Object[] oParams = new Object[] {
			      new Long(organizationUid)};

			  MainSessionHolder holder = new MainSessionHolder();
			  msCommand = holder.getMainSessionCommand(session);
			  ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			  OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
			  if (organizationVO != null) {

			    if (organizationVO.getTheOrganizationNameDTCollection() != null) {
			     Iterator<Object>  orgNameIt = organizationVO.
			          getTheOrganizationNameDTCollection().
			          iterator();
			      while (orgNameIt.hasNext()) {
			        OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.
			            next();
			        result.append(orgName.getNmTxt());
			      }
			    }
			    if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
			      if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
			       Iterator<Object>  orgLocatorIt = organizationVO.
			            getTheEntityLocatorParticipationDTCollection().iterator();
			        while (orgLocatorIt.hasNext()) {
			          EntityLocatorParticipationDT entityLocatorDT = (
			              EntityLocatorParticipationDT) orgLocatorIt.next();
			          if (entityLocatorDT != null) {
			            PostalLocatorDT postaLocatorDT = entityLocatorDT.
			                getThePostalLocatorDT();
			            if (postaLocatorDT != null) {
			              if (postaLocatorDT.getStreetAddr1() != null) {
			                result.append("<br/>").append(postaLocatorDT.
			                                             getStreetAddr1());
			              }
			              if (postaLocatorDT.getStreetAddr2() != null) {
			                result.append("<br/>").append(postaLocatorDT.
			                                             getStreetAddr2());
			              }
			              if (postaLocatorDT.getCityCd() != null) {
			                result.append("<br/>").append(postaLocatorDT.getCityCd());

			              }
			              if (postaLocatorDT.getStateCd() != null) {
			                result.append(", ").append(postaLocatorDT.getStateCd());

			              }
			              if (postaLocatorDT.getZipCd() != null) {
			                result.append(" ").append(postaLocatorDT.getZipCd());

			              }
			            }
			          }
			          TeleLocatorDT telelocatorDT = entityLocatorDT.
			              getTheTeleLocatorDT();
			          if (telelocatorDT != null) {
			            if (telelocatorDT.getPhoneNbrTxt() != null) {
			              result.append("<br/>").append(telelocatorDT.
			                                           getPhoneNbrTxt());
			            }
			            if (telelocatorDT.getExtensionTxt() != null) {
			              result.append(" Ext. ").append(telelocatorDT.
			                                             getExtensionTxt());
			            }
			            break;
			          }
			        }
			      }
			    }
			  }

			  returnMap.put("UID", organizationUid);
			  returnMap.put("result", result.toString());
			  return returnMap;
		} catch (Exception e) {
			logger.error("LabCommonUtil.getOrganization Exception thrown" +  e); 
			logger.error("LabCommonUtil.getOrganization Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}

		}

	
	public static PersonVO getPatientVO(LabResultProxyVO labproxyVO, ObservationVO lab112VO) throws NEDSSAppException
    {
        try {
			PersonVO patientVO = null;
			Long patientUid = null;
			if (lab112VO.getTheParticipationDTCollection() != null)
			{
			    Iterator<Object> it = lab112VO.getTheParticipationDTCollection().iterator();
			    while (it.hasNext())
			    {
			        ParticipationDT partDT = (ParticipationDT) it.next();
			        if (partDT != null && partDT.getTypeCd().equalsIgnoreCase("PATSBJ")
			                && partDT.getActClassCd().equalsIgnoreCase("OBS"))
			        {
			            patientUid = partDT.getSubjectEntityUid();
			        }

			    }
			}
			if (labproxyVO.getThePersonVOCollection() != null)
			{
			    Iterator<Object> it = labproxyVO.getThePersonVOCollection().iterator();
			    while (it.hasNext())
			    {
			        PersonVO personVO = (PersonVO) it.next();
			        if (personVO != null && personVO.getThePersonDT().getPersonUid().compareTo(patientUid) == 0)
			            patientVO = personVO;
			    }
			}
			return patientVO;
		} catch (Exception e) {
			logger.error("LabCommonUtil.getPatientVO Exception thrown" +  e); 
			logger.error("LabCommonUtil.getPatientVO Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
    }
    
    
    public static void GenerateLinks( PageForm obsForm, HttpServletRequest request)  throws NEDSSAppException
	{
    	try {
			HttpSession session = request.getSession();
			setSTDandHIVPAsToForm(obsForm);
			String contextAction = null;
			contextAction = request.getParameter("ContextAction");
			String conditionCode = null;
			TreeMap<Object, Object> tm = new TreeMap<Object, Object>();

	  	  	setLabInfoForCreate(obsForm, request);
	  	  	request.getSession().setAttribute(NEDSSConstants.SUBFORM_HASHMAP, null);

			if (contextAction == null)
				contextAction = (String) request.getAttribute("ContextAction");

			String sCurrTask = NBSContext.getCurrentTask(session);

			NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

			boolean checkEntitySearch = secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FIND);
			request.setAttribute("checkFile", String.valueOf(checkEntitySearch));
			
			boolean checkInvestigationAddPermission = secObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ADD, ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
					ProgramAreaJurisdictionUtil.ANY_JURISDICTION, ProgramAreaJurisdictionUtil.SHAREDISTRUE);

			request.setAttribute("ExternalFlag", String.valueOf(checkInvestigationAddPermission));
			request.setAttribute("orderingOrgDetails", "There is no Ordering Facility selected.");
			request.setAttribute("providerDetails", "There is no Ordering Provider selected.");
			// gets the jurisdiction from security object and sets it to request
			getNBSSecurityJurisdictionsPA(contextAction, obsForm,secObj,request);
			request.setAttribute("mode", "ADD");
			
			if (checkInvestigationAddPermission) {
				obsForm.getSecurityMap().put("CreateInvestigation", "true");
			}
			else{
				obsForm.getSecurityMap().put("CreateInvestigation", "false");
			}

			// Permission check for ProgramArea, Juridition and Shared inidicator.

			boolean retainCheckbox;
			/*@TODO External user mappings
			if (userTypeValue != null) { }
			*/
			if (contextAction.equalsIgnoreCase("AddLabDataEntry")) {
				session.setAttribute("patientFalse", "false");
				session.setAttribute("reportingLabFalse", "false");
			}
			
			PersonVO retainPersonVO = null;
			Long retainedReportingFacilityUid = null;
			try {
				retainPersonVO = (PersonVO) NBSContext.retrieve(session, "DSPatientPersonVO");
			} catch (Exception e) {
				// do nothing
			}

			try {
				retainedReportingFacilityUid = (Long) NBSContext.retrieve(session, "DSLabInformation");

			} catch (Exception e) {
				// do nothing
			}

			tm = NBSContext.getPageContext(session, "PS015", contextAction);
			sCurrTask = NBSContext.getCurrentTask(session);
			if (sCurrTask.equalsIgnoreCase("AddObservationLab1") || sCurrTask.equalsIgnoreCase("AddObservationLab3")
					|| sCurrTask.equalsIgnoreCase("AddObservationLab4")) {

				String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSProgramArea);
				obsForm.getPageClientVO().getAnswerMap().put(PageConstants.PROGRAM_AREA, programArea);
				conditionCode = (String) NBSContext.retrieve(session, NBSConstantUtil.DSConditionCode);
				// System.out.println("conditionCode in add lab is :" + conditionCode);
				request.setAttribute("conditionCode", conditionCode);
				String jurisdiction = (String) NBSContext.retrieve(session, NBSConstantUtil.DSJurisdiction);
				obsForm.getPageClientVO().getAnswerMap().put(PageConstants.JURISDICTION, jurisdiction);
				obsForm.getAttributeMap().put("ReadOnlyJursdiction", "true");
				obsForm.getAttributeMap().put("ReadOnlyProgramArea", "true");
				boolean AddObservationLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD,
						programArea, jurisdiction);
				if (AddObservationLab == false) {
					session.setAttribute("error", "Failed at security checking.");
					throw new ServletException("Failed at security checking.");
				} else {
					request.setAttribute("trueReadOnly", "true");
				}

			} else if (sCurrTask.equalsIgnoreCase("AddObservationLab2")) {
				boolean check2 = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD, "ANY",
						"ANY");
				if (check2 == false) {
					session.setAttribute("error", "Failed at security checking.");
					throw new ServletException("Failed at security checking.");
				}

				//Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
				//PersonVO personVO = this.findMasterPatientRecord(mprUid, session, secObj);
				//request.setAttribute("jurisdictionCd", personVO.getDefaultJurisdictionCd());
				
			}

			if (contextAction.equalsIgnoreCase("AddLabDataEntry")
					|| contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
				tm = NBSContext.getPageContext(session, "PS192", contextAction);
			       sCurrTask = NBSContext.getCurrentTask(session);
				obsForm.getAttributeMap().put("Submit", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Submit"));
				obsForm.getAttributeMap().put("SubmitNoViewAccess", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Submit"));
				obsForm.getAttributeMap().put("Cancel", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Cancel"));
				obsForm.getPageClientVO().getAnswerMap().put(PageConstants.PROGRAM_AREA, NEDSSConstants.PROGRAM_AREA_NONE);
				obsForm.getPageClientVO().getAnswerMap().put(PageConstants.JURISDICTION, NEDSSConstants.JURISDICTION_NONE);
				obsForm.getSecurityMap().put("SubmitCreateInvPage", "false");
				boolean isAssignSecurityPermission = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);
				if (!isAssignSecurityPermission) {
					obsForm.getAttributeMap()
							.put("ReadOnlyJursdiction", "true");
					obsForm.getAttributeMap()
							.put("ReadOnlyProgramArea", "true");
				}
				if (retainPersonVO != null
						&& retainPersonVO.getThePersonDT().getPersonParentUid() != null) {
					if (retainPersonVO != null) {
						ClientUtil.setPatientInformation(
								obsForm.getActionMode(), retainPersonVO,
								obsForm.getPageClientVO(), request,
								obsForm.getPageFormCd());
						ClientUtil.setPersonIdDetails(retainPersonVO, obsForm);
						String uidSt = retainPersonVO.getThePersonDT().getPersonParentUid()
								.toString()
								+ "|1";
						obsForm.getAttributeMap().put(PageConstants.LAB_PATIENT + "Uid",
								uidSt);
						
						LabViewCommonUtil.populatePatientSummary(obsForm, retainPersonVO, null, obsForm.getBusinessObjectType(), request);
					}
				}


				
				if (retainedReportingFacilityUid != null) {
					OrganizationVO organizationVO = InvestigationUtil
							.getOrganization(retainedReportingFacilityUid, secObj,
									request);
					if (organizationVO != null) {
						String uidSt = organizationVO.getTheOrganizationDT()
								.getOrganizationUid().toString()
								+ "|"
								+ organizationVO.getTheOrganizationDT()
										.getVersionCtrlNbr().toString();
						obsForm.getAttributeMap()
								.put(PageConstants.LAB_REPORTING_FACILITY + "Uid",
										uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display1 = helper
								.makeORGDisplayString(organizationVO);
						obsForm.getAttributeMap().put(
								PageConstants.LAB_REPORTING_FACILITY
										+ "SearchResult", display1);
					}
				}
				
				((NBSObjectStore)session.getAttribute(
						NBSConstantUtil.OBJECT_STORE)).remove("DSPatientPersonVO");
				((NBSObjectStore)session.getAttribute(
						NBSConstantUtil.OBJECT_STORE)).remove("DSLabInformation");
				
				obsForm.getSecurityMap().put("CreateInvestigation", "false");

				boolean permissionForProgJuriShar = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
						NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);
				request.setAttribute("countiesInState", PersonUtil.getCountiesByState(PersonUtil.getDefaultStateCd()));
				request.setAttribute("PermissionForProgJuriShar", String.valueOf(permissionForProgJuriShar));
				request.setAttribute("programAreaCd", String.valueOf("NONE"));
				request.setAttribute("jurisdictionCd", String.valueOf("NONE"));

				retainCheckbox = true;
				request.setAttribute("retainCheckbox", String.valueOf(retainCheckbox));
				tm = NBSContext.getPageContext(session, "PS192", contextAction);
				sCurrTask = NBSContext.getCurrentTask(session);
				if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1")) {
					request.setAttribute("ContextAction", tm.get("Submit"));
				}
				if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1")
						|| sCurrTask.equalsIgnoreCase("AddObservationLab2")) {
					
					

					boolean AddObservationLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.ADD, "ANY", "ANY");
					if (AddObservationLab == false) {
						session.setAttribute("error", "Failed at security checking.");
						throw new ServletException("Failed at security checking.");
					}
				}

				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
				// this will set ContextAction for leftnav and file also
				request.setAttribute("ContextAction", tm.get("Submit"));

				obsForm.getAttributeMap().put("Cancel", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Cancel"));

				// ErrorMessageHelper.setErrMsgToRequest(request, "PS192");
				// ErrorMessageHelper.setErrMsgToRequest(request,"PS198");
				ArrayList<Object> list = new ArrayList<Object>();
				list.add("PS192");
				list.add("PS198");
				ErrorMessageHelper.setErrMsgToRequest(request, list);

			} else if (contextAction.equalsIgnoreCase("AddLab")) {
				retainCheckbox = false;
				request.setAttribute("retainCheckbox", String.valueOf(retainCheckbox));
				Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
				request.setAttribute("personUID", mprUid.toString());
				tm = NBSContext.getPageContext(session, "PS015", contextAction);
				sCurrTask = NBSContext.getCurrentTask(session);
				// System.out.println("\n\n after getPageCOntext load " + sCurrTask);
				//ArrayList<Object> stateList = new ArrayList<Object>();
				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
				obsForm.getAttributeMap().put("ContextAction", tm.get("Submit"));

				obsForm.getAttributeMap().put("Submit", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Submit"));
				obsForm.getAttributeMap().put("Cancel", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Cancel"));
				obsForm.getAttributeMap().put("Edit", "/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("Edit"));
				
				if(tm.get("SubmitAndCreateInvestigation") !=null && tm.get("SubmitAndCreateInvestigation").toString().equals("SubmitAndCreateInvestigation") && checkInvestigationAddPermission) {
					obsForm.getSecurityMap().put("SubmitCreateInvPage", "true");
				}else {
					obsForm.getSecurityMap().put("SubmitCreateInvPage", "false");
				}
				
				obsForm.getAttributeMap().put("SubmitAndCreateInvestigation",
						"/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("SubmitAndCreateInvestigation"));
				obsForm.getAttributeMap().put("SubmitNoViewAccess",
						"/nbs/" + sCurrTask + ".do?method=createGenericSubmit&ContextAction=" + tm.get("SubmitNoViewAccess"));
				obsForm.getAttributeMap().put("description", "");
			} 

			// set tab order before we forward, xz 11/01/2004
			sCurrTask = NBSContext.getCurrentTask(session);
			if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1")) {
				request.setAttribute("DSFileTab", new Integer(NEDSSConstants.EVENT_TAB_PATIENT_ORDER).toString());
			} else {
				request.setAttribute("DSFileTab",
						new Integer(PropertyUtil.getInstance().getDefaultLabTabOrder()).toString());
			}
		} catch (Exception e) {
			logger.error("LabCommonUtil.GenerateLinks Exception thrown" +  e); 
			logger.error("LabCommonUtil.GenerateLinks Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

    
    public static void getNBSSecurityJurisdictionsPA(String contextAction, BaseForm obsForm, NBSSecurityObj nbsSecurityObj, HttpServletRequest request) throws NEDSSAppException
    {

        try {
			String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.OBSERVATIONLABREPORT,
			        NBSOperationLookup.ADD);

			StringBuffer stringBuffer = new StringBuffer();
			if (programAreaJurisdictions != null && programAreaJurisdictions.length() > 0)
			{ // "PA$J|PA$J|PA$J|"
			  // change the navigation depending on programArea
			    logger.info("programAreaJurisdictions: " + programAreaJurisdictions);
			    StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
			    while (st.hasMoreTokens())
			    {
			        String token = st.nextToken();
			        if (token.lastIndexOf("$") >= 0)
			        {
			            //String programArea = token.substring(0, token.lastIndexOf("$"));
			            String juris = token.substring(token.lastIndexOf("$") + 1);
			            stringBuffer.append(juris).append("|");
			        }
			    }
			    
			    obsForm.getAttributeMap().put("NBSSecurityJurisdictions", stringBuffer.toString());
			}

			TreeMap<Object, Object> treeMap = nbsSecurityObj.getProgramAreas(NBSBOLookup.OBSERVATIONLABREPORT,
			        NBSOperationLookup.ADD);
			// logger.debug("treeMap: " + treeMap);
			StringBuffer sb = new StringBuffer();
			if (treeMap != null)
			{
			    Set<Object> s = new TreeSet<Object>(treeMap.values());
			    Iterator<Object> it = s.iterator();
			    while (it.hasNext())
			    {
			        String sortedValue = (String) it.next();
			        Iterator<?> anIterator = treeMap.entrySet().iterator();

			        while (anIterator.hasNext())
			        {
			            @SuppressWarnings("rawtypes")
						Map.Entry map = (Map.Entry) anIterator.next();
			            if ((String) map.getValue() == sortedValue)
			            {
			                String key = (String) map.getKey();
			                String value = (String) map.getValue();
			                sb.append(key.trim()).append(NEDSSConstants.SRT_PART);
			                sb.append(value.trim()).append(NEDSSConstants.SRT_LINE);
			                logger.info(key + " : " + value);

			            }
			        }
			    }
			}
			obsForm.getAttributeMap().put("FilteredPrograAreas", sb.toString());
		} catch (Exception e) {
			logger.error("LabCommonUtil.getNBSSecurityJurisdictionsPA Exception thrown" +  e); 
			logger.error("LabCommonUtil.getNBSSecurityJurisdictionsPA Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}

    } // getJurisdictionsPA
    

	
	private static void setLabInfoForCreate(PageForm form,
			HttpServletRequest request) throws NEDSSAppException {
		try {
			HttpSession session = request.getSession();
			Map<Object, Object> answerMap = new HashMap<Object, Object>();
			answerMap.put(PageConstants.LAB_ELR_IND,
					NEDSSConstants.ELECTRONIC_IND);
			answerMap.put(PageConstants.LAB_RPT_TO_STATE_TIME,
					StringUtils.formatDate(new java.sql.Timestamp(new Date().getTime())));
			answerMap.put(PageConstants.DEM_DATA_AS_OF,
					StringUtils.formatDate(new java.sql.Timestamp(new Date().getTime())));
			answerMap.put(PageConstants.LAB_RSLT_ELR_IND,
					NEDSSConstants.ELECTRONIC_IND);
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			String userTypeValue = nbsSecurityObj.getTheUserProfile()
					.getTheUser().getUserType();
			Long reportingFacilityUid = nbsSecurityObj.getTheUserProfile()
					.getTheUser().getReportingFacilityUid();
		     // set electronic indicator value;
		      if (userTypeValue != null) {
				boolean reportExteranlUser = userTypeValue
						.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
		        if(reportExteranlUser){
					answerMap.put(PageConstants.LAB_ELR_IND,
							NEDSSConstants.EXTERNAL_USER_IND);
					answerMap.put(PageConstants.LAB_RSLT_ELR_IND,
							NEDSSConstants.EXTERNAL_USER_IND);
					form.getAttributeMap().put("readOnlyParticipant", PageConstants.LAB_REPORTING_FACILITY) ;
		        }
		      }
			if (reportingFacilityUid != null) {
				OrganizationVO organizationVO = InvestigationUtil
						.getOrganization(reportingFacilityUid, nbsSecurityObj,
								request);
				if (organizationVO != null) {
					String uidSt = organizationVO.getTheOrganizationDT()
							.getOrganizationUid().toString()
							+ "|"
							+ organizationVO.getTheOrganizationDT()
									.getVersionCtrlNbr().toString();
					form.getAttributeMap()
							.put(PageConstants.LAB_REPORTING_FACILITY + "Uid",
									uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					String display1 = helper
							.makeORGDisplayString(organizationVO);
					form.getAttributeMap().put(
							PageConstants.LAB_REPORTING_FACILITY
									+ "SearchResult", display1);
				}
			}
			
			answerMap.put(PageConstants.SHARED_IND, "1");
			form.getPageClientVO().getAnswerMap().putAll(answerMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(
					"LabCommonUtil.setLabInfoForCreate Exception occured in PageLoadUtil.setLabInfoForCreate: PageFormCd: "
							+ form.getPageFormCd()
							+ e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	
	public static ObservationVO getObservationVOByCode(String observationCodeStr, LabResultProxyVO labResultProxyVO) throws NEDSSAppException {
		ObservationVO observationVO=null;
		try {
			Collection<?> coll =  labResultProxyVO.getTheObservationVOCollection();
			if(coll!=null) {
				Iterator<?> it  = coll.iterator();
				while(it.hasNext()) {
					observationVO = (ObservationVO)it.next();
					if(observationVO.getTheObservationDT().getCd().equalsIgnoreCase(observationCodeStr)) {
						return observationVO;
					}
				}
			}
			logger.debug("NO Matching ObservationVO found in collection for matching code"+ observationCodeStr+"!!!");
		} catch (Exception e) {
			logger.error("LabCommonUtil.getObservationVOByCode Exception thrown" +  e); 
			logger.error("LabCommonUtil.getObservationVOByCode Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
		return observationVO;
		
	}
	
	    

   public static Map<String, Map<Long, Object>> resultedTestMap(Collection<ObservationVO>  coll, Long rootObservationUID) throws NEDSSAppException
   {
	   Map<String,  Map<Long, Object>> map  = new HashMap<String,  Map<Long, Object>>();
	   Map<Long, Object> resultedtTestMap  = new HashMap<Long, Object>();
	   Map<Long, Object> observationPatientStatusVOMap  = new HashMap<Long, Object>();
	   try {
		   if (coll == null)
		   return null;
		Iterator<ObservationVO>  itor = coll.iterator();

		 while (itor.hasNext()) {
			   ObservationVO obsVO = (ObservationVO) itor.next();
			   if(rootObservationUID.compareTo(obsVO.getTheObservationDT().getObservationUid())!=0 
					   && !obsVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB_330)) {
				   resultedtTestMap.put(obsVO.getTheObservationDT().getObservationUid(), obsVO);
				   logger.debug("LabCommonUtil.resultedTestMap record inserted in the map:"+obsVO.getTheObservationDT().toString());
		   }else if(obsVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB_330)) {
			   observationPatientStatusVOMap.put(obsVO.getTheObservationDT().getObservationUid(), obsVO);
			   logger.debug("LabCommonUtil.observationPatientStatusVOMap record inserted in the map: "+obsVO.getTheObservationDT().toString());
		   }
		 }
		 map.put(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION, observationPatientStatusVOMap);
		 map.put(PageConstants.LAB_RESULTED_TEST, resultedtTestMap);

		 return map;
	   } catch (Exception e) {
		logger.error("LabCommonUtil.resultedTestMap Exception thrown" +  e); 
		logger.error("LabCommonUtil.resultedTestMap Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
	   }
   }

   
	public static void saveLabComments(BatchEntry be, HttpSession session)
			throws IOException, ServletException {

		if (session == null) {
			logger.fatal("error no session");

			return;
		}
		// System.out.println("sCurrTask: " + sCurrTask);

		try {
			NBSSecurityObj secObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			if(be == null || be.getAnswerMap()==null || be.getAnswerMap().size()==0 || be.getAnswerMap().get(
					PageConstants.LAB_VALUE_TXT)==null)
				return;
			String comment = (String) be.getAnswerMap().get(
					PageConstants.LAB_VALUE_TXT);

			Long obsUid = new Long(NBSContext.retrieve(session,
					"DSObservationUID").toString());

			PageActProxyVO proxyVO = getLabResultProxyVO(obsUid, session);

			ObservationVO rootObsVO = getRootObservationVOByUid(
					obsUid.toString(), (LabResultProxyVO) proxyVO);

			// now set the comment
			ObservationVO resTestVo = new ObservationVO();
			ObservationDT resTestdt = new ObservationDT();
			resTestdt.setObservationUid(new Long(-2));
			resTestdt.setCdSystemCd("NBS");
			resTestdt.setStatusCd("D");// changed as per WPD 8505
			resTestdt.setCdDescTxt("User Report Comment");
			resTestdt.setCdSystemDescTxt("NEDSS Base System");
			resTestdt.setObsDomainCdSt1("C_Result");
			resTestdt.setAddUserId(new Long(secObj.getTheUserProfile()
					.getTheUser().getEntryID()));
			resTestdt.setAddTime(new java.sql.Timestamp(new Date().getTime()));
			resTestdt.setCd("LAB214");
			resTestdt.setEffectiveFromTime(new java.sql.Timestamp(new Date()
					.getTime()));
			resTestdt.setActivityToTime(new java.sql.Timestamp(new Date()
					.getTime()));
			resTestdt.setRptToStateTime(new java.sql.Timestamp(new Date()
					.getTime()));
			resTestdt.setItNew(true);
			resTestdt.setItDirty(false);
			resTestVo.setTheObservationDT(resTestdt);

			// create obs value test collection
			ObsValueTxtDT ovtDT = new ObsValueTxtDT();
			ovtDT.setObservationUid(resTestVo.getTheObservationDT()
					.getObservationUid());
			ovtDT.setObsValueTxtSeq(new Integer(1));
			ovtDT.setItNew(true);
			ovtDT.setItDirty(false);
			ovtDT.setValueTxt(comment);
			ovtDT.setTxtTypeCd("N");
			ArrayList<Object> ovtList = new ArrayList<Object>();
			ovtList.add(ovtDT);
			// add that collection to the resulted test
			resTestVo.setItNew(true);
			resTestVo.setItDirty(false);
			resTestVo.setTheObsValueTxtDTCollection(ovtList);

			// create observationVO for the ordered test

			ObservationVO ordTestVO = new ObservationVO();
			ObservationDT ordTestDT = new ObservationDT();
			ordTestDT.setObservationUid(new Long(-1));
			ordTestDT.setCdSystemCd("2.16.840.1.113883");// changed as per WPD
			ordTestDT.setStatusCd("D");// changed as per WPD 8505
			ordTestDT.setCdDescTxt("No Information Given");
			ordTestDT.setCdSystemDescTxt("Health Level Seven");
			ordTestDT.setObsDomainCdSt1("C_Order");
			ordTestDT.setCtrlCdDisplayForm("LabComment");

			ordTestDT.setCd("NI");
			ordTestDT.setActivityToTime(new java.sql.Timestamp(new Date()
					.getTime()));
			ordTestDT.setItNew(true);
			ordTestDT.setItDirty(false);
			ordTestVO.setItNew(true);
			ordTestVO.setItDirty(false);
			ordTestVO.setTheObservationDT(ordTestDT);

			// code to get around electronic ind not being set
			rootObsVO.getTheObservationDT().setElectronicInd("E");
			// create the act relationship between the ordered test and the
			// result test
			ActRelationshipDT arDT = new ActRelationshipDT();
			arDT.setSourceActUid(resTestVo.getTheObservationDT()
					.getObservationUid());
			arDT.setTargetActUid(ordTestVO.getTheObservationDT()
					.getObservationUid());
			arDT.setTypeCd("COMP");
			arDT.setTypeDescTxt("Has Component");
			arDT.setRecordStatusCd("ACTIVE");
			arDT.setStatusCd("A");// may not be in spec, but we prob need
			arDT.setSourceClassCd("OBS");
			arDT.setTargetClassCd("OBS");
			arDT.setItNew(true);
			arDT.setItDirty(false);

			// create the act relationship between the ordered test and the root
			// observation
			ActRelationshipDT arRootDT = new ActRelationshipDT();
			arRootDT.setSourceActUid(ordTestVO.getTheObservationDT()
					.getObservationUid());
			arRootDT.setTargetActUid(rootObsVO.getTheObservationDT()
					.getObservationUid());
			arRootDT.setTypeCd("APND");
			arRootDT.setSourceClassCd("OBS");
			arRootDT.setTargetClassCd("OBS");
			arRootDT.setTypeDescTxt("Appends");
			arRootDT.setRecordStatusCd("ACTIVE");
			arRootDT.setStatusCd("A");
			arRootDT.setItNew(true);
			arRootDT.setItDirty(false);

			// create the act relationship between the ordered test and the root
			// observation

			// add the act relationship to the ar collection on the proxy
			Collection<Object> arDTColl = new ArrayList<Object>();
			arDTColl.add(arDT);
			arDTColl.add(arRootDT);

			// add the resulted test and ordered test vo to the collection
			Collection<Object> obsVOColl = new ArrayList<Object>();
			obsVOColl.add(ordTestVO);
			obsVOColl.add(resTestVo);
			obsVOColl.add(rootObsVO);
			sendAddUserCommentsToEJB(obsVOColl, arDTColl, session);
		} catch (Exception e) {
			logger.error("error occurred in Add CommentSubmit. sendLabProxyToEJB");
			e.printStackTrace();
		}
	}

	private static void sendAddUserCommentsToEJB(
			Collection<Object> observationVOCollection,
			Collection<Object> actRelationshipDTCollection, HttpSession session)
			throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
			javax.ejb.EJBException, Exception {

		MainSessionCommand msCommand = null;

		try {
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod = null;
		MainSessionHolder holder = new MainSessionHolder();

		sMethod = "addUserComments";
		Object[] oParams = { observationVOCollection,
				actRelationshipDTCollection };
		msCommand = holder.getMainSessionCommand(session);
		msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		logger.info("store addComments completed");
		}
		catch(Exception ex){
			logger.fatal("Exception while storing lab commnets");
			ex.printStackTrace();
		}

	}
	public static void setSTDandHIVPAsToForm(PageForm form) {
		PropertyUtil properties = PropertyUtil.getInstance();
		String STDPAs = properties.getSTDProgramAreas();
		String HIVPAs = properties.getHIVProgramAreas();
		if(HIVPAs == null && STDPAs == null)
			return;
		else if(HIVPAs == null && STDPAs != null)
			form.getAttributeMap().put(NEDSSConstants.STD_PA_LIST, STDPAs);
		else if(STDPAs == null && HIVPAs != null)
			form.getAttributeMap().put(NEDSSConstants.STD_PA_LIST, HIVPAs);
		else{
			String STDHIVPAs = STDPAs+","+HIVPAs;
			form.getAttributeMap().put(NEDSSConstants.STD_PA_LIST, STDHIVPAs);
		}
	}
	public static String observationMaskForELR(ObservationDT observationDT) throws NEDSSAppException {
		String maskedValue="";
		try {
			if(observationDT.getCdDescTxt()!=null)
				maskedValue = observationDT.getCdDescTxt();
			if(maskedValue.trim().length()>0 && observationDT.getAltCdDescTxt()!=null && observationDT.getAltCdDescTxt().length()>0) {
				maskedValue = maskedValue+ " ("+ observationDT.getAltCdDescTxt()+")";
			}
		} catch (Exception e) {
			logger.error("Exception : "+ observationDT.toString(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return maskedValue;
	}
	
	public static String obsTestCodeMaskForELR(ObservationDT observationDT) throws NEDSSAppException {
		StringBuffer maskedValue= new StringBuffer("");
		try {
			if(observationDT.getCd()!=null)
				maskedValue.append(observationDT.getCd());
			if(maskedValue.length()>0 && observationDT.getCdSystemCd()!=null && observationDT.getCdSystemCd().length()>0) {
				maskedValue.append( " (").append(observationDT.getCdSystemCd());
			}
			if(maskedValue.length()>0 && observationDT.getCdSystemDescTxt()!=null && observationDT.getCdSystemDescTxt().length()>0) {
				maskedValue = maskedValue.append(" ").append(observationDT.getCdSystemDescTxt()).append(")");
			}
			maskedValue.append("/");
			if(observationDT.getAltCd()!=null)
				maskedValue.append(observationDT.getAltCd());
			if(maskedValue.length()>0 && observationDT.getAltCdSystemCd()!=null && observationDT.getAltCdSystemCd().length()>0) {
				maskedValue.append( " (").append(observationDT.getAltCdSystemCd());
			}
			if(maskedValue.length()>0 && observationDT.getAltCdSystemDescTxt()!=null && observationDT.getAltCdSystemDescTxt().length()>0) {
				maskedValue = maskedValue.append(" ").append(observationDT.getAltCdSystemDescTxt()).append(")");
			}
		} catch (Exception e) {
			logger.error("Exception : "+ observationDT.toString(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}	
		return maskedValue.toString();
	}
	public static String obsValueCodedMaskForELR(ObsValueCodedDT obsValueCodedDT) {
		String maskedValue="";
		if(obsValueCodedDT.getDisplayName()!=null) {
			maskedValue = obsValueCodedDT.getDisplayName();
		}
		if(obsValueCodedDT.getAltCdDescTxt()!=null) {
			maskedValue = maskedValue + " ("+ obsValueCodedDT.getAltCdDescTxt()+")";
		}
		return maskedValue;
		
	}	
	public static String ObservationDescriptionMaskForELR(ObservationDT observationDT) {
		String maskedValue="";
		if(observationDT.getCdDescTxt()!=null) {
			maskedValue = observationDT.getCdDescTxt();
		}
		if(observationDT.getAltCdDescTxt()!=null) {
			maskedValue = maskedValue + " ("+ observationDT.getAltCdDescTxt()+")";
		}
		return maskedValue;
	}	
	public static String obsCodedTestMaskForELR(ObsValueCodedDT obsValueCodedDT) throws NEDSSAppException {
		StringBuffer maskedValue= new StringBuffer("");
		try {
			if(obsValueCodedDT.getCode()!=null)
				maskedValue.append(obsValueCodedDT.getCode());
			if(maskedValue.length()>0 && obsValueCodedDT.getCodeSystemCd()!=null && obsValueCodedDT.getCodeSystemCd().length()>0) {
				maskedValue.append( " (").append(obsValueCodedDT.getCodeSystemCd());
			}
			if(maskedValue.length()>0 && obsValueCodedDT.getCodeSystemDescTxt()!=null && obsValueCodedDT.getCodeSystemDescTxt().length()>0) {
				maskedValue = maskedValue.append(" ").append(obsValueCodedDT.getCodeSystemDescTxt()).append(")");
			}
			maskedValue.append("/");
			if(obsValueCodedDT.getAltCd()!=null)
				maskedValue.append(obsValueCodedDT.getAltCd());
			if(maskedValue.length()>0 && obsValueCodedDT.getAltCdSystemCd()!=null && obsValueCodedDT.getAltCdSystemCd().length()>0) {
				maskedValue.append( " (").append(obsValueCodedDT.getAltCdSystemCd());
			}
			if(maskedValue.length()>0 && obsValueCodedDT.getAltCdSystemDescTxt()!=null && obsValueCodedDT.getAltCdSystemDescTxt().length()>0) {
				maskedValue = maskedValue.append(" ").append(obsValueCodedDT.getAltCdSystemDescTxt()).append(")");
			}
		} catch (Exception e) {
			logger.error("Exception : "+ obsValueCodedDT.toString(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}	
		return maskedValue.toString();
	}
	
	public static String getDateTImePerformingOrgVO(LabResultProxyVO labResultProxyVO,ObservationVO rootObservationVO, ObservationVO resultedTestVO){
		
	Long uid= null;
	boolean isFound =false;
	
		StringBuffer performingFacilityDetails = new StringBuffer();
		PostalLocatorDT postalDT = null;
		QuickEntryEventHelper qe = new QuickEntryEventHelper();
		try {
			performingFacilityDetails.append("<b>Date/Time</b>: ");
			if(resultedTestVO.getTheObservationDT().getActivityToTime()!=null) {
					Timestamp time = resultedTestVO.getTheObservationDT().getActivityToTime();
					performingFacilityDetails.append(time);
			}
			performingFacilityDetails.append("<br/><b>Performing Facility</b>:");	
			Collection<Object> participationCollection =resultedTestVO.getTheParticipationDTCollection();
			if(participationCollection!=null && participationCollection.size()>0) {
				Iterator<Object> partIterator = participationCollection.iterator();
				while(partIterator.hasNext()) {
					ParticipationDT partDT=(ParticipationDT)partIterator.next();
					uid = partDT.getSubjectEntityUid();
				}
			}
			if(uid!=null) {
				OrganizationVO organizationVO = new OrganizationVO();
				Collection<Object> orgCollection  =labResultProxyVO.getTheOrganizationVOCollection();
				if(orgCollection != null)
				{
					Iterator<Object> iter = orgCollection.iterator();

					while(iter.hasNext())
					{
						organizationVO = (OrganizationVO)iter.next();
						if(organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(uid)==0)
						{
							isFound =true;
							if(organizationVO !=null)
							{
								if(organizationVO.getOrganizationNameDT_s(0)!=null && organizationVO.getOrganizationNameDT_s(0).getNmTxt()!=null)
									performingFacilityDetails.append(organizationVO.getOrganizationNameDT_s(0).getNmTxt());
								
								if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
									Iterator<Object> entIt = organizationVO
											.getTheEntityLocatorParticipationDTCollection()
											.iterator();
									while (entIt.hasNext()) {
										EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
												.next();
										if (entityDT != null) {
											if (entityDT.getClassCd().equalsIgnoreCase("PST")
													&& entityDT.getRecordStatusCd()
															.equalsIgnoreCase("ACTIVE")
													&& entityDT.getStatusCd().equalsIgnoreCase(
															"A")
													&& entityDT.getUseCd().equalsIgnoreCase(
															"WP")) {
												postalDT = entityDT.getThePostalLocatorDT();
												performingFacilityDetails
														.append((postalDT.getStreetAddr1() == null) ? ""
																: ("<br/>" + postalDT.getStreetAddr1()));
												performingFacilityDetails
														.append((postalDT.getStreetAddr2() == null) ? ""
																: ("<br/>" + postalDT.getStreetAddr2()));
												performingFacilityDetails
														.append((postalDT.getCityDescTxt() == null) ? ""
																: ("<br/>" + postalDT.getCityDescTxt()));
												performingFacilityDetails
														.append((postalDT.getCityDescTxt() == null && postalDT
																.getStateCd() == null) ? ""
																: (", "));
												if (postalDT.getStateCd() != null) {
													performingFacilityDetails.append(qe.getStateDescTxt(postalDT
															.getStateCd()));

												}
												performingFacilityDetails
														.append((postalDT.getZipCd() == null) ? ""
																: (" " + postalDT.getZipCd() + " "));
											}
										}
									}
								}
								
								performingFacilityDetails.append("<br/><b>Facility ID</b>:");
								if(organizationVO .getTheEntityIdDTCollection()!=null)
								{
									Iterator<Object> iterator = organizationVO .getTheEntityIdDTCollection().iterator();
									while(iterator.hasNext())
									{
										EntityIdDT entityIdDT = (EntityIdDT)iterator.next();
										if(entityIdDT.getTypeCd().equals("FI"))
											if(entityIdDT.getRootExtensionTxt()!=null)
												performingFacilityDetails.append(entityIdDT.getRootExtensionTxt());
										if(entityIdDT.getTypeCd()!=null)
										{
											if(entityIdDT.getAssigningAuthorityCd() !=null)
												performingFacilityDetails.append(" ("+entityIdDT.getTypeCd()+ " " +entityIdDT.getAssigningAuthorityCd()+")");
											else
												performingFacilityDetails.append(" ("+entityIdDT.getTypeCd()+")");
											break;
										}
										else {
											performingFacilityDetails.append(" ("+entityIdDT.getAssigningAuthorityCd()+")");
											break;
										}
									}
								}
							}
							if(isFound)
								break;

						}
					}
				}
			}else {
				performingFacilityDetails.append("<br/><b>Facility ID</b>:");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return performingFacilityDetails.toString();
	}
	
	/**
	 * Removing UI place holder checkbox answers from answerMap. Doesn't requires to store in NBS_Answer table.
	 * 
	 * @param answersMap
	 * @throws NEDSSAppException
	 */
	public static void removeCheckboxesAnswerFromAnswerMapforLab(Map<Object, Object> answersMap) throws NEDSSAppException{
		try{
			answersMap.remove("NBS_LAB267");
			answersMap.remove("NBS_LAB223");
			answersMap.remove("NBS_LAB224");
		}catch (Exception e) {
			logger.error("Exception : "+e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}	
	}

	/**
	 * This code is valid for Roles within a Lab. This code can be made generic with a few changes
	 * -Metadata driven approach: Get the values from metadata, instead of collection only
	 * -Make Role available on the top level VO (pageActProxyVO)
	 * @throws NEDSSAppException 
	 */
	@SuppressWarnings("unchecked")
	public static void setRolesToParticipantsForELR(BaseForm form, LabResultProxyVO proxyVO, Long patientUid, HttpServletRequest request) throws NEDSSAppException {
		Collection<Object> roleParticipantList= new ArrayList<Object>(); 	
	    Collection<Object>  copyToProviderUIDs = new ArrayList<Object> ();
	    Long personProcurerUID = null;
	    Long alternateContactUID = null;
	    String alternateContactDesc="";
	    
		try {
			Collection<Object> roleColl = proxyVO.getTheRoleDTCollection();
			if(roleColl!=null && roleColl.size()>0) {
				Iterator<Object> it = roleColl.iterator();
				while(it.hasNext()) {
					RoleDT roleDT = (RoleDT)it.next();
					if(roleDT.getScopingEntityUid()!=null && roleDT.getScopingEntityUid().compareTo(patientUid)==0) {
						if(roleDT.getSubjectClassCd()!=null && roleDT.getSubjectClassCd().equals(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE)) {
							alternateContactUID=roleDT.getSubjectEntityUid();
							alternateContactDesc=CachedDropDowns.getCodeDescTxtForCd(roleDT.getCd(), EdxELRConstants.ELR_NEXT_OF_KIN_RL_CLASS);
							if(alternateContactDesc!=null && alternateContactDesc.length()>2) {
								alternateContactDesc  = Character.toUpperCase(alternateContactDesc .charAt(0)) + alternateContactDesc .substring(1);
							}
						}
						if(roleDT.getCd().equalsIgnoreCase("CT") && roleDT.getScopingEntityUid()!=null && roleDT.getScopingEntityUid().compareTo(patientUid)==0)
				         {
				           copyToProviderUIDs.add(roleDT.getSubjectEntityUid());
				         }
						if(roleDT.getCd().equalsIgnoreCase("SPP") && roleDT.getSubjectClassCd().equalsIgnoreCase("PROV"))
				         {
				            personProcurerUID = roleDT.getSubjectEntityUid();
				         }
					}
				}
			}
			
			Collection<Object>  personVOColl = proxyVO.getThePersonVOCollection();
			if(personVOColl!= null)
			{
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				String display="";
				Iterator<Object>  personList = personVOColl.iterator();
			    while(personList.hasNext())
			    {
			        ParticipantListDisplay participant = new ParticipantListDisplay();
			        ParticipantListDisplay nextOfKinParticipant = new ParticipantListDisplay();
			        PersonVO personVO = (PersonVO)personList.next();
			        if(alternateContactUID!=null && personVO.getThePersonDT().getPersonUid().compareTo(alternateContactUID)==0) {
			        	alternateContactDesc = "Next of Kin ("+alternateContactDesc +")";
			        	nextOfKinParticipant.setTitle(alternateContactDesc);
						display=helper.makePatientDisplayString(personVO);
						nextOfKinParticipant.setDetail(display);
						roleParticipantList.add(nextOfKinParticipant);
						continue;
			        }
			        if(personProcurerUID!=null && personVO.getThePersonDT().getPersonUid().compareTo(personProcurerUID)==0) {
						participant.setTitle(PageConstants.SPECIMEN_PROCURER_TITLE);
			        }
			        if(copyToProviderUIDs.contains(personVO.getThePersonDT().getPersonUid())) {
						participant.setTitle(PageConstants.COPY_TO_PROVIDER_TITLE);
			        }
			        if(participant.getTitle()!=null) {
			        	display=helper.makePRVDisplayString(personVO, true);
						participant.setDetail(display);
						roleParticipantList.add(participant);
			        	
			        }
			        
			     } 
			}
				if (roleParticipantList.size() > 0) {
					String sortMethod = "getTitle";
					Collection<Object>  participantList =(ArrayList<Object>)request.getAttribute("participantList");
					participantList.addAll(roleParticipantList);
					NedssUtils util = new NedssUtils();
					util.sortObjectByColumn(sortMethod, participantList);
					request.setAttribute("participantList", participantList);
				}
		} catch (Exception e) {
			logger.fatal("LabCommonUtil.setRolesToParticipantsForELR Exception occured : "+ patientUid + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
		
	}
	

	/**
	 * Code to update ObservationVO for hidden variables
	 * @param obsDT
	 * @param labId
	 * @throws NEDSSAppException
	 */
	public static void updateObsVOForHiddenVarible(ObservationVO obsVO, String labId) throws NEDSSAppException {
		ObsValueCodedDT obsValueCodedDT = null;
		try {
			CodeLookupDT codeLookupDT = CachedDropDowns.getLabCodeSystem(obsVO.getTheObservationDT().getCd(), labId);
			if(codeLookupDT.getCodedValueDescription()==null)
				codeLookupDT = CachedDropDowns.getLabCodeSystem(obsVO.getTheObservationDT().getCd(), NEDSSConstants.DEFAULT);
			if(labId!=null && labId.equals(NEDSSConstants.DEFAULT)) {
				obsVO.getTheObservationDT().setCdDescTxt(codeLookupDT.getCodedValueDescription());
				if((codeLookupDT.getCodedValueCodingSystem()!=null && codeLookupDT.getCodedValueCodingSystem().equals(NEDSSConstants.LOINC_CODE)) ||
						(codeLookupDT.getCodedValueCodingSystem()!=null && codeLookupDT.getCodedValueCodingSystem().equals(NEDSSConstants.SNOMED_CODE))) {
					obsVO.getTheObservationDT().setCdSystemCd(codeLookupDT.getCodedValueCodingSystem());
					obsVO.getTheObservationDT().setCdSystemDescTxt(codeLookupDT.getCodedValueCodingSystemDescTxt());
				} else {
					obsVO.getTheObservationDT().setCdSystemCd(labId);
					obsVO.getTheObservationDT().setCdSystemDescTxt(codeLookupDT.getDescTxt());
				}
			}
			else {
				obsVO.getTheObservationDT().setCdDescTxt(codeLookupDT.getCodedValueDescription());
				obsVO.getTheObservationDT().setCdSystemCd(codeLookupDT.getCodedValueCodingSystem());
				obsVO.getTheObservationDT().setCdSystemDescTxt(codeLookupDT.getCodedValueCodingSystemDescTxt());
			}
			obsVO.getTheObservationDT().setAltCd(codeLookupDT.getLocalCodedValue());
			obsVO.getTheObservationDT().setAltCdSystemCd(codeLookupDT.getLocalCodedValueCodingSystem());
			obsVO.getTheObservationDT().setAltCdDescTxt(codeLookupDT.getLocalCodedValueDescription());
	} catch (Exception e) {
			logger.fatal("LabCommonUtil.updateObsVOForHiddenVarible Exception occured : "+ obsVO.getTheObservationDT().toString() + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

		try {
			CodeLookupDT codeLookupDT = null;
			if(obsVO.getTheObsValueCodedDTCollection()!=null) {
				Iterator<Object> it =obsVO.getTheObsValueCodedDTCollection().iterator();
				while(it.hasNext()) {
					obsValueCodedDT = (ObsValueCodedDT)it.next();
					codeLookupDT = CachedDropDowns.getLabCodeSystem(obsValueCodedDT.getCode(), labId);
					obsValueCodedDT.setCodeSystemCd(codeLookupDT.getCodedValueCodingSystem());
					obsValueCodedDT.setAltCd(codeLookupDT.getLocalCodedValue());
					if(obsValueCodedDT.getAltCd()!=null) {
						obsValueCodedDT.setCodeDerivedInd("Y");
					}
					obsValueCodedDT.setAltCdSystemCd(codeLookupDT.getLocalCodedValueCodingSystem());
					obsValueCodedDT.setAltCdDescTxt(codeLookupDT.getLocalCodedValueDescription());
					obsValueCodedDT.setAltCdSystemDescTxt(codeLookupDT.getLocalCodedValueCodingSystemDescTxt());
				}
			}
			
			if(obsVO.getTheObservationDT().getObsDomainCdSt1()!=null && obsVO.getTheObservationDT().getObsDomainCdSt1().equals(NEDSSConstants.LAB_REPORT) 
					&& obsVO.getTheObservationDT().getObsDomainCdSt1().equals(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD) 
					&& obsVO.getTheObservationDT().getAltCd()==null && codeLookupDT!=null) {
				obsVO.getTheObservationDT().setCdSystemDescTxt(codeLookupDT.getDescTxt());	
			}
		} catch (Exception e) {
			logger.fatal("LabCommonUtil.updateObsVOForHiddenVarible Exception occured : "+ obsValueCodedDT.toString() + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

	}
	
	/**
	 * Convenient method for parsing out ObsValueNumericDT
	 * @param obsValueNumericDT
	 * @return
	 */
	public static String getStringFromObsValueNumericDT(ObsValueNumericDT obsValueNumericDT) {
		StringBuffer text =new StringBuffer("");
		
		if(obsValueNumericDT.getComparatorCd1()!=null) {
			text.append(obsValueNumericDT.getComparatorCd1());
		}
		if(obsValueNumericDT.getNumericValue1()!=null) {
			text.append(obsValueNumericDT.getNumericValue1());
		}
		if(obsValueNumericDT.getSeparatorCd()!=null) {
			text.append(obsValueNumericDT.getSeparatorCd());
		}
		if(obsValueNumericDT.getNumericValue2()!=null) {
			text.append(obsValueNumericDT.getNumericValue2());
		}
		return text.toString();
	}

}

