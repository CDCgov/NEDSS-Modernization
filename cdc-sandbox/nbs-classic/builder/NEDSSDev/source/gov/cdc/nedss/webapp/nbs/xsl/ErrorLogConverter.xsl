<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
		
	<xsl:template match="/">
		<ErrorLog>
			<xsl:for-each select="/csvFile/line">
							<Error>
								<xsl:attribute name="id"><xsl:value-of select="value[position()=1]"></xsl:value-of></xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="value[position()=2]"></xsl:value-of></xsl:attribute>
							</Error>
			</xsl:for-each>	
		</ErrorLog>			
	</xsl:template>	
	
</xsl:stylesheet>
