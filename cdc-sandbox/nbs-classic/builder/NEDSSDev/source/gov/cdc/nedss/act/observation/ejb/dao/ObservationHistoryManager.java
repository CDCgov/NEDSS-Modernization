package gov.cdc.nedss.act.observation.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.ejb.dao.*;
import gov.cdc.nedss.act.observation.vo.*;
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

public class ObservationHistoryManager {
  static final LogUtils logger = new LogUtils(ObservationHistoryManager.class.getName());

  private ObservationVO obsVoHist = null;
  private long observationUid;
  private ObservationHistDAOImpl obsHistDAOImpl = new ObservationHistDAOImpl();
  private short versionCtrlNbr = -1;
  private ObservationInterpHistDAOImpl obsInterpHistDAOImpl= new ObservationInterpHistDAOImpl();
  private ObservationReasonHistDAOImpl obsReasonHistDAOImpl= new ObservationReasonHistDAOImpl();
  private ObservationValueNumericHistDAOImpl obsValueNumericHistDAOImpl= new ObservationValueNumericHistDAOImpl();
  private ObsValueCodedHistDAOImpl obsValueCodedHistDAOImpl= new ObsValueCodedHistDAOImpl();
  private ObsValueCodedModHistDAOImpl obsValueCodedModHistDAOImpl= new ObsValueCodedModHistDAOImpl();
  private ObsValueDateHistDAOImpl obsValueDateHistDAOImpl= new ObsValueDateHistDAOImpl();
  private ObsValueTxtHistDAOImpl obsValueTxtHistDAOImpl= new ObsValueTxtHistDAOImpl();
  private ActIdHistDAOImpl actIdHistDAO= new ActIdHistDAOImpl();
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl= new ActivityLocatorParticipationHistDAOImpl();
  private ActRelationshipHistDAOImpl actRelationshipHist= new ActRelationshipHistDAOImpl();
  private ParticipationHistDAOImpl participationHistDAOImpl= new ParticipationHistDAOImpl();

  /**
   * Default Constructor
   */
  public ObservationHistoryManager() {
   //To be used only when calling the load(...) method
  }//end of constructor

  /**
   * Initializes the following class attributes: observationUid, obsHistDAOImpl, versionCtrlNbr,
   * obsInterpHistDAOImpl, obsReasonHistDAOImpl, obsValueNumericHistDAOImpl, obsValueCodedHistDAOImpl,
   * obsValueCodedModHistDAOImpl, obsValueDateHistDAOImpl, obsValueTxtHistDAOImpl, actIdHistDAO,
   * activityLocatorParticipationHistDAOImpl, actRelationshipHist, participationHistDAOImpl.
   *
   * @param observationUid : long
   * @param versionCtrlNbr : shorts
   */
  public ObservationHistoryManager(long observationUid, short versionCtrlNbr)
    {
        this.observationUid = observationUid;
        obsHistDAOImpl = new ObservationHistDAOImpl(observationUid, versionCtrlNbr);
        this.versionCtrlNbr = obsHistDAOImpl.getVersionCtrlNbr();
        logger.debug("ObservationHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        obsInterpHistDAOImpl = new ObservationInterpHistDAOImpl(versionCtrlNbr);
        obsReasonHistDAOImpl = new ObservationReasonHistDAOImpl(versionCtrlNbr);
        obsValueNumericHistDAOImpl = new ObservationValueNumericHistDAOImpl(versionCtrlNbr);
        obsValueCodedHistDAOImpl = new ObsValueCodedHistDAOImpl(versionCtrlNbr);
        obsValueCodedModHistDAOImpl = new ObsValueCodedModHistDAOImpl(versionCtrlNbr);
        obsValueDateHistDAOImpl = new ObsValueDateHistDAOImpl(versionCtrlNbr);
        obsValueTxtHistDAOImpl = new ObsValueTxtHistDAOImpl(versionCtrlNbr);
        actIdHistDAO = new ActIdHistDAOImpl(versionCtrlNbr);
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
    }//end of constructor


  /**
   * Description:
   *    This function takes a old ObservationVO and stores them into history tables
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
    public void store(Object obj) throws  NEDSSSystemException
    {
        obsVoHist = (ObservationVO)obj;
        if ( obsVoHist == null) return;
        /**
         * Insert observation to history
         */

        if(obsVoHist.getTheObservationDT() != null )
        {
            insertObservation(obsVoHist.getTheObservationDT());
            obsVoHist.getTheObservationDT().setItNew(false);
        }

        /**
        * Insert observation interp collection
        */

        if (obsVoHist.getTheObservationInterpDTCollection() != null)
        {
            insertObservationInterps(obsVoHist.getTheObservationInterpDTCollection());
        }

        /**
        * Insert observation reason collection
        */

        if (obsVoHist.getTheObservationReasonDTCollection() != null)
        {
            insertObsReasons(obsVoHist.getTheObservationReasonDTCollection());
        }

        /**
        * Insert observationValueNumericDT collection
        */
        if (obsVoHist.getTheObsValueNumericDTCollection() != null)
        {
            insertObsValueNumericDts(obsVoHist.getTheObsValueNumericDTCollection());
        }

        /**
         * Insert observation value coded dt collection
         */
        if(obsVoHist.getTheObsValueCodedDTCollection() != null)
        {
            insertValueCodedDts(obsVoHist.getTheObsValueCodedDTCollection());
        }

        /**
        * Insert observation coded mod dt collection
        */
        if(obsVoHist.getTheObsValueCodedModDTCollection() != null)
        {
            insertValueCodedModDts(obsVoHist.getTheObsValueCodedModDTCollection());
        }

        if(obsVoHist.getTheObsValueDateDTCollection() != null) {
          insertValueDateDts(obsVoHist.getTheObsValueDateDTCollection());
        }//end of if

        if(obsVoHist.getTheObsValueTxtDTCollection() != null) {
          insertObsValueTxtDts(obsVoHist.getTheObsValueTxtDTCollection());
        }//end of if

        if(obsVoHist.getTheActIdDTCollection() != null)
          insertActIdDTColl(obsVoHist.getTheActIdDTCollection());

        /*if(obsVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(obsVoHist.getTheActivityLocatorParticipationDTCollection());
        }*/

        if(obsVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(obsVoHist.getTheActRelationshipDTCollection());
        }

        /*if(obsVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(obsVoHist.getTheParticipationDTCollection());
        }*/
    }//end of store


