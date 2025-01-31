<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/jsp/tags.jsp" %>
<%@ page language="java" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page isELIgnored ="false" %>
    
<!-- Popup page header -->
   <h1  class="pageHeader"  style="padding: 0px; margin: 0px; font-size: 13px;">
<table role="presentation" class="nedssPageHeaderAndLogoTable">
    <tbody>
        <tr>
            <td>
                <!-- Page title -->
			    <logic:present name="PageTitle" scope="request">
			        <td class="pageHeader" style="padding-bottom:0px; margin-bottom:0px;">
			            <a name=pageTop>${fn:escapeXml(PageTitle)}</a>
			        </td>
			    </logic:present>
			                
			    <logic:notPresent name="PageTitle" scope="request">
			       <logic:present name="BaseForm">
			        <td class="pageHeader" style="padding-bottom:0px; margin-bottom:0px;">
			            <a name=pageTop>${fn:escapeXml(BaseForm.pageTitle)}</a>
			        </td>
			       </logic:present>
			    </logic:notPresent>    
            </td>
        </tr>
        <tr>
            <td style="padding:0px; margin:0px; height:9px;
                    background-image: url(dropshadow.gif); background-repeat: repeat-x">
            </td>
        </tr>
    </tbody>
</table>
</h1>