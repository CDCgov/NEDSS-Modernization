/**
 * Name:		    EJB class for Interview Enterprise Bean
 * Description:	The bean is an entity bean
 * Copyright:	Copyright (c) 2013
 * Company: 	    Leidos
 */
package gov.cdc.nedss.act.interview.ejb.bean;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.RemoveException;
import javax.ejb.FinderException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.interview.dt.*;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewRootDAOImpl;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.systemservice.util.*;

public class InterviewEJB implements EntityBean {
	private static final long serialVersionUID = 1L;
	private InterviewVO ivo;
	private InterviewVO oldIvo;
	private long interviewUID;
	private EntityContext cntx;
	private InterviewRootDAOImpl interviewRootDAO = null;

	// For logging
	static final LogUtils logger = new LogUtils(InterviewEJB.class.getName());

	public InterviewEJB() {
	}

	/**
	 * Gets a Interview object containing all attributes to find a interview
	 */

	public InterviewVO getInterviewVO() {
		return ivo;
	}

	/*
	 * Sets interview attributes to the values passed in as attributes of the
	 * value object.
	 */

	public void setInterviewVO(InterviewVO ivo)
			throws NEDSSConcurrentDataException {
		try {
			if (this.ivo.getTheInterviewDT().getVersionCtrlNbr().intValue() != ivo
					.getTheInterviewDT().getVersionCtrlNbr().intValue()) {
				logger.error("Throwing NEDSSConcurrentDataException");
				throw new NEDSSConcurrentDataException(
						"NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
			}
			ivo.getTheInterviewDT().setVersionCtrlNbr(
					new Integer(ivo.getTheInterviewDT().getVersionCtrlNbr()
							.intValue() + 1));
			this.ivo = ivo;
		} catch (Exception e) {
			
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("InterviewEJB.setInterviewVO: Concurrent access is not allowed: " + e.getMessage(), e);
			      throw new NEDSSConcurrentDataException(e.getMessage(), e);
			} else {
				logger.fatal("InterviewEJB.setInterviewVO: Exception: " + e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);
			}
		}
	}

	public InterviewDT getInterviewInfo() {
		try {
			return ivo.getTheInterviewDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterviewEJB.setInterviewVO: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public EntityContext getEntityContext() {
		try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterviewEJB.getEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public void setEntityContext(EntityContext cntx) {
		try {
			this.cntx = cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterviewEJB.setEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public void unsetEntityContext() {
		try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterviewEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public Long ejbCreate(InterviewVO ivo) throws RemoteException,
			CreateException, DuplicateKeyException, EJBException,
			NEDSSSystemException {
		logger.debug("EjbCreate is called");

		this.ivo = ivo;

		try {
			if (interviewRootDAO == null) {
				logger.debug("inside the Interview EJB interviewRoot ");
				interviewRootDAO = (InterviewRootDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ROOT_DAO_CLASS);
			}
			this.ivo.getTheInterviewDT().setVersionCtrlNbr(new Integer(1));
			interviewUID = interviewRootDAO.create(this.ivo);
			this.ivo.getTheInterviewDT()
					.setInterviewUid(new Long(interviewUID));
		} catch (NEDSSSystemException ndsex) {
			logger.fatal("InterviewEJB.setInterviewVO: NEDSSSystemException: " + ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
		}

		catch (Exception ex) {
			logger.fatal("InterviewEJB.setInterviewVO: Exception: " + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		}
		return (new Long(interviewUID));
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
		try {
			this.interviewRootDAO = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("InterviewEJB.ejbPassivate: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}

	public void ejbRemove() throws RemoveException, EJBException {
		try {
			if (interviewRootDAO == null) {
				interviewRootDAO = (InterviewRootDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ROOT_DAO_CLASS);
			}
			// insertHistory();
			interviewRootDAO.remove(((Long) cntx.getPrimaryKey()).longValue());

		} catch (NEDSSSystemException ndsex) {
			logger.fatal("InterviewEJB.ejbRemove: " + ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
		}

	}

	public void ejbStore() {
		logger.debug("EjbStore is called");

		try {
			if (interviewRootDAO == null) {
				interviewRootDAO = (InterviewRootDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ROOT_DAO_CLASS);
			}
			if (this.ivo != null && this.ivo.isItDirty()) {
				try {
					interviewRootDAO.store(this.ivo);

					this.ivo.setItDirty(false);
					this.ivo.setItNew(false);
					interviewRootDAO.storeHistory(oldIvo);
				} catch (NEDSSConcurrentDataException ndcex) {
					cntx.setRollbackOnly();
					logger.fatal("InterviewEJB.setInterviewVO: Concurrent access is not allowed: " + ndcex.getMessage(), ndcex);
				      throw new NEDSSConcurrentDataException(ndcex.getMessage(), ndcex);
				}
			}
		} catch (NEDSSSystemException napex) {
			logger.fatal("InterviewEJB.setInterviewVO: NEDSSSystemException: " + napex.getMessage(), napex);
			throw new NEDSSSystemException(napex.getMessage(), napex);
		} catch (Exception ex) {
			logger.fatal("InterviewEJB.setInterviewVO: Exception: " + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		}

	}

	public void ejbLoad() throws EJBException {
		logger.debug("EjbLoad is called");
		try {
			if (interviewRootDAO == null) {
				interviewRootDAO = (InterviewRootDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ROOT_DAO_CLASS);
			}
			this.ivo = (InterviewVO) interviewRootDAO.loadObject(((Long) cntx
					.getPrimaryKey()).longValue());
			this.oldIvo=this.ivo;
			this.ivo.setItDirty(false);
			this.ivo.setItNew(false);
		} catch (NEDSSSystemException npdaex) {
			logger.fatal("InterviewEJB.ejbLoad: NEDSSSystemException: " + npdaex.getMessage(), npdaex);
			throw new NEDSSSystemException(npdaex.getMessage(), npdaex);
		}

		catch (Exception ex) {
			logger.fatal("InterviewEJB.ejbLoad: Exception: " + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		}
	}

	public void ejbPostCreate(InterviewVO ivo) throws RemoteException,
			CreateException, DuplicateKeyException, EJBException,
			NEDSSSystemException {
	}

	public Long ejbFindByPrimaryKey(Long pk) throws RemoteException,
			FinderException, EJBException, NEDSSSystemException {
		logger.debug("EjbFindByPrimaryKey is called");
		Long findPK = null;
		try {
			if (pk != null) {
				if (interviewRootDAO == null) {
					interviewRootDAO = (InterviewRootDAOImpl) NEDSSDAOFactory
							.getDAO(JNDINames.INTERVIEW_ROOT_DAO_CLASS);
				}
				findPK = interviewRootDAO.findByPrimaryKey(pk.longValue());
				logger.debug("return findpk: " + findPK);
			}
		} catch (NEDSSSystemException nsex) {
			logger.fatal("InterviewEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		} catch (Exception ex) {
			logger.fatal("InterviewEJB.ejbFindByPrimaryKey: Exception: " + ex.getMessage(), ex);
			throw new javax.ejb.EJBException(ex.getMessage(), ex);
		}
		return findPK;
	}

}// end of InterviewEJB bean class
