<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="event">
        <xsl:call-template name="event">
            <xsl:with-param name="id" select="../@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="code" select="text()"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="event">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="code"/>
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
            <xsl:comment> event begin </xsl:comment>
        </xsl:if>
        <xsl:if test="$id and $name and $code">
            <xsl:variable name="EventCode">
                <xsl:value-of select="' function '"/>
                <xsl:value-of select="$id"/>
                <xsl:value-of select="$underline"/>
                <xsl:value-of select="$name"/>
                <xsl:value-of select="'(_this) { '"/>
                <xsl:value-of select="$code"/>
                <xsl:value-of select="' } '"/>
            </xsl:variable>
            <script type="text/JavaScript">
                <xsl:value-of select="normalize-space($EventCode)"/>
            </script>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> event end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