    /**
     * Loads a complete ObservationVO from history
     * @param observationUid : Long
     * @param obsInterpCd : String
     * @param obsReasonCd : String
     * @param obsValueNumericSeq : Integer
     * @param code : String
     * @param codeModeCd : String
     * @param obsValueDateSeq : Integer
     * @param obsValueTxtSeq : Integer
     * @param versionCtrlNbr : Integer
     * @return ObservationVO
     * @throws NEDSSSystemException
     */
    public ObservationVO load(Long observationUid, String obsInterpCd, String obsReasonCd,
        Integer obsValueNumericSeq, String code, String codeModeCd, Integer obsValueDateSeq,
        Integer obsValueTxtSeq, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a observationvo for the history...");

        ObservationVO ovo = new ObservationVO();
	       obsHistDAOImpl = new ObservationHistDAOImpl();
        obsInterpHistDAOImpl = new ObservationInterpHistDAOImpl();
        obsReasonHistDAOImpl = new ObservationReasonHistDAOImpl();
        obsValueNumericHistDAOImpl = new ObservationValueNumericHistDAOImpl();
        obsValueCodedHistDAOImpl = new ObsValueCodedHistDAOImpl();
        obsValueCodedModHistDAOImpl = new ObsValueCodedModHistDAOImpl();
        obsValueDateHistDAOImpl = new ObsValueDateHistDAOImpl();
        obsValueTxtHistDAOImpl = new ObsValueTxtHistDAOImpl();
        actIdHistDAO = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        /**
        *  Selects ObservationDT object
        */

        ObservationDT oDT =obsHistDAOImpl.load(observationUid, versionCtrlNbr);
        ovo.setTheObservationDT(oDT);

        /**
        * Selects ObservationInterpDT Collection
        */

        Collection<Object> interpColl = obsInterpHistDAOImpl.load(observationUid, obsInterpCd, versionCtrlNbr);
        ovo.setTheObservationInterpDTCollection(interpColl);

        /**
        * Selects ObservationReasonDT Collection
        */

        Collection<Object> obsReasonColl = obsReasonHistDAOImpl.load(observationUid, obsReasonCd, versionCtrlNbr);
        ovo.setTheObservationReasonDTCollection(obsReasonColl);

        /**
        * Selects ObsValueNumericDT collection
        */

        Collection<Object> obsValueNumericColl = obsValueNumericHistDAOImpl.load(observationUid, obsValueNumericSeq, versionCtrlNbr);
        ovo.setTheObsValueNumericDTCollection(obsValueNumericColl);

        /**
        * Selects ObservationValueCodedDT collection
        */

        Collection<Object> obsValueCodedColl = obsValueCodedHistDAOImpl.load(observationUid, code, versionCtrlNbr);
        ovo.setTheObsValueCodedDTCollection(obsValueCodedColl);

        /**
        * Selects ObservationValueCodedDT collection
        */

        Collection<Object> obsValueCodedModColl = obsValueCodedModHistDAOImpl.load(observationUid, code, codeModeCd, versionCtrlNbr);
        ovo.setTheObsValueCodedModDTCollection(obsValueCodedModColl);

        /**
        * Selects ObservationValueDateDT collection
        */

        Collection<Object> obsValueDateColl = obsValueDateHistDAOImpl.load(observationUid, obsValueDateSeq, versionCtrlNbr);
        ovo.setTheObsValueDateDTCollection(obsValueDateColl);

        /**
         * Selects ObservationValueTxtDT collection
         */
        Collection<Object> obsValueTxtColl = obsValueTxtHistDAOImpl.load(observationUid, obsValueTxtSeq, versionCtrlNbr);
        ovo.setTheObsValueTxtDTCollection(obsValueTxtColl);

        Collection<Object> actIdHistColl = actIdHistDAO.load(observationUid, versionCtrlNbr);
        ovo.setTheActIdDTCollection(actIdHistColl);

        //waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);

        logger.info("Done loadObject() for a observationVo for history - return: " + ovo);
        return ovo;
    }//end of load

