<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="hyperlink">
        <xsl:call-template name="hyperlink">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="display" select="@display"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="hyperlink">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
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
            <xsl:comment> hyperlink begin </xsl:comment>
        </xsl:if>
        <a>
            <xsl:attribute name="href">
                <xsl:value-of select="'#'"/>
            </xsl:attribute>
            <xsl:attribute name="id">
                <xsl:value-of select="$id"/>
            </xsl:attribute>
            <xsl:attribute name="class">
                <xsl:call-template name="VerifyDisplay">
                    <xsl:with-param name="display" select="$display"/>
                </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="onblur">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="ondblclick">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onfocus">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onmousedown">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onmousemove">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onmouseout">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onmouseover">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:attribute name="onmouseup">
                <xsl:value-of select="'ClearStatus(); return true;'"/>
            </xsl:attribute>
            <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                <xsl:attribute name="onclick">
                    <xsl:value-of select="$id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="'onclick(this); ClearStatus(); return false;'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="$name"/>
        </a>
        <xsl:if test="$uids != $true">
            <xsl:apply-templates/>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> hyperlink end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
