<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsp="http://apache.org/xsp" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss">
	<xsl:strip-space elements="*"/>
	<xsl:template match="xsp:page">
		<xsp:page>
			<xsl:apply-templates select="@*"/>
			<xsp:structure>
				<xsp:include>gov.cdc.nedss.util.*</xsp:include>
			</xsp:structure>
			<xsp:logic><![CDATA[

				PropertyUtil propertyUtil = PropertyUtil.getInstance();
				// String variable is converted to integer. 
				// Cannot use getProperty method on propertyUtil for the above reason

			  private int getApprQueueForNotificationsDisplaySize() {
					return propertyUtil.getApprQueueForNotificationsDisplaySize();
				}
			  private int getRejectedQueueForNotificationsDisplaySize() {
					return propertyUtil.getRejectedQueueForNotificationsDisplaySize();
				}

			  private int getUpdQueueForNotificationsDisplaySize() {
					return propertyUtil.getUpdQueueForNotificationsDisplaySize();
				}

			]]></xsp:logic>
			<xsl:apply-templates/>
		</xsp:page>
	</xsl:template>


	<xsl:template match="nedss:getApprQueueForNotificationsDisplaySize">
		<xsp:expr>getApprQueueForNotificationsDisplaySize()</xsp:expr>
	</xsl:template>
	<xsl:template match="nedss:getUpdQueueForNotificationsDisplaySize">
		<xsp:expr>getUpdQueueForNotificationsDisplaySize()</xsp:expr>
	</xsl:template>
	<xsl:template match="nedss:getRejectedQueueForNotificationsDisplaySize">
		<xsp:expr>getRejectedQueueForNotificationsDisplaySize()</xsp:expr>
	</xsl:template>
</xsl:stylesheet>