<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
<style>

</style>
<jsp:useBean id="PageForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.page.PageForm" />
                
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
            <span class="valueTopLine"> <%= name %> </span>
            <span class="valueTopLine">|</span>
            <span class="valueTopLine"> <%= currentSex %> </span>
            <span class="valueTopLine">|</span>
       		<%if(request.getAttribute("currentAgeUnitCd") != null){%>
            <span class="valueTopLine"> <%= DOB %> (<%=request.getAttribute("currentAge")%> <%=CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT")%>) </span>
            <%}else{ %>
            <span class="valueTopLine"> <%= DOB %>  </span>
            <%} %>
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Patient ID: </span>
            <span style="font:16px Arial; margin-left:0.2em;">  ${PageForm.attributeMap.PatientId} </span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
   </tr>
   <% if (PageForm.getActionMode().equals("Edit") ||
		   PageForm.getActionMode().equals("View") ||
		   PageForm.getActionMode().equals("EDIT_SUBMIT")) { %>
   <tr class="cellColor">
            <!-- Investigation Id, Case Status, Notification Status -->
            <td class="border1">
			    <span class="label"> Record ID: </span>
			    <span class="value"> ${PageForm.attributeMap.LocalId} </span>
			</td>
			<td class="border2">
			    <span class="label"> Created: </span>
			    <span class="value"> ${PageForm.attributeMap.CreatedOn} </span>
			</td>
			<td class="border3">
			    <span class="label"> By: </span>
			    <span class="value">${PageForm.attributeMap.CreatedBy}</span>
			</td> 
    </tr>   
    <tr class="cellColor">
            <!-- Created Date/By , Updated Date/By -->
            <td class="border1">
			    <span class="label"></span>
			    <span class="value"></span>
			</td> 
			<td class="border2">
			    <span class="label"> Last Updated: </span>
			    <span class="value"> ${PageForm.attributeMap.UpdatedOn} </span>
			</td> 
			<td class="border3">
			    <span class="label"> By: </span>
			    <span class="value"> ${PageForm.attributeMap.UpdatedBy} </span>
			</td>            
        </tr>
          <% } %>
   </table>
