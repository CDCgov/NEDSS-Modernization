/**
* Name:             EJB class for Treatment Enterprise Bean
* Description:      The bean is an entity bean
* Copyright:        Copyright (c) 2001
* Company:          Computer Sciences Corporation
* @author           Nedss Development Team
* @version          1.1
*/

package gov.cdc.nedss.act.treatment.ejb.bean;

import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.act.treatment.ejb.dao.TreatmentHistoryManager;
import gov.cdc.nedss.act.treatment.ejb.dao.TreatmentRootDAOImpl;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;



public class TreatmentEJB implements EntityBean {
	private static final long serialVersionUID = 1L;
    private TreatmentVO tvo;
    private TreatmentVO oldTreatVo;
    private long treatmentUID;
    private EntityContext cntx;
    private TreatmentRootDAOImpl treatmentRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(TreatmentEJB.class.getName());

    public TreatmentEJB ()
    {
    }

    /**
     * Gets a Treatment object containing all attributes to find a Treatment
     */

    public TreatmentVO getTreatmentVO()
    {
        return tvo;
    } // end getTreatmentVO

    /*Sets treatment attributes to the values passed in as attributes
       of the value object. */

    public void setTreatmentVO(TreatmentVO tvo) throws NEDSSConcurrentDataException
    {
      logger.debug("\n\nNow setting TreatmentVO inside TreatmentEJB\n\n");
       try
      {
          if (this.tvo.getTheTreatmentDT().getVersionCtrlNbr().intValue() !=
                  tvo.getTheTreatmentDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          tvo.getTheTreatmentDT().setVersionCtrlNbr(new Integer(tvo.getTheTreatmentDT().getVersionCtrlNbr().intValue()+1));
          oldTreatVo = this.tvo;
          this.tvo = tvo;
          logger.debug("\n\n--- TreatmentEJB.TreatmentVO.isItNew: " + tvo.isItNew());
          logger.debug("\n\n--- TreatmentEJB.TreatmentVO.isItDirty: " + tvo.isItDirty());
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setTreatmentVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("Throwing NEDSSConcurrentDataException"+e.getMessage(),e);
              throw new NEDSSConcurrentDataException( e.getMessage(),e);
          }
          else
          {
              logger.fatal("Throwing generic Exception"+e.getMessage(),e);
              throw new EJBException(e.getMessage(),e);
          }
      }
    } // end setTreatmentVO

    public TreatmentDT getTreatmentInfo()
    {
        return tvo.getTheTreatmentDT();
    }

    public EntityContext getEntityContext()
    {
        return cntx;
    }

    public void setEntityContext(EntityContext cntx)
    {
        this.cntx = cntx;
    }

    public void unsetEntityContext()
    {
        cntx = null;
    }

