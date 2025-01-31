package gov.cdc.nedss.act.publichealthcase.dt;

import gov.cdc.nedss.act.publichealthcase.ejb.dao.CaseManagementDAOImpl;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * Data Transfer object to create, view , edit Case Management Data(DT Object
 * since Release 4.5)
 * 
 * @author Pradeep Kumar Sharma
 * 
 */

public class CaseManagementDT extends AbstractVO implements RootDTInterface
{

    /**
     * System generated serial version uid
     */
    private static final long serialVersionUID = -5127476121435352079L;
    static final LogUtils logger = new LogUtils(CaseManagementDT.class.getName());
    
    private Long              caseManagementUid;
    private Long              publicHealthCaseUid;
    private String            status900;
    private String            eharsId;
    private String            epiLinkId;
    private String            fieldFollUpOojOutcome;
    private String            fieldRecordNumber;
    private String            fldFollUpDispo;
    private Timestamp         fldFollUpDispoDate;
    private Timestamp         fldFollUpExamDate;
    private Timestamp         fldFollUpExpectedDate;
    private String            fldFollUpExpectedIn;
    private String            fldFollUpInternetOutcome;
    private String            fldFollUpNotificationPlan;
    private String            fldFollUpProvDiagnosis;
    private String            fldFollUpProvExmReason;
    private String            initFollUp;
    private String            initFollUpClinicCode;
    private Timestamp         initFollUpClosedDate;
    private String            initFollUpNotifiable;
    private String            internetFollUp;
    private String            oojAgency;
    private Timestamp         oojDueDate;
    private String            oojNumber;
    private String            patIntvStatusCd;
    private String            subjComplexion;
    private String            subjHair;
    private String           subjHeight;
    private String            subjOthIdntfyngInfo;
    private String            subjSizeBuild;
    private Timestamp         survClosedDate;
    private String            survPatientFollUp;
    private String            survProvDiagnosis;
    private String            survProvExmReason;
    private String            survProviderContact;
    private String            actRefTypeCd;
    private String            initiatingAgncy;
    private Timestamp         oojInitgAgncyOutcDueDate;
    private Timestamp         oojInitgAgncyOutcSntDate;
    private Timestamp         oojInitgAgncyRecdDate;
    public boolean            isCaseManagementDTPopulated;
    public String             caseReviewStatus;
    private Timestamp         survAssignedDate;
    private Timestamp         follUpAssignedDate;
    private Timestamp         initFollUpAssignedDate;
    private Timestamp         interviewAssignedDate;
    private Timestamp         initInterviewAssignedDate;
    private Timestamp         caseClosedDate;
    private Timestamp         caseReviewStatusDate;

    private String            localId;

    public String getCaseReviewStatus()
    {
        return caseReviewStatus;
    }

    public void setCaseReviewStatus(String caseReviewStatus)
    {
        this.caseReviewStatus = caseReviewStatus;
    }

    public boolean isCaseManagementDTPopulated()
    {
        return isCaseManagementDTPopulated;

    }

    public void setCaseManagementDTPopulated(boolean isCaseManagementDTPopulated)
    {
        this.isCaseManagementDTPopulated = isCaseManagementDTPopulated;
    }

    public Long getCaseManagementUid()
    {
        return caseManagementUid;
    }

    public void setCaseManagementUid(Long caseManagementUid)
    {
        this.caseManagementUid = caseManagementUid;

    }

    public Long getPublicHealthCaseUid()
    {
        return publicHealthCaseUid;
    }

    public void setPublicHealthCaseUid(Long publicHealthCaseUid)
    {
        this.publicHealthCaseUid = publicHealthCaseUid;
    }

    public String getStatus900()
    {
        return status900;
    }

    public void setStatus900(String status900)
    {
        this.status900 = status900;
    }

    public String getEharsId()
    {
        return eharsId;
    }

    public void setEharsId(String eharsId)
    {
        this.eharsId = eharsId;
    }

    public String getEpiLinkId()
    {
        return epiLinkId;
    }

    public void setEpiLinkId(String epiLinkId)
    {
        this.epiLinkId = epiLinkId;
    }

    public String getFieldFollUpOojOutcome()
    {
        return fieldFollUpOojOutcome;
    }

