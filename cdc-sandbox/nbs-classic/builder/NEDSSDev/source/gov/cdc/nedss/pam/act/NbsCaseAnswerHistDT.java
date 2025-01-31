/**
 *
 */
package gov.cdc.nedss.pam.act;

/**
* Name:		NbsAnswerHistDT.java
* Description:	DT for NbsAnswerHistory .
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Pradeep Sharma
*/
public class NbsCaseAnswerHistDT extends NbsCaseAnswerDT {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long  nbscaseAnswerHistUid;
	private Integer actVersionCtrlNbr;
	public Long getNbscaseAnswerHistUid() {
		return nbscaseAnswerHistUid;
	}
	public void setNbscaseAnswerHistUid(Long nbscaseAnswerHistUid) {
		this.nbscaseAnswerHistUid = nbscaseAnswerHistUid;
	}
	public Integer getActVersionCtrlNbr() {
		return actVersionCtrlNbr;
	}
	public void setActVersionCtrlNbr(Integer actVersionCtrlNbr) {
		this.actVersionCtrlNbr = actVersionCtrlNbr;
	}

	

}
