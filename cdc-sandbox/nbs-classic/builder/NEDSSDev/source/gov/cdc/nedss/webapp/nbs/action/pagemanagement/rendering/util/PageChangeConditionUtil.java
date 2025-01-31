package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *Name: PageChangeConditionUtil
 * Description: Action class for the Change Condition button on the View Investigation.
 *     If the user has add permission and the condition has related 'family' conditions 
 *     they can change the condition. Note that Change Condition is only available for 
 *     new published dynamic pages.
 * 
 * @Company: CSC
 * @since: NBS4.02
 * @author: Greg Tucker, Jit S. Gill
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 * 
 */
public class PageChangeConditionUtil {

	
	static final LogUtils logger = new LogUtils(PageChangeConditionUtil.class.getName());
	
	/**
	   * loadChangeCondition - load the initial ChangeCondition.jsp form.
	   * @param form  
	   * @param request
	   */
	public static void loadChangeCondition(PageForm form, HttpServletRequest req) {
		
		String caseId = (String) form.getAttributeMap().get("caseLocalId");
		PageProxyVO proxyVO = form.getPageClientVO().getOldPageProxyVO();
		PublicHealthCaseDT phcDT = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = phcDT.getPublicHealthCaseUid();
		String conditionCd = phcDT.getCd();
		if (conditionCd == null || conditionCd.isEmpty())
			conditionCd = (String) req.getSession().getAttribute(NBSConstantUtil.DSInvestigationCondition);
		form.setNewConditionCd(phcDT.getCd());
		form.getAttributeMap().put("caseLocalId", caseId);
		form.getAttributeMap().put("oldConditionDesc", phcDT.getCdDescTxt());
		form.getAttributeMap().put("oldConditionCd", phcDT.getCd());
		form.getAttributeMap().put("oldProgramArea", phcDT.getProgAreaCd());
		form.getAttributeMap().put("investigationUID", phcUid);
	}
	
	
	
	/**
	   * The user has selected a new condition to change to. Call the back-end methods to create a new case with the new condition.
	   * @param mapping 
	   * @param form  
	   * @param request
	   * @param response
	   * @throws ServletException
	   * @throws IOException
	   */
	
