package gov.cdc.nedss.entity.entitygroup.ejb.dao;

// java.* imports
import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

// javax.* imports
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Timestamp;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
import gov.cdc.nedss.entity.entitygroup.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.*;
/**
 * Name:	EntityGroupRootDAOImpl.java
 * Description:	 This class encapsulates all the JDBC calls made by the EntityGroupEJB
 *               for a EntityGroup object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables is implemented through this root DAO class.
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/10/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     11/13/2001 Sohrab Jahani
 *               11/28/2001 John Park
 * @version      1.0.0
 */
public class EntityGroupRootDAOImpl extends BMPBase
{
    /**
     * Description: For logging. Enables Debugging when is set to true.
     *
     */
     static final LogUtils logger = new LogUtils(EntityGroupRootDAOImpl.class.getName());

    /**
     * Description: Current Matrial Value Object
     */
    private EntityGroupVO mvo = null;

    /**
     * Description: Current EntityGroup UID
     */
    private long EntityGroupUID;

    /**
     * Description: Current EntityGroup DAO
     */
    private EntityGroupDAOImpl entityGroupDAO = null;

    /**
     * Description: Current Entity DAO
     */
    private EntityIdDAOImpl entityIdDAO = null;


    /**
     * Description: Current Role DAO
     */
    private RoleDAOImpl roleDAOImpl = null;

    /**
     * Description: Current  Participation DAO
     */
    private ParticipationDAOImpl entityGroupParticipationDAOImpl = null;


    /**
     * Description: Current  EntityLocatorParticipationDAO
     */
    private EntityLocatorParticipationDAOImpl entityLocatorParticipationDAO = null;

    /**
     * Description: Constructor
     * @throws NEDSSSystemException
     */
    public EntityGroupRootDAOImpl()
      throws
        NEDSSSystemException
    {
//      DEBUG_MODE = true;
//      logger.debug("\nEntityGroupRootDAOImp Constructor"+DEBUG_MODE);

      //entityGroupDAO = DEBUG_MODE ? new EntityGroupDAOImpl() : null;
      //entityIdDAO = DEBUG_MODE ? new EntityIdDAOImpl() : null;
      //entityLocatorParticipationDAO  = DEBUG_MODE ? new EntityLocatorParticipationDAOImpl() : null;
    }

    /**
     * Description: Creates(Inserts) a EntityGroup Value Object in the database.
     * @param obj EntityGroup Object
     * @return    EntityGroup UID
     * @throws NEDSSSystemException
     */
    public long create(Object obj)
      throws
        NEDSSSystemException
    {
        this.mvo = (EntityGroupVO)obj;

        /*
         *  Inserts EntityGroupDT object
         */
        logger.debug("In create of EntityGrouprootDAOImpl");
        if (this.mvo != null)
        {
            EntityGroupUID = insertEntityGroup(this.mvo);
            logger.debug("Did insertEntityGroup with this.mvo");
            // logger.debug("EntityGroupRootDAOImpl - EntityGroup UID = " + EntityGroupUID); // debugging
            (this.mvo.getTheEntityGroupDT()).setEntityGroupUid(new Long(EntityGroupUID));
        }

        /*
         * Inserts EntityIdDT collection
         */

        if (this.mvo != null && this.mvo.getTheEntityIdDTCollection() != null){
          // logger.debug("EntityGroupRootDAOImpl - this.mvo.getTheEntityIdDTCollection() = null"); // debugging
          insertEntityIDs(this.mvo);
       }

        /*
         * Inserts EntityLocatorParticipationDT collection
         */

        if (this.mvo != null && this.mvo.getTheEntityLocatorParticipationDTCollection() != null)
        {
            // logger.debug("EntityGroupRootDAOImpl - this.mvo.getTheEntityLocatorParticipationDTCollection() != null"); // debugging
            insertEntityLocatorParticipations(this.mvo);
        }

        this.mvo.setItNew(false);
        this.mvo.setItDirty(false);
        return ((((EntityGroupVO)obj).getTheEntityGroupDT().getEntityGroupUid()).longValue());
    }

