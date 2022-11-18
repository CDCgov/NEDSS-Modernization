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
String [] sectionNames  = {"Patient Information","Occupation and Industry","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Place of Residence","Healthcare Worker Information","Exposure Information","Information Source","Signs &amp; Symptoms","Medical History","Vaccination","Respiratory Diagnostic Testing","COVID-19 Laboratory Findings","Contact Investigation","Retired Questions"};
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
<nedss:container id="NBS_UI_GA35007_2" name="Language" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What is the patient primary language? Please indicate for both hospitalized and not hospitalized cases." id="DEM142_2L" >
Primary Language:</span></td><td>
<span id="DEM142_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM142)"
codeSetNm="LANGUAGE"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA24000_2" name="Tribal Affiliation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have any indian tribal affiliation?" id="NBS681_2L" >
Does this case have any tribal affiliation?:</span></td><td>
<span id="NBS681_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS681)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="95370_3_2L" title="If tribal affiliation, which tribe(s)?">
Tribal Name:</span></td><td>
<span id="95370_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(95370_3)"
codeSetNm="PHVS_TRIBENAME_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If tribal affiliation, which tribe(s)?" id="95370_3_2_OthL">Other Tribal Name:</span></td>
<td> <span id="95370_3_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(95370_3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient an enrolled tribal member?" id="NBS682_2L" >
Enrolled Tribal Member?:</span></td><td>
<span id="NBS682_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS682)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS779_2L" title="List enrolled tribe(s).">
Enrolled Tribe Name:</span></td><td>
<span id="NBS779_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS779)"
codeSetNm="PHVS_TRIBENAME_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="List enrolled tribe(s)." id="NBS779_2_OthL">Other Enrolled Tribe Name:</span></td>
<td> <span id="NBS779_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS779Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35001_2" name="Occupation and Industry Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35001_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA35001_2">
<tr id="patternNBS_UI_GA35001_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA35001_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA35001_2">
<tr id="nopatternNBS_UI_GA35001_2" class="odd" style="display:none">
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
NNDSS Local Record ID (NETSS):</span>
</td>
<td>
<span id="INV200_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV200)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21015_2" name="COVID-19 Case Details" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC-Assigned Case ID" id="NBS547_2L">
CDC 2019-nCoV ID:</span>
</td>
<td>
<span id="NBS547_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS547)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What is the current processing status of this person?" id="NBS548_2L" >
What is the current status of this person?:</span></td><td>
<span id="NBS548_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS548)"
codeSetNm="PATIENT_STATUS_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If probable, select reason for case classification" id="NBS678_2L" >
If probable, select reason for case classification:</span></td><td>
<span id="NBS678_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS678)"
codeSetNm="CASE_CLASS_REASON_COVID"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS551_2L" title="Under what process was the PUI or case first identified?">
Under what process was the case first identified?:</span></td><td>
<span id="NBS551_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS551)"
codeSetNm="CASE_IDENTIFY_PROCESS_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Under what process was the PUI or case first identified?" id="NBS551_2_OthL">Other Under what process was the case first identified?:</span></td>
<td> <span id="NBS551_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS551Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the PUI or case was first identified through and EpiX notification of travelers, enter the Division of Global Migration and Quarantine ID." id="NBS552_2L">
DGMQ ID:</span>
</td>
<td>
<span id="NBS552_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS552)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of first positive specimen collection" id="NBS550_2L">Date of first positive specimen collection:</span>
</td><td>
<span id="NBS550_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS550)"  />
</td></tr>
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient need or want an interpreter to communicate with doctor or healthcare staff?" id="54588_9_2L" >
If hospitalized, was a translator required?:</span></td><td>
<span id="54588_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(54588_9)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient admitted to an intensive care unit (ICU)?" id="309904001_2L" >
Was the patient admitted to an intensive care unit (ICU)?:</span></td><td>
<span id="309904001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(309904001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of ICU admission." id="NBS679_2L">ICU Admission Date:</span>
</td><td>
<span id="NBS679_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS679)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of ICU discharge." id="NBS680_2L">ICU Discharge Date:</span>
</td><td>
<span id="NBS680_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS680)"  />
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the date of death is unknown, select yes." id="NBS545_2L" >
Unknown Date of Death:</span></td><td>
<span id="NBS545_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS545)"
codeSetNm="YN"/>
</td> </tr>
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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV159_2L" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">Detection Method:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV159)" styleId="INV159_2"><html:optionsCollection property="codedValue(PHC_DET_MT)" value="key" label="value" /> </html:select> </td></tr>

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
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPEC_2L">
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
<nedss:container id="NBS_UI_GA24002_2" name="Place of Residence" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc." id="95421_4_2L" >
Is the patient a resident in a congregate care/living setting?:</span></td><td>
<span id="95421_4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(95421_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202_2L" >
Which would best describe where the patient was staying at the time of illness onset?:</span></td><td>
<span id="NBS202_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202_2_OthL">Other Which would best describe where the patient was staying at the time of illness onset?:</span></td>
<td> <span id="NBS202_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS202Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25005_2" name="Healthcare Worker Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a health care worker in the United States?" id="NBS540_2L" >
Was patient healthcare personnel (HCP) at illness onset?:</span></td><td>
<span id="NBS540_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS540)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004_2L" >
If yes, what is their occupation (type of job)?:</span></td><td>
<span id="14679004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(14679004)"
codeSetNm="HCW_OCCUPATION_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004_2_OthL">Other If yes, what is their occupation (type of job)?:</span></td>
<td> <span id="14679004_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(14679004Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683_2L" >
If yes, what is their job setting?:</span></td><td>
<span id="NBS683_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS683)"
codeSetNm="HCW_SETTING_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="If the patient is a healthcare worker, what is their job setting?" id="NBS683_2_OthL">Other If yes, what is their job setting?:</span></td>
<td> <span id="NBS683_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS683Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25007_2" name="Travel Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the case patient travel domestically within program specific time frame?" id="INV664_2L" >
Domestic travel (outside normal state of residence):</span></td><td>
<span id="INV664_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the case patient travel internationally?" id="TRAVEL38_2L" >
International Travel:</span></td><td>
<span id="TRAVEL38_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(TRAVEL38)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?" id="473085002_2L" >
Cruise ship or vessel travel as passenger or crew member:</span></td><td>
<span id="473085002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(473085002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of cruise ship or vessel." id="NBS690_2L">
Specify Name of Ship or Vessel:</span>
</td>
<td>
<span id="NBS690_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS690)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35002_2" name="Travel Events Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35002_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA35002_2">
<tr id="patternNBS_UI_GA35002_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA35002_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA35002_2">
<tr id="nopatternNBS_UI_GA35002_2" class="odd" style="display:none">
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
<span title="Indicate all countries of travel in the last 14 days." id="TRAVEL05_2L" >
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
<span title="Choose the mode of travel" id="NBS453_2L" >
Travel Mode:</span></td><td>
<span id="NBS453_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS453)"
codeSetNm="PHVS_TRAVELMODE_CDC_COVID19"/>
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

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="82310_4_2L" title="Duration of stay in country outside the US">
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21014_2" name="Other Exposure Events" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient have exposure in the workplace?" id="NBS684_2L" >
Workplace:</span></td><td>
<span id="NBS684_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS684)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?" id="NBS685_2L" >
If yes, is the workplace critical infrastructure (e.g. healthcare setting, grocery store)?:</span></td><td>
<span id="NBS685_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS685)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS686_CD_2L" title="Specify the workplace setting">
If yes, specify workplace setting:</span></td><td>
<span id="NBS686_CD_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS686_CD)"
codeSetNm="PHVS_CRITICALINFRASTRUCTURESECTOR_NND_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Specify the workplace setting" id="NBS686_CD_2_OthL">Other If yes, specify workplace setting:</span></td>
<td> <span id="NBS686_CD_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS686_CDOth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient travel by air?" id="445000002_2L" >
Airport/Airplane:</span></td><td>
<span id="445000002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(445000002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility)." id="NBS687_2L" >
Adult Congregate Living Facility (nursing, assisted living, or LTC facility):</span></td><td>
<span id="NBS687_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS687)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was patient exposed to a correctional facility?" id="NBS689_2L" >
Correctional Facility:</span></td><td>
<span id="NBS689_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS689)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a school oruniversity ?" id="257698009_2L" >
School/University Exposure:</span></td><td>
<span id="257698009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(257698009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a child care facility?" id="413817003_2L" >
Child Care Facility Exposure:</span></td><td>
<span id="413817003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(413817003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did subject attend any events or large gatherings prior to onset of illness?" id="FDD_Q_184_2L" >
Community Event/Mass Gathering:</span></td><td>
<span id="FDD_Q_184_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_184)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?" id="NBS559_2L" >
Animal with confirmed or suspected COVID-19:</span></td><td>
<span id="NBS559_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS559)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="FDD_Q_32_2L" title="Animal contact by type of animal">
Animal Type:</span></td><td>
<span id="FDD_Q_32_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(FDD_Q_32)"
codeSetNm="PHVS_ANIMALTYPE_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Animal contact by type of animal" id="FDD_Q_32_2_OthL">Other Animal Type:</span></td>
<td> <span id="FDD_Q_32_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_32Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any other exposure type?" id="NBS560_2L" >
Other Exposure:</span></td><td>
<span id="NBS560_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS560)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other exposure." id="NBS561_2L">
Other Exposure Specify:</span>
</td>
<td>
<span id="NBS561_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS561)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unknown exposures in the 14 days prior to illness onset" id="NBS667_2L" >
Unknown exposures in the 14 days prior to illness onset:</span></td><td>
<span id="NBS667_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS667)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25008_2" name="Exposure to Known Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact." id="NBS557_2L" >
Did the patient have contact with another COVID-19 case (probable or confirmed)?:</span></td><td>
<span id="NBS557_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS557)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?" id="NBS664_2L" >
Household contact:</span></td><td>
<span id="NBS664_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS664)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?" id="NBS665_2L" >
Community contact:</span></td><td>
<span id="NBS665_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS665)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?" id="NBS666_2L" >
Healthcare contact:</span></td><td>
<span id="NBS666_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS666)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If other contact with a known COVID-19 case, indicate other type of contact." id="INV603_6_2L" >
Other Contact Type?:</span></td><td>
<span id="INV603_6_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other contact type" id="INV897_2L">
Other Contact Type Specify:</span>
</td>
<td>
<span id="INV897_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV897)"  />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had contact with another COVID-19 case, was this person a U.S. case?" id="NBS543_2L" >
If the patient had contact with another COVID-19 case, was this person a U.S. case?:</span></td><td>
<span id="NBS543_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS543)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case." id="NBS350_2L">
nCoV ID of source case 1:</span>
</td>
<td>
<span id="NBS350_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS350)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_2_2L">
nCoV ID of source case 2:</span>
</td>
<td>
<span id="NBS350_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS350_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If epi-linked to a known case, Case ID of the epi-linked case." id="NBS350_3_2L">
nCoV ID of source case 3:</span>
</td>
<td>
<span id="NBS350_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS350_3)" />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25014_2" name="Information Source Details" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS553_2L" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.).">
Information Source for Clinical Information (check all that apply):</span></td><td>
<span id="NBS553_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS553)"
codeSetNm="INFO_SOURCE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." id="NBS553_2_OthL">Other Information Source for Clinical Information (check all that apply):</span></td>
<td> <span id="NBS553_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS553Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21003_2" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Were symptoms present during course of illness?" id="INV576_2L" >
Symptoms present during course of illness:</span></td><td>
<span id="INV576_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137_2L">Date of Symptom Onset:</span>
</td><td>
<span id="INV137_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138_2L">Date of Symptom Resolution:</span>
</td><td>
<span id="INV138_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV138)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Status of reported symptom(s)." id="NBS555_2L" >
If symptomatic, symptom status:</span></td><td>
<span id="NBS555_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS555)"
codeSetNm="SYMPTOM_STATUS_COVID"/>
</td> </tr>

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21016_2" name="Clinical Findings" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pneumonia?" id="233604007_2L" >
Did the patient develop pneumonia?:</span></td><td>
<span id="233604007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(233604007)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005_2L" >
Did the patient have acute respiratory distress syndrome?:</span></td><td>
<span id="67782005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(67782005)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an abnormal chest X-ray?" id="168734001_2L" >
Did the patient have an abnormal chest X-ray?:</span></td><td>
<span id="168734001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(168734001)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an abnormal EKG?" id="102594003_2L" >
Did the patient have an abnormal EKG?:</span></td><td>
<span id="102594003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(102594003)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have another diagnosis/etiology for their illness?" id="NBS546_2L" >
Did the patient have another diagnosis/etiology for their illness?:</span></td><td>
<span id="NBS546_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS546)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_2L">
Secondary Diagnosis Description 1:</span>
</td>
<td>
<span id="81885_6_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(81885_6)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_2_2L">
Secondary Diagnosis Description 2:</span>
</td>
<td>
<span id="81885_6_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(81885_6_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_3_2L">
Secondary Diagnosis Description 3:</span>
</td>
<td>
<span id="81885_6_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(81885_6_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other clinical findings associated with the illness being reported" id="NBS776_2L" >
Other Clinical Finding:</span></td><td>
<span id="NBS776_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS776)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other clinical finding" id="NBS776_OTH_2L">
Other Clinical Finding Specify:</span>
</td>
<td>
<span id="NBS776_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS776_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35003_2" name="Clinical Treatments" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive mechanical ventilation (MV)/intubation?" id="NBS673_2L" >
Did the patient receive mechanical ventilation (MV)/intubation?:</span></td><td>
<span id="NBS673_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV547_2L" title="Total days with mechanical ventilation.">
Total days with Mechanical Ventilation:</span>
</td><td>
<span id="INV547_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV547)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive ECMO?" id="233573008_2L" >
Did the patient receive ECMO?:</span></td><td>
<span id="233573008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS780_2L" title="Total days with Extracorporeal Membrane Oxygenation">
Total days with ECMO:</span>
</td><td>
<span id="NBS780_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS780)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator for other treatment type specify indicator" id="NBS781_2L" >
Other Treatment Type?:</span></td><td>
<span id="NBS781_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS781)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the treatment type is Other, specify the treatment." id="NBS389_2L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS389)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS782_2L" title="Total days with Other Treatment Type">
Total days with Other Treatment Type:</span>
</td><td>
<span id="NBS782_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS782)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21002_2" name="Symptom Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fever &gt;100.4F (38C)c?" id="386661006_2L" >
Fever &gt;100.4F (38C):</span></td><td>
<span id="386661006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV202_2L" title="What was the patients highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV202)"  />
<span id="INV202_2_UNIT" /><nedss:view name="PageForm" property="pageClientVO2.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have subjective fever (felt feverish)?" id="103001002_2L" >
Subjective fever (felt feverish):</span></td><td>
<span id="103001002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(103001002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have chills?" id="28376_2_2L" >
Chills:</span></td><td>
<span id="28376_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(28376_2)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have rigors?" id="38880002_2L" >
Rigors:</span></td><td>
<span id="38880002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(38880002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have muscle aches (myalgia)?" id="68962001_2L" >
Muscle aches (myalgia):</span></td><td>
<span id="68962001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient  have runny nose (rhinorrhea)?" id="82272006_2L" >
Runny nose (rhinorrhea):</span></td><td>
<span id="82272006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(82272006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a sore throat?" id="267102003_2L" >
Sore Throat:</span></td><td>
<span id="267102003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(267102003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience new olfactory disorder?" id="44169009_2L" >
New Olfactory Disorder:</span></td><td>
<span id="44169009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(44169009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience new taste disorder?" id="36955009_2L" >
New Taste Disorder:</span></td><td>
<span id="36955009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(36955009)"
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
<span title="Did the patient have new confusion or change in mental status?" id="419284004_2L" >
New confusion or change in mental status?:</span></td><td>
<span id="419284004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fatigue or malaise?" id="271795006_2L" >
Fatigue or malaise:</span></td><td>
<span id="271795006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(271795006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Inability to Wake or Stay Awake" id="NBS793_2L" >
Inability to Wake or Stay Awake:</span></td><td>
<span id="NBS793_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS793)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a Cough (new onset or worsening of chronic cough)?" id="49727002_2L" >
Cough (new onset or worsening of chronic cough):</span></td><td>
<span id="49727002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience wheezing?" id="56018004_2L" >
Wheezing:</span></td><td>
<span id="56018004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(56018004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007_2L" >
Shortness of Breath (dyspnea):</span></td><td>
<span id="267036007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience difficulty breathing?" id="230145002_2L" >
Difficulty Breathing:</span></td><td>
<span id="230145002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(230145002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?" id="3415004_2L" >
Pale/gray/blue skin/lips/nail beds?:</span></td><td>
<span id="3415004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(3415004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience chest pain?" id="29857009_2L" >
Chest Pain:</span></td><td>
<span id="29857009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Persistent Pain or Pressure in Chest" id="NBS794_2L" >
Persistent Pain or Pressure in Chest:</span></td><td>
<span id="NBS794_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS794)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have nausea?" id="16932000_2L" >
Nausea:</span></td><td>
<span id="16932000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(16932000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience vomiting?" id="422400008_2L" >
Vomiting:</span></td><td>
<span id="422400008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(422400008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001_2L" >
Abdominal Pain or Tenderness:</span></td><td>
<span id="21522001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?" id="62315008_2L" >
Diarrhea (=3 loose/looser than normal stools/24hr period):</span></td><td>
<span id="62315008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(62315008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did patient have loss of appetite?" id="79890006_2L" >
Loss of appetite:</span></td><td>
<span id="79890006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(79890006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338_2L" >
Other symptom(s)?:</span></td><td>
<span id="NBS338_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other signs and symptoms." id="NBS338_OTH_2L">
Other Symptoms:</span>
</td>
<td>
<span id="NBS338_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS338_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21004_2" name="Symptom Notes" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Notes pertaining to the symptoms indicated for this case." id="NBS337_2L">
Symptom Notes:</span>
</td>
<td>
<span id="NBS337_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS337)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21009_2" name="Pre-Existing Conditions" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of pre-existing medical conditions?" id="102478008_2L" >
Pre-existing medical conditions?:</span></td><td>
<span id="102478008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(102478008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21008_2" name="Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have diabetes mellitus?" id="73211009_2L" >
Diabetes Mellitus:</span></td><td>
<span id="73211009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(73211009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?" id="ARB017_2L" >
Hypertension:</span></td><td>
<span id="ARB017_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB017)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient severely obese (BMI &gt;= 40)?" id="414916001_2L" >
Severe Obesity (BMI &gt;=40):</span></td><td>
<span id="414916001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of cardiovascular disease?" id="128487001_2L" >
Cardiovascular disease:</span></td><td>
<span id="128487001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(128487001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of chronic renal disease?" id="709044004_2L" >
Chronic Renal disease:</span></td><td>
<span id="709044004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(709044004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of chronic liver disease?" id="328383001_2L" >
Chronic Liver disease:</span></td><td>
<span id="328383001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(328383001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001_2L" >
Chronic Lung Disease (asthma/emphysema/COPD):</span></td><td>
<span id="413839001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any history of other chronic disease?" id="NBS662_2L" >
Other Chronic Diseases:</span></td><td>
<span id="NBS662_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS662)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the other chronic disease(s)." id="NBS662_OTH_2L">
Specify Other Chronic Diseases:</span>
</td>
<td>
<span id="NBS662_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS662_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any other underlying conditions or risk behaviors?" id="NBS677_2L" >
Other Underlying Condition or Risk Behavior:</span></td><td>
<span id="NBS677_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS677)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient had an other underlying condition or risk behavior, specify the condition or behavior." id="NBS677_OTH_2L">
Specify Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<span id="NBS677_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS677_OTH)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of an immunocompromised/immunosuppressive condition?" id="370388006_2L" >
Immunosuppressive Condition:</span></td><td>
<span id="370388006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have an autoimmune disease or condition?" id="85828009_2L" >
Autoimmune Condition:</span></td><td>
<span id="85828009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(85828009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a current smoker?" id="65568007_2L" >
Current smoker:</span></td><td>
<span id="65568007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(65568007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient a former smoker?" id="8517006_2L" >
Former smoker:</span></td><td>
<span id="8517006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(8517006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient engage in substance abuse or misuse?" id="NBS676_2L" >
Substance Abuse or Misuse:</span></td><td>
<span id="NBS676_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS676)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?" id="110359009_2L" >
Disability:</span></td><td>
<span id="110359009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(110359009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_2L">
Specify Disability 1:</span>
</td>
<td>
<span id="NBS671_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS671)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_2_2L">
Specify Disability 2:</span>
</td>
<td>
<span id="NBS671_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS671_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_3_2L">
Specify Disability 3:</span>
</td>
<td>
<span id="NBS671_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS671_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a psychological or psychiatric condition?" id="74732009_2L" >
Psychological or Psychiatric Condition:</span></td><td>
<span id="74732009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(74732009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_2L">
Specify Psychological or Psychiatric Condition 1:</span>
</td>
<td>
<span id="NBS691_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS691)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_2_2L">
Specify Psychological or Psychiatric Condition 2:</span>
</td>
<td>
<span id="NBS691_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS691_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_3_2L">
Specify Psychological or Psychiatric Condition 3:</span>
</td>
<td>
<span id="NBS691_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS691_3)" />
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

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAR159_2L" title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks).">
Number of Weeks Gestation at Onset of Illness:</span>
</td><td>
<span id="VAR159_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAR159)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease." id="VAR160_2L" >
Trimester at Onset of Illness:</span></td><td>
<span id="VAR160_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAR160)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35004_2" name="Vaccination Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did subject ever receive a COVID-containing vaccine?" id="VAC126_2L" >
Did the patient ever received a COVID-containing vaccine?:</span></td><td>
<span id="VAC126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC140_2L" title="Number of vaccine doses against this disease prior to illness onset">
Vaccination Doses Prior to Onset:</span>
</td><td>
<span id="VAC140_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC140)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of last vaccine dose against this disease prior to illness onset" id="VAC142_2L">Date of Last Dose Prior to Illness Onset:</span>
</td><td>
<span id="VAC142_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC142)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?" id="VAC148_2L" >
Vaccinated per ACIP Recommendations:</span></td><td>
<span id="VAC148_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149_2L" >
Reason Not Vaccinated Per ACIP Recommendations:</span></td><td>
<span id="VAC149_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC149)"
codeSetNm="VACCINE_NOT_GIVEN_REASON_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149_2_OthL">Other Reason Not Vaccinated Per ACIP Recommendations:</span></td>
<td> <span id="VAC149_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(VAC149Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Comments about the subjects vaccination history" id="VAC133_2L">
Vaccine History Comments:</span>
</td>
<td>
<span id="VAC133_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC133)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21012_2" name="Laboratory Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was laboratory testing done to confirm the diagnosis?" id="INV740_2L" >
Laboratory Testing Performed:</span></td><td>
<span id="INV740_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV575_2L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span></td><td>
<span id="INV575_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV575)"
codeSetNm="PHVS_REASONFORTEST_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Listing of the reason(s) the subject was tested for the condition" id="INV575_2_OthL">Other Reason for Testing (check all that apply):</span></td>
<td> <span id="INV575_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV575Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="COVID-19 Variant" id="NBS786_2L" >
COVID-19 Variant:</span></td><td>
<span id="NBS786_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS786)"
codeSetNm="COVID_19_VARIANTS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="COVID-19 Variant" id="NBS786_2_OthL">Other COVID-19 Variant:</span></td>
<td> <span id="NBS786_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS786Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21011_2" name="COVID-19 Testing" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA21011_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA21011_2">
<tr id="patternNBS_UI_GA21011_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA21011_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA21011_2">
<tr id="nopatternNBS_UI_GA21011_2" class="odd" style="display:none">
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
<span title="Enter the performing laboratory type" id="LAB606_2L" >
Performing Lab Type:</span></td><td>
<span id="LAB606_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABORATORYTYPE_VPD_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606_2_OthL">Other Performing Lab Type:</span></td>
<td> <span id="LAB606_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB606Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163_2L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB163)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Please enter the performing lab specimen ID number for this lab test." id="NBS674_2L">
Specimen ID:</span>
</td>
<td>
<span id="NBS674_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS674)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specimen type from which positive lab specimen was collected." id="LAB165_2L" >
Specimen Source:</span></td><td>
<span id="LAB165_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB165)"
codeSetNm="SPECIMEN_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Specimen type from which positive lab specimen was collected." id="LAB165_2_OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB165Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Lab Test Type" id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="TEST_TYPE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Lab Test Type" id="INV290_2_OthL">Other Test Type:</span></td>
<td> <span id="INV290_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Lab test coded result" id="INV291_2L" >
Test Result:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="PHVS_LABTESTINTERPRETATION_VPD_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Lab test coded result" id="INV291_2_OthL">Other Test Result:</span></td>
<td> <span id="INV291_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV291Oth)"/></td></tr>

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
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115_2L" >
Quantitative Test Result Units:</span></td><td>
<span id="LAB115_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Comments having to do specifically with the lab result test. These are the comments from the NTE segment if the result was originally an Electronic Laboratory Report." id="8251_1_2L">
Test Result Comments:</span>
</td>
<td>
<span id="8251_1_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(8251_1)"  />
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="LAB331_2L" title="Was the isolate sent to a state public health laboratory? (Answer Yes if it was sent to any state lab, even if it was sent to a lab outside of the cases state of residence)">Specimen Sent to State Public Health Lab?:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(LAB331)" styleId="LAB331_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="LAB515_2L" title="Was a specimen sent to CDC for testing?">Specimen Sent to CDC?:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(LAB515)" styleId="LAB515_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23001_2" name="Additional State or Local Specimen IDs" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23001_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA23001_2">
<tr id="patternNBS_UI_GA23001_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA23001_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA23001_2">
<tr id="nopatternNBS_UI_GA23001_2" class="odd" style="display:none">
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
<span title="Please provide any additional specimen ID of interest." id="NBS670_2L">
Additional Specimen ID:</span>
</td>
<td>
<span id="NBS670_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS670)" />
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
<nedss:container id="NBS_UI_GA25002_2" name="Retired Questions" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Report Date of Person Under Investigation (PUI) to CDC" id="NBS549_2L">Report Date of PUI to CDC:</span>
</td><td>
<span id="NBS549_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS549)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?" id="NBS541_2L" >
Patient history of being in a healthcare facility (as a patient, worker or visitor) in China?:</span></td><td>
<span id="NBS541_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS541)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of healthcare contact with another lab-confirmed case-patient." id="NBS544_2L" >
Type of healthcare contact:</span></td><td>
<span id="NBS544_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS544)"
codeSetNm="HC_CONTACT_TYPE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology." id="NBS558_2L" >
Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology:</span></td><td>
<span id="NBS558_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS558)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS556_2L" title="Did the patient travel to any high-risk locations?">
Did the patient travel to any high-risk locations:</span></td><td>
<span id="NBS556_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS556)"
codeSetNm="HIGH_RISK_TRAVEL_LOC_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Did the patient travel to any high-risk locations?" id="NBS556_2_OthL">Other Did the patient travel to any high-risk locations:</span></td>
<td> <span id="NBS556_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS556Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02." id="NBS554_2L">
Source patient case ID:</span>
</td>
<td>
<span id="NBS554_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS554)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date the case was reported to CDC." id="NBS663_2L">Report Date of Case to CDC:</span>
</td><td>
<span id="NBS663_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS663)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA36000_2" name="Retired Questions Do Not Use" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Questions in this section have been replaced by other standard questions. These versions of the questions should not be used going forward, but are on the page to maintain any data previously collected.</span></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the patient has any tribal affiliation, enter the Indian Tribe name." id="67884_7_2L">
Tribe Name(s) (Retired):</span>
</td>
<td>
<span id="67884_7_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(67884_7)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?" id="54899_0_2L">
If yes, specify which language - Retired:</span>
</td>
<td>
<span id="54899_0_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(54899_0)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the type of workplace setting" id="NBS686_2L">
If yes, specify workplace setting (Retired):</span>
</td>
<td>
<span id="NBS686_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS686)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the type of animal with which the patient had contact." id="FDD_Q_32_TXT_2L">
Specify Type of Animal (Retired):</span>
</td>
<td>
<span id="FDD_Q_32_TXT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_32_TXT)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?" id="NBS688_2L" >
School/University/Childcare Center Exposure - Retired:</span></td><td>
<span id="NBS688_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS688)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have chills or rigors?" id="43724002_2L" >
Chills or Rigors - Retired:</span></td><td>
<span id="43724002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(43724002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?" id="NBS675_2L" >
New Olfactory and Taste Disorder - Retired:</span></td><td>
<span id="NBS675_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS675)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS777_2L" title="Indicate all the countries of travel in the last 14 days (Multi-Select)">
Travel Country (Multi Select):</span></td><td>
<span id="NBS777_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS777)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS778_2L" title="Indicate all the states of travel in the last 14 days (Multi-Select)">
Travel State (Multi-Select):</span></td><td>
<span id="NBS778_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS778)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA22003_2" name="Respiratory Diagnostic Testing Retired" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza A Rapid Ag Result" id="80382_5_2L" >
Influenza A Rapid Ag:</span></td><td>
<span id="80382_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(80382_5)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza B Rapid Ag Result" id="80383_3_2L" >
Influenza B Rapid Ag:</span></td><td>
<span id="80383_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(80383_3)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza A PCR Result" id="34487_9_2L" >
Influenza A PCR:</span></td><td>
<span id="34487_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(34487_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Influenza B PCR Result" id="40982_1_2L" >
Influenza B PCR:</span></td><td>
<span id="40982_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(40982_1)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Respiratory Syncytial Virus (RSV) Result" id="6415009_2L" >
RSV:</span></td><td>
<span id="6415009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(6415009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="H. metapneumovirus Result" id="416730002_2L" >
H. metapneumovirus:</span></td><td>
<span id="416730002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(416730002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Parainfluenza (1-4) Result" id="41505_9_2L" >
Parainfluenza (1-4):</span></td><td>
<span id="41505_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(41505_9)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Adenovirus Result" id="74871001_2L" >
Adenovirus:</span></td><td>
<span id="74871001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(74871001)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Rhinovirus/Enterovirus Result" id="69239002_2L" >
Rhinovirus/enterovirus:</span></td><td>
<span id="69239002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(69239002)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Coronavirus (OC43, 229E, HKU1, NL63) Result" id="84101006_2L" >
Coronavirus (OC43, 229E, HKU1, NL63):</span></td><td>
<span id="84101006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(84101006)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="M. pneumoniae Result" id="58720004_2L" >
M. pneumoniae:</span></td><td>
<span id="58720004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(58720004)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="C. pneumoniae Result" id="103514009_2L" >
C. pneumoniae:</span></td><td>
<span id="103514009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(103514009)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether additional pathogen testing was completed for this patient." id="NBS672_2L" >
Were Other Pathogen(s) Tested?:</span></td><td>
<span id="NBS672_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS672)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23000_2" name="Other Pathogens Tested Retired" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23000_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_GA23000_2">
<tr id="patternNBS_UI_GA23000_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA23000_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_GA23000_2">
<tr id="nopatternNBS_UI_GA23000_2" class="odd" style="display:none">
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
<span title="Other Pathogen Tested" id="NBS669_2L">
Specify Other Pathogen Tested:</span>
</td>
<td>
<span id="NBS669_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS669)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Other Pathogen Tested Result" id="NBS668_2L" >
Other Pathogens Tested:</span></td><td>
<span id="NBS668_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS668)"
codeSetNm="TEST_RESULT_RDT_COVID"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
