<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="yes"/>
	<xsl:template match="*">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="root">
		<group>
			<xsl:for-each select="StateDefinedDT">
				<xsl:if test="classCd='STATE' or classCd='DM' or classCd='CDC' and not(customSubformMetadataUid)">
					<xsl:attribute name="name"><xsl:value-of select="'Custom Fields'"/></xsl:attribute>
				</xsl:if>
			</xsl:for-each>
			<!-- XZ 01/14/2005		these are regular custom field	-->
			<xsl:for-each select="StateDefinedDT">
				<xsl:if test="not(customSubformMetadataUid) or customSubformMetadataUid='null'">
					<line>
						<element>
							<!--	size attribute	-->
							<xsl:if test="fieldSize != 'null' and string-length(fieldSize)>0">
								<xsl:attribute name="size"><xsl:value-of select="fieldSize"/></xsl:attribute>
							</xsl:if>
							<!--		label attribute		-->
							<xsl:choose>
								<xsl:when test="dataType='LNK' ">
									<xsl:call-template name="link-maker">
										<xsl:with-param name="label" select="labelTxt"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="dataType='SUB' ">
									<xsl:attribute name="title"><xsl:value-of select="labelTxt"/></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="label"><xsl:value-of select="labelTxt"/></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="string-length(labelTxt) > 35">
								<xsl:attribute name="align-label">left</xsl:attribute>
							</xsl:if>
							<!--		struts mapping name attribute		-->
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfValue</xsl:attribute>
							<xsl:attribute name="id">ldf[<xsl:value-of select="position()-1"/>].ldfValue</xsl:attribute>
							<xsl:attribute name="onchange"><xsl:value-of select="javaScriptFunctionName"/>()</xsl:attribute>
							<!--		type attribute		-->
							<xsl:choose>
								<!--		if SRT type		-->
								<xsl:when test="dataType='CV'">
									<xsl:attribute name="type">select</xsl:attribute>
									<srt-options-string>
										<xsl:value-of select="codeSetNm"/>
									</srt-options-string>
								</xsl:when>
								<!--		if Date  type		-->
								<xsl:when test="validationTxt='TS' ">
									<xsl:attribute name="mask">##/##/####</xsl:attribute>
									<xsl:attribute name="type">text</xsl:attribute>
									<xsl:attribute name="size">10</xsl:attribute>
								</xsl:when>
								<!--		if text  type		-->
								<xsl:when test="dataType='ST'">
									<xsl:attribute name="type">text</xsl:attribute>
									<xsl:attribute name="keep-decimal">true</xsl:attribute>
								</xsl:when>
								<!--		if textarea  type		-->
								<xsl:when test="dataType='LIST_ST' ">
									<xsl:attribute name="type">textarea</xsl:attribute>
								</xsl:when>
								<!--		if sub heading  type		-->
								<xsl:when test="dataType='SUB' ">
									<xsl:attribute name="type">line-separator</xsl:attribute>
								</xsl:when>
								<!-- type for CDFs -->
								<!--xsl:when test="customSubformMetadataUid='null'">
								<xsl:attribute name="type">text</xsl:attribute>
							</xsl:when-->
								<xsl:otherwise>
									<xsl:attribute name="type"><xsl:value-of select="type"/></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<!--		validation attribute		-->
							<validation>
								<!--xsl:choose-->
								<xsl:if test="requiredInd='T' ">
									<xsl:attribute name="type">required</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
								</xsl:if>
								<!--	if Date -->
								<xsl:if test="validationTxt='TS' ">
									<xsl:attribute name="type">date</xsl:attribute>
									<xsl:attribute name="mask">mm/dd/yyyy</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									<!--if Date is required field-->
									<xsl:if test="requiredInd='T' ">
										<xsl:attribute name="type">requiredDate</xsl:attribute>
										<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									</xsl:if>
								</xsl:if>
								<!--	if int -->
								<xsl:if test="validationTxt='INT' ">
									<xsl:attribute name="type">numeric</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									<!--if Integer is required field-->
									<xsl:if test="requiredInd='T' ">
										<xsl:attribute name="type">requiredNumber</xsl:attribute>
										<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									</xsl:if>
								</xsl:if>
								<!-- custom javascript -->
								<xsl:if test="validationTxt='custom' ">
									<xsl:attribute name="type"><xsl:value-of select="javaScriptFunctionName"/></xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									<xsl:attribute name="userDefinedJS">true</xsl:attribute>
								</xsl:if>
								<!-- ALPHA ONLY  -->
								<xsl:if test="validationTxt='ALPHA' ">
									<xsl:attribute name="type">alpha</xsl:attribute>
									<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									<!--if Integer is required field-->
									<xsl:if test="requiredInd='T' ">
										<xsl:attribute name="type">requiredAlpha</xsl:attribute>
										<xsl:attribute name="name"><xsl:value-of select="labelTxt"/></xsl:attribute>
									</xsl:if>
								</xsl:if>
							</validation>
							<!-- The value of LDF data, if present.-->
							<xsl:element name="value">
								<xsl:value-of select="ldfValue"/>
							</xsl:element>
						</element>
						<element>
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfUid</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="ldfUid"/>
							</xsl:element>
						</element>
						<!-- AKM 9/26 - add more attributes so that action classes can inspect them -->
						<!-- Business object name -->
						<element>
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].businessObjNm</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="businessObjNm"/>
							</xsl:element>
						</element>
						<!-- AKM 9/26 - add more attributes so that action classes can inspect them -->
						<!-- Condition code -->
						<xsl:element name="element">
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].conditionCd</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="conditionCd"/>
							</xsl:element>
						</xsl:element>
					</line>
				</xsl:if>
			</xsl:for-each>
			<line id="csfhidden">
				<xsl:for-each select="StateDefinedDT">
					<xsl:if test="customSubformMetadataUid!='null'">
						<element>
							<!--		struts mapping name attribute		-->
							<xsl:choose>
								<xsl:when test="dataType='CV'">
									<xsl:attribute name="name"><xsl:value-of select="ldfOid"/></xsl:attribute>
									<xsl:attribute name="id"><xsl:value-of select="ldfOid"/></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfValue</xsl:attribute>
									<xsl:attribute name="id"><xsl:value-of select="ldfOid"/></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<!--		type attribute		-->
							<xsl:choose>
								<!--		if SRT type		-->
								<xsl:when test="dataType='CV' ">
									<xsl:attribute name="type">select</xsl:attribute>
									<xsl:attribute name="autocomplete">disable</xsl:attribute>
									<srt-options-string>
										<xsl:value-of select="codeSetNm"/>
									</srt-options-string>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="type">hidden</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<!-- The value of LDF data, if present.-->
							<xsl:element name="value">
								<xsl:value-of select="ldfValue"/>
							</xsl:element>
						</element>
						<element>
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfUid</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="ldfUid"/>
							</xsl:element>
						</element>
						<!-- XZ 01/12/2005 - add more attributes to store multiselected codes for subform -->
						<xsl:if test="dataType='CV'">
							<element>
								<xsl:attribute name="type">hidden</xsl:attribute>
								<xsl:attribute name="id"><xsl:value-of select="ldfOid"/>.value</xsl:attribute>
								<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfValue</xsl:attribute>
								<xsl:element name="value">
									<xsl:value-of select="ldfValue"/>
								</xsl:element>
							</element>
						</xsl:if>
						<!-- AKM 9/26 - add more attributes so that action classes can inspect them -->
						<!-- Business object name -->
						<element>
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].businessObjNm</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="businessObjNm"/>
							</xsl:element>
						</element>
						<!-- AKM 9/26 - add more attributes so that action classes can inspect them -->
						<!-- Condition code -->
						<xsl:element name="element">
							<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].conditionCd</xsl:attribute>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:element name="value">
								<xsl:value-of select="conditionCd"/>
							</xsl:element>
						</xsl:element>
					</xsl:if>
				</xsl:for-each>
			</line>
		</group>
	</xsl:template>
	<xsl:template name="link-maker">
		<xsl:param name="label"/>
		<xsl:attribute name="name">ldf[<xsl:value-of select="position()-1"/>].ldfValue</xsl:attribute>
		<xsl:attribute name="type">hyper-link</xsl:attribute>
		<href>
			<xsl:value-of select="substring-before(substring-after($label,'('),')')"/>
		</href>
		<text>
			<xsl:value-of select="substring-before($label,'(')"/>
		</text>
		<defined-field-link/>
	</xsl:template>
</xsl:stylesheet>
