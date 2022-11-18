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
String tabId = "editLabReport";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Order Information","Test Results","Lab Report Comments","Other Information"};
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
<nedss:container id="NBS_UI_13" name="Facility and Provider Information" isHidden="F" classType="subSect" >

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS_LAB365">
<span style="color:#CC0000">*</span>
<span id="NBS_LAB365L" title="Organization as Reporting lab in Lab Test acts">
Reporting Facility:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB365Uid">
<span id="clearNBS_LAB365" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB365Uid">
<span id="clearNBS_LAB365">
</logic:notEmpty>
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS_LAB365">
<input type="button" class="Button" value="Clear/Reassign" id="NBS_LAB365CodeClearButton" onclick="clearOrganization('NBS_LAB365')"/>
</logic:notEqual>
</span>
<span id="NBS_LAB365SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB365Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS_LAB365Icon" onclick="getReportingOrg('NBS_LAB365');" />&nbsp; - OR - &nbsp;
<html:text styleClass="requiredInputField" property="pageClientVO.answer(NBS_LAB365)" styleId="NBS_LAB365Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS_LAB365Text','NBS_LAB365_qec_list')"
title="Organization as Reporting lab in Lab Test acts"/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS_LAB365CodeLookupButton" onclick="getDWROrganization('NBS_LAB365')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB365Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS_LAB365_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS_LAB365S">Reporting Facility Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB365Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB365Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS_LAB365Uid"/>
<span id="NBS_LAB365">${PageForm.attributeMap.NBS_LAB365SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS_LAB365Error"/>
</td> </tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS_LAB367">
<span id="NBS_LAB367L" title="Organization as Ordering Facility in Lab Test">
Ordering Facility:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB367Uid">
<span id="clearNBS_LAB367" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB367Uid">
<span id="clearNBS_LAB367">
</logic:notEmpty>
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS_LAB367">
<input type="button" class="Button" value="Clear/Reassign" id="NBS_LAB367CodeClearButton" onclick="clearOrganization('NBS_LAB367')"/>
</logic:notEqual>
</span>
<span id="NBS_LAB367SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB367Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS_LAB367Icon" onclick="getReportingOrg('NBS_LAB367');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS_LAB367)" styleId="NBS_LAB367Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS_LAB367Text','NBS_LAB367_qec_list')"
title="Organization as Ordering Facility in Lab Test"/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS_LAB367CodeLookupButton" onclick="getDWROrganization('NBS_LAB367')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB367Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS_LAB367_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS_LAB367S">Ordering Facility Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB367Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB367Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS_LAB367Uid"/>
<span id="NBS_LAB367">${PageForm.attributeMap.NBS_LAB367SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS_LAB367Error"/>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Ordering Facility same as Reporting Facility" id="NBS_LAB267L">
Same as Reporting Facility:</span>
</td>
<td>
<html:checkbox  name="PageForm" property="pageClientVO.answer(NBS_LAB267)" value="1"
onchange="populateParticipationFromOne(this,'NBS_LAB365','NBS_LAB367')"
title="Ordering Facility same as Reporting Facility"></html:checkbox>
</td>
</tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS_LAB366L" title="Person as Ordering Provider in Lab Test">
Ordering Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB366Uid">
<span id="clearNBS_LAB366" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB366Uid">
<span id="clearNBS_LAB366">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS_LAB366CodeClearButton" onclick="clearProvider('NBS_LAB366')"/>
</span>
<span id="NBS_LAB366SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB366Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS_LAB366Icon" onclick="getProvider('NBS_LAB366');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS_LAB366)" styleId="NBS_LAB366Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS_LAB366Text','NBS_LAB366_qec_list')"
title="Person as Ordering Provider in Lab Test"/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS_LAB366CodeLookupButton" onclick="getDWRProvider('NBS_LAB366')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB366Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS_LAB366_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS_LAB366S">Ordering Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS_LAB366Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS_LAB366Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS_LAB366Uid"/>
<span id="NBS_LAB366">${PageForm.attributeMap.NBS_LAB366SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS_LAB366Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Order Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="INV108L" title="The program area associated with the investigaiton condition.">
Program Area:</span>
</td>
<td>

<!--processing Program Area Coded Question-->
<logic:empty name="PageForm" property="attributeMap.ReadOnlyProgramArea"><html:select name="PageForm" property="pageClientVO.answer(INV108)" styleId="INV108" title="The program area associated with the investigaiton condition." onchange="clearFieldsAssociatedToProgramArea();codeLookupLaboratoryReport('entity-table-Org-ReportingOrganizationUID', document);">
<html:optionsCollection property="labProgramAreaList" value="key" label="value" /> </html:select></logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.ReadOnlyProgramArea"><nedss:view name="PageForm" property="pageClientVO.answer(INV108)" codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="PageForm" property="pageClientVO.answer(INV108)" styleId="INV108" /></logic:notEmpty>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="INV107L" title="The jurisdiction of the investigation.">
Jurisdiction:</span>
</td>
<td>

<!--processing Jurisdistion Coded Question-->
<logic:empty name="PageForm" property="attributeMap.ReadOnlyJursdiction"><html:select name="PageForm" property="pageClientVO.answer(INV107)" styleId="INV107" title="The jurisdiction of the investigation.">
<html:optionsCollection property="jurisdictionListWthUnknown" value="key" label="value" /> </html:select></logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="PageForm" property="pageClientVO.answer(INV107)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="PageForm" property="pageClientVO.answer(INV107)" styleId="INV107" /></logic:notEmpty>
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Should this record be shared with guests with program area and jurisdiction rights?" id="NBS012L">
Shared Indicator:</span>
</td>
<td>
<html:checkbox  name="PageForm" property="pageClientVO.answer(NBS012)" value="1"
title="Should this record be shared with guests with program area and jurisdiction rights?"></html:checkbox>
</td>
</tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB197L" title="Indicates the date/time that the lab released the lab report.">
Lab Report Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS_LAB197)" maxlength="10" size="10" styleId="NBS_LAB197" onkeyup="DateMask(this,null,event)" title="Indicates the date/time that the lab released the lab report."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB197','NBS_LAB197Icon'); return false;" styleId="NBS_LAB197Icon" onkeypress="showCalendarEnterKey('NBS_LAB197','NBS_LAB197Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="NBS_LAB201L" title="The date that the lab report was received by public health">
Date Received by Public Health:</span>
</td>
<td>
<html:text  name="PageForm" styleClass="requiredInputField" property="pageClientVO.answer(NBS_LAB201)" maxlength="10" size="10" styleId="NBS_LAB201" onkeyup="DateMask(this,null,event)" title="The date that the lab report was received by public health"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB201','NBS_LAB201Icon'); return false;" styleId="NBS_LAB201Icon" onkeypress="showCalendarEnterKey('NBS_LAB201','NBS_LAB201Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Pregnancy Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1786418();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS128)" size="2" maxlength="2"  title="Number of weeks pregnant at the time of diagnosis." styleId="NBS128" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,42)"/>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16" name="Ordered Test" isHidden="F" classType="subSect" >

<!--processing Coded with Search  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB112L" title="The ordered test.">
Ordered Test:</span>
</td>
<td>
<div style="float:left">
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB112)" styleId="NBS_LAB112" title="The ordered test.">
<nedss:optionsCollection property="codedValue(ORDERED_LAB_TEST)" value="key" label="value"/></html:select>
</div>
<span><input name="attributeMap.NBS_LAB112Code" id="NBS_LAB112CodeId" type="hidden" value=""></input>
<input name="attributeMap.NBS_LAB112DescriptionWithCode" id="NBS_LAB112DescriptionId" type="hidden" value=""></input>
<span title="The ordered test." id="NBS_LAB112Description"></span></span>
<input type="button" class="Button" value="Search"
id="NBS_LAB112Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch('NBS_LAB112');" />
<input type="button" class="Button" value="Clear" id="NBS_LAB112ClearButton" onclick="clearSingleSelectWithSearchButton('NBS_LAB112')"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB269L" title="Indicates the ordered test for the lab report; in most cases this is a standardized (LOINC) test name.">
Ordered Test Codes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB269)" styleId="NBS_LAB269" title="Indicates the ordered test for the lab report; in most cases this is a standardized (LOINC) test name.">
<nedss:optionsCollection property="codedValue(ORDERED_LAB_TEST)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB196L" title="Indicates the status of the lab result(s).">
Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB196)" styleId="NBS_LAB196" title="Indicates the status of the lab result(s).">
<nedss:optionsCollection property="codedValue(ACT_OBJ_ST)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB125L" title="A laboratory generated number that identifies the specimen related to this test.">
Accession Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB125)" size="25" maxlength="25" title="A laboratory generated number that identifies the specimen related to this test." styleId="LAB125"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB165L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB165)" styleId="LAB165" title="Anatomic site or specimen type from which positive lab specimen was collected.">
<nedss:optionsCollection property="codedValue(SPECMN_SRC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB166L" title="Indicates the physical location on the subject (patient) from which the specimen originated (e.g. Right Internal Jugular, Left Arm, Buttock, Right Eye, etc.).">
Specimen Site:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB166)" styleId="NBS_LAB166" title="Indicates the physical location on the subject (patient) from which the specimen originated (e.g. Right Internal Jugular, Left Arm, Buttock, Right Eye, etc.).">
<nedss:optionsCollection property="codedValue(ANATOMIC_SITE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date/Time:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="DateMask(this,null,event)" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB330L" title="The patient's status (hospitalized or outpatient) at the time of specimen collection">
Patient Status at Specimen Collection:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB330)" styleId="NBS_LAB330" title="The patient's status (hospitalized or outpatient) at the time of specimen collection">
<nedss:optionsCollection property="codedValue(PHVSFB_SPCMNPTSTATUS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Specimen details on ELR report." id="NBS_LAB262L">
Specimen Details:</span>
</td>
<td>
<span id="NBS_LAB262S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB262)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS_LAB262)" styleId="NBS_LAB262" />
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="RESULTED_TEST_CONTAINER" name="Resulted Test" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="RESULTED_TEST_CONTAINERerrorMessages">
<b> <a name="RESULTED_TEST_CONTAINERerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "RESULTED_TEST_CONTAINER"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyRESULTED_TEST_CONTAINER">
<tr id="patternRESULTED_TEST_CONTAINER" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewRESULTED_TEST_CONTAINER" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'RESULTED_TEST_CONTAINER');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editRESULTED_TEST_CONTAINER" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'RESULTED_TEST_CONTAINER');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteRESULTED_TEST_CONTAINER" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'RESULTED_TEST_CONTAINER','patternRESULTED_TEST_CONTAINER','questionbodyRESULTED_TEST_CONTAINER');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyRESULTED_TEST_CONTAINER">
<tr id="nopatternRESULTED_TEST_CONTAINER" class="odd" style="display:">
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

