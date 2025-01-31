package gov.cdc.nedss.association.dao;

import java.util.*;
import java.sql.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.exception.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ParticipationHistoryManager {

  static final LogUtils logger = new LogUtils(ParticipationHistoryManager.class.getName());

  private ParticipationDT pDtHist = null;

  private long subjectEntityUid;
  private long actUid;
  private String typeCd;
  private ParticipationHistDAOImpl pHistDAOImpl;
  private short versionCtrlNbr = -1;

  /**
   * Defualt Constructor
   */
  public ParticipationHistoryManager() {
  }

  /**
   * Initializes the class variables based on the parameters required by the constructor
   * @param subjectEntityUid : long
   * @param actUid : long
   * @param typeCd : String
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ParticipationHistoryManager(long subjectEntityUid, long actUid, String typeCd) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.subjectEntityUid = subjectEntityUid;
        this.actUid = actUid;
        this.typeCd = typeCd;
        pHistDAOImpl = new ParticipationHistDAOImpl(subjectEntityUid, actUid, typeCd);
        this.versionCtrlNbr = pHistDAOImpl.getVersionCtrlNbr();
        logger.debug("ParticipationsHistoryManager--versionCtrlNbr: " + versionCtrlNbr);

    }//end of constructor

  /**
   * Description:
   *    This function takes a old ParticipationDT and stores them into history tables
   * @param obj : Object
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        pDtHist = (ParticipationDT)obj;
        if ( pDtHist == null) return;
        /**
         * Insert clinical document to history
         */

        if(pDtHist != null )
        {
            insertParticipation(pDtHist);
            pDtHist.setItNew(false);
        }//end of if
    }//end of store

    /**
     * Loads a Participation record from History
     * @param subjectEntityUid : Long
     * @param actId : Long
     * @param typeCd : String
     * @param versionCtrlNbr : Integer
     */
    public ParticipationDT load(Long subjectEntityUid, Long actId, String typeCd, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a participationDT for the history...");

        //EntityGroupVO egVO = new EntityGroupVO();
        pHistDAOImpl = new ParticipationHistDAOImpl();

        /**
        *  Selects ParticipationDT object
        */

        ParticipationDT participationDT = pHistDAOImpl.load(subjectEntityUid, actId, typeCd, versionCtrlNbr);
        //cdvo.setTheClinicalDocumentDT(cdDT);


        logger.info("Done loadObject() for a participationDT for participation shistory - return: " + participationDT);
        return participationDT;
    }//end of load

    /**
     * Results in the addition of a Participation record into history
     * @param partDt : ParticipationDT
     * @return void
     * @throws NEDSSDAOSysException, NEDSSSystemException
     */
    private void insertParticipation(ParticipationDT partDt)throws NEDSSDAOSysException, NEDSSSystemException {
      if(partDt == null)
          throw new NEDSSSystemException("Error: insert null ParticipationDt into Participation Hist.");
      pHistDAOImpl.store(partDt);
    }//end of insertClinicalDocument
}//end of ParticipationHistoryManager
