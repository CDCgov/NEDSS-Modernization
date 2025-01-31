<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
  <!--============================================================-->
  <!--  Description:  This stylesheet transforms NNDM Notification Master             -->
  <!--     Message instances into Generic Disease-specific NNDM Messages.       -->
  <!--     Standard integrity rules are enforced using templates included in             -->
  <!--     nndmIntegrity.xsl. Refer to the Generic Notification Mapping guide for a   -->
  <!--     definition of the rules contained in this stylesheet.                                     -->
  <!--  Author: Craig Stanley                                                                                     -->
  <!--                Emergint, Inc.                                                                                    -->
  <!--  Created: 8-19-2002 using GENERIC MAPPING GUIDE VER 3 Aug 19, 2002   -->
  <!--  Modified: 9-3-2002 using GENERIC MAPPING GUIDE VER 9 Aug 30, 2002    -->
  <!--   Modified 9-17-2002  Remove white space and not beautify                        -->
  <!--   Modified 9-25-2002  Re-engineer to support new integrity rules                 -->
  <!--   Modified 10-3-2002  Import Generic rules                                       -->
  <!--   Modifies 08-05-2003 BP : added Vaccine xsl include, corrected mapping          -->
  <!--   Modified 08-08-2003 BP : comment out nndm_vaccine.xsl inclusion                -->
  <!--                            removed all LAB*** codes not in CORE                  -->
  <!--   Modified 08-12-2003 BP : added DEM161 / DEM 162 according to V3 STD mapping    -->
  <!--   Modified 09-23-2003 BP : modified DEM161 / DEM 162 according to MasterMessage  -->
  <!--   Modified 11/18/2003 BP : INV147 Investigation Start Date added                 -->
  <!--   Modified 12/04/2003 BP : removed a few demographics "only zip code and county code to remain  -->
  <!--   Modified 12/08/2003 BP : added typeCd as keeper element to INV173              -->
  <!--   Modified 12/08/2003 BP : keeping Person.paticipationTypeCd [top of document]   -->
  <!--   Modified 12/10/2003 BP : moved Person.paticipationTypeCd to _integrity, typeCd [INV173] changed to /*  -->
  <!--   Modified 12/31/2003 BP : NOT110 added as part of R1.1.2. CORE -->
  <!--   Modified 01/09/2004 BP : mapping changes applies to NOT106, NOT109, NTF122     -->
  <!--============================================================-->
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>
  <!--============================================================-->
  <!--            Import the Static Integrity Rules               -->
  <!--============================================================-->
  <xsl:include href="nndm_integrity_static.xsl"/>
  <!--============================================================-->
  <!--            Import the Vaccine Rules                        -->
  <!--============================================================-->
  <xsl:include href="nndm_vaccine.xsl"/>
  <!--============================================================-->
  <!--            Import the LabReport Rules                        -->
  <!--============================================================-->
  <xsl:include href="nndm_labReport.xsl"/>
  <!--============================================================-->
  <!--            Import the MorbReport Rules                        -->
  <!--============================================================-->
  <xsl:include href="nndm_morbReport.xsl"/>
  <!--============================================================-->
  <!--============================================================-->
  <!--            Start at the root to begin the processing.      -->
  <!--============================================================-->
  <xsl:template match="/">
    <xsl:apply-templates select="*"/>
  </xsl:template>
  <!--============================================================-->
  <!--  Generic copy template to pass through the attributes,     -->
  <!--  text nodes to the destination output.                     -->
  <!--============================================================-->
  <xsl:template match="@*|*|text()|comment()">
    <xsl:copy>
      <xsl:apply-templates select="@*|*|text()|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!--============================================================-->
  <!--                                             Disease-specific filters                                      -->
  <!--============================================================-->
  <!-- inserted 12/08/2003 BP - moved to integrity [bottom] -->

  <!-- INV165	MMWR Week -->
  <!-- Integrity includes this template -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:mmwrWeek">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV166	MMWR Year -->
  <!-- Integrity includes this template -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:mmwrYear">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- NOT110	Record Type -->
  <!-- PHC.caseTypeCd -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:caseTypeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- NTF122	NEDSS Notification Version Number -->
  <!-- Integrity (_integrity and _integrity_static included PHC.versionCtrlNbr, 01/09 mapping change-->
  <xsl:template match="IM:MessageHeader/IM:version">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!-- INV111	Investigation Reporting Source Date -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:rptFormCmpltTime">
     <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
     </xsl:copy>
  </xsl:template>
  <!-- INV112	Reporting Source Type Code -->
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
  <!-- DEM163	Zip Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:zipCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- NOT109	NND Reporting State -->
  <xsl:template match="IM:MessageHeader/IM:nbsStateCode">
    <xsl:copy>
       <xsl:attribute name="keep">1</xsl:attribute>
       <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- NOT106	Date Sent -->
  <!-- previously IM:NNDEvent[IM:NedssEventType='Notification']/IM:NedssEvent/IM:Act/IM:notification/IM:rptSentTime -->
  <xsl:template match="IM:MessageHeader/IM:firstReportedToCDC">
    <xsl:copy>
       <xsl:attribute name="keep">1</xsl:attribute>
       <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV187	Case Report County Code DELETED as per request of 09/09/2003 -->
  <!-- DEM165	County Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator/IM:postalLocatorType/IM:cntyCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV107	Case Jurisdiction Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:jurisdictionCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV108	Case Program Area Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:progAreaCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- NTF139	Notification Unique ID -->
  <!-- Integrity includes this template -->
  <!-- DEM197	Person Local ID -->
  <!-- Integrity includes this template -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:localId">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!-- INV145	Patient Death -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:deceasedIndCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM128	Deceased Time -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:deceasedTime">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!-- INV168	Investigation Local ID -->
  <!-- TODO:  Assumed this element is contained in Integrity include.
    <xsl:template match=""/>
  -->
  <!-- <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:localId"> -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:localId">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV173	State Case ID -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId/IM:rootExtensionTxt">
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
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId/IM:typeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- INV169	Condition Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV170	Condition Code Desc Text -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:codeDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV164	Number of Cases / Case Group Case Count -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:groupCaseCnt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV177	Date First Reported PHD -->
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
  <!-- INV176	Date of First Report to CDC -->
  <!-- Integrity includes this template -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='INV176']/IM:obsValueDate/IM:fromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV163	Case Class Status Code -->
  <!-- Integrity includes this template -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:caseClassCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV109	Case Investigation Class Status Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:investigationStatusCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV171	Case Outcome Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outcomeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV152	Case Disease Imported Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:diseaseImportedCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV150	Case Outbreak Indicator -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outbreakInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV151	Case Outbreak Name -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outbreakName">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV137	Date of Onset Illness -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveFromTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV136	Diagnosis Date -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:diagnosisTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  
   <!-- INV138 and INV139 added on FEB 2006-->

  <!-- INV138	Illness End Date-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveToTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
    <!-- INV139	Illness Duration-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveDurationAmt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
    <!-- INV140	Investigation Illness Duration Type -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:effectiveDurationUnitCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- INV128	Was the patient hospitalized as a result of this event? -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='INV128']/IM:obsValueCoded">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM139	Primary Occupation -->
  <!-- DELETE Aug 05 2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:occupationCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- DEM113 Current Sex Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:currSexCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM152	Race Category Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:personRace/IM:raceCategoryCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- 09/04/2003 taken out to preserve only raceCategoryCd elements -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:personRace/IM:raceSeqNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:personRace/IM:raceDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- -->
  <!-- DEM155	Ethnic Group Indicator-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:ethnicGroupInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM156	Ethnic Group Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:personEthnicGroup/IM:ethnicGroupCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM216	Person Age Reported -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:ageReported">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM218	Person Age Reported UNit Code -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:ageReportedUnitCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM115	Birth Time -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:birthTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- DEM137	Education Level Code -->
  <!-- DELETE Aug 05 2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:educationLevelCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- DEM135	Adults in house number -->
  <!-- DELETE Aug 05 2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:adultsInHouseNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- DEM136	Children in house number -->
  <!-- DELETE Aug 05 2003
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:person/IM:childrenInHouseNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- DEM161	City of residence of the case subject -->
  <!-- removed 12/04/2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:cityDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- DEM162	State of residence of the case subject -->
  <!-- removed 12/04/2003
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:classCd='PSN' and IM:person/IM:participationTypeCd='SubjOfPHC']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:stateCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->
  <!-- INV147	Investigation Start Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:activityFromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
<!-- New additions 01/24/2005 -->
<!-- Confirmation method -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:confirmationMethod">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV161 Confirmation method Cd-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:confirmationMethod/IM:confirmationMethodCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- Confirmation method Desc Txt-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:confirmationMethod/IM:confirmationMethodDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV162 Confirmation method Time-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:confirmationMethod/IM:confirmationMethodTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV159 Detection Method Cd-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:detectionMethodCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
<!-- Detection Method Description Txt-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:detectionMethodDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV157 Transmission Mode Cd-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:transmissionModeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
<!-- Transmission Mode Description Txt-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:transmissionModeDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV178 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV178']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV179 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV179']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV148 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV148']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV149 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV149']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


<!-- INV128 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV128']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV132 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV132']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV133 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV133']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV134 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV134']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV153 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV153']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV154 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV154']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV155 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV155']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- INV156 -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV156']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
