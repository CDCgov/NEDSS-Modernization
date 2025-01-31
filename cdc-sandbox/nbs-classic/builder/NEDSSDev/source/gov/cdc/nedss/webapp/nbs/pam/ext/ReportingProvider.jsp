	<!-- Reporting Provider (INV225) -->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Reporting Provider" classType="subSect" >
	 <!--Reporting Physician/Nurse-->
	 <logic:empty name="PamForm" property="attributeMap.INV225Uid">    
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV225.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV225.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV225.tooltip}">${PamForm.formFieldMap.INV225.label}</span>  
		     </td>
		     <td>
		         <span id="clearINV225" class="none">        
		             <input type="button" class="Button" value="Clear/Reassign" 
		                     id="INV225CodeClearButton" onclick="clearProvider('INV225')"/>
		         </span>
		         
		         <span id="INV225SearchControls">
                   <input type="button" class="Button" value="Search" 
                            id="INV225Icon" onclick="getProvider('INV225');" />
                    &nbsp; - OR - &nbsp;
                    <html:text property="pamClientVO.answer(INV225)" styleId="INV225Text"
                            size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV225Text','INV225_qec_list')" title="${PamForm.formFieldMap.INV225.tooltip}"/>
                    <input type="button" class="Button" value="Quick Code Lookup" 
                        id="INV225CodeLookupButton" onclick="getDWRProvider('INV225')"/>                                
                    <div class="page_name_auto_complete" id="INV225_qec_list" style="background:#DCDCDC"></div>
                 </span>
		     </td>
		 </tr>
		 <tr>
         	<td class="fieldName"> Provider Selected:  </td>
	         <td>
	             <span id="test2">
	                 <html:hidden property="attributeMap.INV225Uid"/>
	                 <span id="INV225">${PamForm.attributeMap.INV225SearchResult}</span>
	             </span>
	         </td>
	     </tr>
	     </logic:empty> 
	     <logic:notEmpty name="PamForm" property="attributeMap.INV225Uid">                
	       <tr>
	            <td class="fieldName"> 
	                <span style="${PamForm.formFieldMap.INV225.state.requiredIndClass}">*</span>
	                <span style="${PamForm.formFieldMap.INV225.errorStyleClass}" 
	                        title="${PamForm.formFieldMap.INV225.tooltip}">${PamForm.formFieldMap.INV225.label}</span>   
	            </td>
	            <td>
	                <span id="clearINV225">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV225CodeClearButton" onclick="clearProvider('INV225')"/>
	                </span>
	                
	                <span id="INV225SearchControls" class="none">
                       <input type="button" class="Button" value="Search" 
                                id="INV225Icon" onclick="getProvider('INV225');" />
                        &nbsp; - OR - &nbsp;
                        <html:text property="pamClientVO.answer(INV225)" styleId="INV225Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV225Text','INV225_qec_list')" title="${PamForm.formFieldMap.INV225.tooltip}" 
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV225CodeLookupButton" onclick="getDWRProvider('INV225')" 
                            style="visibility:hidden"/>                                
                        <div class="page_name_auto_complete" id="INV225_qec_list" style="background:#DCDCDC"></div>
                    </span>
	            </td>
	        </tr>
	        <tr>
	        	<td class="fieldName"> Provider Selected:  </td>
	            <td>
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV225Uid"/>
	                    <span id="INV225">${PamForm.attributeMap.INV225SearchResult}</span>
	                </span>
	            </td>
	        </tr>
	    </logic:notEmpty>
	     <!-- Invstigator search error. Not defined in the metadata -->
	    <tr>
	        <td colspan="2" style="text-align:center;">
	            <span id="INV225Error"/></td>
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
