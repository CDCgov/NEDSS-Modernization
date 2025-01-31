<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="grid">
        <xsl:call-template name="grid">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="width" select="@width"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="grid">
        <xsl:param name="id"/>
        <xsl:param name="width"/>
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
            <xsl:comment> grid begin </xsl:comment>
        </xsl:if>
        <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="100%">
            <tbody>
                <xsl:apply-templates/>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> grid end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
