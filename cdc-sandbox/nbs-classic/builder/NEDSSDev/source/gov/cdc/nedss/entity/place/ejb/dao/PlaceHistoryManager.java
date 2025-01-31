package gov.cdc.nedss.entity.place.ejb.dao;

import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.association.dao.RoleHistDAOImpl;
import gov.cdc.nedss.entity.entityid.dao.EntityIdHistDAOImpl;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.EntityLocatorParticipationHistDAOImpl;
import gov.cdc.nedss.locator.dao.PhysicalLocatorHistDAOImpl;
import gov.cdc.nedss.locator.dao.PostalLocatorHistDAOImpl;
import gov.cdc.nedss.locator.dao.TeleLocatorHistDAOImpl;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Title: Description: Copyright: Copyright (c) 2002 Company:
 * 
 * @author
 * @version 1.0
 */

public class PlaceHistoryManager
{

    static final LogUtils                         logger         = new LogUtils(PlaceHistoryManager.class.getName());
    private PlaceVO                               placeVOHist    = null;
    private long                                  placeUid;
    private PlaceHistDAOImpl                      placeHistDAOImpl;
    private short                                 versionCtrlNbr = -1;
    private EntityIdHistDAOImpl                   entityIdHistDAOImpl;
    private EntityLocatorParticipationHistDAOImpl entityLocatorParticipationHistDAOImpl;
    private ParticipationHistDAOImpl              participationHistDAOImpl;
    private RoleHistDAOImpl                       roleHistDAOImpl;
    private PhysicalLocatorHistDAOImpl            physicalLocatorHistDAO;
    private PostalLocatorHistDAOImpl              postalLocatorHistDAO;
    private TeleLocatorHistDAOImpl                teleLocatorHistDAO;

    /**
     * Default constructor. To be used only when calling the load(...) method
     */
    public PlaceHistoryManager()
    {
    }

    /**
     * Initializes the class attributes and prepares child objects.
     * 
     * @param placeUid
     *            : long
     * @param versionCtrlNbr
     *            : short
     */
    public PlaceHistoryManager(long placeUid, short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.placeUid = placeUid;
        placeHistDAOImpl = new PlaceHistDAOImpl(placeUid, versionCtrlNbr);
        this.versionCtrlNbr = placeHistDAOImpl.getVersionCtrlNbr();
        logger.debug("EntityGroupHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        entityIdHistDAOImpl = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
        roleHistDAOImpl = new RoleHistDAOImpl(versionCtrlNbr);
    }// end of constructor

    /**
     * Description: This function takes a old PlaceVO and stores them into
     * history tables
     * 
     * @param obj
     *            : Object PlaceVO
     * @return void
     */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        placeVOHist = (PlaceVO) obj;
        if (placeVOHist == null)
            return;
        /**
         * Insert clinical document to history
         */

        if (placeVOHist != null)
        {
            insertPlace(placeVOHist.getThePlaceDT());
            placeVOHist.getThePlaceDT().setItNew(false);
        }// end of if

        if (placeVOHist.getTheEntityIdDTCollection() != null)
        {
            insertEntityIdDTCollection(placeVOHist.getTheEntityIdDTCollection());
        }

        if (placeVOHist.getTheEntityLocatorParticipationDTCollection() != null)
        {
            insertEntityLocatorParticipationDTCollection(placeVOHist.getTheEntityLocatorParticipationDTCollection());
        }

        if (placeVOHist.getTheRoleDTCollection() != null)
        {
            insertRoleCollection(placeVOHist.getTheRoleDTCollection());
        }

        if (placeVOHist.getTheParticipationDTCollection() != null)
        {
            insertParticipationCollection(placeVOHist.getTheParticipationDTCollection());
        }
    }// end of store

