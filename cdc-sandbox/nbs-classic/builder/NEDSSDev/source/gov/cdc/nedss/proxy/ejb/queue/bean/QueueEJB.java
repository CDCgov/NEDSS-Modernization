package gov.cdc.nedss.proxy.ejb.queue.bean;
 
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.dao.NbsCaseAnswerDAO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.queue.vo.MessageLogVO;
import gov.cdc.nedss.proxy.ejb.queue.vo.SupervisorReviewVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DateUtil;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

public class QueueEJB extends BMPBase implements javax.ejb.SessionBean
{
    private static final long serialVersionUID = 1L;
    LogUtils                  logger           = new LogUtils((QueueEJB.class).getName());

    public QueueEJB() 
    {

    }
 
    @Override
    public void ejbActivate() throws EJBException, RemoteException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void ejbRemove() throws EJBException, RemoteException
    {
        // TODO Auto-generated method stub

    }

    public void setSessionContext(SessionContext sc) throws java.rmi.RemoteException, javax.ejb.EJBException
    {
    }

    public void ejbCreate()
    {

    }

    public Integer deleteMessage(MessageLogVO messageLogVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
        try
        {
            MessageLogDAOImpl messageLogDaoImpl = new MessageLogDAOImpl();
            Integer deleteInteger = messageLogDaoImpl.delete(messageLogVO);
            return deleteInteger;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.fatal(e.getMessage(), e);
        }
        return null;

    }

    public Integer updateMessage(MessageLogVO messageLogVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
        try
        {
            MessageLogDAOImpl messageLogDaoImpl = new MessageLogDAOImpl();
            Integer updateInteger = messageLogDaoImpl.update(messageLogVO);
            return updateInteger;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.fatal(e.getMessage(), e);
        }
        return null;
    }

