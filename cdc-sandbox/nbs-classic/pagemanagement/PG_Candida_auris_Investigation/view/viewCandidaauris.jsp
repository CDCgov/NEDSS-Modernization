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
String tabId = "viewCandidaauris";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Travel and Healthcare","Laboratory Information","Previous History","Exposure History"};
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
<nedss:container id="NBS_UI_34" name="Travel and Healthcare" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the YEAR prior to specimen collection:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the patient received overnight healthcare within the United States, but outside of the patient's resident state in the year prior to the date of specimen collection." id="91515_7L" >
Was Overnight Healthcare Received within the USA, but outside the patient's state of residence?:</span></td><td>
<span id="91515_7" />
<nedss:view name="PageForm" property="pageClientVO.answer(91515_7)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient travel internationally in the year prior to the date of specimen collection?" id="TRAVEL38L" >
Did the patient travel internationally?:</span></td><td>
<span id="TRAVEL38" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL38)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL05L" title="List the names of the country(ies) outside of the United States the patient traveled to in the year prior to the date of specimen collection, if the patient traveled outside of the United States during that time.">
International Destination(s):</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="This data element is used to capture if the patient received overnight healthcare outside of the United States in the year prior to the date of specimen collection." id="90366_6L" >
Received Overnight Healthcare Outside the USA:</span></td><td>
<span id="90366_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(90366_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="91514_0L" title="This data element is used to capture if the patient received overnight healthcare outside of the United States in the year prior to the date of specimen collection.">
Countries in which overnight healthcare was received:</span></td><td>
<span id="91514_0" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(91514_0)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36" name="Laboratory Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Lab Testing Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_37"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_37">
<tr id="patternNBS_UI_37" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_37');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_37">
<tr id="nopatternNBS_UI_37" class="odd" style="display:none">
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
<span title="Please enter the performing lab specimen ID number for this lab test." id="NBS674L">
Performing Laboratory Specimen ID:</span>
</td>
<td>
<span id="NBS674"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS674)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State laboratory specimen identification number." id="FDD_Q_1141L">
State Lab Specimen ID:</span>
</td>
<td>
<span id="FDD_Q_1141"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_1141)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="NCBI SRA Accession Number (SRX#), the accession number generated by NCBI’s Sequence Read Archive when sequence data are uploaded to NCBI. This provides both the sequence data and metadata on how the sample was sequenced." id="INV949L">
NCBI SRA Accession Number (SRX #):</span>
</td>
<td>
<span id="INV949"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV949)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the type of specimen tested." id="667469L" >
Specimen Type:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="SPECIMEN_C_AURIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If specimen type is unspecified swab, please provide anatomical site of swab." id="LAB165L" >
Anatomical Site of Swab (if Specimen Type is unspecified swab):</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="SPECIMEN_SOURCE_C_AURIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If specimen type is unspecified swab, please provide anatomical site of swab." id="LAB165OthL">Other Anatomical Site of Swab (if Specimen Type is unspecified swab):</span></td>
<td> <span id="LAB165Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB165Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="LAB_TEST_TYPE_C_AURIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result (e.g, positive, negative)." id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="TEST_RESULT_C_AURIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify the test method (Text) for the type of test performed for this case (e.g., test brand or software, or type of PCR)." id="NBS482L">
Test Method:</span>
</td>
<td>
<span id="NBS482"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS482)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Specimen Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If multiple specimens were collected, Location/County/State should be for the first positive specimen associated with this incident of disease</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the physical location type of the patient when the specimen was collected." id="90041_5L" >
Location of Specimen Collection:</span></td><td>
<span id="90041_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(90041_5)"
codeSetNm="SPECIMEN_COLLECTION_SETTING_TYPE_C_AURIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate the physical location type of the patient when the specimen was collected." id="90041_5OthL">Other Location of Specimen Collection:</span></td>
<td> <span id="90041_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(90041_5Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="State of facility where specimen was collected" id="68488_6L" >
State of facility where specimen was collected:</span></td><td>
<span id="68488_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(68488_6)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="County of facility where specimen was collected" id="89202_6L" >
County of facility where specimen was collected:</span></td><td>
<span id="89202_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(89202_6)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="County of facility where specimen was collected" id="89202_6OthL">Other County of facility where specimen was collected:</span></td>
<td> <span id="89202_6Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(89202_6Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_45" name="Previously Counted Case" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was patient previously counted as a colonization/screening case?" id="INV1109L" >
Previously counted as a colonization or screening case?:</span></td><td>
<span id="INV1109" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1109)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47" name="Case IDs" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Please provide the related Previously Reported Case ID(s) if patient was previously counted as a colonization/screening case of C. auris or a CP-CRE case.</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical C. auris only) or a CP-CRE case, please provide the related case ID(s)." id="INV1110_1L">
Previously Reported State Case Number 1:</span>
</td>
<td>
<span id="INV1110_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_1)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical C. auris only) or a CP-CRE case, please provide the related case ID(s)." id="INV1110_2L">
Previously Reported State Case Number 2:</span>
</td>
<td>
<span id="INV1110_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical C. auris only) or a CP-CRE case, please provide the related case ID(s)." id="INV1110_3L">
Previously Reported State Case Number 3:</span>
</td>
<td>
<span id="INV1110_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_3)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)" id="INV1110_4L">
Previously Reported State Case Number 4:</span>
</td>
<td>
<span id="INV1110_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_4)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)." id="INV1110_5L">
Previously Reported State Case Number 5:</span>
</td>
<td>
<span id="INV1110_5"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_5)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_41" name="History of Infection" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of infection or colonization with another MDRO?" id="INV1270L" >
History of infection or colonization with another MDRO?:</span></td><td>
<span id="INV1270" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1270)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV831L" title="If patient has a history of infection or colonization with another MDRO, indicate the MDRO.">
If patient has a history of infection or colonization with another MDRO, indicate the MDRO:</span></td><td>
<span id="INV831" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV831)"
codeSetNm="TYPE_COINFECTION_C_AURIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If patient has a history of infection or colonization with another MDRO, indicate the MDRO." id="INV831OthL">Other If patient has a history of infection or colonization with another MDRO, indicate the MDRO:</span></td>
<td> <span id="INV831Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV831Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Exposure History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient have a tracheostomy tube at the time of specimen collection?" id="448621002L" >
At the time of specimen collection, did patient have a tracheostomy tube?:</span></td><td>
<span id="448621002" />
<nedss:view name="PageForm" property="pageClientVO.answer(448621002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was patient on a ventilator at the time of specimen collection?" id="250870006L" >
At the time of specimen collection, was patient on a ventilator?:</span></td><td>
<span id="250870006" />
<nedss:view name="PageForm" property="pageClientVO.answer(250870006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a stay in a long-term care facility in the 90 days before specimen collection date?" id="INV636L" >
In the 90 days prior to specimen collection date, did the patient stay in a long-term care facility?:</span></td><td>
<span id="INV636" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV636)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV1120L" title="If patient had a stay in a long-term care facility in the 90 days before specimen collection date, indicate the type of long-term care facility.">
Long-term Care Facility Type:</span></td><td>
<span id="INV1120" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV1120)"
codeSetNm="LONG_TERM_CARE_FACILITY_TYPE_C_AURIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If patient had a stay in a long-term care facility in the 90 days before specimen collection date, indicate the type of long-term care facility." id="INV1120OthL">Other Long-term Care Facility Type:</span></td>
<td> <span id="INV1120Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV1120Oth)"/></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
