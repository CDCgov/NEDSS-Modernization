package gov.cdc.nedss.entity.material.ejb.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.material.dt.*;
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

public class MaterialHistoryManager {
  static final LogUtils logger = new LogUtils(MaterialHistoryManager.class.getName());

  private MaterialVO mtVoHist = null;
  private long materialUid;
  private MaterialHistDAOImpl mtHistDAOImpl;
  private short versionCtrlNbr = -1;
  private ManufacturedMaterialHistDAOImpl manufacturedMaterialHistDAOImpl;
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
  public MaterialHistoryManager() {
  }//end of constructor

  public MaterialHistoryManager(long materialUid, short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.materialUid = materialUid;
        mtHistDAOImpl = new MaterialHistDAOImpl(materialUid, versionCtrlNbr);
        this.versionCtrlNbr = versionCtrlNbr;
        logger.debug("MaterialHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        manufacturedMaterialHistDAOImpl = new ManufacturedMaterialHistDAOImpl(versionCtrlNbr);
        entityIdHistDAOImpl = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        roleHistDAOImpl = new RoleHistDAOImpl(versionCtrlNbr);
    }//end of constructor

  /**
   * Description:
   *    This function takes a old MaterialVO and stores them into history tables
   * Parameters:
   *    Object: MaterialVO
   * Return:
   *    void
   */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        mtVoHist = (MaterialVO)obj;
        if ( mtVoHist == null) return;
        /**
         * Insert material to history
         */

        if(mtVoHist.getTheMaterialDT() != null )
        {
            insertMaterial(mtVoHist.getTheMaterialDT());
            mtVoHist.getTheMaterialDT().setItNew(false);
        }

        /**
        * Insert manufactured material collection
        */

        if (mtVoHist.getTheManufacturedMaterialDTCollection() != null)
        {
            insertManufacturedMaterial(mtVoHist.getTheManufacturedMaterialDTCollection());
        }

        if(mtVoHist.getTheEntityIdDTCollection() != null) {
          insertEntityIdDTCollection(mtVoHist.getTheEntityIdDTCollection());
        }

        if(mtVoHist.getTheEntityLocatorParticipationDTCollection() != null) {
          insertEntityLocatorParticipationDTCollection(mtVoHist.getTheEntityLocatorParticipationDTCollection());
        }

        /*if(mtVoHist.getTheRoleDTCollection() != null) {
          insertRoleCollection(mtVoHist.getTheRoleDTCollection());
        }

        if(mtVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationCollection(mtVoHist.getTheParticipationDTCollection());
        }*/
    }//end of store

    public MaterialVO load(Long materialUid, Integer manufacturedMaterialSeq, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a materialVO for the history...");

        MaterialVO mtvo = new MaterialVO();
        mtHistDAOImpl = new MaterialHistDAOImpl();
        manufacturedMaterialHistDAOImpl = new ManufacturedMaterialHistDAOImpl();
        entityIdHistDAOImpl = new EntityIdHistDAOImpl();
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        roleHistDAOImpl = new RoleHistDAOImpl();

        /**
        *  Selects MaterialDT object
        */

        MaterialDT mtDT = mtHistDAOImpl.load(materialUid, versionCtrlNbr);
        mtvo.setTheMaterialDT(mtDT);

        /**
        * Selects ManufacturedMaterialDT Collection
        */

        Collection<Object>  manufacturedMaterialColl = manufacturedMaterialHistDAOImpl.load(materialUid, manufacturedMaterialSeq, versionCtrlNbr);
        mtvo.setTheManufacturedMaterialDTCollection(manufacturedMaterialColl);

//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  roleColl = roleHistDAOImpl.load(...);
        //mtvo.setTheRoleDTCollection(roleColl);
//Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  participationColl = participationHistDAOImpl.load(...);
        //mtvo.setTheParticipationDTCollection(participationColl);

        Collection<Object>  idColl = entityIdHistDAOImpl.load(materialUid, versionCtrlNbr);
        mtvo.setTheEntityIdDTCollection(idColl);

        Collection<Object>  elpColl = entityLocatorParticipationHistDAOImpl.load(materialUid, versionCtrlNbr);
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
        mtvo.setTheEntityLocatorParticipationDTCollection(elpColl);


        logger.info("Done loadObject() for a materialVo for history - return: " + mtvo);
        return mtvo;
    }//end of load

    private void insertMaterial(MaterialDT mtDt)throws NEDSSDAOSysException, NEDSSSystemException {
      if(mtDt == null)
          throw new NEDSSSystemException("Error: insert null materialDt into materialHist.");
      mtHistDAOImpl.store(mtDt);
    }//end of insertMaterial

    private void insertManufacturedMaterial(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null manufacturedMaterialDt collection into manufacturedMaterialHist.");
      manufacturedMaterialHistDAOImpl.store(coll);
    }

    private void insertEntityIdDTCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null EntityIdDTCollection  into entity id hist.");
      entityIdHistDAOImpl.store(coll);
    }

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

    private void insertRoleCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null RoleDTCollection  into role hist.");
      roleHistDAOImpl.store(coll);
    }

    private void insertParticipationCollection(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into pariticipation hist.");
      participationHistDAOImpl.store(coll);
    }
}//end of MaterialHistoryManager
