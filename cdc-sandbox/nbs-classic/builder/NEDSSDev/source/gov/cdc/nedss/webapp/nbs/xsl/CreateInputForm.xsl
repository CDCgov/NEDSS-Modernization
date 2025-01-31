<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!--<xsl:preserve-space elements="*"/>-->
	<!--xsl:strip-space elements="*"/-->
	<xsl:param name="mode"/>
	<xsl:template match="content">
		<content>
			<xsl:if test="./@form">
				<xsl:attribute name="form"><xsl:value-of select="normalize-space(./@form)"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="./@title">
				<xsl:attribute name="title"><xsl:value-of select="normalize-space(./@title)"/></xsl:attribute>
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
			<xsl:for-each select="link">
				<xsl:element name="link">
					<xsl:attribute name="name"><xsl:value-of select="./@name"/></xsl:attribute>
					<xsl:if test="@tie-to">
						<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
					</xsl:if>
					<xsl:value-of select="normalize-space(.)"/>
				</xsl:element>
			</xsl:for-each>
		</link-bar>
	</xsl:template>
	<xsl:template match="button-bar">
		<button-bar>
			<xsl:attribute name="name"><xsl:value-of select="normalize-space(../tab/@name)"/></xsl:attribute>
			<xsl:if test="$mode = 'preview' ">
				<xsl:element name="right">
					<xsl:element name="label">Back</xsl:element>
					<xsl:element name="javascript-action">history.go(-1);</xsl:element>
					<!--			<xsl:element name="javascript-action">window.location = "/nbs/ldf/ldf-url-map";</xsl:element>-->
					<!--			<xsl:element name="javascript-action">window.location = "/nbs/ldf/ldf-admin";</xsl:element>-->
				</xsl:element>
			</xsl:if>
			<xsl:for-each select="right|left">
				<xsl:choose>
					<xsl:when test="$mode = 'preview' "/>
					<xsl:when test="contains(javascript-action, 'NOT_DISPLAYED')"/>
					<xsl:when test="contains(javascript-action, 'null')"/>
					<xsl:when test="name()='right' ">
						<xsl:element name="right">
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
		</button-bar>
	</xsl:template>
	<xsl:template match="javascript-files">
		<javascript-files>
			<xsl:for-each select="import">
				<xsl:element name="import">
					<xsl:value-of select="."/>
				</xsl:element>
			</xsl:for-each>
			<xsl:for-each select="insert">
				<xsl:element name="insert">
					<xsl:value-of select="."/>
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
			<xsl:attribute name="align"><xsl:value-of select="@align"/></xsl:attribute>
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
			<!--		initialize gXSLType global javascript variable 		-->
			<script type="text/javascript" language="JavaScript">gXSLType='input';</script>
			<xsl:variable name="jumper">
				<xsl:if test="count(group/@name) > 1 and not(@jumpers='off')">true</xsl:if>
			</xsl:variable>
			<xsl:for-each select="script">
				<script type="text/javascript">
					<xsl:value-of select="text()" disable-output-escaping="yes"/>
				</script>
			</xsl:for-each>
			
			<xsl:if test="normalize-space($jumper)='true'">
				<table id="jumper-table" border="0" width="100%">
					<thead align="center">
						<tr align="center">
							<td align="left">
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
			<table cellpadding="1" cellspacing="0" border="0" bgcolor="white" width="100%" id="1">
				<tbody>
					<xsl:for-each select="./group">
						<xsl:choose>
							<xsl:when test="@authorized='false'"/>
							<xsl:otherwise>
								<tr>
									<xsl:if test="@tie-to">
										<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
									</xsl:if>
									<td colspan="10">
										<table cellpadding="3" cellspacing="0" border="0" width="100%">
											<xsl:attribute name="id"><xsl:value-of select="@label"/></xsl:attribute>
											<xsl:choose>
												<xsl:when test="@name">
													<tr bgcolor="#003470">
														<td align="left">
															<a name="{generate-id(.)}">
																<!--xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute-->
																<font class="boldTwelveYellow">
																	&nbsp;<xsl:value-of select="@name"/>
																</font>
															</a>
														</td>
														<xsl:choose>
															<xsl:when test="normalize-space($jumper)='true'">
																<td align="right" colspan="9">
																	<a href="#top" class="boldTenWhite">Back to Top</a>&nbsp;</td>
															</xsl:when>
															<xsl:otherwise>
																<td colspan="9">&nbsp;</td>
															</xsl:otherwise>
														</xsl:choose>
													</tr>
													<!--		PUT BUTTONS IF THEY EXISTS	-->
													<tr>
														<td colspan="10" align="right">
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
														<td class="boldElevenBlack" colspan="10">
															<center>
																<xsl:value-of select="@label"/>
															</center>
														</td>
													</tr>
												</xsl:when>
												<xsl:when test="@blue-label">
													<tr class="LightBlue">
														<td class="boldElevenBlack" colspan="10">
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
		<xsl:param name="batchId"/>
		<xsl:param name="condition"/>
		<xsl:for-each select="./line">
			<xsl:choose>
				<xsl:when test=" ./element/@type[. = 'conditional-entry']">
					<xsl:variable name="trigger" select="@trigger"/>
					<!-- for type ahead get reference to the next parent for the closed scenario-->
					<xsl:variable name="focus-candidate-close" select="following-sibling::line/element/controller[@type]/@name | following-sibling::line/element[string-length(normalize-space(@name))>0][not(./controller)][@type]/@name"/>
					<xsl:for-each select="element">
						<xsl:call-template name="create-element">
							<xsl:with-param name="parentId" select="$parentId"/>
							<xsl:with-param name="batchId" select="$batchId"/>
							<xsl:with-param name="trigger" select="$trigger"/>
							<xsl:with-param name="condition" select="$condition"/>
							<xsl:with-param name="line-trigger" select="@trigger"/>
							<xsl:with-param name="abc" select="@abc"/>
							<!--xsl:with-param name="Id" select="../@id"/-->
							<xsl:with-param name="lineId" select="../@id"/>
							<xsl:with-param name="focus-candidate-close" select="$focus-candidate-close"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="@id[. = 'csfhidden']">
					<tr rowID="csfhiddenrow" class="none">
						<xsl:call-template name="setup-cell">
							<xsl:with-param name="disabled" select="$disabled"/>
							<xsl:with-param name="onchange" select="$onchange"/>
							<xsl:with-param name="parentId" select="$parentId"/>
							<xsl:with-param name="batchId" select="$batchId"/>
						</xsl:call-template>
					</tr>
				</xsl:when>
				<xsl:otherwise>
					<tr rowID="{generate-id()}">
						<xsl:if test="@tie-to">
							<xsl:attribute name="tie-to"><xsl:value-of select="@tie-to"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="$condition and @trigger">
							<xsl:variable name="test">
								<xsl:value-of select="@trigger"/>|
							</xsl:variable>
							<xsl:choose>
								<!--for listbox-->
								<xsl:when test="contains(normalize-space($condition),'|' ) and contains(normalize-space($condition), normalize-space($test))">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<!--for OR condition in the line trigger-->
								<xsl:when test=" contains(normalize-space(@trigger),'|' ) and contains(normalize-space(@trigger), normalize-space($condition)  ) and not(string-length(normalize-space($condition))=0)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<!--for single select condition in the line trigger-->
								<xsl:when test="normalize-space($condition)=normalize-space(@trigger)">
									<xsl:attribute name="class">visible</xsl:attribute>
								</xsl:when>
								<!--default condition is none-->
								<xsl:otherwise>
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<!--for abcs questions-->
						<xsl:if test="@abc">
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
						</xsl:if>
						<!--	need this for conditional entry based on showing individual lines based on controller value	-->
						<xsl:if test="@trigger">
							<xsl:attribute name="trigger"><xsl:value-of select="@trigger"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@test">
							<xsl:attribute name="test"><xsl:value-of select="@test"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@id">
							<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@indented='yes' ">
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</xsl:if>
						<!-- VL: rowID for access from JavaScript -->
						<!--xsl:attribute name="rowID"><xsl:choose><xsl:when test="string-length($batchId)>0"><xsl:value-of select="number(position())+2000"/></xsl:when><xsl:when test="ancestor::element/@type='entity-search' "><xsl:value-of select="number(position())+1000"/></xsl:when><xsl:otherwise><xsl:value-of select="position()"/></xsl:otherwise></xsl:choose></xsl:attribute-->
						<xsl:call-template name="setup-cell">
							<xsl:with-param name="disabled" select="$disabled"/>
							<xsl:with-param name="onchange" select="$onchange"/>
							<xsl:with-param name="parentId" select="$parentId"/>
							<xsl:with-param name="batchId" select="$batchId"/>
						</xsl:call-template>
					</tr>
					<!-- embeds the error handling with the element-->
					<tr id="error-message-tr">
						<td colspan="10" class="visible" rowID="{generate-id()}">
							<xsl:if test="ancestor::group/@maxcolspan">
								<xsl:attribute name="colspan"><xsl:value-of select="ancestor::group/@maxcolspan"/></xsl:attribute>
							</xsl:if>
							<!--xsl:attribute name="rowID"><xsl:choose><xsl:when test="string-length($batchId)>0"><xsl:value-of select="number(position())+2000"/></xsl:when><xsl:when test="ancestor::element/@type='entity-search' "><xsl:value-of select="number(position())+1000"/></xsl:when><xsl:otherwise><xsl:value-of select="position()"/></xsl:otherwise></xsl:choose></xsl:attribute-->
						</td>
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
		<xsl:param name="batchId"/>
		<xsl:for-each select="./element">
			<xsl:if test="@name='dateAssignedToInvestigation' and string-length(normalize-space(preceding::element[@name='investigator.personUid']/value))=0">
				<xsl:attribute name="class">none</xsl:attribute>
			</xsl:if>
			<xsl:if test="@name='dateAssignedToInvestigation' ">
				<xsl:attribute name="id"><xsl:value-of select="@name"/>-tdCell</xsl:attribute>
			</xsl:if>
			<!-- do authorization check	-->
			<xsl:choose>
				<xsl:when test="@authorized='false'  and not(@type='data-table')"/>
				<xsl:otherwise>
					<!--	template for creating the label	-->
					<xsl:call-template name="create-the-label"/>
					<td valign="middle">
						<xsl:attribute name="align">left</xsl:attribute>
						<xsl:attribute name="valign">middle</xsl:attribute>
						<xsl:if test="position() = last() and not(ancestor::group/@colspan='off')">
							<!--		colspan 100% acts funky	-->
							<xsl:attribute name="colspan">10</xsl:attribute>
						</xsl:if>
						<xsl:if test="@type[. = 'textarea']  and not(ancestor::group/@colspan='off')">
							<xsl:attribute name="colspan">5</xsl:attribute>
						</xsl:if>
						<xsl:if test="position() = last() and @colspan">
							<xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute>
						</xsl:if>
						<!--		need to move the label to the top when there is a mask	-->
						<!--		allow control of background color of cell through attribute	-->
						<xsl:if test="@background">
							<xsl:attribute name="class"><xsl:value-of select="@background"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@align">
							<xsl:attribute name="align"><xsl:value-of select="@align"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@type[. = 'entity-search'] or @type[. = 'line-separator'] or @type[. = 'group-separator']  or @type[. = 'blue-bar'] or  @type[. = 'batch-entry'] or @type[. = 'join'] or (@type[. = 'raw'] and count(../element) = 1)">
							<xsl:attribute name="colspan">100%</xsl:attribute>
							<xsl:attribute name="width">100%</xsl:attribute>
						</xsl:if>
						<table border="0" cellpadding="0" cellspacing="0">
							<xsl:if test="@type[. = 'entity-search'] or @type[. = 'line-separator'] or @type[. = 'group-separator'] or  @type[. = 'blue-bar'] or @type[. = 'batch-entry'] or @type[. = 'join'] or @type[. = 'data-table'] ">
								<xsl:attribute name="width">100%</xsl:attribute>
							</xsl:if>
							<xsl:if test="(validation/@type='required' or validation/required)  and string-length($batchId)>0 and @type='text' ">
								<tr>
									<th colspan="2" class="normalNineBlack">
										<i>(Required for Add/Update <xsl:value-of select="$batchId"/>)</i>
									</th>
								</tr>
							</xsl:if>
							<tr>
								<xsl:call-template name="create-element">
									<xsl:with-param name="disabled" select="$disabled"/>
									<xsl:with-param name="onchange" select="$onchange"/>
									<xsl:with-param name="onclick" select="$onclick"/>
									<xsl:with-param name="parentId" select="$parentId"/>
									<xsl:with-param name="batchId" select="$batchId"/>
								</xsl:call-template>
							</tr>
						</table>
					</td>
				</xsl:otherwise>
			</xsl:choose>
			<!--		this sets up the label cell		-->
		</xsl:for-each>
	</xsl:template>
	<!-- =================================================================== -->
	<!--							create the element		                                  -->
	<!-- =================================================================== -->
	<xsl:template name="create-element">
		<xsl:param name="hide"/>
		<xsl:param name="disabled"/>
		<xsl:param name="onchange"/>
		<xsl:param name="onclick"/>
		<xsl:param name="parentId"/>
		<xsl:param name="batchId"/>
		<xsl:param name="trigger"/>
		<xsl:param name="line-trigger"/>
		<xsl:param name="condition"/>
		<xsl:param name="abc"/>
		<xsl:param name="partner"/>
		<!--xsl:param name="id"/-->
		<xsl:param name="lineId"/>
		<xsl:param name="name"/>
		<xsl:param name="value"/>
		<!--conditional entry params-->
		<xsl:param name="ce-td-id"/>
		<xsl:param name="ce-td-eval"/>
		<xsl:param name="ce-td-colspan"/>
		<xsl:param name="focus-candidate"/>
		<xsl:param name="focus-candidate-close"/>
		<xsl:variable name="id">
			<xsl:choose>
				<xsl:when test="@id">
					<xsl:value-of select="normalize-space(@id)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="normalize-space(@name)"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="length">
			<xsl:value-of select="normalize-space(@length)"/>
		</xsl:variable>
		<!--		notification 	-->
		<xsl:if test="@notification='true'">
			<span class="normalNineBlack" align="left">
				<i>(Required for Notification)</i>
			</span>
			<br/>
		</xsl:if>
		<xsl:if test="@notification='trueIfABCs'">
			<span class="normalNineBlack" align="left">
				<i>(Required for Notification if ABCs case)</i>
			</span>
			<br/>
		</xsl:if>
		<!-- this is added spcific for CRS -->
		<xsl:if test="@notification='false'">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span class="normalNineBlack" align="right">
				<i>(Required for Notification)</i>
			</span>
			<br/>
		</xsl:if>
		<xsl:if test="@count='true'">
			<span class="normalNineBlack" align="left">
				<i>(Required for Add Count)</i>
			</span>
			<br/>
		</xsl:if>
		<td>
			<xsl:if test="string-length($ce-td-id)>0">
				<xsl:attribute name="id"><xsl:value-of select="normalize-space($ce-td-id)"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="string-length($ce-td-eval)>0">
				<xsl:attribute name="eval"><xsl:value-of select="normalize-space($ce-td-eval)"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="string-length($ce-td-colspan)>0">
				<xsl:attribute name="colspan"><xsl:value-of select="$ce-td-colspan"/></xsl:attribute>
			</xsl:if>
			<xsl:choose>
				<!-- =================================================================== -->
				<!-- 			Hyper link                  -->
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
				<!-- ========================================================== -->
				<!--	(LabReport Specific) Code for element type error-msg	-->
				<!-- ========================================================== -->
				<xsl:when test="@type[. = 'error-msg']">
					<span>
						<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<xsl:attribute name="class">none</xsl:attribute>
						<b>
							<font color="red">
								<xsl:value-of select="normalize-space(value)"/>
							</font>
						</b>
					</span>
				</xsl:when>
				<!--	formatting type , places a blue bar on top and indents everything beneath	-->
				<xsl:when test="@type[. = 'blue-bar']">
					<!--table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td width="100%" class="LightBlue" align="left">
								<b>
									<xsl:value-of select="@text" disable-output-escaping="yes"/>
								</b>
							</td>
						</tr>
					</table-->
					<table cellpadding="3" cellspacing="0" border="0" width="100%">
						<tr class="LightBlue">
							<td class="boldElevenBlack">
								<xsl:choose>
									<xsl:when test="string-length(normalize-space(@makeItalic))!=0">
										<xsl:value-of select="substring-before(@light-blue-label,@makeItalic)"/>
										<i>
											<xsl:value-of select="@makeItalic"/>
										</i>
										<xsl:value-of select="substring-after(@light-blue-label,@makeItalic)"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="@text"/>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:if test="@subtext">
									<br/>
									<xsl:value-of select="@subtext"/>
								</xsl:if>
							</td>
						</tr>
					</table>
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
					<span>
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:copy-of select="span"/>
					</span>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates password text field form element                               -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'password']">
					<!--		creates notation for elements		-->
					<xsl:if test="@note">
						<xsl:value-of select="normalize-space(@note)"/>
						<br/>
					</xsl:if>
					<xsl:element name="input">
						<xsl:attribute name="type">password</xsl:attribute>
						<xsl:attribute name="onkeypress">SubmitOnEnter();</xsl:attribute>
						<!--<xsl:attribute name="class">textbox</xsl:attribute>-->
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<xsl:attribute name="size"><xsl:value-of select="normalize-space(@size)"/></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/><xsl:text disable-output-escaping="yes"/></xsl:attribute>
						<xsl:choose>
							<xsl:when test="@maxlength">
								<xsl:attribute name="maxlength"><xsl:value-of select="normalize-space(@maxlength)"/></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="maxlength">50</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates regular input text field form element                               -->
				<!-- =================================================================== -->
				
				<xsl:when test="@type[. = 'inputFile']">
				<div style="display:" >
					<b style="margin-left: 56px" parent="Attachment">Choose File: </b><input type = "file" onchange="setFocusOnNameField()">

					<xsl:attribute name="name"><xsl:value-of select="name"/></xsl:attribute>
					<xsl:attribute name="id"><xsl:value-of select="id"/></xsl:attribute>
					<xsl:attribute name="parent"><xsl:value-of select="parent"/></xsl:attribute>
					<xsl:attribute name="fieldLabel"><xsl:value-of select="fieldLabel"/></xsl:attribute>
					<xsl:attribute name="validate"><xsl:value-of select="validate"/></xsl:attribute>
					<xsl:attribute name="isnested"><xsl:value-of select="isnested"/></xsl:attribute>
					<xsl:attribute name="onload"><xsl:value-of select="onload"/></xsl:attribute>
					<xsl:attribute name="errorCode"><xsl:value-of select="errorCode"/></xsl:attribute>
					<xsl:attribute name="size"><xsl:value-of select="size"/></xsl:attribute>
					<xsl:attribute name="onchange"><xsl:value-of select="onchange"/></xsl:attribute>

					</input>
				</div>
				</xsl:when>
				<xsl:when test="@type[. = 'inputFileHidden']">
				
				<div style="display:none">
					<b style="margin-left: 56px" parent="Attachment">Choose File: </b><input type = "file">

					<xsl:attribute name="name"><xsl:value-of select="name"/></xsl:attribute>
					<xsl:attribute name="id"><xsl:value-of select="id"/></xsl:attribute>
					<xsl:attribute name="parent"><xsl:value-of select="parent"/></xsl:attribute>
					<xsl:attribute name="fieldLabel"><xsl:value-of select="fieldLabel"/></xsl:attribute>
					<xsl:attribute name="isnested"><xsl:value-of select="isnested"/></xsl:attribute>
					<xsl:attribute name="onload"><xsl:value-of select="onload"/></xsl:attribute>
					<xsl:attribute name="errorCode"><xsl:value-of select="errorCode"/></xsl:attribute>
					<xsl:attribute name="size"><xsl:value-of select="size"/></xsl:attribute>
					<xsl:attribute name="onchange"><xsl:value-of select="onchange"/></xsl:attribute>

					</input>
				</div>
				</xsl:when>
				
				
				
				<xsl:when test="@type[. = 'text']">
					<xsl:choose>
						<!--xsl:when test="./readonly or ancestor::element/readonly or ancestor::element/@type='entity-search' or ancestor::controller/@type='entity-search' "-->
						<xsl:when test="./readonly or ancestor::element/readonly ">
							<span>
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="type">text</xsl:attribute>
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
									&nbsp;

							<xsl:choose>
									<xsl:when test="normalize-space(value)='null' or string-length(normalize-space(value))=0">&nbsp;</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="normalize-space(value)"/>
									</xsl:otherwise>
								</xsl:choose>
							</span>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="input">
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="type">TEXT</xsl:attribute>
								<xsl:choose>
									<xsl:when test="string-length($batchId)>0">
										<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
										<xsl:attribute name="isNested">yes</xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="onkeydown">if (SubmitOnEnter()==false) return false;</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
								<!--<xsl:attribute name="class">textbox</xsl:attribute>-->
								<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:attribute name="size"><xsl:value-of select="normalize-space(@size)"/></xsl:attribute>
								<xsl:choose>
									<xsl:when test="@maxlength">
										<xsl:attribute name="maxlength"><xsl:value-of select="normalize-space(@maxlength)"/></xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="maxlength">50</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:if test="@makeHistoryBoxEditable[. = 'true']">
									<xsl:attribute name="makeHistoryBoxEditable"/>
								</xsl:if>
								<!-- VL:  support for displaying text input box disabled   -->
								<xsl:if test="@disabled[. = 'true'] or $disabled= 'true'">
									<xsl:attribute name="disabled"/>
								</xsl:if>
								<xsl:if test="@hide[. = 'true']">
									<xsl:attribute name="class">none</xsl:attribute>
								</xsl:if>
								<xsl:if test="@mask">
                              <xsl:attribute name="onkeyup">DateMask(this,null,event)</xsl:attribute>
									<xsl:attribute name="maxlength"><xsl:value-of select="string-length(normalize-space(@mask))"/></xsl:attribute>
									<xsl:attribute name="size"><xsl:value-of select="string-length(normalize-space(@mask))"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="@maskF">
                              <xsl:attribute name="onkeyup">DateMaskFuture(this,null,event)</xsl:attribute>
									<xsl:attribute name="maxlength"><xsl:value-of select="string-length(normalize-space(@maskF))"/></xsl:attribute>
									<xsl:attribute name="size"><xsl:value-of select="string-length(normalize-space(@maskF))"/></xsl:attribute>
								</xsl:if>

								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
									<xsl:if test="header/@counter">
										<xsl:attribute name="counter"><xsl:value-of select="normalize-space(header/@counter)"/></xsl:attribute>
									</xsl:if>
								</xsl:if>
								<xsl:if test="$onchange">
									<xsl:attribute name="onchange"><xsl:value-of select="$onchange"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="@onblur">
									<xsl:attribute name="onblur"><xsl:value-of select="normalize-space(@onblur)"/></xsl:attribute>
								</xsl:if>	
								<xsl:if test="onchange">
									<xsl:if test="onchange/type[. = 'enableOnOtherOption']">
										<xsl:attribute name="onchange">selectBoxToggleOfInputBox( this.id,'<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/nameRef"/>')</xsl:attribute>
									</xsl:if>
									<xsl:if test="onchange/type[. = 'autoCalculateDOB']">
										<xsl:attribute name="onchange">doAutoCalculateDOB(<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/nameRef[1]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/nameRef[2]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/nameRef[3]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/nameRef[4]"/> );</xsl:attribute>
									</xsl:if>
								</xsl:if>
								<xsl:if test="@onkeyup">
									<xsl:attribute name="onkeyup"><xsl:value-of select="normalize-space(@onkeyup)"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="@onchange">
									<xsl:attribute name="onchange"><xsl:value-of select="normalize-space(@onchange)"/></xsl:attribute>
								</xsl:if>
								<xsl:attribute name="value"><xsl:choose><xsl:when test="@default and string-length(normalize-space(value))=0"><xsl:value-of select="normalize-space(@default)"/></xsl:when><xsl:when test="normalize-space(value)!='null' and @keep-decimal"><xsl:value-of select="normalize-space(value)"/></xsl:when><xsl:when test="contains(normalize-space(value),'.0' )"><xsl:value-of select="substring-before(value,'.0')"/></xsl:when><xsl:when test="normalize-space(value)!='null' "><xsl:value-of select="normalize-space(value)"/></xsl:when></xsl:choose></xsl:attribute>
								<xsl:if test="@default">
									<xsl:attribute name="default"><xsl:value-of select="normalize-space(@default)"/></xsl:attribute>
								</xsl:if>
								<!--		create attributes for nodes that require validation	-->
								<xsl:if test="./validation">
									<xsl:attribute name="validate"><xsl:value-of select="normalize-space(./validation/@type)"/></xsl:attribute>
									<xsl:attribute name="errorCode"><xsl:value-of select="./validation/@error-code"/></xsl:attribute>
									<xsl:if test="./validation/@dateBeforeReference">
										<xsl:attribute name="dateBeforeRef"><xsl:value-of select="normalize-space(./validation/@dateBeforeReference)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@dateAfterReference">
										<xsl:attribute name="dateAfterRef"><xsl:value-of select="./validation/@dateAfterReference"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@nameRef">
										<xsl:attribute name="nameRef"><xsl:value-of select="normalize-space(./validation/@nameRef)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@name">
										<xsl:attribute name="fieldLabel"><xsl:value-of select="normalize-space(./validation/@name)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./onchange/@nameRef">
										<xsl:attribute name="nameRef"><xsl:value-of select="normalize-space(./validation/@nameRef)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/pattern">
										<xsl:attribute name="pattern"><xsl:value-of select="normalize-space(./validation/pattern)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@required">
										<xsl:attribute name="required"><xsl:value-of select="normalize-space(./validation/@required)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@requiredPartner">
										<xsl:attribute name="requiredPartner"><xsl:value-of select="normalize-space(./validation/@requiredPartner)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@userDefinedJS">
										<xsl:attribute name="userDefinedJS">true</xsl:attribute>
									</xsl:if>
									<!--xsl:if test="string(number(normalize-space(value)))!='NaN' and contains(normalize-space(value), '.0')">
										<xsl:attribute name="value"><xsl:value-of select="substring-before(normalize-space(value),'.')"/></xsl:attribute>
									</xsl:if-->
								</xsl:if>
								<xsl:attribute name="onload">this.value='yes';</xsl:attribute>
							</xsl:element>
							<!--		removes the extra carriage return on masks 	-->
							<xsl:if test="./validation/@mask[.='mm/dd/yyyy']">
							<nobr style="position: relative">
						          <a href="#" style="position: absolute">
							     <img src="calendar.gif" border="0" alt="Select a Date" align="bottom" style="float:left">
												<xsl:attribute name="name"><xsl:value-of select="generate-id()"/>_button</xsl:attribute>
												<xsl:attribute name="onclick">getCalDate('<xsl:value-of select="normalize-space(@name)"/>','<xsl:value-of select="generate-id()"/>_button');return false;</xsl:attribute>
												<xsl:attribute name="onkeypress">showCalendarEnterKey('<xsl:value-of select="normalize-space(@name)"/>','<xsl:value-of select="generate-id()"/>_button',event);</xsl:attribute>		
									</img>
								</a>
									<center>
										<xsl:value-of select="normalize-space(./validation/@mask)"/>
									</center>
									<xsl:attribute name="value"><xsl:if test="normalize-space(value)!='null'"><xsl:value-of select="normalize-space(value)"/></xsl:if></xsl:attribute>
								</nobr>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
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
					</input>
				</xsl:when>
				<xsl:when test="@type[. = 'plain-text']">
					<!--		creates notation for elements		-->
					<xsl:if test="@note">
						<b>
							<xsl:value-of select="@note"/>
						</b>
						<br/>
					</xsl:if>
					<xsl:if test="normalize-space(value)!='null'">
						<span>
							<xsl:attribute name="class"><xsl:value-of select="normalize-space(value/@class)"/></xsl:attribute>
							<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
							<xsl:value-of select="value" disable-output-escaping="yes"/>
						</span>
					</xsl:if>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates telephone element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'telephone']">
					<xsl:choose>
						<xsl:when test="./readonly or ancestor::element/readonly">
							<xsl:choose>
								<xsl:when test="(value)='null'">
									<xsl:attribute name="width">100</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<span>
										<!--		create attributes for nodes that require validation	-->
										<xsl:if test="normalize-space(value)!='null' and string-length(value) > 0">
											<xsl:value-of select="normalize-space(value)"/>
										</xsl:if>
									</span>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<input type="hidden">
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
								<xsl:attribute name="validate">reg-expr</xsl:attribute>
								<xsl:attribute name="pattern"><![CDATA[^(((\d{3})-(\d{3}-\d{4})))?$]]></xsl:attribute>
								<xsl:attribute name="errorCode">ERR020</xsl:attribute>
								<xsl:attribute name="fieldLabel"><xsl:value-of select="@label"/></xsl:attribute>
								<!--can set the validate in xsp if different error code than default-->
								<xsl:if test="./validation">
									<xsl:attribute name="validate"><xsl:value-of select="normalize-space(./validation/@type)"/></xsl:attribute>
									<xsl:attribute name="errorCode"><xsl:value-of select="normalize-space(./validation/@error-code)"/></xsl:attribute>
									<xsl:if test="./validation/@requiredPartner">
									<xsl:attribute name="requiredPartner"><xsl:value-of select="normalize-space(./validation/@requiredPartner)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/@name">
										<xsl:attribute name="fieldLabel"><xsl:value-of select="normalize-space(./validation/@name)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="./validation/pattern">
										<xsl:attribute name="pattern"><xsl:value-of select="normalize-space(./validation/pattern)"/></xsl:attribute>
									</xsl:if>
								</xsl:if>
								<xsl:if test="string-length($batchId)>0">
									<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
									<xsl:attribute name="isNested">yes</xsl:attribute>
								</xsl:if>
								<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="onchange">showPhoneString('<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
								<xsl:attribute name="onkeypress">disableTelephoneTextboxes('<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
								<xsl:if test="string-length(normalize-space(value)) != 0">
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/></xsl:attribute>
								</xsl:if>
							</input>
							<nobr>
								<input type="text" size="2" maxlength="3">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>1</xsl:attribute>
									<xsl:attribute name="onchange">addPhoneString(1, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="onkeyup">moveFocusParsedInputs(1, this, '<xsl:value-of select="normalize-space($id)"/>',event)</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before(value, '-'))"/></xsl:attribute>
								</input>-<input type="text" size="2" maxlength="3">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>2</xsl:attribute>
									<xsl:attribute name="onchange">addPhoneString(2, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="onkeyup">moveFocusParsedInputs(2, this, '<xsl:value-of select="normalize-space($id)"/>',event)</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before(substring-after(value, '-'),'-'))"/></xsl:attribute>
								</input>-<input type="text" size="3" maxlength="4">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>3</xsl:attribute>
									<xsl:attribute name="onchange">addPhoneString(3, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-after(substring-after(value, '-'),'-'))"/></xsl:attribute>
								</input>
							</nobr>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates SSN element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'ssn']">
					<xsl:choose>
						<xsl:when test="./readonly or ancestor::element/readonly">
							<xsl:choose>
								<xsl:when test="(value)='null'">
									<xsl:attribute name="width">100</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<span>
										<!--		create attributes for nodes that require validation	-->
										<xsl:if test="normalize-space(value)!='null' and string-length(value) > 0">
											<xsl:value-of select="substring((value), 1, 3)"/>-<xsl:value-of select="substring((value), 4, 3)"/>-<xsl:value-of select="substring((value), 7, 4)"/>
										</xsl:if>
									</span>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<input type="hidden">
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
								<xsl:attribute name="validate">reg-expr</xsl:attribute>
								<xsl:attribute name="pattern"><![CDATA[^(((\d{3})?-(\d{2}-\d{4})))?$]]></xsl:attribute>
								<xsl:attribute name="errorCode">ERR007</xsl:attribute>
								<xsl:attribute name="fieldLabel">SSN</xsl:attribute>
								<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:if test="string-length(normalize-space(value)) != 0">
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="string-length($batchId)>0">
									<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
									<xsl:attribute name="isNested">yes</xsl:attribute>
								</xsl:if>
							</input>
							<nobr>
								<input type="text" size="2" maxlength="3">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>1</xsl:attribute>
									<xsl:attribute name="onchange">addSSNString(1, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="onkeyup">moveFocusParsedInputs(1, this, '<xsl:value-of select="normalize-space($id)"/>',event)</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before(value, '-'))"/></xsl:attribute>
								</input>-<input type="text" size="1" maxlength="2">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>2</xsl:attribute>
									<xsl:attribute name="onchange">addSSNString(2, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="onkeyup">moveFocusParsedInputs(2, this, '<xsl:value-of select="normalize-space($id)"/>',event)</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before(substring-after(value, '-'),'-'))"/></xsl:attribute>
								</input>-<input type="text" size="3" maxlength="4">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>3</xsl:attribute>
									<xsl:attribute name="onchange">addSSNString(3, this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-after(substring-after(value, '-'),'-'))"/></xsl:attribute>
								</input>
							</nobr>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates HH:MM element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'hh:mm']">
					<input type="hidden">
						<xsl:attribute name="name"><xsl:value-of select="concat(normalize-space(@name), '.numericValue1')"/></xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:if test="string-length(normalize-space(value)) != 0">
							<xsl:attribute name="value"><xsl:value-of select="substring-before(normalize-space(value),':')"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="string-length($batchId)>0">
							<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
							<xsl:attribute name="isNested">yes</xsl:attribute>
						</xsl:if>
					</input>
					<input type="hidden">
						<xsl:attribute name="name"><xsl:value-of select="concat(normalize-space(@name), '.separatorCd')"/></xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:attribute name="value">:</xsl:attribute>
						<xsl:if test="string-length($batchId)>0">
							<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
							<xsl:attribute name="isNested">yes</xsl:attribute>
						</xsl:if>
					</input>
					<input type="hidden">
						<xsl:attribute name="name"><xsl:value-of select="concat(normalize-space(@name), '.numericValue2')"/></xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:if test="string-length(normalize-space(value)) != 0">
							<xsl:attribute name="value"><xsl:value-of select="substring-after(normalize-space(value),':')"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="string-length($batchId)>0">
							<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
							<xsl:attribute name="isNested">yes</xsl:attribute>
						</xsl:if>
					</input>
					<input type="text" size="5" maxlength="5">
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/>1</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
						<xsl:attribute name="onchange">updateHHMM(this, '<xsl:value-of select="normalize-space($id)"/>')</xsl:attribute>
						<xsl:attribute name="validate">reg-expr</xsl:attribute>
						<xsl:attribute name="pattern"><![CDATA[^((0[0-9]|1[0-9]|2[0-4]):(0[0-9]|[1-5][0-9]|60))?$]]></xsl:attribute>
						<xsl:attribute name="errorCode">ERR019</xsl:attribute>
						<xsl:attribute name="fieldLabel"><xsl:value-of select="@label"/></xsl:attribute>
					</input>
					<br/>
						hh:mm
					
			</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates regular input text area field form element                               -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'textarea']">
					<xsl:choose>
						<xsl:when test="./readonly or ancestor::element/readonly">
							<!--xsl:attribute name="id">vform<xsl:value-of select="normalize-space(@name)"/></xsl:attribute-->
							<!--		creates notation for elements		-->
							<xsl:element name="span">
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="type">textarea</xsl:attribute>
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="normalize-space(value)!='null'">
									<xsl:value-of select="value"/>
								</xsl:if>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<!--xsl:attribute name="id">vform<xsl:value-of select="normalize-space(@name)"/></xsl:attribute-->
							<!--		creates notation for elements		-->
							<xsl:element name="textarea">
								<!--xsl:attribute name="onkeypress">SubmitOnEnter();</xsl:attribute-->
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="cols">40</xsl:attribute>
								<xsl:attribute name="rows">4</xsl:attribute>
								<!--<xsl:attribute name="class">textbox</xsl:attribute>-->
								<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:attribute name="size"><xsl:value-of select="normalize-space(@size)"/></xsl:attribute>
								<xsl:if test="string-length($batchId)>0">
									<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
									<xsl:attribute name="isNested">yes</xsl:attribute>
								</xsl:if>
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
								<!--		create attributes for nodes that require validation	-->
								<xsl:if test="./validation">
									<xsl:attribute name="validate"><xsl:value-of select="normalize-space(./validation/@type)"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="validation/@name">
									<xsl:attribute name="fieldLabel"><xsl:value-of select="normalize-space(validation/@name)"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="validation/@fieldLength">
									<xsl:attribute name="maxLength"><xsl:value-of select="normalize-space(validation/@fieldLength)"/></xsl:attribute>
								</xsl:if>
								<xsl:choose>
									<xsl:when test="validation/@fieldLength">
										<xsl:attribute name="onkeyup"><xsl:value-of select="concat(concat('chkMaxLength(this,',validation/@fieldLength),')')"/></xsl:attribute>
										<xsl:attribute name="onkeydown"><xsl:value-of select="concat(concat('chkMaxLength(this,',validation/@fieldLength),')')"/></xsl:attribute>									
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="onkeyup"><xsl:value-of select="'chkMaxLength(this,2000)'"/></xsl:attribute>
										<xsl:attribute name="onkeydown"><xsl:value-of select="'chkMaxLength(this,2000)'"/></xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="@maxlength">
										<xsl:attribute name="maxlength"><xsl:value-of select="normalize-space(@maxlength)"/></xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="maxlength">2000</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:if test="normalize-space(value)!='null'">
									<xsl:value-of select="value" disable-output-escaping="yes"/>
								</xsl:if>
								<xsl:if test="@disabled[. = 'true'] or $disabled= 'true'">
									<xsl:attribute name="disabled"/>
								</xsl:if>
							</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates drop down list box form element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'select']">
					<xsl:if test="cellFormat/align">
						<xsl:attribute name="align"><xsl:value-of select="normalize-space(cellFormat/align)"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="cellFormat/bgcolor">
						<xsl:attribute name="bgcolor"><xsl:value-of select="normalize-space(cellFormat/bgcolor)"/></xsl:attribute>
					</xsl:if>
					<xsl:choose>
						<xsl:when test="readonly or ancestor::element/readonly">
							<xsl:element name="span">
								<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
								<xsl:attribute name="type">select</xsl:attribute>
								<xsl:if test="header">
									<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
								</xsl:if>
								<xsl:if test="srt-options-string">
									<xsl:call-template name="srt-codes-readonly">
										<xsl:with-param name="delimiter" select="'|'"/>
										<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
										<xsl:with-param name="value" select="normalize-space(value)"/>
									</xsl:call-template>
								</xsl:if>
							</xsl:element>
							<xsl:element name="input">
								<xsl:attribute name="type">hidden</xsl:attribute>
								<xsl:attribute name="id">list<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="normalize-space(srt-options-string)"/></xsl:attribute>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<table border="0" cellpadding="0" cellspacing="0">
								<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/>_ac_table</xsl:attribute>
								<xsl:if test="@type='select'and @size">
									<tr>
										<td>
											<span class="normalNineBlack" align="left">
												<i>(Use Ctrl to select more than one)</i>
											</span>
										</td>
									</tr>
								</xsl:if>
								<xsl:if test=" not(normalize-space($batchId)='Susceptibility') and not(normalize-space($batchId)='Test Result') and (validation/@type='required' or validation/required)  and string-length($batchId)>0">
									<tr>
										<th colspan="2" class="normalNineBlack">
											<i>(Required for Add/Update <xsl:value-of select="$batchId"/>)</i>
										</th>
									</tr>
								</xsl:if>
								<xsl:if test="not(@size) and not(@autocomplete='disable')">
									<tr>
										<!--autocomplete drop down feature-->
										<td>
											<input type="text" size="30">
												<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>_textbox</xsl:attribute>
												<xsl:if test="@length">
													<xsl:attribute name="size"><xsl:value-of select="$length"/></xsl:attribute>
												</xsl:if>
												<!--xsl:attribute name="onclick"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onselect"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onkeydown"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute>
												<xsl:attribute name="onkeypress"><xsl:value-of select="'storeCaret(this);'"/></xsl:attribute-->
												<xsl:attribute name="onfocus"><xsl:value-of select="'storeCaret(this);'"/>AutocompleteStoreOnFocusValue(this);</xsl:attribute>
												<xsl:attribute name="onkeydown">CheckTab('<xsl:value-of select="normalize-space(@name)"/>',this);</xsl:attribute>
												<xsl:if test = "normalize-space(@name)='ccd'">
												   <xsl:attribute name="onkeydown">enterToTabForSTD(event.keyCode);CheckTab('<xsl:value-of select="normalize-space(@name)"/>',this);</xsl:attribute>
												</xsl:if>
												<xsl:attribute name="onkeyup">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,null,event);storeCaret(this);</xsl:attribute>
												<xsl:attribute name="onblur">AutocompleteSynch('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
												<xsl:if test="@default">
													<xsl:attribute name="value"><xsl:value-of select="substring-before(substring-after(concat('|',srt-options-string),concat('|',concat(@default,'$'))),'|')"/></xsl:attribute>
												</xsl:if>
												<xsl:if test="string-length(normalize-space(value))>0">
													<xsl:attribute name="value"><xsl:value-of select="substring-before(substring-after(concat('|',srt-options-string),concat('|',concat(value,'$'))),'|')"/></xsl:attribute>
												</xsl:if>
												<xsl:if test="string-length($focus-candidate)>0">
													<xsl:attribute name="focusCandidate"><xsl:value-of select="$focus-candidate"/></xsl:attribute>
												</xsl:if>
												<xsl:if test="string-length($focus-candidate-close)>0">
													<xsl:attribute name="focusCandidateClose"><xsl:value-of select="$focus-candidate-close"/></xsl:attribute>
												</xsl:if>
																						</input>
											<img src="type-ahead2.gif" border="0" alt="Choose option" align="top">
												<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>_button</xsl:attribute>
												<xsl:attribute name="onclick">AutocompleteExpandListbox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
											</img>
										</td>
									</tr>
								</xsl:if>
								<tr>
									<td colspan="2">
										<xsl:element name="select">
											<xsl:attribute name="AUTOCOMPLETE">off</xsl:attribute>
											<xsl:if test="ancestor::element/@type='entity-search' ">
												<xsl:attribute name="entitysearch">yes</xsl:attribute>
											</xsl:if>
											<xsl:if test="$partner">
												<xsl:attribute name="partner">yes</xsl:attribute>
											</xsl:if>
											<xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
											<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
											<xsl:if test="@size">
												<xsl:attribute name="size"><xsl:value-of select="normalize-space(@size)"/></xsl:attribute>
												<xsl:attribute name="multiple">true</xsl:attribute>
											</xsl:if>
											<xsl:if test="@default">
												<xsl:attribute name="default"><xsl:value-of select="normalize-space(@default)"/></xsl:attribute>
											</xsl:if>
											<!-- used in conditional entry for additional testing	-->
											<xsl:if test="@check">
												<xsl:attribute name="check"><xsl:value-of select="normalize-space(@check)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="@disabled[. = 'true']">
												<xsl:attribute name="disabled"/>
											</xsl:if>
											<xsl:if test="@hide[. = 'true']">
												<xsl:attribute name="class">none</xsl:attribute>
											</xsl:if>
											<xsl:if test="header">
												<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
											</xsl:if>
											<!--autocomplete feature-->
											<xsl:if test="not(@size) and not(@autocomplete='disable')">
												<xsl:attribute name="size">5</xsl:attribute>
												<xsl:attribute name="class">none</xsl:attribute>
												<xsl:attribute name="onclick">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,true,event);this.className="none";</xsl:attribute>
												<xsl:attribute name="onkeyup">AutocompleteComboBox('<xsl:value-of select="normalize-space(@name)"/>_textbox','<xsl:value-of select="normalize-space(@name)"/>',true,true,event);</xsl:attribute>
												<xsl:attribute name="onblur">this.className='none';</xsl:attribute>
												<xsl:attribute name="typeahead">true</xsl:attribute>
											</xsl:if>
											<xsl:if test="$onchange">
												<xsl:attribute name="onchange"><xsl:value-of select="$onchange"/></xsl:attribute>
											</xsl:if>

											<xsl:if test="string-length($batchId)>0">
												<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
												<xsl:attribute name="isNested">yes</xsl:attribute>
											</xsl:if>
											<!-- VL:  support for allowing disabling text field when "Other" is selected  -->
											<xsl:if test="onchange">
												<xsl:if test="onchange/@type[. = 'enableOnOtherOption']">
													<xsl:attribute name="onchange">selectBoxToggleOfInputBox( this.id,'<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="onchange/@nameRef"/>')</xsl:attribute>
													<xsl:attribute name="validationType"><xsl:value-of select="onchange/@type"/></xsl:attribute>
													<xsl:attribute name="nameRef"><xsl:value-of select="normalize-space(./onchange/@nameRef)"/></xsl:attribute>
												</xsl:if>
												<xsl:if test="onchange/@type[. = 'autoCalculateDOB']">
													<xsl:attribute name="onchange">doAutoCalculateDOB(<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/@nameRef[1]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/@nameRef[2]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/@nameRef[3]"/>, <xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="./onchange/@nameRef[4]"/> );</xsl:attribute>
												</xsl:if>
											</xsl:if>
											<!--	this is for conditional entry types	-->
											<xsl:if test="$onclick">
												<xsl:attribute name="onchange"><xsl:value-of select="$onclick"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="@onchange">
												<xsl:attribute name="onchange"><xsl:value-of select="normalize-space(@onchange)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="@description">
												<xsl:attribute name="onchange">PassSelectDescriptionText('<xsl:value-of select="normalize-space(@description)"/>', this);</xsl:attribute>
											</xsl:if>
											<xsl:if test="@summarydescription">
												<xsl:attribute name="onchange">PassSelectDescriptionText('<xsl:value-of select="normalize-space(@summarydescription)"/>', this);changeCriteriaFlag();</xsl:attribute>
											</xsl:if>
											<!--		create attributes for nodes that require validation	-->
											<xsl:if test="validation/@type">
												<xsl:attribute name="validate"><xsl:value-of select="normalize-space(validation/@type)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="validation/@name">
												<xsl:attribute name="fieldLabel"><xsl:value-of select="normalize-space(validation/@name)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="validation/@nameRef">
												<xsl:attribute name="nameRef"><xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="normalize-space(./validation/@nameRef)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="validation/@error-code">
												<xsl:attribute name="errorCode"><xsl:value-of select="normalize-space(validation/@error-code)"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="srt-options-string">
												<xsl:choose>
													<xsl:when test="@noblank"/>
													<xsl:otherwise>
														<option value=""/>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:choose>
													<xsl:when test="@size">
														<!--handle situations with multi-select -->
														<xsl:variable name="selected">
															<xsl:choose>
																<xsl:when test="contains(value,'|')">
																	<xsl:value-of select="value"/>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:value-of select="concat(value, '|')"/>
																</xsl:otherwise>
															</xsl:choose>
														</xsl:variable>
														<xsl:call-template name="srt-codes">
															<xsl:with-param name="default" select="normalize-space(@default)"/>
															<xsl:with-param name="selected-options" select="normalize-space($selected)"/>
															<xsl:with-param name="selected-option" select="''"/>
															<xsl:with-param name="delimiter" select="'|'"/>
															<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
															<xsl:with-param name="excluded-options" select="normalize-space(@exclude)"/>
														</xsl:call-template>
													</xsl:when>
													<xsl:otherwise>
														<xsl:call-template name="srt-codes">
															<xsl:with-param name="default" select="normalize-space(@default)"/>
															<xsl:with-param name="selected-options" select="''"/>
															<xsl:with-param name="selected-option" select="normalize-space(value)"/>
															<xsl:with-param name="delimiter" select="'|'"/>
															<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
															<xsl:with-param name="excluded-options" select="normalize-space(@exclude)"/>
														</xsl:call-template>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:if>
											<!--explicitly defined options for select-->
											<xsl:for-each select="options/option">
												<xsl:element name="option">
													<xsl:if test="normalize-space(@value)=normalize-space(../../value)">
														<xsl:attribute name="selected">selected</xsl:attribute>
													</xsl:if>
													<xsl:attribute name="value"><xsl:value-of select="normalize-space(@value)"/></xsl:attribute>
													<xsl:value-of select="normalize-space(@name)"/>
												</xsl:element>
											</xsl:for-each>
											<!-- need to update the hidden variable holding the description based on user selection -->
										</xsl:element>
									</td>
								</tr>
								<tr>
									<td>
										<xsl:if test="@description">
											<input type="hidden">
												<xsl:attribute name="name"><xsl:value-of select="normalize-space(@description)"/></xsl:attribute>
												<xsl:attribute name="id">hiddenSelectDescTxt<xsl:value-of select="normalize-space(@description)"/></xsl:attribute>
												<xsl:attribute name="value"/>
											</input>
										</xsl:if>
										<xsl:if test="@summarydescription">
											<input type="hidden">
												<xsl:attribute name="name"><xsl:value-of select="normalize-space(@summarydescription)"/></xsl:attribute>
												<xsl:attribute name="id">hiddenSelectDescTxt<xsl:value-of select="normalize-space(@summarydescription)"/></xsl:attribute>
												<xsl:attribute name="value"/>
											</input>
										</xsl:if>
									</td>
								</tr>
							</table>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates radio button element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'radio']">
					<xsl:if test="@onclick">
						<xsl:attribute name="onclick"><xsl:value-of select="@onclick"/></xsl:attribute>
					</xsl:if>	
					<xsl:if test="@note">
						<b>
							<xsl:value-of select="normalize-space(@note)"/>
						</b>
						<br/>
					</xsl:if>
					<xsl:choose>
						<xsl:when test="normalize-space(@layout)='horizontal' ">
							<table cellpadding="2" cellspacing="0" border="0">
								<tr>
									<xsl:for-each select="options/option">
										<td width="1%">
											<xsl:call-template name="radio-element">
												<xsl:with-param name="onclick" select="$onclick"/>
												<xsl:with-param name="disabled" select="$disabled"/>
												<xsl:with-param name="parentId" select="$parentId"/>
												<xsl:with-param name="batchId" select="$batchId"/>
												<xsl:with-param name="name" select="$name"/>
												<xsl:with-param name="value" select="$value"/>
											</xsl:call-template>
										</td>
										<xsl:call-template name="create-the-label"/>
									</xsl:for-each>
								</tr>
							</table>
						</xsl:when>
						<xsl:otherwise>
							<table cellpadding="2" cellspacing="2" border="0">
								<xsl:for-each select="options/option">
									<tr>
										<td>
											<xsl:call-template name="radio-element">
												<xsl:with-param name="onclick" select="$onclick"/>
												<xsl:with-param name="disabled" select="$disabled"/>
												<xsl:with-param name="parentId" select="$parentId"/>
												<xsl:with-param name="batchId" select="$batchId"/>
											</xsl:call-template>
										</td>
										<xsl:call-template name="create-the-label"/>
									</tr>
								</xsl:for-each>
							</table>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates check box element                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'checkbox']">
					<xsl:if test="@note">
						<b>
							<xsl:value-of select="normalize-space(@note)"/>
						</b>
						<br/>
					</xsl:if>
					<xsl:variable name="header" select="./header"/>
					<table cellpadding="2" cellspacing="2" border="0">
						<tr>
							<xsl:for-each select="options/option">
								<td>
									<xsl:value-of select="@header"/>
								</td>
							</xsl:for-each>
						</tr>
						<tr>
							<td>
								<xsl:for-each select="options/option">
									<xsl:if test="@name">
										<xsl:element name="input">
											<xsl:if test="../../readonly">
												<xsl:attribute name="disabled"/>
											</xsl:if>
											<xsl:if test="$header">
												<xsl:attribute name="header"><xsl:value-of select="$header"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="string-length($batchId)>0">
												<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
												<xsl:attribute name="isNested">yes</xsl:attribute>
											</xsl:if>
											<xsl:attribute name="type">checkbox</xsl:attribute>
											<!--xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute-->
											<!-- Changes made to support check box in data-table -->
											<xsl:choose>
												<xsl:when test="not(normalize-space($name)='' )">
													<xsl:attribute name="name"><xsl:value-of select="$name"/></xsl:attribute>
												</xsl:when>
												<xsl:otherwise>
													<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
												</xsl:otherwise>
											</xsl:choose>
											<!-- Changes made to support check box in data-table -->
											<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
											<xsl:if test="$onclick">
												<xsl:attribute name="onclick"><xsl:value-of select="$onclick"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="@onclick">
												<xsl:attribute name="onclick"><xsl:value-of select="@onclick"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="@onchange">
												<xsl:attribute name="onchange"><xsl:value-of select="@onchange"/></xsl:attribute>
											</xsl:if>
											<xsl:if test="./checked or ($disabled='false' and ancestor::controller) or normalize-space(@value)=normalize-space(value) or contains(normalize-space(value), concat(@value,'|'))">
												<xsl:attribute name="checked">1</xsl:attribute>
											</xsl:if>
											<xsl:attribute name="value"><xsl:value-of select="normalize-space(@value)"/></xsl:attribute>
											<!--check box needs specialization because we need the observation code regardless of if it is checked or not-->
											<!--cannot put this in the general area because this is below the option node not element-->
											<xsl:if test="@observation-name">
												<xsl:attribute name="onchange">checkboxObservationData(this);</xsl:attribute>
												<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>|observation</xsl:attribute>
												<input type="hidden">
													<xsl:attribute name="name"><xsl:value-of select="normalize-space(@observation-name)"/></xsl:attribute>
													<xsl:attribute name="value"><xsl:value-of select="normalize-space(@observation-value)"/></xsl:attribute>
												</input>
												<input type="hidden">
													<xsl:attribute name="name"><xsl:value-of select="concat(substring(normalize-space(@observation-name),1,number(string-length(normalize-space(@observation-name))-2)),'cdDescTxt')"/></xsl:attribute>
													<xsl:attribute name="value"><xsl:value-of select="@label"/></xsl:attribute>
												</input>
												<input type="hidden">
													<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
													<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/></xsl:attribute>
												</input>
											</xsl:if>
											<xsl:if test="partner">
												<xsl:for-each select="partner">
													<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
													<xsl:call-template name="create-element">
														<xsl:with-param name="parentId" select="$parentId"/>
														<xsl:with-param name="batchId" select="$batchId"/>
														<xsl:with-param name="disabled" select="$disabled"/>
														<xsl:with-param name="partner" select=" 'yes' "/>
													</xsl:call-template>
												</xsl:for-each>
											</xsl:if>
										</xsl:element>
									</xsl:if>
									<nobr>
										<xsl:choose>
											<xsl:when test="@class">
												<font>
													<xsl:attribute name="class"><xsl:value-of select="@class"/></xsl:attribute>
													<xsl:value-of select="normalize-space(@label)"/>
												</font>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="normalize-space(@label)"/>
											</xsl:otherwise>
										</xsl:choose>
									</nobr>
									<xsl:choose>
										<xsl:when test="count(../option)!=1">
											<br/>
										</xsl:when>
										<xsl:otherwise>&nbsp;</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</td>
						</tr>
					</table>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			creates check box grid of elements                           -->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'checkbox-grid']">
					<xsl:attribute name="colspan">100%</xsl:attribute>
					<table cellpadding="2" cellspacing="2" border="0">
						<tr>
							<xsl:for-each select="options/option">
								<td colspan="2" valign="top">
									<xsl:call-template name="format-checkbox-text">
										<xsl:with-param name="text" select="normalize-space(@header)"/>
									</xsl:call-template>
								</td>
							</xsl:for-each>
						</tr>
						<tr>
							<xsl:for-each select="options/option">
								<td valign="top">
									<xsl:if test="@name or @value">
										<xsl:element name="input">
											<xsl:if test="../../readonly">
												<xsl:attribute name="disabled"/>
											</xsl:if>
											<xsl:attribute name="type">checkbox</xsl:attribute>
											<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
											<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="normalize-space(@value)"/></xsl:attribute>
											<xsl:if test="./checked">
												<xsl:attribute name="checked">1</xsl:attribute>
											</xsl:if>
										</xsl:element>
									</xsl:if>
								</td>
								<td>
									<xsl:call-template name="format-checkbox-text">
										<xsl:with-param name="text" select="@label"/>
									</xsl:call-template>
									<br/>
									<table cellpadding="0" cellspacing="0" border="0">
										<tbody>
											<tr>
												<xsl:if test="partner">
													<xsl:for-each select="partner">
														<xsl:call-template name="create-element"/>
													</xsl:for-each>
												</xsl:if>
											</tr>
										</tbody>
									</table>
								</td>
								<xsl:if test="position() mod 2 = 0">
									<tr/>
								</xsl:if>
							</xsl:for-each>
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
					<table cellpadding="3" cellspacing="0" border="0" width="100%">
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
				<!-- ===================================================================
				 		Hidden field type
				 ===================================================================
				 -->
				<xsl:when test="@type[. = 'hidden']">
					<xsl:element name="input">
						<xsl:attribute name="id"><xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<xsl:attribute name="type">hidden</xsl:attribute>
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<!--xsl:attribute name="value"><xsl:value-of select="value"/><xsl:text disable-output-escaping="yes"/></xsl:attribute-->
						<xsl:attribute name="value"><xsl:choose><xsl:when test="@default and string-length(normalize-space(value))=0"><xsl:value-of select="normalize-space(@default)"/></xsl:when><xsl:when test="contains(normalize-space(value),'.0' )"><xsl:value-of select="substring-before(value,'.0')"/></xsl:when><xsl:when test="normalize-space(value)!='null' "><xsl:value-of select="normalize-space(value)" disable-output-escaping="yes"/></xsl:when></xsl:choose></xsl:attribute>
						<xsl:if test="header">
							<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="./validation">
							<xsl:attribute name="validate"><xsl:value-of select="normalize-space(./validation/@type)"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="string-length($batchId)>0">
							<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
						</xsl:if>
					</xsl:element>
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
							<xsl:attribute name="header"><xsl:value-of select="./header"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="@onclick">
							<xsl:attribute name="onclick"><xsl:value-of select="@onclick"/></xsl:attribute>
						</xsl:if>
					</xsl:element>
				</xsl:when>
				<!-- =================================================================== -->
				<!-- 			the rest should be handled by compound elements that can incorporate the simple components                        -->
				<!--			the first element must be the select box type element that controls what the other elements do	-->
				<!-- =================================================================== -->
				<xsl:when test="@type[. = 'batch-entry']">
					<fieldset id="batchEntryFieldset">
						<table cellpadding="1" cellspacing="0" border="0" width="100%">
							<xsl:attribute name="id">nestedElementsTable|<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
							<xsl:if test="@showLineNumbers[. = 'true']">
								<xsl:attribute name="showLineNumbers">true</xsl:attribute>
							</xsl:if>
							<thead class="normal">
								<!--		move the history box to the top		-->
								<tr>
									<td colspan="100%" width="100%">
										<fieldset>
											<table cellpadding="3" cellspacing="0" border="0" width="100%">
												<thead class="nestedElementsTypeFootHeader">
													<tr class="Shaded">
														<td/>
														<xsl:if test="@showLineNumbers[. = 'true']">
															<td>
																<b>Number</b>&nbsp;&nbsp;&nbsp;</td>
														</xsl:if>
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
																		<xsl:value-of select="."/>
																	</b>
																</td>
															</xsl:if>
														</xsl:for-each>
													</tr>
												</thead>
												<tbody>
													<xsl:attribute name="id">nestedElementsHistoryBox|<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
												</tbody>
											</table>
										</fieldset>
									</td>
								</tr>
							</thead>
							<tbody onkeydown="BatchEntryKeySubmit(this);">
								<xsl:attribute name="type"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
								<!--		error msg		-->
								<tr>
									<td colspan="10" class="none">
										<xsl:attribute name="id">nestedErrorMsg<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
												One or more fields are in error.  Please make the suggested changes and then try again.
									</td>
								</tr>
								<!--			recursive call to the create lines functions		-->
								<xsl:call-template name="create-lines">
									<xsl:with-param name="disabled">false</xsl:with-param>
									<xsl:with-param name="batchId">
										<xsl:value-of select="normalize-space(@name)"/>
									</xsl:with-param>
								</xsl:call-template>
								<!--			put in the submit action for new or edited info	-->
							</tbody>
							<xsl:choose>
								<xsl:when test="readonly"/>
								<xsl:otherwise>
									<!--		moved the update or add link to the footer		-->
									<tr>
										<td colspan="10" align="right">
											<input type="button" onkeypress="turnOffParentSubmit=true;" class="boldTenBlack">
												<xsl:attribute name="mode">batch-entry</xsl:attribute>
												<xsl:attribute name="id">BatchEntryAddButton<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
												<xsl:attribute name="value">Add <xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
												<xsl:attribute name="onclick">addNewBatchEntry('<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
											</input>
										</td>
									</tr>
								</xsl:otherwise>
							</xsl:choose>
							<tr>
								<td>
									<input type="hidden">
										<xsl:attribute name="mode">batch-entry</xsl:attribute>
										<xsl:attribute name="onfocus">BatchEntryInitialize('<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
										<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
										<xsl:attribute name="id">nestedElementsHiddenField<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
										<xsl:attribute name="value"><xsl:if test="normalize-space(value)!='null'"><xsl:value-of select="normalize-space(value)"/></xsl:if></xsl:attribute>
										<xsl:if test="string-length($batchId)>0">
											<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
											<!--xsl:attribute name="isNested">yes</xsl:attribute-->
										</xsl:if>
									</input>
								</td>
							</tr>
						</table>
					</fieldset>
					<!--input type="hidden">
					<xsl:attribute name="mode">batch-entry</xsl:attribute>
					<xsl:attribute name="onfocus">BatchEntryInitialize('<xsl:value-of select="normalize-space(@name)"/>');</xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
					<xsl:attribute name="id">nestedElementsHiddenField<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:if test="normalize-space(value)!='null'"><xsl:value-of select="normalize-space(value)"/></xsl:if></xsl:attribute>
					<xsl:if test="$parentId">
						<xsl:attribute name="parent"><xsl:value-of select="$parentId"></xsl:value-of></xsl:attribute>
						<xsl:attribute name="isNested">yes</xsl:attribute>
					</xsl:if>
				</input-->
					<xsl:choose>
						<xsl:when test="./readonly">
							<!--			update the history box with information already entered for this person	-->
							<SCRIPT Type="text/javascript" Language="JavaScript"><![CDATA[

							BatchEntryInitializeForView(']]><xsl:value-of select="normalize-space(@name)"/><![CDATA[');

						]]></SCRIPT>
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="not(@popup='true')">
								<!--			update the history box with information already entered for this person	-->
								<SCRIPT Type="text/javascript" Language="JavaScript">
									<xsl:if test="@options='nodelete' ">gBatchEntryDelete=false;</xsl:if><![CDATA[
									BatchEntryInitialize(']]><xsl:value-of select="normalize-space(@name)"/><![CDATA[');
								]]></SCRIPT>
							</xsl:if>
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
							<xsl:when test="normalize-space(controller/options/option/value)='NI' and controller/@inverse = 'true' ">false</xsl:when>
							<xsl:when test="controller/@inverse = 'true'  and not(contains(normalize-space(controller/options/option/value),'|')) and normalize-space(controller/options/option/@value) = normalize-space(controller/options/option/value)">true</xsl:when>
							<xsl:when test="controller/@inverse = 'true'  and contains(normalize-space(controller/options/option/value),'|') and contains(normalize-space(controller/options/option/value), concat(controller/options/option/@value,'|'))">true</xsl:when>
							<xsl:when test="controller/@inverse='true'">false</xsl:when>
							<xsl:when test="controller/@type = 'checkbox' and not(contains(normalize-space(controller/options/option/value),'|')) and normalize-space(controller/options/option/@value) != normalize-space(controller/options/option/value)">true</xsl:when>
							<xsl:when test="controller/@type = 'checkbox'  and contains(normalize-space(controller/options/option/value),'|')  and  not(contains(normalize-space(controller/options/option/value), concat(controller/options/option/@value,'|')))">true</xsl:when>
							<xsl:when test="controller/@type = 'radio' and (normalize-space(controller/@trigger) != normalize-space(controller/value)  or normalize-space(controller/@trigger) != normalize-space(controller/@default) ) and not(normalize-space(controller/value)='') ">true</xsl:when>
							<xsl:when test="controller/@type = 'radio' and not(controller/@trigger)">true</xsl:when>
							<!--	control with more than one controller	-->
							<xsl:when test="controller/@type = 'select' and count(controller)>1 and normalize-space(controller[1]/@trigger) != normalize-space(controller[1]/value) and normalize-space(controller[2]/@trigger) != normalize-space(controller[2]/value)">true</xsl:when>
							<xsl:when test="controller/@type = 'select' and not(controller/@trigger)">false</xsl:when>
							<xsl:when test="controller/@type = 'select'  and count(controller)=1 and not(controller/@size) and normalize-space(controller/@trigger) != normalize-space(controller/value)">true</xsl:when>
							<xsl:when test="controller/@type = 'select'  and count(controller)=1 and not(controller/@size) and normalize-space(controller/value)='' ">true</xsl:when>
							<xsl:when test="controller/@type = 'select' and controller/@size and not(contains(controller/value,controller/@trigger))">true</xsl:when>
							<!--	display is default -->
							<xsl:otherwise>false</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:for-each select="controller">
						<xsl:variable name="controllerName">
							<xsl:choose>
								<xsl:when test="@observation-name and @type='checkbox' ">
									<xsl:value-of select="normalize-space(@name)"/>|observation</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="normalize-space(@name)"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<!--xsl:if test="((validation/@type='required' or validation/required) and name()='controller') and string-length($batchId)>0">
							<tr>
								<th></th>
								<th colspan="2" class="normalNineBlack"><i>(Required for Add/Update <xsl:value-of select="$batchId"/>)</i></th>
							</tr>
						</xsl:if-->
						<tr rowID="{generate-id()}">
							<xsl:if test="$lineId">
								<xsl:attribute name="id"><xsl:value-of select="$lineId"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$condition and ancestor::line/@trigger">
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
							<!--xsl:attribute name="rowID"><xsl:value-of select="number($rowID )+3000"/></xsl:attribute-->
							<xsl:if test="not(string-length($trigger)=0)">
								<xsl:attribute name="trigger"><xsl:value-of select="$trigger"/></xsl:attribute>
							</xsl:if>
							<xsl:call-template name="create-the-label"/>
							<!--td>
							<xsl:attribute name="id">nestedElementsControllerController<xsl:value-of select="normalize-space(../@name)"/></xsl:attribute>
							<xsl:if test="../@eval">
								<xsl:attribute name="eval"><xsl:value-of select="../@eval"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="position() = last()">
								<xsl:attribute name="colspan">3</xsl:attribute>
							</xsl:if-->
							<!--	for type ahead	get reference to the next element for focus, need to explicitly set focus-->
							<xsl:variable name="focus-candidate-temp" select="following::element/controller[@type][position()=1] | following::element[string-length(normalize-space(@name))>0][not(./controller)][@type][position()=1]"/>
							<xsl:call-template name="create-element">
								<xsl:with-param name="parentId" select="$parentId"/>
								<xsl:with-param name="batchId" select="$batchId"/>
								<xsl:with-param name="ce-td-id">nestedElementsControllerController<xsl:value-of select="normalize-space(../@name)"/>
								</xsl:with-param>
								<xsl:with-param name="ce-td-eval">
									<xsl:if test="../@eval">
										<xsl:value-of select="../@eval"/>
									</xsl:if>
								</xsl:with-param>
								<xsl:with-param name="ce-td-colspan">
									<xsl:if test="position() = last()">3</xsl:if>
								</xsl:with-param>
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
								<xsl:with-param name="onclick">
									<xsl:choose>
										<xsl:when test="@trigger">nestedElementsControllerValueSpecific('<xsl:value-of select="normalize-space(../@name)"/>', '<xsl:value-of select="normalize-space($controllerName)"/>' , '<xsl:value-of select="@trigger"/>');</xsl:when>
										<xsl:otherwise>nestedElementsController('<xsl:value-of select="normalize-space(../@name)"/>', '<xsl:value-of select="normalize-space($controllerName)"/>' );</xsl:otherwise>
									</xsl:choose>
								</xsl:with-param>
								<xsl:with-param name="focus-candidate">
									<xsl:choose>
										<xsl:when test="$focus-candidate-temp/@type='entity-search' ">
											<xsl:value-of select="$focus-candidate-temp/@name"/>Button</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$focus-candidate-temp/@name"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:with-param>
								<xsl:with-param name="focus-candidate-close" select="$focus-candidate-close"/>
							</xsl:call-template>
							<!--/td-->
						</tr>
					</xsl:for-each>
					<!--		DETERMINE TO SHOW THE DEPENDANT QUESTIONS	-->
					<tr>
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
						<!--xsl:if test="$condition and ancestor::line/@trigger">
										<xsl:choose>
											<xsl:when test="normalize-space($condition)=normalize-space($trigger)">
												<xsl:attribute name="class">visible</xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:attribute name="class">none</xsl:attribute>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if-->
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
									<xsl:attribute name="id">nestedElementsControllerPayload<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
									<xsl:call-template name="create-lines">
										<xsl:with-param name="condition" select="controller/value"/>
										<xsl:with-param name="parentId">
											<xsl:value-of select="normalize-space($parentId)"/>
										</xsl:with-param>
										<xsl:with-param name="batchId" select="$batchId"/>
									</xsl:call-template>
								</tfoot>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="10" class="visible" rowID="{generate-id(./controller)}">
							<!--xsl:attribute name="rowID"><xsl:value-of select="number($rowID )+3000"/></xsl:attribute-->
						</td>
					</tr>
				</xsl:when>
				<!--
					===================================================================
							join element types to create a single hidden field value
					===================================================================

					use this for formatting only , struts should take care of the object creating and association
				-->
				<xsl:when test="@type[. = 'join']">
				<xsl:choose>
					<xsl:when test="@border">
						<fieldset>
							<table cellpadding="1" cellspacing="0" border="0" width="100%" align="left">
						<xsl:if test="@id">
							<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
						</xsl:if>
						<xsl:call-template name="create-lines">
							<xsl:with-param name="parentId">
								<xsl:value-of select="normalize-space($parentId)"/>
							</xsl:with-param>
							<xsl:with-param name="batchId" select="$batchId"/>
						</xsl:call-template>
							</table>
						</fieldset>
					</xsl:when>
					<xsl:otherwise>					
					<table cellpadding="1" cellspacing="0" border="0">
						<xsl:if test="@id">
							<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
						</xsl:if>
						<xsl:call-template name="create-lines">
							<xsl:with-param name="parentId">
								<xsl:value-of select="normalize-space($parentId)"/>
							</xsl:with-param>
							<xsl:with-param name="batchId" select="$batchId"/>
						</xsl:call-template>
					</table>
					</xsl:otherwise>
				</xsl:choose>
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
							data-table element types to create a table with comments or radio buttons
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
							</thead>
						</xsl:if>
						<tr>
							<td>
								<table class="TableOuter" width="100%" border="0">
									<tr>
										<td>
											<table cellpadding="3" cellspacing="0" border="0" width="100%" class="TableInner">
												<tbody>
													<tr class="Shaded" rowNumber="0" valign="top">
														<xsl:for-each select="header">
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
																<td colspan="5">
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
					<!--</xsl:if>-->
					<SCRIPT Type="text/javascript" Language="JavaScript">
				   if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
				       sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
				}</SCRIPT>
				</xsl:when>
				<!--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-->
				<!--						Entity search 				-->
				<!--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-->
				<xsl:when test="@type[. = 'entity-search']">
					<table cellpadding="2" cellspacing="0" border="0" width="100%">
						<xsl:attribute name="id">entity-table-<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<!--********************************** if alignment do this **************************************************************-->
						<xsl:choose>
							<xsl:when test="@alignment">
								<thead>
									<tr>
										<xsl:if test="not(readonly)">
											<td align="left">
												<nobr>
													<!--  hide search buttons -->
													<xsl:choose>
														<xsl:when test="@displaySearchButtons='false'">
											</xsl:when>
														<xsl:when test="@quick-entry">
															<input type="button" value="Search" id="{@name}Button">
																<xsl:attribute name="onclick">openEntitySearchWindow('<xsl:value-of select="@entity-type"/>','entity-table-<xsl:value-of select="normalize-space(@name)"/>','<xsl:value-of select="@entity-VO"/>')</xsl:attribute>
															</input>
															<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
															<input type="button" value="Clear">
																<xsl:attribute name="onclick">quickEntryClear('entity-table-<xsl:value-of select="normalize-space(@name)"/>')</xsl:attribute>
															</input>
														</xsl:when>
														<xsl:otherwise>
															<input type="button" value="Search" id="{@name}Button">
																<xsl:attribute name="onclick">openEntitySearchWindow('<xsl:value-of select="@entity-type"/>','entity-table-<xsl:value-of select="normalize-space(@name)"/>','<xsl:value-of select="@entity-VO"/>')</xsl:attribute>
															</input>
															<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
															<input type="button" value="Clear">
																<xsl:attribute name="onclick">entitySearchClear('entity-table-<xsl:value-of select="normalize-space(@name)"/>')</xsl:attribute>
															</input>
														</xsl:otherwise>
													</xsl:choose>
												</nobr>
												<!--  hide search buttons -->
											</td>
											<xsl:if test="@code-lookup">
												<td align="right">
													<input type="text" size="10" maxlength="10">
														<xsl:attribute name="name">entity-codeLookupText-<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
													</input>
													<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
													<input type="button" value="Code Lookup">
														<xsl:attribute name="onclick">codeLookup(this,'entity-table-<xsl:value-of select="normalize-space(@name)"/>', '<xsl:value-of select="@entity-type"/>')</xsl:attribute>
													</input>
												</td>
											</xsl:if>
											<xsl:if test="@quick-entry">
												<td align="right">
													<input type="text" size="10" maxlength="10">
														<xsl:attribute name="name">entity-codeLookupText-<xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
													</input>
													<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
													<input type="button" value="Code Lookup">
														<xsl:attribute name="onclick">quickentry(this,'entity-table-<xsl:value-of select="normalize-space(@name)"/>', '<xsl:value-of select="@entity-type"/>')</xsl:attribute>
													</input>
												</td>
											</xsl:if>
										</xsl:if>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td width="60%" colspan="100%">
											<table cellpadding="2" cellspacing="0" border="0" width="100%">
												<xsl:call-template name="create-lines">
													<xsl:with-param name="parentId">
														<xsl:value-of select="normalize-space($parentId)"/>
													</xsl:with-param>
													<xsl:with-param name="batchId" select="$batchId"/>
												</xsl:call-template>
											</table>
										</td>
									</tr>
								</tbody>
							</xsl:when>
							<!--******************************************** else no alignment do this ****************************************************-->
							<xsl:otherwise>
								<tr>
									<td>
										<table cellpadding="2" cellspacing="0" border="0">
											<xsl:call-template name="create-lines">
												<xsl:with-param name="parentId">
													<xsl:value-of select="normalize-space($parentId)"/>
												</xsl:with-param>
												<xsl:with-param name="batchId" select="$batchId"/>
											</xsl:call-template>
										</table>
									</td>
									<xsl:if test="not(readonly)">
										<td width="20%" valign="top">
											<nobr>
												<input type="button" value="Search" id="{@name}Button">
													<xsl:attribute name="onclick">openEntitySearchWindow('<xsl:value-of select="@entity-type"/>','entity-table-<xsl:value-of select="normalize-space(@name)"/>')</xsl:attribute>
												</input>
												<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
												<input type="button" value="Clear">
													<xsl:attribute name="onclick">entitySearchClear('entity-table-<xsl:value-of select="normalize-space(@name)"/>')</xsl:attribute>
												</input>
											</nobr>
										</td>
									</xsl:if>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
						<tr>
							<td>
								<xsl:element name="input">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
									<xsl:attribute name="type">hidden</xsl:attribute>
									<xsl:attribute name="mode">uid</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(value)"/><xsl:text disable-output-escaping="yes"/></xsl:attribute>
									<xsl:if test="validation/@type">
										<xsl:attribute name="validate"><xsl:value-of select="normalize-space(validation/@type)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="validation/@name">
										<xsl:attribute name="fieldLabel"><xsl:value-of select="normalize-space(validation/@name)"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="validation/@error-code">
										<xsl:attribute name="errorCode"><xsl:value-of select="normalize-space(validation/@error-code)"/></xsl:attribute>
									</xsl:if>
									<!--xsl:attribute name="errorCode"><xsl:value-of select="normalize-space(./validation/@error-code)"/></xsl:attribute-->
								</xsl:element>
								<xsl:element name="input">
									<xsl:attribute name="id"><xsl:value-of select="normalize-space(@name)"/>-values</xsl:attribute>
									<xsl:attribute name="type">hidden</xsl:attribute>
									<xsl:attribute name="mode">parsestring</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/>-values</xsl:attribute>
									<xsl:attribute name="value"><xsl:value-of select="normalize-space(hidden-values)"/></xsl:attribute>
								</xsl:element>
							</td>
						</tr>
					</table>
				</xsl:when>
				<!--************************************************************************************************************************************************-->
				<!--************************************************************************************************************************************************-->
				<xsl:when test="partner">
					<xsl:for-each select="partner">
						<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
						<xsl:if test="string-length(normalize-space(@label)) != 0">
							<b>
								<xsl:value-of select="normalize-space(@label)"/>:</b>
						</xsl:if>
						<xsl:call-template name="create-element">
							<xsl:with-param name="parentId" select="$parentId"/>
							<xsl:with-param name="batchId" select="$batchId"/>
							<xsl:with-param name="disabled" select="$disabled"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</xsl:otherwise>
			</xsl:choose>
			<!--#################################################################################
								GENERAL SECTION THAT WILL BE APPLIED REGARDLESS OF TYPE
		####################################################################################-->
			<!-- perform onload javascript if requested -->
			<xsl:if test="@onload">
				<SCRIPT Type="text/javascript" Language="JavaScript">
					<xsl:value-of select="normalize-space(@onload)"/>
				</SCRIPT>
			</xsl:if>
			<!-- perform observation code hidden variable stuff	-->
			<!--		this is for the observation elements that require the question to be sent as a coded value as well as the answer to the question	-->
			<xsl:if test="@observation-name">
				<table>
					<td>
						<input type="hidden">
							<xsl:attribute name="name"><xsl:value-of select="normalize-space(@observation-name)"/></xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="normalize-space(@observation-value)"/></xsl:attribute>
							<xsl:if test="string-length($batchId)>0">
								<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
								<xsl:attribute name="isNested">yes</xsl:attribute>
							</xsl:if>
						</input>
						<input type="hidden">
							<xsl:attribute name="name"><xsl:value-of select="concat(substring(normalize-space(@observation-name),1,number(string-length(normalize-space(@observation-name))-2)),'cdDescTxt')"/></xsl:attribute>
							<xsl:choose>
								<xsl:when test="label">
									<xsl:attribute name="value"><xsl:value-of select="label"/></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="value"><xsl:value-of select="concat(@label, ' ',@subText)"/></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="string-length($batchId)>0">
								<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
								<xsl:attribute name="isNested">yes</xsl:attribute>
							</xsl:if>
						</input>
					</td>
				</table>
			</xsl:if>
			<xsl:if test="partner">
				<xsl:for-each select="partner">
					<!--img src="transparent.gif" width="2" height="0" border="0" alt=""/-->
					<xsl:if test="string-length(normalize-space(@label)) != 0">
						<xsl:call-template name="label">
							<xsl:with-param name="text" select="@label"/>
							<xsl:with-param name="subtext" select="@subText"/>
							<xsl:with-param name="italicString" select="@makeItalic"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:call-template name="create-element">
						<xsl:with-param name="parentId" select="$parentId"/>
						<xsl:with-param name="batchId" select="$batchId"/>
						<xsl:with-param name="disabled" select="$disabled"/>
						<xsl:with-param name="partner" select=" 'yes' "/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:if>
		</td>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		splits up each srt code name pair
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-codes">
		<xsl:param name="default"/>
		<xsl:param name="delimiter"/>
		<xsl:param name="selected-option"/>
		<xsl:param name="selected-options"/>
		<xsl:param name="excluded-options"/>
		<xsl:param name="string"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:call-template name="srt-create-options">
					<xsl:with-param name="default" select="normalize-space($default)"/>
					<xsl:with-param name="delimiter" select="'$'"/>
					<xsl:with-param name="string" select="normalize-space(substring-before($string, $delimiter))"/>
					<xsl:with-param name="selected-option" select="normalize-space($selected-option)"/>
					<xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
					<xsl:with-param name="excluded-options" select="normalize-space($excluded-options)"/>
				</xsl:call-template>
				<xsl:call-template name="srt-codes">
					<xsl:with-param name="default" select="normalize-space($default)"/>
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="normalize-space(substring-after($string, $delimiter))"/>
					<xsl:with-param name="selected-option" select="normalize-space($selected-option)"/>
					<xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
					<xsl:with-param name="excluded-options" select="normalize-space($excluded-options)"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		creates the html option type for each code value pair
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-create-options">
		<xsl:param name="default"/>
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="selected-option"/>
		<xsl:param name="selected-options"/>
		<xsl:param name="excluded-options"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter)   and not(contains(concat('|',$excluded-options,'|'),concat('|',substring-before($string, $delimiter),'|')))">
				<!--  check if this is excluded option  -->
				<xsl:element name="option">
					<xsl:attribute name="value"><xsl:value-of select="normalize-space(substring-before($string, $delimiter))"/></xsl:attribute>
					<!--	check for default when there is no selected value -->
					<xsl:if test="string-length(normalize-space($selected-option))=0 and normalize-space($default)=normalize-space(substring-before($string, $delimiter))">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>
					<xsl:choose>
						<xsl:when test="string-length($selected-option) != 0">
							<xsl:if test="normalize-space($selected-option)=normalize-space(substring-before($string, $delimiter))">
								<xsl:attribute name="selected">selected</xsl:attribute>
							</xsl:if>
						</xsl:when>
						<xsl:when test="string-length($selected-options) != 0">
							<xsl:call-template name="srt-check-list-for-selected">
								<xsl:with-param name="delimiter" select="'|'"/>
								<xsl:with-param name="criteria" select="normalize-space(substring-before($string, $delimiter))"/>
								<xsl:with-param name="selected-options" select="normalize-space($selected-options)"/>
							</xsl:call-template>
						</xsl:when>
					</xsl:choose>
					<xsl:value-of select="substring-after($string, $delimiter)"/>
				</xsl:element>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for making items in parsed string become selected in the multi select box
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-check-list-for-selected">
		<xsl:param name="delimiter"/>
		<xsl:param name="criteria"/>
		<xsl:param name="selected-options"/>
		<xsl:choose>
			<xsl:when test="contains($selected-options, $delimiter) ">
				<xsl:if test="normalize-space($criteria)=normalize-space(substring-before($selected-options, $delimiter))">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="srt-check-list-for-selected">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="criteria" select="normalize-space($criteria)"/>
					<xsl:with-param name="selected-options" select="normalize-space(substring-after($selected-options, $delimiter))"/>
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
						<!-- VL code to alternate color of data lines -->
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
						<xsl:with-param name="lineCounter">
							<xsl:value-of select="$lineCounter"/>
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
							<!-- VL code to alternate color of data lines -->
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
		<xsl:variable name="disableRadio">
			<xsl:choose>
				<xsl:when test="contains($parseString, 'disable')">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
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
								<table cellpadding="0" cellspacing="0" border="0">
									<tbody>
										<tr>
											<xsl:call-template name="create-element">
												<xsl:with-param name="name">
													<xsl:value-of select="concat( @name,substring-before(substring-after($parseString, @name),'--') )"/>
													<xsl:if test="$disableRadio='true' ">
														<xsl:value-of select="$lineCounter"/>
													</xsl:if>
												</xsl:with-param>
												<xsl:with-param name="value">
													<xsl:value-of select="substring-after(substring-before(substring-after($parseString, @name),'---'),'--')"/>
												</xsl:with-param>
												<xsl:with-param name="disabled">
													<xsl:value-of select="$disableRadio"/>
												</xsl:with-param>
											</xsl:call-template>
										</tr>
									</tbody>
								</table>
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
		VL: enhanced-data-table will replace 'data-table' element and will be more easy to use
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="create-enhanced-data-table">
		<table>
			<xsl:if test="@recordsPerPage">
				<thead>
					<xsl:attribute name="pageSize"><xsl:value-of select="@recordsPerPage"/></xsl:attribute>
					<tr>
						<td align="right">
							<table border="0">
								<tr>
									<td class="none">
										<a id="previous">
											<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
											<xsl:attribute name="onclick">enhancedPreviousPage(findCorrespondingNextPageHref(this));</xsl:attribute>Previous</a>
									</td>
									<td class="none"> | </td>
									<td>
										<xsl:if test="count(./record) &lt; number(@recordsPerPage)">
											<xsl:attribute name="class">none</xsl:attribute>
										</xsl:if>
										<a id="next">
											<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
											<xsl:attribute name="onclick">enhancedNextPage(findCorrespondingNextPageHref(this));</xsl:attribute>Next</a>
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
								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="TableInner">
									<tbody class="nestedElementsTypeFootHeader">
										<!--		<tbody >-->
										<xsl:if test="@page-size">
											<xsl:attribute name="pageSize"><xsl:value-of select="@page-size"/></xsl:attribute>
										</xsl:if>
										<xsl:for-each select="header">
											<xsl:if test="( ../@showYesNoRadioButtons[. = 'true'] or ../@showYesNoRadioButtons[. = 'disable']  ) and ( ../@yesNoLabel ) ">
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
												<xsl:if test="../@showYesNoRadioButtons[. = 'true'] or ../@showYesNoRadioButtons[. = 'disable']">
													<xsl:choose>
														<xsl:when test="../@link">
															<td class="ColumnHeaderCenter">
																<a class="SortColumnHeader">
																	<xsl:attribute name="href"><xsl:value-of select="normalize-space(../@link)"/></xsl:attribute>
																	<nobr>Yes No</nobr>
																</a>
															</td>
														</xsl:when>
														<xsl:when test="../@showYesNoRadioButtonsLink[. = 'true']">
															<td class="ColumnHeaderCenter" columnNumber="0">
																<a class="SortColumnHeader">
																	<xsl:attribute name="onclick">sortTableOnColumn(this);</xsl:attribute>
																	<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
																	<xsl:if test="../@showYesNoRadioButtonsSortonload[. = 'true']">
																		<xsl:attribute name="id"><xsl:value-of select="generate-id(..)"/></xsl:attribute>
																	</xsl:if>
																	<xsl:if test="../@showYesNoRadioButtonsSortorder">
																		<xsl:attribute name="sortOrder"><xsl:value-of select="../@showYesNoRadioButtonsSortorder"/></xsl:attribute>
																	</xsl:if>
																	<nobr>Yes No</nobr>
																</a>
															</td>
														</xsl:when>
														<xsl:otherwise>
															<td class="ColumnHeaderCenter">
																<nobr>Yes No</nobr>
															</td>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:if>
												<xsl:for-each select="data">
													<td class="ColumnHeaderCenter">
														<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
														<xsl:if test="@colspan">
															<xsl:attribute name="colspan"><xsl:value-of select="normalize-space(@colspan)"/></xsl:attribute>
														</xsl:if>
														<xsl:choose>
															<xsl:when test="@link">
																<a class="SortColumnHeader">
																	<xsl:attribute name="onclick">sortTableOnColumn(this);</xsl:attribute>
																	<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
																	<xsl:if test="@sortonload[. = 'true']">
																		<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
																	</xsl:if>
																	<xsl:if test="@sortorder">
																		<xsl:attribute name="sortOrder"><xsl:value-of select="@sortorder"/></xsl:attribute>
																	</xsl:if>
																	<!--
		<xsl:attribute name="href"><xsl:value-of select="normalize-space(@link)"/></xsl:attribute>
	-->
																	<xsl:value-of select="."/>
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
									</tbody>
									<tfoot id="enhancedDataTable">
										<xsl:for-each select="record">
											<xsl:call-template name="enhanced-data-table-row-creator">
												<xsl:with-param name="recordsPerPage">
													<xsl:value-of select="../@recordsPerPage"/>
												</xsl:with-param>
											</xsl:call-template>
											<!-- 
							<xsl:choose>
								<xsl:when test="../@displayPage and ../@recordsPerPage and ../@pagingLinkBase">
									<xsl:if test="(position() &gt; (../@displayPage - 1)*../@recordsPerPage) and (position() &lt;= (../@displayPage)*../@recordsPerPage)">
										<xsl:call-template name="enhanced-data-table-row-creator">
	<xsl:with-param name="recordsPerPage"><xsl:value-of select="@recordsPerPage"/></xsl:with-param>
