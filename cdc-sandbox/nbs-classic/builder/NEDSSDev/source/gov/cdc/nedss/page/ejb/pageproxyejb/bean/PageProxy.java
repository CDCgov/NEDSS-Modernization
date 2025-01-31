package gov.cdc.nedss.page.ejb.pageproxyejb.bean;


import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;


/**PageProxy is Release 4.0 EJB interface to store and retrieve Pages. 
 * PageProxyEJB stores the Public_health_case, Participations, act_relationship, nbs_case_answer,
 * NBS_CASE_answer_hist, act_entity, Pam_case_entity_hist. 
 * @author Pradeep Sharma
 *@since: NBS Release 4.0
 */


public interface PageProxy extends javax.ejb.EJBObject {

	public PageProxyVO getPageProxyVO(String typeCd, long uid, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException;
	public long setPageProxyVO(String typeCd,PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException;
	public long setPageProxyVOMerge(String typeCd,PageProxyVO pageProxyVO, PageProxyVO supersededProxyVO,PageProxyVO survivorProxyOld, String localIdSurvivor,String localIdSuperseded, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException;
	public void transferOwnership(String typeCd, Long publicHealthCaseUID,PageActProxyVO pageProxyVO, String newJurisdictionCode, Boolean isExportCase, NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, NEDSSSystemException,NEDSSConcurrentDataException;
	
	public Long setPageProxyWithAutoAssoc(String typeCd,PageProxyVO pageProxyVO, Long observationUid, String observationTypeCd, String processingDecision, NBSSecurityObj nBSSecurityObj) 
	throws javax.ejb.EJBException, javax.ejb.CreateException, java.rmi.RemoteException, NEDSSSystemException, NEDSSConcurrentDataException;
	
	public Map<Object,Object> deletePageProxy(String typeCd,Long uid,   NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, javax.ejb.FinderException, NEDSSConcurrentDataException;
	public Map<Object,Object> checkAssociationBeforeDelete(String typeCd, Long uid,   NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, javax.ejb.FinderException, NEDSSConcurrentDataException;
	public Long exportOwnership(String typeCd, NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException, java.rmi.RemoteException;
	
	public Long setChangeConditionInNewCase(String typeCd, Long oldPublicHealthCaseUID,	PageActProxyVO newPageProxyVO,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, javax.ejb.CreateException, java.rmi.RemoteException, NEDSSSystemException, NEDSSConcurrentDataException;
	
	public Long setNBSAttachment(NBSAttachmentDT nbsAttachmentDT, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException;
	public byte[] getNBSAttachment(Long nbsAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public void deleteNbsAttachment(Long nbsAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public void updateEpilink (String currEpilink, String newEpilink, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
	public long setAutoContactPageProxyVO(String typeCd,CTContactProxyVO cTContactProxyVO,PageProxyVO pageProxyVO,  NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException;
	public void changeCondition(String typeCd, Long publicHealthCaseUID,PageActProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, NEDSSSystemException;
	public void setEventCaseAssociations(Long nbsEventUid, String actType, ArrayList<Long> associatedInvestigationList, ArrayList<Long> disassociatedInvestigationList ,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;	    
	public Collection<Object> getCoinfectionSummaryVOByCoInfectionId(String coninfectionId,Long mprUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;
	public boolean checkForExistingNotificationsByCdsAndUid(String classCd, String typeCd, Long uid,   NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,javax.ejb.FinderException, NEDSSConcurrentDataException;
	public Long setNBSNote(NBSNoteDT nbsNoteDT, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
	public boolean checkForExistingNotificationsByPublicHealthUid(Long uid,   NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,javax.ejb.FinderException, NEDSSConcurrentDataException;
}
