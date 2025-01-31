package gov.cdc.nedss.act.patientencounter.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActIdHistDAOImpl;
import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.association.dao.ActRelationshipHistDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationHistDAOImpl;
import gov.cdc.nedss.util.LogUtils;

import java.util.Collection;

/**
 * Title:PatientEncounterHistoryManager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PatientEncounterHistoryManager {

    static final LogUtils logger = new LogUtils(PatientEncounterHistoryManager.class.getName());
    private PatientEncounterVO peVoHist = null;
    private long patientEncounterUid;
    private PatientEncounterHistDAOImpl peHistDAOImpl;
    private short versionCtrlNbr = -1;
    private ActIdHistDAOImpl actIdHistDAO;
    private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
    private ActRelationshipHistDAOImpl actRelationshipHist;
    private ParticipationHistDAOImpl participationHistDAOImpl;

    public PatientEncounterHistoryManager() {
    } //end of constructor

    public PatientEncounterHistoryManager(long patientEncounterUid, short versionCtrlNbr)
                                   throws NEDSSDAOSysException {
        this.patientEncounterUid = patientEncounterUid;
        peHistDAOImpl = new PatientEncounterHistDAOImpl(patientEncounterUid, versionCtrlNbr);
        this.versionCtrlNbr = peHistDAOImpl.getVersionCtrlNbr();
        logger.debug("PatientEncounterHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        actIdHistDAO = new ActIdHistDAOImpl(versionCtrlNbr);
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
    } //end of constructor

    /**
     *
     * @param obj
     * @throws NEDSSDAOSysException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException {
        peVoHist = (PatientEncounterVO)obj;

        if (peVoHist == null)

            return;

        if (peVoHist.getThePatientEncounterDT() != null) {
            insertPatientEncounter(peVoHist.getThePatientEncounterDT());
            peVoHist.getThePatientEncounterDT().setItNew(false);
        }

        if (peVoHist.getTheActivityIdDTCollection() != null)
            insertActIdDTColl(peVoHist.getTheActivityIdDTCollection());

        if (peVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
            insertActivityLocatorParticipationDTCollection(peVoHist.getTheActivityLocatorParticipationDTCollection());
        }

        if (peVoHist.getTheActRelationshipDTCollection() != null) {
            insertActRelationshipDTCollection(peVoHist.getTheActRelationshipDTCollection());
        }

        if (peVoHist.getTheParticipationDTCollection() != null) {
            insertParticipationDTCollection(peVoHist.getTheParticipationDTCollection());
        }
    } //end of store

    /**
     *
     * @param patientEncounterUid
     * @param versionCtrlNbr
     * @return PatientEncounterVO
     * @throws NEDSSSystemException
     */
    public PatientEncounterVO load(Long patientEncounterUid, Integer versionCtrlNbr)
                            throws NEDSSSystemException {
        logger.info("Starts loadObject() for a patientEncounterVo for the history...");

        PatientEncounterVO pevo = new PatientEncounterVO();
        peHistDAOImpl = new PatientEncounterHistDAOImpl();
        actIdHistDAO = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();

        PatientEncounterDT peDT = peHistDAOImpl.load(patientEncounterUid, versionCtrlNbr);
        pevo.setThePatientEncounterDT(peDT);

        Collection<Object> actIdHistColl = actIdHistDAO.load(patientEncounterUid, versionCtrlNbr);
        pevo.setTheActivityIdDTCollection(actIdHistColl);

        //waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
        //waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);
        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);
        logger.info("Done loadObject() for a patientEncounterVo for history - return: " + pevo);

        return pevo;
    } //end of load

    /**
     *
     * @param peDt
     * @throws NEDSSDAOSysException
     */
    private void insertPatientEncounter(PatientEncounterDT peDt)
                                 throws NEDSSDAOSysException {

        if (peDt == null)
            throw new NEDSSDAOSysException("Error: insert null PatientEncounterDt into patient encounter Hist.");

        peHistDAOImpl.store(peDt);
    } //end of insertPatientEncounter

    /**
     *
     * @param coll
     * @throws NEDSSDAOSysException
     */
    private void insertActIdDTColl(Collection<Object> coll)
                            throws NEDSSDAOSysException {

        if (coll == null)
            throw new NEDSSDAOSysException("Error: insert null actIdDT collection into act id history.");

        actIdHistDAO.store(coll);
    }

    /**
     *
     * @param coll
     * @throws NEDSSDAOSysException
     */
    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)
        throws NEDSSDAOSysException {

        if (coll == null)
            throw new NEDSSDAOSysException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");

        activityLocatorParticipationHistDAOImpl.store(coll);
    }

    /**
     *
     * @param coll
     * @throws NEDSSDAOSysException
     */
    private void insertActRelationshipDTCollection(Collection<Object> coll)
                                            throws NEDSSDAOSysException {

        if (coll == null)
            throw new NEDSSDAOSysException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");

        actRelationshipHist.store(coll);
    }

    /**
     *
     * @param coll
     * @throws NEDSSDAOSysException
     */
    private void insertParticipationDTCollection(Collection<Object> coll)
                                          throws NEDSSDAOSysException {

        if (coll == null)
            throw new NEDSSDAOSysException("Error: insert null ParticipationDTCollection  into act participation hist.");

        participationHistDAOImpl.store(coll);
    }
} //end of PatientEncounterHistoryManager