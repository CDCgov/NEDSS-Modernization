package gov.cdc.nedss.page.ejb.pageproxyejb.vo.act;

import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.util.AbstractVO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Name: PageActProxyVO.java Description: PageActProxyVO Object for Dynamic
 * Pages Copyright(c) 2010 Company: CSC
 * 
 * @since: NBS4.0
 * @author Pradeep Sharma
 * @updated Pradeep Kumar Sharma
 * @since 4.5
 */

public class PageActProxyVO extends AbstractVO implements PageProxyVO
{
    private static final long         serialVersionUID = 1L;

    public String                     pageProxyTypeCd  = "";
    // page business type INV, IXS, etc.
    private PublicHealthCaseVO        publicHealthCaseVO;
    private InterviewVO               interviewVO;
    private NotificationVO            theNotificationVO;
    private InterventionVO			  interventionVO;
    
    private Long                      patientUid;
    private String                    currentInvestigator;
    private String                    fieldSupervisor;
    private String                    caseSupervisor;
    private boolean                   isSTDProgramArea = false;
    private Collection<Object>        thePersonVOCollection;

    private PamVO                     pageVO;
    // contains answer maps

    private Collection<Object>        theVaccinationSummaryVOCollection;
    private Collection<Object>        theNotificationSummaryVOCollection;
    private Collection<Object>        theTreatmentSummaryVOCollection;
    private Collection<Object>        theLabReportSummaryVOCollection;
    private Collection<Object>        theMorbReportSummaryVOCollection;
    private Collection<Object>        theParticipationDTCollection;

    private Collection<Object>        theActRelationshipDTCollection;
    private Collection<Object>        theInvestigationAuditLogSummaryVOCollection;
    private Collection<Object>        theOrganizationVOCollection;
    private Collection<Object>        theCTContactSummaryDTCollection;
    private Collection<Object>        theInterviewSummaryDTCollection;
    private Collection<Object>        theNotificationVOCollection;
    private Collection<Object>        theCSSummaryVOCollection;
    private Collection<Object>        nbsAttachmentDTColl;
    private Collection<Object>        nbsNoteDTColl;
    private Collection<Object>        theDocumentSummaryVOCollection;
    private boolean                   isOOSystemInd;
    private boolean                   isOOSystemPendInd;
    private boolean                   associatedNotificationsInd;
    private boolean                   isUnsavedNote;
    private boolean                   isMergeCase;
    
    
    private Collection<Object>		 theEDXDocumentDTCollection;
    

	private boolean                   isRenterant;
    private boolean					  isConversionHasModified;

    private ExportReceivingFacilityDT exportReceivingFacilityDT;

    public boolean isMergeCase() {
		return isMergeCase;
	}

	public void setMergeCase(boolean isMergeCase) {
		this.isMergeCase = isMergeCase;
	}
    private Map<String, MessageLogDT> messageLogDTMap  = new HashMap<String, MessageLogDT>();

    public boolean isRenterant()
    {
        return isRenterant;
    }

    public void setRenterant(boolean isRenterant)
    {
        this.isRenterant = isRenterant;
    }

    public Collection<Object> getTheActRelationshipDTCollection()
    {
        return theActRelationshipDTCollection;
    }

    public void setTheActRelationshipDTCollection(Collection<Object> theActRelationshipDTCollection)
    {
        this.theActRelationshipDTCollection = theActRelationshipDTCollection;
    }

    public boolean isSTDProgramArea()
    {
        return isSTDProgramArea;
    }

    public Long getPatientUid()
    {
        return patientUid;
    }

    public void setPatientUid(Long patientUid)
    {
        this.patientUid = patientUid;
    }

    public void setSTDProgramArea(boolean isSTDProgramArea)
    {
        this.isSTDProgramArea = isSTDProgramArea;
    }

    public String getFieldSupervisor()
    {
        return fieldSupervisor;
    }

    public void setFieldSupervisor(String fieldSupervisor)
    {
        this.fieldSupervisor = fieldSupervisor;
    }

    public String getCaseSupervisor()
    {
        return caseSupervisor;
    }

    public void setCaseSupervisor(String caseSupervisor)
    {
        this.caseSupervisor = caseSupervisor;
    }

    public Map<String, MessageLogDT> getMessageLogDTMap()
    {
        return messageLogDTMap;
    }

    public void setMessageLogDTMap(Map<String, MessageLogDT> messageLogDTMap)
    {
        this.messageLogDTMap = messageLogDTMap;
    }

    public ExportReceivingFacilityDT getExportReceivingFacilityDT()
    {
        return exportReceivingFacilityDT;
    }

    public void setExportReceivingFacilityDT(ExportReceivingFacilityDT exportReceivingFacilityDT)
    {
        this.exportReceivingFacilityDT = exportReceivingFacilityDT;
    }

    public boolean isUnsavedNote()
    {
        return isUnsavedNote;
    }

