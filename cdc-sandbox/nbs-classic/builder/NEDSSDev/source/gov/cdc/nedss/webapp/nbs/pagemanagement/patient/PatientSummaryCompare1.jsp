<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="eCSStender.js"></script>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %> 
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>  
<jsp:useBean id="BaseForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.util.BaseForm" />
                
<!-- Page Meta data Block -->

<table role="presentation" width="100%" class="OrangeSection">
	<tr>
		<td class="OrangeHeader" >
			<div id="investigationName">${condition}
				(${createdDate})</div></td>
		<td align="right">
		<logic:equal name="BaseForm" property="mode" value="print">
			<input type="checkbox" id="survivingRecord" title="surviving Record" 
				name="survivingRecord" disabled="disabled" value="">
		</logic:equal>
		<logic:notEqual name="BaseForm" property="mode" value="print">
					<input type="checkbox" id="survivingRecord" title="surviving Record" 
				name="survivingRecord" value="">
		</logic:notEqual>
			<label id="survivingRecordL">Surviving
				Record</label></td>
	</tr>
</table>
 <table role="presentation" class="style">
    <!-- Page Errors -->  
    <div style="width:98%;">
    	<%@ include file="/jsp/feedbackMessagesBar.jsp" %>
    </div>
	<!-- Error Messages using Action Messages-->
	<div id="globalFeedbackMessagesBar" class="screenOnly" style="width:98%;">
		<logic:messagesPresent name="error_messages">
			<div class="infoBox errors" id="errorMessages">
				<b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				   <ul>
				   	<html:messages id="msg" name="error_messages">
				    	<li> <bean:write name="msg" /> </li>
				    </html:messages>
				   <ul>
			</div>
		</logic:messagesPresent>
	</div> 
				  
   <tr class="cellColor">
        <td class="border" colspan="2">
            <%
            String name = (String) request.getAttribute("patientLocalName");
            if( name == null || name.trim().length() ==0)
            	name = "---";
            name = name.trim(); 
            request.setAttribute("name", name);
            String suffix = (String) request.getAttribute("patientSuffixName");
            if( suffix == null || suffix.trim().length() ==0)
            	suffix="";
            
            if (suffix.length() != 0)
            {
            	name = name + ", "+suffix;
            }
            
            String currentSex = (String) request.getAttribute("patientCurrSex");
            if( currentSex == null || currentSex.trim().length() ==0)
            	currentSex= "---";
            currentSex = currentSex.trim(); 
            request.setAttribute("currentSex", currentSex);
            String DOB = (String) request.getAttribute("patientDOB");
            if( DOB == null || DOB.trim().length() ==0)
            	DOB= "---";
           request.setAttribute("DOB", DOB);
         %>
            <span class="valueTopLine"> ${fn:escapeXml(name)}</span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine"> ${fn:escapeXml(currentSex)} </span>
            <span class="valueTopLine">|</span>
       		<%if(request.getAttribute("currentAgeUnitCd") != null){%>
            <span class="valueTopLine"> ${fn:escapeXml(DOB)} (<%=HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge")))!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge"))):""%><%=HTMLEncoder.encodeHtml(CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT"))%>) </span>
            <%}else{ %>
            <span class="valueTopLine"> ${fn:escapeXml(DOB)} </span>
            <%} %>
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Patient ID: </span>
            <span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(BaseForm.attributeMap.patientLocalId)}</span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
    </tr>
     <% if (BaseForm.getActionMode().equals("Edit") ||
           BaseForm.getActionMode().equals("View") ||
           BaseForm.getActionMode().equals("EDIT_SUBMIT") ||
           BaseForm.getActionMode().equals("Compare") ||
           BaseForm.getActionMode().equals("Merge")) { %>
     <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Investigation ID: </span>
		    <span class="value">${fn:escapeXml(BaseForm.attributeMap.caseLocalId)}</span>
		    <%String coInfectionConditionList="";
		    	coInfectionConditionList=(String)BaseForm.getAttributeMap().get("coInfectionConditionList");
		    	if (coInfectionConditionList != null && !coInfectionConditionList.isEmpty()){%>
		    <span class="value">|</span>
		    <span class="value">${fn:escapeXml(BaseForm.attributeMap.coInfectionID)}</span>
		    <%} %>
		</td>
		<td class="border2">
		    <span class="label"> Created: </span>
		    <span class="value">${fn:escapeXml(createdDate)}</span>
		</td>
		<td class="border3">
		    <span class="label">By: </span>
		    <span class="value">${fn:escapeXml(createdBy)}</span>
		</td> 
    </tr>
    
    <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Investigation Status: </span>
		    <span class="value" id="headerInvestigationStatus">${fn:escapeXml(BaseForm.attributeMap.investigationStatus)}</span>
		</td>
		<td class="border2">
		    <span class="label"> Last Updated: </span>
		    <span class="value">${fn:escapeXml(updatedDate)}</span>
		</td>
		<td class="border3">
		    <span class="label"> By: </span>
            <span class="value">${fn:escapeXml(updatedBy)}</span>
        </td>
    </tr>
    <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Investigator: </span>
		    <span class="value" id="headerCurrentInvestigator">${fn:escapeXml(BaseForm.attributeMap.investigatorName)}</span>
		</td>
		<td class="border2">
		    <span class="label"> Case Status: </span>
            <span class="value" id="headerCaseStatus">${fn:escapeXml(BaseForm.attributeMap.caseClassCd)}</span>
		</td>
		<td class="border3">
		    <span class="label">Notification Status: </span>
		    <span class="value" id="patientSummaryJSP_view_notificationStatus">
              <% 
                  String notificationStatus = (String) request.getAttribute("notificationStatus");
                  if( notificationStatus == null || "null".equals(notificationStatus) || notificationStatus.trim().length()==0)
	              	notificationStatus= "";
                  
                  notificationStatus = notificationStatus.trim(); 
                  request.setAttribute("notificationStatus", notificationStatus);
                  if (notificationStatus.length() != 0) { 
              %>  
                  ${fn:escapeXml(notificationStatus)}
              <% } %>                                    
          </span>
		</td>
    </tr>
    <% } %>
    <tr style="display:none">
    	<td><span class="value" id="headerConditionCode">${fn:escapeXml(BaseForm.attributeMap.headerConditionCode)}</span>
    	<td><span class="value" id="headerProcessingDecision">${fn:escapeXml(BaseForm.attributeMap.ProcessingDecision)}</span>
    </td></tr>

</table>
