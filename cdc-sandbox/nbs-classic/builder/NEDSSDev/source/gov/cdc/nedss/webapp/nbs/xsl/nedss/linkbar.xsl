<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="linkbar">
        <xsl:call-template name="linkbar"/>
    </xsl:template>

    <xsl:template name="linkbar">
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
            <xsl:comment> linkbar begin </xsl:comment>
        </xsl:if>
        <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="100%">
            <tbody>
                <tr>
                    <td align="left">
                        <table border="{$border}" cellpadding="2" cellspacing="0" summary="">
                            <tbody>
                                <tr>
                                    <xsl:choose>
                                        <xsl:when test="//linkbar/ids/id">
                                            <xsl:for-each select="//linkbar/ids/id">
                                                <xsl:apply-templates select="."/>
                                                <xsl:if test="position() != last()">
                                                    <td align="left">
                                                        <xsl:call-template name="verticalSeparator"/>
                                                    </td>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <td align="left">
                                                <xsl:call-template name="space"/>
                                            </td>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td align="right">
                        <table border="{$border}" cellpadding="2" cellspacing="0" summary="">
                            <tbody>
                                <tr>
                                    <xsl:choose>
                                        <xsl:when test="//linkbar/hyperlinks/hyperlink">
                                            <xsl:for-each select="//linkbar/hyperlinks/hyperlink">
                                                <td>
                                                    <xsl:apply-templates select="."/>
                                                </td>
                                                <xsl:if test="position() != last()">
                                                    <td align="right">
                                                        <xsl:call-template name="verticalSeparator"/>
                                                    </td>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <td align="right">
                                                <xsl:call-template name="space"/>
                                            </td>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> linkbar end </xsl:comment>
        </xsl:if>
    </xsl:template>
    
</xsl:stylesheet>
