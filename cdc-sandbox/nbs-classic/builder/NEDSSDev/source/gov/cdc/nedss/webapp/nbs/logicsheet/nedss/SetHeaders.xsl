<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Disable caching in the web browser. -->
    <xsl:template match="nedss:SetHeaders">
        <xsp:logic>
        <![CDATA[
            response.setDateHeader("Expires", -1);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
        ]]>
        </xsp:logic>
    </xsl:template>

</xsl:stylesheet>
