<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:srt-codes="http://www.cdc.gov/nedss/GetSRTCodes/1.0">
	<xsl:output indent="yes"/>
	<xsl:template match="*">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="root">
		<group name="Preview">
			<xsl:for-each select="StateDefinedDT">
				<line>
					<xsl:element name="element">
									<!--		label attribute		-->
									<xsl:attribute name="label"><xsl:value-of select="label"/></xsl:attribute>
									<!--		type attribute		-->
									<xsl:choose>
										<!--		if SRT type		-->
										<xsl:when test="type='srt' ">
											<xsl:attribute name="type">select</xsl:attribute>
											<srt-options-string>
												<srt-codes:getCodedValues>
													<type>
														<xsl:value-of select="srtCode"/>
													</type>
												</srt-codes:getCodedValues>
											</srt-options-string>
										</xsl:when>
										<!--		if Date  type		-->
										<xsl:when test="type='date' ">
											<xsl:attribute name="type">text</xsl:attribute>
										</xsl:when>
										<!--		if numeric  type		-->
										<xsl:when test="type='numeric' ">
											<xsl:attribute name="type">text</xsl:attribute>
										</xsl:when>

										<xsl:otherwise>
											<xsl:attribute name="type"><xsl:value-of select="type"/></xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
									<!--		size attribute		-->
									<xsl:attribute name="size"><xsl:value-of select="fieldSize"/></xsl:attribute>
									<!--		struts mapping name attribute		-->
									<xsl:attribute name="name">value(<xsl:value-of select="position()"/>)</xsl:attribute>
									<!--		validation attribute		-->
									<xsl:element name="validation">
										<xsl:attribute name="type"><xsl:value-of select="validation"/></xsl:attribute>
									</xsl:element>
									<!--xsl:element name="value">ldf-<xsl:value-of select="position()"/></xsl:element-->
								</xsl:element>
								<element/>
				</line>
			</xsl:for-each>
			<!--xsl:apply-templates/-->
		</group>
	</xsl:template>
	<!--xsl:template match="StateDefinedDT">
		<line>
			<xsl:element name="element">
				<xsl:attribute name="label"><xsl:value-of select="label"/></xsl:attribute>
				<xsl:attribute name="type"><xsl:value-of select="type"/></xsl:attribute>
				<xsl:attribute name="validation"><xsl:value-of select="validation"/></xsl:attribute>
				<xsl:attribute name="fieldSize"><xsl:value-of select="fieldSize"/></xsl:attribute>
				
			</xsl:element>
		</line>
	</xsl:template-->
</xsl:stylesheet>
