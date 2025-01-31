<%@ include file="/jsp/errors.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title>${fn:escapeXml(PageTitle)}</title>
<script type="text/javaScript">
    function cancelForm()
    {
        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg))
        {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.cancel}";
        }
        else {
            return false;
        }
    }

    function submitForm(){
        if(CodeSetReqFlds()) {
            return false;
        }
        else {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
    }
    function populateField(name, selection)
	    {
	        var element = getElementByIdOrByName(name);
	        element.innerHTML=selection;
    }
    
    function checkMaxLength(sTxtBox) {	
    	maxlimit = 2000;	
		if (sTxtBox.value.length > maxlimit)		
			sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}	
</script>

<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width:100%;">


	<%
		String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
		if(! confirmMsg.equals("")) {
			%>
			<tr align="center">
			<td>
			    <!-- Display failure/success messages depending on code update -->
			    <% if(confirmMsg.indexOf("Failure") != -1) { %>
			        <div class="infoBox errors">
			            ${fn:escapeXml(confirmMsg)}
			        </div>
			    <%} else {%>
			        <div class="infoBox success">
			            ${fn:escapeXml(confirmMsg)}
			        </div>
		<% } %>
		</td>
		</tr>
	<%} %>

    <!-- Form -->
    <tr>
        <td>
            <html:form action="/ManageCodeSet.do">
                <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} Code Set " classType="sect" displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;" id="codeset">
                        <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                            <!-- Form Entry Errors -->
                            <tr style="background:#FFF;">
                                <td colspan="2">
                                    <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="fieldName"  id="CdSnm">
                                    <font class="boldTenRed" > * </font><span>Code Set Name:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text property="selection.codeSetNm" size="20" maxlength="20" />
                                    </logic:equal>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSetNm"/>
                                    </logic:notEqual>
                                </td>
                            </tr>
                            

                            <tr>
                                <td class="fieldName"  id="CdSdescCod">
                                    <font class="boldTenRed" > * </font><span>Code Set Description:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:textarea property="selection.codeSetDescTxt" style="WIDTH: 350px; HEIGHT: 70px;" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSetDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
		                        <td class="fieldName"  id="LDFInd">	
		                        	<span>Include in locally defined field drop-down list:</span>
		                    	</td>
		                    	<td>
		                    		<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
		                    			<html:checkbox name="SRTAdminManageForm" property="selection.ldfPickListInd" value="1" styleId="ldfInd"/>
		                    			<input type="hidden" name="selection.ldfPickListInd" value="0"/>
		                    		</logic:notEqual>
		                    		<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
		                    	 		<html:checkbox name="SRTAdminManageForm" property="selection.ldfPickListInd" value="1" disabled="true" styleId="ldfInd"/>
		                    	 	</logic:equal>
		                    	</td>
		                    </tr>
		                    <tr>
	                            <td class="fieldName"  id="CdSrtDescCod">
	                                <span>Code Set Short Description (for display in drop-down):</span>
	                            </td>
	                            <td>
	                                <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <html:text property="selection.codeSetShortDescTxt" size="50" maxlength="100" />
	                                </logic:notEqual>
	                                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                    <nedss:view name="SRTAdminManageForm" property="selection.codeSetShortDescTxt"/>
	                                </logic:equal>
	                            </td>
	                        </tr>
	                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                <tr>
									<td class="fieldName" id="reqaa">Assigning Authority Description:</span></td>
									<td>
										<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
											<nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityDescTxt"/>
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
			                              <html:radio name="SRTAdminManageForm" property="selection.phinStadValueSetInd" value="Y"> Yes </html:radio>
			      						  <html:radio name="SRTAdminManageForm" property="selection.phinStadValueSetInd" value="N"> No </html:radio>
			                          </logic:notEqual>
			                          <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
			                              <nedss:view name="SRTAdminManageForm" property="selection.phinStadValueSetInd"/>
			                          </logic:equal>                
			                      </td>
			                  </tr> 
			                   <tr>
	                                <td class="fieldName"  id=""><span>VADS Value Set Code:</span>
	                                </td>
	                                <td>
	                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <html:text property="selection.vadsValueSetCode" onkeydown="checkMaxLength(this)"/>
	                                    </logic:notEqual>
	                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	                                        <nedss:view name="SRTAdminManageForm" property="selection.vadsValueSetCode"/>
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
                           
                              <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	                            <tr>
	                                <td  class="InputField" colspan="2" align="right">
	                                    <input type="submit" name="submit" id="submit" value="Submit" onClick="return submitForm();"/>
	                                    <input type="submit" name="submit" id="submit" value="Cancel" onClick="return cancelForm();"/>
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
