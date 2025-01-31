<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<script type="text/javascript" src="eCSStender.js"></script>
<style type="text/css">

</style>
<jsp:useBean id="BaseForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.util.BaseForm" />
               
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
            <span class="valueTopLine">${fn:escapeXml(name)}</span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine">${fn:escapeXml(currentSex)}</span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine">${fn:escapeXml(DOB)}</span>
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Patient ID: </span>
            <span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(BaseForm.attributeMap.patientLocalId)}</span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
    </tr>
     <% if (BaseForm.getActionMode().equals("Edit") ||
           BaseForm.getActionMode().equals("View") ||
           BaseForm.getActionMode().equals("EDIT_SUBMIT")) { %>
    
    <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Investigation ID: </span>
		    <span class="value">${fn:escapeXml(BaseForm.attributeMap.caseLocalId)}</span>
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
		    <span class="value">${fn:escapeXml(BaseForm.attributeMap.investigationStatus)}</span>
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
		    <span class="value">${fn:escapeXml(BaseForm.attributeMap.investigatorName)}</span>
		</td>
		<td class="border2">
		    <span class="label"> Case Status: </span>
            <span class="value">${fn:escapeXml(BaseForm.attributeMap.caseClassCd)}</span>
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
</table>

   
   
