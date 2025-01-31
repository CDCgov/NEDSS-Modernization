package gov.cdc.nedss.webapp.nbs.action.investigation.common;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class InvestigationSelectSubmit
    extends Action {

  public InvestigationSelectSubmit() {
  }

  private NEDSSConstants Constants = new NEDSSConstants();
  //private PropertyUtil PropertyUtil = new PropertyUtil(NEDSSConstants.PROPERTY_FILE);

  static final LogUtils logger = new LogUtils(InvestigationSelectSubmit.class.
                                              getName());

  public ActionForward execute(ActionMapping mapping,
                               ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    logger.debug("inside the InvestigationSelectSubmit class");
    HttpSession session = request.getSession();
    if (session == null) {
      logger.fatal("error no session");
      return mapping.findForward("login");
    }

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    if (nbsSecurityObj == null) {
      logger.fatal(
          "Error: no nbsSecurityObj in the session, go back to login screen");
      return mapping.findForward("login");
    }

    String sContextAction = request.getParameter("ContextAction");
    logger.info("sContextAction in InvestigationSelectLoad = " + sContextAction);
    if (sContextAction == null) {
      logger.error("sContextAction is null");
      session.setAttribute("error",
                           "No Context Action in InvestigationSelectLoad");
      throw new ServletException("sContextAction is null");
    }

    if (sContextAction.equals(NBSConstantUtil.SUBMIT)) {
      boolean createInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.
          INVESTIGATION, NBSOperationLookup.ADD);
      if (!createInvestigation) {
        logger.info("You do not have access to Create Investigation");
        session.setAttribute("error",
                             "You do not have access to Create Investigation");
        return (mapping.findForward("error"));
      }

      String DSInvestigationCondition = request.getParameter("ccd");
      String referralBasis = (String)request.getParameter("referralBasis");
      String programAreas = "";
      StringBuffer stingBuffer = new StringBuffer();

      TreeMap<Object,Object> treeMap = (TreeMap<Object, Object>) nbsSecurityObj.getProgramAreas(NBSBOLookup.
          INVESTIGATION, NBSOperationLookup.ADD);
      if (treeMap != null && treeMap.size() > 0) {
        logger.debug("nbsSecurityObj.getProgramAreas(): " +
                     nbsSecurityObj.
                     getProgramAreas(NBSBOLookup.INVESTIGATION,
                                     NBSOperationLookup.ADD));
       Iterator<Object>  anIterator = treeMap.keySet().iterator();
        stingBuffer.append("(");
        while (anIterator.hasNext()) {

          String token = (String) anIterator.next();
          logger.debug("resultValue is : " + token);
          String NewString = "";
          if (token.lastIndexOf("!") < 0) {
            NewString = token;
          }
          else {
            NewString = token.substring(0, token.lastIndexOf("!"));
            logger.debug("NewString Value: " + NewString);
          }

          stingBuffer.append("'").append(NewString).append("',");
        }
        stingBuffer.replace(stingBuffer.length() - 1, stingBuffer.length(), "");
        stingBuffer.append(")");
        programAreas = stingBuffer.toString();
        logger.debug("programAreas: " + programAreas);

      }

      ProgramAreaVO programAreaVO = null;
      SRTValues srtv = new SRTValues();
      TreeMap<Object,Object> programAreaTreeMap = srtv.getProgramAreaConditions(programAreas);
      logger.debug("programAreaTreeMap: " + programAreaTreeMap);
      if (programAreaTreeMap != null) {
        programAreaVO = (ProgramAreaVO) programAreaTreeMap.get(
            DSInvestigationCondition);
        logger.debug("programAreaCd: " + programAreaVO.getStateProgAreaCode() +
                     " programAreaCdDesc: " +
                     programAreaVO.getStateProgAreaCdDesc() + " conditionCd: " +
                     programAreaVO.getConditionCd() + " conditionShortDesc: " +
                     programAreaVO.getConditionShortNm());
      }
      logger.debug("programAreaVO.getInvestigationFormCd()" +
                   programAreaVO.getInvestigationFormCd());
			if (request.getParameter(NEDSSConstants.PROCESSING_DECISION) != null) {
				NBSContext
						.store(session,
								NBSConstantUtil.DSProcessingDecision,
								request.getParameter(NEDSSConstants.PROCESSING_DECISION));
				// add processing decision to DSLabMap is Exists
				try {
					@SuppressWarnings("unchecked")
					Map<Object, Object> labMap = (Map<Object, Object>) NBSContext
							.retrieve(session, NBSConstantUtil.DSLabMap);
					labMap.put(NEDSSConstants.PROCESSING_DECISION, request
							.getParameter(NEDSSConstants.PROCESSING_DECISION));
					NBSContext.store(session, NBSConstantUtil.DSLabMap, labMap);
				} catch (Exception ex) {
					logger.debug("Not the Lab Context while creating Investigation");
				}
			}
      if(request.getParameter(NEDSSConstants.INVESTIGATION_TYPE)!=null)
    	  NBSContext.store(session, NBSConstantUtil.DSInvestigationType,
    			  request.getParameter(NEDSSConstants.INVESTIGATION_TYPE));
      NBSContext.store(session, NBSConstantUtil.DSInvestigationCondition,
                       programAreaVO.getConditionCd());
      NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea,
                       programAreaVO.getStateProgAreaCode());
      NBSContext.store(session, NBSConstantUtil.DSInvestigationCode,
                       programAreaVO.getInvestigationFormCd());
      if(referralBasis!=null && !referralBasis.trim().equals("")){
	      NBSContext.store(session, NBSConstantUtil.DSReferralBasis,
	    		  referralBasis);
      }
      NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");

      return mapping.findForward(sContextAction);
    }
    else if (sContextAction.equals(NBSConstantUtil.CANCEL)) {
      String prevPage = NBSContext.getPrevPageID(session);
			// Using Cancel context to go back to view lab in case user decides
			// to associate STD lab with an existing investigation
			String associateLabWithEsixtingInv = (String) request
					.getParameter("associateLab");
			if (associateLabWithEsixtingInv != null
					&& associateLabWithEsixtingInv.equals(NEDSSConstants.TRUE)) {
				CoinfectionSummaryVO cvo = (CoinfectionSummaryVO) NBSContext
						.retrieve(session,
								NBSConstantUtil.DSCoinfectionInvSummVO);
				Long labUid = (Long) NBSContext.retrieve(session,
						NBSConstantUtil.DSObservationUID);
				String labLocalId = "";
				associateLabWithExistingInv(labUid,cvo.getPublicHealthCaseUid(),
						 session);
				String successMsg = "The Lab Report "+labLocalId +"successfully associated to investigation: "+cvo.getCondition()+", "+cvo.getLocalId();
				logger.info(successMsg);
				request.setAttribute("displayInformationExists", successMsg);
			}
      if (prevPage.equalsIgnoreCase("PS015")) {
        String jurisdictions = getNBSSecurityJurisdictions(request,
            nbsSecurityObj);
        StringTokenizer st = new StringTokenizer(jurisdictions, "|");
        String[] tokens = new String[st.countTokens()];
        String dsInvestJur = (String) NBSContext.retrieve(session,
            "DSInvestigationJurisdiction");
        for (int i = 0; st.hasMoreTokens(); i++) {
          tokens[i] = st.nextToken();
        }
        boolean hasJurisdiction = false;
        for (int i = 0; i < tokens.length; i++) {
          if (tokens[i].equalsIgnoreCase(dsInvestJur)) {
            hasJurisdiction = true;
          }
        }

        if (!hasJurisdiction) {
          String jurisdiction = (String) NBSContext.retrieve(session,
              NBSConstantUtil.DSInvestigationJurisdiction);
          NBSContext.store(session, NBSConstantUtil.DSJurisdiction,
                           jurisdiction);
          ActionForward af = mapping.findForward("CancelNoViewAccess");
          ActionForward forwardNew = new ActionForward();
          StringBuffer strURL = new StringBuffer(af.getPath());
          strURL.append("?ContextAction=CancelNoViewAccess");
          logger.debug("CancelNoViewAccess URL--->" + strURL.toString());
          forwardNew.setPath(strURL.toString());
          forwardNew.setRedirect(true);
          return forwardNew;
        }
      }

      NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
      return mapping.findForward(sContextAction);

    }
    else {
      session.setAttribute("error", " Invalid ContextAction: " + HTMLEncoder.encodeHtml(sContextAction));
      throw new ServletException(" Invalid ContextAction ");
    }

  }

  private String getNBSSecurityJurisdictions(HttpServletRequest request,
                                             NBSSecurityObj nbsSecurityObj) {

    String programAreaJurisdictions = nbsSecurityObj.
        getProgramAreaJurisdictions(
        NBSBOLookup.OBSERVATIONLABREPORT,
        NBSOperationLookup.ADD);

    StringBuffer stringBuffer = new StringBuffer();
    if (programAreaJurisdictions != null &&
        programAreaJurisdictions.length() > 0) { //"PA$J|PA$J|PA$J|"
      //change the navigation depending on programArea
      logger.info("programAreaJurisdictions: " + programAreaJurisdictions);
      StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        if (token.lastIndexOf("$") >= 0) {
          String programArea = token.substring(0, token.lastIndexOf("$"));
          String juris = token.substring(token.lastIndexOf("$") + 1);
          stringBuffer.append(juris).append("|");
        }
      }
      request.setAttribute("NBSSecurityJurisdictions", stringBuffer.toString());

    }
    return stringBuffer.toString();
  }
  
	public void associateLabWithExistingInv(Long labUid, Long invUid, 
			HttpSession session) throws ServletException {
		MainSessionCommand msCommand = null;
		try {
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "setLabInvAssociation";
			Object[] oParams = new Object[] { labUid, invUid  };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		} catch (Exception ex) {
			logger.fatal("associateLabWithExistingInv: ", ex);
			throw new ServletException(ex);
		}
	}

}