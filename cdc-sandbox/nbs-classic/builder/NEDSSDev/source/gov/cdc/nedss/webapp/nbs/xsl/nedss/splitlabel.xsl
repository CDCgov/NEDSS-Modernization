<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="splitlabel">
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
            <xsl:comment> splitlabel begin </xsl:comment>
        </xsl:if>
        <xsl:if test="@ref and (count(label[@name]) > 0)">
            <xsl:choose>
                <xsl:when test="($usermode = $usermode_add) or ($usermode = $usermode_edit)">
                    <label>
                        <xsl:attribute name="for"><xsl:value-of select="@ref"/></xsl:attribute>
                        <xsl:for-each select="label">
                            <xsl:value-of select="@name"/>
                            <xsl:if test="position() != last()">
                                <br/>
                            </xsl:if>
                        </xsl:for-each>
                    </label>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:for-each select="label">
                        <xsl:call-template name="text">
                            <xsl:with-param name="format" select="'Bold'"/>
                            <xsl:with-param name="value" select="@name"/>
                        </xsl:call-template>
                        <xsl:if test="position() != last()">
                            <br/>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> splitlabel end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