<!--processing Coded with Search  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldRESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB220L" title="User either selects a specific reporting lab or the generic local lab option. If a specific reporting lab that has tests mapped to LOINCs, the list will display that labs list of  tests for the selected Program Area. When the test name is not in the list">
Resulted Test:</span>
</td>
<td>
<div style="float:left">
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB220)" styleId="NBS_LAB220" title="User either selects a specific reporting lab or the generic local lab option. If a specific reporting lab that has tests mapped to LOINCs, the list will display that labs list of  tests for the selected Program Area. When the test name is not in the list" onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');showHideOrganism();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST)" value="key" label="value"/></html:select>
</div>
<span><input name="attributeMap.NBS_LAB220Code" id="NBS_LAB220CodeId" type="hidden" value=""></input>
<input name="attributeMap.NBS_LAB220DescriptionWithCode" id="NBS_LAB220DescriptionId" type="hidden" value=""></input>
<span title="User either selects a specific reporting lab or the generic local lab option. If a specific reporting lab that has tests mapped to LOINCs, the list will display that labs list of  tests for the selected Program Area. When the test name is not in the list" id="NBS_LAB220Description"></span></span>
<input type="button" class="Button" value="Search"
id="NBS_LAB220Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch('NBS_LAB220');" />
<input type="button" class="Button" value="Clear" id="NBS_LAB220ClearButton" onclick="clearSingleSelectWithSearchButton('NBS_LAB220')"/>
</td></tr>

