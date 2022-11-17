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
String tabId = "viewCP-CRE";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Travel","Laboratory","Previous History"};
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
<nedss:container id="NBS_UI_34" name="Travel and Healthcare" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the YEAR prior to specimen collection:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient travel internationally in the past 1 year from the date of specimen collection?" id="TRAVEL38L" >
Did the patient travel internationally?:</span></td><td>
<span id="TRAVEL38" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL38)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL05L" title="List any international destinations of recent travel">
International Destination(s):</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection." id="90366_6L" >
Received Healthcare Outside the USA:</span></td><td>
<span id="90366_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(90366_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="91514_0L" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection.">
Countries in which healthcare was received:</span></td><td>
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
<nedss:container id="NBS_UI_37" name="Laboratory Testing Information" isHidden="F" classType="subSect" >
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
<span title="Unique isolate or specimen identifier; ID assigned by the local laboratory. Isolate ID is preferred, but if unknown specimen ID is acceptable." id="NBS674L">
Performing Lab Specimen or Isolate ID:</span>
</td>
<td>
<span id="NBS674"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS674)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Lab isolate identifier from public health lab for mechanism testing" id="FDD_Q_1141L">
State Lab Isolate ID:</span>
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
<span title="This indicates the type of specimen tested." id="667469L" >
Specimen Type:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="SPECIMEN_CP_CRE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="LAB_TEST_TYPE_CP_CRE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Result of diagnostic test" id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="TEST_RESULT_CP_CRE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Species identified through testing" id="LAB278L" >
Organism Name:</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB278)"
codeSetNm="ORGANISM_CP_CRE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." id="85069_3L" >
Test Method:</span></td><td>
<span id="85069_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(85069_3)"
codeSetNm="LAB_TEST_METHOD_CP_CRE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." id="85069_3OthL">Other Test Method:</span></td>
<td> <span id="85069_3Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(85069_3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Gene identifier" id="48018_6L" >
Gene Identifier:</span></td><td>
<span id="48018_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(48018_6)"
codeSetNm="GENE_NAME_CP_CRE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Gene identifier" id="48018_6OthL">Other Gene Identifier:</span></td>
<td> <span id="48018_6Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(48018_6Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If multiple specimens were collected, County/State should be for the first positive specimen associated with this incident of disease.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="County of facility where specimen was collected" id="89202_6L" >
County of facility where specimen was collected:</span></td><td>
<span id="89202_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(89202_6)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="State of facility where specimen was collected" id="68488_6L" >
State of facility where specimen was collected:</span></td><td>
<span id="68488_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(68488_6)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Previously Counted Case" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Clinical CP-CRE Only: Was patient previously counted as a colonization/screening case?" id="INV1109L" >
Previously counted as a colonization/screening case?:</span></td><td>
<span id="INV1109" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1109)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Case IDs" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Please provide the related case ID(s) if patient was previously counted as a colonization/screening case.</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" id="INV1110_1L">
Previously Reported State Case Number 1:</span>
</td>
<td>
<span id="INV1110_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_1)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" id="INV1110_2L">
Previously Reported State Case Number 2:</span>
</td>
<td>
<span id="INV1110_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1110_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" id="INV1110_3L">
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
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