</xsl:call-template>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="enhanced-data-table-row-creator"/>
								</xsl:otherwise>
							</xsl:choose>
							-->
										</xsl:for-each>
									</tfoot>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<xsl:if test="@recordsPerPage">
				<xsl:attribute name="pageSize"><xsl:value-of select="@recordsPerPage"/></xsl:attribute>
				<tr>
					<td align="right">
						<table border="0">
							<tr>
								<td class="none">
									<a id="previous">
										<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
										<xsl:attribute name="onclick">enhancedPreviousPage(findCorrespondingNextPageHref(this));</xsl:attribute>
			Previous
			</a>
								</td>
								<td class="none"> | </td>
								<td>
									<xsl:if test="count(./record) &lt; number(@recordsPerPage)">
										<xsl:attribute name="class">none</xsl:attribute>
									</xsl:if>
									<a id="next">
										<xsl:attribute name="href">javascript:NoOp();</xsl:attribute>
										<xsl:attribute name="onclick">enhancedNextPage(findCorrespondingNextPageHref(this));</xsl:attribute>
			Next
			</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</xsl:if>
		</table>
		<SCRIPT Type="text/javascript" Language="JavaScript">
                           if( document.getElementById('<xsl:value-of select="generate-id()"/>') ) {
                               sortTableOnColumn(document.getElementById('<xsl:value-of select="generate-id()"/>'));
               }</SCRIPT>
		<!-- VLtemp
		<table cellpadding="0" cellspacing="0" border="0" width="600">
			<tr>
				<td align="right">
					<COMMENTS> new way of doing paging;  show all records and get nextHref and prevHref </COMMENTS>
					<xsl:if test="@nextHref and @prevHref and @currentIndex and @totalRecords and @recordsPerPage">
						<xsl:if test="(@currentIndex>0)">
							<a  id="previous">
								<xsl:attribute name="href"><xsl:value-of select="normalize-space(@prevHref)"/></xsl:attribute>
			  	Previous
			  </a>
						</xsl:if>
						<xsl:if test="(@currentIndex>0) and (number(@totalRecords) > number(@currentIndex)+@recordsPerPage)">
				&nbsp;|&nbsp;
			</xsl:if>
						<xsl:if test="number(@totalRecords) > number(@currentIndex)+@recordsPerPage">
							<a  id="next">
								<xsl:attribute name="href"><xsl:value-of select="normalize-space(@nextHref)"/></xsl:attribute>
				  	Next
				  </a>
						</xsl:if>
					</xsl:if>
					<COMMENTS> Old way of doing paging; all records are in XSP and XSL picks the right one to display </COMMENTS>
					<xsl:choose>
						<xsl:when test="@displayPage and @recordsPerPage and @pagingLinkBase">
							<xsl:if test="@displayPage &gt; 1">
								<a  id="previous">
									<xsl:attribute name="href">javascript:document.forms['nedssForm'].enhancedTable_displayPage.value=<xsl:value-of select="normalize-space(@displayPage - 1)"/>;document.forms['nedssForm'].action='<xsl:value-of select="normalize-space(@pagingLinkBase)"/>';document.forms['nedssForm'].submit();</xsl:attribute>
				Previous</a>
							</xsl:if>
							<xsl:if test="(@displayPage &gt; 1) and (@displayPage* @recordsPerPage) &lt; count(./record)">
				&nbsp;|&nbsp;
			</xsl:if>
							<xsl:if test="(@displayPage* @recordsPerPage) &lt; count(./record)">
								<a  id="next">
									<xsl:attribute name="href">javascript:document.forms['nedssForm'].enhancedTable_displayPage.value=<xsl:value-of select="normalize-space(@displayPage + 1)"/>;document.forms['nedssForm'].action='<xsl:value-of select="normalize-space(@pagingLinkBase)"/>';document.forms['nedssForm'].submit();</xsl:attribute>
				Next</a>
							</xsl:if>
						</xsl:when>
					</xsl:choose>
				</td>
			</tr>
		</table>
