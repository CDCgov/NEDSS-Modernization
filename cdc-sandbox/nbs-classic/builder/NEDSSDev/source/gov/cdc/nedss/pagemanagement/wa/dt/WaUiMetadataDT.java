package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.lang.reflect.Field;
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
public class WaUiMetadataDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	
	/* General */
	private Long waUiMetadataUid;
	private Long waTemplateUid;
	private Long nbsUiComponentUid;
	private Long waQuestionUid;
	private Long parentUid;
	private String localId;
	private String publishIndCd;
	private Timestamp addTime;
    private Long addUserId;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private Integer versionCtrlNbr;
    private String futureDateIndCd;
    private Long codeSetGroupId;
    private String codeSetName;
    private String dataCd;
    private String dataLocation;
    private String dataUseCd;
    private String descTxt;
    private String standardQuestionIndCd;
	
	/* Question - Basic Info */
    private String questionType;
    private String questionTypeDesc;
    private String entryMethod;
    private String defaultValue;
	private String dataType;
    private String valSet;
    private String valSetDesc;
    private String mask;
    private String fieldLength;
    private Timestamp earliestDateAllowed;
    private Timestamp latestDateAllowed;
    private Long minValue;
    private Long maxValue;
    private String participationTypeDesc;
    private String relatedUnitLabel;
    private String questionIdentifier;
    private String questionNm;
    private Integer questionGroupSeqNbr;
    private String groupNm;
    private String subGroupNm;
    private String relatedUnitInd;
    private String questionUnitIdentifier;
    private String unitParentIndentifier;

	/* Question - User Interface */
	private String questionLabel;
	private String questionToolTip;
	private String enableInd;
	private String displayInd;
	private String requiredInd;
	private String defaultDisplayControl;
	
	private String isSecured;
	private String isRequired;
	private Long maxLength;
	private String cssStyle;
    private Integer tabOrderId;
    private String tabName;
    private Integer orderNbr;
    private String orderGrpId;
	
	/* Question - Data Mart */
    private String isIncludeInDatamart;
    private String reportLabel;
    private String rdbTableNm;
    private String rdbcolumnNm;
    private String userDefinedColumnNm;
    private String blockName;
    private Integer dataMartRepeatNumber;
    
    
	/* Question - Messaging/NND */
    private String nndMsgInd;
    private String questionLabelNnd;
    private String isRequiredInMessage;
    private String questionIdentifierNnd;
    private String hl7Segment;
    private String questionDataTypeNnd;
    private String questionReqNnd;
    private String standardNndIndCd;
    private String partTypeCd;
	
	/* Administrative Comments */
	private String admComment;
	
	/* Uncategorized */
	private String legacyDataLocation;
	private String questionOid;
	private String questionOidSystemTxt;
	private String questionOidCode; // Key to set in dropdown while editing question on page.
	
	private String repeatsIndCd;
	
    private String unitTypeCd;
    private String unitValue;
    private String otherValIndCd;
    
    private String unitValueCodeSetNm;
    
    /* Batch entry attributes */
    private String batchTableAppearIndCd;
    private String batchTableHeader;
    private Integer batchTableColumnWidth ;
    private String questionWithQuestionIdentifier;
    private String batchTableAppearIndCdForJsp;
    private String coinfectionIndCd;
    
    public String getBatchTableAppearIndCdForJsp() {
		return batchTableAppearIndCdForJsp;
	}
	public void setBatchTableAppearIndCdForJsp(String batchTableAppearIndCdForJsp) {
		this.batchTableAppearIndCdForJsp = batchTableAppearIndCdForJsp;
	}
	public String getQuestionWithQuestionIdentifier() {
		return questionWithQuestionIdentifier;
	}
	public void setQuestionWithQuestionIdentifier(
			String questionWithQuestionIdentifier) {
		this.questionWithQuestionIdentifier = questionWithQuestionIdentifier;
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
	
    public Long getNbsUiComponentUid() {
		return nbsUiComponentUid;
	}
	public void setNbsUiComponentUid(Long nbsUiComponentUid) {
		this.nbsUiComponentUid = nbsUiComponentUid;
	}
	
	public Long getParentUid() {
		return parentUid;
	}
	public void setParentUid(Long parentUid) {
		this.parentUid = parentUid;
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
	public String getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValSet() {
		return valSet;
	}
	public void setValSet(String valSet) {
		this.valSet = valSet;
	}
	public String getDefaultDisplayControl() {
		return defaultDisplayControl;
	}
	public void setDefaultDisplayControl(String defaultDisplayControl) {
		this.defaultDisplayControl = defaultDisplayControl;
	}
	public String getNndMsgInd() {
		return nndMsgInd;
	}
	public void setNndMsgInd(String nndMsgInd) {
		this.nndMsgInd = nndMsgInd;
	}

	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	public Timestamp getEarliestDateAllowed() {
		return earliestDateAllowed;
	}
	public void setEarliestDateAllowed(Timestamp earliestDateAllowed) {
		this.earliestDateAllowed = earliestDateAllowed;
	}
	public Timestamp getLatestDateAllowed() {
		return latestDateAllowed;
	}
	public void setLatestDateAllowed(Timestamp latestDateAllowed) {
		this.latestDateAllowed = latestDateAllowed;
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
	public String getParticipationTypeDesc() {
		return participationTypeDesc;
	}
	public void setParticipationTypeDesc(String participationTypeDesc) {
		this.participationTypeDesc = participationTypeDesc;
	}
	public String getIsSecured() {
		return isSecured;
	}
	public void setIsSecured(String isSecured) {
		this.isSecured = isSecured;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getIsRequiredInMessage() {
		return isRequiredInMessage;
	}
	public void setIsRequiredInMessage(String isRequiredInMessage) {
		this.isRequiredInMessage = isRequiredInMessage;
	}
	public String getIsIncludeInDatamart() {
		return isIncludeInDatamart;
	}
	public void setIsIncludeInDatamart(String isIncludeInDatamart) {
		this.isIncludeInDatamart = isIncludeInDatamart;
	}
	public String getReportLabel() {
		return reportLabel;
	}
	public void setReportLabel(String reportLabel) {
		this.reportLabel = reportLabel;
	}
	public String getRelatedUnitLabel() {
		return relatedUnitLabel;
	}
	public void setRelatedUnitLabel(String relatedUnitLabel) {
		this.relatedUnitLabel = relatedUnitLabel;
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

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public void setLastChgReasonCd(String lastChgReasonCd) {
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
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public String getAdminComment() {
		return admComment;
	}
	public void setAdminComment(String adminComment) {
		this.admComment = adminComment;
	}
	public Integer getTabOrderId() {
		return tabOrderId;
	}
	public void setTabOrderId(Integer tabOrderId) {
		this.tabOrderId = tabOrderId;
	}
	public Long getWaUiMetadataUid() {
		return waUiMetadataUid;
	}
	public void setWaUiMetadataUid(Long waUiMetadataUid) {
		this.waUiMetadataUid = waUiMetadataUid;
	}
	public Long getWaTemplateUid() {
		return waTemplateUid;
	}
	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}
	public Long getWaQuestionUid() {
		return waQuestionUid;
	}
	public void setWaQuestionUid(Long waQuestionUid) {
		this.waQuestionUid = waQuestionUid;
	}
	public String getFutureDateIndCd() {
		return futureDateIndCd;
	}
	public void setFutureDateIndCd(String futureDateIndCd) {
		this.futureDateIndCd = futureDateIndCd;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
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
	public String getQuestionNm() {
        return questionNm;
    }
    public void setQuestionNm(String questionNm) {
        this.questionNm = questionNm;
    }
    public String getCodeSetName() {
		return codeSetName;
	}
	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}
  
    public String getHl7Segment() {
        return hl7Segment;
    }
    public void setHl7Segment(String hl7Segment) {
        this.hl7Segment = hl7Segment;
    }
    public Integer getQuestionGroupSeqNbr() {
        return questionGroupSeqNbr;
    }
    public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
        this.questionGroupSeqNbr = questionGroupSeqNbr;
    }
	public void setGroupNm(String groupNm) {
		this.groupNm = groupNm;
	}
	public String getGroupNm() {
		return groupNm;
	}
	public void setSubGroupNm(String subGroupNm) {
		this.subGroupNm = subGroupNm;
	}
	public String getQuestionLabelNnd() {
        return questionLabelNnd;
    }
    public void setQuestionLabelNnd(String questionLabelNnd) {
        this.questionLabelNnd = questionLabelNnd;
    }
    public String getQuestionIdentifierNnd() {
        return questionIdentifierNnd;
    }
    public void setQuestionIdentifierNnd(String questionIdentifierNnd) {
        this.questionIdentifierNnd = questionIdentifierNnd;
    }
    public String getRdbTableNm() {
        return rdbTableNm;
    }
    public void setRdbTableNm(String rdbTableNm) {
        this.rdbTableNm = rdbTableNm;
    }
    public String getRelatedUnitInd() {
        return relatedUnitInd;
    }
    public void setRelatedUnitInd(String relatedUnitInd) {
        this.relatedUnitInd = relatedUnitInd;
    }
    public String getQuestionTypeDesc() {
        return questionTypeDesc;
    }
    public void setQuestionTypeDesc(String questionTypeDesc) {
        this.questionTypeDesc = questionTypeDesc;
    }
    public String getValSetDesc() {
        return valSetDesc;
    }
    public void setValSetDesc(String valSetDesc) {
        this.valSetDesc = valSetDesc;
    }
    public String getQuestionDataTypeNnd() {
        return questionDataTypeNnd;
    }
    public void setQuestionDataTypeNnd(String questionDataTypeNnd) {
        this.questionDataTypeNnd = questionDataTypeNnd;
    }
    public String getQuestionReqNnd() {
        return questionReqNnd;
    }
    public void setQuestionReqNnd(String questionReqNnd) {
        this.questionReqNnd = questionReqNnd;
    }
    public String getSubGroupNm() {
		return subGroupNm;
	}
	public String getEntryMethod() {
		return entryMethod;
	}
	public void setEntryMethod(String entryMethod) {
		this.entryMethod = entryMethod;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
		
		// also set the question type description for this question type
	    CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
		this.questionTypeDesc = cachedDropDownValues.getDescForCode(NEDSSConstants.NBS_QUESTION_TYPE,
		        this.getQuestionType());
	}
	public String getPublishIndCd() {
		return publishIndCd;
	}
	public void setPublishIndCd(String publishIndCd) {
		this.publishIndCd = publishIndCd;
	}
    public String getOrderGrpId() {
        return orderGrpId;
    }
    public void setOrderGrpId(String orderGrpId) {
        this.orderGrpId = orderGrpId;
    }
	public String getRdbcolumnNm() {
		return rdbcolumnNm;
	}
	public void setRdbcolumnNm(String rdbcolumnNm) {
		this.rdbcolumnNm = rdbcolumnNm;
	}
	public String getQuestionUnitIdentifier() {
		return questionUnitIdentifier;
	}
	public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
		this.questionUnitIdentifier = questionUnitIdentifier;
	}
	public String getUnitParentIndentifier() {
		return unitParentIndentifier;
	}
	public void setUnitParentIndentifier(String unitParentIndentifier) {
		this.unitParentIndentifier = unitParentIndentifier;
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
	public String getStandardNndIndCd() {
		return standardNndIndCd;
	}
	public void setStandardNndIndCd(String standardNndIndCd) {
		this.standardNndIndCd = standardNndIndCd;
	}
	public String getPartTypeCd() {
		return partTypeCd;
	}
	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}
	public String getLegacyDataLocation() {
		return legacyDataLocation;
	}
	public void setLegacyDataLocation(String legacyDataLocation) {
		this.legacyDataLocation = legacyDataLocation;
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
	public String getDescTxt() {
		return descTxt;
	}
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
	public String getRepeatsIndCd() {
		return repeatsIndCd;
	}
	public void setRepeatsIndCd(String repeatsIndCd) {
		this.repeatsIndCd = repeatsIndCd;
	}
	public String getStandardQuestionIndCd() {
		return standardQuestionIndCd;
	}
	public void setStandardQuestionIndCd(String standardQuestionIndCd) {
		this.standardQuestionIndCd = standardQuestionIndCd;
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
	public String getOtherValIndCd() {
		return otherValIndCd;
	}
	public void setOtherValIndCd(String otherValIndCd) {
		this.otherValIndCd = otherValIndCd;
	}
	public String getUnitValueCodeSetNm() {
		return unitValueCodeSetNm;
	}
	public void setUnitValueCodeSetNm(String unitValueCodeSetNm) {
		this.unitValueCodeSetNm = unitValueCodeSetNm;
	}
	public String getUserDefinedColumnNm() {
		return userDefinedColumnNm;
	}
	public void setUserDefinedColumnNm(String userDefinedColumnNm) {
		this.userDefinedColumnNm = userDefinedColumnNm;
	}
	
	public String getCoinfectionIndCd() {
		return coinfectionIndCd;
	}
	public void setCoinfectionIndCd(String coinfectionIndCd) {
		this.coinfectionIndCd = coinfectionIndCd;
	}
	
	
	public String getQuestionOidCode() {
		return questionOidCode;
	}
	public void setQuestionOidCode(String questionOidCode) {
		this.questionOidCode = questionOidCode;
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
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = WaQuestionDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

}