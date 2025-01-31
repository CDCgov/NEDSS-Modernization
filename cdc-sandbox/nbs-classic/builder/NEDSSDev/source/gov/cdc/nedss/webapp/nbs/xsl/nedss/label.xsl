<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="label">
        <xsl:call-template name="label">
            <xsl:with-param name="ref" select="@ref"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="reqInd" select="@reqInd"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="label">
        <xsl:param name="ref"/>
        <xsl:param name="name"/>
        <xsl:param name="reqInd"/>
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
            <xsl:comment> label begin </xsl:comment>
        </xsl:if>
        <xsl:if test="$ref and $name">
            <xsl:choose>
                <xsl:when test="($usermode = $usermode_add) or ($usermode = $usermode_edit)">
                    <label>
                    		<xsl:if test="$reqInd='true'">
                    			<xsl:attribute name="style"><xsl:value-of select="'color:red'"/></xsl:attribute>
                    		</xsl:if>
                        <xsl:attribute name="for"><xsl:value-of select="$ref"/></xsl:attribute>
                        <xsl:value-of select="$name"/>
                    </label>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="text">
                        <xsl:with-param name="format" select="'Bold'"/>
                        <xsl:with-param name="value" select="$name"/>
                        <xsl:with-param name="reqInd" select="$reqInd"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
        <xsl:if test="$verbose = $true">
            <xsl:comment> label end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
