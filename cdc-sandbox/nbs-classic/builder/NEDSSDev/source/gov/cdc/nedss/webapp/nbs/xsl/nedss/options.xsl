<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="options">
        <xsl:call-template name="options">
            <xsl:with-param name="uid" select="../@uid"/>
            <xsl:with-param name="id" select="../@id"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="options">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="blank"/>
        <xsl:param name="other"/>
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
            <xsl:comment> options begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$uid and ($uids = $true)">
                <xsl:call-template name="option">
                    <xsl:with-param name="id" select="$uid"/>
                    <xsl:with-param name="name" select="$uid"/>
                    <xsl:with-param name="value" select="$uid"/>
                    <xsl:with-param name="selected" select="$true"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> options end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
