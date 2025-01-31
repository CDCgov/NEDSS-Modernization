<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
  <!--===================================================================-->
  <!--  Description:  This stylesheet transforms NNDM Notification Master                            -->
  <!--     Message instances into Sexually Transmitted Disease-specific NNDM Messages. -->
  <!--     Standard integrity rules are enforced using templates included in                           -->
  <!--     nndmIntegrity.xsl. Refer to the STD Notification Mapping guide for a                       -->
  <!--     definition of the rules contained in this stylesheet.                                                   -->
  <!--  Author: Craig Stanley                                                                                                   -->
  <!--                Emergint, Inc.                                                                                                  -->
  <!--  Created: 9-10-2002 using Summary MAPPING GUIDE VER 1 Sep 1, 2002                  -->
  <!--   Modified 9-17-2002  Remove white space and not beautify                        -->
  <!--   Modified 9-25-2002  Re-engineer to support new integrity rules                 -->
  <!--   Modified 10-3-2002  Import Generic rules                                       -->
  <!--   Modified 08-11-2003 BP : modified according to new STD documentation           -->
  <!--   Modified 09-23-2003 BP : changed INV161 to reflect changes made to BMird INV161 -->
  <!--   Modified 12-05-2003 BP : added DEM161 and DEM162 as per request from Margaret 12/05 -->
  <!--   Modified 03-02-2005 SC: deleted INV178, INV179, INV128, INV152, INV161, as these elements are added to nndm_generic.xsl	-->
  <!--============================================================-->
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>
  <!--============================================================-->
  <!--                         Import the Generic Rules                                                        -->
  <!--============================================================-->
  <xsl:include href="nndm_generic.xsl"/>
  <!--============================================================-->
  <!--                                             Disease-specific filters                                      -->
  <!--============================================================-->
  <!-- DEM Demographic Value -->
  <!-- Included in generic XSL -->
  <!-- DEM113 Current Sex Code -->
  <!-- Included in generic XSL -->
  <!-- DEM115 Birth Time-->
  <!-- Included in generic XSL -->
  <!-- DEM152 Race Category Code -->
  <!-- Included in generic XSL -->
  <!-- DEM155 Spanish Origin [Ethnic Group Indicator] -->
  <!-- Included in generic XSL -->
  <!-- DEM156 Ethnic Group Code -->
  <!-- Included in generic XSL -->
  <!-- DEM122 Age Category Code DELETED! -->
  <!-- Included in generic XSL -->
  <!-- DEM135 Adults in house number DELETED! -->
  <!-- Included in generic XSL -->
  <!-- DEM136 Children in house number DELETED! -->
  <!-- Included in generic XSL -->
  <!-- DEM137 Education Level Code DELETED! -->
  <!-- Included in generic XSL -->
  <!-- DEM139 Primary Occupation DELETED! -->
  <!-- Included in generic XSL -->
  <!-- DEM163	Zip Code -->
  <!-- Included in generic XSL -->
  <!-- DEM165	County Code -->
  <!-- Included in generic XSL -->
  <!-- DEM197	Person Local ID -->
  <!-- Included in generic XSL -->

  <!-- ============================================================================= -->
  <!-- NOTE : all INV rules below are part of generic.xsl ! Therefore, commented out -->
  <!-- ============================================================================= -->
  <!-- INV107	Case Jurisdiction Code -->
  <!-- -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:jurisdictionCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV108	Case Program Area Code -->
  <!--     -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:progAreaCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV109	Case Investigation Class Status Code -->
  <!--        -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:investigationStatusCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV112	Reporting Source Type Code -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:rptSourceCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:rptSourceCdDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV136	Diagnosis Date -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:diagnosisTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV137	Date of Onset Illness -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveFromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV150	Case Outbreak Indicator -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outbreakInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV151	Case Outbreak Name -->
  <!--   -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outbreakName">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV163	Case Class Status Code -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:caseClassCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV164	Number of Cases -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:groupCaseCnt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV165	MMWR Week -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:mmwrWeek">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV166	MMWR Year -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:mmwrYear">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV168	Investigation Local ID -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:localId">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV169	Condition Code -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV170	Condition Code Desc Text -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:codeDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV171	Case Outcome Code -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outcomeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV173	State Case ID -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:act_Id/IM:rootExtensionTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId/IM:assigningAuthorityCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId/IM:assigningAuthorityDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV176	Date of First Report to CDC -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='INV176']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  -->
  <!-- INV177	Date First Reported PHD -->
  <!--  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:rptToCountyTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:rptToStateTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- OLD std.xsl ends here -->
  <!-- DEM161	City of residence of the case subject -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:cityDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM162	State of residence of the case subject -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:stateCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV187	Case Report County Code DELETED as per request of 09/09/2003-->
  </xsl:stylesheet>
