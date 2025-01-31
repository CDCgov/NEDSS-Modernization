<%@ include file="/jsp/errors.jsp" %>
<title>Manage Code Value General</title>
<script type="text/javascript">
    function cancelForm()
    {
        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.cancel}";
        } else {
            return false;
        }
    }	

    function submitForm()
    {
        if(CodeValGenMetaReqFlds()){
            return false;
        } else {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
    }

	function populateField(name, selection)
	{
        var element = getElementByIdOrByName(name);
        element.innerHTML=selection;		
    }
</script>
	          	
<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width: 100%;">
    <%
    String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
    if(! confirmMsg.equals("")) {
    %>
	    <tr align="center">
	        <td>
	            <!-- Display failure/success messages depending on code update -->
	            <% if(confirmMsg.indexOf("Failure") != -1) { %>
	                <div class="infoBox errors">
	                    <%=  confirmMsg%>
	                </div>
	            <%} else {%>
	                <div class="infoBox success">
	                    <%=  confirmMsg%>
	                </div>
	            <% } %>
	        </td>
	    </tr>
    <%} %>
    
    <tr>
        <td width="100%">
            <% String metaSectionTxt = "";%>
            <html:form action="/CodeValueGeneral.do">
                <logic:equal name="SRTAdminManageForm" property="actionMode" value="CreateSP">
                    <% metaSectionTxt = "Create Code Value General Code"; %>
                </logic:equal>
                <logic:equal name="SRTAdminManageForm" property="actionMode" value="EditSP">
                    <% metaSectionTxt = "Edit Code Value General Code"; %>
                </logic:equal>
                <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                    <% metaSectionTxt = "View Code Value General Code"; %>
                </logic:equal>
		      	<nedss:container id="section4" name="<%=metaSectionTxt%>" classType="sect"  
                        displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;" id="codeval">
                        <nedss:container id="subsec4" classType="subSect" displayImg ="false">
                        <!-- Form Entry Errors -->
                        <tr style="background:#FFF;">
                            <td colspan="2">
                                <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                                </div>                        
                            </td>
                        </tr>
                            <tr>
                                <td class="fieldName" id="cod"><font class="boldTenRed" > * </font><span>Code:</span></td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="CreateSP">
                                        <html:text property="selection.code" size="20" maxlength="20"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="EditSP">
                                        <nedss:view name="SRTAdminManageForm" property="selection.code"/>
                                    </logic:equal>   
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                        <nedss:view name="SRTAdminManageForm" property="selection.code"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            
                            <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                <tr>
                                    <td  style="WIDTH: 170 px;"/>
                                    <td style="padding-bottom: 1px;"><i>(Please enter OID in Code Description)</i></td>
                                </tr>            
                            </logic:notEqual>
                            
                            <tr>
                                <td class="fieldName" id="descCod"><font class="boldTenRed" > * </font><span>Code Description:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                        <html:text property="selection.codeDescTxt" size="60" maxlength="300"/>  
                                    </logic:notEqual>   
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeDescTxt"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="srtDesCod"><font class="boldTenRed" > * </font><span>Code Short Description:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                        <html:text property="selection.codeShortDescTxt" size="60" maxlength="50"/>
                                    </logic:notEqual>   
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeShortDescTxt"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            
                            <!-- Assign Authority and Coding System for Create and Edit-->
                            <logic:equal name="SRTAdminManageForm" property="actionMode" value="CreateSP">
                            <tr>
                                <td class="fieldName" id="reqaa">
							       <font class="boldTenRed" > * </font>
							           <span>Assigning Authority Description:</span>
							    </td>
                                <td>								
                                    <html:select property="selection.assigningAuthorityDescTxt" onchange="populateField('assigningAuthority', this.value)" styleId="assignAuth">
                                        <html:optionsCollection property="SRTAdminAssignAuth" value="key" label="value"/>
                                    </html:select>
                                </td>	
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Assigning Authority:</span></td>
                                <td>
                                    <span id="assigningAuthority">
                                        <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityCd"/>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="reqcsd"><font class="boldTenRed" > * </font><span>Coding System Description:</span></td>
                                <td>
                                    <html:text property="selection.codeSystemDescTxt" size="60" styleId = "codeSystemDescTxt"/>
                                </td>
                            </tr>	  
                            <tr>
                                <td class="fieldName" id="reqcs"><font class="boldTenRed" > * </font><span>Coding System:</span></td>
                                <td>
                                    <html:text property="selection.codeSystemCd" size="60" styleId = "codeSysCd"/>
                                </td>
                            </tr>         
                        </logic:equal>
                        		            
                        <!-- view mode for Assign Authority stuff-->
                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="CreateSP">
                            <tr>
                                <td class="fieldName" ><font class="boldTenRed" > * </font><span>Assigning Authority Description:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityCd" methodNm="SRTAdminAssignAuth"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" ><span>Assigning Authority:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityCd"/>
                                </td>
                            </tr>
                            
                            <!-- view mode for Coding System stuff-->
                            <tr>
                                <td class="fieldName"><font class="boldTenRed" > * </font><span>Coding System Description:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.codeSystemDescTxt"/>				
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" ><font class="boldTenRed" > * </font><span>Coding System:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.codeSystemCd"/>
                                </td>
                            </tr>
                        </logic:notEqual>
                        
                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
                            <tr>
                                <td colspan="2" align="right" class="InputField">
                                    <input type="submit" name="submitCrSub" id="submitCrSub" value="Submit" onClick="return submitForm();"/>
                                    <input type="submit" name="submitCrCan" id="submitCrCan" value="Cancel" onClick="return cancelForm();"/>            
                                    &nbsp;
                                </td>     
                            </tr>  
                        </logic:notEqual>
				       <%--	      	
				        <tr>
										      	    <td class="fieldName">
				      	   	<span>Parent:</span></td>
										       	    <td>
				       	    	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
				               	  	<html:text property="selection.parentIsCd" size="20" maxlength="20"/>
				               	 </logic:notEqual>  
					             <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
					             	<nedss:view name="SRTAdminManageForm" property="selection.parentIsCd"/>
					             </logic:equal> 
				       	    </td>
				        </tr>
				        <tr>
										      	    <td class="fieldName" >
				       	   	<span>Concept ID:</span></td>
										       	    <td>
				       	    	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
				               	  	<html:text property="selection.sourceConceptId" size="20" maxlength="20"/>
				               	 </logic:notEqual>  
					             <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
					         		<nedss:view name="SRTAdminManageForm" property="selection.sourceConceptId"/>
					         	 </logic:equal> 
				       	    </td>
				        </tr>
				         <tr>
										       	    <td class="fieldName">
				       	   	<span>Super Code Set Name:</span></td>
										       	    <td>
				       	    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
				           	  	<html:text property="selection.superCodeSetNm" size="20" maxlength="20"/>
				           	 </logic:notEqual>  
				             <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
				             	<nedss:view name="SRTAdminManageForm" property="selection.superCodeSetNm"/>
				             </logic:equal> 
				       	    </td>
				        </tr>
				         <tr>
										       	    <td class="fieldName">
				       	   	<span>Super Code:</span></td>
										       	    <td>
				       	    	<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="ViewSP">
				               	  <html:text property="selection.superCode" size="20" maxlength="20"/>
				               	</logic:notEqual>  
					             <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">
					         	<nedss:view name="SRTAdminManageForm" property="selection.superCode"/>
					         	 </logic:equal> 
				       	    </td>
				        </tr>
				    --%>              
                    </nedss:container>
                </fieldset>	  	    	
            </nedss:container>
        </html:form>
	</td>
    </tr> 
</table>