<!--processing Coded with Search  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB280L" title="Coded result value text description, such as positive, detected. If Organism name is the coded result, an organism name will display in the result field.">
Coded Result:</span>
</td>
<td>
<div style="float:left">
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB280)" styleId="NBS_LAB280" title="Coded result value text description, such as positive, detected. If Organism name is the coded result, an organism name will display in the result field." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(CODED_LAB_RESULT)" value="key" label="value"/></html:select>
</div>
<span><input name="attributeMap.NBS_LAB280Code" id="NBS_LAB280CodeId" type="hidden" value=""></input>
<input name="attributeMap.NBS_LAB280DescriptionWithCode" id="NBS_LAB280DescriptionId" type="hidden" value=""></input>
<span title="Coded result value text description, such as positive, detected. If Organism name is the coded result, an organism name will display in the result field." id="NBS_LAB280Description"></span></span>
<input type="button" class="Button" value="Search"
id="NBS_LAB280Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch('NBS_LAB280');" />
<input type="button" class="Button" value="Clear" id="NBS_LAB280ClearButton" onclick="clearSingleSelectWithSearchButton('NBS_LAB280')"/>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB364L" title="Indicates the numeric value (quantitative result) for a lab result.">
Numeric Result:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB364)" size="35" maxlength="35" title="Indicates the numeric value (quantitative result) for a lab result." styleId="NBS_LAB364" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="LAB115L" title="Units of measure for the Quantitative Test Result Value">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB115)" styleId="LAB115" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB208L" title="Indicates textual result values for the lab (as opposed to coded or numeric values).">
Text Result:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS_LAB208)" styleId ="NBS_LAB208" onkeyup="checkTextAreaLength(this, 2000)" title="Indicates textual result values for the lab (as opposed to coded or numeric values)."/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB119L" title="Indicates the value on the low end of a expected range of results for the test.">
Reference Range From:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB119)" size="20" maxlength="20" title="Indicates the value on the low end of a expected range of results for the test." styleId="NBS_LAB119" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB120L" title="Indicates the value on the high end of a valid range of results for the test.">
Reference Range To:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB120)" size="20" maxlength="20" title="Indicates the value on the high end of a valid range of results for the test." styleId="NBS_LAB120" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS_LAB217L" title="Indicates the date the lab analyzed the specimen, Performing Facility and ID">
Performing Facility Details:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS_LAB217)" maxlength="10" size="10" styleId="NBS_LAB217" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');DateMask(this,null,event)" styleClass="RESULTED_TEST_CONTAINER" title="Indicates the date the lab analyzed the specimen, Performing Facility and ID"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB217','NBS_LAB217Icon'); unhideBatchImg('RESULTED_TEST_CONTAINER');return false;" styleId="NBS_LAB217Icon" onkeypress="showCalendarEnterKey('NBS_LAB217','NBS_LAB217Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB118L" title="Indicates a result that is not typical, as well as an indication of why it is not typical (e.g. Susceptible, Resistant, Normal, Above upper panic limits, below absolute low).">
Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB118)" styleId="NBS_LAB118" title="Indicates a result that is not typical, as well as an indication of why it is not typical (e.g. Susceptible, Resistant, Normal, Above upper panic limits, below absolute low)." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(OBS_INTRP)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB279L" title="Indicates the test method used (e.g. MIC).">
Result Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB279)" styleId="NBS_LAB279" title="Indicates the test method used (e.g. MIC)." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(OBSERVATION_METHOD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB207L" title="Indicates the status (degree of completion) of the overall lab report, (e.g. Final, Corrected, etc.).">
Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB207)" styleId="NBS_LAB207" title="Indicates the status (degree of completion) of the overall lab report, (e.g. Final, Corrected, etc.)." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(ACT_OBJ_ST)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB293L" title="Indicates the resulted test in most cases this is a standardized test (LOINC) test.">
Test Code:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB293)" styleId="NBS_LAB293" title="Indicates the resulted test in most cases this is a standardized test (LOINC) test." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(RESULTED_LAB_TEST)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="RESULTED_TEST_CONTAINER InputFieldLabel" id="NBS_LAB121L" title="Indicates the coded result value text description, (e.g., positive, detected, etc.)">
Result Code:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB121)" styleId="NBS_LAB121" title="Indicates the coded result value text description, (e.g., positive, detected, etc.)" onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');">
<nedss:optionsCollection property="codedValue(CODED_LAB_RESULT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB104L" title="Indicates free text comments having to do specifically with the lab test result">
Result Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS_LAB104)" styleId ="NBS_LAB104" onkeyup="checkTextAreaLength(this, 2000)" title="Indicates free text comments having to do specifically with the lab test result"/>
</td> </tr>

