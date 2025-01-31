<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Link between Lab Test and LOINC</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
            function searchLink()
            {
                if(labTestLoincLinkSearchReqFlds()) {
                    return false;
                } else {
                    document.forms[0].action ="/nbs/LabTestLoincLink.do?method=searchLoincSubmit";
                }
            }				

            function add(){
                document.forms[0].action ="/nbs/LabTestLoincLink.do?method=createLoadLink";
            }			

            function returnToManage() {
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
	            
	            <html:form action="/LabTestLoincLink.do">	
	                <nedss:container id="section1" name="Search for an existing Link" classType="sect"  
	                        displayImg ="false" displayLink="false">
	                    <fieldset  style="border-width:0px;" id="search">
	                        <nedss:container id="subsec1" classType="subSect" displayImg ="false">
	                            <tr>
	                                <td class="fieldName" id="labTst">
	                                    <font class="boldTenRed" > * </font><span>Lab Test Code:</span>
	                                </td>
	                                <td>					 	 	
	                                    <span id="labTest">${SRTAdminManageForm.searchMap.LABTEST}</span>
	                                    <html:hidden property="searchCriteria(LABTEST)"/>
	                                    <input type="button" name="submit" id="submit" value="Search Lab Test" 
	                                           onClick="searchLabTest();"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td class="fieldName"><span>Lab ID:</span></td>
	                                <td>
	                                    <span id="labId">${SRTAdminManageForm.searchMap.LABID}</span>
	                                    <html:hidden property="searchCriteria(LABID)"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td class="InputField" colspan="2" align="right">
	                                    <input type="submit" name="submit" id="submit" value="Search" onClick="return searchLink();"/>
	                                    <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToManage();"/>			  
	                                    &nbsp;
	                                </td>
	                            </tr>
	                        </nedss:container>
	                    </fieldset>
	                </nedss:container>
	                
	                
	                <!-- search results -->
	                <logic:notEmpty name="SRTAdminManageForm" property="manageList">  
	                    <nedss:container id="section2" name="Search Results" classType="sect" displayImg ="false" 
	                            displayLink="false">
	                        <fieldset style="border-width:0px;" id="result">
	                            <table role="presentation" width="98%">
	                                <tr>
	                                    <td align="center">
								        	<display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
									            <display:column property="labTestCd" title="Lab Test Code" />
									            <display:column property="laboratoryId" title="Lab ID" />
									            <display:column property="loincCd" title="LOINC Code" />
									            <display:setProperty name="basic.empty.showtable" value="true"/>
	                                        </display:table>
	                                    </td>
	                                </tr>       	
	                            </table>
	                        </fieldset>
	                    </nedss:container>    
	                </logic:notEmpty>
	                		
	                <%String name1 = "Lab Test to LOINC link"; String name2 = "Add Link";%>
	                <%@ include file="../../core/searchResults.jsp" %> 			
	                <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
		               <%@ include file="LabTestLoinc.jsp" %>
	                </logic:equal>	
	                <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
		               <%@ include file="LabTestLoinc.jsp" %>
	                </logic:equal>	
	            </html:form>
	        </div>
        </div>
    </body>			
    <%@ include file="/jsp/footer.jsp" %>
</html>