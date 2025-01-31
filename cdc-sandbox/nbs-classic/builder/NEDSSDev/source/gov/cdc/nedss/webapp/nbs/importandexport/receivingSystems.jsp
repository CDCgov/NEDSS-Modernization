<title><%= request.getAttribute("PageTitle") %></title>
<script type="text/javascript">
    function cancelForm()
    {
        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            document.forms[0].action ="${receivingFacilityForm.attributeMap.cancel}";
        } else {
            return false;
        }
    }

 function submitForm(){
	 if(RecFacReqFields()) {
	             return false;
	         }
    else {
    	document.forms[0].action ="${receivingFacilityForm.attributeMap.submit}";
	}
    }
function editSumbit(){
			document.forms[0].action =document.forms[0].action ="${receivingFacilityForm.attributeMap.Edit}";
			document.forms[0].submit();
           }
function checkMaxLength(sTxtBox) {
	maxlimit = 2000;
	if (sTxtBox.value.length > maxlimit)
	sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
}
</script>

<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left"
	style="width: 98%;">

	<% if(request.getAttribute("error") != null) { %>
	<div class="infoBox errors">
		<b> <a name="errorMessagesHref"></a> Please fix the following
			errors:</b> <br />

		<%= request.getAttribute("error")%>

	</div>
	<% }%>


	<%
		String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
		if(! confirmMsg.equals("")) {
			%>
	<tr align="center">
		<td>
			<!-- Display failure/success messages depending on code update --> <% if(confirmMsg.indexOf("Failure") != -1) { %>
			<div class="infoBox errors">
				<%=  confirmMsg%>
			</div> <%} else {%>
			<div class="infoBox success">
				<%=  confirmMsg%>
			</div> <% } %>
		</td>
	</tr>
	<%} %>

	<tr>
		<td><html:form action="/ReceivingSystem.do">
				<nedss:container id="section3"
					name="${receivingFacilityForm.actionMode} System " classType="sect"
					displayImg="false" displayLink="false">
					<table role="presentation" style="width: 100%;">
						<TR>
							<TD align='right'><i> <font class="boldTenRed">
										* </font><font class="boldTenBlack">Indicates a Required Field
								</font> </i></TD>
						</TR>
					</table>
					<fieldset style="border-width: 0px;" id="expAlg">
						<nedss:container id="subsec1" classType="subSect"
							displayImg="false">

							<!-- Form Entry Errors -->
							<tr style="background: #FFF;">
								<td colspan="2">
									<div class="infoBox errors" style="display: none;"
										id="receivingFacErrors"></div></td>
							</tr>
							<tr>
								<td class="fieldName" id="reportTypeLabel"><font
									class="boldTenRed"> * </font><span>Report Type
										Send/Received:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:select property="selection.reportType"
											styleId="reportTypeField" size="50"
											onchange="getActionDropdown();" title="Report Type">
											<html:optionsCollection
												property="codedValue(PUBLIC_HEALTH_EVENT)" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:select property="selection.reportType"
											styleId="reportTypeField" onchange="getActionDropdown();"
											title="Report Type">
											<html:optionsCollection
												property="codedValue(PUBLIC_HEALTH_EVENT)" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.reportType"
											codeSetNm="PUBLIC_HEALTH_EVENT" />
									</logic:equal></td>

							</tr>
							<tr>
								<td class="fieldName" id="sysshrtNM"><font
									class="boldTenRed"> * </font><span>Display Name:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:text property="selection.receivingSystemShortName"
											size="50" maxlength="100" styleId="sysShrtNm"
											title="Enter a unique short name for this system. This name will be displayed as an option where the user has the option to select a system. This value is required in order to export or import a Case Report message.  The sending application NamespaceID in the NBS XML schema is compared against this Display Name field." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:text property="selection.receivingSystemShortName"
											size="50" maxlength="100" styleId="sysShrtNm"
											title="Enter a unique short name for this system. This name will be displayed as an option where the user has the option to select a system. This value is required in order to export or import a Case Report message.  The sending application NamespaceID in the NBS XML schema is compared against this Display Name field." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemShortName" />
									</logic:equal></td>
							</tr>
							<tr>

								<td class="fieldName" id="systemNM"><span id="systemNMreq"><font
										class="boldTenRed"> * </font>
								</span><span>Application Name:</span>
								</td>

								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:text property="selection.receivingSystemNm" size="50"
											maxlength="100" styleId="sysNm" title="Enter the name of the Application. This value is required in order to export or import a Case Report message." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:text property="selection.receivingSystemNm" size="50"
											maxlength="100" styleId="sysNm" title="Enter the name of the Application. This value is required in order to export or import a Case Report message." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemNm" />
									</logic:equal></td>
							</tr>
							<tr>
								<td class="fieldName" id="systemOid"><span
									id="systemOidreq"><font class="boldTenRed"> * </font>
								</span><span>Application OID:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:text property="selection.receivingSystemOid" size="50"
											maxlength="100" styleId="sysOid" title="Enter the Applications associated OID value. If you do not know the OID, please contact the system administrator of the system and request the OID value. This value is required in order to export or import a Case Report message from the system. The sending application UniversalID in NBS XML schema is compared against this Application OID field." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:text property="selection.receivingSystemOid" size="50"
											maxlength="100" styleId="sysOid" title="Enter the Applications associated OID value. If you do not know the OID, please contact the system administrator of the system and request the OID value. This value is required in order to export or import a Case Report message from the system. The sending application UniversalID in NBS XML schema is compared against this Application OID field." />

									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemOid" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="systemOwner"><span id="systemOwnerreq"><font
									class="boldTenRed"> * </font></span><span>Facility Name:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:text property="selection.receivingSystemOwner" size="50"
											maxlength="100" styleId="sysOwner" title="Enter the entity (e.g., city, state, territory, facility, etc.) that owns the system. This value, sent in the MSH4 position of an ELR, is required in order to process a Decision Support algorithm for a Lab Report message. The sending facility NamespaceID in the NBS XML schema is compared against this Facility Name field." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:text property="selection.receivingSystemOwner" size="50"
											maxlength="100" styleId="sysOwner" title="Enter the entity (e.g., city, state, territory, facility, etc.) that owns the system. This value, sent in the MSH4 position of an ELR, is required in order to process a Decision Support algorithm for a Lab Report message. The sending facility NamespaceID in the NBS XML schema is compared against this Facility Name field." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemOwner" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="systemOwnerOId"><span id="systemOwnerOIdreq"><font
									class="boldTenRed"> * </font></span><span>Facility
										OID/Identifier:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:text property="selection.receivingSystemOwnerOid"
											size="50" maxlength="100" styleId="sysOwnerOid"
											title="Required field if Report Type is Lab Report; 
