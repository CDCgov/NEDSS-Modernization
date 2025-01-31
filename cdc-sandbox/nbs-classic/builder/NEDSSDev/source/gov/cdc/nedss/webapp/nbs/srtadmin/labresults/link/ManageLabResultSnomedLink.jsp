<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
	    <title>Manage Lab Results And Snomed Link</title>    
	    <%@ include file="/jsp/resources.jsp" %>
	    <script type="text/javascript" src="srtadmin.js"></script>
	    <script type="text/javascript">
	        function searchLabLink()
	        {
	            if(LabResultSnomedLinkCreateReqFlds()) {
	                return false;
	            } else {
	                document.forms[0].action ="/nbs/ExistingResultsSnomedLink.do?method=searchSnomedLinkSubmit";
	            }
	        }
	        
	        function add()
	        {
	            document.forms[0].action ="/nbs/ExistingResultsSnomedLink.do?method=createLoadLink";
	        }
	        
	        function returnToLink()
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
    <body onload="startCountdown();srtLinkLoad();" onfocus="parent_disable();">
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
                
                <html:form action="/ExistingResultsSnomedLink.do">
                    <nedss:container id="section1" name="Find Laboratory Result And SNOMED Link" 
                            classType="sect" displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td class="fieldName" id="resCd"><font class="boldTenRed" > * </font><span>Lab Result Code:</span></td>
                                    <td>
                                        <span id="labResult">${SRTAdminManageForm.searchMap.LABRESULT}</span>
                                        <html:hidden property="searchCriteria(LABRESULT)"/>
                                        <input type="button" name="submit" id="submit" 
                                                value="Search Lab Result" onClick="searchLabResultCd();"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName"><span>Lab ID:</span></td>
                                    <td>
                                        <span id="labId">${SRTAdminManageForm.searchMap.LABID}</span>
                                        <html:hidden property="searchCriteria(LABID)"/>
                                        <html:hidden property="searchCriteria(SNOMED)"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return searchLabLink();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToLink();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>	
                        </fieldset>
                    </nedss:container>
                    	
                    <!-- search results -->
                    <logic:notEqual name="SRTAdminManageForm" property="actionMode" value="View"> 
                        <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                            <nedss:container id="section2" name=" Search Results" classType="sect" displayImg ="false" displayLink="false">
                                <fieldset style="border-width:0px;" id="result">
                                    <table role="presentation" width="98%">
                                        <tr>
                                            <td align="center">
                                                <display:table name="manageList" class="dtTable">
                                                    <display:column property="labResultCd" title="Lab Result Code" />
                                                    <display:column property="laboratoryID" title="Lab ID" />
                                                    <display:column property="snomedCd" title="SNOMED Code" />
                                                    <display:setProperty name="basic.empty.showtable" value="true"/>
                                                </display:table>
                                            </td>
                                        </tr>       	
                                    </table>
                                </fieldset>
                            </nedss:container>
                        </logic:notEmpty>
                    </logic:notEqual>
                    
                    <%String name1 = "Lab Result to SNOMED link"; String name2 = "Add Link";%>
                    <%@ include file="../../core/searchResults.jsp" %>   
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
                        <%@ include file="LabResultSnomed.jsp" %>
                    </logic:equal>
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
                        <%@ include file="LabResultSnomed.jsp" %>
                    </logic:equal>	
                </html:form>
            </div>
        </div>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>