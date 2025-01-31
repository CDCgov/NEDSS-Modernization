package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class WaQuestionDT extends AbstractVO  implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	
	/* General */
	private Long waQuestionUid;
	private String questionOid;
	private String localId;
    private String questionOidSystemTxt;
    private Integer versionCtrlNbr;
    private Timestamp addTime;
    private Long addUserId; 
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String viewLink;
    private String editLink;
    
    /**
     * referencedInd will be set to 'T' if this question
     * is referenced in a page (in draft or publishes state) or
     * referenced in a template. It will be set to 'F' in all other cases.
     * This field can be used to determine what properties of the 
     * question are 'edit'-able at a given time.
     */
    private String referencedInd;
    
    /**
     * Represents whether this question is considered
     * 'standardized' or not. A question is marked
     * standard (i.e., the letter 'T') whenever the system 
     * does not want the user to change some of its properties.
     * A non standard question will have the value 'F'.
     */
    private String standardQuestionIndCd;
    
	
	/* Question - Basic Info */
    private String questionType;
    private String questionTypeDesc;
    private String entryMethod;
    private String dataType;
    private String questionIdentifier;
	private String questionUnitIdentifier;
	private String unitParentIndentifier;
	private String groupNm;
	private String groupDesc;
	private String subGroupNm;
	private String subGroupDesc;
	private String questionNm;
	private String description;
	private String mask;    
	private String fieldLength;
	private String dataTypeDesc;
	private String valSetDesc;
	private String maskDesc;
	private String relatedUnitInd;
	private String relatedUnitIndDesc;
	private String collectUnitIndDesc;
	private String participationTypeDesc;
	private Long minValue;
    private Long maxValue;
    private String futureDateIndCd;
	
	/* Question - User Interface */
	private String questionLabel;
    private String questionToolTip;
    private String defaultValue;
    private Long nbsUiComponentUid;
    private String defaultDisplayControlDesc;
	
	/* Question - Data Mart */
    private String reportAdmColumnNm;
    private String rdbTableNm;
    private String rdbcolumnNm;
    private String userDefinedColumnNm;
    private String blockName;
    private Integer dataMartRepeatNumber;

    
	
	/* Question - Messaging/NND */
    private String nndMsgInd;
    private String questionIdentifierNnd;   
    private String questionLabelNnd;
    private String questionReqNnd;
    private String questionReqNndDesc;
    private String questionDataTypeNnd;
    private String questionDataTypeNndDesc;
    private String nndMsgIndDesc;
    private String hl7SegmentDesc;
    private String hl7Segment;
    // added by jayasudha to diaplay the code system name.
    private String codeSysName;
    private String codeSysCd;
    /* Administrative Comments */
    private String admComment;
    
	/* Uncategorized */
	private Long codeSetGroupId;
	private String dataCd;
	private String dataLocation;
	private String dataUseCd;
	private String partTypeCd;
	private String questionGroupSeqNbr;
	private String legacyDataLocation;
	private String repeatsIndCd;
	private String orderGrpId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String statusDesc;
    private String standardNndIndCd;
    private String unitTypeCd;
    private String unitValue;
    private String otherValIndCd;
    private String unitTypeCdDesc;
    private String otherValIndCdDesc;
    private String unitValueDesc;
    private String coInfQuestion;
    //private String rdbTableNmHid;
    private String questionOidCode;
	
	

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
		this.itDirty = itDirty;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return itDirty;
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
		return itNew;
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
		return itDelete;
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

	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	public Long getWaQuestionUid() {
		return waQuestionUid;
	}

	public void setWaQuestionUid(Long waQuestionUid) {
		this.waQuestionUid = waQuestionUid;
	}

	public String getUnitParentIndentifier() {
		return unitParentIndentifier;
	}

	public void setUnitParentIndentifier(String unitParentIndentifier) {
		this.unitParentIndentifier = unitParentIndentifier;
	}

	public String getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(String questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}

	public String getFutureDateIndCd() {
		return futureDateIndCd;
	}

	public void setFutureDateIndCd(String futureDateIndCd) {
		this.futureDateIndCd = futureDateIndCd;
	}

	public String getLegacyDataLocation() {
		return legacyDataLocation;
	}

	public void setLegacyDataLocation(String legacyDataLocation) {
		this.legacyDataLocation = legacyDataLocation;
	}

	public String getRepeatsIndCd() {
		return repeatsIndCd;
	}

	public void setRepeatsIndCd(String repeatsIndCd) {
		this.repeatsIndCd = repeatsIndCd;
	}

	public String getQuestionNm() {
		return questionNm;
	}

	public void setQuestionNm(String questionNm) {
		this.questionNm = questionNm;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getNndMsgInd() {
		return nndMsgInd;
	}

	public void setNndMsgInd(String nndMsgInd) {
		this.nndMsgInd = nndMsgInd;
	}
	
	public String getHl7Segment() {
		return hl7Segment;
	}

	public void setHl7Segment(String hl7Segment) {
		this.hl7Segment = hl7Segment;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}
	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getSubGroupDesc() {
		return subGroupDesc;
	}

	public void setSubGroupDesc(String subGroupDesc) {
		this.subGroupDesc = subGroupDesc;
	}

	public String getDataTypeDesc() {
		return dataTypeDesc;
	}

	public void setDataTypeDesc(String dataTypeDesc) {
		this.dataTypeDesc = dataTypeDesc;
	}

	public String getValSetDesc() {
		return valSetDesc;
	}

	public void setValSetDesc(String valSetDesc) {
		this.valSetDesc = valSetDesc;
	}

	public String getMaskDesc() {
		return maskDesc;
	}

	public void setMaskDesc(String maskDesc) {
		this.maskDesc = maskDesc;
	}

	public String getRelatedUnitIndDesc() {
		return relatedUnitIndDesc;
	}

	public void setRelatedUnitIndDesc(String relatedUnitIndDesc) {
		this.relatedUnitIndDesc = relatedUnitIndDesc;
	}

	public String getCollectUnitIndDesc() {
		return collectUnitIndDesc;
	}

	public void setCollectUnitIndDesc(String collectUnitIndDesc) {
		this.collectUnitIndDesc = collectUnitIndDesc;
	}

	public String getDefaultDisplayControlDesc() {
		return defaultDisplayControlDesc;
	}

	public void setDefaultDisplayControlDesc(String defaultDisplayControlDesc) {
		this.defaultDisplayControlDesc = defaultDisplayControlDesc;
	}

	public String getNndMsgIndDesc() {
		return nndMsgIndDesc;
	}

	public void setNndMsgIndDesc(String nndMsgIndDesc) {
		this.nndMsgIndDesc = nndMsgIndDesc;
	}	

	public String getHl7SegmentDesc() {
		return hl7SegmentDesc;
	}

	public void setHl7SegmentDesc(String hl7SegmentDesc) {
		this.hl7SegmentDesc = hl7SegmentDesc;
	}
	
	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}

	public String getEditLink() {
		return editLink;
	}

	public void setEditLink(String editLink) {
		this.editLink = editLink;
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

	public String getParticipationTypeDesc() {
		return participationTypeDesc;
	}

	public void setParticipationTypeDesc(String participationTypeDesc) {
		this.participationTypeDesc = participationTypeDesc;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Long getNbsUiComponentUid() {
		return nbsUiComponentUid;
	}

	public void setNbsUiComponentUid(Long nbsUiComponentUid) {
		this.nbsUiComponentUid = nbsUiComponentUid;
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

	public String getEntryMethod() {
		return entryMethod;
	}

	public void setEntryMethod(String entryMethod) {
		this.entryMethod = entryMethod;
	}

	public String getReportAdminColumnNm() {
		return reportAdmColumnNm;
	}

	public void setReportAdminColumnNm(String reportAdminColumnNm) {
		this.reportAdmColumnNm = reportAdminColumnNm;
	}

	public String getRdbTableNm() {
		return rdbTableNm;
	}

	public void setRdbTableNm(String rdbTableNm) {
		this.rdbTableNm = rdbTableNm;
	}

	public String getOrderGrpId() {
		return orderGrpId;
	}

	public void setOrderGrpId(String orderGrpId) {
		this.orderGrpId = orderGrpId;
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

	public String getQuestionIdentifierNnd() {
		return questionIdentifierNnd;
	}

	public void setQuestionIdentifierNnd(String questionIdentifierNnd) {
		this.questionIdentifierNnd = questionIdentifierNnd;
	}

	public String getQuestionLabelNnd() {
		return questionLabelNnd;
	}

	public void setQuestionLabelNnd(String questionLabelNnd) {
		this.questionLabelNnd = questionLabelNnd;
	}

	public String getQuestionReqNnd() {
		return questionReqNnd;
	}

	public void setQuestionReqNnd(String questionReqNnd) {
		this.questionReqNnd = questionReqNnd;
	}

	public String getQuestionDataTypeNnd() {
		return questionDataTypeNnd;
	}

	public void setQuestionDataTypeNnd(String questionDataTypeNnd) {
		this.questionDataTypeNnd = questionDataTypeNnd;
	}

	public String getQuestionReqNndDesc() {
		return questionReqNndDesc;
	}

	public void setQuestionReqNndDesc(String questionReqNndDesc) {
		this.questionReqNndDesc = questionReqNndDesc;
	}

	public String getQuestionDataTypeNndDesc() {
		return questionDataTypeNndDesc;
	}

	public void setQuestionDataTypeNndDesc(String questionDataTypeNndDesc) {
		this.questionDataTypeNndDesc = questionDataTypeNndDesc;
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

	public String getAdminComment() {
		return admComment;
	}

	public void setAdminComment(String adminComment) {
		this.admComment = adminComment;
	}

	public String getRdbcolumnNm() {
		return rdbcolumnNm;
	}

	public void setRdbcolumnNm(String rdbcolumnNm) {
		this.rdbcolumnNm = rdbcolumnNm;
	}

	public String getStandardNndIndCd() {
		return standardNndIndCd;
	}

	public void setStandardNndIndCd(String standardNndIndCd) {
		this.standardNndIndCd = standardNndIndCd;
	}

    public String getReferencedInd() {
        return referencedInd;
    }

    public void setReferencedInd(String referencedInd) {
        this.referencedInd = referencedInd;
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
	public String getUnitTypeCdDesc() {
		return unitTypeCdDesc;
	}

	public void setUnitTypeCdDesc(String unitTypeCdDesc) {
		this.unitTypeCdDesc = unitTypeCdDesc;
	}

	public String getOtherValIndCdDesc() {
		return otherValIndCdDesc;
	}

	public void setOtherValIndCdDesc(String otherValIndCdDesc) {
		this.otherValIndCdDesc = otherValIndCdDesc;
	}
	public String getUnitValueDesc() {
		return unitValueDesc;
	}

	public void setUnitValueDesc(String unitValueDesc) {
		this.unitValueDesc = unitValueDesc;
	}

	public String getUserDefinedColumnNm() {
		return userDefinedColumnNm;
	}

	public void setUserDefinedColumnNm(String userDefinedColumnNm) {
		this.userDefinedColumnNm = userDefinedColumnNm;
	}

	public String getCoInfQuestion() {
		return coInfQuestion;
	}

	public void setCoInfQuestion(String coInfQuestion) {
		this.coInfQuestion = coInfQuestion;
	}

	public String getCodeSysName() {
		return codeSysName;
	}

	public void setCodeSysName(String codeSysName) {
		this.codeSysName = codeSysName;
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

	/*public String getRdbTableNmHid() {
		return rdbTableNmHid;
	}

	public void setRdbTableNmHid(String rdbTableNmHid) {
		this.rdbTableNmHid = rdbTableNmHid;
	}*/
	
	

}
