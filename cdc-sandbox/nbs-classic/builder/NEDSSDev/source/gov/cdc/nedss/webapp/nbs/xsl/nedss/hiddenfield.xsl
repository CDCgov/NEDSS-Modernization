<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="hiddenfield">
        <xsl:call-template name="hiddenfield">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="value" select="text()"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="hiddenfield">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="value"/>
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
            <xsl:comment> hiddenfield begin </xsl:comment>
        </xsl:if>
        <input>
            <xsl:attribute name="type">hidden</xsl:attribute>
            <xsl:if test="$id">
                <xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
            </xsl:if>
            <xsl:if test="$name">
                <xsl:attribute name="name"><xsl:value-of select="$name"/></xsl:attribute>
            </xsl:if>
            <xsl:attribute name="value">
                <xsl:value-of select="normalize-space($value)"/>
            </xsl:attribute>
        </input>
        <xsl:if test="$verbose = $true">
            <xsl:comment> hiddenfield end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
