package gov.cdc.nedss.localfields.dt;

import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * NbsUiMetadataDT is the Table to store UI Metadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDT.java
 * Sep 2, 2008
 * @version
 */
public class NbsUiMetadataDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long nbsUiMetadataUid;
	private Long nbsUiComponentUid;
	private Long nbsQuestionUid;
	private Long parentUid;
	private Long nbsPageUid;
	private String questionLabel;
	private String questionToolTip;
	private String investigationFormCd;
	private String enableInd;
	private String defaultValue;
	private String displayInd;
	private Integer orderNbr;
	private String requiredInd;
	private Integer tabOrderId;
	private String tabName;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Long maxLength;
	private String ldfPosition;
	private String cssStyle;
	private String ldfPageId;
	private String ldfStatusCd;
	private Timestamp ldfStatusTime;
	private Integer versionCtrlNbr;
	private String adminComment;
	private String fieldSize;
	private String futureDateInd;
	private Long nbsTableUid;
	private Long codeSetGroupId;
	private String dataCd;
	private String dataLocation;
	private String dataType;
	private String dataUseCd;
	private String legacyDataLocation;
	private String partTypeCd;
	private Integer questionGroupSeqNbr;
	private String questionIdentifier;
	private String questionOid;
	private String questionOidSystemTxt;
	private String questionUnitIdentifier;
	private String repeatsIndCd;
	private String unitParentIdentifier;
	private String groupNm;
	private String subGroupNm;
	private String descTxt;
	private String mask;
	private Long minValue;
	private Long maxValue;
	private String standardNndIndCd;
	private String unitTypeCd;
    private String unitValue;
    private String otherValueIndCd;
    private String coinfectionIndCd;
    
    /* Batch entry attributes */
    private String batchTableAppearIndCd;
    private String batchTableHeader;
    private Integer batchTableColumnWidth ;
    private String questionWithQuestionIdentifier;
    private String blockName;
    private Integer dataMartRepeatNumber;
    
    
    
	

	public NbsUiMetadataDT() {}
	
	public NbsUiMetadataDT(WaUiMetadataDT waUiMetadataDT) {
		setNbsUiComponentUid(waUiMetadataDT.getNbsUiComponentUid());
		setParentUid(waUiMetadataDT.getParentUid());
		setQuestionLabel(waUiMetadataDT.getQuestionLabel());
		setQuestionToolTip(waUiMetadataDT.getQuestionToolTip());
		setEnableInd(waUiMetadataDT.getEnableInd());
		setDefaultValue(waUiMetadataDT.getDefaultValue());
		setDisplayInd(waUiMetadataDT.getDisplayInd());
		setOrderNbr(waUiMetadataDT.getOrderNbr());
		setRequiredInd(waUiMetadataDT.getRequiredInd());
		setTabOrderId(waUiMetadataDT.getTabOrderId());
		setTabName(waUiMetadataDT.getTabName());
		setMaxLength(waUiMetadataDT.getMaxLength());
		setAdminComment(waUiMetadataDT.getAdminComment());
		setFieldSize(waUiMetadataDT.getFieldLength());
		setCodeSetGroupId(waUiMetadataDT.getCodeSetGroupId());
		setDataType(waUiMetadataDT.getDataType());
		setQuestionIdentifier(waUiMetadataDT.getQuestionIdentifier());
		setGroupNm(waUiMetadataDT.getGroupNm());
		setSubGroupNm(waUiMetadataDT.getSubGroupNm());
		setMask(waUiMetadataDT.getMask());
		setMinValue(waUiMetadataDT.getMinValue());
		setMaxValue(waUiMetadataDT.getMaxValue());
		setFutureDateInd(waUiMetadataDT.getFutureDateIndCd());
		setLocalId(waUiMetadataDT.getLocalId());
		setStandardNndIndCd(waUiMetadataDT.getStandardNndIndCd());
		setPartTypeCd(waUiMetadataDT.getPartTypeCd());
		setQuestionOid(waUiMetadataDT.getQuestionOid());
		setQuestionOidSystemTxt(waUiMetadataDT.getQuestionOidSystemTxt());
		setLegacyDataLocation(waUiMetadataDT.getLegacyDataLocation());
		setQuestionUnitIdentifier(waUiMetadataDT.getQuestionUnitIdentifier());
		setUnitParentIdentifier(waUiMetadataDT.getUnitParentIndentifier());
		setUnitTypeCd(waUiMetadataDT.getUnitTypeCd());
		setUnitValue(waUiMetadataDT.getUnitValue());
		setOtherValueIndCd(waUiMetadataDT.getOtherValIndCd());
		setRequiredInd(waUiMetadataDT.getRequiredInd());
		setBatchTableAppearIndCd(waUiMetadataDT.getBatchTableAppearIndCd());
		setBatchTableHeader(waUiMetadataDT.getBatchTableHeader());
		setBatchTableColumnWidth(waUiMetadataDT.getBatchTableColumnWidth());
		setQuestionWithQuestionIdentifier(waUiMetadataDT.getQuestionWithQuestionIdentifier());
		setQuestionGroupSeqNbr(waUiMetadataDT.getQuestionGroupSeqNbr());
		setCoinfectionIndCd(waUiMetadataDT.getCoinfectionIndCd());
	}
	
	public Long getNbsUiMetadataUid() {
		return nbsUiMetadataUid;
	}
	public void setNbsUiMetadataUid(Long nbsUiMetadataUid) {
		this.nbsUiMetadataUid = nbsUiMetadataUid;
	}
	public Long getNbsUiComponentUid() {
		return nbsUiComponentUid;
	}
	public void setNbsUiComponentUid(Long nbsUiComponentUid) {
		this.nbsUiComponentUid = nbsUiComponentUid;
	}
	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}
	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}
	public Long getParentUid() {
		return parentUid;
	}
	public void setParentUid(Long parentUid) {
		this.parentUid = parentUid;
	}
	public Long getNbsPageUid() {
		return nbsPageUid;
	}
	public void setNbsPageUid(Long nbsPageUid) {
		this.nbsPageUid = nbsPageUid;
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
	public String getInvestigationFormCd() {
		return investigationFormCd;
	}
	public void setInvestigationFormCd(String investigationFormCd) {
		this.investigationFormCd = investigationFormCd;
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
	public String getDisplayInd() {
		return displayInd;
	}
	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}
	public Integer getOrderNbr() {
		return orderNbr;
	}
	public void setOrderNbr(Integer orderNbr) {
		this.orderNbr = orderNbr;
	}
	public String getRequiredInd() {
		return requiredInd;
	}
	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
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
	public Long getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}
	public String getLdfPosition() {
		return ldfPosition;
	}
	public void setLdfPosition(String ldfPosition) {
		this.ldfPosition = ldfPosition;
	}
	public String getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String getLdfPageId() {
		return ldfPageId;
	}
	public void setLdfPageId(String ldfPageId) {
		this.ldfPageId = ldfPageId;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public String getLdfStatusCd() {
		return ldfStatusCd;
	}
	public void setLdfStatusCd(String ldfStatusCd) {
		this.ldfStatusCd = ldfStatusCd;
	}
	public Timestamp getLdfStatusTime() {
		return ldfStatusTime;
	}
	public void setLdfStatusTime(Timestamp ldfStatusTime) {
		this.ldfStatusTime = ldfStatusTime;
	}
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public String getAdminComment() {
		return adminComment;
	}
	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	public String getFieldSize() {
		return fieldSize;
	}
	public void setFieldSize(String fieldSize) {
		this.fieldSize = fieldSize;
	}
	public Integer getTabOrderId() {
		return tabOrderId;
	}
	public void setTabOrderId(Integer tabOrderId) {
		this.tabOrderId = tabOrderId;
	}
	public String getFutureDateInd() {
		return futureDateInd;
	}
	public void setFutureDateInd(String futureDateInd) {
		this.futureDateInd = futureDateInd;
	}

	public Long getNbsTableUid() {
		return nbsTableUid;
	}

	public void setNbsTableUid(Long nbsTableUid) {
		this.nbsTableUid = nbsTableUid;
	}

	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
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

	public String getLegacyDataLocation() {
		return legacyDataLocation;
	}

	public void setLegacyDataLocation(String legacyDataLocation) {
		this.legacyDataLocation = legacyDataLocation;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}

	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}

	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
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

	public String getRepeatsIndCd() {
		return repeatsIndCd;
	}

	public void setRepeatsIndCd(String repeatsIndCd) {
		this.repeatsIndCd = repeatsIndCd;
	}

	public String getUnitParentIdentifier() {
		return unitParentIdentifier;
	}

	public void setUnitParentIdentifier(String unitParentIdentifier) {
		this.unitParentIdentifier = unitParentIdentifier;
	}

	public String getGroupNm() {
		return groupNm;
	}

	public void setGroupNm(String groupNm) {
		this.groupNm = groupNm;
	}

	public String getSubGroupNm() {
		return subGroupNm;
	}

	public void setSubGroupNm(String subGroupNm) {
		this.subGroupNm = subGroupNm;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	public String getStandardNndIndCd() {
		return standardNndIndCd;
	}

	public void setStandardNndIndCd(String standardNndIndCd) {
		this.standardNndIndCd = standardNndIndCd;
	}

	public String getUnitTypeCd() {
		return unitTypeCd;
	}

	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getOtherValueIndCd() {
		return otherValueIndCd;
	}

	public void setOtherValueIndCd(String otherValueIndCd) {
		this.otherValueIndCd = otherValueIndCd;
	}

	public String getBatchTableAppearIndCd() {
		return batchTableAppearIndCd;
	}

	public void setBatchTableAppearIndCd(String batchTableAppearIndCd) {
		this.batchTableAppearIndCd = batchTableAppearIndCd;
	}

	public String getBatchTableHeader() {
		return batchTableHeader;
	}

	public void setBatchTableHeader(String batchTableHeader) {
		this.batchTableHeader = batchTableHeader;
	}

	public Integer getBatchTableColumnWidth() {
		return batchTableColumnWidth;
	}

	public void setBatchTableColumnWidth(Integer batchTableColumnWidth) {
		this.batchTableColumnWidth = batchTableColumnWidth;
	}

	public String getQuestionWithQuestionIdentifier() {
		return questionWithQuestionIdentifier;
	}

	public void setQuestionWithQuestionIdentifier(
			String questionWithQuestionIdentifier) {
		this.questionWithQuestionIdentifier = questionWithQuestionIdentifier;
	}

	public String getCoinfectionIndCd() {
		return coinfectionIndCd;
	}

	public void setCoinfectionIndCd(String coinfectionIndCd) {
		this.coinfectionIndCd = coinfectionIndCd;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getDataMartRepeatNumber() {
		return dataMartRepeatNumber;
	}

	public void setDataMartRepeatNumber(Integer dataMartRepeatNumber) {
		this.dataMartRepeatNumber = dataMartRepeatNumber;
	}
	
	

}
