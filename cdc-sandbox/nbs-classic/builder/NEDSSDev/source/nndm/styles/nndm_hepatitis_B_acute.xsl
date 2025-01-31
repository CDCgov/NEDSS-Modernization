<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--============================================================-->
   <!--  Description:  This stylesheet transforms NNDM Notification Master -->
   <!--     Message instances into Acute Hepatits B-specific NNDM Messages. -->
   <!--     Standard integrity rules are enforced using templates included in -->
   <!--     nndmIntegrity.xsl. Refer to the Hepatitis Notification Mapping guide for a -->
   <!--     definition of the rules contained in this stylesheet. -->
   <!--  Author: Craig Stanley -->
   <!--                Emergint, Inc. -->
   <!--  Created: 8-19-2002 using Hepatitis MAPPING GUIDE VER 3 Sep 4, 2002 -->
   <!--   Modified 9-17-2002  Remove white space and not beautify -->
   <!--   Modified 9-25-2002  Re-engineer to support new integrity rules -->
   <!--   Modified 10-1-2002  Include Generic Hepatitis rules -->
   <!--   Modified 10-3-2002  Include Generic Hepatitis rules -->
   <!--  Modified 8/1/03 KLCobb - from update document "Hepatitis Notify Msg Mapping for 1_1 FINAL.doc" -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>

   <!--============================================================-->
   <!-- Import the Generic Hepaitits Rules -->
   <!--===========================================================-->
   <xsl:include href="nndm_generic_hepatitis.xsl"/>

   <!--============================================================-->
   <!-- Disease-specific filters -->
   <!--============================================================-->
   <!-- HEP152	CONTACTB -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP152']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP153	BTYPE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP153']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP154	BOTHCON -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP154']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP161	BDIALYSIS -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP161']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP162	BSTICK -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP162']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP163	BTRANS -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP163']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP164	BTRANSDT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP164']/IM:obsValueDate/IM:fromTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP252	BIVOUTPT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP252']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP165	BBLOOD -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP165']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP166	BBLOODTYPE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP166']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP167	BMEDEMP -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP167']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP168	BFREQ1 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP168']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP169	BPUBSAFEMP -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP169']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP170	BFREQ2 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP170']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP171	BTATTOO -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP171']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP172	BTATTOOLOC -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP172']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP173	BTATTOOOTH -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP173']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP174	BPIERCE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP174']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP175	BPIERCELOC -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP175']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP176	BPEIRCEOTH -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP176']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP177	BDENTAL -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP177']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP178	BSURGERY -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP178']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP179	BHOSP -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP179']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP180	BNURSHOME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP180']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP181	BINCAR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP181']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP182	BINCARTYPE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP182']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP159	BIVDRUGS -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP159']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP160	BDRUGS -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP160']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP155	BMALESEX -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP155']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP156	BFEMALESEX -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP156']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP157	BSTD -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP157']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP158	BSTDYR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP158']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP183	BEVERINCAR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP183']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP184	INCARYR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP184']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP185	INCARDUR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP185']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP186	INCARUNIT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP186']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP187	BVACCINE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP187']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP188	BVACCINENO -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP188']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP189	BVACCINEYR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP189']/IM:obsValueNumeric/IM:numericValue1">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP190	BANTIBODY -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP190']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- HEP191	BRESULT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='HEP191']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>
