/**
 * Name:		    InterviewRootDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for the
 *               Interview value object in the Interview entity bean.
 *               This class encapsulates all the JDBC calls made by the InterviewEJB
 *               for a Interview object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of InterviewEJB is
 *               implemented here.
 * Copyright:	Copyright (c) 2013
 * Company: 	Leidos
 * @version	    1.0
 */

package gov.cdc.nedss.act.interview.ejb.dao;

import gov.cdc.nedss.act.interview.dt.InterviewAnswerDT;
import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;

public class InterviewRootDAOImpl extends BMPBase {
	// For logging
	static final LogUtils logger = new LogUtils(
			InterviewRootDAOImpl.class.getName());

	private InterviewVO ivo = null;
	private long interviewUID;
	private InterviewDAOImpl interviewDAO = null;
	private InterviewAnswerDAOImpl interviewAnswerDAO = null;

	public InterviewRootDAOImpl() {
	}

	public long create(Object obj) throws NEDSSSystemException {
		try{
			this.ivo = (InterviewVO) obj;
			if (this.ivo != null)
				interviewUID = insertInterview(this.ivo);
			logger.debug("Interview UID = " + interviewUID);
			(this.ivo.getTheInterviewDT()).setInterviewUid(new Long(interviewUID));
			insertInterviewAnswerColl(ivo);
			return interviewUID;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void store(Object obj) throws NEDSSConcurrentDataException, NEDSSSystemException {
		try{
			this.ivo = (InterviewVO) obj;
			if (this.ivo.getTheInterviewDT() != null
					&& this.ivo.getTheInterviewDT().isItNew()) {
				insertInterview(this.ivo);
				this.ivo.getTheInterviewDT().setItNew(false);
				this.ivo.getTheInterviewDT().setItDirty(false);
			} else if (this.ivo.getTheInterviewDT() != null
					&& this.ivo.getTheInterviewDT().isItDirty()) {
				updateInterview(this.ivo);
				this.ivo.getTheInterviewDT().setItDirty(false);
				this.ivo.getTheInterviewDT().setItNew(false);
			}
		}catch(NEDSSConcurrentDataException ex){
			logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void storeHistory(Object obj) throws NEDSSConcurrentDataException, NEDSSSystemException {
		this.ivo = (InterviewVO) obj;
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			interviewDAO.insertInterviewHist(ivo.getTheInterviewDT());
		} catch (NEDSSDAOSysException ndsex) {
			logger.fatal("Fails updateInterview()"+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.toString());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("Fails updateInterview()"+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.toString());
		}
	}

	public void remove(long interviewUID) throws NEDSSSystemException {
		try{
			removeInterview(interviewUID);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Object loadObject(long interviewUID) throws NEDSSSystemException {
		try{
			this.ivo = new InterviewVO();
	
	    	InterviewDT pDT = selectInterview(interviewUID);
			this.ivo.setTheInterviewDT(pDT);
	
			this.ivo.setItNew(false);
			this.ivo.setItDirty(false);
			return this.ivo;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long findByPrimaryKey(long interviewUID)
			throws NEDSSSystemException {
		try{
			Long interviewPK = findInterview(interviewUID);
			logger.debug("Done find by primarykey!");
			return interviewPK;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private long insertInterview(InterviewVO ivo) throws NEDSSSystemException {
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			logger.debug("Interview DT = " + ivo.getTheInterviewDT());
			interviewUID = interviewDAO.create(ivo.getTheInterviewDT());
			logger.debug("Interview root uid = " + interviewUID);
			ivo.getTheInterviewDT().setInterviewUid(new Long(interviewUID));
			insertInterviewAnswerColl(ivo);
		} catch (NEDSSDAOSysException ndsex) {
			logger.fatal("Fails insertInterview()"+ndsex.getMessage(), ndsex);
			throw new EJBException(ndsex.toString());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("Fails insertInterview()"+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.toString());
		}
		return interviewUID;
	}

	private void insertInterviewAnswerColl(InterviewVO ivo) {
		Collection<Object> interviewAnswerDTCollection = new ArrayList<Object> ();
		logger.debug("Interview root before inserting interview answer collection." );
		/* changed to PamVO.getAnswerMapDT
		  if(ivo.getInterviewAnswerDTMap() != null) {
	
			interviewAnswerDTCollection= ivo.getInterviewAnswerDTMap().values();
		
			if (interviewAnswerDAO == null) {
				interviewAnswerDAO = (InterviewAnswerDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ANSWER_DAO_CLASS);
			}
			 
			interviewAnswerDAO.storeInterviewAnswerDTCollection(interviewAnswerDTCollection, ivo.getTheInterviewDT());
			for(Object o :interviewAnswerDTCollection){
				 if(o instanceof InterviewAnswerDT){
					((InterviewAnswerDT)o).setItDirty(Boolean.FALSE);
					((InterviewAnswerDT)o).setItNew(Boolean.FALSE);
				 }
			 }
		}
			 */
		logger.debug("Interview root after inserting interview answer collection." );
	}
	


	private InterviewDT selectInterview(long interviewUID)
			throws NEDSSSystemException {
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			return ((InterviewDT) interviewDAO.loadObject(interviewUID));
		} catch (NEDSSDAOSysException ndsex) {
			logger.fatal("Fails selectInterview()"+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.toString());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("Fails selectInterview()"+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.toString());
		}
	}

	private void removeInterview(long aUID) throws NEDSSSystemException {
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			interviewDAO.remove(aUID);
			logger.debug("Removing all the associated answers to interviewId");
			removeInterviewAnswers(aUID);
		} catch (NEDSSDAOSysException ndsex) {
			cntx.setRollbackOnly();
			logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
	}

	private void removeInterviewAnswers(long aUID) throws NEDSSSystemException {
		try {
			if (interviewAnswerDAO == null) {
				interviewAnswerDAO = (InterviewAnswerDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_ANSWER_DAO_CLASS);
			}
			for(Object o : interviewAnswerDAO.getSummaryInterviewDTCollection(aUID)){
				if(o instanceof InterviewAnswerDT){
					((InterviewAnswerDT)o).setItDelete(Boolean.TRUE);
					interviewAnswerDAO.remove(((InterviewAnswerDT)o).getInterviewAnswerUid());
				}
			}
			
		} catch (NEDSSDAOSysException ndsex) {
			cntx.setRollbackOnly();
			logger.fatal("NEDSSSystemException  = "+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.getMessage());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
			cntx.setRollbackOnly();
			throw new NEDSSSystemException(ndapex.getMessage());
		}
	}
	private void updateInterview(InterviewVO ivo) throws NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			interviewDAO.storeInterview(ivo.getTheInterviewDT());
				
			insertInterviewAnswerColl(ivo);
			
		} catch (NEDSSDAOSysException ndsex) {
			logger.fatal("Fails updateInterview()"+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.toString());
		} catch (NEDSSConcurrentDataException ncdaex) {
			logger.fatal("Fails updateInterview() due to concurrent access! "+ncdaex.getMessage(),
					ncdaex);
			throw new NEDSSConcurrentDataException(ncdaex.toString());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("Fails updateInterview()"+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.toString());
		}

	}

	private Long findInterview(long interviewUID) throws NEDSSSystemException {
		Long findPK = null;
		try {
			if (interviewDAO == null) {
				interviewDAO = (InterviewDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.INTERVIEW_DAO_CLASS);
			}
			findPK = interviewDAO.findByPrimaryKey(interviewUID);
		} catch (NEDSSDAOSysException ndsex) {
			logger.fatal("Fails findInterview()"+ndsex.getMessage(), ndsex);
			throw new NEDSSSystemException(ndsex.toString());
		} catch (NEDSSSystemException ndapex) {
			logger.fatal("Fails findInterview()"+ndapex.getMessage(), ndapex);
			throw new NEDSSSystemException(ndapex.toString());
		}
		return findPK;
	}
}// end of InterviewRootDAOImpl class
