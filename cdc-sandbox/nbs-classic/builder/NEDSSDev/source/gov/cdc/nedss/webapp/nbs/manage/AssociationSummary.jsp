<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
<style>


</style>

<jsp:useBean id="nbsDocumentForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.nbsdocument.NbsDocumentForm" />
                
<!-- Page Meta data Block -->
<table role="presentation" class="style">
        <tr class="cellColor">
            <!-- Name, Sex, DOB, Patient Id -->
			<td class="border" colspan="3">
				<%
	            String name = (String) request.getAttribute("fullNm") == null ? "---" :  (String) request.getAttribute("fullNm");
	            name = name.trim(); 
	            if (name.length() != 0) {
	            	name = name;
	            }else
	            	name="---";
	            String suffix = (String) request.getAttribute("suffix") == null ? "" : (String) request.getAttribute("suffix");
	            if (suffix.length() != 0)
	            {
	            	name = name + ", "+suffix;
	            }
	            String currentSex = (String) request.getAttribute("sex") == null ? "---" :  (String) request.getAttribute("sex");
	            currentSex = currentSex.trim(); 
	            if(currentSex.length() !=0){
	            	currentSex = currentSex;
	            }else
	            	currentSex="---";
	            
	            String DOB = (String) request.getAttribute("dob") == null ? "---" :  (String) request.getAttribute("dob");
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
			
        </tr>
    <tr class="cellColor">
            <!-- Investigation Id, Case Status, Notification Status -->
            <td class="border1">
			    <span class="label"> Investigation ID: </span>
			    <span class="value"> <%=request.getAttribute("publicHealthCaseLocalUid")%>  </span>
			</td>
             <td class="border2">
			    <span class="label"> Condition: </span>
			    <span class="value"> ${manageEventsForm.conditionCd}  </span>
			</td>
			<td class="border3">
			    <span class="label"> Case Status: </span>
			    <span class="value">  ${manageEventsForm.caseStatus}</span>
			</td> 
        </tr>
   </table>
