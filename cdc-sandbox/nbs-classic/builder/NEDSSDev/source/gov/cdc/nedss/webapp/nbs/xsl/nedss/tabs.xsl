<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="tabs">
        <xsl:call-template name="tabs">
            <xsl:with-param name="location" select="@location"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="tabs">
        <xsl:param name="location"/>
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
            <xsl:comment> tabs begin </xsl:comment>
        </xsl:if>
        <xsl:if test="//tabDef/tab">
            <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="100%">
                <tbody>
                    <tr>
                        <td>
                            <xsl:call-template name="space"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table border="{$border}" cellpadding="0" cellspacing="0" summary="">
                                <tbody>
                                    <tr>
                                        <xsl:for-each select="//tabDef/tab">
                                            <xsl:call-template name="tab">
                                                <xsl:with-param name="location" select="$location"/>
                                            </xsl:call-template>
                                        </xsl:for-each>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="TabLine">
                            <xsl:call-template name="space"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> tabs end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
