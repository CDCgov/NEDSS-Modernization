
package gov.cdc.nedss.webapp.nbs.action.investigation.common;

/**
 * 
 * <p>
 * Title: BaseCreateLoad
 * </p>
 * <p>
 * Description: This is base action class for create investigation. All other
 * Create Investigations extends this class.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Computer Science Corporation
 * </p>
 * 
 * @author Shailender Rachamalla
 */

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class BaseCreateLoad extends CommonAction {

	static final LogUtils logger = new LogUtils(BaseCreateLoad.class.getName());

	/**
	 * empty constructor
	 */
	public BaseCreateLoad() {
	}

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an ActionForward instance describing where and how control should
	 * be forwarded, or null if the response has already been completed.
	 * 
	 * @param mapping -
	 *            The ActionMapping used to select this instance
	 * @param form -
	 *            The ActionForm bean for this request (if any)
	 * @param request -
	 *            The HTTP request we are processing
	 * @param response -
	 *            The HTTP response we are creating
	 * @return mapping.findForward("XSP") -- ActionForward instance describing
	 *         where and how control should be forwarded
	 * @throws IOException --
	 *             if an input/output error occurs
	 * @throws ServletException --
	 *             if a servlet exception occurs
	 */

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new ServletException();
	}

	/**
	 * this method reads the pageContext and sets the request attributes for
	 * page PS020
	 * 
	 * @param request
	 *            :HttpServletRequest
	 * @param session
	 *            :HttpSession
	 * @return :String
	 */
	protected String setContextForCreate(HttpServletRequest request,
			HttpSession session) {

		String passedContextAction = request.getParameter("ContextAction");
		TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS020",
				passedContextAction);
		ErrorMessageHelper.setErrMsgToRequest(request);
		String sCurrentTask = NBSContext.getCurrentTask(session);
		request.setAttribute("CurrentTask", sCurrentTask);
		request.setAttribute("formHref", "/nbs/" + sCurrentTask + ".do");
		if(request.getSession().getAttribute("PrevPageId")!=null 
				 && ((String)request.getSession().getAttribute("PrevPageId")).equals("PS233")){
			if((sCurrentTask.equals("CreateInvestigation11") || sCurrentTask.equals("CreateInvestigation10"))
					   && (NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSDocConditionCD)!= null)){
				request.setAttribute("Cancel", "/nbs/" + sCurrentTask
						+ ".do?ContextAction=" + tm.get("CancelToViewDoc"));
			}
		}else{		
			request.setAttribute("Cancel", "/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("Cancel"));
		}
		request.setAttribute("DiagnosedCondition", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("DiagnosedCondition"));
		request.setAttribute("SubmitNoViewAccess", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
		request.setAttribute("ContextAction", tm.get("Submit"));
		request.getSession().removeAttribute("PrevPageId");

		return sCurrentTask;
	}

	/**
	 * this method sets the request attribute from security object and also
	 * returns the boolean for createInvestigation permission
	 * 
	 * @param request
	 *            :HttpServletRequest
	 * @param nbsSecurityObj
	 *            :NBSSecurityObj
	 * @param programAreaVO
	 *            :ProgramAreaVO
	 * @return :boolean
	 */

	protected boolean setSecurityForCreateLoad(HttpServletRequest request,
			NBSSecurityObj nbsSecurityObj, ProgramAreaVO programAreaVO) {
		boolean viewInvestigation = nbsSecurityObj.getPermission(
				NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		// fix for the defect 5422, if the user dont have the view but create
		// permission, show confirmation page
		request.setAttribute("viewInvestigation", String
				.valueOf(viewInvestigation));

		boolean createInvestigation = nbsSecurityObj.getPermission(
				NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD,
				programAreaVO.getStateProgAreaCode(),
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		boolean checkInvestigationAutoCreatePermission = nbsSecurityObj
				.getPermission(NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.AUTOCREATE,
						ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
						ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
						ProgramAreaJurisdictionUtil.SHAREDISTRUE);

		//boolean checkJurisdiction =
		// nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
		//NBSOperationLookup.TRANSFERPERMISSIONS,
		//programAreaVO.getStateProgAreaCode(),
		//ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
		//ProgramAreaJurisdictionUtil.SHAREDISTRUE);
		//request.setAttribute("checkJurisdiction",
		// String.valueOf(checkJurisdiction));

		if (!createInvestigation && !checkInvestigationAutoCreatePermission)
			return false;
		else
			return true;

	}

	/**
	 * this method sets jurisdiction list from security object for create
	 * investiagtion page
	 * 
	 * @param request
	 *            :HttpServletRequest
	 * @param nbsSecurityObj
	 *            :NBSSecurityObj
	 * @param programAreaVO
	 *            :ProgramAreaVO
	 */

	protected void setJurisdictionForCreate(HttpServletRequest request,
			NBSSecurityObj nbsSecurityObj, ProgramAreaVO programAreaVO) {

		String programAreaJurisdictions = nbsSecurityObj
				.getProgramAreaJurisdictions(NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.VIEW);

		//String programAreaJurisdictionsAutoCreate =
		// nbsSecurityObj.getProgramAreaJurisdictions(
		//					  NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE);
		//programAreaJurisdictions = programAreaJurisdictions +
		// programAreaJurisdictionsAutoCreate;
		StringBuffer stingBuffer = new StringBuffer();
		logger.debug("programAreaJurisdictions: " + programAreaJurisdictions);

		if (programAreaJurisdictions != null
				&& programAreaJurisdictions.length() > 0) {

			//change the navigation depending on programArea
			StringTokenizer st = new StringTokenizer(programAreaJurisdictions,
					"|");

			while (st.hasMoreTokens()) {

				String token = st.nextToken();

				if (token.lastIndexOf("$") >= 0) {

					String programArea = token.substring(0, token
							.lastIndexOf("$"));
					logger.debug("programAreaJurisdictionToken: " + token);

					if (programArea != null
							&& programArea.equals(programAreaVO
									.getStateProgAreaCode())) {
						logger.debug("programArea: " + programArea);

						String juris = token
								.substring(token.lastIndexOf("$") + 1);
						stingBuffer.append(juris).append("|");
						logger.info("jurisdition for this programArea: "
								+ juris);
					}
				}
			}

			request.setAttribute("NBSSecurityJurisdictions", stingBuffer
					.toString());
			logger.debug("NBSSecuirtyJurisdiction: " + stingBuffer.toString());
		}
	}

	/**
	 * gets the MPR from backend using mprUId, this method is used in subclasses
	 * 
	 * @param mprUId :
	 *            Long
	 * @param session :
	 *            HttpSession
	 * @param secObj :
	 *            NBSSecurityObj
	 * @return : PersonVO
	 */
	protected PersonVO findMasterPatientRecord(Long mprUId,
			HttpSession session, NBSSecurityObj secObj) {

		PersonVO personVO = null;
		MainSessionCommand msCommand = null;

		try {

			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getMPR";
			Object[] oParams = new Object[] { mprUId };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			personVO = (PersonVO) arr.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}

			logger.fatal("personVO: ", ex);
		}

		return personVO;
	}//findMasterPatientRecord

	/**
	 * reads the morbMap got from morb and sets the request attributes thus
	 * prepopulating investigation screens
	 * 
	 * @param DSMorbMap :
	 *            TreeMap
	 * @param request :
	 *            HttpServletRequest
	 */

	protected void prepopulateMorbValues(TreeMap<Object, Object> DSMorbMap,
			HttpServletRequest request) {

		String INV111 = (String) DSMorbMap.get("INV111");

		//Reporting Source
		String INV183UID = (String) DSMorbMap.get("INV183UID");
		String INV183ORG = (String) DSMorbMap.get("INV183ORG");

		//Physician
		String INV182UID = (String) DSMorbMap.get("INV182UID");
		String INV182PRV = (String) DSMorbMap.get("INV182PRV");

		//Reporter
		String INV181UID = (String) DSMorbMap.get("INV181UID");
		String INV181PRV = (String) DSMorbMap.get("INV181PRV");

		//Hospital
		String INV184UID = (String) DSMorbMap.get("INV184UID");
		String INV184ORG = (String) DSMorbMap.get("INV184ORG");

		String INV128 = (String) DSMorbMap.get("INV128");

		String PHC108 = (String) DSMorbMap.get("PHC108");
		String INV137 = (String) DSMorbMap.get("INV137");
		String INV163 = (String) DSMorbMap.get("INV163");
		String INV145 = (String) DSMorbMap.get("INV145");
		String INV146 = (String) DSMorbMap.get("INV146");
		String INV178 = (String) DSMorbMap.get("INV178");
		String INV149 = (String) DSMorbMap.get("INV149");
		String INV148 = (String) DSMorbMap.get("INV148");
		String INV114 = (String) DSMorbMap.get("INV114");

		//new for Rel 1.1
		String INV132 = (String) DSMorbMap.get("INV132");
		String INV133 = (String) DSMorbMap.get("INV133");
		String INV136 = (String) DSMorbMap.get("INV136");

		request.setAttribute("dateOfReport", INV111);

		//Reporting Source Information
		request.setAttribute("reportingOrgUID", INV183UID);
		request.setAttribute("reportingOrgDemographics", INV183ORG);

		//Physician Information
		request.setAttribute("physicianPersonUid", INV182UID);
		request.setAttribute("physicianDemographics", INV182PRV);

		//Reporter Information
		request.setAttribute("reporterPersonUid", INV181UID);
		request.setAttribute("reporterDemographics", INV181PRV);

		request.setAttribute("INV128", INV128);

		//Hospital Information
		request.setAttribute("hospitalOrgUID", INV184UID);
		request.setAttribute("hospitalDemographics", INV184ORG);

		request.setAttribute("illnessOnsetDate", INV137);
		request.setAttribute("caseStatus", INV163);
		request.setAttribute("didThePatientDie", INV145);
		request.setAttribute("dateOfDeath", INV146);
		request.setAttribute("INV178", INV178);
		request.setAttribute("INV148", INV148);
		request.setAttribute("INV149", INV149);
		request.setAttribute("reportingSourceName", INV114);

		//new for Rel 1.1
		request.setAttribute("INV132-fromTime", INV132);
		request.setAttribute("INV133-fromTime", INV133);
		request.setAttribute("diagnosisDate", INV136);

		//   logger.info( "key: " + (String)itor.next() + " value: " +
		// DSMorbMap.get((String)itor.next()));
	}

	/**
	 * @param sStateCd :
	 *            String
	 * @return : String
	 */
	public String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<Object, Object> treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}

	private String getCountyDescTxt(String sStateCd, String sCountyCd) {

		String countyDesc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")
				&& sCountyCd != null && !sCountyCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<?, ?> treemap = srtValues.getCountyCodes(sStateCd);
			if (treemap != null && treemap.get(sCountyCd) != null) {
				countyDesc = (String) treemap.get(sCountyCd);
			}
		}

		return countyDesc;
	}

}