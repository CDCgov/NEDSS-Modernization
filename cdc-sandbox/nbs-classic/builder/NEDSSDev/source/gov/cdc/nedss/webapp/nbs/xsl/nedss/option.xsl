<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="option">
        <xsl:variable name="uids">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'uids'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="id">
            <xsl:choose>
                <xsl:when test="@id">
                    <xsl:value-of select="ancestor::listbox/@id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="@id"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="ancestor::listbox/@id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="generate-id(.)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="selected">
            <xsl:choose>
                <xsl:when test="@selected">
                    <xsl:choose>
                        <xsl:when test="$uids = $true">
                            <xsl:choose>
                                <xsl:when test="@name = ancestor::listbox/@uid">
                                    <xsl:value-of select="$true"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$false"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@selected"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$false"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:call-template name="option">
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="value" select="@value"/>
            <xsl:with-param name="selected" select="$selected"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="option">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="value"/>
        <xsl:param name="selected"/>
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
            <xsl:comment> option begin </xsl:comment>
        </xsl:if>
        <option>
            <xsl:if test="$id">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$value">
                <xsl:attribute name="value">
                    <xsl:value-of select="$value"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$selected = $true">
                <xsl:attribute name="selected">
                    <xsl:value-of select="'selected'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$name">
                <xsl:value-of select="$name"/>
            </xsl:if>
        </option>
        <xsl:if test="$verbose = $true">
            <xsl:comment> option end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
