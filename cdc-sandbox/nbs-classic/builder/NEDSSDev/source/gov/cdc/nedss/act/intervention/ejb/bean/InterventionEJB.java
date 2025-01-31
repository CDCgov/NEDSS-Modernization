/**
* Name:		    EJB class for Intervention Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma
* @version	    1.0
*/
package gov.cdc.nedss.act.intervention.ejb.bean;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.ejb.dao.InterventionHistoryManager;
import gov.cdc.nedss.act.intervention.ejb.dao.InterventionRootDAOImpl;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
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


public class InterventionEJB implements EntityBean
{
	private static final long serialVersionUID = 1L;
	private InterventionVO ivo;
    private InterventionVO oldIntVo;
    private long interventionUID;
    private EntityContext cntx;
    private InterventionRootDAOImpl interventionRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(InterventionEJB.class.getName());

	public InterventionEJB ()
    {
    }

    /**
     * Gets a Intervention object containing all attributes to find a intervention
     */

    public InterventionVO getInterventionVO()
    {
        try {
			return ivo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.getInterventionVO: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /*Sets intervention attributes to the values passed in as attributes
       of the value object. */

    public void setInterventionVO(InterventionVO ivo) throws NEDSSConcurrentDataException
    {
       try
      {
          if (this.ivo.getTheInterventionDT().getVersionCtrlNbr().intValue() !=
                  ivo.getTheInterventionDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          ivo.getTheInterventionDT().setVersionCtrlNbr(new Integer(ivo.getTheInterventionDT().getVersionCtrlNbr().intValue()+1));
          oldIntVo = this.ivo;
          this.ivo = ivo;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setInterventionVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("InterventionEJB.setInterventionVO: NEDSSConcurrentDataException: "+ e.getMessage(), e);
              throw new NEDSSConcurrentDataException(e.getMessage(), e);
          }
          else
          {
              logger.fatal("InterventionEJB.setInterventionVO: Exception: "+ e.getMessage(), e);
              throw new EJBException(e.getMessage(), e);
          }
      }
    }

    public InterventionDT getInterventionInfo()
    {
        try {
			return ivo.getTheInterventionDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.getInterventionInfo: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public EntityContext getEntityContext()
    {
        try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.getEntityContext: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void setEntityContext(EntityContext cntx)
    {
        try {
			this.cntx = cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.setEntityContext: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void unsetEntityContext()
    {
        try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.unsetEntityContext: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public Long ejbCreate(InterventionVO ivo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
logger.debug("EjbCreate is called");

        this.ivo = ivo;

        try
        {
            if(interventionRootDAO == null)
            {
				logger.debug("inside the Intervention EJB interventionRoot ");
                interventionRootDAO = (InterventionRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_ROOT_DAO_CLASS);
            }
            this.ivo.getTheInterventionDT().setVersionCtrlNbr(new Integer(1));
            if(this.ivo.getTheInterventionDT().getSharedInd() == null)
            this.ivo.getTheInterventionDT().setSharedInd("T");
            interventionUID = interventionRootDAO.create(this.ivo);
            this.ivo.getTheInterventionDT().setInterventionUid(new Long(interventionUID));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("InterventionEJB.ejbCreate: NEDSSSystemException: "+ ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
        }

        catch(Exception ex)
        {
         logger.fatal("InterventionEJB.ejbCreate: Exception: "+ ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(), ex);
        }
        return (new Long(interventionUID));
    }

    public void ejbActivate() throws EJBException
    {
    }

    public void ejbPassivate() throws EJBException
    {
        try {
			this.interventionRootDAO = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterventionEJB.ejbPassivate: "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void ejbRemove() throws RemoveException, EJBException
    {
        try
        {
            if(interventionRootDAO == null)
            {
                interventionRootDAO = (InterventionRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_ROOT_DAO_CLASS);
            }
            //insertHistory();
            interventionRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());

        }
        catch(NEDSSSystemException ndsex)
        {
        	logger.fatal("InterventionEJB.getInterventionVO: "+ ndsex.getMessage(), ndsex);
        	throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
        }

    }

    public void ejbStore()
    {
	logger.debug("EjbStore is called");

        try
        {
            if(interventionRootDAO == null)
            {
                interventionRootDAO = (InterventionRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_ROOT_DAO_CLASS);
            }
            if(this.ivo != null && this.ivo.isItDirty())
            {
                try
                {
                  interventionRootDAO.store(this.ivo);

                  this.ivo.setItDirty(false);
                  this.ivo.setItNew(false);
                  //Waiting on the "go ahead" to uncomment
                  insertHistory();
                }
                catch(NEDSSConcurrentDataException ndcex)
                {
                     cntx.setRollbackOnly();
                     logger.fatal("InterventionEJB.ejbStore: Concurrent access is not allowed: " + ndcex.getMessage(), ndcex);
                     throw new NEDSSConcurrentDataException(ndcex.getMessage(), ndcex);
                }
            }
        }
        catch(NEDSSSystemException napex)
        {
        	logger.fatal("InterventionEJB.ejbStore: NEDSSSystemException: " + napex.getMessage(), napex);
            throw new NEDSSSystemException(napex.getMessage(), napex);
        }
        catch(Exception ex)
        {
         logger.fatal("InterventionEJB.ejbStore: Exception: " + ex.getMessage(), ex);
         throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }

    }

    public void ejbLoad() throws EJBException
    {
	logger.debug("EjbLoad is called");
        try
        {
            if(interventionRootDAO == null)
            {
                interventionRootDAO = (InterventionRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_ROOT_DAO_CLASS);
            }
            this.ivo = (InterventionVO)interventionRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.ivo.setItDirty(false);
            this.ivo.setItNew(false);
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("InterventionEJB.ejbLoad: NEDSSSystemException: " + napex.getMessage(), napex);
            throw new NEDSSSystemException(napex.getMessage(), napex);
        }

        catch(Exception ex)
        {
        	logger.fatal("InterventionEJB.ejbLoad: Exception: " + ex.getMessage(), ex);
            throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }
    }

    public void ejbPostCreate(InterventionVO ivo)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        //setInterventionID(((Integer)cntx.getPrimaryKey()).intValue());
    }

    public Long ejbFindByPrimaryKey(Long pk) throws RemoteException,
        FinderException, EJBException, NEDSSSystemException
    {
	logger.debug("EjbFindByPrimaryKey is called");
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                if(interventionRootDAO == null)
                {
                    interventionRootDAO = (InterventionRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_ROOT_DAO_CLASS);
                }
                findPK = interventionRootDAO.findByPrimaryKey(pk.longValue());
				logger.debug("return findpk: " + findPK);
				//see comments in clearcase by JLD
                //this.ivo = (InterventionVO)interventionRootDAO.loadObject(pk.longValue());
            }
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("InterventionEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(), nsex);
        }
        catch(Exception ex)
        {
         logger.fatal("InterventionEJB.ejbFindByPrimaryKey: Exception: " + ex.getMessage(), ex);
         throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }
        return findPK;
    }

    private void insertHistory()throws NEDSSSystemException {
    try {
		//If oldIntVO is not null insert into history
		  if(oldIntVo != null) {
		    logger.debug("InterventionEJB in ejbStore(), InterventionUID in oldInterventionVO : " + oldIntVo.getTheInterventionDT().getInterventionUid().longValue());
		    long oldInterventionUID = oldIntVo.getTheInterventionDT().getInterventionUid().longValue();
		    short versionCtrlNbr = oldIntVo.getTheInterventionDT().getVersionCtrlNbr().shortValue();
		    InterventionHistoryManager interventionHistoryManager = new InterventionHistoryManager(oldInterventionUID, versionCtrlNbr);
		    interventionHistoryManager.store(this.oldIntVo);
		    this.oldIntVo = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("InterventionEJB.insertHistory: " + e.getMessage(), e);
	      throw new javax.ejb.EJBException(e.getMessage(), e);
	}
  }
}// end of InterventionEJB bean class
