<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS" exclude-result-prefixes="xsl IM">
  <!--============================================================-->
  <!-- Style to determine the disease type and transform using a                          -->
  <!-- static, disease-specific stylesheet.                                                               -->
  <!-- The style starts processing at the root and interprets the                            -->
  <!-- disease-type, which is assigned to the variable                                           -->
  <!-- $diseaseTypeCode.                                                                                       -->
  <!--                                                                                                                        -->
  <!-- New disease-types can be added by adding to the xsl:choose                   -->
  <!-- in the template matching on NNDEvent, and adding the                                 -->
  <!-- corresponding style.                                                                                      -->
  <!-- to the styles directory relative to this stylesheet.                                          -->
  <!--  Author: Craig Stanley                                                                                    -->
  <!--                Emergint, Inc.                                                                                   -->
  <!--  Date: 8-15-2002                                                                                             -->
  <!--============================================================-->
  <xsl:output encoding="UTF-8" omit-xml-declaration="yes"/>
  <!--============================================================-->
  <!-- Start at the root to begin the processing.                                                      -->
  <!--============================================================-->
  <xsl:template match="/">
    <xsl:apply-templates select="IM:Investigation_Message"/>
  </xsl:template>
  <!--============================================================-->
  <!-- Evaluate the disease type and make a java call to                                         -->
  <!-- transform using the equivalent disease-specific                                           -->
  <!-- stylesheet.                                                                                                      -->
  <!-- To add another disease-specific transformation, add a test                         -->
  <!-- case to the xsl:choose block below.                                                             -->
  <!--============================================================-->
  <xsl:template match="IM:Investigation_Message">
    <xsl:variable name="messageType" select="IM:MessageHeader/IM:interactionId"/>
    <xsl:variable name="diseaseTypeCode" select="IM:NNDEvent/IM:NedssEvent/IM:Act/IM:publicHealthCase/IM:cd"/>
    <!-- To add another disease-specific transformation, add the mapping here.   -->
    <!-- Refer to comments at the bottom of this document for textual descriptions of each mapped code -->
    <xsl:variable name="xslURI">
      <xsl:choose>
        <xsl:when test="$messageType='CDCNND3'">nndm_summary.xsl</xsl:when>
        <xsl:when test="$messageType='CDCNND1' or $messageType='CDCNND2' or $messageType='CDCNND4'">
          <xsl:choose>
            <!-- MEASLES -->
            <xsl:when test="$diseaseTypeCode='10140'">nndm_measles.xsl</xsl:when>
            <!-- CRS -->
            <xsl:when test="$diseaseTypeCode='10370'">nndm_crs.xsl</xsl:when>
            <!-- PERTUSSIS -->
            <xsl:when test="$diseaseTypeCode='10190'">nndm_pertussis.xsl</xsl:when>
            <!-- RUBELLA -->
            <xsl:when test="$diseaseTypeCode='10200'">nndm_rubella.xsl</xsl:when>
            <!-- STD -->
            <xsl:when test="$diseaseTypeCode='10273'
                                   or $diseaseTypeCode='10274'
                                   or $diseaseTypeCode='10276'
                                   or $diseaseTypeCode='10280'
                                   or $diseaseTypeCode='10306'
                                   or $diseaseTypeCode='10307'
                                   or $diseaseTypeCode='10308'
                                   or $diseaseTypeCode='10309'
                                   or $diseaseTypeCode='10311'
                                   or $diseaseTypeCode='10312'
                                   or $diseaseTypeCode='10313'
                                   or $diseaseTypeCode='10314'
                                   or $diseaseTypeCode='10315'
                                   or $diseaseTypeCode='10316'
                                   or $diseaseTypeCode='10317'
                                   or $diseaseTypeCode='10318'">nndm_std.xsl</xsl:when>
            <!-- SUMMARY -->
            <xsl:when test="$diseaseTypeCode='SUMMARY'">nndm_summary.xsl</xsl:when>
            <!-- GENERIC HEPATITIS -->
            <xsl:when test="$diseaseTypeCode='10102' 
                                  or $diseaseTypeCode='10105' 
                                  or $diseaseTypeCode='10103' 
                                  or $diseaseTypeCode='10481'">nndm_generic_hepatitis.xsl</xsl:when>
            <!-- ACUTE HEPATITIS B -->
            <xsl:when test="$diseaseTypeCode='10100'">nndm_hepatitis_B_acute.xsl</xsl:when>
            <!-- ACUTE HEPATITIS C -->
            <xsl:when test="$diseaseTypeCode='10101'">nndm_hepatitis_C_acute.xsl</xsl:when>
            <!-- ACUTE HEPATITIS B VIRAL -->
            <xsl:when test="$diseaseTypeCode='10104'">nndm_hepatitis_B_virus_infection_prenatal.xsl</xsl:when>
            <!-- ACUTE HEPATITIS C VIRAL -->
            <xsl:when test="$diseaseTypeCode='10106'">nndm_hepatitis_C_virus_infection_chronic_or_resolved.xsl</xsl:when>
            <!-- ACUTE HEPATITIS A -->
            <xsl:when test="$diseaseTypeCode='10110'">nndm_hepatitis_A_acute.xsl</xsl:when>
            <!-- GENERIC BMIRD Modified 02/01/2011 civil00020549 -->
            <xsl:when test="$diseaseTypeCode='10650'
                                  or $diseaseTypeCode='11716'">nndm_generic_BMIRD.xsl</xsl:when>
            <!-- NEISSERIA MENINGITIDES -->
            <xsl:when test="$diseaseTypeCode='10150'">nndm_neisseria_meningitides.xsl</xsl:when>
            <!-- HAEMOPHILUS INFLUENZA -->
            <xsl:when test="$diseaseTypeCode='10590'">nndm_haemophilus_influenzae.xsl</xsl:when>
            <!-- STREPTOCOCCUS B -->
            <xsl:when test="$diseaseTypeCode='11715'">nndm_group_B_streptococcus.xsl</xsl:when>
            <!-- STREPTOCOCCUS A Modified 02/01/2011 civil00020549-->
            <xsl:when test="$diseaseTypeCode='11710'
                                  or $diseaseTypeCode='11700'">nndm_group_A_streptococcus.xsl</xsl:when>
            <!-- STREPTOCOCCAL PNEUMONIAE Modified 02/01/2011 civil00020549-->
            <xsl:when test="$diseaseTypeCode='11717'
                                  or $diseaseTypeCode='11720'
                                  or $diseaseTypeCode='11723'">nndm_streptococcal_pneumoniae.xsl</xsl:when>
            <!-- GENERIC -->
            <xsl:otherwise>nndm_generic.xsl</xsl:otherwise>
          </xsl:choose>
        </xsl:when>
      </xsl:choose>
    </xsl:variable>
    <!-- Now that we know the disease type, process the document using the disease-specific style -->
    <xsl:value-of select="string($xslURI)"/>
  </xsl:template>
