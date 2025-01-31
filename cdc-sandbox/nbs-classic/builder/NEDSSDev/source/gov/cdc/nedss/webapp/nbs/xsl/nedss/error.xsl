<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="error">
        <xsl:call-template name="error">
            <xsl:with-param name="ref" select="@ref"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="error">
        <xsl:param name="ref"/>
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
            <xsl:comment> error begin </xsl:comment>
        </xsl:if>
        <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="100%">
            <tbody>
                <tr>
                    <td>
                        <xsl:attribute name="id">
                            <xsl:value-of select="$ref"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'error'"/>
                        </xsl:attribute>
                        <xsl:attribute name="class">
                            <xsl:value-of select="'Hidden'"/>
                        </xsl:attribute>
                        <xsl:value-of select="'.'"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> error end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