<!--processing Action Button-->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB222L" title="Added in NBS 6.0 to support Lab Template in Page Builder.">Susceptibilities:</span>
</td>
<td align="left">
<html:hidden property="attributeMap.NBS_LAB222Uid"/>
<input id="NBS_LAB222Button" onclick="OpenForm('OpenForm: SUS_LAB_SUSCEPTIBILITIES',this);" type="button" value="Manage Susceptibilities"/></td></tr>

<!--processing Action Button-->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB329L" title="Added in NBS 6.0 to support Lab Template in Page Builder.">Track Isolate:</span>
</td>
<td align="left">
<html:hidden property="attributeMap.NBS_LAB329Uid"/>
<input id="NBS_LAB329Button" onclick="OpenForm('OpenForm: ISO_LAB_TRACK_ISOLATES',this);" type="button" value="Manage Track Isolate"/></td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS457L" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically.">
Electronic Indicator 2:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS457)" styleId="NBS457" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically." onchange="unhideBatchImg('RESULTED_TEST_CONTAINER');ruleHideUnhNBS4576426();ruleHideUnhNBS4576422()">
<nedss:optionsCollection property="codedValue(DATA_SOURCE_TYPE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="It is use in linking the resulted test data to the isolate and susceptibility data. It identifies individual entries in resulted test block." id="NBS458L">
Parent UID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS458)" size="20" maxlength="20" title="It is use in linking the resulted test data to the isolate and susceptibility data. It identifies individual entries in resulted test block." styleId="NBS458" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="Used for linking the resulted test data to the isolate and susceptibility data" id="NBS459L">
Linking UID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS459)" size="20" maxlength="20" title="Used for linking the resulted test data to the isolate and susceptibility data" styleId="NBS459" onkeyup="unhideBatchImg('RESULTED_TEST_CONTAINER');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleRESULTED_TEST_CONTAINER">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgRESULTED_TEST_CONTAINERBatchAddFunction()) writeQuestion('RESULTED_TEST_CONTAINER','patternRESULTED_TEST_CONTAINER','questionbodyRESULTED_TEST_CONTAINER')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleRESULTED_TEST_CONTAINER">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgRESULTED_TEST_CONTAINERBatchAddFunction()) writeQuestion('RESULTED_TEST_CONTAINER','patternRESULTED_TEST_CONTAINER','questionbodyRESULTED_TEST_CONTAINER');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleRESULTED_TEST_CONTAINER"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgRESULTED_TEST_CONTAINERBatchAddFunction()) writeQuestion('RESULTED_TEST_CONTAINER','patternRESULTED_TEST_CONTAINER','questionbodyRESULTED_TEST_CONTAINER');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleRESULTED_TEST_CONTAINER"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('RESULTED_TEST_CONTAINER')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_21" name="Add Comment" isHidden="F" classType="subSect" >

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB214L" title="User has option to enter free text comments about a lab report">
User Report Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS_LAB214)" styleId ="NBS_LAB214" onkeyup="checkTextAreaLength(this, 1900)" title="User has option to enter free text comments about a lab report"/>
</td> </tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS_LAB214DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB214Date)" maxlength="10" size="10" styleId="NBS_LAB214Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB214Date','NBS_LAB214DateIcon');return false;" styleId="NBS_LAB214DateIcon" onkeypress="showCalendarEnterKey('NBS_LAB214Date','NBS_LAB214DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS_LAB214UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB214User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS_LAB214User"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Add Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS460L" title="User has option to enter free text comments about a lab report">
Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS460)" styleId ="NBS460" onkeyup="checkTextAreaLength(this, 2000)" title="User has option to enter free text comments about a lab report"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23" name="Other Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB265L" title="Indicates the volume of specimen that was collected as part of the test.">
Collection Volume:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB265)" size="100" maxlength="100" title="Indicates the volume of specimen that was collected as part of the test." styleId="NBS_LAB265"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB313L" title="Indicates the units associated with the amount of specimen collected for testing.">
Collection Volume Units:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB313)" size="100" maxlength="100" title="Indicates the units associated with the amount of specimen collected for testing." styleId="NBS_LAB313"/>
</td> </tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Display Clinical Information Comments for ELR." id="NBS_LAB261L">
Clinical Information:</span>
</td>
<td>
<span id="NBS_LAB261S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB261)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS_LAB261)" styleId="NBS_LAB261" />
</td>
</tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS_LAB124L" title="Indicates the clinical reason for ordering a test the symptoms/observations are text descriptions associated with ICD9 (International Classification of Disease codes).">
Reason for Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS_LAB124)" styleId="NBS_LAB124" title="Indicates the clinical reason for ordering a test the symptoms/observations are text descriptions associated with ICD9 (International Classification of Disease codes).">
<nedss:optionsCollection property="codedValue(LAB_REASON_FOR_STUDY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="The description of the Danger Code that displays for an ELR." id="NBS_LAB316L">
Danger Code:</span>
</td>
<td>
<span id="NBS_LAB316S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB316)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS_LAB316)" styleId="NBS_LAB316" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="The ID of the message from MessageIn" id="NBS_LOG101L">
Message Control ID:</span>
</td>
<td>
<span id="NBS_LOG101S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LOG101)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS_LOG101)" styleId="NBS_LOG101" />
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Participant(s)" isHidden="F" classType="subSect" >
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28" name="Retain Information" isHidden="F" classType="subSect" >

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Retain Patient for next entry" id="NBS_LAB223L">
Retain Patient for next entry:</span>
</td>
<td>
<html:checkbox  name="PageForm" property="pageClientVO.answer(NBS_LAB223)" value="1"
title="Retain Patient for next entry"></html:checkbox>
</td>
</tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Retain Reporting Facility for next entry" id="NBS_LAB224L">
Retain Reporting Facility for next entry:</span>
</td>
<td>
<html:checkbox  name="PageForm" property="pageClientVO.answer(NBS_LAB224)" value="1"
title="Retain Reporting Facility for next entry"></html:checkbox>
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_30" name="Associated Lab Document(s)" isHidden="F" classType="subSect" >
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
