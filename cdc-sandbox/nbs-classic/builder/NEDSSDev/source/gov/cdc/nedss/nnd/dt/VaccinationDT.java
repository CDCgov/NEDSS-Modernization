

package gov.cdc.nedss.nnd.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Comparator;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Name:		VaccinationDT.java
 * Description:	VO to retrieve Vaccination Elements from v_vaccination view
 * to construct Intermediary Messages
 * Company: 	CSRA
 * @author	Fatima Lopez Calzado
 */
public class VaccinationDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;

	
	private Long vaccUid;
	private Long caseUid;
	private String vaccLocalId;
	private Timestamp vaccAdminDt;
	private String vaccTypeCd;
	private String vaccType;
	private Timestamp vaccExpDt;
	private String vaccLotNbr;
	private String vaccBodySiteCd;
	private String vaccBodySite;
	private String vaccMfgrCd;
	private String vaccMfgr;
	private Long vaccSubjUid;
	private String vaccSubjFname;
	private String vaccSubjLname;
	private String vaccSubjLocalId;
	private Timestamp vaccAddTime;
	private Long vaccAddUser;
	private Timestamp vacclastChgTime;
	private Long vaccLastChgUser;
	private Integer vaccVersionNbr;
	private Integer vaccDoseNbr;
	private String vaccInfoSourceCd;
	private String vaccInfoSource;


	public Long getVaccUid() {
		return vaccUid;
	}
	public void setVaccUid(Long vaccUid) {
		this.vaccUid = vaccUid;
	}
	public Long getCaseUid() {
		return caseUid;
	}
	public void setCaseUid(Long caseUid) {
		this.caseUid = caseUid;
	}
	public String getVaccLocalId() {
		return vaccLocalId;
	}
	public void setVaccLocalId(String vaccLocalId) {
		this.vaccLocalId = vaccLocalId;
	}
	public Timestamp getVaccAdminDt() {
		return vaccAdminDt;
	}
	public void setVaccAdminDt(Timestamp vaccAdminDt) {
		this.vaccAdminDt = vaccAdminDt;
	}
	public String getVaccTypeCd() {
		return vaccTypeCd;
	}
	public void setVaccTypeCd(String vaccTypeCd) {
		this.vaccTypeCd = vaccTypeCd;
	}
	public String getVaccType() {
		return vaccType;
	}
	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}
	public Timestamp getVaccExpDt() {
		return vaccExpDt;
	}
	public void setVaccExpDt(Timestamp vaccExpDt) {
		this.vaccExpDt = vaccExpDt;
	}
	public String getVaccLotNbr() {
		return vaccLotNbr;
	}
	public void setVaccLotNbr(String vaccLotNbr) {
		this.vaccLotNbr = vaccLotNbr;
	}
	public String getVaccBodySiteCd() {
		return vaccBodySiteCd;
	}
	public void setVaccBodySiteCd(String vaccBodySiteCd) {
		this.vaccBodySiteCd = vaccBodySiteCd;
	}
	public String getVaccBodySite() {
		return vaccBodySite;
	}
	public void setVaccBodySite(String vaccBodySite) {
		this.vaccBodySite = vaccBodySite;
	}
	public String getVaccMfgrCd() {
		return vaccMfgrCd;
	}
	public void setVaccMfgrCd(String vaccMfgrCd) {
		this.vaccMfgrCd = vaccMfgrCd;
	}
	public String getVaccMfgr() {
		return vaccMfgr;
	}
	public void setVaccMfgr(String vaccMfgr) {
		this.vaccMfgr = vaccMfgr;
	}
	public Long getVaccSubjUid() {
		return vaccSubjUid;
	}
	public void setVaccSubjUid(Long vaccSubjUid) {
		this.vaccSubjUid = vaccSubjUid;
	}
	public String getVaccSubjFname() {
		return vaccSubjFname;
	}
	public void setVaccSubjFname(String vaccSubjFname) {
		this.vaccSubjFname = vaccSubjFname;
	}
	public String getVaccSubjLname() {
		return vaccSubjLname;
	}
	public void setVaccSubjLname(String vaccSubjLname) {
		this.vaccSubjLname = vaccSubjLname;
	}
	public String getVaccSubjLocalId() {
		return vaccSubjLocalId;
	}
	public void setVaccSubjLocalId(String vaccSubjLocalId) {
		this.vaccSubjLocalId = vaccSubjLocalId;
	}
	public Timestamp getVaccAddTime() {
		return vaccAddTime;
	}
	public void setVaccAddTime(Timestamp vaccAddTime) {
		this.vaccAddTime = vaccAddTime;
	}
	public Long getVaccAddUser() {
		return vaccAddUser;
	}
	public void setVaccAddUser(Long vaccAddUser) {
		this.vaccAddUser = vaccAddUser;
	}
	public Timestamp getVacclastChgTime() {
		return vacclastChgTime;
	}
	public void setVacclastChgTime(Timestamp vacclastChgTime) {
		this.vacclastChgTime = vacclastChgTime;
	}
	public Long getVaccLastChgUser() {
		return vaccLastChgUser;
	}
	public void setVaccLastChgUser(Long vaccLastChgUser) {
		this.vaccLastChgUser = vaccLastChgUser;
	}
	public Integer getVaccVersionNbr() {
		return vaccVersionNbr;
	}
	public void setVaccVersionNbr(Integer vaccVersionNbr) {
		this.vaccVersionNbr = vaccVersionNbr;
	}
	public Integer getVaccDoseNbr() {
		return vaccDoseNbr;
	}
	public void setVaccDoseNbr(Integer vaccDoseNbr) {
		this.vaccDoseNbr = vaccDoseNbr;
	}
	public String getVaccInfoSourceCd() {
		return vaccInfoSourceCd;
	}
	public void setVaccInfoSourceCd(String vaccInfoSourceCd) {
		this.vaccInfoSourceCd = vaccInfoSourceCd;
	}
	public String getVaccInfoSource() {
		return vaccInfoSource;
	}
	public void setVaccInfoSource(String vaccInfoSource) {
		this.vaccInfoSource = vaccInfoSource;
	}	
	//R	
	//RootDTInterface methods that are not implemented in new code

	/**
	 * @return the addReasonCd
	 */
	public String getAddReasonCd() {
		return null;
	}
	/**
	 * @param addReasonCd the addReasonCd to set
	 */
	public void setAddReasonCd(String addReasonCd) {
	}
	/**
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return null;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
	}
	/**
	 * @return the addUserId
	 */
	public Long getAddUserId() {
		return null;
	}
	/**
	 * @param addUserId the addUserId to set
	 */
	public void setAddUserId(Long addUserId) {
	}
	/**
	 * @return the lastChgReasonCd
	 */
	public String getLastChgReasonCd() {
		return null;
	}
	/**
	 * @param lastChgReasonCd the lastChgReasonCd to set
	 */
	public void setLastChgReasonCd(String lastChgReasonCd) {
	}
	/**
	 * @return the lastChgTime
	 */
	public Timestamp getLastChgTime() {
		return null;
	}
	/**
	 * @param lastChgTime the lastChgTime to set
	 */
	public void setLastChgTime(Timestamp lastChgTime) {
	}
	/**
	 * @return the lastChguserId
	 */
	public Long getLastChgUserId() {
		return null;
	}
	/**
	 * @param lastChguserId the lastChguserId to set
	 */
	public void setLastChgUserId(Long lastChgUserId) {
	}


	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub

	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub

	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
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
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the recordStatusCd
	 */
	public String getRecordStatusCd() {
		return null;
	}
	/**
	 * @param recordStatusCd the recordStatusCd to set
	 */
	public void setRecordStatusCd(String recordStatusCd) {
	}
	/**
	 * @return the recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}
	/**
	 * @param recordStatusTime the recordStatusTime to set
	 */
	public void setRecordStatusTime(Timestamp recordStatusTime) {
	}
/*
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CaseNotificationDataDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}

	public String getUnitTypeCd() {
		return unitTypeCd;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setOtherValueIndCd(String otherValueIndCd) {
		this.otherValueIndCd = otherValueIndCd;
	}

	public String getOtherValueIndCd() {
		return otherValueIndCd;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setBatchTableHeader(String batchTableHeader) {
		this.batchTableHeader = batchTableHeader;
	}

	public String getBatchTableHeader() {
		return batchTableHeader;
	}

	public void setBatchTableColumnWidth(Integer batchTableColumnWidth) {
		this.batchTableColumnWidth = batchTableColumnWidth;
	}

	public Integer getBatchTableColumnWidth() {
		return batchTableColumnWidth;
	}

	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}

	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}*/
}

