<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xalan:component prefix="ext" elements="textnode">
        <xalan:script lang="javascript">
            function textnode(c, e)
            {
                var t = e.getFirstChild();
                if(t == null)
                {
                    return null;
                }
                var s = t.getNodeValue();
                return "OK_" + s;
            }
        </xalan:script>
    </xalan:component>

    <xalan:component prefix="ext" functions="test">
        <xalan:script lang="javascript">
            function test()
            {
            }
        </xalan:script>
    </xalan:component>

</xsl:stylesheet>
