<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
   <!--============================================================-->
   <!--  Description:  This stylesheet transforms NNDM Notification Master -->
   <!--     Message instances into BMIRD Generic-specific NNDM Messages. -->
   <!--     Standard integrity rules are enforced using templates included in -->
   <!--     nndmIntegrity.xsl. Refer to the BMIRD Notification Mapping guide for a -->
   <!--     definition of the rules contained in this stylesheet. -->
   <!--  Author: Craig Stanley -->
   <!--                Emergint, Inc. -->
   <!--  Created: 9-9-2002 using BMIRD MAPPING GUIDE VER 6 Sep 4, 2002 -->
   <!--   Modified 9-17-2002  Remove white space and not beautify -->
   <!--   Modified 9-25-2002  Re-engineer to support new integrity rules -->
   <!--   Modified 10-3-2002  Import Generic rules -->
   <!--  Modified 8/1/2003 KLCobb - from update document "BMIRD Notify Mapping for 1_1 FINAL.doc" -->
   <!--  Modified 09/04/2003 BPlume - according to Status Tracking Issues 51-54 -->
   <!--  Modified 10/06/2003 BPlume - BMD129/114/117 fixes according to current specification -->
   <!--  Modified 11/18/2003 BPlume - INV147 Investigation Start Date added -->
   <!--  Modified 11/18/2003 BPlume - INV147 Investigation moved to nndm_generic.xsl -->
   <!--  Modified 12/03/2003 BPlume - added two keeper entities at bottom : PerAsReporterOfPHC and PhysicianOfPHC-->
   <!--  Modified 12/29/2003 BPlume - Updated several elements according to R1.1.2. specs -->
   <!--  Modified 01/30/2004 BPlume - Updates according to R1.1.3. specs until 02/02/2004  see  "NEW SECTION for R.1.1.3." -->
   <!-- changed INV134, removed BMD110 -->
   <!--  Modified 03/09/2004 BPlume - Updates according to R1.1.3. specs : changed partType for BMD302 through BMD306 -->
   <!--  Modified 03/25/2004 BPlume - Updates R1.1.3. specs : soft delete of BMD302 through BMD306 to comply with hotfix-->
   <!--  Modified 03/29/2004 BPlume - BMDXXX testing of ABCInvestgrOfPHC keep for BMIRD only - see entity rule 5 in integrity static -->
   <!--  Modified 03-02-2005 SC - INV132, INV133, INV134, INV153, INV154, INV155, INV156, INV161 are removed as they are added to nndm_generic.xsl -->
   <!--============================================================-->
   <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>
   <xsl:strip-space elements="*"/>
   <!--============================================================-->
   <!--                         Import the Generic Rules -->
   <!--============================================================-->
   <xsl:include href="nndm_generic.xsl"/>
   <!--============================================================-->
   <!--                                             Disease-specific filters -->
   <!--============================================================-->
   <!-- BMD100	ABCSCASE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD100']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD101	STATEID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='CASE']/IM:actId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- INV147	Investigation Start Date -->
   <!-- moved to nndm_generic.xsl -->
   <!-- INV110	Investigation Date Assigned -->
   <!-- 09/18 BP added specifically for BMIRD to pull the entity of InvestgrOfPHC -->
   <!-- Integrity includes this template -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='InvestgrOfPHC']/IM:cd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- INV111	Investigation Reporting Source Date -->
   <!-- removed to generic -->
   <!-- INV118	Investigation Reporting Source Zip -->
   <!-- 09/04/2003 BP changed participationTypeCd
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='PrgAsReporterOfPHC']/IM:zipCd"> -->
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
   <!-- INV138	Investigation Illness End Date -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:effectiveToTime">
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
   <!-- BMD102	HOSPID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfCulture']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD279	HOSPNM -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='HospOfCulture']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD280	HOSPIDTY -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfCulture']/IM:entityId/IM:typeCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD103	TRANSFER -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD103']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD104	TRANSID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='TransferHosp']/IM:entityId/IM:rootExtensionTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD278	TRANSIDTY (Hospital Id Type) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='TransferHosp']/IM:entityId/IM:typeCd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD277	TRANSNM (Hospital Name) -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='TransferHosp']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- BMD270	BIRTH (Deleted per update doc on 8/1/2003 -->

   <!-- BMD118	SYNDRM -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD118']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD119	SPECSYN -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD119']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD120	SPECIES -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD120']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD121	OTHBUG1 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD121']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD268	OTHOTHSPC -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD268']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD122	STERSITE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD122']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD123	OTHSTER -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD123']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD124	DATE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:diagnosisTime">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD125	NONSTER -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD125']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD126	UNDERCOND -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD126']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD127	COND -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD127']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD128	OTHMALIG -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD128']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD129	OTHORGAN -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD129']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD130	OTHILL -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD130']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD111	PREGNANT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD111']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD112	FOUTCOME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD112']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD113	UNDER1MNTH -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD113']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD267	BIRTHTIME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD267']/IM:obsValueDate/IM:durationAmt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD114	GESTAGE -->
   <!-- changed 10/06 by BP
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD114']/IM:obsValueNumeric/IM:numericValue1">
   -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD114']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD115	BWGHT -->
   <!-- changed 08/26 by BP
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD115']/IM:obsValueNumeric/IM:numericValue1">
   -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD115']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD116	BWGHTLB -->
   <!-- changed 08/26 by BP
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD116']/IM:obsValueNumeric/IM:numericValue1">
   -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD116']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD117	BWGHTOZ -->
    <!-- changed 10/06 by BP
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD117']/IM:obsValueNumeric/IM:numericValue1">
    -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD117']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD109	OUTCOME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:outcomeCd">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
   </xsl:template>
   <!-- BMD105	DAYCARE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD105']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD106	FACNAME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='DaycareFac']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD 106 additional : BP added 09/04/2003, changed 09/10/2003 -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='DaycareFac']/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD107	NURSHOME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD107']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD108	NHNAME -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='ChronicCareFac']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
        <!-- BMD 108 additional : BP added 09/04/2003, changed 09/10/2003-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization[IM:participationTypeCd='ChronicCareFac']]/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD150	AUDIT -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD150']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD151	RELAPSE -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD151']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD152	PREVID -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD152']/IM:obsValueText">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BMD269	STATUS -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD269']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>

   <!-- NEW SECTION for R.1.1.3. version below, added 01/30 through 02/03/04 -->
   <!-- BMD292	If polymicrobial ABCs case, ... -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD292']/IM:obsValueCoded">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
    <!-- BMD293	Specify Other 1 -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD293']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    <!-- BMD294	Specify Other 2 -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD294']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    <!-- BMD295	Specify Internal Body Site -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD295']/IM:obsValueCoded">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    <!-- BMD296	Other Prior Illness 2 -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD296']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    <!-- BMD297	Other Prior Illness 3 -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD297']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    <!-- BMD298	Other Nonsterile Site -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD298']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
    
    
     <!-- BMD312	INSURANCE -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD312']/IM:obsValueCoded">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>

    
       <!-- BMD314	TRTHOSPNM -->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='TreatmentHosp']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD317	INSURANCEOTH -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD317']/IM:obsValueText">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
     
     <!-- BMD320	WEIGHTLB-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD320']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD321	WEIGHTOZ-->  
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD321']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD322	WEIGHTKG-->  
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD322']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD323	HEIGHTFT-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD323']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD324	HEIGHTIN-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD324']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD325	HEIGHTCM-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD325']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD326	WEIGHTUNK-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD326']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
        <!-- BMD327	HEIGHTUNK-->
     <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act[IM:classCd='OBS']/IM:observation[IM:cd='BMD327']/IM:obsValueNumeric">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   
 
    
    <!-- InvestgrOfPHC data pulled above - see INV110 - , here explicit call again -->
    <!-- BMD302	ABCs Investigator Last Name - partType changed from InvestgrOfPHC to ABCInvestgrOfPHC -->
   <!-- removed on 03/25 to comply with CDC hotfix rules
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='ABCInvestgrOfPHC']/IM:lastNm">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
    <!-- BMD303	ABCs Investigator First Name - partType changed from InvestgrOfPHC to ABCInvestgrOfPHC  -->
   <!-- removed on 03/25 to comply with CDC hotfix rules
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='ABCInvestgrOfPHC']/IM:firstNm">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
    <!-- BMD304	ABCs Investigator Email - partType changed from InvestgrOfPHC to ABCInvestgrOfPHC -->
   <!-- removed on 03/25 to comply with CDC hotfix rules
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ABCInvestgrOfPHC']/IM:entityLocator/IM:teleLocatorType/IM:emailAddress">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
    <!-- BMD305	ABCs Investigator Telephone - partType changed from InvestgrOfPHC to ABCInvestgrOfPHC -->
   <!-- removed on 03/25 to comply with CDC hotfix rules
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ABCInvestgrOfPHC']/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
    <!-- BMD306	ABCs Investigator Ext. - partType changed from InvestgrOfPHC to ABCInvestgrOfPHC  -->
    <!-- removed on 03/25 to comply with CDC hotfix rules
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ABCInvestgrOfPHC']/IM:entityLocator/IM:teleLocatorType/IM:extensionTxt">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
    <!-- BMDXXX	keeping ABCs Investigator entity  -->
    <!-- TODO : uncomment for Investigation / BMIRD filtering ONLY  (see integrity_static RULE 5 [entity] )
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ABCInvestgrOfPHC']">
       <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
       </xsl:copy>
    </xsl:template>
   -->
   <!-- OLD SECTION below -->
   <!-- NOT106	Date Sent -->
   <!-- TODO:  IMPLEMENTATION GUIDE INCOMPLETE FOR THIS ELEMENT -->
   <!-- BMD110	DEATHDATE DELETED for R.1.1.3. -->
   <!-- BMD168	SCHOOLNM -->
   <!-- BP added 09/10/2003-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:organization[IM:participationTypeCd='CollegeUniversity']/IM:organizationName/IM:nmTxt">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BP added 09/10/2003-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='CollegeUniversity']/IM:entityId">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <!-- BP added 12/03/2003-->
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PerAsReporterOfPHC']/IM:person/IM:cd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
   <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PhysicianOfPHC']/IM:person/IM:cd">
      <xsl:copy>
         <xsl:attribute name="keep">1</xsl:attribute>
         <xsl:apply-templates/>
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>