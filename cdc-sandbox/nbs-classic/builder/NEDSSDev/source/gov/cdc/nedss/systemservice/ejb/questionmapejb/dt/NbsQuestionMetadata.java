package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;

public class NbsQuestionMetadata extends AbstractVO  implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long nbsQuestionUid;
	private Timestamp addTime;
	private Long addUserId;
	private Long codeSetGroupId;
	private String dataType;
	private String investigationFormCd;
	private String templateType;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Integer orderNbr;
	private String questionLabel;
	private String questionToolTip;
	private String statusCd;
	private Timestamp statusTime;
	private Integer tabId;
	private Integer questionVersionNbr;
	private Long nndMetadataUid;
	private String questionIdentifier;
	private String questionIdentifierNnd;
	private String questionRequiredNnd;
	private String questionOid;
	private String questionOidSystemTxt;
	private String codeSetNm;
	private String codeSetClassCd;
	private String dataLocation;
	private String dataCd;
	private String dataUseCd;
	private String enableInd;
	private String defaultValue;
	private String requiredInd;
	private Long  parentUid;
	private String ldfPageId;
	private Long nbsUiMetadataUid;
	private Long nbsUiComponentUid;
	private Long nbsTableUid;
	private String fieldSize;
	private String futureDateInd;
	private String displayInd;
	private String jspSnippetCreateEdit;
	private String jspSnippetView;
	private String unitTypeCd;
	private String unitValue;
	private String standardNndIndCd;
	private String hl7SegmentField;
	private Integer questionGroupSeqNbr;
	private String partTypeCd;
	private String questionUnitIdentifier;
	private String mask;
	private String subGroupNm;
	private String coinfectionIndCd;
	
	/**
	 * @return the subGroupNm
	 */
	public String getSubGroupNm() {
		return subGroupNm;
	}

	/**
	 * @param subGroupNm the subGroupNm to set
	 */
	public void setSubGroupNm(String subGroupNm) {
		this.subGroupNm = subGroupNm;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getUnitTypeCd() {
		return unitTypeCd;
	}

	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}

	ArrayList<Object> aList = new ArrayList<Object>();

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getInvestigationFormCd() {
		return investigationFormCd;
	}

	public void setInvestigationFormCd(String investigationFormCd) {
		this.investigationFormCd = investigationFormCd;
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

	public Integer getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(Integer orderNbr) {
		this.orderNbr = orderNbr;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public String getQuestionIdentifierNnd() {
		return questionIdentifierNnd;
	}

	public void setQuestionIdentifierNnd(String questionIdentifierNnd) {
		this.questionIdentifierNnd = questionIdentifierNnd;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
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

	public String getQuestionToolTip() {
		return questionToolTip;
	}

	public void setQuestionToolTip(String questionToolTip) {
		this.questionToolTip = questionToolTip;
	}

	public Integer getQuestionVersionNbr() {
		return questionVersionNbr;
	}

	public void setQuestionVersionNbr(Integer questionVersionNbr) {
		this.questionVersionNbr = questionVersionNbr;
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

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public String getCodeSetNm() {
		return codeSetNm;
	}

	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
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

	public String getDataUseCd() {
		return dataUseCd;
	}

	public void setDataUseCd(String dataUseCd) {
		this.dataUseCd = dataUseCd;
	}

	public String getEnableInd() {
		return enableInd;
	}

	public void setEnableInd(String enableInd) {
		this.enableInd = enableInd;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRequiredInd() {
		return requiredInd;
	}

	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}

	public String getQuestionRequiredNnd() {
		return questionRequiredNnd;
	}

	public void setQuestionRequiredNnd(String questionRequiredNnd) {
		this.questionRequiredNnd = questionRequiredNnd;
	}

	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}

	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}

	public Long getNndMetadataUid() {
		return nndMetadataUid;
	}

	public void setNndMetadataUid(Long nndMetadataUid) {
		this.nndMetadataUid = nndMetadataUid;
	}

	public Long getParentUid() {
		return parentUid;
	}

	public void setParentUid(Long parentUid) {
		this.parentUid = parentUid;
	}

	public String getLdfPageId() {
		return ldfPageId;
	}

	public void setLdfPageId(String ldfPageId) {
		this.ldfPageId = ldfPageId;
	}

	public Long getNbsUiMetadataUid() {
		return nbsUiMetadataUid;
	}

	public void setNbsUiMetadataUid(Long nbsUiMetadataUid) {
		this.nbsUiMetadataUid = nbsUiMetadataUid;
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

	public Long getNbsUiComponentUid() {
		return nbsUiComponentUid;
	}

	public void setNbsUiComponentUid(Long nbsUiComponentUid) {
		this.nbsUiComponentUid = nbsUiComponentUid;
	}

	public String getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(String fieldSize) {
		this.fieldSize = fieldSize;
	}

	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	public String getFutureDateInd() {
		return futureDateInd;
	}

	/**
	 * @param futureDateInd
	 */
	public void setFutureDateInd(String futureDateInd) {
		this.futureDateInd = futureDateInd;
	}

	public String getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}

	public Long getNbsTableUid() {
		return nbsTableUid;
	}

	public void setNbsTableUid(Long nbsTableUid) {
		this.nbsTableUid = nbsTableUid;
	}

	public ArrayList<Object> getAList() {
		return aList;
	}

	public void setAList(ArrayList<Object> list) {
		aList = list;
	}

	public String getJspSnippetCreateEdit() {
		return jspSnippetCreateEdit;
	}

	public void setJspSnippetCreateEdit(String jspSnippetCreateEdit) {
		this.jspSnippetCreateEdit = jspSnippetCreateEdit;
	}

	public String getJspSnippetView() {
		return jspSnippetView;
	}

	public void setJspSnippetView(String jspSnippetView) {
		this.jspSnippetView = jspSnippetView;
	}

	public String getStandardNndIndCd() {
		return standardNndIndCd;
	}

	public void setStandardNndIndCd(String standardNndIndCd) {
		this.standardNndIndCd = standardNndIndCd;
	}

	

	public String getHl7SegmentField() {
		return hl7SegmentField;
	}

	public void setHl7SegmentField(String hl7SegmentField) {
		this.hl7SegmentField = hl7SegmentField;
	}

	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}

	public String getQuestionUnitIdentifier() {
		return questionUnitIdentifier;
	}

	public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
		this.questionUnitIdentifier = questionUnitIdentifier;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getCoinfectionIndCd() {
		return coinfectionIndCd;
	}

	public void setCoinfectionIndCd(String coinfectionIndCd) {
		this.coinfectionIndCd = coinfectionIndCd;
	}

	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}

	public String getCodeSetClassCd() {
		return codeSetClassCd;
	}

	public void setCodeSetClassCd(String codeSetClassCd) {
		this.codeSetClassCd = codeSetClassCd;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsQuestionMetadata.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
}
