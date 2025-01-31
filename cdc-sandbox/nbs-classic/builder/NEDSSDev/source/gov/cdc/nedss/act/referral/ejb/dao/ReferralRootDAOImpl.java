package gov.cdc.nedss.act.referral.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

public class ReferralRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(ReferralRootDAOImpl.class.getName());

    private ReferralVO pvo = null;
    private long referralUID;
    private  ReferralDAOImpl referralDAO = null;

    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    //objectassociation daos added by John Park
    private  ActRelationshipDAOImpl referralActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl referralParticipationDAOImpl = null;

    public ReferralRootDAOImpl()
    {
    }
    /**
     * This method calls the insert methods to insert the Referral value object into the database
	* @param Obj -- Referral Value Object
	* @return ReferralUid -- the newly created UID
	* @throws NEDSSSystemException
	*/
    public long create(Object obj) throws  NEDSSSystemException
    {
    	try{
	        this.pvo = (ReferralVO)obj;
	
	        /**
	        *  Inserts ReferralDT object
	        */
	
	        if (this.pvo != null)
	        referralUID = insertReferral(this.pvo);
	        logger.debug("Referral UID = " + referralUID);
	        (this.pvo.getTheReferralDT()).setReferralUid(new Long(referralUID));
	
	
	        /**
	        * Inserts ActivityIdDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActivityIdDTCollection() != null)
	        insertActivityIDs(this.pvo);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.pvo);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return ((((ReferralVO)obj).getTheReferralDT().getReferralUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
     * This method is used to edit or update the existing record in the database and insert the new records
	* @param Object -- The Referral Value Object
	* @throws NEDSSSystemException
	* @throws NEDSSConcurrentDataException
     */
    public void store(Object obj) throws NEDSSSystemException,NEDSSConcurrentDataException
    {
    	try{
	        this.pvo = (ReferralVO)obj;
	
	        /**
	        *  Updates ReferralDT object
	        */
	        if(this.pvo.getTheReferralDT() != null && this.pvo.getTheReferralDT().isItNew())
	        {
	            insertReferral(this.pvo);
	            this.pvo.getTheReferralDT().setItNew(false);
	            this.pvo.getTheReferralDT().setItDirty(false);
	        }
	       else if(this.pvo.getTheReferralDT() != null && this.pvo.getTheReferralDT().isItDirty())
	        {
	            updateReferral(this.pvo);
	            this.pvo.getTheReferralDT().setItDirty(false);
	            this.pvo.getTheReferralDT().setItNew(false);
	        }
	
	
	
	
	        /**
	         * Updates activity ids collection
	         */
	        if(this.pvo.getTheActivityIdDTCollection() != null)
	        {
	            updateActivityIDs(this.pvo);
	        }
	
	        /**
	        * Updates activity locator participations collection
	        */
	        if (this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.pvo);
	        }
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
   /**
     * Remove the referral and Objects related to it corrosponding to the UID
	* @param referralUID -- Uid of the object to be removed
	* @throws NEDSSSystemException
     */
    public void remove(long referralUID) throws  NEDSSSystemException
    {
    	try{
	        /**
	        * Removes ActivityIdDT Collection
	        */
	
	        removeActivityIDs(referralUID);
	
	
	        /**
	        *  Removes ReferralDT
	        */
	
	        removeReferral(referralUID);
    	}catch(Exception ex){
    		logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
     * Load the referral value object corrosponding to the referralUID
	* @param referralUID -- Uid for the object to be loaded
	* @return pvo  -- The referral value object
	* @throws NEDSSSystemException
     */
    public Object loadObject(long referralUID) throws  NEDSSSystemException
    {
    	try{
	        this.pvo = new ReferralVO();
	
	        /**
	        *  Selects ReferralDT object
	        */
	
	        ReferralDT pDT = selectReferral(referralUID);
	        this.pvo.setTheReferralDT(pDT);
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(referralUID);
	        this.pvo.setTheActivityIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> alpColl = selectActivityLocatorParticipations(referralUID);
	        this.pvo.setTheActivityLocatorParticipationDTCollection(alpColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(referralUID);
	        this.pvo.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(referralUID);
	        this.pvo.setTheParticipationDTCollection(parColl);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return this.pvo;
    	}catch(Exception ex){
    		logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
   /**
     * finds if the record exists in the database
	* @param referralUID
	* @return referralPK
	* @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long referralUID) throws  NEDSSSystemException
    {
    	try{
	        /**
	         * Finds referral object
	         */
	        Long referralPK = findReferral(referralUID);
	        logger.debug("Done find by primarykey!");
	        return referralPK;
    	}catch(Exception ex){
    		logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertReferral(ReferralVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(referralDAO == null)
            {
                referralDAO = (ReferralDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_DAO_CLASS);
            }
            logger.debug("Referral DT = " + pvo.getTheReferralDT());
            referralUID = referralDAO.create(pvo.getTheReferralDT());
            logger.debug("Referral root uid = " + referralUID);
            pvo.getTheReferralDT().setReferralUid(new Long(referralUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertReferral()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertReferral()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return referralUID;
    }


   /**
     * insert the new record for ActivityID
	* @param pvo -- the Referral value object
	* @throws EJBException
	* @throws NEDSSSystemException
     */
    private void insertActivityIDs(ReferralVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            referralUID = activityIdDAO.create((pvo.getTheReferralDT().getReferralUid()).longValue(), pvo.getTheActivityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * insert the new record for ActivityLocatorParticipations
	* @param ReferralVO -- the Referral value object
	* @throws EJBException
	* @throws NEDSSSystemException
     */
    private void insertActivityLocatorParticipations(ReferralVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            referralUID = activityLocatorParticipationDAO.create((pvo.getTheReferralDT().getReferralUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());
            //pvo.getTheActivityLocatorParticipationDTCollection().setReferralUid(new Long(referralUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);

            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Load the ReferralDT object corrosponding to the passed Uid
	* @param referralUID
	* @return ReferralDT
	* @throws NEDSSSystemException
	*/
    private ReferralDT selectReferral(long referralUID) throws NEDSSSystemException
    {
        try
        {
            if(referralDAO == null)
            {
                referralDAO = (ReferralDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_DAO_CLASS);
            }
            return ((ReferralDT)referralDAO.loadObject(referralUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectReferral()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectReferral()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * This method will gives you the collection of ActivityID DTs
	* related to the refferal corrosponding to the passed UID
	* @param aUID -- the referral UID
	* @return Collection<Object>  -- Collection<Object>  of the ActivityID DTs
	* @throws NEDSSSystemException
     */
    private Collection<Object> selectActivityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            return (activityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    /**
     * This method will gives you the collection of ActivityLocatorParticipation DTs
	* related to the referral corrosponding to the passed UID
	* @param aUID -- The Referral UID
	* @return Collection<Object>  -- Collection<Object>  of the ActivityLocatorParticipation DTs
	* @throws NEDSSSystemException
     */
    private Collection<Object> selectActivityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (activityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
    /**
     * This method will remove the Referral object from the database corrosponding
	* to the passed UID
	* @param aUID -- UID for refferal to be removed
	* @throws NEDSSSystemException
     */
    private void removeReferral(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(referralDAO == null)
            {
                referralDAO = (ReferralDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_DAO_CLASS);
            }
            referralDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

   /**
     * This method will remove the ActivityID objects from the database corrosponding
	* to the passed UID
	* @param aUID -- the referral UID
	* @throws NEDSSSystemException
     */
    private void removeActivityIDs(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

   /**
     * This method will Update the Referral Object in the database
	* @param ReferralVO
	* @throws NEDSSSystemException
     */
    private void updateReferral(ReferralVO pvo) throws  NEDSSConcurrentDataException
    {
        try
        {
            if(referralDAO == null)
            {
                referralDAO = (ReferralDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_DAO_CLASS);
            }
            referralDAO.store(pvo.getTheReferralDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateReferral()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateReferral() due to concurrent access! "+ncdaex.getMessage(), ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateReferral()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }

    }
   /**
     * This method will Update the ActivityIDs in the database
	* @param ReferralVO
	* @throws NEDSSSystemException
     */
    private void updateActivityIDs(ReferralVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(pvo.getTheActivityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

   /**
     * This method will Update the related ActivityLocatorParticipations in the database
	* @param ReferralVO
	* @throws NEDSSSystemException
     */
    private void updateActivityLocatorParticipations(ReferralVO pvo)
              throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            activityLocatorParticipationDAO.store(pvo.getTheActivityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
     /**
   * Find if the perticular record exists in the database
   * @param referralUID -- Uid to be find
   * @return referralUID -- Return the Found primary Key
   * @throws NEDSSSystemException
   */
    private Long findReferral(long referralUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(referralDAO == null)
            {
                referralDAO = (ReferralDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.REFERRAL_DAO_CLASS);
            }
            findPK = referralDAO.findByPrimaryKey(referralUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findReferral()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("referralUID: "+referralUID+" Fails findReferral()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
        return findPK;
    }

  /**
     * get collection of ActRelationshipDT from referralActRelationshipDAOImpl entered
	* @param UID -- Uid for which the roles are requird
	* @return Collection<Object>  -- the collection of the ActRelationshipDT
	* @throws NEDSSSystemException
     */
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws
      NEDSSSystemException
    {
        try  {
            if(referralActRelationshipDAOImpl == null) {
               referralActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (referralActRelationshipDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID:"+aUID+" Fails selectActRelationshipDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("aUID:"+aUID+" Fails selectActRelationshipDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


    /**
     * get collection of Participation  from ParticipationDAOImpl
	* @param UID -- Uid for which the Participations are requird
	* @return Collection<Object>  -- the collection of the ParticipationDT
	* @throws NEDSSSystemException
     */
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        try  {
            if(referralParticipationDAOImpl == null) {
                referralParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (referralParticipationDAOImpl.loadAct(aUID));
        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID:"+aUID+" Fails selectParticipationDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("aUID:"+aUID+" Fails selectParticipationDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

  /**
     * set a collection of participationDT and return the participationDTs with sequences
	* @param partDTs -- the participation Collection<Object>  to set
	* @throws NEDSSSystemException
     */
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        try
        {
            if(referralParticipationDAOImpl == null)
            {
                referralParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                referralParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

 /**
   *set collection of ActRelationshipDTs and return them with sequence values assigned
   * @param actRelDTs -- the ActRelationshipDT Collection<Object>  to set
   * @throws NEDSSSystemException
   */
 public void setActRelationship(Collection<Object> actRelDTs)
      throws
      NEDSSSystemException
    {
        try
        {
            if(referralActRelationshipDAOImpl == null)
            {
                referralActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                referralActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

}//end of ReferralRootDAOImpl class
