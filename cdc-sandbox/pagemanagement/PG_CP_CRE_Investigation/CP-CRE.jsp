<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
<!--##Investigation Business Object##-->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<!-- ################### A PAGE TAB ###################### - - -->
<%
Map map = new HashMap();
if(request.getAttribute("SubSecStructureMap") != null){
map =(Map)request.getAttribute("SubSecStructureMap");
}
%>
<%
String tabId = "editCP-CRE";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Travel","Laboratory","Previous History"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<table style="width:100%;" class="sectionsToggler" role="presentation">
<tr><td><ul class="horizontalList">
<li style="margin-right:5px;"><b>Go to: </b></li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
</ul> </td> </tr>
<tr>
<td style="padding-top:1em;">
<a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
</td>
</tr>
</table>
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_34" name="Travel and Healthcare" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_AL136100L">&nbsp;&nbsp;In the YEAR prior to specimen collection:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL38L" title="Did the patient travel internationally in the past 1 year from the date of specimen collection?">
Did the patient travel internationally?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL38)" styleId="TRAVEL38" title="Did the patient travel internationally in the past 1 year from the date of specimen collection?" onchange="ruleEnDisTRAVEL388802();ruleEnDis90366_68803();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
International Destination(s):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(TRAVEL05)" styleId="TRAVEL05" title="List any international destinations of recent travel"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'TRAVEL05-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="TRAVEL05-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="90366_6L" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection.">
Received Healthcare Outside the USA:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(90366_6)" styleId="90366_6" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection." onchange="ruleEnDis90366_68803();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91514_0L" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection.">
Countries in which healthcare was received:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(91514_0)" styleId="91514_0" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '91514_0-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="91514_0-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36" name="Laboratory Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408804();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Laboratory Testing Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_37errorMessages">
<b> <a name="NBS_UI_37errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_37"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666" width="9%" colspan="3"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_37">
<tr id="patternNBS_UI_37" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_37" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_37');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_37" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_37');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_37" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_37">
<tr id="nopatternNBS_UI_37" class="odd" style="display:">
<td colspan="<%=batchrec.length+1%>"> <span>&nbsp; No Data has been entered.
</span>
</td>
</tr>
</tbody>
</table>
</td>
<td width="5%"> &nbsp; </td>
</tr>
</Table>
</td>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS674L" title="Unique isolate or specimen identifier; ID assigned by the local laboratory. Isolate ID is preferred, but if unknown specimen ID is acceptable.">
Performing Lab Specimen or Isolate ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS674)" size="25" maxlength="25" title="Unique isolate or specimen identifier; ID assigned by the local laboratory. Isolate ID is preferred, but if unknown specimen ID is acceptable." styleId="NBS674" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_1141L" title="Lab isolate identifier from public health lab for mechanism testing">
State Lab Isolate ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_1141)" size="25" maxlength="25" title="Lab isolate identifier from public health lab for mechanism testing" styleId="FDD_Q_1141" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV949L" title="NCBI SRA Accession Number (SRX#), the accession number generated by NCBI’s Sequence Read Archive when sequence data are uploaded to NCBI. This provides both the sequence data and metadata on how the sample was sequenced.">
NCBI SRA Accession Number (SRX #):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV949)" size="25" maxlength="25" title="NCBI SRA Accession Number (SRX#), the accession number generated by NCBI’s Sequence Read Archive when sequence data are uploaded to NCBI. This provides both the sequence data and metadata on how the sample was sequenced." styleId="INV949" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_37');DateMask(this,null,event)" styleClass="NBS_UI_37" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_37');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="667469L" title="This indicates the type of specimen tested.">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="This indicates the type of specimen tested." onchange="unhideBatchImg('NBS_UI_37');">
<nedss:optionsCollection property="codedValue(SPECIMEN_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST_TYPE_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case." onkeyup="unhideBatchImg('NBS_UI_37')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="INV291L" title="Result of diagnostic test">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Result of diagnostic test" onchange="unhideBatchImg('NBS_UI_37');">
<nedss:optionsCollection property="codedValue(TEST_RESULT_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_37 InputFieldLabel" id="LAB278L" title="Species identified through testing">
Organism Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB278)" styleId="LAB278" title="Species identified through testing" onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('LAB278');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(ORGANISM_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB278Oth)" size="40" maxlength="40" title="Other Species identified through testing" onkeyup="unhideBatchImg('NBS_UI_37')" styleId="LAB278Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="85069_3L" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case.">
Test Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85069_3)" styleId="85069_3" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('85069_3');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST_METHOD_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." id="85069_3OthL">Other Test Method:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(85069_3Oth)" size="40" maxlength="40" title="Other Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." onkeyup="unhideBatchImg('NBS_UI_37')" styleId="85069_3Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="48018_6L" title="Gene identifier">
Gene Identifier:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(48018_6)" styleId="48018_6" title="Gene identifier" onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('48018_6');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(GENE_NAME_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Gene identifier" id="48018_6OthL">Other Gene Identifier:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(48018_6Oth)" size="40" maxlength="40" title="Other Gene identifier" onkeyup="unhideBatchImg('NBS_UI_37')" styleId="48018_6Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_37">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_37">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_37"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_37"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_37')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_43L">&nbsp;&nbsp;If multiple specimens were collected, County/State should be for the first positive specimen associated with this incident of disease.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="89202_6L" title="County of facility where specimen was collected">
County of facility where specimen was collected:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(89202_6)" styleId="89202_6" title="County of facility where specimen was collected">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68488_6L" title="State of facility where specimen was collected">
State of facility where specimen was collected:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(68488_6)" styleId="68488_6" title="State of facility where specimen was collected">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Previously Counted Case" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV1109L" title="Clinical CP-CRE Only: Was patient previously counted as a colonization/screening case?">
Previously counted as a colonization/screening case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV1109)" styleId="INV1109" title="Clinical CP-CRE Only: Was patient previously counted as a colonization/screening case?" onchange="ruleEnDisINV11098805();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Case IDs" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_40L">&nbsp;&nbsp;Please provide the related case ID(s) if patient was previously counted as a colonization/screening case.</span> </td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_1L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_1)" size="30" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_1"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_2L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_2)" size="30" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_3L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_3)" size="30" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_3"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_4L" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)">
Previously Reported State Case Number 4:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_4)" size="30" maxlength="30" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)" styleId="INV1110_4"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_5L" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s).">
Previously Reported State Case Number 5:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_5)" size="30" maxlength="30" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)." styleId="INV1110_5"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