    /////////////////////////////////private class methods/////////////////////
    /**
     * Results in the addition of 0.* obsValueTxtDT records to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertObsValueTxtDts(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null obsValueTxtDt collection into obsValueTxtHist.");
      obsValueTxtHistDAOImpl.store(coll);
    }

    /**
     * Results in the addtion of an ObservationDT
     * @param observationDt : ObservationDT
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertObservation(ObservationDT observationDt)throws  NEDSSSystemException {
      if(observationDt == null)
          throw new NEDSSSystemException("Error: insert null observationDt into observationHist.");
          obsHistDAOImpl.store(observationDt);
    }//end of insertObseration

    /**
     * Results in the addtion of 0.* observationIterpDT records to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertObservationInterps(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationInterpDt collection into observationHist.");
        //This was changed to fix civil00017076 rel2.0.1
      //ObservationInterpHistDAOImpl obsInterpHistDAOImpl = new ObservationInterpHistDAOImpl();
      obsInterpHistDAOImpl.store(coll);
    }//end of insertObservationInterps()

    /**
     * Results in the addition of 0.* obsReasonDt objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertObsReasons(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationReasonDt collection into observationReasonHist.");
       //This was changed to fix civil00017076 rel2.0.1
      //ObservationReasonHistDAOImpl obsReasonHistDAOImpl= new ObservationReasonHistDAOImpl();
      obsReasonHistDAOImpl.store(coll);
    }// end of insertObsReasons

    /**
     * Results in the addition of 0.* ObsValueNumericDt objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertObsValueNumericDts(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationValueNumericDt collection into observationValueNumericHist.");
      obsValueNumericHistDAOImpl.store(coll);
    }//end of insertObsValueNumericsDts

    /**
     * Results in the addition of 0.* ValueCodedDt objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertValueCodedDts(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationValueCodedDt collection into observationValueCodedHist.");
       //This was changed to fix civil00017076 rel2.0.1
      //ObsValueCodedHistDAOImpl obsValueCodedHistDAOImpl= new ObsValueCodedHistDAOImpl();
      obsValueCodedHistDAOImpl.store(coll);
    }//end of insertValueCodedDts()

    /**
     * Results in the addition of 0.* ValueCodedModDt objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertValueCodedModDts(Collection<Object> coll)throws  NEDSSSystemException {

      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationValueCodedModDt collection into observationValueCodedModHist.");
      obsValueCodedModHistDAOImpl.store(coll);
    }

    /**
     * Results in the addition of 0.* ValueDateDt objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertValueDateDts(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null observationValueDateDt collection into observationValueDateHist.");
      obsValueDateHistDAOImpl.store(coll);
    }

    /**
     * Results in the addition of 0.* ActIdDT objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertActIdDTColl(Collection<Object> coll) throws NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null actIdDT collection into act id history.");
      actIdHistDAO.store(coll);
    }

    /**
     * Results in the addition of 0.* ActivityLocatorParticipationDT objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");
      activityLocatorParticipationHistDAOImpl.store(coll);
    }

    /**
     * Results in the addition of 0.* ActRelationshipDT objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertActRelationshipDTCollection(Collection<Object> coll)throws  NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");
      actRelationshipHist.store(coll);
    }

    /**
     * Results in the addition of 0.* ParticipationDT objects to history
     * @param coll : Collection
     * @return void
     * @throws NEDSSSystemException
     */
    private void insertParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into act participation hist.");
      participationHistDAOImpl.store(coll);
    }
}//end of class
