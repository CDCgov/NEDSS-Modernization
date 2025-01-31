<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>


<html lang="en">
    <head>
        <title>Manage Locally Defined Lab Results</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
        
		/*
		searchTriggerCodes: method to submit the search. First check if the validation method returns true
		*/
        function searchTriggerCodes()
        {
           if(triggerCodesReqFlds()) {
                return false;
            } else {
                document.forms[0].action ="/nbs/TriggerCodes.do?method=manageLoad&initLoad=true";
            }
        }
            
            function add()
            {
                document.forms[0].action ="/nbs/ExistingLocallyDefinedLabResults.do?method=createLoadLab#labresults";
            }
            
            function returnToManage()
            {
                var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
                if (confirm(confirmMsg)) {
                    document.forms[0].action ="/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin31";
                } else {
                    return false;
                }	      	
            }
        </script>      	
    </head>
    <body  onload="startCountdown();srtOnLoad();">
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                <!-- top bar -->
	        <% 

			String method = request.getParameter("method") == null ? "" : (String) request.getParameter("method");
			if(! method.equals("manageAdmin") && ! method.equals("resetLabMappingCache") && ! method.equals("resetCodeValueGeneralCache"))	
			 {
			%>
			    <table role="presentation" style="width:100%;">
					<tr>
					    <td style="text-align:right"  id="srtLink"> 
					        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin31">
					            Return to System Management Main Menu
					        </a>  
					        <input type="hidden" id="actionMode" value="${triggerCodeForm.actionMode}"/>
					    </td>
					</tr>
					<tr>
				        <td align="right"style="padding-top: 5px;">
				            <i>
				                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
				            </i>
				        </td>	
					</tr>
				</table>
			<%}%>
			<logic:empty name="triggerCodeForm" property="actionMode">
				<tr><td><%@ include file="/jsp/errors.jsp" %></td></tr>
			</logic:empty>
                <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>     

                <html lang="en">:form action="/SearchTriggerCodes.do">
				
					<div class="grayButtonBar">
                        <table role="presentation" width="100%">
                            <tr>
                                <td align="left" width="80%"></td>
                                <td align="right" >
									<input type="submit" name="submit" style="width:70px" id="submit" value="Clear" onClick="triggerCodesClearData();"/>	
					   		 	</td>
                                <td align="right" style="width:10px; padding-left: 5px">
									<input type="submit" name="submit" style="width:70px; margin-right:10px" id="submit" value="Submit" onClick="return searchTriggerCodes();"/>
					   		 	</td>
                            </tr>				   		
					   		
					   	</table>	 		
                         
                    </div>
					
					
                    <nedss:container id="section1" name=" Trigger Code Search" classType="sect" 
                            displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>At least one search criteria is required to submit a search.</i></td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqCodingSystem"><font class="boldTenRed" ></font><span>Coding System:</span></td>
                                    <td>
										<html lang="en">:select name = "triggerCodeForm" property="codingSystemSelected" styleId="codingSystem" size="50">
                                            <html lang="en">:optionsCollection property="codingSystemList" value="key" label="value"/>
                                        </html:select>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqCode" nowrap><font class="boldTenRed" ></font><span>Code:</span></td>
                                    <td>
                                        <html lang="en">:text styleId="code" name = "triggerCodeForm" property="codeSelected" size="56" maxlength="20" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqDisplayName"><font class="boldTenRed" ></font><span>Display Name:</span></td>
									
									
                                    <td>
									
										<html lang="en">:select name = "triggerCodeForm" property="displayNameOperatorSelected" styleId="srchCriteria" acomplete="false">
				                        <option value="CT" selected>Contains&nbsp;&nbsp;&nbsp;</option><option value="=">Equal</option><option value="!=">Not Equal</option>
				                        </html:select>				 
                                        <html lang="en">:text styleId="displayName" name = "triggerCodeForm" property="displayNameSelected" size="70" maxlength="50"/>
                                        					 
                                    </td>
                                </tr>
								<tr>
                                    <td class="fieldName" id="reqDefaultCondition"><font class="boldTenRed" ></font><span>Default Condition:</span></td>
                                    <td>
                                        <html lang="en">:select name = "triggerCodeForm" property="defaultConditionSelected" styleId="defaultCondition" size="40">
                                       
                                            <html lang="en">:optionsCollection property="laboratoryIds" value="key" label="value"/>
                                        </html:select>					 
                                    </td>
                                </tr>

                            </nedss:container>
                        </fieldset>
                    </nedss:container>
			 
			 
					<div class="grayButtonBar">
                        <table role="presentation" width="100%">
                            <tr>
                                <td align="left" width="80%"></td>
                                <td align="right" >
									<input type="submit" name="submit" style="width:70px" id="submit" value="Clear" onClick="return triggerCodesClearData();"/>	
					   		 	</td>
                                <td align="right" style="width:10px; padding-left: 5px">
									<input type="submit" name="submit" style="width:70px; margin-right:10px" id="submit" value="Submit" onClick="return searchTriggerCodes();"/>
					   		 	</td>
                            </tr>				   		
					   		
					   	</table>	 		
                         
                    </div>

                </html:form>
            </div>
        </div>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>