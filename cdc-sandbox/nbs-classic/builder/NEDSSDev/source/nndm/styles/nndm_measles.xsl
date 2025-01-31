<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
  <!--============================================================-->
  <!--  Description:  This stylesheet transforms NNDM Notification Master             -->
  <!--     Message instances into Measles Disease-specific NNDM Messages.      -->
  <!--     Standard integrity rules are enforced using templates included in             -->
  <!--     nndmIntegrity.xsl. Refer to the Measles Notification Mapping guide for a -->
  <!--     definition of the rules contained in this stylesheet.                                     -->
  <!--  Author: Craig Stanley                                                                                     -->
  <!--                Emergint, Inc.                                                                                    -->
  <!--  Created: 8-19-2002 using MEASLES MAPPING GUIDE VER 3 Aug 19, 2002 -->
  <!--  Modified: 9-4-2002 using MEASLES MAPPING GUIDE VER 7 Sep 1, 2002    -->
  <!--   Modified 9-17-2002  Remove white space and not beautify                        -->
  <!--   Modified 9-25-2002  Re-engineer to support new integrity rules                 -->
  <!--   Modified 10-3-2002  Import Generic rules                                       -->
  <!--   Modified 08-05-2003 BP : cut VAC rules into nndm_vaccine.xsl                   -->
  <!--   Modified 08-08-2003 BP : comment out nndm_vaccine.xsl inclusion                -->
	<!--   Modified 03-03-2005 SC: deleted INV132, INV133, INV134, INV153, INV154, INV155, INV156, MEA069 as elements are added to nndm_generic.xsl-->
	<!--   Because INV161 in nndm_generic.xsl maps to MEA069, this element is deleted from this xsl -->
  <!--============================================================-->
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>
  <!--============================================================-->
  <!--                         Import the Generic Rules           -->
  <!--============================================================-->
  <xsl:include href="nndm_generic.xsl"/>
  <!--============================================================-->
  <!--                         Import the Vaccine Rules           -->
  <!--============================================================-->
  <!-- <xsl:include href="nndm_vaccine.xsl"/>                     -->
  <!--============================================================-->
  <!--                         Disease-specific filters           -->
  <!--============================================================-->
  <!-- INV110	Investigation Date Assigned -->
  <!-- DELETED INV110					-->
  <!-- Integrity includes this template -->
  <!-- INV111	Investigation Reporting Source Date -->
   <!-- removed to generic -->
  <!-- INV118	Reporting Source Zip Code -->
  <!-- <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ReporterOfPHC']/IM:zipCd"> -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='OrgAsReporterOfPHC']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV120	Investigation Earliest Date Reported County -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:rptToCountyTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV121	Investigation Earliest Date Reported State -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:rptToStateTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV146	Investigation Date of Death -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='INV146']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA001	Did the patient have a rash? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA001']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA002	What was the rash onset date? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA002']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA003	How many days did the rash last? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA003']/IM:obsValueNumeric/IM:numericValue1">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- COMMENTED OUT TO REFLECT SPECS
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA003']/IM:obsValueDate/IM:durationAmt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA003']/IM:obsValueDate/IM:durationUnitCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- MEA004	Was the rash generalized? -->
  <!-- Will be included based on MEA001??? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA004']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA005	Did the patient have a fever? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA005']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA071	Date of Fever Onset -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA071']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA006	What was the highest measured temperature? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA006']/IM:obsValueNumeric/IM:numericValue1">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA007	Temperature units -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA007']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA008	Cough -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA008']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA009	Croup -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA009']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA010	Coryza -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA010']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA011	Hepatitis -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA011']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA012	Conjunctivitis -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA012']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA013	Otitis Media -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA013']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA014	Diarrhea -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA014']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA015	Pneumonia -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA015']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA016	Encephalitis -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA016']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA017	Thrombocytopenia -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA017']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA018	Other Complications -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA018']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA019	Specify Other Complication -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA019']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA027	Was laboratory testing done for measles? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA027']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA073	IgM Testing -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA073']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA028	Date IgM specimen taken -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA028']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA029	Result of IgM test -->
  <!-- Included with MEA073 ??? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA029']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA074	IgM acute/ convalescent -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA074']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA030	Date IgG acute specimen taken -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA030']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA031	Date IgG Convalescent Specimen Taken -->
  <!-- Included with MEA030 ??? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA031']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA032	Result of acute/ convalescent IgG tests -->
  <!-- Included with MEA074 ??? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA032']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA033	Was other lab testing done? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA033']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA034	Specify Other  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA034']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA035	Date of other testing -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA035']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA036	Other laboratory results -->
  <!-- Included with MEA033 ??? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA036']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA038	Clinical specimens sent to CDC for genotyping? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA038']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA072	Date Sent for Genotyping -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA072']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV147	Investigation Start Date -->
  <!-- removed to generic -->
  <!-- MEA060	Does the patient reside in the USA? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA060']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA067	Epi-linked to another confirmed or probable case? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA067']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA068	Case traceable (linked) to an international case? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA068']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA057	Transmission Setting? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA057']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA058	Transmission Setting Other -->
  <!-- DELETED as of Sep 17 2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA058']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- MEA059	Were age and setting verified? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA059']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DELETED -->
  <!-- INV167	General Comments -->
  <!-- MEA039	Did patient receive measles-containing vaccine? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA039']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA040	If no measles vaccine, what was the reason? -->
  <!-- covered by MEA039 -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA040']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA042	Number of doses received BEFORE first birthday -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA042']/IM:obsValueNumeric/IM:numericValue1">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA043	Number of doses ON or AFTER first birthday -->
  <!-- covered by MEA042 -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA043']/IM:obsValueNumeric/IM:numericValue1">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA044	Reason vaccinated before birthday but not after -->
  <!-- covered by MEA039 -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA044']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA045	Reason vaccinated after birthday but no second dose -->
  <!-- covered by MEA039 -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA045']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV139	Investigation Illness Duration -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveDurationAmt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV138	Investigation Illness End Date -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveToTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV130	Hospital ID -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfADT']/IM:entityId/IM:rootExtensionTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV131	Hospital ID Type -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfADT']/IM:entityId/IM:typeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV129	Investigation Hospital Name -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='HospOfADT']/IM:organizationName/IM:nmTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA075	Rash onset occur within 18 days of entering USA -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA075']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA076	Source of Infection -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA076']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA077	Measles Specimen Type -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA077']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- MEA078	Patient Death from Measles -->
  <!-- DOUBLE CHECK PATH b/c the classCd='OBS' is not frequently used with PHC -->
  <!-- <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:publicHealthCase/IM:outcomeCd"> -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA078']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- civil00013515 'MEA079  Was the (Measles) virus genotype sequenced-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA079']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  civil00013515 'MEA080  If yes identify the genotype-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA080']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  civil00013515 'MEA081  Specify Other Sequence-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='MEA081']/IM:obsValueText">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- NOT106	Date Sent -->
</xsl:stylesheet>