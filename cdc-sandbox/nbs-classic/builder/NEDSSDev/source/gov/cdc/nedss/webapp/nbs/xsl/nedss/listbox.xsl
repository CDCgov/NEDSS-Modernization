<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="listbox">
        <xsl:variable name="id">
            <xsl:value-of select="@id"/>
        </xsl:variable>
        <xsl:variable name="hasTrigger">
            <xsl:choose>
                <xsl:when test="//conditional[@ref = $id]">
                    <xsl:value-of select="$true"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$false"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="trigger">
            <xsl:value-of select="//conditional[@ref = $id]"/>
        </xsl:variable>
        <xsl:variable name="TriggerCode">
            var varLST = _this;
            var varSI = varLST.selectedIndex;
            var o = varLST.options[varSI];
            var v = o.value;
            var varPattern = _this.id + "_";
            MakeAllElementsHiddenWhereIdStartsWith(varPattern);
            <xsl:for-each select="//conditional[@ref = $id]">
                if(v == "<xsl:value-of select="@value"/>")
                {
                    varPattern = _this.id + "_<xsl:value-of select="@value"/>_";
                    MakeAllElementsVisibleWhereIdStartsWith(varPattern);
                }
            </xsl:for-each>
        </xsl:variable>
        <xsl:call-template name="listbox">
            <xsl:with-param name="uid" select="@uid"/>
            <xsl:with-param name="id" select="@id"/>
            <xsl:with-param name="name" select="@name"/>
            <xsl:with-param name="type" select="@type"/>
            <xsl:with-param name="size" select="@size"/>
            <xsl:with-param name="width" select="@width"/>
            <xsl:with-param name="hasTrigger" select="$hasTrigger"/>
            <xsl:with-param name="trigger" select="$trigger"/>
            <xsl:with-param name="TriggerCode" select="$TriggerCode"/>
            <xsl:with-param name="struts" select="@struts"/>
            <xsl:with-param name="enabled" select="@enabled"/>
            <xsl:with-param name="readonly" select="@readonly"/>
            <xsl:with-param name="blank" select="@blank"/>
            <xsl:with-param name="srt-options-string" select="srt-options-string"/>
            <xsl:with-param name="value" select="@value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="listbox">
        <xsl:param name="uid"/>
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="type"/>
        <xsl:param name="size"/>
        <xsl:param name="width"/>
        <xsl:param name="hasTrigger"/>
        <xsl:param name="trigger"/>
        <xsl:param name="TriggerCode"/>
        <xsl:param name="struts"/>
        <xsl:param name="enabled"/>
        <xsl:param name="readonly"/>
        <xsl:param name="blank"/>
        <xsl:param name="srt-options-string"/>
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
            <xsl:comment> listbox begin </xsl:comment>
        </xsl:if>
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
                <xsl:choose>
                    <xsl:when test="$readonly = $true">
                        <xsl:choose>
                            <xsl:when test="options/option">
                                <xsl:for-each select="options/option[@selected = $true]">
                                    <xsl:value-of select="@name"/>
                                    <xsl:if test="position() != last()">
                                        <xsl:value-of select="', '"/>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:choose>
                                    <xsl:when test="$type = 'multi'">
                                        <xsl:call-template name="srt-codes-readonly">
                                            <xsl:with-param name="default" select="''"/>
                                            <xsl:with-param name="selected-options" select="normalize-space(value)"/>
                                            <xsl:with-param name="selected-option" select="''"/>
                                            <xsl:with-param name="delimiter" select="'|'"/>
                                            <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="srt-codes-readonly">
                                            <xsl:with-param name="default" select="''"/>
                                            <xsl:with-param name="selected-options" select="''"/>
                                            <xsl:with-param name="selected-option" select="normalize-space(value)"/>
                                            <xsl:with-param name="delimiter" select="'|'"/>
                                            <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <select>
                            <xsl:attribute name="id">
                                <xsl:value-of select="$id"/>
                            </xsl:attribute>
                            <xsl:attribute name="name">
                                <xsl:value-of select="$name"/>
                            </xsl:attribute>
                            <xsl:attribute name="size">
                                <xsl:choose>
                                    <xsl:when test="$type = 'dropdown'">
                                        <xsl:value-of select="'1'"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:choose>
                                            <xsl:when test="$size">
                                                <xsl:value-of select="$size"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="'5'"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:attribute>
                            <xsl:if test="$enabled = $false">
                                <xsl:attribute name="disabled">
                                    <xsl:value-of select="'disabled'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="$type = 'multi'">
                                <xsl:attribute name="multiple">
                                    <xsl:value-of select="'multiple'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="$width">
                                <xsl:attribute name="style">
                                    <xsl:value-of select="'width:'"/>
                                    <xsl:value-of select="$width"/>
                                    <xsl:value-of select="'px;'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="($id) and (event[@name = 'onblur']) and ($uids != $true)">
                                <xsl:attribute name="onblur">
                                    <xsl:value-of select="@id"/>
                                    <xsl:value-of select="$underline"/>
                                    <xsl:value-of select="'onblur(this);'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="($id) and ($uids != $true)">
                                <xsl:choose>
                                    <xsl:when test="$hasTrigger = $true">
                                        <xsl:attribute name="onchange">
                                            <xsl:value-of select="@id"/>
                                            <xsl:value-of select="$underline"/>
                                            <xsl:value-of select="'onchange(this);'"/>
                                        </xsl:attribute>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:if test="event[@name = 'onchange']">
                                            <xsl:attribute name="onchange">
                                                <xsl:value-of select="@id"/>
                                                <xsl:value-of select="$underline"/>
                                                <xsl:value-of select="'onchange(this);'"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:if>
                            <xsl:if test="($id) and (event[@name = 'onclick']) and ($uids != $true)">
                                <xsl:attribute name="onclick">
                                    <xsl:value-of select="@id"/>
                                    <xsl:value-of select="$underline"/>
                                    <xsl:value-of select="'onclick(this);'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="($id) and (event[@name = 'ondblclick']) and ($uids != $true)">
                                <xsl:attribute name="ondblclick">
                                    <xsl:value-of select="@id"/>
                                    <xsl:value-of select="$underline"/>
                                    <xsl:value-of select="'ondblclick(this);'"/>
                                </xsl:attribute>
                            </xsl:if>
                            <xsl:if test="($id) and ($blank = $true) and ($type = 'dropdown')">
                                <xsl:call-template name="CreateBlankOption">
                                    <xsl:with-param name="id" select="$id"/>
                                </xsl:call-template>
                            </xsl:if>
                            <xsl:choose>
                                <xsl:when test="options/option">
                                    <xsl:apply-templates select="options"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:choose>
                                        <xsl:when test="$type = 'multi'">
                                            <xsl:call-template name="srt-codes">
                                                <xsl:with-param name="default" select="''"/>
                                                <xsl:with-param name="selected-options" select="normalize-space(value)"/>
                                                <xsl:with-param name="selected-option" select="''"/>
                                                <xsl:with-param name="delimiter" select="'|'"/>
                                                <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="srt-codes">
                                                <xsl:with-param name="default" select="''"/>
                                                <xsl:with-param name="selected-options" select="''"/>
                                                <xsl:with-param name="selected-option" select="normalize-space(value)"/>
                                                <xsl:with-param name="delimiter" select="'|'"/>
                                                <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="($id) and ($blank = $true) and ( ($type = 'single') or ($type = 'multi') )">
                                <xsl:call-template name="CreateBlankOption">
                                    <xsl:with-param name="id" select="$id"/>
                                </xsl:call-template>
                            </xsl:if>
                        </select>
                        <xsl:if test="$uids != $true">
                            <xsl:choose>
                                <xsl:when test="($id) and ($hasTrigger = $true)">
                                    <xsl:call-template name="event">
                                        <xsl:with-param name="id" select="$id"/>
                                        <xsl:with-param name="name" select="'onchange'"/>
                                        <xsl:with-param name="code" select="$TriggerCode"/>
                                    </xsl:call-template>
                                    <xsl:apply-templates select="event[name() != 'onchange']"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:apply-templates select="event"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$uid and ($uids = $true)">
                        <xsl:value-of select="$uid"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="count(options/option[@selected = $true]) > 0">
                                <xsl:for-each select="options/option[@selected = $true]">
                                    <xsl:value-of select="@name"/>
                                    <xsl:if test="position() != last()">
                                        <xsl:value-of select="', '"/>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:choose>
                                    <xsl:when test="$type = 'multi'">
                                        <xsl:call-template name="srt-codes-readonly">
                                            <xsl:with-param name="default" select="''"/>
                                            <xsl:with-param name="selected-options" select="normalize-space(value)"/>
                                            <xsl:with-param name="selected-option" select="''"/>
                                            <xsl:with-param name="delimiter" select="'|'"/>
                                            <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="srt-codes-readonly">
                                            <xsl:with-param name="default" select="''"/>
                                            <xsl:with-param name="selected-options" select="''"/>
                                            <xsl:with-param name="selected-option" select="normalize-space(value)"/>
                                            <xsl:with-param name="delimiter" select="'|'"/>
                                            <xsl:with-param name="string" select="normalize-space($srt-options-string)"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:call-template name="space"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> listbox end </xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="CreateBlankOption">
        <xsl:param name="id"/>
        <xsl:call-template name="option">
            <xsl:with-param name="name">
                <xsl:value-of select="$empty"/>
            </xsl:with-param>
            <xsl:with-param name="value">
                <xsl:value-of select="$empty"/>
            </xsl:with-param>
            <xsl:with-param name="selected">
                <xsl:value-of select="$false"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="srt-codes">
        <xsl:param name="default"/>
        <xsl:param name="delimiter"/>
        <xsl:param name="selected-option"/>
        <xsl:param name="selected-options"/>
        <xsl:param name="string"/>
