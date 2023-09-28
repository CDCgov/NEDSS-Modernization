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
String tabId = "view";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Patient Information","Occupation and Industry","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Place of Residence","Healthcare Worker Information","Exposure Information","Information Source","Signs &amp; Symptoms","Medical History","Vaccination","Respiratory Diagnostic Testing","COVID-19 Laboratory Findings","Contact Investigation","Retired Questions"};
;
%>
<tr><td>
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare1.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Information As of Date:</span>
</td><td>
<span id="NBS104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS104)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments pertaining to the patient." id="DEM196L">
Comments:</span>
</td>
<td>
<span id="DEM196"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM196)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS095L">Name Information As Of Date:</span>
</td><td>
<span id="NBS095"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS095)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's first name." id="DEM104L">
First Name:</span>
</td>
<td>
<span id="DEM104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM104)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's middle name or initial." id="DEM105L">
Middle Name:</span>
</td>
<td>
<span id="DEM105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM105)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's last name." id="DEM102L">
Last Name:</span>
</td>
<td>
<span id="DEM102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM102)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's name suffix" id="DEM107L" >
Suffix:</span></td><td>
<span id="DEM107" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM107)"
codeSetNm="P_NM_SFX"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS096L">Other Personal Details As Of Date:</span>
</td><td>
<span id="NBS096"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS096)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Reported date of birth of patient." id="DEM115L">Date of Birth:</span>
</td><td>
<span id="DEM115"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM115)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV2001L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td><td>
<span id="INV2001"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2001)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's age units" id="INV2002L" >
Reported Age Units:</span></td><td>
<span id="INV2002" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV2002)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of Birth" id="DEM126L" >
Country of Birth:</span></td><td>
<span id="DEM126" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex." id="DEM113L" >
Current Sex:</span></td><td>
<span id="DEM113" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM113)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS097L">Mortality Information As Of Date:</span>
</td><td>
<span id="NBS097"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS097)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator of whether or not a patient is alive or dead." id="DEM127L" >
Is the patient deceased?:</span></td><td>
<span id="DEM127" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM127)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date on which the individual died." id="DEM128L">Deceased Date:</span>
</td><td>
<span id="DEM128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM128)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098L">Marital Status As Of Date:</span>
</td><td>
<span id="NBS098"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS098)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A code indicating the married or similar partnership status of a patient." id="DEM140L" >
Marital Status:</span></td><td>
<span id="DEM140" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM140)"
codeSetNm="P_MARITAL"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS102L">Address Information As Of Date:</span>
</td><td>
<span id="NBS102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS102)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line one of the address label." id="DEM159L">
Street Address 1:</span>
</td>
<td>
<span id="DEM159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM159)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line two of the address label." id="DEM160L">
Street Address 2:</span>
</td>
<td>
<span id="DEM160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The city for a postal location." id="DEM161L">
City:</span>
</td>
<td>
<span id="DEM161"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM161)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The state code for a postal location." id="DEM162L" >
State:</span></td><td>
<span id="DEM162" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM162)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The zip code of a residence of the case patient or entity." id="DEM163L">
Zip:</span>
</td>
<td>
<span id="DEM163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM163)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient or entity." id="DEM165L" >
County:</span></td><td>
<span id="DEM165" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(DEM165)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(DEM162)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(DEM162)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM165)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.DEM165_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The country code for a postal location." id="DEM167L" >
Country:</span></td><td>
<span id="DEM167" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS103L">Telephone Information As Of Date:</span>
</td><td>
<span id="NBS103"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS103)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's home phone number." id="DEM177L">
Home Phone:</span>
</td>
<td>
<span id="DEM177"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM177)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's work phone number." id="NBS002L">
Work Phone:</span>
</td>
<td>
<span id="NBS002"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS002)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS003L" title="The patient's work phone number extension.">
Ext.:</span>
</td><td>
<span id="NBS003"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS003)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's cellular phone number." id="NBS006L">
Cell Phone:</span>
</td>
<td>
<span id="NBS006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS006)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's email address." id="DEM182L">
Email:</span>
</td>
<td>
<span id="DEM182"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM182)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS100L">Ethnicity Information As Of Date:</span>
</td><td>
<span id="NBS100"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS100)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the patient is hispanic or not." id="DEM155L" >
Ethnicity:</span></td><td>
<span id="DEM155" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM155)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS101L">Race Information As Of Date:</span>
</td><td>
<span id="NBS101"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS101)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152L">
Race:</span>
</td>
<td>
<div id="patientRacesViewContainer">
<logic:equal name="PageForm" property="pageClientVO.americanIndianAlskanRace" value="1"><bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.africanAmericanRace" value="1"><bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.whiteRace" value="1"><bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.asianRace" value="1"><bean:message bundle="RVCT" key="rvct.asian"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.hawaiianRace" value="1"><bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.otherRace" value="1"><bean:message bundle="RVCT" key="rvct.other"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.refusedToAnswer" value="1"><bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.notAsked" value="1"><bean:message bundle="RVCT" key="rvct.notAsked"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.unKnownRace" value="1"><bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
</div>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35007" name="Language" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What is the patient primary language? Please indicate for both hospitalized and not hospitalized cases." id="DEM142L" >
Primary Language:</span></td><td>
<span id="DEM142" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM142)"
codeSetNm="LANGUAGE"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA24000" name="Tribal Affiliation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have any indian tribal affiliation?" id="NBS681L" >
Does this case have any tribal affiliation?:</span></td><td>
<span id="NBS681" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS681)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="95370_3L" title="If tribal affiliation, which tribe(s)?">
Tribal Name:</span></td><td>
<span id="95370_3" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(95370_3)"
codeSetNm="PHVS_TRIBENAME_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If tribal affiliation, which tribe(s)?" id="95370_3OthL">Other Tribal Name:</span></td>
<td> <span id="95370_3Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(95370_3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient an enrolled tribal member?" id="NBS682L" >
Enrolled Tribal Member?:</span></td><td>
<span id="NBS682" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS682)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS779L" title="List enrolled tribe(s).">
Enrolled Tribe Name:</span></td><td>
<span id="NBS779" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS779)"
codeSetNm="PHVS_TRIBENAME_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="List enrolled tribe(s)." id="NBS779OthL">Other Enrolled Tribe Name:</span></td>
<td> <span id="NBS779Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS779Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35001" name="Occupation and Industry Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35001"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA35001">
<tr id="patternNBS_UI_GA35001" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA35001');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA35001">
<tr id="nopatternNBS_UI_GA35001" class="odd" style="display:none">
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
<td class="fieldName">
<span title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." id="85659_1L" >
Current Occupation Standardized:</span></td><td>
<span id="85659_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(85659_1)"
codeSetNm="PHVS_OCCUPATION_CDC_CENSUS2010"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="This data element is used to capture the narrative text of a subjects current occupation." id="85658_3L">
Current Occupation:</span>
</td>
<td>
<span id="85658_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(85658_3)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." id="85657_5L" >
Current  Industry Standardized:</span></td><td>
<span id="85657_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(85657_5)"
codeSetNm="PHVS_INDUSTRY_CDC_CENSUS2010"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="This data element is used to capture the narrative text of subjects current industry." id="85078_4L">
Current Industry:</span>
</td>
<td>
<span id="85078_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(85078_4)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107L" >
Jurisdiction:</span></td><td>
<span id="INV107" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108L" >
Program Area:</span></td><td>
<span id="INV108" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147L">Investigation Start Date:</span>
</td><td>
<span id="INV147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV147)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The status of the investigation." id="INV109L" >
Investigation Status:</span></td><td>
<span id="INV109" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV109)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="NBS012L" title="Should this record be shared with guests with program area and jurisdiction rights?">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
No</logic:notEqual>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The State ID associated with the case." id="INV173L">
State Case ID:</span>
</td>
<td>
<span id="INV173"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV173)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." id="INV200L">
NNDSS Local Record ID (NETSS):</span>
</td>
<td>
<span id="INV200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV200)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21015" name="COVID-19 Case Details" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC-Assigned Case ID" id="NBS547L">
CDC 2019-nCoV ID:</span>
</td>
<td>
<span id="NBS547"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS547)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What is the current processing status of this person?" id="NBS548L" >
What is the current status of this person?:</span></td><td>
<span id="NBS548" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS548)"
codeSetNm="PATIENT_STATUS_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If probable, select reason for case classification" id="NBS678L" >
If probable, select reason for case classification:</span></td><td>
<span id="NBS678" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS678)"
codeSetNm="CASE_CLASS_REASON_COVID"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS551L" title="Under what process was the PUI or case first identified?">
Under what process was the case first identified?:</span></td><td>
<span id="NBS551" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS551)"
codeSetNm="CASE_IDENTIFY_PROCESS_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Under what process was the PUI or case first identified?" id="NBS551OthL">Other Under what process was the case first identified?:</span></td>
<td> <span id="NBS551Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS551Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the PUI or case was first identified through and EpiX notification of travelers, enter the Division of Global Migration and Quarantine ID." id="NBS552L">
DGMQ ID:</span>
</td>
<td>
<span id="NBS552"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS552)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of first positive specimen collection" id="NBS550L">Date of first positive specimen collection:</span>
</td><td>
<span id="NBS550"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS550)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180L" title="The Public Health Investigator assigned to the Investigation.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV180)"/>
<span id="INV180">${PageForm.attributeMap.INV180SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was assigned/started." id="INV110L">Date Assigned to Investigation:</span>
</td><td>
<span id="INV110"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV110)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_22" name="Key Report Dates" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of report of the condition to the public health department." id="INV111L">Date of Report:</span>
</td><td>
<span id="INV111"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV111)"  />
</td></tr>
<!--skipping Hidden Date Question-->

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to county public health system." id="INV120L">Earliest Date Reported to County:</span>
</td><td>
<span id="INV120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to state public health system." id="INV121L">Earliest Date Reported to State:</span>
</td><td>
<span id="INV121"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV121)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of facility or provider associated with the source of information sent to Public Health." id="INV112L" >
Reporting Source Type:</span></td><td>
<span id="INV112" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV112)"
codeSetNm="PHVS_REPORTINGSOURCETYPE_NND"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV183L" title="The organization that reported the case.">
Reporting Organization:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV183)"/>
<span id="INV183">${PageForm.attributeMap.INV183SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV181L" title="The provider that reported the case.">
Reporting Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV181)"/>
<span id="INV181">${PageForm.attributeMap.INV181SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_1" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="County reporting the notification." id="NOT113L" >
Reporting County:</span></td><td>
<span id="NOT113" />
<nedss:view name="PageForm" property="pageClientVO.answer(NOT113)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.NOT113_STATE}"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12" name="Physician" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182L" title="The physician associated with this case.">
Physician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV182)"/>
<span id="INV182">${PageForm.attributeMap.INV182SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient hospitalized as a result of this event?" id="INV128L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184L" title="The hospital associated with the investigation.">
Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV184)"/>
<span id="INV184">${PageForm.attributeMap.INV184SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132L">Admission Date:</span>
</td><td>
<span id="INV132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133L">Discharge Date:</span>
</td><td>
<span id="INV133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV133)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV134L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td><td>
<span id="INV134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV134)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient need or want an interpreter to communicate with doctor or healthcare staff?" id="54588_9L" >
If hospitalized, was a translator required?:</span></td><td>
<span id="54588_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(54588_9)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient admitted to an intensive care unit (ICU)?" id="309904001L" >
Was the patient admitted to an intensive care unit (ICU)?:</span></td><td>
<span id="309904001" />
<nedss:view name="PageForm" property="pageClientVO.answer(309904001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of ICU admission." id="NBS679L">ICU Admission Date:</span>
</td><td>
<span id="NBS679"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS679)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of ICU discharge." id="NBS680L">ICU Discharge Date:</span>
</td><td>
<span id="NBS680"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS680)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the subject dies as a result of the illness." id="INV145L" >
Did the patient die from this illness?:</span></td><td>
<span id="INV145" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV145)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the subject’s death occurred." id="INV146L">Date of Death:</span>
</td><td>
<span id="INV146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV146)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the date of death is unknown, select yes." id="NBS545L" >
Unknown Date of Death:</span></td><td>
<span id="NBS545" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS545)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136L">Diagnosis Date:</span>
</td><td>
<span id="INV136"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV136)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility." id="INV148L" >
Is this person associated with a day care facility?:</span></td><td>
<span id="INV148" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was food handler." id="INV149L" >
Is this person a food handler?:</span></td><td>
<span id="INV149" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV149)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Denotes whether the reported case was associated with an identified outbreak." id="INV150L" >
Is this case part of an outbreak?:</span></td><td>
<span id="INV150" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV150)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation." id="INV151L" >
Outbreak Name:</span></td><td>
<span id="INV151" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV151)"
codeSetNm="OUTBREAK_NM"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of where the disease/condition was likely acquired." id="INV152L" >
Where was the disease acquired?:</span></td><td>
<span id="INV152" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV152)"
codeSetNm="PHVS_DISEASEACQUIREDJURISDICTION_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the country in which the disease was likely acquired." id="INV153L" >
Imported Country:</span></td><td>
<span id="INV153" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV153)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." id="INV154L" >
Imported State:</span></td><td>
<span id="INV154" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV154)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." id="INV155L">
Imported City:</span>
</td>
<td>
<span id="INV155"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV155)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition." id="INV156L" >
Imported County:</span></td><td>
<span id="INV156" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(INV156)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(INV154)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(INV154)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV156)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.INV156_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where does the person usually live (defined as their residence)." id="INV501L" >
Country of Usual Residence:</span></td><td>
<span id="INV501" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_4" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_GENV2_UI_4"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4">
<tr id="patternNBS_UI_NBS_INV_GENV2_UI_4" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_NBS_INV_GENV2_UI_4">
<tr id="nopatternNBS_UI_NBS_INV_GENV2_UI_4" class="odd" style="display:none">
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
<td class="fieldName">
<span title="Indicates the country in which the disease was potentially acquired." id="INV502L" >
Country of Exposure:</span></td><td>
<span id="INV502" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV502)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the state in which the disease was potentially acquired." id="INV503L" >
State or Province of Exposure:</span></td><td>
<span id="INV503" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV503)"
codeSetNm="PHVS_STATEPROVINCEOFEXPOSURE_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicates the city in which the disease was potentially acquired." id="INV504L">
City of Exposure:</span>
</td>
<td>
<span id="INV504"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV504)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the county in which the disease was potentially acquired." id="INV505L" >
County of Exposure:</span></td><td>
<span id="INV505" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV505)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.INV505_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_5" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV515L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span></td><td>
<span id="INV515" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV515)"
codeSetNm="PHVS_BINATIONALREPORTINGCRITERIA_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate." id="INV157L" >
Transmission Mode:</span></td><td>
<span id="INV157" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV157)"
codeSetNm="PHC_TRAN_M"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV159L" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">Detection Method:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(INV159)" styleId="INV159"><html:optionsCollection property="codedValue(PHC_DET_MT)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV161L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span></td><td>
<span id="INV161" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV161)"
codeSetNm="PHC_CONF_M"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If an investigation is confirmed as a case, then the confirmation date is entered." id="INV162L">Confirmation Date:</span>
</td><td>
<span id="INV162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV162)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The current status of the investigation/case." id="INV163L" >
Case Status:</span></td><td>
<span id="INV163" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV163)"
codeSetNm="PHC_CLASS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR week in which the case should be counted." id="INV165L">
MMWR Week:</span>
</td>
<td>
<span id="INV165"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV165)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR year in which the case should be counted." id="INV166L">
MMWR Year:</span>
</td>
<td>
<span id="INV166"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV166)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" id="NOT120L" >
Immediate National Notifiable Condition:</span></td><td>
<span id="NOT120" />
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPECL">
If yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120SPEC)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176L">Date CDC Was First Verbally Notified of This Case:</span>
</td><td>
<span id="INV176"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV176)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data." id="INV886L">
Notification Comments to CDC:</span>
</td>
<td>
<span id="INV886"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV886)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Field which contains general comments for the investigation." id="INV167L">
General Comments:</span>
</td>
<td>
<span id="INV167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV167)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA24002" name="Place of Residence" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc." id="95421_4L" >
Is the patient a resident in a congregate care/living setting?:</span></td><td>
<span id="95421_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(95421_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202L" >
Which would best describe where the patient was staying at the time of illness onset?:</span></td><td>
<span id="NBS202" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202OthL">Other Which would best describe where the patient was staying at the time of illness onset?:</span></td>
<td> <span id="NBS202Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS202Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25005" name="Healthcare Worker Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a health care worker in the United States?" id="NBS540L" >
Was patient healthcare personnel (HCP) at illness onset?:</span></td><td>
<span id="NBS540" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS540)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004L" >
If yes, what is their occupation (type of job)?:</span></td><td>
<span id="14679004" />
<nedss:view name="PageForm" property="pageClientVO.answer(14679004)"
codeSetNm="HCW_OCCUPATION_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004OthL">Other If yes, what is their occupation (type of job)?:</span></td>
<td> <span id="14679004Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(14679004Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683L" >
If yes, what is their job setting?:</span></td><td>
<span id="NBS683" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS683)"
codeSetNm="HCW_SETTING_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683OthL">Other If yes, what is their job setting?:</span></td>
<td> <span id="NBS683Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS683Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25007" name="Travel Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the case patient travel domestically within program specific time frame?" id="INV664L" >
Domestic travel (outside normal state of residence):</span></td><td>
<span id="INV664" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the case patient travel internationally?" id="TRAVEL38L" >
International Travel:</span></td><td>
<span id="TRAVEL38" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL38)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?" id="473085002L" >
Cruise ship or vessel travel as passenger or crew member:</span></td><td>
<span id="473085002" />
<nedss:view name="PageForm" property="pageClientVO.answer(473085002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
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
<td class="fieldName">
<span title="Indicate all countries of travel in the last 14 days." id="TRAVEL05L" >
Country of Travel:</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Domestic destination, states traveled" id="82754_3L" >
State of Travel:</span></td><td>
<span id="82754_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(82754_3)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC_COVID19"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Principal Reason for Travel" id="TRAVEL16L" >
Principal Reason for Travel:</span></td><td>
<span id="TRAVEL16" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16)"
codeSetNm="PHVS_TRAVELREASON_MALARIA"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Principal Reason for Travel" id="TRAVEL16OthL">Other Principal Reason for Travel:</span></td>
<td> <span id="TRAVEL16Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject traveled, when did they arrive to their travel destination?" id="TRAVEL06L">Date of Arrival at Destination:</span>
</td><td>
<span id="TRAVEL06"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL06)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject traveled, when did they depart from their travel destination?" id="TRAVEL07L">Date of Departure from Destination:</span>
</td><td>
<span id="TRAVEL07"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL07)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="82310_4L" title="Duration of stay in country outside the US">
Duration of Stay:</span>
</td><td>
<span id="82310_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82310_4)"  />
<span id="82310_4UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82310_4Unit)" codeSetNm="PHVS_DURATIONUNIT_CDC_1" />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
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
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient have exposure in the workplace?" id="NBS684L" >
Workplace:</span></td><td>
<span id="NBS684" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS684)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?" id="NBS685L" >
If yes, is the workplace critical infrastructure (e.g. healthcare setting, grocery store)?:</span></td><td>
<span id="NBS685" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS685)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS686_CDL" title="Specify the workplace setting">
If yes, specify workplace setting:</span></td><td>
<span id="NBS686_CD" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS686_CD)"
codeSetNm="PHVS_CRITICALINFRASTRUCTURESECTOR_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Specify the workplace setting" id="NBS686_CDOthL">Other If yes, specify workplace setting:</span></td>
<td> <span id="NBS686_CDOth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS686_CDOth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient travel by air?" id="445000002L" >
Airport/Airplane:</span></td><td>
<span id="445000002" />
<nedss:view name="PageForm" property="pageClientVO.answer(445000002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility)." id="NBS687L" >
Adult Congregate Living Facility (nursing, assisted living, or LTC facility):</span></td><td>
<span id="NBS687" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS687)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was patient exposed to a correctional facility?" id="NBS689L" >
Correctional Facility:</span></td><td>
<span id="NBS689" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS689)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a school oruniversity ?" id="257698009L" >
School/University Exposure:</span></td><td>
<span id="257698009" />
<nedss:view name="PageForm" property="pageClientVO.answer(257698009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a child care facility?" id="413817003L" >
Child Care Facility Exposure:</span></td><td>
<span id="413817003" />
<nedss:view name="PageForm" property="pageClientVO.answer(413817003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did subject attend any events or large gatherings prior to onset of illness?" id="FDD_Q_184L" >
Community Event/Mass Gathering:</span></td><td>
<span id="FDD_Q_184" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_184)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?" id="NBS559L" >
Animal with confirmed or suspected COVID-19:</span></td><td>
<span id="NBS559" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS559)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_32L" title="Animal contact by type of animal">
Animal Type:</span></td><td>
<span id="FDD_Q_32" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_32)"
codeSetNm="PHVS_ANIMALTYPE_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Animal contact by type of animal" id="FDD_Q_32OthL">Other Animal Type:</span></td>
<td> <span id="FDD_Q_32Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_32Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any other exposure type?" id="NBS560L" >
Other Exposure:</span></td><td>
<span id="NBS560" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS560)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other exposure." id="NBS561L">
Other Exposure Specify:</span>
</td>
<td>
<span id="NBS561"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS561)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact." id="NBS557L" >
Did the patient have contact with another COVID-19 case (probable or confirmed)?:</span></td><td>
<span id="NBS557" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS557)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?" id="NBS664L" >
Household contact:</span></td><td>
<span id="NBS664" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?" id="NBS665L" >
Community contact:</span></td><td>
<span id="NBS665" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS665)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?" id="NBS666L" >
Healthcare contact:</span></td><td>
<span id="NBS666" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS666)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If other contact with a known COVID-19 case, indicate other type of contact." id="INV603_6L" >
Other Contact Type?:</span></td><td>
<span id="INV603_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
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
<td class="fieldName">
<span title="If the patient had contact with another COVID-19 case, was this person a U.S. case?" id="NBS543L" >
If the patient had contact with another COVID-19 case, was this person a U.S. case?:</span></td><td>
<span id="NBS543" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS543)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case." id="NBS350L">
nCoV ID of source case 1:</span>
</td>
<td>
<span id="NBS350"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_2L">
nCoV ID of source case 2:</span>
</td>
<td>
<span id="NBS350_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_3L">
nCoV ID of source case 3:</span>
</td>
<td>
<span id="NBS350_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS350_3)" />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25014" name="Information Source Details" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS553L" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.).">
Information Source for Clinical Information (check all that apply):</span></td><td>
<span id="NBS553" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS553)"
codeSetNm="INFO_SOURCE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." id="NBS553OthL">Other Information Source for Clinical Information (check all that apply):</span></td>
<td> <span id="NBS553Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS553Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21003" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Were symptoms present during course of illness?" id="INV576L" >
Symptoms present during course of illness:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137L">Date of Symptom Onset:</span>
</td><td>
<span id="INV137"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138L">Date of Symptom Resolution:</span>
</td><td>
<span id="INV138"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV138)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Status of reported symptom(s)." id="NBS555L" >
If symptomatic, symptom status:</span></td><td>
<span id="NBS555" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS555)"
codeSetNm="SYMPTOM_STATUS_COVID"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV139L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td><td>
<span id="INV139"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV139)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit of time used to describe the length of the illness or condition." id="INV140L" >
Illness Duration Units:</span></td><td>
<span id="INV140" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV140)"
codeSetNm="PHVS_DURATIONUNIT_CDC"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV143L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td><td>
<span id="INV143"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV143)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The age units for an age." id="INV144L" >
Age at Onset Units:</span></td><td>
<span id="INV144" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV144)"
codeSetNm="AGE_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21016" name="Clinical Findings" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pneumonia?" id="233604007L" >
Did the patient develop pneumonia?:</span></td><td>
<span id="233604007" />
<nedss:view name="PageForm" property="pageClientVO.answer(233604007)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005L" >
Did the patient have acute respiratory distress syndrome?:</span></td><td>
<span id="67782005" />
<nedss:view name="PageForm" property="pageClientVO.answer(67782005)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an abnormal chest X-ray?" id="168734001L" >
Did the patient have an abnormal chest X-ray?:</span></td><td>
<span id="168734001" />
<nedss:view name="PageForm" property="pageClientVO.answer(168734001)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an abnormal EKG?" id="102594003L" >
Did the patient have an abnormal EKG?:</span></td><td>
<span id="102594003" />
<nedss:view name="PageForm" property="pageClientVO.answer(102594003)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have another diagnosis/etiology for their illness?" id="NBS546L" >
Did the patient have another diagnosis/etiology for their illness?:</span></td><td>
<span id="NBS546" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS546)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6L">
Secondary Diagnosis Description 1:</span>
</td>
<td>
<span id="81885_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_2L">
Secondary Diagnosis Description 2:</span>
</td>
<td>
<span id="81885_6_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_3L">
Secondary Diagnosis Description 3:</span>
</td>
<td>
<span id="81885_6_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other clinical findings associated with the illness being reported" id="NBS776L" >
Other Clinical Finding:</span></td><td>
<span id="NBS776" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS776)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other clinical finding" id="NBS776_OTHL">
Other Clinical Finding Specify:</span>
</td>
<td>
<span id="NBS776_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS776_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35003" name="Clinical Treatments" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive mechanical ventilation (MV)/intubation?" id="NBS673L" >
Did the patient receive mechanical ventilation (MV)/intubation?:</span></td><td>
<span id="NBS673" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV547L" title="Total days with mechanical ventilation.">
Total days with Mechanical Ventilation:</span>
</td><td>
<span id="INV547"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV547)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive ECMO?" id="233573008L" >
Did the patient receive ECMO?:</span></td><td>
<span id="233573008" />
<nedss:view name="PageForm" property="pageClientVO.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS780L" title="Total days with Extracorporeal Membrane Oxygenation">
Total days with ECMO:</span>
</td><td>
<span id="NBS780"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS780)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator for other treatment type specify indicator" id="NBS781L" >
Other Treatment Type?:</span></td><td>
<span id="NBS781" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS781)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the treatment type is Other, specify the treatment." id="NBS389L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS389)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS782L" title="Total days with Other Treatment Type">
Total days with Other Treatment Type:</span>
</td><td>
<span id="NBS782"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS782)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21002" name="Symptom Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fever &gt;100.4F (38C)c?" id="386661006L" >
Fever &gt;100.4F (38C):</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV202L" title="What was the patients highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
<span id="INV202UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have subjective fever (felt feverish)?" id="103001002L" >
Subjective fever (felt feverish):</span></td><td>
<span id="103001002" />
<nedss:view name="PageForm" property="pageClientVO.answer(103001002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have chills?" id="28376_2L" >
Chills:</span></td><td>
<span id="28376_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(28376_2)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have rigors?" id="38880002L" >
Rigors:</span></td><td>
<span id="38880002" />
<nedss:view name="PageForm" property="pageClientVO.answer(38880002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have muscle aches (myalgia)?" id="68962001L" >
Muscle aches (myalgia):</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient  have runny nose (rhinorrhea)?" id="82272006L" >
Runny nose (rhinorrhea):</span></td><td>
<span id="82272006" />
<nedss:view name="PageForm" property="pageClientVO.answer(82272006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a sore throat?" id="267102003L" >
Sore Throat:</span></td><td>
<span id="267102003" />
<nedss:view name="PageForm" property="pageClientVO.answer(267102003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience new olfactory disorder?" id="44169009L" >
New Olfactory Disorder:</span></td><td>
<span id="44169009" />
<nedss:view name="PageForm" property="pageClientVO.answer(44169009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience new taste disorder?" id="36955009L" >
New Taste Disorder:</span></td><td>
<span id="36955009" />
<nedss:view name="PageForm" property="pageClientVO.answer(36955009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have headache?" id="25064002L" >
Headache:</span></td><td>
<span id="25064002" />
<nedss:view name="PageForm" property="pageClientVO.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have new confusion or change in mental status?" id="419284004L" >
New confusion or change in mental status?:</span></td><td>
<span id="419284004" />
<nedss:view name="PageForm" property="pageClientVO.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fatigue or malaise?" id="271795006L" >
Fatigue or malaise:</span></td><td>
<span id="271795006" />
<nedss:view name="PageForm" property="pageClientVO.answer(271795006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Inability to Wake or Stay Awake" id="NBS793L" >
Inability to Wake or Stay Awake:</span></td><td>
<span id="NBS793" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS793)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a Cough (new onset or worsening of chronic cough)?" id="49727002L" >
Cough (new onset or worsening of chronic cough):</span></td><td>
<span id="49727002" />
<nedss:view name="PageForm" property="pageClientVO.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience wheezing?" id="56018004L" >
Wheezing:</span></td><td>
<span id="56018004" />
<nedss:view name="PageForm" property="pageClientVO.answer(56018004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007L" >
Shortness of Breath (dyspnea):</span></td><td>
<span id="267036007" />
<nedss:view name="PageForm" property="pageClientVO.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience difficulty breathing?" id="230145002L" >
Difficulty Breathing:</span></td><td>
<span id="230145002" />
<nedss:view name="PageForm" property="pageClientVO.answer(230145002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?" id="3415004L" >
Pale/gray/blue skin/lips/nail beds?:</span></td><td>
<span id="3415004" />
<nedss:view name="PageForm" property="pageClientVO.answer(3415004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience chest pain?" id="29857009L" >
Chest Pain:</span></td><td>
<span id="29857009" />
<nedss:view name="PageForm" property="pageClientVO.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Persistent Pain or Pressure in Chest" id="NBS794L" >
Persistent Pain or Pressure in Chest:</span></td><td>
<span id="NBS794" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS794)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have nausea?" id="16932000L" >
Nausea:</span></td><td>
<span id="16932000" />
<nedss:view name="PageForm" property="pageClientVO.answer(16932000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience vomiting?" id="422400008L" >
Vomiting:</span></td><td>
<span id="422400008" />
<nedss:view name="PageForm" property="pageClientVO.answer(422400008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001L" >
Abdominal Pain or Tenderness:</span></td><td>
<span id="21522001" />
<nedss:view name="PageForm" property="pageClientVO.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?" id="62315008L" >
Diarrhea (=3 loose/looser than normal stools/24hr period):</span></td><td>
<span id="62315008" />
<nedss:view name="PageForm" property="pageClientVO.answer(62315008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did patient have loss of appetite?" id="79890006L" >
Loss of appetite:</span></td><td>
<span id="79890006" />
<nedss:view name="PageForm" property="pageClientVO.answer(79890006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338L" >
Other symptom(s)?:</span></td><td>
<span id="NBS338" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other signs and symptoms." id="NBS338_OTHL">
Other Symptoms:</span>
</td>
<td>
<span id="NBS338_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21004" name="Symptom Notes" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
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
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21009" name="Pre-Existing Conditions" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of pre-existing medical conditions?" id="102478008L" >
Pre-existing medical conditions?:</span></td><td>
<span id="102478008" />
<nedss:view name="PageForm" property="pageClientVO.answer(102478008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21008" name="Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have diabetes mellitus?" id="73211009L" >
Diabetes Mellitus:</span></td><td>
<span id="73211009" />
<nedss:view name="PageForm" property="pageClientVO.answer(73211009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?" id="ARB017L" >
Hypertension:</span></td><td>
<span id="ARB017" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB017)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient severely obese (BMI &gt;= 40)?" id="414916001L" >
Severe Obesity (BMI &gt;=40):</span></td><td>
<span id="414916001" />
<nedss:view name="PageForm" property="pageClientVO.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of cardiovascular disease?" id="128487001L" >
Cardiovascular disease:</span></td><td>
<span id="128487001" />
<nedss:view name="PageForm" property="pageClientVO.answer(128487001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of chronic renal disease?" id="709044004L" >
Chronic Renal disease:</span></td><td>
<span id="709044004" />
<nedss:view name="PageForm" property="pageClientVO.answer(709044004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of chronic liver disease?" id="328383001L" >
Chronic Liver disease:</span></td><td>
<span id="328383001" />
<nedss:view name="PageForm" property="pageClientVO.answer(328383001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001L" >
Chronic Lung Disease (asthma/emphysema/COPD):</span></td><td>
<span id="413839001" />
<nedss:view name="PageForm" property="pageClientVO.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any history of other chronic disease?" id="NBS662L" >
Other Chronic Diseases:</span></td><td>
<span id="NBS662" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS662)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the other chronic disease(s)." id="NBS662_OTHL">
Specify Other Chronic Diseases:</span>
</td>
<td>
<span id="NBS662_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS662_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any other underlying conditions or risk behaviors?" id="NBS677L" >
Other Underlying Condition or Risk Behavior:</span></td><td>
<span id="NBS677" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS677)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had an other underlying condition or risk behavior, specify the condition or behavior." id="NBS677_OTHL">
Specify Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<span id="NBS677_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS677_OTH)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of an immunocompromised/immunosuppressive condition?" id="370388006L" >
Immunosuppressive Condition:</span></td><td>
<span id="370388006" />
<nedss:view name="PageForm" property="pageClientVO.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an autoimmune disease or condition?" id="85828009L" >
Autoimmune Condition:</span></td><td>
<span id="85828009" />
<nedss:view name="PageForm" property="pageClientVO.answer(85828009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a current smoker?" id="65568007L" >
Current smoker:</span></td><td>
<span id="65568007" />
<nedss:view name="PageForm" property="pageClientVO.answer(65568007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a former smoker?" id="8517006L" >
Former smoker:</span></td><td>
<span id="8517006" />
<nedss:view name="PageForm" property="pageClientVO.answer(8517006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient engage in substance abuse or misuse?" id="NBS676L" >
Substance Abuse or Misuse:</span></td><td>
<span id="NBS676" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS676)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?" id="110359009L" >
Disability:</span></td><td>
<span id="110359009" />
<nedss:view name="PageForm" property="pageClientVO.answer(110359009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671L">
Specify Disability 1:</span>
</td>
<td>
<span id="NBS671"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_2L">
Specify Disability 2:</span>
</td>
<td>
<span id="NBS671_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_3L">
Specify Disability 3:</span>
</td>
<td>
<span id="NBS671_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a psychological or psychiatric condition?" id="74732009L" >
Psychological or Psychiatric Condition:</span></td><td>
<span id="74732009" />
<nedss:view name="PageForm" property="pageClientVO.answer(74732009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691L">
Specify Psychological or Psychiatric Condition 1:</span>
</td>
<td>
<span id="NBS691"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_2L">
Specify Psychological or Psychiatric Condition 2:</span>
</td>
<td>
<span id="NBS691_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_3L">
Specify Psychological or Psychiatric Condition 3:</span>
</td>
<td>
<span id="NBS691_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assesses whether or not the patient is pregnant." id="INV178L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject in pregnant, please enter the due date." id="INV579L">Due Date:</span>
</td><td>
<span id="INV579"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV579)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAR159L" title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks).">
Number of Weeks Gestation at Onset of Illness:</span>
</td><td>
<span id="VAR159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR159)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease." id="VAR160L" >
Trimester at Onset of Illness:</span></td><td>
<span id="VAR160" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAR160)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35004" name="Vaccination Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did subject ever receive a COVID-containing vaccine?" id="VAC126L" >
Did the patient ever received a COVID-containing vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC140L" title="Number of vaccine doses against this disease prior to illness onset">
Vaccination Doses Prior to Onset:</span>
</td><td>
<span id="VAC140"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC140)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of last vaccine dose against this disease prior to illness onset" id="VAC142L">Date of Last Dose Prior to Illness Onset:</span>
</td><td>
<span id="VAC142"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC142)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?" id="VAC148L" >
Vaccinated per ACIP Recommendations:</span></td><td>
<span id="VAC148" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149L" >
Reason Not Vaccinated Per ACIP Recommendations:</span></td><td>
<span id="VAC149" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC149)"
codeSetNm="VACCINE_NOT_GIVEN_REASON_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149OthL">Other Reason Not Vaccinated Per ACIP Recommendations:</span></td>
<td> <span id="VAC149Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(VAC149Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Comments about the subjects vaccination history" id="VAC133L">
Vaccine History Comments:</span>
</td>
<td>
<span id="VAC133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC133)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21012" name="Laboratory Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740L" >
Laboratory Testing Performed:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV575L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span></td><td>
<span id="INV575" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV575)"
codeSetNm="PHVS_REASONFORTEST_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Listing of the reason(s) the subject was tested for the condition" id="INV575OthL">Other Reason for Testing (check all that apply):</span></td>
<td> <span id="INV575Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV575Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="COVID-19 Variant" id="NBS786L" >
COVID-19 Variant:</span></td><td>
<span id="NBS786" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS786)"
codeSetNm="COVID_19_VARIANTS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="COVID-19 Variant" id="NBS786OthL">Other COVID-19 Variant:</span></td>
<td> <span id="NBS786Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS786Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21011" name="COVID-19 Testing" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA21011"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA21011">
<tr id="patternNBS_UI_GA21011" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA21011');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA21011">
<tr id="nopatternNBS_UI_GA21011" class="odd" style="display:none">
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
<td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606L" >
Performing Lab Type:</span></td><td>
<span id="LAB606" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_VPD_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Lab Type:</span></td>
<td> <span id="LAB606Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB606Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Please enter the performing lab specimen ID number for this lab test." id="NBS674L">
Specimen ID:</span>
</td>
<td>
<span id="NBS674"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS674)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specimen type from which positive lab specimen was collected." id="LAB165L" >
Specimen Source:</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="SPECIMEN_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB165Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Lab Test Type" id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="TEST_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Lab Test Type" id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Lab test coded result" id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_VPD_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Lab test coded result" id="INV291OthL">Other Test Result:</span></td>
<td> <span id="INV291Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV291Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Quantitative Test Result Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Comments having to do specifically with the lab result test. These are the comments from the NTE segment if the result was originally an Electronic Laboratory Report." id="8251_1L">
Test Result Comments:</span>
</td>
<td>
<span id="8251_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(8251_1)"  />
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="LAB331L" title="Was the isolate sent to a state public health laboratory? (Answer Yes if it was sent to any state lab, even if it was sent to a lab outside of the cases state of residence)">Specimen Sent to State Public Health Lab?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(LAB331)" styleId="LAB331"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="LAB515L" title="Was a specimen sent to CDC for testing?">Specimen Sent to CDC?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(LAB515)" styleId="LAB515"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23001" name="Additional State or Local Specimen IDs" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23001"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA23001">
<tr id="patternNBS_UI_GA23001" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA23001');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA23001">
<tr id="nopatternNBS_UI_GA23001" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Please provide any additional specimen ID of interest." id="NBS670L">
Additional Specimen ID:</span>
</td>
<td>
<span id="NBS670"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS670)" />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056L">Infectious Period From:</span>
</td><td>
<span id="NBS056"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057L">Infectious Period To:</span>
</td><td>
<span id="NBS057"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The status of the contact investigation." id="NBS058L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25002" name="Retired Questions" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Report Date of Person Under Investigation (PUI) to CDC" id="NBS549L">Report Date of PUI to CDC:</span>
</td><td>
<span id="NBS549"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS549)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?" id="NBS541L" >
Patient history of being in a healthcare facility (as a patient, worker or visitor) in China?:</span></td><td>
<span id="NBS541" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS541)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of healthcare contact with another lab-confirmed case-patient." id="NBS544L" >
Type of healthcare contact:</span></td><td>
<span id="NBS544" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS544)"
codeSetNm="HC_CONTACT_TYPE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology." id="NBS558L" >
Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology:</span></td><td>
<span id="NBS558" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS558)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS556L" title="Did the patient travel to any high-risk locations?">
Did the patient travel to any high-risk locations:</span></td><td>
<span id="NBS556" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS556)"
codeSetNm="HIGH_RISK_TRAVEL_LOC_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Did the patient travel to any high-risk locations?" id="NBS556OthL">Other Did the patient travel to any high-risk locations:</span></td>
<td> <span id="NBS556Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS556Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02." id="NBS554L">
Source patient case ID:</span>
</td>
<td>
<span id="NBS554"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS554)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date the case was reported to CDC." id="NBS663L">Report Date of Case to CDC:</span>
</td><td>
<span id="NBS663"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS663)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA36000" name="Retired Questions Do Not Use" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Questions in this section have been replaced by other standard questions. These versions of the questions should not be used going forward, but are on the page to maintain any data previously collected.</span></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the patient has any tribal affiliation, enter the Indian Tribe name." id="67884_7L">
Tribe Name(s) (Retired):</span>
</td>
<td>
<span id="67884_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(67884_7)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?" id="54899_0L">
If yes, specify which language - Retired:</span>
</td>
<td>
<span id="54899_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(54899_0)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the type of workplace setting" id="NBS686L">
If yes, specify workplace setting (Retired):</span>
</td>
<td>
<span id="NBS686"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS686)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the type of animal with which the patient had contact." id="FDD_Q_32_TXTL">
Specify Type of Animal (Retired):</span>
</td>
<td>
<span id="FDD_Q_32_TXT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_32_TXT)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?" id="NBS688L" >
School/University/Childcare Center Exposure - Retired:</span></td><td>
<span id="NBS688" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS688)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have chills or rigors?" id="43724002L" >
Chills or Rigors - Retired:</span></td><td>
<span id="43724002" />
<nedss:view name="PageForm" property="pageClientVO.answer(43724002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?" id="NBS675L" >
New Olfactory and Taste Disorder - Retired:</span></td><td>
<span id="NBS675" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS675)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS777L" title="Indicate all the countries of travel in the last 14 days (Multi-Select)">
Travel Country (Multi Select):</span></td><td>
<span id="NBS777" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS777)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS778L" title="Indicate all the states of travel in the last 14 days (Multi-Select)">
Travel State (Multi-Select):</span></td><td>
<span id="NBS778" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS778)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA22003" name="Respiratory Diagnostic Testing Retired" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza A Rapid Ag Result" id="80382_5L" >
Influenza A Rapid Ag:</span></td><td>
<span id="80382_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(80382_5)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza B Rapid Ag Result" id="80383_3L" >
Influenza B Rapid Ag:</span></td><td>
<span id="80383_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(80383_3)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza A PCR Result" id="34487_9L" >
Influenza A PCR:</span></td><td>
<span id="34487_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(34487_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza B PCR Result" id="40982_1L" >
Influenza B PCR:</span></td><td>
<span id="40982_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(40982_1)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Respiratory Syncytial Virus (RSV) Result" id="6415009L" >
RSV:</span></td><td>
<span id="6415009" />
<nedss:view name="PageForm" property="pageClientVO.answer(6415009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="H. metapneumovirus Result" id="416730002L" >
H. metapneumovirus:</span></td><td>
<span id="416730002" />
<nedss:view name="PageForm" property="pageClientVO.answer(416730002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Parainfluenza (1-4) Result" id="41505_9L" >
Parainfluenza (1-4):</span></td><td>
<span id="41505_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(41505_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Adenovirus Result" id="74871001L" >
Adenovirus:</span></td><td>
<span id="74871001" />
<nedss:view name="PageForm" property="pageClientVO.answer(74871001)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Rhinovirus/Enterovirus Result" id="69239002L" >
Rhinovirus/enterovirus:</span></td><td>
<span id="69239002" />
<nedss:view name="PageForm" property="pageClientVO.answer(69239002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Coronavirus (OC43, 229E, HKU1, NL63) Result" id="84101006L" >
Coronavirus (OC43, 229E, HKU1, NL63):</span></td><td>
<span id="84101006" />
<nedss:view name="PageForm" property="pageClientVO.answer(84101006)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="M. pneumoniae Result" id="58720004L" >
M. pneumoniae:</span></td><td>
<span id="58720004" />
<nedss:view name="PageForm" property="pageClientVO.answer(58720004)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="C. pneumoniae Result" id="103514009L" >
C. pneumoniae:</span></td><td>
<span id="103514009" />
<nedss:view name="PageForm" property="pageClientVO.answer(103514009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether additional pathogen testing was completed for this patient." id="NBS672L" >
Were Other Pathogen(s) Tested?:</span></td><td>
<span id="NBS672" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS672)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23000" name="Other Pathogens Tested Retired" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23000"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA23000">
<tr id="patternNBS_UI_GA23000" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_GA23000');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA23000">
<tr id="nopatternNBS_UI_GA23000" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Other Pathogen Tested" id="NBS669L">
Specify Other Pathogen Tested:</span>
</td>
<td>
<span id="NBS669"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS669)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Other Pathogen Tested Result" id="NBS668L" >
Other Pathogens Tested:</span></td><td>
<span id="NBS668" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS668)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
