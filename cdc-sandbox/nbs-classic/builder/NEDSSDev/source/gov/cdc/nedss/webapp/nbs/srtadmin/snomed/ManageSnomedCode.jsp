<%@ include file="/jsp/tags.jsp" %>	
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage SNOMED Code</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
            function findSnomed()
            {
                if(snomedSearchReqFlds()) {
                    return false;
                } else {
                    document.forms[0].action ="/nbs/SnomedCode.do?method=searchSnomedSubmit";
                }
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
            
            function add()
            {
                document.forms[0].action ="/nbs/SnomedCode.do?method=createSnomedCode#Snomed";
            }
        </script>
    </head>
    <body  onload="getElementByIdOrByName('srchSnomed').focus();srtOnLoad();startCountdown();">
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
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
					        <input type="hidden" id="actionMode" value="${SRTAdminManageForm.actionMode}"/>
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
			<logic:empty name="SRTAdminManageForm" property="actionMode">
				<tr><td><%@ include file="/jsp/errors.jsp" %></td></tr>
			</logic:empty>
                <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
                
                <html:form action="/SnomedCode.do">
                    <nedss:container id="section1" name="Find an existing SNOMED" classType="sect" displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(SNOMED Code or SNOMED Description is required to submit a search)</i></td>
                                </tr>   		    
                                <tr>
                                    <td class="fieldName" id="snomedCode"><font class="boldTenRed" > * </font><span>SNOMED Code:</span></td>
                                    <td>
                                        <html:text title="SNOMED Code" styleId="srchSnomed" property="searchCriteria(SNOMED_CD)" maxlength="20"/>
                                    </td>
                                </tr>   		    
                                <tr>
                                    <td class="fieldName" id="descS"><font class="boldTenRed" > * </font><span>SNOMED Description:</span></td>
                                    <td>
                                        <html:select title="SNOMED Description operator" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>
										<html:text title="SNOMED Description" styleId="snomedCd" property="searchCriteria(SNOMED)" size="50" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" id="searchb" value="Search" onClick="return findSnomed();"/>
                                        <input type="submit" name="submit" id="cancel" value="Cancel" onClick="return returnToManage();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
                    
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                        <nedss:container id="section2" name="Search Results " classType="sect" displayImg ="false" 
                                displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="editLink" title="Edit"/>
                                                <display:column property="snomedCd" title="SNOMED Code"/>
                                                <display:column property="snomedDescTx" title="SNOMED Description" />
                                                <display:column property="sourceConceptId" title="Source Concept ID" />
                                                <display:column property="sourceVersionId" title="Source Version ID" />
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="InputButton" align="right">
                                            <input type="submit" name="submit" id="submit" value="Add New SNOMED" 
                                                    onClick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>
                    
					<%String name1 = "SNOMED"; String name2 = "Add New SNOMED";%>
					<%@ include file="../core/searchResults.jsp" %> 		
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
					   <%@ include file="SnomedCode.jsp" %>
					</logic:equal>	
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
					   <%@ include file="SnomedCode.jsp" %>
					</logic:equal>	
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
					   <%@ include file="SnomedCode.jsp" %>
					</logic:equal>	
			    </html:form>
            </div>
        </div>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>