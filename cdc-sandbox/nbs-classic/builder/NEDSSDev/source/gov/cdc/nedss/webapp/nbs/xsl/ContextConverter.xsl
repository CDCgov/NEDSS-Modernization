<!--
	author: jay kim
	converts the csv file generated from excel to nbs context xml format

						-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	
	<!--	the keys		-->
	<!--xsl:key name="type" match="line" use="./value[position()=1]"></xsl:key>
	<xsl:key name="source-page-state" match="line" use="./value[position()=2]"></xsl:key>
	<xsl:key name="page-id" match="line" use="./value[position()=3]"></xsl:key>
	<xsl:key name="element-id" match="line" use="./value[position()=4]"></xsl:key>
	<xsl:key name="action" match="line" use="./value[position()=5]"></xsl:key>
	<xsl:key name="target-page-state" match="line" use="./value[position()=6]"></xsl:key>
	<xsl:key name="obj-name-to-preserve" match="line" use="./value[position()=7]"></xsl:key>
	<xsl:key name="diagram-ref" match="line" use="./value[position()=8]"></xsl:key-->

	<xsl:key name="type" match="line" use="./value[position()=1]"></xsl:key>
	<xsl:key name="source-page-state" match="line" use="./value[position()=2]"></xsl:key>
	<xsl:key name="page-id" match="line" use="./value[position()=3]"></xsl:key>
	<xsl:key name="source-page-name" match="line" use="./value[position()=4]"></xsl:key>
	<xsl:key name="element-id" match="line" use="./value[position()=5]"></xsl:key>
	<xsl:key name="action" match="line" use="./value[position()=6]"></xsl:key>
	<xsl:key name="target-page-state" match="line" use="./value[position()=7]"></xsl:key>
	<xsl:key name="target-page-id" match="line" use="./value[position()=8]"></xsl:key>
	<xsl:key name="target-page-name" match="line" use="./value[position()=9]"></xsl:key>
	<xsl:key name="obj-name-to-preserve" match="line" use="./value[position()=10]"></xsl:key>
	<xsl:key name="diagram-ref" match="line" use="./value[position()=11]"></xsl:key>
	<!--xsl:key name="original-wb" match="line" use="./value[position()=12]"></xsl:key>
	<xsl:key name="original-sort-order" match="line" use="./value[position()=13]"></xsl:key-->

	
	
	
	<xsl:param name="test" select=" 'PageElement' "></xsl:param>
	
	<xsl:template match="/">
		<!--	get unique page ids		-->
		<xsl:variable name="unique-page" select="/csvFile/line[not(value[position()=3]=preceding-sibling::line/value[position()=3])]"/>
		
		
		<NBSContextMap>
			<NBSPages>
				
					<!--xsl:copy-of select="key('type',$test)"></xsl:copy-of-->
					
					
					
					<xsl:for-each select="$unique-page">
						<NBSPage>
							<xsl:attribute name="id"><xsl:value-of select="value[position()=3]"></xsl:value-of></xsl:attribute>
							<xsl:attribute name="name">
							
								<xsl:call-template name="remove-trailing-numbers">
									<xsl:with-param name="string" select="normalize-space(value[position()=4])"/>
								</xsl:call-template>
							
							</xsl:attribute>	
												
							<xsl:variable name="id" select="value[position()=3]"></xsl:variable>
							
							
							<NBSPageContext>
							
							
													
													
													<PageElements>
														<xsl:for-each select="key('source-page-state','Default' ) ">
															<xsl:if test="value[position()=1]='PageElement' and value[position()=3]=$id">
																<PageElement>
																	<xsl:attribute name="name"><xsl:value-of select="value[position()=5]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																</PageElement>
															</xsl:if>
														</xsl:for-each>
													</PageElements>
													
													<PreserveObjs>
														<xsl:for-each select="key('source-page-state','Default' )">
															<xsl:if test="value[position()=1]='PreserveObj'  and value[position()=3]=$id">
																<PreserveObj>
																	<xsl:attribute name="name"><xsl:value-of select="value[position()=10]"></xsl:value-of></xsl:attribute>
																</PreserveObj>
															</xsl:if>
														</xsl:for-each>
													</PreserveObjs>
													
													<TaskStarts>
														<xsl:for-each select="key('source-page-state','Default' )">
															<xsl:if test="value[position()=1]='TaskStart'  and value[position()=3]=$id">
																<TaskStart>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="taskName"><xsl:value-of select="value[position()=7]"></xsl:value-of></xsl:attribute>
																</TaskStart>
															</xsl:if>
														</xsl:for-each>
													</TaskStarts>
													
													<!--	don't know what to do for this one	-->
													<TaskEnds>
														<xsl:for-each select="key('source-page-state','Default' )">
															<xsl:if test="value[position()=1]='TaskEnd'  and value[position()=3]=$id">
																<TaskEnd>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="taskName"><xsl:value-of select="value[position()=7]"></xsl:value-of></xsl:attribute>
																</TaskEnd>
															</xsl:if>
														</xsl:for-each>
													</TaskEnds>
									
							</NBSPageContext>
							
							
								<!--	the over rides	-->
								<TaskOverrides>
									<xsl:for-each select="key('page-id',value[position()=3])">
										<!--	don't duplicate the overrides	-->
										<xsl:if test="not(value[position()=2]=preceding-sibling::line/value[position()=2]) and not(value[position()=2] = 'Default') ">
										
											<TaskOverride>
												<xsl:attribute name="taskName"><xsl:value-of select="value[position()=2]"></xsl:value-of></xsl:attribute>	
												<NBSPageContext>
													
													<!--xsl:variable name="id" select="value[position()=3]"></xsl:variable-->
													
													<PageElements>
														<xsl:for-each select="key('source-page-state',value[position()=2])">	
															<xsl:if test="value[position()=1]='PageElement' and value[position()=3]=$id">
																<PageElement>
																	<xsl:attribute name="name"><xsl:value-of select="value[position()=5]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																</PageElement>
															</xsl:if>
														</xsl:for-each>
													</PageElements>
													
													<PreserveObjs>
														<xsl:for-each select="key('source-page-state',value[position()=2])">
															<xsl:if test="value[position()=1]='PreserveObj'  and value[position()=3]=$id">
																<PreserveObj>
																	<xsl:attribute name="name"><xsl:value-of select="value[position()=10]"></xsl:value-of></xsl:attribute>
																</PreserveObj>
															</xsl:if>
														</xsl:for-each>
													</PreserveObjs>
													
													<TaskStarts>
														<xsl:for-each select="key('source-page-state',value[position()=2])">
															<xsl:if test="value[position()=1]='TaskStart'  and value[position()=3]=$id">
																<TaskStart>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="taskName"><xsl:value-of select="value[position()=7]"></xsl:value-of></xsl:attribute>
																</TaskStart>
															</xsl:if>
														</xsl:for-each>
													</TaskStarts>
													
													<!--	don't know what to do for this one	-->
													<TaskEnds>
														<xsl:for-each select="key('source-page-state',value[position()=2])">
															<xsl:if test="value[position()=1]='TaskEnd'  and value[position()=3]=$id">
																<TaskEnd>
																	<xsl:attribute name="action"><xsl:value-of select="value[position()=6]"></xsl:value-of></xsl:attribute>
																	<xsl:attribute name="taskName"><xsl:value-of select="value[position()=7]"></xsl:value-of></xsl:attribute>
																</TaskEnd>
															</xsl:if>
														</xsl:for-each>
													</TaskEnds>
												
												</NBSPageContext>
											</TaskOverride>
										
										</xsl:if>
										
									</xsl:for-each>
								</TaskOverrides>
							
						</NBSPage>
					</xsl:for-each>
			</NBSPages>
		</NBSContextMap>
			
	
		
		
	</xsl:template>
	
	<xsl:template name="remove-trailing-numbers">
		<xsl:param name="string"/>
		
		<!--xsl:value-of select="substring($string,1,string-length($string)-1)"></xsl:value-of-->

		
		<xsl:choose>
			
			<xsl:when test="not(string(number(substring($string,string-length($string))))='NaN' )">
				
				<xsl:call-template name="remove-trailing-numbers">
					<xsl:with-param name="string" select="substring($string,1,string-length($string)-1)"/>
				</xsl:call-template>
			</xsl:when>
		
		
			<xsl:otherwise>			
				<xsl:value-of select="$string"></xsl:value-of>
			</xsl:otherwise>
		
		
		</xsl:choose>
	</xsl:template>
	
	
</xsl:stylesheet>
