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
                 <html:text name="PamForm" title="${PamForm.formFieldMap.INV136.label}" disabled ="${PamForm.formFieldMap.INV136.state.disabled}" 
                     property="pamClientVO.answer(INV136)" maxlength="10" 
                     styleId="INV136" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV136','INV136Icon'); return false;" onkeypress ="showCalendarEnterKey('INV136','INV136Icon',event);"
	                     styleId="INV136Icon"></html:img>
	         </td>
		 </tr>
		 <!--11. Illness Onset Date:-->
		 <tr class="${PamForm.formFieldMap.INV137.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV137.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV137.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV137.tooltip}">${PamForm.formFieldMap.INV137.label}</span>  
		     </td>
		     <td>
			     <html:text name="PamForm" title="${PamForm.formFieldMap.INV137.label}" disabled ="${PamForm.formFieldMap.INV137.state.disabled}" 
	                 property="pamClientVO.answer(INV137)" maxlength="10" 
	                 styleId="INV137" onchange="calculateIllnessOnsetAge();calculateIllnessDuration()" onblur="calculateIllnessOnsetAge();calculateIllnessDuration()" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV137','INV137Icon'); return false;" onkeypress ="showCalendarEnterKey('INV137','INV137Icon',event);" 
	                     styleId="INV137Icon"></html:img>
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
			     <html:text name="PamForm" title="${PamForm.formFieldMap.INV138.label}" disabled ="${PamForm.formFieldMap.INV138.state.disabled}" 
	                 property="pamClientVO.answer(INV138)" maxlength="10" styleId="INV138" onchange = "calculateIllnessDuration()" onblur = "calculateIllnessDuration()" onkeyup="DateMask(this,null,event)" size="10"/>
		             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV138','INV138Icon'); return false;" onkeypress ="showCalendarEnterKey('INV138','INV138Icon',event);" styleId="INV138Icon"></html:img>
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
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV139.state.disabled}" property="pamClientVO.answer(INV139)" maxlength="3" size="5" styleId="INV139"
                 title="${PamForm.formFieldMap.INV139.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        
			     <html:select  title="${PamForm.formFieldMap.INV139.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV140.state.disabled}" property="pamClientVO.answer(INV140)" styleId="INV140" >
		             <html:optionsCollection property="codedValue(INV140)" value="key" label="value"/>
		        </html:select>
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
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV143.state.disabled}" property="pamClientVO.answer(INV143)" maxlength="3" size="5" styleId="INV143"
                 title="${PamForm.formFieldMap.INV143.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        
			     <html:select title="${PamForm.formFieldMap.INV143.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV144.state.disabled}" property="pamClientVO.answer(INV144)" styleId="INV144" >
		             <html:optionsCollection property="codedValue(INV144)" value="key" label="value"/>
		        </html:select>
	         </td>
		 </tr>
	<%
		if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
	%>
		<!--Is the patient pregnant:-->
		<tr class="${PamForm.formFieldMap.INV178.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV178.state.requiredIndClass}">*</span>
		         <span  class="${PamForm.formFieldMap.INV178.state.disabledString}"  id="INV178L" style="${PamForm.formFieldMap.INV178.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV178.tooltip}">${PamForm.formFieldMap.INV178.label}</span>  
		     </td>
		     <td> 
		     	  <html:select title="${PamForm.formFieldMap.INV178.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV178.state.disabled}" property="pamClientVO.answer(INV178)" styleId="INV178" onchange="fireRule('INV178',this)">
	                 <html:optionsCollection property="codedValue(INV178)" value="key" label="value"/>
	              </html:select>
		 	</td>
		 </tr>
		 <!-- TB LDFs -->
	     <%= request.getAttribute("1353") == null ? "" :  request.getAttribute("1353") %>    
	<% } %>
		<!-- Varicella LDFs -->
   <%if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)) {	%>
			 <%= request.getAttribute("2065") == null ? "" :  request.getAttribute("2065") %>    
	<% } %>		     
	</nedss:container>
