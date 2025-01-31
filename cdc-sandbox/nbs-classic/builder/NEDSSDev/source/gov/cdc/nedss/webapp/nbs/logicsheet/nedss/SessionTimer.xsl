<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Initializes the session timer. -->
    <xsl:template match="nedss:InitializeSessionTimer">
        <xsp:logic>
        <![CDATA[
            long lngSessionTimeoutSetting = 0;
            long lngSessionTimeoutTime = 0;
            long lngAccessed = session.getLastAccessedTime();
            long lngNow = System.currentTimeMillis();
            int intSeconds = session.getMaxInactiveInterval();
            if(intSeconds > 0)
            {
                lngSessionTimeoutSetting = (long)(intSeconds * 1000);
                lngSessionTimeoutTime = lngAccessed + lngSessionTimeoutSetting - lngNow;
            }
        ]]>
        </xsp:logic>
        <xsl:variable name="default">
            <xsl:value-of select="'1'"/>
        </xsl:variable>
        <xsl:variable name="freq">
            <xsl:choose>
                <xsl:when test="(@frequency) and (string(number(@frequency)) != 'NaN')">
                    <xsl:value-of select="round(floor(number(@frequency)))"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$default"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="lngSessionTimeoutSetting">
            <xsp:expr>lngSessionTimeoutSetting</xsp:expr>
        </xsl:variable>
        <xsl:variable name="lngSessionTimeoutTime">
            <xsp:expr>lngSessionTimeoutTime</xsp:expr>
        </xsl:variable>
        <xsl:variable name="SessionSetting">
            <xsl:choose>
                <xsl:when test="lngSessionTimeoutSetting">
                    <xsl:value-of select="lngSessionTimeoutSetting"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'0'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="SessionTime">
            <xsl:choose>
                <xsl:when test="lngSessionTimeoutTime">
                    <xsl:value-of select="lngSessionTimeoutTime"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'0'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="interval">
            <xsl:choose>
                <xsl:when test="($freq > 0) and (not($freq > 60))">
                    <xsl:value-of select="$freq"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$default"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <script>
            varSessionTimeoutSetting = <xsl:value-of select="$SessionSetting"/>;
            varSessionTimeoutTime = <xsl:value-of select="$SessionTime"/>;
            varSessionTimerInterval = <xsl:value-of select="$interval"/>;
        </script>
    </xsl:template>

    <!--
        This template only exists to demonstrate session timer functionality for beta testing.
        The session timer remains hidden until a special key is pressed.
        When this feature gets moved into production, we can stop calling this template
        and start calling the ToggleSessionTimer function from global_body_onload.
    -->
    <xsl:template match="nedss:CheckSessionTimer">
        <event name="onkeyup">
            CheckSessionTimer(_this);
        </event>
    </xsl:template>

</xsl:stylesheet>
