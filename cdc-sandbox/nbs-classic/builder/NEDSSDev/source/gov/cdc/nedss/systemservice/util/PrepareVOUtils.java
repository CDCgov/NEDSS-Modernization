//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\helpers\\PrepareVOUtils.java

package gov.cdc.nedss.systemservice.util;

import java.util.*;
import java.sql.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.dao.*;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.entity.person.ejb.dao.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.ejb.dao.PlaceDAOImpl;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.organization.ejb.dao.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.act.observation.ejb.dao.*;
import gov.cdc.nedss.act.notification.ejb.dao.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.*;
import gov.cdc.nedss.entity.material.ejb.dao.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactDAOImpl;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.intervention.ejb.dao.*;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.act.treatment.ejb.dao.*;

/**
 * 
* Name:		PrepareVOUtils.java
* Description:	This is the implementation of NBS version specifi checks for core table.
*Added support for isReentrant variable for merge case scenario               
* Release 5.2/2017 : added support for merge investigations
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @updatedByAuthor Pradeep Sharma
 * @company: CSRA
 * @version 5.2
*/

public class PrepareVOUtils
{
    /**
     * For logging
     */
    private static final LogUtils logger = new LogUtils(PrepareVOUtils.class.getName());

   /**
    * @roseuid 3C7A3CA5022F
    */
   public PrepareVOUtils()
   {

   }

