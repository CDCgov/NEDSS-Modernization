<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--===================================================================-->
   <!-- Description:  This stylesheet transforms NNDM Notification Master -->
   <!-- Message instances into MorbReport-specific NNDM Messages. -->
   <!--******************************************************-->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>
   <!--============================================================-->

   <!--============================================================-->
   <!-- Mor Reports specific Entities-->
   <!--============================================================-->

<!--  Reporter of Morbidity Report-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Retain Version Control Number-->
 <!-- This is done to retain the Organization Shell for participationTypeCd = 'ReporterOfMorbReport' -->
 <!-- Also, to make sure that the siblings such as participationType etc will be populated with keep = 1 attribute -->
 <!-- A side effect: two attributes with the same name is created -->

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ReporterOfMorbReport']/IM:versionCtrlNbr">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:zipCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


 <!--  Reporting Morb Name -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ReporterOfMorbReport']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

 <!--  Reporter of Morbidity Report-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
 <!--  Reporter of Morbidity Report-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='ReporterOfMorbReport']/IM:personName">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ReporterOfMorbReport']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:zipCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

 <!--  Physician of Morbidity Report-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PhysicianOfMorb']/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
 <!--  Physician of Morbidity Report-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PhysicianOfMorb']/IM:personName">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PhysicianOfMorb']/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PhysicianOfMorb']/IM:entityLocator[IM:classCd='PST']/IM:postalLocatorType/IM:zipCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


</xsl:stylesheet>
