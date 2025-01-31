<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:ext="urn:ext" xmlns:xalan="http://xml.apache.org/xalan" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="ext xalan xsl">
	<xsl:template match="head">
		<xsl:call-template name="head"/>
	</xsl:template>
	<xsl:template name="head">
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
			<xsl:comment> head begin </xsl:comment>
		</xsl:if>
		<xsl:variable name="title">
			<xsl:call-template name="getParameter">
				<xsl:with-param name="name" select="'title'"/>
				<xsl:with-param name="type" select="'string'"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="windowtitle">
			<xsl:call-template name="getParameter">
				<xsl:with-param name="name" select="'windowtitle'"/>
				<xsl:with-param name="type" select="'string'"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="headtitle">
			<xsl:choose>
				<xsl:when test="$windowtitle = '[null]'">
					<xsl:value-of select="'NBS'"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$windowtitle"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<head>
			<title>
				<xsl:value-of select="$headtitle"/>
			</title>
			<meta http-equiv="imagetoolbar" content="no"/>
			<meta http-equiv="MSThemeCompatible" content="no"/>
			<meta name="Author" content="Computer Sciences Corporation"/>
			<meta name="Generator" content="NEDSS"/>
			<link href="nedss2.css" rel="stylesheet" type="text/css"/>
			<link rel="stylesheet" type="text/css" href="nedss.css"/>
			<script src="CalendarPopup.js" type="text/JavaScript">;</script>
			<script src="PopupWindow.js" type="text/JavaScript">;</script>
			<script src="date.js" type="text/JavaScript">;</script>
			<script src="AnchorPosition.js" type="text/JavaScript">;</script>
			<script src="sniffer.js" type="text/JavaScript">;</script>
			<script src="EntitySearch.js" type="text/JavaScript">;</script>
			<script src="nedss.js" type="text/JavaScript">;</script>
			<script src="Globals.js" type="text/JavaScript">;</script>
			<xsl:if test="count(//parameters/parameter) > 0">
				<script type="text/JavaScript">
					<xsl:for-each select="//parameters/parameter">
						<xsl:variable name="code">
							<xsl:value-of select="'var _'"/>
							<xsl:value-of select="@name"/>
							<xsl:value-of select="' = '"/>
                            '<xsl:value-of select="."/>'
                            <xsl:value-of select="'; '"/>
						</xsl:variable>
						<xsl:value-of select="normalize-space($code)"/>
					</xsl:for-each>
				</script>
			</xsl:if>
			<xsl:apply-templates/>
		</head>
		<xsl:if test="$verbose = $true">
			<xsl:comment> head end </xsl:comment>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