    public Long ejbCreate(TreatmentVO tvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        logger.debug("EjbCreate is called");

        this.tvo = tvo;

        try
        {
            if(treatmentRootDAO == null)
            {
                logger.debug("inside the Treatment EJB treatmentRoot ");
                treatmentRootDAO = (TreatmentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ROOT_DAO_CLASS);
            }
            this.tvo.getTheTreatmentDT().setVersionCtrlNbr(new Integer(1));
            if(this.tvo.getTheTreatmentDT().getSharedInd() == null)
            this.tvo.getTheTreatmentDT().setSharedInd("T");
            treatmentUID = treatmentRootDAO.create(this.tvo);
            this.tvo.getTheTreatmentDT().setTreatmentUid(new Long(treatmentUID));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("Exception in TreatmentEJB.ejbCreate()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }

        catch(Exception ex)
        {
         logger.fatal(ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return (new Long(treatmentUID));
    } //end ejbCreate

    public void ejbActivate() throws EJBException
    {
    }

    public void ejbPassivate() throws EJBException
    {
        this.treatmentRootDAO = null;
    }

    public void ejbRemove() throws RemoveException, EJBException
    {
        try
        {
            if(treatmentRootDAO == null)
            {
                treatmentRootDAO = (TreatmentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ROOT_DAO_CLASS);
            }
            //insertHistory();
            treatmentRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());

        }
        catch(NEDSSSystemException ndsex)
        {

            logger.fatal(ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }

    }// end ejbRemove

    public void ejbStore()
    {
	logger.debug("Treatment ejbStore is called");

        try
        {
            if(treatmentRootDAO == null)
            {
                treatmentRootDAO = (TreatmentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ROOT_DAO_CLASS);
            }
            logger.debug("About to store if tvo is dirty, isItDirty: " + this.tvo.isItDirty());
            if(this.tvo != null && this.tvo.isItDirty())
            {
                try
                {
                  treatmentRootDAO.store(this.tvo);

                  this.tvo.setItDirty(false);
                  this.tvo.setItNew(false);
                  /** @todo insertHistory needed? */
                  insertHistory();
                }
                catch(NEDSSConcurrentDataException ndcex)
                {
                    logger.fatal("NEDSSConcurrentDataException in treatmentEJB"+ndcex.getMessage(),ndcex);
                    cntx.setRollbackOnly();
                     throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
                }
            }
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("Exception in Treatment.ejbStore"+napex.getMessage(), napex);
            throw new EJBException(napex.getMessage(),napex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception in Treatment.ejbStore"+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }

    }// end ejbStore

    public void ejbLoad() throws EJBException
    {
	logger.debug("EjbLoad is called");
        try
        {
            if(treatmentRootDAO == null)
            {
                treatmentRootDAO = (TreatmentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ROOT_DAO_CLASS);
            }
            this.tvo = (TreatmentVO)treatmentRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.tvo.setItDirty(false);
            this.tvo.setItNew(false);
        }
        catch(NEDSSSystemException npdaex)
        {
            logger.fatal("NEDSSSystemException in Treatment.ejbLoad"+npdaex.getMessage(), npdaex);
            throw new EJBException(npdaex.getMessage(),npdaex);
        }

        catch(Exception ex)
        {
         logger.fatal("Exception in Treatment.ejbLoad"+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
    }//end ejbLoad

    public void ejbPostCreate(TreatmentVO ivo)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        //setTreatmentID(((Integer)cntx.getPrimaryKey()).intValue());
    } //end ejbPostCreate

    public Long ejbFindByPrimaryKey(Long pk) throws RemoteException,
        FinderException, EJBException, NEDSSSystemException
    {
	logger.debug("Treatment EjbFindByPrimaryKey is called");
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                if(treatmentRootDAO == null)
                {
                    treatmentRootDAO = (TreatmentRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.TREATMENT_ROOT_DAO_CLASS);
                }
                findPK = treatmentRootDAO.findByPrimaryKey(pk.longValue());
				logger.debug("return findpk: " + findPK);


            }
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("NEDSSSystemException in find by primary key"+nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
        catch(Exception ex)
        {
         logger.fatal("Exception in Treatment.ejbFindByPrimaryKey"+ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(),ex);
        }
        return findPK;
    } //end ejbFindByPrimaryKey

    private void insertHistory()throws NEDSSSystemException {
    try {
		//If oldIntVO is not null insert into history
		  if(oldTreatVo != null) {
		    logger.debug("TreatmentEJB in ejbStore(), TreatmentUID in oldTreatmentVO : " + oldTreatVo.getTheTreatmentDT().getTreatmentUid().longValue());
		    long oldTreatmentUID = oldTreatVo.getTheTreatmentDT().getTreatmentUid().longValue();
		    short versionCtrlNbr = oldTreatVo.getTheTreatmentDT().getVersionCtrlNbr().shortValue();
		    TreatmentHistoryManager treatmentHistoryManager = new TreatmentHistoryManager(oldTreatmentUID, versionCtrlNbr);
		    treatmentHistoryManager.store(this.oldTreatVo);
		    this.oldTreatVo = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 logger.fatal(e.getMessage(), e);
         throw new EJBException(e.getMessage(),e);
	}
  }

}// end of TreatmentEJB bean class
