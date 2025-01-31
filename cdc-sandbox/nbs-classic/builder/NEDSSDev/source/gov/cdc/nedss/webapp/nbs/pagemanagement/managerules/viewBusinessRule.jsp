
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ include file="../../jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<html lang="en">
<head>
<title>View Rule</title>
<%@ include file="../../jsp/resources.jsp"%>
</head>
<script type="text/javascript">

        function addBusinessRule()
        {   
            document.forms[0].action ="/nbs/ManageRules.do?method=addBusinessRule";
            document.forms[0].submit();
            return true;
        }
         function editBusinessRule()
         {
            document.forms[0].action ="ManageRules.do?method=editBusinessRule&waRuleMetadataUid=<%= request.getAttribute("waRuleMetadataUid")%>&from=view";
             document.forms[0].submit();
             return true;

         }

         function deleteBusinessRule()
         {
        	 var conditionNm = '${fn:escapeXml(codeDesc)}';
             var confirmMsg="You have indicated that you would like to delete business rule " +<%= request.getAttribute("waRuleMetadataUid")%> +". Once deleted, this business rule will be permanently removed from the system and will no longer be associated with the " +conditionNm  +" page. Select OK to continue or Cancel to return to View Rule.";
             if (confirm(confirmMsg)) {
                 document.forms[0].action ="ManageRules.do?method=deleteBusinessRule&waRuleMetadataUid=<%= request.getAttribute("waRuleMetadataUid")%>";
                 document.forms[0].submit();
                return true;
             }else{
             return false;       
             }
         }


         function pageFunctionRule()
         {
             var script ="<bean:write name='manageRulesForm' property='seletedFunction'  />";
             if(script=="Date Compare"){
                getElementByIdOrByName("DateCompare").style.display = "block";
                getElementByIdOrByName("Enabled").style.display = "none";
                getElementByIdOrByName("RequireIf").style.display = "none";
                getElementByIdOrByName("HideShowDiv").style.display = "none";
            }else if (script=="Require If") {
                getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("Enabled").style.display = "none";
                getElementByIdOrByName("HideShowDiv").style.display = "none";
                getElementByIdOrByName("RequireIf").style.display = "block";
	    }else if (script=="Hide" || script=="Unhide") {
	    	//alert("HideShowSection");
	    	getElementByIdOrByName("HideShowDiv").style.display = "block";
	        getElementByIdOrByName("RequireIf").style.display = "none";
	        getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("Enabled").style.display = "none";               
            }else{
                getElementByIdOrByName("Enabled").style.display = "block";
                getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("RequireIf").style.display = "none";
                getElementByIdOrByName("HideShowDiv").style.display = "none";
            }
         }
         
         function createBusinessRule()
         {
             document.forms[0].action ="/nbs/ManageRules.do?method=createBusinessRule";
             document.forms[0].submit();
             return true;
         }
         </script>
     <%    
        String messageInd =  (request.getAttribute("messageInd") == null) ? "" : ((String)request.getAttribute("messageInd"));        
     String templateType =(request.getAttribute("templateType") == null) ? "" : ((String)request.getAttribute("templateType")); //Published
     %>         

<body onLoad="pageFunctionRule()">
<div id="Add Business Rule"></div>

<!-- Container Div: To hold top nav bar, button bar, body and footer -->
<div id="doc3"><html:form action="/ManageRules.do">
    <div id="bd" style="text-align: center;"><!-- Top Nav Bar and top button bar -->
    <%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>

    <!-- Body contents -->
    <div style="">
<div id="doc3">
          
            <div id="bd" style="text-align:center;">               
                <div align="right">
                 <a href="/nbs/ManagePage.do?method=rulesListLoad&context=ReturnToPage&existing=true&initLoad=true">Return to Rule Library</a> &nbsp;&nbsp;&nbsp;
            </div>  
               
  
        </div>        
    <div class="grayButtonBar" style="text-align: right;">
         <%if(!"Published".equalsIgnoreCase(templateType) && !"TEMPLATE".equalsIgnoreCase(templateType)){ %>
            <input type="button" name="Add New" value="Add New" onclick="addBusinessRule();"/>
            <input type="button"  name="Edit"  value="Edit" onclick="editBusinessRule();" />
            <input type="button" name="Delete" value="Delete" onclick="deleteBusinessRule();" />
          <% } %>   
    </div>

