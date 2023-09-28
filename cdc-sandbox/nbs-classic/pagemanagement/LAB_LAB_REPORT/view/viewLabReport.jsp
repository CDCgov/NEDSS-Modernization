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
String tabId = "viewLabReport";
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

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span id="NBS_LAB365L" title="Organization as Reporting lab in Lab Test acts">
Reporting Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB365)"/>
<span id="NBS_LAB365">${PageForm.attributeMap.NBS_LAB365SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS_LAB367L" title="Organization as Ordering Facility in Lab Test">
Ordering Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB367)"/>
<span id="NBS_LAB367">${PageForm.attributeMap.NBS_LAB367SearchResult}</span>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span id="NBS_LAB267L" title="Ordering Facility same as Reporting Facility">
Same as Reporting Facility:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS_LAB267)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS_LAB267)" value="1">
No</logic:notEqual>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS_LAB366L" title="Person as Ordering Provider in Lab Test">
Ordering Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB366)"/>
<span id="NBS_LAB366">${PageForm.attributeMap.NBS_LAB366SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Order Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108L" >
Program Area:</span></td><td>
<span id="INV108" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107L" >
Jurisdiction:</span></td><td>
<span id="INV107" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span id="NBS012L" title="Should this record be shared with guests with program area and jurisdiction rights?">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
No</logic:notEqual>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicates the date/time that the lab released the lab report." id="NBS_LAB197L">Lab Report Date:</span>
</td><td>
<span id="NBS_LAB197"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB197)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The date that the lab report was received by public health" id="NBS_LAB201L">Date Received by Public Health:</span>
</td><td>
<span id="NBS_LAB201"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB201)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Assesses whether or not the patient is pregnant." id="INV178L" >
Pregnancy Status:</span></td><td>
<span id="INV178" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td><td>
<span id="NBS128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS128)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16" name="Ordered Test" isHidden="F" classType="subSect" >

<!--processing Coded With Search Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The ordered test." id="NBS_LAB112L" >
Ordered Test:</span></td><td>
<span id="NBS_LAB112" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB112)"
codeSetNm="ORDERED_LAB_TEST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the ordered test for the lab report; in most cases this is a standardized (LOINC) test name." id="NBS_LAB269L" >
Ordered Test Codes:</span></td><td>
<span id="NBS_LAB269" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB269)"
codeSetNm="ORDERED_LAB_TEST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the status of the lab result(s)." id="NBS_LAB196L" >
Status:</span></td><td>
<span id="NBS_LAB196" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB196)"
codeSetNm="ACT_OBJ_ST"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="A laboratory generated number that identifies the specimen related to this test." id="LAB125L">
Accession Number:</span>
</td>
<td>
<span id="LAB125"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB125)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165L" >
Specimen Source:</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="SPECMN_SRC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the physical location on the subject (patient) from which the specimen originated (e.g. Right Internal Jugular, Left Arm, Buttock, Right Eye, etc.)." id="NBS_LAB166L" >
Specimen Site:</span></td><td>
<span id="NBS_LAB166" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB166)"
codeSetNm="ANATOMIC_SITE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date/Time:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The patient's status (hospitalized or outpatient) at the time of specimen collection" id="NBS_LAB330L" >
Patient Status at Specimen Collection:</span></td><td>
<span id="NBS_LAB330" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB330)"
codeSetNm="PHVSFB_SPCMNPTSTATUS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specimen details on ELR report." id="NBS_LAB262L">
Specimen Details:</span>
</td>
<td>
<span id="NBS_LAB262"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB262)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="RESULTED_TEST_CONTAINER" name="Resulted Test" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyRESULTED_TEST_CONTAINER">
<tr id="patternRESULTED_TEST_CONTAINER" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'RESULTED_TEST_CONTAINER');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyRESULTED_TEST_CONTAINER">
<tr id="nopatternRESULTED_TEST_CONTAINER" class="odd" style="display:none">
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

<!--processing Coded With Search Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="User either selects a specific reporting lab or the generic local lab option. If a specific reporting lab that has tests mapped to LOINCs, the list will display that labs list of  tests for the selected Program Area. When the test name is not in the list" id="NBS_LAB220L" >
Resulted Test:</span></td><td>
<span id="NBS_LAB220" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB220)"
codeSetNm="LAB_TEST"/>
</td> </tr>

