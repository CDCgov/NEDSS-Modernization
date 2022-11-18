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
String tabId = "editContactRecord";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Record","Disposition","Contact Record Comments"};
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
<nedss:container id="GA17101" name="Contact Record Security" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="CON134L" title="The geographic area responsible for managing public health activities including intervention, prevention and surveillance for health event associated with a particular geographic area such as county or city, associated with an event.  A jurisdiction is re">
Jurisdiction:</span>
</td>
<td>

<!--processing Jurisdistion Coded Question-->
<logic:empty name="contactTracingForm" property="attributeMap.ReadOnlyJursdiction"><html:select name="contactTracingForm" property="cTContactClientVO.answer(CON134)" styleId="CON134" title="The geographic area responsible for managing public health activities including intervention, prevention and surveillance for health event associated with a particular geographic area such as county or city, associated with an event.  A jurisdiction is re">
<html:optionsCollection property="jurisdictionList" value="key" label="value" /> </html:select></logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON134)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="contactTracingForm" property="cTContactClientVO.answer(CON134)"/></logic:notEmpty>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON135L" title="The functional area accountable for managing the public health response and surveillance for health events associated with a particular condition(s).  For example, the TB program area works to control and prevent tuberculosis.">
Program Area:</span>
</td>
<td>

<!--processing Program Area Coded Question - read only-->
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON135)" codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="contactTracingForm" property="cTContactClientVO.answer(CON135)" />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="This field indicates whether or not the record should be shared with all users who have guest privileges for the Program Area/Jurisdiction." id="CON136L">
Shared Indicator:</span>
</td>
<td>
<html:checkbox  name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1"
title="This field indicates whether or not the record should be shared with all users who have guest privileges for the Program Area/Jurisdiction."></html:checkbox>
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17103" name="Contact Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="CON141L" title="Indicate if the relationship being recorded is a direct exposure/relationship between this patient and Contact or an exposure/relationship between the Contact and another known infected patient.">
Relationship with Patient/Other infected Patient?:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON141)" styleId="CON141" title="Indicate if the relationship being recorded is a direct exposure/relationship between this patient and Contact or an exposure/relationship between the Contact and another known infected patient." onchange="ruleEnDisCON1417980();ruleRequireIfCON1417981();ruleRequireIfCON1417982();conStdNamedBetweenUpdate();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(CONTACT_REL_WITH)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Contact Type Participation Question-->
<tr>
<td class="fieldName">
<span id="CON142L" title="Search for patients infected with the same condition.">
Other Infected Patient:</span> </td>
<td>
<logic:empty name="contactTracingForm" property="attributeMap.CON142Uid">
<span id="clearCON142" class="none">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON142Uid">
<span id="clearCON142">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="CON142CodeClearButton" onclick="clearOtherContact('CON142')"/>
</span>
<span id="CON142SearchControls"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON142Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="CON142Icon" onclick="getOtherPersonPopUp('CON142');"&nbsp;
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON142Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="CON142_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="CON142S">Other Infected Patient Selected: </td>
<logic:empty name="contactTracingForm" property="attributeMap.CON142Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON142Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.CON142Uid"/>
<span id="CON142">${contactTracingForm.attributeMap.CON142SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="CON142Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON143L" title="The interview at which the contact was named. May also indicate if the contact record was initiated w/out interview">
Named:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON143)" styleId="CON143" title="The interview at which the contact was named. May also indicate if the contact record was initiated w/out interview" onchange="ruleRequireIfCON1437983();conStdNamedBetweenUpdate();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON115L" title="The current health status of the contact.">
Health Status:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON115)" styleId="CON115" title="The current health status of the contact.">
<nedss:optionsCollection property="codedValue(NBS_HEALTH_STATUS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS155L" title="The approximate or specific height of the patient.">
Height:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS155)" size="15" maxlength="15" title="The approximate or specific height of the patient." styleId="NBS155"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS156L" title="The approximate or specific weight or body type of the patient.">
Size/Build:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS156)" size="15" maxlength="15" title="The approximate or specific weight or body type of the patient." styleId="NBS156"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS157L" title="The description of the patients hair, including color, length, and/or style.">
Hair:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS157)" size="15" maxlength="15" title="The description of the patients hair, including color, length, and/or style." styleId="NBS157"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS158L" title="The approximate or specific skine tone/hue of the patient.">
Complexion:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS158)" size="15" maxlength="15" title="The approximate or specific skine tone/hue of the patient." styleId="NBS158"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS159L" title="Any additional demographic information (e.g., tattoos, etc).">
Other Identifying Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(NBS159)" styleId ="NBS159" onkeyup="checkTextAreaLength(this, 2000)" title="Any additional demographic information (e.g., tattoos, etc)."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17104" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON144L" title="Document the appropriate identifier for the specific type of partner, social contact and/or Associate.">
Referral Basis:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON144)" styleId="CON144" title="Document the appropriate identifier for the specific type of partner, social contact and/or Associate." onchange="ruleEnDisCON1447986();ruleEnDisCON1447985();ruleEnDisCON1447984();ruleRequireIfCON1447987();conStdCongenitalHideNotifiable();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS118L" title="The date of the first sexual exposure to the original/index patient.">
First Sexual Exposure:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(NBS118)" maxlength="10" size="10" styleId="NBS118" onkeyup="DateMask(this,null,event)" title="The date of the first sexual exposure to the original/index patient."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS118','NBS118Icon'); return false;" styleId="NBS118Icon" onkeypress="showCalendarEnterKey('NBS118','NBS118Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS119L" title="The frequency (number) of sexual exposure(s)  between the first and last (most recent) exposure(s).">
Sexual Frequency:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS119)" size="15" maxlength="15" title="The frequency (number) of sexual exposure(s)  between the first and last (most recent) exposure(s)." styleId="NBS119"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS120L" title="The date of the last sexual exposure to the original/index patient.">
Last Sexual Exposure:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(NBS120)" maxlength="10" size="10" styleId="NBS120" onkeyup="DateMask(this,null,event)" title="The date of the last sexual exposure to the original/index patient."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS120','NBS120Icon'); return false;" styleId="NBS120Icon" onkeypress="showCalendarEnterKey('NBS120','NBS120Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS121L" title="The date of the first needle-sharing exposure to the original/index patient.">
First Needle-Sharing Exposure:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(NBS121)" maxlength="10" size="10" styleId="NBS121" onkeyup="DateMask(this,null,event)" title="The date of the first needle-sharing exposure to the original/index patient."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS121','NBS121Icon'); return false;" styleId="NBS121Icon" onkeypress="showCalendarEnterKey('NBS121','NBS121Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS122L" title="The frequency (number) of needle-sharing exposure(s) between the first and last (most recent) exposure(s).">
Needle-Sharing Frequency:</span>
</td>
<td>
<html:text name="contactTracingForm" property="cTContactClientVO.answer(NBS122)" size="15" maxlength="15" title="The frequency (number) of needle-sharing exposure(s) between the first and last (most recent) exposure(s)." styleId="NBS122"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS123L" title="The date of the last needle-sharing exposure to the original/index patient.">
Last Needle-Sharing Exposure:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(NBS123)" maxlength="10" size="10" styleId="NBS123" onkeyup="DateMask(this,null,event)" title="The date of the last needle-sharing exposure to the original/index patient."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS123','NBS123Icon'); return false;" styleId="NBS123Icon" onkeypress="showCalendarEnterKey('NBS123','NBS123Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON103L" title="The relationship the contact has with the subject of the investigation or other known infected patient.">
Relationship:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON103)" styleId="CON103" title="The relationship the contact has with the subject of the investigation or other known infected patient.">
<nedss:optionsCollection property="codedValue(NBS_RELATIONSHIP)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS125L" title="Is this contact the original patient's spouse?">
OP Spouse:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(NBS125)" styleId="NBS125" title="Is this contact the original patient's spouse?">
<nedss:optionsCollection property="codedValue(OP_SPOUSE_IND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS126L" title="Did this contact meet the original patient via the internet?">
Met OP via Internet:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(NBS126)" styleId="NBS126" title="Did this contact meet the original patient via the internet?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS127L" title="During the course of the interview, did the DIS elicit internet information about the contact? If Yes, Internet Outcome response will be required in Investigation started for the Contact">
Internet Info Elicited:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(NBS127)" styleId="NBS127" title="During the course of the interview, did the DIS elicit internet information about the contact? If Yes, Internet Outcome response will be required in Investigation started for the Contact">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA25101" name="Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="CON145L" title="The action to be taken for this contact record (e.g., intitiate a new investigation associate to existing investigation, no follow-up reason)">
Processing Decision:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON145)" styleId="CON145" title="The action to be taken for this contact record (e.g., intitiate a new investigation associate to existing investigation, no follow-up reason)" onchange="conStdUpdateDispositionSection();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(STD_CONTACT_RCD_PROCESSING_DECISION)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON146L" title="The date the contact was initiated for follow-up.">
Initiate Follow-up Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON146)" maxlength="10" size="10" styleId="CON146" onkeyup="DateMask(this,null,event)" title="The date the contact was initiated for follow-up."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON146','CON146Icon'); return false;" styleId="CON146Icon" onkeypress="showCalendarEnterKey('CON146','CON146Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="CON137L" title="The investigator assigned to this contact record.">
Investigator:</span> </td>
<td>
<logic:empty name="contactTracingForm" property="attributeMap.CON137Uid">
<span id="clearCON137" class="none">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON137Uid">
<span id="clearCON137">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="CON137CodeClearButton" onclick="clearProvider('CON137')"/>
</span>
<span id="CON137SearchControls"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON137Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="CON137Icon" onclick="getProvider('CON137');" />&nbsp; - OR - &nbsp;
<html:text property="cTContactClientVO.answer(CON137)" styleId="CON137Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('CON137Text','CON137_qec_list')"
title="The investigator assigned to this contact record."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="CON137CodeLookupButton" onclick="getDWRProvider('CON137')"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON137Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="CON137_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="CON137S">Investigator Selected: </td>
<logic:empty name="contactTracingForm" property="attributeMap.CON137Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON137Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.CON137Uid"/>
<span id="CON137">${contactTracingForm.attributeMap.CON137SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="CON137Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON138L" title="The date the investigator was assigned to this contact record.">
Date Assigned:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON138)" maxlength="10" size="10" styleId="CON138" onkeyup="DateMask(this,null,event)" title="The date the investigator was assigned to this contact record."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON138','CON138Icon'); return false;" styleId="CON138Icon" onkeypress="showCalendarEnterKey('CON138','CON138Icon',event)"></html:img>
</td> </tr>