   /**
    * This method prepares the Act value object if it is Dirty(Edit,update or Delete)
    * and check null for record Status State and set the System attribures in the rootDTInterface
    * @param theRootDTInterface -- The DT that needs to be prepared
    * @param businessObjLookupName
    * @param businessTriggerCd
    * @param tableName
    * @param moduleCd
    * @param securityObj -- Security Object to Check permissions
    * @return RootDTInterface -- represents the DT whose system attribute needs to be set
    * @throws NEDSSSystemException
    * @throws NEDSSConcurrentDataException
    * @roseuid 3C6BC0B70278
    */
   private RootDTInterface prepareDirtyActVO(RootDTInterface theRootDTInterface, String businessObjLookupName, String businessTriggerCd, String tableName, String moduleCd, NBSSecurityObj nbsSecurityObj)
		throws NEDSSSystemException, NEDSSConcurrentDataException
   
   
   {
   try
    {
	Long uid = theRootDTInterface.getUid();
	logger.debug("prepareDirtyActVO uid = " + uid);

	PrepareVOUtilsDAOImpl prepareVOUtilsDAOImpl = new PrepareVOUtilsDAOImpl();

	logger.debug("businessTriggerCd in prepareDirtyActVO in prepateVOUtil is :"+businessTriggerCd);
	   logger.debug("moduleCd in prepareDirtyActVO in prepateVOUtil is :"+moduleCd);
	   logger.debug("uid in prepareDirtyActVO in prepateVOUtil is :"+uid);
	   logger.debug("tableName in prepareDirtyActVO in prepateVOUtil is :"+tableName);

	PrepareVOUtilsHelper prepareVOUtilsHelper = prepareVOUtilsDAOImpl.callableStatement(businessTriggerCd, moduleCd, uid, tableName);
	String localId = prepareVOUtilsHelper.getLocalIds();//7


	logger.debug("theRootDTInterface.getLocalId() = " + theRootDTInterface.getLocalId());
	logger.debug("prepareVOUtilsHelper.getLocalIds() = " + prepareVOUtilsHelper.getLocalIds());
	Long addUserId =prepareVOUtilsHelper.getAddUserId();//8
	Timestamp addUserTime = prepareVOUtilsHelper.getAddUserTime();//9
	String recordStatusState = prepareVOUtilsHelper.getRecordStatusState();//12
	String objectStatusState = prepareVOUtilsHelper.getObjectStatusState();//13

	if(recordStatusState==null /*||objectStatusState==null*/)
	    //throw new NEDSSSystemException("prepareDirtyActVO - recordStatusState = " + recordStatusState + "- objectStatusState = " + objectStatusState);
	   throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
	logger.debug("recordStatusState state in prepareDirtyActVO = " + recordStatusState);
	logger.debug("objectStatusState state in prepareDirtyActVO = " + objectStatusState);
	    logger.debug("PrograArea is: " + theRootDTInterface.getProgAreaCd() );
	    logger.debug("Jurisdiction code is: " + theRootDTInterface.getJurisdictionCd() );

	if(!(theRootDTInterface.getProgAreaCd()==null) && !(theRootDTInterface.getJurisdictionCd()==null))
	{
	    String progAreaCd = theRootDTInterface.getProgAreaCd();
	    String jurisdictionCd = theRootDTInterface.getJurisdictionCd();
	    long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(progAreaCd, jurisdictionCd);
	    Long aProgramJurisdictionOid = new Long(pajHash);
	    theRootDTInterface.setProgramJurisdictionOid(aProgramJurisdictionOid);
	}

	logger.debug("localId = " + localId);
	logger.debug("addUserId = " + addUserId);
	theRootDTInterface.setAddUserId(addUserId);
	logger.debug("addUserTime = " + addUserTime);
	theRootDTInterface.setAddTime(addUserTime);

	logger.debug("recordStatusState = " + recordStatusState);
	theRootDTInterface.setRecordStatusCd(recordStatusState);

	java.util.Date dateTime = new java.util.Date();
	Timestamp systemTime = new Timestamp(dateTime.getTime());
	logger.debug("systemTime = " + systemTime);
	theRootDTInterface.setRecordStatusTime(systemTime);

	logger.debug("systemTime = " + systemTime);
	theRootDTInterface.setLastChgTime(systemTime);
	logger.debug("nbsSecurityObj.getEntryID() = " + nbsSecurityObj.getEntryID());
	if(!nbsSecurityObj.getEntryID().equals(""))
	{
	    logger.debug("nbsSecurityObj.getEntryID() = " + nbsSecurityObj.getEntryID());
	    theRootDTInterface.setLastChgUserId(new Long(nbsSecurityObj.getEntryID()));
	}
	else
	    logger.debug("nbsSecurityObj.getEntryID() is wierd ");

	logger.debug("setLastChgReasonCd = " + null);
	theRootDTInterface.setLastChgReasonCd(null);
	return theRootDTInterface;
    }
   catch(Exception e)
    {
	e.printStackTrace();
	logger.fatal("Exception in PrepareVOUtils.prepareDirtyActVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
    }
   }

   /**
    * This method prepares the Act value object if it is New(Create)
    * and check null for record Status State and set the System attributes in the rootDTInterface
    * @param theRootDTInterface
    * @param businessObjLookupName
    * @param businessTriggerCd
    * @param tableName
    * @param moduleCd
    * @param securityObj
    * @return RootDTInterface
    * @throws NEDSSSystemException
    * @roseuid 3C7421E4012F
    */
   private RootDTInterface prepareNewActVO(RootDTInterface theRootDTInterface, String businessObjLookupName, String businessTriggerCd, String tableName, String moduleCd, NBSSecurityObj nbsSecurityObj)throws NEDSSSystemException
   {
    try
    {
	Long uid = theRootDTInterface.getUid();
	logger.debug("prepareNewActVO uid = " + uid);
	PrepareVOUtilsDAOImpl prepareVOUtilsDAOImpl = new PrepareVOUtilsDAOImpl();

	PrepareVOUtilsHelper prepareVOUtilsHelper = prepareVOUtilsDAOImpl.callableStatement(businessTriggerCd, moduleCd, uid, tableName);
	String localId = prepareVOUtilsHelper.getLocalIds();//7
	Long addUserId =prepareVOUtilsHelper.getAddUserId();//8
	Timestamp addUserTime = prepareVOUtilsHelper.getAddUserTime();//9
	String recordStatusState = prepareVOUtilsHelper.getRecordStatusState();//12
	String objectStatusState = prepareVOUtilsHelper.getObjectStatusState();//13
	if(recordStatusState==null /*||objectStatusState==null*/)
	    throw new NEDSSSystemException("prepareNewActVO - recordStatusState = " + recordStatusState + "- objectStatusState = " + objectStatusState);

	logger.debug("recordStatusState state in prepareNewActVO = " + recordStatusState);
	logger.debug("objectStatusState state in prepareNewActVO = " + objectStatusState);
	    logger.debug("PrograArea is: " + theRootDTInterface.getProgAreaCd() );
	    logger.debug("Jurisdiction code is: " + theRootDTInterface.getJurisdictionCd() );

	if(!(theRootDTInterface.getProgAreaCd()==null) && !(theRootDTInterface.getJurisdictionCd()==null))
	{
	    String progAreaCd = theRootDTInterface.getProgAreaCd();
	    String jurisdictionCd = theRootDTInterface.getJurisdictionCd();
	    long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(progAreaCd, jurisdictionCd);
	    Long aProgramJurisdictionOid = new Long(pajHash);
	    logger.debug("aProgramJurisdictionOid is : " + aProgramJurisdictionOid);
	    theRootDTInterface.setProgramJurisdictionOid(aProgramJurisdictionOid);
	    logger.debug("aProgramJurisdictionOid from obj  is : " + theRootDTInterface.getProgramJurisdictionOid());

	}

	theRootDTInterface.setLocalId(null);

	theRootDTInterface.setRecordStatusCd(recordStatusState);
	//design changed: theRootDTInterface.setStatusCd(objectStatusState);

	java.util.Date dateTime = new java.util.Date();
	Timestamp systemTime = new Timestamp(dateTime.getTime());
	theRootDTInterface.setRecordStatusTime(systemTime);
	//design changed: theRootDTInterface.setStatusTime(systemTime);

	Long userId = new Long(nbsSecurityObj.getEntryID());
	theRootDTInterface.setLastChgTime(systemTime);
	theRootDTInterface.setAddTime(systemTime);
	theRootDTInterface.setLastChgUserId(userId);
	theRootDTInterface.setAddUserId(userId);
	theRootDTInterface.setLastChgReasonCd(null);

	return theRootDTInterface;
    }
    catch(Exception e)
    {
    	logger.fatal("Exception in PrepareVOUtils.prepareNewActVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + e.getMessage(), e);
    	throw new NEDSSSystemException(e.getMessage(), e);
    }
   }

   /**
    * This method is used to prepare Dirty Acts,Dirty Entities,New Acts And New Entities depending
    * you want to edit,delete or create records
    * @param theRootDTInterface -- The DT to be prepared
    * @param businessObjLookupName
    * @param businessTriggerCd
    * @param tableName
    * @param moduleCd
    * @param securityObj
    * @return RootDTInterface -- the prepared DT(System attribute Set)
    * @throws NEDSSSystemException
    * @throws NEDSSConcurrentDataException
    * @roseuid 3C7422C50093
    */
   public RootDTInterface prepareVO(RootDTInterface theRootDTInterface, String businessObjLookupName, String businessTriggerCd, String tableName, String moduleCd, NBSSecurityObj nbsSecurityObj)throws NEDSSSystemException, NEDSSConcurrentDataException
   {
    try
    {
		////##!! System.out.println("theRootDTInterface isItNew:" + theRootDTInterface.isItNew()+ ":theRootDTInterface.IsItDirty:" +theRootDTInterface.isItDirty() );
		if(theRootDTInterface.isItNew() == false  && theRootDTInterface.isItDirty() == false && theRootDTInterface.isItDelete() == false) {
			throw new NEDSSSystemException("Error while calling prepareVO method in PrepareVOUtils");
		}
	logger.debug("(Boolean.FALSE).equals(new Boolean(theRootDTInterface.tableName)?:" + tableName +":theRootDTInterface.moduleCd:" +moduleCd +":businessTriggerCd:"+businessTriggerCd);
	//Boolean testNewForRootDTInterface = theRootDTInterface.isItNew();
	if(theRootDTInterface.isItDirty() && (Boolean.FALSE).equals(new Boolean(theRootDTInterface.isItNew())))
	{
	  logger.debug("!test1. theRootDTInterface isItNEW?:" + !theRootDTInterface.isItNew() +":theRootDTInterface.IsItDirty:" +!theRootDTInterface.isItDirty() );
	  boolean result = dataConcurrenceCheck(theRootDTInterface, tableName, nbsSecurityObj);
	  if(result)
	  {
	      logger.debug("result in prepareVOUtil is :" + result);
	      //no concurrent dataAccess has occured, hence can continue!
	  }
	  else
	    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException occurred in PrepareVOUtils.Person");
	}

	if(theRootDTInterface.isItNew() && (theRootDTInterface.getSuperclass().toUpperCase().equals("ACT")))
	{
	    logger.debug("new act");
	    theRootDTInterface = this.prepareNewActVO(theRootDTInterface, businessObjLookupName, businessTriggerCd, tableName, moduleCd, nbsSecurityObj);
	}
	else if(theRootDTInterface.isItNew() && (theRootDTInterface.getSuperclass().toUpperCase().equals("ENTITY")))
	{
	    logger.debug("new entity");
	    theRootDTInterface = this.prepareNewEntityVO(theRootDTInterface, businessObjLookupName, businessTriggerCd, tableName, moduleCd, nbsSecurityObj);
	}
	else if(theRootDTInterface.isItDirty() && (theRootDTInterface.getSuperclass().toUpperCase().equals("ACT")))
	{
	    logger.debug("dirty act");
	    theRootDTInterface = this.prepareDirtyActVO(theRootDTInterface, businessObjLookupName, businessTriggerCd, tableName, moduleCd, nbsSecurityObj);
	}
	else if(theRootDTInterface.isItDirty() && (theRootDTInterface.getSuperclass().toUpperCase().equals("ENTITY")))
	{
	    logger.debug("dirty entity");
	    theRootDTInterface = this.prepareDirtyEntityVO(theRootDTInterface, businessObjLookupName, businessTriggerCd, tableName, moduleCd, nbsSecurityObj);
	}
	return theRootDTInterface;
    }
    catch(NEDSSConcurrentDataException ex)
    {
    	logger.fatal("NEDSSConcurrentDataException in PrepareVOUtils.prepareVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.getMessage(), ex);
    }
    catch(Exception e)
    {
    	logger.fatal("Exception in PrepareVOUtils.prepareVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + e.getMessage(), e);
    	throw new NEDSSSystemException(e.getMessage(), e);
    }
   }

   /**
    * This method prepares the Entity value object if it is Dirty(Edit,update or Delete)
    * and check null for record Status State and set the System attribures in the rootDTInterface
    * @param theRootDTInterface -- The DT that needs to be prepared
    * @param businessObjLookupName
    * @param businessTriggerCd
    * @param tableName
    * @param moduleCd
    * @param securityObj -- Security Object to Check permissions
    * @return RootDTInterface -- represents the DT whose system attribute needs to be set
    * @throws NEDSSSystemException
    * @throws NEDSSConcurrentDataException
    * @roseuid 3C7D8D0202C0
    */
   private RootDTInterface prepareDirtyEntityVO(RootDTInterface theRootDTInterface, String businessObjLookupName, String businessTriggerCd, String tableName, String moduleCd, NBSSecurityObj nbsSecurityObj)
	  throws NEDSSSystemException, NEDSSConcurrentDataException
   {
    try
    {

	Long uid = theRootDTInterface.getUid();
	PrepareVOUtilsDAOImpl prepareVOUtilsDAOImpl = new PrepareVOUtilsDAOImpl();
	logger.debug("prepareDirtyEntityVO uid = " + uid);

	PrepareVOUtilsHelper prepareVOUtilsHelper = prepareVOUtilsDAOImpl.callableStatement(businessTriggerCd, moduleCd, uid, tableName);
	String localId = prepareVOUtilsHelper.getLocalIds();//7
	Long addUserId =prepareVOUtilsHelper.getAddUserId();//8
	Timestamp addUserTime = prepareVOUtilsHelper.getAddUserTime();//9
	String recordStatusState = prepareVOUtilsHelper.getRecordStatusState();//12
	String objectStatusState = prepareVOUtilsHelper.getObjectStatusState();//13
//	We decided to set the status_cd and status_time also for entities 08/01/2005
	if(recordStatusState==null ||objectStatusState==null)
	   // throw new NEDSSSystemException("prepareDirtyEntityVO - recordStatusState = " + recordStatusState + "- objectStatusState = " + objectStatusState);
	    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");

	logger.debug("recordStatusState state in prepareDirtyEntityVO = " + recordStatusState);
	logger.debug("objectStatusState state in prepareDirtyEntityVO = " + objectStatusState);
	java.util.Date dateTime = new java.util.Date();
	Timestamp systemTime = new Timestamp(dateTime.getTime());
	theRootDTInterface.setLocalId(localId);
	theRootDTInterface.setAddUserId(addUserId);
	theRootDTInterface.setAddTime(addUserTime);
	theRootDTInterface.setRecordStatusCd(recordStatusState);
	theRootDTInterface.setStatusCd(objectStatusState);
	theRootDTInterface.setRecordStatusTime(systemTime);
	theRootDTInterface.setStatusTime(systemTime);
	theRootDTInterface.setLastChgTime(systemTime);
	theRootDTInterface.setLastChgUserId(new Long(nbsSecurityObj.getEntryID()));
	theRootDTInterface.setLastChgReasonCd(null);

    if(tableName.equals(NEDSSConstants.PATIENT) && (!businessTriggerCd.equals("PAT_NO_MERGE")))
    {
       if(theRootDTInterface instanceof PersonDT)
       {
           ((PersonDT)theRootDTInterface).setDedupMatchInd(null);
           ((PersonDT)theRootDTInterface).setGroupNbr(null);
           ((PersonDT)theRootDTInterface).setGroupTime(null);
       }
    }

    if(tableName.equals(NEDSSConstants.PATIENT) && businessTriggerCd.equals("PAT_NO_MERGE"))
    {
        if(theRootDTInterface instanceof PersonDT)
        {
            ((PersonDT)theRootDTInterface).setGroupNbr(null);
            ((PersonDT)theRootDTInterface).setGroupTime(null);
        }
    }

	return theRootDTInterface;
    }
    catch(Exception e)
    {
    	logger.fatal("Exception in PrepareVOUtils.prepareDirtyEntityVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + e.getMessage(), e);
    	throw new NEDSSSystemException(e.getMessage(), e);
    }
   }

 /**
    * This method is used to populate the system attributes on the 5 association
    * tables (ActRelationship, Participation, Role, EntityLocatoryParticipation, and
    * ActLocatorParticipation).
    *
    * @param assocDTInterface
    * @param securityObj
    * @return AssocDTInterface
    * @throws nedss.NEDSSBusinessServicesLayer.Top Level Exceptions.NEDSSSystemException
    * @roseuid 3CD96F960027
    */
   public AssocDTInterface prepareAssocDT(AssocDTInterface assocDTInterface, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
   {
       try {
		AssocDTInterface aDTInterface = null;
		   String recStatusCd = assocDTInterface.getRecordStatusCd();
		   String statusCd = assocDTInterface.getStatusCd();
		   logger.debug("AssocDTInterface.Statuscode = "+statusCd);
		   logger.debug("AssocDTInterface.recStatusCd = "+recStatusCd);
		   boolean isRealDirty = assocDTInterface.isItDirty();
		   /*
		   if(recStatusCd == null || statusCd == null)
		   {
		  logger.debug("RecordStatusCd or statusCode is null");
		  throw new NEDSSSystemException("RecordStatusCd -----2----"+recStatusCd+"   statusCode--------"+statusCd);
		   }
		   */
		if(recStatusCd == null)
		   {
		  logger.debug("RecordStatusCd is null");
		  throw new NEDSSSystemException("RecordStatusCd -----2----"+recStatusCd+"   statusCode--------"+statusCd);
		   }

		   else if(!(recStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE) || recStatusCd.equals(NEDSSConstants.RECORD_STATUS_INACTIVE)))
		   {
		   logger.debug("RecordStatusCd is not active or inactive");
		   throw new NEDSSSystemException("RecordStatusCd is not active or inactive");
		   }
		   /*else if(!(statusCd.equals(NEDSSConstants.STATUS_ACTIVE) || statusCd.equals(NEDSSConstants.STATUS_INACTIVE)))
		   {
		   logger.debug("StatusCd is not A or I");
		   //throw new NEDSSSystemException("StatusCd is not A or I");
		   }*/
		   else
		   {
		  try
		  {

		   logger.debug("RecordStatusCd or statusCode is not null");
		   assocDTInterface.setAddUserId(null);
		   assocDTInterface.setAddTime(null);
		   java.util.Date dateTime = new java.util.Date();
		   Timestamp systemTime = new Timestamp(dateTime.getTime());
		   assocDTInterface.setRecordStatusTime(systemTime);
		   assocDTInterface.setStatusTime(systemTime);
		   assocDTInterface.setLastChgTime(systemTime);
		  }
		   catch(Exception e)
		  {
			e.printStackTrace();
		  }
		   if(!nbsSecurityObj.getEntryID().equals(""))
		   {
		     logger.debug("nbsSecurityObj.getEntryID() = " + nbsSecurityObj.getEntryID());
		     assocDTInterface.setLastChgUserId(new Long(nbsSecurityObj.getEntryID()));
		   }
		   else
		   {
		      logger.debug("nbsSecurityObj.getEntryID() is NULL ");
		      throw new NEDSSSystemException("nbsSecurityObj.getEntryID() is NULL ");
		   }
		    assocDTInterface.setLastChgReasonCd(null);
		    aDTInterface = assocDTInterface;
		    logger.debug("DT Prepared");
		   }
		   if(!isRealDirty) aDTInterface.setItDirty(false);//Re-set the flag to original value if necessary
		  return aDTInterface;
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		logger.fatal("Exception in PrepareVOUtils.prepareAssocDT: RecordStatusCd: " + assocDTInterface.getRecordStatusCd() +  ", " + e.getMessage(), e);
    	throw new NEDSSSystemException(e.getMessage(), e);
	}
    }


   /**
    * This method prepares the Entity value object if it is New(Create)
    *
    * @param theRootDTInterface -- The DT that needs to be prepared
    * @param businessObjLookupName
    * @param businessTriggerCd
    * @param tableName
    * @param moduleCd
    * @param securityObj -- Security Object to Check permissions
    * @return RootDTInterface -- represents the DT whose system attribute needs to be set
    * @throws NEDSSSystemException
    * @roseuid 3C7D8D0E0172
    */
   private RootDTInterface prepareNewEntityVO(RootDTInterface theRootDTInterface, String businessObjLookupName, String businessTriggerCd, String tableName, String moduleCd, NBSSecurityObj nbsSecurityObj)throws NEDSSSystemException
   {
    try
    {
	logger.debug("prepareNewEntityVO uid = " + theRootDTInterface.getUid());
	Long uid = theRootDTInterface.getUid();
	PrepareVOUtilsDAOImpl prepareVOUtilsDAOImpl = new PrepareVOUtilsDAOImpl();
	logger.debug("prepareDirtyEntityVO uid = " + uid);

	PrepareVOUtilsHelper prepareVOUtilsHelper = prepareVOUtilsDAOImpl.callableStatement(businessTriggerCd, moduleCd, uid, tableName);
	String localId = prepareVOUtilsHelper.getLocalIds();//7
	Long addUserId =prepareVOUtilsHelper.getAddUserId();//8
	Timestamp addUserTime = prepareVOUtilsHelper.getAddUserTime();//9
	String recordStatusState = prepareVOUtilsHelper.getRecordStatusState();//12
	String objectStatusState = prepareVOUtilsHelper.getObjectStatusState();//13
	//We decided to set the status_cd and status_time also for entities 08/01/2005
	if(recordStatusState==null ||objectStatusState==null)
	   // throw new NEDSSSystemException("prepareDirtyEntityVO - recordStatusState = " + recordStatusState + "- objectStatusState = " + objectStatusState);
	    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");

	logger.debug("recordStatusState state in prepareDirtyEntityVO = " + recordStatusState);
	logger.debug("objectStatusState state in prepareDirtyEntityVO = " + objectStatusState);
	java.util.Date dateTime = new java.util.Date();
	Timestamp systemTime = new Timestamp(dateTime.getTime());
	Long userId = new Long(nbsSecurityObj.getEntryID());
	theRootDTInterface.setLocalId(localId);
	theRootDTInterface.setAddUserId(userId);
	theRootDTInterface.setAddTime(systemTime);
	theRootDTInterface.setRecordStatusCd(recordStatusState);
	theRootDTInterface.setStatusCd(objectStatusState);
	theRootDTInterface.setRecordStatusTime(systemTime);
	theRootDTInterface.setStatusTime(systemTime);
	theRootDTInterface.setLastChgTime(systemTime);
	theRootDTInterface.setLastChgUserId(userId);
	theRootDTInterface.setLastChgReasonCd(null);

    if(tableName.equals(NEDSSConstants.PATIENT) && (!businessTriggerCd.equals("PAT_NO_MERGE")))
    {
       if(theRootDTInterface instanceof PersonDT)
       {
           ((PersonDT)theRootDTInterface).setDedupMatchInd(null);
           ((PersonDT)theRootDTInterface).setGroupNbr(null);
           ((PersonDT)theRootDTInterface).setGroupTime(null);
       }
    }

    if(tableName.equals(NEDSSConstants.PATIENT) && businessTriggerCd.equals("PAT_NO_MERGE"))
    {
        if(theRootDTInterface instanceof PersonDT)
        {
            ((PersonDT)theRootDTInterface).setGroupNbr(null);
            ((PersonDT)theRootDTInterface).setGroupTime(null);
        }
    }

	return theRootDTInterface;
    }
    catch(Exception e)
    {
	e.printStackTrace();
	logger.fatal("Exception in PrepareVOUtils.prepareNewEntityVO: LocalID: " + theRootDTInterface.getLocalId() +  ", businessTriggerCd: " + businessTriggerCd + ", tableName: " + tableName + ", " + e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
    }
   }

// for testing
    public static void main(String arg[])
    {
     try
     {
	logger.setLogLevel(6);
	logger.debug("werealoggin");
	PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
/*
	gov.cdc.nedss.cdm.helpers.PersonDT thePersonDT = new gov.cdc.nedss.cdm.helpers.PersonDT();
	thePersonDT.setPersonUid(new Long(1));
	thePersonDT.setItNew(false);
	thePersonDT.setItDirty(true);
	String businessObjLookupName = "EJBNameGoesHere";
	String businessTriggerCd = "INT_CR";
	String tableName = "PERSON";
	String moduleCd = "BASE";
	SecurityObj securityObj = new SecurityObj();
	securityObj.setUserId("1001001001001001001");

	RootDTInterface theRootDTInterface = prepareVOUtils.prepareVO(thePersonDT, businessObjLookupName, businessTriggerCd, tableName, moduleCd, securityObj);
	logger.debug("returned from prepareVO");

	logger.debug("getUid = " + theRootDTInterface.getUid());
	logger.debug("getSuperclass = " + theRootDTInterface.getSuperclass());
	logger.debug("getProgAreaAccessPermis = " + theRootDTInterface.getProgAreaAccessPermis());
	logger.debug("getOrgAccessPermis = " + theRootDTInterface.getOrgAccessPermis());
	logger.debug("getLocalId = " + theRootDTInterface.getLocalId());
	logger.debug("getAddUserId = " + theRootDTInterface.getAddUserId());
	logger.debug("getAddTime = " + theRootDTInterface.getAddTime());

	logger.debug("getRecordStatusCd = " + theRootDTInterface.getRecordStatusCd());
	logger.debug("getStatusCd = " + theRootDTInterface.getStatusCd());

	logger.debug("getRecordStatusTime = " + theRootDTInterface.getRecordStatusTime());
	logger.debug("getStatusTime = " + theRootDTInterface.getStatusTime());

	logger.debug("getLastChgTime = " + theRootDTInterface.getLastChgTime());
	logger.debug("getLastChgUserId = " + theRootDTInterface.getLastChgUserId());
	logger.debug("getLastChgReasonCd = " + theRootDTInterface.getLastChgReasonCd());
*//*
	gov.cdc.nedss.wum.helpers.ObservationDT observationDT = new gov.cdc.nedss.wum.helpers.ObservationDT();
	observationDT.setObservationUid(new Long(100));
	observationDT.setItNew(true);
	observationDT.setItDirty(false);
	String businessObjLookupName = "EJBNameGoesHere";
	String businessTriggerCd = "INT_CR";
	String tableName = "OBSERVATION";
	String moduleCd = "BASE";
	SecurityObj securityObj = new SecurityObj();
	securityObj.setUserId("1001001001001001001");


	String businessObjLookupName = "EJBNameGoesHere";
	String businessTriggerCd = "NOT_MSG_FAIL";
	String tableName = "Notification";
	String moduleCd = "BASE";
	NBSSecurityObj nbsSecurityObj = new NBSSecurityObj();
	securityObj.setUserId("1001001001001001001");

	gov.cdc.nedss.wum.helpers.NotificationDT notificationDT = new gov.cdc.nedss.wum.helpers.NotificationDT();
	notificationDT.setNotificationUid(new Long(605166160));
	notificationDT.setItNew(false);
	notificationDT.setItDirty(true);

	RootDTInterface theRootDTInterface = prepareVOUtils.prepareVO(notificationDT, businessObjLookupName, businessTriggerCd, tableName, moduleCd, securityObj);
	logger.debug("returned from prepareVO");

	logger.debug("getUid = " + theRootDTInterface.getUid());
	logger.debug("getSuperclass = " + theRootDTInterface.getSuperclass());
	logger.debug("getProgAreaAccessPermis = " + theRootDTInterface.getProgAreaAccessPermis());
	logger.debug("getOrgAccessPermis = " + theRootDTInterface.getOrgAccessPermis());
	logger.debug("getLocalId = " + theRootDTInterface.getLocalId());
	logger.debug("getAddUserId = " + theRootDTInterface.getAddUserId());
	logger.debug("getAddTime = " + theRootDTInterface.getAddTime());

	logger.debug("getRecordStatusCd = " + theRootDTInterface.getRecordStatusCd());
	logger.debug("getStatusCd = " + theRootDTInterface.getStatusCd());

	logger.debug("getRecordStatusTime = " + theRootDTInterface.getRecordStatusTime());
	logger.debug("getStatusTime = " + theRootDTInterface.getStatusTime());

	logger.debug("getLastChgTime = " + theRootDTInterface.getLastChgTime());
	logger.debug("getLastChgUserId = " + theRootDTInterface.getLastChgUserId());
	logger.debug("getLastChgReasonCd = " + theRootDTInterface.getLastChgReasonCd());

	 gov.cdc.nedss.wum.helpers.NotificationDT notificationDT = (gov.cdc.nedss.wum.helpers.NotificationDT)theRootDTInterface;
	logger.debug("notificationDT.isItNew() - : " + notificationDT.isItNew());
	logger.debug("notificationDT.isItDirty() - : " + notificationDT.isItDirty());
	logger.debug("notificationDT.getActivityFromTime() - : " + notificationDT.getActivityFromTime());
	logger.debug("notificationDT.getAddReasonCd() - : " + notificationDT.getAddReasonCd());
	logger.debug("notificationDT.getAddTime() - : " + notificationDT.getAddTime());
	logger.debug("notificationDT.getAddUserId() - : " + notificationDT.getAddUserId());
	logger.debug("notificationDT.getCaseClassCd() - : " + notificationDT.getCaseClassCd());
	logger.debug("notificationDT.getCaseConditionCd() - : " + notificationDT.getCaseConditionCd());
	logger.debug("notificationDT.getCdDescTxt() - : " + notificationDT.getCdDescTxt());
	logger.debug("notificationDT.getTxt() - : " + notificationDT.getTxt());
	logger.debug("notificationDT.getLocalId() - : " + notificationDT.getLocalId());
	logger.debug("notificationDT.getStatusCd() - : " + notificationDT.getStatusCd());
	logger.debug("notificationDT.getStatusTime() - : " + notificationDT.getStatusTime());
	logger.debug("notificationDT.getNedssVersionNbr() - : " + notificationDT.getNedssVersionNbr());
	logger.debug("notificationDT.getJurisdictionCd() - : " + notificationDT.getJurisdictionCd());
	logger.debug("notificationDT.getUid() - : " + notificationDT.getUid());
	logger.debug("notificationDT.getRecordStatusCd() - : " + notificationDT.getRecordStatusCd());
*/     }
     catch (Exception e)
     {
	e.printStackTrace();
	logger.fatal("Exception in PrepareVOUtils.main: " + e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
     }
    }
    /**
     * This method checks for data Concurrency by comparying the version control
	* number of the accessed record with the current record in database.
	* @param theRootDTInterface -- the DT for which the data Concurrency is to be checked
	* @param tableName -- The Table Name to get version Ctrl no. for concurrency Check
	* @param nbsSecurityObj -- for permission check
	* @return boolean
	* @throws NEDSSSystemException
     */
    private boolean dataConcurrenceCheck(RootDTInterface theRootDTInterface, String tableName, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
    {

      try
      {
	if(tableName.equalsIgnoreCase(DataTables.PERSON_TABLE))
	{
	  PersonDAOImpl pDao = new PersonDAOImpl();
	  PersonDT personDT  = (PersonDT)pDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((PersonDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(personDT.getVersionCtrlNbr() == null || personDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }else{
		  PersonDT newPersonDT=(PersonDT)theRootDTInterface;
		  
		  if(personDT.getVersionCtrlNbr() == null || (personDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()-1) && newPersonDT.isReentrant())) {
			  return true;
		  }
			  
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.ORGANIZATION_TABLE))
	{
	  OrganizationDAOImpl oDao = new OrganizationDAOImpl();
	  OrganizationDT organizationDT  = (OrganizationDT)oDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((OrganizationDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(organizationDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.PLACE_TABLE))
	{
	  PlaceDAOImpl pDao = new PlaceDAOImpl();
	  PlaceDT placeDT  = (PlaceDT)pDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((PlaceDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(placeDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.OBSERVATION_TABLE))
	{
	  ObservationDAOImpl oDao = new ObservationDAOImpl();
	  ObservationDT observationDT  = (ObservationDT)oDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((ObservationDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(observationDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.NOTIFICATION_TABLE))
	{
	  NotificationDAOImpl nDao = new NotificationDAOImpl();
	  NotificationDT notificationDT  = (NotificationDT)nDao.loadObject(theRootDTInterface.getUid().longValue());
	  logger.debug("notificationDT!!!!" +notificationDT);
	  logger.debug("theRootDTInterface!!!!" +theRootDTInterface);
	  logger.debug("notificationDT version control number :" + notificationDT.getVersionCtrlNbr());
	  logger.debug("theRootDTInterface.notificationDT version control number :" + theRootDTInterface.getVersionCtrlNbr());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((NotificationDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(notificationDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.PUBLIC_HEALTH_CASE_TABLE))
	{
	  PublicHealthCaseDAOImpl pDao = new PublicHealthCaseDAOImpl();
	  PublicHealthCaseDT phcDT  = (PublicHealthCaseDT)pDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((PublicHealthCaseDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  PublicHealthCaseDT newPhcDT= (PublicHealthCaseDT)theRootDTInterface;
	  /**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
	  if(phcDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()) 
			  || (phcDT.getVersionCtrlNbr().equals(newPhcDT.getVersionCtrlNbr()-1) && newPhcDT.isReentrant()))*/
	  if(phcDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()) )
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.MATERIAL_TABLE))
	{
	  MaterialDAOImpl mDao = new MaterialDAOImpl();
	  MaterialDT materialDT  = (MaterialDT)mDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((MaterialDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(materialDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
	if(tableName.equalsIgnoreCase(DataTables.INTERVENTION_TABLE) )
	{
	  InterventionDAOImpl pDao = new InterventionDAOImpl();
	  InterventionDT interventionDT  = (InterventionDT)pDao.loadObject(theRootDTInterface.getUid().longValue());
	  if(theRootDTInterface.getVersionCtrlNbr() == null)
	    ((InterventionDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
	  if(interventionDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
	  {
	    return true;
	  }
	}
        if(tableName.equalsIgnoreCase(DataTables.TREATMENT_TABLE) )
        {
          TreatmentDAOImpl pDao = new TreatmentDAOImpl();
          TreatmentDT treatmentDT  = (TreatmentDT)pDao.loadObject(theRootDTInterface.getUid().longValue());
          if(theRootDTInterface.getVersionCtrlNbr() == null)
            ((TreatmentDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
          if(treatmentDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
          {
            return true;
          }
        }
        if(tableName.equalsIgnoreCase(DataTables.NBS_DOCUMENT_TABLE))
    	{
    	  NbsDocumentDAOImpl nbsDocDao = new NbsDocumentDAOImpl();
    	  NBSDocumentDT nbsDT  = (NBSDocumentDT)nbsDocDao.loadObject(theRootDTInterface.getUid().longValue());
    	  if(theRootDTInterface.getVersionCtrlNbr() == null)
    	    ((NBSDocumentDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
    	  if(nbsDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
    	  {
    	    return true;
    	  }
    	}
        if(tableName.equalsIgnoreCase(DataTables.CT_CONTACT))
    	{
        	CTContactDAOImpl cTContactDAOImpl = new CTContactDAOImpl();
        	CTContactDT cTContactDT  = (CTContactDT)cTContactDAOImpl.loadObject(theRootDTInterface.getUid().longValue());
    	  if(theRootDTInterface.getVersionCtrlNbr() == null)
    	    ((CTContactDT)theRootDTInterface).setVersionCtrlNbr(new Integer(1));
    	  if(cTContactDT.getVersionCtrlNbr().equals(theRootDTInterface.getVersionCtrlNbr()))
    	  {
    	    return true;
    	  }
    	}

	return false;
      }
      catch(NEDSSSystemException ex)
      {
	logger.fatal("Concurrent Access Exception in PrepareVOUtils.dataConcurrenceCheck: LocalID: " + theRootDTInterface.getLocalId() + ", tableName: " + tableName + ", " + ex.getMessage(), ex);
	throw new NEDSSSystemException(ex.getMessage(), ex);
      }
      catch(Exception e)
      {
	e.printStackTrace();
	logger.fatal("Exception in PrepareVOUtils.dataConcurrenceCheck: LocalID: " + theRootDTInterface.getLocalId() + ", tableName: " + tableName + ", nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
      }
    }
}
