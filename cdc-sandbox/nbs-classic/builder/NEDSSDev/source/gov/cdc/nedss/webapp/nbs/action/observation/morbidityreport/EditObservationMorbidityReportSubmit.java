package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.act.attachment.dt.AttachmentDT;
import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.form.morbidity.*;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.*;
import gov.cdc.nedss.systemservice.util.*;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;


/**
 * Title:        EditObservationMorbidityReportSubmit.java
 * Description:	This is a action class for the struts implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class EditObservationMorbidityReportSubmit
    extends CommonAction
{
  private String sBeanJndiName = "";
  private String sMethod = "";
  private Object[] oParams = null;
  private String userID = "";

  //For logging
  static final LogUtils logger = new LogUtils(
      EditObservationMorbidityReportSubmit.class.getName());

  public EditObservationMorbidityReportSubmit()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {
	 
    HttpSession session = request.getSession();
    String contextAction = request.getParameter("ContextAction");
    if (contextAction == null)
    {
      contextAction = (String) request.getAttribute("ContextAction");
    }
    String sCurrTask = NBSContext.getCurrentTask(session);

    if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
    {
      NBSContext.store(session, "DSFileTab", "3");
      return (mapping.findForward(contextAction));
    }
    try {
    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
    			"NBSSecurityObject");
    	MorbidityForm morbidityForm = (MorbidityForm) aForm;

    	MorbidityProxyVO newProxy = editHandler(morbidityForm, nbsSecurityObj,
    			session, request);
    	 MorbidityUtil morbUtil = new MorbidityUtil();
    	ArrayList<?> result = null;
    	Long obsUid = null;
    	try
    	{
    		result = (ArrayList<?> ) sendProxyToEJB(newProxy, session);
    		obsUid = (Long) result.get(0);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		morbidityForm.reset();
    		if (e instanceof NEDSSAppException)
    		{
    			NEDSSAppException nae = (NEDSSAppException) e;
    			if (nae.getErrorCd().equals("ERR109"))
    			{
    				logger.info("ERROR ,  DataConcurrent exception recieved.", e);
    				//redirect to dataconcurrent message page.
    				return ErrorMessageHelper.redirectToPage("PS158", nae.getErrorCd());
    			}
    			//redirect to generic error page
    			return ErrorMessageHelper.redirectToPage("PS175", nae.getErrorCd());
    		}
    		return ErrorMessageHelper.redirectToPage("PS175", null);
    	}

    	request.setAttribute("morbidityReportUid", obsUid);
    	morbUtil.createAttachment(morbidityForm, nbsSecurityObj,session, request);

    	ObservationVO morbidityReportObsVO = morbidityForm.getMorbidityReport();
    	ObservationDT morbidityReportObsDT = morbidityReportObsVO.
    			getTheObservationDT();
    	String DSInvestigationProgramArea = morbidityReportObsDT.getProgAreaCd();
    	String DSInvestigationJurisdiction = morbidityReportObsDT.
    			getJurisdictionCd();
    	String DSInvestigationCondition = morbidityReportObsDT.getCd();

    	NBSContext.store(session, "DSInvestigationProgramArea",
    			DSInvestigationProgramArea);
    	NBSContext.store(session, "DSInvestigationJurisdiction",
    			DSInvestigationJurisdiction);
    	NBSContext.store(session, "DSInvestigationCondition",
    			DSInvestigationCondition);
    	NBSContext.store(session, "DSObservationUID",
    			morbidityReportObsDT.getObservationUid().toString());
    	NBSContext.store(session, "DSObservationTypeCd",
    			NEDSSConstants.MORBIDITY_CODE);

    	morbidityForm.reset();
    	newProxy = null;
    	NBSContext.store(session, "DSFileTab", "3");
    }catch (Exception e) {
    	logger.error("Exception in Edit Morb Submit: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("Error occurred in Edit Morb Submit : "+e.getMessage());
    }  
    return (mapping.findForward(contextAction));
  }

  
 
  /**
   * @method       : editHandler
   * @returnType   : void
   * @params       : ActionMapping, ActionForm, HttpServletRequest, HttpSession, NBSSecurityObj
   */
  private MorbidityProxyVO editHandler(MorbidityForm form,
                                       NBSSecurityObj securityObj,
                                       HttpSession session,
                                       HttpServletRequest request)
  {
    MorbidityUtil morbidityUtil = new MorbidityUtil();

    // observation create initial uid
    int i = -10;


    ArrayList<ObservationVO> observations = null;
    ArrayList<Object> actrelations = null;
    ArrayList<Object> participations = new ArrayList<Object> ();
    ArrayList<Object> treatments = new ArrayList<Object> ();

    MorbidityProxyVO newProxy = form.getMorbidityProxy();
    MorbidityProxyVO oldProxy = form.getOldProxy();
    ObservationVO morbidityReportObsVO = form.getMorbidityReport();
    ObservationDT morbidityReportObsDT = morbidityReportObsVO.
        getTheObservationDT();
    String programAreaCd = morbidityReportObsDT.getProgAreaCd();

    String sharedInd = (String) request.getAttribute(
        "morbidityReport.theObservationDT.sharedInd");
    if (sharedInd == null)
    {
      sharedInd = (String) request.getParameter(
          "morbidityReport.theObservationDT.sharedInd");
    }
    if (sharedInd == null || sharedInd.trim().length() < 1)
    {
      sharedInd = "F";
    }
    morbidityReportObsDT.setSharedInd(sharedInd);

    //from oldProxy get AssociatedNotif to newProxy
    boolean associatedNotif = oldProxy.getAssociatedNotificationInd();
    newProxy.setAssociatedNotificationInd(associatedNotif);
        
    //from proxy, get all the morbQA and map them with the old observation uid
    TreeMap<Object,Object> treeMap = form.getQaTreeMap();

    Long targetObsUid = morbidityReportObsDT.getObservationUid();

    TreeMap<Object,Object> retMap =  setObservations(
                          newProxy.getTheObservationVOCollection(),
                          treeMap,
                          sharedInd,
                          oldProxy.getTheActRelationshipDTCollection(),
                          targetObsUid, "MorbFrmQ", request, i);

    observations = (ArrayList<ObservationVO> )retMap.get("observations");
    actrelations = (ArrayList<Object> )retMap.get("actrelations");
    i = ((Integer)retMap.get("tempID")).intValue();


    participations.addAll(morbidityUtil.setParticipationsForEdit(oldProxy,
        request));

    observations.add(morbidityReportObsVO);
    //get all the person Uids from request object for build relationships
    Long personSubjectUid = form.getPatient().getThePersonDT().getPersonUid();

    /******************************************************************
     * batch entry observations
     */
    Collection<Object>  labResultsColl = form.getLabResultsCollection();

    if (labResultsColl != null)
    {
      for (Iterator<Object> iter = labResultsColl.iterator(); iter.hasNext(); )
      {
        BatchEntryHelper helper = (BatchEntryHelper) iter.next();

        boolean newFlag = false;
        if (helper.getUid() == null)
        {
          newFlag = true;
        }

        boolean deleteFlag = morbidityUtil.findDeleteOrderTest(helper);

        if (deleteFlag)
        {
          TreeMap<Object,Object> batchTreeMap = helper.getTreeMap();
          if(batchTreeMap!=null)
          {
          Long orderedTestUid = (Long) batchTreeMap.get("OrderedTestuid");
          ObservationVO orderedTest = (ObservationVO) batchTreeMap.get(
              "OrderedTest");
          ObservationVO resultTest = (ObservationVO) batchTreeMap.get(
              "ResultTest");
          //participations.add(morbidityUtil.getPartForDelete(orderedTest, orderedTestUid));
          actrelations.addAll(morbidityUtil.getActForDelete(orderedTest,
              orderedTestUid));
          actrelations.addAll(morbidityUtil.getActForDelete(
              morbidityReportObsVO, orderedTestUid));
          morbidityUtil.getPartForDelete(orderedTest, orderedTestUid);
          }
          //morbidityUtil.getActForDelete(orderedTest, orderedTestUid);
          //morbidityUtil.getActForDelete(morbidityReportObsVO, orderedTestUid);
        }
        else if (newFlag)
        {

          /**
           * ObservationVO 0 is order test - LAB112
           */
          ObservationVO rowVO = helper.getObservationVO_s(0);
          ObservationDT rowDT = rowVO.getTheObservationDT();
          rowDT.setObservationUid(new Long(i--));
          rowDT.setCd("NI");
          rowDT.setCdDescTxt("No Information Given");
          rowDT.setCdSystemCd("2.16.840.1.113883"); // changed as per WPD
          rowDT.setCdSystemDescTxt("Health Level Seven");
          rowDT.setObsDomainCdSt1("Order");
          rowDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT_MORB);
          rowDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
          rowDT.setStatusCd("D"); // changed from F as per WPD
          rowDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
          rowDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          rowDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
          Timestamp rptToStateTime = morbidityReportObsDT.getRptToStateTime();
          rowDT.setRptToStateTime(rptToStateTime);
          rowDT.setItNew(true);
          rowDT.setItDirty(false);
          rowVO.setTheObservationDT(rowDT);
          ObservationUtils.trimEmptyObsValueCodedDTCollections(rowVO);
          rowVO.setItNew(true);
          rowVO.setItDirty(false);
          // add to the collection for the oldProxy
          observations.add(rowVO);

          //PAR110 Person(Patient) as Subject of Lab Test Observation
          // each Ordered Test has a Participation Relationship to the Patient
          if (personSubjectUid != null)
          {
            ParticipationDT par110 = new ParticipationDT();
            par110.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
            par110.setActUid(rowDT.getObservationUid());
            par110.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            par110.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            par110.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
            par110.setSubjectEntityUid(personSubjectUid);
            par110.setTypeCd(NEDSSConstants.PAR110_TYP_CD);
            par110.setTypeDescTxt("Patient Subject");
            par110.setItNew(true);
            par110.setItDirty(false);
            participations.add(par110);
          }

          //  setup act relationship(Act129) of rowObservation( an ordered test) to a morbidity report
          if ( (rowVO.getTheObservationDT().getObservationUid() != null) &&
              (morbidityReportObsDT.getObservationUid() != null))
          {
            ActRelationshipDT act129 = new ActRelationshipDT();
            act129.setSourceActUid(rowVO.getTheObservationDT().
                                   getObservationUid());
            act129.setSourceClassCd("OBS");
            act129.setTargetActUid(morbidityReportObsDT.getObservationUid());
            act129.setTargetClassCd("OBS");
            act129.setTypeCd(NEDSSConstants.LAB_REPORT);
            act129.setTypeDescTxt("Laboratory Report");
            act129.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            act129.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            act129.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
            act129.setItNew(true);
            act129.setItDirty(false);
            actrelations.add(act129);
          }
          //String cdSystemCd = request.getParameter("cdSystemCdRT");
          //  setup the observation vo's and add to the observation collection
          ArrayList<Object> helperColl = new ArrayList<Object> ();
          // only want result test (only one of them)
          ObservationVO resultTest = helper.getObservationVO_s(1);

          resultTest.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.
              RESULTED_TEST_OBS_DOMAIN_CD);

          // need it in a collection for setObservationForCreate method
          helperColl.add(resultTest);

          // sets up the observations in BatchEntryHelps and adds to the observations collection for the oldProxy
          i = setBatchObservationForCreate(helperColl, observations, i);
          String cdSystemCdRT = resultTest.getTheObservationDT().
              getCdSystemCdRT();
          String labId = request.getParameter("labId");
          String searchResultRT = resultTest.getTheObservationDT().
              getSearchResultRT();

          if (searchResultRT != null && searchResultRT.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCdDescTxt(searchResultRT);

          }
          if (cdSystemCdRT != null && cdSystemCdRT.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCdSystemCd(cdSystemCdRT);
            if (cdSystemCdRT.equalsIgnoreCase("LN"))
            {
              resultTest.getTheObservationDT().setCdSystemDescTxt("LOINC");
            }
          }
          else if (labId != null && labId.length() > 0)
          {
            resultTest.getTheObservationDT().setCdSystemCd(labId);

          }
          String hiddenCd = resultTest.getTheObservationDT().getHiddenCd();
          if (hiddenCd != null && hiddenCd.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCd(hiddenCd);

          }
          ObsValueCodedDT obsValueDT = resultTest.getObsValueCodedDT_s(0);
          CachedDropDownValues cdv = new CachedDropDownValues();
          if (resultTest.getTheObservationDT().getCtrlCdUserDefined1().
              equalsIgnoreCase("Y"))
          {

            if (obsValueDT.getDisplayName() != null &&
                !obsValueDT.getDisplayName().equals(""))
            {
              obsValueDT.setCode(obsValueDT.getDisplayName());

              String temp = cdv.getOrganismListDesc(obsValueDT.getDisplayName());
              obsValueDT.setDisplayName(temp);
            }
            if (obsValueDT.getSearchResultRT() != null &&
                !obsValueDT.getSearchResultRT().equals(""))
            {
              obsValueDT.setDisplayName(obsValueDT.getHiddenCd());
              obsValueDT.setCode(obsValueDT.getSearchResultRT());

            }
          }
          else
          {
            if (obsValueDT.getCode() == null ||
                obsValueDT.getCode().trim().length() == 0)
            {
              obsValueDT.setCode("NI");
              resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
            }
            else
            {

              String temp = cdv.getCodedResultDesc(obsValueDT.getCode());
              obsValueDT.setDisplayName(temp);
              resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
            }

          }

          // setup the act relationships in each BatchEntryHelp
          if (helperColl != null)
          {

            for (Iterator<Object> helperIter = helperColl.iterator();
                 helperIter.hasNext(); )
            {
              // item to row act relationship (Act108)
              ObservationVO helperObsVO = (ObservationVO) helperIter.next();

              Collection<Object>  numericColl = helperObsVO.
                  getTheObsValueNumericDTCollection();
              if (numericColl != null)
              {
                ObsValueNumericDT obsValueNumericDT = null;
                // Iterate through the collection
                for (Iterator<Object> numericCollIter = numericColl.iterator();
                     numericCollIter.hasNext(); )
                {
                  obsValueNumericDT = (ObsValueNumericDT) numericCollIter.next();
                  ObservationUtil obsUtil = new ObservationUtil();
                  if (obsValueNumericDT.getNumericValue() != null)
                  {
                    obsValueNumericDT = obsUtil.parseNumericResult(
                        obsValueNumericDT);
                  }
                }
              }

              ObsValueTxtDT obsValueTxtDT = helperObsVO.getObsValueTxtDT_s(1);
              if (obsValueTxtDT != null)
              {
                // Result Comments Text Area
                obsValueTxtDT.setTxtTypeCd("N");
              }

              if ( (helperObsVO.getTheObservationDT().getObservationUid() != null) &&
                  (rowVO.getTheObservationDT().getObservationUid() != null))
              {
                ActRelationshipDT act108 = new ActRelationshipDT();

                act108.setSourceActUid(helperObsVO.getTheObservationDT().
                                       getObservationUid());
                act108.setSourceClassCd("OBS");
                act108.setTargetActUid(rowVO.getTheObservationDT().
                                       getObservationUid());
                act108.setTargetClassCd("OBS");
                act108.setTypeCd("COMP");
                act108.setTypeDescTxt("Has Component");
                act108.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                act108.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                act108.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                act108.setItNew(true);
                act108.setItDirty(false);
                // add to the collection for the oldProxy
                actrelations.add(act108);
              }
            }
          }
        } //end of create a new ordered test
        else //else everything else id set dirty
        {
          //String cdSystemCdRT = request.getParameter("cdSystemCdRT");
          Collection<ObservationVO>  helperColl = helper.getObservationVOCollection();

          setBatchObservationForEdit(helperColl, observations);
          ObservationVO orderedTestVO = helper.getObservationVO_s(0);
          ObservationDT orderedTestDT = orderedTestVO.getTheObservationDT();
          Timestamp rptToStateTime = morbidityReportObsDT.getRptToStateTime();
          orderedTestDT.setRptToStateTime(rptToStateTime);

          ObservationVO resultTest = helper.getObservationVO_s(1);

          // Changes per chris
          String cdSystemCdRT = resultTest.getTheObservationDT().
              getCdSystemCdRT();
          String labId = request.getParameter("labId");
          String searchResultRT = resultTest.getTheObservationDT().
              getSearchResultRT();

          if (searchResultRT != null && searchResultRT.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCdDescTxt(searchResultRT);

          }
          if (cdSystemCdRT != null && cdSystemCdRT.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCdSystemCd(cdSystemCdRT);
          }
          else if (labId != null && labId.length() > 0)
          {
            resultTest.getTheObservationDT().setCdSystemCd(labId);

          }
          String hiddenCd = resultTest.getTheObservationDT().getHiddenCd();
          if (hiddenCd != null && hiddenCd.trim().length() > 0)
          {
            resultTest.getTheObservationDT().setCd(hiddenCd);

          }
          ObsValueCodedDT obsValueDT = resultTest.getObsValueCodedDT_s(0);
          CachedDropDownValues cdv = new CachedDropDownValues();
          if (resultTest.getTheObservationDT().getCtrlCdUserDefined1().
              equalsIgnoreCase("Y"))
          {

            if (obsValueDT.getDisplayName() != null &&
                !obsValueDT.getDisplayName().equals(""))
            {
              obsValueDT.setCode(obsValueDT.getDisplayName());

              String temp = cdv.getOrganismListDesc(obsValueDT.getDisplayName());
              obsValueDT.setDisplayName(temp);
            }
            if (obsValueDT.getSearchResultRT() != null &&
                !obsValueDT.getSearchResultRT().equals(""))
            {
              obsValueDT.setDisplayName(obsValueDT.getHiddenCd());
              obsValueDT.setCode(obsValueDT.getSearchResultRT());

            }
          }
          else
          {
            if (obsValueDT.getCode() == null ||
                obsValueDT.getCode().trim().length() == 0)
            {
              obsValueDT.setCode("NI");
              resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
            }
            else
            {

              String temp = cdv.getCodedResultDesc(obsValueDT.getCode());
              obsValueDT.setDisplayName(temp);
              resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
            }

          }
          // added parsing in edit
          Collection<Object>  numericColl = resultTest.getTheObsValueNumericDTCollection();
          if (numericColl != null)
          {
            ObsValueNumericDT obsValueNumericDT = null;
            // Iterate through the collection
            for (Iterator<Object> numericCollIter = numericColl.iterator();
                 numericCollIter.hasNext(); )
            {
              obsValueNumericDT = (ObsValueNumericDT) numericCollIter.next();
              ObservationUtil obsUtil = new ObservationUtil();
              if (obsValueNumericDT.getNumericValue() != null)
              {
                obsValueNumericDT = obsUtil.parseNumericResult(
                    obsValueNumericDT);
              }
            }
          }

        }
      }
    }

    /******************************************************************
     * batch entry treatments
     */
    Collection<Object>  treatmentColl = form.getTreatmentCollection();

    if (treatmentColl != null)
    {
      for (Iterator<Object> iter = treatmentColl.iterator(); iter.hasNext(); )
      {
        BatchEntryHelper helper = (BatchEntryHelper) iter.next();

        boolean newFlag = false;
        if (helper.getUid() == null)
        {
          newFlag = true;
        }

        // findDeleteOrderTest - bad name - this works
        boolean deleteFlag = morbidityUtil.findDeleteOrderTest(helper);
        TreeMap<Object,Object> batchTreeMap = helper.getTreeMap();

        if (deleteFlag) {
					//Long treatmentUid
					// =(Long)batchTreeMap.get("TreatmentVOuid");
					if (batchTreeMap != null) {
						TreatmentVO treatmentVO = (TreatmentVO) batchTreeMap
								.get("TreatmentVO");
						if (treatmentVO != null
								&& treatmentVO.getTheTreatmentDT() != null
								&& treatmentVO.getTheTreatmentDT()
										.getTreatmentUid() != null) {
							actrelations.addAll(morbidityUtil
									.getActForDeleteTreatment(treatmentVO));
							participations.addAll(morbidityUtil
									.getPartForDeleteTreatment(treatmentVO));
						}
					}
				}
        else if (newFlag)
        {
          Collection<Object>  treatmentHelperColl = helper.getTreatmentVOCollection();
          i = setTreatmentsForCreate(treatmentHelperColl, treatments, programAreaCd, i);

          if (treatmentHelperColl != null)
          {
            for (Iterator<Object> helperIter = treatmentHelperColl.iterator();
                 helperIter.hasNext(); )
            {
              //ActRelationship between treatment and morbreport
              TreatmentVO treatmenthHelperObsVO = (TreatmentVO) helperIter.next();

              if (treatmenthHelperObsVO != null)
              {
                treatmenthHelperObsVO.getTheTreatmentDT().setTreatmentUid(new
                    Long(i--));
                treatmenthHelperObsVO.setItNew(true);
                treatmenthHelperObsVO.setItDirty(false);
                //treatments.add(treatmenthHelperObsVO);

                ActRelationshipDT act131 = new ActRelationshipDT();
                act131.setSourceActUid(treatmenthHelperObsVO.getTheTreatmentDT().
                                       getTreatmentUid());
                act131.setSourceClassCd("TRMT");
                act131.setTargetActUid(morbidityReportObsDT.getObservationUid());
                act131.setTargetClassCd("OBS");
                act131.setTypeCd("TreatmentToMorb");
                act131.setTypeDescTxt("Treatment To Morbidity Report");
                act131.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                act131.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                act131.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                act131.setItNew(true);
                // add to the collection for the proxy
                actrelations.add(act131);

                ParticipationDT par100 = new ParticipationDT();
                par100.setActClassCd("TRMT");
                par100.setActUid(treatmenthHelperObsVO.getTheTreatmentDT().
                                 getTreatmentUid());
                par100.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                par100.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                par100.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
                par100.setSubjectEntityUid(personSubjectUid);
                par100.setTypeCd("SubjOfTrmt");
                par100.setTypeDescTxt("Treatment Subject");
                par100.setItNew(true);
                participations.add(par100);
              }
            }
          }
        }
        else //else everything else id set dirty
        {
          Collection<Object>  treatmentVOColl = helper.getTreatmentVOCollection();

          setTreatmentsForEdit(treatmentVOColl, treatments, batchTreeMap,
                               sharedInd);
        }
      }
    }

    try
    {
      Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
      morbidityUtil.setPatientRevisionForEdit(mprUid, personSubjectUid,
                                              newProxy,
                                              form, request);
    }
    catch (NullPointerException npe)
    {
      logger.fatal("cannot retrieve DSPatientPersonUID from object store");
    }

    /*****************************************************************
     * set the collections
     */
    newProxy.setTheObservationVOCollection(observations);
    newProxy.setTheParticipationDTCollection(participations);
    newProxy.setTheActRelationshipDTCollection(actrelations);
    newProxy.setTheTreatmentVOCollection(treatments);

    //LDF
    CommonLabUtil commonLabUtil = new CommonLabUtil();
    commonLabUtil.submitMorbLDF(newProxy,form, false, request);
    commonLabUtil.submitPatientLDFMorb(form.getPatient(), form, false, request);
    newProxy.setItNew(false);
    newProxy.setItDirty(true);

    observations = null;
    actrelations = null;
    participations = null;
    treatments = null;

    return newProxy;
  }


  /**
   * setBatchObservationForEdit
   *
   */
  private void setBatchObservationForEdit(Collection<ObservationVO>  inColl, Collection<ObservationVO>  outColl)
  {
    if (inColl != null)
    {
      for (Iterator<ObservationVO> itor = inColl.iterator(); itor.hasNext(); )
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        if (obsVO != null)
        {
          ObservationDT obsDT = obsVO.getTheObservationDT();

          if (obsVO.getTheObsValueCodedDTCollection() != null)
          {
           Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();

            while (it.hasNext())
            {
              ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
              obsValDT.setItNew(false);
              obsValDT.setItDirty(true);
              if (obsValDT.getCode() == null ||
                  obsValDT.getCode().trim().length() == 0)
              {
                obsValDT.setCode("NI");
              }
            }
          }

          if (obsVO.getTheObsValueDateDTCollection() != null)
          {

           Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();

            while (it.hasNext())
            {

              ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
              obsValDT.setItNew(false);
              obsValDT.setItDirty(true);
            }
          }

          if (obsVO.getTheObsValueNumericDTCollection() != null)
          {

           Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

            while (it.hasNext())
            {

              ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
              obsValDT.setItNew(false);
              obsValDT.setItDirty(true);
            }
          }

          if (obsVO.getTheObsValueTxtDTCollection() != null)
          {

           Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

            while (it.hasNext())
            {

              ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
              obsValDT.setItNew(false);
              obsValDT.setItDirty(true);
            }
          }

          obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
          obsDT.setStatusCd("D");

          obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          obsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
          obsDT.setItNew(false);
          obsDT.setItDirty(true);
          obsVO.setItNew(false);
          obsVO.setItDirty(true);

          outColl.add(obsVO);
        } //end of while loop
      } // end of if (obsColl != null)
    }
  }

