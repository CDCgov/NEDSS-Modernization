<!--
    [XSL-XSLT] This stylesheet automatically updated from an IE5-compatible XSL stylesheet to XSLT.
    The following problems which need manual attention may exist in this stylesheet:
    -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- [XSL-XSLT] Simulate lack of built-in templates -->
	<xsl:template match="@*|/|node()"/>
	<!-- Match The Root Node -->
	<xsl:template match="/">
		<HTML>
			<BODY>
			
				<table width="126" border="0" cellspacing="0" cellpadding="0">
					<tr valign="top">
						<td rowspan="100%" width="126" bgcolor="white">
							<img src="/nedss/web/resources/images/logo.jpg" width="126" height="57" border="0" alt="I am a logo "/>
						</td>
						<td>
							<table role ="presentation"  width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<!-- BEGIN INFO TABLE -->
									<td valign="top">
										<!-- LAYOUT TABLE START -->
										<table width="624" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td colspan="2" bgcolor="#003470"></td>
											</tr>
											<tr>
												<td bgcolor="#DCDCDC" class="boldTwelveBlack">
													<!--		the page name goes here		-->
													<!--<xsl:value-of select="./template/page-name"></xsl:value-of>-->
													<page-title></page-title>
													
													
												</td>
												<td align="right" bgcolor="#003470" class="boldTenYellow">
													<!--		the name of the user goes here	-->
													<xsl:value-of select="./template/user-name"></xsl:value-of>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<img src="/nedss/web/resources/images/dropshadow.gif" width="624" height="9" border="0" alt=""/>
												</td>
											</tr>
											<!-- PER128 -->
											<tr>
												<td colspan="2" align="right">
													<!--		some sort of unique identifier goes here	-->
													<xsl:value-of select="./template/uid/type"></xsl:value-of>:<xsl:value-of select="./template/uid/value"></xsl:value-of>
												</td>
											</tr>
										</table>
										<!-- LAYOUT TABLE END -->
									</td>
									<!-- END INFO TABLE -->
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
