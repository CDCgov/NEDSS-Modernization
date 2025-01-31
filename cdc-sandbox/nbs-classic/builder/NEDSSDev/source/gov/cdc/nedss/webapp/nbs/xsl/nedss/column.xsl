<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="column">
        <xsl:variable name="id">
            <xsl:choose>
                <xsl:when test="parent::row/parent::conditional">
                    <xsl:value-of select="parent::row/parent::conditional/@ref"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="parent::row/parent::conditional/@value"/>
                    <xsl:value-of select="$underline"/>
                    <xsl:value-of select="generate-id(.)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="@id">
                            <xsl:value-of select="@id"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$empty"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="background">
            <xsl:choose>
                <xsl:when test="@background">
                    <xsl:value-of select="@background"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="../@background"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="display">
            <xsl:choose>
                <xsl:when test="parent::row/parent::conditional">
                    <xsl:value-of select="'Hidden'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="@display">
                            <xsl:value-of select="@display"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="../@display"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="FirstRowID">
            <xsl:value-of select="generate-id(ancestor::box//row[1])"/>
        </xsl:variable>
        <xsl:variable name="LastRowID">
            <xsl:value-of select="generate-id(ancestor::box//row[last()])"/>
        </xsl:variable>
        <xsl:variable name="CurrentRowID">
            <xsl:value-of select="generate-id(ancestor::row)"/>
        </xsl:variable>
        <xsl:variable name="bordertype">
            <xsl:choose>
                <xsl:when test="@bordertype">
                    <xsl:value-of select="@bordertype"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="ancestor::box">
                            <xsl:if test="$CurrentRowID = $FirstRowID">
                                <xsl:value-of select="' BorderTop'"/>
                                <xsl:if test="position() = 1">
                                    <xsl:value-of select="' BorderLeft'"/>
                                </xsl:if>
                                <xsl:if test="position() = last()">
                                    <xsl:value-of select="' BorderRight'"/>
                                </xsl:if>
                            </xsl:if>
                            <xsl:if test="$CurrentRowID = $LastRowID">
                                <xsl:value-of select="' BorderBottom'"/>
                                <xsl:if test="position() = 1">
                                    <xsl:value-of select="' BorderLeft'"/>
                                </xsl:if>
                                <xsl:if test="position() = last()">
                                    <xsl:value-of select="' BorderRight'"/>
                                </xsl:if>
                            </xsl:if>
                            <xsl:if test="($CurrentRowID != $FirstRowID) and ($CurrentRowID != $LastRowID)">
                                <xsl:if test="position() = 1">
                                    <xsl:value-of select="' BorderLeft'"/>
                                </xsl:if>
                                <xsl:if test="position() = last()">
                                    <xsl:value-of select="' BorderRight'"/>
                                </xsl:if>
                                <xsl:if test="(position() != 1) and (position() != last())">
                                    <xsl:value-of select="' NoBorder'"/>
                                </xsl:if>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="' NoBorder'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="size">
            <xsl:choose>
                <xsl:when test="position() = 1">
                    <xsl:choose>
                        <xsl:when test="ancestor::section/part/attributes/SizeOfFirstColumn">
                            <xsl:choose>
                                <xsl:when test="@width = ancestor::section/part/attributes/width">
                                    <xsl:value-of select="'0'"/>
                                </xsl:when>
                                <xsl:when test="ancestor::grid">
                                    <xsl:choose>
                                        <xsl:when test="@size">
                                            <xsl:value-of select="@size"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="'0'"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="ancestor::section/part/attributes/SizeOfFirstColumn"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="@size">
                                    <xsl:value-of select="@size"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'0'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="@size">
                            <xsl:value-of select="@size"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'0'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:call-template name="column">
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="background" select="$background"/>
            <xsl:with-param name="display" select="$display"/>
            <xsl:with-param name="align" select="@align"/>
            <xsl:with-param name="valign" select="@valign"/>
            <xsl:with-param name="size" select="$size"/>
            <xsl:with-param name="width" select="@width"/>
            <xsl:with-param name="bordertype" select="$bordertype"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="column">
        <xsl:param name="id"/>
        <xsl:param name="background"/>
        <xsl:param name="display"/>
        <xsl:param name="align"/>
        <xsl:param name="valign"/>
        <xsl:param name="size"/>
        <xsl:param name="width"/>
        <xsl:param name="bordertype"/>
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
            <xsl:comment> column begin </xsl:comment>
        </xsl:if>
        <xsl:variable name="class">
            <xsl:call-template name="VerifyBackground">
                <xsl:with-param name="background" select="$background"/>
            </xsl:call-template>
            <xsl:value-of select="$space"/>
            <xsl:call-template name="VerifyDisplay">
                <xsl:with-param name="display" select="$display"/>
            </xsl:call-template>
            <xsl:if test="$bordertype">
                <xsl:value-of select="$space"/>
                <xsl:value-of select="$bordertype"/>
            </xsl:if>
        </xsl:variable>
        <td>
            <xsl:if test="string-length($id) > 0">
                <xsl:attribute name="id">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="class">
                <xsl:value-of select="$class"/>
            </xsl:attribute>
            <xsl:attribute name="align">
                <xsl:choose>
                    <xsl:when test="$align = 'center'">
                        <xsl:value-of select="'center'"/>
                    </xsl:when>
                    <xsl:when test="$align = 'right'">
                        <xsl:value-of select="'right'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'left'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:if test="$valign">
                <xsl:attribute name="valign">
                    <xsl:choose>
                        <xsl:when test="$valign = 'top'">
                            <xsl:value-of select="'top'"/>
                        </xsl:when>
                        <xsl:when test="$valign = 'bottom'">
                            <xsl:value-of select="'bottom'"/>
                        </xsl:when>
                        <xsl:when test="$valign = 'middle'">
                            <xsl:value-of select="'middle'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'baseline'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$size > 0">
                <xsl:attribute name="style">
                    <xsl:value-of select="'width:'"/>
                    <xsl:value-of select="$size"/>
                    <xsl:value-of select="'px;'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="colspan">
                <xsl:choose>
                    <xsl:when test="$width">
                        <xsl:value-of select="$width"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'1'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:apply-templates/>
        </td>
        <xsl:if test="$verbose = $true">
            <xsl:comment> column end </xsl:comment>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
