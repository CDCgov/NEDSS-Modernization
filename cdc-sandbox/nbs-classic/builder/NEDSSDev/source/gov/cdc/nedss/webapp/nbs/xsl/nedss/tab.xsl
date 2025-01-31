<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="tab"/>

    <xsl:template name="tab">
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
            <xsl:comment> tab begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="active">
            <xsl:choose>
                <xsl:when test="@id = ancestor::tabDef/@active">
                    <xsl:value-of select="$true"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$false"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="class">
            <xsl:choose>
                <xsl:when test="$active = $true">
                    <xsl:value-of select="'ActiveTab'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'InactiveTab'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="left">
            <xsl:choose>
                <xsl:when test="$active = $true">
                    <xsl:value-of select="'corner_left.gif'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'corner_left_lb.gif'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="right">
            <xsl:choose>
                <xsl:when test="$active = $true">
                    <xsl:value-of select="'corner_right.gif'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'corner_right_lb.gif'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <td>
            <table border="{$border}" cellpadding="0" cellspacing="0" summary="">
                <tbody>
                    <tr>
                        <td>
                            <table border="{$border}" cellpadding="0" cellspacing="0" summary="">
                                <tbody>
                                    <tr>
                                        <td>
                                            <xsl:call-template name="graphic">
                                                <xsl:with-param name="url" select="$left"/>
                                                <xsl:with-param name="width" select="'16'"/>
                                                <xsl:with-param name="height" select="'22'"/>
                                                <xsl:with-param name="type" select="'block'"/>
                                            </xsl:call-template>
                                        </td>
                                        <td class="{$class}">
                                            <xsl:choose>
                                                <xsl:when test="$active = $true">
                                                    <xsl:value-of select="@name"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <a>
                                                        <xsl:attribute name="onmouseover">
                                                            <xsl:value-of select="@id"/>
                                                            <xsl:value-of select="$underline"/>
                                                            <xsl:value-of select="$location"/>
                                                            <xsl:value-of select="$underline"/>
                                                            <xsl:value-of select="'onmouseover(this);'"/>
                                                        </xsl:attribute>
                                                        <xsl:if test="event[@name = 'onclick']">
                                                            <xsl:attribute name="onclick">
                                                                <xsl:value-of select="@id"/>
                                                                <xsl:value-of select="$underline"/>
                                                                <xsl:value-of select="$location"/>
                                                                <xsl:value-of select="$underline"/>
                                                                <xsl:value-of select="'onclick(this);'"/>
                                                            </xsl:attribute>
                                                        </xsl:if>
                                                        <xsl:value-of select="@name"/>
                                                    </a>
<!--
                                                    <xsl:variable name="code">
                                                        <xsl:value-of select="'function '"/>
                                                        <xsl:value-of select="@id"/>
                                                        <xsl:value-of select="$underline"/>
                                                        <xsl:value-of select="$location"/>
                                                        <xsl:value-of select="$underline"/>
                                                        <xsl:value-of select="'onmouseover(_this) '"/>
                                                        <xsl:value-of select="'{ '"/>
                                                        <xsl:value-of select="'window.defaultstatus = &quot;&quot;; '"/>
                                                        <xsl:value-of select="'window.status = &quot;&quot;; '"/>
                                                        <xsl:value-of select="'}'"/>
                                                    </xsl:variable>
                                                    <xsl:call-template name="script">
                                                        <xsl:with-param name="code" select="$code"/>
                                                    </xsl:call-template>
-->
                                                    <xsl:variable name="id1">
                                                        <xsl:value-of select="@id"/>
                                                        <xsl:value-of select="$underline"/>
                                                        <xsl:value-of select="$location"/>
                                                    </xsl:variable>
                                                    <xsl:variable name="code">
                                                        <xsl:value-of select="'window.defaultstatus = &quot;&quot;; '"/>
                                                        <xsl:value-of select="'window.status = &quot;&quot;; '"/>
                                                    </xsl:variable>
                                                    <xsl:call-template name="event">
                                                        <xsl:with-param name="id" select="$id1"/>
                                                        <xsl:with-param name="name" select="'onmouseover'"/>
                                                        <xsl:with-param name="code" select="$code"/>
                                                    </xsl:call-template>
                                                    <xsl:for-each select="event">
                                                        <xsl:variable name="id2">
                                                            <xsl:value-of select="../@id"/>
                                                            <xsl:value-of select="$underline"/>
                                                            <xsl:value-of select="$location"/>
                                                        </xsl:variable>
                                                        <xsl:call-template name="event">
                                                            <xsl:with-param name="id" select="$id2"/>
                                                            <xsl:with-param name="name" select="@name"/>
                                                            <xsl:with-param name="code" select="."/>
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>
                                        <td>
                                            <xsl:call-template name="graphic">
                                                <xsl:with-param name="url" select="$right"/>
                                                <xsl:with-param name="width" select="'16'"/>
                                                <xsl:with-param name="height" select="'22'"/>
                                                <xsl:with-param name="type" select="'block'"/>
                                            </xsl:call-template>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
        <xsl:if test="$verbose = $true">
            <xsl:comment> tab end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