//---------------Treatment--------------------------------------------------
  /**
   * sets negative tempID and newFlag to true for treatments
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */
  private void setTreatmentsForEdit(Collection<Object>  inColl, Collection<Object>  outColl,
                                    TreeMap<Object,Object> treeMap, String sharedInd)
  {
    TreatmentVO oldTreatmentVO = (TreatmentVO) treeMap.get("TreatmentVO");
    TreatmentDT oldTreatmentDT = oldTreatmentVO.getTheTreatmentDT();
    Long treatmentUID = oldTreatmentDT.getTreatmentUid();
    String progAreaCd = oldTreatmentDT.getProgAreaCd();
    String jurisdictionCd = oldTreatmentDT.getJurisdictionCd();
    Long oid = oldTreatmentDT.getProgramJurisdictionOid();

    if (inColl != null)
    {
      for (Iterator<Object> itor = inColl.iterator(); itor.hasNext(); )
      {
        TreatmentVO treatmentVO = (TreatmentVO) itor.next();
        TreatmentDT treatmentDT = treatmentVO.getTheTreatmentDT();
        treatmentDT.setTreatmentUid(treatmentUID);
        treatmentDT.setProgAreaCd(progAreaCd);
        treatmentDT.setJurisdictionCd(jurisdictionCd);
        treatmentDT.setProgramJurisdictionOid(oid);
        treatmentDT.setSharedInd(sharedInd);

        if (treatmentVO.getTheTreatmentAdministeredDTCollection() != null)
        {
          for (Iterator<Object> it = treatmentVO.
               getTheTreatmentAdministeredDTCollection().iterator(); it.hasNext(); )
          {
            TreatmentAdministeredDT treatmentAdministeredDT = (
                TreatmentAdministeredDT) it.next();
            treatmentAdministeredDT.setTreatmentUid(treatmentUID);
            treatmentAdministeredDT.setItNew(false);
            treatmentAdministeredDT.setItDirty(true);
          }
        }

        if (treatmentVO.getTheTreatmentProcedureDTCollection() != null)
        {
          for (Iterator<Object> it = treatmentVO.getTheTreatmentProcedureDTCollection().
               iterator(); it.hasNext(); )
          {
            TreatmentProcedureDT treatmentProcedureDT = (TreatmentProcedureDT)
                it.next();
            treatmentProcedureDT.setTreatmentUid(treatmentUID);
            treatmentProcedureDT.setItNew(false);
            treatmentProcedureDT.setItDirty(true);
          }
        }

        treatmentDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        treatmentDT.setStatusCd("D");
        treatmentDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
            getTime()));
        treatmentDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        PreDefinedTreatmentDT preDefinedTreatmentDT = null;
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
        preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(
            treatmentVO.getTheTreatmentDT().getCd());
        if (treatmentVO.getTheTreatmentDT().getCd() != null &&
            !treatmentVO.getTheTreatmentDT().getCd().equals("OTH"))
        {
          treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.
              getTheTreatmentDT().getCd() == null ? "" : " " +
              cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.
              getTheTreatmentDT().getCd()));
          treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.
              getCodeSystemCd());
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              preDefinedTreatmentDT.getCdSystemDescTxt());
          treatmentDT.setClassCd("TA");
        }
        else
        {
          treatmentDT.setClassCd(null);
          treatmentVO.getTheTreatmentDT().setCdSystemCd("NBS");
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              "NEDSS Base System");
        }
        treatmentDT.setItNew(false);
        treatmentDT.setItDirty(true);
        treatmentVO.setItNew(false);
        treatmentVO.setItDirty(true);
        outColl.add(treatmentVO);
      } //end of while loop
    } // end of if (obsColl != null)
  }

