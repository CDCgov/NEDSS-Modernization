<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
  <!--===============================================================         -->
  <!--  Description:  This stylesheet is included in other stylesheets to match on                 -->
  <!--     selected static elements to meet general integrity rules for the NBS receiver.        -->
  <!--     These static rules are documented in the NBS Integrity Rules document.               -->
  <!--     Elements that match are tagged for inclusion in the resulting document.                 -->
  <!--  Author: Craig Stanley                                                                                                  -->
  <!--                Emergint, Inc.                                                                                                 -->
  <!--  Created: 10-3-2002                                                                                                     -->
  <!--  Modified: 10-7-2002 Added temporary template to pass all observations.                 -->
  <!--  Modified: 10-9-2002 Changes to support version 2 of the                                          -->
  <!--                                   NotificationMasterMessage.xsd Minimal Structural Rules.         -->
  <!--  Modified: 7-11-2003 Added templates for hotfix to remove personal identifiers.       -->
  <!--  Modified: 8-8-2003 Changes to reflect version 2.2 of Object Integrity Rules.             -->
  <!--  Modified: 11-19-2003 added InvestgrOfPHC [BP] -->
  <!--  Modified: 11-20-2003 added Generic Rule #11 reflecting Object Integrity Rules NNDM Release 1.1.2. -->
  <!--  Modified: 01-09-2004 BP hotfix to OrgAsReporterOfPHC Entity issue -->
  <!--  Modified: 03-12-2004 added ABCInvestgrOfPHC ro RULE 5 [BP] -->
  <!--  Modified: 03-26-2004 added five new hotfix rules to remove peron identifiable data -->
  <!--===============================================================         -->
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8"/>

  <!--===============================================================-->
  <!-- VERSION 2.2 STATIC RULES -->
  <!--===============================================================-->

  <!-- ************************************* -->
  <!--         GENERIC RULES                -->
  <!-- ************************************* -->
  <!-- RULES 1 THRU 7: See nndm_integrity.xsl Rule #1 thru #7 -->
  <!-- RULE 8: If NedssEventType = LabReport, pass through all ACTS and sub tables in its entirety -->
    <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Act">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 9: If NedssEventType = Vaccination, pass through all ACTS and sub tables in its entirety -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Act">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 10: If NedssEventType = MorbReport, pass through all ACTS and sub tables in its entirety -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Act">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 10a: If NedssEventType = Notification, rip it out -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Notification']"></xsl:template>

  <!-- RULE 11 : If NedssEventType = CDFData, pass through the entire NNDEvent -->
  <!-- <xsl:template match="IM:NNDEvent[IM:NedssEventType='CDFData']/IM:NedssEvent"> -->
  <!-- <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:CDFData"> -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:CDFData">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|*|comment()"/>
    </xsl:copy>
  </xsl:template>

  <!-- ************************************* -->
  <!--         ENTITY RULES                   -->
  <!-- ************************************* -->
  <!-- RULE 1: For NedssEventType = Investigation, the one instance of PSN with participationTypeCd -->
  <!-- of 'SubjOfPHC' needs to be preserved with at least: -->
  <!--   a. cd  -->
  <!--   b. asOfDateAdmin  -->
  <!--   c. asOfDateEthnicity -->
  <!--   d. asOfDateGeneral -->
  <!--   e. asOfDateMorbidity -->
  <!--   f. asOfDateSex  -->
  <!--   g. electronicInd  -->
  <!--   h. personParentUid  -->
  <!--   i. ageCalc -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:asOfDateAdmin">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:asOfDateEthnicity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:asOfDateGeneral">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:asOfDateMorbidity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:asOfDateSex">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:electronicInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:personParentUid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubjOfPHC']/IM:ageCalc">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 2: For NedssEventType = LabReport, the one instance of PSN with participationTypeCd -->
  <!-- of 'PATSBJ' needs to be preserved with at least: -->
  <!--   a. cd  -->
  <!--   b. asOfDateAdmin  -->
  <!--   c. asOfDateEthnicity -->
  <!--   d. asOfDateGeneral -->
  <!--   e. asOfDateMorbidity -->
  <!--   f. asOfDateSex  -->
  <!--   g. electronicInd  -->
  <!--   h. personParentUid  -->
  <!--   i. ageCalc -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:asOfDateAdmin">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:asOfDateEthnicity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:asOfDateGeneral">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:asOfDateMorbidity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:asOfDateSex">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:electronicInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:personParentUid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='LabReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='PATSBJ']/IM:ageCalc">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 3: For NedssEventType = Vaccination, the one instance of PSN with participationTypeCd -->
  <!-- of 'SubjOfVacc' needs to be preserved with at least: -->
  <!--   a. cd  -->
  <!--   b. asOfDateAdmin  -->
  <!--   c. asOfDateEthnicity -->
  <!--   d. asOfDateGeneral -->
  <!--   e. asOfDateMorbidity -->
  <!--   f. asOfDateSex  -->
  <!--   g. electronicInd  -->
  <!--   h. personParentUid  -->
  <!--   i. ageCalc -->
