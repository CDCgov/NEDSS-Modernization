package gov.cdc.nedss.proxy.ejb.supervisor;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;

import java.util.Date;

public class SupervisorReviewVO
{
    private AuthUserDT supervisor   = new AuthUserDT();
    private AuthUserDT investigator = new AuthUserDT();
    private PersonDT   patient      = new PersonDT();
    private PublicHealthCaseDT phc  = new PublicHealthCaseDT();

    private Date       addTime;
    private String     condition; 
    private String     activityType;

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
        return createName( supervisor.getUserFirstNm(), supervisor.getUserLastNm(), null);
    }

    public String getInvestigatorFullName()
    {
        return createName( investigator.getUserFirstNm(), investigator.getUserLastNm(), null);
    }

    public String getPatientFullName()
    {
        return createName( patient.getFirstNm(), patient.getLastNm(), patient.getMiddleNm());
    }

    public Date getAddTime()
    {
        return addTime;
    }

    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
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
        return activityType;
    }

    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
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
        this.phc.setPublicHealthCaseUid(Long.parseLong(aPublicHealthCaseUid) );
    }
    
    public Long getPublicHealthCaseUid()
    {
        return this.phc.getPublicHealthCaseUid();
    }
    
    public void setReferralBasisCd(String referralBasisCd)
    {
        this.phc.setReferralBasisCd(referralBasisCd);
    }

    private String createName(String fn, String ln, String mn)
    {
        StringBuilder sb = new StringBuilder();
        boolean apend = false;
        if (ln != null)
        {
            sb.append(ln);
            apend = true;
        }
        if( fn != null )
        {
            if (apend)
            {
                sb.append(", ");
            } 
            sb.append(fn);
            apend = true;
        }
        if( mn != null )
        {
            if (apend)
            {
                sb.append(", ");
            } 
            sb.append(mn);
            apend = true;
        }
        return sb.toString();
    }
}