<!--processing Coded With Search Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Coded result value text description, such as positive, detected. If Organism name is the coded result, an organism name will display in the result field." id="NBS_LAB280L" >
Coded Result:</span></td><td>
<span id="NBS_LAB280" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB280)"
codeSetNm="CODED_LAB_RESULT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the numeric value (quantitative result) for a lab result." id="NBS_LAB364L">
Numeric Result:</span>
</td>
<td>
<span id="NBS_LAB364"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB364)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates textual result values for the lab (as opposed to coded or numeric values)." id="NBS_LAB208L">
Text Result:</span>
</td>
<td>
<span id="NBS_LAB208"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB208)"  />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the value on the low end of a expected range of results for the test." id="NBS_LAB119L">
Reference Range From:</span>
</td>
<td>
<span id="NBS_LAB119"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB119)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the value on the high end of a valid range of results for the test." id="NBS_LAB120L">
Reference Range To:</span>
</td>
<td>
<span id="NBS_LAB120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB120)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicates the date the lab analyzed the specimen, Performing Facility and ID" id="NBS_LAB217L">Performing Facility Details:</span>
</td><td>
<span id="NBS_LAB217"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates a result that is not typical, as well as an indication of why it is not typical (e.g. Susceptible, Resistant, Normal, Above upper panic limits, below absolute low)." id="NBS_LAB118L" >
Interpretation:</span></td><td>
<span id="NBS_LAB118" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB118)"
codeSetNm="OBS_INTRP"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the test method used (e.g. MIC)." id="NBS_LAB279L" >
Result Method:</span></td><td>
<span id="NBS_LAB279" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB279)"
codeSetNm="OBSERVATION_METHOD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the status (degree of completion) of the overall lab report, (e.g. Final, Corrected, etc.)." id="NBS_LAB207L" >
Status:</span></td><td>
<span id="NBS_LAB207" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB207)"
codeSetNm="ACT_OBJ_ST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the resulted test in most cases this is a standardized test (LOINC) test." id="NBS_LAB293L" >
Test Code:</span></td><td>
<span id="NBS_LAB293" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB293)"
codeSetNm="RESULTED_LAB_TEST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the coded result value text description, (e.g., positive, detected, etc.)" id="NBS_LAB121L" >
Result Code:</span></td><td>
<span id="NBS_LAB121" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB121)"
codeSetNm="CODED_LAB_RESULT"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates free text comments having to do specifically with the lab test result" id="NBS_LAB104L">
Result Comments:</span>
</td>
<td>
<span id="NBS_LAB104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB104)"  />
</td> </tr>

<!--processing Action Button-->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB222L" title="Added in NBS 6.0 to support Lab Template in Page Builder.">Susceptibilities:</span>
</td>
<td align="left">
<html:hidden property="attributeMap.NBS_LAB222Uid"/>
<input disabled="" id="NBS_LAB222Button" onclick="OpenForm('OpenForm: SUS_LAB_SUSCEPTIBILITIES',this);" type="button" value="Manage Susceptibilities"/></td></tr>

<!--processing Action Button-->
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB329L" title="Added in NBS 6.0 to support Lab Template in Page Builder.">Track Isolate:</span>
</td>
<td align="left">
<html:hidden property="attributeMap.NBS_LAB329Uid"/>
<input disabled="" id="NBS_LAB329Button" onclick="OpenForm('OpenForm: ISO_LAB_TRACK_ISOLATES',this);" type="button" value="Manage Track Isolate"/></td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="NBS457L" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically.">Electronic Indicator 2:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS457)" styleId="NBS457"><html:optionsCollection property="codedValue(DATA_SOURCE_TYPE)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Text Question-->
<!--Processing Hidden Text Question NBS459 in Lab-->
<tr style="display:none"><td class="fieldName">
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS459)" styleId="NBS459" />
</td>
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_21" name="Add Comment" isHidden="F" classType="subSect" >