    public void setFieldFollUpOojOutcome(String fieldFollUpOojOutcome)
    {
        this.fieldFollUpOojOutcome = fieldFollUpOojOutcome;
    }

    public String getFieldRecordNumber()
    {
        return fieldRecordNumber;
    }

    public void setFieldRecordNumber(String pFieldRecordNumber)
    {
    	if (pFieldRecordNumber==null && caseManagementUid!= null && caseManagementUid > 1000L) {
    		logger.error("----------- Field Record Number being set to null in DT -------------");	
    	}
        this.fieldRecordNumber = pFieldRecordNumber;
    }

    public String getFldFollUpDispo()
    {
        return fldFollUpDispo;
    }

    public void setFldFollUpDispo(String fldFollUpDispo)
    {
        this.fldFollUpDispo = fldFollUpDispo;
    }

    public Timestamp getFldFollUpDispoDate()
    {
        return fldFollUpDispoDate;
    }

    public void setFldFollUpDispoDate(Timestamp fldFollUpDispoDate)
    {
        this.fldFollUpDispoDate = fldFollUpDispoDate;
    }

    public Timestamp getFldFollUpExamDate()
    {
        return fldFollUpExamDate;
    }

    public void setFldFollUpExamDate(Timestamp fldFollUpExamDate)
    {
        this.fldFollUpExamDate = fldFollUpExamDate;
    }

    public Timestamp getFldFollUpExpectedDate()
    {
        return fldFollUpExpectedDate;
    }

    public void setFldFollUpExpectedDate(Timestamp fldFollUpExpectedDate)
    {
        this.fldFollUpExpectedDate = fldFollUpExpectedDate;
    }

    public String getFldFollUpExpectedIn()
    {
        return fldFollUpExpectedIn;
    }

    public void setFldFollUpExpectedIn(String fldFollUpExpectedIn)
    {
        this.fldFollUpExpectedIn = fldFollUpExpectedIn;
    }

    public String getFldFollUpInternetOutcome()
    {
        return fldFollUpInternetOutcome;
    }

    public void setFldFollUpInternetOutcome(String fldFollUpInternetOutcome)
    {
        this.fldFollUpInternetOutcome = fldFollUpInternetOutcome;
    }

    public String getFldFollUpNotificationPlan()
    {
        return fldFollUpNotificationPlan;
    }

    public void setFldFollUpNotificationPlan(String fldFollUpNotificationPlan)
    {
        this.fldFollUpNotificationPlan = fldFollUpNotificationPlan;
    }

    public String getFldFollUpProvDiagnosis()
    {
        return fldFollUpProvDiagnosis;
    }

    public void setFldFollUpProvDiagnosis(String fldFollUpProvDiagnosis)
    {
        this.fldFollUpProvDiagnosis = fldFollUpProvDiagnosis;
    }

    public String getFldFollUpProvExmReason()
    {
        return fldFollUpProvExmReason;
    }

    public void setFldFollUpProvExmReason(String fldFollUpProvExmReason)
    {
        this.fldFollUpProvExmReason = fldFollUpProvExmReason;
    }

    public String getInitFollUp()
    {
        return initFollUp;
    }

    public void setInitFollUp(String initFollUp)
    {
        this.initFollUp = initFollUp;
    }

    public String getInitFollUpClinicCode()
    {
        return initFollUpClinicCode;
    }

    public void setInitFollUpClinicCode(String initFollUpClinicCode)
    {
        this.initFollUpClinicCode = initFollUpClinicCode;
    }

    public Timestamp getInitFollUpClosedDate()
    {
        return initFollUpClosedDate;
    }

    public void setInitFollUpClosedDate(Timestamp initFollUpClosedDate)
    {
        this.initFollUpClosedDate = initFollUpClosedDate;
    }

    public String getInitFollUpNotifiable()
    {
        return initFollUpNotifiable;
    }

    public void setInitFollUpNotifiable(String initFollUpNotifiable)
    {
        this.initFollUpNotifiable = initFollUpNotifiable;
    }

    public String getInternetFollUp()
    {
        return internetFollUp;
    }

    public void setInternetFollUp(String internetFollUp)
    {
        this.internetFollUp = internetFollUp;
    }

