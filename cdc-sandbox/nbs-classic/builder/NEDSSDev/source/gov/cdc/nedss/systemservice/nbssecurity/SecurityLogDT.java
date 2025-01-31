package gov.cdc.nedss.systemservice.nbssecurity;

import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.util.*;

import java.lang.reflect.Field;
import java.sql.*;

/**
 *  DT for security log.
 *  @author Ed Jenkins
 */
public class SecurityLogDT extends AbstractVO
{

    private Long securityLogUid = null;
    private Timestamp eventTime = null;
    private String sessionId = null;
    private String userId = null;
    private String eventTypeCd = null;
    private String remoteAddress = null;
    private String remoteHost = null;
    private Long nedssEntryId = null;
    public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = SecurityLogDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
    public Long getNedssEntryId() {
		return nedssEntryId;
	}

	public void setNedssEntryId(Long nedssEntryId) {
		this.nedssEntryId = nedssEntryId;
	}

	public String getFirstNm() {
		return firstNm;
	}

	public void setFirstNm(String firstNm) {
		this.firstNm = firstNm;
	}

	public String getLastNm() {
		return lastNm;
	}

	public void setLastNm(String lastNm) {
		this.lastNm = lastNm;
	}

	private String firstNm = null;
    private String lastNm = null;

    /**
     *  Default constructor.
     *  @roseuid 3D63D0A8003F
     */
    public SecurityLogDT()
    {
    }

    /**
     *  @roseuid 3D63D0A800A3
     *  @param itNew
     */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
     *  @roseuid 3D63D0A800B7
     *  @return boolean
     */
    public boolean isItNew()
    {
        return this.itNew;
    }

    /**
     *  @roseuid 3D63D0A8007B
     *  @param itDirty
     */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
     *  @roseuid 3D63D0A80099
     *  @return boolean
     */
    public boolean isItDirty()
    {
        return this.itDirty;
    }

    /**
     *  @roseuid 3D63D0A800C1
     *  @param itDelete
     */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
     *  @roseuid 3D63D0A800DF
     *  @return boolean
     */
    public boolean isItDelete()
    {
        return this.itDelete;
    }

    /**
     *  @roseuid 3D63D0A80053
     *  @param objectname1
     *  @param objectname2
     *  @param voClass
     *  @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((SecurityLogDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     *  Sets the value of the securityLogUid property.
     *  @param aSecurityLogUid the new value of the securityLogUid property
     */
    public void setSecurityLogUid(Long aSecurityLogUid)
    {
        securityLogUid = aSecurityLogUid;
        setItDirty(true);
    }

    /**
     *  Access method for the securityLogUid property.
     *  @return the current value of the securityLogUid property
     */
    public Long getSecurityLogUid()
    {
        return securityLogUid;
    }

    /**
     *  Sets the value of the eventTime property.
     *  @roseuid 3D63D6030271
     *  @param aEventTime the new value of the eventTime property
     */
    public void setEventTime(java.sql.Timestamp aEventTime)
    {
        eventTime = aEventTime;
        setItDirty(true);
    }

    /**
     *  Access method for the eventTime property.
     *  @return the current value of the eventTime property
     */
    public Timestamp getEventTime()
    {
        return eventTime;
    }

    /**
     *  Sets the value of the sessionId property.
     *  @param aSessionId the new value of the sessionId property
     */
    public void setSessionId(String aSessionId)
    {
        sessionId = aSessionId;
        setItDirty(true);
    }

    /**
     *  Access method for the sessionId property.
     *  @return the current value of the sessionId property
     */
    public String getSessionId()
    {
        return sessionId;
    }

    /**
     *  Sets the value of the userId property.
     *  @param aUserId the new value of the userId property
     */
    public void setUserId(String aUserId)
    {
        userId = aUserId;
        setItDirty(true);
    }

    /**
     *  Access method for the userId property.
     *  @return the current value of the userId property
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     *  Sets the value of the eventTypeCd property.
     *  @param aEventTypeCd the new value of the eventTypeCd property
     */
    public void setEventTypeCd(String aEventTypeCd)
    {
        eventTypeCd = aEventTypeCd;
        setItDirty(true);
    }

    /**
     *  Access method for the eventTypeCd property.
     *  @return the current value of the eventTypeCd property
     */
    public String getEventTypeCd()
    {
        return eventTypeCd;
    }

    /**
     *  Sets the value of the remoteAddress property.
     *  @param aRemoteAddress the new value of the remoteAddress property.
     */
    public void setRemoteAddress(String aRemoteAddress)
    {
        remoteAddress = aRemoteAddress;
        setItDirty(true);
    }

    /**
     *  Access method for the remoteAddress property.
     *  @return the current value of the remoteAddress property.
     */
    public String getRemoteAddress()
    {
        return remoteAddress;
    }

    /**
     *  Sets the value of the remoteHost property.
     *  @param aRemoteHost the new value of the remoteHost property.
     */
    public void setRemoteHost(String aRemoteHost)
    {
        remoteHost = aRemoteHost;
        setItDirty(true);
    }

    /**
     *  Access method for the remoteHost property.
     *  @return the current value of the remoteHost property.
     */
    public String getRemoteHost()
    {
        return remoteHost;
    }

}
