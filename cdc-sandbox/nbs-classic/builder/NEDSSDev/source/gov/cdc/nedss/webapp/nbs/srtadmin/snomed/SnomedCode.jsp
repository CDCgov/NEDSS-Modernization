<%@ include file="/jsp/errors.jsp" %>
<title>Manage SNOMED Code</title>
<script language="JavaScript">
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
		if(snomedCreateReqFlds()){
			return false;
		}else{
			document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
		}
	}
</script>

<html:form action="/SnomedCode.do">
    <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} SNOMED Code "  
            classType="sect"  displayImg ="false" displayLink="false">
        <fieldset style="border-width:0px;" id="Snomed">
            <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                <!-- Form Entry Errors -->
                <tr style="background:#FFF;">
                    <td colspan="2">
                        <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                        </div>                        
                    </td>
                </tr>
                
                <!--Form Fields -->
                <tr>
                    <td class="fieldName" id="sCode"><font class="boldTenRed" > * </font><span>SNOMED Code:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <logic:empty name="SRTAdminManageForm" property="selection.snomedCd">
                                <html:text title="SNOMED Code" property="selection.snomedCd" styleId="snomedCd" size="20" maxlength="20"/>
                            </logic:empty>
                            <logic:notEmpty name="SRTAdminManageForm" property="selection.snomedCd">
                                <html:text title="SNOMED Code" property="selection.snomedCd" styleId="snomedCd" size="20" maxlength="20"/>
                            </logic:notEmpty>	
                        </logic:equal>
                    
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <nedss:view name="SRTAdminManageForm" property="selection.snomedCd"/>
                        </logic:equal>
                         
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.snomedCd"/>
                        </logic:equal> 
                    </td>
                </tr>
                
                <tr>
                    <td class="fieldName" id="sDesc"><font class="boldTenRed" > * </font><span>SNOMED Description:</span></td>
                    <td wrap>
                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                            <html:text title="SNOMED Description" property="selection.snomedDescTx" size="50" maxlength="100"/>
                        </logic:notEqual> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.snomedDescTx" />
                        </logic:equal> 
                    </td>
                </tr>
                
                <tr>
                    <td class="fieldName" id="sConId"><font class="boldTenRed" > * </font><span>Source Concept ID:</span></td>
                    <td>
                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                            <html:text title="Source Concept ID" property="selection.sourceConceptId" size="20" maxlength="20"/>
                        </logic:notEqual> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.sourceConceptId" />
                        </logic:equal>     
                    </td>
                </tr>
                
                <tr>
                    <td class="fieldName" id="sVerId"><font class="boldTenRed" > * </font><span>Source Version ID:</span></td>
                    <td>
                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                            <html:text title="Source Version ID" property="selection.sourceVersionId" size="20" maxlength="20"/>
                        </logic:notEqual> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.sourceVersionId" />
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
                        <td colspan="2" align="right" class="InputField">
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