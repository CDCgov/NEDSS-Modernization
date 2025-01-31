package gov.cdc.nedss.act.actid.dao;

import java.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.exception.*;

/**
 * Title: ActIdHistoryManager
 * Description: This class stores and loads ActId history object.
 * Company: CSC
 * 
 * @author NEDSS Development Team
 * @version 1.0
 */

public class ActIdHistoryManager {
  /**
   * An instance of the LogUtils class.
   */
  static final LogUtils logger = new LogUtils(ActIdHistoryManager.class.getName());

  //private ActIdVO actIdVoHist = null;
  /**
   * An ActIdDT object representing an old 
   * ActId.
   */
  private ActIdDT oldActIdDT;
  /**
   * A long value for an ActUid.
   */
  private long actUid;
  /**
   * An int value for and ActIdSeq
   */
  private int actIdSeq;
  /**
   * An instance of the ActIdHistDAOImpl.
   */
  private ActIdHistDAOImpl actIdHistDAOImpl;
  /**
   * A short value for an act's version control number
   */
  private short versionCtrlNbr = -1;

  /**
   * Default Constructor
   */
  public ActIdHistoryManager() {
  }

  /**
   * Initializes the following class attributes: actUid, actIdSeq, actIdHistDAOImpl,
   * versionCtrlNbr
   * 
   * @param actUid : long value
   * @param actIdSeq : int value
   * @exception NEDSSSystemException 
   */
  public ActIdHistoryManager(long actUid, int actIdSeq) throws  NEDSSSystemException
    {
        this.actUid = actUid;
        this.actIdSeq = actIdSeq;
        actIdHistDAOImpl = new ActIdHistDAOImpl(actUid, actIdSeq);
        this.versionCtrlNbr = actIdHistDAOImpl.getVersionCtrlNbr();
        logger.debug("ActIdHistoryManager--versionCtrlNbr: " + versionCtrlNbr);

    }//end of constructor

    /**
     * This function takes a old ActIdVO and stores them into history tables
     * 
     * @exception NEDSSSystemException 
     * @param obj Object
     */
    public void store(Object obj) throws  NEDSSSystemException
    {
        //actIdVoHist = (ActIdVO)obj;
        oldActIdDT = (ActIdDT)obj;
        if ( oldActIdDT == null) {
           return;
        /**
         * Insert patient encounter to history
         */

        //if(actIdVoHist.getTheActIdDT() != null )
        }else
        {
            insertActId(oldActIdDT);
            oldActIdDT.setItNew(false);
        }
    }//end of store

    /**
     * Loads a collection of ActIdDT objects from history
     * 
     * @exception NEDSSSystemException 
     * @param actUid : Long
     * @param actIdSeq : Integer
     * @param versionCtrlNbr : Integer
     * @return Collection
     */
    public Collection<Object> load(Long actUid, Integer actIdSeq, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a actIdVo for the history...");

        //ActIdVO actvo = new ActIdVO();
        actIdHistDAOImpl = new ActIdHistDAOImpl();

        /**
        *  Selects ActIdDT object
        */

        Collection<Object> actIdDTColl = actIdHistDAOImpl.load(actUid, versionCtrlNbr);
        //actvo.setTheActIdDT(actIdDT);


        logger.info("Done loadObject() for a actIdDT for history - return: " + actIdDTColl);
        return actIdDTColl;
    }//end of load

    /**
     * Results in the addition of an ActIdDT object to history
     * 
     * @exception NEDSSSystemException 
     * @param actDt : ActIdDT
     */
    private void insertActId(ActIdDT actDt)throws  NEDSSSystemException {
      if(actDt == null)
          throw new NEDSSSystemException("Error: insert null ActIdDt into act id Hist.");
      actIdHistDAOImpl.store(actDt);
    }//end of insertPatientEncounter
}//end of ActIdHistoryManager
