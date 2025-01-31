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

public class ActRelationshipHistoryManager {

  static final LogUtils logger = new LogUtils(ActRelationshipHistoryManager.class.getName());

  //private ActRelationshipVO actRelationshipVoHist = null;
  private ActRelationshipDT oldActRelationshipDT;
  private long targetActUid = -1;
  private long sourceActUid = -1;
  private String typeCd = "";
  private ActRelationshipHistDAOImpl actRelationshipHistDAOImpl;
  private short versionCtrlNbr = -1;

  /**
   * Default Constructor
   */
  public ActRelationshipHistoryManager() {
  }

  /**
   * Initializes class variables.
   * @param targetActUid : long
   * @param sourceActUid : long
   * @param typeCd : String
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ActRelationshipHistoryManager(long targetActUid, long sourceActUid, String typeCd) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.targetActUid = targetActUid;
        this.sourceActUid = sourceActUid;
        this.typeCd = typeCd;
        actRelationshipHistDAOImpl = new ActRelationshipHistDAOImpl(targetActUid, sourceActUid, typeCd);
        this.versionCtrlNbr = actRelationshipHistDAOImpl.getVersionCtrlNbr();
        logger.debug("ActRelationshipHistoryManager--versionCtrlNbr: " + versionCtrlNbr);

    }//end of constructor

   /**
   * Description:
   *    This function takes a old ActRelationshipDT and stores them into history tables
   * @param obj : Object
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        oldActRelationshipDT = (ActRelationshipDT)obj;
        if ( oldActRelationshipDT == null){
           return;
        }
        /**
         * Insert clinical document to history
         */

        //if(cdVoHist.getTheClinicalDocumentDT() != null )
        else
        {
            insertActRelationship(oldActRelationshipDT);
            oldActRelationshipDT.setItNew(false);
        }

    }//end of store

    /**
     * Loads the ActRelationshipDT ojbect based on provided parameters
     * @param targetActUid : Long
     * @param sourceActUid : Long
     * @param typeCd : String
     * @param versionCtrlNbr : Integer
     * @throws NEDSSSystemException
     */
    public ActRelationshipDT load(Long targetActUid, Long sourceActUid, String typeCd, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a clinicalDocumentVo for the history...");

        //ClinicalDocumentVO cdvo = new ClinicalDocumentVO();
        actRelationshipHistDAOImpl = new ActRelationshipHistDAOImpl();

        /**
        *  Selects ActRelationshipDT object
        */

        ActRelationshipDT dt = actRelationshipHistDAOImpl.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheClinicalDocumentDT(cdDT);


        logger.info("Done loadObject() for a ActRelationshipDT for history - return: " + dt);
        return dt;
    }//end of load

    /**
     * Results in the insertion of an ActRelationshipDT record into History
     * @param dt : ActRelationshipDT
     * @return void
     * @throws NEDSSDAOSysException, NEDSSSystemException
     */
    private void insertActRelationship(ActRelationshipDT dt)throws NEDSSDAOSysException, NEDSSSystemException {
      if(dt == null)
          throw new NEDSSSystemException("Error: insert null ActRelationshipDt into act relationship Hist.");
      actRelationshipHistDAOImpl.store(dt);
    }//end of insertClinicalDocument
}//end of ActRelationshipHistoryManager
