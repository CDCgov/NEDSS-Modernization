package gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean;

// Import Statements
import gov.cdc.nedss.act.clinicaldocument.dt.ClinicalDocumentDT;
import gov.cdc.nedss.act.clinicaldocument.vo.ClinicalDocumentVO;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.act.util.ManageAutoAssociations;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipHistoryManager;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistoryManager;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dao.RoleHistDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entitygroup.dt.EntityGroupDT;
import gov.cdc.nedss.entity.entitygroup.vo.EntityGroupVO;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.NonPersonLivingSubjectVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.SummaryDataDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.ManageSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.CDAEventSummaryParser;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.UidSummaryVO;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

public class InvestigationProxyEJB extends BMPBase 
    implements javax.ejb.SessionBean {
	private static final long serialVersionUID = 1L;
  //For logging
  static final LogUtils logger = new LogUtils(InvestigationProxyEJB.class.
                                              getName());

  /**
   * @roseuid 3BF98CCD00F7
   * @J2EE_METHOD  --  InvestigationProxyEJB
   */
  public InvestigationProxyEJB() {

  }

  InvestigationProxyVO investigationProxyVO = null;
  /**
   * @roseuid 3BF98CCD0129
   * @J2EE_METHOD  --  ejbRemove
   * A container invokes this method before it ends the life of the session object. This
   * happens as a result of a client's invoking a remove operation, or when a container
   * decides to terminate the session object after a timeout. This method is called with
   * no transaction context.
   */
  public void ejbRemove() {

  }

  /**
   * @roseuid 3BF98CCD0147
   * @J2EE_METHOD  --  ejbActivate
   * The activate method is called when the instance is activated from its 'passive' state.
   * The instance should acquire any resource that it has released earlier in the ejbPassivate()
   * method. This method is called with no transaction context.
   */
  public void ejbActivate() {

  }

  /**
   * @roseuid 3BF98CCD0165
   * @J2EE_METHOD  --  ejbPassivate
   * The passivate method is called before the instance enters the 'passive' state. The
   * instance should release any resources that it can re-acquire later in the ejbActivate()
   * method. After the passivate method completes, the instance must be in a state that
   * allows the container to use the Java Serialization protocol to externalize and store
       * away the instance's state. This method is called with no transaction context.
   */
  public void ejbPassivate() {

  }

  /**
   * @roseuid 3BF98CCD0183
   * @J2EE_METHOD  --  setSessionContext
   * Set the associated session context. The container calls this method after the instance
   * creation. The enterprise Bean instance should store the reference to the context
   * object in an instance variable. This method is called with no transaction context.
   */

  public void setSessionContext(SessionContext sc) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException {
  }

  /**
   * @roseuid 3BF9929601FB
   * @J2EE_METHOD  --  ejbCreate
   * Called by the container to create a session bean instance. Its parameters typically
   * contain the information the client uses to customize the bean instance for its use.
   * It requires a matching pair in the bean class and its home interface.
   */
  public void ejbCreate() throws java.rmi.RemoteException,
      javax.ejb.CreateException {
  }

  /**
   * This method checks for the negative uid value for any ACT & ENTITY DT then compare them
   * with respective negative values in ActRelationshipDT and ParticipationDT as received from
   * the investigationProxyVO(determined in the addInvestigation method).
   * As it has also got the actualUID (determined in the addInvestigation method) it replaces them accordingly.
   */
  private void setFalseToNew(InvestigationProxyVO investigationProxyVO,
                             Long falseUid, Long actualUid) {
   try {
	Iterator<Object>  anIterator = null;
	
	    ParticipationDAOImpl participationDAOImpl = null;
	    ParticipationDT participationDT = null;
	    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
	    ActRelationshipDT actRelationshipDT = null;
	    RoleDT roleDT = null;
	
	    Collection<Object>  participationColl = (ArrayList<Object> ) investigationProxyVO.
	        getTheParticipationDTCollection();
	    Collection<Object>  actRelationShipColl = (ArrayList<Object> ) investigationProxyVO.
	        getTheActRelationshipDTCollection();
	    Collection<Object>  roleColl = (ArrayList<Object> ) investigationProxyVO.
	        getTheRoleDTCollection();
	
	    if (participationColl != null) {
	      for (anIterator = participationColl.iterator(); anIterator.hasNext(); ) {
	        participationDT = (ParticipationDT) anIterator.next();
	        logger.debug("(participationDT.getAct() comparedTo falseUid)" +
	                     (participationDT.getActUid().compareTo(falseUid)));
	        if (participationDT.getActUid().compareTo(falseUid) == 0) {
	          participationDT.setActUid(actualUid);
	        }
	        if (participationDT.getSubjectEntityUid().compareTo(falseUid) == 0) {
	          participationDT.setSubjectEntityUid(actualUid);
	        }
	      }
	      logger.debug("participationDT.getSubjectEntityUid()" +
	                   participationDT.getSubjectEntityUid());
	    }
	
	    if (actRelationShipColl != null) {
	      for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext(); ) {
	        actRelationshipDT = (ActRelationshipDT) anIterator.next();
	
	        if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0) {
	          actRelationshipDT.setTargetActUid(actualUid);
	        }
	        if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0) {
	          actRelationshipDT.setSourceActUid(actualUid);
	        }
	        logger.debug("ActRelationShipDT: falseUid " + falseUid.toString() +
	                     " actualUid: " + actualUid);
	
	      }
	
	    }
	
	    if (roleColl != null) {
	      for (anIterator = roleColl.iterator(); anIterator.hasNext(); ) {
	        roleDT = (RoleDT) anIterator.next();
	        if (roleDT.getSubjectEntityUid().compareTo(falseUid) == 0) {
	          roleDT.setSubjectEntityUid(actualUid);
	        }
	        if (roleDT.getScopingEntityUid() != null) {
	          if (roleDT.getScopingEntityUid().compareTo(falseUid) == 0) {
	            roleDT.setScopingEntityUid(actualUid);
	          }
	          logger.debug(
	              "\n\n\n(roleDT.getSubjectEntityUid() compared to falseUid)  " +
	              roleDT.getSubjectEntityUid().compareTo(falseUid));
	          logger.debug(
	              "\n\n\n(roleDT.getScopingEntityUid() compared to falseUid)  " +
	              roleDT.getScopingEntityUid().compareTo(falseUid));
	        }
	      }
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("InvestigationProxyEJB.setFalseToNew: " + e.getMessage(), e);
	throw new EJBException(e.getMessage(), e);

}
  }

  /**
   * Sets the value of a InvestigationProxy object & automatically resends notifications if notifications exist
   * @roseuid 3BF992960241
   * @J2EE_METHOD  --  setInvestigationProxy
   */
  public Long setInvestigationProxy(InvestigationProxyVO investigationProxyVO,
                                    NBSSecurityObj nbsSecurityObj) throws java.
      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
      NEDSSSystemException, NEDSSConcurrentDataException {
    try {
		//saving InvestigationProxyVO before updating auto resend notifications
		Long actualUid = this.setInvestigationProxyWithoutNotificationAutoResend(
		    investigationProxyVO, nbsSecurityObj);
		NNDMessageSenderHelper nndMessageSenderHelper = NNDMessageSenderHelper.
		    getInstance();

		    	boolean autoResendFlag = false;
				if (investigationProxyVO.isItDirty()
						&& investigationProxyVO.getTheNotificationSummaryVOCollection() != null
						&& investigationProxyVO.getTheNotificationSummaryVOCollection()
								.size() > 0) {
					Iterator ite = investigationProxyVO
							.getTheNotificationSummaryVOCollection().iterator();
					while (ite.hasNext()) {
						NotificationSummaryVO notSummaryVO = (NotificationSummaryVO) ite
								.next();
						if (notSummaryVO.getAutoResendInd()!=null &&
							notSummaryVO.getAutoResendInd().equals("T")) {
								autoResendFlag = true;
								break;
						}
					}
		    //update auto resend notifications
		   try {
		    	  if(autoResendFlag)
				  nndMessageSenderHelper.updateAutoResendNotificationsAsync(investigationProxyVO, nbsSecurityObj);
		  }
		  catch (Exception e) {
		    NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
		    String phcLocalId = investigationProxyVO.getPublicHealthCaseVO().
		        getThePublicHealthCaseDT().getLocalId();
		    nndActivityLogDT.setErrorMessageTxt(e.toString());
		    if (phcLocalId != null)
		      nndActivityLogDT.setLocalId(phcLocalId);
		    else
		      nndActivityLogDT.setLocalId("N/A");
		      //catch & store auto resend notifications exceptions in NNDActivityLog table
		    nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,
		        nbsSecurityObj);
		    logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
		    e.printStackTrace();
		  }
		    return actualUid;
		}
		else
		  return actualUid;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.setInvestigationProxy: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  private Long setInvestigationProxyWithoutNotificationAutoResend(
      InvestigationProxyVO investigationProxyVO, NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException, javax.ejb.EJBException,
      javax.ejb.CreateException, NEDSSSystemException,
      NEDSSConcurrentDataException {

    PublicHealthCaseDT phcDT = investigationProxyVO.getPublicHealthCaseVO().
        getThePublicHealthCaseDT();

    // if both are false throw exception
    if ( (!investigationProxyVO.isItNew()) && (!investigationProxyVO.isItDirty())) {
      logger.info("investigationProxyVO.isItNew() = " +
                  investigationProxyVO.isItNew() +
                  " and investigationProxyVO.isItDirty() = " +
                  investigationProxyVO.isItDirty());
      throw new NEDSSSystemException("investigationProxyVO.isItNew() = " +
                                     investigationProxyVO.isItNew() +
                                     " and investigationProxyVO.isItDirty() = " +
                                     investigationProxyVO.isItDirty() +
                                     " for setInvestigationProxy");
    }
    logger.info("investigationProxyVO.isItNew() = " +
                investigationProxyVO.isItNew() +
                " and investigationProxyVO.isItDirty() = " +
                investigationProxyVO.isItDirty());

    if (investigationProxyVO.isItNew()) {
      logger.info("investigationProxyVO.isItNew() = " +
                  investigationProxyVO.isItNew() +
                  " and investigationProxyVO.isItDirty() = " +
                  investigationProxyVO.isItDirty());

      boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.
          getPermission(
          NBSBOLookup.INVESTIGATION,
          NBSOperationLookup.AUTOCREATE,
          phcDT.getProgAreaCd(),
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
          phcDT.getSharedInd());

      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                        NBSOperationLookup.ADD,
                                        phcDT.getProgAreaCd(),
                                        ProgramAreaJurisdictionUtil.
                                        ANY_JURISDICTION, phcDT.getSharedInd()) &&
          ! (checkInvestigationAutoCreatePermission)) {
        logger.info("no add permissions for setInvestigationProxy");
        throw new NEDSSSystemException(
            "NO ADD PERMISSIONS for setInvestigationProxy");
      }
      logger.info("user has add permissions for setInvestigationProxy");
    }
    else if (investigationProxyVO.isItDirty()) {
      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                        NBSOperationLookup.EDIT,
                                        phcDT.getProgAreaCd(),
                                        phcDT.getJurisdictionCd(),
                                        phcDT.getSharedInd())) {
        logger.info("no edit permissions for setInvestigationProxy");
        throw new NEDSSSystemException(
            "NO EDIT PERMISSIONS for setInvestigationProxy");
      }
    }
    logger.info("investigationProxyVO.isItDirty() = " +
                investigationProxyVO.isItDirty() +
                " and user has edit permissions for setInvestigationProxy");

    if (investigationProxyVO.isItNew() && (!investigationProxyVO.isItDirty())) {
      //changes according to new Analysis
      String classCd;
      Long entityUID;
      String recordStatusCd;
      ParticipationDT partDT = null;
     Iterator<Object>  partIter = investigationProxyVO.getTheParticipationDTCollection().
          iterator();

      while (partIter.hasNext()) {
        partDT = (ParticipationDT) partIter.next();
        entityUID = partDT.getSubjectEntityUid();
        if (entityUID != null && entityUID > 0) {
          classCd = partDT.getSubjectClassCd();
          if (classCd != null &&
              classCd.compareToIgnoreCase(NEDSSConstants.PERSON) == 0) {

            //Now, get PersonVO from Entity Controller and check if Person is active, if not throw DataConcurrenceException

            EntityController entityController = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
            logger.debug("EntityController lookup = " + obj.toString());
            EntityControllerHome home = (EntityControllerHome)
                PortableRemoteObject.narrow(obj, EntityControllerHome.class);
            logger.debug("Found EntityControllerHome: " + home);
            entityController = home.create();
            PersonVO personVO = (PersonVO) entityController.getPerson(entityUID,
                nbsSecurityObj);
            recordStatusCd = personVO.getThePersonDT().getRecordStatusCd();

            if (recordStatusCd != null &&
                recordStatusCd.trim().
                compareToIgnoreCase(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE) ==
                0) {

              throw new NEDSSConcurrentDataException(
                  "The Person you are trying to create Investigation no Longer exists !!");
            }
          } //if
        } //entityUID > 0
      } //while
    } //if

    Long actualUid = null;
    PersonVO personVO = null;
    NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
    ClinicalDocumentVO clinicalDocumentVO = null;
    EntityGroupVO entityGroupVO = null;
    InterventionVO interventionVO = null;
    MaterialVO materialVO = null;
    NotificationVO notificationVO = null;
    OrganizationVO organizationVO = null;
    PatientEncounterVO patientEncounterVO = null;
    PlaceVO placeVO = null;
    ReferralVO referralVO = null;
    ObservationVO observationVO = null;
    EntityGroupDT entityGroupDT = null;
    InterventionDT interventionDT = null;
    PatientEncounterDT patientEncounterDT = null;
    ClinicalDocumentDT clinicalDocumentDT = null;
    ReferralDT referralDT = null;
    PlaceDT placeDT = null;

   Iterator<Object>  anIterator = null;
    ParticipationDAOImpl participationDAOImpl = null;
    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
    RoleDAOImpl roleDAOImpl = null;
    Long falsePublicHealthCaseUid = null;
    try {
      EntityController entityController = null;
      NedssUtils nedssUtils = new NedssUtils();
      Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
      logger.debug("EntityController lookup = " + obj.toString());
      EntityControllerHome home = (EntityControllerHome) PortableRemoteObject.
          narrow(obj, EntityControllerHome.class);
      logger.debug("Found EntityControllerHome: " + home);
      entityController = home.create();

      ActController actController = null;
      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());
      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(object, ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);
      actController = acthome.create();

      /** EntityProxy entityProxy = null;
       Object objEntity = nedssUtils.lookupBean(JNDINames.ENTITY_PROXY_EJB);
       logger.debug("EntityProxyEJB lookup = " + objEntity.toString());
       EntityProxyHome entityProxyHome =(EntityProxyHome)PortableRemoteObject.narrow(objEntity, EntityProxyHome.class);
       logger.debug("Found EntityProxyHome: " + entityProxyHome);
       entityProxy = entityProxyHome.create();
       */

      Long falseUid = null;
      Long realUid = null;
      if (investigationProxyVO.getTheClinicalDocumentVOCollection() != null) {
        for (anIterator = investigationProxyVO.
             getTheClinicalDocumentVOCollection().iterator();
             anIterator.hasNext(); ) {
          clinicalDocumentVO = (ClinicalDocumentVO) anIterator.next();
          if (clinicalDocumentVO.isItNew() || clinicalDocumentVO.isItDirty()) {
            falseUid = clinicalDocumentVO.getTheClinicalDocumentDT().
                getClinicalDocumentUid();
            logger.debug("the  falseClinicalUid is" + falseUid);
            clinicalDocumentDT = clinicalDocumentVO.getTheClinicalDocumentDT();
            realUid = actController.setClinicalDocument(clinicalDocumentVO,
                nbsSecurityObj);
            logger.debug("the  reaClinilcalUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getTheReferralVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheReferralVOCollection().
             iterator(); anIterator.hasNext(); ) {
          referralVO = (ReferralVO) anIterator.next();
          if (referralVO.isItNew() || referralVO.isItDirty()) {
            falseUid = referralVO.getTheReferralDT().getReferralUid();
            logger.debug("the  falseReferralUid is" + falseUid);
            referralDT = referralVO.getTheReferralDT();
            logger.debug("the status time is " + referralDT.getStatusTime());
            logger.debug("the status code  is " + referralDT.getStatusCd());
            realUid = actController.setReferral(referralVO, nbsSecurityObj);
            logger.debug("the  realReferralUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getTheInterventionVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheInterventionVOCollection().
             iterator(); anIterator.hasNext(); ) {
          interventionVO = (InterventionVO) anIterator.next();
          if (interventionVO.isItNew() || interventionVO.isItDirty()) {
            falseUid = interventionVO.getTheInterventionDT().getInterventionUid();
            logger.debug("the  falseInterventionVOUid is" + falseUid);
            interventionDT = interventionVO.getTheInterventionDT();
            realUid = actController.setIntervention(interventionVO,
                nbsSecurityObj);
            logger.debug("the  realInterventionUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getThePatientEncounterVOCollection() != null) {
        for (anIterator = investigationProxyVO.
             getThePatientEncounterVOCollection().iterator();
             anIterator.hasNext(); ) {
          patientEncounterVO = (PatientEncounterVO) anIterator.next();
          if (patientEncounterVO.isItNew() || patientEncounterVO.isItDirty()) {
            falseUid = patientEncounterVO.getThePatientEncounterDT().
                getPatientEncounterUid();
            logger.debug("the  falsePatientEncounterUid is" + falseUid);
            patientEncounterDT = patientEncounterVO.getThePatientEncounterDT();
            realUid = actController.setPatientEncounter(patientEncounterVO,
                nbsSecurityObj);
            logger.debug("the  realPatientEncounterUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getThePlaceVOCollection() != null) {
        for (anIterator = investigationProxyVO.getThePlaceVOCollection().
             iterator(); anIterator.hasNext(); ) {
          placeVO = (PlaceVO) anIterator.next();
          if (placeVO.isItNew() || placeVO.isItDirty()) {
            falseUid = placeVO.getThePlaceDT().getPlaceUid();
            logger.debug("the  falsePlaceUid is" + falseUid);
            placeDT = placeVO.getThePlaceDT();
            realUid = entityController.setPlace(placeVO, nbsSecurityObj);
            logger.debug("the  realPlaceVOUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getTheNonPersonLivingSubjectVOCollection() != null) {
        for (anIterator = investigationProxyVO.
             getTheNonPersonLivingSubjectVOCollection().iterator();
             anIterator.hasNext(); ) {
          nonPersonLivingSubjectVO = (NonPersonLivingSubjectVO) anIterator.next();
          if (nonPersonLivingSubjectVO.isItNew() ||
              nonPersonLivingSubjectVO.isItDirty()) {
            falseUid = nonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().
                getNonPersonUid();
            logger.debug("the  falseNonPersonUid is" + falseUid);
            realUid = entityController.setNonPersonLivingSubject(
                nonPersonLivingSubjectVO, nbsSecurityObj);
            logger.debug("the  RealNonPersonUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getTheOrganizationVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheOrganizationVOCollection().
             iterator(); anIterator.hasNext(); ) {

          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          organizationVO = (OrganizationVO) anIterator.next();
          OrganizationDT newOrganizationDT = null;
          if (organizationVO.isItNew()) {
            newOrganizationDT = (OrganizationDT) prepareVOUtils.prepareVO(
                organizationVO.getTheOrganizationDT(), NBSBOLookup.ORGANIZATION,
                NEDSSConstants.ORG_CR, DataTables.ORGANIZATION_TABLE,
                NEDSSConstants.BASE, nbsSecurityObj);
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            falseUid = organizationVO.getTheOrganizationDT().getOrganizationUid();
            logger.debug("the  falseOrganizationUid is" + falseUid);
            String businessTriggerCd = NEDSSConstants.ORG_CR;
            realUid = entityController.setOrganization(organizationVO,
                businessTriggerCd, nbsSecurityObj);
            logger.debug("the  realOrganizationUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
          else if (organizationVO.isItDirty()) {
            newOrganizationDT = (OrganizationDT) prepareVOUtils.prepareVO(
                organizationVO.getTheOrganizationDT(), NBSBOLookup.ORGANIZATION,
                NEDSSConstants.ORG_EDIT, DataTables.ORGANIZATION_TABLE,
                NEDSSConstants.BASE, nbsSecurityObj);
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            falseUid = organizationVO.getTheOrganizationDT().getOrganizationUid();
            String businessTriggerCd = NEDSSConstants.ORG_EDIT;
            realUid = entityController.setOrganization(organizationVO,
                businessTriggerCd, nbsSecurityObj);
          }

        }
      }

      if (investigationProxyVO.getThePersonVOCollection() != null) {
        for (anIterator = investigationProxyVO.getThePersonVOCollection().
             iterator(); anIterator.hasNext(); ) {
         
          personVO = (PersonVO) anIterator.next();
          logger.debug("The Base personDT is :" + personVO.getThePersonDT());
          logger.debug("The personUID is :" +
                       personVO.getThePersonDT().getPersonUid());

          if (personVO.isItNew()) {
            if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) { //Patient
              String businessTriggerCd = NEDSSConstants.PAT_CR;
              try {
                realUid = entityController.setPatientRevision(personVO,
                    businessTriggerCd, nbsSecurityObj);
              }
              catch (NEDSSConcurrentDataException ex) {
                // cntx.setRollbackOnly();
                logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: Concurrent access is not allowed: " + ex.getMessage(), ex);
                throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
              }
              catch (Exception ex) {
            	  logger.fatal("Error in executing entityController.setPatientRevision when personVO.isNew is true");
                logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: " + ex.getMessage(), ex);
                throw new javax.ejb.EJBException(ex.getMessage(), ex);
              }

            }
            else if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PRV
                )) { //Provider
              String businessTriggerCd = NEDSSConstants.PRV_CR;
              try {
                realUid = entityController.setProvider(personVO,
                    businessTriggerCd, nbsSecurityObj);
              }
              catch (NEDSSConcurrentDataException ex) {
            	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: Concurrent access is not allowed: " + ex.getMessage(), ex);
                  throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
              }
              catch (Exception ex) {
                logger.fatal("Error in executing entityController.setProvider when personVO.isNew is true");
                logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: " + ex.getMessage(), ex);
                throw new javax.ejb.EJBException(ex.getMessage(), ex);
              }

            } //end of else if

            falseUid = personVO.getThePersonDT().getPersonUid();
            logger.debug("the  falsePersonUid is " + falseUid);
            logger.debug("The realUid of Patient/Provider is: " + realUid);
            //replace the falseId with the realId
            if (falseUid.intValue() < 0) {
              logger.debug(
                  "the Value for false uid for Patient/Provider is being set here");
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
          else if (personVO.isItDirty()) {
            if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
              String businessTriggerCd = NEDSSConstants.PAT_EDIT;
              try {
                realUid = entityController.setPatientRevision(personVO,
                    businessTriggerCd, nbsSecurityObj);
              }
              catch (NEDSSConcurrentDataException ex) {
            	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: Concurrent access is not allowed: " + ex.getMessage(), ex);
                  throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
              }
              catch (Exception ex) {
                logger.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true");
                logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: " + ex.getMessage(), ex);
                throw new javax.ejb.EJBException(ex.getMessage(), ex);
              }

            }
            else if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.
                PRV)) { //Provider
              String businessTriggerCd = NEDSSConstants.PRV_EDIT;
              try {
                realUid = entityController.setProvider(personVO,
                    businessTriggerCd, nbsSecurityObj);
              }
              catch (NEDSSConcurrentDataException ex) {
            	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: Concurrent access is not allowed: " + ex.getMessage(), ex);
                  throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
              }
              catch (Exception ex) {
                logger.fatal("Error in executing entityController.setProvider when personVO.isDirty is true");
                logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: " + ex.getMessage(), ex);
                throw new javax.ejb.EJBException(ex.getMessage(), ex);
              }

            } //end of else

            logger.debug("The realUid for the Patient/Provider is: " + realUid);

          }
        } //end of for
      } //end of if(investigationProxyVO.getThePersonVOCollection() != null)

      if (investigationProxyVO.getTheMaterialVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheMaterialVOCollection().
             iterator(); anIterator.hasNext(); ) {
          materialVO = (MaterialVO) anIterator.next();
          if (materialVO.isItNew() || materialVO.isItDirty()) {
            falseUid = materialVO.getTheMaterialDT().getMaterialUid();
            logger.debug("the  falseMaterialUid is" + falseUid);
            realUid = entityController.setMaterial(materialVO, nbsSecurityObj);
            logger.debug("the  realMaterialUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getTheObservationVOCollection() != null) {
        java.util.Date dt = new java.util.Date();
        ////##!! System.out.println("the time required before start is :" + dt.getTime());
        /**
         * commented out to store multiple ejb's throguh one call
         */
        /**
          for(anIterator = investigationProxyVO.getTheObservationVOCollection().iterator(); anIterator.hasNext(); )
          {
               observationVO = (ObservationVO)anIterator.next();
               if(observationVO.isItNew() || observationVO.isItDirty())
               {
             falseUid = observationVO.getTheObservationDT().getObservationUid();
                 //logger.debug("the  falseUid: " + falseUid + " observationVO.getCd: " + observationVO.getTheObservationDT().getCd());
             realUid = actController.setObservation(observationVO, nbsSecurityObj);
                 //logger.debug("the  realUid is" + realUid + " observationVO.getCd: " + observationVO.getTheObservationDT().getCd());
                 if(falseUid.intValue()< 0) setFalseToNew(investigationProxyVO, falseUid, realUid);
               }
          }*/
        ObservationRootDAOImpl ob = (ObservationRootDAOImpl) NEDSSDAOFactory.
            getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);

        HashMap<Object,Object> map = ob.UpdateObservationCollections(investigationProxyVO.
            getTheObservationVOCollection());
       Iterator<Object>  it = map.keySet().iterator();
        while (it.hasNext()) {
          falseUid = (Long) it.next();
          if (falseUid != null) {
            realUid = (Long) map.get(falseUid);
          }
          if (falseUid.intValue() < 0) {
            setFalseToNew(investigationProxyVO, falseUid, realUid);
          }
        }

        java.util.Date dt2 = new java.util.Date();

      }

      if (investigationProxyVO.getTheEntityGroupVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheEntityGroupVOCollection().
             iterator(); anIterator.hasNext(); ) {
          entityGroupVO = (EntityGroupVO) anIterator.next();
          if (entityGroupVO.isItNew() || entityGroupVO.isItDirty()) {
            falseUid = entityGroupVO.getTheEntityGroupDT().getEntityGroupUid();
            logger.debug("the  falseEntitiGroupUid is" + falseUid);
            entityGroupDT = entityGroupVO.getTheEntityGroupDT();
            realUid = entityController.setEntityGroup(entityGroupVO,
                nbsSecurityObj);
            logger.debug("the  realEntitiGroupUid is" + realUid);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      if (investigationProxyVO.getPublicHealthCaseVO() != null) {
        String businessTriggerCd = null;
        PublicHealthCaseVO publicHealthCaseVO = investigationProxyVO.
            getPublicHealthCaseVO();
        PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO.
            getThePublicHealthCaseDT();
        RootDTInterface rootDTInterface = publicHealthCaseDT;
        String businessObjLookupName = NBSBOLookup.INVESTIGATION;
        if (investigationProxyVO.isItNew()) {
          businessTriggerCd = "INV_CR";
        }
        else if (investigationProxyVO.isItDirty()) {
          businessTriggerCd = "INV_EDIT";
        }
        String tableName = "PUBLIC_HEALTH_CASE";
        String moduleCd = "BASE";
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        publicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
            rootDTInterface, businessObjLookupName, businessTriggerCd,
            tableName, moduleCd, nbsSecurityObj);
        publicHealthCaseVO.setThePublicHealthCaseDT(publicHealthCaseDT);

        falsePublicHealthCaseUid = publicHealthCaseVO.getThePublicHealthCaseDT().
            getPublicHealthCaseUid();
        actualUid = actController.setPublicHealthCase(publicHealthCaseVO,
            nbsSecurityObj);
        logger.debug("actualUid = " + actualUid);
        if (falsePublicHealthCaseUid.intValue() < 0) {
          logger.debug("falsePublicHealthCaseUid.intValue() = " +
                       falsePublicHealthCaseUid.intValue());
          setFalseToNew(investigationProxyVO, falsePublicHealthCaseUid,
                        actualUid);
          publicHealthCaseVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(
              actualUid);
        }
        logger.debug("falsePublicHealthCaseUid.intValue() = " +
                     falsePublicHealthCaseUid.intValue());
      }

      if (investigationProxyVO.getTheNotificationVOCollection() != null) {
        for (anIterator = investigationProxyVO.getTheNotificationVOCollection().
             iterator(); anIterator.hasNext(); ) {
          notificationVO = (NotificationVO) anIterator.next();
          if (notificationVO.isItNew() || notificationVO.isItDirty()) {
            falseUid = notificationVO.getTheNotificationDT().getNotificationUid();
            realUid = actController.setNotification(notificationVO,
                nbsSecurityObj);
            if (falseUid.intValue() < 0) {
              setFalseToNew(investigationProxyVO, falseUid, realUid);
            }
          }
        }
      }

      // this collection should only be populated in edit scenario, xz defect 11861 (10/01/04)
      if (investigationProxyVO.getTheNotificationSummaryVOCollection() != null) {
      	Collection<Object>  notSumVOColl = investigationProxyVO.getTheNotificationSummaryVOCollection();
       Iterator<Object>  notSumIter =  notSumVOColl.iterator();
        while(notSumIter.hasNext()){
          NotificationSummaryVO notSummaryVO = (NotificationSummaryVO)notSumIter.next();
          // Only handles notifications that are not history and not in auto-resend status.
          // for auto resend, it'll be handled separately.  xz defect 11861 (10/07/04)
          if(notSummaryVO.getIsHistory().equals("F") && notSummaryVO.getAutoResendInd().equals("F")) {
            Long notificationUid = notSummaryVO.getNotificationUid();
            String phcCd = phcDT.getCd();
            String phcClassCd = phcDT.getCaseClassCd();
            String progAreaCd = phcDT.getProgAreaCd();
            String jurisdictionCd = phcDT.getJurisdictionCd();
            String sharedInd = phcDT.getSharedInd();
            String notificationRecordStatusCode = notSummaryVO.getRecordStatusCd();
            if(notificationRecordStatusCode != null){
            	String trigCd = null;

            	/* The notification status remains same when the
            	 * Investigation or Associated objects are changed
            	 */
        		if (notificationRecordStatusCode
        				.equalsIgnoreCase(NEDSSConstants.APPROVED_STATUS)){
        				trigCd = NEDSSConstants.NOT_CR_APR;
        		}

            	// change from pending approval to approved
    			if (notificationRecordStatusCode
    				.equalsIgnoreCase(NEDSSConstants.PENDING_APPROVAL_STATUS)){
    				trigCd = NEDSSConstants.NOT_CR_PEND_APR;
    			}
              	if(trigCd != null){
              		// we only need to update notification when trigCd is not null
              		RetrieveSummaryVO. updateNotification(notificationUid,trigCd,phcCd,phcClassCd,progAreaCd,jurisdictionCd,sharedInd,nbsSecurityObj);
                }

            }
          }
        }
      }
      Long docUid = null;
      if (investigationProxyVO.getTheActRelationshipDTCollection() != null) {
        actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
        for (anIterator = investigationProxyVO.
             getTheActRelationshipDTCollection().iterator(); anIterator.hasNext(); ) {
          ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.
              next();
          if(actRelationshipDT.getTypeCd() != null && actRelationshipDT.getTypeCd().equals("DocToPHC"))
        	  docUid  = actRelationshipDT.getSourceActUid();
          logger.debug("the actRelationshipDT statusTime is " +
                       actRelationshipDT.getStatusTime());
          logger.debug("the actRelationshipDT statusCode is " +
                       actRelationshipDT.getStatusCd());
          logger.debug("Got into The ActRelationship loop");
          try {
            if (actRelationshipDT.isItDelete()) {
              insertActRelationshipHistory(actRelationshipDT);

            }
            actRelationshipDAOImpl.store(actRelationshipDT);
            logger.debug("Got into The ActRelationship, The ActUid is " +
                         actRelationshipDT.getTargetActUid());
          }
          catch (Exception e) {
        	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
              throw new javax.ejb.EJBException(e.getMessage(), e);
          }
        }
      }
      /* 
       * Updating the Document table
       */ 
      //Getting the DocumentEJB reference
      if(docUid != null){
	      try{
		      NbsDocument nbsDocument = null;
		      Object docEJB = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
		      logger.debug("DocumentEJB lookup = " + docEJB.toString());
		      NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject.
		          narrow(docEJB, NbsDocumentHome.class);
		      logger.debug("Found NbsDocumentHome: " + dochome);
		      nbsDocument = dochome.create(); 
		      //get the 
		      NBSDocumentVO nbsDocVO = nbsDocument.getNBSDocumentWithoutActRelationship(docUid, nbsSecurityObj);
		      Long nbsDocumentUid = nbsDocument.updateDocumentWithOutthePatient(nbsDocVO, nbsSecurityObj);
	      }catch(Exception e){
	    	  logger.error("Error while updating the Document table" , e.getMessage(), e);
	          e.printStackTrace();
	          throw new javax.ejb.EJBException(e.getMessage());
	      }
      }
      if (investigationProxyVO.getTheParticipationDTCollection() != null) {
        logger.debug("got the participation Collection<Object>  Loop");
        participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(
            JNDINames.ACT_PARTICIPATION_DAO_CLASS);
        logger.debug("got the participation Collection<Object>  Loop got the DAO");
        for (anIterator = investigationProxyVO.getTheParticipationDTCollection().
             iterator(); anIterator.hasNext(); ) {
          logger.debug("got the participation Collection<Object>  FOR Loop");
          ParticipationDT participationDT = (ParticipationDT) anIterator.next();
          logger.debug("the participationDT statusTime is " +
                       participationDT.getStatusTime());
          logger.debug("the participationDT statusCode is " +
                       participationDT.getStatusCd());
          logger.debug(" got the participation Loop");
          try {
            if (participationDT.isItDelete()) {
              insertParticipationHistory(participationDT);

            }
            participationDAOImpl.store(participationDT);
            logger.debug("got the participationDT, the ACTUID is " +
                         participationDT.getActUid());
            logger.debug("got the participationDT, the subjectEntityUid is " +
                         participationDT.getSubjectEntityUid());
          }
          catch (Exception e) {
        	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
              throw new javax.ejb.EJBException(e.getMessage(), e);
          }
        }
      }

      if (investigationProxyVO.getTheRoleDTCollection() != null) {
        logger.debug("got into the role Collection<Object>  Loop");
        roleDAOImpl = (RoleDAOImpl) NEDSSDAOFactory.getDAO(JNDINames.
            ENTITY_ROLE_DAO_CLASS);
        for (anIterator = investigationProxyVO.getTheRoleDTCollection().
             iterator(); anIterator.hasNext(); ) {
          RoleDT roleDT = (RoleDT) anIterator.next();
          logger.debug("the roleDT statusTime is " + roleDT.getStatusTime());
          logger.debug("the roleDT statusCode is " + roleDT.getStatusCd());
          logger.debug("Got inside the The Role loop");
          try {
            if (roleDT.isItDelete()) {
              insertRoleHistory(roleDT);
            }
            roleDAOImpl.store(roleDT);
            logger.debug("Got into The role, The rolesubjectentityUid is " +
                         roleDT.getSubjectEntityUid());
          }
          catch (Exception e) {
        	  logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
              throw new javax.ejb.EJBException(e.getMessage(), e);
          }
        }
      }

      if (investigationProxyVO.getTheStateDefinedFieldDataDTCollection() != null) {

        logger.debug("Inside setLDF of InvestigationProxy");
        
        // code for new ldf backend.
                LDFHelper ldfHelper = LDFHelper.getInstance();
                if( investigationProxyVO.getBusinessObjNm()== null)
                {
                  Collection<Object>  ldfColl = investigationProxyVO.getTheStateDefinedFieldDataDTCollection();
                  if(ldfColl!= null)
                  {
                   Iterator<Object>  it = ldfColl.iterator();
                    while(it.hasNext())
                    {
                      StateDefinedFieldDataDT dt = (StateDefinedFieldDataDT)it.next();
                      if(dt.getBusinessObjNm()!=null)
                      {
                        investigationProxyVO.setBusinessObjNm(dt.getBusinessObjNm());
                      }
                    }
                  }
                }

                if (investigationProxyVO.getBusinessObjNm() != null) {
                  ldfHelper.setLDFCollection(investigationProxyVO.
                                             getTheStateDefinedFieldDataDTCollection(),
                                             investigationProxyVO.getLdfUids(),
                                             investigationProxyVO.getBusinessObjNm(),
                                             null,
                                             investigationProxyVO.getPublicHealthCaseVO().
                                             getThePublicHealthCaseDT()
                                             .getPublicHealthCaseUid(), nbsSecurityObj
                                             );
                }


      } //if

      logger.debug("the actual Uid for InvestigationProxy Publichealthcase is " +
                   actualUid);
    }
    catch (NEDSSConcurrentDataException ex) {
    	logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: Concurrent access is not allowed: " + ex.getMessage(), ex);
        throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
    }
    catch (Exception e) {
    	logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
        throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return actualUid;
  }
//done till here
  // getInvestigationProxy *******************************************************8
  /**
   * Sequence of Events
   * 1.) Using the publicHealthCaseUID, retrieve the PublicHealthCaseDT.
   * 2.) Traverse through ActRelationshipDT and ParticipationDT to determine
   * relationships with other Acts and Entities.
   * 3.) Continue building elements of InvestigationVO by examining RoleDT to
   * determine any relevant relationships between Entities.
   * 4.) Pass completed InvestigationVO on to calling component.
   */
  /**
   * @roseuid 3BF9929602AF
   * @J2EE_METHOD  --  getInvestigation
   */
  public InvestigationProxyVO getInvestigationProxy(Long publicHealthCaseUID,
      NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException {
	  
	  return getInvestigationProxyLite(publicHealthCaseUID,
		      nbsSecurityObj, false);
  }
  public InvestigationProxyVO getInvestigationProxyLite(Long publicHealthCaseUID,
	      NBSSecurityObj nbsSecurityObj, boolean lite) throws
	      java.rmi.RemoteException,
	      javax.ejb.EJBException,
	      NEDSSSystemException,
	      javax.ejb.FinderException,
	      javax.ejb.CreateException {
  

    java.util.Date dta = new java.util.Date();
    ////##!! System.out.println("the InvestigationProxyVO starts now :" + dta.getTime());
    // if no permissions - terminate
    if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                      NBSOperationLookup.VIEW)) {
      logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is false");
      throw new NEDSSSystemException("NO PERMISSIONS");
    }
    logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is true");
    investigationProxyVO = new InvestigationProxyVO();

    PublicHealthCaseDT thePublicHealthCaseDT = null;
    PublicHealthCaseVO thePublicHealthCaseVO = null;

    ArrayList<Object>  theParticipationDTCollection;
    ArrayList<Object>  theRoleDTCollection;

    ArrayList<Object>  thePersonVOCollection  = new ArrayList<Object> (); // Person (VO)
    ArrayList<Object>  theOrganizationVOCollection  = new ArrayList<Object> (); // Organization (VO)
    ArrayList<Object>  theMaterialVOCollection  = new ArrayList<Object> (); // Material (VO)
    ArrayList<ObservationVO>  theObservationVOCollection  = new ArrayList<ObservationVO> (); // Observation (VO)
    ArrayList<Object>  theInterventionVOCollection  = new ArrayList<Object> (); // Itervention (VO)
    ArrayList<Object>  theEntityGroupVOCollection  = new ArrayList<Object> (); // Group (VO)
    ArrayList<Object>  theNonPersonLivingSubjectVOCollection  = new ArrayList<Object> (); // NPLS (VO)
    ArrayList<Object>  thePlaceVOCollection  = new ArrayList<Object> (); // Place (VO)
    //ArrayList<Object> theNotificationVOCollection  = new ArrayList<Object> ();		// Notification (VO)
    ArrayList<Object>  theReferralVOCollection  = new ArrayList<Object> (); // Referral (VO)
    ArrayList<Object>  thePatientEncounterVOCollection  = new ArrayList<Object> (); // PatientEncounter (VO)
    ArrayList<Object>  theClinicalDocumentVOCollection  = new ArrayList<Object> (); // Clinical Document (VO)

    // Summary Collections
    ArrayList<Object>  theObservationSummaryVOCollection  = new ArrayList<Object> ();
    ArrayList<Object>  theVaccinationSummaryVOCollection  = new ArrayList<Object> ();
    ArrayList<Object>  theNotificationSummaryVOCollection  = new ArrayList<Object> ();
    ArrayList<Object>  theStateDefinedFieldDTCollection  = new ArrayList<Object> ();
    ArrayList<Object>  theTreatmentSummaryVOCollection  = new ArrayList<Object> ();
    ArrayList<Object>  theDocumentSummaryVOCollection  = new ArrayList<Object> (); 

    NedssUtils nedssUtils = new NedssUtils();
    Object theLookedUpObject;

    try {
      logger.debug("* before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
      // Reference an Act controller to use later
      theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
          narrow(theLookedUpObject, ActControllerHome.class);
      ActController actController = actHome.create();

      logger.debug(
          "* before nedssUtils.lookupBean(JNDINames.EntityControllerEJB");
      // Reference an Entity controller to use later
      theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
      EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.
          narrow(theLookedUpObject, EntityControllerHome.class);
      EntityController entityController = ecHome.create();

      // Step 1: Get the Pubic Health Case
      thePublicHealthCaseVO = actController.getPublicHealthCase(
          publicHealthCaseUID, nbsSecurityObj);

      //before returning PublicHealthCaseVO check security permissions -
      // if no permissions - terminate
      if (!nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.
                                          getThePublicHealthCaseDT(),
                                          NBSBOLookup.INVESTIGATION,
                                          NBSOperationLookup.VIEW)) {
        logger.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is false");
        throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
      }
      logger.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is true");

      NBSAuthHelper helper = new NBSAuthHelper();
      thePublicHealthCaseVO.getThePublicHealthCaseDT().setAddUserName(helper.getUserName(thePublicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
      thePublicHealthCaseVO.getThePublicHealthCaseDT().setLastChgUserName(helper.getUserName(thePublicHealthCaseVO.getThePublicHealthCaseDT().getLastChgUserId()));

      thePublicHealthCaseDT = thePublicHealthCaseVO.getThePublicHealthCaseDT();
      
      Long PatientGroupID = thePublicHealthCaseDT.getPatientGroupId();
      if (PatientGroupID != null) {
        theEntityGroupVOCollection.add(entityController.getEntityGroup(
            thePublicHealthCaseDT.getPatientGroupId(), nbsSecurityObj));
      }
      logger.debug("PatientGroupID = " + PatientGroupID);
      String strTypeCd;
      String strClassCd;
      String recordStatusCd = "";
      Long nEntityID;
      ParticipationDT participationDT = null;

     Iterator<Object>  participationIterator = thePublicHealthCaseVO.
          getTheParticipationDTCollection().iterator();
      logger.debug("ParticipationDTCollection() = " +
                   thePublicHealthCaseVO.getTheParticipationDTCollection());

      // Populate the Entity collections with the results
      while (participationIterator.hasNext()) {
        participationDT = (ParticipationDT) participationIterator.next();
        nEntityID = participationDT.getSubjectEntityUid();
        strClassCd = participationDT.getSubjectClassCd();
        strTypeCd = participationDT.getTypeCd();
        recordStatusCd = participationDT.getRecordStatusCd();
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.PLACE) == 0 &&
            recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) && !lite) {
          thePlaceVOCollection.add(entityController.getPlace(nEntityID,
              nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.
                                           NONPERSONLIVINGSUBJECT) ==
            0 && recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theNonPersonLivingSubjectVOCollection.add(entityController.
              getNonPersonLivingSubject(nEntityID, nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.ORGANIZATION) == 0 &&
            recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theOrganizationVOCollection.add(entityController.getOrganization(
              nEntityID, nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.PERSON) == 0 &&
            recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          thePersonVOCollection.add(entityController.getPerson(nEntityID,
              nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.MATERIAL) == 0 &&
            recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theMaterialVOCollection.add(entityController.getMaterial(nEntityID,
              nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.ENTITYGROUP) == 0 &&
            recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theEntityGroupVOCollection.add(entityController.getEntityGroup(
              nEntityID, nbsSecurityObj));
          continue;
        }
        if (nEntityID == null || strClassCd == null || strClassCd.length() == 0) {
          continue;
        }
      }

      ActRelationshipDT actRelationshipDT = null;
      //Get the Vaccinations for a PublicHealthCase/Investigation
     Iterator<Object>  actRelationshipIterator = thePublicHealthCaseVO.
          getTheActRelationshipDTCollection().iterator();

      // Populate the ACT collections in the results
      while (actRelationshipIterator.hasNext()) {
        actRelationshipDT = (ActRelationshipDT) actRelationshipIterator.next();
        logger.debug("inside while actUid: " +
                     actRelationshipDT.getTargetActUid() + " observationUid: " +
                     actRelationshipDT.getSourceActUid());
        Long nSourceActID = actRelationshipDT.getSourceActUid();
        strClassCd = actRelationshipDT.getSourceClassCd();
        strTypeCd = actRelationshipDT.getTypeCd();
        recordStatusCd = actRelationshipDT.getRecordStatusCd();

        if (!lite && strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.
                                           INTERVENTION_CLASS_CODE) ==
            0
            && recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
            && strTypeCd != null && !strTypeCd.equals("1180")) {
          InterventionVO interventionVO = actController.getIntervention(
              nSourceActID, nbsSecurityObj);
          theInterventionVOCollection.add(interventionVO);
          InterventionDT intDT = interventionVO.getTheInterventionDT();

          if (intDT.getCd() != null &&
              intDT.getCd().compareToIgnoreCase("VACCINES/ANTISERA") == 0) {
            Collection<Object>  intPartDTs = interventionVO.
                getTheParticipationDTCollection();
           Iterator<Object>  intPartIter = intPartDTs.iterator();
            while (intPartIter.hasNext()) {
              ParticipationDT dt = (ParticipationDT) intPartIter.next();

              if (dt.getTypeCd() != null &&
                  dt.getTypeCd() ==
                  NEDSSConstants.VACCINATION_ADMINISTERED_TYPE_CODE) {
                VaccinationSummaryVO vaccinationSummaryVO = new
                    VaccinationSummaryVO();
                vaccinationSummaryVO.setActivityFromTime(intDT.
                    getActivityFromTime());
                vaccinationSummaryVO.setInterventionUid(intDT.
                    getInterventionUid());
                vaccinationSummaryVO.setLocalId(intDT.getLocalId());
                MaterialDT materialDT = entityController.getMaterialInfo(dt.
                    getSubjectEntityUid(), nbsSecurityObj);
                vaccinationSummaryVO.setVaccineAdministered(materialDT.getNm());
                //theVaccinationSummaryVOCollection.add(vaccinationSummaryVO);
              }
            }
          }
          continue;
        }

        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.
                                           CLINICAL_DOCUMENT_CLASS_CODE) ==
            0
            && recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theClinicalDocumentVOCollection.add(actController.getClinicalDocument(
              nSourceActID, nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.REFERRAL_CLASS_CODE) ==
            0
            && recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          theReferralVOCollection.add(actController.getReferral(nSourceActID,
              nbsSecurityObj));
          continue;
        }
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.
                                           PATIENT_ENCOUNTER_CLASS_CODE) ==
            0
            && recordStatusCd != null &&
            recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
          thePatientEncounterVOCollection.add(actController.getPatientEncounter(
              nSourceActID, nbsSecurityObj));
          continue;
        }
        /* if( strClassCd != null &&  strClassCd.compareToIgnoreCase( NEDSSConstants.NOTIFICATION_CLASS_CODE ) == 0
             && recordStatusCd != null && recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
         {
                 theNotificationVOCollection.add( actController.getNotification( nSourceActID, nbsSecurityObj ) );
                 continue;
         }
         */
        if (strClassCd != null &&
            strClassCd.compareToIgnoreCase(NEDSSConstants.
                                           OBSERVATION_CLASS_CODE) ==
            0 && strTypeCd != null &&
            strTypeCd.equals(NEDSSConstants.PHC_INV_FORM)) {
          ObservationVO parentObservationVO = actController.getObservation(
              nSourceActID, nbsSecurityObj);
          RetrieveSummaryVO retrieveObservationQA = new RetrieveSummaryVO();
          theObservationVOCollection  = (ArrayList<ObservationVO> ) retrieveObservationQA.
              retrieveObservationQuestions(nSourceActID, nbsSecurityObj);
          //  //##!! System.out.println("Size of VO in retrieveObservationQA : " +theObservationVOCollection.size() );
          theObservationVOCollection.add(parentObservationVO);
          continue;
        }
        if (nSourceActID == null || strClassCd == null) {
          logger.debug(
              "InvestigationProxyEJB.getInvestigation: check for nulls: SourceActUID" +
              nSourceActID + " classCd: " + strClassCd);
          continue;
        }
      }

      investigationProxyVO.setPublicHealthCaseVO(thePublicHealthCaseVO);
      investigationProxyVO.setThePlaceVOCollection(thePlaceVOCollection);
      investigationProxyVO.setTheNonPersonLivingSubjectVOCollection(
          theNonPersonLivingSubjectVOCollection);
      investigationProxyVO.setTheOrganizationVOCollection(
          theOrganizationVOCollection);
      investigationProxyVO.setThePersonVOCollection(thePersonVOCollection);
      investigationProxyVO.setTheMaterialVOCollection(theMaterialVOCollection);
      investigationProxyVO.setTheEntityGroupVOCollection(
          theEntityGroupVOCollection);
      investigationProxyVO.setTheInterventionVOCollection(
          theInterventionVOCollection);
      investigationProxyVO.setTheClinicalDocumentVOCollection(
          theClinicalDocumentVOCollection);
      investigationProxyVO.setTheReferralVOCollection(theReferralVOCollection);
      investigationProxyVO.setThePatientEncounterVOCollection(
          thePatientEncounterVOCollection);
      //investigationProxyVO.setTheNotificationVOCollection( theNotificationVOCollection  );
      investigationProxyVO.setTheObservationVOCollection(
          theObservationVOCollection);

      //for LDFs
      // ArrayList<Object> ldfList = new ArrayList<Object> ();
      try {
         //code for new ldf back end
    	  if(!lite) {
	        LDFHelper ldfHelper = LDFHelper.getInstance();
	        theStateDefinedFieldDTCollection  = (ArrayList<Object> ) ldfHelper.getLDFCollection(publicHealthCaseUID, investigationProxyVO.getBusinessObjNm(),nbsSecurityObj);
    	  }
}
      catch (Exception e) {
        logger.error("Exception occured while retrieving LDFCollection<Object>  = " +
                     e.toString());
      }

      if (theStateDefinedFieldDTCollection  != null) {
        logger.debug("Before setting LDFCollection<Object>  = " +
                     theStateDefinedFieldDTCollection.size());
        investigationProxyVO.setTheStateDefinedFieldDataDTCollection(
            theStateDefinedFieldDTCollection);
      }


      Collection<Object>  labSumVOCol = new ArrayList<Object> ();
      HashMap<Object,Object> labSumVOMap = new HashMap<Object,Object>();
      java.util.Date dtc = new java.util.Date();
      ////##!! System.out.println("the InvestigationProxyVO time before start getting associated reports is :" + (dtc.getTime()- dta.getTime()));

      if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
                                       NBSOperationLookup.VIEW,
                                       ProgramAreaJurisdictionUtil.
                                       ANY_PROGRAM_AREA,
                                       ProgramAreaJurisdictionUtil.
                                       ANY_JURISDICTION)) {
        String labReportViewClause = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW, "obs");
        labReportViewClause = labReportViewClause != null? " AND " + labReportViewClause:"";

        Collection<Object>  LabReportUidSummarVOs =new ObservationSummaryDAOImpl().findAllActiveLabReportUidListForManage(publicHealthCaseUID,labReportViewClause);
        String uidType = "LABORATORY_UID";
        Collection<Object>  newLabReportSummaryVOCollection  = new ArrayList<Object> ();
        Collection<?>  labReportSummaryVOCollection  = new ArrayList<Object> ();
        LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
       
        if(LabReportUidSummarVOs != null && LabReportUidSummarVOs.size() > 0)
        {
          //labSumVOCol = new ObservationProcessor().
             // retrieveLabReportSummary(LabReportUidSummarVOs, nbsSecurityObj);
        	labSumVOMap = new ObservationProcessor().retrieveLabReportSummaryRevisited(
        												LabReportUidSummarVOs,false, nbsSecurityObj, uidType);
        	if(labSumVOMap !=null)
        	{
        		if(labSumVOMap.containsKey("labEventList"))
        		{
        		  labReportSummaryVOCollection  = (ArrayList<?> )labSumVOMap.get("labEventList");
        		 Iterator<?>  iterator = labReportSummaryVOCollection.iterator();
          		  while( iterator.hasNext())
          		  {
          			 labReportSummaryVOs = (LabReportSummaryVO) iterator. next();
          			 labSumVOCol.add(labReportSummaryVOs);
          			 
          		  }
        		}
        	}

          logger.debug("Size of labreport Collection<Object>  :" + labSumVOCol.size());
        }
      }
      else {
        logger.debug(
            "user has no permission to view ObservationSummaryVO collection");

      }

      if (labSumVOCol != null) {
        investigationProxyVO.setTheLabReportSummaryVOCollection(labSumVOCol);

      }


      Collection<Object>  morbSumVOCol = new ArrayList<Object> ();
      HashMap<Object,Object> morbSumVoMap = new HashMap<Object,Object>();
      if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
                                       NBSOperationLookup.VIEW,
                                       ProgramAreaJurisdictionUtil.
                                       ANY_PROGRAM_AREA,
                                       ProgramAreaJurisdictionUtil.
                                       ANY_JURISDICTION)) {
        String morbReportViewClause = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW, "obs");
        morbReportViewClause = morbReportViewClause != null? " AND " + morbReportViewClause : "";
        Collection<Object>  morbReportUidSummarVOs =new ObservationSummaryDAOImpl().findAllActiveMorbReportUidListForManage(publicHealthCaseUID, morbReportViewClause);
        String uidType = "MORBIDITY_UID";
        Collection<Object>  newMobReportSummaryVOCollection  = new ArrayList<Object> ();
        Collection<?>  mobReportSummaryVOCollection  = new ArrayList<Object> ();
        MorbReportSummaryVO mobReportSummaryVOs = new MorbReportSummaryVO();
        
        if(morbReportUidSummarVOs != null && morbReportUidSummarVOs.size() > 0)
        {
          //morbSumVOCol = new ObservationProcessor().
             // retrieveMorbReportSummaryRevisited(morbReportUidSummarVOs, nbsSecurityObj, uidType);
        	morbSumVoMap = new ObservationProcessor().
             retrieveMorbReportSummaryRevisited(morbReportUidSummarVOs, false, nbsSecurityObj, uidType);
        	
          if(morbSumVoMap !=null)
      	{
      		if(morbSumVoMap.containsKey("MorbEventColl"))
      		{
      			mobReportSummaryVOCollection  = (ArrayList<?> )morbSumVoMap.get("MorbEventColl");
      		  Iterator<?>  iterator = mobReportSummaryVOCollection.iterator();
        		  while( iterator.hasNext())
        		  {
        			  mobReportSummaryVOs = (MorbReportSummaryVO) iterator. next();
        			   morbSumVOCol.add(mobReportSummaryVOs);
        			 
        		  }
      		}
      	}
          logger.debug("Size of Morbidity Collection<Object>  :" + morbSumVOCol.size());
        }
      }
      else {
        logger.debug(
            "user has no permission to view ObservationSummaryVO collection");
      }
      if (morbSumVOCol != null) {
        investigationProxyVO.setTheMorbReportSummaryVOCollection(morbSumVOCol);

      }

      if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
                                       NBSOperationLookup.VIEW)) {
        RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
        theVaccinationSummaryVOCollection  = new ArrayList<Object> (
            retrievePhcVaccinations.retrieveVaccinationSummaryVOForInv(
            publicHealthCaseUID, nbsSecurityObj).values());
        investigationProxyVO.setTheVaccinationSummaryVOCollection(
            theVaccinationSummaryVOCollection);
      }
      else {
        logger.debug(
            "user has no permission to view VaccinationSummaryVO collection");
      }
      if(!lite) {
      investigationProxyVO.setTheNotificationSummaryVOCollection(RetrieveSummaryVO.
          notificationSummaryOnInvestigation(thePublicHealthCaseVO, investigationProxyVO,
                                             nbsSecurityObj));
      
      if(investigationProxyVO.getTheNotificationSummaryVOCollection()!=null){
			Iterator<Object> it = investigationProxyVO.getTheNotificationSummaryVOCollection().iterator();
			while(it.hasNext()){
				NotificationSummaryVO notifVO = (NotificationSummaryVO)it.next();
				Iterator<Object> actIterator = investigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection().iterator();
				while(actIterator.hasNext()){
					ActRelationshipDT actRelationDT = (ActRelationshipDT)actIterator.next();
					if((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) ||
							notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC))
							&& notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
						actRelationDT.setShareInd(true);
					}
					if ((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) || 
							notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
						  notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
						actRelationDT.setExportInd(true);
					}
					if(notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTF) && notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
						actRelationDT.setNNDInd(true);
					}
				}
			}
		}
    }

      //Begin support for TreatmentSummary
      if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                        NBSOperationLookup.VIEW,
                                        ProgramAreaJurisdictionUtil.
                                        ANY_PROGRAM_AREA,
                                        ProgramAreaJurisdictionUtil.
                                        ANY_JURISDICTION)) {

         logger.debug("About to get TreatmentSummaryList for Investigation");
         RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
         theTreatmentSummaryVOCollection  = new ArrayList<Object> ((rsvo.
             retrieveTreatmentSummaryVOForInv(publicHealthCaseUID,
                                              nbsSecurityObj)).values());
         logger.debug("Number of treatments found: " +
                      theTreatmentSummaryVOCollection.size());
         investigationProxyVO.setTheTreatmentSummaryVOCollection(
             theTreatmentSummaryVOCollection);
       }
       else {
        logger.debug(
            "user has no permission to view TreatmentSummaryVO collection");
      }
      // end treatment support

      // document support starts here
		if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,
		              NBSOperationLookup.VIEW)) {
		RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
		theDocumentSummaryVOCollection  = new ArrayList<Object> (
		retrievePhcVaccinations.retrieveDocumentSummaryVOForInv(
		publicHealthCaseUID, nbsSecurityObj).values());
		investigationProxyVO.setTheDocumentSummaryVOCollection(theDocumentSummaryVOCollection);
		}
		else {
		logger.debug(
		"user has no permission to view DocumentSummaryVO collection");
		}
		if (!lite && nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	              NBSOperationLookup.VIEW)) {
			CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
			Collection<Object> contactCollection= cTContactSummaryDAO.getContactListForInvestigation(publicHealthCaseUID, nbsSecurityObj);
           
			investigationProxyVO.setTheCTContactSummaryDTCollection(contactCollection);
		}
		else {
		logger.debug(
		"user has no permission to view Contact Summary collection");
		}
		
		
    }
    catch (Exception e) {
      logger.fatal("InvestigationProxyEJB.getInvestigationProxy: " + e.getMessage(), e);
      throw new java.rmi.RemoteException(e.getMessage(), e);
    }
    java.util.Date dtb = new java.util.Date();
    
    return investigationProxyVO;
  }

  
  public void setAssociations(Long investigationUID,  Collection<Object>  reportSumVOCollection, Collection<Object>  vaccinationSummaryVOCollection, Collection<Object>  summaryDTColl, Collection<Object> treatmentSumColl, Boolean isNNDResendCheckReuired,
		  NBSSecurityObj nbsSecurityObj) throws
		  java.rmi.RemoteException, javax.ejb.EJBException,
		  javax.ejb.CreateException, NEDSSConcurrentDataException{
	  NNDMessageSenderHelper n1 = null;
	  ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();
		 try {
		  if(reportSumVOCollection!=null && !reportSumVOCollection.isEmpty() ){
			  manageAutoAssc.setObservationAssociationsImpl(investigationUID,
					  reportSumVOCollection, nbsSecurityObj);
		  }
		  if(vaccinationSummaryVOCollection!=null && !vaccinationSummaryVOCollection.isEmpty() ){	
			  manageAutoAssc.setVaccinationAssociationsImpl(investigationUID,
					  vaccinationSummaryVOCollection, nbsSecurityObj);
		  }
		  if(summaryDTColl!=null && !summaryDTColl.isEmpty()){
			  manageAutoAssc.setDocumentAssociationsImpl(investigationUID,
					  summaryDTColl, nbsSecurityObj);
		  }
		  if(treatmentSumColl!=null && !treatmentSumColl.isEmpty()){
			  manageAutoAssc.setTreatmentAssociationsImpl(investigationUID,NEDSSConstants.INVESTIGATION,
					  treatmentSumColl, nbsSecurityObj);
		  }
		  if(isNNDResendCheckReuired.booleanValue()){
			  InvestigationProxyVO invVO = getInvestigationProxy(investigationUID,
					  nbsSecurityObj);
			  n1 = NNDMessageSenderHelper.getInstance();
			  n1.updateAutoResendNotificationsAsync(invVO, nbsSecurityObj);
		  }
		  if(reportSumVOCollection!=null && reportSumVOCollection.size()>0){ 
		  		RetrieveSummaryVO.checkBeforeCreateAndStoreMessageLogDTCollection(investigationUID, reportSumVOCollection, nbsSecurityObj);
		  }
	  }catch (Exception e) {
		  NNDActivityLogDT nndActivityLogDT = new  NNDActivityLogDT();
		  String phcLocalId = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
		  nndActivityLogDT.setErrorMessageTxt(e.toString());
		  if (phcLocalId!=null)
			  nndActivityLogDT.setLocalId(phcLocalId);
		  else
			  nndActivityLogDT.setLocalId("N/A");
		  //catch & store auto resend notifications exceptions in NNDActivityLog table
		  n1.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
		  logger.error("setAssoications:Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
		  logger.fatal("InvestigationProxyEJB.setAssociations: " + e.getMessage(), e);
		  throw new EJBException(e.getMessage(), e);
	  }
  }
  
  

  /**
   *
   * @J2EE_METHOD  --  retrieveVaccinationSummaryListForManage
   */

  /**
   * Given an investigationUID and investigationUID, retrieve a summary list of all Vaccinations
       * (VaccinationSummaryVO collection) with the 'isAssociated' attribute populated
       * to indicate for each Vaccination if it is associated with the Investigation
   * identified by the investigationUID.
   * @param investigationUID
   * @param nbsSecurityObj
   * @return Collection<Object>  of VaccinationSummaryVO's
   */

  private  HashMap<Object,Object> retrieveVaccinationSummaryListForManage(Long personUID,
      Long investigationUID, NBSSecurityObj nbsSecurityObj) {
    try {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
		                                  NBSOperationLookup.EDIT)) {
		  logger.info("no edit permissions for setInvestigationProxy");
		 // throw new NEDSSSystemException("NO EDIT PERMISSIONS for setInvestigationProxy");
		}
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
		                                  NBSOperationLookup.VIEW)) {
		  logger.info("no VIEW permissions for INTERVENTIONVACCINATIONRECORD");
		  //throw new NEDSSSystemException("NO VIEW PERMISSIONS for INTERVENTIONVACCINATIONRECORD");
		}
		
		  if (personUID != null && investigationUID != null && nbsSecurityObj != null) {
		    RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
		    return (retrieveSummaryVO.retrieveVaccinationSummaryListForManage(
		        personUID, investigationUID, nbsSecurityObj));
		  }
		  return null;
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveVaccinationSummaryListForManage: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  // retrieveNotificationSummaryListForInvestigation
  public Collection<Object>  retrieveNotificationSummaryListForInvestigation(Long
      publicHealthUID, NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException {
    try {
		RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
		ArrayList<Object> theNotificationSummaryVOCollection  = (ArrayList<Object> )
		    retrieveSummaryVO.retrieveNotificationSummaryListForInvestigation(
		    publicHealthUID, nbsSecurityObj);
		return theNotificationSummaryVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveNotificationSummaryListForInvestigation: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  //retrieveNotificationSummaryList
  public Collection<Object>  retrieveNotificationSummaryListForManage(Long
      publicHealthCaseUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.
      RemoteException, NEDSSSystemException, javax.ejb.CreateException {
    try {
		ActController actController = null;
		PublicHealthCaseDT publicHealthCaseDT = null;
		NedssUtils nedssUtils = new NedssUtils();
		Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		logger.debug("ActController lookup = " + object.toString());
		ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(
		    object, ActControllerHome.class);
		logger.debug("Found ActControllerHome: " + acthome);
		actController = acthome.create();
		publicHealthCaseDT = actController.getPublicHealthCaseInfo(
		    publicHealthCaseUID, nbsSecurityObj);
		RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
		ArrayList<Object>  theNotificationSummaryVOCollection  = (ArrayList<Object> )
		    retrieveSummaryVO.retrieveNotificationSummaryListForManage(
		    publicHealthCaseDT, nbsSecurityObj);
		return theNotificationSummaryVOCollection;
	} catch (ClassCastException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveNotificationSummaryListForManage: ClassCastException: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	} catch (EJBException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveNotificationSummaryListForManage: EJBException: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  //Updates act relationship
  private ActRelationshipDT updateCds(ObservationSummaryVO observationSumVO,
                                      ActRelationshipDT actRelationshipDT) {
    logger.error("isAssociated: " + observationSumVO.getIsAssociated());
    if (!observationSumVO.getIsAssociated()) {
      actRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
      actRelationshipDT.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
    }
    else if (observationSumVO.getIsAssociated()) {
      actRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      actRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    }
    return actRelationshipDT;

  } //end of updateCds()

  private ActRelationshipDT updateCdsVaccination(VaccinationSummaryVO
                                                 vaccinationSummaryVO,
                                                 ActRelationshipDT
                                                 actRelationshipDT) {
    try {
		logger.debug("isAssociated updateCdsVaccination(): " +
		             vaccinationSummaryVO.getIsAssociated());
		if (!vaccinationSummaryVO.getIsAssociated()) {
		  logger.debug("inside !!!vaccinationSummaryVO.getIsAssociated: ");
		  actRelationshipDT.setRecordStatusCd("Inactive");
		  actRelationshipDT.setStatusCd("I");
		}
		else if (vaccinationSummaryVO.getIsAssociated()) {
		  logger.debug("inside vaccinationSummaryVO.getIsAssociated: ");
		  actRelationshipDT.setRecordStatusCd("Active");
		  actRelationshipDT.setStatusCd("A");
		}
		logger.debug("actRelationshipDT: " + actRelationshipDT);
		return actRelationshipDT;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.updateCdsVaccination: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}

  }

  /*Determines whether the association between the observation and the investigation exists,
    if exists, return the  association object*/
  private ActRelationshipDT getActRelationshipDT(Collection<Object> actRelColl,
                                                 Long invUID, Long obsUID,
                                                 String typeCd) {
    try {
		ActRelationshipDT returnValue = null;

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); ) {
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

		  if (actRelDT.getTargetActUid().equals(invUID) &&
		      actRelDT.getSourceActUid().equals(obsUID) &&
		      actRelDT.getTypeCd().equalsIgnoreCase(typeCd)) {
		    returnValue = actRelDT;
		    break;
		  }
		}
		return returnValue;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.getActRelationshipDT: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  } //end of getActRelationshipDT()

  //Determines if observation has any association with investigations
  private boolean observationAssociates(Collection<Object> actRelColl,
                                        Long obsUID,
                                        String arTypeCd,
                                        String sourceClassCd,
                                        String targetClassCd,
                                        String rdStatusCd) {
    try {
		boolean returnValue = false;

		if (actRelColl.isEmpty() || actRelColl == null) {
		  return returnValue;
		}

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); ) {
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();
		  logger.info("Source act uid " + actRelDT.getSourceActUid());
		  logger.info("Obs UID is " + obsUID.longValue());
		  logger.info("AR type cd" + actRelDT.getTypeCd() + "=" + arTypeCd);
		  logger.info("Source class cd " + actRelDT.getSourceClassCd() + "=" +
		              sourceClassCd);
		  logger.info("Target class cd " + actRelDT.getTargetClassCd() + "=" +
		              targetClassCd);
		  logger.info("Record status Cd " + actRelDT.getRecordStatusCd() + "=" +
		              rdStatusCd);

		  if (actRelDT.getSourceActUid().equals(obsUID) &&
		      actRelDT.getTypeCd().equalsIgnoreCase(arTypeCd) &&
		      actRelDT.getSourceClassCd().equalsIgnoreCase(sourceClassCd) &&
		      actRelDT.getTargetClassCd().equalsIgnoreCase(targetClassCd) &&
		      actRelDT.getRecordStatusCd().equalsIgnoreCase(rdStatusCd)) {
		    returnValue = true;
		    break;
		  }
		}
		return returnValue;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.observationAssociates: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  } //end of observationAssociates()

  public void transferOwnership(Long publicHealthCaseUID,
                                String newJurisdictionCode, Boolean isExportCase,
                                NBSSecurityObj nbsSecurityObj) throws javax.ejb.
      EJBException,
      NEDSSSystemException, NEDSSConcurrentDataException {
    try {
    	ActController actController = null;
    	ObservationProxy observationProxy = null;
      PublicHealthCaseDT publicHealthCaseDT = null;
      PublicHealthCaseDT newPublicHealthCaseDT = null;
      NedssUtils nedssUtils = new NedssUtils();
      PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());
      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(object, ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);
      actController = acthome.create();

      publicHealthCaseDT = actController.getPublicHealthCaseInfo(
          publicHealthCaseUID, nbsSecurityObj);

      if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                        NBSOperationLookup.TRANSFERPERMISSIONS,
                                        publicHealthCaseDT.getProgAreaCd(),
                                        publicHealthCaseDT.getJurisdictionCd(),
                                        publicHealthCaseDT.getSharedInd())) {
        logger.info("no add permissions for setInvestigationProxy");
        throw new NEDSSSystemException(
            "NO ADD PERMISSIONS for transferOwnership");
      }

      logger.info("user has add permissions for setInvestigationProxy");

      publicHealthCaseDT.setItDirty(true);
      publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);
      newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
          publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
          NEDSSConstants.INV_EDIT, DataTables.PUBLIC_HEALTH_CASE_TABLE,
          NEDSSConstants.BASE, nbsSecurityObj);


      actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,
                                            nbsSecurityObj);

      Collection<Object>  actRelationShips = actController.getActRelationships(publicHealthCaseUID,nbsSecurityObj);
      if(actRelationShips!=null){
    	 Iterator<Object>  it = actRelationShips.iterator();
    	  while(it.hasNext()){
    		  ActRelationshipDT actRelationshipDT = (ActRelationshipDT)it.next();
    		  if((actRelationshipDT.getTypeCd().equalsIgnoreCase("LabReport"))
    				  ||(actRelationshipDT.getTypeCd().equalsIgnoreCase("MorbReport"))){
    		      Object objObject = nedssUtils.lookupBean(JNDINames.OBSERVATION_PROXY_EJB);
    		      ObservationProxyHome observationProxyhome = (ObservationProxyHome) PortableRemoteObject.narrow(objObject, ObservationProxyHome.class);
    		      logger.debug("Found observationProxyHome: " + acthome);
    		      observationProxy = observationProxyhome.create();
    		      observationProxy.transferOwnership(actRelationshipDT.getSourceActUid(), null, newJurisdictionCode,NEDSSConstants.CASCADING, nbsSecurityObj);
    		  }
    		  else if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.DocToPHC)){
    			 Long docUid = actRelationshipDT.getSourceActUid();
    			  if(docUid != null){
    			      try{
    				      NbsDocument nbsDocument = null;
    				      Object docEJB = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
    				      logger.debug("DocumentEJB lookup = " + docEJB.toString());
    				      NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject.
    				          narrow(docEJB, NbsDocumentHome.class);
    				      logger.debug("Found NbsDocumentHome: " + dochome);
    				      nbsDocument = dochome.create(); 
    				      //get the 
    				      nbsDocument.transferOwnership(docUid, null, newJurisdictionCode, nbsSecurityObj);
    			      }catch(Exception e){
    			    	  logger.error("Error while updating the Document table" , e.getMessage(), e);
    			          e.printStackTrace();
    			          throw new javax.ejb.EJBException(e.getMessage());
    			      }
    		      }
    		  }
    		  else if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT106_TYP_CD)){
    			  
    			  NotificationDT notificationDT = actController.getNotificationInfo(
    					  actRelationshipDT.getSourceActUid(), nbsSecurityObj);
    			  String trigCd = NEDSSConstants.NOT_EDIT;   			  
    			  
    			  //notificationDT.setItNew(false);
    			
    			  RetrieveSummaryVO. updateNotification(actRelationshipDT.getSourceActUid(), trigCd,
      					  notificationDT.getCaseConditionCd(),notificationDT.getCaseClassCd(),
      					  notificationDT.getProgAreaCd(),newJurisdictionCode,
      					  notificationDT.getSharedInd(),nbsSecurityObj);
    			
    		  }
    	  }
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("ObservationProxyEJB.transferOwnership: Concurrent access is not allowed: " + ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
    }
    catch (Exception e) {
      logger.fatal("ObservationProxyEJB.transferOwnership: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
  }
  
  private Long transferOwnershipforExport(NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) throws javax.ejb.
          EJBException,NEDSSSystemException, NEDSSConcurrentDataException {
	  Long newNotficationUid = new Long(0);
	  try {
		  ActController actController = null;
		  ObservationProxy observationProxy = null;
		  PublicHealthCaseDT publicHealthCaseDT = null;
		  PublicHealthCaseDT newPublicHealthCaseDT = null;
		  NedssUtils nedssUtils = new NedssUtils();
		  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  logger.debug("ActController lookup = " + object.toString());
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		  narrow(object, ActControllerHome.class);
		  logger.debug("Found ActControllerHome: " + acthome);
		  actController = acthome.create();

		  publicHealthCaseDT = actController.getPublicHealthCaseInfo(
				  notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), nbsSecurityObj);

		  if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                  NBSOperationLookup.TRANSFERPERMISSIONS,
                  publicHealthCaseDT.getProgAreaCd(),
                  publicHealthCaseDT.getJurisdictionCd(),
                  publicHealthCaseDT.getSharedInd())) {
			  logger.info("no add permissions for setInvestigationProxy");
			  throw new NEDSSSystemException(
			  "NO ADD PERMISSIONS for transferOwnership");
		  }

		  logger.info("user has add permissions for setInvestigationProxy");

		  publicHealthCaseDT.setItDirty(true);
		  //Not updating the jurisdiction for Export
		  //publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);
		   newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
				  publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
				  NEDSSConstants.INV_EDIT, DataTables.PUBLIC_HEALTH_CASE_TABLE,
				  NEDSSConstants.BASE, nbsSecurityObj);

		  actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,
				  nbsSecurityObj);
		  Collection<Object>  actRelationShips = actController.getActRelationships(notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),nbsSecurityObj);
		  
		  NotificationVO notificationVO = notProxyVO.getTheNotificationVO();
		  if(actRelationShips!=null){
			 Iterator<Object>  it = actRelationShips.iterator();
			  while(it.hasNext()){
				  ActRelationshipDT actRelationshipDT = (ActRelationshipDT)it.next();
			      if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT106_TYP_CD)){

					  NotificationDT notificationDT = actController.getNotificationInfo(
							  actRelationshipDT.getSourceActUid(), nbsSecurityObj);
					  

					  // Call the method here 
					  if(notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) ||
							  notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)){
				    	   if(notificationDT.getRecordStatusCd().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE)){
				    		   String trigCd = NEDSSConstants.NOT_EDIT;  				    		   
				    		   notificationVO = updateNotificationforExport(actRelationshipDT.getSourceActUid(), trigCd,
										  notificationDT.getCaseConditionCd(),notificationDT.getCaseClassCd(),
										  notificationDT.getProgAreaCd(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getJurisdictionCd(),
										  notificationDT.getSharedInd(), notProxyVO.getTheNotificationVO().getTheNotificationDT().getExportReceivingFacilityUid(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getTxt(), nbsSecurityObj);
				    		   newNotficationUid = actController.setNotification(notificationVO,nbsSecurityObj);
				    		   		   
				    	   }
				    	  
				    	   
				       }	
					  
				  }
			  }
			  
		  }
	  }
	  catch (NEDSSConcurrentDataException ex) {
		  logger.fatal("InvestigationProxyEJB.transferOwnershipforExport: Concurrent access is not allowed: " + ex.getMessage(), ex);
	      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
	  }
	  catch (Exception e) {
	      logger.fatal("InvestigationProxyEJB.transferOwnershipforExport: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
	      throw new javax.ejb.EJBException(e.getMessage(), e);
	  }
	  return newNotficationUid;

}  
  
  //TODO This method needs to clean up as similar kind of method is existing in caseNotificationEJB.
  
