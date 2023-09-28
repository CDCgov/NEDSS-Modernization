<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
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
String tabId = "editVaccination";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Vaccination"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_7" name="Vaccination Administered" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Please note: Record ALL doses of EVERY vaccine given. Record all information that is known, even data on vaccine doses administered beyond the recommended guidelines.</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC147L" title="The information source for this vaccination record.">
Vaccine Event Information Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC147)" styleId="VAC147" title="The information source for this vaccination record.">
<nedss:optionsCollection property="codedValue(PHVS_VACCINEEVENTINFORMATIONSOURCE_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC103L" title="Enter the date the vaccine was administered">
Vaccine Administered Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC103)" maxlength="10" size="10" styleId="VAC103" onkeyup="DateMask(this,null,event)" onblur="calculateAgeAtVaccination()" onchange="calculateAgeAtVaccination()" title="Enter the date the vaccine was administered"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC103','VAC103Icon'); return false;" styleId="VAC103Icon" onkeypress="showCalendarEnterKey('VAC103','VAC103Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC105L" title="The person's age at the time the vaccination was given.">
Age At Vaccination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC105)" size="3" maxlength="3"  title="The person's age at the time the vaccination was given." styleId="VAC105" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC106L" title="The age units of the person at the time the vaccination was given.">
Age At Vaccination Unit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC106)" styleId="VAC106" title="The age units of the person at the time the vaccination was given." onchange="ruleRequireIfVAC1066362();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC104L" title="The anatomical site where the vaccination was given.">
Vaccination Anatomical Site:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC104)" styleId="VAC104" title="The anatomical site where the vaccination was given.">
<nedss:optionsCollection property="codedValue(NIP_ANATOMIC_ST)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_1" name="Administered By" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="VAC117L" title="Provider that gave the vaccination to the patient.">
Vaccination Given By Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.VAC117Uid">
<span id="clearVAC117" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.VAC117Uid">
<span id="clearVAC117">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="VAC117CodeClearButton" onclick="clearProvider('VAC117')"/>
</span>
<span id="VAC117SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.VAC117Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="VAC117Icon" onclick="getProvider('VAC117');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(VAC117)" styleId="VAC117Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('VAC117Text','VAC117_qec_list')"
title="Provider that gave the vaccination to the patient."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="VAC117CodeLookupButton" onclick="getDWRProvider('VAC117')"
<logic:notEmpty name="PageForm" property="attributeMap.VAC117Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="VAC117_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="VAC117S">Vaccination Given By Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap.VAC117Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.VAC117Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.VAC117Uid"/>
<span id="VAC117">${PageForm.attributeMap.VAC117SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="VAC117Error"/>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="VAC116L" title="Organization that gave the vaccination to the patient.">
Vaccination Given By Organization:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.VAC116Uid">
<span id="clearVAC116" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.VAC116Uid">
<span id="clearVAC116">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="VAC116CodeClearButton" onclick="clearOrganization('VAC116')"/>
</span>
<span id="VAC116SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.VAC116Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="VAC116Icon" onclick="getReportingOrg('VAC116');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(VAC116)" styleId="VAC116Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('VAC116Text','VAC116_qec_list')"
title="Organization that gave the vaccination to the patient."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="VAC116CodeLookupButton" onclick="getDWROrganization('VAC116')"
<logic:notEmpty name="PageForm" property="attributeMap.VAC116Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="VAC116_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="VAC116S">Vaccination Given By Organization Selected: </td>
<logic:empty name="PageForm" property="attributeMap.VAC116Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.VAC116Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.VAC116Uid"/>
<span id="VAC116">${PageForm.attributeMap.VAC116SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="VAC116Error"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="VAC101L" title="The type of vaccine administered for the condition being reported.">
Vaccine Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC101)" styleId="VAC101" title="The type of vaccine administered for the condition being reported.">
<nedss:optionsCollection property="codedValue(VAC_NM)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC107L" title="The company which manufactured the vaccine.">
Vaccine Manufacturer:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC107)" styleId="VAC107" title="The company which manufactured the vaccine.">
<nedss:optionsCollection property="codedValue(VAC_MFGR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC109L" title="The expiration date for the vaccine administered.">
Vaccine Expiration Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC109)" maxlength="10" size="10" styleId="VAC109" onkeyup="DateMaskFuture(this,null,event)" title="The expiration date for the vaccine administered."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('VAC109','VAC109Icon'); return false;" styleId="VAC109Icon" onkeypress ="showCalendarFutureEnterKey('VAC109','VAC109Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="VAC108L" title="The lot number for the vaccine administered.">
Vaccine Lot Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(VAC108)" size="30" maxlength="30" title="The lot number for the vaccine administered." styleId="VAC108"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC120L" title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)">
Dose Number:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC120)" size="2" maxlength="2"  title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)" styleId="VAC120" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,99)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_5" name="Vaccine Schedule Links" isHidden="F" classType="subSect" >

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://www.cdc.gov/vaccines/schedules/downloads/adult/adult-schedule.pdf" TARGET="_blank">Adult Schedule (Over 18 years)</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://www.cdc.gov/vaccines/schedules/downloads/child/0-18yrs-child-combined-schedule.pdf" TARGET="_blank">Child Schedule (0-18 years)</a></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
