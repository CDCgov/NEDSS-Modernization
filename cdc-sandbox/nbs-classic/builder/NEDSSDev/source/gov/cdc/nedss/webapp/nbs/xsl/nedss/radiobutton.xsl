<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="radiobutton">
        <xsl:variable name="id">
            <xsl:value-of select="'id'"/>
            <xsl:value-of select="$underline"/>
            <xsl:value-of select="../@name"/>
            <xsl:value-of select="$underline"/>
            <xsl:value-of select="@id"/>
        </xsl:variable>
        <xsl:call-template name="radiobutton">
            <xsl:with-param name="uid" select="../@uid"/>
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="name" select="../@name"/>
            <xsl:with-param name="value" select="@value"/>
            <xsl:with-param name="label" select="@label"/>
            <xsl:with-param name="checked" select="text()"/>
            <xsl:with-param name="enabled" select="@enabled"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="radiobutton">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="value"/>
        <xsl:param name="label"/>
        <xsl:param name="checked"/>
        <xsl:param name="enabled"/>
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
            <xsl:comment> radiobutton begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$uid and ($uids = $true)">
                <xsl:value-of select="$uid"/>
            </xsl:when>
            <xsl:otherwise>
                <input>
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
                    <xsl:if test="$value">
                        <xsl:attribute name="value">
                            <xsl:value-of select="$value"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="type">
                        <xsl:value-of select="'radio'"/>
                    </xsl:attribute>
                    <xsl:if test="normalize-space($checked) = $true">
                        <xsl:attribute name="checked">
                            <xsl:value-of select="'checked'"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:choose>
                        <xsl:when test="($usermode = $usermode_view) or ($usermode = $usermode_print)">
                            <xsl:attribute name="disabled">
                                <xsl:value-of select="'disabled'"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:if test="$enabled = $false">
                                <xsl:attribute name="disabled">
                                    <xsl:value-of select="'disabled'"/>
                                </xsl:attribute>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </input>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:call-template name="label">
            <xsl:with-param name="ref" select="$id"/>
            <xsl:with-param name="name" select="$label"/>
        </xsl:call-template>
        <xsl:if test="$verbose = $true">
            <xsl:comment> radiobutton end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
