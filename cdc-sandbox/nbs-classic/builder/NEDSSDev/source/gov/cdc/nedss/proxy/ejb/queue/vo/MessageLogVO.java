package gov.cdc.nedss.proxy.ejb.queue.vo;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.MessageConstants;

import java.sql.Timestamp;

public class MessageLogVO extends AbstractVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PersonDT     patientDt;
    private AuthUserDT   userDt;

    private MessageLogDT messageLogDT;

    public MessageLogVO()
    {
        patientDt = new PersonDT();
        userDt = new AuthUserDT();
        messageLogDT = new MessageLogDT();
    }

    public void setFirstNm(String firstNm)
    {
        patientDt.setFirstNm(firstNm);
    }

    public void setLastNm(String lastNm)
    {
        patientDt.setLastNm(lastNm);
    }

    public String getFirstNm()
    {
        return patientDt.getFirstNm();
    }

    public String getLastNm()
    {
        return patientDt.getLastNm();
    }

    public String getFullName()
    {
        return patientDt.getLastNm() + ", " + patientDt.getFirstNm();
    }

    public void setUserFirstNm(String firstNm)
    {
        userDt.setUserFirstNm(firstNm);
    }

    public String getUserFirstNm()
    {
        return userDt.getUserFirstNm();
    }

    public void setUserLastNm(String lastNm)
    {
        userDt.setUserLastNm(lastNm);
    }

    public String getUserLastNm()
    {
        return userDt.getUserLastNm();
    }

    public String getUserFullName()
    {
        return userDt.getUserLastNm() + ", " + userDt.getUserFirstNm();
    }

    public String getPublicHealthCaseUid()
    {
        return (messageLogDT.getEventUid() != null ? messageLogDT.getEventUid().toString() : "");
    }

    public MessageLogDT getMessageLogDT()
    {
        return messageLogDT;
    }

    public void setMessageLogDT(MessageLogDT messageLogDT)
    {
        this.messageLogDT = messageLogDT;
    }

    // Convenient Methods for setting values from SQL.

    public void setMessageLogUid(Long mid)
    {
        this.messageLogDT.setMessageLogUid(mid);
    }

    public void setConditionCd(String cd)
    {
        this.messageLogDT.setConditionCd(cd);
    }

    public void setEventUid(Long eid)
    {
        this.messageLogDT.setEventUid(eid);
    }

    public void setMessageTxt(String messageTxt)
    {
        this.messageLogDT.setMessageTxt(messageTxt);
    }
    
    public void setMessageStatusCd(String messageStatusCd)
    {
        this.messageLogDT.setMessageStatusCd(messageStatusCd);
    }
    
    public void setAddTime(Timestamp addTime)
    {
        this.messageLogDT.setAddTime(addTime);
    }
    
    public Timestamp getAddTime()
    {
        return this.messageLogDT.getAddTime();
    }
    
    public String getMessageTxt()
    {
        return this.messageLogDT.getMessageTxt();
    }
    
    public String getConditionCd()
    {
        return this.messageLogDT.getConditionCd();
    }
    
    public String getMessageStatus()
    {
        if( MessageConstants.N.equalsIgnoreCase(messageLogDT.getMessageStatusCd()))
            return MessageConstants.New;
        else if( MessageConstants.R.equalsIgnoreCase(messageLogDT.getMessageStatusCd()) )
            return MessageConstants.Read;
        else 
            return "";
            
    }

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
}