	public static ActionForward storeChangeConditionRetainSameCase(ActionMapping mapping, PageForm form, HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException  {
		 List<String> allowedUrls = new ArrayList<String>();
		 
		StringBuffer viewURL = new StringBuffer((String)form.getAttributeMap().get("Edit"));
		String newConditionCd = (String) req.getParameter("INV169"); 
		viewURL.append("&newCondition=").append(newConditionCd);
		allowedUrls.add(((String)form.getAttributeMap().get("Edit"))+"&newCondition="+newConditionCd);
		if(NedssUtils.isLocalPath(viewURL.toString()) && allowedUrls.contains(viewURL.toString())) res.sendRedirect(viewURL.toString());
		return null;
	}
	
	public static ActionForward storeChangeConditionInNewCase(ActionMapping mapping, PageForm form, HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException  {
		    List<String> allowedUrls = new ArrayList<String>();
		    allowedUrls.add((String) form.getAttributeMap().get("caseReporting"));
			//String url = (String) form.getAttributeMap().get("linkValue");   //if returning to view file
			String viewURL = (String) form.getAttributeMap().get("caseReporting");
			String newConditionCd = (String) req.getParameter("INV169"); 
			String newConditionDesc = (String) req.getParameter("INV169Desc");
			Long phcUid = (Long) form.getAttributeMap().get("investigationUID");
			String oldLocalId = (String) form.getAttributeMap().get("caseLocalId");
			String oldConditionDesc = (String) form.getAttributeMap().get("oldConditionDesc");
			HttpSession session = req.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
			
			try {
				PageProxyVO pageProxyVO= prepareProxyVOforChangeConditionInNewCase(form, newConditionCd,newConditionDesc, req, nbsSecurityObj);
				Long newPHCUid = callChangeConditionEJB(phcUid,pageProxyVO, req);
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid,String.valueOf(newPHCUid.longValue()));
				addNewNoteForChangeCondition(session, nbsSecurityObj, newPHCUid, oldConditionDesc, newConditionDesc);
			} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
				logger.fatal("ERROR -. NEDSSAppConcurrentDataException, PageChangeConditionOwnershipUtil.The data has been modified by another user, please recheck! ",e);
				return mapping.findForward("dataerror");
			}
			catch(Exception e){
				logger.fatal("ERROR , PageChangeConditionUtil. An error has occured, please recheck! ", e);
				throw new ServletException("ERROR , PageChangeConditionUtil - An error has occured, please recheck! "+e.getMessage(),e);
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("The ").append(oldConditionDesc).append(" investigation(");
			//Note: we don't have the new local id here..we'll replace the tag <1> in InvestigationViewLoad
			sb.append(oldLocalId).append(") has been successfully changed to ").append(newConditionDesc).append("(<1>)");
			
			req.getSession().setAttribute(NBSConstantUtil.ConfirmationMsg, sb.toString());
			
			if(NedssUtils.isLocalPath(viewURL) && allowedUrls.contains(viewURL)) res.sendRedirect(viewURL);
			return null;
		}
	
    /**
     * callChangeConditionEJB - sends request to backend to create a new case with changed condition
     * @param investigationUID
     * @param pageForm
     * @param newConditionCd
     * @param newConditionDesc
     * @param request
     * @return new publicHealthCaseUID
     */
	private static Long callChangeConditionEJB(Long oldPHCUid,
			PageProxyVO pageProxyVO, HttpServletRequest req)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {

		Long publicHealthCaseUID = null;

		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		String sMethod = "setChangeConditionInNewCase";
		// String sMethod = "setPageProxyVO";
		ArrayList<?> resultUIDArr = new ArrayList<Object>();

		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(req.getSession());
		}

		Object[] oParams = { NEDSSConstants.CASE, oldPHCUid, pageProxyVO };
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			publicHealthCaseUID = (Long) resultUIDArr.get(0);
		}
		//Delete the old Proxy after change condition
		sMethod = "deletePageProxy";
		Object[] oParams1 = { NEDSSConstants.CASE, oldPHCUid };
		msCommand.processRequest(sBeanJndiName, sMethod, oParams1);
		return publicHealthCaseUID;
	}
	
	private static PageProxyVO proxyVOforChangeConditionEditPage(PageForm form, String newConditionCd, String newConditionDesc,HttpServletRequest req, NBSSecurityObj nbsSecurityObj)throws Exception{
	
		CachedDropDownValues cdv = new CachedDropDownValues();
		String programArea = cdv.getProgramAreaCd(newConditionCd);
		ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", newConditionCd);
		if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
			   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, newConditionCd);
		String investigationFormCd = programAreaVO.getInvestigationFormCd();
		//PageLoadUtil.loadQuestionKeys(investigationFormCd);
		PageActProxyVO pageProxyVO = (PageActProxyVO)form.getPageClientVO().getOldPageProxyVO();
		//answerMap.put(PageConstants.CONDITION_CD, programAreaVO.getConditionCd());
		pageProxyVO.setItDirty(true);
		pageProxyVO.setItNew(false);
		
		//Set PublicHealthCase Information
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
/*
		//NOTIFICATION UPDATE
		Collection<Object> notifVO=pageProxyVO.getTheNotificationSummaryVOCollection();
		if(notifVO!=null && notifVO.size()>0){
			Iterator<Object> ite = notifVO.iterator();
			while (ite.hasNext()){
				NotificationSummaryVO notificationVO = (NotificationSummaryVO)ite.next();
				notificationVO.setRecordStatusCd("APPROVED");
				notificationVO.setItDirty(true);
				notificationVO.setItNew(false);
			}
		}
	*/				
		//PUBLIC HEALTH CASE VO CHANGE CONDITION
		PublicHealthCaseVO phcVO = pageProxyVO.getPublicHealthCaseVO();
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(newConditionDesc);
		phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		phcVO.getThePublicHealthCaseDT().setItDirty(true);
		phcVO.getThePublicHealthCaseDT().setItNew(false);
		phcVO.setItDirty(true);
		phcVO.setItNew(false);
		
		/*
		//PAGE SPECIFIC ANSWERS FOR CHANGE CONDITION
		PageLoadUtil.loadQuestions(investigationFormCd); //load questions for new form
		PageLoadUtil.loadQuestionKeys(investigationFormCd); //get keys - UIDs
		//Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(formCd);
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPamAnswerDTMap());
		if(answerMap!=null)
		{
			Iterator<Object> answerKeys = answerMap.keySet().iterator();
			while(answerKeys.hasNext()){
				Object key = answerKeys.next();
				if(PageLoadUtil.questionKeyMap.containsKey(key)){
					Object answerHolder = (Object)answerMap.get(key);
					String answerClass = answerHolder.getClass().getSimpleName();
					if (answerClass.contains("NbsCaseAnswerDT")) {
						NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)answerHolder;
						//Object theAnswer = (Object) answerMap.get(key);
						answerDT.setItDirty(true);
						answerDT.setItNew(false);
						returnMap.put(key, answerDT);
					} else { //answer is multiselect
						ArrayList<Object> caseAnsList = (ArrayList <Object>) answerHolder;
						Iterator<Object> amIter = caseAnsList.iterator();
						while (amIter.hasNext()) {
							NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)amIter.next();
							answerDT.setItDirty(true);
							answerDT.setItNew(false);
						}
						returnMap.put(key, caseAnsList);	
					}
			} //answer is in new form
		} //answerKeys.hasNext()
		//overlay the answer map with the updated one
		pageProxyVO.getPageVO().setPamAnswerDTMap(returnMap);
		}
		
		//REPEATING ANSWERS FOR CHANGE CONDITION
		TreeMap<Object,Object> repeatingAnswerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPageRepeatingAnswerDTMap());
		if(repeatingAnswerMap !=null)
		{
			 Map<Object,Object> batchMap = PageLoadUtil.findBatchRecords(investigationFormCd, req.getSession()); 
			 if(batchMap != null && batchMap.size() > 0) {
				 Iterator<Entry<Object, Object>>  ite = batchMap.entrySet().iterator();
				  while(ite.hasNext()) {
					  ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
					  Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)ite.next(); 
					  // SubSectionNm = pairs1.getKey().toString();
					  String batch[][] = (String[][])pairs1.getValue();	
				       for(int i=0;i<batch.length;i++){
				    	   ArrayList<NbsCaseAnswerDT> repeatColl = new ArrayList<NbsCaseAnswerDT>();
				    	   ArrayList<NbsCaseAnswerDT> repeatanswerlist = new ArrayList<NbsCaseAnswerDT>();
						   if(batch[i][1] != null)
							 repeatColl = (ArrayList<NbsCaseAnswerDT>)repeatingAnswerMap.get(new Long(batch[i][1]));
						  
						   if(repeatColl != null && repeatColl.size()>0 ){						   
									for(int arrSize=0;arrSize<repeatColl.size();arrSize++){
										  NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();								
										  answerDT = repeatColl.get(arrSize);	
										  	answerDT.setItDirty(true);
											answerDT.setItNew(false);
											repeatanswerlist.add(answerDT);
									}								
						   }
						   if(batch[i][1] != null)
						   returnMap.put(batch[i][1].toString(),repeatanswerlist); 
				       }//for ends
				       
				  }//while ends
				  
			 }
			
		//overlay the answer map with the updated one
		pageProxyVO.getPageVO().setPageRepeatingAnswerDTMap(returnMap);
		}
		
		
		//SET PAGE PROXY VO
		MainSessionCommand msCommand = null;
		if (msCommand == null) {
					MainSessionHolder holder = new MainSessionHolder();
					msCommand = holder.getMainSessionCommand(req.getSession());
				}
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;

		 Object[] oParams = { NEDSSConstants.CASE, pageProxyVO };
		            String sMethod = "setPageProxyVO";
		            msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	*/		
	return pageProxyVO;
}
	/**
     * prepareProxyVOforChangeConditionRetainSameCase - update the proxy vo received with the View Investigation to
     *      change the condition and the question map
     * @param pageForm
     * @param newConditionCd
     * @param newConditionDesc
     * @param request
     * @param nbsSecurityObj
     * @return updated PageActProxyVO
     */
	private static PageProxyVO prepareProxyVOforChangeConditionRetainSameCase(PageForm form, String newConditionCd, String newConditionDesc,HttpServletRequest req, NBSSecurityObj nbsSecurityObj)throws Exception{
			CachedDropDownValues cdv = new CachedDropDownValues();
			String programArea = cdv.getProgramAreaCd(newConditionCd);
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", newConditionCd);
			if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
				   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, newConditionCd);
			String investigationFormCd = programAreaVO.getInvestigationFormCd();
			//PageLoadUtil.loadQuestionKeys(investigationFormCd);
			PageActProxyVO pageProxyVO = (PageActProxyVO)form.getPageClientVO().getOldPageProxyVO();
			//answerMap.put(PageConstants.CONDITION_CD, programAreaVO.getConditionCd());
			pageProxyVO.setItDirty(true);
			pageProxyVO.setItNew(false);
			
			//Set PublicHealthCase Information
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

			//NOTIFICATION UPDATE
			Collection<Object> notifVO=pageProxyVO.getTheNotificationSummaryVOCollection();
			if(notifVO!=null && notifVO.size()>0){
				Iterator<Object> ite = notifVO.iterator();
				while (ite.hasNext()){
					NotificationSummaryVO notificationVO = (NotificationSummaryVO)ite.next();
					notificationVO.setRecordStatusCd("APPROVED");
					notificationVO.setItDirty(true);
					notificationVO.setItNew(false);
				}
			}
						
			//PUBLIC HEALTH CASE VO CHANGE CONDITION
			PublicHealthCaseVO phcVO = pageProxyVO.getPublicHealthCaseVO();
			phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
			phcVO.getThePublicHealthCaseDT().setCdDescTxt(newConditionDesc);
			phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
			phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
			phcVO.getThePublicHealthCaseDT().setItDirty(true);
			phcVO.getThePublicHealthCaseDT().setItNew(false);
			phcVO.setItDirty(true);
			phcVO.setItNew(false);
			PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			//PAGE SPECIFIC ANSWERS FOR CHANGE CONDITION
			pageLoadUtil.loadQuestionKeys(investigationFormCd); //get keys - UIDs
			//Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(formCd);
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPamAnswerDTMap());
			if(answerMap!=null)
			{
				Iterator<Object> answerKeys = answerMap.keySet().iterator();
				while(answerKeys.hasNext()){
					Object key = answerKeys.next();
					if(pageLoadUtil.getQuestionKeyMap().containsKey(key)){
						Object answerHolder = (Object)answerMap.get(key);
						String answerClass = answerHolder.getClass().getSimpleName();
						if (answerClass.contains("NbsCaseAnswerDT")) {
							NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)answerHolder;
							//Object theAnswer = (Object) answerMap.get(key);
							answerDT.setItDirty(true);
							answerDT.setItNew(false);
							returnMap.put(key, answerDT);
						} else { //answer is multiselect
							ArrayList<Object> caseAnsList = (ArrayList <Object>) answerHolder;
							Iterator<Object> amIter = caseAnsList.iterator();
							while (amIter.hasNext()) {
								NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)amIter.next();
								answerDT.setItDirty(true);
								answerDT.setItNew(false);
							}
							returnMap.put(key, caseAnsList);	
						}
				} //answer is in new form
			} //answerKeys.hasNext()
			//overlay the answer map with the updated one
			pageProxyVO.getPageVO().setPamAnswerDTMap(returnMap);
			}
			
			//REPEATING ANSWERS FOR CHANGE CONDITION
			TreeMap<Object,Object> repeatingAnswerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPageRepeatingAnswerDTMap());
			if(repeatingAnswerMap !=null)
			{
				 Map<Object,Object> batchMap = pageLoadUtil.findBatchRecords(investigationFormCd, req.getSession()); 
				 if(batchMap != null && batchMap.size() > 0) {
					 Iterator<Entry<Object, Object>>  ite = batchMap.entrySet().iterator();
					  while(ite.hasNext()) {
						  ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
						  Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)ite.next(); 
						  // SubSectionNm = pairs1.getKey().toString();
						  String batch[][] = (String[][])pairs1.getValue();	
					       for(int i=0;i<batch.length;i++){
					    	   ArrayList<NbsCaseAnswerDT> repeatColl = new ArrayList<NbsCaseAnswerDT>();
					    	   ArrayList<NbsCaseAnswerDT> repeatanswerlist = new ArrayList<NbsCaseAnswerDT>();
							   if(batch[i][1] != null)
								 repeatColl = (ArrayList<NbsCaseAnswerDT>)repeatingAnswerMap.get(new Long(batch[i][1]));
							  
							   if(repeatColl != null && repeatColl.size()>0 ){						   
										for(int arrSize=0;arrSize<repeatColl.size();arrSize++){
											  NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();								
											  answerDT = repeatColl.get(arrSize);	
											  	answerDT.setItDirty(true);
												answerDT.setItNew(false);
												repeatanswerlist.add(answerDT);
										}								
							   }
							   if(batch[i][1] != null)
							   returnMap.put(batch[i][1].toString(),repeatanswerlist); 
					       }//for ends
					       
					  }//while ends
					  
				 }
				
			//overlay the answer map with the updated one
			pageProxyVO.getPageVO().setPageRepeatingAnswerDTMap(returnMap);
			}
			
			//SET PAGE PROXY VO
			MainSessionCommand msCommand = null;
			if (msCommand == null) {
						MainSessionHolder holder = new MainSessionHolder();
						msCommand = holder.getMainSessionCommand(req.getSession());
					}
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;

			 Object[] oParams = { NEDSSConstants.CASE, pageProxyVO };
			            String sMethod = "setPageProxyVO";
			            msCommand.processRequest(sBeanJndiName, sMethod, oParams);
						
		return pageProxyVO;
	}
	
    /**
     * prepareProxyVOforChangeConditionInNewCase - update the proxy vo received with the View Investigation to
     *      prepare for the create new investigation
     * @param pageForm
     * @param newConditionCd
     * @param newConditionDesc
     * @param request
     * @param nbsSecurityObj
     * @return updated PageActProxyVO
     */
	private static PageProxyVO prepareProxyVOforChangeConditionInNewCase(PageForm form, String newConditionCd, String newConditionDesc,HttpServletRequest req, NBSSecurityObj nbsSecurityObj)throws Exception{
			CachedDropDownValues cdv = new CachedDropDownValues();
			String programArea = cdv.getProgramAreaCd(newConditionCd);
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", newConditionCd);
			if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
				   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, newConditionCd);
			String investigationFormCd = programAreaVO.getInvestigationFormCd();
			//PageLoadUtil.loadQuestionKeys(investigationFormCd);
			PageActProxyVO pageProxyVO = (PageActProxyVO)form.getPageClientVO().getOldPageProxyVO();
			//answerMap.put(PageConstants.CONDITION_CD, programAreaVO.getConditionCd());
			pageProxyVO.setItNew(true);
			
			//Set PublicHealthCase Information
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

			//update the PublicHealthCase
			Long caseUid = setPublicHealthCaseForChangeCondition(pageProxyVO, programAreaVO, userId, newConditionDesc);
			
			Long revisionUid = setPersonVOForChangeCondition(pageProxyVO,userId);
			
			//check that the questions are present in the new condition
			setPageSpecifcAnswersForChangeCondition(pageProxyVO,userId,investigationFormCd);
			//set Participations
			setParticipationsForChangeCondition(pageProxyVO, revisionUid, caseUid);
			
			//set EntityColl (PAM Specific)
			setEntitiesForChangeCondition(pageProxyVO, revisionUid, caseUid);
			
			prepareAssociationsChangeCondition(pageProxyVO);
			
			//set the answers for repeating questions in the new condition
			setRepeatingAnswersForChangeCondition(req,pageProxyVO,userId,investigationFormCd);
					
			
		return pageProxyVO;
	}
    /**
     * setPublicHealthCaseForChangeCondition - update the PHC DT,
     * 	act ids and confirmation methods to ready for the create
     * @param pageProxyVO
     * @param ProgramArea
     * @param UserID
     * @param newConditionDesc

     * @param nbsSecurityObj
     * @return publicHealthCaseUID
     */
	private static Long setPublicHealthCaseForChangeCondition(PageActProxyVO pageProxyVO,ProgramAreaVO programAreaVO,String userId,String newConditionDesc)
	{
		PublicHealthCaseVO phcVO = pageProxyVO.getPublicHealthCaseVO();
		phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(-1));
		phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
		phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(userId));
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(newConditionDesc);
		phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
		phcVO.getThePublicHealthCaseDT().setCaseClassCd(null);
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		phcVO.getThePublicHealthCaseDT().setItDirty(false);
		phcVO.getThePublicHealthCaseDT().setItNew(true);
		//setAdditionalPhcAnswersForCreateEdit(phcVO, form);
		phcVO.setItNew(true);
		phcVO.setItDirty(false);
        // ActIdDTs
		Collection<Object>  theActIdDTCollection  = phcVO.getTheActIdDTCollection();
		if (theActIdDTCollection != null && theActIdDTCollection.size() > 0) {
			Iterator<Object> ite = theActIdDTCollection.iterator();
			while (ite.hasNext()) {
				ActIdDT actIDDT = (ActIdDT) ite.next();
				actIDDT.setActUid(null);
				actIDDT.setItNew(true);
				actIDDT.setItDirty(false);
			}
		}
		// Confirmation method
		Collection<Object>  theCMDTCollection  = phcVO.getTheConfirmationMethodDTCollection();
		if (theCMDTCollection != null && theCMDTCollection.size() > 0) {
			Iterator<Object> ite = theCMDTCollection.iterator();
			while (ite.hasNext()) {
				ConfirmationMethodDT cmDT = (ConfirmationMethodDT) ite.next();
				cmDT.setPublicHealthCaseUid(null);
				cmDT.setItNew(true);
				cmDT.setItDirty(false);
			}
		}
		return phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
	}
	
	  /**
     * setPersonVOForChangeCondition - update the thePersonVOCollection to
     *      prepare for the create new investigation
     * @param pageProxyVO
     * @param userId - last changed by
     * @return personUid for the patient
     */
	private static Long setPersonVOForChangeCondition(PageActProxyVO pageProxyVO, String userId) 
	{
		// Subject of Investigation
		Collection<Object> personVOColl  = pageProxyVO.getThePersonVOCollection();
		Collection<Object> returnColl = new ArrayList<Object>();
		Long personUid = 0L;
		if (personVOColl != null && personVOColl.size() > 0) {
			Iterator<Object> ite = personVOColl.iterator();
			while (ite.hasNext()) {
				PersonVO personVO = (PersonVO) ite.next();
				personVO.getThePersonDT().setPersonUid(new Long(-2));
				personVO.setItNew(true);
				personVO.setItDirty(false);
				personVO.getThePersonDT().setItNew(true);
				personVO.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
				if(personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)){
					// Names			
					Collection<Object> names = personVO.getThePersonNameDTCollection();
					if(names!=null && names.size()>0){
						Iterator<Object> Ite = names.iterator();
						PersonNameDT pnDT = (PersonNameDT)Ite.next();
						pnDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
						pnDT.setItNew(true);
						pnDT.setItDirty(false);
						pnDT.setAddTime(new Timestamp(new Date().getTime()));
						pnDT.setAddUserId(Long.valueOf(userId));
					}
					// Addresses and Telephones
					Collection<Object> elpCollection = personVO.getTheEntityLocatorParticipationDTCollection();
					if (elpCollection != null && elpCollection.size() > 0) {
						Iterator<Object> Ite = elpCollection.iterator();
						EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) Ite
							.next();
						elpDT.setItNew(true);
						elpDT.setItDirty(false);
						elpDT.setAddTime(new Timestamp(new Date().getTime()));
						elpDT.setAddUserId(Long.valueOf(userId));
						elpDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
					if(elpDT.getClassCd().equals(NEDSSConstants.POSTAL)){
						PostalLocatorDT plDT = elpDT.getThePostalLocatorDT();
						if (plDT != null) {
							plDT.setPostalLocatorUid(null);
							plDT.setItNew(true);
							plDT.setItDirty(false);
							plDT.setAddTime(new Timestamp(new Date().getTime()));
							plDT.setAddUserId(Long.valueOf(userId));
						}
					}
					else if (elpDT.getClassCd().equals(NEDSSConstants.TELE)){
						TeleLocatorDT teleDT= elpDT.getTheTeleLocatorDT();
						teleDT.setTeleLocatorUid(null);
						teleDT.setItNew(true);
						teleDT.setItDirty(false);
						teleDT.setAddTime(new Timestamp(new Date().getTime()));
						teleDT.setAddUserId(Long.valueOf(userId));
						}
					}
					// Race Information
					Collection<Object> raceCollection = personVO.getThePersonRaceDTCollection();
					if (raceCollection != null && raceCollection.size() > 0) {
						Iterator<Object> Ite = raceCollection.iterator();
						while (Ite.hasNext()) {
							PersonRaceDT raceDT = (PersonRaceDT) Ite.next();
							raceDT.setItNew(true);
							raceDT.setItDelete(false);
							raceDT.setItDirty(false);
							raceDT.setPersonUid(personVO.getThePersonDT()
								.getPersonUid());
							raceDT.setAddTime(new Timestamp(new Date().getTime()));
							raceDT.setAddUserId(Long.valueOf(userId));
						}
					}
					// ID Information
					Collection<Object> idCollection = personVO
						.getTheEntityIdDTCollection();
					if (idCollection != null && idCollection.size() > 0) {
						Iterator<Object> Ite = idCollection.iterator();
						while (Ite.hasNext()) {
							EntityIdDT iddt = (EntityIdDT) Ite.next();
							iddt.setAddTime(new Timestamp(new Date().getTime()));
							iddt.setAddUserId(Long.valueOf(userId));
							iddt.setLastChgTime(new Timestamp(new Date().getTime()));
							iddt.setRecordStatusTime(new Timestamp(new Date()
								.getTime()));
							iddt.setStatusTime(new Timestamp(new Date().getTime()));
							iddt.setEntityUid(personVO.getThePersonDT().getPersonUid());
							iddt.setItNew(true);
							iddt.setItDirty(false);
						}
					}
				 
					// setEthnicityForCreate(personVO, form.getPageClientVO().getAnswerMap(), userId);
					//  setRaceForCreate(personVO, form.getPageClientVO(), proxyVO, userId);
					//  setIds(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId);
					returnColl.add(personVO);
					personUid = personVO.getThePersonDT().getPersonUid();
				
				} //for the Patient
				pageProxyVO.setThePersonVOCollection(returnColl);
			}
		}
		
		return personUid;
	}
	  /**
     * setPageSpecifcAnswersForChangeCondition - the new condition may not use the same template as the old condition
     *     so some of the answers may not be relevant for the new condition. If they are not on the new form, they are 
     *     dropped. This method culls the answer map of answers that are not in the new form.
     * @param userid
     * @param form code for the new condition
     * 
     */
	private static void setPageSpecifcAnswersForChangeCondition(PageActProxyVO pageProxyVO, String userId, String formCd)throws Exception{
		PageLoadUtil pageLoadUtil  = new PageLoadUtil();
		pageLoadUtil.loadQuestionKeys(formCd); //get keys - UIDs
		//Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(formCd);
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPamAnswerDTMap());
		if(answerMap!=null)
		{
			Iterator<Object> answerKeys = answerMap.keySet().iterator();
			while(answerKeys.hasNext()){
				Object key = answerKeys.next();
				if(pageLoadUtil.getQuestionKeyMap().containsKey(key)){
					Object answerHolder = (Object)answerMap.get(key);
					String answerClass = answerHolder.getClass().getSimpleName();
					if (answerClass.contains("NbsCaseAnswerDT")) {
						NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)answerHolder;
						//Object theAnswer = (Object) answerMap.get(key);
						answerDT.setAddTime(new Timestamp(new Date().getTime()));
						answerDT.setAddUserId(Long.valueOf(userId));
						answerDT.setLastChgUserId(Long.valueOf(userId));
						answerDT.setActUid(null);
						answerDT.setItDirty(false);
						answerDT.setItNew(true);
						returnMap.put(key, answerDT);
					} else { //answer is multiselect
						ArrayList<Object> caseAnsList = (ArrayList <Object>) answerHolder;
						Iterator<Object> amIter = caseAnsList.iterator();
						while (amIter.hasNext()) {
							NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)amIter.next();
							answerDT.setAddTime(new Timestamp(new Date().getTime()));
							answerDT.setAddUserId(Long.valueOf(userId));
							answerDT.setLastChgUserId(Long.valueOf(userId));
							answerDT.setActUid(null);
							answerDT.setItDirty(false);
							answerDT.setItNew(true);
						}
						returnMap.put(key, caseAnsList);	
					}
			} //answer is in new form
		} //answerKeys.hasNext()
		//overlay the answer map with the updated one
		pageProxyVO.getPageVO().setPamAnswerDTMap(returnMap);
		} //answerMap != null
	}
	/**
     * setParticipationsChangeCondition - walk thru act entities
     *  and set it new
     * @param pageProxyVO
     * @param revisionUid
     * @param caseUid
     */
	private static void setParticipationsForChangeCondition(PageActProxyVO pageProxyVO, Long revisionUid, Long phcUid){
		Collection<Object> participationCollection = pageProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		if(participationCollection!=null && participationCollection.size()>0){
			Iterator<Object> Ite = participationCollection.iterator();
			while(Ite.hasNext()){
				ParticipationDT pDT = (ParticipationDT)Ite.next(); 
				if(pDT!=null && pDT.getTypeCd()!=null && pDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT))
					pDT.setSubjectEntityUid(new Long(revisionUid));
				pDT.setActUid(new Long(phcUid));
				pDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
				pDT.setItNew(true);
				pDT.setItDirty(false);
			}
		}
		pageProxyVO.setTheParticipationDTCollection(participationCollection);
		pageProxyVO.getPublicHealthCaseVO().setTheParticipationDTCollection(null);
	}
	
	/**
     * setEntitiesChangeCondition - walk thru act entities
     *  and set it new
     * @param pageProxyVO
     * @param revisionUid
     * @param caseUid
     */
	private static void setEntitiesForChangeCondition(PageActProxyVO pageProxyVO, Long revisionUid,Long caseUid){
		Collection<Object> actEntityCollection  = pageProxyVO.getPageVO().getActEntityDTCollection();
		if(actEntityCollection!=null && actEntityCollection.size()>0){
			Iterator<Object> ite = actEntityCollection.iterator();
			while(ite.hasNext()){
				NbsActEntityDT entityDT = (NbsActEntityDT)ite.next();
				if(entityDT!=null && entityDT.getTypeCd()!=null && entityDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT))
					entityDT.setEntityUid(new Long(revisionUid));
				entityDT.setActUid(new Long(caseUid));
				entityDT.setItNew(true);
				entityDT.setItDirty(false);
				entityDT.setItDelete(false);
			}
		}
	}
	 /**
     * prepareAssociationsChangeCondition - walk thru associations
     *  and set it touched and associated.
     * @param pageProxyVO
     */
	private static void prepareAssociationsChangeCondition(PageActProxyVO pageProxyVO){
		
		//null any notifications
		pageProxyVO.setTheNotificationSummaryVOCollection(null );
		
		
		//Observation Collection
		
		Collection<Object> labsummary = pageProxyVO.getTheLabReportSummaryVOCollection();
		if(labsummary!=null && labsummary.size()>0){
			Iterator<Object> ite = labsummary.iterator();
			while (ite.hasNext()){
				LabReportSummaryVO lrsVO = (LabReportSummaryVO)ite.next();
				lrsVO.setItAssociated(true);
				lrsVO.setItTouched(true);
			}
		}
		Collection<Object> morbsummary = pageProxyVO.getTheMorbReportSummaryVOCollection();
		if(morbsummary!=null && morbsummary.size()>0){
			Iterator<Object> ite = morbsummary.iterator();
			while (ite.hasNext()){
				MorbReportSummaryVO mrsVO = (MorbReportSummaryVO)ite.next();
				mrsVO.setItAssociated(true);
				mrsVO.setItTouched(true);
			}
		}
		
		Collection<Object> vaccsummary = pageProxyVO.getTheVaccinationSummaryVOCollection();
		if(vaccsummary!=null && vaccsummary.size()>0){
			Iterator<Object> ite = vaccsummary.iterator();
			while (ite.hasNext()){
				VaccinationSummaryVO vaccVO = (VaccinationSummaryVO)ite.next();
				vaccVO.setIsTouched(true);
				vaccVO.setIsAssociated(true);
			}
		}
		
		Collection<Object> docsummary = pageProxyVO.getTheDocumentSummaryVOCollection();
		if(docsummary!=null && docsummary.size()>0){
			Iterator<Object> ite = docsummary.iterator();
			while (ite.hasNext()){
				SummaryDT docDT = (SummaryDT)ite.next();
				docDT.setItAssociated(true);
				docDT.setItTouched(true);
			}
		}
		Collection<Object> tresummary = pageProxyVO.getTheTreatmentSummaryVOCollection();
		if(tresummary!=null && tresummary.size()>0){
			Iterator<Object> ite = tresummary.iterator();
			while (ite.hasNext()){
				TreatmentSummaryVO treVO = (TreatmentSummaryVO)ite.next();
				treVO.setIsAssociated(true);
				treVO.setIsTouched(true);
			}
		}
	}
	
	
	
	
	  /**
     * addNewNoteChangeCondition - add a note that the condition was changed from XXX to YYY.
     * @param session
     * @param nbsSecurityObj
     * @param newPHCUid
     * @param oldCond
     * @param oldLocalId
     * @param newDesc
     */
	public static void addNewNoteForChangeCondition(HttpSession session, NBSSecurityObj nbsSecurityObj, Long publicHealthCaseUid, String oldConditionDesc, String newConditionDesc) {		
        NBSNoteDT nbsNoteDT = new NBSNoteDT();
        nbsNoteDT.setItNew(true);
        
        try {
        	nbsNoteDT.setNbsNoteUid(new Long(-1));
        	String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
        	nbsNoteDT.setNoteParentUid(publicHealthCaseUid);
        	nbsNoteDT.setNote("The condition associated with this case investigation was changed from " + oldConditionDesc +
        		" to " + newConditionDesc + "." );
        	nbsNoteDT.setAddTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setAddUserId(Long.valueOf(userId));
        	nbsNoteDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
        	nbsNoteDT.setLastChgUserId(Long.valueOf(userId));
        	nbsNoteDT.setLastChgTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setTypeCd(NEDSSConstants.INVESTIGATION_CD);  //SNOMED code for Public Health Case Investigation
        	nbsNoteDT.setPrivateIndCd(NEDSSConstants.FALSE);
        // store note in DB
        Long resultUid = saveNewNote(nbsNoteDT, session);
        if(resultUid != null && resultUid.longValue() > 0)
        	logger.debug("New note added for change condition");
    } catch (Exception e) {
        logger.error("Change Condition - Error storing note = " + e);
    }
}
	  /**
     * saveNewNote - call the back-end to save the new note
     * @param noteDT
     * @param session
     */
