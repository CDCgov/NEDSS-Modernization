<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Locally Defined Lab Tests</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
            function manageLabTests()
            {
                if(LDLabTestSearchReqFlds()) {
                    return false;
                } else {
                    document.forms[0].action ="/nbs/LDLabTests.do?method=searchLabSubmit";
                }
            }				

            function add(){
                document.forms[0].action ="/nbs/LDLabTests.do?method=createLoadLab#labtests";
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
    <body onload="srtOnLoad();startCountdown();">
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
                <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
                
                <html:form action="/LDLabTests.do">	
                    <nedss:container id="section1" name="Find an existing locally defined test" 
                            classType="sect" displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(Lab Test Code or Lab Test Description is required to submit a search)</i>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="labCode"><font class="boldTenRed" > * </font><span>Lab Test Code:</span></td>
                                    <td>
                                        <html:text title="Lab Test Code" styleId="srchLabTest" property="searchCriteria(LABTEST)" maxlength="20"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="labDes"><font class="boldTenRed" > * </font> <span>Lab Test Description:</span></td>
                                    <td>
                                        <html:select title = "Lab Test Description operator" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>						 
                                        <html:text title="Lab Test Description" styleId="srchLabTestDesc" property="searchCriteria(TEST_DESC)" size="50" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="lab"><font class="boldTenRed" > * </font><span>Lab Name:</span></td>
                                    <td>
                                        <html:select title = "Lab Name" property="searchCriteria(LABID)" styleId="srchLabId">
                                            <html:optionsCollection property="laboratoryIds" value="key" label="value"/>
                                        </html:select>					 
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="test"><font class="boldTenRed" > * </font><span>Test Type:</span></td>
                                    <td>
                                        <html:select title = "Test Type" styleId="testType" property="searchCriteria(TEST_TYPE)">
                                            <html:optionsCollection property="testTypeList" value="key" label="value"/>
                                        </html:select>	
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return manageLabTests();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToManage();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
                    
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList"> 
                        <nedss:container id="section2" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset  style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
	                                            <display:column property="viewLink" title="<p style='display:none'>View</p>" />
	                                            <display:column property="editLink" title="<p style='display:none'>Edit</p>" />
	                                            <display:column property="labTestCd" title="Lab Test Code" />
	                                            <display:column property="labTestDescTxt" title="Lab Test Description" />
	                                            <display:column property="laboratoryId" title="Lab ID" />
	                                            <display:column property="testTypeDescTxt" title="Test Type" />
	                                            <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr>  
                                    <tr>
                                        <td class="InputButton" align="right">
                                            <input type="submit" name="submit" id="submit" value="Add New Lab Test" onClick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>		
				    <%String name1 = "Lab Test"; String name2 = "Add New Lab Test";%>
					<%@ include file="../core/searchResults.jsp" %>  	  
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
						<%@ include file="LocallyDefinedLabTests.jsp" %>
					</logic:equal>	
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
						<%@ include file="LocallyDefinedLabTests.jsp" %>
					</logic:equal>	
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
						<%@ include file="LocallyDefinedLabTests.jsp" %>
					</logic:equal>	
                </html:form>
            </div>
        </div>
    </body>
    <%@ include file="/jsp/footer.jsp" %>
</html>