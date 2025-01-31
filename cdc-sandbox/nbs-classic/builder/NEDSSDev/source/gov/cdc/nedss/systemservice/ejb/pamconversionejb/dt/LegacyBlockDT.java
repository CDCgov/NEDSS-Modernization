package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt;

public class LegacyBlockDT {
private Long targetActUid;
private Long sourceActUid;
private Integer seqNbr;
private Integer groupSeqNbr;

public Integer getGroupSeqNbr() {
	return groupSeqNbr;
}
public void setGroupSeqNbr(Integer groupSeqNbr) {
	this.groupSeqNbr = groupSeqNbr;
}
public Long getTargetActUid() {
	return targetActUid;
}
public void setTargetActUid(Long targetActUid) {
	this.targetActUid = targetActUid;
}
public Long getSourceActUid() {
	return sourceActUid;
}
public void setSourceActUid(Long sourceActUid) {
	this.sourceActUid = sourceActUid;
}
public Integer getSeqNbr() {
	return seqNbr;
}
public void setSeqNbr(Integer seqNbr) {
	this.seqNbr = seqNbr;
}

	
}
