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
String tabId = "viewTBRD";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Manifestation","Complications","Travel","Tick Bite","Treatment","Blood Transfusion","Blood Donation","Organ Transplant","Laboratory"};
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
<nedss:container id="NBS_UI_36" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/ehrlichiosis-and-anaplasmosis/" TARGET="_blank">Click here for the current Anaplasmosis OR Ehrlichiosis case definitions</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/spotted-fever-rickettsiosis/" TARGET="_blank">Click here for the current Spotted Fever Rickettsiosis case definition</a></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient symptomatic for the illness of interest?" id="INV576L" >
Was patient symptomatic?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have fever?" id="386661006L" >
Fever:</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

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
<span title="Did the patient have Eschar?" id="87319000L" >
Eschar:</span></td><td>
<span id="87319000" />
<nedss:view name="PageForm" property="pageClientVO.answer(87319000)"
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
<span title="Did the patient have Hepatic transaminase elevation?" id="PHC2108L" >
Hepatic transaminase elevation:</span></td><td>
<span id="PHC2108" />
<nedss:view name="PageForm" property="pageClientVO.answer(PHC2108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Leukopenia. This is an interpretation based upon the lab values for White Blood Cell (WBC) count." id="419188005L" >
Leukopenia:</span></td><td>
<span id="419188005" />
<nedss:view name="PageForm" property="pageClientVO.answer(419188005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have myalgia?" id="68962001L" >
Myalgia:</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have rash?" id="271807003L" >
Rash:</span></td><td>
<span id="271807003" />
<nedss:view name="PageForm" property="pageClientVO.answer(271807003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have thrombocytopenic disorder?" id="302215000L" >
Thrombocytopenic:</span></td><td>
<span id="302215000" />
<nedss:view name="PageForm" property="pageClientVO.answer(302215000)"
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
<nedss:container id="NBS_UI_38" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience any severe complications in the clinical course of this illness?" id="INV920L" >
Did the patient experience any severe complications?:</span></td><td>
<span id="INV920" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV920)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="67187_5L" title="If the subject experienced severe complications due to this illness, specify the complication(s).">
Type of Complication(s):</span></td><td>
<span id="67187_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(67187_5)"
codeSetNm="COMPLICATIONS_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If the subject experienced severe complications due to this illness, specify the complication(s)." id="67187_5OthL">Other Type of Complication(s):</span></td>
<td> <span id="67187_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(67187_5Oth)"/></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;For the question below, immunocompromised refers to a medical condition or treatment including but not limited to: chemotherapy for current illness, HIV, asplenic, anti-rejection drugs post-transplant, corticosteroids [&gt;14 days, such as prednisone, Medrol, or decadron], rheumatoid arthritis.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="At the time of diagnosis, was the patient immunocompromised due to medical condition(s) or treatment(s)" id="VAR126L" >
Patient immunocompromised at time of diagnosis:</span></td><td>
<span id="VAR126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAR126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="If the patient is immunocompromised, what is the associated condition or treatment?" id="VAR127L">
Immunocompromised associated condition or treatment:</span>
</td>
<td>
<span id="VAR127"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR127)"  />
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
<span title="In the 2 weeks before symptom onset or diagnosis (whichever is earlier) did the patient travel out of their county, state, or country of residence?" id="TRAVEL02L" >
Did patient travel out of their country, state, or country of residence prior to illness onset?:</span></td><td>
<span id="TRAVEL02" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL02)"
codeSetNm="YNU"/>
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
<span title="Which intrastate counties did the patient travel to?" id="82753_5L" >
County of Travel:</span></td><td>
<span id="82753_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(82753_5)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the subject traveled, when did they arrive to their travel destination?" id="TRAVEL06L">Date of Arrival at Destination:</span>
</td><td>
<span id="TRAVEL06"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL06)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the subject traveled, when did they depart from their travel destination?" id="TRAVEL07L">Date of Departure from Destination:</span>
</td><td>
<span id="TRAVEL07"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL07)"  />
</td></tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC"/>
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
<nedss:container id="NBS_UI_43" name="Tick Bite" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient notice any tick bites?" id="95898004L" >
In the eight weeks before symptom onset or diagnosis, did the patient notice any tick bites?:</span></td><td>
<span id="95898004" />
<nedss:view name="PageForm" property="pageClientVO.answer(95898004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Tick Bite Information" isHidden="F" classType="subSect" >
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

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Geographic location when tick was removed" id="82755_0L">
Geographic location when tick was removed:</span>
</td>
<td>
<span id="82755_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82755_0)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of tick removal" id="45347_2L">Date of tick removal:</span>
</td><td>
<span id="45347_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(45347_2)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_46" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Were antibiotics prescribed to the patient for this infection?" id="255633001L" >
Were antibiotics prescribed?:</span></td><td>
<span id="255633001" />
<nedss:view name="PageForm" property="pageClientVO.answer(255633001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47" name="Antibiotics Treatment" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_47"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_47">
<tr id="patternNBS_UI_47" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_47');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_47">
<tr id="nopatternNBS_UI_47" class="odd" style="display:none">
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
Antibiotic Prescribed:</span></td><td>
<span id="29303_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(29303_5)"
codeSetNm="MEDICATION_RECEIVED_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What antibiotic was prescribed to the patient for this infection?" id="29303_5OthL">Other Antibiotic Prescribed:</span></td>
<td> <span id="29303_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(29303_5Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date treatment/medication was prescribed" id="INV1300L">Date Antibiotic Prescribed:</span>
</td><td>
<span id="INV1300"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV1300)"  />
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
<nedss:container id="NBS_UI_49" name="Blood Transfusion" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the subject received a blood transfusiont in the 12 months prior to this illness?" id="82312_0L" >
Has the subject received a blood transfusion in the 12 months prior to this illness?:</span></td><td>
<span id="82312_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(82312_0)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patients infection transfusion associated?" id="418912005L" >
Was the patients infection transfusion associated?:</span></td><td>
<span id="418912005" />
<nedss:view name="PageForm" property="pageClientVO.answer(418912005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="933_2L" title="If a transfused blood product was implicated in an investigation, specify which type(s) of product.">
Type of transfused product implicated:</span></td><td>
<span id="933_2" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(933_2)"
codeSetNm="BLOOD_PRODUCT_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If a transfused blood product was implicated in an investigation, specify which type(s) of product." id="933_2OthL">Other Type of transfused product implicated:</span></td>
<td> <span id="933_2Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(933_2Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_50" name="Blood Transfusion Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_50"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_50">
<tr id="patternNBS_UI_50" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_50');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_50">
<tr id="nopatternNBS_UI_50" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the patient blood transfusion(s) dates</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Blood Transfusion Date" id="14687_8L">Blood Transfusion Date:</span>
</td><td>
<span id="14687_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(14687_8)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_60" name="Blood Donation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient a blood donor identified during a transfusion investigation (i.e., had positive test results and was linked to an infected recipient)?" id="INV617L" >
Was the patient a blood donor identified during a transfusion investigation?:</span></td><td>
<span id="INV617" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV617)"
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
codeSetNm="BLOOD_PRODUCT_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If a donated blood product was implicated in an investigation, specify which types of product" id="INV964OthL">Other Type of donated blood product implicated:</span></td>
<td> <span id="INV964Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV964Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient donate blood in the 30 days prior to symptom onset?" id="ARB005L" >
Did the patient donate blood in the 30 days prior to symptom onset?:</span></td><td>
<span id="ARB005" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the blood bank/hospital/transplant service notified?" id="INV1268L" >
Blood Bank Notified:</span></td><td>
<span id="INV1268" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1268)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Blood Donation Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_53"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_53">
<tr id="patternNBS_UI_53" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_53');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_53">
<tr id="nopatternNBS_UI_53" class="odd" style="display:none">
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
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the patient blood donation(s) dates</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date(s) of blood donation(s)" id="82756_8L">Blood Donation Date:</span>
</td><td>
<span id="82756_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82756_8)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_62" name="Organ Transplant" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="In the year before symptom onset or diagnosis (use earlier date), did the subject receive an organ transplant(s)?" id="ARB008L" >
In the year before symptom onset of diagnosis, did the subject receive an organ transplant(s)?:</span></td><td>
<span id="ARB008" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_55" name="Organ Transplant Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_55"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_55">
<tr id="patternNBS_UI_55" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_55');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_55">
<tr id="nopatternNBS_UI_55" class="odd" style="display:none">
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
<span title="What organ was transplanted?" id="ARB028_TXTL">
Organ(s) Transplanted:</span>
</td>
<td>
<span id="ARB028_TXT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(ARB028_TXT)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date(s) of organ transplant(s)" id="80989_7L">Organ Transplant Date:</span>
</td><td>
<span id="80989_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(80989_7)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the subjects infection transplant-related?" id="INV1267L" >
Transplant Associated Infection:</span></td><td>
<span id="INV1267" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1267)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/ehrlichiosis-and-anaplasmosis/" TARGET="_blank">Click here for the current Anaplasmosis OR Ehrlichiosis case definitions</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/spotted-fever-rickettsiosis/" TARGET="_blank">Click here for the current Spotted Fever Rickettsiosis case definition</a></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_58" name="Laboratory Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_58"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_58">
<tr id="patternNBS_UI_58" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_58');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_58">
<tr id="nopatternNBS_UI_58" class="odd" style="display:none">
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
codeSetNm="LAB_TEST_TYPE_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, detected, negative)" id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="LAB_TEST_INTERPRETATION_LYME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Species identified through testing" id="LAB278L" >
Organism Name:</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB278)"
codeSetNm="ORGANISM_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report." id="LAB163L">Specimen Collection Date:</span>
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
codeSetNm="SPECIMEN_TYPE_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="This indicates the type of specimen tested." id="667469OthL">Other Specimen Type:</span></td>
<td> <span id="667469Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(667469Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Performing Laboratory Name" id="68994_3L">
Performing Laboratory Name:</span>
</td>
<td>
<span id="68994_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(68994_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="State where laboratory is located" id="INV1269L" >
Laboratory State:</span></td><td>
<span id="INV1269" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1269)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specimen identifier that will be used to uniquely identify the lab message" id="LAB125L">
Performing Laboratory Specimen ID:</span>
</td>
<td>
<span id="LAB125"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB125)" />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
