package gov.cdc.nedss.page.ejb.pageproxyejb.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.nbsactentity.dao.NbsActEntityDAO;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityHistoryDAO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dao.NbsAnswerDAO;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

/**
 * Root DAO to control access to Interview Answer collection
 * @author Pradeep Sharma
 * @since: Release 4.5
 *
 */
public class AnswerRootDAOImpl {
	static final LogUtils logger = new LogUtils(AnswerRootDAOImpl.class.getName());
	
	public PageVO get(Long uid) throws NEDSSAppException{
		PageVO pageVO = new PageVO();
		try {
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			//Map<Object, Object> answerCollection =null;
			//answerCollection =answerDAO.getAnswerDTCollection(interviewUid);
			
			Map<Object,Object> answerDTReturnMap =answerDAO.getPageAnswerDTMaps(uid);
			Map<Object, Object> nbsAnswerMap =new HashMap<Object, Object>();
			Map<Object, Object> nbsRepeatingAnswerMap =new HashMap<Object, Object>();
			if(answerDTReturnMap.get(NEDSSConstants.NON_REPEATING_QUESTION)!=null){
				nbsAnswerMap=(HashMap<Object, Object>)answerDTReturnMap.get(NEDSSConstants.NON_REPEATING_QUESTION);
				logger.debug("AnswerRootDAOImpl nbsAnswerMap Size +"+nbsAnswerMap.size());
				logger.debug("AnswerRootDAOImpl nbsAnswerMap Values +"+nbsAnswerMap.toString());
			}
			if(answerDTReturnMap.get(NEDSSConstants.REPEATING_QUESTION)!=null){
				nbsRepeatingAnswerMap=(HashMap<Object, Object>)answerDTReturnMap.get(NEDSSConstants.REPEATING_QUESTION);
				logger.debug("AnswerRootDAOImpl nbsRepeatingAnswerMap Size +"+nbsRepeatingAnswerMap.size());
				logger.debug("AnswerRootDAOImpl nbsRepeatingAnswerMap Values +"+nbsRepeatingAnswerMap.toString());
			}
			pageVO.setAnswerDTMap(nbsAnswerMap);
			pageVO.setPageRepeatingAnswerDTMap(nbsRepeatingAnswerMap);
			
			NbsActEntityDAO pageCaseEntityDAO = new NbsActEntityDAO();
			Collection<Object>  pageCaseEntityDTCollection=pageCaseEntityDAO.getActEntityDTCollection(uid);
			pageVO.setActEntityDTCollection(pageCaseEntityDTCollection);
			
		} catch (NEDSSSystemException e) {
			logger.fatal("InterviewAnswerRootDAOImpl:answerCollection -The system could not get answerMap"+e.getMessage(), e);
			throw new NEDSSAppException("InterviewAnswerRootDAOImpl:answerCollection- could not be returned", e);
		}
		return pageVO;
		
	}
	
	public void store(PageVO pageVO, RootDTInterface rootDTInterface) throws NEDSSAppException{
		try {
			AnswerRootDAOImpl answerRootDAO= new AnswerRootDAOImpl();
			answerRootDAO.delete(rootDTInterface);
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			if(pageVO!=null && pageVO.getAnswerDTMap()!=null && pageVO.getAnswerDTMap().values()!=null)
				answerDAO.storeAnswerDTCollection(pageVO.getAnswerDTMap().values(), rootDTInterface);
			if(pageVO!=null && pageVO.getPageRepeatingAnswerDTMap()!=null && pageVO.getPageRepeatingAnswerDTMap().values()!=null)
				answerDAO.storeAnswerDTCollection(pageVO.getPageRepeatingAnswerDTMap().values(), rootDTInterface);
			NbsActEntityDAO pageCaseEntityDAO = new NbsActEntityDAO();
			pageCaseEntityDAO.insertActEntityDTCollection(pageVO.getActEntityDTCollection(), rootDTInterface);
		} catch (NEDSSSystemException e) {
			logger.fatal("InterviewAnswerRootDAOImpl:store-The system could not store interview collection!"+e.getMessage(), e);
			throw new NEDSSAppException("InterviewAnswerRootDAOImpl:store- answerDTColl could not be stored", e);
		}
	}
	
