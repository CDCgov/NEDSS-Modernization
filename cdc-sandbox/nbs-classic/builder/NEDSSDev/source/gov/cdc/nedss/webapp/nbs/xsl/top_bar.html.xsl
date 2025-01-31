<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- [XSL-XSLT] Simulate lack of built-in templates -->
	<xsl:template match="@*|/|node()"/>
	<!-- Match The Root Node -->
	<xsl:template match="/">
		<current-tab><xsl:value-of select="./template/current-tab"/></current-tab>
		<HTML>
			<BODY>
			
							<table role ="presentation" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									
									<!-- BEGIN INFO TABLE -->   
									
									<td valign="top" width="100%">
										<!-- LAYOUT TABLE START -->
										<table border="0" cellspacing="0" cellpadding="0" width="100%">
											<tr>
												<td colspan="2" bgcolor="#003470" height="15" width="100%"></td>
												<td rowspan="2"  ><img src="../../images/nedssLogo.jpg" width="80" height="32" border="0" title="Logo" style="background : #DCDCDC"/>&nbsp;</td>
											</tr>
											<tr>
												
												<td bgcolor="#DCDCDC" class="boldTwelveBlack">
													<!--		the page name goes here		-->
													<!--<xsl:value-of select="./template/page-name"></xsl:value-of>-->
													<xsl:choose>
														<xsl:when test="string-length(normalize-space(./template/page-name))>0">&nbsp;<xsl:value-of select="normalize-space(./template/page-name)"></xsl:value-of></xsl:when>
														<xsl:otherwise>&nbsp;<page-title></page-title></xsl:otherwise>
													</xsl:choose>
													
													
													
												</td>
												<td align="right" bgcolor="#003470" class="boldTenYellow">
													<xsl:if test="string-length(normalize-space(./template/user-name)) != 0">
														User: <xsl:value-of select="./template/user-name"></xsl:value-of>&nbsp;
													</xsl:if>
												</td>
												
												<xsl:if test="string-length(normalize-space(./template/hiddenFieldIsTest)) != 0">
													<input type = "hidden">
															<xsl:attribute name="value"><xsl:value-of select="/template/hiddenFieldIsTest/value"/></xsl:attribute>
															<xsl:attribute name="id"><xsl:value-of select="/template/hiddenFieldIsTest/id"/></xsl:attribute>
													</input>
												</xsl:if>
											</tr>
											<tr>
												
												<td colspan="100%">
													<img src="dropshadow.gif" width="100%" height="9" border="0" alt=""/>
												</td>
												
											</tr>
											
											
											
										</table>
										<!-- LAYOUT TABLE END -->
									</td>
									<!-- END INFO TABLE -->
								</tr>
								
							</table>
						

			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