<!--
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:asOfDateAdmin">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:asOfDateEthnicity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:asOfDateGeneral">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:asOfDateMorbidity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:asOfDateSex">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:electronicInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:personParentUid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Vaccination']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfVacc']/IM:ageCalc">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
-->
 <!-- RULE 4: For NedssEventType = MorbReport, the one instance of PSN with participationTypeCd -->
  <!-- of 'SubjOfMorbReport' needs to be preserved with at least: -->
  <!--   a. cd  -->
  <!--   b. asOfDateAdmin  -->
  <!--   c. asOfDateEthnicity -->
  <!--   d. asOfDateGeneral -->
  <!--   e. asOfDateMorbidity -->
  <!--   f. asOfDateSex  -->
  <!--   g. electronicInd  -->
  <!--   h. personParentUid  -->
  <!--   i. ageCalc -->
<!--
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:asOfDateAdmin">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:asOfDateEthnicity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:asOfDateGeneral">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:asOfDateMorbidity">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:asOfDateSex">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:electronicInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:personParentUid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='MorbReport']/IM:NedssEvent/IM:Entity/IM:person[IM:participationTypeCd='SubOfMorbReport']/IM:ageCalc">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
-->
 <!-- RULE 5: For every instance of participation specified in R1_1_NND_MappingGuide.xls that needs to be passed,  -->
  <!-- the root table, Person, Organization, Material as applicable needs to be passed. -->
  <!--      The root table should be filtered as applicable. -->
  <!-- RULE 6: See nndm_integrity.xsl Rule #8 -->
  <!-- RULE 7: See nndm_integrity.xsl Rule #9 -->
  <!-- RULE 8: See nndm_integrity.xsl Rule #10 -->
  <!-- RULE 9: See nndm_integrity.xsl Rule #11 -->
  <!-- RULE 10: See nndm_integrity.xsl Rule #12 -->
  <!-- RULE 11: See nndm_integrity.xsl Rule #13 -->

<!-- Participations at the time this code was written were: -->
<!-- ChronicCareFac -->
<!-- DaycareFac -->
<!-- HospOfADT -->
<!-- HospOfBirth -->
<!-- HospOfCulture -->
<!-- OrgAsReporterOfPHC -->
<!-- ReAdmHosp -->
<!-- SubjOfPHC -->
<!-- PATSBJ -->
<!-- SubOfVacc -->
<!-- SubjOfMorbReport -->
<!-- TransferHosp -->
<!-- InvestgrOfPHC [added 11/19/2003 BP] -->
<!-- ABCInvestgrOfPHC [added 03/12/2004 BP] -->
<!-- PhysicianOfPHC [added 03/19/2004 BP] -->

<!-- ChronicCareFac -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ChronicCareFac']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- DaycareFac -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='DaycareFac']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- HospOfADT -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfADT']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- HospOfBirth -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfBirth']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- HospOfCulture -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='HospOfCulture']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- OrgAsReporterOfPHC -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='OrgAsReporterOfPHC']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- ReAdmHosp -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='ReAdmHosp']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- SubjOfPHC -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='SubjOfPHC']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- PATSBJ -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PATSBJ']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- SubOfVacc -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='SubOfVacc']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- SubjOfMorbReport -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='SubjOfMorbReport']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- TransferHosp -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:organization/IM:participationTypeCd='TransferHosp']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

<!-- InvestgrOfPHC -->
<!--
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='InvestgrOfPHC']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
 -->
<!-- ABCInvestgrOfPHC -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='ABCInvestgrOfPHC']">
      <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
      </xsl:copy>
  </xsl:template>

<!-- PhysicianOfPHC -->
<xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Entity[IM:person/IM:participationTypeCd='PhysicianOfPHC']">
      <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
      </xsl:copy>
