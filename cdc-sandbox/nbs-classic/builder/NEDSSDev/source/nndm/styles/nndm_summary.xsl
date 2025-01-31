<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--==================================================================-->
   <!--  Description:  This stylesheet transforms NNDM Notification Master -->
   <!--     Message instances into Summary-specific NNDM Messages. -->
   <!--     Standard integrity rules are enforced using templates included in -->
   <!--     nndmIntegrity.xsl. Refer to the Summary Notification Mapping guide for a -->
   <!--     definition of the rules contained in this stylesheet. -->
   <!--  Author: Craig Stanley -->
   <!--                Emergint, Inc. -->
   <!--  Created: 9-9-2002 using Summary MAPPING GUIDE VER 1 Sep 1, 2002 -->
   <!--   Modified 9-17-2002  Remove white space and not beautify -->
   <!--   Modified 9-25-2002  Re-engineer to support new integrity rules -->
   <!--   Modified 10-3-2002  Import Generic rules -->
   <!--   Modified 7/31/2003  Updated per 'Summary Notif MsgMapping for 1_1 FINAL.doc' file -->
   <!--   Modified 09/10/2003 Updated per 'Summary Notif MsgMapping for 1_1 FINAL 2.doc' file, NedssEventType Changes -->
   <!--   Modified 12/30/2003 BPlume added INV108 and INV163 for R1.1.2. -->
   <!--   Modified 01/09/2004 BPlume added INV168,INV169 soft delete of SUM108,SUM109,SUM111. -->
   <!--   Modified 01/15/2004 BPlume changed SUM107 XPath match to reflect 1.1.2. v2 documentation -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>
   <!--============================================================-->
   <!--   Import the Static Integrity Rules                        -->
   <!--============================================================-->
   <xsl:include href="nndm_integrity_static.xsl"/>
   <!--============================================================-->
   <!--   Start at the root to begin the processing.               -->
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
   <!--  Disease-specific filters                                  -->
   <!--============================================================-->
   <!-- INV187	Case Report County Code -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:rptCntyCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- INV165	MMWR Week -->
   <!-- included in nndm_integrity -->

   <!-- INV166	MMWR Year -->
   <!-- included in nndm_integrity -->

   <!-- INV169	Condition Code -->
   <!-- included in nndm_integrity -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:cd">
     <xsl:copy>
       <xsl:attribute name="keep">1</xsl:attribute>
       <xsl:apply-templates/>
     </xsl:copy>
   </xsl:template>
   <!-- INV164	Case Group Case Count -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:groupCaseCnt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- SUM107	Total Count -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='SUM107']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- SUM108	Last Updated -->
   <!-- deleted as requested 01/09/04
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:lastChgTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- SUM109	Sent Date (NEED TO CONFIRM ACCURACY OF FINAL TWO ELEMENT< THEY ARE NOT IN THE SCHEMA -->
   <!-- deleted as requested 01/09/04
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport' or IM:NedssEventType='Notification']/IM:NedssEvent/IM:Act/IM:notification/IM:rptSentTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- SUM111	Sent Date (NEED TO CONFIRM ACCURACY OF FINAL TWO ELEMENT< THEY ARE NOT IN THE SCHEMA -->
    <!-- deleted as requested 01/09/04
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or IM:NedssEventType='SummaryReport' or IM:NedssEventType='Notification']/IM:NedssEvent/IM:Act/IM:notification/IM:recordStatusCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   -->
   <!-- SUM103	Source -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='SUM103']/IM:obsValueCoded/IM:code">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- SUM104	Count -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='SUM104']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- SUM105	Comments -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='SUM105']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- INV108	Case Program Area Code -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:progAreaCd">
     <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
     </xsl:copy>
   </xsl:template>
   <!-- INV163	Case Class Status Code -->
   <!-- Integrity includes this template -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:caseClassCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
   </xsl:template>
   <!-- NTF122	NEDSS Notification Version Number -->
   <!-- included in nndm_integrity -->
   <!-- INV168	Investigation Local ID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:publicHealthCase/IM:localId">
     <xsl:copy>
       <xsl:attribute name="keep">1</xsl:attribute>
       <xsl:apply-templates/>
     </xsl:copy>
   </xsl:template>
</xsl:stylesheet>
