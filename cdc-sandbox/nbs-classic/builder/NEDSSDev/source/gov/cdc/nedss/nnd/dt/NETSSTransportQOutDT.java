

package gov.cdc.nedss.nnd.dt;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * NETSSTransportQOutDT - DT for the NBS_MSGOUTE.NETSS_TransportQ_out_uid
 *   This was created as a stop gap measure because the message mapping guide for STD
 *   has not been implemented so the legacy NETSS file approach is being utilized.
 *   This should be deleted when it is no longer needed.
 * @author TuckerG 
 * @since 2015-10-13
 *
 */

public class NETSSTransportQOutDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID       = 1L;
	private Long 		netssTransportqOutUid;
	private String 		recordTypeCd;
	private Short  		mmwrWeek;
	private Short  		mmwrYear;
	private String 		netssCaseId;
	private String 		phcLocalId;
	private String 		notificationLocalId;
	private Timestamp    addTime;
	private String 		payload;
	private String 		recordStatusCd;
	private boolean 	itNew;
	private boolean 	itDelete;
	private boolean 	itDirty;


	public Long getNetssTransportqOutUid() {
		return netssTransportqOutUid;
	}
	public void setNetssTransportqOutUid(Long netssTransportqOutUid) {
		this.netssTransportqOutUid = netssTransportqOutUid;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public Short getMmwrWeek() {
		return mmwrWeek;
	}
	public void setMmwrWeek(Short mmwrWeek) {
		this.mmwrWeek = mmwrWeek;
	}
	public Short getMmwrYear() {
		return mmwrYear;
	}
	public void setMmwrYear(Short mmwrYear) {
		this.mmwrYear = mmwrYear;
	}
	public String getNetssCaseId() {
		return netssCaseId;
	}
	public void setNetssCaseId(String netssCaseId) {
		this.netssCaseId = netssCaseId;
	}
	public String getPhcLocalId() {
		return phcLocalId;
	}
	public void setPhcLocalId(String phcLocalId) {
		this.phcLocalId = phcLocalId;
	}
	public String getNotificationLocalId() {
		return notificationLocalId;
	}
	public void setNotificationLocalId(String notificationLocalId) {
		this.notificationLocalId = notificationLocalId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public boolean isItNew() {
		return itNew;
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	public boolean isItDelete() {
		return itDelete;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	public boolean isItDirty() {
		return itDirty;
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		this.recordStatusCd = aRecordStatusCd;
	}
	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getEvent() {
		String thisPayload = getPayload();
		String conditionStr = "";
		if (thisPayload.startsWith("M")) {
			conditionStr =  payload.substring(17, 22);
			try {
				//make sure condition is 5 digits
				Integer conditionInt = new Integer(conditionStr);
				//per CHristi 10/26/15 allow any condition in table to go
				//if (conditionInt == 10273 ||
				//		conditionInt == 10274 ||
				//		conditionInt == 10280 ||
				//		conditionInt == 10311 ||
				//		conditionInt == 10312 ||
				//		conditionInt == 10313 ||
				//		conditionInt == 10314 ||
				//		conditionInt == 10319)
					return conditionStr;
				
			} catch (Exception donothing) {
			// System.out.println("Condition not numeric is: " + conditionStr);
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NETSSTransportQOutDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

}
