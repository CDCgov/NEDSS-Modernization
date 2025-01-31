<!-- SUB_SECTION : Physician -->

	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Physician" classType="subSect" >
	    <!-- Physician -->
        <tr class="${PamForm.formFieldMap.INV247.state.visibleString}">
            <td class="fieldName"> 
                <span style="${PamForm.formFieldMap.INV247.state.requiredIndClass}">*</span>
                <span style="${PamForm.formFieldMap.INV247.errorStyleClass}"
                        title="${PamForm.formFieldMap.INV247.tooltip}">${PamForm.formFieldMap.INV247.label}</span>  
            </td>
            <td>
            	<nedss:view name="PamForm" property="pamClientVO.answer(INV247)"/>   
		       <span id="INV207">${PamForm.attributeMap.INV247SearchResult}</span>
            </td>
        </tr>
	<!-- TB LDFs -->	    
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT))
	%>	  
     <%= request.getAttribute("1345") == null ? "" :  request.getAttribute("1345") %>

	<!-- Varicella LDFs -->
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR))
	%>	  
	     <%= request.getAttribute("2308") == null ? "" :  request.getAttribute("2308") %>    

	</nedss:container>