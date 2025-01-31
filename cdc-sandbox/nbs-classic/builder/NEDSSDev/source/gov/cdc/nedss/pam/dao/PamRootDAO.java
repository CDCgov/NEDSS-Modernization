package gov.cdc.nedss.pam.dao;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityDAO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Name:		PamRootDAO.java
 * Description:	DAO Object for PAM answers.
 * Copyright:	Copyright (c) 2008
 * Company: 	Computer Sciences Corporation
 * @author	Pradeep Sharma
 */
public class PamRootDAO {
    static final LogUtils logger = new LogUtils(PamRootDAO.class.getName());
	/**
	 * gets the PamVO Object for a given publicHealthCaseUID
	 * @return PamVO
	 */
	@SuppressWarnings("unchecked")
	public PamVO getPamVO(Long publicHealthCaseUID) {
		PamVO pamVO = new PamVO();
		try{
			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			Map<Object,Object> pamAnswerDTReturnMap =nbsAnswerDAO.getPamAnswerDTMaps(publicHealthCaseUID);
			Map<Object, Object> nbsAnswerMap =new HashMap<Object, Object>();
			Map<Object, Object> nbsRepeatingAnswerMap =new HashMap<Object, Object>();
			if(pamAnswerDTReturnMap.get(NEDSSConstants.NON_REPEATING_QUESTION)!=null){
				nbsAnswerMap=(HashMap<Object, Object>)pamAnswerDTReturnMap.get(NEDSSConstants.NON_REPEATING_QUESTION);
			}
			if(pamAnswerDTReturnMap.get(NEDSSConstants.REPEATING_QUESTION)!=null){
				nbsRepeatingAnswerMap=(HashMap<Object, Object>)pamAnswerDTReturnMap.get(NEDSSConstants.REPEATING_QUESTION);
			}
			pamVO.setPamAnswerDTMap(nbsAnswerMap);
			pamVO.setPageRepeatingAnswerDTMap(nbsRepeatingAnswerMap);
			
			NbsActEntityDAO pamCaseEntityDAO = new NbsActEntityDAO();
			Collection<Object>  pamCaseEntityDTCollection=pamCaseEntityDAO.getActEntityDTCollection(publicHealthCaseUID);
			pamVO.setActEntityDTCollection(pamCaseEntityDTCollection);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pamVO;
	}

	public void insertPamVO(PamVO pamVO,PublicHealthCaseVO publichHealthCaseVO) throws NEDSSSystemException{
		try {
			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			Collection<Object>  pamDTCollection  =new ArrayList<Object> ();
			Collection<Object>  repeatingAnswerDTCollection  =new ArrayList<Object> ();
			if(pamVO.getPamAnswerDTMap()!=null){
				pamDTCollection= pamVO.getPamAnswerDTMap().values();
			}
			nbsAnswerDAO.storePamAnswerDTCollection(pamDTCollection, publichHealthCaseVO);
			if(pamVO.getPageRepeatingAnswerDTMap()!=null){
				repeatingAnswerDTCollection= pamVO.getPageRepeatingAnswerDTMap().values();
			}
			nbsAnswerDAO.storePamAnswerDTCollection(repeatingAnswerDTCollection, publichHealthCaseVO);
			NbsActEntityDAO pamCaseEntityDAO = new NbsActEntityDAO();
			pamCaseEntityDAO.storeActEntityDTCollection(pamVO.getActEntityDTCollection(),  publichHealthCaseVO.getThePublicHealthCaseDT());
		} catch (NEDSSSystemException e) {
            logger.fatal("Error while insertPamVO:-"+e.getMessage() ,e);
			throw new NEDSSSystemException(e.toString());
		}
	}

	public PublicHealthCaseVO editPamVO(PamVO pamVO, PublicHealthCaseVO publichHealthCaseVO) throws NEDSSSystemException{
		try {
		

			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			Collection<Object>  pamDTCollection  =new ArrayList<Object> ();
			Collection<Object>  repeatingAnswerDTCollection  =new ArrayList<Object> ();
			if(pamVO.getPamAnswerDTMap()!=null){
				pamDTCollection= pamVO.getPamAnswerDTMap().values();
				if(pamDTCollection!=null)
			    logger.debug("********#New AnswerMapSize in PAM DAO:"+pamDTCollection.size()+"");

			}
			nbsAnswerDAO.storePamAnswerDTCollection(pamDTCollection,publichHealthCaseVO);
			if(pamVO.getPageRepeatingAnswerDTMap()!=null){
				repeatingAnswerDTCollection= pamVO.getPageRepeatingAnswerDTMap().values();
				if(repeatingAnswerDTCollection!=null)
			    logger.debug("********#New AnswerBatchMapSize in PAM DAO:"+repeatingAnswerDTCollection.size()+"");
			}
			nbsAnswerDAO.storePamAnswerDTCollection(repeatingAnswerDTCollection, publichHealthCaseVO);
			NbsActEntityDAO pamCaseEntityDAO = new NbsActEntityDAO();
			pamCaseEntityDAO.storeActEntityDTCollection(pamVO.getActEntityDTCollection(),  publichHealthCaseVO.getThePublicHealthCaseDT());
		} catch (NEDSSSystemException e) {
            logger.fatal("Error while editPamVO:-"+e.getMessage() ,e);
			throw new NEDSSSystemException(e.toString());
		}
		return publichHealthCaseVO;
	}
	public PublicHealthCaseVO UpdateFordeletePamVO(PamVO pamVO, PublicHealthCaseVO publichHealthCaseVO) throws NEDSSSystemException{
		try {
			
			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			nbsAnswerDAO.logDelPamAnswerDTCollection(pamVO.getPamAnswerDTMap(),	publichHealthCaseVO);
			nbsAnswerDAO.logDelPamAnswerDTCollection(pamVO.getPageRepeatingAnswerDTMap(),	publichHealthCaseVO);
			
			NbsActEntityDAO pamCaseEntityDAO = new NbsActEntityDAO();
			pamCaseEntityDAO.logDelActEntityDTCollection(pamVO.getActEntityDTCollection(), publichHealthCaseVO.getThePublicHealthCaseDT());
		} catch (NEDSSSystemException e) {
            logger.fatal("Error while editPamVO:-"+e.getMessage() ,e);
			throw new NEDSSSystemException(e.toString());
		}
		return publichHealthCaseVO;
	}
	
	public Collection<Object>  getSummaryPamCaseAnswerCollection(Long publicHealthCaseUID) {
		try {
			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			Collection<Object>  coll =nbsAnswerDAO.getSummaryPamAnswerDTCollection(publicHealthCaseUID);
			return coll;
		} catch (NEDSSSystemException e) {
			logger.fatal("Error while getPamCaseAnswerCollection:-"+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
	}
	
	public void setPamCaseAnswerCollection(Collection<Object> pamCaseAnswerDTCollection, PublicHealthCaseVO publichHealthCaseVO) {
		try {
			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			nbsAnswerDAO.storePamAnswerDTCollection(pamCaseAnswerDTCollection, publichHealthCaseVO);
		} catch (NEDSSSystemException e) {
			logger.fatal("Error while setPamCaseAnswerCollection:-"+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
	}
}
