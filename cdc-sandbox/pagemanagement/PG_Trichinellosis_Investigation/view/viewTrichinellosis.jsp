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
String tabId = "viewTrichinellosis";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Signs and Symptoms","Laboratory","Exposure"};
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
<nedss:container id="NBS_UI_35" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have eosinophilia?" id="386789004L" >
Eosinophilia:</span></td><td>
<span id="386789004" />
<nedss:view name="PageForm" property="pageClientVO.answer(386789004)"
codeSetNm="YNU"/>
</td> </tr>

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
<span id="INV202L" title="What was the patient's highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
<span id="INV202UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

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
<span title="Did the patient have periorbital edema?" id="49563000L" >
Periorbital Edema:</span></td><td>
<span id="49563000" />
<nedss:view name="PageForm" property="pageClientVO.answer(49563000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338L" >
Other Symptom(s):</span></td><td>
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
codeSetNm="PHVS_LABTESTTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)" id="INV291L" >
Test Result Qualitative:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_TRICH"/>
</td> </tr>

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
<span title="This indicates the type of specimen tested" id="667469L" >
Specimen Type:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="PHVS_SPECIMENTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="This indicates the type of specimen tested" id="667469OthL">Other Specimen Type:</span></td>
<td> <span id="667469Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(667469Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date" id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date." id="LAB217L">Specimen Analyzed Date:</span>
</td><td>
<span id="LAB217"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the performing laboratory type" id="LAB606L" >
Performing Laboratory Type:</span></td><td>
<span id="LAB606" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Laboratory Type:</span></td>
<td> <span id="LAB606Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB606Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the specimen was sent for strain identification, indicate the strain" id="NBS212L" >
Strain Type:</span></td><td>
<span id="NBS212" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS212)"
codeSetNm="PHVS_STRAINTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If the specimen was sent for strain identification, indicate the strain" id="NBS212OthL">Other Strain Type:</span></td>
<td> <span id="NBS212Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS212Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Eosinophilia Lab" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was testing performed for eosinophilia?" id="LAB711L" >
Was testing performed for eosinophilia?:</span></td><td>
<span id="LAB711" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB711)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="LAB665L" title="Eosinophil value (absolute number or percentage)">
Eosinophil value (absolute number or percentage):</span>
</td><td>
<span id="LAB665"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB665)"  />
<span id="LAB665UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(LAB665Unit)" codeSetNm="PHVS_EOSINOPHILUNITS_CDC_TRICH" />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Food Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 8 WEEKS before symptom onset/diagnosis:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient consume meat suspected of making the patient ill?" id="NBS406L" >
Did the patient consume meat item(s) suspected of making the patient ill?:</span></td><td>
<span id="NBS406" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS406)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_38" name="Food Exposure History" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_38"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_38">
<tr id="patternNBS_UI_38" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_38');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_38">
<tr id="nopatternNBS_UI_38" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;Specify all the information for suspected meat consumed:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009L" >
Meat Type:</span></td><td>
<span id="INV1009" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1009)"
codeSetNm="PHVS_MEATCONSUMEDTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009OthL">Other Meat Type:</span></td>
<td> <span id="INV1009Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV1009Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Other Meat Type" id="NBS528L">
(Legacy) Other Meat Type:</span>
</td>
<td>
<span id="NBS528"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS528)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date suspected meat was eaten" id="INV967L">Consumed Date:</span>
</td><td>
<span id="INV967"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV967)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Where was the suspected meat obtained?" id="INV969L" >
Meat Obtained:</span></td><td>
<span id="INV969" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV969)"
codeSetNm="PHVS_MEATPURCHASEINFO_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Where was the suspected meat obtained?" id="INV969OthL">Other Meat Obtained:</span></td>
<td> <span id="INV969Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV969Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Meat preparation (further food processing)" id="INV968L" >
Method of Meat Preparation:</span></td><td>
<span id="INV968" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV968)"
codeSetNm="PHVS_FOODPROCESSINGMETHOD_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Meat preparation (further food processing)" id="INV968OthL">Other Method of Meat Preparation:</span></td>
<td> <span id="INV968Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV968Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What method of cooking was used on suspected meat?" id="INV970L" >
Method of Cooking:</span></td><td>
<span id="INV970" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV970)"
codeSetNm="PHVS_FOODCOOKINGMETHOD_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What method of cooking was used on suspected meat?" id="INV970OthL">Other Method of Cooking:</span></td>
<td> <span id="INV970Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV970Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was larva observed in suspected meat by microscopy?" id="INV971L" >
Larva Present in Meat:</span></td><td>
<span id="INV971" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV971)"
codeSetNm="PHVS_PRESENTABSENTUNKNOTEXAMINED_CDC_TRICH"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Where was the suspected meat tested?" id="INV972L" >
Where was the Meat Tested:</span></td><td>
<span id="INV972" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV972)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Where was the suspected meat tested?" id="INV972OthL">Other Where was the Meat Tested:</span></td>
<td> <span id="INV972Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV972Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)" id="INV973L">
Suspect Meat Comments:</span>
</td>
<td>
<span id="INV973"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV973)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?" id="TRAVEL02L" >
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span></td><td>
<span id="TRAVEL02" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL02)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_40"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_40">
<tr id="patternNBS_UI_40" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_40');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_40">
<tr id="nopatternNBS_UI_40" class="odd" style="display:none">
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
<span title="Intrastate destination, counties traveled" id="82753_5L" >
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
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Principal Reason for Travel" id="TRAVEL16OthL">Other Principal Reason for Travel:</span></td>
<td> <span id="TRAVEL16Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16Oth)"/></td></tr>

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
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
