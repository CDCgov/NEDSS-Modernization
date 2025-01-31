<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="toolbarButton"/>

    <xsl:template name="toolbarButton">
        <xsl:param name="location"/>
        <xsl:param name="buttons"/>
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
            <xsl:comment> toolbarButton begin </xsl:comment>
        </xsl:if>
        <tr>
            <td>
                <table border="{$border}" cellpadding="0" cellspacing="0" summary="">
                    <tbody>
                        <tr>
                            <xsl:if test="count($buttons) = 0">
                                <td align="center" class="Hidden" valign="top">
                                    <xsl:call-template name="space"/>
                                </td>
                            </xsl:if>
                            <xsl:for-each select="$buttons">
                                <xsl:call-template name="toolbarButtonGraphic">
                                    <xsl:with-param name="location" select="$location"/>
                                </xsl:call-template>
                            </xsl:for-each>
                        </tr>
                        <tr>
                            <xsl:if test="count($buttons) = 0">
                                <td align="center" class="Hidden" valign="top">
                                    <xsl:call-template name="space"/>
                                </td>
                            </xsl:if>
                            <xsl:for-each select="$buttons">
                                <xsl:call-template name="toolbarButtonLabel">
                                    <xsl:with-param name="location" select="$location"/>
                                </xsl:call-template>
                            </xsl:for-each>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <xsl:if test="$verbose = $true">
            <xsl:comment> toolbarButton end </xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="toolbarButtonGraphic">
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
            <xsl:comment> toolbarButtonGraphic begin </xsl:comment>
        </xsl:if>
        <td align="center" class="Invisible" valign="top">
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
        </td>
        <td align="center" class="NoBorder" valign="top">
            <img>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="$location"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="'ToolbarButtonGraphic'"/>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:value-of select="@id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="$location"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="'ToolbarButtonGraphic'"/>
                </xsl:attribute>
                <xsl:attribute name="src">
                    <xsl:value-of select="'task_button/generic_button.jpg'"/>
                </xsl:attribute>
                <xsl:attribute name="alt">
                    <xsl:value-of select="@name"/>
                    <xsl:value-of select="$space"/>
                    <xsl:value-of select="'Button'"/>
                </xsl:attribute>
                <xsl:attribute name="width">
                    <xsl:value-of select="'30'"/>
                </xsl:attribute>
                <xsl:attribute name="tabIndex">
                    <xsl:value-of select="'0'"/>
                </xsl:attribute>
                <xsl:attribute name="height">
                    <xsl:value-of select="'40'"/>
                </xsl:attribute>
                <xsl:attribute name="class">
                    <xsl:value-of select="'ToolbarButtonGraphic'"/>
                </xsl:attribute>
                <xsl:if test="(@id) and (event[@name = 'onclick']) and ($uids != $true)">
                    <xsl:attribute name="onclick">
                        <xsl:value-of select="@id"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="$location"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="'ToolbarButtonGraphic'"/>
                        <xsl:value-of select="$underline"/>
                        <xsl:value-of select="'onclick(this);'"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:attribute name="onkeypress">
					<xsl:value-of select="'if(is_ie){return id_UserName_onkeypress(this);}else{return id_UserName_onkeypress(event);}'"/>
				</xsl:attribute>
                
                 
            </img>
            <xsl:if test="$uids != $true">
                <xsl:for-each select="event">
                    <xsl:call-template name="event">
                        <xsl:with-param name="id">
                            <xsl:value-of select="../@id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="$location"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'ToolbarButtonGraphic'"/>
                        </xsl:with-param>
                        <xsl:with-param name="name" select="@name"/>
                        <xsl:with-param name="code" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>
        </td>
        <xsl:if test="$verbose = $true">
            <xsl:comment> toolbarButtonGraphic end </xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="toolbarButtonLabel">
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
            <xsl:comment> toolbarButtonLabel begin </xsl:comment>
        </xsl:if>
        <td align="center" class="Invisible" valign="top">
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
            <xsl:call-template name="space"/>
        </td>
        <td>
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
                <xsl:value-of select="$underline"/>
                <xsl:value-of select="$location"/>
                <xsl:value-of select="$underline"/>
                <xsl:value-of select="'ToolbarButtonLabel'"/>
            </xsl:attribute>
            <xsl:attribute name="align">
                <xsl:value-of select="'center'"/>
            </xsl:attribute>
            <xsl:attribute name="class">
                <xsl:value-of select="'ToolbarButtonLabel'"/>
            </xsl:attribute>
            <xsl:attribute name="valign">
                <xsl:value-of select="'top'"/>
            </xsl:attribute>
            <xsl:if test="(@id) and (event[@name = 'onclick']) and ($uids != $true)">
                <xsl:attribute name="onclick">
                    <xsl:value-of select="@id"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="$location"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="'ToolbarButtonLabel'"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="'onclick(this);'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="@name"/>
            <xsl:if test="$uids != $true">
                <xsl:for-each select="event">
                    <xsl:call-template name="event">
                        <xsl:with-param name="id">
                            <xsl:value-of select="../@id"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="$location"/>
                            <xsl:value-of select="$underline"/>
                            <xsl:value-of select="'ToolbarButtonLabel'"/>
                        </xsl:with-param>
                        <xsl:with-param name="name" select="@name"/>
                        <xsl:with-param name="code" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>
        </td>
        <xsl:if test="$verbose = $true">
            <xsl:comment> toolbarButtonLabel end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
