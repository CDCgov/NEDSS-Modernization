<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:param name="page-title"/>
	<xsl:template match="/">
		<!-- defines the layout master -->
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<!--fo:simple-page-master master-name="first"  page-height="29.7cm" 
                           page-width="21cm" 
                           margin-top="1cm" 
                           margin-bottom="2cm" 
                           margin-left="2.5cm" 
                           margin-right="2.5cm">

					<fo:region-body margin-top="1cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1.5cm"/>
				</fo:simple-page-master-->
				<fo:simple-page-master master-name="first" page-height="29.7cm" page-width="21cm" margin-top="1cm" margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
					<fo:region-body margin-top="1cm" margin-bottom="3cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="2cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<!-- starts actual layout -->
			<fo:page-sequence master-reference="first">
				<fo:static-content flow-name="xsl-region-before">
					<!-- table start -->
					<fo:table table-layout="fixed" border-width="0mm" border-style="solid" width="100%">
						<fo:table-column column-width="50mm"/>
						<fo:table-column column-width="30mm"/>
						<fo:table-column column-width="50mm"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">Report:</fo:inline>
										<xsl:value-of select="$page-title"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">User:</fo:inline>
							Xxxxxxx 				          
							</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">Investigation ID:</fo:inline>
							INV123456INV				          
							</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<!-- table end -->
					<fo:table table-layout="fixed" border-width="0mm" border-style="solid" width="100%">
						<fo:table-column column-width="40mm"/>
						<fo:table-column column-width="30mm"/>
						<fo:table-column column-width="20mm"/>
						<fo:table-column column-width="30mm"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">Name:</fo:inline>Natasha B.Noriovsky								
							</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">DOB:</fo:inline>January 15, 1957
							</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">Age:</fo:inline>46 Years	
							</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="left" font-size="10pt" font-style="italic" font-family="serif" font-weight="bold">
										<fo:inline color="red">Current Sex:</fo:inline>Female						       
							</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after">
					<fo:block line-height="14pt" font-size="10pt">
				     </fo:block>
					<fo:block line-height="14pt" font-size="10pt" text-align="right">Page <fo:page-number/>
					</fo:block>
				</fo:static-content>
				<!-- body starts from here -->
				<fo:flow flow-name="xsl-region-body">
					<!--fo:block space-after.optimum="18pt" font-family="Helvetica" font-size="24pt">
						<xsl:value-of select="$page-title"/>
					</fo:block-->
					<xsl:apply-templates/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="group">
		<xsl:if test="@name">
			<fo:block font-size="12pt" font-family="sans-serif" line-height="18pt" space-after.optimum="10pt" background-color="blue" color="yellow" text-align="left" padding-top="0pt">
				<xsl:value-of select="@name"/>
			</fo:block>
		</xsl:if>
		<fo:block space-after.optimum="10pt" font-family="Helvetica" font-size="10pt">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="3in"/>
				<fo:table-column column-width="3in"/>
				<fo:table-body>
					<xsl:call-template name="create-lines">
						<xsl:with-param name="disabled">false</xsl:with-param>
					</xsl:call-template>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>
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
							<xsl:with-param name="lineId" select="../@id"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="setup-cell">
						<xsl:with-param name="disabled" select="$disabled"/>
						<xsl:with-param name="onchange" select="$onchange"/>
						<xsl:with-param name="parentId" select="$parentId"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="setup-cell">
		<xsl:param name="disabled"/>
		<xsl:param name="onchange"/>
		<xsl:param name="onclick"/>
		<xsl:param name="parentId"/>
		<xsl:param name="checked"/>
		<xsl:for-each select="./element">
			<xsl:call-template name="create-element">
				<xsl:with-param name="disabled" select="$disabled"/>
				<xsl:with-param name="onchange" select="$onchange"/>
				<xsl:with-param name="onclick" select="$onclick"/>
				<xsl:with-param name="parentId" select="$parentId"/>
				<xsl:with-param name="checked" select="$checked"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
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
		<xsl:param name="name"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="@type[. = 'join']">
				<xsl:call-template name="create-lines"/>
			</xsl:when>
			<xsl:when test="@type[. = 'line-separator']">
				<fo:table-row>
					<fo:table-cell border=" 0.5pt solid black"/>
					<fo:table-cell border="0.5pt solid black"/>
				</fo:table-row>
			</xsl:when>
			<!--xsl:when test="@type[. = 'batch-entry']">
				<fo:table-row>
					<fo:table-cell  border=" 0.5pt solid black"/>
					<fo:table-cell  border="0.5pt solid black"/>
				</fo:table-row>			
			</xsl:when-->
			<xsl:when test="@type[. = 'hidden'] or @type[. = 'button']  or @type[. = 'raw'] or @type[. = 'entity-search'] or  @type[. = 'data-table'] or @type[. = 'enhanced-data-table'] or @type[. = 'queue-data-table-contacttracing']"/>
			<xsl:when test="@type[. = 'select']">
				<fo:table-row>
					<fo:table-cell padding="6pt">
						<fo:block text-align="right">
							<xsl:value-of select="@label"/>:
						</fo:block>
					</fo:table-cell>
					<xsl:if test="srt-options-string">
						<xsl:call-template name="srt-codes">
							<xsl:with-param name="delimiter" select="'|'"/>
							<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
							<xsl:with-param name="value" select="value"/>
						</xsl:call-template>
					</xsl:if>
				</fo:table-row>
			</xsl:when>
			<!--xsl:when test="@type[. = 'entity-search']">
				<fo:table cellpadding="2" cellspacing="0" border="0" width="100%">
					<fo:table-body>
						<xsl:attribute name="id">entity-table-<xsl:value-of select="normalize-space($id)"/></xsl:attribute>
						<fo:table-row>
							<fo:table-cell width="80%">
								<fo:block>
									<fo:table border="0" width="100%">
										<xsl:call-template name="create-lines"/>
									</fo:table>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell width="20%">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:when-->
			<!-- =================================================================== -->
			<!-- 		display questions based on answers from another quesiton 	-->
			<!--			can be nested    				 -->
			<!-- =================================================================== -->
			<!--xsl:when test="@type[. = 'conditional-entry']">
					
							<xsl:for-each select="controller">

								<fo:table-row>								
									<xsl:if test="$lineId">
										<xsl:attribute name="id"><xsl:value-of select="$lineId"></xsl:value-of></xsl:attribute>
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

									
									<xsl:if test="ancestor::line/@abc">
										<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>	
										<xsl:attribute name="type">abc</xsl:attribute>
										<xsl:choose>
											<xsl:when test="normalize-space(ancestor::line/@abc)='T' ">
												<xsl:attribute name="class">visible</xsl:attribute>
											</xsl:when>
											<xsl:otherwise><xsl:attribute name="class">none</xsl:attribute></xsl:otherwise>
										</xsl:choose>
									</xsl:if>

									
									
									<xsl:if test="not(string-length($trigger)=0)">
										<xsl:attribute name="trigger"><xsl:value-of select="$trigger"></xsl:value-of></xsl:attribute>
									</xsl:if>

										<xsl:call-template name="create-the-label"></xsl:call-template>
									
									<fo:table-cell alignment-adjust="top">
										<fo:block>
										
										<xsl:attribute name="id">nestedElementsControllerController<xsl:value-of select="normalize-space(../@name)"/></xsl:attribute>
										<xsl:if test="position() = last()">
											<xsl:attribute name="colspan">3</xsl:attribute>
										</xsl:if>
											<xsl:call-template name="create-element">
												<xsl:with-param name="parentId" select="$parentId"/>
												<xsl:with-param name="conditionalId" select="normalize-space(../@name)"/>

											</xsl:call-template>										
										</fo:block>
									</fo:table-cell>							
								</fo:table-row>
							</xsl:for-each>

								<fo:table-row>
									
									<xsl:if test="ancestor::line/@abc">
										<xsl:attribute name="bgcolor">#DCDCDC</xsl:attribute>	
										<xsl:attribute name="type">abc</xsl:attribute>
										<xsl:choose>
											<xsl:when test="normalize-space(ancestor::line/@abc)='T' ">
												<xsl:attribute name="class">visible</xsl:attribute>
											</xsl:when>
											<xsl:otherwise><xsl:attribute name="class">none</xsl:attribute></xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								
									<fo:table-cell span="">
										<fo:block>
											<fo:table border="0" width="100%">
												<fo:table-footer>
													<xsl:attribute name="id">nestedElementsControllerPayload<xsl:value-of select="normalize-space($id)"/></xsl:attribute>	
													<xsl:call-template name="create-lines">
														<xsl:with-param name="condition" select="controller/value"></xsl:with-param>
													</xsl:call-template>													
												</fo:table-footer>
											</fo:table>
										</fo:block>										
									</fo:table-cell>
						</fo:table-row>
				</xsl:when-->
			<xsl:otherwise>
				<xsl:if test="./@label and not(@label='')">
					<fo:table-row>
						<fo:table-cell padding="6pt">
							<fo:block text-align="right">
								<xsl:value-of select="@label"/>:
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="6pt">
							<fo:block>
								<xsl:value-of select="./value"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
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
	<xsl:template name="srt-create-options">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:if test="$value=substring-before($string, $delimiter)">
					<fo:table-cell padding="6pt">
						<fo:block>
							<xsl:value-of select="substring-after($string, $delimiter)"/>
						</fo:block>
					</fo:table-cell>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
