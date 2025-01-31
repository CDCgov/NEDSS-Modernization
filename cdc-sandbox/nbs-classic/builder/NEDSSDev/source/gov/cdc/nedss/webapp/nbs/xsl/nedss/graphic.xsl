<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="graphic">
        <xsl:call-template name="graphic">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="url" select="@url"/>
            <xsl:with-param name="tooltip" select="@tooltip"/>
            <xsl:with-param name="width" select="@width"/>
            <xsl:with-param name="height" select="@height"/>
            <xsl:with-param name="type" select="@type"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="graphic">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="url"/>
        <xsl:param name="tooltip"/>
        <xsl:param name="width"/>
        <xsl:param name="height"/>
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
            <xsl:comment> graphic begin </xsl:comment>
        </xsl:if>
            <img>
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
                <xsl:attribute name="class">
                    <xsl:choose>
                        <xsl:when test="event[@name = 'onclick']">
                            <xsl:value-of select="'ClickableGraphic'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'Graphic'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:if test="$type = 'block'">
                        <xsl:value-of select="' GraphicBlock'"/>
                    </xsl:if>
                </xsl:attribute>
                <xsl:if test="$url">
                    <xsl:attribute name="src">
                        <xsl:value-of select="$url"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:attribute name="alt">
                    <xsl:choose>
                        <xsl:when test="$tooltip">
                            <xsl:value-of select="$tooltip"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <xsl:if test="$width">
                    <xsl:attribute name="width">
                        <xsl:value-of select="$width"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="$height">
                    <xsl:attribute name="height">
                        <xsl:value-of select="$height"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                    <xsl:attribute name="onclick">
                        <xsl:value-of select="$id"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="'onclick(this);'"/>
                    </xsl:attribute>
                </xsl:if>
            </img>
            <xsl:if test="$uids != $true">
                <xsl:for-each select="event">
                    <xsl:call-template name="event">
                        <xsl:with-param name="id" select="../@id"/>
                        <xsl:with-param name="name" select="@name"/>
                        <xsl:with-param name="code" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> graphic end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