    public void setUnsavedNote(boolean isUnsavedNote)
    {
        this.isUnsavedNote = isUnsavedNote;
    }

    public Collection<Object> getTheLabReportSummaryVOCollection()
    {
        return theLabReportSummaryVOCollection;
    }

    public Collection<Object> getTheCTContactSummaryDTCollection()
    {
        return theCTContactSummaryDTCollection;
    }

    public void setTheCTContactSummaryDTCollection(Collection<Object> theCTContactSummaryDTCollection)
    {
        this.theCTContactSummaryDTCollection = theCTContactSummaryDTCollection;
    }

    public boolean isOOSystemInd()
    {
        return isOOSystemInd;
    }

    public void setOOSystemInd(boolean isOOSystemInd)
    {
        this.isOOSystemInd = isOOSystemInd;
    }

    public void setTheLabReportSummaryVOCollection(Collection<Object> theLabReportSummaryVOCollection)
    {
        this.theLabReportSummaryVOCollection = theLabReportSummaryVOCollection;
    }

    public Collection<Object> getTheMorbReportSummaryVOCollection()
    {
        return theMorbReportSummaryVOCollection;
    }

    public void setTheMorbReportSummaryVOCollection(Collection<Object> theMorbReportSummaryVOCollection)
    {
        this.theMorbReportSummaryVOCollection = theMorbReportSummaryVOCollection;
    }

    public Collection<Object> getTheNotificationSummaryVOCollection()
    {
        return theNotificationSummaryVOCollection;
    }

    public void setTheNotificationSummaryVOCollection(Collection<Object> theNotificationSummaryVOCollection)
    {
        this.theNotificationSummaryVOCollection = theNotificationSummaryVOCollection;
    }

    public Collection<Object> getTheTreatmentSummaryVOCollection()
    {
        return theTreatmentSummaryVOCollection;
    }

    public void setTheTreatmentSummaryVOCollection(Collection<Object> theTreatmentSummaryVOCollection)
    {
        this.theTreatmentSummaryVOCollection = theTreatmentSummaryVOCollection;
    }

    public Collection<Object> getTheVaccinationSummaryVOCollection()
    {
        return theVaccinationSummaryVOCollection;
    }

    public void setTheVaccinationSummaryVOCollection(Collection<Object> theVaccinationSummaryVOCollection)
    {
        this.theVaccinationSummaryVOCollection = theVaccinationSummaryVOCollection;
    }

    public PamVO getPageVO()
    {
        if (pageVO == null)
            pageVO = new PageVO();
        return pageVO;
    }

    public void setPageVO(PamVO pageVO)
    {
        this.pageVO = pageVO;
    }

    public PublicHealthCaseVO getPublicHealthCaseVO()
    {
        if (publicHealthCaseVO == null)
            publicHealthCaseVO = new PublicHealthCaseVO();
        return publicHealthCaseVO;
    }

