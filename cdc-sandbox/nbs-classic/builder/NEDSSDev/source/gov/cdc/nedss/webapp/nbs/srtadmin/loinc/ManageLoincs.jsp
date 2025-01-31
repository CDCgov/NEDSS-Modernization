<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>Manage LOINC</title>
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
                document.forms[0].action ="/nbs/ManageLoincs.do?method=createManageLoinc#loinc";
            }			

            function returnToManage() {
                var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
                if (confirm(confirmMsg)) {
                    document.forms[0].action ="/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin32";
                } else {
                    return false;
                }	      	
            }
            
            function findComponent()
            {
                var errors = new Array();
                var index = 0;
                var isError = false;					
                var cd  = getElementByIdOrByName("srchLoinc").value;
                var desc  = getElementByIdOrByName("loincCd").value;
                
                if(cd.length == 0 && desc.length == 0) {
                    errors[index++] = "LOINC Code or LOINC Component Name is required";
                    getElementByIdOrByName("loincCode").style.color="#CC0000";
                    getElementByIdOrByName("desLoinc").style.color="#CC0000";
                    isError = true;					
                }
                else {
                    getElementByIdOrByName("loincCode").style.color="black";
                    getElementByIdOrByName("desLoinc").style.color="black";
                }
                
                if (isError) {
                    displayGlobalErrorMessage(errors);
                    return false;
                }

                document.forms[0].action ="/nbs/ManageLoincs.do?method=loincSearchSubmit";				
            }
        </script>      	
    </head>
    <body onload="getElementByIdOrByName('srchLoinc').focus();srtOnLoad();startCountdown();">
        <div id="doc3">
            <div id="bd">
                <div id="blockparent"></div>
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
                                
                <html:form action="/LabTestLoincLink.do">
                    <nedss:container id="section1" name=" Find an existing LOINC" classType="sect" 
                            displayImg ="false" displayLink="false"> 
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td colspan="2" align="left" style="padding-top: 5px;padding-left: 7px"><i>(LOINC Code or LOINC Component Name is required to submit a search)</i></td>
                                </tr>   		    
                                <tr>
                                    <td class="fieldName" id="loincCode"><font class="boldTenRed" > * </font><span>LOINC Code:</span></td>
                                    <td>
                                        <html:text title="LOINC Code" styleId="srchLoinc" property="searchCriteria(LOINC_CD)" maxlength="20"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="desLoinc"><font class="boldTenRed" > * </font><span>LOINC Component Name:</span></td>
                                    <td>
                                        <html:select title="LOINC Component Name operator" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>						 
                                        <html:text title="LOINC Component Name" styleId="loincCd" property="searchCriteria(LOINC)" size="50" maxlength="200"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right">
                                        <table role="presentation">
                                            <tr>
                                                <td class="InputField">
                                                    <input type="submit" name="submit" id="submit" value="Search" onClick="return findComponent();"/>
                                                    <input type="submit" name="submit" id="submit" value="Cancel" onClick=" return returnToManage();"/>			  
                                                    &nbsp;
                                                </td>	  	
                                            </tr>  
                                        </table>
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
			       
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList">  
                        <nedss:container id="section2" name=" Search Results" classType="sect" displayImg ="false" 
                                displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="viewLink" title="View" />
                                                <display:column property="editLink" title="Edit" />
                                                <display:column property="loincCd" title="LOINC Code" />
                                                <display:column property="componentName" title="LOINC Component Name" />
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr> 
                                    <tr>
                                        <td class="InputButton" align="right">
                                            <input type="submit" name="submit" id="submit" value="Add New LOINC" onClick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>
                    	
					<%String name1 = "LOINC"; String name2 = "Add New LOINC";%>
					<%@ include file="../core/searchResults.jsp" %> 	   
					  <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
					<%@ include file="AddEditLoincs.jsp" %>
					</logic:equal>	
					<logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
					<%@ include file="AddEditLoincs.jsp" %> 
						</logic:equal>	
					  <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
					<%@ include file="AddEditLoincs.jsp" %>
					</logic:equal>	
                </html:form>
            </div>
        </div>
    </body>
    <%@ include file="/jsp/footer.jsp" %>
</html>    