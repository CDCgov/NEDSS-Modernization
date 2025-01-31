package gov.cdc.nedss.act.ctcontact.dt;

import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Timestamp;
/**
* Name:		CTContactAnswerDT.java
* Description:	DT for CT_cotact answers.
* Copyright:	Copyright (c) 2009
* Company: 	Computer Sciences Corporation
*/

public class CTContactAnswerDT extends NbsAnswerDT {
	private static final long serialVersionUID = 1L;
	private Long ctContactAnswerUid;
	private Long ctContactUid;

	public CTContactAnswerDT() {
	}

	public CTContactAnswerDT(NbsAnswerDT answerDT) {
		super(answerDT);
		if (answerDT.getNbsAnswerUid() != null)
			ctContactAnswerUid = answerDT.getNbsAnswerUid();
		if (answerDT.getActUid() != null)
			ctContactUid = answerDT.getActUid();
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactAnswerDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Long getCtContactUid() {
		return ctContactUid;
	}
	public void setCtContactUid(Long ctContactUid) {
		this.ctContactUid = ctContactUid;
		super.setActUid(ctContactUid);
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the ctContactAnswerUid
	 */
	public Long getCtContactAnswerUid() {
		return ctContactAnswerUid;
	}
	/**
	 * @param ctContactAnswerUid the ctContactAnswerUid to set
	 */
	public void setCtContactAnswerUid(Long ctContactAnswerUid) {
		this.ctContactAnswerUid = ctContactAnswerUid;
		super.setNbsAnswerUid(ctContactAnswerUid);
	}
}