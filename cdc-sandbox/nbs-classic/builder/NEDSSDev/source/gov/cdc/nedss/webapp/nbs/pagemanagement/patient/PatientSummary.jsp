<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<script type="text/javascript" src="eCSStender.js"></script>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>   
<jsp:useBean id="PageForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.page.PageForm" />
                
<!-- Page Meta data Block -->

 <table role="presentation" class="style">
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
            <span class="valueTopLine"> ${fn:escapeXml(name)} </span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine"> ${fn:escapeXml(currentSex)} </span>
            <span class="valueTopLine">|</span>
       		<%if(request.getAttribute("currentAgeUnitCd") != null){%>
            <span class="valueTopLine"> ${fn:escapeXml(DOB)} (<%= HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge")))!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge"))):""%> <%=HTMLEncoder.encodeHtml(CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT"))%>) </span>
            <%}else{ %>
            <span class="valueTopLine"> ${fn:escapeXml(DOB)}  </span>
            <%} %>
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Patient ID: </span>
            <span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(PageForm.attributeMap.patientLocalId)} </span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
    </tr>
     <% if (PageForm.getActionMode().equals("Edit") ||
           PageForm.getActionMode().equals("View") ||
           PageForm.getActionMode().equals("EDIT_SUBMIT")) { %>
     <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Investigation ID: </span>
		    <span class="value">${fn:escapeXml(PageForm.attributeMap.caseLocalId)}</span>
		    <%String coInfectionConditionList="";
		    	coInfectionConditionList=(String)PageForm.getAttributeMap().get("coInfectionConditionList");
		    	if (coInfectionConditionList != null && !coInfectionConditionList.isEmpty()){%>
		    <span class="value">|</span>
		    <span class="value" id="coinfectionId">${fn:escapeXml(PageForm.attributeMap.coInfectionID)}</span>
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
		    <span class="value" id="headerInvestigationStatus">${fn:escapeXml(PageForm.attributeMap.investigationStatus)}</span>
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
		    <span class="value" id="headerCurrentInvestigator">${fn:escapeXml(PageForm.attributeMap.investigatorName)}</span>
		</td>
		<td class="border2">
		    <span class="label"> Case Status: </span>
            <span class="value" id="headerCaseStatus">${fn:escapeXml(PageForm.attributeMap.caseClassCd)}</span>
		</td>
		<td class="border3">
		    <span class="label">Notification Status: </span>
		    <span class="value" id="patientSummaryJSP_view_notificationStatus">
              <% 
              String notificationStatus1 = (String) request.getAttribute("notificationStatus");
              if( notificationStatus1 == null || "null".equals(notificationStatus1) || notificationStatus1.trim().length()==0)
            	  notificationStatus1= "";
              
              notificationStatus1 = notificationStatus1.trim(); 
              request.setAttribute("notificationStatus1", notificationStatus1);
              if (notificationStatus1.length() != 0) { 
              %>  
                  ${fn:escapeXml(notificationStatus1)}
              <% } %>                                    
          </span>
		</td>
    </tr>
    <% } %>
    <tr style="display:none">
    	<td><span class="value" id="headerConditionCode">${fn:escapeXml(PageForm.attributeMap.headerConditionCode)}</span>
    	<td><span class="value" id="headerProcessingDecision">${fn:escapeXml(PageForm.attributeMap.ProcessingDecision)}</span>
    </td></tr>

</table>
