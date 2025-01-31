/**
 * Title:        TreatmentProxyEJB class.
 * Description:  A session bean for TreatmentProxy.
 * Copyright:    Copyright (c) 2001
 * Company:      Computer Sciences Corporation
 * @author       NEDSS Development Team
 * @version      1.1
 */

package gov.cdc.nedss.proxy.ejb.treatmentproxyejb.bean;

import javax.ejb.SessionBean;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import java.rmi.RemoteException;

import javax.ejb.*;
import javax.rmi.*;

import java.util.*;
import java.sql.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.*;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.act.util.*;
import gov.cdc.nedss.act.sqlscript.*;
import gov.cdc.nedss.ldf.helper.LDFHelper;

public class TreatmentProxyEJB
    extends BMPBase
    implements javax.ejb.SessionBean {
	private static final long serialVersionUID = 1L;
  LogUtils logger = new LogUtils(TreatmentProxyEJB.class.getName());

  public TreatmentProxyEJB() {
  }

  /**
   * A container invokes this method before it ends the life of the session object. This
   * happens as a result of a client's invoking a remove operation, or when a container
   * decides to terminate the session object after a timeout. This method is called with
   * no transaction context.
   */
  public void ejbRemove() {
  }

  /**
   * The activate method is called when the instance is activated from its 'passive' state.
   * The instance should acquire any resource that it has released earlier in the ejbPassivate()
   * method. This method is called with no transaction context.
   */
  public void ejbActivate() {
  }

  /**
   * The passivate method is called before the instance enters the 'passive' state. The
   * instance should release any resources that it can re-acquire later in the ejbActivate()
   * method. After the passivate method completes, the instance must be in a state that
   * allows the container to use the Java Serialization protocol to externalize and store
       * away the instance's state. This method is called with no transaction context.
   */
  public void ejbPassivate() {
  }

  /**
   * Called by the container to create a session bean instance. Its parameters typically
   * contain the information the client uses to customize the bean instance for its use.
   * It requires a matching pair in the bean class and its home interface.
   */
  public void ejbCreate() {
  }

  /**
     * This method checks for the negative uid value for any ACT & ENTITY DT then compare them
     * with respective negative values in ActRelationshipDT and ParticipationDT as received from
     * the TreatmentProxyVO(determined in the addnotification method).
     * It replaces them accordingly.
     * @param NotificationProxyVO
     * @param Long falseUid
     * @param Long actualUid
     */
  private void setFalseToNew(TreatmentProxyVO treatmentProxyVO, Long falseUid,
                             Long actualUid) {

    try {
		//Prepare to reset associations
   Iterator<Object>  anIterator = null;
		ParticipationDAOImpl participationDAOImpl = null;
		ParticipationDT participationDT = null;
		ActRelationshipDAOImpl actRelationshipDAOImpl = null;
		ActRelationshipDT actRelationshipDT = null;
		RoleDT roleDT = null;

		Collection<Object>  participationColl = (ArrayList<Object> )treatmentProxyVO.
		    getTheParticipationDTCollection();
		Collection<Object>  actRelationShipColl = (ArrayList<Object> )treatmentProxyVO.
		    getTheActRelationshipDTCollection();

		if (participationColl != null) {
		  for (anIterator = participationColl.iterator(); anIterator.hasNext(); ) {
		    participationDT = (ParticipationDT) anIterator.next();
		    logger.debug("Inside the participation method call");
		    if (participationDT.getActUid().compareTo(falseUid) == 0)
		      participationDT.setActUid(actualUid);
		    if (participationDT.getSubjectEntityUid().compareTo(falseUid) == 0)
		      participationDT.setSubjectEntityUid(actualUid);
		    logger.debug("participationDT.getSubjectEntityUid(): " +
		                 participationDT.getSubjectEntityUid());
		  } //end for
		} //end if
		if (actRelationShipColl != null) {
		  for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext(); ) {
		    actRelationshipDT = (ActRelationshipDT) anIterator.next();
		    logger.debug(
		        "Just after actRelationShip table replacements after getting actRelationshipDT ");
		    if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0)
		      actRelationshipDT.setTargetActUid(actualUid);
		    logger.debug("Just after actRelationShip table replacements after getting actRelationshipDT and If");
		    if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0)
		      actRelationshipDT.setSourceActUid(actualUid);

		    logger.debug(
		        "\n\n\n(actRelationshipDT.getActUid() compared to falseUid)1 :  " +
		        actRelationshipDT.getSourceActUid().compareTo(falseUid));
		  } //end for
		  logger.debug("actRelationshipDT.getActUid(actualUid)2 :" +
		               actRelationshipDT.getTargetActUid());
		} //end if
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("ActualUid: " + actualUid + ", FalseUid: " + falseUid + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  } //end method setFalseToNew


  /**
   * Sets the value of a TreatmentProxy object.
   * @param treatmentProxyVO  the new value of the TreatmentProxy object
   * @param nbsSecurityObj      the current value of the NBSSecurityObj object
   * @return  the value of the treatmentUID
   * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
   */
  public Long setTreatmentProxy(TreatmentProxyVO treatmentProxyVO,
                                NBSSecurityObj nbsSecurityObj) throws java.rmi.
      RemoteException, javax.ejb.EJBException,
      CreateException, NEDSSSystemException, FinderException,
      NEDSSConcurrentDataException {

    try {
		logger.debug("\n\n Inside setTreatmentProxy()...\n\n");
		//Test to see if the TreatmentProxy needs to be created or updated
		if (!treatmentProxyVO.isItNew() && !treatmentProxyVO.isItDirty()) {
		  logger.error(
		      "treatmentProxyVO is not new or dirty, hence exiting without changes!");
		  return treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().
		      getTreatmentUid();
		} //end if
		else {

		  if (treatmentProxyVO.isItNew()) {
		    logger.debug("TreatmentProxyVO is new");

		  //------------------------------------------------------------------------------
		  // if no permissions - terminate for ADD operation
		    if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
		                                      NBSOperationLookup.MANAGE,
		                                      ProgramAreaJurisdictionUtil.
		                                      ANY_PROGRAM_AREA,
		                                      ProgramAreaJurisdictionUtil.
		                                      ANY_JURISDICTION)) {
		      logger.fatal("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE) is false");
		      throw new RemoteException("NO PERMISSIONS");
		    }
		    logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE) is true in setTreatmentProxyEJB");
		  }
		  else if (treatmentProxyVO.isItDirty()) {
		    logger.debug("TreatmentProxyVO is dirty");
		    // if no permissions - terminate for EDIT operation
		    if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
		                                      NBSOperationLookup.MANAGE,
		                                      ProgramAreaJurisdictionUtil.
		                                      ANY_PROGRAM_AREA,
		                                      ProgramAreaJurisdictionUtil.
		                                      ANY_JURISDICTION)) {
		      logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE) is false");
		      throw new RemoteException("NO PERMISSIONS");
		    }
		    logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE) is true in setTreatmentProxyEJB");
		  }

		  ParticipationDT participationDT = null;
		  ActRelationshipDT actRelationshipDT = null;
		  EntityController entityController = null;
		  NedssUtils nedssUtils = new NedssUtils();
		  PersonVO personVO = null;
		  OrganizationVO organizationVO = null;
		  ObservationVO observationVO = null;

		 Iterator<Object>  anIterator = null;
		  ParticipationDAOImpl participationDAOImpl = null;
		  ActRelationshipDAOImpl actRelationshipDAOImpl = null;
		  Long actualUid = null;

		  /** @todo Add try block here? */
		  ActController actController = null;
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  logger.debug("!!!!!!!!!!ActController lookup = " + object.toString());
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		      narrow(object, ActControllerHome.class);
		  logger.debug("!!!!!!!!!!Found ActControllerHome: " + acthome);
		  actController = acthome.create();
		  Long falseTreatmentUid = null;
		  if (treatmentProxyVO.getTheTreatmentVO() != null) {
		    TreatmentVO treatmentVO = treatmentProxyVO.getTheTreatmentVO();
		    try {
		      if (treatmentProxyVO.getTheTreatmentVO().isItNew()) {
		        logger.debug("TreatmentVO is new");
		        logger.debug("TreatmentVO isItNew: " + treatmentVO.isItNew() +
		                     ", isItDirty: " + treatmentVO.isItDirty());

		        RootDTInterface rDTInterface = treatmentProxyVO.getTheTreatmentVO().
		            getTheTreatmentDT();

		        //Treatment State Model Trigger
		        String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
		        String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_CR;
		        String TABLE_NAME = DataTables.TREATMENT_TABLE;
		        String MODULE_CD = NEDSSConstants.BASE;

		        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		        logger.debug("\nThe treatmentUID is " +
		                     treatmentVO.getTheTreatmentDT().getTreatmentUid());
		        TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils.prepareVO(
		            rDTInterface, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
		            TABLE_NAME, MODULE_CD, nbsSecurityObj);
		        treatmentVO.setTheTreatmentDT(newTreatmentDT);

		        logger.debug("\n\ntreatmentVO status CD" +
		                     treatmentVO.getTheTreatmentDT().getStatusCd());
		        logger.debug("\n\ntreatmentVO status time" +
		                     treatmentVO.getTheTreatmentDT().getStatusTime());
		        logger.debug("\n\nThe treatmentUID is " +
		                     newTreatmentDT.getTreatmentUid());
		        logger.debug(
		            "\n\nThe record status CD in new must have changed now: " +
		            treatmentVO.getTheTreatmentDT().getRecordStatusCd());
		      }
		      else if (treatmentProxyVO.getTheTreatmentVO().isItDirty()) {
		        logger.debug("-- TreatmentVO is dirty");
		        logger.debug("-- TreatmentVO isItNew: " + treatmentVO.isItNew() +
		                     ", isItDirty: " + treatmentVO.isItDirty());
		        RootDTInterface rDTInterface = treatmentProxyVO.getTheTreatmentVO().
		            getTheTreatmentDT();

		        //Treatment State Model Trigger
		        String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
		        String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_EDIT;
		        String TABLE_NAME = DataTables.TREATMENT_TABLE;
		        String MODULE_CD = NEDSSConstants.BASE;

		        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		        TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils.prepareVO(
		            rDTInterface, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
		            TABLE_NAME, MODULE_CD, nbsSecurityObj);
		        treatmentVO.setTheTreatmentDT(newTreatmentDT);
		        logger.debug("\n\n\nThe treatmentUID is " +
		                     treatmentVO.getTheTreatmentDT().getTreatmentUid());
		        logger.debug("\n\n\n  --- dirty treatmentVO status CD" +
		                     treatmentVO.getTheTreatmentDT().getStatusCd());
		        logger.debug("\n\n\n  --- dirty treatmentVO status time" +
		                     treatmentVO.getTheTreatmentDT().getStatusTime());
		        logger.debug("\nThe treatmentUID is " +
		                     newTreatmentDT.getTreatmentUid());
		        logger.debug(
		            "\nThe record status CD in edit must have changed now: " +
		            treatmentVO.getTheTreatmentDT().getRecordStatusCd());
		      }

		      logger.debug("About to set the falseTreatmentId and ActualId");
		      falseTreatmentUid = treatmentVO.getTheTreatmentDT().getTreatmentUid();
		      actualUid = actController.setTreatment(treatmentVO, nbsSecurityObj);
		    }
		    catch (NEDSSConcurrentDataException ex) {
		      logger.fatal(
		          "The entity cannot be updated as concurrent access is not allowed!"+ex.getMessage(),ex);
		      // cntx.setRollbackOnly();
		      throw new NEDSSConcurrentDataException(
		          "Concurrent access occurred in TreatmentProxyEJB : " +
		          ex.getMessage(),ex);
		    }
		    catch (Exception e) {
		      if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
		        logger.fatal(
		            "Throwing NEDSSConcurrentDataException in TreatmentProxyEJB"+e.getMessage(),e);
		        throw new NEDSSConcurrentDataException(e.getMessage(),e);
		      }
		    }
		    logger.debug("\n\n\n actual UID is " + actualUid + "\n\n\n");
		    logger.debug("\n\n\n false UID is " + falseTreatmentUid + "\n\n\n");
		    if (falseTreatmentUid != null && falseTreatmentUid.intValue() < 0) {
		      setFalseToNew(treatmentProxyVO, falseTreatmentUid, actualUid);
		      logger.debug("Completed set false to new");
		    }
		  }
		  logger.debug("About to initialize falseUid and realUid");
		  Long falseUid = null;
		  Long realUid = null;

		  logger.debug(
		      "\n\n\nIn setTreatment, about to check for ParticipationDTColl...\n\n\n");

		  if (treatmentProxyVO.getTheParticipationDTCollection() != null) {
		    logger.debug("\n\n\ngot the participation Collection<Object>  Loop\n\n\n");
		    participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(
		        JNDINames.ACT_PARTICIPATION_DAO_CLASS);
		    logger.debug("got the participation Collection<Object>  Loop got the DAO");
		    for (anIterator = treatmentProxyVO.getTheParticipationDTCollection().
		         iterator(); anIterator.hasNext(); ) {
		      logger.debug("got the participation Collection<Object>  FOR Loop");
		      participationDT = (ParticipationDT) anIterator.next();
		      if (participationDT.getStatusCd() == null)
		        participationDT.setStatusCd(NEDSSConstants.ROLE_STATUS_CD);
		      if (participationDT.getStatusTime() == null)
		        participationDT.setStatusTime(NEDSSConstants.ROLE_TIME);
		      logger.debug(" got the participation Loop");
		      try {
		        logger.debug("got the participationDT, the statusCD is " +
		                     participationDT.getStatusCd());
		        logger.debug("got the participationDT, the typeCD is " +
		                     participationDT.getTypeCd());
		        logger.debug("The delete flag for participation is :" +
		                     participationDT.isItDelete());
		        logger.debug("The dirty flag for participation is :" +
		                     participationDT.isItDirty());
		        participationDAOImpl.store(participationDT);
		        logger.debug("the participationDT, the ACTUID is " +
		                     participationDT.getActUid());
		        logger.debug("the participationDT, the subjectEntityUid is " +
		                     participationDT.getSubjectEntityUid());
		      }
		      catch (Exception e) {
		        logger.fatal(nbsSecurityObj.getFullName()+e.getMessage(), e);
		        throw new javax.ejb.EJBException(e.getMessage(),e);
		      }
		    }

		    if (treatmentProxyVO.getTheActRelationshipDTCollection() != null) {
		      actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
		          getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
		      for (anIterator = treatmentProxyVO.getTheActRelationshipDTCollection().
		           iterator(); anIterator.hasNext(); ) {
		        actRelationshipDT = (ActRelationshipDT) anIterator.next();
		        if (actRelationshipDT.getStatusCd() == null)
		          actRelationshipDT.setStatusCd(NEDSSConstants.ROLE_STATUS_CD);
		        if (actRelationshipDT.getStatusTime() == null)
		          actRelationshipDT.setStatusTime(NEDSSConstants.ROLE_TIME);
		        logger.debug("Got into The ActRelationship loop");
		        try {
		          actRelationshipDAOImpl.store(actRelationshipDT);
		          logger.debug("Got into The ActRelationship, The ActUid is " +
		                       actRelationshipDT.getTargetActUid());
		        }
		        catch (Exception e) {
		          logger.fatal(nbsSecurityObj.getFullName()+e.getMessage(), e);
		          throw new javax.ejb.EJBException(e.getMessage(),e);
		        }
		      }
		    }
		  }
		  else {
		    logger.debug("\n\nParticipation is null!!!\n\n");
		  }
		  if(treatmentProxyVO.getTheStateDefinedFieldDataDTCollection() != null) {
		    logger.debug("Inside setLDF of TreatmentProxy");
		    // code for new ldf backend.
		    LDFHelper ldfHelper = LDFHelper.getInstance();
		    ldfHelper.setLDFCollection(treatmentProxyVO.getTheStateDefinedFieldDataDTCollection() , treatmentProxyVO.getLdfUids(),
		           NEDSSConstants.TREATMENT_LDF,
		           null,
		           actualUid,nbsSecurityObj
		           );
		  }

		  logger.debug("the actual Uid for TreatmentProxy treatmentUID is " +
		               actualUid);
		  logger.debug("\n\n@FINAL@ TreatmentVO isItNew: " +
		               treatmentProxyVO.getTheTreatmentVO().isItNew() +
		               ", isItDirty: " +
		               treatmentProxyVO.getTheTreatmentVO().isItDirty());
		  return actualUid;

		} //if
	} catch (ClassCastException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

  }// end method setTreatmentProxy

  /**
   * Access method for a selected TreatmentProxyVO object.
   * @param treatmentUid     the Uid value of the selected treatment
   * @param nbsSecurityObj      the current value of the NBSSecurityObj object
   * @return  the TreatmentProxyVO object for selected treatment Uid
   * @throws java.rmi.RemoteException, javax.ejb.EJBException, gov.cdc.nedss.exceptions.NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
   */
  public TreatmentProxyVO getTreatmentProxy(Long treatmentUid,
                                            NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {

    // if no permissions - terminate
    if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                      NBSOperationLookup.VIEW,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_PROGRAM_AREA,
                                      ProgramAreaJurisdictionUtil.
                                      ANY_JURISDICTION)) {
      logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.VIEW) is false");
      throw new NEDSSSystemException("NO PERMISSIONS");
    }
    logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.VIEW) is true");


    //declare new treatmentProxyVO
    TreatmentProxyVO treatmentProxyVO = new TreatmentProxyVO();
    TreatmentVO treatmentVO = null;

    //declare collections to add to treatmentProxyVO
    Collection<Object>  thePersonVOCollection  = new ArrayList<Object> ();
    Collection<Object>  theOrganizationVOCollection  = new ArrayList<Object> ();

    //declare Value objects to be added to collections
    ParticipationDT participationDT = null;
    ActRelationshipDT actRelationshipDT = null;

   Iterator<Object>  anIterator = null;
   Iterator<Object>  anoIterator = null;
   Iterator<Object>  actIterator = null;
    int i = 0; //for debugging
    int m = 0; //for debugging
    int a = 0; //for debugging

    ActController actController = null;
    EntityController entityController = null;
    NedssUtils nedssUtils = new NedssUtils();
    try {
      Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
      logger.debug("EntityController lookup = " + obj.toString());
      EntityControllerHome home = (EntityControllerHome) PortableRemoteObject.
          narrow(obj, EntityControllerHome.class);
      logger.debug("Found EntityControllerHome: " + home);
      entityController = home.create();
      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());
      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(object, ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);
      actController = acthome.create();

      treatmentVO = actController.getTreatment(treatmentUid, nbsSecurityObj);

      //before returning TreatmentVO check security permissions -
      // if no permissions - terminate
      if (!nbsSecurityObj.checkDataAccess(treatmentVO.getTheTreatmentDT(),
                                          NBSBOLookup.TREATMENT,
                                          NBSOperationLookup.VIEW)) {
        logger.info("NBSSecurityObj.checkDataAccess(treatmentVO.getTheTreatmentDT(), NBSBOLookup.TREATMENT) is false");
        throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
      }

      logger.info("NBSSecurityObj.checkDataAccess(treatmentVO.getTheTreatmentDT(), NBSBOLookup.TREATMENT) is true");

      //PARTICIPATION

      logger.debug("\n\n\nAbout to check participationDTColl\n\n\n");

      if (treatmentVO.getTheParticipationDTCollection() != null) {
        //iterating through treatmentVO participationDTCollection
        for (anIterator = treatmentVO.getTheParticipationDTCollection().
             iterator(); anIterator.hasNext(); ) {
          i++;
          participationDT = (ParticipationDT) anIterator.next();
          //checking to see if person is the subject of treatment and adding person to person collection
          if (participationDT.getTypeCd() != null) {


            //PROVIDER
            if (participationDT.getTypeCd().equals(NEDSSConstants.PROVIDER_OF_TREATMENT) &&
                participationDT.getSubjectClassCd().equals(NEDSSConstants.
                PERSON_CLASS_CODE) &&
                participationDT.getRecordStatusCd().equals(NEDSSConstants.
                ACTIVE) &&
                participationDT.getActClassCd().equals(NEDSSConstants.
                TREATMENT_CLASS_CODE)) {
              logger.debug(
                  "\nFound provider of Treatment in participationDT\n");
              thePersonVOCollection.add(entityController.getPerson(
                  participationDT.getSubjectEntityUid(), nbsSecurityObj));
              //treatmentProxyVO.setThePersonVOCollection(thePersonVOCollection);
              logger.debug("Found provider of treatment and added to personvo collection, empty? " +
                           thePersonVOCollection.isEmpty());
              continue;
            }

            //SUBJECT
            if (participationDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_TREATMENT) &&
                participationDT.getSubjectClassCd().equals(NEDSSConstants.
                PERSON_CLASS_CODE) &&
                participationDT.getRecordStatusCd().equals(NEDSSConstants.
                ACTIVE) &&
                participationDT.getActClassCd().equals(NEDSSConstants.
                TREATMENT_CLASS_CODE)) {
              logger.debug(
                  "!!!!!!!!!!Found Subject of Treatment in participationDT");
              thePersonVOCollection.add(entityController.getPerson(
                  participationDT.getSubjectEntityUid(), nbsSecurityObj));
              //treatmentProxyVO.setThePersonVOCollection(thePersonVOCollection);
              logger.debug("!!!!!!!!!found Subject of Treatment and added to personvo collection is it empty? " +
                           thePersonVOCollection.isEmpty());
              continue;
            }

            //REPORTING_FACILITY
            if (participationDT.getTypeCd().equals(NEDSSConstants.RPT_FACILITY_OF_TREATMENT) &&
                participationDT.getSubjectClassCd().equals(NEDSSConstants.
                ORGANIZATION_CLASS_CODE) &&
                participationDT.getRecordStatusCd().equals(NEDSSConstants.
                ACTIVE) &&
                participationDT.getActClassCd().equals(NEDSSConstants.
                TREATMENT_CLASS_CODE)) {
              logger.debug(
                  "!!!!!!!!!1Found Reporting facility of Treatment in ParticipationDT");
              theOrganizationVOCollection.add(entityController.getOrganization(
                  participationDT.getSubjectEntityUid(), nbsSecurityObj));
              //treatmentProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
              logger.debug(
                  "!!!!!!!!!!found RptFacility of Treatment and added to organizationVO collection");
              continue;
            }

          }
          else {
            logger.debug(
                "PartcipationDT.getTypeCd null in loop " + i + "withActUid: " + participationDT.getActUid());
            continue;
          }
        } //end of long for statement to go through participation collection
      } //end of if statement checking if participationcollection is empty

      //for LDFs
      ArrayList<Object> ldfList = new ArrayList<Object> ();
      try
      {
          LDFHelper ldfHelper = LDFHelper.getInstance();
          ldfList = (ArrayList<Object> )ldfHelper.getLDFCollection(treatmentUid,null,nbsSecurityObj);
        }
        catch (Exception e) {
          logger.fatal(
            "Exception occured while retrieving LDFCollection<Object>  = " + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(), e);
        }

        if (ldfList != null) {
          logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
          treatmentProxyVO.setTheStateDefinedFieldDataDTCollection(ldfList);
        }

      treatmentProxyVO.setThePersonVOCollection(thePersonVOCollection);
      treatmentProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
      treatmentProxyVO.setTheTreatmentVO(treatmentVO);
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(nbsSecurityObj.getFullName(),
                   "Could not create actcontroller and/or entityController"+e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
    return treatmentProxyVO;
  }// end method getTreatmentProxy

  /**
   * Set the associated session context. The container calls this method after the instance
   * creation. The enterprise Bean instance should store the reference to the context
   * object in an instance variable. This method is called with no transaction context.
   */
  public void setSessionContext(javax.ejb.SessionContext sessioncontext) throws
      javax.ejb.EJBException, java.rmi.RemoteException {
  }

  /**
   * Deletes the value of a TreatmentProxy object.
   * @param treatmentUid     the Uid value of the selected treatment
   * @param nbsSecurityObj      the current value of the NBSSecurityObj object
   * @return  the value for the delete action
   * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException
   */
  public boolean deleteTreatmentProxy(Long treatmentUid,
                                      NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException, javax.ejb.EJBException,
      CreateException, FinderException, NEDSSSystemException,
      NEDSSConcurrentDataException {
    try {
		boolean isDeletable = false;

		if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
		                                  NBSOperationLookup.MANAGE,
		                                  ProgramAreaJurisdictionUtil.
		                                  ANY_PROGRAM_AREA,
		                                  ProgramAreaJurisdictionUtil.
		                                  ANY_JURISDICTION)) {
		  logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE) is false");
		  throw new EJBException("NO PERMISSIONS");
		}

		logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.MANAGE in deleteTreatmentProxy");
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			int resultCount = 0;
			String result = WumSqlQuery.GET_ASSOCIATED_TREATMENT_RECORDS;
			try {
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(result);
				preparedStmt.setLong(1, treatmentUid.longValue());
				resultSet = preparedStmt.executeQuery();
				if (resultSet != null && resultSet.next())
					resultCount = resultSet.getInt(1);
				else
					logger.debug("resultCount: " + resultCount);
				if (resultCount > 0) {
					return false;
				} 
			} catch (Exception e) {
				logger.fatal(
						nbsSecurityObj.getFullName(),
						"Could not delete treatment or exception in deleting Objects",
						e);
				logger.debug("This record cannot be deleted");
				return isDeletable;
			} finally {
				try {
					resultSet.close();
					preparedStmt.close();
					dbConnection.close();
				} catch (SQLException sex) {
					sex.printStackTrace();
				}
			}
		try {
		  NedssUtils nedssUtils = new NedssUtils();
		  ActController actController = null;
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  logger.debug("ActController lookup = " + object.toString());
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		      narrow(object, ActControllerHome.class);
		  logger.debug("Found ActControllerHome: " + acthome);
		  actController = acthome.create();
		  TreatmentDT treatmentDT = actController.getTreatmentInfo(treatmentUid,
		      nbsSecurityObj);
		  treatmentDT.setItDelete(false);
		  treatmentDT.setItDirty(true);
		  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

		  //Treatment State Model Trigger
		  String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
		  String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_DEL;
		  String TABLE_NAME = DataTables.TREATMENT_TABLE;
		  String MODULE_CD = NEDSSConstants.BASE;

		  TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils.prepareVO(
		      treatmentDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
		      TABLE_NAME, MODULE_CD, nbsSecurityObj);

		  try {
		    actController.setTreatmentInfo(newTreatmentDT, nbsSecurityObj);
		    isDeletable = true;
		    logger.debug("isDeletable is :" + isDeletable);
		    return isDeletable;
		  }
		  catch (NEDSSConcurrentDataException ex) {
		    logger.fatal(
		        "The entity cannot be deleted as concurrent access is not allowed!"+ex.getMessage(), ex);
		    // cntx.setRollbackOnly();
		    throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		  }
		  catch (Exception e) {
		    logger.fatal(nbsSecurityObj.getFullName(),
		        "Could not delete treatment or exception in deleting Objects", e);
		    logger.fatal(
		        "Throwing NEDSSConcurrentDataException in TreatmentProxyEJB"+e.getMessage(), e);
		    throw new NEDSSConcurrentDataException(e.getMessage(), e);
		  }
		}
		catch (NEDSSConcurrentDataException ex) {
		  logger.fatal(
		      "The entity cannot be deleted 1 as concurrent access is not allowed!"+ex.getMessage(), ex);
		  throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		}
		catch (Exception e) {
		  if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
		    logger.fatal(
		        "Throwing NEDSSConcurrentDataException in TreatmentProxyEJB");
		    throw new NEDSSConcurrentDataException(e.getMessage(), e);
		  }
		  return isDeletable;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }// end method deleteTreatmentProxy

  /**
   * Access method for all treatmentProxyVO objects for the selected person UID
   * @param personUID      the UID value of the selected person
   * @param nbsSecurityObj      the current value of the NBSSecurityObj object
   * @return  an arraylist which contains all treatmentProxyVO objects for the selected person UID
       * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException
   */
  public Collection<Object>  getTreatmentTableCollectionForPerson(Long personUID,
      NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
      javax.ejb.EJBException, NEDSSSystemException {
    if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                      NBSOperationLookup.VIEW)) {
      logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.VIEW) is false");
      throw new NEDSSSystemException("NO PERMISSIONS");
    }
    ArrayList<Object> treatmentProxyVOColl = new ArrayList<Object> ();

    try {
      logger.info("NBSSecurityObj.getPermission(NBSBOLookup.TREATMENT,NBSOperationLookup.VIEW) is true");
      ParticipationDAOImpl participationDAOImpl = null;
      ParticipationDT participationDT = null;
      ArrayList<Object>  participationDTCollection  = new ArrayList<Object> ();
      logger.debug("personUID = " + personUID.longValue());
      participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(
          JNDINames.ACT_PARTICIPATION_DAO_CLASS);
      participationDTCollection  = (ArrayList<Object> )(participationDAOImpl.load(
          personUID.longValue(), NEDSSConstants.SUBJECT_OF_TREATMENT,
          NEDSSConstants.TREATMENT_CLASS_CODE,
          NEDSSConstants.PERSON_CLASS_CODE));
      logger.debug("got collection part");
      if (!participationDTCollection.isEmpty()) {
        logger.debug("inside add act uids ");
       Iterator<Object>  partIterator = participationDTCollection.iterator();
        while (partIterator.hasNext()) {
          participationDT = (ParticipationDT) partIterator.next();
          if (participationDT.getRecordStatusCd() != null)
            if (participationDT.getRecordStatusCd().trim().equals(
                NEDSSConstants.RECORD_STATUS_ACTIVE)) {
              TreatmentProxyVO treatmentProxyVO = new TreatmentProxyVO();
              treatmentProxyVO = getTreatmentProxy(participationDT.getActUid(),
                  nbsSecurityObj);
              if (treatmentProxyVO != null) {
                if (treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().
                    getRecordStatusCd() != null &&
                    treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().
                    getRecordStatusCd().trim().equals(NEDSSConstants.
                    RECORD_STATUS_ACTIVE)) {
                  treatmentProxyVOColl.add(treatmentProxyVO);
                }
              }
            }
        } //while
      } //if
      else {
        logger.error("ParticipationDT is empty");
      }
    }
    catch (Exception e) {
      logger.fatal("Error in treatment Proxy" + e.getMessage(), e);
      e.printStackTrace();
      throw new java.rmi.RemoteException(e.getMessage(),e);
    }
    return treatmentProxyVOColl;
  }// end method getTreatmentTableCollectionForPerson

