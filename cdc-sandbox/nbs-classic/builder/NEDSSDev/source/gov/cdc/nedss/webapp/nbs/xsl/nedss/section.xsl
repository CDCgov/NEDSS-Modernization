<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="section">
        <xsl:if test="count(descendant::subsection) > 0">
            <xsl:call-template name="section">
                <xsl:with-param name="id" select="part/attributes/id"/>
                <xsl:with-param name="name" select="part/attributes/name"/>
                <xsl:with-param name="linked" select="part/attributes/linked"/>
                <xsl:with-param name="width" select="part/attributes/width"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="section">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="linked"/>
        <xsl:param name="width"/>
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
            <xsl:comment> section begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="(string-length($name) > 0) and (count(//section/part/attributes[name = $name]) > 1)">
                <xsl:if test="(.) = (//section[part/attributes/name = $name][1])">
                    <table>
                        <xsl:attribute name="border">
                            <xsl:value-of select="$border"/>
                        </xsl:attribute>
                        <xsl:attribute name="cellpadding">
                            <xsl:value-of select="'2'"/>
                        </xsl:attribute>
                        <xsl:attribute name="cellspacing">
                            <xsl:value-of select="'0'"/>
                        </xsl:attribute>
                        <xsl:attribute name="id">
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'Section'"/>
                        </xsl:attribute>
                        <xsl:attribute name="summary">
                            <xsl:value-of select="''"/>
                        </xsl:attribute>
                        <xsl:attribute name="width">
                            <xsl:value-of select="'100%'"/>
                        </xsl:attribute>
                        <tbody>
                            <xsl:call-template name="sectionheader">
                                <xsl:with-param name="id" select="$id"/>
                                <xsl:with-param name="name" select="$name"/>
                                <xsl:with-param name="linked" select="$linked"/>
                                <xsl:with-param name="width" select="$width"/>
                            </xsl:call-template>
                            <xsl:for-each select="//section[part/attributes/name = $name]">
                                <xsl:apply-templates/>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <table>
                    <xsl:attribute name="border">
                        <xsl:value-of select="$border"/>
                    </xsl:attribute>
                    <xsl:attribute name="cellpadding">
                        <xsl:value-of select="'2'"/>
                    </xsl:attribute>
                    <xsl:attribute name="cellspacing">
                        <xsl:value-of select="'0'"/>
                    </xsl:attribute>
                    <xsl:attribute name="id">
                        <xsl:value-of select="$id"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="'Section'"/>
                    </xsl:attribute>
                    <xsl:attribute name="summary">
                        <xsl:value-of select="''"/>
                    </xsl:attribute>
                    <xsl:attribute name="width">
                        <xsl:value-of select="'100%'"/>
                    </xsl:attribute>
                    <tbody>
                        <xsl:call-template name="sectionheader">
                            <xsl:with-param name="id" select="$id"/>
                            <xsl:with-param name="name" select="$name"/>
                            <xsl:with-param name="linked" select="$linked"/>
                            <xsl:with-param name="width" select="$width"/>
                        </xsl:call-template>
                        <xsl:apply-templates/>
                    </tbody>
                </table>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> section end </xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="sectionheader">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="linked"/>
        <xsl:param name="width"/>
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
        <xsl:if test="$id">
            <tr>
                <xsl:attribute name="class">
                    <xsl:choose>
                        <xsl:when test="string-length($name) > 0">
                            <xsl:value-of select="'Visible'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'Hidden'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <td colspan="{$width}">
                    <xsl:if test="$linked = 'true'">
                        <a>
                            <xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
                            <xsl:attribute name="name"><xsl:value-of select="$id"/></xsl:attribute>
                        </a>
                    </xsl:if>
                    <xsl:call-template name="space"/>
                </td>
            </tr>
        </xsl:if>
        <xsl:if test="string-length($name) > 0">
            <tr class="Blue">
                <td colspan="{$width}">
                    <table border="{$border}" cellpadding="0" cellspacing="0" summary="" width="100%">
                        <tbody>
                            <tr>
                                <td class="Section" align="left">
                                    <xsl:value-of select="$name"/>
                                </td>
                                <xsl:if test="($usermode = $usermode_add) or ($usermode = $usermode_edit) or ($usermode = $usermode_view)">
                                    <xsl:if test="$linked = $true">
                                        <td align="right">
                                            <a>
                                                <xsl:attribute name="href">#top</xsl:attribute>
                                                <xsl:attribute name="class">BackToTop</xsl:attribute>
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
                                                <xsl:value-of select="'Back to Top'"/>
                                            </a>
                                        </td>
                                    </xsl:if>
                                </xsl:if>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="{$width}">
                    <xsl:call-template name="space"/>
                </td>
            </tr>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
