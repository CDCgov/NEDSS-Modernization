<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>${fn:escapeXml(PageTitle)}</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javaScript" src="srtadmin.js"></SCRIPT>
        <script type="text/javaScript">
            function manageLab()
            {
                var errors = new Array();
                var arrIndex = 0;
                
                var criteria  = getElementByIdOrByName("srchLabTest");
                if(criteria != null && criteria.value.length == 0)
                {
                    errors[arrIndex++] = "Lab Name is required";
                    
                    // display the list of errors    
                    displayGlobalErrorMessage(errors);
                    
                    getElementByIdOrByName("LabNm").style.color="#CC0000";
                    return false;		
                }
    	    	document.forms[0].action ="/nbs/Laboratories.do?method=searchLabSubmit";
            }				

            function add()
            {
                document.forms[0].action ="/nbs/Laboratories.do?method=createLoadLab#laboratory";
            }
            			
	      	function returnToManage()
	      	{
                var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
                if (confirm(confirmMsg))
                {
                    document.forms[0].action ="/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin3";
                } else {
                    return false;
                }	      	
            }
        </script>  
    </head>
    <body onLoad="getElementByIdOrByName('srchLabTest').focus();srtOnLoad();startCountdown();">
        <div id="doc3">
            <div id="bd">
                <!-- Top Page Nav -->
	            <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
	            
	            <!-- top bar -->
	            <%@ include file="../core/srtadmin-topbar.jsp" %>
	            
	            <!-- Page Errors -->
                <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
                
                <html:form action="/Laboratories.do">	
                    <nedss:container id="section1" name=" Find a Laboratory" classType="sect" 
                            displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
                                <tr>
                                    <td class="fieldName" id="LabNm"><font class="boldTenRed" > * </font><span>Lab Name:</span></td>
                                    <td>
                                        <html:select title="Lab Name operator" styleId="srchCriteria" property="searchCriteria(SRCH_CRITERIA)" acomplete="false">
                                            <html:optionsCollection property="searchCriteriaDropDown" value="key" label="value"/>
                                        </html:select>					 
                                        <html:text title="Lab Name" styleId="srchLabTest" property="searchCriteria(LAB)" size="50" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" class="InputField">
                                        <input type="submit" name="submit" id="submit" value="Search" onClick="return manageLab();"/>
                                        <input type="submit" name="submit" id="submit" value="Cancel" onClick="return returnToManage();"/>
                                        &nbsp;
                                    </td>
                                </tr>
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
                    
                    <!-- search results -->
                    <logic:notEmpty name="SRTAdminManageForm" property="manageList">  
                        <nedss:container id="section2" name="Search Results" classType="sect" 
                                displayImg ="false" displayLink="false">
                            <fieldset style="border-width:0px;" id="result">
                                <table role="presentation" width="98%">
                                    <tr>
                                        <td align="center">
                                            <display:table name="manageList" class="dtTable" pagesize="10"  id="parent" requestURI="">
                                                <display:column property="viewLink" title="<p style='display:none'>View</p>" />
                                                <display:column property="editLink" title="<p style='display:none'>Edit</p>" />
                                                <display:column property="laboratoryId" title="Lab ID" />
                                                <display:column property="laboratorySystemDescTxt" title="Lab Name" />
                                                <display:setProperty name="basic.empty.showtable" value="true"/>
                                            </display:table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="InputButton" align="right">
                                            <input type="submit" name="submit" id="submit" 
                                                    value="Add New Lab" onClick="add();"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </nedss:container>
                    </logic:notEmpty>
                    		
					<%String name1 = "Lab"; String name2 = "Add New Lab";%>
					<%@ include file="../core/searchResults.jsp" %>
				    <logic:equal name="SRTAdminManageForm" property="actionMode" value="View">  
						<%@ include file="Laboratory.jsp" %>
					</logic:equal>	
				    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Edit">  
						<%@ include file="Laboratory.jsp" %>
					</logic:equal>	
				    <logic:equal name="SRTAdminManageForm" property="actionMode" value="Create">  
						<%@ include file="Laboratory.jsp" %>
					</logic:equal>	
                </html:form>
                
                <%@ include file="/jsp/footer.jsp" %>
            </div>
        </div>
    </body>
</html>