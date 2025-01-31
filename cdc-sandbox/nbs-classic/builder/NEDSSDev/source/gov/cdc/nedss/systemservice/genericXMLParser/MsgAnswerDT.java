package gov.cdc.nedss.systemservice.genericXMLParser;

import java.sql.Blob;

public class MsgAnswerDT {
	
	private Long msgContainerUid;
	private String msgEventId;
	private String msgEventType;
	private String ansCodeSystemCode;
	private String ansCodeSystemDescTxt;
	private String ansDisplayTxt;
	private String answerTxt;
	private Blob answerLargeTxt;
	private Integer answerGroupSeqNbr;
	private String answerXmlTxt;
	private String partTypeCd;
	private String questionIdenitifer;
	private String quesCodeSystemCd;
	private String quesCodeSystemDescTxt;
	private String quesDisplayTxt;
	private Integer questionGroupSeqNbr;
	private String sectionNm;
	private Integer seqNbr;
	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}
	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}

	public String getMsgEventId() {
		return msgEventId;
	}
	public void setMsgEventId(String msgEventId) {
		this.msgEventId = msgEventId;
	}
	public String getMsgEventType() {
		return msgEventType;
	}
	public void setMsgEventType(String msgEventType) {
		this.msgEventType = msgEventType;
	}
	public String getAnsCodeSystemCode() {
		return ansCodeSystemCode;
	}
	public void setAnsCodeSystemCode(String ansCodeSystemCode) {
		this.ansCodeSystemCode = ansCodeSystemCode;
	}
	public String getAnsCodeSystemDescTxt() {
		return ansCodeSystemDescTxt;
	}
	public void setAnsCodeSystemDescTxt(String ansCodeSystemDescTxt) {
		this.ansCodeSystemDescTxt = ansCodeSystemDescTxt;
	}
	public String getAnsDisplayTxt() {
		return ansDisplayTxt;
	}
	public void setAnsDisplayTxt(String ansDisplayTxt) {
		this.ansDisplayTxt = ansDisplayTxt;
	}
	public String getAnswerTxt() {
		return answerTxt;
	}
	public void setAnswerTxt(String answerTxt) {
		this.answerTxt = answerTxt;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}
	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}
	public String getQuestionIdenitifer() {
		return questionIdenitifer;
	}
	public void setQuestionIdenitifer(String questionIdenitifer) {
		this.questionIdenitifer = questionIdenitifer;
	}
	public String getQuesCodeSystemCd() {
		return quesCodeSystemCd;
	}
	public void setQuesCodeSystemCd(String quesCodeSystemCd) {
		this.quesCodeSystemCd = quesCodeSystemCd;
	}
	public String getQuesCodeSystemDescTxt() {
		return quesCodeSystemDescTxt;
	}
	public void setQuesCodeSystemDescTxt(String quesCodeSystemDescTxt) {
		this.quesCodeSystemDescTxt = quesCodeSystemDescTxt;
	}
	public String getQuesDisplayTxt() {
		return quesDisplayTxt;
	}
	public void setQuesDisplayTxt(String quesDisplayTxt) {
		this.quesDisplayTxt = quesDisplayTxt;
	}
	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}
	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}
	public Integer getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}
	public Long getMsgContainerUid() {
		return msgContainerUid;
	}
	public void setMsgContainerUid(Long msgContainerUid) {
		this.msgContainerUid = msgContainerUid;
	}
	public String getAnswerXmlTxt() {
		return answerXmlTxt;
	}
	public void setAnswerXmlTxt(String answerXmlTxt) {
		this.answerXmlTxt = answerXmlTxt;
	}
	public String getSectionNm() {
		return sectionNm;
	}
	public void setSectionNm(String sectionNm) {
		this.sectionNm = sectionNm;
	}
	public Blob getAnswerLargeTxt() {
		return answerLargeTxt;
	}
	public void setAnswerLargeTxt(Blob answerLargeTxt) {
		this.answerLargeTxt = answerLargeTxt;
	}

}
