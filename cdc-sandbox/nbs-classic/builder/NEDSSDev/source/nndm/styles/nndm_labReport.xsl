<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--===================================================================-->
   <!-- Description:  This stylesheet transforms NNDM Notification Master -->
   <!-- Message instances into LabResult-specific NNDM Messages. -->
   <!--******************************************************-->
   <!-- Author: CStanley -->
   <!-- Emergint, Inc. -->
   <!-- Created: 9-17/2003 CStanley - Created using "Laboratory Event Mapping Guide" -->
   <!-- Modified: 12/30/2003 BPlume - added Lab320-327, 328 removed afterwards -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>
   <!--============================================================-->

   <!--============================================================-->
   <!-- Lab Reports specific -->
   <!--============================================================-->

<!--  LAB250 -->

<!--  Reporting Lab CLIA# -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='AUT']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Organization Shell for participationTypeCd = 'AUT' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='AUT']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>


 <!--  Reporting Lab Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='AUT']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Provider Shell for participationTypeCd = 'ORD' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ORD']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Ordering Provider ID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ORD']/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
 <!--  Ordering Provider Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='ORD']/IM:personName">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Organization Shell for participationTypeCd = 'ORD' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ORD']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>



<!--  LAB142 -->
 <!--  Ordering Facility CLIA Number -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ORD']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
 <!--  Ordering Facility Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ORD']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
 <!--  Ordering Facility CLIA Phone number -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ORD']/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
 <!--  Performing Lab ID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='PRF']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
 <!--  Performing Lab Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='PRF']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>


 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Material Shell for participationTypeCd = 'SPC' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

<!--  LAB165 -->
 <!--  Specimin Source -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']/IM:cd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

<!--  LAB262 -->
 <!--  Specimin Details -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']/IM:description">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']/IM:cdDescTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

<!--  LAB263 -->
 <!--  Danger Code -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']/IM:riskCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

<!-- ************************ -->
<!--  HOT FIX -->
<!-- ************************ -->
 <!--  Directed by Mary Hamilton 11/5/2003 to fix an NBS anomoly where invalid material elements appear in the Master Message -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:material[IM:participationTypeCd='SPC']">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

  <!--  LAB320 -->
  <!--  LOINC Property Descriptive -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:property">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!--  LAB321 -->
  <!--  LOINC Scale Descriptive Text-->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:scaleType">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!--  LAB322 -->
  <!--  LOINC Timing Aspect -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:timeAspect">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!--  LAB323 -->
  <!--  LOINC System Descriptive Text -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:systemCd">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!--  LAB324 -->
  <!--  LOINC Method Descriptive Text -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:methodType">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  <!--  LAB325 -->
  <!--  LOINC Class Descriptive Text -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:relatedClassCd">
        <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
        </xsl:copy>
  </xsl:template>
  <!--  LAB326 -->
  <!--  LOINC Code Value -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:loincCd">
        <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
        </xsl:copy>
  </xsl:template>
  <!--  LAB327 -->
  <!--  LOINC Analyte Name Descriptive Text -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:loincCd/IM:componentName">
        <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
        </xsl:copy>
  </xsl:template>
  
 
    <!-- Added Feb 2006 LOINC Result Method CD-->
 
    <!--  LOINC methodCd -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:methodCd">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>
  
  
  
  
  
  
  
</xsl:stylesheet>
