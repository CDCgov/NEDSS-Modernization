<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="scriptfile">
        <xsl:call-template name="scriptfile">
            <xsl:with-param name="name" select="text()"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="scriptfile">
        <xsl:param name="name"/>
        <xsl:variable name="border">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'border'"/>
                <xsl:with-param name="type" select="'binary'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="uids">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'uids'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="usermode">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'usermode'"/>
                <xsl:with-param name="type" select="'string'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="verbose">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'verbose'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:if test="$verbose = $true">
            <xsl:comment> scriptfile begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="file" select="normalize-space($name)"/>
        <script src="{$file}" type="text/JavaScript">
            <xsl:value-of select="';'"/>
        </script>
        <xsl:if test="$verbose = $true">
            <xsl:comment> scriptfile end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
