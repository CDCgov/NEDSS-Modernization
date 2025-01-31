<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="frame">
        <xsl:call-template name="frame">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="scrolling" select="@scrolling"/>
            <xsl:with-param name="src" select="@src"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="frame">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="scrolling"/>
        <xsl:param name="src"/>
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
            <xsl:comment> frame begin </xsl:comment>
        </xsl:if>
        <frame>
            <xsl:attribute name="id">
                <xsl:value-of select="$id"/>
            </xsl:attribute>
            <xsl:attribute name="name">
                <xsl:value-of select="$name"/>
            </xsl:attribute>
            <xsl:attribute name="frameborder">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:attribute name="marginheight">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:attribute name="marginwidth">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:attribute name="noresize">
                <xsl:value-of select="'noresize'"/>
            </xsl:attribute>
            <xsl:attribute name="scrolling">
                <xsl:value-of select="$scrolling"/>
            </xsl:attribute>
            <xsl:attribute name="src">
                <xsl:value-of select="$src"/>
            </xsl:attribute>
        </frame>
        <xsl:if test="$verbose = $true">
            <xsl:comment> frame end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
