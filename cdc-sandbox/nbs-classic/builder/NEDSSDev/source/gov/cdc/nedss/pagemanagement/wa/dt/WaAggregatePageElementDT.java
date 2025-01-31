package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;


public class WaAggregatePageElementDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long waUiMetadataWaUiMetadataUid;
	private Long waUiMetadataWaTemplateUid;
	private Long waUiMetadataNbsUiComponentUid;
	private Long waUiMetadataWaQuestionUid;
	private Long waUiMetadataParentUid;
	private String waUiMetadataQuestionLabel;
	private String waUiMetadataBlockName;
	private String waUiMetadataQuestionToolTip;
	private String waUiMetadataEnableInd;
	private String waUiMetadataDefaultValue;
	private String waUiMetadataDisplayInd;
	private Integer waUiMetadataOrderNbr;
	private String waUiMetadataRequiredInd;
	private Integer waUiMetadataTabOrderId;
	private String waUiMetadataTabName;
	private Timestamp waUiMetadataAddTime;
	private Long waUiMetadataAddUserId;
	private Timestamp waUiMetadataLastChgTime;
	private Long waUiMetadataLastChgUserId;
	private String waUiMetadataRecordStatusCd;
	private Timestamp waUiMetadataRecordStatusTime;
	private Long waUiMetadataMaxLength;
	private String waUiMetadataCssStyle;
	private Integer waUiMetadataVersionCtrlNbr;
	private String waUiMetadataAdminComment;
	private String waUiMetadataFieldSize;
	private String waUiMetadataFutureDateIndCd;
	private String waUiMetadataLocalId;
	private String waUiMetadataDataType;
	private String waUiMetadataValSet;
	private String waUiMetadataDefaultDisplayControl;
	private String waUiMetadataNndMsgInd;
	private String waUiMetadataMsgLabel;
	private String waUiMetadataMask;
	private String waUiMetadataFieldLength;
	private Timestamp waUiMetadataEarliestDateAllowed;
	private Timestamp waUiMetadataLatestDateAllowed;
	private String waUiMetadataMinValue;
	private String waUiMetadataMaxValue;
	private String waUiMetadataParticipationTypeDesc;
	private String waUiMetadataIsSecured;
	private String waUiMetadataIsRequired;
	private String waUiMetadataIsRequiredInMessage;
	private String waUiMetadataIsIncludeInDatamart;
	
	private String waUiMetadataReportLabel;
	private String waUiMetadataRelatedUnitLabel;
	private String waUiMetadataEntryMethod;
	private String waUiMetadataQuestionType;
	private String waUiMetadataPublishIndCd;
	private String waUiMetadataCoinfectionIndCd;
	
	private Integer waUiMetadataQueGrpSeqNbr;
	private String batchTableAppearIndCd;
    private String batchTableHeader;
    private Integer batchTableColumnWidth ;

	
	private Long waRdbWaRdbMetadataUid;
	private Integer waRdbMetadataDataMartRepeatNbr;
	private Long waRdbWaUiMetadataUid;
	private Long waRdbWaTemplateUid;
	private String waRdbRdbTableNm;
	private String waRdbRdbColumnNm;
	private String waRdbRptAdminColumnNm;
	private String waRdbRecStatusCd;
	private Timestamp waRdbRecStatusTime;
	private Timestamp waRdbAddTime;
	private Long waRdbAddUserId;	
	private Timestamp waRdbLastChgTime;
	private Long waRdbLastChgUserId;	
	private Integer waRdbVersionCtrlNbr;
	private String waRdbLocalId;
	private String waUserDefinedColumnNm;
	private String waRdbQuestionIdentifier;

	private Long waNndWaNndMetadataUid;
	private Long waNndWaUiMetadataUid;
	private Long waNndWaTemplateUid;
	private String waNndQuestionIdentifierNnd;
	private String waNndQuestionLabelNnd;
	private String waNndQuestionRequiredNnd;
	private String waNndQuestionDataTypeNnd;
	private String waNndHl7SegmentField;
	private String waNndOrderGroupId;
	private String waNndTranslationTableNm;		
	private Timestamp waNndAddTime;
	private Long waNndAddUserId;
	private Timestamp waNndLastChgTime;
	private Long waNndLastChgUserId;
	private String waNndRecStatusCd;
	private Timestamp waNndRecStatusTime;
	private String waNndQuestionIdentifier;
	private String waNndMsgTriggerIndCd;
	private String waNndXmlPath;
	private String waNndXmlTag;
	private String waNndXmlDataType;
	private String waNndPartTypeCd;
	private Integer waNndRepeatGroupSeqNbr;
	private Integer waNndQuestionOrderNnd;	
	private String waNndStatusCd;
	private Timestamp waNndStatusTime;
	private String waNndLocalId;
	private String waNndQuestionMap;
	private String waNndIndicatorCd;
	
	private Long codeSetGroupId;
	private String questionIdentifier;
	private String codeSetName;
	private String standardNndIndCd;
	private String questionUnitIdentifier;
	private String unitParentIdentifier;
	private String partTypeCd;
	
	private String dataCd;
	private String legacyDataLocation;
	private String dataLocation	;
	private String dataUseCd;
	private String questionOid;
	private String questionOidSystemTxt;
	private String repeatsIndCd;
	private String groupNm;
	private String subGroupNm;
	private String descTxt;
	private String standardQuestionIndCd;
	private String questionNm;
	private Long minValue;
	private Long maxValue;
	private String unitTypeCd;
	private String unitValue;
	private String otherValIndCd;
	private String publishIndCd;
	
	public Integer getWaUiMetadataQueGrpSeqNbr() {
		return waUiMetadataQueGrpSeqNbr;
	}
	public void setWaUiMetadataQueGrpSeqNbr(Integer waUiMetadataQueGrpSeqNbr) {
		this.waUiMetadataQueGrpSeqNbr = waUiMetadataQueGrpSeqNbr;
	}
	
	public Long getWaUiMetadataWaUiMetadataUid() {
		return waUiMetadataWaUiMetadataUid;
	}
	public void setWaUiMetadataWaUiMetadataUid(Long waUiMetadataWaUiMetadataUid) {
		this.waUiMetadataWaUiMetadataUid = waUiMetadataWaUiMetadataUid;
	}
	public Long getWaUiMetadataWaTemplateUid() {
		return waUiMetadataWaTemplateUid;
	}
	public void setWaUiMetadataWaTemplateUid(Long waUiMetadataWaTemplateUid) {
		this.waUiMetadataWaTemplateUid = waUiMetadataWaTemplateUid;
	}
	public Long getWaUiMetadataNbsUiComponentUid() {
		return waUiMetadataNbsUiComponentUid;
	}
	public void setWaUiMetadataNbsUiComponentUid(Long waUiMetadataNbsUiComponentUid) {
		this.waUiMetadataNbsUiComponentUid = waUiMetadataNbsUiComponentUid;
	}
	public Long getWaUiMetadataWaQuestionUid() {
		return waUiMetadataWaQuestionUid;
	}
	public void setWaUiMetadataWaQuestionUid(Long waUiMetadataWaQuestionUid) {
		this.waUiMetadataWaQuestionUid = waUiMetadataWaQuestionUid;
	}
	public Long getWaUiMetadataParentUid() {
		return waUiMetadataParentUid;
	}
	public void setWaUiMetadataParentUid(Long waUiMetadataParentUid) {
		this.waUiMetadataParentUid = waUiMetadataParentUid;
	}
	public String getWaUiMetadataQuestionLabel() {
		return waUiMetadataQuestionLabel;
	}
	public void setWaUiMetadataQuestionLabel(String waUiMetadataQuestionLabel) {
		this.waUiMetadataQuestionLabel = waUiMetadataQuestionLabel;
	}
	public String getWaUiMetadataQuestionToolTip() {
		return waUiMetadataQuestionToolTip;
	}
	public void setWaUiMetadataQuestionToolTip(String waUiMetadataQuestionToolTip) {
		this.waUiMetadataQuestionToolTip = waUiMetadataQuestionToolTip;
	}
	public String getWaUiMetadataEnableInd() {
		return waUiMetadataEnableInd;
	}
	public void setWaUiMetadataEnableInd(String waUiMetadataEnableInd) {
		this.waUiMetadataEnableInd = waUiMetadataEnableInd;
	}
	public String getWaUiMetadataDefaultValue() {
		return waUiMetadataDefaultValue;
	}
	public void setWaUiMetadataDefaultValue(String waUiMetadataDefaultValue) {
		this.waUiMetadataDefaultValue = waUiMetadataDefaultValue;
	}
	public String getWaUiMetadataDisplayInd() {
		return waUiMetadataDisplayInd;
	}
	public void setWaUiMetadataDisplayInd(String waUiMetadataDisplayInd) {
		this.waUiMetadataDisplayInd = waUiMetadataDisplayInd;
	}
	public Integer getWaUiMetadataOrderNbr() {
		return waUiMetadataOrderNbr;
	}
	public void setWaUiMetadataOrderNbr(Integer waUiMetadataOrderNbr) {
		this.waUiMetadataOrderNbr = waUiMetadataOrderNbr;
	}
	public String getWaUiMetadataRequiredInd() {
		return waUiMetadataRequiredInd;
	}
	public void setWaUiMetadataRequiredInd(String waUiMetadataRequiredInd) {
		this.waUiMetadataRequiredInd = waUiMetadataRequiredInd;
	}
	public Integer getWaUiMetadataTabOrderId() {
		return waUiMetadataTabOrderId;
	}
	public void setWaUiMetadataTabOrderId(Integer waUiMetadataTabOrderId) {
		this.waUiMetadataTabOrderId = waUiMetadataTabOrderId;
	}
	public String getWaUiMetadataTabName() {
		return waUiMetadataTabName;
	}
	public void setWaUiMetadataTabName(String waUiMetadataTabName) {
		this.waUiMetadataTabName = waUiMetadataTabName;
	}
	public Timestamp getWaUiMetadataAddTime() {
		return waUiMetadataAddTime;
	}
	public void setWaUiMetadataAddTime(Timestamp waUiMetadataAddTime) {
		this.waUiMetadataAddTime = waUiMetadataAddTime;
	}
	public Long getWaUiMetadataAddUserId() {
		return waUiMetadataAddUserId;
	}
	public void setWaUiMetadataAddUserId(Long waUiMetadataAddUserId) {
		this.waUiMetadataAddUserId = waUiMetadataAddUserId;
	}
	public Timestamp getWaUiMetadataLastChgTime() {
		return waUiMetadataLastChgTime;
	}
	public void setWaUiMetadataLastChgTime(Timestamp waUiMetadataLastChgTime) {
		this.waUiMetadataLastChgTime = waUiMetadataLastChgTime;
	}
	public Long getWaUiMetadataLastChgUserId() {
		return waUiMetadataLastChgUserId;
	}
	public void setWaUiMetadataLastChgUserId(Long waUiMetadataLastChgUserId) {
		this.waUiMetadataLastChgUserId = waUiMetadataLastChgUserId;
	}
	public String getWaUiMetadataRecordStatusCd() {
		return waUiMetadataRecordStatusCd;
	}
	public void setWaUiMetadataRecordStatusCd(String waUiMetadataRecordStatusCd) {
		this.waUiMetadataRecordStatusCd = waUiMetadataRecordStatusCd;
	}
	public Timestamp getWaUiMetadataRecordStatusTime() {
		return waUiMetadataRecordStatusTime;
	}
	public void setWaUiMetadataRecordStatusTime(
			Timestamp waUiMetadataRecordStatusTime) {
		this.waUiMetadataRecordStatusTime = waUiMetadataRecordStatusTime;
	}
	public Long getWaUiMetadataMaxLength() {
		return waUiMetadataMaxLength;
	}
	public void setWaUiMetadataMaxLength(Long waUiMetadataMaxLength) {
		this.waUiMetadataMaxLength = waUiMetadataMaxLength;
	}
	public String getWaUiMetadataCssStyle() {
		return waUiMetadataCssStyle;
	}
	public void setWaUiMetadataCssStyle(String waUiMetadataCssStyle) {
		this.waUiMetadataCssStyle = waUiMetadataCssStyle;
	}
	public Integer getWaUiMetadataVersionCtrlNbr() {
		return waUiMetadataVersionCtrlNbr;
	}
	public void setWaUiMetadataVersionCtrlNbr(Integer waUiMetadataVersionCtrlNbr) {
		this.waUiMetadataVersionCtrlNbr = waUiMetadataVersionCtrlNbr;
	}
	public String getWaUiMetadataAdminComment() {
		return waUiMetadataAdminComment;
	}
	public void setWaUiMetadataAdminComment(String waUiMetadataAdminComment) {
		this.waUiMetadataAdminComment = waUiMetadataAdminComment;
	}
	public String getWaUiMetadataFieldSize() {
		return waUiMetadataFieldSize;
	}
	public void setWaUiMetadataFieldSize(String waUiMetadataFieldSize) {
		this.waUiMetadataFieldSize = waUiMetadataFieldSize;
	}
	public String getWaUiMetadataFutureDateIndCd() {
		return waUiMetadataFutureDateIndCd;
	}
	public void setWaUiMetadataFutureDateIndCd(String waUiMetadataFutureDateIndCd) {
		this.waUiMetadataFutureDateIndCd = waUiMetadataFutureDateIndCd;
	}
	public String getWaUiMetadataLocalId() {
		return waUiMetadataLocalId;
	}
	public void setWaUiMetadataLocalId(String waUiMetadataLocalId) {
		this.waUiMetadataLocalId = waUiMetadataLocalId;
	}
	public String getWaUiMetadataDataType() {
		return waUiMetadataDataType;
	}
	public void setWaUiMetadataDataType(String waUiMetadataDataType) {
		this.waUiMetadataDataType = waUiMetadataDataType;
	}
	public String getWaUiMetadataValSet() {
		return waUiMetadataValSet;
	}
	public void setWaUiMetadataValSet(String waUiMetadataValSet) {
		this.waUiMetadataValSet = waUiMetadataValSet;
	}
	public String getWaUiMetadataDefaultDisplayControl() {
		return waUiMetadataDefaultDisplayControl;
	}
	public void setWaUiMetadataDefaultDisplayControl(
			String waUiMetadataDefaultDisplayControl) {
		this.waUiMetadataDefaultDisplayControl = waUiMetadataDefaultDisplayControl;
	}
	public String getWaUiMetadataNndMsgInd() {
		return waUiMetadataNndMsgInd;
	}
	public void setWaUiMetadataNndMsgInd(String waUiMetadataNndMsgInd) {
		this.waUiMetadataNndMsgInd = waUiMetadataNndMsgInd;
	}
	public String getWaUiMetadataMsgLabel() {
		return waUiMetadataMsgLabel;
	}
	public void setWaUiMetadataMsgLabel(String waUiMetadataMsgLabel) {
		this.waUiMetadataMsgLabel = waUiMetadataMsgLabel;
	}
	public String getWaUiMetadataMask() {
		return waUiMetadataMask;
	}
	public void setWaUiMetadataMask(String waUiMetadataMask) {
		this.waUiMetadataMask = waUiMetadataMask;
	}
	public String getWaUiMetadataFieldLength() {
		return waUiMetadataFieldLength;
	}
	public void setWaUiMetadataFieldLength(String waUiMetadataFieldLength) {
		this.waUiMetadataFieldLength = waUiMetadataFieldLength;
	}
	public Timestamp getWaUiMetadataEarliestDateAllowed() {
		return waUiMetadataEarliestDateAllowed;
	}
	public void setWaUiMetadataEarliestDateAllowed(
			Timestamp waUiMetadataEarliestDateAllowed) {
		this.waUiMetadataEarliestDateAllowed = waUiMetadataEarliestDateAllowed;
	}
	public Timestamp getWaUiMetadataLatestDateAllowed() {
		return waUiMetadataLatestDateAllowed;
	}
	public void setWaUiMetadataLatestDateAllowed(
			Timestamp waUiMetadataLatestDateAllowed) {
		this.waUiMetadataLatestDateAllowed = waUiMetadataLatestDateAllowed;
	}
	public String getWaUiMetadataMinValue() {
		return waUiMetadataMinValue;
	}
	public void setWaUiMetadataMinValue(String waUiMetadataMinValue) {
		this.waUiMetadataMinValue = waUiMetadataMinValue;
	}
	public String getWaUiMetadataMaxValue() {
		return waUiMetadataMaxValue;
	}
	public void setWaUiMetadataMaxValue(String waUiMetadataMaxValue) {
		this.waUiMetadataMaxValue = waUiMetadataMaxValue;
	}
	public String getWaUiMetadataParticipationTypeDesc() {
		return waUiMetadataParticipationTypeDesc;
	}
	public void setWaUiMetadataParticipationTypeDesc(
			String waUiMetadataParticipationTypeDesc) {
		this.waUiMetadataParticipationTypeDesc = waUiMetadataParticipationTypeDesc;
	}
	public String getWaUiMetadataIsSecured() {
		return waUiMetadataIsSecured;
	}
	public void setWaUiMetadataIsSecured(String waUiMetadataIsSecured) {
		this.waUiMetadataIsSecured = waUiMetadataIsSecured;
	}
	public String getWaUiMetadataIsRequired() {
		return waUiMetadataIsRequired;
	}
	public void setWaUiMetadataIsRequired(String waUiMetadataIsRequired) {
		this.waUiMetadataIsRequired = waUiMetadataIsRequired;
	}
	public String getWaUiMetadataIsRequiredInMessage() {
		return waUiMetadataIsRequiredInMessage;
	}
	public void setWaUiMetadataIsRequiredInMessage(
			String waUiMetadataIsRequiredInMessage) {
		this.waUiMetadataIsRequiredInMessage = waUiMetadataIsRequiredInMessage;
	}
	public String getWaUiMetadataIsIncludeInDatamart() {
		return waUiMetadataIsIncludeInDatamart;
	}
	public void setWaUiMetadataIsIncludeInDatamart(
			String waUiMetadataIsIncludeInDatamart) {
		this.waUiMetadataIsIncludeInDatamart = waUiMetadataIsIncludeInDatamart;
	}
	public String getWaUiMetadataReportLabel() {
		return waUiMetadataReportLabel;
	}
	public void setWaUiMetadataReportLabel(String waUiMetadataReportLabel) {
		this.waUiMetadataReportLabel = waUiMetadataReportLabel;
	}
	public String getWaUiMetadataRelatedUnitLabel() {
		return waUiMetadataRelatedUnitLabel;
	}
	public void setWaUiMetadataRelatedUnitLabel(String waUiMetadataRelatedUnitLabel) {
		this.waUiMetadataRelatedUnitLabel = waUiMetadataRelatedUnitLabel;
	}
	public String getWaUiMetadataEntryMethod() {
		return waUiMetadataEntryMethod;
	}
	public void setWaUiMetadataEntryMethod(String waUiMetadataEntryMethod) {
		this.waUiMetadataEntryMethod = waUiMetadataEntryMethod;
	}
	public String getWaUiMetadataQuestionType() {
		return waUiMetadataQuestionType;
	}
	public void setWaUiMetadataQuestionType(String waUiMetadataQuestionType) {
		this.waUiMetadataQuestionType = waUiMetadataQuestionType;
	}
	public String getWaUiMetadataPublishIndCd() {
		return waUiMetadataPublishIndCd;
	}
	public void setWaUiMetadataPublishIndCd(String waUiMetadataPublishIndCd) {
		this.waUiMetadataPublishIndCd = waUiMetadataPublishIndCd;
	}
	
	public String getWaUiMetadataCoinfectionIndCd() {
		return waUiMetadataCoinfectionIndCd;
	}
	public void setWaUiMetadataCoinfectionIndCd(String waUiMetadataCoinfectionIndCd) {
		this.waUiMetadataCoinfectionIndCd = waUiMetadataCoinfectionIndCd;
	}
	public Long getWaRdbWaRdbMetadataUid() {
		return waRdbWaRdbMetadataUid;
	}
	public void setWaRdbWaRdbMetadataUid(Long waRdbWaRdbMetadataUid) {
		this.waRdbWaRdbMetadataUid = waRdbWaRdbMetadataUid;
	}

	public Long getWaRdbWaTemplateUid() {
		return waRdbWaTemplateUid;
	}
	public void setWaRdbWaTemplateUid(Long waRdbWaTemplateUid) {
		this.waRdbWaTemplateUid = waRdbWaTemplateUid;
	}
	public String getWaRdbRdbTableNm() {
		return waRdbRdbTableNm;
	}
	public void setWaRdbRdbTableNm(String waRdbRdbTableNm) {
		this.waRdbRdbTableNm = waRdbRdbTableNm;
	}
	public String getWaRdbRptAdminColumnNm() {
		return waRdbRptAdminColumnNm;
	}
	public void setWaRdbRptAdminColumnNm(String waRdbRptAdminColumnNm) {
		this.waRdbRptAdminColumnNm = waRdbRptAdminColumnNm;
	}
	public String getWaRdbRecStatusCd() {
		return waRdbRecStatusCd;
	}
	public void setWaRdbRecStatusCd(String waRdbRecStatusCd) {
		this.waRdbRecStatusCd = waRdbRecStatusCd;
	}
	public Timestamp getWaRdbRecStatusTime() {
		return waRdbRecStatusTime;
	}
	public void setWaRdbRecStatusTime(Timestamp waRdbRecStatusTime) {
		this.waRdbRecStatusTime = waRdbRecStatusTime;
	}
	public Timestamp getWaRdbAddTime() {
		return waRdbAddTime;
	}
	public void setWaRdbAddTime(Timestamp waRdbAddTime) {
		this.waRdbAddTime = waRdbAddTime;
	}
	public Long getWaRdbAddUserId() {
		return waRdbAddUserId;
	}
	public void setWaRdbAddUserId(Long waRdbAddUserId) {
		this.waRdbAddUserId = waRdbAddUserId;
	}
	public Timestamp getWaRdbLastChgTime() {
		return waRdbLastChgTime;
	}
	public void setWaRdbLastChgTime(Timestamp waRdbLastChgTime) {
		this.waRdbLastChgTime = waRdbLastChgTime;
	}
	public Long getWaRdbLastChgUserId() {
		return waRdbLastChgUserId;
	}
	public void setWaRdbLastChgUserId(Long waRdbLastChgUserId) {
		this.waRdbLastChgUserId = waRdbLastChgUserId;
	}
	public Integer getWaRdbVersionCtrlNbr() {
		return waRdbVersionCtrlNbr;
	}
	public void setWaRdbVersionCtrlNbr(Integer waRdbVersionCtrlNbr) {
		this.waRdbVersionCtrlNbr = waRdbVersionCtrlNbr;
	}
	public Long getWaNndWaNndMetadataUid() {
		return waNndWaNndMetadataUid;
	}
	public void setWaNndWaNndMetadataUid(Long waNndWaNndMetadataUid) {
		this.waNndWaNndMetadataUid = waNndWaNndMetadataUid;
	}

	public Long getWaNndWaTemplateUid() {
		return waNndWaTemplateUid;
	}
	public void setWaNndWaTemplateUid(Long waNndWaTemplateUid) {
		this.waNndWaTemplateUid = waNndWaTemplateUid;
	}
	public String getWaNndQuestionIdentifierNnd() {
		return waNndQuestionIdentifierNnd;
	}
	public void setWaNndQuestionIdentifierNnd(String waNndQuestionIdentifierNnd) {
		this.waNndQuestionIdentifierNnd = waNndQuestionIdentifierNnd;
	}
	public String getWaNndQuestionLabelNnd() {
		return waNndQuestionLabelNnd;
	}
	public void setWaNndQuestionLabelNnd(String waNndQuestionLabelNnd) {
		this.waNndQuestionLabelNnd = waNndQuestionLabelNnd;
	}
	public String getWaNndQuestionRequiredNnd() {
		return waNndQuestionRequiredNnd;
	}
	public void setWaNndQuestionRequiredNnd(String waNndQuestionRequiredNnd) {
		this.waNndQuestionRequiredNnd = waNndQuestionRequiredNnd;
	}
	public String getWaNndQuestionDataTypeNnd() {
		return waNndQuestionDataTypeNnd;
	}
	public void setWaNndQuestionDataTypeNnd(String waNndQuestionDataTypeNnd) {
		this.waNndQuestionDataTypeNnd = waNndQuestionDataTypeNnd;
	}
	public String getWaNndHl7SegmentField() {
		return waNndHl7SegmentField;
	}
	public void setWaNndHl7SegmentField(String waNndHl7SegmentField) {
		this.waNndHl7SegmentField = waNndHl7SegmentField;
	}
	public String getWaNndOrderGroupId() {
		return waNndOrderGroupId;
	}
	public void setWaNndOrderGroupId(String waNndOrderGroupId) {
		this.waNndOrderGroupId = waNndOrderGroupId;
	}
	public String getWaNndTranslationTableNm() {
		return waNndTranslationTableNm;
	}
	public void setWaNndTranslationTableNm(String waNndTranslationTableNm) {
		this.waNndTranslationTableNm = waNndTranslationTableNm;
	}
	public Timestamp getWaNndAddTime() {
		return waNndAddTime;
	}
	public void setWaNndAddTime(Timestamp waNndAddTime) {
		this.waNndAddTime = waNndAddTime;
	}
	public Long getWaNndAddUserId() {
		return waNndAddUserId;
	}
	public void setWaNndAddUserId(Long waNndAddUserId) {
		this.waNndAddUserId = waNndAddUserId;
	}
	public Timestamp getWaNndLastChgTime() {
		return waNndLastChgTime;
	}
	public void setWaNndLastChgTime(Timestamp waNndLastChgTime) {
		this.waNndLastChgTime = waNndLastChgTime;
	}
	public Long getWaNndLastChgUserId() {
		return waNndLastChgUserId;
	}
	public void setWaNndLastChgUserId(Long waNndLastChgUserId) {
		this.waNndLastChgUserId = waNndLastChgUserId;
	}
	public String getWaNndRecStatusCd() {
		return waNndRecStatusCd;
	}
	public void setWaNndRecStatusCd(String waNndRecStatusCd) {
		this.waNndRecStatusCd = waNndRecStatusCd;
	}
	public Timestamp getWaNndRecStatusTime() {
		return waNndRecStatusTime;
	}
	public void setWaNndRecStatusTime(Timestamp waNndRecStatusTime) {
		this.waNndRecStatusTime = waNndRecStatusTime;
	}
	public String getWaNndQuestionIdentifier() {
		return waNndQuestionIdentifier;
	}
	public void setWaNndQuestionIdentifier(String waNndQuestionIdentifier) {
		this.waNndQuestionIdentifier = waNndQuestionIdentifier;
	}
	public String getWaNndMsgTriggerIndCd() {
		return waNndMsgTriggerIndCd;
	}
	public void setWaNndMsgTriggerIndCd(String waNndMsgTriggerIndCd) {
		this.waNndMsgTriggerIndCd = waNndMsgTriggerIndCd;
	}
	public String getWaNndXmlPath() {
		return waNndXmlPath;
	}
	public void setWaNndXmlPath(String waNndXmlPath) {
		this.waNndXmlPath = waNndXmlPath;
	}
	public String getWaNndXmlTag() {
		return waNndXmlTag;
	}
	public void setWaNndXmlTag(String waNndXmlTag) {
		this.waNndXmlTag = waNndXmlTag;
	}
	public String getWaNndXmlDataType() {
		return waNndXmlDataType;
	}
	public void setWaNndXmlDataType(String waNndXmlDataType) {
		this.waNndXmlDataType = waNndXmlDataType;
	}
	public String getWaNndPartTypeCd() {
		return waNndPartTypeCd;
	}
	public void setWaNndPartTypeCd(String waNndPartTypeCd) {
		this.waNndPartTypeCd = waNndPartTypeCd;
	}
	public Integer getWaNndRepeatGroupSeqNbr() {
		return waNndRepeatGroupSeqNbr;
	}
	public void setWaNndRepeatGroupSeqNbr(Integer waNndRepeatGroupSeqNbr) {
		this.waNndRepeatGroupSeqNbr = waNndRepeatGroupSeqNbr;
	}
	public Integer getWaNndQuestionOrderNnd() {
		return waNndQuestionOrderNnd;
	}
	public void setWaNndQuestionOrderNnd(Integer waNndQuestionOrderNnd) {
		this.waNndQuestionOrderNnd = waNndQuestionOrderNnd;
	}
	public String getWaNndStatusCd() {
		return waNndStatusCd;
	}
	public void setWaNndStatusCd(String waNndStatusCd) {
		this.waNndStatusCd = waNndStatusCd;
	}
	public Timestamp getWaNndStatusTime() {
		return waNndStatusTime;
	}
	public void setWaNndStatusTime(Timestamp waNndStatusTime) {
		this.waNndStatusTime = waNndStatusTime;
	}
	public String getWaNndQuestionMap() {
		return waNndQuestionMap;
	}
	public void setWaNndQuestionMap(String waNndQuestionMap) {
		this.waNndQuestionMap = waNndQuestionMap;
	}
	public String getWaNndIndicatorCd() {
		return waNndIndicatorCd;
	}
	public void setWaNndIndicatorCd(String waNndIndicatorCd) {
		this.waNndIndicatorCd = waNndIndicatorCd;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
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
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public String getWaRdbLocalId() {
		return waRdbLocalId;
	}
	public void setWaRdbLocalId(String waRdbLocalId) {
		this.waRdbLocalId = waRdbLocalId;
	}
	public String getWaNndLocalId() {
		return waNndLocalId;
	}
	public void setWaNndLocalId(String waNndLocalId) {
		this.waNndLocalId = waNndLocalId;
	}
	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}
	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}
	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}
	public String getCodeSetName() {
		return codeSetName;
	}
	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}
	public String getWaRdbRdbColumnNm() {
		return waRdbRdbColumnNm;
	}
	public void setWaRdbRdbColumnNm(String waRdbRdbColumnNm) {
		this.waRdbRdbColumnNm = waRdbRdbColumnNm;
	}
	public String getWaRdbQuestionIdentifier() {
		return waRdbQuestionIdentifier;
	}
	public void setWaRdbQuestionIdentifier(String waRdbQuestionIdentifier) {
		this.waRdbQuestionIdentifier = waRdbQuestionIdentifier;
	}
	public Long getWaRdbWaUiMetadataUid() {
		return waRdbWaUiMetadataUid;
	}
	public void setWaRdbWaUiMetadataUid(Long waRdbWaUiMetadataUid) {
		this.waRdbWaUiMetadataUid = waRdbWaUiMetadataUid;
	}
	public Long getWaNndWaUiMetadataUid() {
		return waNndWaUiMetadataUid;
	}
	public void setWaNndWaUiMetadataUid(Long waNndWaUiMetadataUid) {
		this.waNndWaUiMetadataUid = waNndWaUiMetadataUid;
	}
	public String getStandardNndIndCd() {
		return standardNndIndCd;
	}
	public void setStandardNndIndCd(String standardNndIndCd) {
		this.standardNndIndCd = standardNndIndCd;
	}
	public String getQuestionUnitIdentifier() {
		return questionUnitIdentifier;
	}
	public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
		this.questionUnitIdentifier = questionUnitIdentifier;
	}
	public String getUnitParentIdentifier() {
		return unitParentIdentifier;
	}
	public void setUnitParentIdentifier(String unitParentIdentifier) {
		this.unitParentIdentifier = unitParentIdentifier;
	}
	public String getPartTypeCd() {
		return partTypeCd;
	}
	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}
	public String getDataCd() {
		return dataCd;
	}
	public void setDataCd(String dataCd) {
		this.dataCd = dataCd;
	}
	public String getLegacyDataLocation() {
		return legacyDataLocation;
	}
	public void setLegacyDataLocation(String legacyDataLocation) {
		this.legacyDataLocation = legacyDataLocation;
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
	public String getRepeatsIndCd() {
		return repeatsIndCd;
	}
	public void setRepeatsIndCd(String repeatsIndCd) {
		this.repeatsIndCd = repeatsIndCd;
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
	public String getStandardQuestionIndCd() {
		return standardQuestionIndCd;
	}
	public void setStandardQuestionIndCd(String standardQuestionIndCd) {
		this.standardQuestionIndCd = standardQuestionIndCd;
	}
	public String getQuestionNm() {
		return questionNm;
	}
	public void setQuestionNm(String questionNm) {
		this.questionNm = questionNm;
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
	public String getOtherValIndCd() {
		return otherValIndCd;
	}
	public void setOtherValIndCd(String otherValIndCd) {
		this.otherValIndCd = otherValIndCd;
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
	public String getPublishIndCd() {
		return publishIndCd;
	}
	public void setPublishIndCd(String publishIndCd) {
		this.publishIndCd = publishIndCd;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = WaAggregatePageElementDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public String getWaUserDefinedColumnNm() {
		return waUserDefinedColumnNm;
	}
	public void setWaUserDefinedColumnNm(String waUserDefinedColumnNm) {
		this.waUserDefinedColumnNm = waUserDefinedColumnNm;
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
	public String getWaUiMetadataBlockName() {
		return waUiMetadataBlockName;
	}
	public void setWaUiMetadataBlockName(String waUiMetadataBlockName) {
		this.waUiMetadataBlockName = waUiMetadataBlockName;
	}

	public Integer getWaRdbMetadataDataMartRepeatNbr() {
		return waRdbMetadataDataMartRepeatNbr;
	}
	public void setWaRdbMetadataDataMartRepeatNbr(
			Integer waRdbMetadataDataMartRepeatNbr) {
		this.waRdbMetadataDataMartRepeatNbr = waRdbMetadataDataMartRepeatNbr;
	}
	

	
	
	
}
