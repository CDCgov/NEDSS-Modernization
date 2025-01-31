<%@ include file="../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Varicella</title>
        <%@ include file="../../jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPamForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="PamSpecific.js"></SCRIPT>
    	 <SCRIPT Language="JavaScript" Src="VaricellaSpecific.js"></SCRIPT>
        <script language="JavaScript">
       
        blockEnterKey();
        
        function cancelForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
            	clearErrors();
                document.forms[0].action ="${PamForm.attributeMap.Cancel}";
            } else {
                return false;
            }
        }
        
        
        function saveForm() 
        {
             var jurCheck = validatePAMJurisdiction();
             
					 // call the JS function to block the UI while saving is on progress if jurCheck returns false
						if(jurCheck != "false")
							blockUIDuringFormSubmission();        
						
						if(jurCheck == "valid") {
						var method="${PamForm.attributeMap.method}";    
						if(method=="createSubmit"){
							document.forms[0].action ="/nbs/PamVarAction.do?method=createSubmit&ContextAction=Submit";
						}else{
							document.forms[0].action ="/nbs/PamVarAction.do?method=editSubmit&ContextAction=Submit";
						}
						document.forms[0].submit();
					} 
					else if(jurCheck == "true") {         
						document.forms[0].action ="/nbs/PamVarAction.do?method=createSubmit&ContextAction=SubmitNoViewAccess";
						document.forms[0].submit();
					} 
					else if(jurCheck == "false") { 
						return false;
					}
        
        }
        
        
        </script>
        <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
        </style>
    </head>

    <body class onload="startCountdown();autocompTxtValuesForJSP();attachMoveFocusFunctionToTabKey();varicellaCreateLoad('${PamForm.attributeMap.selectEltIdsArray}'); updateHospitalInformationFields('INV128', 'INV233'); displayDeathWS('INV145', 'resultSpanRowId');" 
            onfocus="parent_disable();">         
        <div id="pamview"></div>
        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/PamVarAction.do">
                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                    
                    <!-- For create/edit mode only -->
                    <logic:notEqual name="BaseForm" property="actionMode" value="Preview">
                        <!-- top button bar -->
                        <%@ include file="../../jsp/topbuttonbarFullScreenWidth.jsp" %>
                        
                        <!-- Page Errors -->
                        <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>

                        <!-- Patient summary -->
                        <%@ include file="../patient/PatientSummary.jsp" %>
                                             
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div>
                    </logic:notEqual>
                    
                    <!-- For preview mode only -->
                    <logic:equal name="BaseForm" property="actionMode" value="Preview">
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenBlack">
                                    <a id="manageLink" href="${PamForm.attributeMap.backToManage}">Back to Manage Varicella LDFs</a>                        
                            </span>  
                        </div>
                    </logic:equal>
                    
                    <!-- Tab container -->
                    <layout:tabs width="100%" styleClass="tabsContainer">
							<logic:notEqual name="BaseForm" property="actionMode" value="Preview">
		                        <layout:tab key="Patient">
		                            <jsp:include page="../patient/RVCT_Patient.jsp"/>
		                        </layout:tab>
							</logic:notEqual>
							<layout:tab key="Varicella">
	                            <jsp:include page="Varicella_Tab1_Layout.jsp"/>
	                     </layout:tab>
                        <logic:equal name="BaseForm" property="actionMode" value="Edit">
	                         <layout:tab key="Supplemental Info">
									<jsp:include page="/pam/supplemental/SupplementalInformation.jsp"/> 
	                         </layout:tab>
                        </logic:equal>	    
                        <logic:equal name="BaseForm" property="actionMode" value="EDIT_SUBMIT">
	                         <layout:tab key="Supplemental Info">
									<jsp:include page="/pam/supplemental/SupplementalInformation.jsp"/> 
	                         </layout:tab>
                        </logic:equal>  
                        <logic:equal name="PamForm" property="securityMap(ContactTracingEnableInd)" value="Y">
                        <logic:equal name="PamForm" property="securityMap(checkToViewContactTracing)" value="true">
	                         <layout:tab key="Contact Tracing">
	                            <jsp:include page="/pam/contactTracing/ContactTracing.jsp"/>                           
	                        </layout:tab>    
                        </logic:equal>
                         </logic:equal>                                                    
	                        <!-- LDF Tab-->
	                        <logic:notEmpty name="PamForm" property="formFieldMap.LDFTAB">
	                             <layout:tab key="${PamForm.formFieldMap.LDFTAB.label}">
	                                <tr>
	                                    <td>
	                                         <nedss:container id="ldfSection" 
	                                              name="Custom Fields" classType="sect" displayLink="false" displayImg="false">
	                                            <nedss:container id="ldfSubsection" name="Custom Fields" 
	                                                   classType="subSect" displayLink="false" displayImg="false">
	                                               <%= request.getAttribute("LDFTAB") == null ? "" :  request.getAttribute("LDFTAB") %>   
	                                            </nedss:container>
	                                         </nedss:container>
	                                    </td>
	                                </tr>
	                            </layout:tab>
	                        </logic:notEmpty>
                    </layout:tabs>
                    
                    
                    
                    <!-- Bottom button bar -->
					<logic:notEqual name="BaseForm" property="actionMode" value="Preview">
                    	<%@ include file="../../jsp/bottombuttonbarFullScreenWidth.jsp" %>
					</logic:notEqual>
	                <logic:equal name="BaseForm" property="actionMode" value="Preview">
	                    <div style="text-align:right; width:100%;"> 
	                        <span class="boldTenBlack">
									<a id="manageLink" href="${PamForm.attributeMap.backToManage}">Back to Manage Varicella LDFs</a>                        
	                        </span>  
	                    </div>
	                </logic:equal>
                </div> <!-- id=bd -->
                    
                <!-- Footer div -->
                <!--
                <div id="ft">
                    NEDSS Base System
                </div>
                -->
            </html:form>
        </div> <!-- Container Div -->
    </body>
</html>