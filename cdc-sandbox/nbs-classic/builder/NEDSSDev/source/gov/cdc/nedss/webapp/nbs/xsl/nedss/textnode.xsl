<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="textnode">
        <xsl:call-template name="textnode">
            <xsl:with-param name="value" select="text()"/>
            <xsl:with-param name="default" select="@default"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="textnode">
        <xsl:param name="value"/>
        <xsl:param name="default"/>
        <xsl:choose>
            <xsl:when test="$value">
                <xsl:value-of select="normalize-space($value)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="$default = $true">
                    <xsl:call-template name="space"/>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
