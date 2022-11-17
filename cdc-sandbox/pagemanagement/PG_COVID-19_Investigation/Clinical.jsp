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
String [] sectionNames  = {"Information Source","Signs &amp; Symptoms","Medical History","Vaccination"};
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
<nedss:container id="NBS_UI_GA25014" name="Information Source Details" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS553L" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.).">
Information Source for Clinical Information (check all that apply):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS553)" styleId="NBS553" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS553-selectedValues');enableOrDisableOther('NBS553')" >
<nedss:optionsCollection property="codedValue(INFO_SOURCE_COVID)" value="key" label="value" /> </html:select>
<div id="NBS553-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." id="NBS553OthL">Other Information Source for Clinical Information (check all that apply):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS553Oth)" size="40" maxlength="40" title="Other Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." styleId="NBS553Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21003" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576L" title="Were symptoms present during course of illness?">
Symptoms present during course of illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV576)" styleId="INV576" title="Were symptoms present during course of illness?" onchange="ruleEnDisINV5768852();ruleEnDisINV5768848();ruleDCompINV1378839();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV137L" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system.">
Date of Symptom Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV137)" maxlength="10" size="10" styleId="INV137" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV137','INV137Icon'); return false;" styleId="INV137Icon" onkeypress="showCalendarEnterKey('INV137','INV137Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV138L" title="The time at which the disease or condition ends.">
Date of Symptom Resolution:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV138)" maxlength="10" size="10" styleId="INV138" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" title="The time at which the disease or condition ends."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV138','INV138Icon'); return false;" styleId="INV138Icon" onkeypress="showCalendarEnterKey('INV138','INV138Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS555L" title="Status of reported symptom(s).">
If symptomatic, symptom status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS555)" styleId="NBS555" title="Status of reported symptom(s).">
<nedss:optionsCollection property="codedValue(SYMPTOM_STATUS_COVID)" value="key" label="value" /></html:select>
</td></tr>

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21016" name="Clinical Findings" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233604007L" title="Did the patient have pneumonia?">
Did the patient develop pneumonia?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233604007)" styleId="233604007" title="Did the patient have pneumonia?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67782005L" title="Did the patient have acute respiratory distress syndrome?">
Did the patient have acute respiratory distress syndrome?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(67782005)" styleId="67782005" title="Did the patient have acute respiratory distress syndrome?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="168734001L" title="Did the patient have an abnormal chest X-ray?">
Did the patient have an abnormal chest X-ray?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(168734001)" styleId="168734001" title="Did the patient have an abnormal chest X-ray?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102594003L" title="Did the patient have an abnormal EKG?">
Did the patient have an abnormal EKG?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(102594003)" styleId="102594003" title="Did the patient have an abnormal EKG?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS546L" title="Did the patient have another diagnosis/etiology for their illness?">
Did the patient have another diagnosis/etiology for their illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS546)" styleId="NBS546" title="Did the patient have another diagnosis/etiology for their illness?" onchange="ruleEnDisNBS5468872();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(81885_6)" size="30" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6_2L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(81885_6_2)" size="30" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6_3L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(81885_6_3)" size="30" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS776L" title="Indication of whether the patient had other clinical findings associated with the illness being reported">
Other Clinical Finding:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS776)" styleId="NBS776" title="Indication of whether the patient had other clinical findings associated with the illness being reported" onchange="ruleEnDisNBS7768873();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS776_OTHL" title="Specify other clinical finding">
Other Clinical Finding Specify:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS776_OTH)" styleId ="NBS776_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify other clinical finding"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35003" name="Clinical Treatments" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS673L" title="Did the patient receive mechanical ventilation (MV)/intubation?">
Did the patient receive mechanical ventilation (MV)/intubation?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS673)" styleId="NBS673" title="Did the patient receive mechanical ventilation (MV)/intubation?" onchange="ruleEnDisNBS6738857();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV547L" title="Total days with mechanical ventilation.">
Total days with Mechanical Ventilation:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV547)" size="3" maxlength="3"  title="Total days with mechanical ventilation." styleId="INV547" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233573008L" title="Did the patient receive ECMO?">
Did the patient receive ECMO?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233573008)" styleId="233573008" title="Did the patient receive ECMO?" onchange="ruleEnDis2335730088874();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS780L" title="Total days with Extracorporeal Membrane Oxygenation">
Total days with ECMO:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS780)" size="3" maxlength="3"  title="Total days with Extracorporeal Membrane Oxygenation" styleId="NBS780" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS781L" title="Indicator for other treatment type specify indicator">
Other Treatment Type?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS781)" styleId="NBS781" title="Indicator for other treatment type specify indicator" onchange="ruleEnDisNBS7818875();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS389L" title="If the treatment type is Other, specify the treatment.">
Other Treatment Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS389)" size="100" maxlength="100" title="If the treatment type is Other, specify the treatment." styleId="NBS389"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS782L" title="Total days with Other Treatment Type">
Total days with Other Treatment Type:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS782)" size="3" maxlength="3"  title="Total days with Other Treatment Type" styleId="NBS782" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21002" name="Symptom Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have fever &gt;100.4F (38C)c?">
Fever &gt;100.4F (38C):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have fever &gt;100.4F (38C)c?" onchange="ruleEnDis3866610068849();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the patients highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the patients highest measured temperature during this illness, in degress Celsius?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV202Unit)" styleId="INV202UNIT">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="103001002L" title="Did the patient have subjective fever (felt feverish)?">
Subjective fever (felt feverish):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(103001002)" styleId="103001002" title="Did the patient have subjective fever (felt feverish)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="28376_2L" title="Did the patient have chills?">
Chills:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(28376_2)" styleId="28376_2" title="Did the patient have chills?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="38880002L" title="Did the patient have rigors?">
Rigors:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(38880002)" styleId="38880002" title="Did the patient have rigors?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have muscle aches (myalgia)?">
Muscle aches (myalgia):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have muscle aches (myalgia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82272006L" title="Did the patient  have runny nose (rhinorrhea)?">
Runny nose (rhinorrhea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82272006)" styleId="82272006" title="Did the patient  have runny nose (rhinorrhea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267102003L" title="Did the patient have a sore throat?">
Sore Throat:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(267102003)" styleId="267102003" title="Did the patient have a sore throat?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44169009L" title="Did the patient experience new olfactory disorder?">
New Olfactory Disorder:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44169009)" styleId="44169009" title="Did the patient experience new olfactory disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="36955009L" title="Did the patient experience new taste disorder?">
New Taste Disorder:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(36955009)" styleId="36955009" title="Did the patient experience new taste disorder?">
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
<span class=" InputFieldLabel" id="419284004L" title="Did the patient have new confusion or change in mental status?">
New confusion or change in mental status?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(419284004)" styleId="419284004" title="Did the patient have new confusion or change in mental status?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271795006L" title="Did the patient have fatigue or malaise?">
Fatigue or malaise:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271795006)" styleId="271795006" title="Did the patient have fatigue or malaise?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS793L" title="Inability to Wake or Stay Awake">
Inability to Wake or Stay Awake:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS793)" styleId="NBS793" title="Inability to Wake or Stay Awake">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA25011L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002L" title="Did the patient have a Cough (new onset or worsening of chronic cough)?">
Cough (new onset or worsening of chronic cough):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49727002)" styleId="49727002" title="Did the patient have a Cough (new onset or worsening of chronic cough)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="56018004L" title="Did the patient experience wheezing?">
Wheezing:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(56018004)" styleId="56018004" title="Did the patient experience wheezing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267036007L" title="Did the patient have shortness of breath (dyspnea)?">
Shortness of Breath (dyspnea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(267036007)" styleId="267036007" title="Did the patient have shortness of breath (dyspnea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="230145002L" title="Did the patient experience difficulty breathing?">
Difficulty Breathing:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(230145002)" styleId="230145002" title="Did the patient experience difficulty breathing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3415004L" title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?">
Pale/gray/blue skin/lips/nail beds?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(3415004)" styleId="3415004" title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29857009L" title="Did the patient experience chest pain?">
Chest Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(29857009)" styleId="29857009" title="Did the patient experience chest pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS794L" title="Persistent Pain or Pressure in Chest">
Persistent Pain or Pressure in Chest:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS794)" styleId="NBS794" title="Persistent Pain or Pressure in Chest">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="16932000L" title="Did the patient have nausea?">
Nausea:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(16932000)" styleId="16932000" title="Did the patient have nausea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="422400008L" title="Did the patient experience vomiting?">
Vomiting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(422400008)" styleId="422400008" title="Did the patient experience vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="21522001L" title="Did the patient have abdominal pain or tenderness?">
Abdominal Pain or Tenderness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(21522001)" styleId="21522001" title="Did the patient have abdominal pain or tenderness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62315008L" title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?">
Diarrhea (=3 loose/looser than normal stools/24hr period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(62315008)" styleId="62315008" title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="79890006L" title="Did patient have loss of appetite?">
Loss of appetite:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(79890006)" styleId="79890006" title="Did patient have loss of appetite?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388850();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTHL" title="Specify other signs and symptoms.">
Other Symptoms:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS338_OTH)" styleId ="NBS338_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify other signs and symptoms."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21004" name="Symptom Notes" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_UI_GA21009" name="Pre-Existing Conditions" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102478008L" title="Does the patient have a history of pre-existing medical conditions?">
Pre-existing medical conditions?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(102478008)" styleId="102478008" title="Does the patient have a history of pre-existing medical conditions?" onchange="ruleEnDis1024780088879();ruleEnDis1024780088851();ruleEnDisNBS6628855();ruleEnDisNBS6778868();ruleEnDis1103590098854();ruleEnDis1103590098854();ruleEnDis747320098867();ruleEnDis747320098867();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21008" name="Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="73211009L" title="Does the patient have diabetes mellitus?">
Diabetes Mellitus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(73211009)" styleId="73211009" title="Does the patient have diabetes mellitus?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB017L" title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?">
Hypertension:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB017)" styleId="ARB017" title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414916001L" title="Is the patient severely obese (BMI &gt;= 40)?">
Severe Obesity (BMI &gt;=40):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(414916001)" styleId="414916001" title="Is the patient severely obese (BMI &gt;= 40)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="128487001L" title="Does the patient have a history of cardiovascular disease?">
Cardiovascular disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(128487001)" styleId="128487001" title="Does the patient have a history of cardiovascular disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="709044004L" title="Does the patient have a history of chronic renal disease?">
Chronic Renal disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(709044004)" styleId="709044004" title="Does the patient have a history of chronic renal disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="328383001L" title="Does the patient have a history of chronic liver disease?">
Chronic Liver disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(328383001)" styleId="328383001" title="Does the patient have a history of chronic liver disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413839001L" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
Chronic Lung Disease (asthma/emphysema/COPD):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(413839001)" styleId="413839001" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS662L" title="Did the patient have any history of other chronic disease?">
Other Chronic Diseases:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS662)" styleId="NBS662" title="Did the patient have any history of other chronic disease?" onchange="ruleEnDisNBS6628855();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS662_OTHL" title="Specify the other chronic disease(s).">
Specify Other Chronic Diseases:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS662_OTH)" size="100" maxlength="100" title="Specify the other chronic disease(s)." styleId="NBS662_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS677L" title="Did the patient have any other underlying conditions or risk behaviors?">
Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS677)" styleId="NBS677" title="Did the patient have any other underlying conditions or risk behaviors?" onchange="ruleEnDisNBS6778868();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS677_OTHL" title="If patient had an other underlying condition or risk behavior, specify the condition or behavior.">
Specify Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS677_OTH)" size="50" maxlength="50" title="If patient had an other underlying condition or risk behavior, specify the condition or behavior." styleId="NBS677_OTH"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA25012L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="370388006L" title="Does the patient have a history of an immunocompromised/immunosuppressive condition?">
Immunosuppressive Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(370388006)" styleId="370388006" title="Does the patient have a history of an immunocompromised/immunosuppressive condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="85828009L" title="Did the patient have an autoimmune disease or condition?">
Autoimmune Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85828009)" styleId="85828009" title="Did the patient have an autoimmune disease or condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="65568007L" title="Is the patient a current smoker?">
Current smoker:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(65568007)" styleId="65568007" title="Is the patient a current smoker?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="8517006L" title="Is the patient a former smoker?">
Former smoker:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(8517006)" styleId="8517006" title="Is the patient a former smoker?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS676L" title="Did the patient engage in substance abuse or misuse?">
Substance Abuse or Misuse:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS676)" styleId="NBS676" title="Did the patient engage in substance abuse or misuse?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="110359009L" title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?">
Disability:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(110359009)" styleId="110359009" title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?" onchange="ruleEnDis1103590098854();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS671)" size="100" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671_2L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS671_2)" size="100" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671_3L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS671_3)" size="100" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="74732009L" title="Did the patient have a psychological or psychiatric condition?">
Psychological or Psychiatric Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(74732009)" styleId="74732009" title="Did the patient have a psychological or psychiatric condition?" onchange="ruleEnDis747320098867();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS691)" size="50" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691_2L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS691_2)" size="50" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691_3L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS691_3)" size="50" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1788876();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV579L" title="If the subject in pregnant, please enter the due date.">
Due Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV579)" maxlength="10" size="10" styleId="INV579" onkeyup="DateMaskFuture(this,null,event)" title="If the subject in pregnant, please enter the due date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('INV579','INV579Icon'); return false;" styleId="INV579Icon" onkeypress ="showCalendarFutureEnterKey('INV579','INV579Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR159L" title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks).">
Number of Weeks Gestation at Onset of Illness:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAR159)" size="20" maxlength="20"  title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks)." styleId="VAR159" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAR160L" title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease.">
Trimester at Onset of Illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAR160)" styleId="VAR160" title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease.">
<nedss:optionsCollection property="codedValue(PHVS_PREG_TRIMESTER)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35004" name="Vaccination Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Did subject ever receive a COVID-containing vaccine?">
Did the patient ever received a COVID-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Did subject ever receive a COVID-containing vaccine?" onchange="ruleEnDisVAC1268877();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140L" title="Number of vaccine doses against this disease prior to illness onset">
Vaccination Doses Prior to Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC140)" size="2" maxlength="2"  title="Number of vaccine doses against this disease prior to illness onset" styleId="VAC140" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC142L" title="Date of last vaccine dose against this disease prior to illness onset">
Date of Last Dose Prior to Illness Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC142)" maxlength="10" size="10" styleId="VAC142" onkeyup="DateMask(this,null,event)" title="Date of last vaccine dose against this disease prior to illness onset"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC142','VAC142Icon'); return false;" styleId="VAC142Icon" onkeypress="showCalendarEnterKey('VAC142','VAC142Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC148L" title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?">
Vaccinated per ACIP Recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC148)" styleId="VAC148" title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?" onchange="ruleEnDisVAC1488878();enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149L" title="Reason subject not vaccinated as recommended by ACIP">
Reason Not Vaccinated Per ACIP Recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC149)" styleId="VAC149" title="Reason subject not vaccinated as recommended by ACIP" onchange="enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(VACCINE_NOT_GIVEN_REASON_COVID19)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Reason subject not vaccinated as recommended by ACIP" id="VAC149OthL">Other Reason Not Vaccinated Per ACIP Recommendations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(VAC149Oth)" size="40" maxlength="40" title="Other Reason subject not vaccinated as recommended by ACIP" styleId="VAC149Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC133L" title="Comments about the subjects vaccination history">
Vaccine History Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(VAC133)" styleId ="VAC133" onkeyup="checkTextAreaLength(this, 200)" title="Comments about the subjects vaccination history"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
