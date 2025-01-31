<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="table">
        <xsl:call-template name="table">
            <xsl:with-param name="id" select="@id"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="table">
        <xsl:param name="id"/>
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
            <xsl:comment> table begin </xsl:comment>
        </xsl:if>
        <table>
            <xsl:attribute name="border">
                <xsl:value-of select="$border"/>
            </xsl:attribute>
            <xsl:attribute name="cellpadding">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:attribute name="cellspacing">
                <xsl:value-of select="'10'"/>
            </xsl:attribute>
            <xsl:attribute name="class">
                <xsl:value-of select="'TableOuter'"/>
            </xsl:attribute>
            <xsl:attribute name="summary">
                <xsl:value-of select="$empty"/>
            </xsl:attribute>
            <xsl:attribute name="width">
                <xsl:value-of select="'100%'"/>
            </xsl:attribute>
            <xsl:if test="$id">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <tbody>
                <tr>
                    <td>
                        <table border="{$border}" cellpadding="2" cellspacing="0" class="TableInner" summary="" width="100%">
                            <xsl:apply-templates/>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> table end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