private static Long saveNewNote(NBSNoteDT dt, HttpSession session) throws Exception 
{
	MainSessionCommand msCommand = null;
	Long resultUid = null;
	try {
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		String sMethod = "setNBSNote";
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		Object[] oParams = { dt };
		Object returnObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, session);
		resultUid = (Long)returnObj;
	} catch (Exception ex) {
		logger.error("Error while calling the back end method to add a note for Change Condition -> " + ex.toString());	
		throw new ServletException("An error occurred storing the new note for the change condition. " +  ex.getMessage());
	}
	
	return resultUid;	
}   

/**
 * setPageSpecifcAnswersForChangeCondition - the new condition may not use the same template as the old condition
 *     so some of the answers may not be relevant for the new condition. If they are not on the new form, they are 
 *     dropped. This method culls the answer map of answers that are not in the new form.
 * @param userid
 * @param form code for the new condition
 * 
 */
private static void setRepeatingAnswersForChangeCondition(HttpServletRequest req,PageActProxyVO pageProxyVO, String userId, String formCd)throws Exception{
	PageLoadUtil pageLoadUtil = new PageLoadUtil();
	pageLoadUtil.loadQuestionKeys(formCd); //get keys - UIDs
	//Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(formCd);
	Map<Object,Object> returnMap = new HashMap<Object,Object>();
	TreeMap<Object,Object> repeatingAnswerMap = new TreeMap<Object,Object>(pageProxyVO.getPageVO().getPageRepeatingAnswerDTMap());
	if(repeatingAnswerMap !=null)
	{
		 Map<Object,Object> batchMap = pageLoadUtil.findBatchRecords(formCd, req.getSession()); 
		 if(batchMap != null && batchMap.size() > 0) {
			 Iterator<Entry<Object, Object>>  ite = batchMap.entrySet().iterator();
			  while(ite.hasNext()) {
				  ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
				  Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)ite.next(); 
				  // SubSectionNm = pairs1.getKey().toString();
				  String batch[][] = (String[][])pairs1.getValue();	
			       for(int i=0;i<batch.length;i++){
			    	   ArrayList<NbsCaseAnswerDT> repeatColl = new ArrayList<NbsCaseAnswerDT>();
			    	   ArrayList<NbsCaseAnswerDT> repeatanswerlist = new ArrayList<NbsCaseAnswerDT>();
					   if(batch[i][1] != null)
						 repeatColl = (ArrayList<NbsCaseAnswerDT>)repeatingAnswerMap.get(new Long(batch[i][1]));
					  
					   if(repeatColl != null && repeatColl.size()>0 ){						   
								for(int arrSize=0;arrSize<repeatColl.size();arrSize++){
									  NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();								
									  answerDT = repeatColl.get(arrSize);	
									  answerDT.setAddTime(new Timestamp(new Date().getTime()));
										answerDT.setAddUserId(Long.valueOf(userId));
										answerDT.setLastChgUserId(Long.valueOf(userId));
										answerDT.setActUid(null);
										answerDT.setItDirty(false);
										answerDT.setItNew(true);
										repeatanswerlist.add(answerDT);
								}								
					   }
					   if(batch[i][1] != null)
					   returnMap.put(batch[i][1].toString(),repeatanswerlist); 
			       }//for ends
			       
			  }//while ends
			 
			 
			 
		 }
		/*Iterator<Object> answerKeys = repeatingAnswerMap.keySet().iterator();
		while(answerKeys.hasNext()){
			Object key = answerKeys.next();
			if(PageLoadUtil.questionKeyMap.containsKey(key)){
				Object answerHolder = (Object)repeatingAnswerMap.get(key);
				String answerClass = answerHolder.getClass().getSimpleName();
				if (answerClass.contains("NbsCaseAnswerDT")) {
					NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)answerHolder;
					//Object theAnswer = (Object) answerMap.get(key);
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setActUid(null);
					answerDT.setItDirty(false);
					answerDT.setItNew(true);
					returnMap.put(key, answerDT);
				} 
		} //answer is in new form
	} //answerKeys.hasNext()*/
	//overlay the answer map with the updated one
	pageProxyVO.getPageVO().setPageRepeatingAnswerDTMap(returnMap);
	} //answerMap != null
}
	
		
	
} //end class

