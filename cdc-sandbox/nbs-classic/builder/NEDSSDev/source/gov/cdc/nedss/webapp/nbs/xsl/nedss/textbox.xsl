<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="textbox">
        <xsl:call-template name="textbox">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="value" select="text()"/>
            <xsl:with-param name="size" select="@size"/>
            <xsl:with-param name="max" select="@max"/>
            <xsl:with-param name="enabled" select="@enabled"/>
            <xsl:with-param name="struts" select="@struts"/>
            <xsl:with-param name="password" select="@password"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="textbox">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="value"/>
        <xsl:param name="size"/>
        <xsl:param name="max"/>
        <xsl:param name="enabled"/>
        <xsl:param name="struts"/>
        <xsl:param name="password"/>
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
            <xsl:comment> textbox begin </xsl:comment>
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
                <input>
                    <xsl:attribute name="type">
                        <xsl:choose>
                            <xsl:when test="$password = $true">
                                <xsl:value-of select="'password'"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'text'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
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
                    <xsl:attribute name="value">
                        <xsl:value-of select="$val"/>
                    </xsl:attribute>
                    <xsl:if test="$size">
                        <xsl:attribute name="size">
                            <xsl:value-of select="$size"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$max">
                        <xsl:attribute name="maxlength">
                            <xsl:value-of select="$max"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="$enabled = $false">
                        <xsl:attribute name="disabled">
                            <xsl:value-of select="'disabled'"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="($id) and (event[@name = 'onkeydown']) and ($uids != $true)">
                        <xsl:attribute name="onkeydown">
                            <xsl:value-of select="'if(is_ie)'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeydown(this);'"/>
                            <xsl:value-of select="'}'"/>
                            <xsl:value-of select="'else'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeydown(event);'"/>
                            <xsl:value-of select="'}'"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="($id) and (event[@name = 'onkeyup']) and ($uids != $true)">
                        <xsl:attribute name="onkeyup">
                            <xsl:value-of select="'if(is_ie)'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeyup(this);'"/>
                            <xsl:value-of select="'}'"/>
                            <xsl:value-of select="'else'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeyup(event);'"/>
                            <xsl:value-of select="'}'"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="($id) and (event[@name = 'onkeypress']) and ($uids != $true)">
                        <xsl:attribute name="onkeypress">
                            <xsl:value-of select="'if(is_ie)'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeypress(this);'"/>
                            <xsl:value-of select="'}'"/>
                            <xsl:value-of select="'else'"/>
                            <xsl:value-of select="'{'"/>
                            <xsl:value-of select="'return '"/>
                            <xsl:value-of select="$id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'onkeypress(event);'"/>
                            <xsl:value-of select="'}'"/>
                        </xsl:attribute>
                    </xsl:if>
                </input>
                <xsl:if test="($id) and (event[@name = 'onkeyup']) and ($uids != $true) and (@max = '10')">
				<a href="#">
					<img src="calendar.gif" border="0" alt="Select a Date" align="bottom">
						<xsl:attribute name="name"><xsl:value-of select="generate-id()"/>_button</xsl:attribute>
						<xsl:attribute name="onclick">getCalDate('<xsl:value-of select="normalize-space($id)"/>','<xsl:value-of select="generate-id()"/>_button');return 						false;</xsl:attribute>
						<xsl:attribute name="onkeypress">showCalendarEnterKey('<xsl:value-of select="normalize-space($id)"/>','<xsl:value-of select="generate-id()"/>_button',event);</xsl:attribute>
	
					</img>
				</a>
			</xsl:if>	                
                <xsl:if test="$uids != $true">
                    <xsl:apply-templates select="event"/>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="text">
                    <xsl:with-param name="format" select="'Normal'"/>
                    <xsl:with-param name="value" select="$val"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> textbox end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