    public Integer updateInvestigationClosure(SupervisorReviewVO supervisorVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
        try
        { 
            NedssUtils nedssUtils = new NedssUtils();
            long publicHealthCaseUid = supervisorVO.getPublicHealthCaseUid();
            Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
            Long currentUserProviderId = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
            Object object = nedssUtils.lookupBean(JNDINames.PAGE_PROXY_EJB);
            PageProxyHome home = (PageProxyHome) PortableRemoteObject.narrow(object, PageProxyHome.class);
            PageProxy pageproxy = home.create();     
            PageProxyVO pageProxyVO =  pageproxy.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
            PageActProxyVO pageActProxyVO=(PageActProxyVO)pageProxyVO; 
            PublicHealthCaseVO phc = pageActProxyVO.getPublicHealthCaseVO();
            Collection partCol = phc.getTheParticipationDTCollection();
            Map<String, MessageLogDT> messageMap = new HashMap<String, MessageLogDT>();
            CaseManagementDT cm = phc.getTheCaseManagementDT();
            cm.setCaseReviewStatus( supervisorVO.getCaseReviewStatus() ); 
            // Current Investigator Participation
            ParticipationDT currentInvParDt = getParticipationDT(partCol, NEDSSConstants.CURRENT_INVESTGR.InvestgrOfPHC.toString());
            markDirty(partCol, pageActProxyVO.getPageVO().getActEntityDTCollection());
            java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
			Long lastChgUserId= new Long(nbsSecurityObj.getEntryID());
			phc.getThePublicHealthCaseDT().setItDirty(true);
            phc.getThePublicHealthCaseDT().setLastChgTime(lastChgTime);
            phc.getThePublicHealthCaseDT().setLastChgUserId(lastChgUserId);
            cm.setItDirty(true);
            if( NEDSSConstants.REJECT.equalsIgnoreCase(cm.getCaseReviewStatus())  )
            {
                phc.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
                // Case Closure Check
                if( cm.getCaseClosedDate() != null )
                {
                    cm.setCaseClosedDate(null);
                    ParticipationDT clsr = getParticipationDT(partCol, NEDSSConstants.CLOSURE_INVESTGR.ClosureInvestgrOfPHC.toString());
                    if( clsr != null )
                    {
                        clsr.setItDelete(true);
                    }
                    NbsActEntityDT nAct = getNbsActEntityDT(pageActProxyVO.getPageVO().getActEntityDTCollection(), NEDSSConstants.CLOSURE_INVESTGR.ClosureInvestgrOfPHC.toString());
                    if( nAct != null )
                    {
                        nAct.setItDelete(true);
                }
                }
                else
                {
                    cm.setFldFollUpDispoDate(null);
                    cm.setFldFollUpDispo(null);
                    ParticipationDT parDt = getParticipationDT(partCol, "DispoFldFupInvestgrOfPHC" );
                    if( parDt != null )
                    {
                        parDt.setItDelete(true);
                    }
                    NbsActEntityDT nAct = getNbsActEntityDT(pageActProxyVO.getPageVO().getActEntityDTCollection(), "DispoFldFupInvestgrOfPHC");
                    if( nAct != null )
                    {
                        nAct.setItDelete(true);
                    }
                }  
                if(currentInvParDt!=null)
                	messageMap.put(MessageConstants.INVESTIGATION_REOPENED, createMessageLogDT(pageActProxyVO, currentInvParDt.getSubjectEntityUid(), MessageConstants.INVESTIGATION_REOPENED, NEDSSConstants.INVESTIGATION));
            }
            
            ParticipationDT fldSupervisorParDT = getParticipationDT(partCol, "FldFupSupervisorOfPHC");
            ParticipationDT caseSupervisorParDT = getParticipationDT(partCol, "CASupervisorOfPHC");
            //12/11/2015 - Jit - After discussing with Jennifer Ward, the case closed by information should not be updated if the case closure is accepted.
            // Case Closure Check
//            if( cm.getCaseClosedDate() != null )
//            { 
//                ParticipationDT parDt = getParticipationDT(partCol, NEDSSConstants.CLOSURE_INVESTGR.ClosureInvestgrOfPHC.toString());
//                if( NEDSSConstants.ACCEPT.equalsIgnoreCase(cm.getCaseReviewStatus())  )
//                { 
//                    if( parDt != null )
//                    {
//                        parDt.setItDelete(true);
//                        // Delete the record and insert with new subject_entity_uid
//                    } 
//                    
//                    ParticipationDT aeDt = new ParticipationDT();
//                    aeDt.setActUid(supervisorVO.getPublicHealthCaseUid());
//                    aeDt.setTypeCd(NEDSSConstants.CLOSURE_INVESTGR.ClosureInvestgrOfPHC.toString()); 
//                    aeDt.setAddUserId(currentUser);
//                    //aeDt.setEntityUid(nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid());
//                    //aeDt.setEntityVersionCtrlNbr(new Integer(1) );
//                    aeDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
//                    aeDt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE); 
//                    aeDt.setItNew(true);
//                    phc.getTheParticipationDTCollection().add(aeDt);
//                } 
//                else
//                {
//                    if( parDt != null )
//                        parDt.setItDelete(true); 
//                } 
//            }
            // Comments added by supervisor.
            
            if( !StringUtils.isEmpty(supervisorVO.getComments()) )
            {
                //NbsQuestionDAOImpl questionDao = new NbsQuestionDAOImpl();
                //Collection colQuestions = questionDao.retrieveDMBOuestions(); //size 10,080
                //Long questionUid = null;
                String commentQuestionIdentifier = "";
                String msgTxt = "";
                Long SupervisorUid = null;
                if(supervisorVO.getActivityType()!=null && supervisorVO.getActivityType().equals(NEDSSConstants.CASE_CLOSURE)){
                	commentQuestionIdentifier=MessageConstants.CASE_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER;
                	msgTxt= MessageConstants.CASE_SUPERVISORY_REVIEW_COMMENT_MODIFIED;
                	if(caseSupervisorParDT!=null)
                		SupervisorUid = caseSupervisorParDT.getSubjectEntityUid();
                	 
                }
                else if(supervisorVO.getActivityType()!=null && supervisorVO.getActivityType().equals(NEDSSConstants.FR_CLOSURE)){
                	commentQuestionIdentifier=MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER;
                	msgTxt= MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENT_MODIFIED;
                	if(fldSupervisorParDT!=null)
                		SupervisorUid = fldSupervisorParDT.getSubjectEntityUid();
                }
                NBSQuestionDAOImpl nbsQuestionDAOImpl = new NBSQuestionDAOImpl();
                NbsQuestionDT existingNbsQuestionDT = nbsQuestionDAOImpl.findNBSQuestionByQuestionIdentifier(commentQuestionIdentifier);
                Long nbsQuestionUid = existingNbsQuestionDT.getNbsQuestionUid();

                //for (Iterator iterator = colQuestions.iterator(); iterator.hasNext();)
                //{
               //     NbsQuestionMetadata nqm = (NbsQuestionMetadata) iterator.next();
                //    if( commentQuestionIdentifier.equalsIgnoreCase(nqm.getQuestionIdentifier()) )
                //    {
                 //       questionUid = nqm.getNbsQuestionUid();
                 //       break;
                 //   } 
               // }
                NbsCaseAnswerDAO answerDao = new NbsCaseAnswerDAO();
                Long maxSeqNbr = answerDao.getAnswerSeqNbr(supervisorVO.getPublicHealthCaseUid(), commentQuestionIdentifier);
                NbsCaseAnswerDT answerDt = new NbsCaseAnswerDT();
                answerDt.setSeqNbr(new Integer(0) );
                answerDt.setAnswerGroupSeqNbr( maxSeqNbr.intValue()  + 1 );
                answerDt.setAddUserId(currentUser);
                answerDt.setItNew(true);
                //answerDt.setNbsQuestionUid(questionUid);
                answerDt.setNbsQuestionUid(nbsQuestionUid);
                String name =  nbsSecurityObj.getFullName();
                String dateValue = DateUtil.getToday("MM/dd/yyyy HH:mm");
                answerDt.setAnswerTxt(name + NEDSSConstants.BATCH_PART + dateValue +  NEDSSConstants.BATCH_PART + NEDSSConstants.BATCH_PART + supervisorVO.getComments().trim() );
                Map pra = pageActProxyVO.getPageVO().getPageRepeatingAnswerDTMap();
                if (pra == null)
                {
                    pra = new HashMap();
                }
                List al = (List) pra.get(nbsQuestionUid);
                if (al == null)
                {
                    al = new ArrayList();
                }
                al.add(answerDt);
                pra.put(nbsQuestionUid, al);
                if(currentUserProviderId!=null && SupervisorUid!=null && currentUserProviderId.longValue()!= SupervisorUid.longValue())
                	messageMap.put(msgTxt,  createMessageLogDT(pageActProxyVO, SupervisorUid, msgTxt, NEDSSConstants.INVESTIGATION));
                if(currentUserProviderId!=null && currentInvParDt !=null && currentInvParDt.getSubjectEntityUid().longValue() != currentUserProviderId)
                	messageMap.put(msgTxt,  createMessageLogDT(pageActProxyVO, currentInvParDt.getSubjectEntityUid(), msgTxt, NEDSSConstants.INVESTIGATION));
            }
            
            if (pageActProxyVO.getThePersonVOCollection() != null) {
				for (Iterator<Object> anIterator = pageActProxyVO.getThePersonVOCollection().iterator(); anIterator.hasNext();) {
					PersonVO personVO= (PersonVO)anIterator.next();
					if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
						personVO.setItDirty(true);
						personVO.getThePersonDT().setLastChgTime(lastChgTime);
						personVO.getThePersonDT().setLastChgUserId(lastChgUserId);
						personVO.getThePersonDT().setItDirty(true);
						personVO.getThePersonDT().setItNew(false);
					}
				}
            }
            //In case of case rejected from Supervisor Review queue set below flag
            pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setStdOpenedFromClosed(true);
            pageActProxyVO.setItDirty(true);
            pageActProxyVO.getMessageLogDTMap().putAll(messageMap);
            pageproxy.setPageProxyVO(NEDSSConstants.CASE, pageActProxyVO, nbsSecurityObj); 
        }
        catch(Exception e)
        { 
            e.printStackTrace();
            logger.fatal(e.getMessage(), e);
            return null;
        }
        return 1;
    }
    
    private ParticipationDT getParticipationDT(Collection parCol, String partType)
    {
        ParticipationDT parDt = null;
        for(Object obj : parCol )
        {
            try {
				parDt = (ParticipationDT)obj;
				if( partType.equalsIgnoreCase( parDt.getTypeCd()  ) )
				{
				    return parDt;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.fatal(e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);

			}
        }
        return null;
    }
    
    private NbsActEntityDT getNbsActEntityDT(Collection parCol, String partType)
    {
        NbsActEntityDT parDt = null;
        for(Object obj : parCol )
        {
            try {
				parDt = (NbsActEntityDT)obj;
				if( partType.equalsIgnoreCase( parDt.getTypeCd()  ) )
				{
				    return parDt;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.fatal(e.getMessage(), e);
				throw new EJBException(e.getMessage(), e);

			}
        }
        return null;
    }
    
    private void markDirty(Collection parCol, Collection entCol)
    {
        try {
			for(Object obj : parCol )
			{
			    ParticipationDT parDt = (ParticipationDT)obj;
			    parDt.setItDirty(true); 
			}
			 
			for(Object entObj : entCol )
			{
			    NbsActEntityDT entDt = (NbsActEntityDT)entObj;
			    entDt.setItDirty(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }
    
	private MessageLogDT createMessageLogDT(PageActProxyVO proxyVO, Long assignedtoUID, String messageTxt, String eventTypeCd)
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
			messageLogDT.setAssignedToUid(assignedtoUID);
			messageLogDT
			.setMessageTxt(messageTxt);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			logger.fatal("MessageLogUtil.createMessageLogDT Exception thrown, "+e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return messageLogDT;

	}

     
}
