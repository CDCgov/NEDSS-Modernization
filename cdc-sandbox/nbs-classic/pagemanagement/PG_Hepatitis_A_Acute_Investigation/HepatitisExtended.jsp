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
String [] sectionNames  = {"Hepatitis A","Vaccination History"};
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
<nedss:container id="NBS_INV_HEPA_UI_3" name="Epidemiologic Link" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV217L" title="If this case has a diagnosis of Hepatitis A that has not been serologically confirmed, is there an epidemiologic link between this pataient and a laboratory-confirmed heptatitis A case?">
Is there an epidemiologic link between this patient and a laboratory-confirmed case of Hepatitis A?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV217)" styleId="INV217" title="If this case has a diagnosis of Hepatitis A that has not been serologically confirmed, is there an epidemiologic link between this pataient and a laboratory-confirmed heptatitis A case?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV602L" title="During the 2-6 weeks prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis A virus infection?">
During the 2-6 weeks prior to onset, was patient a contact of a confirmed or suspected case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV602)" styleId="INV602" title="During the 2-6 weeks prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis A virus infection?" onchange="ruleHideUnhINV6027504();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_4" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPA_UI_5L">&nbsp;&nbsp;If yes, was the contact:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_3L" title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?">
Household Member (Non-Sexual) (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_3)" styleId="INV603_3" title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_5L" title="If the patient was a contact of a confirmed or suspected case, was the contact a sex partner?">
Sex Partner (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_5)" styleId="INV603_5" title="If the patient was a contact of a confirmed or suspected case, was the contact a sex partner?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_2L" title="If the patient was a contact of a confirmed or suspected case, was the contact a child cared for by this patient?">
Child Cared For By This Patient (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_2)" styleId="INV603_2" title="If the patient was a contact of a confirmed or suspected case, was the contact a child cared for by this patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_1L" title="If the patient was a contact of a confirmed or suspected case, was the contact a babysitter of this patient?">
Babysitter of This Patient (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_1)" styleId="INV603_1" title="If the patient was a contact of a confirmed or suspected case, was the contact a babysitter of this patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_4L" title="If the patient was a contact of a confirmed or suspected case, was the contact a playmate?">
Playmate (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_4)" styleId="INV603_4" title="If the patient was a contact of a confirmed or suspected case, was the contact a playmate?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_6L" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?">
Other (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_6)" styleId="INV603_6" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" onchange="ruleHideUnhINV603_67509();pgSelectNextFocus(this);">
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_6" name="Daycare Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPA_UI_7L">&nbsp;&nbsp;Was the patient:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV615L" title="During the 2-6 weeks prior to the onset of symptoms, was the patient a child or employee in daycare center, nursery, or preschool?">
A Child or Employee in a Day Care Center/Nursery/Preschool:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV615)" styleId="INV615" title="During the 2-6 weeks prior to the onset of symptoms, was the patient a child or employee in daycare center, nursery, or preschool?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV616L" title="During the 2-6 weeks prior to the onset of symptoms, was the patient a household contact of a child or employee in a daycare center, nursery, or preschool?">
A Household Contact of a Child or Employee in a Day Care/Nursery/Preschool:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV616)" styleId="INV616" title="During the 2-6 weeks prior to the onset of symptoms, was the patient a household contact of a child or employee in a daycare center, nursery, or preschool?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV896L" title="If the patient was a child or employee or  a household contact of a child or employee in a child care facility, was there an identified hepatitis case in the childcare facility?">
If yes for either of these, was there an identified hepatitis case in the child care facility?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV896)" styleId="INV896" title="If the patient was a child or employee or  a household contact of a child or employee in a child care facility, was there an identified hepatitis case in the childcare facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_8" name="Sexual Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

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
<tr><td colspan="2" align="left"><span id="NBS_INV_HEPA_UI_9L">&nbsp;&nbsp;Note: If 0 is selected on the form, enter 0; if 1 is selected on the form, enter 1; if 2-5 is selected on the form, enter 2; if &gt;5 is selected on the form, enter 6.</span> </td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV605L" title="During the time period prior to the onset of symptoms, number of male sex partners the person had.">
How many male sex partners did the patient have?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV605)" size="4" maxlength="4"  title="During the time period prior to the onset of symptoms, number of male sex partners the person had." styleId="INV605" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV606L" title="During the time period prior to the onset of symptoms, number of female sex partners the person had.">
How many female sex partners did the patient have?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV606)" size="4" maxlength="4"  title="During the time period prior to the onset of symptoms, number of female sex partners the person had." styleId="INV606" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_10" name="Drug Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV607L" title="During the time period prior to the onset of symptoms, did the subject inject drugs?">
Did the patient inject drugs?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV607)" styleId="INV607" title="During the time period prior to the onset of symptoms, did the subject inject drugs?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV608L" title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?">
Did the patient use street drugs but not inject?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV608)" styleId="INV608" title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_11" name="Travel Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL30L" title="During the 2-6 weeks prior to the onset of symptoms, did the subject travel or live outside the U.S.A. or Canada?">
In the 2-6 weeks prior to onset, did the patient travel or live outside of the US or Canada?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL30)" styleId="TRAVEL30" title="During the 2-6 weeks prior to the onset of symptoms, did the subject travel or live outside the U.S.A. or Canada?" onchange="ruleEnDisTRAVEL307500();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL31L" title="The country(s) to which the subject traveled or lived (outside the U.S.A. or Canada) prior to symptom onset.">
If the patient traveled, where (select all that apply)?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(TRAVEL31)" styleId="TRAVEL31" title="The country(s) to which the subject traveled or lived (outside the U.S.A. or Canada) prior to symptom onset."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'TRAVEL31-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /> </html:select>
<div id="TRAVEL31-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL16L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL16)" styleId="TRAVEL16" title="Principal Reason for Travel">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_HEPATITISA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL32L" title="During the 3 months prior to the onset of symptoms, did anyone in the subject's household travel outside the U.S.A. or Canada?">
During 3 months prior to onset, did anyone in patient's household travel outside of US or Canada?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL32)" styleId="TRAVEL32" title="During the 3 months prior to the onset of symptoms, did anyone in the subject's household travel outside the U.S.A. or Canada?" onchange="ruleEnDisTRAVEL327501();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL33L" title="The country(s) to which anyone in the subject's household traveled (outside the U.S.A. or Canada) prior to symptom onset.">
If someone in patient's household traveled, where (select all that apply)?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(TRAVEL33)" styleId="TRAVEL33" title="The country(s) to which anyone in the subject's household traveled (outside the U.S.A. or Canada) prior to symptom onset."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'TRAVEL33-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /> </html:select>
<div id="TRAVEL33-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_12" name="Outbreak Association" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV618L" title="Is the subject suspected as being part of a common-source outbreak?">
Is the patient suspected as being part of a common-source outbreak?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV618)" styleId="INV618" title="Is the subject suspected as being part of a common-source outbreak?" onchange="ruleEnDisINV6187503();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV609L" title="If outbreak assoicated, was the patient associated with a foodborne outbreak that is associated with an infected food handler?">
Was the outbreak Foodborne - Associated with Infected Food Handler?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV609)" styleId="INV609" title="If outbreak assoicated, was the patient associated with a foodborne outbreak that is associated with an infected food handler?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV610L" title="If the patient is associated with an outbreak, is the patient associated with a foodborne outbreak that is not associated with an infected food handler?">
Was the outbreak Foodborne - NOT Associated With an Infected Food Handler?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV610)" styleId="INV610" title="If the patient is associated with an outbreak, is the patient associated with a foodborne outbreak that is not associated with an infected food handler?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV611L" title="Food item with which the foodborne outbreak is associated.">
Specify Food Item:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV611)" size="100" maxlength="100" title="Food item with which the foodborne outbreak is associated." styleId="INV611"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV612L" title="If assoicated with an outbreak, was the patient associated with a waterborne outbreak?">
Was the outbreak waterborne?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV612)" styleId="INV612" title="If assoicated with an outbreak, was the patient associated with a waterborne outbreak?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV613L" title="If associated with an outbreak, is patient associated with an outbreak that does not have an identifed source?">
Was the oubreak source not identified?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV613)" styleId="INV613" title="If associated with an outbreak, is patient associated with an outbreak that does not have an identifed source?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV621L" title="During the 2 weeks prior to the onset of symptoms or while ill, was the subject employed as a food handler?">
Was the patient employed as a food handler during the TWO WEEKS prior to onset or while ill?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV621)" styleId="INV621" title="During the 2 weeks prior to the onset of symptoms or while ill, was the subject employed as a food handler?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_14" name="Hepatitis Containing Vaccine" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Did patient ever receive a hepatitis-containing vaccine? If this is a hepatitis A investigation, answer this question regarding hepatitis A-containing vaccine. If it is a hepatitis B investigation, answer this question regarding hepatitis B-containing vaccine.">
Did patient ever receive a hepatitis A-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Did patient ever receive a hepatitis-containing vaccine? If this is a hepatitis A investigation, answer this question regarding hepatitis A-containing vaccine. If it is a hepatitis B investigation, answer this question regarding hepatitis B-containing vaccine." onchange="ruleHideUnhVAC1267505();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
How many doses?:</span>
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
<span class=" InputFieldLabel" id="VAC143L" title="Has the patient ever received immune globulin for this condition?">
Has the patient ever received immune globulin?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC143)" styleId="VAC143" title="Has the patient ever received immune globulin for this condition?" onchange="ruleHideUnhVAC1437510();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC144L" title="Date the patient received the last dose of immune globulin. For example, if the patient received the last dose in 2012, enter 01/01/2012. To convey an unknown date, enter 00/00/0000.">
When was the last dose of IG received?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC144)" maxlength="10" size="10" styleId="VAC144" onkeyup="DateMask(this,null,event)" title="Date the patient received the last dose of immune globulin. For example, if the patient received the last dose in 2012, enter 01/01/2012. To convey an unknown date, enter 00/00/0000."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC144','VAC144Icon'); return false;" styleId="VAC144Icon" onkeypress="showCalendarEnterKey('VAC144','VAC144Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