-->
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		VL: Part of create-enhanced-data-table template.  Creates actuall TR tag and everything inside of it.
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="enhanced-data-table-row-creator">
		<xsl:param name="recordsPerPage">1000</xsl:param>
		<tr align="center" page="1" rowID="{generate-id()}">
			<!-- VL: rowID for access from JavaScript -->
			<!--xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute-->
			<xsl:attribute name="page"><xsl:value-of select="floor((position()-1) div $recordsPerPage) + 1"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="position() > number($recordsPerPage)">
					<xsl:attribute name="class">none</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<!-- VL code to alternate color of data lines -->
						<xsl:when test=" position() mod 2">
							<xsl:attribute name="class">NotShaded</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="class">Shaded</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="../@showYesNoRadioButtons[. = 'true'] or ../@showYesNoRadioButtons[. = 'disable']">
				<td valign="middle" columnNumber="0">
					<!--<xsl:value-of select="@yesNoValue"></xsl:value-of>_-->
					<input type="radio">
						<xsl:if test="../@showYesNoRadioButtons[. = 'disable']">
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</xsl:if>
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<xsl:attribute name="value">Y</xsl:attribute>
						<xsl:if test="normalize-space(@yesNoValue) = 'Y'">
							<xsl:attribute name="checked">1</xsl:attribute>
						</xsl:if>
					</input>
					<input type="radio">
						<xsl:if test="../@showYesNoRadioButtons[. = 'disable']">
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</xsl:if>
						<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
						<xsl:attribute name="value">N</xsl:attribute>
						<xsl:if test="normalize-space(@yesNoValue)= 'N'">
							<xsl:attribute name="checked">1</xsl:attribute>
						</xsl:if>
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
			<!-- There is one data element for each table column -->
			<xsl:for-each select="data">
				<xsl:attribute name="columnNumber"><xsl:value-of select="position()"/></xsl:attribute>
				<xsl:choose>
					<xsl:when test="element">
						<xsl:for-each select="element">
							<xsl:call-template name="create-element"/>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="@link">
						<td>
							<a>
								<xsl:attribute name="href"><xsl:value-of select="normalize-space(@link)"/></xsl:attribute>
								<xsl:value-of select="."/>
							</a>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td>
							<xsl:value-of select="."/>&nbsp;
							</td>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</tr>
		<!--
		<tr>
			<td colspan="10" class="visible">
				<xsl:attribute name="rowID"><xsl:value-of select="position()+4000"/></xsl:attribute>
			</td>
		</tr>
