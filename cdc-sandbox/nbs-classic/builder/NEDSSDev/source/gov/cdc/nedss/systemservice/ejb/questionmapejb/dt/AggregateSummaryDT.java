package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import gov.cdc.nedss.util.AbstractVO;

/**
 * AggregateSummaryDT is a representation of the NBS_INDICATOR, NBS_AGGREGATE, NBS_TABLE_METADATA, NBS_TABLE, NBS_QUESTION_METADATA
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * AggregateSummaryDT.java
 * Aug 6, 2009
 * @version
 */
public class AggregateSummaryDT extends AbstractVO {

	private static final long serialVersionUID = 1L;
	private Long nbsTableMetaDataUid;
	private Long nbsTableUid; 
	private String nbsTableName; 
	private String questionIdentifier; 
	private Long nbsIndicatorUid;
	private Long nbsAggregateUid; 
	private Long nbsQuestionUid;
	private String indicatorLabel; 
	private String indicatorToolTip; 
	private String aggregateLabel; 
	private String aggregateToolTip; 
	private Integer aggregateSeqNbr;
	private Integer indicatorSeqNbr;
	private String answerTxt;
	
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public Long getNbsTableMetaDataUid() {
		return nbsTableMetaDataUid;
	}
	public void setNbsTableMetaDataUid(Long nbsTableMetaDataUid) {
		this.nbsTableMetaDataUid = nbsTableMetaDataUid;
	}
	public String getNbsTableName() {
		return nbsTableName;
	}
	public void setNbsTableName(String nbsTableName) {
		this.nbsTableName = nbsTableName;
	}
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}
	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}
	public Long getNbsIndicatorUid() {
		return nbsIndicatorUid;
	}
	public void setNbsIndicatorUid(Long nbsIndicatorUid) {
		this.nbsIndicatorUid = nbsIndicatorUid;
	}
	public Long getNbsAggregateUid() {
		return nbsAggregateUid;
	}
	public void setNbsAggregateUid(Long nbsAggregateUid) {
		this.nbsAggregateUid = nbsAggregateUid;
	}
	public String getIndicatorLabel() {
		return indicatorLabel;
	}
	public void setIndicatorLabel(String indicatorLabel) {
		this.indicatorLabel = indicatorLabel;
	}
	public String getIndicatorToolTip() {
		return indicatorToolTip;
	}
	public void setIndicatorToolTip(String indicatorToolTip) {
		this.indicatorToolTip = indicatorToolTip;
	}
	public String getAggregateLabel() {
		return aggregateLabel;
	}
	public void setAggregateLabel(String aggregateLabel) {
		this.aggregateLabel = aggregateLabel;
	}
	public String getAggregateToolTip() {
		return aggregateToolTip;
	}
	public void setAggregateToolTip(String aggregateToolTip) {
		this.aggregateToolTip = aggregateToolTip;
	}
	public Long getNbsTableUid() {
		return nbsTableUid;
	}
	public void setNbsTableUid(Long nbsTableUid) {
		this.nbsTableUid = nbsTableUid;
	}
	public String getAnswerTxt() {
		return answerTxt;
	}
	public void setAnswerTxt(String answerTxt) {
		this.answerTxt = answerTxt;
	}
	public Integer getAggregateSeqNbr() {
		return aggregateSeqNbr;
	}
	public void setAggregateSeqNbr(Integer aggregateSeqNbr) {
		this.aggregateSeqNbr = aggregateSeqNbr;
	}
	public Integer getIndicatorSeqNbr() {
		return indicatorSeqNbr;
	}
	public void setIndicatorSeqNbr(Integer indicatorSeqNbr) {
		this.indicatorSeqNbr = indicatorSeqNbr;
	}
	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}
	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}
	
}