    public String getOojAgency()
    {
        return oojAgency;
    }

    public void setOojAgency(String oojAgency)
    {
        this.oojAgency = oojAgency;
    }

    public Timestamp getOojDueDate()
    {
        return oojDueDate;
    }

    public void setOojDueDate(Timestamp oojDueDate)
    {
        this.oojDueDate = oojDueDate;
    }

    public String getOojNumber()
    {
        return oojNumber;
    }

    public void setOojNumber(String oojNumber)
    {
        this.oojNumber = oojNumber;
    }

    public String getPatIntvStatusCd()
    {
        return patIntvStatusCd;
    }

    public void setPatIntvStatusCd(String patIntvStatusCd)
    {
        this.patIntvStatusCd = patIntvStatusCd;
    }

    public String getSubjComplexion()
    {
        return subjComplexion;
    }

    public void setSubjComplexion(String subjComplexion)
    {
        this.subjComplexion = subjComplexion;
    }

    public String getSubjHair()
    {
        return subjHair;
    }

    public void setSubjHair(String subjHair)
    {
        this.subjHair = subjHair;
    }

    public String getSubjHeight()
    {
        return subjHeight;
    }

    public void setSubjHeight(String subjHeight)
    {
        this.subjHeight = subjHeight;
    }

    public String getSubjOthIdntfyngInfo()
    {
        return subjOthIdntfyngInfo;
    }

    public void setSubjOthIdntfyngInfo(String subjOthIdntfyngInfo)
    {
        this.subjOthIdntfyngInfo = subjOthIdntfyngInfo;
    }

    public String getSubjSizeBuild()
    {
        return subjSizeBuild;
    }

    public void setSubjSizeBuild(String subjSizeBuild)
    {
        this.subjSizeBuild = subjSizeBuild;
    }

    public Timestamp getSurvClosedDate()
    {
        return survClosedDate;
    }

    public void setSurvClosedDate(Timestamp survClosedDate)
    {
        this.survClosedDate = survClosedDate;
    }

    public String getSurvPatientFollUp()
    {
        return survPatientFollUp;
    }

    public void setSurvPatientFollUp(String survPatientFollUp)
    {
        this.survPatientFollUp = survPatientFollUp;
    }

    public String getSurvProvDiagnosis()
    {
        return survProvDiagnosis;
    }

    public void setSurvProvDiagnosis(String survProvDiagnosis)
    {
        this.survProvDiagnosis = survProvDiagnosis;
    }

    public String getSurvProvExmReason()
    {
        return survProvExmReason;
    }

    public void setSurvProvExmReason(String survProvExmReason)
    {
        this.survProvExmReason = survProvExmReason;
    }

    public String getSurvProviderContact()
    {
        return survProviderContact;
    }

    public void setSurvProviderContact(String survProviderContact)
    {
        this.survProviderContact = survProviderContact;
    }

    public String getActRefTypeCd()
    {
        return actRefTypeCd;
    }

    public void setActRefTypeCd(String actRefTypeCd)
    {
        this.actRefTypeCd = actRefTypeCd;
    }

    public String getInitiatingAgncy()
    {
        return initiatingAgncy;
    }

    public void setInitiatingAgncy(String initiatingAgncy)
    {
        this.initiatingAgncy = initiatingAgncy;
    }

    public Timestamp getOojInitgAgncyOutcDueDate()
    {
        return oojInitgAgncyOutcDueDate;
    }

    public void setOojInitgAgncyOutcDueDate(Timestamp oojInitgAgncyOutcDueDate)
    {
        this.oojInitgAgncyOutcDueDate = oojInitgAgncyOutcDueDate;
    }

    public Timestamp getOojInitgAgncyOutcSntDate()
    {
        return oojInitgAgncyOutcSntDate;
    }

    public void setOojInitgAgncyOutcSntDate(Timestamp oojInitgAgncyOutcSntDate)
    {
        this.oojInitgAgncyOutcSntDate = oojInitgAgncyOutcSntDate;
    }

    public Timestamp getOojInitgAgncyRecdDate()
    {
        return oojInitgAgncyRecdDate;
    }

