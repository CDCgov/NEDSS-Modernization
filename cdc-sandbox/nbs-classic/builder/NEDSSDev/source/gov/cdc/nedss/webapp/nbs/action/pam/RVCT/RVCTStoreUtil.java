package gov.cdc.nedss.webapp.nbs.action.pam.RVCT;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to construct PAMProxyVO out of PamClientVO and delegates to PamProxyEJB for persistance.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * RVCTStoreUtil.java
 * Aug 7, 2008
 * @version
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class RVCTStoreUtil extends PamStoreUtil {

	static final LogUtils logger = new LogUtils(RVCTStoreUtil.class.getName());

	/**
	 * createHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PamProxyVO createHandler(PamForm form, HttpServletRequest req) throws Exception {
		
		PamProxyVO proxyVO = null;
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		PamStoreUtil pamStoreUtil = new PamStoreUtil();
		proxyVO = pamStoreUtil.createHandler(form, req);
		if (form.getErrorList().size() == 0) {

			String sCurrentTask = NBSContext.getCurrentTask(req.getSession());
			// RVCT Specific Participations
			int tempID = -1;
			setEntitiesForCreate(proxyVO, form, tempID,userId, req);
			if(sCurrentTask.equals("CreateInvestigation10") ){
				createActRelationshipForDoc(sCurrentTask,proxyVO, req);
			}
			// send Proxy to EJB To Persist
			Long phcUid = sendProxyToPamEJB(proxyVO, req, sCurrentTask);
				
			// Context
			pamStoreUtil.setContextForCreate(proxyVO, phcUid, getProgAreaVO(req.getSession()), req.getSession());
		} else {
			form.setPageTitle(NBSPageConstants.CREATE_RVCT, req);
		}
		req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
		return proxyVO;
	}
	
	/**
	 * editHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PamProxyVO editHandler(PamForm form, HttpServletRequest req)
			throws Exception {
		PamProxyVO proxyVO = null;
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		
		PamStoreUtil pamStoreUtil = new PamStoreUtil();
		proxyVO = pamStoreUtil.editHandler(form, req);
		if (form.getErrorList().size() == 0) {

			PublicHealthCaseVO phcVO = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO();
			// RVCT Edit Participations
			setEntitiesForEdit(proxyVO, form, phcVO, userId,req);
			sendProxyToPamEJB(proxyVO, req, null);
		} else {
			form.setPageTitle(NBSPageConstants.EDIT_RVCT, req);
		}
		req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
		return proxyVO;
	}

	/**
	 * viewHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
    public static void viewHandler(PamForm form, HttpServletRequest req) throws Exception {
    		PamStoreUtil.viewHandler(form, req);
    }	
	
    /**
     * setEntitiesForEdit creates Participations' and NBSActEntities' with types of PRVs and ORGs associated with Tuberculosis
     * @param proxyVO
     * @param form
     * @param revisionPatientUID
     * @param userId
     * @param request
     */
	private void setEntitiesForCreate(PamProxyVO proxyVO, PamForm form, int revisionPatientUID, String userId, HttpServletRequest request) {
		
		//Investigator
		String prvUid = form.getAttributeMap().get("INV207Uid") == null ? "" : (String) form.getAttributeMap().get("INV207Uid");		
		_setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_INVESTIGATOR, "PSN");
		
		//Reporting Provider
		prvUid = form.getAttributeMap().get("INV225Uid") == null ? "" : (String) form.getAttributeMap().get("INV225Uid");		
		_setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_REPORTER, "PSN");

		//Physician
		prvUid = form.getAttributeMap().get("INV247Uid") == null ? "" : (String) form.getAttributeMap().get("INV247Uid");		
		_setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, prvUid, NEDSSConstants.PHC_PHYSICIAN, "PSN");
		
		//Reporting Hospital
		String strOrganizationUID = form.getAttributeMap().get("INV218Uid") == null ? "" : (String) form.getAttributeMap().get("INV218Uid");
		_setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, strOrganizationUID, NEDSSConstants.PHC_REPORTING_SOURCE, "ORG");
		
		//Hospital Information
		strOrganizationUID = form.getAttributeMap().get("INV233Uid") == null ? "" : (String) form.getAttributeMap().get("INV233Uid");
		_setEntitiesForCreate(proxyVO, form, revisionPatientUID, userId, request, strOrganizationUID, NEDSSConstants.HospOfADT, "ORG");
		
	}
	
	/**
	 * setEntitiesForEdit edits Participations' and NBSActEntities' with types of PRVs and ORGs associated with Tuberculosis
	 * @param proxyVO
	 * @param form
	 * @param phcVO
	 * @param userId
	 * @param request
	 */
    private void setEntitiesForEdit(PamProxyVO proxyVO, PamForm form, PublicHealthCaseVO phcVO, String userId, HttpServletRequest request) {
    	
        //investigator
        String strInvestigatorUID = form.getAttributeMap().get("INV207Uid") == null ? null : (String) form.getAttributeMap().get("INV207Uid");
        createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_INVESTIGATOR, "PSN", userId);
        //Provider
        strInvestigatorUID = form.getAttributeMap().get("INV225Uid") == null ? null : (String) form.getAttributeMap().get("INV225Uid");
        createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_REPORTER, "PSN", userId);
        //Physician
        strInvestigatorUID = form.getAttributeMap().get("INV247Uid") == null ? null : (String) form.getAttributeMap().get("INV247Uid");
        createOrDeleteParticipation(strInvestigatorUID, form, proxyVO, NEDSSConstants.PHC_PHYSICIAN, "PSN", userId);

        //Organization    
        String strOrganizationUID = form.getAttributeMap().get("INV218Uid") == null ? null : (String) form.getAttributeMap().get("INV218Uid");
        createOrDeleteParticipation(strOrganizationUID, form, proxyVO, NEDSSConstants.PHC_REPORTING_SOURCE, "ORG", userId);
        //Hospital      
        strOrganizationUID = form.getAttributeMap().get("INV233Uid") == null ? null : (String) form.getAttributeMap().get("INV233Uid");
        createOrDeleteParticipation(strOrganizationUID, form, proxyVO, NEDSSConstants.HospOfADT, "ORG", userId);
          
      }
}
