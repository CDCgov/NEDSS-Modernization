

package gov.cdc.nedss.act.interview.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;

public class InterviewDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;


    private Long interviewUid;

    private String intervieweeRoleCd;

    private Timestamp interviewDate;

    private String interviewTypeCd;
    
    private String interviewStatusCd;
    
    private String interviewLocCd;

    private Timestamp addTime;

    private Long addUserId;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String localId;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private Integer versionCtrlNbr;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    private String addUserName;

    private String lastChgUserName;

    private boolean associated;
    
	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getLastChgUserName() {
		return lastChgUserName;
	}

	public void setLastChgUserName(String lastChgUserName) {
		this.lastChgUserName = lastChgUserName;
	}

    /**
	 * @return the interviewUid
	 */
	public Long getInterviewUid() {
		return interviewUid;
	}

	/**
	 * @param interviewUid the interviewUid to set
	 */
	public void setInterviewUid(Long interviewUid) {
		this.interviewUid = interviewUid;
	}
	/**
	 * @return the interviewStatusCd
	 */
	public String getInterviewStatusCd() {
		return interviewStatusCd;
	}

	/**
	 * @param interviewStatusCd the interviewStatusCd to set
	 */
	public void setInterviewStatusCd(String interviewStatusCd) {
		this.interviewStatusCd = interviewStatusCd;
	}
	/**
	 * @return the intervieweeRoleCd
	 */
	public String getIntervieweeRoleCd() {
		return intervieweeRoleCd;
	}

	/**
	 * @param intervieweeRoleCd the intervieweeRoleCd to set
	 */
	public void setIntervieweeRoleCd(String intervieweeRoleCd) {
		this.intervieweeRoleCd = intervieweeRoleCd;
	}

	/**
	 * @return the interviewDate
	 */
	public Timestamp getInterviewDate() {
		return interviewDate;
	}

	/**
	 * @param interviewDate the interviewDate to set
	 */
	public void setInterviewDate(Timestamp interviewDate) {
		this.interviewDate = interviewDate;
	}

	/**
	 * @return the interviewTypeCd
	 */
	public String getInterviewTypeCd() {
		return interviewTypeCd;
	}

	/**
	 * @param interviewTypeCd the interviewTypeCd to set
	 */
	public void setInterviewTypeCd(String interviewTypeCd) {
		this.interviewTypeCd = interviewTypeCd;
	}
    /**
     * gets AddTime
     * @return : Timestamp value
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * sets AddTime
     * @param aAddTime : Timestamp value
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets AddTime(convenient method for struts)
     * @param strTime : String value
     */
    public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets AddUserId
    * @return : Long value
    */
   public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * sets AddUserId
     * @param aAddUserId : Long value
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets LastChgTime
     * @return : Timestamp value
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * sets LastChgTime
     * @param aLastChgTime : Timestamp value
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets LastChgTime(convenient method for struts)
     * @param strTime : String value
     */
    public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets LastChgUserId
    * @return : Long value
    */
   public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * sets LastChgUserId
     * @param aLastChgUserId : Long value
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * gets LocalId
     * @return : String value
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * sets LocalId
     * @param aLocalId : String value
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }
    /**
     * gets RecordStatusCd
     * @return : String value
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * sets RecordStatusCd
     * @param aRecordStatusCd : String value
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * gets RecordStatusTime
     * @return : Timestamp value
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * sets RecordStatusTime
     * @param aRecordStatusTime : Timestamp value
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

  /**
   * sets RecordStatusTime( convenient method for struts)
   * @param strTime : String value
   */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * gets VersionCtrlNbr
     * @return : Integer value
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * sets VersionCtrlNbr
     * @param aVersionCtrlNbr : Integer value
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }


    /**
     * compare to find if two objects are equal
     * @param objectname1 : first object
     * @param objectname2 : second object
     * @param voClass : ((InterventionDT) objectname1).getClass
     * @return boolean value
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
      voClass =  (( InterviewDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
    }

   /**
    *set itDirty
    * @param itDirty : boolean value
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }

   /**
    * gets itDirty
    * @return : boolean value
    */
   public boolean isItDirty() {
     return itDirty;
   }

   /**
    * sets ItNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }

   /**
    * gets isItNew
    * @return : boolean value
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * gets itDelete
    * @return : boolean value
    */
   public boolean isItDelete() {
     return itDelete;
   }

   /**
    * sets itDelete
    * @param itDelete : boolean value
    */
  public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

   /**
    * gets Super class
    * @return : String value
    */

   public String getSuperclass()
  {
    return NEDSSConstants.CLASSTYPE_ACT;
  }

  /**
   * gets Intervention UID
   * @return : Long value
   */
  public Long getUid()
  {
    return interviewUid;
  }


public String getLastChgReasonCd() {
	return null;
}


public void setLastChgReasonCd(String aLastChgReasonCd) {
	
}


public String getStatusCd() {
	return null;
}


public void setStatusCd(String aStatusCd) {
	
}


public Timestamp getStatusTime() {
	return null;
}


public void setStatusTime(Timestamp aStatusTime) {
	
}

/**
 * @return the interviewLocCd
 */
public String getInterviewLocCd() {
	return interviewLocCd;
}

/**
 * @param interviewLocCd the interviewLocCd to set
 */
public void setInterviewLocCd(String interviewLocCd) {
	this.interviewLocCd = interviewLocCd;
}


public String getJurisdictionCd() {
	// TODO Auto-generated method stub
	return null;
}


public void setJurisdictionCd(String aJurisdictionCd) {
	// TODO Auto-generated method stub
	
}


public String getProgAreaCd() {
	// TODO Auto-generated method stub
	return null;
}

public void setProgAreaCd(String aProgAreaCd) {
	// TODO Auto-generated method stub
	
}

public Long getProgramJurisdictionOid() {
	// TODO Auto-generated method stub
	return null;
}

public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	// TODO Auto-generated method stub
	
}


public String getSharedInd() {
	// TODO Auto-generated method stub
	return null;
}

public void setSharedInd(String aSharedInd) {
	// TODO Auto-generated method stub
	
}

public boolean isAssociated() {
	return associated;
}

public void setAssociated(boolean associated) {
	this.associated = associated;
}

}