</xsl:template>

  <!-- ************************************* -->
  <!--               ACT RULES                 -->
  <!-- ************************************* -->
  <!--  RULE 1: For NedssEventType = Investigation, the one instance PublicHealthCase needs to be preserved with at least:  -->
  <!-- a. caseClassCd -->
  <!-- b. caseTypeCd -->
  <!-- c. investigationStatusCd -->
  <!-- d. jurisdictionCd -->
  <!-- e. mmwrWeek -->
  <!-- f. mmwrYear -->
  <!-- g. progAreaCd -->
  <!-- h. programJurisdictionOid -->
  <!-- i. sharedInd -->
  <!-- j. cd -->
  <!-- k. cdDescTxt -->
  <!-- l. versionCtrlNbr -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:caseClassCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:caseTypeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:investigationStatusCd ">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:jurisdictionCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:mmwrWeek">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:mmwrYear">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:progAreaCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:programJurisdictionOid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:sharedInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:cdDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:versionCtrlNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <!--  RULE 1A: For NedssEventType = SummaryReport, the one instance PublicHealthCase needs to be preserved with at least:  -->
  <!-- a. caseClassCd -->
  <!-- b. caseTypeCd -->
  <!-- c. investigationStatusCd -->
  <!-- d. jurisdictionCd -->
  <!-- e. mmwrWeek -->
  <!-- f. mmwrYear -->
  <!-- g. progAreaCd -->
  <!-- h. programJurisdictionOid -->
  <!-- i. sharedInd -->
  <!-- j. cd -->
  <!-- k. cdDescTxt -->
  <!-- l. versionCtrlNbr -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:caseClassCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:caseTypeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:investigationStatusCd ">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:jurisdictionCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:mmwrWeek">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:mmwrYear">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:progAreaCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:programJurisdictionOid">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:sharedInd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:cdDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:versionCtrlNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>



  <!--  RULE 2: Within NNDEvent for all NedssEventType = "Investigation" the following needs to be preserved:  -->
        <!-- a. The instance of ActRelationship with typeCd = "PHCInvForm" -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation']/IM:NedssEvent/IM:ActRelationship[IM:typeCd='PHCInvForm']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 3: -->
  <!-- Within NNDEvent for all NedssEventType="Investigation" the following needs to be preserved: -->
  <!-- The instance of ActRelationship with typeCd = SummaryForm  -->
  <xsl:template match="IM:NNDEvent[IM:NedssEventType='Investigation' or  IM:NedssEventType='SummaryReport']/IM:NedssEvent/IM:ActRelationship[IM:typeCd='SummaryForm']">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 4: -->
  <!-- Pass through Observations if Observation.cd equals the following: -->
  <!-- INV_FORM_BMDGAS -->
  <!-- INV_FORM_BMDGBS -->
  <!-- INV_FORM_BMDGEN -->
  <!-- INV_FORM_BMDHI -->
  <!-- INV_FORM_BMDNM -->
  <!-- INV_FORM_BMDSP -->
  <!-- INV_FORM_CRS -->
  <!-- INV_FORM_GEN -->
  <!-- INV_FORM_HEPA -->
  <!-- INV_FORM_HEPB -->
  <!-- INV_FORM_HEPBV -->
  <!-- INV_FORM_HEPC -->
  <!-- INV_FORM_HEPCV -->
  <!-- INV_FORM_HEPGEN -->
  <!-- INV_FORM_MEA -->
  <!-- INV_FORM_PER -->
  <!-- INV_FORM_RUB -->
  <!-- SummaryReportForm -->
  <!-- ItemToRow -->
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_GEN']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDGAS']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDGBS']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDGEN']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDHI']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDNM']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_BMDSP']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_CRS']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPA']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPB']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPBV']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPC']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPCV']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_HEPGEN']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_MEA']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_PER']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='INV_FORM_RUB']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='Summary_Report_Form']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:NNDEvent/IM:NedssEvent/IM:Act[IM:observation[IM:cd='ItemToRow']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <!-- RULE 5: -->
  <!-- For the below references to Observation subtables in R1_1 NND_MappingGuide.xls, pass the: -->
  <!-- Observation corresponding to Observation.cd with the unique name. -->
  <!-- Example, R1_1 NND_MappingGuide.xls contains BMD100 : Obs_value_coded.code. In addition,  -->
  <!-- pass Observation table (in its entirety) where Observation.cd = BMD100. -->
  <!--      Observation_Interp  -->
  <!--      Observation_Reason  -->
  <!--      Obs_Value_Coded  -->
  <!--      Obs_Value_Coded_Mod  -->
  <!--      Obs_Value_Date  -->
  <!--      Obs_Value_Numeric  -->
  <!--      Obs_Value_Txt  -->

  <!-- RULE 6: -->
  <!-- For every ActID that's passed through, ActIdSeq needs to be included -->
  <!-- See nndm_integrity.xsl Rule #14 -->

  <!-- RULE 7: -->
  <!-- For every ObservationReason that's passed through, ReasonCd needs to be included -->
  <!-- See nndm_integrity.xsl Rule #15 -->

  <!-- RULE 8: -->
  <!-- For every ObservationInterp that's passed through, InterpretationCd needs to be included, InterpretationDescTxt needs to be included -->
  <!-- See nndm_integrity.xsl Rule #16 -->

  <!-- RULE 9: -->
  <!-- For every ObsValueCodedMod that's passed through, Code needs to be included, CodeModCd needs to be included -->
  <!-- See nndm_integrity.xsl Rule #17 -->

  <!-- RULE 10: -->
  <!-- For every ObsValueCoded that's passed through, Code needs to be included -->
  <!-- See nndm_integrity.xsl Rule #18 -->

  <!-- RULE 11: -->
  <!-- For every ObsValueTxt that's passed through, ObsValueTxtSeq needs to be included -->
  <!-- See nndm_integrity.xsl Rule #19 -->

  <!-- RULE 12: -->
  <!-- For every ObsValueDate that's passed through, ObsValueDateSeq needs to be included -->
  <!-- See nndm_integrity.xsl Rule #20 -->

  <!-- RULE 13: -->
  <!-- For every ObsValueNumeric that's passed through, ObsValueNumericSeq needs to be included -->
  <!-- See nndm_integrity.xsl Rule #21 -->

  <!-- RULE 14: -->
  <!-- For every ConfirmationMethod that's passed through, ConfirmationMethodCd needs to be included -->
  <!-- See nndm_integrity.xsl Rule #22 -->



  <!--  ======================================  -->
  <!--                                   HOTFIX                                 -->
  <!--  ======================================  -->
  <!--  The following templates are to remove sensitive -->
  <!--  elements that erroneosly contain patient              -->
  <!--  identifiers when the NBS user enters field data  -->
  <!--  into incorrect fields.                                              -->
  <!-- The implementation guides specify distinct           -->
  <!--  elements to keep. If prior processing didn't tag   -->
  <!--  with a keep attribute value of 1 for the following -->
  <!--  then throw it out.                                                   -->
  <!--  ======================================  -->
  <xsl:template match="//IM:person/IM:mothersMaidenNm[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:firstNm[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:lastNm[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:middleNm[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:nmPrefix[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:nmSuffix[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person//IM:preferredNm[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmStreetAddr1[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmStreetAddr2[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmCityCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmCityDescTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmStateCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmZipCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmCntyCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmCntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmPhoneNbr[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmPhoneCntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:hmEmailAddr[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:cellPhoneNbr[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkStreetAddr1[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkStreetAddr2[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkCityCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkCityDescTx[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkStateCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkZipCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkCntyCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkCntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkPhoneNbr[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkPhoneCntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:wkEmailAddr[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:SSN[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:medicaidNum[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:dlNum[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:dlStateCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:birthCityCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:birthCityDescTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:birthCntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:birthStateCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:personName[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:postalLocatorType[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:birthTime[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:currSexCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:ethnicGroupInd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:ageReported[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:ageReportedUnitCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:person/IM:description[@keep!=1 or not(@keep)]"/> 
  <xsl:template match="//IM:Entity[IM:person]/IM:entityId/IM:assigningAuthorityCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity/IM:entityId/IM:assigningAuthorityDescTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityId/IM:rootExtensionTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityId/IM:typeCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityId/IM:typeDescTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:teleLocatorType/IM:phoneNbrTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:cityCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:cityDescTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:streetAddr1[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:streetAddr2[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:cntyCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:stateCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:zipCd[@keep!=1 or not(@keep)]"/>
  <!-- inserted 03/26/04 - specific request to remove those as well -->
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:postalLocatorType/IM:cntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:teleLocatorType/IM:cntryCd[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:teleLocatorType/IM:emailAddress[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:teleLocatorType/IM:extensionTxt[@keep!=1 or not(@keep)]"/>
  <xsl:template match="//IM:Entity[IM:person]/IM:entityLocator/IM:teleLocatorType/IM:urlAddress[@keep!=1 or not(@keep)]"/>
  <!-- inserted 03/26/04 -->
  <xsl:template match="//IM:person[IM:participationTypeCd!='SubjOfPHC']/IM:personRace"/>
</xsl:stylesheet>