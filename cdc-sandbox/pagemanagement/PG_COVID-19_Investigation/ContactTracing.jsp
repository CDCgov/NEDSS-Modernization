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
String tabId = "editContactTracing";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS055L" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
Contact Investigation Priority:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS055)" styleId="NBS055" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
<nedss:optionsCollection property="codedValue(NBS_PRIORITY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS056L" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period.">
Infectious Period From:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS056)" maxlength="10" size="10" styleId="NBS056" onkeyup="DateMaskFuture(this,null,event)" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS056','NBS056Icon'); return false;" styleId="NBS056Icon" onkeypress ="showCalendarFutureEnterKey('NBS056','NBS056Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS057L" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period.">
Infectious Period To:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS057)" maxlength="10" size="10" styleId="NBS057" onkeyup="DateMaskFuture(this,null,event)" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS057','NBS057Icon'); return false;" styleId="NBS057Icon" onkeypress ="showCalendarFutureEnterKey('NBS057','NBS057Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS058L" title="The status of the contact investigation.">
Contact Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS058)" styleId="NBS058" title="The status of the contact investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS059L" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation.">
Contact Investigation Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS059)" styleId ="NBS059" onkeyup="checkTextAreaLength(this, 2000)" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25002" name="Retired Questions" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS549L" title="Report Date of Person Under Investigation (PUI) to CDC">
Report Date of PUI to CDC:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS549)" maxlength="10" size="10" styleId="NBS549" onkeyup="DateMask(this,null,event)" title="Report Date of Person Under Investigation (PUI) to CDC"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS549','NBS549Icon'); return false;" styleId="NBS549Icon" onkeypress="showCalendarEnterKey('NBS549','NBS549Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS541L" title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?">
Patient history of being in a healthcare facility (as a patient, worker or visitor) in China?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS541)" styleId="NBS541" title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS544L" title="Type of healthcare contact with another lab-confirmed case-patient.">
Type of healthcare contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS544)" styleId="NBS544" title="Type of healthcare contact with another lab-confirmed case-patient.">
<nedss:optionsCollection property="codedValue(HC_CONTACT_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS558L" title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology.">
Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS558)" styleId="NBS558" title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS556L" title="Did the patient travel to any high-risk locations?">
Did the patient travel to any high-risk locations:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS556)" styleId="NBS556" title="Did the patient travel to any high-risk locations?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS556-selectedValues');enableOrDisableOther('NBS556')" >
<nedss:optionsCollection property="codedValue(HIGH_RISK_TRAVEL_LOC_COVID)" value="key" label="value" /> </html:select>
<div id="NBS556-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Did the patient travel to any high-risk locations?" id="NBS556OthL">Other Did the patient travel to any high-risk locations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS556Oth)" size="40" maxlength="40" title="Other Did the patient travel to any high-risk locations?" styleId="NBS556Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS554L" title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02.">
Source patient case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS554)" size="50" maxlength="50" title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02." styleId="NBS554"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS663L" title="Indicate the date the case was reported to CDC.">
Report Date of Case to CDC:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS663)" maxlength="10" size="10" styleId="NBS663" onkeyup="DateMask(this,null,event)" title="Indicate the date the case was reported to CDC."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS663','NBS663Icon'); return false;" styleId="NBS663Icon" onkeypress="showCalendarEnterKey('NBS663','NBS663Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA36000" name="Retired Questions Do Not Use" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA36001L">&nbsp;&nbsp;Questions in this section have been replaced by other standard questions. These versions of the questions should not be used going forward, but are on the page to maintain any data previously collected.</span> </td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="67884_7L" title="If the patient has any tribal affiliation, enter the Indian Tribe name.">
Tribe Name(s) (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(67884_7)" size="50" maxlength="50" title="If the patient has any tribal affiliation, enter the Indian Tribe name." styleId="67884_7"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="54899_0L" title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?">
If yes, specify which language - Retired:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(54899_0)" size="25" maxlength="25" title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?" styleId="54899_0"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS686L" title="Specify the type of workplace setting">
If yes, specify workplace setting (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS686)" size="50" maxlength="50" title="Specify the type of workplace setting" styleId="NBS686"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_32_TXTL" title="Specify the type of animal with which the patient had contact.">
Specify Type of Animal (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_32_TXT)" size="30" maxlength="30" title="Specify the type of animal with which the patient had contact." styleId="FDD_Q_32_TXT"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS688L" title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?">
School/University/Childcare Center Exposure - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS688)" styleId="NBS688" title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="43724002L" title="Did the patient have chills or rigors?">
Chills or Rigors - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(43724002)" styleId="43724002" title="Did the patient have chills or rigors?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS675L" title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?">
New Olfactory and Taste Disorder - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS675)" styleId="NBS675" title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS777L" title="Indicate all the countries of travel in the last 14 days (Multi-Select)">
Travel Country (Multi Select):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS777)" styleId="NBS777" title="Indicate all the countries of travel in the last 14 days (Multi-Select)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS777-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="NBS777-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS778L" title="Indicate all the states of travel in the last 14 days (Multi-Select)">
Travel State (Multi-Select):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS778)" styleId="NBS778" title="Indicate all the states of travel in the last 14 days (Multi-Select)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS778-selectedValues')" >
<nedss:optionsCollection property="codedValue(STATE_CCD)" value="key" label="value" /> </html:select>
<div id="NBS778-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA22003" name="Respiratory Diagnostic Testing Retired" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="80382_5L" title="Influenza A Rapid Ag Result">
Influenza A Rapid Ag:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(80382_5)" styleId="80382_5" title="Influenza A Rapid Ag Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="80383_3L" title="Influenza B Rapid Ag Result">
Influenza B Rapid Ag:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(80383_3)" styleId="80383_3" title="Influenza B Rapid Ag Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="34487_9L" title="Influenza A PCR Result">
Influenza A PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(34487_9)" styleId="34487_9" title="Influenza A PCR Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="40982_1L" title="Influenza B PCR Result">
Influenza B PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(40982_1)" styleId="40982_1" title="Influenza B PCR Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="6415009L" title="Respiratory Syncytial Virus (RSV) Result">
RSV:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(6415009)" styleId="6415009" title="Respiratory Syncytial Virus (RSV) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="416730002L" title="H. metapneumovirus Result">
H. metapneumovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(416730002)" styleId="416730002" title="H. metapneumovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="41505_9L" title="Parainfluenza (1-4) Result">
Parainfluenza (1-4):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(41505_9)" styleId="41505_9" title="Parainfluenza (1-4) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="74871001L" title="Adenovirus Result">
Adenovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(74871001)" styleId="74871001" title="Adenovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="69239002L" title="Rhinovirus/Enterovirus Result">
Rhinovirus/enterovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(69239002)" styleId="69239002" title="Rhinovirus/Enterovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="84101006L" title="Coronavirus (OC43, 229E, HKU1, NL63) Result">
Coronavirus (OC43, 229E, HKU1, NL63):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(84101006)" styleId="84101006" title="Coronavirus (OC43, 229E, HKU1, NL63) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="58720004L" title="M. pneumoniae Result">
M. pneumoniae:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(58720004)" styleId="58720004" title="M. pneumoniae Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="103514009L" title="C. pneumoniae Result">
C. pneumoniae:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(103514009)" styleId="103514009" title="C. pneumoniae Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS672L" title="Indicates whether additional pathogen testing was completed for this patient.">
Were Other Pathogen(s) Tested?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS672)" styleId="NBS672" title="Indicates whether additional pathogen testing was completed for this patient." onchange="ruleEnDisNBS6728856();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23000" name="Other Pathogens Tested Retired" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA23000errorMessages">
<b> <a name="NBS_UI_GA23000errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_UI_GA23000">
<tr id="patternNBS_UI_GA23000" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA23000" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA23000');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_GA23000" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_GA23000');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_GA23000" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_GA23000','patternNBS_UI_GA23000','questionbodyNBS_UI_GA23000');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_GA23000">
<tr id="nopatternNBS_UI_GA23000" class="odd" style="display:">
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
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS669L" title="Other Pathogen Tested">
Specify Other Pathogen Tested:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS669)" size="100" maxlength="100" title="Other Pathogen Tested" styleId="NBS669" onkeyup="unhideBatchImg('NBS_UI_GA23000');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA23000 InputFieldLabel" id="NBS668L" title="Other Pathogen Tested Result">
Other Pathogens Tested:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS668)" styleId="NBS668" title="Other Pathogen Tested Result" onchange="unhideBatchImg('NBS_UI_GA23000');">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA23000">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000','patternNBS_UI_GA23000','questionbodyNBS_UI_GA23000')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA23000">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000','patternNBS_UI_GA23000','questionbodyNBS_UI_GA23000');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA23000"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000','patternNBS_UI_GA23000','questionbodyNBS_UI_GA23000');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA23000"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_GA23000')"/>&nbsp;	&nbsp;&nbsp;
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
