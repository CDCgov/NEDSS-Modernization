package gov.cdc.nedss.localfields.dt;

import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * NndMetadataDT is the parent Table to store NND Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NndMetadataDT.java
 * @version
 */
public class NndMetadataDT extends AbstractVO  implements RootDTInterface {
	private Long nndMetadataUid;
	private String investigationFormCd;
	private String questionIdentifierNnd;
	private String questionLabelNnd;
	private String questionRequiredNnd;
	private String questionDataTypeNnd;
	private String HL7SegmentField;
	private String orderGroupId;
	private String translationTableNm;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String questionIdentifier;
	private String msgTriggerIndCd;
	private String xmlPath;
	private String xmlTag;
	private String xmlDataType;
	private String partTypeCd;
	private Integer repeatGroupSeqNbr;
	private Integer questionOrderNnd;
	private Long nbsPageUid;
	private Long nbsUiMetadataUid;
	private String questionMap;
	private String indicatorCd;
	private static final long serialVersionUID = 1L;
	
	public NndMetadataDT() {}
	
	public NndMetadataDT(WaNndMetadataDT waNndMetadataDT) {
		setQuestionIdentifierNnd(waNndMetadataDT.getQuestionIdentifierNnd());
		setQuestionLabelNnd(waNndMetadataDT.getQuestionLabelNnd());
		setQuestionRequiredNnd(waNndMetadataDT.getQuestionRequiredNnd());
		setQuestionDataTypeNnd(waNndMetadataDT.getQuestionDataTypeNnd());
		setHL7SegmentField(waNndMetadataDT.getHl7SegmentField());
		setOrderGroupId(waNndMetadataDT.getOrderGroupId());
		setTranslationTableNm(waNndMetadataDT.getTranslationTableNm());
		setQuestionIdentifier(waNndMetadataDT.getQuestionIdentifier());
		setMsgTriggerIndCd(waNndMetadataDT.getMsgTriggerIndCd());
		setXmlPath(waNndMetadataDT.getXmlPath());
		setXmlTag(waNndMetadataDT.getXmlTag());
		setXmlDataType(waNndMetadataDT.getXmlDataType());
		setPartTypeCd(waNndMetadataDT.getPartTypeCd());
		setRepeatGroupSeqNbr(waNndMetadataDT.getRepeatGroupSeqNbr());
		setQuestionOrderNnd(waNndMetadataDT.getQuestionOrderNnd());
	}
	
	public Long getNndMetadataUid() {
		return nndMetadataUid;
	}
	public void setNndMetadataUid(Long nndMetadataUid) {
		this.nndMetadataUid = nndMetadataUid;
	}
	public String getInvestigationFormCd() {
		return investigationFormCd;
	}
	public void setInvestigationFormCd(String investigationFormCd) {
		this.investigationFormCd = investigationFormCd;
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
	public String getQuestionRequiredNnd() {
		return questionRequiredNnd;
	}
	public void setQuestionRequiredNnd(String questionRequiredNnd) {
		this.questionRequiredNnd = questionRequiredNnd;
	}
	public String getQuestionDataTypeNnd() {
		return questionDataTypeNnd;
	}
	public void setQuestionDataTypeNnd(String questionDataTypeNnd) {
		this.questionDataTypeNnd = questionDataTypeNnd;
	}
	public String getHL7SegmentField() {
		return HL7SegmentField;
	}
	public void setHL7SegmentField(String segmentField) {
		HL7SegmentField = segmentField;
	}
	public String getOrderGroupId() {
		return orderGroupId;
	}
	public void setOrderGroupId(String orderGroupId) {
		this.orderGroupId = orderGroupId;
	}
	public String getTranslationTableNm() {
		return translationTableNm;
	}
	public void setTranslationTableNm(String translationTableNm) {
		this.translationTableNm = translationTableNm;
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
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}
	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}
	public String getMsgTriggerIndCd() {
		return msgTriggerIndCd;
	}
	public void setMsgTriggerIndCd(String msgTriggerIndCd) {
		this.msgTriggerIndCd = msgTriggerIndCd;
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public String getXmlTag() {
		return xmlTag;
	}
	public void setXmlTag(String xmlTag) {
		this.xmlTag = xmlTag;
	}
	public String getXmlDataType() {
		return xmlDataType;
	}
	public void setXmlDataType(String xmlDataType) {
		this.xmlDataType = xmlDataType;
	}
	public String getPartTypeCd() {
		return partTypeCd;
	}
	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}
	public Integer getRepeatGroupSeqNbr() {
		return repeatGroupSeqNbr;
	}
	public void setRepeatGroupSeqNbr(Integer repeatGroupSeqNbr) {
		this.repeatGroupSeqNbr = repeatGroupSeqNbr;
	}
	public Integer getQuestionOrderNnd() {
		return questionOrderNnd;
	}
	public void setQuestionOrderNnd(Integer questionOrderNnd) {
		this.questionOrderNnd = questionOrderNnd;
	}
	public Long getNbsPageUid() {
		return nbsPageUid;
	}
	public void setNbsPageUid(Long nbsPageUid) {
		this.nbsPageUid = nbsPageUid;
	}
	public Long getNbsUiMetadataUid() {
		return nbsUiMetadataUid;
	}
	public void setNbsUiMetadataUid(Long nbsUiMetadataUid) {
		this.nbsUiMetadataUid = nbsUiMetadataUid;
	}
	
	public String getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(String questionMap) {
		this.questionMap = questionMap;
	}

	public String getIndicatorCd() {
		return indicatorCd;
	}

	public void setIndicatorCd(String indicatorCd) {
		this.indicatorCd = indicatorCd;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
}
