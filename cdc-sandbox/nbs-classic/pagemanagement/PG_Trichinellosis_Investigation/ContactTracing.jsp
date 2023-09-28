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
String tabId = "editContactTracing";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Investigation","Retired Data Elements"};
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
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS055L" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
Contact Investigation Priority:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS055)" styleId="NBS055" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
<nedss:optionsCollection property="codedValue(NBS_PRIORITY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS056L" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period.">
Infectious Period From:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS056)" maxlength="10" size="10" styleId="NBS056" onkeyup="DateMaskFuture(this,null,event)" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS056','NBS056Icon'); return false;" styleId="NBS056Icon" onkeypress ="showCalendarFutureEnterKey('NBS056','NBS056Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS057L" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period.">
Infectious Period To:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS057)" maxlength="10" size="10" styleId="NBS057" onkeyup="DateMaskFuture(this,null,event)" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS057','NBS057Icon'); return false;" styleId="NBS057Icon" onkeypress ="showCalendarFutureEnterKey('NBS057','NBS057Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS058L" title="The status of the contact investigation.">
Contact Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS058)" styleId="NBS058" title="The status of the contact investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS059L" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation.">
Contact Investigation Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS059)" styleId ="NBS059" onkeyup="checkTextAreaLength(this, 2000)" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101000" name="Legacy Animal Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV667L" title="Did the case have contact with an animal?">
(Legacy) Did the case have contact with an animal?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV667)" styleId="INV667" title="Did the case have contact with an animal?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_32L" title="Animal contact by type of animal">
(Legacy) Animal Type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_32)" styleId="FDD_Q_32" title="Animal contact by type of animal"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_32-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_ANIMALST)" value="key" label="value" /> </html:select>
<div id="FDD_Q_32-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_243L" title="If Other, please specify other type of animal">
(Legacy) If Other, please specify other type of animal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_243)" size="50" maxlength="50" title="If Other, please specify other type of animal" styleId="FDD_Q_243"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_295L" title="If Other Amphibian, please specify other type of amphibian">
(Legacy) If Other Amphibian, please specify other type of amphibian:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_295)" size="50" maxlength="50" title="If Other Amphibian, please specify other type of amphibian" styleId="FDD_Q_295"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_296L" title="If Other Reptile, please specify other type of reptile">
(Legacy) If Other Reptile, please specify other type of reptile:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_296)" size="50" maxlength="50" title="If Other Reptile, please specify other type of reptile" styleId="FDD_Q_296"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_374L" title="If Other Mammal, please specify other type of mammal">
(Legacy) If Other Mammal, please specify other type of mammal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_374)" size="50" maxlength="50" title="If Other Mammal, please specify other type of mammal" styleId="FDD_Q_374"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_33L" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?">
(Legacy) Animal Contact Location:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(FDD_Q_33)" styleId ="FDD_Q_33" onkeyup="checkTextAreaLength(this, 200)" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_34L" title="Did the patient acquire a pet prior to onset of illness?">
(Legacy) Did the patient acquire a pet prior to onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_34)" styleId="FDD_Q_34" title="Did the patient acquire a pet prior to onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_244L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_244)" size="50" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_244"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101001" name="Legacy Underlying Conditions" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_233L" title="Did patient have any of the following underlying conditions?">
(Legacy) Did patient have any of the following underlying conditions?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_233)" styleId="FDD_Q_233" title="Did patient have any of the following underlying conditions?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_233-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_DISEASES)" value="key" label="value" /> </html:select>
<div id="FDD_Q_233-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_234L" title="If Other Prior Illness, please specify">
(Legacy) If Other Prior Illness, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_234)" size="50" maxlength="50" title="If Other Prior Illness, please specify" styleId="FDD_Q_234"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_235L" title="If Diabetes Mellitus, specify whether on insulin">
(Legacy) If Diabetes Mellitus, specify whether on insulin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_235)" styleId="FDD_Q_235" title="If Diabetes Mellitus, specify whether on insulin">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_237L" title="If Gastric Surgery, please specify type">
(Legacy) If Gastric Surgery, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_237)" size="50" maxlength="50" title="If Gastric Surgery, please specify type" styleId="FDD_Q_237"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_238L" title="If Hematologic Disease, please specify type">
(Legacy) If Hematologic Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_238)" size="50" maxlength="50" title="If Hematologic Disease, please specify type" styleId="FDD_Q_238"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_239L" title="If Immunodeficiency, please specify type:">
(Legacy) If Immunodeficiency, please specify type::</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_239)" size="50" maxlength="50" title="If Immunodeficiency, please specify type:" styleId="FDD_Q_239"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_240L" title="If Other Liver Disease, please specify type">
(Legacy) If Other Liver Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_240)" size="50" maxlength="50" title="If Other Liver Disease, please specify type" styleId="FDD_Q_240"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_241L" title="If Other Malignancy, please specify type">
(Legacy) If Other Malignancy, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_241)" size="50" maxlength="50" title="If Other Malignancy, please specify type" styleId="FDD_Q_241"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_242L" title="If Other Renal Disease, please specify">
(Legacy) If Other Renal Disease, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_242)" size="50" maxlength="50" title="If Other Renal Disease, please specify" styleId="FDD_Q_242"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_236L" title="If Organ Transplant, please specify organ">
(Legacy) If Organ Transplant, please specify organ:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_236)" size="50" maxlength="50" title="If Organ Transplant, please specify organ" styleId="FDD_Q_236"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101002" name="Legacy Related Cases" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_77L" title="Does the patient know of other similarly ill persons?">
(Legacy) Does the patient know of other similarly ill persons?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_77)" styleId="FDD_Q_77" title="Does the patient know of other similarly ill persons?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_78L" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
(Legacy) If Yes, did the health department collect contact information about other similarly ill persons and:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_78)" styleId="FDD_Q_78" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_79L" title="Are there other cases related to this one?">
(Legacy) Are there other cases related to this one?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_79)" styleId="FDD_Q_79" title="Are there other cases related to this one?">
<nedss:optionsCollection property="codedValue(PHVSFB_EPIDEMGY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102002" name="Legacy Suspect Food" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_139L" title="What suspect foods did the patient eat?">
(Legacy) What suspect foods did the patient eat?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_139)" styleId="FDD_Q_139" title="What suspect foods did the patient eat?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_139-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_PORKONOT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_139-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_144L" title="Where was the suspect (Pork) meat obtained?">
(Legacy) Where was the suspect (Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_144)" styleId="FDD_Q_144" title="Where was the suspect (Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_144-selectedValues');enableOrDisableOther('FDD_Q_144')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_144-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Pork) meat obtained?" id="FDD_Q_144OthL">Other (Legacy) Where was the suspect (Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(FDD_Q_144Oth)" size="40" maxlength="40" title="Other Where was the suspect (Pork) meat obtained?" styleId="FDD_Q_144Oth"/></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_154L" title="Where was the suspect (Non-Pork) meat obtained?">
(Legacy) Where was the suspect (Non-Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_154)" styleId="FDD_Q_154" title="Where was the suspect (Non-Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_154-selectedValues');enableOrDisableOther('FDD_Q_154')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_154-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Non-Pork) meat obtained?" id="FDD_Q_154OthL">Other (Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(FDD_Q_154Oth)" size="40" maxlength="40" title="Other Where was the suspect (Non-Pork) meat obtained?" styleId="FDD_Q_154Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102000" name="Legacy Food Handler" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_8L" title="Did patient work as a food handler after onset of illness?">
(Legacy) Did patient work as a food handler after onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_8)" styleId="FDD_Q_8" title="Did patient work as a food handler after onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="FDD_Q_9L" title="What was last date worked as a food handler after onset of illness?">
(Legacy) What was last date worked as a food handler after onset of illness?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(FDD_Q_9)" maxlength="10" size="10" styleId="FDD_Q_9" onkeyup="DateMask(this,null,event)" title="What was last date worked as a food handler after onset of illness?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('FDD_Q_9','FDD_Q_9Icon'); return false;" styleId="FDD_Q_9Icon" onkeypress="showCalendarEnterKey('FDD_Q_9','FDD_Q_9Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_10L" title="Where was patient a food handler?">
(Legacy) Where was patient a food handler?:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_10)" size="50" maxlength="50" title="Where was patient a food handler?" styleId="FDD_Q_10"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102001" name="Legacy Travel History" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_12L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_12)" size="50" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_12"/>
</td> </tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_13L" title="What was the purpose of the travel?">
(Legacy) What was the purpose of the travel?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_13)" styleId="FDD_Q_13" title="What was the purpose of the travel?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_13-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_TRAVELTT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_13-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_14L" title="If Other, please specify other purpose of travel">
(Legacy) If Other, please specify other purpose of travel:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_14)" size="50" maxlength="50" title="If Other, please specify other purpose of travel" styleId="FDD_Q_14"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_20L" title="If more than 3 destinations, specify details here">
(Legacy) If more than 3 destinations, specify details here:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_20)" size="50" maxlength="50" title="If more than 3 destinations, specify details here" styleId="FDD_Q_20"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
