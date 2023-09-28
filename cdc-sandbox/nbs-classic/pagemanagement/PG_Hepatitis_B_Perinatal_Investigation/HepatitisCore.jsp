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
String tabId = "editHepatitisCore";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Data","Diagnostic Tests"};
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
<nedss:container id="NBS_INV_HEP_UI_5" name="Reason for Testing" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV575L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV575)" styleId="INV575" title="Listing of the reason(s) the subject was tested for the condition"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV575-selectedValues');ruleHideUnhINV5757421()" >
<nedss:optionsCollection property="codedValue(PHVS_REASONFORTEST_HEPATITIS)" value="key" label="value" /> </html:select>
<div id="INV575-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV901L" title="Other reason(s) the patient was tested for hepatitis.">
Other Reason for Testing:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV901)" size="150" maxlength="150" title="Other reason(s) the patient was tested for hepatitis." styleId="INV901"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Clinical Data" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136L" title="Date of diagnosis of condition being reported to public health system.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV136)" maxlength="10" size="10" styleId="INV136" onkeyup="DateMask(this,null,event)" title="Date of diagnosis of condition being reported to public health system."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV136','INV136Icon'); return false;" styleId="INV136Icon" onkeypress="showCalendarEnterKey('INV136','INV136Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576L" title="Was the patient symptomatic for the illness of interest?">
Is patient symptomatic?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV576)" styleId="INV576" title="Was the patient symptomatic for the illness of interest?" onchange="ruleEnDisINV5767419();ruleDCompINV1377414();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

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
<span class=" InputFieldLabel" id="INV578L" title="At the time of diagnosis, was the patient jaundiced?">
Was the patient jaundiced?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV578)" styleId="INV578" title="At the time of diagnosis, was the patient jaundiced?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1287407();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1327413();pgSelectNextFocus(this);">
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
Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV132)" maxlength="10" size="10" styleId="INV132" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" title="Subject's admission date to the hospital for the condition covered by the investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV132','INV132Icon'); return false;" styleId="INV132Icon" onkeypress="showCalendarEnterKey('INV132','INV132Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Discharge Date:</span>
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
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1787418();pgSelectNextFocus(this);">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1457408();pgSelectNextFocus(this);">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV650L" title="Was the patient aware s/he had hepatitis prior to lab testing?">
Was the patient aware s/he had hepatitis prior to lab testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV650)" styleId="INV650" title="Was the patient aware s/he had hepatitis prior to lab testing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV651L" title="Does the subject have a provider of care for the condition?  This is any healthcare provider that monitors or treats the patient for the condition.">
Does the patient have a provider of care for hepatitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV651)" styleId="INV651" title="Does the subject have a provider of care for the condition?  This is any healthcare provider that monitors or treats the patient for the condition.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182L" title="The physician associated with this case.">
Physician:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV182Uid">
<span id="clearINV182" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
<span id="clearINV182">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV182CodeClearButton" onclick="clearProvider('INV182')"/>
</span>
<span id="INV182SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV182Icon" onclick="getProvider('INV182');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV182)" styleId="INV182Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV182Text','INV182_qec_list')"
title="The physician associated with this case."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV182CodeLookupButton" onclick="getDWRProvider('INV182')"
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV182_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="INV182S">Physician Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV182Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV182Uid"/>
<span id="INV182">${PageForm.attributeMap.INV182SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV182Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV887L" title="Indicate whether the patient has diabetes">
Does the patient have diabetes?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV887)" styleId="INV887" title="Indicate whether the patient has diabetes" onchange="ruleEnDisINV8877420();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV842L" title="If subject has diabetes, date of diabetes diagnosis.">
Diabetes Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV842)" maxlength="10" size="10" styleId="INV842" onkeyup="DateMask(this,null,event)" title="If subject has diabetes, date of diabetes diagnosis."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV842','INV842Icon'); return false;" styleId="INV842Icon" onkeypress="showCalendarEnterKey('INV842','INV842Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_6" name="Liver Enzyme Levels at Time of Diagnosis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="1742_6L" title="Enter the ALT/SGPT result">
ALT [SGPT] Result:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(1742_6)" size="4" maxlength="4"  title="Enter the ALT/SGPT result" styleId="1742_6" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV826L" title="Enter the date of specimen collection of the ALT liver enzyme lab test result.">
Specimen Collection Date (ALT):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV826)" maxlength="10" size="10" styleId="INV826" onkeyup="DateMask(this,null,event)" title="Enter the date of specimen collection of the ALT liver enzyme lab test result."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV826','INV826Icon'); return false;" styleId="INV826Icon" onkeypress="showCalendarEnterKey('INV826','INV826Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV827L" title="Enter the upper limit normal value (numeric) for the ALT liver enzyme test result.">
Test Result Upper Limit Normal (ALT):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV827)" size="4" maxlength="4"  title="Enter the upper limit normal value (numeric) for the ALT liver enzyme test result." styleId="INV827" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPBP_UI_9L"><hr/></span> </td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="11920_8L" title="Enter patient's AST/SGOT result">
AST [SGOT] Result:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(11920_8)" size="4" maxlength="4"  title="Enter patient's AST/SGOT result" styleId="11920_8" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV826bL" title="Enter the date of specimen collection of the AST liver enzyme lab test result.">
Specimen Collection Date (AST):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV826b)" maxlength="10" size="10" styleId="INV826b" onkeyup="DateMask(this,null,event)" title="Enter the date of specimen collection of the AST liver enzyme lab test result."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV826b','INV826bIcon'); return false;" styleId="INV826bIcon" onkeypress="showCalendarEnterKey('INV826b','INV826bIcon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV827bL" title="Enter the upper limit normal value (numeric) for the AST liver enzyme test result.">
Test Result Upper Limit Normal (AST):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV827b)" size="4" maxlength="4"  title="Enter the upper limit normal value (numeric) for the AST liver enzyme test result." styleId="INV827b" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_8" name="Diagnostic Test Results" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38316_3_DTL" title="Date of specimen collection for total anti-HAV test result">
Specimen Collection Date (anti-HAV):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38316_3_DT)" maxlength="10" size="10" styleId="LP38316_3_DT" onkeyup="DateMask(this,null,event)" title="Date of specimen collection for total anti-HAV test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38316_3_DT','LP38316_3_DTIcon'); return false;" styleId="LP38316_3_DTIcon" onkeypress="showCalendarEnterKey('LP38316_3_DT','LP38316_3_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38316_3L" title="Total antibody to Hepatitis A virus result">
total anti-HAV Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38316_3)" styleId="LP38316_3" title="Total antibody to Hepatitis A virus result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38318_9_DTL" title="Specimen collection date of IgM anti-HAV test result">
Specimen Collection Date (IgM anti-HAV):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38318_9_DT)" maxlength="10" size="10" styleId="LP38318_9_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of IgM anti-HAV test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38318_9_DT','LP38318_9_DTIcon'); return false;" styleId="LP38318_9_DTIcon" onkeypress="showCalendarEnterKey('LP38318_9_DT','LP38318_9_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38318_9L" title="IgM antibody to Hepatitis A virus result">
IgM anti-HAV Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38318_9)" styleId="LP38318_9" title="IgM antibody to Hepatitis A virus result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPBP_UI_11L"><hr/></span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38331_2_DTL" title="Specimen Collection Date for HBsAg test">
Specimen Collection Date (HBsAg):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38331_2_DT)" maxlength="10" size="10" styleId="LP38331_2_DT" onkeyup="DateMask(this,null,event)" title="Specimen Collection Date for HBsAg test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38331_2_DT','LP38331_2_DTIcon'); return false;" styleId="LP38331_2_DTIcon" onkeypress="showCalendarEnterKey('LP38331_2_DT','LP38331_2_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38331_2L" title="Hepatitis B virus surface antigen result">
HBsAg Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38331_2)" styleId="LP38331_2" title="Hepatitis B virus surface antigen result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38323_9_DTL" title="Specimen collection date of total anti-HBc test result">
Specimen Collection Date (total anti-HBc):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38323_9_DT)" maxlength="10" size="10" styleId="LP38323_9_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of total anti-HBc test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38323_9_DT','LP38323_9_DTIcon'); return false;" styleId="LP38323_9_DTIcon" onkeypress="showCalendarEnterKey('LP38323_9_DT','LP38323_9_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38323_9L" title="total antibody to Hepatitis B core antigen result">
total anti-HBc Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38323_9)" styleId="LP38323_9" title="total antibody to Hepatitis B core antigen result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38325_4_DTL" title="Specimen collection date of IgM anti-HBc test result">
Specimen Collection Date (IgM anti-HBc):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38325_4_DT)" maxlength="10" size="10" styleId="LP38325_4_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of IgM anti-HBc test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38325_4_DT','LP38325_4_DTIcon'); return false;" styleId="LP38325_4_DTIcon" onkeypress="showCalendarEnterKey('LP38325_4_DT','LP38325_4_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38325_4L" title="IgM antibody to hepatitis B core antigen result">
IgM anti-HBc Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38325_4)" styleId="LP38325_4" title="IgM antibody to hepatitis B core antigen result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38320_5_DTL" title="Specimen collection date of HBV DNA/NAT test">
Specimen Collection Date (HEP B DNA/NAT):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38320_5_DT)" maxlength="10" size="10" styleId="LP38320_5_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of HBV DNA/NAT test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38320_5_DT','LP38320_5_DTIcon'); return false;" styleId="LP38320_5_DTIcon" onkeypress="showCalendarEnterKey('LP38320_5_DT','LP38320_5_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38320_5L" title="Hepatitis B DNA-containing test (Nucleic Acid Test (NAT)) result">
HEP B DNA/(NAT) Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38320_5)" styleId="LP38320_5" title="Hepatitis B DNA-containing test (Nucleic Acid Test (NAT)) result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38329_6_DTL" title="Specimen collection date of HBeAg test">
Specimen Collection Date (HBeAg):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38329_6_DT)" maxlength="10" size="10" styleId="LP38329_6_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of HBeAg test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38329_6_DT','LP38329_6_DTIcon'); return false;" styleId="LP38329_6_DTIcon" onkeypress="showCalendarEnterKey('LP38329_6_DT','LP38329_6_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38329_6L" title="Hepatitis B virus e Antigen result">
HBeAg Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38329_6)" styleId="LP38329_6" title="Hepatitis B virus e Antigen result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPBP_UI_12L"><hr/></span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38332_0_DTL" title="Specimen collection date of total anti-HCV">
Specimen Collection Date (total anti-HCV):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38332_0_DT)" maxlength="10" size="10" styleId="LP38332_0_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date of total anti-HCV"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38332_0_DT','LP38332_0_DTIcon'); return false;" styleId="LP38332_0_DTIcon" onkeypress="showCalendarEnterKey('LP38332_0_DT','LP38332_0_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38332_0L" title="Hepatitis C virus Ab result">
total anti-HCV Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38332_0)" styleId="LP38332_0" title="Hepatitis C virus Ab result" onchange="ruleHideUnhLP38332_07423();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV841L" title="If the antibody test to Hepatitis C Virus (anti-HCV) was positive or negative, enter the signal to cut-off ratio.">
anti-HCV signal to cut-off ratio:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV841)" size="25" maxlength="25" title="If the antibody test to Hepatitis C Virus (anti-HCV) was positive or negative, enter the signal to cut-off ratio." styleId="INV841"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="5199_5_DTL" title="Specimen collection date for supplemental anti-HCV assay">
Specimen Collection Date (supplemental anti-HCV assay):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(5199_5_DT)" maxlength="10" size="10" styleId="5199_5_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date for supplemental anti-HCV assay"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('5199_5_DT','5199_5_DTIcon'); return false;" styleId="5199_5_DTIcon" onkeypress="showCalendarEnterKey('5199_5_DT','5199_5_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="5199_5L" title="supplemental anti-HCV assay (e.g. RIBA) result">
Supplemental anti-HCV Assay Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(5199_5)" styleId="5199_5" title="supplemental anti-HCV assay (e.g. RIBA) result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38335_3_DTL" title="Specimen collection date for HCV RNA test">
Specimen Collection Date (HCV RNA):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38335_3_DT)" maxlength="10" size="10" styleId="LP38335_3_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date for HCV RNA test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38335_3_DT','LP38335_3_DTIcon'); return false;" styleId="LP38335_3_DTIcon" onkeypress="showCalendarEnterKey('LP38335_3_DT','LP38335_3_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38335_3L" title="Hepatitis C virus RNA result">
HCV RNA Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38335_3)" styleId="LP38335_3" title="Hepatitis C virus RNA result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPBP_UI_10L"><hr/></span> </td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38345_2_DTL" title="Specimen collection date for total anti-HDV test">
Specimen Collection Date (total anti-HDV):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38345_2_DT)" maxlength="10" size="10" styleId="LP38345_2_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date for total anti-HDV test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38345_2_DT','LP38345_2_DTIcon'); return false;" styleId="LP38345_2_DTIcon" onkeypress="showCalendarEnterKey('LP38345_2_DT','LP38345_2_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38345_2L" title="Total Hepatitis D virus Ab result">
anti-HDV Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38345_2)" styleId="LP38345_2" title="Total Hepatitis D virus Ab result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP38350_2_DTL" title="Specimen collection date for total anti-HEV test">
Specimen Collection Date (total anti-HEV):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP38350_2_DT)" maxlength="10" size="10" styleId="LP38350_2_DT" onkeyup="DateMask(this,null,event)" title="Specimen collection date for total anti-HEV test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP38350_2_DT','LP38350_2_DTIcon'); return false;" styleId="LP38350_2_DTIcon" onkeypress="showCalendarEnterKey('LP38350_2_DT','LP38350_2_DTIcon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP38350_2L" title="Total Hepatitis E virus Ab result">
anti-HEV Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP38350_2)" styleId="LP38350_2" title="Total Hepatitis E virus Ab result">
<nedss:optionsCollection property="codedValue(PNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
