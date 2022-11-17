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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","Case Information","General Comments","Laboratory","Travel","Prophylactic Treatment","Medical History","Complications","Treatment History","Part II: Completed 4 Weeks After Treatment","Contact Investigation"};
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

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM255L" title="Subject's weight at diagnosis">
Weight:</span>
</td><td>
<span id="DEM255"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM255)"  />
<span id="DEM255UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(DEM255Unit)" codeSetNm="WEIGHT_UNIT" />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS434L" title="Subjects height at diagnosis">
Height:</span>
</td><td>
<span id="NBS434"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS434)"  />
<span id="NBS434UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(NBS434Unit)" codeSetNm="PHVS_HEIGHTUNIT_UCUM" />
</td></tr>
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
<nedss:container id="NBS_UI_80" name="Occupation and Industry Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_80"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_80">
<tr id="patternNBS_UI_80" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_80');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_80">
<tr id="nopatternNBS_UI_80" class="odd" style="display:none">
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
Legacy Case ID:</span>
</td>
<td>
<span id="INV200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV200)" />
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169L" title="Condition_Cd should always be a hidden or read-only field.">Hidden Condition:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(INV169)" styleId="INV169"><html:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select> </td></tr>
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
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospitalization Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient hospitalized as a result of this event?" id="INV128L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_87" name="LEGACY Hospital Information" isHidden="T" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The questions in this subsection are hidden and used only for LEGACY porting purposes. The repeating block should be used for hospitalization information for Malaria</span></td></tr>

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_68" name="Hospitalized for Malaria" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_68"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_68">
<tr id="patternNBS_UI_68" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_68');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_68">
<tr id="nopatternNBS_UI_68" class="odd" style="display:none">
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
<span title="Was patient admitted to the hospital for greater than 24 hours as an inpatient?" id="32485007L" >
Admitted as Inpatient:</span></td><td>
<span id="32485007" />
<nedss:view name="PageForm" property="pageClientVO.answer(32485007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If admitted, what is the name of the hospital?" id="58237_9L">
Hospital Name:</span>
</td>
<td>
<span id="58237_9"/>
<nedss:view name="PageForm" property="pageClientVO.answer(58237_9)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If patient was hospitalized for malaria, enter admit date." id="52455_3L">Admission Date:</span>
</td><td>
<span id="52455_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(52455_3)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If patient was hospitalized for malaria, enter discharge date." id="52525_3L">Discharge Date:</span>
</td><td>
<span id="52525_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(52525_3)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV134_BL" title="Hospital Duration of Stay in Days">
Duration of Stay in Days:</span>
</td><td>
<span id="INV134_B"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV134_B)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient was hospitalized, enter hospital record number" id="46106_1L">
Hospital Record Number:</span>
</td>
<td>
<span id="46106_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(46106_1)" />
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137L">Illness Onset Date:</span>
</td><td>
<span id="INV137"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138L">Illness End Date:</span>
</td><td>
<span id="INV138"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV138)"  />
</td></tr>

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc." id="INV159L" >
Detection Method:</span></td><td>
<span id="INV159" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV159)"
codeSetNm="PHC_DET_MT"/>
</td> </tr>

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
<span title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition." id="NOT120SPECL">
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
<nedss:container id="NBS_UI_84" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests:</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;(i) blood smear with the highest percentage parasitemia,</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;(ii) the test that indicates the Plasmodium species, and</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;(iii) a confirmatory PCR (if applicable).</span></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_59" name="Lab Data Entry Guidance" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If there are conflicting lab results for the species identification, then include only the test with the final result. Multiple species can be selected for one test.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If the species determination is inconclusive, select 'Not Determined.' If there is a suspicion towards a particular species (e.g. non-falciparum), select 'Not Determined' and 'Other' and write the suspected species in the 'Other Organism Name' text box.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;--Example A: 50 infected erythrocytes out of 1000 counted = 5% parasitemia, and the submitted response should "5".</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;--Example B: 2 infected erythrocytes out of 2000 counted = 0.1% parasitemia and the submitted response should be 0.1.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was laboratory testing done to confirm the diagnosis" id="INV740L" >
Was laboratory testing done to confirm the diagnosis?:</span></td><td>
<span id="INV740" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV740)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_79" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_79"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_79">
<tr id="patternNBS_UI_79" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_79');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_79">
<tr id="nopatternNBS_UI_79" class="odd" style="display:none">
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
<span title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="LAB_TEST_PROCEDURE_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Type of diagnostic test(s) performed for this patient: Complete a minimum of one positive malaria diagnostic test. It is preferable to include the following tests: (i) blood smear with the highest percentage parasitemia, (ii) the test that indicates the Plasmodium species, and (iii) a confirmatory PCR (if applicable). If there are conflicting lab results for the species identification, then include only the test with the final result." id="INV290OthL">Other Test Type:</span></td>
<td> <span id="INV290Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV290Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Result of Diagnostic Test" id="INV291L" >
Result of Diagnostic Test:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_PNUND"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="LAB278L" title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section.">
Organism Name (see tool tip for guidance):</span></td><td>
<span id="LAB278" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB278)"
codeSetNm="SPECIES_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Species identified through testing: If there are conflicting lab results for the species identification, then include only the test with the final result.  For a lab result that identifies more than one species, multiple species can be selected for that one test. If the species determination is inconclusive, then select “Not determined”; if there is a suspicion towards a particular species (e.g. “non-falciparum species”) then select “Not determined” and write the suspected species in the “Other species, specify” section." id="LAB278OthL">Other Organism Name (see tool tip for guidance):</span></td>
<td> <span id="LAB278Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB278Oth)"/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="53556_7L" title="The percentage parasitemia  is the number of infected erythrocytes expressed as a percentage of the total erythrocytes. Response should be a numeric value between 0.01 - 100. Extracellular gametocytes should not be counted for the percentage parasitemia.">
For blood smear tests, what is the highest percentage parasitemia:</span>
</td><td>
<span id="53556_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(53556_7)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." id="LAB163L">Specimen Collection Date:</span>
</td><td>
<span id="LAB163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB163)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date result sent from reporting laboratory. Time of result is an optional addition to date." id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Performing laboratory name" id="68994_3L">
Performing laboratory name:</span>
</td>
<td>
<span id="68994_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(68994_3)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Reporting laboratory phone number" id="65651_2L">
Laboratory Phone Number:</span>
</td>
<td>
<span id="65651_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(65651_2)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Specimen Type Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was Specimen(s) sent to CDC?" id="LAB515L" >
Specimen(s) sent to CDC:</span></td><td>
<span id="LAB515" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB515)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Sent to CDC" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_39"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_39">
<tr id="patternNBS_UI_39" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_39');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_39">
<tr id="nopatternNBS_UI_39" class="odd" style="display:none">
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
<span title="Type(s) of specimen sent to CDC" id="667469L" >
Type(s) of Specimen sent to CDC:</span></td><td>
<span id="667469" />
<nedss:view name="PageForm" property="pageClientVO.answer(667469)"
codeSetNm="SPECIMEN_TYPE_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Type(s) of specimen sent to CDC" id="667469OthL">Other Type(s) of Specimen sent to CDC:</span></td>
<td> <span id="667469Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(667469Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456." id="INV965L">
CDC Specimen ID Number:</span>
</td>
<td>
<span id="INV965"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV965)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where does the person usually live (defined as their residence)." id="INV501L" >
Country of Usual Residence:</span></td><td>
<span id="INV501" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="TRAVEL14L" title="Reside in US Prior to Most Recent Travel">Legacy - Did the patient reside in the U.S. prior to most recent travel?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(TRAVEL14)" styleId="TRAVEL14"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="MAL135L" title="Principal Reason for Travel question created for Malaria Porting only.">Legacy - Principal Reason for Travel:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(MAL135)" styleId="MAL135"><html:optionsCollection property="codedValue(TRAVEL_REASON_MAL)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the patient traveled or lived outside the U.S. during the past two years" id="TRAVEL10L" >
Has the patient traveled or lived outside the U.S. during the past two years?:</span></td><td>
<span id="TRAVEL10" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL10)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What was the patient's country of residence prior to most recent travel?" id="TRAVEL15L" >
What was the patient's country of residence prior to most recent travel?:</span></td><td>
<span id="TRAVEL15" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL15)"
codeSetNm="PSL_CNTRY"/>
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If the patient traveled outside of the US or lived outside of the US within the last 2 years:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" id="TRAVEL05L" >
Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span></td><td>
<span id="TRAVEL05" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05)"
codeSetNm="GEOGRAPHIC_LOCATION_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Country of International Travel or Residence (for non-US residents) During the Past 2 Years" id="TRAVEL05OthL">Other Country of International Travel or Residence (for non-US residents) During the Past 2 Years:</span></td>
<td> <span id="TRAVEL05Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL05Oth)"/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="82310_4L" title="Duration of stay in country outside the US">
Duration of Stay in Country Outside the US:</span>
</td><td>
<span id="82310_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82310_4)"  />
<span id="82310_4UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82310_4Unit)" codeSetNm="PHVS_DURATIONUNIT_CDC_1" />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date Returned to/Arrive in US" id="TRAVEL08L">Date Returned to/Arrive in US:</span>
</td><td>
<span id="TRAVEL08"/>
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL08)"  />
</td></tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Date Question-->

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="TRAVEL16L" title="Reason for travel related to current illness">
Reasons for Travel:</span></td><td>
<span id="TRAVEL16" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL16)"
codeSetNm="TRAVEL_REASON_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Reason for travel related to current illness" id="TRAVEL16OthL">Other Reasons for Travel:</span></td>
<td> <span id="TRAVEL16Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Choose the mode of travel" id="NBS453L" >
Travel Mode:</span></td><td>
<span id="NBS453" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS453)"
codeSetNm="TRAVEL_MODE_MAL"/>
</td> </tr>

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_35" name="Chemoprophylaxis Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Complete this section for anti-malarial medications taken prior to or during travel.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?" id="182929008L" >
Was malaria chemoprophylaxis taken for prevention of malaria, prior to or during travel?:</span></td><td>
<span id="182929008" />
<nedss:view name="PageForm" property="pageClientVO.answer(182929008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV931L" title="Chemoprophylaxis Medication(s)">
List chemoprophylaxis medications taken by the patient:</span></td><td>
<span id="INV931" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV931)"
codeSetNm="MEDICATION_PROPHYLAXIS_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Chemoprophylaxis Medication(s)" id="INV931OthL">Other List chemoprophylaxis medications taken by the patient:</span></td>
<td> <span id="INV931Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV931Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was chemoprophylaxis taken as prescribed? (Yes = missed no doses, No = missed doses)" id="INV309L" >
Was chemoprophylaxis taken as prescribed (without any skipped doses)?:</span></td><td>
<span id="INV309" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV309)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV932L" title="Reason for missed chemoprophylaxis">
Reason(s) doses were missed:</span></td><td>
<span id="INV932" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV932)"
codeSetNm="MEDICATION_MISSED_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Reason for missed chemoprophylaxis" id="INV932OthL">Other Reason(s) doses were missed:</span></td>
<td> <span id="INV932Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV932Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="If reason for missed doses is due to a side effect, then specify side effect" id="INV921L">
Specify side effects of missed dose(s):</span>
</td>
<td>
<span id="INV921"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV921)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43" name="Previous Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a previous history of malaria in the last 12 months, prior to this report?" id="161413004L" >
Did the patient have a previous history of malaria in the last 12 months, prior to this report?:</span></td><td>
<span id="161413004" />
<nedss:view name="PageForm" property="pageClientVO.answer(161413004)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Previous Malaria Illness" isHidden="F" classType="subSect" >
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

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV914L" title="Malaria species associated with previous illness">
Organism Associated with Previous Illness:</span></td><td>
<span id="INV914" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV914)"
codeSetNm="SPECIES_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Malaria species associated with previous illness" id="INV914OthL">Other Organism Associated with Previous Illness:</span></td>
<td> <span id="INV914Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV914Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of previous malaria illness" id="82758_4L">Date of Previous Malaria Illness:</span>
</td><td>
<span id="82758_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82758_4)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_56" name="Blood Transfusion/Organ Transplant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?" id="82312_0L" >
Did the patient receive a blood transfusion/organ transplant in the 12 months prior to illness?:</span></td><td>
<span id="82312_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(82312_0)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of blood transfusion/organ transplant" id="80989_7L">Date of blood transfusion/organ transplant:</span>
</td><td>
<span id="80989_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(80989_7)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_82" name="Clinical Complications" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="67187_5L" title="Complication(s) related to this malaria illness">
Complication(s) Related to this Malaria Illness:</span></td><td>
<span id="67187_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(67187_5)"
codeSetNm="COMPLICATIONS_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Complication(s) related to this malaria illness" id="67187_5OthL">Other Complication(s) Related to this Malaria Illness:</span></td>
<td> <span id="67187_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(67187_5Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_71" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;List all malaria medications taken for this illness.</span></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="Therapy for this Attack" isHidden="F" classType="subSect" >
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Listing of treatment the subject received for this illness" id="55753_8L" >
Treatment the Patient Received:</span></td><td>
<span id="55753_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(55753_8)"
codeSetNm="MEDICATION_TREATMENT_MAL"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Listing of treatment the subject received for this illness" id="55753_8OthL">Other Treatment the Patient Received:</span></td>
<td> <span id="55753_8Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(55753_8Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the treatment was initiated" id="INV924L">Treatment Start Date:</span>
</td><td>
<span id="INV924"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV924)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment stopped" id="413947000L">Treatment Stop Date:</span>
</td><td>
<span id="413947000"/>
<nedss:view name="PageForm" property="pageClientVO.answer(413947000)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="67453_1L" title="Number of days the patient was prescribed antimalarial treatment">
Treatment Duration (in days):</span>
</td><td>
<span id="67453_1"/>
<nedss:view name="PageForm" property="pageClientVO.answer(67453_1)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_67" name="Treatment Outcome" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Treatment taken as prescribed? Assess 4 weeks after start of treatment" id="INV917L" >
Was the medicine taken as prescribed?:</span></td><td>
<span id="INV917" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV917)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did all signs or symptoms of malaria resolve without any additional malaria treatment within 7 days after starting treatment? Assess 4 weeks after start of treatment." id="313185002L" >
Did all signs or symptoms of malaria resolve within 7 days after starting treatment?:</span></td><td>
<span id="313185002" />
<nedss:view name="PageForm" property="pageClientVO.answer(313185002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience a recurrence of malaria during the 4 weeks after starting malaria treatment" id="161917009L" >
Was there a recurrence of malaria?:</span></td><td>
<span id="161917009" />
<nedss:view name="PageForm" property="pageClientVO.answer(161917009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject experience any adverse events within 4 weeks after receiving the malaria treatment? If yes, please complete the adverse event and pre/post-treatment medication questions." id="391103005L" >
Did the patient experience any adverse events within 4 weeks of starting malaria treatment?:</span></td><td>
<span id="391103005" />
<nedss:view name="PageForm" property="pageClientVO.answer(391103005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;IF PATIENT EXPERIENCED ANY ADVERSE EVENTS WITHIN 4 WEEKS AFTER RECEIVING MALARIA TREATMENT: Complete Medication Information and Adverse Event Information subsections.</span></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_73" name="Medication Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;List all prescription and over-the-counter medicines the patient had taken during the 2 weeks before, or during the 4 weeks after starting malaria treatment.</span></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_70" name="Medications" isHidden="F" classType="subSect" >
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Please list all prescription and over the counter medicines the patient had taken during the 2 weeks before and during the 4 weeks after starting treatment for malaria. If information for both pre- and post-treatment are available, please complete below questions for each time frame." id="NBS464L">
Medication Administered:</span>
</td>
<td>
<span id="NBS464"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS464)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the patient took the medication 2 weeks before treatment or within the 4 weeks after starting treatment." id="INV1284L" >
Medication Administered Relative to Treatment:</span></td><td>
<span id="INV1284" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV1284)"
codeSetNm="MEDICATION_ADMINISTERED_RELATIVE_TREATMENT_MAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Medication Start Date" id="91381_4L">Medication Start Date:</span>
</td><td>
<span id="91381_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(91381_4)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Medication Stop Date" id="NBS423L">Medication Stop Date:</span>
</td><td>
<span id="NBS423"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS423)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="91383_0L" title="Number of days that patient took/will take the medication referenced.">
Medication Duration (in days):</span>
</td><td>
<span id="91383_0"/>
<nedss:view name="PageForm" property="pageClientVO.answer(91383_0)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33" name="Adverse Event Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_33"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_33">
<tr id="patternNBS_UI_33" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_33');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_33">
<tr id="nopatternNBS_UI_33" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Adverse event description" id="42563_7L">
Adverse event description:</span>
</td>
<td>
<span id="42563_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(42563_7)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is it suspected a causal relationship between the treatment and the adverse event is at least a reasonable possibility" id="INV918L" >
Is the adverse event related to treatment?:</span></td><td>
<span id="INV918" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV918)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="82311_2L" title="Time to onset since starting treatment">
Adverse event time to onset since starting treatment:</span>
</td><td>
<span id="82311_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(82311_2)"  />
<span id="82311_2UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(82311_2Unit)" codeSetNm="DUR_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Adverse event outcome severity" id="64750_3L" >
Adverse event outcome severity:</span></td><td>
<span id="64750_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(64750_3)"
codeSetNm="ADVERSE_EVENT_SEVERITY_MAL"/>
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
</div> </td></tr>
