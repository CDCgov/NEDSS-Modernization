/**
* Name:		OrganizationRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               Organization value object in the Organization entity bean.
*               This class encapsulates all the JDBC calls made by the OrganizationEJB
*               for a Organization object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of OrganizationEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Rick Randazzo & NEDSS Development Team
*               John Park
* @version	1.0
*/

package gov.cdc.nedss.entity.organization.ejb.dao;


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
import javax.ejb.EJBException;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.association.dt.*;


public class OrganizationRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(OrganizationRootDAOImpl.class.getName());

    private OrganizationVO ovo = null;
    private long organizationUID;
    private  OrganizationDAOImpl organizationDAO = null;
    private  OrganizationNameDAOImpl organizationNameDAO = null;
    private  EntityIdDAOImpl entityIdDAO = null;
    private  EntityLocatorParticipationDAOImpl entityLocatorParticipationDAO = null;
    //role and participation DAOs for place
    private  RoleDAOImpl roleDAOImpl = null;
    private  ParticipationDAOImpl organizationParticipationDAOImpl = null;
    public OrganizationRootDAOImpl()
    {
    }
    /**
     * Insert the organization objects(all organization related DTs) to the  database
     * @param obj   the Object
     * @return organization UID   the long
     * @throws NEDSSSystemException
     */
    public long create(Object obj) throws NEDSSSystemException
    {
        this.ovo = (OrganizationVO)obj;

        /**
        *  Inserts OrganizationDT object
        */

        if (this.ovo != null)
        {
            organizationUID = insertOrganization(this.ovo);
            // logger.debug("OrganizationRootDAOImpl - Organization UID = " + organizationUID);
            (this.ovo.getTheOrganizationDT()).setOrganizationUid(new Long(organizationUID));
        }

        /**
        * Inserts OrganizationNameDT collection
        */

        if (this.ovo != null && this.ovo.getTheOrganizationNameDTCollection() != null){
            insertOrganizationNames(this.ovo);
        }

        /**
        * Inserts EntityIdDT collection
        */

        if (this.ovo != null && this.ovo.getTheEntityIdDTCollection() != null){
          // logger.debug("OrganizationRootDAOImpl - this.ovo.getTheEntityIdDTCollection() = null");
          insertEntityIDs(this.ovo);
       }

        /**
        * Inserts EntityLocatorParticipationDT collection
        */

        if (this.ovo != null && this.ovo.getTheEntityLocatorParticipationDTCollection() != null)
        {
            // logger.debug("OrganizationRootDAOImpl - this.ovo.getTheEntityLocatorParticipationDTCollection() != null");
            insertEntityLocatorParticipations(this.ovo);
        }
        /**
        * Inserts RoleDT collection
        */

        if (this.ovo != null && this.ovo.getTheRoleDTCollection() != null){
          insertRoles(this.ovo);
        }

        this.ovo.setItNew(false);
        this.ovo.setItDirty(false);
        return ((((OrganizationVO)obj).getTheOrganizationDT().getOrganizationUid()).longValue());
    }

  /**
   * Updates all the Organization Objects in the database
   * @param obj  the Object
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        // logger.debug("In OrganizatrionRootDAOImpl Store");
        // logger.debug("In OrganizatrionRootDAOImpl Store - obj = " + obj);
        this.ovo = (OrganizationVO)obj;
        // logger.debug("In OrganizatrionRootDAOImpl Store - ovo = " + ovo.toString());

        /**
        *  Updates OrganizationDT object
        */
        if(this.ovo.getTheOrganizationDT() != null && this.ovo.getTheOrganizationDT().isItNew())
        {
             logger.debug("In OrganizatrionRootDAOImpl Store - this.ovo.getTheOrganizationDT() != null & isItNew");
            insertOrganization(this.ovo);
            this.ovo.getTheOrganizationDT().setItNew(false);
            this.ovo.getTheOrganizationDT().setItDirty(false);
        }
        else if(this.ovo.getTheOrganizationDT() != null && this.ovo.getTheOrganizationDT().isItDirty())
        {
             logger.debug("In OrganizatrionRootDAOImpl Store - this.ovo.getTheOrganizationDT() != null & isItDirty()");
            updateOrganization(this.ovo);
            this.ovo.getTheOrganizationDT().setItDirty(false);
            this.ovo.getTheOrganizationDT().setItNew(false);
        }

        /**
        * Updates organization names collection
        */

        if (this.ovo.getTheOrganizationNameDTCollection() != null)
        {
             logger.debug("In OrganizatrionRootDAOImpl Store - this.ovo.getTheOrganizationNameDTCollection() != null");
            updateOrganizationNames(this.ovo);
             logger.debug("In OrganizatrionRootDAOImpl Store - did an update");
        }

        /**
         * Updates entity ids collection
         */
        if(this.ovo.getTheEntityIdDTCollection() != null)
        {
            // logger.debug("In OrganizatrionRootDAOImpl Store - this.ovo.getTheEntityIdDTCollection() != null");
            updateEntityIDs(this.ovo);
            // logger.debug("In OrganizatrionRootDAOImpl completed updateEntityIDs");
        }

        /**
        * Updates entity locator participations collection
        */
        if (this.ovo.getTheEntityLocatorParticipationDTCollection() != null)
        {
            // logger.debug("In OrganizatrionRootDAOImpl Store - this.ovo.getTheEntityLocatorParticipationDTCollection() != null");
            updateEntityLocatorParticipations(this.ovo);
            // logger.debug("In OrganizatrionRootDAOImpl completed updateEntityLocatorParticipations");
        }

                /**
         * Update roles
         */
         if (this.ovo.getTheRoleDTCollection() != null)
        {
            insertRoles(this.ovo);

        }
        logger.info("Done store() for an organization vo- return: void");
    }
  /**
   * The method will remove the organization objects
   * @param organizationUID  the long
   * @throws NEDSSSystemException
   */
    public void remove(long organizationUID) throws NEDSSSystemException
    {
        /**
        * Removes EntityLocatorParticipationDT collection
        */

        removeEntityLocatorParticipations(organizationUID);

        /**
        * Removes EntityIdDT Collection
        */

        removeEntityIDs(organizationUID);

        /**
        * Removes OrganizationNameDT Collection
        */

        removeOrganizationNames(organizationUID);

        /**
        *  Removes OrganizationDT
        */

        removeOrganization(organizationUID);
    }
   /**
     * This method selects the organization objects
     * @param organizationUID
     * @return  OrganizationVO  the object
     * @throws NEDSSSystemException
   */
    public Object loadObject(long organizationUID) throws NEDSSSystemException
    {
        this.ovo = new OrganizationVO();


        /**
        *  Selects OrganizationDT object
        */

        OrganizationDT pDT = selectOrganization(organizationUID);
        this.ovo.setTheOrganizationDT(pDT);

        /**
        * Selects OrganizationNameDT Collection
        */

        Collection<Object>  pnColl = selectOrganizationNames(organizationUID);
        this.ovo.setTheOrganizationNameDTCollection(pnColl);

        /**
        * Selects EntityIdDT collection
        */

        Collection<Object>  idColl = selectEntityIDs(organizationUID);
        this.ovo.setTheEntityIdDTCollection(idColl);

        /**
        * Selects EntityLocatorParticipationDT collection
        */

        Collection<Object>  elpColl = selectEntityLocatorParticipations(organizationUID);
        this.ovo.setTheEntityLocatorParticipationDTCollection(elpColl);
        //Selects RoleDTcollection
        Collection<Object>  roleColl = selectRoleDTCollection(organizationUID);
        this.ovo.setTheRoleDTCollection(roleColl);

        //SelectsParticipationDTCollection
        // Fixed in Rel1.2 for Defect#civil00015469
//        Collection<Object>  parColl = selectParticipationDTCollection(organizationUID);
//        this.ovo.setTheParticipationDTCollection(parColl);


        this.ovo.setItNew(false);
        this.ovo.setItDirty(false);
        return this.ovo;
    }

    /**
     * Specifically for loading the Reporting Lab or when logic requires the loading of a specific participation record.  See logic in method regarding
     * loading the ParticipationDTCollection  for this organization.  The elr can result in
     * the participation to have a substantial amount of Reporting labs with the same
     * subjectEntityUid, therefore need to select based on teh actUid for the observation also.
     * @param organizationUID
     * @param actUid
     * @return
     * @throws NEDSSSystemException
     */
    public Object loadObject(long organizationUID, long actUid) throws NEDSSSystemException
    {
        this.ovo = new OrganizationVO();


        /**
        *  Selects OrganizationDT object
        */

        OrganizationDT pDT = selectOrganization(organizationUID);
        this.ovo.setTheOrganizationDT(pDT);

        /**
        * Selects OrganizationNameDT Collection
        */

        Collection<Object>  pnColl = selectOrganizationNames(organizationUID);
        this.ovo.setTheOrganizationNameDTCollection(pnColl);

        /**
        * Selects EntityIdDT collection
        */

        Collection<Object>  idColl = selectEntityIDs(organizationUID);
        this.ovo.setTheEntityIdDTCollection(idColl);

        /**
        * Selects EntityLocatorParticipationDT collection
        */

        Collection<Object>  elpColl = selectEntityLocatorParticipations(organizationUID);
        this.ovo.setTheEntityLocatorParticipationDTCollection(elpColl);
        //Selects RoleDTcollection
        Collection<Object>  roleColl = selectRoleDTCollection(organizationUID);
        this.ovo.setTheRoleDTCollection(roleColl);

        //SelectsParticipationDTCollection
        Collection<Object>  parColl = selectParticipationDTCollection(organizationUID, actUid);
        this.ovo.setTheParticipationDTCollection(parColl);


        this.ovo.setItNew(false);
        this.ovo.setItDirty(false);
        return this.ovo;
    }


   /**
    * Find the organization using the primary key
    * @param organizationUID
    * @return organizationPK  the long
    * @throws NEDSSSystemException
    */
    public Long findByPrimaryKey(long organizationUID) throws NEDSSSystemException
    {
        /**
         * Finds organization object
         */
        // logger.debug("OrganizationRootDAOImpl - Starting to find by primarykey!");
        Long organizationPK = findOrganization(organizationUID);
        // logger.debug("OrganizationRootDAOImpl - Done find by primarykey!");
        return organizationPK;
    }
  /**
   * The method inserts organization object to the database
   * @param ovo   the OrganizationVO
   * @return organizationUID  the long
   * @throws EJBException
   * @throws NEDSSSystemException
   */
    private long insertOrganization(OrganizationVO ovo) throws EJBException, NEDSSSystemException
    {
        try
        {
            if(organizationDAO == null)
            {
                //organizationDAO = new OrganizationDAOImpl();
                organizationDAO = (OrganizationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            // logger.debug("OrganizationRootDAOImpl - Organization DT = " + ovo.getTheOrganizationDT());
            organizationUID = organizationDAO.create(ovo.getTheOrganizationDT());
            // logger.debug("OrganizationRootDAOImpl - Organization root uid = " + organizationUID);
            ovo.getTheOrganizationDT().setOrganizationUid(new Long(organizationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertOrganization()", ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertOrganization()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return organizationUID;
    }
  /**
  * Inserts Organization Names
  * @param ovo  the OrganizationVO
  * @throws EJBException
  * @throws NEDSSSystemException
  */
    private void insertOrganizationNames(OrganizationVO ovo) throws EJBException, NEDSSSystemException
    {
        try
        {
            if(organizationNameDAO == null)
            {
                //organizationNameDAO =  new OrganizationNameDAOImpl();
                organizationNameDAO = (OrganizationNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_NAME_DAO_CLASS);
            }
            // logger.debug("OrganizationRootDAOImpl - insertOrganizationNames");
            organizationUID = organizationNameDAO.create((ovo.getTheOrganizationDT().getOrganizationUid()).longValue(), ovo.getTheOrganizationNameDTCollection());
            // logger.debug("OrganizationRootDAOImpl - insertOrganizationNames - organizationUID = " + organizationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertOrganizationNames()", ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertOrganizationNames()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Inserts  entityId's of the organization to the database
     * @param ovo  the OrganizationVO
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void insertEntityIDs(OrganizationVO ovo) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            organizationUID = entityIdDAO.create((ovo.getTheOrganizationDT().getOrganizationUid()).longValue(), ovo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
  /**
   * Inserts entityLocatorParticipations
   * @param ovo   the OrganizationVO
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
    private void insertEntityLocatorParticipations(OrganizationVO ovo) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            organizationUID = entityLocatorParticipationDAO.create((ovo.getTheOrganizationDT().getOrganizationUid()).longValue(), ovo.getTheEntityLocatorParticipationDTCollection());
            //ovo.getTheEntityLocatorParticipationDTCollection().setOrganizationUid(new Long(organizationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
    /**
     * Inserts Organization Roles
     * @param ovo   the OrganizationVO
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
private void insertRoles(OrganizationVO ovo) throws NEDSSSystemException, NEDSSSystemException
    {
        logger.debug("*********************** - inside the insertRoles method");
        try
        {
            if(roleDAOImpl == null)
            {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);

           }
          Long organizationUID = ovo.getTheOrganizationDT().getOrganizationUid();
          logger.debug ("remove all old roles");
          //roleDAOImpl.remove(organizationUID.longValue());// remove all role for this organization before adding new one.
          logger.debug("Inside insertRoles");
           if(ovo.getTheRoleDTCollection()!=null){
              Iterator<Object>  itrRoles = ovo.getTheRoleDTCollection().iterator();
                    while(itrRoles.hasNext()){
                      RoleDT roleDt = (RoleDT) itrRoles.next();
                      if (roleDt != null){
                        roleDt.setSubjectEntityUid(organizationUID);
                        roleDAOImpl.store(roleDt);

                      }
                    }

                }
           // }
            //organizationUID = roleDAOImpl.create((ovo.getTheOrganizationDT().getOrganizationUid()).longValue(), ovo.getTheRoleDTCollection());
            //ovo.getTheEntityLocatorParticipationDTCollection().setOrganizationUid(new Long(organizationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding organization role ", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding organization role ", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        catch(Exception e){
          e.printStackTrace();
        }
    }

  /**
    * Selects the organization based on the organizationUID
    * @param organizationUID
    * @return OrganizationDT
    * @throws NEDSSSystemException
    * @throws NEDSSSystemException
    */

    private OrganizationDT selectOrganization(long organizationUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationDAO == null)
            {
                //organizationDAO = new OrganizationDAOImpl();
                organizationDAO = (OrganizationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            return ((OrganizationDT)organizationDAO.loadObject(organizationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectOrganization()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectOrganization()", ndapex);
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
 /**
   * Selects the  Names of the Organization
   * @param aUID  long   the OrganizationUID
   * @return Collection
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
    private Collection<Object>  selectOrganizationNames(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationNameDAO == null)
            {
                //organizationNameDAO = new OrganizationNameDAOImpl();
                organizationNameDAO = (OrganizationNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_NAME_DAO_CLASS);
            }
            return (organizationNameDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectOrganizationNames()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectOrganizationNames()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Selects the EntityIds of the Organization
     * @param aUID long
     * @return Collection<Object>  of EntityIds
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectEntityIDs(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            return (entityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Selects Organization EntityLocatorParticipations
     * @param aUID long
     * @return Collection
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectEntityLocatorParticipations(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (entityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * To remove the Organization from the Database
     * @param aUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removeOrganization(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationDAO == null)
            {
                organizationDAO = (OrganizationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            organizationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
    /**
     * Removing the ORganization Names
     * @param aUID long   the organizationUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removeOrganizationNames(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationNameDAO == null)
            {
                organizationNameDAO = (OrganizationNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_NAME_DAO_CLASS);
            }
            organizationNameDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
  /**
   * Remove EntotyIDs of the organization
   * @param aUID   the organizationUID
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
    private void removeEntityIDs(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
   /**
    * Remove the EntityLocatorParticipations of the organization
    * @param aUID long      the organizationUID
    * @throws NEDSSSystemException
    * @throws NEDSSSystemException
    */
    private void removeEntityLocatorParticipations(long aUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            ndsex.printStackTrace();
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            ndapex.printStackTrace();
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
   /**
    * Update the organization Object
    * @param ovo  OrganizationVO
    * @throws NEDSSConcurrentDataException
    * @throws NEDSSSystemException
    * @throws NEDSSSystemException
    */
    private void updateOrganization(OrganizationVO ovo) throws NEDSSConcurrentDataException, NEDSSSystemException,  NEDSSSystemException
    {
        try
        {
            if(organizationDAO == null)
            {
                //organizationDAO = new OrganizationDAOImpl();
                organizationDAO = (OrganizationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            organizationDAO.store(ovo.getTheOrganizationDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateOrganization()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateOrganization() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateOrganization()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }


    }
  /**
   * Updates the OaganizationNames
   * @param ovo OrganizationVO
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
    private void updateOrganizationNames(OrganizationVO ovo) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationNameDAO == null)
            {

                organizationNameDAO = (OrganizationNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_NAME_DAO_CLASS);
            }

            organizationNameDAO.store(ovo.getTheOrganizationNameDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateOrganizationNames()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateOrganizationNames()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
   /**
     * Updates the Organization EntityIds in the database
     * @param ovo OrganizationVO
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void updateEntityIDs(OrganizationVO ovo) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.store(ovo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
   /**
    * Updates EntityLocatorParticipations of the organization
    * @param ovo  the OrganizationVO
    * @throws NEDSSSystemException
    * @throws NEDSSSystemException
    */
    private void updateEntityLocatorParticipations(OrganizationVO ovo)
              throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.store(ovo.getTheEntityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Find the organization and return it's primary key
     * @param organizationUID  long
     * @return long   returns the primary key of the organization
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Long findOrganization(long organizationUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            // logger.debug("OrganizationRootDAOImpl.findOrganization");
            if(organizationDAO == null)
            {
                organizationDAO = (OrganizationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            findPK = organizationDAO.findByPrimaryKey(organizationUID);
            // logger.debug("OrganizationRootDAOImpl.findOrganization findPK=" + findPK);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findOrganization()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findOrganization()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }
   /**
    * Find the organization Names
    * @param organizationUID  long
    * @throws NEDSSSystemException
    * @throws NEDSSSystemException
    */
    private void findOrganizationNames(long organizationUID) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(organizationNameDAO == null)
            {
                organizationNameDAO = (OrganizationNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_NAME_DAO_CLASS);
            }
            Long findPK = organizationNameDAO.findByPrimaryKey(organizationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findOrganizationNames()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findOrganizationNames()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
    /**
     * Find the EnityIds of the organization
     * @param organizationUID  long
     * @throws NEDSSSystemException
     */
    private void findEntityIDs(long organizationUID) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            Long findPK = entityIdDAO.findByPrimaryKey(organizationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findEntityIDs()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findEntityIDs()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
 /**
   * Find the EnityLocatorParticipations of the organization
   * @param organizationUID  long
   * @throws NEDSSSystemException
   */
    private void findEntityLocatorParticipations(long organizationUID) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            Long findPK = entityLocatorParticipationDAO.findByPrimaryKey(organizationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findEntityLocatorParticipations()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findEntityLocatorParticipations()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

//    public static void main(String args[]){
    /*  logger.debug("OrganizationRootDAOImpl - Doing the main thing");
      try{
//        OrganizationDAOImpl orgDAOI = new OrganizationDAOImpl();
//        OrganizationNameDAOImpl orgNameDAOI = new OrganizationNameDAOImpl();
/*
        Long uid = new Long(199);

        OrganizationNameDT orgNameDT = new OrganizationNameDT();
        orgNameDT.setOrganizationUid(uid);
//        orgNameDT.setNmTxt("Lucy");
        orgNameDT.setOrganizationNameSeq(new Integer(3));
        orgNameDT.setNmUseCd("A");
        orgNameDT.setItNew(false);
        orgNameDT.setItDirty(true);
        ArrayList<Object> cOrgNameDT = new ArrayList<Object> ();
        cOrgNameDT.add(orgNameDT);

        OrganizationDT orgDT = new OrganizationDT();
        orgDT.setPhoneNbr("1234567890");
        orgDT.setOrganizationUid(uid);
        orgDT.setItDirty(true);
        orgDT.setItNew(false);

        OrganizationVO orgVO = new OrganizationVO();
        orgVO.setTheOrganizationDT(orgDT);
        orgVO.setTheOrganizationNameDTCollection(cOrgNameDT);
*/
//        OrganizationRootDAOImpl orgRootDAO = new OrganizationRootDAOImpl();
//        orgRootDAO.create(orgVO);
//        orgRootDAO.updateOrganizationNames(orgVO);

//        Long x = orgRootDAO.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);
//        orgVO = (OrganizationVO)orgRootDAO.loadObject(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - done load");
//        logger.debug("OrganizationRootDAOImpl - phone number = " + odt.getPhoneNbr());
//        x = orgDAOI.findByPrimaryKey(uid.longValue());
//        logger.debug("OrganizationRootDAOImpl - x = " + x);

 //     }catch(Exception e){
 //       logger.debug("\n\nOrganizationRootDAOImpl ERROR : turkey no worky = \n" + e);
  //    }
 //   }

 /**
 * Get collection of RoleDT from RoleDAOImpl
 * @param aUID  long  the ORganizationUID
 * @return    the Collection<Object>  of RoleDT objects
 * @throws NEDSSSystemException
 * @throws NEDSSSystemException
 */
 private Collection<Object>  selectRoleDTCollection(long aUID)
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        try  {
            if(roleDAOImpl == null) {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }
            logger.debug("aUID in selectRoleDTCollection  = " + aUID);
            return (roleDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectRoleDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectRoleDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }



 /**
  * get collection of Participation  from ParticipationDAOImpl
  * @param aUID
  * @return Collection<Object>    the Collection<Object>  of ParticipationDTs
  * @throws NEDSSSystemException
  * @throws NEDSSSystemException
  */
 private Collection<Object>  selectParticipationDTCollection(long aUID)
      throws
        NEDSSSystemException,
        NEDSSSystemException
    {
        try  {
            if(organizationParticipationDAOImpl == null) {
                organizationParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (organizationParticipationDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * To be called when looking for a specific Participation record.
     * @param aUID
     * @param actUid
     * @return
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectParticipationDTCollection(long aUID, long actUid)
        throws
          NEDSSSystemException,
          NEDSSSystemException
      {
          try  {
              if(organizationParticipationDAOImpl == null) {
                  organizationParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_PARTICIPATION_DAO_CLASS);
              }
              logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
              return (organizationParticipationDAOImpl.load(aUID, actUid));


          } catch(NEDSSDAOSysException ndsex) {
              logger.fatal("Fails selectParticipationDTCollection()", ndsex);
              throw new NEDSSSystemException(ndsex.toString());

          } catch(NEDSSSystemException ndapex) {
              logger.fatal("Fails selectParticipationDTCollection()", ndapex);
              throw new NEDSSSystemException(ndapex.toString());
          }
      }


  /**
  * set the participation for the organizations
  * @param partDTs    the Collection<Object>  of participationDTs
  * @throws NEDSSSystemException
  */
 public void setParticipation(Collection<Object> partDTs)
      throws
        NEDSSSystemException
    {
        Collection<Object>  newPartDTs = new ArrayList<Object> ();
        try
        {
            if(organizationParticipationDAOImpl == null)
            {
                organizationParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_PARTICIPATION_DAO_CLASS);
            }
           Iterator<Object>  iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                organizationParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

 /**
 * Set collection of RoleDTs and return them with role_seq values assigned
 * @param roleDTs   the Collection
 * @return  Collection<Object>      the Collection<Object>  of roleDTs with role_seq values
 * @throws NEDSSSystemException
 */
 public Collection<Object>  setRoleDTCollection(Collection<Object> roleDTs)
      throws NEDSSSystemException
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

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setRoleDTCollection()", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


}//end of OrganizationRootDAOImpl class
