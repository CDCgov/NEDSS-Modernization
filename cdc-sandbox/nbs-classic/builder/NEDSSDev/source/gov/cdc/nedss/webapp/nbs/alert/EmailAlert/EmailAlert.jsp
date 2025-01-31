<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.form.alert.EmailAlert.EmailAlertUserForm"%>
<html lang="en">
    <head>
        <title>Manage User Email</title>
        <script type="text/javaScript" src="alertAdmin.js"> </script>    
    </head>

    <body onload ="disableButtonsF1();">
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                <%@ include file="../manageAlert/alert-topbar.jsp" %>
                <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
                
                <table role="presentation" bgcolor="white" border="0" cellspacing="0" cellpadding="0" width="100%">
                    <logic:notEmpty name="EmailAlertUserForm" property="confirmationMessage">
                        <tr>
                            <td id="Msg">
                                <div class="infoBox success">
						            <nedss:view name="EmailAlertUserForm" property="confirmationMessage"/>
						        </div>
                            </td>
                        </tr>
                    </logic:notEmpty >
                </table>
                
                <html:form>
                    <div class="sect" id="manageEmailAddressess">
                        <h2 class="sectHeader"> 
                            <a class="anchor"> Manage Email Addresses: </a> 
                        </h2>
                        <div class="sectBody">
                            <table role="presentation" class ="subSect" >
                                <tr>
                                    <td  class="fieldName" nowrap>Users List:</td>
                                    <td>
                                        <html:select title = "Select a User from the list" styleId="usersList" name="EmailAlertUserForm" property="emailAlertClientVO.nedssEntryId" onchange="return loadEmail();" >
                                            <html:option value="" ></html:option>
                                            <html:optionsCollection property="usersList" value="NEDSS_ENTRY_ID" label="FULL_NM"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <!-- EmailId1-->
								<tr>
									<td class="fieldName" nowrap id="email1">Email Address 1:</td>
									<td>
										<html:text title = "Enter first Email" styleId="emailId1" name="EmailAlertUserForm" property="emailAlertClientVO.emailId1" onkeyup ="deleteCaseButton()" maxlength="100" size="40"/>
									</td>
								</tr>
								<!-- EmailId2-->
								<tr>
									<td class="fieldName" nowrap id="email2">Email Address 2:</td>
									<td >
										<html:text title = "Enter second Email" styleId="emailId2" name="EmailAlertUserForm" property="emailAlertClientVO.emailId2"  onkeyup ="deleteCaseButton()" maxlength="100" size="40"/>
									</td>
								</tr>
								<!-- EmailId3-->
								<tr>
									<td class="fieldName" nowrap id="email3">Email Address 3:</td>
									<td>
										<html:text title = "Enter third Email" styleId="emailId3" name="EmailAlertUserForm" property="emailAlertClientVO.emailId3"  onkeyup ="deleteCaseButton()" maxlength="100" size="40"/>
									</td>
								</tr>
								<!-- Update Buttion -->
								<tr>
									<td colspan="2" align="right" >
										<table role="presentation">
											<tr>
												<td>
                                                    <input id="updateBtn" type="submit"  value="Update" onclick="deleteMsg();return updateEmail();">
                                                    <input  type="button"  value="Cancel" onclick="return Cancel();" >
                                                    &nbsp;
												</td>
											</tr>
										</table>
									</td>
  								</tr>
                            </table>
                        </div>
                    </div>
                </html:form>
            </div>
        </div>
    </body>
</html>