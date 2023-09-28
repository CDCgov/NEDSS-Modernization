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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Pregnancy and Birth Information","Laboratory Findings","Signs and Symptoms","Epidemiologic","General Comments","Contact Investigation"};
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of Birth" id="DEM126_2L" >
Country of Birth:</span></td><td>
<span id="DEM126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

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
codeSetNm="P_ETHN_GRP"/>
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
codeSetNm="PHC_RPT_SRC_T"/>
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
<nedss:container id="NBS_UI_13_2" name="Hospital" isHidden="F" classType="subSect" >

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
Total duration of stay in the hospital (in days):</span>
</td><td>
<span id="INV134_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Condition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of arbovirus the case was infected with." id="ARB001_2L" >
Type of Arbovirus:</span></td><td>
<span id="ARB001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB001)"
codeSetNm="PHVS_VIRUSTYPE_ARBOVIRALDISEASE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Clinical Syndrome is the type of clinical presentation the case had." id="ARB002_2L" >
Clinical Syndrome:</span></td><td>
<span id="ARB002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB002)"
codeSetNm="PHVS_CLINICALSYNDROME_ARBOVIRUS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other clinical syndrome the case had." id="ARB002_OTH_2L">
Other Clinical Syndrome:</span>
</td>
<td>
<span id="ARB002_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB002_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of clinical presentation the case had." id="ARB050_2L" >
Clinical Syndrome, Secondary:</span></td><td>
<span id="ARB050_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB050)"
codeSetNm="PHVS_CLINICALSYNDROMESECONDARY_ARBOVIRUS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other clinical syndrome secondary the case had." id="ARB050_OTH_2L">
Other Clinical Syndrome, Secondary:</span>
</td>
<td>
<span id="ARB050_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB050_OTH)" />
</td> </tr>

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
codeSetNm="DUR_UNIT"/>
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
<nedss:container id="NBS_INV_ARBO_UI_1_2" name="Pregnancy" isHidden="F" classType="subSect" >

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
<span title="Mother Last menstrual period (LMP) start date before delivery." id="752030_2L">Mother's Last Menstrual Period Before Delivery:</span>
</td><td>
<span id="752030_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(752030)"  />
</td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV909_2L" title="Complications of pregnancy such as Microcephaly, Intracranial Calcification, Fetal growth abnormality and Fetus with Central Nervous System (CNS) abnormalities.">
Pregnancy Complications:</span></td><td>
<span id="INV909_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV909)"
codeSetNm="PHVS_PREGNANCYCOMPLICATIONS_ARBOVIRUS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="This maternal data element captures the pregnancy outcomes such as live birth, premature birth, stillbirth, fetal loss, perinatal death, and therapeutic abortion." id="638932_2L" >
Pregnancy Outcome:</span></td><td>
<span id="638932_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(638932)"
codeSetNm="PHVS_PREGNANCYOUTCOME_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_3_2" name="Case Linkage" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Mother and Infant Case ID links detail about maternal to newborn case information. If the case is for mother, please put the related Infant case ID(s), which could include multiple case IDs in case of multiple births. If the case is for an infant or newborn, please put the mother's Case ID." id="INV908_1_2L">
Mother-Infant Case ID Linkage 1:</span>
</td>
<td>
<span id="INV908_1_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV908_1)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Mother and Infant Case ID links detail about maternal to newborn case information. If the case is for mother, please put the related Infant case ID(s), which could include multiple case IDs in case of multiple births. If the case is for an infant or newborn, please put the mother's Case ID." id="INV908_2_2L">
Mother-Infant Case ID Linkage 2:</span>
</td>
<td>
<span id="INV908_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV908_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Mother and Infant Case ID links detail about maternal to newborn case information. If the case is for mother, please put the related Infant case ID(s), which could include multiple case IDs in case of multiple births. If the case is for an infant or newborn, please put the mother's Case ID." id="INV908_3_2L">
Mother-Infant Case ID Linkage 3:</span>
</td>
<td>
<span id="INV908_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV908_3)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_2_2" name="Newborn" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="324160_2L" title="This data element is used only for Congenital/Neonatal cases, and includes findings such as Microcephaly, intracranial calcification, Congenital anomaly of central nervous system, intrauterine growth restriction, ocular defects and limb deformities.">
Newborn Complications:</span></td><td>
<span id="324160_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(324160)"
codeSetNm="PHVS_NEWBORNCOMPLICATIONS_ARBOVIRUS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_4_2" name="Diagnostic Lab Test Findings" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_ARBO_UI_4_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_ARBO_UI_4_2">
<tr id="patternNBS_INV_ARBO_UI_4_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_ARBO_UI_4_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_ARBO_UI_4_2">
<tr id="nopatternNBS_INV_ARBO_UI_4_2" class="odd" style="display:none">
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
<span title="Select the type of lab test performed" id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_ARBOVIRUS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Test Result" id="INV291_2L" >
Test Result/Interpretation:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="DIAGNOSTICLABTESTFINDING"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If 'Other Arbovirus PCR (Not serum / CSF)' was selected as test type, specify the specimen type. List of specimens that are used for Arboviral lab tests include Serum, CSF, Amniotic Fluid, Urine, Semen, Cord blood, etc." id="667469_2L" >
Specimen Type:</span></td><td>
<span id="667469_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(667469)"
codeSetNm="PHVS_SPECIMENTYPE_ARBOVIRUS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the specimen was collected." id="338822_2L">Specimen Collection Date:</span>
</td><td>
<span id="338822_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(338822)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the performing laboratory type" id="LAB606_2L" >
Performing Lab Type:</span></td><td>
<span id="LAB606_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB606)"
codeSetNm="PHVS_PERFORMINGLABTYPE_ARBOVIRUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_5_2" name="Lab Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Cerebrospinal Fluid (CSF) Pleocytosis (&gt;=5 WBC). This is an interpretation based upon the lab values for CSF White Blood Cell (WBC) count." id="91454002_2L" >
Cerebrospinal Fluid (CSF) Pleocytosis (&gt;=5 WBC):</span></td><td>
<span id="91454002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(91454002)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Serum paired antibody result. This is an interpretation based upon the lab values paired sera testing of specimen 1 (acute) and specimen 2 (convalescent). For arboviral lab tests, these tests are usually PRNT." id="LAB617_2L" >
Serum Paired Antibody Test Interpretation:</span></td><td>
<span id="LAB617_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB617)"
codeSetNm="PHVS_POSNEG4FOLDRISENOTDONE_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="For dengue, specify the dengue virus serotype" id="404400_2L" >
Dengue (DENV) Serotype:</span></td><td>
<span id="404400_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(404400)"
codeSetNm="PHVS_SEROTYPE_DENGUE"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_7_2" name="Generalized" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fever?" id="386661006_2L" >
Fever:</span></td><td>
<span id="386661006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have chills or rigors?" id="43724002_2L" >
Chills or Rigors:</span></td><td>
<span id="43724002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(43724002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have fatigue or malaise?" id="271795006_2L" >
Fatigue or Malaise:</span></td><td>
<span id="271795006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(271795006)"
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
<span title="Did the patient have headache?" id="25064002_2L" >
Headache:</span></td><td>
<span id="25064002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_8_2" name="Musculoskeletal" isHidden="F" classType="subSect" >

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
<span title="Did the patient have arthralgia?" id="57676002_2L" >
Arthralgia:</span></td><td>
<span id="57676002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(57676002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have arthritis?" id="3723001_2L" >
Arthritis:</span></td><td>
<span id="3723001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(3723001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have paralysis or paresis?" id="44695005_2L" >
Paralysis or Paresis:</span></td><td>
<span id="44695005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(44695005)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_9_2" name="Neurological" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have stiff neck?" id="161882006_2L" >
Stiff Neck:</span></td><td>
<span id="161882006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(161882006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have ataxia?" id="20262006_2L" >
Ataxia:</span></td><td>
<span id="20262006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(20262006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have Parkinsonism or Cogwheel Rigidity?" id="32798002_2L" >
Parkinsonism or Cogwheel Rigidity:</span></td><td>
<span id="32798002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(32798002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have altered mental status?" id="419284004_2L" >
Altered Mental Status:</span></td><td>
<span id="419284004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have seizures?" id="91175000_2L" >
Seizures:</span></td><td>
<span id="91175000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(91175000)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_10_2" name="Eye" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have conjunctivitis?" id="9826008_2L" >
Conjunctivitis:</span></td><td>
<span id="9826008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(9826008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have retro-orbital pain (pain behind the eye)?" id="PHC1400_2L" >
Retro-orbital Pain:</span></td><td>
<span id="PHC1400_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1400)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_11_2" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have Nausea or Vomiting?" id="16932000_2L" >
Nausea or Vomiting:</span></td><td>
<span id="16932000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(16932000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have diarrhea?" id="62315008_2L" >
Diarrhea:</span></td><td>
<span id="62315008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(62315008)"
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
<span title="Did the patient have persistent vomiting?" id="196746003_2L" >
Persistent Vomiting:</span></td><td>
<span id="196746003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(196746003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have liver enlargement (hepatomegaly)?" id="80515008_2L" >
Liver Enlargement (Hepatomegaly):</span></td><td>
<span id="80515008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(80515008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Oral or Mouth ulcer" id="26284000_2L" >
Oral Ulcer:</span></td><td>
<span id="26284000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(26284000)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_12_2" name="Bleeding" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have extravascular fluid accumulation (e.g Pleural, pericardial effusion, ascites)?" id="PHC1401_2L" >
Extravascular Fluid Accumulation:</span></td><td>
<span id="PHC1401_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1401)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have mucosal bleeding (e.g. epistaxis-nose bleeding, gastrointestinal bleeding)?" id="PHC1402_2L" >
Mucosal Bleeding:</span></td><td>
<span id="PHC1402_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1402)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have severe plasma leakage (shock, pleural effusion)?" id="PHC1403_2L" >
Severe Plasma Leakage:</span></td><td>
<span id="PHC1403_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1403)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have severe bleeding?" id="PHC1404_2L" >
Severe Bleeding:</span></td><td>
<span id="PHC1404_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1404)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a positive tourniquet test?" id="42106004_2L" >
Tourniquet Test Positive:</span></td><td>
<span id="42106004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(42106004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Increase in Hematocrit and concurrent with rapid decrease in platelet count. This is an interpretation based upon the lab values for Hematocrit and platelet count." id="PHC1456_2L" >
Increasing Hematocrit and Decreased Platelet:</span></td><td>
<span id="PHC1456_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1456)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have severe organ involvement (liver, heart, neurologic-central nervous system)?" id="PHC1405_2L" >
Severe Organ Involvement:</span></td><td>
<span id="PHC1405_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1405)"
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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_13_2" name="Other Symptoms" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other signs and symptoms." id="568311_2L">
Other Symptoms:</span>
</td>
<td>
<span id="568311_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(568311)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25_2" name="Epi-Link" isHidden="F" classType="subSect" >
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV148_2L" title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility.">Is this person associated with a day care facility?:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV148)" styleId="INV148_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV149_2L" title="Indicates whether the subject of the investigation was food handler.">Is this person a food handler?:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV149)" styleId="INV149_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>

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
Where was the disease acquired:</span></td><td>
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
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_ARBO_UI_14_2" name="Binational Reporting" isHidden="F" classType="subSect" >

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
<nedss:container id="GA5101_2" name="Risk Factors and Transmission" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person fell ill with arboviral illness that was likely acquired due to work with infectious agents in a laboratory setting." id="ARB003_2L" >
Lab Acquired:</span></td><td>
<span id="ARB003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Donors who have been identified as having a WNV infection through routine blood donation screening by the blood collection agency. May or may not be symptomatic." id="ARB013_2L" >
Identified By Blood Donor Screening:</span></td><td>
<span id="ARB013_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB013)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person who fell ill with arboviral illness and reported that they had donated blood sometime within the last 30 days prior to onset." id="ARB005_2L" >
Blood Donor:</span></td><td>
<span id="ARB005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of blood donation" id="ARB014_2L">Date of Donation:</span>
</td><td>
<span id="ARB014_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB014)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person who fell ill with arboviral illness and reported that they had received a blood transfusion sometime within the last 30 days prior to onset." id="ARB006_2L" >
Blood Transfusion Received:</span></td><td>
<span id="ARB006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person who fell ill with arboviral illness and reported that they had donated an organ sometime within the last 30 days prior to onset." id="ARB007_2L" >
Organ Donor:</span></td><td>
<span id="ARB007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person who fell ill with arboviral illness and reported that they had received an organ transplant sometime within the last 30 days prior to onset." id="ARB008_2L" >
Organ Transplant Received:</span></td><td>
<span id="ARB008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Person who fell ill with arboviral illness and reported that they were breast feeding or breast fed prior to the illness onset." id="ARB009_2L" >
Breast Fed Infant:</span></td><td>
<span id="ARB009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Other Arboviral unusual and rare disease transmission modes. This data element is used to differentiate the In Utero (Transplacental) vs. Perinatal. This data element also can be used to capture more than one transmission mode. Other Arboviral data elements such as blood donation, blood transfusion, lab acquired, organ transplant are not included in this value set as they exists as data elements in the current guide as risk factors." id="INV157_2L" >
Other Arboviral Disease Transmission Mode:</span></td><td>
<span id="INV157_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV157)"
codeSetNm="PHVS_TRANSMISSIONMODE_ARBOVIRUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2_2" name="Case Status" isHidden="F" classType="subSect" >

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Identifies if the CDC Program can publish data for this case." id="ARB011_2L" >
CDC Publish Indicator:</span></td><td>
<span id="ARB011_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB011)"
codeSetNm="YN"/>
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA10000_2" name="Hidden Questions from v1.2" isHidden="T" classType="subSect" >
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="ARB015_2L" title="Information on whether the specimen was tested in public health labs or exclusively in commercial laboratories.">Lab Testing By:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(ARB015)" styleId="ARB015_2"><html:optionsCollection property="codedValue(PHVS_PUBLICPRIVATELAB_NND)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="ARB010_2L" title="Infant that was born to a mother who had a WNV illness/infection during their pregnancy.">Infected In Utero:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(ARB010)" styleId="ARB010_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="ARB004_2L" title="Non-Lab Occupationally Acquired. Indicates possible infection in an occupational setting that is not a laboratory.">Non Lab Acquired:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(ARB004)" styleId="ARB004_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="ARB012_2L" title="Did the patient suffer Acute Flaccid Paralysis?">Acute Flaccid Paralysis (AFP):</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(ARB012)" styleId="ARB012_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
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