private static  NotificationVO updateNotificationforExport(Long notificationUid,
	 		String businessTriggerCd,
	 		String phcCd,
	 		String phcClassCd,
	 		String progAreaCd,
	 		String jurisdictionCd,
	 		String sharedInd,
	 		Long recFacilithyUid,
	 		String comment,
	 		NBSSecurityObj nbsSecurityObj) {

	     NedssUtils nedssUtils = new NedssUtils();
	     Collection<Object>  notificationVOCollection  = null;
	     Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
	     try
	     {
	       ActControllerHome ecHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject,ActControllerHome.class);
	       ActController actController = ecHome.create();
	       NotificationVO notificationVO = actController.getNotification(notificationUid,nbsSecurityObj);
	       
	       PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
	       NotificationDT newNotificationDT = null;
	       NotificationDT notificationDT = notificationVO.getTheNotificationDT();
	       notificationDT.setProgAreaCd(progAreaCd);
	       notificationDT.setJurisdictionCd(jurisdictionCd);
	       notificationDT.setCaseConditionCd(phcCd);
	       notificationDT.setSharedInd(sharedInd);
	       notificationDT.setCaseClassCd(phcClassCd);
	       notificationDT.setExportReceivingFacilityUid(recFacilithyUid);
	       notificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
	       notificationDT.setTxt(comment);
	       notificationDT.setItDirty(true);
	       notificationVO.setItDirty(true);   
	       
			       //retreive the new NotificationDT generated by PrepareVOUtils
			       newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
			       notificationDT, NBSBOLookup.NOTIFICATION, businessTriggerCd,
			       DataTables.NOTIFICATION_TABLE, NEDSSConstants.BASE, nbsSecurityObj); 
			       newNotificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
		
			       //replace old NotificationDT in NotificationVO with the new NotificationDT
			         notificationVO.setTheNotificationDT(newNotificationDT);
			         return notificationVO;
	     }
	        catch (Exception e)
	        {
	        	logger.fatal("InvestigationProxyEJB.updateNotificationforExport: " + e.getMessage(), e);
	        	throw new EJBException(e.getMessage(), e);
	        }
	 }

  /**
   * @roseuid 3CE3EA4B0157
   * @J2EE_METHOD  --  setInvestigationProxyWithAutoAssoc
       * To be used later on for creating investigations from create observation page
   */
  public Long setInvestigationProxyWithAutoAssoc(InvestigationProxyVO
                                                 investigationProxyVO,
                                                 Long observationUid,
                                                 String observationTypeCd,
                                                 NBSSecurityObj nBSSecurityObj) throws
      javax.ejb.EJBException, javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException, NEDSSConcurrentDataException

  {
    try {
		Long investigationUID = setInvestigationProxy(investigationProxyVO,
		                                              nBSSecurityObj);

		Collection<Object>  observationColl = new ArrayList<Object> ();
		if (observationTypeCd.equalsIgnoreCase(NEDSSConstants.LAB_DISPALY_FORM)){
		  LabReportSummaryVO labSumVO = new LabReportSummaryVO();
		  labSumVO.setItTouched(true);
		  labSumVO.setItAssociated(true);
		  labSumVO.setObservationUid(observationUid);
		  observationColl.add(labSumVO);


		}
		else{
		  MorbReportSummaryVO morbSumVO = new MorbReportSummaryVO();
		  morbSumVO.setItTouched(true);
		  morbSumVO.setItAssociated(true);
		  morbSumVO.setObservationUid(observationUid);
		  observationColl.add(morbSumVO);

		}

		setAssociations(investigationUID, observationColl,null, null,null,new Boolean(false),
		                           nBSSecurityObj);
		return investigationUID;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.setInvestigationProxyWithAutoAssoc: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  public static void main(String[] args) {
    /* InvestigationProxyEJB ipejb = new InvestigationProxyEJB();
      Long personUid = new Long(1);
      NBSSecurityObj nbsSecurityObj = null;
     try
     {
     logger.debug("RetrieveVaccination  " + ipejb.retrieveObservationSummaryList(personUid,nbsSecurityObj));
      }
      catch(Exception e)
         {}*/
  }

  /**
    private Map<Object,Object> ObsCollection(Long ObservationUID) throws NEDSSSystemException
        {
          String SELECT_OBSERVATION = "SELECT     Act_relationship.target_act_uid targetActUID, Act_relationship.source_act_uid sourceActUID, Observation_1.observation_uid observationUID, "+
                        "Act_relationship.type_cd typeCd, dbo.Obs_value_txt.observation_uid obsValueObsUID, dbo.Obs_value_coded.code code, dbo.Obs_value_date.from_time dateFromTime, "+
                        "Obs_value_date.to_time dateToTime, Obs_value_date.duration_amt durationAmt, Obs_value_date.duration_unit_cd durationUnitCd, Obs_value_numeric.numeric_value_1 numericValue1, "+
                        "Obs_value_numeric.numeric_value_2 obsValueNumeric2, Obs_value_txt.value_txt valueTxt, Obs_value_date.observation_uid obsValueDateUID, "+
                        "Obs_value_numeric.observation_uid obsValNumericUID, Obs_value_coded.observation_uid obsValueCodedUID "+
          "FROM      Observation Observation_1 LEFT OUTER JOIN "+
                        "Act_relationship ON Observation_1.observation_uid = Act_relationship.source_act_uid LEFT OUTER JOIN "+
                        "Obs_value_txt ON Observation_1.observation_uid = Obs_value_txt.observation_uid LEFT OUTER JOIN "+
                        "Obs_value_coded ON Observation_1.observation_uid = Obs_value_coded.observation_uid LEFT OUTER JOIN "+
                        "Obs_value_date ON Observation_1.observation_uid = Obs_value_date.observation_uid LEFT OUTER JOIN "+
                        "Obs_value_numeric ON Observation_1.observation_uid = Obs_value_numeric.observation_uid "+
          "WHERE     (Act_relationship.type_cd = 'InvFrmQ') AND Act_relationship.target_act_uid = ?)";
          Connection dbConnection = null;
          PreparedStatement preparedStmt = null;
          ResultSet resultSet = null;
          ResultSetMetaData resultSetMetaData = null;
          ResultSetUtils resultSetUtils = new ResultSetUtils();
           ObservationVO obsVO = new ObservationVO();
           HashMap<Object,Object> ObservationVOs = null;
          try
          {
              dbConnection = getConnection();
              preparedStmt = dbConnection.prepareStatement(SELECT_OBSERVATION);
              preparedStmt.setLong(1, ObservationUID.longValue());
              resultSet = preparedStmt.executeQuery();
              logger.debug("get resultSet " + resultSet.toString());
              resultSetMetaData = resultSet.getMetaData();
              String getPK = "getObservationUid";
              HashMap<Object,Object> retval = new HashMap<Object,Object>();
              ObservationVOs = (HashMap)resultSetUtils.mapRsToBeanMap(resultSet, resultSetMetaData, obsVO.getClass(), getPK, retval);
            }
            catch(SQLException se)
            {
              logger.fatal("Error: SQLException while getting vaccination for a person in workup", se);
              throw new NEDSSSystemException( se.getMessage());
            }
            catch(Exception ex)
            {
       logger.fatal("Error while getting vaccination for a person in workup", ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeResultSet(resultSet);
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
       // }
            return ObservationVOs;
      }//end of getObservationSummaryVOCollectionForWorkup()
   */

  /**
   * @roseuid 3D1B937800C8
   * @J2EE_METHOD  --  setSummaryReportProxy
   */
  public Long setSummaryReportProxy(SummaryReportProxyVO summaryReportProxyVO,
                                    NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {
    Long publicHealthCaseUID = null;
    try {

      Long falseUid = null;
      Long realUid = null;
      PrepareVOUtils preVOUtils = new PrepareVOUtils();
      RootDTInterface rootDT = null;
      ObservationVO observationVO = null;
      //Obtains a ActController reference
      NedssUtils nedssUtils = new NedssUtils();
      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      logger.info("ActController lookup = " + object.toString());
      ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object, ActControllerHome.class);
      ActController act = actHome.create();

      if (!summaryReportProxyVO.isItNew() && !summaryReportProxyVO.isItDirty()) {
        logger.error(
            "SummaryReportProxyVO is not new, neither dirty, hence exiting without changes!");
        return summaryReportProxyVO.getThePublicHealthCaseVO().
            getThePublicHealthCaseDT().getPublicHealthCaseUid();
      }
      else {
        //Find user gave right permission to edit
        if (nbsSecurityObj != null && nbsSecurityObj.getPermission(
            NBSBOLookup.SUMMARYREPORT,
            NEDSSConstants.SUMMARY_REPORT_ADD,
            summaryReportProxyVO.getThePublicHealthCaseVO().
            getThePublicHealthCaseDT().getProgAreaCd(),
            ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
          if (summaryReportProxyVO.getThePublicHealthCaseVO().
              getThePublicHealthCaseDT().isItNew()) {
            rootDT = preVOUtils.prepareVO(summaryReportProxyVO.
                                          getThePublicHealthCaseVO().
                                          getThePublicHealthCaseDT(),
                                          NBSBOLookup.SUMMARYREPORT,
                                          NEDSSConstants.INV_SUMMARY_CR,
                                          NEDSSConstants.TABLE_NAME,
                                          NEDSSConstants.BASE,
                                          nbsSecurityObj);
            logger.debug(" else if loop rootDT initail");
          }
          else if (summaryReportProxyVO.getThePublicHealthCaseVO().
                   getThePublicHealthCaseDT().isItDirty()) {
            rootDT = preVOUtils.prepareVO(summaryReportProxyVO.
                                          getThePublicHealthCaseVO().
                                          getThePublicHealthCaseDT(),
                                          NBSBOLookup.SUMMARYREPORT,
                                          NEDSSConstants.INV_SUMMARY_EDIT,
                                          NEDSSConstants.TABLE_NAME,
                                          NEDSSConstants.BASE,
                                          nbsSecurityObj);
            logger.debug(" if loop rootDT initail");
          }

          Long falseHealthCaseUID = summaryReportProxyVO.
              getThePublicHealthCaseVO().getThePublicHealthCaseDT().
              getPublicHealthCaseUid();
          logger.debug("falseHealthCaseUID " + falseHealthCaseUID);
          PublicHealthCaseDT publicHealthCaseDT = (PublicHealthCaseDT) rootDT;
          summaryReportProxyVO.getThePublicHealthCaseVO().
              setThePublicHealthCaseDT(publicHealthCaseDT);
          publicHealthCaseUID = act.setPublicHealthCase(summaryReportProxyVO.
              getThePublicHealthCaseVO(), nbsSecurityObj);
          logger.info("New publicHealthCaseUID created " + publicHealthCaseUID);
          PublicHealthCaseVO phcVO = summaryReportProxyVO.
              getThePublicHealthCaseVO();
          if (phcVO.isItNew()) {
            if (falseHealthCaseUID.intValue() < 0) {
              setFalseToNew(summaryReportProxyVO, falseHealthCaseUID,
                            publicHealthCaseUID);
            }
          } // End of if(phcVO.isItNew())

          if (summaryReportProxyVO.isItNew() || summaryReportProxyVO.isItDirty()) {
            if (summaryReportProxyVO.getTheObservationVOCollection() != null) {
             Iterator<Object>  anIterator = null;
              for (anIterator = summaryReportProxyVO.
                   getTheObservationVOCollection().iterator();
                   anIterator.hasNext(); ) {
                observationVO = (ObservationVO) anIterator.next();
                if (observationVO.isItNew() || observationVO.isItDirty()) {
                  falseUid = observationVO.getTheObservationDT().
                      getObservationUid();
                  logger.debug("the  falseObservationUid is" + falseUid);
                  realUid = act.setObservation(observationVO, nbsSecurityObj);
                  logger.debug("the  realObservationUid is" + realUid);
                  if (falseUid.intValue() < 0) {
                    setFalseToNew(summaryReportProxyVO, falseUid, realUid);
                  }
                }
              } // End of for loop
            } // if(summaryReportProxyVO.getTheObservationVOCollection()!= null)
          } // End of if(summaryReportProxyVO.isItNew() || summaryReportProxyVO.isItDirty())

          if (summaryReportProxyVO.getTheActRelationshipDTCollection() != null) {
            Collection<Object>  newActRelationshipDT = new ArrayList<Object> ();
           Iterator<Object>  anIterator = summaryReportProxyVO.
                getTheActRelationshipDTCollection().iterator();
            ActRelationshipDAOImpl actRelationshipDAOImpl = new
                ActRelationshipDAOImpl();
            actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
                getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            for (anIterator = summaryReportProxyVO.
                 getTheActRelationshipDTCollection().iterator();
                 anIterator.hasNext(); ) {
              ActRelationshipDT actRelationshipDT = (ActRelationshipDT)
                  anIterator.next();
              AssocDTInterface aDTInterface = preVOUtils.prepareAssocDT(
                  actRelationshipDT, nbsSecurityObj);
              logger.debug("the actRelationshipDT statusTime is " +
                           actRelationshipDT.getStatusTime());
              logger.debug("the actRelationshipDT statusCode is " +
                           actRelationshipDT.getStatusCd());
              logger.debug("Got into The ActRelationship loop");
              try {
                actRelationshipDAOImpl.store( (ActRelationshipDT) aDTInterface);
                logger.debug("Got into The ActRelationship, The ActUid is " +
                             actRelationshipDT.getTargetActUid());
                newActRelationshipDT.add(actRelationshipDT);
              }
              catch (Exception e) {
                logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage());
              }
            } // end of FOR loop
            summaryReportProxyVO.setTheActRelationshipDTCollection(
                newActRelationshipDT);
          } // End of if(summaryReportProxyVO.getTheActRelationshipDTCollection() != null)
        } // end of permission check
        else {
          logger.fatal(nbsSecurityObj.getEntryID(),
              "User does not have the rights to edit this investigation");
          throw new EJBException(
              "User does not have the rights to edit this investigation");
        }
      }
    }
    catch (NEDSSConcurrentDataException e) {
      logger.fatal("InvastigationProxyEJB.setSummaryReportProxy: Concurrent access is not allowed: " + e.getMessage(), e);
      throw new NEDSSConcurrentDataException(e.getMessage(), e);
    }
    catch (Exception ex) {
      logger.fatal("InvastigationProxyEJB.setSummaryReportProxy: " + ex.getMessage(), ex);
      throw new NEDSSSystemException(ex.getMessage(), ex);
    }

    return publicHealthCaseUID;
  } //End setSummaryReportProxyVO(SummaryReportProxyVO, NBSSecurity)

  private void setFalseToNew(SummaryReportProxyVO summaryReportProxyVO,
                             Long falseUid, Long actualUid) {
   try {
	Iterator<Object>  anIterator = null;
	    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
	    ActRelationshipDT actRelationshipDT = null;
	    Collection<Object>  actRelationShipColl = (ArrayList<Object> ) summaryReportProxyVO.
	        getTheActRelationshipDTCollection();
	    if (actRelationShipColl != null) {
	      for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext(); ) {
	        actRelationshipDT = (ActRelationshipDT) anIterator.next();
	        if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0) {
	          actRelationshipDT.setTargetActUid(actualUid);
	        }
	        if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0) {
	          actRelationshipDT.setSourceActUid(actualUid);
	        }
	        logger.debug("\n\n\nfalseUid in ActRelationShip " + falseUid.toString());
	        logger.debug("\n\n\nACTUid in ActRelationShip " +
	                     actRelationshipDT.getTargetActUid());
	        logger.debug("\n\n\nSourceUID in ActRelationShip " +
	                     actRelationshipDT.getSourceActUid());
	        logger.debug("\n\n\n(actRelationshipDT.getActUid() == falseUid)1  " +
	                     actRelationshipDT.getTargetActUid().equals(falseUid.
	            toString()));
	        logger.debug("\n\n\n(actRelationshipDT.getActUid() equals falseUid)1  " +
	                     actRelationshipDT.getTargetActUid().equals(falseUid.
	            toString()));
	        logger.debug(
	            "\n\n\n(actRelationshipDT.getActUid() compared to falseUid)1  " +
	            actRelationshipDT.getSourceActUid().compareTo(falseUid));
	      }
	
	      logger.debug("actRelationshipDT.getActUid(actualUid)2" +
	                   actRelationshipDT.getTargetActUid());
	      logger.debug("actRelationshipDT.getActUid(SourceActUid)2" +
	                   actRelationshipDT.getSourceActUid());
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("InvestigationProxyEJB.setFalseToNew: " + e.getMessage(), e);
	throw new EJBException(e.getMessage(), e);

}
  }

  /**
   * @roseuid 3D1B937801F4
   * @J2EE_METHOD  --  getSummaryReportsForMMWR
   */

  public Collection<Object>  getSummaryReportsForMMWR(String county,
                                             String mmwrWeek,
                                             String mmwrYear,
                                             NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException,
      NEDSSConcurrentDataException {
    try {
		Collection<Object>  summaryReportVOColl = new ArrayList<Object> ();
		logger.debug("\n\nThe security for Summary Report view: " +
		             nbsSecurityObj.
		             getPermission(NBSBOLookup.SUMMARYREPORT,
		                           NEDSSConstants.SUMMARY_REPORT_VIEW));
		if (nbsSecurityObj != null && nbsSecurityObj.getPermission(
		    NBSBOLookup.SUMMARYREPORT,
		    NEDSSConstants.SUMMARY_REPORT_VIEW)) {
		  String case_type_cd = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.
		      SUMMARYREPORT,
		      NEDSSConstants.SUMMARY_REPORT_VIEW);
		  if (case_type_cd == null) {
		    case_type_cd = "";
		  }
		  logger.debug("case_type_cd \n " + case_type_cd);

		  SummaryDataDAOImpl summaryDAO = new SummaryDataDAOImpl();
		  Collection<Object>  collection = summaryDAO.retrieveSummaryReportList(county,
		      mmwrWeek, mmwrYear, case_type_cd, nbsSecurityObj);
		  if (collection == null) {
		    logger.debug(" The collection from summary DAO is null");
		  }
		  else {
		    logger.debug("The size of the collection from summary DAO is " +
		                 collection.size());
		  }
		  logger.debug("The collection from summary DAO is " + collection);
		  logger.debug("\n\n summaryDAO.retriveSummaryReportList collection " +
		               collection.size());
		 Iterator<Object>  phcUIDList = collection.iterator();
		  PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
		  while (phcUIDList.hasNext()) {
		    phcDT = (PublicHealthCaseDT) phcUIDList.next();
		    logger.debug("\n\n public health case uid id",
		                 phcDT.getPublicHealthCaseUid());
		    SummaryReportProxyVO summaryReportProxyVO = getSummaryReportProxy(phcDT.
		        getPublicHealthCaseUid(),
		        nbsSecurityObj);
		    summaryReportVOColl.add(summaryReportProxyVO);
		  }
		} // Find permission
		else {
		  logger.fatal(nbsSecurityObj.getEntryID(),
		               "User does not have the rights to edit this investigation");
		  throw new EJBException(
		      "User does not have the rights to edit this investigation");
		}
		return summaryReportVOColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.getSummaryReportsForMMWR: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  } // End of getSummaryReportsForMMWR  method

  public String  getConditionCd(String conditionCd, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException,
  javax.ejb.CreateException,
  java.rmi.RemoteException,
  NEDSSSystemException 
  {
	  try {
		SummaryDataDAOImpl summaryDAO = new SummaryDataDAOImpl();
		  String progAreaCd = summaryDAO.getProgAreaCd(conditionCd);
		  return progAreaCd;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.getConditionCd: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }
  /**
   * @roseuid 3D1B93790015
   * @J2EE_METHOD  --  getSummaryReportProxy
   */
  public SummaryReportProxyVO getSummaryReportProxy(Long publicHealthCaseUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException {
    try {
		NedssUtils nedssUtils = new NedssUtils();
		Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		logger.info("ActController lookup = " + object.toString());
		ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(
		    object, ActControllerHome.class);
		ActController act = actHome.create();
		SummaryReportProxyVO summaryReportProxyVO = new SummaryReportProxyVO();
		NotificationVO notificationVO = new NotificationVO();
		ObservationVO observationVO = new ObservationVO();
		Collection<Object>  observationColl = new ArrayList<Object> ();
		Collection<Object>  notificationColl = new ArrayList<Object> ();
		if (nbsSecurityObj != null && nbsSecurityObj.getPermission(
		    NBSBOLookup.SUMMARYREPORT,
		    NEDSSConstants.SUMMARY_REPORT_VIEW)) {
		  PublicHealthCaseVO phcVO = act.getPublicHealthCase(publicHealthCaseUid,
		      nbsSecurityObj);
		  summaryReportProxyVO.setThePublicHealthCaseVO(phcVO);
		  logger.debug(
		      "\n\n Size of phcVO.getTheActRelationshipDTCollection().size() " +
		      phcVO.getTheActRelationshipDTCollection().size());
		 Iterator<Object>  actRelationIterator = phcVO.getTheActRelationshipDTCollection().
		      iterator();
		  Long observationUID = null;
		  Long notificationUID = null;
		  Long actSourceUID = null;
		  while (actRelationIterator.hasNext()) {
		    ActRelationshipDT actDT = (ActRelationshipDT) actRelationIterator.next();
		    if (actDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.SUMMARY_FORM) &&
		        actDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.
		        SUMMARY_OBS) &&
		        actDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.
		        CLASS_CD_CASE) &&
		        actDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.ACTIVE)) {
		      observationUID = actDT.getSourceActUid();

		      observationVO = act.getObservation(observationUID, nbsSecurityObj);
		      observationColl.add(observationVO);
		      logger.debug(
		          "\n\n Size of observationVO.getTheActRelationshipDTCollection().size() " +
		          observationVO.getTheActRelationshipDTCollection().size());
		    }
		    else if (actDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.
		        SUMMARY_NOTIFICATION) &&
		             actDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.
		        CLASS_CD_NOTIFICATION) &&
		             actDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.
		        CLASS_CD_CASE) &&
		             actDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.
		        ACTIVE)) {
		      notificationUID = actDT.getSourceActUid();

		      notificationVO = act.getNotification(notificationUID, nbsSecurityObj);
		      notificationColl.add(notificationVO);

		    }

		   Iterator<Object>  actCollection  = observationVO.
		        getTheActRelationshipDTCollection().iterator();
		    while (actCollection.hasNext()) {
		      ActRelationshipDT observationActDT = (ActRelationshipDT)
		          actCollection.next();
		      if (observationActDT.getTypeCd().trim().equalsIgnoreCase(
		          NEDSSConstants.SUMMARY_FORM_Q.trim()) &&
		          observationActDT.getSourceClassCd().trim().equalsIgnoreCase(
		          NEDSSConstants.SUMMARY_OBS.trim()) &&
		          observationActDT.getTargetClassCd().trim().equalsIgnoreCase(
		          NEDSSConstants.SUMMARY_OBS.trim()) &&
		          observationActDT.getRecordStatusCd().trim().equalsIgnoreCase(
		          NEDSSConstants.ACTIVE.trim())) {
		        logger.debug("\n child observation: " +
		                     observationActDT.getSourceActUid() + " typeCd: " +
		                     observationActDT.getTypeCd());
		        observationUID = observationActDT.getSourceActUid();
		        observationVO = act.getObservation(observationUID, nbsSecurityObj);
		        observationColl.add(observationVO);
		      }
		    }
		  } // End While Loop
		  summaryReportProxyVO.setTheObservationVOCollection(observationColl);
		  summaryReportProxyVO.setTheNotificationVOCollection(notificationColl);

		}
		else {
		  logger.fatal(nbsSecurityObj.getEntryID(),
		               "User does not have the rights to edit this investigation");
		  throw new EJBException(
		      "User does not have the rights to edit this investigation");
		}
		return summaryReportProxyVO;
	} catch (ClassCastException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.getSummaryReportProxy: ClassCastException: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  } //End of SummaryReportProxyVO method

  private void insertActRelationshipHistory(ActRelationshipDT dt) throws
      NEDSSSystemException {
    try {
		if (dt != null) {
		  ActRelationshipHistoryManager mn = new ActRelationshipHistoryManager(dt.
		      getTargetActUid().longValue(), dt.getSourceActUid().longValue(),
		      dt.getTypeCd());
		  mn.store(dt);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.insertActRelationshipHistory: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  private void insertParticipationHistory(ParticipationDT dt) throws
      NEDSSSystemException {
    try {
		if (dt != null) {
		  ParticipationHistoryManager man = new ParticipationHistoryManager(dt.
		      getSubjectEntityUid().longValue(), dt.getActUid().longValue(),
		      dt.getTypeCd());
		  man.store(dt);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.insertParticipationHistory: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  private void insertRoleHistory(RoleDT dt) throws NEDSSSystemException {
    try {
		if (dt != null) {
		  RoleHistDAOImpl dao = new RoleHistDAOImpl(dt.getSubjectEntityUid().
		                                            longValue(), dt.getCd(),
		                                            dt.getRoleSeq().shortValue());
		  dao.store(dt);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.insertRoleHistory: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  
  public String publicHealthCaseLocalID(Long publicHealthCaseUid,
                                        NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      java.rmi.RemoteException, NEDSSSystemException

  {
    try {
		PublicHealthCaseDAOImpl publicHealthCaseDAOImpl = new
		    PublicHealthCaseDAOImpl();
		PublicHealthCaseDT pdt = (PublicHealthCaseDT) publicHealthCaseDAOImpl.
		    loadObject(publicHealthCaseUid.longValue());
		String publicHealthCaseLocalId = pdt.getLocalId().toString();
		return publicHealthCaseLocalId;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.publicHealthCaseLocalID: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  /**
   * @roseuid 3D78D6E3030D
   * @J2EE_METHOD  --  investigationAssociatedWithMorbidity
   */
  public boolean investigationAssociatedWithMorbidity(Long morbidityUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
      java.rmi.RemoteException, NEDSSSystemException {
    try {
		String typeCd = "MorbReport";
		boolean investigationAssociated = false;
		if (morbidityUid != null) {
		  ActRelationshipDAOImpl actRelationshipDAOImpl = new
		      ActRelationshipDAOImpl();
		  Collection<Object>  col = actRelationshipDAOImpl.loadSource(morbidityUid.longValue(),
		      typeCd);
		 Iterator<Object>  it = col.iterator();
		  while (it.hasNext()) {
		    ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
		    if (actRelationshipDT != null &&
		        actRelationshipDT.getRecordStatusCd().
		        equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

		      investigationAssociated = true;
		    }
		  }
		}
		return investigationAssociated;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.investigationAssociatedWithMorbidity: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  public ManageSummaryVO getManageSummaryVO( Long investigationUID,Long mprUID,
	      NBSSecurityObj nbsSecurityObj) throws java.
	      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
	      NEDSSSystemException, NEDSSConcurrentDataException{
		  ManageSummaryVO manageSummaryVO = new ManageSummaryVO();
		  try {
			PublicHealthCaseVO thePublicHealthCaseVO = null;
			  //PHCVO
			  Object theLookedUpObject;
			  NedssUtils nedssUtils = new NedssUtils();
			  theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			  ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
	          narrow(theLookedUpObject, ActControllerHome.class);
			  ActController actController = actHome.create();

			  //  Get and set the Pubic Health Case
			  thePublicHealthCaseVO = actController.getPublicHealthCase(
	    		  investigationUID, nbsSecurityObj);
			  manageSummaryVO.setPublicHealthCaseVO(thePublicHealthCaseVO);
			  Collection<Object>  partColl =thePublicHealthCaseVO.getTheParticipationDTCollection();
			 Iterator<Object>  it=partColl.iterator();
			  Long patientUID=null;
			  while(it.hasNext()){
				  ParticipationDT partDT = (ParticipationDT)it.next();
				  if(partDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.PHC_PATIENT))
					  patientUID =partDT.getSubjectEntityUid();
			  }
			//docs
			  NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
			  Collection<Object>  docSumColl =nbsDocDAO.getDocSummaryVOColl(mprUID,nbsSecurityObj);
			  if(docSumColl == null) docSumColl = new ArrayList<Object> ();
			  
			  manageSummaryVO.setDocumentCollection(docSumColl);
			  //Treatment
			  manageSummaryVO.setTreatmentSummaryVOCollection( retrieveTreatmentSummaryListForManage( mprUID,
			   investigationUID, docSumColl, nbsSecurityObj));
			 //Vaccination
			  Collection<Object>  vacSummaryColl = new ArrayList<Object> ( retrieveVaccinationSummaryListForManage( mprUID,
				       investigationUID, nbsSecurityObj).values());
			  if(vacSummaryColl == null) vacSummaryColl = new ArrayList<Object> ();
			  
			  manageSummaryVO.setVaccinationSummaryVOCollection(vacSummaryColl);
			  //lab
			  manageSummaryVO.setLabSummaryCollection( retrieveLabReportSummaryListForManage( investigationUID,mprUID,
					  docSumColl, nbsSecurityObj));
			  //Morb
			  manageSummaryVO.setMobReportSummaryVOCollection(retrieveMorbReportSummaryListForManage( investigationUID,mprUID,
					   docSumColl ,nbsSecurityObj));
			  
			  //PersonVO
			  Object theEntityObject;
				  logger.debug("* before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
			      // Reference an Act controller to use later
				  theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				  theEntityObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
			      
			      EntityControllerHome home = (EntityControllerHome)
	              PortableRemoteObject.narrow(theEntityObject, EntityControllerHome.class);
	            logger.debug("Found EntityControllerHome: " + home);
	            EntityController entityController = home.create();
	            PersonVO personVO = (PersonVO) entityController.getPerson(patientUID,
	                nbsSecurityObj);
	            manageSummaryVO.setPatientVO(personVO);
			      
		} catch (Exception e) {
			 logger.fatal("InvestigationProxyEJB.getManageSummaryVO: " + e.getMessage(), e);
			 throw new EJBException(e.getMessage(), e);

		}
		  
		  return manageSummaryVO;
	  }
  /**
   * @J2EE_METHOD  --  retrieveTreatmentSummaryListForManage
   */

  /**
   * Given an investigationUID and investigationUID, retrieve a summary list of all Treatments
   * (TreatmentSummaryVO collection) with the 'isAssociated' attribute populated
   * to indicate for each Treatment if it is associated with the Investigation
   * identified by the investigationUID.
   * @param investigationUID
   * @param nbsSecurityObj
   * @return Collection<Object>  of TreatmentSummaryVO's
   */

  private  ArrayList<Object> retrieveTreatmentSummaryListForManage(Long personUID,
      Long investigationUID, Collection<Object>  docSumColl, NBSSecurityObj nbsSecurityObj) {
	  try {
    if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                                      NBSOperationLookup.EDIT)) {
      logger.info("no edit permissions for setInvestigationProxy");
     //throw new NEDSSSystemException("NO EDIT PERMISSIONS for setInvestigationProxy");
    }
    if (!nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
                                      NBSOperationLookup.VIEW)) {
      logger.info("no VIEW permissions for TREATMENT");
     // throw new NEDSSSystemException("NO VIEW PERMISSIONS for TREATMENT");
    }
    
      if (personUID != null && investigationUID != null && nbsSecurityObj != null) {
        logger.debug(
            "Now enetering the retrieveTreatmentSummaryForManage method");
        RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
        return (retrieveSummaryVO.retrieveTreatmentSummaryListForManage(
            personUID,investigationUID, docSumColl, nbsSecurityObj));
      }
      return null;
    }
    catch (Exception  naex) {
      logger.fatal("InvestigationProxyEJB.retrieveTreatmentSummaryListForManage: " + naex.getMessage(), naex);
      throw new EJBException(naex.getMessage(), naex);

    }

  } // end method retrieveTreatmentSummaryListForManage


  //*****************************************************************************************/
  //BEGIN: retrieveMorbReportSummaryListForManage(Long investigationUID, Long subjectUID,
  //                                              NBSSecurityObj nbsSecurityObj)
  //*****************************************************************************************/

  private Collection<Object>  retrieveMorbReportSummaryListForManage(Long investigationUID, Long subjectUID, Collection<Object>  docSumColl ,
       NBSSecurityObj nbsSecurityObj)
  {
    try {
		logger.debug(
		    "in method retrieveMorbReportSummaryListForManage: personUID: " +
		    subjectUID + " investigationUID: " + investigationUID);

		//Security check
		//checkPermissionToViewMorbReport(nbsSecurityObj);

		//Find all morb report uids
		String MORBREPORT_WHERECLAUSE = nbsSecurityObj.getDataAccessWhereClause(NEDSSConstants.OBSERVATIONMORBIDITYREPORT, NEDSSConstants.VIEW, "obs");
		MORBREPORT_WHERECLAUSE = MORBREPORT_WHERECLAUSE != null ? " AND " + MORBREPORT_WHERECLAUSE : "";
		ObservationSummaryDAOImpl dao = new ObservationSummaryDAOImpl();
		Collection<Object>  allMorbReportUidList = dao.findAllMorbReportUidListForManage(subjectUID, MORBREPORT_WHERECLAUSE);
		 allMorbReportUidList = this.filterAssociatedMorb(allMorbReportUidList, investigationUID, MORBREPORT_WHERECLAUSE);
		//Find all active source and target uids
		Collection<Object>  associaMorbReportUidList = dao.findAllActiveMorbReportUidListForManage(investigationUID, MORBREPORT_WHERECLAUSE);
		String uidType = "MORBIDITY_UID";
		HashMap<Object,Object> morbReportSummaryMap = new HashMap<Object,Object>();
		Collection<Object>  morbReportSummaryList = new ArrayList<Object> ();
		Collection<Object>  newMobReportSummaryVOCollection  = new ArrayList<Object> ();
		Collection<?>  mobReportSummaryVOCollection  = new ArrayList<Object> ();
		MorbReportSummaryVO mobReportSummaryVOs = new MorbReportSummaryVO();
		//Retrieves morb report summary for the allMorbReportUidList
		ObservationProcessor helper = new ObservationProcessor();
		//Collection<Object>  morbReportSummaryList = helper.retrieveMorbReportSummaryRevisited(allMorbReportUidList, nbsSecurityObj, uidType);
		morbReportSummaryMap = helper.retrieveMorbReportSummaryRevisited(allMorbReportUidList, false, nbsSecurityObj, uidType);
		
		if(morbReportSummaryMap !=null)
		{
			if(morbReportSummaryMap.containsKey("MorbEventColl"))
			{
			  mobReportSummaryVOCollection  = (ArrayList<?> )morbReportSummaryMap.get("MorbEventColl");
			 Iterator<?>  iterator = mobReportSummaryVOCollection.iterator();
			  while( iterator.hasNext())
			  {
				mobReportSummaryVOs = (MorbReportSummaryVO) iterator. next();
				morbReportSummaryList.add(mobReportSummaryVOs);
				 
			  }
			}
		}
		//Set association flag
		this.setAssociationFlag(morbReportSummaryList, associaMorbReportUidList);
		//Morbs from documents
		CDAEventSummaryParser esp = new CDAEventSummaryParser();
		morbReportSummaryList.addAll(esp.getMorbMapByLocalId(docSumColl, investigationUID, nbsSecurityObj).values());

		return morbReportSummaryList;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveMorbReportSummaryListForManage: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  private void checkPermissionToViewMorbReport(NBSSecurityObj securityObj)
   {
     try {
		//Aborts the operation if not getting the permission to morbreport
		 if (!securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
		                                NBSOperationLookup.VIEW))
		   throw new SecurityException(
		       "Expected the user to have view morb report permissions.");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.checkPermissionToViewMorbReport: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
   }

   private List<?>[] filterForSourceActUidLists(Long investigationUID, Collection<Object>  activeMorbReportUidList)
   {
     try {
		List<Object> equalMorbReportUidList = null;
		 List<Object> notEqualMorbReportUidList = null;

		 for(int i = 0, j = activeMorbReportUidList.size(); i < j; j++)
		 {
		   Long[] morbReportUid = (Long[])((ArrayList<Object> )activeMorbReportUidList).get(i);
		   if(morbReportUid == null) continue;

		   Long sourceActUid =  morbReportUid[0];
		   Long targetActUid = morbReportUid[1];
		   if(sourceActUid == null || targetActUid == null) continue;

		   if(sourceActUid.compareTo(investigationUID) == 0)
		   {
		     if(equalMorbReportUidList == null) equalMorbReportUidList = new ArrayList<Object> ();
		     equalMorbReportUidList.add(sourceActUid);
		   }
		   else
		   {
		     if (notEqualMorbReportUidList == null) notEqualMorbReportUidList = new ArrayList<Object> ();
		     notEqualMorbReportUidList.add(sourceActUid);
		    }
		  }

		  List<?>[] returnObsUids = {equalMorbReportUidList, notEqualMorbReportUidList};

		  return returnObsUids;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.filterForSourceActUidLists: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
   }

   private boolean filterAllMorbReportUidList(Collection<Object> allMorbReportUidList, Collection<Object>  equalSourceActUidList)
   {
     try {
		return allMorbReportUidList.removeAll(equalSourceActUidList);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.filterAllMorbReportUidList: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
   }

	private void setAssociationFlag(Collection<Object> reportSummaryList,
			Collection<Object> equalSourceActUidList) {
		try {
			if (reportSummaryList == null || equalSourceActUidList == null)
				return;
			for (Iterator<Object> it = reportSummaryList.iterator(); it.hasNext();) {
				ReportSummaryInterface reportSummary = (ReportSummaryInterface) it
						.next();
				if (reportSummary == null)
					continue;
				Iterator<Object> itor = equalSourceActUidList.iterator();
				while (itor.hasNext()) {
					UidSummaryVO uidSummaryVO = (UidSummaryVO) itor.next();

					if (reportSummary.getObservationUid().equals(
							uidSummaryVO.getUid())) {
						reportSummary
								.setActivityFromTime(uidSummaryVO.getAddTime());
						reportSummary.setItAssociated(true);
						if (reportSummary instanceof LabReportSummaryVO)
							((LabReportSummaryVO) reportSummary)
									.setProcessingDecisionCd(uidSummaryVO
											.getAddReasonCd());
						else if (reportSummary instanceof MorbReportSummaryVO)
							((MorbReportSummaryVO) reportSummary)
									.setProcessingDecisionCd(uidSummaryVO
											.getAddReasonCd());
						break;
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InvestigationProxyEJB.setAssociationFlag: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
	}
    //#######################################################################################/
    //END: retrieveMorbReportSummaryListForManage(Long investigationUID, Long subjectUID,
    //                                              NBSSecurityObj nbsSecurityObj)
    //#######################################################################################/


  //*****************************************************************************************/
  //BEGIN: retrieveLabReportSummaryListForManage(Long investigationUID, Long subjectUID,
  //                                              NBSSecurityObj nbsSecurityObj)
  //*****************************************************************************************/

  private  Collection<Object>  retrieveLabReportSummaryListForManage(Long investigationUID, Long subjectUID, Collection<Object>  docSumColl ,
       NBSSecurityObj nbsSecurityObj)
  {
    try {
		logger.debug(
		    "in method retrieveLabReportSummaryListForManage: personUID: " +
		    subjectUID + " investigationUID: " + investigationUID);

		//Security check
		//checkPermissionToViewLabReport(nbsSecurityObj);

		//Find all lab report uids
		String LABREPORT_WHERECLAUSE = nbsSecurityObj.getDataAccessWhereClause(NEDSSConstants.OBSERVATIONLABREPORT, NEDSSConstants.VIEW, "obs");
		LABREPORT_WHERECLAUSE = LABREPORT_WHERECLAUSE != null ? " AND " + LABREPORT_WHERECLAUSE : "";
		ObservationSummaryDAOImpl dao = new ObservationSummaryDAOImpl();
		Collection<Object>  allLabReportUidList = dao.findAllLabReportUidListForManage(subjectUID, LABREPORT_WHERECLAUSE);
		//Find all active source and target uids
		Collection<Object>  associaLabReportUidList = dao.findAllActiveLabReportUidListForManage(investigationUID, LABREPORT_WHERECLAUSE);
		
		String uidType = "LABORATORY_UID";
		Collection<Object>  newLabReportSummaryVOCollection  = new ArrayList<Object> ();
		Collection<?>  labReportSummaryVOCollection  = new ArrayList<Object> ();
		LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
		Collection<Object>  labColl = new ArrayList<Object> ();
		HashMap<Object,Object> labSumVOMap = new HashMap<Object,Object>();
		Collection<Object>  labReportSummaryList = new ArrayList<Object> ();
		//Retrieves lab report summary for the allLabReportUidList
		ObservationProcessor helper = new ObservationProcessor();
		//Collection<Object>  labReportSummaryList = helper.retrieveLabReportSummary(allLabReportUidList, nbsSecurityObj);
		labSumVOMap = helper.retrieveLabReportSummaryRevisited(allLabReportUidList, false, nbsSecurityObj, uidType);
		if(labSumVOMap !=null)
		{
			if(labSumVOMap.containsKey("labEventList"))
			{
			  labReportSummaryVOCollection  = (ArrayList<?> )labSumVOMap.get("labEventList");
			 Iterator<?>  iterator = labReportSummaryVOCollection.iterator();
			  while( iterator.hasNext())
			  {
				 labReportSummaryVOs = (LabReportSummaryVO) iterator. next();
				 labReportSummaryList.add(labReportSummaryVOs);
				 
			  }
			}
		}
		
		//Set association flag
		this.setAssociationFlag(labReportSummaryList, associaLabReportUidList);
  //Labs from documents
		CDAEventSummaryParser esp = new CDAEventSummaryParser();
		labReportSummaryList.addAll(esp.getLabMapByLocalId(docSumColl, investigationUID, nbsSecurityObj).values());

		return labReportSummaryList;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.retrieveLabReportSummaryListForManage: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
  }

  private void checkPermissionToViewLabReport(NBSSecurityObj securityObj)
   {
     try {
		//Aborts the operation if not getting the permission to morbreport
		 if (!securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
		                                NBSOperationLookup.VIEW))
		   throw new SecurityException(
		       "Expected the user to have view lab report permissions.");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.checkPermissionToViewLabReport: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
   }



   private Collection<Object>  filterAssociatedMorb(Collection<Object> allMorbColl, Long investigationUid, String whereClause)
   {
     try {
		Collection<Object>  filterColl = new ArrayList<Object> ();
		 ObservationSummaryDAOImpl dao = new ObservationSummaryDAOImpl();
		 Collection<Object>  assoMorb =null;
		 UidSummaryVO uidSummaryVO = null;
		Iterator<Object>  itor = allMorbColl.iterator();
		 while(itor.hasNext())
		 {
		   uidSummaryVO = (UidSummaryVO)itor.next();
		   assoMorb = dao.assocMorbReportUidListForManage(uidSummaryVO.getUid(), investigationUid, whereClause);
		   if(assoMorb == null || assoMorb.size() == 0)
		   filterColl.add(uidSummaryVO);
		 }

		 return filterColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.filterAssociatedMorb: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);

	}
   }

   public Map<Object,Object> deleteInvestigationProxy(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj) throws
           java.rmi.RemoteException,
           javax.ejb.EJBException,
           javax.ejb.CreateException,
           NEDSSSystemException,
           javax.ejb.FinderException,
           NEDSSConcurrentDataException
   {
      try {
		int labCount=0;
		  int morbCount=0;
		  int vaccineCount=0;
		  int documentCount = 0;
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
		  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

		  NedssUtils nedssUtils = new NedssUtils();
		  ActController actController = null;
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  logger.debug("ActController lookup = " + object.toString());
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		      narrow(object, ActControllerHome.class);
		  logger.debug("Found ActControllerHome: " + acthome);
		  actController = acthome.create();


		  InvestigationProxyVO investigationProxyVO = getInvestigationProxy(
				  publicHealthCaseUID,nbsSecurityObj);
		  PublicHealthCaseDT phcDT = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
		  phcDT.setItDelete(false);
		  phcDT.setItDirty(true);


		  if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
		                                   NBSOperationLookup.DELETE,
		                                   phcDT.getProgAreaCd(),
		                                   phcDT.getJurisdictionCd()))
		  {
			  //Loop through Act Relationships for this Investigation and count # of labs, morbs, vac
			  //If we have any count of these, we return the counts and fail the delete
			  ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			  investigationProxyVO.setTheActRelationshipDTCollection(actRelationshipDAOImpl.load(publicHealthCaseUID.longValue()));
			 Iterator<Object>  anIterator = null;
			  for (anIterator = investigationProxyVO.getTheActRelationshipDTCollection().iterator(); anIterator.hasNext(); ) {
				 ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
				 if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.LAB_REPORT))
					labCount++;
				 if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.MORBIDITY_REPORT))
					morbCount++;
				 if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.AR_TYPE_CODE))
					vaccineCount++;
				 if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.DocToPHC))
					documentCount++; //added as part of ND-8812
			  }
			  
			  returnMap.put(NEDSSConstants.LAB_REPORT, new Integer(labCount));
			  returnMap.put(NEDSSConstants.MORBIDITY_REPORT, new Integer(morbCount));
			  //'1180' AR_TYPE_CODE is for Vaccines
			  returnMap.put(NEDSSConstants.AR_TYPE_CODE, new Integer(vaccineCount));
			  returnMap.put(NEDSSConstants.DocToPHC, new Integer(documentCount));

			  if (labCount > 0 || morbCount > 0 || vaccineCount > 0 || documentCount > 0) {
				   return returnMap;
			  }

			  //Logically delete associated treatments:
			  if (nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
			                                   NBSOperationLookup.VIEW,
			                                   ProgramAreaJurisdictionUtil.
			                                     ANY_PROGRAM_AREA,
			                                   ProgramAreaJurisdictionUtil.
			                                     ANY_JURISDICTION)) {
		         logger.debug("About to get TreatmentSummaryList for Investigation Delete");
		         ArrayList<Object> theTreatmentSummaryVOCollection  = new ArrayList<Object> ((rsvo.
		             retrieveTreatmentSummaryVOForInv(publicHealthCaseUID,
		                                              nbsSecurityObj)).values());
		         logger.debug("Number of treatments found: " +
		                      theTreatmentSummaryVOCollection.size());
		        Iterator<Object>  anTreatmentIterator = null;
			     for (anTreatmentIterator = theTreatmentSummaryVOCollection.iterator(); anTreatmentIterator.hasNext(); ) {
				      TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO) anTreatmentIterator.next();
			    	
				      TreatmentDT treatmentDT = actController.getTreatmentInfo(
				    		                                    treatmentSummaryVO.getTreatmentUid(),
				                                                nbsSecurityObj);
				      treatmentDT.setItDelete(false);
				      treatmentDT.setItDirty(true);
		  			    	
				      String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
				      String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_DEL;
				      String TABLE_NAME = DataTables.TREATMENT_TABLE;
				      String MODULE_CD = NEDSSConstants.BASE;

				      TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils.prepareVO(
				          treatmentDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
				          TABLE_NAME, MODULE_CD, nbsSecurityObj);
			    	
			          actController.setTreatmentInfo(newTreatmentDT, nbsSecurityObj);
			     }
		      }
		      else {
		         logger.debug("user has no permission to view TreatmentSummaryVO collection");
				 returnMap.put(NEDSSConstants.SECURITY_FAIL, NEDSSConstants.SECURITY_FAIL);
		      }
			  NotificationSummaryVO notificationSummaryVO=null;
			  if(investigationProxyVO.getTheNotificationSummaryVOCollection()!=null && investigationProxyVO.getTheNotificationSummaryVOCollection().size() > 0) {
				  Collection<Object>   theNotificationSummaryVOCollection=investigationProxyVO.getTheNotificationSummaryVOCollection();
				 Iterator<Object>  iter = theNotificationSummaryVOCollection.iterator();
				  while(iter.hasNext()){
					  notificationSummaryVO = (NotificationSummaryVO)iter.next();
					  if(notificationSummaryVO.getIsHistory().equalsIgnoreCase("F")){
						  NotificationProxy notificationProxy=null;
						  Object objNotification = nedssUtils.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
						  logger.debug("NotificationProxyEJB lookup = " + objNotification.toString());
						  NotificationProxyHome notificationProxyHome =(NotificationProxyHome)PortableRemoteObject.narrow(objNotification, NotificationProxyHome.class);
						  logger.debug("Found NotificationProxyHome: " + notificationProxyHome);
						  notificationProxy = notificationProxyHome.create();

						  NotificationProxyVO notificationProxyVO = notificationProxy.getNotificationProxy(
								  notificationSummaryVO.getNotificationUid(),nbsSecurityObj);
						  NotificationDT notificationDT = notificationProxyVO.getTheNotificationVO().getTheNotificationDT();

						  //if	auto resend is "T", set to PEND_DEL and resend the notification
						  //if auto resend is off, logically delete the notification
						  if ( notificationDT.getAutoResendInd().equalsIgnoreCase("T") ){
							  try {
								  notificationDT.setItDelete(false);
								  notificationDT.setItDirty(true);

								  String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
								  String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL_NOTF;
								  String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
								  String MODULE_CD = NEDSSConstants.BASE;

								  NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
										  notificationDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
										  TABLE_NAME, MODULE_CD, nbsSecurityObj);

								  actController.setNotificationInfo(newNotificationDT, nbsSecurityObj);
								  //nndMSHelper.updateAutoResendNotificationsAsync(investigationProxyVO, nbsSecurityObj);*/
							  }
							  catch(Exception e) {
								  NNDMessageSenderHelper nndMSHelper = null;
								  nndMSHelper = NNDMessageSenderHelper.getInstance();
								  NNDActivityLogDT nndActivityLogDT = new  NNDActivityLogDT();
								  String phcLocalId = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
								  nndActivityLogDT.setErrorMessageTxt(e.toString());
								  if (phcLocalId!=null)
									  nndActivityLogDT.setLocalId(phcLocalId);
								  else
									  nndActivityLogDT.setLocalId("N/A");

								  //catch & store auto resend notifications exceptions in NNDActivityLog table
								  nndMSHelper.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
								  logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
								  e.printStackTrace();
							  }

						  }
						  else
						  {
							  notificationDT.setItDelete(false);
							  notificationDT.setItDirty(true);

							  String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
							  String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL;
							  String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
							  String MODULE_CD = NEDSSConstants.BASE;

							  NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
									  notificationDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
									  TABLE_NAME, MODULE_CD, nbsSecurityObj);

							  actController.setNotificationInfo(newNotificationDT, nbsSecurityObj);
						  }
					  }
				  }
			  }
		      //Logically delete the Investigation
			  RootDTInterface rootDTInterface = phcDT;
			  String businessObjLookupName = NBSBOLookup.INVESTIGATION;
			  String businessTriggerCd = "INV_DEL";
		      String tableName = "PUBLIC_HEALTH_CASE";
		      String moduleCd = "BASE";
		      phcDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
		           rootDTInterface, businessObjLookupName, businessTriggerCd,
		           tableName, moduleCd, nbsSecurityObj);

		      phcDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_NOT_A_CASE);
		      phcDT.setInvestigationStatusCd(NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED);
		 
		      //Persist the Investigation
		      actController.setPublicHealthCaseInfo(phcDT, nbsSecurityObj);
		      
			  // Logically delete the associated Contacts
		      CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
		      Collection<Object> contactCollection = cTContactSummaryDAO.getContactListForInvestigation(publicHealthCaseUID, nbsSecurityObj);
		      try {
		    	  if (contactCollection != null && contactCollection.size() > 0) {
		    		  Object lookedUpObj = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
		    		  logger.debug("!!!!!!!!!!PamProxy lookup = " + object.toString());
		    		  PamProxyHome ppHome = (PamProxyHome)PortableRemoteObject.narrow(lookedUpObj,PamProxyHome.class);
		    		  logger.debug("!!!!!!!!!!Found PamProxyHome: " + ppHome);
		    		  PamProxy pamProxy = ppHome.create();

		    		  Iterator<Object> itContact = contactCollection.iterator();
		    		  while (itContact.hasNext()) {
		    			  CTContactSummaryDT ctContactSummaryDT = (CTContactSummaryDT)(itContact.next());
		    			  CTContactProxyVO ctContactProxyVO = pamProxy.getContactProxyVO(ctContactSummaryDT.getCtContactUid(), nbsSecurityObj);
		    			  if(ctContactProxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid().compareTo(publicHealthCaseUID)==0)
		                  	ctContactProxyVO.setItDelete(true);
		                  else{
		                  	ctContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(null);
		                  	ctContactProxyVO.setItDirty(true);
		                  	ctContactProxyVO.getcTContactVO().setItDirty(true);
		                  	ctContactProxyVO.getcTContactVO().getcTContactDT().setItDirty(true);
		                  }
		    			  pamProxy.setContactProxyVO(ctContactProxyVO, nbsSecurityObj);
		    		  }
		    	  }
		      } catch(Exception e) {
		    	  throw new NEDSSSystemException(e.getMessage());
		      }
		      

		     //Logically delete the associated Patient revision
		     Long personUid = this.getPersonUidOfInvestigation(publicHealthCaseUID);

		     Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
		     logger.debug("!!!!!!!!!!EntityController lookup = " + object.toString());
		     EntityControllerHome ecHome = (EntityControllerHome)PortableRemoteObject.narrow(lookedUpObj,EntityControllerHome.class);
		     logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
		     EntityController entityController = ecHome.create();

		     //retrieve PersonVO that represents the Patient Revision for this Vaccination by passing in personUid
		     PersonVO personVO = entityController.getPatientRevision(personUid,nbsSecurityObj);
		     personVO.setItDirty(true);
		     personVO.getThePersonDT().setItDirty(true);
		     businessTriggerCd = NEDSSConstants.PAT_DEL;
		     personUid = entityController.setPatientRevision(personVO, businessTriggerCd, nbsSecurityObj);
		     logger.debug("PersonUid value returned by InvestigationProxy.deleteInvestigationProxy(): " + personUid);
		       
			 return returnMap;
		 }
		 else
		 {
			 returnMap.put(NEDSSConstants.SECURITY_FAIL, NEDSSConstants.SECURITY_FAIL);
			 return returnMap;
		 }
	} catch (ClassCastException e) {
		// TODO Auto-generated catch block
		logger.fatal("InvestigationProxyEJB.deleteInvestigationProxy: ClassCastException: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);

	}
   }
   

   /**
    * This method retrieves the PersonUid of the Patient Revision for the current Invesigation by
    * executing the following query. This private method is called by deleteInvestigationProxy.  This method will
    * return null if no PersonUid is found.
    * @param investigationUid Long
    * @param nbsSecurityObj NBSSecurityObj
    * @return personUid Long
    */
   private Long getPersonUidOfInvestigation(Long investigationUid){

     Long personUid = null;
     Connection dbConnection = null;
     PreparedStatement preparedStmt = null;
     ResultSet resultSet = null;

     try
       {

         String aQuery = WumSqlQuery.SELECT_PATIENT_FOR_INVESTIGATION_DELETION;
         logger.info("Query = " + aQuery);
         dbConnection = getConnection();
         preparedStmt = dbConnection.prepareStatement(aQuery);
         preparedStmt.setLong(1, investigationUid.longValue());
         preparedStmt.setString(2,NEDSSConstants.PHC_PATIENT);
         preparedStmt.setString(3,NEDSSConstants.PERSON_CLASS_CODE);
         preparedStmt.setString(4,NEDSSConstants.ACTIVE);
         preparedStmt.setString(5,NEDSSConstants.CLASS_CD_CASE);
         resultSet = preparedStmt.executeQuery();

         if(resultSet != null && resultSet.next()){
            personUid = new Long(resultSet.getLong(1));
            logger.debug("personUid: " + personUid);
          }


       }
       catch(Exception e)
       {
         logger.debug("This record cannot be deleted");
         logger.fatal("InvestigationProxyEJB.getPersonUidOfInvestigation: Exception: " + e.getMessage(), e);
         throw new EJBException(e.getMessage(), e);
       }
      finally
      {
         try
         {
                 resultSet.close();
                 preparedStmt.close();
                 dbConnection.close();
         }
         catch(SQLException sqlex)
         {
               logger.fatal("InvestigationProxyEJB.getPersonUidOfInvestigation: SQLException: " + sqlex.getMessage(), sqlex);
               throw new EJBException(sqlex.getMessage(), sqlex);
         }
      }
   
      return personUid;

   }
   
   public HashMap<Object,Object> getPHCConditionAndProgArea(Long publicHealthCaseUid,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			java.rmi.RemoteException, NEDSSSystemException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {
				logger
						.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			RetrieveSummaryVO retrievePhcInfo = new RetrieveSummaryVO();
			return (HashMap<Object,Object>) retrievePhcInfo
					.getPHCConditionAndProgArea(publicHealthCaseUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InvestigationProxyEJB.getPHCConditionAndProgArea: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}
   public Long exportOwnership( NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException,
   NEDSSConcurrentDataException, RemoteException{
	    try {
			Long sourceActUid = transferOwnershipforExport(notProxyVO,newJurisdictionCode,nbsSecurityObj);
			if(sourceActUid.compareTo(new Long(0))==0){
			   	 ActController actController = null;
				 NedssUtils nedssUtils = new NedssUtils();
				 Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				 logger.debug("ActController lookup = " + object.toString());
				 ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
				 narrow(object, ActControllerHome.class);
				 logger.debug("Found ActControllerHome: " + acthome);
				 
					   try{
							actController = acthome.create();					
						    sourceActUid= actController.setNotification(notProxyVO.getTheNotificationVO(), nbsSecurityObj);
						  }catch(Exception e){
					            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
					            e.printStackTrace();
					            throw new javax.ejb.EJBException(e.getMessage());
					     }
			
			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			  if (notProxyVO.getTheActRelationshipDTCollection() != null) {
			        actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
			            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
			       Iterator<Object>  anIterator = notProxyVO. getTheActRelationshipDTCollection().iterator();
			       while(anIterator.hasNext()){
			          ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
			
			          try {
			        	  actRelationshipDT.setSourceActUid(sourceActUid);
			            actRelationshipDAOImpl.store(actRelationshipDT);
			            logger.debug("Got into The ActRelationship, The ActUid is " +
			                         actRelationshipDT.getTargetActUid());
			          }
			          catch (Exception e) {
			            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
			            e.printStackTrace();
			            throw new javax.ejb.EJBException(e.getMessage());
			          }
			        }
			      }
			}//if it is an update notification 
			  return notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.fatal("InvestigationProxyEJB.exportOwnership: ClassCastException: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	  }
} // InvestigationProxyEJB
