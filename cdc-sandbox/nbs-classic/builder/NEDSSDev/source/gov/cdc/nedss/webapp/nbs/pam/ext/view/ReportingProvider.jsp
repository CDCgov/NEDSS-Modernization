	<!-- SUBSECTION : Reporting Provider: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Reporting Provider" classType="subSect" >
	 <!--Reporting Physician/Nurse-->
		 <tr  class='${PamForm.formFieldMap.INV225.state.visibleString}'>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV225.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV225.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV225.tooltip}">${PamForm.formFieldMap.INV225.label}</span>  
		     </td>
		     <td>
		     <nedss:view name="PamForm" property="pamClientVO.answer(INV225)"/>   
		       <span id="INV225">${PamForm.attributeMap.INV225SearchResult}</span>
		     </td>
		 </tr>
	<!-- TB LDFs -->
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT))
	%>	  
     <%= request.getAttribute("1343") == null ? "" :  request.getAttribute("1343") %>    

	<!-- Varicella LDFs -->
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR))
	%>	  
	     <%= request.getAttribute("2054") == null ? "" :  request.getAttribute("2054") %>    
	</nedss:container>