100 char max	Enter the system owners (facility) associated OID value. If you do not know the OID, please contact the system administrator of the system and request the OID value. This value, sent in the MSH4 position of an ELR, is required in order to process a Decision Support algorithm for a Lab Report message. The sending facility UniversalID in the NBS XML schema is compared against this Facility OID/Identifier field.
" />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:text property="selection.receivingSystemOwnerOid"
											size="50" maxlength="100" styleId="sysOwnerOid"
											title="Required field if Report Type is Lab Report; 
100 char max	Enter the system owners (facility) associated OID value. If you do not know the OID, please contact the system administrator of the system and request the OID value. This value, sent in the MSH4 position of an ELR, is required in order to process a Decision Support algorithm for a Lab Report message. The sending facility UniversalID in the NBS XML schema is compared against this Facility OID/Identifier field.
" />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemOwnerOid" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="sysDescription"><span>Facility
										Description:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:textarea property="selection.receivingSystemDescTxt"
											onkeydown="checkMaxLength(this)"
											onkeyup="checkMaxLength(this)" cols="38" rows="5"
											styleId="sysDesc" title="Enter a brief description of the system" />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:textarea property="selection.receivingSystemDescTxt"
											onkeydown="checkMaxLength(this)"
											onkeyup="checkMaxLength(this)" cols="38" rows="5"
											styleId="sysDesc" title="Enter a brief description of the system" />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingSystemDescTxt" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="sendSystem"><font
									class="boldTenRed"> * </font><span>Sending System:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:select property="selection.sendingIndCd"
											styleId="sendSys" size="50" title="Select Yes if the system is capable of sending messages to the NBS, else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:select property="selection.sendingIndCd"
											styleId="sendSys" title="Select Yes if the system is capable of sending messages to the NBS, else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.sendingIndDescTxt" />
									</logic:equal></td>
							</tr>


							<tr>
								<td class="fieldName" id="receivSystem"><font
									class="boldTenRed"> * </font><span>Receiving System:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:select property="selection.receivingIndCd"
											styleId="receivSys" onchange="disableTransfer()"
											disabled="true" title="Select Yes if the system is capable of receiving messages from the NBS, else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:select property="selection.receivingIndCd"
											styleId="receivSys" onchange="disableTransfer()"
											title="Select Yes if the system is capable of receiving messages from the NBS, else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.receivingIndDescTxt" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="transferRecSys"><font
									class="boldTenRed"> * </font><span>Allows for
										Transfers:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:select property="selection.allowTransferIndCd"
											styleId="allowTransfer" disabled="true"
											title="Select Yes if the system is capable of receiving transferred documents from the NBS (i.e., does the system have the ability to receive a document and absorb in the system as the owner), else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:select property="selection.allowTransferIndCd"
											styleId="allowTransfer" title="Select Yes if the system is capable of receiving transferred documents from the NBS (i.e., does the system have the ability to receive a document and absorb in the system as the owner), else select No.">
											<html:optionsCollection property="sendSysList" value="key"
												label="value" />
										</html:select>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.allowTransferIndDescTxt" />
									</logic:equal></td>
							</tr>

							<tr>
								<td class="fieldName" id="jurDerive"><font
									class="boldTenRed"> * </font><span>Use System Derived
										Jurisdiction:</span>
								</td>
								<td>
									<table role="presentation">
										<tr>
											<td style="white-space:nowrap"><logic:equal name="receivingFacilityForm"
													property="actionMode" value="Create">
													<html:select property="selection.jurDeriveIndCd"
														styleId="jurDeriveIndCd" disabled="true"
														title="Indicates the system will attempt to derive the jurisdiction for Public Health Case Reports
