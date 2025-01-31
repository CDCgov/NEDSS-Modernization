<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Process the enclosed elements if the current mode is add. -->
    <xsl:template match="nedss:IfAdd">
        <xsp:logic>
        <![CDATA[
            if(strUserMode.equalsIgnoreCase("add"))
            {
        ]]>
        </xsp:logic>
        <xsl:apply-templates/>
        <xsp:logic>
        <![CDATA[
            }
        ]]>
        </xsp:logic>
    </xsl:template>

</xsl:stylesheet>
