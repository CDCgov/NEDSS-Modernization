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
String tabId = "editMumps";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Symptoms","Complications","Laboratory","Epidemiology","Vaccination Information"};
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
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_5" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576L" title="Indicate whether the patient is/was symptomatic for mumps?">
Did the patient experience any symptoms related to mumps?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV576)" styleId="INV576" title="Indicate whether the patient is/was symptomatic for mumps?" onchange="ruleEnDisINV5768636();ruleHideUnh3866610068635();ruleHideUnh3866610068635();ruleHideUnhNBS3388640();ruleHideUnhNBS3428638();null;enableOrDisableOther('INV301');null;enableOrDisableOther('INV301');enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS342L" title="Did the patient have Parotitis?">
Parotitis (opposite 2nd molars)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS342)" styleId="NBS342" title="Did the patient have Parotitis?" onchange="ruleHideUnhNBS3428638();null;enableOrDisableOther('INV301');enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV301L" title="Indicates if the parotitis is unilateral or bilateral">
Parotitis Laterality:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV301)" styleId="INV301" title="Indicates if the parotitis is unilateral or bilateral" onchange="enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PAROTITISLATERALITY_MUMPS)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicates if the parotitis is unilateral or bilateral" id="INV301OthL">Other Parotitis Laterality:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV301Oth)" size="40" maxlength="40" title="Other Indicates if the parotitis is unilateral or bilateral" styleId="INV301Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="274667000L" title="Indication of whether patient had jaw pain.">
Jaw Pain?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(274667000)" styleId="274667000" title="Indication of whether patient had jaw pain.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS339L" title="Indicate whether the patient had salivary gland swelling.">
Salivary Gland Swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS339)" styleId="NBS339" title="Indicate whether the patient had salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271614004L" title="Indication of whether patient had submandibular salivary gland swelling.">
Submandibular salivary gland swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271614004)" styleId="271614004" title="Indication of whether patient had submandibular salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271615003L" title="Indication of whether the patient had sublingual salivary gland swelling.">
Sublingual salivary gland swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271615003)" styleId="271615003" title="Indication of whether the patient had sublingual salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV288L" title="Date of subjects salivary gland swelling (including parotitis) onset">
Salivary Gland Swelling Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV288)" maxlength="10" size="10" styleId="INV288" onkeyup="DateMask(this,null,event)" title="Date of subjects salivary gland swelling (including parotitis) onset"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV288','INV288Icon'); return false;" styleId="INV288Icon" onkeypress="showCalendarEnterKey('INV288','INV288Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV303L" title="The length of time (days) that the subject exhibited swelling of the salivary gland">
Salivary Gland Swelling Duration in Days:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV303)" size="3" maxlength="3"  title="The length of time (days) that the subject exhibited swelling of the salivary gland" styleId="INV303" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have fever?">
Fever?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have fever?" onchange="ruleHideUnh3866610068635();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the subjects highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the subjects highest measured temperature during this illness?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV202Unit)" styleId="INV202UNIT">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV203L" title="Indicates the date of fever onset">
Date of Fever Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV203)" maxlength="10" size="10" styleId="INV203" onkeyup="DateMask(this,null,event)" title="Indicates the date of fever onset"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV203','INV203Icon'); return false;" styleId="INV203Icon" onkeypress="showCalendarEnterKey('INV203','INV203Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="79890006L" title="Did patient have loss of appetite?">
Loss of Appetite?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(79890006)" styleId="79890006" title="Did patient have loss of appetite?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267031002L" title="Did the patient have tiredness?">
Tiredness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(267031002)" styleId="267031002" title="Did the patient have tiredness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="25064002L" title="Did the patient have headache?">
Headache?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(25064002)" styleId="25064002" title="Did the patient have headache?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have myalgia (muscle pain)?">
Myalgia/Muscle Pain?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have myalgia (muscle pain)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleHideUnhNBS3388640();pgSelectNextFocus(this);">
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

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS337L" title="Notes pertaining to the symptoms indicated for this case.">
Symptom Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS337)" styleId ="NBS337" onkeyup="checkTextAreaLength(this, 2000)" title="Notes pertaining to the symptoms indicated for this case."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_13" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS340L" title="Indicate whether the patient experienced any complications related to mumps.">
Did the patient experience any complications related to mumps?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS340)" styleId="NBS340" title="Indicate whether the patient experienced any complications related to mumps." onchange="ruleEnDisNBS3408633();ruleHideUnh2720330078639();null;enableOrDisableOther('INV307');null;enableOrDisableOther('INV307');ruleHideUnhNBS3438641();enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003L" title="Meningitis">
Meningitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44201003)" styleId="44201003" title="Meningitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="272033007L" title="Deafness">
Deafness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(272033007)" styleId="272033007" title="Deafness" onchange="ruleHideUnh2720330078639();null;enableOrDisableOther('INV307');enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV307L" title="Was the type of deafness permanent or temporary?">
Type of Deafness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV307)" styleId="INV307" title="Was the type of deafness permanent or temporary?" onchange="enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_DEAFNESSTYPE_MUMPS)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Was the type of deafness permanent or temporary?" id="INV307OthL">Other Type of Deafness:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV307Oth)" size="40" maxlength="40" title="Other Was the type of deafness permanent or temporary?" styleId="INV307Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="78580004L" title="Orchitis">
Orchitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(78580004)" styleId="78580004" title="Orchitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008L" title="Encephalitis">
Encephalitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(31646008)" styleId="31646008" title="Encephalitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="237443002L" title="Mastitis">
Mastitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(237443002)" styleId="237443002" title="Mastitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75548002L" title="Oophoritis">
Oophoritis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75548002)" styleId="75548002" title="Oophoritis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="10665004L" title="Pancreatitis">
Pancreatitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(10665004)" styleId="10665004" title="Pancreatitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS343L" title="Other Complications">
Other Complication(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS343)" styleId="NBS343" title="Other Complications" onchange="ruleHideUnhNBS3438641();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS343_OTHL" title="Specify other complicationsh.">
Specify Other Complication(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS343_OTH)" styleId ="NBS343_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify other complicationsh."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_15" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408643();ruleEnDisINV7408637();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV164L" title="Was the case laboratory confirmed?">
Was the case laboratory confirmed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV164)" styleId="INV164" title="Was the case laboratory confirmed?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB515L" title="Was a specimen sent to CDC for testing?">
Was a specimen sent to CDC for testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB515)" styleId="LAB515" title="Was a specimen sent to CDC for testing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="LEGACY Date Sent for Genotyping" id="NBS380L">
LEGACY Date Sent for Genotyping:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS380)" maxlength="10" size="10" styleId="NBS380" title="LEGACY Date Sent for Genotyping"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS380','NBS380Icon');return false;" styleId="NBS380Icon" onkeypress="showCalendarEnterKey('NBS380','NBS380Icon',event);" ></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_16" name="Lab Interpretive Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_MUM_UI_16errorMessages">
<b> <a name="NBS_UI_NBS_INV_MUM_UI_16errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_16"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_16">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_16" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_MUM_UI_16" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_16');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_NBS_INV_MUM_UI_16" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_16');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_NBS_INV_MUM_UI_16" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_16','patternNBS_UI_NBS_INV_MUM_UI_16','questionbodyNBS_UI_NBS_INV_MUM_UI_16');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_16">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_16" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="INV290L" title="Test Type">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Test Type" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_MUMPS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Test Type" id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Test Type" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="INV291L" title="Test Result (Qualitative)">
Test Result (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Test Result (Qualitative)" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Test Result (Qualitative)" id="INV291OthL">Other Test Result (Qualitative):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV291Oth)" size="40" maxlength="40" title="Other Test Result (Qualitative)" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="INV291Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Quantitative Test Result Value.">
Test Result (Quantitative):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Quantitative Test Result Value." styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB115L" title="Units of measure for the Quantitative Test Result Value">
Quantitative Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB115)" styleId="LAB115" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');DateMask(this,null,event)" styleClass="NBS_UI_NBS_INV_MUM_UI_16" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB165L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB165)" styleId="LAB165" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMENTYPEVPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="LAB165Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB516L" title="Date specimen sent to CDC">
Date Specimen Sent to CDC:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB516)" maxlength="10" size="10" styleId="LAB516" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');DateMask(this,null,event)" styleClass="NBS_UI_NBS_INV_MUM_UI_16" title="Date specimen sent to CDC"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB516','LAB516Icon'); unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');return false;" styleId="LAB516Icon" onkeypress="showCalendarEnterKey('LAB516','LAB516Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB606L" title="Enter the performing laboratory type">
Performing Lab Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB606)" styleId="LAB606" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Lab Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="LAB606Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_16">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16','patternNBS_UI_NBS_INV_MUM_UI_16','questionbodyNBS_UI_NBS_INV_MUM_UI_16')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_16">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16','patternNBS_UI_NBS_INV_MUM_UI_16','questionbodyNBS_UI_NBS_INV_MUM_UI_16');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_MUM_UI_16"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16','patternNBS_UI_NBS_INV_MUM_UI_16','questionbodyNBS_UI_NBS_INV_MUM_UI_16');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_MUM_UI_16"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_NBS_INV_MUM_UI_16')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_17" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_MUM_UI_17errorMessages">
<b> <a name="NBS_UI_NBS_INV_MUM_UI_17errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_17"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_17">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_17" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_MUM_UI_17" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_17');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_NBS_INV_MUM_UI_17" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_17');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_NBS_INV_MUM_UI_17" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_17','patternNBS_UI_NBS_INV_MUM_UI_17','questionbodyNBS_UI_NBS_INV_MUM_UI_17');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_17">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_17" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_NBS_INV_MUM_UI_17 InputFieldLabel" id="LAB143L" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Reference Laboratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB143)" styleId="LAB143" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');">
<nedss:optionsCollection property="codedValue(VPD_LAB_REFERENCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB598L" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Patient Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB598)" size="25" maxlength="25" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message" styleId="LAB598" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB125L" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message">
VPD Lab Message Specimen Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB125)" size="25" maxlength="25" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message" styleId="LAB125" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_17">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17','patternNBS_UI_NBS_INV_MUM_UI_17','questionbodyNBS_UI_NBS_INV_MUM_UI_17')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_17">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17','patternNBS_UI_NBS_INV_MUM_UI_17','questionbodyNBS_UI_NBS_INV_MUM_UI_17');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_MUM_UI_17"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17','patternNBS_UI_NBS_INV_MUM_UI_17','questionbodyNBS_UI_NBS_INV_MUM_UI_17');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_MUM_UI_17"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_NBS_INV_MUM_UI_17')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_19" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM225L" title="Indicate the patients length of time in the U.S. since the last travel">
Length of time in the U.S. since last travel:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM225)" size="3" maxlength="3"  title="Indicate the patients length of time in the U.S. since the last travel" styleId="DEM225" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,364)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(DEM225Unit)" styleId="DEM225UNIT">
<nedss:optionsCollection property="codedValue(DUR_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
International Destination(s) of Recent Travel:</span>
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL08L" title="Date of return from most recent travel">
Date of Return from Travel:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL08)" maxlength="10" size="10" styleId="TRAVEL08" onkeyup="DateMask(this,null,event)" title="Date of return from most recent travel"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL08','TRAVEL08Icon'); return false;" styleId="TRAVEL08Icon" onkeypress="showCalendarEnterKey('TRAVEL08','TRAVEL08Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV224L" title="What was the transmission setting where the condition was acquired?">
Transmission Setting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV224)" styleId="INV224" title="What was the transmission setting where the condition was acquired?" onchange="enableOrDisableOther('INV224');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAN_SETNG)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the transmission setting where the condition was acquired?" id="INV224OthL">Other Transmission Setting:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV224Oth)" size="40" maxlength="40" title="Other What was the transmission setting where the condition was acquired?" styleId="INV224Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV216L" title="Does the age of the case match or make sense for the transmission setting listed?">
Were age and setting verified?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV216)" styleId="INV216" title="Does the age of the case match or make sense for the transmission setting listed?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="Enter State ID if source was an in-state case; enter Country if source was out of U.S.; enter State if source was out-of-sate." id="NBS341L">
LEGACY Source of Infection (Exposure) for Current Case:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS341)" size="100" maxlength="100" title="Enter State ID if source was an in-state case; enter Country if source was out of U.S.; enter State if source was out-of-sate." styleId="NBS341"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV293L" title="Did parotitis or other mumps-associated complication onset occur within 12-25 days of entering the U.S., following any travel or living outside U.S.? (Import Status)">
Symptom onset 12-25 days of entering U.S., following travel/living outside U.S.? (Import Status):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV293)" styleId="INV293" title="Did parotitis or other mumps-associated complication onset occur within 12-25 days of entering the U.S., following any travel or living outside U.S.? (Import Status)">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV516L" title="If this is a U.S.-acquired case, how should the case be classified by source?">
If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV516)" styleId="INV516" title="If this is a U.S.-acquired case, how should the case be classified by source?" onchange="enableOrDisableOther('INV516');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_CASECLASSIFICATIONEXPOSURESOURCE_NND)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If this is a U.S.-acquired case, how should the case be classified by source?" id="INV516OthL">Other If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV516Oth)" size="40" maxlength="40" title="Other If this is a U.S.-acquired case, how should the case be classified by source?" styleId="INV516Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV217L" title="Is this case epi-linked to another confirmed or probable case?">
Is this case epi-linked to another confirmed or probable case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV217)" styleId="INV217" title="Is this case epi-linked to another confirmed or probable case?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_22" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Did patient ever receive mumps-containing vaccine?">
Did patient ever receive mumps-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Did patient ever receive mumps-containing vaccine?" onchange="ruleEnDisVAC1268634();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_NBS_INV_MUM_UI_23L">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</span> </td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC129L" title="Indicate the number of vaccine doses against mumps the patient received on or after his or her first birthday. To indicate that the number of doses is unknown, enter 99.">
Number of doses received ON or AFTER first birthday:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC129)" size="2" maxlength="2"  title="Indicate the number of vaccine doses against mumps the patient received on or after his or her first birthday. To indicate that the number of doses is unknown, enter 99." styleId="VAC129" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,99)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140L" title="Indicate the number of vaccine doses against mumps the patient received prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Number of vaccine doses against mumps received prior to illness onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC140)" size="2" maxlength="2"  title="Indicate the number of vaccine doses against mumps the patient received prior to illness onset. To indicate that the number of doses is unknown, enter 99." styleId="VAC140" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,99)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC142L" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset.">
Date of last dose prior to illness onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC142)" maxlength="10" size="10" styleId="VAC142" onkeyup="DateMask(this,null,event)" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC142','VAC142Icon'); return false;" styleId="VAC142Icon" onkeypress="showCalendarEnterKey('VAC142','VAC142Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC148L" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules.">
Was the patient vaccinated per ACIP recommendations?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC148)" styleId="VAC148" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules." onchange="ruleHideUnhVAC1488642();enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149L" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
Reason patient not vaccinated per ACIP recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC149)" styleId="VAC149" title="Indicate the reason the patient was not vaccinated as recommended by ACIP." onchange="enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_VAC_NOTG_RSN)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the reason the patient was not vaccinated as recommended by ACIP." id="VAC149OthL">Other Reason patient not vaccinated per ACIP recommendations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(VAC149Oth)" size="40" maxlength="40" title="Other Indicate the reason the patient was not vaccinated as recommended by ACIP." styleId="VAC149Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC133L" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form.">
Notes pertaining to the patient's vaccination history:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(VAC133)" styleId ="VAC133" onkeyup="checkTextAreaLength(this, 2000)" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form."/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
