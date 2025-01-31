/**
* Title:	TreatmentRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               Treatment value object in the Treatment entity bean.
*               This class encapsulates all the JDBC calls made by the TreatmentEJB
*               for a Treatment object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of TreatmentEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Aaron Aycock & NEDSS Development Team
* @version	1.1
*/

package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
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


public class TreatmentRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(TreatmentRootDAOImpl.class.getName());

    private TreatmentVO tvo = null;
    private long treatmentUID;
    private  TreatmentDAOImpl treatmentDAO = null;
    private  TreatmentProcedureDAOImpl treatmentProcedureDAO = null;
    private  TreatmentAdministeredDAOImpl treatmentAdministeredDAO = null;
    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl treatmentActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl treatmentParticipationDAOImpl = null;

    public TreatmentRootDAOImpl()
        {
    }

    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.tvo = (TreatmentVO)obj;
	
	        /**
	        *  Inserts TreatmentDT object
	        */
	
	        if (this.tvo != null)
	        treatmentUID = insertTreatment(this.tvo);
	        logger.debug("Treatment UID = " + treatmentUID);
	        (this.tvo.getTheTreatmentDT()).setTreatmentUid(new Long(treatmentUID));
	
	
	        /**
	        * Inserts TreatmentProcedureDT collection
	        */
	
	        if (this.tvo != null && this.tvo.getTheTreatmentProcedureDTCollection() != null)
	        insertProcedures(this.tvo);
	
	        /**
	        * Inserts TreatmentAdministeredDT collection
	        */
	
	        if (this.tvo != null && this.tvo.getTheTreatmentAdministeredDTCollection() != null)
	        insertTreatmentAdministered(this.tvo);
	                logger.debug("insertSubstanceAdministrations : " + treatmentUID);
	
	        /**
	        * Inserts ActivityIdDT collection
	        */
	
	        if (this.tvo != null && this.tvo.getTheActIdDTCollection() != null)
	        insertActivityIDs(this.tvo);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.tvo != null && this.tvo.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.tvo);
	
	        this.tvo.setItNew(false);
	        this.tvo.setItDirty(false);
	
	        return ((((TreatmentVO)obj).getTheTreatmentDT().getTreatmentUid()).longValue());
    	}catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }

    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        this.tvo = (TreatmentVO)obj;
	
	        /**
	        *  Updates TreatmentDT object
	        */
	        if(this.tvo.getTheTreatmentDT() != null && this.tvo.getTheTreatmentDT().isItNew())
	        {
	            insertTreatment(this.tvo);
	            this.tvo.getTheTreatmentDT().setItNew(false);
	            this.tvo.getTheTreatmentDT().setItDirty(false);
	        }
	        else if(this.tvo.getTheTreatmentDT() != null && this.tvo.getTheTreatmentDT().isItDirty())
	        {
	            updateTreatment(this.tvo);
	            this.tvo.getTheTreatmentDT().setItDirty(false);
	            this.tvo.getTheTreatmentDT().setItNew(false);
	        }
	
	        /**
	        * Updates TreatmentProcedure collection
	        */
	
	        if (this.tvo.getTheTreatmentProcedureDTCollection() != null)
	        {
	            updateProcedures(this.tvo);
	
	        }
	
	
	        /**
	        * Updates TreatmentAdministered
	        */
	
	        if (this.tvo.getTheTreatmentAdministeredDTCollection() != null)
	        {
	                        logger.debug("inside testcondition1");
	            updateTreatmentAdministered(this.tvo);
	
	        }
	
	        /**
	         * Updates activity ids collection
	         */
	        if(this.tvo.getTheActIdDTCollection() != null)
	        {
	            updateActivityIDs(this.tvo);
	        }
	
	        /**
	         * Updates ActivityLocatorParticipationDT collection
	         */
	        if(this.tvo.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	          logger.debug("\n\n\n ParticipationDT is NOT null! \n\n\n");
	          updateActivityLocatorParticipations(this.tvo);
	        }
	        else{
	          logger.debug("\n\n\nparticipationDTcollection is null!!!\n\n\n");
	        }
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long treatmentUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes ActivityIdDT Collection
	        */
	
	        removeActivityIDs(treatmentUID);
	
	        /**
	        * Removes TreatmentAdministeredDT Collection
	        */
	
	        removeTreatmentAdministered(treatmentUID);
	
	        /**
	        * Removes TreatmentProcedureDT Collection
	        */
	
	        removeProcedures(treatmentUID);
	
	        /**
	        *  Removes TreatmentDT
	        */
	
	        removeTreatment(treatmentUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long treatmentUID) throws NEDSSSystemException
    {
    	try{
	        this.tvo = new TreatmentVO();
	
	        /**
	        *  Selects TreatmentDT object
	        */
	
	        TreatmentDT pDT = selectTreatment(treatmentUID);
	        this.tvo.setTheTreatmentDT(pDT);
	
	        /**
	        * Selects TreatmentProcedureDT Collection
	        */
	
	        Collection<Object> pnColl = selectProcedures(treatmentUID);
	        this.tvo.setTheTreatmentProcedureDTCollection(pnColl);
	
	        /**
	        * Selects TreatmentAdministeredDT Collection
	        */
	
	        Collection<Object> saColl = selectTreatmentAdministered(treatmentUID);
	        this.tvo.setTheTreatmentAdministeredDTCollection(saColl);
	        logger.debug("inside Root Treatment set SA");
	
	
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(treatmentUID);
	        this.tvo.setTheActIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> activityLocatorParticipationColl = selectActivityLocatorParticipations(treatmentUID);
	        this.tvo.setTheActivityLocatorParticipationDTCollection(activityLocatorParticipationColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(treatmentUID);
	        this.tvo.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(treatmentUID);
	        this.tvo.setTheParticipationDTCollection(parColl);
	
	        this.tvo.setItNew(false);
	        this.tvo.setItDirty(false);
	        return this.tvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long treatmentUID) throws NEDSSSystemException
    {
    	try{
	        /**
	         * Finds treatment object
	         */
	        Long treatmentPK = findTreatment(treatmentUID);
	        logger.debug("Done find by primarykey!");
            return treatmentPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertTreatment(TreatmentVO tvo) throws  NEDSSSystemException
    {
        try
        {
            if(treatmentDAO == null)
            {
                treatmentDAO = (TreatmentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_DAO_CLASS);
            }
                    logger.debug("Treatment DT = " + tvo.getTheTreatmentDT());
            treatmentUID = treatmentDAO.create(tvo.getTheTreatmentDT());
                    logger.debug("Treatment root uid = " + treatmentUID);
            tvo.getTheTreatmentDT().setTreatmentUid(new Long(treatmentUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertTreatment()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertTreatment()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return treatmentUID;
    }

    private void insertProcedures(TreatmentVO tvo) throws  NEDSSSystemException
    {
        try
        {
            if(treatmentProcedureDAO == null)
            {
                treatmentProcedureDAO = (TreatmentProcedureDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_PROCEDURE_DAO_CLASS);
            }
            treatmentUID = treatmentProcedureDAO.create((tvo.getTheTreatmentDT().getTreatmentUid()).longValue(), tvo.getTheTreatmentProcedureDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertProcedures()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertProcedures()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void insertTreatmentAdministered(TreatmentVO tvo)
            throws   NEDSSSystemException
    {
        try
        {
            if(treatmentAdministeredDAO == null)
            {
                treatmentAdministeredDAO = (TreatmentAdministeredDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ADMINISTERED_DAO_CLASS);
            }
                        treatmentUID = treatmentAdministeredDAO.create((tvo.getTheTreatmentDT().getTreatmentUid()).longValue(), tvo.getTheTreatmentAdministeredDTCollection());
                        logger.debug("inside the SA insert method 2");

        }
        catch(NEDSSSystemException npdapex)
        {
            logger.fatal("Fails insertSubstanceAdministrations()"+npdapex.getMessage(), npdapex);
            throw new NEDSSSystemException(npdapex.toString());
        }
    }

    private void insertActivityIDs(TreatmentVO tvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            treatmentUID = activityIdDAO.create((tvo.getTheTreatmentDT().getTreatmentUid()).longValue(), tvo.getTheActIdDTCollection());
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

    private void insertActivityLocatorParticipations(TreatmentVO tvo) throws  NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }

            logger.debug("\n\n\nAbout to create the participation entry...\n\n\n");
            treatmentUID = activityLocatorParticipationDAO.create((tvo.getTheTreatmentDT().getTreatmentUid()).longValue(), tvo.getTheActivityLocatorParticipationDTCollection());
            //tvo.getTheActivityLocatorParticipationDTCollection().setTreatmentUid(new Long(treatmentUID));
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

    private TreatmentDT selectTreatment(long treatmentUID) throws NEDSSSystemException
    {
        try
        {
            if(treatmentDAO == null)
            {
                treatmentDAO = (TreatmentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_DAO_CLASS);
            }
            return ((TreatmentDT)treatmentDAO.loadObject(treatmentUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectTreatment()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectTreatment()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Collection<Object> selectProcedures(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(treatmentProcedureDAO == null)
            {
                treatmentProcedureDAO = (TreatmentProcedureDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_PROCEDURE_DAO_CLASS);
            }
            return (treatmentProcedureDAO.load(aUID));
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

    private Collection<Object> selectTreatmentAdministered(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(treatmentAdministeredDAO == null)
            {
                treatmentAdministeredDAO = (TreatmentAdministeredDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ADMINISTERED_DAO_CLASS);
            }
            return (treatmentAdministeredDAO.load(aUID));
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

    private void removeTreatment(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(treatmentDAO == null)
            {
                treatmentDAO = (TreatmentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_DAO_CLASS);
            }
            treatmentDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeProcedures(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(treatmentProcedureDAO == null)
            {
                treatmentProcedureDAO = (TreatmentProcedureDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_PROCEDURE_DAO_CLASS);
            }
            treatmentProcedureDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeTreatmentAdministered(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(treatmentAdministeredDAO == null)
            {
                treatmentAdministeredDAO = (TreatmentAdministeredDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ADMINISTERED_DAO_CLASS);
            }
            treatmentAdministeredDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception  = "+ndapex.getMessage(), ndapex);
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
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void updateTreatment(TreatmentVO tvo) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(treatmentDAO == null)
            {
                treatmentDAO = (TreatmentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_DAO_CLASS);
            }
            treatmentDAO.storeTreatment(tvo.getTheTreatmentDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateTreatment()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateTreatment() due to concurrent access! "+ncdaex.getMessage(), ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateTreatment()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }


    }

    private void updateProcedures(TreatmentVO tvo) throws NEDSSSystemException
    {
        try
        {
            if(treatmentProcedureDAO == null)
            {
                treatmentProcedureDAO = (TreatmentProcedureDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_PROCEDURE_DAO_CLASS);
            }
            treatmentProcedureDAO.store(tvo.getTheTreatmentProcedureDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateProcedure1s()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateProcedure1s()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateTreatmentAdministered(TreatmentVO tvo) throws NEDSSSystemException
    {
        try
        {
            if(treatmentAdministeredDAO == null)
            {
                treatmentAdministeredDAO = (TreatmentAdministeredDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ADMINISTERED_DAO_CLASS);
            }
            treatmentAdministeredDAO.store(tvo.getTheTreatmentAdministeredDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateSubstanceAdministrations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateSubstanceAdministrations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateActivityIDs(TreatmentVO tvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(tvo.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void updateActivityLocatorParticipations(TreatmentVO tvo)
              throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            activityLocatorParticipationDAO.store(tvo.getTheActivityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Long findTreatment(long treatmentUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(treatmentDAO == null)
            {
                treatmentDAO = (TreatmentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_DAO_CLASS);
            }
            findPK = treatmentDAO.findByPrimaryKey(treatmentUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findTreatment()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findTreatment()"+ndapex.getMessage(), ndapex);
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
      logger.debug("TreatmentRootDAOImpl - Doing the main thing");
      try{
//        OrganizationDAOImpl orgDAOI = new OrganizationDAOImpl();
//        OrganizationNameDAOImpl orgNameDAOI = new OrganizationNameDAOImpl();

        Long uid = new Long(199);

        Procedure1DT PrDT = new Procedure1DT();
        PrDT.setTreatmentUid(uid);
   //   orgNameDT.setNmTxt("Lucy");
        PrDT.setApproachSiteCd("testVO");
        PrDT.setApproachSiteDescTxt("TestVO");
        ArrayList<Object> cPrDT = new ArrayList<Object> ();
        cPrDT.add(PrDT);

        TreatmentDT intDT = new TreatmentDT();
        intDT.setActivityDurationAmt("1234567890");
        intDT.setAddReasonCd("test reason");
        intDT.setItDirty(true);
        intDT.setItNew(false);


        TreatmentVO IntVO = new TreatmentVO();
        IntVO.setTheTreatmentDT(intDT);
                IntVO.setTheActIdDTCollection(cPrDT);

        TreatmentRootDAOImpl intRootDAO = new TreatmentRootDAOImpl();
//        orgRootDAO.create(orgVO);
//        orgRootDAO.updateOrganizationNames(orgVO);

//        Long x = orgRootDAO.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);
        IntVO = (TreatmentVO)intRootDAO.loadObject(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - done load");
//        logger.debug("OrganizationRootDAOImpl - phone number = " + odt.getPhoneNbr());
//        x = orgDAOI.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);

      }catch(Exception e){
        logger.debug("\n\nTreatmentRootDAOImpl ERROR : turkey no worky = \n" + e);
      }
    }

*/
    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(treatmentActRelationshipDAOImpl == null) {
               treatmentActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            /** @todo Decide which AR load is to be used */
            //return (treatmentActRelationshipDAOImpl.load(aUID));
            return (treatmentActRelationshipDAOImpl.loadSource(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(treatmentParticipationDAOImpl == null) {
                treatmentParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (treatmentParticipationDAOImpl.loadAct(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

     //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
       // Collection<Object> newPartDTs = new ArrayList<Object> ();
        try
        {
            if(treatmentParticipationDAOImpl == null)
            {
                treatmentParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                treatmentParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set a collection of ActRelationships - Wade Steele
 public void setActRelationship(Collection<Object> actRelDTs)
      throws NEDSSSystemException
    {
       // Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(treatmentActRelationshipDAOImpl == null)
            {
                treatmentActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                treatmentActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

}//end of TreatmentRootDAOImpl class
