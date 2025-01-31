//
// -- Java Code Generation Process --
package gov.cdc.nedss.proxy.ejb.observationproxyejb.bean;

// Import Statements
import gov.cdc.nedss.act.clinicaldocument.vo.ClinicalDocumentVO;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.ejb.dao.EDXDocumentDAOImpl;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseRootDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.alert.ejb.alertejb.Alert;
import gov.cdc.nedss.alert.ejb.alertejb.AlertHome;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipHistoryManager;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistoryManager;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.elr.ejb.msginprocessor.dao.ElrActivityLogDAOImpl;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.elr.helper.ELRActivityLogSearchDT;
import gov.cdc.nedss.entity.entitygroup.vo.EntityGroupVO;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.NonPersonLivingSubjectVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao.NBSAttachmentNoteDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dao.AnswerRootDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxy;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.dao.GeneralDAOImpl;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.GenericObservationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.proxy.util.LabResultProxyManager;
import gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionService;
import gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionServiceHome;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ObservationUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

public class ObservationProxyEJB
    extends BMPBase
    implements javax.ejb.SessionBean
{
	private static final long serialVersionUID = 1L;
  LogUtils logger = new LogUtils( (ObservationProxyEJB.class).getName());

  private final int PROG_AREA_CD = 0;
  private final int JURISDICTION_CD = 1;
  private final String SETMORB_RETURN_OBS_UID = "SETMORB_RETURN_OBS_UID";
  private final int OBS_UID = 0;
  private final int MPR_UID = 1;
  private final int OBS_LOCAL_UID = 2;
  private final int MPR_LOCAL_UID = 3;

  private final int RETRIEVED_PERSONS_FOR_PROXY = 0;
  private final int RETRIEVED_ORGANIZATIONS_FOR_PROXY = 1;
  private final int RETRIEVED_MATERIALS_FOR_PROXY = 2;

  private final int RETRIEVED_INTERVENTIONS_FOR_PROXY = 0;
  private final int RETRIEVED_OBSERVATIONS_FOR_PROXY = 1;
  private final int RETRIEVED_LABS_FOR_RT = 2;
  private SessionContext cntx = null;
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  /**
   * @roseuid 3C9221D802AF
       /**
    * @J2EE_METHOD  --  ObservationProxyEJB
    */
   public ObservationProxyEJB()
   {
   }

  /**
   * @roseuid 3C9221D802DE
       /**
    * @J2EE_METHOD  --  ejbRemove
    * A container invokes this method before it ends the life of the session object. This
    * happens as a result of a client's invoking a remove operation, or when a container
    * decides to terminate the session object after a timeout. This method is called with
    * no transaction context.
    */
   public void ejbRemove()
   {
   }

  /**
   * @roseuid 3C9221D802FD
       /**
    * @J2EE_METHOD  --  ejbActivate
    * The activate method is called when the instance is activated from its 'passive' state.
    * The instance should acquire any resource that it has released earlier in the ejbPassivate()
    * method. This method is called with no transaction context.
    */
   public void ejbActivate()
   {
   }

  /**
   * @roseuid 3C9221D8031C
       /**
    * @J2EE_METHOD  --  ejbPassivate
    * The passivate method is called before the instance enters the 'passive' state. The
    * instance should release any resources that it can re-acquire later in the ejbActivate()
    * method. After the passivate method completes, the instance must be in a state that
    * allows the container to use the Java Serialization protocol to externalize and store
        * away the instance's state. This method is called with no transaction context.
    */
   public void ejbPassivate()
   {
   }

  /**
   * @roseuid 3C9221D8033C
       /**
    * @J2EE_METHOD  --  setSessionContext
    * Set the associated session context. The container calls this method after the instance
    * creation. The enterprise Bean instance should store the reference to the context
    * object in an instance variable. This method is called with no transaction context.
    */
   public void setSessionContext(javax.ejb.SessionContext sessioncontext) throws
       javax.ejb.EJBException,
       java.rmi.RemoteException
   {
     this.cntx = sessioncontext;
   }

  /**
   * @roseuid 3C9221D900FA
       /**
    * @J2EE_METHOD  --  ejbCreate
    * Called by the container to create a session bean instance. Its parameters typically
    * contain the information the client uses to customize the bean instance for its use.
    * It requires a matching pair in the bean class and its home interface.
    */
   public void ejbCreate()
   {

     // getEJBRefObjects();
   }

  //*****************************************************************************************/
  //BEGIN: setLabResultProxy(LabResultProxyVO labResultProxyVO, NBSSecurityObj securityObj)
  //*****************************************************************************************/

  /**
   * @roseuid 3C9221D90128
   /**
    * @J2EE_METHOD  --  setLabResultProxy
    */
   public Map<Object, Object> setLabResultProxy(LabResultProxyVO labResultProxyVO,
                                NBSSecurityObj securityObj) throws java.rmi.
       RemoteException, javax.ejb.CreateException, NEDSSConcurrentDataException
   {
     try {
		NNDActivityLogDT nndActivityLogDT = null;
		 //saving LabResultProxyVO before updating auto resend notifications
		 Map<Object, Object> returnVal = this.setLabResultProxyWithoutNotificationAutoResend(
		     labResultProxyVO, securityObj);

		 NNDMessageSenderHelper nndMessageSenderHelper = NNDMessageSenderHelper.
		     getInstance();


		  try
		  {
		    //update auto resend notifications
			if(labResultProxyVO.getAssociatedNotificationInd() == true)  
		    nndMessageSenderHelper.updateAutoResendNotificationsAsync(labResultProxyVO, securityObj);
		  }
		  catch(Exception e)
		  {
		    nndActivityLogDT = new  NNDActivityLogDT();
		    nndActivityLogDT.setErrorMessageTxt(e.toString());
		    Collection<ObservationVO>  observationCollection  = labResultProxyVO.getTheObservationVOCollection();
		    ObservationVO obsVO = ObservationUtils.findObservationByCode(observationCollection, NEDSSConstants.LAB_REPORT);
		    String localId = obsVO.getTheObservationDT().getLocalId();
		    if (localId!=null)
		    {
		      nndActivityLogDT.setLocalId(localId);
		     }
		     else
		       nndActivityLogDT.setLocalId("N/A");
		    //catch & store auto resend notifications exceptions in NNDActivityLog table
		      nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,securityObj);
		    logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
		    e.printStackTrace();
		  }
		  
		  Alert  alertRef = null;

		  if(labResultProxyVO.isItNew() && propertyUtil.getEnableELRAlert()!=null && propertyUtil.getEnableELRAlert().equals(NEDSSConstants.TRUE)){
			  try
			  {
				  alertRef= getAlertEJBRemoteInterface();
				  java.util.Date date = new java.util.Date();
			      long time1 = date.getTime();
			       logger.debug("time1 is :" + time1);
			      String LocalId= (String)returnVal.get(NEDSSConstants.SETLAB_RETURN_OBS_LOCAL);
				  alertRef.alertLabsEmailMessage(labResultProxyVO,LocalId,  securityObj);
		   		  java.util.Date date2 = new java.util.Date();
			      long time2 = date2.getTime();
			      logger.debug("time2 is :" + time2);
			      logger.debug("Total alertfunctionality  time taken is:" + (time2-time1));
		  		}
		  		catch(Exception e){
		  			logger.error("Alert message could not be captured" + e.getMessage());
		  		}
		  }
		  if(labResultProxyVO.getMessageLogDCollection()!=null && labResultProxyVO.getMessageLogDCollection().size()>0){
		      MessageLogDAOImpl messageLogDAOImpl =  new MessageLogDAOImpl();
				try {
					messageLogDAOImpl.storeMessageLogDTCollection(labResultProxyVO.getMessageLogDCollection());
				} catch (Exception e) {
					logger.error("Unable to store the Error message for = "+ labResultProxyVO.getMessageLogDCollection());
				}
		  }
		 return returnVal;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.setLabResultProxy: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

   } //setLabResultProxy()

  private Map<Object, Object> setLabResultProxyWithoutNotificationAutoResend(LabResultProxyVO
      labResultProxyVO, NBSSecurityObj securityObj) throws java.rmi.
      RemoteException, javax.ejb.CreateException, NEDSSConcurrentDataException
  {
    //Before doing anything
    checkMethodArgs(labResultProxyVO, securityObj);

    //Set flag for type of processing
    String userID = securityObj.getTheUserProfile().getTheUser().getUserID();
    boolean ELR_PROCESSING = false;
    if (userID != null && userID.equals(NEDSSConstants.ELR_LOAD_USER_ACCOUNT))
    {
      ELR_PROCESSING = true;
    }

    //Check permission to proceed
    checkPermissionToSetProxy(labResultProxyVO, securityObj, ELR_PROCESSING);

    //All well to proceed
    Map<Object, Object> returnVal = null;
    Long falseUid = null;
    Long realUid = null;
    boolean valid = false;

    EntityController entityControllerRef = null;
    ActController actControllerRef = null;

    try
    {
      entityControllerRef = getEntityControllerRemoteInterface();
      actControllerRef = getActControllerRemoteInterface();

      //Process PersonVOCollection  and adds the patient mpr uid to the return
      Long patientMprUid = processLabPersonVOCollection(labResultProxyVO,
          securityObj);
      if (patientMprUid != null)
      {
        if (returnVal == null)
        {
          returnVal = new HashMap<Object, Object>();
        }
        returnVal.put(NEDSSConstants.SETLAB_RETURN_MPR_UID, patientMprUid);
      }

      //ObservationVOCollection
      Map<Object, Object> obsResults = processObservationVOCollection(labResultProxyVO,
          securityObj, ELR_PROCESSING);
      if (obsResults != null)
      {
        if (returnVal == null)
        {
          returnVal = new HashMap<Object, Object>();
        }
        returnVal.putAll(obsResults);
      }

      //For ELR update, mpr uid may be not available
      if(patientMprUid == null || patientMprUid.longValue() < 0)
      {
        patientMprUid = new GeneralDAOImpl().findPatientMprUidByObservationUid( (
            Long) obsResults.get(NEDSSConstants.SETLAB_RETURN_OBS_UID));
        if(patientMprUid == null) throw new NEDSSSystemException("Expected this observation to be associated with a patient, observation uid = " +
              obsResults.get(NEDSSConstants.SETLAB_RETURN_OBS_UID));
        returnVal.put(NEDSSConstants.SETLAB_RETURN_MPR_UID, patientMprUid);
      }


      //Retrieve and return local ids for the patient and observation
      Long observationUid = (Long)obsResults.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
      returnVal.putAll(this.findLocalUidsFor(patientMprUid, observationUid, securityObj));

      //OrganizationCollection
      OrganizationVO organizationVO = null;
      if (labResultProxyVO.getTheOrganizationVOCollection() != null)
      {
        for (Iterator<Object> anIterator = labResultProxyVO.
             getTheOrganizationVOCollection().iterator(); anIterator.hasNext(); )
        {
          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          organizationVO = (OrganizationVO) anIterator.next();
          OrganizationDT newOrganizationDT = null;
          logger.debug("organizationUID: " +
                       organizationVO.getTheOrganizationDT().getOrganizationUid());

          if (organizationVO.isItNew())
          {
            newOrganizationDT = (OrganizationDT) prepareVOUtils.prepareVO(
                organizationVO.getTheOrganizationDT(), NBSBOLookup.ORGANIZATION,
                NEDSSConstants.ORG_CR, DataTables.ORGANIZATION_TABLE,
                NEDSSConstants.BASE, securityObj);
            logger.debug("new organizationUID: " +
                         newOrganizationDT.getOrganizationUid());
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            falseUid = organizationVO.getTheOrganizationDT().getOrganizationUid();
            logger.debug("false organizationUID: " + falseUid);
            realUid = entityControllerRef.setOrganization(organizationVO,
                securityObj);
            if (falseUid.intValue() < 0)
            {
              this.setFalseToNew(labResultProxyVO, falseUid, realUid);
            }
          }
          else if (organizationVO.isItDirty())
          {
            newOrganizationDT = (OrganizationDT) prepareVOUtils.prepareVO(
                organizationVO.getTheOrganizationDT(), NBSBOLookup.ORGANIZATION,
                NEDSSConstants.ORG_EDIT, DataTables.ORGANIZATION_TABLE,
                NEDSSConstants.BASE, securityObj);
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            realUid = entityControllerRef.setOrganization(organizationVO,
                securityObj);
            logger.debug("exisiting but updated organization's UID: " + realUid);
          }
        }
      }

      //MaterialCollection
      MaterialVO materialVO = null;
      if (labResultProxyVO.getTheMaterialVOCollection() != null)
      {
        for (Iterator<Object> anIterator = labResultProxyVO.getTheMaterialVOCollection().
             iterator(); anIterator.hasNext(); )
        {
          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          materialVO = (MaterialVO) anIterator.next();
          MaterialDT newMaterialDT = null;
          logger.debug("materialUID: " +
                       materialVO.getTheMaterialDT().getMaterialUid());

          if (materialVO.isItNew())
          {
            newMaterialDT = (MaterialDT) prepareVOUtils.prepareVO(materialVO.
                getTheMaterialDT(), NBSBOLookup.MATERIAL,
                NEDSSConstants.MAT_MFG_CR, DataTables.MATERIAL_TABLE,
                        NEDSSConstants.BASE, securityObj);
            logger.debug("new materialUID: " + newMaterialDT.getMaterialUid());
            materialVO.setTheMaterialDT(newMaterialDT);
            falseUid = materialVO.getTheMaterialDT().getMaterialUid();
            logger.debug("false materialUID: " + falseUid);
            realUid = entityControllerRef.setMaterial(materialVO, securityObj);
            if (falseUid.intValue() < 0)
            {
              this.setFalseToNew(labResultProxyVO, falseUid, realUid);
            }
          }
          else if (materialVO.isItDirty())
          {
            newMaterialDT = (MaterialDT) prepareVOUtils.prepareVO(materialVO.
                getTheMaterialDT(), NBSBOLookup.MATERIAL,
                NEDSSConstants.MAT_MFG_EDIT, DataTables.MATERIAL_TABLE,
                        NEDSSConstants.BASE, securityObj);
            materialVO.setTheMaterialDT(newMaterialDT);
            realUid = entityControllerRef.setMaterial(materialVO, securityObj);
            logger.debug("exisiting but updated material's UID: " + realUid);
          }
        }
      }

      //ParticipationCollection
      if (labResultProxyVO.getTheParticipationDTCollection() != null)
      {
        ParticipationDAOImpl participationDAOImpl = null;
        logger.debug("Iniside participation Collection<Object>  Loop - Lab");
        participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(
            JNDINames.ACT_PARTICIPATION_DAO_CLASS);
        for (Iterator<Object> anIterator = labResultProxyVO.
             getTheParticipationDTCollection().iterator(); anIterator.hasNext(); )
        {

          logger.debug("Inside loop size of participations: " +
                       labResultProxyVO.getTheParticipationDTCollection().size());
          ParticipationDT participationDT = (ParticipationDT) anIterator.next();
          try
          {
            if (participationDT != null)
            {
              if (participationDT.isItDelete())
              {
                insertParticipationHistory(participationDT);
              }
              participationDAOImpl.store(participationDT);

              logger.debug("got the participationDT, the ACTUID is " +
                           participationDT.getActUid());
              logger.debug("got the participationDT, the subjectEntityUid is " +
                           participationDT.getSubjectEntityUid());
            }
          }
          catch (Exception e)
          {
            logger.fatal(securityObj.getFullName(), e.getMessage(), e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage());
          }
        }
      }

      //ActRelationship Collection
      if (labResultProxyVO.getTheActRelationshipDTCollection() != null)
      {
        logger.debug("Act relationship size: " +
                     labResultProxyVO.getTheActRelationshipDTCollection().size());
        ActRelationshipDAOImpl actRelationshipDAOImpl = null;
        actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);

        for (Iterator<Object> anIterator = labResultProxyVO.
             getTheActRelationshipDTCollection().iterator(); anIterator.hasNext(); )
        {
          ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.
              next();
          try
          {
            if (actRelationshipDT != null)
            {
/*
              if (actRelationshipDT.isItDelete())
              {
                insertActRelationshipHistory(actRelationshipDT);
              }
 */
              actRelationshipDAOImpl.store(actRelationshipDT);

              logger.debug("Got into The ActRelationship, The ActUid is " +
                           actRelationshipDT.getTargetActUid());
            }
          }
          catch (Exception e)
          {
            logger.fatal(securityObj.getFullName(), e.getMessage(), e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage());
          }
        }
      }

      //Processes roleDT collection
      Collection<Object>  roleDTColl = labResultProxyVO.getTheRoleDTCollection();
      if (roleDTColl != null && !roleDTColl.isEmpty())
        storeRoleDTCollection(roleDTColl, securityObj);


        //add LDF data
      /**
       * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
     LDFHelper ldfHelper = LDFHelper.getInstance();
     ldfHelper.setLDFCollection(labResultProxyVO.getTheStateDefinedFieldDataDTCollection(), labResultProxyVO.getLdfUids(),
                         NEDSSConstants.LABREPORT_LDF,null,observationUid,securityObj);
     */
     
   //EDX Document 
     
			Collection<Object> edxDocumentCollection = labResultProxyVO
					.geteDXDocumentCollection();
			ObservationDT rootDT = this.getRootDT(labResultProxyVO);
			if (edxDocumentCollection != null
					&& edxDocumentCollection.size() > 0) {
				if (rootDT.getElectronicInd() != null
						&& rootDT.getElectronicInd().equals(NEDSSConstants.YES)) {
					Iterator<Object> ite = edxDocumentCollection.iterator();
					EDXDocumentDAOImpl dao = new EDXDocumentDAOImpl();
					while (ite.hasNext()) {
						EDXDocumentDT eDXDocumentDt = (EDXDocumentDT) ite
								.next();
						//remove any XML encoding before the container tag
						if(eDXDocumentDt.getPayload()!=null){
							String payload = eDXDocumentDt.getPayload();
							int containerIndex = payload.indexOf("<Container");
							eDXDocumentDt.setPayload(payload.substring(containerIndex));
						}
						if (eDXDocumentDt.isItNew())
							eDXDocumentDt.setActUid(observationUid);
							dao.insertEDXDocument(eDXDocumentDt);
					}
				}
			}
			AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
			rootDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			rootDT.setObservationUid(observationUid);
			if(labResultProxyVO.isItDirty()) {
				PageVO pageVO=(PageVO)labResultProxyVO.getPageVO();
				answerRootDAOImpl.store(pageVO, rootDT);
			}else {
				PageVO pageVO=(PageVO)labResultProxyVO.getPageVO();
				answerRootDAOImpl.insertPageVO(pageVO, rootDT);
			}
     }
    catch (NEDSSConcurrentDataException ex)
    {
    	logger.fatal("ObservationProxyEJB.setLabResultProxyWithoutNotificationAutoResend: NEDSSSystemException: concurrent access is not allowed " + ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.getMessage(), ex);
    } //main try
    catch (NEDSSSystemException e)
    {
    	logger.fatal("ObservationProxyEJB.setLabResultProxyWithoutNotificationAutoResend: NEDSSSystemException: " + e.getMessage(), e);
    	throw new NEDSSSystemException(e.getMessage(), e);
    }
    catch (Exception e)
    {
    	logger.fatal("ObservationProxyEJB.setLabResultProxyWithoutNotificationAutoResend: Exception: " + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    finally
    {
      try
      {
        entityControllerRef.remove();
        actControllerRef.remove();
      }
      catch (RemoveException rex)
      {
        rex.printStackTrace();
        logger.fatal("ObservationProxyEJB.setLabResultProxyWithoutNotificationAutoResend: RemoveException: " + rex.getMessage(), rex);
    	throw new javax.ejb.EJBException(rex.getMessage(), rex);
      }
    }


    return returnVal;
  } //setLabResultProxyWithoutNotificationAutoResend()

  private void checkMethodArgs(AbstractVO labResultProxyVO,
                               NBSSecurityObj securityObj)
  {
    try {
		if (labResultProxyVO == null || securityObj == null)
		{
		  throw new NullPointerException(
		      "Method arguements of setXXXProxy() cannot be null, however," +
		      "\n LabResultProxyVO labResultProxyVO is: " + labResultProxyVO +
		      "\n NBSSecurityObj securityObj is: " + securityObj);
		}

		if ( (!labResultProxyVO.isItNew() && !labResultProxyVO.isItDirty()) ||
		    (labResultProxyVO.isItNew() && labResultProxyVO.isItDirty()))
		{
		  logger.info(
		      " Inside not new not dirty -> labResultProxyVO.isItDirty() = " +
		      labResultProxyVO.isItDirty() + " labResultProxyVO.isItNew() = " +
		      labResultProxyVO.isItNew());
		  throw new IllegalStateException(
		      "Expected one and only one flag to be true:" +
		      "\n labResultProxyVO.isItNew() flag = " + labResultProxyVO.isItNew() +
		      "\n labResultProxyVO.isItDirty() flag = " +
		      labResultProxyVO.isItDirty());
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkMethodArgs: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void checkPermissionToSetProxy(AbstractVO proxyVO,
                                         NBSSecurityObj securityObj,
                                         boolean isElrProcessing)
  {
    try {
		boolean isMorbReport = false,
		    isLabReport = proxyVO instanceof LabResultProxyVO;
		if (!isLabReport)
		{
		  isMorbReport = proxyVO instanceof MorbidityProxyVO;

		  //If the proxyVO is a lab result, is the reporting lab having a permision
		}

		String userType = securityObj.getTheUserProfile().getTheUser().getUserType();
		if (isLabReport && userType != null && userType.equals(NEDSSConstants.SEC_USERTYPE_EXTERNAL))
		{
		  checkReportingLabPermission( (LabResultProxyVO) proxyVO, securityObj);
		}

		//If not elr processing, do further security check
		if (!isElrProcessing)
		{
		  //Defines the operation
		  String nbsOperation = proxyVO.isItNew() ? NBSOperationLookup.ADD :
		      NBSOperationLookup.EDIT;
		  logger.info("proxyVO.isItNew() = " + proxyVO.isItNew() +
		              " proxyVO.isItDirty() = " + proxyVO.isItDirty());

		  //Defines the program area and jurisdiction if not already provided,
		  //and then check permission

		  ObservationDT rootDT = this.getRootDT(proxyVO);
		  if (rootDT != null)
		  {
		    String progAreaCd = null;
		    if (isLabReport)
		    {
		      progAreaCd = (String) defineProgAreaJurisdictionCd(rootDT).get(this.
		          PROG_AREA_CD);
		    }
		    if (isMorbReport)
		    {
		      progAreaCd = rootDT.getProgAreaCd();
		      if (progAreaCd == null || progAreaCd.trim().equalsIgnoreCase(""))
		      {
		        throw new SecurityException(
		            "Expected a program area cd defined for this morbidity report.");
		      }
		    }
		    String jurisdictionCd = proxyVO.isItNew() ?
		        ProgramAreaJurisdictionUtil.ANY_JURISDICTION :
		        (String) defineProgAreaJurisdictionCd(rootDT).get(this.JURISDICTION_CD);

		    //Defines the business object
		    String nbsBOName = null;
		    if (isLabReport)
		    {
		      nbsBOName = NBSBOLookup.OBSERVATIONLABREPORT;
		    }
		    if (isMorbReport)
		    {
		      nbsBOName = NBSBOLookup.OBSERVATIONMORBIDITYREPORT;

		      //Aborts the operation if not getting the permission
		    }
		    if (!securityObj.getPermission(nbsBOName,
		                                   nbsOperation,
		                                   progAreaCd,
		                                   jurisdictionCd,
		                                   rootDT.getSharedInd()))
		    {
		      abort(nbsBOName, nbsOperation,
		            progAreaCd, jurisdictionCd, rootDT.getSharedInd());
		    }

		    //If new lab/morb report proxy and external user, set electronicInd = 'E', else
		    //If old lab/morb report proxy, deny editting elr or externally entered lab/morb report
		    String electronicInd = rootDT.getElectronicInd();

		    if(proxyVO.isItNew() && userType != null && userType.equals(NEDSSConstants.SEC_USERTYPE_EXTERNAL))
		    {
		      rootDT.setElectronicInd(NEDSSConstants.EXTERNAL_USER_IND);
		    }
		    else if(proxyVO.isItDirty() && electronicInd != null
		              && (electronicInd.equals(NEDSSConstants.EXTERNAL_USER_IND) || electronicInd.equals(NEDSSConstants.YES)))
		    {
		      throw new NEDSSSystemException("Cannot edit ELR or externally entered lab/morb report.");
		    }
		  }
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkPermissionToSetProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void checkReportingLabPermission(LabResultProxyVO proxyVO,
                                           NBSSecurityObj securityObj)
  {
    try {
		Long reportingLabUid = this.getUid(proxyVO.
		                                   getTheParticipationDTCollection(),
		                                   NEDSSConstants.ENTITY_UID_LIST_TYPE,
		                                   NEDSSConstants.ORGANIZATION,
		                                   NEDSSConstants.PAR111_TYP_CD,
		                                   NEDSSConstants.PART_ACT_CLASS_CD,
		                                   NEDSSConstants.RECORD_STATUS_ACTIVE,
		                                   false);
		Long secureLabUid = securityObj.getTheUserProfile().getTheUser().
		    getReportingFacilityUid();

		if (secureLabUid != null && (reportingLabUid == null || (reportingLabUid != null &&
		    reportingLabUid.compareTo(secureLabUid) != 0)))
		{
		  throw new SecurityException(
		      "\n\nPermission check fail for setLabResultProxy(), expected identical uids:" +
		      "\n  reporting Lab Uid of the proxy vo is: " + reportingLabUid +
		      "\n  secure default reporting Lab Uid of the security obj is: " +
		      secureLabUid);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkReportingLabPermission: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}

  }

  private Long processLabPersonVOCollection(AbstractVO proxyVO,
                                         NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		Collection<Object>  personVOColl = null;
		boolean isMorbReport = false;

		if (proxyVO instanceof MorbidityProxyVO)
		{
		  personVOColl = ( (MorbidityProxyVO) proxyVO).getThePersonVOCollection();
		  isMorbReport = true;
		}
		if (proxyVO instanceof LabResultProxyVO)
		{
		  personVOColl = ( (LabResultProxyVO) proxyVO).getThePersonVOCollection();
		}

		if (personVOColl == null)
		{
		  throw new IllegalArgumentException(
		      "PersonVO collection is null");
		}

		PersonVO personVO = null;
		Long patientMprUid = null;

		if (personVOColl != null && personVOColl.size() > 0)
		{
		  for (Iterator<Object> anIterator = personVOColl.iterator(); anIterator.hasNext(); )
		  {
		    personVO = (PersonVO) anIterator.next();

		    if (personVO == null)
		    {
		      continue;
		    }

		    logger.debug("personUID: " + personVO.getThePersonDT().getPersonUid());

		    //Finds out the type of person being processed and if it is a new person object,
		    //and abort the processing if the parameters not provided or provided incorrectly
		    String personType = personVO.getThePersonDT().getCd();
		    boolean isNewVO = personVO.isItNew();

		    if (personType == null)
		    {
		      throw new NullPointerException(
		          "Expected a non-null person type cd for this person uid: "
		          + personVO.getThePersonDT().getPersonUid());
		    }

		    ObservationDT rootDT = getRootDT(proxyVO);

		    //Persists the person object
		    boolean isExternal = false;
		    String electronicInd = rootDT.getElectronicInd();
		    if(electronicInd != null &&
		       ((isMorbReport && electronicInd.equals(NEDSSConstants.EXTERNAL_USER_IND)) ||
		        electronicInd.equals(NEDSSConstants.YES)))
		    {
		      isExternal = true;
		    }
		    Long realUid = null;
		    if(personVO.getRole()==null){
		     realUid = setPerson(personType, personVO, isNewVO, isExternal, securityObj);
		    }else{
		    	realUid =personVO.getThePersonDT().getPersonUid();
		    }


		    //If it is a new person object, updates the associations with the newly created uid
		    if (isNewVO && realUid != null)
		    {
		      Long falseUid = personVO.getThePersonDT().getPersonUid();
		      logger.debug("false personUID: " + falseUid);
		      logger.debug("real personUID: " + realUid);

		      if (falseUid.intValue() < 0)
		      {
		        this.setFalseToNew(proxyVO, falseUid, realUid);
		        //set the realUid to person after it has been set to participation
		        //this will help for jurisdiction derivation, this is only local to this call
		        personVO.getThePersonDT().setPersonUid(realUid);
		      }
		    }
		    else if (!isNewVO)
		    {
		      logger.debug("exisiting but updated person's UID: " + realUid);
		    }

		    //If it is patient, return the mpr uid, assuming only one patient in this processing
		    if (personType.equalsIgnoreCase(NEDSSConstants.PAT))
		    {
		      EntityProxyHelper eProxyHelper = EntityProxyHelper.getInstance();
		      patientMprUid = eProxyHelper.findPatientParentUidByUid(realUid);
		    }
		  }
		}
		return patientMprUid;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processLabPersonVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  } //end of perocess person coll

  private Long processMorbPersonVOCollection(AbstractVO proxyVO,
                                         NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		Collection<Object>  personVOColl = null;
		boolean isMorbReport = false;

		if (proxyVO instanceof MorbidityProxyVO)
		{
		  personVOColl = ( (MorbidityProxyVO) proxyVO).getThePersonVOCollection();
		  isMorbReport = true;
		}
		if (proxyVO instanceof LabResultProxyVO)
		{
		  personVOColl = ( (LabResultProxyVO) proxyVO).getThePersonVOCollection();
		}

		if (personVOColl == null)
		{
		  throw new IllegalArgumentException(
		      "PersonVO collection is null");
		}

		PersonVO personVO = null;
		Long patientMprUid = null;

		if (personVOColl != null && personVOColl.size() > 0)
		{
		  for (Iterator<Object> anIterator = personVOColl.iterator(); anIterator.hasNext(); )
		  {
		    personVO = (PersonVO) anIterator.next();

		    if (personVO == null)
		    {
		      continue;
		    }

		    logger.debug("personUID: " + personVO.getThePersonDT().getPersonUid());

		    //Finds out the type of person being processed and if it is a new person object,
		    //and abort the processing if the parameters not provided or provided incorrectly
		    String personType = personVO.getThePersonDT().getCd();
		    boolean isNewVO = personVO.isItNew();

		    if (personType == null)
		    {
		      throw new NullPointerException(
		          "Expected a non-null person type cd for this person uid: "
		          + personVO.getThePersonDT().getPersonUid());
		    }

		    ObservationDT rootDT = getRootDT(proxyVO);

		    //If it is a morbidity report, derive jurisdiction cd if needed
		    if (isMorbReport)
		    {
		      String jurisdictionCd = rootDT.getJurisdictionCd();
		      if (jurisdictionCd != null && (
		          jurisdictionCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
		          || jurisdictionCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.JURISDICTION_NONE)))
		      {
		        String error = deriveJurisdictionCd(proxyVO, rootDT, securityObj);
		      }
		      //Check the result of derivation, and reset to null if necessary
		      jurisdictionCd = rootDT.getJurisdictionCd();
		      if(jurisdictionCd != null && (jurisdictionCd.trim().equals("") || jurisdictionCd.equals("ANY") || jurisdictionCd.equals("NONE")))
		      {
		        rootDT.setJurisdictionCd(null);
		      }
		    }

		    //Persists the person object
		    boolean isExternal = false;
		    String electronicInd = rootDT.getElectronicInd();
		    if(electronicInd != null &&
		       ((isMorbReport && electronicInd.equals(NEDSSConstants.EXTERNAL_USER_IND)) ||
		        electronicInd.equals(NEDSSConstants.YES)))
		    {
		      isExternal = true;
		    }
		    Long realUid = setPerson(personType, personVO, isNewVO, isExternal, securityObj);

		    //If it is a new person object, updates the associations with the newly created uid
		    if (isNewVO && realUid != null)
		    {
		      Long falseUid = personVO.getThePersonDT().getPersonUid();
		      logger.debug("false personUID: " + falseUid);
		      logger.debug("real personUID: " + realUid);

		      if (falseUid.intValue() < 0)
		      {
		        this.setFalseToNew(proxyVO, falseUid, realUid);
		      }
		    }
		    else if (!isNewVO)
		    {
		      logger.debug("exisiting but updated person's UID: " + realUid);
		    }

		    //If it is patient, return the mpr uid, assuming only one patient in this processing
		    if (personType.equalsIgnoreCase(NEDSSConstants.PAT))
		    {
		      EntityProxyHelper eProxyHelper = EntityProxyHelper.getInstance();
		      patientMprUid = eProxyHelper.findPatientParentUidByUid(realUid);
		    }
		  }
		}
		return patientMprUid;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processMorbPersonVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  } //end of perocess person coll

  private Map<Object, Object> findLocalUidsFor(Long personMprUid, Long observationUid, NBSSecurityObj securityObj)
  {
    ActController actControllerRef = null;
    EntityController entityControllerRef = null;
    Map<Object, Object> localIds = null;

    try
    {
      //Find observation local id
      actControllerRef = this.getActControllerRemoteInterface();
      if(localIds == null) localIds = new HashMap<Object, Object> ();
      ObservationDT obsDT = actControllerRef.getObservationInfo(observationUid, securityObj);
      localIds.put(NEDSSConstants.SETLAB_RETURN_OBS_LOCAL, obsDT.getLocalId());
      localIds.put(NEDSSConstants.SETLAB_RETURN_OBSDT, obsDT);
      //Find mpr local id
      entityControllerRef = this.getEntityControllerRemoteInterface();
      localIds.put(NEDSSConstants.SETLAB_RETURN_MPR_LOCAL, entityControllerRef.getPersonInfo(personMprUid, securityObj).getLocalId());
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.setLabResultProxy: Error storing observation vo through act controller, " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
    finally
    {
      try
      {
        actControllerRef.remove();
      }
      catch (Exception ex)
      {
    	  logger.fatal("ObservationProxyEJB.setLabResultProxy: " + ex.getMessage(), ex);
    	  throw new javax.ejb.EJBException(ex.getMessage(), ex);
      }
    }
    return localIds;
  }

  private Map<Object, Object> processObservationVOCollection(AbstractVO proxyVO,
                                             NBSSecurityObj securityObj,
                                             boolean ELR_PROCESSING) throws
      NEDSSConcurrentDataException, RemoteException
  {
    try {
		//If coming from lab report, processing this way
		if (proxyVO instanceof LabResultProxyVO)
		{
		  return processLabReportObsVOCollection( (LabResultProxyVO) proxyVO,
		                                         securityObj, ELR_PROCESSING);
		}

		//If coming from morbidity, processing this way
		if (proxyVO instanceof MorbidityProxyVO)
		{
		  return processMorbObsVOCollection( (MorbidityProxyVO) proxyVO,
		                                    securityObj);
		  
		
		  
		}

		//If not above, abort the operation
		else
		{
		  throw new IllegalArgumentException(
		      "Expected a valid observation proxy vo, it is: " +
		      proxyVO.getClass().getName());
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processObservationVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }
  
  /**
   * setAddTimeInLabs: it sets the same addTime associated to the morbidity report to the labs created from there
   * @param proxyVO
   */
  
  private void setAddTimeInLabs(MorbidityProxyVO proxyVO){
	  
	  Collection <ObservationVO> obs =  ((MorbidityProxyVO) proxyVO).getTheObservationVOCollection();
		 
		 Iterator iter = obs.iterator();
		 Timestamp time = null;
		 while(iter.hasNext()){
			 
			 
			 ObservationVO obs1 = (ObservationVO)iter.next();
			 if(obs1.getTheObservationDT().getCtrlCdDisplayForm()!=null){
			 if(obs1.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase("MorbReport"))
				 
				 time = obs1.getTheObservationDT().getAddTime();
			 
			 if(obs1.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase("LabReportMorb")
					 &&obs1.getTheObservationDT().getAddTime()==null)
				 
				 obs1.getTheObservationDT().setAddTime(time);
		 }
		 }
		 
  }
  private Map<Object, Object> processMorbObsVOCollection(MorbidityProxyVO morbidityProxyVO,
                                         NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException, RemoteException
  {
    try {
		//Pre-process root morbidity
    	//This function will add the addtime to the morbidity reports
		ObservationDT rootDT = processRootMorbReport(morbidityProxyVO, securityObj);

		setAddTimeInLabs(morbidityProxyVO);
		
		Map<Object, Object> returnObsVal = null;

		//Iterates the observation collection and processes each observation vo
		Collection<ObservationVO>  obsVOColl = morbidityProxyVO.getTheObservationVOCollection();
		ObservationVO observationVO = null;

		if (obsVOColl != null && obsVOColl.size() > 0)
		{
		  for (Iterator<ObservationVO> anIterator = obsVOColl.iterator(); anIterator.hasNext(); )
		  {
		    observationVO = (ObservationVO) anIterator.next();

		    if (observationVO == null)
		    {
		      continue;
		    }

		    //If a resulted test, pre-process it this way, otherwise continue
		    ObservationDT currentObsDT = observationVO.getTheObservationDT();
		    String labClia = "";
		    if(morbidityProxyVO.getLabClia()!=null)
		    	labClia = morbidityProxyVO.getLabClia();
		    else
		    	labClia = this.getReportingLabCLIA(morbidityProxyVO, securityObj);
		    String domainCtrlCode = //currentObsDT.getObsDomainCd();
		                            currentObsDT.getObsDomainCdSt1();
		    boolean isOrderedTest = domainCtrlCode != null &&
		                                domainCtrlCode.equalsIgnoreCase(NEDSSConstants.
		                                                                ORDERED_TEST_OBS_DOMAIN_CD);
		    boolean isResultedTest = domainCtrlCode != null &&
		                                   domainCtrlCode.equalsIgnoreCase(NEDSSConstants.
		                                                                    RESULTED_TEST_OBS_DOMAIN_CD);
		    if (isResultedTest)
		    {
		      if(currentObsDT.getCd() != null) continue;

		    
		    }

		    logger.debug("observationUID: " +
		                 observationVO.getTheObservationDT().getObservationUid());
		    //Do loinc and snomed lookups for oredered and resulted tests
		    if(isOrderedTest || isResultedTest)
		    {
		      observationVO = new ObservationProcessor().labLoincSnomedLookup(observationVO, labClia);
		    }
		  }
		}

		//Persist the observations
		Long observationUid = storeObservationVOCollection(morbidityProxyVO,
		    securityObj);

		//Return the root observation uid
		if (observationUid != null)
		{
		  if (returnObsVal == null)
		  {
		    returnObsVal = new HashMap<Object, Object>();
		  }
		  returnObsVal.put(this.SETMORB_RETURN_OBS_UID, observationUid);
		}

		return returnObsVal;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processMorbObsVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void processInterventionVOCollection(MorbidityProxyVO
                                               morbidityProxyVO,
                                               NBSSecurityObj nbsSecurityObj)
  {
    try {
		Collection<Object>  interventionColl = morbidityProxyVO.
		    getTheInterventionVOCollection();
		InterventionVO interventionVO = null;
		if (interventionColl != null && interventionColl.size() > 0)
		{
		  for (Iterator<Object> anIterator = interventionColl.iterator();
		       anIterator.hasNext(); )
		  {
		    interventionVO = (InterventionVO) anIterator.next();
		    if (interventionVO == null)
		    {
		      continue;
		    }

		    if (interventionVO.isItNew() || interventionVO.isItDirty())
		    {
		      ActController actController = null;
		      Long realUid = null, falseUid = null;
		      try
		      {
		        realUid = actController.setIntervention(
		            interventionVO, nbsSecurityObj);

		        falseUid = interventionVO.getTheInterventionDT().getInterventionUid();

		        logger.debug(
		            "the  false interventionUid is" +
		            falseUid);

		        logger.debug(
		            "the  real interventionUid is" + realUid);

		        if (falseUid.intValue() < 0)
		        {
		          setFalseToNewActOnly(morbidityProxyVO, falseUid,
		                               realUid);
		        }
		      }
		      catch (Exception ex)
		      {
		            ex.printStackTrace();
		            throw new EJBException("Error processing intervention");
		      }
		      finally
		      {
		        try
		        {
		          actController.remove();
		        }
		        catch (Exception ex)
		        {
		          ex.printStackTrace();
		        }
		      }
		    }
		  }
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processInterventionVOCollection: " + e.getMessage(), e);
  	  throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void replaceCodeFor(ObservationDT obsDT, String newCd)
  {
    try {
		//Replace cd
		obsDT.setCd(newCd);

		//Replace cdDescTxt
		String cdDescTxt = obsDT.getCdDescTxt();
		if (cdDescTxt == null)
		{
		  throw new NullPointerException(
		      "Expected non-null cd desc text for this observation, uid is: " +
		      obsDT.getObservationUid());
		}

		int hyphenIndex = cdDescTxt.indexOf(NEDSSConstants.CACHED_TESTNAME_DELIMITER);

		cdDescTxt = hyphenIndex == -1 ? cdDescTxt : cdDescTxt.substring(0, hyphenIndex - 1).trim();

		obsDT.setCdDescTxt(cdDescTxt);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.replaceCodeFor: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void processTreatmentVOCollection(MorbidityProxyVO morbidityProxyVO,
                                            NBSSecurityObj nbsSecurityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		Collection<Object>  treatmentColl = morbidityProxyVO.getTheTreatmentVOCollection();
		String programAreaCd = null;
		if(morbidityProxyVO.isItNew())
		{
		  ObservationDT rootDT = getRootDT(morbidityProxyVO);
		  programAreaCd = rootDT.getProgAreaCd();
		}


		TreatmentVO treatmentVO = null;
		if (treatmentColl != null && treatmentColl.size() > 0)
		{
		  for (Iterator<Object> anIterator = treatmentColl.iterator(); anIterator.hasNext(); )
		  {
		    treatmentVO = (TreatmentVO) anIterator.next();
		    if (treatmentVO == null)
		    {
		      continue;
		    }

		    if(morbidityProxyVO.isItNew())
		      treatmentVO.getTheTreatmentDT().setProgAreaCd(programAreaCd);

		    if (treatmentVO.isItNew() || treatmentVO.isItDirty())
		    {
		      ActController actController = null;
		      Long realUid = null, falseUid = null;
		      try
		      {
		        actController = this.getActControllerRemoteInterface();
		        realUid = actController.setTreatment(
		            treatmentVO, nbsSecurityObj);

		        falseUid = treatmentVO.getTheTreatmentDT().getTreatmentUid();

		        logger.debug(
		            "the  false treatmentUid is" +
		            falseUid);

		        logger.debug(
		            "the  real treatmentUid is" + realUid);

		        if (falseUid.intValue() < 0)
		        {
		          this.setFalseToNew(morbidityProxyVO, falseUid,
		                               realUid);

		        }
		      }
		      catch (Exception ex)
		      {
		        ex.printStackTrace();
		        throw new EJBException(
		            "Error while storing treatment and processing associations, "
		            + "false uid is: " + falseUid + " real uid is: " + realUid);
		      }
		      finally
		      {
		        try
		        {
		          actController.remove();
		        }
		        catch (Exception ex)
		        {
		          ex.printStackTrace();
		        }
		      }
		    }
		  }
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processTreatmentVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }


  private List<Object> defineProgAreaJurisdictionCd(ObservationDT orderDT)
  {
    try {
		List<Object> progAreaJurisdictionCdHolder = new ArrayList<Object> ();

		String progAreaCd = orderDT.getProgAreaCd();
		if (progAreaCd == null || progAreaCd.trim().equals(""))
		{
		  throw new SecurityException(
		      "Program area cd must be defined for this observation proxy vo.");
		}
		progAreaCd = (progAreaCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.
		                                          PROGRAM_AREA_NONE)) ?
		    ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA : progAreaCd;
		progAreaJurisdictionCdHolder.add(this.PROG_AREA_CD, progAreaCd);

		String jurisdictionCd = orderDT.getJurisdictionCd();
		if (jurisdictionCd == null || jurisdictionCd.trim().equals(""))
		{
		  throw new SecurityException(
		      "Jurisdiction cd must be defined for this observation proxy vo.");
		}
		jurisdictionCd = (jurisdictionCd.equalsIgnoreCase(
		    ProgramAreaJurisdictionUtil.JURISDICTION_NONE)) ?
		    ProgramAreaJurisdictionUtil.ANY_JURISDICTION : jurisdictionCd;
		progAreaJurisdictionCdHolder.add(this.JURISDICTION_CD, jurisdictionCd);

		return progAreaJurisdictionCdHolder;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.defineProgAreaJurisdictionCd: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void abort(String nbsBO, String nbsOp, String nbsPA,
                     String nbsJurisdiction, String sharableFlag)
  {
    //This can turn into a custom exception
    throw new SecurityException(
        "Permission check fail for an operation of ObservationProxyEJB," +
        " NBS business object: " + nbsBO +
        ", NBS business operation: " + nbsOp +
        ", NBS program area: " + nbsPA +
        ", NBS jurisdiction: " + nbsJurisdiction +
        ", NBS sharable indicator: " + sharableFlag);

  }

  private Long setPerson(String personType, PersonVO personVO, boolean isNew, boolean isExternal,
                         NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    EntityController entityControllerRef = null;
    try
    {
      entityControllerRef = getEntityControllerRemoteInterface();

      if (personType.equalsIgnoreCase(NEDSSConstants.PAT))
      {
        return entityControllerRef.setPatientRevision(personVO,
            isNew ? NEDSSConstants.PAT_CR : NEDSSConstants.PAT_EDIT,
            securityObj);
      }
      else if (personType.equalsIgnoreCase(NEDSSConstants.PRV) && (!isNew || (isNew && isExternal)))
      {
        return entityControllerRef.setProvider(personVO,
                                               isNew ? NEDSSConstants.PRV_CR :
                                               NEDSSConstants.PRV_EDIT,
                                               securityObj);
      }
      else
      {
        throw new IllegalArgumentException("Expected a valid person type: " +
                                           personType);
      }
    }
    catch (RemoteException rex)
    {
      logger.fatal("ObservationProxyEJB.setPerson: RemoteException" + "Fail to set a person: \n persontype is " +
              personType +
              "\n person isNew is " + isNew +
              "\n personUid is " +
              personVO.getThePersonDT().getPersonUid() + rex.getMessage(), rex);
      throw new javax.ejb.EJBException(rex.getMessage(), rex);
    }
    finally
    {
      try
      {
        entityControllerRef.remove();
      }
      catch (Exception ex)
      {
    	  logger.fatal("ObservationProxyEJB.setPerson: Exception: " + ex.getMessage(), ex);
    	  throw new javax.ejb.EJBException(ex.getMessage(), ex);
      }
    }
  }

  private Map<Object, Object> processLabReportObsVOCollection(LabResultProxyVO labResultProxyVO,
                                              NBSSecurityObj securityObj,
                                              boolean ELR_PROCESSING) throws
      NEDSSConcurrentDataException
  {
    try {
		Collection<ObservationVO>  obsVOColl = labResultProxyVO.getTheObservationVOCollection();
		ObservationVO observationVO = null;
		Map<Object, Object> returnObsVal = null;
		boolean isMannualLab = false;

		//Find out if it is mannual lab
		String electronicInd = getRootDT(labResultProxyVO).getElectronicInd();
		if(electronicInd != null && !electronicInd.equals(NEDSSConstants.YES))
		  isMannualLab = true;

		//Iterates the observation collection and
		//assign a lab test code to each of the ordered test and resulted tests

		if (obsVOColl != null && obsVOColl.size() > 0)
		{
		  for (Iterator<ObservationVO> anIterator = obsVOColl.iterator(); anIterator.hasNext(); )
		  {
		    observationVO = (ObservationVO) anIterator.next();

		    if (observationVO == null)
		    {
		      continue;
		    }

		    //For ordered test and resulted tests
		    ObservationDT currentDT = observationVO.getTheObservationDT();
		    String obsDomainCdSt1 = currentDT.getObsDomainCdSt1();
		    boolean isOrderedTest = (obsDomainCdSt1 != null &&
		            obsDomainCdSt1.equalsIgnoreCase(NEDSSConstants.
		                                            ORDERED_TEST_OBS_DOMAIN_CD))  && currentDT.getCd().equalsIgnoreCase("LAB112");
		    boolean isResultedTest = obsDomainCdSt1 != null &&
		               obsDomainCdSt1.equalsIgnoreCase(NEDSSConstants.
		                                                RESULTED_TEST_OBS_DOMAIN_CD);


		    if(isMannualLab)
		    {
		      // Removed for Rel 1.1.3 - as we are not doing a reverse translation for ORdered test and Resulted Test
		      if ( isOrderedTest || isResultedTest) {
		        //Retrieve lab test code
		   
		        //Do loinc and snomed lookups for oredered and resulted tests
		        //String labClia = this.getReportingLabCLIA(labResultProxyVO, securityObj);
		    	observationVO = new ObservationProcessor().labLoincSnomedLookup(observationVO, labResultProxyVO.getLabClia());
		      }
		      logger.debug("observationUID: " +
		                   observationVO.getTheObservationDT().getObservationUid());
		    }
		  }
		}

		//Process the ordered test further
		if (returnObsVal == null)
		{
		  returnObsVal = new HashMap<Object, Object>();
		}
		returnObsVal.putAll(processLabReportOrderTest(labResultProxyVO,
		                                              securityObj,
		                                              ELR_PROCESSING));

		//Then, persist the observations
		Long observationUid = storeObservationVOCollection(labResultProxyVO,
		    securityObj);

		//Return the order test uid
		if (observationUid != null)
		{
		  if (returnObsVal == null)
		  {
		    returnObsVal = new HashMap<Object, Object>();
		  }
		  returnObsVal.put(NEDSSConstants.SETLAB_RETURN_OBS_UID, observationUid);
		}
		return returnObsVal;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processLabReportObsVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Long storeObservationVOCollection(AbstractVO proxyVO,
                                            NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		//Iterates the observation collection and process each observation vo
		Collection<ObservationVO>  obsVOColl = null;
		boolean isLabResultProxyVO = false;
		if (proxyVO instanceof LabResultProxyVO)
		{
		  obsVOColl = ( (LabResultProxyVO) proxyVO).getTheObservationVOCollection();
		  isLabResultProxyVO = true;
		}
		if (proxyVO instanceof MorbidityProxyVO)
		{
		  obsVOColl = ( (MorbidityProxyVO) proxyVO).getTheObservationVOCollection();
		}

		ObservationVO observationVO = null;
		Long returnObsVal = null;

		if (obsVOColl != null && obsVOColl.size() > 0)
		{
		  for (Iterator<ObservationVO> anIterator = obsVOColl.iterator(); anIterator.hasNext(); )
		  {
		    observationVO = (ObservationVO) anIterator.next();

		    if (observationVO == null)
		    {
		      continue;
		    }

		    //If lab report's order test, set a flag
		    boolean isRootObs = false;

		    String obsDomainCdSt1 = observationVO.getTheObservationDT().
		        getObsDomainCdSt1();
		    if (isLabResultProxyVO && obsDomainCdSt1 != null &&
		        obsDomainCdSt1.equalsIgnoreCase(NEDSSConstants.
		                                        ORDERED_TEST_OBS_DOMAIN_CD))
		    {
		      isRootObs = true;
		    }

		    //If a root morbidity, set a flag so to return the observation uid
		    String ctrlCdDisplayForm = observationVO.getTheObservationDT().
		        getCtrlCdDisplayForm();
		    if (ctrlCdDisplayForm != null &&
		        ctrlCdDisplayForm.equalsIgnoreCase(NEDSSConstants.
		                                           MOB_CTRLCD_DISPLAY))
		    {
		      isRootObs = true;
		    }

		    //Persist the observation vo
		    Long observationUid = storeObservation(observationVO, securityObj);

		    //Update associations with real uid if new
		    if (observationVO.isItNew())
		    {
		      Long falseUid = observationVO.getTheObservationDT().getObservationUid();
		      logger.debug("false observationUID: " + falseUid);
		      if (falseUid.intValue() < 0) {
		        this.setFalseToNew(proxyVO, falseUid, observationUid);
		      }
		     }


		    //Return the order test uid
		    if (observationUid != null && isRootObs)
		    {
		      returnObsVal = observationUid;
		    }
		  } //end of for loop
		} //end of main if
		return returnObsVal;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeObservationVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Long storeObservation(ObservationVO observationVO,
                                NBSSecurityObj securityObj)
  {
    ActController actControllerRef = null;
    Long realUid = null;

    try
    {
      actControllerRef = getActControllerRemoteInterface();
      realUid = actControllerRef.setObservation(observationVO, securityObj);
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.storeObservation: " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
    finally
    {
      try
      {
        actControllerRef.remove();
      }
      catch (Exception ex)
      {
        logger.fatal("ObservationProxyEJB.storeObservation: " + ex.getMessage(), ex);
        throw new javax.ejb.EJBException(ex.getMessage(), ex);
      }
    }
    return realUid;
  }

  private Map<Object, Object> processLabReportOrderTest(LabResultProxyVO labResultProxyVO,
                                        NBSSecurityObj securityObj,
                                        boolean isELR) throws
      NEDSSConcurrentDataException
  {
    try {
		//Retrieve the ordered test
		ObservationVO orderTest = this.getRootObservationVO(labResultProxyVO);

		//Overrides rptToStateTime to current date/time for external user
		String userType = securityObj.getTheUserProfile().getTheUser().getUserType();
		if (userType != null &&
		    userType.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL))
		{
		  orderTest.getTheObservationDT().setRptToStateTime(new java.sql.Timestamp( (new
		      java.util.Date()).getTime()));
		}

		//Assign program area cd if necessary, and return any errors to the client
		Map<Object, Object> returnErrors = new HashMap<Object, Object>();
		String paCd = orderTest.getTheObservationDT().getProgAreaCd();
		if (paCd != null &&
		    paCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA))
		{
		  String paError = deriveProgramAreaCd(labResultProxyVO, orderTest,
		                                       securityObj);
		  if (paError != null)
		  {
		    returnErrors.put(NEDSSConstants.SETLAB_RETURN_PROGRAM_AREA_ERRORS,
		                     paError);
		  }
		}

		//Assign jurisdiction cd if necessary
		String jurisdictionCd = orderTest.getTheObservationDT().getJurisdictionCd();
		if (jurisdictionCd != null &&
		    (jurisdictionCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
		    || jurisdictionCd.equalsIgnoreCase(ProgramAreaJurisdictionUtil.JURISDICTION_NONE)))
		{
		  String jurisdictionError = deriveJurisdictionCd(labResultProxyVO,
		      orderTest.getTheObservationDT(), securityObj);
		  if (jurisdictionCd != null)
		  {
		    returnErrors.put(NEDSSConstants.SETLAB_RETURN_JURISDICTION_ERRORS,
		                     jurisdictionCd);
		  }
		}

		//Manipulate jurisdiction for preparing vo
		jurisdictionCd = orderTest.getTheObservationDT().getJurisdictionCd();
		if(jurisdictionCd != null && (jurisdictionCd.trim().equals("") || jurisdictionCd.equals("ANY") || jurisdictionCd.equals("NONE")))
		{
		  orderTest.getTheObservationDT().setJurisdictionCd(null);
		}

		//Do observation object state transition accordingly
		performOrderTestStateTransition(labResultProxyVO, orderTest, securityObj,
		                                isELR);

		return returnErrors;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processLabReportOrderTest: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private ObservationDT processRootMorbReport(MorbidityProxyVO morbProxyVO,
                                              NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		//Define prog area cd for the root
		GeneralDAOImpl dao = new GeneralDAOImpl();
		ObservationVO rootMorbVO = this.getRootObservationVO(morbProxyVO);

		if (rootMorbVO == null)
		{
		  throw new NullPointerException(
		      "Expected a non-null root morbidity observation vo.");
		}

		ObservationDT rootMorbDT = rootMorbVO.getTheObservationDT();
		String conditionCd = rootMorbDT.getLabConditionCd();

		List<Object> progAreaCdList = dao.findProgramAreaCdByConditionCd(conditionCd);

		if (progAreaCdList == null || progAreaCdList.size() != 1)
		{
		  rootMorbDT.setLabConditionCd("");
		}
		else
		{
		  rootMorbDT.setLabConditionCd( (String) progAreaCdList.get(0));
		}

		//Manipulate jurisdiction for preparing vo
		String jurisdictionCd = rootMorbDT.getJurisdictionCd();
		if(jurisdictionCd != null && (jurisdictionCd.trim().equals("") || jurisdictionCd.equals("ANY") || jurisdictionCd.equals("NONE")))
		{
		  rootMorbDT.setJurisdictionCd(null);
		}

		//Do observation object state transition accordingly
		performRootMorbidityStateTransition(morbProxyVO, rootMorbVO, securityObj);

		return rootMorbDT;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.processRootMorbReport: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private String deriveProgramAreaCd(LabResultProxyVO labResultProxyVO,
                                     ObservationVO orderTest,
                                     NBSSecurityObj securityObj)
  {
    try {
		//Gathering the result tests
		Collection<Object>  resultTests = new ArrayList<Object> ();
		for (Iterator<ObservationVO> it = labResultProxyVO.getTheObservationVOCollection().
		     iterator(); it.hasNext(); )
		{
		  ObservationVO obsVO = (ObservationVO) it.next();

		  String obsDomainCdSt1 = obsVO.getTheObservationDT().getObsDomainCdSt1();
		  if (obsDomainCdSt1 != null &&
		      obsDomainCdSt1.equalsIgnoreCase(NEDSSConstants.
		                                      RESULTED_TEST_OBS_DOMAIN_CD))
		  {
		    resultTests.add(obsVO);
		  }
		}

		//Get the reporting lab clia
		String reportingLabCLIA = "";
		if(labResultProxyVO.getLabClia()!=null && labResultProxyVO.isManualLab())
			reportingLabCLIA =labResultProxyVO.getLabClia();
		else
			reportingLabCLIA = getReportingLabCLIA(labResultProxyVO, securityObj);

		if(reportingLabCLIA == null || reportingLabCLIA.trim().equals(""))
		  reportingLabCLIA = NEDSSConstants.DEFAULT;

		//Get program area
		if(!orderTest.getTheObservationDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)){
		Map<Object, Object> paResults = null;
		if (resultTests.size() > 0)
		{
		  ObservationProcessor obsProcessor = new ObservationProcessor();
		  paResults = obsProcessor.getProgramArea(reportingLabCLIA, resultTests,
		                                          orderTest.getTheObservationDT().getElectronicInd());
		}

		  //set program area for order test
		  if (paResults != null &&
		      paResults.containsKey(ELRConstants.PROGRAM_AREA_HASHMAP_KEY))
		  {
		    orderTest.getTheObservationDT().setProgAreaCd( (String) paResults.get(
		        ELRConstants.PROGRAM_AREA_HASHMAP_KEY));
		  }
		  else
		  {
		    orderTest.getTheObservationDT().setProgAreaCd(null);
		  }


		//Return errors if any
		if (paResults != null &&
		    paResults.containsKey("ERROR"))
		{
		  return (String) paResults.get("ERROR");
		}
		else
		{
		  return null;
		}
		}
		return null;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.deriveProgramAreaCd: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private String getReportingLabCLIA(AbstractVO proxy,
                                     NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  partColl = null;
		if (proxy instanceof LabResultProxyVO)
		{
		  partColl = ( (LabResultProxyVO) proxy).getTheParticipationDTCollection();
		}
		if (proxy instanceof MorbidityProxyVO)
		{
		  partColl = ( (MorbidityProxyVO) proxy).getTheParticipationDTCollection();
		}

		//Get the reporting lab
		Long reportingLabUid = this.getUid(partColl,
		                                   NEDSSConstants.ENTITY_UID_LIST_TYPE,
		                                   NEDSSConstants.ORGANIZATION,
		                                   NEDSSConstants.PAR111_TYP_CD,
		                                   NEDSSConstants.PART_ACT_CLASS_CD,
		                                   NEDSSConstants.RECORD_STATUS_ACTIVE,
		                                   false);

		OrganizationVO reportingLabVO = null;
		EntityController eController = null;
		try
		{
		  eController = getEntityControllerRemoteInterface();
		  if (reportingLabUid != null)
		  {
		    reportingLabVO = eController.getOrganization(reportingLabUid,
		        securityObj);
		  }
		}
		catch (RemoteException rex)
		{
		  rex.printStackTrace();
		  throw new EJBException(
		      "Error while retriving reporting organization vo, its uid is: "
		      + reportingLabUid);
		}
		finally
		{
		  try
		  {
		    eController.remove();
		  }
		  catch (Exception ex)
		  {
		    ex.printStackTrace();
		  }
		}

		//Get the CLIA
		String reportingLabCLIA = null;

		if(reportingLabVO != null)
		{
		  Collection<Object>  entityIdColl = reportingLabVO.getTheEntityIdDTCollection();

		  if (entityIdColl != null && entityIdColl.size() > 0) {
		    for (Iterator<Object> it = entityIdColl.iterator(); it.hasNext(); ) {
		      EntityIdDT idDT = (EntityIdDT) it.next();
		      if (idDT == null) {
		        continue;
		      }

		      String authoCd = idDT.getAssigningAuthorityCd();
		      String idTypeCd = idDT.getTypeCd();
		      if (authoCd != null && idTypeCd != null &&
		          authoCd.equalsIgnoreCase(NEDSSConstants.REPORTING_LAB_CLIA) &&
		          idTypeCd.equalsIgnoreCase(NEDSSConstants.REPORTING_LAB_FI_TYPE)) { //civil00011659
		        reportingLabCLIA = idDT.getRootExtensionTxt();
		        break;
		      }
		    }
		  }
		}
		return reportingLabCLIA;
	} catch (EJBException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getReportingLabCLIA: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private String deriveJurisdictionCd(AbstractVO proxyVO,
                                      ObservationDT rootObsDT,
                                      NBSSecurityObj securityObj)
  {
    try {
		//Retieve provider uid and patient uid
		Collection<Object>  partColl = null;
		boolean isLabReport = false, isMorbReport = false;
		String jurisdictionDerivationInd = securityObj.getTheUserProfile().getTheUser().getJurisdictionDerivationInd();
		if (proxyVO instanceof MorbidityProxyVO)
		{
		  isMorbReport = true;
		  partColl = ( (MorbidityProxyVO) proxyVO).getTheParticipationDTCollection();
		}
		if (proxyVO instanceof LabResultProxyVO)
		{
		  isLabReport = true;
		  partColl = ( (LabResultProxyVO) proxyVO).getTheParticipationDTCollection();
		}
		if (partColl == null || partColl.size() <= 0)
		{
		  throw new NullPointerException(
		      "Participation collection is null or empty, it is: " + partColl);
		}

		Long providerUid = null;
		Long patientUid = null;
		Long orderingFacilityUid = null;
		Long reportingFacilityUid = null;

		   for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
		{
		  ParticipationDT partDT = (ParticipationDT) it.next();
		  if (partDT == null)
		  {
		    continue;
		  }

		  String typeCd = partDT.getTypeCd();
		  String subjectClassCd = partDT.getSubjectClassCd();
		  if (typeCd != null && (typeCd.equalsIgnoreCase(NEDSSConstants.PAR101_TYP_CD) || typeCd.equalsIgnoreCase(NEDSSConstants.MOB_PHYSICIAN_OF_MORB_REPORT))
				  && subjectClassCd != null && subjectClassCd.equalsIgnoreCase(NEDSSConstants.PERSON_CLASS_CODE))
		  {
			  providerUid = partDT.getSubjectEntityUid();
		  }
		  else if (typeCd != null &&
				  (typeCd.equalsIgnoreCase(NEDSSConstants.PAR110_TYP_CD) || typeCd.equalsIgnoreCase(NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT)))
		  {
			  patientUid = partDT.getSubjectEntityUid();
		  }
		  else if (typeCd != null &&
				  (typeCd.equalsIgnoreCase(NEDSSConstants.PAR102_TYP_CD)))
		  {
			  orderingFacilityUid = partDT.getSubjectEntityUid();
		  }
		  else if(jurisdictionDerivationInd!=null && jurisdictionDerivationInd.equals(NEDSSConstants.YES) && typeCd != null &&
				  typeCd.equalsIgnoreCase(NEDSSConstants.PAR111_TYP_CD) && subjectClassCd != null && subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR111_SUB_CD) &&
			  rootObsDT!=null && rootObsDT.getCtrlCdDisplayForm()!=null && rootObsDT.getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LAB_REPORT) && rootObsDT.getElectronicInd()!=null &&  rootObsDT.getElectronicInd().equals(NEDSSConstants.EXTERNAL_USER_IND))
				  reportingFacilityUid=partDT.getSubjectEntityUid();
		}

		//Get the provider vo from db
		   PersonVO providerVO = null;
		   OrganizationVO orderingFacilityVO = null;
		   OrganizationVO reportingFacilityVO = null;
		   EntityController eController = null;
		try
		{
		  eController = getEntityControllerRemoteInterface();
		  if (providerUid != null)
		  {
		    providerVO = eController.getProvider(providerUid, securityObj);
		  }
		  if (orderingFacilityUid != null)
		  {
		    orderingFacilityVO = eController.getOrganization(orderingFacilityUid, securityObj);
		  }
		  if(reportingFacilityUid!=null)
		  {
			  reportingFacilityVO = eController.getOrganization(reportingFacilityUid, securityObj);
		  }
		}
		catch (RemoteException rex)
		{
		  rex.printStackTrace();
		  throw new EJBException("Error retieving provider with UID:"+ providerUid +" OR Ordering Facility, its uid is: " +
				  orderingFacilityUid);
		}
		finally
		{
		  try
		  {
		    eController.remove();
		  }
		  catch (Exception ex)
		  {
		    ex.printStackTrace();
		  }
		}

		//Get the patient subject
		PersonVO patientVO = null;
		Collection<Object>  personVOColl = null;
		if (isLabReport)
		{
		  personVOColl = ( (LabResultProxyVO) proxyVO).getThePersonVOCollection();
		}
		if (isMorbReport)
		{
		  personVOColl = ( (MorbidityProxyVO) proxyVO).getThePersonVOCollection();

		}
		if (patientUid != null && personVOColl != null && personVOColl.size() > 0)
		{
		  for (Iterator<Object> it = personVOColl.iterator(); it.hasNext(); )
		  {
		    PersonVO pVO = (PersonVO) it.next();
		    if (pVO == null || pVO.getThePersonDT() == null)
		    {
		      continue;
		    }
		    if (pVO.getThePersonDT().getPersonUid().compareTo(patientUid) == 0)
		    {
		      patientVO = pVO;
		      break;
		    }
		  }
		}

		//Derive the jurisdictionCd
		Map<Object, Object> jMap = null;
		if (patientVO != null)
		{

		  try
		  {
		    NedssUtils nu = new NedssUtils();
		    Object obj = nu.lookupBean(JNDINames.JURISDICTIONSERVICE_EJB);
		    JurisdictionServiceHome home = (JurisdictionServiceHome) javax.rmi.
		        PortableRemoteObject.narrow(obj, JurisdictionServiceHome.class);
		    JurisdictionService jurisdiction = home.create();
		    jMap = jurisdiction.resolveLabReportJurisdiction(patientVO, providerVO, orderingFacilityVO, reportingFacilityVO);
		  }
		  catch (CreateException cex)
		  {
		    cex.printStackTrace();
		    throw new EJBException("Error creating jurisdiction services.");
		  }
		  catch (RemoteException rex)
		  {
		    rex.printStackTrace();
		    throw new EJBException("Error in resolving jurisdiction.");
		  }
		}

		//set jurisdiction for order test
		if (jMap != null &&
		    jMap.containsKey(ELRConstants.JURISDICTION_HASHMAP_KEY))
		{
		  rootObsDT.setJurisdictionCd( (String) jMap.get(
		      ELRConstants.JURISDICTION_HASHMAP_KEY));
		}
		else
		{
		  rootObsDT.setJurisdictionCd(null);
		}

		//Return errors if any
		if (jMap != null &&
		    jMap.containsKey("ERROR"))
		{
		  return (String) jMap.get("ERROR");
		}
		else
		{
		  return null;
		}
	} catch (EJBException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.deriveJurisdictionCd: EJBException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.deriveJurisdictionCd: NEDSSSystemException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	} catch (ClassCastException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.deriveJurisdictionCd: ClassCastException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}

  }

  private void performOrderTestStateTransition(LabResultProxyVO
                                               labResultProxyVO,
                                               ObservationVO orderTest,
                                               NBSSecurityObj securityObj,
                                               boolean isELR) throws
      NEDSSConcurrentDataException
  {
    try {
		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		String businessTriggerCd = null;
		ObservationDT newObservationDT = null;
		logger.debug("order test UID: " +
		             orderTest.getTheObservationDT().getObservationUid());

		if (labResultProxyVO.isItNew() && orderTest.getTheObservationDT().getProcessingDecisionCd()!=null && !orderTest.getTheObservationDT().getProcessingDecisionCd().trim().equals(""))
		{
		  businessTriggerCd = NEDSSConstants.OBS_LAB_CR_MR;
		}
		else if (labResultProxyVO.isItNew())
		{
		    businessTriggerCd = NEDSSConstants.OBS_LAB_CR;
		  }
		else if (labResultProxyVO.isItDirty())
		{
		  if (isELR)
		  {
		    businessTriggerCd = NEDSSConstants.OBS_LAB_CORRECT;
		  }
		  else
		  {
		    businessTriggerCd = NEDSSConstants.OBS_LAB_EDIT;
		  }
		}
		logger.debug("\n **********businessTriggerCd" + businessTriggerCd);
		newObservationDT = (ObservationDT) prepareVOUtils.prepareVO(
		    orderTest.getTheObservationDT(), NBSBOLookup.OBSERVATIONLABREPORT,
		    businessTriggerCd, DataTables.OBSERVATION_TABLE, NEDSSConstants.BASE,
		    securityObj);
		orderTest.setTheObservationDT(newObservationDT);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.performOrderTestStateTransition: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void performRootMorbidityStateTransition(MorbidityProxyVO morbProxyVO,
      ObservationVO rootMorbVO, NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    try {
		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		String businessTriggerCd = null;
		ObservationDT newObservationDT = null;
		logger.debug("order test UID: " +
		             rootMorbVO.getTheObservationDT().getObservationUid());
		if (rootMorbVO.isItNew() && rootMorbVO.getTheObservationDT().getProcessingDecisionCd()!=null && !rootMorbVO.getTheObservationDT().getProcessingDecisionCd().trim().equals(""))
		{
		  businessTriggerCd = NEDSSConstants.OBS_MORB_CR_MR;
		}
		else if (morbProxyVO.isItNew())
		{
		  businessTriggerCd = NEDSSConstants.OBS_MORB_CREATE;
		}
		else if (morbProxyVO.isItDirty())
		{
		  businessTriggerCd = NEDSSConstants.OBS_MORB_EDIT;
		}
		logger.debug("\n **********businessTriggerCd" + businessTriggerCd);
		newObservationDT = (ObservationDT) prepareVOUtils.prepareVO(
		    rootMorbVO.getTheObservationDT(),
		    NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
		    businessTriggerCd, DataTables.OBSERVATION_TABLE, NEDSSConstants.BASE,
		    securityObj);
		rootMorbVO.setTheObservationDT(newObservationDT);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.performRootMorbidityStateTransition: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  //#######################################################################################/
  //END: setLabResultProxy(LabResultProxyVO labResultProxyVO, NBSSecurityObj securityObj)
  //#######################################################################################/

  //*****************************************************************************************/
  //BEGIN: getLabResultProxy(Long observationUID, NBSSecurityObj securityObj)
  //*****************************************************************************************/
  /**
   * @roseuid 3C9221D902BF
   /**
    * @J2EE_METHOD  --  getLabResultProxy
    */
	public LabResultProxyVO getLabResultProxy(Long observationUID, NBSSecurityObj securityObj)
			throws javax.ejb.EJBException, NEDSSSystemException {
		return getLabResultProxyByType(observationUID, false, securityObj);

	}
  
   public LabResultProxyVO getLabResultProxyByType(Long observationUID, boolean isELR,
                                             NBSSecurityObj securityObj) throws
       javax.ejb.EJBException,
       NEDSSSystemException
     {
       try {
         logger.info(securityObj.getFullName(), "Starts getLabResultProxy()...");
         LabResultProxyManager lrpManager = new LabResultProxyManager();
         return lrpManager.getLabResultProxyVO(observationUID, isELR, securityObj);
       } catch(Exception e) {
    	 logger.fatal("ObservationProxyEJB.getLabResultProxy: " + e.getMessage(), e);
         throw new NEDSSSystemException(e.getMessage(), e);
       }

   } //getLabResultProxy

  private void checkMethodArgs(Long uid, NBSSecurityObj securityObj)
  {
    if (uid == null || securityObj == null)
    {
      logger.error(securityObj.getFullName(),
                   "The observation uid provided is not valid (null value).");
      throw new NullPointerException(
          "Method arguements of getXXXProxy() cannot be null, however," +
          "\n Act/Entity uid is: " + uid +
          "\n NBSSecurityObj securityObj is: " + securityObj);
    }
  }

  private void checkPermissionToGetProxy(ObservationVO rootVO,
                                         NBSSecurityObj securityObj)
  {
    try {
		//Aborts the operation if not getting the permission
		ObservationDT rootDT = rootVO.getTheObservationDT();
		String nbsBOName = NBSBOLookup.OBSERVATIONLABREPORT;
		String nbsOperation = NBSOperationLookup.VIEW;

		if (!securityObj.checkDataAccess(rootDT,
		                                 nbsBOName,
		                                 nbsOperation))
		{
		  String errorMessage = "User " + securityObj.getFullName() +
		      "does not have data Access privileges for this Lab Report";
		  logger.error(errorMessage);
		  abort(nbsBOName, nbsOperation,
		        "", "", "");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkPermissionToGetProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private List<Object> retrieveEntityForProxyVO(Collection<Object> partColl,
                                        NBSSecurityObj securityObj)
  {
    try {
		List<Object> allEntityHolder = new ArrayList<Object> ();

		//Retrieve associated persons
		allEntityHolder.add(this.RETRIEVED_PERSONS_FOR_PROXY,
		                    retrievePersonVOsForProxyVO(partColl, securityObj));

		//Retrieve associated organizations
		allEntityHolder.add(this.RETRIEVED_ORGANIZATIONS_FOR_PROXY,
		                    retrieveOrganizationVOsForProxyVO(partColl, securityObj));

		//Retrieve associated materials
		allEntityHolder.add(this.RETRIEVED_MATERIALS_FOR_PROXY,
		                    retrieveMaterialVOsForProxyVO(partColl, securityObj));

		return allEntityHolder;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveEntityForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrievePersonVOsForProxyVO(Collection<Object> partColl,
                                                 NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  thePersonVOCollection  = new ArrayList<Object> ();

		for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
		{
		  ParticipationDT partDT = (ParticipationDT) it.next();

		  if (partDT == null)
		  {
		    continue;
		  }

		  String subjectClassCd = partDT.getSubjectClassCd();
		  String recordStatusCd = partDT.getRecordStatusCd();
		  String typeCd = partDT.getTypeCd();

		  //If person...
		  if (subjectClassCd != null &&
		      subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR110_SUB_CD)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    thePersonVOCollection.add(getEntityVO(NEDSSConstants.PERSON,
		                                          partDT.getSubjectEntityUid(),
		                                          securityObj));

		    //If the person is a patient, do more...
		    if (typeCd != null &&
		        typeCd.equalsIgnoreCase(NEDSSConstants.PAR110_TYP_CD))
		    {
		      Collection<Object>  scopedPersons = retrieveScopedPersons(partDT.
		          getSubjectEntityUid(), securityObj);
		      if (scopedPersons != null && scopedPersons.size() > 0)
		      {
		        thePersonVOCollection.addAll(scopedPersons);
		      }
		    }
		  }
		}
		return thePersonVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrievePersonVOsForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrievePatientScopingRoles(Collection<Object> partColl,
                                           NBSSecurityObj securityObj)
  {
    try {
		//Retrieve patient's scoped persons
		Long patientUid = this.getUid(partColl,
		                                         NEDSSConstants.ENTITY_UID_LIST_TYPE,
		                                         NEDSSConstants.PAR110_SUB_CD,
		                                         NEDSSConstants.PAR110_TYP_CD,
		                                         NEDSSConstants.PART_ACT_CLASS_CD,
		                                         NEDSSConstants.RECORD_STATUS_ACTIVE,
		                                         false);
		if(patientUid == null) throw new NEDSSSystemException("Errors finding patient uid from the participation collection.");
		return new RoleDAOImpl().loadScoping(patientUid.longValue());
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrievePatientScopingRoles: NEDSSDAOSysException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrievePatientScopingRoles: NEDSSSystemException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveScopedPersons(Long scopingUid,
                                           NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  roleDTColl = new RoleDAOImpl().loadScoping(scopingUid.longValue());
		Collection<Object>  scopedPersons = null;

		for (Iterator<Object> it = roleDTColl.iterator(); it.hasNext(); )
		{
		  RoleDT roleDT = (RoleDT) it.next();
		  if (roleDT == null)
		  {
		    continue;
		  }

		  String subjectClassCd = roleDT.getSubjectClassCd();
		  String recordStatusCd = roleDT.getRecordStatusCd();
		  Long subjectEntityUid = roleDT.getSubjectEntityUid();
		  if (subjectClassCd != null &&
		     (subjectClassCd.equalsIgnoreCase("PROV")||
		     subjectClassCd.equalsIgnoreCase("CON"))
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    if (scopedPersons == null)
		    {
		      scopedPersons = new ArrayList<Object> ();
		    }
		    if (subjectEntityUid != null)
		    {
		      scopedPersons.add(getEntityVO(NEDSSConstants.PERSON, subjectEntityUid,
		                                    securityObj));
		    }
		  }
		}
		return scopedPersons;
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveScopedPersons: NEDSSDAOSysException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveScopedPersons: NEDSSSystemException: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveOrganizationVOsForProxyVO(Collection<Object> partColl,
      NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  theOrganizationVOCollection  = null;

		for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
		{
		  ParticipationDT partDT = (ParticipationDT) it.next();

		  if (partDT == null)
		  {
		    continue;
		  }

		  String subjectClassCd = partDT.getSubjectClassCd();
		  String recordStatusCd = partDT.getRecordStatusCd();

		  //If organization...
		  if (subjectClassCd != null &&
		      subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR102_SUB_CD)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long organizationUid = partDT.getSubjectEntityUid();
		    if (theOrganizationVOCollection  == null)
		    {
		      theOrganizationVOCollection  = new ArrayList<Object> ();
		    }
		    theOrganizationVOCollection.add(getEntityVO(NEDSSConstants.ORGANIZATION,
		        organizationUid,
		        securityObj));
		  }
		}
		return theOrganizationVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveOrganizationVOsForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveMaterialVOsForProxyVO(Collection<Object> partColl,
      NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  theMaterialVOCollection  = null;

		for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
		{
		  ParticipationDT partDT = (ParticipationDT) it.next();

		  if (partDT == null)
		  {
		    continue;
		  }

		  String subjectClassCd = partDT.getSubjectClassCd();
		  String recordStatusCd = partDT.getRecordStatusCd();

		  //If material...
		  if (subjectClassCd != null &&
		      subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR104_SUB_CD)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long materialUid = partDT.getSubjectEntityUid();
		    if (theMaterialVOCollection  == null)
		    {
		      theMaterialVOCollection  = new ArrayList<Object> ();
		    }
		    theMaterialVOCollection.add(getEntityVO(NEDSSConstants.MATERIAL,
		                                            materialUid,
		                                            securityObj));
		  }
		}
		return theMaterialVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveMaterialVOsForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private List<Object> retrieveActForProxyVO(Collection<Object> actRelColl,
                                     NBSSecurityObj securityObj)
  {
    try {
		List<Object> allActHolder = new ArrayList<Object> ();

		//Retrieve associated interventions
		allActHolder.add(this.RETRIEVED_INTERVENTIONS_FOR_PROXY,
		                 retrieveInterventionVOsForProxyVO(actRelColl, securityObj));

		//Retrieve associated observations and performing labs of any resulted tests
		List<Object> obs_org = (List<Object>) retrieveObservationVOsForProxyVO(actRelColl,
		    securityObj);
		allActHolder.add(this.RETRIEVED_OBSERVATIONS_FOR_PROXY, obs_org.get(0));
		allActHolder.add(this.RETRIEVED_LABS_FOR_RT, obs_org.get(1));

		return allActHolder;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveActForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveInterventionVOsForProxyVO(Collection<Object> actRelColl,
      NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  theInterventionVOCollection  = null;

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
		{
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

		  if (actRelDT == null)
		  {
		    continue;
		  }

		  String sourceClassCd = actRelDT.getSourceClassCd();
		  String targetClassCd = actRelDT.getTargetClassCd();
		  String recordStatusCd = actRelDT.getRecordStatusCd();

		  //If intervention...
		  if (sourceClassCd != null &&
		      sourceClassCd.equalsIgnoreCase(NEDSSConstants.INTERVENTION_CLASS_CODE)
		      && targetClassCd != null &&
		      targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long interventionUid = actRelDT.getSourceActUid();
		    if (theInterventionVOCollection  == null)
		    {
		      theInterventionVOCollection  = new ArrayList<Object> ();
		    }
		    theInterventionVOCollection.add(getActVO(NEDSSConstants.
		                                             INTERVENTION_CLASS_CODE,
		                                             interventionUid,
		                                             securityObj));
		  }
		}
		return theInterventionVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveInterventionVOsForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveObservationVOsForProxyVO(Collection<Object> actRelColl,
      NBSSecurityObj securityObj)
  {
    try {
		List<Object> obs_org = new ArrayList<Object> ();
		Collection<Object>  theObservationVOCollection  = new ArrayList<Object> ();
		Collection<Object>  performingLabColl = new ArrayList<Object> ();

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
		{
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

		  if (actRelDT == null)
		  {
		    continue;
		  }

		  String typeCd = actRelDT.getTypeCd();
		  String sourceClassCd = actRelDT.getSourceClassCd();
		  String targetClassCd = actRelDT.getTargetClassCd();
		  String recordStatusCd = actRelDT.getRecordStatusCd();

		  //If observation...
		  if (sourceClassCd != null &&
		      sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && targetClassCd != null &&
		      targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long observationUid = actRelDT.getSourceActUid();

		    //If a Comments observation
		    if (typeCd != null && typeCd.equalsIgnoreCase("APND"))
		    {
		      ObservationVO ordTestCommentVO = (ObservationVO)getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE,
		                                              observationUid,
		                                              securityObj);

		      theObservationVOCollection.add(ordTestCommentVO);
		      Collection<Object>  arColl = ordTestCommentVO.getTheActRelationshipDTCollection();
		      if(arColl != null){
		       Iterator<Object>  arCollIter = arColl.iterator();
		        while(arCollIter.hasNext()){
		          ActRelationshipDT ordTestDT = (ActRelationshipDT)arCollIter.next();
		          if(ordTestDT.getTypeCd().equals("COMP")){
		            //add the resulted test to the collection
		            ObservationVO resTestVO = (ObservationVO) getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE,
		                                              ordTestDT.getSourceActUid(),
		                                              securityObj);

		            theObservationVOCollection.add(resTestVO);

		          }
		        }

		      }
		    }
		    //If a Resulted Test observation
		    else if (typeCd != null &&
		             typeCd.equalsIgnoreCase(NEDSSConstants.ACT108_TYP_CD))
		    {
		      ObservationVO rtObservationVO = (ObservationVO) getActVO(
		          NEDSSConstants.OBSERVATION_CLASS_CODE,
		          observationUid,
		          securityObj);
		      if (rtObservationVO == null)
		      {
		        continue;
		      }
		      theObservationVOCollection.add(rtObservationVO); //The Resulted Test itself
		      //Retrieve the RT's lab
		      OrganizationVO rtPerformingLab = retrievePerformingLab(
		          rtObservationVO.getTheParticipationDTCollection(), securityObj);
		      if (rtPerformingLab != null)
		      {
		        performingLabColl.add(rtPerformingLab);
		      }


		      //Retrieves all reflex observations, including each ordered and its resulted
		      Collection<Object>  reflexObsColl = retrieveReflexObservations(rtObservationVO.
		          getTheActRelationshipDTCollection(), securityObj);
		      if (reflexObsColl == null || reflexObsColl.size() <= 0)
		      {
		        continue;
		      }
		      theObservationVOCollection.addAll(reflexObsColl);
		    }
		  }
		}

		obs_org.add(0, theObservationVOCollection);
		obs_org.add(1, performingLabColl);
		return obs_org;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveObservationVOsForProxyVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private OrganizationVO retrievePerformingLab(Collection<Object> partColl,
                                               NBSSecurityObj securityObj)
  {
    try {
		OrganizationVO lab = null;

		for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
		{
		  ParticipationDT partDT = (ParticipationDT) it.next();

		  if (partDT == null)
		  {
		    continue;
		  }

		  String typeCd = partDT.getTypeCd();
		  String subjectClassCd = partDT.getSubjectClassCd();
		  String actClassCd = partDT.getActClassCd();
		  String recordStatusCd = partDT.getRecordStatusCd();

		  //If performing lab...
		  if (typeCd != null && typeCd.equals(NEDSSConstants.PAR122_TYP_CD)
		      && subjectClassCd != null &&
		      subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR122_SUB_CD)
		      && actClassCd != null &&
		      actClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long organizationUid = partDT.getSubjectEntityUid();

		    lab = (OrganizationVO) getEntityVO(NEDSSConstants.ORGANIZATION,
		                                       organizationUid,
		                                       securityObj);
		    break; //only one lab for each RT
		  }
		}
		return lab;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrievePerformingLab: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveReflexObservations(Collection<Object> actRelColl,
                                                NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  reflexObsVOCollection  = null;

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
		{
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

		  if (actRelDT == null)
		  {
		    continue;
		  }

		  String typeCd = actRelDT.getTypeCd();
		  String sourceClassCd = actRelDT.getSourceClassCd();
		  String targetClassCd = actRelDT.getTargetClassCd();
		  String recordStatusCd = actRelDT.getRecordStatusCd();

		  //If reflex ordered test observation...
		  if (typeCd != null &&
		      typeCd.equalsIgnoreCase(NEDSSConstants.ACT109_TYP_CD)
		      && sourceClassCd != null &&
		      sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && targetClassCd != null &&
		      targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long observationUid = actRelDT.getSourceActUid();

		    ObservationVO reflexObs = (ObservationVO) getActVO(NEDSSConstants.
		        OBSERVATION_CLASS_CODE,
		        observationUid,
		        securityObj);

		    if (reflexObs == null)
		    {
		      continue;
		    }
		    else
		    {
		      if(reflexObsVOCollection  == null) reflexObsVOCollection  = new ArrayList<Object> ();
		      reflexObsVOCollection.add(reflexObs);
		    }

		    //Retrieves its associated reflex resulted tests
		    Collection<Object>  reflexRTs = retrieveReflexRTs(reflexObs.
		        getTheActRelationshipDTCollection(), securityObj);
		    if (reflexRTs == null || reflexRTs.size() < 0)
		    {
		      continue;
		    }
		    reflexObsVOCollection.addAll(reflexRTs);
		  }
		}
		return reflexObsVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveReflexObservations: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private Collection<Object>  retrieveReflexRTs(Collection<Object> actRelColl,
                                       NBSSecurityObj securityObj)
  {
    try {
		Collection<Object>  reflexRTCollection  = null;

		for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
		{
		  ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

		  if (actRelDT == null)
		  {
		    continue;
		  }

		  String typeCd = actRelDT.getTypeCd();
		  String sourceClassCd = actRelDT.getSourceClassCd();
		  String targetClassCd = actRelDT.getTargetClassCd();
		  String recordStatusCd = actRelDT.getRecordStatusCd();

		  //If reflex resulted test observation...
		  if (typeCd != null &&
		      typeCd.equalsIgnoreCase(NEDSSConstants.ACT110_TYP_CD)
		      && sourceClassCd != null &&
		      sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && targetClassCd != null &&
		      targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
		      && recordStatusCd != null &&
		      recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
		  {
		    Long observationUid = actRelDT.getSourceActUid();

		    ObservationVO reflexObs = (ObservationVO) getActVO(NEDSSConstants.
		        OBSERVATION_CLASS_CODE,
		        observationUid,
		        securityObj);

		    if (reflexObs == null)
		    {
		      continue;
		    }
		    if(reflexRTCollection  == null) reflexRTCollection  = new ArrayList<Object> ();
		    reflexRTCollection.add(reflexObs);
		  }
		}
		return reflexRTCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.retrieveReflexRTs: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  //#######################################################################################/
  //END: getLabResultProxy(Long observationUID, NBSSecurityObj securityObj)
  //#######################################################################################/

  /**
   * @roseuid 3CEA3999030D
   * @J2EE_METHOD  --  setMorbidityProxy
   */
  public Collection<Object>  setMorbidityProxy(MorbidityProxyVO morbidityProxyVO,
                                      NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {

    try {
		NNDActivityLogDT nndActivityLogDT = null;
		//saving MorbidityProxyVO before updating auto resend notifications
		List<Object> returnVal = (ArrayList<Object> )this.
		    setMorbidityProxyWithoutNotificationAutoResend(morbidityProxyVO,
		    nbsSecurityObj);
		NNDMessageSenderHelper nndMessageSenderHelper = NNDMessageSenderHelper.
		    getInstance();


		         try{
		         //update auto resend notifications
		  if(morbidityProxyVO.getAssociatedNotificationInd() == true)              	 

			  nndMessageSenderHelper.updateAutoResendNotificationsAsync(
		      morbidityProxyVO, nbsSecurityObj);
		         }
		         catch(Exception e)
		         {
		  nndActivityLogDT = new  NNDActivityLogDT();
		  nndActivityLogDT.setErrorMessageTxt(e.toString());
		  Collection<ObservationVO>  observationCollection  = morbidityProxyVO.getTheObservationVOCollection();
		  ObservationVO obsVO = ObservationUtils.findObservationByCode(observationCollection, NEDSSConstants.MORBIDITY_REPORT);
		  String localId = obsVO.getTheObservationDT().getLocalId();
		  if (localId!=null)
		  {
		    nndActivityLogDT.setLocalId(localId);
		  }
		  else
		    nndActivityLogDT.setLocalId("N/A");
		  //catch & store auto resend notifications exceptions in NNDActivityLog table
		     nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
		  logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync in InterventionProxyEJB");
		  e.printStackTrace();
		         }
		return returnVal;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.setMorbidityProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}

  }

  private Collection<Object>  setMorbidityProxyWithoutNotificationAutoResend(
      MorbidityProxyVO morbidityProxyVO,
      NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {
    //Before doing anything
    checkMethodArgs(morbidityProxyVO, nbsSecurityObj);

    //Check permission to proceed
    checkPermissionToSetProxy(morbidityProxyVO, nbsSecurityObj, false);

    String morbidityReportFlag = "";
    EntityController entityController = null;
    ActController actController = null;
    ObservationVO observationVO = null;
    ObservationDT observationDT = null;
   
    List<Object> returnVal = null;
    Long returnedUid = null;
    Long actualUid = null;
    PlaceVO placeVO = null;
    NonPersonLivingSubjectVO nonPersonLivingSubjectVO = null;
    OrganizationVO organizationVO = null;
    PersonVO personVO = null;
    MaterialVO materialVO = null;
    EntityGroupVO entityGroupVO = null;
    ActRelationshipDT actRelationshipDT = null;
    ParticipationDT participationDT = null;
    RoleDT roleDT = null;
    PublicHealthCaseVO publicHealthCaseVO = null;
    NotificationVO notificationVO = null;
    ClinicalDocumentVO clinicalDocumentVO = null;
    ReferralVO referralVO = null;
    InterventionVO interventionVO = null;
    PatientEncounterVO patientEncounterVO = null;
   Iterator<Object>  anIterator = null;
    ParticipationDAOImpl participationDAOImpl = null;
    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
    Long falsePublicHealthCaseUid = null;

    try
    {
      actController = getActControllerRemoteInterface();
      entityController = getEntityControllerRemoteInterface();

      Long falseUid = null;
      Long realUid = null;

      //Process notification collection
      if (morbidityProxyVO.getTheNotificationVOCollection() != null)
      {

        for (anIterator = morbidityProxyVO.getTheNotificationVOCollection()
             .iterator(); anIterator.hasNext(); )
        {
          notificationVO = (NotificationVO) anIterator.next();

          if (notificationVO.isItNew() ||
              notificationVO.isItDirty())
          {
            falseUid = notificationVO.getTheNotificationDT().getNotificationUid();
            logger.debug("the  falseNotificationUid is" +
                         falseUid);
            realUid = actController.setNotification(notificationVO,
                nbsSecurityObj);
            logger.debug("the  reaNotificationUid is" + realUid);

            if (falseUid.intValue() < 0)
            {
              setFalseToNewActOnly(morbidityProxyVO, falseUid,
                                   realUid);
            }
          }
        }
      }

      //Process public health collection
      if (morbidityProxyVO.getThePublicHealthCaseVOCollection() != null)
      {

        for (anIterator = morbidityProxyVO.getThePublicHealthCaseVOCollection()
             .iterator(); anIterator.hasNext(); )
        {
          publicHealthCaseVO = (PublicHealthCaseVO) anIterator.next();

          if (publicHealthCaseVO.isItNew() ||
              publicHealthCaseVO.isItDirty())
          {
            falseUid = publicHealthCaseVO.getThePublicHealthCaseDT()
                .getPublicHealthCaseUid();
            logger.debug(
                "the  falsepublicHealthCaseUid is" +
                falseUid);
            realUid = actController.setPublicHealthCase(
                publicHealthCaseVO, nbsSecurityObj);
            logger.debug(
                "the  reapublicHealthCaseUid is" + realUid);

            if (falseUid.intValue() < 0)
            {
              setFalseToNewActOnly(morbidityProxyVO, falseUid,
                                   realUid);
            }
          }
        }
      }

      //Process clinical document collection
      if (morbidityProxyVO.getTheClinicalDocumentVOCollection() != null)
      {

        for (anIterator = morbidityProxyVO.getTheClinicalDocumentVOCollection()
             .iterator(); anIterator.hasNext(); )
        {
          clinicalDocumentVO = (ClinicalDocumentVO) anIterator.next();

          if (clinicalDocumentVO.isItNew() ||
              clinicalDocumentVO.isItDirty())
          {
            falseUid = clinicalDocumentVO.getTheClinicalDocumentDT()
                .getClinicalDocumentUid();
            logger.debug(
                "the  falseClinicalDocumentUid is" +
                falseUid);
            realUid = actController.setClinicalDocument(
                clinicalDocumentVO, nbsSecurityObj);
            logger.debug(
                "the  realClinicalDocumentUid is" + realUid);

            if (falseUid.intValue() < 0)
            {
              setFalseToNewActOnly(morbidityProxyVO, falseUid,
                                   realUid);
            }
          }
        }
      }

      //process referral collection
      if (morbidityProxyVO.getTheReferralVOCollection() != null)
      {

        for (anIterator = morbidityProxyVO.getTheReferralVOCollection()
             .iterator(); anIterator.hasNext(); )
        {
          referralVO = (ReferralVO) anIterator.next();

          if (referralVO.isItNew() || referralVO.isItDirty())
          {
            falseUid = referralVO.getTheReferralDT().getReferralUid();
            logger.debug("the  falseReferralUid is" + falseUid);
            realUid = actController.setReferral(referralVO,
                                                nbsSecurityObj);
            logger.debug("the  realReferralUid is" + realUid);

            if (falseUid.intValue() < 0)
            {
              setFalseToNewActOnly(morbidityProxyVO, falseUid,
                                   realUid);
            }
          }
        }
      }

      //Process intervention collection
      processInterventionVOCollection(morbidityProxyVO, nbsSecurityObj);
     
      //Process patient encounter collection
      if (morbidityProxyVO.getThePatientEncounterVOCollection() != null)
      {

        for (anIterator = morbidityProxyVO.getThePatientEncounterVOCollection()
             .iterator(); anIterator.hasNext(); )
        {
          patientEncounterVO = (PatientEncounterVO) anIterator.next();

          if (patientEncounterVO.isItNew() ||
              patientEncounterVO.isItDirty())
          {
            falseUid = patientEncounterVO.getThePatientEncounterDT()
                .getPatientEncounterUid();
            logger.debug(
                "the  falsePatientEncounterUid is" +
                falseUid);
            realUid = actController.setPatientEncounter(
                patientEncounterVO, nbsSecurityObj);
            logger.debug(
                "the  realPatientEncounterUid is" + realUid);

            if (falseUid.intValue() < 0)
            {
              setFalseToNewActOnly(morbidityProxyVO, falseUid,
                                   realUid);
            }
          }
        }
      }

      //Process PersonVOCollection  and adds the patient mpu uid to the return
      Long patientMprUid = processMorbPersonVOCollection(morbidityProxyVO,
          nbsSecurityObj);

      //Processes observationVOCollection
      Map<Object, Object> results = processObservationVOCollection(morbidityProxyVO,
          nbsSecurityObj, false);

      //Adds observation uid and mpr uid to return
      if (returnVal == null)
      {
        returnVal = new ArrayList<Object> ();
      }
      returnVal.add(this.OBS_UID, results.get(this.SETMORB_RETURN_OBS_UID));

      //For ELR update, mpr uid may be not available
      if(patientMprUid == null || patientMprUid.longValue() < 0)
      {
        patientMprUid = new GeneralDAOImpl().findPatientMprUidByObservationUid( (
            Long) results.get(this.SETMORB_RETURN_OBS_UID));
        if(patientMprUid == null) throw new NEDSSSystemException("Expected this observation to be associated with a patient, observation uid = " +
              results.get(this.SETMORB_RETURN_OBS_UID));
      }
      returnVal.add(this.MPR_UID, patientMprUid);

      //Retrieve and return local ids
      Long observationUid = (Long)results.get(this.SETMORB_RETURN_OBS_UID);
      Map<Object, Object> localIds = this.findLocalUidsFor(patientMprUid, observationUid, nbsSecurityObj);
      returnVal.add(this.OBS_LOCAL_UID, localIds.get(NEDSSConstants.SETLAB_RETURN_OBS_LOCAL));
      returnVal.add(this.MPR_LOCAL_UID, localIds.get(NEDSSConstants.SETLAB_RETURN_MPR_LOCAL));

      //Process treatment collection
      processTreatmentVOCollection(morbidityProxyVO, nbsSecurityObj);

      
      if (morbidityProxyVO.getTheParticipationDTCollection() != null)
      {

        Collection<Object>  coll = new ArrayList<Object> ();
        AssocDTInterface assocDTInterface;
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

        for (anIterator = morbidityProxyVO.getTheParticipationDTCollection()
             .iterator(); anIterator.hasNext(); )
        {
          participationDT = (ParticipationDT) anIterator.next();
          assocDTInterface = (AssocDTInterface) prepareVOUtils.prepareAssocDT(
              (AssocDTInterface) participationDT,
              nbsSecurityObj);
          participationDT = (ParticipationDT) assocDTInterface;
          coll.add(participationDT);

          try
          {
            logger.debug(
                "got the participationDT for Mordity Report, and isItDirty:?: " +
                participationDT.isItDirty());
            if (participationDT.isItDelete())
            {
              insertParticipationHistory(participationDT);
            }
            new ParticipationDAOImpl().store(participationDT);
            logger.debug(
                "got the participationDT for Mordity Report, the ACTUID is " +
                participationDT.getActUid());
            logger.debug(
                "got the participationDT for Mordity Report, the subjectEntityUid is " +
                participationDT.getSubjectEntityUid());
          }
          catch (Exception e)
          {
            logger.fatal(nbsSecurityObj.getFullName(),
                         e.getMessage(), e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage());
          }

          logger.debug(
              "\n\n\n\n Before caling morbidityProxyVO.setTheParticipationDTCollection(coll)");
          morbidityProxyVO.setTheParticipationDTCollection(coll);
        }
      }

      if (morbidityProxyVO.getTheActRelationshipDTCollection() != null)
      {

        Collection<Object>  coll = new ArrayList<Object> ();
        AssocDTInterface assocDTInterface;
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

        for (anIterator = morbidityProxyVO.getTheActRelationshipDTCollection()
             .iterator(); anIterator.hasNext(); )
        {
          actRelationshipDT = (ActRelationshipDT) anIterator.next();
          assocDTInterface = (AssocDTInterface) prepareVOUtils.prepareAssocDT(
              (AssocDTInterface) actRelationshipDT,
              nbsSecurityObj);
          actRelationshipDT = (ActRelationshipDT) assocDTInterface;
          coll.add(actRelationshipDT);

          try
          {
            new ActRelationshipDAOImpl().store(actRelationshipDT);
            logger.debug(
                "got the actRelationshipDT for Mordity Report, the  TargetActUid is " +
                actRelationshipDT.getTargetActUid());
            logger.debug(
                "got the actRelationshipDT for Mordity Report, the  SourceActUid is " +
                actRelationshipDT.getSourceActUid());
          }
          catch (Exception e)
          {
            logger.fatal(nbsSecurityObj.getFullName(),
                         e.getMessage(), e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage());
          }

          morbidityProxyVO.setTheActRelationshipDTCollection(coll);
        }
      }

    LDFHelper ldfHelper = LDFHelper.getInstance();
    ldfHelper.setLDFCollection(morbidityProxyVO.getTheStateDefinedFieldDataDTCollection(), morbidityProxyVO.getLdfUids(),
                    NEDSSConstants.MORBREPORT_LDF,null,observationUid,nbsSecurityObj);

    }
    catch (NEDSSConcurrentDataException ex)
    {
      // cntx.setRollbackOnly(); 
      logger.fatal("ObservationProxyEJB.setMorbidityProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access occurred: " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.setMorbidityProxyWithoutNotificationAutoResend: Security Object Name: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    finally
    {
      try
      {
        actController.remove();
        actController.remove();
      }
      catch (RemoveException rex)
      {
        rex.printStackTrace();
      }
    }

    return returnVal;
  } //end of setMorbidityProxyWithoutNotificationAutoResend()

  /**
   * @roseuid 3CEA39990232
   * @J2EE_METHOD  --  getMorbidityProxy
   */
  public MorbidityProxyVO getMorbidityProxy(Long observationUID,
                                            NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException
  {

    // if no permissions - terminate
    if (!nbsSecurityObj.getPermission(
        NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
        NBSOperationLookup.VIEW,
        ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
        ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
    {
      logger.info(
          "securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW) is false");
      throw new NEDSSSystemException("NO PERMISSIONS");
    }

    logger.info(
        "securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW) is true");

    MorbidityProxyVO morbidityProxyVO = new MorbidityProxyVO();
    ObservationVO observationVO = new ObservationVO();
    PlaceVO placeVO = new PlaceVO();
    ArrayList<Object> theParticipationDTCollection  = new ArrayList<Object> ();
    ArrayList<Object> theActRelationshipDTCollection  = new ArrayList<Object> ();
    ArrayList<Object> theObservationDTCollection  = new ArrayList<Object> ();
    ArrayList<Object> thePersonVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> thePlaceVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theOrganizationVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theMaterialVOCollection  = new ArrayList<Object> ();
    ArrayList<ObservationVO> theObservationVOCollection  = new ArrayList<ObservationVO> ();
    ArrayList<Object> theInterventionVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theEntityGroupVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theNonPersonLivingSubjectVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> thePublicHealthCaseVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theNotificationVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theReferralVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> thePatientEncounterVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theClinicalDocumentVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theTreatmentVOCollection  = new ArrayList<Object> ();
    ArrayList<Object> theAttachmentVOCollection  = new ArrayList<Object> ();
    
    ParticipationDT participationDT = new ParticipationDT();
    NedssUtils nedssUtils = new NedssUtils();
    Collection<Object>  coll = new ArrayList<Object> ();
    EntityController entityController = null;
    ActController actController = null;
   Iterator<Object>  anIterator = null;

    try
    {
      actController = getActControllerRemoteInterface();
      entityController = getEntityControllerRemoteInterface();
      observationVO = actController.getObservation(observationUID,
          nbsSecurityObj);
      theAttachmentVOCollection=getAttachment(observationUID, nbsSecurityObj);

      if (!nbsSecurityObj.checkDataAccess(
          (RootDTInterface) observationVO.getTheObservationDT(),
          NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
          NBSOperationLookup.VIEW))
      {
        logger.info(
            "securityObj.checkDataAccess(observationVO.getTheObservationDT(), NBSBOLookup.OBSERVATIONMORBIDITYREPORT) is false");
        throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
      }

      logger.info(
          "securityObj.checkDataAccess(observationVO.getTheObservationDT(), NBSBOLookup.OBSERVATIONMORBIDITYREPORT) is true");

      NBSAuthHelper helper = new NBSAuthHelper();
      observationVO.getTheObservationDT().setAddUserName(helper.getUserName(observationVO.getTheObservationDT().getAddUserId()));
      observationVO.getTheObservationDT().setLastChgUserName(helper.getUserName(observationVO.getTheObservationDT().getLastChgUserId()));

      theObservationVOCollection.add(observationVO);

      //morbidityProxyVO.setTheObservationVOCollection(coll);
      theParticipationDTCollection.addAll(observationVO.
                                          getTheParticipationDTCollection());
      logger.debug(
          "\n\n\n size of theParticipationDTCollection  in getMorbidity =" +
          theParticipationDTCollection.size());
      theActRelationshipDTCollection.addAll(observationVO.
                                            getTheActRelationshipDTCollection());
      logger.debug(
          "\n\n\n size of theActRelationshipDTCollection  in getMorbidity =" +
          theParticipationDTCollection.size());

      String subClassCd;
      Long eUid;
     Iterator<Object>  pIterator = theParticipationDTCollection.iterator();

      //iterate through the participationCollection  for instance of morbidity report
      while (pIterator.hasNext())
      {
        participationDT = (ParticipationDT) pIterator.next();
        eUid = participationDT.getSubjectEntityUid();
        subClassCd = participationDT.getSubjectClassCd();

        //Place
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(NEDSSConstants.PLACE)))
        {
          thePlaceVOCollection.add(entityController.getPlace(eUid,
              nbsSecurityObj));
        }

        //NonPersonLivingSubject
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(
            NEDSSConstants.NONPERSONLIVINGSUBJECT)))
        {
          theNonPersonLivingSubjectVOCollection.add(entityController.
              getNonPersonLivingSubject(
              eUid,
              nbsSecurityObj));
        }

        //Organization
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(NEDSSConstants.ORGANIZATION)))
        {
          theOrganizationVOCollection.add(entityController.getOrganization(
              eUid,
              nbsSecurityObj));
        }

        //Person
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(NEDSSConstants.PERSON)))
        {
          thePersonVOCollection.add(entityController.getPerson(eUid,
              nbsSecurityObj));
        }

        //Material
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(NEDSSConstants.MATERIAL)))
        {
          theMaterialVOCollection.add(entityController.getMaterial(
              eUid, nbsSecurityObj));
        }

        //EntityGroup
        if ( (participationDT.getActUid() != null &&
              participationDT.getActUid().compareTo(observationUID) == 0) &&
            (participationDT.getSubjectClassCd() != null &&
             subClassCd.equalsIgnoreCase(NEDSSConstants.ENTITYGROUP)))
        {
          theEntityGroupVOCollection.add(entityController.getEntityGroup(
              eUid,
              nbsSecurityObj));
        }
      }

      ActRelationshipDT actRelationshipDT = null;
      String sourceClassCd;
      String typeCd;
      Long aUid;
     Iterator<Object>  aIterator = theActRelationshipDTCollection.iterator();
      NBSAuthHelper authHelper = new NBSAuthHelper();

      // iterate through theActRelationshipCollection   for insatance of Morbidity Report
      while (aIterator.hasNext())
      {
        actRelationshipDT = (ActRelationshipDT) aIterator.next();
        aUid = actRelationshipDT.getSourceActUid();
        sourceClassCd = actRelationshipDT.getSourceClassCd();
        typeCd = actRelationshipDT.getTypeCd();

        //Intervention
        if ( ( (actRelationshipDT.getTargetActUid() != null &&
                actRelationshipDT.getTargetActUid().compareTo(
            observationUID) == 0) &&
              (actRelationshipDT.getSourceClassCd() != null &&
               sourceClassCd.equalsIgnoreCase(
            NEDSSConstants.INTERVENTION_CLASS_CODE))))
        {
          theInterventionVOCollection.add(actController.getIntervention(
              aUid,
              nbsSecurityObj));
        }

        //ClinicalDocument
        if ( ( (actRelationshipDT.getTargetActUid() != null &&
                actRelationshipDT.getTargetActUid().compareTo(
            observationUID) == 0) &&
              (actRelationshipDT.getSourceClassCd() != null &&
               sourceClassCd.equalsIgnoreCase(
            NEDSSConstants.CLINICAL_DOCUMENT_CLASS_CODE))))
        {
          theClinicalDocumentVOCollection.add(actController.getClinicalDocument(
              aUid,
              nbsSecurityObj));
        }

        // PatientEncounter
        if ( ( (actRelationshipDT.getTargetActUid() != null &&
                actRelationshipDT.getTargetActUid().compareTo(
            observationUID) == 0) &&
              (actRelationshipDT.getSourceClassCd() != null &&
               sourceClassCd.equalsIgnoreCase(
            NEDSSConstants.PATIENT_ENCOUNTER_CLASS_CODE))))
        {
          thePatientEncounterVOCollection.add(actController.getPatientEncounter(
              aUid,
              nbsSecurityObj));
        }

        // Referral
        if ( ( (actRelationshipDT.getTargetActUid() != null &&
                actRelationshipDT.getTargetActUid().compareTo(
            observationUID) == 0) &&
              (actRelationshipDT.getSourceClassCd() != null &&
               sourceClassCd.equalsIgnoreCase(
            NEDSSConstants.REFERRAL_CLASS_CODE))))
        {
          theReferralVOCollection.add(actController.getReferral(aUid,
              nbsSecurityObj));
        }

        // Observations - morb report questions
                if ((sourceClassCd != null
                     && sourceClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE))
                    && (typeCd != null &&
                            typeCd.equalsIgnoreCase(
                                    NEDSSConstants.MORBIDITY_FORM_Q)))
                {
                  theObservationVOCollection.add(actController.getObservation(
                                                      aUid,
                                                      nbsSecurityObj));
                }


                // Comments
                if ((sourceClassCd != null && sourceClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE))
                    && (typeCd != null & typeCd.equalsIgnoreCase("APND")))
                {

                  ObservationVO commentObservationVO = actController.getObservation(
                                                aUid,
                                                nbsSecurityObj);
                                                    theObservationVOCollection.add(commentObservationVO);

                                                  Collection<Object>  commentResTestColl = commentObservationVO.getTheActRelationshipDTCollection();
                        if (commentObservationVO != null && commentResTestColl != null)
                                {

                                 Iterator<Object>  rtIter = commentResTestColl.iterator();
                                  while (rtIter.hasNext())
                                  {
                                    ActRelationshipDT ucrActRelationshipDT =
                                        (ActRelationshipDT)rtIter.next();
                                    Long ucrObsUid = ucrActRelationshipDT.getSourceActUid();
                                    Long ucrActUid = ucrActRelationshipDT.getTargetActUid();
                                    String ucrSourceClassCd = ucrActRelationshipDT.getSourceClassCd();
                                    String ucrTypeCd = ucrActRelationshipDT.getTypeCd();
                                    String ucrRecordStatusCd = ucrActRelationshipDT.getRecordStatusCd();
                                    String ucrTargetClassCd = ucrActRelationshipDT.getTargetClassCd();

                                    if(ucrActUid != null && ucrActUid.compareTo(aUid) == 0
                                       && ucrTypeCd != null && ucrTypeCd.equals(NEDSSConstants.ACT110_TYP_CD)//"COMP"
                                       && ucrSourceClassCd != null && ucrSourceClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
                                       && ucrTargetClassCd != null && ucrTargetClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
                                       && ucrRecordStatusCd != null && ucrRecordStatusCd.equals(NEDSSConstants.ACTIVE))
                                    {
                                      ObservationVO aucResultV0 = actController.getObservation(ucrObsUid, nbsSecurityObj);

                                      //BB - civil0012298 - Retrieve User Name to be displayed instead of ID
                                      aucResultV0.getTheObservationDT().setAddUserName(authHelper.getUserName(aucResultV0.getTheObservationDT().getAddUserId()));
                                      theObservationVOCollection.add(aucResultV0);
                                    }
                                  }
                                }
                              }//end code that adds add user comment

                // Observations - morb report ordered tests
                if ((sourceClassCd != null && sourceClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE))
                    && (typeCd != null &&
                            typeCd.equalsIgnoreCase(
                                    NEDSSConstants.LAB_REPORT)))
                {

                    ObservationVO orderedTestObservationVO = actController.getObservation(
                                                                aUid,
                                                                nbsSecurityObj);
                    theObservationVOCollection.add(orderedTestObservationVO);

                    Collection<Object>  actCollection  = orderedTestObservationVO.getTheActRelationshipDTCollection();
                    if (orderedTestObservationVO != null &&
                        actCollection  != null)
                    {
                        if(actCollection  == null || actCollection.size() < 1)//at least one active
                          throw new NEDSSSystemException("Expeceted at least one ACTIVE resulted test for this ordered test, OT uid is: " + aUid);

                       Iterator<Object>  anOTIterator = actCollection.iterator();

                        while (anOTIterator.hasNext())
                        {
                            ActRelationshipDT otActRelationshipDT =
                                    (ActRelationshipDT)anOTIterator.next();
                            Long rtObsUid = otActRelationshipDT.getSourceActUid();
                            Long otActUid = otActRelationshipDT.getTargetActUid();
                            String rtClassCd = otActRelationshipDT.getSourceClassCd();
                            String rtTypeCd = otActRelationshipDT.getTypeCd();
                            String relRecordStatusCd = otActRelationshipDT.getRecordStatusCd();
                            String otClassCd = otActRelationshipDT.getTargetClassCd();

                            if(otActUid != null && otActUid.compareTo(aUid) == 0
                               && rtTypeCd != null && rtTypeCd.equals(NEDSSConstants.ACT110_TYP_CD)//"COMP"
                               && rtClassCd != null && rtClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
                               && otClassCd != null && otClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
                               && relRecordStatusCd != null && relRecordStatusCd.equals(NEDSSConstants.ACTIVE))
                            {

                              theObservationVOCollection.add(actController.getObservation(rtObsUid,
                                                                                        nbsSecurityObj));
                            }
                        }
                    }
                }

                // Public health cases
                if (sourceClassCd != null
                     && sourceClassCd.equals(NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE)
                    )
                {
                  thePublicHealthCaseVOCollection.add(actController.getPublicHealthCase(
                                                      aUid,
                                                      nbsSecurityObj));
                }

                // Treatments
                if ((sourceClassCd != null
                     && sourceClassCd.equals(NEDSSConstants.TREATMENT_CLASS_CODE))
                    && (typeCd != null &&
                            typeCd.equalsIgnoreCase(
                                    NEDSSConstants.TREATMENT_TO_MORB_TYPE)))
                {
                  theTreatmentVOCollection.add(actController.getTreatment(
                                                      aUid,
                                                      nbsSecurityObj));
                }


      }

      //Populate the morbidityProxyVO with the returned Collections
      morbidityProxyVO.setTheActRelationshipDTCollection(
          theActRelationshipDTCollection);
      morbidityProxyVO.setTheParticipationDTCollection(
          theParticipationDTCollection);
      morbidityProxyVO.setThePlaceVOCollection(thePlaceVOCollection);
      morbidityProxyVO.setTheNonPersonLivingSubjectVOCollection(
          theNonPersonLivingSubjectVOCollection);
      morbidityProxyVO.setTheOrganizationVOCollection(
          theOrganizationVOCollection);
      morbidityProxyVO.setThePersonVOCollection(thePersonVOCollection);
      morbidityProxyVO.setTheMaterialVOCollection(
          theMaterialVOCollection);
      morbidityProxyVO.setTheInterventionVOCollection(
          theInterventionVOCollection);
      morbidityProxyVO.setTheEntityGroupVOCollection(
          theEntityGroupVOCollection);
      morbidityProxyVO.setTheClinicalDocumentVOCollection(
          theClinicalDocumentVOCollection);
      morbidityProxyVO.setTheReferralVOCollection(
          theReferralVOCollection);
      morbidityProxyVO.setThePatientEncounterVOCollection(
          thePatientEncounterVOCollection);
      morbidityProxyVO.setTheObservationVOCollection(
          theObservationVOCollection);
      morbidityProxyVO.setThePublicHealthCaseVOCollection(thePublicHealthCaseVOCollection);
      morbidityProxyVO.setTheTreatmentVOCollection(theTreatmentVOCollection);
      morbidityProxyVO.setTheAttachmentVOCollection(theAttachmentVOCollection);

      boolean exists = NNDMessageSenderHelper.getInstance().
          checkForExistingNotifications(morbidityProxyVO).booleanValue();
      morbidityProxyVO.setAssociatedNotificationInd(exists);

      //retrieve LDF data
      LDFHelper ldfHelper = LDFHelper.getInstance();
      ArrayList<Object> ldfList = (ArrayList<Object> )ldfHelper.getLDFCollection(observationUID,null,nbsSecurityObj);

      if (ldfList != null)
      {
        morbidityProxyVO.setTheStateDefinedFieldDataDTCollection(ldfList);
      }
      try{
          ActRelationshipDAOImpl actRelationshipDAOImpl = new
       	          ActRelationshipDAOImpl();
       	      Collection<Object>  col = actRelationshipDAOImpl.loadSource(observationUID, NEDSSConstants.MORBIDITY_REPORT);
       	      if(col!=null && col.size()>0)
       	    	morbidityProxyVO.setAssociatedInvInd(true);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    }
    catch (Exception ne)
    {
     logger.fatal("ObservationProxyEJB.getMorbidityProxy: " + ne.getMessage(), ne);
      throw new javax.ejb.EJBException(ne.getMessage(), ne);
    }

    return morbidityProxyVO;
  }

  private void setFalseToNewActOnly(MorbidityProxyVO morbidityProxyVO,
                                    Long falseUid, Long actualUid)
  {

   try {
	Iterator<Object>  anIterator = null;
	    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
	    ActRelationshipDT actRelationshipDT = null;
	    Collection<Object>  actRelationShipColl = (ArrayList<Object> ) morbidityProxyVO.
	        getTheActRelationshipDTCollection();
	
	    if (actRelationShipColl != null)
	    {
	
	      for (anIterator = actRelationShipColl.iterator();
	           anIterator.hasNext(); )
	      {
	        actRelationshipDT = (ActRelationshipDT) anIterator.next();
	
	        if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0)
	        {
	          actRelationshipDT.setTargetActUid(actualUid);
	
	        }
	        if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0)
	        {
	          actRelationshipDT.setSourceActUid(actualUid);
	
	        }
	        logger.debug(
	            "\n\n\nfalseUid in ActRelationShip " +
	            falseUid.toString());
	        logger.debug(
	            "\n\n\nACTUid in ActRelationShip " +
	            actRelationshipDT.getTargetActUid());
	        logger.debug(
	            "\n\n\nSourceUID in ActRelationShip " +
	            actRelationshipDT.getSourceActUid());
	        logger.debug(
	            "\n\n\n(actRelationshipDT.getActUid() == falseUid)1  " +
	            actRelationshipDT.getTargetActUid().equals(falseUid.toString()));
	        logger.debug(
	            "\n\n\n(actRelationshipDT.getActUid() equals falseUid)1  " +
	            actRelationshipDT.getTargetActUid().equals(falseUid.toString()));
	        logger.debug(
	            "\n\n\n(actRelationshipDT.getActUid() compared to falseUid)1  " +
	            actRelationshipDT.getSourceActUid().compareTo(falseUid));
	      }
	
	      logger.debug(
	          "actRelationshipDT.getActUid(actualUid)2" +
	          actRelationshipDT.getTargetActUid());
	      logger.debug(
	          "actRelationshipDT.getActUid(SourceActUid)2" +
	          actRelationshipDT.getSourceActUid());
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("ObservationProxyEJB.setFalseToNewActOnly: " + e.getMessage(), e);
	throw new javax.ejb.EJBException(e.getMessage(), e);
}
  }

  private void setFalseToNewParticipationOnly(MorbidityProxyVO morbidityProxyVO,
                                              Long falseUid, Long actualUid)
  {

   try {
	Iterator<Object>  anIterator = null;
	    ParticipationDAOImpl participationDAOImpl = null;
	    ParticipationDT participationDT = null;
	    Collection<Object>  participationColl = (ArrayList<Object> ) morbidityProxyVO.
	        getTheParticipationDTCollection();
	
	    if (participationColl != null)
	    {
	
	      for (anIterator = participationColl.iterator();
	           anIterator.hasNext(); )
	      {
	        participationDT = (ParticipationDT) anIterator.next();
	        logger.debug(
	            "(participationDT.getActUid() comparedTo falseUid)1" +
	            (participationDT.getActUid().compareTo(falseUid)));
	
	        if (participationDT.getActUid().compareTo(falseUid) == 0)
	        {
	          participationDT.setActUid(actualUid);
	
	        }
	        if (participationDT.getSubjectEntityUid().compareTo(falseUid) == 0)
	        {
	          participationDT.setSubjectEntityUid(actualUid);
	        }
	      }
	
	      logger.debug(
	          "participationDT.getSubjectEntityUid()" +
	          participationDT.getSubjectEntityUid());
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("ObservationProxyEJB.setFalseToNewParticipationOnly: " + e.getMessage(), e);
	throw new javax.ejb.EJBException(e.getMessage(), e);
}
  }

  /**
   * @roseuid 3C9221DA005D
   * @J2EE_METHOD  --  getGenericObservationProxy
   */
  public GenericObservationProxyVO getGenericObservationProxy(Long
      observationUID,
      NBSSecurityObj securityObj) throws javax.ejb.EJBException
  {
    logger.info(securityObj.getFullName(),
                "Start getGenericObservationProxy()...");

    //The GenericObservationProxyVO properties
    ObservationVO theObservationVO = null;
    PersonVO thePersonVO = null;
    EntityController entityControllerRef = null;
    ActController actControllerRef = null;
    GenericObservationProxyVO genObsProxyVO = new GenericObservationProxyVO();

    try
    {

      if (observationUID == null)
      {
        logger.error(securityObj.getFullName(),
                     "Observation id provided is not valid (null value):" +
                     " no observation can be retrieved");
      }
      else
      {

        /**
         * Obtains entity controller and act controller references
         */
    	  
        actControllerRef = getActControllerRemoteInterface();
        entityControllerRef = getEntityControllerRemoteInterface();


        /**
         * Gets the observation value object for the observation uid passed in
         */
        theObservationVO = (ObservationVO) getActVO(
            NEDSSConstants.OBSERVATION_CLASS_CODE,
            observationUID, securityObj);
        genObsProxyVO.setTheObservationVO(theObservationVO);
        logger.info(securityObj.getFullName(),
                    "Got an observation for the observation proxy, uid = " +
                    observationUID);

        /**
         * Gets the person associated with this observation where
         * ObservationVO.ParticipationDT.type_cd = SubjOfGenObs
         */
        Long obsPersonUID = getUid(theObservationVO.
                                   getTheParticipationDTCollection(),
                                   NEDSSConstants.ENTITY_UID_LIST_TYPE,
                                   NEDSSConstants.PERSON,
                                   NEDSSConstants.
            OBSERVATION_GEN_OBS_SUBJECT_TYPE_CODE_PART_DT,
                                   NEDSSConstants.ACTRELATION_CLASS_CD,
                                   NEDSSConstants.RECORD_STATUS_ACTIVE,
                                   false);
        logger.info(securityObj.getFullName(),
                    "Found a person uid for the observation proxy,  obs uid = " +
                    observationUID + "; person uid = " +
                    obsPersonUID);

        if (obsPersonUID == null)
        {
          logger.warn(securityObj.getFullName(),
                      "No person associated with this generic observation!");
        }
        else
        {
          thePersonVO = (PersonVO) getEntityVO(NEDSSConstants.PERSON,
                                               obsPersonUID,
                                               securityObj);
          genObsProxyVO.setThePersonVO(thePersonVO);
          logger.info(securityObj.getFullName(),
                      "Got a person for the observation proxy,  person uid = " +
                      obsPersonUID);
        }
      }
    }
    catch (Exception ex)
    {
      //  cntx.setRollbackOnly();
      logger.fatal("ObservationProxyEJB.getGenericObservationProxy: " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }

    logger.info(securityObj.getFullName(),
                "Done getGenericObservationProxy() for " +
                observationUID + " - return: " +
                genObsProxyVO.toString());

    return genObsProxyVO;
  }

  /**
   * @roseuid 3C9221DA01F4
       /**
    * @J2EE_METHOD  --  setGenericObservationProxy
    */
   public Long setGenericObservationProxy(GenericObservationProxyVO
                                          genericObservationProxyVO,
                                          NBSSecurityObj securityObj) throws
       javax.ejb.EJBException, NEDSSConcurrentDataException
   {
     logger.info(securityObj.getFullName(),
                 "Starts setGenericObservationProxy()...");

     Long obsUID = null;
     EntityController entityControllerRef = null;
     ActController actControllerRef = null;
     ObservationVO theObservationVO = genericObservationProxyVO.
         getTheObservationVO();
     ParticipationDT theParticipationDT = genericObservationProxyVO.
         getTheParticipationDT();

     try
     {

       if (theObservationVO == null)
       {
         logger.error(securityObj.getFullName(),
                      "No generic observation is provided, observation = " +
                      theObservationVO);

         return obsUID;
       }
       else
       {

         /**
          * Obtains entity controller and act controller references
          */

         actControllerRef = getActControllerRemoteInterface();
         entityControllerRef = getEntityControllerRemoteInterface();

         /**
          * Sets(either updates or creates) the ObservationVO object
          * (and its ParticipationDT, if new) associated with the
          * GenericObservationProxyVO passed in.
          */
         if (theObservationVO.isItNew() ||
             theObservationVO.isItDirty())
         {
           obsUID = actControllerRef.setObservation(theObservationVO,
               securityObj);
           logger.info(securityObj.getFullName(),
                       "Obtained an observation uid, value = " +
                       obsUID);

           if (obsUID == null)
           {
             logger.error(securityObj.getFullName(),
                          "Returned uid from act controller is " +
                          obsUID);
           }
           else
           {

             if (theObservationVO.isItNew())
             {
               logger.info(securityObj.getFullName(),
                           "The observation is: " +
                           theObservationVO.isItNew() +
                           ", must create a new participation record.");

               ParticipationDAOImpl participationDAOImpl =
                   (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(
                   JNDINames.ACT_PARTICIPATION_DAO_CLASS);
               theParticipationDT.setActUid(obsUID);
               participationDAOImpl.create(theParticipationDT.
                                           getSubjectEntityUid().longValue(),
                                           theParticipationDT);
               logger.info(securityObj.getFullName(),
                   "Done creating the new participation record for the new obs.");
             }
           }
         }
       }
     }
     catch (Exception ex)
     {
       //cntx.setRollbackOnly();
       logger.fatal("ObservationProxyEJB.setGenericObservationProxy: " + ex.getMessage(), ex);
       throw new javax.ejb.EJBException(ex.getMessage(), ex);
     }

     logger.info(securityObj.getFullName(),
                 "Done setGenericObservationProxy() - return: void.");

     return obsUID;
   }

  /**
   * @roseuid 3C9221DA038A
   * @J2EE_METHOD  --  getEntityVOList
   */
  /**
   * A method used to get a collection of entity value objects through EntityControllerEJB
   */
  private java.util.Collection<Object>  getEntityVOList(String entityType,
                                               java.util.Collection<Object>  listOfUid,
                                               NBSSecurityObj securityObj) throws
      java.lang.Exception
  {

    try {
		Collection<Object>  entityVOColl = new ArrayList<Object> ();
   Iterator<Object>  uidIter = listOfUid.iterator();

		while (uidIter.hasNext())
		{

		  Long anEntityUid = (Long) uidIter.next();
		  entityVOColl.add(getEntityVO(entityType, anEntityUid, securityObj));
		}

		return entityVOColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getEntityVOList: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  /**
   * @roseuid 3C9221DB0222
   * @J2EE_METHOD  --  getEntityVO
   */
  /**
       * A method used to get a single entity value object through EntityControllerEJB
   */
  private Object getEntityVO(String entityType, Long anUid,
                             NBSSecurityObj securityObj)
  //throws java.lang.Exception
  {

    Object obj = null;
    EntityController entityControllerRef = null;

    try
    {
      entityControllerRef = getEntityControllerRemoteInterface();

      if (entityType.equalsIgnoreCase(NEDSSConstants.PERSON))
      {
        obj = entityControllerRef.getPerson(anUid, securityObj);
      }
      else if (entityType.equalsIgnoreCase(NEDSSConstants.ORGANIZATION))
      {
        obj = entityControllerRef.getOrganization(anUid, securityObj);
      }
      else if (entityType.equalsIgnoreCase(NEDSSConstants.MATERIAL))
      {
        obj = entityControllerRef.getMaterial(anUid, securityObj);
      }
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.getEntityVO: Error while retrieving a " + entityType +
                             " value object. " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
    finally
    {
      try
      {
        entityControllerRef.remove();
        entityControllerRef = null;
      }
      catch (Exception ex)
      {
        logger.fatal("ObservationProxyEJB.getEntityVO: " + ex.getMessage(), ex);
        throw new javax.ejb.EJBException(ex.getMessage(), ex);
      }
    }

    return obj;
  }

  /**
   * @roseuid 3C9221DC000F
   * @J2EE_METHOD  --  getActVOList
   */
  /**
   * A method used to get a collection of activity value objects through ActControllerEJB
   */
  private java.util.Collection<Object>  getActVOList(String actType,
                                            java.util.Collection<Object>  listOfUid,
                                            NBSSecurityObj securityObj) throws
      java.lang.Exception
  {

    try {
		Collection<Object>  actVOColl = new ArrayList<Object> ();
		ActController actControllerRef = getActControllerRemoteInterface();
   Iterator<Object>  uidIter = listOfUid.iterator();

		while (uidIter.hasNext())
		{

		  Long anActUid = (Long) uidIter.next();
		  actVOColl.add(getActVO(actType, anActUid, securityObj));
		}

		return actVOColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getActVOList: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  /**
   * @roseuid 3C9221DC0290
   * @J2EE_METHOD  --  getActVO
   */
  /**
       * A method used to get a single activity value object through ActControllerEJB
   */
  private Object getActVO(String actType, Long anUid,
                          NBSSecurityObj securityObj)
  //throws java.lang.Exception
  {

    Object obj = null;

    try
    {

      if (anUid != null)
      {

        ActController actControllerRef = getActControllerRemoteInterface();

        if (actType.equalsIgnoreCase(
            NEDSSConstants.INTERVENTION_CLASS_CODE))
        {
          obj = actControllerRef.getIntervention(anUid, securityObj);
        }
        else if (actType.equalsIgnoreCase(
            NEDSSConstants.OBSERVATION_CLASS_CODE))
        {
          obj = actControllerRef.getObservation(anUid, securityObj);
        }
      }
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.getActVO: Error while retrieving a " + actType +
                             " value object. " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }

    return obj;
  }

  /**
   * @roseuid 3C9221DD009C
   * @J2EE_METHOD  --  getUidList
   */
  /**
       * A method used to get a collection of uids from a collection of associations,
   * either Participation or ActRelationship: if ActRelationship, the uidClassCd should
   *  be null
   */
  private java.util.Collection<Object>  getUidList(java.util.Collection
                                          associationDTColl,
                                          String uidListType,
                                          String uidClassCd,
                                          String uidTypeCd, String uidTarCd,
                                          String uidRecordStatusCd,
                                          boolean act) throws java.lang.
      Exception
  {

    Collection<Object>  uidColl = new ArrayList<Object> ();
    Long anUid = null;

    try
    {

     Iterator<?>  assocIter = associationDTColl.iterator();

      while (assocIter.hasNext())
      {

        if (!act)
        {

          ParticipationDT partDT = (ParticipationDT) assocIter.next();

          if ( ( (partDT.getSubjectClassCd() != null &&
                  partDT.getSubjectClassCd().equalsIgnoreCase(
              uidClassCd)) &&
                (partDT.getTypeCd() != null &&
                 partDT.getTypeCd().equalsIgnoreCase(uidTypeCd)) &&
                (partDT.getActClassCd() != null &&
                 partDT.getActClassCd().equalsIgnoreCase(
              uidTarCd)) &&
                (partDT.getRecordStatusCd() != null &&
                 partDT.getRecordStatusCd().equalsIgnoreCase(
              uidRecordStatusCd))))
          {
            anUid = partDT.getSubjectEntityUid();
            uidColl.add(anUid);
          }
        }
        else
        {

          ActRelationshipDT actRelDT = (ActRelationshipDT) assocIter.next();

          if ( ( (actRelDT.getSourceClassCd() != null &&
                  actRelDT.getSourceClassCd().equalsIgnoreCase(
              uidClassCd)) &&
                (actRelDT.getTypeCd() != null &&
                 actRelDT.getTypeCd().equalsIgnoreCase(
              uidTypeCd)) &&
                (actRelDT.getTargetClassCd() != null &&
                 actRelDT.getTargetClassCd().equalsIgnoreCase(
              uidTarCd)) &&
                (actRelDT.getRecordStatusCd() != null &&
                 actRelDT.getRecordStatusCd().equalsIgnoreCase(
              uidRecordStatusCd))))
          {

            if (uidListType.equalsIgnoreCase(
                NEDSSConstants.ACT_UID_LIST_TYPE))
            {
              anUid = actRelDT.getTargetActUid();
              uidColl.add(anUid);
            }
            else if (uidListType.equalsIgnoreCase(
                NEDSSConstants.SOURCE_ACT_UID_LIST_TYPE))
            {
              anUid = actRelDT.getSourceActUid();
              uidColl.add(anUid);
            }
          }
        }
      }
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.getUidList: Error while retrieving a " + uidListType +
                          " uid. " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }

    return uidColl;
  }

  /**
   * @roseuid 3C9221DD031C
   * @J2EE_METHOD  --  getUid
   */
  /**
   * A method used to get a single uid from a collection of associations,
   * either Participation or ActRelationship: if ActRelationship, the uidClassCd should
   *  be null
   */
  private Long getUid(java.util.Collection<Object>  associationDTColl,
                      String uidListType, String uidClassCd,
                      String uidTypeCd, String uidActClassCd,
                      String uidRecordStatusCd, boolean act)

  {

    Long anUid = null;

    try
    {

     Iterator<Object>  assocIter = associationDTColl.iterator();

      while (assocIter.hasNext())
      {

        if (!act)
        {

          ParticipationDT partDT = (ParticipationDT) assocIter.next();

          if ( ( (partDT.getSubjectClassCd() != null &&
                  partDT.getSubjectClassCd().equalsIgnoreCase(
              uidClassCd)) &&
                (partDT.getTypeCd() != null &&
                 partDT.getTypeCd().equalsIgnoreCase(uidTypeCd)) &&
                (partDT.getActClassCd() != null &&
                 partDT.getActClassCd().equalsIgnoreCase(
              uidActClassCd)) &&
                (partDT.getRecordStatusCd() != null &&
                 partDT.getRecordStatusCd().equalsIgnoreCase(
              uidRecordStatusCd))))
          {
            anUid = partDT.getSubjectEntityUid();
          }
        }
        else
        {

          ActRelationshipDT actRelDT = (ActRelationshipDT) assocIter.next();

          if ( ( (actRelDT.getSourceClassCd() != null &&
                  actRelDT.getSourceClassCd().equalsIgnoreCase(
              uidClassCd)) &&
                (actRelDT.getTypeCd() != null &&
                 actRelDT.getTypeCd().equalsIgnoreCase(
              uidTypeCd)) &&
                (actRelDT.getTargetClassCd() != null &&
                 actRelDT.getTargetClassCd().equalsIgnoreCase(
              uidActClassCd)) &&
                (actRelDT.getRecordStatusCd() != null &&
                 actRelDT.getRecordStatusCd().equalsIgnoreCase(
              uidRecordStatusCd))))
          {

            if (uidListType.equalsIgnoreCase(
                NEDSSConstants.ACT_UID_LIST_TYPE))
            {
              anUid = actRelDT.getTargetActUid();
            }
            else if (uidListType.equalsIgnoreCase(
                NEDSSConstants.SOURCE_ACT_UID_LIST_TYPE))
            {
              anUid = actRelDT.getSourceActUid();
            }
          }
        }
      }
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.getUid: Error while retrieving a " + uidListType +
                                 " uid. " + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }

    return anUid;
  }

  /**
   * @roseuid 3C9221DE0177
   * @J2EE_METHOD  --  setUid
   */
  private java.util.Collection<Object>  setUid(java.util.Collection<Object>  assocColl,
                                      java.util.Collection<Object>  newColl,
                                      Long tempUID, Long newUID,
                                      String objectCode)
  {

    try {
		Collection<Object>  returnColl = null;
		logger.debug("************temp UID = " + tempUID);
		logger.debug("************new UID = " + newUID);

		//Updates, and adds the participationDT to the new collection
		if (objectCode.equalsIgnoreCase("participation"))
		{

		  for (Iterator<Object> partIt = assocColl.iterator(); partIt.hasNext(); )
		  {

		    ParticipationDT partDT = (ParticipationDT) partIt.next();
		    logger.info(
		        "100 new Participation collection in setUid method: " +
		        newColl.size());

		    if (newColl.contains(partDT))
		    {
		      newColl.remove(partDT);
		    }

		    logger.info(
		        "200 new Participation collection in setUid method: " +
		        newColl.size());

		    if (partDT.getSubjectEntityUid().compareTo(tempUID) == 0)
		    {
		      partDT.setSubjectEntityUid(newUID);
		    }

		    if (partDT.getActUid().compareTo(tempUID) == 0)
		    {
		      partDT.setActUid(newUID);
		    }

		    newColl.add(partDT);
		  }

		  logger.info(
		      "300 new Participation collection in setUid method: " +
		      newColl.size());
		  returnColl = newColl;
		}

		//Updates, and adds the roleDT to the new collection
		if (objectCode.equalsIgnoreCase("role"))
		{

		  for (Iterator<Object> roleIt = assocColl.iterator(); roleIt.hasNext(); )
		  {

		    RoleDT roleDT = (RoleDT) roleIt.next();

		    if (newColl.contains(roleDT))
		    {
		      newColl.remove(roleDT);
		    }

		    if (roleDT.getSubjectEntityUid().compareTo(tempUID) == 0)
		    {
		      roleDT.setSubjectEntityUid(newUID);
		    }

		    if (roleDT.getScopingEntityUid().compareTo(tempUID) == 0)
		    {
		      roleDT.setScopingEntityUid(newUID);
		    }

		    newColl.add(roleDT);
		  }

		  returnColl = newColl;
		}

		//Updates, and adds the actRelationshipDT to the new collection
		if (objectCode.equalsIgnoreCase("actrelation"))
		{

		  for (Iterator<Object> actRelIt = assocColl.iterator(); actRelIt.hasNext(); )
		  {

		    ActRelationshipDT actRelDT = (ActRelationshipDT) actRelIt.next();

		    if (newColl.contains(actRelDT))
		    {
		      newColl.remove(actRelDT);
		    }

		    if (actRelDT.getTargetActUid().compareTo(tempUID) == 0)
		    {
		      actRelDT.setTargetActUid(newUID);
		    }

		    if (actRelDT.getSourceActUid().compareTo(tempUID) == 0)
		    {
		      actRelDT.setSourceActUid(newUID);
		    }

		    newColl.add(actRelDT);
		  }

		  returnColl = newColl;
		}

		return returnColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.setUid: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

 
  private EntityController getEntityControllerRemoteInterface() throws
      EJBException
  {

    EntityController entityController = null;
    try
    {

      logger.debug("YOU ARE IN THE getRemoteInterface() method");
      NedssUtils nu = new NedssUtils();
      Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
      EntityControllerHome entityControllerHome = (EntityControllerHome) javax.
          rmi.PortableRemoteObject.narrow(obj, EntityControllerHome.class);
      entityController = entityControllerHome.create();
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.getEntityControllerRemoteInterface: " + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return entityController;
  } 

  private ActController getActControllerRemoteInterface() throws EJBException
  {

    ActController actController = null;
    try
    {

      logger.debug("YOU ARE IN THE getRemoteInterface() method");
      NedssUtils nu = new NedssUtils();
      Object obj = nu.lookupBean(JNDINames.ActControllerEJB);
      ActControllerHome actControllerHome = (ActControllerHome) javax.rmi.
          PortableRemoteObject.narrow(obj, ActControllerHome.class);
      actController = actControllerHome.create();
    }
    catch (Exception e)
    {
    	logger.fatal("ObservationProxyEJB.getActControllerRemoteInterface: " + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return actController;
  } 

  private InvestigationProxy getInvestigationProxyRemoteInterface() throws
      EJBException
  {

    InvestigationProxy proxy = null;
    try
    {

      NedssUtils nu = new NedssUtils();
      Object obj = nu.lookupBean(JNDINames.INVESTIGATION_PROXY_EJB);
      InvestigationProxyHome home = (InvestigationProxyHome) javax.rmi.
          PortableRemoteObject.narrow(obj, InvestigationProxyHome.class);
      proxy = home.create();

    }
    catch (Exception e)
    {
    	logger.fatal("ObservationProxyEJB.getInvestigationProxyRemoteInterface: " + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return proxy;
  }

  /**
   * @roseuid 3C922206009C
   * @J2EE_METHOD  --  deleteGenericObservationProxy
   */
  public boolean deleteGenericObservationProxy(Long observationUid,
                                               NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    logger.info("deleting Generic Observation-----------------");
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    boolean bCount = false;
    try
    {

      boolean check1 = securityObj.getPermission(
          NBSBOLookup.OBSERVATIONGENERICOBSERVATION,
          NBSOperationLookup.DELETE);

      if (check1 == false)
      {
        logger.error(
            "don't have permission to delete Generic Observation");
        throw new NEDSSSystemException(
            "don't have permission to delete a Generic Observation");
      }
      else
      {
        dbConnection = getConnection();
        int resultCount = 0;
        preparedStmt = dbConnection.prepareStatement(
            WumSqlQuery.COUNT_OBSERVATION_GENERIC);

        int i = 1;
        preparedStmt.setLong(i++, observationUid.longValue());
        preparedStmt.setString(i++, NEDSSConstants.GENERIC_OBS);
        preparedStmt.setString(i++, NEDSSConstants.STATUS_ACTIVE);
        resultCount = preparedStmt.executeUpdate();

        if (resultCount > 0)
        {

          return false;
        }
        else
        {
          bCount = true;
        }

        if (bCount == true)
        {

          NedssUtils nedssUtils = new NedssUtils();
          Object theLookedUpObject;
          logger.debug("get reference for ActControllerEJB");
          theLookedUpObject = nedssUtils.lookupBean(
              JNDINames.ActControllerEJB);

          ActControllerHome acHome = (ActControllerHome) PortableRemoteObject.
              narrow(
              theLookedUpObject,
              ActControllerHome.class);
          ActController actController = acHome.create();
          ObservationDT observationDT = actController.getObservationInfo(
              observationUid,
              securityObj);
          observationDT.setItDelete(false);
          observationDT.setItDirty(true);

          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          String BUSINESS_OBJ_LOOKUP_NAME = NBSBOLookup.
              OBSERVATIONGENERICOBSERVATION;
          String BUSINESS_TRIGGER_CD = NEDSSConstants.OBS_GEN_DEL;
          String TABLE_NAME = DataTables.OBSERVATION_TABLE;
          String MODULE_CD = NEDSSConstants.BASE;
          RootDTInterface rootDTInterface = prepareVOUtils.prepareVO(
              observationDT,
              BUSINESS_OBJ_LOOKUP_NAME,
              BUSINESS_TRIGGER_CD,
              TABLE_NAME,
              MODULE_CD,
              securityObj);
          actController.setObservationInfo(
              (ObservationDT) rootDTInterface, securityObj);

          return true;
        }
        else
        {

          return false;
        }
      } //main else
    }
    catch (SQLException sqlex)
    {
      logger.fatal("ObservationProxyEJB.deleteGenericObservationProxy: SQLException: " + sqlex.getMessage(), sqlex);
      throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
    }
    catch (Exception e)
    {
    	
      logger.fatal("ObservationProxyEJB.deleteGenericObservationProxy: Exception: " + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
      
    }
    finally
    {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } 

  /**
   * @roseuid 3C9222BF00FA
   * @J2EE_METHOD  --  deleteLabResultProxy
   */

  public boolean deleteLabResultProxy(Long observationUid,
                                      NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      javax.ejb.FinderException,
      NEDSSSystemException,
      NEDSSConcurrentDataException
  {
    logger.debug("deleting LabReport");

    NedssUtils nedssUtils = new NedssUtils();
    logger.debug("get reference for ActControllerEJB");

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    boolean bCount = false;
    ResultSet result = null;
    try
    {
      dbConnection = getConnection();
      Object theLookedUpObject;
      theLookedUpObject = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);

      ActControllerHome acHome = (ActControllerHome) PortableRemoteObject.
          narrow(
          theLookedUpObject,
          ActControllerHome.class);
      ActController actController = acHome.create();
      ObservationDT observationDT = actController.getObservationInfo(
          observationUid, nbsSecurityObj);

      boolean check1 = nbsSecurityObj.getPermission(
          NBSBOLookup.OBSERVATIONLABREPORT,
          NBSOperationLookup.DELETE,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      if (check1 == false)
      {
        logger.error("don't have permission to delete LabReport");
        throw new NEDSSSystemException(
            "don't have permission to delete LabReport");
      }
      else
      {
        int resultCount = 0;
        String sql = null;
        sql = WumSqlQuery.ASSOC_LAB_COUNT;
        logger.debug("The Query for count is:" + sql);
        preparedStmt = dbConnection.prepareStatement(sql);

        preparedStmt.setLong(1, observationUid.longValue());
        result = preparedStmt.executeQuery();
        if (result.next())
        {
          resultCount = result.getInt(1);
          logger.debug("Count for this Query is :" + resultCount);
        }
        if (resultCount > 0)
        {
          logger.debug("The returned value is false");
          return false;

        }
        else
        {
          bCount = true;
        }

      }

      if (bCount == true)
      {
        observationDT.setItDirty(true);
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        String BUSINESS_OBJ_LOOKUP_NAME = NBSBOLookup.OBSERVATIONLABREPORT;
        String BUSINESS_TRIGGER_CD = NEDSSConstants.OBS_LAB_DEL;
        String TABLE_NAME = DataTables.OBSERVATION_TABLE;
        String MODULE_CD = NEDSSConstants.BASE;
        logger.debug("In side the if to prepareVO");
        RootDTInterface rootDTInterface = prepareVOUtils.prepareVO(
            observationDT,
            BUSINESS_OBJ_LOOKUP_NAME,
            BUSINESS_TRIGGER_CD,
            TABLE_NAME,
            MODULE_CD,
            nbsSecurityObj);
        actController.setObservationInfo(
            (ObservationDT) rootDTInterface, nbsSecurityObj);

//          Logically delete the associated Patient revision
            Long personUid = this.getPersonUidOfObservation(observationUid, NEDSSConstants.PAR110_TYP_CD);
            String typeCode = "";
            typeCode=NEDSSConstants.PAR110_TYP_CD;
            Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
            EntityControllerHome ecHome = (EntityControllerHome)PortableRemoteObject.narrow(lookedUpObj,EntityControllerHome.class);
            logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
            EntityController entityController = ecHome.create();

            //retrieve PersonVO that represents the Patient Revision for this Vaccination by passing in personUid
            PersonVO personVO = entityController.getPatientRevision(personUid,nbsSecurityObj);
            personVO.setItDirty(true);
            personVO.getThePersonDT().setItDirty(true);
            String businessTriggerCd = NEDSSConstants.PAT_DEL;
            personUid = entityController.setPatientRevision(personVO, businessTriggerCd, nbsSecurityObj);
            logger.debug("PersonUid value returned by ObservationProxyEJB(): " + personUid);
            
            AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
            answerRootDAOImpl.logDelete(observationDT);
            
        return true;
      }
      else
      {
        return false;
      }
    }
    catch (SQLException sqlex)
    {
      logger.fatal("ObservationProxyEJB.deleteLabResultProxy: SQLException: " + sqlex.getMessage(), sqlex);
      throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.deleteLabResultProxy: Exception: " + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    finally
    {
      closeResultSet(result);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  }

  public boolean deleteMorbidityProxy(Long observationUid,
                                      NBSSecurityObj securityObj) throws
      NEDSSConcurrentDataException
  {
    logger.debug("deleting Morbidity Observation");

    NedssUtils nedssUtils = new NedssUtils();
    logger.debug("get reference for ActControllerEJB");

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    boolean bCount = false;
    ResultSet result = null;
    try
    {
      dbConnection = getConnection();
      Object theLookedUpObject;
      theLookedUpObject = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);

      ActControllerHome acHome = (ActControllerHome) PortableRemoteObject.
          narrow(
          theLookedUpObject,
          ActControllerHome.class);
      ActController actController = acHome.create();
      ObservationDT observationDT = actController.getObservationInfo(
          observationUid, securityObj);
      observationDT.setItDirty(true);
      boolean check1 = securityObj.getPermission(
          NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
          NBSOperationLookup.DELETE,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      if (check1 == false)
      {
        logger.error("don't have permission to delete Morbidity");
        throw new NEDSSSystemException(
            "don't have permission to delete Morbidity");
      }
      else
      {
        int resultCount = 0;
        String sql = null;
       sql = WumSqlQuery.ASSOC_MORB_COUNT;

        logger.debug("The Query for count is:" + sql);
        preparedStmt = dbConnection.prepareStatement(sql);

        preparedStmt.setLong(1, observationUid.longValue());
        result = preparedStmt.executeQuery();
        if (result.next())
        {
          resultCount = result.getInt(1);
          logger.debug("Count for this Query is :" + resultCount);
        }
        if (resultCount > 0)
        {
          logger.debug("The returned value is false");
          return false;

        }
        else
        {
          logger.debug("The bCount is = " + bCount);
          bCount = true;
        }

      }

      if (bCount == true)
      {

        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        String BUSINESS_OBJ_LOOKUP_NAME = NBSBOLookup.
            OBSERVATIONMORBIDITYREPORT;
        String BUSINESS_TRIGGER_CD = NEDSSConstants.OBS_MORB_DEL;
        String TABLE_NAME = DataTables.OBSERVATION_TABLE;
        String MODULE_CD = NEDSSConstants.BASE;
        logger.debug("In side the if to prepareVO");
        RootDTInterface rootDTInterface = prepareVOUtils.prepareVO(
            observationDT,
            BUSINESS_OBJ_LOOKUP_NAME,
            BUSINESS_TRIGGER_CD,
            TABLE_NAME,
            MODULE_CD,
            securityObj);
        actController.setObservationInfo(
            (ObservationDT) rootDTInterface, securityObj);
        //Logically delete the associated Patient revision
        Long personUid = this.getPersonUidOfObservation(observationUid, NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT);
        String typeCode = "";
        typeCode=NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT;
        Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
        EntityControllerHome ecHome = (EntityControllerHome)PortableRemoteObject.narrow(lookedUpObj,EntityControllerHome.class);
        logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
        EntityController entityController = ecHome.create();

        //retrieve PersonVO that represents the Patient Revision for this Vaccination by passing in personUid
        PersonVO personVO = entityController.getPatientRevision(personUid,securityObj);
        personVO.setItDirty(true);
        personVO.getThePersonDT().setItDirty(true);
        String businessTriggerCd = NEDSSConstants.PAT_DEL;
        personUid = entityController.setPatientRevision(personVO, businessTriggerCd, securityObj);
        logger.debug("PersonUid value returned by ObservationProxyEJB(): " + personUid);
        
        return true;
      }
      else
      {
        return false;
      }
    }
    catch (SQLException sqlex)
    {
      logger.fatal("ObservationProxyEJB.deleteMorbidityProxy: SQLException: " + sqlex.getMessage(), sqlex);
      throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
    }
    catch (Exception e)
    {
    	logger.fatal("ObservationProxyEJB.deleteMorbidityProxy: Exception: " + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    finally
    {
      closeResultSet(result);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  }

  /**
   * @roseuid 3D0F37B40129
   * @J2EE_METHOD  --  setMorbidityProxyWithAutoAssoc
   */
  public Collection<Object>  setMorbidityProxyWithAutoAssoc(MorbidityProxyVO
      morbidityProxyVO,
      Long investigationUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
      NEDSSSystemException,
      CreateException,
      RemoteException,
      NEDSSConcurrentDataException
  {
    List<Object> results = null;
    Long observationUid = null;
    MorbReportSummaryVO morbReportSummaryVO = new MorbReportSummaryVO();

    try
    {
      results = (List<Object>) setMorbidityProxy(morbidityProxyVO,
                                         nbsSecurityObj);
      observationUid = (Long) results.get(this.OBS_UID);
      morbReportSummaryVO.setItTouched(true);
      morbReportSummaryVO.setItAssociated(true);
      morbReportSummaryVO.setObservationUid(observationUid);
      morbReportSummaryVO.setActivityFromTime(new Timestamp(new java.util.Date().getTime()));

      Collection<Object>  morbReportSummaryVOColl = new ArrayList<Object> ();
      morbReportSummaryVOColl.add(morbReportSummaryVO);
      setObservationAssociations(investigationUid,
                                 morbReportSummaryVOColl,
                                 nbsSecurityObj);
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.setMorbidityProxyWithAutoAssoc: Investigation UID = " +
          investigationUid + "MorbidityProxyVO = " +
          morbidityProxyVO.toString() + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return results;
  }

  /**
   * @roseuid 3D0F37B301AA
   * @J2EE_METHOD  --  setLabResultProxyWithAutoAssoc
   */
  public Map<Object, Object> setLabResultProxyWithAutoAssoc(LabResultProxyVO labResultProxyVO,
                                            Long investigationUid,
                                            NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.FinderException,
      javax.ejb.CreateException,
      NEDSSSystemException,
      NEDSSConcurrentDataException
  {
    Map<Object, Object> results = null;
    Long observationUid = null;
    LabReportSummaryVO labReportSummaryVO = new LabReportSummaryVO();

    try
    {
      results = setLabResultProxy(labResultProxyVO,
                                  nbsSecurityObj);
      observationUid = (Long) results.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
      labReportSummaryVO.setItTouched(true);
      labReportSummaryVO.setItAssociated(true);
      labReportSummaryVO.setObservationUid(observationUid);
      labReportSummaryVO.setActivityFromTime(new Timestamp(new java.util.Date().getTime()));
    
      Collection<Object>  labReportSummaryVOColl = new ArrayList<Object> ();
      labReportSummaryVOColl.add(labReportSummaryVO);

      setObservationAssociations(investigationUid, labReportSummaryVOColl, nbsSecurityObj);
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.setLabResultProxyWithAutoAssoc: Investigation UID = " +
          investigationUid + "LabResultProxyVO = " +
          labResultProxyVO.toString() + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }

    return results;
  }
  
	public void setLabInvAssociation(Long labUid,
			Long investigationUid, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, javax.ejb.FinderException,
			javax.ejb.CreateException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		LabReportSummaryVO labReportSummaryVO = new LabReportSummaryVO();

		try {

			labReportSummaryVO.setItTouched(true);
			labReportSummaryVO.setItAssociated(true);
			labReportSummaryVO.setObservationUid(labUid);
			labReportSummaryVO.setActivityFromTime(new Timestamp(
					new java.util.Date().getTime()));
		
			Collection<Object> labReportSummaryVOColl = new ArrayList<Object>();
			labReportSummaryVOColl.add(labReportSummaryVO);

			setObservationAssociations(investigationUid,
					labReportSummaryVOColl, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("ObservationProxyEJB.setLabInvAssociation: Investigation UID = "
					+ investigationUid + "LabUid = " + labUid + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}

  private void setObservationAssociations(Long investigationUid,
                                          Collection<Object>  observationSummaryVOColl,
                                          NBSSecurityObj nbsSecurityObj)
  {
    InvestigationProxy proxy = null;
    try
    {
      proxy = this.getInvestigationProxyRemoteInterface();

      proxy.setAssociations(investigationUid,
                                       observationSummaryVOColl,null, null,null, new Boolean(true),
                                       nbsSecurityObj);
    }
    catch (Exception e)
    {
    	logger.fatal("ObservationProxyEJB.setObservationAssociations: " + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    finally
    {
      try
      {
        proxy.remove();
      }
      catch (Exception ex)
      {
    	  logger.fatal("ObservationProxyEJB.setObservationAssociations: " + ex.getMessage(), ex);
    	  throw new javax.ejb.EJBException(ex.getMessage(), ex);
      }
    }

  }
/**
 *
 * @param observationUID
 * @param newProgramAreaCode: newProgramAreaCode will be null if the call is
 * made from InvestigationProxyEJB. In this case the program area code is retained.
 * @param newJurisdictionCode
 * @param Cascading : The value of Cascading is either CASCADING or NON-CASCADING
 * depending on the origin of its call. In case the value is set CASCADING the
 * transferOwnership is CASCADING from Investigation to associated Labs and Morbs.
 * @param nbsSecurityObj
 * @throws java.rmi.RemoteException
 * @throws javax.ejb.EJBException
 * @throws javax.ejb.CreateException
 * @throws javax.ejb.FinderException
 * @throws NEDSSSystemException
 * @throws NEDSSConcurrentDataException
 */
  public void transferOwnership(Long observationUID,
                                String newProgramAreaCode,
                                String newJurisdictionCode,String Cascading,
                                NBSSecurityObj nbsSecurityObj) throws java.rmi.
      RemoteException,
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      javax.ejb.FinderException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {

    try
    {

      ActController actController = null;
      ObservationDT observationDT = null;
      ObservationDT newObservationDT = null;
      NedssUtils nedssUtils = new NedssUtils();
      PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
      Object object = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object,
          ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);
      actController = acthome.create();
      observationDT = actController.getObservationInfo(observationUID,
          nbsSecurityObj);


	  if (observationDT != null &&
          observationDT.getProgAreaCd() != null &&
          observationDT.getJurisdictionCd() != null &&
          observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.
          LABRESULT_CODE)
          && Cascading.equals(NEDSSConstants.NON_CASCADING))
      {

        if (!nbsSecurityObj.getPermission(
            NBSBOLookup.OBSERVATIONLABREPORT,
            NBSOperationLookup.TRANSFERPERMISSIONS,
            observationDT.getProgAreaCd(),
            observationDT.getJurisdictionCd(),
            observationDT.getSharedInd()))
        {
          logger.debug(
              "no add permissions for transferOwnership - Lab Report");
          throw new NEDSSSystemException(
              "NO ADD PERMISSIONS for transferOwnership - Lab Report ");
        }

        logger.debug(
            "user has add permissions for transferOwnership - Lab Report");
      }
     else if (observationDT != null &&
      observationDT.getProgAreaCd() != null &&
      observationDT.getJurisdictionCd() != null &&
      observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.
      MORBIDITY_CODE)
      && Cascading.equals(NEDSSConstants.NON_CASCADING))
      {

           if (!nbsSecurityObj.getPermission(
            NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
            NBSOperationLookup.TRANSFERPERMISSIONS,
            observationDT.getProgAreaCd(),
            observationDT.getJurisdictionCd(),
            observationDT.getSharedInd()))
        {
          logger.debug(
              "no add permissions for transferOwnership - Morbidity Report");
          throw new NEDSSSystemException(
              "NO ADD PERMISSIONS for transferOwnership - Morbidity Report");
        }

        logger.debug(
            "user has add permissions for transferOwnership - Morbidity Report");
        // * Note: This logic is for ELRs requiring PgmAreas and Jurisdiction assignments which
        // does not contain either programArea or Jurisdiction basically.
      }
      else if (observationDT != null &&
               observationDT.getProgAreaCd() != null &&
               observationDT.getJurisdictionCd() == null &&
               observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.
          MORBIDITY_CODE)
          && Cascading.equals(NEDSSConstants.NON_CASCADING)) {

        if (!nbsSecurityObj.getPermission(
            NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
            NBSOperationLookup.TRANSFERPERMISSIONS,
            observationDT.getProgAreaCd(),
            ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
            observationDT.getSharedInd())) {
          logger.debug(
              "no add permissions for transferOwnership - Morbidity Report");
          throw new NEDSSSystemException(
              "NO ADD PERMISSIONS for transferOwnership - Morbidity Report");
        }

        logger.debug(
            "user has add permissions for transferOwnership - Morbidity Report");
        // * Note: This logic is for ELRs requiring PgmAreas and Jurisdiction assignments which
        // does not contain either programArea or Jurisdiction basically.
  }
  else if (observationDT != null &&
       observationDT.getProgAreaCd() == null &&
       observationDT.getJurisdictionCd() == null &&
       observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.
       MORBIDITY_CODE)
       && Cascading.equals(NEDSSConstants.NON_CASCADING))

      {
        if (!nbsSecurityObj.getPermission(
            NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
            NBSOperationLookup.ASSIGNSECURITY,
            ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
            ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
            observationDT.getSharedInd()))
           {

             logger.debug("no add permissions for transferOwnership - ELR");
             throw new NEDSSSystemException(
                 "NO ADD PERMISSIONS for transferOwnership - ELR ");
           }
           logger.debug("user has add permissions for transferOwnership - ELR");
      }
      else if (observationDT != null &&
               observationDT.getProgAreaCd() == null &&
               observationDT.getJurisdictionCd() == null &&
               observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.
          LABRESULT_CODE)
          && Cascading.equals(NEDSSConstants.NON_CASCADING))
      {

        if (!nbsSecurityObj.getPermission(
            NBSBOLookup.OBSERVATIONLABREPORT,
            NBSOperationLookup.ASSIGNSECURITY,
            ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
            ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
            observationDT.getSharedInd()))
        {

          logger.debug("no add permissions for transferOwnership - ELR");
          throw new NEDSSSystemException(
              "NO ADD PERMISSIONS for transferOwnership - ELR ");
        }
        logger.debug("user has add permissions for transferOwnership - ELR");
      }
      else if (observationDT != null &&
              (observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.LABRESULT_CODE) ||
                      observationDT.getCtrlCdDisplayForm().equals(NEDSSConstants.MORBIDITY_CODE))
              && Cascading.equals(NEDSSConstants.CASCADING)){

    	  newProgramAreaCode =  observationDT.getProgAreaCd();
      }
      else
      { //should be logger.error
        logger.error(
            "Either the observationDT is null or the getCtrlCdDisplayForm is incorrect");
      }

      if (observationDT.getCtrlCdDisplayForm().equals(
          NEDSSConstants.LABRESULT_CODE) || observationDT.getCtrlCdDisplayForm().equals(
                  NEDSSConstants.MORBIDITY_CODE))
      {
    	  if(newProgramAreaCode!=null && !newProgramAreaCode.isEmpty())
    		  observationDT.setProgAreaCd(newProgramAreaCode);
      }

      if(newJurisdictionCode!=null && !newJurisdictionCode.isEmpty())
    	  observationDT.setJurisdictionCd(newJurisdictionCode);
      observationDT.setItDirty(true);

      if (observationDT != null &&
          observationDT.getCtrlCdDisplayForm().equals(
          NEDSSConstants.LABRESULT_CODE))
      {
        newObservationDT = (ObservationDT) prepareVOUtils.prepareVO(
            observationDT,
            NBSBOLookup.OBSERVATIONLABREPORT,
            NEDSSConstants.OBS_LAB_EDIT,
            DataTables.OBSERVATION_TABLE,
            NEDSSConstants.BASE,
            nbsSecurityObj);
      }
      else if (observationDT != null &&
               observationDT.getCtrlCdDisplayForm().equals(
          NEDSSConstants.MORBIDITY_CODE))
      {
        newObservationDT = (ObservationDT) prepareVOUtils.prepareVO(
            observationDT,
            NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
            NEDSSConstants.OBS_MORB_EDIT,
            DataTables.OBSERVATION_TABLE,
            NEDSSConstants.BASE,
            nbsSecurityObj);
      }

      actController.setObservationInfo(newObservationDT, nbsSecurityObj);
    }
    catch (NEDSSConcurrentDataException ex)
    {
      logger.fatal("ObservationProxyEJB.transferOwnership: Concurrent access is not allowed: " + ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.transferOwnership: nbsSecurity Object Name: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }

    ///end of transferOwnership Method
  }

	public boolean processObservation(Long observationUid,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			javax.ejb.CreateException, java.rmi.RemoteException,
			javax.ejb.FinderException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		try {
			return processObservationWithProcessingDecision(observationUid,
					null, null, nbsSecurityObj);
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("ObservationProxyEJB.processObservation: Concurrent access is not allowed: " + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.fatal("ObservationProxyEJB.processObservation: nbsSecurity Object: " + nbsSecurityObj.getFullName() + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		}

	}
  

  /**
   * @roseuid 3D2077BC0394
   * @J2EE_METHOD  --  processObservation
   */
  public boolean processObservationWithProcessingDecision(Long observationUid, String processingDecisionCd, String processingDecisionTxt,
                                  NBSSecurityObj nbsSecurityObj) throws javax.
      ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      javax.ejb.FinderException,
      NEDSSSystemException,
      NEDSSConcurrentDataException
  {

    NedssUtils nedssUtils = new NedssUtils();
    PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

    try
    {

      Object object = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object,
          ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);

      ActController actController = acthome.create();
      ObservationVO observationVO = actController.getObservation(observationUid, nbsSecurityObj);

      ObservationDT observationDT = observationVO.getTheObservationDT();
      observationDT.setProcessingDecisionCd(processingDecisionCd);
      if(processingDecisionTxt!=null && !processingDecisionTxt.isEmpty())
    	  observationDT.setProcessingDecisionTxt(processingDecisionTxt);
      
      String observationType = observationDT.getCtrlCdDisplayForm();
      boolean accessPermission = false;
      String businessTrigger = null;
      String businessObjLookupName = null;

      if(observationType.equalsIgnoreCase(NEDSSConstants.
          LABRESULT_CODE)){
        accessPermission = nbsSecurityObj.getPermission(
          NBSBOLookup.OBSERVATIONLABREPORT,
          NBSOperationLookup.VIEW,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      businessTrigger = NEDSSConstants.OBS_LAB_PROCESS;
      businessObjLookupName = NBSBOLookup.OBSERVATIONLABREPORT;
      logger.info("Attempting to set the Lab Report record_status from UNPROCESSED to PROCESSED");

      }
      else if (observationType.equalsIgnoreCase(NEDSSConstants.MORBIDITY_CODE)){
        accessPermission = nbsSecurityObj.getPermission(
          NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
          NBSOperationLookup.VIEW,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      businessTrigger = NEDSSConstants.OBS_MORB_PROCESS;
       businessObjLookupName = NBSBOLookup.OBSERVATIONMORBIDITYREPORT;
       logger.info("Attempting to set the Morbidity Report record_status from UNPROCESSED to PROCESSED");
      }
      else{
        throw new NEDSSSystemException(nbsSecurityObj.getFullName() +
            "This is not a Lab Report OR a Morbidity Report! MarkAsReviewed only applies to Lab Report or Morbidity Report ");

      }


      if (accessPermission == false)
      {
        throw new NEDSSSystemException(nbsSecurityObj.getFullName() +
            " doesnt have access to process observation! ");
      }

      if ((observationDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.
          OBS_UNPROCESSED)))
      {

        logger.debug("ready to process labreport !!");
        logger.debug(" -observationDT.getCtrlCdDisplayForm() -- " +
                     observationDT.getCtrlCdDisplayForm());
        logger.debug(" -observationDT.getRecordStatusCd(): " +
                     observationDT.getRecordStatusCd());
        observationDT.setItNew(false);
        observationDT.setItDirty(true);
        logger.debug("Is obsDT dirty ?" + observationDT.isItDirty());

        RootDTInterface rootDTInterface = prepareVOUtils.prepareVO(
            observationDT,
            businessObjLookupName,
            businessTrigger,
            DataTables.OBSERVATION_TABLE,
            NEDSSConstants.BASE,
            nbsSecurityObj);
        actController.setObservationInfo( (ObservationDT) rootDTInterface,
                                         nbsSecurityObj);
        return true;

      }
      else
      {
        logger.debug("fields of observation are not right!! ");
        logger.debug("observationDT.getCtrlCdDisplayForm() -- " +
                     observationDT.getCtrlCdDisplayForm());
        logger.debug("observationDT.getRecordStatusCd(): " +
                     observationDT.getRecordStatusCd());
        return false;
      }

    }
    catch (NEDSSConcurrentDataException ex)
    {
      logger.fatal("ObservationProxyEJB.processObservationWithProcessingDecision: Concurrent access is not allowed: " + ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.processObservationWithProcessingDecision: nbsSecurity Object: " + nbsSecurityObj.getFullName() + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
  } 
  
  /**
   * @roseuid 3D2077BC0394
   * @J2EE_METHOD  --  processObservation
   */
  public boolean unProcessObservation(Long observationUid,
                                  NBSSecurityObj nbsSecurityObj) throws javax.
      ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      javax.ejb.FinderException,
      NEDSSSystemException,
      NEDSSConcurrentDataException
  {

    NedssUtils nedssUtils = new NedssUtils();
    PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

    try
    {

      Object object = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object,
          ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);

      ActController actController = acthome.create();
      ObservationVO observationVO = actController.getObservation(observationUid, nbsSecurityObj);
     
      ObservationDT observationDT = observationVO.getTheObservationDT();
      observationDT.setProcessingDecisionCd(null);
      observationDT.setProcessingDecisionTxt(null);
      
      String observationType = observationDT.getCtrlCdDisplayForm();
      boolean accessPermission = false;
      String businessTrigger = null;
      String businessObjLookupName = null;

      if(observationType.equalsIgnoreCase(NEDSSConstants.
          LABRESULT_CODE)){
        accessPermission = nbsSecurityObj.getPermission(
          NBSBOLookup.OBSERVATIONLABREPORT,
          NBSOperationLookup.VIEW,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      businessTrigger = NEDSSConstants.OBS_LAB_UNPROCESS;
      businessObjLookupName = NBSBOLookup.OBSERVATIONLABREPORT;
      logger.info("Attempting to set the Lab Report record_status from PROCESSED to UNPROCESSED");

      }
      else if (observationType.equalsIgnoreCase(NEDSSConstants.MORBIDITY_CODE)){
        accessPermission = nbsSecurityObj.getPermission(
          NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
          NBSOperationLookup.VIEW,
          observationDT.getProgAreaCd(),
          observationDT.getJurisdictionCd(),
          observationDT.getSharedInd());

      businessTrigger = NEDSSConstants.OBS_MORB_UNPROCESS;
       businessObjLookupName = NBSBOLookup.OBSERVATIONMORBIDITYREPORT;
       logger.info("Attempting to set the Morbidity Report record_status from PROCESSED to UNPROCESSED");
      }
      else{
        throw new NEDSSSystemException(nbsSecurityObj.getFullName() +
            "This is not a Lab Report OR a Morbidity Report! ClearMarkAsReviewed only applies to Lab Report or Morbidity Report ");

      }


      if (accessPermission == false)
      {
        throw new NEDSSSystemException(nbsSecurityObj.getFullName() +
            " doesnt have access to unprocess observation! ");
      }

      if (observationDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_PROCESSED))
      {

        logger.debug("ready to unprocess labreport !!");
        logger.debug(" -observationDT.getCtrlCdDisplayForm() -- " +
                     observationDT.getCtrlCdDisplayForm());
        logger.debug(" -observationDT.getRecordStatusCd(): " +
                     observationDT.getRecordStatusCd());
        observationDT.setItNew(false);
        observationDT.setItDirty(true);
        logger.debug("Is obsDT dirty ?" + observationDT.isItDirty());

        RootDTInterface rootDTInterface = prepareVOUtils.prepareVO(
            observationDT,
            businessObjLookupName,
            businessTrigger,
            DataTables.OBSERVATION_TABLE,
            NEDSSConstants.BASE,
            nbsSecurityObj);
        actController.setObservationInfo( (ObservationDT) rootDTInterface,
                                         nbsSecurityObj);
        return true;

      }
      else
      {
        logger.debug("fields of observation are not right!! ");
        logger.debug("observationDT.getCtrlCdDisplayForm() -- " +
                     observationDT.getCtrlCdDisplayForm());
        logger.debug("observationDT.getRecordStatusCd(): " +
                     observationDT.getRecordStatusCd());
        return false;
      }

    }
    catch (NEDSSConcurrentDataException ex)
    {
      logger.fatal("ObservationProxyEJB.unProcessObservation: Concurrent access is not allowed: " + ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.unProcessObservation: nbsSecurity Object: " + nbsSecurityObj.getFullName() + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }
  } //processLabReport
  
  

  /**
   * @roseuid
   * @J2EE_METHOD -- getObservationLocalID
   */
  public String getObservationLocalID(Long observationUID,
                                      NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      javax.ejb.FinderException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {

    try
    {

      ActController actController = null;
      actController = getActControllerRemoteInterface();

      ObservationVO observationVO = actController.getObservation(
          observationUID,
          nbsSecurityObj);
      ObservationDT observationDT = observationVO.getTheObservationDT();

      if (observationDT.getLocalId() != null)
      {

        return observationDT.getLocalId();
      }
    }
    catch (Exception ex)
    {
      logger.fatal("ObservationProxyEJB.getObservationLocalID: nbsSecurity Object: " + nbsSecurityObj.getFullName() + ex.getMessage(), ex);
      throw new javax.ejb.EJBException(ex.getMessage(), ex);
    }

    return null;
  } 

  private void insertParticipationHistory(ParticipationDT dt) throws
      NEDSSSystemException
  {

    try {
		if (dt != null)
		{

		  ParticipationHistoryManager man = new ParticipationHistoryManager(dt.
		      getSubjectEntityUid().longValue(),
		      dt.getActUid().longValue(),
		      dt.getTypeCd());
		  man.store(dt);
		}
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.insertParticipationHistory: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);

	}
  }

  private void insertActRelationshipHistory(ActRelationshipDT dt) throws
      NEDSSSystemException
  {
    try {
		if (dt != null)
		{
		  ActRelationshipHistoryManager mn = new ActRelationshipHistoryManager(dt.
		      getTargetActUid().longValue(), dt.getSourceActUid().longValue(),
		      dt.getTypeCd());
		  mn.store(dt);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.insertActRelationshipHistory: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  /**
   * @method      :   getRootObservationVO
   * @params      :   LabResultProxyVO
   * @returnType  :   ObservationVO
   */
  private ObservationVO getRootObservationVO(AbstractVO proxy) throws
      NEDSSSystemException
  {
    try {
		Collection<ObservationVO>  obsColl = null;
		boolean isLabReport = false;

		if (proxy instanceof LabResultProxyVO)
		{
		  obsColl = ( (LabResultProxyVO) proxy).getTheObservationVOCollection();
		  isLabReport = true;
		}
		if (proxy instanceof MorbidityProxyVO)
		{
		  obsColl = ( (MorbidityProxyVO) proxy).getTheObservationVOCollection();
		}

		ObservationVO rootVO = getRootObservationVO(obsColl, isLabReport);

		if( rootVO != null)
		  return rootVO;
		throw new IllegalArgumentException(
		    "Expected the proxyVO containing a root observation(e.g., ordered test)");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getRootObservationVO: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  } 

  private ObservationVO getRootObservationVO(Collection<ObservationVO> obsColl)
  {
    return getRootObservationVO(obsColl, false);
  }
  private ObservationVO getRootObservationVO(Collection<ObservationVO> obsColl, boolean isLabReport)
  {
    try {
		if(obsColl == null) return null;

		logger.debug("ObservationVOCollection  is not null");
		 Iterator<ObservationVO>  iterator = null;
		  for (iterator = obsColl.iterator(); iterator.hasNext(); )
		  {
		    ObservationVO observationVO = (ObservationVO) iterator.next();
		    if (observationVO.getTheObservationDT() != null &&
		        ( (observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null &&
		           observationVO.getTheObservationDT().getCtrlCdDisplayForm().
		           equalsIgnoreCase(NEDSSConstants.LAB_CTRLCD_DISPLAY))
		         ||
		         (observationVO.getTheObservationDT().getObsDomainCdSt1() != null &&
		          observationVO.getTheObservationDT().getObsDomainCdSt1().
		          equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD) && isLabReport)
		         ||
		         (observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null &&
		          observationVO.getTheObservationDT().getCtrlCdDisplayForm().
		          equalsIgnoreCase(NEDSSConstants.MOB_CTRLCD_DISPLAY))))
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
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getRootObservationVO: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

  private ObservationDT getRootDT(AbstractVO proxyVO)
  {
    try {
		ObservationVO rootVO = getRootObservationVO(proxyVO);
		if (rootVO != null)
		{
		  return rootVO.getTheObservationDT();
		}
		return null;
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getRootDT: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

  /**
   * This method checks for the negative uid value for any ACT & ENTITY DT then compare them
   * with respective negative values in ActRelationshipDT and ParticipationDT as received from
   * the investigationProxyVO(determined in the addInvestigation method).
   * As it has also got the actualUID (determined in the addInvestigation method) it replaces them accordingly.
   */
  private void setFalseToNew(AbstractVO proxyVO, Long falseUid, Long actualUid)
  {
   try {
	Iterator<Object>  anIterator = null;
	
	    ParticipationDT participationDT = null;
	    ActRelationshipDT actRelationshipDT = null;
	    RoleDT roleDT = null;
	
	    Collection<Object>  participationColl = null;
	    Collection<Object>  actRelationShipColl = null;
	    Collection<Object>  roleColl = null;
	
	    if (proxyVO instanceof LabResultProxyVO)
	    {
	      participationColl = (ArrayList<Object> ) ( (LabResultProxyVO) proxyVO).
	          getTheParticipationDTCollection();
	      actRelationShipColl = (ArrayList<Object> ) ( (LabResultProxyVO) proxyVO).
	          getTheActRelationshipDTCollection();
	      roleColl = (ArrayList<Object> ) ( (LabResultProxyVO) proxyVO).
	          getTheRoleDTCollection();
	    }
	    if (proxyVO instanceof MorbidityProxyVO)
	    {
	      participationColl = (ArrayList<Object> ) ( (MorbidityProxyVO) proxyVO).
	          getTheParticipationDTCollection();
	      actRelationShipColl = (ArrayList<Object> ) ( (MorbidityProxyVO) proxyVO).
	          getTheActRelationshipDTCollection();
	      roleColl = (ArrayList<Object> ) ( (MorbidityProxyVO) proxyVO).
	          getTheRoleDTCollection();
	    }
	
	    if (participationColl != null)
	    {
	      for (anIterator = participationColl.iterator(); anIterator.hasNext(); )
	      {
	        participationDT = (ParticipationDT) anIterator.next();
	        if (participationDT != null && falseUid != null)
	        {
	          logger.debug("(participationDT.getAct() comparedTo falseUid)1" +
	                       (participationDT.getActUid().compareTo(falseUid)));
	          if (participationDT.getActUid().compareTo(falseUid) == 0)
	          {
	            participationDT.setActUid(actualUid);
	          }
	          if (participationDT.getSubjectEntityUid().compareTo(falseUid) == 0)
	          {
	            participationDT.setSubjectEntityUid(actualUid);
	          }
	          logger.info("participationDT.getSubjectEntityUid()" +
	                      participationDT.getSubjectEntityUid());
	        }
	      }
	    }
	
	    if (actRelationShipColl != null)
	    {
	      for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext(); )
	      {
	        actRelationshipDT = (ActRelationshipDT) anIterator.next();
	
	        if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0)
	        {
	          actRelationshipDT.setTargetActUid(actualUid);
	        }
	        if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0)
	        {
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
	
	    }
	
	    if (roleColl != null && roleColl.size() != 0)
	    {
	    	Iterator<Object>  roleIterator = null;
	      for (anIterator = roleColl.iterator(); anIterator.hasNext(); )
	      {
	        roleDT = (RoleDT) anIterator.next();
	
	        if (roleDT.getSubjectEntityUid().compareTo(falseUid) == 0)
	        {
	          roleDT.setSubjectEntityUid(actualUid);
	
	        }
	        if (roleDT.getScopingEntityUid() != null)
	        {
	          if (roleDT.getScopingEntityUid().compareTo(falseUid) == 0)
	          {
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
	logger.fatal("ObservationProxyEJB.setFalseToNew: " + e.getMessage(), e);
	throw new javax.ejb.EJBException(e.getMessage(), e);
}

  } 

  public Collection<Object>  findOrderedTestName(String clia, Long labId,
                                        String searchString, String searchType,
                                        int cacheNumber, int fromIndex,
                                        NBSSecurityObj nbsSecurityObj) throws
      EJBException, NEDSSSystemException
  {
    try {
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findOrderedTestName(clia, labId,
		    searchString, searchType, cacheNumber, fromIndex);

		return result;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findOrderedTestName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

	  public Collection<Object>  findDrugTestName(String clia, Long labId,
	          String searchString, String searchType,
	          int cacheNumber, int fromIndex,
	          NBSSecurityObj nbsSecurityObj) throws
	          EJBException, NEDSSSystemException
	  {
		try {
			
		String method = "";//TODO: find out method
		
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findDrugsByName(clia, labId,
		searchString, searchType,method, cacheNumber, fromIndex);
		
		
		
		return result;
		} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findDrugTestName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
		
		}
	}

	  public Collection<Object>  findDrugTestNameOrCode(String clia, Long labId,
	          String searchString, String searchType,
	          int cacheNumber, int fromIndex,
	          NBSSecurityObj nbsSecurityObj) throws
	          EJBException, NEDSSSystemException
	  {
		try {
			
		String method = "";//TODO: find out method
		
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findDrugsByNameOrCode(clia, labId,
		searchString, searchType,method, cacheNumber, fromIndex);
		
		
		
		return result;
		} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findDrugTestNameOrCOde: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
		
		}
	}
	  
  public Collection<Object>  findResultedTestName(String clia, Long labId,
                                         String searchString, String searchType,
                                         int cacheNumber, int fromIndex,
                                         NBSSecurityObj nbsSecurityObj) throws
      EJBException, NEDSSSystemException
  {
    try {
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findResultedTestName(clia, labId,
		    searchString, searchType, cacheNumber, fromIndex);

		return result;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findResultedTestName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }
  
  /**
   * getCodeSetGroupIdFromQuestionIdentifier: returns the code_set_group_id based on the question identifier. This is used from the new
   * UI component (Coded with Search) to determine what JSP to open: resulted test, organism, etc.
   * @param questionIdentifier
   * @return
   */
  public String getCodeSetGroupIdFromQuestionIdentifier(String questionIdentifier, NBSSecurityObj nbsSecurityObj)  throws NEDSSSystemException, RemoteException{
	  
	  
	  try {
		  ObservationProcessor obsProcessor = new ObservationProcessor();
		  
		  String  codeSetGroupId = obsProcessor.findCodeSetGroupIdFromQuestionIdentifier(questionIdentifier);
	
		  //codeSetGroupId="6310";//Temporal
		 
		  return codeSetGroupId;
	  
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findResultedTestName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }
  
  
	public Collection<Object> findLabResultedTestName(String clia, Long labId,
			String searchString, String searchType,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSSystemException {
		try {
			ObservationProcessor obsProcessor = new ObservationProcessor();
			Collection<Object> result = obsProcessor.findLabResultedTestName(clia,
					labId, searchString, searchType);

			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.findLabResultedTestName: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	public Collection<Object> findLabCodedResult(String clia, Long labId,
			String searchString, String searchType,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSSystemException {
		try {
			ObservationProcessor obsProcessor = new ObservationProcessor();
			Collection<Object> result = obsProcessor.findLabCodedResult(clia,
					labId, searchString, searchType);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.findLabCodedResult: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	public Collection<Object> findLabCodedResultByCode(String clia, Long labId,
			String searchCode, String searchType,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSSystemException {
		try {
			ObservationProcessor obsProcessor = new ObservationProcessor();
			Collection<Object> result = obsProcessor.findLabCodedResultByCode(clia,
					labId, searchCode, searchType);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.findLabCodedResultByCode: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
  
	public Collection<Object> findLabResultedTestByCode(String clia,
			Long labId, String searchCode, String searchType,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSSystemException {
		try {
			ObservationProcessor obsProcessor = new ObservationProcessor();
			Collection<Object> result = obsProcessor.findLabResultedTestByCode(
					clia, labId, searchCode, searchType);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.findLabResultedTestByCode: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

  public Collection<Object>  findDrugsByName(String clia, Long labId,
                                    String searchString, String searchType,
                                    String method, int cacheNumber,
                                    int fromIndex,
                                    NBSSecurityObj nbsSecurityObj) throws
      EJBException, NEDSSSystemException
  {
    try {
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findDrugsByName(clia, labId, searchString,
		    searchType, method, cacheNumber, fromIndex);
		return result;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findDrugsByName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  public Collection<Object>  findOrganismsByName(String clia, Long labId,
                                        String searchString, String searchType,
                                        int cacheNumber, int fromIndex,
                                        NBSSecurityObj nbsSecurityObj) throws
      EJBException, NEDSSSystemException
  {
    try {
		ObservationProcessor obsProcessor = new ObservationProcessor();
		Collection<Object>  result = obsProcessor.findOrganismsByName(clia, labId,
		    searchString, searchType, cacheNumber, fromIndex);
		return result;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.findOrganismsByName: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  /**
   *
   * @param observationVOCollection
   * @param actRelationshipDTCollection
   * @param nbsSecurityObj
   */
  public void addUserComments(Collection<ObservationVO> observationVOCollection,
                              Collection<ActRelationshipDT>  actRelationshipDTCollection,
                              NBSSecurityObj nbsSecurityObj)
  {
    try {
		checkMethodArgs(observationVOCollection, actRelationshipDTCollection);

		if(!isExternalObservation(observationVOCollection))
		  throw new IllegalArgumentException("Expected an external observation in order to add user comments.");

		if(!checkDataAccess(getRootObservationVO(observationVOCollection).getTheObservationDT(), nbsSecurityObj))
		  throw new SecurityException("Insufficient privileges for adding comments.");

		storeObservationVOCollection(observationVOCollection,
		                          actRelationshipDTCollection,
		                          nbsSecurityObj);
		storeActRelationshipDTCollection(actRelationshipDTCollection, nbsSecurityObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.addUserComments: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }
  
	private void addProcessingDecision(
			Collection<ObservationVO> observationVOCollection,
			Collection<ActRelationshipDT> actRelationshipDTCollection,
			NBSSecurityObj nbsSecurityObj) {
		try {
			storeObservationVOCollection(observationVOCollection,
					actRelationshipDTCollection, nbsSecurityObj);
			storeActRelationshipDTCollection(actRelationshipDTCollection,
					nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.addProcessingDecision: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

  private void checkMethodArgs(Collection<ObservationVO> obsColl, Collection<ActRelationshipDT>  actRelColl)
  {
    try {
		int NUMBER_OF_OBS = 3, NUMBER_OF_ACT_REL = 2;
		if(obsColl == null || obsColl.size() != NUMBER_OF_OBS
		   || actRelColl == null || actRelColl.size() != NUMBER_OF_ACT_REL)
		throw new IllegalArgumentException("Expected the number of observations and act relationships to be "+
		                                   NUMBER_OF_OBS + " and " + NUMBER_OF_ACT_REL);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkMethodArgs: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private boolean isExternalObservation(Collection<ObservationVO> observationVOCollection)
  {
    try {
		ObservationVO rootObsVO = getRootObservationVO(observationVOCollection);

		if(rootObsVO == null) return false;

		String electronicInd = rootObsVO.getTheObservationDT().getElectronicInd();

		if(electronicInd == null) return false;

		if(electronicInd.equalsIgnoreCase(NEDSSConstants.YES)
		   || electronicInd.equals(NEDSSConstants.EXTERNAL_USER_IND))
		  return true;
		return false;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.isExternalObservation: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private boolean checkDataAccess(ObservationDT obsDT, NBSSecurityObj nbsSecurityObj)
  {
    try {
		String nbsBOName = null;
		String viewOperation = NEDSSConstants.VIEW;
		String editOperation = NEDSSConstants.EDIT;

		if(obsDT.getCtrlCdDisplayForm().equals(NEDSSConstants.LAB_REPORT))
		{
		  nbsBOName = NEDSSConstants.OBSERVATIONLABREPORT;
		  if(nbsSecurityObj.checkDataAccess(obsDT, nbsBOName, viewOperation))
		    return true;
		  else if(nbsSecurityObj.checkDataAccess(obsDT, nbsBOName, editOperation))
		    return true;
		  else
		    return false;

		}
		else if (obsDT.getCtrlCdDisplayForm().equals(NEDSSConstants.MORBIDITY_REPORT))
		{
		  nbsBOName = NEDSSConstants.OBSERVATIONMORBIDITYREPORT;
		  if(nbsSecurityObj.checkDataAccess(obsDT, nbsBOName, viewOperation))
		    return true;
		  else if(nbsSecurityObj.checkDataAccess(obsDT, nbsBOName, editOperation))
		    return true;
		  else
		    return false;
		}
		else
		  throw new NEDSSSystemException("Expected a morb or lab report.");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.checkDataAccess: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  private void storeObservationVOCollection(Collection<ObservationVO> obsVOColl,
                              Collection<ActRelationshipDT>  actRelDTColl,
                              NBSSecurityObj nbsSecurityObj)
  {
    try {
		for (Iterator<ObservationVO> anIterator = obsVOColl.iterator(); anIterator.hasNext(); )
		  {
		    ObservationVO observationVO = (ObservationVO) anIterator.next();

		    if (observationVO == null)
		    {
		      continue;
		    }

		    //If it is a root observation vo, bypass
		    String ctrlCdDisplayForm = observationVO.getTheObservationDT().
		        getCtrlCdDisplayForm();
		    if (ctrlCdDisplayForm != null &&
		        (ctrlCdDisplayForm.equalsIgnoreCase(NEDSSConstants.
		                                           MOB_CTRLCD_DISPLAY)
		         || ctrlCdDisplayForm.equalsIgnoreCase(NEDSSConstants.LAB_CTRLCD_DISPLAY)))
		    {
		      continue;
		    }

		    //Persist the non-root observation vo only
		    Long observationUid = storeObservation(observationVO, nbsSecurityObj);

		    //Update associations with real uid if new
		    if (observationVO.isItNew())
		    {
		      Long falseUid = observationVO.getTheObservationDT().getObservationUid();
		      logger.debug("false observationUID: " + falseUid);
		      if (falseUid.intValue() < 0) {
		        this.setFalseToNewForActRelationships(actRelDTColl, falseUid, observationUid);
		      }
		     }
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeObservationVOCollection: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

  private void setFalseToNewForActRelationships(Collection<ActRelationshipDT> actRelDTColl, Long falseUid, Long actualUid)
  {
    try {
		ActRelationshipDT actRelationshipDT = null;

		for (Iterator<ActRelationshipDT> anIterator = actRelDTColl.iterator(); anIterator.hasNext(); )
		  {
		    actRelationshipDT = (ActRelationshipDT) anIterator.next();

		    if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0)
		    {
		      actRelationshipDT.setTargetActUid(actualUid);
		    }
		    if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0)
		    {
		      actRelationshipDT.setSourceActUid(actualUid);
		    }
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.setFalseToNewForActRelationships: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

  private void storeActRelationshipDTCollection(Collection<ActRelationshipDT> actRelationshipDTColl, NBSSecurityObj nbsSecurityObj)
  {
    try {
		for (Iterator<ActRelationshipDT> anIterator = actRelationshipDTColl.iterator(); anIterator.hasNext(); )
		{
		  ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
		  if(actRelationshipDT == null) continue;

		  actRelationshipDT = (ActRelationshipDT)new PrepareVOUtils().prepareAssocDT(actRelationshipDT, nbsSecurityObj);

		  ActRelationshipDAOImpl actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
		            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);

		  actRelationshipDAOImpl.store(actRelationshipDT);
		}
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeActRelationshipDTCollection: NEDSSDAOSysException: " + e.getMessage(), e);
		throw new NEDSSDAOSysException(e.getMessage(), e);
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeActRelationshipDTCollection: NEDSSSystemException: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  private void storeRoleDTCollection(Collection<Object> roleDTColl, NBSSecurityObj securityObj)
  {
    try {
		if(roleDTColl == null || roleDTColl.isEmpty() || securityObj == null) return;

		for (Iterator<Object> anIterator = roleDTColl.iterator(); anIterator.hasNext(); )
		{
		  RoleDT roleDT = (RoleDT) anIterator.next();
		  if(roleDT == null) continue;

		  roleDT = (RoleDT)new PrepareVOUtils().prepareAssocDT(roleDT, securityObj);

		  RoleDAOImpl roleDAOImpl = (RoleDAOImpl) NEDSSDAOFactory.
		            getDAO(JNDINames.ROLE_DAO_CLASS);

		  roleDAOImpl.store(roleDT);
		}
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeRoleDTCollection: NEDSSDAOSysException: " + e.getMessage(), e);
		throw new NEDSSDAOSysException(e.getMessage(), e);

	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.storeRoleDTCollection: NEDSSSystemException: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);

	}
  }

  /**
   *
   * @param testNameCondition
   * @param labClia
   * @param labUid
   * @param programAreaCd
   * @return
   */
  public Boolean getOrganismReqdIndicatorForResultedTest(String testNameCondition,
                                                         String labClia,
                                                         Long labUid,
                                                         String programAreaCd,
                                                         NBSSecurityObj nbsSecurityObj)
  {
    try {
		return new Boolean(new ObservationProcessor().getOrganismReqdIndicatorForResultedTest(testNameCondition,
		                                                     labClia,
		                                                     labUid,
		                                                     programAreaCd));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getOrganismReqdIndicatorForResultedTest: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }


  public String getLaboratorySystemDescTxt(String laboratory_id,
                                                 NBSSecurityObj nbsSecurityObj) throws RemoteException
  {
    try {
		ObservationProcessor obsprocessor = new ObservationProcessor();
		String labTestDescTxt = obsprocessor.getLaboratorySystemDescTxt(laboratory_id);
		return labTestDescTxt;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getLaboratorySystemDescTxt: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  public TreeMap<Object, Object> getCodeSystemDescription(String laboratory_id, NBSSecurityObj nbsSecurityObj) throws RemoteException
  {
    try {
		ObservationProcessor obsprocessor = new ObservationProcessor();
		TreeMap<Object, Object> map = (TreeMap<Object, Object>)obsprocessor.getCodeSystemDescription(laboratory_id);
		return map;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getCodeSystemDescription: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }

  public Boolean isAssociatedWithMorb(Long observationUID, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
    {
      try {
		Boolean isAssociatedWithMorb = new Boolean(false);
		  ArrayList<Object> list = new ArrayList<Object> ();
		  ActRelationshipDAOImpl actDAO = new ActRelationshipDAOImpl();
		  if(observationUID!= null)
		  {
		    list = (ArrayList<Object> )actDAO.loadSource(observationUID.longValue());
		    if(list != null)
		   {
		    Iterator<Object>  it = list.iterator();
		     while(it.hasNext())
		     {
		       ActRelationshipDT actDT = (ActRelationshipDT)it.next();
		       if(actDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.TREATMENT_TO_MORB_TYPE))
		       {
		         isAssociatedWithMorb = new Boolean(true);
		         break;
		       }
		     }
		   }
		  }
		  return isAssociatedWithMorb;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.isAssociatedWithMorb: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }
  public Integer associatedInvestigationCheck(Long observationUID,
          NBSSecurityObj nbsSecurityObj) throws java.
rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
NEDSSSystemException {
	  try {
		int i = 0;
		   ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
		  Collection<Object>  coll = actRelationshipDAOImpl.loadSource(observationUID.longValue());
		  if(coll!=null && coll.size()>0){
		 Iterator<Object>  anIterator = null;
		  	for (anIterator = coll.iterator(); anIterator.hasNext(); ) {
		    ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.
		        next();
		    if(actRelationshipDT.getTypeCd().equalsIgnoreCase("LabReport")
		    		|| actRelationshipDT.getTypeCd().equalsIgnoreCase("MorbReport")){
		   	i++;
		    }
		    
		  	}
		  }
		  return  new Integer(i);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.associatedInvestigationCheck: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }
  private Long getPersonUidOfObservation(Long observationUid, String type_cd ){

	     Long personUid = null;
	     Connection dbConnection = null;
	     PreparedStatement preparedStmt = null;
	     ResultSet resultSet = null;

	     try
	       {

	         String aQuery = WumSqlQuery.SELECT_PATIENT_FOR_OBSERVATION_DELETION;
	         logger.info("Query = " + aQuery);
	         dbConnection = getConnection();
	         preparedStmt = dbConnection.prepareStatement(aQuery);
	         preparedStmt.setLong(1, observationUid.longValue());
	         preparedStmt.setString(2,type_cd);
	         preparedStmt.setString(3,NEDSSConstants.PERSON_CLASS_CODE);
	         preparedStmt.setString(4,NEDSSConstants.ACTIVE);
	         preparedStmt.setString(5,NEDSSConstants.CLASS_CD_OBS);
	         resultSet = preparedStmt.executeQuery();

	         if(resultSet != null && resultSet.next()){
	            personUid = new Long(resultSet.getLong(1));
	            logger.debug("personUid: " + personUid);
	          }


	       }
	       catch(Exception e)
	       {
	    	 logger.debug("This record "+personUid+" cannot be deleted");
	         logger.fatal("ObservationProxyEJB.getPersonUidOfObservation: PersonUid: " + personUid + e.getMessage(), e);
	         throw new javax.ejb.EJBException(e.getMessage(), e);
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
	               logger.fatal("ObservationProxyEJB.getPersonUidOfObservation: " + sqlex.getMessage(), sqlex);
	               throw new javax.ejb.EJBException(sqlex.getMessage(), sqlex);
	         }
	      }
	   
	      return personUid;

	   }
  
  private Alert getAlertEJBRemoteInterface() throws EJBException
  {

    Alert alert= null;
    try
    {

      logger.debug("YOU ARE IN THE getRemoteInterface() method");
      NedssUtils nu = new NedssUtils();
      Object obj = nu.lookupBean(JNDINames.ALERT_EJB);
      AlertHome alertHome = (AlertHome) javax.rmi.
          PortableRemoteObject.narrow(obj, AlertHome.class);
      alert = alertHome.create();
    }
    catch (Exception e)
    {
      logger.fatal("ObservationProxyEJB.getAlertEJBRemoteInterface: " + e.getMessage(), e);
      throw new javax.ejb.EJBException(e.getMessage(), e);
    }
    return alert;
  } 
  
	public EDXDocumentDT getXMLDocument(Long documentUid,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			javax.ejb.CreateException, java.rmi.RemoteException,
			javax.ejb.FinderException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		EDXDocumentDT dt = null;
		try {
			EDXDocumentDAOImpl dao = new EDXDocumentDAOImpl();
			dt = dao.selectIndividualEDXDocument(documentUid);
		} catch (Exception e) {
			logger.fatal("ObservationProxyEJB.getXMLDocument: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return dt;
	}
    /**
    1.) With personUID, retrieve all existing InvestigationSummaryVO,
    * Retrieve only those Investigations linked to the Person through the Participation table.
    * 2.) Retrieve ActRelationshipDT so front-end can determine if the passed observation is
    * assigned or unassigned and set the flag in the VO.
    * @J2EE_METHOD  --  getAssociatedInvestigations
    */
   public Collection<Object> getAssociatedInvestigations (Long personUID, Long observationUid, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
   {
	   try {
		logger.debug("in getAssociatedInvestigations ");
					
		   Collection<Object>  invSummaryVOs = null;
		   if(! securityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW, ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
		   {
		       logger.info("getAssociatedInvestigations access denied NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
		       throw new NEDSSAppException("getAssociatedInvestigations access denied NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NedssOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
		   }
		   logger.info("getAssociatedInvestigations access permissions NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);

		   RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
		 
		       logger.debug("  personUID = " + personUID);
		       ArrayList<Object> uidList = new ArrayList<Object> ();
		       uidList.add(personUID);


		       /**
		        * Get TheInvestigationSummaryVOCollection
		        */
		       logger.debug("in getAssociatedInvestigations() retrieving associated Investigations");
		       logger.info("getWorkupProxy access permissions NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
		       invSummaryVOs = retrieveSummaryVO.retrieveInvestgationSummaryVO(uidList, securityObj);
		       logger.debug("getAssociatedInvestigations() done retreiving investigation summary");
		       /**
		        * set the isAssociated flag for the observation 
		        */
		       
		  
		   
		   //if empty, no need for next step
		   if (invSummaryVOs.isEmpty())
			   return invSummaryVOs;
		   
		   /**
		    * Set Associated Flag for each getPublicHealthCaseUid()
		    */
		   
		  
		  	   ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
		       Collection<Object>  coll = actRelationshipDAOImpl.loadSource(observationUid.longValue());
		       if(coll!=null && coll.size()>0){
		    	   Iterator<Object>  anIterator = null;
		    	   Iterator<Object>  caseIterator = null;
		    	   for (anIterator = coll.iterator(); anIterator.hasNext(); ) {
		    		   ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
		    		   Long targetUID = actRelationshipDT.getTargetActUid();
		    		   for (caseIterator = invSummaryVOs.iterator(); caseIterator.hasNext(); ) {
		    			   InvestigationSummaryVO invSummary = (InvestigationSummaryVO) caseIterator.next();
		    			   Long caseUID = invSummary.getPublicHealthCaseUid();
		    			   if (targetUID.longValue() == caseUID.longValue()) {
		    				   invSummary.setIsAssociated(true);
		    				   invSummary.setDispositionCd(actRelationshipDT.getAddReasonCd()); //for STD disposition
		    				   //set act from time for comparing the act relationship from_time with the investigation 
		    				   //add_time in the front end to identify if the act relationship is created when creating investigation from LAB/Morb report.
		    				   invSummary.setActFromTime(actRelationshipDT.getFromTime());
		    			   } //target UID = this case
		    		   } //case iter
		    	   } //act iter
		         
		       } //if actrelationship(s) exist    
		 

		   return invSummaryVOs;
	} catch(NEDSSSystemException ex)
	   {
	   logger.fatal("ObservationProxyEJB.getAssociatedInvestigations: NEDSSSystemException: " + ex.getMessage(), ex);
	   throw new NEDSSSystemException(ex.getMessage(), ex);

	   }
	   catch(Exception e)
	   {
		   logger.fatal("ObservationProxyEJB.getAssociatedInvestigations: Exception: " + e.getMessage(), e);
		   throw new javax.ejb.EJBException(e.getMessage(), e);
	   }
   }
   
   /**
    * setAssociatedInvestigations
    * For the passed observation, create new associations with an investigation or
    * delete existing associations. The target is the public health case. The source
    * is the observation. The type code is either LabReport or MorbReport
    * @param observationUID - the Lab Report or Morb Report to associate to
    * @param obsTypeCode - LabReport or MorbReport - (tbd:always in ctrl_cd_disp_form?)
    * @param processingDecision - if assoc to a closed investigation, code i.e. NPP Not Program Priority 
    * @param newPhcRelations - investigation uids to associate the observation to
    * @param deletePhyRelations - investigation uids to disassociate the observation from 
    * 
    * @J2EE_METHOD  --  setAssociatedInvestigations
    */
   public void setAssociatedInvestigations(Long observationUID,  
		   String obsTypeCd,
		   String processingDecisionCd,
		   Collection<Object> newPhcRelations, 
		   Collection<Object> deletePhcRelations,
		   String businessTriggerSpecified,
		   NBSSecurityObj nbsSecurityObj) 
		   throws java.rmi.RemoteException, javax.ejb.CreateException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
   			{
			try {
				logger.debug("Start of setAssociatedInvestigations()");
				ActRelationshipDAOImpl actRelationshipDAOImpl =
				            (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
				PrepareVOUtils preVOUtils=new PrepareVOUtils();
				String businessObjLookupName="";
				String businessTriggerCd="";
				String tableName=NEDSSConstants.OBSERVATION;
				String moduleCd=NEDSSConstants.BASE;
				
				
				//Get the ActController reference
				NedssUtils nedssUtils = new NedssUtils();
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());
				ActControllerHome actHome =(ActControllerHome)PortableRemoteObject.narrow(
				                                              object, ActControllerHome.class);
				ActController act = actHome.create();
				//get the DT for the observation
				ObservationDT  obsDT = act.getObservationInfo(observationUID, nbsSecurityObj);
				
				//Walk through the deleted set of investigation relationships
				if (!deletePhcRelations.isEmpty())
				{
					Long phcUID = null;
					
					Iterator<Object>  drIterator = null;
					for (drIterator = deletePhcRelations.iterator(); drIterator.hasNext(); ) { 
						phcUID = (Long) drIterator.next();
						PublicHealthCaseDT phcDT = act.getPublicHealthCaseInfo(phcUID,nbsSecurityObj);

						/* check rignts of user to change relationships for this investigation */
						if(nbsSecurityObj != null && !hasPermissionsToSetObsAssoc(nbsSecurityObj, phcDT))
						{
							logger.error("Error: User does not have the rights to edit this investigation");
							throw new EJBException("Error: User does not have sufficient rights to edit this investigation");
						}	
						try
						{
							ActRelationshipDT actRelationshipDT = actRelationshipDAOImpl.load(phcUID.longValue(), observationUID.longValue(),  obsTypeCd);
							actRelationshipDT.setItDelete(true);
							actRelationshipDT.setItDirty(false);
							actRelationshipDAOImpl.store(actRelationshipDT); //delete the association
						}
						catch(NEDSSSystemException ex)
						{
							logger.debug("Error deleting observation association to an Investigation");
							logger.error(ex);
						}
						catch(Exception e)
						{
							logger.fatal("Exception is: " + e.toString(), e);
								throw new NEDSSSystemException(e.toString());
						}
					} //has next unassociated investigation

					if(obsTypeCd.equals(NEDSSConstants.LAB_DISPALY_FORM)) //lab report
					{
						businessObjLookupName=NEDSSConstants.OBSERVATIONLABREPORT;
						if (businessTriggerSpecified != null && !businessTriggerSpecified.isEmpty())
							businessTriggerCd = businessTriggerSpecified;
						else
							businessTriggerCd=NEDSSConstants.OBS_LAB_DIS_ASC;
					}
					else if(obsTypeCd.equals(NEDSSConstants.DISPLAY_FORM)) //morb report
					{
						businessObjLookupName=NEDSSConstants.OBSERVATIONMORBIDITYREPORT;
						businessTriggerCd=NEDSSConstants.OBS_MORB_DIS_ASC;
					}
				
				}//unassociated investigation list not empty
				
				///////////////////////////////////
				//newly associated investigations//
				///////////////////////////////////
				if (!newPhcRelations.isEmpty())
				{
					logger.debug("in setAssociatedInvestigations - adding new associations to the Obs..");


					try
					{
						Collection<MessageLogDT> messageLogDTCollection =  new ArrayList<MessageLogDT>();
						Long phcUID = null;
						Iterator<Object>  arIterator = null;
						for (arIterator = newPhcRelations.iterator(); arIterator.hasNext(); ) { 
							phcUID = (Long) arIterator.next(); //get the case id to add the new relation to
				    		PublicHealthCaseDT phcDT = act.getPublicHealthCaseInfo(phcUID,nbsSecurityObj);

				    		/* check rignts of user to add associations to this investigation */
				    		if(nbsSecurityObj != null && !hasPermissionsToSetObsAssoc(nbsSecurityObj, phcDT))
				    		{
				    			logger.error("User does not have the rights to associate observations to this investigation");
				    			throw new EJBException("Error: User does not have sufficient rights to associate this investigation");
				    		}
				    		String investigationStatus = phcDT.getInvestigationStatusCd();
							ActRelationshipDT actRelationshipDT = new ActRelationshipDT();
							actRelationshipDT.setTargetActUid(new Long(phcUID));
				            actRelationshipDT.setSourceActUid(new Long(observationUID));
				            if (investigationStatus.equalsIgnoreCase(NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED))
				            		actRelationshipDT.setAddReasonCd(processingDecisionCd);
				            actRelationshipDT.setTypeCd(obsTypeCd); //LabReport or MorbReport
				            actRelationshipDT.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
				            actRelationshipDT.setTargetClassCd(NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE);
				            actRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				            actRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				            actRelationshipDT=(ActRelationshipDT)preVOUtils.prepareAssocDT(actRelationshipDT,nbsSecurityObj);
				            actRelationshipDT.setItNew(true);
				            actRelationshipDT.setItDirty(false);
							actRelationshipDAOImpl.store(actRelationshipDT); //insert new act_relationship
							
							if(phcDT.isStdHivProgramAreaCode()){
								PublicHealthCaseRootDAOImpl phc = new PublicHealthCaseRootDAOImpl();
				            	PublicHealthCaseDT publicHealthCaseDT =phc.getOpenPublicHealthCaseWithInvestigatorDT(phcDT.getPublicHealthCaseUid());
				            	if(publicHealthCaseDT!=null){
				    				MessageLogDT messageLogDT =RetrieveSummaryVO.createMessageLogDT(publicHealthCaseDT,nbsSecurityObj);
				    				messageLogDTCollection.add(messageLogDT);
				            		
				            	}
							}
					
					   } //act relations to add iterator	 
						MessageLogDAOImpl messageLogDAOImpl =  new MessageLogDAOImpl();
						try {
							messageLogDAOImpl.storeMessageLogDTCollection(messageLogDTCollection);
						} catch (Exception e) {
							logger.error("Unable to store the Error message in setAssociatedInvestigations for = "
								+"\nobservationUID"	+ observationUID  
								+"\nobsTypeCd"+	    obsTypeCd
								+"\nprocessingDecisionCd"+	   processingDecisionCd
								+"\nnewPhcRelations"+	    newPhcRelations
								+"\ndeletePhcRelations"+	   deletePhcRelations
								+"\nbusinessTriggerSpecified"+	   businessTriggerSpecified);
						}
				   }
				   catch(NEDSSSystemException ex)
				   {
				     logger.error(ex);
				   }
				   catch(Exception e)
				   {
				   	logger.fatal("Exception is: " + e.toString(), e);
				          throw new NEDSSSystemException(e.toString());
				   }
					
					//update the observation record status if needed
					if(obsTypeCd.equals(NEDSSConstants.LAB_DISPALY_FORM)) //lab report
					{
						businessObjLookupName=NEDSSConstants.OBSERVATIONLABREPORT;
						businessTriggerCd=NEDSSConstants.OBS_LAB_ASC;
					}
					else if(obsTypeCd.equals(NEDSSConstants.DISPLAY_FORM)) //morb report
					{
						businessObjLookupName=NEDSSConstants.OBSERVATIONMORBIDITYREPORT;
						businessTriggerCd=NEDSSConstants.OBS_MORB_ASC;
					}

				}// if new associations added for object to investigations
				
				///////////////////////////////////////////////////////////////
				//update the observation record status for the business trigger
				//in some cases will change the record status of the observation
				//from UNPROCESSED to PROCESSED
				///////////////////////////////////////////////////////////////
				try
				{
					RootDTInterface rootDT=null;
					obsDT.setItDirty(true);
					rootDT =  preVOUtils.prepareVO(obsDT,businessObjLookupName,
				        businessTriggerCd, tableName, moduleCd, nbsSecurityObj);
					obsDT = (ObservationDT)rootDT;
					//set processing decision to null if previously selected during marking the observation as reviewed.
					obsDT.setProcessingDecisionCd(null);
					logger.debug("ObsDt.ItDirty :"+obsDT.isItDirty());
					act.setObservationInfo(obsDT,nbsSecurityObj);
				}
				catch(NEDSSSystemException ex)
				{
					logger.debug("Error updating observation record status");
					logger.error(ex);
				}
				catch(Exception e)
				{
					logger.fatal("Exception is: " + e.toString(), e);
					throw new NEDSSSystemException(e.toString());
				}		    				

				logger.debug("end of setAssociatedInvestigations()");
				return;
			} catch (ClassCastException e) {
				// TODO Auto-generated catch block
				logger.fatal("ObservationProxyEJB.setAssociatedInvestigations: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
			}
   		}
   
   
	/*
	 * hasPermissionsToSetObjAssoc 
	 * Does the user have permissions to edit associations for the passed PHC?
	 *
	 * @param mapping - nbsSecurityObj
	 * @param mapping - publicHealthCaseDT
	 * @return boolean True user has permission, False - does not have permission
	 */  
   private boolean hasPermissionsToSetObsAssoc(NBSSecurityObj nbsSecurityObj, PublicHealthCaseDT phcDT)
   {
		    	     try {
						//Strike #1
  if(nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
						                                NEDSSConstants.EDIT,
						                                phcDT.getProgAreaCd(),
						                                phcDT.getJurisdictionCd(),
						                                phcDT.getSharedInd()))
   return true;//can edit

						  //Strike #2
   if(nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
						                                NBSOperationLookup.ADD,
						                                phcDT.getProgAreaCd(),
						                                ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
						   && nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
						                                NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
						                                ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
						                                ProgramAreaJurisdictionUtil.ANY_JURISDICTION) )
   return true;//can add

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
  	return true;//can associate
   else
  	return false; //Out
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.fatal("ObservationProxyEJB.hasPermissionsToSetObsAssoc: " + e.getMessage(), e);
						throw new javax.ejb.EJBException(e.getMessage(), e);

					}
   }	
   
   public Collection<Object> getAssociatedPublicHealthCases(Long uid, NBSSecurityObj securityObj) throws java.rmi.
   RemoteException, javax.ejb.CreateException, NEDSSConcurrentDataException{
	   try {
		ObservationRootDAOImpl obsRootDAO = new ObservationRootDAOImpl();
		   Collection<Object> coll =obsRootDAO.getAssociatedPublicHealthCasesForLab(uid, true);
		   return coll;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationProxyEJB.getAssociatedPublicHealthCases: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
	   
   }
   
/**
    * Retrieves a report from the Elr Activity Log based on specified parameters.
	* @param fromDate : String
	* @param toDate : String
	* @param statusList : ArrayList
	* @param nbsSecurityObj : NBSSecurityObj
	* @return Collection
	* @throws NEDSSSystemException
*/
	public Collection<Object> getActivityLogReport(
			ELRActivityLogSearchDT elrActivityLogSearchDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			boolean hasPermission = nbsSecurityObj.getPermission(
					"OBSERVATIONLABREPORT", "VIEWELRACTIVITY");
			if (!hasPermission)
				throw new NEDSSSystemException(
						"user does not have permisssion to view elr actvity!");

			ElrActivityLogDAOImpl elrLogDAO = new ElrActivityLogDAOImpl();
			list.add(elrLogDAO.getActivityReport(elrActivityLogSearchDT));
			list.add(elrLogDAO.getPassFailCount(elrActivityLogSearchDT));
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationProxyEJB.getActivityLogReport: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
    public Long setNBSAttachment(NBSAttachmentDT nbsAttachmentDT, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException{
		// Used the View permission of Investigation.   Should we change it ?
		Long nbsCaseAttachmentUid = null;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
				NBSOperationLookup.ADD,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)){
			logger.debug("no Edit  permissions for for the investigation");
			throw new NEDSSSystemException(
				"NO EDIT PERMISSIONS for the Investigation");
		}else{
			try{
				NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
				
				if(nbsAttachmentDT.isItNew()){
					nbsCaseAttachmentUid =  nbsAttachmentNoteDao.insertNbsAttachment(nbsAttachmentDT);
				}
			}catch(Exception e){
				logger.fatal("PageProxyEJB.setNBSAttachment:" + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			return nbsCaseAttachmentUid;
		}
		
	}
    
    public ArrayList<Object> getAttachment(Long parentUid, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException{
		// Used the View permission of Investigation.   Should we change it ?
		Long nbsCaseAttachmentUid = null;
		Collection<Object> attachments;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
				NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)){
			logger.debug("no Edit  permissions for for the investigation");
			throw new NEDSSSystemException(
				"NO EDIT PERMISSIONS for the Investigation");
		}else{
			try{
				NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
				
			//	if(nbsAttachmentDT.isItNew()){
					attachments =  nbsAttachmentNoteDao.getNbsAttachmentCollection(parentUid);
				//}
			}catch(Exception e){
				logger.fatal("PageProxyEJB.getNBSAttachment:" + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			return (ArrayList<Object>) attachments;
		}
		
	}
    
    public void deleteNbsAttachment(Long nbsAttachmentUid, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException{
		// Used the View permission of Investigation.   Should we change it ?
		Long nbsCaseAttachmentUid = null;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
				NBSOperationLookup.ADD,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)){
			logger.debug("no Delete permissions for the attachment");
			throw new NEDSSSystemException(
				"NO EDIT PERMISSIONS for the Investigation");
		}else{
			try{
				NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
				
				
					nbsAttachmentNoteDao.removeNbsAttachment(nbsAttachmentUid);
				
			}catch(Exception e){
				logger.fatal("PageProxyEJB.setNBSAttachment:" + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			
		}
		
	}
    
    
} 
