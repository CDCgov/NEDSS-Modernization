<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="button">
        <xsl:call-template name="button">
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="button">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
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
            <xsl:comment> button begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="($usermode = $usermode_add) or ($usermode = $usermode_edit)">
                <input>
                    <xsl:attribute name="type">
                        <xsl:value-of select="'button'"/>
                    </xsl:attribute>
                    <xsl:if test="$id">
                        <xsl:attribute name="id">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$name">
                        <xsl:attribute name="name">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$name"/>
                    </xsl:attribute>
                    <xsl:attribute name="class">
                        <xsl:value-of select="'Button'"/>
                    </xsl:attribute>
                    <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                        <xsl:attribute name="onclick">
                            <xsl:value-of select="@id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onclick(this);'"/>
                        </xsl:attribute>
                    </xsl:if>
                </input>
                <xsl:if test="$uids != $true">
                    <xsl:apply-templates/>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="space"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> button end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
