<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage Code Value General</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></script>
        <script type="text/javascript">
            function selectCVG()
            {
                var elem = getElementByIdOrByName("cvg").value;
                if(elem=='ASSGN_AUTHORITY' || elem=='CODE_SYSTEM'){
                    var confirmMsg="You have selected a highly standardized codeset. Are you sure you want to Continue?";
                    if (confirm(confirmMsg)) {
                    } else {
                        getElementByIdOrByName("cvg").value="";
                    }								
                } 
                document.forms[0].action ='/nbs/CodeValueGeneral.do?method=searchCodeValGenSubmit';
                document.forms[0].submit();										
            }

            function addCodeValGenCode()
            {
                document.forms[0].action ="/nbs/CodeValueGeneral.do?method=createCodeValueGenCode#codeval";
            }
        </script>      	
    </head>
    <body  onload="startCountdown();srtOnLoad();disableSRTFlds();">
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
						        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin41">
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
                
                <html:form action="/CodeValueGeneral.do">
                    <nedss:container id="section1" name="Find an existing Code Set" classType="sect" 
                            displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td class="fieldName" ><span>Code Set:</span></td>
                                    <td>
                                        <html:select property="searchCriteria(CODEVALGEN)" onchange="selectCVG();" styleId="cvg">
                                            <html:optionsCollection property="allCodeSetNms" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
                    
                    <% if(request.getAttribute("SearchResult")!=null && !request.getAttribute("SearchResult").equals("")){%>
                        <nedss:container id="section2" name="Search Results" classType="sect" displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="viewLink" title="<p style='display:none'>View</p"/>
                                                <display:column property="editLink" title="<p style='display:none'>Edit</p" />
                                                <display:column property="code" title="Code" />
                                                <display:column property="codeShortDescTxt" title="Code Short Description" />
                                                <display:column property="codeSetNm" title="Code Set Name"/>
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right" class="InputButton">
                                            <input type="submit" name="submitCr" id="submitCr" value="Add New Code" onClick="addCodeValGenCode();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </nedss:container>
                    <%}%>
                    
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
                        <%@ include file="CodeValueGeneral.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
                        <%@ include file="CodeValueGeneral.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
                        <%@ include file="CodeValueGeneral.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="ViewSP">  
                        <%@ include file="CodeValueGeneralMeta.jsp" %>
                    </logic:equal>	 	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="EditSP">  
                        <%@ include file="CodeValueGeneralMeta.jsp" %>
                    </logic:equal>	
                    <logic:equal name="SRTAdminManageForm" property="actionMode" value="CreateSP">  
                        <%@ include file="CodeValueGeneralMeta.jsp" %>
                    </logic:equal>	
                </html:form>
            </div>
        </div>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>