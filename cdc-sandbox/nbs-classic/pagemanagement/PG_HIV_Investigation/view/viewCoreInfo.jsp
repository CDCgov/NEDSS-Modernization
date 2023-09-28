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
String tabId = "viewCoreInfo";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Pregnant Information","900 Case Status","Risk Factors-Last 12 Months","Hangouts","Partner Information","Target Populations","STD Testing","Signs and Symptoms","STD History","900 Partner Services Information"};
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
<nedss:container id="NBS_INV_STD_UI_37" name="Pregnant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Assesses whether or not the patient is pregnant." id="INV178L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td><td>
<span id="NBS128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS128)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient pregnant at the initial exam for the condition?" id="NBS216L" >
Pregnant at Exam:</span></td><td>
<span id="NBS216" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS216)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS217L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam.">
Weeks:</span>
</td><td>
<span id="NBS217"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient pregnant at the time of interview for the condition." id="NBS218L" >
Pregnant at Interview:</span></td><td>
<span id="NBS218" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS218)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS219L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview.">
Weeks:</span>
</td><td>
<span id="NBS219"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS219)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient receiving/received prenatal care for this pregnancy?" id="NBS220L" >
Currently in Prenatal Care:</span></td><td>
<span id="NBS220" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS220)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy." id="NBS221L" >
Pregnant in Last 12 Months:</span></td><td>
<span id="NBS221" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS221)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If pregnant in the last 12 months, indicate the outcome of the pregnancy." id="NBS222L" >
Pregnancy Outcome:</span></td><td>
<span id="NBS222" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS222)"
codeSetNm="PREGNANCY_OUTCOME"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_39" name="Patient HIV Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The patient's HIV status." id="NBS153L" >
900 Status:</span></td><td>
<span id="NBS153" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS153)"
codeSetNm="STATUS_900"/>
</td> </tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the HIV status of this case investigated through search of eHARS?" id="INV892L" >
HIV Status Documented Through eHARS Record Search:</span></td><td>
<span id="INV892" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV892)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Enter the state-assigned HIV Case Number for this patient." id="NBS269L">
State HIV (eHARS) Case ID:</span>
</td>
<td>
<span id="NBS269"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS269)" />
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Mode of exposure from eHARS for HIV+ cases." id="INV894L" >
Transmission Category (eHARS):</span></td><td>
<span id="INV894" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV894)"
codeSetNm="PHVS_TRANSMISSIONCATEGORY_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was this case selected by reporting jurisdiction for enhanced investigation?" id="INV895L" >
Case Sampled for Enhanced Investigation:</span></td><td>
<span id="INV895" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV895)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_41" name="Risk Factors (Last 12 Months)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Were behavioral risk factors assessed for the client?" id="NBS229L" >
Was Behavioral Risk Assessed:</span></td><td>
<span id="NBS229" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS229)"
codeSetNm="RISK_PROFILE_IND"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_42" name="Sex Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex with a male within past 12 months?" id="STD107L" >
Had Sex with Male:</span></td><td>
<span id="STD107" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD107)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex with a female within past 12 months?" id="STD108L" >
Had Sex with Female:</span></td><td>
<span id="STD108" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD108)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex with transgender partner within past 12 months?" id="NBS230L" >
Had Sex with Transgender:</span></td><td>
<span id="NBS230" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS230)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex with an anonymous partner within past 12 months?" id="STD109L" >
Had Sex with Anonymous Partner:</span></td><td>
<span id="STD109" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD109)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_43" name="Sex Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex without a condom within past 12 months?" id="NBS231L" >
Had Sex Without a Condom:</span></td><td>
<span id="NBS231" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS231)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex while intoxicated/high within past 12 months?" id="STD111L" >
Had Sex While Intoxicated/High:</span></td><td>
<span id="STD111" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD111)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex in exchange for drugs/money within past 12 months?" id="STD112L" >
Exchanged Drugs/Money for Sex:</span></td><td>
<span id="STD112" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD112)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Female only.  Had sex with a known MSM within past 12 months?" id="STD113L" >
Females - Had Sex with Known MSM:</span></td><td>
<span id="STD113" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD113)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Had sex with a known Injection Drug User within past 12 months?" id="STD110L" >
Had Sex with Known IDU:</span></td><td>
<span id="STD110" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD110)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_44" name="Risk Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Incarcerated within the past 12 months?" id="STD118L" >
Been Incarcerated:</span></td><td>
<span id="STD118" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD118)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Used injection drugs within the past 12 months?" id="STD114L" >
Injection Drug Use:</span></td><td>
<span id="STD114" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD114)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Shared injection equipment within the past 12 months?" id="NBS232L" >
Shared Injection Equipment:</span></td><td>
<span id="NBS232" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS232)"
codeSetNm="YNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_45" name="Drug Use Past 12 Months" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;During the past 12 months, indicate whether or not the patient used any of the following injection or non-injection drugs.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="No drug use within the past 12 months?" id="NBS233L" >
No drug use reported:</span></td><td>
<span id="NBS233" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS233)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Cocaine use within the past 12 months?" id="NBS237L" >
Cocaine:</span></td><td>
<span id="NBS237" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS237)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Crack use within the past 12 months?" id="NBS235L" >
Crack:</span></td><td>
<span id="NBS235" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS235)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Heroine use within the past 12 months?" id="NBS239L" >
Heroin:</span></td><td>
<span id="NBS239" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS239)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Methamphetamine (meth)use within the past 12 months?" id="NBS234L" >
Methamphetamine:</span></td><td>
<span id="NBS234" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS234)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Nitrate/popper use within the past 12 months?" id="NBS236L" >
Nitrates/Poppers:</span></td><td>
<span id="NBS236" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS236)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Erectile dysfunction drug use within the past 12 months?" id="NBS238L" >
Erectile Dysfunction Medications:</span></td><td>
<span id="NBS238" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS238)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Other drug use within the past 12 months?" id="NBS240L" >
Other drug used:</span></td><td>
<span id="NBS240" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS240)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient indicated other drug use, specify the drug name(s)." id="STD300L">
Specify Other Drug Used:</span>
</td>
<td>
<span id="STD300"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD300)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_48" name="Places to Meet Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Places to Meet Partners" id="NBS242L" >
Places to Meet Partners:</span></td><td>
<span id="NBS242" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS242)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_49" name="Places Selected to Meet Partners" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_49"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_49">
<tr id="patternNBS_INV_STD_UI_49" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_49');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_49">
<tr id="nopatternNBS_INV_STD_UI_49" class="odd" style="display:none">
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

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS243L" title="Select the Hangout or Meetup.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS243)"/>
<span id="NBS243Disp"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_50" name="Places to have Sex" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Places to Have Sex" id="NBS244L" >
Places to Have Sex:</span></td><td>
<span id="NBS244" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS244)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_51" name="Places Selected to Have Sex" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_51"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_51">
<tr id="patternNBS_INV_STD_UI_51" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_51');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_51">
<tr id="nopatternNBS_INV_STD_UI_51" class="odd" style="display:none">
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

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS290L" title="Select the Hangout or Sex.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS290)"/>
<span id="NBS290Disp"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_53" name="Partners Past Year" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Female partners claimed in the last 12 months?" id="NBS223L" >
Female Partners (Past Year):</span></td><td>
<span id="NBS223" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS223)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS224L" title="The total number of female partners claimed in the last 12 months.">
Number Female (Past Year):</span>
</td><td>
<span id="NBS224"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS224)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Male partners claimed in the last 12 months?" id="NBS225L" >
Male Partners (Past Year):</span></td><td>
<span id="NBS225" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS225)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS226L" title="The total number of male partners claimed in the last 12 months.">
Number Male (Past Year):</span>
</td><td>
<span id="NBS226"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS226)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Transgender partners claimed in the last 12 months?" id="NBS227L" >
Transgender Partners (Past Year):</span></td><td>
<span id="NBS227" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS227)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS228L" title="The total number of transgender partners claimed in the last 12 months.">
Number Transgender (Past Year):</span>
</td><td>
<span id="NBS228"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS228)"  />
</td></tr>
<!--skipping Hidden Numeric Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD888L" title="Patient refused to answer questions regarding number of sex partners">Patient refused to answer questions regarding number of sex partners:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD888)" styleId="STD888"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD999L" title="Unknown number of sex partners in last 12 months">Unknown number of sex partners in last 12 months:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD999)" styleId="STD999"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_54" name="Partners in Interview Period" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Female sex/needle-sharing interview period partners claimed?" id="NBS129L" >
Female Partners (Interview Period):</span></td><td>
<span id="NBS129" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS129)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS130L" title="The total number of female sex/needle-sharing interview period partners claimed.">
Number Female (Interview Period):</span>
</td><td>
<span id="NBS130"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS130)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Male sex/needle-sharing interview period partners claimed?" id="NBS131L" >
Male Partners (Interview Period):</span></td><td>
<span id="NBS131" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS131)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS132L" title="The total number of male sex/needle-sharing interview period partners claimed.">
Number Male (Interview Period):</span>
</td><td>
<span id="NBS132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS132)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Transgender sex/needle-sharing interview period partners claimed?" id="NBS133L" >
Transgender Partners (Interview Period):</span></td><td>
<span id="NBS133" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS133)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS134L" title="The total number of transgender sex/needle-sharing interview period partners claimed.">
Number Transgender (Interview Period):</span>
</td><td>
<span id="NBS134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_55" name="Partner Internet Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Have you met sex partners through the Internet in the last 12 months?" id="STD119L" >
Met Sex Partners through the Internet:</span></td><td>
<span id="STD119" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD119)"
codeSetNm="YNRUD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_57" name="Target Populations" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS271L" title="Target populations identified for the patient.">
Target Population(s):</span></td><td>
<span id="NBS271" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS271)"
codeSetNm="TARGET_POPULATIONS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_59" name="Syphilis Test Results" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Have  non-treponemal or treponemal syphilis tests been performed?" id="NBS275L" >
Tests Performed?:</span></td><td>
<span id="NBS275" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS275)"
codeSetNm="YN"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD122L" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Nontreponemal Serologic Test for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD122)" styleId="STD122"><html:optionsCollection property="codedValue(PHVS_NONTREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD123L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD123)" styleId="STD123"><html:optionsCollection property="codedValue(PHVS_QUANTITATIVESYPHILISTESTRESULT_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD126L" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">Nontreponemal Serologic Syphilis Test Result (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD126)" styleId="STD126"><html:optionsCollection property="codedValue(PHVS_LABTESTREACTIVITY_NND)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD124L" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Treponemal SerologicTest for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD124)" styleId="STD124"><html:optionsCollection property="codedValue(PHVS_TREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="STD125L" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">Treponemal Serologic Syphilis Test Result  (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD125)" styleId="STD125"><html:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_71" name="STD Lab Test Results" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_71"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_71">
<tr id="patternNBS_INV_STD_UI_71" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_71');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_71">
<tr id="nopatternNBS_INV_STD_UI_71" class="odd" style="display:none">
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
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result)." id="STD123_1L" >
Test Result Coded Quantitative:</span></td><td>
<span id="STD123_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
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
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Test Result Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165L" >
Specimen Source:</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="PHVS_SPECIMENSOURCE_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB165Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event." id="338822L">Specimen Collection Date:</span>
</td><td>
<span id="338822"/>
<nedss:view name="PageForm" property="pageClientVO.answer(338822)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_72" name="Antimicrobial Susceptibility Testing(AST)" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_72"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_72">
<tr id="patternNBS_INV_STD_UI_72" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_72');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_72">
<tr id="nopatternNBS_INV_STD_UI_72" class="odd" style="display:none">
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
<span title="Pathogen/Organism Identified in Isolate." id="LABAST1L" >
Microorganism Identified in Isolate:</span></td><td>
<span id="LABAST1" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST1)"
codeSetNm="PHVS_ORGANISMIDENTIFIEDAST_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Isolate identifier unique for each isolate within laboratory." id="LABAST2L">
Isolate Identifier:</span>
</td>
<td>
<span id="LABAST2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST2)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3L" >
Specimen Type:</span></td><td>
<span id="LABAST3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST3)"
codeSetNm="PHVS_SPECIMENTYPEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3OthL">Other Specimen Type:</span></td>
<td> <span id="LABAST3Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LABAST3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4L" >
Specimen Collection Site:</span></td><td>
<span id="LABAST4" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST4)"
codeSetNm="PHVS_SPECIMENCOLLECTIONSITEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4OthL">Other Specimen Collection Site:</span></td>
<td> <span id="LABAST4Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LABAST4Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Collection Date" id="LABAST5L">Specimen Collection Date:</span>
</td><td>
<span id="LABAST5"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST5)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs." id="LABAST6L" >
AST Type:</span></td><td>
<span id="LABAST6" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST6)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" id="LABAST7L" >
AST Method:</span></td><td>
<span id="LABAST7" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST7)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTMETHOD_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)" id="LABAST8L" >
AST Interpretation:</span></td><td>
<span id="LABAST8" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST8)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTINTERPRETATION_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result." id="LABAST11L" >
AST Result Coded Quantitative:</span></td><td>
<span id="LABAST11" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST11)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTQUANTITATIVE_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)" id="LABAST9L">
AST Result Quantitative Value:</span>
</td>
<td>
<span id="LABAST9"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST9)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)" id="LABAST10L" >
Test Result Units:</span></td><td>
<span id="LABAST10" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST10)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTUNITS_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_61" name="Signs and Symptoms" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_61"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_61">
<tr id="patternNBS_INV_STD_UI_61" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_61');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_61">
<tr id="nopatternNBS_INV_STD_UI_61" class="odd" style="display:none">
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
<span title="Is the sign/symptom experienced by the patient or observed by a clinician?" id="NBS246L" >
Source:</span></td><td>
<span id="NBS246" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS246)"
codeSetNm="SIGN_SX_OBSRV_SOURCE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician." id="NBS247L">Observation/Onset Date:</span>
</td><td>
<span id="NBS247"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS247)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Sign/symptom observed on exam or described." id="INV272L" >
Sign/Symptom:</span></td><td>
<span id="INV272" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV272)"
codeSetNm="SIGN_SX_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span id="STD121L" title="The anatomic site of the sign/symptom.">
Anatomic Site:</span></td><td>
<span id="STD121" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(STD121)"
codeSetNm="PHVS_CLINICIANOBSERVEDLESIONS_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Provide a description of the other anatomic site." id="NBS248L">
Other Anatomic Site, Specify:</span>
</td>
<td>
<span id="NBS248"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS248)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS249L" title="The number of days signs/symptoms were present. Document “99” if unknown.">
Duration (Days):</span>
</td><td>
<span id="NBS249"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS249)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_63" name="Previous STD History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?" id="STD117L" >
Previous STD history (self-reported)?:</span></td><td>
<span id="STD117" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD117)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_64" name="STD History" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_64"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_64">
<tr id="patternNBS_INV_STD_UI_64" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_64');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_64">
<tr id="nopatternNBS_INV_STD_UI_64" class="odd" style="display:none">
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
<span title="With what condition was the patient previously diagnosed?" id="NBS250L" >
Previous Condition:</span></td><td>
<span id="NBS250" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS250)"
codeSetNm="STD_HISTORY_DIAGNOSIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Diagnosis Date of previous STD." id="NBS251L">Diagnosis Date:</span>
</td><td>
<span id="NBS251"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS251)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Treatment Date of previous STD." id="NBS252L">Treatment Date:</span>
</td><td>
<span id="NBS252"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS252)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Confirmed the Previous STD?" id="NBS253L" >
Confirmed:</span></td><td>
<span id="NBS253" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS253)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_66" name="Consented to Enrollment in Partner Services" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview)." id="NBS257L" >
Enrolled in Partner Services:</span></td><td>
<span id="NBS257" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS257)"
codeSetNm="ENROLL_HIV_PARTNER_SERVICES_IND"/>
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_67" name="Self-Reported Results" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Previously tested for 900?" id="NBS254L" >
Previous 900 Test:</span></td><td>
<span id="NBS254" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS254)"
codeSetNm="YNRUD"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test." id="STD106L" >
Self-reported or Documented Result:</span></td><td>
<span id="STD106" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD106)"
codeSetNm="STD_SELF_REPORTED_900_TEST_RESULT"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of last 900 Test" id="NBS259L">Date Last 900 Test:</span>
</td><td>
<span id="NBS259"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS259)"  />
</td></tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_68" name="Referred to Testing" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Referred for 900 test?" id="NBS260L" >
Refer for Test:</span></td><td>
<span id="NBS260" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS260)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date referred for 900 test." id="NBS261L">Referral Date:</span>
</td><td>
<span id="NBS261"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS261)"  />
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="900 test performed at this event?" id="NBS262L" >
900 Test:</span></td><td>
<span id="NBS262" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS262)"
codeSetNm="900_TST_AT_EVENT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date that the specimen for the HIV test was collected." id="NBS450L">900 Test Sample Date:</span>
</td><td>
<span id="NBS450"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS450)"  />
</td></tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Result of 900 test at this event." id="NBS263L" >
900 Result:</span></td><td>
<span id="NBS263" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS263)"
codeSetNm="900_TEST_RESULTS"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The partner was informed of their 900 test result?" id="NBS265L" >
Result provided:</span></td><td>
<span id="NBS265" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS265)"
codeSetNm="900_RESULT_PROVIDED"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Post-test Counselling" id="NBS264L" >
Post-test Counselling:</span></td><td>
<span id="NBS264" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS264)"
codeSetNm="YNU"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities." id="NBS447L" >
Patient Tested for Syphilis In Conjunction with HIV Test:</span></td><td>
<span id="NBS447" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS447)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services." id="NBS448L" >
Syphilis Test Result:</span></td><td>
<span id="NBS448" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS448)"
codeSetNm="SYPHILIS_TEST_RESULT_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_69" name="Referred to Medical Testing (900 +)" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Referred to 900 medical care/evaluation/treatment." id="NBS266L" >
Refer for Care:</span></td><td>
<span id="NBS266" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS266)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If referred, did patient keep 1st appointment?" id="NBS267L" >
Keep Appointment:</span></td><td>
<span id="NBS267" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS267)"
codeSetNm="KEEP_FIRST_APPT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services." id="NBS302L">Appointment Date (If Confirmed):</span>
</td><td>
<span id="NBS302"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS302)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_75" name="Pre Exposure Prophylaxis (PrEP)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication." id="NBS443L" >
Is the Client Currently On PrEP?:</span></td><td>
<span id="NBS443" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS443)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP)." id="NBS446L" >
Has Client Been Referred to PrEP Provider?:</span></td><td>
<span id="NBS446" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS446)"
codeSetNm="PREP_REFERRAL_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_70" name="Anti-Retroviral Therapy for HIV Infection" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Anti-virals taken within the last 12 months?" id="NBS255L" >
Anti-viral Therapy - Last 12 Months:</span></td><td>
<span id="NBS255" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS255)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Anti-virals ever taken (including past year)?" id="NBS256L" >
Anti-viral Therapy - Ever:</span></td><td>
<span id="NBS256" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS256)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
