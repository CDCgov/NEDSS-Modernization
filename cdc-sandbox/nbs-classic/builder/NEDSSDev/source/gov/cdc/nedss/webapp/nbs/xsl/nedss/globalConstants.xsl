<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <!-- logical symbols -->
    <xsl:variable name="true" select="'true'"/>
    <xsl:variable name="false" select="'false'"/>

    <!-- common strings -->
    <xsl:variable name="underline" select="'_'"/>
    <xsl:variable name="space" select="' '"/>
    <xsl:variable name="empty" select="''"/>

    <!-- standard delimiters -->
    <xsl:variable name="dollar" select="'$'"/>
    <xsl:variable name="pipe" select="'|'"/>

    <!-- view modes -->
    <xsl:variable name="usermode_add" select="'add'"/>
    <xsl:variable name="usermode_edit" select="'edit'"/>
    <xsl:variable name="usermode_view" select="'view'"/>
    <xsl:variable name="usermode_print" select="'print'"/>

</xsl:stylesheet>
