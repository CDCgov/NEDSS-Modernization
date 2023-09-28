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
String tabId = "editCoreInfo";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Pregnant Information","900 Case Status","Risk Factors-Last 12 Months","Hangouts","Partner Information","Target Populations","STD Testing","Signs and Symptoms","STD History","900 Partner Services Information"};
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
<nedss:container id="NBS_INV_STD_UI_37" name="Pregnant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1787933();ruleRequireIfINV1787934();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS128)" size="2" maxlength="2"  title="Number of weeks pregnant at the time of diagnosis." styleId="NBS128" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS216L" title="Was the patient pregnant at the initial exam for the condition?">
Pregnant at Exam:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS216)" styleId="NBS216" title="Was the patient pregnant at the initial exam for the condition?" onchange="ruleEnDisNBS2167925();ruleRequireIfNBS2167926();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS217L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS217)" size="2" maxlength="2"  title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam." styleId="NBS217" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS218L" title="Was the patient pregnant at the time of interview for the condition.">
Pregnant at Interview:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS218)" styleId="NBS218" title="Was the patient pregnant at the time of interview for the condition." onchange="ruleEnDisNBS2187927();ruleRequireIfNBS2187928();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS219L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS219)" size="2" maxlength="2"  title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview." styleId="NBS219" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS220L" title="Is the patient receiving/received prenatal care for this pregnancy?">
Currently in Prenatal Care:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS220)" styleId="NBS220" title="Is the patient receiving/received prenatal care for this pregnancy?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS221L" title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy.">
Pregnant in Last 12 Months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS221)" styleId="NBS221" title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy." onchange="ruleEnDisNBS2217929();ruleRequireIfNBS2217930();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS222L" title="If pregnant in the last 12 months, indicate the outcome of the pregnancy.">
Pregnancy Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS222)" styleId="NBS222" title="If pregnant in the last 12 months, indicate the outcome of the pregnancy.">
<nedss:optionsCollection property="codedValue(PREGNANCY_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_39" name="Patient HIV Status" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS153L" title="The patient's HIV status.">
900 Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS153)" styleId="NBS153" title="The patient's HIV status.">
<nedss:optionsCollection property="codedValue(STATUS_900)" value="key" label="value" /></html:select>
</td></tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV892L" title="Was the HIV status of this case investigated through search of eHARS?">
HIV Status Documented Through eHARS Record Search:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV892)" styleId="INV892" title="Was the HIV status of this case investigated through search of eHARS?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS269L" title="Enter the state-assigned HIV Case Number for this patient.">
State HIV (eHARS) Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS269)" size="16" maxlength="16" title="Enter the state-assigned HIV Case Number for this patient." styleId="NBS269"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV894L" title="Mode of exposure from eHARS for HIV+ cases.">
Transmission Category (eHARS):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV894)" styleId="INV894" title="Mode of exposure from eHARS for HIV+ cases.">
<nedss:optionsCollection property="codedValue(PHVS_TRANSMISSIONCATEGORY_STD)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV895L" title="Was this case selected by reporting jurisdiction for enhanced investigation?">
Case Sampled for Enhanced Investigation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV895)" styleId="INV895" title="Was this case selected by reporting jurisdiction for enhanced investigation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_41" name="Risk Factors (Last 12 Months)" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS229L" title="Were behavioral risk factors assessed for the client?">
Was Behavioral Risk Assessed:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS229)" styleId="NBS229" title="Were behavioral risk factors assessed for the client?" onchange="stdBehavorialRiskAssessedEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(RISK_PROFILE_IND)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_42" name="Sex Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD107L" title="Had sex with a male within past 12 months?">
Had Sex with Male:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD107)" styleId="STD107" title="Had sex with a male within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD108L" title="Had sex with a female within past 12 months?">
Had Sex with Female:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD108)" styleId="STD108" title="Had sex with a female within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS230L" title="Had sex with transgender partner within past 12 months?">
Had Sex with Transgender:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS230)" styleId="NBS230" title="Had sex with transgender partner within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD109L" title="Had sex with an anonymous partner within past 12 months?">
Had Sex with Anonymous Partner:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD109)" styleId="STD109" title="Had sex with an anonymous partner within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_43" name="Sex Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS231L" title="Had sex without a condom within past 12 months?">
Had Sex Without a Condom:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS231)" styleId="NBS231" title="Had sex without a condom within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD111L" title="Had sex while intoxicated/high within past 12 months?">
Had Sex While Intoxicated/High:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD111)" styleId="STD111" title="Had sex while intoxicated/high within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD112L" title="Had sex in exchange for drugs/money within past 12 months?">
Exchanged Drugs/Money for Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD112)" styleId="STD112" title="Had sex in exchange for drugs/money within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD113L" title="Female only.  Had sex with a known MSM within past 12 months?">
Females - Had Sex with Known MSM:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD113)" styleId="STD113" title="Female only.  Had sex with a known MSM within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD110L" title="Had sex with a known Injection Drug User within past 12 months?">
Had Sex with Known IDU:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD110)" styleId="STD110" title="Had sex with a known Injection Drug User within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_44" name="Risk Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD118L" title="Incarcerated within the past 12 months?">
Been Incarcerated:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD118)" styleId="STD118" title="Incarcerated within the past 12 months?">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD114L" title="Used injection drugs within the past 12 months?">
Injection Drug Use:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD114)" styleId="STD114" title="Used injection drugs within the past 12 months?" onchange="ruleEnDisSTD1147966();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS232L" title="Shared injection equipment within the past 12 months?">
Shared Injection Equipment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS232)" styleId="NBS232" title="Shared injection equipment within the past 12 months?">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_45" name="Drug Use Past 12 Months" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_STD_UI_46L">&nbsp;&nbsp;During the past 12 months, indicate whether or not the patient used any of the following injection or non-injection drugs.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS233L" title="No drug use within the past 12 months?">
No drug use reported:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS233)" styleId="NBS233" title="No drug use within the past 12 months?" onchange="ruleEnDisNBS2337964();ruleEnDisNBS2407965();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS237L" title="Cocaine use within the past 12 months?">
Cocaine:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS237)" styleId="NBS237" title="Cocaine use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS235L" title="Crack use within the past 12 months?">
Crack:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS235)" styleId="NBS235" title="Crack use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS239L" title="Heroine use within the past 12 months?">
Heroin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS239)" styleId="NBS239" title="Heroine use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS234L" title="Methamphetamine (meth)use within the past 12 months?">
Methamphetamine:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS234)" styleId="NBS234" title="Methamphetamine (meth)use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS236L" title="Nitrate/popper use within the past 12 months?">
Nitrates/Poppers:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS236)" styleId="NBS236" title="Nitrate/popper use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS238L" title="Erectile dysfunction drug use within the past 12 months?">
Erectile Dysfunction Medications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS238)" styleId="NBS238" title="Erectile dysfunction drug use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS240L" title="Other drug use within the past 12 months?">
Other drug used:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS240)" styleId="NBS240" title="Other drug use within the past 12 months?" onchange="ruleEnDisNBS2407965();stdOtherDrugEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="STD300L" title="If patient indicated other drug use, specify the drug name(s).">
Specify Other Drug Used:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(STD300)" size="50" maxlength="50" title="If patient indicated other drug use, specify the drug name(s)." styleId="STD300"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_48" name="Places to Meet Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS242L" title="Places to Meet Partners">
Places to Meet Partners:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS242)" styleId="NBS242" title="Places to Meet Partners" onchange="ruleHideUnhNBS2427949();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_49" name="Places Selected to Meet Partners" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_49errorMessages">
<b> <a name="NBS_INV_STD_UI_49errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_49"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_49">
<tr id="patternNBS_INV_STD_UI_49" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_49" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_49');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_49" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_49');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_49" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_49','patternNBS_INV_STD_UI_49','questionbodyNBS_INV_STD_UI_49');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_49">
<tr id="nopatternNBS_INV_STD_UI_49" class="odd" style="display:">
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

