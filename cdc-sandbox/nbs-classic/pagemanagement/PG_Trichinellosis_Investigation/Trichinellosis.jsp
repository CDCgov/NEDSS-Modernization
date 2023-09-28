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
String tabId = "editTrichinellosis";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Signs and Symptoms","Laboratory","Exposure"};
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
<nedss:container id="NBS_UI_35" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386789004L" title="Did the patient have eosinophilia?">
Eosinophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386789004)" styleId="386789004" title="Did the patient have eosinophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have a fever?">
Fever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have a fever?" onchange="ruleEnDis3866610068560();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the patient's highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the patient's highest measured temperature during this illness?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV202Unit)" styleId="INV202UNIT">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have myalgia or muscle pain?">
Myalgia/Muscle Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have myalgia or muscle pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000L" title="Did the patient have periorbital edema?">
Periorbital Edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49563000)" styleId="49563000" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other Symptom(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388559();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_42" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408554();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_43errorMessages">
<b> <a name="NBS_UI_43errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_43"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_43">
<tr id="patternNBS_UI_43" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_43" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_43');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_43" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_43');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_43" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_43">
<tr id="nopatternNBS_UI_43" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_43 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="INV291L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)">
Test Result Qualitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)" onchange="unhideBatchImg('NBS_UI_43');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Quantitative Test Result Value" styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_43');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="667469L" title="This indicates the type of specimen tested">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="This indicates the type of specimen tested" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('667469');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="This indicates the type of specimen tested" id="667469OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(667469Oth)" size="40" maxlength="40" title="Other This indicates the type of specimen tested" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="667469Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_43');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB217L" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date.">
Specimen Analyzed Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB217)" maxlength="10" size="10" styleId="LAB217" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB217','LAB217Icon'); unhideBatchImg('NBS_UI_43');return false;" styleId="LAB217Icon" onkeypress="showCalendarEnterKey('LAB217','LAB217Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="LAB606L" title="Enter the performing laboratory type">
Performing Laboratory Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB606)" styleId="LAB606" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Laboratory Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="LAB606Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="NBS212L" title="If the specimen was sent for strain identification, indicate the strain">
Strain Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS212)" styleId="NBS212" title="If the specimen was sent for strain identification, indicate the strain" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('NBS212');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_STRAINTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the specimen was sent for strain identification, indicate the strain" id="NBS212OthL">Other Strain Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS212Oth)" size="40" maxlength="40" title="Other If the specimen was sent for strain identification, indicate the strain" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="NBS212Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_43">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_43">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_43"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_43"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_43')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Eosinophilia Lab" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB711L" title="Was testing performed for eosinophilia?">
Was testing performed for eosinophilia?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB711)" styleId="LAB711" title="Was testing performed for eosinophilia?" onchange="ruleEnDisLAB7118555();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="LAB665L" title="Eosinophil value (absolute number or percentage)">
Eosinophil value (absolute number or percentage):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB665)" size="10" maxlength="10"  title="Eosinophil value (absolute number or percentage)" styleId="LAB665" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(LAB665Unit)" styleId="LAB665UNIT">
<nedss:optionsCollection property="codedValue(PHVS_EOSINOPHILUNITS_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Food Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_45L">&nbsp;&nbsp;In the 8 WEEKS before symptom onset/diagnosis:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS406L" title="Did the patient consume meat suspected of making the patient ill?">
Did the patient consume meat item(s) suspected of making the patient ill?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS406)" styleId="NBS406" title="Did the patient consume meat suspected of making the patient ill?" onchange="ruleEnDisNBS4068553();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_38" name="Food Exposure History" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_38errorMessages">
<b> <a name="NBS_UI_38errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_38"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_38">
<tr id="patternNBS_UI_38" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_38" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_38');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_38" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_38');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_38" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_38">
<tr id="nopatternNBS_UI_38" class="odd" style="display:">
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_46L">&nbsp;&nbsp;Specify all the information for suspected meat consumed:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV1009L" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill">
Meat Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV1009)" styleId="INV1009" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV1009');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_MEATCONSUMEDTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009OthL">Other Meat Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV1009Oth)" size="40" maxlength="40" title="Other Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV1009Oth"/></td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Other Meat Type" id="NBS528L">
(Legacy) Other Meat Type:</span>
</td>
<td>
<span id="NBS528S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS528)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS528)" styleId="NBS528" />
</td>
</tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV967L" title="Date suspected meat was eaten">
Consumed Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV967)" maxlength="10" size="10" styleId="INV967" onkeyup="unhideBatchImg('NBS_UI_38');DateMask(this,null,event)" styleClass="NBS_UI_38" title="Date suspected meat was eaten"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV967','INV967Icon'); unhideBatchImg('NBS_UI_38');return false;" styleId="INV967Icon" onkeypress="showCalendarEnterKey('INV967','INV967Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV969L" title="Where was the suspected meat obtained?">
Meat Obtained:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV969)" styleId="INV969" title="Where was the suspected meat obtained?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV969');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_MEATPURCHASEINFO_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat obtained?" id="INV969OthL">Other Meat Obtained:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV969Oth)" size="40" maxlength="40" title="Other Where was the suspected meat obtained?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV969Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV968L" title="Meat preparation (further food processing)">
Method of Meat Preparation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV968)" styleId="INV968" title="Meat preparation (further food processing)" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV968');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_FOODPROCESSINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Meat preparation (further food processing)" id="INV968OthL">Other Method of Meat Preparation:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV968Oth)" size="40" maxlength="40" title="Other Meat preparation (further food processing)" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV968Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV970L" title="What method of cooking was used on suspected meat?">
Method of Cooking:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV970)" styleId="INV970" title="What method of cooking was used on suspected meat?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV970');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_FOODCOOKINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What method of cooking was used on suspected meat?" id="INV970OthL">Other Method of Cooking:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV970Oth)" size="40" maxlength="40" title="Other What method of cooking was used on suspected meat?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV970Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV971L" title="Was larva observed in suspected meat by microscopy?">
Larva Present in Meat:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV971)" styleId="INV971" title="Was larva observed in suspected meat by microscopy?" onchange="unhideBatchImg('NBS_UI_38');ruleEnDisINV9718561();enableOrDisableOther('INV972');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PRESENTABSENTUNKNOTEXAMINED_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV972L" title="Where was the suspected meat tested?">
Where was the Meat Tested:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV972)" styleId="INV972" title="Where was the suspected meat tested?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV972');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat tested?" id="INV972OthL">Other Where was the Meat Tested:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV972Oth)" size="40" maxlength="40" title="Other Where was the suspected meat tested?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV972Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV973L" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)">
Suspect Meat Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV973)" styleId ="INV973" onkeyup="checkTextAreaLength(this, 199)" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_38">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_38">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_38"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_38"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_38')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL02L" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?">
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL02)" styleId="TRAVEL02" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?" onchange="ruleEnDisTRAVEL028558();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_40errorMessages">
<b> <a name="NBS_UI_40errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_40"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_40">
<tr id="patternNBS_UI_40" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_40" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_40');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_40" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_40');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_40" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_40">
<tr id="nopatternNBS_UI_40" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_40 InputFieldLabel" id="NBS454L" title="Domestic vs International Travel">
Was the travel domestic or international?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS454)" styleId="NBS454" title="Domestic vs International Travel" onchange="unhideBatchImg('NBS_UI_40');ruleEnDisNBS4548557();ruleEnDisNBS4548556();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_DOMINTNL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL05)" styleId="TRAVEL05" title="List any international destinations of recent travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82754_3L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(82754_3)" styleId="82754_3" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82753_5L" title="Intrastate destination, counties traveled">
County of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82753_5)" styleId="82753_5" title="Intrastate destination, counties traveled" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="NBS453L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS453)" styleId="NBS453" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL16L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL16)" styleId="TRAVEL16" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_40');enableOrDisableOther('TRAVEL16');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel" id="TRAVEL16OthL">Other Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel" onkeyup="unhideBatchImg('NBS_UI_40')" styleId="TRAVEL16Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06L" title="If the patient traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40" title="If the patient traveled, when did they arrive to their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL06','TRAVEL06Icon'); unhideBatchImg('NBS_UI_40');return false;" styleId="TRAVEL06Icon" onkeypress="showCalendarEnterKey('TRAVEL06','TRAVEL06Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07L" title="If the patient traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40" title="If the patient traveled, when did they depart from their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL07','TRAVEL07Icon'); unhideBatchImg('NBS_UI_40');return false;" styleId="TRAVEL07Icon" onkeypress="showCalendarEnterKey('TRAVEL07','TRAVEL07Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4L" title="Duration of Stay">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82310_4)" size="3" maxlength="3"  title="Duration of Stay" styleId="82310_4" onkeyup="unhideBatchImg('NBS_UI_40');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_40"/>
<html:select name="PageForm" property="pageClientVO.answer(82310_4Unit)" styleId="82310_4UNIT" onchange="unhideBatchImg('NBS_UI_40')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(TRAVEL23)" styleId ="TRAVEL23" onkeyup="checkTextAreaLength(this, 2000)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_40">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_40">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_40"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_40"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_40')"/>&nbsp;	&nbsp;&nbsp;
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
