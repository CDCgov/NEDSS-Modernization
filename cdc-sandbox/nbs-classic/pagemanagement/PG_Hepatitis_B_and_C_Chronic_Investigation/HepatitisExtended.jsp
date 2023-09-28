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
String [] sectionNames  = {"Chronic Hepatitis Infection"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_3" name="Chronic Hepatitis C Only" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV645L" title="Did the patient receive a blood transfusion prior to 1992?">
Did the patient receive a blood transfusion prior to 1992?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV645)" styleId="INV645" title="Did the patient receive a blood transfusion prior to 1992?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV646L" title="Did the patient receive an organ transplant prior to 1992?">
Did the patient receive an organ transplant prior to 1992?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV646)" styleId="INV646" title="Did the patient receive an organ transplant prior to 1992?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_4" name="Risk Factors" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV647L" title="Did the patient receive clotting factor concentrates prior to 1987?">
Did the patient receive clotting factor concentrates prior to 1987?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV647)" styleId="INV647" title="Did the patient receive clotting factor concentrates prior to 1987?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV644L" title="Was the patient ever on long-term hemodialysis?">
Was the patient ever on long-term hemodialysis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV644)" styleId="INV644" title="Was the patient ever on long-term hemodialysis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV643L" title="Has the patient ever injected drugs (those not prescribed by a doctor) even if only once or a few times?">
Has the patient ever injected drugs not prescribed by a doctor?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV643)" styleId="INV643" title="Has the patient ever injected drugs (those not prescribed by a doctor) even if only once or a few times?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV642L" title="How many sex partners has the patient had (approximate lifetime)?">
How many sex partners has the patient had?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV642)" size="4" maxlength="4"  title="How many sex partners has the patient had (approximate lifetime)?" styleId="INV642" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV649L" title="Was the patient ever incarcerated?">
Was the patient ever incarcerated?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV649)" styleId="INV649" title="Was the patient ever incarcerated?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV653bL" title="Was the patient ever treated for a sexually transmitted disease?">
Was the patient ever treated for a sexually transmitted disease?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV653b)" styleId="INV653b" title="Was the patient ever treated for a sexually transmitted disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV829L" title="Was the patient ever a contact of a  person who had hepatitis?">
Was the patient ever a contact of a  person who had hepatitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV829)" styleId="INV829" title="Was the patient ever a contact of a  person who had hepatitis?" onchange="ruleHideUnhINV8297447();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_5" name="Contact Types" isHidden="F" classType="subSect" >

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
<span class=" InputFieldLabel" id="INV603_3L" title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?">
Household Member (Non-Sexual) (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_3)" styleId="INV603_3" title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_6L" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?">
Other (Contact Type):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_6)" styleId="INV603_6" title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" onchange="ruleHideUnhINV603_67450();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_INV_HEPCHBC_UI_6" name="Risk Factors Continued" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV648L" title="Was the patient ever employed in a medical or dental field  involving direct contact with human blood?">
Patient ever employed in a medical or dental field involving direct contact with human blood?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV648)" styleId="INV648" title="Was the patient ever employed in a medical or dental field  involving direct contact with human blood?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH109L" title="What is the birth country of the mother?">
What is the birth country of the mother?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH109)" styleId="MTH109" title="What is the birth country of the mother?">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>

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
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
