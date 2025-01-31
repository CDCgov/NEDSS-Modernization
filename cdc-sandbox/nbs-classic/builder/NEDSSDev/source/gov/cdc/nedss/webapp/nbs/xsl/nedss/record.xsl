<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="record">
        <xsl:call-template name="record">
            <xsl:with-param name="background" select="@background"/>
            <xsl:with-param name="display" select="@display"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="record">
        <xsl:param name="background"/>
        <xsl:param name="display"/>
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
            <xsl:comment> record begin </xsl:comment>
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
            <xsl:attribute name="class">
                <xsl:value-of select="$class"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </tr>
        <xsl:if test="$verbose = $true">
            <xsl:comment> record end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
