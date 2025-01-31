package gov.cdc.nedss.proxy.ejb.queue.dt;
 

import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class MessageLogDT  extends AbstractVO 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long messageLogUid;
    private String  messageTxt;
    private String  conditionCd;
    private Long personUid;
    private Long assignedToUid;
    private Long eventUid;
    private String  eventTypeCd;
    private String  messageStatusCd;
    private String  recordStatusCd;
    private Timestamp recordStatusTime;
    private Timestamp addTime;
    private Long userId;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    
    
    public Long getMessageLogUid()
    {
        return messageLogUid;
    }
    public void setMessageLogUid(Long messageLogUid)
    {
        this.messageLogUid = messageLogUid; 
    }
    public String getMessageTxt()
    {
        return messageTxt;
    }
    public void setMessageTxt(String messageTxt)
    {
        this.messageTxt = messageTxt;
    }
    public String getConditionCd()
    {
        return conditionCd;
    }
    public void setConditionCd(String conditionCd)
    {
        this.conditionCd = conditionCd;
    }
    public Long getPersonUid()
    {
        return personUid;
    }
    public void setPersonUid(Long personUid)
    {
        this.personUid = personUid;
    }
    public Long getAssignedToUid()
    {
        return assignedToUid;
    }
    public void setAssignedToUid(Long assignedToUid)
    {
        this.assignedToUid = assignedToUid;
    }
    public Long getEventUid()
    {
        return eventUid;
    }
    public void setEventUid(Long eventUid)
    {
        this.eventUid = eventUid;
    }
    public String getEventTypeCd()
    {
        return eventTypeCd;
    }
    public void setEventTypeCd(String eventTypeCd)
    {
        this.eventTypeCd = eventTypeCd;
    }
    public String getMessageStatusCd()
    {
        return messageStatusCd;
    }
    public void setMessageStatusCd(String messageStatusCd)
    {
        this.messageStatusCd = messageStatusCd;
    }
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }
    public void setRecordStatusCd(String recordStatusCd)
    {
        this.recordStatusCd = recordStatusCd;
    }
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }
    public void setRecordStatusTime(Timestamp recordStatusTime)
    {
        this.recordStatusTime = recordStatusTime;
    }
    public Timestamp getAddTime()
    {
        return addTime;
    }
    public void setAddTime(Timestamp addTime)
    {
        this.addTime = addTime;
    }
    public Long getUserId()
    {
        return userId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }
    public void setLastChgTime(Timestamp lastChgTime)
    {
        this.lastChgTime = lastChgTime;
    }
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }
    public void setLastChgUserId(Long lastChgUserId)
    {
        this.lastChgUserId = lastChgUserId;
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
