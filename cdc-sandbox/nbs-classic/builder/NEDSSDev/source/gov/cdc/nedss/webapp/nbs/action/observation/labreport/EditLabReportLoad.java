package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
//import gov.cdc.nedss.util.*;
//import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
//import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import java.util.*;
import java.io.*;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 * Title:        AddLabReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */


public class EditLabReportLoad
    extends CommonLabUtil
{

    //For logging
    static final LogUtils logger = new LogUtils(EditLabReportLoad.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException,
        ServletException {

      HttpSession session = request.getSession();





      if (session == null) {
        logger.fatal("error no session");

        return mapping.findForward("login");
      }

      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
      String userTypeValue = secObj.getTheUserProfile().getTheUser().getUserType();
     // boolean retainCheckbox;
      if (userTypeValue != null) {
        boolean reportExteranlUser = userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
        boolean displayFacility =    userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
        request.setAttribute("displayFacility",String.valueOf(displayFacility));
        if(reportExteranlUser == true)
                 reportExteranlUser=false;
        request.setAttribute("ReportExteranlUser",
                             String.valueOf(reportExteranlUser));
        try {
          Long userReportingFacilityUid = secObj.getTheUserProfile().getTheUser().
              getReportingFacilityUid();
          if (userReportingFacilityUid != null) {
            Map<Object,Object> results = getOrganization(String.valueOf(
                userReportingFacilityUid.longValue()), session);
            request.setAttribute("reportingFacilityUID",
                                 userReportingFacilityUid.toString());
            if (results != null) {
              request.setAttribute("reportingSourceDetails",
                                   (String) results.get("result"));
            }
          }
          else {
            request.setAttribute("reportingSourceDetails",
                                 "There is no Reporting Facility selected.");
          }

        }
        catch (Exception e) {
    		logger.error("Exception in EditLabReportLoad: " + e.getMessage());
    		e.printStackTrace();
        }
      }


      String contextAction = request.getParameter("ContextAction");
      request.setAttribute("currentTask", "EditObservation");
      //Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");

      Long observationUID = (Long)NBSContext.retrieve(session, "DSObservationUID");
      //Long observationUID = new Long(obsUID);
      LabResultProxyVO  labResultProxyVO = null;
      ObservationForm obsForm = (ObservationForm) aForm;
      Long lab112UID = null;
      try {
        labResultProxyVO = getLabResultProxyVO(observationUID, session);

        obsForm.setOldProxy(labResultProxyVO);

        ObservationVO obsLAB112 = (ObservationVO) convertProxyToObs112(labResultProxyVO, request);
        lab112UID = obsLAB112.getTheObservationDT().getObservationUid();
        request.setAttribute("hiddenOrderedTestNameCode", obsLAB112.getTheObservationDT().getCd());
        request.setAttribute("hiddenOrderedTestNameDesc", obsLAB112.getTheObservationDT().getCdDescTxt());
        request.setAttribute("hiddenOrderedTestCdSystemCd", obsLAB112.getTheObservationDT().getCdSystemCd());
        request.setAttribute("hiddenOrderedTestCdSystemDsec", obsLAB112.getTheObservationDT().getCdSystemDescTxt());
        request.setAttribute("observationLocalUID", obsLAB112.getTheObservationDT().getLocalId().toString());
        ArrayList<String> conditionList = labResultProxyVO.getTheConditionsList();
        if (conditionList != null) 
        	NBSContext.store(session, "DSConditionList",conditionList);
    	if ((obsLAB112.getTheObservationDT().getProgAreaCd() != null) && !obsLAB112.getTheObservationDT().getProgAreaCd().isEmpty()) 
    		NBSContext.store(session, "DSProgramArea",obsLAB112.getTheObservationDT().getProgAreaCd()); 	
        		CommonAction ca = new CommonAction();
    	request.setAttribute("PDLogic", ca.checkIfSyphilisIsInConditionList(obsLAB112.getTheObservationDT().getProgAreaCd(), conditionList, NEDSSConstants.LAB_REPORT));
        ParticipationDT part110 = getParticipationDT(obsLAB112.
            getTheParticipationDTCollection(), "PSN","PATSBJ","Patient Subject");
        PersonVO vo = this.getPerson(labResultProxyVO,
                                         part110.getSubjectEntityUid(), request,
                                         secObj);

        obsForm.setPatientRevision(vo);
        obsForm.setPatient(vo);
        LabResultProxyVO oldLabResultProxyVO =	(LabResultProxyVO)labResultProxyVO.deepCopy();

        obsForm.setProxy(oldLabResultProxyVO);

        //obsForm.reset();
        boolean permissionCheck = secObj.getPermission(
            NBSBOLookup.OBSERVATIONLABREPORT,
            NBSOperationLookup.EDIT,
            obsLAB112.getTheObservationDT().getProgAreaCd(),
            obsLAB112.getTheObservationDT().getJurisdictionCd(),
            obsLAB112.getTheObservationDT().getSharedInd());
          String electronicInd = obsLAB112.getTheObservationDT().getElectronicInd();

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

        request.setAttribute("formHref",
                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
                             tm.get("Submit"));
        request.setAttribute("Cancel",
                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
                             tm.get("Cancel"));
        convertProxyToRequestForEdit(labResultProxyVO, request, secObj);
        convertLAB214RequestObject(labResultProxyVO, request);

        //convertActIdInToRequestObject(labResultProxyVO, request);
        convertMaterialInToRequestObject(labResultProxyVO, request);
        ArrayList<Object> resultedTest = this.findResultedTest(obsForm, obsLAB112,
            labResultProxyVO, request);

        ArrayList<Object> batchResultedTest = this.findResultedTest(obsForm, obsLAB112,labResultProxyVO, request);
        //session.setAttribute("ResultedTestArray", resultedTest);

        if (batchResultedTest != null && batchResultedTest.size() > 0) {

          obsForm.setResultedTestVOCollection(null);

          String batchString = new String();
          batchString = convertDoubleBatchEntry(batchResultedTest,request);


          request.setAttribute("batchString", batchString.toString());

        }

        obsForm.setOldResultedTestVOCollection(resultedTest);
        //VOTester.createReport(labResultProxyVO, "labResultProxyVO from backend");
      }
      catch (Exception e) {
    	  logger.error("The Exception thrown in EditLabReportLoad:" + e.getMessage());
    	  e.printStackTrace();
    }
      //sets the ProgramArea and Jurisdiction to request from security object
     super.getNBSSecurityJurisdictionsPA(request, secObj, contextAction);
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
     
      return (mapping.findForward("XSP"));
    }

  private String convertDoubleBatchEntry(ArrayList<Object> resultedTest, HttpServletRequest request){
      StringBuffer batch = new StringBuffer();
      if(resultedTest != null){
       Iterator<Object>  resulted = resultedTest.iterator();
        while(resulted.hasNext()){
          ResultedTestVO obsVOLAB220 = (ResultedTestVO) resulted.next();
          ObservationVO isolatedVO = obsVOLAB220.getTheIsolateVO();
          ObservationVO susceptibilityVO = obsVOLAB220.getTheSusceptibilityVO();
          ObservationDT isolatedDT = isolatedVO.getTheObservationDT();

          batch.append("resultedTest[i].theSusceptibilityVO.theObservationDT.statusCd").append("~")
              .append("A")
              .append("^");

    batch.append("resultedTest[i].theIsolateVO.theObservationDT.searchResultRT").append("~")
        .append((isolatedDT.getCdDescTxt()==null)?"":isolatedDT.getCdDescTxt())
        .append("^");

    batch.append("resultedTest[i].theIsolateVO.theObservationDT.cdSystemCdRT").append("~")
        .append( (isolatedDT.getCdDescTxt() == null) ? "" : isolatedDT.getCdSystemCd())
        .append("^");

    batch.append("resultedTest[i].theIsolateVO.theObservationDT.hiddenCd").append("~")
        .append( (isolatedDT.getCdDescTxt() == null) ? "" : isolatedDT.getCd())
        .append("^");

          request.setAttribute("resultedTestName",isolatedDT.getCdDescTxt());
          
          if(isolatedDT.getCtrlCdUserDefined1()!=null && isolatedDT.getCtrlCdUserDefined1().equalsIgnoreCase("N")){
            batch.append("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code").
                append("~")
                .append( (isolatedVO.getObsValueCodedDT_s(0).getCode() != null
                          &&
                          !isolatedVO.getObsValueCodedDT_s(0).getCode().
                          equalsIgnoreCase("NI")) ?
                        isolatedVO.getObsValueCodedDT_s(0).getCode() : "")
                .append("^");
          }
          else{
            batch.append(
                "resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").
                append("~")
                .append( (isolatedVO.getObsValueCodedDT_s(0).getCode() != null
                          &&
                          !isolatedVO.getObsValueCodedDT_s(0).getCode().
                          equalsIgnoreCase("NI")) ?
                        isolatedVO.getObsValueCodedDT_s(0).getCode() : "")
                .append("^");

						batch.append("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd").append("~").
								append(isolatedVO.getObsValueCodedDT_s(0).getDisplayName()).append("^");
						batch.append("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].cdSystemCdRT").append("~").
								append(isolatedVO.getObsValueCodedDT_s(0).getCodeSystemCd()).append("^");
						batch.append("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].searchResultRT").append("~").
								append(isolatedVO.getObsValueCodedDT_s(0).getCode()).append("^");
						batch.append("resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1").append("~").
								append(isolatedDT.getCtrlCdUserDefined1()).append("^");
          }
         // ObsValueNumericDT  obsValueNumericDT=	isolatedVO.getObsValueNumericDT_s(0);
          StringBuffer appendString = new StringBuffer();
          ObsValueNumericDT  obsValueNumericDT=	isolatedVO.getObsValueNumericDT_s(0);
          appendString.append(obsValueNumericDT.getComparatorCd1() == null?"":obsValueNumericDT.getComparatorCd1().trim());
          appendString.append(obsValueNumericDT.getNumericValue1()== null?"":obsValueNumericDT.getNumericValue1().toString().trim());
          appendString.append(obsValueNumericDT.getSeparatorCd()==null?"":obsValueNumericDT.getSeparatorCd().trim());
          appendString.append(obsValueNumericDT.getNumericValue2()==null?"":obsValueNumericDT.getNumericValue2().toString().trim());

          batch.append("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue").append("~")
              .append(appendString)
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd").append("~")
              .append((isolatedVO.getObsValueNumericDT_s(0).getNumericUnitCd())==null?"":isolatedVO.getObsValueNumericDT_s(0).getNumericUnitCd())
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt").append("~")
              .append((isolatedVO.getObsValueTxtDT_s(0).getValueTxt())==null?"":isolatedVO.getObsValueTxtDT_s(0).getValueTxt())
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].lowRange").append("~")
              .append((isolatedVO.getObsValueNumericDT_s(0).getLowRange())==null?"":isolatedVO.getObsValueNumericDT_s(0).getLowRange())
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].highRange").append("~")
              .append((isolatedVO.getObsValueNumericDT_s(0).getHighRange())==null?"":isolatedVO.getObsValueNumericDT_s(0).getHighRange())
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.theObservationDT.statusCd").append("~")
              .append((isolatedVO.getTheObservationDT().getStatusCd())==null?"":isolatedVO.getTheObservationDT().getStatusCd())
              .append("^");

          batch.append("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").append("~")
              .append((susceptibilityVO.getObsValueCodedDT_s(0).getCode()!=null
                       &&!susceptibilityVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("NI"))?
                      susceptibilityVO.getObsValueCodedDT_s(0).getCode():"")
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.obsValueTxtDT_s[1].valueTxt").append("~")
              .append((isolatedVO.getObsValueTxtDT_s(1).getValueTxt())==null?"":isolatedVO.getObsValueTxtDT_s(1).getValueTxt())
              .append("^");
          batch.append("resultedTest[i].theIsolateVO.theObservationDT.observationUid").append("~")
              .append(isolatedVO.getTheObservationDT().getObservationUid())
              .append("^");
          batch.append("resultedTest[i].theSusceptibilityVO.theObservationDT.observationUid").append("~")
                .append(susceptibilityVO.getTheObservationDT().getObservationUid())
                .append("^");
            batch.append("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").append("~")
              .append((susceptibilityVO.getObsValueCodedDT_s(0).getCode()==null)?"":susceptibilityVO.getObsValueCodedDT_s(0).getCode())
              .append("^");
            batch.append("resultedTest[i].theSusceptibilityVO.theObservationDT.cd").append("~")
            .append((susceptibilityVO.getTheObservationDT().getCd()==null)?"":susceptibilityVO.getTheObservationDT().getCd())
            .append("^");
            batch.append("resultedTest[i].theSusceptibilityVO.theObservationDT.cdDescTxt").append("~")
            .append((susceptibilityVO.getTheObservationDT().getCdDescTxt()==null)?"":susceptibilityVO.getTheObservationDT().getCdDescTxt())
            .append("^");
            Collection<Object>  obsTrackIsolateColl = obsVOLAB220.getTheTrackIsolateCollection();
            //batch.append("[");
            
            HashMap<Object,Object> obsMap = (HashMap<Object,Object>)ConvertIsolateTrackInfo(obsTrackIsolateColl);
            
            String isolateString = convertIsolateToBatch(obsVOLAB220,obsMap);
            batch.append(isolateString);
           // batch.append("]");
          Collection<Object>  obsLAB222 = obsVOLAB220.getTheSusceptibilityCollection();
          batch.append("[");
          if(obsLAB222!=null && obsLAB222.size()>0){
          if(obsLAB222 != null){
           Iterator<Object>  obsLAB222Itrator = obsLAB222.iterator();



            while (obsLAB222Itrator.hasNext()) {
              ObservationVO obsVOLAB110 = (ObservationVO) obsLAB222Itrator.next();

              batch.append(
                  "resultedTest[i].susceptibility[j].theObservationDT.methodCd")
                  .append("~")
                  .append( (obsVOLAB110.getTheObservationDT().getMethodCd()) == null ?
                          "" : obsVOLAB110.getTheObservationDT().getMethodCd()).
                  append("^");
            if(obsVOLAB110.getTheObservationDT().getCd()!= null)
              {
                obsVOLAB110.getTheObservationDT().setSearchResultOT(obsVOLAB110.getTheObservationDT().getCdDescTxt());
                //obsVOLAB110.getTheObservationDT().setCdSystemCdRT(obsVOLAB110.getTheObservationDT().getCd());
                obsVOLAB110.getTheObservationDT().setHiddenCd(obsVOLAB110.getTheObservationDT().getCd());
                //obsVOLAB110.getTheObservationDT().setCd(null);

              }
              batch.append(
                        "resultedTest[i].susceptibility[j].theObservationDT.hiddenCd")
                        .append("~").
                        append( (obsVOLAB110.getTheObservationDT().getCd() == null) ?
                               "" : obsVOLAB110.getTheObservationDT().getCd()).
                        append("^");

              batch.append(
                       "resultedTest[i].susceptibility[j].theObservationDT.searchResultRT")
                       .append("~").
                       append( (obsVOLAB110.getTheObservationDT().getSearchResultOT() == null) ?
                              "" : obsVOLAB110.getTheObservationDT().getSearchResultOT()).
                       append("^");
             batch.append(
                   "resultedTest[i].susceptibility[j].theObservationDT.cdSystemCdRT")
                    .append("~").
                    append( (obsVOLAB110.getTheObservationDT().getCdSystemCdRT() == null) ?
                           "" : obsVOLAB110.getTheObservationDT().getCdSystemCdRT()).
                   append("^");

          StringBuffer appendString1 = new StringBuffer();
          ObsValueNumericDT  obsValueNumericDT1 =obsVOLAB110.getObsValueNumericDT_s(0);
          appendString1.append(obsValueNumericDT1.getComparatorCd1() == null?"":obsValueNumericDT1.getComparatorCd1().trim());
          appendString1.append(obsValueNumericDT1.getNumericValue1()== null?"":obsValueNumericDT1.getNumericValue1().toString().trim());
          appendString1.append(obsValueNumericDT1.getSeparatorCd()==null?"":obsValueNumericDT1.getSeparatorCd().trim());
          appendString1.append(obsValueNumericDT1.getNumericValue2()==null?"":obsValueNumericDT1.getNumericValue2().toString().trim());
          batch.append("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue").append("~")
              .append(appendString1)
              .append("^");

              batch.append(
                  "resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd")
                  .append("~")
                  .append( (obsVOLAB110.getObsValueNumericDT_s(0).
                            getNumericUnitCd()) == null ? "" :
                          obsVOLAB110.getObsValueNumericDT_s(0).getNumericUnitCd()).
                  append("^");
              batch.append("resultedTest[i].susceptibility[j].obsValueCodedDT_s[0].code")
                  .append("~")
                  .append((obsVOLAB110.getObsValueCodedDT_s(0).getCode() != null
                           && !obsVOLAB110.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("NI")) ?
                           obsVOLAB110.getObsValueCodedDT_s(0).getCode() :"")
                  .append("^");
              batch.append(
                  "resultedTest[i].susceptibility[j].observationInterpDT_s[0].interpretationCd")
                  .append("~")
                  .append( (obsVOLAB110.getObservationInterpDT_s(0).
                            getInterpretationCd()) == null ? "" :
                          obsVOLAB110.getObservationInterpDT_s(0).
                          getInterpretationCd())
                  .append("^");
              batch.append(
                  "resultedTest[i].susceptibility[j].theObservationDT.statusCd").
                  append("~")
                  .append(obsVOLAB110.getTheObservationDT().getStatusCd())
                  .append("^");
              batch.append(
                  "resultedTest[i].susceptibility[j].theObservationDT.observationUid")
                  .append("~")
                  .append(obsVOLAB110.getTheObservationDT().getObservationUid())
                  .append("^");

              if(obsLAB222Itrator.hasNext())
              {
                batch.append("|");
              }

            }
          }
        }
          batch.append("]");   
          batch.append("|");
        }//End of While Main Loop
      }
    return batch.toString();
    }
 
