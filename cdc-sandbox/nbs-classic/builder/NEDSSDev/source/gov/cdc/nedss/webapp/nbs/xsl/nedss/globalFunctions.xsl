<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <!-- Prints a non-breaking space -->
    <xsl:template name="space">
        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
    </xsl:template>
    
    <!-- Prints a space, a vertical bar, and a space -->
    <xsl:template name="verticalSeparator">
        <xsl:call-template name="space"/>
        <xsl:text>|</xsl:text>
        <xsl:call-template name="space"/>
    </xsl:template>

    <!-- Normalizes a value to either 1 or 0. -->
    <xsl:template name="normalizeBinary">
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="normalize-space($value) = '1'">
                <xsl:value-of select="'1'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'0'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Normalizes a value to either true or false. -->
    <xsl:template name="normalizeBoolean">
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="normalize-space($value) = $true">
                <xsl:value-of select="$true"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$false"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Gets a parameter. -->
    <xsl:template name="getParameter">
        <xsl:param name="name"/>
        <xsl:param name="type"/>
        <xsl:param name="default"/>
        <xsl:variable name="value" select="//parameters/parameter[@name = $name]"/>
        <xsl:choose>
            <xsl:when test="$value">
                <xsl:choose>
                    <xsl:when test="$type = 'binary'">
                        <xsl:call-template name="normalizeBinary">
                            <xsl:with-param name="value" select="$value"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$type = 'boolean'">
                        <xsl:call-template name="normalizeBoolean">
                            <xsl:with-param name="value" select="$value"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$value"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$type = 'binary'">
                        <xsl:choose>
                            <xsl:when test="$default">
                                <xsl:call-template name="normalizeBinary">
                                    <xsl:with-param name="value" select="$default"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'0'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$type = 'boolean'">
                        <xsl:choose>
                            <xsl:when test="$default">
                                <xsl:call-template name="normalizeBoolean">
                                    <xsl:with-param name="value" select="$default"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$false"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="$default">
                                <xsl:value-of select="$default"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'[null]'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
