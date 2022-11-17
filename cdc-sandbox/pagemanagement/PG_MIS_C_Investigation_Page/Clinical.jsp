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
String tabId = "editClinical";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Inclusion Criteria","Comorbidities","Hospitalization","Clinical Signs and Symptoms","Complications","Treatments","Vaccination"};
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
<nedss:container id="NBS_UI_GA28002" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS694L" title="Was the patient less than 21 years old at illness onset?">
Age &lt; 21:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS694)" styleId="NBS694" title="Was the patient less than 21 years old at illness onset?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426000000L" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
Fever For At Least 24 Hrs:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(426000000)" styleId="426000000" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS695L" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
Laboratory Markers of Inflammation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS695)" styleId="NBS695" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="434081000124108L" title="Evidence of clinically severe illness requiring hospitalization.">
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(434081000124108)" styleId="434081000124108" title="Evidence of clinically severe illness requiring hospitalization." onchange="ruleEnDis4340810001241088748();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="56265001L" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
Cardiac:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(56265001)" styleId="56265001" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="90708001L" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
Renal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(90708001)" styleId="90708001" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50043002L" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Respiratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(50043002)" styleId="50043002" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="128480004L" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
Hematologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(128480004)" styleId="128480004" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="119292006L" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
Gastrointestinal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(119292006)" styleId="119292006" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95320005L" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Dermatologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95320005)" styleId="95320005" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="118940003L" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
Neurological:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(118940003)" styleId="118940003" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS696L" title="Indicates whether there is no alternative plausible diagnosis.">
No alternative plausible diagnosis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS696)" styleId="NBS696" title="Indicates whether there is no alternative plausible diagnosis.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS697L" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?">
Positive for current or recent SARS-COV-2 infection by lab testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS697)" styleId="NBS697" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" onchange="ruleEnDisNBS6978749();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS709L" title="Was RT-PCR testing completed for this disease?">
RT-PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS709)" styleId="NBS709" title="Was RT-PCR testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS710L" title="Was serology testing completed for this disease?">
Serology:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS710)" styleId="NBS710" title="Was serology testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS711L" title="Was antigen testing completed for this disease?">
Antigen:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS711)" styleId="NBS711" title="Was antigen testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="840546002L" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?">
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(840546002)" styleId="840546002" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" onchange="ruleEnDis8405460028746();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP248166_3L" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior.">
Date of first exposure within the 4 weeks prior:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP248166_3)" maxlength="10" size="10" styleId="LP248166_3" onkeyup="DateMask(this,null,event)" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP248166_3','LP248166_3Icon'); return false;" styleId="LP248166_3Icon" onkeypress="showCalendarEnterKey('LP248166_3','LP248166_3Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS698L" title="Exposure date unknown.">
Exposure Date Unknown:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS698)" styleId="NBS698" title="Exposure date unknown.">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS434L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS434)" size="10" maxlength="10"  title="Enter the height of the patient in inches." styleId="NBS434" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM255L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM255)" size="5" maxlength="5"  title="Enter the patients weight at diagnosis in pounds (lbs)." styleId="DEM255" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="39156_5L" title="What is the patient's body mass index?">
BMI:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(39156_5)" size="10" maxlength="10"  title="What is the patient's body mass index?" styleId="39156_5" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="370388006L" title="Does the patient have a history of an immunocompromised condition?">
Immunosuppressive disorder or malignancy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(370388006)" styleId="370388006" title="Does the patient have a history of an immunocompromised condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414916001L" title="Is the patient obese?">
Obesity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(414916001)" styleId="414916001" title="Is the patient obese?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="46635009L" title="Does the patient have type 1 diabetes?">
Type 1 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(46635009)" styleId="46635009" title="Does the patient have type 1 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44054006L" title="Does the patient have type 2 diabetes?">
Type 2 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44054006)" styleId="44054006" title="Does the patient have type 2 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91175000L" title="Did the patient have seizures?">
Seizures:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(91175000)" styleId="91175000" title="Did the patient have seizures?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="13213009L" title="Congenital heart disease">
Congenital heart disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(13213009)" styleId="13213009" title="Congenital heart disease">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="417357006L" title="Does the patient have sickle cell disease?">
Sickle cell disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(417357006)" styleId="417357006" title="Does the patient have sickle cell disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413839001L" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
Chronic Lung Disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(413839001)" styleId="413839001" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="276654001L" title="Does the patient have any other congenital malformations?">
Other congenital malformations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(276654001)" styleId="276654001" title="Does the patient have any other congenital malformations?" onchange="ruleEnDis2766540018747();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="276654001_OTHL" title="Specify other congenital malformations.">
Specify other congenital malformations:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(276654001_OTH)" size="100" maxlength="100" title="Specify other congenital malformations." styleId="276654001_OTH"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288733();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328739();ruleEnDis3099040018750();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="INV184">
<span id="INV184L" title="The hospital associated with the investigation.">
Hospital:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV184Uid">
<span id="clearINV184" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
<span id="clearINV184">
</logic:notEmpty>
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="INV184">
<input type="button" class="Button" value="Clear/Reassign" id="INV184CodeClearButton" onclick="clearOrganization('INV184')"/>
</logic:notEqual>
</span>
<span id="INV184SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV184Icon" onclick="getReportingOrg('INV184');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV184)" styleId="INV184Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV184Text','INV184_qec_list')"
title="The hospital associated with the investigation."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV184CodeLookupButton" onclick="getDWROrganization('INV184')"
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV184_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="INV184S">Hospital Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV184Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV184Uid"/>
<span id="INV184">${PageForm.attributeMap.INV184SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV184Error"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV132L" title="Subject's admission date to the hospital for the condition covered by the investigation.">
Hospital Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV132)" maxlength="10" size="10" styleId="INV132" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" title="Subject's admission date to the hospital for the condition covered by the investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV132','INV132Icon'); return false;" styleId="INV132Icon" onkeypress="showCalendarEnterKey('INV132','INV132Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Hospital Discharge:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV133)" maxlength="10" size="10" styleId="INV133" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" title="Subject's discharge date from the hospital for the condition covered by the investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV133','INV133Icon'); return false;" styleId="INV133Icon" onkeypress="showCalendarEnterKey('INV133','INV133Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV134L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV134)" size="3" maxlength="3"  title="Subject's duration of stay at the hospital for the condition covered by the investigation." styleId="INV134" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="309904001L" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?">
Was patient admitted to ICU?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(309904001)" styleId="309904001" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" onchange="ruleEnDis3099040018750();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS679L" title="Enter the date of admission.">
ICU Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS679)" maxlength="10" size="10" styleId="NBS679" onkeyup="DateMask(this,null,event)" title="Enter the date of admission."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS679','NBS679Icon'); return false;" styleId="NBS679Icon" onkeypress="showCalendarEnterKey('NBS679','NBS679Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="74200_7L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(74200_7)" size="3" maxlength="3"  title="Indicate the number of days the patient was in intensive care." styleId="74200_7" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,364)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_1038L" title="Patients Outcome">
Patients Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_1038)" styleId="FDD_Q_1038" title="Patients Outcome">
<nedss:optionsCollection property="codedValue(PATIENT_HOSP_STATUS_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458734();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV146L" title="The date the subject’s death occurred.">
Date of Death:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV146)" maxlength="10" size="10" styleId="INV146" onkeyup="DateMask(this,null,event)" title="The date the subject’s death occurred."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV146','INV146Icon'); return false;" styleId="INV146Icon" onkeypress="showCalendarEnterKey('INV146','INV146Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29007" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS707L" title="Did the patient have preceding COVID-like illness?">
Did patient have preceding COVID-like illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS707)" styleId="NBS707" title="Did the patient have preceding COVID-like illness?" onchange="ruleEnDisNBS7078759();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS708L" title="Date of COVID-like illness symptom onset.">
Date of COVID-like illness symptom onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS708)" maxlength="10" size="10" styleId="NBS708" onkeyup="DateMask(this,null,event)" title="Date of COVID-like illness symptom onset."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS708','NBS708Icon'); return false;" styleId="NBS708Icon" onkeypress="showCalendarEnterKey('NBS708','NBS708Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30015L">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136L" title="Date of diagnosis of MIS-C.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV136)" maxlength="10" size="10" styleId="INV136" onkeyup="DateMask(this,null,event)" title="Date of diagnosis of MIS-C."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV136','INV136Icon'); return false;" styleId="INV136Icon" onkeypress="showCalendarEnterKey('INV136','INV136Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV137L" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system.">
Illness Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV137)" maxlength="10" size="10" styleId="INV137" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV137','INV137Icon'); return false;" styleId="INV137Icon" onkeypress="showCalendarEnterKey('INV137','INV137Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV138L" title="The time at which the disease or condition ends.">
Illness End Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV138)" maxlength="10" size="10" styleId="INV138" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" title="The time at which the disease or condition ends."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV138','INV138Icon'); return false;" styleId="INV138Icon" onkeypress="showCalendarEnterKey('INV138','INV138Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV139L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV139)" size="3" maxlength="3"  title="The length of time this person had this disease or condition." styleId="INV139" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV140L" title="Unit of time used to describe the length of the illness or condition.">
Illness Duration Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV140)" styleId="INV140" title="Unit of time used to describe the length of the illness or condition.">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV143L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV143)" size="3" maxlength="3"  title="Subject's age at the onset of the disease or condition." styleId="INV143" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV144L" title="The age units for an age.">
Age at Onset Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV144)" styleId="INV144" title="The age units for an age.">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have fever?">
Fever Greater Than 38.0 C or 100.4 F:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have fever?" onchange="ruleEnDis3866610068751();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
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

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the subjects highest measured temperature during this illness, in degress Celsius?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR125L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAR125)" size="20" maxlength="20"  title="Total number of days fever lasted" styleId="VAR125" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA29001L">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="444931001L" title="Did the patient have elevated troponin?">
Elevated troponin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(444931001)" styleId="444931001" title="Did the patient have elevated troponin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414798009L" title="Does the patient have elevated BNP or NT-proBNP?">
Elevated BNP or NT-proBNP:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(414798009)" styleId="414798009" title="Does the patient have elevated BNP or NT-proBNP?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="14350001000004108L" title="Did the patient have acute kidney injury?">
Acute kidney injury:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(14350001000004108)" styleId="14350001000004108" title="Did the patient have acute kidney injury?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="42399005L" title="Did the patient have renal failure?">
Renal failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(42399005)" styleId="42399005" title="Did the patient have renal failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002L" title="Did the patients illness include the symptom of cough?">
Cough:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49727002)" styleId="49727002" title="Did the patients illness include the symptom of cough?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267036007L" title="Did the patient have shortness of breath (dyspnea)?">
Shortness of breath (dyspnea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(267036007)" styleId="267036007" title="Did the patient have shortness of breath (dyspnea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29857009L" title="Did the patient experience chest pain?">
Chest pain or tightness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(29857009)" styleId="29857009" title="Did the patient experience chest pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA29000L">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59282003L" title="Did the patient have pulmonary embolism?">
Pulmonary embolism:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(59282003)" styleId="59282003" title="Did the patient have pulmonary embolism?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="449830004L" title="Did the patient have elevated D-dimers?">
Elevated D-dimers:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(449830004)" styleId="449830004" title="Did the patient have elevated D-dimers?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="234467004L" title="Did the patient have thrombophilia?">
Thrombophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(234467004)" styleId="234467004" title="Did the patient have thrombophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302215000L" title="Did the subject have thrombocytopenia?">
Thrombocytopenia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(302215000)" styleId="302215000" title="Did the subject have thrombocytopenia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="21522001L" title="Did the patient have abdominal pain or tenderness?">
Abdominal pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(21522001)" styleId="21522001" title="Did the patient have abdominal pain or tenderness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="422400008L" title="Did the subject experience vomiting?">
Vomiting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(422400008)" styleId="422400008" title="Did the subject experience vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62315008L" title="Did the patient have diarrhea?">
Diarrhea:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(62315008)" styleId="62315008" title="Did the patient have diarrhea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="26165005L" title="Did the patient have elevated bilirubin?">
Elevated bilirubin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(26165005)" styleId="26165005" title="Did the patient have elevated bilirubin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="707724006L" title="Did the patient have elevated liver enzymes?">
Elevated liver enzymes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(707724006)" styleId="707724006" title="Did the patient have elevated liver enzymes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022" name="Dermatologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271807003L" title="Did the patient have rash?">
Rash:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271807003)" styleId="271807003" title="Did the patient have rash?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95346009L" title="Did the patient have mucocutaneous lesions?">
Mucocutaneous lesions:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95346009)" styleId="95346009" title="Did the patient have mucocutaneous lesions?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023" name="Neurological" isHidden="F" classType="subSect" >

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
<span class=" InputFieldLabel" id="419284004L" title="Did the patient have altered mental status?">
Altered Mental Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(419284004)" styleId="419284004" title="Did the patient have altered mental status?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS712L" title="Did the patient have syncope/near syncope?">
Syncope/near syncope:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS712)" styleId="NBS712" title="Did the patient have syncope/near syncope?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003L" title="Did the patient have meningitis?">
Meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44201003)" styleId="44201003" title="Did the patient have meningitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81308009L" title="Did the patient have encephalopathy?">
Encephalopathy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(81308009)" styleId="81308009" title="Did the patient have encephalopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81680005L" title="Did the patient have neck pain?">
Neck pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(81680005)" styleId="81680005" title="Did the patient have neck pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have myalgia?">
Myalgia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have myalgia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="193894004L" title="Did the patient have conjunctival injection?">
Conjunctival injection:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(193894004)" styleId="193894004" title="Did the patient have conjunctival injection?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000L" title="Did the patient have periorbital edema?">
Periorbital edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49563000)" styleId="49563000" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="127086001L" title="Did the patient have cervical lymphadenopathy?">
Cervical lymphadenopathy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(127086001)" styleId="127086001" title="Did the patient have cervical lymphadenopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="698247007L" title="Did the patient have arrhythmia?">
Arrhythmia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(698247007)" styleId="698247007" title="Did the patient have arrhythmia?" onchange="ruleEnDis6982470078752();enableOrDisableOther('76281_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="76281_5L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(76281_5)" styleId="76281_5" title="Indicate the type of arrhythmia."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '76281_5-selectedValues');enableOrDisableOther('76281_5')" >
<nedss:optionsCollection property="codedValue(CARDIAC_ARRHYTHMIA_TYPE)" value="key" label="value" /> </html:select>
<div id="76281_5-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the type of arrhythmia." id="76281_5OthL">Other Type of arrhythmia:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(76281_5Oth)" size="40" maxlength="40" title="Other Indicate the type of arrhythmia." styleId="76281_5Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB020L" title="Did the patient have congestive heart failure?">
Congestive Heart Failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB020)" styleId="ARB020" title="Did the patient have congestive heart failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50920009L" title="Did the patient have myocarditis?">
Myocarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(50920009)" styleId="50920009" title="Did the patient have myocarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA29003L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3238004L" title="Did the patient have pericarditis?">
Pericarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(3238004)" styleId="3238004" title="Did the patient have pericarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59927004L" title="Did the patient have liver failure?">
Liver failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(59927004)" styleId="59927004" title="Did the patient have liver failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS713L" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS713)" styleId="NBS713" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67782005L" title="Did the patient have acute respiratory distress syndrome?">
ARDS:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(67782005)" styleId="67782005" title="Did the patient have acute respiratory distress syndrome?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233604007L" title="Did the patient have pneumonia?">
Pneumonia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233604007)" styleId="233604007" title="Did the patient have pneumonia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB021L" title="Did patient develop CVA or stroke?">
CVA or Stroke:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB021)" styleId="ARB021" title="Did patient develop CVA or stroke?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008L" title="Did the patient have encephalitis?">
Encephalitis or aseptic meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(31646008)" styleId="31646008" title="Did the patient have encephalitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="27942005L" title="Did the subject have septic shock?">
Shock:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(27942005)" styleId="27942005" title="Did the subject have septic shock?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="45007003L" title="Did the patient have hypotension?">
Hypotension:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(45007003)" styleId="45007003" title="Did the patient have hypotension?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="466713001L" title="Did the patient receive low flow nasal cannula?">
Low flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(466713001)" styleId="466713001" title="Did the patient receive low flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426854004L" title="Did the patient receive high flow nasal cannula?">
High flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(426854004)" styleId="426854004" title="Did the patient receive high flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="428311008L" title="Did the patient receive non-invasive ventilation?">
Non-invasive ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(428311008)" styleId="428311008" title="Did the patient receive non-invasive ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="52765003L" title="Did the patient receive intubation?">
Intubation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(52765003)" styleId="52765003" title="Did the patient receive intubation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS673L" title="Did the patient receive mechanical ventilation?">
Mechanical ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS673)" styleId="NBS673" title="Did the patient receive mechanical ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233573008L" title="Did the patient receive ECMO?">
ECMO:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233573008)" styleId="233573008" title="Did the patient receive ECMO?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS699L" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?">
Vasoactive medications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS699)" styleId="NBS699" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" onchange="ruleEnDisNBS6998755();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS700L" title="Specify the vasoactive medications the patient received.">
Specify vasoactive medications:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS700)" size="100" maxlength="100" title="Specify the vasoactive medications the patient received." styleId="NBS700"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB040L" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
Steroids:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB040)" styleId="ARB040" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB045L" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?">
Immune modulators:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB045)" styleId="ARB045" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" onchange="ruleEnDisARB0458756();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="373244000L" title="Specify immune modulators the patient received.">
Specify immune modulators:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(373244000)" size="100" maxlength="100" title="Specify immune modulators the patient received." styleId="373244000"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA29005L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372560006L" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?">
Antiplatelets:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(372560006)" styleId="372560006" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" onchange="ruleEnDis3725600068753();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372560006_OTHL" title="Specify antiplatelets the patient received.">
Specify antiplatelets:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(372560006_OTH)" size="100" maxlength="100" title="Specify antiplatelets the patient received." styleId="372560006_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372862008L" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?">
Anticoagulation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(372862008)" styleId="372862008" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" onchange="ruleEnDis3728620088754();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372862008_OTHL" title="Specify anticoagulation the patient received?">
Specify anticoagulation:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(372862008_OTH)" size="100" maxlength="100" title="Specify anticoagulation the patient received?" styleId="372862008_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302497006L" title="Did the patient receive hemodialysis?">
Dialysis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(302497006)" styleId="302497006" title="Did the patient receive hemodialysis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS701L" title="Did the patient receive first intravenous immunoglobulin?">
First IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS701)" styleId="NBS701" title="Did the patient receive first intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS702L" title="Did the patient receive second intravenous immunoglobulin?">
Second IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS702)" styleId="NBS702" title="Did the patient receive second intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Has the patient received a COVID-19 vaccine?">
Has the patient received a COVID-19 vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Has the patient received a COVID-19 vaccine?" onchange="ruleEnDisVAC1268769();enableOrDisableOther('VAC107');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC132_CDL" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC132_CD)" styleId="VAC132_CD" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
<nedss:optionsCollection property="codedValue(VAC_DOSE_NUM_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS791L" title="Date the first vaccine dose was received.">
Vaccine Dose 1 Received Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS791)" maxlength="10" size="10" styleId="NBS791" onkeyup="DateMask(this,null,event)" title="Date the first vaccine dose was received."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS791','NBS791Icon'); return false;" styleId="NBS791Icon" onkeypress="showCalendarEnterKey('NBS791','NBS791Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS792L" title="Date the second vaccine dose was received.">
Vaccine Dose 2 Received Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS792)" maxlength="10" size="10" styleId="NBS792" onkeyup="DateMask(this,null,event)" title="Date the second vaccine dose was received."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS792','NBS792Icon'); return false;" styleId="NBS792Icon" onkeypress="showCalendarEnterKey('NBS792','NBS792Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC107L" title="COVID-19 vaccine manufacturer">
COVID-19 vaccine manufacturer:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC107)" styleId="VAC107" title="COVID-19 vaccine manufacturer" onchange="enableOrDisableOther('VAC107');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(MIS_VAC_MFGR)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="COVID-19 vaccine manufacturer" id="VAC107OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(VAC107Oth)" size="40" maxlength="40" title="Other COVID-19 vaccine manufacturer" styleId="VAC107Oth"/></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
