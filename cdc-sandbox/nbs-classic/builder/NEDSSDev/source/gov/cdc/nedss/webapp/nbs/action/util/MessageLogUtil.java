package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.dt.PersonInvestgationSummaryDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class MessageLogUtil {

	static final LogUtils logger = new LogUtils(MessageLogUtil.class.getName());

	private static final long serialVersionUID = 1L;

	public static void createMessageLogForParticipant(PageActProxyVO proxyVO,
			String typeCd, ParticipationDT oldParticipationDT, Long providerUid)
			throws NEDSSAppException {
		logger.debug("in createMessageLogForParticipant();");
		try {
			if (proxyVO.getCurrentInvestigator() == null || proxyVO.getCurrentInvestigator().trim().equals("")  || !typeCd.equalsIgnoreCase(NEDSSConstants.PHC_INVESTIGATOR)){
				return;
			}else{
				if(providerUid==null ||  !providerUid.toString().equals(proxyVO.getCurrentInvestigator())) {
						
					if (((oldParticipationDT==null || oldParticipationDT.getSubjectEntityUid() == null))
								|| (oldParticipationDT.getSubjectEntityUid() != null && !oldParticipationDT.getSubjectEntityUid().toString().equals(proxyVO.getCurrentInvestigator())))
					{
								MessageLogDT messageLogDT = createMessageLogDT(proxyVO);
								messageLogDT.setMessageTxt(MessageConstants.NEW_ASSIGNMENT);
								messageLogDT.setAssignedToUid(new Long(proxyVO.getCurrentInvestigator()));
			
								proxyVO.getMessageLogDTMap().put(
												ActionConstants.CURRENT_INVESTGATOR, messageLogDT);
					}
					
				}
			}
		}catch (Exception e) {
			logger.error("Caught Exception in createMessageLogForParticipant",e);
			logger.error("MessageLogUtil.createMessageLogForParticipant Exception thrown, "+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageLogForParticipant Exception thrown, ", e);
		}
	}

	
	public static MessageLogDT createMessageLogDT(PageActProxyVO proxyVO)
			throws NEDSSAppException {
		MessageLogDT messageLogDT = new MessageLogDT();
		Long userId =null;
		Timestamp time =null;
		if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId()==null){
			userId =proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId();
			time=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime();
		}
		else{
			userId =proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId();
			time=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();
		}
		try {
			messageLogDT.setConditionCd(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			messageLogDT.setLastChgTime(time);
			messageLogDT.setAddTime(time);
			messageLogDT.setItNew(true);
			messageLogDT.setLastChgUserId(userId);
			messageLogDT.setEventUid(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			messageLogDT.setEventTypeCd(NEDSSConstants.INVESTIGATION);
			messageLogDT.setMessageStatusCd(MessageConstants.N);
			messageLogDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			messageLogDT.setRecordStatusTime(time);
			messageLogDT.setUserId(userId);
		} catch (Exception e) {
			logger.error("Caught Exception in createMessageLogDT",e);
			logger.error("MessageLogUtil.createMessageLogDT Exception thrown, "
					+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageLogDT Exception thrown, ", e);
		}
		return messageLogDT;

	}
	
	public static MessageLogDT createMessageLogDT(PageActProxyVO proxyVO, String messageTxt) throws NEDSSAppException{
		return createMessageLogDT( proxyVO,  messageTxt, null);
	}
	
	public static MessageLogDT createMessageLogDT(PageActProxyVO proxyVO, String messageTxt, String eventTypeCd)
			throws NEDSSAppException {
		if (eventTypeCd == null) eventTypeCd=NEDSSConstants.INVESTIGATION;
		MessageLogDT messageLogDT = new MessageLogDT();
		Long userId =null;
		Timestamp time =null;
		if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId()==null){
			userId =proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId();
			time=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime();
		}
		else{
			userId =proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId();
			time=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();
		}
		try {
			messageLogDT.setConditionCd(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			messageLogDT.setLastChgTime(time);
			messageLogDT.setAddTime(time);
			messageLogDT.setItNew(true);
			messageLogDT.setLastChgUserId(userId);
			messageLogDT.setEventUid(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			messageLogDT.setEventTypeCd(eventTypeCd);
			messageLogDT.setMessageStatusCd(MessageConstants.N);
			messageLogDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			messageLogDT.setRecordStatusTime(time);
			messageLogDT.setUserId(userId);
			if(proxyVO.getCurrentInvestigator() != null)
			messageLogDT.setAssignedToUid(new Long (proxyVO.getCurrentInvestigator()));
			messageLogDT
			.setMessageTxt(messageTxt);
		} catch (Exception e) {
			logger.error("MessageLogUtil.createMessageLogDT Exception thrown, "+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageLogDT Exception thrown, ", e);
		}
		return messageLogDT;

	}

	public static MessageLogDT createMessageLogDT(ObservationVO obsVOLab112,
			PublicHealthCaseDT phcDT) throws NEDSSAppException {
		MessageLogDT messageLogDT = new MessageLogDT();
		try {
			messageLogDT.setConditionCd(phcDT.getCd());
			messageLogDT.setPersonUid(phcDT.getCurrentPatientUid());
			messageLogDT.setAssignedToUid(new Long(phcDT.getCurrentInvestigatorUid()));
			messageLogDT.setLastChgTime(obsVOLab112.getTheObservationDT()
					.getLastChgTime());
			messageLogDT.setAddTime(obsVOLab112.getTheObservationDT()
					.getLastChgTime());
			messageLogDT.setItNew(true);
			messageLogDT.setLastChgUserId(obsVOLab112.getTheObservationDT()
					.getLastChgUserId());
			messageLogDT.setEventUid(phcDT.getPublicHealthCaseUid());
			messageLogDT.setEventTypeCd(NEDSSConstants.INVESTIGATION);
			messageLogDT.setMessageStatusCd(MessageConstants.N);
			messageLogDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			messageLogDT.setRecordStatusTime(obsVOLab112.getTheObservationDT()
					.getLastChgTime());
			messageLogDT.setUserId(obsVOLab112.getTheObservationDT()
					.getLastChgUserId());
			messageLogDT
					.setMessageTxt(MessageConstants.PENDING_LAB_RESULT_UPDATED);
		} catch (Exception e) {
			logger.error("MessageLogUtil.createMessageLogDT Exception thrown, "	+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageLogDT Exception thrown, ",e);
		}
		return messageLogDT;

	}


	public static void createMessageForUpdatedComments(PageForm pageForm,
			PageActProxyVO proxyVO, Long providerUid) throws NEDSSAppException {
		try {
       			Map<Object, Object> map = pageForm.getQuestionMetadataMap();
			if (map != null) {
				Set<Object> mappedQuestions = map.keySet();
				Iterator<Object> iter = mappedQuestions.iterator();

				while (iter.hasNext()) {
					NbsQuestionMetadata metadata = (NbsQuestionMetadata) iter
							.next();

					
					if(pageForm.getUpdatedRepeatingCommentsMap().get(metadata.getNbsQuestionUid())!=null){
						createMessageForUpdatedComments(proxyVO, metadata, providerUid);

					}
				}

			}
		} catch (Exception e) {
			logger.error("MessageLogUtil.createMessageForUpdatedComments Exception thrown, "+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageForUpdatedComments Exception thrown, ",e);	
	}
	}

	public static void createMessageForUpdatedComments(PageActProxyVO proxyVO,
			NbsQuestionMetadata metaData, Long providerUid) throws NEDSSAppException {
		String strProviderUid="";
		String strSupervisor = null;
		String strCondition = null;
		
		
		if(providerUid!=null)
		{
			strProviderUid=providerUid.toString();
		}
		
		if(!proxyVO.isSTDProgramArea() && proxyVO.getCurrentInvestigator()==null || proxyVO.getCurrentInvestigator().trim().equalsIgnoreCase("")){
			return;	
		}
		
		
		try {
			strSupervisor = proxyVO.getCaseSupervisor();
			strCondition = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			if (strSupervisor == null && strCondition != null && strCondition.equals(CTConstants.CongenitalSyphilisConditionCode))
				strSupervisor = proxyVO.getFieldSupervisor(); //no Interview supervisor for Congenital
			if (metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.CASE_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)) {
				
				
				if(strSupervisor!=null 
						&& !strSupervisor.trim().equals(strProviderUid) 
						&& proxyVO.getCurrentInvestigator()!=null && proxyVO.getCurrentInvestigator().equals(strProviderUid)) {
					// write message for supervisor
					MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					messageLogDT.setMessageTxt(MessageConstants.CASE_SUPERVISORY_REVIEW_COMMENT_MODIFIED);
					messageLogDT.setAssignedToUid(new Long(strSupervisor));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_CASE_SPERVISOR_NOTES_CURR_SUPERV,messageLogDT);

				} else if (strSupervisor!=null 
						&& strSupervisor.trim().equals(strProviderUid) 
						&& proxyVO.getCurrentInvestigator()!=null && !proxyVO.getCurrentInvestigator().equals(strProviderUid)) {
					// write message for Investigator
					MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					messageLogDT.setMessageTxt(MessageConstants.CASE_SUPERVISORY_REVIEW_COMMENT_MODIFIED);
					messageLogDT.setAssignedToUid(new Long(proxyVO.getCurrentInvestigator()));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_CASE_SPERVISOR_NOTES_CURRENT_SUPRV,messageLogDT);
				} else {
					// write message for both current investigator and field
					// supervisor
					// write message for field supervisor
					if(strSupervisor!=null && !strSupervisor.trim().equals("")){
						MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
						messageLogDT.setMessageTxt(MessageConstants.CASE_SUPERVISORY_REVIEW_COMMENT_MODIFIED);
						messageLogDT.setAssignedToUid(new Long(strSupervisor));
						proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_CASE_SPERVISOR_NOTES_CURR_SUPERV,messageLogDT);
				}
					// write message for Investigator
					MessageLogDT supervisorMessageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					supervisorMessageLogDT.setMessageTxt(MessageConstants.CASE_SUPERVISORY_REVIEW_COMMENT_MODIFIED);
					supervisorMessageLogDT.setAssignedToUid(new Long(proxyVO.getCurrentInvestigator()));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_CASE_SPERVISOR_NOTES_CURRENT_SUPRV,supervisorMessageLogDT);
				}
			} else if (metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)) {
				
				if(proxyVO.getFieldSupervisor()!=null 
						&& !proxyVO.getFieldSupervisor().trim().equals(strProviderUid) 
						&& proxyVO.getCurrentInvestigator()!=null && proxyVO.getCurrentInvestigator().equals(strProviderUid)) {
					// write message for Supervisor from Investigator
					MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					messageLogDT.setMessageTxt(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENT_MODIFIED);
					messageLogDT.setAssignedToUid(new Long(proxyVO.getFieldSupervisor()));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_FIELD_SPERVISOR_NOTES_CURR_SUPERV,messageLogDT);

				} else if (proxyVO.getFieldSupervisor()!=null 
						&& proxyVO.getFieldSupervisor().trim().equals(strProviderUid) 
						&& proxyVO.getCurrentInvestigator()!=null && !proxyVO.getCurrentInvestigator().equals(strProviderUid)) {
					// write message for Investigator from Supervisor
					MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					messageLogDT.setMessageTxt(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENT_MODIFIED);
					messageLogDT.setAssignedToUid(new Long(proxyVO.getCurrentInvestigator()));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_FIELD_SPERVISOR_NOTES_CURR_SUPERV,messageLogDT);
				} else {
					// write message for both current investigator and field
					// supervisor
					// write message for Field Supervisor
					if(proxyVO.getFieldSupervisor()!=null && !proxyVO.getFieldSupervisor().trim().equals("")){
						MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
						messageLogDT.setMessageTxt(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENT_MODIFIED);
						messageLogDT.setAssignedToUid(new Long(proxyVO.getFieldSupervisor()));
						proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_FIELD_SPERVISOR_NOTES_CURR_SUPERV,messageLogDT);
					}
					// write message for Investigator
					MessageLogDT supervisorMessageLogDT = MessageLogUtil.createMessageLogDT(proxyVO);
					supervisorMessageLogDT.setMessageTxt(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENT_MODIFIED);
					supervisorMessageLogDT.setAssignedToUid(new Long(proxyVO.getCurrentInvestigator()));
					proxyVO.getMessageLogDTMap().put(ActionConstants.UPDATED_FIELD_SPERVISOR_NOTES_CURR_SUPERV,supervisorMessageLogDT);
				}
		}//FIELD SUPERVISOR REVIEW COMMENTS
		} catch (Exception e) {
			logger.error("MessageLogUtil.createMessageForUpdatedComments Exception thrown, "
					+ e);
			throw new NEDSSAppException("MessageLogUtil.createMessageForUpdatedComments Exception thrown, ", e);
		}
	}

	public static ArrayList<Object> getMessagesFromQueue(
			ObservationVO obsVOLab112, HttpServletRequest request)
			throws NEDSSAppException {
		ArrayList<Object> assiciatedInvestigationCollections = new ArrayList<Object> ();
		ArrayList<Object> messageLogCollections = new ArrayList<Object> ();
		try {

			Object[] oParams = new Object[] { obsVOLab112.getTheObservationDT()
					.getObservationUid() };
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "getAssociatedPublicHealthCases";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName,
					sMethod, request.getSession());
			if (obj != null) {
				assiciatedInvestigationCollections=(ArrayList<Object>)obj;
				if(assiciatedInvestigationCollections!=null && assiciatedInvestigationCollections.size()>0){
					Iterator<Object> iter = assiciatedInvestigationCollections.iterator();
					while (iter.hasNext()) {
						PublicHealthCaseDT phcDT = (PublicHealthCaseDT) iter.next();
						
						if(phcDT.getCurrentInvestigatorUid()==null){
							logger.error("There exists no current investigator to the Investigation for the public_health_case_uid"+ phcDT.getPublicHealthCaseUid());
							continue;
						}
						if (phcDT.getProgAreaCd().equalsIgnoreCase(
								obsVOLab112.getTheObservationDT().getProgAreaCd())
								&& !(obsVOLab112
										.getTheObservationDT()
										.getLastChgUserId()
										.compareTo(
												phcDT.getCurrentInvestigatorUid()) == 0)) {
							// create Message
							MessageLogDT messageLogDT = createMessageLogDT(
									obsVOLab112, phcDT);
							messageLogCollections.add(messageLogDT);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("MessageLogUtil.getMessagesFromQueue Exception thrown, "+ e);
			throw new NEDSSAppException("MessageLogUtil.getMessagesFromQueue Exception thrown, ", e);
		}
		return messageLogCollections;
	}

	public static void createNewClusterMessage(NBSSecurityObj nbsSecurityObj,
			PageActProxyVO proxyVO, Long invUid) {
		if (proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT() != null) {
			MessageLogDT messageLogDT = RetrieveSummaryVO
					.createMessageLogDT(proxyVO.getPublicHealthCaseVO()
							.getThePublicHealthCaseDT(), nbsSecurityObj);
			messageLogDT
					.setMessageTxt(MessageConstants.NEW_CLUSTER_TO_YOUR_CASE);
			messageLogDT.setAssignedToUid(invUid);
			proxyVO.getMessageLogDTMap().put(MessageConstants.NEW_CLUSTER_TO_YOUR_CASE_KEY, messageLogDT);
			

		}
	}
	
	public static void createNotificationForSecondaryReferral(
			HttpServletRequest request, String conditionCd,
			Long investigatorUid, CTContactProxyVO proxyVO,
			NBSSecurityObj nbsSecurityObj) {

		
			String refType = proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd();
		
				if("SR".equals(refType) || "RSC".equals(refType)) {
			Collection<?> contactInvestigations = (Collection<?>) request
					.getSession().getAttribute("strContactInvestigationList");
			for (Object o : contactInvestigations) {
				if (o instanceof PersonInvestgationSummaryDT
						&& ((PersonInvestgationSummaryDT) o).getRscSecRef() != null 
						&&("SR".equals(((PersonInvestgationSummaryDT) o).getRscSecRef()) ||
								"RSC".equals(((PersonInvestgationSummaryDT) o).getRscSecRef()))) {
					if (request.getSession().getAttribute(
							"strContactInvestigationSummVOList") != null) {
						Collection<?> investigations = (Collection<?>) request
								.getSession().getAttribute(
										"strContactInvestigationSummVOList");
						for (Object vo : investigations) {
							if (vo instanceof InvestigationSummaryVO
									&& ((InvestigationSummaryVO) vo)
											.getLocalId()
											.equals(((PersonInvestgationSummaryDT) o)
													.getInvestigationId())) {
								createAddMessageLogDT(investigatorUid, proxyVO,
										nbsSecurityObj, (InvestigationSummaryVO)vo,MessageConstants.NEW_SECONDARY_ADDED_KEY,
										MessageConstants.NEW_SECONDARY_ADDED);
							}
						}
					}

				}

			}
		
		}
		
	}


	public static void createAddMessageLogDT(Long investigatorUid,
			CTContactProxyVO proxyVO, NBSSecurityObj nbsSecurityObj, InvestigationSummaryVO vo, String messageKey,   String messageText) {
		
		Long mprUid = vo.getPatientRevisionUid();
		MessageLogDT messageLogDT = createMessageLogDT(
				 vo,
				investigatorUid,mprUid,
				messageText,
				nbsSecurityObj); 
		proxyVO.getMessageLogDtMap().put(
				messageKey+mprUid, messageLogDT);
	}

	
	private static MessageLogDT createMessageLogDT(InvestigationSummaryVO investigation, Long investigatorUid, Long mprUid, String messageText, NBSSecurityObj nbsSecurityObj){
		MessageLogDT messageLogDT = new MessageLogDT();
		try {
			java.util.Date dateTime = new java.util.Date();
			Timestamp time = new Timestamp(dateTime.getTime());
			messageLogDT.setConditionCd(investigation.getCd());
			messageLogDT.setLastChgTime(time);
			messageLogDT.setAddTime(time);
			messageLogDT.setItNew(true);
			messageLogDT.setLastChgUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
			messageLogDT.setEventUid(investigation.getPublicHealthCaseUid());
			messageLogDT.setEventTypeCd(NEDSSConstants.INVESTIGATION);
			messageLogDT.setMessageStatusCd(MessageConstants.N);
			messageLogDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			messageLogDT.setRecordStatusTime(time);
			messageLogDT.setMessageTxt(messageText);
			messageLogDT.setUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
			messageLogDT.setPersonUid(mprUid);
			messageLogDT.setAssignedToUid(investigatorUid);
		} catch (NumberFormatException e) {
        	logger.error("Caught exception in createMessageLogDT:", e);
			logger.error("Unable to store the Error message in createMessageLogDT for = "
					+ investigation.toString());
		}	
		return messageLogDT;
		
	  }
}
