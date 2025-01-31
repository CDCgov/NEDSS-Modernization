package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.entity.organization.util.*;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.exception.*;

import org.apache.struts.action.*;

/**
 * Title:        AddLabReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */


public class AddCommentSubmit
    extends Action
{

  //For logging
  static final LogUtils logger = new LogUtils(AddCommentSubmit.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {

    HttpSession session = request.getSession();
    if (session == null) {
      logger.fatal("error no session");

      return mapping.findForward("login");
    }
    String contextAction = null;
    contextAction = request.getParameter("ContextAction");
    String observationType = request.getParameter("observationType");
    //System.out.println("observationType="+observationType);
    //System.out.println("\nAddCommentSubmit and contextAction :" +     contextAction);
    if (contextAction == null)
      contextAction = (String) request.getAttribute("ContextAction");
    if(contextAction == null || contextAction.equals("Load"))
    	return mapping.findForward("Load");
    String sCurrTask = NBSContext.getCurrentTask(session);
    //System.out.println("sCurrTask: " + sCurrTask);
    NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
    AddCommentForm commentForm = (AddCommentForm)aForm;
    String comment = commentForm.getComment();
    //System.out.println("comment ="+comment);
    //String sobsUid = (String)NBSContext.retrieve(session,"DSObservationUID");
    Long obsUid =new Long(NBSContext.retrieve(session,"DSObservationUID").toString());
    //System.out.println("obsUid="+obsUid);
    if(contextAction.equals("Submit")){

      //get the ProxyVo
      AbstractVO proxyVO = null;
      if(observationType.equals("Lab"))
        proxyVO  = this.getLabResultProxyVO(obsUid,session);
      else if(observationType.equals("Morb"))
        proxyVO  = this.getMorbidityProxyVO(obsUid,session);
      ObservationVO rootObsVO = this.getRootObservationVO(proxyVO, obsUid);

      //now set the comment
      ObservationVO resTestVo = new ObservationVO();
      ObservationDT resTestdt = new ObservationDT();
      resTestdt.setObservationUid(new Long(-2));
      resTestdt.setCdSystemCd("NBS");
      resTestdt.setStatusCd("D");//changed as per WPD 8505
      resTestdt.setCdDescTxt("User Report Comment");
      resTestdt.setCdSystemDescTxt("NEDSS Base System");
      resTestdt.setObsDomainCdSt1("C_Result");
      resTestdt.setAddUserId(new Long(secObj.getTheUserProfile().getTheUser().getEntryID()));
      if(observationType.equals("Lab"))
        resTestdt.setCd("LAB214");
      else if(observationType.equals("Morb"))
        resTestdt.setCd("MRB180");
      resTestdt.setEffectiveFromTime(new java.sql.Timestamp(new Date().getTime()));
      resTestdt.setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
      resTestdt.setRptToStateTime(new java.sql.Timestamp(new Date().getTime()));
      resTestdt.setItNew(true);
      resTestdt.setItDirty(false);
      resTestVo.setTheObservationDT(resTestdt);

      //create obs value test collection
      ObsValueTxtDT ovtDT = new ObsValueTxtDT();
      ovtDT.setObservationUid(resTestVo.getTheObservationDT().getObservationUid());
      ovtDT.setObsValueTxtSeq(new Integer(1));
      ovtDT.setItNew(true);
      ovtDT.setItDirty(false);
      ovtDT.setValueTxt(comment);
      ovtDT.setTxtTypeCd("N");
      ArrayList<Object> ovtList = new ArrayList<Object> ();
      ovtList.add(ovtDT);
      //add that collection to the resulted test
      resTestVo.setItNew(true);
      resTestVo.setItDirty(false);
      resTestVo.setTheObsValueTxtDTCollection(ovtList);

      //create observationVO for the ordered test

      ObservationVO ordTestVO = new ObservationVO();
      ObservationDT ordTestDT = new ObservationDT();
      ordTestDT.setObservationUid(new Long(-1));
      ordTestDT.setCdSystemCd("2.16.840.1.113883");// changed as per WPD
      ordTestDT.setStatusCd("D");// changed as per WPD 8505
      ordTestDT.setCdDescTxt("No Information Given");
      ordTestDT.setCdSystemDescTxt("Health Level Seven");
      ordTestDT.setObsDomainCdSt1("C_Order");
      if(observationType.equals("Lab"))
        ordTestDT.setCtrlCdDisplayForm("LabComment");
      else if(observationType.equals("Morb"))
        ordTestDT.setCtrlCdDisplayForm("MorbComment");

      ordTestDT.setCd("NI");
      ordTestDT.setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
      ordTestDT.setItNew(true);
      ordTestDT.setItDirty(false);
      ordTestVO.setItNew(true);
      ordTestVO.setItDirty(false);
      ordTestVO.setTheObservationDT(ordTestDT);


      //code to get around electronic ind not being set
      rootObsVO.getTheObservationDT().setElectronicInd("E");
      //create the act relationship between the ordered test and the result test
      ActRelationshipDT arDT = new ActRelationshipDT();
      arDT.setSourceActUid(resTestVo.getTheObservationDT().getObservationUid());
      arDT.setTargetActUid(ordTestVO.getTheObservationDT().getObservationUid());
      arDT.setTypeCd("COMP");
      arDT.setTypeDescTxt("Has Component");
      arDT.setRecordStatusCd("ACTIVE");
      arDT.setStatusCd("A");//may not be in spec, but we prob need
      arDT.setSourceClassCd("OBS");
      arDT.setTargetClassCd("OBS");
      arDT.setItNew(true);
      arDT.setItDirty(false);

      //create the act relationship between the ordered test and the root observation
      ActRelationshipDT arRootDT = new ActRelationshipDT();
      arRootDT.setSourceActUid(ordTestVO.getTheObservationDT().getObservationUid());
      arRootDT.setTargetActUid(rootObsVO.getTheObservationDT().getObservationUid());
      arRootDT.setTypeCd("APND");
      arRootDT.setSourceClassCd("OBS");
      arRootDT.setTargetClassCd("OBS");
      arRootDT.setTypeDescTxt("Appends");
      arRootDT.setRecordStatusCd("ACTIVE");
      arRootDT.setStatusCd("A");
      arRootDT.setItNew(true);
      arRootDT.setItDirty(false);


      //create the act relationship between the ordered test and the root observation

      //add the act relationship to the ar collection on the proxy
      Collection<Object>  arDTColl = new ArrayList<Object> ();
      arDTColl.add(arDT);
      arDTColl.add(arRootDT);


      //add the resulted test and ordered test vo to the collection
      Collection<Object>  obsVOColl = new ArrayList<Object> ();
      obsVOColl.add(ordTestVO);
      obsVOColl.add(resTestVo);
      obsVOColl.add(rootObsVO);


      //persist the lab result proxy
      try{
        this.sendAddUserCommentsToEJB(obsVOColl, arDTColl,session);
      }
      catch(Exception e){
        logger.error("error occurred in Add CommentSubmit. sendLabProxyToEJB");
        e.printStackTrace();
      }
    }
    session.setAttribute("MorbReportAddCommentDSTab","2");
    return mapping.findForward("XSP");

  }
 private LabResultProxyVO getLabResultProxyVO(Long observationUID,
                                               HttpSession session) {
     LabResultProxyVO labResultProxyVO = null;
     MainSessionCommand msCommand = null;

     if (observationUID == null) {
       logger.error("observationUID is null inside getLabResultProxyVO method");
       return null;
     }
     else if (observationUID != null) {
       try {
         logger.debug("observationUID inside getLabResultProxyVO method is: " +
                      observationUID);

         String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
         String sMethod = "getLabResultProxy";
         Object[] oParams = new Object[] {
             observationUID};
         MainSessionHolder holder = new MainSessionHolder();
         msCommand = holder.getMainSessionCommand(session);

         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                                  sMethod, oParams);
         labResultProxyVO = (LabResultProxyVO) arr.get(0);
       }
       catch (Exception ex) {

         if (session == null) {
           logger.debug("Error: no session, please login");

         }
         logger.fatal("getLabResultProxyVO: ", ex);
       }
     }
     return labResultProxyVO;
   }

   private MorbidityProxyVO getMorbidityProxyVO(Long observationUID,
                                                 HttpSession session) {
       MorbidityProxyVO morbidityProxyVO = null;
       MainSessionCommand msCommand = null;

       if (observationUID == null) {
         logger.error("observationUID is null inside getLabResultProxyVO method");
         return null;
       }
       else if (observationUID != null) {
         try {
           logger.debug("observationUID inside getLabResultProxyVO method is: " +
                        observationUID);

           String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
           String sMethod = "getMorbidityProxy";
           Object[] oParams = new Object[] {
               observationUID};
           MainSessionHolder holder = new MainSessionHolder();
           msCommand = holder.getMainSessionCommand(session);

           ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                                    sMethod, oParams);
           morbidityProxyVO = (MorbidityProxyVO) arr.get(0);
         }
         catch (Exception ex) {

           if (session == null) {
             logger.debug("Error: no session, please login");

           }
           logger.fatal("getMorbidityProxyVO: ", ex);
         }
       }
       return morbidityProxyVO;
     }


   private Long sendAddUserCommentsToEJB(Collection<Object>  observationVOCollection,
                              Collection<Object>  actRelationshipDTCollection,HttpSession session) throws
     NEDSSAppConcurrentDataException, java.rmi.RemoteException,
     javax.ejb.EJBException, Exception
 {

   MainSessionCommand msCommand = null;

   //try {
   String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
   String sMethod = null;
   Long obsUid = null;
   MainSessionHolder holder = new MainSessionHolder();
   ArrayList<?> resultUIDArr = null;

   sMethod = "addUserComments";
   Object[] oParams = {observationVOCollection, actRelationshipDTCollection};
   msCommand = holder.getMainSessionCommand(session);
   resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
   logger.info("store addComments worked!");

   return null;
 } //sendProxyToEJB

