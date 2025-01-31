package gov.cdc.nedss.proxy.ejb.pamproxyejb.vo;

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;

/**
 * Name: PamProxyVO.java Description: PamProxyVO Object for Pam Copyright:
 * Copyright(c) 2008 Company: Computer Sciences Corporation
 *
 * @author Pradeep Sharma
 */

public class PamProxyVO extends AbstractVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private PublicHealthCaseVO publicHealthCaseVO;

	private Collection<Object>  thePersonVOCollection;

	private PamVO pamVO;

	private Collection<Object>  theVaccinationSummaryVOCollection;

	private Collection<Object>  theNotificationSummaryVOCollection;

	private Collection<Object>  theTreatmentSummaryVOCollection;

	private Collection<Object>  theLabReportSummaryVOCollection;

	private Collection<Object>  theMorbReportSummaryVOCollection;

	private Collection<Object>  theParticipationDTCollection;

	private Collection<Object>  theInvestigationAuditLogSummaryVOCollection;

	private Collection<Object>  theOrganizationVOCollection;

	 public Collection<Object>  theNotificationVOCollection;
	private boolean associatedNotificationsInd;

	private NotificationVO  theNotificationVO;
	
	public Collection<Object>  theDocumentSummaryVOCollection; 
	private boolean isOOSystemInd;
	private boolean isOOSystemPendInd;
	private Collection<Object> theCTContactSummaryDTCollection;
	
	private Collection<Object> nbsAttachmentDTColl;
	private Collection<Object> nbsNoteDTColl; 
	
	private boolean isUnsavedNote;
