<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="@*|/|node()"/>
	<xsl:template match="/">
		<HTML>

			<meta content="">
			<meta http-equiv="cache-control" content="no-cache"/>
			<meta http-equiv="expires" content="0"/>
			<meta http-equiv="pragma" content="no-cache"/>
			
						
			<SCRIPT Language="JavaScript" Type="text/javascript">

				function startCountdown() {
					var sessionTimeout = <xsl:value-of select="normalize-space(navbar/sessioninfo)"/>;
					min = sessionTimeout / 60;
					sec = 0;
					getTimerCountDown();
				}
				function openHelpWindow(){
					var varStartWidth  = screen.width - screen.availWidth;
		                    var varStartHeight = screen.height - screen.availHeight;
		                    var varMarginWidth = varStartWidth + varStartHeight;
		                    var varMarginHeight= varStartWidth + varStartHeight;
		                    var varWinWidth    = screen.availWidth - (varMarginWidth * 3);
		                    var varWinHeight   = screen.availHeight - (varMarginHeight * 4);
		                    var varWinLeft     = varMarginWidth;
		                    var varWinTop      = varMarginHeight;
		                    var s = "";
		                    var w = null;
		                    s += "toolbar=1,";
		                    s += "scrollbars=1,";
		                    s += "resizable=1,";
		                    s += "width=" + varWinWidth + ",";
		                    s += "height=" + varWinHeight;
		                    w = window.open("/nbs/help/index.html", "help", s);

				}

				function openInfoWindow(){
					 var varStartWidth  = screen.width - screen.availWidth;
		                    var varStartHeight = screen.height - screen.availHeight;
		                    var varMarginWidth = varStartWidth + varStartHeight;
		                    var varMarginHeight= varStartWidth + varStartHeight;
		                    var varWinWidth    = screen.availWidth - (varMarginWidth * 8);
		                    var varWinHeight   = screen.availHeight - (varMarginHeight * 10);
		                    var varWinLeft     = varMarginWidth;
		                    var varWinTop      = varMarginHeight;
		                    var s = "";
		                    var t = null;
		                    s += "toolbar=1,";
		                    s += "scrollbars=1,";
		                    s += "resizable=1,";
		                    s += "width=" + varWinWidth + ",";
		                    s += "height=" + varWinHeight;
		                     t = window.open("Info", "buildInfo", s);
				}
			</SCRIPT>
			</meta>
			<BODY>
				<table width="750" border="0" cellspacing="0" cellpadding="0">
					<!--tr>
                        <td width="126" bgcolor="white">
                            <img src="logo.jpg" width="126" height="63" border="0" title="Logo"/>
                        </td>
                    </tr-->
					<!--tr>
                        <td>
                            <img src="transparent.gif" width="0" height="8" border="0" alt=""/>
                        </td>
                    </tr-->
					<!--		the top picture goes here	-->
					<!--tr>
                        <td align="right" bgcolor="#003470">
                            <xsl:element name="img">
                                <xsl:attribute name="src"><xsl:value-of select="./navbar/top-graphic/image"/></xsl:attribute>
                                <xsl:attribute name="width"><xsl:value-of select="./navbar/top-graphic/image/@width"/></xsl:attribute>
                                <xsl:attribute name="height"><xsl:value-of select="./navbar/top-graphic/image/@height"/></xsl:attribute>
                                <xsl:attribute name="border">0</xsl:attribute>
                                <xsl:attribute name="alt"><xsl:value-of select="./navbar/top-graphic/image/@alt"/></xsl:attribute>
                            </xsl:element>
                        </td>
                    </tr-->
					<tr bgcolor="#003470">
						<td>
							<table border="0" bgcolor="#003470" cellspacing="0" cellpadding="2" align="left">
								<tbody>
									<tr>
										<xsl:for-each select="./navbar/selections/heading">
											<td align="left">
												<xsl:if test="sub-heading">
													<xsl:attribute name="bgcolor">#DGDGDG</xsl:attribute>
												</xsl:if>
												<a class="navbar">
												<xsl:if test="@new">
														<xsl:attribute name="target">_blank</xsl:attribute>
												</xsl:if>
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
										<xsl:when test="./navbar/selections/heading/sub-heading">
											<tr>
												<xsl:if test="navbar/selections/heading/@showFromBeginning">
													<td bgcolor="#003470"/>
													<td bgcolor="#003470"/>
												</xsl:if>
												<td bgcolor="#DGDGDG">
													<xsl:attribute name="colspan"><xsl:value-of select="count(navbar/selections/heading/sub-heading)+count(navbar/selections/heading/sub-heading)"/></xsl:attribute>
													<table>
														<tbody>
															<tr>
																<xsl:for-each select="./navbar/selections/heading/sub-heading">
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
										<xsl:otherwise>

										</xsl:otherwise>
									</xsl:choose>
								</tbody>
							</table>
						</td>
					</tr>
					<!--tr>
								<td colspan="100%">
														<img src="dropshadow.gif" width="750" height="9" border="0" alt=""/>
												</td>


					</tr-->
					<!--		the bottom picture goes here	-->
					<!--tr>
                        <td align="right" bgcolor="#003470">
                            <xsl:element name="img">
                                <xsl:attribute name="src"><xsl:value-of select="./navbar/bottom-graphic/image"/></xsl:attribute>
                                <xsl:attribute name="width"><xsl:value-of select="./navbar/bottom-graphic/image/@width"/></xsl:attribute>
                                <xsl:attribute name="height"><xsl:value-of select="./navbar/bottom-graphic/image/@height"/></xsl:attribute>
                                <xsl:attribute name="border">0</xsl:attribute>
                                <xsl:attribute name="alt"><xsl:value-of select="./navbar/bottom-graphic/image/@alt"/></xsl:attribute>
                            </xsl:element>
                        </td>
                    </tr-->
				</table>
				<!--xsl:if test="//navbar//context/info">
                    <br/>
                    <table border="1">
                        <xsl:for-each select="//navbar//context/info">
                            <xsl:if test="@name">
                                <tr>
                                    <td class="ContextInfoName">
                                        <xsl:value-of select="@name"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="@value">
                                <tr>
                                    <td class="ContextInfoValue">
                                        <xsl:value-of select="@value"/>
                                    </td>
                                </tr>
                            </xsl:if>
                        </xsl:for-each>
                    </table>
                </xsl:if-->
			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
