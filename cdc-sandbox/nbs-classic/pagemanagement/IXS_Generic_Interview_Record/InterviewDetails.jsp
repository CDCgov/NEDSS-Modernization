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
String tabId = "editInterviewDetails";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Interview Details"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA24102" name="Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="IXS100L" title="The status of the interview.">
Interview Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(IXS100)" styleId="IXS100" title="The status of the interview.">
<nedss:optionsCollection property="codedValue(NBS_INTVW_STATUS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="IXS101L" title="The date of the interview.">
Date of Interview:</span>
</td>
<td>
<html:text  name="PageForm" styleClass="requiredInputField" property="pageClientVO.answer(IXS101)" maxlength="10" size="10" styleId="IXS101" onkeyup="DateMaskFuture(this,null,event)" title="The date of the interview."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('IXS101','IXS101Icon'); return false;" styleId="IXS101Icon" onkeypress ="showCalendarFutureEnterKey('IXS101','IXS101Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="IXS102L" title="The person performing the interview.">
Interviewer:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.IXS102Uid">
<span id="clearIXS102" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.IXS102Uid">
<span id="clearIXS102">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="IXS102CodeClearButton" onclick="clearProvider('IXS102')"/>
</span>
<span id="IXS102SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.IXS102Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="IXS102Icon" onclick="getProvider('IXS102');" />&nbsp; - OR - &nbsp;
<html:text styleClass="requiredInputField" property="pageClientVO.answer(IXS102)" styleId="IXS102Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('IXS102Text','IXS102_qec_list')"
title="The person performing the interview."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="IXS102CodeLookupButton" onclick="getDWRProvider('IXS102')"
<logic:notEmpty name="PageForm" property="attributeMap.IXS102Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="IXS102_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="IXS102S">Interviewer Selected: </td>
<logic:empty name="PageForm" property="attributeMap.IXS102Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.IXS102Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.IXS102Uid"/>
<span id="IXS102">${PageForm.attributeMap.IXS102SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="IXS102Error"/>
</td></tr>

<!--processing ReadOnly Select Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The role of the interviewee." id="IXS103L" >
Interviewee Role:</span></td><td>
<span id="IXS103" />
<nedss:view name="PageForm" property="pageClientVO.answer(IXS103)"
codeSetNm="NBS_INTVWEE_ROLE"/>
</td><td><html:hidden name="PageForm" property="pageClientVO.answer(IXS103)" styleId="IXS103cd" />
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="IXS104L" title="The subject of the interview (i.e., person interviewed).">
Interviewee:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.IXS104Uid">
<span id="clearIXS104" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.IXS104Uid">
<span id="clearIXS104">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="IXS104CodeClearButton" onclick="clearProvider('IXS104')"/>
</span>
<span id="IXS104SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.IXS104Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="IXS104Icon" onclick="getProvider('IXS104');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(IXS104)" styleId="IXS104Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('IXS104Text','IXS104_qec_list')"
title="The subject of the interview (i.e., person interviewed)."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="IXS104CodeLookupButton" onclick="getDWRProvider('IXS104')"
<logic:notEmpty name="PageForm" property="attributeMap.IXS104Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="IXS104_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" valign="top" id="IXS104S">Interviewee Selected: </td>
<logic:empty name="PageForm" property="attributeMap.IXS104Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.IXS104Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.IXS104Uid"/>
<span id="IXS104">${PageForm.attributeMap.IXS104SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="IXS104Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="IXS105L" title="The type of interview (e.g., initial interview)">
Interview Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(IXS105)" styleId="IXS105" title="The type of interview (e.g., initial interview)">
<nedss:optionsCollection property="codedValue(NBS_INTERVIEW_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="IXS106L" title="The location of the interview (e.g., phone, clinic, etc.).">
Interview Location:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(IXS106)" styleId="IXS106" title="The location of the interview (e.g., phone, clinic, etc.).">
<nedss:optionsCollection property="codedValue(NBS_INTVW_LOC)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA25100" name="Interview Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="GA25100errorMessages">
<b> <a name="GA25100errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "GA25100"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyGA25100">
<tr id="patternGA25100" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewGA25100" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'GA25100');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editGA25100" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'GA25100');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteGA25100" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'GA25100','patternGA25100','questionbodyGA25100');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyGA25100">
<tr id="nopatternGA25100" class="odd" style="display:">
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
<span class="InputFieldLabel" id="IXS111L" title="The interview notes that were recorded during the interview process.">
Interview Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(IXS111)" styleId ="IXS111" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('IXS111');unhideBatchImg('GA25100');" title="The interview notes that were recorded during the interview process."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="IXS111DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(IXS111Date)" maxlength="10" size="10" styleId="IXS111Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('IXS111Date','IXS111DateIcon');unhideBatchImg('GA25100');return false;" styleId="IXS111DateIcon" onkeypress="showCalendarEnterKey('IXS111Date','IXS111DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="IXS111UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(IXS111User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="IXS111User" onkeyup="unhideBatchImg('GA25100');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleGA25100">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgGA25100BatchAddFunction()) writeQuestion('GA25100','patternGA25100','questionbodyGA25100')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleGA25100">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgGA25100BatchAddFunction()) writeQuestion('GA25100','patternGA25100','questionbodyGA25100');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleGA25100"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgGA25100BatchAddFunction()) writeQuestion('GA25100','patternGA25100','questionbodyGA25100');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleGA25100"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('GA25100')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
