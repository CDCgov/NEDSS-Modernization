<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="tab">
		<html lang="en">
			<head>
				<title>
					<xsl:value-of select="title"/>
				</title>
				<link rel="STYLESHEET" type="text/css" href="/nedss/web/resources/styles/basemasterstyles.css"/>

				<SCRIPT Type="text/javascript" Language="JavaScript"><![CDATA[	
					function NoOp()
					{
					    return;
					}

				
					
					function ShowPropertiesOfElement(element) {
						alert(element);
					
					}
					
					function CreateNewElement() {
					
					}
					
					function CreateNewLine() {
					
					}
					
					function CreateNewGroup() {
					
					}


					
				]]></SCRIPT>
			</head>
			<body>
				<center>
					<h3>Administration Extension Prototype</h3>
					<p><xsl:value-of select="@name"/></p>
				</center>
				<xsl:for-each select="group">
					<table cellpadding="4" cellspacing="4" border="0" bgcolor="white">
						<thead>
							<tr><th><xsl:value-of select="@name"/></th></tr>
						</thead>
						<tbody>
							<xsl:for-each select="line">
								<tr>
									<xsl:for-each select="element">
										<td bgcolor="#e4f7e4" align="center">
											<a  href="javascript:NoOp();">
												<xsl:attribute name="onclick">ShowPropertiesOfElement('<xsl:value-of select="@name"/>');</xsl:attribute>	
												<xsl:value-of select="@label"/> - <xsl:value-of select="@name"/>
											</a>
										</td>	
									</xsl:for-each>
								<!--			add a new element option		-->		
											<td bgcolor="#e4ebf8" align="center">
												<a  href="javascript:NoOp();">
													<xsl:attribute name="onclick">CreateNewElement('<xsl:value-of select="@name"/>');</xsl:attribute>	
													create new element
												</a>
											</td>

								</tr>
							</xsl:for-each>
							
								<!--			add a line option			-->
								<tr>
									<td bgcolor="#eeeeee" colspan="100%" align="center">
										<a  href="javascript:NoOp();">
											<xsl:attribute name="onclick">CreateNewLine('<xsl:value-of select="@name"/>');</xsl:attribute>	
											create new line
										</a>
									</td>
								</tr>
						
						
						</tbody>
						<tfoot></tfoot>
					</table>
				</xsl:for-each>
				
				<!--		add a new group option		-->
				<table cellpadding="4" cellspacing="4" border="0" bgcolor="white">
					<tbody>
						
						<tr>
							<td bgcolor="#c9ddf1" colspan="100%" align="center">
								<a  href="javascript:NoOp();">
									<xsl:attribute name="onclick">CreateNewGroup('<xsl:value-of select="@name"/>');</xsl:attribute>	
									create new group
								</a>
							</td>
						</tr>

					</tbody>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