<!--processing Place Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS243L" title="Select the Hangout or Meetup.">
Place:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS243Uid">
<span id="clearNBS243" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS243Uid">
<span id="clearNBS243">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS243CodeClearButton" onclick="clearPlace('NBS243')"/>
</span>
<span id="NBS243SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS243Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS243Icon" onclick="getHangoutPlace('NBS243');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS243)" styleId="NBS243Text"
size="10" maxlength="10"
title="Select the Hangout or Meetup."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS243CodeLookupButton" onclick="getDWRPlace('NBS243')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS243Uid">
style="visibility:hidden"
</logic:notEmpty>
/>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS243S">Place Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS243Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS243Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS243Disp" styleId="NBS243"/>
<span id="NBS243Disp">${PageForm.attributeMap.NBS243SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS243Error"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_49">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49','patternNBS_INV_STD_UI_49','questionbodyNBS_INV_STD_UI_49')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_49">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49','patternNBS_INV_STD_UI_49','questionbodyNBS_INV_STD_UI_49');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_49"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49','patternNBS_INV_STD_UI_49','questionbodyNBS_INV_STD_UI_49');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_49"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_49')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_50" name="Places to have Sex" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS244L" title="Places to Have Sex">
Places to Have Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS244)" styleId="NBS244" title="Places to Have Sex" onchange="ruleHideUnhNBS2447950();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_51" name="Places Selected to Have Sex" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_51errorMessages">
<b> <a name="NBS_INV_STD_UI_51errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_51"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_51">
<tr id="patternNBS_INV_STD_UI_51" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_51" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_51');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_51" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_51');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_51" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_51','patternNBS_INV_STD_UI_51','questionbodyNBS_INV_STD_UI_51');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_51">
<tr id="nopatternNBS_INV_STD_UI_51" class="odd" style="display:">
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

