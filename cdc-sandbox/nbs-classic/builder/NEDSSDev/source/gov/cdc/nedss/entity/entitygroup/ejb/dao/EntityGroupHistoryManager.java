package gov.cdc.nedss.entity.entitygroup.ejb.dao;

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
import gov.cdc.nedss.entity.entitygroup.vo.*;
import gov.cdc.nedss.entity.entitygroup.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.*;

/**
 * Title:       EntityGroupHistoryManager
 * Description: This is the implementation of NEDSSDAOInterface for inserting
 *               EntityGroup value object into EntityGroup related history table.
 * Copyright:   Copyright (c) 2002
 * Company:     CSC
 * @author      Nedss Development Team
 * @version     1.0
*/


public class EntityGroupHistoryManager {
  static final LogUtils logger = new LogUtils(EntityGroupHistoryManager.class.getName());
  private EntityGroupVO egVOHist = null;
  private long entityGroupUid;
  private EntityGroupHistDAOImpl egHistDAOImpl;
  private short versionCtrlNbr = -1;
  private EntityIdHistDAOImpl entityIdHistDAOImpl;
  private EntityLocatorParticipationHistDAOImpl entityLocatorParticipationHistDAOImpl;
  private ParticipationHistDAOImpl participationHistDAOImpl;
  private RoleHistDAOImpl roleHistDAOImpl;
  private PhysicalLocatorHistDAOImpl physicalLocatorHistDAO;
  private PostalLocatorHistDAOImpl postalLocatorHistDAO;
  private TeleLocatorHistDAOImpl teleLocatorHistDAO;
  /**
   *Description: Constructor for EntityGroupHistoryManager object.

   */
  public EntityGroupHistoryManager() {
  }

  /**
   * Description: Constructor for EntityGroupHistoryManager object for a given entityGroupUid
   * and versionCtrlNbr
   * @param entityGroupUid : long value
   * @param versionCtrlNbr : short value
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public EntityGroupHistoryManager(long entityGroupUid, short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.entityGroupUid = entityGroupUid;
        egHistDAOImpl = new EntityGroupHistDAOImpl(entityGroupUid, versionCtrlNbr);
        this.versionCtrlNbr = egHistDAOImpl.getVersionCtrlNbr();
        logger.debug("EntityGroupHistoryManager--nextSeqNumber: " + versionCtrlNbr);
        entityIdHistDAOImpl = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        roleHistDAOImpl = new RoleHistDAOImpl(versionCtrlNbr);
    }//end of constructor

  /**
   * Description: This function takes a old EnityGroupVO and stores them into history tables
   * @param obj  Object
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException


   */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        egVOHist = (EntityGroupVO)obj;
        if ( egVOHist == null) return;
        /**
         * Insert clinical document to history
         */

        if(egVOHist != null )
        {
            insertEntityGroup(egVOHist.getTheEntityGroupDT());
            egVOHist.getTheEntityGroupDT().setItNew(false);
        }//end of if

        if(egVOHist.getTheEntityIdDTCollection() != null) {
          insertEntityIdDTCollection(egVOHist.getTheEntityIdDTCollection());
        }

        if(egVOHist.getTheEntityLocatorParticipationDTCollection() != null) {
          insertEntityLocatorParticipationDTCollection(egVOHist.getTheEntityLocatorParticipationDTCollection());
        }

        if(egVOHist.getTheRoleDTCollection() != null) {
          insertRoleCollection(egVOHist.getTheRoleDTCollection());
        }

        if(egVOHist.getTheParticipationDTCollection() != null) {
          insertParticipationCollection(egVOHist.getTheParticipationDTCollection());
        }
    }//end of store

    /**
     * Description: loads a EntityGroupVO object for a given entityGroupUid and versionCtrlNbr.
     * @param entityGroupUid : Long value
     * @param versionCtrlNbr : Integer value
     * @return EntityGroupVO
     * @throws NEDSSSystemException
     */
    public EntityGroupVO load(Long entityGroupUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a entityGroupVO for the history...");

        EntityGroupVO egVO = new EntityGroupVO();
        egHistDAOImpl = new EntityGroupHistDAOImpl();
        entityIdHistDAOImpl = new EntityIdHistDAOImpl();
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        roleHistDAOImpl = new RoleHistDAOImpl();
        /**
        *  Selects ParticipationDT object
        */

        EntityGroupDT egDT = egHistDAOImpl.load(entityGroupUid,versionCtrlNbr);
        egVO.setTheEntityGroupDT(egDT);
//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  roleColl = roleHistDAOImpl.load(...);
        //egVO.setTheRoleDTCollection(roleColl);
//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  participationColl = participationHistDAOImpl.load(...);
        //egVO.setTheParticipationDTCollection(participationColl);

        Collection<Object>  idColl = entityIdHistDAOImpl.load(entityGroupUid, versionCtrlNbr);
        egVO.setTheEntityIdDTCollection(idColl);

        Collection<Object>  elpColl = entityLocatorParticipationHistDAOImpl.load(entityGroupUid, versionCtrlNbr);
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
        egVO.setTheEntityLocatorParticipationDTCollection(elpColl);

        logger.info("Done loadObject() for a EntityGroupVO for entity group history - return: " + egVO);
        return egVO;
    }//end of load

    /**
     * Description: Inserts a  EntityGroupDT object in the database.
     * @param egDt : EntityGroupDT
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertEntityGroup(EntityGroupDT egDt)throws NEDSSDAOSysException, NEDSSSystemException {
      if(egDt == null)
          throw new NEDSSSystemException("Error: insert null EntityGroupsDt into entity id Hist.");
      egHistDAOImpl.store(egDt);
    }//end of insertEntityGroup

    /**
     * Description: Inserts a  EntityGroupDT object in the database.
     * @param coll : Collection<Object>  object that needs to be stored in database.
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertEntityIdDTCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null EntityIdDTCollection  into entity id hist.");
      entityIdHistDAOImpl.store(coll);
    }

    /**
     * Description: Inserts ther EntityLocatorParticipationDTCollection  in the
     * History tables using store method of  PhysicalLocatorHistDAO, PostalLocatorHistDAO and TeleLocatorHistDAO
     * @param coll : Collection<Object>  object that needs to be stored in the history tables
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertEntityLocatorParticipationDTCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null EntityLocatorParticipationDTCollection  into entity locator participation hist.");
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

    /**
     * Description: Inserts ther RoleHistory using roleHistDAOImpl method.
     * @param coll : collection of roles to be stored in history table
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertRoleCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null RoleDTCollection  into role hist.");
      roleHistDAOImpl.store(coll);
    }

    /**
     * Description: Inserts ther participationHistory using participationHistDAOImpl method.
     * @param coll : collection of participations to be stored in history table
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertParticipationCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into pariticipation hist.");
      participationHistDAOImpl.store(coll);
    }
}//end of EntityGroupHistoryManager