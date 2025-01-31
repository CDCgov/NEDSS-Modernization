/**
* Name:		PublicHealthCaseRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PublicHealthCaseVO value object in the PublicHealthCase entity bean.
*               This class encapsulates all the JDBC calls made by the PublicHealthCaseEJB
*               for a PublicHealthCase object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PublicHealthCaseEJB is
*               implemented here.
*Added support for isReentrant variable for merge case scenario           
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
* @updatedByAuthor Pradeep Sharma
* @company: SAIC
* @version 4.5
* @updatedByAuthor Pradeep Sharma
* @company: CSRA 
* @version 5.2
*/

package gov.cdc.nedss.act.publichealthcase.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJBException;

public class PublicHealthCaseRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(PublicHealthCaseRootDAOImpl.class.getName());

    private PublicHealthCaseVO phcVO = null;
    private long phcUID;
    private  CaseManagementDAOImpl cmDAO = null;
    private  PublicHealthCaseDAOImpl phcDAO = null;
    private  ConfirmationMethodDAOImpl confirmationMethodDAO = null;
    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl publicHealthCaseActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl publicHealthCaseParticipationDAOImpl = null;

    public PublicHealthCaseRootDAOImpl()
    {
    }

    public long create(Object obj) throws NEDSSSystemException
    {
    	long phcUid;
    	
    	try{
	        this.phcVO = (PublicHealthCaseVO)obj;
	
	        /**
	        *  Inserts PublicHealthCaseDT object
	        */
	
	        if (this.phcVO != null)
	        phcUID = insertPublicHealthCase(this.phcVO);
	        logger.debug("PublicHealthCase UID = " + phcUID);
	        (this.phcVO.getThePublicHealthCaseDT()).setPublicHealthCaseUid(new Long(phcUID));
	
	
	        /**
	        * Inserts ConfirmationMethodDT collection
	        */
	
	        if (this.phcVO != null && this.phcVO.getTheConfirmationMethodDTCollection() != null)
	        insertConfirmationMethods(this.phcVO);
	        /**
	         * Inserts CaseManagementDT 
	         */
	
			if (this.phcVO != null && this.phcVO.getTheCaseManagementDT() != null
					&& phcVO.getTheCaseManagementDT().isCaseManagementDTPopulated)
				insertCaseManagementDT(this.phcVO);
	        /**
	        * Inserts ActIdDT collection
	        */
	
	        if (this.phcVO != null && this.phcVO.getTheActIdDTCollection() != null)
	        insertActivityIDs(this.phcVO);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.phcVO != null && this.phcVO.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.phcVO);
	
	        this.phcVO.setItNew(false);
	        this.phcVO.setItDirty(false);
	        phcUid = ((((PublicHealthCaseVO)obj).getThePublicHealthCaseDT().getPublicHealthCaseUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
        return phcUid;
    }

    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        this.phcVO = (PublicHealthCaseVO)obj;
	
	        /**
	        *  Updates PublicHealthCaseDT object
	        */
	        if(this.phcVO.getThePublicHealthCaseDT() != null && this.phcVO.getThePublicHealthCaseDT().isItNew())
	        {
	            insertPublicHealthCase(this.phcVO);
	            this.phcVO.getThePublicHealthCaseDT().setItNew(false);
	            this.phcVO.getThePublicHealthCaseDT().setItDirty(false);
	        }
	        else if(this.phcVO.getThePublicHealthCaseDT() != null && this.phcVO.getThePublicHealthCaseDT().isItDirty())
	        {
	            updatePublicHealthCase(this.phcVO);
	            this.phcVO.getThePublicHealthCaseDT().setItDirty(false);
	            this.phcVO.getThePublicHealthCaseDT().setItNew(false);
	        }
	
	        /**
	        * Updates ConfirmationMethodDT collection
	        */
	
	        if (this.phcVO.getTheConfirmationMethodDTCollection() != null)
	        {
	            Collection<Object> coll = phcVO.getTheConfirmationMethodDTCollection();
	            Iterator<Object> iter = coll.iterator();
	            Long phcUID = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
	            ArrayList<Object>  arry = new ArrayList<Object> (coll.size());
	            while(iter.hasNext())
	            {
	              ConfirmationMethodDT cdt = (ConfirmationMethodDT)iter.next();
	              cdt.setPublicHealthCaseUid(phcUID);
	              arry.add(cdt);
	            }
	            phcVO.setTheConfirmationMethodDTCollection((Collection<Object>)arry);
	            updateConfirmationMethods(this.phcVO);
	
	        //Else part is added as part of R2.0.2 to delete all ConfirmationMethods (if any)     
	        } else {
	        	removeConfirmationMethods(this.phcVO);
	        }
	
	        /**
	        * Updates CaseManagementDT collection
	        */
	
	        if (this.phcVO.getTheCaseManagementDT() != null)
	        {
	        	Long phcUID = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
	            
	            updateCaseManagementDT(this.phcVO);
	
	        } else {
	        	removeCaseManagementDT(this.phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
	        }
	
	
	        /**
	         * Updates actity ids collection
	         */
	        if(this.phcVO.getTheActIdDTCollection() != null)
	        {
	            updateActityIDs(this.phcVO);
	        }
	
	        /**
	        * Updates ActivityLocatorParticipationDT collection
	        */
	        if (this.phcVO.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.phcVO);
	        }
	
	        /**
	        * Updates ParticipationDT collection
	        */
	        if (this.phcVO.getTheParticipationDTCollection() != null)
	        {
	            this.setParticipation(this.phcVO.getTheParticipationDTCollection());
	        }
        
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long phcUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes ActityIdDT Collection
	        */
	
	        removeActityIDs(phcUID);
	
	        /**
	        * Removes ConfirmationMethodDT Collection
	        */
	
	        removeConfirmationMethods(phcUID);
	
	        /**
	        *  Removes PublicHealthCaseDT
	        */
	
	        removePublicHealthCase(phcUID);
	        /**
	         *  Removes CaseManagementDT
	         */
	        removeCaseManagementDT(phcUID);
    	}catch(Exception ex){
    		logger.fatal("phcUID: "+phcUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long phcUID) throws
        NEDSSSystemException
    {
        this.phcVO = new PublicHealthCaseVO();

        try{
	        /**
	        *  Selects PublicHealthCaseDT object
	        */
	
	        PublicHealthCaseDT obDT = selectPublicHealthCase(phcUID);
	        this.phcVO.setThePublicHealthCaseDT(obDT);
	
	        /**
	        * Selects ConfirmationMethodDT Collection
	        */
	
	        Collection<Object> confMethodColl = selectConfirmationMethods(phcUID);
	        this.phcVO.setTheConfirmationMethodDTCollection(confMethodColl);
	
	        /**
	        * Selects CaseManagementDT
	        */
	
	        CaseManagementDT caseManagementDT= selectCaseManagementDT(phcUID);
	        this.phcVO.setTheCaseManagementDT(caseManagementDT);
	        /**
	        * Selects ActityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActityIDs(phcUID);
	        this.phcVO.setTheActIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> activityLocatorParticipationColl = selectActivityLocatorParticipations(phcUID);
	        this.phcVO.setTheActivityLocatorParticipationDTCollection(activityLocatorParticipationColl);
	
	
	        //Selects ActRelationshipDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(phcUID);
	        this.phcVO.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(phcUID);
	        this.phcVO.setTheParticipationDTCollection(parColl);
	
	
	        this.phcVO.setItNew(false);
	        this.phcVO.setItDirty(false);
        }catch(Exception ex){
        	logger.fatal("phcUID: "+phcUID+" Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
        return this.phcVO;
    }

    public Long findByPrimaryKey(long phcUID) throws
		NEDSSSystemException
    {
    	try{
	        /**
	         * Finds PublicHealthCase object
	         */
	        Long phcPK = findPublicHealthCase(phcUID);
	
	        logger.debug("Done find by primarykey!");
	        return phcPK;
    	}catch(Exception ex){
    		logger.fatal("phcUID: "+phcUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertPublicHealthCase(PublicHealthCaseVO phcVO) throws  NEDSSSystemException
    {
        try
        {
            if(phcDAO == null)
            {
                phcDAO = (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
            }
            logger.debug("PublicHealthCase DT = " + phcVO.getThePublicHealthCaseDT());
            phcUID = phcDAO.create(phcVO.getThePublicHealthCaseDT());
            logger.debug("PublicHealthCase root uid = " + phcUID);
            phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(phcUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertPublicHealthCase()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertPublicHealthCase()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return phcUID;
    }//end of insertPublicHealthCase() method

    private void insertCaseManagementDT(PublicHealthCaseVO phcVO) throws  NEDSSSystemException
    {
        try
        {
            if(cmDAO == null)
            {
            	cmDAO = (CaseManagementDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CASE_MANAGEMENT_DAO_CLASS);
            }
             cmDAO.store(phcVO);
        }
        catch(NEDSSDAOSysException nexception)
        {
            logger.fatal("Fails insertCaseManagementDT(phcVO)"+nexception.getMessage(), nexception);
            throw new EJBException(nexception.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertCaseManagementDT(phcVO)"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertConfirmationMethods()

    
    private void insertConfirmationMethods(PublicHealthCaseVO phcVO) throws  NEDSSSystemException
    {
        try
        {
            if(confirmationMethodDAO == null)
            {
                confirmationMethodDAO = (ConfirmationMethodDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CONFIRMATION_METHOD_DAO_CLASS);
            }
            phcUID = confirmationMethodDAO.create((phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()).longValue(), phcVO.getTheConfirmationMethodDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertConfirmationMethods()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertConfirmationMethods()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertConfirmationMethods()

    private void insertActivityIDs(PublicHealthCaseVO phcVO)
            throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            phcUID = activityIdDAO.create((phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()).longValue(), phcVO.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException nodsex)
        {
            logger.fatal("Fails insertActivityIDs()"+nodsex.getMessage(), nodsex);
            throw new NEDSSDAOSysException(nodsex.toString());
        }

    }//end of insertActivityIds()

    private void insertActivityLocatorParticipations(PublicHealthCaseVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            phcUID = activityLocatorParticipationDAO.create((pvo.getThePublicHealthCaseDT().getPublicHealthCaseUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());
            //pvo.getTheActivityLocatorParticipationDTCollection().setPublicHealthCaseUid(new Long(phcUID));
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
    }//end of insertActivityLocatorParticipations()

    private PublicHealthCaseDT selectPublicHealthCase(long phcUID) throws NEDSSSystemException
    {
        try
        {
            if(phcDAO == null)
            {
                phcDAO = (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
            }
            return ((PublicHealthCaseDT)phcDAO.loadObject(phcUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("phcUID: "+phcUID+" Fails selectPublicHealthCase()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("phcUID: "+phcUID+" Fails selectPublicHealthCase()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectPublicHealthCase()

    private Collection<Object> selectConfirmationMethods(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(confirmationMethodDAO == null)
            {
                confirmationMethodDAO = (ConfirmationMethodDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CONFIRMATION_METHOD_DAO_CLASS);
            }
            return (confirmationMethodDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectConfirmationMethods()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectConfirmationMethods()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectConfirmationMethods()

    private CaseManagementDT selectCaseManagementDT(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(cmDAO == null)
            {
            	cmDAO = (CaseManagementDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CASE_MANAGEMENT_DAO_CLASS);
            }
            return (cmDAO.selectCaseManagementDT(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectCaseManagementDT()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectCaseManagementDT()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectCaseManagementDT
    
    private Collection<Object> selectActityIDs(long aUID) throws NEDSSSystemException
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
            logger.fatal("aUID: "+aUID+ " Fails selectActityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectActityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectActivityIDs()

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
            logger.fatal("aUID: "+aUID+ " Fails selectActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("aUID: "+aUID+ " Fails selectActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectActivityLocatorParticipations()

    private void removePublicHealthCase(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(phcDAO == null)
            {
                phcDAO = (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
            }
            phcDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("aUID: "+aUID+ " Exception ="+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("aUID: "+aUID+ " Exception ="+ndapex.getMessage(), ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }//end of removePublicHealthCase()

    private void removeConfirmationMethods(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(confirmationMethodDAO == null)
            {
                confirmationMethodDAO = (ConfirmationMethodDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CONFIRMATION_METHOD_DAO_CLASS);
            }
            confirmationMethodDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("aUID: "+aUID+ " Exception ="+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("aUID: "+aUID+ " Exception ="+ndapex.getMessage(), ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }//end of removeConfirmationMethods()

    private void removeCaseManagementDT(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(cmDAO == null)
            {
                cmDAO = (CaseManagementDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CASE_MANAGEMENT_DAO_CLASS);
            }
            cmDAO.removeCaseManagementDT(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("aUID: "+aUID+ " Exception ="+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("aUID: "+aUID+ " Exception ="+ndapex.getMessage(), ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }//end of removeConfirmationMethods()

    private void removeActityIDs(long aUID) throws NEDSSSystemException
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

        	logger.fatal("aUID: "+aUID+ " Exception ="+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("aUID: "+aUID+ " Exception ="+ndapex.getMessage(), ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void updatePublicHealthCase(PublicHealthCaseVO phcVO) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(phcDAO == null)
            {
                phcDAO = (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
            }
            phcDAO.store(phcVO.getThePublicHealthCaseDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updatePublicHealthCase()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updatePublicHealthCase() due to concurrent access! "+ncdaex.getMessage(), ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updatePublicHealthCase()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }


    }//end of updatePublicHealthCase()

    private void updateConfirmationMethods(PublicHealthCaseVO phcVO) throws NEDSSSystemException
    {
        try
        {
            if(confirmationMethodDAO == null)
            {
                confirmationMethodDAO = (ConfirmationMethodDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CONFIRMATION_METHOD_DAO_CLASS);
            }
            confirmationMethodDAO.store(phcVO.getTheConfirmationMethodDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateConfirmationMethods()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateConfirmationMethods()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateConfirmationMethods()

    private void updateCaseManagementDT(PublicHealthCaseVO phcVO) throws NEDSSSystemException
    {
        try
        {
            if(cmDAO == null)
            {
                cmDAO = (CaseManagementDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CASE_MANAGEMENT_DAO_CLASS);
            }
            if(phcVO.getTheCaseManagementDT()!=null && (phcVO.getTheCaseManagementDT().getPublicHealthCaseUid()!=null 
            		|| phcVO.getTheCaseManagementDT().isCaseManagementDTPopulated())){
            	cmDAO.store(phcVO);
            }
        }
        catch(NEDSSDAOSysException ndex)
        {
            logger.fatal("Fails updateCaseManagementDT()"+ndex.getMessage(), ndex);
            throw new NEDSSSystemException(ndex.toString());
        }
        catch(NEDSSSystemException ndsysex)
        {
            logger.fatal("Fails updateCaseManagementDT()"+ndsysex.getMessage(), ndsysex);
            throw new NEDSSSystemException(ndsysex.toString());
        }
    }//end of updateConfirmationMethods()

    
    private void updateActityIDs(PublicHealthCaseVO phcVO) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(phcVO.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActityIDs()"+ndapex.getMessage(), ndapex);

            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateActivityIDs()

    private void updateActivityLocatorParticipations(PublicHealthCaseVO pvo)
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
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateActivityLocatorParticipations()

    private Long findPublicHealthCase(long phcUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(phcDAO == null)
            {
                phcDAO = (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
            }
            findPK = phcDAO.findByPrimaryKey(phcUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findPublicHealthCase()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findPublicHealthCase()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }//end of findPublicHealthCase()

    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(publicHealthCaseActRelationshipDAOImpl == null) {
               publicHealthCaseActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (publicHealthCaseActRelationshipDAOImpl.load(aUID));


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
            if(publicHealthCaseParticipationDAOImpl == null) {
                publicHealthCaseParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (publicHealthCaseParticipationDAOImpl.loadAct(aUID));



        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+ " Fails selectParticipationDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("aUID: "+aUID+ " Fails selectParticipationDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        //Collection<Object> newPartDTs = new ArrayList<Object> ();
        try
        {
            if(publicHealthCaseParticipationDAOImpl == null)
            {
                publicHealthCaseParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                publicHealthCaseParticipationDAOImpl.store(partDT);
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
        //Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(publicHealthCaseActRelationshipDAOImpl == null)
            {
                publicHealthCaseActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                publicHealthCaseActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    public static void main(String args[])
    {

        Collection<Object> actDTs = new ArrayList<Object> ();
        ActRelationshipDT actDT1 = new ActRelationshipDT();
        ActRelationshipDT actDT2 = new ActRelationshipDT();
        //populate an ActRelationshipDT for testing purposes
		String aStatusCd = "A";
		Timestamp time = new Timestamp((new Date()).getTime());

                Long aActUid = new Long(8);
		actDT1.setTargetActUid(aActUid);
		Long aSourceUid = new Long(7);
		actDT1.setSourceActUid(aSourceUid);
		String aAddReasonCd = "Austin";
		actDT1.setAddReasonCd(aAddReasonCd);
		actDT1.setStatusCd(aStatusCd);
		actDT1.setStatusTime(time);
		actDT1.setItNew(true);

                //populate an ActRelationshipDT for testing purposes
		Long aActUid2 = new Long(9);
		actDT2.setTargetActUid(aActUid2);
		Long aSourceUid2 = new Long(7);
		actDT2.setSourceActUid(aSourceUid2);
		aAddReasonCd = "Powers";
		actDT2.setAddReasonCd(aAddReasonCd);
		actDT2.setStatusCd(aStatusCd);
		actDT2.setStatusTime(time);
		actDT2.setItNew(true);

                actDTs.add(actDT1);
                actDTs.add(actDT2);

                try
                {
                    PublicHealthCaseRootDAOImpl phc = new PublicHealthCaseRootDAOImpl();
                    phc.setActRelationship(actDTs);
                }
                catch(Exception e)
                {
                    logger.debug("Error is: Turkey don't worky: " + e.getMessage(), e);
                }
    }
    /**
     * 
     * @param phcVO
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removeConfirmationMethods(PublicHealthCaseVO phcVO) throws NEDSSSystemException
    {
        try
        {
            if(confirmationMethodDAO == null)
            {
                confirmationMethodDAO = (ConfirmationMethodDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CONFIRMATION_METHOD_DAO_CLASS);
            }
            confirmationMethodDAO.removeall(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails removeConfirmationMethods()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails removeConfirmationMethods()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of removeConfirmationMethods()
    
    public PublicHealthCaseDT getOpenPublicHealthCaseWithInvestigatorDT (Long uid) throws NEDSSSystemException {
    	try{
	    	PublicHealthCaseDAOImpl phc = new PublicHealthCaseDAOImpl();
	    	PublicHealthCaseDT phcDT=phc.getOpenPublicHealthCaseWithInvestigatorDT(uid);
	    	return phcDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    
}//end of PublicHealthCaseRootDAOImpl class
