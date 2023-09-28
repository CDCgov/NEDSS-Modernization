<!-- ### DMB: BEGIN JSP VIEW PAGE GENERATE ###- - -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%
Map map = new HashMap();
if(request.getAttribute("SubSecStructureMap") != null){
// String watemplateUid="1000879";
// map = util.getBatchMap(new Long(watemplateUid));
map =(Map)request.getAttribute("SubSecStructureMap");
}%>
<%
String tabId = "viewCaseManagement";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Case Numbers","Initial Follow-up","Surveillance","Notification of Exposure Information","Field Follow-up Information","Interview Case Assignment","Case Closure"};
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

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Unique field record identifier." id="NBS160L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS191)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." id="INV200L">
Legacy Case ID:</span>
</td>
<td>
<span id="INV200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV200)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS139L" title="The investigator assigning the initial follow-up.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS139)"/>
<span id="NBS139">${PageForm.attributeMap.NBS139SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Initial Follow-up action." id="NBS140L" >
Initial Follow-Up:</span></td><td>
<span id="NBS140" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS140)"
codeSetNm="STD_NBS_PROCESSING_DECISION_ALL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the inital follow-up was identified as closed." id="NBS141L">Date Closed:</span>
</td><td>
<span id="NBS141"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS141)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Initiate for Internet follow-up?" id="NBS142L" >
Internet Follow-Up:</span></td><td>
<span id="NBS142" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS142)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If applicable, enter the specific clinic code identifying the initiating clinic." id="NBS144L">
Clinic Code:</span>
</td>
<td>
<span id="NBS144"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS144)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS145L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS145)"/>
<span id="NBS145">${PageForm.attributeMap.NBS145SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date surveillance follow-up is assigned." id="NBS146L">Date Assigned:</span>
</td><td>
<span id="NBS146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date surveillance follow-up is completed." id="NBS147L">Date Closed:</span>
</td><td>
<span id="NBS147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS147)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the contact with the provider was successful or not." id="NBS148L" >
Provider Contact:</span></td><td>
<span id="NBS148" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS148)"
codeSetNm="PRVDR_CONTACT_OUTCOME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The reporting provider's reason for examing the patient." id="NBS149L" >
Exam Reason:</span></td><td>
<span id="NBS149" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS149)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The reporting provider's diagnosis." id="NBS150L" >
Reporting Provider Diagnosis (Surveillance):</span></td><td>
<span id="NBS150" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS150)"
codeSetNm="PRVDR_DIAGNOSIS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." id="NBS151L" >
Patient Follow-Up:</span></td><td>
<span id="NBS151" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS151)"
codeSetNm="SURVEILLANCE_PATIENT_FOLLOWUP"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18" name="Surveillance Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="patternNBS_INV_STD_UI_18" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_18');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_18">
<tr id="nopatternNBS_INV_STD_UI_18" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes for surveillance activities (e.g., type of information needed, additional comments.)" id="NBS152L">
Surveillance Notes:</span>
</td>
<td>
<span id="NBS152"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS152)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_20" name="Patient Notification" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="For field follow-up, is patient eligible for notification of exposure?" id="NBS143L" >
Patient Eligible for Notification of Exposure:</span></td><td>
<span id="NBS143" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS143)"
codeSetNm="NOTIFIABLE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The method agreed upon by the patient and investigator for notifying the partner(s) and clusters of potential HIV exposure." id="NBS167L" >
Notification Plan:</span></td><td>
<span id="NBS167" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS167)"
codeSetNm="NOTIFICATION_PLAN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The notification method by which field follow-up patients are brought to examination, brought to treatment, and/or notified of exposure." id="NBS177L" >
Actual Referral Type:</span></td><td>
<span id="NBS177" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS177)"
codeSetNm="NOTIFICATION_ACTUAL_METHOD_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS161L" title="The investigator assigned to field follow-up activities.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS161)"/>
<span id="NBS161">${PageForm.attributeMap.NBS161SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the investigator is assigned to field follow-up activities." id="NBS162L">Date Assigned:</span>
</td><td>
<span id="NBS162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS162)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS163L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS163)"/>
<span id="NBS163">${PageForm.attributeMap.NBS163SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS164)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The reporting provider's reason for examing the patient." id="NBS165L" >
Exam Reason:</span></td><td>
<span id="NBS165" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS165)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The reporting provider's diagnosis." id="NBS166L" >
Reporting Provider Diagnosis (Field Follow-up):</span></td><td>
<span id="NBS166" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS166)"
codeSetNm="PRVDR_DIAGNOSIS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Do you expect the patient to come in for examination?" id="NBS168L" >
Expected In:</span></td><td>
<span id="NBS168" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS168)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the patient is expected to come in for examination." id="NBS169L">Expected In Date:</span>
</td><td>
<span id="NBS169"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS169)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the patient was examined as a result of field activities." id="NBS170L">Exam Date:</span>
</td><td>
<span id="NBS170"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS170)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS171L" title="The provider who performed the exam.">
Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS171)"/>
<span id="NBS171">${PageForm.attributeMap.NBS171SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS172L" title="The facility at which the exam was performed.">
Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS172)"/>
<span id="NBS172">${PageForm.attributeMap.NBS172SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The disposition of the field follow-up activities." id="NBS173L" >
Disposition:</span></td><td>
<span id="NBS173" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS173)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="When the disposition was determined as relates to exam or treatment situation." id="NBS174L">Disposition Date:</span>
</td><td>
<span id="NBS174"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS174)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS175L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS175)"/>
<span id="NBS175">${PageForm.attributeMap.NBS175SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS176L" title="The supervisor who should review the field record disposition.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS176)"/>
<span id="NBS176">${PageForm.attributeMap.NBS176SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The outcome of internet based activities." id="NBS178L" >
Internet Outcome:</span></td><td>
<span id="NBS178" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS178)"
codeSetNm="INTERNET_FOLLOWUP_OUTCOME"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The name of the area where the out-of-jurisdiction Field Follow-up is sent." id="NBS179L" >
OOJ Agency FR Sent To:</span></td><td>
<span id="NBS179" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS179)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Field record number from initiating or receiving jurisdiction." id="NBS180L">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<span id="NBS180"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS180)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" id="NBS181L">OOJ Due Date from Receiving Area:</span>
</td><td>
<span id="NBS181"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS181)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The outcome of the OOJ jurisdiction field activities." id="NBS182L" >
OOJ Outcome from Receiving Area:</span></td><td>
<span id="NBS182" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS182)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26" name="Field Follow-Up Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="patternNBS_INV_STD_UI_26" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_26');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_26">
<tr id="nopatternNBS_INV_STD_UI_26" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Note text." id="NBS185L">
Note:</span>
</td>
<td>
<span id="NBS185"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS185)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="patternNBS_INV_STD_UI_27" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_27');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_27">
<tr id="nopatternNBS_INV_STD_UI_27" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Note text" id="NBS268L">
Note:</span>
</td>
<td>
<span id="NBS268"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS268)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_29" name="Interview Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS186L" title="The investigator assigned to perform interview(s).">
Interviewer:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS186)"/>
<span id="NBS186">${PageForm.attributeMap.NBS186SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date assigned for interview." id="NBS187L">Date Assigned:</span>
</td><td>
<span id="NBS187"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS187)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS188L" title="The investigator originally assigned for interview.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS188)"/>
<span id="NBS188">${PageForm.attributeMap.NBS188SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of initial assignment for case assignment." id="NBS189L">Initial Assignment Date:</span>
</td><td>
<span id="NBS189"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS189)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS190L" title="The supervisor who should review the case follow-up closure.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS190)"/>
<span id="NBS190">${PageForm.attributeMap.NBS190SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the status of interviewing the patient of this investigation." id="NBS192L" >
Patient Interview Status:</span></td><td>
<span id="NBS192" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS192)"
codeSetNm="PAT_INTVW_STATUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_30" name="Interview/Investigation Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_30"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_30">
<tr id="patternNBS_INV_STD_UI_30" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_30');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_30">
<tr id="nopatternNBS_INV_STD_UI_30" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Note text." id="NBS195L">
Note:</span>
</td>
<td>
<span id="NBS195"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS195)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Investigation may not be closed while interview status is awaiting or investigation is pending supervisor approval of field record closure. Also all contact records identified in this investigation must have a disposition.</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the case follow-up is closed." id="NBS196L">Date Closed:</span>
</td><td>
<span id="NBS196"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS196)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS197L" title="The investigator who closed out the case follow-up.">
Closed By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS197)"/>
<span id="NBS197">${PageForm.attributeMap.NBS197SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether or not the patient was in medical care at the time of the case close date." id="NBS444L" >
Care Status at Case Close Date:</span></td><td>
<span id="NBS444" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS444)"
codeSetNm="NBS_CARE_STATUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34" name="Supervisory Review/Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="patternNBS_INV_STD_UI_34" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_34');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_34">
<tr id="nopatternNBS_INV_STD_UI_34" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Note text." id="NBS200L">
Note:</span>
</td>
<td>
<span id="NBS200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS200)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
