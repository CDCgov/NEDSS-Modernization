<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- This file is no longer in use. But keep it because we might use it again in the future. -->

    <xsl:template match="nedss:getObsIndex">
        <xsp:logic>
            int i = 0;
            String strObsIndex = (String)request.getAttribute("ObsIndex");
            if(strObsIndex != null)
            {
                try
                {
                    i = Integer.parseInt(strObsIndex);
                }
                catch(Exception ex)
                {
                    logger.fatal("Unable to parse ObsIndex.", ex);
                }
            }
        </xsp:logic>
    </xsl:template>

    <xsl:template match="nedss:setObsIndex">
        <xsp:logic>
            request.setAttribute("ObsIndex", String.valueOf(i));
        </xsp:logic>
    </xsl:template>

<!--
    <xsl:template match="nedss:ObsValueCoded">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueCodedDT_s[0].code</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="(@srt) and (ancestor::listbox/@uid)">
            <xsl:variable name="uid">
                <xsl:value-of select="ancestor::listbox/@uid"/>
            </xsl:variable>
            <srt-options-string>
                <srt-codes:getCodedValues>
                    <type><xsl:value-of select="@srt"/></type>
                </srt-codes:getCodedValues>
            </srt-options-string>
            <value>
                <xsp:logic>
                    if(cdv != null)
                    {
                        System.out.println("nedss:ObsValueCoded = " + cdv.getCodedValues("<xsl:value-of select="$uid"/>"));
                </xsp:logic>
                <xsp:expr>cdv.getCodedValues("<xsl:value-of select="$uid"/>")</xsp:expr>
                <xsp:logic>
                    }
                </xsp:logic>
            </value>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueDateFrom">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueDateDT_s[0].fromTime_s</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-fromTime'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueDateTo">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueDateDT_s[0].toTime_s</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-toTime'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueDateDurationAmt">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueDateDT_s[0].durationAmt</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-durationAmt'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueDateDurationUnitCd">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueDateDT_s[0].durationUnitCd</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-durationUnitCd'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueNumeric1">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueNumericDT_s[0].numericValue1_s</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-numericValue1'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueNumericUnitCd">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueNumericDT_s[0].numericUnitCd</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-numericUnitCd'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="nedss:ObsValueTxt">
        <xsp:attribute name="name">proxy.observationVO_s[<xsp:expr>i</xsp:expr>].obsValueTxtDT_s[0].valueTxt</xsp:attribute>
        <xsp:attribute name="struts">proxy.observationVO_s[<xsp:expr>i++</xsp:expr>].theObservationDT.cd</xsp:attribute>
        <xsl:if test="parent::*/@uid">
            <xsl:variable name="name">
                <xsl:value-of select="parent::*/@uid"/>
                <xsl:value-of select="'-valueTxt'"/>
            </xsl:variable>
            <xsp:expr>request.getAttribute("<xsl:value-of select="$name"/>")</xsp:expr>
        </xsl:if>
    </xsl:template>
-->

</xsl:stylesheet>
