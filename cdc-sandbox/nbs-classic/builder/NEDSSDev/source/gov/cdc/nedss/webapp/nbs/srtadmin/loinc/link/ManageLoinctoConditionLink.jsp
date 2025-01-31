<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Link between LOINC and Condition Code</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
            function searchLink()
            {
                if(LoincCondLinkSearchReqFlds()) {
                    return false;
 				} else {
                    document.forms[0].action ="/nbs/LoinctoConditionLink.do?method=searchLoincSubmit";
                }
            }				

            function add()
            {
                var loinc = getElementByIdOrByName("loinc");
                var condition = getElementByIdOrByName("srchCondition");    		       
                if((loinc.innerHTML.length>0) && (condition.value != null && condition.value.length>0)) {
                    document.forms[0].action ="/nbs/LoinctoConditionLink.do?method=createSubmitLink";
                } else {
                    document.forms[0].action ="/nbs/LoinctoConditionLink.do?method=createLoadLink#Add";
                }
            }			
 
            function returnToManage()
            {
                var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
                if (confirm(confirmMsg)) {
                    document.forms[0].action ="/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin32";
                } else {
                    return false;
                }	      	
            }
        </script>      	
    </head>
    <body onload="srtLinkLoad();startCountdown();" onfocus="parent_disable();">
        <div id="blockparent"></div>
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
					        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin32">
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
                
                <html:form action="/LoinctoConditionLink.do">	
                    <nedss:container id="section1" name="Search for an existing Link" classType="sect" 
                            displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(LOINC Code or Condition  is required to submit a search)</i></td>
                                </tr>	
                                <tr>
                                    <td class="fieldName" id="codeLoinc"><font class="boldTenRed" > * </font><span>LOINC Code:</span></td>
                                    <td>
                                        <span id="loinc">${SRTAdminManageForm.searchMap.LOINC}</span>
                                        <html:hidden property="searchCriteria(LOINC)"/>
                                        <input type="button" name="submit" id="submit" value="Search LOINC Code" 
                                            onClick="searchLoinc();"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="con">
                                        <font class="boldTenRed" > * </font><span>Condition:</span>
                                    </td>
                                    <td>
                                        <html:select title="Condition" property="searchCriteria(CONDITION)" styleId="srchCondition">
                                            <html:optionsCollection property="conditionList" value="key" label="value"/>
                                        </html:select>              
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"  class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return searchLink();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToManage();"/>			  
					 					&nbsp;
                                    </td>
                                    <td>
                                    	<input type="hidden" id="lastCodeSel" name="lastCodeSelected"/>
                                    </td>
                                </tr>
                            </nedss:container>     
                        </fieldset>
                    </nedss:container>
                          
			        <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                        <nedss:container id="section2" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="conditionCd" title="Condition Code" />
                                                <display:column property="loincCd" title="LOINC Code" />
                                                <display:column property="diseaseNm" title="Disease Name" />
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr>
                                    
                                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View"> 			
                                        <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
                                            <logic:equal name="SRTAdminManageForm" property="srchFldCount" value="2">
                                                <logic:empty name="SRTAdminManageForm" property="attributeMap.NORESULT">
                                                    <tr>
                                                        <td class="InputButton" align="right">
                                                            <input type="submit" name="submit" id="submit" value="Add Link" onClick="add();"/>
                                                        </td>
                                                    </tr>
                                                </logic:empty>     		     			 
                                            </logic:equal>
                                        </logic:notEqual> 
                                    </logic:notEqual>  
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>
                    
                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View"> 
                        <%String name1 = "LOINC to Condition link"; String name2 = "Add Link";%>
                        <%@ include file="../../core/searchResults.jsp" %> 			
                    </logic:notEqual>
                      
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
                        <%@ include file="LoincCondition.jsp" %>
                    </logic:equal>
                    	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
                        <%@ include file="LoincCondition.jsp" %>
                    </logic:equal>	
                </html:form>
            </div>
        </div>
    </body>        
    <%@ include file="/jsp/footer.jsp" %>
</html>