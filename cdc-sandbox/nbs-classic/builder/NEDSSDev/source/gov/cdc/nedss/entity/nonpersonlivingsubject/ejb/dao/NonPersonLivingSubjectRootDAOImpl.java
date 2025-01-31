/**
* Name:		NonPersonLivingSubjectRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               NonPersonLivingSubject value object in the NonPersonLivingSubject entity bean.
*               This class encapsulates all the JDBC calls made by the NonPersonLivingSubjectEJB
*               for a NonPersonLivingSubject object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of NonPersonLivingSubjectEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Rick Randazzo & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao;


import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.NonPersonLivingSubjectDT;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.NonPersonLivingSubjectVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.EntityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

public class NonPersonLivingSubjectRootDAOImpl extends BMPBase
{
    //For LOg4J logging
    static final LogUtils logger = new LogUtils(NonPersonLivingSubjectRootDAOImpl.class.getName());

    private NonPersonLivingSubjectVO nplsvo = null;
    private long nonPersonLivingSubjectUID;
    private  NonPersonLivingSubjectDAOImpl nonPersonLivingSubjectDAO = null;
    private  EntityIdDAOImpl entityIdDAO = null;
    private  EntityLocatorParticipationDAOImpl entityLocatorParticipationDAO = null;
    //role and participation DAOs for place
    private  RoleDAOImpl roleDAOImpl = null;
    private  ParticipationDAOImpl nonPersonLivingSubjectParticipationDAOImpl = null;

    // for testing puposes - causes console print statements to print if set tot true
    private boolean DEBUG = false;

    public NonPersonLivingSubjectRootDAOImpl()
    {
    }
     /**
      * This method creates a new nonPersonLivingSubject record and returns the nonPersonLivingSubjectUID for this nonPersonLivingSubject.
      * @J2EE_METHOD  --  create
      * @param obj       the Object
      * @throws NEDSSSystemException
      **/
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.nplsvo = (NonPersonLivingSubjectVO)obj;
	
	        /**
	        *  Inserts NonPersonLivingSubjectDT object
	        */
	
	        if (this.nplsvo != null)
	        {
	            nonPersonLivingSubjectUID = insertNonPersonLivingSubject(this.nplsvo);
	             if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - NonPersonLivingSubject UID = " + nonPersonLivingSubjectUID);
	            (this.nplsvo.getTheNonPersonLivingSubjectDT()).setNonPersonUid(new Long(nonPersonLivingSubjectUID));
	        }
	
	        /**
	        * Inserts EntityIdDT collection
	        */
	
	        if (this.nplsvo != null && this.nplsvo.getTheEntityIdDTCollection() != null){
	           if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - this.nplsvo.getTheEntityIdDTCollection() = null");
	          insertEntityIDs(this.nplsvo);
	       }
	
	        /**
	        * Inserts EntityLocatorParticipationDT collection
	        */
	
	        if (this.nplsvo != null && this.nplsvo.getTheEntityLocatorParticipationDTCollection() != null)
	        {
	             if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - this.nplsvo.getTheEntityLocatorParticipationDTCollection() != null");
	            insertEntityLocatorParticipations(this.nplsvo);
	        }
	
	        this.nplsvo.setItNew(false);
	        this.nplsvo.setItDirty(false);
	        return ((((NonPersonLivingSubjectVO)obj).getTheNonPersonLivingSubjectDT().getNonPersonUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
     /**
      * This method is used to update a nonPersonLivingSubject record.
      * @J2EE_METHOD  --  store
      * @param obj       the Object
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
      **/
    public void store(Object obj) throws NEDSSSystemException,
                NEDSSConcurrentDataException
    {
    	try{
	         if(DEBUG) logger.debug("In NonPersonLivingSubjectRootDAOImpl Store");
	         if(DEBUG) logger.debug("In NonPersonLivingSubjectnRootDAOImpl Store - obj = " + obj);
	         this.nplsvo = (NonPersonLivingSubjectVO)obj;
	         if(DEBUG) logger.debug("In NonPersonLivingSubjectRootDAOImpl Store - nplsvo = " + nplsvo.toString());
	
	        /**
	        *  Updates NonPersonLivingSubjectDT object
	        */
	        if(this.nplsvo.getTheNonPersonLivingSubjectDT() != null && this.nplsvo.getTheNonPersonLivingSubjectDT().isItNew())
	        {
	             if(DEBUG) logger.debug("In NonPersonLivingSubjectRootDAOImpl Store - this.nplsvo.getTheNonPersonLivingSubjectDT() != null & isItNew");
	            insertNonPersonLivingSubject(this.nplsvo);
	            this.nplsvo.getTheNonPersonLivingSubjectDT().setItNew(false);
	            this.nplsvo.getTheNonPersonLivingSubjectDT().setItDirty(false);
	        }
	        else if(this.nplsvo.getTheNonPersonLivingSubjectDT() != null && this.nplsvo.getTheNonPersonLivingSubjectDT().isItDirty())
	        {
	             if(DEBUG) logger.debug("In NonPersonLivingSubjectRootDAOImpl Store - this.nplsvo.getTheNonPersonLivingSubjectDT() != null & isItDirty()");
	            updateNonPersonLivingSubject(this.nplsvo);
	            this.nplsvo.getTheNonPersonLivingSubjectDT().setItDirty(false);
	            this.nplsvo.getTheNonPersonLivingSubjectDT().setItNew(false);
	        }
	
	
	        /**
	         * Updates entity ids collection
	         */
	        if(this.nplsvo.getTheEntityIdDTCollection() != null)
	        {
	             if(DEBUG) logger.debug("In OrganizatrionRootDAOImpl Store - this.nplsvo.getTheEntityIdDTCollection() != null");
	            updateEntityIDs(this.nplsvo);
	             if(DEBUG) logger.debug("In OrganizatrionRootDAOImpl completed updateEntityIDs");
	        }
	
	        /**
	        * Updates entity locator participations collection
	        */
	        if (this.nplsvo.getTheEntityLocatorParticipationDTCollection() != null)
	        {
	             if(DEBUG) logger.debug("In OrganizatrionRootDAOImpl Store - this.nplsvo.getTheEntityLocatorParticipationDTCollection() != null");
	            updateEntityLocatorParticipations(this.nplsvo);
	             if(DEBUG) logger.debug("In OrganizatrionRootDAOImpl completed updateEntityLocatorParticipations");
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
   /**
      * This method is used to delete a nonPersonLivingSubject record.
      * @J2EE_METHOD  --  remove
      * @param nonPersonLivingSubjectUID       the long
      * @throws NEDSSSystemException
    **/
    public void remove(long nonPersonLivingSubjectUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes EntityLocatorParticipationDT collection
	        */
	
	        removeEntityLocatorParticipations(nonPersonLivingSubjectUID);
	
	        /**
	        * Removes EntityIdDT Collection
	        */
	
	        removeEntityIDs(nonPersonLivingSubjectUID);
	
	        /**
	        *  Removes NonPersonLivingSubjectDT
	        */
	
	        removeNonPersonLivingSubject(nonPersonLivingSubjectUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
      * This method loads a NonPersonLivingSubjectDT object for a given nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  loadObject
      * @param nonPersonLivingSubjectUID       the long
      * @throws NEDSSSystemException
    **/
    public Object loadObject(long nonPersonLivingSubjectUID) throws NEDSSSystemException
    {
    	try{
	        this.nplsvo = new NonPersonLivingSubjectVO();
	
	        /**
	        *  Selects NonPersonLivingSubjectDT object
	        */
	
	        NonPersonLivingSubjectDT pDT = selectNonPersonLivingSubject(nonPersonLivingSubjectUID);
	        this.nplsvo.setTheNonPersonLivingSubjectDT(pDT);
	
	        /**
	        * Selects EntityIdDT collection
	        */
	
	        Collection<Object>  idColl = selectEntityIDs(nonPersonLivingSubjectUID);
	        this.nplsvo.setTheEntityIdDTCollection(idColl);
	
	        /**
	        * Selects EntityLocatorParticipationDT collection
	        */
	
	        Collection<Object>  elpColl = selectEntityLocatorParticipations(nonPersonLivingSubjectUID);
	        this.nplsvo.setTheEntityLocatorParticipationDTCollection(elpColl);
	
	        //Selects RoleDTcollection
	        Collection<Object>  roleColl = selectRoleDTCollection(nonPersonLivingSubjectUID);
	        this.nplsvo.setTheRoleDTCollection(roleColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object>  parColl = selectParticipationDTCollection(nonPersonLivingSubjectUID);
	        this.nplsvo.setTheParticipationDTCollection(parColl);
	
	        this.nplsvo.setItNew(false);
	        this.nplsvo.setItDirty(false);
	        return this.nplsvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
      * This method is used to determine is a nonPersonLivingSubject record exists for a given nonPersonLivingSubjectUID.
      * @J2EE_METHOD  --  findByPrimaryKey
      * @param nonPersonLivingSubjectUID       the long
      * @throws NEDSSSystemException
   **/
    public Long findByPrimaryKey(long nonPersonLivingSubjectUID) throws NEDSSSystemException
    {
    	try{
	        /**
	         * Finds nonPersonLivingSubject object
	         */
	         if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - Starting to find by primarykey!");
	        Long nonPersonLivingSubjectPK = findNonPersonLivingSubject(nonPersonLivingSubjectUID);
	         if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - Done find by primarykey!");
	        return nonPersonLivingSubjectPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
   /**
      * This method is used to insert the nonPersonLivingSubjectDT onto the NonPersonLivingSubjectVO.
      * @J2EE_METHOD  --  insertPerson
      * @param nplsvo       the NonPersonLivingSubjectVO
      * @throws EJBException
      * @throws NEDSSSystemException
    **/
    private long insertNonPersonLivingSubject(NonPersonLivingSubjectVO nplsvo) throws EJBException, NEDSSSystemException
    {
        try
        {
            if(nonPersonLivingSubjectDAO == null)
            {
                //nonPersonLivingSubjectDAO = new NonPersonLivingSubjectDAOImpl();
                nonPersonLivingSubjectDAO = (NonPersonLivingSubjectDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_DAO_CLASS);
            }
            if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - NonPersonLivingSubject DT = " + nplsvo.getTheNonPersonLivingSubjectDT());
            nonPersonLivingSubjectUID = nonPersonLivingSubjectDAO.create(nplsvo.getTheNonPersonLivingSubjectDT());
            if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl - NonPersonLivingSubject root uid = " + nonPersonLivingSubjectUID);
            nplsvo.getTheNonPersonLivingSubjectDT().setNonPersonUid(new Long(nonPersonLivingSubjectUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertNonPersonLivingSubject()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        return nonPersonLivingSubjectUID;
    }

    private void insertEntityIDs(NonPersonLivingSubjectVO nplsvo) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                //entityIdDAO = new EntityIdDAOImpl();
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            nonPersonLivingSubjectUID = entityIdDAO.create((nplsvo.getTheNonPersonLivingSubjectDT().getNonPersonUid()).longValue(), nplsvo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertEntityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }

    }

    private void insertEntityLocatorParticipations(NonPersonLivingSubjectVO nplsvo) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                //entityLocatorParticipationDAO = new EntityLocatorParticipationDAOImpl();
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            nonPersonLivingSubjectUID = entityLocatorParticipationDAO.create((nplsvo.getTheNonPersonLivingSubjectDT().getNonPersonUid()).longValue(), nplsvo.getTheEntityLocatorParticipationDTCollection());
            //nplsvo.getTheEntityLocatorParticipationDTCollection().setNonPersonLivingSubjectUid(new Long(nonPersonLivingSubjectUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertEntityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        /*catch(NEDSSDAOAppException ndapex)
        {
            logger.fatal("Fails insertEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }*/
    }

    private NonPersonLivingSubjectDT selectNonPersonLivingSubject(long nonPersonLivingSubjectUID) throws NEDSSSystemException
    {
        try
        {
            if(nonPersonLivingSubjectDAO == null)
            {
                //nonPersonLivingSubjectDAO = new NonPersonLivingSubjectDAOImpl();
                nonPersonLivingSubjectDAO = (NonPersonLivingSubjectDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_DAO_CLASS);
            }
            return ((NonPersonLivingSubjectDT)nonPersonLivingSubjectDAO.loadObject(nonPersonLivingSubjectUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectNonPersonLivingSubject()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
       
    }

    private Collection<Object>  selectEntityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                //entityIdDAO = new EntityIdDAOImpl();
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            return (entityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectEntityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        
    }

    private Collection<Object>  selectEntityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                //entityLocatorParticipationDAO = new EntityLocatorParticipationDAOImpl();
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (entityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectEntityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        
    }

    private void removeNonPersonLivingSubject(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(nonPersonLivingSubjectDAO == null)
            {
                nonPersonLivingSubjectDAO = (NonPersonLivingSubjectDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_DAO_CLASS);
            }
            nonPersonLivingSubjectDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails removeNonPersonLivingSubject()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
      
    }

    private void removeEntityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails removeEntityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
  
    }

    private void removeEntityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails removeEntityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
       
    }

    private void updateNonPersonLivingSubject(NonPersonLivingSubjectVO nplsvo)
                       throws NEDSSSystemException,
                       NEDSSConcurrentDataException
    {
        try
        {
            if(nonPersonLivingSubjectDAO == null)
            {
                //nonPersonLivingSubjectDAO = new NonPersonLivingSubjectDAOImpl();
                nonPersonLivingSubjectDAO = (NonPersonLivingSubjectDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_DAO_CLASS);
            }
            nonPersonLivingSubjectDAO.store(nplsvo.getTheNonPersonLivingSubjectDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateNonPersonLivingSubject()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateNonPersonLivingSubject() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
    }

    private void updateEntityIDs(NonPersonLivingSubjectVO nplsvo) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                //entityIdDAO = new EntityIdDAOImpl();
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.store(nplsvo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateEntityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }

    }

    private void updateEntityLocatorParticipations(NonPersonLivingSubjectVO nplsvo)
              throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                //entityLocatorParticipationDAO = new EntityLocatorParticipationDAOImpl();
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.store(nplsvo.getTheEntityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateEntityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
    }

    private Long findNonPersonLivingSubject(long nonPersonLivingSubjectUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
             if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl.findNonPersonLivingSubject");
            if(nonPersonLivingSubjectDAO == null)
            {
                //nonPersonLivingSubjectDAO = new NonPersonLivingSubjectDAOImpl();
                nonPersonLivingSubjectDAO = (NonPersonLivingSubjectDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_DAO_CLASS);
            }
            findPK = nonPersonLivingSubjectDAO.findByPrimaryKey(nonPersonLivingSubjectUID);
             if(DEBUG) logger.debug("NonPersonLivingSubjectRootDAOImpl.findNonPersonLivingSubject findPK=" + findPK);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findNonPersonLivingSubject()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        return findPK;
    }

//*************************** main for testing purposes ********************************
    public static void main(String args[]){
      logger.debug("NonPersonLivingSubjectRootDAOImpl - Doing the main thing");
      try{
        NonPersonLivingSubjectDAOImpl orgDAOI = new NonPersonLivingSubjectDAOImpl();

        Long uid = new Long(286);

        NonPersonLivingSubjectDT orgDT = new NonPersonLivingSubjectDT();
        orgDT.setAddReasonCd("just seemed right");
        orgDT.setNonPersonUid(uid);
        orgDT.setItDirty(true);
        orgDT.setItNew(false);

        NonPersonLivingSubjectVO orgVO = new NonPersonLivingSubjectVO();
        orgVO.setTheNonPersonLivingSubjectDT(orgDT);
        ArrayList<Object> areid = new ArrayList<Object> ();
        EntityIdDT eiddt = new EntityIdDT();
        //eiddt.setEntityUid(uid);
        eiddt.setAddReasonCd("some reason");
        eiddt.setEntityIdSeq(new Integer(222));
        eiddt.setItDirty(true);
        eiddt.setItNew(false);
        areid.add(eiddt);
        orgVO.setTheEntityIdDTCollection(areid);

        NonPersonLivingSubjectRootDAOImpl orgRootDAO = new NonPersonLivingSubjectRootDAOImpl();
        logger.debug("NonPersonLivingSubjectRootDAOImpl - Doing the main thing");

//        Long uid = new Long(orgRootDAO.create(orgVO));
        orgRootDAO.store(orgVO);

        Long x = orgRootDAO.findByPrimaryKey(uid.longValue());
        logger.debug("NonPersonLivingSubjectRootDAOImpl - x = " + x);
        orgVO = (NonPersonLivingSubjectVO)orgRootDAO.loadObject(uid.longValue());
        logger.debug("NonPersonLivingSubjectRootDAOImpl - done load");
        logger.debug("NonPersonLivingSubjectRootDAOImpl - add reason code = " + orgVO.getTheNonPersonLivingSubjectDT().getAddReasonCd());
        x = orgDAOI.findByPrimaryKey(uid.longValue());
        logger.debug("NonPersonLivingSubjectRootDAOImpl - x = " + x);
        ArrayList<Object> ar = new ArrayList<Object> ();
        ar = (ArrayList<Object> )orgVO.getTheEntityIdDTCollection();
        for(int z=0;z<ar.size();z++){
          EntityIdDT eid = (EntityIdDT)ar.get(z);
          logger.debug("NonPersonLivingSubjectRootDAOImpl - entity add reason code = " + eid.getAddReasonCd());
        }

      }catch(Exception e){
        logger.error("\n\nNonPersonLivingSubjectRootDAOImpl ERROR : turkey no worky = \n" + e.getMessage(),e);
      }
    }
    //get collection of RoleDT from RoleDAOImpl entered by John Park
 private Collection<Object>  selectRoleDTCollection(long aUID)
      throws
        NEDSSSystemException
    {
        try  {
            if(roleDAOImpl == null) {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }
            logger.debug("aUID in selectRoleDTCollection  = " + aUID);
            return (roleDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectRoleDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object>  selectParticipationDTCollection(long aUID)
      throws
        NEDSSSystemException
    {
        try  {
            if(nonPersonLivingSubjectParticipationDAOImpl == null) {
                nonPersonLivingSubjectParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (nonPersonLivingSubjectParticipationDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        }
    }

    //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        Collection<Object>  newPartDTs = new ArrayList<Object> ();
        try
        {
            if(nonPersonLivingSubjectParticipationDAOImpl == null)
            {
                nonPersonLivingSubjectParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_PARTICIPATION_DAO_CLASS);
            }
           Iterator<Object>  iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                nonPersonLivingSubjectParticipationDAOImpl.store(partDT);
            }


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        }
    }

 //set collection of RoleDTs and return them with sequence values assigned- Wade Steele
 public Collection<Object>  setRoleDTCollection(Collection<Object> roleDTs)
      throws
        NEDSSSystemException
    {
        ArrayList<Object> returnRoles = new ArrayList<Object> ();

        try
        {
            if(roleDAOImpl == null)
            {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }

            returnRoles = (ArrayList<Object> )roleDAOImpl.setRoleDTCollection(roleDTs);
            logger.debug("Size of the collection returned is: " + returnRoles.size());
            return returnRoles;


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setRoleDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        }
    }

}//end of NonPersonLivingSubjectRootDAOImpl class
