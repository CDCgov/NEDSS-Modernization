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
String tabId = "editCaseManagement";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Case Numbers","Initial Follow-up","Surveillance","Field Follow-up Information","Case Closure"};
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
<nedss:container id="NBS_INV_STD_UI_12" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Unique field record identifier." id="NBS160L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS160)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS160)" styleId="NBS160" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS191)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS191)" styleId="NBS191" />
</td>
</tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV200L" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here.">
Legacy Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV200)" size="25" maxlength="25" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139L" title="The investigator assigning the initial follow-up.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS139Uid">
<span id="clearNBS139" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
<span id="clearNBS139">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS139CodeClearButton" onclick="clearProvider('NBS139')"/>
</span>
<span id="NBS139SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS139Icon" onclick="getProvider('NBS139');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS139)" styleId="NBS139Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS139Text','NBS139_qec_list')"
title="The investigator assigning the initial follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS139CodeLookupButton" onclick="getDWRProvider('NBS139')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS139_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS139S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS139Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS139Uid"/>
<span id="NBS139">${PageForm.attributeMap.NBS139SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS139Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS140L" title="Initial Follow-up action.">
Initial Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS140)" styleId="NBS140" title="Initial Follow-up action." onchange="stdFixedRuleInitialFollowupEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(STD_NBS_PROCESSING_DECISION_ALL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS141L" title="The date the inital follow-up was identified as closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS141)" maxlength="10" size="10" styleId="NBS141" onkeyup="DateMask(this,null,event)" onblur="stdInitialFollowupDateClosedEntered()" onchange="stdInitialFollowupDateClosedEntered()" title="The date the inital follow-up was identified as closed."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS141','NBS141Icon'); return false;" styleId="NBS141Icon" onkeypress="showCalendarEnterKey('NBS141','NBS141Icon',event)"></html:img>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS142L" title="Initiate for Internet follow-up?">
Internet Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS142)" styleId="NBS142" title="Initiate for Internet follow-up?">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS144L" title="If applicable, enter the specific clinic code identifying the initiating clinic.">
Clinic Code:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS144)" size="8" maxlength="8" title="If applicable, enter the specific clinic code identifying the initiating clinic." styleId="NBS144"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS145Uid">
<span id="clearNBS145" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
<span id="clearNBS145">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS145CodeClearButton" onclick="clearProvider('NBS145')"/>
</span>
<span id="NBS145SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS145Icon" onclick="getProvider('NBS145');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS145)" styleId="NBS145Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS145Text','NBS145_qec_list')"
title="The investigator assigned for surveillance follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS145CodeLookupButton" onclick="getDWRProvider('NBS145')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS145_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS145S">Assigned To Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS145Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS145Uid"/>
<span id="NBS145">${PageForm.attributeMap.NBS145SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS145Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS146L" title="The date surveillance follow-up is assigned.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS146)" maxlength="10" size="10" styleId="NBS146" onkeyup="DateMaskFuture(this,null,event)" title="The date surveillance follow-up is assigned."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS146','NBS146Icon'); return false;" styleId="NBS146Icon" onkeypress ="showCalendarFutureEnterKey('NBS146','NBS146Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS147L" title="The date surveillance follow-up is completed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS147)" maxlength="10" size="10" styleId="NBS147" onkeyup="DateMask(this,null,event)" onblur="stdSurveillanceDateClosedEntered()" onchange="stdSurveillanceDateClosedEntered()" title="The date surveillance follow-up is completed."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS147','NBS147Icon'); return false;" styleId="NBS147Icon" onkeypress="showCalendarEnterKey('NBS147','NBS147Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS148L" title="Indicate if the contact with the provider was successful or not.">
Provider Contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS148)" styleId="NBS148" title="Indicate if the contact with the provider was successful or not.">
<nedss:optionsCollection property="codedValue(PRVDR_CONTACT_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS149L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS149)" styleId="NBS149" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS150L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Surveillance):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS150)" styleId="NBS150" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS151L" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason.">
Patient Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS151)" styleId="NBS151" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." onchange="stdFixedRuleSurveillancePatientFollowupEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SURVEILLANCE_PATIENT_FOLLOWUP)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18" name="Surveillance Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_18errorMessages">
<b> <a name="NBS_INV_STD_UI_18errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_18"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="patternNBS_INV_STD_UI_18" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_18" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_18');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_18" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_18');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_18" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="nopatternNBS_INV_STD_UI_18" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS152L" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)">
Surveillance Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS152)" styleId ="NBS152" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS152');unhideBatchImg('NBS_INV_STD_UI_18');" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS152DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS152Date)" maxlength="10" size="10" styleId="NBS152Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS152Date','NBS152DateIcon');unhideBatchImg('NBS_INV_STD_UI_18');return false;" styleId="NBS152DateIcon" onkeypress="showCalendarEnterKey('NBS152Date','NBS152DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS152UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS152User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS152User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_18');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_18"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_18"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_18')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161L" title="The investigator assigned to field follow-up activities.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS161Uid">
<span id="clearNBS161" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
<span id="clearNBS161">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS161CodeClearButton" onclick="clearProvider('NBS161')"/>
</span>
<span id="NBS161SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS161Icon" onclick="getProvider('NBS161');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS161)" styleId="NBS161Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS161Text','NBS161_qec_list')"
title="The investigator assigned to field follow-up activities."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS161CodeLookupButton" onclick="getDWRProvider('NBS161')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS161_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS161S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS161Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS161Uid"/>
<span id="NBS161">${PageForm.attributeMap.NBS161SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS161Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS162L" title="The date the investigator is assigned to field follow-up activities.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS162)" maxlength="10" size="10" styleId="NBS162" onkeyup="DateMaskFuture(this,null,event)" title="The date the investigator is assigned to field follow-up activities."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS162','NBS162Icon'); return false;" styleId="NBS162Icon" onkeypress ="showCalendarFutureEnterKey('NBS162','NBS162Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS163Uid">
<span id="clearNBS163" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
<span id="clearNBS163">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS163CodeClearButton" onclick="clearProvider('NBS163')"/>
</span>
<span id="NBS163SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS163Icon" onclick="getProvider('NBS163');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS163)" styleId="NBS163Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS163Text','NBS163_qec_list')"
title="The investigator originally assigned to field follow-up activities."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS163CodeLookupButton" onclick="getDWRProvider('NBS163')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS163_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS163S">Initially Assigned Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS163Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS163Uid"/>
<span id="NBS163">${PageForm.attributeMap.NBS163SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS163Error"/>
</td></tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS164)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS164)" styleId="NBS164" />
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS165L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS165)" styleId="NBS165" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS166L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Field Follow-up):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS166)" styleId="NBS166" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS168L" title="Do you expect the patient to come in for examination?">
Expected In:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS168)" styleId="NBS168" title="Do you expect the patient to come in for examination?" onchange="ruleEnDisNBS1687288();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS169L" title="The date the patient is expected to come in for examination.">
Expected In Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS169)" maxlength="10" size="10" styleId="NBS169" onkeyup="DateMaskFuture(this,null,event)" title="The date the patient is expected to come in for examination."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS169','NBS169Icon'); return false;" styleId="NBS169Icon" onkeypress ="showCalendarFutureEnterKey('NBS169','NBS169Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS170L" title="The date the patient was examined as a result of field activities.">
Exam Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS170)" maxlength="10" size="10" styleId="NBS170" onkeyup="DateMaskFuture(this,null,event)" title="The date the patient was examined as a result of field activities."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS170','NBS170Icon'); return false;" styleId="NBS170Icon" onkeypress ="showCalendarFutureEnterKey('NBS170','NBS170Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171L" title="The provider who performed the exam.">
Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS171Uid">
<span id="clearNBS171" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
<span id="clearNBS171">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS171CodeClearButton" onclick="clearProvider('NBS171')"/>
</span>
<span id="NBS171SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS171Icon" onclick="getProvider('NBS171');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS171)" styleId="NBS171Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS171Text','NBS171_qec_list')"
title="The provider who performed the exam."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS171CodeLookupButton" onclick="getDWRProvider('NBS171')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS171_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS171S">Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS171Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS171Uid"/>
<span id="NBS171">${PageForm.attributeMap.NBS171SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS171Error"/>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS172">
<span id="NBS172L" title="The facility at which the exam was performed.">
Facility:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS172Uid">
<span id="clearNBS172" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
<span id="clearNBS172">
</logic:notEmpty>
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS172">
<input type="button" class="Button" value="Clear/Reassign" id="NBS172CodeClearButton" onclick="clearOrganization('NBS172')"/>
</logic:notEqual>
</span>
<span id="NBS172SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS172Icon" onclick="getReportingOrg('NBS172');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS172)" styleId="NBS172Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS172Text','NBS172_qec_list')"
title="The facility at which the exam was performed."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS172CodeLookupButton" onclick="getDWROrganization('NBS172')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS172_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS172S">Facility Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS172Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS172Uid"/>
<span id="NBS172">${PageForm.attributeMap.NBS172SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS172Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS173L" title="The disposition of the field follow-up activities.">
Disposition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS173)" styleId="NBS173" title="The disposition of the field follow-up activities." onchange="ruleEnDisNBS1737285();stdFixedRuleFieldFollowupDispositionEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS174L" title="When the disposition was determined as relates to exam or treatment situation.">
Disposition Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS174)" maxlength="10" size="10" styleId="NBS174" onkeyup="DateMask(this,null,event)" onblur="stdFieldFollowupDispositionDateEntry()" onchange="stdFieldFollowupDispositionDateEntry()" title="When the disposition was determined as relates to exam or treatment situation."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS174','NBS174Icon'); return false;" styleId="NBS174Icon" onkeypress="showCalendarEnterKey('NBS174','NBS174Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS175Uid">
<span id="clearNBS175" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
<span id="clearNBS175">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS175CodeClearButton" onclick="clearProvider('NBS175')"/>
</span>
<span id="NBS175SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS175Icon" onclick="getProvider('NBS175');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS175)" styleId="NBS175Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS175Text','NBS175_qec_list')"
title="The person who brought the field record/activities to final disposition."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS175CodeLookupButton" onclick="getDWRProvider('NBS175')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS175_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS175S">Dispositioned By Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS175Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS175Uid"/>
<span id="NBS175">${PageForm.attributeMap.NBS175SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS175Error"/>
</td></tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176L" title="The supervisor who should review the field record disposition.">
Supervisor:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS176Uid">
<span id="clearNBS176" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
<span id="clearNBS176">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS176CodeClearButton" onclick="clearProvider('NBS176')"/>
</span>
<span id="NBS176SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS176Icon" onclick="getProvider('NBS176');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS176)" styleId="NBS176Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS176Text','NBS176_qec_list')"
title="The supervisor who should review the field record disposition."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS176CodeLookupButton" onclick="getDWRProvider('NBS176')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS176_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS176S">Supervisor Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS176Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS176Uid"/>
<span id="NBS176">${PageForm.attributeMap.NBS176SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS176Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS392L" title="Enter the investigator is unable to disposition the case">
Reason Unable to Disposition Case:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS392)" styleId="NBS392" title="Enter the investigator is unable to disposition the case">
<nedss:optionsCollection property="codedValue(REASON_NOT_DISPO_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS178L" title="The outcome of internet based activities.">
Internet Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS178)" styleId="NBS178" title="The outcome of internet based activities.">
<nedss:optionsCollection property="codedValue(INTERNET_FOLLOWUP_OUTCOME)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS179L" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
OOJ Agency FR Sent To:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS179)" styleId="NBS179" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
<nedss:optionsCollection property="codedValue(OOJ_AGENCY_LOCAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS180L" title="Field record number from initiating or receiving jurisdiction.">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS180)" size="10" maxlength="10" title="Field record number from initiating or receiving jurisdiction." styleId="NBS180"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS181L" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)">
OOJ Due Date from Receiving Area:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS181)" maxlength="10" size="10" styleId="NBS181" onkeyup="DateMaskFuture(this,null,event)" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS181','NBS181Icon'); return false;" styleId="NBS181Icon" onkeypress ="showCalendarFutureEnterKey('NBS181','NBS181Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS182L" title="The outcome of the OOJ jurisdiction field activities.">
OOJ Outcome from Receiving Area:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS182)" styleId="NBS182" title="The outcome of the OOJ jurisdiction field activities.">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26" name="Field Follow-Up Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_26errorMessages">
<b> <a name="NBS_INV_STD_UI_26errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_26"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="patternNBS_INV_STD_UI_26" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_26" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_26');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_26" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_26');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_26" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="nopatternNBS_INV_STD_UI_26" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS185L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS185)" styleId ="NBS185" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS185');unhideBatchImg('NBS_INV_STD_UI_26');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS185DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS185Date)" maxlength="10" size="10" styleId="NBS185Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS185Date','NBS185DateIcon');unhideBatchImg('NBS_INV_STD_UI_26');return false;" styleId="NBS185DateIcon" onkeypress="showCalendarEnterKey('NBS185Date','NBS185DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS185UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS185User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS185User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_26');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_26"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_26"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_26')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_27errorMessages">
<b> <a name="NBS_INV_STD_UI_27errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_27"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="patternNBS_INV_STD_UI_27" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_27" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_27');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_27" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_27');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_27" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="nopatternNBS_INV_STD_UI_27" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS268L" title="Note text">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS268)" styleId ="NBS268" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS268');unhideBatchImg('NBS_INV_STD_UI_27');" title="Note text"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS268DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS268Date)" maxlength="10" size="10" styleId="NBS268Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS268Date','NBS268DateIcon');unhideBatchImg('NBS_INV_STD_UI_27');return false;" styleId="NBS268DateIcon" onkeypress="showCalendarEnterKey('NBS268Date','NBS268DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS268UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS268User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS268User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_27');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_27"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_27"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_27')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS196L" title="The date the case follow-up is closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS196)" maxlength="10" size="10" styleId="NBS196" onkeyup="DateMask(this,null,event)" onblur="stdCaseClosureDateClosedEntered()" onchange="stdCaseClosureDateClosedEntered()" title="The date the case follow-up is closed."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS196','NBS196Icon'); return false;" styleId="NBS196Icon" onkeypress="showCalendarEnterKey('NBS196','NBS196Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197L" title="The investigator who closed out the case follow-up.">
Closed By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS197Uid">
<span id="clearNBS197" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
<span id="clearNBS197">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS197CodeClearButton" onclick="clearProvider('NBS197')"/>
</span>
<span id="NBS197SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS197Icon" onclick="getProvider('NBS197');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS197)" styleId="NBS197Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS197Text','NBS197_qec_list')"
title="The investigator who closed out the case follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS197CodeLookupButton" onclick="getDWRProvider('NBS197')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS197_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="NBS197S">Closed By Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS197Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS197Uid"/>
<span id="NBS197">${PageForm.attributeMap.NBS197SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS197Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34" name="Supervisory Review/Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_34errorMessages">
<b> <a name="NBS_INV_STD_UI_34errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_34"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="patternNBS_INV_STD_UI_34" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_34" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_34');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_34" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_34');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_34" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="nopatternNBS_INV_STD_UI_34" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS200L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS200)" styleId ="NBS200" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS200');unhideBatchImg('NBS_INV_STD_UI_34');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS200DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS200Date)" maxlength="10" size="10" styleId="NBS200Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS200Date','NBS200DateIcon');unhideBatchImg('NBS_INV_STD_UI_34');return false;" styleId="NBS200DateIcon" onkeypress="showCalendarEnterKey('NBS200Date','NBS200DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS200UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS200User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS200User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_34');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_34"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_34"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_34')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
