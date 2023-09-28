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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Clinical Data","Diagnostic Tests","Hepatitis D Infection","Contact with Case","Sexual and Drug Exposures","Exposures Prior to Onset","Hepatitis Treatment","Vaccination History","Contact Investigation"};
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
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176_2L">Date CDC was First Verbally Notified of This Case:</span>
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
HEP B DNA/NAT Result:</span></td><td>
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Note: The question regarding a negative hepatitis-related tests refers to the condition being reported. If this is an acute hepatitis B case, indicate if the patient had a negative hepatitis B-related test in the previous 6 months.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a negative hepatitis-related test in the previous 6 months? For Hep B: Did patient have a negative HBsAg test in the previous 6 months? For Hep C: Did patient have a negative HCV antibody test in the previous 6 months?" id="INV832_2L" >
Did the patient have a negative hepatitis-related test in the previous 6 months?:</span></td><td>
<span id="INV832_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV832)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the patient had a previous negative hepatitis test within 6 months, enter the date of the test." id="INV843_2L">Verified Test Date:</span>
</td><td>
<span id="INV843_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV843)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_2_2" name="Hepatitis D Infection" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient tested for Hepatitis D" id="INV840_2L" >
Was the patient tested for hepatitis D?:</span></td><td>
<span id="INV840_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV840)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a co-infection with Hepatitis D?" id="INV831_2L" >
Did the patient have a co-infection with hepatitis D?:</span></td><td>
<span id="INV831_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV831)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_5_2" name="Contact with a Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the time period prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis virus infection? For hepatitis B, the time period is 6 weeks to 6 months. For hepatitis C, it is 2 weeks to 6 months." id="INV602_2L" >
During the time period prior to onset, was patient a contact of a case?:</span></td><td>
<span id="INV602_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV602)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_7_2" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type sexual?" id="INV603_5_2L" >
Sexual (Contact Type):</span></td><td>
<span id="INV603_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV603_5)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a contact of a confirmed or suspected case, was the contact type  household non-sexual)?" id="INV603_3_2L" >
Household (Non-Sexual) (Contact Type):</span></td><td>
<span id="INV603_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV603_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" id="INV603_6_2L" >
Other (Contact Type):</span></td><td>
<span id="INV603_6_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If contact type with a confirmed or suspected case was 'other', specify other contact type." id="INV897_2L">
Other Contact Type (Specify):</span>
</td>
<td>
<span id="INV897_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV897)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_9_2" name="Sexual Exposures in Prior 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Please enter the sexual preference of the patient." id="INV592_2L" >
What is the sexual preference of the patient?:</span></td><td>
<span id="INV592_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV592)"
codeSetNm="PHVS_SEXUALPREFERENCE_NETSS"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Note: If 0 is selected on the form, enter 0; if 1 is selected on the form, enter 1; if 2-5 is selected on the form, enter 2; if &gt;5 is selected on the form, enter 6.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 6 months before symptom onset, how many:</span></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV605_2L" title="During the 6 months prior to the onset of symptoms, number of male sex partners the person had.">
Male Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV605_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV605)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV606_2L" title="During the 6 months prior to the onset of symptoms, number of female sex partners the person had.">
Female Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV606_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV606)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient ever treated for a sexually transmitted disease?" id="INV653b_2L" >
Was the patient ever treated for a sexually transmitted disease?:</span></td><td>
<span id="INV653b_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV653b)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV654_2L" title="If the subject was ever treated for a sexually-transmitted diases, in what year was the most recent treatment?">
If yes, in what year was the most recent treatment?:</span>
</td><td>
<span id="INV654_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV654)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_12_2" name="Blood Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;During the time period prior to onset, did the patient:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient undergo hemodialysis? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV583_2L" >
Undergo Hemodialysis:</span></td><td>
<span id="INV583_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV583)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have an accidental stick or puncture with a needle or other object contaminated with blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV655_2L" >
Have an Accidental Stick or Puncture With a Needle or Other Object Contaminated With Blood:</span></td><td>
<span id="INV655_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV655)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive blood or blood products (transfusion)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV580_2L" >
Receive Blood or Blood Products (Transfusion):</span></td><td>
<span id="INV580_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV580)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the subject began receiving blood or blood products (transfusion) prior to symptom onset. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV614_2L">If Yes, Date of Transfusion:</span>
</td><td>
<span id="INV614_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV614)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive any IV infusions and/or injections in an outpatient setting? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV620_2L" >
Receive Any IV Infusions and/or Injections in the Outpatient Setting:</span></td><td>
<span id="INV620_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV620)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have other exposure to someone else's blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV617_2L" >
Have Other Exposure to Someone Else's Blood:</span></td><td>
<span id="INV617_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV617)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="If patient had exposure to someone else's blood prior to symptom onset, specify the other blood exposure." id="INV898_2L">
Other Blood Exposure (Specify):</span>
</td>
<td>
<span id="INV898_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV898)"  />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient employed in a medical or dental field involving direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV590_2L" >
Was the patient employed in a medical or dental field involving contact with human blood?:</span></td><td>
<span id="INV590_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV590)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Subject's frequency of blood contact as an employee in a medical or dental field involving direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months" id="INV594_2L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV594_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV594)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the subject employed as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months.  For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV595_2L" >
Was the patient employed as a public safety worker having direct contact with human blood?:</span></td><td>
<span id="INV595_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV595)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Subject's frequency of blood contact as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV596_2L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV596_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV596)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_16_2" name="Tattooing/Drugs/Piercing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the time period prior to onset:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive a tattoo? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV597_2L" >
Did the patient receive a tattoo?:</span></td><td>
<span id="INV597_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV597)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV598_2L" title="Where was the tattooing performed (check all that apply)">
Where was the tattooing performed (check all that apply)?:</span></td><td>
<span id="INV598_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV598)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="specify other location(s) where the patient received a tattoo" id="INV900_2L">
Other Location(s) Tattoo Received:</span>
</td>
<td>
<span id="INV900_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV900)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the timeperiod prior to the onset of symptoms, did the subject inject drugs?" id="INV607_2L" >
Inject Drugs Not Prescribed By a Doctor:</span></td><td>
<span id="INV607_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV607)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?" id="INV608_2L" >
Use Street Drugs But Not Inject:</span></td><td>
<span id="INV608_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV608)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any part of their body pierced (other than ear)?" id="INV622_2L" >
Did the patient have any part of their body pierced (other than ear)?:</span></td><td>
<span id="INV622_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV622)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV623_2L" title="Where was the piercing performed (check all that apply)">
Where was the piercing performed (check all that apply)?:</span></td><td>
<span id="INV623_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV623)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other location(s) where the patient received a piercing" id="INV899_2L">
Other Location(s) Piercing Received:</span>
</td>
<td>
<span id="INV899_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV899)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_18_2" name="Other Healthcare Exposure" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have dental work or oral surgery? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV633_2L" >
Did the patient have dental work or oral surgery?:</span></td><td>
<span id="INV633_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV633)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have surgery (other than oral surgery)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV634_2L" >
Did the patient have surgery (other than oral surgery)?:</span></td><td>
<span id="INV634_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV634)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient hospitalized? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV635_2L" >
Was the patient hospitalized?:</span></td><td>
<span id="INV635_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV635)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient a resident of a long-term care facility? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV636_2L" >
Was the patient a resident of a long-term care facility?:</span></td><td>
<span id="INV636_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV636)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient incarcerated for longer than 24 hours? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV637_2L" >
Was the patient incarcerated for longer than 24 hours?:</span></td><td>
<span id="INV637_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV637)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_19_2" name="Incarceration Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, indicate if the incarceration type was prison." id="INV638_3_2L" >
Prison:</span></td><td>
<span id="INV638_3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV638_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, what type of facility?" id="INV638_1_2L" >
Jail:</span></td><td>
<span id="INV638_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV638_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, indicate the type of facility?" id="INV638_2_2L" >
Juvenile Facility:</span></td><td>
<span id="INV638_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV638_2)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_20_2" name="Incarceration More than 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During his/her lifetime, was the patient EVER incarcerated for more than 6 months?" id="INV639_2L" >
Was the patient ever incarcerated for longer than 6 months?:</span></td><td>
<span id="INV639_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV639)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV640_2L" title="Enter the 4-digit year the patient was most recently incarcerated for longer than 6 months.">
If yes, what year was the most recent incarceration?:</span>
</td><td>
<span id="INV640_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV640)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV641_2L" title="If the patient was incarcerated for longer than 6 months, enter the length of time of incarceration in months">
If yes, for how long (answer in months)?:</span>
</td><td>
<span id="INV641_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV641)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_22_2" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the subject ever received medication for the type of Hepatitis being reported?" id="INV652_2L" >
Has the patient received medication for the type of hepatitis being reported?:</span></td><td>
<span id="INV652_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV652)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_24_2" name="Hepatitis B Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient ever receive hepatitis B vaccine?" id="VAC126_2L" >
Did the patient ever receive hepatitis B vaccine?:</span></td><td>
<span id="VAC126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC132_2L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td><td>
<span id="VAC132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC132)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC142b_2L" title="Enter the 4 digit year of when the last dose of vaccine for the condition being investigated was received.">
In what year was the last dose received?:</span>
</td><td>
<span id="VAC142b_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC142b)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient tested for antibody to HBsAg (anti-HBS) within 1-2 months after the last does of vaccine?" id="HEP190_2L" >
Was patient tested for antibody to HBsAg (anti-HBs) within 1-2 mos after last dose?:</span></td><td>
<span id="HEP190_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(HEP190)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the serum anti-HBs &gt;= 10ml U/ml?  (Answer 'Yes' if lab result reported as positive or reactive.)" id="HEP191_2L" >
Was the serum anti-HBs &gt;= 10ml U/ml?:</span></td><td>
<span id="HEP191_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(HEP191)"
codeSetNm="YNU"/>
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
