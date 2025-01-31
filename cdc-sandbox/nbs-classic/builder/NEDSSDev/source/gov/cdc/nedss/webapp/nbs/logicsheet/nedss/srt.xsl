<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- This file is no longer in use. But keep it because we might use it again in the future. -->

<!--
    <xsl:template match="nedss:DataValue">
        <xsl:if test="(@srt) and (parent::*/@uid)">
            <data>
                <xsp:expr>cdv.getCodedValues("<xsl:value-of select="@srt"/>")</xsp:expr>
            </data>
            <value>
                <xsp:expr>request.getAttribute("<xsl:value-of select="parent::*/@uid"/>")</xsp:expr>
            </value>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:srt">
        <xsl:if test="@uid and @srt">
            <xsp:logic>
                if(cdv != null)
                {
                    TreeMap tm<xsl:value-of select="@uid"/> = cdv.getCodedValuesAsTreeMap("<xsl:value-of select="@srt"/>");
                    ArrayList al<xsl:value-of select="@uid"/> = (ArrayList)request.getAttribute("<xsl:value-of select="@uid"/>");
                    Set set<xsl:value-of select="@uid"/> = tm<xsl:value-of select="@uid"/>.keySet();
                    Iterator i<xsl:value-of select="@uid"/> = set<xsl:value-of select="@uid"/>.iterator();
                    while(i<xsl:value-of select="@uid"/>.hasNext())
                    {
                        String strKey = (String)i<xsl:value-of select="@uid"/>.next();
                        String strVal = (String)tm<xsl:value-of select="@uid"/>.get(strKey);
                        String strSel = "false";
                        if(al<xsl:value-of select="@uid"/> != null)
                        {
                            if(al<xsl:value-of select="@uid"/>.contains(strVal))
                            {
                                strSel = "true";
                            }
                        }
            </xsp:logic>
            <xsp:element name="option">
                <xsp:attribute name="name"><xsp:expr>strKey</xsp:expr></xsp:attribute>
                <xsp:attribute name="value"><xsp:expr>strVal</xsp:expr></xsp:attribute>
                <xsp:attribute name="selected"><xsp:expr>strSel</xsp:expr></xsp:attribute>
            </xsp:element>
            <xsp:logic>
                    }
                }
            </xsp:logic>
        </xsl:if>
    </xsl:template>
-->

</xsl:stylesheet>
