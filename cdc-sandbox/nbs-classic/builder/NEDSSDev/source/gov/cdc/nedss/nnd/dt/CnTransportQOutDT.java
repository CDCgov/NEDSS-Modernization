package gov.cdc.nedss.nnd.dt;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
/**
* Name:		CnTransportQOutDT.java
* Description:	DT for New PAM NND Transport Q for Intermediary Message.
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/
public class CnTransportQOutDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;

	private Long cnTransportQOutUID;
	private Long notificationUID;
	private String addReasonCd;
	private Timestamp addTime;
	private Long addUserId;
	private String lastChgReasonCd;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String messagePayload;
	private String reportStatus;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
    private String notificationLocalId;
    private String publicHealthCaseLocalId;
    private Integer versionCtrlNbr;

	/**
	 * @return the cnTransportQOutUID
	 */
	public Long getCnTransportQOutUID() {
		return cnTransportQOutUID;
	}
	/**
	 * @param cnTransportQOutUID the cnTransportQOutUID to set
	 */
	public void setCnTransportQOutUID(Long cnTransportQOutUID) {
		this.cnTransportQOutUID = cnTransportQOutUID;
        setItDirty(true);
	}
	/**
	 * @return the notificationUID
	 */
	public Long getNotificationUID() {
		return notificationUID;
	}
	/**
	 * @param notificationUID the notificationUID to set
	 */
	public void setNotificationUID(Long notificationUID) {
		this.notificationUID = notificationUID;
        setItDirty(true);
	}
	/**
	 * @return the addReasonCd
	 */
	public String getAddReasonCd() {
		return addReasonCd;
	}
	/**
	 * @param addReasonCd the addReasonCd to set
	 */
	public void setAddReasonCd(String addReasonCd) {
		this.addReasonCd = addReasonCd;
        setItDirty(true);
	}
	/**
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
        setItDirty(true);
	}
	/**
	 * @return the addUserId
	 */
	public Long getAddUserId() {
		return addUserId;
	}
	/**
	 * @param addUserId the addUserId to set
	 */
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
        setItDirty(true);
	}
	/**
	 * @return the lastChgReasonCd
	 */
	public String getLastChgReasonCd() {
		return lastChgReasonCd;
	}
	/**
	 * @param lastChgReasonCd the lastChgReasonCd to set
	 */
	public void setLastChgReasonCd(String lastChgReasonCd) {
		this.lastChgReasonCd = lastChgReasonCd;
        setItDirty(true);
	}
	/**
	 * @return the lastChgTime
	 */
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	/**
	 * @param lastChgTime the lastChgTime to set
	 */
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
        setItDirty(true);
	}
	/**
	 * @return the lastChguserId
	 */
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	/**
	 * @param lastChguserId the lastChguserId to set
	 */
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
        setItDirty(true);
	}
	/**
	 * @return the messagePayload
	 */
	public String getMessagePayload() {
		return messagePayload;
	}
	/**
	 * @param messagePayload the messagePayload to set
	 */
	public void setMessagePayload(String messagePayload) {
		this.messagePayload = messagePayload;
        setItDirty(true);
	}
	/**
	 * @return the reportStatus
	 */
	public String getReportStatus() {
		return reportStatus;
	}
	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
        setItDirty(true);
	}
	/**
	 * @return the recordStatusCd
	 */
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	/**
	 * @param recordStatusCd the recordStatusCd to set
	 */
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
        setItDirty(true);
	}
	/**
	 * @return the recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}
	/**
	 * @param recordStatusTime the recordStatusTime to set
	 */
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
        setItDirty(true);
	}

	public String getNotificationLocalId() {
		return notificationLocalId;
	}
	public void setNotificationLocalId(String notificationLocalId) {
		this.notificationLocalId = notificationLocalId;
        setItDirty(true);
	}
	public String getPublicHealthCaseLocalId() {
		return publicHealthCaseLocalId;
	}
	public void setPublicHealthCaseLocalId(String publicHealthCaseLocalId) {
		this.publicHealthCaseLocalId = publicHealthCaseLocalId;
        setItDirty(true);
	}
    /**
     * gets the VersionCtrlNbr
     * @return : Integer value
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }
    /**
     * sets the VersionCtrlNbr
     * @param aVersionCtrlNbr : Integer value
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }
	//RootDTInterface methods that are not implemented in new code
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
	    voClass =  (( CnTransportQOutDT) objectname1).getClass();
	    NedssUtils compareObjs = new NedssUtils();
	    return (compareObjs.equals(objectname1,objectname2,voClass));
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
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	public String getSuperclass() {
	    return NEDSSConstants.CLASSTYPE_ACT;
	}
	public Long getUid() {
		return cnTransportQOutUID;
	}
   /**
    * sets itDirty
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
    * sets itNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }
   /**
    * gets itNew
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
    * sets the setItDelete
    * @param itDelete : boolean value
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CnTransportQOutDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}


}