for this sending system when processed by a Workflow Decision Support algorithm.
">
														<html:optionsCollection property="sendSysList" value="key"
															label="value" />
													</html:select>
												</logic:equal> <logic:equal name="receivingFacilityForm"
													property="actionMode" value="Edit">
													<html:select property="selection.jurDeriveIndCd"
														styleId="jurDeriveIndCd"
														title="Indicates the system will attempt to derive the jurisdiction for Public Health Case Reports
for this sending system when processed by a Workflow Decision Support algorithm.
														">
														<html:optionsCollection property="sendSysList" value="key"
															label="value" />
													</html:select>
												</logic:equal> <logic:equal name="receivingFacilityForm"
													property="actionMode" value="View">
													<nedss:view name="receivingFacilityForm"
														property="exportRecFacDT.jurDeriveDescTxt" />
												</logic:equal>
											</td>
											<td>&nbsp;</td>
											<td><I>Indicates the system will attempt to derive
													the jurisdiction for Public Health Case Reports<br>
													for this sending system when processed by a Workflow
													Decision Support algorithm</I></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td class="fieldName" id="recSystem"><span>Administrative
										Comments:</span>
								</td>
								<td><logic:equal name="receivingFacilityForm"
										property="actionMode" value="Create">
										<html:textarea property="selection.adminComment"
											onkeydown="checkMaxLength(this)"
											onkeyup="checkMaxLength(this)" cols="40" rows="5"
											styleId="sysOwner"  title="Enter any additional comments about the system that may be useful (e.g., the contact person for the system)."/>
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="Edit">
										<html:textarea property="selection.adminComment"
											onkeydown="checkMaxLength(this)"
											onkeyup="checkMaxLength(this)" cols="40" rows="5"
											styleId="sysOwner" title="Enter any additional comments about the system that may be useful (e.g., the contact person for the system)." />
									</logic:equal> <logic:equal name="receivingFacilityForm"
										property="actionMode" value="View">
										<nedss:view name="receivingFacilityForm"
											property="exportRecFacDT.adminComment" />
									</logic:equal></td>
							</tr>
							<logic:notEqual name="receivingFacilityForm"
								property="actionMode" value="View">
								<tr>
									<td colspan="2" align="right" class="InputField"><input
										type="submit" name="submitCrSub" id="submitCrSub"
										value="Submit" onClick="return submitForm();" /> <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Cancel" onClick="return cancelForm();" /> &nbsp;</td>
								</tr>
							</logic:notEqual>
							<logic:equal name="receivingFacilityForm" property="actionMode"
								value="View">
								<tr>
									<td colspan="2" align="right" class="InputField"><input
										id="edit" type="button" value="Edit"
										onclick="return editSumbit();"
										style="height: 25px; width: 100px" /></td>
								</tr>
							</logic:equal>

						</nedss:container>
					</fieldset>
				</nedss:container>
			</html:form></td>
	</tr>
</table>