    public void setOojInitgAgncyRecdDate(Timestamp oojInitgAgncyRecdDate)
    {
        this.oojInitgAgncyRecdDate = oojInitgAgncyRecdDate;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        System.out.println(sb);
        System.out.println(sb);
        try
        {
            sb.append(this.getClass().getName() + "\r\n");
            Field[] f = CaseManagementDT.class.getDeclaredFields();
            for (int i = 0; i < f.length; i++)
            {
                if (f[i] != null)
                    sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
                System.out.println(sb);
            }
        }
        catch (Exception ignore)
        {
        }
        return sb.toString();
    }

    @Override
    public Long getLastChgUserId()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastChgUserId(Long aLastChgUserId)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getJurisdictionCd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setJurisdictionCd(String aJurisdictionCd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getProgAreaCd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProgAreaCd(String aProgAreaCd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Timestamp getLastChgTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLocalId()
    {
        // TODO Auto-generated method stub
        return localId;
    }

    @Override
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;

    }

    @Override
    public Long getAddUserId()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAddUserId(Long aAddUserId)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLastChgReasonCd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getRecordStatusCd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Timestamp getRecordStatusTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getStatusCd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStatusCd(String aStatusCd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Timestamp getStatusTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStatusTime(Timestamp aStatusTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getSuperclass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getUid()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAddTime(Timestamp aAddTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Timestamp getAddTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isItNew()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setItNew(boolean itNew)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isItDirty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setItDirty(boolean itDirty)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isItDelete()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setItDelete(boolean itDelete)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Long getProgramJurisdictionOid()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getSharedInd()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSharedInd(String aSharedInd)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getVersionCtrlNbr()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @return the survAssignedDate
     */
    public Timestamp getSurvAssignedDate()
    {
        return survAssignedDate;
    }

    /**
     * @param survAssignedDate
     *            the survAssignedDate to set
     */
    public void setSurvAssignedDate(Timestamp survAssignedDate)
    {
        this.survAssignedDate = survAssignedDate;
    }

    /**
     * @return the follUpAssignedDate
     */
    public Timestamp getFollUpAssignedDate()
    {
        return follUpAssignedDate;
    }

    /**
     * @param follUpAssignedDate
     *            the follUpAssignedDate to set
     */
    public void setFollUpAssignedDate(Timestamp follUpAssignedDate)
    {
        this.follUpAssignedDate = follUpAssignedDate;
    }

    /**
     * @return the initFollUpAssignedDate
     */
    public Timestamp getInitFollUpAssignedDate()
    {
        return initFollUpAssignedDate;
    }

    /**
     * @param initFollUpAssignedDate
     *            the initFollUpAssignedDate to set
     */
    public void setInitFollUpAssignedDate(Timestamp initFollUpAssignedDate)
    {
        this.initFollUpAssignedDate = initFollUpAssignedDate;
    }

    /**
     * @return the interviewAssignedDate
     */
    public Timestamp getInterviewAssignedDate()
    {
        return interviewAssignedDate;
    }

    /**
     * @param interviewAssignedDate
     *            the interviewAssignedDate to set
     */
    public void setInterviewAssignedDate(Timestamp interviewAssignedDate)
    {
        this.interviewAssignedDate = interviewAssignedDate;
    }

    /**
     * @return the initInterviewAssignedDate
     */
    public Timestamp getInitInterviewAssignedDate()
    {
        return initInterviewAssignedDate;
    }

    /**
     * @param initInterviewAssignedDate
     *            the initInterviewAssignedDate to set
     */
    public void setInitInterviewAssignedDate(Timestamp initInterviewAssignedDate)
    {
        this.initInterviewAssignedDate = initInterviewAssignedDate;
    }

    /**
     * @return the caseClosedDate
     */
    public Timestamp getCaseClosedDate()
    {
        return caseClosedDate;
    }

    /**
     * @param caseClosedDate
     *            the caseClosedDate to set
     */
    public void setCaseClosedDate(Timestamp caseClosedDate)
    {
        this.caseClosedDate = caseClosedDate;
    }

    public Timestamp getCaseReviewStatusDate()
    {
        return caseReviewStatusDate;
    }

    public void setCaseReviewStatusDate(Timestamp caseReviewStatusDate)
    {
        this.caseReviewStatusDate = caseReviewStatusDate;
    }
}
