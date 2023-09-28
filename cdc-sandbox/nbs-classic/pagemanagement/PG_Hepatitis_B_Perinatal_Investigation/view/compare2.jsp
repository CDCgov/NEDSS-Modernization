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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Clinical Data","Diagnostic Tests","Perinatal Hep B Mother Information","Infant Information","Contact Investigation"};
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
<span title="Country of Birth" id="DEM126_2L" >
Country of Birth:</span></td><td>
<span id="DEM126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

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
<nedss:container id="NBS_INV_HEP_UI_1_2" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_INV_HEP_UI_2_2" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_HEP_UI_2_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_HEP_UI_2_2">
<tr id="patternNBS_INV_HEP_UI_2_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_HEP_UI_2_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_HEP_UI_2_2">
<tr id="nopatternNBS_INV_HEP_UI_2_2" class="odd" style="display:none">
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
<nedss:container id="NBS_INV_HEP_UI_3_2" name="Binational Reporting" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_INV_HEP_UI_5_2" name="Reason for Testing" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV575_2L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span></td><td>
<span id="INV575_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV575)"
codeSetNm="PHVS_REASONFORTEST_HEPATITIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Other reason(s) the patient was tested for hepatitis." id="INV901_2L">
Other Reason for Testing:</span>
</td>
<td>
<span id="INV901_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV901)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Clinical Data" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136_2L">Diagnosis Date:</span>
</td><td>
<span id="INV136_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV136)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient symptomatic for the illness of interest?" id="INV576_2L" >
Is patient symptomatic?:</span></td><td>
<span id="INV576_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

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
<span title="At the time of diagnosis, was the patient jaundiced?" id="INV578_2L" >
Was the patient jaundiced?:</span></td><td>
<span id="INV578_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV578)"
codeSetNm="YNU"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient aware s/he had hepatitis prior to lab testing?" id="INV650_2L" >
Was the patient aware s/he had hepatitis prior to lab testing?:</span></td><td>
<span id="INV650_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV650)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the subject have a provider of care for the condition?  This is any healthcare provider that monitors or treats the patient for the condition." id="INV651_2L" >
Does the patient have a provider of care for hepatitis?:</span></td><td>
<span id="INV651_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV651)"
codeSetNm="YNU"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate whether the patient has diabetes" id="INV887_2L" >
Does the patient have diabetes?:</span></td><td>
<span id="INV887_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV887)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If subject has diabetes, date of diabetes diagnosis." id="INV842_2L">Diabetes Diagnosis Date:</span>
</td><td>
<span id="INV842_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV842)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_6_2" name="Liver Enzyme Levels at Time of Diagnosis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="1742_6_2L" title="Enter the ALT/SGPT result">
ALT [SGPT] Result:</span>
</td><td>
<span id="1742_6_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(1742_6)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of specimen collection of the ALT liver enzyme lab test result." id="INV826_2L">Specimen Collection Date (ALT):</span>
</td><td>
<span id="INV826_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV826)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV827_2L" title="Enter the upper limit normal value (numeric) for the ALT liver enzyme test result.">
Test Result Upper Limit Normal (ALT):</span>
</td><td>
<span id="INV827_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV827)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="11920_8_2L" title="Enter patient's AST/SGOT result">
AST [SGOT] Result:</span>
</td><td>
<span id="11920_8_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(11920_8)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of specimen collection of the AST liver enzyme lab test result." id="INV826b_2L">Specimen Collection Date (AST):</span>
</td><td>
<span id="INV826b_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV826b)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV827b_2L" title="Enter the upper limit normal value (numeric) for the AST liver enzyme test result.">
Test Result Upper Limit Normal (AST):</span>
</td><td>
<span id="INV827b_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV827b)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_8_2" name="Diagnostic Test Results" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of specimen collection for total anti-HAV test result" id="LP38316_3_DT_2L">Specimen Collection Date (anti-HAV):</span>
</td><td>
<span id="LP38316_3_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38316_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total antibody to Hepatitis A virus result" id="LP38316_3_2L" >
total anti-HAV Result:</span></td><td>
<span id="LP38316_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38316_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of IgM anti-HAV test result" id="LP38318_9_DT_2L">Specimen Collection Date (IgM anti-HAV):</span>
</td><td>
<span id="LP38318_9_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38318_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM antibody to Hepatitis A virus result" id="LP38318_9_2L" >
IgM anti-HAV Result:</span></td><td>
<span id="LP38318_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38318_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen Collection Date for HBsAg test" id="LP38331_2_DT_2L">Specimen Collection Date (HBsAg):</span>
</td><td>
<span id="LP38331_2_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38331_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B virus surface antigen result" id="LP38331_2_2L" >
HBsAg Result:</span></td><td>
<span id="LP38331_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38331_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of total anti-HBc test result" id="LP38323_9_DT_2L">Specimen Collection Date (total anti-HBc):</span>
</td><td>
<span id="LP38323_9_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38323_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="total antibody to Hepatitis B core antigen result" id="LP38323_9_2L" >
total anti-HBc Result:</span></td><td>
<span id="LP38323_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38323_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of IgM anti-HBc test result" id="LP38325_4_DT_2L">Specimen Collection Date (IgM anti-HBc):</span>
</td><td>
<span id="LP38325_4_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38325_4_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM antibody to hepatitis B core antigen result" id="LP38325_4_2L" >
IgM anti-HBc Result:</span></td><td>
<span id="LP38325_4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38325_4)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of HBV DNA/NAT test" id="LP38320_5_DT_2L">Specimen Collection Date (HEP B DNA/NAT):</span>
</td><td>
<span id="LP38320_5_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38320_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B DNA-containing test (Nucleic Acid Test (NAT)) result" id="LP38320_5_2L" >
HEP B DNA/(NAT) Result:</span></td><td>
<span id="LP38320_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38320_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of HBeAg test" id="LP38329_6_DT_2L">Specimen Collection Date (HBeAg):</span>
</td><td>
<span id="LP38329_6_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38329_6_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B virus e Antigen result" id="LP38329_6_2L" >
HBeAg Result:</span></td><td>
<span id="LP38329_6_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38329_6)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of total anti-HCV" id="LP38332_0_DT_2L">Specimen Collection Date (total anti-HCV):</span>
</td><td>
<span id="LP38332_0_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38332_0_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis C virus Ab result" id="LP38332_0_2L" >
total anti-HCV Result:</span></td><td>
<span id="LP38332_0_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38332_0)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the antibody test to Hepatitis C Virus (anti-HCV) was positive or negative, enter the signal to cut-off ratio." id="INV841_2L">
anti-HCV signal to cut-off ratio:</span>
</td>
<td>
<span id="INV841_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV841)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for supplemental anti-HCV assay" id="5199_5_DT_2L">Specimen Collection Date (supplemental anti-HCV assay):</span>
</td><td>
<span id="5199_5_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(5199_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="supplemental anti-HCV assay (e.g. RIBA) result" id="5199_5_2L" >
Supplemental anti-HCV Assay Result:</span></td><td>
<span id="5199_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(5199_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for HCV RNA test" id="LP38335_3_DT_2L">Specimen Collection Date (HCV RNA):</span>
</td><td>
<span id="LP38335_3_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38335_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis C virus RNA result" id="LP38335_3_2L" >
HCV RNA Result:</span></td><td>
<span id="LP38335_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38335_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for total anti-HDV test" id="LP38345_2_DT_2L">Specimen Collection Date (total anti-HDV):</span>
</td><td>
<span id="LP38345_2_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38345_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total Hepatitis D virus Ab result" id="LP38345_2_2L" >
anti-HDV Result:</span></td><td>
<span id="LP38345_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38345_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for total anti-HEV test" id="LP38350_2_DT_2L">Specimen Collection Date (total anti-HEV):</span>
</td><td>
<span id="LP38350_2_DT_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38350_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total Hepatitis E virus Ab result" id="LP38350_2_2L" >
anti-HEV Result:</span></td><td>
<span id="LP38350_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP38350_2)"
codeSetNm="PNU"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_3_2" name="Mother Race and Ethnicity" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page." id="NBS387_2L" >
Next of Kin Relationship:</span></td><td>
<span id="NBS387_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS387)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="MTH157_2L" title="Enter the race of the patient's mother">
Race of Mother:</span></td><td>
<span id="MTH157_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(MTH157)"
codeSetNm="PHVS_RACECATEGORY_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If other race is selected for the mother, please specify" id="MTH171_2L">
Other Race for Mother (specify):</span>
</td>
<td>
<span id="MTH171_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH171)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Ethnicity of the patient's mother" id="MTH159_2L" >
Ethnicity of Mother:</span></td><td>
<span id="MTH159_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH159)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>
<!--skipping Hidden Text Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient's mother born outside of the United States?" id="MTH161_2L" >
Was Mother born outside of United States?:</span></td><td>
<span id="MTH161_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH161)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="What is the birth country of the mother" id="MTH109_2L" >
What is the birth country of the mother?:</span></td><td>
<span id="MTH109_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH109)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_4_2" name="Mother HBsAg Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the mother confirmed positive for the illness being reported prior to or at the time of delivery?" id="MTH162_2L" >
Was the mother confirmed positive prior to or at the time of delivery?:</span></td><td>
<span id="MTH162_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH162)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the mother confirmed positive for the illness being reported after delivery?" id="MTH163_2L" >
If no, was the mother confirmed positive after delivery?:</span></td><td>
<span id="MTH163_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH163)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of the mother's earliest postive test result" id="MTH164_2L">Date of Earliest Positive Test Result:</span>
</td><td>
<span id="MTH164_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH164)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_6_2" name="Hepatitis Containing Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the child ever received a vaccination for Hepatitis B?" id="VAC126_2L" >
Has the child ever received a vaccination for Hepatitis B?:</span></td><td>
<span id="VAC126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC132_2L" title="Total number of doses of vaccine the child received. Enter a numeric value based on the category on the form.">
How many doses of Hepatitis B vaccine did the child receive?:</span>
</td><td>
<span id="VAC132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC132)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_7_2" name="Vaccine Doses Received" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_HEPBP_7_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_HEPBP_7_2">
<tr id="patternNBS_INV_HEPBP_7_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_HEPBP_7_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_HEPBP_7_2">
<tr id="nopatternNBS_INV_HEPBP_7_2" class="odd" style="display:none">
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
<tr><td class="fieldName">
<span id="VAC120_2L" title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)">
Dose Number:</span>
</td><td>
<span id="VAC120_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the vaccine was administered" id="VAC103_2L">Date of Vaccination:</span>
</td><td>
<span id="VAC103_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC103)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_8_2" name="HBIG Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the patient ever received immune globulin for this condition?" id="VAC143_2L" >
Did the child receive hepatitis B immune globulin (HBIG)?:</span></td><td>
<span id="VAC143_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC143)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the child received the dose of HBIG." id="VAC144_2L">If yes, on what date did the child receive HBIG?:</span>
</td><td>
<span id="VAC144_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC144)"  />
</td></tr>
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
