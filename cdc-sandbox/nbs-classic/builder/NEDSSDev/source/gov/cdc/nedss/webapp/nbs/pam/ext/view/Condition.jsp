<!-- SUBSECTION : Condition: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Condition" classType="subSect" >
	 <!--10. Diagnosis Date:-->
		 <tr class="${PamForm.formFieldMap.INV136.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV136.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV136.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV136.tooltip}">${PamForm.formFieldMap.INV136.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV136)"/>
	         </td>
		 </tr>
		 <!--Illness Onset Date:-->
		 <tr class="${PamForm.formFieldMap.INV137.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV137.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV137.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV137.tooltip}">${PamForm.formFieldMap.INV137.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV137)"/>
	         </td>
		 </tr>
		<!-- Illness End Date: -->		 
		 <tr class="${PamForm.formFieldMap.INV138.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV138.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV138.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV138.tooltip}">${PamForm.formFieldMap.INV138.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV138)"/>
	         </td>
		 </tr>		
		<!-- Illness Duration: -->
		 <tr class="${PamForm.formFieldMap.INV139.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV139.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV139.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV139.tooltip}">${PamForm.formFieldMap.INV139.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV139)"/>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV140)" codeSetNm="${PamForm.formFieldMap.INV140.codeSetNm}"/>
	         </td>
		 </tr>		
		 <!--Age at Illness onset:-->
		 <tr class="${PamForm.formFieldMap.INV143.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV143.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV143.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV143.tooltip}">${PamForm.formFieldMap.INV143.label}</span>  
		     </td>
		     <td>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV143)"/>
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV144)" codeSetNm="${PamForm.formFieldMap.INV144.codeSetNm}"/>
	         </td>
		 </tr>
		 <!-- TB LDFs -->
   <%if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) 	{ %>
		<tr class="${PamForm.formFieldMap.INV178.state.visibleString}">
			<td class="fieldName"> 
				<span style="${PamForm.formFieldMap.INV178.state.requiredIndClass}">*</span>
				<span style="${PamForm.formFieldMap.INV178.errorStyleClass}" title="${PamForm.formFieldMap.INV178.tooltip}">${PamForm.formFieldMap.INV178.label}</span>
			</td>
			<td> 
				<nedss:view name="PamForm" property="pamClientVO.answer(INV178)" codeSetNm="${PamForm.formFieldMap.INV178.codeSetNm}"/>	
			</td>
		</tr>   
	     <%= request.getAttribute("1353") == null ? "" :  request.getAttribute("1353") %>    
	<%} %>     
		<!-- Varicella LDFs -->
   <%if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)) 	%>
			 <%= request.getAttribute("2065") == null ? "" :  request.getAttribute("2065") %>    
	</nedss:container>
