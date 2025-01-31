<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:ext="urn:ext" xmlns:xalan="http://xml.apache.org/xalan" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="ext xalan xsl">
	<xsl:template match="combobox">
		<xsl:call-template name="combobox">
			<xsl:with-param name="id" select="@id"/>
			<xsl:with-param name="name" select="@name"/>
			<xsl:with-param name="max" select="@max"/>
			<xsl:with-param name="restricted" select="@restricted"/>
			<xsl:with-param name="size" select="@size"/>
			<xsl:with-param name="width" select="@width"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="combobox">
		<xsl:param name="id"/>
		<xsl:param name="name"/>
		<xsl:param name="max"/>
		<xsl:param name="width"/>
		<xsl:param name="restricted"/>
		<xsl:param name="size"/>
		<xsl:param name="width"/>
		<xsl:variable name="isRestricted">
			<xsl:call-template name="normalizeBoolean">
				<xsl:with-param name="value" select="$restricted"/>
			</xsl:call-template>
		</xsl:variable>
		<table border="0" cellpadding="0" cellspacing="0">
			<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/>_ac_table</xsl:attribute>
			<xsl:if test="not(@size) and not(@autocomplete='disable')">
				<tr>
					<!--autocomplete drop down feature-->
					<td>
						<input type="text" size="30">
							<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>_textbox</xsl:attribute>
							<xsl:if test="@length">
								<xsl:attribute name="size"><xsl:value-of select="@length"/></xsl:attribute>
							</xsl:if>
							<!--xsl:attribute name="onclick"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onselect"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onkeydown"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onkeypress"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute-->
							<xsl:attribute name="onfocus"><xsl:value-of select="'storeCaret(this);'"/>AutocompleteStoreOnFocusValue(this);</xsl:attribute>
							<!--xsl:attribute name="onkeydown">CheckTab('<xsl:value-of select="normalize-space(@name)"/>',this);</xsl:attribute-->
							<xsl:attribute name="onkeyup">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,null,event);storeCaret(this);</xsl:attribute>
							<xsl:attribute name="onblur">AutocompleteSynch('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
							<!--xsl:if test="@default">
													<xsl:attribute name="value"><xsl:value-of select="substring-before(substring-after(concat('|',srt-options-string),concat('|',concat(@default,'$'))),'|')"/></xsl:attribute>
												</xsl:if-->
							<xsl:attribute name="value"><xsl:value-of select="descendant::options/option[@selected='true']/@name"/></xsl:attribute>
						</input>
						<img src="type-ahead2.gif" border="0" alt="Choose option" align="top">
							<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>_button</xsl:attribute>
							<xsl:attribute name="onclick">AutocompleteExpandListbox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
						</img>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td colspan="2">
					<select id="{@id}" name="{@name}" autocomplete="off" onchange="{event}">
						<!--autocomplete feature-->
						<xsl:if test="not(@size) and not(@autocomplete='disable')">
							<xsl:attribute name="size">5</xsl:attribute>
							<xsl:attribute name="class">none</xsl:attribute>
							<xsl:attribute name="onclick">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,true,event);</xsl:attribute>
							<xsl:attribute name="onkeyup">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,true,event);</xsl:attribute>
							<xsl:attribute name="onblur">this.className='none';</xsl:attribute>
							<xsl:attribute name="typeahead">true</xsl:attribute>
						</xsl:if>
						<!--explicitly defined options for select-->
						<xsl:for-each select="options/option">
							<xsl:element name="option">
								<xsl:if test="@selected='true' ">
									<xsl:attribute name="selected">selected</xsl:attribute>
								</xsl:if>
								<xsl:attribute name="value"><xsl:value-of select="normalize-space(@value)"/></xsl:attribute>
								<xsl:value-of select="normalize-space(@name)"/>
							</xsl:element>
						</xsl:for-each>
						
					</select>
				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
