package gov.cdc.nedss.systemservice.ejb.dbauthejb.dt;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * NBSConfigurationDT - Holds configuration data from database replaced NEDSS.property
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: CSRA for CDC</p>
 * Feb 10th, 2017
 * @version 0.9
 */

public class NBSConfigurationDT  extends AbstractVO  implements RootDTInterface,  Serializable{
	private static final long serialVersionUID = 1L;

    private String configKey;
    private String configValue;
    private String adminComment;
    private String category;
    private String descTxt;
    private String defaultValue;
    private String addRelease;
    private Long addUserId;
    private Timestamp addTime;
    private Long lastChgUserId;
    private Timestamp lastChgTime;
	private String statusCd;
    private Timestamp statusTime;
    private Integer versionCtrlNbr;
    private String validValues;

    public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getAdminComment() {
		return adminComment;
	}
	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescTxt() {
		return descTxt;
	}
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getAddRelease() {
		return addRelease;
	}
	public void setAddRelease(String addRelease) {
		this.addRelease = addRelease;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public Timestamp getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public String getValidValues() {
		return validValues;
	}
	public void setValidValues(String validValues) {
		this.validValues = validValues;
	}



	@Override
	public String getJurisdictionCd() {
		return null;
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
	}
	@Override
	public String getProgAreaCd() {
		return null;
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
	}
	@Override
	public String getLocalId() {
		return null;
	}
	@Override
	public void setLocalId(String aLocalId) {
	}
	@Override
	public String getLastChgReasonCd() {
		return null;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
	}
	@Override
	public String getRecordStatusCd() {
		return null;
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
	}
	@Override
	public Timestamp getRecordStatusTime() {
		return null;
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
	}
	@Override
	public String getSuperclass() {
		return null;
	}
	@Override
	public Long getUid() {
		return null;
	}
	@Override
	public boolean isItNew() {
		return false;
	}
	@Override
	public void setItNew(boolean itNew) {
	}
	@Override
	public boolean isItDirty() {

		return false;
	}
	@Override
	public void setItDirty(boolean itDirty) {
	}
	@Override
	public boolean isItDelete() {
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
	}
	@Override
	public Long getProgramJurisdictionOid() {
		return null;
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	}
	@Override
	public String getSharedInd() {
		return null;
	}
	@Override
	public void setSharedInd(String aSharedInd) {
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}


}
