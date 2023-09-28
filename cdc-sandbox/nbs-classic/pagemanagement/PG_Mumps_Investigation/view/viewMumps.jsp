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
String tabId = "viewMumps";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Symptoms","Complications","Laboratory","Epidemiology","Vaccination Information"};
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
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_5" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether the patient is/was symptomatic for mumps?" id="INV576L" >
Did the patient experience any symptoms related to mumps?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have Parotitis?" id="NBS342L" >
Parotitis (opposite 2nd molars)?:</span></td><td>
<span id="NBS342" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS342)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the parotitis is unilateral or bilateral" id="INV301L" >
Parotitis Laterality:</span></td><td>
<span id="INV301" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV301)"
codeSetNm="PHVS_PAROTITISLATERALITY_MUMPS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicates if the parotitis is unilateral or bilateral" id="INV301OthL">Other Parotitis Laterality:</span></td>
<td> <span id="INV301Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV301Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether patient had jaw pain." id="274667000L" >
Jaw Pain?:</span></td><td>
<span id="274667000" />
<nedss:view name="PageForm" property="pageClientVO.answer(274667000)"
codeSetNm="YNU"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="NBS339L" title="Indicate whether the patient had salivary gland swelling.">Salivary Gland Swelling?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS339)" styleId="NBS339"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether patient had submandibular salivary gland swelling." id="271614004L" >
Submandibular salivary gland swelling?:</span></td><td>
<span id="271614004" />
<nedss:view name="PageForm" property="pageClientVO.answer(271614004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had sublingual salivary gland swelling." id="271615003L" >
Sublingual salivary gland swelling?:</span></td><td>
<span id="271615003" />
<nedss:view name="PageForm" property="pageClientVO.answer(271615003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of subjects salivary gland swelling (including parotitis) onset" id="INV288L">Salivary Gland Swelling Onset Date:</span>
</td><td>
<span id="INV288"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV288)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV303L" title="The length of time (days) that the subject exhibited swelling of the salivary gland">
Salivary Gland Swelling Duration in Days:</span>
</td><td>
<span id="INV303"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV303)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have fever?" id="386661006L" >
Fever?:</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV202L" title="What was the subjects highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
<span id="INV202UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicates the date of fever onset" id="INV203L">Date of Fever Onset:</span>
</td><td>
<span id="INV203"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV203)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient have loss of appetite?" id="79890006L" >
Loss of Appetite?:</span></td><td>
<span id="79890006" />
<nedss:view name="PageForm" property="pageClientVO.answer(79890006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have tiredness?" id="267031002L" >
Tiredness?:</span></td><td>
<span id="267031002" />
<nedss:view name="PageForm" property="pageClientVO.answer(267031002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have headache?" id="25064002L" >
Headache?:</span></td><td>
<span id="25064002" />
<nedss:view name="PageForm" property="pageClientVO.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have myalgia (muscle pain)?" id="68962001L" >
Myalgia/Muscle Pain?:</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338L" >
Other symptom(s)?:</span></td><td>
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes pertaining to the symptoms indicated for this case." id="NBS337L">
Symptom Notes:</span>
</td>
<td>
<span id="NBS337"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS337)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_13" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether the patient experienced any complications related to mumps." id="NBS340L" >
Did the patient experience any complications related to mumps?:</span></td><td>
<span id="NBS340" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS340)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Meningitis" id="44201003L" >
Meningitis?:</span></td><td>
<span id="44201003" />
<nedss:view name="PageForm" property="pageClientVO.answer(44201003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Deafness" id="272033007L" >
Deafness?:</span></td><td>
<span id="272033007" />
<nedss:view name="PageForm" property="pageClientVO.answer(272033007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the type of deafness permanent or temporary?" id="INV307L" >
Type of Deafness:</span></td><td>
<span id="INV307" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV307)"
codeSetNm="PHVS_DEAFNESSTYPE_MUMPS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Was the type of deafness permanent or temporary?" id="INV307OthL">Other Type of Deafness:</span></td>
<td> <span id="INV307Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV307Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Orchitis" id="78580004L" >
Orchitis?:</span></td><td>
<span id="78580004" />
<nedss:view name="PageForm" property="pageClientVO.answer(78580004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Encephalitis" id="31646008L" >
Encephalitis?:</span></td><td>
<span id="31646008" />
<nedss:view name="PageForm" property="pageClientVO.answer(31646008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Mastitis" id="237443002L" >
Mastitis?:</span></td><td>
<span id="237443002" />
<nedss:view name="PageForm" property="pageClientVO.answer(237443002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Oophoritis" id="75548002L" >
Oophoritis?:</span></td><td>
<span id="75548002" />
<nedss:view name="PageForm" property="pageClientVO.answer(75548002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Pancreatitis" id="10665004L" >
Pancreatitis?:</span></td><td>
<span id="10665004" />
<nedss:view name="PageForm" property="pageClientVO.answer(10665004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Other Complications" id="NBS343L" >
Other Complication(s)?:</span></td><td>
<span id="NBS343" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS343)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other complicationsh." id="NBS343_OTHL">
Specify Other Complication(s):</span>
</td>
<td>
<span id="NBS343_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS343_OTH)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_15" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the case laboratory confirmed?" id="INV164L" >
Was the case laboratory confirmed?:</span></td><td>
<span id="INV164" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV164)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was a specimen sent to CDC for testing?" id="LAB515L" >
Was a specimen sent to CDC for testing?:</span></td><td>
<span id="LAB515" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB515)"
codeSetNm="YNU"/>
</td> </tr>
<!--skipping Hidden Date Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_16" name="Lab Interpretive Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_16"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_16">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_16" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_16');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_NBS_INV_MUM_UI_16">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_16" class="odd" style="display:none">
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
<span title="Test Type" id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_MUMPS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Test Type" id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Test Result (Qualitative)" id="INV291L" >
Test Result (Qualitative):</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_VPD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Test Result (Qualitative)" id="INV291OthL">Other Test Result (Qualitative):</span></td>
<td> <span id="INV291Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV291Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Quantitative Test Result Value." id="LAB628L">
Test Result (Quantitative):</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Quantitative Test Result Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
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
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165L" >
Specimen Source:</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="SPECIMENTYPEVPD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB165Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date specimen sent to CDC" id="LAB516L">Date Specimen Sent to CDC:</span>
</td><td>
<span id="LAB516"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB516)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the performing laboratory type" id="LAB606L" >
Performing Lab Type:</span></td><td>
<span id="LAB606" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_VPD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Lab Type:</span></td>
<td> <span id="LAB606Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB606Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_17" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_17"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_17">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_17" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_MUM_UI_17');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_NBS_INV_MUM_UI_17">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_17" class="odd" style="display:none">
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
<span title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message" id="LAB143L" >
VPD Lab Message Reference Laboratory:</span></td><td>
<span id="LAB143" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB143)"
codeSetNm="VPD_LAB_REFERENCE"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message" id="LAB598L">
VPD Lab Message Patient Identifier:</span>
</td>
<td>
<span id="LAB598"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB598)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message" id="LAB125L">
VPD Lab Message Specimen Identifier:</span>
</td>
<td>
<span id="LAB125"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB125)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_19" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="DEM225L" title="Indicate the patients length of time in the U.S. since the last travel">
Length of time in the U.S. since last travel:</span>
</td><td>
<span id="DEM225"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM225)"  />
<span id="DEM225UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(DEM225Unit)" codeSetNm="DUR_UNIT" />
</td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL05L" title="List any international destinations of recent travel">
International Destination(s) of Recent Travel:</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of return from most recent travel" id="TRAVEL08L">Date of Return from Travel:</span>
</td><td>
<span id="TRAVEL08"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL08)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What was the transmission setting where the condition was acquired?" id="INV224L" >
Transmission Setting:</span></td><td>
<span id="INV224" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV224)"
codeSetNm="PHVS_TRAN_SETNG"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What was the transmission setting where the condition was acquired?" id="INV224OthL">Other Transmission Setting:</span></td>
<td> <span id="INV224Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV224Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the age of the case match or make sense for the transmission setting listed?" id="INV216L" >
Were age and setting verified?:</span></td><td>
<span id="INV216" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV216)"
codeSetNm="YNU"/>
</td> </tr>
<!--skipping Hidden Text Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did parotitis or other mumps-associated complication onset occur within 12-25 days of entering the U.S., following any travel or living outside U.S.? (Import Status)" id="INV293L" >
Symptom onset 12-25 days of entering U.S., following travel/living outside U.S.? (Import Status):</span></td><td>
<span id="INV293" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV293)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If this is a U.S.-acquired case, how should the case be classified by source?" id="INV516L" >
If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span></td><td>
<span id="INV516" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV516)"
codeSetNm="PHVS_CASECLASSIFICATIONEXPOSURESOURCE_NND"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If this is a U.S.-acquired case, how should the case be classified by source?" id="INV516OthL">Other If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span></td>
<td> <span id="INV516Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV516Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is this case epi-linked to another confirmed or probable case?" id="INV217L" >
Is this case epi-linked to another confirmed or probable case?:</span></td><td>
<span id="INV217" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV217)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_22" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient ever receive mumps-containing vaccine?" id="VAC126L" >
Did patient ever receive mumps-containing vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC129L" title="Indicate the number of vaccine doses against mumps the patient received on or after his or her first birthday. To indicate that the number of doses is unknown, enter 99.">
Number of doses received ON or AFTER first birthday:</span>
</td><td>
<span id="VAC129"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC129)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC140L" title="Indicate the number of vaccine doses against mumps the patient received prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Number of vaccine doses against mumps received prior to illness onset:</span>
</td><td>
<span id="VAC140"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC140)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset." id="VAC142L">Date of last dose prior to illness onset:</span>
</td><td>
<span id="VAC142"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC142)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules." id="VAC148L" >
Was the patient vaccinated per ACIP recommendations?:</span></td><td>
<span id="VAC148" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the reason the patient was not vaccinated as recommended by ACIP." id="VAC149L" >
Reason patient not vaccinated per ACIP recommendations:</span></td><td>
<span id="VAC149" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC149)"
codeSetNm="PHVS_VAC_NOTG_RSN"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate the reason the patient was not vaccinated as recommended by ACIP." id="VAC149OthL">Other Reason patient not vaccinated per ACIP recommendations:</span></td>
<td> <span id="VAC149Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(VAC149Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form." id="VAC133L">
Notes pertaining to the patient's vaccination history:</span>
</td>
<td>
<span id="VAC133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC133)"  />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
