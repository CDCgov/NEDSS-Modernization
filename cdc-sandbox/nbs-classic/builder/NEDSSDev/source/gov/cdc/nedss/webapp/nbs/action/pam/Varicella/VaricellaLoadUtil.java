package gov.cdc.nedss.webapp.nbs.action.pam.Varicella;

import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class to build Varicella specific PamClientVO from PAMProxyVO(backend VO)
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * VaricellaLoadUtil.java
 * Aug 6, 2008
 * @version
 * 
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class VaricellaLoadUtil extends PamLoadUtil{

	static final LogUtils logger = new LogUtils(VaricellaLoadUtil.class.getName());
	/**
	 * This method retrieves the Patient Revision Information on the create load instance from the backend,
	 * constructs and returns a PAMClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PamClientVO.PamClientVO
	 */
	public void createLoadUtil(PamForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("SupplementalInfo");
		PamLoadUtil pamLoadUtil = new PamLoadUtil();
		pamLoadUtil.createLoadUtil(form, request);
		form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
		form.getPamClientVO().setAnswer(PamConstants.DEM_DATA_AS_OF, StringUtils.formatDate(new Timestamp(new Date().getTime())));
		form.setPageTitle(NBSPageConstants.CREATE_VARICELLA, request);
		form.getAttributeMap().put("INV156_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
		form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
		if(( sCurrentTask.equals("CreateInvestigation10")  || sCurrentTask.equals("CreateInvestigation11")) && NBSContext.retrieve(request.getSession(),"DSDocumentUID")!=null){
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			pamLoadUtil.setJurisdictionForCreate(form, nbsSecurityObj, request.getSession());
			PamProxyVO pamProxyVO= new PamProxyVO();
			Long DSDocumentUID=(Long)NBSContext.retrieve(request.getSession(),"DSDocumentUID");
			InvestigationUtil investigationUtil = new InvestigationUtil();
			Object object = investigationUtil.createProxyObject(DSDocumentUID,"10030", nbsSecurityObj);
			if(object!=null){
				pamProxyVO = (PamProxyVO)object;//PageActProxyVO
				investigationUtil.setEntities(pamProxyVO, nbsSecurityObj, request);
				pamProxyVO.getPublicHealthCaseVO().setTheParticipationDTCollection(pamProxyVO.getTheParticipationDTCollection());
				this.setCommonAnswersForViewEdit(form, pamProxyVO, request);
				  //Pam Specific Answers
				 	setMSelectCBoxAnswersForViewEdit(form, updateCreateMapWithQIds(pamProxyVO.getPamVO().getPamAnswerDTMap()), pamLoadUtil);
					String programAreaCd = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea);
					pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setProgAreaCd(programAreaCd);
					this.setInvestigationInformationOnForm(form, pamProxyVO); 
					this.fireRulesOnEditLoad(form, request, pamLoadUtil);
					this._loadEntities(form, pamProxyVO, request);
					  
			}else{
				String programAreaCd = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea);
				pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setProgAreaCd(programAreaCd);
				this.setInvestigationInformationOnForm(form, pamProxyVO); 
				this.fireRulesOnCreateLoad(form, request);
			}
		}
	}

	/**
	 * viewLoadUtil method retrieves the PamProxyVO from the EJB and sets to
	 * PamClientVO, attribute of PamForm
	 *
	 * @param form
	 * @param request
	 */
	  public PamProxyVO viewLoadUtil(PamForm form, HttpServletRequest request) throws Exception {
		  
		  PamLoadUtil pamLoadUtil = new PamLoadUtil();
		  PamProxyVO proxyVO = pamLoadUtil.viewLoadUtil(form, request);

		  //Check if any session attribute "SupplementalInfo" exists, if so, set default tabid to Supplemental Tab.
		  String tabId = request.getSession().getAttribute("SupplementalInfo") == null ? "" : (String) request.getSession().getAttribute("SupplementalInfo");
		  if(!tabId.equals("")) {
			  form.setTabId("2");
			  //Remove attribute from session
			  request.getSession().removeAttribute("SupplementalInfo");
		  } else
			  form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
		  this._loadEntities(form, proxyVO, request);
		  
		  //this.setQuestionMap(pamLoadUtil.getQuestionMap());
	//	  this.setQuestionKeyMap(pamLoadUtil.getQuestionKeyMap());
		  
		  this.setMSelectCBoxAnswersForViewEdit(form, pamLoadUtil.updateMapWithQIds(proxyVO.getPamVO().getPamAnswerDTMap()), pamLoadUtil);
		  this.fireRulesOnViewLoad(form, pamLoadUtil);
		  form.setPageTitle(NBSPageConstants.VIEW_VARICELLA, request);
		  request.setAttribute("PageTitle",NBSPageConstants.VIEW_VARICELLA);
		  if(form.getPamClientVO().getAnswer("INV154")==null)
			  form.getAttributeMap().put("INV156_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
		  else
			  form.getAttributeMap().put("INV156_STATE", form.getPamClientVO().getAnswer("INV154"));
		  if(form.getPamClientVO().getAnswer("INV154")==null)
		  	  form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
		  else
		  	  form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getPamClientVO().getAnswer("INV154")));
		  return proxyVO;
	  }

	/**
	 * editLoadUtil method retrieves the PamProxyVO from the EJB and sets to PamClientVO, attribute of PamForm
	 * @param form
	 * @param request
	 */
	  public PamProxyVO editLoadUtil(PamForm form, HttpServletRequest request) throws Exception {

		  PamLoadUtil pamLoadUtil = new PamLoadUtil();
		  PamProxyVO proxyVO = pamLoadUtil.editLoadUtil(form, request);
		  this._loadEntities(form, proxyVO, request);
		  this.setMSelectCBoxAnswersForViewEdit(form, pamLoadUtil.updateMapWithQIds(proxyVO.getPamVO().getPamAnswerDTMap()), pamLoadUtil);		  
		  this.fireRulesOnEditLoad(form, request, pamLoadUtil);
		  form.setPageTitle(NBSPageConstants.EDIT_VARICELLA, request);
		  if(form.getPamClientVO().getAnswer("INV154")==null)
		  	  form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
		  else
		  	  form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getPamClientVO().getAnswer("INV154")));
		  return proxyVO;

	  }

	  /**
	   * setEntities loads PRVs and ORGs associated with Varicella Case
	   * @param form
	   * @param proxyVO
	   * @param request
	   */
	  public static void _loadEntities(PamForm form, PamProxyVO proxyVO, HttpServletRequest request) {
		  
		  	//Investigator
			PersonVO investigatorPersonVO = getPersonVO(NEDSSConstants.PHC_INVESTIGATOR, proxyVO);
			if (investigatorPersonVO != null) {
				String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("INV207Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put("INV207SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
			}		  
			//Reporting Provider
			investigatorPersonVO = getPersonVO(NEDSSConstants.PHC_REPORTER, proxyVO);
			if (investigatorPersonVO != null) {
				String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("INV225Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put("INV225SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
			}
			
			//Physician
			investigatorPersonVO = getPersonVO(NEDSSConstants.PHC_PHYSICIAN, proxyVO);
			if (investigatorPersonVO != null) {
				String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("INV247Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put("INV247SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
			}
			
			//Reporting Hospital
			OrganizationVO organizationVO = getOrganizationVO(NEDSSConstants.PHC_REPORTING_SOURCE,proxyVO);
			if (organizationVO != null) {
				String uidSt =  organizationVO.getTheOrganizationDT().getOrganizationUid().toString() + "|" + organizationVO.getTheOrganizationDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("INV218Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put("INV218SearchResult", helper.makeORGDisplayString(organizationVO));
			}
			
		  	//Hospital Information
		  	organizationVO = getOrganizationVO(NEDSSConstants.HospOfADT,proxyVO);
			if (organizationVO != null) {
				String uidSt =  organizationVO.getTheOrganizationDT().getOrganizationUid().toString() + "|" + organizationVO.getTheOrganizationDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("INV233Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put("INV233SearchResult", helper.makeORGDisplayString(organizationVO));
			}			
	  }

	  /**
	   * setVaricellaAnswersForViewEdit retrives Varicella Answers from NBS_Answers and puts in the form.
	   * @param form
	   * @param answerMap
	   */
	  public static void setVaricellaAnswersForViewEdit(PamForm form, Map<Object,Object> answerMap) {

		  PamLoadUtil pamLoadUtil = new PamLoadUtil();
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  pamLoadUtil.setPamSpecificAnswersForViewEdit(form, answerMap, returnMap);
		  form.getPamClientVO().getAnswerMap().putAll(returnMap);

	  }
}
