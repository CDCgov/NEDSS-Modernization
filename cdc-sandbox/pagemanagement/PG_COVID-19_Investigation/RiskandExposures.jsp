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
String tabId = "editRiskandExposures";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Place of Residence","Healthcare Worker Information","Exposure Information"};
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
<nedss:container id="NBS_UI_GA24002" name="Place of Residence" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95421_4L" title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc.">
Is the patient a resident in a congregate care/living setting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95421_4)" styleId="95421_4" title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS202L" title="Which would best describe where the patient was staying at the time of illness onset?">
Which would best describe where the patient was staying at the time of illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS202)" styleId="NBS202" title="Which would best describe where the patient was staying at the time of illness onset?" onchange="enableOrDisableOther('NBS202');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(RESIDENCE_TYPE_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202OthL">Other Which would best describe where the patient was staying at the time of illness onset?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS202Oth)" size="40" maxlength="40" title="Other Which would best describe where the patient was staying at the time of illness onset?" styleId="NBS202Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25005" name="Healthcare Worker Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS540L" title="Is the patient a health care worker in the United States?">
Was patient healthcare personnel (HCP) at illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS540)" styleId="NBS540" title="Is the patient a health care worker in the United States?" onchange="ruleEnDisNBS5408861();enableOrDisableOther('NBS683');enableOrDisableOther('14679004');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="14679004L" title="If the patient is a healthcare worker, specify occupation (type of job)">
If yes, what is their occupation (type of job)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(14679004)" styleId="14679004" title="If the patient is a healthcare worker, specify occupation (type of job)" onchange="enableOrDisableOther('14679004');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(HCW_OCCUPATION_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004OthL">Other If yes, what is their occupation (type of job)?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(14679004Oth)" size="40" maxlength="40" title="Other If the patient is a healthcare worker, specify occupation (type of job)" styleId="14679004Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS683L" title="If the patient is a healthcare worker, what is their job setting?">
If yes, what is their job setting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS683)" styleId="NBS683" title="If the patient is a healthcare worker, what is their job setting?" onchange="enableOrDisableOther('NBS683');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(HCW_SETTING_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the patient is a healthcare worker, what is their job setting?" id="NBS683OthL">Other If yes, what is their job setting?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS683Oth)" size="40" maxlength="40" title="Other If the patient is a healthcare worker, what is their job setting?" styleId="NBS683Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25007" name="Travel Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA21020L">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV664L" title="Did the case patient travel domestically within program specific time frame?">
Domestic travel (outside normal state of residence):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV664)" styleId="INV664" title="Did the case patient travel domestically within program specific time frame?" onchange="ruleEnDisINV6648862();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL38L" title="Did the case patient travel internationally?">
International Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL38)" styleId="TRAVEL38" title="Did the case patient travel internationally?" onchange="ruleEnDisTRAVEL388863();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="473085002L" title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?">
Cruise ship or vessel travel as passenger or crew member:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(473085002)" styleId="473085002" title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?" onchange="ruleEnDis4730850028864();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS690L" title="Name of cruise ship or vessel.">
Specify Name of Ship or Vessel:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS690)" size="50" maxlength="50" title="Name of cruise ship or vessel." styleId="NBS690"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35002" name="Travel Events Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA35002errorMessages">
<b> <a name="NBS_UI_GA35002errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35002"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA35002">
<tr id="patternNBS_UI_GA35002" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA35002" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA35002');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_GA35002" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_GA35002');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_GA35002" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_GA35002','patternNBS_UI_GA35002','questionbodyNBS_UI_GA35002');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_GA35002">
<tr id="nopatternNBS_UI_GA35002" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="TRAVEL05L" title="Indicate all countries of travel in the last 14 days.">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL05)" styleId="TRAVEL05" title="Indicate all countries of travel in the last 14 days." onchange="unhideBatchImg('NBS_UI_GA35002');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="82754_3L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(82754_3)" styleId="82754_3" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="NBS453L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS453)" styleId="NBS453" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_GA35002');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC_COVID19)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="TRAVEL16L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL16)" styleId="TRAVEL16" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_GA35002');enableOrDisableOther('TRAVEL16');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel" id="TRAVEL16OthL">Other Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel" onkeyup="unhideBatchImg('NBS_UI_GA35002')" styleId="TRAVEL16Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06L" title="If the subject traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06" onkeyup="unhideBatchImg('NBS_UI_GA35002');DateMask(this,null,event)" styleClass="NBS_UI_GA35002" title="If the subject traveled, when did they arrive to their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL06','TRAVEL06Icon'); unhideBatchImg('NBS_UI_GA35002');return false;" styleId="TRAVEL06Icon" onkeypress="showCalendarEnterKey('TRAVEL06','TRAVEL06Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07L" title="If the subject traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07" onkeyup="unhideBatchImg('NBS_UI_GA35002');DateMask(this,null,event)" styleClass="NBS_UI_GA35002" title="If the subject traveled, when did they depart from their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL07','TRAVEL07Icon'); unhideBatchImg('NBS_UI_GA35002');return false;" styleId="TRAVEL07Icon" onkeypress="showCalendarEnterKey('TRAVEL07','TRAVEL07Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4L" title="Duration of stay in country outside the US">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82310_4)" size="3" maxlength="3"  title="Duration of stay in country outside the US" styleId="82310_4" onkeyup="unhideBatchImg('NBS_UI_GA35002');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_GA35002"/>
<html:select name="PageForm" property="pageClientVO.answer(82310_4Unit)" styleId="82310_4UNIT" onchange="unhideBatchImg('NBS_UI_GA35002')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(TRAVEL23)" styleId ="TRAVEL23" onkeyup="checkTextAreaLength(this, 199)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA35002">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002','patternNBS_UI_GA35002','questionbodyNBS_UI_GA35002')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA35002">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002','patternNBS_UI_GA35002','questionbodyNBS_UI_GA35002');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA35002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002','patternNBS_UI_GA35002','questionbodyNBS_UI_GA35002');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA35002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_GA35002')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21014" name="Other Exposure Events" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA25015L">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS684L" title="Prior to illness onset, did the patient have exposure in the workplace?">
Workplace:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS684)" styleId="NBS684" title="Prior to illness onset, did the patient have exposure in the workplace?" onchange="ruleEnDisNBS6848865();ruleEnDisNBS6858870();null;enableOrDisableOther('NBS686_CD');null;enableOrDisableOther('NBS686_CD');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS685L" title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?">
If yes, is the workplace critical infrastructure (e.g. healthcare setting, grocery store)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS685)" styleId="NBS685" title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?" onchange="ruleEnDisNBS6858870();null;enableOrDisableOther('NBS686_CD');enableOrDisableOther('NBS686_CD');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS686_CDL" title="Specify the workplace setting">
If yes, specify workplace setting:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS686_CD)" styleId="NBS686_CD" title="Specify the workplace setting"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS686_CD-selectedValues');enableOrDisableOther('NBS686_CD')" >
<nedss:optionsCollection property="codedValue(PHVS_CRITICALINFRASTRUCTURESECTOR_NND_COVID19)" value="key" label="value" /> </html:select>
<div id="NBS686_CD-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specify the workplace setting" id="NBS686_CDOthL">Other If yes, specify workplace setting:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS686_CDOth)" size="40" maxlength="40" title="Other Specify the workplace setting" styleId="NBS686_CDOth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="445000002L" title="Prior to illness onset, did the patient travel by air?">
Airport/Airplane:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(445000002)" styleId="445000002" title="Prior to illness onset, did the patient travel by air?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS687L" title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility).">
Adult Congregate Living Facility (nursing, assisted living, or LTC facility):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS687)" styleId="NBS687" title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility).">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS689L" title="Prior to illness onset, was patient exposed to a correctional facility?">
Correctional Facility:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS689)" styleId="NBS689" title="Prior to illness onset, was patient exposed to a correctional facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="257698009L" title="Prior to illness onset, was the patient exposed to a school oruniversity ?">
School/University Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(257698009)" styleId="257698009" title="Prior to illness onset, was the patient exposed to a school oruniversity ?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413817003L" title="Prior to illness onset, was the patient exposed to a child care facility?">
Child Care Facility Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(413817003)" styleId="413817003" title="Prior to illness onset, was the patient exposed to a child care facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_184L" title="Did subject attend any events or large gatherings prior to onset of illness?">
Community Event/Mass Gathering:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_184)" styleId="FDD_Q_184" title="Did subject attend any events or large gatherings prior to onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS559L" title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?">
Animal with confirmed or suspected COVID-19:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS559)" styleId="NBS559" title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?" onchange="ruleEnDisNBS5598866();enableOrDisableOther('FDD_Q_32');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_32L" title="Animal contact by type of animal">
Animal Type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_32)" styleId="FDD_Q_32" title="Animal contact by type of animal"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_32-selectedValues');enableOrDisableOther('FDD_Q_32')" >
<nedss:optionsCollection property="codedValue(PHVS_ANIMALTYPE_COVID19)" value="key" label="value" /> </html:select>
<div id="FDD_Q_32-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Animal contact by type of animal" id="FDD_Q_32OthL">Other Animal Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(FDD_Q_32Oth)" size="40" maxlength="40" title="Other Animal contact by type of animal" styleId="FDD_Q_32Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS560L" title="Did the patient have any other exposure type?">
Other Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS560)" styleId="NBS560" title="Did the patient have any other exposure type?" onchange="ruleEnDisNBS5608846();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS561L" title="Specify other exposure.">
Other Exposure Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS561)" size="100" maxlength="100" title="Specify other exposure." styleId="NBS561"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS667L" title="Unknown exposures in the 14 days prior to illness onset">
Unknown exposures in the 14 days prior to illness onset:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS667)" styleId="NBS667" title="Unknown exposures in the 14 days prior to illness onset">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25008" name="Exposure to Known Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA25016L">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS557L" title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact.">
Did the patient have contact with another COVID-19 case (probable or confirmed)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS557)" styleId="NBS557" title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact." onchange="ruleEnDisNBS5578844();ruleEnDisNBS5438845();ruleEnDisINV603_68871();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS664L" title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?">
Household contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS664)" styleId="NBS664" title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS665L" title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?">
Community contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS665)" styleId="NBS665" title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS666L" title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?">
Healthcare contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS666)" styleId="NBS666" title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_6L" title="If other contact with a known COVID-19 case, indicate other type of contact.">
Other Contact Type?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV603_6)" styleId="INV603_6" title="If other contact with a known COVID-19 case, indicate other type of contact." onchange="ruleEnDisINV603_68871();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV897L" title="Specify other contact type">
Other Contact Type Specify:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV897)" styleId ="INV897" onkeyup="checkTextAreaLength(this, 100)" title="Specify other contact type"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA35006L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS543L" title="If the patient had contact with another COVID-19 case, was this person a U.S. case?">
If the patient had contact with another COVID-19 case, was this person a U.S. case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS543)" styleId="NBS543" title="If the patient had contact with another COVID-19 case, was this person a U.S. case?" onchange="ruleEnDisNBS5438845();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350L" title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case.">
nCoV ID of source case 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS350)" size="25" maxlength="25" title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case." styleId="NBS350"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_2L" title="If epi-linked to a known case, Case ID of the epi-linked case.">
nCoV ID of source case 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS350_2)" size="25" maxlength="25" title="If epi-linked to a known case, Case ID of the epi-linked case." styleId="NBS350_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_3L" title="If epi-linked to a known case, Case ID of the epi-linked case.">
nCoV ID of source case 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS350_3)" size="25" maxlength="25" title="If epi-linked to a known case, Case ID of the epi-linked case." styleId="NBS350_3"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
