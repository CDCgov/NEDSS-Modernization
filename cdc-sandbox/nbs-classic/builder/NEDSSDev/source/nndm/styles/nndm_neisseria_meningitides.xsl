<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--===============================================================-->
   <!--  Description:  This stylesheet transforms NNDM Notification Master -->
   <!--     Message instances into Neisseria meningitidis-specific NNDM Messages. -->
   <!--     Standard integrity rules are enforced using templates included in -->
   <!--     nndmIntegrity.xsl. Refer to the BMIRD Notification Mapping guide for a -->
   <!--     definition of the rules contained in this stylesheet. -->
   <!--  Author: Craig Stanley -->
   <!--                Emergint, Inc. -->
   <!--  Created: 9-9-2002 using BMIRD MAPPING GUIDE VER 6 Sep 4, 2002 -->
   <!--  Modified 9-17-2002  Remove white space and not beautify -->
   <!--  Modified 9-25-2002  Re-engineer to support new integrity rules -->
   <!--  Modified 10-3-2002  Import Generic BMIRD rules -->
   <!--  Modified 8/4/2003   KLCobb - from update document "BMIRD Notif Msg Mapping for 1_1 FAINL.doc"-->
   <!--  Modified 12/31/2003 BPlume - updated BMD134 for R1.1.2.-->
   <!--  Modified 02/02/2004 BPlume - updated for R1.1.3. : delete BMD168, submitted request for BMD161 -->
   <!--  Modified 02/03/2004 BPlume - [R 1.1.3. revision 1] BMD161 XPath specified -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>

   <!--============================================================-->
   <!-- Import the Generic BMIRD Rules -->
   <!--============================================================-->
   <xsl:include href="nndm_generic_BMIRD.xsl"/>

   <!--============================================================-->
   <!-- Disease-specific filters -->
   <!--============================================================-->
   <!-- BMD133	SEROGROUP -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD133']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD134	OTHSERO -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD134']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD161	CASEID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD161']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD162	OTHSTRST -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD162']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD163	OTHID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD163']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD271	SECCASE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD271']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD272	SECCASETY -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD272']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD273	OTHSECCASE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD273']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD274	NMSULFRES -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD274']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD275	NMRIFARES -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD275']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
   

      <!-- BMD307	DIAGDATE-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD307']/IM:obsValueDate">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
    <!-- BMD308	PCRSOURCE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD308']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
    <!-- BMD309	IHCSPEC1-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD309']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
      <!-- BMD310	IHCSPEC2-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD310']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
      <!-- BMD311	IHCSPEC3-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD311']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
       <!-- BMD313  MENGVAC-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD313']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   

   
   
   <!-- BMD135	COLLEGE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD135']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD164	SCHOOLYR -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD164']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD165	STUDTYPE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD165']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD166	HOUSE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD166']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD167	OTHHOUSE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD167']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD168	SCHOOLNM -->
   <!-- BPlume 10/07/2003 deleted - can be found in BMIRD_generic -->
   <!-- BMD169	POLYVAC -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD169']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>