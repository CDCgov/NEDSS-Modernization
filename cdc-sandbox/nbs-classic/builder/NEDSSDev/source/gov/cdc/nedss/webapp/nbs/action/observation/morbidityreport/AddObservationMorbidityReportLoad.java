package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;

import java.util.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.MorbidityUtil;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;


/**
 * Title:        AddObservationMorbidityReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class AddObservationMorbidityReportLoad
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(AddObservationMorbidityReportLoad.class.
                                              getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {

    HttpSession session = request.getSession();

    String contextAction = request.getParameter("ContextAction");

    NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");

    // xz (12/09/2004), the NBSContext.getPageContext must be called before
    // NBSContext.getCurrentTask, otherwise context error occurs.
    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS049", contextAction);
    String sCurrTask = NBSContext.getCurrentTask(session);
    this.setSTDandHIVPAsToRequest(request);
    NBSObjectStore NBSObjectStore = (NBSObjectStore)session.getAttribute(
						 NBSConstantUtil.OBJECT_STORE);
	NBSObjectStore.remove(NBSConstantUtil.DSInvestigationType);
	NBSObjectStore.remove(NBSConstantUtil.DSCoinfectionInvSummVO);
    //civil0012081 - only for data entry mode
    //if strJurisdiction is false, jurisdiction and share indicator will be read-only for dataentry
    if ( sCurrTask.equalsIgnoreCase("AddObservationMorbDataEntry1") )
    {
      boolean strJurisdiction = secObj.getPermission(NBSBOLookup.
          OBSERVATIONMORBIDITYREPORT,
          NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);
      request.setAttribute("jurisdictionPermission",
                           String.valueOf(strJurisdiction));
    }

    if (sCurrTask.equalsIgnoreCase("AddObservationMorb1") ||
        sCurrTask.equalsIgnoreCase("AddObservationMorb3") ||
        sCurrTask.equalsIgnoreCase("AddObservationMorb4"))
    {
      request.setAttribute("fromFile", "false");
      request.setAttribute("addObservationMorb134", "true");

      try
      {
        String DSObservationProgramArea = (String) NBSContext.retrieve(session,
        		NBSConstantUtil.DSProgramArea);
        String DSObservationJurisdiction = (String) NBSContext.retrieve(
            session, NBSConstantUtil.DSJurisdiction);
        if (DSObservationProgramArea != null && DSObservationJurisdiction != null)
        {

          boolean check1 = secObj.getPermission(NBSBOLookup.
                                                OBSERVATIONMORBIDITYREPORT,
                                                NBSOperationLookup.ADD,
                                                DSObservationProgramArea,
                                                DSObservationJurisdiction);
          if (check1 == false)
          {
            session.setAttribute("error", "Failed at security checking.");
            throw new ServletException("Failed at security checking.");
          }
          else
          {
            request.setAttribute("MRB137", DSObservationJurisdiction);
          }

        }
      }
      catch (NullPointerException ne)
      {
        logger.error("There is no DSObservationProgramArea or DSObservationJurisdication in the Object Store");
        session.setAttribute("error", "Failed at security checking.");
        throw new ServletException("Failed at security checking."+ne.getMessage(),ne);
      }
    }
    else if (sCurrTask.equalsIgnoreCase("AddObservationMorb2"))
    {
      boolean check2 = secObj.getPermission(NBSBOLookup.
                                            OBSERVATIONMORBIDITYREPORT,
                                            NBSOperationLookup.ADD, "ANY",
                                            "ANY");
      if (check2 == false)
      {
        session.setAttribute("error", "Failed at security checking.");
        throw new ServletException("Failed at security checking.");
      }
      boolean check3 = secObj.getPermission(NBSBOLookup.INVESTIGATION,
                                            NBSOperationLookup.ADD, "ANY",
                                            "ANY");
      boolean check4 = secObj.getPermission(NBSBOLookup.INVESTIGATION,
                                            NBSOperationLookup.AUTOCREATE,
                                            "ANY",
                                            "ANY");

      if (check3 == false && check4 == false)
        request.setAttribute("fromFile", "false");
      else
        request.setAttribute("fromFile", "true");
    }

    ArrayList<Object> stateList = new ArrayList<Object> ();
    try
    {

      //using personUid to get personvo from back-end

      Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
      PersonVO personVO = this.findMasterPatientRecord(mprUid, session, secObj);
      String personLocalID = personVO.getThePersonDT().getLocalId();
      request.setAttribute("personLocalID", personLocalID);
      session.setAttribute("personLocalID", personLocalID);
      request.setAttribute("reportingSourceDetails","There is no Reporting Facility selected.");
      request.setAttribute("hospitalDetails", "There is no Hospital selected.");

      request.setAttribute("providerSourceDetails", "There is no Provider selected.");

      request.setAttribute("reporterSourceDetails", "There is no Reporter selected.");

      String jurisdictionValues = new CachedDropDownValues().getJurisdictionCodedSortedValues();
      jurisdictionValues = "NONE$Unknown|" + jurisdictionValues;
      request.setAttribute("jurisdictionCodedSortedValues", jurisdictionValues);

      if (sCurrTask.equalsIgnoreCase("AddObservationMorb2"))
      {
        String strJurisdictionCd = personVO.getDefaultJurisdictionCd();
        if (strJurisdictionCd != null)
        {
          request.setAttribute("MRB137", strJurisdictionCd);
        }
      }

      PersonUtil.convertPersonToRequestObj(personVO, request,
                                           "PatientFromEvent", stateList);
      createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
    }
    catch (NullPointerException ne)
    {
    	logger.error("Add Morbidity null pointer Error: "+ne.getMessage());
      ne.printStackTrace();
    }
  catch(Exception ex) {
		throw new ServletException(ex.getMessage());
	}

    request.setAttribute("MRB142", "T");
    request.setAttribute("MRB162", StringUtils.formatDate(new java.sql.Timestamp(new Date().getTime())));
    request.setAttribute("CurrentTask", sCurrTask);
    request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
    request.setAttribute("ContextAction", tm.get("Submit"));
    request.setAttribute("SubmitNoViewAccess",
                         "/nbs/" + sCurrTask +
                         ".do?ContextAction=" +
                         tm.get("SubmitNoViewAccess"));
    request.setAttribute("SubmitAndCreateInvestigationHref",
                         "/nbs/" + sCurrTask +
                         ".do");
    request.setAttribute("cancelButtonHref",
                         "/nbs/" + sCurrTask + ".do?ContextAction=" +
                         tm.get("Cancel"));

    ErrorMessageHelper.setErrMsgToRequest(request, "PS049");
    MorbidityUtil morbUtil = new MorbidityUtil();
    morbUtil.getFilteredProgramAreaJurisdiction(request, secObj);
    morbUtil.setExternalUserForCreate(request, session, secObj);

    //LDF
    CommonLabUtil commonLabUtil = new CommonLabUtil();
    commonLabUtil.loadMorbLDFAdd(request);
    PropertyUtil propUtil = PropertyUtil.getInstance();
    int maxSizeInMB = propUtil.getMaxFileAttachmentSizeInMB();
	  request.setAttribute("maxFileSizeInMB", String.valueOf(maxSizeInMB)); 

	  String allowedExtensions = propUtil.getFileAttachmentExtensions();
	  request.setAttribute("allowedExtensions", allowedExtensions); 
	  
    // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004
    request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultMorbTabOrder()).toString());

    request.setAttribute("addMorb", "true");
    return (mapping.findForward("XSP"));
  }

  private PersonVO findMasterPatientRecord(Long mprUId, HttpSession session,
                                           NBSSecurityObj secObj)
  {

    PersonVO personVO = null;
    MainSessionCommand msCommand = null;

    try
    {

      String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
      String sMethod = "getMPR";
      Object[] oParams = new Object[]
          {
          mprUId};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      personVO = (PersonVO) arr.get(0);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      if (session == null)
      {
        logger.error("Error: no session, please login");
      }

      logger.fatal("Add Observation Morbidity get MPR: ", ex);
    }

    return personVO;
  }



}