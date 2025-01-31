<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--============================================================ -->
   <!--  Description:  This stylesheet transforms NNDM Notification Master -->
   <!--     Message instances into Pertussis-specific NNDM Messages. -->
   <!--     Standard integrity rules are enforced using templates included in -->
   <!--     nndmIntegrity.xsl. Refer to the Pertussis Notification Mapping guide for -->
   <!--     a definition of the rules contained in this stylesheet. -->
   <!--  Author: Craig Stanley -->
   <!--                Emergint, Inc. -->
   <!--  Created: 8-19-2002 using Pertussis MAPPING GUIDE VER 6 Sep 4, 2002 -->
   <!--   Modified 9-17-2002  Remove white space and not beautify -->
   <!--   Modified 9-25-2002  Re-engineer to support new integrity rules -->
   <!--   Modified 10-3-2002  Import Generic rules -->
   <!--   Modified 7/31/2003  Updated per 'Pertussis Notify MsgMapping for 1_1 FINAL.doc' file -->
   <!--============================================================ -->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>

   <!--============================================================ -->
   <!-- Import the Generic Rules -->
   <!--============================================================ -->
   <xsl:include href="nndm_generic.xsl"/>

   <!--============================================================ -->
   <!-- Disease-specific filters -->
   <!--============================================================ -->
   <!-- INV147	Investigation Start Date -->
   <!-- removed to generic -->

   <!-- INV110	Investigation Date Assigned -->
   <!-- Integrity includes this template -->

   <!-- INV111	Investigation Reporting Source Date -->
   <!-- removed to generic -->
   <!-- INV118	Investigation Reporting Source Zip -->
   <!-- 09/08/03 BP hotfix correcting XPath
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ReporterOfPHC']/IM:zipCd">
   -->
	 <!-- Modified 03-02-2005 deleted INV132, INV133, INV134, PRT085 as the elements are added in nndm_generic.xsl -->
	 <!-- As PRT085 maps to INV161 in nndm_generic.xsl, PRT085 is removed from this xsl -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='OrgAsReporterOfPHC']/IM:entityLocator/IM:postalLocatorType/IM:zipCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
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
  <!-- INV129	Investigation Hospital Name -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='HospOfADT']/IM:organizationName/IM:nmTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
   <!-- INV184	Hospital Local Id -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='HospOfADT']/IM:localId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- INV130	Investigation Hospital Id -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfADT']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- INV131	Investigation Hospital Id Type -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfADT']/IM:entityId/IM:typeCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT001	Did the patient have any cough? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT001']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT002	What was the date of cough onset? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT002']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT003	Did the patient have paroxysmal cough? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT003']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT004	Did the patient have whoop? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT004']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT005	Did the patient have post-tussive vomiting? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT005']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT006	Did the patient have apnea? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT006']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT007	Date of final interview -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT007']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT008	Was there a cough at final interview? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT008']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT009	What was the duration of the cough (in days)? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT009']">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
<!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT009']/IM:obsValueDate/IM:durationUnitCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
-->
   <!-- PRT011	Result chest X-ray for pneumonia -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT011']/IM:obsValueCoded">
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
   <!-- PRT012	Generalized/focal seizures due to pertussis? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT012']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT013	Acute encephalopathy due to pertussis? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT013']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT020	Were antibiotics given? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT020']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT021	Antibiotic Received -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT021']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT023	Antibiotic Start Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT023']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT024 	Number of days antibiotic actually taken -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT024']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT029	Was laboratory testing for pertussis done? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT029']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT089	Bordetella Pertussis Culture -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT089']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT030	Culture Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT030']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT031	Bordetella pertussis culture result -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT031']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT090	Bordetella Pertussis Serology #1 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT090']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT033	Serology #1 Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT033']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT034	Bordetella pertussis serology #1 result -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT034']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT091	Bordetella Pertussis Serology #2 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT091']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT037	Serology #2 Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT037']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT038	Bordetella pertussis serology #2 result -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT038']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT092	Bordetella Pertussis PCR Specimen -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT092']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT040	PCR specimen date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT040']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT041	Bordetella pertussis PCR result -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT041']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT081	Was other laboratory testing done? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT081']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT082	Specify other test -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT082']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT083	Date of other test -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT083']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT084	Other laboratory test results -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT084']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT093	Genotyping Sent to CDC for Genotyping -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT093']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT094	Genotyping Date Pertussis -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT094']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT044	Did the patient ever receive a pertussis vaccine? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT044']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT046	# of doses pertussis vaccine>=2 weeks b/f illness -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT046']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT045	Last pertussis vaccine date before illness -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT045']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT047	Reason not vaccinated w/ 3 or more doses -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT047']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT060	Epi-linked to another confirmed/probable case? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT060']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- PRT061	Case ID of epi-linked case -->
   <!-- included by integrity rules -->

   <!-- PRT062	Case part of an outbreak of 2 or more cases? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:outbreakInd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- PRT063	If part of outreak, what is the outbreak name? (Deleted per update document on 7/31/2003) -->
   <!-- Part of Code Investigation INV151 -->

   <!-- PRT067	Spread beyond transmission setting documented? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT067']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT068	Setting for spread of this case outside household? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT068']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT069	Setting (other) for spread outside household? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT069']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT070	One or more suspected sources of infection? -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT070']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT071	Number of suspected sources of infection -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT071']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT074	Source Age -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT074']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT075	Source Age Type -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT075']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT076	Source Sex -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT076']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT077	Relationship to Case -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT077']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT078	Relationship (other) to Case -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT078']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT087	Relationship Number Doses -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT087']">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT088	Relationship Cough Onset Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT088']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT080	# of contacts recommended to receive prophylaxis -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT080']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT096	Serology #1 Lab Where Performed -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT096']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT097	Serology #1 Lab Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT097']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT098	Serology #2 Lab Where Performed -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT098']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT099	Serology #2 Lab Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT099']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT100	PCR Lab Where Performed -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT100']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT101	PCR Lab Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT101']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT102	Genotyping Specimen Type -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT102']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT103	Patient Death From Pertussis -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT103']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT104	Not Vaccinated Reason -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT104']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- PRT105	Doses Pertussis Vaccine 2 Weeks Before Illness -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT105']">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
      <!-- PRT107	Patient < 12 Months -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT107']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
    <!-- PRT108 	Mother's Age at Infant Birth -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT108']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   <!-- PRT109 	Infant Birth Weight (in pounds) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT109']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
    <!-- PRT110 	Infant Birth Weight (in ounces) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT110']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
       <!-- PRT111 	Infant Birth Weight (in grams) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT111']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
       <!-- PRT112 	Infant Birth Weight (Unknown) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='PRT112']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
   
   
   
   
   <!-- INV167	Investigation General Comments (Deleted per update document on 7/31/2003) -->

   <!-- NOT106	Date Sent -->
   <!-- TODO:  IMPLEMENTATION GUIDE INCOMPLETE FOR THIS ELEMENT -->
</xsl:stylesheet>