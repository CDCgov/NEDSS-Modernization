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
String tabId = "editLyme";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Manifestations","Treatment","Laboratory"};
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
<nedss:container id="NBS_UI_42" name="Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271807003L" title="Did the patient have Erythema chronica migrans?">
Erythema Migrans:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271807003)" styleId="271807003" title="Did the patient have Erythema chronica migrans?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233917008L" title="Did the patient have Atrioventricular block?">
Atrioventricular block:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233917008)" styleId="233917008" title="Did the patient have Atrioventricular block?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3723001L" title="Did the patient have Lyme arthritis?">
Arthritis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(3723001)" styleId="3723001" title="Did the patient have Lyme arthritis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="193093009L" title="Did the patient have Bells palsy?">
Bells palsy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(193093009)" styleId="193093009" title="Did the patient have Bells palsy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="48780006L" title="Did the patient have Other Cranial neuritis?">
Other Cranial neuritis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(48780006)" styleId="48780006" title="Did the patient have Other Cranial neuritis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="72274001L" title="Did the patient have Radiculoneuropathy (Nerve root disorder)?">
Radiculoneuropathy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(72274001)" styleId="72274001" title="Did the patient have Radiculoneuropathy (Nerve root disorder)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003L" title="Did the patient have Lymphocytic Meningitis?">
Lymphocytic Meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44201003)" styleId="44201003" title="Did the patient have Lymphocytic Meningitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008L" title="Did the Patient have Encephalitis?">
Encephalitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(31646008)" styleId="31646008" title="Did the Patient have Encephalitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62950007L" title="Did the patient have Encephalomyelitis?">
Encephalomyelitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(62950007)" styleId="62950007" title="Did the patient have Encephalomyelitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388657();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTHL" title="Specify other signs and symptoms.">
Specify Other Symptom(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS338_OTH)" styleId ="NBS338_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify other signs and symptoms."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="255633001L" title="Did the patient receive antibiotics treatment for this infection?">
Did the patient receive antibiotic treatment for this infection?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(255633001)" styleId="255633001" title="Did the patient receive antibiotics treatment for this infection?" onchange="ruleEnDis2556330018658();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_45" name="Antibiotics Treatment Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_45errorMessages">
<b> <a name="NBS_UI_45errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_45"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_45">
<tr id="patternNBS_UI_45" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_45" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_45');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_45" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_45');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_45" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_45','patternNBS_UI_45','questionbodyNBS_UI_45');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_45">
<tr id="nopatternNBS_UI_45" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_45 InputFieldLabel" id="29303_5L" title="What antibiotic was prescribed to the patient for this infection?">
Medication Administered:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(29303_5)" styleId="29303_5" title="What antibiotic was prescribed to the patient for this infection?" onchange="unhideBatchImg('NBS_UI_45');enableOrDisableOther('29303_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(MEDICATION_RECEIVED_LYME)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What antibiotic was prescribed to the patient for this infection?" id="29303_5OthL">Other Medication Administered:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(29303_5Oth)" size="40" maxlength="40" title="Other What antibiotic was prescribed to the patient for this infection?" onkeyup="unhideBatchImg('NBS_UI_45')" styleId="29303_5Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV924L" title="Date the treatment was prescribed">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV924)" maxlength="10" size="10" styleId="INV924" onkeyup="unhideBatchImg('NBS_UI_45');DateMask(this,null,event)" styleClass="NBS_UI_45" title="Date the treatment was prescribed"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV924','INV924Icon'); unhideBatchImg('NBS_UI_45');return false;" styleId="INV924Icon" onkeypress="showCalendarEnterKey('INV924','INV924Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="67453_1L" title="Prescribed duration (in days) of antibiotic treatment">
Treatment Duration (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(67453_1)" size="3" maxlength="3"  title="Prescribed duration (in days) of antibiotic treatment" styleId="67453_1" onkeyup="unhideBatchImg('NBS_UI_45');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_45">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45','patternNBS_UI_45','questionbodyNBS_UI_45')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_45">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45','patternNBS_UI_45','questionbodyNBS_UI_45');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_45"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45','patternNBS_UI_45','questionbodyNBS_UI_45');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_45"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_45')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408659();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_48" name="Interpretative Lab Data Repeating  Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_48errorMessages">
<b> <a name="NBS_UI_48errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_48"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_48">
<tr id="patternNBS_UI_48" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_48" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_48');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_48" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_48');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_48" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_48','patternNBS_UI_48','questionbodyNBS_UI_48');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_48">
<tr id="nopatternNBS_UI_48" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_48 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_UI_48');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST_TYPE_LYME)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case." onkeyup="unhideBatchImg('NBS_UI_48')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_48 InputFieldLabel" id="INV291L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)" onchange="unhideBatchImg('NBS_UI_48');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST_INTERPRETATION_LYME)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)" id="INV291OthL">Other Test Result:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV291Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)" onkeyup="unhideBatchImg('NBS_UI_48')" styleId="INV291Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Quantitative Test Result Value" styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_48');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_48 InputFieldLabel" id="LAB115L" title="Units of measure for the Quantitative Test Result Value">
Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB115)" styleId="LAB115" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_48');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_48 InputFieldLabel" id="LAB278L" title="Species identified through testing">
Organism Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB278)" styleId="LAB278" title="Species identified through testing" onchange="unhideBatchImg('NBS_UI_48');enableOrDisableOther('LAB278');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(ORGANISM_LYME)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB278Oth)" size="40" maxlength="40" title="Other Species identified through testing" onkeyup="unhideBatchImg('NBS_UI_48')" styleId="LAB278Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_48 InputFieldLabel" id="667469L" title="If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc.">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." onchange="unhideBatchImg('NBS_UI_48');enableOrDisableOther('667469');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMEN_TYPE_LYME)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." id="667469OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(667469Oth)" size="40" maxlength="40" title="Other If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." onkeyup="unhideBatchImg('NBS_UI_48')" styleId="667469Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_48');DateMask(this,null,event)" styleClass="NBS_UI_48" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_48');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_48">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_48BatchAddFunction()) writeQuestion('NBS_UI_48','patternNBS_UI_48','questionbodyNBS_UI_48')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_48">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_48BatchAddFunction()) writeQuestion('NBS_UI_48','patternNBS_UI_48','questionbodyNBS_UI_48');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_48"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_48BatchAddFunction()) writeQuestion('NBS_UI_48','patternNBS_UI_48','questionbodyNBS_UI_48');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_48"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_48')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
