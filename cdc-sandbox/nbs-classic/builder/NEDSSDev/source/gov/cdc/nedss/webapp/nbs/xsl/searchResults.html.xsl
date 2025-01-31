<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	<xsl:template match="content">
		<content>
			<xsl:if test="./@title">
				<xsl:attribute name="title"><xsl:value-of select="normalize-space(./@title)"/></xsl:attribute>
			</xsl:if>
			
			<xsl:variable name="type" select="./@type"></xsl:variable>
		

		
		
		<xsl:apply-templates/>
		
		
		<link-bar>
			<link name="New Search"><xsl:value-of select="normalize-space(newSearchHref)"/></link>
			
			<link name="Refine Search"><xsl:value-of select="normalize-space(refineSearchHref)"/></link>
		</link-bar>
		
		<tab>
			<xsl:for-each select="script">
				<script type="text/javascript">
					<xsl:value-of select="normalize-space(text())" disable-output-escaping="yes"/>
				</script>
			</xsl:for-each>
		<html lang="en"><body>
				<table role ="presentation" width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<thead>
						<tr>
							<td valign="top">
								<!-- BEGIN Task Bar -->
								
								<!--END Task Bar-->
								<!--\\\\\\\\\\\ BEGIN CONTENT ////////////-->
								<p>
									<div align="center" class="boldTenDkRed">Your Search Criteria: <xsl:value-of select="substring(normalize-space(searchCriteria),1,string-length(normalize-space(searchCriteria))-1)"/> 
										resulted in <xsl:choose><xsl:when test="(totalCounts>totalRecords)">more than 100</xsl:when><xsl:otherwise><xsl:value-of select="totalRecords"></xsl:value-of></xsl:otherwise></xsl:choose> possible matches.
										<xsl:if test="(totalCounts>totalRecords) or (totalRecords=0)">
											<br/>Would you like to <a><xsl:attribute name="href"><xsl:value-of select="normalize-space(refineSearchHref)"/></xsl:attribute>refine your search?</a>
										</xsl:if>
									</div>
								</p>
								<div align="right">
									<xsl:if test="(currentIndex>0)">
										<a>
											<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-10"/></xsl:attribute>Previous</a>
									</xsl:if>
									<xsl:if test="(currentIndex>0) and (number(totalRecords) > number(currentIndex)+10)">
										&nbsp;|&nbsp;
									</xsl:if>
									<xsl:if test="number(totalRecords) > number(currentIndex)+10">
										<a>
											<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+10"/></xsl:attribute>Next</a>
									</xsl:if>
								</div>
							</td>
						</tr>
					</thead>
					<tbody>
						<!--tr>
							<th>what is the type = <xsl:value-of select="$type"></xsl:value-of></th>
						</tr-->
						
						
						<tr><td>
										<table class="TableOuter" width="100%" border="0"><tr><td>
											<table cellpadding="4" cellspacing="0" border="0" width="100%" class="TableInner" id="searchResultsTable">

												<thead class="nestedElementsTypeFootHeader">
													<tr class="Shaded">
												
														<xsl:for-each select="header/column">
															<td class="ColumnHeader" align="left">
																<xsl:choose>
																	<xsl:when test="link">
																		<a class="boldTenBlack">
																			<xsl:attribute name="href"><xsl:value-of select="normalize-space(./link)"/></xsl:attribute>
																			<xsl:value-of select="normalize-space(./name)"/>
																		</a>
																	</xsl:when>
																	<xsl:otherwise>
																		<b><xsl:value-of select="normalize-space(./name)"/></b>
																	</xsl:otherwise>
																</xsl:choose>
																
															</td>
														</xsl:for-each>
													</tr>
												</thead>

												<tbody>										
													<xsl:choose>
														<xsl:when test="records/record">
															<xsl:for-each select="records/record">
																<xsl:variable name="uid"><xsl:value-of select="normalize-space(uid)"/></xsl:variable>
																<xsl:variable name="recordStatusCd"><xsl:value-of select="normalize-space(uid/@record-status-cd)"/></xsl:variable>

																<xsl:variable name="className">
																	<!-- VL code to alternate color of data lines -->
																		<xsl:choose>
																			<xsl:when test=" position() mod 2">NotShaded</xsl:when>
																			<xsl:otherwise>Shaded</xsl:otherwise>
																		</xsl:choose>
																</xsl:variable>
																<tr valign="top">
																	<!--xsl:attribute name="onclick">reorderBatchEntry(this);</xsl:attribute-->
																	<xsl:attribute name="class"><xsl:value-of select="$className"></xsl:value-of></xsl:attribute>

																	
																	
																	
																	<!--		view link		-->
																	<td>  	
																		<table border="0" cellpadding="2">
																			<tbody>
																				<tr>
																					
																				
																					<xsl:choose>
																						<!--		entity search	-->
																						<xsl:when test="normalize-space(uid/@type)='EntitySearch' ">
																							<td>
																							<xsl:attribute name="id"><xsl:value-of select="$uid"/></xsl:attribute>
			
																							
																							
																							<a>
																								<xsl:choose>
																									<xsl:when test=" not(normalize-space(uid/@VOLookup)='') "><xsl:attribute name="href">javascript:entitySearchPersonVOSelect('<xsl:value-of select="$uid"/>');</xsl:attribute></xsl:when>
																									<xsl:when test="$type='labspecificpatient' "><xsl:attribute name="href">javascript:entitySearchlabPatientSelect('<xsl:value-of select="$uid"/>');</xsl:attribute></xsl:when>
																									<xsl:when test="$type='patient' "><xsl:attribute name="href">javascript:entitySearchPersonSelect('<xsl:value-of select="$uid"/>');</xsl:attribute></xsl:when>
																									<xsl:when test="$type='organization' "><xsl:attribute name="href">javascript:entitySearchOrganizationSelect('<xsl:value-of select="normalize-space($uid)"/>');</xsl:attribute></xsl:when>
																									<xsl:when test="$type='LabReportTestName' "><xsl:attribute name="href">javascript:entitySearchlabReportTestNameSelect('<xsl:value-of select="normalize-space($uid)"/>', '<xsl:value-of select="normalize-space(uid/@labType)"/>');</xsl:attribute></xsl:when>
																									<xsl:when test="$type='provider' "><xsl:attribute name="href">javascript:entitySearchPersonSelect('<xsl:value-of select="$uid"/>');</xsl:attribute></xsl:when>
																								</xsl:choose>	
																								
																								Select
																							</a>

																							<!--	create the hidden inputs for entity search	-->
																							<xsl:for-each select="entity-search/entity-element">
																								<input type="hidden">
																									<xsl:attribute name="name"><xsl:value-of select="normalize-space(@name)"/></xsl:attribute>
																									<xsl:attribute name="value"><xsl:value-of select="normalize-space(.)"/></xsl:attribute>	
																								</input>
																							</xsl:for-each>
																							</td>
																							<!--create icon for exploding or collapsing sub records-->
																							<xsl:if test="nested-records/record">
																								<td valign="middle" align="center"><img src="plus_sign.gif" border="0" alt=""><xsl:attribute name="onclick">searchResultsControlNestedRows(<xsl:value-of select="$uid"/>, this);</xsl:attribute></img></td>
																							</xsl:if>
																						</xsl:when>
																						<!--		regular		-->
																						<xsl:otherwise>
																							<td>
																								<a><xsl:attribute name="onclick">changeSubmitOnce(this);</xsl:attribute><xsl:attribute name="href"><xsl:value-of select="normalize-space(/content/viewHref)"/>&amp;uid=<xsl:value-of select="$uid"/></xsl:attribute>View</a>
																								<xsl:choose>
																									<xsl:when test="$type='patient' and string-length(normalize-space(/content/viewFileHref))!=0 and normalize-space($recordStatusCd)!='SUPERCEDED' and normalize-space($recordStatusCd)!='LOG_DEL' ">
																										<br/>
																										<nobr>
																											<a href="javascript:NoOp();"><xsl:attribute name="onclick" >confirmSubmitOnce(this,'<xsl:value-of select="normalize-space(/content/viewFileHref)"/>&amp;uid=<xsl:value-of select="$uid"/>');</xsl:attribute>View File</a>
																										</nobr>
																									</xsl:when>
																								</xsl:choose>
																								
																								<!-- need to check if view file is valid-->
																								
																								
																							</td>
																							<!--create icon for exploding or collapsing sub records-->
																							<xsl:if test="nested-records/record">
																								<td valign="middle" align="center"><img src="plus_sign.gif" border="0" alt=""><xsl:attribute name="onclick">searchResultsControlNestedRows(<xsl:value-of select="$uid"/>, this);</xsl:attribute></img></td>
																							</xsl:if>


																						</xsl:otherwise>
																					</xsl:choose>
																		
																				</tr>
																			</tbody>
																		</table>			
																	</td>
																	
																	<xsl:call-template name="create-data-columns">
																	</xsl:call-template>
																	
																	<!-- create the hidden revisions	-->
																	
																</tr>
																<!-- create the hidden revisions	-->
															
																<xsl:for-each select="nested-records/record">
																	<tr class="none" >
																		<xsl:attribute name="bgcolor">
																			<xsl:choose>
																				<xsl:when test="$className='Shaded' ">#C0C0C0</xsl:when>
																				<xsl:otherwise>#DCDCDC</xsl:otherwise>
																			</xsl:choose>	
																		</xsl:attribute>

																			
																		<xsl:attribute name="mpr"><xsl:value-of select="$uid"></xsl:value-of></xsl:attribute>
																		<td><!-- place holder for the view links-->&nbsp;</td>
																		<xsl:call-template name="create-data-columns"></xsl:call-template>
																	</tr>
																</xsl:for-each>
																				
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
												</tbody>
										</table>							
									</td>									
							</tr>
						</table>
						</td></tr></tbody>								
						</table>
							<div align="right">
								<xsl:if test="(currentIndex>0)">
									<a>
										<xsl:attribute name="href"><xsl:value-of select="normalize-space(prevHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)-10"/></xsl:attribute>Previous</a>
								</xsl:if>
								<xsl:if test="(currentIndex>0) and (number(totalRecords) > number(currentIndex)+10)">
									&nbsp;|&nbsp;
								</xsl:if>
								<xsl:if test="number(totalRecords) > number(currentIndex)+10">
									<a>
										<xsl:attribute name="href"><xsl:value-of select="normalize-space(nextHref)"/>&amp;currentIndex=<xsl:value-of select="normalize-space(currentIndex)+10"/></xsl:attribute>Next</a>
								</xsl:if>
							</div>
					</body>
				</html>
			</tab>
		</content>
		
	</xsl:template>
	
	<xsl:template name="create-data-columns">
							<xsl:for-each select="column">
								<xsl:choose>
									<!-- name	-->
									<xsl:when test="name">
										<td>
											<xsl:for-each select="name">
												<table border="0" cellspacing="0" cellpadding="2">
													<tr><td colspan="100%"><i><xsl:value-of select="normalize-space(use-code)"/></i></td></tr>
													<tr><td><xsl:value-of select="normalize-space(last-name)"/><xsl:if test="string-length(normalize-space(first-name))!=0">, <br/></xsl:if><xsl:value-of select="normalize-space(first-name)"/></td></tr>
												</table>
											</xsl:for-each>
										</td>

									</xsl:when>
									<!--	address	-->
									<xsl:when test="address">
										<td>
											<xsl:for-each select="address">
												<table border="0" cellspacing="0" cellpadding="2">
													<tr><td><i><xsl:value-of select="normalize-space(use-code)"/> <xsl:if test="string-length(normalize-space(type-code))!=0"> - </xsl:if> <xsl:value-of select="normalize-space(type-code)"/></i></td></tr>
													<tr><td><xsl:value-of select="normalize-space(street-address1)"/></td></tr>
													<tr><td><xsl:value-of select="normalize-space(street-address2)"/></td></tr>
													<tr><td><xsl:value-of select="normalize-space(city)"/><xsl:if test="string-length(normalize-space(state))!=0">, </xsl:if>&nbsp;<xsl:value-of select="normalize-space(state)"/>&nbsp;<xsl:value-of select="normalize-space(zip)"/></td></tr>
												</table>
											</xsl:for-each>
										</td>
									</xsl:when>
									<!--	telephone	-->
									<xsl:when test="telephone">
										<td>
											<xsl:for-each select="telephone">
												<table border="0" cellspacing="0" cellpadding="2">
													<tr><td><i><xsl:value-of select="normalize-space(use-code)"/><xsl:if test="string-length(normalize-space(type-code))!=0"> - </xsl:if><xsl:value-of select="normalize-space(type-code)"/></i></td></tr>
													<tr><td><xsl:value-of select="normalize-space(telephone-number)"/></td></tr>
												</table>
											</xsl:for-each>
										</td>

									</xsl:when>
									<!--	id	-->
									<xsl:when test="id">
										<td>
											<xsl:for-each select="id">
												<table border="0" cellspacing="0" cellpadding="2">
													<tr><td><i><xsl:value-of select="normalize-space(use-code)"/> <xsl:if test="string-length(normalize-space(type-code))!=0">, </xsl:if> <xsl:value-of select="normalize-space(type-code)"/></i></td></tr>
													<tr><td><xsl:value-of select="normalize-space(local-id)"/></td></tr>
												</table>
											</xsl:for-each>
										</td>
									</xsl:when>

									<xsl:otherwise>
										<td>
											<!--xsl:value-of select="column" disable-output-escaping="yes"/-->
											<xsl:copy-of select="."/>
										</td>
									</xsl:otherwise>
								</xsl:choose>
									
								
			
							</xsl:for-each>

		
	</xsl:template>
	
	
	<xsl:template match="button-bar">
			<button-bar>
				<xsl:attribute name="name"><xsl:value-of select="normalize-space(../tab/@name)"/></xsl:attribute>
				<xsl:for-each select="right">
					<xsl:element name="right">
						<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
						<xsl:element name="label">
							<xsl:value-of select="./label"/>
						</xsl:element>
						<xsl:element name="javascript-action">
							<xsl:value-of select="./javascript-action"/>
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="left">
					<xsl:element name="left">
						<xsl:attribute name="authorized"><xsl:value-of select="normalize-space(./@authorized)"/></xsl:attribute>
						<xsl:element name="label">
							<xsl:value-of select="./label"/>
						</xsl:element>
						<xsl:element name="javascript-action">
							<xsl:value-of select="./javascript-action"/>     
						</xsl:element>
					</xsl:element>
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
		</javascript-files>
	</xsl:template>

	
</xsl:stylesheet>
