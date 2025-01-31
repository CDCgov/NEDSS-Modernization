<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="iframe">
        <xsl:call-template name="iframe">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="align" select="@align"/>
            <xsl:with-param name="frameborder" select="@frameborder"/>
            <xsl:with-param name="height" select="@height"/>
            <xsl:with-param name="scrolling" select="@scrolling"/>
            <xsl:with-param name="url" select="@url"/>
            <xsl:with-param name="width" select="@width"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="iframe">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="align"/>
        <xsl:param name="frameborder"/>
        <xsl:param name="height"/>
        <xsl:param name="scrolling"/>
        <xsl:param name="url"/>
        <xsl:param name="width"/>
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
            <xsl:comment> iframe begin </xsl:comment>
        </xsl:if>
        <iframe>
            <xsl:if test="$id">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$name">
                <xsl:attribute name="name">
                    <xsl:value-of select="$name"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$align">
                <xsl:attribute name="align">
                    <xsl:choose>
                        <xsl:when test="$align = 'top'">
                            <xsl:value-of select="'top'"/>
                        </xsl:when>
                        <xsl:when test="$align = 'bottom'">
                            <xsl:value-of select="'bottom'"/>
                        </xsl:when>
                        <xsl:when test="$align = 'right'">
                            <xsl:value-of select="'right'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'left'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$frameborder">
                <xsl:attribute name="frameborder">
                    <xsl:value-of select="$frameborder"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$height">
                <xsl:attribute name="height">
                    <xsl:value-of select="$height"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="marginheight">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:attribute name="marginwidth">
                <xsl:value-of select="'0'"/>
            </xsl:attribute>
            <xsl:if test="$scrolling">
                <xsl:attribute name="scrolling">
                    <xsl:value-of select="$scrolling"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$url">
                <xsl:attribute name="src">
                    <xsl:value-of select="$url"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$width">
                <xsl:attribute name="width">
                    <xsl:value-of select="$width"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="text">
                <xsl:with-param name="format" select="'Bold'"/>
                <xsl:with-param name="value" select="'This is an iframe.'"/>
            </xsl:call-template>
        </iframe>
        <xsl:if test="$verbose = $true">
            <xsl:comment> iframe end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
