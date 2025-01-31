<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsp="http://apache.org/xsp" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:util="http://apache.org/xsp/util/2.0" xmlns:codes="http://www.cdc.gov/nedss/getCodedValues/1.0">


<xsl:template match="xsp:page">
	<xsp:page>
		<xsl:apply-templates select="@*"/>
		
                                                                  
      <xsl:apply-templates/>
    </xsp:page>
  </xsl:template>
   
<xsl:template match="item">
			<xsl:element name="element">
				<xsl:attribute name="name"><xsl:value-of select="@name"></xsl:value-of></xsl:attribute>
				<xsl:attribute name="type"><xsl:value-of select="type"></xsl:value-of></xsl:attribute>
				<xsl:attribute name="label"><xsl:value-of select="label"></xsl:value-of></xsl:attribute>
				
				<xsl:for-each select="note">
					<xsl:attribute name="note"><xsl:value-of select="note"></xsl:value-of></xsl:attribute>
				</xsl:for-each>
				
				<xsl:for-each select="header">
					<xsl:copy>
	      				<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
					</xsl:copy>
				</xsl:for-each>
				
				<xsl:for-each select="controller">
					<xsl:copy>
	      				<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
					</xsl:copy>
				</xsl:for-each>
				<xsl:for-each select="line">
					<xsl:copy>
	      				<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
					</xsl:copy>
				</xsl:for-each>
				<xsl:for-each select="options">
					<xsl:element name="options">
						<xsl:for-each select="option">
							<xsl:element name="option">
								<xsl:attribute name="name"><xsl:value-of select="name"></xsl:value-of></xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="value"></xsl:value-of></xsl:attribute>
							</xsl:element>
						</xsl:for-each>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="validation">
					<xsl:copy>
	      				<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
					</xsl:copy>
				</xsl:for-each>

		
			<xsl:for-each select="html">
					<xsl:element name="span">
						<xsl:value-of select="html"></xsl:value-of>
					</xsl:element>
			</xsl:for-each>

		</xsl:element>
</xsl:template>
 

	<xsl:template match="@*|*|text()|processing-instruction()">           
    		<xsl:copy>
      			<xsl:apply-templates select="@*|*|text()|processing-instruction()"/>
		</xsl:copy>
  	</xsl:template>

</xsl:stylesheet>