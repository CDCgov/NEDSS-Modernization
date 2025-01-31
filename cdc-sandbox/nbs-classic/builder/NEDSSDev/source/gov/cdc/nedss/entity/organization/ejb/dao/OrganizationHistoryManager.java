/**
* Name:		OrganizationHistRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for inserting
*               Organization value object into Organization related history table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Neddss Development team
* @version	1.0
*/
package gov.cdc.nedss.entity.organization.ejb.dao;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.locator.dt.*;



import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */
public class OrganizationHistoryManager
{

    static final LogUtils logger = new LogUtils(OrganizationHistoryManager.class.getName());
    private long organizationUid;
    private OrganizationHistDAOImpl organizationHistDAO;
    private OrganizationNameHistDAOImpl orgNameHistDAO;
    public  int versionCtrlNbr = -1;
    private OrganizationVO orgVoHist;
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
    public OrganizationHistoryManager()
    {
    } //end of constructor

    /**
    * The Constructor which will create the instances of OrganizationHistDAOImpl,
    * EntityIdHistDAOImpl, ParticipationHistDAOImpl and RoleHistDAOImpl.
    * @param organizationUid long
    * @param versionCtrlNbr short
    * @throws NEDSSDAOSysException
    * @throws NEDSSSystemException
    */
    public OrganizationHistoryManager(long organizationUid,
                                      short versionCtrlNbr)
                               throws NEDSSDAOSysException,
                                      NEDSSSystemException
    {
        this.organizationUid = organizationUid;
        organizationHistDAO = new OrganizationHistDAOImpl(organizationUid,
                                                          versionCtrlNbr);
        this.versionCtrlNbr = versionCtrlNbr;
        logger.debug(
                "OrganizationHistoryManager--versionCtrlNbr: " +
                versionCtrlNbr);
        this.orgNameHistDAO = new OrganizationNameHistDAOImpl(versionCtrlNbr);
        entityIdHistDAOImpl = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl(
                                                        versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        roleHistDAOImpl = new RoleHistDAOImpl(versionCtrlNbr);
    } //end of constructor

   /**
   * This function takes a old InterventionVO and stores them into history tables
   * @param obj  Object: InterventionVOs
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
        orgVoHist = (OrganizationVO)obj;

        if (orgVoHist == null)

            return;

        /**
         * Insert organization to history
         */
        if (orgVoHist.getTheOrganizationDT() != null)
        {

            OrganizationDT dt = orgVoHist.getTheOrganizationDT();
            insertOrganization(dt);
            this.versionCtrlNbr = dt.getVersionCtrlNbr().intValue();
            logger.debug(
                    "OrganizationHistoryManager--nextSeqNumber: " +
                    versionCtrlNbr);
            this.orgNameHistDAO = new OrganizationNameHistDAOImpl(
                                          versionCtrlNbr);
            orgVoHist.getTheOrganizationDT().setItNew(false);
        }

        /**
        * Insert substance admin collection
        */
        if (orgVoHist.getTheOrganizationNameDTCollection() != null)
        {
            insertOrganizationNameColl(orgVoHist.getTheOrganizationNameDTCollection());
        }

        if (orgVoHist.getTheEntityIdDTCollection() != null)
        {
            insertEntityIdDTCollection(orgVoHist.getTheEntityIdDTCollection());
        }

        if (orgVoHist.getTheEntityLocatorParticipationDTCollection() != null)
        {
            insertEntityLocatorParticipationDTCollection(orgVoHist.getTheEntityLocatorParticipationDTCollection());
        }

        /*if (orgVoHist.getTheRoleDTCollection() != null)
        {
            insertRoleCollection(orgVoHist.getTheRoleDTCollection());
        }

        if (orgVoHist.getTheParticipationDTCollection() != null)
        {
            insertParticipationCollection(orgVoHist.getTheParticipationDTCollection());
        }*/
    } //end of store

