<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="radiobuttons">
        <xsl:call-template name="radiobuttons">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="align" select="@align"/>
            <xsl:with-param name="struts" select="@struts"/>
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template name="radiobuttons">
        <xsl:param name="uid"/>
        <xsl:param name="name"/>
        <xsl:param name="align"/>
        <xsl:param name="struts"/>
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
            <xsl:comment> radiobuttons begin </xsl:comment>
        </xsl:if>
        <xsl:if test="$uid and $struts">
            <xsl:variable name="StrutsID">
                <xsl:value-of select="'id_Struts_'"/>
                <xsl:value-of select="$uid"/>
            </xsl:variable>
            <xsl:call-template name="hiddenfield">
                <xsl:with-param name="uid" select="$uid"/>
                <xsl:with-param name="id" select="$StrutsID"/>
                <xsl:with-param name="name" select="$struts"/>
                <xsl:with-param name="value" select="$uid"/>
            </xsl:call-template>
        </xsl:if>
        <table border="{$border}" cellpadding="2" cellspacing="0" summary="">
            <tbody>
                <xsl:choose>
                    <xsl:when test="$align = 'horizontal'">
                        <tr>
                            <xsl:for-each select="radiobutton">
                                <td>
                                    <xsl:apply-templates select="."/>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each select="radiobutton">
                            <tr>
                                <td>
                                    <xsl:apply-templates select="."/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> radiobuttons end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
