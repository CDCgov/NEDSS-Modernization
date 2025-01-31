<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--===================================================================-->
   <!-- Description:  This stylesheet transforms NNDM Notification Master -->
   <!-- Message instances into Vaccine-specific NNDM Messages. -->
   <!-- Standard integrity rules are enforced using templates included in -->
   <!-- nndmIntegrity.xsl. Refer to the STD Notification Mapping guide for a -->
   <!-- definition of the rules contained in this stylesheet. -->
   <!--******************************************************-->
   <!-- Author: KLCobb -->
   <!-- Emergint, Inc. -->
   <!-- Created: 8-4/2003 KLCobb - Created using "Vaccine Notif MsgMapping for 1_1 FINAL.doc" -->
   <!--                             These were previously included in the disease specific XSL's-->
   <!-- Modified 12-03-2003 BPlume - rechecked PerformerOfVacc and ManufacturedMaterial -->
   <!-- Modified 12-30-2003 BPlume - soft deleted VAC116, VAC110, VAC111 -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>
   <!--============================================================-->

   <!--============================================================-->
   <!-- Disease-specific filters -->
   <!--============================================================-->

 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Material Shell for participationTypeCd = 'VaccGiven' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='MAT']/IM:material[IM:participationTypeCd='VaccGiven']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- VAC101	Vaccine Administered -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='MAT']/IM:material[IM:participationTypeCd='VaccGiven']/IM:nm">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- VAC102	Vaccination Record ID -->
   <!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='INTV']/IM:intervention/IM:localId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC103	Vaccine Administered Date -->
   <!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='INTV']/IM:intervention/IM:effectiveFromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC104	Vaccination Anatomical Site -->
   <!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='INTV']/IM:intervention/IM:targetSiteCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC105	Age At Vaccination -->
   <!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC106	Age At Vaccination Unit -->
   <!--
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='INTV']/IM:observation/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->

	 <!-- VAC107	Vaccine Manufacturer -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent[IM:Role/IM:cd='MfgdVaccine']/IM:Entity/IM:organization/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- VAC118	Vaccine Manufacturer Organization Id -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent[IM:Role/IM:cd='MfgdVaccine']/IM:Entity[IM:classCd='ORG']/IM:organization/IM:localId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>


 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Material Shell -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='MAT']/IM:material/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>


   <!-- VAC108	Vaccine Lot Number -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='MAT']/IM:material/IM:ManufacturedMaterial/IM:lotNm">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- VAC109	Vaccine Expiration Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='MAT']/IM:material/IM:ManufacturedMaterial/IM:expirationTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- VAC116	Vaccination Given By Person Id -->
   <!-- deleted for R1.1.2.
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='PSN']/IM:person[IM:participationTypeCd='PerformerOfVacc']/IM:localId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC110	Vaccination Given By First Name -->
   <!-- deleted for R1.1.2.
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='PSN']/IM:person[IM:participationTypeCd='PerformerOfVacc']/IM:personName/IM:firstNm">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- VAC111	Vaccination Given By Last Name -->
   <!-- deleted for R1.1.2.
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='PSN']/IM:person[IM:participationTypeCd='PerformerOfVacc']/IM:personName/IM:lastNm">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->

	 <!-- VAC117	Vaccination Given By Organization Id -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='ORG']/IM:organization[IM:participationTypeCd='PerformerOfVacc']/IM:localId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- VAC112	Vaccination Given By Organization Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity[IM:classCd='ORG']/IM:organization[IM:participationTypeCd='PerformerOfVacc']/IM:organizationName">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>


   <!-- VAC113	Vaccination Program Area -->
   <!-- This was excluded from the updates made 8/5/2003, was not in the update doc "Vaccine Notif MagMapping for 1_1 FINAL.doc"
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act[IM:classCd='INT']/IM:intervention/IM:progAreaCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->

</xsl:stylesheet>