    /**
     * Description: Stores(Updates) a EntityGroup Value Object in the database
     * @param obj EntityGroup Object
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj)
      throws
      NEDSSSystemException,
        NEDSSConcurrentDataException

    {
        // logger.debug("In OrganizatrionRootDAOImpl Store"); // debugging
        // logger.debug("In OrganizatrionRootDAOImpl Store - obj = " + obj); // debugging
        this.mvo = (EntityGroupVO)obj;
        logger.debug("In EntityGroupRootDAOImpl Store - mvo = " + mvo.toString()); // debugging

        /*
         *  Updates EntityGroupDT object
         */
        if(this.mvo.getTheEntityGroupDT() != null && this.mvo.getTheEntityGroupDT().isItNew())
        {
            // logger.debug("In OrganizatrionRootDAOImpl Store - this.mvo.getTheEntityGroupDT() != null & isItNew"); // debugging
            logger.debug("about to insert entityGroup from EntityGroupRootDAOImpl");
            insertEntityGroup(this.mvo);
            this.mvo.getTheEntityGroupDT().setItNew(false);
            this.mvo.getTheEntityGroupDT().setItDirty(false);
        }
        else if(this.mvo.getTheEntityGroupDT() != null && this.mvo.getTheEntityGroupDT().isItDirty())
        {
            logger.debug("In EntityGruopRootDAOImpl Store - this.mvo.getTheEntityGroupDT() != null & isItDirty()"); // debugging
            updateEntityGroup(this.mvo);
            this.mvo.getTheEntityGroupDT().setItDirty(false);
            this.mvo.getTheEntityGroupDT().setItNew(false);
        }

        /*
         * Updates entity ids collection
         */
        if(this.mvo.getTheEntityIdDTCollection() != null)
        {
            // logger.debug("In OrganizatrionRootDAOImpl Store - this.mvo.getTheEntityIdDTCollection() != null"); // debugging
            updateEntityIDs(this.mvo);
            // logger.debug("In OrganizatrionRootDAOImpl completed updateEntityIDs"); // debugging
        }

