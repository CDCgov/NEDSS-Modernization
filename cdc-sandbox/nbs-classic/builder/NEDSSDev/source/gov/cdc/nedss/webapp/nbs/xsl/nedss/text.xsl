<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="text">
        <xsl:call-template name="text">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="format" select="@format"/>
            <xsl:with-param name="display" select="@display"/>
            <xsl:with-param name="value" select="text()"/>
            <xsl:with-param name="style" select="@style"/>

        </xsl:call-template>
    </xsl:template>

    <xsl:template name="text">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="format"/>
        <xsl:param name="display"/>
        <xsl:param name="value"/>
        <xsl:param name="style"/>
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
            <xsl:comment> text begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="val">
            <xsl:choose>
                <xsl:when test="$uid and ($uids = $true)">
                    <xsl:value-of select="$uid"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="$value">
                            <xsl:value-of select="normalize-space($value)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$empty"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <span>
            <xsl:if test="$id">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="class">
                <xsl:choose>
                    <xsl:when test="$format = 'Bold'">
                        <xsl:value-of select="'TextBold'"/>
                    </xsl:when>
                    <xsl:when test="$format = 'Underline'">
                        <xsl:value-of select="'TextUnderline'"/>
                    </xsl:when>
                    <xsl:when test="$format = 'BoldAndUnderline'">
                        <xsl:value-of select="'TextBoldAndUnderline'"/>
                    </xsl:when>
                    <xsl:when test="$format = 'Caption'">
                        <xsl:value-of select="'TextCaption'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'Text'"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:call-template name="VerifyDisplay">
                    <xsl:with-param name="display" select="$display"/>
                </xsl:call-template>
            </xsl:attribute>
            <xsl:choose>
			<xsl:when test="$style and ($style != $empty)">
		            <xsl:attribute name="style">
		            		<xsl:value-of select="$style"/>
		            </xsl:attribute>
			</xsl:when>
		  </xsl:choose>
            <xsl:choose>
                <xsl:when test="$val and ($val != $empty)">
                    <xsl:value-of select="$val"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="count(textnode/text()) = 0">
                        <xsl:call-template name="space"/>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates/>
        </span>
        <xsl:if test="$verbose = $true">
            <xsl:comment> text end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
