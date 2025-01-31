package gov.cdc.nedss.page.ejb.pageproxyejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.Timestamp;
/**
* Name:		AnswerDT.java
* Description:	DT for NBS CASE answers.
* Copyright:	Copyright (c) 2013
* Company: 	Leidos
*/
public class NbsAnswerDT extends AbstractVO
implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private Long nbsAnswerUid;
	private Integer seqNbr;
	private String answerTxt;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Long nbsQuestionUid;
	private Integer nbsQuestionVersionCtrlNbr;
	private String recordStatusCd;  
	private Timestamp recordStatusTime;
	private Long actUid;
	private Clob answerLargeTxt;
	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;
	private Integer answerGroupSeqNbr;
	private Timestamp addTime;
	private Long addUserId;
	
	public NbsAnswerDT(){
		
	}
	public NbsAnswerDT(NbsAnswerDT nbsAnswerDT) {
		nbsAnswerUid = nbsAnswerDT.nbsAnswerUid;
		seqNbr = nbsAnswerDT.seqNbr;
		answerTxt = nbsAnswerDT.answerTxt;
		lastChgTime = nbsAnswerDT.lastChgTime;
		lastChgUserId = nbsAnswerDT.lastChgUserId;
		nbsQuestionUid = nbsAnswerDT.nbsQuestionUid;
		nbsQuestionVersionCtrlNbr = nbsAnswerDT.nbsQuestionVersionCtrlNbr;
		recordStatusCd = nbsAnswerDT.recordStatusCd;
		recordStatusTime = nbsAnswerDT.recordStatusTime;
		actUid = nbsAnswerDT.actUid;
		answerLargeTxt = nbsAnswerDT.answerLargeTxt;
		itDirty = nbsAnswerDT.itDirty;
		itNew = nbsAnswerDT.itNew;
		itDelete = nbsAnswerDT.itDelete;
		answerGroupSeqNbr = nbsAnswerDT.answerGroupSeqNbr;
		addTime = nbsAnswerDT.addTime;
		addUserId = nbsAnswerDT.addUserId;
	}

	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}
	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}
	public Clob getAnswerLargeTxt() {
		return answerLargeTxt;
	}
	public void setAnswerLargeTxt(Clob answerLargeTxt) {
		this.answerLargeTxt = answerLargeTxt;
	}
	public String getAnswerTxt() {
		return answerTxt;
	}
	public void setAnswerTxt(String answerTxt) {
		this.answerTxt = answerTxt;
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
	public Integer getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}

//RootDTInterface methods that are not implemented in new code
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub

	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    public boolean isItDirty() {

        return itDirty;
    }

    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    public boolean isItNew() {

        return itNew;
    }

    public boolean isItDelete() {

        return itDelete;
    }

    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsAnswerDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}
	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}
	public Long getNbsAnswerUid() {
		return nbsAnswerUid;
	}
	public void setNbsAnswerUid(Long nbsAnswerUid) {
		this.nbsAnswerUid = nbsAnswerUid;
	}
	public Integer getNbsQuestionVersionCtrlNbr() {
		return nbsQuestionVersionCtrlNbr;
	}
	public void setNbsQuestionVersionCtrlNbr(Integer nbsQuestionVersionCtrlNbr) {
		this.nbsQuestionVersionCtrlNbr = nbsQuestionVersionCtrlNbr;
	}
	public Long getActUid() {
		return actUid;
	}
	public void setActUid(Long actUid) {
		this.actUid = actUid;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
}
