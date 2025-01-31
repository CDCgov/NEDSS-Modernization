<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout"
	prefix="layout"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants"%>
<%@ page isELIgnored="false"%>
<tr>
	<td>
		<fieldset style="border-width: 0px;" id="condition">
			<nedss:container id="section3" name="Action" classType="sect"
				includeBackToTopLink="no">
				<!-- Form Entry Errors -->
				<nedss:container id="subsec4" classType="subSect"
					addedClass="actionSect" name="Action">
					<tr>
						<td class="fieldName" id="ActionListL"><font
							class="boldTenRed"> * </font><span>Action:</span></td>
						<td><html:select title= "Action" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ACTION+")"%>'
								onchange="getActionRelatedFields()" styleId="ActionList">
								<html:optionsCollection property="codedValue(NBS_EVENT_ACTION)"
									value="key" label="value" />
							</html:select> <html:hidden styleId="actionVal"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ACTION+")"%>' />
						</td>
					</tr>
					<!-- Investigation Type (Related Page) -->
					<tr>
						<td class="fieldName" id="RelatedLabel"><font
							class="boldTenRed"> * </font><span>Investigation
								Type(Related Page):</span></td>
						<td id="RelatedField"><logic:equal name="decisionSupportForm"
								property="actionMode" value="Create">
								<html:select title="Investigation Type(Related Page)" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE+")"%>'
									onchange="changeRelatedPage(this);" styleId="relatedPage">
									<html:optionsCollection property="relatedPage" value="key"
										label="value" />
								</html:select>
							</logic:equal> <logic:equal name="decisionSupportForm" property="actionMode"
								value="Edit">
								<html:select title="Investigation Type(Related Page)" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE+")"%>'
									onchange="changeRelatedPage(this);" styleId="relatedPage">
									<html:optionsCollection property="relatedPage" value="key"
										label="value" />
								</html:select>
							</logic:equal></td>
					</tr>
					<!-- Condition(s) -->
					<tr>
						<td class="fieldName" id="ConditionLabel"><font
							class="boldTenRed"> * </font><span>Condition(s):</span></td>
						<td id="ConditionField">
							<div class="multiSelectBlock">
								<i> (Use Ctrl to select more than one value) </i> <br />
								<html:select title="Condition(s)"
									property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.CONDITIONS+")"%>'
									styleId="conditionList"
									onchange="displaySelectedOptions(this, 'conditionList-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="dwrConditions" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="conditionList-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="fieldName" id="PublishedConditionLabel"><font
							class="boldTenRed"> * </font><span>Condition:</span></td>
						<td id="PublishedConditionField"><html:select title="Condition"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.PUBLISHED_CONDITION+")"%>'
								onchange="changePublishedCondition(this);"
								styleId="PublishedCondition">
								<html:optionsCollection property="publishedConditionDropDown"
									value="key" label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName" id="conditionPageLabel"><font
							class="boldTenRed"> * </font><span>Investigation Type
								(Related Page):</span></td>
						<td id="conditionPageField"><span id="conditionRelatedPage">
								<nedss:view name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE+")"%>'
									methodNm='InvestigationTypeRelatedPage' />
						</span></td>
					</tr>
					<!-- Specific for Action= Create Investigation(TO DO) -->
					<tr>
						<td class="fieldName" id="onFailLabel"><font
							class="boldTenRed"> * </font><span>On Failure to Create
								Investigation:</span></td>
						<td id="onFailField"><html:select title= "On Failure to Create Investigation" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV+")"%>'
								styleId="onfail">
								<html:optionsCollection
									property="codedValue(NBS_FAILURE_RESPONSE)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName" id="onFailReviewLabel"><font
							class="boldTenRed"> * </font><span>On Failure to Mark as
								Reviewed:</span></td>
						<td id="onFailReviewField"><html:select title= "On Failure to Mark as Reviewed" 
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED+")"%>'
								styleId="onfailReview">
								<html:optionsCollection
									property="codedValue(NBS_FAILURE_RESPONSE)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<%-- <tr >
                    <td class="fieldName" colspan="2" id="noActionReasonMessage" >
                       <div align="left" style="word-wrap: break-word" class="multiSelectBlock">
	                   Please enter a Reason for No Further Action and any additional comments
	                   that further explain this action. Note that lab reports will only be 
	                   auto-marked as reviewed for records that have program area and jurisdiction
	                   assignments.
	                   </div>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="noActionReasonLabel">
                        <font class="boldTenRed" > * </font><span>Reason for No Further Action:</span>
                    </td>
                    <td id="noActionReasonField"> 
                   		<html:select name="decisionSupportForm" property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NO_ACTION_REASON+")"%>" styleId = "noActionReason">
                           	<html:optionsCollection property="codedValue(RSN_NO_FURTHER_ACTION)"  value="key" label="value"/>
                       	</html:select> 
                    </td>
                </tr>
                 <tr>
                    <td class="fieldName" id="AdditionalCommentL">
                        <span>Additional Comments:</span>
                    </td>
                    <td id="AdditionalComment"> 
                   		<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" onkeyup="chkMaxLength(this,2000)" 
                       onkeydown="chkMaxLength(this,2000)"   name="decisionSupportForm" property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADDITIONAL_COMMENTS+")"%>" styleId="AdditionalComment"/> 
                    </td>
                </tr> --%>
					<%-- <tr>
                    <td class="fieldName" id="updateLabel">
                        <font class="boldTenRed" > * </font><span>Update Action:</span>
                    </td>
                    <td id="updateField"> 
                   		<html:select name="decisionSupportForm" property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.UPDATE_ACTION+")"%>" styleId = "UpdateAct">
                            <html:optionsCollection property="codedValue(NBS_UPDATE_ACTION)" value="key" label="value"/>
                        </html:select>
                    </td>
                </tr>  --%>
				</nedss:container>

				<!--  Subsection: Advanced Criteria -->

				<nedss:container id="IdAdvancedSubSection" classType="subSect"
					name="Advanced Criteria [IF Logic]">
					<tr>
						<td colspan="2"  width="100%">
										<div class="infoBox" style="display: none; visibility: none;"
											id="IdAdvancedSubSectionInfoMessages">
											Select the type of logic that should be applied to the lab
											criteria entered in the table below. All test/result data
											entered below<br> will have either AND or OR logic
											applied.
										</div>
										<div class="infoBox errors"
											style="display: none; visibility: none;"
											id="IdAdvancedSubSectionerrorMessages">
											<b> <a
												name="IdAdvancedSubSectionerrorMessages_errorMessagesHref"></a>
												Please fix the following errors:
											</b> <br />
										</div>
										<table class="dtTable">
											<thead>
												<td width="10%" colspan="3" style="background-color: #EFEFEF;border:1px solid #666666">&nbsp;</td>
												<th width="40%"><font color="black">Question</font></th>
												<th width="15%"><font color="black">Logic</font></th>
												<th width="35%"><font color="black">Value</font></th>
											</thead>

											<tbody id="questionbodyIdAdvancedSubSection">
												<tr id="patternIdAdvancedSubSection" class="odd"
													style="display: none">
													<td style="width: 3%; text-align: center;"><input
														id="viewIdAdvancedSubSection" type="image"
														src="page_white_text.gif"
														onclick="viewAdvCriteriaClicked(this.id,'IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="View" alt="View"></td>
													<td style="width: 3%; text-align: center;"><input
														id="editIdAdvancedSubSection" type="image"
														src="page_white_edit.gif" tabIndex="0"
														onclick="editAdvCriteriaClicked(this.id,'IdAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Edit" alt="Edit"></td>
													<td style="width: 3%; text-align: center;"><input
														id="deleteIdAdvancedSubSection" type="image"
														src="cross.gif" tabIndex="0"
														onclick="deleteAdvCriteriaClicked(this.id,'IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Delete" alt="Delete"></td>
													<td width="40%" align="left"><span
														id="tableAdvQueListTxt"> </span></td>

													<td width="15%" align="left"><span
														id="tableAdvLogicTxt"> </span></td>

													<td width="35%" align="left"><span
														id="tableAdvValValueTxt"> </span></td>
												</tr>
											</tbody>
											<tbody id="questionbodyIdAdvancedSubSection">
												<tr id="nopatternIdAdvancedSubSection" class="odd">
												
													<td colspan="21"><span>&nbsp; No Data has been
															entered. </span></td>
												</tr>
											</tbody>
										</table>
									</td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="questionAdvL" title="Question">
								Question:</span></td>
						<td><html:select title= "Question" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_QUESTION+")"%>'
								styleId="questionAdvList"
								onchange="clearAdvSelection(this);getDWRLogic(this);changeDarkBGColor('IdAdvancedSubSection');getDWRAdvValue(this);"
								onblur="selectAdvacedCriteriaBlur();">
								<html:optionsCollection name="decisionSupportForm"
									property="advDwrQuestions" value="key" label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="advLogicL" title="Logic">
								Logic:</span></td>
						<td><html:select title= "Logic" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_LOGIC+")"%>'
								styleId="advLogicList"
								onchange="changeDarkBGColor('IdAdvancedSubSection');">
								<html:optionsCollection name="decisionSupportForm"
									property="dwrAdvanceValues" value="key" label="value" />
							</html:select></td>
					</tr>

					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class="InputFieldLabel" id="advValueL" title="Value">
								Value: </span></td>
						<td>
							<div id="advDate">
								<html:text title= "Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
									maxlength="10" size="10" styleId="advVal_date"
									onkeyup="DateMask(this,null,event)"
									onchange="changeDarkBGColor('IdAdvancedSubSection');" />
								<html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('advVal_date','advVal_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('advVal_date','advVal_dateIcon', event);"
									
									styleId="advVal_dateIcon"></html:img>
							</div>
							<div id="advText">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
									styleId="advVal_text" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);" />
							</div> <html:textarea title="Value" style="WIDTH: 500px; HEIGHT: 100px;"
								onkeyup="chkMaxLength(this,2000)" styleId="advVal_textArea"
								onkeydown="chkMaxLength(this,2000)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
								onchange="changeDarkBGColor('IdAdvancedSubSection');" />
							<div id="advS_sel">
								<html:select title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
									styleId="advValueList1"
									onchange="changeDarkBGColor('IdAdvancedSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>

							<div class="multiSelectBlock" id="advM_sel">
								<i> (Use Ctrl to select more than one) </i> <br />
								<html:select title="Value"
									property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
									styleId="advValueList2"
									onchange="displaySelectedOptions(this, 'advValueList2-selectedValues'); changeDarkBGColor('IdAdvancedSubSection');"
									multiple="true" size="4">
									<html:optionsCollection property="dwrValue" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="advValueList2-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>

							<div id="advNum_Coded">
								<html:text title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE1)"
									styleId="advVal_num" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdSubSection');" />
								<html:select title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE2)"
									styleId="advVal_code"
									onchange="changeDarkBGColor('IdAdvancedSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>
							<div id="advNum_Literal">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>'
									styleId="advVal_lit" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdSubSection');" />
							</div>

						</td>
					</tr>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Create">
						<tr id="AddButtonToggleIdAdvancedSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateAdvancedCriteriaBatchFields())writeAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Edit">
						<tr id="AddButtonToggleIdAdvancedSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateAdvancedCriteriaBatchFields())writeAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<tr id="UpdateButtonToggleIdAdvancedSubSection"
						style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Update   "
							onclick="if(ValidateAdvancedCriteriaBatchFields())updateAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="AddNewButtonToggleIdAdvancedSubSection"
						style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Add New   "
							onclick="addNewAdvancedBatchIdEntryFields('IdAdvancedSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</nedss:container>
				<nedss:container id="ElrTheAdvancedSubSection" classType="subSect"
					name="Lab Criteria Logic">
											<tr>
												<td colspan="3"><span>Select the type of logic
														that should be applied to the lab criteria entered in the
														table below. All test/result data entered below will have
														either AND or OR logic applied.</span></td>
											</tr>
											<tr>
															<td nowrap class="fieldName" id="AndOrL"><font
																class="boldTenRed"> * </font><span>Apply the
																	following logic to the lab criteria below:</span></td>
															<td colspan="2"><html:radio title= "ANY of the criteria are met (OR logic)" name="decisionSupportForm"
																	
																	property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.AND_OR_LOGIC+")"%>'
																	styleId="andOrLogicRadio" value="OR"> ANY of the criteria are met (OR logic)</FONT>
																</html:radio></td>

											</tr>
											<tr>
															<td></td>
															<td colspan="2"><html:radio title="ALL of the criteria are met (AND logic)" name="decisionSupportForm"
																	property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.AND_OR_LOGIC+")"%>'
																	styleId="andOrLogicRadio" value="AND"> ALL of the criteria are met (AND logic)</FONT>
															</html:radio></td>
											</tr>
				</nedss:container>


				<nedss:container id="ElrIdAdvancedSubSection" classType="subSect"
					name="Lab Criteria">
					<tr>
						<td colspan="3" width="100%">
									<div class="infoBox errors"
											style="display: none; visibility: none;"
											id="ElrIdAdvancedSubSectionerrorMessages">
											<b> <a
												name="IdAdvancedSubSectionerrorMessages_errorMessagesHref"></a>
												Please fix the following errors:
											</b> <br />
										</div>
										<table class="dtTable" align="center">
											<thead>
												<td width="10%" colspan="3" style="background-color: #EFEFEF;border:1px solid #666666">&nbsp;</td>
												<th width="40%"><font color="black">Resulted
														Test</font></th>
												<th width="15%"><font color="black">Operator</font></th> 
												<th width="35%"><font color="black">Result</font></th>
											</thead>

											<tbody id="questionbodyIdElrAdvancedSubSection">
												<tr id="patternIdElrAdvancedSubSection" class="odd"
													style="display: none">
													<td style="width: 3%; text-align: center;"><input
														id="viewIdElrAdvancedSubSection" type="image"
														src="page_white_text.gif"
														onclick="viewElrAdvCriteriaClicked(this.id,'ElrIdAdvancedSubSection','patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="View" alt="View"></td>
													<td style="width: 3%; text-align: center;"><input
														id="editIdElrAdvancedSubSection" type="image"
														src="page_white_edit.gif" tabIndex="0"
														onclick="editElrAdvCriteriaClicked(this.id,'ElrIdAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Edit" alt="Edit"></td>
													<td style="width: 3%; text-align: center;"><input
														id="deleteIdElrAdvancedSubSection" type="image"
														src="cross.gif" tabIndex="0"
														onclick="deleteElrAdvCriteriaClicked(this.id,'ElrIdAdvancedSubSection','patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Delete" alt="Delete"></td>
													<td width="40%" align="left"><span
														id="tableResultedTestName"> </span></td>
													<td width="15%" align="left"><span
														id="tableResultNameOperatorTxt"> </span></td>
													<td width="35%" align="left"><span
														id="tableResultNameTxt"> </span></td>
												</tr>
											</tbody>
											<tbody id="questionbodyIdElrAdvancedSubSection">
												<tr id="nopatternElrIdAdvancedSubSection" class="odd">
													<td colspan="21"><span>&nbsp; No Data has been
															entered. </span></td>
												</tr>
											</tbody>
										</table>
						</td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="resultedTestL" title="Resulted Test">
								Resulted Test:</span></td>

						<td><span class="test2"> <html:hidden
									styleId="testCodeId" property="attributeMap.TestCode" /> <html:hidden
									styleId="testDescriptionId"	property="attributeMap.ResultedTestDescriptionWithCode" /> <span
								id="testDescription" title="Test Description">${decisionSupportForm.attributeMap.ResultedTestDescriptionWithCode}</span>
						</span></td>

						<td colspan="" align="right"><input id="testSearchButton"
							type="button" value="     Search    "
							onclick="searchLabResultedTest()" /> <input id="testClearButton"
							type="button" value="     Clear    "
							onclick="clearTestedResult()" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>

					<tr>
						<td class="fieldName">&nbsp;</td>
						<td colspan="2"><span id="testResultNote"
							title="Test Result Note"> (One of the following is
								required for the Resulted Test: Coded Result, Numeric Result, or
								Text Result) </span></td>
					</tr>

					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="codeResultL" title="Code Result">
								Coded Result:</span></td>

						<td><span class="test2"> <html:hidden
									styleId="codeResultId" property="attributeMap.ResultCode" /> <html:hidden
									styleId="resultDescriptionId"
									property="attributeMap.ResultDescription" /> <span
								id="resultDescription" title="Result Description">${decisionSupportForm.attributeMap.ResultDescription}</span>
						</span></td>

						<td colspan="" align="right"><input id="codeSearchButton"
							type="button" value="     Search    "
							onclick="searchCodeResult()" /> <input id="codeClearButton"
							type="button" value="     Clear    " onclick="clearCodeResult()" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>

					<%-- <tr>
				 	<td class="fieldName">
					 	<font class="boldTenRed" > * </font><span class=" InputFieldLabel" id="codedResultAdvL" title="Coded Result">
					 	Coded Result:</span>
				 	</td>
				 	<td colspan="2">
					 	<html:select name="decisionSupportForm" property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CODED_RESULT+")"%>" styleId="codedResultAdvList" onkeyup="changeElrResultSelection('codedResultAdvList');" onchange="changeElrResultSelection('codedResultAdvList');changeElrSectionDarkBGColor()" >
					 	<html:optionsCollection name="decisionSupportForm" property="codedResultList" value="key" label="value" /></html:select>
				 	</td>
			 	</tr> --%>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="numericResultL"
							title="Numeric Result"> Numeric Result:</span></td>
						<td colspan="3">
							<table role="presentation" id="numericResultTable" style="padding: 0; margin: 0; border: 0;">
								<tr>
									<td>
										<html:select title="Numeric Result operator" name="decisionSupportForm"
											property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT_CRITERIA+")"%>' styleId="numericResultOperatorList"
											onkeyup="changeElrResultSelection('numericResultOperatorList');"
											onchange="changeElrResultSelection('numericResultOperatorList');changeElrSectionDarkBGColor()">
											<html:optionsCollection name="decisionSupportForm" property="numericResultList" value="key" label="value" />
										</html:select> 
										
										<%-- 
										<html:hidden value="=" styleId="numericResultOperatorList" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT_CRITERIA+")"%>'/>
										<html:hidden value="Equal" styleId="numericResultOperatorList_textbox" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT+")"%>'/>
										--%>
									</td>
									<td>&nbsp;</td>
									<td>
										<html:text title="Numeric Result" name="decisionSupportForm"
											property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT+")"%>'
											styleId="numericResult_text" size="15" maxlength="100"
											onkeyup="changeElrResultSelection('numericResult_text');"
											onchange="changeElrResultSelection('numericResult_text');changeElrSectionDarkBGColor()" />
									&nbsp;</td>
									<td>
										<div id="numericResultHigh_div">
										And &nbsp;
											<html:text title="Numeric Result" name="decisionSupportForm"
												property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT+")"%>'
												styleId="numericResultHigh_text" size="15" maxlength="100"
												onchange="changeDarkBGColor('IdSubSection');" />&nbsp;

										</div>
									</td>
									<td>
										<html:select title="Numeric result type" name="decisionSupportForm"
											property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NUMERIC_RESULT_TYPE+")"%>'
											styleId="numericResultTypeList"
											onkeyup="changeElrResultSelection('numericResultTypeList');"
											onchange="changeElrResultSelection('numericResultTypeList');changeElrSectionDarkBGColor()">
											<html:optionsCollection name="decisionSupportForm"
												property="resultTypeList" value="key" label="value" />
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class="InputFieldLabel" id="textResultL" title="Text Result">
								Text Result: </span></td>
						<td colspan="3">
							<table role="presentation" id="textResultTable" style="padding: 0; margin: 0; border: 0;">
								<td valign="top">
									<html:select title="Text Result operator" name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.TEXT_RESULT_CRITERIA+")"%>' styleId="resultOperatorList" 
										onkeyup="changeElrResultSelection('resultOperatorList');"
										onchange="changeElrResultSelection('resultOperatorList');changeElrSectionDarkBGColor()">
										<html:optionsCollection property="textResultList" value="key" label="value" />
									</html:select>
									
									<%--
									<html:hidden value="=" styleId="resultOperatorList" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.TEXT_RESULT_CRITERIA+")"%>'/>
									<html:hidden value="Equal" styleId="resultOperatorList_textbox" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT+")"%>'/>
									--%>
								</td>
								<td>&nbsp;</td>
								<td><html:text title="Text Result" name="decisionSupportForm"
										property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.TEXT_RESULT+")"%>'
										styleId="textResult_text" size="30" maxlength="100"
										onkeyup="changeElrResultSelection('textResult_text');"
										onchange="changeElrResultSelection('textResult_text');changeElrSectionDarkBGColor()" />
								</td>
							</table>
						</td>
					</tr>

					<tr id="AddButtonToggleIdELRAdvancedSubSection">
						<td colspan="3" align="right"><input type="button"
							value="     Add    "
							onclick="if(ValidateElrAdvancedCriteriaBatchFields())writeElrAdvCriteriaBatchIdEntry('ElrIdAdvancedSubSection','patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection');" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="UpdateButtonToggleIdELRAdvancedSubSection"
						style="display: none">
						<td colspan="3" align="right"><input type="button"
							value="   Update   "
							onclick="if(ValidateElrAdvancedCriteriaBatchFields())updateElrAdvCriteriaBatchIdEntry('ElrIdAdvancedSubSection','patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="AddNewButtonToggleIdELRAdvancedSubSection"
						style="display: none">
						<td colspan="3" align="right"><input type="button"
							value="   Add New   "
							onclick="addNewElrAdvCriteriaBatchIdEntry('ElrIdAdvancedSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</nedss:container>

				<!-- Subsection: Investigation Criteria -->
				<nedss:container id="invCriteriaSubSection" classType="subSect"
					name="Investigation Criteria" addedClass="batchSubSection">
					<tr>
						<td colspan="3" width="100%"><span>If 'Mark as
									Reviewed' is selected as the action above, and the
									investigation logic defined below are met, then the incoming
									document will be 'auto-associated' to the pre-existing
									investigation that is found. </span></td>
					</tr>

					<tr>
						<td nowrap class="fieldName" id="applyInvLogicL"><font
							class="boldTenRed"> * </font><span>Apply investigation
								logic to this algorithm: </span></td>
						<td colspan="2"><html:radio
								title="Yes (Apply Investigation Logic)"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_INV_CRITERIA_LOGIC
								+ ")"%>'
								onclick="enableDisableInvLogic(this);"
								styleId="useInvLogicRadioYes" value="Y"> Yes </FONT>
							</html:radio> <html:radio title="No (Don't Apply Investigation Logic)"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_INV_CRITERIA_LOGIC
								+ ")"%>'
								onclick="enableDisableInvLogic();"
								styleId="useInvLogicRadioNo" value="N"> No </FONT>
							</html:radio>
							<html:hidden styleId="useInvLogic" property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_INV_CRITERIA_LOGIC
								+ ")"%>'/>
							
							</td>
					</tr>
				</nedss:container>

				<nedss:container id="IdAdvancedInvSubSection" classType="subSect"
					name="Advanced Investigation Criteria [IF Logic]">
					<tr>
						<td colspan="2"  width="100%">
										<div class="infoBox errors"
											style="display: none; visibility: none;"
											id="IdAdvancedInvSubSectionerrorMessages">
											<b> <a
												name="IdAdvancedInvSubSectionerrorMessages_errorMessagesHref"></a>
												Please fix the following errors:
											</b> <br />
										</div>
										<table class="dtTable">
											<thead>
												<td width="10%" colspan="3" style="background-color: #EFEFEF;border:1px solid #666666">&nbsp;</td>
												<th width="40%"><font color="black">Question</font></th>
												<th width="15%"><font color="black">Logic</font></th>
												<th width="35%"><font color="black">Value</font></th>
											</thead>

											<tbody id="questionbodyIdAdvancedInvSubSection">
												<tr id="patternIdAdvancedInvSubSection" class="odd"
													style="display: none">
													<td style="width: 3%; text-align: center;"><input
														id="viewIdAdvancedInvSubSection" type="image"
														src="page_white_text.gif"
														onclick="viewAdvInvCriteriaClicked(this.id,'IdAdvancedInvSubSection','patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="View" alt="View"></td>
													<td style="width: 3%; text-align: center;"><input
														id="editIdAdvancedInvSubSection" type="image"
														src="page_white_edit.gif" tabIndex="0"
														onclick="editAdvInvCriteriaClicked(this.id,'IdAdvancedInvSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Edit" alt="Edit"></td>
													<td style="width: 3%; text-align: center;"><input
														id="deleteIdAdvancedInvSubSection" type="image"
														src="cross.gif" tabIndex="0"
														onclick="deleteAdvInvCriteriaClicked(this.id,'IdAdvancedInvSubSection','patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Delete" alt="Delete"></td>
													<td width="40%" align="left"><span
														id="tableAdvInvQueListTxt"> </span></td>

													<td width="15%" align="left"><span
														id="tableAdvInvLogicTxt"> </span></td>

													<td width="35%" align="left"><span
														id="tableAdvInvValValueTxt"> </span></td>
												</tr>
											</tbody>
											<tbody id="questionbodyIdAdvancedInvSubSection">
												<tr id="nopatternIdAdvancedInvSubSection" class="odd">
												
													<td colspan="21"><span>&nbsp; No Data has been
															entered. </span></td>
												</tr>
											</tbody>
										</table>
									</td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="questionAdvInvL" title="Question">
								Question:</span></td>
						<td><html:select title= "Question" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION+")"%>'
								styleId="questionAdvInvList"
								onchange="clearAdvInvSelection(this);getDWRInvLogic(this);changeDarkBGColor('IdAdvancedInvSubSection');getDWRAdvInvValue(this);"
								onblur="selectAdvacedInvCriteriaBlur();">
								<html:optionsCollection name="decisionSupportForm"
									property="advInvDwrQuestions" value="key" label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="advInvLogicL" title="Logic">
								Logic:</span></td>
						<td><html:select title= "Logic" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC+")"%>'
								styleId="advInvLogicList"
								onchange="changeDarkBGColor('IdAdvancedInvSubSection');">
								<html:optionsCollection name="decisionSupportForm"
									property="dwrAdvanceInvValues" value="key" label="value" />
							</html:select></td>
					</tr>

					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class="InputFieldLabel" id="advInvValueL" title="Value">
								Value: </span></td>
						<td>
							<div id="advInvDate">
								<html:text title= "Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
									maxlength="10" size="10" styleId="advInvVal_date"
									onkeyup="DateMask(this,null,event)"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');" />
								<html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('advInvVal_date','advInvVal_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('advInvVal_date','advInvVal_dateIcon', event);"
									
									styleId="advInvVal_dateIcon"></html:img>
							</div>
							<div id="advInvText">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
									styleId="advInvVal_text" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');changeLightBGColorOfAdvancedInvBatch('','',this);" />
							</div> <html:textarea title="Value" style="WIDTH: 500px; HEIGHT: 100px;"
								onkeyup="chkMaxLength(this,2000)" styleId="advInvVal_textArea"
								onkeydown="chkMaxLength(this,2000)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
								onchange="changeDarkBGColor('IdAdvancedInvSubSection');" />
							<div id="advInvS_sel">
								<html:select title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
									styleId="advInvValueList1"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>

							<div class="multiSelectBlock" id="advInvM_sel">
								<i> (Use Ctrl to select more than one) </i> <br />
								<html:select title="Value"
									property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
									styleId="advInvValueList2"
									onchange="displaySelectedOptions(this, 'advInvValueList2-selectedValues'); changeDarkBGColor('IdAdvancedInvSubSection');"
									multiple="true" size="4">
									<html:optionsCollection property="dwrValue" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="advInvValueList2-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>

							<div id="advInvNum_Coded">
								<html:text title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE1)"
									styleId="advInvVal_num" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');" />
								<html:select title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE2)"
									styleId="advInvVal_code"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>
							<div id="advInvNum_Literal">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADV_INV_CRITERIA_VALUE+")"%>'
									styleId="advInvVal_lit" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdAdvancedInvSubSection');" />
							</div>

						</td>
					</tr>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Create">
						<tr id="AddButtonToggleIdAdvancedInvSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateAdvancedInvCriteriaBatchFields())writeAdvInvCriteriaBatchIdEntry('IdAdvancedInvSubSection','patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Edit">
						<tr id="AddButtonToggleIdAdvancedInvSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateAdvancedInvCriteriaBatchFields())writeAdvInvCriteriaBatchIdEntry('IdAdvancedInvSubSection','patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<tr id="UpdateButtonToggleIdAdvancedInvSubSection"
						style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Update   "
							onclick="if(ValidateAdvancedInvCriteriaBatchFields())updateAdvInvCriteriaBatchIdEntry('IdAdvancedInvSubSection','patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="AddNewButtonToggleIdAdvancedInvSubSection"
						style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Add New   "
							onclick="addNewAdvancedInvBatchIdEntryFields('IdAdvancedInvSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</nedss:container>
				



				<!-- Subsection: Time Frame Logic -->
				<nedss:container id="timeFrameSubSection" classType="subSect"
					name="Time Frame Logic" addedClass="batchSubSection">
					<tr>
						<td colspan="3" width="100%"><span><b>If the
									number of days falls within the time frame specified below, the
									action specified in the algorithm will be applied. If No is selected, the logic will check for any investigation EVER (regardless of time frame).</b> The number
								of days is calculated as the difference between the incoming
								electronic lab report (specimen collection date) and existing
								investigations for the same patient and condition (specimen
								collection date of earliest associated lab or investigation
								create date if no associated lab). </span></td>
					</tr>

					<tr>
						<td nowrap class="fieldName" id="applyTimeFrameLogicL"><font
							class="boldTenRed"> * </font><span>Apply time frame logic
								to this algorithm: </span></td>
						<td colspan="2"><html:radio title="Yes (Apply timeframe)"
								onclick="enableDisableTimeLogic(this);"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_EVENT_DATE_LOGIC
								+ ")"%>'
								styleId="useEventDateLogicRadioYes" value="Y"> Yes </FONT>
							</html:radio> <html:radio title="No (Don't Apply timeframe)"
								onclick="enableDisableTimeLogic()"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_EVENT_DATE_LOGIC
								+ ")"%>'
								styleId="useEventDateLogicRadioNo" value="N"> No </FONT>
							</html:radio>
							<html:hidden styleId="useEventDateLogic" property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.USE_EVENT_DATE_LOGIC
								+ ")"%>'/>
							</td>
					</tr>

					<tr>
						<td class="fieldName" id="EventDateTypeSelL"><font
							class="boldTenRed"> * </font><span>Event Date: </span></td>
						<td colspan="2"><html:select title="Event Date"
								name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.NBS_EVENT_DATE_SELECTED
								+ ")"%>'
								styleId="EventDateTypeSel">
								<html:optionsCollection property="codedValue(NBS_EVENT_DATE)"
									value="key" label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName" id="TimeFrameOpSelL"><font
							class="boldTenRed"> * </font><span>Time Frame: </span></td>
						<td colspan="2">
							<table role="presentation" id="timeFrameTable"
								style="padding: 0; margin: 0; border: 0;">
								<tr>
									<td><html:select title="Timeframe"
											name="decisionSupportForm"
											property='<%="decisionSupportClientVO.answer("
								+ DecisionSupportConstants.TIMEFRAME_OPERATOR_SELECTED
								+ ")"%>'
											styleId="TimeFrameOpSel" disabled="true">
											<html:optionsCollection
												property="codedValue(NBS_BRE_LOGIC_1)" value="key"
												label="value" />
										</html:select></td>
									<td valign="center">&nbsp; <html:text
											name="decisionSupportForm"
											property='<%="decisionSupportClientVO.answer("
							+ DecisionSupportConstants.TIMEFRAME_DAYS + ")"%>'
											size="4" maxlength="4" title="The number of days."
											styleId="TimeFrameDays"
											onkeyup="isNumericCharacterEntered(this)" disabled="true" />
										<span id="spanDays"> Days</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>

				</nedss:container>
				<!-- Subsection: Investigation Default Values -->
				<nedss:container id="IdSubSection" classType="subSect"
					name="Investigation Default Values" addedClass="batchSubSection">
					<tr>
						<td colspan="2" width="100%">
										<div class="infoBox" style="display: none; visibility: none;"
											id="IdSubSectionInfoMessages">
											Please enter the default values for auto-created
											investigations. Note that investigations will only be
											auto-created for lab reports that have program area and
											jurisdiction <br> assignments.
										</div>
										<div class="infoBox errors"
											style="display: none; visibility: none;"
											id="IdSubSectionerrorMessages">
											<b> <a name="IdSubSectionerrorMessages_errorMessagesHref"></a>
												Please fix the following errors:
											</b> <br />
										</div>
										<table class="dtTable" align="center" id="actionQuestionTable">
											<thead>
												<td width="10%" colspan="3" style="background-color: #EFEFEF;border:1px solid #666666">&nbsp;</td>
												<th width="40%"><font color="black">Question</font></th>
												<th width="15%"><font color="black">Value</font></th>
												<th width="35%"><font color="black">Behavior</font></th>
											</thead>

											<tbody id="questionbodyIdSubSection">
												<tr id="patternIdSubSection" class="odd"
													style="display: none">
													<td style="width: 3%; text-align: center;"><input
														id="viewIdSubSection" type="image"
														src="page_white_text.gif"
														onclick="viewClicked(this.id,'IdSubSection','patternIdSubSection','questionbodyIdSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="View" alt="View"></td>
													<td style="width: 3%; text-align: center;"><input
														id="editIdSubSection" type="image"
														src="page_white_edit.gif" tabIndex="0"
														onclick="editClicked(this.id,'IdSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Edit" alt="Edit"></td>
													<td style="width: 3%; text-align: center;"><input
														id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0"
														onclick="deleteClicked(this.id,'IdSubSection','patternIdSubSection','questionbodyIdSubSection');return false"
														name="image" align="middle" cellspacing="2"
														cellpadding="3" border="55" class="cursorHand"
														title="Delete" alt="Delete"></td>
													<td width="40%" align="left"><span
														id="tablequeListTxt"> </span></td>

													<td width="15%" align="left"><span
														id="tablevalValueTxt"> </span></td>

													<td width="35%" align="left"><span
														id="tablebehaviorTxt"> </span></td>
												</tr>
											</tbody>
											<tbody id="questionbodyIdSubSection">
												<tr id="nopatternIdSubSection" class="odd">
												
													<td colspan="21"><span>&nbsp; No Data has been
															entered. </span></td>
												</tr>
											</tbody>
										</table>
						</td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="questionL" title="Question">
								Question:</span></td>
						<td><html:select title="Question" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.QUESTION+")"%>'
								styleId="questionList" onblur="selectBlur();"
								onchange="changeDarkBGColor('IdSubSection');getDWRValue(this);">
								<html:optionsCollection name="decisionSupportForm"
									property="dwrQuestions" value="key" label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class=" InputFieldLabel" id="valueL" title="Value"> Value:</span>
						</td>
						<td>
							<div id="date">
							<html:radio title="Use Current Date" name="decisionSupportForm"
								onclick="currentSelectDateLogic(this)"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CURRENT_SELECT_DATE_LOGIC+")"%>'
								styleId="CURRENT_CURRENT_SELECT_DATE_LOGIC" value="CurrentDate">Use Current Date</FONT>
								</html:radio>
								</br>
								<html:radio title="Select date" name="decisionSupportForm"
								onclick="currentSelectDateLogic(this)"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CURRENT_SELECT_DATE_LOGIC+")"%>'
								styleId="SELECT_CURRENT_SELECT_DATE_LOGIC" value="SelectDate">
								</html:radio>
								<html:text title="Enter date" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.VALUE+")"%>'
									maxlength="10" size="10" styleId="Val_date"
									onkeyup="DateMask(this,null,event)"
									onchange="changeDarkBGColor('IdSubSection');" />
								<html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('Val_date','Val_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('Val_date','Val_dateIcon', event);"
									
									styleId="Val_dateIcon"></html:img>
							</div>
							<div id="text">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.VALUE+")"%>'
									styleId="Val_text" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdSubSection');" />
							</div> <html:textarea title="Value" style="WIDTH: 500px; HEIGHT: 100px;"
								onkeyup="chkMaxLength(this,2000)" styleId="Val_textArea"
								onkeydown="chkMaxLength(this,2000)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.VALUE+")"%>'
								onchange="changeDarkBGColor('IdSubSection');" />
							<div id="s_sel">
								<html:select title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.VALUE+")"%>'
									styleId="valueList1"
									onchange="changeDarkBGColor('IdSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>

							<div class="multiSelectBlock" id="m_sel">
								<i> (Use Ctrl to select more than one) </i> <br />
								<html:select title="Value"
									property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.VALUE+")"%>'
									styleId="valueList2"
									onchange="displaySelectedOptions(this, 'valueList2-selectedValues'); changeDarkBGColor('IdSubSection');"
									multiple="true" size="4">
									<html:optionsCollection property="dwrValue" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="valueList2-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>

							<div id="Num_Coded">
								<html:text title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE1)"
									styleId="Val_num" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdSubSection');" />
								<html:select title="Value" name="decisionSupportForm"
									property="decisionSupportClientVO.answer(VALUE2)"
									styleId="Val_code"
									onchange="changeDarkBGColor('IdSubSection');">
									<html:optionsCollection name="decisionSupportForm"
										property="dwrValue" value="key" label="value" />
								</html:select>
							</div>
							<div id="Num_Literal">
								<html:text title="Value" name="decisionSupportForm"
									property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.VALUE+")"%>'
									styleId="Val_lit" size="30" maxlength="100"
									onchange="changeDarkBGColor('IdSubSection');" />
							</div>
							<div id="Part_Per_Div">
								<span id="clearPartPer" class="none">
								<input type="button" class="Button" value="Clear/Reassign" id="PartPerCodeClearButton" onclick="clearPartPerson('PartPer')"/>
								</span>
								<span id="PartPerSearchControls">
								<input type="button" class="Button" value="Search" id="PartPerIcon" onclick="getDwrEntitySearch();" />
								<span id = "theOrSpan">&nbsp; - OR - &nbsp;</span>
								<html:text title="Quick Code" property="decisionSupportClientVO.answer(CriteriaValue)" styleId="PartPerText" size="5" maxlength="10" />
								<input type="button" class="Button" value="Quick Code Lookup" id="PartPerCodeLookupButton"  onclick="getDwrPartPerson('PartPer')"/>
								</span>
								<div id="PartPerS" style="margin: 0.25em;">
									<b>Selected: </b>
									<span id="PartPer"></span>
									
								</div>
									<td><html:hidden styleId="PartPerUid" property="attributeMap.PartPerUid"/></td>
									<td><html:hidden styleId="PartEntityType" property="attributeMap.PartEntityType"/></td>
								<tr>
									<td colspan="2" style="text-align:center;">
									<span id="PartPerError"/>
									</td>
								</tr>								
							</div>							
						</td>
					</tr>

					<tr>
						<td class="fieldName"><font class="boldTenRed"> * </font><span
							class="InputFieldLabel" id="bahaviorL" title="Behavior">
								Behavior: </span></td>
						<td><html:select title="Behavior" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.BEHAVIOR+")"%>'
								styleId="behavior" onchange="changeDarkBGColor('IdSubSection');">
								<html:optionsCollection name="decisionSupportForm"
									property="codedValue(NBS_DEFAULT_BEHAVIOR)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Create">
						<tr id="AddButtonToggleIdSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateDefaultBatchFields())writeBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection')" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<logic:equal name="decisionSupportForm" property="actionMode"
						value="Edit">
						<tr id="AddButtonToggleIdSubSection">
							<td colspan="2" align="right"><input type="button"
								value="     Add    "
								onclick="if(ValidateDefaultBatchFields())writeBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection')" />&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</logic:equal>
					<tr id="UpdateButtonToggleIdSubSection" style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Update   "
							onclick="if(ValidateDefaultBatchFields())updateBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="AddNewButtonToggleIdSubSection" style="display: none">
						<td colspan="2" align="right"><input type="button"
							value="   Add New   "
							onclick="addNewBatchIdEntryFields('IdSubSection')" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<%-- <tr id="defaultidNote"	style="display:none">
					 <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" onkeyup="chkMaxLength(this,2000)" 
                       onkeydown="chkMaxLength(this,2000)"   name="decisionSupportForm" 
                       property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NOTIFICATION_COMMENTS+")"%>" 
                       styleId="NotComment"/> 
	            </tr> --%>
					<tr id="defaultidNote" style="display: none">
						<td class="fieldName"><span class="InputFieldLabel"
							id="notesL" title="Notes"> Notes: </span></td>
						<td><html:textarea title="Notes" style="WIDTH: 500px; HEIGHT: 100px;"
								onkeyup="chkMaxLength(this,2000)" 
								onkeydown="chkMaxLength(this,2000)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NOTES+")"%>'
								styleId="Notes" /></td>
					</tr>
				</nedss:container>
				<!-- Subsection: NND Notification Details (Specific for Create Inv with NND Notification) TODO-->
				<nedss:container id="subsec6" classType="subSect"
					name="NND Notification Details">
					<tr>
						<td class="fieldName" id="onFailNotL"><font
							class="boldTenRed"> * </font><span>On Failure to Create
								Notification:</span></td>
						<td><html:select title="On Failure to Create Notification" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION+")"%>'
								styleId="onFailNot">
								<html:optionsCollection
									property="codedValue(NBS_NOT_FAILURE_RESPONSE)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName" id="queueAppLabel"><font
							class="boldTenRed"> * </font><span>Queue for Approval:</span></td>
						<td><input type="hidden" name="approval1" value="N">
							<html:radio title="Yes (Queue for approval)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.QUEUE_FOR_APPROVAL+")"%>'
								styleId="approval" value="Y" disabled="true">
								<FONT COLOR="#666666"> Yes</FONT>
							</html:radio> <html:radio title="No (Queue for approval)"  name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.QUEUE_FOR_APPROVAL+")"%>'
								styleId="approval" value="N" disabled="true">
								<FONT COLOR="#666666"> No</FONT>
							</html:radio></td>
					</tr>
					<tr>
						<td class="fieldName" id="NotCommentL"><font
							class="boldTenRed"> * </font><span>Notification Comments:</span>
						</td>
						<td><html:textarea title="Notification Comments" style="WIDTH: 500px; HEIGHT: 100px;"
								onkeyup="chkMaxLength(this,2000)"
								onkeydown="chkMaxLength(this,2000)" name="decisionSupportForm"
								property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NOTIFICATION_COMMENTS+")"%>'
								styleId="NotComment" /></td>
					</tr>
				</nedss:container>
			</nedss:container>
		</fieldset>
		<div class="tabNavLinks">
			<a href="javascript:navigateTab('previous')"> Previous </a>
			&nbsp;&nbsp;&nbsp; <a href="javascript:navigateTab('next')"> Next
			</a>
			<!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
			<input type="hidden" name="endOfTab" />
		</div>
	</td>
</tr>