private Map<Object,Object> ConvertIsolateTrackInfo(Collection<Object>  obsTrackIsolateColl){
	HashMap<Object,Object> map = new HashMap<Object,Object>();
	if(obsTrackIsolateColl!=null){
		Iterator<Object> it = obsTrackIsolateColl.iterator();
		while(it.hasNext()){
			ObservationVO trackIsolateVO = (ObservationVO)it.next();
			map.put(trackIsolateVO.getTheObservationDT().getCd(), trackIsolateVO);
		}
	}
	return map;
}
	private String convertIsolateToBatch(ResultedTestVO obsVOLAB220, Map<Object,Object>  obsMap){
		StringBuffer batch = new StringBuffer();
		ObservationVO obsTrackVO =null;
		int i = 0;
		StringBuffer buff = new StringBuffer();
		if(obsVOLAB220.getTheLab329VO()!=null){
			try {
				obsTrackVO = obsVOLAB220.getTheLab329VO();
				
				buff.append("^").append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.cd").
				append("~")
				.append( obsTrackVO.getTheObservationDT().getCd()).append("^");
				
				buff.append("resultedTest[i].theTrackIsolate["+ i++ +"].theObservationDT.observationUid").
				append("~")
				.append( (obsTrackVO != null) ?
						obsTrackVO.getTheObservationDT().getObservationUid().toString() : "").append("^");
			} catch (Exception e) {
				logger.error("Exception thrown"+ e);
				e.printStackTrace();
			} 
			batch.append(buff.toString());
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null,"NOLAB329ADDED"));
		}
		if(obsMap.get(NEDSSConstants.LAB_329A)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_329A);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_329A));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null,NEDSSConstants.LAB_329A));
		}
		if(obsMap.get(NEDSSConstants.LAB_331)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_331);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_331));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_331));
		}
		if(obsMap.get(NEDSSConstants.LAB_332)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_332);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO,NEDSSConstants.LAB_332));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_332));
		}
		if(obsMap.get(NEDSSConstants.LAB_333)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_333);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_333));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_333));
		}	
		if(obsMap.get(NEDSSConstants.LAB_334)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_334);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_334));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_334));
		}	
		if(obsMap.get(NEDSSConstants.LAB_335)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_335);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_335));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_335));
		}	
		if(obsMap.get(NEDSSConstants.LAB_336)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_336);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_336));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_336));
		}
		if(obsMap.get(NEDSSConstants.LAB_337)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_337);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_337));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_337));
		}
		if(obsMap.get(NEDSSConstants.LAB_338)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_338);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_338));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_338));
		}
		if(obsMap.get(NEDSSConstants.LAB_339)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_339);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_339));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_339));
		}	
		if(obsMap.get(NEDSSConstants.LAB_340)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_340);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_340));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_340));
		}	
		if(obsMap.get(NEDSSConstants.LAB_341)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_341);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_341));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_341));
		}	
		if(obsMap.get(NEDSSConstants.LAB_342)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_342);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_342));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_342));
		}	
		if(obsMap.get(NEDSSConstants.LAB_343)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_343);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_343));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_343));
		}	
		if(obsMap.get(NEDSSConstants.LAB_344)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_344);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_344));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_344));
		}
		if(obsMap.get(NEDSSConstants.LAB_345)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_345);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_345));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_345));
		}
		if(obsMap.get(NEDSSConstants.LAB_346)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_346);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_346));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_346));
		}
		if(obsMap.get(NEDSSConstants.LAB_347)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_347);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_347));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_347));
		}
		if(obsMap.get(NEDSSConstants.LAB_348)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_348);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_348));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_348));
		}
		if(obsMap.get(NEDSSConstants.LAB_349)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_349);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_349));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_349));
		}		
		if(obsMap.get(NEDSSConstants.LAB_350)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_350);
			 batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_350));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_350));
		}	
		if(obsMap.get(NEDSSConstants.LAB_351)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_351);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_351));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_351));
		}
		if(obsMap.get(NEDSSConstants.LAB_352)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_352);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_352));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_352));
		}
		if(obsMap.get(NEDSSConstants.LAB_353)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_353);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_353));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_353));
		}
		if(obsMap.get(NEDSSConstants.LAB_354)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_354);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_354));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_354));
		}	
		if(obsMap.get(NEDSSConstants.LAB_355)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_355);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_355));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_355));
		}	
		if(obsMap.get(NEDSSConstants.LAB_356)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_356);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_356));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_356));
		}	
		if(obsMap.get(NEDSSConstants.LAB_357)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_357);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_357));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_357));
		}	
		if(obsMap.get(NEDSSConstants.LAB_358)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_358);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_358));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_358));
		}	
		if(obsMap.get(NEDSSConstants.LAB_359)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_359);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_359));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_359));
		}	
		if(obsMap.get(NEDSSConstants.LAB_360)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_360);
			batch.append(convertObsValueTxtToRequest(i++,obsTrackVO, NEDSSConstants.LAB_360));
		}
		else{
			batch.append(convertObsValueTxtToRequest(i++,null, NEDSSConstants.LAB_360));
		}	
		if(obsMap.get(NEDSSConstants.LAB_361)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_361);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_361));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_361));
		}	
		if(obsMap.get(NEDSSConstants.LAB_362)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_362);
			batch.append(convertObsValueDateToRequest(i++,obsTrackVO, NEDSSConstants.LAB_362));
		}
		else{
			batch.append(convertObsValueDateToRequest(i++,null, NEDSSConstants.LAB_362));
		}	
		//LAB363 added as part of R2.0 (civil00016734)		
		if(obsMap.get(NEDSSConstants.LAB_363)!=null){
			 obsTrackVO = (ObservationVO)obsMap.get(NEDSSConstants.LAB_363);
			batch.append(convertObsValueCodeToRequest(i++,obsTrackVO, NEDSSConstants.LAB_363));
		}
		else{
			batch.append(convertObsValueCodeToRequest(i++,null, NEDSSConstants.LAB_363));
		}
		
		return batch.toString();
	}

