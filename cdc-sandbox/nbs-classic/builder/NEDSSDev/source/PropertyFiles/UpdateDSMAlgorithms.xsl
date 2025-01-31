<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lab="http://www.cdc.gov/NEDSS" version="1.0" exclude-result-prefixes="xsl lab">
	<xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="UTF-8"/>
	<xsl:strip-space elements="*"/>
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="lab:EventDateLogic">
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="not(lab:ElrTimeLogic)">
					<ElrTimeLogic>
						<ElrTimeLogicInd>
							<Code>Y</Code>
							<CodeDescTxt>Yes</CodeDescTxt>
						</ElrTimeLogicInd>
					</ElrTimeLogic>
					<xsl:apply-templates/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="lab:ElrAdvancedCriteria">
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="lab:EventDateLogic and lab:AndOrLogic and not(lab:InvLogic)">
					<xsl:apply-templates/>
					<InvLogic>
						<InvLogicInd>
							<Code>Y</Code>
							<CodeDescTxt>Yes</CodeDescTxt>
						</InvLogicInd>
					</InvLogic>
				</xsl:when>
				<xsl:when test="not(lab:EventDateLogic) and not(lab:InvLogic)">
					<EventDateLogic>
						<ElrTimeLogic>
							<ElrTimeLogicInd>
								<Code>N</Code>
								<CodeDescTxt>No</CodeDescTxt>
							</ElrTimeLogicInd>
						</ElrTimeLogic>
					</EventDateLogic>
					<xsl:apply-templates/>
					<InvLogic>
						<InvLogicInd>
							<Code>N</Code>
							<CodeDescTxt>No</CodeDescTxt>
						</InvLogicInd>
					</InvLogic>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="lab:ElrCriteria/lab:ElrCodedResultValue/lab:CodeDescTxt">
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="../lab:Code and not(contains(../lab:CodeDescTxt, concat('(',../lab:Code,')')))">
					<xsl:value-of select="concat(.,' (',../lab:Code,')')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
