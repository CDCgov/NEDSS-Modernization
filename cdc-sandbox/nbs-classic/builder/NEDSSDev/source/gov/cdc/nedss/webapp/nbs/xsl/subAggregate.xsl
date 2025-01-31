<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="">
	<xsl:strip-space elements="*"/>
	<xsl:template match="page">
		<content>
			<xsl:if test="content/@form">
				<xsl:attribute name="form"><xsl:value-of select="normalize-space(content/@form)"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="content/@title">
				<xsl:attribute name="title"><xsl:value-of select="normalize-space(content/@title)"/></xsl:attribute>
			</xsl:if>
			<javascript-files>
				<xsl:for-each select="content/javascript-files">
					<xsl:apply-templates/>
				</xsl:for-each>
			</javascript-files>
			<id-bar>
				<xsl:for-each select="content/id-bar">
					<xsl:apply-templates/>
				</xsl:for-each>
			</id-bar>
			<link-bar>
				<xsl:for-each select="content/link-bar">
					<xsl:apply-templates/>
				</xsl:for-each>
			</link-bar>
			<button-bar>
				<xsl:for-each select="content/button-bar">
					<xsl:apply-templates/>
				</xsl:for-each>
			</button-bar>
			<topgroup>
				<xsl:for-each select="content/topgroup">
					<xsl:if test="@anchor">
						<xsl:attribute name="anchor"><xsl:value-of select="@anchor"/></xsl:attribute>
					</xsl:if>
					<xsl:apply-templates/>
				</xsl:for-each>
			</topgroup>
			<xsl:if test="content/separator">
				<separator>
					<xsl:if test="content/separator/@name">
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(content/separator/@name)"/></xsl:attribute>
					</xsl:if>
				</separator>
			</xsl:if>
			<tab>
				<xsl:attribute name="name"><xsl:value-of select="normalize-space(content/tab/@name)"/></xsl:attribute>
				<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(content/tab/@authorized)"/></xsl:attribute>
				<xsl:attribute name="view"><xsl:value-of select="normalize-space(content/tab/@view)"/></xsl:attribute>
				<!--script to initialize the global javascript variable holding the type of XSL used in transformation-->
				<script language="javascript" type="text/javascript">
					<xsl:value-of select="content/tab/script[1]"/>
				</script>
				<!--script to initialize the js function for error handling -->
				<script language="javascript" type="text/javascript">
					<xsl:value-of select="content/tab/script[2]"/>
				</script>
				<!--jumper table, need to combine them from both xsp pages-->
				<table width="100%">
					<tr align="center">
						<td align="left">
							

							<xsl:for-each select="content/tab/table[@id='jumper-table']/thead/tr/td">
								<xsl:apply-templates/>
							<xsl:if test="position() != last()"><xsl:text disable-output-escaping="yes">&amp;nbsp;|&amp;nbsp;</xsl:text></xsl:if>
							</xsl:for-each>
						</td>
					</tr>
				</table>
				<table width="100%">
					<xsl:for-each select="content/tab/table">
						<xsl:if test="not(@id='jumper-table') ">
							<xsl:apply-templates/>
						</xsl:if>
					</xsl:for-each>
				</table>
			</tab>
		</content>
	</xsl:template>
	<xsl:template match="HTML">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="@*|*|text()" priority="-1">
		<xsl:copy>
			<xsl:apply-templates select="@*|*|text()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
