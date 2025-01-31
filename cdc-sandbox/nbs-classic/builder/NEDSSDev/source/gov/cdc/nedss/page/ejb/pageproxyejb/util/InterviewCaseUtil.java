package gov.cdc.nedss.page.ejb.pageproxyejb.util;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dao.AnswerRootDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;


/**
 *Name: InterviewCaseUtil.java Description: Utility class for Interview Object(for
 * Page Builder Dynamic Interview functionality)
 * @Company: LEIDOS
 * @since: NBS4.5
 * @author Pradeep Sharma
 */
public class InterviewCaseUtil  {
	static final LogUtils logger = new LogUtils(InterviewCaseUtil.class.getName());

	/**
	 * 
	 * @param interviewUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSSystemException
	 */
	public PageProxyVO getPageProxyVO(long interviewUid,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	NEDSSSystemException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW, NBSOperationLookup.VIEW)) {
			logger.fatal("InterviewCaseUtil.getPageProxyVO =nbsSecurityObj.getPermission(NedssBOLookup.INTERVIEW ,NBSOperationLookup.VIEW) is false");
			throw new NEDSSSystemException("View interview access denied. NO PERMISSIONS to view interview Object.");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INTERVIEW,NBSOperationLookup.VIEW) is true");
		PageActProxyVO pageProxyVO = new PageActProxyVO();

		InterviewVO theInterviewVO=null;
		NedssUtils nedssUtils = new NedssUtils();
		Object actLookedUpObject;

		try {
			logger.debug("InterviewCaseUtil.getPageProxyVO: before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
			// Reference an Act controller to use later
			actLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(actLookedUpObject, ActControllerHome.class);
			ActController actController = actHome.create();

			theInterviewVO = actController.getInterview(interviewUid, nbsSecurityObj);
			NBSAuthHelper helper = new NBSAuthHelper();
			theInterviewVO.getTheInterviewDT().setAddUserName(helper.getUserName(theInterviewVO.getTheInterviewDT().getAddUserId()));
			theInterviewVO.getTheInterviewDT().setLastChgUserName(helper.getUserName(theInterviewVO.getTheInterviewDT().getLastChgUserId()));

			logger.debug("InterviewCaseUtil.getPageProxyVO: successful lookup of Interview DT Object.");

			AnswerRootDAOImpl answerRootDAO = new AnswerRootDAOImpl();
			PageVO pageVO = answerRootDAO.get(interviewUid);
			
			pageProxyVO.setPageVO(pageVO);
			pageProxyVO.setInterviewVO(theInterviewVO);
			
			PageActProxyHelper.getPageActProxyVO(pageProxyVO, interviewUid, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("Exception is "+ e);
			String errorMessage =e.getCause()+ e.getMessage();
			logger.error("InterviewCaseUtil.getPageProxyVO:-Exception There is an error while getting pageActProxyVO:", errorMessage);
			throw new java.rmi.RemoteException(e.toString());
		}
		return pageProxyVO;
	}

/**
 * 
 * @param interviewCaseUid
 * @param nbsSecurityObj
 * @return
 * @throws RemoteException
 * @throws CreateException
 * @throws NEDSSConcurrentDataException
 */
	public Map<Object, Object> deleteInterview(Long interviewCaseUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, CreateException, NEDSSConcurrentDataException {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		NedssUtils nedssUtils = new NedssUtils();
		ActController actController = null;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW, NBSOperationLookup.DELETE)) {
			logger.fatal("InterviewCaseUtil.deleteInterview =nbsSecurityObj.getPermission(NedssBOLookup.INTERVIEW ,NBSOperationLookup.DELETE) is false");
			throw new NEDSSSystemException("DELETE interview access denied. NO PERMISSIONS to DELETE interview Object.");
		}
		try {
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("InterviewCaseUtil.deleteInterview ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			logger.debug("InterviewCaseUtil.deleteInterviewFound ActControllerHome: " + acthome);
			try {
				actController = acthome.create();
			} catch (RemoteException e1) {
				logger.fatal("InterviewCaseUtil.deleteInterviewProxy RemoteException thrown"+ e1);
				throw new RemoteException();
			} catch (CreateException e1) {
				logger.fatal("InterviewCaseUtil.deleteInterviewProxy CreateException thrown"+ e1);
				throw new CreateException();
			}

			PageActProxyVO pageActProxyVO  = null;
			try {
				pageActProxyVO = (PageActProxyVO)getPageProxyVO(interviewCaseUid, nbsSecurityObj);
			} catch (RemoteException e1) {
				logger.fatal("InterviewCaseUtil.deleteInterview RemoteException thrown"+ e1);
				throw new RemoteException();
			} catch (NEDSSSystemException e1) {
				logger.fatal("InterviewCaseUtil.deleteInterviewProxy NEDSSSystemException thrown"+ e1);
				throw new NEDSSSystemException();
			}
			java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
			
			try {
				InterviewDT interviewDT = pageActProxyVO.getInterviewVO().getTheInterviewDT();
				AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
				interviewDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
				interviewDT.setRecordStatusTime(lastChgTime);
				interviewDT.setLastChgTime(lastChgTime);
				answerRootDAOImpl.logDelete(interviewDT);
				interviewDT.setItDelete(false);
				interviewDT.setItDirty(true);
				pageActProxyVO.setItDirty(true);
				pageActProxyVO.getInterviewVO().setItDirty(true);
				actController.setInterview(pageActProxyVO.getInterviewVO(), nbsSecurityObj);
				
				PageActProxyHelper.setPageActProxyVO(pageActProxyVO,pageActProxyVO.getInterviewVO().getTheInterviewDT(), nbsSecurityObj);
				
			} catch (NEDSSSystemException e1) {logger.fatal("InterviewCaseUtil.deleteInterviewProxy NEDSSSystemException thrown"+ e1);
			throw new NEDSSSystemException();
			} catch (NEDSSConcurrentDataException e1) {logger.fatal("InterviewCaseUtil.deleteInterviewProxy NEDSSConcurrentDataException thrown"+ e1);
			throw new NEDSSConcurrentDataException();
			} catch (NEDSSAppException e1) {logger.fatal("InterviewCaseUtil.deleteInterviewProxy NedssApp thrown"+ e1);
			throw new NEDSSSystemException();
			} catch (EJBException e1) {
				logger.fatal("InterviewCaseUtil.deleteInterviewProxy EJBException thrown"+ e1);
				throw new RemoteException();
			}
		} catch (NEDSSSystemException e) {
			logger.fatal("InterviewCaseUtil.deleteInterview NEDSSSystemException thrown ", e);
			throw new RemoteException();
		} catch (ClassCastException e) {
			logger.fatal("InterviewCaseUtil.deleteInterview ClassCastException thrown ", e);
			throw new RemoteException();
		}
		return returnMap;
	}

	/**
	 * 
	 * @param pageProxyVO
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSConcurrentDataException
	 * @throws CreateException
	 */
	public Long setPageActProxyVO(PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj) throws RemoteException,NEDSSConcurrentDataException, CreateException {
		PageActProxyVO pageActProxyVO = (PageActProxyVO) pageProxyVO;
		Long actualUid =null;
		InterviewDT interviewDT = pageActProxyVO.getInterviewVO().getTheInterviewDT();
		// if both are false throw exception
		if ((!pageActProxyVO.isItNew()) && (!pageActProxyVO.isItDirty())) {
			logger.fatal("InterviewCaseUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+" and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());
			throw new NEDSSSystemException("InterviewCaseUtil.setPageActProxyVO:- pageProxyVO.isItNew() = "+ pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty() + " for setInterviewProxy");
		}
		logger.info("InterviewCaseUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());

		if (pageActProxyVO.isItNew()) {
			logger.info("InterviewCaseUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());
		}
		boolean addPermission=nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW, NBSOperationLookup.ADD);
		boolean editPermission=nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW, NBSOperationLookup.EDIT);
		if(pageActProxyVO.isItNew() && !addPermission) {
			String errString ="nterviewCaseUtil.setPageActProxyVO:-user does not have permission to add interview record!!! Please check addPermission:-"+addPermission;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString +"\nInterviewCaseUtil.setPageActProxyVO:-pageProxyVO.isItNew() = "+ pageActProxyVO.isItNew()+ " and interview add permission= "+ addPermission);
		}else if(pageActProxyVO.isItDirty() && !editPermission) {
			String errString ="InterviewCaseUtil.setPageActProxyVO:-user does not have permission to update interview record!!! Please check editPermission:-"+editPermission;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString+"\nInterviewCaseUtil.setPageActProxyVO:-pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty()+ " and interview add permission= "+ editPermission);
		}
		try {
			NedssUtils nedssUtils = new NedssUtils();
			ActController actController = null;
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("InterviewCaseUtil.setPageActProxyVO:-ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			logger.debug("InterviewCaseUtil.setPageActProxyVO:-Found ActControllerHome: " + acthome);
			actController = acthome.create();
			InterviewVO interviewVO = pageActProxyVO.getInterviewVO();
			actualUid = actController.setInterview(interviewVO, nbsSecurityObj);
			if(pageProxyVO.getInterviewVO().getTheInterviewDT().getInterviewUid()==null || (pageProxyVO.getInterviewVO().getTheInterviewDT().getInterviewUid().compareTo(new Long(1))<0)) {
				pageProxyVO.getInterviewVO().getTheInterviewDT().setInterviewUid(actualUid);
				interviewDT.setInterviewUid(actualUid);
			}
			AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
			if(pageActProxyVO.isItDirty()) {
				PageVO pageVO=(PageVO)pageActProxyVO.getPageVO();
				answerRootDAOImpl.store(pageVO, interviewDT);
			}else {
				PageVO pageVO=(PageVO)pageActProxyVO.getPageVO();
				answerRootDAOImpl.insertPageVO(pageVO, interviewDT);
			}
			
			
			PageActProxyHelper.setPageActProxyVO(pageActProxyVO, pageProxyVO.getInterviewVO().getTheInterviewDT(), nbsSecurityObj);
			
			if (pageActProxyVO.getInterviewVO()
					.getEdxEventProcessDTCollection() != null) {
				NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
				for (EDXEventProcessDT processDT : pageActProxyVO
						.getInterviewVO()
						.getEdxEventProcessDTCollection()) {
					processDT.setNbsEventUid(actualUid);
					documentDAO.insertEventProcessDTs(processDT);
					logger.debug("Inserted the event Process for sourceId: "
							+ processDT.getSourceEventId());
				}
			}

		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("Exception is "+ ex);
			logger.fatal("InterviewCaseUtil.setPageActProxyVO:-The entity cannot be updated as concurrent access is not allowed!");
			logger.fatal(nbsSecurityObj.getFullName(), ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException("Concurrent access occurred in InterviewProxyEJB : "+ ex.toString());
		} catch (Exception e) {
			logger.fatal("Exception is "+ e);
			logger.fatal("InterviewCaseUtil.setPageActProxyVO:-The entity cannot be updated. Please check Exception!");
			logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
			throw new javax.ejb.EJBException("InterviewCaseUtil.setPageActProxyVO:- Exception thrown: "+ e.toString());
		}
	return actualUid;
	}

}
