<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="parameters">
        <xsl:call-template name="parameters"/>
    </xsl:template>

    <xsl:template name="parameters">
        <xsl:comment> parameters begin </xsl:comment>
        <xsl:apply-templates/>
        <xsl:comment> parameters end </xsl:comment>
    </xsl:template>

</xsl:stylesheet>
