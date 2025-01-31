<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.form.alert.alertAdmin.AlertUserForm"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.reportadmin.dt.UserProfileDT" %>
<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<html lang="en">
    <head>
	    <title>Manage Alerts</title>
	    <script type="text/javaScript" src="alertAdmin.js"> </script>
	    <script type="text/javascript">
	        function submitSearch()
	        {
	            if(SearchAlertReqField()) {
	                return false;
	            } else {
	                document.forms[0].action ="/nbs/AlertUser.do?method=searchAlert";
	                document.forms[0].submit();
	                return false;
	            }
	        }
	        
	        function submitAdd()
	        {
	            if(validateAlert()) {
	                return false;
	            } else {
	                document.forms[0].action ="/nbs/AlertUser.do?method=addAlert";
	                setOptionValue();
	                document.forms[0].submit();
	                return false;
	            }
	        }
	        
			function submitUpdate()
			{
	            if(validateAlert()) {
	                return false;
	            } else {
	                document.forms[0].action ="/nbs/AlertUser.do?method=updateAlert";
	                setOptionValue();
	                document.forms[0].submit();
	                return false;
	            }
	        }
	        
	        function submitDelete()
	        {
	            if(validateAlert()) {
	                return false;
	            } else {
	                var confirmMsg="Are you sure you want to delete this Alert? Select OK to continue, or Cancel to not continue.";
	                if (confirm(confirmMsg)) {
						document.forms[0].action ="/nbs/AlertUser.do?method=deleteAlert";
						setOptionValue();
						document.forms[0].submit();
						return false;
	                }else {
	                    return false;
	                }
	            }
	        }
	        
	        function submitSimulate()
	        {
	            if(validateAlert()) {
	                return false;
	            } else {
	                document.forms[0].action ="/nbs/AlertUser.do?method=sendSimulateMessage";
	                setOptionValue();
	                document.forms[0].submit();
	                return false;
	            }
	        }
	    </script>
    </head>
    <body onload="autocompTxtValuesForJSP();">
        <div id="doc3">
            <div id="bd">
		    	<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
		    	<%@ include file="../manageAlert/alert-topbar.jsp" %>
		    	<%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
		    	
                <html:form onsubmit="return setOptionValue();">
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="infoBox errors">
	                        <b> Please fix the following errors:</b> <br/>
	                        <ul>
	                          <li>
	                              ${fn:escapeXml(error)}      
	                          </li>
	                        </ul>
	                    </div>    
                    <% } %>

                    <logic:notEmpty name="alertAdminForm" property="confirmationMessage">
                            <logic:equal name="alertAdminForm" property="confirmationMessage" 
                                    value="No Alerts Found For The Selected Search Criteria">
                                <div class="infoBox messages">
                                    <nedss:view name="alertAdminForm" property="confirmationMessage"/>
                                </div>                                            
                            </logic:equal>
                            <logic:notEqual name="alertAdminForm" property="confirmationMessage" 
                                    value="No Alerts Found For The Selected Search Criteria">
                                <div class="infoBox success">
                                    <nedss:view name="alertAdminForm" property="confirmationMessage"/>
                                </div>                                            
                            </logic:notEqual>
                        </div>
                    </logic:notEmpty >

                    <div class="sect" id="searchCriteria">
                        <h2 class="sectHeader"><a class="anchor"> Search Criteria </a></h2>
                        <div class="sectBody">
                            <table role="presentation" class ="subSect" >
                                <tr>
                                    <td class="fieldName" id="cond"><span style="color:#CC0000;">* </span>Condition:
                                    </td>
                                    <td>
                                        <html:select title = "Select the condition to search by" styleId="sCondCd" name="alertAdminForm" property="searchParamsVO.condition_CD">
                                            <html:optionsCollection property="conditionList" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="jur"><span style="color:#CC0000;">*</span>Jurisdiction:
                                    </td>
                                    <td>
                                        <html:select title = "Select the Jurisdiction to search by" styleId="sJurisCd" name="alertAdminForm" property="searchParamsVO.jurisdiction_CD">
                                            <html:option value="" >	</html:option>
                                            <html:optionsCollection property="jurisdictionList" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td  class="fieldName" id="phe"><span style="color:#CC0000;">*</span>
                                        Public Health Event:
                                    </td>
                                    <td>
                                    <html:select title = "Select the Public Health Event to search by" styleId="sEventCd" name="alertAdminForm" property="searchParamsVO.event_CD">
                                          <html:optionsCollection property="eventList" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="right" style="padding-bottom:5PX;" >	
                                        <input type="button"  value="Search" id=" " onclick="return submitSearch();"/> 
                                        <input type="button"  value="Cancel" id=" " onClick="cancelAlert();"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
					</div>  <!-- end of section -->
						
                    <!-- section 2 Begins -->
                    <div class="sect" id="alertDetails">
                        <h2 class="sectHeader"><a class="anchor"> Alert Details </a></h2>
                        <div class="sectBody">
                            <table role="presentation"  class ="subSect">
                                <!-- Form Entry Errors -->
	                            <tr style="background:#FFF;">
	                                <td colspan="4">
	                                    <div class="infoBox errors" style="display:none;" id="alertDetailsFormEntryErrors">
	                                    </div>                        
	                                </td>
	                            </tr>    
                                <tr>
                                    <td class="fieldName"> Condition: </td>
                                    <td colspan= "3">
                                        <html:select title = "Condition's details" styleId="aCondCd" name="alertAdminForm" property="alertClientVO.condition_code" disabled="true">
                                            <html:optionsCollection property="conditionList" value="key" label="value"/>
                                        </html:select>					
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName"> Jurisdiction: </td>
                                    <td colspan= "3">
                                        <html:select title = "Jurisdiction's details" styleId="aJurisCd" name="alertAdminForm" property="alertClientVO.jurisdiction_code" disabled="true">
                                            <html:option value="" ></html:option>
                                            <html:optionsCollection property="jurisdictionList" value="key" label="value"/>
                                        </html:select>					
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" >Public Health Event: </td>
                                    <td colspan= "3">
                                        <html:select title = "Public Health Event's details" styleId="aEventCd" name="alertAdminForm" property="alertClientVO.event_code" disabled="true" >
                                            <html:option value="" ></html:option>
                                            <html:optionsCollection property="eventList" value="key" label="value"/>
                                        </html:select>			      
                                    </td>
                                </tr>
                                <tr>
                                    <td  class="fieldName" id="user"><span style="color:#CC0000;">* </span>Select User: </td>
                                    <td  style="width:11;">
                                        <html:select title = "User to select" styleId="aUserList" name="alertAdminForm" property="alertUserIds" multiple="true" size="10" style="width:185" >
                                            <logic:iterate id="userProfileDT"  name="alertAdminForm" property="userList"	type="gov.cdc.nedss.reportadmin.dt.UserProfileDT">
                                                <bean:define id="FULL_NM" name="userProfileDT"  property="FULL_NM"/>
                                                <bean:define id="NEDSS_ENTRY_ID" name="userProfileDT"  property="NEDSS_ENTRY_ID" />
                                                <html:option value="<%=NEDSS_ENTRY_ID.toString()%>">
                                                    <bean:write name="FULL_NM"/>
                                                </html:option>
                                            </logic:iterate>
                                        </html:select>
                                    </td>
                                    <td  style="width:1;">
                                        <input type="button"  value="Add &gt" id=" " onClick="copySelectedOptions(document.forms[0]			['alertUserIds'],document.forms[0]['selectedAlertUserIds'],false);return false;" style="width:75;"/>
                                        <input type="button"  value="&lt Remove" id=" "   onClick="removeSelectedOptions		(document.forms	[0]	['selectedAlertUserIds']); return false;" style="width:75;"/>
                                    </td>
                                    <td>
                                        <html:select title = "User selected" styleId="sUserList" name="alertAdminForm" property="selectedAlertUserIds" multiple="true" size="10" style="width:185">
                                            <logic:iterate id="userProfileDT"  name="alertAdminForm" property="selectedUserList" type="gov.cdc.nedss.reportadmin.dt.UserProfileDT">
                                                <bean:define id="FULL_NM" name="userProfileDT"  property="FULL_NM"/>
                                                <bean:define id="NEDSS_ENTRY_ID" name="userProfileDT"  property="NEDSS_ENTRY_ID" />
                                                <html:option value="<%=NEDSS_ENTRY_ID.toString()%>">
                                                    <bean:write name="FULL_NM"/>
                                                </html:option>
                                            </logic:iterate>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="sev"><span style="color:#CC0000;">* </span> Severity: </td>
                                    <td colspan= "3">
                                        <html:select title = "Severity" styleId="aSevCd" name="alertAdminForm" property="alertClientVO.severity_code">
                                            <html:optionsCollection property="codedValue(SEVERITY)" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                	<td class="fieldName"><span> Extended Alert Message: </span></td>
                                	<td colspan= "3">
                                	<html:textarea title = "Extended Alert Message" style="WIDTH: 425px; HEIGHT: 100px;" name="alertAdminForm" property="alertClientVO.alertExtendedMessage" styleId ="ExtendedMessage" onkeyup="checkTextAreaLength(this, 1800)"/>
                                	</td>
								</tr>	
                                <logic:equal name="alertAdminForm" property="actionMode" value="Update">
                                    <tr  align="right" >
                                        <td  colspan="4" align="right" style="padding-bottom:5PX;">
                                            <input type="submit"  value="Update Alert" id=" " onClick="return  submitUpdate();"/>
                                            <input type="submit"  value="Add Alert" id=" " disabled="false" />
                                            <input type="submit"  value="Delete" id=" " onClick="return  submitDelete();"/>
                                            <input type="submit"  value="Simulate"  id=" " onClick="return  submitSimulate();" />
                                        </td>
                                    </tr>
                                </logic:equal>
                                
                                <logic:equal name="alertAdminForm" property="actionMode" value="Add">
									<tr  align="right" >
									   <td colspan="4" align="right" style="padding-bottom:5PX;" >
                                            <input type="submit"  value="Update Alert" id=" "  disabled="false" onClick="return submitUpdate();"/>
                                            <input type="submit" value="Add Alert" id=" " onClick="return  submitAdd();"/>
                                            <input type="submit"  value="Delete" id=" " disabled="false" onClick=" return submitDelete();"/>
                                            <input type="submit"  value="Simulate" disabled="true"   id=" " />
                                        </td>
                                    </tr>
								</logic:equal>

                                <logic:equal name="alertAdminForm" property="actionMode" value="Validate">
                                    <tr  align="right">
                                        <td  colspan="4" align="right" style="padding-bottom:5PX;">
                                            <input type="submit"  value="Update Alert" id=" " onClick="return  submitUpdate();"/>
                                            <input type="submit"  value="Add Alert" id=" " disabled="false" />
                                            <input type="submit" value="Delete" id=" " disabled="false"/>
                                            <input type="submit"  value="Simulate"  id=" " disabled="false" />
                                        </td>
                                    </tr>
                                </logic:equal>
                            </table>
                        </div> <!-- end of section body -->	
                    </div>  <!-- end of section -->
                </html:form>                    		
            </div> <!-- end of div id=bd -->
        </div> <!-- end of div id=doc3 -->
    </body>
</html>