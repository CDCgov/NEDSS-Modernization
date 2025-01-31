<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Strip all whitespace. -->
    <xsl:strip-space elements="*"/>

    <!-- Include global templates. -->
    <xsl:include href="import.xsl"/>
    <xsl:include href="init.xsl"/>

    <!-- Include all other templates. -->
    <xsl:include href="else.xsl"/>
    <xsl:include href="IfAdd.xsl"/>
    <xsl:include href="IfEdit.xsl"/>
    <xsl:include href="IfAddOrEdit.xsl"/>
    <xsl:include href="IfAddOrEditOrView.xsl"/>
    <xsl:include href="IfPrint.xsl"/>
    <xsl:include href="IfServer.xsl"/>
    <xsl:include href="IfSHOW_CONTEXT_INFO.xsl"/>
    <xsl:include href="IfSHOW_DEBUG_BUTTON.xsl"/>
    <xsl:include href="IfUIDs.xsl"/>
    <xsl:include href="IfView.xsl"/>
    <xsl:include href="IfViewOrPrint.xsl"/>
    <xsl:include href="IncludeTextResource.xsl"/>
    <xsl:include href="GetConstants.xsl"/>
    <xsl:include href="SessionTimer.xsl"/>
    <xsl:include href="SetHeaders.xsl"/>
    <xsl:include href="CommonUtil.xsl"/>
    <xsl:include href="PersonSpecific.xsl"/>
    <xsl:include href="ErrorSpecific.xsl"/>
    <xsl:include href="GetNedssProperty.xsl"/>

    
    <!-- Define root template. -->
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <!-- Keep all undefined nodes. -->
    <xsl:template match="*|@*|text()|processing-instruction()">
        <xsl:copy>
            <xsl:apply-templates select="*|@*|text()|processing-instruction()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
