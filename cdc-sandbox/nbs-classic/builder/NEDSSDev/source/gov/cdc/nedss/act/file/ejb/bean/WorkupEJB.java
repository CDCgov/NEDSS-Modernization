/**
* Name:		EJB class for Workup Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.file.ejb.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.RemoveException;

import java.sql.SQLException;

import javax.ejb.FinderException;











import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.file.vo.*;
import gov.cdc.nedss.act.file.dt.*;
import gov.cdc.nedss.act.file.ejb.dao.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

public class WorkupEJB implements EntityBean
{
	private static final long serialVersionUID = 1L;
	
    private WorkupVO pvo;
    private long workupUID;
    private EntityContext cntx;
    private  WorkupRootDAOImpl workupRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(WorkupEJB.class.getName());


    public WorkupEJB ()
    {
    }

    /**
     * Gets a Workup object containing all attributes to find a workup
     */

    public WorkupVO getWorkupVO()
    {
        try {
			return pvo;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.getWorkupVO: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    /*Sets workup attributes to the values passed in as attributes
       of the value object. */

    public void setWorkupVO(WorkupVO pvo)
    {
        try {
			this.pvo = pvo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.setWorkupVO: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public WorkupDT getWorkupInfo()
    {
        try {
			return pvo.getTheWorkupDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.getWorkupInfo: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public Collection<Object>  getWorkupIDs()
    {
        try {
			return pvo.getTheActivityIdDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.getWorkupIDs: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public Collection<Object>  getLocators()
    {
        try {
			return pvo.getTheActivityLocatorParticipationDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.getLocators: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public EntityContext getEntityContext()
    {
        try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.getEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void setEntityContext(EntityContext cntx)
    {
        try {
			this.cntx = cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.setEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void unsetEntityContext()
    {
        try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public Long ejbCreate(WorkupVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
logger.debug("EjbCreate is called");

        this.pvo = pvo;

        try
        {
            if(workupRootDAO == null)
            {
                workupRootDAO = (WorkupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_ROOT_DAO_CLASS);
            }
            workupUID = workupRootDAO.create(this.pvo);
            this.pvo.getTheWorkupDT().setWorkupUid(new Long(workupUID));
        }
        catch(NEDSSSystemException ndsex)
        {
        	logger.fatal("WorkupEJB.ejbCreate: " + ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage(), ndsex);
        }
        catch(Exception ex)
        {
         logger.fatal("WorkupEJB.ejbCreate: " + ex.getMessage(), ex);
		 throw new EJBException(ex.getMessage(), ex);
        }
        return (new Long(workupUID));
    }

    public void ejbActivate() throws EJBException
    {
    }

    public void ejbPassivate() throws EJBException
    {
        try {
			this.workupRootDAO = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("WorkupEJB.ejbPassivate: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    public void ejbRemove() throws RemoveException, EJBException
    {
        try
        {
            if(workupRootDAO == null)
            {
                workupRootDAO = (WorkupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_ROOT_DAO_CLASS);
            }
            workupRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());
        }
        catch(NEDSSSystemException ndsex)
        {
        	logger.fatal("WorkupEJB.ejbRemove: " + ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.getMessage(), ndsex);
        }
    }

    public void ejbStore() throws EJBException
    {
logger.debug("EjbStore is called");

        try
        {
            if(workupRootDAO == null)
            {
                workupRootDAO = (WorkupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_ROOT_DAO_CLASS);
            }
            workupRootDAO.store(this.pvo);
        }
        catch(NEDSSSystemException napex)
        {
            logger.fatal("WorkupEJB.ejbStore: NEDSSSystemException: " + napex.getMessage(), napex);
			throw new EJBException(napex.getMessage(), napex);
        }
        catch(Exception ex)
        {
         logger.fatal("WorkupEJB.ejbStore: Exception: " + ex.getMessage(), ex);
			throw new EJBException(ex.getMessage(), ex);
        }
    }

    public void ejbLoad() throws EJBException
    {
    	logger.debug("EjbLoad is called");
        try
        {
            if(workupRootDAO == null)
            {
                workupRootDAO = (WorkupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_ROOT_DAO_CLASS);
            }
            this.pvo = (WorkupVO)workupRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
        }
        catch(NEDSSSystemException npdaex)
        {
        	logger.fatal("WorkupEJB.ejbLoad: NEDSSSystemException: " + npdaex.getMessage(), npdaex);
			throw new EJBException(npdaex.getMessage(), npdaex);
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupEJB.ejbLoad: Exception: " + ex.getMessage(), ex);
			throw new EJBException(ex.getMessage(), ex);
        }
    }

    public void ejbPostCreate(WorkupVO pvo)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
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
                if(workupRootDAO == null)
                {
                    workupRootDAO = (WorkupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.WORKUP_ROOT_DAO_CLASS);
                }
                findPK = workupRootDAO.findByPrimaryKey(pk.longValue());
logger.debug("return findpk: " + findPK);
				//see comments in clearcase by JLD
            }
        }
       catch(NEDSSSystemException nsex)
        {
            logger.fatal("WorkupEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
			throw new EJBException(nsex.getMessage(), nsex);
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupEJB.ejbFindByPrimaryKey: Exception: " + ex.getMessage(), ex);
			throw new EJBException(ex.getMessage(), ex);
        }
        return findPK;
    }

}// end of WorkupEJB bean class
