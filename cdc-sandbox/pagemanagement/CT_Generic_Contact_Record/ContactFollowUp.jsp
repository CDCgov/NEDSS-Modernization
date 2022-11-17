<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
<!--##Contract Record Business Object##-->
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
String tabId = "editContactFollowUp";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Follow Up"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17108" name="Sign and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON128L" title="Indication of whether the contact has/had any signs/symptoms for this condition.">
Were there any signs/symptoms for this illness?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON128)" styleId="CON128" title="Indication of whether the contact has/had any signs/symptoms for this condition." onchange="ruleEnDisCON1287356();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON129L" title="The onset date of the symptom(s); i.e. when did the symptom first appear.">
Symptom Onset Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON129)" maxlength="10" size="10" styleId="CON129" onkeyup="DateMask(this,null,event)" title="The onset date of the symptom(s); i.e. when did the symptom first appear."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON129','CON129Icon'); return false;" styleId="CON129Icon" onkeypress="showCalendarEnterKey('CON129','CON129Icon',event)"></html:img>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="CON130L" title="Notes pertinent to the signs and symptoms.">
Signs &amp; Symptoms Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(CON130)" styleId ="CON130" onkeyup="checkMaxLength(this)" title="Notes pertinent to the signs and symptoms."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17109" name="Risk Factors" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON131L" title="Indication of whether the contact has any risk factors for this condition.">
Were there any risk factors for this illness?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON131)" styleId="CON131" title="Indication of whether the contact has any risk factors for this condition.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="CON132L" title="Notes pertinent to the risk factors.">
Risk Factor Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(CON132)" styleId ="CON132" onkeyup="checkMaxLength(this)" title="Notes pertinent to the risk factors."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17110" name="Testing and Evaluation" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON117L" title="Indication of whether the contact has been tested and/or evaluated for this condition.">
Was testing/evaluation completed for this illness?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON117)" styleId="CON117" title="Indication of whether the contact has been tested and/or evaluated for this condition." onchange="ruleEnDisCON1177357();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON118L" title="The date that the contact was evaluated.">
Date of Evaluation:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON118)" maxlength="10" size="10" styleId="CON118" onkeyup="DateMask(this,null,event)" title="The date that the contact was evaluated."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON118','CON118Icon'); return false;" styleId="CON118Icon" onkeypress="showCalendarEnterKey('CON118','CON118Icon',event)"></html:img>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="CON119L" title="A textual description of the fndings from the evaluation.">
Evaluation Findings:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(CON119)" styleId ="CON119" onkeyup="checkMaxLength(this)" title="A textual description of the fndings from the evaluation."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17111" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON120L" title="Indication of whether the contact has been treated for this condition.">
Was treatment initiated for this illness?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON120)" styleId="CON120" title="Indication of whether the contact has been treated for this condition." onchange="ruleEnDisCON1207359();ruleEnDisCON1207358();ruleEnDisCON1237360();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON121L" title="The date the treatment was started.">
Treatment Start Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON121)" maxlength="10" size="10" styleId="CON121" onkeyup="DateMask(this,null,event)" title="The date the treatment was started."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON121','CON121Icon'); return false;" styleId="CON121Icon" onkeypress="showCalendarEnterKey('CON121','CON121Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON122L" title="If treatment was not started, the reason the treatment was not started.">
Reason Treatment Not Started:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON122)" styleId="CON122" title="If treatment was not started, the reason the treatment was not started.">
<nedss:optionsCollection property="codedValue(NBS_NO_TRTMNT_REAS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON123L" title="Indication of whether the treatment for this condition was completed.">
Was treatment completed?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON123)" styleId="CON123" title="Indication of whether the treatment for this condition was completed." onchange="ruleEnDisCON1237360();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON124L" title="The date the treatment was ended.">
Treatment End Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON124)" maxlength="10" size="10" styleId="CON124" onkeyup="DateMask(this,null,event)" title="The date the treatment was ended."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON124','CON124Icon'); return false;" styleId="CON124Icon" onkeypress="showCalendarEnterKey('CON124','CON124Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON125L" title="If treatment was not completed, the reason the treatment was not completed.">
Reason Treatment Not Completed:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON125)" styleId="CON125" title="If treatment was not completed, the reason the treatment was not completed.">
<nedss:optionsCollection property="codedValue(NBS_NO_TRTMNT_REAS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="CON126L" title="Notes pertinent to the treatment.">
Treatment Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(CON126)" styleId ="CON126" onkeyup="checkMaxLength(this)" title="Notes pertinent to the treatment."/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
