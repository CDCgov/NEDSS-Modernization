<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/jsp/errors.jsp" %>
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
        if(LabReqFlds()) {
            return false;
        } 
        else {
            document.forms[0].action ="${SRTAdminManageForm.attributeMap.submit}";
        }
    }	
</script>
      	
<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width:100%;">
    <!-- Form -->
    <tr>
        <td>
            <html:form action="/Laboratories.do">
                <nedss:container id="section3" name="${SRTAdminManageForm.actionMode} Laboratory " classType="sect" displayImg ="false" displayLink="false">
                    <fieldset style="border-width:0px;" id="laboratory">
                        <nedss:container id="subsec3" classType="subSect" displayImg ="false">
                            <!-- Form Entry Errors -->
                            <tr style="background:#FFF;">
                                <td colspan="2">
                                    <div class="infoBox errors" style="display:none;" id="srtDataFormEntryErrors">
                                    </div>                        
                                </td>
                            </tr>

                            <tr>
                                <td class="fieldName"  id="abc">
                                    <font class="boldTenRed" > * </font><span>Lab ID:</span>
                                </td>
                                <td>
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <html:text title="Lab ID" property="selection.laboratoryId" size="20" maxlength="20" styleId="labId"/>
                                    </logic:equal>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
                                        <nedss:view name="SRTAdminManageForm" property="selection.laboratoryId"/>
                                    </logic:notEqual>                
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"  id="labDescen">
                                    <font class="boldTenRed" > * </font><span>Lab Name:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Lab Name" property="selection.laboratorySystemDescTxt" size="60" maxlength="100" styleId="labDesc" />      			
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.laboratorySystemDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"  id="cdSysCode">
                                    <font class="boldTenRed" > * </font><span>Coding System:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Coding System" property="selection.codingSystemCd" size="20" maxlength="20" styleId="cdSysCd"/>      			
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codingSystemCd"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="cdSysDesci">
                                    <font class="boldTenRed" > * </font><span>Coding System Description:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Coding System Description" property="selection.codeSystemDescTxt" size="60" maxlength="100" styleId="cdSysDesc"/>      			
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.codeSystemDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName" id="assignAuCD">
                                    <font class="boldTenRed" > * </font><span>Assigning Authority:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Assigning Authority" property="selection.assigningAuthorityCd" size="20" maxlength="300" styleId="assignAuthCd"/>
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityCd"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"  id="AssignDes">
                                    <font class="boldTenRed" > * </font><span>Assigning Authority Description:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <html:text title="Assigning Autority Description" property="selection.assigningAuthorityDescTxt" size="60" maxlength="100" styleId="assignAuthDesc"/>      			
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.assigningAuthorityDescTxt"/>
                                    </logic:equal>
                                </td>
                            </tr>                 
                            <tr>
                                <td class="fieldName"  id="eLabo">
                                    <font class="boldTenRed" > * </font><span>Electronic Lab:</span>
                                </td>
                                <td>
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View">
                                        <!--html:checkbox name="SRTAdminManageForm" property="selection.electronicLabInd" 
                                                value="1" styleId="eLab"/>
                                            <input type="hidden" name="selection.electronicLabInd" value="0"
                                        -->  
                                        <html:select title="Electronic Lab" property="selection.electronicLabInd" styleId = "eLab">
                                            <html:optionsCollection property="codedValue(YN)" value="key" label="value"/>
                                        </html:select>
                                    </logic:notEqual>                
                                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">
                                        <nedss:view name="SRTAdminManageForm" property="selection.electronicLabInd" codeSetNm="YN"/>
                                    </logic:equal>
                                </td>
                            </tr>
                                   
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