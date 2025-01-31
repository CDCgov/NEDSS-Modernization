<title><%=request.getAttribute("PageTitle")%></title>
<script type="text/javascript">
	function cancelFormTrigger() {
		// var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		// if (confirm(confirmMsg)) {
		document.forms[0].action = "${triggerCodeForm.attributeMap.cancel}";
		document.forms[0].submit();
		//} else {
		//  return false;
		//}
	}

	function submitForm() {
		if (RecFacReqFields()) {
			return false;
		} else {
			document.forms[0].action = "${triggerCodeForm.attributeMap.submit}";
		}
	}

	/**
	 * editSumbit(): update the code from edit view
	 */
	function editSumbit() {
		if(!requiredFields()){
			document.forms[0].action = "${triggerCodeForm.attributeMap.Edit}";
			document.forms[0].submit();
		}
		else
			return false;
	}

	/**
	 * createSumbit(): create the code from add view
	 */
	function createSumbit() {
		if(!requiredFields()){
			document.forms[0].action = "${triggerCodeForm.attributeMap.Create}";
			document.forms[0].submit();
		}
		else
			return false;
	}

	/*
	 editFromView(): method called from Edit button in view section
	 */
	function editFromView() {
		document.forms[0].action = "${triggerCodeForm.attributeMap.EditView}";
		document.forms[0].submit();
	}

	/*        
	function cancelSubmit(){
	document.forms[0].action = "/nbs/TriggerCodes.do?method=viewTriggerCode&context=cancel";
	document.forms[0].submit();		
	}*/

	function checkMaxLength(sTxtBox) {
		maxlimit = 2000;
		if (sTxtBox.value.length > maxlimit)
			sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}
	
		window.onload = function() {
			setDropdowns();
			document.getElementById("requiredFieldsErrors2").setAttribute("style","display:''");
			disableElementsTriggerCodes();
			filterFunctionality();
		}
</script>

