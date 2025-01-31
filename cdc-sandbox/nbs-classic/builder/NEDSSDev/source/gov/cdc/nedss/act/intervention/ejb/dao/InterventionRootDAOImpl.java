/**
* Name:		    InterventionRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               Intervention value object in the Intervention entity bean.
*               This class encapsulates all the JDBC calls made by the InterventionEJB
*               for a Intervention object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of InterventionEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma & NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.act.intervention.ejb.dao;


import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

public class InterventionRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(InterventionRootDAOImpl.class.getName());

    private InterventionVO pvo = null;
    private long interventionUID;
    private  InterventionDAOImpl interventionDAO = null;
    private  Procedure1DAOImpl procedure1DAO = null;
    private  SubstanceAdministrationDAOImpl substanceAdministrationDAO = null;
    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl interventionActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl interventionParticipationDAOImpl = null;

    public InterventionRootDAOImpl()
	{
    }

    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.pvo = (InterventionVO)obj;
	
	        /**
	        *  Inserts InterventionDT object
	        */
	
	        if (this.pvo != null)
	        interventionUID = insertIntervention(this.pvo);
	        logger.debug("Intervention UID = " + interventionUID);
	        (this.pvo.getTheInterventionDT()).setInterventionUid(new Long(interventionUID));
	
	
	        /**
	        * Inserts Procedure1DT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheProcedure1DTCollection() != null)
	        insertProcedure1s(this.pvo);
	
	        /**
	        * Inserts SubstanceAdministrationDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheSubstanceAdministrationDTCollection() != null)
	        insertSubstanceAdministrations(this.pvo);
			logger.debug("insertSubstanceAdministrations : " + interventionUID);
	
	        /**
	        * Inserts ActivityIdDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActIdDTCollection() != null)
	        insertActivityIDs(this.pvo);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.pvo);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return ((((InterventionVO)obj).getTheInterventionDT().getInterventionUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Object obj) throws NEDSSConcurrentDataException, NEDSSSystemException
    {
    	try{
	        this.pvo = (InterventionVO)obj;
	
	        /**
	        *  Updates InterventionDT object
	        */
	        if(this.pvo.getTheInterventionDT() != null && this.pvo.getTheInterventionDT().isItNew())
	        {
	            insertIntervention(this.pvo);
	            this.pvo.getTheInterventionDT().setItNew(false);
	            this.pvo.getTheInterventionDT().setItDirty(false);
	        }
	        else if(this.pvo.getTheInterventionDT() != null && this.pvo.getTheInterventionDT().isItDirty())
	        {
	            updateIntervention(this.pvo);
	            this.pvo.getTheInterventionDT().setItDirty(false);
	            this.pvo.getTheInterventionDT().setItNew(false);
	        }
	
	        /**
	        * Updates Procedure1 collection
	        */
	
	        if (this.pvo.getTheProcedure1DTCollection() != null)
	        {
	            updateProcedure1s(this.pvo);
	
	        }
	
	
	        /**
	        * Updates SubstanceAdministration
	        */
	
	        if (this.pvo.getTheSubstanceAdministrationDTCollection() != null)
	        {
				logger.debug("inside testcondition1");
	            updateSubstanceAdministrations(this.pvo);
	
	        }
	
	        /**
	         * Updates activity ids collection
	         */
	        if(this.pvo.getTheActIdDTCollection() != null)
	        {
	            updateActivityIDs(this.pvo);
	        }
	
	        /**
	         * Updates ActivityLocatorParticipationDT collection
	         */
	        if(this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.pvo);
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long interventionUID) throws NEDSSSystemException
    {
    	try{

	        /**
	        * Removes ActivityIdDT Collection
	        */
	
	        removeActivityIDs(interventionUID);
	
	        /**
	        * Removes SubstanceAdministrationDT Collection
	        */
	
	        removeSubstanceAdministrations(interventionUID);
	
	        /**
	        * Removes Procedure1DT Collection
	        */
	
	        removeProcedure1s(interventionUID);
	
	        /**
	        *  Removes InterventionDT
	        */
	
	        removeIntervention(interventionUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        this.pvo = new InterventionVO();
	
	        /**
	        *  Selects InterventionDT object
	        */
	
	        InterventionDT pDT = selectIntervention(interventionUID);
	        this.pvo.setTheInterventionDT(pDT);
	
	        /**
	        * Selects Procedure1DT Collection
	        */
	
	        Collection<Object> pnColl = selectProcedure1s(interventionUID);
	        this.pvo.setTheProcedure1DTCollection(pnColl);
	
	        /**
	        * Selects SubstanceAdministrationDT Collection
	        */
	
	        Collection<Object> saColl = selectSubstanceAdministrations(interventionUID);
	        this.pvo.setTheSubstanceAdministrationDTCollection(saColl);
	        logger.debug("inside Root Intervention set SA");
	
	
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(interventionUID);
	        this.pvo.setTheActIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> activityLocatorParticipationColl = selectActivityLocatorParticipations(interventionUID);
	        this.pvo.setTheActivityLocatorParticipationDTCollection(activityLocatorParticipationColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(interventionUID);
	        this.pvo.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(interventionUID);
	        this.pvo.setTheParticipationDTCollection(parColl);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return this.pvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        /**
	         * Finds intervention object
	         */
	        Long interventionPK = findIntervention(interventionUID);
	        logger.debug("Done find by primarykey!");
	        return interventionPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertIntervention(InterventionVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(interventionDAO == null)
            {
                interventionDAO = (InterventionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
		    logger.debug("Intervention DT = " + pvo.getTheInterventionDT());
            interventionUID = interventionDAO.create(pvo.getTheInterventionDT());
		    logger.debug("Intervention root uid = " + interventionUID);
            pvo.getTheInterventionDT().setInterventionUid(new Long(interventionUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertIntervention()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertIntervention()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return interventionUID;
    }

    private void insertProcedure1s(InterventionVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(procedure1DAO == null)
            {
                procedure1DAO = (Procedure1DAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_PROCEDURE1_DAO_CLASS);
            }
            interventionUID = procedure1DAO.create((pvo.getTheInterventionDT().getInterventionUid()).longValue(), pvo.getTheProcedure1DTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertProcedure1s()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertProcedure1s()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void insertSubstanceAdministrations(InterventionVO pvo)
            throws   NEDSSSystemException
    {
        try
        {
            if(substanceAdministrationDAO == null)
            {
                substanceAdministrationDAO = (SubstanceAdministrationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_SUBSTANCE_ADMINISTRATION_DAO_CLASS);
            }
			interventionUID = substanceAdministrationDAO.create((pvo.getTheInterventionDT().getInterventionUid()).longValue(), pvo.getTheSubstanceAdministrationDTCollection());
			logger.debug("inside the SA insert method 2");

        }
        catch(NEDSSSystemException npdapex)
        {
            logger.fatal("Fails insertSubstanceAdministrations()"+npdapex.getMessage(), npdapex);
            throw new NEDSSSystemException(npdapex.toString());
        }
    }

    private void insertActivityIDs(InterventionVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            interventionUID = activityIdDAO.create((pvo.getTheInterventionDT().getInterventionUid()).longValue(), pvo.getTheActIdDTCollection());
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

    private void insertActivityLocatorParticipations(InterventionVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            interventionUID = activityLocatorParticipationDAO.create((pvo.getTheInterventionDT().getInterventionUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());
            //pvo.getTheActivityLocatorParticipationDTCollection().setInterventionUid(new Long(interventionUID));
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

    private InterventionDT selectIntervention(long interventionUID) throws NEDSSSystemException
    {
        try
        {
            if(interventionDAO == null)
            {
                interventionDAO = (InterventionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
            return ((InterventionDT)interventionDAO.loadObject(interventionUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectIntervention()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectIntervention()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Collection<Object> selectProcedure1s(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(procedure1DAO == null)
            {
                procedure1DAO = (Procedure1DAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_PROCEDURE1_DAO_CLASS);
            }
            return (procedure1DAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectProcedure1s()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectProcedure1s()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Collection<Object> selectSubstanceAdministrations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(substanceAdministrationDAO == null)
            {
                substanceAdministrationDAO = (SubstanceAdministrationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_SUBSTANCE_ADMINISTRATION_DAO_CLASS);
            }
            return (substanceAdministrationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectSubstanceAdministrations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectSubstanceAdministrations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
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
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
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
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void removeIntervention(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(interventionDAO == null)
            {
                interventionDAO = (InterventionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
            interventionDAO.remove(aUID);
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

    private void removeProcedure1s(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(procedure1DAO == null)
            {
                procedure1DAO = (Procedure1DAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_PROCEDURE1_DAO_CLASS);
            }
            procedure1DAO.remove(aUID);
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

    private void removeSubstanceAdministrations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(substanceAdministrationDAO == null)
            {
                substanceAdministrationDAO = (SubstanceAdministrationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_SUBSTANCE_ADMINISTRATION_DAO_CLASS);
            }
            substanceAdministrationDAO.remove(aUID);
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

    private void removeActivityIDs(long aUID) throws  NEDSSSystemException
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
            logger.fatal("NEDSSSystemException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void updateIntervention(InterventionVO pvo) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(interventionDAO == null)
            {
                interventionDAO = (InterventionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
            interventionDAO.storeIntervention(pvo.getTheInterventionDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateIntervention()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateIntervention() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateIntervention()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }


    }

    private void updateProcedure1s(InterventionVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(procedure1DAO == null)
            {
                procedure1DAO = (Procedure1DAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_PROCEDURE1_DAO_CLASS);
            }
            procedure1DAO.store(pvo.getTheProcedure1DTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateProcedure1s()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateProcedure1s()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateSubstanceAdministrations(InterventionVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(substanceAdministrationDAO == null)
            {
                substanceAdministrationDAO = (SubstanceAdministrationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_SUBSTANCE_ADMINISTRATION_DAO_CLASS);
            }
            substanceAdministrationDAO.store(pvo.getTheSubstanceAdministrationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateSubstanceAdministrations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateSubstanceAdministrations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateActivityIDs(InterventionVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(pvo.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateActivityLocatorParticipations(InterventionVO pvo)
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
            logger.fatal("Fails updateActivityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Long findIntervention(long interventionUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(interventionDAO == null)
            {
                interventionDAO = (InterventionDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
            findPK = interventionDAO.findByPrimaryKey(interventionUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findIntervention() interventionUID: "+interventionUID, ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findIntervention() interventionUID: "+interventionUID, ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }
/**
   protected synchronized Connection getConnection(){
      Connection conn = null;
      try{
        logger.debug("test21");
        Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
        logger.debug("test1");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa", "sapasswd");
      logger.debug("test2");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
    }

    public static void main(String args[]){
      logger.debug("InterventionRootDAOImpl - Doing the main thing");
      try{
//        OrganizationDAOImpl orgDAOI = new OrganizationDAOImpl();
//        OrganizationNameDAOImpl orgNameDAOI = new OrganizationNameDAOImpl();

        Long uid = new Long(199);

        Procedure1DT PrDT = new Procedure1DT();
        PrDT.setInterventionUid(uid);
   //   orgNameDT.setNmTxt("Lucy");
        PrDT.setApproachSiteCd("testVO");
        PrDT.setApproachSiteDescTxt("TestVO");
        ArrayList<Object> cPrDT = new ArrayList<Object> ();
        cPrDT.add(PrDT);

        InterventionDT intDT = new InterventionDT();
        intDT.setActivityDurationAmt("1234567890");
        intDT.setAddReasonCd("test reason");
        intDT.setItDirty(true);
        intDT.setItNew(false);


        InterventionVO IntVO = new InterventionVO();
        IntVO.setTheInterventionDT(intDT);
		IntVO.setTheActIdDTCollection(cPrDT);

        InterventionRootDAOImpl intRootDAO = new InterventionRootDAOImpl();
//        orgRootDAO.create(orgVO);
//        orgRootDAO.updateOrganizationNames(orgVO);

//        Long x = orgRootDAO.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);
        IntVO = (InterventionVO)intRootDAO.loadObject(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - done load");
//        logger.debug("OrganizationRootDAOImpl - phone number = " + odt.getPhoneNbr());
//        x = orgDAOI.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);

      }catch(Exception e){
        logger.debug("\n\nInterventionRootDAOImpl ERROR : turkey no worky = \n" + e);
      }
    }

*/
    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(interventionActRelationshipDAOImpl == null) {
               interventionActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (interventionActRelationshipDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection() aUID: "+aUID, ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection() aUID: "+aUID, ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(interventionParticipationDAOImpl == null) {
                interventionParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (interventionParticipationDAOImpl.loadAct(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection() aUID: "+aUID, ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection() aUID: "+aUID, ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

     //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        Collection<Object> newPartDTs = new ArrayList<Object> ();
        try
        {
            if(interventionParticipationDAOImpl == null)
            {
                interventionParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                interventionParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set a collection of ActRelationships - Wade Steele
 public void setActRelationship(Collection<Object> actRelDTs)
      throws NEDSSSystemException
    {
        Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(interventionActRelationshipDAOImpl == null)
            {
                interventionActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                interventionActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

}//end of InterventionRootDAOImpl class
