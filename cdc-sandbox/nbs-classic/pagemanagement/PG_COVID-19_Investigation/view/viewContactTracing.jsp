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
String tabId = "viewContactTracing";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Investigation","Retired Questions"};
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
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056L">Infectious Period From:</span>
</td><td>
<span id="NBS056"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057L">Infectious Period To:</span>
</td><td>
<span id="NBS057"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The status of the contact investigation." id="NBS058L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25002" name="Retired Questions" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Report Date of Person Under Investigation (PUI) to CDC" id="NBS549L">Report Date of PUI to CDC:</span>
</td><td>
<span id="NBS549"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS549)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?" id="NBS541L" >
Patient history of being in a healthcare facility (as a patient, worker or visitor) in China?:</span></td><td>
<span id="NBS541" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS541)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Type of healthcare contact with another lab-confirmed case-patient." id="NBS544L" >
Type of healthcare contact:</span></td><td>
<span id="NBS544" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS544)"
codeSetNm="HC_CONTACT_TYPE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology." id="NBS558L" >
Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology:</span></td><td>
<span id="NBS558" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS558)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS556L" title="Did the patient travel to any high-risk locations?">
Did the patient travel to any high-risk locations:</span></td><td>
<span id="NBS556" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS556)"
codeSetNm="HIGH_RISK_TRAVEL_LOC_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Did the patient travel to any high-risk locations?" id="NBS556OthL">Other Did the patient travel to any high-risk locations:</span></td>
<td> <span id="NBS556Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS556Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02." id="NBS554L">
Source patient case ID:</span>
</td>
<td>
<span id="NBS554"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS554)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date the case was reported to CDC." id="NBS663L">Report Date of Case to CDC:</span>
</td><td>
<span id="NBS663"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS663)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA36000" name="Retired Questions Do Not Use" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Questions in this section have been replaced by other standard questions. These versions of the questions should not be used going forward, but are on the page to maintain any data previously collected.</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the patient has any tribal affiliation, enter the Indian Tribe name." id="67884_7L">
Tribe Name(s) (Retired):</span>
</td>
<td>
<span id="67884_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(67884_7)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?" id="54899_0L">
If yes, specify which language - Retired:</span>
</td>
<td>
<span id="54899_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(54899_0)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify the type of workplace setting" id="NBS686L">
If yes, specify workplace setting (Retired):</span>
</td>
<td>
<span id="NBS686"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS686)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify the type of animal with which the patient had contact." id="FDD_Q_32_TXTL">
Specify Type of Animal (Retired):</span>
</td>
<td>
<span id="FDD_Q_32_TXT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_32_TXT)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?" id="NBS688L" >
School/University/Childcare Center Exposure - Retired:</span></td><td>
<span id="NBS688" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS688)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have chills or rigors?" id="43724002L" >
Chills or Rigors - Retired:</span></td><td>
<span id="43724002" />
<nedss:view name="PageForm" property="pageClientVO.answer(43724002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?" id="NBS675L" >
New Olfactory and Taste Disorder - Retired:</span></td><td>
<span id="NBS675" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS675)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS777L" title="Indicate all the countries of travel in the last 14 days (Multi-Select)">
Travel Country (Multi Select):</span></td><td>
<span id="NBS777" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS777)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS778L" title="Indicate all the states of travel in the last 14 days (Multi-Select)">
Travel State (Multi-Select):</span></td><td>
<span id="NBS778" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS778)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA22003" name="Respiratory Diagnostic Testing Retired" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Influenza A Rapid Ag Result" id="80382_5L" >
Influenza A Rapid Ag:</span></td><td>
<span id="80382_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(80382_5)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Influenza B Rapid Ag Result" id="80383_3L" >
Influenza B Rapid Ag:</span></td><td>
<span id="80383_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(80383_3)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Influenza A PCR Result" id="34487_9L" >
Influenza A PCR:</span></td><td>
<span id="34487_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(34487_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Influenza B PCR Result" id="40982_1L" >
Influenza B PCR:</span></td><td>
<span id="40982_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(40982_1)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Respiratory Syncytial Virus (RSV) Result" id="6415009L" >
RSV:</span></td><td>
<span id="6415009" />
<nedss:view name="PageForm" property="pageClientVO.answer(6415009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="H. metapneumovirus Result" id="416730002L" >
H. metapneumovirus:</span></td><td>
<span id="416730002" />
<nedss:view name="PageForm" property="pageClientVO.answer(416730002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Parainfluenza (1-4) Result" id="41505_9L" >
Parainfluenza (1-4):</span></td><td>
<span id="41505_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(41505_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Adenovirus Result" id="74871001L" >
Adenovirus:</span></td><td>
<span id="74871001" />
<nedss:view name="PageForm" property="pageClientVO.answer(74871001)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Rhinovirus/Enterovirus Result" id="69239002L" >
Rhinovirus/enterovirus:</span></td><td>
<span id="69239002" />
<nedss:view name="PageForm" property="pageClientVO.answer(69239002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Coronavirus (OC43, 229E, HKU1, NL63) Result" id="84101006L" >
Coronavirus (OC43, 229E, HKU1, NL63):</span></td><td>
<span id="84101006" />
<nedss:view name="PageForm" property="pageClientVO.answer(84101006)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="M. pneumoniae Result" id="58720004L" >
M. pneumoniae:</span></td><td>
<span id="58720004" />
<nedss:view name="PageForm" property="pageClientVO.answer(58720004)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="C. pneumoniae Result" id="103514009L" >
C. pneumoniae:</span></td><td>
<span id="103514009" />
<nedss:view name="PageForm" property="pageClientVO.answer(103514009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates whether additional pathogen testing was completed for this patient." id="NBS672L" >
Were Other Pathogen(s) Tested?:</span></td><td>
<span id="NBS672" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS672)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23000" name="Other Pathogens Tested Retired" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23000"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA23000">
<tr id="patternNBS_UI_GA23000" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA23000');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA23000">
<tr id="nopatternNBS_UI_GA23000" class="odd" style="display:none">
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

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Other Pathogen Tested" id="NBS669L">
Specify Other Pathogen Tested:</span>
</td>
<td>
<span id="NBS669"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS669)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Other Pathogen Tested Result" id="NBS668L" >
Other Pathogens Tested:</span></td><td>
<span id="NBS668" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS668)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
