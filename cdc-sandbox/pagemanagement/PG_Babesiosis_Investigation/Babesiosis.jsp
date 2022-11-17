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
String tabId = "editBabesiosis";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Manifestations","Complications","Medical History","Treatment","Blood Transfusion and Donation","Epidemiology","Laboratory"};
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
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
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
<nedss:container id="NBS_UI_37" name="Clinical Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576L" title="Did the patient experience any clinical manifestation related to babesiosis?">
Was the patient symptomatic?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV576)" styleId="INV576" title="Did the patient experience any clinical manifestation related to babesiosis?" onchange="ruleEnDisINV5768313();ruleHideUnh3866610068316();ruleHideUnhNBS3388327();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_66L">&nbsp;&nbsp;Objective Clinical Manifestations:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have a fever?">
Fever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have a fever?" onchange="ruleHideUnh3866610068316();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the patient's highest measured temperature?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the patient's highest measured temperature?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV202Unit)" styleId="INV202UNIT">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271737000L" title="Did the patient have anemia?">
Anemia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271737000)" styleId="271737000" title="Did the patient have anemia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302215000L" title="Did the patient have thrombocytopenic disorder?">
Thrombocytopenia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(302215000)" styleId="302215000" title="Did the patient have thrombocytopenic disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_67L"><hr/></span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_68L">&nbsp;&nbsp;Subjective Clinical Manifestations:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="43724002L" title="Did the patient have chills?">
Chills:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(43724002)" styleId="43724002" title="Did the patient have chills?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="25064002L" title="Did the patient have headache?">
Headache:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(25064002)" styleId="25064002" title="Did the patient have headache?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="57676002L" title="Did the patient have a arthralgia or joint pain?">
Arthralgia/Joint Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(57676002)" styleId="57676002" title="Did the patient have a arthralgia or joint pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
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
<span class=" InputFieldLabel" id="415690000L" title="Did the patient experience sweating?">
Sweating:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(415690000)" styleId="415690000" title="Did the patient experience sweating?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_69L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified?">
Other Symptom(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified?" onchange="ruleHideUnhNBS3388327();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTHL" title="Specify Other signs and symptoms">
Specify Other Symptom(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS338_OTH)" styleId ="NBS338_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify Other signs and symptoms"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Complications" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67187_5L" title="Complications of Babesiosis">
Type of Complications:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(67187_5)" styleId="67187_5" title="Complications of Babesiosis"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '67187_5-selectedValues');enableOrDisableOther('67187_5')" >
<nedss:optionsCollection property="codedValue(PHVS_COMPLICATIONS_BABESIOSIS)" value="key" label="value" /> </html:select>
<div id="67187_5-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Complications of Babesiosis" id="67187_5OthL">Other Type of Complications:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(67187_5Oth)" size="40" maxlength="40" title="Other Complications of Babesiosis" styleId="67187_5Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35" name="Previous Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="300564004L" title="Patient being without a spleen or without a functional spleen">
Is the patient asplenic?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(300564004)" styleId="300564004" title="Patient being without a spleen or without a functional spleen" onchange="ruleEnDis3005640048311();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="82760_0L" title="Splenectomy Date">
Splenectomy Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82760_0)" maxlength="10" size="10" styleId="82760_0" onkeyup="DateMask(this,null,event)" title="Splenectomy Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('82760_0','82760_0Icon'); return false;" styleId="82760_0Icon" onkeypress="showCalendarEnterKey('82760_0','82760_0Icon',event)"></html:img>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82759_2L" title="Why was the patient's spleen removed?">
Why was the patient's spleen removed?:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(82759_2)" styleId ="82759_2" onkeyup="checkTextAreaLength(this, 199)" title="Why was the patient's spleen removed?"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="161413004L" title="Did the patient have a history of babesiosis?">
Did the patient have a history of babesiosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(161413004)" styleId="161413004" title="Did the patient have a history of babesiosis?" onchange="ruleEnDis1614130048312();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_70" name="Date of Previous Illness" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_70errorMessages">
<b> <a name="NBS_UI_70errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_70"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_70">
<tr id="patternNBS_UI_70" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_70" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_70');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_70" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_70');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_70" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_70">
<tr id="nopatternNBS_UI_70" class="odd" style="display:">
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="82758_4L" title="Date of previous babesiosis diagnosis">
Date of Previous Babesiosis Diagnosis:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82758_4)" maxlength="10" size="10" styleId="82758_4" onkeyup="unhideBatchImg('NBS_UI_70');DateMask(this,null,event)" styleClass="NBS_UI_70" title="Date of previous babesiosis diagnosis"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('82758_4','82758_4Icon'); unhideBatchImg('NBS_UI_70');return false;" styleId="82758_4Icon" onkeypress="showCalendarEnterKey('82758_4','82758_4Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_70">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_70">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_70"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_70"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_70')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Patient Immunocompromised" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAR126L" title="At the time of diagnosis, was the patient immunocompromised?">
Immunocompromised at time of diagnosis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAR126)" styleId="VAR126" title="At the time of diagnosis, was the patient immunocompromised?" onchange="ruleEnDisVAR1268314();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_72L">&nbsp;&nbsp;If there are multiple Answers for the below question please separate the answers using a semicolon (;) (e.g., ABC;XYZ)</span> </td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR127L" title="If the patient was immunocompromised, what was the associated condition or treatment?">
Condition and/or Treatment:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(VAR127)" styleId ="VAR127" onkeyup="checkTextAreaLength(this, 199)" title="If the patient was immunocompromised, what was the associated condition or treatment?"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_65" name="Organ Transplant" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB008L" title="In the month before symptom onset or diagnosis (use earlier date), did the patient receive an organ transplant">
In the month before symptoms onset or diagnosis, did the patient receive an organ transplant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB008)" styleId="ARB008" title="In the month before symptom onset or diagnosis (use earlier date), did the patient receive an organ transplant">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_45" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="255633001L" title="Did the patient receive antimicrobial treatment for this infection?">
Did the patient receive antimicrobial treatment for this infection?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(255633001)" styleId="255633001" title="Did the patient receive antimicrobial treatment for this infection?" onchange="ruleEnDis2556330018317();enableOrDisableOther('29303_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29303_5L" title="Treatment drugs used for babesiosis">
Medications Received:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(29303_5)" styleId="29303_5" title="Treatment drugs used for babesiosis"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '29303_5-selectedValues');enableOrDisableOther('29303_5')" >
<nedss:optionsCollection property="codedValue(PHVS_MEDICATIONTREATMENT_BABESIOSIS)" value="key" label="value" /> </html:select>
<div id="29303_5-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Treatment drugs used for babesiosis" id="29303_5OthL">Other Medications Received:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(29303_5Oth)" size="40" maxlength="40" title="Other Treatment drugs used for babesiosis" styleId="29303_5Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_48" name="Blood Transfusion" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB006L" title="In the year before symptom onset or diagnosis (use earlier date), did the patient receive a blood transfusion?">
In the year before symptom onset or diagnosis, did the patient receive a blood transfusion?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB006)" styleId="ARB006" title="In the year before symptom onset or diagnosis (use earlier date), did the patient receive a blood transfusion?" onchange="ruleEnDisARB0068329();ruleEnDisARB0068325();ruleEnDis4189120058318();ruleEnDis4189120058318();enableOrDisableOther('933_2');enableOrDisableOther('933_2');ruleEnDis4189120058318();enableOrDisableOther('933_2');enableOrDisableOther('933_2');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="418912005L" title="Was the patient's infection transfusion associated?">
Was the patient's infection transfusion associated?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(418912005)" styleId="418912005" title="Was the patient's infection transfusion associated?" onchange="ruleEnDis4189120058318();enableOrDisableOther('933_2');enableOrDisableOther('933_2');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="933_2L" title="If a transfused blood product was implicated in an investigation, specify which types of product">
Type of transfused blood product implicated:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(933_2)" styleId="933_2" title="If a transfused blood product was implicated in an investigation, specify which types of product"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '933_2-selectedValues');enableOrDisableOther('933_2')" >
<nedss:optionsCollection property="codedValue(PHVS_BLOODPRODUCT_CDC)" value="key" label="value" /> </html:select>
<div id="933_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If a transfused blood product was implicated in an investigation, specify which types of product" id="933_2OthL">Other Type of transfused blood product implicated:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(933_2Oth)" size="40" maxlength="40" title="Other If a transfused blood product was implicated in an investigation, specify which types of product" styleId="933_2Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_63" name="Blood Transfusion Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_63errorMessages">
<b> <a name="NBS_UI_63errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_63"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_63">
<tr id="patternNBS_UI_63" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_63" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_63');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_63" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_63');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_63" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_63','patternNBS_UI_63','questionbodyNBS_UI_63');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_63">
<tr id="nopatternNBS_UI_63" class="odd" style="display:">
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
<tr><td colspan="2" align="left"><span id="NBS_UI_74L">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the date(s) of blood transfusion(s) the patient received:</span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="14687_8L" title="Date(s) of blood transfusion(s)">
Blood Transfusion Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(14687_8)" maxlength="10" size="10" styleId="14687_8" onkeyup="unhideBatchImg('NBS_UI_63');DateMask(this,null,event)" styleClass="NBS_UI_63" title="Date(s) of blood transfusion(s)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('14687_8','14687_8Icon'); unhideBatchImg('NBS_UI_63');return false;" styleId="14687_8Icon" onkeypress="showCalendarEnterKey('14687_8','14687_8Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_63">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_63BatchAddFunction()) writeQuestion('NBS_UI_63','patternNBS_UI_63','questionbodyNBS_UI_63')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_63">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_63BatchAddFunction()) writeQuestion('NBS_UI_63','patternNBS_UI_63','questionbodyNBS_UI_63');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_63"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_63BatchAddFunction()) writeQuestion('NBS_UI_63','patternNBS_UI_63','questionbodyNBS_UI_63');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_63"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_63')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_52" name="Blood Donation" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB005L" title="In the year before symptom onset or diagnosis (use earlier date), did the patient donate blood?">
In the year before symptom onset or diagnosis, did the patient donate blood?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB005)" styleId="ARB005" title="In the year before symptom onset or diagnosis (use earlier date), did the patient donate blood?" onchange="ruleEnDisARB0058328();ruleEnDisARB0058326();ruleEnDisARB0138319();ruleEnDisARB0138319();enableOrDisableOther('INV964');enableOrDisableOther('INV964');ruleEnDisARB0138319();enableOrDisableOther('INV964');enableOrDisableOther('INV964');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB013L" title="Was the patient a blood donor identified during a transfusion investigation (i.e., had positive Babesia test results and was linked to an infected recipient)?">
Was the patient a blood donor identified during a transfusion investigation?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB013)" styleId="ARB013" title="Was the patient a blood donor identified during a transfusion investigation (i.e., had positive Babesia test results and was linked to an infected recipient)?" onchange="ruleEnDisARB0138319();enableOrDisableOther('INV964');enableOrDisableOther('INV964');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV964L" title="If a donated blood product was implicated in an investigation, specify which types of product">
Type of donated blood product implicated:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV964)" styleId="INV964" title="If a donated blood product was implicated in an investigation, specify which types of product"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV964-selectedValues');enableOrDisableOther('INV964')" >
<nedss:optionsCollection property="codedValue(PHVS_BLOODPRODUCT_CDC)" value="key" label="value" /> </html:select>
<div id="INV964-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If a donated blood product was implicated in an investigation, specify which types of product" id="INV964OthL">Other Type of donated blood product implicated:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV964Oth)" size="40" maxlength="40" title="Other If a donated blood product was implicated in an investigation, specify which types of product" styleId="INV964Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_75" name="Blood Donation Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_75errorMessages">
<b> <a name="NBS_UI_75errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_75"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_75">
<tr id="patternNBS_UI_75" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_75" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_75');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_75" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_75');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_75" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_75','patternNBS_UI_75','questionbodyNBS_UI_75');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_75">
<tr id="nopatternNBS_UI_75" class="odd" style="display:">
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
<tr><td colspan="2" align="left"><span id="NBS_UI_76L">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the date(s) of blood donation(s):</span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="82756_8L" title="Blood Donation Date">
Blood Donation Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82756_8)" maxlength="10" size="10" styleId="82756_8" onkeyup="unhideBatchImg('NBS_UI_75');DateMask(this,null,event)" styleClass="NBS_UI_75" title="Blood Donation Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('82756_8','82756_8Icon'); unhideBatchImg('NBS_UI_75');return false;" styleId="82756_8Icon" onkeypress="showCalendarEnterKey('82756_8','82756_8Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_75">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_75BatchAddFunction()) writeQuestion('NBS_UI_75','patternNBS_UI_75','questionbodyNBS_UI_75')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_75">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_75BatchAddFunction()) writeQuestion('NBS_UI_75','patternNBS_UI_75','questionbodyNBS_UI_75');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_75"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_75BatchAddFunction()) writeQuestion('NBS_UI_75','patternNBS_UI_75','questionbodyNBS_UI_75');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_75"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_75')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_56" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_61L">&nbsp;&nbsp;In the 8 weeks before symptoms/diagnosis:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82762_6L" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient engage in outdoor activities?">
Did the patient engage in outdoor activities?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82762_6)" styleId="82762_6" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient engage in outdoor activities?" onchange="ruleEnDis82762_68320();enableOrDisableOther('82763_4');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82763_4L" title="Which outdoor activities did the patient engage in?">
Outdoor activities:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(82763_4)" styleId="82763_4" title="Which outdoor activities did the patient engage in?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '82763_4-selectedValues');enableOrDisableOther('82763_4')" >
<nedss:optionsCollection property="codedValue(PHVS_OUTDOORACTIVITIES_CDC)" value="key" label="value" /> </html:select>
<div id="82763_4-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Which outdoor activities did the patient engage in?" id="82763_4OthL">Other Outdoor activities:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(82763_4Oth)" size="40" maxlength="40" title="Other Which outdoor activities did the patient engage in?" styleId="82763_4Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="272500005L" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient spend time outdoors in or near wooded or brushy areas?">
Did the patient spend time near wooded areas?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(272500005)" styleId="272500005" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient spend time outdoors in or near wooded or brushy areas?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95898004L" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient notice any tick bites?">
Did the patient notice any tick bites?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95898004)" styleId="95898004" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient notice any tick bites?" onchange="ruleEnDis958980048321();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Tick Bite Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_57errorMessages">
<b> <a name="NBS_UI_57errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_57"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_57">
<tr id="patternNBS_UI_57" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_57" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_57');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_57" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_57');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_57" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_57">
<tr id="nopatternNBS_UI_57" class="odd" style="display:">
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
<tr><td colspan="2" align="left"><span id="NBS_UI_62L">&nbsp;&nbsp;To be filled out if patient observed a tick bite in 8 weeks prior to symptoms/diagnosis</span> </td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="82755_0L" title="If patient noticed tick bite, what was the geographic location?">
Geographic Location of Tick Bite:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(82755_0)" size="25" maxlength="25" title="If patient noticed tick bite, what was the geographic location?" styleId="82755_0" onkeyup="unhideBatchImg('NBS_UI_57');"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="45347_2L" title="If patient noticed tick bite, when did the bite occur?">
Date of Tick Bite:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(45347_2)" maxlength="10" size="10" styleId="45347_2" onkeyup="unhideBatchImg('NBS_UI_57');DateMask(this,null,event)" styleClass="NBS_UI_57" title="If patient noticed tick bite, when did the bite occur?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('45347_2','45347_2Icon'); unhideBatchImg('NBS_UI_57');return false;" styleId="45347_2Icon" onkeypress="showCalendarEnterKey('45347_2','45347_2Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_57')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_58" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL02L" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their county, state, or country of residence?">
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL02)" styleId="TRAVEL02" title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their county, state, or country of residence?" onchange="ruleEnDisTRAVEL028322();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_59" name="Travel History Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_59errorMessages">
<b> <a name="NBS_UI_59errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_59"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_59">
<tr id="patternNBS_UI_59" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_59" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_59');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_59" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_59');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_59" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_59','patternNBS_UI_59','questionbodyNBS_UI_59');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_59">
<tr id="nopatternNBS_UI_59" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_59 InputFieldLabel" id="NBS454L" title="Domestic vs International Travel">
Was the travel domestic or international?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS454)" styleId="NBS454" title="Domestic vs International Travel" onchange="unhideBatchImg('NBS_UI_59');ruleEnDisNBS4548324();ruleEnDisNBS4548323();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_DOMINTNL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_59 InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL05)" styleId="TRAVEL05" title="List any international destinations of recent travel" onchange="unhideBatchImg('NBS_UI_59');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_59 InputFieldLabel" id="82754_3L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(82754_3)" styleId="82754_3" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_59 InputFieldLabel" id="82753_5L" title="Which intrastate counties did the patient travel?">
County of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82753_5)" styleId="82753_5" title="Which intrastate counties did the patient travel?" onchange="unhideBatchImg('NBS_UI_59');">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_59 InputFieldLabel" id="NBS453L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS453)" styleId="NBS453" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_59');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_59 InputFieldLabel" id="TRAVEL16L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL16)" styleId="TRAVEL16" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_59');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06L" title="If the patient traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06" onkeyup="unhideBatchImg('NBS_UI_59');DateMask(this,null,event)" styleClass="NBS_UI_59" title="If the patient traveled, when did they arrive to their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL06','TRAVEL06Icon'); unhideBatchImg('NBS_UI_59');return false;" styleId="TRAVEL06Icon" onkeypress="showCalendarEnterKey('TRAVEL06','TRAVEL06Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07L" title="If the patient traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07" onkeyup="unhideBatchImg('NBS_UI_59');DateMask(this,null,event)" styleClass="NBS_UI_59" title="If the patient traveled, when did they depart from their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL07','TRAVEL07Icon'); unhideBatchImg('NBS_UI_59');return false;" styleId="TRAVEL07Icon" onkeypress="showCalendarEnterKey('TRAVEL07','TRAVEL07Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4L" title="Duration of Stay">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82310_4)" size="3" maxlength="3"  title="Duration of Stay" styleId="82310_4" onkeyup="unhideBatchImg('NBS_UI_59');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_59"/>
<html:select name="PageForm" property="pageClientVO.answer(82310_4Unit)" styleId="82310_4UNIT" onchange="unhideBatchImg('NBS_UI_59')">
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
<tr id="AddButtonToggleNBS_UI_59">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_59BatchAddFunction()) writeQuestion('NBS_UI_59','patternNBS_UI_59','questionbodyNBS_UI_59')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_59">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_59BatchAddFunction()) writeQuestion('NBS_UI_59','patternNBS_UI_59','questionbodyNBS_UI_59');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_59"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_59BatchAddFunction()) writeQuestion('NBS_UI_59','patternNBS_UI_59','questionbodyNBS_UI_59');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_59"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_59')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_60" name="Congenital Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82751_9L" title="Was the patient an infant born to a mother who had babesiosis or Babesia infection during pregnancy?">
Was the patient an infant born to a mother who had babesiosis or Babesia infection during pregnancy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82751_9)" styleId="82751_9" title="Was the patient an infant born to a mother who had babesiosis or Babesia infection during pregnancy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH162L" title="Did the patient's mother test positive for babesiosis or Babesia infection before or at the time of delivery?">
Did the patient's mother test positive for Babesia before/at the time of delivery?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH162)" styleId="MTH162" title="Did the patient's mother test positive for babesiosis or Babesia infection before or at the time of delivery?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH163L" title="Did the patient's mother test positive for babesiosis or Babesia infection after delivery?">
Did the patient's mother test positive for Babesia infection after delivery?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH163)" styleId="MTH163" title="Did the patient's mother test positive for babesiosis or Babesia infection after delivery?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="MTH164L" title="Date of mother's earliest positive test result">
Date of mother's earliest positive test result:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(MTH164)" maxlength="10" size="10" styleId="MTH164" onkeyup="DateMask(this,null,event)" title="Date of mother's earliest positive test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('MTH164','MTH164Icon'); return false;" styleId="MTH164Icon" onkeypress="showCalendarEnterKey('MTH164','MTH164Icon',event)"></html:img>
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
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408315();pgSelectNextFocus(this);">
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
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_BABESIOSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="INV291L" title="For each test performed, provide result">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="For each test performed, provide result" onchange="unhideBatchImg('NBS_UI_43');">
<nedss:optionsCollection property="codedValue(PHVS_LAB_TEST_INTERP)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Provide titer (if applicable)">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Provide titer (if applicable)" styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_43');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="LAB278L" title="Babesia species identified">
Organism Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB278)" styleId="LAB278" title="Babesia species identified" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('LAB278');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABRESULT_BABESIOSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Babesia species identified" id="LAB278OthL">Other Organism Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB278Oth)" size="40" maxlength="40" title="Other Babesia species identified" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="LAB278Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date laboratory specimen was collected">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43" title="Date laboratory specimen was collected"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_43');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="53556_7L" title="Estimated number of infected erythrocytes expressed as a percentage of the total erythrocytes">
Estimated percentage of infected erythrocytes:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(53556_7)" size="10" maxlength="10"  title="Estimated number of infected erythrocytes expressed as a percentage of the total erythrocytes" styleId="53556_7" onkeyup="unhideBatchImg('NBS_UI_43');isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,0,100)"/>
</td></tr>
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
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
