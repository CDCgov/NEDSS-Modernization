<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
version="1.0"
xmlns:ext="urn:ext"
xmlns:xalan="http://xml.apache.org/xalan"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
exclude-result-prefixes="ext xalan xsl"
>

    <xsl:template match="body">
        <xsl:call-template name="body">
            <xsl:with-param name="style" select="part/attributes/style"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="body">
        <xsl:param name="style"/>
        <xsl:variable name="border">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'border'"/>
                <xsl:with-param name="type" select="'binary'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="uids">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'uids'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="usermode">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'usermode'"/>
                <xsl:with-param name="type" select="'string'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="verbose">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'verbose'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:if test="$verbose = $true">
            <xsl:comment> body begin </xsl:comment>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$style = 'plain'">
                <xsl:call-template name="body-plain"/>
            </xsl:when>
            <xsl:when test="$style = 'nedss-nonavbar'">
                <xsl:call-template name="body-nedss">
                    <xsl:with-param name="navbar" select="$false"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="body-nedss">
                    <xsl:with-param name="navbar" select="$true"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$verbose = $true">
            <xsl:comment> body end </xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="body-plain">
        <xsl:variable name="border">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'border'"/>
                <xsl:with-param name="type" select="'binary'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="uids">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'uids'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="usermode">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'usermode'"/>
                <xsl:with-param name="type" select="'string'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="verbose">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'verbose'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <body>
            <xsl:attribute name="onload">
                <xsl:value-of select="'startCountdown(),body'"/>
                <xsl:value-of select="$underline"/>
                <xsl:value-of select="'onload(this);'"/>
            </xsl:attribute>
            <xsl:if test="part/event[@name = 'onkeyup']">
                <xsl:attribute name="onkeyup">
                    <xsl:value-of select="'if(is_ie){body_onkeyup(this)}else{body_onkeyup(event)}'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:variable name="code">
                <xsl:value-of select="'try { global_body_onload(); } catch(ex) { return; }'"/>
                <xsl:if test="part/event[@name = 'onload']">
                    <xsl:value-of select="part/event[@name = 'onload']"/>
                </xsl:if>
            </xsl:variable>
            <xsl:call-template name="event">
                <xsl:with-param name="id" select="'body'"/>
                <xsl:with-param name="name" select="'onload'"/>
                <xsl:with-param name="code" select="$code"/>
            </xsl:call-template>
            <xsl:if test="part/event[@name = 'onkeyup']">
                <xsl:call-template name="event">
                    <xsl:with-param name="id" select="'body'"/>
                    <xsl:with-param name="name" select="'onkeyup'"/>
                    <xsl:with-param name="code" select="part/event[@name = 'onkeyup']"/>
                </xsl:call-template>
            </xsl:if>
            <form>
                <xsl:attribute name="id">
                    <xsl:value-of select="'frm'"/>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:value-of select="'frm'"/>
                </xsl:attribute>
                <xsl:attribute name="method">
                    <xsl:value-of select="'post'"/>
                </xsl:attribute>
                <xsl:attribute name="enctype">
                    <xsl:value-of select="'application/x-www-form-urlencoded'"/>
                </xsl:attribute>
                <xsl:attribute name="target">
                    <xsl:value-of select="'_self'"/>
                </xsl:attribute>
                <xsl:attribute name="action">
                    <xsl:value-of select="'/nedss/nfc'"/>
                </xsl:attribute>
                <table border="0" cellpadding="0" cellspacing="0" summary="">
                    <tbody>
                        <tr>
                            <td class="Hidden">
                                <input type="hidden" id="id_mode" name="mode"/>
                                <input type="hidden" id="id_ObjectType" name="ObjectType"/>
                                <input type="hidden" id="id_OperationType" name="OperationType"/>
                                <input type="hidden" id="PopupDataResult" name="PopupDataResult" onfocus="PopupDataResult_onfocus(this);"/>
                                <xsl:call-template name="event">
                                    <xsl:with-param name="id" select="'PopupDataResult'"/>
                                    <xsl:with-param name="name" select="'onfocus'"/>
                                    <xsl:with-param name="code" select="'return;'"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" summary="">
                    <tbody>
                        <tr>
                            <td>
                                <xsl:apply-templates/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </body>
    </xsl:template>

    <xsl:template name="body-nedss">
        <xsl:param name="navbar"/>
        <xsl:variable name="border">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'border'"/>
                <xsl:with-param name="type" select="'binary'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="uids">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'uids'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="usermode">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'usermode'"/>
                <xsl:with-param name="type" select="'string'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="verbose">
            <xsl:call-template name="getParameter">
                <xsl:with-param name="name" select="'verbose'"/>
                <xsl:with-param name="type" select="'boolean'"/>
            </xsl:call-template>
        </xsl:variable>
        <body>
            <xsl:attribute name="onload">
                <xsl:value-of select="'startCountdown(),body'"/>
                <xsl:value-of select="$underline"/>
                <xsl:value-of select="'onload(this);'"/>
            </xsl:attribute>
            <xsl:if test="part/event[@name = 'onkeyup']">
                <xsl:attribute name="onkeyup">
                    <xsl:value-of select="'if(is_ie){body_onkeyup(this)}else{body_onkeyup(event)}'"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:variable name="code">
                <xsl:value-of select="'try { global_body_onload(); } catch(ex) { return; }'"/>
                <xsl:if test="part/event[@name = 'onload']">
                    <xsl:value-of select="part/event[@name = 'onload']"/>
                </xsl:if>
            </xsl:variable>
            <xsl:call-template name="event">
                <xsl:with-param name="id" select="'body'"/>
                <xsl:with-param name="name" select="'onload'"/>
                <xsl:with-param name="code" select="$code"/>
            </xsl:call-template>
            <xsl:if test="part/event[@name = 'onkeyup']">
                <xsl:call-template name="event">
                    <xsl:with-param name="id" select="'body'"/>
                    <xsl:with-param name="name" select="'onkeyup'"/>
                    <xsl:with-param name="code" select="part/event[@name = 'onkeyup']"/>
                </xsl:call-template>
            </xsl:if>
            <a name="top" id="id_top"/>
            <form>
                <xsl:attribute name="id">
                    <xsl:value-of select="'frm'"/>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:value-of select="'frm'"/>
                </xsl:attribute>
                <xsl:attribute name="method">
                    <xsl:value-of select="'post'"/>
                </xsl:attribute>
                <xsl:attribute name="enctype">
                    <xsl:value-of select="'application/x-www-form-urlencoded'"/>
                </xsl:attribute>
                <xsl:attribute name="target">
                    <xsl:value-of select="'_self'"/>
                </xsl:attribute>
                <xsl:attribute name="action">
                    <xsl:value-of select="'/nedss/nfc'"/>
                </xsl:attribute>
                <table border="0" cellpadding="0" cellspacing="0" summary="" width="760">
                    <tbody>
                        <tr>
                            <td class="Hidden">
                                <input type="hidden" id="id_mode" name="mode"/>
                                <input type="hidden" id="id_ObjectType" name="ObjectType"/>
                                <input type="hidden" id="id_OperationType" name="OperationType"/>
                                <input type="hidden" id="PopupDataResult" name="PopupDataResult" onfocus="PopupDataResult_onfocus(this);"/>
                                <xsl:call-template name="event">
                                    <xsl:with-param name="id" select="'PopupDataResult'"/>
                                    <xsl:with-param name="name" select="'onfocus'"/>
                                    <xsl:with-param name="code" select="'return;'"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <xsl:choose>
                    <xsl:when test="$usermode = $usermode_print">
                        <table border="0" cellpadding="0" cellspacing="0" summary="" width="750">
                            <tbody>
                                <tr>
                                    <td>
                                        <xsl:apply-templates/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </xsl:when>
                    <xsl:otherwise>
                        <table border="0" cellpadding="0" cellspacing="0" summary="" >
                            <tbody>
                                <tr>
                                    <xsl:if test="$navbar = $true">
                                        <td class="BodyLeft" valign="top">
                                            <table border="0" cellpadding="0" cellspacing="0" summary="" width="750">
                                                <tbody>
                                                    
                                                    <tr>
                                                        <td>
                                                            <xsl:call-template name="navbar"/>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                    </xsl:if>
                                    <!--td class="BodyMiddle" valign="top">
                                        <xsl:call-template name="space"/>
                                    </td-->
				</tr>
				<tr>
                                    <td class="BodyRight" valign="top">
                                        <table border="0" cellpadding="0" cellspacing="0" summary="" width="750">
                                            <tbody>
                                                
                                                <tr>
                                                    <td>
                                                        <table border="0" cellpadding="0" cellspacing="0" summary="" width="100%">
                                                            <tbody>
                                                            
                                                                		<tr>
												<td colspan="2" bgcolor="#003470" height="15" width="100%"></td>
												<td rowspan="2"  ><img src="../../images/nedssLogo.jpg" width="80" height="32" border="0" title="Logo" style="background : #DCDCDC"/></td>
											</tr>

                                                                		<tr>
												
												<td bgcolor="#DCDCDC" class="boldTwelveBlack">
												 	<xsl:call-template name="getParameter">
		                                                                            <xsl:with-param name="name" select="'title'"/>
		                                                                            <xsl:with-param name="type" select="'string'"/>
		                                                                        </xsl:call-template>
												</td>
												<xsl:variable name="user">
                                                                                        <xsl:call-template name="getParameter">
                                                                                            <xsl:with-param name="name" select="'user'"/>
                                                                                            <xsl:with-param name="type" select="'string'"/>
                                                                                        </xsl:call-template>
                                                                                    </xsl:variable>

												<td align="right" bgcolor="#003470" class="boldTenYellow">
													<xsl:if test="string-length($user)>0">
														User:  <xsl:value-of select="$user"/>
														 <xsl:call-template name="space"/> <xsl:call-template name="space"/>
													</xsl:if>
														


												</td>
											</tr>

                                                            
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <xsl:call-template name="graphic">
                                                            <xsl:with-param name="url" select="'dropshadow.gif'"/>
                                                            <xsl:with-param name="width" select="'100%'"/>
                                                            <xsl:with-param name="height" select="'9'"/>
                                                            <xsl:with-param name="type" select="'block'"/>
<!--
Include for NS, but not for IE.
                                                            <xsl:with-param name="type" select="'block'"/>
-->
                                                        </xsl:call-template>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <xsl:apply-templates/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </xsl:otherwise>
                </xsl:choose>
            </form>
        </body>
    </xsl:template>

</xsl:stylesheet>