//---------------Treatment--------------------------------------------------
  /**
   * sets negative tempID and newFlag to true for treatments
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */
  private int setTreatmentsForCreate(Collection<Object>  inColl, Collection<Object>  outColl, String programAreaCd,
                                     int tempID)
  {
    if (inColl != null)
    {
      new MorbidityUtil().setPredefinedTreatment(inColl);
     Iterator<Object>  itor = inColl.iterator();
      while (itor.hasNext())
      {
        TreatmentVO treatmentVO = (TreatmentVO) itor.next();
        TreatmentDT treatmentDT = treatmentVO.getTheTreatmentDT();
        Timestamp effectiveFromTime = null;
        if (treatmentVO.getTheTreatmentAdministeredDTCollection() != null)
        {
         Iterator<Object>  it = treatmentVO.getTheTreatmentAdministeredDTCollection().
              iterator();

          while (it.hasNext())
          {
            TreatmentAdministeredDT treatmentAdministeredDT = (
                TreatmentAdministeredDT) it.next();
            effectiveFromTime = treatmentAdministeredDT.getEffectiveFromTime();
            treatmentAdministeredDT.setItNew(true);
            treatmentAdministeredDT.setItDirty(false);
            break;
          }
        }

        if (treatmentVO.getTheTreatmentProcedureDTCollection() != null)
        {
         Iterator<Object>  it = treatmentVO.getTheTreatmentProcedureDTCollection().
              iterator();

          while (it.hasNext())
          {
            TreatmentProcedureDT treatmentProcedureDT = (TreatmentProcedureDT)
                it.next();
            treatmentProcedureDT.setItNew(true);
            treatmentProcedureDT.setItDirty(false);
          }
        }

        if (effectiveFromTime != null)
        {
          treatmentDT.setActivityToTime(effectiveFromTime);
        }
        treatmentDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        treatmentDT.setStatusCd("D");
        treatmentDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
            getTime()));
        treatmentDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);


        treatmentDT.setTreatmentUid(new Long(tempID--));
        PreDefinedTreatmentDT preDefinedTreatmentDT = null;
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
        preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(
            treatmentVO.getTheTreatmentDT().getCd());
        if (treatmentVO.getTheTreatmentDT().getCd() != null &&
            !treatmentVO.getTheTreatmentDT().getCd().equals("OTH"))
        {
          treatmentDT.setClassCd("TA");
          treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.
              getTheTreatmentDT().getCd() == null ? "" : " " +
              cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.
              getTheTreatmentDT().getCd()));
          treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.
              getCodeSystemCd());
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              preDefinedTreatmentDT.getCdSystemDescTxt());
        }
        else
        {
          treatmentVO.getTheTreatmentDT().setCdSystemCd("NBS");
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              "NEDSS Base System");
        }
        treatmentDT.setProgAreaCd(programAreaCd);
        treatmentDT.setItNew(true);
        treatmentDT.setItDirty(false);
        treatmentVO.setItNew(true);
        treatmentVO.setItDirty(false);
        outColl.add(treatmentVO);
      } //end of while loop
    } // end of if (obsColl != null)
    return tempID;
  } // end of treatment

  //-----------------------------------------------------------------

  /** Send the proxy to the backend for persistance
   * @param proxy -- The fully populated Value Object
   * @request session -- The HTTP Session object
   * @return result -- The UID for the persisted data or return null if not persisted
   * @throws NEDSSAppConcurrentDataException
   * @throws RemoteException
   * @throws EJBException
   */
  private Collection<?>  sendProxyToEJB(MorbidityProxyVO proxy, HttpSession session) throws
      NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException, Exception
  {

    MainSessionCommand msCommand = null;

    //try {
    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    String sMethod = null;
    MainSessionHolder holder = new MainSessionHolder();
    ArrayList<?> resultUIDArr = new ArrayList<Object> ();

    sMethod = "setMorbidityProxy";
    Object[] oParams =
        {
        proxy};
    msCommand = holder.getMainSessionCommand(session);
    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

    for (int i = 0; i < resultUIDArr.size(); i++)
    {
      logger.info("Result " + i + " is: " + resultUIDArr.get(i));
    }

    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
    {
      Collection<?>  result = (Collection<?>) resultUIDArr.get(0);
      return result;
    }
    return null;
  } //sendProxyToEJB

  private int setBatchObservationForCreate(Collection<Object>  inColl,
                                           Collection<ObservationVO>  outColl,
                                           int tempID)
  {
    if (inColl != null)
    {
     Iterator<Object>  itor = inColl.iterator();

      while (itor.hasNext())
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        if (obsVO != null)
        {
          ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

          ObservationDT obsDT = obsVO.getTheObservationDT();

          if (obsVO.getTheObsValueCodedDTCollection() != null)
          {

           Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();

            while (it.hasNext())
            {
              ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
              obsValDT.setItNew(true);
              obsValDT.setItDirty(false);
              if (obsValDT.getCode() == null ||
                  obsValDT.getCode().trim().length() == 0)
              {
                obsValDT.setCode("NI");
              }
            }
          }

          if (obsVO.getTheObsValueDateDTCollection() != null)
          {
           Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();

            while (it.hasNext())
            {
              ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
              obsValDT.setItNew(true);
              obsValDT.setItDirty(false);
            }
          }

          if (obsVO.getTheObsValueNumericDTCollection() != null)
          {
           Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

            while (it.hasNext())
            {
              ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
              obsValDT.setItNew(true);
              obsValDT.setItDirty(false);
            }
          }

          if (obsVO.getTheObsValueTxtDTCollection() != null)
          {
           Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

            while (it.hasNext())
            {

              ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
              obsValDT.setItNew(true);
              obsValDT.setItDirty(false);
            }
          }

          obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
          obsDT.setStatusCd("D");
          obsDT.setObservationUid(new Long(tempID--));
          obsDT.setCdSystemCd("NBS");
          obsDT.setCdSystemDescTxt("NEDSS Base System");
          obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          obsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
          obsDT.setItNew(true);
          obsDT.setItDirty(false);
          obsVO.setItNew(true);
          obsVO.setItDirty(false);
          outColl.add(obsVO);
        } //end of while loop
      } // end of if (obsColl != null)
    }

    return tempID;
  }

}
