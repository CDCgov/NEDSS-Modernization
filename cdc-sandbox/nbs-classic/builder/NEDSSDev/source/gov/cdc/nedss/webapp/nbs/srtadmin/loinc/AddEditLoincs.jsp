<%@ include file="/jsp/errors.jsp" %>
<title>Manage LOINC</title>
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
            var errors = new Array();
            var index = 0;		
            var isError = false;
            		
            var loincd  = getElementByIdOrByName("loinc_cd");
            var cname  = getElementByIdOrByName("compName");

            if(loincd != null && loincd.value.length == 0)
            {
                errors[index++] = "LOINC Code is required";
                getElementByIdOrByName("cLoinc").style.color="#CC0000";
                isError = true;		
            }
            else {
                getElementByIdOrByName("cLoinc").style.color="black";
            }
            
            if( cname != null && cname.value.length == 0) {
                errors[index++] = "LOINC Component Name is required";
                getElementByIdOrByName("descLoinc").style.color="#CC0000";
                isError = true;		
            }
            else {
                getElementByIdOrByName("descLoinc").style.color="black";
            }
            
            if (isError) {
                displayErrors("srtDataFormEntryErrors", errors);
                return false;
            }			  

            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
</script>
      	
<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width:100%;">
    <tr>
        <td>
            <html:form action="/CodeValueGeneral.do">
                <nedss:container id="section3" name=" ${SRTAdminManageForm.actionMode} LOINC" classType="sect" displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;" id="loinc">
                        <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                            <!-- Form Entry Errors -->
                            <tr style="background:#FFF;">
                                <td colspan="2">
                                    <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                                    </div>                        
                                </td>
                            </tr>
                            
                            <!-- Form fields -->
                            <tr>
                                <td class="fieldName" id="cLoinc">
                                    <font class="boldTenRed" > * </font><span>LOINC Code:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text title="LOINC Code" styleId="loinc_cd" property="selection.loincCd" size="20" maxlength="20"/>
                                    </logic:equal>
                                    
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                                        <nedss:view name="SRTAdminManageForm" property="selection.loincCd"/>
                                    </logic:equal> 
							        
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.loincCd"/>
                                    </logic:equal>   
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="descLoinc">
                                    <font class="boldTenRed" > * </font><span>LOINC Component Name :</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="LOINC Component Name" styleId="compName" property="selection.componentName" size="60" maxlength="200"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.componentName"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span>Property:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Property" property="selection.property" size="60" maxlength="10"/>  
                                    </logic:notEqual>
                                    
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.property"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Time Aspect:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Time Aspect" property="selection.time_aspect" size="60" maxlength="10"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.time_aspect"/>
                                    </logic:equal>	
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>System Code:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="System Code" property="selection.system_cd" size="60" maxlength="50"/>
                                    </logic:notEqual>
                                    
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.system_cd"/>
                                    </logic:equal>	
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Scale Type:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Scale Type" property="selection.scale_type" size="60" maxlength="20"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.scale_type"/>
                                    </logic:equal>	
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Method Type:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Method Type" property="selection.method_type" size="60" maxlength="50"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.method_type"/>
                                    </logic:equal>	
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Display Name:</span></td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                                        <html:text title="Display Name" property="selection.display_name" size="60" maxlength="300"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text title="Display Name" property="selection.display_name" size="60" maxlength="300"/>
                                    </logic:equal>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.display_name"/>
                                    </logic:equal>	
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Related Class Code:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Related Class Code" property="selection.related_class_cd" size="60" maxlength="50"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.related_class_cd"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
	        	                <td class="fieldName"><span>Exclude from Program Area Derivation:</span></td>
	        	                <td>
	        	                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
	        	                        <html:checkbox title="Exclude from Program Area Derivation" name="SRTAdminManageForm" property="selection.paDerivationExcludeCd" value="1"/>
	        	                        <input type="hidden" name="selection.paDerivationExcludeCd" value="0">
	        	                    </logic:notEqual>
	        	                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
	        	                        <html:checkbox title="Exclude from Program Area Derivation" name="SRTAdminManageForm" property="selection.paDerivationExcludeCd" value="1" disabled="true"/>	           
	        	                    </logic:equal>              
	        	                </td>
	        	            </tr>
                            
                            <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                <tr>
                                    <td colspan="2" align="right"  class="InputField">
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
</body>
