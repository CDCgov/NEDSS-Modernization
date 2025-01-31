<!-- Author:LP FU
	ActionConverter.xsl: to enerate Struts actions mappings from the NBSContextMap.xml file
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="//NBSPages">
	<action-mappings>
		<xsl:comment>load actions</xsl:comment>
		<xsl:for-each select="NBSPage">					
			<xsl:for-each select="TaskOverrides/TaskOverride">
				<action>
					<xsl:attribute name="path">/Load<xsl:value-of select="@taskName"/></xsl:attribute>
					<xsl:attribute name="type">gov.cdc.nedss.wum.commands.classLoad</xsl:attribute>
					<xsl:attribute name="name">form</xsl:attribute>
					<xsl:attribute name="scope">request</xsl:attribute>					
					<forward>
						<xsl:attribute name="name">XSP</xsl:attribute>
						<xsl:attribute name="path">/wum/xsp_path</xsl:attribute>						
					</forward>
				</action>
			</xsl:for-each>
		</xsl:for-each>
		<xsl:comment>submit actions</xsl:comment>
		<xsl:for-each select="NBSPage">					
			<xsl:for-each select="TaskOverrides/TaskOverride">
				<action>
					<xsl:attribute name="path">/<xsl:value-of select="@taskName"/></xsl:attribute>
					<xsl:attribute name="type">gov.cdc.nedss.wum.commands.classSubmit</xsl:attribute>
					<xsl:attribute name="name">form</xsl:attribute>
					<xsl:attribute name="scope">request</xsl:attribute>					
					<xsl:for-each select="NBSPageContext/TaskStarts/TaskStart">
						<forward>
							<xsl:attribute name="name"><xsl:value-of select="@action"/></xsl:attribute>
							<xsl:attribute name="path">/Load<xsl:value-of select="@taskName"/>.do</xsl:attribute>
						</forward>
					</xsl:for-each>					
				</action>					
			</xsl:for-each>			
		</xsl:for-each>
	</action-mappings>
</xsl:template>	
</xsl:stylesheet>
