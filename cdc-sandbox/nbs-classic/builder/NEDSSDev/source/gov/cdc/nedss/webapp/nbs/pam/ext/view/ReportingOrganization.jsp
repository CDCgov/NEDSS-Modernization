	<!-- SUBSECTION : Reporting Organization: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Reporting Organization" classType="subSect" >
	  <!-- Reporting Source -->
		<tr class='${PamForm.formFieldMap.INV112.state.visibleString}'>
			<td class="fieldName">
				<span style="${PamForm.formFieldMap.INV112.state.requiredIndClass}">*</span>
				<span style="${PamForm.formFieldMap.INV112.errorStyleClass}" title="${PamForm.formFieldMap.INV112.tooltip}">${PamForm.formFieldMap.INV112.label}</span>
			</td>
			<td>
				<nedss:view name="PamForm" property="pamClientVO.answer(INV112)" codeSetNm="${PamForm.formFieldMap.INV112.codeSetNm}"/>
			</td>
		</tr>	  
	 <!--Reporting Organization-->
		 <tr  class='${PamForm.formFieldMap.inv218.state.visibleString}'>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV218.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV218.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV218.tooltip}">${PamForm.formFieldMap.INV218.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV218)"/>
		     	<span id="INV218">${PamForm.attributeMap.INV218SearchResult}</span>
		     </td>
		 </tr>
	<!-- TB LDFs -->	    
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT))
	%>	  
     <%= request.getAttribute("1340") == null ? "" :  request.getAttribute("1340") %>

	<!-- Varicella LDFs -->
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR))
	%>	  
	     <%= request.getAttribute("2056") == null ? "" :  request.getAttribute("2056") %>   	 	

	</nedss:container>
