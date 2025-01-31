package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao;

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
import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.*;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.locator.dt.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class NonPersonLivingSubjectHistoryManager {
  static final LogUtils logger = new LogUtils(NonPersonLivingSubjectHistoryManager.class.getName());

  private NonPersonLivingSubjectVO nplsVoHist = null;
  private long nonPersonUid;
  private NonPersonLivingSubjectHistDAOImpl nplsHistDAOImpl;
  private short versionCtrlNbr = -1;
  private EntityIdHistDAOImpl entityIdHistDAOImpl;
  private EntityLocatorParticipationHistDAOImpl entityLocatorParticipationHistDAOImpl;
  private ParticipationHistDAOImpl participationHistDAOImpl;
  private RoleHistDAOImpl roleHistDAOImpl;
  private PhysicalLocatorHistDAOImpl physicalLocatorHistDAO;
  private PostalLocatorHistDAOImpl postalLocatorHistDAO;
  private TeleLocatorHistDAOImpl teleLocatorHistDAO;
  /**
   * To be used only when calling the load(...) method
   */
  public NonPersonLivingSubjectHistoryManager() {
  }

  public NonPersonLivingSubjectHistoryManager(long nonPersonUid, short versionCtrlNbr) throws NEDSSDAOSysException
    {
        this.nonPersonUid = nonPersonUid;
        nplsHistDAOImpl = new NonPersonLivingSubjectHistDAOImpl(nonPersonUid, versionCtrlNbr);
        this.versionCtrlNbr = versionCtrlNbr;
        logger.debug("NonPersonLivingSubjectHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        entityIdHistDAOImpl = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        roleHistDAOImpl = new RoleHistDAOImpl(versionCtrlNbr);
    }//end of constructor

  /**
   * Description:
   * This function takes a old NonPersonLivingSubjectVO and stores them into history tables
   * @Parameters: Object: NonPersonLivingSubjectVO
   * @Return: void
   * @throws NEDSSDAOSysException
   */
   public void store(Object obj) throws NEDSSDAOSysException
    {
        nplsVoHist = (NonPersonLivingSubjectVO)obj;
        if ( nplsVoHist == null) return;
        /**
         * Insert material to history
         */

        if(nplsVoHist.getTheNonPersonLivingSubjectDT() != null )
        {
            insertNonPersonLivingSubject(nplsVoHist.getTheNonPersonLivingSubjectDT());
            nplsVoHist.getTheNonPersonLivingSubjectDT().setItNew(false);
        }

        if(nplsVoHist.getTheEntityIdDTCollection() != null) {
          insertEntityIdDTCollection(nplsVoHist.getTheEntityIdDTCollection());
        }

        if(nplsVoHist.getTheEntityLocatorParticipationDTCollection() != null) {
          insertEntityLocatorParticipationDTCollection(nplsVoHist.getTheEntityLocatorParticipationDTCollection());
        }

        if(nplsVoHist.getTheRoleDTCollection() != null) {
          insertRoleCollection(nplsVoHist.getTheRoleDTCollection());
        }

        if(nplsVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationCollection(nplsVoHist.getTheParticipationDTCollection());
        }

    }//end of store
    /**
     * Loads the NonPersonLivingSubjectVO from history
     * @param nonPersonUid : Long
     * @param versionCtrlNbr : Integer
     * @return NonPersonLivingSubjectVO
     * @throws NEDSSSystemException
     */
    public NonPersonLivingSubjectVO load(Long nonPersonUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a nonPersonLivingSubjectVO for the history...");

        NonPersonLivingSubjectVO nplsvo = new NonPersonLivingSubjectVO();
        nplsHistDAOImpl = new NonPersonLivingSubjectHistDAOImpl();
        entityIdHistDAOImpl = new EntityIdHistDAOImpl();
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        roleHistDAOImpl = new RoleHistDAOImpl();


        /**
        *  Selects NonPersonLivingSubjectDT object
        */

        NonPersonLivingSubjectDT nplsDT = nplsHistDAOImpl.load(nonPersonUid, versionCtrlNbr);
        nplsvo.setTheNonPersonLivingSubjectDT(nplsDT);

//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  roleColl = roleHistDAOImpl.load(...);
        //mtvo.setTheRoleDTCollection(roleColl);
//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  participationColl = participationHistDAOImpl.load(...);
        //mtvo.setTheParticipationDTCollection(participationColl);

        Collection<Object>  idColl = entityIdHistDAOImpl.load(nonPersonUid, versionCtrlNbr);
        nplsvo.setTheEntityIdDTCollection(idColl);

        Collection<Object>  elpColl = entityLocatorParticipationHistDAOImpl.load(nonPersonUid, versionCtrlNbr);
	ArrayList<Object> newElpColl = new ArrayList<Object> ();

	for(Iterator<Object> anIterator = elpColl.iterator(); anIterator.hasNext(); )
	{
	    EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();
		if(elpdt != null)
                {
		    Long locatorUid = elpdt.getLocatorUid();
		    physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl();
		    postalLocatorHistDAO = new PostalLocatorHistDAOImpl();
		    teleLocatorHistDAO = new TeleLocatorHistDAOImpl();
		    Collection<Object>  physicalLocColl = physicalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
		    Collection<Object>  postalLocColl = postalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
		    Collection<Object>  teleLocColl = teleLocatorHistDAO.load(locatorUid, versionCtrlNbr);

		    for(Iterator<Object> it = physicalLocColl.iterator(); it.hasNext(); )
		    {
			PhysicalLocatorDT pldt = (PhysicalLocatorDT)it.next();
			elpdt.setThePhysicalLocatorDT(pldt);
		    }
		    for(Iterator<Object> it = postalLocColl.iterator(); it.hasNext(); )
		    {
			PostalLocatorDT pldt = (PostalLocatorDT)it.next();
			elpdt.setThePostalLocatorDT(pldt);
		    }
		    for(Iterator<Object> it = teleLocColl.iterator(); it.hasNext(); )
		    {
			TeleLocatorDT pldt = (TeleLocatorDT)it.next();
			elpdt.setTheTeleLocatorDT(pldt);
		    }
                }
		newElpColl.add(elpdt);

	}
        nplsvo.setTheEntityLocatorParticipationDTCollection(elpColl);


        logger.info("Done loadObject() for a nonPersonLivingSubjectVo for history - return: " + nplsvo);
        return nplsvo;
    }//end of load
   /**
   * This method inserts the NonPersonLivingSubject history by calling the store method of nplsHistDAOImpl
   * @param  nplsDt   the NonPersonLivingSubjectDT
   * @throws NEDSSDAOSysException
   */
    private void insertNonPersonLivingSubject(NonPersonLivingSubjectDT nplsDt)throws NEDSSDAOSysException {
      if(nplsDt == null)
          throw new NEDSSDAOSysException("Error: insert null nonPersonLivingSubjectDt into nonPersonLivingSubjectHist.");
      nplsHistDAOImpl.store(nplsDt);
    }//end of insertNonPersonLivingSubject

   /**
    * Helps to isert the entityId Collection<Object>  by calling store(Collection<Object> coll) method of
    * entityIdHistDAOImpl
    * @param coll Collection
    * @throws NEDSSDAOSysException
   */
    private void insertEntityIdDTCollection(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null EntityIdDTCollection  into entity id hist.");
      entityIdHistDAOImpl.store(coll);
    }
     /**
     * Helps to insert a collection of entityLocatorParticipation
     * @param coll Collection
     * @throws NEDSSDAOSysException
   */
    private void insertEntityLocatorParticipationDTCollection(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null EntityLocatorParticipationDTCollection  into entity locator participation hist.");
      physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl(versionCtrlNbr);
      postalLocatorHistDAO = new PostalLocatorHistDAOImpl(versionCtrlNbr);
      teleLocatorHistDAO = new TeleLocatorHistDAOImpl(versionCtrlNbr);
      entityLocatorParticipationHistDAOImpl.store(coll);
      for(Iterator<Object> anIterator = coll.iterator(); anIterator.hasNext(); )
        {
                EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();
		if(elpdt != null)
                {
		    PhysicalLocatorDT pldt = elpdt.getThePhysicalLocatorDT();
		    PostalLocatorDT poldt = elpdt.getThePostalLocatorDT();
		    TeleLocatorDT tldt = elpdt.getTheTeleLocatorDT();

		    physicalLocatorHistDAO.store(pldt);
		    postalLocatorHistDAO.store(poldt);
		    teleLocatorHistDAO.store(tldt);
                }

        }
    }

    private void insertRoleCollection(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null RoleDTCollection  into role hist.");
      roleHistDAOImpl.store(coll);
    }
  /**
   * Helps to insert the  Collection<Object>  of ParticipationCollection
   * @param  coll  the Collection
   * @throws NEDSSDAOSysException
   */
    private void insertParticipationCollection(Collection<Object> coll)throws NEDSSDAOSysException {
      if(coll == null)
        throw new NEDSSDAOSysException("Error: insert null ParticipationDTCollection  into pariticipation hist.");
      participationHistDAOImpl.store(coll);
    }

}//end of NonPersonLivingSubjectHistoryManager