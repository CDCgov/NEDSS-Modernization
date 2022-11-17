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
String tabId = "viewMalaria";
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests:</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;(i) blood smear with the highest percentage parasitemia,</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;(ii) the test that indicates the Plasmodium species, and</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;(iii) a confirmatory PCR (if applicable).</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_59" name="Lab Data Entry Guidance" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If there are conflicting lab results for the species identification, then include only the test with the final result. Multiple species can be selected for one test.</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If the species determination is inconclusive, select 'Not Determined.' If there is a suspicion towards a particular species (e.g. non-falciparum), select 'Not Determined' and 'Other' and write the suspected species in the 'Other Organism Name' text box.</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;--Example A: 50 infected erythrocytes out of 1000 counted = 5% parasitemia, and the submitted response should "5".</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;--Example B: 2 infected erythrocytes out of 2000 counted = 0.1% parasitemia and the submitted response should be 0.1.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was laboratory testing done to confirm the diagnosis" id="INV740L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_79" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_79">
<tr id="patternNBS_UI_79" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_79');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_79">
<tr id="nopatternNBS_UI_79" class="odd" style="display:none">
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="LAB_TEST_PROCEDURE_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Result of Diagnostic Test" id="INV291L" >
Result of Diagnostic Test:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_PNUND"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="LAB278L" title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section.">
Organism Name (see tool tip for guidance):</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB278)"
codeSetNm="SPECIES_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section." id="LAB278OthL">Other Organism Name (see tool tip for guidance):</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="53556_7L" title="The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.">
For blood smear tests, what is the highest percentage parasitemia:</span>
</td><td>
<span id="53556_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(53556_7)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date result sent from reporting laboratory. Time of result is an optional addition to date." id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Performing laboratory name" id="68994_3L">
Performing laboratory name:</span>
</td>
<td>
<span id="68994_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(68994_3)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Reporting laboratory phone number" id="65651_2L">
Laboratory Phone Number:</span>
</td>
<td>
<span id="65651_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(65651_2)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Specimen Type Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was Specimen(s) sent to CDC?" id="LAB515L" >
Specimen(s) sent to CDC:</span></td><td>
<span id="LAB515" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB515)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Sent to CDC" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_39">
<tr id="patternNBS_UI_39" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_39');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_39">
<tr id="nopatternNBS_UI_39" class="odd" style="display:none">
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Type(s) of specimen sent to CDC" id="667469L" >
Type(s) of Specimen sent to CDC:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="SPECIMEN_TYPE_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Type(s) of specimen sent to CDC" id="667469OthL">Other Type(s) of Specimen sent to CDC:</span></td>
<td> <span id="667469Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(667469Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456." id="INV965L">
CDC Specimen ID Number:</span>
</td>
<td>
<span id="INV965"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV965)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Where does the person usually live (defined as their residence)." id="INV501L" >
Country of Usual Residence:</span></td><td>
<span id="INV501" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="TRAVEL14L" title="Reside in US Prior to Most Recent Travel">Legacy - Did the patient reside in the U.S. prior to most recent travel?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(TRAVEL14)" styleId="TRAVEL14"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="MAL135L" title="Principal Reason for Travel question created for Malaria Porting only.">Legacy - Principal Reason for Travel:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(MAL135)" styleId="MAL135"><html:optionsCollection property="codedValue(TRAVEL_REASON_MAL)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the patient traveled or lived outside the U.S. during the past two years" id="TRAVEL10L" >
Has the patient traveled or lived outside the U.S. during the past two years?:</span></td><td>
<span id="TRAVEL10" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL10)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What was the patient's country of residence prior to most recent travel?" id="TRAVEL15L" >
What was the patient's country of residence prior to most recent travel?:</span></td><td>
<span id="TRAVEL15" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL15)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_41" name="Travel History Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_41">
<tr id="patternNBS_UI_41" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_41');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_41">
<tr id="nopatternNBS_UI_41" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;If the patient traveled outside of the US or lived outside of the US within the last 2 years:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" id="TRAVEL05L" >
Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05)"
codeSetNm="GEOGRAPHIC_LOCATION_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" id="TRAVEL05OthL">Other Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span></td>
<td> <span id="TRAVEL05Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05Oth)"/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="82310_4L" title="Duration of stay in country outside the US">
Duration of Stay in Country Outside the US:</span>
</td><td>
<span id="82310_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82310_4)"  />
<span id="82310_4UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82310_4Unit)" codeSetNm="PHVS_DURATIONUNIT_CDC_1" />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date Returned to/Arrive in US" id="TRAVEL08L">Date Returned to/Arrive in US:</span>
</td><td>
<span id="TRAVEL08"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL08)"  />
</td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Date Question-->

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL16L" title="Reason for travel related to current illness">
Reasons for Travel:</span></td><td>
<span id="TRAVEL16" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL16)"
codeSetNm="TRAVEL_REASON_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Reason for travel related to current illness" id="TRAVEL16OthL">Other Reasons for Travel:</span></td>
<td> <span id="TRAVEL16Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="TRAVEL_MODE_MAL"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Additional Travel Information" id="TRAVEL23L">
Additional Travel Information:</span>
</td>
<td>
<span id="TRAVEL23"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL23)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35" name="Chemoprophylaxis Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Complete this section for anti-malarial medications taken prior to or during travel.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?" id="182929008L" >
Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?:</span></td><td>
<span id="182929008" />
<nedss:view name="PageForm" property="pageClientVO.answer(182929008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV931L" title="Chemoprophylaxis Medication(s)">
List chemoprophylaxis medications taken by the patient:</span></td><td>
<span id="INV931" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV931)"
codeSetNm="MEDICATION_PROPHYLAXIS_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Chemoprophylaxis Medication(s)" id="INV931OthL">Other List chemoprophylaxis medications taken by the patient:</span></td>
<td> <span id="INV931Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV931Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was chemoprophylaxis taken as prescribed? (Yes = missed no doses, No = missed doses)" id="INV309L" >
Was chemoprophylaxis taken as prescribed (without any skipped doses)?:</span></td><td>
<span id="INV309" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV309)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV932L" title="Reason for missed chemoprophylaxis">
Reason(s) doses were missed:</span></td><td>
<span id="INV932" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV932)"
codeSetNm="MEDICATION_MISSED_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Reason for missed chemoprophylaxis" id="INV932OthL">Other Reason(s) doses were missed:</span></td>
<td> <span id="INV932Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV932Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="If reason for missed doses is due to a side effect, then specify side effect" id="INV921L">
Specify side effects of missed dose(s):</span>
</td>
<td>
<span id="INV921"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV921)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43" name="Previous Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a previous history of malaria in the last 12 months, prior to this report?" id="161413004L" >
Did the patient have a previous history of malaria in the last 12 months, prior to this report?:</span></td><td>
<span id="161413004" />
<nedss:view name="PageForm" property="pageClientVO.answer(161413004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Previous Malaria Illness" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_44">
<tr id="patternNBS_UI_44" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_44');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_44">
<tr id="nopatternNBS_UI_44" class="odd" style="display:none">
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

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV914L" title="Malaria species associated with previous illness">
Organism Associated with Previous Illness:</span></td><td>
<span id="INV914" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV914)"
codeSetNm="SPECIES_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Malaria species associated with previous illness" id="INV914OthL">Other Organism Associated with Previous Illness:</span></td>
<td> <span id="INV914Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV914Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of previous malaria illness" id="82758_4L">Date of Previous Malaria Illness:</span>
</td><td>
<span id="82758_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82758_4)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_56" name="Blood Transfusion/Organ Transplant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?" id="82312_0L" >
Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?:</span></td><td>
<span id="82312_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(82312_0)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of blood transfusion/organ transplant" id="80989_7L">Date of blood transfusion/organ transplant:</span>
</td><td>
<span id="80989_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(80989_7)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_82" name="Clinical Complications" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="67187_5L" title="Complication(s) related to this malaria illness">
Complication(s) Related to this Malaria Illness:</span></td><td>
<span id="67187_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(67187_5)"
codeSetNm="COMPLICATIONS_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Complication(s) related to this malaria illness" id="67187_5OthL">Other Complication(s) Related to this Malaria Illness:</span></td>
<td> <span id="67187_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(67187_5Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_71" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;List all malaria medications taken for this illness.</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Therapy for this Attack" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_57">
<tr id="patternNBS_UI_57" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_57');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_57">
<tr id="nopatternNBS_UI_57" class="odd" style="display:none">
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Listing of treatment the subject received for this illness" id="55753_8L" >
Treatment the Patient Received:</span></td><td>
<span id="55753_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(55753_8)"
codeSetNm="MEDICATION_TREATMENT_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Listing of treatment the subject received for this illness" id="55753_8OthL">Other Treatment the Patient Received:</span></td>
<td> <span id="55753_8Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(55753_8Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the treatment was initiated" id="INV924L">Treatment Start Date:</span>
</td><td>
<span id="INV924"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV924)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date treatment stopped" id="413947000L">Treatment Stop Date:</span>
</td><td>
<span id="413947000"/>
<nedss:view name="PageForm" property="pageClientVO.answer(413947000)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="67453_1L" title="Number of days the patient was prescribed antimalarial treatment">
Treatment Duration (in days):</span>
</td><td>
<span id="67453_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(67453_1)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_67" name="Treatment Outcome" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Treatment taken as prescribed? Assess 4 weeks after start of treatment" id="INV917L" >
Was the medicine taken as prescribed?:</span></td><td>
<span id="INV917" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV917)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did all signs or symptoms of malaria resolve without any additional malaria treatment within 7 days after starting treatment? Assess 4 weeks after start of treatment." id="313185002L" >
Did all signs or symptoms of malaria resolve within 7 days after starting treatment?:</span></td><td>
<span id="313185002" />
<nedss:view name="PageForm" property="pageClientVO.answer(313185002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience a recurrence of malaria during the 4 weeks after starting malaria treatment" id="161917009L" >
Was there a recurrence of malaria?:</span></td><td>
<span id="161917009" />
<nedss:view name="PageForm" property="pageClientVO.answer(161917009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject experience any adverse events within 4 weeks after receiving the malaria treatment? If yes, please complete the adverse event and pre/post-treatment medication questions." id="391103005L" >
Did the patient experience any adverse events within 4 weeks of starting malaria treatment?:</span></td><td>
<span id="391103005" />
<nedss:view name="PageForm" property="pageClientVO.answer(391103005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;IF PATIENT EXPERIENCED ANY ADVERSE EVENTS WITHIN 4 WEEKS AFTER RECEIVING MALARIA TREATMENT: Complete Medication Information and Adverse Event Information subsections.</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_73" name="Medication Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;List all prescription and over-the-counter medicines the patient had taken during the 2 weeks before, or during the 4 weeks after starting malaria treatment.</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_70" name="Medications" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_70">
<tr id="patternNBS_UI_70" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_70');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_70">
<tr id="nopatternNBS_UI_70" class="odd" style="display:none">
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
<span title="Please list all prescription and over the counter medicines the patient had taken during the 2 weeks before and during the 4 weeks after starting treatment for malaria. If information for both pre- and post-treatment are available, please complete below questions for each time frame." id="NBS464L">
Medication Administered:</span>
</td>
<td>
<span id="NBS464"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS464)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the patient took the medication 2 weeks before treatment or within the 4 weeks after starting treatment." id="INV1284L" >
Medication Administered Relative to Treatment:</span></td><td>
<span id="INV1284" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1284)"
codeSetNm="MEDICATION_ADMINISTERED_RELATIVE_TREATMENT_MAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Medication Start Date" id="91381_4L">Medication Start Date:</span>
</td><td>
<span id="91381_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(91381_4)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Medication Stop Date" id="NBS423L">Medication Stop Date:</span>
</td><td>
<span id="NBS423"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS423)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="91383_0L" title="Number of days that patient took/will take the medication referenced.">
Medication Duration (in days):</span>
</td><td>
<span id="91383_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(91383_0)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33" name="Adverse Event Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_33">
<tr id="patternNBS_UI_33" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_33');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_33">
<tr id="nopatternNBS_UI_33" class="odd" style="display:none">
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
<span title="Adverse event description" id="42563_7L">
Adverse event description:</span>
</td>
<td>
<span id="42563_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(42563_7)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is it suspected a causal relationship between the treatment and the adverse event is at least a reasonable possibility" id="INV918L" >
Is the adverse event related to treatment?:</span></td><td>
<span id="INV918" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV918)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="82311_2L" title="Time to onset since starting treatment">
Adverse event time to onset since starting treatment:</span>
</td><td>
<span id="82311_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82311_2)"  />
<span id="82311_2UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82311_2Unit)" codeSetNm="DUR_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Adverse event outcome severity" id="64750_3L" >
Adverse event outcome severity:</span></td><td>
<span id="64750_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(64750_3)"
codeSetNm="ADVERSE_EVENT_SEVERITY_MAL"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
