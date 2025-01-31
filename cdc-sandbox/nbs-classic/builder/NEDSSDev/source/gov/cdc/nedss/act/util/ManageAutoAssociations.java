package gov.cdc.nedss.act.util;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;



/**
 * Title:        NEDSS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC EMPLOYEE
 * @version 1.0
 */

public class ManageAutoAssociations {

  public ManageAutoAssociations() {
  }
  static final LogUtils logger = new LogUtils(ManageAutoAssociations.class.getName());
 
  /**
   * This method Associates or disassociates the observation(LAb or MORB) to Investigation from
   * the manage observation page
   * @param investigationUID -- The UID for the investigation to which observation is to be associated or disassociates
   * @param observationSumVOCollection  -- The SummaryVOCollection  of the observations on Manage Observation page
   * @param nbsSecurityObj -- The Security Object to check the permission
   * @throws RemoteException
   * @throws EJBException
   * @throws CreateException
   * @throws NEDSSConcurrentDataException
   */

	public void setObservationAssociationsImpl(Long investigationUID,
			Collection<Object> reportSumVOCollection,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			javax.ejb.EJBException, javax.ejb.CreateException,
			NEDSSConcurrentDataException {
		// false flag indicates that investigation already exists and LAB/MORB
		// is associated to it using manage associations page
		setObservationAssociationsImpl(investigationUID, reportSumVOCollection,
				nbsSecurityObj, false);
	}
/**
   * This method Associates the observation(LAb or MORB) to Investigation 
   * @param investigationUID -- The UID for the investigation to which observation is to be associated or disassociates
   * @param observationSumVOCollection  -- The SummaryVOCollection  of the observations on Manage Observation page
   * @param nbsSecurityObj -- The Security Object to check the permission
   * @param invFromEvent - flag to indicates if lab or morb report is the reactor for investigation.
   * @throws RemoteException
   * @throws EJBException
   * @throws CreateException
   * @throws NEDSSConcurrentDataException
   */
 public void setObservationAssociationsImpl(Long investigationUID, Collection<Object>  reportSumVOCollection,
                                         NBSSecurityObj nbsSecurityObj, boolean invFromEvent)throws java.rmi.RemoteException,
                                         javax.ejb.EJBException, javax.ejb.CreateException, NEDSSConcurrentDataException
   {
       logger.debug("Starts setObservationAssociations()...with NBSSecurity Object");
       ActRelationshipDAOImpl actRelationshipDAOImpl =
                (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
       PrepareVOUtils preVOUtils=new PrepareVOUtils();
       //Obtains a ActController reference
       NedssUtils nedssUtils = new NedssUtils();
       Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
       logger.debug("ActController lookup = " + object.toString());
       ActControllerHome actHome =(ActControllerHome)PortableRemoteObject.narrow(
                                                  object, ActControllerHome.class);
       ActController act = actHome.create();
       //Reads the investigation object and return PublicHealthCaseDT
       PublicHealthCaseDT phcDT = act.getPublicHealthCaseInfo(investigationUID,nbsSecurityObj);

       //Determines if caller has rights to edit investigation
       logger.debug("Check user have persmission for setObsAssoc.");
       if(nbsSecurityObj != null && hasPermissionsToSetObsAssoc(nbsSecurityObj, phcDT))
       {
        logger.debug("Got rights to handle editing investigations.");
        try
        {
          //For each report summary vo
          if(!reportSumVOCollection.isEmpty())
          {
           logger.debug("Number of observation sum vo: " + reportSumVOCollection.size());
          Iterator<Object>  theIterator = reportSumVOCollection.iterator();
           while( theIterator.hasNext() )
           {
            ReportSummaryInterface reportSumVO = (ReportSummaryInterface)theIterator.next();
            ActRelationshipDT actRelationshipDT = null;
            RootDTInterface rootDT=null;

             //Gets and checks whether any association change; if changed, do something, else go next one
             boolean isTouched = reportSumVO.getIsTouched();
             logger.debug("IsTouched: " + isTouched);
             if(!isTouched) continue;

               actRelationshipDT = new ActRelationshipDT();
              //Sets the properties of ActRelationshipDT object
              actRelationshipDT.setTargetActUid(investigationUID);
              actRelationshipDT.setSourceActUid(reportSumVO.getObservationUid());
              actRelationshipDT.setFromTime(reportSumVO.getActivityFromTime());
              actRelationshipDT.setLastChgUserId(Long.parseLong(nbsSecurityObj.getEntryID()));
            //Set from time same as investigation create time if act relationship is created while creating investigation from lab or morbidity report
				if (invFromEvent)
					actRelationshipDT.setFromTime(phcDT.getAddTime());
              actRelationshipDT.setSourceClassCd(NEDSSConstants.OBSERVATION_CLASS_CODE);
              actRelationshipDT.setTargetClassCd(NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE);
              //actRelationshipDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));
              boolean reportFromDoc = false;
              if(reportSumVO instanceof LabReportSummaryVO){
                 actRelationshipDT.setTypeCd(NEDSSConstants.LAB_DISPALY_FORM); 
                 if(((LabReportSummaryVO)reportSumVO).isLabFromDoc())
                 	reportFromDoc=true;
                 if(invFromEvent)
                	 actRelationshipDT.setAddReasonCd(((LabReportSummaryVO)reportSumVO).getProcessingDecisionCd());
                 }
              if(reportSumVO instanceof MorbReportSummaryVO){
                 actRelationshipDT.setTypeCd(NEDSSConstants.DISPLAY_FORM);
                 if(((MorbReportSummaryVO)reportSumVO).isMorbFromDoc())
                  	reportFromDoc=true;
                 if(invFromEvent)
                	 actRelationshipDT.setAddReasonCd(((MorbReportSummaryVO)reportSumVO).getProcessingDecisionCd());
              }

              logger.debug("Now, ActRelationshipDT is: " + actRelationshipDT);

              if(reportSumVO.getIsAssociated()==true){
              logger.debug("observationSumVO.getIsAssociated()  :"+reportSumVO.getIsAssociated());
               //actRelationshipDT.setItNew(true);
               actRelationshipDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
               actRelationshipDT.setStatusCd(NEDSSConstants.A);
              }
              else{
              logger.debug("observationSumVO.getIsAssociated()  :"+reportSumVO.getIsAssociated());
               // actRelationshipDT.setItDelete(true);
                actRelationshipDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
                actRelationshipDT.setStatusCd(NEDSSConstants.I);
            //    actRelationshipDAOImpl.store(actRelationshipDT);
              }

              actRelationshipDT=(ActRelationshipDT)preVOUtils.prepareAssocDT(actRelationshipDT,nbsSecurityObj);
              // needs to be done here as prepareAssocDT will always set dirty flag true
                  if(reportSumVO.getIsAssociated()== true){
                   actRelationshipDT.setItNew(true);
                    actRelationshipDT.setItDirty(false);
                  }
               else{
                  actRelationshipDT.setItDelete(true);
                  actRelationshipDT.setItDirty(false);
                }
              actRelationshipDAOImpl.store(actRelationshipDT);
              
              if(!reportFromDoc){
            	  //Obtains the core observation object
                  logger.debug("+++++++++++++++++++++ getObservationUid ="+reportSumVO.getObservationUid());
                  ObservationDT  obsDT = act.getObservationInfo(reportSumVO.getObservationUid(), nbsSecurityObj);
                  logger.debug("+++++++++++++++++++++ getVersionCtrlNbr and UID ="+obsDT.getObservationUid());
                  logger.debug("+++++++++++++++++++++ getVersionCtrlNbr ="+obsDT.getVersionCtrlNbr());
              //Starts persist observationDT
              if(reportSumVO.getIsAssociated()==true)
              {
               obsDT.setItDirty(true);
               String businessObjLookupName="";
               String businessTriggerCd="";
               String tableName=NEDSSConstants.OBSERVATION;
               String moduleCd=NEDSSConstants.BASE;
               if(reportSumVO instanceof LabReportSummaryVO)
               {
                  businessObjLookupName=NEDSSConstants.OBSERVATIONLABREPORT;
                  businessTriggerCd=NEDSSConstants.OBS_LAB_ASC;
               }
               if(reportSumVO instanceof MorbReportSummaryVO)
               {
                  businessObjLookupName=NEDSSConstants.OBSERVATIONMORBIDITYREPORT;
                  businessTriggerCd=NEDSSConstants.OBS_MORB_ASC;
               }
               logger.debug("##################isItNew "+obsDT.isItNew());
               logger.debug("##################isItDirty "+obsDT.isItDirty());
               rootDT =  preVOUtils.prepareVO(obsDT,businessObjLookupName,
                                              businessTriggerCd,tableName,
                                              moduleCd,nbsSecurityObj);
              } // End if(observationSumVO.getIsAssociated()==true)

              if(reportSumVO.getIsAssociated()==false)
              {
               obsDT.setItDirty(true);
               String businessObjLookupName="";
               String businessTriggerCd="";
               String tableName=NEDSSConstants.OBSERVATION;
               String moduleCd=NEDSSConstants.BASE;
               if(reportSumVO instanceof LabReportSummaryVO)
               { 
            	  Collection<Object> actRelColl = actRelationshipDAOImpl.loadSource(reportSumVO.getObservationUid(),"LabReport");
                  businessObjLookupName=NEDSSConstants.OBSERVATIONLABREPORT;
                  if(actRelColl!=null && actRelColl.size()>0)
                	  businessTriggerCd=NEDSSConstants.OBS_LAB_DIS_ASC;
                  else
                	  businessTriggerCd=NEDSSConstants.OBS_LAB_UNPROCESS;// if  Lab does not have other associations it will be sent back into needing review queue
               }
               if(reportSumVO instanceof MorbReportSummaryVO)
               {
                  businessObjLookupName=NEDSSConstants.OBSERVATIONMORBIDITYREPORT;
                  businessTriggerCd=NEDSSConstants.OBS_MORB_UNPROCESS;
               }
               rootDT =  preVOUtils.prepareVO(obsDT,businessObjLookupName,
                                              businessTriggerCd,tableName,
                                              moduleCd,nbsSecurityObj);
              } // End Of if(observationSumVO.getIsAssociated()==false)
            obsDT = (ObservationDT)rootDT;
            logger.debug("ObsDt.ItDirty :"+obsDT.isItDirty());
            //set the previous entered processing decision to null
            obsDT.setProcessingDecisionCd(null);
            act.setObservationInfo(obsDT,nbsSecurityObj);
              } // End Of while(theIterator.hasNext())
           } // END Of if(!observationSumVOCollection.isEmpty())
       }
     }// End of try
     catch(Exception e)
     {
      logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
      e.printStackTrace();
      throw new javax.ejb.EJBException(e.getMessage());
     }
    } // End if(nbsSecurityObj != null ....)
    else
    {
     logger.error("User does not have the rights to edit this investigation");
     throw new EJBException("User does not have the rights to edit this investigation");
    }
     logger.debug("Done setObservationAssociations() ---- return: void");
   }//end of setObservationAssociations()
 
   private boolean hasPermissionsToSetObsAssoc(NBSSecurityObj nbsSecurityObj, PublicHealthCaseDT phcDT)
   {
     //Strike #1
     if(nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                    NEDSSConstants.EDIT,
                                    phcDT.getProgAreaCd(),
                                    phcDT.getJurisdictionCd(),
                                    phcDT.getSharedInd()))
      return true;//a homer

      //Strike #2
      if(nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                    NBSOperationLookup.ADD,
                                    phcDT.getProgAreaCd(),
                                    ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
       && nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                    NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
                                    ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                    ProgramAreaJurisdictionUtil.ANY_JURISDICTION) )
       return true;//a homer

     //Strike #3
     if(nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                    NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
                                    ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                    ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
       && (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                     NBSOperationLookup.AUTOCREATE,
                                     phcDT.getProgAreaCd(),
                                     ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
       || nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                  NBSOperationLookup.ADD,
                                  phcDT.getProgAreaCd(),
                                  ProgramAreaJurisdictionUtil.ANY_JURISDICTION) ) )
        return true;//a homer
      else
        return false; //Out
   }
 /**
   * This method Associates or disassociates the Vaccination to Investigation from
   * the manage Vaccination page
   * @param investigationUID -- The UID for the investigation to which observation is to be associated or disassociates
   * @param observationSumVOCollection  -- The SummaryVOCollection  of the Vaccination on Manage Vaccination page
   * @param nbsSecurityObj -- The Security Object to check the permission
   * @throws RemoteException
   * @throws EJBException
   * @throws CreateException
   * @throws NEDSSConcurrentDataException
   */
     public void setVaccinationAssociationsImpl(Long investigationUID,
                                            Collection<Object>  vaccinationSummaryVOCollection,
                                            NBSSecurityObj nbsSecurityObj)throws
                                          java.rmi.RemoteException,
                                          javax.ejb.EJBException,
                                          NEDSSSystemException,
                                          javax.ejb.FinderException,
                                          javax.ejb.CreateException

      {
          logger.debug("Starts new setVaccinationAssociations()...");
          ActController actController = null;
          PublicHealthCaseVO publicHealthCaseVO = null;
          PrepareVOUtils preVOUtils=new PrepareVOUtils();
          NedssUtils nedssUtils = new NedssUtils();
          Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
          ActRelationshipDAOImpl actRelationshipDAOImpl =
                     (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
          logger.debug("ActController lookup = " + object.toString());
          ActControllerHome acthome =
                     (ActControllerHome)PortableRemoteObject.narrow(object, ActControllerHome.class);
          logger.debug("Found ActControllerHome: " + acthome);
          actController = acthome.create();
          publicHealthCaseVO = actController.getPublicHealthCase(investigationUID, nbsSecurityObj );

          //Determines if caller has rights to edit investigation
       /*if(nbsSecurityObj != null &&
            nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                         NEDSSConstants.EDIT,
                                         publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd(),
                                         publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd(),
                                         publicHealthCaseVO.getThePublicHealthCaseDT().getSharedInd()))
          {
       */  
        if (true) {
           logger.debug("\nGot rights to handle editing investigations. This is modified method\n");
           try
           {
            if(vaccinationSummaryVOCollection  != null && !vaccinationSummaryVOCollection.isEmpty())
            {
             logger.debug("Number of vaccination sum vo: " + vaccinationSummaryVOCollection.size());
            Iterator<Object>  theIterator = vaccinationSummaryVOCollection.iterator();
             while( theIterator.hasNext() )
             {
              VaccinationSummaryVO vaccinationSummaryVO = (VaccinationSummaryVO)theIterator.next();
              ActRelationshipDT actRelationshipDT = null;
              //Get and check association flag
              boolean isTouched = vaccinationSummaryVO.getIsTouched();
              logger.debug("IsTouched: " + isTouched);
              if(isTouched)
              {
               //Get the ActRelationshipDT object associated with this vaccine summary vo
               actRelationshipDT = new ActRelationshipDT();
               actRelationshipDT.setTargetActUid(investigationUID);
               actRelationshipDT.setSourceActUid(vaccinationSummaryVO.getInterventionUid());
               actRelationshipDT.setTargetClassCd(NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE);
               actRelationshipDT.setSourceClassCd(NEDSSConstants.INTERVENTION_CLASS_CODE);
               actRelationshipDT.setTypeCd(NEDSSConstants.TYPE_CD);
               actRelationshipDT.setLastChgUserId(Long.parseLong(nbsSecurityObj.getEntryID()));
               if(vaccinationSummaryVO.getIsAssociated()== true){
                 logger.debug("Associatioan is true");
                 actRelationshipDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
                 actRelationshipDT.setStatusCd(NEDSSConstants.A);
               }
               else{
                 logger.debug("Associatioan is false");
                 actRelationshipDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
                 actRelationshipDT.setStatusCd(NEDSSConstants.I);
               }
                actRelationshipDT=(ActRelationshipDT)preVOUtils.prepareAssocDT(actRelationshipDT,nbsSecurityObj);
               // needs to be done here as prepareAssocDT will always set dirty flag true
                if(vaccinationSummaryVO.getIsAssociated()== true)
                {
                   actRelationshipDT.setItNew(true);
                   actRelationshipDT.setItDelete(false);
                   actRelationshipDT.setItDirty(false);
                }
               else
               {
                  actRelationshipDT.setItDelete(true);
                  actRelationshipDT.setItNew(false);
                  actRelationshipDT.setItDirty(false);
               }
                actRelationshipDAOImpl.store(actRelationshipDT);
             }
            }
           }
          }
          catch(NEDSSSystemException ex)
          {
            logger.fatal("NEDSSSystemException The entity cannot be deleted as concurrent access is not allowed!");
            throw new NEDSSSystemException("NEDSSSystemException occurred in ManageAutoAssociations : " + ex.toString());
          }
          catch(Exception e)
          {
           logger.error("Error in ManageAssociations");
           logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
           e.printStackTrace();
            throw new NEDSSSystemException("NEDSSSystemException :general exception occurred in ManageAutoAssociations : " + e.toString());
          }
        }
        else
        {
          logger.fatal(nbsSecurityObj.getEntryID(), "User does not have the rights to edit this investigation");
          throw new NEDSSSystemException("NEDSSSystemException :occurred in 'else' ManageAutoAssociations.");
        }
        logger.debug("Done setObservationAssociations() ---- return: void");
      }

      /**
       * This method Associates or disassociates the Treatment to Investigation or Morb
       * @param investigationUID -- The UID for the investigation to which observation is to be associated or disassociates
       * @param treatmentSumVOCollection  -- The SummaryVOCollection  of the Treatment on Manage Treatment page
       * @param nbsSecurityObj -- The Security Object to check the permission
       * @throws RemoteException
       * @throws EJBException
       * @throws CreateException
       * @throws NEDSSConcurrentDataException
       */
      public void setTreatmentAssociationsImpl(Long actUID, String actType,
                                               Collection<Object>
                                               treatmentSummaryVOCollection,
                                               NBSSecurityObj nbsSecurityObj) throws
          java.rmi.RemoteException,
          javax.ejb.EJBException,
          NEDSSSystemException,
          javax.ejb.FinderException,
          javax.ejb.CreateException

      {
        logger.debug("Starts new setTreatmentAssociations()...");
        ActController actController = null;
        PublicHealthCaseVO publicHealthCaseVO = null;
        PrepareVOUtils preVOUtils = new PrepareVOUtils();
        NedssUtils nedssUtils = new NedssUtils();
        Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
        ActRelationshipDAOImpl actRelationshipDAOImpl =
            (ActRelationshipDAOImpl) NEDSSDAOFactory.getDAO(JNDINames.
            ACT_RELATIONSHIP_DAO_CLASS);
        logger.debug("ActController lookup = " + object.toString());
        ActControllerHome acthome =
            (ActControllerHome) PortableRemoteObject.narrow(object,
            ActControllerHome.class);
        logger.debug("Found ActControllerHome: " + acthome);
        actController = acthome.create();
        publicHealthCaseVO = actController.getPublicHealthCase(actUID,
            nbsSecurityObj);

        //Determines if caller has rights to edit investigation
        /*if(nbsSecurityObj != null &&
           nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                        NEDSSConstants.EDIT,
             publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd(),
             publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd(),
             publicHealthCaseVO.getThePublicHealthCaseDT().getSharedInd()))
         */
        /** @todo Add proper security back in */
        if (true) {
          logger.debug(
              "\nGot rights to handle editing investigations. This is modified method\n");
          try {
            if (treatmentSummaryVOCollection  != null &&
                !treatmentSummaryVOCollection.isEmpty()) {
              logger.debug("Number of treatment sum vo: " +
                           treatmentSummaryVOCollection.size());
             Iterator<Object>  theIterator = treatmentSummaryVOCollection.iterator();
              while (theIterator.hasNext()) {
                TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO)
                    theIterator.next();
                ActRelationshipDT actRelationshipDT = null;
                //Get and check association flag
                boolean isTouched = treatmentSummaryVO.getIsTouched();
                logger.debug("IsTouched: " + isTouched);
                if (isTouched) {
                  //Get the ActRelationshipDT object associated with this treatment summary vo
                  actRelationshipDT = new ActRelationshipDT();
                  actRelationshipDT.setTargetActUid(actUID);
                  actRelationshipDT.setSourceActUid(treatmentSummaryVO.getTreatmentUid());
                  actRelationshipDT.setSourceClassCd(NEDSSConstants.TREATMENT_CLASS_CODE);
                  actRelationshipDT.setLastChgUserId(Long.parseLong(nbsSecurityObj.getEntryID()));
                  if (actType.equals(NEDSSConstants.INVESTIGATION)) {
                    actRelationshipDT.setTargetClassCd(NEDSSConstants.
                        PUBLIC_HEALTH_CASE_CLASS_CODE);
                    actRelationshipDT.setTypeCd(NEDSSConstants.TRT_TO_PHC);
                  }
                  else if (actType.equals(NEDSSConstants.MORBIDITY_REPORT)) {
                    actRelationshipDT.setTargetClassCd(NEDSSConstants.
                        OBSERVATION_CLASS_CODE);
                    actRelationshipDT.setTypeCd(NEDSSConstants.TRT_TO_MORB);
                  }
                  else {
                    logger.error(
                        "\n\nUnkown actType when setting Treatment associations");
                  }

                  if (treatmentSummaryVO.getIsAssociated() == true) {
                    logger.debug("Association is true");
                    actRelationshipDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
                    actRelationshipDT.setStatusCd(NEDSSConstants.A);
                  }
                  else {
                    logger.debug("Associatioan is false");
                    actRelationshipDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
                    actRelationshipDT.setStatusCd(NEDSSConstants.I);
                  }
                  actRelationshipDT = (ActRelationshipDT) preVOUtils.prepareAssocDT(
                      actRelationshipDT, nbsSecurityObj);
                  // needs to be done here as prepareAssocDT will always set dirty flag true
                  if (treatmentSummaryVO.getIsAssociated() == true) {
                    actRelationshipDT.setItNew(true);
                    actRelationshipDT.setItDelete(false);
                    actRelationshipDT.setItDirty(false);
                  }
                  else {
                    actRelationshipDT.setItDelete(true);
                    actRelationshipDT.setItNew(false);
                    actRelationshipDT.setItDirty(false);
                  }
                  actRelationshipDAOImpl.store(actRelationshipDT);
                }
              }
            }
          }
          catch (NEDSSSystemException ex) {
            logger.fatal("NEDSSSystemException The entity cannot be deleted as concurrent access is not allowed!");
            throw new NEDSSSystemException(
                "NEDSSSystemException occurred in ManageAutoAssociations : " +
                ex.toString());
          }
          catch (Exception e) {
            logger.error("Error in ManageAssociations");
            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
            e.printStackTrace();
            throw new NEDSSSystemException(
                "NEDSSSystemException :general exception occurred in ManageAutoAssociations : " +
                e.toString());
          }
        }
        else {
          logger.fatal(nbsSecurityObj.getEntryID(),
                       "User does not have the rights to edit this investigation");
          throw new NEDSSSystemException(
              "NEDSSSystemException :occurred in 'else' ManageAutoAssociations.");
        }
        logger.debug("Done setTreatmentAssociations() ---- return: void");
      }

      /**
       * This method Associates or disassociates the Interview to Investigation
       * @param investigationUID -- The UID for the investigation to which Interview is to be associated or disassociates
       * @param interviewSummaryDTCollection  -- The SummaryVOCollection  of the Interview on Manage Associations page
       * @param nbsSecurityObj -- The Security Object to check the permission
       * @throws RemoteException
       * @throws EJBException
       * @throws CreateException
       * @throws NEDSSConcurrentDataException
       */
      public void setInterviewAssociationsImpl(Long actUID, 
    		  								   String actType,
                                               Collection<Object> interviewSummaryDTCollection,
                                               NBSSecurityObj nbsSecurityObj) throws
          java.rmi.RemoteException,
          javax.ejb.EJBException,
          NEDSSSystemException,
          javax.ejb.FinderException,
          javax.ejb.CreateException

      {
        logger.debug("Starts new setInterviewAssociations()...");
        ActController actController = null;
        PublicHealthCaseVO publicHealthCaseVO = null;
        PrepareVOUtils preVOUtils = new PrepareVOUtils();
        NedssUtils nedssUtils = new NedssUtils();
        Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
        ActRelationshipDAOImpl actRelationshipDAOImpl =
            (ActRelationshipDAOImpl) NEDSSDAOFactory.getDAO(JNDINames.
            ACT_RELATIONSHIP_DAO_CLASS);
        logger.debug("ActController lookup = " + object.toString());
        ActControllerHome acthome =
            (ActControllerHome) PortableRemoteObject.narrow(object,
            ActControllerHome.class);
        logger.debug("Found ActControllerHome: " + acthome);
        actController = acthome.create();
        publicHealthCaseVO = actController.getPublicHealthCase(actUID,
            nbsSecurityObj);

        if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.EDIT,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
          logger.debug(
              "\nGot rights to handle editing investigations. This is modified method\n");
          try {
            if (interviewSummaryDTCollection  != null &&
                !interviewSummaryDTCollection.isEmpty()) {
              logger.debug("Number of Interview sum dt: " +
            		  interviewSummaryDTCollection.size());
             Iterator<Object>  theIterator = interviewSummaryDTCollection.iterator();
              while (theIterator.hasNext()) {
                InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT)
                    theIterator.next();
                ActRelationshipDT actRelationshipDT = null;
                //Get and check association flag
                boolean isTouched = interviewSummaryDT.isTouched();
                logger.debug("IsTouched: " + isTouched);
                  //Get the ActRelationshipDT object associated with this treatment summary vo
                actRelationshipDT = new ActRelationshipDT();
                actRelationshipDT.setTargetActUid(actUID);
                actRelationshipDT.setSourceActUid(interviewSummaryDT.
                                                    getInterviewUid());
                actRelationshipDT.setSourceClassCd(NEDSSConstants.OBS);
                actRelationshipDT.setTypeDescTxt("Interview Related To");                 
                if (actType.equals(NEDSSConstants.INVESTIGATION)) {
                    actRelationshipDT.setTargetClassCd(NEDSSConstants.
                        PUBLIC_HEALTH_CASE_CLASS_CODE);
                    actRelationshipDT.setTypeCd(NEDSSConstants.INTERVIEW_CLASS_CODE);
                }
                else {
                    logger.error(
                        "\n\nUnkown actType when setting Interview associations");
                }

                if (interviewSummaryDT.isAssociated() == true) {
                    logger.debug("Association is true");
                    actRelationshipDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
                    actRelationshipDT.setStatusCd(NEDSSConstants.A);
                }
                else {
                    logger.debug("Association is false");
                    actRelationshipDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
                    actRelationshipDT.setStatusCd(NEDSSConstants.I);
                }
                  actRelationshipDT = (ActRelationshipDT) preVOUtils.prepareAssocDT(
                      actRelationshipDT, nbsSecurityObj);
                  // needs to be done here as prepareAssocDT will always set dirty flag true
                if (interviewSummaryDT.isAssociated() == true) {
                    actRelationshipDT.setItNew(true);
                    actRelationshipDT.setItDelete(false);
                    actRelationshipDT.setItDirty(false);
                }
                else {
                    actRelationshipDT.setItDelete(true);
                    actRelationshipDT.setItNew(false);
                    actRelationshipDT.setItDirty(false);
                }
                actRelationshipDAOImpl.store(actRelationshipDT);
              }
            }
          }
          catch (NEDSSSystemException ex) {
            logger.fatal("NEDSSSystemException The AR entity cannot be deleted as concurrent access is not allowed!");
            throw new NEDSSSystemException(
                "NEDSSSystemException occurred in ManageAutoAssociations : " +
                ex.toString());
          }
          catch (Exception e) {
            logger.error("Error in ManageAssociations");
            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
            e.printStackTrace();
            throw new NEDSSSystemException(
                "NEDSSSystemException :general exception occurred in ManageAutoAssociations : " +
                e.toString());
          }
        } //EDIT Permission
        else {
          logger.fatal(nbsSecurityObj.getEntryID(),
                       "User does not have the rights to edit this investigation");
          throw new NEDSSSystemException(
              "NEDSSSystemException :occurred in 'else' ManageAutoAssociations.");
        }
        logger.debug("Done setInterviewAssociations() ---- return: void");
      }      
      
      public void setDocumentAssociationsImpl(Long investigationUID,
    		  Collection<Object>   summaryDTColl,
    		  NBSSecurityObj nbsSecurityObj)throws
    		  java.rmi.RemoteException,
    		  javax.ejb.EJBException,
    		  NEDSSSystemException,
    		  javax.ejb.FinderException,
    		  javax.ejb.CreateException

    		  {
    	  logger.debug("Starts new setDocumentAssociations()...");
    	  ActController actController = null;
    	  PublicHealthCaseVO publicHealthCaseVO = null;
    	  PrepareVOUtils preVOUtils=new PrepareVOUtils();
    	  NedssUtils nedssUtils = new NedssUtils();
    	  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
    	  ActRelationshipDAOImpl actRelationshipDAOImpl =
    		  (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
    	  logger.debug("ActController lookup = " + object.toString());
    	  ActControllerHome acthome =
    		  (ActControllerHome)PortableRemoteObject.narrow(object, ActControllerHome.class);
    	  logger.debug("Found ActControllerHome: " + acthome);
    	  actController = acthome.create();
    	  publicHealthCaseVO = actController.getPublicHealthCase(investigationUID, nbsSecurityObj );
    	  NBSDocumentVO nbsDocumentVO = null;

//  	  Determines if caller has rights to edit investigation
    	/*  if(nbsSecurityObj != null &&
    			  nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
    					  NEDSSConstants.EDIT,
    					  publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd(),
    					  publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd(),
    					  publicHealthCaseVO.getThePublicHealthCaseDT().getSharedInd()))
    	  {*/
    		
    	 if (true) {
    	          logger.debug(
    	              "\nGot rights to handle editing investigations. This is modified method\n");
    	  try
    		  {
    			  if(summaryDTColl != null)
    			  {
    				 Iterator<Object>  it =summaryDTColl.iterator();
    				  while(it.hasNext()){
    					  SummaryDT summaryDT=(SummaryDT) it.next();
    					  NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
    					  nbsDocumentVO =nbsDocumentDAOImpl.getNBSDocument(summaryDT.getNbsDocumentUid());

    				 
    				  logger.debug("NBSDocumentUid: " + summaryDT.getNbsDocumentUid());
    				  
    					  ActRelationshipDT actRelationshipDT = null;
//  					  Get and check association flag
    					  boolean isTouched = summaryDT.getIsTouched();
    					  logger.debug("IsTouched: " + isTouched);
    					  if(isTouched)
    					  {
//  						  Get the ActRelationshipDT object associated with this vaccine summary vo
    						  actRelationshipDT = new ActRelationshipDT();
    						  actRelationshipDT.setTargetActUid(investigationUID);
    						  actRelationshipDT.setSourceActUid( summaryDT.getNbsDocumentUid());
    						  actRelationshipDT.setTargetClassCd(NEDSSConstants.CLASS_CD_CASE);
    						  actRelationshipDT.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
    						  actRelationshipDT.setTypeCd(NEDSSConstants.DocToPHC);
    						  if(summaryDT.getIsAssociated()== true){
    							  logger.debug("Associatioan is true");
    							  actRelationshipDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
    							  actRelationshipDT.setStatusCd(NEDSSConstants.A);
    						  }
    						  else{
    							  logger.debug("Associatioan is false");
    							  actRelationshipDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
    							  actRelationshipDT.setStatusCd(NEDSSConstants.I);
    						  }
    						  actRelationshipDT=(ActRelationshipDT)preVOUtils.prepareAssocDT(actRelationshipDT,nbsSecurityObj);
//  						  needs to be done here as prepareAssocDT will always set dirty flag true
    						  if(summaryDT.getIsAssociated()== true)
    						  {
    							  actRelationshipDT.setItNew(true);
    							  actRelationshipDT.setItDelete(false);
    							  actRelationshipDT.setItDirty(false);
    						  }
    						  else
    						  {
    							  actRelationshipDT.setItDelete(true);
    							  actRelationshipDT.setItNew(false);
    							  actRelationshipDT.setItDirty(false);
    						  }
    						  actRelationshipDAOImpl.store(actRelationshipDT);
    						  
    						  Object lookedUpObj = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
    						  logger.debug("!!!!!!!!!!NBS_DOCUMENT_EJB lookup = " + lookedUpObj.toString());
    						  NbsDocumentHome nbsDocHome = (NbsDocumentHome)PortableRemoteObject.narrow(lookedUpObj, NbsDocumentHome.class);
    						  logger.debug("!!!!!!!!!!Found NBS_DOCUMENT_EJBHome: " + nbsDocHome);
    						  NbsDocument nbsDocument = nbsDocHome.create();
   							  nbsDocument.updateDocumentWithOutthePatient(nbsDocumentVO, nbsSecurityObj);

    					  }
    				  }
    			  }
    		  }
    		  catch(NEDSSSystemException ex)
    		  {
    			  logger.fatal("NEDSSSystemException The entity cannot be deleted as concurrent access is not allowed!");
    			  throw new NEDSSSystemException("NEDSSSystemException occurred in ManageAutoAssociations : " + ex.toString());
    		  }
    		  catch(Exception e)
    		  {
    			  logger.error("Error in ManageAssociations");
    			  logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
    			  e.printStackTrace();
    			  throw new NEDSSSystemException("NEDSSSystemException :general exception occurred in ManageAutoAssociations : " + e.toString());
    		  }
    	  }
    	  else
    	  {
    		  logger.fatal(nbsSecurityObj.getEntryID(), "User does not have the rights to edit this investigation");
    		  throw new NEDSSSystemException("NEDSSSystemException :occurred in 'else' ManageAutoAssociations.");
    	  }
    	  logger.debug("Done setObservationAssociations() ---- return: void");
    }

}
