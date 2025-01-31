<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="toolbar">
        <xsl:call-template name="toolbar">
            <xsl:with-param name="location" select="@location"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="toolbar">
        <xsl:param name="location"/>
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
            <xsl:comment> toolbar begin </xsl:comment>
        </xsl:if>
<!--
        <xsl:if test="(count(//toolbarDef/toolbarLeft/toolbarButton)>0) or (count(//toolbarDef/toolbarRight/toolbarButton)>0)">
        </xsl:if>
-->
        <table border="0" cellpadding="0" cellspacing="0" summary="" width="100%">
            <tbody>
                <xsl:if test="$location = 'bottom'">
                    <tr>
                        <td colspan="4">
                            <xsl:call-template name="space"/>
                        </td>
                    </tr>
                </xsl:if>
                <tr>
                    <td  valign="top">
                        <xsl:choose>
                            <xsl:when test="$location = 'top'">
                                <img src="task_button/left_corner.jpg" alt="" height="53" width="25"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <img src="task_button/left_bottom_corner.jpg" alt="" height="53" width="25"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    <td class="ToolbarLeft" valign="top">
                        <table border="0" cellpadding="0" cellspacing="0" width="528">
                            <tbody>
                                <xsl:call-template name="toolbarButton">
                                    <xsl:with-param name="location" select="$location"/>
                                    <xsl:with-param name="buttons" select="//toolbarDef/toolbarLeft/toolbarButton"/>
                                </xsl:call-template>
                            </tbody>
                        </table>
                    </td>
                    <td class="ToolbarMiddle" valign="top">
                        <img src="task_button/strip_spacer.jpg" alt="" height="53" width="3"/>
                    </td>
                    <td class="ToolbarRight" valign="top">
                        <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="196">
                            <tbody>
                                <xsl:call-template name="toolbarButton">
                                    <xsl:with-param name="location" select="$location"/>
                                    <xsl:with-param name="buttons" select="//toolbarDef/toolbarRight/toolbarButton"/>
                                </xsl:call-template>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <xsl:if test="$verbose = $true">
            <xsl:comment> toolbar end </xsl:comment>
        </xsl:if>
    </xsl:template>
    
</xsl:stylesheet>
