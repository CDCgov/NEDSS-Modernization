<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page
	import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>


<!-- ################### PAGE TAB ###################### - - -->

<%
	String tabId = "editCaseInfo1";
	tabId = tabId.replace("]", "");
	tabId = tabId.replace("[", "");
	tabId = tabId.replaceAll(" ", "");
	int subSectionIndex = 0;
	int sectionIndex = 0;
	String sectionId = "";
	String[] sectionNames = {"Investigation Information","Reporting Information", "Clinical", "Epidemiologic","General Comments"};
%>
<tr>
	<td>
		<div class="view" id="patientSearchByDetails"
			style="text-align: center;">

			<%
				sectionIndex = 0;
			%>


			<!-- ################# SECTION ################  -->
			<nedss:container id="patSearch1" name="Simple Search"
				defaultDisplay="F" includeBackToTopLink="F" isHidden="F"
				classType="sect">

				<!-- ########### SUB SECTION ###########  -->
				<nedss:container id="patSearch2" displayImg="F" displayLink="F"
					defaultDisplay="F" includeBackToTopLink="F" isHidden="F"
					classType="subSect">
					<tr>
						<td width="20%">&nbsp;</td>
						<td align="" width="13%"><span id=""><span><b>Operators</b></span></span></td>
						<td width="1%"></td>
						<td><span id=""><span><b>Search Criteria</b></span></span></td>

					</tr>
					<tr>
						<td class="fieldName" valign="middle"><span
							class="InputFieldLabel" valign="middle" id="DEM102L" style=""
							title="Patient's Last Name"> Last Name: </span> &nbsp;</td>

						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							<table role="presentation" style="background: #d1e5f6"
								width="98%" align="center">
								<tr>
									<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td>
									<td style="background: #d1e5f6; min-width: 120px" width="98%">

										<html:select title="patient's last Name Operator"
											name="personSearchForm"
											property="personSearch.lastNameOperator" styleId="DEM102O">

											<option value="SW" selected>Starts With</option>
											<option value="CT">Contains</option>
											<option value="=">Equal</option>
											<option value="!=">Not Equal</option>
											<option value="SL">Sounds Like</option>
										</html:select> 
										<SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DEM102O"),"${fn:escapeXml(LNameOp)}");</SCRIPT>
									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>

							</table>
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.lastName"
								styleId="DEM102" title="The patient's last name" /></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM104L" style="" title="Patient's First Name"> First
								Name: </span>&nbsp;</td>

						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							<table role="presentation" style="background: #d1e5f6"
								width="98%" align="center">
								<tr>
									<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td>
									<td style="background: #d1e5f6" width="98%"><html:select
											title="patient's first Name Operator" name="personSearchForm"
											property="personSearch.firstNameOperator" styleId="DEM104O">
											<option value="SW" selected>Starts With</option>
											<option value="CT">Contains</option>
											<option value="=">Equal</option>
											<option value="!=">Not Equal</option>
											<option value="SL">Sounds Like</option>


										</html:select> <SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DEM104O"),"${fn:escapeXml(FNameOp)}");</SCRIPT>
									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>

							</table>
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.firstName"
								styleId="DEM104" title="The patient's first name" /></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM105L" style="" title="Patient's Date of Birth">Date
								of Birth:</span>&nbsp;</td>

						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							<table role="presentation" style="background: #d1e5f6"
								width="98%" align="center">
								<tr>
									<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td>
									<td style="background: #d1e5f6" width="98%">
										<!--  <table id="personSearch.birthTimeOperator_ac_table" cellspacing="0" cellpadding="0" border="0">
												<tr>
												<td>
													<table cellspacing="0" cellpadding="0" border="0" id="personSearch.birthTimeOperator_ac_table">
													<tr>
													<td><input  type="text" name="personSearch.birthTimeOperator_textbox" onfocus="storeCaret(this);AutocompleteStoreOnFocusValue(this);" onkeydown="CheckTab('personSearch.birthTimeOperator',this);" onkeyup="AutocompleteComboBox('personSearch.birthTimeOperator_textbox','personSearch.birthTimeOperator',true);storeCaret(this);" onblur="AutocompleteSynch('personSearch.birthTimeOperator_textbox','personSearch.birthTimeOperator');" value="Equal"><img align="top" alt="" border="0" src="type-ahead2.gif" name="personSearch.birthTimeOperator_button" onclick="AutocompleteExpandListbox('personSearch.birthTimeOperator_textbox','personSearch.birthTimeOperator');"></td>
													</tr>
													<tr>
													<td ><select AUTOCOMPLETE="off" id="personSearch.birthTimeOperator" name="personSearch.birthTimeOperator" default="=" size="5" class="none" onclick="AutocompleteComboBox('personSearch.birthTimeOperator_textbox','personSearch.birthTimeOperator',true,true);this.className=&quot;none&quot;;" onkeyup="AutocompleteComboBox('personSearch.birthTimeOperator_textbox','personSearch.birthTimeOperator',true,true);" onblur="this.className='none';" typeahead="true" onchange="flipDate(this);"><option value="BET">Between</option><option value="=" selected>Equal</option></select></td>
													</tr>
													<tr>
													<td></td>
													</tr>
													</table>
												</td>
												</tr>
												</table>  --> <html:select
											title="patient's birth date Operator" name="personSearchForm"
											property="personSearch.birthTimeOperator" styleId="DEM105O"
											onchange="flipDate(this);">

											<option value="BET">Between&nbsp;&nbsp;&nbsp;&nbsp;</option>
											<option value="=" selected>Equal</option>

										</html:select> <SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DEM105O"),"${fn:escapeXml(DOBOp)}");</SCRIPT>

									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>

							</table>
						</td>
						<td width="1%"></td>
						<td width="60%">
							<div id="fulldate">
								<html:text property="personSearch.birthTimeMonth"
									styleId="patientDOB1" size="2" maxlength="2"
									onkeyup="isNumericCharacterEntered(this);moveFocusParsedInputs(1, this, 'patientDOB',event)"
									title="For searching month of DOB" />
								<html:text property="personSearch.birthTimeDay"
									styleId="patientDOB2" size="2" maxlength="2"
									onkeyup="isNumericCharacterEntered(this);moveFocusParsedInputs(2, this, 'patientDOB',event)"
									title="For searching day of DOB" />
								<html:text property="personSearch.birthTimeYear"
									styleId="patientDOB3" size="4" maxlength="4"
									onkeyup="isNumericCharacterEntered(this);"
									title="For searching year of DOB" />

								<!--  <input id="patientDOB1" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeMonth" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);" >
											<input id="patientDOB2" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeDay" size="2" maxlength="2" onkeyup="isNumericCharacterEntered(this);" >
											<input id="patientDOB3" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeYear" size="4" maxlength="4" onkeyup="isNumericCharacterEntered(this);" >  -->

							</div>
							<div id="betweendate">
								<html:text property="personSearch.beforeBirthTime"
									styleId="patientDOBBet1" size="10" maxlength="10"
									onkeyup="DateMask(this,null,event);" title="Date of Birth from" />
								<!-- <input id="patientDOBBet1" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.beforeBirthTime" size="10" maxlength="10" onkeyup="DateMask(this,null,event)" value="" validate="dateRange" errorCode="" fieldLabel="DOB Begin Date" requiredPartner="personSearch.afterBirthTime" onload="this.value='yes';">-->
								<img tabIndex="0" lign="bottom" alt="Select a Date" border="0"
									src="calendar.gif" name="N10090_button"
									onclick="getCalDate('personSearch.beforeBirthTime','N10090_button');return false;"
									onkeypress="showCalendarEnterKey('personSearch.beforeBirthTime','N10090_button',event);">
									- 
								<html:text property="personSearch.afterBirthTime"
									styleId="patientDOBBet2" size="10" maxlength="10"
									onkeyup="DateMask(this,null,event);" title="Date of Birth to" />
								<!--<input id="patientDOBBet2" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.afterBirthTime" size="10" maxlength="10" onkeyup="DateMask(this,null,event)" value="" validate="dateRange" errorCode="" dateBeforeRef="personSearch.beforeBirthTime" fieldLabel="DOB End Date" requiredPartner="personSearch.beforeBirthTime" onload="this.value='yes';"> -->
								<nobr>
									<img tabIndex="0" align="bottom" alt="Select a Date" border="0"
										src="calendar.gif" name="N1009A_button"
										onclick="getCalDate('personSearch.afterBirthTime','N1009A_button');return false;"
										onkeypress="showCalendarEnterKey('personSearch.afterBirthTime','N1009A_button',event);">
									<SCRIPT Language="JavaScript" Type="text/javascript">flipDateOnload('DEM105O');</SCRIPT>
							</div>
						</td>
					</tr>

					<!--  <table   width="100%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" id="fulldate">
														<tr rowID="N10067">
														<td valign="middle" align="left">
															<table cellspacing="0" cellpadding="0" border="0">
															<tr>
															<td><input id="patientDOB1" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeMonth" size="2" maxlength="2" onkeyup="moveFocusParsedInputs(1, this, 'patientDOB')" onchange="roundOff(this)" value="" validate="validateDOBMonth" errorCode="" fieldLabel="Month" onload="this.value='yes';"></td>
															</tr>
															</table>
														</td><td valign="middle" align="left">
															<table cellspacing="0" cellpadding="0" border="0">
															<tr>
															<td><input id="patientDOB2" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeDay" size="2" maxlength="2" onkeyup="moveFocusParsedInputs(2, this, 'patientDOB')" onchange="roundOff(this);" value="" validate="validateDOBDay" errorCode="" fieldLabel="Day" onload="this.value='yes';"></td>
															</tr>
															</table>
														</td><td valign="middle" align="left" colspan="10">
															<table cellspacing="0" cellpadding="0" border="0">
															<tr>
															<td><input id="patientDOB3" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.birthTimeYear" size="4" maxlength="4" value="" validate="validateDOBYear" errorCode="" fieldLabel="Year" onload="this.value='yes';"></td>
															</tr>
															</table>
														</td>
														</tr>							
														</table>
													</td>	
													<td>
															<table border="0" cellspacing="0" cellpadding="0" id="betweendate">
															<tr rowID="N1008F">
															<td valign="middle" align="left">
																<table cellspacing="0" cellpadding="0" border="0">
																<tr>
																<td><input id="personSearch.beforeBirthTime" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.beforeBirthTime" size="10" maxlength="10" onkeyup="DateMask(this,null,event)" value="" validate="dateRange" errorCode="" fieldLabel="DOB Begin Date" requiredPartner="personSearch.afterBirthTime" onload="this.value='yes';"><nobr><a href="#"><img align="bottom" alt="Select a Date" border="0" src="calendar.gif" name="N10090_button" onclick="getCalDate('personSearch.beforeBirthTime','N10090_button');return false;"></a>
																<center>mm/dd/yyyy</center>
																</nobr></td>
																</tr>
																</table>
															</td><td align="right" valign="top"><nobr><label for="personSearch.afterBirthTime" class="boldTenBlack"> - </label></nobr></td><td valign="middle" align="left" colspan="10">
																<table cellspacing="0" cellpadding="0" border="0">
																<tr>
																<td><input id="personSearch.afterBirthTime" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.afterBirthTime" size="10" maxlength="10" onkeyup="DateMask(this,null,event)" value="" validate="dateRange" errorCode="" dateBeforeRef="personSearch.beforeBirthTime" fieldLabel="DOB End Date" requiredPartner="personSearch.beforeBirthTime" onload="this.value='yes';"><nobr><a href="#"><img align="bottom" alt="Select a Date" border="0" src="calendar.gif" name="N1009A_button" onclick="getCalDate('personSearch.afterBirthTime','N1009A_button');return false;"></a>
																<center>mm/dd/yyyy</center>
																</nobr><SCRIPT Language="JavaScript" Type="text/javascript">flipDateOnload('DEM105O');</SCRIPT></td>
																</tr>
															    </table>
														    </td>
														    </tr>
															
														   </table>
													   </td>
												   </tr>
												   </table>
										    </td>
										    </tr>-->



					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM114L" style="" title="Patient's Current Sex">
								Current Sex: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;"></td>
						<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.currentSex" styleId="DEM114"
								title="The patient's current sex">
								<html:optionsCollection property="codedValue(SEX)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM106L" style="" title="Patient's Street Address">
								Street Address: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							<table role="presentation" style="background: #d1e5f6"
								width="98%" align="center">
								<tr>
									<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td>
									<td style="background: #d1e5f6" width="98%"><html:select
											title="patient's street addres Operator"
											name="personSearchForm"
											property="personSearch.streetAddr1Operator" styleId="DEM106O">

											<option value="CT" selected>Contains&nbsp;&nbsp;&nbsp;</option>
											<option value="=">Equal</option>
											<option value="!=">Not Equal</option>

										</html:select> <SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DEM106O"),"${fn:escapeXml(StrAddOp)}");</SCRIPT>
									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>
							</table>
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.streetAddr1"
								styleId="DEM106" title="Patient's Street Address"
								onkeydown="if (SubmitOnEnter()==false) return false;" size=""
								maxlength="50" /></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM107L" style="" title="Patient's City"> City: </span>&nbsp;
						</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							<table role="presentation" style="background: #d1e5f6"
								width="98%" align="center">
								<tr>
									<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td>
									<td style="background: #d1e5f6" width="98%"><html:select
											title="patient's city Operator" name="personSearchForm"
											property="personSearch.cityDescTxtOperator" styleId="DEM107O">

											<option value="CT" selected>Contains&nbsp;&nbsp;&nbsp;</option>
											<option value="=">Equal</option>
											<option value="!=">Not Equal</option>

										</html:select> <SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DEM107O"),"${fn:escapeXml(CityOp)}");
										</SCRIPT></td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>
							</table>
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.cityDescTxt"
								styleId="DEM107" title="Patient's City"
								onkeydown="if (SubmitOnEnter()==false) return false;" size=""
								maxlength="50" /></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM108L" style="" title="Patient's State"> State: </span>&nbsp;
						</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.state" styleId="DEM108O"
								title="Patient's State">
								<html:optionsCollection property="stateList" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM108L" style="" title="Patient's Zip"> Zip: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.zipCd" styleId="DEM109"
								title="Patient's Zip Code"
								onkeydown="if (SubmitOnEnter()==false) return false;" size=""
								maxlength="50" onkeyup="ZipMask(this,event)" /></td>
					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM119L" style="" title="Patient's ID"> Patient ID(s):</span>&nbsp;
						</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:text size="35" property="personSearch.localID"
								styleId="DEM229"
								title="The patient's local (system) ID. To search multiple patient IDs, separate IDs by commas, semicolons, or spaces." />
							<!-- <input id="personSearch.localID" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.localID" size="" maxlength="50" value="" onload="this.value='yes';"> -->
							<!-- <input id="personSearch.patientIDOperator" type="hidden" name="personSearch.patientIDOperator" value="=">-->
							<span style="white-space: nowrap" class="desc">(Separate
								IDs by commas, semicolons, or spaces)</span></td>


					</tr>

				</nedss:container>
			</nedss:container>

			<nedss:container id="patSearch3" name="Advanced Search"
				defaultDisplay="F" includeBackToTopLink="F" isHidden="F"
				classType="sect">

				<!-- ########### SUB SECTION ###########  -->
				<nedss:container id="patSearch4" name="" displayImg="F"
					displayLink="F" defaultDisplay="F" includeBackToTopLink="F"
					isHidden="F" classType="subSect">




					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM222L" style="" title="Patient's ID Type">ID Type:</span>&nbsp;
						</td>
						<td style="background: #d1e5f6; width =13%; min-width: 84px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.typeCd" styleId="DEM222"
								title="Patient's ID Type">
								<html:optionsCollection property="codedValue(EI_TYPE_PAT)"
									value="key" label="value" />
							</html:select></td>

					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM223L" style="" title="Patient's ID Value">ID
								Number: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.rootExtensionTxt"
								size="" maxlength="50" styleId="DEM223"
								title="Any associated patient ID (e.g., SSN, State HIV Case ID, Medicaid Number, Medicare Number, etc.)" />
							<!--  <input id="DEM223" type="TEXT" onkeydown="if (SubmitOnEnter()==false) return false;" name="personSearch.rootExtensionTxt" size="" maxlength="50" value="" validate="acctIdSearchCheck" errorCode="" nameRef="personSearch.typeCd" fieldLabel="ID Value" onload="this.value='yes';"> -->

						</td>

					</tr>

					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM238L" style="" title="Patient's Telephone">Phone: </span>&nbsp;
						</td>

						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.phoneNbrTxt"
								styleId="DEM238" maxlength="50" onkeyup="TeleMask(this,event)"
								title="Any associated patient phone number (e.g., home, work, cell)" />

							<!--   <input type="hidden" validate="reg-expr" pattern="^((\d{3})?-(\d{3})?-(\d{4})?)?$" errorCode="ERR107" 
				                   fieldLabel="Telephone" name="personSearch.phoneNbrTxt" id="personSearch.phoneNbrTxt" 
				                   onchange="showPhoneString('personSearch.phoneNbrTxt')" 
				                   onkeypress="disableTelephoneTextboxes('personSearch.phoneNbrTxt')"><nobr><input maxlength="3" size="2" 
				                   type="text" id="personSearch.phoneNbrTxt1" onchange="addPhoneString(1, this, 'personSearch.phoneNbrTxt')" 
				                   onkeyup="moveFocusParsedInputs(1, this, 'personSearch.phoneNbrTxt')" value="">-<input maxlength="3" size="2"
				                    type="text" id="personSearch.phoneNbrTxt2" onchange="addPhoneString(2, this, 'personSearch.phoneNbrTxt')" 
				                    onkeyup="moveFocusParsedInputs(2, this, 'personSearch.phoneNbrTxt')" value="">-<input maxlength="4" 
				                    size="3" type="text" id="personSearch.phoneNbrTxt3" onchange="addPhoneString(3, this, 
				                    'personSearch.phoneNbrTxt')" value=""></nobr>  -->

						</td>

					</tr>

					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM182L" style="" title="Patient's Email Address.">
								Email: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:text property="personSearch.emailAddress"
								styleId="DEM182" maxlength="50"
								title="Any associated patient email address" /></td>

					</tr>

					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="DEM221L" style="" title="Patient's Ethnicity">
								Ethnicity: </span>&nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.ethnicGroupInd" styleId="DEM221"
								title="Indicates if the patient is hispanic or not">
								<html:optionsCollection property="codedValue(P_ETHN_GRP)"
									value="key" label="value" />
							</html:select></td>

					</tr>
					<tr>
						<td class="fieldName"><span class="InputFieldLabel"
							id="personSearch.raceCdL" style="" title="Patient's Race">
								Race: </span> &nbsp;</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
						</td>
						<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.raceCd" styleId="DEM176"
								title="Reported race">

								<!-- <option value=""></option><option value="1002-5">American Indian or Alaska Native</option>
				                     <option value="2028-9">Asian</option><option value="2054-5">Black or African American</option>
				                     <option value="2076-8">Native Hawaiian or Other Pacific Islander</option>
				                     <option value="U">Unknown</option><option value="2106-3">White</option> -->

								<html:optionsCollection property="codedValue(RACE_CALCULATED)"
									value="key" label="value" />

							</html:select> <!--  <input size="41" type="text" name="personSearch.raceCd_textbox" onfocus="storeCaret(this);AutocompleteStoreOnFocusValue(this);" onkeydown="CheckTab('personSearch.raceCd',this);" onkeyup="AutocompleteComboBox('personSearch.raceCd_textbox','personSearch.raceCd',true);storeCaret(this);" onblur="AutocompleteSynch('personSearch.raceCd_textbox','personSearch.raceCd');"><img align="top" alt="" border="0" src="type-ahead2.gif" name="personSearch.raceCd_button" onclick="AutocompleteExpandListbox('personSearch.raceCd_textbox','personSearch.raceCd');">	                                                    
			                        <select AUTOCOMPLETE="off" id="personSearch.raceCd" name="personSearch.raceCd" size="5" class="none" onclick="AutocompleteComboBox('personSearch.raceCd_textbox','personSearch.raceCd',true,true);this.className=&quot;none&quot;;" onkeyup="AutocompleteComboBox('personSearch.raceCd_textbox','personSearch.raceCd',true,true);" onblur="this.className='none';" typeahead="true"><option value="">
			                        </option><option value="1002-5">American Indian or Alaska Native</option><option value="2028-9">Asian</option><option value="2054-5">Black or African American</option><option value="2076-8">Native Hawaiian or Other Pacific Islander</option><option value="U">Unknown</option><option value="2106-3">White</option></select>  -->
						</td>

					</tr>
					<logic:notEqual name="PatientEntitySearch" value="true">
					<logic:notEqual name="MergePatient" value="true">
					
						<tr>
							<td class="fieldName">Include records that are: &nbsp;</td>
							<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							</td>
							<td width="1%"></td>
							<td><html:checkbox title="Active records checkbox"
									name="personSearchForm" property="personSearch.active"
									styleId="recordAct" value="true"></html:checkbox> <nobr>Active</nobr>

								&nbsp; <html:checkbox title="Deleted records checkbox"
									name="personSearchForm" property="personSearch.inActive"
									styleId="recordDel" value="true"></html:checkbox> <nobr>Deleted</nobr>

								<html:checkbox title="Superceded records checkbox"
									name="personSearchForm" property="personSearch.superceded"
									styleId="recordSup" value="true"></html:checkbox> <nobr>Superceded</nobr>
							</td>

						</tr>
					</logic:notEqual>
					</logic:notEqual>
				</nedss:container>
			</nedss:container>
			
			<logic:notEqual name="PatientEntitySearch" value="true">
							 <logic:notEqual name="MergePatient" value="true">
			
				<div class="tabNavLinks">
					<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:navigateTab('next')"> Next </a> <input
						name="endOfTab" type="hidden" />
				</div>
			</logic:notEqual>
			</logic:notEqual>
		</div>
	</td>
</tr>