        /*
         * Updates entity locator participations collection
         */
        if (this.mvo.getTheEntityLocatorParticipationDTCollection() != null)
        {
            // logger.debug("In OrganizatrionRootDAOImpl Store - this.mvo.getTheEntityLocatorParticipationDTCollection() != null"); // debugging
            updateEntityLocatorParticipations(this.mvo);
            // logger.debug("In OrganizatrionRootDAOImpl completed updateEntityLocatorParticipations"); // debugging
        }
    }

    /**
     * Description: Removes(Deletes) a EntityGroup Value Object from the database
     * @param EntityGroupUID EntityGroup UID
     * @throws NEDSSSystemException
     */
    public void remove(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        /*
         * Removes EntityLocatorParticipationDT collection
         */

          removeEntityLocatorParticipations(EntityGroupUID);

        /*
         * Removes EntityIdDT Collection
         */

          removeEntityIDs(EntityGroupUID);

        /*
         *  Removes EntityGroupDT
         */

        removeEntityGroup(EntityGroupUID);
    }

    /**
     * Description: Loads(Selects) a EntityGroup Value Object entry from the database
     * @param EntityGroupUID EntityGroup UID
     * @return    EntityGroup Object
     * @throws NEDSSSystemException
     */
    public Object loadObject(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        this.mvo = new EntityGroupVO();

        /*
         *  Selects EntityGroupDT object
         */

        EntityGroupDT pDT = selectEntityGroup(EntityGroupUID);
        this.mvo.setTheEntityGroupDT(pDT);

        //SelectsParticipationDTCollection
        Collection<Object>  parColl = selectParticipationDTCollection(EntityGroupUID);
        this.mvo.setTheParticipationDTCollection(parColl);

        // Selects EntityIdDT collection


        Collection<Object>  idColl = selectEntityIDs(EntityGroupUID);
        this.mvo.setTheEntityIdDTCollection(idColl);


        //Selects EntityLocatorParticipationDT collection

        Collection<Object>  elpColl = selectEntityLocatorParticipations(EntityGroupUID);
        this.mvo.setTheEntityLocatorParticipationDTCollection(elpColl);

        //Selects RoleDTcollection
        Collection<Object>  roleColl = selectRoleDTCollection(EntityGroupUID);
        this.mvo.setTheRoleDTCollection(roleColl);

        this.mvo.setItNew(false);
        this.mvo.setItDirty(false);
        return this.mvo;

    }

    /**
     * Description: Finds a EntityGroup Value Object in the database using its
     * EntityGroupUID as the primary key
     * @param EntityGroupUID EntityGroup UID
     * @return    EntityGroup UID
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        /*
         * Finds entityGroup object
         */
        // logger.debug("EntityGroupRootDAOImpl - Starting to find by primarykey!"); // debugging
        Long entityGroupPK = findEntityGroup(EntityGroupUID);
        // logger.debug("EntityGroupRootDAOImpl - Done find by primarykey!"); // debugging
        return entityGroupPK;
    }

    /**
     * Description: Inserts EntityGroup Value Object into database.
     * @param mvo EntityGroup EntityGroup Value Object
     * @return    EntityGroup UID
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    private long insertEntityGroup(EntityGroupVO mvo)
      throws
        EJBException,
        NEDSSSystemException
    {
        try {
            if(entityGroupDAO == null) {
                //entityGroupDAO = new EntityGroupDAOImpl();
                entityGroupDAO = (EntityGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_DAO_CLASS);
                logger.debug(" in InsertEntityGroup and entityGroupDAO = " + JNDINames.ENTITYGROUP_DAO_CLASS);
            }
            // logger.debug("EntityGroupRootDAOImpl - EntityGroup DT = " + mvo.getTheEntityGroupDT()); // debugging
            EntityGroupUID = entityGroupDAO.create(mvo.getTheEntityGroupDT());
            // logger.debug("EntityGroupRootDAOImpl - EntityGroup root uid = " + EntityGroupUID); // debugging
            mvo.getTheEntityGroupDT().setEntityGroupUid(new Long(EntityGroupUID));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to insert an entity groupd", ndsex);
            throw new EJBException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to insert an entity groupd", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return EntityGroupUID;
    }

    /**
     * Description: Inserts EntityGroup UID of EntityGroup Value Object into database.
     * @param mvo EntityGroup EntityGroup Value Object
     * @throws NEDSSSystemException
     */
    private void insertEntityIDs(EntityGroupVO mvo)
      throws
        NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            EntityGroupUID = entityIdDAO.create((mvo.getTheEntityGroupDT().getEntityGroupUid()).longValue(), mvo.getTheEntityIdDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to insert entity groupd ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to insert entity groupd ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Inserts Entity Locator Participation of EntityGroup Value Object
     * into database
     * @param mvo EntityGroup EntityGroup Value Object
     * @throws NEDSSSystemException
     */
    private void insertEntityLocatorParticipations(EntityGroupVO mvo)
      throws
        NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            EntityGroupUID = entityLocatorParticipationDAO.create((mvo.getTheEntityGroupDT().getEntityGroupUid()).longValue(), mvo.getTheEntityLocatorParticipationDTCollection());
            //mvo.getTheEntityLocatorParticipationDTCollection().setEntityGroupUid(new Long(EntityGroupUID));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to insert entity groupd locator paticipations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to insert entity groupd locator paticipations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Selects EntityGroup Value Object from database.
     * @param EntityGroupUID EntityGroup UID

     * @return EntityGroup Data Table
     * @throws NEDSSSystemException
     */
    private EntityGroupDT selectEntityGroup(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        try {
            if(entityGroupDAO == null) {
                //entityGroupDAO = new EntityGroupDAOImpl();
                entityGroupDAO = (EntityGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_DAO_CLASS);
            }
            return ((EntityGroupDT)entityGroupDAO.loadObject(EntityGroupUID));
        }

        catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to select an entity group", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to select an entity group", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Selects Entity IDs of EntityGroup Value Object from database
     * @param aUID Entity UID
     * @return Collection<Object>  of EntityIDs
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectEntityIDs(long aUID)
      throws
        NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            return (entityIdDAO.load(aUID));

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to select entity group ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to select entity group ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Selects Entity Locator Participations of EntityGroup Value Object from database
     * @param aUID Entity Locator Participation UID
     * @return Collection<Object>  of Entity Locator Participations
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectEntityLocatorParticipations(long aUID)
      throws
        NEDSSSystemException
    {
        try  {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectEntityLocatorParticipations = " + aUID);
            return (entityLocatorParticipationDAO.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to select entity group locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to select entity group locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Description: get collection of RoleDT object for a given UID.
     * @param aUID : long value
     * @return : Collection<Object>  object
     * @throws NEDSSSystemException
     */
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
            logger.fatal("Fails to select entity group roles", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to select entity group roles", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    /**
     * Description: get collection of Participation  for a given UID
     * @param aUID : long value
     * @return : Collection<Object>  object
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectParticipationDTCollection(long aUID)
      throws
        NEDSSSystemException
    {
        try  {
            if(entityGroupParticipationDAOImpl == null) {
                entityGroupParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (entityGroupParticipationDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to select entity group participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to select entity group participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Removes EntityGroup Data Table of EntityGroup Value Object from database
     * @param aUID EntityGroup UID
     * @throws NEDSSSystemException
     */
    private void removeEntityGroup(long aUID)
      throws
        NEDSSSystemException
    {
        try {
            if(entityGroupDAO == null) {
                entityGroupDAO = (EntityGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_DAO_CLASS);
            }
            logger.debug("in root about to call EntityGroupDAOImpl.remove");
            entityGroupDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to remove entity group", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
        logger.fatal("Fails to remove entity group", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Removes Entity IDs of EntityGroup Value Object from database for a given UID
     * @param aUID Entity UID
     * @throws NEDSSSystemException
     */
    private void removeEntityIDs(long aUID)
      throws
        NEDSSSystemException
    {
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to remove entity group ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to remove entity group ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Removes Entity Locator Participations of EntityGroup Value Object from database
     * @param aUID Entity Locator Participations UID
     * @throws NEDSSSystemException
     */
    private void removeEntityLocatorParticipations(long aUID)
      throws
        NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.remove(aUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to remove entity group participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to remove entity group participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Updates EntityGroup of EntityGroup Value Object in database
     * @param mvo EntityGroup Value Object
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    private void updateEntityGroup(EntityGroupVO mvo)
      throws
        NEDSSSystemException, NEDSSConcurrentDataException
    {
        try {
            logger.debug("in updateentitygruop");
            if(entityGroupDAO == null) {
                //entityGroupDAO = new EntityGroupDAOImpl();
                entityGroupDAO = (EntityGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_DAO_CLASS);
            }
            entityGroupDAO.store(mvo.getTheEntityGroupDT());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to update entity group", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSConcurrentDataException ncdaex) {
            logger.fatal("Fails updateEntityGroup() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to update entity group", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Updates Entity IDs of EntityGroup Value Object in database
     * @param mvo EntityGroup Value Object
     * @throws NEDSSSystemException
     */
    private void updateEntityIDs(EntityGroupVO mvo)
      throws
        NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.store(mvo.getTheEntityIdDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to update entity group ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to update entity group ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Updates Entity Locator Participations of EntityGroup Value Object in database
     * @param mvo EntityGroup Value Object
     * @throws NEDSSSystemException
     */
    private void updateEntityLocatorParticipations(EntityGroupVO mvo)
      throws
        NEDSSSystemException
    {
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.store(mvo.getTheEntityLocatorParticipationDTCollection());

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to update entity group locator particiopations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to update entity group locator particiopations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: Finds EntityGroup Value Object in database
     * @param EntityGroupUID EntityGroup UID
     * @return EntityGroup Data Table Entry UID
     * @throws NEDSSSystemException
     */
    private Long findEntityGroup(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        Long findPK = null;
        try {
            // logger.debug("EntityGroupRootDAOImpl.findEntityGroup"); // debugging
            if(entityGroupDAO == null) {
                entityGroupDAO = (EntityGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_DAO_CLASS);
            }
            findPK = entityGroupDAO.findByPrimaryKey(EntityGroupUID);
            // logger.debug("EntityGroupRootDAOImpl.findEntityGroup findPK=" + findPK); // debugging

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to find entity group", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to find entity group", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    /**
     * Description: Finds Entity IDs of EntityGroup Value Object in database
     * @param EntityGroupUID EntityGroup UID
     * @return EntityGroup Entity ID UID
     * @throws NEDSSSystemException
     */
    private Long findEntityIDs(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        Long findPK = null;
        try {
            if(entityIdDAO == null) {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            findPK = entityIdDAO.findByPrimaryKey(EntityGroupUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to find entity group ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to find entity group ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    /**
     * Description: Finds Entity Locator Participations of EntityGroup Value Object in database
     * @param EntityGroupUID EntityGroup UID
     * @return Entity Locator Participations
     * @throws NEDSSSystemException
     */
    private Long findEntityLocatorParticipations(long EntityGroupUID)
      throws
        NEDSSSystemException
    {
        Long findPK = null;
        try {
            if(entityLocatorParticipationDAO == null) {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            findPK = entityLocatorParticipationDAO.findByPrimaryKey(EntityGroupUID);

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to find entity group locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to find entity group locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }
/*    protected synchronized Connection getConnection(){
    Connection conn = null;
    try{
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa","sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver in main try");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
    }

//test main
      public static void main(String args[])
      {
        logger.debug("EntityGroupRootDAOImpl - Doing the main thing");
        try
        {

          EntityGroupRootDAOImpl entityGroupRootDAOImpl = new EntityGroupRootDAOImpl();
          Long entityuid = new Long(601);
          Integer cnt = new Integer(5050);
          EntityGroupDT entityDT = new EntityGroupDT();
          EntityGroupVO entityVO = new EntityGroupVO();

          entityDT.setEntityGroupUid(entityuid);
          entityDT.setAddReasonCd("newreasonCdstore4");
          entityDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
          entityDT.setAddUserId(entityuid);
          entityDT.setCd("cd");
          entityDT.setCdDescTxt("CDDEscText");
          entityDT.setDescription("description");
          entityDT.setDurationAmt("durationamt");
          entityDT.setFromTime(new Timestamp(new java.util.Date().getTime()));

          entityDT.setLastChgReasonCd("lastchgreasoncd2");
          entityDT.setLastChgTime(new Timestamp(new java.util.Date().getTime()));
          entityDT.setLastChgUserId(entityuid);
          entityDT.setNm("fd");
          entityDT.setOrgAccessPermis("orgaccesspermis");
          entityDT.setProgAreaAccessPermis("progareaaccesspermis");
          entityDT.setRecordStatusCd("recordstatuscd2");
          entityDT.setStatusCd("b");
          entityDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));
          entityDT.setToTime(new Timestamp(new java.util.Date().getTime()));
          entityDT.setUserAffiliationTxt("useraffiliation2");
          entityDT.setLocalId("localid");
          entityDT.setItNew(false);
          entityDT.setItDirty(true);
          logger.debug("entityDT.isItNew: " + entityDT.isItNew());
          logger.debug("entityDT.isDirty: " + entityDT.isItDirty());
          entityVO.setTheEntityGroupDT(entityDT);
          entityGroupRootDAOImpl.store(entityVO);
          logger.debug("went through without errors...i think.. " );//+ obserDT.getObservationUid);
        }
        catch(Exception e)
        {
          logger.debug("\n\nEntityGroupRootDAOImpl ERROR : Not good = \n" + e);
        }
      }

//end of test main
*/



/**
 * Description: sets a collection of participationDT
 * @param partDTs : Collection<Object>  of ParticipationDT.
 * @throws NEDSSSystemException
 */
    public void setParticipation(Collection<Object> partDTs)
      throws
        NEDSSSystemException
    {
        Collection<Object>  newPartDTs = new ArrayList<Object> ();
        try
        {
            if(entityGroupParticipationDAOImpl == null)
            {
                entityGroupParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
            }
           Iterator<Object>  iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                entityGroupParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails to set entity group participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to set entity group participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * Description: set collection of RoleDTs
     * @param partDTs : Collection<Object>  of RoleDTs
     * @throws NEDSSSystemException
     */

 public Collection<Object>  setRoleDTCollection(Collection<Object> roleDTs)
      throws
        NEDSSSystemException
    {
        ArrayList<Object> returnRoles = new ArrayList<Object> ();
        //ArrayList<Object> rolesDTArray = (ArrayList<Object> )roleDTs;
        //logger.debug("Size of array after cast is: " + rolesDTArray.size());
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
            logger.fatal("Fails to set entity group roles", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails to set entity group roles", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

}//end of EntityGroupRootDAOImpl class

