<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="format">
        <xsl:call-template name="format">
            <xsl:with-param name="type" select="@type"/>
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template name="format">
        <xsl:param name="type"/>
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
            <xsl:comment> format begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$type = 'date'">
                <span style="text-indent: 4px;">
                    <xsl:value-of select="'mm/dd/yyyy'"/>
                </span>
            </xsl:when>
            <xsl:when test="$type = 'monthYear'">
                <span style="text-indent: 4px;">
                    <xsl:value-of select="'mm/yyyy'"/>
                </span>
            </xsl:when>
            <xsl:when test="$type = 'time'">
                <span style="text-indent: 10px;">
                    <xsl:value-of select="'hh:mm'"/>
                </span>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="space"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> format end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
