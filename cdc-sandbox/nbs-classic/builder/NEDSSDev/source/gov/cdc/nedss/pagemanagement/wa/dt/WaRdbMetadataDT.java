package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class WaRdbMetadataDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long waRdbMetadataUid;
	private Long waQuestionUid;
	private Long waTemplateUid;
	private String rdbTableNm;
	private String recStatusCd;
	private Timestamp recStatusTime;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Integer versionCtrlNbr;
	private String localId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String rdbcolumNm;
	private Long waUiMetadataUid;
	private String rptAdmColumnNm;
	private String userDefinedColumnNm;
	private String questionIdentifier;
	private Integer dataMartRepeatNbr;
	private String partTypeCd;
	private String blockName;
	private String dataType;
	private String otherValueIndCd;
	private String unitTypeCd;
	
	
	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return true;
	}

	/**
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return this.itDirty;
	}

	/**
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	 * @return boolean
	 */
	public boolean isItNew() {
		return this.itNew;
	}

	/**
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDelete() {
		return this.itDelete;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId() {
		return localId;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return recordStatusTime;
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

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub

	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub

	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub

	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub

	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;

	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;

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

	public Long getWaQuestionUid() {
		return waQuestionUid;
	}

	public void setWaQuestionUid(Long waQuestionUid) {
		this.waQuestionUid = waQuestionUid;
	}

	public Long getWaRdbMetadataUid() {
		return waRdbMetadataUid;
	}

	public void setWaRdbMetadataUid(Long waRdbMetadataUid) {
		this.waRdbMetadataUid = waRdbMetadataUid;
	}

	public Long getWaTemplateUid() {
		return waTemplateUid;
	}

	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}

	public String getRdbTableNm() {
		return rdbTableNm;
	}

	public void setRdbTableNm(String rdbTableNm) {
		this.rdbTableNm = rdbTableNm;
	}

	public String getRecStatusCd() {
		return recStatusCd;
	}

	public void setRecStatusCd(String recStatusCd) {
		this.recStatusCd = recStatusCd;
	}

	public Timestamp getRecStatusTime() {
		return recStatusTime;
	}

	public void setRecStatusTime(Timestamp recStatusTime) {
		this.recStatusTime = recStatusTime;
	}

	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}

	public String getRdbcolumNm() {
		return rdbcolumNm;
	}

	public void setRdbcolumNm(String rdbcolumNm) {
		this.rdbcolumNm = rdbcolumNm;
	}

	public Long getWaUiMetadataUid() {
		return waUiMetadataUid;
	}

	public void setWaUiMetadataUid(Long waUiMetadataUid) {
		this.waUiMetadataUid = waUiMetadataUid;
	}

	public void setRptAdminColumnNm(String rptAdminColumnNm) {
		this.rptAdmColumnNm = rptAdminColumnNm;
	}

	public String getRptAdminColumnNm() {
		return rptAdmColumnNm;
	}

	public String getUserDefinedColumnNm() {
		return userDefinedColumnNm;
	}

	public void setUserDefinedColumnNm(String userDefinedColumnNm) {
		this.userDefinedColumnNm = userDefinedColumnNm;
	}
	
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public Integer getDataMartRepeatNbr() {
		return dataMartRepeatNbr;
	}

	public void setDataMartRepeatNbr(Integer dataMartRepeatNbr) {
		this.dataMartRepeatNbr = dataMartRepeatNbr;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}

	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	

	public String getOtherValueIndCd() {
		return otherValueIndCd;
	}

	public void setOtherValueIndCd(String otherValueIndCd) {
		this.otherValueIndCd = otherValueIndCd;
	}

	public String getUnitTypeCd() {
		return unitTypeCd;
	}

	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}
}
