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
String tabId = "editMalaria";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Laboratory","Travel","Prophylactic Treatment","Medical History","Complications","Treatment History","Part II: Completed 4 Weeks After Treatment"};
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
<nedss:container id="NBS_UI_84" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_58L">&nbsp;&nbsp;Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests:</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_75L">&nbsp;&nbsp;(i) blood smear with the highest percentage parasitemia,</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_76L">&nbsp;&nbsp;(ii) the test that indicates the Plasmodium species, and</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_77L">&nbsp;&nbsp;(iii) a confirmatory PCR (if applicable).</span> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_59" name="Lab Data Entry Guidance" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_60L">&nbsp;&nbsp;If there are conflicting lab results for the species identification, then include only the test with the final result. Multiple species can be selected for one test.</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_61L">&nbsp;&nbsp;If the species determination is inconclusive, select 'Not Determined.' If there is a suspicion towards a particular species (e.g. non-falciparum), select 'Not Determined' and 'Other' and write the suspected species in the 'Other Organism Name' text box.</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_62L">&nbsp;&nbsp;The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_63L">&nbsp;&nbsp;--Example A: 50 infected erythrocytes out of 1000 counted = 5% parasitemia, and the submitted response should "5".</span> </td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_64L">&nbsp;&nbsp;--Example B: 2 infected erythrocytes out of 2000 counted = 0.1% parasitemia and the submitted response should be 0.1.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis" onchange="ruleEnDisINV7408511();ruleEnDisINV7408507();ruleEnDisLAB5158510();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_79" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_79errorMessages">
<b> <a name="NBS_UI_79errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_79"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_79">
<tr id="patternNBS_UI_79" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_79" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_79');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_79" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_79');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_79" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_79','patternNBS_UI_79','questionbodyNBS_UI_79');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_79">
<tr id="nopatternNBS_UI_79" class="odd" style="display:">
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
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_79 InputFieldLabel" id="INV290L" title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." onchange="unhideBatchImg('NBS_UI_79');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(LAB_TEST_PROCEDURE_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." onkeyup="unhideBatchImg('NBS_UI_79')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_79 InputFieldLabel" id="INV291L" title="Result of Diagnostic Test">
Result of Diagnostic Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Result of Diagnostic Test" onchange="unhideBatchImg('NBS_UI_79');">
<nedss:optionsCollection property="codedValue(PHVS_PNUND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB278L" title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section.">
Organism Name (see tool tip for guidance):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(LAB278)" styleId="LAB278" title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section."
multiple="true" size="4"
onchange="unhideBatchImg('NBS_UI_79');displaySelectedOptions(this, 'LAB278-selectedValues');enableOrDisableOther('LAB278')" >
<nedss:optionsCollection property="codedValue(SPECIES_MAL)" value="key" label="value" /> </html:select>
<div id="LAB278-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section." id="LAB278OthL">Other Organism Name (see tool tip for guidance):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB278Oth)" size="40" maxlength="40" title="Other Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section." onkeyup="unhideBatchImg('NBS_UI_79');" styleId="LAB278Oth"/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="53556_7L" title="The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.">
For blood smear tests, what is the highest percentage parasitemia:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(53556_7)" size="10" maxlength="10"  title="The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia." styleId="53556_7" onkeyup="unhideBatchImg('NBS_UI_79');isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,0,100)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_79');DateMask(this,null,event)" styleClass="NBS_UI_79" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_79');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB167L" title="Date result sent from reporting laboratory. Time of result is an optional addition to date.">
Lab Result Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB167)" maxlength="10" size="10" styleId="LAB167" onkeyup="unhideBatchImg('NBS_UI_79');DateMask(this,null,event)" styleClass="NBS_UI_79" title="Date result sent from reporting laboratory. Time of result is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB167','LAB167Icon'); unhideBatchImg('NBS_UI_79');return false;" styleId="LAB167Icon" onkeypress="showCalendarEnterKey('LAB167','LAB167Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="68994_3L" title="Performing laboratory name">
Performing laboratory name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(68994_3)" size="50" maxlength="50" title="Performing laboratory name" styleId="68994_3" onkeyup="unhideBatchImg('NBS_UI_79');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="65651_2L" title="Reporting laboratory phone number">
Laboratory Phone Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(65651_2)" size="12" maxlength="12" title="Reporting laboratory phone number" styleId="65651_2" onkeyup="unhideBatchImg('NBS_UI_79');TeleMask(this, event)"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_79">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_79BatchAddFunction()) writeQuestion('NBS_UI_79','patternNBS_UI_79','questionbodyNBS_UI_79')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_79">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_79BatchAddFunction()) writeQuestion('NBS_UI_79','patternNBS_UI_79','questionbodyNBS_UI_79');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_79"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_79BatchAddFunction()) writeQuestion('NBS_UI_79','patternNBS_UI_79','questionbodyNBS_UI_79');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_79"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_79')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Specimen Type Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB515L" title="Was Specimen(s) sent to CDC?">
Specimen(s) sent to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB515)" styleId="LAB515" title="Was Specimen(s) sent to CDC?" onchange="ruleEnDisLAB5158510();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Sent to CDC" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_39errorMessages">
<b> <a name="NBS_UI_39errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_39"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_39">
<tr id="patternNBS_UI_39" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_39" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_39');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_39" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_39');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_39" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_39','patternNBS_UI_39','questionbodyNBS_UI_39');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_39">
<tr id="nopatternNBS_UI_39" class="odd" style="display:">
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
<span class="NBS_UI_39 InputFieldLabel" id="667469L" title="Type(s) of specimen sent to CDC">
Type(s) of Specimen sent to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="Type(s) of specimen sent to CDC" onchange="unhideBatchImg('NBS_UI_39');enableOrDisableOther('667469');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMEN_TYPE_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type(s) of specimen sent to CDC" id="667469OthL">Other Type(s) of Specimen sent to CDC:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(667469Oth)" size="40" maxlength="40" title="Other Type(s) of specimen sent to CDC" onkeyup="unhideBatchImg('NBS_UI_39')" styleId="667469Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV965L" title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456.">
CDC Specimen ID Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV965)" size="10" maxlength="10" title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456." styleId="INV965" onkeyup="unhideBatchImg('NBS_UI_39');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_39">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_39BatchAddFunction()) writeQuestion('NBS_UI_39','patternNBS_UI_39','questionbodyNBS_UI_39')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_39">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_39BatchAddFunction()) writeQuestion('NBS_UI_39','patternNBS_UI_39','questionbodyNBS_UI_39');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_39"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_39BatchAddFunction()) writeQuestion('NBS_UI_39','patternNBS_UI_39','questionbodyNBS_UI_39');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_39"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_39')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV501L" title="Where does the person usually live (defined as their residence).">
Country of Usual Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV501)" styleId="INV501" title="Where does the person usually live (defined as their residence).">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL14L" title="Reside in US Prior to Most Recent Travel">
Legacy - Did the patient reside in the U.S. prior to most recent travel?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL14)" styleId="TRAVEL14" title="Reside in US Prior to Most Recent Travel">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="MAL135L" title="Principal Reason for Travel question created for Malaria Porting only.">
Legacy - Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MAL135)" styleId="MAL135" title="Principal Reason for Travel question created for Malaria Porting only." onchange="enableOrDisableOther('MAL135')">
<nedss:optionsCollection property="codedValue(TRAVEL_REASON_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel question created for Malaria Porting only." id="MAL135OthL">Other Legacy - Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(MAL135Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel question created for Malaria Porting only." styleId="MAL135Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL10L" title="Has the patient traveled or lived outside the U.S. during the past two years">
Has the patient traveled or lived outside the U.S. during the past two years?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL10)" styleId="TRAVEL10" title="Has the patient traveled or lived outside the U.S. during the past two years" onchange="ruleEnDisTRAVEL108516();ruleEnDisTRAVEL108514();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL15L" title="What was the patient's country of residence prior to most recent travel?">
What was the patient's country of residence prior to most recent travel?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL15)" styleId="TRAVEL15" title="What was the patient's country of residence prior to most recent travel?">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_41" name="Travel History Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_41errorMessages">
<b> <a name="NBS_UI_41errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_41"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_41">
<tr id="patternNBS_UI_41" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_41" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_41');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_41" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_41');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_41" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_41','patternNBS_UI_41','questionbodyNBS_UI_41');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_41">
<tr id="nopatternNBS_UI_41" class="odd" style="display:">
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_54L">&nbsp;&nbsp;If the patient traveled outside of the US or lived outside of the US within the last 2 years:</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_41 InputFieldLabel" id="TRAVEL05L" title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years">
Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL05)" styleId="TRAVEL05" title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" onchange="unhideBatchImg('NBS_UI_41');enableOrDisableOther('TRAVEL05');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(GEOGRAPHIC_LOCATION_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" id="TRAVEL05OthL">Other Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(TRAVEL05Oth)" size="40" maxlength="40" title="Other Country of International Travel or Residence (for non-US residents) During the Past 2 Years" onkeyup="unhideBatchImg('NBS_UI_41')" styleId="TRAVEL05Oth"/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4L" title="Duration of stay in country outside the US">
Duration of Stay in Country Outside the US:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82310_4)" size="3" maxlength="3"  title="Duration of stay in country outside the US" styleId="82310_4" onkeyup="unhideBatchImg('NBS_UI_41');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_41"/>
<html:select name="PageForm" property="pageClientVO.answer(82310_4Unit)" styleId="82310_4UNIT" onchange="unhideBatchImg('NBS_UI_41')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL08L" title="Date Returned to/Arrive in US">
Date Returned to/Arrive in US:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL08)" maxlength="10" size="10" styleId="TRAVEL08" onkeyup="unhideBatchImg('NBS_UI_41');DateMask(this,null,event)" styleClass="NBS_UI_41" title="Date Returned to/Arrive in US"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL08','TRAVEL08Icon'); unhideBatchImg('NBS_UI_41');return false;" styleId="TRAVEL08Icon" onkeypress="showCalendarEnterKey('TRAVEL08','TRAVEL08Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="If the patient traveled, when did they arrive to their travel destination?" id="TRAVEL06L">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06" title="If the patient traveled, when did they arrive to their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL06','TRAVEL06Icon');unhideBatchImg('NBS_UI_41');return false;" styleId="TRAVEL06Icon" onkeypress="showCalendarEnterKey('TRAVEL06','TRAVEL06Icon',event);" ></html:img>
</td> </tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="If the patient traveled, when did they depart from their travel destination?" id="TRAVEL07L">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07" title="If the patient traveled, when did they depart from their travel destination?"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL07','TRAVEL07Icon');unhideBatchImg('NBS_UI_41');return false;" styleId="TRAVEL07Icon" onkeypress="showCalendarEnterKey('TRAVEL07','TRAVEL07Icon',event);" ></html:img>
</td> </tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL16L" title="Reason for travel related to current illness">
Reasons for Travel:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(TRAVEL16)" styleId="TRAVEL16" title="Reason for travel related to current illness"
multiple="true" size="4"
onchange="unhideBatchImg('NBS_UI_41');displaySelectedOptions(this, 'TRAVEL16-selectedValues');enableOrDisableOther('TRAVEL16')" >
<nedss:optionsCollection property="codedValue(TRAVEL_REASON_MAL)" value="key" label="value" /> </html:select>
<div id="TRAVEL16-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Reason for travel related to current illness" id="TRAVEL16OthL">Other Reasons for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Reason for travel related to current illness" onkeyup="unhideBatchImg('NBS_UI_41');" styleId="TRAVEL16Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_41 InputFieldLabel" id="NBS453L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS453)" styleId="NBS453" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_41');">
<nedss:optionsCollection property="codedValue(TRAVEL_MODE_MAL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(TRAVEL23)" styleId ="TRAVEL23" onkeyup="checkTextAreaLength(this, 2000)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_41">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_41BatchAddFunction()) writeQuestion('NBS_UI_41','patternNBS_UI_41','questionbodyNBS_UI_41')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_41">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_41BatchAddFunction()) writeQuestion('NBS_UI_41','patternNBS_UI_41','questionbodyNBS_UI_41');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_41"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_41BatchAddFunction()) writeQuestion('NBS_UI_41','patternNBS_UI_41','questionbodyNBS_UI_41');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_41"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_41')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35" name="Chemoprophylaxis Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_65L">&nbsp;&nbsp;Complete this section for anti-malarial medications taken prior to or during travel.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="182929008L" title="Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?">
Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(182929008)" styleId="182929008" title="Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?" onchange="ruleEnDis1829290088506();enableOrDisableOther('INV931');ruleEnDisINV3098513();;ruleEnDisINV9328517();enableOrDisableOther('INV932');enableOrDisableOther('INV932');;ruleEnDisINV9328517();enableOrDisableOther('INV932');enableOrDisableOther('INV932');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV931L" title="Chemoprophylaxis Medication(s)">
List chemoprophylaxis medications taken by the patient:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV931)" styleId="INV931" title="Chemoprophylaxis Medication(s)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV931-selectedValues');enableOrDisableOther('INV931')" >
<nedss:optionsCollection property="codedValue(MEDICATION_PROPHYLAXIS_MAL)" value="key" label="value" /> </html:select>
<div id="INV931-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Chemoprophylaxis Medication(s)" id="INV931OthL">Other List chemoprophylaxis medications taken by the patient:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV931Oth)" size="40" maxlength="40" title="Other Chemoprophylaxis Medication(s)" styleId="INV931Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV309L" title="Was chemoprophylaxis taken as prescribed? (Yes = missed no doses, No = missed doses)">
Was chemoprophylaxis taken as prescribed (without any skipped doses)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV309)" styleId="INV309" title="Was chemoprophylaxis taken as prescribed? (Yes = missed no doses, No = missed doses)" onchange="ruleEnDisINV3098513();;ruleEnDisINV9328517();enableOrDisableOther('INV932');enableOrDisableOther('INV932');enableOrDisableOther('INV932');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_45L"><hr/></span> </td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV932L" title="Reason for missed chemoprophylaxis">
Reason(s) doses were missed:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV932)" styleId="INV932" title="Reason for missed chemoprophylaxis"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV932-selectedValues');ruleEnDisINV9328517();enableOrDisableOther('INV932')" >
<nedss:optionsCollection property="codedValue(MEDICATION_MISSED_MAL)" value="key" label="value" /> </html:select>
<div id="INV932-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Reason for missed chemoprophylaxis" id="INV932OthL">Other Reason(s) doses were missed:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV932Oth)" size="40" maxlength="40" title="Other Reason for missed chemoprophylaxis" styleId="INV932Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV921L" title="If reason for missed doses is due to a side effect, then specify side effect">
Specify side effects of missed dose(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV921)" styleId ="INV921" onkeyup="checkTextAreaLength(this, 199)" title="If reason for missed doses is due to a side effect, then specify side effect"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43" name="Previous Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="161413004L" title="Did the patient have a previous history of malaria in the last 12 months, prior to this report?">
Did the patient have a previous history of malaria in the last 12 months, prior to this report?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(161413004)" styleId="161413004" title="Did the patient have a previous history of malaria in the last 12 months, prior to this report?" onchange="ruleEnDis1614130048509();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Previous Malaria Illness" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_44errorMessages">
<b> <a name="NBS_UI_44errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_44"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_44">
<tr id="patternNBS_UI_44" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_44" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_44');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_44" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_44');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_44" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_44">
<tr id="nopatternNBS_UI_44" class="odd" style="display:">
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

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV914L" title="Malaria species associated with previous illness">
Organism Associated with Previous Illness:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV914)" styleId="INV914" title="Malaria species associated with previous illness"
multiple="true" size="4"
onchange="unhideBatchImg('NBS_UI_44');displaySelectedOptions(this, 'INV914-selectedValues');enableOrDisableOther('INV914')" >
<nedss:optionsCollection property="codedValue(SPECIES_MAL)" value="key" label="value" /> </html:select>
<div id="INV914-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Malaria species associated with previous illness" id="INV914OthL">Other Organism Associated with Previous Illness:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV914Oth)" size="40" maxlength="40" title="Other Malaria species associated with previous illness" onkeyup="unhideBatchImg('NBS_UI_44');" styleId="INV914Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="82758_4L" title="Date of previous malaria illness">
Date of Previous Malaria Illness:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82758_4)" maxlength="10" size="10" styleId="82758_4" onkeyup="unhideBatchImg('NBS_UI_44');DateMask(this,null,event)" styleClass="NBS_UI_44" title="Date of previous malaria illness"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('82758_4','82758_4Icon'); unhideBatchImg('NBS_UI_44');return false;" styleId="82758_4Icon" onkeypress="showCalendarEnterKey('82758_4','82758_4Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_44">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_44">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_44"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_44"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_44')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_56" name="Blood Transfusion/Organ Transplant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82312_0L" title="Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?">
Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82312_0)" styleId="82312_0" title="Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?" onchange="ruleEnDis82312_08508();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="80989_7L" title="Date of blood transfusion/organ transplant">
Date of blood transfusion/organ transplant:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(80989_7)" maxlength="10" size="10" styleId="80989_7" onkeyup="DateMask(this,null,event)" title="Date of blood transfusion/organ transplant"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('80989_7','80989_7Icon'); return false;" styleId="80989_7Icon" onkeypress="showCalendarEnterKey('80989_7','80989_7Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_82" name="Clinical Complications" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67187_5L" title="Complication(s) related to this malaria illness">
Complication(s) Related to this Malaria Illness:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(67187_5)" styleId="67187_5" title="Complication(s) related to this malaria illness"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '67187_5-selectedValues');enableOrDisableOther('67187_5')" >
<nedss:optionsCollection property="codedValue(COMPLICATIONS_MAL)" value="key" label="value" /> </html:select>
<div id="67187_5-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Complication(s) related to this malaria illness" id="67187_5OthL">Other Complication(s) Related to this Malaria Illness:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(67187_5Oth)" size="40" maxlength="40" title="Other Complication(s) related to this malaria illness" styleId="67187_5Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_71" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_72L">&nbsp;&nbsp;List all malaria medications taken for this illness.</span> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Therapy for this Attack" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_57errorMessages">
<b> <a name="NBS_UI_57errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_57"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_57">
<tr id="patternNBS_UI_57" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_57" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_57');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_57" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_57');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_57" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_57">
<tr id="nopatternNBS_UI_57" class="odd" style="display:">
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
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_57 InputFieldLabel" id="55753_8L" title="Listing of treatment the subject received for this illness">
Treatment the Patient Received:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(55753_8)" styleId="55753_8" title="Listing of treatment the subject received for this illness" onchange="unhideBatchImg('NBS_UI_57');enableOrDisableOther('55753_8');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(MEDICATION_TREATMENT_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Listing of treatment the subject received for this illness" id="55753_8OthL">Other Treatment the Patient Received:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(55753_8Oth)" size="40" maxlength="40" title="Other Listing of treatment the subject received for this illness" onkeyup="unhideBatchImg('NBS_UI_57')" styleId="55753_8Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV924L" title="Date the treatment was initiated">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV924)" maxlength="10" size="10" styleId="INV924" onkeyup="unhideBatchImg('NBS_UI_57');DateMask(this,null,event)" styleClass="NBS_UI_57" title="Date the treatment was initiated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV924','INV924Icon'); unhideBatchImg('NBS_UI_57');return false;" styleId="INV924Icon" onkeypress="showCalendarEnterKey('INV924','INV924Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="413947000L" title="Date treatment stopped">
Treatment Stop Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(413947000)" maxlength="10" size="10" styleId="413947000" onkeyup="unhideBatchImg('NBS_UI_57');DateMask(this,null,event)" styleClass="NBS_UI_57" title="Date treatment stopped"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('413947000','413947000Icon'); unhideBatchImg('NBS_UI_57');return false;" styleId="413947000Icon" onkeypress="showCalendarEnterKey('413947000','413947000Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="67453_1L" title="Number of days the patient was prescribed antimalarial treatment">
Treatment Duration (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(67453_1)" size="3" maxlength="3"  title="Number of days the patient was prescribed antimalarial treatment" styleId="67453_1" onkeyup="unhideBatchImg('NBS_UI_57');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_57')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_67" name="Treatment Outcome" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV917L" title="Treatment taken as prescribed? Assess 4 weeks after start of treatment">
Was the medicine taken as prescribed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV917)" styleId="INV917" title="Treatment taken as prescribed? Assess 4 weeks after start of treatment">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="313185002L" title="Did all signs or symptoms of malaria resolve without any additional malaria treatment within 7 days after starting treatment? Assess 4 weeks after start of treatment.">
Did all signs or symptoms of malaria resolve within 7 days after starting treatment?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(313185002)" styleId="313185002" title="Did all signs or symptoms of malaria resolve without any additional malaria treatment within 7 days after starting treatment? Assess 4 weeks after start of treatment.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="161917009L" title="Did the patient experience a recurrence of malaria during the 4 weeks after starting malaria treatment">
Was there a recurrence of malaria?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(161917009)" styleId="161917009" title="Did the patient experience a recurrence of malaria during the 4 weeks after starting malaria treatment">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="391103005L" title="Did the subject experience any adverse events within 4 weeks after receiving the malaria treatment? If yes, please complete the adverse event and pre/post-treatment medication questions.">
Did the patient experience any adverse events within 4 weeks of starting malaria treatment?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(391103005)" styleId="391103005" title="Did the subject experience any adverse events within 4 weeks after receiving the malaria treatment? If yes, please complete the adverse event and pre/post-treatment medication questions." onchange="ruleEnDis3911030058512();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_88L">&nbsp;&nbsp;IF PATIENT EXPERIENCED ANY ADVERSE EVENTS WITHIN 4 WEEKS AFTER RECEIVING MALARIA TREATMENT: Complete Medication Information and Adverse Event Information subsections.</span> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_73" name="Medication Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_74L">&nbsp;&nbsp;List all prescription and over-the-counter medicines the patient had taken during the 2 weeks before, or during the 4 weeks after starting malaria treatment.</span> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_70" name="Medications" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_70errorMessages">
<b> <a name="NBS_UI_70errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_70"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_70">
<tr id="patternNBS_UI_70" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_70" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_70');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_70" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_70');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_70" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_70">
<tr id="nopatternNBS_UI_70" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS464L" title="Please list all prescription and over the counter medicines the patient had taken during the 2 weeks before and during the 4 weeks after starting treatment for malaria. If information for both pre- and post-treatment are available, please complete below questions for each time frame.">
Medication Administered:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS464)" size="50" maxlength="50" title="Please list all prescription and over the counter medicines the patient had taken during the 2 weeks before and during the 4 weeks after starting treatment for malaria. If information for both pre- and post-treatment are available, please complete below questions for each time frame." styleId="NBS464" onkeyup="unhideBatchImg('NBS_UI_70');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_70 InputFieldLabel" id="INV1284L" title="Indicate if the patient took the medication 2 weeks before treatment or within the 4 weeks after starting treatment.">
Medication Administered Relative to Treatment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV1284)" styleId="INV1284" title="Indicate if the patient took the medication 2 weeks before treatment or within the 4 weeks after starting treatment." onchange="unhideBatchImg('NBS_UI_70');">
<nedss:optionsCollection property="codedValue(MEDICATION_ADMINISTERED_RELATIVE_TREATMENT_MAL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="91381_4L" title="Medication Start Date">
Medication Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(91381_4)" maxlength="10" size="10" styleId="91381_4" onkeyup="unhideBatchImg('NBS_UI_70');DateMask(this,null,event)" styleClass="NBS_UI_70" title="Medication Start Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('91381_4','91381_4Icon'); unhideBatchImg('NBS_UI_70');return false;" styleId="91381_4Icon" onkeypress="showCalendarEnterKey('91381_4','91381_4Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS423L" title="Medication Stop Date">
Medication Stop Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS423)" maxlength="10" size="10" styleId="NBS423" onkeyup="unhideBatchImg('NBS_UI_70');DateMask(this,null,event)" styleClass="NBS_UI_70" title="Medication Stop Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS423','NBS423Icon'); unhideBatchImg('NBS_UI_70');return false;" styleId="NBS423Icon" onkeypress="showCalendarEnterKey('NBS423','NBS423Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="91383_0L" title="Number of days that patient took/will take the medication referenced.">
Medication Duration (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(91383_0)" size="3" maxlength="3"  title="Number of days that patient took/will take the medication referenced." styleId="91383_0" onkeyup="unhideBatchImg('NBS_UI_70');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_70">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_70">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_70"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_70BatchAddFunction()) writeQuestion('NBS_UI_70','patternNBS_UI_70','questionbodyNBS_UI_70');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_70"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_70')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33" name="Adverse Event Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_33errorMessages">
<b> <a name="NBS_UI_33errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_33"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_33">
<tr id="patternNBS_UI_33" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_33" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_33');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_33" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_33');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_33" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_33','patternNBS_UI_33','questionbodyNBS_UI_33');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_33">
<tr id="nopatternNBS_UI_33" class="odd" style="display:">
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
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="42563_7L" title="Adverse event description">
Adverse event description:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(42563_7)" styleId ="42563_7" onkeyup="checkTextAreaLength(this, 199)" title="Adverse event description"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_33 InputFieldLabel" id="INV918L" title="Is it suspected a causal relationship between the treatment and the adverse event is at least a reasonable possibility">
Is the adverse event related to treatment?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV918)" styleId="INV918" title="Is it suspected a causal relationship between the treatment and the adverse event is at least a reasonable possibility" onchange="unhideBatchImg('NBS_UI_33');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82311_2L" title="Time to onset since starting treatment">
Adverse event time to onset since starting treatment:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82311_2)" size="3" maxlength="3"  title="Time to onset since starting treatment" styleId="82311_2" onkeyup="unhideBatchImg('NBS_UI_33');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_33"/>
<html:select name="PageForm" property="pageClientVO.answer(82311_2Unit)" styleId="82311_2UNIT" onchange="unhideBatchImg('NBS_UI_33')">
<nedss:optionsCollection property="codedValue(DUR_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_33 InputFieldLabel" id="64750_3L" title="Adverse event outcome severity">
Adverse event outcome severity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(64750_3)" styleId="64750_3" title="Adverse event outcome severity" onchange="unhideBatchImg('NBS_UI_33');">
<nedss:optionsCollection property="codedValue(ADVERSE_EVENT_SEVERITY_MAL)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_33">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_33BatchAddFunction()) writeQuestion('NBS_UI_33','patternNBS_UI_33','questionbodyNBS_UI_33')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_33">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_33BatchAddFunction()) writeQuestion('NBS_UI_33','patternNBS_UI_33','questionbodyNBS_UI_33');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_33"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_33BatchAddFunction()) writeQuestion('NBS_UI_33','patternNBS_UI_33','questionbodyNBS_UI_33');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_33"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_33')"/>&nbsp;	&nbsp;&nbsp;
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
