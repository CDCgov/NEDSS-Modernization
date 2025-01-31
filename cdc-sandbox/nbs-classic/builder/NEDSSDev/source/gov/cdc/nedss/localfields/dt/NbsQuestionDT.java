package gov.cdc.nedss.localfields.dt;

import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * NbsQuestionDT is the parent Table to store Question root Metadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDT.java
 * Sep 2, 2008
 * @version
 */
public class NbsQuestionDT extends AbstractVO  implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long nbsQuestionUid;
	private Timestamp addTime;
	private Long addUserId;
	private Long codeSetGroupId;
	private String dataCd;
	private String dataLocation;
	private String questionIdentifier;
	private String questionOid;
	private String questionOidSystemTxt;
	private String questionUnitIdentifier;
	private String dataType;	
	private String dataUseCd;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String questionLabel;
	private String questionToolTip;
	private String datamartColumnNm;
	private String partTypeCd;
	private String defaultValue;
	private Integer versionCtrlNbr;
	private String unitParentIdentifier;

	public NbsQuestionDT() {}
	
	public NbsQuestionDT(WaQuestionDT waQuestionDT) {
		setCodeSetGroupId(waQuestionDT.getCodeSetGroupId());
		setDataCd(waQuestionDT.getDataCd());
		setDataLocation(waQuestionDT.getDataLocation());
		setQuestionIdentifier(waQuestionDT.getQuestionIdentifier());
		setQuestionOid(waQuestionDT.getQuestionOid());
		setQuestionOidSystemTxt(waQuestionDT.getQuestionOidSystemTxt());
		setQuestionUnitIdentifier(waQuestionDT.getQuestionUnitIdentifier());
		setDataType(waQuestionDT.getDataType());	
		setDataUseCd(waQuestionDT.getDataUseCd());
		setQuestionLabel(waQuestionDT.getQuestionNm());
		setQuestionToolTip(waQuestionDT.getQuestionToolTip());
		setDatamartColumnNm(waQuestionDT.getRdbcolumnNm());
		setPartTypeCd(waQuestionDT.getPartTypeCd());
		setDefaultValue(waQuestionDT.getDefaultValue());
	}

	
	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		return true;
	}

	/**
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {

	}

	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return false;
	}

	/**
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {

	}

	/**
	 * @return boolean
	 */
	public boolean isItNew() {
		return false;
	}

	/**
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {

	}

	/**
	 * @return boolean
	 */
	public boolean isItDelete() {
		return false;
	}

	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}

	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
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

	public String getDataCd() {
		return dataCd;
	}

	public void setDataCd(String dataCd) {
		this.dataCd = dataCd;
	}

	public String getDataLocation() {
		return dataLocation;
	}

	public void setDataLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public String getQuestionOid() {
		return questionOid;
	}

	public void setQuestionOid(String questionOid) {
		this.questionOid = questionOid;
	}

	public String getQuestionOidSystemTxt() {
		return questionOidSystemTxt;
	}

	public void setQuestionOidSystemTxt(String questionOidSystemTxt) {
		this.questionOidSystemTxt = questionOidSystemTxt;
	}

	public String getQuestionUnitIdentifier() {
		return questionUnitIdentifier;
	}

	public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
		this.questionUnitIdentifier = questionUnitIdentifier;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataUseCd() {
		return dataUseCd;
	}

	public void setDataUseCd(String dataUseCd) {
		this.dataUseCd = dataUseCd;
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

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getQuestionToolTip() {
		return questionToolTip;
	}

	public void setQuestionToolTip(String questionToolTip) {
		this.questionToolTip = questionToolTip;
	}

	public String getDatamartColumnNm() {
		return datamartColumnNm;
	}

	public void setDatamartColumnNm(String datamartColumnNm) {
		this.datamartColumnNm = datamartColumnNm;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}

	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getRecordStatusTime() {
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

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
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

	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
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

	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}

	public String getUnitParentIdentifier() {
		return unitParentIdentifier;
	}

	public void setUnitParentIdentifier(String unitParentIdentifier) {
		this.unitParentIdentifier = unitParentIdentifier;
	}

	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}
}
