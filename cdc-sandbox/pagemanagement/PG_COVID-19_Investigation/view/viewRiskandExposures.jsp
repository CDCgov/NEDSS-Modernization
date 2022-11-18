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
String tabId = "viewRiskandExposures";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Place of Residence","Healthcare Worker Information","Exposure Information"};
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
<nedss:container id="NBS_UI_GA24002" name="Place of Residence" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc." id="95421_4L" >
Is the patient a resident in a congregate care/living setting?:</span></td><td>
<span id="95421_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(95421_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202L" >
Which would best describe where the patient was staying at the time of illness onset?:</span></td><td>
<span id="NBS202" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202OthL">Other Which would best describe where the patient was staying at the time of illness onset?:</span></td>
<td> <span id="NBS202Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS202Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25005" name="Healthcare Worker Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient a health care worker in the United States?" id="NBS540L" >
Was patient healthcare personnel (HCP) at illness onset?:</span></td><td>
<span id="NBS540" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS540)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004L" >
If yes, what is their occupation (type of job)?:</span></td><td>
<span id="14679004" />
<nedss:view name="PageForm" property="pageClientVO.answer(14679004)"
codeSetNm="HCW_OCCUPATION_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004OthL">Other If yes, what is their occupation (type of job)?:</span></td>
<td> <span id="14679004Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(14679004Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683L" >
If yes, what is their job setting?:</span></td><td>
<span id="NBS683" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS683)"
codeSetNm="HCW_SETTING_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683OthL">Other If yes, what is their job setting?:</span></td>
<td> <span id="NBS683Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS683Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25007" name="Travel Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the case patient travel domestically within program specific time frame?" id="INV664L" >
Domestic travel (outside normal state of residence):</span></td><td>
<span id="INV664" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the case patient travel internationally?" id="TRAVEL38L" >
International Travel:</span></td><td>
<span id="TRAVEL38" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL38)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?" id="473085002L" >
Cruise ship or vessel travel as passenger or crew member:</span></td><td>
<span id="473085002" />
<nedss:view name="PageForm" property="pageClientVO.answer(473085002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Name of cruise ship or vessel." id="NBS690L">
Specify Name of Ship or Vessel:</span>
</td>
<td>
<span id="NBS690"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS690)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35002" name="Travel Events Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35002"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA35002">
<tr id="patternNBS_UI_GA35002" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA35002');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA35002">
<tr id="nopatternNBS_UI_GA35002" class="odd" style="display:none">
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
<span title="Indicate all countries of travel in the last 14 days." id="TRAVEL05L" >
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
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC_COVID19"/>
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

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="82310_4L" title="Duration of stay in country outside the US">
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
<nedss:container id="NBS_UI_GA21014" name="Other Exposure Events" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, did the patient have exposure in the workplace?" id="NBS684L" >
Workplace:</span></td><td>
<span id="NBS684" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS684)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?" id="NBS685L" >
If yes, is the workplace critical infrastructure (e.g. healthcare setting, grocery store)?:</span></td><td>
<span id="NBS685" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS685)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS686_CDL" title="Specify the workplace setting">
If yes, specify workplace setting:</span></td><td>
<span id="NBS686_CD" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS686_CD)"
codeSetNm="PHVS_CRITICALINFRASTRUCTURESECTOR_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Specify the workplace setting" id="NBS686_CDOthL">Other If yes, specify workplace setting:</span></td>
<td> <span id="NBS686_CDOth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS686_CDOth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, did the patient travel by air?" id="445000002L" >
Airport/Airplane:</span></td><td>
<span id="445000002" />
<nedss:view name="PageForm" property="pageClientVO.answer(445000002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility)." id="NBS687L" >
Adult Congregate Living Facility (nursing, assisted living, or LTC facility):</span></td><td>
<span id="NBS687" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS687)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, was patient exposed to a correctional facility?" id="NBS689L" >
Correctional Facility:</span></td><td>
<span id="NBS689" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS689)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, was the patient exposed to a school oruniversity ?" id="257698009L" >
School/University Exposure:</span></td><td>
<span id="257698009" />
<nedss:view name="PageForm" property="pageClientVO.answer(257698009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to illness onset, was the patient exposed to a child care facility?" id="413817003L" >
Child Care Facility Exposure:</span></td><td>
<span id="413817003" />
<nedss:view name="PageForm" property="pageClientVO.answer(413817003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did subject attend any events or large gatherings prior to onset of illness?" id="FDD_Q_184L" >
Community Event/Mass Gathering:</span></td><td>
<span id="FDD_Q_184" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_184)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?" id="NBS559L" >
Animal with confirmed or suspected COVID-19:</span></td><td>
<span id="NBS559" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS559)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_32L" title="Animal contact by type of animal">
Animal Type:</span></td><td>
<span id="FDD_Q_32" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_32)"
codeSetNm="PHVS_ANIMALTYPE_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Animal contact by type of animal" id="FDD_Q_32OthL">Other Animal Type:</span></td>
<td> <span id="FDD_Q_32Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_32Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have any other exposure type?" id="NBS560L" >
Other Exposure:</span></td><td>
<span id="NBS560" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS560)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other exposure." id="NBS561L">
Other Exposure Specify:</span>
</td>
<td>
<span id="NBS561"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS561)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Unknown exposures in the 14 days prior to illness onset" id="NBS667L" >
Unknown exposures in the 14 days prior to illness onset:</span></td><td>
<span id="NBS667" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS667)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25008" name="Exposure to Known Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact." id="NBS557L" >
Did the patient have contact with another COVID-19 case (probable or confirmed)?:</span></td><td>
<span id="NBS557" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS557)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?" id="NBS664L" >
Household contact:</span></td><td>
<span id="NBS664" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?" id="NBS665L" >
Community contact:</span></td><td>
<span id="NBS665" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS665)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?" id="NBS666L" >
Healthcare contact:</span></td><td>
<span id="NBS666" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS666)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If other contact with a known COVID-19 case, indicate other type of contact." id="INV603_6L" >
Other Contact Type?:</span></td><td>
<span id="INV603_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other contact type" id="INV897L">
Other Contact Type Specify:</span>
</td>
<td>
<span id="INV897"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV897)"  />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had contact with another COVID-19 case, was this person a U.S. case?" id="NBS543L" >
If the patient had contact with another COVID-19 case, was this person a U.S. case?:</span></td><td>
<span id="NBS543" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS543)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case." id="NBS350L">
nCoV ID of source case 1:</span>
</td>
<td>
<span id="NBS350"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_2L">
nCoV ID of source case 2:</span>
</td>
<td>
<span id="NBS350_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_3L">
nCoV ID of source case 3:</span>
</td>
<td>
<span id="NBS350_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350_3)" />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
