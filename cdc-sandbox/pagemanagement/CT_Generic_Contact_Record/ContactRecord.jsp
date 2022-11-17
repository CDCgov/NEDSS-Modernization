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
String [] sectionNames  = {"Contact Record"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
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
<span style="color:#CC0000">*</span>
<span title="This field indicates whether or not the record should be shared with all users who have guest privileges for the Program Area/Jurisdiction." id="CON136L">
Shared Indicator:</span>
</td>
<td>
<html:checkbox styleClass="requiredInputField" name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1"
title="This field indicates whether or not the record should be shared with all users who have guest privileges for the Program Area/Jurisdiction."></html:checkbox>
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17102" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON139L" title="The status of this contact record (e.g., open, closed).">
Status:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON139)" styleId="CON139" title="The status of this contact record (e.g., open, closed).">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON112L" title="The priority or importance assigned to tracking the contact.">
Priority:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON112)" styleId="CON112" title="The priority or importance assigned to tracking the contact.">
<nedss:optionsCollection property="codedValue(NBS_PRIORITY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON127L" title="An identifier given to all individuals within an epi network (a network of related individuals potentially involved in the transmission of a disease/illness).">
Group/Lot ID:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON127)" styleId="CON127" title="An identifier given to all individuals within an epi network (a network of related individuals potentially involved in the transmission of a disease/illness).">
<nedss:optionsCollection property="codedValue(NBS_GROUP_NM)" value="key" label="value" /></html:select>
</td></tr>

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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON114L" title="The disposition (or outcome) of this contact record.">
Disposition:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON114)" styleId="CON114" title="The disposition (or outcome) of this contact record." onchange="ruleEnDisCON1147361();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(NBS_DISPO)" value="key" label="value" /></html:select>
</td></tr>

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17103" name="Contact Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="CON101L" title="The date that the contact was named within this investigation.">
Date Named:</span>
</td>
<td>
<html:text  name="contactTracingForm" styleClass="requiredInputField" property="cTContactClientVO.answer(CON101)" maxlength="10" size="10" styleId="CON101" onkeyup="DateMask(this,null,event)" title="The date that the contact was named within this investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON101','CON101Icon'); return false;" styleId="CON101Icon" onkeypress="showCalendarEnterKey('CON101','CON101Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="CON103L" title="The relationship the contact has with the subject of the investigation.">
Relationship:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON103)" styleId="CON103" title="The relationship the contact has with the subject of the investigation.">
<nedss:optionsCollection property="codedValue(NBS_RELATIONSHIP)" value="key" label="value" /></html:select>
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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17104" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="CON104L" title="The type of exposure the contact had with the subject of the investigation.">
Exposure Type:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON104)" styleId="CON104" title="The type of exposure the contact had with the subject of the investigation.">
<nedss:optionsCollection property="codedValue(NBS_EXPOSURE_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="CON105L" title="The type of site (location) where the exopsure occurred.">
Exposure Site Type:</span>
</td>
<td>
<html:select name="contactTracingForm" property="cTContactClientVO.answer(CON105)" styleId="CON105" title="The type of site (location) where the exopsure occurred.">
<nedss:optionsCollection property="codedValue(NBS_EXPOSURE_LOC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<logic:notEqual name="contactTracingForm" property="attributeMap.readOnlyParticipant" value="CON106">
<span id="CON106L" title="The site (location) where the exposure occurred.">
Exposure Site:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="contactTracingForm" property="attributeMap.CON106Uid">
<span id="clearCON106" class="none">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON106Uid">
<span id="clearCON106">
</logic:notEmpty>
<logic:notEqual name="contactTracingForm" property="attributeMap.readOnlyParticipant" value="CON106">
<input type="button" class="Button" value="Clear/Reassign" id="CON106CodeClearButton" onclick="clearOrganization('CON106')"/>
</logic:notEqual>
</span>
<span id="CON106SearchControls"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON106Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="CON106Icon" onclick="getReportingOrg('CON106');" />&nbsp; - OR - &nbsp;
<html:text property="cTContactClientVO.answer(CON106)" styleId="CON106Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('CON106Text','CON106_qec_list')"
title="The site (location) where the exposure occurred."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="CON106CodeLookupButton" onclick="getDWROrganization('CON106')"
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON106Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="CON106_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="CON106S">Exposure Site Selected: </td>
<logic:empty name="contactTracingForm" property="attributeMap.CON106Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="contactTracingForm" property="attributeMap.CON106Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.CON106Uid"/>
<span id="CON106">${contactTracingForm.attributeMap.CON106SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="CON106Error"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON107L" title="The date on which the contact was first exposed to the subject of the investigation.">
First Exposure Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON107)" maxlength="10" size="10" styleId="CON107" onkeyup="DateMask(this,null,event)" title="The date on which the contact was first exposed to the subject of the investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON107','CON107Icon'); return false;" styleId="CON107Icon" onkeypress="showCalendarEnterKey('CON107','CON107Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="CON108L" title="The date on which the contact was last exposed to the subject of the investigation.">
Last Exposure Date:</span>
</td>
<td>
<html:text name="contactTracingForm"  property="cTContactClientVO.answer(CON108)" maxlength="10" size="10" styleId="CON108" onkeyup="DateMask(this,null,event)" title="The date on which the contact was last exposed to the subject of the investigation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON108','CON108Icon'); return false;" styleId="CON108Icon" onkeypress="showCalendarEnterKey('CON108','CON108Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17105" name="Contact Record Comments" isHidden="F" classType="subSect" >

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