<!--
        <xsl:comment> selected-option = '<xsl:value-of select="$selected-option"/>' </xsl:comment>
        <xsl:comment> selected-options = '<xsl:value-of select="$selected-options"/>' </xsl:comment>
-->
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter) ">
                <xsl:call-template name="srt-create-options">
                    <xsl:with-param name="default" select="normalize-space($default)"/>
                    <xsl:with-param name="delimiter" select="'$'"/>
                    <xsl:with-param name="string" select="normalize-space(substring-before($string, $delimiter))"/>
                    <xsl:with-param name="selected-option" select="normalize-space($selected-option)"/>
                    <xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
                </xsl:call-template>
                <xsl:call-template name="srt-codes">
                    <xsl:with-param name="default" select="normalize-space($default)"/>
                    <xsl:with-param name="delimiter" select="'|'"/>
                    <xsl:with-param name="string" select="normalize-space(substring-after($string, $delimiter))"/>
                    <xsl:with-param name="selected-option" select="normalize-space($selected-option)"/>
                    <xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="srt-create-options">
        <xsl:param name="default"/>
        <xsl:param name="delimiter"/>
        <xsl:param name="string"/>
        <xsl:param name="selected-option"/>
        <xsl:param name="selected-options"/>
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter) ">
                <option>
                    <xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before($string, $delimiter))"/></xsl:attribute>
                    <xsl:if test="string-length(normalize-space($selected-option))=0 and normalize-space($default)=normalize-space(substring-before($string, $delimiter))">
                        <xsl:attribute name="selected">selected</xsl:attribute>
                    </xsl:if>
                    <xsl:choose>
                        <xsl:when test="string-length($selected-option) != 0">
                            <xsl:if test="normalize-space($selected-option)=normalize-space(substring-before($string, $delimiter))">
                                <xsl:attribute name="selected">selected</xsl:attribute>
                            </xsl:if>
                        </xsl:when>
                        <xsl:when test="string-length($selected-options) != 0">
                            <xsl:call-template name="srt-check-list-for-selected">
                                <xsl:with-param name="delimiter" select="'|'"/>
                                <xsl:with-param name="criteria" select="normalize-space(substring-before($string, $delimiter))"/>
                                <xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
                            </xsl:call-template>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="substring-after($string, $delimiter)"/>
                </option>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="srt-check-list-for-selected">
        <xsl:param name="delimiter"/>
        <xsl:param name="criteria"/>
        <xsl:param name="selected-options"/>
        <xsl:choose>
            <xsl:when test="contains($selected-options, $delimiter) ">
                <xsl:if test="normalize-space($criteria)=normalize-space(substring-before($selected-options, $delimiter))">
                    <xsl:attribute name="selected">selected</xsl:attribute>
                </xsl:if>
                <xsl:call-template name="srt-check-list-for-selected">
                    <xsl:with-param name="delimiter" select="'|'"/>
                    <xsl:with-param name="criteria" select="normalize-space($criteria)"/>
                    <xsl:with-param name="selected-options" select="normalize-space(substring-after($selected-options, $delimiter))"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="srt-codes-readonly">
        <xsl:param name="delimiter"/>
        <xsl:param name="string"/>
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter) ">
                <xsl:call-template name="srt-create-options-readonly">
                    <xsl:with-param name="delimiter" select="'$'"/>
                    <xsl:with-param name="string" select="substring-before($string, $delimiter)"/>
                    <xsl:with-param name="value" select="$value"/>
                </xsl:call-template>
                <xsl:call-template name="srt-codes-readonly">
                    <xsl:with-param name="delimiter" select="'|'"/>
                    <xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
                    <xsl:with-param name="value" select="$value"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="srt-create-options-readonly">
                    <xsl:with-param name="delimiter" select="'$'"/>
                    <xsl:with-param name="string" select="$string"/>
                    <xsl:with-param name="value" select="$value"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="srt-create-options-readonly">
        <xsl:param name="delimiter"/>
        <xsl:param name="string"/>
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter) ">
                <xsl:if test="$value=substring-before($string, $delimiter)">
                    <xsl:value-of select="substring-after($string, $delimiter)"/>
                </xsl:if>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
