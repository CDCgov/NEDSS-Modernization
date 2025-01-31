<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <!-- Include a version in the default namespace for aggregates. -->
    <xsl:template match="page">
        <xsl:call-template name="page"/>
    </xsl:template>

    <xsl:template match="page">
        <xsl:call-template name="page"/>
    </xsl:template>

    <xsl:template name="page">
        <xsl:param name="doctype"/>
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
            <xsl:comment> page begin </xsl:comment>
        </xsl:if>
        <xsl:if test="$doctype">
            <xsl:choose>
                <xsl:when test="$doctype = 'xhtml1-transitional-dtd'">
                    <xsl:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">]]></xsl:text>
                </xsl:when>
                <xsl:when test="$doctype = 'xhtml1-strict-dtd'">
                    <xsl:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">]]></xsl:text>
                </xsl:when>
                <xsl:when test="$doctype = 'xhtml1-frameset-dtd'">
                    <xsl:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">]]></xsl:text>
                </xsl:when>
            </xsl:choose>
        </xsl:if>
        <html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
            <xsl:apply-templates/>
        </html>
        <xsl:if test="$verbose = $true">
            <xsl:comment> page end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
