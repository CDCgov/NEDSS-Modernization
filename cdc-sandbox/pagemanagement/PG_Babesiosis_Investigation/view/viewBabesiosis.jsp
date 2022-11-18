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
String tabId = "viewBabesiosis";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Manifestations","Complications","Medical History","Treatment","Blood Transfusion and Donation","Epidemiology","Laboratory"};
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
<nedss:container id="NBS_UI_37" name="Clinical Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience any clinical manifestation related to babesiosis?" id="INV576L" >
Was the patient symptomatic?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Objective Clinical Manifestations:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a fever?" id="386661006L" >
Fever:</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV202L" title="What was the patient's highest measured temperature?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
<span id="INV202UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have anemia?" id="271737000L" >
Anemia:</span></td><td>
<span id="271737000" />
<nedss:view name="PageForm" property="pageClientVO.answer(271737000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have thrombocytopenic disorder?" id="302215000L" >
Thrombocytopenia:</span></td><td>
<span id="302215000" />
<nedss:view name="PageForm" property="pageClientVO.answer(302215000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Subjective Clinical Manifestations:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have chills?" id="43724002L" >
Chills:</span></td><td>
<span id="43724002" />
<nedss:view name="PageForm" property="pageClientVO.answer(43724002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have headache?" id="25064002L" >
Headache:</span></td><td>
<span id="25064002" />
<nedss:view name="PageForm" property="pageClientVO.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a arthralgia or joint pain?" id="57676002L" >
Arthralgia/Joint Pain:</span></td><td>
<span id="57676002" />
<nedss:view name="PageForm" property="pageClientVO.answer(57676002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have myalgia or muscle pain?" id="68962001L" >
Myalgia/Muscle Pain:</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience sweating?" id="415690000L" >
Sweating:</span></td><td>
<span id="415690000" />
<nedss:view name="PageForm" property="pageClientVO.answer(415690000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified?" id="NBS338L" >
Other Symptom(s):</span></td><td>
<span id="NBS338" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify Other signs and symptoms" id="NBS338_OTHL">
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
<nedss:container id="NBS_UI_39" name="Complications" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="67187_5L" title="Complications of Babesiosis">
Type of Complications:</span></td><td>
<span id="67187_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(67187_5)"
codeSetNm="PHVS_COMPLICATIONS_BABESIOSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Complications of Babesiosis" id="67187_5OthL">Other Type of Complications:</span></td>
<td> <span id="67187_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(67187_5Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35" name="Previous Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Patient being without a spleen or without a functional spleen" id="300564004L" >
Is the patient asplenic?:</span></td><td>
<span id="300564004" />
<nedss:view name="PageForm" property="pageClientVO.answer(300564004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Splenectomy Date" id="82760_0L">Splenectomy Date:</span>
</td><td>
<span id="82760_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82760_0)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Why was the patient's spleen removed?" id="82759_2L">
Why was the patient's spleen removed?:</span>
</td>
<td>
<span id="82759_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82759_2)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a history of babesiosis?" id="161413004L" >
Did the patient have a history of babesiosis?:</span></td><td>
<span id="161413004" />
<nedss:view name="PageForm" property="pageClientVO.answer(161413004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_70" name="Date of Previous Illness" isHidden="F" classType="subSect" >
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

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of previous babesiosis diagnosis" id="82758_4L">Date of Previous Babesiosis Diagnosis:</span>
</td><td>
<span id="82758_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82758_4)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Patient Immunocompromised" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="At the time of diagnosis, was the patient immunocompromised?" id="VAR126L" >
Immunocompromised at time of diagnosis:</span></td><td>
<span id="VAR126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAR126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If there are multiple Answers for the below question please separate the answers using a semicolon (;) (e.g., ABC;XYZ)</td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="If the patient was immunocompromised, what was the associated condition or treatment?" id="VAR127L">
Condition and/or Treatment:</span>
</td>
<td>
<span id="VAR127"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR127)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_65" name="Organ Transplant" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the month before symptom onset or diagnosis (use earlier date), did the patient receive an organ transplant" id="ARB008L" >
In the month before symptoms onset or diagnosis, did the patient receive an organ transplant?:</span></td><td>
<span id="ARB008" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_45" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive antimicrobial treatment for this infection?" id="255633001L" >
Did the patient receive antimicrobial treatment for this infection?:</span></td><td>
<span id="255633001" />
<nedss:view name="PageForm" property="pageClientVO.answer(255633001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="29303_5L" title="Treatment drugs used for babesiosis">
Medications Received:</span></td><td>
<span id="29303_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(29303_5)"
codeSetNm="PHVS_MEDICATIONTREATMENT_BABESIOSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Treatment drugs used for babesiosis" id="29303_5OthL">Other Medications Received:</span></td>
<td> <span id="29303_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(29303_5Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_48" name="Blood Transfusion" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the year before symptom onset or diagnosis (use earlier date), did the patient receive a blood transfusion?" id="ARB006L" >
In the year before symptom onset or diagnosis, did the patient receive a blood transfusion?:</span></td><td>
<span id="ARB006" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient's infection transfusion associated?" id="418912005L" >
Was the patient's infection transfusion associated?:</span></td><td>
<span id="418912005" />
<nedss:view name="PageForm" property="pageClientVO.answer(418912005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="933_2L" title="If a transfused blood product was implicated in an investigation, specify which types of product">
Type of transfused blood product implicated:</span></td><td>
<span id="933_2" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(933_2)"
codeSetNm="PHVS_BLOODPRODUCT_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If a transfused blood product was implicated in an investigation, specify which types of product" id="933_2OthL">Other Type of transfused blood product implicated:</span></td>
<td> <span id="933_2Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(933_2Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_63" name="Blood Transfusion Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_63"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_63">
<tr id="patternNBS_UI_63" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_63');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_63">
<tr id="nopatternNBS_UI_63" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the date(s) of blood transfusion(s) the patient received:</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date(s) of blood transfusion(s)" id="14687_8L">Blood Transfusion Date:</span>
</td><td>
<span id="14687_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(14687_8)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_52" name="Blood Donation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the year before symptom onset or diagnosis (use earlier date), did the patient donate blood?" id="ARB005L" >
In the year before symptom onset or diagnosis, did the patient donate blood?:</span></td><td>
<span id="ARB005" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient a blood donor identified during a transfusion investigation (i.e., had positive Babesia test results and was linked to an infected recipient)?" id="ARB013L" >
Was the patient a blood donor identified during a transfusion investigation?:</span></td><td>
<span id="ARB013" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB013)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV964L" title="If a donated blood product was implicated in an investigation, specify which types of product">
Type of donated blood product implicated:</span></td><td>
<span id="INV964" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV964)"
codeSetNm="PHVS_BLOODPRODUCT_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If a donated blood product was implicated in an investigation, specify which types of product" id="INV964OthL">Other Type of donated blood product implicated:</span></td>
<td> <span id="INV964Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV964Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_75" name="Blood Donation Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_75"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_75">
<tr id="patternNBS_UI_75" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_75');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_75">
<tr id="nopatternNBS_UI_75" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the date(s) of blood donation(s):</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Blood Donation Date" id="82756_8L">Blood Donation Date:</span>
</td><td>
<span id="82756_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82756_8)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_56" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 8 weeks before symptoms/diagnosis:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient engage in outdoor activities?" id="82762_6L" >
Did the patient engage in outdoor activities?:</span></td><td>
<span id="82762_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(82762_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="82763_4L" title="Which outdoor activities did the patient engage in?">
Outdoor activities:</span></td><td>
<span id="82763_4" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(82763_4)"
codeSetNm="PHVS_OUTDOORACTIVITIES_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Which outdoor activities did the patient engage in?" id="82763_4OthL">Other Outdoor activities:</span></td>
<td> <span id="82763_4Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(82763_4Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient spend time outdoors in or near wooded or brushy areas?" id="272500005L" >
Did the patient spend time near wooded areas?:</span></td><td>
<span id="272500005" />
<nedss:view name="PageForm" property="pageClientVO.answer(272500005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient notice any tick bites?" id="95898004L" >
Did the patient notice any tick bites?:</span></td><td>
<span id="95898004" />
<nedss:view name="PageForm" property="pageClientVO.answer(95898004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Tick Bite Information" isHidden="F" classType="subSect" >
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;To be filled out if patient observed a tick bite in 8 weeks prior to symptoms/diagnosis</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient noticed tick bite, what was the geographic location?" id="82755_0L">
Geographic Location of Tick Bite:</span>
</td>
<td>
<span id="82755_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82755_0)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If patient noticed tick bite, when did the bite occur?" id="45347_2L">Date of Tick Bite:</span>
</td><td>
<span id="45347_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(45347_2)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_58" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their county, state, or country of residence?" id="TRAVEL02L" >
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span></td><td>
<span id="TRAVEL02" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL02)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_59" name="Travel History Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_59"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_59">
<tr id="patternNBS_UI_59" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_59');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_59">
<tr id="nopatternNBS_UI_59" class="odd" style="display:none">
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
<span title="Domestic vs International Travel" id="NBS454L" >
Was the travel domestic or international?:</span></td><td>
<span id="NBS454" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS454)"
codeSetNm="PHVSFB_DOMINTNL"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="List any international destinations of recent travel" id="TRAVEL05L" >
Country of Travel:</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Domestic destination, states traveled" id="82754_3L" >
State of Travel:</span></td><td>
<span id="82754_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(82754_3)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Which intrastate counties did the patient travel?" id="82753_5L" >
County of Travel:</span></td><td>
<span id="82753_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(82753_5)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Principal Reason for Travel" id="TRAVEL16L" >
Principal Reason for Travel:</span></td><td>
<span id="TRAVEL16" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16)"
codeSetNm="PHVS_TRAVELREASON_MALARIA"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the patient traveled, when did they arrive to their travel destination?" id="TRAVEL06L">Date of Arrival at Destination:</span>
</td><td>
<span id="TRAVEL06"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL06)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the patient traveled, when did they depart from their travel destination?" id="TRAVEL07L">Date of Departure from Destination:</span>
</td><td>
<span id="TRAVEL07"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL07)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="82310_4L" title="Duration of Stay">
Duration of Stay:</span>
</td><td>
<span id="82310_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82310_4)"  />
<span id="82310_4UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82310_4Unit)" codeSetNm="PHVS_DURATIONUNIT_CDC_1" />
</td></tr>

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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_60" name="Congenital Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient an infant born to a mother who had babesiosis or Babesia infection during pregnancy?" id="82751_9L" >
Was the patient an infant born to a mother who had babesiosis or Babesia infection during pregnancy?:</span></td><td>
<span id="82751_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(82751_9)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient's mother test positive for babesiosis or Babesia infection before or at the time of delivery?" id="MTH162L" >
Did the patient's mother test positive for Babesia before/at the time of delivery?:</span></td><td>
<span id="MTH162" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH162)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient's mother test positive for babesiosis or Babesia infection after delivery?" id="MTH163L" >
Did the patient's mother test positive for Babesia infection after delivery?:</span></td><td>
<span id="MTH163" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH163)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of mother's earliest positive test result" id="MTH164L">Date of mother's earliest positive test result:</span>
</td><td>
<span id="MTH164"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH164)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Lab Testing" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_UI_43" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_43"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_43">
<tr id="patternNBS_UI_43" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_43');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_43">
<tr id="nopatternNBS_UI_43" class="odd" style="display:none">
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
<span title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_BABESIOSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="For each test performed, provide result" id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LAB_TEST_INTERP"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Provide titer (if applicable)" id="LAB628L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Babesia species identified" id="LAB278L" >
Organism Name:</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB278)"
codeSetNm="PHVS_LABRESULT_BABESIOSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Babesia species identified" id="LAB278OthL">Other Organism Name:</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date laboratory specimen was collected" id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="53556_7L" title="Estimated number of infected erythrocytes expressed as a percentage of the total erythrocytes">
Estimated percentage of infected erythrocytes:</span>
</td><td>
<span id="53556_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(53556_7)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
