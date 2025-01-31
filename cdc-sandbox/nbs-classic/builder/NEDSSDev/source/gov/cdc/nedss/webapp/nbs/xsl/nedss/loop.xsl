<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="loop">
        <xsl:if test="@times">
            <xsl:call-template name="loop">
                <xsl:with-param name="times" select="@times"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
    
    <xsl:template name="loop">
        <xsl:param name="times"/>
        <xsl:if test="$times > 0">
            <xsl:apply-templates/>
            <xsl:call-template name="loop">
                <xsl:with-param name="times" select="$times - 1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
