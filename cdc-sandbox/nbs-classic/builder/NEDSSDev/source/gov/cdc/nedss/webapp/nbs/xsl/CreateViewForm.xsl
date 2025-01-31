<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!--	<xsl:preserve-space elements="*"/>-->
	<!--xsl:strip-space elements="*|@*"/-->
	<xsl:template match="content">
		<content>
			<xsl:if test="./@title">
				<xsl:attribute name="title"><xsl:value-of select="./@title"/></xsl:attribute>
			</xsl:if>
			<xsl:apply-templates/>
		</content>
	</xsl:template>
	<xsl:template match="id-bar">
		<id-bar>
			<xsl:for-each select="id">
				<xsl:element name="id">
					<xsl:attribute name="name"><xsl:value-of select="./@name"/></xsl:attribute>
					<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
					<xsl:attribute name="show"><xsl:value-of select="./@show"/></xsl:attribute>
					<xsl:value-of select="normalize-space(.)"/>
				</xsl:element>
			</xsl:for-each>
		</id-bar>
	</xsl:template>
	<xsl:template match="link-bar">
		<link-bar>
			<xsl:choose>
				<xsl:when test="@authorized = 'false'"/>
				<xsl:otherwise>
					<xsl:for-each select="link">
						<xsl:element name="link">
							<xsl:attribute name="name"><xsl:value-of select="./@name"/></xsl:attribute>
							<xsl:if test="@tie-to">
								<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
							</xsl:if>
							<xsl:value-of select="normalize-space(.)"/>
						</xsl:element>
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
		</link-bar>
	</xsl:template>
	<xsl:template match="button-bar">
		<button-bar>
			<xsl:attribute name="name"><xsl:value-of select="normalize-space(../tab/@name)"/></xsl:attribute>
			<xsl:for-each select="right|left">
				<xsl:choose>
					<xsl:when test="contains(javascript-action, 'NOT_DISPLAYED')"/>
					<xsl:when test="contains(javascript-action, 'null')"/>
					<xsl:when test="name()='right' ">
						<xsl:element name="right">
							<xsl:if test="@anchor">
								<xsl:attribute name="anchor"><xsl:value-of select="@anchor"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="@tie-to">
								<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
							</xsl:if>
							<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
							<xsl:element name="label">
								<xsl:value-of select="./label"/>
							</xsl:element>
							<xsl:choose>
								<xsl:when test="contains(javascript-action, 'NO_ACTION')">
									<xsl:element name="javascript-action">NoOp();</xsl:element>
								</xsl:when>
								<xsl:otherwise>
									<xsl:element name="javascript-action">
										<xsl:value-of select="./javascript-action"/>
									</xsl:element>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:when>
					<xsl:when test="name()='left' ">
						<xsl:element name="left">
							<xsl:if test="@tie-to">
								<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
							</xsl:if>
							<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
							<xsl:element name="label">
								<xsl:value-of select="./label"/>
							</xsl:element>
							<xsl:choose>
								<xsl:when test="contains(javascript-action, 'NO_ACTION')">
									<xsl:element name="javascript-action">NoOp();</xsl:element>
								</xsl:when>
								<xsl:otherwise>
									<xsl:element name="javascript-action">
										<xsl:value-of select="./javascript-action"/>
									</xsl:element>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:for-each>
			<!--xsl:for-each select="left">
				<xsl:if test="not(contains(javascript-action, 'NOT_DISPLAYED')) and not(contains(javascript-action, 'null'))">
					<xsl:element name="left">
						<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
						<xsl:element name="label">
							<xsl:value-of select="./label"/>
						</xsl:element>
						<xsl:element name="javascript-action">
							<xsl:value-of select="./javascript-action"/>
						</xsl:element>
					</xsl:element>
				</xsl:if>

			</xsl:for-each-->
		</button-bar>
	</xsl:template>
	<xsl:template match="javascript-files">
		<javascript-files>
			<xsl:for-each select="import">
				<xsl:element name="import">
					<xsl:value-of select="normalize-space(.)"/>
				</xsl:element>
			</xsl:for-each>
		</javascript-files>
	</xsl:template>
	<!-- VL  This is needed for data to propagate to aggregate.xsl file -->
	<xsl:template match="separator">
		<xsl:copy-of select="."/>
	</xsl:template>
	<!-- VL for display topgroup above links and under buttons' bar  -->
	<xsl:template match="topgroup">
		<xsl:element name="topgroup">
			<xsl:if test="@anchor">
				<xsl:attribute name="anchor"><xsl:value-of select="@anchor"/></xsl:attribute>
			</xsl:if>
			<xsl:call-template name="create-lines">
				<xsl:with-param name="disabled">false</xsl:with-param>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template match="tab">
		<xsl:element name="tab">
			<xsl:attribute name="name"><xsl:value-of select="normalize-space(./@name)"/></xsl:attribute>
			<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
			<xsl:attribute name="view"><xsl:value-of select="normalize-space(./@view)"/></xsl:attribute>
			<xsl:variable name="jumper">
				<xsl:if test="count(group/@name) > 1 and not(@jumpers='off')">true</xsl:if>
			</xsl:variable>
			<!--		initialize gXSLType global javascript variable 		-->
			<SCRIPT Type="text/javascript" Language="JavaScript">gXSLType='view';</SCRIPT>
			<xsl:for-each select="script">
				<script type="text/javascript">
					<xsl:value-of select="text()" disable-output-escaping="yes"/>
				</script>
			</xsl:for-each>
			<xsl:if test="normalize-space($jumper)='true'">
				<table id="jumper-table" border="0" width="100%">
					<thead align="left">
						<tr align="left">
							<td align="left" width="100%">
								<xsl:for-each select="./group/@name">
									<xsl:choose>
										<xsl:when test="@authorized='false'"/>
										<xsl:when test="string-length(.)=0"/>
										<xsl:otherwise>
											<a href="#{generate-id(..)}">
												<!--xsl:attribute name="href">#<xsl:value-of select="."/></xsl:attribute-->
												<xsl:value-of select="."/>
											</a>
											<xsl:if test="position() != last()">&nbsp;|&nbsp;</xsl:if>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</td>
						</tr>
						<xsl:if test="./group/@warnMessage">
							<tr align="left">
								<td align="left" width="100%">
									<span class="boldTenRed">
										<br/>
										<xsl:value-of select="normalize-space(./group/@warnMessage)"/>
									</span>
								</td>
							</tr>
						</xsl:if>
					</thead>
				</table>
			</xsl:if>
			<!-- 		the content table that contains all the form elements	-->
			<table cellpadding="1" cellspacing="0" border="0" bgcolor="white" width="100%">
				<tbody>
					<xsl:for-each select="./group">
						<xsl:choose>
							<xsl:when test="@authorized='false'"/>
							<xsl:otherwise>
								<tr>
									<xsl:if test="@tie-to">
										<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="normalize-space(@tie-to-null)='null' or (@tie-to-null and normalize-space(@tie-to-null)='') ">
										<xsl:attribute name="tie-to">null</xsl:attribute>
									</xsl:if>
									<td colspan="10">
										<table cellpadding="3" cellspacing="0" border="0" width="100%">
											<xsl:choose>
												<xsl:when test="@name">
													<tr bgcolor="#003470">
														<td align="left" valign="top">
															<a name="{generate-id(.)}">
																<!--xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute-->
																<font class="boldTwelveYellow">
																	&nbsp;<xsl:value-of select="@name"/>
																</font>
															</a>
														</td>
														<xsl:choose>
															<xsl:when test="normalize-space($jumper)='true'">
																<td align="right">
																	<a href="#top" class="boldTenWhite">Back to Top</a>&nbsp;</td>
															</xsl:when>
															<xsl:otherwise>
																<td>&nbsp;</td>
															</xsl:otherwise>
														</xsl:choose>
													</tr>
													<!--		PUT BUTTONS IF THEY EXISTS	-->
													<tr>
														<td colspan="2" align="right">
															<!--		put buttons here if they exist	-->
															<xsl:choose>
																<xsl:when test="button-bar/button/@authorized='false' and not(contains(button-bar/button/javascript-action, 'NOT_DISPLAYED'))"/>
																<xsl:when test="button-bar">
																	<input type="button" name="" style="padding-left:8;padding-right:8">
																		<xsl:attribute name="value"><xsl:value-of select="button-bar/button/label"/></xsl:attribute>
																		<xsl:attribute name="onclick"><xsl:value-of select="normalize-space(button-bar/button/javascript-action)"/></xsl:attribute>
																	</input>
																</xsl:when>
															</xsl:choose>
														</td>
													</tr>
												</xsl:when>
												<xsl:when test="@background">
													<xsl:choose>
														<xsl:when test="@background='blue'">
															<xsl:attribute name="class">LightBlue</xsl:attribute>
														</xsl:when>
														<xsl:when test="@background='gray'">
															<xsl:attribute name="class">Shaded</xsl:attribute>
														</xsl:when>
													</xsl:choose>
												</xsl:when>
												<xsl:when test="@abc">
													<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>
													<xsl:attribute name="type">abc</xsl:attribute>
													<xsl:choose>
														<xsl:when test="normalize-space(@abc)='T' ">
															<xsl:attribute name="class">visible</xsl:attribute>
														</xsl:when>
														<xsl:otherwise>
															<xsl:attribute name="class">none</xsl:attribute>
														</xsl:otherwise>
													</xsl:choose>
													<tr class="LightBlue">
														<td class="boldElevenBlack">
															<center>
																<xsl:value-of select="@label"/>
															</center>
														</td>
													</tr>
												</xsl:when>
												<xsl:when test="@blue-label">
													<tr class="LightBlue">
														<td class="boldElevenBlack">
															<center>
																<xsl:value-of select="@blue-label"/>
															</center>
														</td>
													</tr>
												</xsl:when>
												<xsl:otherwise>
													
												</xsl:otherwise>
											</xsl:choose>
											<tr>
												<td colspan="10">
													<table cellpadding="2" cellspacing="0" border="0" width="100%">
														<xsl:call-template name="create-lines">
															<xsl:with-param name="disabled">false</xsl:with-param>
														</xsl:call-template>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</tbody>
			</table>
		</xsl:element>
	</xsl:template>
	<!-- =================================================================== -->
	<!-- 								Creates the lines		                                  -->
	<!-- =================================================================== -->
	<xsl:template name="create-lines">
		<xsl:param name="disabled"/>
		<xsl:param name="onchange"/>
		<xsl:param name="parentId"/>
		<xsl:param name="trigger"/>
		<xsl:param name="condition"/>
		<xsl:for-each select="./line">
			<xsl:choose>
				<xsl:when test=" ./element/@type[. = 'conditional-entry']">
					<xsl:variable name="trigger" select="@trigger"/>
					<xsl:for-each select="element">
						<xsl:call-template name="create-element">
							<xsl:with-param name="trigger" select="$trigger"/>
							<xsl:with-param name="line-trigger" select="@trigger"/>
							<xsl:with-param name="condition" select="$condition"/>
							<xsl:with-param name="abc" select="@abc"/>
							<xsl:with-param name="parentId" select="$parentId"/>
							<!--xsl:with-param name="Id" select="../@id"/-->
							<xsl:with-param name="lineId" select="../@id"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="@id[. = 'csfhidden']">
					<tr rowID="csfhiddenrow" class="none">
						<xsl:call-template name="setup-cell">
							<xsl:with-param name="disabled" select="$disabled"/>
							<xsl:with-param name="onchange" select="$onchange"/>
							<xsl:with-param name="parentId" select="$parentId"/>
						</xsl:call-template>
					</tr>
				</xsl:when>
				<xsl:when test="@type[. = 'sp-line']">
					<tr>
						<td>
							<table>
								<xsl:call-template name="setup-cell">
									<xsl:with-param name="disabled" select="$disabled"/>
									<xsl:with-param name="onchange" select="$onchange"/>
									<xsl:with-param name="parentId" select="$parentId"/>
								</xsl:call-template>
							</table>
						</td>
					</tr>
				</xsl:when>
				<xsl:otherwise>
					<tr>
						<xsl:if test="@id">
							<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@tie-to">
							<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="normalize-space(@tie-to-null)='null' or (@tie-to-null and normalize-space(@tie-to-null)='') ">
							<xsl:attribute name="tie-to">null</xsl:attribute>
						</xsl:if>
						<xsl:if test="$condition and @trigger">
							<xsl:variable name="test">
								<xsl:value-of select="@trigger"/>|
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="contains(normalize-space($condition),'|' ) and contains(normalize-space($condition), normalize-space($test))">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:when test=" contains(normalize-space(@trigger),'|' ) and contains(normalize-space(@trigger), normalize-space($condition)  ) and not(string-length(normalize-space($condition))=0)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:when test="normalize-space($condition)=normalize-space(@trigger)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<!--for abcs questions-->
						<xsl:if test="@abc">
							<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>
							<xsl:choose>
								<xsl:when test="normalize-space(@abc)='T' ">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<!--	need this for conditional entry based on showing individual lines based on controller value	-->
						<xsl:if test="@trigger">
							<xsl:attribute name="trigger"><xsl:value-of select="@trigger"/></xsl:attribute>
						</xsl:if>
						<!-- VL: rowID for access from JavaScript -->
						<xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute>
						<xsl:choose>
							<xsl:when test="./free or ./element/@type[. = 'line-separator'] or ./element/@type[. = 'entity-search'] or ./element/@type[. = 'group-separator'] or  ./element/@type[. = 'batch-entry'] or ./element/@type[. = 'join'] or (./element/@type[. = 'raw'] and count(./element) = 1)">
								<td colspan="10" align="center">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<xsl:if test="./element/@type[. = 'raw'] or ./element/@type[. = 'line-separator']">
											<xsl:attribute name="width">100%</xsl:attribute>
										</xsl:if>
										<tr rowID="1" valign="center">
											<xsl:call-template name="setup-cell">
												<xsl:with-param name="disabled" select="$disabled"/>
												<xsl:with-param name="onchange" select="$onchange"/>
												<xsl:with-param name="parentId" select="$parentId"/>
											</xsl:call-template>
										</tr>
										<tr>
											<td class="none" colspan="10" rowID="errorCell1">cell1</td>
										</tr>
									</table>
								</td>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="setup-cell">
									<xsl:with-param name="disabled" select="$disabled"/>
									<xsl:with-param name="onchange" select="$onchange"/>
									<xsl:with-param name="parentId" select="$parentId"/>
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</tr>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!-- =================================================================== -->
	<!--							prepare the cell with label information		                                  -->
	<!-- =================================================================== -->
	<xsl:template name="setup-cell">
		<xsl:param name="disabled"/>
		<xsl:param name="onchange"/>
		<xsl:param name="onclick"/>
		<xsl:param name="parentId"/>
		<xsl:param name="checked"/>
		<xsl:for-each select="./element">
			<xsl:if test="@name='dateAssignedToInvestigation' and string-length(normalize-space(preceding::element[@name='investigator.personUid']/value))=0">
				<xsl:attribute name="class">none</xsl:attribute>
			</xsl:if>
			<!-- do authorization check	-->
			<xsl:choose>
				<xsl:when test="@authorized='false'  and not(@type='data-table')"/>
				<xsl:otherwise>
					<!--	template for creating the label	-->
					<xsl:call-template name="create-the-label"/>
					<!--		cell for the content		-->
					<td align="left" valign="top">
						<xsl:if test="position() = last() and not(@type='join')  and not(ancestor::group/@colspan='off')">
							<xsl:attribute name="colspan">5</xsl:attribute>
						</xsl:if>
						<!--xsl:if test="@type[. = 'text'] or @type[. = 'select'] or @type[. = 'password'] or @type[. = 'radio'] or @type[. = 'checkbox'] or @type[. = 'telephone']">
							<xsl:attribute name="width">20%</xsl:attribute>
						</xsl:if-->
						<xsl:if test="@type[. = 'raw']">
							<xsl:attribute name="width">100%</xsl:attribute>
						</xsl:if>
						<xsl:if test="@type[. = 'line-separator']">
							<xsl:attribute name="width">100%</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@type)">
							<xsl:attribute name="width">5</xsl:attribute>
						</xsl:if>
						<!-- VL: we can't have <nobr/> on "raw" elements. -->
						<xsl:choose>
							<xsl:when test="@type[ .= 'raw' ]">
								<xsl:call-template name="create-element">
									<xsl:with-param name="disabled" select="$disabled"/>
									<xsl:with-param name="onchange" select="$onchange"/>
									<xsl:with-param name="onclick" select="$onclick"/>
									<xsl:with-param name="parentId" select="$parentId"/>
									<xsl:with-param name="checked" select="$checked"/>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="not(@type[ .= 'textarea' ]) and not(@type='select' and @size) and string-length(./value)&lt;20 ">
										<xsl:call-template name="create-element">
											<xsl:with-param name="disabled" select="$disabled"/>
											<xsl:with-param name="onchange" select="$onchange"/>
											<xsl:with-param name="onclick" select="$onclick"/>
											<xsl:with-param name="parentId" select="$parentId"/>
											<xsl:with-param name="checked" select="$checked"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="create-element">
											<xsl:with-param name="disabled" select="$disabled"/>
											<xsl:with-param name="onchange" select="$onchange"/>
											<xsl:with-param name="onclick" select="$onclick"/>
											<xsl:with-param name="parentId" select="$parentId"/>
											<xsl:with-param name="checked" select="$checked"/>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!-- =================================================================== -->
	<!--							create the element		                                  -->
	<!-- =================================================================== -->
	<xsl:template name="create-element">
		<xsl:param name="disabled"/>
		<xsl:param name="onchange"/>
		<xsl:param name="onclick"/>
		<xsl:param name="parentId"/>
		<xsl:param name="checked"/>
		<xsl:param name="trigger"/>
		<xsl:param name="line-trigger"/>
		<xsl:param name="abc"/>
		<xsl:param name="partner"/>
		<xsl:param name="condition"/>
		<xsl:param name="lineId"/>
		<xsl:param name="conditionalId"/>
		<!--xsl:param name="id"/-->
		<xsl:param name="name"/>
		<xsl:param name="value"/>
		<xsl:variable name="id">
			<xsl:choose>
				<xsl:when test="@id">
					<xsl:value-of select="normalize-space(@id)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@name"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<!-- =================================================================== -->
			<!-- 			creates horizonal line <hr/> separator with any text on top of it.                    -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'hyper-link']">
				<nobr>
					<xsl:choose>
						<xsl:when test="href">
							<a>
								<xsl:attribute name="href"><xsl:value-of select="normalize-space(href)"/></xsl:attribute>
								<xsl:choose>
									<xsl:when test="defined-field-link">
										<xsl:attribute name="target">_blank</xsl:attribute>
										<font size="4" style="Geneva, Arial, Helvetica, sans-serif" color="#003470">
											<xsl:value-of select="text"/>
										</font>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="text"/>
									</xsl:otherwise>
								</xsl:choose>
							</a>
						</xsl:when>
						<xsl:otherwise>
							<b>
								<xsl:value-of select="text"/>
							</b>
						</xsl:otherwise>
					</xsl:choose>
				</nobr>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			put out raw HTML                         -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'raw']">
				<xsl:if test="cellFormat/align">
					<xsl:attribute name="align"><xsl:value-of select="normalize-space(cellFormat/align)"/></xsl:attribute>
				</xsl:if>
				<xsl:if test="cellFormat/bgcolor">
					<xsl:attribute name="bgcolor"><xsl:value-of select="normalize-space(cellFormat/bgcolor)"/></xsl:attribute>
				</xsl:if>
				<xsl:attribute name="align"><xsl:value-of select="normalize-space(@align)"/></xsl:attribute>
				<xsl:copy-of select="span"/>
			</xsl:when>
			<!--================================================================================-->
			<!--	formatting type , places a blue bar on top and indents everything beneath					-->
			<!--=================================================================================-->
			<xsl:when test="@type[. = 'blue-bar']">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td colspan="100%" class="LightBlue" align="left">
							<b>
								<xsl:value-of select="@text" disable-output-escaping="yes"/>
							</b>
						</td>
					</tr>
				</table>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates horizonal line <hr/> separator with any text on top of it.                    -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'line-separator']">
					<xsl:if test="@title">
						<span class="boldTwelveDkBlue">
							<xsl:value-of select="normalize-space(@title)"/>
						</span>
						<br/>
					</xsl:if>
					<img src="transparent.gif" width="500" height="2" border="0" alt="" class="DarkGray"/>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates horizonal line <hr/> separator with any text on top of it.                 -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'group-separator']">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<xsl:choose>
						<xsl:when test="@light-blue-label">
							<tr class="LightBlue">
								<td class="boldElevenBlack">
									<center>
										<xsl:choose>
											<xsl:when test="string-length(normalize-space(@makeItalic))!=0">
												<xsl:value-of select="substring-before(@light-blue-label,@makeItalic)"/>
												<i>
													<xsl:value-of select="@makeItalic"/>
												</i>
												<xsl:value-of select="substring-after(@light-blue-label,@makeItalic)"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="@light-blue-label"/>
											</xsl:otherwise>
										</xsl:choose>
									</center>
								</td>
							</tr>
						</xsl:when>
						<xsl:otherwise>
							<tr>
								<td bgcolor="#003470">
									<nobr>
										<xsl:if test="@title">
											<font class="boldTwelveYellow">
												&nbsp;<xsl:value-of select="normalize-space(@title)"/>
											</font>
										</xsl:if>
									</nobr>
								</td>
							</tr>
						</xsl:otherwise>
					</xsl:choose>
				</table>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates regular input text field form element                               -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'text']">
				<!--		creates notation for elements		-->
				<xsl:element name="span">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--		create attributes for nodes that require validation	-->
					<xsl:choose>
						<xsl:when test="./validation and not(./validation/@mask)">
							<xsl:choose>
								<xsl:when test="normalize-space(value)!='null' and @keep-decimal">
									<xsl:value-of select="normalize-space(value)"/>
								</xsl:when>
								<xsl:when test="string(number(value))!='NaN' and contains(value, '.0')">
									<xsl:value-of select="substring-before(value,'.0')"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="value!='null'">
										<xsl:value-of select="value" disable-output-escaping="yes"/>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:choose>
								<xsl:when test="@default and string-length(value)=0">
									<xsl:value-of select="@default"/>
								</xsl:when>
								<xsl:when test="normalize-space(value)!='null' and @keep-decimal">
									<xsl:value-of select="normalize-space(value)"/>
								</xsl:when>
								<xsl:when test="contains(value,'.0' )">
									<xsl:value-of select="translate(value,'.0','')"/>
								</xsl:when>
								<xsl:when test="value!='null' ">
									<xsl:value-of select="value"/>
								</xsl:when>
								<xsl:otherwise/>
							</xsl:choose>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates regular input text field form element                               -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'telephone']">
				<!--		creates notation for elements		-->
				<xsl:element name="span">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--		create attributes for nodes that require validation	-->
					<xsl:if test="value!='null'  and string-length(value) > 0">
						<xsl:value-of select="value"/>
					</xsl:if>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates SSN element                             -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'ssn']">
				<!--		creates notation for elements		-->
				<xsl:element name="span">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--		create attributes for nodes that require validation	-->
					<xsl:if test="normalize-space(value)!='null'  and string-length(value) > 0">
						<xsl:value-of select="value"/>
					</xsl:if>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates HH:MM element                           -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'hh:mm']">
				<!--		creates notation for elements		-->
				<xsl:element name="span">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--		create attributes for nodes that require validation	-->
					<xsl:if test="normalize-space(value)!='null'  and string-length(value) > 0">
						<xsl:value-of select="value"/>
					</xsl:if>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 		  just a label and text, just to display some values                            -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'quick-entry-text']">
				<span>
					<xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
					<xsl:value-of select="value" disable-output-escaping="yes"/>
					<xsl:text disable-output-escaping="yes"/>
				</span>
				<input>
					<xsl:attribute name="type"><xsl:value-of select="'hidden'"/></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="concat('hidden-',@name)"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="value" disable-output-escaping="yes"/></xsl:attribute>
					<xsl:attribute name="onload">this.value='yes';</xsl:attribute>
					<xsl:if test="@onload">
						<SCRIPT Type="text/javascript" Language="JavaScript">
							<xsl:value-of select="normalize-space(@onload)"/>
						</SCRIPT>
					</xsl:if>
				</input>
			</xsl:when>
			<xsl:when test="@type[. = 'plain-text']">
				<xsl:if test="@size">
					<xsl:attribute name="width">100%</xsl:attribute>
				</xsl:if>
				<!--		creates notation for elements		-->
				<xsl:if test="value!='null'">
					<xsl:choose>
						<xsl:when test="value/@class">
							<span>
								<xsl:attribute name="class"><xsl:value-of select="value/@class"/></xsl:attribute>
								<xsl:value-of select="value" disable-output-escaping="yes"/>
								<xsl:text disable-output-escaping="yes"/>
							</span>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="value" disable-output-escaping="yes"/>
							<xsl:text disable-output-escaping="yes"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates regular input text area field form element                               -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'textarea']">
				<xsl:attribute name="id">vform<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
				<!--		creates notation for elements		-->
				<xsl:element name="span">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--		create attributes for nodes that require validation	-->
					<p>
						<xsl:if test="normalize-space(value)!='null'">
							<xsl:value-of select="value"/>
						</xsl:if>
					</p>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates drop down list box form element                           -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'select']">
				<xsl:if test="@size">
					<xsl:attribute name="width">100%</xsl:attribute>
				</xsl:if>
				<xsl:element name="span">
					<!--		create value attribute for conditional entry determined at run time using eval expression	-->
					<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
					<xsl:if test="$partner">
						<xsl:attribute name="partner">yes</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="type">select</xsl:attribute>
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<!--determine whether it is a controller question for conditional entry inside of batch entry-->
					<xsl:if test="$conditionalId">
						<xsl:attribute name="conditional"><xsl:value-of select="$conditionalId"/></xsl:attribute>
						<xsl:attribute name="trigger"><xsl:value-of select="@trigger"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="srt-options-string">
						<xsl:call-template name="srt-codes">
							<xsl:with-param name="delimiter" select="'|'"/>
							<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
							<xsl:with-param name="value" select="value"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:if test="@size">
						<xsl:variable name="test">
							<xsl:call-template name="multiple-entry-selectbox-display">
								<xsl:with-param name="values" select="value"/>
								<xsl:with-param name="srts" select="normalize-space(srt-options-string)"/>
							</xsl:call-template>
						</xsl:variable>
						<!--	remove the leading comma and the trailing comma	-->
						<xsl:choose>
							<xsl:when test="starts-with(normalize-space($test),',')">
								<xsl:value-of select="substring(normalize-space(substring-after($test,',')),1,string-length(normalize-space(substring-after($test,',')))-1)"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="substring(normalize-space($test),1,string-length(normalize-space($test))-1)"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
				</xsl:element>
				<xsl:element name="input">
					<xsl:attribute name="type">hidden</xsl:attribute>
					<xsl:attribute name="id">list<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="normalize-space(srt-options-string)"/></xsl:attribute>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates radio button element                           -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'radio']">
				<xsl:for-each select="options/option">
					<xsl:element name="span">
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:choose>
							<xsl:when test="normalize-space($value)='' ">
								<xsl:if test="normalize-space(@value)=normalize-space(../../value)">
									<xsl:value-of select="@name"/>
								</xsl:if>
							</xsl:when>
							<xsl:otherwise>
								<xsl:if test="normalize-space(@value)=normalize-space($value)">
									<xsl:value-of select="@name"/>
								</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:for-each>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			creates check box element                           -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'checkbox']">
				<xsl:for-each select="options/option">
					<xsl:choose>
						<xsl:when test="ancestor::element/@type='conditional-entry' ">
							<xsl:element name="span">
								<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="normalize-space(@value)=normalize-space(value)  or contains(normalize-space(value), concat(@value,'|'))">
									<b>
										<xsl:value-of select="@label"/>
									</b>
								</xsl:if>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<nobr>
								<input type="checkbox" disabled="disabled">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
									<xsl:if test="./checked or ($disabled='false' and ancestor::controller) or normalize-space(@value)=normalize-space(value)">
										<xsl:attribute name="checked">1</xsl:attribute>
									</xsl:if>
								</input>
								<xsl:value-of select="normalize-space(@label)"/>
							</nobr>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<!-- ===================================================================
				 		Button
				 ===================================================================
				 -->
			<xsl:when test="@type[. = 'button']">
				<xsl:element name="input">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:attribute name="type">button</xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/><xsl:text disable-output-escaping="yes"/></xsl:attribute>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="@onclick">
						<xsl:attribute name="onclick"><xsl:value-of select="@onclick"/></xsl:attribute>
					</xsl:if>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== 
				 		Hidden field type
				 =================================================================== 
				 -->
			<xsl:when test="@type[. = 'hidden']">
				<xsl:element name="input">
					<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<xsl:attribute name="type">hidden</xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="value"/><xsl:text disable-output-escaping="yes"/></xsl:attribute>
					<xsl:if test="@onload">
						<SCRIPT Type="text/javascript" Language="JavaScript">
							<xsl:value-of select="normalize-space(@onload)"/>
						</SCRIPT>
					</xsl:if>
					<xsl:if test="header">
						<xsl:attribute name="header"><xsl:value-of select="normalize-space(./header)"/></xsl:attribute>
					</xsl:if>
				</xsl:element>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 			the rest should be handled by compound elements that can incorporate the simple components                        -->
			<!--			the first element must be the select box type element that controls what the other elements do	-->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'batch-entry']">
				<fieldset>
					<table cellpadding="1" cellspacing="0" border="0" width="100%">
						<xsl:attribute name="id">nestedElementsTable|<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<thead class="normal">
							<!--		move the history box to the top		-->
							<tr>
								<td width="100%" colspan="50">
									<table cellpadding="3" cellspacing="0" border="0" width="100%">
										<thead>
											<tr class="Shaded">
												<td/>
												<!--		this is a placeholder for the function links	-->
												<xsl:variable name="level" select="count(ancestor::element[@type='batch-entry'])+1"/>
												<!--td><xsl:value-of select="$level"></xsl:value-of></td-->
												<xsl:variable name="groupName" select="@name"/>
												<!--td>**<xsl:value-of select="$groupName"></xsl:value-of></td-->
												<xsl:variable name="headers" select="descendant::header[not(.=preceding::header[ancestor::element[@type='batch-entry']/@name=$groupName])]"/>
												<xsl:for-each select="$headers">
													<xsl:if test="$level=count(ancestor::element[@type='batch-entry'])">
														<!--td><xsl:value-of select="count(ancestor::element[@type='batch-entry'])"></xsl:value-of></td-->
														<td>
															<xsl:if test="ancestor::line/@tie-to">
																<xsl:attribute name="tie-to"><xsl:value-of select="ancestor::line/@tie-to"/></xsl:attribute>
															</xsl:if>
															<b>
																<xsl:value-of select="normalize-space(.)"/>
															</b>
														</td>
													</xsl:if>
												</xsl:for-each>
											</tr>
										</thead>
										<tbody>
											<xsl:attribute name="id">nestedElementsHistoryBox|<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
										</tbody>
									</table>
								</td>
							</tr>
						</thead>
						<tbody>
							<!--			recursive call to the create lines functions		-->
							<xsl:call-template name="create-lines">
								<xsl:with-param name="disabled">false</xsl:with-param>
								<xsl:with-param name="parentId">true</xsl:with-param>
							</xsl:call-template>
							<!--			put in the submit action for new or edited info	-->
						</tbody>
					</table>
					<input type="hidden">
						<xsl:attribute name="onfocus">BatchEntryInitializeForView('<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<xsl:attribute name="id">nestedElementsHiddenField<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="./value"/></xsl:attribute>
					</input>
				</fieldset>
				<!--			update the history box with information already entered for this person	-->
				<xsl:if test="not(@popup='true')">
					<!--			update the history box with information already entered for this person	-->
					<SCRIPT Type="text/javascript" Language="JavaScript"><![CDATA[
							BatchEntryInitializeForView(']]><xsl:value-of select="normalize-space(@name)"/><![CDATA[');
						]]></SCRIPT>
				</xsl:if>
			</xsl:when>
			<!--
					===================================================================
							join element types to create a single hidden field value
					===================================================================
				-->
			<xsl:when test="@type[. = 'join']">
				<xsl:choose>
					<xsl:when test="@border">
						<fieldset>
							<xsl:if test="@background='blue'">
								<xsl:attribute name="class">LightBlue</xsl:attribute>
							</xsl:if>
							<xsl:if test="@background='gray'">
								<xsl:attribute name="class">Shaded</xsl:attribute>
							</xsl:if>
							<table cellpadding="1" cellspacing="0" border="0" width="100%" align="left">
								<tbody>
									<xsl:call-template name="create-lines"/>
								</tbody>
							</table>
						</fieldset>
					</xsl:when>
					<xsl:otherwise>
						<table cellpadding="1" cellspacing="0" border="0" align="left">
							<tbody>
								<xsl:call-template name="create-lines"/>
							</tbody>
						</table>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<!-- =================================================================== -->
			<!-- 		display questions based on answers from another quesiton 	-->
			<!--			can be nested    				 -->
			<!-- =================================================================== -->
			<xsl:when test="@type[. = 'conditional-entry']">
				<xsl:variable name="hide">
					<xsl:choose>
						<!-- just for conditional entry inside of batch entry -->
						<!--xsl:when test="normalize-space($parentId)='true' ">false</xsl:when-->
						<xsl:when test="normalize-space(controller/options/option/value)='NI' and controller/@inverse = 'true' ">false</xsl:when>
						<xsl:when test="controller/@inverse = 'true'  and not(contains(normalize-space(controller/options/option/value),'|')) and normalize-space(controller/options/option/@value) = normalize-space(controller/options/option/value)">true</xsl:when>
						<xsl:when test="controller/@inverse = 'true'  and contains(normalize-space(controller/options/option/value),'|') and contains(normalize-space(controller/options/option/value), concat(controller/options/option/@value,'|'))">true</xsl:when>
						<xsl:when test="controller/@inverse='true'">false</xsl:when>
						<xsl:when test="controller/@type = 'checkbox' and not(contains(normalize-space(controller/options/option/value),'|')) and normalize-space(controller/options/option/@value) != normalize-space(controller/options/option/value)">true</xsl:when>
						<xsl:when test="controller/@type = 'checkbox'  and contains(normalize-space(controller/options/option/value),'|')  and  not(contains(normalize-space(controller/options/option/value), concat(controller/options/option/@value,'|')))">true</xsl:when>
						<xsl:when test="controller/@type = 'radio' and (normalize-space(controller/@trigger) != normalize-space(controller/value)  or normalize-space(controller/@trigger) != normalize-space(controller/@default) ) and not(normalize-space(controller/value)='') ">true</xsl:when>
						<!--	control with more than one controller	-->
						<xsl:when test="controller/@type = 'select' and count(controller)>1 and normalize-space(controller[1]/@trigger) != normalize-space(controller[1]/value) and normalize-space(controller[2]/@trigger) != normalize-space(controller[2]/value)">true</xsl:when>
						<xsl:when test="controller/@type = 'select' and not(controller/@trigger)">false</xsl:when>
						<xsl:when test="controller/@type = 'select'  and count(controller)=1  and not(controller/@size) and normalize-space(controller/@trigger) != normalize-space(controller/value)">true</xsl:when>
						<!--if trigger is not set , hide	-->
						<xsl:when test="controller/@type = 'select'  and count(controller)=1  and not(controller/@size) and normalize-space(controller/value)='' ">true</xsl:when>
						<xsl:when test="controller/@type = 'select' and controller/@size and not(contains(controller/value,controller/@trigger))">true</xsl:when>
						<!--	display is default -->
						<xsl:otherwise>false</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:for-each select="controller">
					<tr>
						<xsl:if test="$lineId">
							<xsl:attribute name="id"><xsl:value-of select="$lineId"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="$condition  and ancestor::line/@trigger">
							<xsl:choose>
								<xsl:when test="normalize-space($condition)=normalize-space($trigger)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:when test=" contains(normalize-space($trigger),'|' ) and contains(normalize-space($trigger), normalize-space($condition) ) and not(string-length(normalize-space($condition))=0)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<!--for abcs questions-->
						<xsl:if test="ancestor::line/@abc">
							<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>
							<xsl:attribute name="type">abc</xsl:attribute>
							<xsl:choose>
								<xsl:when test="normalize-space(ancestor::line/@abc)='T' ">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<xsl:if test="not(string-length($trigger)=0)">
							<xsl:attribute name="trigger"><xsl:value-of select="$trigger"/></xsl:attribute>
						</xsl:if>
						<xsl:call-template name="create-the-label"/>
						<td valign="top">
							<xsl:attribute name="id">nestedElementsControllerController<xsl:value-of select="normalize-space(../@name)"/></xsl:attribute>
							<xsl:if test="position() = last()">
								<xsl:attribute name="colspan">3</xsl:attribute>
							</xsl:if>
							<xsl:call-template name="create-element">
								<xsl:with-param name="parentId" select="$parentId"/>
								<xsl:with-param name="conditionalId" select="normalize-space(../@name)"/>
								<xsl:with-param name="disabled">
									<xsl:choose>
										<xsl:when test="@inverse='true' and normalize-space($hide)='false'">true</xsl:when>
										<xsl:when test="@inverse='true' and normalize-space($hide)='true'">false</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="normalize-space($hide)"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:with-param>
								<!--	for radio and checkbox onclick is used but select requires onchange-->
							</xsl:call-template>
						</td>
					</tr>
				</xsl:for-each>
				<!--		DETERMINE TO SHOW THE DEPENDANT QUESTIONS	-->
				<tr>
					<!--xsl:if test="$condition  and ancestor::line/@trigger">
										<xsl:choose>
											<xsl:when test="normalize-space($condition)=normalize-space($trigger)">
												<xsl:attribute name="class">visible</xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:attribute name="class">none</xsl:attribute>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if-->
					<!--for abcs questions-->
					<xsl:if test="ancestor::line/@abc">
						<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>
						<xsl:attribute name="type">abc</xsl:attribute>
						<xsl:choose>
							<xsl:when test="normalize-space(ancestor::line/@abc)='T' ">
								<xsl:attribute name="class">visible</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="class">none</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<td colspan="10">
						<table cellpadding="1" cellspacing="0" border="0" width="100%">
							<tfoot>
								<xsl:choose>
									<xsl:when test="$hide='true'">
										<xsl:attribute name="class">none</xsl:attribute>
									</xsl:when>
									<xsl:when test="$hide='false'">
										<xsl:attribute name="class">visible</xsl:attribute>
									</xsl:when>
								</xsl:choose>
								<xsl:attribute name="id">nestedElementsControllerPayload<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:call-template name="create-lines">
									<xsl:with-param name="condition" select="controller/value"/>
								</xsl:call-template>
							</tfoot>
						</table>
					</td>
				</tr>
			</xsl:when>
			<!--
					===================================================================
							enhanced-data-table element types to create a table with comments or radio buttons
					===================================================================
				-->
			<xsl:when test="@type[. = 'enhanced-data-table']">
				<xsl:call-template name="create-enhanced-data-table"/>
			</xsl:when>
			<!--
					===================================================================
							the data table type for looking at collection of objects
					===================================================================
				-->
			<xsl:when test="@type[. = 'data-table']">
				<!--<xsl:if test="string-length(./value) != 0">-->
				<table role ="presentation" width="100%" border="0" id="root">
					<xsl:if test="@page-size">
						<thead>
							<xsl:attribute name="pageSize"><xsl:value-of select="@page-size"/></xsl:attribute>
							<tr>
								<td align="right">
									<!-- Next/Prev table -->
									<table border="0">
										<tr>
											<td class="none">
												<a id="previous">
													<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
													<xsl:attribute name="onclick">previousPage(findCorrespondingNextPageHref(this));</xsl:attribute>
                                                        Previous</a>
											</td>
											<td class="none"> | </td>
											<td>
												<xsl:call-template name="data-table-ShowNextIfNeeded">
													<xsl:with-param name="delimiter" select="'|'"/>
													<xsl:with-param name="string" select="value"/>
													<xsl:with-param name="page-size" select="@page-size"/>
												</xsl:call-template>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</thead>
					</xsl:if>
					<tr>
						<td>
							<table class="TableOuter" width="100%">
								<tr>
									<td>
										<table cellpadding="3" cellspacing="0" border="0" width="100%" class="TableInner">
											<tbody>
												<tr class="Shaded" rowNumber="0" valign="top">
													<xsl:for-each select="header[not(position='bottom')]">
														<td class="ColumnHeader" align="center">
															<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
															<xsl:if test="@width">
																<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
																<xsl:attribute name="align">left</xsl:attribute>
															</xsl:if>
															<!--xsl:choose>
															<xsl:when test="@link" we nolonger need to specify a link-->
															<a class="SortColumnHeader">
																<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
																<xsl:attribute name="onclick">sortTableOnColumn(this);</xsl:attribute>
																<xsl:if test="@sortonload[. = 'true']">
																	<xsl:attribute name="id"><xsl:value-of select="generate-id(..)"/></xsl:attribute>
																</xsl:if>
																<xsl:if test="@sortorder">
																	<xsl:attribute name="sortOrder"><xsl:value-of select="@sortorder"/></xsl:attribute>
																</xsl:if>
																<b>
																	<xsl:value-of select="."/>
																</b>
															</a>
															<!--/xsl:when>
															<xsl:otherwise>
																<b>
																	<xsl:value-of select="."/>
																</b>
															</xsl:otherwise>
														</xsl:choose-->
														</td>
													</xsl:for-each>
												</tr>
											</tbody>
											<xsl:choose>
												<xsl:when test="string-length(value)!=0">
													<tfoot>
														<xsl:call-template name="data-table-find-rows">
															<xsl:with-param name="delimiter" select="'|'"/>
															<xsl:with-param name="string" select="value"/>
															<xsl:with-param name="makeLinksColumn" select="@makeLinksColumn"/>
															<xsl:with-param name="editLink" select="edit-link"/>
															<xsl:with-param name="viewLink" select="view-link"/>
															<xsl:with-param name="page-size" select="@page-size"/>
															<xsl:with-param name="coldelimiter" select=" '$' "/>
															<xsl:with-param name="rowPosition" select=" 'top' "/>
														</xsl:call-template>
													</tfoot>
												</xsl:when>
												<xsl:otherwise>
													<tfoot>
														<tr>
															<td colspan="6">
																<p>
																	<center>
																		<b>There is no information to display</b>
																	</center>
																</p>
															</td>
														</tr>
													</tfoot>
												</xsl:otherwise>
											</xsl:choose>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							<!-- Next/Prev table -->
							<table border="0">
								<tr>
									<td class="none">
										<a id="previous">
											<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
											<xsl:attribute name="onclick">previousPage(findCorrespondingNextPageHref(this));</xsl:attribute>
                                                                                                                        Previous
                                                                                                                </a>
									</td>
									<td class="none"> | </td>
									<td>
										<xsl:call-template name="data-table-ShowNextIfNeeded">
											<xsl:with-param name="delimiter" select="'|'"/>
											<xsl:with-param name="string" select="value"/>
											<xsl:with-param name="page-size" select="@page-size"/>
										</xsl:call-template>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<SCRIPT Type="text/javascript" Language="JavaScript">
				if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
				   sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
			}</SCRIPT>
				<!--</xsl:if>-->
			</xsl:when>
			<xsl:when test="@type[. = 'queue-data-table']">
				<!--<xsl:if test="string-length(./value) != 0">-->
				<table role ="presentation" width="100%" border="0" cellpadding="0" cellspacing="0" id="root">
					<thead>
						<tr>
							<td align="right">
								<!-- Next/Prev table -->
								<table border="0" cellpadding="0" cellspacing="0">
									<!--th colspan="4" align="center">Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/></th-->
									<xsl:if test="not(@pagenumbers)">
										<tr>
											<td class="boldEightBlack" colspan="4" align="center">
												Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number	(totalRecords) div number(maxRowCount)))"/>
											</td>
										</tr>
									</xsl:if>
									<tr>
										<xsl:if test="(currentIndex>0)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="0"/></xsl:attribute>
																<img src="list_remove_all.jpg" border="0" alt="First Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">First</td>
													</tr>
												</table>
											</td>
										</xsl:if>
										<xsl:if test="(currentIndex>=maxRowCount)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-number(maxRowCount)"/></xsl:attribute>
																<img src="list_remove.jpg" border="0" alt="Previous Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Prev</td>
													</tr>
												</table>
											</td>
										</xsl:if>
										<xsl:if test="number(totalRecords) > number(currentIndex)+number(maxRowCount)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+number(maxRowCount)"/></xsl:attribute>
																<img src="list_add.jpg" border="0" alt="Next Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Next</td>
													</tr>
												</table>
											</td>
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:choose>
																	<xsl:when test="(number(totalRecords) div number(maxRowCount)) = ceiling(number(totalRecords) div number(maxRowCount))">
																		<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount) -number(maxRowCount)"/></xsl:attribute>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount)"/></xsl:attribute>
																	</xsl:otherwise>
																</xsl:choose>
																<img src="list_add_all.jpg" border="0" alt="Last Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Last</td>
													</tr>
												</table>
											</td>
										</xsl:if>
									</tr>
								</table>
							</td>
						</tr>
					</thead>
					<tr>
						<td>
							<table class="TableOuter" width="100%">
								<tr>
									<td>
										<table cellpadding="3" cellspacing="0" border="0" width="100%" class="TableInner" id="notificationApprovalQueueTable">
											<tbody>
												<tr class="Shaded" rowNumber="0" valign="top">
													<xsl:for-each select="noLinkHeader[not(position='bottom')]">
														<td class="ColumnHeader" align="center">
															<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
															<xsl:if test="@width">
																<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
																<xsl:attribute name="align">center</xsl:attribute>
															</xsl:if>
															<b>
																<xsl:value-of select="."/>
															</b>
														</td>
													</xsl:for-each>
													<xsl:for-each select="header[not(position='bottom')]">
														<td class="ColumnHeader" align="center">
															<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
															<xsl:if test="@width">
																<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
																<xsl:attribute name="align">center</xsl:attribute>
															</xsl:if>
															<xsl:if test="@methodName">
																<xsl:variable name="methodName">
																	<xsl:value-of select="@methodName"/>
																</xsl:variable>
															</xsl:if>
															<a class="SortColumnHeader">
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(../sortHref)"/>&amp;direction=<xsl:value-of select="normalize-space(../sortDirection)"/>&amp;sortMethod=<xsl:value-of select="normalize-space(@methodName)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(../currentIndex)"/></xsl:attribute>
																<b>
																	<xsl:value-of select="."/>
																</b>
																<!--xsl:choose>
																	<xsl:when test="(normalize-space(../sortDirection)='false')">
																		<xsl:value-of select="'^'"/>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:value-of select="'v'"/>
																	</xsl:otherwise>
																</xsl:choose-->
															</a>
														</td>
													</xsl:for-each>
												</tr>
											</tbody>
											<tfoot>
												<xsl:choose>
													<xsl:when test="record">
														<xsl:for-each select="record">
															<!-- declare and the required variables-->
															<xsl:variable name="className">
																<!-- VL code to alternate color of data lines -->
																<xsl:choose>
																	<xsl:when test=" position() mod 2">NotShaded</xsl:when>
																	<xsl:otherwise>Shaded</xsl:otherwise>
																</xsl:choose>
															</xsl:variable>
															<tr valign="top">
																<xsl:attribute name="class"><xsl:value-of select="$className"/></xsl:attribute>
																<xsl:call-template name="create-data-columns">
																</xsl:call-template>
																<!--create the hidden revisions-->
															</tr>
														</xsl:for-each>
													</xsl:when>
													<xsl:otherwise>
														<TR>
															<TD ALIGN="LEFT" VALIGN="MIDDLE" colspan="100%">
																<FONT SIZE="2" COLOR="#3131B1" FACE="Helvetica">
																	<SPAN STYLE="font-size:12;">There is no information to display</SPAN>
																</FONT>
															</TD>
														</TR>
													</xsl:otherwise>
												</xsl:choose>
											</tfoot>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							<!-- Next/Prev table -->
							<table border="0" cellpadding="0" cellspacing="0">
								<!--th colspan="4" align="center">Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/></th-->
								<xsl:if test="not(@pagenumbers)">
									<tr>
										<td class="boldEightBlack" colspan="4" align="center">
											Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/>
										</td>
									</tr>
								</xsl:if>
								<tr>
									<xsl:if test="(currentIndex>0)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="0"/></xsl:attribute>
															<img src="list_remove_all.jpg" border="0" alt="First Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">First</td>
												</tr>
											</table>
										</td>
									</xsl:if>
									<xsl:if test="(currentIndex>=maxRowCount)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-number(maxRowCount)"/></xsl:attribute>
															<img src="list_remove.jpg" border="0" alt="Previous Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Prev</td>
												</tr>
											</table>
										</td>
									</xsl:if>
									<xsl:if test="number(totalRecords) > number(currentIndex)+number(maxRowCount)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+number(maxRowCount)"/></xsl:attribute>
															<img src="list_add.jpg" border="0" alt="Next Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Next</td>
												</tr>
											</table>
										</td>
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:choose>
																<xsl:when test="(number(totalRecords) div number(maxRowCount)) = ceiling(number(totalRecords) div number(maxRowCount))">
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount) -number(maxRowCount)"/></xsl:attribute>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount)"/></xsl:attribute>
																</xsl:otherwise>
															</xsl:choose>
															<img src="list_add_all.jpg" border="0" alt="Last Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Last</td>
												</tr>
											</table>
										</td>
									</xsl:if>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<SCRIPT Type="text/javascript" Language="JavaScript">
				if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
				   sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
			}</SCRIPT>
				<!--</xsl:if>-->
			</xsl:when>
			
			<xsl:when test="@type[. = 'queue-data-table-contacttracing']">
				<!--<xsl:if test="string-length(./value) != 0">-->
				<table role ="presentation" width="100%" border="0" cellpadding="0" cellspacing="0" id="root">
					<thead>
						<tr>
							<td align="right">
								<!-- Next/Prev table -->
								<table border="0" cellpadding="0" cellspacing="0">
									<!--th colspan="4" align="center">Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/></th-->
									<!--<xsl:if test="not(@pagenumbers)">
										<tr>
											<td class="boldEightBlack" colspan="4" align="center">
												Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number	(totalRecords) div number(maxRowCount)))"/>
											</td>
										</tr>
									</xsl:if>-->

									<tr>
										<xsl:if test="(currentIndex>0)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="0"/></xsl:attribute>
																<img src="list_remove_all.jpg" border="0" alt="First Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">First</td>
													</tr>
												</table>
											</td>
										</xsl:if>
										<xsl:if test="(currentIndex>=maxRowCount)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-number(maxRowCount)"/></xsl:attribute>
																<img src="list_remove.jpg" border="0" alt="Previous Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Prev</td>
													</tr>
												</table>
											</td>
										</xsl:if>
										<xsl:if test="number(totalRecords) > number(currentIndex)+number(maxRowCount)">
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+number(maxRowCount)"/></xsl:attribute>
																<img src="list_add.jpg" border="0" alt="Next Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Next</td>
													</tr>
												</table>
											</td>
											<td>
												<table cellpadding="0" cellspacing="3">
													<tr>
														<td>
															<a>
																<xsl:choose>
																	<xsl:when test="(number(totalRecords) div number(maxRowCount)) = ceiling(number(totalRecords) div number(maxRowCount))">
																		<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount) -number(maxRowCount)"/></xsl:attribute>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount)"/></xsl:attribute>
																	</xsl:otherwise>
																</xsl:choose>
																<img src="list_add_all.jpg" border="0" alt="Last Page" align="top"/>
															</a>
														</td>
													</tr>
													<tr>
														<td class="boldEightBlack" valign="top">Last</td>
													</tr>
												</table>
											</td>
										</xsl:if>
									</tr>
								</table>
							</td>
						</tr>
					</thead>
					<tr>
						<td>
							<table class="TableOuter" width="100%">
								<tr>
									<td>
										<table cellpadding="3" cellspacing="0" border="0" width="100%" class="TableInner" id="notificationApprovalQueueTable">
											<tbody>
												<tr class="Shaded" rowNumber="0" valign="top">
													<xsl:for-each select="noLinkHeader[not(position='bottom')]">
														<td class="ColumnHeader" align="center">
															<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
															<xsl:if test="@width">
																<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
																<xsl:attribute name="align">center</xsl:attribute>
															</xsl:if>
															<b>
																<xsl:value-of select="."/>
															</b>
														</td>
													</xsl:for-each>
													<xsl:for-each select="header[not(position='bottom')]">
														<td class="ColumnHeader" align="center">
															<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
															<xsl:if test="@width">
																<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
																<xsl:attribute name="align">center</xsl:attribute>
															</xsl:if>
															<xsl:if test="@methodName">
																<xsl:variable name="methodName">
																	<xsl:value-of select="@methodName"/>
																</xsl:variable>
															</xsl:if>
																
															<a class="SortColumnHeader">
																<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
																<xsl:attribute name="onclick">sortTableOnColumn(this);</xsl:attribute>
																<xsl:if test="@sortonload[. = 'true']">
																	<xsl:attribute name="id"><xsl:value-of select="generate-id(..)"/></xsl:attribute>
																</xsl:if>
																<xsl:if test="@sortorder">
																	<xsl:attribute name="sortOrder"><xsl:value-of select="@sortorder"/></xsl:attribute>
																</xsl:if>
																<b>
																	<xsl:value-of select="."/>
																</b>
															

														<!--	 <a class="SortColumnHeader">
																<xsl:attribute name="href"><xsl:value-of select="normalize-space(../sortHref)"/>&amp;direction=<xsl:value-of select="normalize-space(../sortDirection)"/>&amp;sortMethod=<xsl:value-of select="normalize-space(@methodName)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(../currentIndex)"/></xsl:attribute>
																<b>
																	<xsl:value-of select="."/>
																</b>
																xsl:choose>
																	<xsl:when test="(normalize-space(../sortDirection)='false')">
																		<xsl:value-of select="'^'"/>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:value-of select="'v'"/>
																	</xsl:otherwise>
																</xsl:choose-->
															</a>
														</td>
													</xsl:for-each>
												</tr>
											</tbody>
											<tfoot>
												<xsl:choose>
													<xsl:when test="record">
														<xsl:for-each select="record">
															<!-- declare and the required variables-->
															<xsl:variable name="className">
																<!-- VL code to alternate color of data lines -->
																<xsl:choose>
																	<xsl:when test=" position() mod 2">NotShaded</xsl:when>
																	<xsl:otherwise>Shaded</xsl:otherwise>
																</xsl:choose>
															</xsl:variable>
															<tr valign="top">
																<xsl:attribute name="class"><xsl:value-of select="$className"/></xsl:attribute>
																<xsl:call-template name="create-data-columns">
																</xsl:call-template>
																<!--create the hidden revisions-->
															</tr>
														</xsl:for-each>
													</xsl:when>
													<xsl:otherwise>
														<TR>
															<TD ALIGN="LEFT" VALIGN="MIDDLE" colspan="100%">
																<FONT SIZE="2" COLOR="#3131B1" FACE="Helvetica">
																	<SPAN STYLE="font-size:12;">There is no information to display</SPAN>
																</FONT>
															</TD>
														</TR>
													</xsl:otherwise>
												</xsl:choose>
											</tfoot>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							<!-- Next/Prev table -->
							<table border="0" cellpadding="0" cellspacing="0">
								<!--th colspan="4" align="center">Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/></th-->
								<!--<xsl:if test="not(@pagenumbers)">
									<tr>
										<td class="boldEightBlack" colspan="4" align="center">
											Page <xsl:value-of select="concat(concat(normalize-space(currentIndex) div number(maxRowCount) + 1, ' of '),  ceiling(number(totalRecords) div number(maxRowCount)))"/>
										</td>
									</tr>
								</xsl:if>-->
								<tr>
									<xsl:if test="(currentIndex>0)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="0"/></xsl:attribute>
															<img src="list_remove_all.jpg" border="0" alt="First Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">First</td>
												</tr>
											</table>
										</td>
									</xsl:if>
									<xsl:if test="(currentIndex>=maxRowCount)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-number(maxRowCount)"/></xsl:attribute>
															<img src="list_remove.jpg" border="0" alt="Previous Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Prev</td>
												</tr>
											</table>
										</td>
									</xsl:if>
									<xsl:if test="number(totalRecords) > number(currentIndex)+number(maxRowCount)">
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+number(maxRowCount)"/></xsl:attribute>
															<img src="list_add.jpg" border="0" alt="Next Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Next</td>
												</tr>
											</table>
										</td>
										<td>
											<table cellpadding="0" cellspacing="3">
												<tr>
													<td>
														<a>
															<xsl:choose>
																<xsl:when test="(number(totalRecords) div number(maxRowCount)) = ceiling(number(totalRecords) div number(maxRowCount))">
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount) -number(maxRowCount)"/></xsl:attribute>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="floor(number(totalRecords) div number(maxRowCount)) * number(maxRowCount)"/></xsl:attribute>
																</xsl:otherwise>
															</xsl:choose>
															<img src="list_add_all.jpg" border="0" alt="Last Page" align="top"/>
														</a>
													</td>
												</tr>
												<tr>
													<td class="boldEightBlack" valign="top">Last</td>
												</tr>
											</table>
										</td>
									</xsl:if>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<SCRIPT Type="text/javascript" Language="JavaScript">
				if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
				   sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
			}</SCRIPT>
				<!--</xsl:if>-->
			</xsl:when>

			
			<!--						Entity search 				-->
			<!--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-->
			<xsl:when test="@type[. = 'entity-search']">
				<table cellpadding="2" cellspacing="0" border="0" width="100%">
					<xsl:attribute name="id">entity-table-<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
					<tr>
						<td width="80%">
							<table cellpadding="2" cellspacing="0" border="0" width="100%">
								<xsl:call-template name="create-lines"/>
							</table>
						</td>
						<td width="20%" valign="top">
								&nbsp;
							</td>
					</tr>
				</table>
			</xsl:when>
			<!--
					===================================================================
							tree view element for collapsing data
					===================================================================
				-->
			<xsl:when test="@type[. = 'tree-view']">
				<table cellpadding="2" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<td valign="middle" align="left" colspan="2">
								<img src="plus_sign.gif" border="0" alt="">
									<xsl:attribute name="onclick">treeViewControl(this);</xsl:attribute>
								</img>&nbsp;
							<b>
									<xsl:value-of select="@name"/>
								</b>
							</td>
						</tr>
					</thead>
					<tbody class="none">
						<xsl:variable name="childCntr">
							<xsl:value-of select="count(links/link)"/>
						</xsl:variable>
						<xsl:for-each select="links/link">
							<xsl:if test="$childCntr > 1">
								<tr>
									<td>&nbsp;</td>
									<td>
										<a>
											<xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
											<xsl:value-of select="."/>
										</a>
									</td>
								</tr>
							</xsl:if>
							<xsl:if test="$childCntr = 1">
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;
										<a>
											<xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
											<xsl:value-of select="."/>
										</a>
									</td>
								</tr>
							</xsl:if>
							
						</xsl:for-each>
					</tbody>
				</table>
			</xsl:when>
			<xsl:otherwise>&nbsp;&nbsp;&nbsp;</xsl:otherwise>
		</xsl:choose>
		<!--#################################################################################
								GENERAL SECTION THAT WILL BE APPLIED REGARDLESS OF TYPE
		####################################################################################-->
		<xsl:if test="@onloadview">
			<SCRIPT Type="text/javascript" Language="JavaScript">
				<xsl:value-of select="normalize-space(@onloadview)"/>
			</SCRIPT>
		</xsl:if>
		<xsl:if test="partner">
			<xsl:for-each select="partner">
						&nbsp;
						<xsl:if test="string-length(normalize-space(@label)) != 0">
					<b>
						<xsl:value-of select="normalize-space(@label)"/>:&nbsp;</b>
				</xsl:if>
				<xsl:call-template name="create-element">
					<xsl:with-param name="parentId" select="$parentId"/>
					<xsl:with-param name="disabled" select="$disabled"/>
					<xsl:with-param name="partner" select=" 'yes' "/>
				</xsl:call-template>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template name="create-data-columns">
		<xsl:for-each select="column">
			<xsl:choose>
				<xsl:when test="@link1">
					<xsl:variable name="link1">
						<xsl:value-of select="@link1"/>
					</xsl:variable>
					<td align="center">
						<table border="0" cellspacing="0" cellpadding="2">
							<tr>
								<td align="center">
									<a>
										<xsl:attribute name="href"><xsl:value-of select="normalize-space(../link1)"/></xsl:attribute>
										<xsl:choose>
											<xsl:when test="srt-options-string">
												<xsl:call-template name="srt-codes">
													<xsl:with-param name="delimiter" select="'|'"/>
													<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
													<xsl:with-param name="value" select="value"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="normalize-space(value)"/>
											</xsl:otherwise>
										</xsl:choose>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</xsl:when>
				<xsl:when test="@link2">
					<td align="center">
						<table border="0" cellspacing="0" cellpadding="2">
							<tr>
								<td align="center">
									<a>
										<xsl:attribute name="href"><xsl:value-of select="normalize-space(../link2)"/></xsl:attribute>
										<xsl:choose>
											<xsl:when test="srt-options-string">
												<xsl:call-template name="srt-codes">
													<xsl:with-param name="delimiter" select="'|'"/>
													<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
													<xsl:with-param name="value" select="value"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="normalize-space(value)"/>
											</xsl:otherwise>
										</xsl:choose>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</xsl:when>
				<xsl:when test="@link3">
					<td align="center">
						<table border="0" cellspacing="0" cellpadding="2">
							<tr>
								<td align="center">
									<a>
										<xsl:attribute name="href"><xsl:value-of select="normalize-space(../link3)"/></xsl:attribute>
										<xsl:choose>
											<xsl:when test="srt-options-string">
												<xsl:call-template name="srt-codes">
													<xsl:with-param name="delimiter" select="'|'"/>
													<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
													<xsl:with-param name="value" select="value"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="normalize-space(value)"/>
											</xsl:otherwise>
										</xsl:choose>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</xsl:when>
				<xsl:when test="@type[. = 'tree-view']">
					<td align="center">
						<table border="0" cellspacing="0" cellpadding="2">
							<tr>
								<td valign="middle" align="center">
									<img src="plus_sign.gif" border="0" alt="">
										<xsl:attribute name="onclick">displayApprovalQueueFull(<xsl:value-of select="normalize-space(../uid)"/>, this);</xsl:attribute>
									</img>
								</td>
							</tr>
						</table>
					</td>
				</xsl:when>
				<xsl:when test="@type[. = 'checkbox']">
					<td align="center">
						<table cellpadding="2" cellspacing="2" border="0">
							<tr>
								<td align="center">
									<xsl:if test="@name">
										<xsl:element name="input">
											<xsl:attribute name="type">checkbox</xsl:attribute>
											<xsl:attribute name="name"><xsl:value-of select="concat(normalize-space(@name),normalize-space(../notfuid))"/></xsl:attribute>
											<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="normalize-space(options/option/@value)"/></xsl:attribute>
										</xsl:element>
									</xsl:if>
								</td>
							</tr>
						</table>
					</td>
				</xsl:when>
				<xsl:otherwise>
					<td align="center">
						<table border="0" cellspacing="0" cellpadding="2">
							<tr>
								<td align="center">
									<xsl:choose>
										<xsl:when test="srt-options-string">
											<xsl:call-template name="srt-codes">
												<xsl:with-param name="delimiter" select="'|'"/>
												<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
												<xsl:with-param name="value" select="value"/>
											</xsl:call-template>
										</xsl:when>
										<xsl:when test="multiline">
											<xsl:call-template name="data-table-multiline-columns">
												<xsl:with-param name="input" select="value"/>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="normalize-space(value)"/>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</table>
					</td>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:if test="recordPlainComments">
			<tr rowID="{generate-id()}">
				<xsl:choose>
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="class">NotShaded</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">Shaded</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<td/>
				<td class="ColumnHeader" colspan="7">
					<xsl:value-of select="recordPlainComments/@label"/>:
					<xsl:value-of select="normalize-space(recordPlainComments)"/>
				</td>
			</tr>
		</xsl:if>
		<xsl:if test="recordComments">
			<tr rowID="{generate-id()}">
				<xsl:choose>
					<!-- VL code to alternate color of data lines -->
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="class">NotShaded</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">Shaded</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<td colspan="10">
					<xsl:if test="recordComments/@label">
						<table cellpadding="0" border="0" cellspacing="4">
							<tr valign="top">
								<xsl:attribute name="uid"><xsl:value-of select="normalize-space(uid)"/></xsl:attribute>
								<td valign="top">
									<b>
										<xsl:value-of select="recordComments/@label"/>:</b>
								</td>
								<td/>
								<td valign="top" align="right">
									<textarea rows="4" cols="65">
										<xsl:attribute name="name"><xsl:value-of select="normalize-space(recordComments/@name)"/></xsl:attribute>
										<xsl:value-of select="normalize-space(recordComments)"/>
									</textarea>
								</td>
								<td/>
								<xsl:if test="button">
									<td valign="bottom" align="left">
										<xsl:for-each select="button">
											<input type="button" style="width: 5em; font-size: 9.5pt;">
												<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
												<xsl:attribute name="value"><xsl:value-of select="@label"/></xsl:attribute>
												<xsl:attribute name="onClick"><xsl:value-of select="@onClick"/></xsl:attribute>
											</input>
											<br/>
										</xsl:for-each>
									</td>
								</xsl:if>
							</tr>
						</table>
					</xsl:if>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		creates "Next" link for paging if needed.  Determines if it is visible or not.
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="data-table-ShowNextIfNeeded">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="page-size"/>
		<xsl:param name="lineCounter">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$page-size &lt; $lineCounter">
				<a id="next">
					<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
					<xsl:attribute name="onclick">nextPage(findCorrespondingNextPageHref(this));</xsl:attribute>
					Next
					</a>
			</xsl:when>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:variable name="parsedString">
					<xsl:choose>
						<xsl:when test="contains(substring-before($string, $delimiter),'[[')">
							<xsl:value-of select="substring-before(substring-before($string, $delimiter),'[[')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-before($string, $delimiter)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:call-template name="data-table-ShowNextIfNeeded">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="page-size" select="$page-size"/>
					<xsl:with-param name="lineCounter">
						<xsl:value-of select="$lineCounter + 1"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		create the table row for the data table type
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="data-table-find-rows">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="lineCounter">0</xsl:param>
		<xsl:param name="editLink"/>
		<xsl:param name="viewLink"/>
		<xsl:param name="page-size"/>
		<!-- VL counts lines so that different color can be for even and odd lines -->
		<xsl:param name="makeLinksColumn"/>
		<!-- VL indicates what column number should have all of its data as a link -->
		<xsl:param name="coldelimiter"/>
		<xsl:param name="rowPosition"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<tr>
					<xsl:if test="$rowPosition!='bottom' ">
						<xsl:attribute name="id">sortParent</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="rowNumber"><xsl:value-of select="$lineCounter"/></xsl:attribute>
					<xsl:attribute name="page"><xsl:value-of select="floor($lineCounter div $page-size) + 1"/></xsl:attribute>
					<xsl:choose>
						<xsl:when test="string-length($page-size)>0 and number($lineCounter) > (number($page-size)-1)">
							<xsl:attribute name="class">none</xsl:attribute>
						</xsl:when>
						<xsl:when test="$rowPosition='bottom'"/>
						<xsl:when test=" number($lineCounter) mod 2 ">
							<xsl:attribute name="class">NotShaded</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="class">Shaded</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:variable name="parsedString">
						<xsl:choose>
							<xsl:when test="contains(substring-before($string, $delimiter),'[[')">
								<xsl:value-of select="substring-before(substring-before($string, $delimiter),'[[')"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="substring-before($string, $delimiter)"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:call-template name="data-table-find-cols">
						<xsl:with-param name="delimiter">
							<xsl:value-of select="$coldelimiter"/>
						</xsl:with-param>
						<xsl:with-param name="string" select="$parsedString"/>
						<xsl:with-param name="viewLink" select="$viewLink"/>
						<xsl:with-param name="page-size" select="$page-size"/>
						<xsl:with-param name="rowPosition" select="$rowPosition"/>
					</xsl:call-template>
				</tr>
				<xsl:if test="contains(substring-before($string, $delimiter),'[[')">
					<tr>
						<xsl:if test="$rowPosition!='bottom' ">
							<xsl:attribute name="id">sortChild</xsl:attribute>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="string-length($page-size)>0 and number($lineCounter) > (number($page-size)-1)">
								<xsl:attribute name="class">none</xsl:attribute>
							</xsl:when>
							<xsl:when test="$rowPosition='bottom'"/>
							<xsl:when test=" number($lineCounter) mod 2 ">
								<xsl:attribute name="class">NotShaded</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="class">Shaded</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<td colspan="100%">
							<table border="0" width="100%">
								<tbody>
									<xsl:call-template name="data-table-find-rows">
										<xsl:with-param name="delimiter" select="'$'"/>
										<xsl:with-param name="string" select="substring-after(substring-before($string, $delimiter),'[[')"/>
										<xsl:with-param name="viewLink" select="$viewLink"/>
										<xsl:with-param name="page-size" select="$page-size"/>
										<xsl:with-param name="rowPosition" select=" 'bottom' "/>
										<xsl:with-param name="coldelimiter" select=" '~' "/>
									</xsl:call-template>
								</tbody>
							</table>
						</td>
					</tr>
				</xsl:if>
				<xsl:call-template name="data-table-find-rows">
					<xsl:with-param name="delimiter" select="$delimiter"/>
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="lineCounter">
						<xsl:value-of select="$lineCounter + 1"/>
					</xsl:with-param>
					<xsl:with-param name="makeLinksColumn">
						<xsl:value-of select="$makeLinksColumn"/>
					</xsl:with-param>
					<xsl:with-param name="viewLink" select="$viewLink"/>
					<xsl:with-param name="editLink" select="$editLink"/>
					<xsl:with-param name="page-size" select="$page-size"/>
					<xsl:with-param name="coldelimiter" select="$coldelimiter"/>
					<xsl:with-param name="rowPosition" select="$rowPosition"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		create the table columns for the data table type
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="data-table-find-cols">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="lineCounter"/>
		<xsl:param name="columnCounter">1</xsl:param>
		<xsl:param name="viewLink"/>
		<xsl:param name="rowPosition"/>
		<xsl:variable name="parseString">
			<xsl:choose>
				<xsl:when test="header[position()=$columnCounter]/element and $rowPosition='top'">
					<xsl:value-of select="$string"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after($string, $delimiter)"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<td align="center" valign="top">
					<xsl:attribute name="columnNumber"><xsl:value-of select="$columnCounter"/></xsl:attribute>
					<xsl:if test="footer[position()=$columnCounter] and $rowPosition='bottom'">
						<xsl:attribute name="width"><xsl:value-of select="footer[position()=$columnCounter]/@width"/></xsl:attribute>
						<xsl:attribute name="align">left</xsl:attribute>
					</xsl:if>
					<xsl:if test="header[position()=$columnCounter]/@width">
						<xsl:attribute name="align">left</xsl:attribute>
					</xsl:if>
					<xsl:choose>
						<xsl:when test="header[position()=$columnCounter]/element and $rowPosition='top' ">
							<xsl:for-each select="header[position()=$columnCounter]/element">
								<xsl:call-template name="create-element">
									<xsl:with-param name="name">
										<xsl:value-of select="concat( @name,substring-before(substring-after($parseString, @name),'--') )"/>
									</xsl:with-param>
									<xsl:with-param name="value">
										<xsl:value-of select="substring-after(substring-before(substring-after($parseString, @name),'---'),'--')"/>
									</xsl:with-param>
								</xsl:call-template>
							</xsl:for-each>
						</xsl:when>
						<xsl:when test="contains(substring-before($string, $delimiter), '--') and contains($viewLink,'NOT_DISPLAYED')">
							<xsl:value-of select="substring-before(substring-after(substring-before($string, $delimiter),'--'),'--')"/>
						</xsl:when>
						<xsl:when test="contains(substring-before($string, $delimiter), '--')">
							<xsl:variable name="link-index">
								<xsl:value-of select="string(number(substring-before(substring-before($string, $delimiter),'--')))"/>
							</xsl:variable>
							<xsl:if test="$link-index='NaN' ">
								<a>
									<xsl:attribute name="href"><xsl:value-of select="$viewLink"/><xsl:value-of select="substring-before(substring-after($string, '**'), '**')"/></xsl:attribute>
									<xsl:value-of select="substring-before(substring-after(substring-before($string, $delimiter),'--'),'--')"/>
								</a>
							</xsl:if>
							<xsl:if test="not($link-index='NaN') ">
								<a>
									<xsl:if test="$viewLink/@calljs">
										<xsl:attribute name="onclick"><xsl:value-of select="'javascript:showPayload(this);return false;'"/></xsl:attribute>
									</xsl:if>
									<xsl:attribute name="href"><xsl:value-of select="view-link[position()=$link-index]"/><xsl:value-of select="substring-before(substring-after($string, concat('(',$link-index,')','**')), '**')"/></xsl:attribute>
									<xsl:value-of select="substring-before(substring-after(substring-before($string, $delimiter),'--'),'--')"/>
								</a>
							</xsl:if>
						</xsl:when>
						<xsl:when test="contains(substring-before($string, $delimiter),'!')">
							<xsl:call-template name="data-table-multiline-columns">
								<xsl:with-param name="input" select="substring-before($string, $delimiter)"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:when test="normalize-space(substring-before($string, $delimiter))='' ">&nbsp;</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-before($string, $delimiter)"/>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<xsl:call-template name="data-table-find-cols">
					<xsl:with-param name="delimiter" select="$delimiter"/>
					<xsl:with-param name="string" select="$parseString"/>
					<xsl:with-param name="columnCounter">
						<xsl:value-of select="$columnCounter + 1"/>
					</xsl:with-param>
					<xsl:with-param name="viewLink" select="$viewLink"/>
					<xsl:with-param name="rowPosition" select="$rowPosition"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template to create multiline columns
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="data-table-multiline-columns">
		<xsl:param name="input"/>
		<xsl:choose>
			<xsl:when test="contains($input, '!') ">
				<xsl:value-of select="substring-before($input, '!')"/>
				<br/>
				<xsl:call-template name="data-table-multiline-columns">
					<xsl:with-param name="input" select="substring-after($input, '!')"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		templates to display the srt coded values coming back from database
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-codes">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:call-template name="srt-create-options">
					<xsl:with-param name="delimiter" select="'$'"/>
					<xsl:with-param name="string" select="substring-before($string, $delimiter)"/>
					<xsl:with-param name="value" select="$value"/>
				</xsl:call-template>
				<xsl:call-template name="srt-codes">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="value" select="$value"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="srt-create-options">
					<xsl:with-param name="delimiter" select="'$'"/>
					<xsl:with-param name="string" select="$string"/>
					<xsl:with-param name="value" select="$value"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		create the table columns for the data table type
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-create-options">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:if test="$value=substring-before($string, $delimiter)">
					<xsl:value-of select="substring-after($string, $delimiter)"/>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		VL: enhanced-data-table will replace 'data-table' element and will be more easy to use
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="create-enhanced-data-table">
		<!--For testing purpose-->
		<!--	<table border="2" bordercolor="red">
		<tr><td>
			recordsPerPage: <xsl:value-of select="@recordsPerPage"/><br/>
			pagingLinkBase: <xsl:value-of select="@pagingLinkBase"/><br/>
			displayPage: <xsl:value-of select="@displayPage"/><br/>
			data size: <xsl:value-of select="count(./record)"/><br/>
		</td></tr></table>-->
		<!--		<table cellpadding="0" cellspacing="0" border="0" width="600">-->
		<table class="TableOuter" border="0" width="100%">
			<tr>
				<td>
					<!--MMMMMM -->
					<table cellpadding="5" cellspacing="0" border="0" width="100%" class="TableInner">
						<xsl:if test="@page-size">
							<thead>
								<xsl:attribute name="pageSize"><xsl:value-of select="@page-size"/></xsl:attribute>
								<tr>
									<td colspan="100%" align="right" valign="bottom">
										<table border="0">
											<tr>
												<td id="previous" class="none">
													<a id="previous">
														<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
														<xsl:attribute name="onclick">previousPage(findCorrespondingNextPageHref(this));</xsl:attribute>
															Previous
														</a>
												</td>
												<td class="none"> | </td>
												<td id="next">
													<xsl:if test="count(./record) &lt;= @page-size">
														<xsl:attribute name="class">none</xsl:attribute>
													</xsl:if>
													<a id="next">
														<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
														<xsl:attribute name="onclick">nextPage(findCorrespondingNextPageHref(this));</xsl:attribute>
															Next
														</a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</thead>
						</xsl:if>
						<thead class="nestedElementsTypeFootHeader">
							<xsl:for-each select="header">
								<xsl:if test="( ../@showYesNoRadioButtons[. = 'true'] ) and ( ../@yesNoLabel ) ">
									<tr class="Shaded">
										<td colspan="10">
											<xsl:value-of select="../@yesNoLabel"/>
										</td>
									</tr>
								</xsl:if>
								<tr class="Shaded">
									<xsl:if test="@showRadioButtons[. = 'true']">
										<td/>
									</xsl:if>
									<xsl:if test="../@showYesNoRadioButtons[. = 'true']">
										<td>Yes No</td>
									</xsl:if>
									<xsl:for-each select="data">
										<!--	<td class="ColumnHeader">-->
										<td class="boldTenBlack">
											<xsl:if test="@colspan">
												<xsl:attribute name="colspan"><xsl:value-of select="normalize-space(@colspan)"/></xsl:attribute>
											</xsl:if>
											<xsl:choose>
												<xsl:when test="@link">
													<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
													<a class="SortColumnHeader">
														<xsl:if test="@sortonload[. = 'true']">
															<xsl:attribute name="id"><xsl:value-of select="generate-id(../..)"/></xsl:attribute>
														</xsl:if>
														<xsl:if test="@sortorder">
															<xsl:attribute name="sortOrder"><xsl:value-of select="@sortorder"/></xsl:attribute>
														</xsl:if>
														<!--	<xsl:attribute name="href"><xsl:value-of select="normalize-space(@link)"/></xsl:attribute>-->
														<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
														<xsl:attribute name="onClick">sortTableOnColumn(this);</xsl:attribute>
														<!--	<xsl:value-of select="normalize-space(.)"/>-->
														<b>
															<xsl:value-of select="."/>
														</b>
													</a>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="."/>
												</xsl:otherwise>
											</xsl:choose>
										</td>
									</xsl:for-each>
								</tr>
							</xsl:for-each>
							<!--		</tfoot>-->
							<!-- display message if no records returned -->
							<xsl:if test="count(./record) &lt; 1">
								<tr align="center" class="NotShaded">
									<td colspan="10">
										<p>
											<center>
												<b>No Records returned</b>
											</center>
										</p>
									</td>
								</tr>
							</xsl:if>
							<!--			<tfoot>-->
							<!-- initial -->
							<tfoot>
								<xsl:for-each select="record">
									<xsl:choose>
										<xsl:when test="string-length(value)!=0">
											<xsl:call-template name="enhanced-data-table-row-creator">
												<xsl:with-param name="editLink" select="edit-link"/>
												<xsl:with-param name="viewLink" select="view-link"/>
												<xsl:with-param name="page-size" select="../@page-size"/>
												<!--		<xsl:with-param name="lineCounter" select="position()-1"/>-->
												<!--		<xsl:with-param name="lineCounter">
												<xsl:value-of select="$lineCounter + 1"/>
												</xsl:with-param>-->
												<xsl:with-param name="string" select="value"/>
											</xsl:call-template>
											<!--	</xsl:if>-->
										</xsl:when>
										<xsl:otherwise>
											<xsl:call-template name="enhanced-data-table-row-creator">
												<xsl:with-param name="editLink" select="edit-link"/>
												<xsl:with-param name="viewLink" select="view-link"/>
												<xsl:with-param name="page-size" select="../@page-size"/>
												<xsl:with-param name="string" select="value"/>
												<xsl:with-param name="lineCounter" select="position()-1"/>
												<!--		<xsl:with-param name="lineCounter">
													<xsl:value-of select="$lineCounter + 1"/>
												</xsl:with-param>-->
											</xsl:call-template>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</tfoot>
						</thead>
						<tbody/>
					</table>
					<table cellpadding="3" cellspacing="0" border="0" width="100%" class="TableInner">
						<thead>
							<xsl:if test="@nextHref and @prevHref and @currentIndex and @totalRecords and @recordsPerPage">
								<xsl:attribute name="pageSize"><xsl:value-of select="@page-size"/></xsl:attribute>
								<tr>
									<td colspan="100%" align="right" valign="bottom">
										<table border="0">
											<tr>
												<td class="none">
													<a id="previous">
														<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
														<xsl:attribute name="onclick">previousPage(findCorrespondingNextPageHref(this));</xsl:attribute>
                                                                                                                        Previous
                                                                                                                </a>
												</td>
												<td class="none"> | </td>
												<td>
													<xsl:if test="count(./record) &lt;= @page-size">
														<xsl:attribute name="class">none</xsl:attribute>
													</xsl:if>
													<a id="next">
														<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
														<xsl:attribute name="onclick">nextPage(findCorrespondingNextPageHref(this));</xsl:attribute>
															Next

                                                                                                                </a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</xsl:if>
						</thead>
					</table>
				</td>
			</tr>
		</table>
		<SCRIPT Type="text/javascript" Language="JavaScript">
							if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
							  sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
							}</SCRIPT>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td colspan="10" class="visible">
					<xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute>
				</td>
			</tr>
		</table>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		VL: Part of create-enhanced-data-table template.  Creates actuall TR tag and everything inside of it.	
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="enhanced-data-table-row-creator">
		<xsl:param name="editLink"/>
		<xsl:param name="viewLink"/>
		<xsl:param name="lineCounter">0</xsl:param>
		<xsl:param name="page-size"/>
		<xsl:param name="string"/>
		<tr id="sortParent">
			<xsl:attribute name="rowNumber"><xsl:value-of select="$lineCounter"/></xsl:attribute>
			<xsl:attribute name="page"><xsl:value-of select="floor($lineCounter div $page-size) + 1"/></xsl:attribute>
			<!--		<xsl:attribute name="page"><xsl:value-of select="floor($lineCounter div 5) + 1"/></xsl:attribute>-->
			<!--		<xsl:attribute name="rowNumber"><xsl:value-of select="$position"/></xsl:attribute>-->
			<xsl:choose>
				<xsl:when test="string-length($page-size)>0 and number($lineCounter) > (number($page-size)-1)">
					<xsl:attribute name="class">none</xsl:attribute>
				</xsl:when>
				<!-- VL code to alternate color of data lines -->
				<xsl:when test=" position() mod 2">
					<xsl:attribute name="class">NotShaded</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="class">Shaded</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="../@showYesNoRadioButtons[. = 'true']">
				<td valign="middle">
					<xsl:if test="recordComments">
						<xsl:attribute name="rowspan">2</xsl:attribute>
					</xsl:if>
					<input type="radio">
						<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
						<xsl:attribute name="value">Y</xsl:attribute>
					</input>
					<input type="radio">
						<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
						<xsl:attribute name="value">N</xsl:attribute>
						<xsl:attribute name="checked">1</xsl:attribute>
					</input>
				</td>
			</xsl:if>
			<xsl:if test="../@showRadioButtons[. = 'true']">
				<td valign="middle">
					<xsl:if test="recordComments">
						<xsl:attribute name="rowspan">2</xsl:attribute>
					</xsl:if>
					<input type="radio">
						<xsl:attribute name="name"><xsl:value-of select="../@name"/></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="position()"/></xsl:attribute>
					</input>
				</td>
			</xsl:if>
			<xsl:for-each select="data">
				<td>
					<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
					<xsl:choose>
						<xsl:when test="element">
							<xsl:for-each select="element">
								<xsl:call-template name="create-element"/>
							</xsl:for-each>
						</xsl:when>
						<xsl:when test="@link">
							<a>
								<xsl:attribute name="href"><xsl:value-of select="normalize-space(@link)"/></xsl:attribute>
								<xsl:value-of select="."/>
							</a>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="."/>
						</xsl:otherwise>
					</xsl:choose>
				&nbsp;</td>
			</xsl:for-each>
		</tr>
		<xsl:if test="recordComments">
			<tr>
				<xsl:choose>
					<!-- VL code to alternate color of data lines -->
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="class">NotShaded</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">Shaded</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<td colspan="10">
					<xsl:attribute name="name"><xsl:value-of select="recordComments/@name"/></xsl:attribute>
					<xsl:if test="recordComments/@label">
						<b>
							<xsl:value-of select="recordComments/@label"/>:</b>&nbsp;<xsl:value-of select="normalize-space(recordComments)"/>
					</xsl:if>
				</td>
			</tr>
		</xsl:if>
		<!--		<tr>
			<td>-->
		<!--some spaceWWWW-->
		<!--&nbsp;</td>
		</tr>-->
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		display values for multiple entry select boxes	
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="multiple-entry-selectbox-display">
		<xsl:param name="values"/>
		<xsl:param name="srts"/>
		<xsl:choose>
			<xsl:when test="contains($values, '|') ">
				<xsl:variable name="desc">
					<xsl:call-template name="srt-codes">
						<xsl:with-param name="delimiter" select="'|'"/>
						<xsl:with-param name="string" select="normalize-space($srts)"/>
						<xsl:with-param name="value" select="normalize-space(substring-before($values, '|'))"/>
					</xsl:call-template>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="normalize-space($desc)='' "/>
					<xsl:otherwise>
						<xsl:value-of select="$desc"/>, </xsl:otherwise>
				</xsl:choose>
				<xsl:call-template name="multiple-entry-selectbox-display">
					<xsl:with-param name="values" select="normalize-space(substring-after($values, '|'))"/>
					<xsl:with-param name="srts" select="normalize-space($srts)"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for creating the label
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="create-the-label">
		<xsl:if test="@label and not(@hide) or @note">
			<xsl:if test="@childalign='yes'">
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</xsl:if>
			<td valign="top">
				<xsl:choose>
					<xsl:when test="ancestor::topgroup"/>
					<xsl:when test="string-length(normalize-space(@label))&lt;40">
						<xsl:attribute name="width">20%</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="width">50%</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:attribute name="align">right</xsl:attribute>
				<xsl:if test="@align-label">
					<xsl:attribute name="align"><xsl:value-of select="@align-label"/></xsl:attribute>
				</xsl:if>
				<!--xsl:choose>
						<xsl:when test="(@type='select' and @size) or @type='textarea'">	
							<xsl:attribute name="valign">top</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="valign">middle</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose-->
				<xsl:choose>
					<xsl:when test="@indented='yes'">
						<table cellpadding="1" cellspacing="0" border="0">
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td>
									<xsl:choose>
										<xsl:when test="string-length(normalize-space(@label))&lt;40">
											<nobr>
												<!--xsl:choose>
														<xsl:when test="contains(@label,'(' ) and contains( substring(normalize-space(@label),string-length(normalize-space(@label))), ')')">
															<xsl:call-template name="format-label-parenthesis">
																<xsl:with-param name="text" select="@label"/>
															</xsl:call-template>
													
														</xsl:when>	
														<xsl:otherwise-->
												<xsl:call-template name="label">
													<xsl:with-param name="text" select="@label"/>
													<xsl:with-param name="subtext" select="@subText"/>
													<xsl:with-param name="italicString" select="@makeItalic"/>
												</xsl:call-template>
												<!--/xsl:otherwise>
													</xsl:choose-->
											</nobr>
										</xsl:when>
										<xsl:otherwise>
											<!--xsl:choose>
														<xsl:when test="contains(@label,'(' ) and contains( substring(normalize-space(@label),string-length(normalize-space(@label))), ')')">
															<xsl:call-template name="format-label-parenthesis">
																<xsl:with-param name="text" select="@label"/>
															</xsl:call-template>
													
														</xsl:when>	
														<xsl:otherwise-->
											<xsl:call-template name="label">
												<xsl:with-param name="text" select="@label"/>
												<xsl:with-param name="subtext" select="@subText"/>
												<xsl:with-param name="italicString" select="@makeItalic"/>
											</xsl:call-template>
											<!--/xsl:otherwise>
													</xsl:choose-->
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</table>
					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
							<xsl:when test="string-length(normalize-space(@label))&lt;40">
								<nobr>
									<!--xsl:choose>
														<xsl:when test="contains(@label,'(' ) and contains( substring(normalize-space(@label),string-length(normalize-space(@label))), ')')">
															<xsl:call-template name="format-label-parenthesis">
																<xsl:with-param name="text" select="label"/>
															</xsl:call-template>
													
														</xsl:when>	
														<xsl:otherwise-->
									<xsl:call-template name="label">
										<xsl:with-param name="text" select="@label"/>
										<xsl:with-param name="subtext" select="@subText"/>
										<xsl:with-param name="italicString" select="@makeItalic"/>
									</xsl:call-template>
									<!--/xsl:otherwise>
													</xsl:choose-->
								</nobr>
							</xsl:when>
							<xsl:otherwise>
								<!--xsl:choose>
														<xsl:when test="contains(@label,'(' ) and contains( substring(normalize-space(@label),string-length(normalize-space(@label))), ')')">
															<xsl:call-template name="format-label-parenthesis">
																<xsl:with-param name="text" select="@label"/>
															</xsl:call-template>
													
														</xsl:when>	
														<xsl:otherwise-->
								<xsl:call-template name="label">
									<xsl:with-param name="text" select="@label"/>
									<xsl:with-param name="subtext" select="@subText"/>
									<xsl:with-param name="italicString" select="@makeItalic"/>
								</xsl:call-template>
								<!--font>
																<xsl:choose>
																	<xsl:when test="contains(normalize-space(@label), '*')"><xsl:attribute name="class">boldTenRed</xsl:attribute></xsl:when>
																	<xsl:otherwise><xsl:attribute name="class">boldTenBlack</xsl:attribute></xsl:otherwise>
																</xsl:choose>

																<xsl:value-of select="@label" disable-output-escaping="yes"/>
																<xsl:if test="string-length(normalize-space(@label))!=0 and not(contains(normalize-space(@label), '?'))  and not(contains(normalize-space(@subText), '?'))">:&nbsp;</xsl:if>
															</font-->
								<!--/xsl:otherwise>
													</xsl:choose-->
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</xsl:if>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for generating the label
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="label">
		<xsl:param name="text"/>
		<xsl:param name="subtext"/>
		<xsl:param name="italicString"/>
		<xsl:variable name="options">
			<xsl:if test="contains(substring($text,1,1),'[')">
				<xsl:value-of select="substring-before($text,']')"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="label">
			<xsl:choose>
				<xsl:when test="contains(substring($text,1,1),'[')">
					<xsl:value-of select="substring-after($text,']')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$text"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<span>
			<xsl:choose>
				<xsl:when test="contains(normalize-space($options), '-r')">
					<xsl:attribute name="class">boldTenRed</xsl:attribute>
				</xsl:when>
				<xsl:when test="contains(normalize-space($label), '*')">
					<xsl:attribute name="class">boldTenRed</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="class">boldTenBlack</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="string-length(normalize-space($italicString))!=0">
					<xsl:value-of select="substring-before($label,$italicString)"/>
					<i>
						<xsl:value-of select="$italicString"/>
					</i>
					<xsl:value-of select="substring-after($label,$italicString)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$label"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="string-length(normalize-space($label))!=0 and not(contains(normalize-space($label), '?'))  and not(contains(normalize-space($subtext), '?')) and not(contains($options,'-nc'))  and not(contains(normalize-space($label), 'Hispanic or Latino'))  and not(contains(normalize-space($label), 'Not Hispanic or Latino')) ">:</xsl:if>
		</span>
		<xsl:if test="string-length(normalize-space($subtext))!=0 ">
			<xsl:variable name="cleanedSubText">
				<xsl:choose>
					<xsl:when test="contains(substring($subtext,1,1),'[') and not(contains(substring($subtext,string-length($subtext),1),']'))">
						<xsl:value-of select="substring-after($subtext,']')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$subtext"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<br/>
			<span>
				<xsl:choose>
					<xsl:when test="contains(normalize-space($subtext), '-nb')"/>
					<xsl:otherwise>
						<xsl:attribute name="class">boldTenBlack</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:value-of select="$cleanedSubText"/>
			</span>
		</xsl:if>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for formating labels with parenthesis
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="format-label-parenthesis">
		<xsl:param name="text"/>
		<b>
			<xsl:value-of select="substring-before($text,'(' )" disable-output-escaping="yes"/>
		</b>
		<br/>
		<font class="normalEightBlack">(<xsl:value-of select="substring-after($text,'(' )" disable-output-escaping="yes"/>
		</font>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2007. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->