    /**
     * Results in the population of a PlaceVO
     * 
     * @param placeUid
     *            : Long
     * @param versionCtrlNbr
     *            : Integer
     * @return PlaceVO
     */
    public PlaceVO load(Long placeUid, Integer versionCtrlNbr) throws NEDSSSystemException, NEDSSSystemException
    {

        logger.info("Starts loadObject() for a entityGroupVO for the history...");

        PlaceVO placeVO = new PlaceVO();
        placeHistDAOImpl = new PlaceHistDAOImpl();
        entityIdHistDAOImpl = new EntityIdHistDAOImpl();
        entityLocatorParticipationHistDAOImpl = new EntityLocatorParticipationHistDAOImpl();
        participationHistDAOImpl = new ParticipationHistDAOImpl();
        roleHistDAOImpl = new RoleHistDAOImpl();
        /**
         * Selects PlaceDT object
         */

        PlaceDT placeDT = placeHistDAOImpl.load(placeUid, versionCtrlNbr);
        placeVO.setThePlaceDT(placeDT);

        // Need to determine if load(...) returns a collection or dt object.
        // Collection<Object> roleColl = roleHistDAOImpl.load(...);
        // placeVO.setTheRoleDTCollection(roleColl);
        // Need to determine if load(...) returns a collection or dt object.
        // Collection<Object> participationColl =
        // participationHistDAOImpl.load(...);
        // placeVO.setTheParticipationDTCollection(participationColl);

        Collection<Object> idColl = entityIdHistDAOImpl.load(placeUid, versionCtrlNbr);
        placeVO.setTheEntityIdDTCollection(idColl);

        Collection<Object> elpColl = entityLocatorParticipationHistDAOImpl.load(placeUid, versionCtrlNbr);
        ArrayList<Object> newElpColl = new ArrayList<Object>();

        for (Iterator<Object> anIterator = elpColl.iterator(); anIterator.hasNext();)
        {
            EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT) anIterator.next();
            if (elpdt != null)
            {
                Long locatorUid = elpdt.getLocatorUid();
                physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl();
                postalLocatorHistDAO = new PostalLocatorHistDAOImpl();
                teleLocatorHistDAO = new TeleLocatorHistDAOImpl();
                Collection<Object> physicalLocColl = physicalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
                Collection<Object> postalLocColl = postalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
                Collection<Object> teleLocColl = teleLocatorHistDAO.load(locatorUid, versionCtrlNbr);

                for (Iterator<Object> it = physicalLocColl.iterator(); it.hasNext();)
                {
                    PhysicalLocatorDT pldt = (PhysicalLocatorDT) it.next();
                    elpdt.setThePhysicalLocatorDT(pldt);
                }
                for (Iterator<Object> it = postalLocColl.iterator(); it.hasNext();)
                {
                    PostalLocatorDT pldt = (PostalLocatorDT) it.next();
                    elpdt.setThePostalLocatorDT(pldt);
                }
                for (Iterator<Object> it = teleLocColl.iterator(); it.hasNext();)
                {
                    TeleLocatorDT pldt = (TeleLocatorDT) it.next();
                    elpdt.setTheTeleLocatorDT(pldt);
                }
            }
            newElpColl.add(elpdt);

        }
        placeVO.setTheEntityLocatorParticipationDTCollection(elpColl);

        logger.info("Done loadObject() for a PlaceVO for place history - return: " + placeVO);
        return placeVO;
    }// end of load

    /**
     * Results in the insertion of a place record into history
     * 
     * @param dt
     *            : PlaceDT
     * @return void
     */
    private void insertPlace(PlaceDT dt) throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (dt == null)
            throw new NEDSSSystemException("Error: insert null PlaceDt into place Hist.");
        placeHistDAOImpl.store(dt);
    }// end of insertPlace

    /**
     * Results in the insertion of 0 --> 0.* entity id records into history
     * 
     * @param coll
     *            : Collection
     * @return void
     */
    private void insertEntityIdDTCollection(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (coll == null)
            throw new NEDSSSystemException("Error: insert null EntityIdDTCollection  into entity id hist.");
        entityIdHistDAOImpl.store(coll);
    }

    /**
     * Results in the insertion of the physical, postal, and tele locator
     * records into history
     * 
     * @param coll
     *            : Collection
     * @return void
     */
    private void insertEntityLocatorParticipationDTCollection(Collection<Object> coll) throws NEDSSDAOSysException,
            NEDSSSystemException
    {
        if (coll == null)
            throw new NEDSSSystemException(
                    "Error: insert null EntityLocatorParticipationDTCollection  into entity locator participation hist.");
        physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl(versionCtrlNbr);
        postalLocatorHistDAO = new PostalLocatorHistDAOImpl(versionCtrlNbr);
        teleLocatorHistDAO = new TeleLocatorHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationHistDAOImpl.store(coll);
        for (Iterator<Object> anIterator = coll.iterator(); anIterator.hasNext();)
        {
            EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT) anIterator.next();
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
     * Results in the insertion of Role records into history
     * 
     * @param coll
     *            : Collection
     * @return void
     */
    private void insertRoleCollection(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (coll == null)
            throw new NEDSSSystemException("Error: insert null RoleDTCollection  into role hist.");
        roleHistDAOImpl.store(coll);
    }

    /**
     * Results in multiple Participation records being inserted to history
     * 
     * @param coll
     *            : Collection
     * @return void
     */
    private void insertParticipationCollection(Collection<Object> coll) throws NEDSSDAOSysException,
            NEDSSSystemException
    {
        if (coll == null)
            throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into pariticipation hist.");
        participationHistDAOImpl.store(coll);
    }
}// end of PlaceHistoryManager
