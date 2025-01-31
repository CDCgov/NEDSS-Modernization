<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="subsection">
        <xsl:call-template name="subsection">
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="line" select="@line"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="subsection">
        <xsl:param name="name"/>
        <xsl:param name="line"/>
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
            <xsl:comment> subsection begin </xsl:comment>
        </xsl:if>
        <xsl:if test="($name) or ($line = $true)">
            <tr>
                <td colspan="{ancestor::section/part/attributes/width}" class="Subsection">
                    <xsl:if test="$name">
                        <xsl:value-of select="$name"/>
                    </xsl:if>
                    <xsl:if test="$line = $true">
                        <hr/>
                    </xsl:if>
                </td>
            </tr>
        </xsl:if>
        <xsl:apply-templates/>
        <xsl:if test="$verbose = $true">
            <xsl:comment> subsection end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
