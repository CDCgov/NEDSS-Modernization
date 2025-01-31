package gov.cdc.nedss.act.publichealthcase.ejb.dao;

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
import gov.cdc.nedss.act.publichealthcase.ejb.dao.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
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

public class PublicHealthCaseHistoryManager {
  static final LogUtils logger = new LogUtils(PublicHealthCaseHistoryManager.class.getName());

  private PublicHealthCaseVO phcVoHist = null;
  private long publicHealthCaseUid;
  private PublicHealthCaseHistDAOImpl phcHistDAOImpl;
  private ConfirmationMethodHistDAOImpl cmHistDAOImpl;
  private CaseManagementHistDAOImpl caseHistDAOImpl;
  
  private int versionCtrlNbr = -1;
  private ActIdHistDAOImpl actIdHistDAO;
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
  private ActRelationshipHistDAOImpl actRelationshipHist;
  private ParticipationHistDAOImpl participationHistDAOImpl;

  /**
   * To be used only when calling the load(...) method
   */
  public PublicHealthCaseHistoryManager() {
  }//end of constructor

  public PublicHealthCaseHistoryManager(long publicHealthCaseUid, short versionCtrlNbr) throws NEDSSDAOSysException
    {
        this.publicHealthCaseUid = publicHealthCaseUid;
        phcHistDAOImpl = new PublicHealthCaseHistDAOImpl(publicHealthCaseUid, versionCtrlNbr);
        this.versionCtrlNbr = phcHistDAOImpl.getVersionCtrlNbr();
        logger.debug("PublicHealthCaseHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        cmHistDAOImpl = new ConfirmationMethodHistDAOImpl(versionCtrlNbr);
        actIdHistDAO = new ActIdHistDAOImpl(versionCtrlNbr);
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        actRelationshipHist = new ActRelationshipHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
    }//end of constructor

  /**
   * Description:
   *    This function takes a old PublicHealthCaseVO and stores them into history tables
   * Parameters:
   *    Object: PublicHealthCaseVO
   * Return:
   *    void
   */
    public void store(Object obj) throws NEDSSDAOSysException
    {
        phcVoHist = (PublicHealthCaseVO)obj;
        if ( phcVoHist == null) return;
        /**
         * Insert observation to history
         */

        if(phcVoHist.getThePublicHealthCaseDT() != null )
        {
            PublicHealthCaseDT dt = phcVoHist.getThePublicHealthCaseDT();
            insertPHC(dt);
            //this.versionCtrlNbr = dt.getVersionCtrlNbr().intValue();
            //logger.debug("PublicHealthCaseHistoryManager--nextSeqNumber: " + versionCtrlNbr);
            //cmHistDAOImpl = new ConfirmationMethodHistDAOImpl(versionCtrlNbr);

            phcVoHist.getThePublicHealthCaseDT().setItNew(false);
        }

        /**
        * Insert confirmation methods collection
        */

        if (phcVoHist.getTheConfirmationMethodDTCollection() != null)
        {
            insertConfirmationMethods(phcVoHist.getTheConfirmationMethodDTCollection());
        }
        if (phcVoHist.getTheCaseManagementDT() != null)
        {
            insertCaseManagementDT(phcVoHist.getTheCaseManagementDT(), phcVoHist);
        }

        if(phcVoHist.getTheActIdDTCollection() != null)
          insertActIdDTColl(phcVoHist.getTheActIdDTCollection());

        if(phcVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(phcVoHist.getTheActivityLocatorParticipationDTCollection());
        }
        /*
        if(phcVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(phcVoHist.getTheActRelationshipDTCollection());
        }

        if(phcVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(phcVoHist.getTheParticipationDTCollection());
        }*/

    }//end of store

    public PublicHealthCaseVO load(Long publicHealthCaseUid, String confirmationMethodCd, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a publichealthcaseVo for the history...");

        PublicHealthCaseVO phcvo = new PublicHealthCaseVO();
        phcHistDAOImpl = new PublicHealthCaseHistDAOImpl();
        cmHistDAOImpl = new ConfirmationMethodHistDAOImpl();
        actIdHistDAO = new ActIdHistDAOImpl();
        activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        actRelationshipHist = new ActRelationshipHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();

        /**
        *  Selects PublicHealthCaseDT object
        */

        PublicHealthCaseDT phcDT = phcHistDAOImpl.load(publicHealthCaseUid, versionCtrlNbr);
        phcvo.setThePublicHealthCaseDT(phcDT);

        /**
        * Selects ConfirmationMethodDT Collection
        */

        Collection<Object> confirmationMethodColl = cmHistDAOImpl.load(publicHealthCaseUid, confirmationMethodCd, versionCtrlNbr);
        phcvo.setTheConfirmationMethodDTCollection(confirmationMethodColl);

        Collection<Object> actIdHistColl = actIdHistDAO.load(publicHealthCaseUid, versionCtrlNbr);
        phcvo.setTheActIdDTCollection(actIdHistColl);

        //waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);


        logger.info("Done loadObject() for a publicHealthCaseVo for history - return: " + phcvo);
        return phcvo;
    }//end of load

    private void insertPHC(PublicHealthCaseDT phcDt)throws NEDSSDAOSysException {
      if(phcDt == null)
          throw new NEDSSDAOSysException("Error: insert null phcDt into public health case Hist.");
      phcHistDAOImpl.store(phcDt);
    }//end of insertPHC

    private void insertConfirmationMethods(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
          throw new NEDSSDAOSysException("Error: insert null confirmation method into confirmation method Hist.");
      cmHistDAOImpl.store(coll);
    }//end of insertPHC
    private void insertCaseManagementDT(CaseManagementDT caseManagementDT, PublicHealthCaseVO publicHealthCaseVO)throws NEDSSDAOSysException {
        if(caseManagementDT!=null 
        		&& (caseManagementDT.getPublicHealthCaseUid() != null 
        			&& (caseManagementDT.isCaseManagementDTPopulated() 
        				|| caseManagementDT.getPublicHealthCaseUid().compareTo(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid())==0))){
        	caseHistDAOImpl =new CaseManagementHistDAOImpl();
            caseHistDAOImpl.insertCaseManagementHistDT(caseManagementDT, publicHealthCaseVO);
        }//insert only case CaseManagementDT is not null!!
      }//end of insertPHC

    private void insertActIdDTColl(Collection<Object> coll) {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null actIdDT collection into act id history.");
      actIdHistDAO.store(coll);
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
    
    
    public PublicHealthCaseDT getPhcHist(Long publicHealthCaseUid,  Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a publichealthcaseVo for the history...");

        PublicHealthCaseVO phcvo = new PublicHealthCaseVO();
        phcHistDAOImpl = new PublicHealthCaseHistDAOImpl();
       
        /**
        *  Selects PublicHealthCaseDT object
        */

        return phcHistDAOImpl.load(publicHealthCaseUid, versionCtrlNbr);
       
    }

}//end of class