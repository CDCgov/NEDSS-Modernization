<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="parameter">
        <xsl:call-template name="parameter">
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="type" select="@type"/>
            <xsl:with-param name="value" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="parameter">
        <xsl:param name="name"/>
        <xsl:param name="type"/>
        <xsl:param name="value"/>
        <xsl:variable name="val">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="type" select="$type"/>
                <xsl:with-param name="default" select="normalize-space($value)"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:comment> parameter  <xsl:value-of select="$name"/>='<xsl:value-of select="$val"/>' </xsl:comment>
        <xsl:apply-templates/>
    </xsl:template>

</xsl:stylesheet>