private ExportReceivingFacilityDT exportReceivingFacilityDT;
	
	public ExportReceivingFacilityDT getExportReceivingFacilityDT() {
		return exportReceivingFacilityDT;
	}

	public void setExportReceivingFacilityDT(
			ExportReceivingFacilityDT exportReceivingFacilityDT) {
		this.exportReceivingFacilityDT = exportReceivingFacilityDT;
	}
	
	public boolean isUnsavedNote() {
		return isUnsavedNote;
	}

	public void setUnsavedNote(boolean isUnsavedNote) {
		this.isUnsavedNote = isUnsavedNote;
	}

	public Collection<Object> getNbsAttachmentDTColl() {
		return nbsAttachmentDTColl;
	}

	public void setNbsAttachmentDTColl(Collection<Object> nbsAttachmentDTColl) {
		this.nbsAttachmentDTColl = nbsAttachmentDTColl;
	}

	public Collection<Object> getNbsNoteDTColl() {
		return nbsNoteDTColl;
	}

	public void setNbsNoteDTColl(Collection<Object> nbsNoteDTColl) {
		this.nbsNoteDTColl = nbsNoteDTColl;
	}

	public Collection<Object>  getTheLabReportSummaryVOCollection() {
		return theLabReportSummaryVOCollection;
	}

	public Collection<Object> getTheCTContactSummaryDTCollection() {
		return theCTContactSummaryDTCollection;
	}

	public void setTheCTContactSummaryDTCollection(
			Collection<Object> theCTContactSummaryDTCollection) {
		this.theCTContactSummaryDTCollection = theCTContactSummaryDTCollection;
	}

	public boolean isOOSystemInd() {
		return isOOSystemInd;
	}

	public void setOOSystemInd(boolean isOOSystemInd) {
		this.isOOSystemInd = isOOSystemInd;
	}

	public void setTheLabReportSummaryVOCollection(Collection<Object> theLabReportSummaryVOCollection) {
		this.theLabReportSummaryVOCollection  = theLabReportSummaryVOCollection;
	}

	public Collection<Object>  getTheMorbReportSummaryVOCollection() {
		return theMorbReportSummaryVOCollection;
	}

	public void setTheMorbReportSummaryVOCollection(Collection<Object> theMorbReportSummaryVOCollection) {
		this.theMorbReportSummaryVOCollection  = theMorbReportSummaryVOCollection;
	}

	public Collection<Object>  getTheNotificationSummaryVOCollection() {
		return theNotificationSummaryVOCollection;
	}

	public void setTheNotificationSummaryVOCollection(Collection<Object> theNotificationSummaryVOCollection) {
		this.theNotificationSummaryVOCollection  = theNotificationSummaryVOCollection;
	}

	public Collection<Object>  getTheTreatmentSummaryVOCollection() {
		return theTreatmentSummaryVOCollection;
	}

	public void setTheTreatmentSummaryVOCollection(Collection<Object> theTreatmentSummaryVOCollection) {
		this.theTreatmentSummaryVOCollection  = theTreatmentSummaryVOCollection;
	}

	public Collection<Object>  getTheVaccinationSummaryVOCollection() {
		return theVaccinationSummaryVOCollection;
	}

	public void setTheVaccinationSummaryVOCollection(Collection<Object> theVaccinationSummaryVOCollection) {
		this.theVaccinationSummaryVOCollection  = theVaccinationSummaryVOCollection;
	}

	public PamVO getPamVO() {
		if(pamVO==null)
			pamVO = new PamVO();
		return pamVO;
	}

	public void setPamVO(PamVO pamVO) {
		this.pamVO = pamVO;
	}

	public PublicHealthCaseVO getPublicHealthCaseVO() {
		if(publicHealthCaseVO==null)
			publicHealthCaseVO= new PublicHealthCaseVO();
		return publicHealthCaseVO;
	}

	public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO) {
		this.publicHealthCaseVO = publicHealthCaseVO;
	}

	/**
	 *
	 * @param aItDirty
	 */
	public void setItDirty(boolean aItDirty) {
		itDirty = aItDirty;
	}

	/**
	 *
	 * @return boolean
	 */
	public boolean isItDirty() {

		return itDirty;
	}

	/**
	 *
	 * @param aItNew
	 */
	public void setItNew(boolean aItNew) {
		itNew = aItNew;
	}

	/**
	 *
	 * @return boolean
	 */
	public boolean isItNew() {

		return itNew;
	}

	/**
	 *
	 * @return boolean
	 */
	public boolean isItDelete() {

		return itDelete;
	}

	/**
	 *
	 * @param aItDelete
	 */
	public void setItDelete(boolean aItDelete) {
		itDelete = aItDelete;
	}

	/**
	 *
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {

		return true;
	}

	public Collection<Object>  getThePersonVOCollection() {
		return thePersonVOCollection;
	}

	public void setThePersonVOCollection(Collection<Object> thePersonVOCollection) {
		this.thePersonVOCollection  = thePersonVOCollection;
	}

	public Collection<Object>  getTheParticipationDTCollection() {
		return theParticipationDTCollection;
	}

	public void setTheParticipationDTCollection(
			Collection<Object>  theParticipationDTCollection) {
		this.theParticipationDTCollection  = theParticipationDTCollection;
	}

	public boolean getAssociatedNotificationsInd() {
		return associatedNotificationsInd;
	}

	public void setAssociatedNotificationsInd(boolean associatedNotificationsInd) {
		this.associatedNotificationsInd = associatedNotificationsInd;
	}

	public Collection<Object>  getTheInvestigationAuditLogSummaryVOCollection() {
		return theInvestigationAuditLogSummaryVOCollection;
	}

	public void setTheInvestigationAuditLogSummaryVOCollection(
			Collection<Object>  theInvestigationAuditLogVOCollection) {
		this.theInvestigationAuditLogSummaryVOCollection  = theInvestigationAuditLogVOCollection;
	}

	public Collection<Object>  getTheOrganizationVOCollection() {
		return theOrganizationVOCollection;
	}

	public void setTheOrganizationVOCollection(
			Collection<Object>  theOrganizationVOCollection) {
		this.theOrganizationVOCollection  = theOrganizationVOCollection;
	}
	public NotificationVO getNotificationVO_s()
	   {
	      if (this.theNotificationVO == null)
	        this.theNotificationVO = new NotificationVO();

	      return this.theNotificationVO;
	   }

	public Collection<Object>  getTheNotificationVOCollection() {
		return theNotificationVOCollection;
	}

	public void setTheNotificationVOCollection(
			Collection<Object>  theNotificationVOCollection) {
		this.theNotificationVOCollection  = theNotificationVOCollection;
		 setItDirty(true);
	}

	public Collection<Object>  getTheDocumentSummaryVOCollection() {
		return theDocumentSummaryVOCollection;
	}

	public void setTheDocumentSummaryVOCollection(
			Collection<Object>  theDocumentSummaryVOCollection) {
		this.theDocumentSummaryVOCollection  = theDocumentSummaryVOCollection;
	}

	public boolean isOOSystemPendInd() {
		return isOOSystemPendInd;
	}

	public void setOOSystemPendInd(boolean isOOSystemPendInd) {
		this.isOOSystemPendInd = isOOSystemPendInd;
	}



}