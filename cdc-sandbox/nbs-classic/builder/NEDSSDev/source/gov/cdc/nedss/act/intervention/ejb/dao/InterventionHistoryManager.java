package gov.cdc.nedss.act.intervention.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.intervention.ejb.dao.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.actid.dao.*;
import gov.cdc.nedss.association.dao.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class InterventionHistoryManager {
  static final LogUtils logger = new LogUtils(InterventionHistoryManager.class.getName());
  private long interventionUid;
  private InterventionHistDAOImpl interventionHistDAO;
  private int versionCtrlNbr = -1;
  private SubstanceAdminHistDAOImpl substanceAdminHistDAO;
  private ProceduresHistDAOImpl procedureHistDAO;
  private InterventionVO intVoHist;
  private ActIdHistDAOImpl actIdHistDAO;
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
  private ActRelationshipHistDAOImpl actRelationshipHist;
  private ParticipationHistDAOImpl participationHistDAOImpl;

  /**
   * To be used only when calling the load(...) method
   */
  public InterventionHistoryManager() {
  }//end of constructor

  public InterventionHistoryManager(long interventionUid, short versionCtrlNbr)throws  NEDSSSystemException {
        this.interventionUid = interventionUid;
        interventionHistDAO = new InterventionHistDAOImpl(interventionUid, versionCtrlNbr);
        this.versionCtrlNbr = interventionHistDAO.getVersionCtrlNbr();
        logger.debug("InterventionHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        this.substanceAdminHistDAO = new SubstanceAdminHistDAOImpl(versionCtrlNbr);
        this.procedureHistDAO = new ProceduresHistDAOImpl(versionCtrlNbr);
        this.actIdHistDAO = new ActIdHistDAOImpl(versionCtrlNbr);
        this.participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        this.activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        this.actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
  }//end of constructor

  /**
   * Description:
   *    This function takes a old InterventionVO and stores them into history tables
   * Parameters:
   *    Object: InterventionVO
   * Return:
   *    void
   */
    public void store(Object obj) throws  NEDSSSystemException
    {

        intVoHist = (InterventionVO)obj;
        if ( intVoHist == null) return;
        /**
         * Insert intervention to history
         */

        if(intVoHist.getTheInterventionDT() != null )
        {
            //if(interventionHistDAO == null)
              //interventionHistDAO = new InterventionHistDAOImpl();
            InterventionDT dt = intVoHist.getTheInterventionDT();
            insertIntervention(dt);

            intVoHist.getTheInterventionDT().setItNew(false);
        }

        /**
        * Insert substance admin collection
        */
        if (intVoHist.getTheSubstanceAdministrationDTCollection() != null)
        {
            insertSubstanceAdmin(intVoHist.getTheSubstanceAdministrationDTCollection());
        }

        /**
        * Insert observation reason collection
        */

        if (intVoHist.getTheProcedure1DTCollection() != null)
        {
            insertProcedure1(intVoHist.getTheProcedure1DTCollection());
        }

        /*if(intVoHist.getTheActIdDTCollection() != null)
          insertActIdDTColl(intVoHist.getTheActIdDTCollection());

        if(intVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(intVoHist.getTheActivityLocatorParticipationDTCollection());
        }

        if(intVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(intVoHist.getTheActRelationshipDTCollection());
        }

        if(intVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(intVoHist.getTheParticipationDTCollection());
        }*/

    }//end of store

    public InterventionVO load(Long interventionUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException, NEDSSSystemException
    {

        logger.info("Starts loadObject() for a intervention vo for the history...");

        InterventionVO ivo = new InterventionVO();
	       interventionHistDAO = new InterventionHistDAOImpl();
        substanceAdminHistDAO = new SubstanceAdminHistDAOImpl();
        procedureHistDAO = new ProceduresHistDAOImpl();
        actIdHistDAO = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();

        /**
        *  Selects InterventionDT object
        */

        InterventionDT intDT =interventionHistDAO.load(interventionUid, versionCtrlNbr);
        ivo.setTheInterventionDT(intDT);

        /**
        * Selects SubstanceAdministrationsDT Collection
        */

        Collection<Object> substanceAdminColl = substanceAdminHistDAO.load(interventionUid, versionCtrlNbr);
        ivo.setTheSubstanceAdministrationDTCollection(substanceAdminColl);

        /**
        * Selects Procedure1DT Collection
        */

        Collection<Object> procedure1DTColl = procedureHistDAO.load(interventionUid, versionCtrlNbr);
        ivo.setTheProcedure1DTCollection(procedure1DTColl);

        Collection<Object> actIdDtColl = actIdHistDAO.load(interventionUid, versionCtrlNbr);
        ivo.setTheActIdDTCollection(actIdDtColl);
//waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);

        logger.info("Done loadObject() for a interventionVo for history - return: " + ivo);
        return ivo;
    }//end of load

    private void insertIntervention(InterventionDT dt) throws NEDSSSystemException {
      if(dt == null)
          throw new NEDSSSystemException("Error: insert null interventionDt into interventionHist.");
      interventionHistDAO.store(dt);
    }//end of insertIntervention()

    private void insertSubstanceAdmin(Collection<Object> coll)throws NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null SubstanceAdministrationDT into substance administration history.");

      substanceAdminHistDAO.store(coll);
    }//end of insertSubstanceAdmin()

    private void insertProcedure1(Collection<Object> coll)throws NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null Procedure1DT collection into procedure 1 history.");
      procedureHistDAO.store(coll);
    }//end of insertSubstanceAdmin()

    private void insertActIdDTColl(Collection<Object> coll) throws NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null actIdDT collection into act id history.");
      actIdHistDAO.store(coll);
    }
    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");
      activityLocatorParticipationHistDAOImpl.store(coll);
    }

    private void insertActRelationshipDTCollection(Collection<Object> coll)throws  NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");
      actRelationshipHist.store(coll);
    }

    private void insertParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into act participation hist.");
      participationHistDAOImpl.store(coll);
    }
}//end of class
