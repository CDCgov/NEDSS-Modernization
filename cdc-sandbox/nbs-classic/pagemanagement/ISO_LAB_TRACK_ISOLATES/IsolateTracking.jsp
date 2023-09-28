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
String tabId = "editIsolateTracking";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Isolate Tracking"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Isolate Tracking" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB331L" title="Indicates if an isolate was received at the state public health lab.">
Was an isolate received at the state public health lab?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB331)" styleId="NBS_LAB331" title="Indicates if an isolate was received at the state public health lab." onchange="ruleHideUnhNBS_LAB3316397();ruleHideUnhNBS_LAB3316396();ruleHideUnhNBS_LAB3326412();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB332L" title="If an isolate wasnt received at the state public health lab, specifies the reason the isolate wasnt received">
If an isolate wasnt received at the state public health lab, what is the reason?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB332)" styleId="NBS_LAB332" title="If an isolate wasnt received at the state public health lab, specifies the reason the isolate wasnt received" onchange="ruleHideUnhNBS_LAB3326412();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_SPECFORW)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB333L" title="If an isolate wasnt received at the state public health lab, specifies the other (free-text) reason the isolate wasnt received.">
If Other, please specify:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB333)" size="20" maxlength="20" title="If an isolate wasnt received at the state public health lab, specifies the other (free-text) reason the isolate wasnt received." styleId="NBS_LAB333"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB334L" title="Specifies the date the isolate was received at the state public health lab">
If Yes, please specify date received in state public health lab:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB334)" maxlength="10" size="10" styleId="NBS_LAB334" onkeyup="DateMask(this,null,event)" title="Specifies the date the isolate was received at the state public health lab"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB334','NBS_LAB334Icon'); return false;" styleId="NBS_LAB334Icon" onkeypress="showCalendarEnterKey('NBS_LAB334','NBS_LAB334Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB335L" title="State public health lab isolate id number">
State public health lab isolate id number:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB335)" size="20" maxlength="20" title="State public health lab isolate id number" styleId="NBS_LAB335"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB336L" title="Indicates if case was confirmed at the state public health lab">
Was case confirmed at state public health lab?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB336)" styleId="NBS_LAB336" title="Indicates if case was confirmed at the state public health lab">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_5L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB363L" title="Indicates if the specimen or isolate was forwarded to the CDC for testing or confirmation.">
Was specimen or isolate forwarded to CDC for testing or confirmation?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB363)" styleId="NBS_LAB363" title="Indicates if the specimen or isolate was forwarded to the CDC for testing or confirmation.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_6L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB337L" title="Indicates if a PulseNet isolate was collected">
PulseNet Isolate?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB337)" styleId="NBS_LAB337" title="Indicates if a PulseNet isolate was collected" onchange="ruleHideUnhNBS_LAB3376398();ruleHideUnhNBS_LAB3386399();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB338L" title="Indicates if PulseNet isolate PFGE pattern was sent to central PulseNet database.">
Has isolate PFGE pattern been sent to central PulseNet database?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB338)" styleId="NBS_LAB338" title="Indicates if PulseNet isolate PFGE pattern was sent to central PulseNet database." onchange="ruleHideUnhNBS_LAB3386399();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB339L" title="PulseNet PFGE Designation Enzyme 1">
PulseNet PFGE Designation Enzyme 1:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB339)" size="20" maxlength="20" title="PulseNet PFGE Designation Enzyme 1" styleId="NBS_LAB339"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB340L" title="State Health Dept Lab PFGE Designation Enzyme 1">
State Health Dept Lab PFGE Designation Enzyme 1:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB340)" size="20" maxlength="20" title="State Health Dept Lab PFGE Designation Enzyme 1" styleId="NBS_LAB340"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB341L" title="PulseNet PFGE Designation Enzyme 2">
PulseNet PFGE Designation Enzyme 2:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB341)" size="20" maxlength="20" title="PulseNet PFGE Designation Enzyme 2" styleId="NBS_LAB341"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB342L" title="State Health Dept Lab PFGE Designation Enzyme 2">
State Health Dept Lab PFGE Designation Enzyme 2:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB342)" size="20" maxlength="20" title="State Health Dept Lab PFGE Designation Enzyme 2" styleId="NBS_LAB342"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB343L" title="PulseNet PFGE Designation Enzyme 3">
PulseNet PFGE Designation Enzyme 3:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB343)" size="20" maxlength="20" title="PulseNet PFGE Designation Enzyme 3" styleId="NBS_LAB343"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB344L" title="State Health Dept Lab PFGE Designation Enzyme 3">
State Health Dept Lab PFGE Designation Enzyme 3:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB344)" size="20" maxlength="20" title="State Health Dept Lab PFGE Designation Enzyme 3" styleId="NBS_LAB344"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_7L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB345L" title="Indicates if NARMS isolate was collected">
NARMS Isolate:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB345)" styleId="NBS_LAB345" title="Indicates if NARMS isolate was collected" onchange="ruleHideUnhNBS_LAB3456400();ruleHideUnhNBS_LAB3466401();ruleHideUnhNBS_LAB3466402();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB346L" title="Indicates if NARMs isolate was sent to NARMS">
Has isolate been sent to NARMS?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB346)" styleId="NBS_LAB346" title="Indicates if NARMs isolate was sent to NARMS" onchange="ruleHideUnhNBS_LAB3466402();ruleHideUnhNBS_LAB3466401();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB347L" title="If the NARMS isolate was not sent to NARMS, specifies the reason the NARMS isolate was not sent">
If an isolate was not sent to NARMS, what is the reason?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB347)" styleId="NBS_LAB347" title="If the NARMS isolate was not sent to NARMS, specifies the reason the NARMS isolate was not sent">
<nedss:optionsCollection property="codedValue(PHVSFB_ISOLATNO)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB348L" title="State-assigned NARMS ID number">
State-assigned NARMS ID number:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB348)" size="20" maxlength="20" title="State-assigned NARMS ID number" styleId="NBS_LAB348"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB349L" title="NARMS Isolate Expected Ship Date">
Expected Ship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB349)" maxlength="10" size="10" styleId="NBS_LAB349" onkeyup="DateMaskFuture(this,null,event)" title="NARMS Isolate Expected Ship Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS_LAB349','NBS_LAB349Icon'); return false;" styleId="NBS_LAB349Icon" onkeypress ="showCalendarFutureEnterKey('NBS_LAB349','NBS_LAB349Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB350L" title="NARMS Isolate Actual Ship Date">
Actual Ship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB350)" maxlength="10" size="10" styleId="NBS_LAB350" onkeyup="DateMask(this,null,event)" title="NARMS Isolate Actual Ship Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB350','NBS_LAB350Icon'); return false;" styleId="NBS_LAB350Icon" onkeypress="showCalendarEnterKey('NBS_LAB350','NBS_LAB350Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_8L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB351L" title="Indicates if EIP isolate was collected.">
EIP Isolate?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB351)" styleId="NBS_LAB351" title="Indicates if EIP isolate was collected." onchange="ruleHideUnhNBS_LAB3516403();ruleHideUnhNBS_LAB3526404();ruleHideUnhNBS_LAB3526408();;ruleHideUnhNBS_LAB3586406();;ruleHideUnhNBS_LAB3596405();ruleDCompNBS_LAB3616409();ruleDCompNBS_LAB3616410();ruleDCompNBS_LAB3626411();ruleEnDisNBS_LAB3536395();ruleHideUnhNBS_LAB3536407();ruleEnDisNBS_LAB3536395();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB352L" title="Indicates if the EIP isolate specimen was available for further EIP testing.">
Is this specimen available for further EIP testing?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB352)" styleId="NBS_LAB352" title="Indicates if the EIP isolate specimen was available for further EIP testing." onchange="ruleHideUnhNBS_LAB3526408();ruleHideUnhNBS_LAB3526404();;ruleHideUnhNBS_LAB3586406();;ruleHideUnhNBS_LAB3596405();ruleDCompNBS_LAB3616409();ruleDCompNBS_LAB3616410();ruleDCompNBS_LAB3626411();ruleEnDisNBS_LAB3536395();ruleHideUnhNBS_LAB3536407();ruleEnDisNBS_LAB3536395();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_ISOLATAV)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB353L" title="If the EIP isolate specimen was not available for further EIP testing, specifies the reason the EIP isolate specimen was not
available.">
If a specimen is not available for further EIP testing, what is the reason?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB353)" styleId="NBS_LAB353" title="If the EIP isolate specimen was not available for further EIP testing, specifies the reason the EIP isolate specimen was not
available." onchange="ruleHideUnhNBS_LAB3536407();ruleEnDisNBS_LAB3536395();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_SPECAVAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB354L" title="If the EIP isolate specimen was not available for further EIP testing, specifies the other (free-text) reason the EIP isolate
specimen was not available.">
If Other, please specify other reason why specimen is not available:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB354)" size="20" maxlength="20" title="If the EIP isolate specimen was not available for further EIP testing, specifies the other (free-text) reason the EIP isolate
specimen was not available." styleId="NBS_LAB354"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB355L" title="If the EIP isolate specimen was available for further EIP testing, indicates where the specimen will be shipped.">
If Yes, where will the specimen be shipped?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB355)" styleId="NBS_LAB355" title="If the EIP isolate specimen was available for further EIP testing, indicates where the specimen will be shipped.">
<nedss:optionsCollection property="codedValue(PHVSFB_CDCLABSH)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB356L" title="EIP isolate expected ship date.">
Expected Ship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB356)" maxlength="10" size="10" styleId="NBS_LAB356" onkeyup="DateMaskFuture(this,null,event)" title="EIP isolate expected ship date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS_LAB356','NBS_LAB356Icon'); return false;" styleId="NBS_LAB356Icon" onkeypress ="showCalendarFutureEnterKey('NBS_LAB356','NBS_LAB356Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB357L" title="EIP isolate actual ship date">
Actual Ship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB357)" maxlength="10" size="10" styleId="NBS_LAB357" onkeyup="DateMask(this,null,event)" title="EIP isolate actual ship date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB357','NBS_LAB357Icon'); return false;" styleId="NBS_LAB357Icon" onkeypress="showCalendarEnterKey('NBS_LAB357','NBS_LAB357Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB358L" title="Indicates if the EIP isolate specimen was requested for reshipment">
Was specimen requested for reshipment?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB358)" styleId="NBS_LAB358" title="Indicates if the EIP isolate specimen was requested for reshipment" onchange="ruleHideUnhNBS_LAB3586406();;ruleHideUnhNBS_LAB3596405();ruleDCompNBS_LAB3616409();ruleDCompNBS_LAB3616410();ruleDCompNBS_LAB3626411();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB359L" title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the reason">
If a specimen was requested for reshipment for further EIP testing, what is the reason?:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB359)" styleId="NBS_LAB359" title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the reason" onchange="ruleHideUnhNBS_LAB3596405();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_CONTAMIN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB360L" title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the other (free-text) reason">
If Other, please specify reason for reshipment:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS_LAB360)" size="20" maxlength="20" title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the other (free-text) reason" styleId="NBS_LAB360"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB361L" title="EIP isolate expected reship date">
Expected Reship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB361)" maxlength="10" size="10" styleId="NBS_LAB361" onkeyup="DateMaskFuture(this,null,event)" title="EIP isolate expected reship date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS_LAB361','NBS_LAB361Icon'); return false;" styleId="NBS_LAB361Icon" onkeypress ="showCalendarFutureEnterKey('NBS_LAB361','NBS_LAB361Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB362L" title="EIP isolate actual reship date">
Actual Reship Date:</span>
</td>
<td>
<html:text name="PageSubForm"  property="pageClientVO.answer(NBS_LAB362)" maxlength="10" size="10" styleId="NBS_LAB362" onkeyup="DateMask(this,null,event)" title="EIP isolate actual reship date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB362','NBS_LAB362Icon'); return false;" styleId="NBS_LAB362Icon" onkeypress="showCalendarEnterKey('NBS_LAB362','NBS_LAB362Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
