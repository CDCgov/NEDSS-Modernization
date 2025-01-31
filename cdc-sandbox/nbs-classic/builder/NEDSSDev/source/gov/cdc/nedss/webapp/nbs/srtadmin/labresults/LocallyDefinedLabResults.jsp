<%@ include file="/jsp/errors.jsp" %>
<title>Manage Locally Defined Lab Results</title>
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
        if(LDLabResultReqFlds()) {
            return false;
        } else {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
    }	
</script>
      	
<html:form action="/ExistingLocallyDefinedLabResults.do">
    <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} Lab Result" 
            classType="sect" displayImg ="false" displayLink="false"> 
        <fieldset style="border-width:0px;"  id="labresults">
            <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                <!-- Form Entry Errors -->
                <tr style="background:#FFF;">
                    <td colspan="2">
                        <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                        </div>                        
                    </td>
                </tr>
                     
                <!-- Data form -->                
                <tr>
                    <td class="fieldName" id="resCd"><font class="boldTenRed" > * </font><span>Lab Result Code:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <html:text title="Lab Result Code" property="selection.labResultCd" size="20" maxlength="20" onkeyup="isSpecialCharEnteredForCode(this,event)"/>
                        </logic:equal>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <nedss:view name="SRTAdminManageForm" property="selection.labResultCd"/>
                        </logic:equal>   
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.labResultCd"/>
                        </logic:equal>  
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="resDes"><font class="boldTenRed" > * </font><span>Lab Result Description:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <html:text title="Lab Result Description" property="selection.labResultDescTxt" size="60" maxlength="50"/>
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <html:text title="Lab Result Description" property="selection.labResultDescTxt" size="60" maxlength="50"/>
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.labResultDescTxt"/>
                        </logic:equal>  
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" ><span>Lab ID:</span></td>
                    <td>
                        <nedss:view name="SRTAdminManageForm" property="selection.laboratoryId"/>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName"><span>Lab Name:</span></td>
                    <td>
                        <nedss:view name="SRTAdminManageForm" property="selection.laboratoryId" methodNm="LaboratoryIds"/>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName"><span>Default Program Area:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <html:select title="Default Program Area"  property="selection.defaultProgAreaCd" styleId="progCd">
                                <html:optionsCollection property="programAreaList" value="key" label="value"/>
                            </html:select>
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <html:select title="Default Program Area" property="selection.defaultProgAreaCd" styleId="progCd">
                                <html:optionsCollection property="programAreaList" value="key" label="value"/>
                            </html:select>
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.defaultProgAreaCd" methodNm="ProgramAreaList"/>
                        </logic:equal> 
                    </td>
                </tr>
                <tr>
                    <td class="fieldName"><span>Default Condition:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <html:select title="Default Condition" property="selection.defaultConditionCd" styleId="condCd">
  	                             <html:optionsCollection property="conditionList" value="key" label="value"/>
                            </html:select> 
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <html:select title="Default Condition" property="selection.defaultConditionCd" styleId="condCd">
                                <html:optionsCollection property="conditionList" value="key" label="value"/>
                            </html:select> 
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <nedss:view name="SRTAdminManageForm" property="selection.conditionDescTxt"/>
                        </logic:equal> 
                    </td> 
                </tr>
                <tr>
                    <td class="fieldName"><span>Organism Result:</span></td>
                    <td>
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <html:checkbox title="Organism Result" name="SRTAdminManageForm" property="selection.organismNameInd" value="1"/>
                            <input type="hidden" name="selection.organismNameInd" value="0">
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <html:checkbox title="Organism Result" name="SRTAdminManageForm" property="selection.organismNameInd" value="1"/>
                            <input type="hidden" name="selection.organismNameInd" value="0">
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                            <html:checkbox title="Organism Result" name="SRTAdminManageForm" property="selection.organismNameInd" value="1" disabled="true"/>
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
                <tr>
                    <td colspan="2" align="right" class="InputField">
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                            <input type="submit" name="submit" id="submit" value="Submit" onClick="return submitForm();"/>
                            <input type="submit" name="submit" id="submit" value="Cancel" onClick="return cancelForm();"/>
                        </logic:equal> 
                        <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">
                            <input type="submit" name="submit" id="submit" value="Submit" onClick="return submitForm();"/>
                            <input type="submit" name="submit" id="submit" value="Cancel" onClick="return cancelForm();"/>
                        </logic:equal> 
                        &nbsp;
                    </td>
                </tr>
            </nedss:container>	
        </fieldset>	
    </nedss:container>	
</html:form>