    public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO)
    {
        this.publicHealthCaseVO = publicHealthCaseVO;
    }

    public InterventionVO getInterventionVO() {
		return interventionVO;
	}

	public void setInterventionVO(InterventionVO interventionVO) {
		this.interventionVO = interventionVO;
	}

	/**
     * 
     * @param aItDirty
     */
    public void setItDirty(boolean aItDirty)
    {
        itDirty = aItDirty;
    }

    /**
     * 
     * @return boolean
     */
    public boolean isItDirty()
    {

        return itDirty;
    }

    /**
     * 
     * @param aItNew
     */
    public void setItNew(boolean aItNew)
    {
        itNew = aItNew;
    }

    /**
     * 
     * @return boolean
     */
    public boolean isItNew()
    {

        return itNew;
    }

    /**
     * 
     * @return boolean
     */
    public boolean isItDelete()
    {

        return itDelete;
    }

    /**
     * 
     * @param aItDelete
     */
    public void setItDelete(boolean aItDelete)
    {
        itDelete = aItDelete;
    }

    /**
     * 
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {

        return true;
    }

    public Collection<Object> getThePersonVOCollection()
    {
        return thePersonVOCollection;
    }

    public void setThePersonVOCollection(Collection<Object> thePersonVOCollection)
    {
        this.thePersonVOCollection = thePersonVOCollection;
    }

    public Collection<Object> getTheParticipationDTCollection()
    {
        return theParticipationDTCollection;
    }

    public void setTheParticipationDTCollection(Collection<Object> theParticipationDTCollection)
    {
        this.theParticipationDTCollection = theParticipationDTCollection;
    }

    public boolean getAssociatedNotificationsInd()
    {
        return associatedNotificationsInd;
    }

    public void setAssociatedNotificationsInd(boolean associatedNotificationsInd)
    {
        this.associatedNotificationsInd = associatedNotificationsInd;
    }

    public Collection<Object> getTheInvestigationAuditLogSummaryVOCollection()
    {
        return theInvestigationAuditLogSummaryVOCollection;
    }

    public void setTheInvestigationAuditLogSummaryVOCollection(Collection<Object> theInvestigationAuditLogVOCollection)
    {
        this.theInvestigationAuditLogSummaryVOCollection = theInvestigationAuditLogVOCollection;
    }

    public Collection<Object> getTheOrganizationVOCollection()
    {
        return theOrganizationVOCollection;
    }

    public void setTheOrganizationVOCollection(Collection<Object> theOrganizationVOCollection)
    {
        this.theOrganizationVOCollection = theOrganizationVOCollection;
    }

    public NotificationVO getNotificationVO_s()
    {
        if (this.theNotificationVO == null)
            this.theNotificationVO = new NotificationVO();

        return this.theNotificationVO;
    }

    public void setNotificationVO_s(NotificationVO notificationVO)
    {
       this.theNotificationVO = notificationVO;
    }
    
    public Collection<Object> getTheNotificationVOCollection()
    {
        return theNotificationVOCollection;
    }

    public void setTheNotificationVOCollection(Collection<Object> theNotificationVOCollection)
    {
        this.theNotificationVOCollection = theNotificationVOCollection;
        setItDirty(true);
    }

    public Collection<Object> getTheDocumentSummaryVOCollection()
    {
        return theDocumentSummaryVOCollection;
    }

    public void setTheDocumentSummaryVOCollection(Collection<Object> theDocumentSummaryVOCollection)
    {
        this.theDocumentSummaryVOCollection = theDocumentSummaryVOCollection;
    }

    public boolean isOOSystemPendInd()
    {
        return isOOSystemPendInd;
    }

    public void setOOSystemPendInd(boolean isOOSystemPendInd)
    {
        this.isOOSystemPendInd = isOOSystemPendInd;
    }

    public Collection<Object> getNbsAttachmentDTColl()
    {
        return nbsAttachmentDTColl;
    }

    public void setNbsAttachmentDTColl(Collection<Object> nbsAttachmentDTColl)
    {
        this.nbsAttachmentDTColl = nbsAttachmentDTColl;
    }

    public Collection<Object> getNbsNoteDTColl()
    {
        return nbsNoteDTColl;
    }

    public void setNbsNoteDTColl(Collection<Object> nbsNoteDTColl)
    {
        this.nbsNoteDTColl = nbsNoteDTColl;
    }

    public String getCurrentInvestigator()
    {
        return currentInvestigator;
    }

    public void setCurrentInvestigator(String currentInvestigator)
    {
        this.currentInvestigator = currentInvestigator;
    }

    /**
     * When on an Interview Page, we have a single Interview
     * 
     * @return the interviewVO
     */
    public InterviewVO getInterviewVO()
    {
        return interviewVO;
    }

    /**
     * @param interviewVO
     *            the interviewVO to set
     */
    public void setInterviewVO(InterviewVO interviewVO)
    {
        this.interviewVO = interviewVO;
    }

    /**
     * @return InterviewSummaryDTCollection
     * @see InterviewSummaryDT
     */
    public Collection<Object> getTheInterviewSummaryDTCollection()
    {
        return theInterviewSummaryDTCollection;
    }

    public void setTheInterviewSummaryDTCollection(Collection<Object> theInterviewSummaryDTCollection)
    {
        this.theInterviewSummaryDTCollection = theInterviewSummaryDTCollection;
    }

    /**
     * This is set to the Business Object Type
     * 
     * @return the pageProxyTypeCd
     */
    public String getPageProxyTypeCd()
    {
        return pageProxyTypeCd;
    }

    /**
     * @param pageProxyTypeCd
     *            the pageProxyTypeCd to set
     */
    public void setPageProxyTypeCd(String pageProxyTypeCd)
    {
        this.pageProxyTypeCd = pageProxyTypeCd;
    }

    public Collection<Object> getTheCSSummaryVOCollection()
    {
        return theCSSummaryVOCollection;
    }

    public void setTheCSSummaryVOCollection(Collection<Object> theCSSummaryVOCollection)
    {
        this.theCSSummaryVOCollection = theCSSummaryVOCollection;
    }

	public boolean isConversionHasModified() {
		return isConversionHasModified;
	}

	public void setConversionHasModified(boolean isConversionHasModified) {
		this.isConversionHasModified = isConversionHasModified;
	}
	
	  public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
	  {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      ObjectOutputStream oos = new ObjectOutputStream(baos);
	      oos.writeObject(this);
	      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	      ObjectInputStream ois = new ObjectInputStream(bais);
	      Object deepCopy = ois.readObject();

	      return  deepCopy;
	  }

	public Collection<Object> getTheEDXDocumentDTCollection() {
		return theEDXDocumentDTCollection;
	}

	public void setTheEDXDocumentDTCollection(
			Collection<Object> theEDXDocumentDTCollection) {
		this.theEDXDocumentDTCollection = theEDXDocumentDTCollection;
	}
	
}
