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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Clinical Manifestation","Complications","Travel","Tick Bite","Treatment","Blood Transfusion","Blood Donation","Organ Transplant","Laboratory","Contact Investigation"};
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
<nedss:container id="NBS_UI_32_2" name="Occupation Exposure" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient's exposure related to the current occupation?" id="INV1266_2L" >
Is exposure related to current occupation?:</span></td><td>
<span id="INV1266_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1266)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33_2" name="Occupation and Industry Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_33_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_33_2">
<tr id="patternNBS_UI_33_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_33_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_33_2">
<tr id="nopatternNBS_UI_33_2" class="odd" style="display:none">
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
Current Industry Standardized:</span></td><td>
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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169_2L" title="Condition_Cd should always be a hidden or read-only field.">Hidden Condition:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV169)" styleId="INV169_2"><html:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select> </td></tr>
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
<nedss:container id="NBS_UI_36_2" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/ehrlichiosis-and-anaplasmosis/" TARGET="_blank">Click here for the current Anaplasmosis OR Ehrlichiosis case definitions</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/spotted-fever-rickettsiosis/" TARGET="_blank">Click here for the current Spotted Fever Rickettsiosis case definition</a></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient symptomatic for the illness of interest?" id="INV576_2L" >
Was patient symptomatic?:</span></td><td>
<span id="INV576_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fever?" id="386661006_2L" >
Fever:</span></td><td>
<span id="386661006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have anemia?" id="271737000_2L" >
Anemia:</span></td><td>
<span id="271737000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(271737000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have Eschar?" id="87319000_2L" >
Eschar:</span></td><td>
<span id="87319000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(87319000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have headache?" id="25064002_2L" >
Headache:</span></td><td>
<span id="25064002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have Hepatic transaminase elevation?" id="PHC2108_2L" >
Hepatic transaminase elevation:</span></td><td>
<span id="PHC2108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC2108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Leukopenia. This is an interpretation based upon the lab values for White Blood Cell (WBC) count." id="419188005_2L" >
Leukopenia:</span></td><td>
<span id="419188005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(419188005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have myalgia?" id="68962001_2L" >
Myalgia:</span></td><td>
<span id="68962001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have rash?" id="271807003_2L" >
Rash:</span></td><td>
<span id="271807003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(271807003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have thrombocytopenic disorder?" id="302215000_2L" >
Thrombocytopenic:</span></td><td>
<span id="302215000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(302215000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338_2L" >
Other symptom(s):</span></td><td>
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
<nedss:container id="NBS_UI_38_2" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience any severe complications in the clinical course of this illness?" id="INV920_2L" >
Did the patient experience any severe complications?:</span></td><td>
<span id="INV920_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV920)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="67187_5_2L" title="If the subject experienced severe complications due to this illness, specify the complication(s).">
Type of Complication(s):</span></td><td>
<span id="67187_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(67187_5)"
codeSetNm="COMPLICATIONS_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the subject experienced severe complications due to this illness, specify the complication(s)." id="67187_5_2_OthL">Other Type of Complication(s):</span></td>
<td> <span id="67187_5_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(67187_5Oth)"/></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;For the question below, immunocompromised refers to a medical condition or treatment including but not limited to: chemotherapy for current illness, HIV, asplenic, anti-rejection drugs post-transplant, corticosteroids [&gt;14 days, such as prednisone, Medrol, or decadron], rheumatoid arthritis.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="At the time of diagnosis, was the patient immunocompromised due to medical condition(s) or treatment(s)" id="VAR126_2L" >
Patient immunocompromised at time of diagnosis:</span></td><td>
<span id="VAR126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAR126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="If the patient is immunocompromised, what is the associated condition or treatment?" id="VAR127_2L">
Immunocompromised associated condition or treatment:</span>
</td>
<td>
<span id="VAR127_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAR127)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40_2" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="In the 2 weeks before symptom onset or diagnosis (whichever is earlier) did the patient travel out of their county, state, or country of residence?" id="TRAVEL02_2L" >
Did patient travel out of their country, state, or country of residence prior to illness onset?:</span></td><td>
<span id="TRAVEL02_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL02)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_41_2" name="Travel History Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_41_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_41_2">
<tr id="patternNBS_UI_41_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_41_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_41_2">
<tr id="nopatternNBS_UI_41_2" class="odd" style="display:none">
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
<span title="Which intrastate counties did the patient travel to?" id="82753_5_2L" >
County of Travel:</span></td><td>
<span id="82753_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(82753_5)"
codeSetNm="PHVS_COUNTY_FIPS_6-4"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject traveled, when did they arrive to their travel destination?" id="TRAVEL06_2L">Date of Arrival at Destination:</span>
</td><td>
<span id="TRAVEL06_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL06)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the subject traveled, when did they depart from their travel destination?" id="TRAVEL07_2L">Date of Departure from Destination:</span>
</td><td>
<span id="TRAVEL07_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL07)"  />
</td></tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Choose the mode of travel" id="NBS453_2L" >
Travel Mode:</span></td><td>
<span id="NBS453_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC"/>
</td> </tr>

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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43_2" name="Tick Bite" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="In the eight weeks before symptom onset or diagnosis (use earlier date), did the patient notice any tick bites?" id="95898004_2L" >
In the eight weeks before symptom onset or diagnosis, did the patient notice any tick bites?:</span></td><td>
<span id="95898004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(95898004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44_2" name="Tick Bite Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_44_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_44_2">
<tr id="patternNBS_UI_44_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_44_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_44_2">
<tr id="nopatternNBS_UI_44_2" class="odd" style="display:none">
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
<span title="Geographic location when tick was removed" id="82755_0_2L">
Geographic location when tick was removed:</span>
</td>
<td>
<span id="82755_0_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(82755_0)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of tick removal" id="45347_2_2L">Date of tick removal:</span>
</td><td>
<span id="45347_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(45347_2)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_46_2" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Were antibiotics prescribed to the patient for this infection?" id="255633001_2L" >
Were antibiotics prescribed?:</span></td><td>
<span id="255633001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(255633001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47_2" name="Antibiotics Treatment" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_47_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_47_2">
<tr id="patternNBS_UI_47_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_47_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_47_2">
<tr id="nopatternNBS_UI_47_2" class="odd" style="display:none">
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
<span title="What antibiotic was prescribed to the patient for this infection?" id="29303_5_2L" >
Antibiotic Prescribed:</span></td><td>
<span id="29303_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(29303_5)"
codeSetNm="MEDICATION_RECEIVED_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What antibiotic was prescribed to the patient for this infection?" id="29303_5_2_OthL">Other Antibiotic Prescribed:</span></td>
<td> <span id="29303_5_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(29303_5Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment/medication was prescribed" id="INV1300_2L">Date Antibiotic Prescribed:</span>
</td><td>
<span id="INV1300_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1300)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="67453_1_2L" title="Prescribed duration (in days) of antibiotic treatment">
Treatment Duration (in days):</span>
</td><td>
<span id="67453_1_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(67453_1)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_49_2" name="Blood Transfusion" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the subject received a blood transfusiont in the 12 months prior to this illness?" id="82312_0_2L" >
Has the subject received a blood transfusion in the 12 months prior to this illness?:</span></td><td>
<span id="82312_0_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(82312_0)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patients infection transfusion associated?" id="418912005_2L" >
Was the patients infection transfusion associated?:</span></td><td>
<span id="418912005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(418912005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="933_2_2L" title="If a transfused blood product was implicated in an investigation, specify which type(s) of product.">
Type of transfused product implicated:</span></td><td>
<span id="933_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(933_2)"
codeSetNm="BLOOD_PRODUCT_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If a transfused blood product was implicated in an investigation, specify which type(s) of product." id="933_2_2_OthL">Other Type of transfused product implicated:</span></td>
<td> <span id="933_2_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(933_2Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_50_2" name="Blood Transfusion Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_50_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_50_2">
<tr id="patternNBS_UI_50_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_50_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_50_2">
<tr id="nopatternNBS_UI_50_2" class="odd" style="display:none">
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
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the patient blood transfusion(s) dates</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Blood Transfusion Date" id="14687_8_2L">Blood Transfusion Date:</span>
</td><td>
<span id="14687_8_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(14687_8)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_60_2" name="Blood Donation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient a blood donor identified during a transfusion investigation (i.e., had positive test results and was linked to an infected recipient)?" id="INV617_2L" >
Was the patient a blood donor identified during a transfusion investigation?:</span></td><td>
<span id="INV617_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV617)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV964_2L" title="If a donated blood product was implicated in an investigation, specify which types of product">
Type of donated blood product implicated:</span></td><td>
<span id="INV964_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV964)"
codeSetNm="BLOOD_PRODUCT_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If a donated blood product was implicated in an investigation, specify which types of product" id="INV964_2_OthL">Other Type of donated blood product implicated:</span></td>
<td> <span id="INV964_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV964Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient donate blood in the 30 days prior to symptom onset?" id="ARB005_2L" >
Did the patient donate blood in the 30 days prior to symptom onset?:</span></td><td>
<span id="ARB005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the blood bank/hospital/transplant service notified?" id="INV1268_2L" >
Blood Bank Notified:</span></td><td>
<span id="INV1268_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1268)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53_2" name="Blood Donation Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_53_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_53_2">
<tr id="patternNBS_UI_53_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_53_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_53_2">
<tr id="nopatternNBS_UI_53_2" class="odd" style="display:none">
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
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the year before symptom onset or diagnosis, list the patient blood donation(s) dates</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date(s) of blood donation(s)" id="82756_8_2L">Blood Donation Date:</span>
</td><td>
<span id="82756_8_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(82756_8)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_62_2" name="Organ Transplant" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="In the year before symptom onset or diagnosis (use earlier date), did the subject receive an organ transplant(s)?" id="ARB008_2L" >
In the year before symptom onset of diagnosis, did the subject receive an organ transplant(s)?:</span></td><td>
<span id="ARB008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_55_2" name="Organ Transplant Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_55_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_55_2">
<tr id="patternNBS_UI_55_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_55_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_55_2">
<tr id="nopatternNBS_UI_55_2" class="odd" style="display:none">
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
<span title="What organ was transplanted?" id="ARB028_TXT_2L">
Organ(s) Transplanted:</span>
</td>
<td>
<span id="ARB028_TXT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB028_TXT)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date(s) of organ transplant(s)" id="80989_7_2L">Organ Transplant Date:</span>
</td><td>
<span id="80989_7_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(80989_7)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the subjects infection transplant-related?" id="INV1267_2L" >
Transplant Associated Infection:</span></td><td>
<span id="INV1267_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1267)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57_2" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740_2L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/ehrlichiosis-and-anaplasmosis/" TARGET="_blank">Click here for the current Anaplasmosis OR Ehrlichiosis case definitions</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://wwwn.cdc.gov/nndss/conditions/spotted-fever-rickettsiosis/" TARGET="_blank">Click here for the current Spotted Fever Rickettsiosis case definition</a></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_58_2" name="Laboratory Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_58_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_58_2">
<tr id="patternNBS_UI_58_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_58_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_58_2">
<tr id="nopatternNBS_UI_58_2" class="odd" style="display:none">
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
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="LAB_TEST_TYPE_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290_2_OthL">Other Test Type:</span></td>
<td> <span id="INV290_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g., positive, detected, negative)" id="INV291_2L" >
Test Result:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="LAB_TEST_INTERPRETATION_LYME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Species identified through testing" id="LAB278_2L" >
Organism Name:</span></td><td>
<span id="LAB278_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB278)"
codeSetNm="ORGANISM_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Species identified through testing" id="LAB278_2_OthL">Other Organism Name:</span></td>
<td> <span id="LAB278_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB278Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628_2L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB628)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report." id="LAB163_2L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB163)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="This indicates the type of specimen tested." id="667469_2L" >
Specimen Type:</span></td><td>
<span id="667469_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(667469)"
codeSetNm="SPECIMEN_TYPE_TBRD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="This indicates the type of specimen tested." id="667469_2_OthL">Other Specimen Type:</span></td>
<td> <span id="667469_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(667469Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Performing Laboratory Name" id="68994_3_2L">
Performing Laboratory Name:</span>
</td>
<td>
<span id="68994_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(68994_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="State where laboratory is located" id="INV1269_2L" >
Laboratory State:</span></td><td>
<span id="INV1269_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV1269)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specimen identifier that will be used to uniquely identify the lab message" id="LAB125_2L">
Performing Laboratory Specimen ID:</span>
</td>
<td>
<span id="LAB125_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB125)" />
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
</div> </td></tr>
