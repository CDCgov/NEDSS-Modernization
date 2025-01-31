package gov.cdc.nedss.act.patientencounter.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
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

/**
 * Title:PatientEncounterRootDAOImpl
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PatientEncounterRootDAOImpl
    extends BMPBase {

    //For logging
    static final LogUtils logger = new LogUtils(PatientEncounterRootDAOImpl.class.getName());
    private PatientEncounterVO pvo = null;
    private long patientencounterUID;
    private PatientEncounterDAOImpl patientencounterDAO = null;
    private ActivityIdDAOImpl activityIdDAO = null;
    private ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private ActRelationshipDAOImpl patientEncounterActRelationshipDAOImpl = null;
    private ParticipationDAOImpl patientEncounterParticipationDAOImpl = null;

    public PatientEncounterRootDAOImpl() {
    }

    /**
     *
     * @param obj
     * @return long
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(Object obj)
                throws NEDSSSystemException, NEDSSSystemException {
        this.pvo = (PatientEncounterVO)obj;

        if (this.pvo != null)
            patientencounterUID = insertPatientEncounter(this.pvo);

        logger.debug("PatientEncounter UID = " + patientencounterUID);
        (this.pvo.getThePatientEncounterDT()).setPatientEncounterUid(new Long(patientencounterUID));

        if (this.pvo != null && this.pvo.getTheActivityIdDTCollection() != null)
            insertActivityIDs(this.pvo);

        if (this.pvo != null && this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
            insertActivityLocatorParticipations(this.pvo);

        this.pvo.setItNew(false);
        this.pvo.setItDirty(false);

        return ((((PatientEncounterVO)obj).getThePatientEncounterDT().getPatientEncounterUid()).longValue());
    }

    /**
     *
     * @param obj
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj)
               throws NEDSSSystemException, NEDSSSystemException, NEDSSConcurrentDataException {
        this.pvo = (PatientEncounterVO)obj;

        if (this.pvo.getThePatientEncounterDT() != null && this.pvo.getThePatientEncounterDT()
                .isItNew()) {
            insertPatientEncounter(this.pvo);
            this.pvo.getThePatientEncounterDT().setItNew(false);
            this.pvo.getThePatientEncounterDT().setItDirty(false);
        } else if (this.pvo.getThePatientEncounterDT() != null && this.pvo.getThePatientEncounterDT()
                .isItDirty()) {
            updatePatientEncounter(this.pvo);
            this.pvo.getThePatientEncounterDT().setItDirty(false);
            this.pvo.getThePatientEncounterDT().setItNew(false);
        }

        if (this.pvo.getTheActivityIdDTCollection() != null) {
            updateActivityIDs(this.pvo);
        }

        if (this.pvo.getTheActivityLocatorParticipationDTCollection() != null) {
            updateActivityLocatorParticipations(this.pvo);
        }
    }

    /**
     *
     * @param patientencounterUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public void remove(long patientencounterUID)
                throws NEDSSSystemException, NEDSSSystemException {

        //        removeActivityLocatorParticipations(patientencounterUID);
        removeActivityIDs(patientencounterUID);
        removePatientEncounter(patientencounterUID);
    }

    /**
     *
     * @param patientencounterUID
     * @return Object
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public Object loadObject(long patientencounterUID)
                      throws NEDSSSystemException, NEDSSSystemException {
        this.pvo = new PatientEncounterVO();

        PatientEncounterDT pDT = selectPatientEncounter(patientencounterUID);
        this.pvo.setThePatientEncounterDT(pDT);

        Collection<Object> idColl = selectActivityIDs(patientencounterUID);
        this.pvo.setTheActivityIdDTCollection(idColl);

        Collection<Object> elpColl = selectActivityLocatorParticipations(patientencounterUID);
        this.pvo.setTheActivityLocatorParticipationDTCollection(elpColl);

        //Selects ActRelationshiopDTcollection
        Collection<Object> actColl = selectActRelationshipDTCollection(patientencounterUID);
        this.pvo.setTheActRelationshipDTCollection(actColl);

        //SelectsParticipationDTCollection
        Collection<Object> parColl = selectParticipationDTCollection(patientencounterUID);
        this.pvo.setTheParticipationDTCollection(parColl);
        this.pvo.setItNew(false);
        this.pvo.setItDirty(false);

        return this.pvo;
    }

    /**
     *
     * @param patientencounterUID
     * @return Long patientencounterPK
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long patientencounterUID)
                          throws NEDSSSystemException, NEDSSSystemException {

        Long patientencounterPK = findPatientEncounter(patientencounterUID);
        logger.debug("Done find by primarykey!");

        return patientencounterPK;
    }

    /**
     *
     * @param pvo
     * @return long
     * @throws NEDSSSystemException
     */
    private long insertPatientEncounter(PatientEncounterVO pvo)
                                 throws NEDSSSystemException {

        try {

            if (patientencounterDAO == null) {
                patientencounterDAO = (PatientEncounterDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_DAO_CLASS);
            }

            logger.debug("PatientEncounter DT = " + pvo.getThePatientEncounterDT());
            patientencounterUID = patientencounterDAO.create(pvo.getThePatientEncounterDT());
            logger.debug("PatientEncounter root uid = " + patientencounterUID);
            pvo.getThePatientEncounterDT().setPatientEncounterUid(new Long(patientencounterUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails insertPatientEncounter()", ndsex);
            throw new EJBException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails insertPatientEncounter()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }

        return patientencounterUID;
    }

    /**
     *
     * @param pvo
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void insertActivityIDs(PatientEncounterVO pvo)
                            throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityIdDAO == null) {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }

            patientencounterUID = activityIdDAO.create((pvo.getThePatientEncounterDT().getPatientEncounterUid()).longValue(), pvo.getTheActivityIdDTCollection());
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails insertActivityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails insertActivityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param pvo
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void insertActivityLocatorParticipations(PatientEncounterVO pvo)
                                              throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityLocatorParticipationDAO == null) {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }

            patientencounterUID = activityLocatorParticipationDAO.create((pvo.getThePatientEncounterDT().getPatientEncounterUid()).longValue(), pvo.getTheActivityLocatorParticipationDTCollection());

            //pvo.getTheActivityLocatorParticipationDTCollection().setPatientEncounterUid(new Long(patientencounterUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails insertActivityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails insertActivityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param patientencounterUID
     * @return PatientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private PatientEncounterDT selectPatientEncounter(long patientencounterUID)
                                               throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (patientencounterDAO == null) {
                patientencounterDAO = (PatientEncounterDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_DAO_CLASS);
            }

            return (PatientEncounterDT)patientencounterDAO.loadObject(patientencounterUID);
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectPatientEncounter()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails selectPatientEncounter()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param aUID
     * @return Collection
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectActivityIDs(long aUID)
                                  throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityIdDAO == null) {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }

            return (activityIdDAO.load(aUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectPatientEncounter()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails selectPatientEncounter()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param aUID
     * @return Collection
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectActivityLocatorParticipations(long aUID)
        throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityLocatorParticipationDAO == null) {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }

            return (activityLocatorParticipationDAO.load(aUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActivityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActivityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param aUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removePatientEncounter(long aUID)
                                 throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (patientencounterDAO == null) {
                patientencounterDAO = (PatientEncounterDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_DAO_CLASS);
            }

            patientencounterDAO.remove(aUID);
        } catch (NEDSSDAOSysException ndsex) {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
         catch (NEDSSSystemException ndapex) {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    /**
     *
     * @param aUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removeActivityIDs(long aUID)
                            throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityIdDAO == null) {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }

            activityIdDAO.remove(aUID);
        } catch (NEDSSDAOSysException ndsex) {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
         catch (NEDSSSystemException ndapex) {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    /*
        private void removeActivityLocatorParticipations(long aUID) throws NEDSSSystemException, NEDSSSystemException
        {
            try
            {
                if(activityLocatorParticipationDAO == null)
                {
                    activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
                }
                activityLocatorParticipationDAO.remove(aUID);
            }
            catch(NEDSSDAOSysException ndsex)
            {
                cntx.setRollbackOnly();
                ndsex.printStackTrace();
                throw new NEDSSSystemException(ndsex.getMessage());
            }
            catch(NEDSSSystemException ndapex)
            {
                ndapex.printStackTrace();
                cntx.setRollbackOnly();
                throw new NEDSSSystemException(ndapex.getMessage());
            }
        }
    */

    /**
     *
     * @param pvo
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    private void updatePatientEncounter(PatientEncounterVO pvo)
                                 throws NEDSSSystemException, NEDSSSystemException, NEDSSConcurrentDataException {

        try {

            if (patientencounterDAO == null) {
                patientencounterDAO = (PatientEncounterDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_DAO_CLASS);
            }

            patientencounterDAO.store(pvo.getThePatientEncounterDT());
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updatePatientEncounter()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSConcurrentDataException ncdaex) {
            logger.fatal("Fails updatePatientEncounter() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails updatePatientEncounter()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param pvo
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void updateActivityIDs(PatientEncounterVO pvo)
                            throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityIdDAO == null) {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }

            activityIdDAO.store(pvo.getTheActivityIdDTCollection());
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateActivityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails updateActivityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param pvo
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void updateActivityLocatorParticipations(PatientEncounterVO pvo)
                                              throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (activityLocatorParticipationDAO == null) {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }

            activityLocatorParticipationDAO.store(pvo.getTheActivityLocatorParticipationDTCollection());
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateActivityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails updateActivityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     *
     * @param patientencounterUID
     * @return Long
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Long findPatientEncounter(long patientencounterUID)
                               throws NEDSSSystemException, NEDSSSystemException {

        Long findPK = null;

        try {

            if (patientencounterDAO == null) {
                patientencounterDAO = (PatientEncounterDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_DAO_CLASS);
            }

            findPK = patientencounterDAO.findByPrimaryKey(patientencounterUID);
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails findPatientEncounter()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails findPatientEncounter()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }

        return findPK;
    }

    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park

    /**
     *
     * @param aUID
     * @return Long
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectActRelationshipDTCollection(long aUID)
                                                  throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (patientEncounterActRelationshipDAOImpl == null) {
                patientEncounterActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }

            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);

            return (patientEncounterActRelationshipDAOImpl.load(aUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    //get collection of Participation  from ParticipationDAOImpl entered by John Park

    /**
     *
     * @param aUID
     * @return Long
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectParticipationDTCollection(long aUID)
                                                throws NEDSSSystemException, NEDSSSystemException {

        try {

            if (patientEncounterParticipationDAOImpl == null) {
                patientEncounterParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }

            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);

            return (patientEncounterParticipationDAOImpl.loadAct(aUID));
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    //set a collection of participationDT and return the participationDTs with sequences- Wade Steele

    /**
     *
     * @param partDTs
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public void setParticipation(Collection<Object> partDTs)
                          throws NEDSSSystemException, NEDSSSystemException {

        //Collection<Object> newPartDTs = new ArrayList<Object> ();

        try {

            if (patientEncounterParticipationDAOImpl == null) {
                patientEncounterParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }

            Iterator<Object> iter = partDTs.iterator();

            while (iter.hasNext()) {

                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                patientEncounterParticipationDAOImpl.store(partDT);
            }
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    //set a collection of ActRelationships - Wade Steele

    /**
     *
     * @param actRelDTs
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public void setActRelationship(Collection<Object> actRelDTs)
                            throws NEDSSSystemException, NEDSSSystemException {

      //  Collection<Object> newActRelDTs = new ArrayList<Object> ();

        try {

            if (patientEncounterActRelationshipDAOImpl == null) {
                patientEncounterActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }

            Iterator<Object> iter = actRelDTs.iterator();

            while (iter.hasNext()) {

                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                patientEncounterActRelationshipDAOImpl.store(actRelDT);
            }
        } catch (NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
         catch (NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
} //end of PatientEncounterRootDAOImpl class