  /**
   * LoadObject for a organizationVo for history
   * @param organizationUid Long
   * @param orgNameSeq Long
   * @param versionCtrlNbr Integer
   * @return object  OrganizationVO
   * @throws NEDSSSystemException
   * @throws NEDSSSystemException
   */
    public OrganizationVO load(Long organizationUid, Long orgNameSeq,
                               Integer versionCtrlNbr)
                        throws NEDSSSystemException, NEDSSSystemException
    {
        logger.info(
                "Starts loadObject() for a organization vo for the history...");

        OrganizationVO ovo = new OrganizationVO();
        organizationHistDAO = new OrganizationHistDAOImpl();
        orgNameHistDAO = new OrganizationNameHistDAOImpl();
        entityIdHistDAOImpl = new EntityIdHistDAOImpl();
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        roleHistDAOImpl = new RoleHistDAOImpl();

        /**
        *  Selects OrganizationDT object
        */
        OrganizationDT orgDT = organizationHistDAO.load(organizationUid,
                                                        versionCtrlNbr);
        ovo.setTheOrganizationDT(orgDT);

        /**
        * Selects ObservationNamesDT Collection
        */
        Collection<Object>  orgNameColl = orgNameHistDAO.load(organizationUid,
                                                     orgNameSeq,
                                                     versionCtrlNbr);
        ovo.setTheOrganizationNameDTCollection(orgNameColl);

        //Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  roleColl = roleHistDAOImpl.load(...);
        //mtvo.setTheRoleDTCollection(roleColl);
        //Need to determine if load(...) returns a collection or dt object.
        //Collection<Object>  participationColl = participationHistDAOImpl.load(...);
        //mtvo.setTheParticipationDTCollection(participationColl);
        Collection<Object>  idColl = entityIdHistDAOImpl.load(organizationUid,
                                                     versionCtrlNbr);
        ovo.setTheEntityIdDTCollection(idColl);

        Collection<Object>  elpColl = entityLocatorParticipationHistDAOImpl.load(
                                     organizationUid, versionCtrlNbr);
        ArrayList<Object> newElpColl = new ArrayList<Object> ();

        for (Iterator<Object> anIterator = elpColl.iterator(); anIterator.hasNext();)
        {

            EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();

            if (elpdt != null)
            {

                Long locatorUid = elpdt.getLocatorUid();
                physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl();
                postalLocatorHistDAO = new PostalLocatorHistDAOImpl();
                teleLocatorHistDAO = new TeleLocatorHistDAOImpl();

                Collection<Object>  physicalLocColl = physicalLocatorHistDAO.load(
                                                     locatorUid,
                                                     versionCtrlNbr);
                Collection<Object>  postalLocColl = postalLocatorHistDAO.load(
                                                   locatorUid, versionCtrlNbr);
                Collection<Object>  teleLocColl = teleLocatorHistDAO.load(locatorUid,
                                                                 versionCtrlNbr);

                for (Iterator<Object> it = physicalLocColl.iterator(); it.hasNext();)
                {

                    PhysicalLocatorDT pldt = (PhysicalLocatorDT)it.next();
                    elpdt.setThePhysicalLocatorDT(pldt);
                }

                for (Iterator<Object> it = postalLocColl.iterator(); it.hasNext();)
                {

                    PostalLocatorDT pldt = (PostalLocatorDT)it.next();
                    elpdt.setThePostalLocatorDT(pldt);
                }

                for (Iterator<Object> it = teleLocColl.iterator(); it.hasNext();)
                {

                    TeleLocatorDT pldt = (TeleLocatorDT)it.next();
                    elpdt.setTheTeleLocatorDT(pldt);
                }
            }

            newElpColl.add(elpdt);
        }

        ovo.setTheEntityLocatorParticipationDTCollection(elpColl);
        logger.info(
                "Done loadObject() for a organizationVo for history - return: " +
                ovo);

        return ovo;
    } //end of load

  /**
   * This method inserts the organization history by calling the store method of organizationHistDAO
   * @param  dt   the OrganizationDT
   * @throws NEDSSSystemException
   */
   private void insertOrganization(OrganizationDT dt)
                             throws NEDSSSystemException
    {

        if (dt == null)
            throw new NEDSSSystemException("Error: insert null organizationDt into organization Hist.");

        organizationHistDAO.store(dt);
    } //end of insertOrganization()

   /**
    * Inserts the organizationName Collection<Object>  to the database by calling the store(Collection<Object> coll)
    * method of orgNameHistDAO
    * @param coll Collection
    * @throws            NEDSSSystemException
    */
    private void insertOrganizationNameColl(Collection<Object> coll)
                                     throws NEDSSSystemException
    {

        if (coll == null)
            throw new NEDSSSystemException("Error: insert null OrganizationNameDT into organization name history.");

        orgNameHistDAO.store(coll);
    } //end of insertOrganizationNameColl
    /**
    * Helps to isert the entityId Collection<Object>  by calling store(Collection<Object> coll) method of
    * entityIdHistDAOImpl
    * @param coll Collection
    * @throws NEDSSDAOSysException
    * @throws NEDSSSystemException
    */
    private void insertEntityIdDTCollection(Collection<Object> coll)
                                     throws NEDSSDAOSysException,
                                            NEDSSSystemException
    {

        if (coll == null)
            throw new NEDSSSystemException("Error: insert null EntityIdDTCollection  into entity id hist.");

        entityIdHistDAOImpl.store(coll);
    }
    /**
     * Helps to insert a collection of entityLocatorParticipation
     * @param coll Collection
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertEntityLocatorParticipationDTCollection(Collection<Object> coll)
        throws NEDSSDAOSysException, NEDSSSystemException
    {

        if (coll == null)
            throw new NEDSSSystemException("Error: insert null EntityLocatorParticipationDTCollection  into entity locator participation hist.");

        physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl(versionCtrlNbr);
        postalLocatorHistDAO = new PostalLocatorHistDAOImpl(versionCtrlNbr);
        teleLocatorHistDAO = new TeleLocatorHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl.store(coll);

        for (Iterator<Object> anIterator = coll.iterator(); anIterator.hasNext();)
        {

            EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();

            if (elpdt != null)
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
 * Inserts the history of a collection of Roles
 * @param coll Collection
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
  private void insertRoleCollection(Collection<Object> coll)
                               throws NEDSSDAOSysException,
                                      NEDSSSystemException
    {

        if (coll == null)
            throw new NEDSSSystemException("Error: insert null RoleDTCollection  into role hist.");

        roleHistDAOImpl.store(coll);
    }
  /**
   * Helps to insert the  Collection<Object>  of participation history
   * @param  coll  the Collection
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
    private void insertParticipationCollection(Collection<Object> coll)
                                        throws NEDSSDAOSysException,
                                               NEDSSSystemException
    {

        if (coll == null)
            throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into pariticipation hist.");

        participationHistDAOImpl.store(coll);
    }
} //end of class
