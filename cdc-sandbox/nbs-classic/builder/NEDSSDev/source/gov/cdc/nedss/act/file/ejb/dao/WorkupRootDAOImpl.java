package gov.cdc.nedss.act.file.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.file.dt.WorkupDT;
import gov.cdc.nedss.act.file.vo.WorkupVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

public class WorkupRootDAOImpl extends BMPBase
{
   //For logging
   static final LogUtils logger = new LogUtils(WorkupRootDAOImpl.class.getName());

    private WorkupVO pvo = null;
    private long workupUID;
    private  WorkupDAOImpl workupDAO = null;

    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    //activityRelationship and participation DAOs for place
    private  ActRelationshipDAOImpl workupActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl workupParticipationDAOImpl = null;

    public WorkupRootDAOImpl()
    {
    }

    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.pvo = (WorkupVO)obj;
	
	        /**
	        *  Inserts WorkupDT object
	        */
	
	        if (this.pvo != null)
	        workupUID = insertWorkup(this.pvo);
	        logger.debug("Workup UID = " + workupUID);
	        (this.pvo.getTheWorkupDT()).setWorkupUid(new Long(workupUID));
	
	
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
	        return ((((WorkupVO)obj).getTheWorkupDT().getWorkupUid()).longValue());
    	}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
    }

    public void store(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.pvo = (WorkupVO)obj;
	
	        /**
	        *  Updates WorkupDT object
	        */
	        if(this.pvo.getTheWorkupDT() != null && this.pvo.getTheWorkupDT().isItNew())
	        {
	            insertWorkup(this.pvo);
	            this.pvo.getTheWorkupDT().setItNew(false);
	            this.pvo.getTheWorkupDT().setItDirty(false);
	        }
	        else if(this.pvo.getTheWorkupDT() != null && this.pvo.getTheWorkupDT().isItDirty())
	        {
	            updateWorkup(this.pvo);
	            this.pvo.getTheWorkupDT().setItDirty(false);
	            this.pvo.getTheWorkupDT().setItNew(false);
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
    	}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
    }

    public void remove(long workupUID) throws NEDSSSystemException
    {
    	try{
	
	        /**
	        * Removes ActivityIdDT Collection
	        */
	
	        removeActivityIDs(workupUID);
	
	
	        /**
	        *  Removes WorkupDT
	        */
	
	        removeWorkup(workupUID);
    	}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
    }

    public Object loadObject(long workupUID) throws NEDSSSystemException
    {
    	try{
	        this.pvo = new WorkupVO();
	
	        /**
	        *  Selects WorkupDT object
	        */
	
	        WorkupDT pDT = selectWorkup(workupUID);
	        this.pvo.setTheWorkupDT(pDT);
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(workupUID);
	        this.pvo.setTheActivityIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> alpColl = selectActivityLocatorParticipations(workupUID);
	        this.pvo.setTheActivityLocatorParticipationDTCollection(alpColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(workupUID);
	        this.pvo.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(workupUID);
	        this.pvo.setTheParticipationDTCollection(parColl);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return this.pvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long workupUID) throws NEDSSSystemException
    {
    	try{
	        /**
	         * Finds workup object
	         */
	        Long workupPK = findWorkup(workupUID);
	        logger.debug("Done find by primarykey!");
	        return workupPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertWorkup(WorkupVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(workupDAO == null)
            {
                workupDAO = (WorkupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_DAO_CLASS);
            }
            logger.debug("Workup DT = " + pvo.getTheWorkupDT());
            workupUID = workupDAO.create(pvo.getTheWorkupDT());
            logger.debug("Workup root uid = " + workupUID);
            pvo.getTheWorkupDT().setWorkupUid(new Long(workupUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertWorkup()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertWorkup()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
        return workupUID;
    }



    private void insertActivityIDs(WorkupVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            workupUID = activityIdDAO.create((pvo.getTheWorkupDT().getWorkupUid()).longValue(), pvo.getTheActivityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


    private void insertActivityLocatorParticipations(WorkupVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            workupUID = activityLocatorParticipationDAO.create((pvo.getTheWorkupDT().getWorkupUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());
            //pvo.getTheActivityLocatorParticipationDTCollection().setWorkupUid(new Long(workupUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private WorkupDT selectWorkup(long workupUID) throws NEDSSSystemException
    {
        try
        {
            if(workupDAO == null)
            {
                workupDAO = (WorkupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_DAO_CLASS);
            }
            return ((WorkupDT)workupDAO.loadObject(workupUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectWorkup()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectWorkup()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

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
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


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

    private void removeWorkup(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(workupDAO == null)
            {
                workupDAO = (WorkupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_DAO_CLASS);
            }
            workupDAO.remove(aUID);
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


    private void removeActivityIDs(long aUID) throws NEDSSSystemException
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

    private void updateWorkup(WorkupVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(workupDAO == null)
            {
                workupDAO = (WorkupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_DAO_CLASS);
            }
            workupDAO.store(pvo.getTheWorkupDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateWorkup()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateWorkup()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void updateActivityIDs(WorkupVO pvo) throws NEDSSSystemException
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


    private void updateActivityLocatorParticipations(WorkupVO pvo)
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

    private Long findWorkup(long workupUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(workupDAO == null)
            {
                workupDAO = (WorkupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_DAO_CLASS);
            }
            findPK = workupDAO.findByPrimaryKey(workupUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findWorkup()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findWorkup()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
        return findPK;
    }

    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(workupActRelationshipDAOImpl == null) {
               workupActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (workupActRelationshipDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(workupParticipationDAOImpl == null) {
                workupParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (workupParticipationDAOImpl.loadAct(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

      //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        try
        {
            if(workupParticipationDAOImpl == null)
            {
                workupParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                workupParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

  //set a collection of ActRelationships - Wade Steele
 public void setActRelationship(Collection<Object> actRelDTs)
      throws NEDSSSystemException
    {
        Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(workupActRelationshipDAOImpl == null)
            {
                workupActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                workupActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


}//end of WorkupRootDAOImpl class
