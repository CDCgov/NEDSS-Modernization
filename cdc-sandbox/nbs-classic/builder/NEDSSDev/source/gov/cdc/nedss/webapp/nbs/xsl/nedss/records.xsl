<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="records">
        <xsl:call-template name="records">
            <xsl:with-param name="type" select="@type"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="records">
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
            <xsl:comment> records begin </xsl:comment>
        </xsl:if>
        <tbody>
            <xsl:choose>
                <xsl:when test="count(record) = 0">
                    <xsl:variable name="width" select="count(ancestor::table/columnheaders/columnheader)"/>
                    <xsl:variable name="ColumnsSkip" select="count(ancestor::table/columnheaders/columnheader[@name = $empty])"/>
                    <xsl:variable name="ColumnsToDo" select="count(ancestor::table/columnheaders/columnheader[@name != $empty])"/>
                    <tr>
                        <td colspan="{$width}">
                            <xsl:call-template name="space"/>
                        </td>
                    </tr>
                    <tr>
<!--
                        <td class="Shaded" colspan="{$ColumnsSkip}">
                            <xsl:call-template name="space"/>
                        </td>
-->
<!--
                        <td class="Shaded" colspan="{$ColumnsToDo}">
                            <xsl:call-template name="text">
                                <xsl:with-param name="format" select="'Bold'"/>
                                <xsl:with-param name="value" select="'There is no information to display.'"/>
                            </xsl:call-template>
                        </td>
-->
                        <td class="Shaded" colspan="{$width}">
                            <xsl:call-template name="text">
                                <xsl:with-param name="format" select="'Bold'"/>
                                <xsl:with-param name="value" select="'There is no information to display.'"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates/>
                </xsl:otherwise>
            </xsl:choose>
        </tbody>
        <xsl:if test="$verbose = $true">
            <xsl:comment> records end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
