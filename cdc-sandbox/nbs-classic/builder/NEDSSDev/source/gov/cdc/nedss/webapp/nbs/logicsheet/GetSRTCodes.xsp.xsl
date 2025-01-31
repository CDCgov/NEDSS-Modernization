<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsp="http://apache.org/xsp" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:srt-codes="http://www.cdc.gov/nedss/GetSRTCodes/1.0">
	<xsl:strip-space elements="*"/>
	<xsl:template match="xsp:page">
		<xsp:page>
			<xsl:apply-templates select="@*"/>
			<xsp:structure>
				<xsp:include>gov.cdc.nedss.webapp.nbs.logicsheet.helper.*</xsp:include>
			</xsp:structure>
			<xsp:logic><![CDATA[

				CachedDropDownValues srtStatic = new CachedDropDownValues();
			   	//GetSRTCodes srtStatic = new GetSRTCodes();

			   	private String getCodedValues(String type) {
					return srtStatic.getCodedValues(type);
				}

				private String getCodesForDesc(String type){
					return srtStatic.getCodesForDesc(type);
				}


			      private String getStateCodes(String type) {
					return srtStatic.getStateCodes(type);
				}

				private String getRaceCodes(String type) {
					return srtStatic.getRaceCodes(type);
				}

				private String getAddressType() {
					return srtStatic.getAddressType();
				}

				private String getAddressUse() {
					return srtStatic.getAddressUse();
				}

				private String getConditionCode() {
					return srtStatic.getConditionCode();
				}

				private String getDiagnosisCode() {
					return srtStatic.getDiagnosisCode();
				}

				private String getCodedResultValue() {
					return srtStatic.getCodedResultValue();
				}
				private String getProgramAreaCodedValue() {
					return srtStatic.getProgramAreaCodedValue();
				}
				private String getLanguageCodes() {
					return srtStatic.getLanguageCode();
				}
				private String getIndustryCodes(){
					return srtStatic.getNAICSGetIndustryCode();
				}
				private String getOccupationCodes(){
					return srtStatic.getOccupationCode();
				}

				private String getLabTestCodes() {
					return srtStatic.getLabTestCodes();
				}
				private String getSuspTestCodes() {
					return srtStatic.getSuspTestCodes();
				}
				private String getJurisdictionCodedSortedValues() {
					return srtStatic.getJurisdictionCodedSortedValues();
				}
				private String getJurisdictionWithUnknown() {
					return srtStatic.getJurisdictionWithUnknown();
				}
   	 		      private String getJurisdictionCodedSortedValuesWithAll() {
					return srtStatic.getJurisdictionCodedSortedValuesWithAll();
				}


				private String getCountryCodesAsString(){
					return srtStatic.getCountryCodesAsString();
				}
				private String getBMDConditionCode(){
					return srtStatic.getBMDConditionCode();
				}
			     private String getSRCountyConditionCode(String countyCd){
					return srtStatic.getSRCountyConditionCode(countyCd);
				}
				private String getOrderedTests() {
					return srtStatic.getOrderedTests();
				}

				private String getResultTests() {
					return srtStatic.getResultTests();
				}

				private String getDrugNames() {
					return srtStatic.getDrugNames();
				}

				private String getTreatmentDesc() {
					return srtStatic.getTreatmentDesc();
				}
				private String getTreatmentDrug() {
					return srtStatic.getTreatmentDrug();
				}
				private String getOrganismList() {
					return srtStatic.getOrganismList();
				}
				private String getJurisdictionCodedSortedValuesNoExport(){
					return srtStatic.getJurisdictionCodedSortedValuesNoExport();
				}


		 // Uncomment the method when resolving the defect civil00010077
		 // for release 1.1.2
		 // ------------------------------------------------------------------------------
		 
               //  private String getACountysReportingSources() {
               //    String countyCode = (String) request.getAttribute("countyValue");
               //    return srtStatic.getACountysReportingSources(countyCode);
               // }

			]]></xsp:logic>
			<xsl:apply-templates/>
		</xsp:page>
	</xsl:template>
	<!-- =================================================================== -->
	<!-- 			initialize the person object                           -->
	<!-- =================================================================== -->
	<xsl:template match="srt-codes:getCodedValues">
		<xsl:variable name="type">
        	"<xsl:value-of select="./type"/>"
      </xsl:variable>
		<xsp:expr>getCodedValues(<xsl:copy-of select="$type"/>)</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getCodesForDesc">
		<xsl:variable name="type">
        	"<xsl:value-of select="./type"/>"
      </xsl:variable>
		<xsp:expr>getCodesForDesc(<xsl:copy-of select="$type"/>)</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getRaceCodes">
		<xsl:variable name="type">
        	"<xsl:value-of select="./type"/>"
      </xsl:variable>
		<xsp:expr>getRaceCodes(<xsl:copy-of select="$type"/>)</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getStateCodes">
		<xsl:variable name="type">
        	"<xsl:value-of select="./type"/>"
      </xsl:variable>
		<xsp:expr>getStateCodes(<xsl:copy-of select="$type"/>)</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getAddressType">
		<xsp:expr>getAddressType()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getAddressUse">
		<xsp:expr>getAddressUse()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getConditionCode">
		<xsp:expr>getConditionCode()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getDiagnosisCode">
		<xsp:expr>getDiagnosisCode()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getCodedResultValue">
		<xsp:expr>getCodedResultValue()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getProgramAreaCodedValue">
		<xsp:expr>getProgramAreaCodedValue()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getLanguageCodes">
		<xsp:expr>getLanguageCodes()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getIndustryCodes">
		<xsp:expr>getIndustryCodes()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getOccupationCodes">
		<xsp:expr>getOccupationCodes()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getLabTestCodes">
		<xsp:expr>getLabTestCodes()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getSuspTestCodes">
		<xsp:expr>getSuspTestCodes()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getJurisdictionCodedSortedValues">
		<xsp:expr>getJurisdictionCodedSortedValues()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getJurisdictionWithUnknown">
		<xsp:expr>getJurisdictionWithUnknown()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getJurisdictionCodedSortedValuesWithAll">
		<xsp:expr>getJurisdictionCodedSortedValuesWithAll()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getCountryCodesAsString">
		<xsp:expr>getCountryCodesAsString()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getBMDConditionCode">
		<xsp:expr>getBMDConditionCode()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getSRCountyConditionCode">
		<xsl:variable name="type">
        	"<xsl:value-of select="./type"/>"
      </xsl:variable>
		<xsp:expr>getSRCountyConditionCode(<xsl:copy-of select="$type"/>)</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getOrderedTests">
		<xsp:expr>getOrderedTests()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getResultTests">
		<xsp:expr>getResultTests()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getDrugNames">
		<xsp:expr>getDrugNames()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getTreatmentDesc">
		<xsp:expr>getTreatmentDesc()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getTreatmentDrug">
		<xsp:expr>getTreatmentDrug()</xsp:expr>
	</xsl:template>
    <xsl:template match="srt-codes:getSourceInCounty">
		<xsp:expr>getACountysReportingSources()</xsp:expr>
	</xsl:template>
	<xsl:template match="srt-codes:getJurisdictionCodedSortedValuesNoExport">
		<xsp:expr>getJurisdictionCodedSortedValuesNoExport()</xsp:expr>
	</xsl:template>

	<!-- Create formatted title -->
	<xsl:template match="@*|*|text()|processing-instruction()">
		<xsl:copy>
			<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="srt-codes:getOrganismList">
		<xsp:expr>getOrganismList()</xsp:expr>
	</xsl:template>
</xsl:stylesheet>