/**
   * Associates the treatment with the selected investigation record automatically
* @param treatmentProxyVO    the value of the treatmentProxyVO
* @param investigationList   ArrayList of Investigations to associate the treatment with
* @param nbsSecurityObj      the current value of the NBSSecurityObj object
* @return  the Uid value of the treatment
* @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
*/
public Long setTreatmentProxyWithAutoAssoc(TreatmentProxyVO treatmentProxyVO,
                                         String actType,
                                         ArrayList<Long> investigationList,
                                         NBSSecurityObj nbsSecurityObj) throws
  java.rmi.RemoteException, javax.ejb.EJBException,
  CreateException, FinderException, NEDSSSystemException {

logger.debug("Now in setTreatmentWithAA, actType: " + actType + ", id: " +
             investigationList.get(0));

Long treatmentUid = null;
Long investigationUid = null;

ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();

try {

  treatmentUid = setTreatmentProxy(treatmentProxyVO, nbsSecurityObj);
  TreatmentSummaryVO treatmentSummaryVO = new TreatmentSummaryVO();
  treatmentSummaryVO.setIsTouched(true);
  treatmentSummaryVO.setIsAssociated(true);
  treatmentSummaryVO.setTreatmentUid(treatmentUid);
  Collection<Object>  treatmentSummaryVOColl = new ArrayList<Object> ();
  treatmentSummaryVOColl.add(treatmentSummaryVO);
  /** @todo Decide Act Here (Inv or Morb?) */
  //if actType=INV....if actType=MORB....
  
  Iterator invIter = investigationList.iterator();
  while (invIter.hasNext()) {
	  investigationUid = (Long) invIter.next();
	  manageAutoAssc.setTreatmentAssociationsImpl(investigationUid, actType,
                                              treatmentSummaryVOColl,
                                              nbsSecurityObj);
  }

}
catch (Exception e) {
  logger.fatal(
      "An error occurred while setting setTreatmentProxy auto associations Investigation UID = " +
      investigationUid + "TreatmentProxyVO = " +
      treatmentProxyVO.toString() + ", " + e.getMessage(),e);
  e.printStackTrace();

  return treatmentUid;

}

return treatmentUid;
}// end method setTreatmentProxyWithAutoAssoc
 
