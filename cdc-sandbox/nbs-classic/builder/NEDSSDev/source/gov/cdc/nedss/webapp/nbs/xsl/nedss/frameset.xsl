<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="frameset">
        <xsl:call-template name="frameset">
            <xsl:with-param name="type" select="@type"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="frameset">
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
            <xsl:comment> frameset begin </xsl:comment>
        </xsl:if>
        <frameset>
            <xsl:attribute name="style">
                <xsl:value-of select="'border: none;'"/>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="$type = 'cols'">
                    <xsl:attribute name="cols">
                        <xsl:for-each select="frame">
                            <xsl:value-of select="@size"/>
                            <xsl:if test="position() != last()">
                                <xsl:value-of select="','"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="rows">
                        <xsl:for-each select="frame">
                            <xsl:value-of select="@size"/>
                            <xsl:if test="position() != last()">
                                <xsl:value-of select="','"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates/>
        </frameset>
        <xsl:if test="$verbose = $true">
            <xsl:comment> frameset end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
