<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template name="VerifyBackground">
        <xsl:param name="background"/>
        <xsl:choose>
            <xsl:when test="$background = 'BackgroundGrayDark'">
                <xsl:value-of select="'BackgroundGrayDark'"/>
            </xsl:when>
            <xsl:when test="$background = 'BackgroundGrayLight'">
                <xsl:value-of select="'BackgroundGrayLight'"/>
            </xsl:when>
            <xsl:when test="$background = 'BackgroundFunkyBlue'">
                <xsl:value-of select="'BackgroundFunkyBlue'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'BackgroundWhite'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="VerifyDisplay">
        <xsl:param name="display"/>
        <xsl:choose>
            <xsl:when test="$display = 'Hidden'">
                <xsl:value-of select="'Hidden'"/>
            </xsl:when>
            <xsl:when test="$display = 'Invisible'">
                <xsl:value-of select="'Invisible'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'Visible'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
