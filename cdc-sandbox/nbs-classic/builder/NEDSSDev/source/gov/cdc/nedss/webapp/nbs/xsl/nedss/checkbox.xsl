<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="checkbox">
        <xsl:call-template name="checkbox">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="checked" select="@checked"/>
            <xsl:with-param name="enabled" select="@enabled"/>
            <xsl:with-param name="struts" select="@struts"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="checkbox">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="checked"/>
        <xsl:param name="enabled"/>
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
            <xsl:comment> checkbox begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$uid and ($uids = $true)">
                <xsl:value-of select="$uid"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="$uid and $id and $struts">
                    <xsl:variable name="StrutsID">
                        <xsl:value-of select="'id_Struts_'"/>
                        <xsl:value-of select="$id"/>
                    </xsl:variable>
                    <xsl:call-template name="hiddenfield">
                        <xsl:with-param name="uid" select="$uid"/>
                        <xsl:with-param name="id" select="$StrutsID"/>
                        <xsl:with-param name="name" select="$struts"/>
                        <xsl:with-param name="value" select="$uid"/>
                    </xsl:call-template>
                </xsl:if>
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
                    <xsl:attribute name="type">
                        <xsl:value-of select="'checkbox'"/>
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
                            <xsl:choose>
                                <xsl:when test="$enabled = $false">
                                    <xsl:attribute name="disabled">
                                        <xsl:value-of select="'disabled'"/>
                                    </xsl:attribute>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                                        <xsl:attribute name="onclick">
                                            <xsl:value-of select="'if(is_ie)'"/>
                                            <xsl:value-of select="'{'"/>
                                            <xsl:value-of select="'return '"/>
                                            <xsl:value-of select="$id"/>
                                            <xsl:value-of select="$underline"/>
                                            <xsl:value-of select="'onclick(this);'"/>
                                            <xsl:value-of select="'}'"/>
                                            <xsl:value-of select="'else'"/>
                                            <xsl:value-of select="'{'"/>
                                            <xsl:value-of select="'return '"/>
                                            <xsl:value-of select="$id"/>
                                            <xsl:value-of select="$underline"/>
                                            <xsl:value-of select="'onclick(event);'"/>
                                            <xsl:value-of select="'}'"/>
                                        </xsl:attribute>
                                    </xsl:if>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </input>
                <xsl:if test="$uids != $true">
                    <xsl:apply-templates select="event"/>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> checkbox end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
