package gov.cdc.nedss.act.referral.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActIdHistDAOImpl;
import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.association.dao.ActRelationshipHistDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationHistDAOImpl;
import gov.cdc.nedss.util.LogUtils;

import java.util.Collection;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ReferralHistoryManager {
  static final LogUtils logger = new LogUtils(ReferralHistoryManager.class.getName());
  private long referralUid;
  private ReferralHistDAOImpl referralHistDAO;
  private int versionCtrlNbr = -1;
  private ReferralVO refVoHist;
  private ActIdHistDAOImpl actIdHistDAO;
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
  private ActRelationshipHistDAOImpl actRelationshipHist;
  private ParticipationHistDAOImpl participationHistDAOImpl;

  /**
   * To be used only when calling the load(...) method
   */
  public ReferralHistoryManager() {
  }//end of constructor

  public ReferralHistoryManager(long referralUid, short versionCtrlNbr)throws NEDSSSystemException {
        this.referralUid = referralUid;
        referralHistDAO = new ReferralHistDAOImpl(referralUid, versionCtrlNbr);
        this.versionCtrlNbr = referralHistDAO.getVersionCtrlNbr();
        logger.debug("ReferralHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        actIdHistDAO = new ActIdHistDAOImpl(versionCtrlNbr);
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
  }//end of constructor

  /**
   * This function takes a old ReferralVO and stores them into history tables
   * @param obj -- ReferralVO
   * @throws NEDSSSystemException
   */
    public void store(Object obj) throws  NEDSSSystemException
    {

        refVoHist = (ReferralVO)obj;
        if ( refVoHist == null) return;
        /**
         * Insert referral to history
         */

        if(refVoHist.getTheReferralDT() != null )
        {
            insertReferral(refVoHist.getTheReferralDT());
            refVoHist.getTheReferralDT().setItNew(false);
        }//end of if

        if(refVoHist.getTheActivityIdDTCollection() != null)
          insertActIdDTColl(refVoHist.getTheActivityIdDTCollection());

        if(refVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(refVoHist.getTheActivityLocatorParticipationDTCollection());
        }

        if(refVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(refVoHist.getTheActRelationshipDTCollection());
        }

        if(refVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(refVoHist.getTheParticipationDTCollection());
        }

    }//end of store
    /**
     * Load the referralVO object from database with specified UID and version control Number
	* @param referralUid -- UID for the refferal object to be loaded
	* @param versionCtrlNbr -- Version ctrl number for the refferal object to be loaded
	* @return refvo -- the referralVO object from database with specified UID and version control Number
     */
    public ReferralVO load(Long referralUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException, NEDSSSystemException
    {

        logger.info("Starts loadObject() for a referral vo for the history...");

        ReferralVO refvo = new ReferralVO();
        referralHistDAO = new ReferralHistDAOImpl();
        actIdHistDAO = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();

        /**
        *  Selects ReferralDT object
        */

        ReferralDT refDT = referralHistDAO.load(referralUid, versionCtrlNbr);
        refvo.setTheReferralDT(refDT);

        Collection<Object> actIdHistColl = actIdHistDAO.load(referralUid, versionCtrlNbr);
        refvo.setTheActivityIdDTCollection(actIdHistColl);

        //waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);


        logger.info("Done loadObject() for a referralVo for history - return: " + refvo);
        return refvo;
    }//end of load
     /**
     * insert the new record for Referral in the History Table
	* @param dt -- the Referral DT
	* @throws NEDSSSystemException
     */
    private void insertReferral(ReferralDT dt) throws NEDSSSystemException {

      if(dt == null)
          throw new NEDSSSystemException("Error: insert null referralDt into referralsHist.");
      referralHistDAO.store(dt);
    }//end of insertReferral()
    /**
     * Insert the ActDT collection in the History Table
	* @param coll -- ActDT collection
	* @throws NEDSSSystemException
     */
    private void insertActIdDTColl(Collection<Object> coll) throws NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null actIdDT collection into act id history.");
      actIdHistDAO.store(coll);
    }
     /**
     * Insert the ActivityLocatorParticipationDT Collection<Object>  in the History Table
	* @param coll -- ActivityLocatorParticipationDT Collection
	* @throws NEDSSSystemException
     */
    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");
      activityLocatorParticipationHistDAOImpl.store(coll);
    }
     /**
     * Insert the ActRelationshipDT Collection<Object>  in the History Table
	* @param coll -- ActRelationshipDT Collection
	* @throws NEDSSSystemException
     */
    private void insertActRelationshipDTCollection(Collection<Object> coll)throws  NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");
      actRelationshipHist.store(coll);
    }
    /**
     * Insert the ParticipationDT Collection<Object>  in the History Table
	* @param coll -- ParticipationDT Collection
	* @throws NEDSSSystemException
     */
    private void insertParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into act participation hist.");
      participationHistDAOImpl.store(coll);
    }
}//end of ReferralHistoryManager