</xsl:stylesheet>
<!--
********************************
MAPPING DESCRIPTIONS
********************************
Generic
==================================================
Anything not listed below.

Measles 
==================================================
cd = 10140, condition_desc_txt = Measles(Rubeola)

CRS 
==================================================
cd = 10370, condition_desc_txt = Rubella, Congential Syndrome

Pertussis 
==================================================
cd = 10190, condition_desc_txt =Pertussis

Rubella 
==================================================
cd = 10200, condition_desc_txt =Rubella

STD
==================================================
cd = 10273                Chancroid
cd = 10274                Chlamydia trachomatis genital infection
cd = 10276                Granuloma inguinale (GI)
cd = 10280                Gonorrhea
cd = 10306                Lymphogranuloma venereum (LGV)
cd = 10307                Nongonococcal urethritis (NGU)
cd = 10308                Mucopurulent cervicitis (MPC)
cd = 10309                Pelvic Inflammatory Disease (PID), Unknown Etiology
cd = 10311                Syphilis, primary
cd = 10312                Syphilis, secondary
cd = 10313                Syphilis, early latent
cd = 10314                Syphilis, late latent
cd = 10315                Syphilis, unknown latent
cd = 10317                Neurosyphilis
cd = 10318                Syphilis, late with clinical manifestations other


Summary
==================================================
TODO: Still Undefined

Generic Hepatitis 
==================================================
cd = 10102, condition_desc_txt = Hepatitis Delta, Co- or Super-Infection 
cd = 10105, condition_desc_txt = Hepatitis B virus infection, Chronic 
cd = 10103, condition_desc_txt = Hepatitis E, Acute 
cd = 10481, condition_desc_txt = Hepatitis Non-ABC, Acute 

Hepatitis B, Acute 
==================================================
cd = 10100 condition_desc_txt = Hepatitis B, Acute 

Hepatitis C, Acute 
==================================================
cd = 10101 condition_desc_txt = Hepatitis C, Acute 

Hepatitis B, virus infection, perinatal 
==================================================
cd = 10104 condition_desc_txt = Hepatitis B Viral Infection, Perinatal 

Hepatitis C, virus infection, chronic or  resolved 
==================================================
cd = 10106 condition_desc_txt = Hepatitis C Virus Infection, chronic or resolved 

Hepatitis A, Acute 
==================================================
cd = 10110 condition_desc_txt = Hepatitis A, Acute Generic 

BMIRD 
==================================================
cd = 10650 condition_desc_txt = Bacterial meningitis, other 

Neisseria Meningitides 
==================================================
cd = 10150 condition_desc_txt = Neisseria meningitidis, invasive (Meningococcal disease) 

Haemophilus influenzae
==================================================
cd = 10590 condition_desc_txt = Haemophilus influenzae, invasive

Group B Streptococcus
==================================================
cd = 11715 condition_desc_txt = Group B Streptococcus, invasive

Group A Streptococcus
==================================================
cd = 11710 condition_desc_txt = Group A Streptococcus, invasive

Streptococcal pneumoniae 
==================================================
cd = 11717  condition_desc_txt =  Streptococcus pneumoniae, invasive
-->