<!-- ########### EDITABLE SUB SECTION ON VIEW SCREEN###########  -->
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_21errorMessages">
<b> <a name="NBS_UI_21errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_21"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_21">
<tr id="patternNBS_UI_21" class="odd" style="display:none">
<td style="width:3%;text-align:center;">&nbsp;</td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_21" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClickedForEditableBatch(this.id,'NBS_UI_21');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td>
<td style="width:3%;text-align:center;">&nbsp;</td>
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
<tbody id="questionbodyNBS_UI_21">
<tr id="nopatternNBS_UI_21" class="odd" style="display:">
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

<!--processing editable TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS_LAB214L" title="User has option to enter free text comments about a lab report">
User Report Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS_LAB214)" styleId ="NBS_LAB214" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS_LAB214');unhideBatchImg('NBS_UI_21');" title="User has option to enter free text comments about a lab report"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS_LAB214DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB214Date)" maxlength="10" size="10" styleId="NBS_LAB214Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS_LAB214Date','NBS_LAB214DateIcon');unhideBatchImg('NBS_UI_21');return false;" styleId="NBS_LAB214DateIcon" onkeypress="showCalendarEnterKey('NBS_LAB214Date','NBS_LAB214DateIcon',event);" ></html:img>
</td>
</tr>

<!--processing Hidden Text Question-->
<tr style="display:none">
<td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS_LAB214UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS_LAB214User)" size="30" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS_LAB214User" onkeyup="unhideBatchImg('NBS_UI_21');"/>
</td>
</tr>
<tr id="AddButtonToggleNBS_UI_21">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_21BatchAddFunction()) writeQuestion('NBS_UI_21','patternNBS_UI_21','questionbodyNBS_UI_21')"/>&nbsp;&nbsp;
&nbsp;
</td>
<tr id="AddNewButtonToggleNBS_UI_21"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_21')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Add Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="User has option to enter free text comments about a lab report" id="NBS460L">
Comments:</span>
</td>
<td>
<span id="NBS460"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS460)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23" name="Other Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the volume of specimen that was collected as part of the test." id="NBS_LAB265L">
Collection Volume:</span>
</td>
<td>
<span id="NBS_LAB265"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB265)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the units associated with the amount of specimen collected for testing." id="NBS_LAB313L">
Collection Volume Units:</span>
</td>
<td>
<span id="NBS_LAB313"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB313)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Display Clinical Information Comments for ELR." id="NBS_LAB261L">
Clinical Information:</span>
</td>
<td>
<span id="NBS_LAB261"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB261)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the clinical reason for ordering a test the symptoms/observations are text descriptions associated with ICD9 (International Classification of Disease codes)." id="NBS_LAB124L" >
Reason for Test:</span></td><td>
<span id="NBS_LAB124" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB124)"
codeSetNm="LAB_REASON_FOR_STUDY"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The description of the Danger Code that displays for an ELR." id="NBS_LAB316L">
Danger Code:</span>
</td>
<td>
<span id="NBS_LAB316"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LAB316)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The ID of the message from MessageIn" id="NBS_LOG101L">
Message Control ID:</span>
</td>
<td>
<span id="NBS_LOG101"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS_LOG101)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Participant(s)" isHidden="F" classType="subSect" >

<!--processing Static Participant List-->
<tr><td style="padding:0.5em;" colspan="2"><display:table name="participantList" class="dtTable"><display:column property="title" title="Role" style="width:25%;" /><display:column property="detail" title="Detail" style="width:75%;"/><display:setProperty name="basic.empty.showtable" value="true"/></display:table></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28" name="Retain Information" isHidden="F" classType="subSect" >

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span id="NBS_LAB223L" title="Retain Patient for next entry">
Retain Patient for next entry:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS_LAB223)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS_LAB223)" value="1">
No</logic:notEqual>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span id="NBS_LAB224L" title="Retain Reporting Facility for next entry">
Retain Reporting Facility for next entry:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS_LAB224)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS_LAB224)" value="1">
No</logic:notEqual>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_30" name="Associated Lab Document(s)" isHidden="F" classType="subSect" >

<!--processing Original Electronic Document List-->
<tr><td style="padding:0.5em;" colspan="2"><display:table name="origDocList" class="dtTable"><display:column property="viewLink" title="Add Time" style="width:50%;" /><display:column property="versionNbr" title="Version" style="width:50%;"/><display:setProperty name="basic.empty.showtable" value="true"/></display:table></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
