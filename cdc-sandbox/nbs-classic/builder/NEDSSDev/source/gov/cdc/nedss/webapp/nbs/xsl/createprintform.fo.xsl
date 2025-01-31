<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<!--xmlns:svg="http://www.w3.org/2000/svg"-->
	<xsl:param name="page-title"/>
	<xsl:key name="header-elements" match="element[./header] | controller[./header] | partner[./header]" use="@name"/>
	<xsl:template match="/">
		<!-- defines the layout master -->
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="first" page-height="29.7cm" page-width="21cm" margin-top="1cm" margin-bottom="1cm" margin-left="0.5cm" margin-right="0.5cm">
					<fo:region-body margin-top="2.5cm" margin-bottom="3cm"/>
					<fo:region-before extent="12cm"/>
					<fo:region-after extent="2cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<!-- starts actual layout -->
			<fo:page-sequence master-reference="first">
				<fo:static-content flow-name="xsl-region-before" font-family="Times" font-size="12pt" font-weight="bold" font-style="italic" text-align="left">
					<!-- table start -->
					<fo:table table-layout="fixed" border-width="0mm" border-style="solid" width="100%">
						<fo:table-column column-width="50mm"/>
						<fo:table-column column-width="50mm"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell number-columns-spanned="2">
									<fo:block text-align="left">
										<xsl:choose>
											<xsl:when test="$page-title='View Morbidity Report'">
												<xsl:value-of select="'Morbidity Report'"/>
											</xsl:when>
											<xsl:when test="$page-title='View Lab Report'">
												<xsl:value-of select="'Lab Report'"/>
											</xsl:when>
											<xsl:when test="$page-title='View Vaccination'">
												<xsl:value-of select="'Vaccination'"/>
											</xsl:when>
											<xsl:when test="$page-title='View Treatment'">
												<xsl:value-of select="'Treatment'"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:for-each select=".//tab">
													<xsl:choose>
														<xsl:when test="contains($page-title,'View Investigation - Hepatitis') and not($page-title='View Investigation - Hepatitis Generic')">
															<xsl:if test="position()=3">
																<xsl:value-of select="concat('Investigation - ',@name)"/>
															</xsl:if>
														</xsl:when>
														<xsl:otherwise>
															<xsl:if test="position()=2">
																<xsl:value-of select="concat('Investigation - ',@name)"/>
															</xsl:if>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:for-each>
											</xsl:otherwise>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<xsl:for-each select=".//id-bar">
									<xsl:for-each select="element">
										<xsl:if test="@id[.='printUser']">
											<fo:table-cell>
												<fo:block text-align="left">
													User: <xsl:value-of select="value"/>
												</fo:block>
											</fo:table-cell>
										</xsl:if>
									</xsl:for-each>
									<xsl:for-each select="id">
										<xsl:if test="not(@name[. = 'Patient ID'])">
											<fo:table-cell>
												<fo:block text-align="right">
													<xsl:value-of select="@name"/>: <xsl:value-of select="."/>
												</fo:block>
											</fo:table-cell>
										</xsl:if>
									</xsl:for-each>
								</xsl:for-each>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<!-- table end -->
					<xsl:for-each select=".//topgroup">
						<xsl:if test="not(position() = 3)">
							<fo:table table-layout="fixed" width="100%">
								<fo:table-column/>
								<fo:table-column/>
								<fo:table-column/>
								<fo:table-body>
									<xsl:if test="./@anchor">
										<xsl:variable name="lastName">
											<xsl:value-of select="//element[@name='topGroupPatientLastName']"/>
										</xsl:variable>
										<xsl:for-each select="line/element">
											<xsl:if test="./@label and @name[.='topGroupPatientFirstName']">
												<fo:table-row>
													<fo:table-cell number-columns-spanned="2">
														<fo:block text-align="left">
															<!--xsl:call-template name="makeLabel">
															<xsl:with-param name="label" select="@label"/>
															<xsl:with-param name="subText" select="@subText"/>
														</xsl:call-template-->
															<xsl:choose>
																<xsl:when test="@name[.='topGroupPatientFirstName']">
																	<xsl:value-of select="concat(concat(value, ' '), $lastName)"/>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:value-of select="value"/>
																</xsl:otherwise>
															</xsl:choose>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block text-align="right">
															<xsl:value-of select="//page/content/id-bar/id/@name"/>: <xsl:value-of select="//page/content/id-bar/id/."/>
														</fo:block>
													</fo:table-cell>													
												</fo:table-row>
											</xsl:if>
											<xsl:if test="./@label and @name[.='patientName']">
												<fo:table-row>
													<fo:table-cell number-columns-spanned="2">
														<fo:block text-align="left">
															<xsl:value-of select="value"/>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block text-align="right">
															<xsl:value-of select="//page/content/id-bar/id/@name"/>: <xsl:value-of select="//page/content/id-bar/id/."/>
														</fo:block>
													</fo:table-cell>														
												</fo:table-row>
											</xsl:if>
											<xsl:if test="@name[.='printPatientNameForViewLab']">
												<fo:table-row>
													<fo:table-cell number-columns-spanned="2">
														<fo:block text-align="left">
															<xsl:value-of select="value"/>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block text-align="right">
															<xsl:value-of select="//page/content/id-bar/id/@name"/>: <xsl:value-of select="//page/content/id-bar/id/."/>
														</fo:block>
													</fo:table-cell>														
												</fo:table-row>
											</xsl:if>
										</xsl:for-each>
										<fo:table-row>
											<xsl:for-each select="line/element">
												<xsl:if test="@name[.='dateOfBirth']">
													<fo:table-cell>
														<fo:block text-align="left">
																DOB: <xsl:value-of select="value"/>
														</fo:block>
													</fo:table-cell>
												</xsl:if>
												<xsl:if test="@id[.='printAge']">
													<fo:table-cell>
														<fo:block text-align="center">
																Age: <xsl:value-of select="value"/>
														</fo:block>
													</fo:table-cell>
												</xsl:if>
												<xsl:if test="@name[.='patientCurrentSex']">
													<fo:table-cell>
														<fo:block text-align="right">Current Sex:
																<xsl:choose>
																<xsl:when test="(@type[.='select'] or @type[.='hidden']) and (string-length(value)>0)">
																	<xsl:value-of select="substring-after(substring-before(substring-after(srt-options-string,value), '|'), '$')"/>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:value-of select="value"/>
																</xsl:otherwise>
															</xsl:choose>
														</fo:block>
													</fo:table-cell>
												</xsl:if>
											</xsl:for-each>
										</fo:table-row>
									</xsl:if>
								</fo:table-body>
							</fo:table>
						</xsl:if>
					</xsl:for-each>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after" font-size="12pt" font-family="Times" font-weight="bold" font-style="italic" line-height="14pt">
					<fo:table table-layout="fixed" width="100%">
						<fo:table-column/>
						<fo:table-column/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<xsl:for-each select=".//group/line/element">
									<xsl:if test="@id[.='printDate']">
										<fo:table-cell>
											<fo:block text-align="left">
												<xsl:value-of select="value"/>
											</fo:block>
										</fo:table-cell>
									</xsl:if>
									<xsl:if test="@id[.='printTime']">
										<fo:table-cell>
											<fo:block text-align="center">
												<xsl:value-of select="value"/>
											</fo:block>
										</fo:table-cell>
									</xsl:if>
								</xsl:for-each>
								<fo:table-cell>
									<fo:block text-align="right">
									Page <fo:page-number/> of <fo:page-number-citation ref-id="last-page"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<!-- body starts from here -->
				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates/>
					<fo:block id="last-page"/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="group">
		<xsl:if test="@name">
			<fo:block font-weight="bold" background-color="#003470" color="#FFF200" text-align="left" padding-top="0pt">
				<xsl:value-of select="@name"/>
			</fo:block>
		</xsl:if>
		<xsl:if test="@label">
			<fo:block font-weight="bold" background-color="#e4ebf8" text-align="center" padding-top="0pt" font-size="11">
				<xsl:value-of select="@label"/>
			</fo:block>
		</xsl:if>
		<fo:block space-after.optimum="10pt" font-family="Helvetica" font-size="10pt">
			<xsl:call-template name="create-lines"/>
		</fo:block>
	</xsl:template>
	<xsl:template name="create-lines">
		<xsl:for-each select="./line">
			<xsl:choose>
				<xsl:when test="not(@abc)">
					<xsl:choose>
						<xsl:when test="not(@tie-to) or @tie-to[. = 'Edit|View'] or @tie-to[. = 'View']">
							<xsl:call-template name="setup-cell"/>
						</xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="@abc[.='T']">
					<xsl:call-template name="setup-cell"/>
				</xsl:when>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="setup-cell">
		<xsl:for-each select="./element">
			<xsl:choose>
				<xsl:when test="@type[. = 'line-separator']">
					<xsl:call-template name="line-separator"/>
				</xsl:when>
				<xsl:when test="@type[. = 'batch-entry']">
					<xsl:call-template name="batch-entry"/>
				</xsl:when>
				<xsl:when test="@type[. = 'entity-search']">
					<xsl:call-template name="entity-search"/>
				</xsl:when>
				<xsl:when test="@type[. = 'data-table']">
					<xsl:call-template name="data-table"/>
				</xsl:when>
				<xsl:when test="@type[. = 'enhanced-data-table']">
					<xsl:call-template name="enhanced-data-table"/>
				</xsl:when>
				<xsl:when test="@type[. = 'queue-data-table-contacttracing']">
					<xsl:call-template name="queue-data-table-contacttracing"/>
				</xsl:when>
				<xsl:when test="@type[. = 'conditional-entry']">
					<xsl:call-template name="conditional-entry"/>
				</xsl:when>
				<xsl:when test="@type[. = 'join']">
					<xsl:call-template name="create-lines"/>
				</xsl:when>
				<xsl:when test="@type[. = 'select']">
					<xsl:call-template name="element-select"/>
				</xsl:when>
				<xsl:when test="@type[. = 'checkbox']">
					<xsl:call-template name="checkbox"/>
				</xsl:when>
				<xsl:when test="@type[. = 'raw']">
					<xsl:call-template name="raw"/>
				</xsl:when>
				<xsl:when test="@type[. = 'group-separator']">
					<xsl:call-template name="group-separator"/>
				</xsl:when>
				<xsl:when test="@type[. = 'blue-bar']">
					<xsl:call-template name="blue-bar"/>
				</xsl:when>
				<xsl:when test="@type[. = 'subform-select']">
					<xsl:call-template name="subform-select"/>
				</xsl:when>
				<xsl:when test="@type[. = 'subform-text']">
					<xsl:call-template name="subform-text"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="@type or @label or ./value">
						<xsl:call-template name="label-value"/>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="blue-bar">
		<xsl:param name="delimiter" select="'&lt;br/&gt;'"/>
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-weight="bold" background-color="#e4ebf8" text-align="left">
							<xsl:choose>
								<xsl:when test="contains(@text, $delimiter)">
									<xsl:value-of select="concat(substring-before(@text,$delimiter), substring-after(@text, $delimiter))"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="@text"/>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="group-separator">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-weight="bold" background-color="#e4ebf8" text-align="center" padding-top="0pt" font-size="11">
							<fo:inline font-style="normal">
								<xsl:value-of select="substring-before(@light-blue-label,@makeItalic)"/>
							</fo:inline>
							<fo:inline font-style="italic">
								<xsl:value-of select="@makeItalic"/>
							</fo:inline>
							<fo:inline font-style="normal">
								<xsl:value-of select="substring-after(@light-blue-label,@makeItalic)"/>
							</fo:inline>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="makeLabel">
		<xsl:param name="label"/>
		<xsl:param name="subText"/>
		<xsl:param name="italicText"/>
		<xsl:variable name="label" select="normalize-space($label)"/>
		<xsl:variable name="cleanedSubText">
			<xsl:choose>
				<xsl:when test="contains(substring($subText,1,1),'[') and not(contains(substring($subText,string-length($subText),1),']'))">
					<xsl:value-of select="substring-after($subText,']')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$subText"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="contains($label, '*')">
			<xsl:attribute name="color">red</xsl:attribute>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="string-length($cleanedSubText)>0">
				<xsl:choose>
					<xsl:when test="contains($label,'?') or contains($cleanedSubText,'?')">
						<xsl:value-of select="concat(concat($label, ' '), $cleanedSubText)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat(concat($label, ' '), $cleanedSubText)"/>:	
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="contains($label,'?')">
						<fo:inline font-style="normal">
							<xsl:value-of select="substring-before($label,$italicText)"/>
						</fo:inline>
						<fo:inline font-style="italic">
							<xsl:value-of select="$italicText"/>
						</fo:inline>
						<fo:inline font-style="normal">
							<xsl:value-of select="substring-after($label,$italicText)"/>
						</fo:inline>
					</xsl:when>
					<xsl:when test="string-length($italicText)>0">
						<fo:inline font-style="normal">
							<xsl:value-of select="substring-before($label,$italicText)"/>
						</fo:inline>
						<fo:inline font-style="italic">
							<xsl:value-of select="$italicText"/>
						</fo:inline>
						<fo:inline font-style="normal">
							<xsl:value-of select="substring-after($label,$italicText)"/>:</fo:inline>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="string-length($label)>0">
							<xsl:value-of select="$label"/>:
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="checkbox">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block text-align="right">
							<xsl:if test="@label">
								<xsl:call-template name="makeLabel">
									<xsl:with-param name="label" select="@label"/>
									<xsl:with-param name="italicText" select="@makeItalic"/>
								</xsl:call-template>
							</xsl:if>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block text-align="left">
							<xsl:if test="string-length(options/option/value)>0">
								<xsl:choose>
									<xsl:when test="options/option/value[. = 'F']">
										<xsl:value-of select="concat('Do Not ', options/option/@label)"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="options/option/@label"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="line-separator">
		<fo:block text-align="left" font-weight="bold" font-size="12pt" space-before.optimum="12pt" space-after.optimum="0pt" color="#003470">
			<xsl:value-of select="@title"/>
		</fo:block>
		<fo:block text-align="center" space-after.optimum="12pt">
			<fo:leader leader-pattern="rule" rule-thickness="1.0pt" leader-length="100%" color="#C0C0C0"/>
		</fo:block>
	</xsl:template>
	<xsl:template name="raw">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<xsl:choose>
						<xsl:when test=".//font">
							<fo:table-cell>
								<fo:block font-style="italic" font-weight="bold">
									<xsl:if test=".//font/@class[.='boldTenRed']">
										<xsl:attribute name="color">red</xsl:attribute>
									</xsl:if>
									<xsl:value-of select=".//font/."/>
								</fo:block>
							</fo:table-cell>
						</xsl:when>
						<xsl:when test="./span/b">
							<fo:table-cell>
								<fo:block font-weight="bold">
									<xsl:for-each select="./span/b">
										<xsl:variable name="italic-text" select="./i"/>
										<fo:inline font-style="normal">
											<xsl:value-of select="substring-before(.,$italic-text)"/>
										</fo:inline>
										<fo:inline font-style="italic">
											<xsl:value-of select="$italic-text"/>
										</fo:inline>
										<fo:inline font-style="normal">
											<xsl:value-of select="substring-after(.,$italic-text)"/>
										</fo:inline>
									</xsl:for-each>
								</fo:block>
							</fo:table-cell>
						</xsl:when>
						<xsl:when test="./span/@class">
							<fo:table-cell>
								<fo:block font-weight="bold">
									<xsl:if test="./span/@class[.='boldTenRed']">
										<xsl:attribute name="color">red</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="span"/>
								</fo:block>
							</fo:table-cell>
						</xsl:when>
						<xsl:when test="./span">
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="span"/>
								</fo:block>
							</fo:table-cell>
						</xsl:when>
					</xsl:choose>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="label-value">
		<xsl:variable name="partner">
			<xsl:choose>
				<xsl:when test="./partner/@type[.='select']">
					<xsl:variable name="desc">
						<xsl:call-template name="srt-codes">
							<xsl:with-param name="delimiter" select="'|'"/>
							<xsl:with-param name="string" select="normalize-space(./partner/srt-options-string)"/>
							<xsl:with-param name="value" select="./partner/value"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:value-of select="$desc"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="./partner/value"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<fo:table table-layout="fixed" width="100%">
			<xsl:if test="ancestor::element/@background[. = 'gray']">
				<xsl:attribute name="background-color"><xsl:value-of select="'#C0C0C0'"/></xsl:attribute>
			</xsl:if>
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:if test="not(@type='hidden')">
					<fo:table-row>
						<xsl:if test="ancestor::group/@background[. = 'blue']">
							<xsl:attribute name="background-color"><xsl:value-of select="'#e4ebf8'"/></xsl:attribute>
						</xsl:if>
						<fo:table-cell padding="6pt">
							<fo:block text-align="right" font-weight="bold">
								<xsl:call-template name="makeLabel">
									<xsl:with-param name="label" select="@label"/>
									<xsl:with-param name="subText" select="@subText"/>
									<xsl:with-param name="italicText" select="@makeItalic"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="6pt">
							<fo:block>
								<xsl:choose>
									<xsl:when test="contains(./value, '&lt;br/&gt;')">
										<xsl:call-template name="multiple-value">
											<xsl:with-param name="string" select="./value"/>
											<xsl:with-param name="delimiter" select="'&lt;br/&gt;'"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:when test="@type[. = 'text']">
										<xsl:call-template name="text">
											<xsl:with-param name="partner" select="$partner"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:when test="string-length(./value)>0">
										<xsl:if test="./value!='null'">
											<xsl:value-of select="concat(concat(normalize-space(./value), ' '), $partner)"/>
										</xsl:if>
									</xsl:when>
								</xsl:choose>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="text">
		<xsl:param name="partner"/>
		<xsl:choose>
			<xsl:when test="./validation and not(./validation/@mask)">
				<xsl:choose>
					<xsl:when test="normalize-space(value)!='null' and string-length(value)>0 and @keep-decimal">
						<xsl:value-of select="concat(concat(normalize-space(./value), ' '), $partner)"/>
					</xsl:when>
					<xsl:when test="string(number(value))!='NaN' and contains(value, '.0')">
						<xsl:value-of select="concat(concat(substring-before(value,'.0'),' '), $partner)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="value!='null' and string-length(value)>0">
							<xsl:value-of select="concat(concat(normalize-space(value), ' '), $partner)" disable-output-escaping="yes"/>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="@default and string-length(value)=0">
						<xsl:value-of select="normalize-space(@default)"/>
					</xsl:when>
					<xsl:when test="normalize-space(value)!='null'  and string-length(value)>0 and @keep-decimal">
						<xsl:value-of select="concat(concat(normalize-space(value), ' '), $partner)"/>
					</xsl:when>
					<xsl:when test="contains(value,'.0' )">
						<xsl:value-of select="concat(concat(translate(value,'.0',''),' '), $partner)"/>
					</xsl:when>
					<xsl:when test="value!='null' and string-length(value)>0">
						<xsl:value-of select="concat(concat(normalize-space(value), ' '), $partner)" disable-output-escaping="yes"/>
					</xsl:when>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="multiple-value">
		<xsl:param name="string"/>
		<xsl:param name="delimiter"/>
		<xsl:param name="delimiter1" select="'&lt;b&gt;'"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<fo:table>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="substring-before($string,$delimiter)"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
				<xsl:call-template name="multiple-value">
					<xsl:with-param name="string" select="substring-after($string,$delimiter)"/>
					<xsl:with-param name="delimiter" select="'&lt;br/&gt;'"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains($string, $delimiter1) ">
				<xsl:call-template name="multiple-value-text">
					<xsl:with-param name="string" select="substring-after($string,$delimiter1)"/>
					<xsl:with-param name="boldornot" select="'true'"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="multiple-value-text">
		<xsl:param name="string"/>
		<xsl:param name="delimiter1" select="'&lt;b&gt;'"/>
		<xsl:param name="delimiter2" select="'&lt;/b&gt;'"/>
		<xsl:choose>
			<xsl:when test="contains(substring-after($string,$delimiter2), $delimiter1)">
				<fo:table width="40mm">
					<fo:table-column/>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell width="20mm">
								<fo:block text-align="left" wrap-option="wrap" font-weight="bold">
									<xsl:value-of select="substring-before($string,$delimiter2)"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell width="20mm">
								<fo:block text-align="left">
									<xsl:value-of select="substring-before(substring-after($string,$delimiter2), $delimiter1)"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
				<xsl:call-template name="multiple-value-text">
					<xsl:with-param name="string" select="substring-after($string, $delimiter1)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<fo:table width="40mm">
					<fo:table-column/>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell width="20mm">
								<fo:block text-align="left" wrap-option="wrap" font-weight="bold">
									<xsl:value-of select="substring-before($string,$delimiter2)"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell width="20mm">
								<fo:block text-align="left">
									<xsl:value-of select="substring-after($string,$delimiter2)"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="element-select">
		<xsl:variable name="select-id" select="@id"/>
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding="6pt">
						<fo:block text-align="right" font-weight="bold">
							<xsl:choose>
								<xsl:when test="string-length(normalize-space(@label))>0">
									<xsl:call-template name="makeLabel">
										<xsl:with-param name="label" select="@label"/>
										<xsl:with-param name="subText" select="@subText"/>
										<xsl:with-param name="italicText" select="@makeItalic"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="not(@label) and string-length(normalize-space(@print-label))>0">
									<xsl:call-template name="makeLabel">
										<xsl:with-param name="label" select="@print-label"/>
										<xsl:with-param name="subText" select="@subText"/>
										<xsl:with-param name="italicText" select="@makeItalic"/>
									</xsl:call-template>
								</xsl:when>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
					<xsl:variable name="desc">
						<xsl:call-template name="srt-codes">
							<xsl:with-param name="delimiter" select="'|'"/>
							<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
							<xsl:with-param name="value" select="value"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="normalize-space($desc)='' "/>
						<xsl:otherwise>
							<fo:table-cell padding="6pt">
								<fo:block>
									<xsl:choose>
										<xsl:when test="string-length(@id)>0 and following::element[@id=$select-id]"/>
										<xsl:otherwise>
											<xsl:value-of select="$desc"/>
										</xsl:otherwise>
									</xsl:choose>
								</fo:block>
							</fo:table-cell>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:if test="@size">
						<xsl:variable name="test">
							<xsl:call-template name="multiple-entry-selectbox-display">
								<xsl:with-param name="values" select="value"/>
								<xsl:with-param name="srts" select="normalize-space(srt-options-string)"/>
							</xsl:call-template>
						</xsl:variable>
						<fo:table-cell padding="6pt">
							<fo:block>
								<xsl:choose>
									<xsl:when test="starts-with(normalize-space($test),',')">
										<xsl:value-of select="substring(normalize-space(substring-after($test,',')),1,string-length(normalize-space(substring-after($test,',')))-	1)"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="substring(normalize-space($test),1,string-length(normalize-space($test))-1)"/>
									</xsl:otherwise>
								</xsl:choose>
							</fo:block>
						</fo:table-cell>
					</xsl:if>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="create-lines"/>
	</xsl:template>
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
						<xsl:value-of select="$desc"/>, 
					</xsl:otherwise>
				</xsl:choose>
				<xsl:call-template name="multiple-entry-selectbox-display">
					<xsl:with-param name="values" select="normalize-space(substring-after($values, '|'))"/>
					<xsl:with-param name="srts" select="normalize-space($srts)"/>
				</xsl:call-template>
			</xsl:when>
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
					<xsl:value-of select="substring-after($string, $delimiter)"/>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="entity-search">
		<fo:table table-layout="fixed" width="15cm">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:choose>
					<xsl:when test="count(line)>2">
						<xsl:for-each select="line">
							<xsl:if test="not(@tie-to[.='Create'])">
								<xsl:call-template name="entity-search-rows">
									<xsl:with-param name="elementsperline" select="count(./element)"/>
								</xsl:call-template>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="not(contains(descendant::element/value,'&lt;br/&gt;')) and (count(line)=1 or count(line)=2)">
						<xsl:for-each select="line">
							<xsl:if test="not(@tie-to[.='Create'])">
								<xsl:call-template name="entity-search-rows">
									<xsl:with-param name="elementsperline" select="count(./element)"/>
								</xsl:call-template>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="line/element">
							<xsl:choose>
								<xsl:when test="@label">
									<fo:table-row>
										<fo:table-cell>
											<fo:block font-weight="bold">
												<xsl:call-template name="makeLabel">
													<xsl:with-param name="label" select="@label"/>
													<xsl:with-param name="italicText" select="@makeItalic"/>
												</xsl:call-template>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</xsl:when>
							</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(value,'&lt;br/&gt;')">
									<xsl:call-template name="entity-search1">
										<xsl:with-param name="string" select="value"/>
									</xsl:call-template>
								</xsl:when>
							</xsl:choose>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="entity-search1">
		<xsl:param name="string"/>
		<xsl:param name="delimiter" select="'&lt;br/&gt;'"/>
		<xsl:variable name="bold-open" select="'&lt;b&gt;'"/>
		<xsl:variable name="bold-close" select="'&lt;/b&gt;'"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<fo:table-row>
					<fo:table-cell number-columns-spanned="4">
						<fo:block>
							<xsl:value-of select="substring-before($string,$delimiter)"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<xsl:call-template name="entity-search1">
					<xsl:with-param name="string" select="substring-after($string,$delimiter)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<fo:table-row>
					<fo:table-cell number-columns-spanned="4">
						<fo:block>
							<xsl:choose>
								<xsl:when test="contains($string, $bold-open)">
									<!--xsl:value-of select="concat(concat(substring-before($string, $bold-open), substring-before(substring-after($string,$bold-open), $bold-close)), substring-after($string, $bold-close))"/-->
									<xsl:value-of select="translate(translate($string,$bold-open,''), $bold-close,'')"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$string"/>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="entity-search-rows">
		<xsl:param name="elementsperline"/>
		<fo:table-row>
			<xsl:for-each select="./element">
				<fo:table-cell>
					<xsl:if test="$elementsperline=1">
						<xsl:attribute name="number-columns-spanned">2</xsl:attribute>
					</xsl:if>
					<fo:block font-weight="bold" text-align="right">
						<xsl:call-template name="makeLabel">
							<xsl:with-param name="label" select="normalize-space(@label)"/>
							<xsl:with-param name="subText" select="@subText"/>
							<xsl:with-param name="italicText" select="@makeItalic"/>
						</xsl:call-template>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<xsl:if test="$elementsperline=1">
						<xsl:attribute name="number-columns-spanned">2</xsl:attribute>
					</xsl:if>
					<fo:block font-weight="100" text-align="left">
						<xsl:choose>
							<xsl:when test="@type[.='raw']">
								<xsl:value-of select="./span"/>
							</xsl:when>
							<xsl:when test="@type[.='plain-text']">
								<xsl:value-of select="./value"/>
							</xsl:when>
							<xsl:when test="@type[.='select'] and string-length(./value)>0">
								<xsl:value-of select="substring-after(substring-before(substring-after(srt-options-string,value), '|'), '$')"/>
							</xsl:when>
						</xsl:choose>
					</fo:block>
				</fo:table-cell>
				<!--/xsl:if-->
			</xsl:for-each>
		</fo:table-row>
	</xsl:template>
	<xsl:template name="conditional-entry">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:for-each select="controller">
					<fo:table-row>
						<fo:table-cell padding="6pt">
							<fo:block text-align="right" font-weight="bold">
								<xsl:call-template name="makeLabel">
									<xsl:with-param name="label" select="@label"/>
									<xsl:with-param name="subText" select="@subText"/>
									<xsl:with-param name="italicText" select="@makeItalic"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
						<xsl:variable name="desc">
							<xsl:call-template name="srt-codes">
								<xsl:with-param name="delimiter" select="'|'"/>
								<xsl:with-param name="string" select="normalize-space(srt-options-string)"/>
								<xsl:with-param name="value" select="value"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:choose>
							<xsl:when test="normalize-space($desc)='' "/>
							<xsl:otherwise>
								<fo:table-cell padding="6pt">
									<fo:block>
										<xsl:value-of select="$desc"/>
									</fo:block>
								</fo:table-cell>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@size">
							<xsl:variable name="test">
								<xsl:call-template name="multiple-entry-selectbox-display">
									<xsl:with-param name="values" select="value"/>
									<xsl:with-param name="srts" select="normalize-space(srt-options-string)"/>
								</xsl:call-template>
							</xsl:variable>
							<fo:table-cell padding="6pt">
								<fo:block>
									<xsl:choose>
										<xsl:when test="starts-with(normalize-space($test),',')">
											<xsl:value-of select="substring(normalize-space(substring-after($test,',')),1,string-length(normalize-space(substring-after($test,',')))-	1)"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="substring(normalize-space($test),1,string-length(normalize-space($test))-1)"/>
										</xsl:otherwise>
									</xsl:choose>
								</fo:block>
							</fo:table-cell>
						</xsl:if>
					</fo:table-row>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="create-lines"/>
	</xsl:template>
	<xsl:template name="batch-entry">
		<xsl:variable name="level" select="count(ancestor::element[@type='batch-entry'])+1"/>
		<xsl:variable name="groupName" select="@name"/>
		<xsl:variable name="headers" select="descendant::element/header | descendant::controller/header | descendant::partner/header"/>
		<!--xsl:variable name="element-with-headers" select="descendant::element[./header]/@name | descendant::controller[./header]/@name | descendant::partner[./header]/@name"/-->
		<xsl:variable name="non-dup-headers" select="descendant::header[not(.=preceding::header[ancestor::element[@type='batch-entry']/@name=$groupName])]"/>
		<xsl:variable name="element-with-headers" select="descendant::element[./header[not(.=preceding::header)]] | descendant::controller[./header[not(.=preceding::header)]] | descendant::partner[./header[not(.=preceding::header)]]"/>
		<fo:table border="0" table-layout="fixed" width="80%" border-style="solid">
			<xsl:for-each select="$non-dup-headers">
				<fo:table-column width="40mm"/>
			</xsl:for-each>
			<fo:table-body>
				<fo:table-row>
					<xsl:for-each select="$non-dup-headers">
						<xsl:if test="$level=count(ancestor::element[@type='batch-entry'])">
							<fo:table-cell background-color="#C0C0C0">
								<xsl:if test="ancestor::line/@tie-to">
									<xsl:attribute name="tie-to"><xsl:value-of select="ancestor::line/@tie-to"/></xsl:attribute>
								</xsl:if>
								<fo:block font-weight="bold">
									<xsl:value-of select="normalize-space(.)"/>
								</fo:block>
							</fo:table-cell>
						</xsl:if>
					</xsl:for-each>
				</fo:table-row>
				<xsl:call-template name="fill-batch-entry-columns">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="value"/>
					<xsl:with-param name="lineCounter">0</xsl:with-param>
					<xsl:with-param name="element-with-headers" select="$element-with-headers"/>
				</xsl:call-template>
			</fo:table-body>
		</fo:table>
		<!--xsl:call-template name="create-lines"/-->
	</xsl:template>
	<xsl:template name="fill-batch-entry-columns">
		<xsl:param name="delimiter"/>
		<xsl:param name="string"/>
		<xsl:param name="lineCounter"/>
		<xsl:param name="element-with-headers"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:variable name="parsedString">
					<xsl:value-of select="substring-before($string, $delimiter)"/>
				</xsl:variable>
				<fo:table-row>
					<xsl:call-template name="make-row">
						<xsl:with-param name="parsedString" select="$parsedString"/>
						<xsl:with-param name="headerNames"/>
						<xsl:with-param name="lineCounter">
							<xsl:value-of select="$lineCounter + 1"/>
						</xsl:with-param>
						<xsl:with-param name="element-with-headers" select="$element-with-headers"/>
					</xsl:call-template>
				</fo:table-row>
				<xsl:call-template name="fill-batch-entry-columns">
					<xsl:with-param name="delimiter" select="'|'"/>
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="lineCounter" select="$lineCounter + 1"/>
					<xsl:with-param name="element-with-headers" select="$element-with-headers"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make-row">
		<xsl:param name="parsedString"/>
		<xsl:param name="lineCounter"/>
		<xsl:param name="element-with-headers"/>
		<xsl:variable name="active" select="not(substring-before(substring-after($parsedString, concat('statusCd', '~')), '^')='I')"/>
		<xsl:if test="$active">
			<xsl:for-each select="$element-with-headers">
				<xsl:choose>
					<xsl:when test="contains($parsedString,@name)">
						<xsl:variable name="batch-cell" select="substring-before(substring-after($parsedString, concat(@name, '~')), '^')"/>
						<fo:table-cell>
							<xsl:choose>
								<xsl:when test=" number($lineCounter) mod 2 ">
									<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<!--xsl:value-of select="."></xsl:value-of-->
							<xsl:choose>
								<!--do merge of headers for lab on morb-->
								<xsl:when test="normalize-space(header)='Result(s)' ">
									<xsl:variable name="dn" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName', '~')), '^')"/>
									<xsl:variable name="hc" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd', '~')), '^')"/>
									<xsl:variable name="c" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code', '~')), '^')"/>
									<xsl:variable name="nv" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue', '~')), '^')"/>
									<xsl:variable name="nu" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd', '~')), '^')"/>
									<xsl:variable name="vt" select="substring-before(substring-after($parsedString, concat('labResults_s[i].observationVO_s[1].obsValueTxtDT_s[0].valueTxt', '~')), '^')"/>
									<fo:block>
										<xsl:value-of select="substring-before(substring-after(concat('|',key('header-elements','labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName')/srt-options-string),concat('|',concat($dn,'$'))),'|')"/>
									</fo:block>
									<!--fo:block><xsl:value-of select="$hc"></xsl:value-of></fo:block-->
									<fo:block>
										<xsl:value-of select="substring-before(substring-after(concat('|',key('header-elements','labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code')/srt-options-string),concat('|',concat($c,'$'))),'|')"/>
									</fo:block>
									<fo:block>
										<xsl:value-of select="$nv"/>
										<xsl:value-of select="substring-before(substring-after(concat('|',key('header-elements','labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd')/srt-options-string),concat('|',concat($nu,'$'))),'|')"/>
									</fo:block>
									<!--fo:block></fo:block-->
									<fo:block>
										<xsl:value-of select="$vt"/>
									</fo:block>
								</xsl:when>
								<!--do the converstion of custom treatment-->
								<xsl:when test="normalize-space(header)='Treatment' ">
									<xsl:if test="normalize-space($batch-cell)='OTH' ">
										<fo:block>
											<xsl:value-of select="substring-before(substring-after($parsedString, concat('treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt', '~')), '^')"/>
										</fo:block>
									</xsl:if>
									<xsl:variable name="cdt" select="substring-before(substring-after($parsedString, concat('treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd', '~')), '^')"/>
									<xsl:if test="not(normalize-space($batch-cell)='OTH' )">
										<fo:block>
											<xsl:value-of select="substring-before(substring-after(concat('|',key('header-elements','treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd')/srt-options-string),concat('|',concat($cdt,'$'))),'|')"/>
										</fo:block>
									</xsl:if>
								</xsl:when>
								<xsl:when test="@type='select' ">
									<xsl:variable name="code-name" select="substring-before(substring-after(concat('|',srt-options-string),concat('|',concat($batch-cell,'$'))),'|')"/>
									<!--xsl:value-of select="substring-after(substring-before(substring-after(.././srt-options-string,$batch-cell), '|'), '$')"/-->
									<xsl:choose>
										<xsl:when test="contains($code-name,'+')">
											<fo:block>
												<xsl:value-of select="concat(substring-before($code-name,'+'),'+')"/>
											</fo:block>
											<fo:block inline-progression-dimension="true">
												<xsl:value-of select="substring-after($code-name,'+')"/>
											</fo:block>
										</xsl:when>
										<xsl:otherwise>
											<fo:block>
												<xsl:value-of select="$code-name"/>
											</fo:block>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="string-length(normalize-space($code-name))=0">
										<fo:block>
											<xsl:value-of select="$batch-cell"/>
										</fo:block>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<fo:block>
										<xsl:value-of select="$batch-cell"/>
									</fo:block>
								</xsl:otherwise>
							</xsl:choose>
						</fo:table-cell>
					</xsl:when>
					<xsl:otherwise>
						<fo:table-cell>
							<fo:block/>
						</fo:table-cell>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template name="data-table">
		<fo:table border="0" table-layout="fixed" width="100%" border-style="solid">
			<xsl:for-each select="header[not(position='bottom')]">
				<fo:table-column absolute-position="auto"/>
			</xsl:for-each>
			<fo:table-body>
				<fo:table-row>
					<xsl:for-each select="header[not(position='bottom')]">
						<fo:table-cell background-color="#C0C0C0">
							<fo:block font-weight="bold">
								<fo:inline text-decoration="underline">
									<xsl:value-of select="."/>
								</fo:inline>
							</fo:block>
						</fo:table-cell>
					</xsl:for-each>
				</fo:table-row>
				<xsl:choose>
					<xsl:when test="string-length(value)!=0">
						<xsl:call-template name="data-table-fill-rows">
							<xsl:with-param name="string" select="value"/>
							<xsl:with-param name="lineCounter">0</xsl:with-param>
							<xsl:with-param name="delimiter" select="'|'"/>
							<xsl:with-param name="coldelimiter" select=" '$' "/>
							<xsl:with-param name="rowPosition" select=" 'top' "/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<fo:table-row>
							<fo:table-cell number-columns-spanned="6">
								<fo:block font-weight="bold" text-align="center" background-color="#DCDCDC">
									There is no information to display
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="create-lines"/>
	</xsl:template>
	<xsl:template name="queue-data-table-contacttracing">
		<xsl:variable name="colCount" select="count(header/record[not(position='bottom')])"/>
		<fo:table border="0" table-layout="fixed" width="100%" border-style="solid">
			<xsl:for-each select="header[not(position='bottom')]">
				<fo:table-column/>
			</xsl:for-each>
			<fo:table-body>
				<fo:table-row>
					<xsl:for-each select="header[not(position='bottom')]">
						<fo:table-cell background-color="#C0C0C0">
							<fo:block font-weight="bold">
								<fo:inline text-decoration="underline">
									<xsl:value-of select="."/>
								</fo:inline>
							</fo:block>
						</fo:table-cell>
					</xsl:for-each>
				</fo:table-row>
				<xsl:choose>
					<xsl:when test="count(record) &gt; 0">
						<xsl:for-each select="record">
							<xsl:call-template name="enhanced-data-table-fill-rows-CT">
								<xsl:with-param name="colCount" select="$colCount"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<fo:table-row>
							<fo:table-cell>
								<xsl:attribute name="number-columns-spanned"><xsl:value-of select="$colCount"/></xsl:attribute>
								<fo:block font-weight="bold" text-align="center" background-color="#DCDCDC">
												There is no information to display
											</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="create-lines"/>
	</xsl:template>
	<xsl:template name="data-table-fill-rows">
		<xsl:param name="string"/>
		<xsl:param name="lineCounter"/>
		<xsl:param name="delimiter"/>
		<xsl:param name="coldelimiter"/>
		<xsl:param name="rowPosition"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter) ">
				<xsl:variable name="parsedString">
					<xsl:choose>
						<xsl:when test="contains(substring-before($string, $delimiter),'[[')">
							<xsl:value-of select="substring-before(substring-before($string, $delimiter),'[[')">						</xsl:value-of>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-before($string, $delimiter)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<fo:table-row>
					<xsl:choose>
						<xsl:when test="$rowPosition='bottom'"/>
						<xsl:when test=" number($lineCounter) mod 2 ">
							<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:call-template name="data-table-fill-columns">
						<xsl:with-param name="parsedString" select="$parsedString"/>
						<xsl:with-param name="delimiter">
							<xsl:value-of select="$coldelimiter"/>
						</xsl:with-param>
						<xsl:with-param name="lineCounter" select="$lineCounter + 1"/>
						<xsl:with-param name="rowPosition" select="$rowPosition"/>
					</xsl:call-template>
				</fo:table-row>
				<!-- add functionality for lab resulted test column -->
				<xsl:if test="contains(substring-before($string, $delimiter),'[[')">
					<fo:table-row>
						<xsl:choose>
							<xsl:when test="$rowPosition='bottom'"/>
							<xsl:when test=" number($lineCounter) mod 2 ">
								<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<fo:table-cell number-columns-spanned="{count(header)}">
							<fo:table border="0" table-layout="fixed" width="100%">
								<xsl:for-each select="footer">
									<fo:table-column/>
								</xsl:for-each>
								<fo:table-body>
									<!--fo:table-row>
											<fo:table-cell>
												<fo:block><xsl:value-of select="substring-after(substring-before($string, concat(']]',$delimiter)),'[[')"></xsl:value-of></fo:block>
											</fo:table-cell>
										</fo:table-row-->
									<xsl:call-template name="data-table-fill-rows">
										<xsl:with-param name="delimiter" select="'$'"/>
										<xsl:with-param name="string" select="substring-after(substring-before($string, $delimiter),'[[')"/>
										<xsl:with-param name="rowPosition" select=" 'bottom' "/>
										<xsl:with-param name="coldelimiter" select=" '~' "/>
									</xsl:call-template>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
				<xsl:call-template name="data-table-fill-rows">
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="lineCounter" select="$lineCounter + 1"/>
					<xsl:with-param name="delimiter" select="$delimiter"/>
					<xsl:with-param name="coldelimiter" select="$coldelimiter"/>
					<xsl:with-param name="rowPosition" select="$rowPosition"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="data-table-fill-columns">
		<xsl:param name="parsedString"/>
		<xsl:param name="delimiter"/>
		<xsl:param name="lineCounter"/>
		<xsl:param name="rowPosition"/>
		<xsl:if test="contains($parsedString,$delimiter)">
			<fo:table-cell>
				<fo:block>
					<xsl:choose>
						<xsl:when test="contains(substring-before($parsedString, $delimiter),'--')">
							<xsl:value-of select="translate(translate(substring-after(substring-before($parsedString, $delimiter),'--'),'--',''),'!','')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="translate(translate(substring-before($parsedString, $delimiter),'--',''),'!','')"/>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
			</fo:table-cell>
			<xsl:call-template name="data-table-fill-columns">
				<xsl:with-param name="parsedString" select="substring-after($parsedString, $delimiter)"/>
				<xsl:with-param name="delimiter" select="$delimiter"/>
				<xsl:with-param name="lineCounter" select="$lineCounter"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="enhanced-data-table">
		<xsl:variable name="colCount" select="count(header/data[not(position='bottom')])"/>
		<fo:table border="0" table-layout="fixed" width="100%" border-style="solid">
			<xsl:for-each select="header/data[not(position='bottom')]">
				<fo:table-column/>
			</xsl:for-each>
			<fo:table-body>
				<fo:table-row>
					<xsl:for-each select="header/data[not(position='bottom')]">
						<fo:table-cell background-color="#C0C0C0">
							<fo:block font-weight="bold">
								<fo:inline text-decoration="underline">
									<xsl:value-of select="."/>
								</fo:inline>
							</fo:block>
						</fo:table-cell>
					</xsl:for-each>
				</fo:table-row>
				<xsl:choose>
					<xsl:when test="count(record) &gt; 0">
						<xsl:for-each select="record">
							<xsl:call-template name="enhanced-data-table-fill-rows">
								<xsl:with-param name="colCount" select="$colCount"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<fo:table-row>
							<fo:table-cell>
								<xsl:attribute name="number-columns-spanned"><xsl:value-of select="$colCount"/></xsl:attribute>
								<fo:block font-weight="bold" text-align="center" background-color="#DCDCDC">
												No Records returned
											</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:otherwise>
				</xsl:choose>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="create-lines"/>
	</xsl:template>
	<xsl:template name="enhanced-data-table-fill-rows">
		<xsl:param name="colCount"/>
		<fo:table-row>
			<xsl:choose>
				<xsl:when test=" position() mod 2">
					<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:for-each select="./data">
				<fo:table-cell>
					<fo:block>
						<xsl:choose>
							<xsl:when test="element">
								<xsl:choose>
									<xsl:when test=".//element/@type[. = 'select'] and (string-length(./element/value)>0)">
										<xsl:value-of select="substring-after(substring-before(substring-after(./element/srt-options-string,./element/value), '|'), '$')"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="./element/value"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="."/>
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>
				</fo:table-cell>
			</xsl:for-each>
		</fo:table-row>
		<xsl:for-each select="recordComments">
			<fo:table-row>
				<xsl:choose>
					<xsl:when test=" position() mod 2">
						<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<fo:table-cell>
					<xsl:attribute name="number-columns-spanned"><xsl:value-of select="$colCount"/></xsl:attribute>
					<fo:block>
						<xsl:call-template name="makeLabel">
							<xsl:with-param name="label" select="@label"/>
							<xsl:with-param name="subText" select="@subText"/>
							<xsl:with-param name="italicText" select="@makeItalic"/>
						</xsl:call-template>
						<xsl:value-of select="./value"/>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="enhanced-data-table-fill-rows-CT">
		<xsl:param name="colCount"/>
		<fo:table-row>
			<xsl:choose>
				<xsl:when test=" position() mod 2">
					<xsl:attribute name="background-color">#DCDCDC</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:for-each select="./column">
				<fo:table-cell>
					<fo:block>
						<xsl:choose>
							<xsl:when test="element">
								<xsl:choose>
									<xsl:when test=".//element/@type[. = 'select'] and (string-length(./element/value)>0)">
										<xsl:value-of select="substring-after(substring-before(substring-after(./element/srt-options-string,./element/value), '|'), '$')"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="./element/value"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="."/>
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>
				</fo:table-cell>
			</xsl:for-each>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template name="subform-select">
		<xsl:variable name="subformId" select="@id"/>
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding="6pt">
						<fo:block text-align="right" font-weight="bold">
							<xsl:call-template name="makeLabel">
								<xsl:with-param name="label" select="@label"/>
							</xsl:call-template>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell padding="6pt">
						<fo:block>
							<xsl:call-template name="fetchCSFValues">
								<xsl:with-param name="subformId" select="$subformId"/>
							</xsl:call-template>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<xsl:template name="fetchCSFValues">
		<xsl:param name="subformId"/>
		<xsl:for-each select="//group/line[@id='csfhidden']/element[@id]">
			<xsl:choose>
				<xsl:when test="@id=$subformId">
					<xsl:variable name="test">
						<xsl:call-template name="multiple-entry-selectbox-display">
							<xsl:with-param name="values" select="value"/>
							<xsl:with-param name="srts" select="normalize-space(srt-options-string)"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="starts-with(normalize-space($test),',')">
							<xsl:value-of select="substring(normalize-space(substring-after($test,',')),1,string-length(normalize-space(substring-after($test,',')))-1)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring(normalize-space($test),1,string-length(normalize-space($test))-1)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="subform-text">
		<fo:table table-layout="fixed" width="100%">
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell padding="6pt">
						<fo:block text-align="right" font-weight="bold">
							<xsl:call-template name="makeLabel">
								<xsl:with-param name="label" select="@label"/>
							</xsl:call-template>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell padding="6pt">
						<fo:block>
							<xsl:value-of select="translate(./value,'|',',')"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>
