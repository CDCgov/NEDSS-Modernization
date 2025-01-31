<%@ include file="/jsp/errors.jsp" %>
<title>Manage Locally Defined Lab Tests</title>
<script type="text/javascript">
    function cancelForm(){
        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.cancel}";
        } else {
            return false;
        }
    }	

    function submitForm()
    {
        if(LDLabTestReqFlds()) {
            return false;
        } else {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
    }	
</script>
          	
<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width: 100%;">
    <tr>
	    <td>
            <html:form action="/LDLabTests.do">
                <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} Lab Test"  classType="sect"  displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;"  id="labtests">
                        <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                            <!-- Form Entry Errors -->
                            <tr style="background:#FFF;">
                                <td colspan="2">
                                    <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                                    </div>                        
                                </td>
                            </tr>
                            
                            <tr>
                                <td class="fieldName" id="labTest">
                                    <font class="boldTenRed" > * </font><span>Lab Test Code:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text title="Lab Test Code" property="selection.labTestCd" size="20" maxlength="20" onkeyup="isSpecialCharEnteredForCode(this,event)"/>
                                    </logic:equal>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <nedss:view name="SRTAdminManageForm" property="selection.labTestCd"/>
                                    </logic:notEqual>                
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="desc">
                                    <font class="boldTenRed" > * </font><span>Lab Test Description:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Lab Test Description" property="selection.labTestDescTxt" size="60"  maxlength="100"/>
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.labTestDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> <span>Lab ID:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.laboratoryId"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> <span>Lab Name:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.laboratoryId" methodNm="LaboratoryIds"/>
                                </td>
                            </tr>            
                            <tr>
                                <td class="fieldName"> <span>Test Type:</span></td>
                                <td>
                                    <nedss:view name="SRTAdminManageForm" property="selection.testTypeCd" methodNm="TestTypeList"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> <span>Default Program Area:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:select title="Default Program Area" property="selection.defaultProgAreaCd" styleId="progAreaCd">
                                            <html:optionsCollection property="programAreaList" value="key" label="value"/>
                                        </html:select>
                                    </logic:notEqual>              
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.defaultProgAreaCd" methodNm="ProgramAreaList"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> <span>Default Condition:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:select title="Default Condition" property="selection.defaultConditionCd" styleId="condCd">
                                            <html:optionsCollection property="conditionList" value="key" label="value"/>
                                        </html:select>              
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.conditionDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Drug Test:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title="Drug Test" name="SRTAdminManageForm" property="selection.drugTestInd" value="1"/>
                                        <input type="hidden" title = "Drug Test" name="selection.drugTestInd" value="0">
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title = "Drug Test" name="SRTAdminManageForm" property="selection.drugTestInd" value="1" disabled="true"/>              
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Organism Result Test:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title="Organism Result" name="SRTAdminManageForm" property="selection.organismResultTestInd" value="1"/>              
                                        <input type="hidden" name="selection.organismResultTestInd" value="0">
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title="Organism Result" name="SRTAdminManageForm" property="selection.organismResultTestInd" value="1" disabled="true"/>              
                                    </logic:equal>              
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"><span>Common Test:</span></td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title="Common Test" name="SRTAdminManageForm" property="selection.indentLevelNbr" value="1"/>
                                        <input type="hidden" name="selection.indentLevelNbr" value="0">
                                    </logic:notEqual>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:checkbox title="Common Test" name="SRTAdminManageForm" property="selection.indentLevelNbr" value="1" disabled="true"/>	           
                                    </logic:equal>              
                                </td>
                            </tr>
                            <% 
                            String testTypeCd = (String)request.getAttribute("TestTypeCd");
                            if(testTypeCd.equals("R")){%>
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
	        	            <%}%>
                            <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                <tr>
                                    <td colspan="2" align="right" class="InputField" >
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