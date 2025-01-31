/**
* Name:		ObservationRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               ObservationVO value object in the Observation entity bean.
*               This class encapsulates all the JDBC calls made by the ObservationEJB
*               for a Observation object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of ObservationEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.observation.ejb.dao;


import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
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
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.EJBException;


public class ObservationRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(ObservationRootDAOImpl.class.getName());

    private ObservationVO obVO = null;
    private long obUID;
    private  ObservationDAOImpl observationDAO = null;
    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ObservationReasonDAOImpl observationReasonDAO = null;
    private  ObservationInterpDAOImpl observationInterpDAO = null;
    private  ObsValueCodedDAOImpl obsValueCodedDAO = null;
    private  ObsValueTxtDAOImpl obsValueTxtDAO = null;
    private  ObsValueDateDAOImpl obsValueDateDAO = null;
    private  ObsValueNumericDAOImpl obsValueNumericDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl obsActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl obsParticipationDAOImpl = null;

    public ObservationRootDAOImpl()
    {
    }

    public HashMap<Object, Object> UpdateObservationCollections(Collection<ObservationVO> ObservationCollections) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
      Long falseUid = null;
      Long realUid =  null;
      HashMap<Object, Object> realFalseUidMap = new HashMap<Object, Object>();
      try{
	      if(ObservationCollections != null)
	      {
	        Iterator<ObservationVO> it = ObservationCollections.iterator();
	        while(it.hasNext())
	        {
	          ObservationVO obvo = (ObservationVO)it.next();
	          if(obvo != null)
	          {
	            falseUid = obvo.getTheObservationDT().getObservationUid();
	            if(obvo.isItNew())
	            {
	              obvo.getTheObservationDT().setVersionCtrlNbr(new Integer(1));
	              if(obvo.getTheObservationDT().getSharedInd() == null)
	              obvo.getTheObservationDT().setSharedInd("T");
	
	              realUid = new Long(create(obvo));
	              realFalseUidMap.put(falseUid, realUid);
	            }
	            if(obvo.isItDirty())
	            {
	              //realUid = store(obvo);
	              obvo.getTheObservationDT().setVersionCtrlNbr(new Integer(obvo.getTheObservationDT().getVersionCtrlNbr().intValue()+1));
	              store(obvo);
	            }
	            if(obvo.isItDelete())
	            {
	              remove( obvo.getTheObservationDT().getObservationUid().longValue());
	            }
	          }
	        }
	      }
      }catch(Exception ex){
    	  logger.fatal("Exception  = "+ex.getMessage(), ex);
    	  throw new NEDSSSystemException(ex.toString());
      }
      return realFalseUidMap;
    }
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.obVO = (ObservationVO)obj;
	
	        /**
	        *  Inserts ObservationDT object
	        */
	
	        if (this.obVO != null)
	        obUID = insertObservation(this.obVO);
	        logger.debug("Observation UID = " + obUID + " for cd: " + obVO.getTheObservationDT().getCd());
	        (this.obVO.getTheObservationDT()).setObservationUid(new Long(obUID));
	
	
	        /**
	        * Inserts ObservationReasonDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObservationReasonDTCollection() != null)
	        insertObservationReasons(this.obVO);
	
	        /**
	        * Inserts ActIdDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheActIdDTCollection() != null)
	        insertActivityIDs(this.obVO);
	
	        /**
	        * Inserts ObservationInterpDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObservationInterpDTCollection() != null)
	        insertObservationInterps(this.obVO);
	
	        /**
	        * Inserts ObsValueCodedDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObsValueCodedDTCollection() != null)
	        insertObsValueCodeds(this.obVO);
	
	        /**
	        * Inserts ObsValueTxtDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObsValueTxtDTCollection() != null)
	        insertObsValueTxts(this.obVO);
	
	        /**
	        * Inserts ObsValueTxtDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObsValueDateDTCollection() != null)
	        insertObsValueDates(this.obVO);
	
	        /**
	        * Inserts ObsValueTxtDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheObsValueNumericDTCollection() != null)
	        insertObsValueNumerics(this.obVO);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.obVO != null && this.obVO.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.obVO);
	
	        this.obVO.setItNew(false);
	        this.obVO.setItDirty(false);
	        return ((((ObservationVO)obj).getTheObservationDT().getObservationUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try{
	        this.obVO = (ObservationVO)obj;
	
	        /**
	        *  Updates ObservationDT object
	        */
	        if(obVO != null && obVO.getTheObservationDT() != null)
	        logger.info(" Updates observation for cd: " + obVO.getTheObservationDT().getCd());
	        if(this.obVO.getTheObservationDT() != null && this.obVO.getTheObservationDT().isItNew())
	        {
	            insertObservation(this.obVO);
	            this.obVO.getTheObservationDT().setItNew(false);
	            this.obVO.getTheObservationDT().setItDirty(false);
	        }
	        else if(this.obVO.getTheObservationDT() != null && this.obVO.getTheObservationDT().isItDirty())
	        {
	            updateObservation(this.obVO);
	            this.obVO.getTheObservationDT().setItDirty(false);
	            this.obVO.getTheObservationDT().setItNew(false);
	        }
	
	        /**
	        * Updates Observation reasons collection
	        */
	
	        if (this.obVO.getTheObservationReasonDTCollection() != null)
	        {
	            updateObservationReasons(this.obVO);
	        }
	
	        /**
	         * Updates actity ids collection
	         */
	        if(this.obVO.getTheActIdDTCollection() != null)
	        {
	            updateActityIDs(this.obVO);
	        }
	
	        /**
	        * Updates observation interps collection
	        */
	        if (this.obVO.getTheObservationInterpDTCollection() != null)
	        {
	            updateObservationInterps(this.obVO);
	        }
	
	        /**
	        * Updates obs value codeds collection
	        */
	        if (this.obVO.getTheObsValueCodedDTCollection() != null)
	        {
	            updateObsValueCodeds(this.obVO);
	        }
	
	        /**
	        * Updates obs value txts collection
	        */
	        if (this.obVO.getTheObsValueTxtDTCollection() != null)
	        {
	            updateObsValueTxts(this.obVO);
	        }
	
	        /**
	        * Updates obs value dates collection
	        */
	        if (this.obVO.getTheObsValueDateDTCollection() != null)
	        {
	            updateObsValueDates(this.obVO);
	        }
	
	        /**
	        * Updates obs value numerics collection
	        */
	        if (this.obVO.getTheObsValueNumericDTCollection() != null)
	        {
	            updateObsValueNumerics(this.obVO);
	        }
	
	        /**
	        * Updates ActivityLocatorParticipationDT collection
	        */
	        if (this.obVO.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.obVO);
	        }
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }

    public void remove(long obUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes ObservationInterpDT collection
	        */
	
	        removeObservationInterps(obUID);
	
	        /**
	        * Removes ObsValueCodedDT collection
	        */
	
	        removeObsValueCodeds(obUID);
	
	        /**
	        * Removes ObsValueTxtDT collection
	        */
	
	        removeObsValueTxts(obUID);
	
	        /**
	        * Removes ObsValueDateDT collection
	        */
	
	        removeObsValueDates(obUID);
	
	        /**
	        * Removes ObsValueNumericDT collection
	        */
	
	        removeObsValueNumerics(obUID);
	
	        /**
	        * Removes ActityIdDT Collection
	        */
	
	        removeActityIDs(obUID);
	
	        /**
	        * Removes ObservationReasonDT Collection
	        */
	
	        removeObservationReasons(obUID);
	
	        /**
	        *  Removes ObservationDT
	        */
	
	        removeObservation(obUID);
    	}catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }

    public Object loadObject(long obUID) throws
		NEDSSSystemException
    {
    	try{
	        this.obVO = new ObservationVO();
	
	        /**
	        *  Selects ObservationDT object
	        */
	
	        ObservationDT obDT = selectObservation(obUID);
	        this.obVO.setTheObservationDT(obDT);
	        /**
	        * Selects ObservationReasonDT Collection
	        */
	
	        Collection<Object> obReasonColl = selectObservationReasons(obUID);
	        this.obVO.setTheObservationReasonDTCollection(obReasonColl);
	
	        /**
	        * Selects ActityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActityIDs(obUID);
	        this.obVO.setTheActIdDTCollection(idColl);
	
	        /**
	        * Selects ObservationInterpDT collection
	        */
	
	        Collection<Object> obInterpColl = selectObservationInterps(obUID);
	        this.obVO.setTheObservationInterpDTCollection(obInterpColl);
	
	        /**
	        * Selects ObsValueCodedDT collection
	        */
	
	        Collection<Object> obsValueCodedColl = selectObsValueCodeds(obUID);
	        this.obVO.setTheObsValueCodedDTCollection(obsValueCodedColl);
	
	        /**
	        * Selects ObsValueTxtDT collection
	        */
	
	        Collection<Object> obsValueTxtColl = selectObsValueTxts(obUID);
	        this.obVO.setTheObsValueTxtDTCollection(obsValueTxtColl);
	
	        /**
	        * Selects ObsValueDateDT collection
	        */
	
	        Collection<Object> obsValueDateColl = selectObsValueDates(obUID);
	        this.obVO.setTheObsValueDateDTCollection(obsValueDateColl);
	
	        /**
	        * Selects ObsValueNumericDT collection
	        */
	
	        Collection<Object> obsValueNumericColl = selectObsValueNumerics(obUID);
	        this.obVO.setTheObsValueNumericDTCollection(obsValueNumericColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> activityLocatorParticipationColl = selectActivityLocatorParticipations(obUID);
	        this.obVO.setTheActivityLocatorParticipationDTCollection(activityLocatorParticipationColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(obUID);
	        this.obVO.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(obUID);
	        this.obVO.setTheParticipationDTCollection(parColl);
	
	        this.obVO.setItNew(false);
	        this.obVO.setItDirty(false);
	        return this.obVO;
    	}catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }

    public Long findByPrimaryKey(long obUID) throws
		NEDSSSystemException
    {
    	try{
	        /**
	         * Finds Observation object
	         */
	        Long observationPK = findObservation(obUID);
	
	        logger.debug("Done find by primarykey!");
	        return observationPK;
    	}catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }

    private long insertObservation(ObservationVO obVO) throws  NEDSSSystemException
    {
        try
        {
            if(observationDAO == null)
            {
                observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
            }
            logger.debug("Observation DT = " + obVO.getTheObservationDT());
            obUID = observationDAO.create(obVO.getTheObservationDT());
            logger.debug("Observation root uid = " + obUID);
            obVO.getTheObservationDT().setObservationUid(new Long(obUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObservation()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertObservation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return obUID;
    }//end of insertObservation() method

    private void insertObservationReasons(ObservationVO obVO) throws  NEDSSSystemException
    {
        try
        {
            if(observationReasonDAO == null)
            {
                observationReasonDAO = (ObservationReasonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_REASON_DAO_CLASS);
            }
            obUID = observationReasonDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObservationReasonDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObservationReasons()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertObservationReasons()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertObservationReasons()

    private void insertActivityIDs(ObservationVO obVO)
            throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            obUID = activityIdDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException nodsex)
        {
            logger.fatal("Fails insertActivityIDs()"+nodsex.getMessage(), nodsex);
            throw new NEDSSDAOSysException(nodsex.toString());
        }

    }//end of insertActivityIds()

    private void insertObservationInterps(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationInterpDAO == null)
            {
                observationInterpDAO = (ObservationInterpDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_INTERP_DAO_CLASS);
            }
            obUID = observationInterpDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObservationInterpDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObservationInterps()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
    }//end of insertObservationInterps()

    private void insertObsValueCodeds(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueCodedDAO == null)
            {
                obsValueCodedDAO = (ObsValueCodedDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_CODED_DAO_CLASS);
            }
            obUID = obsValueCodedDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObsValueCodedDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObsValueCodeds()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertObsValueCodeds()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertObsValueCodeds()

    private void insertObsValueTxts(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueTxtDAO == null)
            {
                obsValueTxtDAO = (ObsValueTxtDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_TXT_DAO_CLASS);
            }
            obUID = obsValueTxtDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObsValueTxtDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObsValueTxts()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertObsValueTxts()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertObsValueTxts()

    private void insertObsValueDates(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueDateDAO == null)
            {
                obsValueDateDAO = (ObsValueDateDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_DATE_DAO_CLASS);
            }
            obUID = obsValueDateDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObsValueDateDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObsValueDates()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertObsValueDates()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of insertObsValueDates()

    private void insertObsValueNumerics(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueNumericDAO == null)
            {
                obsValueNumericDAO = (ObsValueNumericDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_NUMERIC_DAO_CLASS);
            }
            obUID = obsValueNumericDAO.create((obVO.getTheObservationDT().getObservationUid()).longValue(), obVO.getTheObsValueNumericDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertObsValueNumerics()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        
    }//end of insertObsValueNumerics()

    private void insertActivityLocatorParticipations(ObservationVO pvo) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            obUID = activityLocatorParticipationDAO.create((pvo.getTheObservationDT().getObservationUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());
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

    private ObservationDT selectObservation(long obUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationDAO == null)
            {
                observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
            }
            return ((ObservationDT)observationDAO.loadObject(obUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObservation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectObservation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectObservation()

    private Collection<Object> selectObservationReasons(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationReasonDAO == null)
            {
                observationReasonDAO = (ObservationReasonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_REASON_DAO_CLASS);
            }
            return (observationReasonDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObservationReasons()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectObservationReasons()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectObservationReasons()

    private Collection<Object> selectActityIDs(long aUID) throws NEDSSSystemException, NEDSSSystemException
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
            logger.fatal("Fails selectActityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectActivityIDs()

    private Collection<Object> selectObservationInterps(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationInterpDAO == null)
            {
                observationInterpDAO = (ObservationInterpDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_INTERP_DAO_CLASS);
            }
            return (observationInterpDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObservationInterps()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }

    }//end of selectObservationInterps()

    private Collection<Object> selectObsValueCodeds(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueCodedDAO == null)
            {
                obsValueCodedDAO = (ObsValueCodedDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_CODED_DAO_CLASS);
            }
            return (obsValueCodedDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObsValueCodeds()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectObsValueCodeds()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectObsValueCodeds()

    private Collection<Object> selectObsValueTxts(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueTxtDAO == null)
            {
                obsValueTxtDAO = (ObsValueTxtDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_TXT_DAO_CLASS);
            }
            return (obsValueTxtDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObsValueTxts()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectObsValueTxts()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectObsValueTxts()

    private Collection<Object> selectObsValueDates(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueDateDAO == null)
            {
                obsValueDateDAO = (ObsValueDateDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_DATE_DAO_CLASS);
            }
            return (obsValueDateDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObsValueDates()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectObsValueDates()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of selectObsValueDates()

    private Collection<Object> selectObsValueNumerics(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueNumericDAO == null)
            {
                obsValueNumericDAO = (ObsValueNumericDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_NUMERIC_DAO_CLASS);
            }
            return (obsValueNumericDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectObsValueNumerics()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
    }//end of selectObsValueNumerics()

    private Collection<Object> selectActivityLocatorParticipations(long aUID) throws NEDSSSystemException, NEDSSSystemException
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
    }//end of selectActivityLocatorParticipations()

    private void removeObservation(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationDAO == null)
            {
                observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
            }
            observationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

            logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }//end of removeObservation()

    private void removeObservationReasons(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationReasonDAO == null)
            {
                observationReasonDAO = (ObservationReasonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_REASON_DAO_CLASS);
            }
            observationReasonDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }//end of removeObservationReasons()


    private void removeActityIDs(long aUID) throws NEDSSSystemException, NEDSSSystemException
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

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeObservationInterps(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationInterpDAO == null)
            {
                observationInterpDAO = (ObservationInterpDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_INTERP_DAO_CLASS);
            }
            observationInterpDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
    }

    private void removeObsValueCodeds(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueCodedDAO == null)
            {
                obsValueCodedDAO = (ObsValueCodedDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_CODED_DAO_CLASS);
            }
            obsValueCodedDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeObsValueTxts(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueTxtDAO == null)
            {
                obsValueTxtDAO = (ObsValueTxtDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_TXT_DAO_CLASS);
            }
            obsValueTxtDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeObsValueDates(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueDateDAO == null)
            {
                obsValueDateDAO = (ObsValueDateDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_DATE_DAO_CLASS);
            }
            obsValueDateDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException ="+ndapex.getMessage(),ndapex);

            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    private void removeObsValueNumerics(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueNumericDAO == null)
            {
                obsValueNumericDAO = (ObsValueNumericDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_NUMERIC_DAO_CLASS);
            }
            obsValueNumericDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {

        	logger.fatal("NEDSSDAOSysException ="+ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        
    }


    private void updateObservation(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(observationDAO == null)
            {
                observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
            }
            observationDAO.store(obVO.getTheObservationDT());
        }
         catch(NEDSSConcurrentDataException ncde)
         {
            logger.fatal("Fails updateObservation() inside dataconcu..."+ncde.getMessage(), ncde);
            throw new NEDSSConcurrentDataException(ncde.toString());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObservation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }

    }//end of updateObservation()

    private void updateObservationReasons(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationReasonDAO == null)
            {
                observationReasonDAO = (ObservationReasonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_REASON_DAO_CLASS);
            }
            observationReasonDAO.store(obVO.getTheObservationReasonDTCollection());

        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObservationReasons()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateObservationReasons()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateObservationReasons()



    private void updateActityIDs(ObservationVO obVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(obVO.getTheActIdDTCollection());
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

    private void updateObservationInterps(ObservationVO obVO)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(observationInterpDAO == null)
            {
                observationInterpDAO = (ObservationInterpDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_INTERP_DAO_CLASS);
            }
            observationInterpDAO.store(obVO.getTheObservationInterpDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObservationInterps()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        
    }//end of updateObservationInterps()

    private void updateObsValueCodeds(ObservationVO obVO)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueCodedDAO == null)
            {
                obsValueCodedDAO = (ObsValueCodedDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_CODED_DAO_CLASS);
            }
            obsValueCodedDAO.store(obVO.getTheObsValueCodedDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObsValueCodeds()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateObsValueCodeds()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateObsValueCodeds()

    private void updateObsValueTxts(ObservationVO obVO)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueTxtDAO == null)
            {
                obsValueTxtDAO = (ObsValueTxtDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_TXT_DAO_CLASS);
            }
            obsValueTxtDAO.store(obVO.getTheObsValueTxtDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObsValueTxts()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateObsValueTxts()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateObsValueTxts()

    private void updateObsValueDates(ObservationVO obVO)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueDateDAO == null)
            {
                obsValueDateDAO = (ObsValueDateDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_DATE_DAO_CLASS);
            }
            obsValueDateDAO.store(obVO.getTheObsValueDateDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObsValueDates()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateObsValueDates()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }//end of updateObsValueDates()

    private void updateObsValueNumerics(ObservationVO obVO)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(obsValueNumericDAO == null)
            {
                obsValueNumericDAO = (ObsValueNumericDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBS_VALUE_NUMERIC_DAO_CLASS);
            }
            obsValueNumericDAO.store(obVO.getTheObsValueNumericDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateObsValueNumerics()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        
    }//end of updateObsValueNumerics()

    private void updateActivityLocatorParticipations(ObservationVO pvo)
              throws NEDSSSystemException, NEDSSSystemException
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

    private Long findObservation(long obUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(observationDAO == null)
            {
                observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
            }
            findPK = observationDAO.findByPrimaryKey(obUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findObservation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findObservation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }//end of findObservation()

        //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        try  {
            if(obsActRelationshipDAOImpl == null) {
               obsActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (obsActRelationshipDAOImpl.load(aUID));


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
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        try  {
            if(obsParticipationDAOImpl == null) {
                obsParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (obsParticipationDAOImpl.loadAct(aUID));



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
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        Collection<Object> newPartDTs = new ArrayList<Object> ();
        try
        {
            if(obsParticipationDAOImpl == null)
            {
                obsParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                obsParticipationDAOImpl.store(partDT);
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
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(obsActRelationshipDAOImpl == null)
            {
                obsActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                obsActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

public Collection<Object> getAssociatedPublicHealthCasesForLab(Long uid,  boolean alreadyAssociated ) {
	Collection<Object> coll = null;
    try
     {
		 if(observationDAO == null)
         {
             observationDAO = (ObservationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_DAO_CLASS);
         }
         coll  = observationDAO.openPublicHealthCaseForLab(uid, alreadyAssociated);
         }
	     catch(NEDSSDAOSysException ndsex)
	     {
	         logger.fatal("Fails insertObservation()"+ndsex.getMessage(), ndsex);
	         throw new EJBException(ndsex.toString());
	     }
	     catch(NEDSSSystemException ndapex)
	     {
         logger.fatal("Fails insertObservation()"+ndapex.getMessage(), ndapex);
         throw new NEDSSSystemException(ndapex.toString());
     }
     return coll;
}

}//end of ObservationRootDAOImpl class
