<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants" %>
<%@ page isELIgnored ="false" %>
								
<tr><td>
	<fieldset style="border-width:0px;" id="condition">
      <nedss:container id="section4" name="Advanced Algorithm Criteria" classType="sect" includeBackToTopLink="no">
            <nedss:container id="IdAdvancedSubSection" classType="subSect" name="Advanced Criteria" >
            	<tr> 
            	<td colspan="2" width="100%">
								 <div class="infoBox errors" style="display: none;visibility: none;" id="IdAdvancedSubSectionerrorMessages">
								 	<b> <a name="IdAdvancedSubSectionerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
								 </div>
								 <table  class="dtTable" align="center" >
									 <thead>
										 <td width="10%" colspan="3" style="background-color: #EFEFEF;border:1px solid #666666"> &nbsp;</td>
										 <th width="25%"><font color="black">Question</font></th>
										 <th width="20%"><font color="black">Logic</font></th>
										 <th width="45%"><font color="black">Value</font></th>
									 </thead>
									 
									 <tbody id="questionbodyIdAdvancedSubSection">
										 <tr id="patternIdAdvancedSubSection" class="odd" style="display:none">
											 <td style="width:3%;text-align:center;">
											 	<input id="viewIdAdvancedSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewAdvCriteriaClicked(this.id,'IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td>
											 <td style="width:3%;text-align:center;" >
											 	<input id="editIdAdvancedSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editAdvCriteriaClicked(this.id,'IdAdvancedSubSection');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td>
											 <td style="width:3%;text-align:center;">
											 	<input id="deleteIdAdvancedSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteAdvCriteriaClicked(this.id,'IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">
											 </td>
											 <td width="20%" align="left">
											 	<span id="tableAdvQueListTxt"> </span>
											 </td>
											 
											 <td width="20%" align="left">
											 	<span id="tableAdvLogicTxt"> </span>
											 </td>
											 
											<td width="50%" align="left">
												<span id="tableAdvValValueTxt"> </span>
											</td>
										</tr>
									</tbody>
									<tbody id="questionbodyIdAdvancedSubSection">
									 <tr id="nopatternIdAdvancedSubSection" class="odd" style="display:block">
										 <td colspan="21"> 
											 <span>&nbsp; No Data has been entered.
											 </span>
										 </td>
									 </tr>
									</tbody>
								 </table>
							 </td>
							 <td width="5%"> &nbsp; </td>
						 </tr>
			 	<tr>
				 	<td class="fieldName">
					 	<font class="boldTenRed" > * </font><span class=" InputFieldLabel" id="questionAdvL" title="Question">
					 	Question:</span>
				 	</td>
				 	<td>
					 	<html:select name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_QUESTION+")"%>' styleId="questionAdvList" onchange="clearAdvSelection(this);getDWRLogic(this);changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch(this,'','');getDWRAdvValue(this);"  onblur="selectAdvacedCriteriaBlur();">
					 	<html:optionsCollection name="decisionSupportForm" property="advDwrQuestions" value="key" label="value" /></html:select>
				 	</td>
			 	</tr>
			 	<tr>
			 		<td class="fieldName">
					 	<font class="boldTenRed" > * </font><span class=" InputFieldLabel" id="advLogicL" title="Logic">
					 	Logic:</span>
				 	</td>
				 	<td>
					 	<html:select name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_LOGIC+")"%>' styleId="advLogicList" onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('',this,'');">
					 	<html:optionsCollection name="decisionSupportForm" property="dwrAdvanceValues" value="key" label="value" /></html:select>
                    </td>
			 	</tr> 	
			 	
		        <tr>
		         	<td class="fieldName"> 
			         	<font class="boldTenRed" > * </font><span class="InputFieldLabel" id="advValueL" title="Value"> 
			                Value: 
			            </span>
			        </td> 
		           	<td>
				 		<div id="advDate">  
						 	<html:text name="decisionSupportForm" title="Enter a Date" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' maxlength="10" size="10" styleId="advVal_date" onkeyup="DateMask(this,null,event)" onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);"/>
							<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('advVal_date','advVal_dateIcon'); return false;" onkeypress ="showCalendarEnterKey('advVal_date','advVal_dateIcon',event);" styleId="advVal_dateIcon"></html:img>
						</div>
						<div id="advText"> 
						 	<html:text name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' styleId="advVal_text"
			               	 size="30" maxlength="100" onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);" />
		                </div>
						 	<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" onkeyup="chkMaxLength(this,2000)" styleId="advVal_textArea"
		                       onkeydown="chkMaxLength(this,2000)"   name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);"/>
	                    <div id="advS_sel">   
						 	<html:select name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' styleId="advValueList1" onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);" >
						 		<html:optionsCollection name="decisionSupportForm" property="dwrValue" value="key" label="value" />
						 	</html:select>
					 	</div>
						 	
	                   	<div class="multiSelectBlock" id="advM_sel">
	                        <i> (Use Ctrl to select more than one) </i> <br/>
	                        <html:select property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' 
	                                styleId="advValueList2" 
	                                onchange="displaySelectedOptions(this, 'advValueList2-selectedValues'); changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);"
	                                multiple="true" size="4" >
	                            <html:optionsCollection property="dwrValue" value="key" label="value"/>
	                        </html:select> 
	                        <br/>
	                        <div id="advValueList2-selectedValues" style="margin:0.25em;">
	                           <b> Selected Values: </b>
	                        </div>
	                   	</div>
	                   	
	                   	<div id="advNum_Coded"> 
						 	<html:text name="decisionSupportForm" property="decisionSupportClientVO.answer(VALUE1)" styleId="advVal_num"
			               	 size="30" maxlength="100" onchange="changeDarkBGColor('IdSubSection');changeLightBGColorOfAdvancedBatch('','',this);" />
			               	 <html:select name="decisionSupportForm" property="decisionSupportClientVO.answer(VALUE2)" styleId="advVal_code" onchange="changeDarkBGColor('IdAdvancedSubSection');changeLightBGColorOfAdvancedBatch('','',this);" >
						 		<html:optionsCollection name="decisionSupportForm" property="dwrValue" value="key" label="value" />
						 	</html:select>
		                </div>
		                <div id="advNum_Literal"> 
						 	<html:text name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.CRITERIA_VALUE+")"%>' styleId="advVal_lit"
			               	 size="30" maxlength="100" onchange="changeDarkBGColor('IdSubSection');changeLightBGColorOfAdvancedBatch('','',this);" />
		                </div>
                    </td>
		        </tr>
		        <logic:equal name="decisionSupportForm" property="actionMode" value="Create">
					<tr id="AddButtonToggleIdAdvancedSubSection">
						<td colspan="2" align="right">
							<input type="button" value="     Add    "  onclick="if(ValidateAdvancedCriteriaBatchFields())writeAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');"/>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="decisionSupportForm" property="actionMode" value="Edit">
					<tr id="AddButtonToggleIdAdvancedSubSection">
						<td colspan="2" align="right">
							<input type="button" value="     Add    "  onclick="if(ValidateAdvancedCriteriaBatchFields())writeAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');"/>&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</logic:equal>
				<tr id="UpdateButtonToggleIdAdvancedSubSection"	style="display:none">
					<td colspan="2" align="right">
					<input type="button" value="   Update   "    onclick="if(ValidateAdvancedCriteriaBatchFields())updateAdvCriteriaBatchIdEntry('IdAdvancedSubSection','patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection')"/>&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<logic:equal name="decisionSupportForm" property="actionMode" value="Create">
				<tr id="AddNewButtonToggleIdAdvancedSubSection"	style="display:none">
					<td colspan="2" align="right">
					<input type="button" value="   Add New   "    onclick="addNewAdvancedBatchIdEntryFields('IdAdvancedSubSection')"/>&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
	            </tr> 
	            </logic:equal>   
            </nedss:container>
      </nedss:container>
    </fieldset>
    <div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
</td></tr>