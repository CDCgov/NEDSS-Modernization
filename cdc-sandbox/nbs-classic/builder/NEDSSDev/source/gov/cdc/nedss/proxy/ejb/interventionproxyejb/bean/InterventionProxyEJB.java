/**
 * Title: InterventionProxyEJB class.
 * Description: A session bean for InterventionProxy.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean;

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
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.act.util.*;
import gov.cdc.nedss.act.sqlscript.*;
import gov.cdc.nedss.nnd.helper.*;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.ldf.helper.LDFHelper;



public class InterventionProxyEJB extends BMPBase implements javax.ejb.SessionBean
{
    LogUtils logger = new LogUtils (InterventionProxyEJB.class.getName());

    public InterventionProxyEJB    ()
    {
    }

    /**
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove    ()
    {
    }

    /**
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate    ()
    {
    }

    /**
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate    ()
    {
    }

    /**
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate    ()
    {
    }

    private void setFalseToNew(VaccinationProxyVO vaccinationProxyVO, Long falseUid, Long actualUid)
    {
     try {
		Iterator<Object>  anIterator = null;

		  ParticipationDAOImpl participationDAOImpl = null;
		  ParticipationDT participationDT = null;
		  ActRelationshipDAOImpl actRelationshipDAOImpl = null;
		  ActRelationshipDT actRelationshipDT = null;
		  RoleDT roleDT = null;

		  Collection<Object>  participationColl = (ArrayList<Object> )vaccinationProxyVO.getTheParticipationDTCollection();
		  Collection<Object>  actRelationShipColl = (ArrayList<Object> )vaccinationProxyVO.getTheActRelationshipDTCollection();
		  Collection<Object>  roleColl = (ArrayList<Object> )vaccinationProxyVO.getTheRoleDTCollection();

		  if(participationColl!= null)
		  {
		     for(anIterator = participationColl.iterator(); anIterator.hasNext();)
		     {
		        participationDT =  (ParticipationDT)anIterator.next();
		        // logger.debug("(participationDT.getActUid() comparedTo falseUid)1: " + (participationDT.getActUid().compareTo(falseUid)));
		        logger.debug("Inside the participation method call");
		        if(participationDT.getActUid().compareTo(falseUid) == 0) participationDT.setActUid(actualUid);
		        logger.debug("The participation got here !!!!Testing@@@");
		        if(participationDT.getSubjectEntityUid().compareTo(falseUid) == 0) participationDT.setSubjectEntityUid(actualUid);
		        logger.debug("participationDT.getSubjectEntityUid(): " + participationDT.getSubjectEntityUid());
		     }
		  }
		  if(actRelationShipColl!= null)
		  {
		    for(anIterator = actRelationShipColl.iterator(); anIterator.hasNext();)
		    {
		      actRelationshipDT =  (ActRelationshipDT)anIterator.next();
		      logger.debug("Just after actRelationShip table replacements aftergetting actRelationshipDT ");
		      if(actRelationshipDT.getTargetActUid().compareTo(falseUid)== 0) actRelationshipDT.setTargetActUid(actualUid);
		      logger.debug("Just after actRelationShip table replacements aftergetting actRelationshipDT and If");
		      if(actRelationshipDT.getSourceActUid().compareTo(falseUid)== 0) actRelationshipDT.setSourceActUid(actualUid);

		      logger.debug("\n\n\n(actRelationshipDT.getActUid() compared to falseUid)1 :  " + actRelationshipDT.getSourceActUid().compareTo(falseUid));
		    }
		    logger.debug("actRelationshipDT.getActUid(actualUid)2 :" + actRelationshipDT.getTargetActUid());
		  }

		  if(roleColl!= null)
		  {
		    for(anIterator = roleColl.iterator(); anIterator.hasNext();)
		    {
		      roleDT =  (RoleDT)anIterator.next();
		      if(roleDT.getSubjectEntityUid().compareTo(falseUid)== 0) roleDT.setSubjectEntityUid(actualUid);
		      if(roleDT.getScopingEntityUid() != null)
		      {
		        if(roleDT.getScopingEntityUid().compareTo(falseUid)== 0) roleDT.setScopingEntityUid(actualUid);
		        logger.debug("\n\n\n(roleDT.getSubjectEntityUid() compared to falseUid)1 :  " + roleDT.getSubjectEntityUid().compareTo(falseUid));
		        logger.debug("\n\n\n(roleDT.getScopingEntityUid() compared to falseUid)1  : " + roleDT.getScopingEntityUid().compareTo(falseUid));
		      }
		    }
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InterventionProxyEJB.setFalseToNew: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
    }

    /**
    * Sets the value of a VaccinationProxy object & automatically resends notifications if notifications exist
    * @param vaccinationProxyVO  the new value of the VaccinationProxy object
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value of the interventionUID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
    */
    public Long setVaccinationProxy    (VaccinationProxyVO vaccinationProxyVO, NBSSecurityObj nbsSecurityObj)
              throws java.rmi.RemoteException, javax.ejb.EJBException,
              CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
    {
      try {
		NNDActivityLogDT  nndActivityLogDT = null;
		  //saving VaccinationProxyVO before updating auto resend notifications
		  Long actualUid = this.setVaccinationProxyWithoutNotificationAutoResend(vaccinationProxyVO,nbsSecurityObj);
		  NNDMessageSenderHelper nndMessageSenderHelper= NNDMessageSenderHelper.getInstance();

		   try{
		    //update auto resend notifications
			   if(vaccinationProxyVO.getAssociatedNotificationInd() == true)
				   nndMessageSenderHelper.updateAutoResendNotificationsAsync(vaccinationProxyVO, nbsSecurityObj);
		  }
		  catch(Exception e)
		  {
		    nndActivityLogDT = new  NNDActivityLogDT();
		    String localId = vaccinationProxyVO.theInterventionVO.getTheInterventionDT().getLocalId();
		    nndActivityLogDT.setErrorMessageTxt(e.toString());

		    if (localId!=null)
		      nndActivityLogDT.setLocalId(localId);
		    else
		      nndActivityLogDT.setLocalId("N/A");

		    //only catch & store auto resend notifications exceptions in NNDActivityLog table
		    nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
		    logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync in InterventionProxyEJB");
		    e.printStackTrace();
		  }
		  return actualUid;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InterventionProxyEJB.setVaccinationProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
    }


     private Long setVaccinationProxyWithoutNotificationAutoResend(VaccinationProxyVO vaccinationProxyVO, NBSSecurityObj nbsSecurityObj)
                      throws java.rmi.RemoteException, javax.ejb.EJBException,
                       CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
              {
               try {
				// if no permissions - terminate for ADD operation
				   if(vaccinationProxyVO.isItNew())
				   {
				     if(!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.ADD))
				     {
				       logger.fatal("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.ADD) is false");
				       throw new RemoteException("NO PERMISSIONS");
				     }
				     logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.ADD) is true in setInterventionProxyEJB");
				   }
				   else if(vaccinationProxyVO.isItDirty())
				   {
				     // if no permissions - terminate for EDIT operation
				     if(!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.EDIT))
				       {
				         logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.EDIT) is false");
				         throw new RemoteException("NO PERMISSIONS");
				       }
				       logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.EDIT) is true in setInterventionProxyEJB");
				     }
				     else{
				       logger.error("vaccinationProxyVO is neither NEW nor DIRTY, therefore exit without changes!");
				       return vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getInterventionUid();
				     }

				     RoleDT roleDT = null;
				     ParticipationDT participationDT = null;
				     ActRelationshipDT actRelationshipDT = null;
				     logger.debug("!!!!!!!!!in setVacinationProxy");
				     EntityController entityController = null;
				     NedssUtils nedssUtils = new NedssUtils();
				     PersonVO personVO = null;
				     OrganizationVO organizationVO = null;
				     ObservationVO observationVO = null;
				     MaterialVO materialVO = null;

				     
				     ParticipationDAOImpl participationDAOImpl = null;
				     ActRelationshipDAOImpl actRelationshipDAOImpl = null;
				     RoleDAOImpl roleDAOImpl = null;
				     Long actualUid = null;
				    
				     Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
				     logger.debug("!!!!!!!!!!!EntityController lookup = " + obj.toString());
				     EntityControllerHome home =(EntityControllerHome)PortableRemoteObject.narrow(obj, EntityControllerHome.class);
				     logger.debug("!!!!!!!!!Found EntityControllerHome: " + home);
				     entityController = home.create();
				     ActController actController = null;
				     Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				     logger.debug("!!!!!!!!!!ActController lookup = " + object.toString());
				     ActControllerHome acthome =(ActControllerHome)PortableRemoteObject.narrow(object, ActControllerHome.class);
				     logger.debug("!!!!!!!!!!Found ActControllerHome: " + acthome);
				     actController = acthome.create();
				     Long falseInterventionUid = null;

				     if(vaccinationProxyVO.getTheInterventionVO() != null)
				     {
				       InterventionVO interventionVO = vaccinationProxyVO.getTheInterventionVO();
				       try
				       {
				         if(vaccinationProxyVO.getTheInterventionVO().isItNew())
				         {
				           RootDTInterface rDTInterface =  vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT();
				           String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.INTERVENTIONVACCINERECORD;
				           String BUSINESS_TRIGGER_CD = NEDSSConstants.INT_VAC_CR;
				           String TABLE_NAME = DataTables.INTERVENTION_TABLE;
				           String MODULE_CD = NEDSSConstants.BASE;
				           PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				           logger.debug("\nThe interventionUID is " + interventionVO.getTheInterventionDT().getInterventionUid());
				           InterventionDT newInterventionDT = (InterventionDT)prepareVOUtils.prepareVO(rDTInterface, BUSINESS_OBJECT_LOOKUP_NAME,BUSINESS_TRIGGER_CD, TABLE_NAME,MODULE_CD, nbsSecurityObj);
				           interventionVO.setTheInterventionDT(newInterventionDT);
				           logger.debug("\n\n\n\n\n!!!!!!!interventionVO status CD"+interventionVO.getTheInterventionDT().getStatusCd());
				           logger.debug("\n\n\n\n!!!!interventionVO status time"+interventionVO.getTheInterventionDT().getStatusTime());
				           logger.debug("\nThe interventionUID is " + newInterventionDT.getInterventionUid());
				           logger.debug("\nThe record status CD in new must have changed now: "+interventionVO.getTheInterventionDT().getRecordStatusCd());
				         }
				         else if(vaccinationProxyVO.getTheInterventionVO().isItDirty())
				         {
				           RootDTInterface rDTInterface =  vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT();
				           String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.INTERVENTIONVACCINERECORD;
				           String BUSINESS_TRIGGER_CD = NEDSSConstants.INT_VAC_EDIT;
				           String TABLE_NAME = DataTables.INTERVENTION_TABLE;
				           String MODULE_CD = NEDSSConstants.BASE;
				           PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				           InterventionDT newInterventionDT = (InterventionDT)prepareVOUtils.prepareVO(rDTInterface, BUSINESS_OBJECT_LOOKUP_NAME,BUSINESS_TRIGGER_CD, TABLE_NAME,MODULE_CD, nbsSecurityObj);
				           interventionVO.setTheInterventionDT(newInterventionDT);
				           logger.debug("\n\n\nThe interventionUID is " + interventionVO.getTheInterventionDT().getInterventionUid());
				           logger.debug("\n\n\n\n\n!!!!!!!dirty interventionVO status CD"+interventionVO.getTheInterventionDT().getStatusCd());
				           logger.debug("\n\n\n\n!!!!dirty interventionVO status time"+interventionVO.getTheInterventionDT().getStatusTime());
				           logger.debug("\nThe interventionUID is " + newInterventionDT.getInterventionUid());
				           logger.debug("\nThe record status CD in edit must have changed now: "+interventionVO.getTheInterventionDT().getRecordStatusCd());
				         }

				         logger.debug("NEDSSConcurrentDataException in interventionProxy 1");
				         falseInterventionUid = interventionVO.getTheInterventionDT().getInterventionUid();
				         actualUid  =  actController.setIntervention(interventionVO, nbsSecurityObj);
				         vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().setInterventionUid(actualUid);
				         logger.debug("NEDSSConcurrentDataException in interventionProxy 2");

				       }
				       catch(NEDSSConcurrentDataException ex)
				       {
				         logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access is not allowed: " + ex.getMessage(), ex);
				         throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
				       }
				       catch (Exception e)
				       {
				         if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
				         {
				        	 logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: " + e.getMessage(), e);
				             throw new NEDSSConcurrentDataException(e.getMessage(), e);
				         }
				       }
				       logger.debug("!!!!!!!\n\n\nactual UID is \n\n\n" + actualUid);
				       if(falseInterventionUid < 0)
				       {
				         logger.debug("inside the if statement");
				         setFalseToNew(vaccinationProxyVO, falseInterventionUid, actualUid);
				         logger.debug("Completed the if statement");
				       }
				     }
				     logger.debug("Testing if it reaches here");
				     Long falseUid = null;
				     Long realUid = null;
				     Iterator<Object>  anIteratorOrg = null;
				     if(vaccinationProxyVO.getTheOrganizationVOCollection() != null)
				     {
				       for(anIteratorOrg = vaccinationProxyVO.getTheOrganizationVOCollection().iterator(); anIteratorOrg.hasNext(); )
				       {
				         organizationVO = (OrganizationVO)anIteratorOrg.next();
				         if(organizationVO.isItNew() || organizationVO.isItDirty())
				         {
				           try
				           {
				             if(organizationVO.isItNew())
				             {
				               String businessTriggerCd = NEDSSConstants.ORG_CR;
				               realUid = entityController.setOrganization(organizationVO, businessTriggerCd, nbsSecurityObj);
				             }
				             else if(organizationVO.isItDirty())
				             {
				               String businessTriggerCd = NEDSSConstants.ORG_EDIT;
				               realUid = entityController.setOrganization(organizationVO,businessTriggerCd,nbsSecurityObj);
				             }
				           }
				           catch(NEDSSConcurrentDataException ex)
				           {
				             logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access in setOrganization/InterventionProxyEJB is not allowed: " + ex.getMessage(), ex);
				             throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
				           }
				           catch(Exception ex)
				           {
				        	   logger.fatal("EntityController.setOrganization: " + ex.getMessage(), ex);
				               throw new EJBException(ex.getMessage(), ex);
				           }
				           falseUid = organizationVO.getTheOrganizationDT().getOrganizationUid();
				           logger.debug("the  falseOrganizationUid is" + falseUid);
				           logger.debug("the  realOrganizationUid is" + realUid);
				           if(falseUid< 0) setFalseToNew(vaccinationProxyVO, falseUid, realUid);
				         }
				       }
				     }
				     Iterator<Object>  anIteratorPer = null;
				     if(vaccinationProxyVO.getThePersonVOCollection() != null)
				     {
				       for(anIteratorPer = vaccinationProxyVO.getThePersonVOCollection().iterator(); anIteratorPer.hasNext(); )
				       {
				         personVO = (PersonVO)anIteratorPer.next();
				         if(personVO.isItNew() || personVO.isItDirty())
				         {
				           try
				           {
				             String businessTriggerCd = null;
				             if(personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)){
				               if(personVO.getThePersonDT().isItNew()){
				                 personVO.setItNew(true);
				                 businessTriggerCd = NEDSSConstants.PAT_CR;
				               }
				               else if(personVO.getThePersonDT().isItDirty()){
				                 personVO.setItDirty(true);
				                 businessTriggerCd = NEDSSConstants.PAT_EDIT;
				               }
				               realUid = entityController.setPatientRevision(personVO,businessTriggerCd,nbsSecurityObj);
				             }
				             if(personVO.getThePersonDT().getCd().equals(NEDSSConstants.PRV)){
				               if(personVO.getThePersonDT().isItNew()){
				                 personVO.setItNew(true);
				                 businessTriggerCd = NEDSSConstants.PRV_CR;
				               }
				               else if(personVO.getThePersonDT().isItDirty()){
				                 personVO.setItDirty(true);
				                 businessTriggerCd = NEDSSConstants.PRV_EDIT;
				               }
				               realUid = entityController.setProvider(personVO, businessTriggerCd, nbsSecurityObj);
				             }
				           }
				               catch(NEDSSConcurrentDataException ex)
				               {
				            	   logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access in setPerson/InterventionProxyEJB is not allowed: " + ex.getMessage(), ex);
				                   throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
				               }
				               catch(Exception ex)
				               {
				            	   logger.fatal("EntityController.setProvider: " + ex.getMessage(), ex);
				                   throw new EJBException(ex.getMessage(), ex);
				               }
				               falseUid = personVO.getThePersonDT().getPersonUid();
				               logger.debug("@the  falsePersonUid is : " + falseUid);
				               logger.debug("the Type code value set here is : " + personVO.getThePersonDT().getCd() );
				               logger.debug("@the  realPersonUid is : " + realUid);
				               if(falseUid< 0) setFalseToNew(vaccinationProxyVO, falseUid, realUid);
				             }
				           }
				         }

				        Iterator<ObservationVO>  anIteratorObs =  null;
				         if(vaccinationProxyVO.getTheObservationVOCollection() != null)
				         {
				           for(anIteratorObs = vaccinationProxyVO.getTheObservationVOCollection().iterator(); anIteratorObs.hasNext(); )
				           {
				             observationVO = (ObservationVO)anIteratorObs.next();
				             if(observationVO.isItDirty() || observationVO.isItNew())
				             {
				               try
				               {
				                 if(observationVO.getTheObservationDT().isItNew() || observationVO.getTheObservationDT().isItDirty())
				                 {
				                   falseUid = observationVO.getTheObservationDT().getObservationUid();
				                   logger.debug("the  falseObservationUid is" + falseUid);
				                   realUid = actController.setObservation(observationVO, nbsSecurityObj);
				                   logger.debug("the  realOrganizationUid is" + realUid);
				                   if(falseUid< 0) setFalseToNew(vaccinationProxyVO, falseUid, realUid);
				                 }
				               }
				               catch(NEDSSConcurrentDataException ex)
				               {
				            	   logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access in setObservation/InterventionProxyEJB is not allowed: " + ex.getMessage(), ex);
				                   throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
				               }
				             }
				           }
				         }

				         if(vaccinationProxyVO.getTheMaterialVO() != null)
				         {
				           materialVO = vaccinationProxyVO.getTheMaterialVO();
				           if(materialVO.isItDirty() || materialVO.isItNew())
				           {
				             try
				             {
				               if(materialVO.getTheMaterialDT().isItNew())
				               {
				                 PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				                 logger.debug("\n\nmaterialVO UID is :" + materialVO.getTheMaterialDT().getMaterialUid());
				                 MaterialDT newMaterialDT = (MaterialDT)prepareVOUtils.prepareVO(materialVO.getTheMaterialDT(), NBSBOLookup.MATERIAL, NEDSSConstants.MAT_MFG_CR, DataTables.MATERIAL_TABLE, NEDSSConstants.BASE, nbsSecurityObj);
				                 logger.debug("\n\n\nMaterial UID after prepareVO is :"+ newMaterialDT.getMaterialUid());
				                 materialVO.setTheMaterialDT(newMaterialDT);
				                 logger.debug("\n\n\n\n\n!!!!!!!dirty materialVO status CD"+materialVO.getTheMaterialDT().getStatusCd());
				                 logger.debug("\n\n\n\n!!!!dirty materialVO status time"+materialVO.getTheMaterialDT().getStatusTime());
				               }
				               else if(materialVO.getTheMaterialDT().isItDirty())
				               {
				                 PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				                 logger.debug("\n\nmaterialVO UID is :" + materialVO.getTheMaterialDT().getMaterialUid());
				                 MaterialDT newMaterialDT = (MaterialDT)prepareVOUtils.prepareVO(materialVO.getTheMaterialDT(), NBSBOLookup.MATERIAL, NEDSSConstants.MAT_MFG_EDIT, DataTables.MATERIAL_TABLE, NEDSSConstants.BASE, nbsSecurityObj);
				                 logger.debug("\n\n\nMaterial UID after prepareVO is :"+ newMaterialDT.getMaterialUid());
				                 materialVO.setTheMaterialDT(newMaterialDT);
				                 logger.debug("\n\n\n\n\n!!!!!!!dirty getTheMaterialDT status CD"+materialVO.getTheMaterialDT().getStatusCd());
				                 logger.debug("\n\n\n\n!!!!dirty getTheMaterialDT status time"+materialVO.getTheMaterialDT().getStatusTime());
				               }
				             }
				             catch(NEDSSConcurrentDataException ex)
				             {
				            	 logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: NEDSSConcurrentDataException: Concurrent access in setMaterial/InterventionProxyEJB is not allowed: " + ex.getMessage(), ex);
				                 throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
				             }
				             catch(Exception ex)
				             {
				               logger.fatal("vaccinationProxyVO.getTheMaterialVO(): " + ex.getMessage(), ex);
				               throw new EJBException(ex.getMessage(), ex);
				             }

				             falseUid = materialVO.getTheMaterialDT().getMaterialUid();
				             logger.debug(" the falseMaterialUid is: " + falseUid);
				             realUid = entityController.setMaterial(materialVO, nbsSecurityObj);
				             logger.debug("the realMaterialUid is: " + realUid);
				             if (falseUid<0) setFalseToNew(vaccinationProxyVO, falseUid,realUid);
				           }
				         }
				         Iterator<Object>  anIteratorRole =  null;
				         //start of inserting values into role association tables
				         if(vaccinationProxyVO.getTheRoleDTCollection()!= null)
				         {
				           logger.debug("got the role Collection<Object>  Loop and size is :"+ vaccinationProxyVO.getTheRoleDTCollection().size());
				           roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ROLE_DAO_CLASS);
				           for(anIteratorRole = vaccinationProxyVO.getTheRoleDTCollection().iterator(); anIteratorRole.hasNext(); )
				           {
				             roleDT = (RoleDT)anIteratorRole.next();
				             if(roleDT.getStatusCd()== null) roleDT.setStatusCd(NEDSSConstants.ROLE_STATUS_CD);
				             if(roleDT.getStatusTime()== null) roleDT.setStatusTime(NEDSSConstants.ROLE_TIME);
				             logger.debug("Got into The Role loop");
				             try
				             {
				               logger.debug("Got into The role, The rolesubjectentityUid new?: " + roleDT.isItNew() +" dirty ?: " + roleDT.isItDirty() + " :delete ?:" + roleDT.isItDelete());
				               logger.debug("getSubjectEntityUid :" +roleDT.getSubjectEntityUid() + ": getCD :"+roleDT.getCd() +": RoleSequence :"+ roleDT.getRoleSeq());
				               if(roleDT.getCd().equals(NEDSSConstants.MANUFACTURER_OF_VACCINE))
				               {
				                 int i = 0;//this.maxRoleSeqValForEntity(roleDT.getSubjectEntityUid());
				                 if(i < 1)
				                 {
				                   logger.debug("do nothing, there is no data in the database for that value");
				                 }
				                 else
				                 {
				                   logger.debug("The value of i here is :" + i);
				                   roleDT.setRoleSeq(new Long(i + 1));
				                 }
				               }
				               logger.debug("The new Role sequence number is :" + roleDT.getRoleSeq());
				               roleDAOImpl.store(roleDT);
				               logger.debug("Got into The role, The rolesubjectentityUid is " + roleDT.getSubjectEntityUid());
				             }
				             catch(Exception e)
				             {
				            	 logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
				                 throw new javax.ejb.EJBException(e.getMessage(), e);
				             }
				           }
				         }
				         Iterator<Object>  anIteratorPar =  null;
				         if(vaccinationProxyVO.getTheParticipationDTCollection() != null)
				         {
				           logger.debug("got the participation Collection<Object>  Loop");
				           participationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_PARTICIPATION_DAO_CLASS);
				           logger.debug("got the participation Collection<Object>  Loop got the DAO");
				           for(anIteratorPar = vaccinationProxyVO.getTheParticipationDTCollection().iterator(); anIteratorPar.hasNext();)
				           {
				             logger.debug("got the participation Collection<Object>  FOR Loop");
				             participationDT = (ParticipationDT)anIteratorPar.next();
				             if(participationDT.getStatusCd()== null) participationDT.setStatusCd(NEDSSConstants.ROLE_STATUS_CD);
				             if(participationDT.getStatusTime()== null) participationDT.setStatusTime(NEDSSConstants.ROLE_TIME);
				             logger.debug(" got the participation Loop");
				             try
				             {
				               logger.debug("got the participationDT, the statusCD is " + participationDT.getStatusCd() );
				               logger.debug("got the participationDT, the typeCD is " + participationDT.getTypeCd() );
				               logger.debug("The delete flag for participation is :"+ participationDT.isItDelete());
				               logger.debug("The dirty flag for participation is :"+ participationDT.isItDirty());
				               participationDAOImpl.store(participationDT);
				               logger.debug("the participationDT, the ACTUID is " + participationDT.getActUid() );
				               logger.debug("the participationDT, the subjectEntityUid is " + participationDT.getSubjectEntityUid() );
				             }
				             catch(Exception e)
				             {
				            	 logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
				                 throw new javax.ejb.EJBException(e.getMessage(), e);
				             }
				           }
				           Iterator<Object>  anIteratorAct =  null;
				           if(vaccinationProxyVO.getTheActRelationshipDTCollection() != null)
				           {
				             actRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
				             for(anIteratorAct = vaccinationProxyVO.getTheActRelationshipDTCollection().iterator(); anIteratorAct.hasNext();)
				             {
				               actRelationshipDT = (ActRelationshipDT)anIteratorAct.next();
				               if(actRelationshipDT.getStatusCd()== null) actRelationshipDT.setStatusCd(NEDSSConstants.ROLE_STATUS_CD);
				               if(actRelationshipDT.getStatusTime()== null) actRelationshipDT.setStatusTime(NEDSSConstants.ROLE_TIME);
				               logger.debug("Got into The ActRelationship loop");
				               try
				               {
				                 actRelationshipDAOImpl.store(actRelationshipDT);
				                 logger.debug("Got into The ActRelationship, The ActUid is " + actRelationshipDT.getTargetActUid());
				               }
				               catch(Exception e)
				               {
				            	   logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: nbsSecurity Object: " + nbsSecurityObj.getFullName() + e.getMessage(), e);
				                   throw new javax.ejb.EJBException(e.getMessage(), e);
				               }
				             }
				           }
				         }
				         try
				         {
				         // code for new ldf backend.
				         LDFHelper ldfHelper = LDFHelper.getInstance();
				         ldfHelper.setLDFCollection(vaccinationProxyVO.
				                                    getTheStateDefinedFieldDataDTCollection(), vaccinationProxyVO.getLdfUids(),
				                                    NEDSSConstants.VACCINATION_LDF,null,actualUid,nbsSecurityObj);
				         }
				         catch(Exception e)
				         {
				           logger.fatal("LDFHelper.getLDFCollection: " + e.getMessage(), e);
				           throw new javax.ejb.EJBException(e.getMessage(), e);

				         }



				         logger.debug("the actual Uid for InterventionProxy interventionUID is " + actualUid);
				         return actualUid;
               			} catch (ClassCastException e) {
               					// TODO Auto-generated catch block
               					logger.fatal("InterventionProxyEJB.setVaccinationProxyWithoutNotificationAutoResend: " + e.getMessage(), e);
               						throw new javax.ejb.EJBException(e.getMessage(), e);
               			}

                   }


    /**
    * Access method for a selected VaccinationProxyVO object.
    * @param interventionUid     the Uid value of the selected intervention
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the VaccinationProxyVO object for selected intervention Uid
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, gov.cdc.nedss.exceptions.NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
    */
    public VaccinationProxyVO getVaccinationProxy    (Long interventionUid, NBSSecurityObj nbsSecurityObj)
     throws
                java.rmi.RemoteException,
    		javax.ejb.EJBException,
    		NEDSSSystemException,
    		javax.ejb.FinderException,
    		javax.ejb.CreateException
    {
    try {
		// if no permissions - terminate
		if(!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
		                    NBSOperationLookup.VIEW))
		{
		    logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW) is false");
		    throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW) is true");

		  //declare new vaccinationProxyVO
		  VaccinationProxyVO vaccinationProxyVO = new VaccinationProxyVO();
		  InterventionVO interventionVO = null;//vaccination is type of intervention
		  //declare collections to add to vaccinationProxyVO
		  Collection<Object>  thePersonVOCollection  = new ArrayList<Object> ();
		  Collection<Object>  theOrganizationVOCollection  = new ArrayList<Object> ();
		  Collection<ObservationVO>  theObservationVOCollection  = new ArrayList<ObservationVO> ();

		  //declare Value objects to be added to collections
		  MaterialVO materialVO = null;//vaccine
		  ParticipationDT participationDT = null;
		  ActRelationshipDT actRelationshipDT = null;
		  RoleDT roleDT = null;

		 Iterator<Object>  anIterator = null;
		 Iterator<Object>  anoIterator = null;
		 Iterator<Object>  actIterator = null;
		  int i = 0;//for debugging
		  int m = 0;//for debugging
		  int a = 0;//for debugging

		  ActController actController = null;
		  EntityController entityController = null;
		  NedssUtils nedssUtils = new NedssUtils();
		  try
		  {
		    Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
		    logger.debug("!!!!1EntityController lookup = " + obj.toString());
		    EntityControllerHome home =(EntityControllerHome)PortableRemoteObject.narrow(obj, EntityControllerHome.class);
		    logger.debug("!!!!!Found EntityControllerHome: " + home);
		    entityController = home.create();
		    Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		    logger.debug("!!!!!!!1ActController lookup = " + object.toString());
		    ActControllerHome acthome =(ActControllerHome)PortableRemoteObject.narrow(object, ActControllerHome.class);
		    logger.debug("!!!!!!!1Found ActControllerHome: " + acthome);
		    actController = acthome.create();

		    interventionVO = actController.getIntervention(interventionUid, nbsSecurityObj);
		    //before returning InterventionVO check security permissions -
		    // if no permissions - terminate
		    if(!nbsSecurityObj.checkDataAccess(interventionVO.getTheInterventionDT(), NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW ))
		    {
		        logger.info("NBSSecurityObj.checkDataAccess(interventionVO.getTheInterventionDT(), NBSBOLookup.INTERVENTIONVACCINERECORD) is false");
		        throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
		    }
		    logger.info("NBSSecurityObj.checkDataAccess(interventionVO.getTheInterventionDT(), NBSBOLookup.INTERVENTIONVACCINERECORD) is true");

		  if(interventionVO.getTheParticipationDTCollection() != null)
		  {
		    //iterating through internventionVO participationDTCollection
		    for (anIterator = interventionVO.getTheParticipationDTCollection().iterator(); anIterator.hasNext();)
		    {
		      i++;
		      logger.debug("!!!!!!!!!1in interventionVO.getTheParticipationDT Collection<Object>  for loop: " + i);
		      participationDT = (ParticipationDT)anIterator.next();
		      //checking to see if person is the subject of vaccination and adding person to person collection
		      if(participationDT.getTypeCd() != null)
		      {
		        if(participationDT.getActUid().equals(interventionUid) && participationDT.getTypeCd().equals(NEDSSConstants.PERFORMER_OF_VACCINE) &&
			        participationDT.getSubjectClassCd().equals(NEDSSConstants.PERSON_CLASS_CODE) &&
		        	participationDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
		  		participationDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
		        {
		          logger.debug("!!!!!!!!!!Found performer of Vaccination in participationDT");
		          thePersonVOCollection.add(entityController.getProvider(participationDT.getSubjectEntityUid(), nbsSecurityObj));
		          //vaccinationProxyVO.setThePersonVOCollection(thePersonVOCollection);
		          logger.debug("!!!!!!!!!found performer of Vaccination and added to personvo collection is it empty? " + thePersonVOCollection.isEmpty());
		          continue;
		        }

		        if(participationDT.getActUid().equals(interventionUid) && participationDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_VACCINE) &&
		                   (participationDT.getSubjectClassCd().equals(NEDSSConstants.PERSON_CLASS_CODE) || participationDT.getSubjectClassCd().equals(NEDSSConstants.CLASS_CD_PAT)) &&
		                    participationDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
		                      participationDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
		        {
		          logger.debug("!!!!!!!!!!Found Subject of Vaccination in participationDT");
		          thePersonVOCollection.add(entityController.getPatientRevision(participationDT.getSubjectEntityUid(),nbsSecurityObj));
		          logger.debug("!!!!!!!!!found Subject of Vaccination and added to personvo collection is it empty? " + thePersonVOCollection.isEmpty());
		          continue;
		        }

		        if(participationDT.getActUid().equals(interventionUid) && participationDT.getTypeCd().equals(NEDSSConstants.PERFORMER_OF_VACCINE) &&
		          participationDT.getSubjectClassCd().equals(NEDSSConstants.ORGANIZATION_CLASS_CODE)&&
		          participationDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
		          participationDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
		        {
		          logger.debug("!!!!!!!!!1Found Organization admin of Vaccination in ParticipationDT");
		          theOrganizationVOCollection.add(entityController.getOrganization(participationDT.getSubjectEntityUid(), nbsSecurityObj));
		          //vaccinationProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
		          logger.debug("!!!!!!!!!!found Organization of Vaccination and added to personvo collection");
		          continue;
		        }
		        if(participationDT.getTypeCd().equals(NEDSSConstants.VACCINE_GIVEN) &&
		          participationDT.getSubjectClassCd().equals(NEDSSConstants.MATERIAL_CLASS_CODE)&&
		          participationDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
		          participationDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
		        {
		          logger.debug("\n\n\nmaterial UID is :" + participationDT.getSubjectEntityUid());
		          materialVO = entityController.getMaterial(participationDT.getSubjectEntityUid(), nbsSecurityObj);
		          logger.debug("Material number value here is :" + materialVO.getTheMaterialDT().getNm());
		          vaccinationProxyVO.setTheMaterialVO(materialVO);
		          continue;
		        }//end of materialif statement
		      }
		      else
		      {
		        logger.debug("!!!!!!!!!!!!!! PartcipationDT.getTypeCd null in loop " + i + "withActUid: " + participationDT.getActUid());
		        continue;
		      }
		    }//end of long for statement to go through participation collection
		  }//end of if statement checking if participationcollection is empty
		  //iterating through internventionVO actRelationshipDTCollection
		  logger.debug("\n\n\n\n!!!!!size of the actrealtionshipcollection :" + interventionVO.getTheActRelationshipDTCollection().size());
		  if (interventionVO.getTheActRelationshipDTCollection() != null)
		  {
		    for (actIterator = interventionVO.getTheActRelationshipDTCollection().iterator(); actIterator.hasNext();)
		    {
		      a++;
		      logger.debug("!!!!!!!!!!in interventionVO.getTheActRelationshiopDTCollection  for loop " + a);
		      obj = actIterator.next();
		      logger.debug("\n\n\n\nobject class type :" + obj.getClass());
		      actRelationshipDT = (ActRelationshipDT)obj;
		      //actRelationshipDT = (ActRelationshipDT)actIterator.next();
		      logger.debug("test 1");
		  if (actRelationshipDT.getTypeCd() != null)
		      {
		        logger.debug("test2");
		        //To check for VAC105
		      if (actRelationshipDT.getTargetActUid().equals(interventionUid) &&
		                      actRelationshipDT.getTypeCd().equals(NEDSSConstants.VACCINE_ACT_TYPE_CD) &&
		                      actRelationshipDT.getSourceClassCd().equals(NEDSSConstants.OBSERVATION_CLASS_CODE) &&
		                      actRelationshipDT.getTargetClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE) &&
		                      actRelationshipDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE))
		        {
		          logger.debug("found VAC105 in act relationship");
		          theObservationVOCollection.add(actController.getObservation(actRelationshipDT.getSourceActUid(), nbsSecurityObj));
		          //vaccinationProxyVO.setTheObservationVOCollection(theObservationVOCollection);
		          logger.debug("added VAC105 observationVO to observationVO collection.  is it empty? " + theObservationVOCollection.isEmpty());
		          continue;
		        }
		      }
		      else
		      {
		        logger.debug("!!!!!!!!!ActRelationshipDT.getTypeCd is null in loop: " + a + "for sourceactuid: " + actRelationshipDT.getSourceActUid());
		        continue;
		      }
		    }//end of for loop to go through InterventionVO actrelationship
		  }//end of if blck testing if actrelationship collection is null
		  if(materialVO != null)
		  {
		    logger.debug("Size of roleDTCollection  is: " + materialVO.getTheRoleDTCollection().size());
		    for (anoIterator = materialVO.getTheRoleDTCollection().iterator(); anoIterator.hasNext();)
		        {
		          m++;
		          logger.debug("!!!!!!!!in materialVO.getRoleDT Collection<Object>  for loop to find vaccine manufacturer " + m);
		          roleDT = (RoleDT)anoIterator.next();
		          logger.debug("!!!!!!!!!!looking in Role with id: " + roleDT.getScopingEntityUid());
		          logger.debug("!!!!!!!!!!roleDT.getscopiongRoleCD is: "+ roleDT.getScopingRoleCd());
		          logger.debug("Material UID = " + materialVO.getTheMaterialDT().getMaterialUid());
		          if (checkForNulls(roleDT))
		          //if (roleDT.getScopingRoleCd() != null)
		          {
		            if (roleDT.getSubjectEntityUid().equals(materialVO.getTheMaterialDT().getMaterialUid()) &&
		                  roleDT.getCd().equals(NEDSSConstants.MANUFACTURERD_VACCINE) &&
		                  roleDT.getSubjectClassCd().equals(NEDSSConstants.MATERIAL_CLASS_CODE) &&
		                  roleDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
		                  roleDT.getScopingRoleCd().equals(NEDSSConstants.MANUFACTURER_OF_VACCINE) &&
		                  roleDT.getScopingClassCd().equals(NEDSSConstants.ORGANIZATION_CLASS_CODE))
		            {
		              logger.debug("!!!!!!!!Found  Manufacturer of Vaccination in roleDT");
		              theOrganizationVOCollection.add(entityController.getOrganization(roleDT.getScopingEntityUid(), nbsSecurityObj));
		              logger.debug("!!!!!!!!!!found Manufacturer of Vaccination and added to organizatioVOcollection. is it empty? " + theOrganizationVOCollection.isEmpty());
		              continue;
		            }//end of if role statement
		          }//end of checking for null
		          else
		          {
		            logger.debug("nullscopingcd cannot process");
		            continue;
		          }
		        }//end of for within for for materialVO
		    }//end of if checking for null materialVO
		    else
		    {
		      logger.debug("!!!!!!!!!!!!!no vaccine found for intervention so no vaccine manufacturer");
		    }

		  vaccinationProxyVO.setThePersonVOCollection(thePersonVOCollection);
		  vaccinationProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
		  vaccinationProxyVO.setTheMaterialVO(materialVO);
		  vaccinationProxyVO.setTheObservationVOCollection(theObservationVOCollection);
		  vaccinationProxyVO.setTheInterventionVO(interventionVO);
		  //MHankey
		  boolean exists = NNDMessageSenderHelper.getInstance().checkForExistingNotifications(vaccinationProxyVO).booleanValue();
		  vaccinationProxyVO.setAssociatedNotificationInd(exists);
		  //for LDFs
		  ArrayList<Object> ldfList = new ArrayList<Object> ();
		  try {
		    //ldfList = (ArrayList<Object> ) getLDFCollection(organizationUid.longValue());
		    //code for new ldf back end condition code is null for person
		    LDFHelper ldfHelper = LDFHelper.getInstance();
		    ldfList = (ArrayList<Object> ) ldfHelper.getLDFCollection(interventionUid, null,nbsSecurityObj);

		  }
		  catch (Exception e) {
			  logger.fatal("LDFHelper.getLDFCollection: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
		  }

		  if (ldfList != null) {
		    logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
		    vaccinationProxyVO.setTheStateDefinedFieldDataDTCollection(ldfList);
		 }
		 }
		  catch(Exception e)
		  {
			  logger.fatal("InterventionProxyEJB.getVaccinationProxy: Concurrent access is not allowed: " + e.getMessage(), e);
				throw new javax.ejb.EJBException(e.getMessage(), e);
		  }
		  return vaccinationProxyVO;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InterventionProxyEJB.getVaccinationProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
    }


    /**
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext    (javax.ejb.SessionContext sessioncontext) throws javax.ejb.EJBException, java.rmi.RemoteException
    {
    }

    /**
    * Deletes the value of a VaccinationProxy object.
    * @param interventionUid     the Uid value of the selected intervention
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value for the delete action
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException
    */
    public boolean deleteVaccinationProxy    (Long interventionUid, NBSSecurityObj nbsSecurityObj)
              throws java.rmi.RemoteException, javax.ejb.EJBException,
              CreateException, FinderException, NEDSSSystemException,  NEDSSConcurrentDataException
    {
      try {
		boolean isDeletable = false;
		  if(!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,
		                    NBSOperationLookup.DELETE))
		  {
		    logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.DELETE) is false");
		    throw new EJBException("NO PERMISSIONS");
		  }
		  logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.DELETE in setInterventionProxyEJB");
		  Connection dbConnection = null;
		  PreparedStatement preparedStmt = null;
		  ResultSet resultSet = null;
		  int resultCount = 0;
		  boolean bCount = false;
		  String result = WumSqlQuery.GET_ASSOCIATED_VACCINE_RECORDS;
		  try
		  {
		    dbConnection = getConnection();
		    preparedStmt = dbConnection.prepareStatement(result);
		    preparedStmt.setLong(1, interventionUid.longValue());
		    resultSet = preparedStmt.executeQuery();
		    if(resultSet != null && resultSet.next())
		    resultCount = resultSet.getInt(1);
		    else
		    logger.debug("resultCount: "+resultCount);
		    if(resultCount > 0)
		    {
		            return false;
		    }
		    else
		    {
		            bCount = true;
		    }
		  }
		  catch(Exception e)
		  {
		     logger.fatal("ObservationProxyEJB.unProcessObservation: nbsSecurity Object: " + nbsSecurityObj.getFullName() + " Record cannot be deleted, " + e.getMessage(), e);
		    return isDeletable;
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
		          logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: SQLException: " + sqlex.getMessage(), sqlex);
		          throw new EJBException(sqlex.getMessage(), sqlex);
		    }
		 }
		   try
		   {
		      NedssUtils nedssUtils = new NedssUtils();
		      logger.debug("3");
		      ActController actController = null;
		      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		      logger.debug("!!!!!!!!!!ActController lookup = " + object.toString());
		      ActControllerHome acthome =(ActControllerHome)PortableRemoteObject.narrow(object, ActControllerHome.class);
		      logger.debug("!!!!!!!!!!Found ActControllerHome: " + acthome);
		      actController = acthome.create();
		      InterventionDT interventionDT = actController.getInterventionInfo(interventionUid, nbsSecurityObj);
		      String recordStatusCd = interventionDT.getRecordStatusCd();
		      if (recordStatusCd != null &&
		            recordStatusCd.trim().
		            compareToIgnoreCase(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE) ==
		            0) {

		          throw new NEDSSConcurrentDataException(
		              "The Vaccination you are trying to delete no Longer exists !!");
		        }

		      interventionDT.setItDelete(false);
		      interventionDT.setItDirty(true);
		      PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		      InterventionDT newInterventionDT = (InterventionDT)prepareVOUtils.prepareVO(interventionDT, NBSBOLookup.INTERVENTIONVACCINERECORD, "INT_VAC_DEL", DataTables.INTERVENTION_TABLE, "BASE", nbsSecurityObj);
		      try
		      {
		        actController.setInterventionInfo(newInterventionDT, nbsSecurityObj);

		        //getting the personUid of the Patient Revision for this Vaccination
		        Long personUid = this.getPersonUidOfVaccination(interventionUid, nbsSecurityObj);

		        Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
		        logger.debug("!!!!!!!!!!EntityController lookup = " + object.toString());
		        EntityControllerHome ecHome = (EntityControllerHome)PortableRemoteObject.narrow(lookedUpObj,EntityControllerHome.class);
		        logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
		        EntityController entityController = ecHome.create();

		        //retrieve PersonVO that represents the Patient Revision for this Vaccination by passing in personUid
		        PersonVO personVO = entityController.getPatientRevision(personUid,nbsSecurityObj);
		        personVO.setItDirty(true);
		        personVO.getThePersonDT().setItDirty(true);
		        String businessTriggerCd = NEDSSConstants.PAT_DEL;
		        personUid = entityController.setPatientRevision(personVO, businessTriggerCd, nbsSecurityObj);
		        logger.debug("PersonUid value returned by InterventionProxy.deleteVaccinationProxy(): " + personUid);

		        isDeletable = true;
		        logger.debug("isDeletable is :" + isDeletable);
		        return isDeletable;
		      }
		      catch(NEDSSConcurrentDataException ex)
		      {
		        logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: NEDSSConcurrentDataException: Concurrent access is not allowed: " + ex.getMessage(), ex);
		        throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		      }
		      catch (Exception e)
		      {
		        logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: nbsSecurity Object: " + nbsSecurityObj.getFullName() + " Cannot delete intervention objects, " + e.getMessage(), e);
		        throw new javax.ejb.EJBException(e.getMessage(), e);
		      }
		   }
		  catch(NEDSSConcurrentDataException ex)
		  {
			  logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: NEDSSConcurrentDataException: Concurrent access is not allowed: " + ex.getMessage(), ex);
		        throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		  }
		  catch (Exception e)
		  {
		     if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
		    {
		      logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: NEDSSConcurrentDataException: " + e.getMessage(), e);
		        throw new NEDSSConcurrentDataException(e.getMessage(), e);
		    }
		    return isDeletable;
		   }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InterventionProxyEJB.deleteVaccinationProxy: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
    }

    /**
    * Access method for all vaccinationProxyVO objects for the selected person UID
    * @param personUID      the UID value of the selected person
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  an arraylist which contains all vaccinationProxyVO objects for the selected person UID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException
    */
   public Collection<Object>  getVaccinationTableCollectionForPatient(Long personUID, NBSSecurityObj nbsSecurityObj)
              throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException
  {
    if(!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW))
    {
        logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW) is false");
        throw new NEDSSSystemException("NO PERMISSIONS");
    }
    ArrayList<Object> vaccinationProxyVOColl  = new ArrayList<Object> ();


    try {
          ArrayList<Object> participationDTCollection  = new ArrayList<Object> ();
          EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
          Collection<Object>  uidList = entityProxyHelper.findActivePatientUidsByParentUid(personUID);
          ParticipationDAOImpl participationDAOImpl = null;
          ParticipationDT participationDT = null;
          if(uidList!=null)
          {

            Iterator<Object>  ite = uidList.iterator();
             while(ite.hasNext())
             {
                Long personParentUid = (Long)ite.next();
                if(personParentUid.longValue() != personUID.longValue())
                {
                   logger.info("NBSSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW) is true");
                   logger.debug("personUID = " + personUID.longValue());
                   participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory.getDAO(JNDINames.ACT_PARTICIPATION_DAO_CLASS);
                   ArrayList<Object> col = (ArrayList<Object> )(participationDAOImpl.load(personParentUid.longValue(),NEDSSConstants.SUBJECT_OF_VACCINE, NEDSSConstants.INTERVENTION_CLASS_CODE, NEDSSConstants.CLASS_CD_PAT ));
                   if(col != null && col.size()>0)
                   {
                      participationDT = (ParticipationDT)col.get(0);
                      participationDTCollection.add(participationDT);
                   }
                }
              }
          }
        
         logger.debug("got collection part");
         if (! participationDTCollection.isEmpty()){
            logger.debug("inside add act uids ");
           Iterator<Object>  partIterator = participationDTCollection.iterator();
            while (partIterator.hasNext()) {
                participationDT =  (ParticipationDT) partIterator.next();
               if (participationDT.getRecordStatusCd() != null )
                   if(participationDT.getRecordStatusCd().trim().equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
                  {
                   VaccinationProxyVO vaccinationProxyVO = new VaccinationProxyVO ();
                   vaccinationProxyVO = getVaccinationProxy(participationDT.getActUid(), nbsSecurityObj);
                   if (vaccinationProxyVO != null)
                   {
                      if(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getRecordStatusCd() != null &&
                        vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getRecordStatusCd().trim().equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
                      {
                        vaccinationProxyVOColl.add(vaccinationProxyVO);
                      }
                   }
                  }
            }//while
         }//if
         else { logger.info("ParticipationDT is empty");
         }
    }
    catch (Exception e) {
        logger.fatal("InterventionProxyEJB.getVaccinationTableCollectionForPatient: " + e.getMessage(), e);
        throw new java.rmi.RemoteException(e.getMessage(), e);
    }
    return vaccinationProxyVOColl;
  }

    /**
    * Associates the vaccination with the selected investigation record automatically
    * @param vaccinationProxyVO    the value of the vaccinationProxyVO
    * @param investigationUid      the Uid value of the selected investigation
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the Uid value of the intervention
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
    */
    public Long setVaccinationProxyWithAutoAssoc    (VaccinationProxyVO vaccinationProxyVO, Long investigationUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,
              CreateException, FinderException, NEDSSSystemException
    {

        try {
			Long interventionUid = null;

			ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();


     try {

			interventionUid = setVaccinationProxy(vaccinationProxyVO, nbsSecurityObj);
			VaccinationSummaryVO vaccinationSummaryVO  = new VaccinationSummaryVO();
			vaccinationSummaryVO.setIsTouched(true);
			vaccinationSummaryVO.setIsAssociated(true);
			vaccinationSummaryVO.setInterventionUid(interventionUid);
			Collection<Object>  vaccinationSummaryVOColl = new ArrayList<Object> ();
			vaccinationSummaryVOColl.add(vaccinationSummaryVO);
			manageAutoAssc.setVaccinationAssociationsImpl(investigationUid,vaccinationSummaryVOColl,nbsSecurityObj);


     }
     catch (Exception e) {
			    logger.error("Error while setting setVaccinationProxy auto associations Investigation UID = " +
			            investigationUid + "VaccinationProxyVO = " +
			            vaccinationProxyVO.toString());
			    e.printStackTrace();
			    logger.fatal("InterventionProxyEJB.setVaccinationProxyWithAutoAssoc: Investigation UID = " + investigationUid + " VaccinationProxyVO = " + vaccinationProxyVO.toString() + " " + e.getMessage(), e);
			    return interventionUid;

     }



			  return interventionUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionProxyEJB.setVaccinationProxyWithAutoAssoc: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }


  private boolean checkForNulls(RoleDT roleDT) {
  try {
	boolean notNull = true;
	
	  if (roleDT.getSubjectEntityUid() == null)
	       notNull = false;
	
	  if (roleDT.getCd() ==  null)
	         notNull = false;
	
	  if (roleDT.getSubjectClassCd() == null)
	         notNull = false;
	
	  if (roleDT.getRecordStatusCd() == null)
	        notNull = false;
	  if (roleDT.getScopingRoleCd() == null)
	              notNull = false;
	
	  if (roleDT.getScopingClassCd() == null)
	             notNull = false;
	
	  return notNull;
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("InterventionProxyEJB.checkForNulls: " + e.getMessage(), e);
	throw new javax.ejb.EJBException(e.getMessage(), e);
}


  }


  /**
   * This method retrieves the PersonUid of the Patient Revision for the current Vaccination by
   * executing the following query. This private method is called by deleteVaccinationProxy.This method will
   * return null if no PersonUid is found.
   * @param interventionUid Long
   * @param nbsSecurityObj NBSSecurityObj
   * @return personUid Long
   */
  private Long getPersonUidOfVaccination(Long interventionUid, NBSSecurityObj nbsSecurityObj){

    Long personUid = null;
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    int resultCount = 0;

    try
      {

        String aQuery = WumSqlQuery.SELECT_PATIENT_FOR_VACCINATION_DELETION;
        logger.info("Query = " + aQuery);
        dbConnection = getConnection();
        preparedStmt = dbConnection.prepareStatement(aQuery);
        preparedStmt.setLong(1, interventionUid.longValue());
        preparedStmt.setString(2,NEDSSConstants.SUBJECT_OF_VACCINE);
        preparedStmt.setString(3,NEDSSConstants.PERSON_CLASS_CODE);
        preparedStmt.setString(4,NEDSSConstants.ACTIVE);
        preparedStmt.setString(5,NEDSSConstants.INTERVENTION_CLASS_CODE);
        resultSet = preparedStmt.executeQuery();

        if(resultSet != null && resultSet.next()){
           personUid = new Long(resultSet.getLong(1));
           logger.debug("personUid: " + personUid);
         }


      }
      catch(Exception e)
      {
        logger.fatal("InterventionProxyEJB.getPersonUidOfVaccination: nbsSecurity Object: " + nbsSecurityObj.getFullName() + " Record cannot be deleted, " + e.getMessage(), e);
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
              logger.fatal("InterventionProxyEJB.getPersonUidOfVaccination: " + sqlex.getMessage(), sqlex);
              throw new javax.ejb.EJBException(sqlex.getMessage(), sqlex);
        }
     }
     return personUid;

  }
}



