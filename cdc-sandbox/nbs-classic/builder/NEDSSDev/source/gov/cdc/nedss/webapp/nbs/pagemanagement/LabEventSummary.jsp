<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder" %>
<style>

</style>
<jsp:useBean id="BaseForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.util.BaseForm" />
<c:set var="accessnumber" value="${BaseForm.attributeMap.AccessionNumber}"/>           
<c:set var="address" value="${BaseForm.attributeMap.Address}"/>  
<c:set var="processingDecissionNotes" value="${BaseForm.attributeMap.ProcessingDecisionNotes}"/>                       
<!-- Page Meta data Block -->
<table role="presentation" class="style">
     <tr class="cellColor">
            <!-- Name, Sex, DOB, Patient Id -->
			<td class="border" colspan="2">
		            <%
		            String name = (String) request.getAttribute("FullName") == null ? "---" :  (String) request.getAttribute("FullName");
		            name = name.trim(); 
		            if (name.length() != 0) {
		            	name = name;
		            }else
		            	name="---";
		            String suffix = (String) request.getAttribute("patientSuffixName") == null ? "" : (String) request.getAttribute("patientSuffixName");
		            if (suffix.length() != 0)
		            {
		            	name = name + ", "+suffix;
		            }
		            String currentSex = (String) request.getAttribute("CurrentSex") == null ? "---" :  (String) request.getAttribute("CurrentSex");
		            currentSex = currentSex.trim(); 
		            if(currentSex.length() !=0){
		            	currentSex = currentSex;
		            }else
		            	currentSex="---";
		            
		            String DOB = (String) request.getAttribute("DOB") == null ? "---" :  (String) request.getAttribute("DOB");
		            if(DOB.length() !=0){
		            	DOB = DOB;
		            }else
		            	DOB="---";
		            //DOB = currentsex.trim();
		           
		         %>
		         
            <span class="valueTopLine" id="Name"><%=name%></span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine" id="Sex"><%=currentSex%></span>
            <span class="valueTopLine">|</span>
       		<%if(request.getAttribute("currentAgeUnitCd") != null){%>
            <span class="valueTopLine" id="Dob"><%=DOB%> (<%=request.getAttribute("currentAge")%> <%=CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT")%>) </span>
            <%}else{ %>
            <span class="valueTopLine" id="Dob"> <%=DOB%> </span>
            <%} %>
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Patient ID: </span>
            <span style="font:16px Arial; margin-left:0.2em;" id="PatientId">${fn:escapeXml(BaseForm.attributeMap.PatientId)}</span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
   </tr>
   <tr class="cellColor">
            <!-- Investigation Id, Case Status, Notification Status -->
            <td class="border1" colspan="2">
			    <span class="label"> Address: </span>
			    <span class="value" id="Address"><%=HTMLEncoder.sanitizeHtmlForSpecialCharacters((String)pageContext.getAttribute("address"))%></span>
			</td>
			<td class="border3">
			    <span class="label"> SSN: </span>
			    <span class="value" id="SSN">${fn:escapeXml(BaseForm.attributeMap.SSN)}</span>
			</td> 
   </tr>   
   <% if ("Edit".equals(BaseForm.getActionMode()) ||
		   "View".equals(BaseForm.getActionMode()) ||
		   "EDIT_SUBMIT".equals(BaseForm.getActionMode())) { %>
   <tr class="cellColor">
            <!-- Investigation Id, Case Status, Notification Status -->
            <td class="border1">
			    <span class="label"> Lab ID: </span> 
			    <span class="value"><c:out value="${BaseForm.attributeMap.LocalId}"/></span>
			    <c:if test = "${BaseForm.attributeMap.ElectronicInd == 'Y'}">
			    	<img class="img-valign" src="ind_electronic.gif" title="Electronic Indicator" alt="Electronic Indicator">
			    </c:if>
			</td>
			<td class="border2">
			    <span class="label"> Created: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.CreatedOn)}</span>
			</td>
			<td class="border3">
			    <span class="label"> By: </span>
			    <span class="value"><c:out value="${BaseForm.attributeMap.CreatedBy}"/></span>
			    <c:if test = "${BaseForm.attributeMap.ElectronicInd == 'Y' || BaseForm.attributeMap.ElectronicInd == 'E'}">
			    	<i>(Submitted by Outside Facility)</i>
			    </c:if>
			</td> 
    </tr>   
    <tr class="cellColor">
            <!-- Created Date/By , Updated Date/By -->
            <td class="border1">
			    <span class="label">Accession Number: </span>
			    <span class="value"><%=HTMLEncoder.sanitizeHtmlForSpecialCharacters((String)pageContext.getAttribute("accessnumber"))%></span>
			</td> 
			<td class="border2">
			    <span class="label"> Last Updated: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.UpdatedOn)}</span>
			</td> 
			<td class="border3">
			    <span class="label"> By: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.UpdatedBy)}</span>
			</td>            
      </tr>
      <tr class="cellColor">
            <!-- Investigation Id, Case Status, Notification Status -->
            <td class="border1">
			    <span class="label"> Collection Date: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.CollectionDate)}</span>
			</td>
			<td class="border2">
			    <span class="label"> Lab Report Date: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.LabReportDate)}</span>
			</td>
			<td class="border3">
			    <span class="label"> Date Received by Public Health: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.DateReceivedByPublicHealth)}</span>
			</td> 
      </tr>   
      <tr class="cellColor">
            <td class="border1">
			    <span class="label"> Processing Decision: </span>
			    <span class="value">${fn:escapeXml(BaseForm.attributeMap.ProcessingDecision)}</span>
			</td>
			<td class="border2" colspan="2">
			    <span class="label"> Processing Decision Notes: </span>
			    <span class="value"><%=HTMLEncoder.sanitizeHtmlForSpecialCharacters((String)pageContext.getAttribute("processingDecissionNotes"))%></span>
			</td>
      </tr>   
          <% } %>
   </table>
