<!-- SUBSECTION : Physician : --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Physician" classType="subSect">
		<!-- Physician -->
	    <logic:empty name="PamForm" property="attributeMap.INV247Uid">                
	        <tr class="${PamForm.formFieldMap.INV247.state.visibleString}">
	            <td class="fieldName"> 
	                <span style="${PamForm.formFieldMap.INV247.state.requiredIndClass}">*</span>
	                <span style="${PamForm.formFieldMap.INV247.errorStyleClass}"
	                        title="${PamForm.formFieldMap.INV247.tooltip}">${PamForm.formFieldMap.INV247.label}</span>  
	            </td>
	            <td>
	                <span id="clearINV247" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV247CodeClearButton" onclick="clearProvider('INV247')"/>
	                </span>  
	                
	                <span id="INV247SearchControls">
                       <input type="button" class="Button" value="Search" 
                                id="INV247Icon" onclick="getProvider('INV247');" />
                        &nbsp; - OR - &nbsp;
                        <html:text property="pamClientVO.answer(INV247)" styleId="INV247Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV247Text','INV247_qec_list')" title="${PamForm.formFieldMap.INV247.tooltip}"/>
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV247CodeLookupButton" onclick="getDWRProvider('INV247')"/>                                
                        <div class="page_name_auto_complete" id="INV247_qec_list" style="background:#DCDCDC"></div>
                    </span>
	            </td>
	        </tr>
	        <tr  class="${PamForm.formFieldMap.INV247.state.visibleString}">
	            <td class="fieldName"> Physician Selected:  </td>
	            <td>
	                <span id="test2">
	                    <html:hidden property="attributeMap.INV247Uid"/>
	                    <span id="INV247">${PamForm.attributeMap.INV247SearchResult}</span>
	                </span>
	            </td>
	        </tr>
	   </logic:empty>
	   <logic:notEmpty name="PamForm" property="attributeMap.INV247Uid">                
	       <tr  class="${PamForm.formFieldMap.INV247.state.visibleString}">
	            <td class="fieldName"> 
	                <span style="${PamForm.formFieldMap.INV247.state.requiredIndClass}">*</span>
	                <span style="${PamForm.formFieldMap.INV247.errorStyleClass}" 
	                        title="${PamForm.formFieldMap.INV247.tooltip}">${PamForm.formFieldMap.INV247.label}</span>   
	            </td>
	            <td>
	                <span id="clearINV247">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV247CodeClearButton" onclick="clearProvider('INV247')"/>
	                </span>
	                
	                <span id="INV247SearchControls" class="none">
	                   <input type="button" class="Button" value="Search" 
                                id="INV247Icon" onclick="getProvider('INV247');" />
                        &nbsp; - OR - &nbsp;
                        <html:text property="pamClientVO.answer(INV247)" styleId="INV247Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV247Text','INV247_qec_list')" title="${PamForm.formFieldMap.INV247.tooltip}" 
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV247CodeLookupButton" onclick="getDWRProvider('INV247')" 
                            style="visibility:hidden"/>                                
                        <div class="page_name_auto_complete" id="INV247_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
	        </tr>
	        <tr  class="${PamForm.formFieldMap.INV247.state.visibleString}">
            <td class="fieldName"> Physician Selected:  </td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV247Uid"/>
	                    <span id="INV247">${PamForm.attributeMap.INV247SearchResult}</span>
	                </span>
	            </td>
	        </tr>
	    </logic:notEmpty>
	    <!-- Invstigator search error. Not defined in the metadata -->
	    <tr>
	        <td colspan="2" style="text-align:center;">
	            <span id="INV247Error"/></td>
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
