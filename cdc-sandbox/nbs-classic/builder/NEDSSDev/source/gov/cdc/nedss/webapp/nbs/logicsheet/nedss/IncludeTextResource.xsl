<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsp="http://apache.org/xsp" xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss" version="1.0">

    <!-- Similar to an XInclude, but the content is cached in the servlet context. -->
    <!-- Reads a file as a system resource and caches it. -->
    <xsl:template match="nedss:IncludeTextResource">
        <xsl:choose>
            <xsl:when test="@name">
                <xsp:logic>
                    final String CRLF = &quot;\r\n&quot;;
                    String s = null;
                    String strIssues = (String)context.getAttribute(&quot;<xsl:value-of select="@name"/>&quot;);
                    if(strIssues == null)
                    {
                        String strFilename = &quot;<xsl:value-of select="@name"/>&quot;;
                        try
                        {
                            Class c = this.getClass();
                            InputStream is = c.getResourceAsStream(strFilename);
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            StringBuffer sb = new StringBuffer();
                            while(true)
                            {
                                s = br.readLine();
                                if(s == null)
                                {
                                    break;
                                }
                                sb.append(s);
                                sb.append(CRLF);
                            }
                            strIssues = sb.toString();
                            context.setAttribute(&quot;<xsl:value-of select="@name"/>&quot;, strIssues);
                        }
                        catch(Exception ex)
                        {
                            logger.error(&quot;Unable to read text file:  &quot; + strFilename);
                        }
                    }
                    if(strIssues == null)
                    {
                        <empty/>
                    }
                    else
                    {
                        StringReader sr = new StringReader(strIssues);
                        BufferedReader brx = new BufferedReader(sr);
                        while(true)
                        {
                            s = brx.readLine();
                            if(s == null)
                            {
                                break;
                            }
                            <textnode><xsp:expr>s</xsp:expr></textnode><br/>
                        }
                    }
                </xsp:logic>
            </xsl:when>
            <!--
            &lt;xsl:otherwise&gt;
                &lt;xsl:call-template name=&quot;space&quot;/&gt;
            &lt;/xsl:otherwise&gt;
            -->
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>