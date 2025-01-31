<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Locally Defined Lab Results</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
            function manageLabResult()
            {
                if(LDLabResultSearchReqFlds()) {
                    return false;
                } else {
                    document.forms[0].action ="/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabSubmit";
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

                <html:form action="/ExistingLocallyDefinedLabResults.do">
                    <nedss:container id="section1" name=" Find an existing locally defined result" classType="sect" 
                            displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(Lab Result Code or Lab Result Description is required to submit a search)</i></td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="resCode"><font class="boldTenRed" > * </font><span>Lab Result Code:</span></td>
                                    <td>
                                        <html:text title="Lab Result Code" styleId="srchLabResult" property="searchCriteria(LABTEST)" size="20" maxlength="20" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="resDesc" nowrap><font class="boldTenRed" > * </font><span>Lab Result Description:</span></td>
                                    <td>
                                        <html:select title = "Lab Result Description" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>						 
                                        <html:text title = "Lab Result Description" styleId="srchLabResultDesc" property="searchCriteria(RESULT_DESC)" size="50" maxlength="50"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="nameLab"><font class="boldTenRed" > * </font><span>Lab Name:</span></td>
                                    <td>
                                        <html:select title = "Lab Name" property="searchCriteria(LABID)" styleId="srchLabId">
                                            <html:optionsCollection property="laboratoryIds" value="key" label="value"/>
                                        </html:select>					 
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return manageLabResult();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToManage();"/>			  
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
			 
                    <!--Search Result-->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList">
                        <nedss:container id="section2" name=" Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="viewLink" title="<p style='display:none'>View</p" />
                                                <display:column property="editLink" title="<p style='display:none'>Edit</p" />
                                                <display:column property="labResultCd" title="Lab Result Code" />
                                                <display:column property="labResultDescTxt" title="Lab Result Description" />
                                                <display:column property="laboratoryId" title="Lab ID"/>
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr> 
                                    <tr>
                                        <td class="InputButton" align="right">
                                            <input type="submit" name="submit" id="submit" 
                                                    value="Add New Lab Result" onClick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset> 
                        </nedss:container> 
                    </logic:notEmpty>
                    <%String name1 = "Lab Result"; String name2 = "Add New Lab Result";%>
                    <%@ include file="../core/searchResults.jsp" %>   
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
                        <%@ include file="LocallyDefinedLabResults.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
                        <%@ include file="LocallyDefinedLabResults.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
                        <%@ include file="LocallyDefinedLabResults.jsp" %>
                    </logic:equal>	
                </html:form>
            </div>
        </div>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>