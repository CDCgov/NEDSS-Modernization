package gov.cdc.nedss.elr.dt;



import java.lang.reflect.Field;
import java.sql.Timestamp;
/**
 * 
 * @author Pradeep K Sharma: Data Transfer Utility class
 *
 */
public class NbsInterfaceDT{
	private Long nbsInterfaceUid;
	private String impExpIndCd;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String sendingSystemNm;
	private Timestamp addTime;
	private String receivingSystemNm;
	private Long notificationUid;
	private Long nbsDocumentUid;
	private String xmlPayLoadContent;
	private String systemNm;
	private String docTypeCd;
	


	private static final long serialVersionUID = 1L;

	public String getDocTypeCd() {
		return docTypeCd;
	}


	public void setDocTypeCd(String docTypeCd) {
		this.docTypeCd = docTypeCd;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}


	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}




	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub
		
	}

	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}


	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	public Long getNbsInterfaceUid() {
		return nbsInterfaceUid;
	}

	public void setNbsInterfaceUid(Long nbsInterfaceUid) {
		this.nbsInterfaceUid = nbsInterfaceUid;
	}

	public String getImpExpIndCd() {
		return impExpIndCd;
	}

	public void setImpExpIndCd(String impExpIndCd) {
		this.impExpIndCd = impExpIndCd;
	}

	public String getSendingSystemNm() {
		return sendingSystemNm;
	}

	public void setSendingSystemNm(String sendingSystemNm) {
		this.sendingSystemNm = sendingSystemNm;
	}

	public String getReceivingSystemNm() {
		return receivingSystemNm;
	}

	public void setReceivingSystemNm(String receivingSystemNm) {
		this.receivingSystemNm = receivingSystemNm;
	}

	public Long getNotificationUid() {
		return notificationUid;
	}

	public void setNotificationUid(Long notificationUid) {
		this.notificationUid = notificationUid;
	}

	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}

	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}


	public Timestamp getAddTime() {
		return addTime;
	}


	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}


	public String getXmlPayLoadContent() {
		return xmlPayLoadContent;
	}


	public void setXmlPayLoadContent(String xmlPayLoadContent) {
		this.xmlPayLoadContent = xmlPayLoadContent;
	}


	public String getSystemNm() {
		return systemNm;
	}


	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsInterfaceDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	
		

}
