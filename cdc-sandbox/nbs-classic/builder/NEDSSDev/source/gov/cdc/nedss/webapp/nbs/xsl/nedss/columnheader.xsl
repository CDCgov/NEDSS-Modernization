<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="columnheader">
        <xsl:call-template name="columnheader">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="align" select="@align"/>
            <xsl:with-param name="wrap" select="@wrap"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="columnheader">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="align"/>
        <xsl:param name="wrap"/>
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
            <xsl:comment> columnheader begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="classTh">
            <xsl:choose>
                <xsl:when test="$align = 'center'">
                    <xsl:value-of select="'ColumnHeaderCenter'"/>
                </xsl:when>
                <xsl:when test="$align = 'right'">
                    <xsl:value-of select="'ColumnHeaderRight'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'ColumnHeaderLeft'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="classSpan">
            <xsl:value-of select="$classTh"/>
            <xsl:if test="($name != $empty) and (event[@name = 'onclick'])">
                <xsl:value-of select="$space"/>
                <xsl:value-of select="'ColumnHeader'"/>
            </xsl:if>
            <xsl:if test="$wrap = $false">
                <xsl:value-of select="$space"/>
                <xsl:value-of select="'DontWrap'"/>
            </xsl:if>
        </xsl:variable>
        <th class="{$classTh}" valign="bottom">
            <span>
                <xsl:attribute name="class">
                    <xsl:value-of select="$classSpan"/>
                </xsl:attribute>
                <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                    <xsl:attribute name="onclick">
                        <xsl:value-of select="@id"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="'onclick(this);'"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:choose>
                    <xsl:when test="$name = $empty">
                        <xsl:call-template name="space"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$name"/>
                    </xsl:otherwise>
                </xsl:choose>
            </span>
            <xsl:if test="$uids != $true">
                <xsl:apply-templates/>
            </xsl:if>
        </th>
        <xsl:if test="$verbose = $true">
            <xsl:comment> columnheader end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
