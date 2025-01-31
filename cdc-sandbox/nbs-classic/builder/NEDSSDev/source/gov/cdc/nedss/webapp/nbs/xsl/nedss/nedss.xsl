<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <!-- Strip all whitespace. -->
    <xsl:strip-space elements="*"/>
    
    <!-- Write XML declaration. -->
<!--
    No longer needed.  Cocoon's XML serializer will do it now.
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no" standalone="no" omit-xml-declaration="no"/>
-->

    <!-- Include global stuff that can be used by other templates. -->
    <xsl:include href="globalConstants.xsl"/>
    <xsl:include href="globalFunctions.xsl"/>
    <xsl:include href="globalVerifiers.xsl"/>

    <!-- Include all other templates. -->
    <xsl:include href="body.xsl"/>
    <xsl:include href="box.xsl"/>
    <xsl:include href="br.xsl"/>
    <xsl:include href="button.xsl"/>
    <xsl:include href="checkbox.xsl"/>
    <xsl:include href="column.xsl"/>
    <xsl:include href="columnheader.xsl"/>
    <xsl:include href="columnheaders.xsl"/>
    <xsl:include href="combobox.xsl"/>
    <xsl:include href="conditional.xsl"/>
    <xsl:include href="demobar.xsl"/>
    <xsl:include href="empty.xsl"/>
    <xsl:include href="error.xsl"/>
    <xsl:include href="errorbar.xsl"/>
    <xsl:include href="event.xsl"/>
    <xsl:include href="format.xsl"/>
    <xsl:include href="frame.xsl"/>
    <xsl:include href="frameset.xsl"/>
    <xsl:include href="graphic.xsl"/>
    <xsl:include href="grid.xsl"/>
    <xsl:include href="head.xsl"/>
    <xsl:include href="hiddenfield.xsl"/>
    <xsl:include href="hr.xsl"/>
    <xsl:include href="hyperlink.xsl"/>
    <xsl:include href="hyperlinks.xsl"/>
    <xsl:include href="id.xsl"/>
    <xsl:include href="ids.xsl"/>
    <xsl:include href="iframe.xsl"/>
    <xsl:include href="jumpers.xsl"/>
    <xsl:include href="label.xsl"/>
    <xsl:include href="linkbar.xsl"/>
    <xsl:include href="listbox.xsl"/>
    <xsl:include href="navbar.xsl"/>
    <xsl:include href="option.xsl"/>
    <xsl:include href="options.xsl"/>
    <xsl:include href="page.xsl"/>
    <xsl:include href="parameter.xsl"/>
    <xsl:include href="parameters.xsl"/>
    <xsl:include href="part.xsl"/>
    <xsl:include href="radiobutton.xsl"/>
    <xsl:include href="radiobuttons.xsl"/>
    <xsl:include href="record.xsl"/>
    <xsl:include href="records.xsl"/>
    <xsl:include href="row.xsl"/>
    <xsl:include href="script.xsl"/>
    <xsl:include href="scriptfile.xsl"/>
    <xsl:include href="section.xsl"/>
    <xsl:include href="splitlabel.xsl"/>
    <xsl:include href="subsection.xsl"/>
    <xsl:include href="tab.xsl"/>
    <xsl:include href="table.xsl"/>
    <xsl:include href="tabs.xsl"/>
    <xsl:include href="text.xsl"/>
    <xsl:include href="textarea.xsl"/>
    <xsl:include href="textbox.xsl"/>
    <xsl:include href="textnode.xsl"/>
    <xsl:include href="toolbar.xsl"/>
    <xsl:include href="toolbarButton.xsl"/>
    <xsl:include href="toolbarDef.xsl"/>
    <xsl:include href="toolbarLeft.xsl"/>
    <xsl:include href="toolbarRight.xsl"/>

    <!-- Define root template. -->
    <xsl:template match="/">
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

    <!-- Ignore all undefined nodes. -->
    <xsl:template match="*"/>
    <xsl:template match="@*"/>
    <xsl:template match="text()"/>
    <xsl:template match="processing-instruction()"/>
    <xsl:template match="comment()"/>

</xsl:stylesheet>
