package gov.cdc.nedss.proxy.ejb.pamproxyejb.bean;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.sql.Blob;
import java.util.Collection;
import java.util.Map;

import javax.ejb.CreateException;

public interface PamProxy extends javax.ejb.EJBObject {

	public Long setPamProxy(PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;

	public PamProxyVO getPamProxy(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException,
			javax.ejb.CreateException;

	public void transferOwnership(Long publicHealthCaseUID,	PamProxyVO pamProxyVO,  String newJurisdictionCode, Boolean isExportCase,NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

	public Long setPamProxyWithAutoAssoc(PamProxyVO pamProxyVO, Long observationUid, String observationTypeCd, NBSSecurityObj nBSSecurityObj) 
			throws javax.ejb.EJBException, javax.ejb.CreateException, java.rmi.RemoteException, NEDSSSystemException, 
			NEDSSConcurrentDataException;

	public Map<Object,Object> deletePamProxy(Long publicHealthCaseUid,   NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			javax.ejb.FinderException, NEDSSConcurrentDataException;

	public String getRootExtensionTxt(String formCode, int seqNum, String caseNumber, String caseLocalId) 
			throws javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException;		
	
	public Long setAggregateSummary(SummaryReportProxyVO summaryReportProxyVO, NBSSecurityObj nbsSecurityObj) 
			throws NEDSSSystemException, NEDSSConcurrentDataException, RemoteException, CreateException,NEDSSAppException;
	
	public Long exportOwnership( NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) 
			throws javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException, java.rmi.RemoteException;
	
	public Collection<Object> getAggregateSummaryColl(Map map, NBSSecurityObj nbsSecurityObj)  
			throws NEDSSSystemException, NEDSSConcurrentDataException, RemoteException, CreateException;
	
	public  SummaryReportProxyVO getAggregateSummary(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) 
			throws javax.ejb.EJBException, javax.ejb.CreateException, java.rmi.RemoteException,	NEDSSSystemException;
	
	public Long setContactProxyVO(CTContactProxyVO cTContactProxyVO, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	
	public CTContactProxyVO getContactProxyVO(Long ctContactUid, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	
	public Long setContactAttachment(CTContactAttachmentDT ctContactAttachmentDT, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	
	public byte[] getContactAttachment(Long ctContactAttachmentUid, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	
	public Collection<Object> getContactAttachmentSummaryCollection(Long ctContactUid, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;

	public void deleteContactAttachment(Long ctContactAttachmentUid, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;

	/*
	public Collection<Object> getContactNotesCollection(Long ctContactUid, String userId, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	*/		
	
	public Long setContactNote(CTContactNoteDT ctContactNoteDT, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, 
			NEDSSSystemException, NEDSSConcurrentDataException;
	
	public Collection<Object> getNamedAsContactSummaryByCondition(Long publicHealthCaseUID, Long mprUid, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;

	public void updateContactAssociations(Long publicHealthCaseUID, Collection<Object> addContactAssociationUIDs, Collection<Object> removeContactAssociationsUIDs, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
			NEDSSConcurrentDataException;
	public PublicHealthCaseVO getPublicHealthCaseVO(Long publicHealthCaseUid,NBSSecurityObj nbsSecurityObj)throws  javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException;
	
	public Collection<Object> getInterviewSummaryforInvestigation(Long publicHealthCaseUid, String programArea,  NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;
	
}