<!--processing Place Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS290L" title="Select the Hangout or Sex.">
Place:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS290Uid">
<span id="clearNBS290" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS290Uid">
<span id="clearNBS290">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS290CodeClearButton" onclick="clearPlace('NBS290')"/>
</span>
<span id="NBS290SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS290Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS290Icon" onclick="getHangoutPlace('NBS290');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS290)" styleId="NBS290Text"
size="10" maxlength="10"
title="Select the Hangout or Sex."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS290CodeLookupButton" onclick="getDWRPlace('NBS290')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS290Uid">
style="visibility:hidden"
</logic:notEmpty>
/>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS290S">Place Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS290Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS290Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS290Disp" styleId="NBS290"/>
<span id="NBS290Disp">${PageForm.attributeMap.NBS290SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS290Error"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_51">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51','patternNBS_INV_STD_UI_51','questionbodyNBS_INV_STD_UI_51')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_51">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51','patternNBS_INV_STD_UI_51','questionbodyNBS_INV_STD_UI_51');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_51"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51','patternNBS_INV_STD_UI_51','questionbodyNBS_INV_STD_UI_51');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_51"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_51')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_53" name="Partners Past Year" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS223L" title="Female partners claimed in the last 12 months?">
Female Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS223)" styleId="NBS223" title="Female partners claimed in the last 12 months?" onchange="ruleEnDisNBS2237938();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS224L" title="The total number of female partners claimed in the last 12 months.">
Number Female (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS224)" size="3" maxlength="3"  title="The total number of female partners claimed in the last 12 months." styleId="NBS224" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS225L" title="Male partners claimed in the last 12 months?">
Male Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS225)" styleId="NBS225" title="Male partners claimed in the last 12 months?" onchange="ruleEnDisNBS2257939();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS226L" title="The total number of male partners claimed in the last 12 months.">
Number Male (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS226)" size="3" maxlength="3"  title="The total number of male partners claimed in the last 12 months." styleId="NBS226" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS227L" title="Transgender partners claimed in the last 12 months?">
Transgender Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS227)" styleId="NBS227" title="Transgender partners claimed in the last 12 months?" onchange="ruleEnDisNBS2277940();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS228L" title="The total number of transgender partners claimed in the last 12 months.">
Number Transgender (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS228)" size="3" maxlength="3"  title="The total number of transgender partners claimed in the last 12 months." styleId="NBS228" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="STD120L" title="Total number of sex partners last 12 months?">
Total number of sex partners last 12 months?:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(STD120)" size="3" maxlength="3"  title="Total number of sex partners last 12 months?" styleId="STD120" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD888L" title="Patient refused to answer questions regarding number of sex partners">
Patient refused to answer questions regarding number of sex partners:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD888)" styleId="STD888" title="Patient refused to answer questions regarding number of sex partners">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD999L" title="Unknown number of sex partners in last 12 months">
Unknown number of sex partners in last 12 months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD999)" styleId="STD999" title="Unknown number of sex partners in last 12 months">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_54" name="Partners in Interview Period" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS129L" title="Female sex/needle-sharing interview period partners claimed?">
Female Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS129)" styleId="NBS129" title="Female sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1297935();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS130L" title="The total number of female sex/needle-sharing interview period partners claimed.">
Number Female (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS130)" size="3" maxlength="3"  title="The total number of female sex/needle-sharing interview period partners claimed." styleId="NBS130" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS131L" title="Male sex/needle-sharing interview period partners claimed?">
Male Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS131)" styleId="NBS131" title="Male sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1317936();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS132L" title="The total number of male sex/needle-sharing interview period partners claimed.">
Number Male (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS132)" size="3" maxlength="3"  title="The total number of male sex/needle-sharing interview period partners claimed." styleId="NBS132" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS133L" title="Transgender sex/needle-sharing interview period partners claimed?">
Transgender Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS133)" styleId="NBS133" title="Transgender sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1337937();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS134L" title="The total number of transgender sex/needle-sharing interview period partners claimed.">
Number Transgender (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS134)" size="3" maxlength="3"  title="The total number of transgender sex/needle-sharing interview period partners claimed." styleId="NBS134" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_55" name="Partner Internet Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD119L" title="Have you met sex partners through the Internet in the last 12 months?">
Met Sex Partners through the Internet:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD119)" styleId="STD119" title="Have you met sex partners through the Internet in the last 12 months?">
<nedss:optionsCollection property="codedValue(YNRUD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_57" name="Target Populations" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS271L" title="Target populations identified for the patient.">
Target Population(s):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS271)" styleId="NBS271" title="Target populations identified for the patient."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS271-selectedValues')" >
<nedss:optionsCollection property="codedValue(TARGET_POPULATIONS)" value="key" label="value" /> </html:select>
<div id="NBS271-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_59" name="Syphilis Test Results" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS275L" title="Have  non-treponemal or treponemal syphilis tests been performed?">
Tests Performed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS275)" styleId="NBS275" title="Have  non-treponemal or treponemal syphilis tests been performed?" onchange="ruleEnDisNBS2757978();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD122L" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
Type of Nontreponemal Serologic Test for Syphilis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD122)" styleId="STD122" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD123L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD123)" styleId="STD123" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">
<nedss:optionsCollection property="codedValue(PHVS_QUANTITATIVESYPHILISTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD126L" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">
Nontreponemal Serologic Syphilis Test Result (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD126)" styleId="STD126" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTREACTIVITY_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD124L" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
Type of Treponemal SerologicTest for Syphilis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD124)" styleId="STD124" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
<nedss:optionsCollection property="codedValue(PHVS_TREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD125L" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">
Treponemal Serologic Syphilis Test Result  (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD125)" styleId="STD125" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_71" name="STD Lab Test Results" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_71errorMessages">
<b> <a name="NBS_INV_STD_UI_71errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_71"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_71">
<tr id="patternNBS_INV_STD_UI_71" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_71" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_71');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_71" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_71');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_71" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_71','patternNBS_INV_STD_UI_71','questionbodyNBS_INV_STD_UI_71');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_71">
<tr id="nopatternNBS_INV_STD_UI_71" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_INV_STD_UI_71');ruleEnDisINV2907970();ruleEnDisINV2907969();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="INV291L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative.">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="STD123_1L" title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result).">
Test Result Coded Quantitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD123_1)" styleId="STD123_1" title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result)." onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Quantitative Test Result Value" styleId="LAB628" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="LAB115L" title="Units of measure for the Quantitative Test Result Value">
Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB115)" styleId="LAB115" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB167L" title="Date result sent from Reporting Laboratory">
Lab Result Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB167)" maxlength="10" size="10" styleId="LAB167" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_71" title="Date result sent from Reporting Laboratory"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB167','LAB167Icon'); unhideBatchImg('NBS_INV_STD_UI_71');return false;" styleId="LAB167Icon" onkeypress="showCalendarEnterKey('LAB167','LAB167Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="LAB165L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB165)" styleId="LAB165" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_INV_STD_UI_71');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENSOURCE_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_INV_STD_UI_71')" styleId="LAB165Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="338822L" title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(338822)" maxlength="10" size="10" styleId="338822" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_71" title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('338822','338822Icon'); unhideBatchImg('NBS_INV_STD_UI_71');return false;" styleId="338822Icon" onkeypress="showCalendarEnterKey('338822','338822Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_71">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71','patternNBS_INV_STD_UI_71','questionbodyNBS_INV_STD_UI_71')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_71">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71','patternNBS_INV_STD_UI_71','questionbodyNBS_INV_STD_UI_71');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_71"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71','patternNBS_INV_STD_UI_71','questionbodyNBS_INV_STD_UI_71');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_71"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_71')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_72" name="Antimicrobial Susceptibility Testing(AST)" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_72errorMessages">
<b> <a name="NBS_INV_STD_UI_72errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_72"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_72">
<tr id="patternNBS_INV_STD_UI_72" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_72" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_72');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_72" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_72');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_72" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_72','patternNBS_INV_STD_UI_72','questionbodyNBS_INV_STD_UI_72');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_72">
<tr id="nopatternNBS_INV_STD_UI_72" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST1L" title="Pathogen/Organism Identified in Isolate.">
Microorganism Identified in Isolate:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST1)" styleId="LABAST1" title="Pathogen/Organism Identified in Isolate." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_ORGANISMIDENTIFIEDAST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LABAST2L" title="Isolate identifier unique for each isolate within laboratory.">
Isolate Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LABAST2)" size="30" maxlength="30" title="Isolate identifier unique for each isolate within laboratory." styleId="LABAST2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST3L" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST3)" styleId="LABAST3" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');enableOrDisableOther('LABAST3');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENTYPEAST_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LABAST3Oth)" size="40" maxlength="40" title="Other Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72')" styleId="LABAST3Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST4L" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)">
Specimen Collection Site:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST4)" styleId="LABAST4" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');enableOrDisableOther('LABAST4');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENCOLLECTIONSITEAST_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4OthL">Other Specimen Collection Site:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LABAST4Oth)" size="40" maxlength="40" title="Other Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72')" styleId="LABAST4Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LABAST5L" title="Antimicrobial Susceptibility Specimen Collection Date">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LABAST5)" maxlength="10" size="10" styleId="LABAST5" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_72" title="Antimicrobial Susceptibility Specimen Collection Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LABAST5','LABAST5Icon'); unhideBatchImg('NBS_INV_STD_UI_72');return false;" styleId="LABAST5Icon" onkeypress="showCalendarEnterKey('LABAST5','LABAST5Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST6L" title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs.">
AST Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST6)" styleId="LABAST6" title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTTYPE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST7L" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)">
AST Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST7)" styleId="LABAST7" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTMETHOD_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST8L" title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)">
AST Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST8)" styleId="LABAST8" title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTINTERPRETATION_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST11L" title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result.">
AST Result Coded Quantitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST11)" styleId="LABAST11" title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTRESULTQUANTITATIVE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LABAST9L" title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)">
AST Result Quantitative Value:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LABAST9)" size="10" maxlength="10" title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)" styleId="LABAST9" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST10L" title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)">
Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LABAST10)" styleId="LABAST10" title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTRESULTUNITS_STD)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_72">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72','patternNBS_INV_STD_UI_72','questionbodyNBS_INV_STD_UI_72')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_72">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72','patternNBS_INV_STD_UI_72','questionbodyNBS_INV_STD_UI_72');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_72"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72','patternNBS_INV_STD_UI_72','questionbodyNBS_INV_STD_UI_72');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_72"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_72')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_61" name="Signs and Symptoms" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_61errorMessages">
<b> <a name="NBS_INV_STD_UI_61errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_61"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_61">
<tr id="patternNBS_INV_STD_UI_61" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_61" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_61');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_61" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_61');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_61" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_61','patternNBS_INV_STD_UI_61','questionbodyNBS_INV_STD_UI_61');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_61">
<tr id="nopatternNBS_INV_STD_UI_61" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_INV_STD_UI_61 InputFieldLabel" id="NBS246L" title="Is the sign/symptom experienced by the patient or observed by a clinician?">
Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS246)" styleId="NBS246" title="Is the sign/symptom experienced by the patient or observed by a clinician?" onchange="unhideBatchImg('NBS_INV_STD_UI_61');">
<nedss:optionsCollection property="codedValue(SIGN_SX_OBSRV_SOURCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS247L" title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician.">
Observation/Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS247)" maxlength="10" size="10" styleId="NBS247" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_61" title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS247','NBS247Icon'); unhideBatchImg('NBS_INV_STD_UI_61');return false;" styleId="NBS247Icon" onkeypress="showCalendarEnterKey('NBS247','NBS247Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_61 InputFieldLabel" id="INV272L" title="Sign/symptom observed on exam or described.">
Sign/Symptom:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV272)" styleId="INV272" title="Sign/symptom observed on exam or described." onchange="unhideBatchImg('NBS_INV_STD_UI_61');">
<nedss:optionsCollection property="codedValue(SIGN_SX_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_61 InputFieldLabel" id="STD121L" title="The anatomic site of the sign/symptom.">
Anatomic Site:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(STD121)" styleId="STD121" title="The anatomic site of the sign/symptom."
multiple="true" size="4"
onchange="unhideBatchImg('NBS_INV_STD_UI_61');displaySelectedOptions(this, 'STD121-selectedValues');ruleEnDisSTD1217942()" >
<nedss:optionsCollection property="codedValue(PHVS_CLINICIANOBSERVEDLESIONS_STD)" value="key" label="value" /> </html:select>
<div id="STD121-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS248L" title="Provide a description of the other anatomic site.">
Other Anatomic Site, Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS248)" size="50" maxlength="50" title="Provide a description of the other anatomic site." styleId="NBS248" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS249L" title="The number of days signs/symptoms were present. Document “99” if unknown.">
Duration (Days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS249)" size="4" maxlength="4"  title="The number of days signs/symptoms were present. Document “99” if unknown." styleId="NBS249" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_61">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61','patternNBS_INV_STD_UI_61','questionbodyNBS_INV_STD_UI_61')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_61">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61','patternNBS_INV_STD_UI_61','questionbodyNBS_INV_STD_UI_61');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_61"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61','patternNBS_INV_STD_UI_61','questionbodyNBS_INV_STD_UI_61');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_61"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_61')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_63" name="Previous STD History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD117L" title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?">
Previous STD history (self-reported)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD117)" styleId="STD117" title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?" onchange="ruleHideUnhSTD1177951();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_64" name="STD History" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_64errorMessages">
<b> <a name="NBS_INV_STD_UI_64errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_64"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_64">
<tr id="patternNBS_INV_STD_UI_64" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_64" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_64');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_64" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_64');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_64" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_64','patternNBS_INV_STD_UI_64','questionbodyNBS_INV_STD_UI_64');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_64">
<tr id="nopatternNBS_INV_STD_UI_64" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_INV_STD_UI_64 InputFieldLabel" id="NBS250L" title="With what condition was the patient previously diagnosed?">
Previous Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS250)" styleId="NBS250" title="With what condition was the patient previously diagnosed?" onchange="unhideBatchImg('NBS_INV_STD_UI_64');">
<nedss:optionsCollection property="codedValue(STD_HISTORY_DIAGNOSIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS251L" title="Diagnosis Date of previous STD.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS251)" maxlength="10" size="10" styleId="NBS251" onkeyup="unhideBatchImg('NBS_INV_STD_UI_64');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_64" title="Diagnosis Date of previous STD."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS251','NBS251Icon'); unhideBatchImg('NBS_INV_STD_UI_64');return false;" styleId="NBS251Icon" onkeypress="showCalendarEnterKey('NBS251','NBS251Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS252L" title="Treatment Date of previous STD.">
Treatment Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS252)" maxlength="10" size="10" styleId="NBS252" onkeyup="unhideBatchImg('NBS_INV_STD_UI_64');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_64" title="Treatment Date of previous STD."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS252','NBS252Icon'); unhideBatchImg('NBS_INV_STD_UI_64');return false;" styleId="NBS252Icon" onkeypress="showCalendarEnterKey('NBS252','NBS252Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_64 InputFieldLabel" id="NBS253L" title="Confirmed the Previous STD?">
Confirmed:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS253)" styleId="NBS253" title="Confirmed the Previous STD?" onchange="unhideBatchImg('NBS_INV_STD_UI_64');">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_64">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64','patternNBS_INV_STD_UI_64','questionbodyNBS_INV_STD_UI_64')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_64">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64','patternNBS_INV_STD_UI_64','questionbodyNBS_INV_STD_UI_64');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64','patternNBS_INV_STD_UI_64','questionbodyNBS_INV_STD_UI_64');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_64')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_66" name="Consented to Enrollment in Partner Services" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS257L" title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview).">
Enrolled in Partner Services:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS257)" styleId="NBS257" title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview).">
<nedss:optionsCollection property="codedValue(ENROLL_HIV_PARTNER_SERVICES_IND)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_67" name="Self-Reported Results" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS254L" title="Previously tested for 900?">
Previous 900 Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS254)" styleId="NBS254" title="Previously tested for 900?" onchange="ruleEnDisNBS2547953();ruleEnDisSTD1067955();ruleRequireIfSTD1067956();ruleRequireIfNBS2547954();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNRUD)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD106L" title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test.">
Self-reported or Documented Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD106)" styleId="STD106" title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test." onchange="ruleEnDisSTD1067955();ruleRequireIfSTD1067956();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(STD_SELF_REPORTED_900_TEST_RESULT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS259L" title="Date of last 900 Test">
Date Last 900 Test:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS259)" maxlength="10" size="10" styleId="NBS259" onkeyup="DateMask(this,null,event)" title="Date of last 900 Test"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS259','NBS259Icon'); return false;" styleId="NBS259Icon" onkeypress="showCalendarEnterKey('NBS259','NBS259Icon',event)"></html:img>
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_68" name="Referred to Testing" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS260L" title="Referred for 900 test?">
Refer for Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS260)" styleId="NBS260" title="Referred for 900 test?" onchange="ruleEnDisNBS2607957();ruleEnDisNBS2627959();ruleRequireIfNBS2627960();;ruleEnDisNBS4477976();ruleRequireIfNBS2607958();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS261L" title="Date referred for 900 test.">
Referral Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS261)" maxlength="10" size="10" styleId="NBS261" onkeyup="DateMask(this,null,event)" title="Date referred for 900 test."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS261','NBS261Icon'); return false;" styleId="NBS261Icon" onkeypress="showCalendarEnterKey('NBS261','NBS261Icon',event)"></html:img>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS262L" title="900 test performed at this event?">
900 Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS262)" styleId="NBS262" title="900 test performed at this event?" onchange="ruleEnDisNBS2627959();;ruleEnDisNBS4477976();ruleRequireIfNBS2627960();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(900_TST_AT_EVENT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS450L" title="Indicate the date that the specimen for the HIV test was collected.">
900 Test Sample Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS450)" maxlength="10" size="10" styleId="NBS450" onkeyup="DateMask(this,null,event)" title="Indicate the date that the specimen for the HIV test was collected."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS450','NBS450Icon'); return false;" styleId="NBS450Icon" onkeypress="showCalendarEnterKey('NBS450','NBS450Icon',event)"></html:img>
</td> </tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS263L" title="Result of 900 test at this event.">
900 Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS263)" styleId="NBS263" title="Result of 900 test at this event.">
<nedss:optionsCollection property="codedValue(900_TEST_RESULTS)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS265L" title="The partner was informed of their 900 test result?">
Result provided:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS265)" styleId="NBS265" title="The partner was informed of their 900 test result?">
<nedss:optionsCollection property="codedValue(900_RESULT_PROVIDED)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS264L" title="Post-test Counselling">
Post-test Counselling:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS264)" styleId="NBS264" title="Post-test Counselling">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS447L" title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities.">
Patient Tested for Syphilis In Conjunction with HIV Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS447)" styleId="NBS447" title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities." onchange="ruleEnDisNBS4477976();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS448L" title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services.">
Syphilis Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS448)" styleId="NBS448" title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services.">
<nedss:optionsCollection property="codedValue(SYPHILIS_TEST_RESULT_PS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_69" name="Referred to Medical Testing (900 +)" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS266L" title="Referred to 900 medical care/evaluation/treatment.">
Refer for Care:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS266)" styleId="NBS266" title="Referred to 900 medical care/evaluation/treatment." onchange="ruleEnDisNBS2667961();ruleEnDisNBS2677977();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS267L" title="If referred, did patient keep 1st appointment?">
Keep Appointment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS267)" styleId="NBS267" title="If referred, did patient keep 1st appointment?" onchange="ruleEnDisNBS2677977();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(KEEP_FIRST_APPT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS302L" title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services.">
Appointment Date (If Confirmed):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS302)" maxlength="10" size="10" styleId="NBS302" onkeyup="DateMask(this,null,event)" title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS302','NBS302Icon'); return false;" styleId="NBS302Icon" onkeypress="showCalendarEnterKey('NBS302','NBS302Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_75" name="Pre Exposure Prophylaxis (PrEP)" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS443L" title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication.">
Is the Client Currently On PrEP?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS443)" styleId="NBS443" title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS446L" title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP).">
Has Client Been Referred to PrEP Provider?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS446)" styleId="NBS446" title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP).">
<nedss:optionsCollection property="codedValue(PREP_REFERRAL_PS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_70" name="Anti-Retroviral Therapy for HIV Infection" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS255L" title="Anti-virals taken within the last 12 months?">
Anti-viral Therapy - Last 12 Months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS255)" styleId="NBS255" title="Anti-virals taken within the last 12 months?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS256L" title="Anti-virals ever taken (including past year)?">
Anti-viral Therapy - Ever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS256)" styleId="NBS256" title="Anti-virals ever taken (including past year)?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
