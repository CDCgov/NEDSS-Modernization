package gov.cdc.nedss.act.ctcontact.dt;

/**
* Name:		CTContactAnswerHistDT.java
* Description:	DT for CT_contact History.
* Copyright:	Copyright (c) 2009
* Company: 	Computer Sciences Corporation
*/

public class CTContactAnswerHistDT extends CTContactAnswerDT {
	private static final long serialVersionUID = 1L;

	private Long ctContactAnswerHistUid;
	private Integer ctContactVersionCtrlNbr;

	public Long getCtContactAnswerHistUid() {
		return ctContactAnswerHistUid;
	}
	public void setCtContactAnswerHistUid(Long ctContactAnswerHistUid) {
		this.ctContactAnswerHistUid = ctContactAnswerHistUid;
	}
	public Integer getCtContactVersionCtrlNbr() {
		return ctContactVersionCtrlNbr;
	}
	public void setCtContactVersionCtrlNbr(Integer ctContactVersionCtrlNbr) {
		this.ctContactVersionCtrlNbr = ctContactVersionCtrlNbr;
	}
}
