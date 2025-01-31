<%@ include file="/jsp/tags.jsp"%>
<%@ include file="/jsp/resources.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.form.epilink.EpiLinkIdForm"%>
<html lang="en">


<head>
<title>Manage Epi-Link</title>
<script type="text/javaScript" src="epilink.js">
	
</script>
<SCRIPT LANGUAGE="JavaScript">
	function startCountdown() {
		autocompTxtValuesForJSP();
		var sessionTimeout =
<%=request.getSession().getMaxInactiveInterval()%>
	min = sessionTimeout / 60;
		sec = 0;
		getTimerCountDown();
	}
</SCRIPT>
</head>

<body onload="startCountdown()">
	<div id="doc3">
		<div id="bd">
			<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>
			<%@ include file="../../jsp/feedbackMessagesBar.jsp"%>
			<html:form >

				<!-- Form Entry Errors -->

				<tr style="background: #FFF;">
					<td colspan="4">
						<div class="infoBox errors" style="display: none;"
							id="epilinkFormEntryErrors"></div>
					</td>
				</tr>

				<tr>

					<td>
						<table role="presentation" bgcolor="white" border="0" cellspacing="0" cellpadding="0"
							width="100%">
							<tr>
								<td align="right" style="padding-top: 5px;" id="srtLink"><a
									href="/nbs/EpiLinkAdmin.do?method=returnLink&focus=systemAdmin7">Return
										to System Management Main Menu</a></td>
							</tr>
						</table>
					</td>

				</tr>


				<tr>
					<td>
						<table role="presentation" bgcolor="white" border="0" cellspacing="0" cellpadding="0"
							width="100%">
							<tr>
								<td align="right" style="padding-top: 5px;"><i> <font
										class="boldTenRed"> * </font><font class="boldTenBlack">Indicates
											a Required Field </font>
								</i></td>
							</tr>
						</table>
					</td>
				</tr>
				<html:errors />
				<div class="sect" id="searchCriteria">
					<h2 class="sectHeader">
						<a class="anchor"> Merge </a>
					</h2>

				<div class="sectBody">
						<table role="presentation" class="subSect">
							
										<tr>
												<td >
													<bean:message key="epilink.utility"  />
												
												</td>
											</tr>
						</table>
				</div>
					<div class="sectBody">
						<table role="presentation" class="subSect">
							<tr>
								<td colspan="2">
									<table role="presentation" bgcolor="white" border="0" cellspacing="0"
										cellpadding="0" width="100%">
										
										<logic:notEmpty name="epiLinkIdForm"
											property="confirmationMessage">
											<tr>
												<td id="Msg" >
													<div class="infoBox success">
														<nedss:view name="epiLinkIdForm"
															property="confirmationMessage" />
													</div>
												</td>
											</tr>
										</logic:notEmpty>
									</table>
								</td>
							</tr>

							<tr>
								<td class="fieldName" nowrap id="epl"><font
									class="boldTenRed"> * </font>Current Epi-Link ID:</td>
								<td><html:text title = "Current Epi-Link ID" styleId="epilinkId1" name="epiLinkIdForm"
										property="currentEpiLinkId" maxlength="20" size="30" /></td>
							</tr>

							<tr>
								<td class="fieldName" nowrap id="newepl"><font
									class="boldTenRed"> * </font>New Epi-Link ID:</td>
								<td><html:text title = "New Epi-Link ID" styleId="epilinkId2" name="epiLinkIdForm"
										property="newEpiLinkId" maxlength="20" size="30" /></td>
							</tr>

							<!-- Update Buttion -->
							<tr>
								<td colspan="2" align="right">
									<table role="presentation">
										<tr>
											<td><input id="updateBtn" type="button" value="Submit"
												onclick="submitForm()"> 
												<input type="submit"
												value="Cancel" onclick="backtomain()"> &nbsp;</td>
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