package gov.cdc.nedss.act.interview.dt;

import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Timestamp;
/**
* Name:		InterviewAnswerDT.java
* Description:	DT for Interview_Answer.
* Copyright:	Copyright (c) 2014
* Company: 	Leidos
*/

public class InterviewAnswerDT extends NbsAnswerDT {
	private static final long serialVersionUID = 1L;
	private Long interviewAnswerUid;
	private Long interviewUid;

	public InterviewAnswerDT() {
	}

	public InterviewAnswerDT(NbsAnswerDT answerDT) {
		super(answerDT);
		if (answerDT.getNbsAnswerUid() != null)
			interviewAnswerUid = answerDT.getNbsAnswerUid();
		if (answerDT.getActUid() != null)
			interviewUid = answerDT.getActUid();
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = InterviewAnswerDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Long getInterviewUid() {
		return interviewUid;
	}
	public void setInterviewUid(Long interviewUid) {
		this.interviewUid = interviewUid;
		super.setActUid(interviewUid);
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the interviewAnswerUid
	 */
	public Long getInterviewAnswerUid() {
		return interviewAnswerUid;
	}
	/**
	 * @param interviewAnswerUid the interviewAnswerUid to set
	 */
	public void setInterviewAnswerUid(Long interviewAnswerUid) {
		this.interviewAnswerUid = interviewAnswerUid;
		super.setNbsAnswerUid(interviewAnswerUid);
	}
}