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
        if(CodeValGenReqFlds()) {
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
            <html:form action="/CodeValueGeneral.do">
                <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} Code Value General Code" classType="sect"  displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;" id="codeval">
                        <nedss:container id="subsec1" classType="subSect" displayImg ="false">
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
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text property="selection.code" size="20" maxlength="20"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                                        <nedss:view name="SRTAdminManageForm" property="selection.code"/>
                                    </logic:equal>   
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.code"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            
                            <tr>
                                <td class="fieldName" id="descCod"><font class="boldTenRed" > * </font><span>Code Description:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text property="selection.codeDescTxt" size="60" maxlength="300"/>  
                                    </logic:notEqual>
                                       
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeDescTxt"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            
                            <tr>
                                <td class="fieldName" id="srtDesCod"><font class="boldTenRed" > * </font><span>Code Short Description:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text property="selection.codeShortDescTxt" size="60" maxlength="50"/>
                                    </logic:notEqual>
                                       
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeShortDescTxt"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            
                            <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <tr>
								<td class="fieldName" id="reqaa">Assigning Authority Description:</span></td>
								<td>
									<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
										<nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityDescTxt" />
									</logic:equal>
                                </td>
                            </tr>
                            
                            <tr>
                                <td class="fieldName"><span>Assigning Authority:</span></td>
                                <td>
								<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityCd"/>
								 </logic:equal>
                                </td>
                            </tr>
								<tr>
									<td class="fieldName" id="reqcs">Coding System Description:</span></td>
									<td>
										<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
											<nedss:view name="SRTAdminManageForm" property="selection.codeSystemDescTxt"/>
										</logic:equal>
									</td>
								</tr>
								<tr>
									<td class="fieldName" ><span>Coding System:</span></td>
									<td>
									<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
										<nedss:view name="SRTAdminManageForm" property="selection.codeSystemCd"/>
									</logic:equal>
									</td>
								</tr>

						</logic:equal>
	                       <tr>
			                      <td class="fieldName"  id=""><span>PHIN Standard Value Set:</span>
			                      </td>
			                      <td>
			                          <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
			                              <html:radio name="SRTAdminManageForm" property="selection.phinStdConceptInd" value="Y"> Yes </html:radio>
			      						  <html:radio name="SRTAdminManageForm" property="selection.phinStdConceptInd" value="N"> No </html:radio>
			                          </logic:notEqual>
			                          <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
			                              <nedss:view name="SRTAdminManageForm" property="selection.phinStdConceptInd"/>
			                          </logic:equal>                
			                      </td>
			                  </tr> 
			                   <tr>
	                                <td class="fieldName"  id=""><span>VADS Value Set Code:</span>
	                                </td>
	                                <td>
	                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <html:text property="selection.vadsConceptCd" onkeydown="checkMaxLength(this)"/>
	                                    </logic:notEqual>
	                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <nedss:view name="SRTAdminManageForm" property="selection.vadsConceptCd"/>
	                                    </logic:equal>
	                                </td>
	                            </tr> 
	                            <tr>
	                                <td class="fieldName"  id=""><span>Code System:</span>
	                                </td>
	                                <td>
	                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <html:text property="selection.codeSystemDescTxt" onkeydown="checkMaxLength(this)"/>
	                                    </logic:notEqual>
	                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSystemDescTxt"/>
	                                    </logic:equal>
	                                </td>
	                            </tr>  
	                            <tr>
	                                <td class="fieldName"  id=""><span>Code System Code:</span>
	                                </td>
	                                <td>
	                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <html:text property="selection.codeSystemCd" onkeydown="checkMaxLength(this)"/>
	                                    </logic:notEqual>
	                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSystemCd"/>
	                                    </logic:equal>
	                                </td>
	                            </tr> 
	                            <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
		                            <tr>
		                                <td class="fieldName"  id=""><span>Status:</span>
		                                </td>
		                                <td>
		                                    <nedss:view name="SRTAdminManageForm" property="selection.statusCdDescTxt"/>
		                                </td>
		                            </tr>
	                            </logic:notEqual>
	                        <%--
	                        <tr>
	                            <td class="fieldName"><span>Parent:</span></td>
	                            <td>
	                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <html:text property="selection.parentIsCd" size="20" maxlength="20"/>
	                                </logic:notEqual>  
	                                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <nedss:view name="SRTAdminManageForm" property="selection.parentIsCd"/>
	                                </logic:equal> 
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"><span>Concept ID:</span></td>
	                            <td>
	                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <html:text property="selection.sourceConceptId" size="20" maxlength="20"/>
	                                </logic:notEqual>  
	                                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <nedss:view name="SRTAdminManageForm" property="selection.sourceConceptId"/>
	                                </logic:equal> 
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"><span>Super Code Set Name:</span></td>
	                            <td>
	                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <html:text property="selection.superCodeSetNm" size="20" maxlength="20"/>
	                                </logic:notEqual>  
	                                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <nedss:view name="SRTAdminManageForm" property="selection.superCodeSetNm"/>
	                                </logic:equal> 
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"><span>Super Code:</span></td>
	                            <td>
	                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <html:text property="selection.superCode" size="20" maxlength="20"/>
	                                </logic:notEqual>  
	                                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <nedss:view name="SRTAdminManageForm" property="selection.superCode"/>
	                                </logic:equal> 
	                            </td>
	                        </tr>
                            --%>
            
		                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                        <tr>
		                            <td colspan="2" align="right"  class="InputField">
		                                <input type="submit" name="submitCrSub" id="submitCrSub" value="Submit" onClick="return submitForm();"/>
		                                <input type="submit" name="submitCrCan" id="submitCrCan" value="Cancel" onClick="return cancelForm();"/>			  
		                                &nbsp;
		                            </td>	  	
		                        </tr>  
		                    </logic:notEqual>  
	                    </nedss:container>
	                </fieldset>	  	    	
	            </nedss:container>
	        </html:form>
		</td>
	</tr>
</table>