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
String tabId = "viewPertussis";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Pregnancy and Birth","Symptoms","Complications","Treatment","Laboratory","Vaccination Information","Epidemiology"};
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
<nedss:container id="NBS_UI_56" name="Pregnancy and Birth Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient &lt; 12 months old at illness onset?" id="NBS364L" >
Was the patient &lt; 12 months old at illness onset?:</span></td><td>
<span id="NBS364" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS364)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="MTH111L" title="Mothers age at infants birth (used only if patient &lt; 1 year of age)">
Mothers Age at Infants Birth (in Years):</span>
</td><td>
<span id="MTH111"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH111)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS347L" title="Birth Weight in Grams">
Birth Weight (in grams):</span>
</td><td>
<span id="NBS347"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS347)"  />
</td></tr>
<!--skipping Hidden Numeric Question-->
<!--skipping Hidden Numeric Question-->

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="DEM228L" title="Indicate the patient's gestational age (in weeks) if case-patient was &lt; 1 year of age at illness onset.">
Patient's Gestational Age (in weeks):</span>
</td><td>
<span id="DEM228"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM228)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did mother receive Tdap (if case-patient &lt; 1 year of age at illness onset)?" id="MTH172L" >
Did the mother receive Tdap?:</span></td><td>
<span id="MTH172" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH172)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If mother received Tdap, when was it administered in relation to the pregnancy?" id="MTH173L" >
If mother received Tdap, when was it administered in relation to the pregnancy?:</span></td><td>
<span id="MTH173" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH173)"
codeSetNm="PHVS_TIMINGOFMATERNALTREATMENT_NND"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If mother received Tdap, when was it administered in relation to the pregnancy?" id="MTH173OthL">Other If mother received Tdap, when was it administered in relation to the pregnancy?:</span></td>
<td> <span id="MTH173Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(MTH173Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If mother received Tdap, what date was it administered?*(if available)" id="MTH174L">If mother received Tdap, what date was it administered? (if available):</span>
</td><td>
<span id="MTH174"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH174)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether the patient is/was symptomatic for pertussis?" id="INV576L" >
Did the patient experience any symptoms related to pertussis?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient's illness include the symptom of cough?" id="49727002L" >
Did the patient have any cough?:</span></td><td>
<span id="49727002" />
<nedss:view name="PageForm" property="pageClientVO.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Cough onset date" id="INV550L">Cough Onset Date:</span>
</td><td>
<span id="INV550"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV550)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV902L" title="Patients age at cough onset">
Age at Cough Onset:</span>
</td><td>
<span id="INV902"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV902)"  />
<span id="INV902UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV902Unit)" codeSetNm="PHVS_AGEUNIT_NND" />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV557L" title="What was the duration (in days) of the patients cough?">
Total Cough Duration (in days):</span>
</td><td>
<span id="INV557"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV557)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of the patients final interview" id="INV555L">Date of Final Interview:</span>
</td><td>
<span id="INV555"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV555)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was there a cough at the patients final interview?" id="NBS349L" >
Cough at Final Interview:</span></td><td>
<span id="NBS349" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS349)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patients illness include the symptom of paroxysmal cough?" id="43025008L" >
Did the patient have paroxysmal cough?:</span></td><td>
<span id="43025008" />
<nedss:view name="PageForm" property="pageClientVO.answer(43025008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patients illness include the symptom of whoop?" id="60537006L" >
Did the patient have whoop?:</span></td><td>
<span id="60537006" />
<nedss:view name="PageForm" property="pageClientVO.answer(60537006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patients illness include the symptom of post-tussive vomiting?" id="424580008L" >
Did the patient have post-tussive vomiting?:</span></td><td>
<span id="424580008" />
<nedss:view name="PageForm" property="pageClientVO.answer(424580008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patients illness include the symptom of apnea?" id="1023001L" >
Did the patient have apnea?:</span></td><td>
<span id="1023001" />
<nedss:view name="PageForm" property="pageClientVO.answer(1023001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient's illness include the symptom of cyanosis?" id="3415004L" >
Did the patient have cyanosis?:</span></td><td>
<span id="3415004" />
<nedss:view name="PageForm" property="pageClientVO.answer(3415004)"
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_64" name="Signs and Symptoms Repeating Block" isHidden="T" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_64"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_64">
<tr id="patternNBS_UI_64" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_64');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_64">
<tr id="nopatternNBS_UI_64" class="odd" style="display:none">
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
<span title="Select all the signs and symptoms that are associated with the patient" id="INV272L" >
Signs and Symptoms Associated with Patient:</span></td><td>
<span id="INV272" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV272)"
codeSetNm="PHVS_SIGNSSYMPTOMS_PERTUSSIS_NND"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Select all the signs and symptoms that are associated with the patient" id="INV272OthL">Other Signs and Symptoms Associated with Patient:</span></td>
<td> <span id="INV272Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV272Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicator for associated signs and symptoms" id="INV919L" >
Signs and Symptoms Indicator:</span></td><td>
<span id="INV919" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV919)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether the patient experienced any complications related to pertussis." id="NBS340L" >
Did the patient experience any complications related to pertussis?:</span></td><td>
<span id="NBS340" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS340)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Result of chest x-ray for pneumonia" id="INV923L" >
Result of Chest X-Ray for Pneumonia:</span></td><td>
<span id="INV923" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV923)"
codeSetNm="PHVS_PNUND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have generalized or focal seizures due to pertussis?" id="91175000L" >
Did the patient have generalized or focal seizures due to pertussis?:</span></td><td>
<span id="91175000" />
<nedss:view name="PageForm" property="pageClientVO.answer(91175000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have acute encephalopathy due to pertussis?" id="81308009L" >
Did the patient have acute encephalopathy due to pertussis?:</span></td><td>
<span id="81308009" />
<nedss:view name="PageForm" property="pageClientVO.answer(81308009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Other Complications" id="NBS343L" >
Other Complication(s):</span></td><td>
<span id="NBS343" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS343)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify Other Complication" id="NBS343_OTHL">
Specify Other Complication:</span>
</td>
<td>
<span id="NBS343_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS343_OTH)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_65" name="Complications Repeating Block" isHidden="T" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_65"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_65">
<tr id="patternNBS_UI_65" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_65');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_65">
<tr id="nopatternNBS_UI_65" class="odd" style="display:none">
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
<span title="Complications associated with the illness being reported" id="67187_5L" >
Type of Complications:</span></td><td>
<span id="67187_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(67187_5)"
codeSetNm="PHVS_COMPLICATIONS_PERTUSSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Complications associated with the illness being reported" id="67187_5OthL">Other Type of Complications:</span></td>
<td> <span id="67187_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(67187_5Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicator for associated complication" id="INV920L" >
Type of Complications Indicator:</span></td><td>
<span id="INV920" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV920)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Were antibiotics given to the patient?" id="INV559L" >
Were antibiotics given to the patient?:</span></td><td>
<span id="INV559" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV559)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_54" name="Please list all of the antibiotics the patient received in the order the antibiotics were taken." isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_54"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_54">
<tr id="patternNBS_UI_54" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_54');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_54">
<tr id="nopatternNBS_UI_54" class="odd" style="display:none">
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
<span title="Indicate the antibiotic the patient received." id="29303_5L" >
Medication (Antibiotic) Administered:</span></td><td>
<span id="29303_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(29303_5)"
codeSetNm="PHVS_ANTIBIOTICRECEIVED_PERTUSSIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date the treatment was initiated." id="INV924L">Treatment Start Date:</span>
</td><td>
<span id="INV924"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV924)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="67453_1L" title="Indicate the number of days the patient actually took the antibiotic referenced.">
Number of Days Actually Taken:</span>
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
Was laboratory testing done for pertussis?:</span></td><td>
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
Specimen Sent to CDC:</span></td><td>
<span id="LAB515" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB515)"
codeSetNm="YNU"/>
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Interpretive Lab Data Repeating Block" isHidden="F" classType="subSect" >
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Type of test(s) performed for this case." id="INV290L" >
Lab Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_PERTUSSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Type of test(s) performed for this case." id="INV290OthL">Other Lab Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." id="INV291L" >
Lab Test Result Qualitative:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_PERTUSSIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." id="INV291OthL">Other Lab Test Result Qualitative:</span></td>
<td> <span id="INV291Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV291Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicate the quantitative test result value for the lab test performed." id="LAB628L">
Lab Test Result Quantitative:</span>
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
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date/Time:</span>
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
<nedss:container id="NBS_UI_42" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_42"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_42">
<tr id="patternNBS_UI_42" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_42');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_42">
<tr id="nopatternNBS_UI_42" class="odd" style="display:none">
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
<nedss:container id="NBS_UI_37" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient ever receive pertussis-containing vaccine?" id="VAC126L" >
Did the patient ever receive pertussis-containing vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine). To indicate that the number of doses is unknown, enter 99.">
If yes, how many doses?:</span>
</td><td>
<span id="VAC132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC140L" title="Number of vaccine doses against this disease prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Vaccination Doses Prior to Onset:</span>
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

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is this case epi-linked to a laboratory-confirmed case?" id="INV907L" >
Is this case epi-linked to a laboratory-confirmed case?:</span></td><td>
<span id="INV907" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV907)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If epi-linked to a laboratory-confirmed case, Case ID of epi-linked case." id="NBS350L">
Case ID of epi-linked case:</span>
</td>
<td>
<span id="NBS350"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What was the transmission setting where the condition was acquired?" id="INV224L" >
Transmission Setting (Where did the case acquire pertusis?):</span></td><td>
<span id="INV224" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV224)"
codeSetNm="PHVS_TRAN_SETNG"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What was the transmission setting where the condition was acquired?" id="INV224OthL">Other Transmission Setting (Where did the case acquire pertusis?):</span></td>
<td> <span id="INV224Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV224Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was there documented transmission from this case of pertussis to a new setting (outside of the household)?" id="NBS351L" >
Was there documented transmission from this case to a new setting (outside of the household)?:</span></td><td>
<span id="NBS351" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS351)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What was the new setting (outside of the household) for transmission of pertussis from this case?" id="INV561L" >
What was the new setting (outside of the household) for transmission of pertussis from this case?:</span></td><td>
<span id="INV561" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV561)"
codeSetNm="PHVS_SETTINGOFFURTHERSPREAD_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What was the new setting (outside of the household) for transmission of pertussis from this case?" id="INV561OthL">Other What was the new setting (outside of the household) for transmission of pertussis from this case?:</span></td>
<td> <span id="INV561Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV561Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Were there one or more suspected sources of infection (A suspected source is another person with a cough who was in contact with  the case 7-20 days before the cases cough)." id="NBS354L" >
Were there one or more suspected sources of infection?:</span></td><td>
<span id="NBS354" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS354)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS355L" title="Indicate the number of suspected sources of infection.">
Number of suspected sources of infection:</span>
</td><td>
<span id="NBS355"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS355)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV562L" title="Indicate the number of contacts of this case recommended to receive antibiotic prophylaxis.">
Number of contacts of this case recommended to receive antibiotic prophylaxis:</span>
</td><td>
<span id="INV562"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV562)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="For each suspected source of infection, indicate the following:" isHidden="F" classType="subSect" >
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

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS356L" title="Suspected source age">
Age:</span>
</td><td>
<span id="NBS356"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS356)"  />
<span id="NBS356UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(NBS356Unit)" codeSetNm="AGE_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Suspected source sex" id="NBS358L" >
Sex:</span></td><td>
<span id="NBS358" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS358)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Suspected source cough onset date" id="NBS363L">Cough Onset Date:</span>
</td><td>
<span id="NBS363"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS363)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Suspected source relationship to case" id="NBS359L" >
Relationship to Case:</span></td><td>
<span id="NBS359" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS359)"
codeSetNm="PHVS_RELATIONSHIP_VPD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Suspected source relationship to case" id="NBS359OthL">Other Relationship to Case:</span></td>
<td> <span id="NBS359Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS359Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="How many doses of pertussis-containing vaccine has the suspected source received?" id="NBS362L" >
How many doses of pertussis-containing vaccine has the suspected source received?:</span></td><td>
<span id="NBS362" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS362)"
codeSetNm="NBS_VAC_DOSE_NUM"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
