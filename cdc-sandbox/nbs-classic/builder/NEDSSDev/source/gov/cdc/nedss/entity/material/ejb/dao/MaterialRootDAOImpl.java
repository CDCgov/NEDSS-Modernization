/**
 * Name:	MaterialRootDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for the
 *               Material value object in the Material entity bean.
 *               This class encapsulates all the JDBC calls made by the MaterialEJB
 *               for a Material object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of MaterialEJB is
 *               implemented here.
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/10/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     11/19/2001 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.entity.material.ejb.dao;

// java.* imports
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.EntityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
// javax.* imports

public class MaterialRootDAOImpl extends BMPBase
{
    /**
     * Enables Debugging when is set to true.
     */
  /*  static {
      DEBUG_MODE = false;
//      DEBUG_MODE = true;
    } */

    /**
     * For logging
     */
     static final LogUtils logger = new LogUtils(MaterialRootDAOImpl.class.getName());

    /**
     * Current Matrial Value Object
     */
    private MaterialVO mvo = null;

    /**
     * Current Material UID
     */
    private long materialUid;

    /**
     * Current Material DAO
     */
    private MaterialDAOImpl materialDAO = null;

    /**
     * Manufactured material DAO
     */
    private ManufacturedMaterialDAOImpl manufacturedMatDAO = null;

    /**
     * Current Entity DAO
     */
    private  EntityIdDAOImpl entityIdDAO = null;

    /**
     * Current Entity Locator Participation DAO
     */
    private  EntityLocatorParticipationDAOImpl entityLocatorParticipationDAO = null;
    //role and participation DAOs for place
    private  RoleDAOImpl roleDAOImpl = null;
    private  ParticipationDAOImpl materialParticipationDAOImpl = null;
    /**
     * Constructor
     * @return    An Instance of MaterialRootDAOImpl
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAODupKeyException NEDSS Material Duplicate Key Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     */
    public MaterialRootDAOImpl()

    {
//      DEBUG_MODE = true;
//      logger.debug("\nMaterialRootDAOImp Constructor"+DEBUG_MODE);

    }

    /**
     * Creates(Inserts) a Material Value Object in the database
     * @param obj Material Object
     *
     * @return    Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAODupKeyException NEDSS Material Duplicate Key Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public long create(Object obj)
      throws
        NEDSSSystemException
    {
    	try{
	        this.mvo = (MaterialVO)obj;
	
	        /*
	         *  Inserts MaterialDT object
	         */
	
	        if (this.mvo != null)
	        {
	            materialUid = insertMaterial(this.mvo);
	            logger.debug("MaterialRootDAOImpl - Material UID = " + materialUid); // debugging
	            (this.mvo.getTheMaterialDT()).setMaterialUid(new Long(materialUid));
	        }
	
	        /*
	         * Inserts EntityIdDT collection
	         */
	
	        if (this.mvo != null && this.mvo.getTheEntityIdDTCollection() != null){
	          logger.debug("MaterialRootDAOImpl - this.mvo.getTheEntityIdDTCollection() = null"); // debugging
	          insertEntityIDs(this.mvo);
	       }
	
	        /*
	         * Inserts EntityLocatorParticipationDT collection
	         */
	
	        if (this.mvo != null && this.mvo.getTheEntityLocatorParticipationDTCollection() != null)
	        {
	            logger.debug("MaterialRootDAOImpl - this.mvo.getTheEntityLocatorParticipationDTCollection() != null"); // debugging
	            insertEntityLocatorParticipations(this.mvo);
	        }
	
	        //Inserts ManufacturedMaterialDT collection
	        if (this.mvo != null && this.mvo.getTheManufacturedMaterialDTCollection() != null)
	        {
	            logger.debug("MaterialRootDAOImpl - materialUID = :" +mvo.getTheMaterialDT().getMaterialUid().longValue() ); // debugging
	            insertManufacturedMaterials(mvo.getTheMaterialDT().getMaterialUid().longValue(), this.mvo);
	        }
	
	
	        this.mvo.setItNew(false);
	        this.mvo.setItDirty(false);
	        return ((((MaterialVO)obj).getTheMaterialDT().getMaterialUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Stores(Updates) a Material Value Object in the database
     * @param obj Material Object
     *
     * @exception NEDSSMaterialDAOAppException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOUpdateException NEDSS Material Update Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public void store(Object obj)
      throws
        NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        logger.debug("In MaterialRootDAOImpl Store"); // debugging
	        logger.debug("In MaterialRootDAOImpl Store - obj = " + obj); // debugging
	        this.mvo = (MaterialVO)obj;
	        logger.debug("In MaterialRootDAOImpl Store - mvo = " + mvo.toString()); // debugging
	
	        /*
	         *  Updates MaterialDT object
	         */
	        if(this.mvo.getTheMaterialDT() != null && this.mvo.getTheMaterialDT().isItNew())
	        {
	            logger.debug("In MaterialRootDAOImpl Store - this.mvo.getTheMaterialDT() != null & isItNew"); // debugging
	            insertMaterial(this.mvo);
	            this.mvo.getTheMaterialDT().setItNew(false);
	            this.mvo.getTheMaterialDT().setItDirty(false);
	        }
	        else if(this.mvo.getTheMaterialDT() != null && this.mvo.getTheMaterialDT().isItDirty())
	        {
	            logger.debug("In MaterialRootDAOImpl Store - this.mvo.getTheMaterialDT() != null & isItDirty()"); // debugging
	            updateMaterial(this.mvo);
	            this.mvo.getTheMaterialDT().setItDirty(false);
	            this.mvo.getTheMaterialDT().setItNew(false);
	        }
	
	        /*
	         * Updates entity ids collection
	         */
	        if(this.mvo.getTheEntityIdDTCollection() != null)
	        {
	            logger.debug("In MaterialRootDAOImpl Store - this.mvo.getTheEntityIdDTCollection() != null"); // debugging
	            updateEntityIDs(this.mvo);
	            logger.debug("In MaterialRootDAOImpl completed updateEntityIDs"); // debugging
	        }
	
	        /*
	         * Updates entity locator participations collection
	         */
	        if (this.mvo.getTheEntityLocatorParticipationDTCollection() != null)
	        {
	            logger.debug("In MaterialRootDAOImpl Store - this.mvo.getTheEntityLocatorParticipationDTCollection() != null"); // debugging
	            updateEntityLocatorParticipations(this.mvo);
	            logger.debug("In MaterialRootDAOImpl completed updateEntityLocatorParticipations"); // debugging
	        }
	
	        /*
	         * Updates manufactured material collection
	         */
	        if (this.mvo.getTheManufacturedMaterialDTCollection() != null)
	        {
	            logger.debug("In MaterialRootDAOImpl Store - this.mvo.getTheManufacturedMaterialDTCollection() != null"); // debugging
	            updateManufacturedMaterials(this.mvo);
	            logger.debug("In MaterialRootDAOImpl completed updateManufacturedMaterials"); // debugging
	        }
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Removes(Deletes) a Material Value Object in the database
     * @param materialUid Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public void remove(long materialUid)
      throws NEDSSSystemException
    {
    	try{
	        /*
	         * Removes EntityLocatorParticipationDT collection
	         */
	
	          removeEntityLocatorParticipations(materialUid);
	
	        /*
	         * Removes EntityIdDT Collection
	         */
	
	          removeEntityIDs(materialUid);
	
	        /*
	         *  Removes MaterialDT
	         */
	
	        removeMaterial(materialUid);
    	}catch(Exception ex){
    		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Loads(Selects) a Material Value Object entry from the database
     * @param materialUid Material UID
     *
     * @return    Material Object
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOFinderException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public Object loadObject(long materialUid)
      throws NEDSSSystemException
    {
    	try{
	        this.mvo = new MaterialVO();
	
	        /*
	         *  Selects MaterialDT object
	         */
	
	        MaterialDT pDT = selectMaterial(materialUid);
	        this.mvo.setTheMaterialDT(pDT);
	
	        /*
	         * Selects EntityIdDT collection
	         */
	
	        Collection<Object>  idColl = selectEntityIDs(materialUid);
	        this.mvo.setTheEntityIdDTCollection(idColl);
	
	        /*
	         * Selects EntityLocatorParticipationDT collection
	         */
	
	        Collection<Object>  elpColl = selectEntityLocatorParticipations(materialUid);
	        this.mvo.setTheEntityLocatorParticipationDTCollection(elpColl);
	
	        //Selects RoleDTcollection
	        Collection<Object>  roleColl = selectRoleDTCollection(materialUid);
	        this.mvo.setTheRoleDTCollection(roleColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object>  parColl = selectParticipationDTCollection(materialUid);
	        this.mvo.setTheParticipationDTCollection(parColl);
	
	        //SelectsManufacturedMaterialDTCollection
	        Collection<Object>  manufacturedMatColl = selectManufacturedMaterials(materialUid);
	        this.mvo.setTheManufacturedMaterialDTCollection(manufacturedMatColl);
	
	        this.mvo.setItNew(false);
	        this.mvo.setItDirty(false);
	        return this.mvo;
    	}catch(Exception ex){
    		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Finds a Metrial Value Object in the database using its materialUid as the primary key
     * @param materialUid Material UID
     *
     * @return    Material UID
     *
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     * @exception NEDSSMaterialDAOFinderException NEDSS Material Data Access Object Applicaton Exception
     * @exception NEDSSMaterialDAOSysException NEDSS Material System Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    public Long findByPrimaryKey(long materialUid)
      throws NEDSSSystemException
    {
    	try{
	        /*
	         * Finds material object
	         */
	        logger.debug("MaterialRootDAOImpl - Starting to find by primarykey!"); // debugging
	        Long materialPK = findMaterial(materialUid);
	        logger.debug("MaterialRootDAOImpl - Done find by primarykey!"); // debugging
	        return materialPK;
    	}catch(Exception ex){
    		logger.fatal("materialUid: "+materialUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Inserts Material Data Table of Material Value Object into database
     * @param mvo Material Material Value Object
     *
     * @return    Material UID
     *
     * @exception EJBException Any general EJB Exception
     * @exception NEDSSSystemException NEDSS Applicaton Exception
     */
    private long insertMaterial(MaterialVO mvo)
      throws EJBException, NEDSSSystemException
    {
        try {
            if(materialDAO == null) {
                //materialDAO = new MaterialDAOImpl();
                materialDAO = (MaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_DAO_CLASS);
            }
            logger.debug("MaterialRootDAOImpl - Material DT = " + mvo.getTheMaterialDT()); // debugging
            materialUid = materialDAO.create(mvo.getTheMaterialDT());
            logger.debug("MaterialRootDAOImpl - Material root uid = " + materialUid); // debugging
            mvo.getTheMaterialDT().setMaterialUid(new Long(materialUid));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails insertMaterial()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());

        }
        return materialUid;
    }

    private void insertManufacturedMaterials(long materialUID, MaterialVO mvo)
      throws EJBException, NEDSSSystemException
    {
    	logger.debug("materialUID in insertManufacturedMaterials is :" + materialUID);
        try {
            if(manufacturedMatDAO == null)
            {
                manufacturedMatDAO = (ManufacturedMaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MANUFACTURED_MATERIAL_DAO_CLASS);
            }
						materialUid = manufacturedMatDAO.create(materialUID, mvo.getTheManufacturedMaterialDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUID: "+materialUID+" Fails insertManufacturedMaterials()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());

        }
    }

    /**
     * Inserts Material UID of Material Value Object into database
     * @param mvo Material Material Value Object
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void insertEntityIDs(MaterialVO mvo)
      throws NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            materialUid = entityIdDAO.create((mvo.getTheMaterialDT().getMaterialUid()).longValue(), mvo.getTheEntityIdDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails inserEntityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails insertEntityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Inserts Entity Locator Participation of Material Value Object into database
     * @param mvo Material Material Value Object
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void insertEntityLocatorParticipations(MaterialVO mvo)
      throws NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            materialUid = entityLocatorParticipationDAO.create((mvo.getTheMaterialDT().getMaterialUid()).longValue(), mvo.getTheEntityLocatorParticipationDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails insertEntityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails insertEntityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Selects Material Data Table of Material Value Object from database
     * @param materialUid Material UID
     *
     * @return Material Data Table
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private MaterialDT selectMaterial(long materialUid)
      throws NEDSSSystemException
    {
        try {
            if(materialDAO == null) {
                materialDAO = (MaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_DAO_CLASS);
            }
            return ((MaterialDT)materialDAO.loadObject(materialUid));
        }

        catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUid: "+materialUid+" Fails selectMaterial()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("materialUid: "+materialUid+" Fails selectMaterial()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Collection<Object>  selectManufacturedMaterials(long materialUid)
      throws NEDSSSystemException
    {
        try {
            if(manufacturedMatDAO == null) {
                manufacturedMatDAO = (ManufacturedMaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MANUFACTURED_MATERIAL_DAO_CLASS);
            }
            return (manufacturedMatDAO.load(materialUid));
        }

        catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUid: "+materialUid+" Fails selectManufacturedMaterials()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("materialUid: "+materialUid+" Fails selectManufacturedMaterials()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Selects Entity IDs of Material Value Object from database
     * @param aUID Entity UID
     *
     * @return Collection<Object>  of EntityIDs
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private Collection<Object>  selectEntityIDs(long aUID)
      throws NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            return (entityIdDAO.load(aUID));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails selectEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails selectEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Selects Entity Locator Participations of Material Value Object from database
     * @param aUID Entity Locator Participation UID
     *
     * @return Collection<Object>  of Entity Locator Participations
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private Collection<Object>  selectEntityLocatorParticipations(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (entityLocatorParticipationDAO.load(aUID));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails selectEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails selectEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Removes Material Data Table of Material Value Object from database
     * @param aUID Material UID
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void removeMaterial(long aUID)
      throws NEDSSSystemException
    {
        try {
            if(materialDAO == null) {
                materialDAO = (MaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_DAO_CLASS);
            }
            materialDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails removeMaterial()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails removeMaterial()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Removes Entity IDs of Material Value Object from database
     * @param aUID Entity UID
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void removeEntityIDs(long aUID)
      throws NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails removeEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails removeEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Removes Entity Locator Participations of Material Value Object from database
     * @param aUID Entity Locator Participations UID
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void removeEntityLocatorParticipations(long aUID)
      throws NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails removeEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails removeEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Updates Material of Material Value Object in database
     * @param mvo Material Value Object
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void updateMaterial(MaterialVO mvo)
      throws NEDSSSystemException, NEDSSConcurrentDataException, NEDSSSystemException
    {
        try {
            if(materialDAO == null) {
                materialDAO = (MaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_DAO_CLASS);
            }
            materialDAO.store(mvo.getTheMaterialDT());

        }catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateMaterial() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateMaterial()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails updateMaterial()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }

    }

    private void updateManufacturedMaterials(MaterialVO mvo)
      throws NEDSSSystemException
    {
        try {
            if(manufacturedMatDAO == null) {
                manufacturedMatDAO = (ManufacturedMaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MANUFACTURED_MATERIAL_DAO_CLASS);
            }
            manufacturedMatDAO.store(mvo.getTheManufacturedMaterialDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateManufacturedMaterials()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails updateManufacturedMaterials()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Updates Entity IDs of Material Value Object in database
     * @param mvo Material Value Object
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void updateEntityIDs(MaterialVO mvo)
      throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.store(mvo.getTheEntityIdDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails updateEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Updates Entity Locator Participations of Material Value Object in database
     * @param mvo Material Value Object
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private void updateEntityLocatorParticipations(MaterialVO mvo)
      throws NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.store(mvo.getTheEntityLocatorParticipationDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails updateEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails updateEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Finds Material Data Table Entry of Material Value Object in database
     * @param materialUid Material UID
     *
     * @return Material Data Table Entry UID
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private Long findMaterial(long materialUid)
      throws NEDSSSystemException
    {
        Long findPK = null;
        try {
            logger.debug("MaterialRootDAOImpl.findMaterial"); // debugging
            if(materialDAO == null) {
                materialDAO = (MaterialDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_DAO_CLASS);
            }
            findPK = materialDAO.findByPrimaryKey(materialUid);
            logger.debug("MaterialRootDAOImpl.findMaterial findPK=" + findPK); // debugging

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUid: "+materialUid+" Fails findMaterial()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("materialUid: "+materialUid+" Fails findMaterial()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    /**
     * Finds Entity IDs of Material Value Object in database
     * @param materialUid Material UID
     *
     * @return Material Entity ID UID
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private Long findEntityIDs(long materialUid)
      throws NEDSSSystemException
    {
        Long findPK = null;
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            findPK = entityIdDAO.findByPrimaryKey(materialUid);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUid: "+materialUid+" Fails findEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("materialUid: "+materialUid+" Fails findEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    /**
     * Finds Entity Locator Participations of Material Value Object in database
     * @param materialUid Material UID
     *
     * @return Entity Locator Participations
     *
     * @exception NEDSSSystemException NEDSS Application Exception
     * @exception NEDSSSystemException NEDSS System Exception
     */
    private Long findEntityLocatorParticipations(long materialUid)
      throws NEDSSSystemException
    {
        Long findPK = null;
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            findPK = entityLocatorParticipationDAO.findByPrimaryKey(materialUid);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("materialUid: "+materialUid+" Fails findEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("materialUid: "+materialUid+" Fails findEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    //get collection of RoleDT from RoleDAOImpl entered by John Park
 private Collection<Object>  selectRoleDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(roleDAOImpl == null) {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }
            logger.debug("aUID in selectRoleDTCollection  = " + aUID);
            return (roleDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails selectRoleDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails selectRoleDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object>  selectParticipationDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(materialParticipationDAOImpl == null) {
                materialParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (materialParticipationDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("aUID: "+aUID+" Fails selectParticipationDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("aUID: "+aUID+" Fails selectParticipationDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws NEDSSSystemException
    {
        Collection<Object>  newPartDTs = new ArrayList<Object> ();
        try
        {
            if(materialParticipationDAOImpl == null)
            {
                materialParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_PARTICIPATION_DAO_CLASS);
            }
           Iterator<Object>  iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                materialParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails setParticipation()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set collection of RoleDTs and return them with sequence values assigned- Wade Steele
 public Collection<Object>  setRoleDTCollection(Collection<Object> roleDTs)
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        ArrayList<Object> returnRoles = new ArrayList<Object> ();
        try
        {
            if(roleDAOImpl == null)
            {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }
            returnRoles = (ArrayList<Object> )roleDAOImpl.setRoleDTCollection(roleDTs);
            logger.debug("Size of the collection returned is: " + returnRoles.size());
            return returnRoles;


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setRoleDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(Exception ndapex) {
            logger.fatal("Fails setRoleDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


}//end of MaterialRootDAOImpl class