/* not needed
  private ObservationVO getRootLabReportVO(Collection<Object>  obsColl){
   Iterator<Object>  iter = obsColl.iterator();
    ObservationVO obsVO = null;
    while(iter.hasNext()){
       obsVO = (ObservationVO)iter.next();
       if((obsVO.getTheObservationDT().getObsDomainCdSt1().equals("Order"))&&(obsVO.getTheObservationDT().getCtrlCdDisplayForm().equals("LabReport"))){
        break;
       }
       else if(!iter.hasNext()){
         logger.error("AddCommentSubmit.getRootLabReportVO no root found");
       }
      }
      return obsVO;


    }
      */

  private ObservationVO getRootObservationVO(AbstractVO proxy, Long obsUid) throws
    NEDSSSystemException
{
  Collection<ObservationVO>  obsColl = null;

  if (proxy instanceof LabResultProxyVO)
  {
    obsColl = ( (LabResultProxyVO) proxy).getTheObservationVOCollection();
  }
  if (proxy instanceof MorbidityProxyVO)
  {
    obsColl = ( (MorbidityProxyVO) proxy).getTheObservationVOCollection();
  }

  ObservationVO rootVO = null;
  if (proxy instanceof LabResultProxyVO)
    rootVO = getRootObservationVO(obsColl, obsUid);
  else if (proxy instanceof MorbidityProxyVO)
    rootVO = getRootObservationVO(obsColl, obsUid);
    //rootVO = this.getRootLabFromMorbColl(obsColl);

  if( rootVO != null)
    return rootVO;
  throw new IllegalArgumentException(
      "Expected the proxyVO containing a root observation(e.g., ordered test)");
} //getRootObservationVO

