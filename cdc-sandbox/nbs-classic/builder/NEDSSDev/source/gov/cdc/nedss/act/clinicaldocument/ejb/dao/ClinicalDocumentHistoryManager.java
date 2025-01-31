package gov.cdc.nedss.act.clinicaldocument.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.clinicaldocument.ejb.dao.*;
import gov.cdc.nedss.act.clinicaldocument.vo.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.act.observation.dt.*;
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

public class ClinicalDocumentHistoryManager {
  static final LogUtils logger = new LogUtils(ClinicalDocumentHistoryManager.class.getName());

  private ClinicalDocumentVO cdVoHist = null;

  private long clinicalDocumentUid;
  private ClinicalDocumentHistDAOImpl cdHistDAOImpl;
  private ActIdHistDAOImpl actIdHist;
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
  private ActRelationshipHistDAOImpl actRelationshipHist;
  private ParticipationHistDAOImpl participationHistDAOImpl;
  private short versionCtrlNbr = -1;

  /**
   * To be used only when calling the load(...) method
   */
  public ClinicalDocumentHistoryManager() {
  }

  public ClinicalDocumentHistoryManager(long clinicalDocumentUid, short versionCtrlNbr) throws NEDSSDAOSysException
    {
        this.clinicalDocumentUid = clinicalDocumentUid;
        cdHistDAOImpl = new ClinicalDocumentHistDAOImpl(clinicalDocumentUid, versionCtrlNbr);
        this.versionCtrlNbr = cdHistDAOImpl.getVersionCtrlNbr();
        logger.debug("ClinicalDocumentHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        this.actIdHist = new ActIdHistDAOImpl(versionCtrlNbr);
        this.activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        this.actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
        this.participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
    }//end of constructor

    /**
   * Description:
   *    This function takes a old ClinicalDocumentVO and stores them into history tables
   * Parameters:
   *    Object: ClinicalDocumentVO
   * Return:
   *    void
   */
    public void store(Object obj) throws NEDSSDAOSysException
    {
        cdVoHist = (ClinicalDocumentVO)obj;
        if ( cdVoHist == null) return;
        /**
         * Insert clinical document to history
         */

        if(cdVoHist.getTheClinicalDocumentDT() != null )
        {
            /*this.versionCtrlNbr = cdVoHist.getTheClinicalDocumentDT().getVersionCtrlNbr().shortValue();
            this.clinicalDocumentUid = cdVoHist.getTheClinicalDocumentDT().getClinicalDocumentUid().longValue();
            this.cdHistDAOImpl = new ClinicalDocumentHistDAOImpl(clinicalDocumentUid, versionCtrlNbr);
            this.actIdHist = new ActIdHistDAOImpl(versionCtrlNbr);
            this.activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
            this.actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
            this.participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);*/

            insertClinicalDocument(cdVoHist.getTheClinicalDocumentDT());
            cdVoHist.getTheClinicalDocumentDT().setItNew(false);
        }

        if(cdVoHist.getTheActivityIdDTCollection() != null ) {
          insertActivityId(cdVoHist.getTheActivityIdDTCollection());
        }

        if(cdVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(cdVoHist.getTheActivityLocatorParticipationDTCollection());
        }

        if(cdVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(cdVoHist.getTheActRelationshipDTCollection());
        }

        if(cdVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(cdVoHist.getTheParticipationDTCollection());
        }
    }//end of store

    public ClinicalDocumentVO load(Long clinicalDocumentUid, Integer versionCtrlNbr, Long actIdUid,
                                     Long targetActUid, Long sourceActUid, String typeCd)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a clinicalDocumentVo for the history...");

        ClinicalDocumentVO cdvo = new ClinicalDocumentVO();
        cdHistDAOImpl = new ClinicalDocumentHistDAOImpl();
        actIdHist = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        /**
        *  Selects ClinicalDocumentDT object
        */

        ClinicalDocumentDT cdDT = cdHistDAOImpl.load(clinicalDocumentUid, versionCtrlNbr);
        cdvo.setTheClinicalDocumentDT(cdDT);

        Collection<Object> actIdDtColl = actIdHist.load(actIdUid, versionCtrlNbr);
        cdvo.setTheActivityIdDTCollection(actIdDtColl);
//waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);

        logger.info("Done loadObject() for a clinicalDocumentVo for history - return: " + cdvo);
        return cdvo;
    }//end of load

    private void insertClinicalDocument(ClinicalDocumentDT cdDt)throws NEDSSDAOSysException {
      if(cdDt == null)
          throw new NEDSSDAOSysException("Error: insert null ClinicalDocumentDt into clinical document Hist.");
      cdHistDAOImpl.store(cdDt);
    }//end of insertClinicalDocument

    private void insertActivityId(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null ActivityIDCollection<Object>  into activity id hist.");
      actIdHist.store(coll);
    }

    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");
      activityLocatorParticipationHistDAOImpl.store(coll);
    }

    private void insertActRelationshipDTCollection(Collection<Object> coll)throws NEDSSDAOSysException{
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");
      actRelationshipHist.store(coll);
    }
    private void insertParticipationDTCollection(Collection<Object> coll)throws NEDSSDAOSysException{
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null ParticipationDTCollection  into act participation hist.");
      participationHistDAOImpl.store(coll);
    }
}//ClinicalDocumentHistoryManager