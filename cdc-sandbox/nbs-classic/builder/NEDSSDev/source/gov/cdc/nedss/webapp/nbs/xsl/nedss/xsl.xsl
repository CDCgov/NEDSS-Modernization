<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="xsl:stylesheet">
        <xsl:variable name="xsldoc-global">
            Put package-style comments here.
        </xsl:variable>
        <xsl:variable name="xsldoc-general">
            A general description of what this template does.
        </xsl:variable>
        <xsl:variable name="xsldoc-xhtml">
            What this template will do for XHTML output
            that it won't do for any other output format.
        </xsl:variable>
        <xsl:variable name="xsldoc-fo">
            What this template will do for PDF output
            that it won't do for any other output format.
        </xsl:variable>
        <xsl:variable name="xsldoc-write">
            When a page is displayed in 'add' or 'edit' mode,
            how will this template deviate from the general comments.
        </xsl:variable>
        <xsl:variable name="xsldoc-read">
            When a page is displayed in 'view' or 'print' mode,
            how will this template deviate from the general comments.
        </xsl:variable>
        <xsl:variable name="xsldoc-seealso">
            What other templates are related to this one?
        </xsl:variable>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="xsl:template">
        <xsl:value-of select="@match"/>
    </xsl:template>

    <!-- Ignore all undefined nodes. -->
    <xsl:template match="*"/>
    <xsl:template match="@*"/>
    <xsl:template match="text()"/>
    <xsl:template match="processing-instruction()"/>
    <xsl:template match="comment()"/>

</xsl:stylesheet>
