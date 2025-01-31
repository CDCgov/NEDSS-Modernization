<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="id">
        <xsl:call-template name="id">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="value" select="text()"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="id">
        <xsl:param name="uid"/>
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
            <xsl:comment> id begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="val">
            <xsl:choose>
                <xsl:when test="$uid and ($uids = $true)">
                    <xsl:value-of select="$uid"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="$value">
                            <xsl:value-of select="normalize-space($value)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="empty"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <td align="left">
            <xsl:call-template name="text">
                <xsl:with-param name="uid" select="@uid"/>
                <xsl:with-param name="format" select="'Bold'"/>
                <xsl:with-param name="value" select="@name"/>
            </xsl:call-template>
        </td>
        <td align="left">
            <xsl:call-template name="text">
                <xsl:with-param name="uid" select="@uid"/>
                <xsl:with-param name="format" select="'Normal'"/>
                <xsl:with-param name="value" select="$val"/>
            </xsl:call-template>
        </td>
        <xsl:if test="$verbose = $true">
            <xsl:comment> id end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
