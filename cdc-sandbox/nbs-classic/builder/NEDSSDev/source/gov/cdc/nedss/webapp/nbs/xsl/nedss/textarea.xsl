<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="textarea">
        <xsl:call-template name="textarea">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="rows" select="@rows"/>
            <xsl:with-param name="cols" select="@cols"/>
            <xsl:with-param name="enabled" select="@enabled"/>
            <xsl:with-param name="value" select="text()"/>
            <xsl:with-param name="struts" select="@struts"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="textarea">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="rows"/>
        <xsl:param name="cols"/>
        <xsl:param name="enabled"/>
        <xsl:param name="value"/>
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
            <xsl:comment> textarea begin </xsl:comment>
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
        <xsl:choose>
            <xsl:when test="($usermode = $usermode_add) or ($usermode = $usermode_edit)">
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
                <textarea>
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
                    <xsl:if test="$rows">
                        <xsl:attribute name="rows">
                            <xsl:value-of select="$rows"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$cols">
                        <xsl:attribute name="cols">
                            <xsl:value-of select="$cols"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$enabled = $false">
                        <xsl:attribute name="disabled">
                            <xsl:value-of select="'disabled'"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$val">
                        <xsl:value-of select="$val"/>
                    </xsl:if>
                    
                     <!--xsl:comment>prevents the textarea collapsing</xsl:comment-->
                <![CDATA[
                
                
                
                
                `]]>	

                </textarea>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="text">
                    <xsl:with-param name="format" select="'Normal'"/>
                    <xsl:with-param name="value" select="$val"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> textarea end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
