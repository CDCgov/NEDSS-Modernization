package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.util.CaseCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class to submit CREATE / VIEW / EDIT 'INVESTIGATION' Type Pages added from Builder
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * CaseLoadUtil.java
 * Jan 21, 2010
 * @version 1.0
 */

public class CaseStoreUtil {

	static final LogUtils logger = new LogUtils(CaseStoreUtil.class.getName());
	static final CachedDropDownValues srtc = new CachedDropDownValues();

	/**
	 * createHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static PageProxyVO createHandler(PageForm form, HttpServletRequest req) throws Exception {

		PageActProxyVO proxyVO = null;
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		proxyVO = CaseCreateHelper.create(form, req);
		if (form.getErrorList().size() == 0) {

			String sCurrentTask = NBSContext.getCurrentTask(req.getSession());
			int tempID = -1;
			setEntitiesForCreate(proxyVO, form, tempID, userId, req);
			if(sCurrentTask.equals("CreateInvestigation10") ){
				CaseCreateHelper.createActRelationshipForDoc(sCurrentTask,proxyVO, req);
			}
			// send Proxy to EJB To Persist
			Long phcUid = sendProxyToPageEJB(proxyVO, req, sCurrentTask);
			// Context
			setContextForCreate(proxyVO, phcUid, getProgAreaVO(req.getSession()), req.getSession());
		} else {
			form.setPageTitle(NBSPageConstants.CREATE_VARICELLA, req);
		}
		return proxyVO;
	}

	
	public static ProgramAreaVO getProgAreaVO(HttpSession session) {

		String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
		String programArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
		ProgramAreaVO programAreaVO = null;

		programAreaVO = srtc.getProgramAreaCondition("('" +programArea + "')", conditionCd);
		return programAreaVO;
	}
	
	public static void setContextForCreate(PageProxyVO proxyVO, Long phcUid, ProgramAreaVO programArea, HttpSession session) {
		// context store
		String investigationJurisdiction = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
		NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, phcUid.toString());
		NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
		String progArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
		NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, progArea);
	}	
	/**
	 * 
	 * @param proxyVO
	 * @param request
	 * @param sCurrentTask
	 * @return
	 * @throws NEDSSAppConcurrentDataException
	 * @throws Exception
	 */
	public static Long sendProxyToPageEJB(PageProxyVO proxyVO, HttpServletRequest request, String sCurrentTask) throws NEDSSAppConcurrentDataException, Exception {
		
		HttpSession session = request.getSession();
		MainSessionCommand msCommand = null;
		Long publicHealthCaseUID = null;
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		if (sCurrentTask != null && (sCurrentTask.equals("CreateInvestigation2")
				|| sCurrentTask.equals("CreateInvestigation3")
				|| sCurrentTask.equals("CreateInvestigation4")
				|| sCurrentTask.equals("CreateInvestigation5")
				|| sCurrentTask.equals("CreateInvestigation6")
				|| sCurrentTask.equals("CreateInvestigation7")
				|| sCurrentTask.equals("CreateInvestigation8")
				|| sCurrentTask.equals("CreateInvestigation9"))) {

			String sMethod = "setPageProxyWithAutoAssoc";

		    Object sObservationUID = NBSContext.retrieve(session, NBSConstantUtil.DSObservationUID);
		    Object observationTypeCd = NBSContext.retrieve(session, NBSConstantUtil.DSObservationTypeCd);
		    Long DSObservationUID = new Long(sObservationUID.toString());
		    Object[]  oParams = { NEDSSConstants.CASE, proxyVO, DSObservationUID, observationTypeCd.toString()};
		      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		        publicHealthCaseUID = (Long) resultUIDArr.get(0);
		}else{
			Object[] oParams = { NEDSSConstants.CASE, proxyVO };
			String sMethod = "setPageProxyVO";
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				publicHealthCaseUID = (Long) resultUIDArr.get(0);
			}

		}
		return publicHealthCaseUID;
	}	
	
	/**
	 * editHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static PageProxyVO editHandler(PageForm form, HttpServletRequest req)
			throws Exception {
		PageProxyVO proxyVO = null;
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		proxyVO = CaseCreateHelper.editHandler(form, req);
		if (form.getErrorList().size() == 0) {
			
			PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();

			setEntitiesForEdit((PageActProxyVO)proxyVO, form, phcVO, userId,req);
			
			sendProxyToPageEJB(proxyVO, req, null);
		} else {
			form.setPageTitle(NBSPageConstants.EDIT_VARICELLA, req);
		}
		return proxyVO;
	}


    /**
     * setEntitiesForEdit creates Participations' and NBSActEntities' with types of PRVs and ORGs associated with Varicella
     * @param proxyVO
     * @param form
     * @param revisionPatientUID
     * @param userId
     * @param request
     */
	private static void setEntitiesForCreate(PageActProxyVO proxyVO, PageForm form, int revisionPatientUID, String userId, HttpServletRequest request) {
		//Investigator
		String prvUid = form.getAttributeMap().get("INV207Uid") == null ? "" : (String) form.getAttributeMap().get("INV207Uid");		
		CaseCreateHelper._setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_INVESTIGATOR, "PSN");
		
		//Reporting Provider
		prvUid = form.getAttributeMap().get("INV225Uid") == null ? "" : (String) form.getAttributeMap().get("INV225Uid");		
		CaseCreateHelper._setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_REPORTER, "PSN");

		//Physician
		prvUid = form.getAttributeMap().get("INV247Uid") == null ? "" : (String) form.getAttributeMap().get("INV247Uid");		
		CaseCreateHelper._setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_PHYSICIAN, "PSN");
		
		//Reporting Hospital
		String strOrganizationUID = form.getAttributeMap().get("INV218Uid") == null ? "" : (String) form.getAttributeMap().get("INV218Uid");
		CaseCreateHelper._setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, strOrganizationUID, NEDSSConstants.PHC_REPORTING_SOURCE, "ORG");
		
		//Hospital Information
		strOrganizationUID = form.getAttributeMap().get("INV233Uid") == null ? "" : (String) form.getAttributeMap().get("INV233Uid");
		CaseCreateHelper._setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, strOrganizationUID, NEDSSConstants.HospOfADT, "ORG");
		
	}
	/**
	 * setEntitiesForEdit creates Participations' and NBSActEntities' with types of PRVs and ORGs associated with Varicella
	 * @param proxyVO
	 * @param form
	 * @param phcVO
	 * @param userId
	 * @param request
	 */
    private static void setEntitiesForEdit(PageActProxyVO proxyVO, PageForm form, PublicHealthCaseVO phcVO, String userId, HttpServletRequest request) {
    	
      //investigator
      String strInvestigatorUID = form.getAttributeMap().get("INV207Uid") == null ? null : (String) form.getAttributeMap().get("INV207Uid");
      CaseCreateHelper.createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_INVESTIGATOR, "PSN", userId);
      //Provider
      strInvestigatorUID = form.getAttributeMap().get("INV225Uid") == null ? null : (String) form.getAttributeMap().get("INV225Uid");
      CaseCreateHelper.createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_REPORTER, "PSN", userId);
      //Physician
      strInvestigatorUID = form.getAttributeMap().get("INV247Uid") == null ? null : (String) form.getAttributeMap().get("INV247Uid");
      CaseCreateHelper.createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_PHYSICIAN, "PSN", userId);

      //Organization    
      String strOrganizationUID = form.getAttributeMap().get("INV218Uid") == null ? null : (String) form.getAttributeMap().get("INV218Uid");
      CaseCreateHelper.createOrDeleteParticipation(strOrganizationUID, form, proxyVO, NEDSSConstants.PHC_REPORTING_SOURCE, "ORG", userId);
      //Hospital      
      strOrganizationUID = form.getAttributeMap().get("INV233Uid") == null ? null : (String) form.getAttributeMap().get("INV233Uid");
      CaseCreateHelper.createOrDeleteParticipation(strOrganizationUID, form, proxyVO, NEDSSConstants.HospOfADT, "ORG", userId);
        
    }
    
}