<%@ include file="/jsp/feedbackMessagesBar.jsp" %> 
    <% if("ADD".equalsIgnoreCase(messageInd)){ %>
        <div class="infoBox success" style="text-align: left;">
            Rule <bean:write name="manageRulesForm" property="ruleId"  /> has been successfully added to the system.
        </div>
    <%} else if("UPDATE".equalsIgnoreCase(messageInd)){ %>
        <div class="infoBox success" style="text-align: left;">
            Rule <bean:write name="manageRulesForm" property="ruleId"  /> has been successfully updated in the system.
        </div>
    <% } %> 
    <div>
           <nedss:container id="sect_question" name="View Rule" classType="sect" displayImg="false" includeBackToTopLink="no">
                <nedss:container id="question" name="Rule Description"   classType="subSect" includeBackToTopLink="no">
                     <tr>
                        <td class="fieldName" title="The system-generated ID for this business rule.">Rule ID:</td>
                        <td> <bean:write name="manageRulesForm" property="ruleId"  /></td>
                    </tr>                    
                    <tr>
                        <td class="fieldName" title="The function needed to support this business rule.">Function:</td>
                        <td>
                            <bean:write name="manageRulesForm" property="seletedFunction"  />
                        </td>
                    </tr>
                    <tr>
                        <td class="fieldName" title="A description of the trigger and logic associated with this business rule (a textual representation of the business rule).">Description:</td>
                        <td> <bean:write name="manageRulesForm" property="ruleDescription"  /> </td>
                    </tr>
                </nedss:container>
                <div id="DateCompare" style="display:none">
                    <nedss:container id="DateCompare" name="Rule Details"  classType="subSect" includeBackToTopLink="no"  isHidden="true">
                        <tr>
                            <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule).">Source:</td>
                            <td> <bean:write name="manageRulesForm" property="seletedSourceDateComp"  /> </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">Logic/Comparator:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="seletedLogicDateComp"  />
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The date(s) to compare to see if the 'trigger condition' is met.">Target(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewTargetDateComp"  /> 
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="Error Message">Error Message:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="errorMessage"  /> 
                            </td>
                        </tr>                        
                    </nedss:container>
                </div>
                <div id="Enabled" style="display:none">        
                    <nedss:container id="Enabled" name="Rule Details" classType="subSect"  includeBackToTopLink="no" isHidden="false">
                        <tr>
                            <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule).">Source:</td>
                            <td> 
                               <bean:write name="manageRulesForm" property="seletedSourceDisOrEnabled"  />  
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">Logic/Comparator:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="seletedLogDisOrEnabled"  />
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The value(s) that meet the 'trigger condition'.">Source Value(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewSourceValDisOrEnabled"  />
                           </td>
                        </tr>
                        <tr>
                       <td class="fieldName" title="The type of target (e.g., subsection, question, etc.).">Target Type:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="selectedTargetType"  />
                            </td>
                        </tr>                         
                        <tr>
                            <td class="fieldName" title="The field(s) that should be disabled/enabled if the 'trigger condition' is met.">Target(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewTarDisOrEnabled"  />
                            </td>
                        </tr>                     
                    </nedss:container>
                </div>
                <div id="RequireIf" style="display:none">        
                    <nedss:container id="RequireIf" name="Rule Details" classType="subSect"  includeBackToTopLink="no" isHidden="false">
                        <tr>
                            <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule).">Source:</td>
                            <td> 
                               <bean:write name="manageRulesForm" property="selectedSourceRequired"  />  
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">Logic/Comparator:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="selectedLogicRequired"  />
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The value(s) that meet the 'trigger condition'.">Source Value(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewSourceValRequired"  />
                           </td>
                        </tr>                       
                        <tr>
                            <td class="fieldName" title="The field(s) required if the 'trigger condition' is met.">Target(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewTargetRequired"  />
                            </td>
                        </tr>                     
                    </nedss:container>
                </div>  
                <div id="HideShowDiv" style="display:none">        
                    <nedss:container id="HideShowSection" name="Rule Details" classType="subSect"  includeBackToTopLink="no" isHidden="false">
                        <tr>
                            <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule).">Source:</td>
                            <td> 
                               <bean:write name="manageRulesForm" property="selectedSourceHideOrUnhide"  />  
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">Logic/Comparator:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="selectedLogicHideOrUnhide"  />
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName" title="The value(s) that meet the 'trigger condition'.">Source Value(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewSourceValHideOrUnhide"  />
                           </td>
                        </tr>
                        <tr>
                       <td class="fieldName" title="The type of target (e.g., subsection, question, etc.).">Target Type:</td>
                            <td>
                                <bean:write name="manageRulesForm" property="selectedTargetTypeHD"  />
                            </td>
                        </tr>                         
                        <tr>
                            <td class="fieldName" title="The field(s) that should be hidden/shown if the 'trigger condition' is met.">Target(s):</td>
                            <td>
                                <bean:write name="manageRulesForm" property="viewTargetHideOrUnhide"  />
                            </td>
                        </tr>                     
                    </nedss:container>
                </div>                
        </nedss:container>
    
    </div>

    <div class="grayButtonBar" style="text-align: right;">
         <%if(!"Published".equalsIgnoreCase(templateType) && !"TEMPLATE".equalsIgnoreCase(templateType)){ %>
            <input type="button" name="Add New" value="Add New" onclick="addBusinessRule();"/>
            <input type="button"  name="Edit"  value="Edit" onclick="editBusinessRule();" /> 
            <input type="button" name="Delete" value="Delete" onclick="deleteBusinessRule();" />
          <% } %>  

    </div>
    </div>
    </div>
</html:form></div>
</body>
</html>