<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="row">
        <xsl:variable name="ref">
            <xsl:choose>
                <xsl:when test="descendant::*[(@id) and (@error = $true)]">
                    <xsl:value-of select="descendant::*[(@id) and (@error = $true)][1]/@id"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$empty"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:call-template name="row">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="background" select="@background"/>
            <xsl:with-param name="display" select="@display"/>
            <xsl:with-param name="ref" select="$ref"/>
            <xsl:with-param name="SectionWidth" select="ancestor::section/part/attributes/width"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="row">
        <xsl:param name="id"/>
        <xsl:param name="background"/>
        <xsl:param name="display"/>
        <xsl:param name="ref"/>
        <xsl:param name="SectionWidth"/>
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
            <xsl:comment> row begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="class">
            <xsl:call-template name="VerifyBackground">
                <xsl:with-param name="background" select="$background"/>
            </xsl:call-template>
            <xsl:value-of select="$space"/>
            <xsl:call-template name="VerifyDisplay">
                <xsl:with-param name="display" select="$display"/>
            </xsl:call-template>
        </xsl:variable>
        <tr>
            <xsl:if test="$id">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="class">
                <xsl:value-of select="$class"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </tr>
        <xsl:if test="($ref != $empty) and ($SectionWidth)">
            <tr>
                <td colspan="{$SectionWidth}">
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
                </td>
            </tr>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> row end </xsl:comment>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
