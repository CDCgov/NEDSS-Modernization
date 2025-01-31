package gov.cdc.nedss.proxy.util;

import java.sql.Timestamp;

/**
 * Created by Sathya Lukkireddy
 */
public class PatientHashCodeModel {


   
    private long patientuid;
    private String lastNm;
    private String firstNm;
    private Timestamp birthTime;
    private String currSexCd;
    private String rootExtensionTxt;
    private String typeCd;
    private String code;
    private String codeDescTxt;
    private String codeSystemCd;
    private Long matchStringHashCode;
    private String matchstring;
    private String cd;
    
    public boolean generateHash()
    {
    	if(this.lastNm != null && this.firstNm != null && this.birthTime != null && this.currSexCd != null)
    	{
    		matchstring = this.lastNm + "^" + this.firstNm + "^" + this.birthTime + "^" + this.currSexCd;
    		matchstring = matchstring.toUpperCase();
    		matchStringHashCode = new Long(matchstring.hashCode());
    		return true;
    	}
    	else if(this.lastNm != null && this.firstNm != null && this.rootExtensionTxt != null && this.typeCd != null && this.code != null && this.codeDescTxt != null
    			&& codeSystemCd != null)
    	{
    		
    		matchstring = this.rootExtensionTxt + "^" + this.typeCd + "^" + this.code + "^" + this.codeDescTxt + "^" + this.codeSystemCd + "^" + this.lastNm + "^" + this.firstNm;
    		matchstring = matchstring.toUpperCase();
    		matchStringHashCode = new Long(matchstring.hashCode());
    		return true;
    			
    		
    	}
    	return false;
    }
    
	public String getMatchstring() {
		return matchstring;
	}

	public void setMatchstring(String matchstring) {
		this.matchstring = matchstring;
	}

	public long getPatientuid() {
		return this.patientuid;
	}
	public void setPatientuid(long patientuid) {
		this.patientuid = patientuid;
	}
	public String getLastNm() {
		return this.lastNm;
	}
	public void setLastNm(String lastNm) {
		this.lastNm = lastNm;
	}
	public String getFirstNm() {
		return this.firstNm;
	}
	public void setFirstNm(String firstNm) {
		this.firstNm = firstNm;
	}
	public Timestamp getBirthTime() {
		return this.birthTime;
	}
	public void setBirthTime(Timestamp birthTime) {
		this.birthTime = birthTime;
	}
	public String getCurrSexCd() {
		return this.currSexCd;
	}
	public void setCurrSexCd(String currSexCd) {
		this.currSexCd = currSexCd;
	}
	public String getRootExtensionTxt() {
		return this.rootExtensionTxt;
	}
	public void setRootExtensionTxt(String rootExtensionTxt) {
		this.rootExtensionTxt = rootExtensionTxt;
	}
	public String getTypeCd() {
		return this.typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeDescTxt() {
		return this.codeDescTxt;
	}
	public void setCodeDescTxt(String codeDescTxt) {
		this.codeDescTxt = codeDescTxt;
	}
	public String getCodeSystemCd() {
		return this.codeSystemCd;
	}
	public void setCodeSystemCd(String codeSystemCd) {
		this.codeSystemCd = codeSystemCd;
	}

	public Long getMatchStringHashCode() {
		return this.matchStringHashCode;
	}

	public void setMatchStringHashCode(Long matchStringHashCode) {
		this.matchStringHashCode = matchStringHashCode;
	}

	public String getCd() {
		return this.cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}
    
  
}
