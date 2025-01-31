package  gov.cdc.nedss.pagemanagement.wa.dt;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class WaNndMetadataDT extends AbstractVO  implements RootDTInterface {


		private static final long serialVersionUID = 1L;
		private Long waNndMetadataUid;
		private Long waQuestionUid;
		private Long waTemplateUid;
		private String questionIdentifierNnd;
		private String questionLabelNnd;
		private String questionRequiredNnd;
		private String questionDataTypeNnd;
		private String hl7SegmentField;
		private String orderGroupId;
		private String translationTableNm;		
		private Timestamp addTime;
		private Long addUserId;
		private Timestamp lastChgTime;
		private Long lastChgUserId;
		private String recStatusCd;
		private Timestamp recStatusTime;
		private String questionIdentifier;
		private String msgTriggerIndCd;
		private String xmlPath;
		private String xmlTag;
		private String xmlDataType;
		private String partTypeCd;
		private Integer repeatGroupSeqNbr;
		private Integer questionOrderNnd;	
		private String localId;		
		private String statusCd;
		private Timestamp statusTime;
		private Long waUiMetadataUid;
		private String questionMap;
		private String indicatorCd;
		
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
		
		public Long getWaNndMetadataUid() {
			return waNndMetadataUid;
		}

		public void setWaNndMetadataUid(Long waNndMetadataUid) {
			this.waNndMetadataUid = waNndMetadataUid;
		}

		public Long getWaQuestionUid() {
			return waQuestionUid;
		}

		public void setWaQuestionUid(Long waQuestionUid) {
			this.waQuestionUid = waQuestionUid;
		}

		public Long getWaTemplateUid() {
			return waTemplateUid;
		}

		public void setWaTemplateUid(Long waTemplateUid) {
			this.waTemplateUid = waTemplateUid;
		}

		public String getQuestionLabelNnd() {
			return questionLabelNnd;
		}

		public void setQuestionLabelNnd(String questionLabelNnd) {
			this.questionLabelNnd = questionLabelNnd;
		}

		public String getQuestionDataTypeNnd() {
			return questionDataTypeNnd;
		}

		public void setQuestionDataTypeNnd(String questionDataTypeNnd) {
			this.questionDataTypeNnd = questionDataTypeNnd;
		}

		public String getHl7SegmentField() {
			return hl7SegmentField;
		}

		public void setHl7SegmentField(String hl7SegmentField) {
			this.hl7SegmentField = hl7SegmentField;
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

		public String getQuestionRequiredNnd() {
			return questionRequiredNnd;
		}

		public void setQuestionRequiredNnd(String questionRequiredNnd) {
			this.questionRequiredNnd = questionRequiredNnd;
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
			this.localId = localId;
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

		public void setSharedInd(String sharedInd) {
			// TODO Auto-generated method stub
			
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

		public Long getWaUiMetadataUid() {
			return waUiMetadataUid;
		}

		public void setWaUiMetadataUid(Long waUiMetadataUid) {
			this.waUiMetadataUid = waUiMetadataUid;
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
		
}