private ObservationVO getRootObservationVO(Collection<ObservationVO>  obsColl, Long obsUid)
{
  if(obsColl == null) return null;

  logger.debug("ObservationVOCollection  is not null");
   Iterator<ObservationVO>  iterator = null;
    for (iterator = obsColl.iterator(); iterator.hasNext(); )
    {
      ObservationVO observationVO = (ObservationVO) iterator.next();
      if (observationVO.getTheObservationDT() != null &&
          observationVO.getTheObservationDT().getObservationUid() != null &&
          observationVO.getTheObservationDT().getObservationUid().equals(obsUid))
      {
        logger.debug("found root vo !!");
        return observationVO;
      }
      else
      {
        continue;
      }
    }
    return null;
}

  private ObservationVO getRootLabFromMorbColl(Collection<Object>  obsColl)
  {
    if(obsColl == null) return null;

    logger.debug("ObservationVOCollection  is not null");
     Iterator<Object>  iterator = null;
      for (iterator = obsColl.iterator(); iterator.hasNext(); )
      {
        ObservationVO observationVO = (ObservationVO) iterator.next();
        if (observationVO.getTheObservationDT() != null &&
            (observationVO.getTheObservationDT().getCd().equals("LAB112")))
        {
          logger.debug("found root vo !!");
          return observationVO;
        }
        else
        {
          continue;
        }
      }
      return null;
  }
}