<!--processing ReadOnly Select Coded Question-->
<tr>
<td class="fieldName">
<span title="The disposition (or outcome) of this contact record." id="CON114L" >
Disposition:</span></td><td>
<span id="CON114" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON114)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STDHIV"/>
</td><td><html:hidden name="contactTracingForm" property="cTContactClientVO.answer(CON114)" styleId="CON114cd" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON140L" title="The date that the contact record was dispositioned.">
Disposition Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON140)" maxlength="10" size="10" styleId="CON140" onkeyup="DateMask(this,null,event)" title="The date that the contact record was dispositioned."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON140','CON140Icon'); return false;" styleId="CON140Icon" onkeypress="showCalendarEnterKey('CON140','CON140Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="CON147L" title="The person that placed the disposition (or outcome) for this contact record.">
Dispositioned By:</span> </td>
<td>
<logic:empty name="contactTracingForm" property="attributeMap.CON147Uid">
<span id="clearCON147" class="none">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON147Uid">
<span id="clearCON147">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="CON147CodeClearButton" onclick="clearProvider('CON147')"/>
</span>
<span id="CON147SearchControls"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON147Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="CON147Icon" onclick="getProvider('CON147');" />&nbsp; - OR - &nbsp;
<html:text property="cTContactClientVO.answer(CON147)" styleId="CON147Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('CON147Text','CON147_qec_list')"
title="The person that placed the disposition (or outcome) for this contact record."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="CON147CodeLookupButton" onclick="getDWRProvider('CON147')"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON147Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="CON147_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="CON147S">Dispositioned By Selected: </td>
<logic:empty name="contactTracingForm" property="attributeMap.CON147Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON147Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.CON147Uid"/>
<span id="CON147">${contactTracingForm.attributeMap.CON147SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="CON147Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS135L" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
Source/Spread:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(NBS135)" styleId="NBS135" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
<nedss:optionsCollection property="codedValue(SOURCE_SPREAD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA28101" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="CON133L" title="General comments about the contact record that may not have been captured in other notes fields.">
General Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="contactTracingForm" property="cTContactClientVO.answer(CON133)" styleId ="CON133" onkeyup="checkMaxLength(this)" title="General comments about the contact record that may not have been captured in other notes fields."/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