<table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left"
	style="width: 100%;">


	<div id="requiredFieldsErrors" class="infoBox errors" style="display:none">
		<b> <a name="errorMessagesHref"></a> Please fix the following
			errors:
		</b> <br />
		<div id="errorText">
		
		</div>
	</div>


	<div id="requiredFieldsErrors2">
	<%
		if (request.getAttribute("error") != null) {
	%>
		<div class="infoBox errors">
			<b> <a name="errorMessagesHref2"></a> Please fix the following
				errors:
			</b> <br />

			<%=request.getAttribute("error")%>

		</div>
	<%
		}
	%>
	
	</div>


	<tr>
		<td><nedss:container id="section3"
				name="${triggerCodeForm.actionMode} Code " classType="sect"
				displayImg="false" displayLink="false">


				<!--<table style="width: 100%;">
						<TR>
							<TD align='right'><i> <font class="boldTenRed">
										* </font><font class="boldTenBlack">Indicates a Required Field
								</font> </i></TD>
						</TR>
					</table>-->
				<fieldset style="border-width: 0px;" id="expAlg">
					<nedss:container id="subsec1" classType="subSect"
						displayImg="false">

						<!-- Form Entry Errors -->
						<tr style="background: #FFF;">
							<td colspan="2">
								<div class="infoBox errors" style="display: none;"
									id="receivingFacErrors"></div>
							</td>
						</tr>
						<tr>
							<td class="fieldName" id="codingSystemLabel"><font
								class="boldTenRed"> * </font><span>Coding System:</span></td>
							<td><logic:equal name="triggerCodeForm"
									property="actionMode" value="Create">

									<html:select property="selection.codingSystem"
										styleId="codingSystem2" title="Select Coding System">
										<html:optionsCollection property="codingSystemList"
											value="key" label="value" />
									</html:select>

								</logic:equal>
								<logic:equal name="triggerCodeForm" property="actionMode"
									value="Edit">
									<nedss:view name="triggerCodeForm"
										property="selection.codingSystem" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="View">
									<nedss:view name="triggerCodeForm"
										property="trigCodesDT.codingSystem" />
								</logic:equal></td>

						</tr>
						<tr>
							<td class="fieldName" id="codeLabel"><font
								class="boldTenRed"> * </font><span>Code:</span></td>
							<td><logic:equal name="triggerCodeForm"
									property="actionMode" value="Create">
									<html:text property="selection.codeColumn" size="50"
										maxlength="100" styleId="codeColumn2" title="Enter Code" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="Edit">
									<nedss:view name="triggerCodeForm"
										property="selection.codeColumn" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="View">
									<nedss:view name="triggerCodeForm"
										property="trigCodesDT.codeColumn" />
								</logic:equal></td>
						</tr>
						<tr>

							<td class="fieldName" id="displayName"><span
								id="displayNameReq"><font class="boldTenRed"> * </font> </span><span>Display
									Name:</span></td>

							<td><logic:equal name="triggerCodeForm"
									property="actionMode" value="Create">
									<html:text property="selection.displayName" size="50"
										maxlength="100" styleId="displayNm2"
										title="Enter the Display Name" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="Edit">
									<html:text property="selection.displayName" size="50"
										maxlength="100" styleId="displayNm2"
										title="Enter the Display Name" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="View">
									<nedss:view name="triggerCodeForm"
										property="trigCodesDT.displayName" />
								</logic:equal></td>
						</tr>


						<tr>
							<td class="fieldName" id="defaultCondition"><span
								id="defaultConditionReq"><font class="boldTenRed"></font>
							</span><span>Default Condition:</span></td>
							<td><logic:equal name="triggerCodeForm"
									property="actionMode" value="Create">

									<html:select property="selection.conditionCd"
										styleId="defaultCondition2" title="Select Default Condition">
										<html:optionsCollection property="conditionList" value="key"
											label="value" />
									</html:select>

								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="Edit">

									<html:select property="selection.conditionCd"
										styleId="defaultCondition2" title="Select Default Condition">
										<html:optionsCollection property="conditionList" value="key"
											label="value" />
									</html:select>

								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="View">
									<nedss:view name="triggerCodeForm"
										property="trigCodesDT.diseaseNm" />
								</logic:equal></td>
						</tr>

						<tr>
							<td class="fieldName" id="versionIdTd"><span id="versionIdReq"><font
									class="boldTenRed"> * </font></span><span>Version ID:</span></td>
							<td><logic:equal name="triggerCodeForm"
									property="actionMode" value="Create">
									<html:text property="selection.codeSystemVersionId" size="20"
										maxlength="20" styleId="versionId"
										title="Enter the Version Id" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="Edit">
									<html:text property="selection.codeSystemVersionId" size="20"
										maxlength="20" styleId="versionId"
										title="Enter the Version Id" />
								</logic:equal> <logic:equal name="triggerCodeForm" property="actionMode"
									value="View">
									<nedss:view name="triggerCodeForm"
										property="trigCodesDT.codeSystemVersionId" />
								</logic:equal></td>
						</tr>

						<logic:equal name="triggerCodeForm" property="actionMode"
							value="Create">
							<tr>
								<td colspan="2" align="right" class="InputField"><input
									type="submit" name="submitCrSub" id="submitCrSub"
									value="Submit" onClick="return createSumbit();"
									style="font-size: 13px;" /> <input id="cancel" type="submit"
									title="Cancel" value="Cancel" onclick="return cancelFormTrigger();"
									style="width: 70px; font-size: 13px;" /></td>
							</tr>
						</logic:equal>

						<logic:equal name="triggerCodeForm" property="actionMode"
							value="Edit">
							<tr>
								<td colspan="2" align="right" class="InputField"><input
									type="submit" name="submitCrSub" id="submitCrSub"
									value="Update" onClick="return editSumbit();"
									style="font-size: 13px;" /> <input id="cancel" type="submit"
									title="Cancel" value="Cancel" onclick="return cancelFormTrigger();"
									style="width: 70px; font-size: 13px;" /></td>
							</tr>
						</logic:equal>
						<logic:equal name="triggerCodeForm" property="actionMode"
							value="View">
							<tr>
								<td colspan="2" align="right" class="InputField"><input
									id="edit" type="button" title="Edit" value="Edit"
									onclick="return editFromView();"
									style="width: 70px; font-size: 13px;" /> <input id="cancel"
									type="submit" title="Cancel" value="Cancel"
									onclick="return cancelFormTrigger();"
									style="width: 70px; font-size: 13px;" /></td>

							</tr>
						</logic:equal>


					</nedss:container>
				</fieldset>
			</nedss:container></td>
	</tr>
</table>
