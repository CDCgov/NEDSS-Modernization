package gov.cdc.nedss.proxy.ejb.queue.vo;

import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;

public class SupervisorReviewVO extends AbstractVO
{
      
    private static final long serialVersionUID = -3270479616588778370L;
    private AuthUserDT         supervisor   = new AuthUserDT();
    private AuthUserDT         investigator = new AuthUserDT();
    private PersonDT           patient      = new PersonDT();
    private PublicHealthCaseDT phc          = new PublicHealthCaseDT();
    private CaseManagementDT   cm           = new CaseManagementDT();
 
    private String             condition; 
    private String             comments;
    
    // Case Closure Supervisor First Nm, Last Nm
    private String             ccrFirstNm;
    private String             ccrLastNm;
    
    
    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setCaseReviewStatus(String st)
    {
        this.cm.setCaseReviewStatus(st);
    }
    
    public String getCaseReviewStatus()
    {
        return this.cm.getCaseReviewStatus();
    }
    
    public Timestamp getCaseClosedDate()
    {
        return this.cm.getCaseClosedDate();
    }

    public void setCaseClosedDate(Timestamp caseClosedDate)
    {
        this.cm.setCaseClosedDate(caseClosedDate);
    }

    public void setFldFollUpDispo(String fld)
    {
        this.cm.setFldFollUpDispo(fld);
    }

    public String getFldFollUpDispo()
    {
        return this.cm.getFldFollUpDispo();
    }

    public void setInitFollUpClosedDate(Timestamp initFollUpClosedDate)
    {
        this.cm.setInitFollUpClosedDate(initFollUpClosedDate);
    }

    public Timestamp getInitFollUpClosedDate()
    {
        return this.cm.getInitFollUpClosedDate();
    }

    public void setFldFollUpDispoDate(Timestamp fldFollUpDispoDate)
    {
        this.cm.setFldFollUpDispoDate(fldFollUpDispoDate);
    }

    public Timestamp getFldFollUpDispoDate()
    {
        return this.cm.getFldFollUpDispoDate();
    }

    public AuthUserDT getSupervisor()
    {
        return supervisor;
    }

    public void setSupervisor(AuthUserDT supervisor)
    {
        this.supervisor = supervisor;
    }

    public AuthUserDT getInvestigator()
    {
        return investigator;
    }

    public void setInvestigator(AuthUserDT investigator)
    {
        this.investigator = investigator;
    }

    public PersonDT getPatient()
    {
        return patient;
    }

    public void setPatient(PersonDT patient)
    {
        this.patient = patient;
    }

    public String getSupervisorFullName()
    {
        if (this.cm.getCaseClosedDate() != null && ccrLastNm != null)
            return createName(ccrLastNm, ccrFirstNm,  null);
        else
            return createName(supervisor.getUserLastNm(),supervisor.getUserFirstNm(),  null);
    }

    public String getInvestigatorFullName()
    {
        return createName(investigator.getUserLastNm(),investigator.getUserFirstNm(),  null);
    }

    public String getPatientFullName()
    {
        return createName(patient.getLastNm(), patient.getFirstNm(),  patient.getMiddleNm());
    }
 
    public Timestamp getSubmitDate()
    {
        return this.cm.getCaseReviewStatusDate();
    }
    
    public void setSubmitDate(Timestamp aAddTime)
    {
        this.cm.setCaseReviewStatusDate(aAddTime);
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getReferralBasisCd()
    {
        return this.phc.getReferralBasisCd();
    }

    public String getActivityType()
    {
    	if(this.cm != null && this.cm.getFldFollUpDispo() != null 
    			&& this.cm.getFldFollUpDispo().equalsIgnoreCase(NEDSSConstants.FLD_DISPO_OOJ))
    			return(NEDSSConstants.OOJ_XFER);
    	else if( this.cm != null && this.cm.getCaseClosedDate() != null )
            return NEDSSConstants.CASE_CLOSURE;
        else
            return NEDSSConstants.FR_CLOSURE;
    }
 

    public void setSupervisorLastNm(String lastNm)
    {
        this.supervisor.setUserLastNm(lastNm);
    }

    public void setSuperviorFirstNm(String firstNm)
    {
        this.supervisor.setUserFirstNm(firstNm);
    }

    public void setInvestigatorFirstNm(String firstNm)
    {
        this.investigator.setUserFirstNm(firstNm);
    }

    public void setInvestigatorLastNm(String lastNm)
    {
        this.investigator.setUserLastNm(lastNm);
    }

    public void setPatientFirstNm(String firstNm)
    {
        this.patient.setFirstNm(firstNm);
    }

    public void setPatientLastNm(String lastNm)
    {
        this.patient.setLastNm(lastNm);
    }

    public void setPublicHealthCaseUid(Long aPublicHealthCaseUid)
    {
        this.phc.setPublicHealthCaseUid(aPublicHealthCaseUid);
    }

    public void setPublicHealthCaseUid(String aPublicHealthCaseUid)
    {
        this.phc.setPublicHealthCaseUid(Long.parseLong(aPublicHealthCaseUid));
    }

    public Long getPublicHealthCaseUid()
    {
        return this.phc.getPublicHealthCaseUid();
    }

    public void setReferralBasisCd(String referralBasisCd)
    {
        this.phc.setReferralBasisCd(referralBasisCd);
    }

    public Long getMPRUid()
    {
        return this.patient.getPersonParentUid();
    }

    public void setMPRUid(Long uid)
    {
        this.patient.setPersonParentUid(uid);
    }

    public String getCcrFirstNm()
    {
        return ccrFirstNm;
    }

    public void setCcrFirstNm(String ccrFirstNm)
    {
        this.ccrFirstNm = ccrFirstNm;
    }

     
    public String getCcrLastNm()
    {
        return ccrLastNm;
    }

    public void setCcrLastNm(String ccrLastNm)
    {
        this.ccrLastNm = ccrLastNm;
    }

    private String createName(String ln, String fn, String mn)
    {
    	String lnfn = StringUtils.combine(new String[]{ln,fn}, ",");
        return mn !=null ? StringUtils.combine(new String[]{lnfn, mn }, " "): lnfn;
    }

    @Override
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = (itDirty);
        
    }

    @Override
    public boolean isItDirty()
    {
        return itDirty;
    }

    @Override
    public void setItNew(boolean itNew)
    {
        this.itNew  = itNew;
    }

    @Override
    public boolean isItNew()
    {
        return itNew;
    }

    @Override
    public void setItDelete(boolean itDelete)
    {
        this.itDelete  = itDelete;
    }

    @Override
    public boolean isItDelete()
    {
        return itDelete;
    }

    @Override
    public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
    { 
        return false;
    } 
}
