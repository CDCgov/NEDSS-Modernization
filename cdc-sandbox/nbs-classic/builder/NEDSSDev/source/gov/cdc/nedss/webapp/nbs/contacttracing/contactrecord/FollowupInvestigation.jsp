<?xml version="1.0" encoding="UTF-8"?>
<!-- ### Hardcoded Contact STD Follow-up Page ###- - -->
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
String tabId = "editFollow-upInvestigation";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Create Investigation for Follow-up"};
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA30102" name="Investigation Details" isHidden="F" classType="subSect" >
<html:hidden styleId="coInfectionInv" property="attributeMap.coInfectionInv"/>
<html:hidden styleId="CONDITION_CD" property="attributeMap.CONDITION_CD"/>
<html:hidden styleId="investigationType" property="cTContactClientVO.answer(investigationType)"/>
<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Enter the following information to start an investigation for Field Follow-up from this Contact Record. This is a one time action on the Add Contact Record.</td></tr>

<!-- Investigation Start Date -->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="INV147L" title="The date the investigation was started or initiated.">
Investigation Start Date:</span>
</td>
<td>
<html:text  name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(INV147)"  maxlength="10" size="10" styleId="INV147" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV147','INV147Icon');return false;" onkeypress ="showCalendarEnterKey('INV147','INV147Icon',event);" styleId="INV147Icon"></html:img>
</td> </tr>
<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191S"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS191)" />
</td>
<td>
<html:hidden name="contactTracingForm"  property="cTContactClientVO.answer(NBS191)" styleId="NBS191" />
</td>
</tr>
<!--Investigator Participation Question-->
<tr>
<td class="fieldName">
<span id="CONINV180L" title="The Public Health Investigator assigned to the Investigation.">
Investigator:</span> </td>
<td>
<logic:empty name="contactTracingForm" property="attributeMap.CONINV180Uid">
<span id="clearCONINV180" class="none">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CONINV180Uid">
<span id="clearCONINV180">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="CONINV180CodeClearButton" onclick="clearProvider('CONINV180')"/>
</span>
<span id="CONINV180SearchControls"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CONINV180Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="CONINV180Icon" onclick="getProvider('CONINV180');" />&nbsp; - OR - &nbsp;
<html:text property="cTContactClientVO.answer(CONINV180)" styleId="CONINV180Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('CONINV180Text','CONINV180_qec_list')"
title="The Public Health Investigator assigned to the Investigation."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="CONINV180CodeLookupButton" onclick="getDWRProvider('CONINV180')"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CONINV180Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="CONINV180_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="CONINV180S">Investigator Selected: </td>
<logic:empty name="contactTracingForm" property="attributeMap.CONINV180Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CONINV180Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.CONINV180Uid"/>
<span id="CONINV180">${contactTracingForm.attributeMap.CONINV180SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="CONINV180Error"/>
</td></tr>

<!-- Date Investigator Assigned -->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="INV110L" title="The date the investigation was assigned/started.">
Date Assigned to Investigation:</span>
</td>
<td>
<html:text  name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(INV110)" maxlength="10" size="10" styleId="INV110" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon');return false;" onkeypress ="showCalendarEnterKey('INV110','INV110Icon',event);" styleId="INV110Icon"></html:img>
</td> </tr>

<!--Internet Follow-up  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS142L" title="Initiate for Internet follow-up?">
Internet Follow-Up:</span>
</td>
<td>
<html:select title = "Internet Follow-Up" name="contactTracingForm" property="cTContactClientVO.answer(NBS142)" styleId="NBS142">
<html:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>


<!--Notifiable Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS143L" title="For field follow-up, is patient notifiable?">
Notifiable:</span>
</td>
<td>
<html:select title="Notifiable" name="contactTracingForm" property="cTContactClientVO.answer(NBS143)" styleId="NBS143">
<html:optionsCollection property="codedValue(NOTIFIABLE)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
