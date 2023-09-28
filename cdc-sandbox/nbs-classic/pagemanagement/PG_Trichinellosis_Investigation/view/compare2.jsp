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
Map map2 = new HashMap();
if(request.getAttribute("SubSecStructureMap2") != null){
// String watemplateUid="1000879";
// map2 = util.getBatchMap(new Long(watemplateUid));
map2 =(Map)request.getAttribute("SubSecStructureMap2");
}%>
<%
String tabId = "view";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Signs and Symptoms","Laboratory","Exposure","Contact Investigation","Retired Data Elements"};
;
%>
<tr><td>
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare2.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6_2" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="As of Date is the last known date for which the information is valid." id="NBS104_2L">Information As of Date:</span>
</td><td>
<span id="NBS104_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS104)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments pertaining to the patient." id="DEM196_2L">
Comments:</span>
</td>
<td>
<span id="DEM196_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM196)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7_2" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS095_2L">Name Information As Of Date:</span>
</td><td>
<span id="NBS095_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS095)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's first name." id="DEM104_2L">
First Name:</span>
</td>
<td>
<span id="DEM104_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM104)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's middle name or initial." id="DEM105_2L">
Middle Name:</span>
</td>
<td>
<span id="DEM105_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM105)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's last name." id="DEM102_2L">
Last Name:</span>
</td>
<td>
<span id="DEM102_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM102)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's name suffix" id="DEM107_2L" >
Suffix:</span></td><td>
<span id="DEM107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM107)"
codeSetNm="P_NM_SFX"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8_2" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS096_2L">Other Personal Details As Of Date:</span>
</td><td>
<span id="NBS096_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS096)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Reported date of birth of patient." id="DEM115_2L">Date of Birth:</span>
</td><td>
<span id="DEM115_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM115)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV2001_2L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td><td>
<span id="INV2001_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2001)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's age units" id="INV2002_2L" >
Reported Age Units:</span></td><td>
<span id="INV2002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2002)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of Birth" id="DEM126_2L" >
Country of Birth:</span></td><td>
<span id="DEM126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex." id="DEM113_2L" >
Current Sex:</span></td><td>
<span id="DEM113_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM113)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS097_2L">Mortality Information As Of Date:</span>
</td><td>
<span id="NBS097_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS097)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator of whether or not a patient is alive or dead." id="DEM127_2L" >
Is the patient deceased?:</span></td><td>
<span id="DEM127_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM127)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date on which the individual died." id="DEM128_2L">Deceased Date:</span>
</td><td>
<span id="DEM128_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM128)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098_2L">Marital Status As Of Date:</span>
</td><td>
<span id="NBS098_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS098)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A code indicating the married or similar partnership status of a patient." id="DEM140_2L" >
Marital Status:</span></td><td>
<span id="DEM140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM140)"
codeSetNm="P_MARITAL"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15_2" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS102_2L">Address Information As Of Date:</span>
</td><td>
<span id="NBS102_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS102)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line one of the address label." id="DEM159_2L">
Street Address 1:</span>
</td>
<td>
<span id="DEM159_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM159)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line two of the address label." id="DEM160_2L">
Street Address 2:</span>
</td>
<td>
<span id="DEM160_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The city for a postal location." id="DEM161_2L">
City:</span>
</td>
<td>
<span id="DEM161_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM161)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The state code for a postal location." id="DEM162_2L" >
State:</span></td><td>
<span id="DEM162_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM162)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The zip code of a residence of the case patient or entity." id="DEM163_2L">
Zip:</span>
</td>
<td>
<span id="DEM163_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM163)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient or entity." id="DEM165_2L" >
County:</span></td><td>
<span id="DEM165_2" />
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(DEM165)">
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(DEM162)">
<bean:define id="value" name="PageForm" property="pageClientVO2.answer(DEM162)"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM165)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.DEM165_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The country code for a postal location." id="DEM167_2L" >
Country:</span></td><td>
<span id="DEM167_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16_2" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS103_2L">Telephone Information As Of Date:</span>
</td><td>
<span id="NBS103_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS103)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's home phone number." id="DEM177_2L">
Home Phone:</span>
</td>
<td>
<span id="DEM177_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM177)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's work phone number." id="NBS002_2L">
Work Phone:</span>
</td>
<td>
<span id="NBS002_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS002)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS003_2L" title="The patient's work phone number extension.">
Ext.:</span>
</td><td>
<span id="NBS003_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS003)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's cellular phone number." id="NBS006_2L">
Cell Phone:</span>
</td>
<td>
<span id="NBS006_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS006)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's email address." id="DEM182_2L">
Email:</span>
</td>
<td>
<span id="DEM182_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM182)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9_2" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS100_2L">Ethnicity Information As Of Date:</span>
</td><td>
<span id="NBS100_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS100)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the patient is hispanic or not." id="DEM155_2L" >
Ethnicity:</span></td><td>
<span id="DEM155_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM155)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS101_2L">Race Information As Of Date:</span>
</td><td>
<span id="NBS101_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS101)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152_2L">
Race:</span>
</td>
<td>
<div id="patientRacesViewContainer2">
<logic:equal name="PageForm" property="pageClientVO2.americanIndianAlskanRace" value="1"><bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.africanAmericanRace" value="1"><bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.whiteRace" value="1"><bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.asianRace" value="1"><bean:message bundle="RVCT" key="rvct.asian"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.hawaiianRace" value="1"><bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.otherRace" value="1"><bean:message bundle="RVCT" key="rvct.other"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.refusedToAnswer" value="1"><bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.notAsked" value="1"><bean:message bundle="RVCT" key="rvct.notAsked"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.unKnownRace" value="1"><bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
</div>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32_2" name="Occupation and Industry Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_32_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_32_2">
<tr id="patternNBS_UI_32_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_32_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_UI_32_2">
<tr id="nopatternNBS_UI_32_2" class="odd" style="display:none">
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
<span title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." id="85659_1_2L" >
Current Occupation Standardized:</span></td><td>
<span id="85659_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(85659_1)"
codeSetNm="PHVS_OCCUPATION_CDC_CENSUS2010"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="This data element is used to capture the narrative text of a subjects current occupation." id="85658_3_2L">
Current Occupation:</span>
</td>
<td>
<span id="85658_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(85658_3)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." id="85657_5_2L" >
Current  Industry Standardized:</span></td><td>
<span id="85657_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(85657_5)"
codeSetNm="PHVS_INDUSTRY_CDC_CENSUS2010"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="This data element is used to capture the narrative text of subjects current industry." id="85078_4_2L">
Current Industry:</span>
</td>
<td>
<span id="85078_4_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(85078_4)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19_2" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107_2L" >
Jurisdiction:</span></td><td>
<span id="INV107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108_2L" >
Program Area:</span></td><td>
<span id="INV108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147_2L">Investigation Start Date:</span>
</td><td>
<span id="INV147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV147)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The status of the investigation." id="INV109_2L" >
Investigation Status:</span></td><td>
<span id="INV109_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV109)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="NBS012_2L" title="Should this record be shared with guests with program area and jurisdiction rights?">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO2.answer(NBS012)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO2.answer(NBS012)" value="1">
No</logic:notEqual>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The State ID associated with the case." id="INV173_2L">
State Case ID:</span>
</td>
<td>
<span id="INV173_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV173)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." id="INV200_2L">
Legacy Case ID:</span>
</td>
<td>
<span id="INV200_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV200)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20_2" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180_2L" title="The Public Health Investigator assigned to the Investigation.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV180)"/>
<span id="INV180_2">${PageForm.attributeMap2.INV180SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was assigned/started." id="INV110_2L">Date Assigned to Investigation:</span>
</td><td>
<span id="INV110_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV110)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_22_2" name="Key Report Dates" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of report of the condition to the public health department." id="INV111_2L">Date of Report:</span>
</td><td>
<span id="INV111_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV111)"  />
</td></tr>
<!--skipping Hidden Date Question-->

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to county public health system." id="INV120_2L">Earliest Date Reported to County:</span>
</td><td>
<span id="INV120_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to state public health system." id="INV121_2L">Earliest Date Reported to State:</span>
</td><td>
<span id="INV121_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV121)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23_2" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of facility or provider associated with the source of information sent to Public Health." id="INV112_2L" >
Reporting Source Type:</span></td><td>
<span id="INV112_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV112)"
codeSetNm="PHVS_REPORTINGSOURCETYPE_NND"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV183_2L" title="The organization that reported the case.">
Reporting Organization:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV183)"/>
<span id="INV183_2">${PageForm.attributeMap2.INV183SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10_2" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV181_2L" title="The provider that reported the case.">
Reporting Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV181)"/>
<span id="INV181_2">${PageForm.attributeMap2.INV181SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_1_2" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="County reporting the notification." id="NOT113_2L" >
Reporting County:</span></td><td>
<span id="NOT113_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT113)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.NOT113_STATE}"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12_2" name="Physician" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182_2L" title="The physician associated with this case.">
Physician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV182)"/>
<span id="INV182_2">${PageForm.attributeMap2.INV182SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient hospitalized as a result of this event?" id="INV128_2L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184_2L" title="The hospital associated with the investigation.">
Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV184)"/>
<span id="INV184_2">${PageForm.attributeMap2.INV184SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132_2L">Admission Date:</span>
</td><td>
<span id="INV132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133_2L">Discharge Date:</span>
</td><td>
<span id="INV133_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV133)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV134_2L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td><td>
<span id="INV134_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136_2L">Diagnosis Date:</span>
</td><td>
<span id="INV136_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV136)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137_2L">Illness Onset Date:</span>
</td><td>
<span id="INV137_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138_2L">Illness End Date:</span>
</td><td>
<span id="INV138_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV138)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV139_2L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td><td>
<span id="INV139_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV139)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit of time used to describe the length of the illness or condition." id="INV140_2L" >
Illness Duration Units:</span></td><td>
<span id="INV140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV140)"
codeSetNm="PHVS_DURATIONUNIT_CDC"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV143_2L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td><td>
<span id="INV143_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV143)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The age units for an age." id="INV144_2L" >
Age at Onset Units:</span></td><td>
<span id="INV144_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV144)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assesses whether or not the patient is pregnant." id="INV178_2L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject in pregnant, please enter the due date." id="INV579_2L">Due Date:</span>
</td><td>
<span id="INV579_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV579)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the subject dies as a result of the illness." id="INV145_2L" >
Did the patient die from this illness?:</span></td><td>
<span id="INV145_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV145)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the subject’s death occurred." id="INV146_2L">Date of Death:</span>
</td><td>
<span id="INV146_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV146)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25_2" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is this case epi-linked to another confirmed or probable case?" id="INV217_2L" >
Is this case epi-linked to another confirmed or probable case?:</span></td><td>
<span id="INV217_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV217)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility." id="INV148_2L" >
Is this person associated with a day care facility?:</span></td><td>
<span id="INV148_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was food handler." id="INV149_2L" >
Is this person a food handler?:</span></td><td>
<span id="INV149_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV149)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Denotes whether the reported case was associated with an identified outbreak." id="INV150_2L" >
Is this case part of an outbreak?:</span></td><td>
<span id="INV150_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV150)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation." id="INV151_2L" >
Outbreak Name:</span></td><td>
<span id="INV151_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV151)"
codeSetNm="OUTBREAK_NM"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1_2" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of where the disease/condition was likely acquired." id="INV152_2L" >
Where was the disease acquired?:</span></td><td>
<span id="INV152_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV152)"
codeSetNm="PHVS_DISEASEACQUIREDJURISDICTION_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the country in which the disease was likely acquired." id="INV153_2L" >
Imported Country:</span></td><td>
<span id="INV153_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV153)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." id="INV154_2L" >
Imported State:</span></td><td>
<span id="INV154_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV154)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." id="INV155_2L">
Imported City:</span>
</td>
<td>
<span id="INV155_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV155)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition." id="INV156_2L" >
Imported County:</span></td><td>
<span id="INV156_2" />
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(INV156)">
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(INV154)">
<bean:define id="value" name="PageForm" property="pageClientVO2.answer(INV154)"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV156)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.INV156_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where does the person usually live (defined as their residence)." id="INV501_2L" >
Country of Usual Residence:</span></td><td>
<span id="INV501_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_4_2" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_GENV2_UI_4_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2">
<tr id="patternNBS_UI_NBS_INV_GENV2_UI_4_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_NBS_INV_GENV2_UI_4_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_UI_NBS_INV_GENV2_UI_4_2">
<tr id="nopatternNBS_UI_NBS_INV_GENV2_UI_4_2" class="odd" style="display:none">
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
<span title="Indicates the country in which the disease was potentially acquired." id="INV502_2L" >
Country of Exposure:</span></td><td>
<span id="INV502_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV502)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the state in which the disease was potentially acquired." id="INV503_2L" >
State or Province of Exposure:</span></td><td>
<span id="INV503_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV503)"
codeSetNm="PHVS_STATEPROVINCEOFEXPOSURE_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicates the city in which the disease was potentially acquired." id="INV504_2L">
City of Exposure:</span>
</td>
<td>
<span id="INV504_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV504)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the county in which the disease was potentially acquired." id="INV505_2L" >
County of Exposure:</span></td><td>
<span id="INV505_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV505)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.INV505_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_5_2" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV515_2L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span></td><td>
<span id="INV515_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV515)"
codeSetNm="PHVS_BINATIONALREPORTINGCRITERIA_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate." id="INV157_2L" >
Transmission Mode:</span></td><td>
<span id="INV157_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV157)"
codeSetNm="PHC_TRAN_M"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc." id="INV159_2L" >
Detection Method:</span></td><td>
<span id="INV159_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV159)"
codeSetNm="PHC_DET_MT"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV161_2L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span></td><td>
<span id="INV161_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV161)"
codeSetNm="PHC_CONF_M"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If an investigation is confirmed as a case, then the confirmation date is entered." id="INV162_2L">Confirmation Date:</span>
</td><td>
<span id="INV162_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV162)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The current status of the investigation/case." id="INV163_2L" >
Case Status:</span></td><td>
<span id="INV163_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV163)"
codeSetNm="PHC_CLASS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR week in which the case should be counted." id="INV165_2L">
MMWR Week:</span>
</td>
<td>
<span id="INV165_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV165)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR year in which the case should be counted." id="INV166_2L">
MMWR Year:</span>
</td>
<td>
<span id="INV166_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV166)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" id="NOT120_2L" >
Immediate National Notifiable Condition:</span></td><td>
<span id="NOT120_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition." id="NOT120SPEC_2L">
If yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176_2L">Date CDC Was First Verbally Notified of This Case:</span>
</td><td>
<span id="INV176_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV176)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data." id="INV886_2L">
Notification Comments to CDC:</span>
</td>
<td>
<span id="INV886_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV886)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27_2" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Field which contains general comments for the investigation." id="INV167_2L">
General Comments:</span>
</td>
<td>
<span id="INV167_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV167)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35_2" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have eosinophilia?" id="386789004_2L" >
Eosinophilia:</span></td><td>
<span id="386789004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386789004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a fever?" id="386661006_2L" >
Fever:</span></td><td>
<span id="386661006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV202_2L" title="What was the patient's highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV202)"  />
<span id="INV202_2_UNIT" /><nedss:view name="PageForm" property="pageClientVO2.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have myalgia or muscle pain?" id="68962001_2L" >
Myalgia/Muscle Pain:</span></td><td>
<span id="68962001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have periorbital edema?" id="49563000_2L" >
Periorbital Edema:</span></td><td>
<span id="49563000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(49563000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338_2L" >
Other Symptom(s):</span></td><td>
<span id="NBS338_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other signs and symptoms." id="NBS338_OTH_2L">
Specify Other Symptom(s):</span>
</td>
<td>
<span id="NBS338_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS338_OTH)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42_2" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740_2L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43_2" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_43_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_43_2">
<tr id="patternNBS_UI_43_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_43_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_UI_43_2">
<tr id="nopatternNBS_UI_43_2" class="odd" style="display:none">
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
<span style="color:#CC0000">*</span>
<span title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290_2_OthL">Other Test Type:</span></td>
<td> <span id="INV290_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)" id="INV291_2L" >
Test Result Qualitative:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_TRICH"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628_2L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="This indicates the type of specimen tested" id="667469_2L" >
Specimen Type:</span></td><td>
<span id="667469_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(667469)"
codeSetNm="PHVS_SPECIMENTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="This indicates the type of specimen tested" id="667469_2_OthL">Other Specimen Type:</span></td>
<td> <span id="667469_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(667469Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date" id="LAB163_2L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB163)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date." id="LAB217_2L">Specimen Analyzed Date:</span>
</td><td>
<span id="LAB217_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606_2L" >
Performing Laboratory Type:</span></td><td>
<span id="LAB606_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606_2_OthL">Other Performing Laboratory Type:</span></td>
<td> <span id="LAB606_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB606Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the specimen was sent for strain identification, indicate the strain" id="NBS212_2L" >
Strain Type:</span></td><td>
<span id="NBS212_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS212)"
codeSetNm="PHVS_STRAINTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the specimen was sent for strain identification, indicate the strain" id="NBS212_2_OthL">Other Strain Type:</span></td>
<td> <span id="NBS212_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS212Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44_2" name="Eosinophilia Lab" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was testing performed for eosinophilia?" id="LAB711_2L" >
Was testing performed for eosinophilia?:</span></td><td>
<span id="LAB711_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB711)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="LAB665_2L" title="Eosinophil value (absolute number or percentage)">
Eosinophil value (absolute number or percentage):</span>
</td><td>
<span id="LAB665_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB665)"  />
<span id="LAB665_2_UNIT" /><nedss:view name="PageForm" property="pageClientVO2.answer(LAB665Unit)" codeSetNm="PHVS_EOSINOPHILUNITS_CDC_TRICH" />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37_2" name="Food Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 8 WEEKS before symptom onset/diagnosis:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient consume meat suspected of making the patient ill?" id="NBS406_2L" >
Did the patient consume meat item(s) suspected of making the patient ill?:</span></td><td>
<span id="NBS406_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS406)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_38_2" name="Food Exposure History" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_38_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_38_2">
<tr id="patternNBS_UI_38_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_38_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_UI_38_2">
<tr id="nopatternNBS_UI_38_2" class="odd" style="display:none">
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
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Specify all the information for suspected meat consumed:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009_2L" >
Meat Type:</span></td><td>
<span id="INV1009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1009)"
codeSetNm="PHVS_MEATCONSUMEDTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009_2_OthL">Other Meat Type:</span></td>
<td> <span id="INV1009_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV1009Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Other Meat Type" id="NBS528_2L">
(Legacy) Other Meat Type:</span>
</td>
<td>
<span id="NBS528_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS528)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date suspected meat was eaten" id="INV967_2L">Consumed Date:</span>
</td><td>
<span id="INV967_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV967)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where was the suspected meat obtained?" id="INV969_2L" >
Meat Obtained:</span></td><td>
<span id="INV969_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV969)"
codeSetNm="PHVS_MEATPURCHASEINFO_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Where was the suspected meat obtained?" id="INV969_2_OthL">Other Meat Obtained:</span></td>
<td> <span id="INV969_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV969Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Meat preparation (further food processing)" id="INV968_2L" >
Method of Meat Preparation:</span></td><td>
<span id="INV968_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV968)"
codeSetNm="PHVS_FOODPROCESSINGMETHOD_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Meat preparation (further food processing)" id="INV968_2_OthL">Other Method of Meat Preparation:</span></td>
<td> <span id="INV968_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV968Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What method of cooking was used on suspected meat?" id="INV970_2L" >
Method of Cooking:</span></td><td>
<span id="INV970_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV970)"
codeSetNm="PHVS_FOODCOOKINGMETHOD_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What method of cooking was used on suspected meat?" id="INV970_2_OthL">Other Method of Cooking:</span></td>
<td> <span id="INV970_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV970Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was larva observed in suspected meat by microscopy?" id="INV971_2L" >
Larva Present in Meat:</span></td><td>
<span id="INV971_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV971)"
codeSetNm="PHVS_PRESENTABSENTUNKNOTEXAMINED_CDC_TRICH"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where was the suspected meat tested?" id="INV972_2L" >
Where was the Meat Tested:</span></td><td>
<span id="INV972_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV972)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_TRICH"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Where was the suspected meat tested?" id="INV972_2_OthL">Other Where was the Meat Tested:</span></td>
<td> <span id="INV972_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV972Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)" id="INV973_2L">
Suspect Meat Comments:</span>
</td>
<td>
<span id="INV973_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV973)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39_2" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?" id="TRAVEL02_2L" >
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span></td><td>
<span id="TRAVEL02_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL02)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40_2" name="Travel History Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_40_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_40_2">
<tr id="patternNBS_UI_40_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_40_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_UI_40_2">
<tr id="nopatternNBS_UI_40_2" class="odd" style="display:none">
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
<span style="color:#CC0000">*</span>
<span title="Domestic vs International Travel" id="NBS454_2L" >
Was the travel domestic or international?:</span></td><td>
<span id="NBS454_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS454)"
codeSetNm="PHVSFB_DOMINTNL"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="List any international destinations of recent travel" id="TRAVEL05_2L" >
Country of Travel:</span></td><td>
<span id="TRAVEL05_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL05)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Domestic destination, states traveled" id="82754_3_2L" >
State of Travel:</span></td><td>
<span id="82754_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(82754_3)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Intrastate destination, counties traveled" id="82753_5_2L" >
County of Travel:</span></td><td>
<span id="82753_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(82753_5)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Choose the mode of travel" id="NBS453_2L" >
Travel Mode:</span></td><td>
<span id="NBS453_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Principal Reason for Travel" id="TRAVEL16_2L" >
Principal Reason for Travel:</span></td><td>
<span id="TRAVEL16_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL16)"
codeSetNm="PHVS_TRAVELREASON_MALARIA"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Principal Reason for Travel" id="TRAVEL16_2_OthL">Other Principal Reason for Travel:</span></td>
<td> <span id="TRAVEL16_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL16Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the patient traveled, when did they arrive to their travel destination?" id="TRAVEL06_2L">Date of Arrival at Destination:</span>
</td><td>
<span id="TRAVEL06_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL06)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the patient traveled, when did they depart from their travel destination?" id="TRAVEL07_2L">Date of Departure from Destination:</span>
</td><td>
<span id="TRAVEL07_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL07)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="82310_4_2L" title="Duration of Stay">
Duration of Stay:</span>
</td><td>
<span id="82310_4_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(82310_4)"  />
<span id="82310_4_2_UNIT" /><nedss:view name="PageForm" property="pageClientVO2.answer(82310_4Unit)" codeSetNm="PHVS_DURATIONUNIT_CDC_1" />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Additional Travel Information" id="TRAVEL23_2L">
Additional Travel Information:</span>
</td>
<td>
<span id="TRAVEL23_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL23)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28_2" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055_2L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056_2L">Infectious Period From:</span>
</td><td>
<span id="NBS056_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057_2L">Infectious Period To:</span>
</td><td>
<span id="NBS057_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29_2" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The status of the contact investigation." id="NBS058_2L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059_2L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101000_2" name="Legacy Animal Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the case have contact with an animal?" id="INV667_2L" >
(Legacy) Did the case have contact with an animal?:</span></td><td>
<span id="INV667_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV667)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_32_2L" title="Animal contact by type of animal">
(Legacy) Animal Type:</span></td><td>
<span id="FDD_Q_32_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_32)"
codeSetNm="PHVSFB_ANIMALST"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other, please specify other type of animal" id="FDD_Q_243_2L">
(Legacy) If Other, please specify other type of animal:</span>
</td>
<td>
<span id="FDD_Q_243_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_243)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Amphibian, please specify other type of amphibian" id="FDD_Q_295_2L">
(Legacy) If Other Amphibian, please specify other type of amphibian:</span>
</td>
<td>
<span id="FDD_Q_295_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_295)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Reptile, please specify other type of reptile" id="FDD_Q_296_2L">
(Legacy) If Other Reptile, please specify other type of reptile:</span>
</td>
<td>
<span id="FDD_Q_296_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_296)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Mammal, please specify other type of mammal" id="FDD_Q_374_2L">
(Legacy) If Other Mammal, please specify other type of mammal:</span>
</td>
<td>
<span id="FDD_Q_374_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_374)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?" id="FDD_Q_33_2L">
(Legacy) Animal Contact Location:</span>
</td>
<td>
<span id="FDD_Q_33_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_33)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient acquire a pet prior to onset of illness?" id="FDD_Q_34_2L" >
(Legacy) Did the patient acquire a pet prior to onset of illness?:</span></td><td>
<span id="FDD_Q_34_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_34)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Applicable incubation period for this illness" id="FDD_Q_244_2L">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<span id="FDD_Q_244_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_244)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101001_2" name="Legacy Underlying Conditions" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_233_2L" title="Did patient have any of the following underlying conditions?">
(Legacy) Did patient have any of the following underlying conditions?:</span></td><td>
<span id="FDD_Q_233_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_233)"
codeSetNm="PHVSFB_DISEASES"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Prior Illness, please specify" id="FDD_Q_234_2L">
(Legacy) If Other Prior Illness, please specify:</span>
</td>
<td>
<span id="FDD_Q_234_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_234)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If Diabetes Mellitus, specify whether on insulin" id="FDD_Q_235_2L" >
(Legacy) If Diabetes Mellitus, specify whether on insulin:</span></td><td>
<span id="FDD_Q_235_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_235)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Gastric Surgery, please specify type" id="FDD_Q_237_2L">
(Legacy) If Gastric Surgery, please specify type:</span>
</td>
<td>
<span id="FDD_Q_237_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_237)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Hematologic Disease, please specify type" id="FDD_Q_238_2L">
(Legacy) If Hematologic Disease, please specify type:</span>
</td>
<td>
<span id="FDD_Q_238_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_238)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Immunodeficiency, please specify type:" id="FDD_Q_239_2L">
(Legacy) If Immunodeficiency, please specify type::</span>
</td>
<td>
<span id="FDD_Q_239_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_239)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Liver Disease, please specify type" id="FDD_Q_240_2L">
(Legacy) If Other Liver Disease, please specify type:</span>
</td>
<td>
<span id="FDD_Q_240_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_240)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Malignancy, please specify type" id="FDD_Q_241_2L">
(Legacy) If Other Malignancy, please specify type:</span>
</td>
<td>
<span id="FDD_Q_241_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_241)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other Renal Disease, please specify" id="FDD_Q_242_2L">
(Legacy) If Other Renal Disease, please specify:</span>
</td>
<td>
<span id="FDD_Q_242_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_242)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Organ Transplant, please specify organ" id="FDD_Q_236_2L">
(Legacy) If Organ Transplant, please specify organ:</span>
</td>
<td>
<span id="FDD_Q_236_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_236)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101002_2" name="Legacy Related Cases" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient know of other similarly ill persons?" id="FDD_Q_77_2L" >
(Legacy) Does the patient know of other similarly ill persons?:</span></td><td>
<span id="FDD_Q_77_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_77)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?" id="FDD_Q_78_2L" >
(Legacy) If Yes, did the health department collect contact information about other similarly ill persons and:</span></td><td>
<span id="FDD_Q_78_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_78)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Are there other cases related to this one?" id="FDD_Q_79_2L" >
(Legacy) Are there other cases related to this one?:</span></td><td>
<span id="FDD_Q_79_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_79)"
codeSetNm="PHVSFB_EPIDEMGY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102002_2" name="Legacy Suspect Food" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_139_2L" title="What suspect foods did the patient eat?">
(Legacy) What suspect foods did the patient eat?:</span></td><td>
<span id="FDD_Q_139_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_139)"
codeSetNm="PHVSFB_PORKONOT"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_144_2L" title="Where was the suspect (Pork) meat obtained?">
(Legacy) Where was the suspect (Pork) meat obtained?:</span></td><td>
<span id="FDD_Q_144_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_144)"
codeSetNm="PHVSFB_SOURCEMT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Where was the suspect (Pork) meat obtained?" id="FDD_Q_144_2_OthL">Other (Legacy) Where was the suspect (Pork) meat obtained?:</span></td>
<td> <span id="FDD_Q_144_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_144Oth)"/></td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_154_2L" title="Where was the suspect (Non-Pork) meat obtained?">
(Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td><td>
<span id="FDD_Q_154_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_154)"
codeSetNm="PHVSFB_SOURCEMT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Where was the suspect (Non-Pork) meat obtained?" id="FDD_Q_154_2_OthL">Other (Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td>
<td> <span id="FDD_Q_154_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_154Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102000_2" name="Legacy Food Handler" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did patient work as a food handler after onset of illness?" id="FDD_Q_8_2L" >
(Legacy) Did patient work as a food handler after onset of illness?:</span></td><td>
<span id="FDD_Q_8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_8)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="What was last date worked as a food handler after onset of illness?" id="FDD_Q_9_2L">(Legacy) What was last date worked as a food handler after onset of illness?:</span>
</td><td>
<span id="FDD_Q_9_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_9)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Where was patient a food handler?" id="FDD_Q_10_2L">
(Legacy) Where was patient a food handler?:</span>
</td>
<td>
<span id="FDD_Q_10_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_10)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102001_2" name="Legacy Travel History" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Applicable incubation period for this illness" id="FDD_Q_12_2L">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<span id="FDD_Q_12_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_12)" />
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_13_2L" title="What was the purpose of the travel?">
(Legacy) What was the purpose of the travel?:</span></td><td>
<span id="FDD_Q_13_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_13)"
codeSetNm="PHVSFB_TRAVELTT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If Other, please specify other purpose of travel" id="FDD_Q_14_2L">
(Legacy) If Other, please specify other purpose of travel:</span>
</td>
<td>
<span id="FDD_Q_14_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_14)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If more than 3 destinations, specify details here" id="FDD_Q_20_2L">
(Legacy) If more than 3 destinations, specify details here:</span>
</td>
<td>
<span id="FDD_Q_20_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_20)" />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
