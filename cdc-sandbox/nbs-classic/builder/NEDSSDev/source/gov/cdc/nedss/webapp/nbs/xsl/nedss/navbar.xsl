<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:ext="urn:ext" xmlns:xalan="http://xml.apache.org/xalan" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="ext xalan xsl">
	<xsl:template match="navbar"/>
	<xsl:template name="navbar">
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
			<xsl:comment> navbar begin </xsl:comment>
		</xsl:if>
		<xsl:variable name="active">
			<xsl:call-template name="getParameter">
				<xsl:with-param name="name" select="'navbar'"/>
				<xsl:with-param name="type" select="'string'"/>
			</xsl:call-template>
		</xsl:variable>
		<table width="750" border="0" cellspacing="0" cellpadding="0">
			<tr bgcolor="#003470">
				<td>
					<table border="0" bgcolor="#003470" cellspacing="0" cellpadding="2" align="left">
						<tbody>
							<tr>
								<xsl:for-each select="//navbar/selections/heading">
									<td align="left">
										<xsl:if test="sub-heading">
											<xsl:attribute name="bgcolor">#DGDGDG</xsl:attribute>
										</xsl:if>
										<a class="navbar">
											<xsl:attribute name="href"><xsl:value-of select="normalize-space(reference)"/></xsl:attribute>
											<font class="boldTenWhite">
												<xsl:if test="sub-heading">
													<xsl:attribute name="class">boldTenBlack</xsl:attribute>
												</xsl:if>
												<xsl:value-of select="@label"/>
											</font>
										</a>
									</td>
									<xsl:if test="position() != last()">
										<td>
											<font class="boldTenWhite">|</font>
										</td>
									</xsl:if>
								</xsl:for-each>
							</tr>
							<xsl:choose>
								<xsl:when test="//navbar/selections/heading/sub-heading">
									<tr>
										<xsl:for-each select="//navbar/selections/heading[following::heading/sub-heading]">
											<td bgcolor="#003470"/>
											<td bgcolor="#003470"/>
										</xsl:for-each>
										<td bgcolor="#DGDGDG">
											<xsl:attribute name="colspan"><xsl:value-of select="count(//navbar/selections/heading/sub-heading)+count(//navbar/selections/heading/sub-heading)"/></xsl:attribute>
											<table>
												<tbody>
													<tr>
														<xsl:for-each select="//navbar/selections/heading/sub-heading">
															<td>
																<xsl:attribute name="class">cursorHand</xsl:attribute>
																<a class="navbar">
																	<xsl:attribute name="tabindex"><xsl:value-of select="position()"/></xsl:attribute>
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(reference)"/></xsl:attribute>
																	<font class="boldEightBlack">
																		<xsl:value-of select="label"/>
																	</font>
																</a>
															</td>
															<xsl:if test="position() != last()">
																<td>|</td>
															</xsl:if>
														</xsl:for-each>
													</tr>
												</tbody>
											</table>
										</td>
										<td bgcolor="#003470" colspan="20"/>
									</tr>
								</xsl:when>
								
								<xsl:when test="//navbar/selections/hiddenFieldIsTest">
										<input type = "hidden">
															<xsl:attribute name="value"><xsl:value-of select="//navbar/selections/hiddenFieldIsTest/value"/></xsl:attribute>
															<xsl:attribute name="id"><xsl:value-of select="//navbar/selections/hiddenFieldIsTest/id"/></xsl:attribute>
													</input>
													
										</xsl:when>		
										
										
								<xsl:otherwise>
											
										</xsl:otherwise>
							</xsl:choose>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		<xsl:choose>
			<xsl:when test="//navbar/sessioninfo">
					<script type="text/JavaScript">
						function startCountdown() {
							var sessionTimeout = <xsl:value-of select="normalize-space(//navbar/sessioninfo)"/>;
							min = sessionTimeout / 60;
							sec = 0;
							getTimerCountDown();
						}			
					</script>	
			</xsl:when>
		</xsl:choose>
		<xsl:if test="$verbose = $true">
			<xsl:comment> navbar end </xsl:comment>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
