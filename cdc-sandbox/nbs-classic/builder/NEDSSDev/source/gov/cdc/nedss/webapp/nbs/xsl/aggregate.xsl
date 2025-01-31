<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	<xsl:param name="page-title"/>
	<xsl:template match="page">
		<html lang="en">
			<head>
				<title>
					<xsl:choose>
						<xsl:when test="content/@title">
							<xsl:value-of select="content/@title"/>
						</xsl:when>
						<xsl:otherwise>NBS</xsl:otherwise>
					</xsl:choose>
				</title>
				<!--link rel="stylesheet" type="text/css" href="/nedss/web/resources/styles/basemasterstyles.css"/-->
				<link rel="stylesheet" type="text/css" href="nedss.css"/>
				
				<style type="text/css">
				    div.submissionStatusMsg {height:5em; border:2px solid #3670AF; text-align:left; width:500px; margin:0 auto;}  
					div.submissionStatusMsg div.header {height:1em; line-height:1em; font:13px bold Arial; 
					        width:100%; background:#3670AF; color:#FFF; text-align:left; padding:3px; font-weight:bold; margin-bottom:5px;}
					div.submissionStatusMsg div.body {padding:10px 10px 15px 10px; font:13px normal Arial; }
				</style>
    
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="Globals.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="tabControl.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="validate.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="batch-entry-v2.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<!--SCRIPT Type="text/javascript" Language="JavaScript" SRC="PopUpBatchEntry.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT-->
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="conditional-entry.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="sniffer.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="nedss.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="ElementValidate.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="cdfSubformSpecific.js">
					<xsl:comment><![CDATA[//]]></xsl:comment>
				</SCRIPT>
				
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="CalendarPopup.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="PopupWindow.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="date.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="AnchorPosition.js">
					<xsl:comment><![CDATA[ //]]></xsl:comment>
				</SCRIPT>
				
				<!-- JQuery scripts -->
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="jquery-1.2.6.min.js">
                    <xsl:comment><![CDATA[ //]]></xsl:comment>
                </SCRIPT>
                <SCRIPT Type="text/javascript" Language="JavaScript" SRC="jquery.tablesorter.js">
                    <xsl:comment><![CDATA[ //]]></xsl:comment>
                </SCRIPT>
                <SCRIPT Type="text/javascript" Language="JavaScript" SRC="jquery.blockUI.js">
                    <xsl:comment><![CDATA[ //]]></xsl:comment>
                </SCRIPT>
                
                  <link rel="stylesheet" href="recent/common.css" type="text/css" media="screen,print"/>
                  
			    <script type="text/javascript">
			        /* This resolves any conflicts JQuery has with other libraries.
			           By doing this, jQuery will be referred by $j instead of the default $ symbol */
			        var $j = jQuery.noConflict();
			        
			        // set default values for the blockUI JQuery plugin
			        $j.blockUI.defaults.overlayCSS.opacity = '0.4';
			        $j.blockUI.defaults.css.border = '0px';
			    </script>
				
				<SCRIPT Type="text/javascript" Language="JavaScript" SRC="helperScripts.js"/>
				<!--	dynamically include javascript libraries defined inside the xsp pages	-->
				<xsl:for-each select="content/javascript-files">
					<xsl:if test="not(normalize-space(../tab/@view)='false') ">
						<xsl:for-each select="import">
							<SCRIPT Type="text/javascript" Language="JavaScript">
								<xsl:attribute name="SRC"><xsl:value-of select="."/></xsl:attribute>
								<xsl:comment><![CDATA[//]]></xsl:comment>
							</SCRIPT>
						</xsl:for-each>
						<xsl:for-each select="insert">
							<SCRIPT Type="text/javascript" Language="JavaScript">
								<xsl:value-of select="."/>
								<xsl:comment><![CDATA[//]]></xsl:comment>
							</SCRIPT>
						</xsl:for-each>
					</xsl:if>
				</xsl:for-each>
			</head>
			<xsl:variable name="currentTab">
				<!--get tab that has focus from the top_bar.xsp	-->
				<xsl:choose>
					<xsl:when test="top_bar/current-tab">
						<xsl:value-of select="top_bar/current-tab"/>
					</xsl:when>
					<xsl:otherwise>1</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<!--	need to get the unique tabs	-->
			<!--xsl:variable name="unique-tabs" select="/page/content[not(tab/@name=preceding-sibling::content/tab/@name)]/tab"/-->
			<xsl:variable name="unique-tabs" select="content/tab"/>
			<body onunload="ReturnState();" onContextMenu="return false;" onload="startCountdown(), callLoadData(); focusCriteria();autocompTxtValuesForJSP();">
			<div id="blockparent"></div>
				<!--		this is the main table		-->
				<table cellpadding="0" cellspacing="0" border="0" bgcolor="white" align="left">
					<tr valign="top">
						<!--td valign="top" rowspan="100%">
							<xsl:for-each select="navbar">
								<xsl:apply-templates/>
							</xsl:for-each>
							<xsl:if test="nav_bar">
								<img src="transparent.gif" width="125" height="1" border="0" alt=""/>
							</xsl:if>

						</td>
						<td rowspan="100%"><img src="transparent.gif" width="10" height="1" border="0" alt=""/></td-->
						<td>
							<table cellpadding="0" cellspacing="0" border="0" bgcolor="white" align="center">
								<tr>
									<td>
										<xsl:for-each select="navbar">
											<xsl:apply-templates/>
										</xsl:for-each>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2">
										<xsl:for-each select="top_bar/HTML">
											<xsl:apply-templates/>
										</xsl:for-each>
									</td>
								</tr>
								<tr valign="top">
									<!--		
					            			****	this is the content pane with the tabs inside		
					            			****												-->
									<td width="100%" valign="top">
										<table cellpadding="0" cellspacing="0" border="0" bgcolor="white">
											<!-- need to change width based on popup or not, determined if the navbar and topbar are available-->
											<xsl:choose>
												<xsl:when test="navbar and top_bar">
													<xsl:attribute name="width">750</xsl:attribute>
												</xsl:when>
												<xsl:otherwise><xsl:attribute name="width">624</xsl:attribute></xsl:otherwise>
											</xsl:choose>
											<!--			the button bar			-->
											<thead>
												<tr>
													<td>
														<table cellpadding="0" cellspacing="0" border="0" bgcolor="white" >
															<!-- need to change width based on popup or not, determined if the navbar and topbar are available-->
															<xsl:choose>
																<xsl:when test="navbar and top_bar">
																	<xsl:attribute name="width">750</xsl:attribute>
																</xsl:when>
																<xsl:otherwise><xsl:attribute name="width">624</xsl:attribute></xsl:otherwise>
															</xsl:choose>

															<tr>
																<td align="left">
																	<xsl:variable name="ids" select="content/id-bar/id[not(string-length(normalize-space(.))=0) or not(string-length(normalize-space(@id))=0)]"/>
																	<xsl:for-each select="$ids">
																		<span>
																			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
																			<xsl:attribute name="class"><xsl:choose><xsl:when test="normalize-space(@show)='false'">none</xsl:when><xsl:otherwise>visible</xsl:otherwise></xsl:choose></xsl:attribute>
																			<b><xsl:value-of select="@name"/>:&nbsp;<xsl:value-of select="."/></b>
																			<xsl:if test="following::id[not(string-length(normalize-space(.))=0)]">&nbsp;|&nbsp;</xsl:if>
																		</span>
																	</xsl:for-each>
															&nbsp;
															</td>
																<td align="right">
																&nbsp;
																<xsl:variable name="links" select="content/link-bar/link[not(string-length(normalize-space(.))=0)]"/>
																	<xsl:for-each select="$links">
																		<xsl:choose>
																			<xsl:when test="contains(., 'NOT_DISPLAYED')"/>
																			<xsl:when test="@tie-to and not(contains(@tie-to,normalize-space(substring-before($page-title,' '))))"/>
																			<xsl:otherwise>
																				<a>
																					<xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute>
																					<xsl:attribute name="onclick">changeSubmitOnce(this);</xsl:attribute>
																					<xsl:value-of select="@name"/>
																				</a>
																				<xsl:if test="following::link[not(string-length(normalize-space(.))=0)]">&nbsp;|&nbsp;</xsl:if>
																			</xsl:otherwise>
																		</xsl:choose>
																	</xsl:for-each>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<!--xsl:for-each select="content/button-bar"-->
														<div>
															<xsl:attribute name="id">buttonbartop<xsl:value-of select="@name"/></xsl:attribute>
															<!--xsl:choose>
																	<xsl:when test="position()=$currentTab or $currentTab='' or number(count($unique-tabs))&lt;number($currentTab) ">
																		<xsl:attribute name="class">visible</xsl:attribute>
																		<xsl:attribute name="selected">true</xsl:attribute>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:attribute name="class">none</xsl:attribute>
																	</xsl:otherwise>
																</xsl:choose-->
															<xsl:call-template name="button-bar">
																<xsl:with-param name="location">top</xsl:with-param>
																<xsl:with-param name="currentTab">
																	<xsl:value-of select="$currentTab"/>
																</xsl:with-param>
															</xsl:call-template>
															<xsl:for-each select="content/topgroup">
																<table cellpadding="0" cellspacing="0" border="0">
																	<xsl:variable name="current" select="ancestor::content/tab"/>
																	<xsl:if test="@anchor">
																		<xsl:attribute name="anchor"><xsl:value-of select="generate-id(ancestor::content/tab)"></xsl:value-of></xsl:attribute>
																		<xsl:for-each select="ancestor::page/content/tab">
																			<xsl:choose>
																				<xsl:when test="normalize-space($current/@name) = normalize-space(./@name) and not(position()=$currentTab)">
																					<xsl:attribute name="class">none</xsl:attribute>
																				</xsl:when>
																				<xsl:otherwise/>
																			</xsl:choose>
																		</xsl:for-each>
																	</xsl:if>
																	<tr align="left">
																		<td id="top-group">
																			<table border="0" cellpadding="2" cellspacing="2">
																				<xsl:apply-templates/>
																			</table>
																		</td>
																	</tr>
																</table>
															</xsl:for-each>
														</div>
														<!--/xsl:for-each-->
													</td>
												</tr>
											</thead>
											<!--			the tab content			-->
											<tbody>
												<!-- VL: error message TD for validation JavaScript -->
												<tr>
													<td colspan="4" id="error1" class="none"/>
												</tr>
												<tr>
													<td colspan="4" id="error2" class="none">You have entered or edited information and have not clicked on Add or Edit in the multiple entry table. Please take the appropriate action and try again.</td>
												</tr>
												<tr>
													<td colspan="4" id="errorRange" class="none">&nbsp;</td>
												</tr>
												<!-- populate the javascript array that will hold the tab names for hot key activation	-->
												<tr>
													<xsl:for-each select="$unique-tabs">
														<xsl:if test="not(string-length(normalize-space(@name))=0)">
															<td>
																<SCRIPT Type="text/javascript" Language="JavaScript">
																		tabHotKeyArray[<xsl:value-of select="position()-1"/>] = '<xsl:value-of select="generate-id()"/>';
																	</SCRIPT>
															</td>
														</xsl:if>
													</xsl:for-each>
												</tr>
												<xsl:if test="count($unique-tabs) > 1">
													<xsl:call-template name="create-tabs">
														<xsl:with-param name="location">top</xsl:with-param>
														<xsl:with-param name="currentTab">
															<xsl:value-of select="$currentTab"/>
														</xsl:with-param>
														<xsl:with-param name="tabs" select="$unique-tabs"/>
													</xsl:call-template>
												</xsl:if>
												<xsl:for-each select="content/separator">
													<xsl:call-template name="create-separator"/>
												</xsl:for-each>
												<tr>
													<td align="center">
														<!--		THE FORM ELEMENT		-->
														<xsl:choose>
															<xsl:when test="content/@form='remove'">
																<xsl:for-each select="$unique-tabs">
																	<xsl:if test="@name or count($unique-tabs)=1">
																		<div type="tab" nid="{@name}">
																			<xsl:attribute name="name"><xsl:value-of select="generate-id()"/></xsl:attribute>
																			<xsl:attribute name="id">tabControl<xsl:value-of select="generate-id()"/></xsl:attribute>
																			<xsl:choose>
																				<xsl:when test="position()=$currentTab or $currentTab='' or number(count($unique-tabs))&lt;number($currentTab) ">
																					<xsl:attribute name="class">visible</xsl:attribute>
																					<xsl:attribute name="selected">true</xsl:attribute>
																					<xsl:attribute name="focus">content</xsl:attribute>
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:attribute name="class">none</xsl:attribute>
																				</xsl:otherwise>
																			</xsl:choose>
																			<!-- 		do the payload		-->
																			<xsl:choose>
																				<xsl:when test="@authorized = 'false'">
																					<table cellpadding="2" cellspacing="0" border="0" width="590" class="TableInner">
																						<tbody>
																							<tr>
																								<td>
																									<p>
																										<center>
																											<b>You do not have access to view the information on this tab</b>
																										</center>
																									</p>
																								</td>
																							</tr>
																						</tbody>
																					</table>
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:apply-templates/>
																				</xsl:otherwise>
																			</xsl:choose>
																		</div>
																	</xsl:if>
																</xsl:for-each>
															</xsl:when>
															<xsl:otherwise>
																<form id="nedssForm" method="post">
																	<xsl:choose>
																		<xsl:when test="content/@form">
																			<xsl:attribute name="action"><xsl:value-of select="content/@form"/></xsl:attribute>
																		</xsl:when>
																		<xsl:otherwise>
																			<xsl:attribute name="action">/nbs/nfc</xsl:attribute>
																		</xsl:otherwise>
																	</xsl:choose>
																	<!-- VL: The following hidden fields are needed for paging (next, previous) functionality of enhanced-data-table; It is set by JavaScript  -->
																	<input type="hidden" name="enhancedTable_name"/>
																	<input type="hidden" name="enhancedTable_displayPage"/>
																	<xsl:for-each select="$unique-tabs">
																		<xsl:if test="@name or count($unique-tabs)=1">
																			<xsl:if test="not(normalize-space(@view)='false')">
																				<div type="tab" align="center" nid="{@name}">
																					<xsl:attribute name="name"><xsl:value-of select="generate-id()"/></xsl:attribute>
																					<xsl:attribute name="id">tabControl<xsl:value-of select="generate-id()"/></xsl:attribute>
																					<xsl:choose>
																						<xsl:when test="position()=$currentTab or $currentTab='' or number(count($unique-tabs))&lt;number($currentTab) ">
																							<xsl:attribute name="class">visible</xsl:attribute>
																							<xsl:attribute name="selected">true</xsl:attribute>
																							<xsl:attribute name="focus">content</xsl:attribute>
																						</xsl:when>
																						<xsl:otherwise>
																							<xsl:attribute name="class">none</xsl:attribute>
																						</xsl:otherwise>
																					</xsl:choose>
																					<!-- 		do the payload		-->
																					<xsl:choose>
																						<xsl:when test="normalize-space(@authorized) = 'false'">
																							<table cellpadding="2" cellspacing="0" border="0" width="590" class="TableInner">
																								<tbody>
																									<tr>
																										<td>
																											<p>
																												<center>
																													<b>You do not have access to view the information on this tab</b>
																												</center>
																											</p>
																										</td>
																									</tr>
																								</tbody>
																							</table>
																						</xsl:when>
																						<xsl:otherwise>
																							<xsl:apply-templates/>
																						</xsl:otherwise>
																					</xsl:choose>
																				</div>
																			</xsl:if>
																		</xsl:if>
																	</xsl:for-each>
																</form>
															</xsl:otherwise>
														</xsl:choose>
													</td>
												</tr>
												<!--		the bottom tab bar		-->
												<xsl:if test="count($unique-tabs) > 1">
													<xsl:call-template name="create-tabs">
														<xsl:with-param name="location">bottom</xsl:with-param>
														<xsl:with-param name="currentTab">
															<xsl:value-of select="$currentTab"/>
														</xsl:with-param>
														<xsl:with-param name="tabs" select="$unique-tabs"/>
													</xsl:call-template>
												</xsl:if>
											</tbody>
											<tfoot>
												<tr>
													<td>
														<img src="transparent.gif" width="0" height="3" border="0" alt=""/>
													</td>
												</tr>
												<tr>
													<td>
														<!--xsl:for-each select="content/button-bar"-->
														<div>
															<xsl:attribute name="id">buttonbarbottom<xsl:value-of select="@name"/></xsl:attribute>
															<!--xsl:choose>
																	<xsl:when test="position()=$currentTab or $currentTab='' or number(count($unique-tabs))&lt;number($currentTab) ">
																		<xsl:attribute name="class">visible</xsl:attribute>
																		<xsl:attribute name="selected">true</xsl:attribute>
																	
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:attribute name="class">none</xsl:attribute>
																	</xsl:otherwise>
																</xsl:choose-->
															<!-- 		do the payload		-->
															<xsl:call-template name="button-bar">
																<xsl:with-param name="location">bottom</xsl:with-param>
																<xsl:with-param name="currentTab">
																	<xsl:value-of select="$currentTab"/>
																</xsl:with-param>
															</xsl:call-template>
														</div>
														<!--/xsl:for-each-->
													</td>
												</tr>
											</tfoot>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<SCRIPT Type="text/javascript" Language="JavaScript">GiveFirstInputFocus();blockEnterKey();listenersToPreventBackButton();</SCRIPT>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="HTML">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="BODY">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="page-title">
		<xsl:value-of select="$page-title"/>
	</xsl:template>
	<xsl:template match="@* | *[ (not((ancestor-or-self::tr|ancestor-or-self::td)/@tie-to) ) or ( (ancestor-or-self::tr|ancestor-or-self::td)/@tie-to and contains((ancestor-or-self::tr|ancestor-or-self::td)/@tie-to,normalize-space(substring-before($page-title,' '))))] | processing-instruction()" priority="-1">
		<xsl:copy>
			<xsl:apply-templates select="@* | *[ (not((ancestor-or-self::tr|ancestor-or-self::td)/@tie-to) ) or ( (ancestor-or-self::tr|ancestor-or-self::td)/@tie-to and contains((ancestor-or-self::tr|ancestor-or-self::td)/@tie-to,normalize-space(substring-before($page-title,' '))))] | text() | processing-instruction()"/>
		</xsl:copy>
	</xsl:template>
	<!--
			template for creating the tabs 
	-->
	<xsl:template name="create-tabs">
		<xsl:param name="location"/>
		<xsl:param name="currentTab"/>
		<xsl:param name="tabs"/>
		<tr>
			<td align="left">
				<table cellpadding="0" cellspacing="0" border="0">
					<!-- need to change width based on popup or not, determined if the navbar and topbar are available-->
					<xsl:choose>
						<xsl:when test="navbar and top_bar">
							<xsl:attribute name="width">750</xsl:attribute>
						</xsl:when>
						<xsl:otherwise><xsl:attribute name="width">624</xsl:attribute></xsl:otherwise>
					</xsl:choose>

					<xsl:if test="$location='bottom'">
						<tr>
							<td bgcolor="#003470" colspan="100%">
								<img src="transparent.gif" width="100%" height="5" border="0" alt=""/>
							</td>
						</tr>
					</xsl:if>
					<tr>
						<td bgcolor="white">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<xsl:for-each select="$tabs">
										
										<xsl:if test="not(string-length(normalize-space(@name))=0)">
											<xsl:if test="not(normalize-space(@view)='false')">
												<xsl:choose>
												
													<!-- TEST TO SEE WHETHER OR NOT WE ARE PROCESSING THE CURRENT TAB AND SHOW CURRENT TAB GRAPHICS ACCORDINGLY-->
													<xsl:when test="position()=$currentTab or $currentTab='' or number(count($tabs))&lt;number($currentTab) ">
														
															<td width="16" height="22" style="cursor:hand">
															<a href="#" style="text-decoration: none">
															<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
															<xsl:attribute name="tabindex">-1</xsl:attribute>
																<img width="16" height="22" border="0" alt="">
																	<xsl:attribute name="id">tabImgLeft<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
																	<xsl:choose>
																		<xsl:when test="$location='top'">
																			<xsl:attribute name="src">corner_left.gif</xsl:attribute>
																		</xsl:when>
																		<xsl:when test="$location='bottom'">
																			<xsl:attribute name="src">corner_left_inverted.gif</xsl:attribute>
																		</xsl:when>
																	</xsl:choose>
																</img>
																</a>
															</td>
															<td bgcolor="#003470" style="cursor:hand" height="22" valign="middle">
															<xsl:attribute name="id">tabTdBgcolor<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
															<a href="#" style="text-decoration: none">
															<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
															<xsl:attribute name="tabindex">-1</xsl:attribute>
																<nobr>
																	<font class="boldNineYellow">
																		<xsl:value-of select="@name"/>
																	</font>
																</nobr>
																</a>
															</td>
															<td width="16" height="22" style="cursor:hand">
															<a href="#" style="text-decoration: none">
															<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
															<xsl:attribute name="tabindex">-1</xsl:attribute>
																<img width="16" height="22" border="0" alt="">
																	<xsl:attribute name="id">tabImgRight<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
																	<xsl:choose>
																		<xsl:when test="$location='top'">
																			<xsl:attribute name="src">corner_right.gif</xsl:attribute>
																		</xsl:when>
																		<xsl:when test="$location='bottom'">
																			<xsl:attribute name="src">corner_right_inverted.gif</xsl:attribute>
																		</xsl:when>
																	</xsl:choose>
																</img>
																</a>
															</td>
														
													</xsl:when>
													
													
													<xsl:otherwise>
													
															<td width="16" style="cursor:hand">
																<a href="#" style="text-decoration: none">
																<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
																<xsl:attribute name="tabindex">-1</xsl:attribute>
																<img width="16" height="22" border="0" alt="">
																	<xsl:attribute name="id">tabImgLeft<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
																	<xsl:choose>
																		<xsl:when test="$location='top'">
																			<xsl:attribute name="src">corner_left_lb.gif</xsl:attribute>
																		</xsl:when>
																		<xsl:when test="$location='bottom'">
																			<xsl:attribute name="src">corner_left_lb_inverted.gif</xsl:attribute>
																		</xsl:when>
																	</xsl:choose>
																</img>
																</a>
															</td>
															<td bgcolor="#3A6295" style="cursor:hand" height="22" valign="middle">
																<xsl:attribute name="id">tabTdBgcolor<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
																<a href="#" style="text-decoration: none">
																<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
																<xsl:attribute name="tabindex">-1</xsl:attribute>
																<nobr>
																	<font class="boldNineYellow">
																		<xsl:choose>
																			<xsl:when test="@name='Strep pneumoniae, invasive'">
																				<i>Strep pneumoniae, </i>invasive
																				</xsl:when>
																			<xsl:when test="@name='Neisseria meningitidis, invasive (Mening. disease)'">
																				<i>Neisseria meningitidis, </i>  invasive (Mening. disease)																													
																			</xsl:when>
																			<xsl:when test="@name='Haemophilus influenzae, invasive'">
																				<i>Haemophilus influenzae, </i>  invasive																														
																			</xsl:when>
																			<xsl:otherwise>
																				<xsl:value-of select="@name"/>
																			</xsl:otherwise>
																		</xsl:choose>
																	</font>
																	
																</nobr>
																</a>
															</td>
															<td width="16" style="cursor:hand">
																
														
															<a href="#" style="text-decoration: none">
															<xsl:attribute name="onclick">Toggle('<xsl:value-of select="generate-id()"/>')</xsl:attribute>
															<xsl:attribute name="tabindex">-1</xsl:attribute>
																<img width="16" height="22" border="0" alt="">
																	<xsl:attribute name="id">tabImgRight<xsl:value-of select="$location"/><xsl:value-of select="generate-id()"/></xsl:attribute>
																	<xsl:choose>
																		<xsl:when test="$location='top'">
																			<xsl:attribute name="src">corner_right_lb.gif</xsl:attribute>
																		</xsl:when>
																		<xsl:when test="$location='bottom'">
																			<xsl:attribute name="src">corner_right_lb_inverted.gif</xsl:attribute>
																		</xsl:when>
																	</xsl:choose>
																</img>
																</a>
															</td>
														
													</xsl:otherwise>
												</xsl:choose>
											</xsl:if>
										</xsl:if>
									</xsl:for-each>
								</tr>
							</table>
						</td>
					</tr>
					<xsl:if test="$location='top'">
						<tr>
							<td bgcolor="#003470" colspan="100%">
								<img src="transparent.gif" width="100%" height="5" border="0" alt=""/>
							</td>
						</tr>
					</xsl:if>
				</table>
			</td>
		</tr>
	</xsl:template>
	<!--
		prepares the button bar , takes in a parameter to tell it whether it is the top button bar or the bottom button bar
	-->
	<xsl:template name="button-bar">
		<xsl:param name="location"/>
		<xsl:param name="currentTab"/>
		<xsl:if test="content/button-bar">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table border="0" cellspacing="0" cellpadding="0">
							<!-- need to change width based on popup or not, determined if the navbar and topbar are available-->
							<xsl:choose>
								<xsl:when test="navbar and top_bar">
									<xsl:attribute name="width">750</xsl:attribute>
								</xsl:when>
								<xsl:otherwise><xsl:attribute name="width">624</xsl:attribute></xsl:otherwise>
							</xsl:choose>

							<tr align="left">
								<td valign="top">
									<xsl:choose>
										<xsl:when test="$location='top'">
											<img src="task_button/left_corner.jpg" width="25" height="54" border="0" alt=""/>
										</xsl:when>
										<xsl:when test="$location='bottom'">
											<img src="task_button/left_bottom_corner.jpg" width="25" height="54" border="0" alt=""/>
										</xsl:when>
									</xsl:choose>
								</td>
								<td width="500" align="left" valign="top" background="task_button/tb_cel_bak.jpg">
									<xsl:for-each select="content/button-bar/left">
										<xsl:choose>
											<xsl:when test="@tie-to and not(contains(@tie-to,normalize-space(substring-before($page-title,' '))))">
											              
											</xsl:when>
											<xsl:when test="@authorized='false' and not(contains(./javascript-action, 'NOT_DISPLAYED'))">
												<!--		don't create the button		-->
											</xsl:when>
											<xsl:otherwise>
												<table width="80" border="0" cellspacing="0" cellpadding="0" align="left">
													<tr>
														<td align="center" valign="top">
															<xsl:element name="input">
																<xsl:attribute name="type">image</xsl:attribute>
																<xsl:attribute name="src">task_button/fa_submit.jpg</xsl:attribute>
																<xsl:attribute name="width">30</xsl:attribute>
																<xsl:attribute name="height">40</xsl:attribute>
																<xsl:attribute name="border">0</xsl:attribute>
																<xsl:attribute name="name"><xsl:value-of select="normalize-space(./label)"/></xsl:attribute>
																<xsl:attribute name="id"><xsl:value-of select="normalize-space(./label)"/></xsl:attribute>
																<xsl:attribute name="alt"><xsl:value-of select="normalize-space(./label)"/> button</xsl:attribute>
																<xsl:attribute name="class">cursorHand</xsl:attribute>
																<xsl:attribute name="onclick"><xsl:value-of select="./javascript-action"/></xsl:attribute>
															</xsl:element>
														</td>
													</tr>
													<tr>
														<!--<td valign="top" class="boldEightBlack">&nbsp;&nbsp;<xsl:value-of select="./label"/>&nbsp;&nbsp;</td>-->
														<td valign="top" align="center" class="boldEightBlack">&nbsp;&nbsp;<xsl:value-of select="./label" disable-output-escaping="yes"/>
														</td>
													</tr>
												</table>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
								</td>
								<td width="3" valign="top">
									<img src="task_button/strip_spacer.jpg" width="3" height="54" border="0" alt=""/>
								</td>
								<td width="270" align="left" valign="top" background="task_button/tb_cel_bak.jpg">
									<xsl:for-each select="content/button-bar/right">
										<xsl:choose>
											<xsl:when test="@tie-to and not(contains(@tie-to,normalize-space(substring-before($page-title,' '))))">
											              
											</xsl:when>
											<xsl:when test="@authorized='false' and not(contains(./javascript-action, 'NOT_DISPLAYED'))">
												<!--		don't create the button		-->
											</xsl:when>
											<xsl:otherwise>
												<table width="30" border="0" cellspacing="0" cellpadding="0" align="left">
													<xsl:variable name="current" select="ancestor::content/tab"/>
													<xsl:if test="@anchor">
														<xsl:attribute name="anchor"><xsl:value-of select="generate-id(ancestor::content/tab)"/></xsl:attribute>
														<xsl:for-each select="ancestor::page/content/tab">
															<xsl:choose>
																<xsl:when test="normalize-space($current/@name) = normalize-space(./@name) and not(position()=$currentTab)">
																	<xsl:attribute name="class">none</xsl:attribute>
																</xsl:when>
																<xsl:otherwise/>
															</xsl:choose>
														</xsl:for-each>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="count(.) &lt; 3"/>
														<xsl:otherwise>
															<xsl:attribute name="width">30</xsl:attribute>
														</xsl:otherwise>
													</xsl:choose>
													<tr>
														<td align="center" valign="top">
															<xsl:element name="input">
																<xsl:attribute name="type">image</xsl:attribute>
																<xsl:attribute name="src">task_button/fa_submit.jpg</xsl:attribute>
																<xsl:attribute name="width">30</xsl:attribute>
																<xsl:attribute name="height">40</xsl:attribute>
																<xsl:attribute name="border">0</xsl:attribute>
																<xsl:attribute name="name"><xsl:value-of select="normalize-space(./label)"/></xsl:attribute>
																<xsl:attribute name="id"><xsl:value-of select="normalize-space(./label)"/></xsl:attribute>
																<xsl:attribute name="alt"><xsl:value-of select="normalize-space(./label)"/> button</xsl:attribute>
																<xsl:attribute name="class">cursorHand</xsl:attribute>
																<xsl:attribute name="onclick"><xsl:value-of select="./javascript-action"/></xsl:attribute>
															</xsl:element>
														</td>
													</tr>
													<tr>
														<!--<td valign="top" class="boldEightBlack">&nbsp;&nbsp;<xsl:value-of select="./label"/>&nbsp;&nbsp;</td>-->
														<td valign="top" align="center" class="boldEightBlack">&nbsp;<xsl:value-of select="./label" disable-output-escaping="yes"/>&nbsp;</td>
													</tr>
												</table>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</xsl:if>
	</xsl:template>
	<!-- VL  template for separator line -->
	<xsl:template name="create-separator">
		<tr>
			<td align="left">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<thead>
						<tr bgcolor="#003470">
							<td align="left" colspan="100%">
								<font class="boldTwelveYellow">
									<b>
						&nbsp;<xsl:value-of select="@name"/>&nbsp;
					</b>
								</font>
							</td>
						</tr>
					</thead>
				</table>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