private String convertObsValueCodeToRequest(int i, ObservationVO obstrackIsolateVO, String code){
	StringBuffer buff = new StringBuffer();
	
	try {
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].obsValueCodedDT_s[0].code").
		append("~")
		.append( (obstrackIsolateVO!= null) ?
		        		  obstrackIsolateVO.getObsValueCodedDT_s(0).getCode() : "").append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.cd").
		append("~")
		.append( code).append("^");
		buff.append("resultedTest[i].theTrackIsolate["+i +"]theObservationDT.cdDescTxt").
		append("~")
		.append((obstrackIsolateVO !=null) ?
				obstrackIsolateVO.getTheObservationDT().getCdDescTxt() : "").append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.observationUid").
		append("~")
		.append( (obstrackIsolateVO != null) ?
				obstrackIsolateVO.getTheObservationDT().getObservationUid().toString() : "").append("^");
	} catch (Exception e) {
		logger.error("Exception thrown"+ e);
		e.printStackTrace();
	} 
	return buff.toString();
}

private String convertObsValueTxtToRequest(int i ,ObservationVO obstrackIsolateVO,String code){
	StringBuffer buff = new StringBuffer();
	 
	try {
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].obsValueTxtDT_s[0].valueTxt").
		append("~")
		.append( (obstrackIsolateVO!= null) ?
		        		  obstrackIsolateVO.getObsValueTxtDT_s(0).getValueTxt() : "")
		.append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.cd").
		append("~")
		.append( code).append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+i +"]theObservationDT.cdDescTxt").
		append("~")
		.append((obstrackIsolateVO !=null) ?
				obstrackIsolateVO.getTheObservationDT().getCdDescTxt() : "").append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.observationUid").
		append("~")
		.append( (obstrackIsolateVO != null) ?
				obstrackIsolateVO.getTheObservationDT().getObservationUid().toString() : "")
		.append("^");
	} catch (Exception e) {
		logger.error("Exception thrown"+ e);
		e.printStackTrace();
	}
	return buff.toString();
}
private String convertObsValueDateToRequest(int i, ObservationVO obstrackIsolateVO, String code){
	StringBuffer buff = new StringBuffer();
	
	try {
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].obsValueDateDT_s[0].fromTime_s").
		append("~")
		.append( (obstrackIsolateVO!=null && obstrackIsolateVO.getObsValueDateDT_s(0).getFromTime() != null) ?
				formatDate(obstrackIsolateVO.getObsValueDateDT_s(0).getFromTime()): "")
		.append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.cd").
		append("~")
		.append(code).append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+i +"]theObservationDT.cdDescTxt").
		append("~")
		.append((obstrackIsolateVO !=null) ?
				obstrackIsolateVO.getTheObservationDT().getCdDescTxt() : "").append("^");
		
		buff.append("resultedTest[i].theTrackIsolate["+ i +"].theObservationDT.observationUid").
		append("~")
		.append( (obstrackIsolateVO != null) ?
				obstrackIsolateVO.getTheObservationDT().getObservationUid().toString() : "")
		.append("^");
	} catch (Exception e) {
		logger.error("Exception thrown"+ e);
		e.printStackTrace();
	}
	return buff.toString();
} 
private ArrayList<Object> findResultedTest(ObservationForm obsForm,ObservationVO obsVO112,LabResultProxyVO labResultProxyVO,HttpServletRequest request){
     ArrayList<Object> resultedTestVOCollection  = new ArrayList<Object> ();
     Collection<ObservationVO>  obsColl = labResultProxyVO.getTheObservationVOCollection();
     

     if(obsVO112 == null)
        return null;
     if(obsVO112.getTheActRelationshipDTCollection() == null)
       return null;
    Iterator<Object>  obsVO112Iterator = obsVO112.getTheActRelationshipDTCollection().iterator();
     obsForm.setOldPatientStatusVO(null);
     while (obsVO112Iterator.hasNext())
     {
    	 Collection<Object>  susceptibilityCollection  = new ArrayList<Object> ();
    	 Collection<Object>  trackIsolateCollection  = new ArrayList<Object> ();
    	 

       ActRelationshipDT actrelLAB112 = (ActRelationshipDT)obsVO112Iterator.next();
       if (actrelLAB112 == null)
            continue;
       ResultedTestVO resultedTestVO  = new ResultedTestVO();
       if(actrelLAB112.getTypeCd().equals("COMP")){
           Long sourceActUidLAB112 = actrelLAB112.getSourceActUid();
           //System.out.println("\n\n  sourceActUidLAB112  " + sourceActUidLAB112);

           ObservationVO obsResultDependentVO = fetchObservationVO(sourceActUidLAB112, obsColl);
           if(obsResultDependentVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_330))
           {
        	   request.setAttribute(NEDSSConstants.LAB_330, obsResultDependentVO.getObsValueCodedDT_s(0).getCode());
        	   obsForm.setOldPatientStatusVO(obsResultDependentVO);
           	continue;
           }
           resultedTestVO.setTheIsolateVO(obsResultDependentVO);
           
           if(obsResultDependentVO != null && obsResultDependentVO.getTheActRelationshipDTCollection() != null){

            Iterator<Object>  obsVOLAB220Iterator = obsResultDependentVO.getTheActRelationshipDTCollection().iterator();

             while(obsVOLAB220Iterator.hasNext()){
               ActRelationshipDT actrelLAB220 = (ActRelationshipDT)obsVOLAB220Iterator.next();
               Long sourceActUidLAB220 = actrelLAB220.getSourceActUid();
               //System.out.println("\n\n  sourceActUidLAB220  " + sourceActUidLAB220);

               ObservationVO obsRTDependentVO = fetchObservationVO(sourceActUidLAB220, obsColl);
               if(obsRTDependentVO!=null && obsRTDependentVO.getTheObservationDT().getCd()!=null 
            		   && obsRTDependentVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){
            	   resultedTestVO.setTheLab329VO(obsRTDependentVO);
               }
               else{
            	   resultedTestVO.setTheSusceptibilityVO(obsRTDependentVO);
               }
            	   if(obsRTDependentVO != null && obsRTDependentVO.getTheActRelationshipDTCollection() != null){

            		  Iterator<Object>  obsVOLAB222Iterator = obsRTDependentVO.getTheActRelationshipDTCollection().iterator();

            		   while(obsVOLAB222Iterator.hasNext()){
            			   ActRelationshipDT actrelLAB222 = (ActRelationshipDT)obsVOLAB222Iterator.next();
            			   Long sourceActUidLAB222 = actrelLAB222.getSourceActUid();
            			   //System.out.println("\n\n  sourceActUidLAB222  " + sourceActUidLAB222);
            			   ObservationVO obsChildVO = fetchObservationVO(sourceActUidLAB222, obsColl);
            			   if(obsChildVO.getTheObservationDT().getCtrlCdDisplayForm()!=null 
            					   && obsChildVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LAB_REPORT))
            				   susceptibilityCollection.add(obsChildVO);
            			   else
            				   trackIsolateCollection.add(obsChildVO);
            		   }
            		   resultedTestVO.setTheSusceptibilityCollection(susceptibilityCollection);
            		   resultedTestVO.setTheTrackIsolateCollection(trackIsolateCollection);
            	   }
               	}
       		}//end susceptability collection
         }
       	else 
    	   continue;
      	resultedTestVOCollection.add(resultedTestVO);
     } // End while Loop obsVO112Iterator.hasNext()
     return resultedTestVOCollection;
    }

    /**
     * @method      : fetchObservationVO
     * @params      : ObservationUID, ObservationVOCollection
     * @returnType  : ObservationVO
     */
    private ObservationVO fetchObservationVO(Long sUID, Collection<ObservationVO>  obsColl)
    {

       Iterator<ObservationVO>  obsIter = obsColl.iterator();

        while (obsIter.hasNext())
        {

            ObservationVO observationVO = (ObservationVO)obsIter.next();

            if (observationVO.getTheObservationDT().getObservationUid().compareTo(
                        sUID) == 0)

                return observationVO;
        }

        return null;
    } //fetchObservationVO



  private PersonVO getPerson(LabResultProxyVO labResultProxyVO,
                             Long personUID,
                             HttpServletRequest request,
                             NBSSecurityObj secObj) throws ServletException{
    PersonVO personVO = null;
    ArrayList<Object> stateList = new ArrayList<Object> ();
    ArrayList<Object> list = (ArrayList<Object> )labResultProxyVO.getThePersonVOCollection();
   Iterator<Object>  it = list.iterator();
      while(it.hasNext())
      {
        PersonVO pvo = (PersonVO)it.next();
        if(pvo.getThePersonDT().getCd().equalsIgnoreCase(NEDSSConstants.PAT)){
          pvo = this.findMasterPatientRecord(pvo.getThePersonDT().getPersonUid(),
                                             request.getSession(), secObj);
          if(pvo.getThePersonDT().getPersonUid().equals(personUID)){
        	  try {
            PersonUtil.convertPersonToRequestObj(pvo, request,
                                                 "AddPatientFromEvent", stateList);
        	  }catch(Exception ex) {
      			throw new ServletException(ex.getMessage());
      		}
            return pvo;
          }
        }
      }
      return personVO;
  }



}