	public void history(Collection<Object> answerDTColl) throws NEDSSAppException{
		try {
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			answerDAO.insertAnswerHistoryDTCollection(answerDTColl);
		} catch (NEDSSSystemException e) {
			logger.fatal("InterviewAnswerRootDAOImpl:history- The system could not put interview collection in the history!"+e.getMessage(), e);
			throw new NEDSSAppException("InterviewAnswerRootDAOImpl:history- could not be returned", e);
		}
	}
	
	
	public void insertPageVO(PageVO pageVO,RootDTInterface rootDTInterface) throws NEDSSSystemException{
		try {
			NbsAnswerDAO answerRootDAOImpl= new NbsAnswerDAO();
			if(pageVO!=null && pageVO.getAnswerDTMap() !=null ) {
				Collection<Object> answerDTColl =pageVO.getAnswerDTMap().values();
				if(answerDTColl!=null && answerDTColl.size()>0) {
					answerRootDAOImpl.storeAnswerDTCollection(answerDTColl, rootDTInterface);
				}
				if(pageVO!=null && pageVO.getPageRepeatingAnswerDTMap() !=null ) {
					Collection<Object> interviewRepeatingAnswerDTColl =pageVO.getPageRepeatingAnswerDTMap().values();
					if(interviewRepeatingAnswerDTColl!=null && interviewRepeatingAnswerDTColl.size()>0) {
						answerRootDAOImpl.storeAnswerDTCollection(interviewRepeatingAnswerDTColl, rootDTInterface);
					}
				}
			}
			NbsActEntityDAO pageCaseEntityDAO = new NbsActEntityDAO();
			pageCaseEntityDAO.storeActEntityDTCollection(pageVO.getActEntityDTCollection(), rootDTInterface);
	
		} catch (NEDSSSystemException e) {
            logger.fatal("Error while insertPageVO:-"+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
	}
	
	private void delete(RootDTInterface rootDTInterface) throws NEDSSAppException{
		try {
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			Collection<Object> answerCollection=null;
			answerCollection =answerDAO.getAnswerDTCollectionForRemoveToHist(rootDTInterface.getUid());
			if(answerCollection!=null && answerCollection.size()>0) {
				AnswerRootDAOImpl rootDAOImpl = new AnswerRootDAOImpl();
				rootDAOImpl.history(answerCollection);
			}
			NbsActEntityDAO nbsActEntityDAO = new NbsActEntityDAO();
			Collection<Object> actEntityCollection = nbsActEntityDAO.getActEntityDTCollection(rootDTInterface.getUid());
			
			if(actEntityCollection!=null && actEntityCollection.size()>0) {
				NbsActEntityHistoryDAO nbsActEntityHistDAO = new NbsActEntityHistoryDAO();
				nbsActEntityHistDAO.insertPageEntityHistoryDTCollection(actEntityCollection, rootDTInterface);
			}
		} catch (NEDSSSystemException e) {
			logger.fatal("InterviewAnswerRootDAOImpl:answerCollection -The system could not get answerMap"+e.getMessage(), e);
			throw new NEDSSAppException("InterviewAnswerRootDAOImpl:answerCollection- could not be returned", e);
		}
		
	}
	
	
	/**
	 * It create history record for answers, delete them and insert new answers with LOG_DEL status.
	 * @param rootDTInterface
	 * @throws NEDSSAppException
	 */
	public void logDelete(RootDTInterface rootDTInterface) throws NEDSSAppException{
		try {
			
			Collection<Object> logDelAnswerCollection =  markAnswerForInsert(rootDTInterface);
			
			// Create History records
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			Collection<Object> answerCollection =answerDAO.getAnswerDTCollectionForRemoveToHist(rootDTInterface.getUid());
			if(answerCollection!=null && answerCollection.size()>0) {
				AnswerRootDAOImpl rootDAOImpl = new AnswerRootDAOImpl();
				//Create History record and delete it from NBS_ANSWER table.
				rootDAOImpl.history(answerCollection);
			}

			//insert Answers with LOG_DEL status
			answerDAO.storeAnswerDTCollection(logDelAnswerCollection, rootDTInterface);
			
			NbsActEntityDAO nbsActEntityDAO = new NbsActEntityDAO();
			Collection<Object> actEntityCollection = nbsActEntityDAO.getActEntityDTCollection(rootDTInterface.getUid());
			if(actEntityCollection!=null && actEntityCollection.size()>0) {
				NbsActEntityHistoryDAO nbsActEntityHistDAO = new NbsActEntityHistoryDAO();
				nbsActEntityHistDAO.insertPageEntityHistoryDTCollection(actEntityCollection, rootDTInterface);
				
				for (Iterator<Object> anIterator = actEntityCollection.iterator(); anIterator.hasNext();) {
					NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) anIterator.next();
					nbsActEntityDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
					nbsActEntityDT.setItNew(true);
				}
				rootDTInterface.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
				nbsActEntityDAO.storeActEntityDTCollection(actEntityCollection, rootDTInterface);
			}
			
			
			
		} catch (NEDSSSystemException e) {
			logger.fatal("logDelete Excepton: "+e.getMessage(), e);
			throw new NEDSSAppException("Exceptionw while LOG_DEL nbsActEntity: ", e);
		}
	}
	
	private Collection<Object> markAnswerForInsert(RootDTInterface rootDTInterface) throws NEDSSAppException{
		try{
			NbsAnswerDAO answerDAO= new NbsAnswerDAO();
			Collection<Object> answerCollection=null;
			answerCollection =answerDAO.getAnswerDTCollectionForRemoveToHist(rootDTInterface.getUid());
			if(answerCollection!=null && answerCollection.size()>0) {
				Iterator<Object> it = answerCollection.iterator();
				while (it.hasNext()) {
					Object obj=it.next();
					if(obj!=null && obj instanceof ArrayList<?> && ((ArrayList<Object>)obj).size()>0) {
						Iterator<NbsAnswerDT> iter = ((ArrayList<NbsAnswerDT>)obj).iterator();
						while(iter.hasNext()){
							NbsAnswerDT answerDT = iter.next();
							answerDT.setItNew(true);
						}
					}else if(obj!=null && obj instanceof NbsAnswerDT){
						NbsAnswerDT answerDT = (NbsAnswerDT)obj;
						answerDT.setItNew(true);
					}
				}
			}
			return answerCollection;
		}catch(Exception ex){
			logger.fatal("markAnswerForInsert Excepton: "+ex.getMessage(), ex);
			throw new NEDSSAppException("Exceptionw while LOG_DEL NbsAnswerDT: ", ex);
		}
	}
	
}

