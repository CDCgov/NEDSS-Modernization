package gov.cdc.nedss.proxy.util;

/**
 * Created by Sathya Lukkireddy
 */
public class PatientNOKHashCodeModel {
	private long patientuid;
	private String lastNm;
	private String firstNm;
	private String rootExtensionTxt;
	private String typeCd;
	private String code;
	private String codeDescTxt;
	private String codeSystemCd;
	private Long matchStringHashCode;
	private String matchstring;
	private String cd;
	private String name;
	private String streetaddress1;
	private String city;
	private String state;
	private String zipcode;
	private String telephonetxt;

	public boolean generateHash() {

		if (this.rootExtensionTxt != null && this.typeCd != null
				&& this.code != null && this.codeDescTxt != null
				&& codeSystemCd != null) {

			matchstring = this.rootExtensionTxt + "^" + this.typeCd + "^"
					+ this.code + "^" + this.codeDescTxt + "^"
					+ this.codeSystemCd;
			matchstring = matchstring.toUpperCase();
			matchStringHashCode = new Long(matchstring.hashCode());
			return true;

		} else if (this.lastNm != null && this.firstNm != null
				&& this.streetaddress1 != null && this.city != null
				&& this.state != null && this.zipcode != null) {
			matchstring = this.lastNm + "^" + this.firstNm + "^"
					+ this.streetaddress1 + "^" + this.city + "^" + this.state
					+ "^" + this.zipcode;
			matchstring = matchstring.toUpperCase();
			matchStringHashCode = new Long(matchstring.hashCode());
			return true;
		} else if (this.lastNm != null && this.firstNm != null
				&& this.telephonetxt != null) {

			matchstring = this.lastNm + "^" + this.firstNm + "^"
					+ this.telephonetxt;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetaddress1() {
		return streetaddress1;
	}

	public void setStreetaddress1(String streetaddress1) {
		this.streetaddress1 = streetaddress1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTelephonetxt() {
		return telephonetxt;
	}

	public void setTelephonetxt(String telephonetxt) {
		this.telephonetxt = telephonetxt;
	}

}