/**
 * Associates the treatment with the investigations in the associate list.
 * Physically deletes associations in the disassociate list.
* @param treatmentUid   the treatmentUid to associate to the list of Invs
* @param associatedInvestigationList  list of Investigations to associate the treatment with
* @param disassociatedInvestigationList   list of Investigations to remove association with the treatment with
* @param nbsSecurityObj      the current value of the NBSSecurityObj object
* @return  void
* @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
*/
public void setTreatmentCaseAssociations(Long treatmentUid,
                                       String actType,
                                       ArrayList<Long> associatedInvestigationList,
                                       ArrayList<Long> disassociatedInvestigationList,
                                       NBSSecurityObj nbsSecurityObj) throws
java.rmi.RemoteException, javax.ejb.EJBException,
CreateException, FinderException, NEDSSSystemException {

	try {
		logger.debug("Now in setTreatmentCaseAssociations for treatmentUid: " + treatmentUid.toString() + "/n Number to associate= " +
			associatedInvestigationList.size() + " Number to DisAssociate=" + disassociatedInvestigationList.size());


		Long investigationUid = null;

		ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();
		TreatmentSummaryVO treatmentSummaryVO = new TreatmentSummaryVO();
		
		//process associated investigations
		treatmentSummaryVO.setIsTouched(true);
		treatmentSummaryVO.setIsAssociated(true);
		treatmentSummaryVO.setTreatmentUid(treatmentUid);
		Collection<Object>  treatmentSummaryVOColl = new ArrayList<Object> ();
		treatmentSummaryVOColl.add(treatmentSummaryVO);
		
		try {
				Iterator addIter = associatedInvestigationList.iterator();
				while (addIter.hasNext()) {
					investigationUid = (Long) addIter.next();
					manageAutoAssc.setTreatmentAssociationsImpl(investigationUid, actType,
		                                        treatmentSummaryVOColl,
		                                        nbsSecurityObj);
				}
		} catch (Exception e) {
				logger.fatal(
						"An error occurred while adding Case associations setTreatmentCaseAssociations Investigation UID = " +
								investigationUid + " TreatmentUID = " + treatmentUid.toString() + ", " + e.getMessage(),e);
				e.printStackTrace();
				throw new NEDSSSystemException(e.getMessage(), e);
		}
		
		//process disassociated investigations
		treatmentSummaryVO = new TreatmentSummaryVO();
		treatmentSummaryVO.setIsTouched(true);
		treatmentSummaryVO.setIsAssociated(false);
		treatmentSummaryVO.setTreatmentUid(treatmentUid);
		treatmentSummaryVOColl = new ArrayList<Object> ();
		treatmentSummaryVOColl.add(treatmentSummaryVO);
		
		try {
				Iterator removeIter = disassociatedInvestigationList.iterator();
				while (removeIter.hasNext()) {
					investigationUid = (Long) removeIter.next();
					manageAutoAssc.setTreatmentAssociationsImpl(investigationUid, actType,
		                                        treatmentSummaryVOColl,
		                                        nbsSecurityObj);
				}
		} catch (Exception e) {
				logger.fatal(
						"An error occurred while removing Case associations setTreatmentCaseAssociations TreatmentUID = " +
								treatmentUid.toString() + " Investigation UID = " + investigationUid + ", " + e.getMessage(),e);
				e.printStackTrace();
				throw new NEDSSSystemException(e.getMessage(), e);
		}

		
		
return;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
}// end method setTreatmentCaseAssociations
  
  
}// end class TreatmentProxyEJB