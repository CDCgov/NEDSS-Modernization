package gov.cdc.nedss.systemservice.dt;

import java.io.Serializable;
import java.sql.Timestamp;


public class XSSFilterPatternDT implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long XSSFilterPatternUid;
	private String regExp;
	private String flag;
	private String descTxt;
	private String statusCd;
	private Timestamp statusTime;

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
		
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
		
	}

	/**
	 * @return the xSSFilterPatternUid
	 */
	public Long getXSSFilterPatternUid() {
		return XSSFilterPatternUid;
	}

	/**
	 * @param xSSFilterPatternUid the xSSFilterPatternUid to set
	 */
	public void setXSSFilterPatternUid(Long xSSFilterPatternUid) {
		XSSFilterPatternUid = xSSFilterPatternUid;
	}

	/**
	 * @return the regExp
	 */
	public String getRegExp() {
		return regExp;
	}

	/**
	 * @param regExp the regExp to set
	 */
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the descTxt
	 */
	public String getDescTxt() {
		return descTxt;
	}

	/**
	 * @param descTxt the descTxt to set
	 */
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * @return the statusTime
	 */
	public Timestamp getStatusTime() {
		return statusTime;
	}

}