-->
		<!-- There are cases where table is too wide and extra columns are represented in sub-row.
								       This is handled by subdata elements, and we handle them next -->
		<xsl:if test="subdata">
			<tr rowID="{generate-id()}">
				<!-- VL: rowID for access from JavaScript -->
				<!--xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute-->
				<xsl:attribute name="page"><xsl:value-of select="floor((position()-1) div $recordsPerPage) + 1"/></xsl:attribute>
				<xsl:choose>
					<xsl:when test="position() > number($recordsPerPage)">
						<xsl:attribute name="class">none</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
							<!-- VL code to alternate color of data lines -->
							<xsl:when test=" position() mod 2">
								<xsl:attribute name="class">NotShaded</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="class">Shaded</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
				<!-- VLtemp
				<xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute>
				<xsl:choose>
					__ VL code to alternate color of data lines __
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="class">NotShaded</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">Shaded</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
-->
				<xsl:if test="(../@showYesNoRadioButtons[. = 'true'] or ../@showYesNoRadioButtons[. = 'disable']) and recordComments">
					<td>&nbsp;</td>
				</xsl:if>
				<td colspan="100%">
					<table border="0">
						<tr>
							<xsl:for-each select="subdata">
								<xsl:if test="@label">
									<td>
										<b>
											<xsl:value-of select="normalize-space(@label)"/>:</b>
									</td>
								</xsl:if>
								<td>
									<xsl:choose>
										<xsl:when test="@link">
											<a>
												<xsl:attribute name="href"><xsl:value-of select="normalize-space(@link)"/></xsl:attribute>
												<xsl:value-of select="value"/>
											</a>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="normalize-space(value)"/>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:for-each>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
		<!-- show comments in a separate row if needed -->
		<xsl:if test="recordComments">
			<tr rowID="{generate-id()}">
				<!-- VL: rowID for access from JavaScript -->
				<!--xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute-->
				<xsl:attribute name="page"><xsl:value-of select="floor((position()-1) div $recordsPerPage) + 1"/></xsl:attribute>
				<xsl:choose>
					<xsl:when test="position() > number($recordsPerPage)">
						<xsl:attribute name="class">none</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
							<!-- VL code to alternate color of data lines -->
							<xsl:when test=" position() mod 2">
								<xsl:attribute name="class">NotShaded</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="class">Shaded</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
				<!-- VLtemp
				<xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute>
				<xsl:choose>
					__ VL code to alternate color of data lines __
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="class">NotShaded</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class">Shaded</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
-->
				<td colspan="10">
					<xsl:if test="recordComments/@label">
						<table cellpadding="0" border="0" cellspacing="4">
							<tr>
								<td>
									<b>
										<xsl:value-of select="recordComments/@label"/>:</b>&nbsp;
													<textarea rows="3" cols="50">
										<xsl:attribute name="name"><xsl:value-of select="normalize-space(recordComments/@name)"/></xsl:attribute>
										<xsl:value-of select="normalize-space(recordComments)"/>
									</textarea>
								</td>
								<xsl:if test="button">
									<td valign="bottom" align="left">
										<xsl:for-each select="button">
											<input type="button" style="width: 5em; font-size: 10pt;">
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
		<!-- 
		<tr page="1">
			<td colspan="10" class="visible">
				<xsl:attribute name="rowID"><xsl:value-of select="position()"/></xsl:attribute>
			</td>
		</tr>
