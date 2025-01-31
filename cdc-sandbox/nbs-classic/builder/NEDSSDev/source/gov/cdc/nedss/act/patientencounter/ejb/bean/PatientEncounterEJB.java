package gov.cdc.nedss.act.patientencounter.ejb.bean;

import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.ejb.dao.PatientEncounterRootDAOImpl;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;


/**
* Name:		EJB class for PatientEncounter Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

public class PatientEncounterEJB
    implements EntityBean {
	private static final long serialVersionUID = 1L;
    private PatientEncounterVO patientEncounterVO;
    private PatientEncounterVO oldPEVo;
    private long patientencounterUID;
    private EntityContext cntx;
    private PatientEncounterRootDAOImpl patientencounterRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(PatientEncounterEJB.class.getName());

    public PatientEncounterEJB() {
    }

    /**
     *
     * @return PatientEncounterVO
     */
    public PatientEncounterVO getPatientEncounterVO() {

        return patientEncounterVO;
    }

    /*Sets patientencounter attributes to the values passed in as attributes
       of the value object. */

    /**
     *
     * @param patientEncounterVO
     * @throws NEDSSConcurrentDataException
     */
    public void setPatientEncounterVO(PatientEncounterVO patientEncounterVO)
                               throws NEDSSConcurrentDataException {

        try {

            if (this.patientEncounterVO.getThePatientEncounterDT().getVersionCtrlNbr()
                       .intValue() != patientEncounterVO.getThePatientEncounterDT()
                  .getVersionCtrlNbr().intValue()) {
                logger.error("Throwing NEDSSConcurrentDataException");
                throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }

            patientEncounterVO.getThePatientEncounterDT().setVersionCtrlNbr(new Integer(patientEncounterVO
                  .getThePatientEncounterDT().getVersionCtrlNbr().intValue() + 1));
            oldPEVo = this.patientEncounterVO;
            this.patientEncounterVO = patientEncounterVO;
        } catch (Exception e) {
            logger.debug(e.toString() + " : setpatientEncounterVO dataconcurrency catch: " + e.getClass());
            logger.debug("Exception string is: " + e.toString());

            if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
                logger.fatal("Throwing NEDSSConcurrentDataException");
                throw new NEDSSConcurrentDataException(e.getMessage(),e);
            } else {
                logger.fatal("Throwing generic Exception");
                throw new EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     *
     * @return PatientEncounterDT
     */
    public PatientEncounterDT getPatientEncounterInfo() {

        return patientEncounterVO.getThePatientEncounterDT();
    }

    /**
     *
     * @return Collection
     */
    public Collection<Object> getPatientEncounterIDs() {

        return patientEncounterVO.getTheActivityIdDTCollection();
    }

    /**
     *
     * @return Collection
     */
    public Collection<Object> getLocators() {

        return patientEncounterVO.getTheActivityLocatorParticipationDTCollection();
    }

    /**
     *
     * @return EntityContext
     */
    public EntityContext getEntityContext() {

        return cntx;
    }

    /**
     *
     * @param cntx
     */
    public void setEntityContext(EntityContext cntx) {
        this.cntx = cntx;
    }

    /**
     *
     **/
    public void unsetEntityContext() {
        cntx = null;
    }

    /**
     *
     * @param patientEncounterVO
     * @return
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Long ejbCreate(PatientEncounterVO patientEncounterVO)
                   throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException {
        logger.debug("EjbCreate is called");
        this.patientEncounterVO = patientEncounterVO;

        try {

            if (patientencounterRootDAO == null) {
                patientencounterRootDAO = (PatientEncounterRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_ROOT_DAO_CLASS);
            }

            this.patientEncounterVO.getThePatientEncounterDT().setVersionCtrlNbr(new Integer(1));

            if (this.patientEncounterVO.getThePatientEncounterDT().getSharedInd() == null)
                this.patientEncounterVO.getThePatientEncounterDT().setSharedInd("T");

            patientencounterUID = patientencounterRootDAO.create(this.patientEncounterVO);
            this.patientEncounterVO.getThePatientEncounterDT().setPatientEncounterUid(new Long(patientencounterUID));
        } catch (NEDSSSystemException ndsex) {
            logger.fatal("NEDSSSystemException occured: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
         catch (Exception ex) {
            logger.fatal("Exception occured: "+ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(),ex);
        }

        return (new Long(patientencounterUID));
    }

    /**
     *
     * @throws EJBException
     */
    public void ejbActivate()
                     throws EJBException {
    }

    /**
     *
     * @throws EJBException
     */
    public void ejbPassivate()
                      throws EJBException {
        this.patientencounterRootDAO = null;
    }

    /**
     *
     * @throws RemoveException
     * @throws EJBException
     */
    public void ejbRemove()
                   throws RemoveException, EJBException {

        try {

            if (patientencounterRootDAO == null) {
                patientencounterRootDAO = (PatientEncounterRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_ROOT_DAO_CLASS);
            }

            patientencounterRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());
        } catch (NEDSSSystemException ndsex) {
            logger.fatal("NEDSSSystemException occured: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
    }

    /**
     *
     * @throws EJBException
     */
    public void ejbStore()
                  throws EJBException {
        logger.debug("EjbStore is called");

        try {

            if (patientencounterRootDAO == null) {
                patientencounterRootDAO = (PatientEncounterRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_ROOT_DAO_CLASS);
            }

            if (this.patientEncounterVO != null && this.patientEncounterVO.isItDirty()) {

                try {
                    patientencounterRootDAO.store(this.patientEncounterVO);
                    this.patientEncounterVO.setItDirty(false);
                    this.patientEncounterVO.setItNew(false);

                    //Waiting on the "go ahead" to uncomment
                    //insertHistory();Not required as of 06/27/02
                } catch (NEDSSConcurrentDataException ndcex) {
                    logger.fatal("Got into concurrent exception in interventionEJB"+ndcex.getMessage(),ndcex);

                    //cntx.setRollbackOnly();
                    throw new NEDSSSystemException("NEDSSConcurrentDataException in interventionEJB"+ndcex.getMessage(),ndcex);
                }

                //Waiting on the "go ahead" to uncomment
                //If oldPEVO is not null insert into history

                /*if(oldPEVo != null) {
                  logger.debug("PatientEncounterEJB in ejbStore(), PatientEncounterUID in oldPatientEncounterVO : " + oldPEVo.getThePatientEncounterDT().getPatientEncounterUid().longValue());
                  long oldPEUID = oldPEVo.getThePatientEncounterDT().getPatientEncounterUid().longValue();
                  PatientEncounterHistoryManager patientEncounterHistoryManager = new PatientEncounterHistoryManager(oldPEUID);
                  patientEncounterHistoryManager.store(this.oldPEVo);
                  this.oldPEVo = null;
                }*/
            }
        } catch (NEDSSSystemException napex) {
            logger.fatal("NEDSSSystemException occured: "+napex.getMessage(), napex);
            throw new EJBException(napex.getMessage(),napex);
        }
         catch (Exception ex) {
            logger.fatal("Exception occured: "+ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(),ex);
        }
    }

    /**
     *
     * @throws EJBException
     */
    public void ejbLoad()
                 throws EJBException {
        logger.debug("EjbLoad is called");

        try {

            if (patientencounterRootDAO == null) {
                patientencounterRootDAO = (PatientEncounterRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_ROOT_DAO_CLASS);
            }

            this.patientEncounterVO = (PatientEncounterVO)patientencounterRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.patientEncounterVO.setItDirty(false);
            this.patientEncounterVO.setItNew(false);
        } catch (NEDSSSystemException npdaex) {
            logger.fatal("NEDSSSystemException occured: "+npdaex.getMessage(), npdaex);
            throw new EJBException(npdaex.getMessage(),npdaex);
        }
         catch (Exception ex) {
            logger.fatal("Exception occured: "+ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(),ex);
        }
    }

    /**
     *
     * @param patientEncounterVO
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public void ejbPostCreate(PatientEncounterVO patientEncounterVO)
                       throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException {

        //setPatientEncounterID(((Integer)cntx.getPrimaryKey()).intValue());
    }

    /**
     *
     * @param pk
     * @return
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Long ejbFindByPrimaryKey(Long pk)
                             throws RemoteException, FinderException, EJBException, NEDSSSystemException {
        logger.debug("EjbFindByPrimaryKey is called");

        Long findPK = null;

        try {

            if (pk != null) {

                if (patientencounterRootDAO == null) {
                    patientencounterRootDAO = (PatientEncounterRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PATIENTENCOUNTER_ROOT_DAO_CLASS);
                }

                findPK = patientencounterRootDAO.findByPrimaryKey(pk.longValue());
                logger.debug("return findpk: " + findPK);
                //see comments by JLD
                //this.patientEncounterVO = (PatientEncounterVO)patientencounterRootDAO.loadObject(pk.longValue());
            }
        } catch (NEDSSSystemException nsex) {
            logger.fatal("NEDSSSystemException in find by primary key"+nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
         catch (Exception ex) {
            logger.fatal("Exception in find by primary key"+ex.getMessage(), ex);
            ;
            throw new EJBException(ex.getMessage(),ex);
        }

        return findPK;
    }

    
} // end of PatientEncounterEJB bean class  