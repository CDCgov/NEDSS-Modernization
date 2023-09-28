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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Inclusion Criteria","Comorbidities","Hospitalization","Clinical Signs and Symptoms","Complications","Treatments","Vaccination","Studies Details","SARS-COV-2 Testing","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here." id="NBS547_2L">
nCoV ID (If Available):</span>
</td>
<td>
<span id="NBS547_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS547)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of the person who abstracted the medical record." id="67153_7_2L">
Abstractor Name:</span>
</td>
<td>
<span id="67153_7_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(67153_7)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the information was abstracted from the medical record." id="NBS692_2L">Date of Abstraction:</span>
</td><td>
<span id="NBS692_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS692)"  />
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Pregnancy" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_UI_GA28002_2" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient less than 21 years old at illness onset?" id="NBS694_2L" >
Age &lt; 21:</span></td><td>
<span id="NBS694_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS694)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more" id="426000000_2L" >
Fever For At Least 24 Hrs:</span></td><td>
<span id="426000000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(426000000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin." id="NBS695_2L" >
Laboratory Markers of Inflammation:</span></td><td>
<span id="NBS695_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS695)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Evidence of clinically severe illness requiring hospitalization." id="434081000124108_2L" >
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span></td><td>
<span id="434081000124108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(434081000124108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?" id="56265001_2L" >
Cardiac:</span></td><td>
<span id="56265001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(56265001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?" id="90708001_2L" >
Renal:</span></td><td>
<span id="90708001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(90708001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="50043002_2L" >
Respiratory:</span></td><td>
<span id="50043002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(50043002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?" id="128480004_2L" >
Hematologic:</span></td><td>
<span id="128480004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(128480004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea)." id="119292006_2L" >
Gastrointestinal:</span></td><td>
<span id="119292006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(119292006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="95320005_2L" >
Dermatologic:</span></td><td>
<span id="95320005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(95320005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?" id="118940003_2L" >
Neurological:</span></td><td>
<span id="118940003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(118940003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether there is no alternative plausible diagnosis." id="NBS696_2L" >
No alternative plausible diagnosis:</span></td><td>
<span id="NBS696_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS696)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" id="NBS697_2L" >
Positive for current or recent SARS-COV-2 infection by lab testing?:</span></td><td>
<span id="NBS697_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS697)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was RT-PCR testing completed for this disease?" id="NBS709_2L" >
RT-PCR:</span></td><td>
<span id="NBS709_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS709)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was serology testing completed for this disease?" id="NBS710_2L" >
Serology:</span></td><td>
<span id="NBS710_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS710)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was antigen testing completed for this disease?" id="NBS711_2L" >
Antigen:</span></td><td>
<span id="NBS711_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS711)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" id="840546002_2L" >
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span></td><td>
<span id="840546002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(840546002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of first exposure to individual with confirmed illness within the 4 weeks prior." id="LP248166_3_2L">Date of first exposure within the 4 weeks prior:</span>
</td><td>
<span id="LP248166_3_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LP248166_3)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Exposure date unknown." id="NBS698_2L" >
Exposure Date Unknown:</span></td><td>
<span id="NBS698_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS698)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004_2" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS434_2L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td><td>
<span id="NBS434_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS434)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM255_2L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td><td>
<span id="DEM255_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM255)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="39156_5_2L" title="What is the patient's body mass index?">
BMI:</span>
</td><td>
<span id="39156_5_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(39156_5)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003_2" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of an immunocompromised condition?" id="370388006_2L" >
Immunosuppressive disorder or malignancy:</span></td><td>
<span id="370388006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient obese?" id="414916001_2L" >
Obesity:</span></td><td>
<span id="414916001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have type 1 diabetes?" id="46635009_2L" >
Type 1 diabetes:</span></td><td>
<span id="46635009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(46635009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have type 2 diabetes?" id="44054006_2L" >
Type 2 diabetes:</span></td><td>
<span id="44054006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(44054006)"
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Congenital heart disease" id="13213009_2L" >
Congenital heart disease:</span></td><td>
<span id="13213009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(13213009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have sickle cell disease?" id="417357006_2L" >
Sickle cell disease:</span></td><td>
<span id="417357006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(417357006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001_2L" >
Chronic Lung Disease:</span></td><td>
<span id="413839001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have any other congenital malformations?" id="276654001_2L" >
Other congenital malformations:</span></td><td>
<span id="276654001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(276654001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other congenital malformations." id="276654001_OTH_2L">
Specify other congenital malformations:</span>
</td>
<td>
<span id="276654001_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(276654001_OTH)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132_2L">Hospital Admission Date:</span>
</td><td>
<span id="INV132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133_2L">Hospital Discharge:</span>
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
<span title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" id="309904001_2L" >
Was patient admitted to ICU?:</span></td><td>
<span id="309904001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(309904001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of admission." id="NBS679_2L">ICU Admission Date:</span>
</td><td>
<span id="NBS679_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS679)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="74200_7_2L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td><td>
<span id="74200_7_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(74200_7)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patients Outcome" id="FDD_Q_1038_2L" >
Patients Outcome:</span></td><td>
<span id="FDD_Q_1038_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(FDD_Q_1038)"
codeSetNm="PATIENT_HOSP_STATUS_MIS"/>
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
<nedss:container id="NBS_UI_GA29007_2" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have preceding COVID-like illness?" id="NBS707_2L" >
Did patient have preceding COVID-like illness:</span></td><td>
<span id="NBS707_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS707)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of COVID-like illness symptom onset." id="NBS708_2L">Date of COVID-like illness symptom onset:</span>
</td><td>
<span id="NBS708_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS708)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016_2" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of MIS-C." id="INV136_2L">Diagnosis Date:</span>
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
<span title="Did the patient have fever?" id="386661006_2L" >
Fever Greater Than 38.0 C or 100.4 F:</span></td><td>
<span id="386661006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicates the date of fever onset" id="INV203_2L">Date of Fever Onset:</span>
</td><td>
<span id="INV203_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV203)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV202_2L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td><td>
<span id="INV202_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV202)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAR125_2L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td><td>
<span id="VAR125_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(VAR125)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017_2" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated troponin?" id="444931001_2L" >
Elevated troponin:</span></td><td>
<span id="444931001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(444931001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have elevated BNP or NT-proBNP?" id="414798009_2L" >
Elevated BNP or NT-proBNP:</span></td><td>
<span id="414798009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(414798009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018_2" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute kidney injury?" id="14350001000004108_2L" >
Acute kidney injury:</span></td><td>
<span id="14350001000004108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(14350001000004108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have renal failure?" id="42399005_2L" >
Renal failure:</span></td><td>
<span id="42399005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(42399005)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019_2" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patients illness include the symptom of cough?" id="49727002_2L" >
Cough:</span></td><td>
<span id="49727002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007_2L" >
Shortness of breath (dyspnea):</span></td><td>
<span id="267036007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience chest pain?" id="29857009_2L" >
Chest pain or tightness:</span></td><td>
<span id="29857009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pulmonary embolism?" id="59282003_2L" >
Pulmonary embolism:</span></td><td>
<span id="59282003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(59282003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020_2" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated D-dimers?" id="449830004_2L" >
Elevated D-dimers:</span></td><td>
<span id="449830004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(449830004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have thrombophilia?" id="234467004_2L" >
Thrombophilia:</span></td><td>
<span id="234467004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(234467004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have thrombocytopenia?" id="302215000_2L" >
Thrombocytopenia:</span></td><td>
<span id="302215000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(302215000)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021_2" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001_2L" >
Abdominal pain:</span></td><td>
<span id="21522001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject experience vomiting?" id="422400008_2L" >
Vomiting:</span></td><td>
<span id="422400008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(422400008)"
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
<span title="Did the patient have elevated bilirubin?" id="26165005_2L" >
Elevated bilirubin:</span></td><td>
<span id="26165005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(26165005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated liver enzymes?" id="707724006_2L" >
Elevated liver enzymes:</span></td><td>
<span id="707724006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(707724006)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022_2" name="Dermatologic" isHidden="F" classType="subSect" >

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
<span title="Did the patient have mucocutaneous lesions?" id="95346009_2L" >
Mucocutaneous lesions:</span></td><td>
<span id="95346009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(95346009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023_2" name="Neurological" isHidden="F" classType="subSect" >

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
<span title="Did the patient have altered mental status?" id="419284004_2L" >
Altered Mental Status:</span></td><td>
<span id="419284004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have syncope/near syncope?" id="NBS712_2L" >
Syncope/near syncope:</span></td><td>
<span id="NBS712_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS712)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have meningitis?" id="44201003_2L" >
Meningitis:</span></td><td>
<span id="44201003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(44201003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have encephalopathy?" id="81308009_2L" >
Encephalopathy?:</span></td><td>
<span id="81308009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(81308009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024_2" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have neck pain?" id="81680005_2L" >
Neck pain:</span></td><td>
<span id="81680005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(81680005)"
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
<span title="Did the patient have conjunctival injection?" id="193894004_2L" >
Conjunctival injection:</span></td><td>
<span id="193894004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(193894004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have periorbital edema?" id="49563000_2L" >
Periorbital edema:</span></td><td>
<span id="49563000_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(49563000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have cervical lymphadenopathy?" id="127086001_2L" >
Cervical lymphadenopathy:</span></td><td>
<span id="127086001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(127086001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002_2" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have arrhythmia?" id="698247007_2L" >
Arrhythmia:</span></td><td>
<span id="698247007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(698247007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="76281_5_2L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span></td><td>
<span id="76281_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(76281_5)"
codeSetNm="CARDIAC_ARRHYTHMIA_TYPE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate the type of arrhythmia." id="76281_5_2_OthL">Other Type of arrhythmia:</span></td>
<td> <span id="76281_5_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(76281_5Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have congestive heart failure?" id="ARB020_2L" >
Congestive Heart Failure:</span></td><td>
<span id="ARB020_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB020)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have myocarditis?" id="50920009_2L" >
Myocarditis:</span></td><td>
<span id="50920009_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(50920009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pericarditis?" id="3238004_2L" >
Pericarditis:</span></td><td>
<span id="3238004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(3238004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have liver failure?" id="59927004_2L" >
Liver failure:</span></td><td>
<span id="59927004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(59927004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?" id="NBS713_2L" >
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span></td><td>
<span id="NBS713_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS713)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005_2L" >
ARDS:</span></td><td>
<span id="67782005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(67782005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pneumonia?" id="233604007_2L" >
Pneumonia:</span></td><td>
<span id="233604007_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(233604007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did patient develop CVA or stroke?" id="ARB021_2L" >
CVA or Stroke:</span></td><td>
<span id="ARB021_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB021)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have encephalitis?" id="31646008_2L" >
Encephalitis or aseptic meningitis:</span></td><td>
<span id="31646008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(31646008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have septic shock?" id="27942005_2L" >
Shock:</span></td><td>
<span id="27942005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(27942005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have hypotension?" id="45007003_2L" >
Hypotension:</span></td><td>
<span id="45007003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(45007003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004_2" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive low flow nasal cannula?" id="466713001_2L" >
Low flow nasal cannula:</span></td><td>
<span id="466713001_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(466713001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive high flow nasal cannula?" id="426854004_2L" >
High flow nasal cannula:</span></td><td>
<span id="426854004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(426854004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive non-invasive ventilation?" id="428311008_2L" >
Non-invasive ventilation:</span></td><td>
<span id="428311008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(428311008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive intubation?" id="52765003_2L" >
Intubation:</span></td><td>
<span id="52765003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(52765003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive mechanical ventilation?" id="NBS673_2L" >
Mechanical ventilation:</span></td><td>
<span id="NBS673_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive ECMO?" id="233573008_2L" >
ECMO:</span></td><td>
<span id="233573008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" id="NBS699_2L" >
Vasoactive medications:</span></td><td>
<span id="NBS699_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS699)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the vasoactive medications the patient received." id="NBS700_2L">
Specify vasoactive medications:</span>
</td>
<td>
<span id="NBS700_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS700)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?" id="ARB040_2L" >
Steroids:</span></td><td>
<span id="ARB040_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB040)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" id="ARB045_2L" >
Immune modulators:</span></td><td>
<span id="ARB045_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(ARB045)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify immune modulators the patient received." id="373244000_2L">
Specify immune modulators:</span>
</td>
<td>
<span id="373244000_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(373244000)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" id="372560006_2L" >
Antiplatelets:</span></td><td>
<span id="372560006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(372560006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify antiplatelets the patient received." id="372560006_OTH_2L">
Specify antiplatelets:</span>
</td>
<td>
<span id="372560006_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(372560006_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" id="372862008_2L" >
Anticoagulation:</span></td><td>
<span id="372862008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(372862008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify anticoagulation the patient received?" id="372862008_OTH_2L">
Specify anticoagulation:</span>
</td>
<td>
<span id="372862008_OTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(372862008_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive hemodialysis?" id="302497006_2L" >
Dialysis:</span></td><td>
<span id="302497006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(302497006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive first intravenous immunoglobulin?" id="NBS701_2L" >
First IVIG:</span></td><td>
<span id="NBS701_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS701)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive second intravenous immunoglobulin?" id="NBS702_2L" >
Second IVIG:</span></td><td>
<span id="NBS702_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS702)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001_2" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the patient received a COVID-19 vaccine?" id="VAC126_2L" >
Has the patient received a COVID-19 vaccine?:</span></td><td>
<span id="VAC126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine)." id="VAC132_CD_2L" >
If yes, how many doses?:</span></td><td>
<span id="VAC132_CD_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC132_CD)"
codeSetNm="VAC_DOSE_NUM_MIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the first vaccine dose was received." id="NBS791_2L">Vaccine Dose 1 Received Date:</span>
</td><td>
<span id="NBS791_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS791)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the second vaccine dose was received." id="NBS792_2L">Vaccine Dose 2 Received Date:</span>
</td><td>
<span id="NBS792_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS792)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="COVID-19 vaccine manufacturer" id="VAC107_2L" >
COVID-19 vaccine manufacturer:</span></td><td>
<span id="VAC107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(VAC107)"
codeSetNm="MIS_VAC_MFGR"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="COVID-19 vaccine manufacturer" id="VAC107_2_OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td> <span id="VAC107_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(VAC107Oth)"/></td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27008_2" name="Blood Test Results" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS714_2L" title="If the patient had a fibrinogen result, enter the highest value.">
Fibrinogen Highest Value:</span>
</td><td>
<span id="NBS714_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS714)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a fibrinogen test, enter the unit of measure associated with the value." id="NBS715_2L" >
Fibrinogen Units:</span></td><td>
<span id="NBS715_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS715)"
codeSetNm="FIBRINOGEN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a fibrinogen result, indicate the interpretation." id="NBS716_2L" >
Fibrinogen Interpretation:</span></td><td>
<span id="NBS716_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS716)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS717_2L" title="If the patient had a CRP result, enter the highest value.">
CRP Highest Value:</span>
</td><td>
<span id="NBS717_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS717)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a CRP result enter the unit of measure associated with the value." id="NBS728_2L" >
CRP Unit:</span></td><td>
<span id="NBS728_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS728)"
codeSetNm="CRP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a CRP result, indicate the interpretation." id="NBS729_2L" >
CRP Interpretation:</span></td><td>
<span id="NBS729_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS729)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS730_2L" title="If the patient had a ferritin result, enter the highest value.">
Ferritin Test Highest Value:</span>
</td><td>
<span id="NBS730_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS730)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a ferritin result enter the unit of measure associated with the value." id="NBS731_2L" >
Ferritin Units:</span></td><td>
<span id="NBS731_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS731)"
codeSetNm="FERRITIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a ferritin result, indicate the interpretation." id="NBS732_2L" >
Ferritin Interpretation:</span></td><td>
<span id="NBS732_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS732)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS733_2L" title="If the patient had a troponin result, enter the highest value.">
Troponin Highest Value:</span>
</td><td>
<span id="NBS733_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS733)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a troponin result, enter the unit of measure associated with the value." id="NBS734_2L" >
Troponin Units:</span></td><td>
<span id="NBS734_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS734)"
codeSetNm="TROPONIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a troponin result, indicate the interpretation." id="NBS735_2L" >
Troponin Interpretation:</span></td><td>
<span id="NBS735_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS735)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS736_2L" title="If the patient had a BNP result, enter the highest value.">
BNP Highest Value:</span>
</td><td>
<span id="NBS736_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS736)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a BNP result, enter the unit of measure associated with the value." id="NBS737_2L" >
BNP Units:</span></td><td>
<span id="NBS737_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS737)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a BNP result, indicate the interpretation." id="NBS738_2L" >
BNP Interpretation:</span></td><td>
<span id="NBS738_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS738)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS739_2L" title="If the patient had a NT-proBNP result, enter the highest value.">
NT-proBNP Test Highest Value:</span>
</td><td>
<span id="NBS739_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS739)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value." id="NBS740_2L" >
NT-proBNP Units:</span></td><td>
<span id="NBS740_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS740)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a NT-proBNP result, indicate the interpretation." id="NBS741_2L" >
NT-proBNP Interpretation:</span></td><td>
<span id="NBS741_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS741)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS742_2L" title="If the patient had a D-dimer result, enter the highest value.">
D-dimer Highest Value:</span>
</td><td>
<span id="NBS742_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS742)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a D-dimer result, enter the unit of measure associated with the value." id="NBS743_2L" >
D-dimer Units:</span></td><td>
<span id="NBS743_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS743)"
codeSetNm="D_DIMER_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a D-dimer result, indicate the interpretation." id="NBS744_2L" >
D-dimer Interpretation:</span></td><td>
<span id="NBS744_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS744)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS745_2L" title="If the patient had a IL-6 result, enter the highest value.">
IL-6 Highest Value:</span>
</td><td>
<span id="NBS745_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS745)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a IL-6 result, enter the unit of measure associated with the value." id="NBS746_2L" >
IL-6 Unit:</span></td><td>
<span id="NBS746_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS746)"
codeSetNm="IL_6_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a IL-6 result, indicate the interpretation." id="NBS747_2L" >
IL-6 Interpretation:</span></td><td>
<span id="NBS747_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS747)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS748_2L" title="If the patient had a serum white blood count result, enter the test interpretation.">
Serum White Blood Count Highest Value:</span>
</td><td>
<span id="NBS748_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS748)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS749_2L" title="If the patient had a serum white blood count result, enter the lowest value.">
Serum White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS749_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS749)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a serum white blood count result, enter the unit of measure associated with the value." id="NBS750_2L" >
Serum White Blood Count Units:</span></td><td>
<span id="NBS750_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS750)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS751_2L" title="If the patient had a platelets result, enter the highest value.">
Platelets Highest Value:</span>
</td><td>
<span id="NBS751_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS751)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS752_2L" title="If the patient had a platelets result, enter the lowest value.">
Platelets Lowest Value:</span>
</td><td>
<span id="NBS752_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS752)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a platelets result, enter the unit of measure associated with the value." id="NBS753_2L" >
Platelets Units:</span></td><td>
<span id="NBS753_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS753)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS754_2L" title="If the patient had a neutrophils result, enter the highest value.">
Neutrophils Highest Value:</span>
</td><td>
<span id="NBS754_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS754)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS755_2L" title="If the patient had a neutrophils result, enter the lowest value.">
Neutrophils Lowest Value:</span>
</td><td>
<span id="NBS755_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS755)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a neutrophils result, enter the unit of measure associated with the value." id="NBS756_2L" >
Neutrophils Units:</span></td><td>
<span id="NBS756_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS756)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS757_2L" title="If the patient had a lymphocytes result, enter the highest value.">
Lymphocytes Highest Value:</span>
</td><td>
<span id="NBS757_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS757)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS758_2L" title="If the patient had a lymphocytes result, enter the lowest value.">
Lymphocytes Lowest Value:</span>
</td><td>
<span id="NBS758_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS758)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a lymphocytes result, enter the unit of measure associated with the value." id="NBS759_2L" >
Lymphocytes Units:</span></td><td>
<span id="NBS759_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS759)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS760_2L" title="If the patient had a bands test result, enter the highest value.">
Bands Test Highest Value:</span>
</td><td>
<span id="NBS760_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS760)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS761_2L" title="If the patient had a band test result, enter the lowest value.">
Bands Test Lowest Value:</span>
</td><td>
<span id="NBS761_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS761)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a bands test result, enter the unit of measure associated with the value." id="NBS762_2L" >
Bands Test Units:</span></td><td>
<span id="NBS762_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS762)"
codeSetNm="BANDS_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27009_2" name="CSF Studies" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS763_2L" title="If the patient had a white blood count result, enter the highest value.">
White Blood Count Highest Value:</span>
</td><td>
<span id="NBS763_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS763)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS764_2L" title="If the patient had a white blood count result, enter the lowest value.">
White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS764_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS764)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a white blood count result, enter the unit of measure associated with the value." id="NBS765_2L" >
White Blood Count Units:</span></td><td>
<span id="NBS765_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS765)"
codeSetNm="WBC_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS766_2L" title="If the patient had a protein test result, enter the highest value.">
Protein Test Highest Value:</span>
</td><td>
<span id="NBS766_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS766)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS767_2L" title="If the patient had a protein test result, enter the lowest value.">
Protein Test Lowest Value:</span>
</td><td>
<span id="NBS767_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS767)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a protein test result, enter the unit of measure associated with the value." id="NBS768_2L" >
Protein Test Units:</span></td><td>
<span id="NBS768_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS768)"
codeSetNm="PROTEIN_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS769_2L" title="If the patient had a glucose result, enter the highest value.">
Glucose Highest Value:</span>
</td><td>
<span id="NBS769_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS769)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS770_2L" title="If the patient had a glucose test result, enter the lowest value.">
Glucose Lowest Value:</span>
</td><td>
<span id="NBS770_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS770)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a glucose result, enter the unit of measure associated with the value." id="NBS771_2L" >
Glucose Units:</span></td><td>
<span id="NBS771_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS771)"
codeSetNm="GLUCOSE_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27010_2" name="Urinalysis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS772_2L" title="If the patient had a urine while blood count result, enter the highest value.">
Urine White Blood Count Highest Value:</span>
</td><td>
<span id="NBS772_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS772)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS773_2L" title="If the patient had a urine white blood count result, enter the lowest value.">
Urine White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS773_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS773)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a urine white blood count result, enter the unit of measure associated with the value." id="NBS774_2L" >
Urine White Blood Count Units:</span></td><td>
<span id="NBS774_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS774)"
codeSetNm="URINE_WBC_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27011_2" name="Echocardiogram" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="40701008_2L" title="Indicate echocardiogram result.">
Echocardiogram result:</span></td><td>
<span id="40701008_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(40701008)"
codeSetNm="ECHOCARDIOGRAM_RESULT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate echocardiogram result." id="40701008_2_OthL">Other Echocardiogram result:</span></td>
<td> <span id="40701008_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(40701008Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicate max coronary artery Z-score." id="373065002_2L">
Max coronary artery Z-score:</span>
</td>
<td>
<span id="373065002_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(373065002)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate cardiac dysfunction type." id="NBS703_2L" >
Cardiac dysfunction type:</span></td><td>
<span id="NBS703_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS703)"
codeSetNm="CARDIAC_DYSFUNCTION_TYPE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date of first test showing coronary artery aneurysm or dilatation." id="NBS704_2L">Date of first test showing coronary artery aneurysm or dilatation:</span>
</td><td>
<span id="NBS704_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS704)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate mitral regurgitation type." id="18113_1_2L" >
Mitral regurgitation type:</span></td><td>
<span id="18113_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(18113_1)"
codeSetNm="MITRAL_REGURG_TYPE"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27012_2" name="Abdominal Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was abdominal imaging was completed for this patient?" id="441987005_2L" >
Was abdominal imaging done?:</span></td><td>
<span id="441987005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(441987005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS705_2L" title="Indicate abdominal imaging type.">
Abdominal imaging type:</span></td><td>
<span id="NBS705_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS705)"
codeSetNm="ABD_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS706_2L" title="Indicate abdominal imaging results.">
Abdominal imaging results:</span></td><td>
<span id="NBS706_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS706)"
codeSetNm="ABD_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate abdominal imaging results." id="NBS706_2_OthL">Other Abdominal imaging results:</span></td>
<td> <span id="NBS706_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(NBS706Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27013_2" name="Chest Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was chest imaging completed for this patient?" id="413815006_2L" >
Was chest imaging done?:</span></td><td>
<span id="413815006_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(413815006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="LAB677_2L" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so.">
Type of Chest Study:</span></td><td>
<span id="LAB677_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(LAB677)"
codeSetNm="CHEST_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="LAB678_2L" title="Result of chest diagnostic testing">
Result of Chest Study:</span></td><td>
<span id="LAB678_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(LAB678)"
codeSetNm="CHEST_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Result of chest diagnostic testing" id="LAB678_2_OthL">Other Result of Chest Study:</span></td>
<td> <span id="LAB678_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB678Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015_2" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="RT-PCR Test Result" id="NBS718_2L" >
RT-PCR Test Result:</span></td><td>
<span id="NBS718_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS718)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="RT-PCR Test Date" id="NBS719_2L">RT-PCR Test Date:</span>
</td><td>
<span id="NBS719_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS719)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antigen Test Result" id="NBS720_2L" >
Antigen Test Result:</span></td><td>
<span id="NBS720_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS720)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Antigen Test Date" id="NBS721_2L">Antigen Test Date:</span>
</td><td>
<span id="NBS721_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS721)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgG Test Result" id="NBS722_2L" >
IgG Test Result:</span></td><td>
<span id="NBS722_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS722)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgG Test Date" id="NBS723_2L">IgG Test Date:</span>
</td><td>
<span id="NBS723_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS723)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM Test Result" id="NBS724_2L" >
IgM Test Result:</span></td><td>
<span id="NBS724_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS724)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgM Test Date" id="NBS725_2L">IgM Test Date:</span>
</td><td>
<span id="NBS725_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS725)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgA Test Result" id="NBS726_2L" >
IgA Test Result:</span></td><td>
<span id="NBS726_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS726)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgA Test Date" id="NBS727_2L">IgA Test Date:</span>
</td><td>
<span id="NBS727_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS727)"  />
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
