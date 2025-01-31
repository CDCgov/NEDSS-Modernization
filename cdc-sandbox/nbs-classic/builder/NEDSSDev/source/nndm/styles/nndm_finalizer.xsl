<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS">
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8" indent="yes"/>
  <xsl:strip-space elements="*"/>


  <!-- Always process the Investigation_Message -->
  <!-- Always process the NNDEvent-->
  <!-- Always process the NedssEvent -->
  <!-- Always process the NedssEventType -->
  <xsl:template match="IM:Investigation_Message|IM:NNDEvent|IM:NedssEvent|IM:NedssEventType">
    <xsl:copy>
      <xsl:copy-of select="@*[not(name()='keep')]"/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


  <!-- Always include the MessageHeader in it's entirety -->
  <xsl:template match="IM:MessageHeader">
    <xsl:copy-of select="."/>
  </xsl:template>


  <!-- process all elements, if an element has a child with an attribute --> 
  <!-- of keep="1" then copy this element and process the children       -->
  <xsl:template match="*">
    <xsl:if test=".//*[@keep='1']">
      <xsl:copy>
        <xsl:copy-of select="@*|text()"/>
        <xsl:apply-templates select="*"/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>

  <!-- if the element has an attribute of keep="1" then copy through     -->
  <!-- the element and all children                                      -->
  <xsl:template match="*[@keep='1']">
    <xsl:copy>
      <xsl:copy-of select="./@*|*|text()"/>
    </xsl:copy>
  </xsl:template>

<!-- Uncomment for TESTING -->
<!--
  <xsl:template match="@*|*|text()|comment()">
    <xsl:copy>
      <xsl:copy-of select="@*|*|text()|comment()"/>
    </xsl:copy>
  </xsl:template>
-->
</xsl:stylesheet>