-->
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		retrieve the description text for srt codes in the readonly version 
		retrieve the description from the coded value in the srt list	
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="srt-codes-readonly">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:call-template name="srt-create-options-readonly">
					<xsl:with-param name="delimiter" select="'$'"/>
					<xsl:with-param name="string" select="substring-before($string, $delimiter)"/>
					<xsl:with-param name="value" select="$value"/>
				</xsl:call-template>
				<xsl:call-template name="srt-codes-readonly">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="value" select="$value"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="srt-create-options-readonly">
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
	<xsl:template name="srt-create-options-readonly">
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
		template for creating the label box
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="create-the-label">
		<xsl:if test="@label and not(@hide) or @note">
			<td>
				<xsl:attribute name="align">right</xsl:attribute>
				<xsl:if test="@align-label">
					<xsl:attribute name="align"><xsl:value-of select="@align-label"/></xsl:attribute>
				</xsl:if>
				<xsl:if test="./readonly or ancestor::element/readonly or ancestor::element/@type='entity-search' ">
					<xsl:attribute name="width">10%</xsl:attribute>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="(@type='select' and @size) or @type='textarea'  or @valign-label='top' ">
						<xsl:attribute name="valign">top</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="valign">middle</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="descendant::validation/@mask">
					<xsl:attribute name="valign">top</xsl:attribute>
				</xsl:if>
				<xsl:if test="(@type='select'and @size) or @notification">
					<br/>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="@indented='yes'">
						<table cellpadding="1" cellspacing="0" border="0">
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td>
									<xsl:choose>
										<xsl:when test="string-length(normalize-space(@label))&lt;30">
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
							<xsl:when test="string-length(normalize-space(@label))&lt;35">
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
		<label>
			<xsl:attribute name="for"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
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
			<xsl:if test="string-length(normalize-space($label))!=0 and not(contains(normalize-space($label), '?'))  and not(contains(normalize-space($subtext), '?')) and not(contains($options,'-nc'))">:</xsl:if>
		</label>
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
		template for handling labels with parenthesis
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="format-label-parenthesis">
		<xsl:param name="text"/>
		<xsl:call-template name="label">
			<xsl:with-param name="text">
				<xsl:value-of select="substring-before($text,'(' )" disable-output-escaping="yes"/>
			</xsl:with-param>
		</xsl:call-template>
		<!--br/-->
		<font class="boldTenBlack">(<xsl:value-of select="substring-after($text,'(' )" disable-output-escaping="yes"/>
		</font>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for checkbox special situation in security
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="format-checkbox-text">
		<xsl:param name="text"/>
		<xsl:choose>
			<xsl:when test="contains($text,'*' )">
				<b>
					<xsl:value-of select="substring-before($text,'*' )"/>
				</b>
				*<xsl:value-of select="substring-after($text,'*' )"/>
			</xsl:when>
			<xsl:when test="contains($text,'Note:' )">
				<b>
					<xsl:value-of select="substring-before($text,'Note:' )"/>
				</b>
				<i>&nbsp;&nbsp;Note:&nbsp;<xsl:value-of select="substring-after($text,'Note:' )"/>
				</i>
			</xsl:when>
			<xsl:otherwise>
				<b>
					<xsl:value-of select="translate($text,' ','&nbsp;')"/>
				</b>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--%%%%%%%%%%%%%%%%%%%%%%
		template for generating radio elements
%%%%%%%%%%%%%%%%%%%%%%%%%%-->
	<xsl:template name="radio-element">
		<xsl:param name="onclick"/>
		<xsl:param name="disabled"/>
		<xsl:param name="parentId"/>
		<xsl:param name="batchId"/>
		<xsl:param name="name"/>
		<xsl:param name="value"/>
		<xsl:element name="input">
			<xsl:attribute name="type">radio</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="@value"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="not(normalize-space($name)='' )">
					<xsl:attribute name="name"><xsl:value-of select="$name"/></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="name"><xsl:value-of select="normalize-space(../../@name)"/></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="$onclick">
				<xsl:attribute name="onclick"><xsl:value-of select="$onclick"/></xsl:attribute>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="$disabled='true' and not(ancestor::controller)">
					<xsl:attribute name="disabled"/>
				</xsl:when>
			</xsl:choose>
			<xsl:if test="../../readonly">
				<xsl:attribute name="disabled"/>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="not(normalize-space($value)='' )">
					<xsl:if test="(normalize-space(@value)=normalize-space(../../@default) and (normalize-space($value)='' or normalize-space($value)='null'))or normalize-space(@value)=normalize-space($value)">
						<xsl:attribute name="checked">1</xsl:attribute>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="(normalize-space(@value)=normalize-space(../../@default) and (normalize-space(../../value)='' or normalize-space(../../value)='null'))or normalize-space(@value)=normalize-space(../../value)">
						<xsl:attribute name="checked">1</xsl:attribute>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:attribute name="value"><xsl:value-of select="@value"/></xsl:attribute>
			<!-- VL:  support for allowing disabling text field when "Other" is selected  -->
			<xsl:if test="../../onchange">
				<xsl:if test="../../onchange/type[. = 'toggleOtherInputs']">
					<xsl:attribute name="onchange">toggleOtherInputs( this, '<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="../../onchange/nameRef[1]"/>', '<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="../../onchange/nameRef[2]"/>', '<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="../../onchange/nameRef[3]"/>', '<xsl:if test="$parentId"><xsl:value-of select="$parentId"/></xsl:if><xsl:value-of select="../../onchange/nameRef[4]"/>' )</xsl:attribute>
				</xsl:if>
			</xsl:if>
			<xsl:if test="@onclick">
				<xsl:attribute name="onclick"><xsl:value-of select="@onclick"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="string-length($batchId)>0">
				<xsl:attribute name="parent"><xsl:value-of select="$batchId"/></xsl:attribute>
				<xsl:attribute name="isNested">yes</xsl:attribute>
			</xsl:if>
		</xsl:element>
		<xsl:if test="partner">
			<xsl:for-each select="partner">
				<img src="transparent.gif" width="2" height="0" border="0" alt=""/>
				<xsl:if test="string-length(normalize-space(@label)) != 0">
					<b>
						<xsl:value-of select="normalize-space(@label)"/>:  </b>
				</xsl:if>
				<xsl:call-template name="create-element">
					<xsl:with-param name="parentId" select="$parentId"/>
					<xsl:with-param name="batchId" select="$batchId"/>
					<xsl:with-param name="disabled" select="$disabled"/>
					<xsl:with-param name="partner" select=" 'yes' "/>
				</xsl:call-template>
			</xsl:for-each>
		</xsl:if>
		
		</xsl:template>



</xsl:stylesheet>