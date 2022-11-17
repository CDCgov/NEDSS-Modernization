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
String tabId = "editHepatitisExtended";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact with Case","Sexual and Drug Exposures","Exposures Prior to Onset","Hepatitis Treatment","Vaccination History"};
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
<nedss:container id="NBS_INV_HEPACBC_UI_5" name="Contact with a Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_6L">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV602L" title="During the time period prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis virus infection? For hepatitis B, the time period is 6 weeks to 6 months. For hepatitis C, it is 2 weeks to 6 months.">
During the time period prior to onset, was patient a contact of a case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV602)" styleId="INV602" title="During the time period prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis virus infection? For hepatitis B, the time period is 6 weeks to 6 months. For hepatitis C, it is 2 weeks to 6 months." onchange="ruleHideUnhINV6027468();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_7" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_5L" title="If the patient was a contact of a confirmed or suspected case, was the contact type sexual?">
Sexual (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_5)" styleId="INV603_5" title="If the patient was a contact of a confirmed or suspected case, was the contact type sexual?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_3L" title="If the patient is a contact of a confirmed or suspected case, was the contact type  household non-sexual)?">
Household (Non-Sexual) (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_3)" styleId="INV603_3" title="If the patient is a contact of a confirmed or suspected case, was the contact type  household non-sexual)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_6L" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?">
Other (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_6)" styleId="INV603_6" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" onchange="ruleHideUnhINV603_67469();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV897L" title="If contact type with a confirmed or suspected case was 'other', specify other contact type.">
Other Contact Type (Specify):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV897)" size="100" maxlength="100" title="If contact type with a confirmed or suspected case was 'other', specify other contact type." styleId="INV897"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_9" name="Sexual Exposures in Prior 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV592L" title="Please enter the sexual preference of the patient.">
What is the sexual preference of the patient?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV592)" styleId="INV592" title="Please enter the sexual preference of the patient.">
<nedss:optionsCollection property="codedValue(PHVS_SEXUALPREFERENCE_NETSS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_25L">&nbsp;&nbsp;Note: If 0 is selected on the form, enter 0; if 1 is selected on the form, enter 1; if 2-5 is selected on the form, enter 2; if &gt;5 is selected on the form, enter 6.</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_10L">&nbsp;&nbsp;In the 6 months before symptom onset, how many:</span> </td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV605L" title="During the 6 months prior to the onset of symptoms, number of male sex partners the person had.">
Male Sex Partners Did the Patient Have:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV605)" size="4" maxlength="4"  title="During the 6 months prior to the onset of symptoms, number of male sex partners the person had." styleId="INV605" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV606L" title="During the 6 months prior to the onset of symptoms, number of female sex partners the person had.">
Female Sex Partners Did the Patient Have:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV606)" size="4" maxlength="4"  title="During the 6 months prior to the onset of symptoms, number of female sex partners the person had." styleId="INV606" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV653bL" title="Was the patient ever treated for a sexually transmitted disease?">
Was the patient ever treated for a sexually transmitted disease?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV653b)" styleId="INV653b" title="Was the patient ever treated for a sexually transmitted disease?" onchange="ruleHideUnhINV653b7470();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV654L" title="If the subject was ever treated for a sexually-transmitted diases, in what year was the most recent treatment?">
If yes, in what year was the most recent treatment?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV654)" size="10" maxlength="10"  title="If the subject was ever treated for a sexually-transmitted diases, in what year was the most recent treatment?" styleId="INV654" onkeyup="YearMask(this, event)" onblur="pgCheckFullYear(this)"/>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_12" name="Blood Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_13L">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_14L">&nbsp;&nbsp;During the time period prior to onset, did the patient:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV583L" title="Prior to the onset of symptoms, did the patient undergo hemodialysis? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Undergo Hemodialysis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV583)" styleId="INV583" title="Prior to the onset of symptoms, did the patient undergo hemodialysis? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV655L" title="Prior to the onset of symptoms, did the patient have an accidental stick or puncture with a needle or other object contaminated with blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Have an Accidental Stick or Puncture With a Needle or Other Object Contaminated With Blood:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV655)" styleId="INV655" title="Prior to the onset of symptoms, did the patient have an accidental stick or puncture with a needle or other object contaminated with blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV580L" title="Prior to the onset of symptoms, did the patient receive blood or blood products (transfusion)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Receive Blood or Blood Products (Transfusion):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV580)" styleId="INV580" title="Prior to the onset of symptoms, did the patient receive blood or blood products (transfusion)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV5807471();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV614L" title="Date the subject began receiving blood or blood products (transfusion) prior to symptom onset. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
If Yes, Date of Transfusion:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV614)" maxlength="10" size="10" styleId="INV614" onkeyup="DateMask(this,null,event)" title="Date the subject began receiving blood or blood products (transfusion) prior to symptom onset. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV614','INV614Icon'); return false;" styleId="INV614Icon" onkeypress="showCalendarEnterKey('INV614','INV614Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV620L" title="Prior to the onset of symptoms, did the patient receive any IV infusions and/or injections in an outpatient setting? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Receive Any IV Infusions and/or Injections in the Outpatient Setting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV620)" styleId="INV620" title="Prior to the onset of symptoms, did the patient receive any IV infusions and/or injections in an outpatient setting? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV617L" title="Prior to the onset of symptoms, did the patient have other exposure to someone else's blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Have Other Exposure to Someone Else's Blood:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV617)" styleId="INV617" title="Prior to the onset of symptoms, did the patient have other exposure to someone else's blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV6177472();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV898L" title="If patient had exposure to someone else's blood prior to symptom onset, specify the other blood exposure.">
Other Blood Exposure (Specify):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV898)" styleId ="INV898" onkeyup="checkTextAreaLength(this, 200)" title="If patient had exposure to someone else's blood prior to symptom onset, specify the other blood exposure."/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_15L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV590L" title="Prior to the onset of symptoms, was the patient employed in a medical or dental field involving direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Was the patient employed in a medical or dental field involving contact with human blood?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV590)" styleId="INV590" title="Prior to the onset of symptoms, was the patient employed in a medical or dental field involving direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV5907473();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV594L" title="Subject's frequency of blood contact as an employee in a medical or dental field involving direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months">
If Yes, Frequency of Direct Blood Contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV594)" styleId="INV594" title="Subject's frequency of blood contact as an employee in a medical or dental field involving direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months">
<nedss:optionsCollection property="codedValue(PHVS_BLOODCONTACTFREQUENCY_HEPATITIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV595L" title="Prior to the onset of symptoms, was the subject employed as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months.  For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Was the patient employed as a public safety worker having direct contact with human blood?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV595)" styleId="INV595" title="Prior to the onset of symptoms, was the subject employed as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months.  For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV5957474();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV596L" title="Subject's frequency of blood contact as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
If Yes, Frequency of Direct Blood Contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV596)" styleId="INV596" title="Subject's frequency of blood contact as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(PHVS_BLOODCONTACTFREQUENCY_HEPATITIS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_16" name="Tattooing/Drugs/Piercing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPACBC_UI_17L">&nbsp;&nbsp;In the time period prior to onset:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV597L" title="Prior to the onset of symptoms, did the patient receive a tattoo? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Did the patient receive a tattoo?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV597)" styleId="INV597" title="Prior to the onset of symptoms, did the patient receive a tattoo? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV5977475();ruleHideUnhINV5987476();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV598L" title="Where was the tattooing performed (check all that apply)">
Where was the tattooing performed (check all that apply)?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV598)" styleId="INV598" title="Where was the tattooing performed (check all that apply)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV598-selectedValues');ruleHideUnhINV5987476()" >
<nedss:optionsCollection property="codedValue(PHVS_TATTOOOBTAINEDFROM_HEPATITIS)" value="key" label="value" /> </html:select>
<div id="INV598-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV900L" title="specify other location(s) where the patient received a tattoo">
Other Location(s) Tattoo Received:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV900)" styleId ="INV900" onkeyup="checkTextAreaLength(this, 150)" title="specify other location(s) where the patient received a tattoo"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV607L" title="During the timeperiod prior to the onset of symptoms, did the subject inject drugs?">
Inject Drugs Not Prescribed By a Doctor:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV607)" styleId="INV607" title="During the timeperiod prior to the onset of symptoms, did the subject inject drugs?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV608L" title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?">
Use Street Drugs But Not Inject:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV608)" styleId="INV608" title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV622L" title="Did the patient have any part of their body pierced (other than ear)?">
Did the patient have any part of their body pierced (other than ear)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV622)" styleId="INV622" title="Did the patient have any part of their body pierced (other than ear)?" onchange="ruleHideUnhINV6227477();ruleHideUnhINV6237478();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV623L" title="Where was the piercing performed (check all that apply)">
Where was the piercing performed (check all that apply)?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV623)" styleId="INV623" title="Where was the piercing performed (check all that apply)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV623-selectedValues');ruleHideUnhINV6237478()" >
<nedss:optionsCollection property="codedValue(PHVS_TATTOOOBTAINEDFROM_HEPATITIS)" value="key" label="value" /> </html:select>
<div id="INV623-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV899L" title="Specify other location(s) where the patient received a piercing">
Other Location(s) Piercing Received:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV899)" styleId ="INV899" onkeyup="checkTextAreaLength(this, 150)" title="Specify other location(s) where the patient received a piercing"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_18" name="Other Healthcare Exposure" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV633L" title="Prior to the onset of symptoms, did the patient have dental work or oral surgery? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Did the patient have dental work or oral surgery?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV633)" styleId="INV633" title="Prior to the onset of symptoms, did the patient have dental work or oral surgery? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV634L" title="Prior to the onset of symptoms, did the patient have surgery (other than oral surgery)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Did the patient have surgery (other than oral surgery)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV634)" styleId="INV634" title="Prior to the onset of symptoms, did the patient have surgery (other than oral surgery)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV635L" title="Prior to the onset of symptoms, was the patient hospitalized? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Was the patient hospitalized?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV635)" styleId="INV635" title="Prior to the onset of symptoms, was the patient hospitalized? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV636L" title="Prior to the onset of symptoms, was the patient a resident of a long-term care facility? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Was the patient a resident of a long-term care facility?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV636)" styleId="INV636" title="Prior to the onset of symptoms, was the patient a resident of a long-term care facility? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV637L" title="Prior to the onset of symptoms, was the patient incarcerated for longer than 24 hours? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months.">
Was the patient incarcerated for longer than 24 hours?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV637)" styleId="INV637" title="Prior to the onset of symptoms, was the patient incarcerated for longer than 24 hours? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." onchange="ruleHideUnhINV6377480();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_19" name="Incarceration Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV638_3L" title="If patient was incarcerated, indicate if the incarceration type was prison.">
Prison:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV638_3)" styleId="INV638_3" title="If patient was incarcerated, indicate if the incarceration type was prison.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV638_1L" title="If patient was incarcerated, what type of facility?">
Jail:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV638_1)" styleId="INV638_1" title="If patient was incarcerated, what type of facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV638_2L" title="If patient was incarcerated, indicate the type of facility?">
Juvenile Facility:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV638_2)" styleId="INV638_2" title="If patient was incarcerated, indicate the type of facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_20" name="Incarceration More than 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV639L" title="During his/her lifetime, was the patient EVER incarcerated for more than 6 months?">
Was the patient ever incarcerated for longer than 6 months?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV639)" styleId="INV639" title="During his/her lifetime, was the patient EVER incarcerated for more than 6 months?" onchange="ruleHideUnhINV6397479();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV640L" title="Enter the 4-digit year the patient was most recently incarcerated for longer than 6 months.">
If yes, what year was the most recent incarceration?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV640)" size="10" maxlength="10"  title="Enter the 4-digit year the patient was most recently incarcerated for longer than 6 months." styleId="INV640" onkeyup="YearMask(this, event)" onblur="pgCheckFullYear(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV641L" title="If the patient was incarcerated for longer than 6 months, enter the length of time of incarceration in months">
If yes, for how long (answer in months)?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV641)" size="3" maxlength="3"  title="If the patient was incarcerated for longer than 6 months, enter the length of time of incarceration in months" styleId="INV641" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_22" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV652L" title="Has the subject ever received medication for the type of Hepatitis being reported?">
Has the patient received medication for the type of hepatitis being reported?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV652)" styleId="INV652" title="Has the subject ever received medication for the type of Hepatitis being reported?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_24" name="Hepatitis B Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Did the patient ever receive hepatitis B vaccine?">
Did the patient ever receive hepatitis B vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Did the patient ever receive hepatitis B vaccine?" onchange="ruleEnDisVAC1267486();ruleHideUnhVAC1267482();ruleHideUnhHEP1907483();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC132)" size="2" maxlength="2"  title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine)." styleId="VAC132" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC142bL" title="Enter the 4 digit year of when the last dose of vaccine for the condition being investigated was received.">
In what year was the last dose received?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC142b)" size="10" maxlength="10"  title="Enter the 4 digit year of when the last dose of vaccine for the condition being investigated was received." styleId="VAC142b" onkeyup="YearMask(this, event)" onblur="pgCheckFullYear(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="HEP190L" title="Was the patient tested for antibody to HBsAg (anti-HBS) within 1-2 months after the last does of vaccine?">
Was patient tested for antibody to HBsAg (anti-HBs) within 1-2 mos after last dose?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(HEP190)" styleId="HEP190" title="Was the patient tested for antibody to HBsAg (anti-HBS) within 1-2 months after the last does of vaccine?" onchange="ruleHideUnhHEP1907483();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="HEP191L" title="Was the serum anti-HBs &gt;= 10ml U/ml?  (Answer 'Yes' if lab result reported as positive or reactive.)">
Was the serum anti-HBs &gt;= 10ml U/ml?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(HEP191)" styleId="HEP191" title="Was the serum anti-HBs &gt;= 10ml U/ml?  (Answer 'Yes' if lab result reported as positive or reactive.)">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
