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
String tabId = "viewLyme";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Manifestations","Treatment","Laboratory"};
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
<nedss:container id="NBS_UI_42" name="Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Erythema chronica migrans?" id="271807003L" >
Erythema Migrans:</span></td><td>
<span id="271807003" />
<nedss:view name="PageForm" property="pageClientVO.answer(271807003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Atrioventricular block?" id="233917008L" >
Atrioventricular block:</span></td><td>
<span id="233917008" />
<nedss:view name="PageForm" property="pageClientVO.answer(233917008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Lyme arthritis?" id="3723001L" >
Arthritis:</span></td><td>
<span id="3723001" />
<nedss:view name="PageForm" property="pageClientVO.answer(3723001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Bells palsy?" id="193093009L" >
Bells palsy:</span></td><td>
<span id="193093009" />
<nedss:view name="PageForm" property="pageClientVO.answer(193093009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Other Cranial neuritis?" id="48780006L" >
Other Cranial neuritis:</span></td><td>
<span id="48780006" />
<nedss:view name="PageForm" property="pageClientVO.answer(48780006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Radiculoneuropathy (Nerve root disorder)?" id="72274001L" >
Radiculoneuropathy:</span></td><td>
<span id="72274001" />
<nedss:view name="PageForm" property="pageClientVO.answer(72274001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Lymphocytic Meningitis?" id="44201003L" >
Lymphocytic Meningitis:</span></td><td>
<span id="44201003" />
<nedss:view name="PageForm" property="pageClientVO.answer(44201003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the Patient have Encephalitis?" id="31646008L" >
Encephalitis:</span></td><td>
<span id="31646008" />
<nedss:view name="PageForm" property="pageClientVO.answer(31646008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Encephalomyelitis?" id="62950007L" >
Encephalomyelitis:</span></td><td>
<span id="62950007" />
<nedss:view name="PageForm" property="pageClientVO.answer(62950007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338L" >
Other symptom(s):</span></td><td>
<span id="NBS338" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other signs and symptoms." id="NBS338_OTHL">
Specify Other Symptom(s):</span>
</td>
<td>
<span id="NBS338_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338_OTH)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive antibiotics treatment for this infection?" id="255633001L" >
Did the patient receive antibiotic treatment for this infection?:</span></td><td>
<span id="255633001" />
<nedss:view name="PageForm" property="pageClientVO.answer(255633001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_45" name="Antibiotics Treatment Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_45"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_45">
<tr id="patternNBS_UI_45" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_45');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_45">
<tr id="nopatternNBS_UI_45" class="odd" style="display:none">
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
<span title="What antibiotic was prescribed to the patient for this infection?" id="29303_5L" >
Medication Administered:</span></td><td>
<span id="29303_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(29303_5)"
codeSetNm="MEDICATION_RECEIVED_LYME"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What antibiotic was prescribed to the patient for this infection?" id="29303_5OthL">Other Medication Administered:</span></td>
<td> <span id="29303_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(29303_5Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the treatment was prescribed" id="INV924L">Treatment Start Date:</span>
</td><td>
<span id="INV924"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV924)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="67453_1L" title="Prescribed duration (in days) of antibiotic treatment">
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
<nedss:container id="NBS_UI_47" name="Lab Testing" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_UI_48" name="Interpretative Lab Data Repeating  Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_48"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_48">
<tr id="patternNBS_UI_48" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_48');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_48">
<tr id="nopatternNBS_UI_48" class="odd" style="display:none">
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
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="LAB_TEST_TYPE_LYME"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)" id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="LAB_TEST_INTERPRETATION_LYME"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, negative, not done)" id="INV291OthL">Other Test Result:</span></td>
<td> <span id="INV291Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV291Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Result Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Species identified through testing" id="LAB278L" >
Organism Name:</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB278)"
codeSetNm="ORGANISM_LYME"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." id="667469L" >
Specimen Type:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="SPECIMEN_TYPE_LYME"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If Other Arbovirus PCR (Not serum / CSF) was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." id="667469OthL">Other Specimen Type:</span></td>
<td> <span id="667469Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(667469Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report." id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
