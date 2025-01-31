<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="jumpers">
        <xsl:call-template name="jumpers">
            <xsl:with-param name="value" select="@value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="jumpers">
        <xsl:param name="value"/>
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
            <xsl:comment> jumpers begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="count(//section[(string-length(part/attributes/name) > 0) and (part/attributes/linked = $true) and (count(descendant::subsection) > 0)]) >= $value">
                <xsl:for-each select="//section[(string-length(part/attributes/name) > 0) and (part/attributes/linked = $true) and (count(descendant::subsection) > 0)]">
                    <a>
                        <xsl:attribute name="class">
                            <xsl:value-of select="'JumperLink'"/>
                        </xsl:attribute>
                        <xsl:attribute name="href">
                            <xsl:value-of select="'#'"/>
                            <xsl:value-of select="part/attributes/id"/>
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
                        <xsl:value-of select="part/attributes/name"/>
                    </a>
                    <xsl:if test="position() != last()">
                        <span class="JumperBar">
                            <xsl:text>|</xsl:text>
                        </span>
                    </xsl:if>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="space"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> jumpers end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
