	<div class="${PamForm.formFieldMap.INV218.state.visibleString}">
	<!-- SUBSECTION : Reporting Organization: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Reporting Organization" classType="subSect" >
	  <!-- Reporting Source -->
		<tr class="${PamForm.formFieldMap.INV112.state.visibleString}">
			<td class="fieldName">
				<span style="${PamForm.formFieldMap.INV112.state.requiredIndClass}">*</span>
				<span class="${PamForm.formFieldMap.INV112.state.disabledString}" id="INV112L" style="${PamForm.formFieldMap.INV112.errorStyleClass}" title="${PamForm.formFieldMap.INV112.tooltip}">${PamForm.formFieldMap.INV112.label}</span>
			</td>
			<td>
				<html:select title="${PamForm.formFieldMap.INV112.label}" name="PamForm" disabled="${PamForm.formFieldMap.INV112.state.disabled}" property="pamClientVO.answer(INV112)" styleId="INV112">
					<html:optionsCollection property="codedValue(INV112)" value="key" label="value"/>
				</html:select>
			</td>
		</tr>
	 <!--Reporting Organization-->
	
	 <logic:empty name="PamForm" property="attributeMap.INV218Uid">
	  <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV218.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV218.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV218.tooltip}">${PamForm.formFieldMap.INV218.label}</span>  
		     </td>
		     <td>
		         <span id="clearINV218" class="none">        
		             <input type="button" class="Button" value="Clear/Reassign" 
		                     id="INV218CodeClearButton" onclick="clearOrganization('INV218');" />
	             </span>   
	             
	             <span id="INV218SearchControls">
                   <input type="button" class="Button" value="Search" 
                            id="INV218Icon" onclick="getReportingOrg('INV218');" />
                    &nbsp; - OR - &nbsp;
                    <html:text property="pamClientVO.answer(INV218)" styleId="INV218Text"
                            size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV218Text','INV218_qec_list')" title="${PamForm.formFieldMap.INV218.tooltip}"/>
                    <input type="button" class="Button" value="Quick Code Lookup" 
                        id="INV218CodeLookupButton" onclick="getDWROrganization('INV218');"/>                                
                    <div class="page_name_auto_complete" id="INV218_qec_list" style="background:#DCDCDC"></div>
                 </span>
		     </td>
		 </tr>
		 <tr>
	         <td class="fieldName">Organization Selected:  </td>
	         <td>
	             <!--  <span class="none test2"> -->
	             <span class="test2">
	                 <html:hidden property="attributeMap.INV218Uid"/>
	                 <span id="INV218">${PamForm.attributeMap.INV218SearchResult}</span>
	             </span>
	         </td>
	     </tr>
	</logic:empty>
	
	 <logic:notEmpty name="PamForm" property="attributeMap.INV218Uid">
	 		<tr>
			     <td class="fieldName"> 
			         <span style="${PamForm.formFieldMap.INV218.state.requiredIndClass}">*</span>
			         <span style="${PamForm.formFieldMap.INV218.errorStyleClass}"
			                 title="${PamForm.formFieldMap.INV218.tooltip}">${PamForm.formFieldMap.INV218.label}</span>  
			     </td>
			     <td>
			         <span id="clearINV218" >        
			             <input type="button" class="Button" value="Clear/Reassign" 
			                     id="INV218CodeClearButton" onclick="clearOrganization('INV218');"/>
			         </span>
			         
			         <span id="INV218SearchControls" class="none">
                       <input type="button" class="Button" value="Search" 
                                id="INV218Icon" onclick="getReportingOrg('INV218');" />
                        &nbsp; - OR - &nbsp;
                        <html:text property="pamClientVO.answer(INV218)" styleId="INV218Text"
                                size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV218Text','INV218_qec_list')" title="${PamForm.formFieldMap.INV218.tooltip}" 
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV218CodeLookupButton" onclick="getDWROrganization('INV218');" 
                            style="visibility:hidden"/>                                
                        <div class="page_name_auto_complete" id="INV218_qec_list" style="background:#DCDCDC"></div>
                    </span>
			     </td>
		</tr>
	    <tr>
             <td class="fieldName">Organization Selected:  </td>
             <td>
                 <!--  <span class="none test2"> -->
                 <span class="test2">
                     <html:hidden property="attributeMap.INV218Uid"/>
                     <span id="INV218">${PamForm.attributeMap.INV218SearchResult}</span>
                 </span>
             </td>
	   </tr>
	</logic:notEmpty>
	 <!-- Reporting search error. Not defined in the metadata -->
	    <tr>
	        <td colspan="2" style="text-align:center;">
	            <span id="INV218Error"/></td>
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
	</div>
